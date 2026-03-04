package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Base class for local user/company management servlets.
 * Provides shared DataSource initialization, password hashing,
 * and redirect helpers.
 */
public abstract class LocalUserManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    protected String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    protected boolean verifyPassword(String input, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) return false;
        if (input.equals(storedHash)) return true; // plain text match (testing)
        return hashPassword(input).equals(storedHash);
    }

    protected void redirectToError(HttpServletRequest req, HttpServletResponse resp,
                                   String message, String returnPage) throws IOException {
        String brand = req.getParameter("PortalBrand");
        String solutions = req.getParameter("PortalSolutions");
        StringBuilder url = new StringBuilder("/ErrorMessage.jsp?ErrorMessageText=");
        url.append(java.net.URLEncoder.encode(message, "UTF-8"));
        url.append("&ErrorMessageReturn=").append(java.net.URLEncoder.encode(returnPage, "UTF-8"));
        if (brand != null && !brand.isEmpty()) {
            url.append("&PortalBrand=").append(java.net.URLEncoder.encode(brand, "UTF-8"));
        }
        if (solutions != null && !solutions.isEmpty()) {
            url.append("&PortalSolutions=").append(java.net.URLEncoder.encode(solutions, "UTF-8"));
        }
        try {
            req.getRequestDispatcher(url.toString()).forward(req, resp);
        } catch (ServletException e) {
            resp.sendRedirect("IWLogin.jsp?error=" + java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }

    protected void redirectToLogin(HttpServletResponse resp, String successMessage) throws IOException {
        resp.sendRedirect("IWLogin.jsp?success=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
    }

    protected String param(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        return (val != null) ? val.trim() : "";
    }

    /**
     * Checks whether the current JDBC connection is to a PostgreSQL database.
     * Useful for any future cases where dialect-specific SQL is unavoidable.
     */
    protected boolean isPostgres(Connection conn) {
        try {
            String dbName = conn.getMetaData().getDatabaseProductName();
            return dbName != null && dbName.toLowerCase().contains("postgre");
        } catch (SQLException e) {
            log("Could not determine database product name", e);
            return false;
        }
    }

    /**
     * The closing root-tag that CompanyConfiguration.jsp appends before parsing.
     * The "configuration" TransactionThread parameter must NEVER contain this tag
     * because the JSP adds it (see CompanyConfiguration.jsp line 101).
     */
    protected static final String CONFIG_CLOSE_TAG = "</SF2QBConfiguration>";

    /**
     * Removes ALL occurrences of {@code </SF2QBConfiguration>} from the XML.
     * <p>
     * The compiled intermediate servlets (CompanyConfigurationSetvletDT, DTT,
     * etc.) APPEND new tags at the end of the StringBuffer rather than inserting
     * before the closing tag. If the "configuration" parameter ever contains a
     * closing tag, those appended elements end up OUTSIDE the root element,
     * producing malformed XML that DOMParser rejects.  By stripping ALL
     * occurrences (not just the trailing one) we guarantee the parameter stays
     * clean regardless of how many save cycles have occurred.
     * </p>
     */
    protected static String sanitizeConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "");
    }

    /**
     * Produces valid, complete XML for storage in the database.
     * Strips every {@code </SF2QBConfiguration>} and appends exactly one.
     */
    protected static String sanitizeFullConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration></SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "") + CONFIG_CLOSE_TAG;
    }

    /**
     * Sets a private field on TransactionThread via reflection.
     * Needed because TransactionThread has getFirstName()/getLastName()/getCompany()/getTitle()
     * but no corresponding setters — only private fields.
     */
    protected void setThreadField(Object tt, String fieldName, String value) {
        try {
            Field f = tt.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(tt, value != null ? value : "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log("Could not set TransactionThread." + fieldName + " via reflection", e);
        }
    }
}
