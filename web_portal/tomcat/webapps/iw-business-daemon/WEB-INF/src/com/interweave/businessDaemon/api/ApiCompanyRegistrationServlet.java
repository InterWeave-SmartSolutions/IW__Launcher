package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * ApiCompanyRegistrationServlet - JSON API endpoint for company + admin registration.
 *
 * Performs the same registration logic as LocalCompanyRegistrationServlet but accepts
 * and returns JSON instead of form parameters and JSP redirects. This enables
 * the React IW Portal to register companies via fetch().
 *
 * API Endpoints:
 *   POST /api/register/company         - Register new company + admin user
 *   GET  /api/register/company         - Alias for solution-types (not used)
 *   GET  /api/register/solution-types  - List available solution types
 *
 * POST Request Body:
 *   {"companyName": "...", "email": "...", "firstName": "...", "lastName": "...",
 *    "password": "...", "confirmPassword": "...", "solutionType": "QB"}
 *
 * POST Success (200): {"success": true, "message": "Company registered successfully", "companyId": 42}
 * POST Error (400):   {"success": false, "error": "..."}  (validation errors)
 * POST Error (409):   {"success": false, "error": "..."}  (company or email exists)
 * POST Error (500):   {"success": false, "error": "..."}  (database error)
 *
 * GET Success (200):  {"success": true, "solutionTypes": [...]}
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiCompanyRegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiCompanyRegistrationServlet initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            log("Failed to initialize DataSource", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    /**
     * GET /api/register/company — returns available solution types.
     * Also mapped via /api/register/solution-types in web.xml.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        sendJson(response, HttpServletResponse.SC_OK, buildSolutionTypesJson());
    }

    /**
     * POST /api/register/company — registers a new company and admin user.
     */
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
        String password = extractJsonString(body, "password");
        String confirmPassword = extractJsonString(body, "confirmPassword");
        String solutionType = extractJsonString(body, "solutionType");

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

        // Default solution type if not provided
        if (solutionType == null || solutionType.trim().isEmpty()) {
            solutionType = "QB";
        }

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Step 1: Check company name uniqueness
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
                    stmt.setString(1, companyName.trim());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            sendJson(response, 409,
                                "{\"success\":false,\"error\":\"Company '" +
                                escapeJson(companyName.trim()) + "' already exists\"}");
                            return;
                        }
                    }
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

                // Step 3: Hash password (bcrypt)
                String hashedPw = PasswordHasher.hash(password);

                // Step 4: Insert company (uses getGeneratedKeys for cross-DB compat)
                int companyId;
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO companies (company_name, email, password, solution_type, is_active) " +
                        "VALUES (?, ?, ?, ?, TRUE)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, companyName.trim());
                    stmt.setString(2, email.trim().toLowerCase());
                    stmt.setString(3, hashedPw);
                    stmt.setString(4, solutionType.trim());
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Failed to retrieve generated company ID");
                        }
                        companyId = rs.getInt(1);
                    }
                }

                // Step 5: Insert admin user for this company
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (company_id, email, password, first_name, last_name, role, is_active) " +
                        "VALUES (?, ?, ?, ?, ?, 'admin', TRUE)")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, email.trim().toLowerCase());
                    stmt.setString(3, hashedPw);
                    stmt.setString(4, firstName.trim());
                    stmt.setString(5, lastName.trim());
                    stmt.executeUpdate();
                }

                conn.commit();

                // Audit: company registration
                try {
                    AuditService.record(dataSource, null, companyId, email.trim().toLowerCase(),
                        "company_register",
                        "New company registered: " + companyName.trim() +
                            " (admin: " + firstName.trim() + " " + lastName.trim() +
                            ", solution: " + solutionType.trim() + ")",
                        "company", String.valueOf(companyId),
                        request, null);
                } catch (Exception auditEx) {
                    log("Audit logging failed for company registration", auditEx);
                }

                log("API company registered: " + companyName.trim() + " (Admin: " + email.trim() + ")");
                sendJson(response, HttpServletResponse.SC_OK,
                    "{\"success\":true,\"message\":\"Company registered successfully\",\"companyId\":" +
                    companyId + "}");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            log("Database error during API company registration: " + e.getMessage(), e);
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

    // ─── Solution Types ─────────────────────────────────────────────────

    /**
     * Builds the full solution types JSON. Mirrors the options from
     * CompanyRegistration.jsp — all 50 solution type codes with labels,
     * grouped by CRM and financial system.
     */
    private String buildSolutionTypesJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\"success\":true,\"solutionTypes\":[");

        // Salesforce to QuickBooks family
        appendType(json, "SF2QB3", "Salesforce to QuickBooks Professional", "Salesforce", "QuickBooks");
        appendType(json, "SF2QB2", "Salesforce to QuickBooks Premier", "Salesforce", "QuickBooks");
        appendType(json, "SF2QB1", "Salesforce to QuickBooks Small Business", "Salesforce", "QuickBooks");
        appendType(json, "SF2QB", "Salesforce to QuickBooks Enterprise", "Salesforce", "QuickBooks");

        // Salesforce to other systems
        appendType(json, "SF2CMS", "Salesforce to CMS", "Salesforce", "CMS");
        appendType(json, "SF2OMC", "Salesforce to Ecommerce/OMS (Full Generic)", "Salesforce", "Ecommerce/OMS");
        appendType(json, "SF2OMS", "Salesforce to Ecommerce/OMS (Generic)", "Salesforce", "Ecommerce/OMS");
        appendType(json, "SF2OMSQB", "Salesforce to Nexternal and QB", "Salesforce", "Nexternal/QuickBooks");
        appendType(json, "SF2OMSDB", "Salesforce to Nexternal and DB", "Salesforce", "Nexternal/DB");
        appendType(json, "SF2DBG", "Salesforce to DB (Generic)", "Salesforce", "Database");
        appendType(json, "SF2AUTH", "Salesforce to Authorize.net", "Salesforce", "Authorize.net");
        appendType(json, "SF2PGG", "Salesforce to Payment Gateway (Generic)", "Salesforce", "Payment Gateway");
        appendType(json, "SF2MAS200", "Salesforce to MAS", "Salesforce", "MAS");

        // Salesforce to Nexternal 2
        appendType(json, "SF2OMS2", "Salesforce to Nexternal 2", "Salesforce", "Nexternal");

        // Salesforce to NetSuite family
        appendType(json, "SF2NS3", "Salesforce to NetSuite Professional", "Salesforce", "NetSuite");
        appendType(json, "SF2NS2", "Salesforce to NetSuite Premier", "Salesforce", "NetSuite");
        appendType(json, "SF2NS1", "Salesforce to NetSuite Small Business", "Salesforce", "NetSuite");
        appendType(json, "SF2NS", "Salesforce to NetSuite Enterprise", "Salesforce", "NetSuite");

        // OMS/Nexternal integrations
        appendType(json, "OMS2QB", "Nexternal to QuickBooks", "Nexternal", "QuickBooks");
        appendType(json, "OMS2ACC", "Nexternal to Accpac", "Nexternal", "Accpac");

        // Oracle
        appendType(json, "ORA2QB", "Oracle Fusion to QuickBooks", "Oracle Fusion", "QuickBooks");

        // Microsoft Dynamics CRM
        appendType(json, "MSDCRM2QB", "Microsoft Dynamics CRM to QuickBooks", "MS Dynamics CRM", "QuickBooks");

        // PPOL
        appendType(json, "PPOL2QB", "PPOL to QuickBooks", "PPOL", "QuickBooks");

        // Aria
        appendType(json, "AR2QB", "Aria to QuickBooks", "Aria", "QuickBooks");
        appendType(json, "AR2NS", "Aria to NetSuite", "Aria", "NetSuite");

        // Sugar CRM family
        appendType(json, "SUG2QB3", "Sugar CRM to QuickBooks Professional", "Sugar CRM", "QuickBooks");
        appendType(json, "SUG2QB2", "Sugar CRM to QuickBooks Premier", "Sugar CRM", "QuickBooks");
        appendType(json, "SUG2QB1", "Sugar CRM to QuickBooks Small Business", "Sugar CRM", "QuickBooks");
        appendType(json, "SUG2QB", "Sugar CRM to QuickBooks Enterprise", "Sugar CRM", "QuickBooks");
        appendType(json, "SUG2DBG", "Sugar CRM to DB (Generic)", "Sugar CRM", "Database");

        // Generic DB integrations
        appendType(json, "DB2QBG", "DB to QB (Generic)", "Database", "QuickBooks");
        appendType(json, "DB2FSG", "DB to Financial System (Generic)", "Database", "Financial System");

        // Generic CRM integrations
        appendType(json, "CRM2EGG", "CRM to Email Gateway (Generic)", "CRM", "Email Gateway");
        appendType(json, "CRM2PGG", "CRM to Payment Gateway (Generic)", "CRM", "Payment Gateway");
        appendType(json, "CRM2DBG", "CRM to DB (Generic)", "CRM", "Database");
        appendType(json, "CRM2NS", "CRM to NetSuite (Generic)", "CRM", "NetSuite");
        appendType(json, "CRM2OMC", "CRM to Ecommerce/OMS (Generic)", "CRM", "Ecommerce/OMS");

        // CRM to Sage family
        appendType(json, "CRM2PT3", "CRM to Sage Professional", "CRM", "Sage");
        appendType(json, "CRM2PT2", "CRM to Sage Premier", "CRM", "Sage");
        appendType(json, "CRM2PT1", "CRM to Sage Small Business", "CRM", "Sage");
        appendType(json, "CRM2PT", "CRM to Sage Enterprise", "CRM", "Sage");

        // CRM to MS GP family
        appendType(json, "CRM2GP3", "CRM to MS GP Professional", "CRM", "MS Great Plains");
        appendType(json, "CRM2GP2", "CRM to MS GP Premier", "CRM", "MS Great Plains");
        appendType(json, "CRM2GP1", "CRM to MS GP Small Business", "CRM", "MS Great Plains");
        appendType(json, "CRM2GP", "CRM to MS GP Enterprise", "CRM", "MS Great Plains");

        // CRM to QuickBooks family
        appendType(json, "CRM2QB3", "CRM to QuickBooks Professional", "CRM", "QuickBooks");
        appendType(json, "CRM2QB2", "CRM to QuickBooks Premier", "CRM", "QuickBooks");
        appendType(json, "CRM2QB1", "CRM to QuickBooks Small Business", "CRM", "QuickBooks");

        // Last entry — no trailing comma
        json.append("{\"code\":\"CRM2QB\",\"label\":\"CRM to QuickBooks Enterprise\",");
        json.append("\"crm\":\"CRM\",\"fs\":\"QuickBooks\"}");

        json.append("]}");
        return json.toString();
    }

    /**
     * Appends a solution type entry to the JSON array (with trailing comma).
     */
    private void appendType(StringBuilder json, String code, String label, String crm, String fs) {
        json.append("{\"code\":\"").append(code).append("\",");
        json.append("\"label\":\"").append(label).append("\",");
        json.append("\"crm\":\"").append(crm).append("\",");
        json.append("\"fs\":\"").append(fs).append("\"},");
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
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
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
