# EmailNotificationService Documentation

## Overview

The **EmailNotificationService** is a background service that processes pending email alerts and sends them to configured recipients. It works in conjunction with the AlertService to provide complete email alerting functionality for transaction failures.

## Architecture

### Component Interaction

```
Transaction Fails
    ↓
AlertService evaluates rules
    ↓
Creates alert_history entries (status='pending')
    ↓
EmailNotificationService polls database
    ↓
Sends emails via JavaMail/SMTP
    ↓
Updates alert_history (status='sent' or 'failed')
```

### Key Features

- **Background Polling**: Checks for pending emails every 30 seconds
- **Batch Processing**: Processes up to 50 emails per cycle
- **Retry Logic**: Automatic retry with exponential backoff (1min, 5min, 15min)
- **HTML Email Formatting**: Professional-looking alert emails with styling
- **Configurable SMTP**: Supports various SMTP servers and authentication methods
- **Graceful Failure Handling**: Never blocks transaction execution
- **Multi-Recipient Support**: Sends individual emails to each recipient in alert rule

## Configuration

### SMTP Configuration File

Create `WEB-INF/monitoring.properties` (or `WEB-INF/classes/monitoring.properties`) with SMTP settings:

```properties
# Email Service Configuration
email.enabled=true

# SMTP Server Settings
smtp.host=smtp.gmail.com
smtp.port=587
smtp.auth=true
smtp.starttls.enable=true
smtp.ssl.enable=false

# SMTP Authentication (if smtp.auth=true)
smtp.username=your-email@gmail.com
smtp.password=your-app-password

# Email Sender Information
smtp.from.email=noreply@interweave.com
smtp.from.name=InterWeave Monitoring

# Timeout Settings (milliseconds)
smtp.timeout=10000
smtp.connectiontimeout=10000
```

### Common SMTP Configurations

#### Gmail
```properties
smtp.host=smtp.gmail.com
smtp.port=587
smtp.auth=true
smtp.starttls.enable=true
smtp.username=your-email@gmail.com
smtp.password=your-app-password  # Use App Password, not regular password
```

#### Office 365
```properties
smtp.host=smtp.office365.com
smtp.port=587
smtp.auth=true
smtp.starttls.enable=true
smtp.username=your-email@company.com
smtp.password=your-password
```

#### Amazon SES
```properties
smtp.host=email-smtp.us-east-1.amazonaws.com
smtp.port=587
smtp.auth=true
smtp.starttls.enable=true
smtp.username=your-smtp-username
smtp.password=your-smtp-password
```

#### SendGrid
```properties
smtp.host=smtp.sendgrid.net
smtp.port=587
smtp.auth=true
smtp.starttls.enable=false
smtp.username=apikey
smtp.password=your-sendgrid-api-key
```

#### Local SMTP (Testing/Development)
```properties
smtp.host=localhost
smtp.port=25
smtp.auth=false
smtp.starttls.enable=false
```

## Usage

### Automatic Startup

The service starts automatically when the web application loads via `MonitoringContextListener`:

```java
// In MonitoringContextListener.contextInitialized()
EmailNotificationService emailService = EmailNotificationService.getInstance();
emailService.start();
```

### Manual Control

```java
// Get singleton instance
EmailNotificationService emailService = EmailNotificationService.getInstance();

// Start the service (begins polling)
emailService.start();

// Check if running
boolean running = emailService.isRunning();

// Stop the service (graceful shutdown)
emailService.stop();
```

### Alert Flow Example

```java
// 1. Transaction fails
TransactionLogger.logFailure(executionId, "Connection timeout", "TIMEOUT_ERROR");

// 2. AlertService creates pending email alerts
AlertService.getInstance().evaluateAlerts(
    executionId,
    companyId,
    projectId,
    "CustomerSync",
    "Connection timeout",
    "TIMEOUT_ERROR"
);

// 3. EmailNotificationService automatically picks up and sends emails
// (no manual intervention required)
```

## Email Format

### HTML Email Structure

The service sends professionally-formatted HTML emails that include:

- **Header**: Red banner with alert icon
- **Content Section**: Formatted alert details
  - Flow Name
  - Execution ID
  - Error Code (highlighted in yellow box)
  - Error Message (highlighted in yellow box)
  - Timestamp
- **Action Button**: Link to monitoring dashboard
- **Footer**: Alert metadata and InterWeave branding

### Sample Email Content

```
┌─────────────────────────────────────┐
│ ⚠ Integration Failure Alert         │ (Red Header)
└─────────────────────────────────────┘
│                                     │
│ An integration transaction has      │
│ failed and requires your attention. │
│                                     │
│ Flow Name: CustomerSync             │
│ Execution ID: exec_2024_001         │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Error Code: TIMEOUT_ERROR       │ │ (Yellow Box)
│ └─────────────────────────────────┘ │
│ ┌─────────────────────────────────┐ │
│ │ Error Message:                  │ │ (Yellow Box)
│ │ Connection timeout after 30s    │ │
│ └─────────────────────────────────┘ │
│                                     │
│ [View Details in Dashboard]         │ (Blue Button)
│                                     │
└─────────────────────────────────────┘
│ This is an automated alert          │
│ Alert ID: 123 | Created: 2024-01-09 │
└─────────────────────────────────────┘
```

