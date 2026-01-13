# WebhookNotificationService

## Overview

The WebhookNotificationService is responsible for dispatching webhook notifications to external alerting systems when integration failures occur. It works in conjunction with the AlertService to deliver real-time alerts to third-party platforms like Slack, PagerDuty, Microsoft Teams, and custom webhook endpoints.

## Features

### Core Capabilities

- **Async Webhook Dispatch**: Polls for pending webhook alerts and dispatches them asynchronously
- **Multiple Authentication Methods**: Supports none, basic, bearer token, and custom header authentication
- **Retry Logic**: Automatic retry with exponential backoff (1 min, 5 min, 15 min)
- **Health Tracking**: Monitors webhook endpoint health and automatically disables unhealthy endpoints
- **Configurable Timeouts**: Per-endpoint timeout configuration with sensible defaults
- **Custom Headers**: Support for custom HTTP headers (useful for API keys, signatures, etc.)
- **JSON Payloads**: Sends structured JSON payloads with transaction failure details

### Supported Integrations

1. **Slack** - Incoming Webhooks
2. **PagerDuty** - Events API
3. **Microsoft Teams** - Incoming Webhooks
4. **Custom Webhooks** - Any HTTP endpoint that accepts JSON POST

## Architecture

```
┌─────────────────┐     ┌──────────────────────┐     ┌─────────────────┐
│  AlertService   │────>│ alert_history table  │<────│  Webhook        │
│                 │     │ (status='pending')   │     │  Notification   │
└─────────────────┘     └──────────────────────┘     │  Service        │
                                                      └────────┬────────┘
                                                               │
                                                               v
                        ┌──────────────────────┐     ┌─────────────────┐
                        │ webhook_endpoints    │<────│ HttpURLConnection│
                        │ (configuration)      │     │ POST JSON        │
                        └──────────────────────┘     └────────┬────────┘
                                                                │
                                                                v
                                                       ┌─────────────────┐
                                                       │ External System │
                                                       │ (Slack, etc.)   │
                                                       └─────────────────┘
```

## Workflow

1. **AlertService** evaluates alert rules when transactions fail
2. AlertService creates webhook entries in `alert_history` with `status='pending'`
3. **WebhookNotificationService** polls for pending webhooks every 30 seconds
4. Service looks up webhook endpoint configuration from `webhook_endpoints` table
5. Constructs HTTP request with proper authentication and headers
6. POSTs JSON payload to webhook URL
7. Updates alert status based on response (sent/failed/retrying)
8. Updates endpoint health metrics (success/failure timestamps, consecutive failures)
9. On failure, schedules retry with exponential backoff

## Configuration

### monitoring.properties

Create or update `WEB-INF/monitoring.properties`:

```properties
# Webhook Service Configuration
webhook.enabled=true
webhook.default.timeout=30
webhook.max.consecutive.failures=10
webhook.user.agent=InterWeave-Monitoring/1.0
```

### Configuration Options

| Property | Default | Description |
|----------|---------|-------------|
| `webhook.enabled` | `true` | Enable/disable webhook notification service |
| `webhook.default.timeout` | `30` | Default HTTP timeout in seconds |
| `webhook.max.consecutive.failures` | `10` | Disable endpoint after N failures |
| `webhook.user.agent` | `InterWeave-Monitoring/1.0` | User-Agent header for requests |

## Database Tables

### webhook_endpoints

Stores webhook endpoint configuration:

```sql
CREATE TABLE webhook_endpoints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    endpoint_name VARCHAR(255) NOT NULL,
    endpoint_url VARCHAR(1000) NOT NULL,
    http_method ENUM('POST', 'PUT', 'PATCH') DEFAULT 'POST',

    -- Authentication
    auth_type ENUM('none', 'basic', 'bearer', 'custom_header') DEFAULT 'none',
    auth_username VARCHAR(255),
    auth_password VARCHAR(255),
    auth_token VARCHAR(500),
    custom_headers TEXT,

    -- Reliability
    timeout_seconds INT UNSIGNED DEFAULT 30,
    retry_count INT UNSIGNED DEFAULT 3,

    -- Health tracking
    is_enabled TINYINT(1) DEFAULT 1,
    last_success_at TIMESTAMP NULL,
    last_failure_at TIMESTAMP NULL,
    consecutive_failures INT UNSIGNED DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### alert_history

Tracks webhook delivery status:

```sql
SELECT id, alert_rule_id, execution_id, alert_type, recipient,
       subject, message, payload_sent, status, status_message,
       retry_count, next_retry_at, sent_at, created_at
