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
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;

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
                        sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "{\"success\":false,\"error\":\"Invalid email or password\"}");
                        return;
                    }

                    String storedHash = rs.getString("password");
                    boolean userActive = rs.getBoolean("is_active");
                    boolean companyActive = rs.getBoolean("company_active");

                    // Verify password (SHA-256 or plain text for testing)
                    if (!verifyPassword(password, storedHash)) {
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

                    // All checks passed - build user info
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

                    // Set up TransactionThreads for downstream JSP/servlet compatibility
                    String profileName = companyName + ":" + userEmail;
                    String defaultConfig = "<SF2QBConfiguration></SF2QBConfiguration>";
                    setupTransactionThread(ConfigContext.getCompanyRegistration(), profileName, defaultConfig);
                    setupTransactionThread(ConfigContext.getUpdateCompany(), profileName, defaultConfig);
                    setupTransactionThread(ConfigContext.getRequestCompany(), profileName, defaultConfig);

                    log("API login successful: " + userEmail + " (Company: " + companyName + ")");

                    // Build JSON response matching React User type
                    StringBuilder json = new StringBuilder();
                    json.append("{\"success\":true,\"user\":{");
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
        TransactionThread tt = ctx.addTransactionThread(profileName);
        if (tt != null) {
            tt.putParameter("configuration", config);
            tt.setCompanyConfiguration(config);
        }
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
