package com.interweave.monitoring.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.error.ErrorCode;

/**
 * DashboardApiServlet - Provides real-time dashboard data for monitoring UI.
 *
 * This servlet returns comprehensive system status information including:
 * - Count of running, completed, and failed transactions
 * - Success rate percentages for 24h and 7d periods
 * - List of currently running transaction flows with progress
 * - Recent activity summary
 *
 * Supports multi-tenant filtering by company_id.
 *
 * API Endpoint: GET /api/monitoring/dashboard
 *
 * Query Parameters:
 * - company_id (optional): Filter results by specific company (default: user's company)
 * - include_running (optional): Include detailed list of running transactions (default: true)
 *
 * Response Format:
 * {
 *   "success": true,
 *   "data": {
 *     "summary": {
 *       "running_count": 5,
 *       "completed_count": 1234,
 *       "failed_count": 23,
 *       "success_rate_24h": 98.5,
 *       "success_rate_7d": 97.2,
 *       "avg_duration_ms_24h": 1523,
 *       "last_updated": "2026-01-09T14:30:00Z"
 *     },
 *     "running_transactions": [
 *       {
 *         "execution_id": "exec-12345",
 *         "flow_name": "Customer Order Sync",
 *         "started_at": "2026-01-09T14:25:00Z",
 *         "duration_ms": 5000,
 *         "records_processed": 150
 *       }
 *     ],
 *     "recent_activity": {
 *       "last_hour": {
 *         "total": 45,
 *         "success": 43,
 *         "failed": 2
 *       }
 *     }
 *   }
 * }
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class DashboardApiServlet extends MonitoringApiServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        // Only support GET for dashboard data
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                ErrorCode.VALIDATION001, "Only GET method is supported for dashboard endpoint");
            return;
        }

        // Get user context for filtering
        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse query parameters
        Integer filterCompanyId = null;
        String companyIdParam = getStringParameter(request, "company_id", null);
        if (companyIdParam != null && !companyIdParam.isEmpty()) {
            try {
                filterCompanyId = Integer.parseInt(companyIdParam);

                // Security check: non-admin users can only view their own company data
                if (!isAdminUser && !filterCompanyId.equals(userCompanyId)) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                        ErrorCode.AUTH005, "You are not authorized to view data for other companies");
                    return;
                }
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid company_id parameter. Must be a valid integer.");
                return;
            }
        } else {
            // Default to user's company if not specified
            filterCompanyId = userCompanyId;
        }

        boolean includeRunning = getBooleanParameter(request, "include_running", true);

        try {
            // Build the dashboard data
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("\"data\":{");

            // Add summary section
            jsonBuilder.append("\"summary\":");
            jsonBuilder.append(getSummaryData(conn, filterCompanyId));
            jsonBuilder.append(",");

            // Add running transactions if requested
            if (includeRunning) {
                jsonBuilder.append("\"running_transactions\":");
                jsonBuilder.append(getRunningTransactions(conn, filterCompanyId));
                jsonBuilder.append(",");
            }

            // Add recent activity summary
            jsonBuilder.append("\"recent_activity\":");
            jsonBuilder.append(getRecentActivity(conn, filterCompanyId));

            jsonBuilder.append("}");

            // Send successful response
            String successJson = buildSuccessJson(jsonBuilder.toString());
            sendJsonResponse(response, successJson);

        } catch (SQLException e) {
            log("Database error while fetching dashboard data", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                ErrorCode.DB001, "Failed to retrieve dashboard data. Please try again.");
        }
    }

    /**
     * Retrieves summary statistics for the dashboard
     *
     * @param conn Database connection
     * @param companyId Company ID to filter by (null for all companies)
     * @return JSON string with summary data
     * @throws SQLException If database error occurs
     */
    private String getSummaryData(Connection conn, Integer companyId) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("{");

        String sql =
            "SELECT " +
            "  COUNT(CASE WHEN status = 'running' THEN 1 END) AS running_count, " +
            "  COUNT(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) AS success_24h, " +
            "  COUNT(CASE WHEN status = 'failed' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) AS failed_24h, " +
            "  COUNT(CASE WHEN status IN ('success', 'failed') AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) AS total_24h, " +
            "  COUNT(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 END) AS success_7d, " +
            "  COUNT(CASE WHEN status = 'failed' AND started_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 END) AS failed_7d, " +
            "  COUNT(CASE WHEN status IN ('success', 'failed') AND started_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 END) AS total_7d, " +
            "  AVG(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN duration_ms END) AS avg_duration_ms_24h " +
            "FROM transaction_executions " +
            (companyId != null ? "WHERE company_id = ? " : "");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (companyId != null) {
                stmt.setInt(1, companyId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int runningCount = rs.getInt("running_count");
                    int success24h = rs.getInt("success_24h");
                    int failed24h = rs.getInt("failed_24h");
                    int total24h = rs.getInt("total_24h");
                    int success7d = rs.getInt("success_7d");
                    int failed7d = rs.getInt("failed_7d");
                    int total7d = rs.getInt("total_7d");
                    Double avgDuration24h = rs.getDouble("avg_duration_ms_24h");
                    if (rs.wasNull()) {
                        avgDuration24h = 0.0;
                    }

                    // Calculate success rates
                    double successRate24h = 0.0;
                    if (total24h > 0) {
                        successRate24h = (success24h * 100.0) / total24h;
                    }

                    double successRate7d = 0.0;
                    if (total7d > 0) {
                        successRate7d = (success7d * 100.0) / total7d;
                    }

                    // Build JSON
                    json.append("\"running_count\":").append(runningCount).append(",");
                    json.append("\"completed_count_24h\":").append(success24h).append(",");
                    json.append("\"failed_count_24h\":").append(failed24h).append(",");
                    json.append("\"total_count_24h\":").append(total24h).append(",");
                    json.append("\"success_rate_24h\":").append(String.format("%.2f", successRate24h)).append(",");
                    json.append("\"completed_count_7d\":").append(success7d).append(",");
                    json.append("\"failed_count_7d\":").append(failed7d).append(",");
                    json.append("\"total_count_7d\":").append(total7d).append(",");
                    json.append("\"success_rate_7d\":").append(String.format("%.2f", successRate7d)).append(",");
                    json.append("\"avg_duration_ms_24h\":").append(Math.round(avgDuration24h)).append(",");
                    json.append("\"last_updated\":\"").append(getCurrentTimestamp()).append("\"");
                } else {
                    // No data found, return zeros
                    json.append("\"running_count\":0,");
                    json.append("\"completed_count_24h\":0,");
                    json.append("\"failed_count_24h\":0,");
                    json.append("\"total_count_24h\":0,");
                    json.append("\"success_rate_24h\":0.0,");
                    json.append("\"completed_count_7d\":0,");
                    json.append("\"failed_count_7d\":0,");
                    json.append("\"total_count_7d\":0,");
                    json.append("\"success_rate_7d\":0.0,");
                    json.append("\"avg_duration_ms_24h\":0,");
                    json.append("\"last_updated\":\"").append(getCurrentTimestamp()).append("\"");
                }
            }
        }

        json.append("}");
        return json.toString();
    }

    /**
     * Retrieves list of currently running transactions
     *
     * @param conn Database connection
     * @param companyId Company ID to filter by (null for all companies)
     * @return JSON array string with running transactions
     * @throws SQLException If database error occurs
     */
    private String getRunningTransactions(Connection conn, Integer companyId) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("[");

        String sql =
            "SELECT " +
            "  te.execution_id, " +
            "  te.flow_name, " +
            "  te.flow_type, " +
            "  te.started_at, " +
            "  TIMESTAMPDIFF(SECOND, te.started_at, NOW()) * 1000 AS duration_ms, " +
            "  te.records_processed, " +
            "  te.records_failed, " +
            "  p.name AS project_name " +
            "FROM transaction_executions te " +
            "LEFT JOIN projects p ON te.project_id = p.id " +
            "WHERE te.status = 'running' " +
            (companyId != null ? "AND te.company_id = ? " : "") +
            "ORDER BY te.started_at DESC " +
            "LIMIT 50";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (companyId != null) {
                stmt.setInt(1, companyId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append("{");
                    json.append("\"execution_id\":\"").append(escapeJson(rs.getString("execution_id"))).append("\",");
                    json.append("\"flow_name\":\"").append(escapeJson(rs.getString("flow_name"))).append("\",");
                    json.append("\"flow_type\":\"").append(escapeJson(rs.getString("flow_type"))).append("\",");
                    json.append("\"started_at\":\"").append(rs.getTimestamp("started_at")).append("\",");
                    json.append("\"duration_ms\":").append(rs.getLong("duration_ms")).append(",");
                    json.append("\"records_processed\":").append(rs.getInt("records_processed")).append(",");
                    json.append("\"records_failed\":").append(rs.getInt("records_failed")).append(",");

                    String projectName = rs.getString("project_name");
                    if (projectName != null) {
                        json.append("\"project_name\":\"").append(escapeJson(projectName)).append("\"");
                    } else {
                        json.append("\"project_name\":null");
                    }

                    json.append("}");
                }
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Retrieves recent activity summary
     *
     * @param conn Database connection
     * @param companyId Company ID to filter by (null for all companies)
     * @return JSON string with recent activity data
     * @throws SQLException If database error occurs
     */
    private String getRecentActivity(Connection conn, Integer companyId) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("{");

        // Get activity for last hour
        String sql =
            "SELECT " +
            "  COUNT(*) AS total, " +
            "  COUNT(CASE WHEN status = 'success' THEN 1 END) AS success, " +
            "  COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed " +
            "FROM transaction_executions " +
            "WHERE started_at >= DATE_SUB(NOW(), INTERVAL 1 HOUR) " +
            (companyId != null ? "AND company_id = ? " : "");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (companyId != null) {
                stmt.setInt(1, companyId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    json.append("\"last_hour\":{");
                    json.append("\"total\":").append(rs.getInt("total")).append(",");
                    json.append("\"success\":").append(rs.getInt("success")).append(",");
                    json.append("\"failed\":").append(rs.getInt("failed"));
                    json.append("}");
                } else {
                    json.append("\"last_hour\":{\"total\":0,\"success\":0,\"failed\":0}");
                }
            }
        }

        json.append("}");
        return json.toString();
    }

    /**
     * Gets current timestamp in ISO 8601 format
     *
     * @return ISO 8601 formatted timestamp
     */
    private String getCurrentTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new java.util.Date());
    }
}
