package com.interweave.integration;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.error.ErrorCategory;
import com.interweave.error.ErrorCode;
import com.interweave.error.ErrorDocumentation;
import com.interweave.error.ErrorLogger;
import com.interweave.error.ErrorSeverity;
import com.interweave.error.IWError;
import com.interweave.validation.ConnectionValidator;
import com.interweave.validation.FlowConfigValidator;
import com.interweave.validation.ValidationIssue;
import com.interweave.validation.ValidationResult;
import com.interweave.validation.ValidationService;
import com.interweave.validation.ValidationSeverity;
import com.interweave.validation.XPathValidator;
import com.interweave.web.ErrorHandlingFilter;

/**
 * Integration tests for end-to-end error handling.
 *
 * Tests comprehensive error handling flow from trigger to user display including:
 * - Error flow through web layer (ErrorHandlingFilter)
 * - Validation service integration (all validators coordinated)
 * - Error logging completeness (transaction IDs, stack traces, timestamps)
 * - Error documentation integration
 * - Multi-component error scenarios
 *
 * These tests verify that all error handling components work together correctly
 * to provide a complete error handling solution.
 *
 * @author InterWeave Integration Tests
 * @version 1.0
 */
public class ErrorHandlingIntegrationTest {

    private File tempDir;
    private List<File> tempFiles;
    private TestLogHandler logHandler;
    private Logger errorLogger;

    @Before
    public void setUp() throws IOException {
        // Create temporary directory for test files
        tempDir = createTempDirectory();
        tempFiles = new ArrayList<>();

        // Set up log handler to capture log messages
        logHandler = new TestLogHandler();
        errorLogger = Logger.getLogger("com.interweave.error");
        errorLogger.addHandler(logHandler);
        errorLogger.setLevel(Level.ALL);
    }

    @After
    public void tearDown() {
        // Clean up temporary files
        for (File file : tempFiles) {
            if (file.exists()) {
                file.delete();
            }
        }
        if (tempDir != null && tempDir.exists()) {
            tempDir.delete();
        }

        // Remove log handler
        if (errorLogger != null && logHandler != null) {
            errorLogger.removeHandler(logHandler);
        }
    }

    // ========================================================================
    // Web Layer Integration Tests
    // ========================================================================

    @Test
    public void testErrorHandlingFilterCatchesException() throws Exception {
        ErrorHandlingFilter filter = new ErrorHandlingFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Create a filter chain that throws an exception
        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(javax.servlet.ServletRequest request,
                               javax.servlet.ServletResponse response)
                    throws IOException, ServletException {
                throw new RuntimeException("Test exception from filter chain");
            }
        };

        // Execute filter - should catch exception and handle it
        filter.doFilter(request, response, chain);

        // Verify error was logged
        assertFalse("Error should have been logged", logHandler.getLogRecords().isEmpty());

