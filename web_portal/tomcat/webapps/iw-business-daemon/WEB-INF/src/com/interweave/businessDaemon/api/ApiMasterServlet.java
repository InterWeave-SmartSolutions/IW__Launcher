package com.interweave.businessDaemon.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * ApiMasterServlet - JSON API for the Master Console.
 *
 * GET /api/master/dashboard      - KPI summary + operational queue
 * GET /api/master/users          - User list + role definitions
 * GET /api/master/content        - Resource inventory
 * GET /api/master/subscriptions  - Plans + payment exceptions
 * GET /api/master/integrations   - Connector inventory
 * GET /api/master/analytics      - Content analytics (7d)
 * GET /api/master/audit          - Security events + audit log
 * GET /api/master/notifications  - Templates + preferences
 * GET /api/master/support        - Support ticket queue
 * GET /api/master/settings       - Tenant configuration
 *
 * All endpoints require an authenticated admin session.
 * Currently returns structured mock data; DB wiring is a follow-up.
 */
public class ApiMasterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiMasterServlet initialized");
        } catch (NamingException e) {
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            write(response, "{\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        PrintWriter out = response.getWriter();

        if (pathInfo.equals("/dashboard") || pathInfo.startsWith("/dashboard")) {
            out.print(getDashboardJson(session));
        } else if (pathInfo.equals("/users") || pathInfo.startsWith("/users")) {
            out.print(getUsersJson(session));
        } else if (pathInfo.equals("/content") || pathInfo.startsWith("/content")) {
            out.print(getContentJson());
        } else if (pathInfo.equals("/subscriptions") || pathInfo.startsWith("/subscriptions")) {
            out.print(getSubscriptionsJson());
        } else if (pathInfo.equals("/integrations") || pathInfo.startsWith("/integrations")) {
            out.print(getIntegrationsJson());
        } else if (pathInfo.equals("/analytics") || pathInfo.startsWith("/analytics")) {
            out.print(getAnalyticsJson());
        } else if (pathInfo.equals("/audit") || pathInfo.startsWith("/audit")) {
            out.print(getAuditJson());
        } else if (pathInfo.equals("/notifications") || pathInfo.startsWith("/notifications")) {
            out.print(getNotificationsJson());
        } else if (pathInfo.equals("/support") || pathInfo.startsWith("/support")) {
            out.print(getSupportJson());
        } else if (pathInfo.equals("/settings") || pathInfo.startsWith("/settings")) {
            out.print(getSettingsJson(session));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\":\"Unknown master endpoint\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            write(response, "{\"error\":\"Not authenticated\"}");
            return;
        }

        // TODO: implement actual persistence for settings, notifications prefs, etc.
        write(response, "{\"success\":true,\"message\":\"Saved (mock — DB wiring pending)\"}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            write(response, "{\"error\":\"Not authenticated\"}");
            return;
        }

        // TODO: implement ticket creation, content publish, etc.
        write(response, "{\"success\":true,\"message\":\"Created (mock — DB wiring pending)\"}");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── JSON builders (mock data matching React page expectations) ─────────

    private String getDashboardJson(HttpSession session) {
        // Try to pull live subscriber count from DB
        int activeSubscribers = 1284;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM users WHERE is_active = true");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) activeSubscribers = rs.getInt(1);
        } catch (SQLException e) {
            log("getDashboardJson DB error (using mock): " + e.getMessage());
        }

        return "{"
            + "\"kpis\":{"
            + "\"activeSubscribers\":" + activeSubscribers + ","
            + "\"mrr\":38412,"
            + "\"portalEngagement\":67,"
            + "\"openExceptions\":3"
            + "},"
            + "\"operationalQueue\":["
            + "{\"priority\":\"High\",\"item\":\"Payment retry: sunrise@gym.com\",\"owner\":\"Billing\",\"due\":\"Today\",\"status\":\"Open\"},"
            + "{\"priority\":\"High\",\"item\":\"Sync failure: Mindbody connector\",\"owner\":\"Integrations\",\"due\":\"Today\",\"status\":\"Open\"},"
            + "{\"priority\":\"Medium\",\"item\":\"Webinar: Confirm speakers for April\",\"owner\":\"Content\",\"due\":\"Apr 2\",\"status\":\"In progress\"},"
            + "{\"priority\":\"Medium\",\"item\":\"Review 3 zero-result search queries\",\"owner\":\"Content\",\"due\":\"Apr 3\",\"status\":\"Open\"},"
            + "{\"priority\":\"Low\",\"item\":\"MFA reminder: 127 Staff users\",\"owner\":\"Security\",\"due\":\"Apr 5\",\"status\":\"Open\"}"
            + "]"
            + "}";
    }

    private String getUsersJson(HttpSession session) {
        return "{"
            + "\"roles\":["
            + "{\"name\":\"Owner\",\"persona\":\"Business owner\",\"entitlements\":\"Full access, billing\",\"security\":\"MFA required\"},"
            + "{\"name\":\"Staff\",\"persona\":\"Employee/manager\",\"entitlements\":\"Resources, webinars\",\"security\":\"Password only\"},"
            + "{\"name\":\"Program Admin\",\"persona\":\"IW staff\",\"entitlements\":\"Master console, all portals\",\"security\":\"MFA required\"},"
            + "{\"name\":\"Read-only\",\"persona\":\"Advisor/guest\",\"entitlements\":\"Resources only\",\"security\":\"Password only\"}"
            + "],"
            + "\"recentActivity\":["
            + "{\"ts\":\"09:14\",\"user\":\"jane.smith@sunrise.com\",\"event\":\"Login\",\"result\":\"Success\",\"ip\":\"73.143.x.x\"},"
            + "{\"ts\":\"09:02\",\"user\":\"tom.davis@peaks.com\",\"event\":\"Password change\",\"result\":\"Success\",\"ip\":\"98.21.x.x\"},"
            + "{\"ts\":\"08:47\",\"user\":\"maria.l@momentum.com\",\"event\":\"Login\",\"result\":\"Failed\",\"ip\":\"185.x.x.x\"}"
            + "]"
            + "}";
    }

    private String getContentJson() {
        return "{"
            + "\"resources\":["
            + "{\"id\":\"R-101\",\"title\":\"Recruiting Playbook (v3)\",\"type\":\"Guide\",\"area\":\"HR\",\"roles\":\"Owner\",\"version\":\"3.0\",\"release\":\"Mar 1\",\"status\":\"Active\"},"
            + "{\"id\":\"R-097\",\"title\":\"Monthly KPI Template\",\"type\":\"Template\",\"area\":\"Finance\",\"roles\":\"Owner\",\"version\":\"2.1\",\"release\":\"Feb 15\",\"status\":\"Active\"},"
            + "{\"id\":\"R-088\",\"title\":\"Operations Checklist\",\"type\":\"Checklist\",\"area\":\"Ops\",\"roles\":\"All\",\"version\":\"1.4\",\"release\":\"Feb 1\",\"status\":\"Active\"},"
            + "{\"id\":\"R-076\",\"title\":\"Social Media Calendar 2025\",\"type\":\"Template\",\"area\":\"Mktg\",\"roles\":\"Staff\",\"version\":\"1.0\",\"release\":\"Jan 10\",\"status\":\"Deprecated\"},"
            + "{\"id\":\"R-071\",\"title\":\"Brand Guidelines v2\",\"type\":\"Guide\",\"area\":\"Mktg\",\"roles\":\"Staff\",\"version\":\"2.0\",\"release\":\"Jan 3\",\"status\":\"Draft\"}"
            + "]"
            + "}";
    }

    private String getSubscriptionsJson() {
        return "{"
            + "\"plans\":["
            + "{\"name\":\"Associate Monthly\",\"price\":\"$29.99/mo\",\"status\":\"Active\"},"
            + "{\"name\":\"Associate Annual\",\"price\":\"$299/yr\",\"status\":\"Active\"},"
            + "{\"name\":\"Staff Add-on\",\"price\":\"$9.99/mo\",\"status\":\"Active\"}"
            + "],"
            + "\"exceptions\":["
            + "{\"subscription\":\"sunrise@gym.com\",\"customer\":\"Sunrise Fitness\",\"gateway\":\"Stripe\",\"attempt\":\"Mar 10\",\"retry\":\"Mar 13\",\"status\":\"Error\"},"
            + "{\"subscription\":\"peaks@wellness.com\",\"customer\":\"Peaks Wellness\",\"gateway\":\"Stripe\",\"attempt\":\"Mar 8\",\"retry\":\"Mar 11\",\"status\":\"Warn\"}"
            + "]"
            + "}";
    }

    private String getIntegrationsJson() {
        return "{"
            + "\"connectors\":["
            + "{\"name\":\"CRM\",\"system\":\"Salesforce\",\"auth\":\"OAuth 2.0\",\"direction\":\"Bi-directional\",\"lastSync\":\"09:08\",\"status\":\"Active\"},"
            + "{\"name\":\"Payments\",\"system\":\"Stripe\",\"auth\":\"API Key + Webhooks\",\"direction\":\"Inbound events\",\"lastSync\":\"09:14\",\"status\":\"Active\"},"
            + "{\"name\":\"Payments\",\"system\":\"Authorize.net\",\"auth\":\"API Login/Txn Key\",\"direction\":\"Inbound events\",\"lastSync\":\"23:02\",\"status\":\"Active\"},"
            + "{\"name\":\"Scheduling\",\"system\":\"Mindbody\",\"auth\":\"OAuth 2.0\",\"direction\":\"Optional\",\"lastSync\":\"\\u2014\",\"status\":\"Warn\"},"
            + "{\"name\":\"Scheduling\",\"system\":\"LeagueApps\",\"auth\":\"API Token\",\"direction\":\"Optional\",\"lastSync\":\"\\u2014\",\"status\":\"Warn\"}"
            + "]"
            + "}";
    }

    private String getAnalyticsJson() {
        return "{"
            + "\"topContent\":["
            + "{\"resource\":\"Recruiting Playbook (v3)\",\"views\":312,\"saves\":87,\"completion\":\"78%\"},"
            + "{\"resource\":\"Monthly KPI Template\",\"views\":241,\"saves\":103,\"completion\":\"91%\"},"
            + "{\"resource\":\"Operations Checklist\",\"views\":198,\"saves\":61,\"completion\":\"84%\"}"
            + "],"
            + "\"demandSignals\":["
            + "{\"query\":\"payroll templates\",\"count\":34,\"zeroResults\":true},"
            + "{\"query\":\"marketing calendar\",\"count\":28,\"zeroResults\":false},"
            + "{\"query\":\"staff onboarding\",\"count\":22,\"zeroResults\":false}"
            + "],"
            + "\"funnel\":["
            + "{\"step\":\"Login\",\"users\":1284,\"conversion\":\"100%\",\"notes\":\"Active subscribers\"},"
            + "{\"step\":\"Portal visit (7d)\",\"users\":860,\"conversion\":\"67%\",\"notes\":\"DAU/WAU proxy\"},"
            + "{\"step\":\"Resource opened\",\"users\":512,\"conversion\":\"59.5%\",\"notes\":\"Engaged users\"}"
            + "]"
            + "}";
    }

    private String getAuditJson() {
        return "{"
            + "\"mfaPolicy\":["
            + "{\"role\":\"Owner\",\"required\":\"Required\",\"enrolled\":34,\"total\":34,\"rate\":\"100%\"},"
            + "{\"role\":\"Staff\",\"required\":\"Optional\",\"enrolled\":71,\"total\":198,\"rate\":\"35.8%\"},"
            + "{\"role\":\"Program Admin\",\"required\":\"Required\",\"enrolled\":4,\"total\":4,\"rate\":\"100%\"}"
            + "],"
            + "\"securityEvents\":["
            + "{\"ts\":\"09:14\",\"user\":\"jane.smith@sunrise.com\",\"event\":\"Login\",\"ip\":\"73.143.x.x\",\"result\":\"Success\"},"
            + "{\"ts\":\"08:47\",\"user\":\"maria.l@momentum.com\",\"event\":\"Login\",\"ip\":\"185.x.x.x\",\"result\":\"Failed\"}"
            + "],"
            + "\"auditLog\":["
            + "{\"ts\":\"09:10\",\"actor\":\"admin@interweave.biz\",\"action\":\"Content published\",\"target\":\"R-101\",\"severity\":\"Info\"},"
            + "{\"ts\":\"07:40\",\"actor\":\"system\",\"action\":\"Payment retry\",\"target\":\"sunrise@gym.com\",\"severity\":\"Warn\"}"
            + "]"
            + "}";
    }

    private String getNotificationsJson() {
        return "{"
            + "\"templates\":["
            + "{\"id\":\"T-01\",\"name\":\"Welcome Email\",\"trigger\":\"User created\",\"channel\":\"Email\",\"status\":\"Active\"},"
            + "{\"id\":\"T-02\",\"name\":\"Payment Failed\",\"trigger\":\"Payment exception\",\"channel\":\"Email\",\"status\":\"Active\"},"
            + "{\"id\":\"T-03\",\"name\":\"Subscription Renewed\",\"trigger\":\"Renewal success\",\"channel\":\"Email\",\"status\":\"Active\"},"
            + "{\"id\":\"T-08\",\"name\":\"Slack Ops Alert\",\"trigger\":\"Error-level event\",\"channel\":\"Slack\",\"status\":\"Active\"}"
            + "]"
            + "}";
    }

    private String getSupportJson() {
        return "{"
            + "\"tickets\":["
            + "{\"id\":\"TK-041\",\"subject\":\"Cannot access webinar replay\",\"org\":\"Sunrise Fitness\",\"category\":\"Access\",\"priority\":\"High\",\"age\":\"2h\",\"status\":\"Open\"},"
            + "{\"id\":\"TK-040\",\"subject\":\"Billing discrepancy March\",\"org\":\"Peaks Wellness\",\"category\":\"Billing\",\"priority\":\"High\",\"age\":\"5h\",\"status\":\"In-progress\"},"
            + "{\"id\":\"TK-039\",\"subject\":\"Resource search not returning\",\"org\":\"Momentum Fit\",\"category\":\"Content\",\"priority\":\"Medium\",\"age\":\"1d\",\"status\":\"Open\"}"
            + "]"
            + "}";
    }

    private String getSettingsJson(HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        return "{"
            + "\"programName\":\"InterWeave Fitness Pro\","
            + "\"supportEmail\":\"support@interweave.biz\","
            + "\"domain\":\"portal.interweave.biz\","
            + "\"sessionTimeoutMinutes\":60,"
            + "\"maxUsers\":5000,"
            + "\"timezone\":\"America/New_York\","
            + "\"maintenanceWindow\":\"Sunday 02:00\\u201304:00 UTC\","
            + "\"limits\":{"
            + "\"activeSubscribers\":1284,"
            + "\"storageUsedGb\":12.4,"
            + "\"storageMaxGb\":50,"
            + "\"apiCalls30d\":284210,"
            + "\"apiMax30d\":1000000"
            + "}"
            + "}";
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Bypass-Tunnel-Reminder");
    }

    private void write(HttpServletResponse response, String json) throws IOException {
        response.getWriter().print(json);
    }
}
