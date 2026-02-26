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
 * Changes a company's password after verifying admin credentials.
 * Cross-database compatible (Postgres and MySQL).
 */
public class LocalChangeCompanyPasswordServlet extends LocalUserManagementServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String company = param(req, "Company");
        String email = param(req, "Email");
        String oldPassword = param(req, "OldPassword");
        String newPassword = param(req, "NewPassword");
        String confirmPassword = param(req, "ConfirmNewPassword");

        if (company.isEmpty()) { redirectToError(req, resp, "Company is missing.", "ChangeCompanyPassword.jsp"); return; }
        if (email.isEmpty()) { redirectToError(req, resp, "Administrator email is missing.", "ChangeCompanyPassword.jsp"); return; }
        if (oldPassword.isEmpty()) { redirectToError(req, resp, "Current password is missing.", "ChangeCompanyPassword.jsp"); return; }
        if (newPassword.isEmpty()) { redirectToError(req, resp, "New password is missing.", "ChangeCompanyPassword.jsp"); return; }
        if (!newPassword.equals(confirmPassword)) { redirectToError(req, resp, "New passwords do not match.", "ChangeCompanyPassword.jsp"); return; }
        if (newPassword.length() < 4) { redirectToError(req, resp, "New password must be at least 4 characters.", "ChangeCompanyPassword.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            // Verify the admin user exists and belongs to this company
            int companyId = -1;
            String storedCompanyHash = null;
            String storedUserHash = null;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT c.id, c.password AS company_password, u.password AS user_password " +
                    "FROM companies c JOIN users u ON u.company_id = c.id " +
                    "WHERE LOWER(c.company_name) = LOWER(?) AND LOWER(u.email) = LOWER(?) AND u.role = 'admin'")) {
                stmt.setString(1, company);
                stmt.setString(2, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        redirectToError(req, resp, "Company/admin combination not found.", "ChangeCompanyPassword.jsp");
                        return;
                    }
                    companyId = rs.getInt("id");
                    storedCompanyHash = rs.getString("company_password");
                    storedUserHash = rs.getString("user_password");
                }
            }

            // Verify the old password (check against both company and user password)
            if (!verifyPassword(oldPassword, storedCompanyHash) && !verifyPassword(oldPassword, storedUserHash)) {
                redirectToError(req, resp, "Current password is incorrect.", "ChangeCompanyPassword.jsp");
                return;
            }

            // Update company password and admin user password
            String newHash = hashPassword(newPassword);
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE companies SET password = ?, updated_at = NOW() WHERE id = ?")) {
                    stmt.setString(1, newHash);
                    stmt.setInt(2, companyId);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE users SET password = ?, updated_at = NOW() " +
                        "WHERE company_id = ? AND LOWER(email) = LOWER(?) AND role = 'admin'")) {
                    stmt.setString(1, newHash);
                    stmt.setInt(2, companyId);
                    stmt.setString(3, email);
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

            log("Company password changed for: " + company + " by admin: " + email);
            redirectToLogin(resp, "Company password changed successfully!");

        } catch (SQLException e) {
            log("Change company password failed", e);
            redirectToError(req, resp, "Failed to change company password: " + e.getMessage(), "ChangeCompanyPassword.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