## Retry Logic

### Retry Strategy

The service implements exponential backoff for failed email deliveries:

| Attempt | Delay  | Status     |
|---------|--------|------------|
| 1       | 0      | pending    |
| 2       | 1 min  | retrying   |
| 3       | 5 min  | retrying   |
| 4       | 15 min | retrying   |
| Failed  | -      | failed     |

### Database Fields

```sql
retry_count          INT           -- Current retry attempt (0-3)
next_retry_at        TIMESTAMP     -- When to retry next
status               ENUM          -- 'pending', 'retrying', 'sent', 'failed'
status_message       TEXT          -- Error message or retry info
```

### Retry Query

The service retrieves alerts for retry using:

```sql
SELECT * FROM alert_history
WHERE alert_type = 'email'
  AND (status = 'pending' OR (status = 'retrying' AND next_retry_at <= NOW()))
  AND retry_count < 3
ORDER BY created_at ASC
LIMIT 50
```

## Performance Characteristics

### Polling Cycle

- **Interval**: 30 seconds
- **Initial Delay**: 5 seconds (allows application to fully start)
- **Batch Size**: 50 emails per cycle
- **Typical Processing Time**: 2-5 seconds per batch (depends on SMTP latency)

### Resource Usage

- **Memory**: Minimal (~5-10 MB for service)
- **CPU**: Negligible when idle, <5% during batch processing
- **Database Connections**: 1 connection per query (connection pooling via JNDI)
- **Network**: 1 SMTP connection per email sent

### Scalability

- Can process ~100 emails per minute (30-second polling + 50 email batch)
- For higher throughput, reduce `POLLING_INTERVAL_MS` or increase `MAX_BATCH_SIZE`
- Thread-safe singleton design prevents concurrent processing issues

## Monitoring and Troubleshooting

### Service Status Check

```java
EmailNotificationService emailService = EmailNotificationService.getInstance();
boolean isRunning = emailService.isRunning();
System.out.println("Email service running: " + isRunning);
```

### Log Messages

The service logs important events to stdout/stderr:

```
[EmailNotificationService] EmailNotificationService initialized - using JNDI DataSource jdbc/IWDB
[EmailNotificationService] Loaded SMTP configuration from monitoring.properties
[EmailNotificationService] Starting EmailNotificationService...
[EmailNotificationService] EmailNotificationService started - polling every 30 seconds
[EmailNotificationService] Processing 5 pending email alerts
[EmailNotificationService] Email sent successfully to admin@example.com for alert ID 123
[EmailNotificationService] Email batch complete: 5 sent, 0 failed
[EmailNotificationService] Stopping EmailNotificationService...
[EmailNotificationService] EmailNotificationService stopped
```

### Check for Pending Emails

```sql
-- View pending emails
SELECT id, recipient, subject, status, retry_count, created_at
FROM alert_history
WHERE alert_type = 'email'
  AND status IN ('pending', 'retrying')
ORDER BY created_at ASC;

-- View failed emails
SELECT id, recipient, subject, status_message, retry_count, created_at
FROM alert_history
WHERE alert_type = 'email'
  AND status = 'failed'
ORDER BY created_at DESC
LIMIT 20;

-- View recently sent emails
SELECT id, recipient, subject, sent_at
FROM alert_history
WHERE alert_type = 'email'
  AND status = 'sent'
ORDER BY sent_at DESC
LIMIT 20;
```

### Common Issues

#### 1. Emails Not Sending

**Check service status:**
```sql
-- Look for recent email activity
SELECT COUNT(*) AS pending_count
FROM alert_history
WHERE alert_type = 'email'
  AND status = 'pending';
```

**Check logs:**
```bash
tail -f web_portal/tomcat/logs/catalina.out | grep EmailNotificationService
```

**Verify service started:**
Look for initialization message in logs

#### 2. Authentication Failures

**Error:** `AuthenticationFailedException`

**Solutions:**
- Verify `smtp.username` and `smtp.password` in monitoring.properties
- For Gmail: Use App Password, not regular password (enable 2FA first)
- Check if SMTP server allows authentication

#### 3. Connection Timeouts

**Error:** `MailConnectException` or timeout errors

**Solutions:**
- Verify `smtp.host` and `smtp.port` are correct
- Check firewall rules (port 587 or 465 must be open)
- Increase timeout values in configuration
- Test network connectivity: `telnet smtp.gmail.com 587`

#### 4. SSL/TLS Errors

**Error:** `SSLHandshakeException`

**Solutions:**
- For port 587: Use `smtp.starttls.enable=true`, `smtp.ssl.enable=false`
- For port 465: Use `smtp.ssl.enable=true`, `smtp.starttls.enable=false`
- Update Java SSL certificates if needed

#### 5. Emails Going to Spam

