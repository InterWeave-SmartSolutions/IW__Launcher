package com.interweave.monitoring.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.error.ErrorCode;

/**
 * MetricsApiServlet - Provides time-series performance metrics for trend analysis.
 *
 * This servlet returns aggregated metrics over time for visualization in charts:
 * - Success/failure counts over time
 * - Average execution time trends
 * - Records processed throughput
 * - Configurable time granularity (hourly, daily, weekly)
 *
 * Metrics are computed from both the transaction_metrics table (pre-aggregated)
 * and transaction_executions table (real-time data) to provide the best balance
 * of performance and freshness.
 *
 * Supports multi-tenant filtering by company_id with security enforcement.
 *
 * API Endpoint: GET /api/monitoring/metrics
 *
 * Query Parameters:
 * - granularity (optional): Time granularity - hourly, daily, weekly (default: daily)
 * - period (optional): Time period - 24h, 7d, 30d, 90d (default: 7d)
 * - flow_name (optional): Filter by specific flow name
 * - project_id (optional): Filter by project ID
 * - company_id (optional): Filter by company (admin only, default: user's company)
 *
 * Response Format (Chart.js compatible):
 * {
 *   "success": true,
 *   "data": {
 *     "labels": ["2026-01-08", "2026-01-09", ...],
 *     "datasets": [
 *       {
 *         "label": "Successful Executions",
 *         "data": [45, 52, 48, ...]
 *       },
 *       {
 *         "label": "Failed Executions",
 *         "data": [2, 1, 3, ...]
 *       },
 *       {
 *         "label": "Average Duration (ms)",
 *         "data": [1523, 1456, 1589, ...]
 *       },
 *       {
 *         "label": "Records Processed",
 *         "data": [1250, 1340, 1180, ...]
 *       }
 *     ],
 *     "summary": {
 *       "total_executions": 150,
 *       "success_rate": 97.3,
 *       "avg_duration_ms": 1523,
 *       "total_records": 3770
 *     }
 *   }
 * }
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class MetricsApiServlet extends MonitoringApiServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        // Only support GET for metrics data
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                ErrorCode.VALIDATION001, "Only GET method is supported for metrics endpoint");
            return;
        }

        // Get user context for filtering
        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse and validate company_id filter
        Integer filterCompanyId = parseCompanyIdFilter(request, userCompanyId, isAdminUser, response);
        if (filterCompanyId == null && response.isCommitted()) {
            return; // Error response already sent
        }

        // Parse granularity parameter
        String granularity = getStringParameter(request, "granularity", "daily");
        if (!isValidGranularity(granularity)) {
            sendValidationError(response,
                "Invalid granularity parameter. Must be one of: hourly, daily, weekly");
            return;
        }

        // Parse period parameter
        String period = getStringParameter(request, "period", "7d");
        int periodHours = parsePeriod(period);
        if (periodHours == -1) {
            sendValidationError(response,
                "Invalid period parameter. Must be one of: 24h, 7d, 30d, 90d");
            return;
        }

        // Parse filter parameters
        String flowName = getStringParameter(request, "flow_name", null);
        Integer projectId = null;
        String projectIdParam = getStringParameter(request, "project_id", null);
        if (projectIdParam != null) {
            try {
                projectId = Integer.parseInt(projectIdParam);
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid project_id parameter. Must be a valid integer.");
                return;
            }
        }

        try {
            // Get metrics data based on granularity and period
            MetricsData metricsData = getMetricsData(conn, filterCompanyId, flowName, projectId,
                                                      granularity, periodHours);

            // Build Chart.js compatible response
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("\"data\":{");

            // Add labels array
            jsonBuilder.append("\"labels\":[");
            for (int i = 0; i < metricsData.labels.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append("\"").append(escapeJson(metricsData.labels[i])).append("\"");
            }
            jsonBuilder.append("],");

            // Add datasets array
            jsonBuilder.append("\"datasets\":[");

            // Dataset 1: Successful Executions
            jsonBuilder.append("{");
            jsonBuilder.append("\"label\":\"Successful Executions\",");
            jsonBuilder.append("\"data\":[");
            for (int i = 0; i < metricsData.successCounts.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(metricsData.successCounts[i]);
            }
            jsonBuilder.append("]");
            jsonBuilder.append("},");

            // Dataset 2: Failed Executions
            jsonBuilder.append("{");
            jsonBuilder.append("\"label\":\"Failed Executions\",");
            jsonBuilder.append("\"data\":[");
            for (int i = 0; i < metricsData.failedCounts.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(metricsData.failedCounts[i]);
            }
            jsonBuilder.append("]");
            jsonBuilder.append("},");

            // Dataset 3: Average Duration
            jsonBuilder.append("{");
            jsonBuilder.append("\"label\":\"Average Duration (ms)\",");
            jsonBuilder.append("\"data\":[");
            for (int i = 0; i < metricsData.avgDurations.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(metricsData.avgDurations[i]);
            }
            jsonBuilder.append("]");
            jsonBuilder.append("},");

            // Dataset 4: Records Processed
            jsonBuilder.append("{");
            jsonBuilder.append("\"label\":\"Records Processed\",");
            jsonBuilder.append("\"data\":[");
            for (int i = 0; i < metricsData.recordsProcessed.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(metricsData.recordsProcessed[i]);
            }
            jsonBuilder.append("]");
            jsonBuilder.append("}");

            jsonBuilder.append("],");

            // Add summary section
            jsonBuilder.append("\"summary\":{");
            jsonBuilder.append("\"total_executions\":").append(metricsData.totalExecutions).append(",");
            jsonBuilder.append("\"success_rate\":").append(String.format("%.2f", metricsData.successRate)).append(",");
            jsonBuilder.append("\"avg_duration_ms\":").append(Math.round(metricsData.overallAvgDuration)).append(",");
            jsonBuilder.append("\"total_records\":").append(metricsData.totalRecords);
            jsonBuilder.append("}");

            jsonBuilder.append("}");

            // Send successful response
            String successJson = buildSuccessJson(jsonBuilder.toString());
            sendJsonResponse(response, successJson);

        } catch (SQLException e) {
            log("Database error while fetching metrics data", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                ErrorCode.DB001, "Failed to retrieve metrics data. Please try again.");
        }
    }

    /**
     * Retrieves metrics data from the database and organizes it for Chart.js
     *
     * @param conn Database connection
     * @param companyId Company ID filter
     * @param flowName Flow name filter
     * @param projectId Project ID filter
     * @param granularity Time granularity (hourly, daily, weekly)
     * @param periodHours Number of hours to look back
     * @return MetricsData object with time-series data
     * @throws SQLException If database error occurs
     */
    private MetricsData getMetricsData(Connection conn, Integer companyId, String flowName,
                                      Integer projectId, String granularity, int periodHours)
            throws SQLException {

        // Calculate the start time based on period
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -periodHours);
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());

        // Determine number of data points and time format based on granularity
        int dataPoints = calculateDataPoints(granularity, periodHours);
        String timeFormat = getTimeFormat(granularity);
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);

        // Initialize data arrays
        String[] labels = new String[dataPoints];
        int[] successCounts = new int[dataPoints];
        int[] failedCounts = new int[dataPoints];
        int[] avgDurations = new int[dataPoints];
        long[] recordsProcessed = new long[dataPoints];

        // Build SQL query to aggregate metrics by time period
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getTimeBucketExpression(granularity)).append(" AS time_bucket, ");
        sql.append("COUNT(CASE WHEN status = 'success' THEN 1 END) AS success_count, ");
        sql.append("COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed_count, ");
        sql.append("AVG(CASE WHEN status = 'success' THEN duration_ms END) AS avg_duration, ");
        sql.append("SUM(records_processed) AS total_records ");
        sql.append("FROM transaction_executions ");
        sql.append("WHERE started_at >= ? ");

        // Add filters
        if (companyId != null) {
            sql.append("AND company_id = ? ");
        }
        if (flowName != null) {
            sql.append("AND flow_name LIKE ? ");
        }
        if (projectId != null) {
            sql.append("AND project_id = ? ");
        }

        sql.append("GROUP BY time_bucket ");
        sql.append("ORDER BY time_bucket ASC");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stmt.setTimestamp(paramIndex++, startTime);

            if (companyId != null) {
                stmt.setInt(paramIndex++, companyId);
            }
            if (flowName != null) {
                stmt.setString(paramIndex++, "%" + flowName + "%");
            }
            if (projectId != null) {
                stmt.setInt(paramIndex++, projectId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                // Initialize all labels with proper time buckets
                calendar.setTimeInMillis(startTime.getTime());
                for (int i = 0; i < dataPoints; i++) {
                    labels[i] = dateFormat.format(calendar.getTime());
                    successCounts[i] = 0;
                    failedCounts[i] = 0;
                    avgDurations[i] = 0;
                    recordsProcessed[i] = 0;
                    advanceCalendar(calendar, granularity);
                }

                // Fill in actual data from database
                calendar.setTimeInMillis(startTime.getTime());
                int currentIndex = 0;
                while (rs.next()) {
                    Timestamp timeBucket = rs.getTimestamp("time_bucket");
                    String bucketLabel = dateFormat.format(timeBucket);

                    // Find the matching index in our labels array
                    for (int i = currentIndex; i < dataPoints; i++) {
                        if (labels[i].equals(bucketLabel)) {
                            successCounts[i] = rs.getInt("success_count");
                            failedCounts[i] = rs.getInt("failed_count");

                            Double avgDuration = rs.getDouble("avg_duration");
                            avgDurations[i] = avgDuration != null && !rs.wasNull() ?
                                            (int) Math.round(avgDuration) : 0;

                            recordsProcessed[i] = rs.getLong("total_records");
                            currentIndex = i;
                            break;
                        }
                    }
                }
            }
        }

        // Calculate summary statistics
        int totalExecutions = 0;
        int totalSuccess = 0;
        int totalFailed = 0;
        long totalDuration = 0;
        int durationCount = 0;
        long totalRecordsSum = 0;

        for (int i = 0; i < dataPoints; i++) {
            totalSuccess += successCounts[i];
            totalFailed += failedCounts[i];
            if (avgDurations[i] > 0) {
                totalDuration += avgDurations[i] * (successCounts[i] + failedCounts[i]);
                durationCount += (successCounts[i] + failedCounts[i]);
            }
            totalRecordsSum += recordsProcessed[i];
        }

        totalExecutions = totalSuccess + totalFailed;
        double successRate = totalExecutions > 0 ? (totalSuccess * 100.0) / totalExecutions : 0.0;
        double overallAvgDuration = durationCount > 0 ? (double) totalDuration / durationCount : 0.0;

        // Build and return metrics data
        MetricsData data = new MetricsData();
        data.labels = labels;
        data.successCounts = successCounts;
        data.failedCounts = failedCounts;
        data.avgDurations = avgDurations;
        data.recordsProcessed = recordsProcessed;
        data.totalExecutions = totalExecutions;
        data.successRate = successRate;
        data.overallAvgDuration = overallAvgDuration;
        data.totalRecords = totalRecordsSum;

        return data;
    }

    /**
     * Returns the SQL expression for time bucketing based on granularity
     *
     * @param granularity Time granularity (hourly, daily, weekly)
     * @return SQL expression for time bucket
     */
    private String getTimeBucketExpression(String granularity) {
        switch (granularity.toLowerCase()) {
            case "hourly":
                return "DATE_FORMAT(started_at, '%Y-%m-%d %H:00:00')";
            case "weekly":
                return "DATE_FORMAT(DATE_SUB(started_at, INTERVAL WEEKDAY(started_at) DAY), '%Y-%m-%d')";
            case "daily":
            default:
                return "DATE_FORMAT(started_at, '%Y-%m-%d')";
        }
    }

    /**
     * Returns the time format string for labels based on granularity
     *
     * @param granularity Time granularity
     * @return SimpleDateFormat pattern string
     */
    private String getTimeFormat(String granularity) {
        switch (granularity.toLowerCase()) {
            case "hourly":
                return "yyyy-MM-dd HH:00";
            case "weekly":
                return "yyyy-MM-dd";
            case "daily":
            default:
                return "yyyy-MM-dd";
        }
    }

    /**
     * Calculates the number of data points based on granularity and period
     *
     * @param granularity Time granularity
     * @param periodHours Number of hours in period
     * @return Number of data points
     */
    private int calculateDataPoints(String granularity, int periodHours) {
        switch (granularity.toLowerCase()) {
            case "hourly":
                return periodHours;
            case "weekly":
                return (periodHours / 24 / 7) + 1;
            case "daily":
            default:
                return (periodHours / 24) + 1;
        }
    }

    /**
     * Advances the calendar by one time bucket based on granularity
     *
     * @param calendar Calendar to advance
     * @param granularity Time granularity
     */
    private void advanceCalendar(Calendar calendar, String granularity) {
        switch (granularity.toLowerCase()) {
            case "hourly":
                calendar.add(Calendar.HOUR, 1);
                break;
            case "weekly":
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case "daily":
            default:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
        }
    }

    /**
     * Parses and validates company_id filter with security checks
     *
     * @param request HTTP request
     * @param userCompanyId User's company ID
     * @param isAdmin Whether user is admin
     * @param response HTTP response for error handling
     * @return Company ID to filter by, or null if validation failed (error response sent)
     * @throws IOException If I/O error occurs
     */
    private Integer parseCompanyIdFilter(HttpServletRequest request, Integer userCompanyId,
                                         boolean isAdmin, HttpServletResponse response)
            throws IOException {

        String companyIdParam = getStringParameter(request, "company_id", null);
        if (companyIdParam != null && !companyIdParam.isEmpty()) {
            try {
                Integer filterCompanyId = Integer.parseInt(companyIdParam);

                // Security check: non-admin users can only view their own company data
                if (!isAdmin && !filterCompanyId.equals(userCompanyId)) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                        ErrorCode.AUTH005, "You are not authorized to view data for other companies");
                    return null;
                }

                return filterCompanyId;
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid company_id parameter. Must be a valid integer.");
                return null;
            }
        } else {
            // Default to user's company if not specified
            return userCompanyId;
        }
    }

    /**
     * Validates granularity parameter value
     *
     * @param granularity Granularity value to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidGranularity(String granularity) {
        return "hourly".equalsIgnoreCase(granularity) ||
               "daily".equalsIgnoreCase(granularity) ||
               "weekly".equalsIgnoreCase(granularity);
    }

    /**
     * Parses period parameter and returns number of hours
     *
     * @param period Period string (24h, 7d, 30d, 90d)
     * @return Number of hours, or -1 if invalid
     */
    private int parsePeriod(String period) {
        if (period == null || period.isEmpty()) {
            return -1;
        }

        period = period.toLowerCase().trim();

        if (period.equals("24h")) {
            return 24;
        } else if (period.equals("7d")) {
            return 24 * 7;
        } else if (period.equals("30d")) {
            return 24 * 30;
        } else if (period.equals("90d")) {
            return 24 * 90;
        }

        return -1;
    }

    /**
     * Internal class to hold metrics data for response building
     */
    private static class MetricsData {
        String[] labels;
        int[] successCounts;
        int[] failedCounts;
        int[] avgDurations;
        long[] recordsProcessed;
        int totalExecutions;
        double successRate;
        double overallAvgDuration;
        long totalRecords;
    }
}
