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
 * Registers a new user under an existing company.
 * Bridge implementation using direct SQL against Supabase.
 */
public class LocalRegistrationServlet extends LocalUserManagementServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = param(req, "FirstName");
        String lastName = param(req, "LastName");
        String email = param(req, "Email");
        String password = param(req, "Password");
        String confirmPassword = param(req, "ConfirmPassword");
        String company = param(req, "CompanyOrganization");
        String title = param(req, "Title");

        // Validation
        if (firstName.isEmpty()) { redirectToError(req, resp, "First name is missing.", "Registration.jsp"); return; }
        if (lastName.isEmpty()) { redirectToError(req, resp, "Last name is missing.", "Registration.jsp"); return; }
        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "Registration.jsp"); return; }
        if (company.isEmpty()) { redirectToError(req, resp, "Company/Organization is missing.", "Registration.jsp"); return; }
        if (password.isEmpty()) { redirectToError(req, resp, "Password is missing.", "Registration.jsp"); return; }
        if (!password.equals(confirmPassword)) { redirectToError(req, resp, "Passwords do not match.", "Registration.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            // Find the company
            int companyId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
                stmt.setString(1, company);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        companyId = rs.getInt("id");
                    }
                }
            }
            if (companyId == -1) {
                redirectToError(req, resp, "Company '" + company + "' not found. Please register the company first.", "Registration.jsp");
                return;
            }

            // Check if email already exists
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM users WHERE LOWER(email) = LOWER(?)")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        redirectToError(req, resp, "A user with this email already exists.", "Registration.jsp");
                        return;
                    }
                }
            }

            // Insert the new user
            String hashedPw = hashPassword(password);
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (company_id, email, password, first_name, last_name, role, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, 'user', TRUE)")) {
                stmt.setInt(1, companyId);
                stmt.setString(2, email.toLowerCase());
                stmt.setString(3, hashedPw);
                stmt.setString(4, firstName);
                stmt.setString(5, lastName);
                stmt.executeUpdate();
            }

            log("User registered: " + email + " (Company: " + company + ")");
            redirectToLogin(resp, "Registration successful! You can now log in.");

        } catch (SQLException e) {
            log("Registration failed", e);
            redirectToError(req, resp, "Registration failed: " + e.getMessage(), "Registration.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // doGet is a no-op in original servlet
    }
}
