package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalSaveProfileServlet extends LocalUserManagementServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String firstName = param(req, "FirstName");
        String lastName = param(req, "LastName");
        String email = param(req, "Email");

        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "EditProfile.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE users SET first_name = ?, last_name = ? WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    redirectToError(req, resp, "User not found.", "EditProfile.jsp");
                    return;
                }
            }
            log("Profile updated for: " + email);
            redirectToLogin(resp, "Profile updated successfully!");
        } catch (SQLException e) {
            log("Save profile failed", e);
            redirectToError(req, resp, "Failed to save profile: " + e.getMessage(), "EditProfile.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
