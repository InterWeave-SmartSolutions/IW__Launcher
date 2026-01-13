# AlertService - Transaction Failure Alert System

## Overview

The **AlertService** is a core component of the IW_IDE Monitoring Dashboard that evaluates alert rules and triggers notifications when transactions fail. It provides intelligent alerting with threshold-based triggers, cooldown periods, and rate limiting to prevent alert storms.

## Features

### Core Capabilities
- ✅ **Immediate Alerts** - Trigger on first transaction failure
- ✅ **Threshold-Based Alerts** - Alert after N failures in X minutes
- ✅ **Cooldown Periods** - Prevent alert spam with configurable cooldowns
- ✅ **Rate Limiting** - Maximum alerts per day per rule
- ✅ **Multi-Channel Notifications** - Email and webhook support
- ✅ **Async Dispatch** - Non-blocking alert processing via thread pool
- ✅ **Multi-Tenant Support** - Company-level alert isolation
- ✅ **Alert History** - Complete audit trail in alert_history table

### Alert Rule Matching
Alert rules are evaluated based on:
- **Company ID** - Only rules for the failing transaction's company
- **Project ID** - Specific project or global (NULL = all projects)
- **Flow Name** - Specific flow or global (NULL = all flows)
- **Enabled Status** - Only enabled rules are evaluated
- **Alert Type** - alert_on_failure or alert_on_timeout

## Architecture

### Singleton Pattern
```java
AlertService alertService = AlertService.getInstance();
```

### Thread Pool
- Uses `ExecutorService` with 5 worker threads
- Daemon threads don't block JVM shutdown
- Graceful shutdown with 30-second timeout

### Database Tables Used
1. **alert_rules** - Alert configuration and state
2. **webhook_endpoints** - Webhook endpoint definitions
3. **transaction_executions** - Source of failure events
4. **alert_history** - Record of all sent alerts

## Usage

### Basic Integration

When a transaction fails, call `evaluateAlerts()`:

```java
import com.interweave.monitoring.service.AlertService;

public class MyTransactionProcessor {

    public void processTransaction() {
        String executionId = null;

        try {
            // Start transaction logging
            TransactionLogger logger = TransactionLogger.getInstance();
            executionId = logger.logStart(companyId, projectId, transformationId,
                                         "Order Processing", "transaction", "scheduler", null);

            // ... process transaction ...

            // Log success
            logger.logComplete(executionId, recordsProcessed, recordsFailed, 0);

        } catch (Exception e) {
            // Log failure
            TransactionLogger logger = TransactionLogger.getInstance();
            logger.logFailure(executionId, e.getMessage(),
                            e.getClass().getSimpleName(), getStackTrace(e));

            // Trigger alert evaluation (ASYNC - won't block)
            AlertService alertService = AlertService.getInstance();
            alertService.evaluateAlerts(
                executionId,
                companyId,
                projectId,
                "Order Processing",
                e.getMessage(),
                e.getClass().getSimpleName()
            );
        }
    }
}
```

### Integration with TransactionLogger

The typical integration pattern:

```java
// 1. Log transaction start
String executionId = TransactionLogger.getInstance().logStart(...);

try {
    // 2. Process transaction
    processRecords();

    // 3. Log success
    TransactionLogger.getInstance().logComplete(executionId, ...);

} catch (Exception e) {
    // 4. Log failure
    TransactionLogger.getInstance().logFailure(executionId, ...);

    // 5. Evaluate alerts (async)
    AlertService.getInstance().evaluateAlerts(
        executionId, companyId, projectId, flowName,
        e.getMessage(), e.getClass().getSimpleName()
    );
}
```

## Alert Evaluation Logic

### Step 1: Find Matching Rules
```sql
SELECT * FROM alert_rules
WHERE company_id = ?
  AND is_enabled = 1
  AND alert_on_failure = 1
  AND (project_id IS NULL OR project_id = ?)
  AND (flow_name IS NULL OR flow_name = ?)
```

### Step 2: Check Threshold (if > 1)
For threshold-based alerts, count recent failures:
```sql
SELECT COUNT(*) FROM transaction_executions
WHERE company_id = ?
  AND status IN ('failed', 'timeout')
  AND started_at >= DATE_SUB(NOW(), INTERVAL ? MINUTE)
```

If `failure_count < threshold`, skip alert.

