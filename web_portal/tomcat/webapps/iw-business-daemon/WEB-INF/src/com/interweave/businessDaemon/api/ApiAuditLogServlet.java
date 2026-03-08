package com.interweave.businessDaemon.api;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * ApiAuditLogServlet - JSON API for the admin audit log.
 *
 * ADMIN ONLY — every handler checks session isAdmin attribute.
 *
 * Path dispatch on getPathInfo():
 *   GET  /            — List audit entries (paginated, filterable)
 *   GET  /stats       — Summary statistics for last 30 days
 *   GET  /users       — Distinct users with audit entries (for filter dropdown)
 */
public class ApiAuditLogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiAuditLogServlet initialized");
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

        if (!isAdmin(request, response)) {
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            handleListEntries(request, response);
        } else if (pathInfo.equals("/stats")) {
            handleStats(request, response);
        } else if (pathInfo.equals("/users")) {
            handleUsers(request, response);
        } else {
            sendJson(response, 404, "{\"success\":false,\"error\":\"Not found\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Handlers ───────────────────────────────────────────────────────

    private void handleListEntries(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int page = getIntParam(request, "page", 1);
        int pageSize = getIntParam(request, "page_size", 50);
        if (page < 1) page = 1;
        if (pageSize < 1 || pageSize > 200) pageSize = 50;

        String actionType = request.getParameter("action_type");
        String userIdParam = request.getParameter("user_id");
        String dateFrom = request.getParameter("date_from");
        String dateTo = request.getParameter("date_to");
        String search = request.getParameter("search");

        // Build dynamic WHERE clause
        List<String> conditions = new ArrayList<String>();
        List<Object> params = new ArrayList<Object>();

        if (actionType != null && !actionType.trim().isEmpty()) {
            conditions.add("a.action_type = ?");
            params.add(actionType.trim());
        }
        if (userIdParam != null && !userIdParam.trim().isEmpty()) {
            try {
                int uid = Integer.parseInt(userIdParam.trim());
                conditions.add("a.user_id = ?");
                params.add(Integer.valueOf(uid));
            } catch (NumberFormatException ignored) {
                // skip invalid user_id filter
            }
        }
        if (dateFrom != null && !dateFrom.trim().isEmpty()) {
            conditions.add("a.created_at >= ?::timestamp");
            params.add(dateFrom.trim() + " 00:00:00");
        }
        if (dateTo != null && !dateTo.trim().isEmpty()) {
            conditions.add("a.created_at <= ?::timestamp");
            params.add(dateTo.trim() + " 23:59:59");
        }
        if (search != null && !search.trim().isEmpty()) {
            conditions.add("a.action_detail ILIKE ?");
            params.add("%" + search.trim() + "%");
        }

        StringBuilder whereClause = new StringBuilder();
        if (!conditions.isEmpty()) {
            whereClause.append(" WHERE ");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) whereClause.append(" AND ");
                whereClause.append(conditions.get(i));
            }
        }

        int offset = (page - 1) * pageSize;

        try (Connection conn = dataSource.getConnection()) {
            // Count query
            String countSql = "SELECT COUNT(*) FROM audit_log a" + whereClause.toString();
            int totalCount = 0;
            try (PreparedStatement stmt = conn.prepareStatement(countSql)) {
                setParams(stmt, params);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalCount = rs.getInt(1);
                    }
                }
            }

            // Data query
            String dataSql = "SELECT a.id, a.user_id, a.user_email, a.action_type, " +
                    "a.action_detail, a.resource_type, a.resource_id, a.ip_address, " +
                    "a.created_at " +
                    "FROM audit_log a" + whereClause.toString() +
                    " ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
            List<Object> dataParams = new ArrayList<Object>(params);
            dataParams.add(Integer.valueOf(pageSize));
            dataParams.add(Integer.valueOf(offset));

            StringBuilder entries = new StringBuilder("[");
            boolean first = true;
            try (PreparedStatement stmt = conn.prepareStatement(dataSql)) {
                setParams(stmt, dataParams);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        if (!first) entries.append(",");
                        first = false;
                        entries.append("{");
                        entries.append("\"id\":").append(rs.getLong("id")).append(",");
                        int uid = rs.getInt("user_id");
                        if (rs.wasNull()) {
                            entries.append("\"userId\":null,");
                        } else {
                            entries.append("\"userId\":").append(uid).append(",");
                        }
                        entries.append("\"userEmail\":\"").append(escapeJson(rs.getString("user_email"))).append("\",");
                        entries.append("\"actionType\":\"").append(escapeJson(rs.getString("action_type"))).append("\",");
                        entries.append("\"actionDetail\":\"").append(escapeJson(rs.getString("action_detail"))).append("\",");
                        String resType = rs.getString("resource_type");
                        if (resType != null) {
                            entries.append("\"resourceType\":\"").append(escapeJson(resType)).append("\",");
                        } else {
                            entries.append("\"resourceType\":null,");
                        }
                        String resId = rs.getString("resource_id");
                        if (resId != null) {
                            entries.append("\"resourceId\":\"").append(escapeJson(resId)).append("\",");
                        } else {
                            entries.append("\"resourceId\":null,");
                        }
                        String ip = rs.getString("ip_address");
                        if (ip != null) {
                            entries.append("\"ipAddress\":\"").append(escapeJson(ip)).append("\",");
                        } else {
                            entries.append("\"ipAddress\":null,");
                        }
                        java.sql.Timestamp ts = rs.getTimestamp("created_at");
                        entries.append("\"createdAt\":\"").append(ts != null ? ts.toString().replace(' ', 'T') : "").append("\"");
                        entries.append("}");
                    }
                }
            }
            entries.append("]");

            String json = "{\"success\":true," +
                    "\"entries\":" + entries.toString() + "," +
                    "\"totalCount\":" + totalCount + "," +
                    "\"page\":" + page + "," +
                    "\"pageSize\":" + pageSize + "}";
            sendJson(response, 200, json);

        } catch (SQLException e) {
            log("Database error listing audit entries", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleStats(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            // 1. Totals by action type
            String byTypeSql = "SELECT action_type, COUNT(*) as cnt FROM audit_log " +
                    "WHERE created_at > NOW() - INTERVAL '30 days' GROUP BY action_type";
            StringBuilder byType = new StringBuilder("{");
            int totalEvents = 0;
            boolean first = true;
            try (PreparedStatement stmt = conn.prepareStatement(byTypeSql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (!first) byType.append(",");
                    first = false;
                    int cnt = rs.getInt("cnt");
                    totalEvents += cnt;
                    byType.append("\"").append(escapeJson(rs.getString("action_type")))
                          .append("\":").append(cnt);
                }
            }
            byType.append("}");

            // 2. Counts by day
            String byDaySql = "SELECT DATE(created_at) as date, COUNT(*) as cnt FROM audit_log " +
                    "WHERE created_at > NOW() - INTERVAL '30 days' " +
                    "GROUP BY DATE(created_at) ORDER BY date";
            StringBuilder byDay = new StringBuilder("[");
            first = true;
            try (PreparedStatement stmt = conn.prepareStatement(byDaySql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (!first) byDay.append(",");
                    first = false;
                    byDay.append("{\"date\":\"").append(rs.getString("date"))
                         .append("\",\"count\":").append(rs.getInt("cnt")).append("}");
                }
            }
            byDay.append("]");

            String json = "{\"success\":true,\"stats\":{" +
                    "\"totalEvents\":" + totalEvents + "," +
                    "\"byActionType\":" + byType.toString() + "," +
                    "\"byDay\":" + byDay.toString() + "}}";
            sendJson(response, 200, json);

        } catch (SQLException e) {
            log("Database error loading audit stats", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleUsers(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT DISTINCT u.id, u.email, u.first_name, u.last_name " +
                    "FROM users u INNER JOIN audit_log a ON u.id = a.user_id " +
                    "ORDER BY u.email";
            StringBuilder users = new StringBuilder("[");
            boolean first = true;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (!first) users.append(",");
                    first = false;
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String name = "";
                    if (firstName != null) name = firstName;
                    if (lastName != null) name = name.isEmpty() ? lastName : name + " " + lastName;
                    users.append("{\"id\":").append(rs.getInt("id"))
                         .append(",\"email\":\"").append(escapeJson(rs.getString("email")))
                         .append("\",\"name\":\"").append(escapeJson(name)).append("\"}");
                }
            }
            users.append("]");

            sendJson(response, 200, "{\"success\":true,\"users\":" + users.toString() + "}");

        } catch (SQLException e) {
            log("Database error loading audit users", e);
            sendJson(response, 500, "{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    // ─── Helpers ────────────────────────────────────────────────────────

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendJson(response, 401, "{\"success\":false,\"error\":\"Not authenticated\"}");
            return false;
        }
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            sendJson(response, 403, "{\"success\":false,\"error\":\"Admin access required\"}");
            return false;
        }
        return true;
    }

    private int getIntParam(HttpServletRequest request, String name, int defaultValue) {
        String val = request.getParameter(name);
        if (val == null || val.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void setParams(PreparedStatement stmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof Integer) {
                stmt.setInt(i + 1, ((Integer) p).intValue());
            } else {
                stmt.setString(i + 1, (String) p);
            }
        }
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
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
