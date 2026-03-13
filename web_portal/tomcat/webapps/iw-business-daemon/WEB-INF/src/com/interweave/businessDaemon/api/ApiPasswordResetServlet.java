package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
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
import javax.sql.DataSource;

import com.interweave.web.PasswordHasher;

/**
 * ApiPasswordResetServlet - JSON API endpoint for self-service password reset.
 *
 * Supports three actions via POST with a JSON "action" field:
 *   - request  : Generate a reset token and log it (no email yet)
 *   - validate : Check if a token is still valid
 *   - reset    : Consume the token and set a new password
 *
 * API Endpoint: POST /api/auth/password-reset
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiPasswordResetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // Token validity: 1 hour
    private static final long TOKEN_EXPIRY_MS = 60 * 60 * 1000L;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiPasswordResetServlet initialized - using JNDI DataSource jdbc/IWDB");
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

        String body = readRequestBody(request);
        String action = extractJsonString(body, "action");

        if (action == null || action.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Action is required\"}");
            return;
        }

        switch (action.trim().toLowerCase()) {
            case "request":
                handleRequest(body, response);
                break;
            case "validate":
                handleValidate(body, response);
                break;
            case "reset":
                handleReset(body, response);
                break;
            default:
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"Unknown action: " + escapeJson(action) + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Action Handlers ─────────────────────────────────────────────────

    /**
     * action=request: Generate a reset token for the given email.
     * Always returns success to prevent email enumeration.
     */
    private void handleRequest(String body, HttpServletResponse response) throws IOException {
        String email = extractJsonString(body, "email");

        if (email == null || email.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Email is required\"}");
            return;
        }

        email = email.trim().toLowerCase();

        try (Connection conn = dataSource.getConnection()) {
            // Look up user by email
            String sql = "SELECT id FROM users WHERE LOWER(email) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");

                        // Generate 48-byte hex token
                        byte[] tokenBytes = new byte[48];
                        SECURE_RANDOM.nextBytes(tokenBytes);
                        String token = bytesToHex(tokenBytes);

                        Timestamp expiresAt = new Timestamp(
                            System.currentTimeMillis() + TOKEN_EXPIRY_MS);

                        // Store token
                        String insertSql = "INSERT INTO password_reset_tokens " +
                            "(user_id, token, expires_at) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setString(2, token);
                            insertStmt.setTimestamp(3, expiresAt);
                            insertStmt.executeUpdate();
                        }

                        // Log token to stdout (no email sending for now)
                        log("PASSWORD RESET TOKEN for " + email + ": " + token +
                            " (expires: " + expiresAt + ")");
                    }
                    // If user not found, we still return success (prevent enumeration)
                }
            }
        } catch (SQLException e) {
            log("Database error during password reset request: " + e.getMessage(), e);
            // Still return success to prevent enumeration
        }

        sendJson(response, HttpServletResponse.SC_OK,
            "{\"success\":true,\"message\":\"If that email exists, a reset link has been generated. Check server logs.\"}");
    }

    /**
     * action=validate: Check if a token is valid (not expired, not used).
     */
    private void handleValidate(String body, HttpServletResponse response) throws IOException {
        String token = extractJsonString(body, "token");

        if (token == null || token.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Token is required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            boolean valid = isTokenValid(conn, token.trim());
            sendJson(response, HttpServletResponse.SC_OK,
                "{\"success\":true,\"valid\":" + valid + "}");
        } catch (SQLException e) {
            log("Database error during token validation: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    /**
     * action=reset: Consume the token and set a new password.
     */
    private void handleReset(String body, HttpServletResponse response) throws IOException {
        String token = extractJsonString(body, "token");
        String newPassword = extractJsonString(body, "newPassword");
        String confirmPassword = extractJsonString(body, "confirmPassword");

        if (token == null || token.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Token is required\"}");
            return;
        }
        if (newPassword == null || newPassword.isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"New password is required\"}");
            return;
        }
        if (newPassword.length() < 4) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Password must be at least 4 characters\"}");
            return;
        }
        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Passwords do not match\"}");
            return;
        }

        token = token.trim();

        try (Connection conn = dataSource.getConnection()) {
            // Validate token and get user_id
            String sql = "SELECT id, user_id FROM password_reset_tokens " +
                "WHERE token = ? AND used_at IS NULL AND expires_at > NOW()";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"success\":false,\"error\":\"Invalid or expired token\"}");
                        return;
                    }

                    int tokenId = rs.getInt("id");
                    int userId = rs.getInt("user_id");

                    // Hash new password (bcrypt via PasswordHasher)
                    String hashedPassword = PasswordHasher.hash(newPassword);

                    // Update user password
                    String updateSql = "UPDATE users SET password = ?, updated_at = NOW() WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setInt(2, userId);
                        updateStmt.executeUpdate();
                    }

                    // Mark token as used
                    String useSql = "UPDATE password_reset_tokens SET used_at = NOW() WHERE id = ?";
                    try (PreparedStatement useStmt = conn.prepareStatement(useSql)) {
                        useStmt.setInt(1, tokenId);
                        useStmt.executeUpdate();
                    }

                    log("Password reset successful for user_id=" + userId);
                }
            }

            sendJson(response, HttpServletResponse.SC_OK,
                "{\"success\":true,\"message\":\"Password has been reset successfully\"}");

        } catch (SQLException e) {
            log("Database error during password reset: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    // ─── Helper Methods ─────────────────────────────────────────────────

    private boolean isTokenValid(Connection conn, String token) throws SQLException {
        String sql = "SELECT id FROM password_reset_tokens " +
            "WHERE token = ? AND used_at IS NULL AND expires_at > NOW()";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }
        return hex.toString();
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
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
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
