package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.HostedTransactionBase;
import com.interweave.businessDaemon.QueryContext;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;
import com.interweave.web.LoginRateLimiter;
import com.interweave.web.PasswordHasher;

/**
 * LocalLoginServlet - Authenticates users against the local MySQL database
 * instead of the InterWeave central server.
 *
 * This servlet replaces the original LoginServlet for standalone IW_IDE operation.
 *
 * @author IW_IDE Local Authentication
 * @version 1.1
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
            log("LocalLoginServlet initialized - using local MySQL database");
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

            log("Login validation failed: missing email or password");
            redirectToLogin(request, response, "VALIDATION001",
                           "Please enter both email and password",
                           email, portalBrand, portalSolutions);
            return;
        }

        email = email.trim().toLowerCase();

        // Check account lockout before hitting the database
        if (LoginRateLimiter.isLockedOut(email)) {
            long remaining = LoginRateLimiter.getRemainingLockoutSeconds(email);
            redirectToLogin(request, response, "AUTH005",
                "Too many failed attempts. Please try again in " + remaining + " seconds.",
                email, portalBrand, portalSolutions);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Authenticate user and get detailed result
            AuthenticationResult authResult = authenticateUser(conn, email, password);

            if (authResult.success) {
                // Authentication successful — clear lockout counter
                LoginRateLimiter.clearFailures(email);
                UserInfo userInfo = authResult.userInfo;
                // Prevent session fixation: invalidate old session and create new one
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userInfo.userId);
                session.setAttribute("userEmail", userInfo.email);
                session.setAttribute("userName", userInfo.firstName + " " + userInfo.lastName);
                session.setAttribute("companyId", userInfo.companyId);
                session.setAttribute("companyName", userInfo.companyName);
                session.setAttribute("isAdmin", userInfo.isAdmin);
                session.setAttribute("role", userInfo.role);
                session.setAttribute("solutionType", userInfo.solutionType);
                session.setAttribute("authenticated", true);

                // Update last login timestamp
                updateLastLogin(conn, userInfo.userId);

                // Set ConfigContext flags so downstream JSPs recognize the login
                ConfigContext.setHosted(true);
                ConfigContext.setAdminLoggedIn(true);
                if (userInfo.isAdmin) {
                    ConfigContext.setLoggedUserType('A');
                } else {
                    ConfigContext.setLoggedUserType('U');
                }

                // Build profile name in the format expected by downstream JSPs
                String profileName = userInfo.companyName + ":" + userInfo.email;

                // Load previously saved configuration from DB, or use empty default
                String savedConfig = loadSavedConfig(conn, userInfo.companyId, profileName);
                String config = (savedConfig != null) ? savedConfig
                    : "<SF2QBConfiguration></SF2QBConfiguration>";

                // Register the logged-in profile across the hosted runtime so
                // the classic wizard pages, BDConfigurator.jsp, and query list
                // all see the same per-profile state.
                bindHostedProfile(profileName, config);
                if (savedConfig != null) {
                    try {
                        WorkspaceProfileCompiler.compileProfile(
                            getServletContext(), profileName, userInfo.solutionType, savedConfig);
                    } catch (IOException ioe) {
                        log("Login succeeded but workspace compiler failed for " + profileName, ioe);
                    }
                }

                log("User authenticated successfully: " + email + " (Company: " + userInfo.companyName + ")");

                // Build common query string parts
                String encodedProfile = java.net.URLEncoder.encode(profileName, "UTF-8");
                String encodedSolution = java.net.URLEncoder.encode(
                    userInfo.solutionType != null ? userInfo.solutionType : "QB", "UTF-8");
                String brandSol = "";
                if (portalBrand != null && !portalBrand.isEmpty()) {
                    brandSol += "&PortalBrand=" + java.net.URLEncoder.encode(portalBrand, "UTF-8");
                }
                if (portalSolutions != null && !portalSolutions.isEmpty()) {
                    brandSol += "&PortalSolutions=" + java.net.URLEncoder.encode(portalSolutions, "UTF-8");
                }

                // Route: returning users → flows page (IMConfig.jsp)
                //        first-time users  → wizard    (CompanyConfiguration.jsp)
                String redirectUrl;
                if (savedConfig != null) {
                    // User has saved config → go to flows management page
                    // (matches original LoginServlet which forwards to IMConfig.jsp)
                    redirectUrl = "IMConfig.jsp?CurrentProfile=" + encodedProfile + brandSol;
                } else {
                    // No saved config → go to wizard for initial setup
                    redirectUrl = "CompanyConfiguration.jsp?CurrentProfile=" + encodedProfile
                        + "&Solution=" + encodedSolution + brandSol;
                }

                response.sendRedirect(redirectUrl);
            } else {
                // Authentication failed — record for lockout tracking
                LoginRateLimiter.recordFailure(email);
                if ("AUTH001".equals(authResult.errorCode)) {
                    log("Authentication failed: Invalid credentials");
                } else {
                    log("Authentication failed for " + email + ": " + authResult.errorMessage);
                }

                redirectToLogin(request, response, authResult.errorCode, authResult.errorMessage,
                               email, portalBrand, portalSolutions);
            }

        } catch (SQLException e) {
            // Database connection or query error
            log("Database error during authentication: " + e.getMessage());

            redirectToLogin(request, response, "DB001",
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
        String sqlUserCheck = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, " +
                              "u.role, u.is_active, u.company_id, c.company_name, " +
                              "c.solution_type, c.is_active as company_active " +
                              "FROM users u " +
                              "JOIN companies c ON u.company_id = c.id " +
                              "WHERE LOWER(u.email) = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlUserCheck)) {
            stmt.setString(1, email.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    boolean userActive = rs.getBoolean("is_active");
                    boolean companyActive = rs.getBoolean("company_active");

                    // Verify password first
                    if (!verifyPassword(password, storedHash)) {
                        // Wrong password - return generic invalid credentials error
                        // (don't reveal that the email exists - security best practice)
                        return AuthenticationResult.failure(
                            "AUTH001",
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
                            "AUTH003",
                            "Your company account is inactive",
                            "Company account disabled or suspended",
                            "Contact your company administrator or InterWeave support to reactivate your company account. " +
                            "This may be due to expired subscription or administrative action."
                        );
                    }

                    // Check if user is inactive
                    if (!userActive) {
                        return AuthenticationResult.failure(
                            "AUTH002",
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
                    userInfo.role = rs.getString("role");
                    userInfo.isAdmin = "admin".equalsIgnoreCase(userInfo.role);
                    userInfo.companyId = rs.getInt("company_id");
                    userInfo.companyName = rs.getString("company_name");
                    userInfo.solutionType = rs.getString("solution_type");

                    // Progressive bcrypt migration: upgrade SHA-256/plaintext to bcrypt
                    if (PasswordHasher.needsRehash(storedHash)) {
                        PasswordHasher.rehashIfNeeded(conn, "users", "id",
                            userInfo.userId, password);
                    }

                    return AuthenticationResult.success(userInfo);

                } else {
                    // User not found - return generic invalid credentials error
                    // (don't reveal that the email doesn't exist - security best practice)
                    return AuthenticationResult.failure(
                        "AUTH001",
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
     * Verifies password against stored hash.
     * Supports bcrypt, SHA-256 hex, and plain text (delegates to PasswordHasher).
     */
    private boolean verifyPassword(String password, String storedHash) {
        return PasswordHasher.verify(password, storedHash);
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
                                 String errorCode, String errorMessage, String email,
                                 String portalBrand, String portalSolutions) throws IOException {

        StringBuilder url = new StringBuilder("IWLogin.jsp?error=");
        url.append(java.net.URLEncoder.encode(errorMessage, "UTF-8"));

        // Add error code for specific error handling in JSP
        if (errorCode != null) {
            url.append("&errorCode=").append(java.net.URLEncoder.encode(errorCode, "UTF-8"));
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
     * Sets up a TransactionThread in the given TransactionContext for the profile.
     * Creates the thread with a default "configuration" parameter so downstream
     * servlets (CompanyConfigurationServletOS) don't NPE.
     *
     * IMPORTANT: CompanyConfiguration.jsp (line 101) appends the closing
     * {@code </SF2QBConfiguration>} tag before parsing, so the "configuration"
     * parameter must NOT include it.  {@code setCompanyConfiguration()} keeps
     * the full XML because CompanyConfiguration.jsp line 80 parses it as-is.
     */
    private static final String CONFIG_CLOSE_TAG = "</SF2QBConfiguration>";

    /**
     * Removes ALL occurrences of the closing tag — see LocalUserManagementServlet
     * for the full rationale.  Compiled intermediate servlets append new elements
     * at the end of the buffer, so the parameter must never contain a closing tag.
     */
    private static String sanitizeConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "");
    }

    private static String sanitizeFullConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration></SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "") + CONFIG_CLOSE_TAG;
    }

    private void setupTransactionThread(TransactionContext ctx, String profileName, String config) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(ctx, profileName);
        if (tt == null) {
            tt = ctx.addTransactionThread(profileName);
        }
        if (tt != null) {
            // Strip ALL closing tags — the JSP appends one before parsing
            String paramConfig = sanitizeConfig(config);
            tt.putParameter("configuration", paramConfig);
            // Full valid XML for getCompanyConfiguration() (JSP Path A)
            tt.setCompanyConfiguration(sanitizeFullConfig(config));
            applyEndpointMode(tt);
        }
    }

    private void setupQueryInstance(QueryContext ctx, String profileName) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        if (ConfigContext.getQueryInstanceByProfileName(ctx, profileName) == null) {
            ctx.addQueryInstance(profileName);
        }
    }

    private void bindHostedProfile(String profileName, String config) {
        applyRuntimeEndpointMode();

        if (!ConfigContext.getMonitorsStarted().contains(profileName)) {
            ConfigContext.getMonitorsStarted().add(profileName);
        }
        if (!ConfigContext.getProfileDescriptors().containsKey(profileName)) {
            ConfigContext.getProfileDescriptors().put(profileName,
                new ConfigContext.ProfileDescriptor());
        }

        setupTransactionThread(ConfigContext.getCompanyRegistration(), profileName, config);
        setupTransactionThread(ConfigContext.getUpdateCompany(), profileName, config);
        setupTransactionThread(ConfigContext.getRequestCompany(), profileName, config);

        for (TransactionContext ctx : ConfigContext.getTransactionList()) {
            setupTransactionThread(ctx, profileName, config);
        }
        for (QueryContext ctx : ConfigContext.getQueryList()) {
            setupQueryInstance(ctx, profileName);
        }
    }

    private void applyRuntimeEndpointMode() {
        ConfigContext.setMyGlobalIP(resolveTransformationServerHost());
        ConfigContext.setPrimaryTransformationServerURL(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL()));
        ConfigContext.setPrimaryTransformationServerURLT(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT()));
        ConfigContext.setPrimaryTransformationServerURL1(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL1()));
        ConfigContext.setPrimaryTransformationServerURLT1(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT1()));
        ConfigContext.setPrimaryTransformationServerURLD(
            rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLD()));
        ConfigContext.setSecondaryTransformationServerURL(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL()));
        ConfigContext.setSecondaryTransformationServerURLT(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT()));
        ConfigContext.setSecondaryTransformationServerURL1(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL1()));
        ConfigContext.setSecondaryTransformationServerURLT1(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT1()));
        ConfigContext.setSecondaryTransformationServerURLD(
            rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(HostedTransactionBase runtimeItem) {
        runtimeItem.setPrimaryTransformationServerURL(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL()));
        runtimeItem.setPrimaryTransformationServerURLT(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT()));
        runtimeItem.setPrimaryTransformationServerURL1(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL1()));
        runtimeItem.setPrimaryTransformationServerURLT1(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT1()));
        runtimeItem.setPrimaryTransformationServerURLD(
            rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLD()));
        runtimeItem.setSecondaryTransformationServerURL(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL()));
        runtimeItem.setSecondaryTransformationServerURLT(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT()));
        runtimeItem.setSecondaryTransformationServerURL1(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL1()));
        runtimeItem.setSecondaryTransformationServerURLT1(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT1()));
        runtimeItem.setSecondaryTransformationServerURLD(
            rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(TransactionThread thread) {
        thread.setPrimaryDedicatedURL(rewriteRuntimeUrl(thread.getPrimaryDedicatedURL()));
        thread.setPrimaryDedicatedURLc(rewriteRuntimeUrl(thread.getPrimaryDedicatedURLc()));
        thread.setSecondaryDedicatedURL(rewriteRuntimeUrl(thread.getSecondaryDedicatedURL()));
        thread.setSecondaryDedicatedURLc(rewriteRuntimeUrl(thread.getSecondaryDedicatedURLc()));
    }

    private static String resolveTransformationServerBase() {
        if (isLegacyTsMode()) {
            return envOrDefault("TS_BASE_LEGACY",
                "http://iw0.interweave.biz:9090/iwtransformationserver");
        }
        return envOrDefault("TS_BASE_LOCAL",
            "http://localhost:9090/iwtransformationserver");
    }

    private static String resolveTransformationServerOrigin() {
        String base = resolveTransformationServerBase();
        int marker = base.indexOf("/iwtransformationserver");
        if (marker >= 0) {
            return base.substring(0, marker);
        }
        int scheme = base.indexOf("://");
        if (scheme < 0) {
            return base;
        }
        int slash = base.indexOf('/', scheme + 3);
        return (slash >= 0) ? base.substring(0, slash) : base;
    }

    private static String resolveTransformationServerHost() {
        String origin = resolveTransformationServerOrigin();
        int scheme = origin.indexOf("://");
        if (scheme >= 0) {
            origin = origin.substring(scheme + 3);
        }
        int slash = origin.indexOf('/');
        if (slash >= 0) {
            origin = origin.substring(0, slash);
        }
        int colon = origin.lastIndexOf(':');
        if (colon > -1) {
            return origin.substring(0, colon);
        }
        return origin;
    }

    private static String rewriteRuntimeUrl(String url) {
        if (url == null) return "";
        String value = url.trim();
        if (value.isEmpty() || "null".equalsIgnoreCase(value)) {
            return value;
        }

        String origin = resolveTransformationServerOrigin();
        String fallback = resolveTransformationServerBase();
        int marker = value.indexOf("/iwtransformationserver");
        if (marker >= 0) {
            return origin + value.substring(marker);
        }

        if (value.startsWith("http://") || value.startsWith("https://")) {
            int scheme = value.indexOf("://");
            int slash = value.indexOf('/', scheme + 3);
            if (slash >= 0) {
                return origin + value.substring(slash);
            }
            return fallback;
        }

        return fallback;
    }

    private static boolean isLegacyTsMode() {
        String mode = System.getenv("TS_MODE");
        return mode != null && "legacy".equalsIgnoreCase(mode.trim());
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            value = fallback;
        }
        value = value.trim();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    /**
     * Loads previously saved configuration XML from company_configurations.
     * Returns the saved XML, or null if none found.
     */
    private String loadSavedConfig(Connection conn, int companyId, String profileName) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT configuration_xml FROM company_configurations " +
                "WHERE company_id = ? AND profile_name = ?")) {
            stmt.setInt(1, companyId);
            stmt.setString(2, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String xml = rs.getString("configuration_xml");
                    if (xml != null && !xml.isEmpty()) {
                        log("Loaded saved configuration for " + profileName
                            + " (" + xml.length() + " chars)");
                        return xml;
                    }
                }
            }
        } catch (SQLException e) {
            log("Could not load saved configuration for " + profileName, e);
        }
        return null;
    }

    /**
     * Inner class to hold authentication result with detailed error information
     */
    private static class AuthenticationResult {
        boolean success;
        UserInfo userInfo;
        String errorCode;
        String errorMessage;
        String cause;
        String resolution;

        private AuthenticationResult(boolean success, UserInfo userInfo, String errorCode,
                                    String errorMessage, String cause, String resolution) {
            this.success = success;
            this.userInfo = userInfo;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.cause = cause;
            this.resolution = resolution;
        }

        static AuthenticationResult success(UserInfo userInfo) {
            return new AuthenticationResult(true, userInfo, null, null, null, null);
        }

        static AuthenticationResult failure(String errorCode, String errorMessage,
                                           String cause, String resolution) {
            return new AuthenticationResult(false, null, errorCode, errorMessage, cause, resolution);
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
        String role;
        boolean isAdmin;
        int companyId;
        String companyName;
        String solutionType;
    }
}
