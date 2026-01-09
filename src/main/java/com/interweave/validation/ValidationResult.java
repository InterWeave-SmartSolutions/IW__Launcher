package com.interweave.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ValidationResult - Aggregates multiple validation issues from flow validation.
 *
 * This class collects all validation issues found during design-time validation
 * and provides convenient methods to check validation status, filter by severity,
 * and format results for display or API responses.
 *
 * A validation passes if there are no ERROR-level issues. Warnings and info
 * messages do not prevent deployment but should be reviewed.
 *
 * Example usage:
 * <pre>
 * ValidationResult result = ValidationResult.builder()
 *     .validationType("Flow Configuration")
 *     .addIssue(ValidationIssue.builder()
 *         .severity(ValidationSeverity.ERROR)
 *         .message("Missing required connection")
 *         .filePath("transactions/sync-flow.xml")
 *         .lineNumber(42)
 *         .suggestion("Add connection reference in &lt;source&gt; element")
 *         .build())
 *     .build();
 *
 * if (!result.isValid()) {
 *     System.out.println("Validation failed with " + result.getErrorCount() + " errors");
 * }
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class ValidationResult {

    private final String validationType;
    private final List<ValidationIssue> issues;
    private final long validationTimestamp;

    /**
     * Private constructor - use the builder to create instances
     *
     * @param builder The builder instance containing validation result details
     */
    private ValidationResult(Builder builder) {
        this.validationType = builder.validationType;
        this.issues = Collections.unmodifiableList(new ArrayList<>(builder.issues));
        this.validationTimestamp = builder.validationTimestamp;
    }

    /**
     * Gets the type of validation performed
     *
     * @return The validation type description (e.g., "Flow Configuration", "XPath Validation")
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Gets all validation issues
     *
     * @return Unmodifiable list of all validation issues
     */
    public List<ValidationIssue> getIssues() {
        return issues;
    }

    /**
     * Gets the timestamp when validation was performed
     *
     * @return Timestamp in milliseconds since epoch
     */
    public long getValidationTimestamp() {
        return validationTimestamp;
    }

    /**
     * Checks if the validation passed (no ERROR-level issues)
     *
     * @return true if validation passed (no errors), false otherwise
     */
    public boolean isValid() {
        return getErrorCount() == 0;
    }

    /**
     * Gets the total number of issues
     *
     * @return Total count of all issues
     */
    public int getIssueCount() {
        return issues.size();
    }

    /**
     * Gets the number of ERROR-level issues
     *
     * @return Count of errors
     */
    public int getErrorCount() {
        return (int) issues.stream()
                .filter(ValidationIssue::isError)
                .count();
    }

    /**
     * Gets the number of WARNING-level issues
     *
     * @return Count of warnings
     */
    public int getWarningCount() {
        return (int) issues.stream()
                .filter(ValidationIssue::isWarning)
                .count();
    }

    /**
     * Gets the number of INFO-level issues
     *
     * @return Count of info messages
     */
    public int getInfoCount() {
        return (int) issues.stream()
                .filter(ValidationIssue::isInfo)
                .count();
    }

    /**
     * Gets all ERROR-level issues
     *
     * @return List of error issues
     */
    public List<ValidationIssue> getErrors() {
        return issues.stream()
                .filter(ValidationIssue::isError)
                .collect(Collectors.toList());
    }

    /**
     * Gets all WARNING-level issues
     *
     * @return List of warning issues
     */
    public List<ValidationIssue> getWarnings() {
        return issues.stream()
                .filter(ValidationIssue::isWarning)
                .collect(Collectors.toList());
    }

    /**
     * Gets all INFO-level issues
     *
     * @return List of info issues
     */
    public List<ValidationIssue> getInfos() {
        return issues.stream()
                .filter(ValidationIssue::isInfo)
                .collect(Collectors.toList());
    }

    /**
     * Gets issues filtered by validation category
     *
     * @param category The category to filter by (e.g., "XPath", "Connection")
     * @return List of issues matching the category
     */
    public List<ValidationIssue> getIssuesByCategory(String category) {
        if (category == null) {
            return Collections.emptyList();
        }
        return issues.stream()
                .filter(issue -> category.equals(issue.getValidationCategory()))
                .collect(Collectors.toList());
    }

    /**
     * Converts the validation result to a JSON-formatted string
     *
     * @return JSON representation of the validation result
     */
    public String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        json.append("\"validationType\":\"").append(escapeJson(validationType)).append("\",");
        json.append("\"timestamp\":").append(validationTimestamp).append(",");
        json.append("\"isValid\":").append(isValid()).append(",");
        json.append("\"totalIssues\":").append(getIssueCount()).append(",");
        json.append("\"errorCount\":").append(getErrorCount()).append(",");
        json.append("\"warningCount\":").append(getWarningCount()).append(",");
        json.append("\"infoCount\":").append(getInfoCount()).append(",");

        json.append("\"issues\":[");
        for (int i = 0; i < issues.size(); i++) {
            json.append(issues.get(i).toJSON());
            if (i < issues.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        json.append("}");
        return json.toString();
    }

    /**
     * Converts the validation result to a formatted string for display
     *
     * @return Formatted multi-line string with validation summary and all issues
     */
    public String toDisplayString() {
        StringBuilder display = new StringBuilder();

        display.append("Validation Result: ").append(validationType).append("\n");
        display.append("Status: ").append(isValid() ? "PASSED" : "FAILED").append("\n");
        display.append("Summary: ");
        display.append(getErrorCount()).append(" error(s), ");
        display.append(getWarningCount()).append(" warning(s), ");
        display.append(getInfoCount()).append(" info message(s)").append("\n");

        if (!issues.isEmpty()) {
            display.append("\nIssues:\n");
            for (ValidationIssue issue : issues) {
                display.append("  ").append(issue.toString()).append("\n");
            }
        }

        return display.toString();
    }

    /**
     * Returns a summary string representation of this validation result
     *
     * @return String in format "ValidationResult: [type] - [status] ([issue counts])"
     */
    @Override
    public String toString() {
        return String.format("ValidationResult: %s - %s (%d error(s), %d warning(s), %d info)",
                validationType,
                isValid() ? "PASSED" : "FAILED",
                getErrorCount(),
                getWarningCount(),
                getInfoCount());
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
     * Creates a new Builder instance for constructing ValidationResult objects
     *
     * @return A new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing ValidationResult instances
     */
    public static class Builder {
        private String validationType = "Validation";
        private List<ValidationIssue> issues = new ArrayList<>();
        private long validationTimestamp = System.currentTimeMillis();

        /**
         * Sets the validation type description
         *
         * @param validationType The type of validation performed
         * @return This builder instance for method chaining
         */
        public Builder validationType(String validationType) {
            this.validationType = validationType;
            return this;
        }

        /**
         * Adds a single validation issue
         *
         * @param issue The validation issue to add
         * @return This builder instance for method chaining
         */
        public Builder addIssue(ValidationIssue issue) {
            if (issue != null) {
                this.issues.add(issue);
            }
            return this;
        }

        /**
         * Adds multiple validation issues
         *
         * @param issues The validation issues to add
         * @return This builder instance for method chaining
         */
        public Builder addIssues(List<ValidationIssue> issues) {
            if (issues != null) {
                this.issues.addAll(issues);
            }
            return this;
        }

        /**
         * Sets all validation issues (replaces existing)
         *
         * @param issues The validation issues
         * @return This builder instance for method chaining
         */
        public Builder issues(List<ValidationIssue> issues) {
            this.issues = new ArrayList<>(issues != null ? issues : Collections.emptyList());
            return this;
        }

        /**
         * Sets the validation timestamp
         *
         * @param validationTimestamp Timestamp in milliseconds since epoch
         * @return This builder instance for method chaining
         */
        public Builder validationTimestamp(long validationTimestamp) {
            this.validationTimestamp = validationTimestamp;
            return this;
        }

        /**
         * Builds and returns the ValidationResult instance
         *
         * @return The constructed ValidationResult object
         */
        public ValidationResult build() {
            return new ValidationResult(this);
        }
    }
}
