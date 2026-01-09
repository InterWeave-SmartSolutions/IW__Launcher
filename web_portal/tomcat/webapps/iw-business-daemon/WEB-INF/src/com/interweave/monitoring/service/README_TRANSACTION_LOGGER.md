# TransactionLogger Service

## Overview

The `TransactionLogger` service provides comprehensive transaction execution logging for the InterWeave monitoring dashboard. It captures detailed information about transaction start, progress, completion, and failure events, storing data in the `transaction_executions` and `transaction_payloads` tables.

## Features

- **Thread-Safe Singleton**: Ensures safe concurrent access from multiple transaction threads
- **Comprehensive Logging**: Captures start, progress, completion, failure, and timeout events
- **Payload Storage**: Stores request/response data in separate table for drill-down analysis
- **Error Handling**: Graceful error handling that doesn't impact transaction execution
- **Resource Management**: Efficient connection pooling via JNDI DataSource
- **Auto-Generation**: Automatically generates unique execution IDs

## Architecture

```
TransactionLogger (Singleton)
    │
    ├─ logStart()       → Creates execution record with 'running' status
    ├─ logProgress()    → Updates records processed/failed counts
    ├─ logComplete()    → Marks success, calculates duration
    ├─ logFailure()     → Marks failed, stores error details
    ├─ logTimeout()     → Marks timeout
    └─ logPayload()     → Stores request/response data
```

## Database Tables

### transaction_executions
Main execution log with:
- Execution metadata (id, execution_id, flow_name, status)
- Timing information (started_at, completed_at, duration_ms)
- Processing metrics (records_processed, records_failed, records_skipped)
- Error details (error_message, error_code, stack_trace)
- Context (company_id, project_id, triggered_by, server_hostname)

### transaction_payloads
Payload storage with:
- Reference to execution (execution_id foreign key)
- Payload type (request, response, error_context, debug)
- Payload format (xml, json, text, binary)
- Payload data (LONGTEXT, auto-truncated if exceeds 10MB)
- Source and destination system information

## Usage Examples

### Basic Transaction Logging

```java
import com.interweave.monitoring.service.TransactionLogger;

// Get singleton instance
TransactionLogger logger = TransactionLogger.getInstance();

// Log transaction start
String executionId = logger.logStart(
    companyId,           // Integer or null
    projectId,           // Integer or null
    transformationId,    // Integer or null
    "Customer Order Sync",  // Flow name (required)
    "transaction",       // Flow type: transaction, utility, query
    "scheduler",         // Triggered by: scheduler, manual, webhook, api
    userId               // Integer or null for scheduled executions
);

if (executionId != null) {
    // Transaction started successfully
    System.out.println("Started execution: " + executionId);
}
```

### Logging Progress During Execution

```java
// Update progress periodically during processing
int recordsProcessed = 0;
int recordsFailed = 0;

for (Record record : records) {
    try {
        processRecord(record);
        recordsProcessed++;

        // Log progress every 100 records
        if (recordsProcessed % 100 == 0) {
            logger.logProgress(executionId, recordsProcessed, recordsFailed);
        }
    } catch (Exception e) {
        recordsFailed++;
    }
}
```

### Logging Successful Completion

```java
// On successful completion
boolean logged = logger.logComplete(
    executionId,
    recordsProcessed,  // Total records processed
    recordsFailed,     // Total records failed
    recordsSkipped     // Total records skipped
);

if (logged) {
    System.out.println("Transaction completed successfully");
}
```

### Logging Failure

```java
try {
    // Transaction processing
    processTransaction();
} catch (Exception e) {
    // Log failure with error details
    String errorMessage = e.getMessage();
    String errorCode = e.getClass().getSimpleName();
    String stackTrace = getStackTrace(e);

    logger.logFailure(executionId, errorMessage, errorCode, stackTrace);
}
```

### Logging Timeout

```java
// Check if transaction exceeded maximum execution time
if (duration > MAX_EXECUTION_TIME) {
    logger.logTimeout(
        executionId,
        "Transaction exceeded maximum execution time of " + MAX_EXECUTION_TIME + "ms"
    );
}
```

### Logging Payloads

```java
// Log request payload before sending to external system
logger.logPayload(
    executionId,
    "request",           // Payload type: request, response, error_context, debug
    "xml",               // Format: xml, json, text, binary
    requestXml,          // Actual payload data
    "InterWeave",        // Source system
    "Salesforce"         // Destination system
);

// Log response payload after receiving from external system
logger.logPayload(
    executionId,
    "response",
    "json",
    responseJson,
    "Salesforce",
    "InterWeave"
);
```

