package com.interweave.error;

/**
 * ErrorCategory - Categorizes error codes for better organization and filtering.
 *
 * Each category represents a major subsystem or functional area within the
 * InterWeave IDE platform that can produce errors.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public enum ErrorCategory {

    /**
     * Authentication and authorization errors
     * Examples: login failures, session expiration, access denied
     */
    AUTH("Authentication/Authorization", "AUTH"),

    /**
     * Database connection and query errors
     * Examples: connection failures, SQL errors, data integrity issues
     */
    DB("Database", "DB"),

    /**
     * Integration flow execution errors
     * Examples: flow configuration issues, execution failures, runtime errors
     */
    FLOW("Integration Flow", "FLOW"),

    /**
     * Configuration validation and loading errors
     * Examples: invalid XML, missing required fields, malformed config
     */
    CONFIG("Configuration", "CONFIG"),

    /**
     * Data validation errors
     * Examples: invalid input, missing required fields, format errors
     */
    VALIDATION("Validation", "VALIDATION"),

    /**
     * XPath expression and XSLT transformation errors
     * Examples: syntax errors, invalid expressions, namespace issues
     */
    XPATH("XPath/XSLT", "XPATH"),

    /**
     * External system connection errors
     * Examples: API connection failures, timeout, network errors
     */
    CONNECTION("Connection", "CONNECTION"),

    /**
     * General system and runtime errors
     * Examples: uncaught exceptions, internal server errors, unexpected failures
     */
    SYSTEM("System", "SYSTEM");

    private final String displayName;
    private final String code;

    /**
     * Creates an error category with display name and code prefix
     *
     * @param displayName Human-readable category name
     * @param code Short code prefix for error codes (e.g., "AUTH", "DB")
     */
    ErrorCategory(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    /**
     * Gets the human-readable display name for this category
     *
     * @return Display name (e.g., "Authentication/Authorization")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the short code prefix for this category
     *
     * @return Code prefix (e.g., "AUTH", "DB")
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns a string representation of this category
     *
     * @return String in format "CODE: Display Name"
     */
    @Override
    public String toString() {
        return code + ": " + displayName;
    }
}
