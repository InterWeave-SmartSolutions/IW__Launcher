package com.interweave.businessDaemon.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * AuditService - Static utility for recording audit log entries.
 *
 * Designed to be called from any servlet after a significant action.
 * Gets its own Connection from the DataSource (independent of the caller's
 * transaction) and catches ALL exceptions internally so that audit logging
 * never breaks the calling operation.
 */
public class AuditService {

    private static final String INSERT_SQL =
        "INSERT INTO audit_log " +
        "(user_id, company_id, user_email, action_type, action_detail, " +
        " resource_type, resource_id, ip_address, user_agent, metadata) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /** Prevent instantiation — all methods are static. */
    private AuditService() {}

    /**
     * Record an audit log entry.
     *
     * @param ds           JNDI DataSource (jdbc/IWDB)
     * @param userId       Authenticated user ID (nullable for failed logins)
     * @param companyId    Company ID (nullable)
     * @param userEmail    User email address (nullable)
     * @param actionType   One of the CHECK-constrained action_type values
     * @param actionDetail Human-readable description of what happened
     * @param resourceType Type of resource affected (e.g. "user", "company", "flow")
     * @param resourceId   ID of the affected resource
     * @param request      HttpServletRequest for IP / User-Agent extraction
     * @param metadata     Optional JSON metadata string
     */
    public static void record(DataSource ds, Integer userId, Integer companyId,
            String userEmail, String actionType, String actionDetail,
            String resourceType, String resourceId,
            HttpServletRequest request, String metadata) {
        if (ds == null || actionType == null) {
            return;
        }

        String ipAddress = null;
        String userAgent = null;
        if (request != null) {
            ipAddress = request.getRemoteAddr();
            userAgent = request.getHeader("User-Agent");
            if (userAgent != null && userAgent.length() > 500) {
                userAgent = userAgent.substring(0, 500);
            }
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            if (userId != null) {
                stmt.setInt(1, userId);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            if (companyId != null) {
                stmt.setInt(2, companyId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, userEmail);
            stmt.setString(4, actionType);
            stmt.setString(5, actionDetail);
            stmt.setString(6, resourceType);
            stmt.setString(7, resourceId);
            stmt.setString(8, ipAddress);
            stmt.setString(9, userAgent);
            stmt.setString(10, metadata);
            stmt.executeUpdate();
        } catch (Exception e) {
            // Audit logging must never break the calling operation.
            // Log to stderr so it appears in catalina.out but do not propagate.
            System.err.println("[AuditService] Failed to record audit entry: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ignored) {}
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
    }
}
