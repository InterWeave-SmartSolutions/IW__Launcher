package com.interweave.monitoring.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * MonitoringContextListener - Initializes and shuts down monitoring services.
 *
 * This listener starts the MetricsAggregator and AlertService when the web
 * application loads and gracefully shuts them down when the application unloads.
 * This ensures that background metrics aggregation tasks and alert thread pools
 * are properly managed throughout the application lifecycle.
 *
 * The listener is registered in web.xml and runs during application startup.
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class MonitoringContextListener implements ServletContextListener {

    /**
     * Called when the web application is initialized.
     * Starts the MetricsAggregator scheduler and initializes AlertService.
     *
     * @param event ServletContext initialization event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        log("Initializing monitoring services...");

        try {
            // Start metrics aggregation scheduler
            MetricsAggregator aggregator = MetricsAggregator.getInstance();
            aggregator.start();

            // Initialize alert service (creates thread pool)
            AlertService alertService = AlertService.getInstance();
            log("AlertService initialized with thread pool");

            log("Monitoring services initialized successfully");

        } catch (Exception e) {
            logError("Failed to initialize monitoring services - some features may not work", e);
            // Don't throw exception - allow application to continue even if monitoring fails
        }
    }

    /**
     * Called when the web application is shutting down.
     * Stops the MetricsAggregator scheduler and AlertService thread pool gracefully.
     *
     * @param event ServletContext destruction event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log("Shutting down monitoring services...");

        try {
            // Stop alert service thread pool
            AlertService alertService = AlertService.getInstance();
            alertService.shutdown();

            // Stop metrics aggregation scheduler
            MetricsAggregator aggregator = MetricsAggregator.getInstance();
            aggregator.stop();

            log("Monitoring services shut down successfully");

        } catch (Exception e) {
            logError("Error shutting down monitoring services", e);
        }
    }

    /**
     * Logs informational message.
     *
     * @param message Message to log
     */
    private void log(String message) {
        System.out.println("[MonitoringContextListener] " + message);
    }

    /**
     * Logs error message with exception.
     *
     * @param message Error message
     * @param e Exception that occurred
     */
    private void logError(String message, Exception e) {
        System.err.println("[MonitoringContextListener ERROR] " + message);
        if (e != null) {
            e.printStackTrace();
        }
    }
}
