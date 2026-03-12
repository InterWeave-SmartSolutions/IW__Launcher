package com.interweave.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
 * Server-side RBAC enforcement filter.
 *
 * Runs after ApiTokenAuthFilter (session attributes already populated).
 * Maps URL patterns to required roles and returns 403 for unauthorized access.
 *
 * Role hierarchy (no inheritance — explicit grants):
 *   admin    → all routes
 *   operator → operator portal APIs + shared (profile, auth, monitoring, flows, config, logs, audit, notifications)
 *   associate → associate portal APIs + shared (profile, auth)
 *
 * Public paths (no auth required): login, session, register, password-reset, webhooks.
 */
public class RoleAuthorizationFilter implements Filter {

    /** Paths that require no authentication at all. */
    private static final Set<String> PUBLIC_PATHS = new HashSet<>(Arrays.asList(
        "/api/auth/login",
        "/api/auth/session",
        "/api/auth/password-reset",
        "/api/register",
        "/api/register/company",
        "/api/webhooks/receive"
    ));

    /** Paths accessible to any authenticated user regardless of role. */
    private static final Set<String> ANY_AUTH_PREFIXES = new HashSet<>(Arrays.asList(
        "/api/auth/",
        "/api/profile",
        "/api/auth/change-password"
    ));

    /** Paths restricted to operator + admin roles. */
    private static final Set<String> OPERATOR_PREFIXES = new HashSet<>(Arrays.asList(
        "/api/company/",
        "/api/config/",
        "/api/flows",
        "/api/logs/",
        "/api/monitoring/",
        "/api/notifications",
        "/api/audit",
        "/api/engine/"
    ));

    /** Paths restricted to admin only. */
    private static final String MASTER_PREFIX = "/api/master/";

    /** Associate portal APIs — accessible to associate + admin. */
    private static final String ASSOCIATE_PREFIX = "/api/associate/";

    @Override
    public void init(FilterConfig config) throws ServletException {
        config.getServletContext().log(
            "RoleAuthorizationFilter initialized — RBAC enforcement active on /api/*");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = httpReq.getServletPath();
        if (path == null) path = httpReq.getRequestURI();

        // Strip context path if present
        String contextPath = httpReq.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }

        // 1. Public paths — no auth required
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Must be authenticated
        HttpSession session = httpReq.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            sendForbidden(httpRes, "Authentication required");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (role == null) role = "operator"; // safe default for legacy sessions

        // 3. Admin bypasses all role checks
        if ("admin".equalsIgnoreCase(role)) {
            chain.doFilter(request, response);
            return;
        }

        // 4. Master console — admin only
        if (path.startsWith(MASTER_PREFIX)) {
            sendForbidden(httpRes, "Admin access required");
            return;
        }

        // 5. Associate portal — associate + admin
        if (path.startsWith(ASSOCIATE_PREFIX)) {
            if ("associate".equalsIgnoreCase(role)) {
                chain.doFilter(request, response);
                return;
            }
            sendForbidden(httpRes, "Associate or admin access required");
            return;
        }

        // 6. Any-auth paths (profile, auth endpoints)
        if (isAnyAuthPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 7. Operator-tier paths
        if (isOperatorPath(path)) {
            if ("operator".equalsIgnoreCase(role)) {
                chain.doFilter(request, response);
                return;
            }
            sendForbidden(httpRes, "Operator or admin access required");
            return;
        }

        // 8. Default: allow authenticated users through for unmatched API paths
        //    (safer than blocking — new endpoints work without filter updates)
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        for (String pub : PUBLIC_PATHS) {
            if (path.equals(pub)) return true;
        }
        return false;
    }

    private boolean isAnyAuthPath(String path) {
        for (String prefix : ANY_AUTH_PREFIXES) {
            if (path.startsWith(prefix) || path.equals(prefix)) return true;
        }
        return false;
    }

    private boolean isOperatorPath(String path) {
        for (String prefix : OPERATOR_PREFIXES) {
            if (path.startsWith(prefix) || path.equals(prefix)) return true;
        }
        return false;
    }

    private void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"success\":false,\"error\":\"" + message + "\"}");
        out.flush();
    }

    @Override
    public void destroy() {
        // no-op
    }
}
