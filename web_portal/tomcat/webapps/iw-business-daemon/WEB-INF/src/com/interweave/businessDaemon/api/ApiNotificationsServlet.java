package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
 * ApiNotificationsServlet - JSON API for the Notifications Inbox.
 *
 * GET    /api/notifications           - List notifications (paginated, filterable)
 * GET    /api/notifications/count     - Unread count (for bell badge)
 * PUT    /api/notifications/read      - Mark specific notifications as read
 * PUT    /api/notifications/read-all  - Mark all notifications as read
 * DELETE /api/notifications           - Delete specific notifications
 *
 * All endpoints require an authenticated session.
 */
public class ApiNotificationsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiNotificationsServlet initialized");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    // ─── GET ────────────────────────────────────────────────────────────

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

        Integer userId = getUserId(session);
        if (userId == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"User ID not found in session\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/count")) {
            handleGetUnreadCount(response, userId);
        } else {
            handleListNotifications(request, response, userId);
        }
    }

    private void handleListNotifications(HttpServletRequest request,
            HttpServletResponse response, int userId) throws IOException {
        int page = getIntParam(request, "page", 1);
        int pageSize = getIntParam(request, "page_size", 20);
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 1;
        if (pageSize > 100) pageSize = 100;

        String type = request.getParameter("type");
        boolean filterByType = type != null && !type.isEmpty() && !type.equals("all");
        String unreadOnlyParam = request.getParameter("unread_only");
        boolean unreadOnly = "true".equalsIgnoreCase(unreadOnlyParam);

        int offset = (page - 1) * pageSize;

        try (Connection conn = dataSource.getConnection()) {
            // Build WHERE clause
            StringBuilder where = new StringBuilder("WHERE user_id = ?");
            List<Object> params = new ArrayList<Object>();
            params.add(userId);

            if (filterByType) {
                where.append(" AND notification_type = ?");
                params.add(type);
            }
            if (unreadOnly) {
                where.append(" AND is_read = FALSE");
            }

            // Total count
            String countSql = "SELECT COUNT(*) FROM notifications " + where.toString();
            int totalCount = 0;
            try (PreparedStatement stmt = conn.prepareStatement(countSql)) {
                setParams(stmt, params);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) totalCount = rs.getInt(1);
                }
            }

            // Unread count (always for current user, ignoring filters)
            int unreadCount = 0;
            String unreadSql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = FALSE";
            try (PreparedStatement stmt = conn.prepareStatement(unreadSql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) unreadCount = rs.getInt(1);
                }
            }

            // Fetch page
            String selectSql = "SELECT id, notification_type, title, message, link, is_read, created_at " +
                    "FROM notifications " + where.toString() +
                    " ORDER BY created_at DESC LIMIT ? OFFSET ?";
            List<Object> selectParams = new ArrayList<Object>(params);
            selectParams.add(pageSize);
            selectParams.add(offset);

            StringBuilder items = new StringBuilder("[");
            boolean first = true;
            try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                setParams(stmt, selectParams);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        if (!first) items.append(",");
                        first = false;
                        items.append("{");
                        items.append("\"id\":").append(rs.getLong("id")).append(",");
                        items.append("\"type\":\"").append(escapeJson(rs.getString("notification_type"))).append("\",");
                        items.append("\"title\":\"").append(escapeJson(rs.getString("title"))).append("\",");
                        items.append("\"message\":\"").append(escapeJson(rs.getString("message"))).append("\",");
                        String link = rs.getString("link");
                        if (link != null) {
                            items.append("\"link\":\"").append(escapeJson(link)).append("\",");
                        } else {
                            items.append("\"link\":null,");
                        }
                        items.append("\"isRead\":").append(rs.getBoolean("is_read")).append(",");
                        Timestamp createdAt = rs.getTimestamp("created_at");
                        items.append("\"createdAt\":\"").append(createdAt != null ? createdAt.toInstant().toString() : "").append("\"");
                        items.append("}");
                    }
                }
            }
            items.append("]");

            String json = "{\"success\":true," +
                    "\"notifications\":" + items.toString() + "," +
                    "\"unreadCount\":" + unreadCount + "," +
                    "\"totalCount\":" + totalCount + "," +
                    "\"page\":" + page + "," +
                    "\"pageSize\":" + pageSize + "}";
            sendJson(response, 200, json);

        } catch (SQLException e) {
            log("Database error listing notifications", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleGetUnreadCount(HttpServletResponse response, int userId)
            throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = FALSE";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    int count = 0;
                    if (rs.next()) count = rs.getInt(1);
                    sendJson(response, 200, "{\"success\":true,\"unreadCount\":" + count + "}");
                }
            }
        } catch (SQLException e) {
            log("Database error getting unread count", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── PUT ────────────────────────────────────────────────────────────

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

        Integer userId = getUserId(session);
        if (userId == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"User ID not found in session\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/read-all")) {
            handleMarkAllRead(response, userId);
        } else if (pathInfo != null && pathInfo.equals("/read")) {
            handleMarkRead(request, response, userId);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Not found\"}");
        }
    }

    private void handleMarkRead(HttpServletRequest request,
            HttpServletResponse response, int userId) throws IOException {
        String body = readRequestBody(request);
        List<Long> ids = extractIdArray(body, "notificationIds");
        if (ids.isEmpty()) {
            sendJson(response, 400,
                    "{\"success\":false,\"error\":\"notificationIds array is required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE notifications SET is_read = TRUE, read_at = NOW() " +
                    "WHERE id = ? AND user_id = ?";
            int updated = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Long id : ids) {
                    stmt.setLong(1, id);
                    stmt.setInt(2, userId);
                    updated += stmt.executeUpdate();
                }
            }
            sendJson(response, 200,
                    "{\"success\":true,\"updated\":" + updated + "}");
        } catch (SQLException e) {
            log("Database error marking notifications as read", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleMarkAllRead(HttpServletResponse response, int userId)
            throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE notifications SET is_read = TRUE, read_at = NOW() " +
                    "WHERE user_id = ? AND is_read = FALSE";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                int updated = stmt.executeUpdate();
                sendJson(response, 200,
                        "{\"success\":true,\"updated\":" + updated + "}");
            }
        } catch (SQLException e) {
            log("Database error marking all notifications as read", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── DELETE ─────────────────────────────────────────────────────────

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        Integer userId = getUserId(session);
        if (userId == null) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"User ID not found in session\"}");
            return;
        }

        String body = readRequestBody(request);
        List<Long> ids = extractIdArray(body, "notificationIds");
        if (ids.isEmpty()) {
            sendJson(response, 400,
                    "{\"success\":false,\"error\":\"notificationIds array is required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM notifications WHERE id = ? AND user_id = ?";
            int deleted = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Long id : ids) {
                    stmt.setLong(1, id);
                    stmt.setInt(2, userId);
                    deleted += stmt.executeUpdate();
                }
            }
            sendJson(response, 200,
                    "{\"success\":true,\"deleted\":" + deleted + "}");
        } catch (SQLException e) {
            log("Database error deleting notifications", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── OPTIONS (CORS preflight) ───────────────────────────────────────

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Helpers ────────────────────────────────────────────────────────

    private Integer getUserId(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj instanceof Integer) {
            return (Integer) userIdObj;
        }
        if (userIdObj instanceof String) {
            // userId might be stored as the email string; look up by email
            // For now, try to find user ID from the database
            String userEmail = (String) userIdObj;
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT id FROM users WHERE LOWER(email) = LOWER(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, userEmail);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt("id");
                        }
                    }
                }
            } catch (SQLException e) {
                log("Database error looking up user ID for " + userEmail, e);
            }
        }
        // Also try userEmail attribute
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT id FROM users WHERE LOWER(email) = LOWER(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt("id");
                        }
                    }
                }
            } catch (SQLException e) {
                log("Database error looking up user ID for " + email, e);
            }
        }
        return null;
    }

    private int getIntParam(HttpServletRequest request, String name, int defaultValue) {
        String val = request.getParameter(name);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parse a JSON array of numbers manually (no JSON library).
     * Scans for digits between '[' and ']' in the value for the given key.
     */
    private List<Long> extractIdArray(String json, String key) {
        List<Long> ids = new ArrayList<Long>();
        if (json == null || key == null) return ids;

        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex < 0) return ids;

        int bracketStart = json.indexOf('[', keyIndex + searchKey.length());
        if (bracketStart < 0) return ids;

        int bracketEnd = json.indexOf(']', bracketStart);
        if (bracketEnd < 0) return ids;

        String arrayContent = json.substring(bracketStart + 1, bracketEnd);

        // Scan for digit sequences
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            if (c >= '0' && c <= '9') {
                num.append(c);
            } else {
                if (num.length() > 0) {
                    try {
                        ids.add(Long.parseLong(num.toString()));
                    } catch (NumberFormatException e) {
                        // skip invalid
                    }
                    num.setLength(0);
                }
            }
        }
        if (num.length() > 0) {
            try {
                ids.add(Long.parseLong(num.toString()));
            } catch (NumberFormatException e) {
                // skip
            }
        }
        return ids;
    }

    private void setParams(PreparedStatement stmt, List<Object> params)
            throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof Integer) {
                stmt.setInt(i + 1, (Integer) p);
            } else if (p instanceof String) {
                stmt.setString(i + 1, (String) p);
            } else if (p instanceof Long) {
                stmt.setLong(i + 1, (Long) p);
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

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, DELETE, OPTIONS");
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
