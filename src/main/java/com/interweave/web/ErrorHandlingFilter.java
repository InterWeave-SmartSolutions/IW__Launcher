package com.interweave.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.error.ErrorCode;
import com.interweave.error.ErrorDocumentation;
import com.interweave.error.ErrorLogger;
import com.interweave.error.IWError;

/**
 * ErrorHandlingFilter - Global error handling filter for the InterWeave web portal.
 *
 * This filter catches all uncaught exceptions that occur during request processing
 * and transforms them into structured error responses with proper logging.
 *
 * Features:
 * - Catches all uncaught exceptions from servlets and JSPs
 * - Logs full error details with transaction IDs and stack traces
 * - Differentiates between page requests and API requests
 * - Returns user-friendly error pages for browser requests
 * - Returns JSON error responses for API/AJAX requests
 * - Integrates with the IWError framework for consistent error handling
 *
 * The filter detects API requests by checking:
 * 1. Accept header contains "application/json"
 * 2. Content-Type header contains "application/json"
 * 3. Request URI starts with "/api/"
 * 4. Request parameter "_format=json" is present
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class ErrorHandlingFilter implements Filter {

    private FilterConfig filterConfig;

    /**
     * Initialize the filter
     *
     * @param config The filter configuration
     * @throws ServletException If initialization fails
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
        filterConfig.getServletContext().log("ErrorHandlingFilter initialized - global exception handling active");
    }

    /**
     * Process the request and catch any uncaught exceptions
     *
     * @param request The servlet request
     * @param response The servlet response
     * @param chain The filter chain
     * @throws IOException If an I/O error occurs
     * @throws ServletException If a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast to HTTP types for additional functionality
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Continue processing the request
            chain.doFilter(request, response);

        } catch (Throwable throwable) {
            // Catch all uncaught exceptions and handle them appropriately
            handleException(httpRequest, httpResponse, throwable);
        }
    }

    /**
     * Handle an uncaught exception by logging it and returning an appropriate error response
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param throwable The uncaught exception
     */
    private void handleException(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        try {
            // Determine the error code based on the exception type
            ErrorCode errorCode = determineErrorCode(throwable);

            // Extract component information from the stack trace
            String affectedComponent = extractAffectedComponent(throwable);

            // Build a structured error object
            IWError error = IWError.builder(errorCode)
                    .message(errorCode.getDefaultMessage())
                    .cause(throwable.getClass().getSimpleName() + ": " + throwable.getMessage(), throwable)
                    .affectedComponent(affectedComponent)
                    .suggestedResolution(ErrorDocumentation.getResolutionSteps(errorCode))
                    .documentationLink(ErrorDocumentation.getDocumentationLink(errorCode))
                    .build();

            // Log the error with full details including stack trace
            ErrorLogger.logError(error);

            // Determine if this is an API request or a page request
            boolean isApiRequest = isApiRequest(request);

            if (isApiRequest) {
                // Return JSON error response for API requests
                sendJsonErrorResponse(response, error);
            } else {
                // Forward to error page for browser requests
                forwardToErrorPage(request, response, error);
            }

        } catch (Exception e) {
            // If error handling itself fails, fall back to basic error response
            filterConfig.getServletContext().log("ErrorHandlingFilter: Error handling failed", e);
            sendFallbackErrorResponse(response);
        }
    }

    /**
     * Determine the appropriate error code based on the exception type
     *
     * @param throwable The exception
     * @return The error code
     */
    private ErrorCode determineErrorCode(Throwable throwable) {
        String exceptionType = throwable.getClass().getSimpleName();

        // Map common exception types to error codes
        if (exceptionType.contains("SQLException") || exceptionType.contains("Database")) {
            return ErrorCode.DB001;
        } else if (exceptionType.contains("NullPointer") || exceptionType.contains("IllegalArgument")) {
            return ErrorCode.SYSTEM001;
        } else if (exceptionType.contains("Timeout")) {
            return ErrorCode.SYSTEM005;
        } else if (exceptionType.contains("XPath") || exceptionType.contains("Transform")) {
            return ErrorCode.XPATH004;
        } else if (exceptionType.contains("Config") || exceptionType.contains("XML")) {
            return ErrorCode.CONFIG001;
        } else if (exceptionType.contains("IO") || exceptionType.contains("FileNotFound")) {
            return ErrorCode.SYSTEM003;
        } else {
            // Default to general internal server error
            return ErrorCode.SYSTEM001;
        }
    }

    /**
     * Extract the affected component from the exception stack trace
     *
     * @param throwable The exception
     * @return The name of the affected component
     */
    private String extractAffectedComponent(Throwable throwable) {
        if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
            StackTraceElement topElement = throwable.getStackTrace()[0];
            String className = topElement.getClassName();

            // Extract just the class name without package
            int lastDot = className.lastIndexOf('.');
            if (lastDot >= 0) {
                className = className.substring(lastDot + 1);
            }

            return className + "." + topElement.getMethodName() + "()";
        }
        return "Unknown Component";
    }

    /**
     * Determine if the request is an API request or a page request
     *
     * @param request The HTTP request
     * @return True if this is an API request, false otherwise
     */
    private boolean isApiRequest(HttpServletRequest request) {
        // Check Accept header
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }

        // Check Content-Type header
        String contentType = request.getHeader("Content-Type");
        if (contentType != null && contentType.contains("application/json")) {
            return true;
        }

        // Check URI pattern
        String uri = request.getRequestURI();
        if (uri != null && uri.contains("/api/")) {
            return true;
        }

        // Check request parameter
        String format = request.getParameter("_format");
        if ("json".equalsIgnoreCase(format)) {
            return true;
        }

        // Default to page request
        return false;
    }

    /**
     * Send a JSON error response for API requests
     *
     * @param response The HTTP response
     * @param error The error object
     * @throws IOException If writing the response fails
     */
    private void sendJsonErrorResponse(HttpServletResponse response, IWError error) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(error.toJSON());
        writer.flush();
    }

    /**
     * Forward to the error page for browser requests
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param error The error object
     * @throws IOException If forwarding fails
     * @throws ServletException If forwarding fails
     */
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response, IWError error)
            throws IOException, ServletException {

        // Set error attributes for the error page
        request.setAttribute("iwError", error);
        request.setAttribute("errorCode", error.getErrorCode().getCode());
        request.setAttribute("errorMessage", error.getMessage());
        request.setAttribute("errorCause", error.getCause());
        request.setAttribute("errorComponent", error.getAffectedComponent());
        request.setAttribute("errorResolution", error.getSuggestedResolution());
        request.setAttribute("errorDocLink", error.getDocumentationLink());
        request.setAttribute("transactionId", error.getTransactionId());

        // Set HTTP status code
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        // Forward to error page
        try {
            request.getRequestDispatcher("/ErrorMessage.jsp").forward(request, response);
        } catch (Exception e) {
            // If forwarding fails, send a basic error response
            filterConfig.getServletContext().log("ErrorHandlingFilter: Failed to forward to error page", e);
            sendFallbackErrorResponse(response);
        }
    }

    /**
     * Send a fallback error response if normal error handling fails
     *
     * @param response The HTTP response
     */
    private void sendFallbackErrorResponse(HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.println("<html><head><title>Error</title></head><body>");
            writer.println("<h1>Internal Server Error</h1>");
            writer.println("<p>An unexpected error occurred. Please contact support.</p>");
            writer.println("</body></html>");
            writer.flush();
        } catch (IOException e) {
            // Nothing more we can do
            filterConfig.getServletContext().log("ErrorHandlingFilter: Failed to send fallback error response", e);
        }
    }

    /**
     * Destroy the filter
     */
    @Override
    public void destroy() {
        filterConfig.getServletContext().log("ErrorHandlingFilter destroyed");
        filterConfig = null;
    }
}
