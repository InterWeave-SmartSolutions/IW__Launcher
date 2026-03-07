package com.interweave.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * TransactionLoggingFilter — intercepts transformation requests and logs
 * execution data to the monitoring transaction_executions table.
 *
 * Registered in iwtransformationserver's web.xml to intercept /transform
 * and /scheduledtransform endpoints. Uses the shared jdbc/IWDB DataSource
 * defined in Tomcat's conf/context.xml.
 *
 * Design goals:
 * - Never break a transformation due to logging failure
 * - Minimal overhead (single INSERT after request completes)
 * - Extract flow context from request parameters
 */
public class TransactionLoggingFilter implements Filter {

    private DataSource dataSource;
    private String hostname;
    private boolean enabled = true;

    private static final String INSERT_SQL =
        "INSERT INTO transaction_executions " +
        "(execution_id, flow_name, flow_type, status, started_at, completed_at, " +
        " duration_ms, records_processed, error_message, triggered_by, server_hostname) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void init(FilterConfig config) throws ServletException {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            dataSource = (DataSource) envCtx.lookup("jdbc/IWDB");
        } catch (Exception e) {
            System.err.println("[TransactionLoggingFilter] WARNING: Could not look up DataSource jdbc/IWDB. " +
                "Transaction logging disabled. Error: " + e.getMessage());
            enabled = false;
        }

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostname = "unknown";
        }

        String enabledParam = config.getInitParameter("enabled");
        if ("false".equalsIgnoreCase(enabledParam)) {
            enabled = false;
        }

        System.out.println("[TransactionLoggingFilter] Initialized. enabled=" + enabled +
            ", hostname=" + hostname);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!enabled || !(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        // Extract context from request parameters
        String flowName = extractFlowName(httpReq);
        String flowType = determineFlowType(httpReq);
        String triggeredBy = determineTriggeredBy(httpReq);

        // Wrap response to capture status code
        StatusCapturingResponseWrapper wrapper = new StatusCapturingResponseWrapper(httpRes);

        long startTime = System.currentTimeMillis();
        String errorMessage = null;
        String status = "success";

        try {
            chain.doFilter(request, wrapper);

            int httpStatus = wrapper.getCapturedStatus();
            if (httpStatus >= 400) {
                status = "failed";
                errorMessage = "HTTP " + httpStatus;
            }
        } catch (Exception e) {
            status = "failed";
            errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
            if (e instanceof IOException) throw (IOException) e;
            if (e instanceof ServletException) throw (ServletException) e;
            throw new ServletException(e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logExecution(flowName, flowType, status, startTime, duration,
                        errorMessage, triggeredBy);
        }
    }

    /**
     * Extract flow name from request parameters.
     * The transformation server receives flow identifiers via query params.
     */
    private String extractFlowName(HttpServletRequest req) {
        // Try common parameter names used by the transformation engine
        String[] paramNames = {"flowName", "flow", "profile", "ProfileName",
                               "transactionName", "name", "id"};
        for (String p : paramNames) {
            String val = req.getParameter(p);
            if (val != null && !val.isEmpty()) {
                return val;
            }
        }
        // Fall back to servlet path + query info
        String path = req.getServletPath();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            return path + pathInfo;
        }
        return path != null ? path : "unknown";
    }

    /**
     * Determine flow type based on the endpoint being called.
     */
    private String determineFlowType(HttpServletRequest req) {
        String path = req.getServletPath();
        if (path != null) {
            if (path.contains("scheduledtransform")) return "transaction";
            if (path.contains("transform")) return "utility";
            if (path.contains("iwxml")) return "query";
        }
        return "transaction";
    }

    /**
     * Determine how the transformation was triggered.
     */
    private String determineTriggeredBy(HttpServletRequest req) {
        // Scheduled transforms are triggered by the scheduler
        String path = req.getServletPath();
        if (path != null && path.contains("scheduled")) {
            return "scheduler";
        }
        // Check for API trigger header
        String trigger = req.getHeader("X-Triggered-By");
        if (trigger != null) {
            return trigger;
        }
        return "manual";
    }

    /**
     * Insert execution record into transaction_executions.
     * Failures are logged but never propagated — transformation must not break.
     */
    private void logExecution(String flowName, String flowType, String status,
                              long startTimeMs, long durationMs,
                              String errorMessage, String triggeredBy) {
        if (dataSource == null) return;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(INSERT_SQL);

            String executionId = UUID.randomUUID().toString();
            Timestamp startedAt = new Timestamp(startTimeMs);
            Timestamp completedAt = new Timestamp(startTimeMs + durationMs);

            ps.setString(1, executionId);                    // execution_id
            ps.setString(2, truncate(flowName, 255));        // flow_name
            ps.setString(3, flowType);                       // flow_type
            ps.setString(4, status);                         // status
            ps.setTimestamp(5, startedAt);                    // started_at
            ps.setTimestamp(6, completedAt);                  // completed_at
            ps.setInt(7, (int) durationMs);                  // duration_ms
            ps.setInt(8, 0);                                 // records_processed (not available from HTTP layer)
            ps.setString(9, truncate(errorMessage, 1000));   // error_message
            ps.setString(10, triggeredBy);                   // triggered_by
            ps.setString(11, hostname);                      // server_hostname

            ps.executeUpdate();
        } catch (Exception e) {
            // Never let logging failures break transformations
            System.err.println("[TransactionLoggingFilter] Failed to log execution for flow '" +
                flowName + "': " + e.getMessage());
        } finally {
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    private static String truncate(String s, int maxLen) {
        if (s == null) return null;
        return s.length() <= maxLen ? s : s.substring(0, maxLen);
    }

    private static void closeQuietly(AutoCloseable c) {
        if (c != null) {
            try { c.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void destroy() {
        System.out.println("[TransactionLoggingFilter] Destroyed.");
    }

    /**
     * Response wrapper that captures the HTTP status code.
     * HttpServletResponse doesn't expose getStatus() in Servlet 2.x,
     * so we capture it via sendError/setStatus/sendRedirect.
     */
    private static class StatusCapturingResponseWrapper extends HttpServletResponseWrapper {
        private int capturedStatus = 200;

        public StatusCapturingResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void setStatus(int sc) {
            capturedStatus = sc;
            super.setStatus(sc);
        }

        @Override
        public void sendError(int sc) throws IOException {
            capturedStatus = sc;
            super.sendError(sc);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            capturedStatus = sc;
            super.sendError(sc, msg);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            capturedStatus = 302;
            super.sendRedirect(location);
        }

        public int getCapturedStatus() {
            return capturedStatus;
        }
    }
}
