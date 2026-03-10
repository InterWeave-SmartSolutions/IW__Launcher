package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * ApiConfigurationServlet - JSON API for company configuration management.
 *
 * Routes (dispatched by pathInfo):
 *   GET  /api/config/wizard      - Load current wizard configuration (parsed from XML)
 *   PUT  /api/config/wizard      - Save wizard configuration (serialized to XML)
 *   GET  /api/config/credentials - Load company credentials
 *   PUT  /api/config/credentials - Save/update a company credential
 *
 * All endpoints require an authenticated admin session.
 */
public class ApiConfigurationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            ensureTableExists();
            log("ApiConfigurationServlet initialized");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    /** Create company_configurations table if it doesn't exist. */
    private void ensureTableExists() {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS company_configurations (" +
                    "id SERIAL PRIMARY KEY, " +
                    "company_id INTEGER NOT NULL REFERENCES companies(id) ON DELETE CASCADE, " +
                    "profile_name VARCHAR(255) NOT NULL, " +
                    "solution_type VARCHAR(50), " +
                    "configuration_xml TEXT, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "UNIQUE (company_id, profile_name))")) {
                stmt.executeUpdate();
                log("company_configurations table ensured");
            }
        } catch (SQLException e) {
            log("Could not ensure company_configurations table (may already exist)", e);
        }
    }

    // ─── GET ────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        Object companyIdObj = session.getAttribute("companyId");
        if (companyIdObj == null) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"No company associated\"}");
            return;
        }
        int companyId = (Integer) companyIdObj;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        if (pathInfo.startsWith("/wizard")) {
            handleGetWizard(response, companyId);
        } else if (pathInfo.startsWith("/credentials")) {
            handleGetCredentials(response, companyId);
        } else if (pathInfo.startsWith("/profiles")) {
            handleGetProfiles(response, companyId);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    private void handleGetWizard(HttpServletResponse response, int companyId) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            // Get company solution type
            String solutionType = "QB";
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT solution_type FROM companies WHERE id = ?")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String st = rs.getString("solution_type");
                        if (st != null && !st.isEmpty()) solutionType = st;
                    }
                }
            }

            // Get latest configuration
            String configXml = null;
            String profileName = null;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT profile_name, solution_type, configuration_xml " +
                    "FROM company_configurations WHERE company_id = ? " +
                    "ORDER BY updated_at DESC LIMIT 1")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        profileName = rs.getString("profile_name");
                        String st = rs.getString("solution_type");
                        if (st != null && !st.isEmpty()) solutionType = st;
                        configXml = rs.getString("configuration_xml");
                    }
                }
            }

            // Parse XML to JSON key-value pairs
            StringBuilder syncFields = new StringBuilder();
            if (configXml != null && !configXml.isEmpty()) {
                syncFields.append(parseXmlToJsonFields(configXml));
            }

            String json = "{\"success\":true,\"data\":{" +
                    "\"solutionType\":\"" + escapeJson(solutionType) + "\"," +
                    "\"profileName\":" + (profileName != null ? "\"" + escapeJson(profileName) + "\"" : "null") + "," +
                    "\"hasConfiguration\":" + (configXml != null) + "," +
                    "\"syncMappings\":{" + syncFields.toString() + "}" +
                    "}}";
            sendJson(response, 200, json);

        } catch (SQLException e) {
            log("Database error loading wizard config", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleGetCredentials(HttpServletResponse response, int companyId) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            StringBuilder creds = new StringBuilder("[");
            boolean first = true;

            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, credential_type, credential_name, username, " +
                    "endpoint_url, api_key, extra_config, is_active " +
                    "FROM company_credentials WHERE company_id = ? ORDER BY credential_type")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        if (!first) creds.append(",");
                        first = false;
                        creds.append("{");
                        creds.append("\"id\":").append(rs.getInt("id")).append(",");
                        creds.append("\"credentialType\":\"").append(escapeJson(rs.getString("credential_type"))).append("\",");
                        creds.append("\"credentialName\":\"").append(escapeJson(rs.getString("credential_name"))).append("\",");
                        creds.append("\"username\":\"").append(escapeJson(rs.getString("username"))).append("\",");
                        creds.append("\"endpointUrl\":\"").append(escapeJson(rs.getString("endpoint_url"))).append("\",");
                        creds.append("\"hasApiKey\":").append(rs.getString("api_key") != null && !rs.getString("api_key").isEmpty()).append(",");
                        creds.append("\"isActive\":").append(rs.getBoolean("is_active"));
                        creds.append("}");
                    }
                }
            }
            creds.append("]");

            // Also get profile credentials (SF/QB/CRM fields from profiles table)
            String profileCreds = "{}";
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT p.sf_username, p.sf_url, p.qb_company_file, p.qb_username, " +
                    "p.crm_url, p.crm_username " +
                    "FROM profiles p JOIN users u ON p.user_id = u.id " +
                    "WHERE u.company_id = ? AND u.role = 'admin' LIMIT 1")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        profileCreds = "{" +
                                "\"sfUsername\":\"" + escapeJson(rs.getString("sf_username")) + "\"," +
                                "\"sfUrl\":\"" + escapeJson(rs.getString("sf_url")) + "\"," +
                                "\"qbCompanyFile\":\"" + escapeJson(rs.getString("qb_company_file")) + "\"," +
                                "\"qbUsername\":\"" + escapeJson(rs.getString("qb_username")) + "\"," +
                                "\"crmUrl\":\"" + escapeJson(rs.getString("crm_url")) + "\"," +
                                "\"crmUsername\":\"" + escapeJson(rs.getString("crm_username")) + "\"" +
                                "}";
                    }
                }
            }

            String json = "{\"success\":true,\"data\":{" +
                    "\"credentials\":" + creds.toString() + "," +
                    "\"profileCredentials\":" + profileCreds +
                    "}}";
            sendJson(response, 200, json);

        } catch (SQLException e) {
            log("Database error loading credentials", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── PUT ────────────────────────────────────────────────────────────

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (!Boolean.TRUE.equals(isAdmin)) {
            sendJson(response, 403, "{\"success\":false,\"error\":\"Admin access required\"}");
            return;
        }

        Object companyIdObj = session.getAttribute("companyId");
        if (companyIdObj == null) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"No company associated\"}");
            return;
        }
        int companyId = (Integer) companyIdObj;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";
        String body = readRequestBody(request);

        if (pathInfo.startsWith("/wizard")) {
            handlePutWizard(request, response, companyId, body, session);
        } else if (pathInfo.startsWith("/credentials")) {
            handlePutCredentials(request, response, companyId, body, session);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    private void handlePutWizard(HttpServletRequest request, HttpServletResponse response,
            int companyId, String body, HttpSession session) throws IOException {
        String solutionType = extractJsonString(body, "solutionType");
        if (solutionType == null || solutionType.trim().isEmpty()) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"solutionType is required\"}");
            return;
        }

        // Build configuration XML from syncMappings JSON object
        StringBuilder xml = new StringBuilder("<SF2QBConfiguration>");

        // Extract all sync fields from the body
        // The body format is: {"solutionType":"SF2QB1","syncMappings":{"SyncTypeAC":"SF2QB",...}}
        String mappingsStr = extractJsonObject(body, "syncMappings");
        if (mappingsStr != null) {
            // Parse JSON object respecting quoted strings (handles commas inside values)
            parseJsonObjectEntries(mappingsStr, (key, val) -> {
                if (!key.isEmpty()) {
                    xml.append("<").append(key).append(">")
                       .append(xmlEscape(val))
                       .append("</").append(key).append(">");
                }
            });
        }
        xml.append("</SF2QBConfiguration>");

        // Build profile name from session
        String userName = (String) session.getAttribute("userEmail");
        String companyName = "";
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT company_name FROM companies WHERE id = ?")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) companyName = rs.getString("company_name");
                }
            }
        } catch (SQLException e) {
            log("Could not look up company name", e);
        }

        String profileName = companyName + ":" + (userName != null ? userName : "admin");

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Update company solution type
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE companies SET solution_type = ?, updated_at = NOW() WHERE id = ?")) {
                    stmt.setString(1, solutionType.trim());
                    stmt.setInt(2, companyId);
                    stmt.executeUpdate();
                }

                // Upsert configuration
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (company_id, profile_name) DO UPDATE SET " +
                        "solution_type = EXCLUDED.solution_type, " +
                        "configuration_xml = EXCLUDED.configuration_xml, " +
                        "updated_at = NOW()")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType.trim());
                    stmt.setString(4, xml.toString());
                    stmt.executeUpdate();
                }

                conn.commit();

                // Update session
                session.setAttribute("solutionType", solutionType.trim());

                // Audit: configuration wizard change
                try {
                    Integer uid = (Integer) session.getAttribute("userId");
                    AuditService.record(dataSource, uid, companyId, userName,
                        "config_change",
                        "Configuration wizard saved (solution type: " + solutionType.trim() +
                            ", profile: " + profileName + ")",
                        "company_configuration", String.valueOf(companyId),
                        request, null);
                } catch (Exception auditEx) {
                    // AuditService.record already swallows exceptions, but guard outer code too
                    System.err.println("[ApiConfigurationServlet] Audit logging failed: " + auditEx.getMessage());
                }

                log("Configuration wizard saved for company " + companyId +
                    ", solution=" + solutionType + ", xml=" + xml.length() + " chars");
                sendJson(response, 200,
                    "{\"success\":true,\"message\":\"Configuration saved successfully\"}");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            log("Database error saving wizard config", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handlePutCredentials(HttpServletRequest request, HttpServletResponse response,
            int companyId, String body, HttpSession session) throws IOException {
        String credentialType = extractJsonString(body, "credentialType");
        if (credentialType == null || credentialType.trim().isEmpty()) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"credentialType is required\"}");
            return;
        }

        String credentialName = extractJsonString(body, "credentialName");
        String username = extractJsonString(body, "username");
        String password = extractJsonString(body, "password");
        String apiKey = extractJsonString(body, "apiKey");
        String apiSecret = extractJsonString(body, "apiSecret");
        String endpointUrl = extractJsonString(body, "endpointUrl");
        String extraConfig = extractJsonString(body, "extraConfig");

        try (Connection conn = dataSource.getConnection()) {
            // Upsert credential
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO company_credentials " +
                    "(company_id, credential_type, credential_name, username, password, " +
                    "api_key, api_secret, endpoint_url, extra_config, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE) " +
                    "ON CONFLICT (company_id, credential_type) DO UPDATE SET " +
                    "credential_name = EXCLUDED.credential_name, " +
                    "username = EXCLUDED.username, " +
                    "password = CASE WHEN EXCLUDED.password IS NOT NULL AND EXCLUDED.password != '' " +
                    "  THEN EXCLUDED.password ELSE company_credentials.password END, " +
                    "api_key = CASE WHEN EXCLUDED.api_key IS NOT NULL AND EXCLUDED.api_key != '' " +
                    "  THEN EXCLUDED.api_key ELSE company_credentials.api_key END, " +
                    "api_secret = CASE WHEN EXCLUDED.api_secret IS NOT NULL AND EXCLUDED.api_secret != '' " +
                    "  THEN EXCLUDED.api_secret ELSE company_credentials.api_secret END, " +
                    "endpoint_url = EXCLUDED.endpoint_url, " +
                    "extra_config = EXCLUDED.extra_config, " +
                    "is_active = TRUE, " +
                    "updated_at = NOW()")) {
                stmt.setInt(1, companyId);
                stmt.setString(2, credentialType.trim());
                stmt.setString(3, credentialName != null ? credentialName.trim() : credentialType.trim());
                stmt.setString(4, username != null ? username.trim() : "");
                stmt.setString(5, password != null ? password.trim() : "");
                stmt.setString(6, apiKey != null ? apiKey.trim() : "");
                stmt.setString(7, apiSecret != null ? apiSecret.trim() : "");
                stmt.setString(8, endpointUrl != null ? endpointUrl.trim() : "");
                stmt.setString(9, extraConfig != null ? extraConfig.trim() : "");
                stmt.executeUpdate();
            }

            // Audit: credential change
            try {
                Integer uid = (Integer) session.getAttribute("userId");
                String userEmail = (String) session.getAttribute("userEmail");
                AuditService.record(dataSource, uid, companyId, userEmail,
                    "config_change",
                    "Credential saved (type: " + credentialType.trim() + ")",
                    "company_credential", String.valueOf(companyId),
                    request, null);
            } catch (Exception auditEx) {
                System.err.println("[ApiConfigurationServlet] Audit logging failed: " + auditEx.getMessage());
            }

            log("Credential saved: type=" + credentialType + " for company " + companyId);
            sendJson(response, 200,
                "{\"success\":true,\"message\":\"Credential saved successfully\"}");

        } catch (SQLException e) {
            log("Database error saving credential", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleGetProfiles(HttpServletResponse response, int companyId) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            StringBuilder profiles = new StringBuilder("[");
            boolean first = true;

            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT profile_name, solution_type, updated_at " +
                    "FROM company_configurations WHERE company_id = ? ORDER BY updated_at DESC")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        if (!first) profiles.append(",");
                        first = false;
                        profiles.append("{");
                        profiles.append("\"profileName\":\"").append(escapeJson(rs.getString("profile_name"))).append("\",");
                        profiles.append("\"solutionType\":\"").append(escapeJson(rs.getString("solution_type"))).append("\",");
                        profiles.append("\"updatedAt\":\"").append(escapeJson(rs.getString("updated_at"))).append("\"");
                        profiles.append("}");
                    }
                }
            }
            profiles.append("]");

            sendJson(response, 200, "{\"success\":true,\"data\":{\"profiles\":" + profiles.toString() + "}}");
        } catch (SQLException e) {
            log("Database error loading profiles", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── POST ────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        if (pathInfo.startsWith("/credentials/test")) {
            String body = readRequestBody(request);
            handleTestCredential(response, body);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    private void handleTestCredential(HttpServletResponse response, String body) throws IOException {
        String credentialType = extractJsonString(body, "credentialType");
        String endpointUrl = extractJsonString(body, "endpointUrl");

        if (endpointUrl == null || endpointUrl.trim().isEmpty()) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"endpointUrl is required\"}");
            return;
        }

        long startTime = System.currentTimeMillis();
        boolean reachable = false;
        int statusCode = 0;
        String message = "";

        try {
            java.net.URL url = new java.net.URL(endpointUrl.trim());
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setInstanceFollowRedirects(true);
            statusCode = conn.getResponseCode();
            reachable = (statusCode >= 200 && statusCode < 500);
            message = reachable ? "Endpoint reachable (HTTP " + statusCode + ")"
                                : "Endpoint returned HTTP " + statusCode;
            conn.disconnect();
        } catch (java.net.MalformedURLException e) {
            message = "Invalid URL format";
        } catch (java.net.SocketTimeoutException e) {
            message = "Connection timed out after 10 seconds";
        } catch (java.io.IOException e) {
            message = "Connection failed: " + e.getMessage();
        }

        long elapsed = System.currentTimeMillis() - startTime;

        String json = "{\"success\":true," +
                "\"reachable\":" + reachable + "," +
                "\"statusCode\":" + statusCode + "," +
                "\"responseTimeMs\":" + elapsed + "," +
                "\"message\":\"" + escapeJson(message) + "\"}";
        sendJson(response, 200, json);
    }

    // ─── OPTIONS (CORS) ─────────────────────────────────────────────────

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── XML Parsing Helpers ────────────────────────────────────────────

    /** Parse XML config tags into JSON field string: "key1":"val1","key2":"val2" */
    private String parseXmlToJsonFields(String xml) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        // Remove root tags
        String content = xml;
        int rootStart = content.indexOf("<SF2QBConfiguration>");
        if (rootStart >= 0) content = content.substring(rootStart + "<SF2QBConfiguration>".length());
        int rootEnd = content.indexOf("</SF2QBConfiguration>");
        if (rootEnd >= 0) content = content.substring(0, rootEnd);

        int pos = 0;
        while (pos < content.length()) {
            int tagStart = content.indexOf('<', pos);
            if (tagStart < 0) break;
            if (content.charAt(tagStart + 1) == '/') {
                // Skip closing tags without matching open
                int tagEnd = content.indexOf('>', tagStart);
                pos = tagEnd >= 0 ? tagEnd + 1 : content.length();
                continue;
            }

            int tagEnd = content.indexOf('>', tagStart);
            if (tagEnd < 0) break;

            String tagName = content.substring(tagStart + 1, tagEnd);
            String closeTag = "</" + tagName + ">";
            int closePos = content.indexOf(closeTag, tagEnd + 1);

            if (closePos >= 0) {
                String value = content.substring(tagEnd + 1, closePos);

                if (!first) result.append(",");
                first = false;
                result.append("\"").append(escapeJson(tagName)).append("\":\"")
                      .append(escapeJson(xmlUnescape(value))).append("\"");

                pos = closePos + closeTag.length();
            } else {
                pos = tagEnd + 1;
            }
        }

        return result.toString();
    }

    private String xmlUnescape(String s) {
        if (s == null) return "";
        return s.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&apos;", "'");
    }

    private String xmlEscape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /** Extract a JSON object value as a raw string: {"key":{...}} -> {...} */
    private String extractJsonObject(String json, String key) {
        if (json == null || key == null) return null;
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex < 0) return null;
        int colonIndex = json.indexOf(':', keyIndex + searchKey.length());
        if (colonIndex < 0) return null;

        // Find opening brace
        int start = json.indexOf('{', colonIndex + 1);
        if (start < 0) return null;

        // Find matching closing brace
        int depth = 1;
        int pos = start + 1;
        while (pos < json.length() && depth > 0) {
            char c = json.charAt(pos);
            if (c == '{') depth++;
            else if (c == '}') depth--;
            else if (c == '"') {
                // Skip string content
                pos++;
                while (pos < json.length() && json.charAt(pos) != '"') {
                    if (json.charAt(pos) == '\\') pos++;
                    pos++;
                }
            }
            pos++;
        }

        if (depth == 0) {
            return json.substring(start, pos);
        }
        return null;
    }

    // ─── Common Helpers ─────────────────────────────────────────────────

    /** Functional interface for JSON key-value pair processing. */
    private interface JsonEntryHandler {
        void handle(String key, String value);
    }

    /**
     * Parse a JSON object string into key-value pairs, correctly handling
     * commas inside quoted values (e.g. email lists like "a@b.com,c@d.com").
     */
    private void parseJsonObjectEntries(String json, JsonEntryHandler handler) {
        if (json == null || json.isEmpty()) return;
        int len = json.length();
        int pos = 0;
        // Skip opening brace
        while (pos < len && json.charAt(pos) != '{') pos++;
        if (pos < len) pos++;

        while (pos < len) {
            // Skip whitespace
            while (pos < len && Character.isWhitespace(json.charAt(pos))) pos++;
            if (pos >= len || json.charAt(pos) == '}') break;

            // Parse key (must be quoted string)
            if (json.charAt(pos) != '"') { pos++; continue; }
            pos++; // skip opening quote
            int keyStart = pos;
            while (pos < len && json.charAt(pos) != '"') {
                if (json.charAt(pos) == '\\') pos++;
                pos++;
            }
            String key = json.substring(keyStart, pos);
            if (pos < len) pos++; // skip closing quote

            // Skip colon
            while (pos < len && json.charAt(pos) != ':') pos++;
            if (pos < len) pos++;

            // Skip whitespace
            while (pos < len && Character.isWhitespace(json.charAt(pos))) pos++;

            // Parse value (quoted string)
            String val = "";
            if (pos < len && json.charAt(pos) == '"') {
                pos++; // skip opening quote
                int valStart = pos;
                while (pos < len && json.charAt(pos) != '"') {
                    if (json.charAt(pos) == '\\') pos++;
                    pos++;
                }
                val = json.substring(valStart, pos);
                // Unescape basic sequences
                val = val.replace("\\\"", "\"").replace("\\\\", "\\");
                if (pos < len) pos++; // skip closing quote
            } else if (pos < len) {
                // Non-string value (number, boolean, null)
                int valStart = pos;
                while (pos < len && json.charAt(pos) != ',' && json.charAt(pos) != '}') pos++;
                val = json.substring(valStart, pos).trim();
            }

            handler.handle(key, val);

            // Skip comma
            while (pos < len && json.charAt(pos) != ',' && json.charAt(pos) != '}') pos++;
            if (pos < len && json.charAt(pos) == ',') pos++;
        }
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
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
        return json.substring(start, end);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendJson(HttpServletResponse response, int statusCode, String json)
            throws IOException {
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
