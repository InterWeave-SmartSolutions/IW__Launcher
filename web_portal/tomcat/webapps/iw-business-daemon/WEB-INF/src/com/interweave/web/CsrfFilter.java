package com.interweave.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * CSRF protection filter for JSP form submissions.
 *
 * Generates a per-session CSRF token and validates it on state-changing
 * HTTP methods (POST, PUT, DELETE, PATCH).
 *
 * Exclusions:
 *   - /api/* endpoints are EXCLUDED (they use Bearer token auth, not cookies)
 *   - GET/HEAD/OPTIONS requests are EXCLUDED (safe methods)
 *
 * JSPs must include the token in forms:
 *   <input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
 *
 * The token is also accepted via the X-CSRF-Token header (for AJAX calls).
 */
public class CsrfFilter implements Filter {

    private static final String CSRF_TOKEN_ATTR = "_csrf";
    private static final String CSRF_HEADER = "X-CSRF-Token";
    private static final int TOKEN_BYTES = 32;

    private final SecureRandom random = new SecureRandom();

    @Override
    public void init(FilterConfig config) throws ServletException {
        config.getServletContext().log("CsrfFilter initialized — protecting JSP form submissions");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = httpReq.getRequestURI();
        String contextPath = httpReq.getContextPath();
        String relativePath = path.substring(contextPath.length());

        // Skip API endpoints — they use Bearer token auth, not session cookies
        if (relativePath.startsWith("/api/")) {
            chain.doFilter(request, response);
            return;
        }

        // Ensure a CSRF token exists in the session
        HttpSession session = httpReq.getSession(true);
        String token = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        if (token == null) {
            token = generateToken();
            session.setAttribute(CSRF_TOKEN_ATTR, token);
        }

        // For safe methods, just ensure the token is in the session and continue
        String method = httpReq.getMethod().toUpperCase();
        if ("GET".equals(method) || "HEAD".equals(method) || "OPTIONS".equals(method)) {
            chain.doFilter(request, response);
            return;
        }

        // For state-changing methods (POST, PUT, DELETE, PATCH), validate the token
        String submittedToken = httpReq.getParameter(CSRF_TOKEN_ATTR);
        if (submittedToken == null || submittedToken.isEmpty()) {
            submittedToken = httpReq.getHeader(CSRF_HEADER);
        }

        if (submittedToken == null || !token.equals(submittedToken)) {
            // CSRF validation failed
            httpRes.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // Check if this is likely a JSON request
            String accept = httpReq.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                httpRes.setContentType("application/json");
                httpRes.setCharacterEncoding("UTF-8");
                PrintWriter out = httpRes.getWriter();
                out.print("{\"success\":false,\"error\":\"CSRF token validation failed\"}");
                out.flush();
            } else {
                // Redirect to login page with error for HTML form submissions
                httpRes.sendRedirect(contextPath + "/IWLogin.jsp?error=Session+expired.+Please+log+in+again.");
            }
            return;
        }

        chain.doFilter(request, response);
    }

    private String generateToken() {
        byte[] bytes = new byte[TOKEN_BYTES];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public void destroy() {}
}
