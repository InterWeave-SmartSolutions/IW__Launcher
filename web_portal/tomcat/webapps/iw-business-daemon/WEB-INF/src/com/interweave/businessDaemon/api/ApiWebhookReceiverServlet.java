package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * ApiWebhookReceiverServlet - Receives webhook callbacks from WebhookNotificationService.
 *
 * POST /api/webhooks/receive - Accept a webhook event payload, log it, and create
 *                              a notification for all admin users.
 *
 * Expected JSON payload:
 *   {
 *     "event_type": "flow_failure",
 *     "flow_name": "SF2QBInvoiceSync",
 *     "execution_id": "exec-12345",
 *     "error_code": "CONN_TIMEOUT",
 *     "error_message": "Connection timed out after 30s",
 *     "timestamp": "2026-03-10T14:30:00Z"
 *   }
 *
 * No authentication required (same-origin localhost call from WebhookNotificationService).
 */
public class ApiWebhookReceiverServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiWebhookReceiverServlet initialized");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    // --- POST ---

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String body = readRequestBody(request);
        if (body == null || body.trim().isEmpty()) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"Empty request body\"}");
            return;
        }

        // Parse fields from JSON payload (manual parsing, no JSON library)
        String eventType = extractJsonString(body, "event_type");
        String flowName = extractJsonString(body, "flow_name");
        String executionId = extractJsonString(body, "execution_id");
        String errorCode = extractJsonString(body, "error_code");
        String errorMessage = extractJsonString(body, "error_message");
        String timestamp = extractJsonString(body, "timestamp");

        if (eventType == null || eventType.isEmpty()) {
            sendJson(response, 400, "{\"success\":false,\"error\":\"event_type is required\"}");
            return;
        }

        // Log the received webhook
        log("[WebhookReceiver] Received webhook: event_type=" + eventType
                + ", flow_name=" + flowName
                + ", execution_id=" + executionId
                + ", error_code=" + errorCode
                + ", timestamp=" + timestamp);

        // Build notification title and message
        String title = buildNotificationTitle(eventType, flowName);
        String message = buildNotificationMessage(eventType, flowName, executionId,
                errorCode, errorMessage, timestamp);

        // Create a notification for each admin user
        try (Connection conn = dataSource.getConnection()) {
            List<Integer> adminUserIds = getAdminUserIds(conn);
            if (adminUserIds.isEmpty()) {
                log("[WebhookReceiver] No admin users found, notification skipped");
            } else {
                for (Integer userId : adminUserIds) {
                    NotificationService.createNotification(dataSource, userId, null,
                            "alert", title, message, "/monitoring");
                }
                log("[WebhookReceiver] Created notifications for " + adminUserIds.size() + " admin user(s)");
            }
        } catch (SQLException e) {
            log("[WebhookReceiver] Database error looking up admin users: " + e.getMessage());
            // Still return success to the webhook sender so it does not retry
        }

        sendJson(response, 200, "{\"success\":true}");
    }

    // --- OPTIONS (CORS preflight) ---

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // --- Helpers ---

    /**
     * Look up all admin user IDs. Admins have the email '__iw_admin__' or
     * a role/title indicating admin status. Falls back to user ID 1 if no
     * explicit admin is found.
     */
    private List<Integer> getAdminUserIds(Connection conn) throws SQLException {
        List<Integer> ids = new ArrayList<Integer>();
        String sql = "SELECT id FROM users WHERE LOWER(email) = '__iw_admin__' OR LOWER(email) = 'admin@sample.com'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }
        // Fallback: if no admin users found, try user ID 1
        if (ids.isEmpty()) {
            String fallbackSql = "SELECT id FROM users ORDER BY id LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(fallbackSql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            }
        }
        return ids;
    }

    private String buildNotificationTitle(String eventType, String flowName) {
        if (flowName != null && !flowName.isEmpty()) {
            return "Webhook: " + eventType + " - " + flowName;
        }
        return "Webhook: " + eventType;
    }

    private String buildNotificationMessage(String eventType, String flowName,
            String executionId, String errorCode, String errorMessage, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("Event: ").append(eventType != null ? eventType : "unknown");
        if (flowName != null && !flowName.isEmpty()) {
            sb.append("\nFlow: ").append(flowName);
        }
        if (executionId != null && !executionId.isEmpty()) {
            sb.append("\nExecution ID: ").append(executionId);
        }
        if (errorCode != null && !errorCode.isEmpty()) {
            sb.append("\nError Code: ").append(errorCode);
        }
        if (errorMessage != null && !errorMessage.isEmpty()) {
            sb.append("\nError: ").append(errorMessage);
        }
        if (timestamp != null && !timestamp.isEmpty()) {
            sb.append("\nTimestamp: ").append(timestamp);
        }
        return sb.toString();
    }

    /**
     * Extract a string value for a given key from JSON (manual parsing, no library).
     * Handles simple {"key":"value"} patterns. Returns null if not found.
     */
    private String extractJsonString(String json, String key) {
        if (json == null || key == null) return null;

        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex < 0) return null;

        // Find the colon after the key
        int colonIndex = json.indexOf(':', keyIndex + searchKey.length());
        if (colonIndex < 0) return null;

        // Skip whitespace after colon
        int valueStart = colonIndex + 1;
        while (valueStart < json.length() && json.charAt(valueStart) == ' ') {
            valueStart++;
        }
        if (valueStart >= json.length()) return null;

        // Check for null value
        if (json.charAt(valueStart) == 'n' && json.length() > valueStart + 3
                && json.substring(valueStart, valueStart + 4).equals("null")) {
            return null;
        }

        // Expect opening quote
        if (json.charAt(valueStart) != '"') return null;
        valueStart++; // skip opening quote

        // Find closing quote (handle escaped quotes)
        StringBuilder value = new StringBuilder();
        for (int i = valueStart; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '\\' && i + 1 < json.length()) {
                char next = json.charAt(i + 1);
                if (next == '"') {
                    value.append('"');
                    i++;
                } else if (next == '\\') {
                    value.append('\\');
                    i++;
                } else if (next == 'n') {
                    value.append('\n');
                    i++;
                } else if (next == 'r') {
                    value.append('\r');
                    i++;
                } else if (next == 't') {
                    value.append('\t');
                    i++;
                } else {
                    value.append(c);
                }
            } else if (c == '"') {
                break;
            } else {
                value.append(c);
            }
        }
        return value.toString();
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

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
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
}
