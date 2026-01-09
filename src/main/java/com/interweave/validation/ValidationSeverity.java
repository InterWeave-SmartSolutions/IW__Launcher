package com.interweave.validation;

/**
 * ValidationSeverity - Defines the severity levels for validation issues.
 *
 * Severity levels help prioritize validation issues and determine appropriate
 * actions during design-time validation of integration flows.
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public enum ValidationSeverity {

    /**
     * Critical validation error that prevents flow deployment
     * Requires immediate attention and blocks execution until resolved
     */
    ERROR("Error"),

    /**
     * Warning condition that may cause issues but doesn't prevent deployment
     * Should be reviewed but doesn't block flow execution
     */
    WARNING("Warning"),

    /**
     * Informational message for best practices and suggestions
     * No action required but provides helpful guidance
     */
    INFO("Info");

    private final String displayName;

    /**
     * Creates a severity level with a display name
     *
     * @param displayName Human-readable severity name
     */
    ValidationSeverity(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the human-readable display name
     *
     * @return Display name (e.g., "Error", "Warning", "Info")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns a string representation of this severity level
     *
     * @return The display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}
