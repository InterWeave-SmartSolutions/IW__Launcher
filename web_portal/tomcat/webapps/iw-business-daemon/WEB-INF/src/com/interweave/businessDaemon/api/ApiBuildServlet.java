package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ApiBuildServlet — Phase 1 of AI Management Architecture (Build layer).
 *
 * Provides HTTP API for compiling XSLT transformers to Java bytecode using
 * Apache Xalan XSLTC, replicating the IDE's ProjectActions stage 1 build.
 *
 * POST /api/build/compile-xslt    — compile one or more XSLT files for a project
 * GET  /api/build/inventory/{name} — list compilable XSLT files for a project
 *
 * All endpoints require authentication + admin role.
 *
 * The XSLTC compiler JARs are located in the iwtransformationserver webapp:
 *   iwtransformationserver/WEB-INF/lib/xsltc.jar
 *   iwtransformationserver/WEB-INF/lib/xalan.jar
 *   iwtransformationserver/WEB-INF/lib/serializer.jar
 */
public class ApiBuildServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int BUILD_TIMEOUT_MS = 60000; // 60 seconds per file

    private File workspaceDir;
    private File tsLibDir;
    private String javaExe;

    @Override
    public void init() throws ServletException {
        super.init();
        String catalinaBase = System.getProperty("catalina.base", ".");

        workspaceDir = new File(catalinaBase, "../../workspace").getAbsoluteFile();
        if (!workspaceDir.isDirectory()) {
            workspaceDir = new File(getServletContext().getRealPath("/"), "../../../workspace").getAbsoluteFile();
        }

        tsLibDir = new File(catalinaBase, "webapps/iwtransformationserver/WEB-INF/lib").getAbsoluteFile();

        // Find Java executable (bundled JRE)
        File jreDir = new File(catalinaBase, "../../jre").getAbsoluteFile();
        File javaCmd = new File(jreDir, "bin/java.exe");
        if (!javaCmd.isFile()) {
            javaCmd = new File(jreDir, "bin/java");
        }
        if (!javaCmd.isFile()) {
            // Fallback to system Java
            javaCmd = new File(System.getProperty("java.home"), "bin/java.exe");
            if (!javaCmd.isFile()) {
                javaCmd = new File(System.getProperty("java.home"), "bin/java");
            }
        }
        javaExe = javaCmd.getAbsolutePath();

        log("ApiBuildServlet initialized — workspace: " + workspaceDir.getAbsolutePath()
            + ", tsLib: " + tsLibDir.getAbsolutePath()
            + ", java: " + javaExe);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        if (!checkAuth(request, response, false)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/inventory/")) {
            String projectName = pathInfo.substring("/inventory/".length());
            handleInventory(projectName, response);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Use GET /inventory/{projectName} or POST /compile-xslt\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        // Admin required for build operations
        if (!checkAuth(request, response, true)) return;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/compile-xslt".equals(pathInfo)) {
            handleCompileXslt(request, response);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Use POST /compile-xslt\"}");
        }
    }

    // ─── Inventory ──────────────────────────────────────────────────

    /**
     * GET /api/build/inventory/{projectName}
     * Lists all .xslt files in the project's xslt/ directory (excludes include/ subdirs).
     */
    private void handleInventory(String projectName, HttpServletResponse response) throws IOException {
        if (!isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Invalid project name\"}");
            return;
        }

        File xsltDir = new File(workspaceDir, projectName + "/xslt");
        if (!xsltDir.isDirectory()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"XSLT directory not found for: " + escapeJson(projectName) + "\"}");
            return;
        }

        File classDir = new File(workspaceDir, projectName + "/classes/iwtransformationserver");

        // List top-level .xslt files (these are the compilable transformers)
        File[] xsltFiles = xsltDir.listFiles((d, n) -> n.endsWith(".xslt"));
        if (xsltFiles == null) xsltFiles = new File[0];
        Arrays.sort(xsltFiles);

        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"project\":\"").append(escapeJson(projectName));
        json.append("\",\"compilerAvailable\":").append(isCompilerAvailable());
        json.append(",\"transformers\":[");

        for (int i = 0; i < xsltFiles.length; i++) {
            if (i > 0) json.append(",");
            String name = xsltFiles[i].getName();
            String baseName = name.substring(0, name.length() - ".xslt".length());
            File classFile = new File(classDir, baseName + ".class");

            json.append("{");
            json.append("\"name\":\"").append(escapeJson(name)).append("\"");
            json.append(",\"baseName\":\"").append(escapeJson(baseName)).append("\"");
            json.append(",\"compiled\":").append(classFile.isFile());
            json.append(",\"sourceSize\":").append(xsltFiles[i].length());
            json.append(",\"sourceLastModified\":\"").append(formatIso(xsltFiles[i].lastModified())).append("\"");
            if (classFile.isFile()) {
                json.append(",\"classSize\":").append(classFile.length());
                json.append(",\"classLastModified\":\"").append(formatIso(classFile.lastModified())).append("\"");
                json.append(",\"stale\":").append(xsltFiles[i].lastModified() > classFile.lastModified());
            }
            json.append("}");
        }

        json.append("]}");
        sendJson(response, 200, json.toString());
    }

    // ─── Compile XSLT ──────────────────────────────────────────────

    /**
     * POST /api/build/compile-xslt
     * Body: { "projectName": "CRM2QB", "xsltFiles": ["file1.xslt", "file2.xslt"] }
     *       xsltFiles is optional — defaults to all top-level .xslt files
     *
     * Invokes Xalan XSLTC compiler as subprocess for each file:
     *   java -cp "xsltc.jar;xalan.jar;serializer.jar"
     *        org.apache.xalan.xsltc.cmdline.Compile
     *        -o {baseName} -d {outputDir} {inputFile}
     */
    private void handleCompileXslt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isCompilerAvailable()) {
            sendJson(response, 503, "{\"success\":false,\"error\":\"XSLTC compiler JARs not found in iwtransformationserver\"}");
            return;
        }

        // Parse request body
        String body = readBody(request);
        String projectName = extractJsonString(body, "projectName");
        if (projectName == null || !isValidName(projectName)) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"projectName is required\"}");
            return;
        }

        File xsltDir = new File(workspaceDir, projectName + "/xslt");
        File outputDir = new File(workspaceDir, projectName + "/classes/iwtransformationserver");
        if (!xsltDir.isDirectory()) {
            sendJson(response, 404, "{\"success\":false,\"error\":\"XSLT directory not found\"}");
            return;
        }

        // Ensure output directory exists
        if (!outputDir.isDirectory()) {
            outputDir.mkdirs();
        }

        // Determine which files to compile
        List<String> filesToCompile = extractJsonStringArray(body, "xsltFiles");
        if (filesToCompile == null || filesToCompile.isEmpty()) {
            // Default: all top-level .xslt files
            File[] xsltFiles = xsltDir.listFiles((d, n) -> n.endsWith(".xslt"));
            if (xsltFiles == null || xsltFiles.length == 0) {
                sendJson(response, 200, "{\"success\":true,\"compiled\":[],\"errors\":[],\"message\":\"No XSLT files found\"}");
                return;
            }
            filesToCompile = new ArrayList<>();
            for (File f : xsltFiles) {
                filesToCompile.add(f.getName());
            }
        }

        // Build classpath
        String cpSep = System.getProperty("os.name", "").toLowerCase().contains("win") ? ";" : ":";
        String classpath = new File(tsLibDir, "xsltc.jar").getAbsolutePath() + cpSep
            + new File(tsLibDir, "xalan.jar").getAbsolutePath() + cpSep
            + new File(tsLibDir, "serializer.jar").getAbsolutePath();

        // Compile each file
        StringBuilder compiled = new StringBuilder("[");
        StringBuilder errors = new StringBuilder("[");
        int successCount = 0;
        int errorCount = 0;

        for (String fileName : filesToCompile) {
            // Security: validate filename
            if (!fileName.endsWith(".xslt") || fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
                if (errorCount > 0) errors.append(",");
                errors.append("{\"file\":\"").append(escapeJson(fileName)).append("\",\"error\":\"Invalid filename\"}");
                errorCount++;
                continue;
            }

            File xsltFile = new File(xsltDir, fileName);
            if (!xsltFile.isFile()) {
                if (errorCount > 0) errors.append(",");
                errors.append("{\"file\":\"").append(escapeJson(fileName)).append("\",\"error\":\"File not found\"}");
                errorCount++;
                continue;
            }

            String baseName = fileName.substring(0, fileName.length() - ".xslt".length());

            try {
                CompileResult result = compileXslt(classpath, baseName, xsltFile, outputDir);
                if (result.success) {
                    if (successCount > 0) compiled.append(",");
                    compiled.append("{\"file\":\"").append(escapeJson(fileName));
                    compiled.append("\",\"className\":\"").append(escapeJson(baseName + ".class"));
                    compiled.append("\",\"durationMs\":").append(result.durationMs).append("}");
                    successCount++;
                } else {
                    if (errorCount > 0) errors.append(",");
                    errors.append("{\"file\":\"").append(escapeJson(fileName));
                    errors.append("\",\"error\":\"").append(escapeJson(result.errorMessage));
                    errors.append("\",\"durationMs\":").append(result.durationMs).append("}");
                    errorCount++;
                }
            } catch (Exception e) {
                if (errorCount > 0) errors.append(",");
                errors.append("{\"file\":\"").append(escapeJson(fileName));
                errors.append("\",\"error\":\"").append(escapeJson(e.getClass().getSimpleName() + ": " + e.getMessage())).append("\"}");
                errorCount++;
            }
        }

        compiled.append("]");
        errors.append("]");

        // Broadcast SSE event if anything compiled successfully
        if (successCount > 0) {
            try {
                SyncEventServlet.broadcast("build-complete", projectName, "ai");
            } catch (Exception e) {
                log("SSE broadcast failed: " + e.getMessage());
            }
        }

        StringBuilder json = new StringBuilder();
        json.append("{\"success\":").append(errorCount == 0);
        json.append(",\"project\":\"").append(escapeJson(projectName)).append("\"");
        json.append(",\"compiledCount\":").append(successCount);
        json.append(",\"errorCount\":").append(errorCount);
        json.append(",\"compiled\":").append(compiled);
        json.append(",\"errors\":").append(errors);
        json.append("}");
        sendJson(response, 200, json.toString());
    }

    // ─── XSLTC Subprocess ───────────────────────────────────────────

    private static class CompileResult {
        boolean success;
        String errorMessage;
        long durationMs;
    }

    private CompileResult compileXslt(String classpath, String outputName, File xsltFile, File outputDir)
            throws IOException, InterruptedException {
        CompileResult result = new CompileResult();
        long start = System.currentTimeMillis();

        ProcessBuilder pb = new ProcessBuilder(
            javaExe,
            "-cp", classpath,
            "org.apache.xalan.xsltc.cmdline.Compile",
            "-o", outputName,
            "-d", outputDir.getAbsolutePath(),
            xsltFile.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        pb.directory(xsltFile.getParentFile());

        Process process = pb.start();
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        boolean finished = process.waitFor(BUILD_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
        result.durationMs = System.currentTimeMillis() - start;

        if (!finished) {
            process.destroyForcibly();
            result.success = false;
            result.errorMessage = "Compilation timed out after " + BUILD_TIMEOUT_MS + "ms";
            return result;
        }

        int exitCode = process.exitValue();
        result.success = (exitCode == 0);
        if (!result.success) {
            result.errorMessage = "Exit code " + exitCode + ": " + output.toString().trim();
        }

        // Verify .class file was created
        if (result.success) {
            File classFile = new File(outputDir, outputName + ".class");
            if (!classFile.isFile()) {
                result.success = false;
                result.errorMessage = "Compiler exited 0 but .class file not found";
            }
        }

        return result;
    }

    private boolean isCompilerAvailable() {
        return new File(tsLibDir, "xsltc.jar").isFile()
            && new File(tsLibDir, "xalan.jar").isFile()
            && new File(tsLibDir, "serializer.jar").isFile();
    }

    // ─── Auth ───────────────────────────────────────────────────────

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response, boolean requireAdmin)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return false;
        }
        if (requireAdmin) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            if (isAdmin == null || !isAdmin) {
                sendJson(response, 403, "{\"success\":false,\"error\":\"Admin access required\"}");
                return false;
            }
        }
        return true;
    }

    // ─── JSON Parsing (simple, no external deps) ────────────────────

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private String extractJsonString(String json, String key) {
        if (json == null || key == null) return null;
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + search.length());
        if (colon < 0) return null;
        int start = json.indexOf('"', colon + 1);
        if (start < 0) return null;
        start++;
        int end = start;
        while (end < json.length()) {
            if (json.charAt(end) == '\\') { end += 2; continue; }
            if (json.charAt(end) == '"') break;
            end++;
        }
        return (end < json.length()) ? json.substring(start, end) : null;
    }

    /**
     * Extract a simple JSON string array: "key": ["val1", "val2"]
     */
    private List<String> extractJsonStringArray(String json, String key) {
        if (json == null || key == null) return null;
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + search.length());
        if (colon < 0) return null;
        int bracketStart = json.indexOf('[', colon);
        if (bracketStart < 0) return null;
        int bracketEnd = json.indexOf(']', bracketStart);
        if (bracketEnd < 0) return null;

        String arrayContent = json.substring(bracketStart + 1, bracketEnd).trim();
        if (arrayContent.isEmpty()) return new ArrayList<>();

        List<String> result = new ArrayList<>();
        int pos = 0;
        while (pos < arrayContent.length()) {
            int qStart = arrayContent.indexOf('"', pos);
            if (qStart < 0) break;
            int qEnd = qStart + 1;
            while (qEnd < arrayContent.length()) {
                if (arrayContent.charAt(qEnd) == '\\') { qEnd += 2; continue; }
                if (arrayContent.charAt(qEnd) == '"') break;
                qEnd++;
            }
            if (qEnd < arrayContent.length()) {
                result.add(arrayContent.substring(qStart + 1, qEnd));
            }
            pos = qEnd + 1;
        }
        return result;
    }

    // ─── Utilities ──────────────────────────────────────────────────

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty()
            && name.matches("[a-zA-Z0-9_\\-]+")
            && !name.contains("..");
    }

    private static final java.text.SimpleDateFormat ISO_FORMAT;
    static {
        ISO_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        ISO_FORMAT.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    }

    private String formatIso(long millis) {
        synchronized (ISO_FORMAT) {
            return ISO_FORMAT.format(new java.util.Date(millis));
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
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
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
