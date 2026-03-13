package com.interweave.businessDaemon.config;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.interweave.businessDaemon.api.ApiTokenStore;

/**
 * Replacement for the original compiled LogoutServlet which does NOT
 * call session.invalidate() — it only redirects to IWLogin.jsp.
 *
 * This version properly invalidates the Tomcat session AND clears all
 * Bearer tokens for the user from ApiTokenStore, ensuring that both
 * JSP and React UIs see the user as logged out.
 */
public class LocalLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleLogout(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleLogout(req, resp);
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            // Clear all Bearer tokens for this user BEFORE invalidating
            // (session attributes become inaccessible after invalidate())
            Object email = session.getAttribute("userEmail");
            if (email != null) {
                ApiTokenStore.removeTokensByAttribute("userEmail", email);
            }
            session.invalidate();
        }

        // Also clear any Bearer token sent in this request
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            ApiTokenStore.removeToken(authHeader.substring(7).trim());
        }

        // Redirect to login page (same behavior as the original LogoutServlet)
        resp.sendRedirect(req.getContextPath() + "/IWLogin.jsp");
    }
}