FROM alert_history
WHERE alert_type = 'webhook'
  AND status IN ('pending', 'retrying', 'sent', 'failed');
```

## Usage Examples

### 1. Slack Integration

**Create Slack Webhook Endpoint:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, timeout_seconds, is_enabled
) VALUES (
    1,
    'Slack - Alerts Channel',
    'https://hooks.slack.com/services/YOUR/WEBHOOK/URL',
    'POST',
    'none',
    30,
    1
);
```

**Create Alert Rule:**

```sql
INSERT INTO alert_rules (
    company_id, rule_name, description, flow_name,
    alert_on_failure, notification_type, webhook_endpoint_ids,
    cooldown_minutes, max_alerts_per_day, is_enabled
) VALUES (
    1,
    'Critical Flow Failures to Slack',
    'Send Slack notification for critical integration failures',
    'OrderProcessingFlow',
    1,
    'webhook',
    '1', -- Webhook endpoint ID
    15,
    50,
    1
);
```

**Payload Format for Slack:**

The service sends JSON that you can adapt using Slack Block Kit:

```json
{
  "event_type": "transaction_failure",
  "flow_name": "OrderProcessingFlow",
  "execution_id": "exec_20260109_123456",
  "error_code": "TIMEOUT_ERROR",
  "error_message": "Database connection timeout after 30 seconds",
  "timestamp": "2026-01-09T15:30:00Z"
}
```

For formatted Slack messages, use a proxy service or custom webhook that transforms this to Slack's format.

### 2. PagerDuty Integration

**Create PagerDuty Webhook Endpoint:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, custom_headers, timeout_seconds, is_enabled
) VALUES (
    1,
    'PagerDuty - Production Incidents',
    'https://events.pagerduty.com/v2/enqueue',
    'POST',
    'custom_header',
    '{"Authorization": "Token token=YOUR_PAGERDUTY_TOKEN"}',
    30,
    1
);
```

### 3. Microsoft Teams Integration

**Create Teams Webhook Endpoint:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, timeout_seconds, is_enabled
) VALUES (
    1,
    'Microsoft Teams - Operations Channel',
    'https://outlook.office.com/webhook/YOUR-WEBHOOK-ID',
    'POST',
    'none',
    30,
    1
);
```

### 4. Custom Webhook with Bearer Token

**Create Custom Endpoint with Auth:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, auth_token, timeout_seconds, is_enabled
) VALUES (
    1,
    'Internal Alerting Service',
    'https://alerts.example.com/api/events',
    'POST',
    'bearer',
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...',
    15,
    1
);
```

### 5. Basic Authentication

**Create Endpoint with Basic Auth:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, auth_username, auth_password, timeout_seconds, is_enabled
) VALUES (
    1,
    'Legacy Monitoring System',
    'https://monitoring.example.com/webhook',
    'POST',
    'basic',
    'alert_user',
    'secure_password_123',
    30,
    1
);
```

## Payload Format

### Standard JSON Payload

The service sends a standardized JSON payload to all webhook endpoints:

```json
{
  "event_type": "transaction_failure",
  "flow_name": "CustomerSyncFlow",
  "execution_id": "exec_20260109_143022",
  "error_code": "DB_CONNECTION_ERROR",
  "error_message": "Failed to connect to customer database: Connection refused",
  "timestamp": "2026-01-09T14:30:22Z"
}
```

### Custom Payload Templates

For endpoints requiring specific formats, you can use the `payload_template` field in `webhook_endpoints` table (future enhancement).

## Retry Logic

### Retry Strategy

The service implements exponential backoff for failed deliveries:

| Attempt | Delay | Total Time |
|---------|-------|------------|
| Initial | 0 min | 0 min |
| Retry 1 | 1 min | 1 min |
| Retry 2 | 5 min | 6 min |
| Retry 3 | 15 min | 21 min |
| Failed  | - | Marked as failed |

### Retry Status Flow

```
pending → retrying → retrying → retrying → failed
  ↓          ↓          ↓          ↓
 sent       sent       sent       sent
```

### Query Retry Status

```sql
-- Check alerts pending retry
SELECT id, recipient, retry_count, next_retry_at, status_message
FROM alert_history
WHERE status = 'retrying'
  AND alert_type = 'webhook'
ORDER BY next_retry_at;

-- Check failed webhooks
SELECT id, recipient, retry_count, status_message, created_at
FROM alert_history
WHERE status = 'failed'
  AND alert_type = 'webhook'
  AND created_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR);
```

## Health Monitoring

### Endpoint Health Tracking

The service tracks webhook endpoint health:

