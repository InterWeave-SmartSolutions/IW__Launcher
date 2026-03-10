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
 * ApiChangePasswordServlet - JSON API endpoint for changing user passwords.
 *
 * Performs the same password change logic as LocalChangePasswordServlet but
 * accepts and returns JSON instead of form parameters and redirects. This
 * enables the React IW Portal to change passwords via fetch() while sharing
 * the same Tomcat session with classic JSP pages.
 *
 * Requires an authenticated session. The session email must match the
 * request email to prevent users from changing other users' passwords.
 *
 * API Endpoint: POST /api/auth/change-password
 *
 * Request Body:  {"email": "user@example.com", "oldPassword": "...",
 *                 "newPassword": "...", "confirmPassword": "..."}
 * Success (200): {"success": true, "message": "Password changed successfully"}
 * Error (400):   {"success": false, "error": "validation error message"}
 * Error (401):   {"success": false, "error": "Authentication required"}
 * Error (403):   {"success": false, "error": "Not authorized to change this password"}
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiChangePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiChangePasswordServlet initialized - using JNDI DataSource jdbc/IWDB");
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

        // Require authenticated session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authenticated") == null
                || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String sessionEmail = (String) session.getAttribute("userEmail");
        if (sessionEmail == null || sessionEmail.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        // Read and parse JSON body
        String body = readRequestBody(request);
        String email = extractJsonString(body, "email");
        String oldPassword = extractJsonString(body, "oldPassword");
        String newPassword = extractJsonString(body, "newPassword");
        String confirmPassword = extractJsonString(body, "confirmPassword");

        // Validate input
        if (email == null || email.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Email is required\"}");
            return;
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Current password is required\"}");
            return;
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"New password is required\"}");
            return;
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Password confirmation is required\"}");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"New passwords do not match\"}");
            return;
        }
        if (newPassword.length() < 4) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"New password must be at least 4 characters\"}");
            return;
        }

        // Verify session email matches request email (prevent changing other users' passwords)
        if (!sessionEmail.trim().equalsIgnoreCase(email.trim())) {
            sendJson(response, HttpServletResponse.SC_FORBIDDEN,
                "{\"success\":false,\"error\":\"Not authorized to change this password\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Verify the old password
            String storedHash = null;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT password FROM users WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, email.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"success\":false,\"error\":\"User not found\"}");
                        return;
                    }
                    storedHash = rs.getString("password");
                }
            }

            if (!verifyPassword(oldPassword, storedHash)) {
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"Current password is incorrect\"}");
                return;
            }

            // Update with new hashed password
            String newHash;
            try {
                newHash = hashPassword(newPassword);
            } catch (NoSuchAlgorithmException e) {
                log("SHA-256 algorithm not available", e);
                sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "{\"success\":false,\"error\":\"A system error occurred. Please try again later.\"}");
                return;
            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE users SET password = ?, updated_at = NOW() WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, newHash);
                stmt.setString(2, email.trim());
                stmt.executeUpdate();
            }

            // Audit: password change
            try {
                Integer uid = (Integer) session.getAttribute("userId");
                Integer cid = (Integer) session.getAttribute("companyId");
                AuditService.record(dataSource, uid, cid, email.trim(),
                    "password_change",
                    "User changed password via change-password API",
                    "user", uid != null ? String.valueOf(uid) : null,
                    request, null);
            } catch (Exception auditEx) {
                log("Audit logging failed for password change", auditEx);
            }

            log("Password changed via API for user: " + email.trim());
            sendJson(response, HttpServletResponse.SC_OK,
                "{\"success\":true,\"message\":\"Password changed successfully\"}");

        } catch (SQLException e) {
            log("Database error during API password change: " + e.getMessage(), e);
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

    // --- Helper Methods ---

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
     * Simple parser - does not handle nested objects or arrays.
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

    /**
     * Produces a lowercase hex SHA-256 hash of the given password.
     * Same algorithm as LocalUserManagementServlet.hashPassword().
     */
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
}
