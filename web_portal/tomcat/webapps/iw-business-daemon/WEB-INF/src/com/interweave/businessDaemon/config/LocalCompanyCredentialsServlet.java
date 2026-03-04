package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.TransactionBase;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;

/**
 * LocalCompanyCredentialsServlet - Replacement for the compiled
 * CompanyCredentialsServlet that persists configuration to the local database.
 *
 * The original compiled servlet tries to save through the InterWeave server
 * backend which is unavailable in the local setup. This servlet saves the
 * complete wizard configuration XML to the company_configurations table.
 *
 * Handles two actions:
 *   "Previous"        - updates config XML, forwards back to CompanyConfigurationDetailP.jsp
 *   "Save and Finish" - updates config XML, persists to DB, redirects to IWLogin.jsp
 */
public class LocalCompanyCredentialsServlet extends LocalUserManagementServlet {

    private static final long serialVersionUID = 1L;

    // Parameters that are NOT part of the configuration XML
    private static final java.util.Set<String> META_PARAMS = new java.util.HashSet<>(
        java.util.Arrays.asList(
            "CurrentProfile", "OldProfile", "Solution", "submit",
            "PortalBrand", "PortalSolutions", "QBCompFilNum",
            "SourceName", "SourceName1", "SourceName2", "SourceName3", "DestName"
        )
    );

    // Password confirmation fields (not stored in config XML)
    private static final java.util.Set<String> CONFIRM_PARAMS = new java.util.HashSet<>(
        java.util.Arrays.asList("SFPswdCfrm")
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Extract parameters ---
        String profile = "";
        String oldProfile = null;
        String solutionType = "";
        String brand = "";
        String solutions = "";
        String submitAction = "";

        Map<String, String[]> par = request.getParameterMap();
        Set<String> ks = par.keySet();

        for (String fn : ks) {
            String vl = par.get(fn)[0];
            if ("CurrentProfile".equals(fn)) {
                profile = vl;
            } else if ("Solution".equals(fn)) {
                solutionType = vl;
            } else if ("OldProfile".equals(fn)) {
                oldProfile = vl;
            } else if ("PortalBrand".equals(fn)) {
                brand = vl.trim();
            } else if ("PortalSolutions".equals(fn)) {
                solutions = vl.trim();
            } else if ("submit".equals(fn)) {
                submitAction = vl;
            }
        }

        // Build portal brand/solutions query string
        String brandSol = "";
        if (brand != null && brand.length() > 0) {
            brandSol += "&PortalBrand=" + brand;
        }
        if (solutions != null && solutions.length() > 0) {
            brandSol += "&PortalSolutions=" + solutions;
        }

        // --- Password validation ---
        String sfPswd = param(request, "SFPswd");
        String sfPswdCfrm = param(request, "SFPswdCfrm");
        if (!sfPswd.isEmpty() && !sfPswd.equals(sfPswdCfrm)) {
            redirectToError(request, response,
                "SF Password and SF Confirmation are out of synch.",
                "IWLogin.jsp");
            return;
        }

        // Validate QB password confirmations (dynamic: QBPswd0/QBPswdCfrm0, QBPswd1/QBPswdCfrm1, etc.)
        for (int i = 0; i < 10; i++) {
            String qbPswd = param(request, "QBPswd" + i);
            String qbPswdCfrm = param(request, "QBPswdCfrm" + i);
            if (!qbPswd.isEmpty() && !qbPswdCfrm.isEmpty() && !qbPswd.equals(qbPswdCfrm)) {
                redirectToError(request, response,
                    "QB Password " + i + " and its confirmation are out of synch.",
                    "IWLogin.jsp");
                return;
            }
        }

        // --- Look up the TransactionThread (with null safety) ---
        TransactionThread ptt = null;

        if (oldProfile == null) {
            TransactionContext ctx = ConfigContext.getCompanyRegistration();
            if (ctx != null) {
                Hashtable threads = ctx.getTransactionThreads();
                if (threads != null) {
                    ptt = (TransactionThread) threads.get(profile);
                }
            }
        } else {
            TransactionContext ctx = ConfigContext.getUpdateCompany();
            if (ctx != null) {
                Hashtable threads = ctx.getTransactionThreads();
                if (threads != null) {
                    ptt = (TransactionThread) threads.get(profile);
                }
            }
        }

        if (ptt == null) {
            log("TransactionThread not found for profile '" + profile
                + "' in CompanyCredentials — cannot save");
            redirectToError(request, response,
                "Session state lost. Please log in and try again.",
                "IWLogin.jsp");
            return;
        }

        // --- Build/update the configuration XML from form parameters ---
        // NOTE: "configuration" parameter must NOT include closing </SF2QBConfiguration>
        // because the JSP appends it before parsing (CompanyConfiguration.jsp line 101).
        String configStr = "<SF2QBConfiguration>";
        try {
            TransactionBase.ParameterValue pv = ptt.getParameters().get("configuration");
            if (pv != null) {
                String existing = pv.getParameterValue();
                if (existing != null && !existing.isEmpty()) {
                    configStr = sanitizeConfig(existing);
                }
            }
        } catch (Exception e) {
            log("Could not read existing configuration, using default", e);
        }

        StringBuffer cnfg = new StringBuffer(configStr);

        for (String fn : ks) {
            String vl = par.get(fn)[0];

            // Skip non-config params
            if (META_PARAMS.contains(fn)) continue;
            // Skip password confirmation fields (the actual password is stored, not the confirm)
            if (CONFIRM_PARAMS.contains(fn)) continue;
            if (fn.startsWith("QBPswdCfrm")) continue;

            // XML-escape the value to prevent malformed XML
            String safeVal = xmlEscape(vl);

            String ft = "<" + fn + ">";
            int ftpos = cnfg.indexOf(ft);

            if (ftpos < 0) {
                // Tag doesn't exist — insert before the closing root tag
                String closeRoot = "</SF2QBConfiguration>";
                int closePos = cnfg.indexOf(closeRoot);
                if (closePos >= 0) {
                    cnfg.insert(closePos, ft + safeVal + "</" + fn + ">");
                } else {
                    cnfg.append(ft + safeVal + "</" + fn + ">");
                }
            } else {
                // Tag exists — replace value
                ftpos = ftpos + ft.length();
                int ftlpos = cnfg.indexOf("</", ftpos);
                if (ftlpos >= 0) {
                    cnfg.replace(ftpos, ftlpos, safeVal);
                }
            }
        }

        // Store updated configuration back in TransactionThread
        ptt.putParameter("configuration", cnfg.toString());

        // --- Handle Previous button ---
        if ("Previous".equals(submitAction)) {
            ConfigContext.setAdminLoggedIn(true);

            String forwardUrl = "/CompanyConfigurationDetailP.jsp?CurrentProfile=" + profile
                + (oldProfile != null ? "&OldProfile=" + oldProfile : "")
                + "&Solution=" + solutionType
                + "&Navigation=B"
                + brandSol;

            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            }
            return;
        }

