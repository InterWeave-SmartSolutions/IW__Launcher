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

import com.interweave.error.ErrorCode;
import com.interweave.error.ErrorLogger;
import com.interweave.error.IWError;

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

        // Validate input - check for missing credentials
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            IWError error = IWError.builder(ErrorCode.VALIDATION001)
                .message("Please enter both email and password")
                .affectedComponent("LocalLoginServlet")
                .cause("Required login credentials not provided")
                .suggestedResolution("Enter your email address and password to log in")
                .build();

            ErrorLogger.logError(error);
            redirectToLogin(request, response, ErrorCode.VALIDATION001,
                           "Please enter both email and password",
                           email, portalBrand, portalSolutions);
            return;
        }

        email = email.trim().toLowerCase();

        try (Connection conn = dataSource.getConnection()) {
            // Authenticate user and get detailed result
            AuthenticationResult authResult = authenticateUser(conn, email, password);

            if (authResult.success) {
                // Authentication successful
                UserInfo userInfo = authResult.userInfo;
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
                // Authentication failed - log and redirect with specific error
                IWError error = IWError.builder(authResult.errorCode)
                    .message(authResult.errorMessage)
                    .affectedComponent("LocalLoginServlet")
                    .cause(authResult.cause)
                    .suggestedResolution(authResult.resolution)
                    .build();

                ErrorLogger.logError(error);

                // Don't log email for invalid credentials (security - avoid info leakage in logs)
                if (authResult.errorCode == ErrorCode.AUTH001) {
                    log("Authentication failed: Invalid credentials");
                } else {
                    log("Authentication failed for " + email + ": " + authResult.errorMessage);
                }

                redirectToLogin(request, response, authResult.errorCode, authResult.errorMessage,
                               email, portalBrand, portalSolutions);
            }

        } catch (SQLException e) {
            // Database connection or query error
            IWError error = IWError.builder(ErrorCode.DB001)
                .message("Unable to connect to authentication database")
                .affectedComponent("LocalLoginServlet")
                .cause("SQLException: " + e.getMessage())
                .suggestedResolution("Please try again. If the problem persists, contact your system administrator")
                .throwable(e)
                .build();

            ErrorLogger.logError(error);

            redirectToLogin(request, response, ErrorCode.DB001,
                           "A system error occurred. Please try again later.",
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
     * Returns detailed result including specific error information
     */
    private AuthenticationResult authenticateUser(Connection conn, String email, String password)
            throws SQLException {

        // First, check if user exists (regardless of active status)
        String sqlUserCheck = "SELECT u.id, u.email, u.password_hash, u.first_name, u.last_name, " +
                              "u.is_admin, u.is_active, u.company_id, c.organization_name, " +
                              "c.solution_type, c.auth_token, c.is_active as company_active " +
                              "FROM users u " +
                              "JOIN companies c ON u.company_id = c.id " +
                              "WHERE LOWER(u.email) = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlUserCheck)) {
            stmt.setString(1, email.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    boolean userActive = rs.getBoolean("is_active");
                    boolean companyActive = rs.getBoolean("company_active");

                    // Verify password first
                    if (!verifyPassword(password, storedHash)) {
                        // Wrong password - return generic invalid credentials error
                        // (don't reveal that the email exists - security best practice)
                        return AuthenticationResult.failure(
                            ErrorCode.AUTH001,
                            "Invalid email or password",
                            "Password verification failed",
                            "Verify your email address and password are correct. " +
                            "Passwords are case-sensitive. Contact your administrator if you've forgotten your password."
                        );
                    }

                    // Password is correct, now check account status

                    // Check if company is inactive
                    if (!companyActive) {
                        return AuthenticationResult.failure(
                            ErrorCode.AUTH003,
                            "Your company account is inactive",
                            "Company account disabled or suspended",
                            "Contact your company administrator or InterWeave support to reactivate your company account. " +
                            "This may be due to expired subscription or administrative action."
                        );
                    }

                    // Check if user is inactive
                    if (!userActive) {
                        return AuthenticationResult.failure(
                            ErrorCode.AUTH002,
                            "Your user account is inactive",
                            "User account disabled",
                            "Contact your company administrator to activate your account. " +
                            "Your account may need to be reactivated or you may need to complete registration."
                        );
                    }

                    // All checks passed - authentication successful
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

                    return AuthenticationResult.success(userInfo);

                } else {
                    // User not found - return generic invalid credentials error
                    // (don't reveal that the email doesn't exist - security best practice)
                    return AuthenticationResult.failure(
                        ErrorCode.AUTH001,
"Invalid email or password",
                        "User not found in database",
                        "Verify your email address and password are correct. " +
                        "If you haven't registered yet, please register first. " +
                        "Contact your administrator if you need assistance."
                    );
                }
            }
        }
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
     * Redirects back to login page with an error message and error code
     */
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response,
                                 ErrorCode errorCode, String errorMessage, String email,
                                 String portalBrand, String portalSolutions) throws IOException {

        StringBuilder url = new StringBuilder("IWLogin.jsp?error=");
        url.append(java.net.URLEncoder.encode(errorMessage, "UTF-8"));

        // Add error code for specific error handling in JSP
        if (errorCode != null) {
            url.append("&errorCode=").append(java.net.URLEncoder.encode(errorCode.getCode(), "UTF-8"));
        }

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
     * Inner class to hold authentication result with detailed error information
     */
    private static class AuthenticationResult {
        boolean success;
        UserInfo userInfo;
        ErrorCode errorCode;
        String errorMessage;
        String cause;
        String resolution;

        static AuthenticationResult success(UserInfo userInfo) {
            AuthenticationResult result = new AuthenticationResult();
            result.success = true;
            result.userInfo = userInfo;
            return result;
        }

        static AuthenticationResult failure(ErrorCode errorCode, String errorMessage,
                                           String cause, String resolution) {
            AuthenticationResult result = new AuthenticationResult();
            result.success = false;
            result.errorCode = errorCode;
            result.errorMessage = errorMessage;
            result.cause = cause;
            result.resolution = resolution;
            return result;
        }
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