package com.interweave.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds standard security headers to all HTTP responses.
 *
 * Headers set:
 * - X-Frame-Options: SAMEORIGIN (allows framesets within same origin — required by IMConfig.jsp)
 * - X-Content-Type-Options: nosniff (prevents MIME type sniffing)
 * - X-XSS-Protection: 1; mode=block (legacy browser XSS filter)
 * - Referrer-Policy: strict-origin-when-cross-origin
 * - Permissions-Policy: restricts browser features
 *
 * Note: Content-Security-Policy is NOT set here because legacy JSPs use inline
 * scripts and eval(). Enable CSP only after auditing all inline JS usage.
 */
public class SecurityHeadersFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        config.getServletContext().log("SecurityHeadersFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // SAMEORIGIN instead of DENY — IMConfig.jsp uses framesets to load BDConfigurator.jsp
            httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");

            // Prevent browsers from MIME-sniffing the content type
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");

            // Legacy XSS protection for older browsers
            httpResponse.setHeader("X-XSS-Protection", "1; mode=block");

            // Control referrer information sent with requests
            httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

            // Restrict browser features the app doesn't need
            httpResponse.setHeader("Permissions-Policy",
                "geolocation=(), camera=(), microphone=(), payment=()");

            // Cache control for authenticated pages — don't cache sensitive data
            // (static assets like CSS/JS/images are served by Tomcat's DefaultServlet
            // which sets its own cache headers, so this primarily affects JSP/servlet responses)
            httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
