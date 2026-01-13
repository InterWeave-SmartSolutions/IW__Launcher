package com.interweave.monitoring.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.interweave.error.ErrorCode;
import com.interweave.error.ErrorLogger;
import com.interweave.error.IWError;

/**
 * EmailNotificationService - Processes pending email alerts and sends them via JavaMail.
 *
 * This service is responsible for:
 * - Polling the alert_history table for pending email notifications
 * - Sending HTML-formatted failure notification emails using JavaMail
 * - Configuring SMTP settings from properties file
 * - Handling email delivery failures gracefully with retry logic
 * - Supporting multiple recipients per alert rule
 * - Updating alert delivery status in the database
 *
 * The service runs as a background thread that checks for pending alerts every 30 seconds.
 * SMTP configuration is loaded from WEB-INF/monitoring.properties with fallback defaults.
 *
 * Usage Example:
 * <pre>
 * EmailNotificationService emailService = EmailNotificationService.getInstance();
 * emailService.start();  // Begin processing pending emails
 * ...
 * emailService.stop();   // Graceful shutdown on application exit
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class EmailNotificationService {

    private static EmailNotificationService instance;
    private DataSource dataSource;
    private Timer schedulerTimer;
    private boolean isRunning = false;
    private Properties smtpConfig;

    // Thread-safe singleton instance
    private static final Object instanceLock = new Object();

    // Constants
    private static final int POLLING_INTERVAL_MS = 30000; // 30 seconds
    private static final int MAX_BATCH_SIZE = 50; // Max emails to process per cycle
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final String DEFAULT_FROM_EMAIL = "noreply@interweave.com";
    private static final String DEFAULT_FROM_NAME = "InterWeave Monitoring";

    /**
     * Private constructor for singleton pattern.
     * Initializes JNDI DataSource and loads SMTP configuration.
     */
    private EmailNotificationService() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");

            // Load SMTP configuration
            loadSmtpConfiguration();

            log("EmailNotificationService initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            logError("Failed to initialize EmailNotificationService DataSource", e);
            throw new RuntimeException("Cannot initialize EmailNotificationService - database connection unavailable", e);
        }
    }

    /**
     * Gets the singleton instance of EmailNotificationService.
     * Thread-safe lazy initialization.
     *
     * @return The EmailNotificationService instance
     */
    public static EmailNotificationService getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new EmailNotificationService();
                }
            }
        }
        return instance;
    }

    /**
     * Loads SMTP configuration from monitoring.properties file.
     * Falls back to sensible defaults if file not found.
     */
    private void loadSmtpConfiguration() {
        smtpConfig = new Properties();

        try {
            // Try to load from WEB-INF/monitoring.properties
            InputStream is = getClass().getClassLoader().getResourceAsStream("monitoring.properties");
            if (is != null) {
                smtpConfig.load(is);
                log("Loaded SMTP configuration from monitoring.properties");
                is.close();
            } else {
                log("monitoring.properties not found - using default SMTP configuration");
                setDefaultSmtpConfiguration();
            }
        } catch (Exception e) {
            logError("Error loading monitoring.properties - using defaults", e);
            setDefaultSmtpConfiguration();
        }
    }

    /**
     * Sets default SMTP configuration values.
     * These are reasonable defaults for testing - production should use monitoring.properties.
     */
    private void setDefaultSmtpConfiguration() {
        smtpConfig.setProperty("smtp.host", "localhost");
        smtpConfig.setProperty("smtp.port", "587");
        smtpConfig.setProperty("smtp.auth", "false");
        smtpConfig.setProperty("smtp.starttls.enable", "false");
        smtpConfig.setProperty("smtp.from.email", DEFAULT_FROM_EMAIL);
        smtpConfig.setProperty("smtp.from.name", DEFAULT_FROM_NAME);
        smtpConfig.setProperty("email.enabled", "true");
    }

    /**
     * Starts the email notification service.
     * Begins polling for pending email alerts in the background.
     */
    public synchronized void start() {
        if (isRunning) {
            log("EmailNotificationService already running - ignoring start request");
            return;
        }

        // Check if email notifications are enabled
        String emailEnabled = smtpConfig.getProperty("email.enabled", "true");
        if (!"true".equalsIgnoreCase(emailEnabled)) {
            log("Email notifications are disabled in configuration - service will not start");
            return;
        }

        log("Starting EmailNotificationService...");

        schedulerTimer = new Timer("EmailNotificationService-Scheduler", true);
        schedulerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    processPendingEmails();
                } catch (Exception e) {
                    logError("Unexpected error processing pending emails", e);
                }
            }
        }, 5000, POLLING_INTERVAL_MS); // Start after 5 seconds, then every 30 seconds

        isRunning = true;
        log("EmailNotificationService started - polling every " + (POLLING_INTERVAL_MS / 1000) + " seconds");
    }

    /**
     * Stops the email notification service.
     * Cancels the polling timer and waits for current batch to complete.
     */
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }

        log("Stopping EmailNotificationService...");

        if (schedulerTimer != null) {
            schedulerTimer.cancel();
            schedulerTimer = null;
        }

        isRunning = false;
        log("EmailNotificationService stopped");
    }

    /**
     * Checks if the service is currently running.
     *
     * @return true if service is actively polling for emails
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Main processing method - retrieves and sends pending email alerts.
     * Called periodically by the scheduler.
     */
    private void processPendingEmails() {
        // Get pending email alerts
        List<PendingEmail> pendingEmails = getPendingEmailAlerts();

        if (pendingEmails.isEmpty()) {
            return; // Nothing to do
        }

        log("Processing " + pendingEmails.size() + " pending email alerts");

        int successCount = 0;
        int failureCount = 0;

        for (PendingEmail email : pendingEmails) {
            try {
                boolean sent = sendEmail(email);
                if (sent) {
                    updateAlertStatus(email.id, "sent", null);
                    successCount++;
                } else {
                    handleFailedEmail(email);
                    failureCount++;
                }
            } catch (Exception e) {
                logError("Error processing email alert ID " + email.id, e);
                handleFailedEmail(email);
                failureCount++;
            }
        }

        log("Email batch complete: " + successCount + " sent, " + failureCount + " failed");
    }

    /**
     * Retrieves pending email alerts from alert_history table.
     * Only returns alerts that are pending or due for retry.
     *
     * @return List of pending email alerts
     */
    private List<PendingEmail> getPendingEmailAlerts() {
        List<PendingEmail> emails = new ArrayList<>();

        String sql =
            "SELECT id, alert_rule_id, execution_id, recipient, subject, message, " +
            "       retry_count, created_at " +
            "FROM alert_history " +
            "WHERE alert_type = 'email' " +
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
                PendingEmail email = new PendingEmail();
                email.id = rs.getLong("id");
                email.alertRuleId = rs.getInt("alert_rule_id");

                long executionId = rs.getLong("execution_id");
                email.executionId = rs.wasNull() ? null : executionId;

                email.recipient = rs.getString("recipient");
                email.subject = rs.getString("subject");
                email.message = rs.getString("message");
                email.retryCount = rs.getInt("retry_count");
                email.createdAt = rs.getTimestamp("created_at");

                emails.add(email);
            }

        } catch (SQLException e) {
            logError("Database error retrieving pending email alerts", e);
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }

        return emails;
    }

    /**
     * Sends an email alert using JavaMail.
     * Formats the message as HTML and includes all alert details.
     *
     * @param email The email to send
     * @return true if sent successfully, false otherwise
     */
    private boolean sendEmail(PendingEmail email) {
        try {
            // Create JavaMail session
            Session session = createMailSession();

            // Create message
            MimeMessage message = new MimeMessage(session);

            // Set from address
            String fromEmail = smtpConfig.getProperty("smtp.from.email", DEFAULT_FROM_EMAIL);
            String fromName = smtpConfig.getProperty("smtp.from.name", DEFAULT_FROM_NAME);
            message.setFrom(new InternetAddress(fromEmail, fromName));

            // Set recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.recipient));

            // Set subject
            message.setSubject(email.subject);

            // Set content as HTML
            String htmlContent = formatEmailAsHtml(email);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Send message
            Transport.send(message);

            log("Email sent successfully to " + email.recipient + " for alert ID " + email.id);
            return true;

        } catch (Exception e) {
            logError("Failed to send email to " + email.recipient + " (alert ID " + email.id + ")", e);
            return false;
        }
    }

    /**
     * Creates a JavaMail Session with SMTP configuration.
     *
     * @return Configured JavaMail Session
     */
    private Session createMailSession() {
        Properties props = new Properties();

        // Basic SMTP properties
        props.put("mail.smtp.host", smtpConfig.getProperty("smtp.host", "localhost"));
        props.put("mail.smtp.port", smtpConfig.getProperty("smtp.port", "587"));

        // TLS/SSL settings
        String startTls = smtpConfig.getProperty("smtp.starttls.enable", "false");
        props.put("mail.smtp.starttls.enable", startTls);

        String sslEnable = smtpConfig.getProperty("smtp.ssl.enable", "false");
        props.put("mail.smtp.ssl.enable", sslEnable);

        // Authentication
        String authEnabled = smtpConfig.getProperty("smtp.auth", "false");
        props.put("mail.smtp.auth", authEnabled);

        // Timeout settings
        props.put("mail.smtp.timeout", smtpConfig.getProperty("smtp.timeout", "10000"));
        props.put("mail.smtp.connectiontimeout", smtpConfig.getProperty("smtp.connectiontimeout", "10000"));

        // Create session with or without authentication
        if ("true".equalsIgnoreCase(authEnabled)) {
            final String username = smtpConfig.getProperty("smtp.username", "");
            final String password = smtpConfig.getProperty("smtp.password", "");

            return Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            return Session.getInstance(props);
        }
    }

    /**
     * Formats an email alert as HTML.
     * Creates a professional-looking notification email with all alert details.
     *
     * @param email The email to format
     * @return HTML-formatted email content
     */
    private String formatEmailAsHtml(PendingEmail email) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append(".header { background-color: #d9534f; color: white; padding: 20px; border-radius: 5px 5px 0 0; }");
        html.append(".content { background-color: #f9f9f9; padding: 20px; border: 1px solid #ddd; border-top: none; }");
        html.append(".footer { background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 12px; color: #666; border-radius: 0 0 5px 5px; }");
        html.append(".label { font-weight: bold; color: #555; }");
        html.append(".value { margin-bottom: 15px; }");
        html.append(".error-box { background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 10px; margin: 15px 0; }");
        html.append(".button { display: inline-block; padding: 10px 20px; background-color: #0275d8; color: white; text-decoration: none; border-radius: 3px; margin-top: 15px; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='container'>");

        // Header
        html.append("<div class='header'>");
        html.append("<h2 style='margin: 0;'>&#9888; Integration Failure Alert</h2>");
        html.append("</div>");

        // Content
        html.append("<div class='content'>");
        html.append("<p>An integration transaction has failed and requires your attention.</p>");

        // Format the message content - convert plain text to HTML
        String[] messageLines = email.message.split("\n");
        for (String line : messageLines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            // Parse "Label: Value" format
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                String label = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();

                if ("Error Message".equals(label) || "Error Code".equals(label)) {
                    html.append("<div class='error-box'>");
                    html.append("<div class='label'>").append(escapeHtml(label)).append(":</div>");
                    html.append("<div>").append(escapeHtml(value)).append("</div>");
                    html.append("</div>");
                } else {
                    html.append("<div class='value'>");
                    html.append("<span class='label'>").append(escapeHtml(label)).append(":</span> ");
                    html.append(escapeHtml(value));
                    html.append("</div>");
                }
            } else {
                html.append("<p>").append(escapeHtml(line)).append("</p>");
            }
        }

        // Add link to dashboard (if execution ID available)
        if (email.executionId != null) {
            html.append("<a href='http://localhost:8080/iw-business-daemon/monitoring/Dashboard.jsp' class='button'>");
            html.append("View Details in Dashboard");
            html.append("</a>");
        }

        html.append("</div>");

        // Footer
        html.append("<div class='footer'>");
        html.append("<p>This is an automated alert from InterWeave Monitoring Dashboard.</p>");
        html.append("<p>Alert ID: ").append(email.id).append(" | Created: ").append(email.createdAt).append("</p>");
        html.append("</div>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * Escapes HTML special characters to prevent XSS and formatting issues.
     *
     * @param text Text to escape
     * @return HTML-safe text
     */
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#39;");
    }

    /**
     * Handles a failed email delivery.
     * Updates status and schedules retry if attempts remaining.
     *
     * @param email The email that failed to send
     */
    private void handleFailedEmail(PendingEmail email) {
        int newRetryCount = email.retryCount + 1;

        if (newRetryCount >= MAX_RETRY_ATTEMPTS) {
            // Max retries reached - mark as failed permanently
            String errorMsg = "Failed to send email after " + MAX_RETRY_ATTEMPTS + " attempts";
            updateAlertStatus(email.id, "failed", errorMsg);
            log("Alert ID " + email.id + " marked as failed after " + MAX_RETRY_ATTEMPTS + " attempts");
        } else {
            // Schedule retry - exponential backoff (1 min, 5 min, 15 min)
            int retryDelayMinutes = calculateRetryDelay(newRetryCount);
            scheduleRetry(email.id, newRetryCount, retryDelayMinutes);
            log("Alert ID " + email.id + " scheduled for retry " + newRetryCount + " in " + retryDelayMinutes + " minutes");
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
     * Schedules an email alert for retry.
     *
     * @param alertId The alert history ID
     * @param retryCount New retry count
     * @param delayMinutes Minutes to wait before retry
     */
    private void scheduleRetry(long alertId, int retryCount, int delayMinutes) {
        String sql =
            "UPDATE alert_history " +
            "SET status = 'retrying', " +
            "    retry_count = ?, " +
            "    next_retry_at = DATE_ADD(NOW(), INTERVAL ? MINUTE), " +
            "    status_message = ? " +
            "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retryCount);
            stmt.setInt(2, delayMinutes);
            stmt.setString(3, "Retry scheduled in " + delayMinutes + " minutes (attempt " + retryCount + ")");
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
            "    sent_at = NOW() " +
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
        System.out.println("[EmailNotificationService] " + message);
    }

    /**
     * Logs error message with optional exception.
     * Uses IWError framework for structured error logging.
     *
     * @param message Error message
     * @param e Exception that occurred (can be null)
     */
    private void logError(String message, Exception e) {
        System.err.println("[EmailNotificationService ERROR] " + message);
        if (e != null) {
            e.printStackTrace();

            // Log to IWError framework
            IWError error = IWError.builder(ErrorCode.DB001)
                .message(message)
                .affectedComponent("EmailNotificationService")
                .cause(e.getClass().getSimpleName() + ": " + e.getMessage())
                .throwable(e)
                .build();

            ErrorLogger.logError(error);
        }
    }

    /**
     * Inner class to represent a pending email alert.
     */
    private static class PendingEmail {
        long id;
        int alertRuleId;
        Long executionId;
        String recipient;
        String subject;
        String message;
        int retryCount;
        Timestamp createdAt;
    }
}
