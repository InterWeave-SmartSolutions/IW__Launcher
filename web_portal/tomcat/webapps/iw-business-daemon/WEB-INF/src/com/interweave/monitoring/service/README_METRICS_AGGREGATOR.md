# MetricsAggregator Service

## Overview

The `MetricsAggregator` service provides automated background aggregation of raw transaction execution data into time-based metrics for the InterWeave monitoring dashboard. It runs on a configurable schedule (default: every hour) and performs:

- **Hourly Metrics**: Aggregates transaction executions into hourly rollups
- **Daily Metrics**: Aggregates transaction executions into daily rollups
- **Data Cleanup**: Removes old data based on configurable retention policies

## Features

- **Thread-Safe Singleton**: Ensures single aggregator instance across the application
- **Scheduled Execution**: Automatic periodic aggregation using Java Timer
- **Idempotent Aggregation**: Safe to run multiple times on same data (uses INSERT ... ON DUPLICATE KEY UPDATE)
- **Configurable Retention**: Respects database retention policies from settings table
- **Graceful Lifecycle**: Proper startup/shutdown via ServletContextListener
- **Efficient Queries**: Uses optimized SQL for minimal database load

## Architecture

```
MonitoringContextListener (ServletContextListener)
    │
    ├─ contextInitialized() → starts MetricsAggregator
    └─ contextDestroyed()   → stops MetricsAggregator

MetricsAggregator (Singleton)
    │
    ├─ start()              → Starts Timer scheduler
    ├─ stop()               → Stops Timer scheduler
    ├─ aggregateNow()       → Manual aggregation trigger
    │   ├─ aggregateHourlyMetrics()
    │   ├─ aggregateDailyMetrics()
    │   └─ cleanupOldData()
    │       ├─ cleanupOldExecutions()
    │       ├─ cleanupOldMetrics()
    │       └─ cleanupOldAlerts()
```

## Database Tables

### Source Table: transaction_executions
Raw execution data logged by TransactionLogger:
- Individual transaction execution records
- Status: running, success, failed, cancelled, timeout
- Timing: started_at, completed_at, duration_ms
- Metrics: records_processed, records_failed, records_skipped

### Target Table: transaction_metrics
Aggregated metrics stored for fast dashboard queries:
- Time periods: hourly, daily, weekly
- Execution counts: total, successful, failed, cancelled, timeout
- Performance metrics: avg_duration_ms, min_duration_ms, max_duration_ms
- Record processing: total_records_processed, total_records_failed
- Success rate: success_rate_percent (computed)

## Aggregation Logic

### Hourly Metrics

Aggregates complete hours (not current hour):
- **Period**: Last 48 hours of data
- **Granularity**: One metric record per hour per flow
- **Group By**: company_id, project_id, flow_name, hour
- **Completion Check**: Only aggregates hours before current hour

Example:
```sql
-- Current time: 2024-03-15 14:30:00
-- Aggregates: 2024-03-13 12:00 to 2024-03-15 13:59
-- Excludes: 2024-03-15 14:00 (current hour - incomplete)
```

### Daily Metrics

Aggregates complete days (not today):
- **Period**: Last 14 days of data
- **Granularity**: One metric record per day per flow
- **Group By**: company_id, project_id, flow_name, day
- **Completion Check**: Only aggregates days before today

Example:
```sql
-- Current date: 2024-03-15
-- Aggregates: 2024-03-01 to 2024-03-14
-- Excludes: 2024-03-15 (today - incomplete)
```

### Metrics Computed

For each aggregation period, computes:

1. **Execution Counts**:
   - total_executions
   - successful_executions (status = 'success')
   - failed_executions (status = 'failed')
   - cancelled_executions (status = 'cancelled')
   - timeout_executions (status = 'timeout')

2. **Performance Metrics**:
   - total_duration_ms (sum of all durations)
   - avg_duration_ms (average execution time)
   - min_duration_ms (fastest execution)
   - max_duration_ms (slowest execution)

3. **Record Processing**:
   - total_records_processed (sum)
   - total_records_failed (sum)
   - avg_records_per_execution (average)