**Solutions:**
- Configure SPF, DKIM, and DMARC records for your domain
- Use a reputable SMTP provider (SendGrid, Amazon SES, etc.)
- Ensure `smtp.from.email` matches authenticated domain
- Add unsubscribe link to email footer (future enhancement)

### Manual Email Retry

Force retry of failed emails by resetting status:

```sql
-- Reset failed emails to pending for retry
UPDATE alert_history
SET status = 'pending',
    retry_count = 0,
    next_retry_at = NULL,
    status_message = 'Manually reset for retry'
WHERE alert_type = 'email'
  AND status = 'failed'
  AND created_at >= DATE_SUB(NOW(), INTERVAL 1 DAY);
```

## Testing

### Test SMTP Configuration

Create a test script or use the database to manually create a test alert:

```sql
-- Create a test email alert
INSERT INTO alert_history
(alert_rule_id, execution_id, alert_type, recipient, subject, message, status, created_at)
VALUES
(1, NULL, 'email', 'test@example.com',
 'Test Alert', 'This is a test email from EmailNotificationService.\n\nIf you receive this, email notifications are working correctly.',
 'pending', NOW());

-- Check if it was sent (wait 30 seconds for polling cycle)
SELECT id, recipient, status, sent_at, status_message
FROM alert_history
WHERE recipient = 'test@example.com'
ORDER BY id DESC
LIMIT 1;
```

### Test Email Formats

```sql
-- Test with realistic failure alert
INSERT INTO alert_history
(alert_rule_id, execution_id, alert_type, recipient, subject, message, status, created_at)
VALUES
(1, 123, 'email', 'admin@example.com',
 'Transaction Failure Alert: CustomerSync',
 'Transaction Failure Detected\n\nFlow Name: CustomerSync\nExecution ID: exec_test_001\nError Code: TIMEOUT_ERROR\nError Message: Connection timeout after 30 seconds\n\nPlease check the monitoring dashboard for more details.',
 'pending', NOW());
```

## Integration with AlertService

The EmailNotificationService is designed to work seamlessly with AlertService:

```java
// AlertService creates pending email entries
// in alert_history table (status='pending')
recordAlert(alertRuleId, executionId, "email",
           recipientEmail, subject, message, null);

// EmailNotificationService automatically picks them up
// and sends them in the next polling cycle (≤30 seconds)
```

### Alert Rule Configuration

Email recipients are configured in the `alert_rules` table:

```sql
-- Alert rule with email notifications
INSERT INTO alert_rules
(company_id, rule_name, notification_type, email_recipients, is_enabled)
VALUES
(1, 'Critical Failures', 'email', 'admin@example.com,ops@example.com', 1);

-- Alert rule with both email and webhook
INSERT INTO alert_rules
(company_id, rule_name, notification_type, email_recipients, webhook_endpoint_ids, is_enabled)
VALUES
(1, 'All Failures', 'both', 'admin@example.com', '1,2', 1);
```

## Security Considerations

### Credential Storage

- SMTP passwords stored in plain text in monitoring.properties
- **Recommendation**: Restrict file permissions (chmod 600)
- **Future Enhancement**: Encrypt passwords or use environment variables

### Email Content

- HTML content is escaped to prevent XSS
- No user-controlled content injection
- Error messages may contain sensitive data - ensure recipients are authorized

### Rate Limiting

The service respects alert rule rate limits:
- `cooldown_minutes`: Prevents alert storms
- `max_alerts_per_day`: Caps daily email volume
- Set in `alert_rules` table, enforced by AlertService

## Performance Tuning

### Adjust Polling Interval

Edit `EmailNotificationService.java`:

```java
// Default: 30 seconds
private static final int POLLING_INTERVAL_MS = 30000;

// For faster alerts (5 seconds)
private static final int POLLING_INTERVAL_MS = 5000;

// For lower load (2 minutes)
private static final int POLLING_INTERVAL_MS = 120000;
```

### Adjust Batch Size

```java
// Default: 50 emails per cycle
private static final int MAX_BATCH_SIZE = 50;

// For higher throughput
private static final int MAX_BATCH_SIZE = 100;

// For lower SMTP load
private static final int MAX_BATCH_SIZE = 10;
```

### Disable Email Notifications

In monitoring.properties:

```properties
email.enabled=false
```

The service will not start if `email.enabled=false`.

## Future Enhancements

- [ ] Support for email templates (Velocity or FreeMarker)
- [ ] Attachment support (log files, screenshots)
- [ ] Email tracking (open rates, click rates)
- [ ] Priority queue (critical alerts sent first)
- [ ] Digest mode (batch multiple alerts into one email)
- [ ] Unsubscribe link support
- [ ] Email signature customization
- [ ] Multi-threaded sending for higher throughput
- [ ] Encrypted credential storage

## Related Documentation

- [AlertService Documentation](README_ALERT_SERVICE.md)
- [MetricsAggregator Documentation](README_METRICS_AGGREGATOR.md)
- [Monitoring Schema](../../../../_internal/sql/005_monitoring_schema.sql)
- [API Endpoints](../api/API_ENDPOINTS.md)
