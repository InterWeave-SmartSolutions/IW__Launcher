package com.interweave.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ErrorLogger - Enhanced error logging utility for the InterWeave IDE platform.
 *
 * This class provides centralized error logging with structured error information including:
 * - ISO-8601 timestamps
 * - Transaction IDs for tracking
 * - Full stack traces
 * - Error codes and categories
 * - Affected components
 * - Suggested resolutions
 * - Documentation links
 *
 * Integrates with Tomcat's java.util.logging framework for consistent log management.
 *
 * Usage Examples:
 * <pre>
 *   // Log an IWError object
 *   IWError error = IWError.builder(ErrorCode.DB001)
 *       .cause("Connection refused")
 *       .affectedComponent("DatabaseManager")
 *       .build();
 *   ErrorLogger.logError(error);
 *
 *   // Log an error with exception
 *   ErrorLogger.logError(ErrorCode.CONFIG001, "Invalid XML syntax", exception);
 *
 *   // Log error with custom details
 *   ErrorLogger.logError(ErrorCode.FLOW002, "Flow execution failed",
 *       "FlowExecutor", "Invalid XPath in transformation", exception);
 * </pre>
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class ErrorLogger {

    private static final Logger logger = Logger.getLogger("com.interweave.error");
    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * Private constructor to prevent instantiation
     */
    private ErrorLogger() {
        // Utility class - no instances allowed
    }

    /**
     * Logs an IWError object with full details
     *
     * @param error The IWError to log
     */
    public static void logError(IWError error) {
        if (error == null) {
            logger.warning("Attempted to log null error");
            return;
        }

        Level logLevel = getLogLevel(error.getSeverity());
        String logMessage = formatErrorLogMessage(error);

        if (error.getThrowable() != null) {
            logger.log(logLevel, logMessage, error.getThrowable());
        } else {
            logger.log(logLevel, logMessage);
        }
    }

    /**
     * Logs an error with error code and message
     *
     * @param errorCode The error code
     * @param message Custom error message
     */
    public static void logError(ErrorCode errorCode, String message) {
        IWError error = IWError.builder(errorCode, message).build();
        logError(error);
    }

    /**
     * Logs an error with error code, message, and exception
     *
     * @param errorCode The error code
     * @param message Custom error message
     * @param throwable The exception that caused the error
     */
    public static void logError(ErrorCode errorCode, String message, Throwable throwable) {
        IWError error = IWError.builder(errorCode, message)
                .throwable(throwable)
                .cause(throwable.getMessage())
                .build();
        logError(error);
    }

    /**
     * Logs an error with full details
     *
     * @param errorCode The error code
     * @param message Custom error message
     * @param affectedComponent The component where the error occurred
     * @param cause Description of what caused the error
     * @param throwable The exception that caused the error (can be null)
     */
    public static void logError(ErrorCode errorCode, String message, String affectedComponent,
                                 String cause, Throwable throwable) {
        IWError error = IWError.builder(errorCode, message)
                .affectedComponent(affectedComponent)
                .cause(cause)
                .throwable(throwable)
                .build();
        logError(error);
    }

    /**
     * Logs a warning message
     *
     * @param errorCode The error code
     * @param message Warning message
     */
    public static void logWarning(ErrorCode errorCode, String message) {
        IWError error = IWError.builder(errorCode, message).build();
        logError(error);
    }

    /**
     * Logs an informational message
     *
     * @param errorCode The error code
     * @param message Informational message
     */
    public static void logInfo(ErrorCode errorCode, String message) {
        IWError error = IWError.builder(errorCode, message).build();
        logError(error);
    }

    /**
     * Logs a generic exception with automatic error code selection
     *
     * @param throwable The exception to log
     * @param affectedComponent The component where the exception occurred
     */
    public static void logException(Throwable throwable, String affectedComponent) {
        ErrorCode errorCode = selectErrorCodeForException(throwable);
        IWError error = IWError.builder(errorCode)
                .message(throwable.getMessage() != null ? throwable.getMessage() : throwable.getClass().getName())
                .affectedComponent(affectedComponent)
                .throwable(throwable)
                .build();
        logError(error);
    }

    /**
     * Formats an IWError object into a structured log message
     *
     * @param error The error to format
     * @return Formatted log message string
     */
    private static String formatErrorLogMessage(IWError error) {
        StringBuilder log = new StringBuilder();

        // Header line with error code, severity, and timestamp
        log.append("\n");
        log.append("================================================================================\n");
        log.append("ERROR: [").append(error.getCode()).append("]");

        if (error.getSeverity() != null) {
            log.append(" [").append(error.getSeverity()).append("]");
        }

        if (error.getCategory() != null) {
            log.append(" [").append(error.getCategory().getDisplayName()).append("]");
        }

        log.append("\n");
        log.append("================================================================================\n");

        // Message
        log.append("Message: ").append(error.getMessage()).append("\n");

        // Transaction ID
        log.append("Transaction ID: ").append(error.getTransactionId()).append("\n");

        // Timestamp (ISO-8601 format)
        synchronized (ISO_DATE_FORMAT) {
            log.append("Timestamp: ").append(ISO_DATE_FORMAT.format(new Date(error.getTimestamp()))).append("\n");
        }

        // Affected Component
        if (error.getAffectedComponent() != null && !error.getAffectedComponent().isEmpty()) {
            log.append("Affected Component: ").append(error.getAffectedComponent()).append("\n");
        }

        // Cause
        if (error.getCause() != null && !error.getCause().isEmpty()) {
            log.append("Cause: ").append(error.getCause()).append("\n");
        }

        // Suggested Resolution
        if (error.getSuggestedResolution() != null && !error.getSuggestedResolution().isEmpty()) {
            log.append("\nSuggested Resolution:\n");
            log.append(formatResolutionSteps(error.getSuggestedResolution()));
        }

        // Documentation Link
        if (error.getDocumentationLink() != null && !error.getDocumentationLink().isEmpty()) {
            log.append("\nDocumentation: ").append(error.getDocumentationLink()).append("\n");
        }

        // Stack Trace (if available)
        if (error.getThrowable() != null) {
            log.append("\nException Details:\n");
            log.append("  Type: ").append(error.getThrowable().getClass().getName()).append("\n");
            log.append("  Message: ").append(error.getThrowable().getMessage()).append("\n");
            log.append("\nStack Trace:\n");
            log.append(getStackTraceAsString(error.getThrowable()));
        }

        log.append("================================================================================\n");

        return log.toString();
    }

    /**
     * Formats resolution steps with proper indentation
     *
     * @param resolutionSteps The resolution steps to format
     * @return Formatted resolution steps
     */
    private static String formatResolutionSteps(String resolutionSteps) {
        if (resolutionSteps == null || resolutionSteps.isEmpty()) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();
        String[] lines = resolutionSteps.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                formatted.append("  ").append(line.trim()).append("\n");
            }
        }

        return formatted.toString();
    }

    /**
     * Converts a throwable's stack trace to a string
     *
     * @param throwable The throwable to convert
     * @return String representation of the stack trace
     */
    private static String getStackTraceAsString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String stackTrace = sw.toString();

        // Indent stack trace lines
        StringBuilder indented = new StringBuilder();
        String[] lines = stackTrace.split("\n");
        for (String line : lines) {
            indented.append("  ").append(line).append("\n");
        }

        return indented.toString();
    }

    /**
     * Determines the appropriate log level based on error severity
     *
     * @param severity The error severity
     * @return The corresponding java.util.logging.Level
     */
    private static Level getLogLevel(ErrorSeverity severity) {
        if (severity == null) {
            return Level.SEVERE;
        }

        switch (severity) {
            case ERROR:
                return Level.SEVERE;
            case WARNING:
                return Level.WARNING;
            case INFO:
                return Level.INFO;
            default:
                return Level.SEVERE;
        }
    }

    /**
     * Selects an appropriate error code based on exception type
     *
     * @param throwable The exception to analyze
     * @return An appropriate ErrorCode
     */
    private static ErrorCode selectErrorCodeForException(Throwable throwable) {
        if (throwable == null) {
            return ErrorCode.CONFIG001; // Generic config error
        }

        String exceptionName = throwable.getClass().getName().toLowerCase();

        // Database exceptions
        if (exceptionName.contains("sqlexception") || exceptionName.contains("sql")) {
            return ErrorCode.DB002;
        }

        // Connection exceptions
        if (exceptionName.contains("connect") || exceptionName.contains("timeout") ||
            exceptionName.contains("socket") || exceptionName.contains("network")) {
            return ErrorCode.CONNECTION001;
        }

        // XML/XPath exceptions
        if (exceptionName.contains("xpath") || exceptionName.contains("xslt") ||
            exceptionName.contains("transform")) {
            return ErrorCode.XPATH001;
        }

        // Configuration exceptions
        if (exceptionName.contains("xml") || exceptionName.contains("parse") ||
            exceptionName.contains("config")) {
            return ErrorCode.CONFIG001;
        }

        // Validation exceptions
        if (exceptionName.contains("validation") || exceptionName.contains("illegal")) {
            return ErrorCode.VALIDATION001;
        }

        // Default to generic config error
        return ErrorCode.CONFIG001;
    }

    /**
     * Gets the logger instance used by this class
     * For testing and advanced logging configuration
     *
     * @return The logger instance
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Sets the logging level for the error logger
     *
     * @param level The logging level to set
     */
    public static void setLogLevel(Level level) {
        logger.setLevel(level);
    }

    /**
     * Logs a debug message (FINE level) for development troubleshooting
     *
     * @param message The debug message
     */
    public static void logDebug(String message) {
        logger.fine(message);
    }

    /**
     * Logs a debug message with details
     *
     * @param message The debug message
     * @param component The component generating the debug message
     */
    public static void logDebug(String message, String component) {
        logger.fine("[" + component + "] " + message);
    }
}
