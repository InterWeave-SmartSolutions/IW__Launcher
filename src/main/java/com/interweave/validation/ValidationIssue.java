package com.interweave.validation;

/**
 * ValidationIssue - Represents a single validation issue found during flow validation.
 *
 * This class captures all relevant information about a validation problem including:
 * - Severity level (ERROR, WARNING, INFO)
 * - Location (file path and line number)
 * - Descriptive message
 * - Suggested resolution
 *
 * Validation issues are collected during design-time validation to help developers
 * identify and fix problems before deployment.
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class ValidationIssue {

    private final ValidationSeverity severity;
    private final String message;
    private final String filePath;
    private final int lineNumber;
    private final String suggestion;
    private final String validationCategory;

    /**
     * Private constructor - use the builder to create instances
     *
     * @param builder The builder instance containing validation issue details
     */
    private ValidationIssue(Builder builder) {
        this.severity = builder.severity != null ? builder.severity : ValidationSeverity.ERROR;
        this.message = builder.message;
        this.filePath = builder.filePath;
        this.lineNumber = builder.lineNumber;
        this.suggestion = builder.suggestion;
        this.validationCategory = builder.validationCategory;
    }

    /**
     * Gets the severity level of this validation issue
     *
     * @return The severity (ERROR, WARNING, or INFO)
     */
    public ValidationSeverity getSeverity() {
        return severity;
    }

    /**
     * Gets the validation message
     *
     * @return The descriptive message explaining the issue
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the file path where the issue was found
     *
     * @return The file path, or null if not applicable
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the line number where the issue was found
     *
     * @return The line number, or -1 if not applicable
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the suggested resolution for this issue
     *
     * @return The suggestion, or null if not provided
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * Gets the validation category (e.g., "XPath", "Connection", "Configuration")
     *
     * @return The validation category, or null if not set
     */
    public String getValidationCategory() {
        return validationCategory;
    }

    /**
     * Checks if this is an error-level issue
     *
     * @return true if severity is ERROR
     */
    public boolean isError() {
        return severity == ValidationSeverity.ERROR;
    }

    /**
     * Checks if this is a warning-level issue
     *
     * @return true if severity is WARNING
     */
    public boolean isWarning() {
        return severity == ValidationSeverity.WARNING;
    }

    /**
     * Checks if this is an info-level issue
     *
     * @return true if severity is INFO
     */
    public boolean isInfo() {
        return severity == ValidationSeverity.INFO;
    }

    /**
     * Converts the validation issue to a JSON-formatted string
     *
     * @return JSON representation of the validation issue
     */
    public String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        json.append("\"severity\":\"").append(escapeJson(severity.name())).append("\",");
        json.append("\"message\":\"").append(escapeJson(message)).append("\"");

        if (filePath != null) {
            json.append(",\"filePath\":\"").append(escapeJson(filePath)).append("\"");
        }

        if (lineNumber >= 0) {
            json.append(",\"lineNumber\":").append(lineNumber);
        }

        if (suggestion != null) {
            json.append(",\"suggestion\":\"").append(escapeJson(suggestion)).append("\"");
        }

        if (validationCategory != null) {
            json.append(",\"category\":\"").append(escapeJson(validationCategory)).append("\"");
        }

        json.append("}");
        return json.toString();
    }

    /**
     * Returns a formatted string representation of this validation issue
     *
     * @return Formatted string with severity, location, and message
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(severity).append("]");

        if (filePath != null) {
            sb.append(" ").append(filePath);
            if (lineNumber >= 0) {
                sb.append(":").append(lineNumber);
            }
        }

        sb.append(" - ").append(message);

        if (suggestion != null) {
            sb.append(" (Suggestion: ").append(suggestion).append(")");
        }

        return sb.toString();
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
     * Creates a new Builder instance for constructing ValidationIssue objects
     *
     * @return A new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing ValidationIssue instances
     */
    public static class Builder {
        private ValidationSeverity severity;
        private String message;
        private String filePath;
        private int lineNumber = -1;
        private String suggestion;
        private String validationCategory;

        /**
         * Sets the severity level
         *
         * @param severity The severity level
         * @return This builder instance for method chaining
         */
        public Builder severity(ValidationSeverity severity) {
            this.severity = severity;
            return this;
        }

        /**
         * Sets the validation message
         *
         * @param message The descriptive message
         * @return This builder instance for method chaining
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the file path where the issue was found
         *
         * @param filePath The file path
         * @return This builder instance for method chaining
         */
        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        /**
         * Sets the line number where the issue was found
         *
         * @param lineNumber The line number (use -1 if not applicable)
         * @return This builder instance for method chaining
         */
        public Builder lineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        /**
         * Sets the suggested resolution
         *
         * @param suggestion The suggestion for fixing the issue
         * @return This builder instance for method chaining
         */
        public Builder suggestion(String suggestion) {
            this.suggestion = suggestion;
            return this;
        }

        /**
         * Sets the validation category
         *
         * @param validationCategory The category (e.g., "XPath", "Connection")
         * @return This builder instance for method chaining
         */
        public Builder validationCategory(String validationCategory) {
            this.validationCategory = validationCategory;
            return this;
        }

        /**
         * Builds and returns the ValidationIssue instance
         *
         * @return The constructed ValidationIssue object
         * @throws IllegalStateException if required fields are not set
         */
        public ValidationIssue build() {
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalStateException("Validation issue message is required");
            }
            return new ValidationIssue(this);
        }
    }
}
