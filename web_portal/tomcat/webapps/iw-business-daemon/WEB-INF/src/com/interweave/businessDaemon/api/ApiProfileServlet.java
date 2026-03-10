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
 * ApiProfileServlet - JSON API for user profile management.
 *
 * GET  /api/profile  - Load current user's profile (from authenticated session)
 * PUT  /api/profile  - Update profile fields (firstName, lastName, title)
 * POST /api/profile/password - Change user password
 *
 * All endpoints require an authenticated session (set by ApiLoginServlet).
 */
public class ApiProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiProfileServlet initialized");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

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

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = (String) session.getAttribute("userId");
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT u.id, u.email, u.first_name, u.last_name, u.title, u.role, " +
                    "c.company_name, c.solution_type " +
                    "FROM users u JOIN companies c ON u.company_id = c.id " +
                    "WHERE LOWER(u.email) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userEmail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String json = "{\"success\":true,\"profile\":{" +
                                "\"email\":\"" + escapeJson(rs.getString("email")) + "\"," +
                                "\"firstName\":\"" + escapeJson(rs.getString("first_name")) + "\"," +
                                "\"lastName\":\"" + escapeJson(rs.getString("last_name")) + "\"," +
                                "\"title\":\"" + escapeJson(rs.getString("title")) + "\"," +
                                "\"role\":\"" + escapeJson(rs.getString("role")) + "\"," +
                                "\"company\":\"" + escapeJson(rs.getString("company_name")) + "\"," +
                                "\"solutionType\":\"" + escapeJson(rs.getString("solution_type")) + "\"" +
                                "}}";
                        sendJson(response, 200, json);
                    } else {
                        sendJson(response, 404, "{\"success\":false,\"error\":\"Profile not found\"}");
                    }
                }
            }
        } catch (SQLException e) {
            log("Database error loading profile", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

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

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = (String) session.getAttribute("userId");
        }

        String body = readRequestBody(request);
        String firstName = extractJsonString(body, "firstName");
        String lastName = extractJsonString(body, "lastName");
        String title = extractJsonString(body, "title");

        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            sendJson(response, 400,
                "{\"success\":false,\"error\":\"First name and last name are required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, title = ?, " +
                    "updated_at = NOW() WHERE LOWER(email) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, firstName.trim());
                stmt.setString(2, lastName.trim());
                stmt.setString(3, title != null ? title.trim() : "");
                stmt.setString(4, userEmail);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    // Update session display name
                    session.setAttribute("userName", firstName.trim() + " " + lastName.trim());

                    // Audit: profile update
                    try {
                        Integer uid = (Integer) session.getAttribute("userId");
                        Integer cid = (Integer) session.getAttribute("companyId");
                        AuditService.record(dataSource, uid, cid, userEmail,
                            "profile_update",
                            "User updated profile (name: " + firstName.trim() + " " + lastName.trim() + ")",
                            "user", uid != null ? String.valueOf(uid) : null,
                            request, null);
                    } catch (Exception auditEx) {
                        log("Audit logging failed for profile update", auditEx);
                    }

                    log("ApiProfileServlet: Profile updated for " + userEmail);
                    sendJson(response, 200,
                        "{\"success\":true,\"message\":\"Profile updated successfully\"}");
                } else {
                    sendJson(response, 404,
                        "{\"success\":false,\"error\":\"User not found\"}");
                }
            }
        } catch (SQLException e) {
            log("Database error updating profile", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // POST is used for password change
        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = (String) session.getAttribute("userId");
        }

        String body = readRequestBody(request);
        String oldPassword = extractJsonString(body, "oldPassword");
        String newPassword = extractJsonString(body, "newPassword");
        String confirmPassword = extractJsonString(body, "confirmPassword");

        if (oldPassword == null || oldPassword.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            sendJson(response, 400,
                "{\"success\":false,\"error\":\"All password fields are required\"}");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            sendJson(response, 400,
                "{\"success\":false,\"error\":\"New passwords do not match\"}");
            return;
        }

        if (newPassword.length() < 4) {
            sendJson(response, 400,
                "{\"success\":false,\"error\":\"Password must be at least 4 characters\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Verify old password
            String sql = "SELECT password FROM users WHERE LOWER(email) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userEmail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        sendJson(response, 404,
                            "{\"success\":false,\"error\":\"User not found\"}");
                        return;
                    }
                    String storedHash = rs.getString("password");
                    if (!verifyPassword(oldPassword, storedHash)) {
                        sendJson(response, 401,
                            "{\"success\":false,\"error\":\"Current password is incorrect\"}");
                        return;
                    }
                }
            }

            // Update password
            String hashedNew = hashPassword(newPassword);
            String updateSql = "UPDATE users SET password = ?, updated_at = NOW() " +
                    "WHERE LOWER(email) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, hashedNew);
                stmt.setString(2, userEmail);
                stmt.executeUpdate();
            }

            // Audit: password change via profile page
            try {
                Integer uid = (Integer) session.getAttribute("userId");
                Integer cid = (Integer) session.getAttribute("companyId");
                AuditService.record(dataSource, uid, cid, userEmail,
                    "password_change",
                    "User changed password via profile page",
                    "user", uid != null ? String.valueOf(uid) : null,
                    request, null);
            } catch (Exception auditEx) {
                log("Audit logging failed for password change", auditEx);
            }

            log("ApiProfileServlet: Password changed for " + userEmail);
            sendJson(response, 200,
                "{\"success\":true,\"message\":\"Password changed successfully\"}");

        } catch (NoSuchAlgorithmException e) {
            log("Password hashing error", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"System error\"}");
        } catch (SQLException e) {
            log("Database error changing password", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Helpers ────────────────────────────────────────────────────────

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
