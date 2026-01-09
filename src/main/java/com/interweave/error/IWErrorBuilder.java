package com.interweave.error;

import java.util.UUID;

/**
 * IWErrorBuilder - Builder pattern for constructing IWError instances.
 *
 * Provides a fluent API for creating structured error objects with all
 * necessary context information. This builder ensures consistent error
 * construction and automatically generates timestamps and transaction IDs.
 *
 * Example usage:
 * <pre>
 * IWError error = IWError.builder(ErrorCode.DB001)
 *     .message("Failed to connect to database")
 *     .cause("Connection timeout after 30 seconds")
 *     .affectedComponent("DatabaseConnectionPool")
 *     .suggestedResolution("Check database server status and network connectivity")
 *     .documentationLink("https://docs.interweave.com/errors/DB001")
 *     .build();
 * </pre>
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class IWErrorBuilder {

    ErrorCode errorCode;
    String message;
    String cause;
    String affectedComponent;
    String suggestedResolution;
    String documentationLink;
    long timestamp;
    String transactionId;
    Throwable throwable;

    /**
     * Creates a new builder with the specified error code
     *
     * @param errorCode The error code for this error
     */
    IWErrorBuilder(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
        this.transactionId = generateTransactionId();
    }

    /**
     * Sets a custom error message (overrides the default message from error code)
     *
     * @param message The error message
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the cause description (what went wrong)
     *
     * @param cause Description of the root cause
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder cause(String cause) {
        this.cause = cause;
        return this;
    }

    /**
     * Sets the cause from an exception
     *
     * @param throwable The exception that caused the error
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder cause(Throwable throwable) {
        this.throwable = throwable;
        if (throwable != null) {
            this.cause = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
        }
        return this;
    }

    /**
     * Sets both cause description and throwable
     *
     * @param cause Description of the root cause
     * @param throwable The exception that caused the error
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder cause(String cause, Throwable throwable) {
        this.cause = cause;
        this.throwable = throwable;
        return this;
    }

    /**
     * Sets the affected component (which part of the system failed)
     *
     * @param affectedComponent Name of the component or module that experienced the error
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder affectedComponent(String affectedComponent) {
        this.affectedComponent = affectedComponent;
        return this;
    }

    /**
     * Sets the suggested resolution (how to fix the problem)
     *
     * @param suggestedResolution Step-by-step resolution instructions
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder suggestedResolution(String suggestedResolution) {
        this.suggestedResolution = suggestedResolution;
        return this;
    }

    /**
     * Sets the documentation link (URL to detailed documentation)
     *
     * @param documentationLink URL to error documentation
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder documentationLink(String documentationLink) {
        this.documentationLink = documentationLink;
        return this;
    }

    /**
     * Sets a custom timestamp (default is current time)
     *
     * @param timestamp Timestamp in milliseconds since epoch
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Sets a custom transaction ID (default is auto-generated UUID)
     *
     * @param transactionId Custom transaction ID for tracking
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    /**
     * Sets the underlying throwable/exception
     *
     * @param throwable The exception that caused the error
     * @return This builder instance for method chaining
     */
    public IWErrorBuilder throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    /**
     * Builds and returns the IWError instance
     *
     * @return The constructed IWError object
     */
    public IWError build() {
        return new IWError(this);
    }

    /**
     * Generates a unique transaction ID for error tracking
     *
     * @return A unique transaction ID string
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