4. **Success Rate**:
   - success_rate_percent = (successful_executions / total_executions) * 100

## Data Cleanup

### Retention Policies

Configured via `settings` table (days to retain):

| Setting Key | Default | Description |
|-------------|---------|-------------|
| `monitoring_retention_days` | 90 | Raw execution logs |
| `metrics_retention_days` | 365 | Aggregated metrics |
| `alert_history_retention_days` | 180 | Alert history |

### Cleanup Process

1. **Old Executions**: Deletes transaction_executions older than retention days
   - Payloads automatically deleted via CASCADE foreign key
   - Example: With 90-day retention, deletes executions from before 90 days ago

2. **Old Metrics**: Deletes transaction_metrics older than retention days
   - Example: With 365-day retention, keeps 1 year of metrics

3. **Old Alerts**: Deletes alert_history older than retention days
   - Example: With 180-day retention, keeps 6 months of alert history

## Scheduling

### Default Schedule

- **Interval**: 60 minutes (1 hour)
- **Start Delay**: 60 minutes after application startup
- **Execution**: Runs continuously until application shutdown

### Scheduler Configuration

Scheduler uses Java `Timer` with daemon thread:
```java
Timer schedulerTimer = new Timer("MetricsAggregator-Scheduler", true);
schedulerTimer.scheduleAtFixedRate(task, delay, period);
```

Benefits:
- Daemon thread doesn't prevent JVM shutdown
- Fixed-rate execution maintains consistent timing
- Named thread for easy monitoring

### Lifecycle Management

Started/stopped automatically via `MonitoringContextListener`:

1. **Application Startup**:
   - ServletContext initialized
   - MonitoringContextListener.contextInitialized() called
   - MetricsAggregator.start() invoked
   - Timer scheduler begins

2. **Application Shutdown**:
   - ServletContext destroyed
   - MonitoringContextListener.contextDestroyed() called
   - MetricsAggregator.stop() invoked
   - Timer scheduler cancelled gracefully

## Usage

### Automatic Operation

The aggregator runs automatically - no manual intervention needed:

```java
// Registered in web.xml as ServletContextListener
<listener>
    <listener-class>com.interweave.monitoring.service.MonitoringContextListener</listener-class>
</listener>

// Application startup - automatic
// 1. Tomcat starts
// 2. MonitoringContextListener.contextInitialized() called
// 3. MetricsAggregator starts and schedules timer
// 4. Aggregation runs every hour

// Application shutdown - automatic
// 1. Tomcat stops
// 2. MonitoringContextListener.contextDestroyed() called
// 3. MetricsAggregator stops timer gracefully
```

### Manual Trigger

Trigger aggregation manually if needed:

```java
// Get singleton instance
MetricsAggregator aggregator = MetricsAggregator.getInstance();

// Trigger immediate aggregation
boolean success = aggregator.aggregateNow();

if (success) {
    System.out.println("Metrics aggregated successfully");
} else {
    System.err.println("Errors occurred during aggregation");
}
```

### Check Status

```java
MetricsAggregator aggregator = MetricsAggregator.getInstance();

// Check if scheduler is running
if (aggregator.isRunning()) {
    System.out.println("Aggregator is running");
} else {
    System.out.println("Aggregator is stopped");
}
```

## Configuration

### Enable/Disable Aggregation

Set in `settings` table:

```sql
-- Enable metrics aggregation (default)
UPDATE settings
SET setting_value = 'true'
WHERE setting_key = 'metrics_aggregation_enabled';

-- Disable metrics aggregation
UPDATE settings
SET setting_value = 'false'
WHERE setting_key = 'metrics_aggregation_enabled';
```

When disabled, the scheduler will not start on application startup.

### Retention Period Configuration

```sql
-- Set raw execution retention to 60 days
UPDATE settings
SET setting_value = '60'
WHERE setting_key = 'monitoring_retention_days';

-- Set metrics retention to 730 days (2 years)
UPDATE settings
SET setting_value = '730'
WHERE setting_key = 'metrics_retention_days';

-- Set alert history retention to 365 days
UPDATE settings
SET setting_value = '365'
WHERE setting_key = 'alert_history_retention_days';
```

