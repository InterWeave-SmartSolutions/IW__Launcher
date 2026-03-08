package com.interweave.businessDaemon.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * NotificationService - Static utility for creating notifications.
 *
 * Usage:
 *   NotificationService.createNotification(ds, userId, companyId,
 *       "alert", "Threshold breached", "Failure rate exceeded 10%", "/monitoring");
 *
 * Never throws — all exceptions are caught and logged internally so that
 * calling code is never broken by a notification-creation failure.
 */
public final class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    private static final String INSERT_SQL =
            "INSERT INTO notifications (user_id, company_id, notification_type, title, message, link) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private NotificationService() {
        // utility class — no instances
    }

    /**
     * Insert a notification row. Catches and logs all exceptions internally.
     *
     * @param ds         JNDI DataSource (jdbc/IWDB)
     * @param userId     target user ID (required)
     * @param companyId  optional company ID (may be null)
     * @param type       one of: system, alert, flow-status, security
     * @param title      short title (max 255 chars)
     * @param message    full message body
     * @param link       optional in-app link (e.g. "/monitoring")
     */
    public static void createNotification(DataSource ds, int userId, Integer companyId,
            String type, String title, String message, String link) {
        if (ds == null) {
            LOG.warning("NotificationService: DataSource is null, skipping notification");
            return;
        }
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            stmt.setInt(1, userId);
            if (companyId != null) {
                stmt.setInt(2, companyId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, type != null ? type : "system");
            stmt.setString(4, title != null ? title : "");
            stmt.setString(5, message != null ? message : "");
            if (link != null) {
                stmt.setString(6, link);
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "NotificationService: Failed to create notification for user " + userId, e);
        }
    }
}
