package com.interweave.monitoring.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * AlertService - Service that evaluates alert rules and triggers notifications on transaction failures.
 *
 * This service is responsible for:
 * - Evaluating configured alert rules when transactions fail
 * - Supporting immediate alerts (alert on first failure)
 * - Supporting threshold-based alerts (N failures in X minutes)
 * - Respecting alert cooldown periods to prevent spam
 * - Recording alert events in alert_history table
 * - Asynchronously dispatching notifications to avoid blocking transaction completion
 *
 * The service uses a thread pool for async notification dispatch and implements
 * proper cooldown and rate limiting logic to prevent alert storms.
 *
 * Usage Example:
 * <pre>
 * AlertService alertService = AlertService.getInstance();
 *
 * // When a transaction fails
 * alertService.evaluateAlerts(executionId, companyId, projectId, flowName,
 *                            "Connection timeout", "TIMEOUT_ERROR");
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class AlertService {

    private static AlertService instance;
    private DataSource dataSource;
    private ExecutorService executorService;

    // Thread-safe singleton instance
    private static final Object instanceLock = new Object();

    // Constants
    private static final int THREAD_POOL_SIZE = 5;

    /**
     * Private constructor for singleton pattern.
     * Initializes JNDI DataSource and creates thread pool for async dispatch.
     */
    private AlertService() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");

            // Create thread pool for async alert dispatch
            executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("AlertService-Worker-" + thread.getId());
                    thread.setDaemon(true); // Don't prevent JVM shutdown
                    return thread;
                });

            log("AlertService initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            logError("Failed to initialize AlertService DataSource", e);
            throw new RuntimeException("Cannot initialize AlertService - database connection unavailable", e);
        }
    }

    /**
     * Gets the singleton instance of AlertService.
     * Thread-safe lazy initialization.
     *
     * @return The AlertService instance
     */
    public static AlertService getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new AlertService();
                }
            }
        }
        return instance;
    }

    /**
     * Shuts down the alert service and thread pool.
     * Call this when the application is shutting down.
     */
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            log("Shutting down AlertService thread pool...");
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log("AlertService shutdown complete");
        }
    }

    /**
     * Evaluates alert rules for a failed transaction and triggers notifications asynchronously.
     * This is the main entry point called when a transaction fails.
     *
     * @param executionId The execution ID from transaction_executions table
     * @param companyId Company that owns the transaction
     * @param projectId Project containing the transaction flow (can be null)
     * @param flowName Name of the failed transaction flow
     * @param errorMessage Error message from the failure
     * @param errorCode Error code or exception type
     */
    public void evaluateAlerts(String executionId, Integer companyId, Integer projectId,
                              String flowName, String errorMessage, String errorCode) {

        if (executionId == null || companyId == null || flowName == null) {
            logError("Cannot evaluate alerts - executionId, companyId, and flowName are required", null);
            return;
        }

        // Submit to thread pool for async processing
        executorService.submit(() -> {
            try {
                evaluateAlertsSync(executionId, companyId, projectId, flowName, errorMessage, errorCode);
            } catch (Exception e) {
                logError("Unexpected error evaluating alerts for execution: " + executionId, e);
            }
        });
    }

    /**
     * Synchronous implementation of alert evaluation.
     * Finds matching alert rules and triggers notifications if conditions are met.
     */
    private void evaluateAlertsSync(String executionId, Integer companyId, Integer projectId,
                                   String flowName, String errorMessage, String errorCode) {

        log("Evaluating alerts for failed transaction: " + executionId + " (" + flowName + ")");

        // Get internal execution ID for foreign key
        Long internalExecutionId = getInternalExecutionId(executionId);
        if (internalExecutionId == null) {
            logError("Cannot evaluate alerts - execution not found: " + executionId, null);
            return;
        }

        // Find matching alert rules
        List<AlertRule> matchingRules = findMatchingAlertRules(companyId, projectId, flowName);

        if (matchingRules.isEmpty()) {
            log("No alert rules match this failure - skipping notification");
            return;
        }

        log("Found " + matchingRules.size() + " matching alert rules");

        // Evaluate each rule
        for (AlertRule rule : matchingRules) {
            try {
                evaluateAlertRule(rule, internalExecutionId, executionId, flowName, errorMessage, errorCode);
            } catch (Exception e) {
                logError("Error evaluating alert rule " + rule.id + ": " + rule.ruleName, e);
                // Continue with other rules
            }
        }
    }

    /**
     * Evaluates a single alert rule and triggers notification if conditions are met.
     */
    private void evaluateAlertRule(AlertRule rule, Long internalExecutionId, String executionId,
                                   String flowName, String errorMessage, String errorCode) {

        // Check if rule should fire based on threshold
        if (rule.failureThreshold > 1) {
            // Threshold-based alert - check failure count in time window
            int recentFailures = countRecentFailures(rule.companyId, rule.projectId, rule.flowName,
                                                     rule.thresholdWindowMinutes);

            if (recentFailures < rule.failureThreshold) {
                log("Alert rule '" + rule.ruleName + "' threshold not met (" +
                    recentFailures + "/" + rule.failureThreshold + " failures) - skipping");
                return;
            }

            log("Alert rule '" + rule.ruleName + "' threshold met (" +
                recentFailures + "/" + rule.failureThreshold + " failures)");
        }

        // Check cooldown period
        if (!isOutsideCooldown(rule)) {
            log("Alert rule '" + rule.ruleName + "' is in cooldown period - skipping");
            return;
        }

        // Check daily rate limit
        if (rule.alertsSentToday >= rule.maxAlertsPerDay) {
            log("Alert rule '" + rule.ruleName + "' has reached daily limit (" +
                rule.maxAlertsPerDay + ") - skipping");
            return;
        }

        // All conditions met - trigger alert
        log("Triggering alert for rule: " + rule.ruleName);
        triggerAlert(rule, internalExecutionId, executionId, flowName, errorMessage, errorCode);
    }

    /**
     * Triggers an alert notification by recording it in alert_history.
     * The actual email/webhook dispatch will be handled by notification services.
     */
    private void triggerAlert(AlertRule rule, Long internalExecutionId, String executionId,
                             String flowName, String errorMessage, String errorCode) {

        // Build alert message
        String subject = "Transaction Failure Alert: " + flowName;
        String message = buildAlertMessage(flowName, errorMessage, errorCode, executionId);

        // Trigger email notifications
        if ("email".equals(rule.notificationType) || "both".equals(rule.notificationType)) {
            if (rule.emailRecipients != null && !rule.emailRecipients.trim().isEmpty()) {
                String[] emails = rule.emailRecipients.split(",");
                for (String email : emails) {
                    email = email.trim();
                    if (!email.isEmpty()) {
                        recordAlert(rule.id, internalExecutionId, "email", email, subject, message, null);
                    }
                }
            }
        }

        // Trigger webhook notifications
        if ("webhook".equals(rule.notificationType) || "both".equals(rule.notificationType)) {
            if (rule.webhookEndpointIds != null && !rule.webhookEndpointIds.trim().isEmpty()) {
                String[] webhookIds = rule.webhookEndpointIds.split(",");
                for (String webhookId : webhookIds) {
                    webhookId = webhookId.trim();
                    if (!webhookId.isEmpty()) {
                        // Get webhook URL from webhook_endpoints table
                        String webhookUrl = getWebhookUrl(webhookId);
                        if (webhookUrl != null) {
                            String payload = buildWebhookPayload(flowName, errorMessage, errorCode, executionId);
                            recordAlert(rule.id, internalExecutionId, "webhook", webhookUrl, subject, message, payload);
                        }
                    }
                }
            }
        }

        // Update rule's last triggered timestamp and daily count
        updateAlertRuleAfterTrigger(rule.id);
    }

    /**
     * Records an alert in alert_history table.
     */
    private void recordAlert(int alertRuleId, Long executionId, String alertType, String recipient,
                            String subject, String message, String payload) {

        String sql =
            "INSERT INTO alert_history " +
            "(alert_rule_id, execution_id, alert_type, recipient, subject, message, payload_sent, " +
            " status, sent_at, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, 'pending', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, alertRuleId);
            if (executionId != null) {
                stmt.setLong(2, executionId);
            } else {
                stmt.setNull(2, java.sql.Types.BIGINT);
            }
            stmt.setString(3, alertType);
            stmt.setString(4, recipient);
            stmt.setString(5, subject);
            stmt.setString(6, message);
            stmt.setString(7, payload);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                log("Alert recorded: " + alertType + " to " + recipient);
            }

        } catch (SQLException e) {
            logError("Database error recording alert", e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Updates alert rule's last triggered timestamp and increments daily count.
     */
    private void updateAlertRuleAfterTrigger(int alertRuleId) {
        String sql =
            "UPDATE alert_rules " +
            "SET last_triggered_at = CURRENT_TIMESTAMP, " +
            "    alerts_sent_today = alerts_sent_today + 1, " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, alertRuleId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logError("Database error updating alert rule after trigger", e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Finds alert rules that match the failed transaction.
     * Returns rules that:
     * - Belong to the same company
     * - Are enabled
     * - Match the flow name (or are global with flow_name = null)
     * - Match the project (or are global with project_id = null)
     */
    private List<AlertRule> findMatchingAlertRules(Integer companyId, Integer projectId, String flowName) {
        List<AlertRule> rules = new ArrayList<>();

        String sql =
            "SELECT id, company_id, rule_name, description, project_id, flow_name, " +
            "       alert_on_failure, alert_on_timeout, failure_threshold, threshold_window_minutes, " +
            "       notification_type, email_recipients, webhook_endpoint_ids, " +
            "       cooldown_minutes, max_alerts_per_day, last_triggered_at, alerts_sent_today " +
            "FROM alert_rules " +
            "WHERE company_id = ? " +
            "  AND is_enabled = true " +
            "  AND alert_on_failure = true " +  // Currently only handling failures
            "  AND (project_id IS NULL OR project_id = ?) " +
            "  AND (flow_name IS NULL OR flow_name = ?) " +
            "ORDER BY flow_name DESC, project_id DESC";  // Specific rules first

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, companyId);
            if (projectId != null) {
                stmt.setInt(2, projectId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, flowName);

            rs = stmt.executeQuery();

            while (rs.next()) {
                AlertRule rule = new AlertRule();
                rule.id = rs.getInt("id");
                rule.companyId = rs.getInt("company_id");
                rule.ruleName = rs.getString("rule_name");
                rule.description = rs.getString("description");

                int projId = rs.getInt("project_id");
                rule.projectId = rs.wasNull() ? null : projId;

                rule.flowName = rs.getString("flow_name");
                rule.alertOnFailure = rs.getBoolean("alert_on_failure");
                rule.alertOnTimeout = rs.getBoolean("alert_on_timeout");
                rule.failureThreshold = rs.getInt("failure_threshold");
                rule.thresholdWindowMinutes = rs.getInt("threshold_window_minutes");
                rule.notificationType = rs.getString("notification_type");
                rule.emailRecipients = rs.getString("email_recipients");
                rule.webhookEndpointIds = rs.getString("webhook_endpoint_ids");
                rule.cooldownMinutes = rs.getInt("cooldown_minutes");
                rule.maxAlertsPerDay = rs.getInt("max_alerts_per_day");
                rule.lastTriggeredAt = rs.getTimestamp("last_triggered_at");
                rule.alertsSentToday = rs.getInt("alerts_sent_today");

                rules.add(rule);
            }

        } catch (SQLException e) {
            logError("Database error finding matching alert rules", e);
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }

        return rules;
    }

    /**
     * Counts recent failures for threshold-based alerting.
     * Returns the number of failed transactions in the specified time window.
     */
    private int countRecentFailures(Integer companyId, Integer projectId, String flowName,
                                    int windowMinutes) {

        String sql =
            "SELECT COUNT(*) AS failure_count " +
            "FROM transaction_executions " +
            "WHERE company_id = ? " +
            "  AND status IN ('failed', 'timeout') " +
            "  AND started_at >= CURRENT_TIMESTAMP - (? * INTERVAL '1 minute') " +
            "  AND (? IS NULL OR project_id = ?) " +
            "  AND (? IS NULL OR flow_name = ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, companyId);
            stmt.setInt(2, windowMinutes);

            if (projectId != null) {
                stmt.setInt(3, projectId);
                stmt.setInt(4, projectId);
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            if (flowName != null) {
                stmt.setString(5, flowName);
                stmt.setString(6, flowName);
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("failure_count");
            }

            return 0;

        } catch (SQLException e) {
            logError("Database error counting recent failures", e);
            return 0;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Checks if enough time has passed since last alert (cooldown period).
     */
    private boolean isOutsideCooldown(AlertRule rule) {
        if (rule.lastTriggeredAt == null) {
            return true; // Never triggered before
        }

        long minutesSinceLastAlert = (System.currentTimeMillis() - rule.lastTriggeredAt.getTime()) / (60 * 1000);
        return minutesSinceLastAlert >= rule.cooldownMinutes;
    }

    /**
     * Gets internal database ID from execution_id string.
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
     * Gets webhook URL from webhook_endpoints table by ID.
     */
    private String getWebhookUrl(String webhookId) {
        String sql = "SELECT endpoint_url FROM webhook_endpoints WHERE id = ? AND is_enabled = true";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(webhookId));
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("endpoint_url");
            }
            return null;

        } catch (NumberFormatException e) {
            logError("Invalid webhook ID: " + webhookId, e);
            return null;
        } catch (SQLException e) {
            logError("Database error getting webhook URL for ID: " + webhookId, e);
            return null;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Builds a human-readable alert message for email notifications.
     */
    private String buildAlertMessage(String flowName, String errorMessage, String errorCode, String executionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction Failure Detected\n\n");
        sb.append("Flow Name: ").append(flowName).append("\n");
        sb.append("Execution ID: ").append(executionId).append("\n");
        sb.append("Error Code: ").append(errorCode != null ? errorCode : "N/A").append("\n");
        sb.append("Error Message: ").append(errorMessage != null ? errorMessage : "No error message available").append("\n");
        sb.append("\n");
        sb.append("Please check the monitoring dashboard for more details.\n");
        return sb.toString();
    }

    /**
     * Builds a JSON payload for webhook notifications.
     */
    private String buildWebhookPayload(String flowName, String errorMessage, String errorCode, String executionId) {
        // Build simple JSON manually (no external dependencies)
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"event_type\":\"transaction_failure\",");
        json.append("\"flow_name\":\"").append(escapeJson(flowName)).append("\",");
        json.append("\"execution_id\":\"").append(escapeJson(executionId)).append("\",");
        json.append("\"error_code\":\"").append(escapeJson(errorCode != null ? errorCode : "")).append("\",");
        json.append("\"error_message\":\"").append(escapeJson(errorMessage != null ? errorMessage : "")).append("\",");
        json.append("\"timestamp\":\"").append(new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new java.util.Date())).append("\"");
        json.append("}");
        return json.toString();
    }

    /**
     * Escapes special characters for JSON.
     */
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    /**
     * Safely closes a ResultSet without throwing exceptions.
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
     */
    private void log(String message) {
        System.out.println("[AlertService] " + message);
    }

    /**
     * Logs error message with optional exception.
     * Uses IWError framework for structured error logging.
     */
    private void logError(String message, Exception e) {
        System.err.println("[AlertService ERROR] " + message);
        if (e != null) {
            e.printStackTrace();

        }
    }

    /**
     * Inner class to represent an alert rule.
     */
    private static class AlertRule {
        int id;
        int companyId;
        String ruleName;
        String description;
        Integer projectId;
        String flowName;
        boolean alertOnFailure;
        boolean alertOnTimeout;
        int failureThreshold;
        int thresholdWindowMinutes;
        String notificationType;
        String emailRecipients;
        String webhookEndpointIds;
        int cooldownMinutes;
        int maxAlertsPerDay;
        Timestamp lastTriggeredAt;
        int alertsSentToday;
    }
}
