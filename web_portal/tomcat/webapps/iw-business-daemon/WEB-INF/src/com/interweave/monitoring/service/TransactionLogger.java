package com.interweave.monitoring.service;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * TransactionLogger - Service class for logging detailed transaction execution data.
 *
 * This service captures and persists comprehensive transaction execution information including:
 * - Transaction start, progress, completion, and failure events
 * - Processing metrics (records processed, failed, skipped)
 * - Error details and stack traces for failed transactions
 * - Request/response payloads for drill-down analysis
 *
 * The logger is thread-safe and designed to handle concurrent transaction logging
 * without impacting transaction performance. All database operations use connection
 * pooling via JNDI DataSource for efficient resource management.
 *
 * Usage Example:
 * <pre>
 * TransactionLogger logger = TransactionLogger.getInstance();
 * String executionId = logger.logStart(companyId, projectId, transformationId,
 *                                      "Customer Order Sync", "transaction", "scheduler", userId);
 *
 * // During processing
 * logger.logProgress(executionId, 150, 2);
 *
 * // On completion
 * logger.logComplete(executionId, 200, 3, 0);
 *
 * // Or on failure
 * logger.logFailure(executionId, "Connection timeout", "TIMEOUT_ERROR", stackTrace);
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class TransactionLogger {

    private static TransactionLogger instance;
    private DataSource dataSource;
    private String serverHostname;

    // Thread-safe singleton instance
    private static final Object instanceLock = new Object();

    /**
     * Private constructor for singleton pattern.
     * Initializes JNDI DataSource and captures server hostname.
     */
    private TransactionLogger() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");

            // Capture server hostname for execution context
            try {
                serverHostname = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                serverHostname = "unknown";
            }

            log("TransactionLogger initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            logError("Failed to initialize TransactionLogger DataSource", e);
            throw new RuntimeException("Cannot initialize TransactionLogger - database connection unavailable", e);
        }
    }

    /**
     * Gets the singleton instance of TransactionLogger.
     * Thread-safe lazy initialization.
     *
     * @return The TransactionLogger instance
     */
    public static TransactionLogger getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new TransactionLogger();
                }
            }
        }
        return instance;
    }

    /**
     * Logs the start of a transaction execution.
     * Creates a new record in transaction_executions table with 'running' status.
     *
     * @param companyId The company that owns this transaction (can be null)
     * @param projectId The project containing the transaction flow (can be null)
     * @param transformationId The transformation/flow being executed (can be null)
     * @param flowName Name of the transaction flow (required)
     * @param flowType Type of flow: 'transaction', 'utility', or 'query' (required)
     * @param triggeredBy How execution was triggered: 'scheduler', 'manual', 'webhook', or 'api' (required)
     * @param triggeredByUserId User who triggered manual execution (can be null)
     * @return The generated execution ID, or null if logging failed
     */
    public String logStart(Integer companyId, Integer projectId, Integer transformationId,
                          String flowName, String flowType, String triggeredBy, Integer triggeredByUserId) {

        if (flowName == null || flowName.trim().isEmpty()) {
            logError("Cannot log transaction start - flowName is required", null);
            return null;
        }

        if (flowType == null || flowType.trim().isEmpty()) {
            flowType = "transaction";
        }

        if (triggeredBy == null || triggeredBy.trim().isEmpty()) {
            triggeredBy = "scheduler";
        }

        // Generate unique execution ID
        String executionId = generateExecutionId(flowName);

        String sql =
            "INSERT INTO transaction_executions " +
            "(execution_id, company_id, project_id, transformation_id, flow_name, flow_type, " +
            " status, started_at, triggered_by, triggered_by_user_id, server_hostname, records_processed, records_failed, records_skipped) " +
            "VALUES (?, ?, ?, ?, ?, ?, 'running', CURRENT_TIMESTAMP, ?, ?, ?, 0, 0, 0)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, executionId);
            setIntOrNull(stmt, 2, companyId);
            setIntOrNull(stmt, 3, projectId);
            setIntOrNull(stmt, 4, transformationId);
            stmt.setString(5, flowName);
            stmt.setString(6, flowType);
            stmt.setString(7, triggeredBy);
            setIntOrNull(stmt, 8, triggeredByUserId);
            stmt.setString(9, serverHostname);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                log("Transaction started: " + executionId + " (" + flowName + ")");
                return executionId;
            } else {
                logError("Failed to insert transaction start record for: " + flowName, null);
                return null;
            }

        } catch (SQLException e) {
            logError("Database error logging transaction start: " + flowName, e);
            return null;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Logs progress update for a running transaction.
     * Updates the records_processed and records_failed counts.
     * Thread-safe - can be called multiple times during execution.
     *
     * @param executionId The execution ID returned from logStart()
     * @param recordsProcessed Total number of records processed so far
     * @param recordsFailed Total number of records failed so far
     * @return true if update successful, false otherwise
     */
    public boolean logProgress(String executionId, int recordsProcessed, int recordsFailed) {

        if (executionId == null || executionId.trim().isEmpty()) {
            logError("Cannot log progress - executionId is required", null);
            return false;
        }

        String sql =
            "UPDATE transaction_executions " +
            "SET records_processed = ?, records_failed = ?, updated_at = CURRENT_TIMESTAMP " +
            "WHERE execution_id = ? AND status = 'running'";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, recordsProcessed);
            stmt.setInt(2, recordsFailed);
            stmt.setString(3, executionId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            logError("Database error logging progress for execution: " + executionId, e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Logs successful completion of a transaction.
     * Updates status to 'success', sets completion time, calculates duration,
     * and records final processing metrics.
     *
     * @param executionId The execution ID returned from logStart()
     * @param recordsProcessed Total number of records successfully processed
     * @param recordsFailed Total number of records that failed
     * @param recordsSkipped Total number of records skipped
     * @return true if update successful, false otherwise
     */
    public boolean logComplete(String executionId, int recordsProcessed, int recordsFailed, int recordsSkipped) {

        if (executionId == null || executionId.trim().isEmpty()) {
            logError("Cannot log completion - executionId is required", null);
            return false;
        }

        String sql =
            "UPDATE transaction_executions " +
            "SET status = 'success', " +
            "    completed_at = CURRENT_TIMESTAMP, " +
            "    duration_ms = CAST(EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - started_at)) * 1000 AS INTEGER), " +
            "    records_processed = ?, " +
            "    records_failed = ?, " +
            "    records_skipped = ?, " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE execution_id = ? AND status = 'running'";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, recordsProcessed);
            stmt.setInt(2, recordsFailed);
            stmt.setInt(3, recordsSkipped);
            stmt.setString(4, executionId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                log("Transaction completed: " + executionId + " (processed: " + recordsProcessed +
                    ", failed: " + recordsFailed + ", skipped: " + recordsSkipped + ")");
                return true;
            } else {
                logError("Failed to update transaction completion for: " + executionId, null);
                return false;
            }

        } catch (SQLException e) {
            logError("Database error logging completion for execution: " + executionId, e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Logs transaction failure with error details.
     * Updates status to 'failed', sets completion time, and captures error information
     * including error message, error code, and full stack trace for debugging.
     *
     * @param executionId The execution ID returned from logStart()
     * @param errorMessage Primary error message describing the failure
     * @param errorCode Error code or exception class name
     * @param stackTrace Full stack trace for debugging (can be null)
     * @return true if update successful, false otherwise
     */
    public boolean logFailure(String executionId, String errorMessage, String errorCode, String stackTrace) {

        if (executionId == null || executionId.trim().isEmpty()) {
            logError("Cannot log failure - executionId is required", null);
            return false;
        }

        // Truncate very long error messages to prevent database issues
        if (errorMessage != null && errorMessage.length() > 5000) {
            errorMessage = errorMessage.substring(0, 5000) + "... (truncated)";
        }

        if (stackTrace != null && stackTrace.length() > 10000) {
            stackTrace = stackTrace.substring(0, 10000) + "... (truncated)";
        }

        String sql =
            "UPDATE transaction_executions " +
            "SET status = 'failed', " +
            "    completed_at = CURRENT_TIMESTAMP, " +
            "    duration_ms = CAST(EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - started_at)) * 1000 AS INTEGER), " +
            "    error_message = ?, " +
            "    error_code = ?, " +
            "    stack_trace = ?, " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE execution_id = ? AND status = 'running'";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, errorMessage);
            stmt.setString(2, errorCode);
            stmt.setString(3, stackTrace);
            stmt.setString(4, executionId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                log("Transaction failed: " + executionId + " - " + errorMessage);
                return true;
            } else {
                logError("Failed to update transaction failure for: " + executionId, null);
                return false;
            }

        } catch (SQLException e) {
            logError("Database error logging failure for execution: " + executionId, e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Logs timeout for a transaction that exceeded maximum execution time.
     * Similar to logFailure but sets status to 'timeout'.
     *
     * @param executionId The execution ID returned from logStart()
     * @param timeoutMessage Description of the timeout
     * @return true if update successful, false otherwise
     */
    public boolean logTimeout(String executionId, String timeoutMessage) {

        if (executionId == null || executionId.trim().isEmpty()) {
            logError("Cannot log timeout - executionId is required", null);
            return false;
        }

        String sql =
            "UPDATE transaction_executions " +
            "SET status = 'timeout', " +
            "    completed_at = CURRENT_TIMESTAMP, " +
            "    duration_ms = CAST(EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - started_at)) * 1000 AS INTEGER), " +
            "    error_message = ?, " +
            "    error_code = 'TIMEOUT', " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE execution_id = ? AND status = 'running'";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, timeoutMessage);
            stmt.setString(2, executionId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                log("Transaction timeout: " + executionId + " - " + timeoutMessage);
                return true;
            }
            return false;

        } catch (SQLException e) {
            logError("Database error logging timeout for execution: " + executionId, e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Logs request or response payload data for drill-down analysis.
     * Stores payload in separate transaction_payloads table to keep main execution table lean.
     *
     * @param executionId The execution ID returned from logStart()
     * @param payloadType Type of payload: 'request', 'response', 'error_context', or 'debug'
     * @param payloadFormat Format of payload: 'xml', 'json', 'text', or 'binary'
     * @param payloadData The actual payload content (will be truncated if too large)
     * @param sourceSystem System that generated this payload (can be null)
     * @param destinationSystem Target system for this payload (can be null)
     * @return true if payload logged successfully, false otherwise
     */
    public boolean logPayload(String executionId, String payloadType, String payloadFormat,
                             String payloadData, String sourceSystem, String destinationSystem) {

        if (executionId == null || executionId.trim().isEmpty()) {
            logError("Cannot log payload - executionId is required", null);
            return false;
        }

        if (payloadType == null || payloadType.trim().isEmpty()) {
            payloadType = "debug";
        }

        if (payloadFormat == null || payloadFormat.trim().isEmpty()) {
            payloadFormat = "text";
        }

        // Check max payload size setting (default 10MB)
        int maxPayloadSizeMB = 10;
        int maxPayloadBytes = maxPayloadSizeMB * 1024 * 1024;

        if (payloadData != null && payloadData.length() > maxPayloadBytes) {
            payloadData = payloadData.substring(0, maxPayloadBytes) + "\n\n... (payload truncated - exceeded " + maxPayloadSizeMB + "MB limit)";
        }

        // Get the internal execution ID from execution_id string
        Long internalExecutionId = getInternalExecutionId(executionId);
        if (internalExecutionId == null) {
            logError("Cannot log payload - execution not found: " + executionId, null);
            return false;
        }

        String sql =
            "INSERT INTO transaction_payloads " +
            "(execution_id, payload_type, payload_format, payload_data, payload_size, " +
            " captured_at, source_system, destination_system) " +
            "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, internalExecutionId);
            stmt.setString(2, payloadType);
            stmt.setString(3, payloadFormat);
            stmt.setString(4, payloadData);
            stmt.setInt(5, payloadData != null ? payloadData.length() : 0);
            stmt.setString(6, sourceSystem);
            stmt.setString(7, destinationSystem);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                log("Payload logged for execution: " + executionId + " (" + payloadType + ", " +
                    (payloadData != null ? payloadData.length() : 0) + " bytes)");
                return true;
            }
            return false;

        } catch (SQLException e) {
            logError("Database error logging payload for execution: " + executionId, e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Helper method to get internal database ID from execution_id string.
     *
     * @param executionId The execution ID string
     * @return The internal database ID, or null if not found
     */
    private Long getInternalExecutionId(String executionId) {
        String sql = "SELECT id FROM transaction_executions WHERE execution_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, executionId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;

        } catch (SQLException e) {
            logError("Database error getting internal execution ID: " + executionId, e);
            return null;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Generates a unique execution ID based on flow name and timestamp.
     * Format: flowname-yyyyMMddHHmmss-randomnumber
     *
     * @param flowName Name of the transaction flow
     * @return Generated execution ID
     */
    private String generateExecutionId(String flowName) {
        // Sanitize flow name for use in ID
        String sanitizedName = flowName.replaceAll("[^a-zA-Z0-9]", "");
        if (sanitizedName.length() > 30) {
            sanitizedName = sanitizedName.substring(0, 30);
        }

        // Generate timestamp
        String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

        // Add random number for uniqueness
        int random = (int)(Math.random() * 10000);

        return sanitizedName + "-" + timestamp + "-" + random;
    }

    /**
     * Helper method to set integer parameter or NULL in PreparedStatement.
     *
     * @param stmt PreparedStatement to set parameter on
     * @param index Parameter index (1-based)
     * @param value Integer value (can be null)
     * @throws SQLException If database error occurs
     */
    private void setIntOrNull(PreparedStatement stmt, int index, Integer value) throws SQLException {
        if (value != null) {
            stmt.setInt(index, value);
        } else {
            stmt.setNull(index, java.sql.Types.INTEGER);
        }
    }

    /**
     * Safely closes a ResultSet without throwing exceptions.
     *
     * @param rs ResultSet to close
     */
    private void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    /**
     * Safely closes a PreparedStatement without throwing exceptions.
     *
     * @param stmt PreparedStatement to close
     */
    private void closeQuietly(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    /**
     * Safely closes a Connection without throwing exceptions.
     *
     * @param conn Connection to close
     */
    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    /**
     * Logs informational message.
     *
     * @param message Message to log
     */
    private void log(String message) {
        System.out.println("[TransactionLogger] " + message);
    }

    /**
     * Logs error message with optional exception.
     * Uses IWError framework for structured error logging.
     *
     * @param message Error message
     * @param e Exception that occurred (can be null)
     */
    private void logError(String message, Exception e) {
        System.err.println("[TransactionLogger ERROR] " + message);
        if (e != null) {
            e.printStackTrace();

        }
    }
}
