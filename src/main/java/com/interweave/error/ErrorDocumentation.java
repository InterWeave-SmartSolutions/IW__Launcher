package com.interweave.error;

import java.util.HashMap;
import java.util.Map;

/**
 * ErrorDocumentation - Registry mapping error codes to documentation URLs and resolution steps.
 *
 * This class provides a centralized registry for error documentation, including:
 * - Documentation URLs for each error code
 * - Detailed resolution steps
 * - Common causes and troubleshooting tips
 *
 * The documentation is embedded within the application for offline access,
 * with optional external links to comprehensive online documentation.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class ErrorDocumentation {

    private static final String BASE_DOCS_URL = "http://localhost:8080/iw-business-daemon/help/errors/";

    /**
     * Documentation entry containing resolution steps and documentation link
     */
    public static class DocumentationEntry {
        private final String resolutionSteps;
        private final String documentationUrl;

        public DocumentationEntry(String resolutionSteps, String documentationUrl) {
            this.resolutionSteps = resolutionSteps;
            this.documentationUrl = documentationUrl;
        }

        public String getResolutionSteps() {
            return resolutionSteps;
        }

        public String getDocumentationUrl() {
            return documentationUrl;
        }
    }

    private static final Map<ErrorCode, DocumentationEntry> documentationRegistry = new HashMap<>();

    static {
        initializeAuthenticationErrors();
        initializeDatabaseErrors();
        initializeFlowErrors();
        initializeConfigErrors();
        initializeValidationErrors();
        initializeXPathErrors();
        initializeConnectionErrors();
    }

    /**
     * Initialize documentation for authentication/authorization errors
     */
    private static void initializeAuthenticationErrors() {
        documentationRegistry.put(ErrorCode.AUTH001, new DocumentationEntry(
            "1. Verify email address is correct\n" +
            "2. Check password for typos (password is case-sensitive)\n" +
            "3. If forgotten, contact your administrator to reset password\n" +
            "4. Ensure account has been activated",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH001"
        ));

        documentationRegistry.put(ErrorCode.AUTH002, new DocumentationEntry(
            "1. Contact your company administrator to activate your account\n" +
            "2. Check if account activation email was received\n" +
            "3. Verify your user status in the company user management portal",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH002"
        ));

        documentationRegistry.put(ErrorCode.AUTH003, new DocumentationEntry(
            "1. Contact InterWeave support to verify company account status\n" +
            "2. Check if company license has expired\n" +
            "3. Verify payment and subscription status",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH003"
        ));

        documentationRegistry.put(ErrorCode.AUTH004, new DocumentationEntry(
            "1. Log in again to create a new session\n" +
            "2. Ensure browser cookies are enabled\n" +
            "3. Check session timeout settings in company configuration",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH004"
        ));

        documentationRegistry.put(ErrorCode.AUTH005, new DocumentationEntry(
            "1. Contact your administrator to request necessary permissions\n" +
            "2. Verify you are accessing the correct resource\n" +
            "3. Check your user role and permission assignments",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH005"
        ));

        documentationRegistry.put(ErrorCode.AUTH006, new DocumentationEntry(
            "1. Ensure you are logged in before accessing this resource\n" +
            "2. Include authentication token in API requests\n" +
            "3. Check that session has not expired",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH006"
        ));

        documentationRegistry.put(ErrorCode.AUTH007, new DocumentationEntry(
            "1. Log in again to obtain a new authentication token\n" +
            "2. Verify token format and encoding\n" +
            "3. Check system time synchronization (tokens may be time-based)",
            BASE_DOCS_URL + "auth-errors.jsp#AUTH007"
        ));
    }

    /**
     * Initialize documentation for database errors
     */
    private static void initializeDatabaseErrors() {
        documentationRegistry.put(ErrorCode.DB001, new DocumentationEntry(
            "1. Verify database server is running and accessible\n" +
            "2. Check network connectivity to database host\n" +
            "3. Verify database credentials in configuration\n" +
            "4. Check firewall rules allow database connections\n" +
            "5. Review database connection settings in .env file",
            BASE_DOCS_URL + "database-errors.jsp#DB001"
        ));

        documentationRegistry.put(ErrorCode.DB002, new DocumentationEntry(
            "1. Review the SQL query for syntax errors\n" +
            "2. Verify table and column names exist in schema\n" +
            "3. Check database user permissions\n" +
            "4. Review full error message and stack trace in logs\n" +
            "5. Verify data types match schema definitions",
            BASE_DOCS_URL + "database-errors.jsp#DB002"
        ));

        documentationRegistry.put(ErrorCode.DB003, new DocumentationEntry(
            "1. Check for duplicate key violations (unique constraint)\n" +
            "2. Verify foreign key relationships are valid\n" +
            "3. Review constraint definitions in database schema\n" +
            "4. Ensure required fields are populated\n" +
            "5. Check data integrity rules",
            BASE_DOCS_URL + "database-errors.jsp#DB003"
        ));

        documentationRegistry.put(ErrorCode.DB004, new DocumentationEntry(
            "1. Increase database connection pool size\n" +
            "2. Check for connection leaks (unclosed connections)\n" +
            "3. Review application connection usage patterns\n" +
            "4. Monitor database server load\n" +
            "5. Consider scaling database resources",
            BASE_DOCS_URL + "database-errors.jsp#DB004"
        ));

        documentationRegistry.put(ErrorCode.DB005, new DocumentationEntry(
            "1. Review transaction logs for error details\n" +
            "2. Check data consistency requirements\n" +
            "3. Verify business logic constraints\n" +
            "4. Consider retry logic for transient failures\n" +
            "5. Review transaction isolation levels",
            BASE_DOCS_URL + "database-errors.jsp#DB005"
        ));

        documentationRegistry.put(ErrorCode.DB006, new DocumentationEntry(
            "1. Verify database schema is up to date\n" +
            "2. Run database migrations if pending\n" +
            "3. Check table and column names for typos\n" +
            "4. Review schema documentation\n" +
            "5. Ensure application version matches database schema version",
            BASE_DOCS_URL + "database-errors.jsp#DB006"
        ));

        documentationRegistry.put(ErrorCode.DB007, new DocumentationEntry(
            "1. Optimize slow database queries\n" +
            "2. Increase query timeout settings if appropriate\n" +
            "3. Check database server performance and load\n" +
            "4. Review query execution plans\n" +
            "5. Consider adding database indexes",
            BASE_DOCS_URL + "database-errors.jsp#DB007"
        ));
    }

    /**
     * Initialize documentation for integration flow errors
     */
    private static void initializeFlowErrors() {
        documentationRegistry.put(ErrorCode.FLOW001, new DocumentationEntry(
            "1. Verify flow configuration file exists in workspace project\n" +
            "2. Check file path and name for typos\n" +
            "3. Ensure project is properly loaded in IDE\n" +
            "4. Review workspace directory structure\n" +
            "5. Check file permissions",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW001"
        ));

        documentationRegistry.put(ErrorCode.FLOW002, new DocumentationEntry(
            "1. Review flow execution logs for detailed error\n" +
            "2. Check all required connections are configured\n" +
            "3. Verify transformation mappings are correct\n" +
            "4. Test individual flow components\n" +
            "5. Check input data format and validity",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW002"
        ));

        documentationRegistry.put(ErrorCode.FLOW003, new DocumentationEntry(
            "1. Review flow configuration for missing components\n" +
            "2. Check that all referenced components are defined\n" +
            "3. Verify component names match exactly (case-sensitive)\n" +
            "4. Ensure required plugins are installed\n" +
            "5. Review flow dependencies",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW003"
        ));

        documentationRegistry.put(ErrorCode.FLOW004, new DocumentationEntry(
            "1. Review flow diagram for circular dependencies\n" +
            "2. Restructure flow to eliminate circular references\n" +
            "3. Check transformation chains for loops\n" +
            "4. Use validation service to detect circular references\n" +
            "5. Consult flow design best practices documentation",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW004"
        ));

        documentationRegistry.put(ErrorCode.FLOW005, new DocumentationEntry(
            "1. Review allowed state transitions in flow documentation\n" +
            "2. Check current flow state\n" +
            "3. Verify transition prerequisites are met\n" +
            "4. Review flow state machine configuration\n" +
            "5. Check for concurrent state modifications",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW005"
        ));

        documentationRegistry.put(ErrorCode.FLOW006, new DocumentationEntry(
            "1. Review XSLT transformation file for errors\n" +
            "2. Verify input XML structure matches expected format\n" +
            "3. Check XPath expressions in transformation\n" +
            "4. Test transformation with sample data\n" +
            "5. Review transformation logs for detailed error",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW006"
        ));

        documentationRegistry.put(ErrorCode.FLOW007, new DocumentationEntry(
            "1. Check that referenced connection is defined in project\n" +
            "2. Verify connection name matches exactly (case-sensitive)\n" +
            "3. Review connection configuration\n" +
            "4. Use validation service to detect missing connections\n" +
            "5. Check connection is not disabled or archived",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW007"
        ));

        documentationRegistry.put(ErrorCode.FLOW008, new DocumentationEntry(
            "1. Increase transaction timeout setting if appropriate\n" +
            "2. Optimize flow performance\n" +
            "3. Check external system response times\n" +
            "4. Review flow complexity and processing time\n" +
            "5. Consider breaking into smaller transactions",
            BASE_DOCS_URL + "flow-errors.jsp#FLOW008"
        ));
    }

    /**
     * Initialize documentation for configuration errors
     */
    private static void initializeConfigErrors() {
        documentationRegistry.put(ErrorCode.CONFIG001, new DocumentationEntry(
            "1. Validate XML syntax using XML editor or validator\n" +
            "2. Check for unclosed tags or missing brackets\n" +
            "3. Verify XML special characters are properly escaped\n" +
            "4. Ensure XML declaration is present and correct\n" +
            "5. Review error line number for specific syntax issue",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG001"
        ));

        documentationRegistry.put(ErrorCode.CONFIG002, new DocumentationEntry(
            "1. Review schema documentation for required elements\n" +
            "2. Add missing configuration elements\n" +
            "3. Check element names for typos\n" +
            "4. Verify configuration file structure\n" +
            "5. Use configuration template as reference",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG002"
        ));

        documentationRegistry.put(ErrorCode.CONFIG003, new DocumentationEntry(
            "1. Review allowed values for configuration element\n" +
            "2. Check data type (string, number, boolean, etc.)\n" +
            "3. Verify value format (date, URL, etc.)\n" +
            "4. Check for valid enumeration values\n" +
            "5. Consult configuration documentation",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG003"
        ));

        documentationRegistry.put(ErrorCode.CONFIG004, new DocumentationEntry(
            "1. Verify configuration file exists at expected location\n" +
            "2. Check file path and name\n" +
            "3. Verify file permissions allow reading\n" +
            "4. Check file encoding (should be UTF-8)\n" +
            "5. Review file system access logs",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG004"
        ));

        documentationRegistry.put(ErrorCode.CONFIG005, new DocumentationEntry(
            "1. Review validation errors in detail\n" +
            "2. Use schema validator to identify specific issues\n" +
            "3. Check configuration against schema definition\n" +
            "4. Verify all required fields are present\n" +
            "5. Test configuration in validation mode",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG005"
        ));

        documentationRegistry.put(ErrorCode.CONFIG006, new DocumentationEntry(
            "1. Verify file path format is correct\n" +
            "2. Use absolute or relative paths consistently\n" +
            "3. Check path separators (forward vs backward slash)\n" +
            "4. Verify directory structure exists\n" +
            "5. Check for special characters in path",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG006"
        ));

        documentationRegistry.put(ErrorCode.CONFIG007, new DocumentationEntry(
            "1. Remove duplicate configuration element\n" +
            "2. Merge duplicate definitions if both needed\n" +
            "3. Check configuration merge logic\n" +
            "4. Verify element uniqueness constraints\n" +
            "5. Review configuration file for copy-paste errors",
            BASE_DOCS_URL + "config-errors.jsp#CONFIG007"
        ));
    }

    /**
     * Initialize documentation for validation errors
     */
    private static void initializeValidationErrors() {
        documentationRegistry.put(ErrorCode.VALIDATION001, new DocumentationEntry(
            "1. Provide value for required field\n" +
            "2. Check field name in documentation\n" +
            "3. Verify field is not hidden or disabled\n" +
            "4. Review form validation rules\n" +
            "5. Check for client-side validation errors",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION001"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION002, new DocumentationEntry(
            "1. Check expected format in documentation\n" +
            "2. Review format examples\n" +
            "3. Verify special characters and encoding\n" +
            "4. Check data type (string, number, date, etc.)\n" +
            "5. Use format validation tools",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION002"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION003, new DocumentationEntry(
            "1. Check minimum and maximum allowed values\n" +
            "2. Review range constraints in documentation\n" +
            "3. Verify value is within business rules\n" +
            "4. Check for numeric overflow\n" +
            "5. Ensure value makes sense in context",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION003"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION004, new DocumentationEntry(
            "1. Verify email format: user@domain.com\n" +
            "2. Check for typos in email address\n" +
            "3. Ensure @ symbol is present\n" +
            "4. Verify domain has valid TLD\n" +
            "5. Remove extra spaces or special characters",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION004"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION005, new DocumentationEntry(
            "1. Verify URL format: http://domain.com or https://domain.com\n" +
            "2. Include protocol (http:// or https://)\n" +
            "3. Check for typos in URL\n" +
            "4. Verify domain name is valid\n" +
            "5. Test URL in browser",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION005"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION006, new DocumentationEntry(
            "1. Check expected date/time format in documentation\n" +
            "2. Use ISO-8601 format: YYYY-MM-DD or YYYY-MM-DDTHH:mm:ss\n" +
            "3. Verify date is valid (e.g., not February 30)\n" +
            "4. Check timezone handling\n" +
            "5. Use date picker if available",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION006"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION007, new DocumentationEntry(
            "1. Check maximum length constraint\n" +
            "2. Shorten field value\n" +
            "3. Review length requirements in documentation\n" +
            "4. Check for accidental extra content\n" +
            "5. Use text truncation if appropriate",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION007"
        ));

        documentationRegistry.put(ErrorCode.VALIDATION008, new DocumentationEntry(
            "1. Review required pattern in documentation\n" +
            "2. Check pattern examples\n" +
            "3. Verify value matches regex pattern\n" +
            "4. Look for typos or format errors\n" +
            "5. Use pattern validation tools",
            BASE_DOCS_URL + "validation-errors.jsp#VALIDATION008"
        ));
    }

    /**
     * Initialize documentation for XPath/XSLT errors
     */
    private static void initializeXPathErrors() {
        documentationRegistry.put(ErrorCode.XPATH001, new DocumentationEntry(
            "1. Validate XPath syntax using XPath evaluator\n" +
            "2. Check for unbalanced parentheses or brackets\n" +
            "3. Verify function names are correct\n" +
            "4. Check operator usage (/, //, ., @, etc.)\n" +
            "5. Review XPath syntax documentation",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH001"
        ));

        documentationRegistry.put(ErrorCode.XPATH002, new DocumentationEntry(
            "1. Define namespace prefix in XSLT file\n" +
            "2. Check namespace declarations at top of file\n" +
            "3. Verify namespace URI matches XML document\n" +
            "4. Use correct prefix in XPath expression\n" +
            "5. Review namespace handling documentation",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH002"
        ));

        documentationRegistry.put(ErrorCode.XPATH003, new DocumentationEntry(
            "1. Verify template name exists in XSLT file\n" +
            "2. Check template name spelling (case-sensitive)\n" +
            "3. Ensure template is not commented out\n" +
            "4. Review template definitions in XSLT\n" +
            "5. Check template import/include statements",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH003"
        ));

        documentationRegistry.put(ErrorCode.XPATH004, new DocumentationEntry(
            "1. Review XSLT file for syntax errors\n" +
            "2. Check input XML structure matches expected format\n" +
            "3. Verify XPath expressions in transformation\n" +
            "4. Test with sample input data\n" +
            "5. Review transformation logs for detailed error",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH004"
        ));

        documentationRegistry.put(ErrorCode.XPATH005, new DocumentationEntry(
            "1. Verify XPath expression is correct\n" +
            "2. Check input XML contains expected elements\n" +
            "3. Review XPath context node\n" +
            "4. Test XPath with sample XML\n" +
            "5. Consider using conditional logic for optional elements",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH005"
        ));

        documentationRegistry.put(ErrorCode.XPATH006, new DocumentationEntry(
            "1. Verify function name is correct\n" +
            "2. Check number and types of function arguments\n" +
            "3. Review XPath function documentation\n" +
            "4. Verify function is supported in XPath version\n" +
            "5. Check for typos in function name",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH006"
        ));

        documentationRegistry.put(ErrorCode.XPATH007, new DocumentationEntry(
            "1. Validate XSLT file syntax\n" +
            "2. Check XSLT namespace declaration\n" +
            "3. Verify well-formed XML structure\n" +
            "4. Review XSLT elements and attributes\n" +
            "5. Use XSLT validator tool",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH007"
        ));

        documentationRegistry.put(ErrorCode.XPATH008, new DocumentationEntry(
            "1. Define XPath variable before use\n" +
            "2. Check variable name spelling (case-sensitive)\n" +
            "3. Verify variable scope\n" +
            "4. Review variable declarations in XSLT\n" +
            "5. Check variable is not defined in unreachable scope",
            BASE_DOCS_URL + "xpath-errors.jsp#XPATH008"
        ));
    }

    /**
     * Initialize documentation for connection errors
     */
    private static void initializeConnectionErrors() {
        documentationRegistry.put(ErrorCode.CONNECTION001, new DocumentationEntry(
            "1. Verify external system is online and accessible\n" +
            "2. Check network connectivity\n" +
            "3. Verify connection URL/endpoint is correct\n" +
            "4. Test connection with ping or curl\n" +
            "5. Check firewall and proxy settings",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION001"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION002, new DocumentationEntry(
            "1. Check external system response time\n" +
            "2. Increase timeout setting if appropriate\n" +
            "3. Verify network latency is acceptable\n" +
            "4. Check for network congestion\n" +
            "5. Contact external system administrator",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION002"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION003, new DocumentationEntry(
            "1. Review connection configuration settings\n" +
            "2. Verify all required parameters are set\n" +
            "3. Check parameter values and formats\n" +
            "4. Consult connection setup documentation\n" +
            "5. Use connection configuration template",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION003"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION004, new DocumentationEntry(
            "1. Check which parameters are required\n" +
            "2. Add missing connection parameter\n" +
            "3. Verify parameter name is correct\n" +
            "4. Review connection type documentation\n" +
            "5. Check parameter is not hidden or conditional",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION004"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION005, new DocumentationEntry(
            "1. Check network connectivity\n" +
            "2. Verify DNS resolution\n" +
            "3. Check for network outages\n" +
            "4. Review network logs\n" +
            "5. Contact network administrator",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION005"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION006, new DocumentationEntry(
            "1. Verify SSL/TLS certificate is valid\n" +
            "2. Check certificate expiration date\n" +
            "3. Install or update trusted certificates\n" +
            "4. Verify certificate chain\n" +
            "5. Contact external system administrator for certificate info",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION006"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION007, new DocumentationEntry(
            "1. Verify authentication credentials are correct\n" +
            "2. Check username/password or API key\n" +
            "3. Verify authentication method matches external system\n" +
            "4. Check if credentials have expired\n" +
            "5. Review external system authentication documentation",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION007"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION008, new DocumentationEntry(
            "1. Verify API endpoint URL is correct\n" +
            "2. Check for typos in endpoint path\n" +
            "3. Review API documentation for correct endpoints\n" +
            "4. Verify API version in URL if required\n" +
            "5. Check external system API changes or deprecations",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION008"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION009, new DocumentationEntry(
            "1. Review error response from external system\n" +
            "2. Check external system status page\n" +
            "3. Verify request parameters and format\n" +
            "4. Review external system logs if available\n" +
            "5. Contact external system support",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION009"
        ));

        documentationRegistry.put(ErrorCode.CONNECTION010, new DocumentationEntry(
            "1. Reduce request frequency\n" +
            "2. Implement rate limiting in integration\n" +
            "3. Check rate limit details from external system\n" +
            "4. Consider batch processing requests\n" +
            "5. Contact external system to increase rate limit",
            BASE_DOCS_URL + "connection-errors.jsp#CONNECTION010"
        ));
    }

    /**
     * Gets the documentation entry for an error code
     *
     * @param errorCode The error code to look up
     * @return DocumentationEntry containing resolution steps and URL, or null if not found
     */
    public static DocumentationEntry getDocumentation(ErrorCode errorCode) {
        return documentationRegistry.get(errorCode);
    }

    /**
     * Gets resolution steps for an error code
     *
     * @param errorCode The error code to look up
     * @return Resolution steps as a string, or a default message if not found
     */
    public static String getResolutionSteps(ErrorCode errorCode) {
        DocumentationEntry entry = documentationRegistry.get(errorCode);
        if (entry != null) {
            return entry.getResolutionSteps();
        }
        return "No specific resolution steps available. Check logs for details and contact support if needed.";
    }

    /**
     * Gets documentation URL for an error code
     *
     * @param errorCode The error code to look up
     * @return Documentation URL, or null if not found
     */
    public static String getDocumentationUrl(ErrorCode errorCode) {
        DocumentationEntry entry = documentationRegistry.get(errorCode);
        return entry != null ? entry.getDocumentationUrl() : null;
    }

    /**
     * Checks if documentation exists for an error code
     *
     * @param errorCode The error code to check
     * @return true if documentation exists, false otherwise
     */
    public static boolean hasDocumentation(ErrorCode errorCode) {
        return documentationRegistry.containsKey(errorCode);
    }

    /**
     * Gets all documented error codes
     *
     * @return Array of error codes that have documentation
     */
    public static ErrorCode[] getDocumentedErrorCodes() {
        return documentationRegistry.keySet().toArray(new ErrorCode[0]);
    }
}
