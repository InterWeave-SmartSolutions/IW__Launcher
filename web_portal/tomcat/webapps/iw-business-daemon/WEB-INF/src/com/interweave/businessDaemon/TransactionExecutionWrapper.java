package com.interweave.businessDaemon;

import com.interweave.monitoring.service.TransactionLogger;

/**
 * TransactionExecutionWrapper - Wrapper class for adding monitoring to transaction execution.
 *
 * This class wraps the execution of transactions (whether via TransactionThread or other
 * execution mechanisms) and automatically logs execution details to the monitoring system.
 *
 * Usage Example:
 * <pre>
 * TransactionExecutionWrapper wrapper = new TransactionExecutionWrapper(
 *     companyId, projectId, transformationId, "Customer Order Sync"
 * );
 *
 * wrapper.execute(new TransactionExecutable() {
 *     public TransactionResult run(TransactionContext context) {
 *         // Your existing transaction logic here
 *         int processed = 0, failed = 0;
 *
 *         for (Record record : records) {
 *             try {
 *                 processRecord(record);
 *                 processed++;
 *
 *                 // Report progress periodically
 *                 if (processed % 100 == 0) {
 *                     context.reportProgress(processed, failed);
 *                 }
 *             } catch (Exception e) {
 *                 failed++;
 *             }
 *         }
 *
 *         return new TransactionResult(processed, failed, 0);
 *     }
 * });
 * </pre>
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class TransactionExecutionWrapper {

    private final Integer companyId;
    private final Integer projectId;
    private final Integer transformationId;
    private final String flowName;
    private final String flowType;
    private final String triggeredBy;
    private final Integer triggeredByUserId;

    private final TransactionLogger logger;

    /**
     * Creates a new transaction execution wrapper with full context.
     *
     * @param companyId The company that owns this transaction
     * @param projectId The project containing the transaction flow
     * @param transformationId The transformation/flow being executed
     * @param flowName Name of the transaction flow (required)
     * @param flowType Type of flow: 'transaction', 'utility', or 'query'
     * @param triggeredBy How execution was triggered: 'scheduler', 'manual', 'webhook', or 'api'
     * @param triggeredByUserId User who triggered manual execution (null for automated)
     */
    public TransactionExecutionWrapper(Integer companyId, Integer projectId,
                                       Integer transformationId, String flowName,
                                       String flowType, String triggeredBy,
                                       Integer triggeredByUserId) {
        this.companyId = companyId;
        this.projectId = projectId;
        this.transformationId = transformationId;
        this.flowName = flowName;
        this.flowType = flowType != null ? flowType : "transaction";
        this.triggeredBy = triggeredBy != null ? triggeredBy : "scheduler";
        this.triggeredByUserId = triggeredByUserId;
        this.logger = TransactionLogger.getInstance();
    }

    /**
     * Simplified constructor for scheduled transactions.
     *
     * @param companyId The company ID
     * @param projectId The project ID
     * @param transformationId The transformation ID
     * @param flowName Name of the transaction flow
     */
    public TransactionExecutionWrapper(Integer companyId, Integer projectId,
                                       Integer transformationId, String flowName) {
        this(companyId, projectId, transformationId, flowName, "transaction", "scheduler", null);
    }

    /**
     * Executes a transaction with automatic monitoring and error handling.
     *
     * @param executable The transaction logic to execute
     * @return The transaction result
     * @throws TransactionException If transaction execution fails critically
     */
    public TransactionResult execute(TransactionExecutable executable) throws TransactionException {
        String executionId = null;

        try {
            // Log transaction start
            executionId = logger.logStart(
                companyId,
                projectId,
                transformationId,
                flowName,
                flowType,
                triggeredBy,
                triggeredByUserId
            );

            if (executionId == null) {
                System.err.println("[TransactionWrapper] Failed to log transaction start - continuing anyway");
            }

            // Create context for transaction
            TransactionContext context = new TransactionContext(executionId, logger);

            // Execute the transaction
            TransactionResult result = executable.run(context);

            // Log successful completion
            if (executionId != null) {
                try {
                    logger.logComplete(
                        executionId,
                        result.getRecordsProcessed(),
                        result.getRecordsFailed(),
                        result.getRecordsSkipped()
                    );
                } catch (Exception e) {
                    System.err.println("[TransactionWrapper] Failed to log completion: " + e.getMessage());
                }
            }

            return result;

        } catch (Exception e) {
            // Log failure
            if (executionId != null) {
                try {
                    String stackTrace = getStackTraceAsString(e);
                    logger.logFailure(
                        executionId,
                        e.getMessage(),
                        e.getClass().getSimpleName(),
                        stackTrace
                    );
                } catch (Exception logError) {
                    System.err.println("[TransactionWrapper] Failed to log error: " + logError.getMessage());
                }
            }

            // Re-throw as TransactionException
            throw new TransactionException("Transaction execution failed: " + e.getMessage(), e);
        }
    }

    /**
     * Logs a payload associated with this transaction execution.
     * Can be called before execute() for request payloads or after for response payloads.
     *
     * @param executionId The execution ID (obtained from execute() method)
     * @param payloadType Type of payload: 'request', 'response', 'error_context', or 'debug'
     * @param payloadFormat Format of payload: 'xml', 'json', 'text', or 'binary'
     * @param payloadData The actual payload content
     * @param sourceSystem System that generated this payload (can be null)
     * @param destinationSystem Target system for this payload (can be null)
     */
    public void logPayload(String executionId, String payloadType, String payloadFormat,
                          String payloadData, String sourceSystem, String destinationSystem) {
        if (executionId != null) {
            try {
                logger.logPayload(executionId, payloadType, payloadFormat,
                    payloadData, sourceSystem, destinationSystem);
            } catch (Exception e) {
                System.err.println("[TransactionWrapper] Failed to log payload: " + e.getMessage());
            }
        }
    }

    /**
     * Converts exception stack trace to string.
     *
     * @param e The exception
     * @return Stack trace as string
     */
    private String getStackTraceAsString(Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Interface for transaction execution logic.
     */
    public interface TransactionExecutable {
        /**
         * Executes the transaction logic.
         *
         * @param context Transaction context for progress reporting
         * @return Transaction result with processing metrics
         * @throws Exception If execution fails
         */
        TransactionResult run(TransactionContext context) throws Exception;
    }

    /**
     * Context provided to transaction during execution for progress reporting.
     */
    public static class TransactionContext {
        private final String executionId;
        private final TransactionLogger logger;

        public TransactionContext(String executionId, TransactionLogger logger) {
            this.executionId = executionId;
            this.logger = logger;
        }

        /**
         * Gets the execution ID for this transaction.
         *
         * @return The execution ID, or null if logging failed
         */
        public String getExecutionId() {
            return executionId;
        }

        /**
         * Reports progress during transaction execution.
         * Safe to call frequently - logging failures are suppressed.
         *
         * @param recordsProcessed Number of records processed so far
         * @param recordsFailed Number of records failed so far
         */
        public void reportProgress(int recordsProcessed, int recordsFailed) {
            if (executionId != null) {
                try {
                    logger.logProgress(executionId, recordsProcessed, recordsFailed);
                } catch (Exception e) {
                    // Suppress logging errors during progress reporting
                }
            }
        }

        /**
         * Logs a payload during transaction execution.
         *
         * @param payloadType Type of payload
         * @param payloadFormat Format of payload
         * @param payloadData The payload content
         * @param sourceSystem Source system (optional)
         * @param destinationSystem Destination system (optional)
         */
        public void logPayload(String payloadType, String payloadFormat, String payloadData,
                              String sourceSystem, String destinationSystem) {
            if (executionId != null) {
                try {
                    logger.logPayload(executionId, payloadType, payloadFormat,
                        payloadData, sourceSystem, destinationSystem);
                } catch (Exception e) {
                    // Suppress logging errors
                }
            }
        }
    }

    /**
     * Result of transaction execution.
     */
    public static class TransactionResult {
        private final int recordsProcessed;
        private final int recordsFailed;
        private final int recordsSkipped;

        public TransactionResult(int recordsProcessed, int recordsFailed, int recordsSkipped) {
            this.recordsProcessed = recordsProcessed;
            this.recordsFailed = recordsFailed;
            this.recordsSkipped = recordsSkipped;
        }

        public int getRecordsProcessed() {
            return recordsProcessed;
        }

        public int getRecordsFailed() {
            return recordsFailed;
        }

        public int getRecordsSkipped() {
            return recordsSkipped;
        }

        @Override
        public String toString() {
            return String.format("TransactionResult{processed=%d, failed=%d, skipped=%d}",
                recordsProcessed, recordsFailed, recordsSkipped);
        }
    }

    /**
     * Exception thrown when transaction execution fails.
     */
    public static class TransactionException extends Exception {
        private static final long serialVersionUID = 1L;

        public TransactionException(String message) {
            super(message);
        }

        public TransactionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