        // --- Handle Save and Finish ---
        // Parse company name and email from profile key (format: "CompanyName:user@email.com")
        String companyName = "";
        String userEmail = "";
        if (profile != null && profile.contains(":")) {
            int colonPos = profile.indexOf(":");
            companyName = profile.substring(0, colonPos);
            userEmail = profile.substring(colonPos + 1);
        }

        // Persist configuration XML to database
        try (Connection conn = dataSource.getConnection()) {
            // Look up company_id from company name
            int companyId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
                stmt.setString(1, companyName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        companyId = rs.getInt(1);
                    }
                }
            }

            if (companyId < 0) {
                // Fallback: try to find company by user email
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT c.id FROM companies c " +
                        "JOIN users u ON u.company_id = c.id " +
                        "WHERE LOWER(u.email) = LOWER(?)")) {
                    stmt.setString(1, userEmail);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            companyId = rs.getInt(1);
                        }
                    }
                }
            }

            if (companyId < 0) {
                log("Could not find company for profile: " + profile);
                redirectToError(request, response,
                    "Company not found. Please register first.",
                    "IWLogin.jsp");
                return;
            }

            // Upsert configuration (works for both Postgres and MySQL)
            // Ensure closing tag is present for valid XML in the database
            String configXml = sanitizeFullConfig(cnfg.toString());
            if (isPostgres(conn)) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (company_id, profile_name) DO UPDATE SET " +
                        "solution_type = EXCLUDED.solution_type, " +
                        "configuration_xml = EXCLUDED.configuration_xml")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profile);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, configXml);
                    stmt.executeUpdate();
                }
            } else {
                // MySQL: INSERT ... ON DUPLICATE KEY UPDATE
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "solution_type = VALUES(solution_type), " +
                        "configuration_xml = VALUES(configuration_xml)")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profile);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, configXml);
                    stmt.executeUpdate();
                }
            }

            log("Configuration saved for company " + companyName + " (id=" + companyId
                + "), profile=" + profile + ", solution=" + solutionType
                + ", xml length=" + configXml.length());

            try {
                WorkspaceProfileCompiler.CompileResult compiled =
                    WorkspaceProfileCompiler.compileProfile(
                        getServletContext(), profile, solutionType, configXml);
                log("Workspace compiler refreshed for " + compiled.profileName
                    + " -> " + compiled.generatedRoot);
            } catch (IOException ioe) {
                log("Configuration saved but workspace compiler failed for " + profile, ioe);
            }

        } catch (SQLException e) {
            log("Failed to save configuration to database", e);
            redirectToError(request, response,
                "Failed to save configuration. Please try again.",
                "IWLogin.jsp");
            return;
        }

        // Redirect to login page with success
        response.sendRedirect("IWLogin.jsp?success=" +
            java.net.URLEncoder.encode("Configuration saved successfully!", "UTF-8"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not used
    }

    /** Escape XML special characters in a value. */
    private static String xmlEscape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
