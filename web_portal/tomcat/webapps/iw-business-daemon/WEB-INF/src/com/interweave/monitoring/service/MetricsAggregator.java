package com.interweave.monitoring.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * MetricsAggregator - Service that periodically aggregates raw execution data into hourly/daily metrics.
 *
 * This service runs on a configurable schedule (default: every hour) and performs the following tasks:
 * - Aggregates transaction_executions data into transaction_metrics table
 * - Computes hourly and daily rollups with success/failure counts and performance metrics
 * - Handles partial periods correctly (only aggregates complete periods)
 * - Cleans up old raw data based on retention policy from settings table
 *
 * The aggregator is thread-safe and designed to run as a background task without impacting
 * transaction performance. It uses efficient SQL queries to minimize database load.
 *
 * Usage Example:
 * <pre>
 * // Start the aggregator when application initializes
 * MetricsAggregator aggregator = MetricsAggregator.getInstance();
 * aggregator.start();
 *
 * // Optionally trigger manual aggregation
 * aggregator.aggregateNow();
 *
 * // Stop when application shuts down
 * aggregator.stop();
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class MetricsAggregator {

    private static MetricsAggregator instance;
    private DataSource dataSource;
    private Timer schedulerTimer;
    private boolean isRunning = false;

    // Default configuration
    private static final long DEFAULT_INTERVAL_MINUTES = 60; // Run every hour
    private static final int DEFAULT_RETENTION_DAYS = 90;
    private static final int DEFAULT_METRICS_RETENTION_DAYS = 365;

    // Thread-safe singleton instance
    private static final Object instanceLock = new Object();

    /**
     * Private constructor for singleton pattern.
     * Initializes JNDI DataSource.
     */
    private MetricsAggregator() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");

            log("MetricsAggregator initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            logError("Failed to initialize MetricsAggregator DataSource", e);
            throw new RuntimeException("Cannot initialize MetricsAggregator - database connection unavailable", e);
        }
    }

    /**
     * Gets the singleton instance of MetricsAggregator.
     * Thread-safe lazy initialization.
     *
     * @return The MetricsAggregator instance
     */
    public static MetricsAggregator getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new MetricsAggregator();
                }
            }
        }
        return instance;
    }

    /**
     * Starts the metrics aggregation scheduler.
     * Runs aggregation on a configurable interval (default: every hour).
     * Safe to call multiple times - will not start duplicate schedulers.
     */
    public synchronized void start() {
        if (isRunning) {
            log("MetricsAggregator already running - ignoring start request");
            return;
        }

        // Check if metrics aggregation is enabled
        if (!isAggregationEnabled()) {
            log("Metrics aggregation is disabled in settings - not starting scheduler");
            return;
        }

        long intervalMinutes = DEFAULT_INTERVAL_MINUTES;
        long intervalMillis = TimeUnit.MINUTES.toMillis(intervalMinutes);

        schedulerTimer = new Timer("MetricsAggregator-Scheduler", true);

        // Schedule aggregation task
        schedulerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    aggregateNow();
                } catch (Exception e) {
                    logError("Unexpected error during scheduled aggregation", e);
                }
            }
        }, intervalMillis, intervalMillis); // First run after one interval, then repeat

        isRunning = true;
        log("MetricsAggregator started - running every " + intervalMinutes + " minutes");
    }

    /**
     * Stops the metrics aggregation scheduler.
     * Safe to call multiple times.
     */
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }

        if (schedulerTimer != null) {
            schedulerTimer.cancel();
            schedulerTimer = null;
        }

        isRunning = false;
        log("MetricsAggregator stopped");
    }

    /**
     * Checks if the aggregator is currently running.
     *
     * @return true if running, false otherwise
     */
    public synchronized boolean isRunning() {
        return isRunning;
    }

    /**
     * Triggers immediate aggregation of metrics.
     * Can be called manually to force aggregation outside of scheduled runs.
     * Thread-safe - can be called while scheduler is running.
     *
     * @return true if aggregation completed successfully, false if errors occurred
     */
    public boolean aggregateNow() {
        log("Starting manual metrics aggregation");

        boolean success = true;

        try {
            // Aggregate hourly metrics
            if (!aggregateHourlyMetrics()) {
                success = false;
            }

            // Aggregate daily metrics
            if (!aggregateDailyMetrics()) {
                success = false;
            }

            // Clean up old data
            if (!cleanupOldData()) {
                success = false;
            }

            if (success) {
                log("Metrics aggregation completed successfully");
            } else {
                log("Metrics aggregation completed with errors");
            }

        } catch (Exception e) {
            logError("Unexpected error during metrics aggregation", e);
            success = false;
        }

        return success;
    }

    /**
     * Aggregates hourly metrics from transaction_executions.
     * Only processes complete hours (not current hour).
            "ON CONFLICT (company_id, project_id, flow_name, metric_period, period_start) DO UPDATE SET " +
     *
     * @return true if aggregation successful, false otherwise
     */
    private boolean aggregateHourlyMetrics() {
        log("Aggregating hourly metrics...");

        String sql =
            "INSERT INTO transaction_metrics " +
            "(company_id, project_id, flow_name, metric_period, period_start, period_end, " +
            " total_executions, successful_executions, failed_executions, cancelled_executions, timeout_executions, " +
            " total_duration_ms, avg_duration_ms, min_duration_ms, max_duration_ms, " +
            " total_records_processed, total_records_failed, avg_records_per_execution, success_rate_percent, computed_at) " +
            "SELECT " +
            "  company_id, " +
            "  project_id, " +
            "  flow_name, " +
            "  'hourly' AS metric_period, " +
            "  date_trunc('hour', started_at) AS period_start, " +
            "  date_trunc('hour', started_at) + INTERVAL '1 hour' - INTERVAL '1 second' AS period_end, " +
            "  COUNT(*) AS total_executions, " +
            "  SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) AS successful_executions, " +
            "  SUM(CASE WHEN status = 'failed' THEN 1 ELSE 0 END) AS failed_executions, " +
            "  SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled_executions, " +
            "  SUM(CASE WHEN status = 'timeout' THEN 1 ELSE 0 END) AS timeout_executions, " +
            "  SUM(COALESCE(duration_ms, 0)) AS total_duration_ms, " +
            "  AVG(COALESCE(duration_ms, 0)) AS avg_duration_ms, " +
            "  MIN(duration_ms) AS min_duration_ms, " +
            "  MAX(duration_ms) AS max_duration_ms, " +
            "  SUM(COALESCE(records_processed, 0)) AS total_records_processed, " +
            "  SUM(COALESCE(records_failed, 0)) AS total_records_failed, " +
            "  AVG(COALESCE(records_processed, 0)) AS avg_records_per_execution, " +
            "  ROUND(SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS success_rate_percent, " +
            "  CURRENT_TIMESTAMP AS computed_at " +
            "FROM transaction_executions " +
            "WHERE started_at >= CURRENT_TIMESTAMP - INTERVAL '48 hours' " +  // Process last 48 hours
            "  AND started_at < date_trunc('hour', CURRENT_TIMESTAMP) " +  // Only complete hours
            "  AND status IN ('success', 'failed', 'cancelled', 'timeout') " +  // Exclude running
            "GROUP BY company_id, project_id, flow_name, date_trunc('hour', started_at) " +
            "ON CONFLICT (company_id, project_id, flow_name, metric_period, period_start) DO UPDATE SET " +
            "  total_executions = EXCLUDED.total_executions, " +
            "  successful_executions = EXCLUDED.successful_executions, " +
            "  failed_executions = EXCLUDED.failed_executions, " +
            "  cancelled_executions = EXCLUDED.cancelled_executions, " +
            "  timeout_executions = EXCLUDED.timeout_executions, " +
            "  total_duration_ms = EXCLUDED.total_duration_ms, " +
            "  avg_duration_ms = EXCLUDED.avg_duration_ms, " +
            "  min_duration_ms = EXCLUDED.min_duration_ms, " +
            "  max_duration_ms = EXCLUDED.max_duration_ms, " +
            "  total_records_processed = EXCLUDED.total_records_processed, " +
            "  total_records_failed = EXCLUDED.total_records_failed, " +
            "  avg_records_per_execution = EXCLUDED.avg_records_per_execution, " +
            "  success_rate_percent = EXCLUDED.success_rate_percent, " +
            "  computed_at = CURRENT_TIMESTAMP";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            int rowsAffected = stmt.executeUpdate();
            log("Hourly metrics aggregated: " + rowsAffected + " metric records inserted/updated");
            return true;

        } catch (SQLException e) {
            logError("Database error aggregating hourly metrics", e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Aggregates daily metrics from transaction_executions.
     * Only processes complete days (not today).
            "ON CONFLICT (company_id, project_id, flow_name, metric_period, period_start) DO UPDATE SET " +
     *
     * @return true if aggregation successful, false otherwise
     */
    private boolean aggregateDailyMetrics() {
        log("Aggregating daily metrics...");

        String sql =
            "INSERT INTO transaction_metrics " +
            "(company_id, project_id, flow_name, metric_period, period_start, period_end, " +
            " total_executions, successful_executions, failed_executions, cancelled_executions, timeout_executions, " +
            " total_duration_ms, avg_duration_ms, min_duration_ms, max_duration_ms, " +
            " total_records_processed, total_records_failed, avg_records_per_execution, success_rate_percent, computed_at) " +
            "SELECT " +
            "  company_id, " +
            "  project_id, " +
            "  flow_name, " +
            "  'daily' AS metric_period, " +
            "  date_trunc('day', started_at) AS period_start, " +
            "  date_trunc('day', started_at) + INTERVAL '1 day' - INTERVAL '1 second' AS period_end, " +
            "  COUNT(*) AS total_executions, " +
            "  SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) AS successful_executions, " +
            "  SUM(CASE WHEN status = 'failed' THEN 1 ELSE 0 END) AS failed_executions, " +
            "  SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled_executions, " +
            "  SUM(CASE WHEN status = 'timeout' THEN 1 ELSE 0 END) AS timeout_executions, " +
            "  SUM(COALESCE(duration_ms, 0)) AS total_duration_ms, " +
            "  AVG(COALESCE(duration_ms, 0)) AS avg_duration_ms, " +
            "  MIN(duration_ms) AS min_duration_ms, " +
            "  MAX(duration_ms) AS max_duration_ms, " +
            "  SUM(COALESCE(records_processed, 0)) AS total_records_processed, " +
            "  SUM(COALESCE(records_failed, 0)) AS total_records_failed, " +
            "  AVG(COALESCE(records_processed, 0)) AS avg_records_per_execution, " +
            "  ROUND(SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS success_rate_percent, " +
            "  CURRENT_TIMESTAMP AS computed_at " +
            "FROM transaction_executions " +
            "WHERE started_at >= CURRENT_DATE - INTERVAL '14 days' " +  // Process last 14 days
            "  AND started_at < CURRENT_DATE " +  // Only complete days (not today)
            "  AND status IN ('success', 'failed', 'cancelled', 'timeout') " +  // Exclude running
            "GROUP BY company_id, project_id, flow_name, date_trunc('day', started_at) " +
            "ON CONFLICT (company_id, project_id, flow_name, metric_period, period_start) DO UPDATE SET " +
            "  total_executions = EXCLUDED.total_executions, " +
            "  successful_executions = EXCLUDED.successful_executions, " +
            "  failed_executions = EXCLUDED.failed_executions, " +
            "  cancelled_executions = EXCLUDED.cancelled_executions, " +
            "  timeout_executions = EXCLUDED.timeout_executions, " +
            "  total_duration_ms = EXCLUDED.total_duration_ms, " +
            "  avg_duration_ms = EXCLUDED.avg_duration_ms, " +
            "  min_duration_ms = EXCLUDED.min_duration_ms, " +
            "  max_duration_ms = EXCLUDED.max_duration_ms, " +
            "  total_records_processed = EXCLUDED.total_records_processed, " +
            "  total_records_failed = EXCLUDED.total_records_failed, " +
            "  avg_records_per_execution = EXCLUDED.avg_records_per_execution, " +
            "  success_rate_percent = EXCLUDED.success_rate_percent, " +
            "  computed_at = CURRENT_TIMESTAMP";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            int rowsAffected = stmt.executeUpdate();
            log("Daily metrics aggregated: " + rowsAffected + " metric records inserted/updated");
            return true;

        } catch (SQLException e) {
            logError("Database error aggregating daily metrics", e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Cleans up old data based on retention policy from settings table.
     * Removes:
     * - Old transaction_executions records (default: 90 days)
     * - Old transaction_metrics records (default: 365 days)
     * - Old alert_history records (default: 180 days)
     * - Orphaned transaction_payloads (cascade delete handles this)
     *
     * @return true if cleanup successful, false otherwise
     */
    private boolean cleanupOldData() {
        log("Cleaning up old data...");

        boolean success = true;

        // Get retention settings
        int executionRetentionDays = getRetentionDays("monitoring_retention_days", DEFAULT_RETENTION_DAYS);
        int metricsRetentionDays = getRetentionDays("metrics_retention_days", DEFAULT_METRICS_RETENTION_DAYS);
        int alertRetentionDays = getRetentionDays("alert_history_retention_days", 180);

        // Clean up old executions
        if (!cleanupOldExecutions(executionRetentionDays)) {
            success = false;
        }

        // Clean up old metrics
        if (!cleanupOldMetrics(metricsRetentionDays)) {
            success = false;
        }

        // Clean up old alert history
        if (!cleanupOldAlerts(alertRetentionDays)) {
            success = false;
        }

        if (success) {
            log("Data cleanup completed successfully");
        } else {
            log("Data cleanup completed with errors");
        }

        return success;
    }

    /**
     * Cleans up old transaction_executions records.
     * Payloads are automatically deleted via foreign key cascade.
     *
     * @param retentionDays Number of days to retain
     * @return true if cleanup successful, false otherwise
     */
    private boolean cleanupOldExecutions(int retentionDays) {
        String sql = "DELETE FROM transaction_executions WHERE started_at < CURRENT_TIMESTAMP - (? * INTERVAL '1 day')";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retentionDays);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log("Cleaned up " + rowsDeleted + " old execution records (retention: " + retentionDays + " days)");
            }
            return true;

        } catch (SQLException e) {
            logError("Database error cleaning up old executions", e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Cleans up old transaction_metrics records.
     *
     * @param retentionDays Number of days to retain
     * @return true if cleanup successful, false otherwise
     */
    private boolean cleanupOldMetrics(int retentionDays) {
        String sql = "DELETE FROM transaction_metrics WHERE period_start < CURRENT_TIMESTAMP - (? * INTERVAL '1 day')";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retentionDays);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log("Cleaned up " + rowsDeleted + " old metric records (retention: " + retentionDays + " days)");
            }
            return true;

        } catch (SQLException e) {
            logError("Database error cleaning up old metrics", e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Cleans up old alert_history records.
     *
     * @param retentionDays Number of days to retain
     * @return true if cleanup successful, false otherwise
     */
    private boolean cleanupOldAlerts(int retentionDays) {
        String sql = "DELETE FROM alert_history WHERE created_at < CURRENT_TIMESTAMP - (? * INTERVAL '1 day')";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retentionDays);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log("Cleaned up " + rowsDeleted + " old alert records (retention: " + retentionDays + " days)");
            }
            return true;

        } catch (SQLException e) {
            logError("Database error cleaning up old alerts", e);
            return false;
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Checks if metrics aggregation is enabled in settings.
     *
     * @return true if enabled, false otherwise
     */
    private boolean isAggregationEnabled() {
        String sql = "SELECT setting_value FROM settings WHERE setting_key = 'metrics_aggregation_enabled'";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String value = rs.getString("setting_value");
                return "true".equalsIgnoreCase(value) || "1".equals(value);
            }

            // Default to true if setting not found
            return true;

        } catch (SQLException e) {
            logError("Database error checking aggregation enabled setting", e);
            // Default to true on error
            return true;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Gets retention days setting from database.
     *
     * @param settingKey The setting key to look up
     * @param defaultValue Default value if setting not found
     * @return The retention days value
     */
    private int getRetentionDays(String settingKey, int defaultValue) {
        String sql = "SELECT setting_value FROM settings WHERE setting_key = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, settingKey);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String value = rs.getString("setting_value");
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    log("Invalid retention days value for " + settingKey + ": " + value + " - using default: " + defaultValue);
                    return defaultValue;
                }
            }

            // Setting not found, use default
            return defaultValue;

        } catch (SQLException e) {
            logError("Database error getting retention days for " + settingKey, e);
            return defaultValue;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
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
        System.out.println("[MetricsAggregator] " + message);
    }

    /**
     * Logs error message with optional exception.
     * Uses IWError framework for structured error logging.
     *
     * @param message Error message
     * @param e Exception that occurred (can be null)
     */
    private void logError(String message, Exception e) {
        System.err.println("[MetricsAggregator ERROR] " + message);
        if (e != null) {
            e.printStackTrace();

        }
    }
}