### Step 3: Check Cooldown
```java
long minutesSinceLastAlert = (now - lastTriggeredAt) / 60000;
if (minutesSinceLastAlert < cooldownMinutes) {
    skip; // In cooldown period
}
```

### Step 4: Check Daily Limit
```java
if (alertsSentToday >= maxAlertsPerDay) {
    skip; // Reached daily limit
}
```

### Step 5: Trigger Alert
- Record in `alert_history` with status='pending'
- Update `alert_rules.last_triggered_at`
- Increment `alert_rules.alerts_sent_today`

## Alert Types

### Immediate Alert
```sql
failure_threshold = 1
threshold_window_minutes = 60  -- irrelevant for immediate
```
Fires on **every** failure (subject to cooldown and daily limit).

### Threshold Alert
```sql
failure_threshold = 3
threshold_window_minutes = 15
```
Fires when **3 or more failures** occur within **15 minutes**.

## Notification Channels

### Email Notifications
```sql
notification_type = 'email'
email_recipients = 'ops@company.com,dev@company.com'
```
Records one alert_history entry per recipient with:
- `alert_type = 'email'`
- `recipient = email_address`
- `status = 'pending'` (EmailNotificationService will process)

### Webhook Notifications
```sql
notification_type = 'webhook'
webhook_endpoint_ids = '1,2,3'
```
Records one alert_history entry per webhook with:
- `alert_type = 'webhook'`
- `recipient = webhook_url`
- `payload_sent = JSON payload`
- `status = 'pending'` (WebhookNotificationService will process)

### Both Channels
```sql
notification_type = 'both'
```
Creates alert_history entries for both email and webhook.

## Alert Message Format

### Email Message
```
Transaction Failure Detected

Flow Name: Customer Order Sync
Execution ID: CustomerOrderSync-20260109143022-5847
Error Code: SQLException
Error Message: Connection timeout after 30 seconds

Please check the monitoring dashboard for more details.
```

### Webhook Payload (JSON)
```json
{
  "event_type": "transaction_failure",
  "flow_name": "Customer Order Sync",
  "execution_id": "CustomerOrderSync-20260109143022-5847",
  "error_code": "SQLException",
  "error_message": "Connection timeout after 30 seconds",
  "timestamp": "2026-01-09T14:30:22Z"
}
```

## Configuration

### Alert Rule Settings
```sql
INSERT INTO alert_rules (
    company_id, rule_name, flow_name,
    alert_on_failure, alert_on_timeout,
    failure_threshold, threshold_window_minutes,
    notification_type, email_recipients,
    cooldown_minutes, max_alerts_per_day,
    is_enabled
) VALUES (
    1, 'Critical Order Sync Failure',
    'Customer Order Sync',
    1, 1,  -- Alert on failure and timeout
    3, 15,  -- 3 failures in 15 minutes
    'both', 'ops@company.com',
    30, 50,  -- 30-min cooldown, max 50/day
    1
);
```

### Thread Pool Settings
Currently hardcoded in AlertService:
```java
private static final int THREAD_POOL_SIZE = 5;
```

To modify, edit the constant in `AlertService.java` and recompile.

## Lifecycle Management

### Initialization
```java
// In MonitoringContextListener.contextInitialized()
AlertService alertService = AlertService.getInstance();
// Thread pool created automatically
```

### Shutdown
```java
// In MonitoringContextListener.contextDestroyed()
AlertService alertService = AlertService.getInstance();
alertService.shutdown();
// Waits up to 30 seconds for pending alerts
```

## Performance Characteristics

### Async Processing
- `evaluateAlerts()` returns immediately (< 1ms)
- Alert evaluation runs on worker thread
- No impact on transaction throughput

### Database Impact
Per alert evaluation:
- 1 SELECT (find matching rules) - indexed
- N SELECTs (threshold checks) - indexed, fast
- M SELECTs (webhook URLs) - cached in app
- M INSERTs (alert_history) - async, non-blocking
- 1 UPDATE (alert_rules state) - async

Typical evaluation: **< 50ms per failure**

### Thread Pool Sizing
- 5 threads handle ~100 failures/second
- Queue unbounded (unlikely to block)
- Increase `THREAD_POOL_SIZE` if needed

## Monitoring

