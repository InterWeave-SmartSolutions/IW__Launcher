package com.interweave.error;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for IWError, IWErrorBuilder, ErrorCode, ErrorCategory, and ErrorSeverity classes.
 *
 * Tests comprehensive error framework functionality including:
 * - Error code system and categorization
 * - Error builder pattern
 * - Error message formatting (JSON and log format)
 * - Error metadata (timestamps, transaction IDs, components)
 * - Exception handling
 *
 * @author InterWeave Error Framework Tests
 * @version 1.0
 */
public class IWErrorTest {

    private IWError simpleError;
    private IWError detailedError;
    private Throwable testException;

    @Before
    public void setUp() {
        // Create a simple error for basic tests
        simpleError = IWError.builder(ErrorCode.AUTH001).build();

        // Create a test exception
        testException = new RuntimeException("Test exception message");

        // Create a detailed error with all fields populated
        detailedError = IWError.builder(ErrorCode.DB001)
                .message("Custom database connection failure")
                .cause("Connection timeout after 30 seconds")
                .affectedComponent("DatabaseConnectionPool")
                .suggestedResolution("1. Check database server status\n2. Verify network connectivity\n3. Review firewall settings")
                .documentationLink("https://docs.interweave.com/errors/DB001")
                .throwable(testException)
                .build();
    }

    // ========================================================================
    // ErrorCode Tests
    // ========================================================================

    @Test
    public void testErrorCodeStructure() {
        ErrorCode errorCode = ErrorCode.AUTH001;

        assertNotNull("Error code should not be null", errorCode);
        assertEquals("Error code should be AUTH001", "AUTH001", errorCode.getCode());
        assertEquals("Error category should be AUTH", ErrorCategory.AUTH, errorCode.getCategory());
        assertEquals("Error severity should be ERROR", ErrorSeverity.ERROR, errorCode.getSeverity());
        assertNotNull("Default message should not be null", errorCode.getDefaultMessage());
        assertTrue("Default message should not be empty", errorCode.getDefaultMessage().length() > 0);
    }

    @Test
    public void testErrorCodeCategories() {
        // Test each category has error codes
        assertEquals("AUTH error should be in AUTH category", ErrorCategory.AUTH, ErrorCode.AUTH001.getCategory());
        assertEquals("DB error should be in DB category", ErrorCategory.DB, ErrorCode.DB001.getCategory());
        assertEquals("FLOW error should be in FLOW category", ErrorCategory.FLOW, ErrorCode.FLOW001.getCategory());
        assertEquals("CONFIG error should be in CONFIG category", ErrorCategory.CONFIG, ErrorCode.CONFIG001.getCategory());
        assertEquals("VALIDATION error should be in VALIDATION category", ErrorCategory.VALIDATION, ErrorCode.VALIDATION001.getCategory());
        assertEquals("XPATH error should be in XPATH category", ErrorCategory.XPATH, ErrorCode.XPATH001.getCategory());
        assertEquals("CONNECTION error should be in CONNECTION category", ErrorCategory.CONNECTION, ErrorCode.CONNECTION001.getCategory());
        assertEquals("SYSTEM error should be in SYSTEM category", ErrorCategory.SYSTEM, ErrorCode.SYSTEM001.getCategory());
    }

    @Test
    public void testErrorCodeSeverities() {
        // Test errors have ERROR severity
        assertEquals("AUTH001 should be ERROR", ErrorSeverity.ERROR, ErrorCode.AUTH001.getSeverity());
        assertEquals("DB001 should be ERROR", ErrorSeverity.ERROR, ErrorCode.DB001.getSeverity());

        // Test warnings have WARNING severity
        assertEquals("AUTH004 should be WARNING", ErrorSeverity.WARNING, ErrorCode.AUTH004.getSeverity());
        assertEquals("DB005 should be WARNING", ErrorSeverity.WARNING, ErrorCode.DB005.getSeverity());
    }

