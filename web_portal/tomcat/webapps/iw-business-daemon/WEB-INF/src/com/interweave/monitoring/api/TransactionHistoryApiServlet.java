package com.interweave.monitoring.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * TransactionHistoryApiServlet - Provides transaction execution history with search and filtering.
 *
 * This servlet enables querying historical transaction executions with:
 * - Pagination support for large result sets
 * - Filtering by flow name, status, date range, and project
 * - Search by transaction ID or error message
 * - Detailed drill-down view including request/response payloads
 *
 * Supports multi-tenant filtering by company_id with security enforcement.
 *
 * API Endpoints:
 *
 * 1. GET /api/monitoring/transactions - Returns paginated list of transactions
 *
 * Query Parameters:
 * - page (optional): Page number, 1-based (default: 1)
 * - page_size (optional): Records per page, max 100 (default: 20)
 * - flow_name (optional): Filter by flow name (partial match supported)
 * - status (optional): Filter by status (running, success, failed, cancelled, timeout)
 * - project_id (optional): Filter by project ID
 * - start_date (optional): Filter from date (ISO format: yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss)
 * - end_date (optional): Filter to date (ISO format: yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss)
 * - search (optional): Search in execution_id or error_message
 * - company_id (optional): Filter by company (admin only)
 *
 * Response Format:
 * {
 *   "success": true,
 *   "data": {
 *     "transactions": [...],
 *     "pagination": {
 *       "page": 1,
 *       "page_size": 20,
 *       "total_count": 150,
 *       "total_pages": 8
 *     }
 *   }
 * }
 *
 * 2. GET /api/monitoring/transactions/{id} - Returns detailed execution with payloads
 *
 * Path Parameters:
 * - id: execution_id or numeric database ID
 *
 * Response Format:
 * {
 *   "success": true,
 *   "data": {
 *     "execution": {...},
 *     "payloads": [...]
 *   }
 * }
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class TransactionHistoryApiServlet extends MonitoringApiServlet {

    private static final long serialVersionUID = 1L;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        // Only support GET for transaction history
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "VALIDATION001", "Only GET method is supported for transaction history endpoint");
            return;
        }

        // Parse the request path to determine which endpoint was called
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List endpoint: GET /api/monitoring/transactions
            handleListTransactions(request, response, conn);
        } else {
            // Detail endpoint: GET /api/monitoring/transactions/{id}
            String transactionId = pathInfo.substring(1); // Remove leading slash
            handleGetTransactionDetail(request, response, conn, transactionId);
        }
    }

    /**
     * Handles the list transactions endpoint with filtering and pagination
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param conn Database connection
     * @throws ServletException If servlet error occurs
     * @throws IOException If I/O error occurs
     * @throws SQLException If database error occurs
     */
    private void handleListTransactions(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        // Get user context for security filtering
        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse and validate company_id filter
        Integer filterCompanyId = parseCompanyIdFilter(request, userCompanyId, isAdminUser, response);
        if (filterCompanyId == null && response.isCommitted()) {
            return; // Error response already sent
        }

        // Parse pagination parameters
        int page = getIntParameter(request, "page", 1);
        if (page < 1) {
            page = 1;
        }

        int pageSize = getIntParameter(request, "page_size", DEFAULT_PAGE_SIZE);
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        // Parse filter parameters
        String flowName = getStringParameter(request, "flow_name", null);
        String status = getStringParameter(request, "status", null);
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

        // Parse date range
        Timestamp startDate = parseDateParameter(request, "start_date", response);
        if (startDate == null && response.isCommitted()) {
            return; // Error response already sent
        }

        Timestamp endDate = parseDateParameter(request, "end_date", response);
        if (endDate == null && response.isCommitted()) {
            return; // Error response already sent
        }

        // Parse search parameter
        String search = getStringParameter(request, "search", null);

        // Validate status parameter
        if (status != null) {
            if (!isValidStatus(status)) {
                sendValidationError(response,
                    "Invalid status parameter. Must be one of: running, success, failed, cancelled, timeout");
                return;
            }
        }

        try {
            // Get total count for pagination
            int totalCount = getTotalCount(conn, filterCompanyId, flowName, status, projectId,
                                          startDate, endDate, search);

            // Calculate pagination values
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            int offset = (page - 1) * pageSize;

            // Get transactions for current page
            String transactionsJson = getTransactions(conn, filterCompanyId, flowName, status,
                                                     projectId, startDate, endDate, search,
                                                     pageSize, offset);

            // Build response
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("\"data\":{");
            jsonBuilder.append("\"transactions\":").append(transactionsJson).append(",");
            jsonBuilder.append("\"pagination\":{");
            jsonBuilder.append("\"page\":").append(page).append(",");
            jsonBuilder.append("\"page_size\":").append(pageSize).append(",");
            jsonBuilder.append("\"total_count\":").append(totalCount).append(",");
            jsonBuilder.append("\"total_pages\":").append(totalPages);
            jsonBuilder.append("}");
            jsonBuilder.append("}");

            // Send successful response
            String successJson = buildSuccessJson(jsonBuilder.toString());
            sendJsonResponse(response, successJson);

        } catch (SQLException e) {
            log("Database error while fetching transaction history", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "DB001", "Failed to retrieve transaction history. Please try again.");
        }
    }

    /**
     * Handles the transaction detail endpoint with payload data
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param conn Database connection
     * @param transactionId Transaction ID (execution_id or numeric ID)
     * @throws ServletException If servlet error occurs
     * @throws IOException If I/O error occurs
     * @throws SQLException If database error occurs
     */
    private void handleGetTransactionDetail(HttpServletRequest request, HttpServletResponse response,
                                           Connection conn, String transactionId)
            throws ServletException, IOException, SQLException {

        // Get user context for security filtering
        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        try {
            // Get transaction execution details
            String executionJson = getExecutionDetail(conn, transactionId, userCompanyId, isAdminUser);

            if (executionJson == null) {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    "VALIDATION001", "Transaction not found or you do not have permission to view it.");
                return;
            }

            // Get associated payloads
            String payloadsJson = getTransactionPayloads(conn, transactionId);

            // Build response
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("\"data\":{");
            jsonBuilder.append("\"execution\":").append(executionJson).append(",");
            jsonBuilder.append("\"payloads\":").append(payloadsJson);
            jsonBuilder.append("}");

            // Send successful response
            String successJson = buildSuccessJson(jsonBuilder.toString());
            sendJsonResponse(response, successJson);

        } catch (SQLException e) {
            log("Database error while fetching transaction detail", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "DB001", "Failed to retrieve transaction details. Please try again.");
        }
    }

    /**
     * Gets the total count of transactions matching the filters
     *
     * @param conn Database connection
     * @param companyId Company ID filter
     * @param flowName Flow name filter
     * @param status Status filter
     * @param projectId Project ID filter
     * @param startDate Start date filter
     * @param endDate End date filter
     * @param search Search term
     * @return Total count of matching transactions
     * @throws SQLException If database error occurs
     */
    private int getTotalCount(Connection conn, Integer companyId, String flowName, String status,
                             Integer projectId, Timestamp startDate, Timestamp endDate, String search)
            throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS total FROM transaction_executions WHERE 1=1 ");

        // Build WHERE clause
        StringBuilder whereClause = new StringBuilder();
        if (companyId != null) {
            whereClause.append(" AND company_id = ?");
        }
        if (flowName != null) {
            whereClause.append(" AND flow_name LIKE ?");
        }
        if (status != null) {
            whereClause.append(" AND status = ?");
        }
        if (projectId != null) {
            whereClause.append(" AND project_id = ?");
        }
        if (startDate != null) {
            whereClause.append(" AND started_at >= ?");
        }
        if (endDate != null) {
            whereClause.append(" AND started_at <= ?");
        }
        if (search != null) {
            whereClause.append(" AND (execution_id LIKE ? OR error_message LIKE ?)");
        }

        sql.append(whereClause);

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (companyId != null) {
                stmt.setInt(paramIndex++, companyId);
            }
            if (flowName != null) {
                stmt.setString(paramIndex++, "%" + flowName + "%");
            }
            if (status != null) {
                stmt.setString(paramIndex++, status);
            }
            if (projectId != null) {
                stmt.setInt(paramIndex++, projectId);
            }
            if (startDate != null) {
                stmt.setTimestamp(paramIndex++, startDate);
            }
            if (endDate != null) {
                stmt.setTimestamp(paramIndex++, endDate);
            }
            if (search != null) {
                String searchPattern = "%" + search + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }

        return 0;
    }

    /**
     * Gets paginated list of transactions matching the filters
     *
     * @param conn Database connection
     * @param companyId Company ID filter
     * @param flowName Flow name filter
     * @param status Status filter
     * @param projectId Project ID filter
     * @param startDate Start date filter
     * @param endDate End date filter
     * @param search Search term
     * @param limit Page size limit
     * @param offset Pagination offset
     * @return JSON array of transactions
     * @throws SQLException If database error occurs
     */
    private String getTransactions(Connection conn, Integer companyId, String flowName, String status,
                                  Integer projectId, Timestamp startDate, Timestamp endDate, String search,
                                  int limit, int offset)
            throws SQLException {

        StringBuilder json = new StringBuilder();
        json.append("[");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  te.id, ");
        sql.append("  te.execution_id, ");
        sql.append("  te.flow_name, ");
        sql.append("  te.flow_type, ");
        sql.append("  te.status, ");
        sql.append("  te.started_at, ");
        sql.append("  te.completed_at, ");
        sql.append("  te.duration_ms, ");
        sql.append("  te.records_processed, ");
        sql.append("  te.records_failed, ");
        sql.append("  te.records_skipped, ");
        sql.append("  te.error_message, ");
        sql.append("  te.error_code, ");
        sql.append("  te.triggered_by, ");
        sql.append("  p.name AS project_name, ");
        sql.append("  c.company_name ");
        sql.append("FROM transaction_executions te ");
        sql.append("LEFT JOIN projects p ON te.project_id = p.id ");
        sql.append("LEFT JOIN companies c ON te.company_id = c.id ");
        sql.append("WHERE 1=1 ");

        // Build WHERE clause (same as getTotalCount)
        StringBuilder whereClause = new StringBuilder();
        if (companyId != null) {
            whereClause.append(" AND te.company_id = ?");
        }
        if (flowName != null) {
            whereClause.append(" AND te.flow_name LIKE ?");
        }
        if (status != null) {
            whereClause.append(" AND te.status = ?");
        }
        if (projectId != null) {
            whereClause.append(" AND te.project_id = ?");
        }
        if (startDate != null) {
            whereClause.append(" AND te.started_at >= ?");
        }
        if (endDate != null) {
            whereClause.append(" AND te.started_at <= ?");
        }
        if (search != null) {
            whereClause.append(" AND (te.execution_id LIKE ? OR te.error_message LIKE ?)");
        }

        sql.append(whereClause);
        sql.append(" ORDER BY te.started_at DESC ");
        sql.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (companyId != null) {
                stmt.setInt(paramIndex++, companyId);
            }
            if (flowName != null) {
                stmt.setString(paramIndex++, "%" + flowName + "%");
            }
            if (status != null) {
                stmt.setString(paramIndex++, status);
            }
            if (projectId != null) {
                stmt.setInt(paramIndex++, projectId);
            }
            if (startDate != null) {
                stmt.setTimestamp(paramIndex++, startDate);
            }
            if (endDate != null) {
                stmt.setTimestamp(paramIndex++, endDate);
            }
            if (search != null) {
                String searchPattern = "%" + search + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            stmt.setInt(paramIndex++, limit);
            stmt.setInt(paramIndex++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append("{");
                    json.append("\"id\":").append(rs.getLong("id")).append(",");
                    json.append("\"execution_id\":\"").append(escapeJson(rs.getString("execution_id"))).append("\",");
                    json.append("\"flow_name\":\"").append(escapeJson(rs.getString("flow_name"))).append("\",");
                    json.append("\"flow_type\":\"").append(escapeJson(rs.getString("flow_type"))).append("\",");
                    json.append("\"status\":\"").append(escapeJson(rs.getString("status"))).append("\",");
                    json.append("\"started_at\":\"").append(rs.getTimestamp("started_at")).append("\",");

                    Timestamp completedAt = rs.getTimestamp("completed_at");
                    if (completedAt != null) {
                        json.append("\"completed_at\":\"").append(completedAt).append("\",");
                    } else {
                        json.append("\"completed_at\":null,");
                    }

                    json.append("\"duration_ms\":").append(rs.getInt("duration_ms")).append(",");
                    json.append("\"records_processed\":").append(rs.getInt("records_processed")).append(",");
                    json.append("\"records_failed\":").append(rs.getInt("records_failed")).append(",");
                    json.append("\"records_skipped\":").append(rs.getInt("records_skipped")).append(",");

                    String errorMessage = rs.getString("error_message");
                    if (errorMessage != null) {
                        json.append("\"error_message\":\"").append(escapeJson(errorMessage)).append("\",");
                    } else {
                        json.append("\"error_message\":null,");
                    }

                    String errorCode = rs.getString("error_code");
                    if (errorCode != null) {
                        json.append("\"error_code\":\"").append(escapeJson(errorCode)).append("\",");
                    } else {
                        json.append("\"error_code\":null,");
                    }

                    json.append("\"triggered_by\":\"").append(escapeJson(rs.getString("triggered_by"))).append("\",");

                    String projectName = rs.getString("project_name");
                    if (projectName != null) {
                        json.append("\"project_name\":\"").append(escapeJson(projectName)).append("\",");
                    } else {
                        json.append("\"project_name\":null,");
                    }

                    String companyName = rs.getString("company_name");
                    if (companyName != null) {
                        json.append("\"company_name\":\"").append(escapeJson(companyName)).append("\"");
                    } else {
                        json.append("\"company_name\":null");
                    }

                    json.append("}");
                }
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Gets detailed execution information for a specific transaction
     *
     * @param conn Database connection
     * @param transactionId Transaction ID (execution_id or numeric ID)
     * @param userCompanyId User's company ID for security check
     * @param isAdmin Whether user is admin
     * @return JSON object with execution details, or null if not found/not authorized
     * @throws SQLException If database error occurs
     */
    private String getExecutionDetail(Connection conn, String transactionId, Integer userCompanyId, boolean isAdmin)
            throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  te.*, ");
        sql.append("  p.name AS project_name, ");
        sql.append("  c.company_name, ");
        sql.append("  u.username AS triggered_by_username ");
        sql.append("FROM transaction_executions te ");
        sql.append("LEFT JOIN projects p ON te.project_id = p.id ");
        sql.append("LEFT JOIN companies c ON te.company_id = c.id ");
        sql.append("LEFT JOIN users u ON te.triggered_by_user_id = u.id ");
        sql.append("WHERE (te.execution_id = ? OR te.id = ?) ");

        // Security: non-admin users can only see their company's data
        if (!isAdmin) {
            sql.append("AND te.company_id = ? ");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setString(1, transactionId);

            // Try to parse as numeric ID
            try {
                long numericId = Long.parseLong(transactionId);
                stmt.setLong(2, numericId);
            } catch (NumberFormatException e) {
                stmt.setLong(2, -1); // Invalid numeric ID, won't match
            }

            if (!isAdmin) {
                stmt.setInt(3, userCompanyId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StringBuilder json = new StringBuilder();
                    json.append("{");
                    json.append("\"id\":").append(rs.getLong("id")).append(",");
                    json.append("\"execution_id\":\"").append(escapeJson(rs.getString("execution_id"))).append("\",");
                    json.append("\"company_id\":").append(rs.getInt("company_id")).append(",");
                    json.append("\"company_name\":\"").append(escapeJson(rs.getString("company_name"))).append("\",");

                    Integer projectId = rs.getInt("project_id");
                    if (!rs.wasNull()) {
                        json.append("\"project_id\":").append(projectId).append(",");
                    } else {
                        json.append("\"project_id\":null,");
                    }

                    String projectName = rs.getString("project_name");
                    if (projectName != null) {
                        json.append("\"project_name\":\"").append(escapeJson(projectName)).append("\",");
                    } else {
                        json.append("\"project_name\":null,");
                    }

                    Integer transformationId = rs.getInt("transformation_id");
                    if (!rs.wasNull()) {
                        json.append("\"transformation_id\":").append(transformationId).append(",");
                    } else {
                        json.append("\"transformation_id\":null,");
                    }

                    json.append("\"flow_name\":\"").append(escapeJson(rs.getString("flow_name"))).append("\",");
                    json.append("\"flow_type\":\"").append(escapeJson(rs.getString("flow_type"))).append("\",");
                    json.append("\"status\":\"").append(escapeJson(rs.getString("status"))).append("\",");
                    json.append("\"started_at\":\"").append(rs.getTimestamp("started_at")).append("\",");

                    Timestamp completedAt = rs.getTimestamp("completed_at");
                    if (completedAt != null) {
                        json.append("\"completed_at\":\"").append(completedAt).append("\",");
                    } else {
                        json.append("\"completed_at\":null,");
                    }

                    Integer durationMs = rs.getInt("duration_ms");
                    if (!rs.wasNull()) {
                        json.append("\"duration_ms\":").append(durationMs).append(",");
                    } else {
                        json.append("\"duration_ms\":null,");
                    }

                    json.append("\"records_processed\":").append(rs.getInt("records_processed")).append(",");
                    json.append("\"records_failed\":").append(rs.getInt("records_failed")).append(",");
                    json.append("\"records_skipped\":").append(rs.getInt("records_skipped")).append(",");

                    String errorMessage = rs.getString("error_message");
                    if (errorMessage != null) {
                        json.append("\"error_message\":\"").append(escapeJson(errorMessage)).append("\",");
                    } else {
                        json.append("\"error_message\":null,");
                    }

                    String errorCode = rs.getString("error_code");
                    if (errorCode != null) {
                        json.append("\"error_code\":\"").append(escapeJson(errorCode)).append("\",");
                    } else {
                        json.append("\"error_code\":null,");
                    }

                    String stackTrace = rs.getString("stack_trace");
                    if (stackTrace != null) {
                        json.append("\"stack_trace\":\"").append(escapeJson(stackTrace)).append("\",");
                    } else {
                        json.append("\"stack_trace\":null,");
                    }

                    json.append("\"triggered_by\":\"").append(escapeJson(rs.getString("triggered_by"))).append("\",");

                    Integer triggeredByUserId = rs.getInt("triggered_by_user_id");
                    if (!rs.wasNull()) {
                        json.append("\"triggered_by_user_id\":").append(triggeredByUserId).append(",");
                    } else {
                        json.append("\"triggered_by_user_id\":null,");
                    }

                    String triggeredByUsername = rs.getString("triggered_by_username");
                    if (triggeredByUsername != null) {
                        json.append("\"triggered_by_username\":\"").append(escapeJson(triggeredByUsername)).append("\",");
                    } else {
                        json.append("\"triggered_by_username\":null,");
                    }

                    String serverHostname = rs.getString("server_hostname");
                    if (serverHostname != null) {
                        json.append("\"server_hostname\":\"").append(escapeJson(serverHostname)).append("\",");
                    } else {
                        json.append("\"server_hostname\":null,");
                    }

                    json.append("\"created_at\":\"").append(rs.getTimestamp("created_at")).append("\",");
                    json.append("\"updated_at\":\"").append(rs.getTimestamp("updated_at")).append("\"");

                    json.append("}");
                    return json.toString();
                }
            }
        }

        return null; // Not found or not authorized
    }

    /**
     * Gets transaction payloads (request/response data) for a transaction
     *
     * @param conn Database connection
     * @param transactionId Transaction ID (execution_id or numeric ID)
     * @return JSON array of payloads
     * @throws SQLException If database error occurs
     */
    private String getTransactionPayloads(Connection conn, String transactionId) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("[");

        String sql =
            "SELECT " +
            "  tp.id, " +
            "  tp.payload_type, " +
            "  tp.payload_format, " +
            "  tp.payload_data, " +
            "  tp.payload_size, " +
            "  tp.captured_at, " +
            "  tp.source_system, " +
            "  tp.destination_system " +
            "FROM transaction_payloads tp " +
            "JOIN transaction_executions te ON tp.execution_id = te.id " +
            "WHERE (te.execution_id = ? OR te.id = ?) " +
            "ORDER BY tp.payload_type, tp.captured_at";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);

            // Try to parse as numeric ID
            try {
                long numericId = Long.parseLong(transactionId);
                stmt.setLong(2, numericId);
            } catch (NumberFormatException e) {
                stmt.setLong(2, -1); // Invalid numeric ID, won't match
            }

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append("{");
                    json.append("\"id\":").append(rs.getLong("id")).append(",");
                    json.append("\"payload_type\":\"").append(escapeJson(rs.getString("payload_type"))).append("\",");
                    json.append("\"payload_format\":\"").append(escapeJson(rs.getString("payload_format"))).append("\",");

                    String payloadData = rs.getString("payload_data");
                    if (payloadData != null) {
                        json.append("\"payload_data\":\"").append(escapeJson(payloadData)).append("\",");
                    } else {
                        json.append("\"payload_data\":null,");
                    }

                    Integer payloadSize = rs.getInt("payload_size");
                    if (!rs.wasNull()) {
                        json.append("\"payload_size\":").append(payloadSize).append(",");
                    } else {
                        json.append("\"payload_size\":null,");
                    }

                    json.append("\"captured_at\":\"").append(rs.getTimestamp("captured_at")).append("\",");

                    String sourceSystem = rs.getString("source_system");
                    if (sourceSystem != null) {
                        json.append("\"source_system\":\"").append(escapeJson(sourceSystem)).append("\",");
                    } else {
                        json.append("\"source_system\":null,");
                    }

                    String destinationSystem = rs.getString("destination_system");
                    if (destinationSystem != null) {
                        json.append("\"destination_system\":\"").append(escapeJson(destinationSystem)).append("\"");
                    } else {
                        json.append("\"destination_system\":null");
                    }

                    json.append("}");
                }
            }
        }

        json.append("]");
        return json.toString();
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
                        "AUTH005", "You are not authorized to view data for other companies");
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
     * Parses a date parameter from the request
     *
     * @param request HTTP request
     * @param paramName Parameter name
     * @param response HTTP response for error handling
     * @return Parsed timestamp, or null if not provided or invalid (error response sent)
     * @throws IOException If I/O error occurs
     */
    private Timestamp parseDateParameter(HttpServletRequest request, String paramName,
                                         HttpServletResponse response)
            throws IOException {

        String dateStr = getStringParameter(request, paramName, null);
        if (dateStr == null) {
            return null;
        }

        // Support two formats: yyyy-MM-dd and yyyy-MM-dd'T'HH:mm:ss
        SimpleDateFormat[] formats = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd")
        };

        for (SimpleDateFormat format : formats) {
            format.setLenient(false);
            try {
                java.util.Date parsedDate = format.parse(dateStr);
                return new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                // Try next format
            }
        }

        sendValidationError(response,
            "Invalid " + paramName + " parameter. Use format: yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss");
        return null;
    }

    /**
     * Validates status parameter value
     *
     * @param status Status value to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return "running".equalsIgnoreCase(status) ||
               "success".equalsIgnoreCase(status) ||
               "failed".equalsIgnoreCase(status) ||
               "cancelled".equalsIgnoreCase(status) ||
               "timeout".equalsIgnoreCase(status);
    }
}
