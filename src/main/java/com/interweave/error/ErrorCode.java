package com.interweave.error;

/**
 * ErrorCode - Comprehensive error code system for the InterWeave IDE platform.
 *
 * Each error code represents a specific error condition with:
 * - Unique identifier (e.g., AUTH001, DB002)
 * - Error category
 * - Severity level (ERROR, WARNING, INFO)
 * - Default error message
 *
 * Error codes are organized by category for easy reference and maintenance.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public enum ErrorCode {

    // ========================================================================
    // AUTHENTICATION/AUTHORIZATION ERRORS (AUTH001-AUTH099)
    // ========================================================================

    /**
     * Invalid credentials provided during login
     */
    AUTH001(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH001",
            "Invalid email or password"),

    /**
     * User account is inactive or disabled
     */
    AUTH002(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH002",
            "User account is inactive"),

    /**
     * Company account is inactive or suspended
     */
    AUTH003(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH003",
            "Company account is inactive"),

    /**
     * Session has expired
     */
    AUTH004(ErrorCategory.AUTH, ErrorSeverity.WARNING, "AUTH004",
            "Session has expired. Please log in again"),

    /**
     * User does not have permission to access the requested resource
     */
    AUTH005(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH005",
            "Access denied. Insufficient permissions"),

    /**
     * Missing authentication credentials
     */
    AUTH006(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH006",
            "Authentication credentials are required"),

    /**
     * Invalid or expired authentication token
     */
    AUTH007(ErrorCategory.AUTH, ErrorSeverity.ERROR, "AUTH007",
            "Invalid or expired authentication token"),

    // ========================================================================
    // DATABASE ERRORS (DB001-DB099)
    // ========================================================================

    /**
     * Cannot establish connection to database
     */
    DB001(ErrorCategory.DB, ErrorSeverity.ERROR, "DB001",
            "Cannot connect to database"),

    /**
     * SQL query execution failed
     */
    DB002(ErrorCategory.DB, ErrorSeverity.ERROR, "DB002",
            "Database query failed"),

    /**
     * Database constraint violation (e.g., duplicate key, foreign key)
     */
    DB003(ErrorCategory.DB, ErrorSeverity.ERROR, "DB003",
            "Database constraint violation"),

    /**
     * Database connection pool exhausted
     */
    DB004(ErrorCategory.DB, ErrorSeverity.ERROR, "DB004",
            "Database connection pool exhausted"),

    /**
     * Database transaction rollback occurred
     */
    DB005(ErrorCategory.DB, ErrorSeverity.WARNING, "DB005",
            "Database transaction was rolled back"),

    /**
     * Required database table or column not found
     */
    DB006(ErrorCategory.DB, ErrorSeverity.ERROR, "DB006",
            "Database schema mismatch - table or column not found"),

    /**
     * Database timeout occurred
     */
    DB007(ErrorCategory.DB, ErrorSeverity.ERROR, "DB007",
            "Database operation timed out"),

    // ========================================================================
    // INTEGRATION FLOW ERRORS (FLOW001-FLOW099)
    // ========================================================================

    /**
     * Integration flow configuration file not found
     */
    FLOW001(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW001",
            "Flow configuration file not found"),

    /**
     * Flow execution failed due to runtime error
     */
    FLOW002(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW002",
            "Flow execution failed"),

    /**
     * Missing required flow component
     */
    FLOW003(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW003",
            "Required flow component is missing"),

    /**
     * Flow contains circular references
     */
    FLOW004(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW004",
            "Flow contains circular references"),

    /**
     * Invalid flow state transition
     */
    FLOW005(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW005",
            "Invalid flow state transition"),

    /**
     * Flow transformation failed
     */
    FLOW006(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW006",
            "Data transformation failed in flow"),

    /**
     * Flow missing required connection
     */
    FLOW007(ErrorCategory.FLOW, ErrorSeverity.ERROR, "FLOW007",
            "Flow references undefined connection"),

    /**
     * Flow transaction timeout
     */
    FLOW008(ErrorCategory.FLOW, ErrorSeverity.WARNING, "FLOW008",
            "Flow transaction timed out"),

    // ========================================================================
    // CONFIGURATION ERRORS (CONFIG001-CONFIG099)
    // ========================================================================

    /**
     * Configuration file has invalid XML syntax
     */
    CONFIG001(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG001",
            "Invalid XML syntax in configuration file"),

    /**
     * Required configuration element is missing
     */
    CONFIG002(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG002",
            "Required configuration element is missing"),

    /**
     * Configuration element has invalid value
     */
    CONFIG003(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG003",
            "Configuration element has invalid value"),

    /**
     * Configuration file cannot be loaded
     */
    CONFIG004(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG004",
            "Cannot load configuration file"),

    /**
     * Configuration validation failed
     */
    CONFIG005(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG005",
            "Configuration validation failed"),

    /**
     * Configuration file path is invalid
     */
    CONFIG006(ErrorCategory.CONFIG, ErrorSeverity.ERROR, "CONFIG006",
            "Invalid configuration file path"),

    /**
     * Duplicate configuration element found
     */
    CONFIG007(ErrorCategory.CONFIG, ErrorSeverity.WARNING, "CONFIG007",
            "Duplicate configuration element found"),

    // ========================================================================
    // VALIDATION ERRORS (VALIDATION001-VALIDATION099)
    // ========================================================================

    /**
     * Required field is missing or empty
     */
    VALIDATION001(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION001",
            "Required field is missing"),

    /**
     * Field value format is invalid
     */
    VALIDATION002(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION002",
            "Invalid field format"),

    /**
     * Field value is out of allowed range
     */
    VALIDATION003(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION003",
            "Field value out of range"),

    /**
     * Email address format is invalid
     */
    VALIDATION004(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION004",
            "Invalid email address format"),

    /**
     * URL format is invalid
     */
    VALIDATION005(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION005",
            "Invalid URL format"),

    /**
     * Date/time format is invalid
     */
    VALIDATION006(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION006",
            "Invalid date/time format"),

    /**
     * Field length exceeds maximum allowed
     */
    VALIDATION007(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION007",
            "Field length exceeds maximum"),

    /**
     * Field value does not match required pattern
     */
    VALIDATION008(ErrorCategory.VALIDATION, ErrorSeverity.ERROR, "VALIDATION008",
            "Field value does not match required pattern"),

    // ========================================================================
    // XPATH/XSLT ERRORS (XPATH001-XPATH099)
    // ========================================================================

    /**
     * XPath expression has syntax error
     */
    XPATH001(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH001",
            "XPath expression syntax error"),

    /**
     * XPath namespace prefix is undefined
     */
    XPATH002(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH002",
            "Undefined namespace prefix in XPath expression"),

    /**
     * XSLT template not found
     */
    XPATH003(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH003",
            "XSLT template not found"),

    /**
     * XSLT transformation failed
     */
    XPATH004(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH004",
            "XSLT transformation failed"),

    /**
     * XPath evaluation returned no results
     */
    XPATH005(ErrorCategory.XPATH, ErrorSeverity.WARNING, "XPATH005",
            "XPath expression returned no results"),

    /**
     * Invalid XPath function call
     */
    XPATH006(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH006",
            "Invalid XPath function call"),

    /**
     * XSLT file has invalid syntax
     */
    XPATH007(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH007",
            "Invalid XSLT syntax"),

    /**
     * XPath variable is undefined
     */
    XPATH008(ErrorCategory.XPATH, ErrorSeverity.ERROR, "XPATH008",
            "Undefined XPath variable"),

    // ========================================================================
    // CONNECTION ERRORS (CONNECTION001-CONNECTION099)
    // ========================================================================

    /**
     * Cannot connect to external system
     */
    CONNECTION001(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION001",
            "Cannot connect to external system"),

    /**
     * Connection timeout occurred
     */
    CONNECTION002(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION002",
            "Connection timed out"),

    /**
     * Connection configuration is invalid
     */
    CONNECTION003(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION003",
            "Invalid connection configuration"),

    /**
     * Missing required connection parameter
     */
    CONNECTION004(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION004",
            "Required connection parameter is missing"),

    /**
     * Network error occurred
     */
    CONNECTION005(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION005",
            "Network error occurred"),

    /**
     * SSL/TLS certificate error
     */
    CONNECTION006(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION006",
            "SSL/TLS certificate error"),

    /**
     * Authentication failed with external system
     */
    CONNECTION007(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION007",
            "Authentication failed with external system"),

    /**
     * API endpoint not found (404)
     */
    CONNECTION008(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION008",
            "API endpoint not found"),

    /**
     * External system returned error response
     */
    CONNECTION009(ErrorCategory.CONNECTION, ErrorSeverity.ERROR, "CONNECTION009",
            "External system returned error"),

    /**
     * Request rate limit exceeded
     */
    CONNECTION010(ErrorCategory.CONNECTION, ErrorSeverity.WARNING, "CONNECTION010",
            "Request rate limit exceeded"),

    // ========================================================================
    // SYSTEM ERRORS (SYSTEM001-SYSTEM099)
    // ========================================================================

    /**
     * Internal server error - uncaught exception
     */
    SYSTEM001(ErrorCategory.SYSTEM, ErrorSeverity.ERROR, "SYSTEM001",
            "Internal server error occurred"),

    /**
     * Service temporarily unavailable
     */
    SYSTEM002(ErrorCategory.SYSTEM, ErrorSeverity.ERROR, "SYSTEM002",
            "Service temporarily unavailable"),

    /**
     * Resource not found (404)
     */
    SYSTEM003(ErrorCategory.SYSTEM, ErrorSeverity.ERROR, "SYSTEM003",
            "Requested resource not found"),

    /**
     * Request method not allowed
     */
    SYSTEM004(ErrorCategory.SYSTEM, ErrorSeverity.ERROR, "SYSTEM004",
            "HTTP method not allowed for this request"),

    /**
     * Request timeout
     */
    SYSTEM005(ErrorCategory.SYSTEM, ErrorSeverity.ERROR, "SYSTEM005",
            "Request processing timed out");

    private final ErrorCategory category;
    private final ErrorSeverity severity;
    private final String code;
    private final String defaultMessage;

    /**
     * Creates an error code with category, severity, code, and default message
     *
     * @param category The error category
     * @param severity The severity level
     * @param code The unique error code identifier
     * @param defaultMessage The default error message
     */
    ErrorCode(ErrorCategory category, ErrorSeverity severity, String code, String defaultMessage) {
        this.category = category;
        this.severity = severity;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Gets the error category
     *
     * @return The error category
     */
    public ErrorCategory getCategory() {
        return category;
    }

    /**
     * Gets the severity level
     *
     * @return The severity level
     */
    public ErrorSeverity getSeverity() {
        return severity;
    }

    /**
     * Gets the unique error code identifier
     *
     * @return The error code (e.g., "AUTH001")
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the default error message
     *
     * @return The default error message
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     * Returns a string representation of this error code
     *
     * @return String in format "[CODE] Message (SEVERITY)"
     */
    @Override
    public String toString() {
        return "[" + code + "] " + defaultMessage + " (" + severity + ")";
    }

    /**
     * Finds an error code by its code string
     *
     * @param code The error code string (e.g., "AUTH001")
     * @return The matching ErrorCode, or null if not found
     */
    public static ErrorCode findByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return null;
    }
}