## Method Reference

### logStart()

```java
public String logStart(
    Integer companyId,          // Company that owns this transaction (nullable)
    Integer projectId,          // Project containing the flow (nullable)
    Integer transformationId,   // Transformation being executed (nullable)
    String flowName,            // Name of flow (required)
    String flowType,            // Type: transaction, utility, query (default: transaction)
    String triggeredBy,         // How triggered: scheduler, manual, webhook, api (default: scheduler)
    Integer triggeredByUserId   // User who triggered (nullable)
)
```

**Returns:** Generated execution ID string, or null if logging failed

**Example:**
```java
String execId = logger.logStart(123, 456, 789, "Order Sync", "transaction", "scheduler", null);
```

### logProgress()

```java
public boolean logProgress(
    String executionId,      // Execution ID from logStart()
    int recordsProcessed,    // Total records processed so far
    int recordsFailed        // Total records failed so far
)
```

**Returns:** true if update successful, false otherwise

**Thread Safety:** Can be called multiple times concurrently

### logComplete()

```java
public boolean logComplete(
    String executionId,      // Execution ID from logStart()
    int recordsProcessed,    // Final count of processed records
    int recordsFailed,       // Final count of failed records
    int recordsSkipped       // Final count of skipped records
)
```

**Returns:** true if update successful, false otherwise

**Side Effects:**
- Sets status to 'success'
- Sets completed_at timestamp
- Calculates duration_ms automatically

### logFailure()

```java
public boolean logFailure(
    String executionId,    // Execution ID from logStart()
    String errorMessage,   // Primary error message (max 5000 chars, auto-truncated)
    String errorCode,      // Error code or exception class name
    String stackTrace      // Full stack trace (max 10000 chars, auto-truncated)
)
```

**Returns:** true if update successful, false otherwise

**Side Effects:**
- Sets status to 'failed'
- Sets completed_at timestamp
- Calculates duration_ms automatically
- Stores error details for troubleshooting

### logTimeout()

```java
public boolean logTimeout(
    String executionId,      // Execution ID from logStart()
    String timeoutMessage    // Description of the timeout
)
```

**Returns:** true if update successful, false otherwise

**Side Effects:**
- Sets status to 'timeout'
- Sets error_code to 'TIMEOUT'
- Sets completed_at and calculates duration

### logPayload()

```java
public boolean logPayload(
    String executionId,          // Execution ID from logStart()
    String payloadType,          // Type: request, response, error_context, debug
    String payloadFormat,        // Format: xml, json, text, binary
    String payloadData,          // Actual payload content (max 10MB, auto-truncated)
    String sourceSystem,         // System that generated payload (nullable)
    String destinationSystem     // Target system (nullable)
)
```

**Returns:** true if payload logged successfully, false otherwise

**Payload Size Limit:** 10MB (configurable via settings table)

## Thread Safety

The `TransactionLogger` is designed to be thread-safe:

1. **Singleton Pattern**: Double-checked locking ensures single instance
2. **Stateless Operations**: Each method uses its own database connection
3. **Connection Pooling**: JNDI DataSource handles connection concurrency
4. **Independent Transactions**: Each log operation is independent

Safe for concurrent use:
```java
// Multiple threads can log simultaneously
Thread t1 = new Thread(() -> {
    String exec1 = logger.logStart(...);
    logger.logComplete(exec1, ...);
});

Thread t2 = new Thread(() -> {
    String exec2 = logger.logStart(...);
    logger.logProgress(exec2, ...);
});

t1.start();
t2.start();
```

## Error Handling

The logger is designed to fail gracefully and never throw exceptions:

- **Null Return Values**: Methods return null or false on failure
- **Logging Errors**: Errors are logged to console and IWError framework
- **No Transaction Impact**: Logging failures don't affect transaction execution
- **Resource Cleanup**: Connections always closed via try-finally

Example error handling:
```java
String executionId = logger.logStart(...);
if (executionId == null) {
    // Logging failed, but continue with transaction
    System.err.println("Warning: Failed to log transaction start");
}

// Transaction continues regardless of logging success
processTransaction();
```

## Performance Considerations

### Efficient Database Operations

