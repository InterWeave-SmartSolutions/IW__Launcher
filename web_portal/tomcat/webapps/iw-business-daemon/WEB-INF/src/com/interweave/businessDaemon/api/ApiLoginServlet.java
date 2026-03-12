package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.HostedTransactionBase;
import com.interweave.businessDaemon.QueryContext;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;
import com.interweave.businessDaemon.config.WorkspaceProfileCompiler;
import com.interweave.web.LoginRateLimiter;

/**
 * ApiLoginServlet - JSON API endpoint for user authentication.
 *
 * Performs the same authentication logic as LocalLoginServlet but accepts
 * and returns JSON instead of form parameters and redirects. This enables
 * the React IW Portal to authenticate via fetch() while sharing the same
 * Tomcat session with classic JSP pages.
 *
 * API Endpoint: POST /api/auth/login
 *
 * Request Body:  {"email": "user@example.com", "password": "secret"}
 * Success (200): {"success": true, "user": {...}}
 * Error (401):   {"success": false, "error": "Invalid email or password"}
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiLoginServlet initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            log("Failed to initialize DataSource", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read and parse JSON body
        String body = readRequestBody(request);
        String email = extractJsonString(body, "email");
        String password = extractJsonString(body, "password");

        // Validate input
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Email and password are required\"}");
            return;
        }

        email = email.trim().toLowerCase();

        // Check account lockout before hitting the database
        if (LoginRateLimiter.isLockedOut(email)) {
            long remaining = LoginRateLimiter.getRemainingLockoutSeconds(email);
            AuditService.record(dataSource, null, null, email,
                "login_locked", "Account temporarily locked due to too many failed attempts",
                "user", null, request, null);
            sendJson(response, 429,
                "{\"success\":false,\"error\":\"Too many failed attempts. Please try again in " +
                remaining + " seconds.\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Same authentication query as LocalLoginServlet
            String sql = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, " +
                         "u.role, u.is_active, u.company_id, c.company_name, " +
                         "c.solution_type, c.is_active as company_active " +
                         "FROM users u JOIN companies c ON u.company_id = c.id " +
                         "WHERE LOWER(u.email) = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        LoginRateLimiter.recordFailure(email);
                        AuditService.record(dataSource, null, null, email,
                            "login_failed", "Invalid email or password",
                            "user", null, request, null);
                        sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "{\"success\":false,\"error\":\"Invalid email or password\"}");
                        return;
                    }

                    String storedHash = rs.getString("password");
                    boolean userActive = rs.getBoolean("is_active");
                    boolean companyActive = rs.getBoolean("company_active");

                    // Verify password (SHA-256 or plain text for testing)
                    if (!verifyPassword(password, storedHash)) {
                        LoginRateLimiter.recordFailure(email);
                        AuditService.record(dataSource, rs.getInt("id"), rs.getInt("company_id"), email,
                            "login_failed", "Invalid password",
                            "user", String.valueOf(rs.getInt("id")), request, null);
                        sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "{\"success\":false,\"error\":\"Invalid email or password\"}");
                        return;
                    }

                    if (!companyActive) {
                        sendJson(response, HttpServletResponse.SC_FORBIDDEN,
                            "{\"success\":false,\"error\":\"Your company account is inactive\"}");
                        return;
                    }

                    if (!userActive) {
                        sendJson(response, HttpServletResponse.SC_FORBIDDEN,
                            "{\"success\":false,\"error\":\"Your user account is inactive\"}");
                        return;
                    }

                    // All checks passed - clear lockout counter
                    LoginRateLimiter.clearFailures(email);

                    // Build user info
                    int userId = rs.getInt("id");
                    String userEmail = rs.getString("email");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String role = rs.getString("role");
                    boolean isAdmin = "admin".equalsIgnoreCase(role);
                    int companyId = rs.getInt("company_id");
                    String companyName = rs.getString("company_name");
                    String solutionType = rs.getString("solution_type");
                    String userName = firstName + " " + lastName;

                    // Prevent session fixation: invalidate old session and create new one
                    HttpSession oldSession = request.getSession(false);
                    if (oldSession != null) {
                        oldSession.invalidate();
                    }
                    // Set session attributes (identical to LocalLoginServlet)
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", userId);
                    session.setAttribute("userEmail", userEmail);
                    session.setAttribute("userName", userName);
                    session.setAttribute("companyId", companyId);
                    session.setAttribute("companyName", companyName);
                    session.setAttribute("isAdmin", isAdmin);
                    session.setAttribute("role", role);
                    session.setAttribute("solutionType", solutionType);
                    session.setAttribute("authenticated", true);

                    // Update last login timestamp
                    updateLastLogin(conn, userId);

                    // Set ConfigContext flags (same as LocalLoginServlet)
                    ConfigContext.setHosted(true);
                    ConfigContext.setAdminLoggedIn(true);
                    ConfigContext.setLoggedUserType(isAdmin ? 'A' : 'U');

                    // Mirror the classic hosted runtime setup so the API login
                    // path keeps the JSP, servlet, and flow state in sync.
                    String profileName = companyName + ":" + userEmail;
                    String savedConfig = loadSavedConfig(conn, companyId, profileName);
                    String config = (savedConfig != null)
                        ? savedConfig
                        : "<SF2QBConfiguration></SF2QBConfiguration>";
                    bindHostedProfile(profileName, config);
                    if (savedConfig != null) {
                        try {
                            WorkspaceProfileCompiler.compileProfile(
                                getServletContext(), profileName, solutionType, savedConfig);
                        } catch (IOException ioe) {
                            log("API login succeeded but workspace compiler failed for " + profileName, ioe);
                        }
                    }

                    // Record successful login audit event
                    AuditService.record(dataSource, userId, companyId, userEmail,
                        "login", "User logged in successfully via API",
                        "user", String.valueOf(userId), request, null);

                    log("API login successful: " + userEmail + " (Company: " + companyName + ")");

                    // Generate a Bearer token for stateless auth (Vercel/proxy deployments)
                    java.util.Map<String, Object> tokenAttrs = new java.util.HashMap<>();
                    tokenAttrs.put("userId", userId);
                    tokenAttrs.put("userEmail", userEmail);
                    tokenAttrs.put("userName", userName);
                    tokenAttrs.put("companyId", companyId);
                    tokenAttrs.put("companyName", companyName);
                    tokenAttrs.put("isAdmin", isAdmin);
                    tokenAttrs.put("role", role);
                    tokenAttrs.put("solutionType", solutionType);
                    tokenAttrs.put("authenticated", true);
                    String authToken = ApiTokenStore.createToken(tokenAttrs);

                    // Build JSON response matching React User type
                    StringBuilder json = new StringBuilder();
                    json.append("{\"success\":true,\"token\":\"").append(authToken).append("\",\"user\":{");
                    json.append("\"userId\":\"").append(userId).append("\",");
                    json.append("\"userName\":\"").append(escapeJson(userName)).append("\",");
                    json.append("\"email\":\"").append(escapeJson(userEmail)).append("\",");
                    json.append("\"companyId\":").append(companyId).append(",");
                    json.append("\"companyName\":\"").append(escapeJson(companyName)).append("\",");
                    json.append("\"isAdmin\":").append(isAdmin).append(",");
                    json.append("\"role\":\"").append(escapeJson(role)).append("\",");
                    json.append("\"solutionType\":\"").append(escapeJson(solutionType != null ? solutionType : "")).append("\"");
                    json.append("}}");

                    sendJson(response, HttpServletResponse.SC_OK, json.toString());
                }
            }

        } catch (SQLException e) {
            log("Database error during API login: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred. Please try again later.\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Helper Methods ─────────────────────────────────────────────────

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

    /**
     * Extracts a string value from a flat JSON object by key name.
     * Simple parser — does not handle nested objects or arrays.
     */
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

    /**
     * Verifies password against stored hash.
     * Supports both plain text (for testing) and SHA-256 hashed passwords.
     * Identical logic to LocalLoginServlet.verifyPassword().
     */
    private boolean verifyPassword(String password, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) return false;
        if (password.equals(storedHash)) return true;
        try {
            return hashPassword(password).equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            log("Error hashing password", e);
            return false;
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }
        return hex.toString();
    }

    private void updateLastLogin(Connection conn, int userId) {
        String sql = "UPDATE users SET last_login = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            log("Failed to update last login for user " + userId, e);
        }
    }

    private void setupTransactionThread(TransactionContext ctx, String profileName, String config) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(ctx, profileName);
        if (tt == null) {
            tt = ctx.addTransactionThread(profileName);
        }
        if (tt != null) {
            tt.putParameter("configuration", sanitizeConfig(config));
            tt.setCompanyConfiguration(sanitizeFullConfig(config));
            applyEndpointMode(tt);
        }
    }

    private void setupQueryInstance(QueryContext ctx, String profileName) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        if (ConfigContext.getQueryInstanceByProfileName(ctx, profileName) == null) {
            ctx.addQueryInstance(profileName);
        }
    }

    private void bindHostedProfile(String profileName, String config) {
        applyRuntimeEndpointMode();

        if (!ConfigContext.getMonitorsStarted().contains(profileName)) {
            ConfigContext.getMonitorsStarted().add(profileName);
        }
        if (!ConfigContext.getProfileDescriptors().containsKey(profileName)) {
            ConfigContext.getProfileDescriptors().put(profileName,
                new ConfigContext.ProfileDescriptor());
        }

        setupTransactionThread(ConfigContext.getCompanyRegistration(), profileName, config);
        setupTransactionThread(ConfigContext.getUpdateCompany(), profileName, config);
        setupTransactionThread(ConfigContext.getRequestCompany(), profileName, config);

        for (TransactionContext ctx : ConfigContext.getTransactionList()) {
            setupTransactionThread(ctx, profileName, config);
        }
        for (QueryContext ctx : ConfigContext.getQueryList()) {
            setupQueryInstance(ctx, profileName);
        }
    }

    private void applyRuntimeEndpointMode() {
        ConfigContext.setMyGlobalIP(resolveTransformationServerHost());
        ConfigContext.setPrimaryTransformationServerURL(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL()));
        ConfigContext.setPrimaryTransformationServerURLT(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT()));
        ConfigContext.setPrimaryTransformationServerURL1(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL1()));
        ConfigContext.setPrimaryTransformationServerURLT1(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT1()));
        ConfigContext.setPrimaryTransformationServerURLD(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLD()));
        ConfigContext.setSecondaryTransformationServerURL(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL()));
        ConfigContext.setSecondaryTransformationServerURLT(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT()));
        ConfigContext.setSecondaryTransformationServerURL1(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL1()));
        ConfigContext.setSecondaryTransformationServerURLT1(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT1()));
        ConfigContext.setSecondaryTransformationServerURLD(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(HostedTransactionBase runtimeItem) {
        runtimeItem.setPrimaryTransformationServerURL(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL()));
        runtimeItem.setPrimaryTransformationServerURLT(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT()));
        runtimeItem.setPrimaryTransformationServerURL1(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL1()));
        runtimeItem.setPrimaryTransformationServerURLT1(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT1()));
        runtimeItem.setPrimaryTransformationServerURLD(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLD()));
        runtimeItem.setSecondaryTransformationServerURL(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL()));
        runtimeItem.setSecondaryTransformationServerURLT(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT()));
        runtimeItem.setSecondaryTransformationServerURL1(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL1()));
        runtimeItem.setSecondaryTransformationServerURLT1(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT1()));
        runtimeItem.setSecondaryTransformationServerURLD(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(TransactionThread thread) {
        thread.setPrimaryDedicatedURL(rewriteRuntimeUrl(thread.getPrimaryDedicatedURL()));
        thread.setPrimaryDedicatedURLc(rewriteRuntimeUrl(thread.getPrimaryDedicatedURLc()));
        thread.setSecondaryDedicatedURL(rewriteRuntimeUrl(thread.getSecondaryDedicatedURL()));
        thread.setSecondaryDedicatedURLc(rewriteRuntimeUrl(thread.getSecondaryDedicatedURLc()));
    }

    private static String resolveTransformationServerBase() {
        if (isLegacyTsMode()) {
            return envOrDefault("TS_BASE_LEGACY",
                "http://iw0.interweave.biz:9090/iwtransformationserver");
        }
        return envOrDefault("TS_BASE_LOCAL",
            "http://localhost:9090/iwtransformationserver");
    }

    private static String resolveTransformationServerOrigin() {
        String base = resolveTransformationServerBase();
        int marker = base.indexOf("/iwtransformationserver");
        if (marker >= 0) {
            return base.substring(0, marker);
        }
        int scheme = base.indexOf("://");
        if (scheme < 0) {
            return base;
        }
        int slash = base.indexOf('/', scheme + 3);
        return (slash >= 0) ? base.substring(0, slash) : base;
    }

    private static String resolveTransformationServerHost() {
        String origin = resolveTransformationServerOrigin();
        int scheme = origin.indexOf("://");
        if (scheme >= 0) {
            origin = origin.substring(scheme + 3);
        }
        int slash = origin.indexOf('/');
        if (slash >= 0) {
            origin = origin.substring(0, slash);
        }
        int colon = origin.lastIndexOf(':');
        if (colon > -1) {
            return origin.substring(0, colon);
        }
        return origin;
    }

    private static String rewriteRuntimeUrl(String url) {
        if (url == null) return "";
        String value = url.trim();
        if (value.isEmpty() || "null".equalsIgnoreCase(value)) {
            return value;
        }

        String origin = resolveTransformationServerOrigin();
        String fallback = resolveTransformationServerBase();
        int marker = value.indexOf("/iwtransformationserver");
        if (marker >= 0) {
            return origin + value.substring(marker);
        }

        if (value.startsWith("http://") || value.startsWith("https://")) {
            int scheme = value.indexOf("://");
            int slash = value.indexOf('/', scheme + 3);
            if (slash >= 0) {
                return origin + value.substring(slash);
            }
            return fallback;
        }

        return fallback;
    }

    private static boolean isLegacyTsMode() {
        String mode = System.getenv("TS_MODE");
        return mode != null && "legacy".equalsIgnoreCase(mode.trim());
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            value = fallback;
        }
        value = value.trim();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static final String CONFIG_CLOSE_TAG = "</SF2QBConfiguration>";

    private static String sanitizeConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "");
    }

    private static String sanitizeFullConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration></SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "") + CONFIG_CLOSE_TAG;
    }

    private String loadSavedConfig(Connection conn, int companyId, String profileName) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT configuration_xml FROM company_configurations " +
                "WHERE company_id = ? AND profile_name = ?")) {
            stmt.setInt(1, companyId);
            stmt.setString(2, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String xml = rs.getString("configuration_xml");
                    if (xml != null && !xml.isEmpty()) {
                        return xml;
                    }
                }
            }
        } catch (SQLException e) {
            log("Could not load saved configuration for " + profileName, e);
        }
        return null;
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendJson(HttpServletResponse response, int statusCode, String json) throws IOException {
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