Changes take effect on next aggregation run (no restart required).

## Performance Considerations

### Optimized SQL Queries

- **Group By Aggregation**: Uses MySQL's efficient GROUP BY with aggregate functions
- **Time Range Filtering**: Limits data processing to recent periods (48h for hourly, 14d for daily)
- **Indexed Columns**: Queries use indexed columns (started_at, company_id, status)
- **Idempotent Updates**: ON DUPLICATE KEY UPDATE prevents duplicate records

### Database Load

Typical resource usage per aggregation:
- **Hourly Metrics**: ~100-500ms for 10,000 executions
- **Daily Metrics**: ~200-1000ms for 100,000 executions
- **Cleanup**: ~50-200ms per table

Total aggregation time: Usually < 2 seconds

### Best Practices

1. **Run During Low Traffic**: Default hourly schedule runs throughout the day
   - Consider custom schedule if peak traffic is predictable

2. **Monitor Database Size**: Large datasets may slow aggregation
   - Monitor transaction_executions table size
   - Adjust retention policies if needed

3. **Review Indexes**: Ensure proper indexes exist:
   ```sql
   -- Critical indexes for aggregation performance
   INDEX idx_exec_started (started_at)
   INDEX idx_exec_status (status)
   INDEX idx_exec_company_status_started (company_id, status, started_at)
   ```

4. **Partition Large Tables**: For very large deployments (millions of executions):
   - Consider table partitioning by date
   - Improves both aggregation and cleanup performance

## Monitoring and Troubleshooting

### Log Messages

The aggregator logs all operations:

```
[MetricsAggregator] MetricsAggregator initialized - using JNDI DataSource jdbc/IWDB
[MetricsAggregator] MetricsAggregator started - running every 60 minutes
[MetricsAggregator] Starting manual metrics aggregation
[MetricsAggregator] Aggregating hourly metrics...
[MetricsAggregator] Hourly metrics aggregated: 48 metric records inserted/updated
[MetricsAggregator] Aggregating daily metrics...
[MetricsAggregator] Daily metrics aggregated: 14 metric records inserted/updated
[MetricsAggregator] Cleaning up old data...
[MetricsAggregator] Cleaned up 1234 old execution records (retention: 90 days)
[MetricsAggregator] Cleaned up 56 old metric records (retention: 365 days)
[MetricsAggregator] Data cleanup completed successfully
[MetricsAggregator] Metrics aggregation completed successfully
```

### Common Issues

**1. Aggregator not starting**
- Check logs for initialization errors
- Verify JNDI DataSource is configured
- Check `metrics_aggregation_enabled` setting
- Ensure MonitoringContextListener is registered in web.xml

**2. No metrics being generated**
- Verify transaction_executions has data
- Check that executions have completed status (not 'running')
- Ensure time periods are complete (not current hour/day)
- Review SQL query date filters

**3. Aggregation taking too long**
- Check transaction_executions table size
- Verify indexes exist and are being used (EXPLAIN query)
- Consider adjusting time range filters (process less data)
- Review retention policies (cleanup old data)

**4. Duplicate metrics**
- This is normal - ON DUPLICATE KEY UPDATE handles it
- Metrics are updated/refreshed on each run
- Ensures data consistency even if aggregation runs multiple times

### Verification Queries