### Check Alert Service Status
```sql
-- Count pending alerts
SELECT COUNT(*) FROM alert_history WHERE status = 'pending';

-- Check recently triggered rules
SELECT rule_name, last_triggered_at, alerts_sent_today
FROM alert_rules
WHERE is_enabled = 1 AND last_triggered_at >= DATE_SUB(NOW(), INTERVAL 1 HOUR);

-- Alert delivery success rate
SELECT
    status,
    COUNT(*) as count,
    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER(), 2) as percent
FROM alert_history
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
GROUP BY status;
```

### Check for Alert Storms
```sql
-- Rules hitting daily limits
SELECT rule_name, alerts_sent_today, max_alerts_per_day
FROM alert_rules
WHERE alerts_sent_today >= max_alerts_per_day;

-- Rules in cooldown
SELECT rule_name, last_triggered_at, cooldown_minutes,
       TIMESTAMPDIFF(MINUTE, last_triggered_at, NOW()) as minutes_since_alert
FROM alert_rules
WHERE is_enabled = 1
  AND last_triggered_at IS NOT NULL
  AND TIMESTAMPDIFF(MINUTE, last_triggered_at, NOW()) < cooldown_minutes;
```

## Troubleshooting

### Alerts Not Firing

**Check 1: Rule is enabled**
```sql
SELECT id, rule_name, is_enabled FROM alert_rules WHERE id = ?;
```

**Check 2: Rule matches transaction**
```sql
-- Does company_id match?
-- Does project_id match (or is NULL)?
-- Does flow_name match (or is NULL)?
```

**Check 3: In cooldown period**
```sql
SELECT
    last_triggered_at,
    cooldown_minutes,
    TIMESTAMPDIFF(MINUTE, last_triggered_at, NOW()) as minutes_ago
FROM alert_rules WHERE id = ?;
```

**Check 4: Daily limit reached**
```sql
SELECT alerts_sent_today, max_alerts_per_day FROM alert_rules WHERE id = ?;
```

**Check 5: Threshold not met**
```sql
-- Count recent failures matching this rule
SELECT COUNT(*) FROM transaction_executions
WHERE company_id = ? AND flow_name = ?
  AND status IN ('failed', 'timeout')
  AND started_at >= DATE_SUB(NOW(), INTERVAL ? MINUTE);
```

### Thread Pool Exhausted

Check logs for:
```
[AlertService] RejectedExecutionException
```

**Solution**: Increase `THREAD_POOL_SIZE` or investigate why alerts are backing up.

### Database Connection Pool Exhausted

Check logs for:
```
[AlertService ERROR] Cannot get database connection
```

**Solution**: Increase JNDI DataSource pool size in Tomcat's `context.xml`.

## Daily Reset Job

Alert rules have `alerts_sent_today` counter that should be reset daily. Create a scheduled job:

```sql
-- Reset daily counters (run at midnight)
UPDATE alert_rules SET alerts_sent_today = 0;
```

Or use a database scheduled event:
```sql
CREATE EVENT reset_daily_alert_counters
ON SCHEDULE EVERY 1 DAY
STARTS '2026-01-10 00:00:00'
DO
  UPDATE alert_rules SET alerts_sent_today = 0;
```

## Integration Checklist

When integrating AlertService into your code:

- [ ] Import `com.interweave.monitoring.service.AlertService`
- [ ] Call `AlertService.getInstance().evaluateAlerts()` after `logFailure()`
- [ ] Pass all required parameters: executionId, companyId, projectId, flowName, errorMessage, errorCode
- [ ] Verify alert rules exist for your flows in `alert_rules` table
- [ ] Test with actual transaction failures
- [ ] Monitor `alert_history` table for recorded alerts
- [ ] Verify EmailNotificationService and WebhookNotificationService process pending alerts

## Next Steps

After implementing AlertService:

1. **Phase 4.2**: Implement EmailNotificationService to process pending email alerts
2. **Phase 4.3**: Implement WebhookNotificationService to process pending webhook alerts
3. **Phase 4.4**: Create monitoring.properties configuration file
4. **Phase 5**: Build dashboard UI for managing alert rules

## See Also

- `TransactionLogger.java` - Transaction execution logging
- `AlertConfigApiServlet.java` - REST API for managing alert rules
- `MonitoringContextListener.java` - Service lifecycle management
- `005_monitoring_schema.sql` - Database schema for alerting

---

**Author**: InterWeave Monitoring Dashboard Team
**Version**: 1.0
**Last Updated**: 2026-01-09
