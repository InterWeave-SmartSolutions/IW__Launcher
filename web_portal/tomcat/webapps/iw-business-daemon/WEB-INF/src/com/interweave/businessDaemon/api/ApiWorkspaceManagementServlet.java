package com.interweave.businessDaemon.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;

/**
 * ApiWorkspaceManagementServlet — Phase 1 of AI Management Architecture.
 *
 * Provides read-only HTTP API for inspecting workspace integration projects.
 * This enables AI agents, the React portal, and external automation to
 * discover projects, transactions, queries, connections, and XSLT inventories
 * without needing direct filesystem access.
 *
 * GET /api/workspace/projects               — list all workspace projects
 * GET /api/workspace/projects/{name}        — full project details
 * GET /api/workspace/projects/{name}/config — raw config.xml content as JSON
 *
 * All endpoints require authentication (session or Bearer token).
 */
public class ApiWorkspaceManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private File workspaceDir;

    /** Projects to exclude from listing (internal/generated). */
    private static final List<String> EXCLUDED_DIRS = Arrays.asList(
        ".metadata", "GeneratedProfiles", "IW_Runtime_Sync", "FirstTest"
    );

    @Override
    public void init() throws ServletException {
        super.init();
        // Resolve workspace directory relative to the webapp
        String catalinaBase = System.getProperty("catalina.base", ".");
        workspaceDir = new File(catalinaBase, "../../workspace").getAbsoluteFile();
        if (!workspaceDir.isDirectory()) {
            // Try relative to webapp
            workspaceDir = new File(getServletContext().getRealPath("/"), "../../../workspace").getAbsoluteFile();
        }
        log("ApiWorkspaceManagementServlet initialized — workspace: " + workspaceDir.getAbsolutePath());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        // Auth check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo) || "/projects".equals(pathInfo)) {
            handleListProjects(response);
        } else if (pathInfo.startsWith("/projects/")) {
            String rest = pathInfo.substring("/projects/".length());
            int slash = rest.indexOf('/');
            if (slash < 0) {
                // GET /api/workspace/projects/{name}
                handleGetProject(rest, response);
            } else {
                String projectName = rest.substring(0, slash);
                String subPath = rest.substring(slash);
                if ("/config".equals(subPath)) {
                    handleGetConfig(projectName, response);
                } else {
                    sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown sub-resource: " + escapeJson(subPath) + "\"}");
                }
            }
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Use /projects or /projects/{name}\"}");
        }
    }

    // ─── List Projects ──────────────────────────────────────────────

    private void handleListProjects(HttpServletResponse response) throws IOException {
        File[] dirs = workspaceDir.listFiles(File::isDirectory);
        if (dirs == null) {
            sendJson(response, 500, "{\"success\":false,\"error\":\"Cannot read workspace directory\"}");
            return;
        }

        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"workspacePath\":\"").append(escapeJson(workspaceDir.getAbsolutePath())).append("\",\"projects\":[");

        boolean first = true;
        Arrays.sort(dirs);
        for (File dir : dirs) {
            if (EXCLUDED_DIRS.contains(dir.getName())) continue;
            if (dir.getName().startsWith(".")) continue;

            // Must have configuration/im/config.xml to be a valid project
            File configXml = new File(dir, "configuration/im/config.xml");
            if (!configXml.isFile()) continue;

            if (!first) json.append(",");
            first = false;

            ProjectSummary summary = scanProject(dir);
            json.append("{");
            json.append("\"name\":\"").append(escapeJson(dir.getName())).append("\"");
            json.append(",\"transactionCount\":").append(summary.transactionCount);
            json.append(",\"queryCount\":").append(summary.queryCount);
            json.append(",\"xsltCount\":").append(summary.xsltCount);
            json.append(",\"compiledClassCount\":").append(summary.compiledClassCount);
            json.append(",\"profileCount\":").append(summary.profileCount);
            json.append(",\"solutionType\":\"").append(escapeJson(summary.solutionType)).append("\"");
            json.append(",\"lastModified\":\"").append(formatIso(configXml.lastModified())).append("\"");
            json.append("}");
        }

        json.append("]}");
        sendJson(response, 200, json.toString());
    }

    // ─── Get Project Details ────────────────────────────────────────

    private void handleGetProject(String projectName, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File projectDir = new File(workspaceDir, projectName);
        File configXml = new File(projectDir, "configuration/im/config.xml");
        if (!projectDir.isDirectory() || !configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Project not found: " + escapeJson(projectName) + "\"}");
            return;
        }

        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"project\":{");
        json.append("\"name\":\"").append(escapeJson(projectName)).append("\"");
        json.append(",\"path\":\"").append(escapeJson(projectDir.getAbsolutePath())).append("\"");
        json.append(",\"lastModified\":\"").append(formatIso(configXml.lastModified())).append("\"");

        // Parse config.xml for transactions and queries
        try {
            Document doc = parseXml(configXml);
            Element root = doc.getDocumentElement();

            // Root attributes (engine config)
            json.append(",\"engineConfig\":{");
            NamedNodeMap rootAttrs = root.getAttributes();
            boolean firstAttr = true;
            for (int i = 0; i < rootAttrs.getLength(); i++) {
                Node attr = rootAttrs.item(i);
                if (!firstAttr) json.append(",");
                firstAttr = false;
                json.append("\"").append(escapeJson(attr.getNodeName())).append("\":\"").append(escapeJson(attr.getNodeValue())).append("\"");
            }
            json.append("}");

            // Transactions
            json.append(",\"transactions\":[");
            NodeList txNodes = root.getElementsByTagName("TransactionDescription");
            for (int i = 0; i < txNodes.getLength(); i++) {
                if (i > 0) json.append(",");
                Element tx = (Element) txNodes.item(i);
                appendElementAsJson(json, tx);
            }
            json.append("]");

            // Queries
            json.append(",\"queries\":[");
            NodeList qNodes = root.getElementsByTagName("Query");
            for (int i = 0; i < qNodes.getLength(); i++) {
                if (i > 0) json.append(",");
                Element q = (Element) qNodes.item(i);
                appendElementAsJson(json, q);
            }
            json.append("]");

        } catch (Exception e) {
            json.append(",\"configError\":\"").append(escapeJson(e.getMessage())).append("\"");
            json.append(",\"transactions\":[],\"queries\":[]");
        }

        // XSLT files
        json.append(",\"xsltFiles\":[");
        File xsltDir = new File(projectDir, "xslt");
        List<String> xsltFiles = listFilesRecursive(xsltDir, ".xslt");
        for (int i = 0; i < xsltFiles.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"").append(escapeJson(xsltFiles.get(i))).append("\"");
        }
        json.append("]");

        // Compiled classes
        json.append(",\"compiledClasses\":[");
        File classDir = new File(projectDir, "classes/iwtransformationserver");
        List<String> classFiles = listFilesRecursive(classDir, ".class");
        for (int i = 0; i < classFiles.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"").append(escapeJson(classFiles.get(i))).append("\"");
        }
        json.append("]");

        // Runtime profiles
        json.append(",\"runtimeProfiles\":[");
        File profileDir = new File(projectDir, "configuration/runtime_profiles");
        if (profileDir.isDirectory()) {
            File[] profileFiles = profileDir.listFiles((d, n) -> n.endsWith(".properties") || n.endsWith(".xml"));
            if (profileFiles != null) {
                Arrays.sort(profileFiles);
                boolean firstProfile = true;
                for (File pf : profileFiles) {
                    if (!firstProfile) json.append(",");
                    firstProfile = false;
                    json.append("{\"name\":\"").append(escapeJson(pf.getName()));
                    json.append("\",\"lastModified\":\"").append(formatIso(pf.lastModified()));
                    json.append("\",\"sizeBytes\":").append(pf.length()).append("}");
                }
            }
        }
        json.append("]");

        // Connection info (from dataconnections.xslt)
        File dcXslt = new File(projectDir, "xslt/include/dataconnections.xslt");
        if (dcXslt.isFile()) {
            json.append(",\"hasDataConnections\":true");
            json.append(",\"dataConnectionsLastModified\":\"").append(formatIso(dcXslt.lastModified())).append("\"");
        } else {
            json.append(",\"hasDataConnections\":false");
        }

        json.append("}}");
        sendJson(response, 200, json.toString());
    }

    // ─── Get Raw Config ─────────────────────────────────────────────

    private void handleGetConfig(String projectName, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Config not found for project: " + escapeJson(projectName) + "\"}");
            return;
        }

        // Read raw XML and return as escaped string in JSON
        String xmlContent = readFileContent(configXml, 1024 * 1024); // 1MB limit
        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"project\":\"").append(escapeJson(projectName));
        json.append("\",\"configXml\":\"").append(escapeJson(xmlContent)).append("\"}");
        sendJson(response, 200, json.toString());
    }

    // ─── XML Parsing ────────────────────────────────────────────────

    private Document parseXml(File xmlFile) throws Exception {
        // Mirrors WorkspaceProfileCompiler.parseXml() pattern — uses only
        // JAXP 1.1 methods to avoid AbstractMethodError from endorsed JAXB 1.0-ea.
        String xml = readFileContent(xmlFile, 2 * 1024 * 1024);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    private void appendElementAsJson(StringBuilder json, Element elem) {
        json.append("{");
        // Attributes
        NamedNodeMap attrs = elem.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            if (i > 0) json.append(",");
            Node attr = attrs.item(i);
            json.append("\"").append(escapeJson(attr.getNodeName())).append("\":\"");
            json.append(escapeJson(attr.getNodeValue())).append("\"");
        }

        // Child Parameter elements
        NodeList params = elem.getElementsByTagName("Parameter");
        if (params.getLength() > 0) {
            json.append(",\"parameters\":[");
            for (int i = 0; i < params.getLength(); i++) {
                if (i > 0) json.append(",");
                Element param = (Element) params.item(i);
                json.append("{");
                NamedNodeMap pAttrs = param.getAttributes();
                for (int j = 0; j < pAttrs.getLength(); j++) {
                    if (j > 0) json.append(",");
                    Node pAttr = pAttrs.item(j);
                    json.append("\"").append(escapeJson(pAttr.getNodeName())).append("\":\"");
                    json.append(escapeJson(pAttr.getNodeValue())).append("\"");
                }
                json.append("}");
            }
            json.append("]");
        }

        json.append("}");
    }

    // ─── Project Scanning ───────────────────────────────────────────

    private static class ProjectSummary {
        int transactionCount;
        int queryCount;
        int xsltCount;
        int compiledClassCount;
        int profileCount;
        String solutionType = "";
    }

    private ProjectSummary scanProject(File projectDir) {
        ProjectSummary s = new ProjectSummary();

        // Count XSLT files
        File xsltDir = new File(projectDir, "xslt");
        s.xsltCount = countFiles(xsltDir, ".xslt");

        // Count compiled classes
        File classDir = new File(projectDir, "classes/iwtransformationserver");
        s.compiledClassCount = countFiles(classDir, ".class");

        // Count runtime profiles (property files only)
        File profileDir = new File(projectDir, "configuration/runtime_profiles");
        if (profileDir.isDirectory()) {
            File[] profs = profileDir.listFiles((d, n) -> n.endsWith(".properties"));
            s.profileCount = (profs != null) ? profs.length : 0;
        }

        // Parse config.xml for transaction/query counts and solution type
        File configXml = new File(projectDir, "configuration/im/config.xml");
        if (configXml.isFile()) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();
                s.transactionCount = root.getElementsByTagName("TransactionDescription").getLength();
                s.queryCount = root.getElementsByTagName("Query").getLength();

                // Extract solution type from first transaction
                NodeList txNodes = root.getElementsByTagName("TransactionDescription");
                if (txNodes.getLength() > 0) {
                    s.solutionType = ((Element) txNodes.item(0)).getAttribute("Solution");
                }
                if (s.solutionType.isEmpty()) {
                    NodeList qNodes = root.getElementsByTagName("Query");
                    if (qNodes.getLength() > 0) {
                        s.solutionType = ((Element) qNodes.item(0)).getAttribute("Solution");
                    }
                }
            } catch (Exception e) {
                log("Error parsing config.xml for " + projectDir.getName() + ": " + e.getMessage());
            }
        }

        return s;
    }

    // ─── File Utilities ─────────────────────────────────────────────

    private int countFiles(File dir, String extension) {
        if (!dir.isDirectory()) return 0;
        int count = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            if (f.isDirectory()) {
                count += countFiles(f, extension);
            } else if (f.getName().endsWith(extension)) {
                count++;
            }
        }
        return count;
    }

    private List<String> listFilesRecursive(File dir, String extension) {
        List<String> result = new ArrayList<>();
        if (!dir.isDirectory()) return result;
        listFilesRecursiveHelper(dir, dir, extension, result);
        return result;
    }

    private void listFilesRecursiveHelper(File baseDir, File current, String extension, List<String> result) {
        File[] files = current.listFiles();
        if (files == null) return;
        Arrays.sort(files);
        for (File f : files) {
            if (f.isDirectory()) {
                listFilesRecursiveHelper(baseDir, f, extension, result);
            } else if (f.getName().endsWith(extension)) {
                // Return path relative to baseDir
                String relative = baseDir.toURI().relativize(f.toURI()).getPath();
                result.add(relative);
            }
        }
    }

    private String readFileContent(File file, int maxBytes) throws IOException {
        long len = file.length();
        if (len > maxBytes) {
            return "[File too large: " + len + " bytes, max " + maxBytes + "]";
        }
        byte[] bytes = new byte[(int) len];
        try (FileInputStream fis = new FileInputStream(file)) {
            int read = fis.read(bytes);
            return new String(bytes, 0, read, "UTF-8");
        }
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty()
            && name.matches("[a-zA-Z0-9_\\-]+")
            && !name.contains("..")
            && !name.startsWith(".");
    }

    // ─── JSON / HTTP Helpers ────────────────────────────────────────

    private static final SimpleDateFormat ISO_FORMAT;
    static {
        ISO_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        ISO_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private String formatIso(long millis) {
        synchronized (ISO_FORMAT) {
            return ISO_FORMAT.format(new Date(millis));
        }
    }

    private void sendJson(HttpServletResponse response, int status, String json) throws IOException {
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }
}
