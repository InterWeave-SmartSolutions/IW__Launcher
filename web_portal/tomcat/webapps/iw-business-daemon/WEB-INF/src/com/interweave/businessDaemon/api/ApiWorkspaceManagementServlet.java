package com.interweave.businessDaemon.api;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;

/**
 * ApiWorkspaceManagementServlet — Phases 1-2 of AI Management Architecture.
 *
 * Read + write HTTP API for workspace integration projects.
 *
 * GET    /api/workspace/projects                       — list all workspace projects
 * GET    /api/workspace/projects/{name}                — full project details
 * GET    /api/workspace/projects/{name}/config          — raw config.xml content
 * POST   /api/workspace/projects/{name}/transactions    — create TransactionDescription
 * POST   /api/workspace/projects/{name}/queries         — create Query
 * PUT    /api/workspace/projects/{name}/connections      — update dataconnections.xslt
 * PUT    /api/workspace/projects/{name}/transactions/{id} — update TransactionDescription
 * DELETE /api/workspace/projects/{name}/transactions/{id} — delete TransactionDescription
 * DELETE /api/workspace/projects/{name}/queries/{id}      — delete Query
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

    /** Default attributes for new TransactionDescription elements. */
    private static final String[][] TX_DEFAULT_ATTRS = {
        {"NumberOfExecutions", "0"}, {"InnerCycles", "0"},
        {"PrimaryTSURL", ""}, {"SecondaryTSURL", ""},
        {"PrimaryTSURLT", ""}, {"SecondaryTSURLT", ""},
        {"PrimaryTSURL1", ""}, {"SecondaryTSURL1", ""},
        {"PrimaryTSURLT1", ""}, {"SecondaryTSURLT1", ""},
        {"PrimaryTSURLD", ""}, {"SecondaryTSURLD", ""},
        {"FailoverURL", ""}, {"IsPublic", "false"},
    };

    @Override
    public void init() throws ServletException {
        super.init();
        String catalinaBase = System.getProperty("catalina.base", ".");
        workspaceDir = new File(catalinaBase, "../../workspace").getAbsoluteFile();
        if (!workspaceDir.isDirectory()) {
            workspaceDir = new File(getServletContext().getRealPath("/"), "../../../workspace").getAbsoluteFile();
        }
        log("ApiWorkspaceManagementServlet initialized — workspace: " + workspaceDir.getAbsolutePath());
    }

    // ─── Auth Check ──────────────────────────────────────────────────

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return false;
        }
        return true;
    }

    // ─── Route: GET ──────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        if (!checkAuth(request, response)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo) || "/projects".equals(pathInfo)) {
            handleListProjects(response);
        } else if (pathInfo.startsWith("/projects/")) {
            String rest = pathInfo.substring("/projects/".length());
            int slash = rest.indexOf('/');
            if (slash < 0) {
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

    // ─── Route: POST ─────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        if (!checkAuth(request, response)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Missing path\"}");
            return;
        }

        // POST /api/workspace/projects/{name}/transactions
        // POST /api/workspace/projects/{name}/queries
        if (pathInfo.startsWith("/projects/")) {
            String rest = pathInfo.substring("/projects/".length());
            int slash = rest.indexOf('/');
            if (slash > 0) {
                String projectName = rest.substring(0, slash);
                String subPath = rest.substring(slash);
                if ("/transactions".equals(subPath)) {
                    handleCreateTransaction(projectName, request, response);
                    return;
                } else if ("/queries".equals(subPath)) {
                    handleCreateQuery(projectName, request, response);
                    return;
                }
            }
        }
        sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown POST path\"}");
    }

    // ─── Route: PUT ──────────────────────────────────────────────────

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        if (!checkAuth(request, response)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Missing path\"}");
            return;
        }

        if (pathInfo.startsWith("/projects/")) {
            String rest = pathInfo.substring("/projects/".length());
            int slash = rest.indexOf('/');
            if (slash > 0) {
                String projectName = rest.substring(0, slash);
                String subPath = rest.substring(slash);

                // PUT /api/workspace/projects/{name}/connections
                if ("/connections".equals(subPath)) {
                    handleUpdateConnections(projectName, request, response);
                    return;
                }

                // PUT /api/workspace/projects/{name}/transactions/{id}
                if (subPath.startsWith("/transactions/")) {
                    String txId = subPath.substring("/transactions/".length());
                    handleUpdateTransaction(projectName, txId, request, response);
                    return;
                }
            }
        }
        sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown PUT path\"}");
    }

    // ─── Route: DELETE ───────────────────────────────────────────────

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        if (!checkAuth(request, response)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Missing path\"}");
            return;
        }

        if (pathInfo.startsWith("/projects/")) {
            String rest = pathInfo.substring("/projects/".length());
            int slash = rest.indexOf('/');
            if (slash > 0) {
                String projectName = rest.substring(0, slash);
                String subPath = rest.substring(slash);

                // DELETE /api/workspace/projects/{name}/transactions/{id}
                if (subPath.startsWith("/transactions/")) {
                    String txId = subPath.substring("/transactions/".length());
                    handleDeleteElement(projectName, txId, "TransactionDescription", response);
                    return;
                }

                // DELETE /api/workspace/projects/{name}/queries/{id}
                if (subPath.startsWith("/queries/")) {
                    String qId = subPath.substring("/queries/".length());
                    handleDeleteElement(projectName, qId, "Query", response);
                    return;
                }
            }
        }
        sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown DELETE path\"}");
    }

    // ═══════════════════════════════════════════════════════════════════
    // WRITE HANDLERS
    // ═══════════════════════════════════════════════════════════════════

    // ─── Create Transaction ──────────────────────────────────────────

    private void handleCreateTransaction(String projectName, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Project not found\"}");
            return;
        }

        String body = readRequestBody(request);
        String id = extractJsonString(body, "id");
        if (id == null || id.isEmpty() || !id.matches("[A-Za-z0-9_]+")) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Missing or invalid 'id'\"}");
            return;
        }

        synchronized (workspaceDir) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();

                // Check for duplicate ID
                NodeList existing = root.getElementsByTagName("TransactionDescription");
                for (int i = 0; i < existing.getLength(); i++) {
                    if (id.equals(((Element) existing.item(i)).getAttribute("Id"))) {
                        sendJson(response, 409, "{\"success\":false,\"error\":\"Transaction '" + escapeJson(id) + "' already exists\"}");
                        return;
                    }
                }

                // Create element
                Element tx = doc.createElement("TransactionDescription");
                tx.setAttribute("Id", id);
                tx.setAttribute("Description", extractJsonStringOr(body, "description", ""));
                tx.setAttribute("Solution", extractJsonStringOr(body, "solution", ""));
                tx.setAttribute("Interval", extractJsonStringOr(body, "interval", "60000"));
                tx.setAttribute("Shift", extractJsonStringOr(body, "shift", "0"));
                tx.setAttribute("RunAtStartUp", extractJsonStringOr(body, "runAtStartUp", "false"));
                tx.setAttribute("IsActive", extractJsonStringOr(body, "isActive", "true"));
                tx.setAttribute("IsStateful", extractJsonStringOr(body, "isStateful", "true"));
                for (String[] def : TX_DEFAULT_ATTRS) {
                    tx.setAttribute(def[0], def[1]);
                }

                // Add parameters
                addParametersFromJson(doc, tx, body);

                // Always add QueryStartTime if not already present
                if (!hasParameter(tx, "QueryStartTime")) {
                    Element qst = doc.createElement("Parameter");
                    qst.setAttribute("Name", "QueryStartTime");
                    qst.setAttribute("Value", new Timestamp(System.currentTimeMillis()).toString());
                    tx.appendChild(qst);
                }

                root.appendChild(tx);
                writeXmlAtomic(configXml, doc);

                StringBuilder json = new StringBuilder();
                json.append("{\"success\":true,\"entityId\":\"").append(escapeJson(id));
                json.append("\",\"message\":\"Transaction created\",\"transaction\":");
                appendElementAsJson(json, tx);
                json.append("}");
                sendJson(response, 201, json.toString());

            } catch (Exception e) {
                sendJson(response, 500, "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // ─── Create Query ────────────────────────────────────────────────

    private void handleCreateQuery(String projectName, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Project not found\"}");
            return;
        }

        String body = readRequestBody(request);
        String id = extractJsonString(body, "id");
        if (id == null || id.isEmpty() || !id.matches("[A-Za-z0-9_]+")) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Missing or invalid 'id'\"}");
            return;
        }

        synchronized (workspaceDir) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();

                // Check for duplicate
                NodeList existing = root.getElementsByTagName("Query");
                for (int i = 0; i < existing.getLength(); i++) {
                    if (id.equals(((Element) existing.item(i)).getAttribute("Id"))) {
                        sendJson(response, 409, "{\"success\":false,\"error\":\"Query '" + escapeJson(id) + "' already exists\"}");
                        return;
                    }
                }

                Element q = doc.createElement("Query");
                q.setAttribute("Id", id);
                q.setAttribute("Description", extractJsonStringOr(body, "description", ""));
                q.setAttribute("Solution", extractJsonStringOr(body, "solution", ""));
                q.setAttribute("InnerCycles", "0");
                q.setAttribute("IsActive", extractJsonStringOr(body, "isActive", "true"));
                q.setAttribute("IsPublic", "false");
                // URL attributes
                for (String[] def : TX_DEFAULT_ATTRS) {
                    if (def[0].contains("TSURL") || def[0].equals("FailoverURL")) {
                        q.setAttribute(def[0], def[1]);
                    }
                }

                addParametersFromJson(doc, q, body);

                if (!hasParameter(q, "QueryStartTime")) {
                    Element qst = doc.createElement("Parameter");
                    qst.setAttribute("Name", "QueryStartTime");
                    qst.setAttribute("Value", new Timestamp(System.currentTimeMillis()).toString());
                    q.appendChild(qst);
                }

                root.appendChild(q);
                writeXmlAtomic(configXml, doc);

                StringBuilder json = new StringBuilder();
                json.append("{\"success\":true,\"entityId\":\"").append(escapeJson(id));
                json.append("\",\"message\":\"Query created\",\"query\":");
                appendElementAsJson(json, q);
                json.append("}");
                sendJson(response, 201, json.toString());

            } catch (Exception e) {
                sendJson(response, 500, "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // ─── Update Transaction ──────────────────────────────────────────

    private void handleUpdateTransaction(String projectName, String txId,
                                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName) || !isValidName(txId)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid name\"}");
            return;
        }

        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Project not found\"}");
            return;
        }

        String body = readRequestBody(request);

        synchronized (workspaceDir) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();

                // Find the transaction
                Element tx = findElementById(root, "TransactionDescription", txId);
                if (tx == null) {
                    sendJson(response, 404, "{\"success\":false,\"error\":\"Transaction not found: " + escapeJson(txId) + "\"}");
                    return;
                }

                // Update attributes if present in body
                updateAttrIfPresent(tx, body, "description", "Description");
                updateAttrIfPresent(tx, body, "solution", "Solution");
                updateAttrIfPresent(tx, body, "interval", "Interval");
                updateAttrIfPresent(tx, body, "shift", "Shift");
                updateAttrIfPresent(tx, body, "runAtStartUp", "RunAtStartUp");
                updateAttrIfPresent(tx, body, "isActive", "IsActive");
                updateAttrIfPresent(tx, body, "isStateful", "IsStateful");

                // If parameters are provided, replace all
                if (body.contains("\"parameters\"")) {
                    // Remove existing parameters
                    NodeList params = tx.getElementsByTagName("Parameter");
                    while (params.getLength() > 0) {
                        tx.removeChild(params.item(0));
                    }
                    addParametersFromJson(doc, tx, body);
                    if (!hasParameter(tx, "QueryStartTime")) {
                        Element qst = doc.createElement("Parameter");
                        qst.setAttribute("Name", "QueryStartTime");
                        qst.setAttribute("Value", new Timestamp(System.currentTimeMillis()).toString());
                        tx.appendChild(qst);
                    }
                }

                writeXmlAtomic(configXml, doc);

                StringBuilder json = new StringBuilder();
                json.append("{\"success\":true,\"message\":\"Transaction updated\",\"transaction\":");
                appendElementAsJson(json, tx);
                json.append("}");
                sendJson(response, 200, json.toString());

            } catch (Exception e) {
                sendJson(response, 500, "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // ─── Update Connections (dataconnections.xslt) ───────────────────

    private void handleUpdateConnections(String projectName, HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File dcXslt = new File(workspaceDir, projectName + "/xslt/include/dataconnections.xslt");
        if (!dcXslt.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"dataconnections.xslt not found\"}");
            return;
        }

        String body = readRequestBody(request);
        String target = extractJsonString(body, "target");
        if (target == null || (!"source".equals(target) && !"destination".equals(target))) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"'target' must be 'source' or 'destination'\"}");
            return;
        }

        String driver = extractJsonString(body, "driver");
        String url = extractJsonString(body, "url");
        String user = extractJsonString(body, "user");
        String password = extractJsonString(body, "password");

        // source → iw* params, destination → ms* params
        String driverParam = "source".equals(target) ? "iwdriver" : "msdriver";
        String urlParam = "source".equals(target) ? "iwurl" : "msurl";
        String userParam = "source".equals(target) ? "iwuser" : "msuser";
        String passParam = "source".equals(target) ? "password" : "mspassword";

        synchronized (workspaceDir) {
            try {
                String content = readFileContent(dcXslt, 1024 * 1024);

                if (driver != null) content = replaceXsltParam(content, driverParam, driver);
                if (url != null) content = replaceXsltParam(content, urlParam, url);
                if (user != null) content = replaceXsltParam(content, userParam, user);
                if (password != null) content = replaceXsltParam(content, passParam, password);

                // Write atomically
                File tmpFile = new File(dcXslt.getAbsolutePath() + ".tmp");
                writeStringToFile(tmpFile, content);
                if (dcXslt.exists()) dcXslt.delete();
                if (!tmpFile.renameTo(dcXslt)) {
                    // Fallback: direct overwrite
                    writeStringToFile(dcXslt, content);
                    tmpFile.delete();
                }

                sendJson(response, 200, "{\"success\":true,\"message\":\"Connections updated for " + escapeJson(target) + "\"}");

            } catch (Exception e) {
                sendJson(response, 500, "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // ─── Delete Transaction or Query ─────────────────────────────────

    private void handleDeleteElement(String projectName, String elementId,
                                      String tagName, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName) || !isValidName(elementId)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid name\"}");
            return;
        }

        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Project not found\"}");
            return;
        }

        synchronized (workspaceDir) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();

                Element elem = findElementById(root, tagName, elementId);
                if (elem == null) {
                    sendJson(response, 404, "{\"success\":false,\"error\":\"" + tagName + " not found: " + escapeJson(elementId) + "\"}");
                    return;
                }

                root.removeChild(elem);
                writeXmlAtomic(configXml, doc);

                sendJson(response, 200, "{\"success\":true,\"deleted\":\"" + escapeJson(elementId) + "\",\"type\":\"" + tagName + "\"}");

            } catch (Exception e) {
                sendJson(response, 500, "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // READ HANDLERS (unchanged from Phase 1)
    // ═══════════════════════════════════════════════════════════════════

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

        try {
            Document doc = parseXml(configXml);
            Element root = doc.getDocumentElement();

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

            json.append(",\"transactions\":[");
            NodeList txNodes = root.getElementsByTagName("TransactionDescription");
            for (int i = 0; i < txNodes.getLength(); i++) {
                if (i > 0) json.append(",");
                appendElementAsJson(json, (Element) txNodes.item(i));
            }
            json.append("]");

            json.append(",\"queries\":[");
            NodeList qNodes = root.getElementsByTagName("Query");
            for (int i = 0; i < qNodes.getLength(); i++) {
                if (i > 0) json.append(",");
                appendElementAsJson(json, (Element) qNodes.item(i));
            }
            json.append("]");

        } catch (Exception e) {
            json.append(",\"configError\":\"").append(escapeJson(e.getMessage())).append("\"");
            json.append(",\"transactions\":[],\"queries\":[]");
        }

        // XSLT files
        json.append(",\"xsltFiles\":[");
        List<String> xsltFiles = listFilesRecursive(new File(projectDir, "xslt"), ".xslt");
        for (int i = 0; i < xsltFiles.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"").append(escapeJson(xsltFiles.get(i))).append("\"");
        }
        json.append("]");

        // Compiled classes
        json.append(",\"compiledClasses\":[");
        List<String> classFiles = listFilesRecursive(new File(projectDir, "classes/iwtransformationserver"), ".class");
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

        // Connection info
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

    private void handleGetConfig(String projectName, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }
        File configXml = new File(workspaceDir, projectName + "/configuration/im/config.xml");
        if (!configXml.isFile()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Config not found\"}");
            return;
        }
        String xmlContent = readFileContent(configXml, 1024 * 1024);
        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"project\":\"").append(escapeJson(projectName));
        json.append("\",\"configXml\":\"").append(escapeJson(xmlContent)).append("\"}");
        sendJson(response, 200, json.toString());
    }

    // ═══════════════════════════════════════════════════════════════════
    // XML HELPERS
    // ═══════════════════════════════════════════════════════════════════

    private Document parseXml(File xmlFile) throws Exception {
        String xml = readFileContent(xmlFile, 2 * 1024 * 1024);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    /** Write XML document to file, matching engine format (no declaration, no indent). */
    private void writeXml(File file, Document doc) throws IOException {
        OutputStream out = null;
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            out = new BufferedOutputStream(new FileOutputStream(file));
            transformer.transform(new DOMSource(doc), new StreamResult(out));
        } catch (Exception e) {
            throw new IOException("Failed to write XML: " + file.getAbsolutePath(), e);
        } finally {
            if (out != null) out.close();
        }
    }

    /** Write XML atomically: write to .tmp, delete original, rename. */
    private void writeXmlAtomic(File target, Document doc) throws IOException {
        File tmpFile = new File(target.getAbsolutePath() + ".tmp");
        writeXml(tmpFile, doc);
        if (target.exists()) target.delete();
        if (!tmpFile.renameTo(target)) {
            // Fallback: direct overwrite
            writeXml(target, doc);
            tmpFile.delete();
        }
    }

    private Element findElementById(Element root, String tagName, String id) {
        NodeList nodes = root.getElementsByTagName(tagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element elem = (Element) nodes.item(i);
            if (id.equals(elem.getAttribute("Id"))) return elem;
        }
        return null;
    }

    private boolean hasParameter(Element parent, String paramName) {
        NodeList params = parent.getElementsByTagName("Parameter");
        for (int i = 0; i < params.getLength(); i++) {
            if (paramName.equals(((Element) params.item(i)).getAttribute("Name"))) return true;
        }
        return false;
    }

    /** Parse "parameters" JSON array from request body and add as child Parameter elements. */
    private void addParametersFromJson(Document doc, Element parent, String body) {
        // Find the parameters array
        int arrStart = body.indexOf("\"parameters\"");
        if (arrStart < 0) return;
        int bracketOpen = body.indexOf('[', arrStart);
        if (bracketOpen < 0) return;

        // Extract each {...} object from the array
        int pos = bracketOpen + 1;
        while (pos < body.length()) {
            int objStart = body.indexOf('{', pos);
            if (objStart < 0) break;

            // Find matching closing brace
            int depth = 1;
            int objEnd = objStart + 1;
            while (objEnd < body.length() && depth > 0) {
                char c = body.charAt(objEnd);
                if (c == '{') depth++;
                else if (c == '}') depth--;
                else if (c == '"') {
                    objEnd++;
                    while (objEnd < body.length() && body.charAt(objEnd) != '"') {
                        if (body.charAt(objEnd) == '\\') objEnd++;
                        objEnd++;
                    }
                }
                objEnd++;
            }

            if (depth == 0) {
                String paramObj = body.substring(objStart, objEnd);
                String name = extractJsonString(paramObj, "name");
                if (name == null) name = extractJsonString(paramObj, "Name");
                String value = extractJsonString(paramObj, "value");
                if (value == null) value = extractJsonString(paramObj, "Value");
                String fixed = extractJsonString(paramObj, "fixed");
                if (fixed == null) fixed = extractJsonString(paramObj, "Fixed");

                if (name != null && !name.isEmpty()) {
                    Element param = doc.createElement("Parameter");
                    param.setAttribute("Name", name);
                    param.setAttribute("Value", value != null ? value : "");
                    if ("true".equalsIgnoreCase(fixed)) {
                        param.setAttribute("Fixed", "true");
                    }
                    parent.appendChild(param);
                }
            }

            pos = objEnd;
            // Check if we hit the array close
            int nextComma = body.indexOf(',', pos);
            int arrClose = body.indexOf(']', pos);
            if (arrClose >= 0 && (nextComma < 0 || arrClose < nextComma)) break;
        }
    }

    private void appendElementAsJson(StringBuilder json, Element elem) {
        json.append("{");
        NamedNodeMap attrs = elem.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            if (i > 0) json.append(",");
            Node attr = attrs.item(i);
            json.append("\"").append(escapeJson(attr.getNodeName())).append("\":\"");
            json.append(escapeJson(attr.getNodeValue())).append("\"");
        }
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

    // ═══════════════════════════════════════════════════════════════════
    // XSLT PARAM REPLACEMENT
    // ═══════════════════════════════════════════════════════════════════

    /** Replace an xsl:param value in dataconnections.xslt content. */
    private String replaceXsltParam(String content, String paramName, String newValue) {
        // Pattern: <xsl:param name="paramName">oldValue</xsl:param>
        String openTag = "<xsl:param name=\"" + paramName + "\">";
        String closeTag = "</xsl:param>";
        int start = content.indexOf(openTag);
        if (start < 0) return content;
        int valueStart = start + openTag.length();
        int valueEnd = content.indexOf(closeTag, valueStart);
        if (valueEnd < 0) return content;
        return content.substring(0, valueStart) + escapeXml(newValue) + content.substring(valueEnd);
    }

    private String escapeXml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    // ═══════════════════════════════════════════════════════════════════
    // PROJECT SCANNING
    // ═══════════════════════════════════════════════════════════════════

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
        s.xsltCount = countFiles(new File(projectDir, "xslt"), ".xslt");
        File classDir = new File(projectDir, "classes/iwtransformationserver");
        s.compiledClassCount = countFiles(classDir, ".class");
        File profileDir = new File(projectDir, "configuration/runtime_profiles");
        if (profileDir.isDirectory()) {
            File[] profs = profileDir.listFiles((d, n) -> n.endsWith(".properties"));
            s.profileCount = (profs != null) ? profs.length : 0;
        }
        File configXml = new File(projectDir, "configuration/im/config.xml");
        if (configXml.isFile()) {
            try {
                Document doc = parseXml(configXml);
                Element root = doc.getDocumentElement();
                s.transactionCount = root.getElementsByTagName("TransactionDescription").getLength();
                s.queryCount = root.getElementsByTagName("Query").getLength();
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

    // ═══════════════════════════════════════════════════════════════════
    // FILE UTILITIES
    // ═══════════════════════════════════════════════════════════════════

    private int countFiles(File dir, String extension) {
        if (!dir.isDirectory()) return 0;
        int count = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            if (f.isDirectory()) count += countFiles(f, extension);
            else if (f.getName().endsWith(extension)) count++;
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
            if (f.isDirectory()) listFilesRecursiveHelper(baseDir, f, extension, result);
            else if (f.getName().endsWith(extension)) {
                result.add(baseDir.toURI().relativize(f.toURI()).getPath());
            }
        }
    }

    private String readFileContent(File file, int maxBytes) throws IOException {
        long len = file.length();
        if (len > maxBytes) return "[File too large: " + len + " bytes, max " + maxBytes + "]";
        byte[] bytes = new byte[(int) len];
        try (FileInputStream fis = new FileInputStream(file)) {
            int read = fis.read(bytes);
            return new String(bytes, 0, read, "UTF-8");
        }
    }

    private void writeStringToFile(File file, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes("UTF-8"));
        }
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty()
            && name.matches("[a-zA-Z0-9_\\-]+")
            && !name.contains("..")
            && !name.startsWith(".");
    }

    // ═══════════════════════════════════════════════════════════════════
    // JSON / HTTP HELPERS
    // ═══════════════════════════════════════════════════════════════════

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }

    private String extractJsonString(String json, String key) {
        if (json == null || key == null) return null;
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex < 0) return null;
        int colonIndex = json.indexOf(':', keyIndex + searchKey.length());
        if (colonIndex < 0) return null;
        int start = json.indexOf('"', colonIndex + 1);
        if (start < 0) return null;
        start++;
        int end = start;
        while (end < json.length()) {
            if (json.charAt(end) == '\\') { end += 2; continue; }
            if (json.charAt(end) == '"') break;
            end++;
        }
        if (end >= json.length()) return null;
        return json.substring(start, end).replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private String extractJsonStringOr(String json, String key, String defaultValue) {
        String val = extractJsonString(json, key);
        return val != null ? val : defaultValue;
    }

    /** Update an element attribute if the JSON body contains the given key. */
    private void updateAttrIfPresent(Element elem, String body, String jsonKey, String attrName) {
        String val = extractJsonString(body, jsonKey);
        if (val != null) elem.setAttribute(attrName, val);
    }

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
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
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
