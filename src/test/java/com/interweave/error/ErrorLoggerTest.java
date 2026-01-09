package com.interweave.error;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for ErrorLogger class.
 *
 * Tests comprehensive error logging functionality including:
 * - Logging IWError objects
 * - Logging with error codes and messages
 * - Logging with exceptions
 * - Log level mapping based on severity
 * - Log message formatting
 * - Exception type detection
 *
 * @author InterWeave Error Framework Tests
 * @version 1.0
 */
public class ErrorLoggerTest {

    private TestLogHandler testHandler;
    private Logger logger;
    private Level originalLogLevel;

    /**
     * Custom log handler that captures log records for testing
     */
    private static class TestLogHandler extends Handler {
        private List<LogRecord> logRecords = new ArrayList<LogRecord>();

        @Override
        public void publish(LogRecord record) {
            logRecords.add(record);
        }

        @Override
        public void flush() {
            // No-op for testing
        }

        @Override
        public void close() throws SecurityException {
            logRecords.clear();
        }

        public List<LogRecord> getLogRecords() {
            return logRecords;
        }

        public LogRecord getLastLogRecord() {
            if (logRecords.isEmpty()) {
                return null;
            }
            return logRecords.get(logRecords.size() - 1);
        }

        public void clear() {
            logRecords.clear();
        }

        public int getLogCount() {
            return logRecords.size();
        }
    }

    @Before
    public void setUp() {
        // Get the logger instance used by ErrorLogger
        logger = ErrorLogger.getLogger();

        // Store original log level
        originalLogLevel = logger.getLevel();

        // Set to ALL to capture all log levels
        logger.setLevel(Level.ALL);

        // Remove existing handlers to avoid interference
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }

