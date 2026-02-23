package com.interweave.monitoring.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * WebhookNotificationService - Processes pending webhook alerts and dispatches them to external systems.
 *
 * This service is responsible for:
 * - Polling the alert_history table for pending webhook notifications
 * - Looking up webhook endpoint configuration from webhook_endpoints table
 * - POSTing JSON payloads to configured webhook URLs
 * - Supporting multiple authentication types (none, basic, bearer, custom headers)
 * - Handling webhook delivery failures gracefully with retry logic
 * - Updating webhook endpoint health metrics
 * - Updating alert delivery status in the database
 *
 * The service runs as a background thread that checks for pending alerts every 30 seconds.
 * Configuration is loaded from WEB-INF/monitoring.properties with fallback defaults.
 *
 * Supports integrations with:
 * - Slack (incoming webhooks)
 * - PagerDuty (events API)
 * - Microsoft Teams (connectors)
 * - Custom webhook endpoints
 *
 * Usage Example:
 * <pre>
 * WebhookNotificationService webhookService = WebhookNotificationService.getInstance();
 * webhookService.start();  // Begin processing pending webhooks
 * ...
 * webhookService.stop();   // Graceful shutdown on application exit
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class WebhookNotificationService {

    private static WebhookNotificationService instance;
    private DataSource dataSource;
    private Timer schedulerTimer;
    private boolean isRunning = false;
    private Properties webhookConfig;

    // Thread-safe singleton instance
    private static final Object instanceLock = new Object();

    // Constants
    private static final int POLLING_INTERVAL_MS = 30000; // 30 seconds
    private static final int MAX_BATCH_SIZE = 50; // Max webhooks to process per cycle
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;
    private static final int MAX_CONSECUTIVE_FAILURES_BEFORE_DISABLE = 10;

    /**
     * Private constructor for singleton pattern.
     * Initializes JNDI DataSource and loads webhook configuration.
     */
    private WebhookNotificationService() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");

            // Load webhook configuration
            loadWebhookConfiguration();

            log("WebhookNotificationService initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            logError("Failed to initialize WebhookNotificationService DataSource", e);
            throw new RuntimeException("Cannot initialize WebhookNotificationService - database connection unavailable", e);
        }
    }

    /**
     * Gets the singleton instance of WebhookNotificationService.
     * Thread-safe lazy initialization.
     *
     * @return The WebhookNotificationService instance
     */
    public static WebhookNotificationService getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new WebhookNotificationService();
                }
            }
        }
        return instance;
    }

    /**
     * Loads webhook configuration from monitoring.properties file.
     * Falls back to sensible defaults if file not found.
     */
    private void loadWebhookConfiguration() {
        webhookConfig = new Properties();

        try {
            // Try to load from WEB-INF/monitoring.properties
            InputStream is = getClass().getClassLoader().getResourceAsStream("monitoring.properties");
            if (is != null) {
                webhookConfig.load(is);
                log("Loaded webhook configuration from monitoring.properties");
                is.close();
            } else {
                log("monitoring.properties not found - using default webhook configuration");
                setDefaultWebhookConfiguration();
            }
        } catch (Exception e) {
            logError("Error loading monitoring.properties - using defaults", e);
            setDefaultWebhookConfiguration();
        }
    }

    /**
     * Sets default webhook configuration values.
     * These are reasonable defaults for testing - production should use monitoring.properties.
     */
    private void setDefaultWebhookConfiguration() {
        webhookConfig.setProperty("webhook.enabled", "true");
        webhookConfig.setProperty("webhook.default.timeout", String.valueOf(DEFAULT_TIMEOUT_SECONDS));
        webhookConfig.setProperty("webhook.max.consecutive.failures", String.valueOf(MAX_CONSECUTIVE_FAILURES_BEFORE_DISABLE));
        webhookConfig.setProperty("webhook.user.agent", "InterWeave-Monitoring/1.0");
    }

    /**
     * Starts the webhook notification service.
     * Begins polling for pending webhook alerts in the background.
     */
    public synchronized void start() {
        if (isRunning) {
            log("WebhookNotificationService already running - ignoring start request");
            return;
        }

        // Check if webhook notifications are enabled
        String webhookEnabled = webhookConfig.getProperty("webhook.enabled", "true");
        if (!"true".equalsIgnoreCase(webhookEnabled)) {
            log("Webhook notifications are disabled in configuration - service will not start");
            return;
        }

        log("Starting WebhookNotificationService...");

        schedulerTimer = new Timer("WebhookNotificationService-Scheduler", true);
        schedulerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    processPendingWebhooks();
                } catch (Exception e) {
                    logError("Unexpected error processing pending webhooks", e);
                }
            }
        }, 5000, POLLING_INTERVAL_MS); // Start after 5 seconds, then every 30 seconds

        isRunning = true;
        log("WebhookNotificationService started - polling every " + (POLLING_INTERVAL_MS / 1000) + " seconds");
    }

    /**
     * Stops the webhook notification service.
     * Cancels the polling timer and waits for current batch to complete.
     */
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }

        log("Stopping WebhookNotificationService...");

        if (schedulerTimer != null) {
            schedulerTimer.cancel();
            schedulerTimer = null;
        }

        isRunning = false;
        log("WebhookNotificationService stopped");
    }

    /**
     * Checks if the service is currently running.
     *
     * @return true if service is actively polling for webhooks
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Main processing method - retrieves and sends pending webhook alerts.
     * Called periodically by the scheduler.
     */
    private void processPendingWebhooks() {
        // Get pending webhook alerts
        List<PendingWebhook> pendingWebhooks = getPendingWebhookAlerts();

        if (pendingWebhooks.isEmpty()) {
            return; // Nothing to do
        }

        log("Processing " + pendingWebhooks.size() + " pending webhook alerts");

        int successCount = 0;
        int failureCount = 0;

        for (PendingWebhook webhook : pendingWebhooks) {
            try {
                // Get webhook endpoint configuration
                WebhookEndpoint endpoint = getWebhookEndpoint(webhook.recipient);
                if (endpoint == null) {
                    logError("Webhook endpoint not found or disabled: " + webhook.recipient, null);
                    updateAlertStatus(webhook.id, "failed", "Webhook endpoint not found or disabled");
                    failureCount++;
                    continue;
                }

                // Send webhook
                WebhookResponse response = sendWebhook(webhook, endpoint);
                if (response.success) {
                    updateAlertStatus(webhook.id, "sent", "Delivered successfully - HTTP " + response.statusCode);
                    updateWebhookEndpointSuccess(endpoint.id);
                    successCount++;
                } else {
                    handleFailedWebhook(webhook, endpoint, response);
                    failureCount++;
                }
            } catch (Exception e) {
                logError("Error processing webhook alert ID " + webhook.id, e);
                handleFailedWebhook(webhook, null, new WebhookResponse(false, 0, e.getMessage()));
                failureCount++;
            }
        }

        log("Webhook batch complete: " + successCount + " sent, " + failureCount + " failed");
    }

    /**
     * Retrieves pending webhook alerts from alert_history table.
     * Only returns alerts that are pending or due for retry.
     *
     * @return List of pending webhook alerts
     */
    private List<PendingWebhook> getPendingWebhookAlerts() {
        List<PendingWebhook> webhooks = new ArrayList<>();

        String sql =
            "SELECT id, alert_rule_id, execution_id, recipient, subject, message, payload_sent, " +
            "       retry_count, created_at " +
            "FROM alert_history " +
            "WHERE alert_type = 'webhook' " +
            "  AND (status = 'pending' OR (status = 'retrying' AND next_retry_at <= NOW())) " +
            "  AND retry_count < ? " +
            "ORDER BY created_at ASC " +
            "LIMIT ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, MAX_RETRY_ATTEMPTS);
            stmt.setInt(2, MAX_BATCH_SIZE);

            rs = stmt.executeQuery();

            while (rs.next()) {
                PendingWebhook webhook = new PendingWebhook();
                webhook.id = rs.getLong("id");
                webhook.alertRuleId = rs.getInt("alert_rule_id");

                long executionId = rs.getLong("execution_id");
                webhook.executionId = rs.wasNull() ? null : executionId;

                webhook.recipient = rs.getString("recipient");
                webhook.subject = rs.getString("subject");
                webhook.message = rs.getString("message");
                webhook.payloadSent = rs.getString("payload_sent");
                webhook.retryCount = rs.getInt("retry_count");
                webhook.createdAt = rs.getTimestamp("created_at");

                webhooks.add(webhook);
            }

        } catch (SQLException e) {
            logError("Database error retrieving pending webhook alerts", e);
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }

        return webhooks;
    }

    /**
     * Retrieves webhook endpoint configuration from webhook_endpoints table.
     *
     * @param webhookIdOrUrl Webhook endpoint ID (as string) or URL
     * @return WebhookEndpoint configuration or null if not found
     */
    private WebhookEndpoint getWebhookEndpoint(String webhookIdOrUrl) {
        // Try to parse as ID first
        Integer webhookId = null;
        try {
            webhookId = Integer.parseInt(webhookIdOrUrl);
        } catch (NumberFormatException e) {
            // Not an ID, might be a URL - try to find by URL
        }

        String sql;
        if (webhookId != null) {
            sql = "SELECT id, endpoint_name, endpoint_url, http_method, auth_type, " +
                  "       auth_username, auth_password, auth_token, custom_headers, " +
                  "       timeout_seconds, retry_count, consecutive_failures " +
                  "FROM webhook_endpoints " +
                  "WHERE id = ? AND is_enabled = true";
        } else {
            sql = "SELECT id, endpoint_name, endpoint_url, http_method, auth_type, " +
                  "       auth_username, auth_password, auth_token, custom_headers, " +
                  "       timeout_seconds, retry_count, consecutive_failures " +
                  "FROM webhook_endpoints " +
                  "WHERE endpoint_url = ? AND is_enabled = true";
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);

            if (webhookId != null) {
                stmt.setInt(1, webhookId);
            } else {
                stmt.setString(1, webhookIdOrUrl);
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                WebhookEndpoint endpoint = new WebhookEndpoint();
                endpoint.id = rs.getInt("id");
                endpoint.endpointName = rs.getString("endpoint_name");
                endpoint.endpointUrl = rs.getString("endpoint_url");
                endpoint.httpMethod = rs.getString("http_method");
                endpoint.authType = rs.getString("auth_type");
                endpoint.authUsername = rs.getString("auth_username");
                endpoint.authPassword = rs.getString("auth_password");
                endpoint.authToken = rs.getString("auth_token");
                endpoint.customHeaders = rs.getString("custom_headers");
                endpoint.timeoutSeconds = rs.getInt("timeout_seconds");
                endpoint.retryCount = rs.getInt("retry_count");
                endpoint.consecutiveFailures = rs.getInt("consecutive_failures");

                return endpoint;
            }

            return null;

        } catch (SQLException e) {
            logError("Database error retrieving webhook endpoint: " + webhookIdOrUrl, e);
            return null;
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Sends a webhook alert using HttpURLConnection.
     * Supports multiple authentication types and custom headers.
     *
     * @param webhook The webhook alert to send
     * @param endpoint The webhook endpoint configuration
     * @return WebhookResponse indicating success/failure
     */
    private WebhookResponse sendWebhook(PendingWebhook webhook, WebhookEndpoint endpoint) {
        HttpURLConnection conn = null;

        try {
            // Create URL connection
            URL url = new URL(endpoint.endpointUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Set HTTP method
            conn.setRequestMethod(endpoint.httpMethod);

            // Set timeout
            int timeout = endpoint.timeoutSeconds > 0 ? endpoint.timeoutSeconds * 1000 : DEFAULT_TIMEOUT_SECONDS * 1000;
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);

            // Set common headers
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("User-Agent", webhookConfig.getProperty("webhook.user.agent", "InterWeave-Monitoring/1.0"));
            conn.setRequestProperty("Accept", "application/json");

            // Set authentication headers
            setAuthenticationHeaders(conn, endpoint);

            // Set custom headers if provided
            if (endpoint.customHeaders != null && !endpoint.customHeaders.trim().isEmpty()) {
                setCustomHeaders(conn, endpoint.customHeaders);
            }

            // Enable output for POST body
            conn.setDoOutput(true);

            // Use the payload from the alert_history if available, otherwise build from message
            String payload = webhook.payloadSent;
            if (payload == null || payload.trim().isEmpty()) {
                payload = webhook.message; // Fallback to message field
            }

            // Send payload
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payloadBytes);
                os.flush();
            }

            // Get response code
            int responseCode = conn.getResponseCode();

            // Read response body
            String responseBody = readResponse(conn);

            // Check if successful (2xx status codes)
            boolean success = responseCode >= 200 && responseCode < 300;

            if (success) {
                log("Webhook sent successfully to " + endpoint.endpointUrl + " - HTTP " + responseCode);
                return new WebhookResponse(true, responseCode, responseBody);
            } else {
                log("Webhook failed to " + endpoint.endpointUrl + " - HTTP " + responseCode + ": " + responseBody);
                return new WebhookResponse(false, responseCode, "HTTP " + responseCode + ": " + responseBody);
            }

        } catch (Exception e) {
            logError("Failed to send webhook to " + endpoint.endpointUrl, e);
            return new WebhookResponse(false, 0, e.getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Sets authentication headers based on endpoint configuration.
     *
     * @param conn HttpURLConnection to configure
     * @param endpoint Webhook endpoint with auth settings
     */
    private void setAuthenticationHeaders(HttpURLConnection conn, WebhookEndpoint endpoint) {
        if (endpoint.authType == null || "none".equals(endpoint.authType)) {
            return; // No authentication
        }

        switch (endpoint.authType) {
            case "basic":
                if (endpoint.authUsername != null && endpoint.authPassword != null) {
                    String auth = endpoint.authUsername + ":" + endpoint.authPassword;
                    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                    conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
                }
                break;

            case "bearer":
                if (endpoint.authToken != null && !endpoint.authToken.trim().isEmpty()) {
                    conn.setRequestProperty("Authorization", "Bearer " + endpoint.authToken);
                }
                break;

            case "custom_header":
                // Custom headers are handled separately in setCustomHeaders
                break;

            default:
                log("Unknown auth type: " + endpoint.authType);
        }
    }

    /**
     * Sets custom HTTP headers from JSON configuration.
     * Expects format: {"Header-Name": "value", "Another-Header": "value"}
     *
     * @param conn HttpURLConnection to configure
     * @param customHeadersJson JSON string with custom headers
     */
    private void setCustomHeaders(HttpURLConnection conn, String customHeadersJson) {
        if (customHeadersJson == null || customHeadersJson.trim().isEmpty()) {
            return;
        }

        try {
            // Simple JSON parsing without external dependencies
            // Remove outer braces and split by comma
            String content = customHeadersJson.trim();
            if (content.startsWith("{")) {
                content = content.substring(1);
            }
            if (content.endsWith("}")) {
                content = content.substring(0, content.length() - 1);
            }

            // Split by comma (simple approach - doesn't handle commas in values)
            String[] pairs = content.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");
                    conn.setRequestProperty(key, value);
                }
            }
        } catch (Exception e) {
            logError("Error parsing custom headers: " + customHeadersJson, e);
        }
    }

    /**
     * Reads response body from HttpURLConnection.
     *
     * @param conn HttpURLConnection to read from
     * @return Response body as string
     */
    private String readResponse(HttpURLConnection conn) {
        StringBuilder response = new StringBuilder();

        try {
            // Try to read from input stream (success response)
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (Exception e1) {
            // If that fails, try error stream
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } catch (Exception e2) {
                return ""; // No response body available
            }
        }

        return response.toString();
    }

    /**
     * Handles a failed webhook delivery.
     * Updates status and schedules retry if attempts remaining.
     *
     * @param webhook The webhook that failed to send
     * @param endpoint The webhook endpoint (can be null if lookup failed)
     * @param response The failed response
     */
    private void handleFailedWebhook(PendingWebhook webhook, WebhookEndpoint endpoint, WebhookResponse response) {
        int newRetryCount = webhook.retryCount + 1;

        if (newRetryCount >= MAX_RETRY_ATTEMPTS) {
            // Max retries reached - mark as failed permanently
            String errorMsg = "Failed to send webhook after " + MAX_RETRY_ATTEMPTS + " attempts: " + response.errorMessage;
            updateAlertStatus(webhook.id, "failed", errorMsg);
            log("Alert ID " + webhook.id + " marked as failed after " + MAX_RETRY_ATTEMPTS + " attempts");

            // Update endpoint consecutive failures
            if (endpoint != null) {
                updateWebhookEndpointFailure(endpoint.id);
            }
        } else {
            // Schedule retry - exponential backoff (1 min, 5 min, 15 min)
            int retryDelayMinutes = calculateRetryDelay(newRetryCount);
            scheduleRetry(webhook.id, newRetryCount, retryDelayMinutes, response.errorMessage);
            log("Alert ID " + webhook.id + " scheduled for retry " + newRetryCount + " in " + retryDelayMinutes + " minutes");
        }
    }

    /**
     * Calculates retry delay with exponential backoff.
     *
     * @param retryCount Current retry attempt number
     * @return Delay in minutes before next retry
     */
    private int calculateRetryDelay(int retryCount) {
        switch (retryCount) {
            case 1: return 1;   // 1 minute
            case 2: return 5;   // 5 minutes
            case 3: return 15;  // 15 minutes
            default: return 30; // 30 minutes
        }
    }

    /**
     * Schedules a webhook alert for retry.
     *
     * @param alertId The alert history ID
     * @param retryCount New retry count
     * @param delayMinutes Minutes to wait before retry
     * @param errorMessage Error message from previous attempt
     */
    private void scheduleRetry(long alertId, int retryCount, int delayMinutes, String errorMessage) {
        String sql =
            "UPDATE alert_history " +
            "SET status = 'retrying', " +
            "    retry_count = ?, " +
            "    next_retry_at = CURRENT_TIMESTAMP + (? * INTERVAL '1 minute'), " +
            "    status_message = ? " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retryCount);
            stmt.setInt(2, delayMinutes);
            stmt.setString(3, "Retry scheduled in " + delayMinutes + " minutes (attempt " + retryCount + "): " + errorMessage);
            stmt.setLong(4, alertId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logError("Database error scheduling retry for alert ID " + alertId, e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Updates the delivery status of an alert in alert_history.
     *
     * @param alertId The alert history ID
     * @param status New status ('sent' or 'failed')
     * @param statusMessage Optional status message or error details
     */
    private void updateAlertStatus(long alertId, String status, String statusMessage) {
        String sql =
            "UPDATE alert_history " +
            "SET status = ?, " +
            "    status_message = ?, " +
            "    sent_at = CURRENT_TIMESTAMP " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setString(2, statusMessage);
            stmt.setLong(3, alertId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logError("Database error updating alert status for ID " + alertId, e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Updates webhook endpoint health after successful delivery.
     *
     * @param endpointId The webhook endpoint ID
     */
    private void updateWebhookEndpointSuccess(int endpointId) {
        String sql =
            "UPDATE webhook_endpoints " +
            "SET last_success_at = CURRENT_TIMESTAMP, " +
            "    consecutive_failures = 0, " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, endpointId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logError("Database error updating webhook endpoint success for ID " + endpointId, e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Updates webhook endpoint health after failed delivery.
     * Disables endpoint if consecutive failures exceed threshold.
     *
     * @param endpointId The webhook endpoint ID
     */
    private void updateWebhookEndpointFailure(int endpointId) {
        int maxConsecutiveFailures = Integer.parseInt(
            webhookConfig.getProperty("webhook.max.consecutive.failures",
                                     String.valueOf(MAX_CONSECUTIVE_FAILURES_BEFORE_DISABLE))
        );

        String sql =
            "UPDATE webhook_endpoints " +
            "SET last_failure_at = CURRENT_TIMESTAMP, " +
            "    consecutive_failures = consecutive_failures + 1, " +
            "    is_enabled = CASE WHEN consecutive_failures + 1 >= ? THEN false ELSE is_enabled END, " +
            "    updated_at = CURRENT_TIMESTAMP " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maxConsecutiveFailures);
            stmt.setInt(2, endpointId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Check if endpoint was disabled
                String checkSql = "SELECT is_enabled, consecutive_failures FROM webhook_endpoints WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, endpointId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    boolean isEnabled = rs.getBoolean("is_enabled");
                    int failures = rs.getInt("consecutive_failures");

                    if (!isEnabled && failures >= maxConsecutiveFailures) {
                        log("WARNING: Webhook endpoint " + endpointId + " has been disabled after " +
                            failures + " consecutive failures");
                    }
                }

                closeQuietly(rs);
                closeQuietly(checkStmt);
            }

        } catch (SQLException e) {
            logError("Database error updating webhook endpoint failure for ID " + endpointId, e);
        } finally {
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
        System.out.println("[WebhookNotificationService] " + message);
    }

    /**
     * Logs error message with optional exception.
     * Uses IWError framework for structured error logging.
     *
     * @param message Error message
     * @param e Exception that occurred (can be null)
     */
    private void logError(String message, Exception e) {
        System.err.println("[WebhookNotificationService ERROR] " + message);
        if (e != null) {
            e.printStackTrace();

        }
    }

    /**
     * Inner class to represent a pending webhook alert.
     */
    private static class PendingWebhook {
        long id;
        int alertRuleId;
        Long executionId;
        String recipient;
        String subject;
        String message;
        String payloadSent;
        int retryCount;
        Timestamp createdAt;
    }

    /**
     * Inner class to represent webhook endpoint configuration.
     */
    private static class WebhookEndpoint {
        int id;
        String endpointName;
        String endpointUrl;
        String httpMethod;
        String authType;
        String authUsername;
        String authPassword;
        String authToken;
        String customHeaders;
        int timeoutSeconds;
        int retryCount;
        int consecutiveFailures;
    }

    /**
     * Inner class to represent webhook response.
     */
    private static class WebhookResponse {
        boolean success;
        int statusCode;
        String errorMessage;

        WebhookResponse(boolean success, int statusCode, String errorMessage) {
            this.success = success;
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
        }
    }
}