    @Test
    public void testErrorCodeFindByCode() {
        // Test successful lookup
        ErrorCode found = ErrorCode.findByCode("AUTH001");
        assertNotNull("Should find AUTH001", found);
        assertEquals("Should return AUTH001", ErrorCode.AUTH001, found);

        // Test case sensitivity
        ErrorCode notFound = ErrorCode.findByCode("auth001");
        assertNull("Should not find lowercase code", notFound);

        // Test null handling
        ErrorCode nullResult = ErrorCode.findByCode(null);
        assertNull("Should return null for null input", nullResult);

        // Test non-existent code
        ErrorCode invalid = ErrorCode.findByCode("INVALID999");
        assertNull("Should return null for invalid code", invalid);
    }

    @Test
    public void testErrorCodeToString() {
        String str = ErrorCode.AUTH001.toString();
        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain code", str.contains("AUTH001"));
        assertTrue("toString should contain severity", str.contains("ERROR"));
    }

    // ========================================================================
    // ErrorCategory Tests
    // ========================================================================

    @Test
    public void testErrorCategoryStructure() {
        ErrorCategory category = ErrorCategory.AUTH;

        assertNotNull("Category should not be null", category);
        assertEquals("Category code should be AUTH", "AUTH", category.getCode());
        assertEquals("Category display name should be Authentication/Authorization", "Authentication/Authorization", category.getDisplayName());
    }

    @Test
    public void testAllErrorCategories() {
        // Verify all categories are defined
        ErrorCategory[] categories = ErrorCategory.values();
        assertEquals("Should have 8 categories", 8, categories.length);

        // Test each category has valid code and display name
        for (ErrorCategory category : categories) {
            assertNotNull("Category code should not be null", category.getCode());
            assertNotNull("Category display name should not be null", category.getDisplayName());
            assertTrue("Category code should not be empty", category.getCode().length() > 0);
            assertTrue("Category display name should not be empty", category.getDisplayName().length() > 0);
        }
    }

    @Test
    public void testErrorCategoryToString() {
        String str = ErrorCategory.AUTH.toString();
        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain code", str.contains("AUTH"));
        assertTrue("toString should contain display name", str.contains("Authentication"));
    }

    // ========================================================================
    // ErrorSeverity Tests
    // ========================================================================

    @Test
    public void testErrorSeverityStructure() {
        ErrorSeverity severity = ErrorSeverity.ERROR;

        assertNotNull("Severity should not be null", severity);
        assertEquals("Severity display name should be Error", "Error", severity.getDisplayName());
    }

    @Test
    public void testAllErrorSeverities() {
        // Verify all severities are defined
        ErrorSeverity[] severities = ErrorSeverity.values();
        assertEquals("Should have 3 severities", 3, severities.length);

        // Test each severity has valid display name
        for (ErrorSeverity severity : severities) {
            assertNotNull("Severity display name should not be null", severity.getDisplayName());
            assertTrue("Severity display name should not be empty", severity.getDisplayName().length() > 0);
        }
    }

    @Test
    public void testErrorSeverityToString() {
        assertEquals("ERROR toString should return display name", "Error", ErrorSeverity.ERROR.toString());
        assertEquals("WARNING toString should return display name", "Warning", ErrorSeverity.WARNING.toString());
        assertEquals("INFO toString should return display name", "Info", ErrorSeverity.INFO.toString());
    }

    // ========================================================================
    // IWError Builder Tests
    // ========================================================================

    @Test
    public void testBuilderBasicConstruction() {
        IWError error = IWError.builder(ErrorCode.AUTH001).build();

        assertNotNull("Error should not be null", error);
        assertEquals("Error code should be AUTH001", ErrorCode.AUTH001, error.getErrorCode());
        assertEquals("Code string should be AUTH001", "AUTH001", error.getCode());
        assertNotNull("Message should not be null", error.getMessage());
        assertNotNull("Transaction ID should not be null", error.getTransactionId());
        assertTrue("Timestamp should be positive", error.getTimestamp() > 0);
    }

    @Test
    public void testBuilderWithCustomMessage() {
        String customMessage = "Custom error message";
        IWError error = IWError.builder(ErrorCode.AUTH001, customMessage).build();

        assertEquals("Should use custom message", customMessage, error.getMessage());
    }

