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

import com.interweave.web.PasswordHasher;

/**
 * ApiCompanyProfileServlet - JSON API for company profile management.
 *
 * GET  /api/company/profile  - Load company profile (from authenticated session)
 * PUT  /api/company/profile  - Update company admin name and solution type
 * POST /api/company/profile  - Change company password
 *
 * All endpoints require an authenticated admin session.
 */
public class ApiCompanyProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiCompanyProfileServlet initialized");
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

        Object companyIdObj = session.getAttribute("companyId");
        if (companyIdObj == null) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"No company associated\"}");
            return;
        }
        int companyId = (Integer) companyIdObj;

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT c.id, c.company_name, c.email AS company_email, " +
                    "c.solution_type, c.is_active, c.license_key, c.license_expiry, " +
                    "u.first_name, u.last_name, u.email AS admin_email, u.title, u.role " +
                    "FROM companies c " +
                    "LEFT JOIN users u ON u.company_id = c.id AND u.role = 'admin' " +
                    "WHERE c.id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String licenseKey = rs.getString("license_key");
                        String licenseExpiry = rs.getString("license_expiry");
                        String json = "{\"success\":true,\"company\":{" +
                                "\"id\":" + rs.getInt("id") + "," +
                                "\"companyName\":\"" + escapeJson(rs.getString("company_name")) + "\"," +
                                "\"companyEmail\":\"" + escapeJson(rs.getString("company_email")) + "\"," +
                                "\"solutionType\":\"" + escapeJson(rs.getString("solution_type")) + "\"," +
                                "\"isActive\":" + rs.getBoolean("is_active") + "," +
                                "\"licenseKey\":" + (licenseKey != null ? "\"" + escapeJson(licenseKey) + "\"" : "null") + "," +
                                "\"licenseExpiry\":" + (licenseExpiry != null ? "\"" + escapeJson(licenseExpiry) + "\"" : "null") + "," +
                                "\"admin\":{" +
                                "\"firstName\":\"" + escapeJson(rs.getString("first_name")) + "\"," +
                                "\"lastName\":\"" + escapeJson(rs.getString("last_name")) + "\"," +
                                "\"email\":\"" + escapeJson(rs.getString("admin_email")) + "\"," +
                                "\"title\":\"" + escapeJson(rs.getString("title")) + "\"," +
                                "\"role\":\"" + escapeJson(rs.getString("role")) + "\"" +
                                "}},\"userCount\":" + getUserCount(conn, companyId) + "}";
                        sendJson(response, 200, json);
                    } else {
                        sendJson(response, 404, "{\"success\":false,\"error\":\"Company not found\"}");
                    }
                }
            }
        } catch (SQLException e) {
            log("Database error loading company profile", e);
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

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (!Boolean.TRUE.equals(isAdmin)) {
            sendJson(response, 403,
                "{\"success\":false,\"error\":\"Admin access required to update company profile\"}");
            return;
        }

        Object companyIdObj = session.getAttribute("companyId");
        if (companyIdObj == null) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"No company associated\"}");
            return;
        }
        int companyId = (Integer) companyIdObj;
        String adminEmail = (String) session.getAttribute("userEmail");

        String body = readRequestBody(request);
        String firstName = extractJsonString(body, "firstName");
        String lastName = extractJsonString(body, "lastName");
        String solutionType = extractJsonString(body, "solutionType");

        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            sendJson(response, 400,
                "{\"success\":false,\"error\":\"First name and last name are required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Update admin user
                String userSql = "UPDATE users SET first_name = ?, last_name = ?, " +
                        "updated_at = NOW() WHERE company_id = ? AND LOWER(email) = LOWER(?) AND role = 'admin'";
                try (PreparedStatement stmt = conn.prepareStatement(userSql)) {
                    stmt.setString(1, firstName.trim());
                    stmt.setString(2, lastName.trim());
                    stmt.setInt(3, companyId);
                    stmt.setString(4, adminEmail);
                    stmt.executeUpdate();
                }

                // Update company solution type if provided
                if (solutionType != null && !solutionType.trim().isEmpty()) {
                    String companySql = "UPDATE companies SET solution_type = ?, " +
                            "updated_at = NOW() WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(companySql)) {
                        stmt.setString(1, solutionType.trim());
                        stmt.setInt(2, companyId);
                        stmt.executeUpdate();
                    }
                }

                conn.commit();

                // Update session
                session.setAttribute("userName", firstName.trim() + " " + lastName.trim());
                if (solutionType != null && !solutionType.trim().isEmpty()) {
                    session.setAttribute("solutionType", solutionType.trim());
                }

                // Audit: company profile update
                try {
                    Integer uid = (Integer) session.getAttribute("userId");
                    AuditService.record(dataSource, uid, companyId, adminEmail,
                        "company_update",
                        "Admin updated company profile" +
                            (solutionType != null ? " (solution type: " + solutionType.trim() + ")" : ""),
                        "company", String.valueOf(companyId),
                        request, null);
                } catch (Exception auditEx) {
                    log("Audit logging failed for company profile update", auditEx);
                }

                log("ApiCompanyProfileServlet: Company profile updated for company " + companyId);
                sendJson(response, 200,
                    "{\"success\":true,\"message\":\"Company profile updated successfully\"}");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            log("Database error updating company profile", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST is for company password change
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
            sendJson(response, 403,
                "{\"success\":false,\"error\":\"Admin access required\"}");
            return;
        }

        Object companyIdObj = session.getAttribute("companyId");
        if (companyIdObj == null) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"No company associated\"}");
            return;
        }
        int companyId = (Integer) companyIdObj;
        String adminEmail = (String) session.getAttribute("userEmail");

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
            // Verify old password against both company and user
            String sql = "SELECT c.password AS company_password, u.password AS user_password " +
                    "FROM companies c JOIN users u ON u.company_id = c.id " +
                    "WHERE c.id = ? AND LOWER(u.email) = LOWER(?) AND u.role = 'admin'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, companyId);
                stmt.setString(2, adminEmail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        sendJson(response, 404,
                            "{\"success\":false,\"error\":\"Company admin not found\"}");
                        return;
                    }
                    String companyPwd = rs.getString("company_password");
                    String userPwd = rs.getString("user_password");
                    if (!PasswordHasher.verify(oldPassword, companyPwd) &&
                        !PasswordHasher.verify(oldPassword, userPwd)) {
                        sendJson(response, 401,
                            "{\"success\":false,\"error\":\"Current password is incorrect\"}");
                        return;
                    }
                }
            }

            // Update both company and user passwords
            String hashedNew = PasswordHasher.hash(newPassword);
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE companies SET password = ?, updated_at = NOW() WHERE id = ?")) {
                    stmt.setString(1, hashedNew);
                    stmt.setInt(2, companyId);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE users SET password = ?, updated_at = NOW() " +
                        "WHERE company_id = ? AND LOWER(email) = LOWER(?) AND role = 'admin'")) {
                    stmt.setString(1, hashedNew);
                    stmt.setInt(2, companyId);
                    stmt.setString(3, adminEmail);
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

            // Audit: company password change
            try {
                Integer uid = (Integer) session.getAttribute("userId");
                AuditService.record(dataSource, uid, companyId, adminEmail,
                    "password_change",
                    "Admin changed company password",
                    "company", String.valueOf(companyId),
                    request, null);
            } catch (Exception auditEx) {
                log("Audit logging failed for company password change", auditEx);
            }

            log("ApiCompanyProfileServlet: Company password changed for company " + companyId);
            sendJson(response, 200,
                "{\"success\":true,\"message\":\"Company password changed successfully\"}");

        } catch (SQLException e) {
            log("Database error changing company password", e);
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

    private int getUserCount(Connection conn, int companyId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE company_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
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
