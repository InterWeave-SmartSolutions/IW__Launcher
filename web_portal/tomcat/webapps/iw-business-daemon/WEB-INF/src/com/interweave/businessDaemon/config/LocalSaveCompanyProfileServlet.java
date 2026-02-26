package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalSaveCompanyProfileServlet extends LocalUserManagementServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String company = param(req, "CompanyOrganization");
        String email = param(req, "Email");
        String firstName = param(req, "FirstName");
        String lastName = param(req, "LastName");
        String solutionType = param(req, "Type");

        if (company.isEmpty() || email.isEmpty()) {
            redirectToError(req, resp, "Company and Email are required.", "EditCompanyProfile.jsp");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Look up company_id first (cross-DB compatible — avoids Postgres UPDATE...FROM)
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
                redirectToError(req, resp, "Company not found.", "EditCompanyProfile.jsp");
                return;
            }

            // Update admin user's name
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE users SET first_name = ?, last_name = ?, updated_at = NOW() " +
                    "WHERE company_id = ? AND LOWER(email) = LOWER(?) AND role = 'admin'")) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setInt(3, companyId);
                stmt.setString(4, email);
                stmt.executeUpdate();
            }

            // Update company solution_type if provided
            if (!solutionType.isEmpty()) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE companies SET solution_type = ?, updated_at = NOW() " +
                        "WHERE id = ?")) {
                    stmt.setString(1, solutionType);
                    stmt.setInt(2, companyId);
                    stmt.executeUpdate();
                }
            }

            // Set flags required by CompanyConfiguration.jsp
            com.interweave.businessDaemon.ConfigContext.setAdminLoggedIn(true);

            String brand = param(req, "PortalBrand");
            String solutions = param(req, "PortalSolutions");
            StringBuilder url = new StringBuilder("CompanyConfiguration.jsp?CurrentProfile=");
            url.append(java.net.URLEncoder.encode(company + ":" + email.toLowerCase(), "UTF-8"));
            url.append("&Solution=").append(java.net.URLEncoder.encode(solutionType.isEmpty() ? "QB" : solutionType, "UTF-8"));
            url.append("&Company=").append(java.net.URLEncoder.encode(company, "UTF-8"));
            url.append("&Email=").append(java.net.URLEncoder.encode(email, "UTF-8"));
            if (!brand.isEmpty()) url.append("&PortalBrand=").append(java.net.URLEncoder.encode(brand, "UTF-8"));
            if (!solutions.isEmpty()) url.append("&PortalSolutions=").append(java.net.URLEncoder.encode(solutions, "UTF-8"));
            url.append("&Message=").append(java.net.URLEncoder.encode("Company profile saved.", "UTF-8"));
            resp.sendRedirect(url.toString());

        } catch (SQLException e) {
            log("Save company profile failed", e);
            redirectToError(req, resp, "Failed to save company profile: " + e.getMessage(), "EditCompanyProfile.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