    @Test
    public void testBuilderWithDefaultMessage() {
        IWError error = IWError.builder(ErrorCode.AUTH001).build();

        assertEquals("Should use default message from error code",
                ErrorCode.AUTH001.getDefaultMessage(), error.getMessage());
    }

    @Test
    public void testBuilderFluentAPI() {
        String cause = "Test cause";
        String component = "TestComponent";
        String resolution = "Test resolution";
        String docLink = "https://test.com";

        IWError error = IWError.builder(ErrorCode.DB001)
                .message("Test message")
                .cause(cause)
                .affectedComponent(component)
                .suggestedResolution(resolution)
                .documentationLink(docLink)
                .build();

        assertEquals("Cause should be set", cause, error.getCause());
        assertEquals("Component should be set", component, error.getAffectedComponent());
        assertEquals("Resolution should be set", resolution, error.getSuggestedResolution());
        assertEquals("Doc link should be set", docLink, error.getDocumentationLink());
    }

    @Test
    public void testBuilderWithThrowable() {
        Throwable exception = new IllegalArgumentException("Test exception");
        IWError error = IWError.builder(ErrorCode.CONFIG001)
                .cause(exception)
                .build();

        assertNotNull("Throwable should be set", error.getThrowable());
        assertEquals("Should store original exception", exception, error.getThrowable());
        assertNotNull("Cause should be auto-generated from exception", error.getCause());
        assertTrue("Cause should contain exception class name", error.getCause().contains("IllegalArgumentException"));
        assertTrue("Cause should contain exception message", error.getCause().contains("Test exception"));
    }

    @Test
    public void testBuilderWithCauseAndThrowable() {
        String customCause = "Custom cause description";
        Throwable exception = new RuntimeException("Exception message");

        IWError error = IWError.builder(ErrorCode.DB001)
                .cause(customCause, exception)
                .build();

        assertEquals("Should use custom cause", customCause, error.getCause());
        assertEquals("Should store throwable", exception, error.getThrowable());
    }

    @Test
    public void testBuilderCustomTimestamp() {
        long customTimestamp = 1234567890000L;
        IWError error = IWError.builder(ErrorCode.AUTH001)
                .timestamp(customTimestamp)
                .build();

        assertEquals("Should use custom timestamp", customTimestamp, error.getTimestamp());
    }

    @Test
    public void testBuilderCustomTransactionId() {
        String customTxnId = "CUSTOM-TXN-123";
        IWError error = IWError.builder(ErrorCode.AUTH001)
                .transactionId(customTxnId)
                .build();

        assertEquals("Should use custom transaction ID", customTxnId, error.getTransactionId());
    }

    @Test
    public void testBuilderAutoGeneratesTransactionId() {
        IWError error1 = IWError.builder(ErrorCode.AUTH001).build();
        IWError error2 = IWError.builder(ErrorCode.AUTH001).build();

        assertNotNull("Transaction ID should be auto-generated", error1.getTransactionId());
        assertNotNull("Transaction ID should be auto-generated", error2.getTransactionId());
        assertNotEquals("Each error should have unique transaction ID",
                error1.getTransactionId(), error2.getTransactionId());
        assertTrue("Transaction ID should start with TXN-", error1.getTransactionId().startsWith("TXN-"));
    }

    // ========================================================================
    // IWError Getter Tests
    // ========================================================================

    @Test
    public void testGetCategory() {
        assertEquals("Should get category from error code", ErrorCategory.AUTH, simpleError.getCategory());
        assertEquals("Should get category from error code", ErrorCategory.DB, detailedError.getCategory());
    }

    @Test
    public void testGetSeverity() {
        assertEquals("Should get severity from error code", ErrorSeverity.ERROR, simpleError.getSeverity());
        assertEquals("Should get severity from error code", ErrorSeverity.ERROR, detailedError.getSeverity());
    }

    @Test
    public void testGetCodeString() {
        assertEquals("Should return code string", "AUTH001", simpleError.getCode());
        assertEquals("Should return code string", "DB001", detailedError.getCode());
    }

