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
 * Registers a new company and its admin user.
 * Bridge implementation using direct SQL against Supabase.
 */
public class LocalCompanyRegistrationServlet extends LocalUserManagementServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String company = param(req, "CompanyOrganization");
        String email = param(req, "Email");
        String firstName = param(req, "FirstName");
        String lastName = param(req, "LastName");
        String password = param(req, "Password");
        String confirmPassword = param(req, "ConfirmPassword");
        String solutionType = param(req, "SolutionType");

        if (company.isEmpty()) { redirectToError(req, resp, "Company/Organization is missing.", "CompanyRegistration.jsp"); return; }
        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "CompanyRegistration.jsp"); return; }
        if (firstName.isEmpty()) { redirectToError(req, resp, "First name is missing.", "CompanyRegistration.jsp"); return; }
        if (lastName.isEmpty()) { redirectToError(req, resp, "Last name is missing.", "CompanyRegistration.jsp"); return; }
        if (password.isEmpty()) { redirectToError(req, resp, "Password is missing.", "CompanyRegistration.jsp"); return; }
        if (!password.equals(confirmPassword)) { redirectToError(req, resp, "Passwords do not match.", "CompanyRegistration.jsp"); return; }
        if (solutionType.isEmpty()) solutionType = "QB";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Check if company already exists
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
                    stmt.setString(1, company);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            redirectToError(req, resp, "Company '" + company + "' already exists.", "CompanyRegistration.jsp");
                            return;
                        }
                    }
                }

                // Check if email already exists
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id FROM users WHERE LOWER(email) = LOWER(?)")) {
                    stmt.setString(1, email);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            redirectToError(req, resp, "A user with this email already exists.", "CompanyRegistration.jsp");
                            return;
                        }
                    }
                }

                // Insert company
                String hashedPw = hashPassword(password);
                int companyId;
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO companies (company_name, email, password, solution_type, is_active) " +
                        "VALUES (?, ?, ?, ?, TRUE) RETURNING id")) {
                    stmt.setString(1, company);
                    stmt.setString(2, email.toLowerCase());
                    stmt.setString(3, hashedPw);
                    stmt.setString(4, solutionType);
                    try (ResultSet rs = stmt.executeQuery()) {
                        rs.next();
                        companyId = rs.getInt(1);
                    }
                }

                // Insert admin user for this company
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (company_id, email, password, first_name, last_name, role, is_active) " +
                        "VALUES (?, ?, ?, ?, ?, 'admin', TRUE)")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, email.toLowerCase());
                    stmt.setString(3, hashedPw);
                    stmt.setString(4, firstName);
                    stmt.setString(5, lastName);
                    stmt.executeUpdate();
                }

                conn.commit();
                log("Company registered: " + company + " (Admin: " + email + ")");
                redirectToLogin(resp, "Company registered successfully! You can now log in.");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            log("Company registration failed", e);
            redirectToError(req, resp, "Registration failed: " + e.getMessage(), "CompanyRegistration.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
