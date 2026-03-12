package com.interweave.businessDaemon.api;

import java.io.IOException;
import java.util.Map;
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
 * Servlet filter that bridges Bearer-token authentication to Tomcat sessions.
 *
 * When a request includes an {@code Authorization: Bearer <token>} header
 * with a valid token from {@link ApiTokenStore}, this filter creates an
 * HttpSession and populates it with the stored user attributes. Downstream
 * servlets see a normal authenticated session — no changes needed.
 *
 * This solves the Vercel/proxy deployment problem where session cookies
 * don't survive the server-side rewrite proxy.
 *
 * Mapped to {@code /api/*} in web.xml.
 */
public class ApiTokenAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().log("ApiTokenAuthFilter initialized — token-to-session bridge active");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        // Check for Bearer token in Authorization header
        String token = null;
        String authHeader = httpReq.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
        }

        // Fallback: check query parameter (for SSE/EventSource which can't set headers)
        if (token == null || token.isEmpty()) {
            token = httpReq.getParameter("token");
        }

        if (token != null && !token.isEmpty()) {
            ApiTokenStore.TokenEntry entry = ApiTokenStore.getToken(token);
            if (entry != null) {
                // Token is valid — populate session with stored user attributes
                HttpSession session = httpReq.getSession(true);
                for (Map.Entry<String, Object> attr : entry.attributes.entrySet()) {
                    session.setAttribute(attr.getKey(), attr.getValue());
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
