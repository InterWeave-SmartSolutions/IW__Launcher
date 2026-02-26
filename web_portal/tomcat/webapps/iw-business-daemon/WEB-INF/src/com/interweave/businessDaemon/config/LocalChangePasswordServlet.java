package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Changes a user's password after verifying the old password.
 * Cross-database compatible (Postgres and MySQL).
 */
public class LocalChangePasswordServlet extends LocalUserManagementServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = param(req, "Email");
        String oldPassword = param(req, "OldPassword");
        String newPassword = param(req, "NewPassword");
        String confirmPassword = param(req, "ConfirmNewPassword");

        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "ChangePassword.jsp"); return; }
        if (oldPassword.isEmpty()) { redirectToError(req, resp, "Current password is missing.", "ChangePassword.jsp"); return; }
        if (newPassword.isEmpty()) { redirectToError(req, resp, "New password is missing.", "ChangePassword.jsp"); return; }
        if (!newPassword.equals(confirmPassword)) { redirectToError(req, resp, "New passwords do not match.", "ChangePassword.jsp"); return; }
        if (newPassword.length() < 4) { redirectToError(req, resp, "New password must be at least 4 characters.", "ChangePassword.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            // Verify the old password
            String storedHash = null;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT password FROM users WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        redirectToError(req, resp, "User not found.", "ChangePassword.jsp");
                        return;
                    }
                    storedHash = rs.getString("password");
                }
            }

            if (!verifyPassword(oldPassword, storedHash)) {
                redirectToError(req, resp, "Current password is incorrect.", "ChangePassword.jsp");
                return;
            }

            // Update with new hashed password
            String newHash = hashPassword(newPassword);
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE users SET password = ?, updated_at = NOW() WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, newHash);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }

            log("Password changed for user: " + email);
            redirectToLogin(resp, "Password changed successfully!");

        } catch (SQLException e) {
            log("Change password failed", e);
            redirectToError(req, resp, "Failed to change password: " + e.getMessage(), "ChangePassword.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
