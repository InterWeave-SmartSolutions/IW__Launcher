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
import javax.sql.DataSource;

import com.interweave.web.PasswordHasher;

/**
 * ApiRegistrationServlet - JSON API endpoint for user self-registration.
 *
 * Performs the same registration logic as LocalRegistrationServlet but accepts
 * and returns JSON instead of form parameters and JSP redirects. This enables
 * the React IW Portal to register users via fetch().
 *
 * API Endpoint: POST /api/register
 *
 * Request Body:  {"companyName": "...", "email": "...", "firstName": "...",
 *                 "lastName": "...", "title": "...", "password": "...",
 *                 "confirmPassword": "..."}
 * Success (200): {"success": true, "message": "Registration successful"}
 * Error (400):   {"success": false, "error": "..."}  (validation errors)
 * Error (404):   {"success": false, "error": "..."}  (company not found)
 * Error (409):   {"success": false, "error": "..."}  (email already registered)
 * Error (500):   {"success": false, "error": "..."}  (database error)
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiRegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiRegistrationServlet initialized - using JNDI DataSource jdbc/IWDB");
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
        String companyName = extractJsonString(body, "companyName");
        String email = extractJsonString(body, "email");
        String firstName = extractJsonString(body, "firstName");
        String lastName = extractJsonString(body, "lastName");
        String title = extractJsonString(body, "title");
        String password = extractJsonString(body, "password");
        String confirmPassword = extractJsonString(body, "confirmPassword");

        // Validate required fields
        if (companyName == null || companyName.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Company name is required\"}");
            return;
        }
        if (email == null || email.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Email is required\"}");
            return;
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"First name is required\"}");
            return;
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Last name is required\"}");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Password is required\"}");
            return;
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Confirm password is required\"}");
            return;
        }
        if (!password.equals(confirmPassword)) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Passwords do not match\"}");
            return;
        }
        if (password.length() < 4) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Password must be at least 4 characters\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Step 1: Look up company by name
            int companyId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
                stmt.setString(1, companyName.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        companyId = rs.getInt("id");
                    }
                }
            }
            if (companyId == -1) {
                sendJson(response, HttpServletResponse.SC_NOT_FOUND,
                    "{\"success\":false,\"error\":\"Company '" + escapeJson(companyName.trim()) +
                    "' not found. Please register the company first.\"}");
                return;
            }

            // Step 2: Check email uniqueness
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM users WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, email.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        sendJson(response, 409,
                            "{\"success\":false,\"error\":\"A user with this email already exists\"}");
                        return;
                    }
                }
            }

            // Step 3: Hash password (bcrypt) and insert user
            String hashedPw = PasswordHasher.hash(password);
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (company_id, email, password, first_name, last_name, title, role, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?, 'user', TRUE)")) {
                stmt.setInt(1, companyId);
                stmt.setString(2, email.trim().toLowerCase());
                stmt.setString(3, hashedPw);
                stmt.setString(4, firstName.trim());
                stmt.setString(5, lastName.trim());
                stmt.setString(6, title != null ? title.trim() : "");
                stmt.executeUpdate();
            }

            // Audit: user registration
            try {
                AuditService.record(dataSource, null, companyId, email.trim().toLowerCase(),
                    "user_register",
                    "New user registered: " + firstName.trim() + " " + lastName.trim() +
                        " (company: " + companyName.trim() + ")",
                    "user", null,
                    request, null);
            } catch (Exception auditEx) {
                log("Audit logging failed for user registration", auditEx);
            }

            log("API user registered: " + email.trim() + " (Company: " + companyName.trim() + ")");
            sendJson(response, HttpServletResponse.SC_OK,
                "{\"success\":true,\"message\":\"Registration successful\"}");

        } catch (SQLException e) {
            log("Database error during API registration: " + e.getMessage(), e);
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