    @Test
    public void testGettersForOptionalFields() {
        // Test simple error with minimal fields
        assertNotNull("Message should never be null", simpleError.getMessage());
        assertNull("Cause should be null if not set", simpleError.getCause());
        assertNull("Component should be null if not set", simpleError.getAffectedComponent());
        assertNull("Resolution should be null if not set", simpleError.getSuggestedResolution());
        assertNull("Doc link should be null if not set", simpleError.getDocumentationLink());
        assertNull("Throwable should be null if not set", simpleError.getThrowable());

        // Test detailed error with all fields
        assertEquals("Custom database connection failure", detailedError.getMessage());
        assertEquals("Connection timeout after 30 seconds", detailedError.getCause());
        assertEquals("DatabaseConnectionPool", detailedError.getAffectedComponent());
        assertNotNull("Resolution should be set", detailedError.getSuggestedResolution());
        assertEquals("https://docs.interweave.com/errors/DB001", detailedError.getDocumentationLink());
        assertEquals(testException, detailedError.getThrowable());
    }

    // ========================================================================
    // IWError JSON Formatting Tests
    // ========================================================================

    @Test
    public void testToJSONBasic() {
        String json = simpleError.toJSON();

        assertNotNull("JSON should not be null", json);
        assertTrue("JSON should start with {", json.startsWith("{"));
        assertTrue("JSON should end with }", json.endsWith("}"));
        assertTrue("JSON should contain errorCode", json.contains("\"errorCode\""));
        assertTrue("JSON should contain category", json.contains("\"category\""));
        assertTrue("JSON should contain severity", json.contains("\"severity\""));
        assertTrue("JSON should contain message", json.contains("\"message\""));
        assertTrue("JSON should contain timestamp", json.contains("\"timestamp\""));
        assertTrue("JSON should contain transactionId", json.contains("\"transactionId\""));
    }

    @Test
    public void testToJSONDetailed() {
        String json = detailedError.toJSON();

        assertTrue("JSON should contain custom message", json.contains("Custom database connection failure"));
        assertTrue("JSON should contain cause", json.contains("\"cause\""));
        assertTrue("JSON should contain Connection timeout", json.contains("Connection timeout"));
        assertTrue("JSON should contain affectedComponent", json.contains("\"affectedComponent\""));
        assertTrue("JSON should contain DatabaseConnectionPool", json.contains("DatabaseConnectionPool"));
        assertTrue("JSON should contain suggestedResolution", json.contains("\"suggestedResolution\""));
        assertTrue("JSON should contain documentationLink", json.contains("\"documentationLink\""));
        assertTrue("JSON should contain doc URL", json.contains("https://docs.interweave.com/errors/DB001"));
    }

    @Test
    public void testToJSONEscaping() {
        IWError error = IWError.builder(ErrorCode.CONFIG001)
                .message("Error with \"quotes\" and \n newlines and \t tabs")
                .cause("Backslash \\ test")
                .build();

        String json = error.toJSON();

        assertTrue("JSON should escape quotes", json.contains("\\\"quotes\\\""));
        assertTrue("JSON should escape newlines", json.contains("\\n"));
        assertTrue("JSON should escape tabs", json.contains("\\t"));
        assertTrue("JSON should escape backslashes", json.contains("\\\\"));
    }

    // ========================================================================
    // IWError Log String Formatting Tests
    // ========================================================================

    @Test
    public void testToLogStringBasic() {
        String log = simpleError.toLogString();

        assertNotNull("Log string should not be null", log);
        assertTrue("Log should contain error code", log.contains("AUTH001"));
        assertTrue("Log should contain severity", log.contains("ERROR"));
        assertTrue("Log should contain transaction ID", log.contains("Transaction ID:"));
        assertTrue("Log should contain timestamp", log.contains("Timestamp:"));
    }

    @Test
    public void testToLogStringDetailed() {
        String log = detailedError.toLogString();

        assertTrue("Log should contain error code", log.contains("DB001"));
        assertTrue("Log should contain custom message", log.contains("Custom database connection failure"));
        assertTrue("Log should contain cause", log.contains("Cause:"));
        assertTrue("Log should contain cause text", log.contains("Connection timeout"));
        assertTrue("Log should contain affected component", log.contains("Affected Component:"));
        assertTrue("Log should contain component name", log.contains("DatabaseConnectionPool"));
        assertTrue("Log should contain suggested resolution", log.contains("Suggested Resolution:"));
        assertTrue("Log should contain resolution steps", log.contains("Check database server status"));
        assertTrue("Log should contain documentation link", log.contains("Documentation:"));
        assertTrue("Log should contain doc URL", log.contains("https://docs.interweave.com/errors/DB001"));
    }

