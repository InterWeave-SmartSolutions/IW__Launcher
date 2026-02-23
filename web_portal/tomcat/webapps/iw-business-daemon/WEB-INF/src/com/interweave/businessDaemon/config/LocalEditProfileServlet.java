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

/**
 * Authenticates a user and loads their profile data into ConfigContext
 * so EditProfile.jsp can display it.
 * Bridge implementation using direct SQL against Supabase.
 */
public class LocalEditProfileServlet extends LocalUserManagementServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = param(req, "Email");
        String password = param(req, "Password");

        if (email.isEmpty()) { redirectToError(req, resp, "Email is missing.", "EditProfile.jsp"); return; }
        if (password.isEmpty()) { redirectToError(req, resp, "Password is missing.", "EditProfile.jsp"); return; }

        try (Connection conn = dataSource.getConnection()) {
            String storedHash = null;
            String firstName = null;
            String lastName = null;
            String companyName = null;
            String solutionType = null;

            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.password, u.first_name, u.last_name, " +
                    "c.company_name, c.solution_type " +
                    "FROM users u JOIN companies c ON u.company_id = c.id " +
                    "WHERE LOWER(u.email) = LOWER(?)")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        redirectToError(req, resp, "User not found.", "EditProfile.jsp");
                        return;
                    }
                    storedHash = rs.getString("password");
                    firstName = rs.getString("first_name");
                    lastName = rs.getString("last_name");
                    companyName = rs.getString("company_name");
                    solutionType = rs.getString("solution_type");
                }
            }

            if (!verifyPassword(password, storedHash)) {
                redirectToError(req, resp, "Invalid password.", "EditProfile.jsp");
                return;
            }

            String profileName = companyName + ":" + email.toLowerCase();

            // Set up ConfigContext so EditProfile.jsp can read the data
            TransactionContext reqUser = ConfigContext.getRequestUser();
            if (reqUser != null) {
                TransactionThread tt = reqUser.addTransactionThread(profileName);
                if (tt != null) {
                    setThreadField(tt, "firstName", firstName);
                    setThreadField(tt, "lastName", lastName);
                    setThreadField(tt, "company", companyName);
                    setThreadField(tt, "title", solutionType);
                    tt.setEmail(email.toLowerCase());
                }
            }

            ConfigContext.setUserLoggedIn(true);

            // Redirect back to EditProfile.jsp with the profile loaded
            String brand = param(req, "PortalBrand");
            String solutions = param(req, "PortalSolutions");
            StringBuilder url = new StringBuilder("EditProfile.jsp?CurrentProfile=");
            url.append(java.net.URLEncoder.encode(profileName, "UTF-8"));
            url.append("&Email=").append(java.net.URLEncoder.encode(email, "UTF-8"));
            if (!brand.isEmpty()) url.append("&PortalBrand=").append(java.net.URLEncoder.encode(brand, "UTF-8"));
            if (!solutions.isEmpty()) url.append("&PortalSolutions=").append(java.net.URLEncoder.encode(solutions, "UTF-8"));
            resp.sendRedirect(url.toString());

        } catch (SQLException e) {
            log("Edit profile failed", e);
            redirectToError(req, resp, "Failed to load profile: " + e.getMessage(), "EditProfile.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {}
}
