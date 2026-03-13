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
 * - Content-Security-Policy: permissive baseline (supports both legacy JSPs with inline scripts
 *   and modern React portal; allows same-origin iframes, trusted sources for external JS/CSS)
 *
 * Note: CSP uses 'unsafe-inline' for script-src to support legacy JSP inline scripts.
 * The React portal (iw-portal) can add stricter meta CSP directives if needed.
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

            // Content Security Policy — now that inline scripts are extracted to external .js files,
            // we can use a strict policy without 'unsafe-inline' for script-src.
            // style-src still needs 'unsafe-inline' for legacy JSP inline styles.
            // cdn.jsdelivr.net is needed for Chart.js on monitoring Dashboard.jsp.
            String csp = "default-src 'self'; "
                + "script-src 'self' https://cdn.jsdelivr.net; "
                + "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; "
                + "font-src 'self' https://fonts.gstatic.com; "
                + "img-src 'self' data: https:; "
                + "frame-src 'self'; "
                + "connect-src 'self'; "
                + "object-src 'none'; "
                + "base-uri 'self'; "
                + "form-action 'self'";
            httpResponse.setHeader("Content-Security-Policy", csp);

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