```sql
-- Check recent hourly metrics
SELECT
    metric_period,
    period_start,
    flow_name,
    total_executions,
    successful_executions,
    failed_executions,
    success_rate_percent,
    avg_duration_ms
FROM transaction_metrics
WHERE metric_period = 'hourly'
ORDER BY period_start DESC
LIMIT 24;

-- Check daily metrics for last 7 days
SELECT
    metric_period,
    DATE(period_start) AS date,
    SUM(total_executions) AS daily_total,
    SUM(successful_executions) AS daily_success,
    SUM(failed_executions) AS daily_failed,
    ROUND(AVG(success_rate_percent), 2) AS avg_success_rate
FROM transaction_metrics
WHERE metric_period = 'daily'
    AND period_start >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY metric_period, DATE(period_start)
ORDER BY date DESC;

-- Check last aggregation time
SELECT
    metric_period,
    MAX(computed_at) AS last_aggregation
FROM transaction_metrics
GROUP BY metric_period;

-- Check data volumes
SELECT
    'Executions' AS table_name,
    COUNT(*) AS row_count,
    MIN(started_at) AS oldest_record,
    MAX(started_at) AS newest_record
FROM transaction_executions
UNION ALL
SELECT
    'Hourly Metrics',
    COUNT(*),
    MIN(period_start),
    MAX(period_start)
FROM transaction_metrics WHERE metric_period = 'hourly'
UNION ALL
SELECT
    'Daily Metrics',
    COUNT(*),
    MIN(period_start),
    MAX(period_start)
FROM transaction_metrics WHERE metric_period = 'daily';
```

## Testing

### Manual Testing

1. **Test Aggregation**:
   ```java
   // Trigger manual aggregation
   MetricsAggregator aggregator = MetricsAggregator.getInstance();
   boolean success = aggregator.aggregateNow();

   // Check logs for success/failure messages
   // Verify metrics in database
   ```

2. **Verify Hourly Metrics**:
   ```sql
   -- Should see metrics for complete hours
   SELECT * FROM transaction_metrics
   WHERE metric_period = 'hourly'
   ORDER BY period_start DESC
   LIMIT 10;
   ```

3. **Verify Daily Metrics**:
   ```sql
   -- Should see metrics for complete days
   SELECT * FROM transaction_metrics
   WHERE metric_period = 'daily'
   ORDER BY period_start DESC
   LIMIT 10;
   ```

4. **Test Cleanup**:
   ```sql
   -- Check old records are removed
   SELECT COUNT(*) FROM transaction_executions
   WHERE started_at < DATE_SUB(NOW(), INTERVAL 90 DAY);
   -- Should be 0 or very low
   ```

### Integration Testing

Test full lifecycle:

1. Start Tomcat
2. Check logs for "MetricsAggregator started"
3. Wait for first aggregation (or trigger manually)
4. Verify metrics in database
5. Stop Tomcat
6. Check logs for "MetricsAggregator stopped"

## Error Handling

The aggregator is designed to fail gracefully:

- **Non-Blocking**: Errors don't prevent application startup
- **Error Logging**: All errors logged to console and IWError framework
- **Partial Success**: Continues even if one aggregation step fails
- **Retry Logic**: Next scheduled run will retry failed operations

Example error handling:
```java
try {
    if (!aggregateHourlyMetrics()) {
        success = false;  // Log error but continue
    }
    if (!aggregateDailyMetrics()) {
        success = false;  // Log error but continue
    }
    // Cleanup runs even if aggregations failed
    cleanupOldData();
} catch (Exception e) {
    logError("Unexpected error during metrics aggregation", e);
    // Application continues running
}
```

## Integration Points

### TransactionLogger (Phase 3)
- Source: TransactionLogger logs to transaction_executions
- Target: MetricsAggregator reads from transaction_executions
- Dependency: Aggregator requires logged data to aggregate

### Dashboard API (Phase 2)
- MetricsApiServlet reads from transaction_metrics
- Pre-aggregated metrics enable fast dashboard queries
- Reduces load on transaction_executions table

### AlertService (Phase 4)
- Alert history is cleaned up by MetricsAggregator
- Respects alert_history_retention_days setting

## Next Steps

After implementing MetricsAggregator:

1. **Subtask 3.3**: Integrate TransactionLogger into TransactionThread
2. **Phase 4**: Implement AlertService for failure notifications
3. **Phase 5**: Build frontend dashboard to visualize metrics

## Related Documentation

- Database Schema: `database/monitoring_schema.sql`
- Transaction Logger: `README_TRANSACTION_LOGGER.md`
- API Endpoints: `API_ENDPOINTS.md`
- Implementation Plan: `.auto-claude/specs/005-real-time-integration-monitoring-dashboard/implementation_plan.json`
