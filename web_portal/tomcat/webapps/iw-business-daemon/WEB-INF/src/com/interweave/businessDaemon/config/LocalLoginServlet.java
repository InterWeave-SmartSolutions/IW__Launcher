package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * LocalLoginServlet - Authenticates users against the local PostgreSQL database
 * instead of the InterWeave central server.
 *
 * This servlet replaces the original LoginServlet for standalone IW_IDE operation.
 *
 * @author IW_IDE Local Authentication
 * @version 1.0
 */
public class LocalLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("LocalLoginServlet initialized - using local PostgreSQL database");
        } catch (NamingException e) {
            log("Failed to initialize DataSource", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String portalBrand = request.getParameter("PortalBrand");
        String portalSolutions = request.getParameter("PortalSolutions");

        // Validate input
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            redirectToLogin(request, response, "Please enter both email and password",
                           email, portalBrand, portalSolutions);
            return;
        }

        email = email.trim().toLowerCase();

        try (Connection conn = dataSource.getConnection()) {
            // Look up the user
            UserInfo userInfo = authenticateUser(conn, email, password);

            if (userInfo != null) {
                // Authentication successful
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userInfo.userId);
                session.setAttribute("userEmail", userInfo.email);
                session.setAttribute("userName", userInfo.firstName + " " + userInfo.lastName);
                session.setAttribute("companyId", userInfo.companyId);
                session.setAttribute("companyName", userInfo.companyName);
                session.setAttribute("isAdmin", userInfo.isAdmin);
                session.setAttribute("solutionType", userInfo.solutionType);
                session.setAttribute("authToken", userInfo.authToken);
                session.setAttribute("authenticated", true);

                // Update last login timestamp
                updateLastLogin(conn, userInfo.userId);

                log("User authenticated successfully: " + email + " (Company: " + userInfo.companyName + ")");

                // Redirect to company configuration page
                String redirectUrl = "CompanyConfiguration.jsp";
                if (portalBrand != null && !portalBrand.isEmpty()) {
                    redirectUrl += "?PortalBrand=" + portalBrand;
                    if (portalSolutions != null && !portalSolutions.isEmpty()) {
                        redirectUrl += "&PortalSolutions=" + portalSolutions;
                    }
                } else if (portalSolutions != null && !portalSolutions.isEmpty()) {
                    redirectUrl += "?PortalSolutions=" + portalSolutions;
                }

                response.sendRedirect(redirectUrl);
            } else {
                // Authentication failed
                log("Authentication failed for email: " + email);
                redirectToLogin(request, response, "Invalid email or password",
                               email, portalBrand, portalSolutions);
            }

        } catch (SQLException e) {
            log("Database error during authentication", e);
            redirectToLogin(request, response, "System error. Please try again later.",
                           email, portalBrand, portalSolutions);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to the login page
        response.sendRedirect("IWLogin.jsp");
    }

    /**
     * Authenticates a user against the local database
     */
    private UserInfo authenticateUser(Connection conn, String email, String password)
            throws SQLException {

        String sql = "SELECT u.id, u.email, u.password_hash, u.first_name, u.last_name, " +
                     "u.is_admin, u.company_id, c.organization_name, c.solution_type, c.auth_token " +
                     "FROM users u " +
                     "JOIN companies c ON u.company_id = c.id " +
                     "WHERE LOWER(u.email) = ? AND u.is_active = true AND c.is_active = true";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");

                    // Verify password
                    if (verifyPassword(password, storedHash)) {
                        UserInfo userInfo = new UserInfo();
                        userInfo.userId = rs.getInt("id");
                        userInfo.email = rs.getString("email");
                        userInfo.firstName = rs.getString("first_name");
                        userInfo.lastName = rs.getString("last_name");
                        userInfo.isAdmin = rs.getBoolean("is_admin");
                        userInfo.companyId = rs.getInt("company_id");
                        userInfo.companyName = rs.getString("organization_name");
                        userInfo.solutionType = rs.getString("solution_type");
                        userInfo.authToken = rs.getString("auth_token");
                        return userInfo;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Verifies password against stored hash
     * Supports both plain text (for testing) and SHA-256 hashed passwords
     */
    private boolean verifyPassword(String password, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        // Check if stored hash is a plain text password (for testing purposes)
        if (password.equals(storedHash)) {
            return true;
        }

        // Check SHA-256 hash
        try {
            String hashedInput = hashPassword(password);
            return hashedInput.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            log("Error hashing password", e);
            return false;
        }
    }

    /**
     * Hashes a password using SHA-256
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Updates the last login timestamp for a user
     */
    private void updateLastLogin(Connection conn, int userId) {
        String sql = "UPDATE users SET last_login = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            log("Failed to update last login for user " + userId, e);
        }
    }

    /**
     * Redirects back to login page with an error message
     */
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response,
                                 String errorMessage, String email, String portalBrand,
                                 String portalSolutions) throws IOException {

        StringBuilder url = new StringBuilder("IWLogin.jsp?error=");
        url.append(java.net.URLEncoder.encode(errorMessage, "UTF-8"));

        if (email != null && !email.isEmpty()) {
            url.append("&Email=").append(java.net.URLEncoder.encode(email, "UTF-8"));
        }
        if (portalBrand != null && !portalBrand.isEmpty()) {
            url.append("&PortalBrand=").append(java.net.URLEncoder.encode(portalBrand, "UTF-8"));
        }
        if (portalSolutions != null && !portalSolutions.isEmpty()) {
            url.append("&PortalSolutions=").append(java.net.URLEncoder.encode(portalSolutions, "UTF-8"));
        }

        response.sendRedirect(url.toString());
    }

    /**
     * Inner class to hold user information
     */
    private static class UserInfo {
        int userId;
        String email;
        String firstName;
        String lastName;
        boolean isAdmin;
        int companyId;
        String companyName;
        String solutionType;
        String authToken;
    }
}