        // Add test handler to capture log records
        testHandler = new TestLogHandler();
        testHandler.setLevel(Level.ALL);
        logger.addHandler(testHandler);
    }

    @After
    public void tearDown() {
        // Restore original log level
        if (originalLogLevel != null) {
            logger.setLevel(originalLogLevel);
        }

        // Remove test handler
        if (testHandler != null) {
            logger.removeHandler(testHandler);
            testHandler.close();
        }
    }

    // ========================================================================
    // Basic Logging Tests
    // ========================================================================

    @Test
    public void testLogErrorWithIWError() {
        IWError error = IWError.builder(ErrorCode.AUTH001)
                .message("Test authentication failure")
                .build();

        ErrorLogger.logError(error);

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertEquals("Log level should be SEVERE for ERROR", Level.SEVERE, record.getLevel());
        assertTrue("Log message should contain error code", record.getMessage().contains("AUTH001"));
        assertTrue("Log message should contain message", record.getMessage().contains("Test authentication failure"));
    }

    @Test
    public void testLogErrorWithNullError() {
        ErrorLogger.logError((IWError) null);

        assertEquals("Should log warning for null error", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertEquals("Should use WARNING level", Level.WARNING, record.getLevel());
        assertTrue("Should log warning message", record.getMessage().contains("null error"));
    }

    @Test
    public void testLogErrorWithErrorCodeAndMessage() {
        ErrorLogger.logError(ErrorCode.DB001, "Database connection failed");

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain error code", record.getMessage().contains("DB001"));
        assertTrue("Log message should contain custom message", record.getMessage().contains("Database connection failed"));
    }

    @Test
    public void testLogErrorWithException() {
        RuntimeException exception = new RuntimeException("Test exception");
        ErrorLogger.logError(ErrorCode.CONFIG001, "Configuration error", exception);

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertEquals("Log level should be SEVERE", Level.SEVERE, record.getLevel());
        assertTrue("Log message should contain error code", record.getMessage().contains("CONFIG001"));
        assertTrue("Log message should contain message", record.getMessage().contains("Configuration error"));
        assertNotNull("Log record should have throwable", record.getThrown());
        assertEquals("Throwable should match", exception, record.getThrown());
    }

    @Test
    public void testLogErrorWithFullDetails() {
        RuntimeException exception = new RuntimeException("Database timeout");
        ErrorLogger.logError(ErrorCode.DB001, "Connection failed",
                "DatabaseManager", "Connection pool exhausted", exception);

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain error code", record.getMessage().contains("DB001"));
        assertTrue("Log message should contain message", record.getMessage().contains("Connection failed"));
        assertTrue("Log message should contain component", record.getMessage().contains("DatabaseManager"));
        assertTrue("Log message should contain cause", record.getMessage().contains("Connection pool exhausted"));
        assertNotNull("Log record should have throwable", record.getThrown());
    }

    // ========================================================================
    // Severity Level Mapping Tests
    // ========================================================================

    @Test
    public void testLogLevelMappingForError() {
        IWError error = IWError.builder(ErrorCode.AUTH001).build();
        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        assertEquals("ERROR severity should map to SEVERE log level",
                Level.SEVERE, record.getLevel());
    }

    @Test
    public void testLogLevelMappingForWarning() {
        IWError warning = IWError.builder(ErrorCode.AUTH004).build(); // AUTH004 is WARNING
        ErrorLogger.logError(warning);

        LogRecord record = testHandler.getLastLogRecord();
        assertEquals("WARNING severity should map to WARNING log level",
                Level.WARNING, record.getLevel());
    }

    @Test
    public void testLogWarningMethod() {
        ErrorLogger.logWarning(ErrorCode.DB005, "Transaction rolled back");

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain message", record.getMessage().contains("Transaction rolled back"));
    }

    @Test
    public void testLogInfoMethod() {
        ErrorLogger.logInfo(ErrorCode.AUTH004, "Session refreshed");

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain message", record.getMessage().contains("Session refreshed"));
    }

    // ========================================================================
    // Exception Type Detection Tests
    // ========================================================================

    @Test
    public void testLogExceptionWithSQLException() {
        // Simulate SQL exception by name (since we may not have SQL libs in test classpath)
        Throwable sqlException = new RuntimeException("SQL error") {
            @Override
            public String toString() {
                return "java.sql.SQLException: Connection refused";
            }
        };

        ErrorLogger.logException(sqlException, "DatabaseComponent");

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain component", record.getMessage().contains("DatabaseComponent"));
    }

    @Test
    public void testLogExceptionWithConnectionException() {
        Throwable connectException = new RuntimeException("Connection timeout");

        ErrorLogger.logException(connectException, "NetworkModule");

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain component", record.getMessage().contains("NetworkModule"));
        assertTrue("Log message should contain exception message", record.getMessage().contains("Connection timeout"));
    }

    @Test
    public void testLogExceptionWithNullMessage() {
        Throwable exception = new RuntimeException((String) null);

        ErrorLogger.logException(exception, "TestComponent");

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain component", record.getMessage().contains("TestComponent"));
        assertTrue("Log message should contain exception class name", record.getMessage().contains("RuntimeException"));
    }

    // ========================================================================
    // Log Message Formatting Tests
    // ========================================================================

    @Test
    public void testLogMessageContainsHeader() {
        IWError error = IWError.builder(ErrorCode.FLOW001)
                .message("Flow execution failed")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain separator line", message.contains("===="));
        assertTrue("Log should contain ERROR label", message.contains("ERROR:"));
        assertTrue("Log should contain error code", message.contains("[FLOW001]"));
    }

    @Test
    public void testLogMessageContainsTransactionId() {
        IWError error = IWError.builder(ErrorCode.AUTH001)
                .transactionId("TXN-TEST-123")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Transaction ID label", message.contains("Transaction ID:"));
        assertTrue("Log should contain transaction ID value", message.contains("TXN-TEST-123"));
    }

    @Test
    public void testLogMessageContainsTimestamp() {
        IWError error = IWError.builder(ErrorCode.DB001).build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Timestamp label", message.contains("Timestamp:"));
        assertTrue("Log should contain ISO-8601 formatted timestamp", message.matches("(?s).*\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z.*"));
    }

    @Test
    public void testLogMessageContainsCategory() {
        IWError error = IWError.builder(ErrorCode.XPATH001).build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain category", message.contains("XPath/XSLT") || message.contains("XPATH"));
    }

    @Test
    public void testLogMessageContainsSeverity() {
        IWError error = IWError.builder(ErrorCode.AUTH001).build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain severity", message.contains("ERROR"));
    }

    @Test
    public void testLogMessageContainsAffectedComponent() {
        IWError error = IWError.builder(ErrorCode.FLOW001)
                .affectedComponent("FlowExecutor")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Affected Component label", message.contains("Affected Component:"));
        assertTrue("Log should contain component name", message.contains("FlowExecutor"));
    }

    @Test
    public void testLogMessageContainsCause() {
        IWError error = IWError.builder(ErrorCode.CONNECTION001)
                .cause("Network timeout after 30 seconds")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Cause label", message.contains("Cause:"));
        assertTrue("Log should contain cause text", message.contains("Network timeout"));
    }

    @Test
    public void testLogMessageContainsSuggestedResolution() {
        IWError error = IWError.builder(ErrorCode.DB001)
                .suggestedResolution("1. Check database connection\n2. Verify credentials\n3. Review firewall rules")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Suggested Resolution label", message.contains("Suggested Resolution:"));
        assertTrue("Log should contain first step", message.contains("Check database connection"));
        assertTrue("Log should contain second step", message.contains("Verify credentials"));
        assertTrue("Log should contain third step", message.contains("Review firewall rules"));
    }

    @Test
    public void testLogMessageContainsDocumentationLink() {
        IWError error = IWError.builder(ErrorCode.CONFIG001)
                .documentationLink("https://docs.interweave.com/errors/CONFIG001")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Documentation label", message.contains("Documentation:"));
        assertTrue("Log should contain documentation URL", message.contains("https://docs.interweave.com/errors/CONFIG001"));
    }

    @Test
    public void testLogMessageContainsExceptionDetails() {
        RuntimeException exception = new RuntimeException("Test exception message");
        IWError error = IWError.builder(ErrorCode.SYSTEM001)
                .throwable(exception)
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain Exception Details label", message.contains("Exception Details:"));
        assertTrue("Log should contain exception type", message.contains("RuntimeException"));
        assertTrue("Log should contain exception message", message.contains("Test exception message"));
        assertTrue("Log should contain Stack Trace label", message.contains("Stack Trace:"));
    }

    @Test
    public void testLogMessageOmitsEmptyFields() {
        IWError error = IWError.builder(ErrorCode.VALIDATION001)
                .message("Required field missing")
                .build();

        ErrorLogger.logError(error);

        LogRecord record = testHandler.getLastLogRecord();
        String message = record.getMessage();

        assertTrue("Log should contain message", message.contains("Required field missing"));
        // Should not contain labels for unset fields
        assertFalse("Log should not contain Affected Component label when not set",
                message.contains("Affected Component:"));
        assertFalse("Log should not contain Cause label when not set",
                message.contains("Cause:"));
        assertFalse("Log should not contain Suggested Resolution label when not set",
                message.contains("Suggested Resolution:"));
    }

    // ========================================================================
    // Multiple Error Logging Tests
    // ========================================================================

    @Test
    public void testLogMultipleErrors() {
        ErrorLogger.logError(ErrorCode.AUTH001, "First error");
        ErrorLogger.logError(ErrorCode.DB001, "Second error");
        ErrorLogger.logError(ErrorCode.FLOW001, "Third error");

        assertEquals("Should log three records", 3, testHandler.getLogCount());

        List<LogRecord> records = testHandler.getLogRecords();
        assertTrue("First log should contain first error", records.get(0).getMessage().contains("First error"));
        assertTrue("Second log should contain second error", records.get(1).getMessage().contains("Second error"));
        assertTrue("Third log should contain third error", records.get(2).getMessage().contains("Third error"));
    }

    @Test
    public void testLogErrorsWithDifferentSeverities() {
        ErrorLogger.logError(ErrorCode.AUTH001, "Error severity");      // ERROR
        ErrorLogger.logWarning(ErrorCode.AUTH004, "Warning severity");  // WARNING
        ErrorLogger.logInfo(ErrorCode.AUTH004, "Info severity");        // Could be INFO

        assertEquals("Should log three records", 3, testHandler.getLogCount());

        List<LogRecord> records = testHandler.getLogRecords();
        assertEquals("First should be SEVERE", Level.SEVERE, records.get(0).getLevel());
        // Note: logWarning and logInfo still create IWError, so level depends on ErrorCode's severity
    }

    // ========================================================================
    // Logger Configuration Tests
    // ========================================================================

    @Test
    public void testGetLogger() {
        Logger retrievedLogger = ErrorLogger.getLogger();

        assertNotNull("Logger should not be null", retrievedLogger);
        assertEquals("Logger name should be com.interweave.error",
                "com.interweave.error", retrievedLogger.getName());
    }

    @Test
    public void testSetLogLevel() {
        ErrorLogger.setLogLevel(Level.WARNING);

        assertEquals("Log level should be set to WARNING",
                Level.WARNING, logger.getLevel());

        // Reset for other tests
        ErrorLogger.setLogLevel(Level.ALL);
    }

    @Test
    public void testLogDebugMessages() {
        testHandler.clear();

        ErrorLogger.logDebug("Debug message");
        ErrorLogger.logDebug("Component message", "TestComponent");

        // Debug messages use FINE level, verify they're logged
        List<LogRecord> records = testHandler.getLogRecords();
        assertTrue("Should log debug messages", records.size() >= 1);

        boolean foundDebugMessage = false;
        for (LogRecord record : records) {
            if (record.getMessage().contains("Debug message") ||
                record.getMessage().contains("Component message")) {
                foundDebugMessage = true;
                assertEquals("Debug should use FINE level", Level.FINE, record.getLevel());
            }
        }
        assertTrue("Should find debug message", foundDebugMessage);
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testLogErrorWithVeryLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("This is a very long message. ");
        }

        ErrorLogger.logError(ErrorCode.SYSTEM001, longMessage.toString());

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log message should contain long text", record.getMessage().length() > 1000);
    }

    @Test
    public void testLogErrorWithSpecialCharacters() {
        String specialMessage = "Error with special chars: \n\t\r\\ \" ' < > & å ö ü";
        ErrorLogger.logError(ErrorCode.CONFIG001, specialMessage);

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertTrue("Log should contain special characters", record.getMessage().contains("special chars"));
    }

    @Test
    public void testLogErrorWithNestedExceptions() {
        RuntimeException rootCause = new RuntimeException("Root cause");
        RuntimeException wrappedException = new RuntimeException("Wrapper exception", rootCause);

        IWError error = IWError.builder(ErrorCode.SYSTEM001)
                .throwable(wrappedException)
                .build();

        ErrorLogger.logError(error);

        assertEquals("Should log exactly one record", 1, testHandler.getLogCount());
        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Log record should not be null", record);
        assertEquals("Log should contain wrapped exception", wrappedException, record.getThrown());
    }

    @Test
    public void testConcurrentLogging() throws InterruptedException {
        testHandler.clear();

        // Create multiple threads that log errors simultaneously
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            final int threadNum = i;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    ErrorLogger.logError(ErrorCode.FLOW001, "Error from thread " + threadNum);
                }
            });
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Verify all logs were recorded
        assertEquals("Should log 10 records from concurrent threads", 10, testHandler.getLogCount());
    }

    @Test
    public void testLogErrorPreservesStackTrace() {
        RuntimeException exception = new RuntimeException("Test exception");
        ErrorLogger.logError(ErrorCode.CONFIG001, "Configuration error", exception);

        LogRecord record = testHandler.getLastLogRecord();
        assertNotNull("Throwable should be preserved", record.getThrown());
        assertTrue("Stack trace should have elements", record.getThrown().getStackTrace().length > 0);
    }
}