        // Find error log record
        LogRecord errorRecord = null;
        for (LogRecord record : logHandler.getLogRecords()) {
            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                errorRecord = record;
                break;
            }
        }

        assertNotNull("Should have error log record", errorRecord);
        assertTrue("Log message should contain exception text",
                errorRecord.getMessage().contains("Test exception") ||
                (errorRecord.getThrown() != null &&
                 errorRecord.getThrown().getMessage().contains("Test exception")));
    }

    @Test
    public void testErrorHandlingFilterPageRequest() throws Exception {
        ErrorHandlingFilter filter = new ErrorHandlingFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);

        // Create page request (not API)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test.jsp");
        request.setMethod("GET");
        request.setHeader("Accept", "text/html");

        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(javax.servlet.ServletRequest request,
                               javax.servlet.ServletResponse response)
                    throws IOException, ServletException {
                throw new IllegalArgumentException("Invalid configuration parameter");
            }
        };

        filter.doFilter(request, response, chain);

        // Verify error was handled (logged)
        boolean foundLog = false;
        for (LogRecord record : logHandler.getLogRecords()) {
            if (record.getMessage() != null &&
                (record.getMessage().contains("Invalid configuration") ||
                 record.getMessage().contains("CONFIG"))) {
                foundLog = true;
                break;
            }
        }
        assertTrue("Error should be logged for page request", foundLog);
    }

    @Test
    public void testErrorHandlingFilterAPIRequest() throws Exception {
        ErrorHandlingFilter filter = new ErrorHandlingFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);

        // Create API request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/flows");
        request.setMethod("POST");
        request.setHeader("Accept", "application/json");

        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(javax.servlet.ServletRequest request,
                               javax.servlet.ServletResponse response)
                    throws IOException, ServletException {
                throw new RuntimeException("Database connection failed");
            }
        };

        filter.doFilter(request, response, chain);

        // Verify error was logged
        assertFalse("Error should be logged", logHandler.getLogRecords().isEmpty());
    }

    // ========================================================================
    // Validation Service Integration Tests
    // ========================================================================

    @Test
    public void testValidationServiceIntegration() throws IOException {
        // Create a test project structure with various files
        File configFile = createTempFile("config.xml",
                "<?xml version=\"1.0\"?>\n" +
                "<BusinessDaemonConfiguration Name=\"TestIM\" " +
                "HartbeatInterval=\"30000\" " +
                "RefreshInterval=\"60000\" " +
                "PrimaryTSURL=\"http://localhost:8080/ts\" " +
                "SecondaryTSURL=\"\" " +
                "PrimaryTSURLT=\"\" " +
                "SecondaryTSURLT=\"\" " +
                "PrimaryTSURL1=\"\" " +
                "SecondaryTSURL1=\"\" " +
                "PrimaryTSURLT1=\"\" " +
                "SecondaryTSURLT1=\"\" " +
                "PrimaryTSURLD=\"\" " +
                "SecondaryTSURLD=\"\" " +
                "FailoverURL=\"\" " +
                "IsPrimary=\"1\" " +
                "RunAtStartUp=\"0\" " +
                "BufferSize=\"2048\" " +
                "IsHosted=\"0\"/>");

        File flowFile = createTempFile("flow.xml",
                "<?xml version=\"1.0\"?>\n" +
                "<iwmappings>\n" +
                "  <mapping name=\"TestFlow\" type=\"sync\">\n" +
                "    <description>Test flow for integration testing</description>\n" +
                "    <source entity=\"Order\" connection=\"sourceConn\"/>\n" +
                "    <destination entity=\"Order\" connection=\"destConn\" operation=\"create\"/>\n" +
                "  </mapping>\n" +
                "</iwmappings>");

        // Create ValidationService and configure it
        ValidationService service = new ValidationService(tempDir.getAbsolutePath());

        // Register connections that the flow references
        ConnectionValidator connValidator = new ConnectionValidator();
        connValidator.registerConnection("sourceConn", "REST", new HashMap<String, String>() {{
            put("url", "http://source.example.com/api");
            put("authenticationType", "BASIC");
        }});
        connValidator.registerConnection("destConn", "REST", new HashMap<String, String>() {{
            put("url", "http://dest.example.com/api");
            put("authenticationType", "BASIC");
        }});

        // Validate individual files
        ValidationResult configResult = service.validateFile(configFile.getAbsolutePath());
        assertNotNull("Config validation result should not be null", configResult);
        assertTrue("Valid config should pass validation", configResult.isValid());

        ValidationResult flowResult = service.validateFile(flowFile.getAbsolutePath());
        assertNotNull("Flow validation result should not be null", flowResult);
    }

    @Test
    public void testValidationServiceWithErrors() throws IOException {
        // Create files with validation errors
        File invalidXPathFile = createTempFile("invalid.xml",
                "<?xml version=\"1.0\"?>\n" +
                "<test xpath=\"/Orders/Order[@id='123'\"/>");  // Missing closing bracket

        XPathValidator validator = new XPathValidator();
        ValidationResult result = validator.validate("/Orders/Order[@id='123'", invalidXPathFile.getAbsolutePath());

        assertNotNull("Validation result should not be null", result);
        assertFalse("Invalid XPath should fail validation", result.isValid());
        assertFalse("Should have validation issues", result.getIssues().isEmpty());

        ValidationIssue issue = result.getIssues().get(0);
        assertNotNull("Issue should have message", issue.getMessage());
        assertNotNull("Issue should have category", issue.getCategory());
        assertEquals("Category should be XPath", "XPath", issue.getCategory());
    }

    @Test
    public void testValidationServiceMultipleValidators() throws IOException {
        // Test that multiple validators can work together
        XPathValidator xpathValidator = new XPathValidator();
        ConnectionValidator connectionValidator = new ConnectionValidator();
        FlowConfigValidator flowValidator = new FlowConfigValidator();

        // Configure connection validator
        Map<String, String> connConfig = new HashMap<>();
        connConfig.put("url", "http://example.com/api");
        connConfig.put("authenticationType", "BASIC");
        connConfig.put("username", "testuser");
        connConfig.put("password", "testpass");
        connectionValidator.registerConnection("testConn", "REST", connConfig);

        // Validate XPath
        ValidationResult xpathResult = xpathValidator.validate("/Orders/Order[@id='123']");
        assertTrue("Valid XPath should pass", xpathResult.isValid());

        // Validate connection
        ValidationResult connResult = connectionValidator.validate();
        assertTrue("Valid connection should pass", connResult.isValid());

        // Create and validate flow
        File flowFile = createTempFile("test-flow.xml",
                "<?xml version=\"1.0\"?>\n" +
                "<iwmappings>\n" +
                "  <mapping name=\"TestFlow\" type=\"import\">\n" +
                "    <description>Test mapping</description>\n" +
                "    <source entity=\"Product\" connection=\"testConn\"/>\n" +
                "    <destination entity=\"Product\" connection=\"testConn\" operation=\"create\"/>\n" +
                "  </mapping>\n" +
                "</iwmappings>");

        ValidationResult flowResult = flowValidator.validateFile(flowFile);
        assertNotNull("Flow validation should return result", flowResult);
    }

    // ========================================================================
    // Error Logging Integration Tests
    // ========================================================================

    @Test
    public void testErrorLoggerIntegration() {
        // Clear existing logs
        logHandler.clear();

        // Create and log an error
        IWError error = IWError.builder(ErrorCode.DB001)
                .message("Database connection failed")
                .cause("Connection timeout after 30 seconds")
                .affectedComponent("DatabaseConnectionPool")
                .suggestedResolution("1. Check database server status\n2. Verify network connectivity")
                .documentationLink(ErrorDocumentation.getDocumentationUrl(ErrorCode.DB001))
                .build();

        ErrorLogger.logError(error);

        // Verify logging occurred
        assertFalse("Should have log records", logHandler.getLogRecords().isEmpty());

        LogRecord record = logHandler.getLogRecords().get(0);
        assertNotNull("Log record should not be null", record);
        assertEquals("Log level should be SEVERE", Level.SEVERE, record.getLevel());

        String message = record.getMessage();
        assertNotNull("Log message should not be null", message);
        assertTrue("Log should contain error code", message.contains("DB001"));
        assertTrue("Log should contain transaction ID", message.contains("Transaction ID:") || message.contains("TXN-"));
    }

    @Test
    public void testErrorLoggerWithException() {
        logHandler.clear();

        Exception testException = new RuntimeException("Test exception message");
        IWError error = IWError.builder(ErrorCode.SYSTEM001)
                .message("Unexpected system error")
                .throwable(testException)
                .build();

        ErrorLogger.logError(error);

        assertFalse("Should have log records", logHandler.getLogRecords().isEmpty());

        LogRecord record = logHandler.getLogRecords().get(0);
        assertNotNull("Should have throwable", record.getThrown());
        assertEquals("Throwable should be the test exception", testException, record.getThrown());
    }

    @Test
    public void testErrorLoggingCompleteness() {
        logHandler.clear();

        // Create comprehensive error
        RuntimeException exception = new RuntimeException("Root cause exception");
        IWError error = IWError.builder(ErrorCode.FLOW001)
                .message("Flow execution failed")
                .cause("Invalid XPath expression in transformation")
                .affectedComponent("FlowExecutor")
                .suggestedResolution("Review XPath syntax in transformation mapping")
                .documentationLink(ErrorDocumentation.getDocumentationUrl(ErrorCode.FLOW001))
                .throwable(exception)
                .build();

        ErrorLogger.logError(error);

        // Verify all components are present
        assertNotNull("Error should have error code", error.getErrorCode());
        assertEquals("Error code should be FLOW001", ErrorCode.FLOW001, error.getErrorCode());
        assertNotNull("Error should have message", error.getMessage());
        assertNotNull("Error should have cause", error.getCause());
        assertNotNull("Error should have affected component", error.getAffectedComponent());
        assertNotNull("Error should have resolution", error.getSuggestedResolution());
        assertNotNull("Error should have documentation link", error.getDocumentationLink());
        assertNotNull("Error should have throwable", error.getThrowable());
        assertNotNull("Error should have transaction ID", error.getTransactionId());
        assertTrue("Error should have timestamp", error.getTimestamp() > 0);

        // Verify logging
        assertFalse("Should have log records", logHandler.getLogRecords().isEmpty());
        LogRecord record = logHandler.getLogRecords().get(0);

        String logMessage = record.getMessage();
        assertTrue("Log should contain error code", logMessage.contains("FLOW001"));
        assertTrue("Log should contain component", logMessage.contains("FlowExecutor") || logMessage.contains("Affected Component"));
    }

    // ========================================================================
    // Error Documentation Integration Tests
    // ========================================================================

    @Test
    public void testErrorDocumentationIntegration() {
        // Test that error documentation is available for all error codes
        for (ErrorCode code : ErrorCode.values()) {
            String docUrl = ErrorDocumentation.getDocumentationUrl(code);
            assertNotNull("Documentation URL should not be null for " + code.getCode(), docUrl);
            assertFalse("Documentation URL should not be empty for " + code.getCode(), docUrl.isEmpty());
            assertTrue("Documentation URL should be valid for " + code.getCode(),
                    docUrl.startsWith("http"));
        }
    }

    @Test
    public void testErrorDocumentationResolutionSteps() {
        // Test that common errors have resolution steps
        ErrorCode[] commonErrors = {
            ErrorCode.AUTH001, ErrorCode.DB001, ErrorCode.FLOW001,
            ErrorCode.CONFIG001, ErrorCode.VALIDATION001, ErrorCode.XPATH001,
            ErrorCode.CONNECTION001
        };

        for (ErrorCode code : commonErrors) {
            String resolution = ErrorDocumentation.getResolutionSteps(code);
            assertNotNull("Resolution should not be null for " + code.getCode(), resolution);
            assertFalse("Resolution should not be empty for " + code.getCode(), resolution.isEmpty());
        }
    }

    // ========================================================================
    // Multi-Component Integration Tests
    // ========================================================================

    @Test
    public void testEndToEndErrorFlow() {
        logHandler.clear();

        // Simulate complete error flow: validation error -> error creation -> logging -> documentation

        // Step 1: Validation detects error
        XPathValidator validator = new XPathValidator();
        ValidationResult validationResult = validator.validate("/Orders/Order[@id='123'");  // Invalid

        assertFalse("Validation should fail", validationResult.isValid());
        assertFalse("Should have issues", validationResult.getIssues().isEmpty());

        // Step 2: Convert validation issue to IWError
        ValidationIssue issue = validationResult.getIssues().get(0);
        IWError error = IWError.builder(ErrorCode.XPATH001)
                .message("XPath validation failed: " + issue.getMessage())
                .cause(issue.getMessage())
                .affectedComponent("XPathValidator")
                .suggestedResolution(issue.getSuggestion())
                .documentationLink(ErrorDocumentation.getDocumentationUrl(ErrorCode.XPATH001))
                .build();

        // Step 3: Log the error
        ErrorLogger.logError(error);

        // Step 4: Verify complete flow
        assertNotNull("Error should have transaction ID", error.getTransactionId());
        assertNotNull("Error should have documentation", error.getDocumentationLink());
        assertFalse("Error should be logged", logHandler.getLogRecords().isEmpty());

        // Step 5: Verify log contains all necessary information
        LogRecord logRecord = logHandler.getLogRecords().get(0);
        String logMessage = logRecord.getMessage();
        assertTrue("Log should contain error code", logMessage.contains("XPATH001"));
        assertTrue("Log should reference component",
                logMessage.contains("XPathValidator") || logMessage.contains("Component"));
    }

    @Test
    public void testMultipleErrorsIndependence() {
        logHandler.clear();

        // Create multiple errors and verify they're independent
        IWError error1 = IWError.builder(ErrorCode.AUTH001).build();
        IWError error2 = IWError.builder(ErrorCode.DB001).build();
        IWError error3 = IWError.builder(ErrorCode.FLOW001).build();

        ErrorLogger.logError(error1);
        ErrorLogger.logError(error2);
        ErrorLogger.logError(error3);

        // Verify unique transaction IDs
        assertNotEquals("Errors should have different transaction IDs",
                error1.getTransactionId(), error2.getTransactionId());
        assertNotEquals("Errors should have different transaction IDs",
                error2.getTransactionId(), error3.getTransactionId());

        // Verify all were logged
        assertTrue("Should have at least 3 log records", logHandler.getLogRecords().size() >= 3);
    }

    @Test
    public void testErrorCategoryAndSeverityIntegration() {
        // Verify that all error categories and severities work correctly
        for (ErrorCategory category : ErrorCategory.values()) {
            assertNotNull("Category should have code", category.getCode());
            assertNotNull("Category should have display name", category.getDisplayName());
        }

        for (ErrorSeverity severity : ErrorSeverity.values()) {
            assertNotNull("Severity should have display name", severity.getDisplayName());
        }

        // Test that errors maintain category and severity
        IWError authError = IWError.builder(ErrorCode.AUTH001).build();
        assertEquals("Auth error should be in AUTH category",
                ErrorCategory.AUTH, authError.getCategory());
        assertEquals("AUTH001 should be ERROR severity",
                ErrorSeverity.ERROR, authError.getSeverity());
    }

    // ========================================================================
    // Helper Methods and Mock Classes
    // ========================================================================

    private File createTempDirectory() throws IOException {
        File temp = File.createTempFile("iw-test-", "");
        temp.delete();
        temp.mkdir();
        return temp;
    }

    private File createTempFile(String name, String content) throws IOException {
        File file = new File(tempDir, name);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        tempFiles.add(file);
        return file;
    }

    /**
     * Test log handler that captures log records for verification
     */
    private static class TestLogHandler extends Handler {
        private final List<LogRecord> logRecords = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            logRecords.add(record);
        }

        @Override
        public void flush() {
            // No-op
        }

        @Override
        public void close() {
            logRecords.clear();
        }

        public List<LogRecord> getLogRecords() {
            return logRecords;
        }

        public void clear() {
            logRecords.clear();
        }
    }

    /**
     * Mock HttpServletRequest for testing
     */
    private static class MockHttpServletRequest implements HttpServletRequest {
        private String requestURI;
        private String method;
        private final Map<String, String> headers = new HashMap<>();
        private final Map<String, Object> attributes = new HashMap<>();

        public void setRequestURI(String uri) {
            this.requestURI = uri;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setHeader(String name, String value) {
            headers.put(name, value);
        }

        @Override
        public String getRequestURI() {
            return requestURI;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public String getHeader(String name) {
            return headers.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        // Stub implementations of other required methods
        @Override public String getAuthType() { return null; }
        @Override public javax.servlet.http.Cookie[] getCookies() { return null; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public java.util.Enumeration<String> getHeaders(String name) { return null; }
        @Override public java.util.Enumeration<String> getHeaderNames() { return null; }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getContextPath() { return ""; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public StringBuffer getRequestURL() { return new StringBuffer(); }
        @Override public String getServletPath() { return ""; }
        @Override public javax.servlet.http.HttpSession getSession(boolean create) { return null; }
        @Override public javax.servlet.http.HttpSession getSession() { return null; }
        @Override public String changeSessionId() { return null; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public java.util.Collection<javax.servlet.http.Part> getParts() { return null; }
        @Override public javax.servlet.http.Part getPart(String name) { return null; }
        @Override public <T extends javax.servlet.http.HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public java.util.Enumeration<String> getAttributeNames() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public javax.servlet.ServletInputStream getInputStream() { return null; }
        @Override public String getParameter(String name) { return null; }
        @Override public java.util.Enumeration<String> getParameterNames() { return null; }
        @Override public String[] getParameterValues(String name) { return null; }
        @Override public java.util.Map<String, String[]> getParameterMap() { return null; }
        @Override public String getProtocol() { return "HTTP/1.1"; }
        @Override public String getScheme() { return "http"; }
        @Override public String getServerName() { return "localhost"; }
        @Override public int getServerPort() { return 8080; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return "127.0.0.1"; }
        @Override public String getRemoteHost() { return "localhost"; }
        @Override public void removeAttribute(String name) { attributes.remove(name); }
        @Override public java.util.Locale getLocale() { return java.util.Locale.getDefault(); }
        @Override public java.util.Enumeration<java.util.Locale> getLocales() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public javax.servlet.RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public String getRealPath(String path) { return null; }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return "localhost"; }
        @Override public String getLocalAddr() { return "127.0.0.1"; }
        @Override public int getLocalPort() { return 8080; }
        @Override public javax.servlet.ServletContext getServletContext() { return null; }
        @Override public javax.servlet.AsyncContext startAsync() { return null; }
        @Override public javax.servlet.AsyncContext startAsync(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public javax.servlet.AsyncContext getAsyncContext() { return null; }
        @Override public javax.servlet.DispatcherType getDispatcherType() { return null; }
    }

    /**
     * Mock HttpServletResponse for testing
     */
    private static class MockHttpServletResponse implements HttpServletResponse {
        private int status = 200;
        private String contentType;
        private final StringWriter stringWriter = new StringWriter();
        private final PrintWriter writer = new PrintWriter(stringWriter);
        private final Map<String, String> headers = new HashMap<>();

        @Override
        public void setStatus(int sc) {
            this.status = sc;
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public void setContentType(String type) {
            this.contentType = type;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public void setHeader(String name, String value) {
            headers.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            return headers.get(name);
        }

        public String getOutput() {
            writer.flush();
            return stringWriter.toString();
        }

        // Stub implementations of other required methods
        @Override public void addCookie(javax.servlet.http.Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return headers.containsKey(name); }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public String encodeUrl(String url) { return url; }
        @Override public String encodeRedirectUrl(String url) { return url; }
        @Override public void sendError(int sc, String msg) { this.status = sc; }
        @Override public void sendError(int sc) { this.status = sc; }
        @Override public void sendRedirect(String location) {}
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void addHeader(String name, String value) { headers.put(name, value); }
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public void setStatus(int sc, String sm) { this.status = sc; }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public javax.servlet.ServletOutputStream getOutputStream() { return null; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(java.util.Locale loc) {}
        @Override public java.util.Locale getLocale() { return java.util.Locale.getDefault(); }
        @Override public java.util.Collection<String> getHeaders(String name) { return null; }
        @Override public java.util.Collection<String> getHeaderNames() { return headers.keySet(); }
    }

    /**
     * Mock FilterConfig for testing
     */
    private static class MockFilterConfig implements javax.servlet.FilterConfig {
        private final Map<String, String> initParameters = new HashMap<>();

        @Override
        public String getFilterName() {
            return "ErrorHandlingFilter";
        }

        @Override
        public javax.servlet.ServletContext getServletContext() {
            return new MockServletContext();
        }

        @Override
        public String getInitParameter(String name) {
            return initParameters.get(name);
        }

        @Override
        public java.util.Enumeration<String> getInitParameterNames() {
            return java.util.Collections.enumeration(initParameters.keySet());
        }
    }

    /**
     * Mock ServletContext for testing
     */
    private static class MockServletContext implements javax.servlet.ServletContext {
        private final List<String> logs = new ArrayList<>();

        @Override
        public void log(String msg) {
            logs.add(msg);
        }

        @Override
        public void log(String message, Throwable throwable) {
            logs.add(message + ": " + throwable.getMessage());
        }

        // Stub implementations of other required methods
        @Override public String getContextPath() { return ""; }
        @Override public javax.servlet.ServletContext getContext(String uripath) { return null; }
        @Override public int getMajorVersion() { return 3; }
        @Override public int getMinorVersion() { return 1; }
        @Override public int getEffectiveMajorVersion() { return 3; }
        @Override public int getEffectiveMinorVersion() { return 1; }
        @Override public String getMimeType(String file) { return null; }
        @Override public java.util.Set<String> getResourcePaths(String path) { return null; }
        @Override public java.net.URL getResource(String path) { return null; }
        @Override public java.io.InputStream getResourceAsStream(String path) { return null; }
        @Override public javax.servlet.RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public javax.servlet.RequestDispatcher getNamedDispatcher(String name) { return null; }
        @Override public javax.servlet.Servlet getServlet(String name) { return null; }
        @Override public java.util.Enumeration<javax.servlet.Servlet> getServlets() { return null; }
        @Override public java.util.Enumeration<String> getServletNames() { return null; }
        @Override public void log(Exception exception, String msg) {}
        @Override public String getRealPath(String path) { return null; }
        @Override public String getServerInfo() { return "Mock"; }
        @Override public String getInitParameter(String name) { return null; }
        @Override public java.util.Enumeration<String> getInitParameterNames() { return null; }
        @Override public boolean setInitParameter(String name, String value) { return false; }
        @Override public Object getAttribute(String name) { return null; }
        @Override public java.util.Enumeration<String> getAttributeNames() { return null; }
        @Override public void setAttribute(String name, Object object) {}
        @Override public void removeAttribute(String name) {}
        @Override public String getServletContextName() { return "Mock"; }
        @Override public javax.servlet.ServletRegistration.Dynamic addServlet(String servletName, String className) { return null; }
        @Override public javax.servlet.ServletRegistration.Dynamic addServlet(String servletName, javax.servlet.Servlet servlet) { return null; }
        @Override public javax.servlet.ServletRegistration.Dynamic addServlet(String servletName, Class<? extends javax.servlet.Servlet> servletClass) { return null; }
        @Override public <T extends javax.servlet.Servlet> T createServlet(Class<T> clazz) { return null; }
        @Override public javax.servlet.ServletRegistration getServletRegistration(String servletName) { return null; }
        @Override public java.util.Map<String, ? extends javax.servlet.ServletRegistration> getServletRegistrations() { return null; }
        @Override public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) { return null; }
        @Override public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, javax.servlet.Filter filter) { return null; }
        @Override public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends javax.servlet.Filter> filterClass) { return null; }
        @Override public <T extends javax.servlet.Filter> T createFilter(Class<T> clazz) { return null; }
        @Override public javax.servlet.FilterRegistration getFilterRegistration(String filterName) { return null; }
        @Override public java.util.Map<String, ? extends javax.servlet.FilterRegistration> getFilterRegistrations() { return null; }
        @Override public javax.servlet.SessionCookieConfig getSessionCookieConfig() { return null; }
        @Override public void setSessionTrackingModes(java.util.Set<javax.servlet.SessionTrackingMode> sessionTrackingModes) {}
        @Override public java.util.Set<javax.servlet.SessionTrackingMode> getDefaultSessionTrackingModes() { return null; }
        @Override public java.util.Set<javax.servlet.SessionTrackingMode> getEffectiveSessionTrackingModes() { return null; }
        @Override public void addListener(String className) {}
        @Override public <T extends java.util.EventListener> void addListener(T t) {}
        @Override public void addListener(Class<? extends java.util.EventListener> listenerClass) {}
        @Override public <T extends java.util.EventListener> T createListener(Class<T> clazz) { return null; }
        @Override public javax.servlet.descriptor.JspConfigDescriptor getJspConfigDescriptor() { return null; }
        @Override public ClassLoader getClassLoader() { return null; }
        @Override public void declareRoles(String... roleNames) {}
        @Override public String getVirtualServerName() { return null; }
    }
}
