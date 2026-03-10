package com.interweave.error;

/**
 * ErrorSeverity - Defines the severity levels for error codes.
 *
 * Severity levels help prioritize error handling and determine appropriate
 * user notifications and logging behavior.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public enum ErrorSeverity {

    /**
     * Critical error that prevents operation completion
     * Requires immediate attention and prevents normal flow execution
     */
    ERROR("Error"),

    /**
     * Warning condition that may cause issues but doesn't prevent operation
     * Should be logged and monitored but doesn't block execution
     */
    WARNING("Warning"),

    /**
     * Informational message for logging and debugging purposes
     * No action required but provides useful context
     */
    INFO("Info");

    private final String displayName;

    /**
     * Creates a severity level with a display name
     *
     * @param displayName Human-readable severity name
     */
    ErrorSeverity(String displayName) {
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
        return name();
    }
}