- **last_success_at**: Timestamp of last successful delivery
- **last_failure_at**: Timestamp of last failed delivery
- **consecutive_failures**: Number of consecutive failures
- **is_enabled**: Automatically disabled after 10 consecutive failures

### Query Endpoint Health

```sql
-- Check webhook endpoint health
SELECT
    id,
    endpoint_name,
    endpoint_url,
    is_enabled,
    consecutive_failures,
    last_success_at,
    last_failure_at,
    TIMESTAMPDIFF(MINUTE, last_success_at, NOW()) AS minutes_since_success
FROM webhook_endpoints
ORDER BY consecutive_failures DESC, last_failure_at DESC;

-- Find disabled endpoints
SELECT id, endpoint_name, endpoint_url, consecutive_failures, last_failure_at
FROM webhook_endpoints
WHERE is_enabled = 0
ORDER BY last_failure_at DESC;
```

### Re-enabling Disabled Endpoints

After fixing the issue, re-enable the endpoint:

```sql
UPDATE webhook_endpoints
SET is_enabled = 1,
    consecutive_failures = 0
WHERE id = 1;
```

## Monitoring and Troubleshooting

### Check Service Status

```sql
-- Recent webhook deliveries
SELECT
    ah.id,
    ah.recipient,
    ah.status,
    ah.status_message,
    ah.retry_count,
    ah.sent_at,
    ah.created_at,
    we.endpoint_name
FROM alert_history ah
LEFT JOIN webhook_endpoints we ON ah.recipient = we.id OR ah.recipient = we.endpoint_url
WHERE ah.alert_type = 'webhook'
  AND ah.created_at >= DATE_SUB(NOW(), INTERVAL 1 HOUR)
ORDER BY ah.created_at DESC;

-- Success rate by endpoint
SELECT
    we.endpoint_name,
    we.endpoint_url,
    COUNT(*) AS total_attempts,
    SUM(CASE WHEN ah.status = 'sent' THEN 1 ELSE 0 END) AS successful,
    SUM(CASE WHEN ah.status = 'failed' THEN 1 ELSE 0 END) AS failed,
    ROUND(100.0 * SUM(CASE WHEN ah.status = 'sent' THEN 1 ELSE 0 END) / COUNT(*), 2) AS success_rate
FROM alert_history ah
LEFT JOIN webhook_endpoints we ON ah.recipient = we.id OR ah.recipient = we.endpoint_url
WHERE ah.alert_type = 'webhook'
  AND ah.created_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
GROUP BY we.endpoint_name, we.endpoint_url
ORDER BY success_rate ASC;
```

### Log Messages

The service logs all activities with `[WebhookNotificationService]` prefix:

```
[WebhookNotificationService] WebhookNotificationService initialized - using JNDI DataSource jdbc/IWDB
[WebhookNotificationService] Starting WebhookNotificationService...
[WebhookNotificationService] WebhookNotificationService started - polling every 30 seconds
[WebhookNotificationService] Processing 3 pending webhook alerts
[WebhookNotificationService] Webhook sent successfully to https://hooks.slack.com/... - HTTP 200
[WebhookNotificationService] Webhook batch complete: 2 sent, 1 failed
[WebhookNotificationService ERROR] Failed to send webhook to https://api.example.com/webhook
[WebhookNotificationService] Alert ID 123 scheduled for retry 1 in 1 minutes
[WebhookNotificationService] WARNING: Webhook endpoint 5 has been disabled after 10 consecutive failures
```

### Common Issues and Solutions

#### Issue: Webhooks Not Sending

**Check:**
1. Service is running: Look for "WebhookNotificationService started" in logs
2. Service is enabled: Check `webhook.enabled=true` in monitoring.properties
3. Alert rules configured: Verify `notification_type` includes 'webhook' or 'both'
4. Endpoints are enabled: Check `is_enabled=1` in webhook_endpoints

**Solution:**
```sql
-- Verify webhook endpoints
SELECT id, endpoint_name, is_enabled, consecutive_failures
FROM webhook_endpoints;

-- Check pending alerts
SELECT COUNT(*) FROM alert_history WHERE status='pending' AND alert_type='webhook';
```

#### Issue: High Failure Rate

**Check:**
1. Endpoint URL is correct and accessible
2. Authentication credentials are valid
3. Timeout is appropriate for endpoint
4. Network connectivity from server

**Solution:**
```bash
# Test webhook endpoint manually
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"test":"message"}' \
  https://your-webhook-url.com/endpoint
```

#### Issue: Endpoint Disabled Automatically

**Cause:** Endpoint had 10 consecutive failures

