package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.lang.reflect.Field;
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

import com.interweave.web.PasswordHasher;

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

    /**
     * Hash a password using bcrypt (delegates to PasswordHasher).
     */
    protected String hashPassword(String password) {
        return PasswordHasher.hash(password);
    }

    /**
     * Verify a password against a stored hash.
     * Supports bcrypt, SHA-256 hex, and plain text (delegates to PasswordHasher).
     */
    protected boolean verifyPassword(String input, String storedHash) {
        return PasswordHasher.verify(input, storedHash);
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
     * The legacy closing root-tag (kept for backward compatibility with JSPs).
     */
    protected static final String CONFIG_CLOSE_TAG = "</SF2QBConfiguration>";

    /**
     * Detects the root element name from configuration XML.  The root element
     * varies by solution type (SF2QBConfiguration, SF2NSConfiguration,
     * CRM2MG2Configuration, etc.).  Returns the tag name or "SF2QBConfiguration"
     * as fallback.
     */
    private static String detectRootElement(String xml) {
        if (xml == null) return "SF2QBConfiguration";
        int open = xml.indexOf('<');
        if (open < 0) return "SF2QBConfiguration";
        // Skip XML declaration <?xml ... ?>
        if (xml.length() > open + 1 && xml.charAt(open + 1) == '?') {
            open = xml.indexOf('<', xml.indexOf("?>", open) + 2);
            if (open < 0) return "SF2QBConfiguration";
        }
        int end = open + 1;
        while (end < xml.length() && xml.charAt(end) != '>' && xml.charAt(end) != ' '
                && xml.charAt(end) != '/' && xml.charAt(end) != '\n') {
            end++;
        }
        String tag = xml.substring(open + 1, end).trim();
        return tag.isEmpty() ? "SF2QBConfiguration" : tag;
    }

    /**
     * Removes ALL closing root-tags from the XML (solution-type-aware).
     * Handles both the detected root element and the legacy SF2QBConfiguration.
     */
    protected static String sanitizeConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration>";
        String root = detectRootElement(xml);
        String result = xml.replace("</" + root + ">", "");
        if (!"SF2QBConfiguration".equals(root)) {
            result = result.replace(CONFIG_CLOSE_TAG, "");
        }
        return result;
    }

    /**
     * Produces valid, complete XML for storage/parsing.  Strips all closing
     * root-tags and appends exactly one matching the detected root element.
     * Works with any solution type (SF2QB, SF2NS, CRM2MG2, etc.).
     */
    protected static String sanitizeFullConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration></SF2QBConfiguration>";
        String root = detectRootElement(xml);
        String closeTag = "</" + root + ">";
        String result = xml.replace(closeTag, "");
        if (!"SF2QBConfiguration".equals(root)) {
            result = result.replace(CONFIG_CLOSE_TAG, "");
        }
        return result + closeTag;
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
