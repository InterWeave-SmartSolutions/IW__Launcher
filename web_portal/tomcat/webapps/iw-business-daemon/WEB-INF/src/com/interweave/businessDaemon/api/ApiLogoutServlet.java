package com.interweave.businessDaemon.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * JSON logout endpoint that properly invalidates the Tomcat session.
 * The original LogoutServlet only redirects without calling session.invalidate().
 *
 * POST /api/auth/logout  ->  {"success": true}
 * GET  /api/auth/logout   ->  {"success": true}
 */
public class ApiLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleLogout(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleLogout(req, resp);
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Remove the specific Bearer token from the request
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            ApiTokenStore.removeToken(token);
        }

        // Also remove ALL tokens for this user (covers multi-device/multi-tab)
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object email = session.getAttribute("userEmail");
            if (email != null) {
                ApiTokenStore.removeTokensByAttribute("userEmail", email);
            }
            session.invalidate();
        }

        PrintWriter out = resp.getWriter();
        out.print("{\"success\":true}");
        out.flush();
    }
}
