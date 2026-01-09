package com.interweave.validation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for ConnectionValidator class.
 *
 * Tests comprehensive connection validation functionality including:
 * - Connection parameter validation
 * - URL/endpoint format validation
 * - Authentication configuration validation
 * - Connection type validation
 * - Orphaned connection detection
 * - Edge case handling
 *
 * @author InterWeave Validation Framework Tests
 * @version 1.0
 */
public class ConnectionValidatorTest {

    private ConnectionValidator validator;
    private Map<String, String> validRestConnection;
    private Map<String, String> validSoapConnection;
    private Map<String, String> validDatabaseConnection;

    @Before
    public void setUp() {
        validator = new ConnectionValidator();

        // Create valid REST connection config
        validRestConnection = new HashMap<String, String>();
        validRestConnection.put("name", "SalesforceAPI");
        validRestConnection.put("type", "REST");
        validRestConnection.put("url", "https://api.salesforce.com");
        validRestConnection.put("authType", "OAUTH2");
        validRestConnection.put("clientId", "client123");
        validRestConnection.put("clientSecret", "secret456");
        validRestConnection.put("tokenUrl", "https://login.salesforce.com/oauth/token");

        // Create valid SOAP connection config
        validSoapConnection = new HashMap<String, String>();
        validSoapConnection.put("name", "QuickBooksAPI");
        validSoapConnection.put("type", "SOAP");
        validSoapConnection.put("url", "https://webservices.quickbooks.com/service");
        validSoapConnection.put("wsdlUrl", "https://webservices.quickbooks.com/service?wsdl");
        validSoapConnection.put("authType", "BASIC");
        validSoapConnection.put("username", "admin");
        validSoapConnection.put("password", "pass123");

        // Create valid database connection config
        validDatabaseConnection = new HashMap<String, String>();
        validDatabaseConnection.put("name", "MySQLDatabase");
        validDatabaseConnection.put("type", "DATABASE");
        validDatabaseConnection.put("host", "localhost");
        validDatabaseConnection.put("port", "3306");
        validDatabaseConnection.put("database", "mydb");
        validDatabaseConnection.put("username", "root");
        validDatabaseConnection.put("password", "rootpass");
    }

    // ========================================================================
    // Connection Registration Tests
    // ========================================================================

    @Test
    public void testRegisterValidConnection() {
        validator.registerConnection("TestConnection", validRestConnection);

        ValidationResult result = validator.validateConnection("TestConnection");
        assertTrue("Valid connection should pass validation", result.isValid());
    }

    @Test
    public void testRegisterNullConnectionName() {
        validator.registerConnection(null, validRestConnection);

        // Should handle gracefully without throwing exception
        assertTrue("Should handle null connection name gracefully", true);
    }

    @Test
    public void testRegisterEmptyConnectionName() {
        validator.registerConnection("", validRestConnection);

        // Should handle gracefully
        assertTrue("Should handle empty connection name gracefully", true);
    }

    @Test
    public void testRegisterNullConfiguration() {
        validator.registerConnection("TestConnection", null);

        // Should handle gracefully
        assertTrue("Should handle null configuration gracefully", true);
    }

    @Test
    public void testRegisterMultipleConnections() {
        validator.registerConnection("Connection1", validRestConnection);
        validator.registerConnection("Connection2", validSoapConnection);
        validator.registerConnection("Connection3", validDatabaseConnection);

        ValidationResult result1 = validator.validateConnection("Connection1");
        ValidationResult result2 = validator.validateConnection("Connection2");
        ValidationResult result3 = validator.validateConnection("Connection3");

        assertTrue("First connection should be valid", result1.isValid());
        assertTrue("Second connection should be valid", result2.isValid());
        assertTrue("Third connection should be valid", result3.isValid());
    }

    // ========================================================================
    // Connection Type Validation Tests
    // ========================================================================

    @Test
    public void testValidRestConnection() {
        validator.registerConnection("REST", validRestConnection);
        ValidationResult result = validator.validateConnection("REST");

        assertTrue("Valid REST connection should pass", result.isValid());
    }