- **Connection Pooling**: Uses JNDI DataSource for efficient connection management
- **Prepared Statements**: Prevents SQL injection and improves performance
- **Minimal Queries**: Each method executes only 1-2 SQL statements
- **Payload Separation**: Large payloads stored in separate table to keep main table fast

### Best Practices

1. **Log Progress Sparingly**: Don't log progress on every record
   ```java
   // Good: Log every 100 records
   if (count % 100 == 0) logger.logProgress(execId, count, failed);

   // Bad: Log every record (too slow)
   logger.logProgress(execId, count++, failed);
   ```

2. **Log Payloads Selectively**: Only log payloads when needed
   ```java
   // Log only on failure for debugging
   if (failed) {
       logger.logPayload(execId, "error_context", "json", errorData, null, null);
   }
   ```

3. **Async Logging**: Consider wrapping in ExecutorService for high-volume transactions
   ```java
   ExecutorService executor = Executors.newFixedThreadPool(5);
   executor.submit(() -> logger.logProgress(execId, count, failed));
   ```

## Integration with Existing Code

### Integration Points

The `TransactionLogger` should be integrated at these points in transaction execution:

1. **Transaction Start**: Beginning of TransactionThread.run()
2. **Progress Updates**: During record processing loops
3. **Completion**: After successful processing
4. **Failure**: In catch blocks for exception handling

See subtask 3.3 for detailed integration with `TransactionThread`.

## Configuration

### Database Settings

Monitoring behavior is configured via `settings` table:

```sql
-- Enable/disable monitoring
UPDATE settings SET setting_value = 'true' WHERE setting_key = 'monitoring_enabled';

-- Retention periods
UPDATE settings SET setting_value = '90' WHERE setting_key = 'monitoring_retention_days';

-- Max payload size (MB)
UPDATE settings SET setting_value = '10' WHERE setting_key = 'max_payload_size_mb';
```

### JNDI DataSource

Requires JNDI DataSource configured in `context.xml`:

```xml
<Resource name="jdbc/IWDB"
          auth="Container"
          type="javax.sql.DataSource"
          maxTotal="100"
          maxIdle="30"
          maxWaitMillis="10000"
          ... />
```

## Testing

### Manual Testing

```java
public static void main(String[] args) {
    TransactionLogger logger = TransactionLogger.getInstance();

    // Test start
    String execId = logger.logStart(1, 2, 3, "Test Flow", "transaction", "manual", 1);
    System.out.println("Started: " + execId);

    // Test progress
    logger.logProgress(execId, 50, 2);

    // Test completion
    logger.logComplete(execId, 100, 5, 0);

    // Verify in database
    // SELECT * FROM transaction_executions WHERE execution_id = ?
}
```

### Verification Queries

```sql
-- Check recent executions
SELECT execution_id, flow_name, status, started_at, duration_ms, records_processed
FROM transaction_executions
ORDER BY started_at DESC
LIMIT 10;

-- Check payloads
SELECT te.execution_id, tp.payload_type, tp.payload_format, tp.payload_size
FROM transaction_payloads tp
JOIN transaction_executions te ON tp.execution_id = te.id
ORDER BY tp.captured_at DESC
LIMIT 10;
```

## Troubleshooting

### Common Issues

**1. executionId returns null**
- Check database connection (JNDI DataSource configured?)
- Verify `transaction_executions` table exists
- Check logs for SQLException details

**2. Updates not reflecting in database**
- Verify execution_id exists and status is 'running'
- Check for database permissions issues
- Verify foreign key constraints

**3. Payloads not storing**
- Check max payload size setting
- Verify `transaction_payloads` table exists
- Ensure execution_id is valid

### Debug Mode

Enable detailed logging:

```java
// Add to logError() method temporarily
private void logError(String message, Exception e) {
    System.err.println("[TransactionLogger ERROR] " + message);
    if (e != null) {
        e.printStackTrace();  // Full stack trace
    }
}
```

## Next Steps

After implementing TransactionLogger:

1. **Subtask 3.2**: Create MetricsAggregator service for hourly/daily rollups
2. **Subtask 3.3**: Integrate logging into TransactionThread
3. **Phase 4**: Implement alerting service to notify on failures

## Related Documentation

- Database Schema: `database/monitoring_schema.sql`
- Migration Script: `_internal/sql/005_monitoring_schema.sql`
- API Servlets: `com.interweave.monitoring.api.*`
- Implementation Plan: `.auto-claude/specs/005-real-time-integration-monitoring-dashboard/implementation_plan.json`
