package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * ApiEngineStatusServlet - Transformation server health, test, and transaction recording endpoints.
 *
 * GET  /api/engine/status  - Ping the TS and return health info
 * GET  /api/engine/test    - Run the sessionvars transaction and return live XML result
 * POST /api/engine/record  - Run sessionvars, record result in transaction_executions, return execution_id
 * POST /api/engine/seed    - Admin: insert N synthetic test records for dashboard/chart testing
 */
public class ApiEngineStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int TIMEOUT_MS = 8000;

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            dataSource = (DataSource) envCtx.lookup("jdbc/IWDB");
        } catch (Exception e) {
            // DataSource unavailable — record/seed endpoints will fail gracefully
            log("ApiEngineStatusServlet: DataSource unavailable: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/status".equals(pathInfo)) {
            handleStatus(response);
        } else if ("/test".equals(pathInfo)) {
            handleTest(request, response);
        } else {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Use /status, /test, or POST /record\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/record".equals(pathInfo)) {
            handleRecord(request, response, session);
        } else if ("/seed".equals(pathInfo)) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            if (isAdmin == null || !isAdmin) {
                response.setStatus(403);
                response.getWriter().write("{\"success\":false,\"error\":\"Admin required\"}");
                return;
            }
            handleSeed(request, response, session);
        } else {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Use /record or /seed\"}");
        }
    }

    /**
     * GET /api/engine/status — pings the TS /transform endpoint with no params.
     */
    private void handleStatus(HttpServletResponse response) throws IOException {
        String tsUrl = getTsBaseUrl() + "/transform";
        long start = System.currentTimeMillis();
        int httpCode = -1;
        String rawResponse = null;
        String error = null;

        try {
            URL url = new URL(tsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            conn.connect();
            httpCode = conn.getResponseCode();
            rawResponse = readStream(conn);
            conn.disconnect();
        } catch (Exception e) {
            error = e.getClass().getSimpleName() + ": " + e.getMessage();
        }

        long elapsed = System.currentTimeMillis() - start;
        boolean up = (httpCode == 200);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true");
        sb.append(",\"engineUp\":").append(up);
        sb.append(",\"httpCode\":").append(httpCode);
        sb.append(",\"responseMs\":").append(elapsed);
        sb.append(",\"tsUrl\":\"").append(escapeJson(tsUrl)).append("\"");
        if (error != null) {
            sb.append(",\"error\":\"").append(escapeJson(error)).append("\"");
        }
        if (rawResponse != null) {
            String excerpt = rawResponse.length() > 200 ? rawResponse.substring(0, 200) : rawResponse;
            sb.append(",\"responseExcerpt\":\"").append(escapeJson(excerpt)).append("\"");
        }
        sb.append("}");
        response.getWriter().write(sb.toString());
    }

    /**
     * GET /api/engine/test?flow=sessionvars — runs a named transaction and returns raw XML.
     */
    private void handleTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flow = request.getParameter("flow");
        if (flow == null || flow.isEmpty()) flow = "sessionvars";

        if (!flow.matches("[a-zA-Z0-9_-]+")) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"Invalid flow name\"}");
            return;
        }

        String tsUrl = getTsBaseUrl() + "/transform";
        long start = System.currentTimeMillis();
        int httpCode = -1;
        String rawXml = null;
        String error = null;

        try {
            String fullUrl = tsUrl;
            if (!"sessionvars".equals(flow)) {
                fullUrl = tsUrl + "?iwflowname=" + URLEncoder.encode(flow, "UTF-8");
            }

            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            String cookieHeader = request.getHeader("Cookie");
            if (cookieHeader != null) {
                conn.setRequestProperty("Cookie", cookieHeader);
            }
            conn.connect();
            httpCode = conn.getResponseCode();
            rawXml = readStream(conn);
            conn.disconnect();
        } catch (Exception e) {
            error = e.getClass().getSimpleName() + ": " + e.getMessage();
        }

        long elapsed = System.currentTimeMillis() - start;
        boolean ok = (httpCode == 200);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":").append(ok);
        sb.append(",\"flow\":\"").append(escapeJson(flow)).append("\"");
        sb.append(",\"httpCode\":").append(httpCode);
        sb.append(",\"responseMs\":").append(elapsed);
        if (error != null) {
            sb.append(",\"error\":\"").append(escapeJson(error)).append("\"");
        }
        if (rawXml != null) {
            sb.append(",\"rawXml\":\"").append(escapeJson(rawXml)).append("\"");
        }
        sb.append("}");
        response.getWriter().write(sb.toString());
    }

    /**
     * POST /api/engine/record — calls the TS (sessionvars), records result in transaction_executions.
     * Returns the execution_id of the newly inserted row.
     */
    private void handleRecord(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session) throws IOException {
        if (dataSource == null) {
            response.setStatus(503);
            response.getWriter().write("{\"success\":false,\"error\":\"Database unavailable\"}");
            return;
        }

        Integer companyId = (Integer) session.getAttribute("companyId");
        Integer userId = (Integer) session.getAttribute("userId");
        String flowName = "sessionvars";

        // Call the TS
        String tsUrl = getTsBaseUrl() + "/transform";
        long start = System.currentTimeMillis();
        int httpCode = -1;
        String rawXml = null;
        String error = null;

        try {
            URL url = new URL(tsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            conn.connect();
            httpCode = conn.getResponseCode();
            rawXml = readStream(conn);
            conn.disconnect();
        } catch (Exception e) {
            error = e.getClass().getSimpleName() + ": " + e.getMessage();
        }

        long elapsed = System.currentTimeMillis() - start;
        boolean ok = (httpCode == 200);
        String status = ok ? "success" : "failed";
        String executionId = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp startTs = new Timestamp(start);

        // Insert into transaction_executions
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO transaction_executions " +
                "(execution_id, company_id, flow_name, flow_type, status, " +
                "started_at, completed_at, duration_ms, records_processed, " +
                "triggered_by, triggered_by_user_id, server_hostname, error_message) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, executionId);
                if (companyId != null) ps.setInt(2, companyId); else ps.setNull(2, java.sql.Types.INTEGER);
                ps.setString(3, flowName);
                ps.setString(4, "query");
                ps.setString(5, status);
                ps.setTimestamp(6, startTs);
                ps.setTimestamp(7, now);
                ps.setInt(8, (int) elapsed);
                ps.setInt(9, ok ? 1 : 0);
                ps.setString(10, "api");
                if (userId != null) ps.setInt(11, userId); else ps.setNull(11, java.sql.Types.INTEGER);
                ps.setString(12, "localhost");
                ps.setString(13, error);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"DB insert failed: " +
                escapeJson(e.getMessage()) + "\"}");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":").append(ok);
        sb.append(",\"executionId\":\"").append(executionId).append("\"");
        sb.append(",\"flow\":\"").append(flowName).append("\"");
        sb.append(",\"status\":\"").append(status).append("\"");
        sb.append(",\"httpCode\":").append(httpCode);
        sb.append(",\"responseMs\":").append(elapsed);
        if (error != null) {
            sb.append(",\"error\":\"").append(escapeJson(error)).append("\"");
        }
        sb.append("}");
        response.getWriter().write(sb.toString());
    }

    /**
     * POST /api/engine/seed?count=20 — admin only.
     * Inserts N synthetic transaction_executions records spread across the last 24h.
     * Used to populate dashboard charts and transaction history for testing.
     */
    private void handleSeed(HttpServletRequest request, HttpServletResponse response,
                            HttpSession session) throws IOException {
        if (dataSource == null) {
            response.setStatus(503);
            response.getWriter().write("{\"success\":false,\"error\":\"Database unavailable\"}");
            return;
        }

        Integer companyId = (Integer) session.getAttribute("companyId");
        Integer userId = (Integer) session.getAttribute("userId");

        int count = 20;
        try {
            String c = request.getParameter("count");
            if (c != null) count = Math.min(500, Math.max(1, Integer.parseInt(c)));
        } catch (NumberFormatException e) { /* use default */ }

        int days = 1;
        try {
            String d = request.getParameter("days");
            if (d != null) days = Math.min(90, Math.max(1, Integer.parseInt(d)));
        } catch (NumberFormatException e) { /* use default */ }

        String[] flowNames = {
            "GetSFAccounts", "GetSFOpportunities", "SyncContacts",
            "CreateInvoice", "UpdatePaymentStatus", "sessionvars",
            "SyncProducts", "GetQBInvoices", "PushContacts", "ReconcilePayments"
        };
        String[] flowTypes = {"transaction", "transaction", "transaction", "utility", "utility", "query",
                              "transaction", "transaction", "transaction", "utility"};
        String[] statuses = {"success", "success", "success", "success", "failed"};

        long now = System.currentTimeMillis();
        long windowMs = days * 24L * 3600_000L;

        int inserted = 0;
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO transaction_executions " +
                "(execution_id, company_id, flow_name, flow_type, status, " +
                "started_at, completed_at, duration_ms, records_processed, " +
                "triggered_by, triggered_by_user_id, server_hostname) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < count; i++) {
                    long offset = (long)(Math.random() * windowMs);
                    long startMs = now - offset;
                    int durationMs = 500 + (int)(Math.random() * 8000);
                    int records = (int)(Math.random() * 150);
                    int fi = (int)(Math.random() * flowNames.length);
                    String st = statuses[(int)(Math.random() * statuses.length)];

                    ps.setString(1, UUID.randomUUID().toString());
                    if (companyId != null) ps.setInt(2, companyId); else ps.setNull(2, java.sql.Types.INTEGER);
                    ps.setString(3, flowNames[fi]);
                    ps.setString(4, flowTypes[fi]);
                    ps.setString(5, st);
                    ps.setTimestamp(6, new Timestamp(startMs));
                    ps.setTimestamp(7, new Timestamp(startMs + durationMs));
                    ps.setInt(8, durationMs);
                    ps.setInt(9, records);
                    ps.setString(10, "scheduler");
                    if (userId != null) ps.setInt(11, userId); else ps.setNull(11, java.sql.Types.INTEGER);
                    ps.setString(12, "localhost");
                    ps.executeUpdate();
                    inserted++;
                }
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"Seed failed: " +
                escapeJson(e.getMessage()) + "\",\"inserted\":" + inserted + "}");
            return;
        }

        response.getWriter().write("{\"success\":true,\"inserted\":" + inserted +
            ",\"message\":\"Seeded " + inserted + " test records across " + days + " day(s)\"}");
    }

    private String getTsBaseUrl() {
        String tsBase = System.getProperty("iw.ts.base.url");
        if (tsBase != null && !tsBase.isEmpty()) return tsBase;
        return "http://localhost:9090/iwtransformationserver";
    }

    private static String readStream(HttpURLConnection conn) {
        try {
            java.io.InputStream is = conn.getResponseCode() >= 400
                ? conn.getErrorStream() : conn.getInputStream();
            if (is == null) return "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            reader.close();
            return sb.toString().trim();
        } catch (Exception e) {
            return "";
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }
}