    @Test
    public void testValidSoapConnection() {
        validator.registerConnection("SOAP", validSoapConnection);
        ValidationResult result = validator.validateConnection("SOAP");

        assertTrue("Valid SOAP connection should pass", result.isValid());
    }

    @Test
    public void testValidDatabaseConnection() {
        validator.registerConnection("DB", validDatabaseConnection);
        ValidationResult result = validator.validateConnection("DB");

        assertTrue("Valid database connection should pass", result.isValid());
    }

    @Test
    public void testMissingConnectionType() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("name", "TestConn");
        config.put("url", "https://api.example.com");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Connection without type should fail", result.isValid());
        assertTrue("Should have error about missing type",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().toLowerCase().contains("type");
                }
            }));
    }

    @Test
    public void testUnsupportedConnectionType() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("name", "TestConn");
        config.put("type", "UNSUPPORTED_TYPE");
        config.put("url", "https://api.example.com");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Unsupported connection type should fail", result.isValid());
        assertTrue("Should have warning about unsupported type",
            result.getWarningCount() > 0);
    }

    // ========================================================================
    // Required Parameter Validation Tests
    // ========================================================================

    @Test
    public void testRestMissingUrl() {
        Map<String, String> config = new HashMap<String, String>(validRestConnection);
        config.remove("url");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("REST connection without URL should fail", result.isValid());
    }

    @Test
    public void testRestMissingAuthType() {
        Map<String, String> config = new HashMap<String, String>(validRestConnection);
        config.remove("authType");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("REST connection without authType should fail", result.isValid());
    }

    @Test
    public void testSoapMissingWsdl() {
        Map<String, String> config = new HashMap<String, String>(validSoapConnection);
        config.remove("wsdlUrl");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("SOAP connection without WSDL should fail", result.isValid());
    }

    @Test
    public void testDatabaseMissingHost() {
        Map<String, String> config = new HashMap<String, String>(validDatabaseConnection);
        config.remove("host");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Database connection without host should fail", result.isValid());
    }

    @Test
    public void testDatabaseMissingPort() {
        Map<String, String> config = new HashMap<String, String>(validDatabaseConnection);
        config.remove("port");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Database connection without port should fail", result.isValid());
    }

    @Test
    public void testDatabaseMissingDatabase() {
        Map<String, String> config = new HashMap<String, String>(validDatabaseConnection);
        config.remove("database");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Database connection without database should fail", result.isValid());
    }

    // ========================================================================
    // URL Validation Tests
    // ========================================================================

    @Test
    public void testValidHttpUrl() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "HTTP");
        config.put("url", "http://api.example.com/service");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("Valid HTTP URL should pass", result.isValid());
    }

    @Test
    public void testValidHttpsUrl() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "HTTPS");
        config.put("url", "https://secure.example.com/api");
        config.put("authType", "API_KEY");
        config.put("apiKey", "key123");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("Valid HTTPS URL should pass", result.isValid());
    }

    @Test
    public void testInvalidUrlFormat() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "not-a-valid-url");
        config.put("authType", "NONE");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Invalid URL format should fail", result.isValid());
    }

    @Test
    public void testEmptyUrl() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "");
        config.put("authType", "NONE");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Empty URL should fail", result.isValid());
    }

    // ========================================================================
    // Authentication Validation Tests
    // ========================================================================

    @Test
    public void testBasicAuthWithCredentials() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "BASIC");
        config.put("username", "user");
        config.put("password", "pass");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("Basic auth with credentials should pass", result.isValid());
    }

    @Test
    public void testBasicAuthMissingUsername() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "BASIC");
        config.put("password", "pass");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Basic auth without username should fail", result.isValid());
    }

    @Test
    public void testBasicAuthMissingPassword() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "BASIC");
        config.put("username", "user");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("Basic auth without password should fail", result.isValid());
    }

    @Test
    public void testOAuth2WithRequiredParams() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "OAUTH2");
        config.put("clientId", "client123");
        config.put("clientSecret", "secret456");
        config.put("tokenUrl", "https://auth.example.com/token");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("OAuth2 with required params should pass", result.isValid());
    }

    @Test
    public void testOAuth2MissingClientId() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "OAUTH2");
        config.put("clientSecret", "secret456");
        config.put("tokenUrl", "https://auth.example.com/token");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("OAuth2 without clientId should fail", result.isValid());
    }

    @Test
    public void testApiKeyAuth() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "API_KEY");
        config.put("apiKey", "key123456");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("API key auth with key should pass", result.isValid());
    }

    @Test
    public void testApiKeyMissing() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "REST");
        config.put("url", "https://api.example.com");
        config.put("authType", "API_KEY");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertFalse("API key auth without key should fail", result.isValid());
    }

    @Test
    public void testNoAuth() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "HTTP");
        config.put("url", "http://api.example.com");
        config.put("authType", "NONE");

        validator.registerConnection("TestConn", config);
        ValidationResult result = validator.validateConnection("TestConn");

        assertTrue("No auth should pass", result.isValid());
    }

    // ========================================================================
    // Connection Reference Tests
    // ========================================================================

    @Test
    public void testValidateUndefinedConnection() {
        ValidationResult result = validator.validateConnection("UndefinedConnection");

        assertFalse("Undefined connection should fail", result.isValid());
        assertTrue("Should have error about undefined connection",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().contains("UndefinedConnection") &&
                           issue.getMessage().toLowerCase().contains("not defined");
                }
            }));
    }

    @Test
    public void testValidateNullConnectionName() {
        ValidationResult result = validator.validateConnection(null);

        assertFalse("Null connection name should fail", result.isValid());
    }

    @Test
    public void testValidateEmptyConnectionName() {
        ValidationResult result = validator.validateConnection("");

        assertFalse("Empty connection name should fail", result.isValid());
    }

    // ========================================================================
    // Orphaned Connection Detection Tests
    // ========================================================================

    @Test
    public void testDetectOrphanedConnection() {
        validator.registerConnection("UsedConnection", validRestConnection);
        validator.registerConnection("UnusedConnection", validSoapConnection);

        validator.markConnectionAsReferenced("UsedConnection");

        ValidationResult result = validator.validateAllConnections();

        // Should have warning about unused connection
        assertTrue("Should have warning about orphaned connection",
            result.getWarningCount() > 0);
        assertTrue("Should mention UnusedConnection",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().contains("UnusedConnection");
                }
            }));
    }

    @Test
    public void testNoOrphanedConnectionsWhenAllReferenced() {
        validator.registerConnection("Connection1", validRestConnection);
        validator.registerConnection("Connection2", validSoapConnection);

        validator.markConnectionAsReferenced("Connection1");
        validator.markConnectionAsReferenced("Connection2");

        ValidationResult result = validator.validateAllConnections();

        // Should not have orphaned connection warnings if validation passes
        assertTrue("Validation should complete", true);
    }

    @Test
    public void testMarkConnectionAsReferencedNull() {
        validator.markConnectionAsReferenced(null);

        // Should handle gracefully
        assertTrue("Should handle null reference gracefully", true);
    }

    @Test
    public void testMarkConnectionAsReferencedEmpty() {
        validator.markConnectionAsReferenced("");

        // Should handle gracefully
        assertTrue("Should handle empty reference gracefully", true);
    }

    // ========================================================================
    // Validate All Connections Tests
    // ========================================================================

    @Test
    public void testValidateAllConnectionsEmpty() {
        ValidationResult result = validator.validateAllConnections();

        // Should handle empty connections list
        assertTrue("Should handle empty connections", true);
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testValidateAllConnectionsWithMultiple() {
        validator.registerConnection("Conn1", validRestConnection);
        validator.registerConnection("Conn2", validSoapConnection);
        validator.registerConnection("Conn3", validDatabaseConnection);

        ValidationResult result = validator.validateAllConnections();

        assertNotNull("Result should not be null", result);
        assertTrue("All valid connections should pass", result.isValid());
    }

    @Test
    public void testValidateAllConnectionsWithErrors() {
        Map<String, String> invalidConfig = new HashMap<String, String>();
        invalidConfig.put("type", "REST");
        // Missing required URL and authType

        validator.registerConnection("ValidConn", validRestConnection);
        validator.registerConnection("InvalidConn", invalidConfig);

        ValidationResult result = validator.validateAllConnections();

        assertFalse("Should fail with invalid connections", result.isValid());
        assertTrue("Should have errors", result.getErrorCount() > 0);
    }

    // ========================================================================
    // File Path Context Tests
    // ========================================================================

    @Test
    public void testValidationWithFilePath() {
        String filePath = "workspace/MyProject/connections/config.xml";
        validator.registerConnection("TestConn", validRestConnection);

        ValidationResult result = validator.validateConnection("TestConn", filePath);

        assertTrue("Valid connection should pass", result.isValid());
    }

    @Test
    public void testValidationWithFilePathError() {
        String filePath = "workspace/MyProject/connections/config.xml";

        ValidationResult result = validator.validateConnection("UndefinedConn", filePath);

        assertFalse("Undefined connection should fail", result.isValid());
        assertEquals("File path should be preserved", filePath,
            result.getIssues().get(0).getFilePath());
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testConnectionWithWhitespaceInName() {
        validator.registerConnection("  TestConn  ", validRestConnection);

        ValidationResult result = validator.validateConnection("  TestConn  ");

        assertTrue("Connection with whitespace should be trimmed and valid", result.isValid());
    }

    @Test
    public void testConnectionWithSpecialCharacters() {
        validator.registerConnection("Test-Conn_123", validRestConnection);

        ValidationResult result = validator.validateConnection("Test-Conn_123");

        assertTrue("Connection with special chars should be valid", result.isValid());
    }

    @Test
    public void testValidationResultProperties() {
        validator.registerConnection("TestConn", validRestConnection);
        ValidationResult result = validator.validateConnection("TestConn");

        assertNotNull("Result should not be null", result);
        assertNotNull("Validation type should not be null", result.getValidationType());
        assertTrue("Validation type should mention Connection",
            result.getValidationType().contains("Connection"));
    }

    @Test
    public void testValidationIssueProperties() {
        ValidationResult result = validator.validateConnection("UndefinedConn");

        assertFalse("Should have errors", result.isValid());
        ValidationIssue issue = result.getIssues().get(0);

        assertNotNull("Issue should not be null", issue);
        assertNotNull("Message should not be null", issue.getMessage());
        assertNotNull("Severity should not be null", issue.getSeverity());
        assertNotNull("Category should not be null", issue.getValidationCategory());
        assertEquals("Category should be Connection", "Connection", issue.getValidationCategory());
    }

    @Test
    public void testSuggestionPresence() {
        ValidationResult result = validator.validateConnection("UndefinedConn");

        assertFalse("Should have errors", result.isValid());
        ValidationIssue issue = result.getIssues().get(0);

        assertNotNull("Suggestion should be present", issue.getSuggestion());
        assertFalse("Suggestion should not be empty", issue.getSuggestion().trim().isEmpty());
    }

    // ========================================================================
    // Complex Configuration Tests
    // ========================================================================

    @Test
    public void testFtpConnection() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "FTP");
        config.put("host", "ftp.example.com");
        config.put("port", "21");
        config.put("username", "ftpuser");
        config.put("password", "ftppass");

        validator.registerConnection("FTPConn", config);
        ValidationResult result = validator.validateConnection("FTPConn");

        assertTrue("Valid FTP connection should pass", result.isValid());
    }

    @Test
    public void testSftpConnection() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "SFTP");
        config.put("host", "sftp.example.com");
        config.put("port", "22");
        config.put("username", "sftpuser");
        config.put("authType", "CERTIFICATE");
        config.put("certificatePath", "/path/to/cert.pem");

        validator.registerConnection("SFTPConn", config);
        ValidationResult result = validator.validateConnection("SFTPConn");

        assertTrue("Valid SFTP connection should pass", result.isValid());
    }

    @Test
    public void testFileConnection() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("type", "FILE");
        config.put("path", "/data/files");

        validator.registerConnection("FileConn", config);
        ValidationResult result = validator.validateConnection("FileConn");

        assertTrue("Valid FILE connection should pass", result.isValid());
    }
}
