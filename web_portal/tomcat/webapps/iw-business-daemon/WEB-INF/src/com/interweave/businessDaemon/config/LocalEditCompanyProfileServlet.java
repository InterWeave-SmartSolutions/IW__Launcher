package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;

public class LocalEditCompanyProfileServlet extends LocalUserManagementServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String company = param(req, "Company");
        String email = param(req, "Email");
        String password = param(req, "Password");

        if (company.isEmpty()) { redirectToError(req, resp, "Company is missing.", "EditCompanyProfile.jsp"); return; }
        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "EditCompanyProfile.jsp"); return; }
        if (password.isEmpty()) { redirectToError(req, resp, "Password is missing.", "EditCompanyProfile.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            String storedHash = null;
            String firstName = null, lastName = null, solutionType = null;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.password, u.first_name, u.last_name, c.solution_type " +
                    "FROM users u JOIN companies c ON u.company_id = c.id " +
                    "WHERE LOWER(c.company_name) = LOWER(?) AND LOWER(u.email) = LOWER(?) AND u.role = 'admin'")) {
                stmt.setString(1, company);
                stmt.setString(2, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        redirectToError(req, resp, "Company/admin not found.", "EditCompanyProfile.jsp");
                        return;
                    }
                    storedHash = rs.getString("password");
                    firstName = rs.getString("first_name");
                    lastName = rs.getString("last_name");
                    solutionType = rs.getString("solution_type");
                }
            }
            if (!verifyPassword(password, storedHash)) {
                redirectToError(req, resp, "Invalid password.", "EditCompanyProfile.jsp");
                return;
            }

            String profileName = company + ":" + email.toLowerCase();
            TransactionContext reqCompany = ConfigContext.getRequestCompany();
            if (reqCompany != null) {
                TransactionThread tt = reqCompany.addTransactionThread(profileName);
                if (tt != null) {
                    setThreadField(tt, "firstName", firstName);
                    setThreadField(tt, "lastName", lastName);
                    setThreadField(tt, "company", company);
                    setThreadField(tt, "title", solutionType);
                    tt.setEmail(email.toLowerCase());
                }
            }
            ConfigContext.setUserLoggedIn(true);

            String brand = param(req, "PortalBrand");
            String solutions = param(req, "PortalSolutions");
            StringBuilder url = new StringBuilder("EditCompanyProfile.jsp?CurrentProfile=");
            url.append(java.net.URLEncoder.encode(profileName, "UTF-8"));
            url.append("&Email=").append(java.net.URLEncoder.encode(email, "UTF-8"));
            url.append("&Company=").append(java.net.URLEncoder.encode(company, "UTF-8"));
            if (!brand.isEmpty()) url.append("&PortalBrand=").append(java.net.URLEncoder.encode(brand, "UTF-8"));
            if (!solutions.isEmpty()) url.append("&PortalSolutions=").append(java.net.URLEncoder.encode(solutions, "UTF-8"));
            resp.sendRedirect(url.toString());

        } catch (SQLException e) {
            log("Edit company profile failed", e);
            redirectToError(req, resp, "Failed to load company profile: " + e.getMessage(), "EditCompanyProfile.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