    @Test
    public void testToLogStringWithException() {
        String log = detailedError.toLogString();

        assertTrue("Log should contain exception section", log.contains("Exception:"));
        assertTrue("Log should contain exception class", log.contains("RuntimeException"));
        assertTrue("Log should contain exception message", log.contains("Test exception message"));
        assertTrue("Log should contain stack trace section", log.contains("Stack Trace:"));
    }

    @Test
    public void testToLogStringMultilineResolution() {
        String log = detailedError.toLogString();

        assertTrue("Log should contain first resolution step", log.contains("Check database server status"));
        assertTrue("Log should contain second resolution step", log.contains("Verify network connectivity"));
        assertTrue("Log should contain third resolution step", log.contains("Review firewall settings"));
    }

    // ========================================================================
    // IWError toString Tests
    // ========================================================================

    @Test
    public void testToString() {
        String str = simpleError.toString();

        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain error code", str.contains("AUTH001"));
        assertTrue("toString should contain message", str.contains(simpleError.getMessage()));
    }

    @Test
    public void testToStringFormat() {
        String str = detailedError.toString();

        assertTrue("toString should start with [", str.startsWith("["));
        assertTrue("toString should contain code in brackets", str.contains("[DB001]"));
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testErrorWithNullThrowableMessage() {
        Throwable exceptionWithNullMessage = new RuntimeException((String) null);
        IWError error = IWError.builder(ErrorCode.SYSTEM001)
                .cause(exceptionWithNullMessage)
                .build();

        assertNotNull("Error should be created", error);
        assertNotNull("Cause should not be null", error.getCause());
        assertTrue("Cause should contain exception class", error.getCause().contains("RuntimeException"));
    }

    @Test
    public void testErrorWithEmptyStrings() {
        IWError error = IWError.builder(ErrorCode.VALIDATION001)
                .message("")
                .cause("")
                .affectedComponent("")
                .suggestedResolution("")
                .documentationLink("")
                .build();

        assertNotNull("Error should be created", error);
        // Empty strings are valid - they're different from null
        assertEquals("Empty message should be preserved", "", error.getMessage());
        assertEquals("Empty cause should be preserved", "", error.getCause());
    }

    @Test
    public void testMultipleErrorsIndependence() {
        IWError error1 = IWError.builder(ErrorCode.AUTH001)
                .message("Error 1")
                .build();

        IWError error2 = IWError.builder(ErrorCode.DB001)
                .message("Error 2")
                .build();

        assertNotEquals("Errors should have different messages", error1.getMessage(), error2.getMessage());
        assertNotEquals("Errors should have different codes", error1.getCode(), error2.getCode());
        assertNotEquals("Errors should have different transaction IDs",
                error1.getTransactionId(), error2.getTransactionId());
    }

    @Test
    public void testTimestampAccuracy() {
        long beforeCreation = System.currentTimeMillis();
        IWError error = IWError.builder(ErrorCode.AUTH001).build();
        long afterCreation = System.currentTimeMillis();

        assertTrue("Timestamp should be after beforeCreation",
                error.getTimestamp() >= beforeCreation);
        assertTrue("Timestamp should be before afterCreation",
                error.getTimestamp() <= afterCreation);
    }

    @Test
    public void testAllErrorCodesAreAccessible() {
        // Verify all error codes can be instantiated
        ErrorCode[] allCodes = ErrorCode.values();
        assertTrue("Should have error codes defined", allCodes.length > 0);

        for (ErrorCode code : allCodes) {
            IWError error = IWError.builder(code).build();
            assertNotNull("Error should be created for code " + code.getCode(), error);
            assertEquals("Error should have correct code", code, error.getErrorCode());
        }
    }
}
