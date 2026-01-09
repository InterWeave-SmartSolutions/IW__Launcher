# TransactionThread Logging Integration Guide

## Status: MANUAL INTEGRATION REQUIRED

**Issue**: The `TransactionThread.java` source file does not exist in the codebase. Only the compiled `.class` file is present at:
```
web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/businessDaemon/TransactionThread.class
```

This is a common situation in legacy systems where source code may have been lost or is proprietary.

## Solution Options

### Option 1: Decompile and Modify (Recommended)

1. **Decompile the TransactionThread class**:
   ```bash
   # Using JD-GUI, JAD, or CFR decompiler
   java -jar cfr.jar TransactionThread.class > TransactionThread.java
   ```

2. **Add TransactionLogger integration** at these key points:
   - **Start of `run()` method** - Call `TransactionLogger.logStart()`
   - **During record processing loop** - Call `TransactionLogger.logProgress()` every N records
   - **On successful completion** - Call `TransactionLogger.logComplete()`
   - **In catch block** - Call `TransactionLogger.logFailure()`
   - **On timeout** - Call `TransactionLogger.logTimeout()`

3. **Recompile and deploy**:
   ```bash
   javac -cp "path/to/dependencies/*" TransactionThread.java
   cp TransactionThread.class web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/businessDaemon/
   ```

### Option 2: Create a Wrapper Class (Lower Risk)

Use the provided `TransactionThreadWrapper.java` (see below) to wrap existing transaction execution with logging.

### Option 3: Aspect-Oriented Approach

Create a servlet filter or Java agent that intercepts transaction execution and adds logging.

## Integration Code Examples

### Example 1: Direct Integration in run() Method

```java
package com.interweave.businessDaemon;

import com.interweave.monitoring.service.TransactionLogger;

public class TransactionThread implements Runnable {

    private String executionId;
    private TransactionLogger logger;

    public void run() {
        logger = TransactionLogger.getInstance();

        try {
            // Log transaction start
            executionId = logger.logStart(
                companyId,
                projectId,
                transformationId,
                flowName,
                "transaction",  // flowType
                "scheduler",    // triggeredBy
                null            // triggeredByUserId
            );

            if (executionId == null) {
                System.err.println("Failed to log transaction start - continuing anyway");
            }

            // ... existing transaction setup code ...

            int recordsProcessed = 0;
            int recordsFailed = 0;

            // Process records
            while (hasMoreRecords()) {
                try {
                    processRecord();
                    recordsProcessed++;

                    // Log progress every 100 records
                    if (recordsProcessed % 100 == 0 && executionId != null) {
                        logger.logProgress(executionId, recordsProcessed, recordsFailed);
                    }

                } catch (Exception e) {
                    recordsFailed++;
                    // Handle error...
                }
            }

            // Log successful completion
            if (executionId != null) {
                logger.logComplete(executionId, recordsProcessed, recordsFailed, 0);
            }

        } catch (Exception e) {
            // Log failure
            if (executionId != null) {
                String stackTrace = getStackTraceAsString(e);
                logger.logFailure(executionId, e.getMessage(),
                    e.getClass().getSimpleName(), stackTrace);
            }

            // ... existing error handling ...

        } finally {
            // ... existing cleanup code ...
        }
    }

    private String getStackTraceAsString(Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
```

### Example 2: Logging Request/Response Payloads

```java
// After receiving request
if (executionId != null && requestXml != null) {
    logger.logPayload(
        executionId,
        "request",
        "xml",
        requestXml,
        sourceSystem,
        destinationSystem
    );
}

// After receiving response
if (executionId != null && responseXml != null) {
    logger.logPayload(
        executionId,
        "response",
        "xml",
        responseXml,
        destinationSystem,
        sourceSystem
    );
}
```

## Key Integration Points

Based on typical transaction execution flow, logging should be added at:

1. **Transaction Initialization**
   - Location: Beginning of `run()` or `execute()` method
   - Action: `logStart(companyId, projectId, transformationId, flowName, ...)`
   - Capture: execution_id for subsequent logging calls

