package com.interweave.monitoring.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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
 * MonitoringApiServlet - Base class for monitoring REST API servlets.
 *
 * Provides common functionality for all monitoring API endpoints including:
 * - JSON response handling with proper content type
 * - Session-based authentication validation
 * - Database connection management via JNDI DataSource
 * - Utility methods for parsing request parameters
 * - Structured error response generation with proper HTTP status codes
 * - Transaction ID tracking for error correlation
 *
 * All monitoring API servlets should extend this class and implement
 * the processRequest() method to handle their specific logic.
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public abstract class MonitoringApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("MonitoringApiServlet initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            log("Failed to initialize DataSource", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    /**
     * Main request handler that validates authentication and delegates to processRequest()
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Add CORS headers for API access
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Handle OPTIONS preflight request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            // Validate user session
            if (!isAuthenticated(request)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "AUTH004", "Session expired. Please log in again.");
                return;
            }

            // Get database connection and process request
            try (Connection conn = dataSource.getConnection()) {
                processRequest(request, response, conn);
            } catch (SQLException e) {
                System.err.println("Database error in " + this.getClass().getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();

                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "DB001", "A database error occurred. Please try again.");
            }

        } catch (Exception e) {
            System.err.println("Unexpected error in " + this.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();

            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "SYSTEM001", "An unexpected error occurred. Please try again.");
        }
    }

    /**
     * Abstract method to be implemented by subclasses for request processing.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param conn Database connection for the request
     * @throws ServletException If a servlet error occurs
     * @throws IOException If an I/O error occurs
     * @throws SQLException If a database error occurs
     */
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response,
                                          Connection conn)
            throws ServletException, IOException, SQLException;

    /**
     * Validates that the user has an active authenticated session
     *
     * @param request The HTTP request
     * @return true if user is authenticated, false otherwise
     */
    protected boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        Integer userId = (Integer) session.getAttribute("userId");

        return authenticated != null && authenticated && userId != null;
    }

    /**
     * Gets the authenticated user ID from session
     *
     * @param request The HTTP request
     * @return The user ID, or null if not authenticated
     */
    protected Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Integer) session.getAttribute("userId");
        }
        return null;
    }

    /**
     * Gets the authenticated user's company ID from session
     *
     * @param request The HTTP request
     * @return The company ID, or null if not authenticated
     */
    protected Integer getCompanyId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Integer) session.getAttribute("companyId");
        }
        return null;
    }

    /**
     * Checks if the authenticated user is an admin
     *
     * @param request The HTTP request
     * @return true if user is admin, false otherwise
     */
    protected boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            return isAdmin != null && isAdmin;
        }
        return false;
    }

    /**
     * Parses a string parameter from the request
     *
     * @param request The HTTP request
     * @param paramName The parameter name
     * @param defaultValue The default value if parameter is not present
     * @return The parameter value or default value
     */
    protected String getStringParameter(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        return (value != null && !value.trim().isEmpty()) ? value.trim() : defaultValue;
    }

    /**
     * Parses an integer parameter from the request
     *
     * @param request The HTTP request
     * @param paramName The parameter name
     * @param defaultValue The default value if parameter is not present or invalid
     * @return The parameter value or default value
     */
    protected int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String value = request.getParameter(paramName);
        if (value != null && !value.trim().isEmpty()) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                log("Invalid integer parameter '" + paramName + "': " + value);
            }
        }
        return defaultValue;
    }

    /**
     * Parses a long parameter from the request
     *
     * @param request The HTTP request
     * @param paramName The parameter name
     * @param defaultValue The default value if parameter is not present or invalid
     * @return The parameter value or default value
     */
    protected long getLongParameter(HttpServletRequest request, String paramName, long defaultValue) {
        String value = request.getParameter(paramName);
        if (value != null && !value.trim().isEmpty()) {
            try {
                return Long.parseLong(value.trim());
            } catch (NumberFormatException e) {
                log("Invalid long parameter '" + paramName + "': " + value);
            }
        }
        return defaultValue;
    }

    /**
     * Parses a boolean parameter from the request
     *
     * @param request The HTTP request
     * @param paramName The parameter name
     * @param defaultValue The default value if parameter is not present
     * @return The parameter value or default value
     */
    protected boolean getBooleanParameter(HttpServletRequest request, String paramName, boolean defaultValue) {
        String value = request.getParameter(paramName);
        if (value != null && !value.trim().isEmpty()) {
            return "true".equalsIgnoreCase(value.trim()) || "1".equals(value.trim());
        }
        return defaultValue;
    }

    /**
     * Sends a successful JSON response
     *
     * @param response The HTTP response
     * @param json The JSON string to send
     * @throws IOException If an I/O error occurs
     */
    protected void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    /**
     * Sends a successful JSON response with custom status code
     *
     * @param response The HTTP response
     * @param statusCode The HTTP status code
     * @param json The JSON string to send
     * @throws IOException If an I/O error occurs
     */
    protected void sendJsonResponse(HttpServletResponse response, int statusCode, String json) throws IOException {
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    /**
     * Sends an error response with structured error information
     *
     * @param response The HTTP response
     * @param statusCode The HTTP status code
     * @param errorCode The error code
     * @param message The error message
     * @throws IOException If an I/O error occurs
     */
    protected void sendErrorResponse(HttpServletResponse response, int statusCode,
                                     String errorCode, String message) throws IOException {
        response.setStatus(statusCode);

        PrintWriter out = response.getWriter();
        out.print(buildErrorJson(errorCode, message));
        out.flush();
    }

    /**
     * Sends a validation error response
     *
     * @param response The HTTP response
     * @param message The validation error message
     * @throws IOException If an I/O error occurs
     */
    protected void sendValidationError(HttpServletResponse response, String message) throws IOException {
        sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST,
            "VALIDATION001", message);
    }

    /**
     * Builds a simple JSON error response
     *
     * @param error The IWError object
     * @return JSON error response string
     */
    private String buildErrorJson(String errorCode, String message) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"success\":false,");
        json.append("\"error\":{");
        json.append("\"code\":\"").append(escapeJson(errorCode)).append("\",");
        json.append("\"message\":\"").append(escapeJson(message)).append("\"");
        json.append("}");
        json.append("}");
        return json.toString();
    }

    /**
     * Escapes special characters for JSON strings
     *
     * @param str The string to escape
     * @return Escaped string safe for JSON
     */
    protected String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /**
     * Builds a JSON success response wrapper
     *
     * @param data The data JSON string (without outer braces)
     * @return Complete JSON response with success flag
     */
    protected String buildSuccessJson(String data) {
        return "{\"success\":true," + data + "}";
    }
}