**Solution:**
1. Fix the underlying issue (credentials, URL, firewall, etc.)
2. Test endpoint manually
3. Re-enable endpoint:

```sql
UPDATE webhook_endpoints
SET is_enabled = 1,
    consecutive_failures = 0
WHERE id = YOUR_ENDPOINT_ID;
```

#### Issue: Authentication Not Working

**Check:**
1. `auth_type` matches endpoint requirements
2. Credentials are correct and not expired
3. Custom headers are properly formatted JSON

**Solution:**
```sql
-- Verify authentication settings
SELECT id, endpoint_name, auth_type, auth_username, auth_token IS NOT NULL AS has_token
FROM webhook_endpoints
WHERE id = YOUR_ENDPOINT_ID;

-- Update authentication
UPDATE webhook_endpoints
SET auth_type = 'bearer',
    auth_token = 'NEW_TOKEN'
WHERE id = YOUR_ENDPOINT_ID;
```

## Performance Characteristics

- **Polling Interval**: 30 seconds (configurable)
- **Batch Size**: 50 webhooks per cycle
- **Typical Processing Time**: 1-3 seconds per batch (depends on endpoint response time)
- **Concurrent Requests**: Sequential (one at a time per batch)
- **Memory Usage**: ~2MB baseline + ~50KB per pending webhook
- **CPU Usage**: Minimal (< 1% during processing)

## Testing

### Manual Test

1. **Create test webhook endpoint:**

```sql
INSERT INTO webhook_endpoints (
    company_id, endpoint_name, endpoint_url, http_method,
    auth_type, is_enabled
) VALUES (
    1, 'Test Webhook', 'https://webhook.site/YOUR-UNIQUE-URL', 'POST', 'none', 1
);
```

2. **Create test alert:**

```sql
INSERT INTO alert_history (
    alert_rule_id, execution_id, alert_type, recipient,
    subject, message, payload_sent, status, created_at
) VALUES (
    1,
    NULL,
    'webhook',
    '1', -- webhook endpoint ID
    'Test Alert',
    'This is a test webhook notification',
    '{"event_type":"test","message":"Hello from InterWeave"}',
    'pending',
    NOW()
);
```

3. **Check webhook.site to verify delivery**

### Verify Service Running

```sql
-- Check recent webhook activity
SELECT id, recipient, status, sent_at, status_message
FROM alert_history
WHERE alert_type = 'webhook'
ORDER BY created_at DESC
LIMIT 10;
```

## Integration with AlertService

The WebhookNotificationService works seamlessly with AlertService:

1. **AlertService** evaluates rules when transaction fails
2. For webhook alerts, AlertService:
   - Looks up webhook endpoint IDs from alert rule
   - Creates entries in `alert_history` with `status='pending'`
   - Sets `payload_sent` to formatted JSON payload
3. **WebhookNotificationService** picks up pending alerts within 30 seconds
4. Service dispatches to configured endpoints
5. Updates delivery status in `alert_history`

## Best Practices

1. **Use HTTPS**: Always use HTTPS URLs for webhook endpoints
2. **Test Endpoints**: Test webhook URLs manually before configuring
3. **Monitor Health**: Regularly check endpoint health metrics
4. **Set Appropriate Timeouts**: Balance between reliability and responsiveness
5. **Secure Credentials**: Store auth tokens securely, rotate regularly
6. **Handle Retries**: Design receiving systems to handle duplicate alerts (idempotency)
7. **Log Everything**: Monitor logs for delivery issues
8. **Set Rate Limits**: Use `max_alerts_per_day` to prevent alert storms
9. **Use Cooldown**: Configure appropriate cooldown periods
10. **Alert on Failures**: Monitor webhook delivery failures and act promptly

## Future Enhancements

Planned features for future versions:

1. **Custom Payload Templates**: Support for Mustache/Handlebars templates
2. **Batch Webhooks**: Send multiple alerts in single request
3. **Signature Verification**: HMAC signatures for webhook security
4. **Webhook Metrics**: Dashboard for webhook delivery metrics
5. **Circuit Breaker**: Temporary disable on sustained failures
6. **Async Verification**: Webhook delivery confirmation callbacks
7. **Priority Queuing**: Priority-based webhook delivery
8. **Payload Compression**: Gzip compression for large payloads

## See Also

- [AlertService README](README_ALERT_SERVICE.md)
- [EmailNotificationService README](README_EMAIL_NOTIFICATION.md)
- [Monitoring Dashboard API Documentation](../../monitoring/API_ENDPOINTS.md)
- [Database Schema Migration](../../../../../_internal/sql/005_monitoring_schema.sql)