2. **Record Processing Loop**
   - Location: Inside record iteration loop
   - Action: `logProgress(executionId, recordsProcessed, recordsFailed)`
   - Frequency: Every 50-100 records (configurable)

3. **Successful Completion**
   - Location: After all records processed successfully
   - Action: `logComplete(executionId, recordsProcessed, recordsFailed, recordsSkipped)`
   - Include: Final counts of all metrics

4. **Error Handling**
   - Location: In catch blocks
   - Action: `logFailure(executionId, errorMessage, errorCode, stackTrace)`
   - Capture: Full error details including stack trace

5. **Timeout Handling**
   - Location: In timeout detection code
   - Action: `logTimeout(executionId, timeoutMessage)`
   - Include: Duration and timeout threshold

## Transaction Context Information

Extract these values from TransactionThread context:

```java
// Required parameters for logStart()
Integer companyId = getCompanyId();           // From session or config
Integer projectId = getProjectId();           // From transformation config
Integer transformationId = getTransformationId();  // From flow definition
String flowName = getFlowName();              // Human-readable flow name
String flowType = "transaction";              // or "utility", "query"
String triggeredBy = "scheduler";             // or "manual", "webhook", "api"
Integer triggeredByUserId = getUserId();      // If manual trigger
```

## Error Handling Best Practices

**CRITICAL**: Logging must NEVER cause transaction failure

```java
try {
    executionId = logger.logStart(...);
} catch (Exception e) {
    // Log error but continue transaction
    System.err.println("Logging failed: " + e.getMessage());
    executionId = null;  // Disable subsequent logging
}

// Always check executionId before logging
if (executionId != null) {
    try {
        logger.logProgress(...);
    } catch (Exception e) {
        // Suppress logging errors
    }
}
```

## Performance Considerations

1. **Minimize Logging Frequency**
   - Log progress every 50-100 records, not every record
   - Adjust based on typical transaction size

2. **Async Considerations**
   - TransactionLogger uses connection pooling
   - Each call is independent (fire-and-forget)
   - No blocking on database writes

3. **Resource Management**
   - TransactionLogger handles all connection cleanup
   - No resource leaks even if logging fails

## Testing Integration

After integrating TransactionLogger:

1. **Start a test transaction** and verify execution record created:
   ```sql
   SELECT * FROM transaction_executions
   WHERE flow_name = 'YourTestFlow'
   ORDER BY started_at DESC LIMIT 1;
   ```

2. **Check progress updates** during execution:
   ```sql
   SELECT execution_id, records_processed, records_failed, updated_at
   FROM transaction_executions
   WHERE status = 'running';
   ```

3. **Verify completion** after transaction finishes:
   ```sql
   SELECT execution_id, status, duration_ms, records_processed, records_failed
   FROM transaction_executions
   WHERE execution_id = 'your-execution-id';
   ```

4. **Test failure scenarios** and check error capture:
   ```sql
   SELECT execution_id, error_message, error_code, stack_trace
   FROM transaction_executions
   WHERE status = 'failed'
   ORDER BY started_at DESC LIMIT 1;
   ```

## Rollback Plan

If logging causes issues:

1. **Revert TransactionThread class**:
   ```bash
   # Restore from backup
   cp TransactionThread.class.backup TransactionThread.class
   ```

2. **Restart Tomcat**:
   ```bash
   ./web_portal/tomcat/bin/shutdown.sh
   ./web_portal/tomcat/bin/startup.sh
   ```

3. **Monitor logs**:
   ```bash
   tail -f ./web_portal/tomcat/logs/catalina.out
   ```

## Next Steps

1. Decompile `TransactionThread.class`
2. Review decompiled code to identify exact integration points
3. Add TransactionLogger calls following examples above
4. Test with a non-critical transaction first
5. Monitor performance impact
6. Roll out to all transactions

## Questions?

Contact the monitoring dashboard development team for assistance with integration.
