package com.interweave.error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * IWError - Structured error message model for the InterWeave IDE platform.
 *
 * This class provides a comprehensive error representation including:
 * - Error code and severity
 * - Detailed error message
 * - Root cause information
 * - Affected component identification
 * - Suggested resolution steps
 * - Documentation links
 * - Timestamp and transaction tracking
 *
 * Use the IWErrorBuilder to construct instances of this class.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class IWError {

    private final ErrorCode errorCode;
    private final String message;
    private final String cause;
    private final String affectedComponent;
    private final String suggestedResolution;
    private final String documentationLink;
    private final long timestamp;
    private final String transactionId;
    private final Throwable throwable;

    /**
     * Package-private constructor - use IWErrorBuilder to create instances
     *
     * @param builder The builder instance containing error details
     */
    IWError(IWErrorBuilder builder) {
        this.errorCode = builder.errorCode;
        this.message = builder.message != null ? builder.message :
                      (builder.errorCode != null ? builder.errorCode.getDefaultMessage() : "Unknown error");
        this.cause = builder.cause;
        this.affectedComponent = builder.affectedComponent;
        this.suggestedResolution = builder.suggestedResolution;
        this.documentationLink = builder.documentationLink;
        this.timestamp = builder.timestamp;
        this.transactionId = builder.transactionId;
        this.throwable = builder.throwable;
    }

    /**
     * Gets the error code
     *
     * @return The error code, or null if not set
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the error message
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the cause description
     *
     * @return The cause description, or null if not set
     */
    public String getCause() {
        return cause;
    }

    /**
     * Gets the affected component
     *
     * @return The affected component name, or null if not set
     */
    public String getAffectedComponent() {
        return affectedComponent;
    }

    /**
     * Gets the suggested resolution
     *
     * @return The suggested resolution steps, or null if not set
     */
    public String getSuggestedResolution() {
        return suggestedResolution;
    }

    /**
     * Gets the documentation link
     *
     * @return The documentation URL, or null if not set
     */
    public String getDocumentationLink() {
        return documentationLink;
    }

    /**
     * Gets the timestamp when the error occurred
     *
     * @return The timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the transaction ID
     *
     * @return The transaction ID for tracking
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the underlying throwable/exception
     *
     * @return The throwable, or null if not set
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Gets the error category
     *
     * @return The error category, or null if error code is not set
     */
    public ErrorCategory getCategory() {
        return errorCode != null ? errorCode.getCategory() : null;
    }

    /**
     * Gets the error severity
     *
     * @return The error severity, or null if error code is not set
     */
    public ErrorSeverity getSeverity() {
        return errorCode != null ? errorCode.getSeverity() : null;
    }

    /**
     * Gets the error code string
     *
     * @return The error code string (e.g., "AUTH001"), or "UNKNOWN" if not set
     */
    public String getCode() {
        return errorCode != null ? errorCode.getCode() : "UNKNOWN";
    }

    /**
     * Converts the error to a JSON-formatted string for API responses
     *
     * @return JSON representation of the error
     */
    public String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        json.append("\"errorCode\":\"").append(escapeJson(getCode())).append("\",");

        if (errorCode != null) {
            json.append("\"category\":\"").append(escapeJson(errorCode.getCategory().getCode())).append("\",");
            json.append("\"severity\":\"").append(escapeJson(errorCode.getSeverity().name())).append("\",");
        }

        json.append("\"message\":\"").append(escapeJson(message)).append("\",");

        if (cause != null) {
            json.append("\"cause\":\"").append(escapeJson(cause)).append("\",");
        }

        if (affectedComponent != null) {
            json.append("\"affectedComponent\":\"").append(escapeJson(affectedComponent)).append("\",");
        }

        if (suggestedResolution != null) {
            json.append("\"suggestedResolution\":\"").append(escapeJson(suggestedResolution)).append("\",");
        }

        if (documentationLink != null) {
            json.append("\"documentationLink\":\"").append(escapeJson(documentationLink)).append("\",");
        }

        json.append("\"timestamp\":\"").append(formatTimestamp()).append("\",");
        json.append("\"transactionId\":\"").append(escapeJson(transactionId)).append("\"");

        json.append("}");
        return json.toString();
    }

    /**
     * Converts the error to a detailed log string for logging purposes
     *
     * @return Formatted log string with all error details
     */
    public String toLogString() {
        StringBuilder log = new StringBuilder();

        log.append("[").append(getCode()).append("]");
        if (errorCode != null) {
            log.append(" [").append(errorCode.getSeverity()).append("]");
        }
        log.append(" ").append(message);
        log.append("\n");

        log.append("  Transaction ID: ").append(transactionId).append("\n");
        log.append("  Timestamp: ").append(formatTimestamp()).append("\n");

        if (affectedComponent != null) {
            log.append("  Affected Component: ").append(affectedComponent).append("\n");
        }

        if (cause != null) {
            log.append("  Cause: ").append(cause).append("\n");
        }

        if (suggestedResolution != null) {
            log.append("  Suggested Resolution: ").append(suggestedResolution).append("\n");
        }

        if (documentationLink != null) {
            log.append("  Documentation: ").append(documentationLink).append("\n");
        }

        if (throwable != null) {
            log.append("  Exception: ").append(throwable.getClass().getName());
            log.append(": ").append(throwable.getMessage()).append("\n");
            log.append("  Stack Trace:\n");
            for (StackTraceElement element : throwable.getStackTrace()) {
                log.append("    ").append(element.toString()).append("\n");
            }
        }

        return log.toString();
    }

    /**
     * Formats the timestamp as an ISO-8601 date-time string
     *
     * @return ISO-8601 formatted timestamp
     */
    private String formatTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return sdf.format(new Date(timestamp));
    }

    /**
     * Escapes special characters for JSON strings
     *
     * @param str The string to escape
     * @return Escaped string safe for JSON
     */
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /**
     * Returns a string representation of this error
     *
     * @return String in format "[CODE] Message"
     */
    @Override
    public String toString() {
        return "[" + getCode() + "] " + message;
    }

    /**
     * Creates a new IWErrorBuilder instance
     *
     * @param errorCode The error code for this error
     * @return A new builder instance
     */
    public static IWErrorBuilder builder(ErrorCode errorCode) {
        return new IWErrorBuilder(errorCode);
    }

    /**
     * Creates a new IWErrorBuilder instance with a custom message
     *
     * @param errorCode The error code for this error
     * @param message Custom error message
     * @return A new builder instance
     */
    public static IWErrorBuilder builder(ErrorCode errorCode, String message) {
        return new IWErrorBuilder(errorCode).message(message);
    }
}
