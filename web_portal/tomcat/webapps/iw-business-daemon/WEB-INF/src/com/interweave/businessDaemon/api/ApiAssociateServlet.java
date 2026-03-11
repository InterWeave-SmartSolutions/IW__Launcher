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
 * ApiAssociateServlet - JSON API for the Associate Portal.
 *
 * GET  /api/associate/home                - Home dashboard (quick actions, recent resources, upcoming webinars)
 * GET  /api/associate/resources           - Resource library (filterable)
 * GET  /api/associate/resources/search    - Search resources by keyword + filters
 * GET  /api/associate/webinars            - Upcoming + replay webinars
 * POST /api/associate/intake              - Submit business intake form
 * GET  /api/associate/support/tickets     - My support tickets
 * POST /api/associate/support/tickets     - Create a new support ticket
 * GET  /api/associate/billing             - Current plan + invoice history
 *
 * All endpoints require an authenticated session.
 * Currently returns structured mock data; DB wiring is a follow-up.
 */
public class ApiAssociateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiAssociateServlet initialized");
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

        if (pathInfo.equals("/home") || pathInfo.startsWith("/home")) {
            out.print(getHomeJson(session));
        } else if (pathInfo.startsWith("/resources/search")) {
            String q = request.getParameter("q");
            out.print(getSearchJson(q));
        } else if (pathInfo.equals("/resources") || pathInfo.startsWith("/resources")) {
            out.print(getResourcesJson());
        } else if (pathInfo.equals("/webinars") || pathInfo.startsWith("/webinars")) {
            out.print(getWebinarsJson());
        } else if (pathInfo.equals("/support/tickets") || pathInfo.startsWith("/support/tickets")) {
            out.print(getSupportJson());
        } else if (pathInfo.equals("/billing") || pathInfo.startsWith("/billing")) {
            out.print(getBillingJson(session));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\":\"Unknown associate endpoint\"}");
        }
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

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        if (pathInfo.equals("/intake") || pathInfo.startsWith("/intake")) {
            // TODO: persist intake form to DB (intake_submissions table)
            write(response, "{\"success\":true,\"message\":\"Intake submitted successfully (DB wiring pending)\"}");
        } else if (pathInfo.startsWith("/support/tickets")) {
            // TODO: persist ticket to DB (support_tickets table)
            write(response, "{\"success\":true,\"ticketId\":\"TK-042\",\"message\":\"Ticket created (DB wiring pending)\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            write(response, "{\"error\":\"Unknown associate endpoint\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── JSON builders ──────────────────────────────────────────────────────

    private String getHomeJson(HttpSession session) {
        String firstName = (String) session.getAttribute("firstName");
        if (firstName == null) firstName = "there";
        return "{"
            + "\"greeting\":\"Welcome back, " + escapeJson(firstName) + "\","
            + "\"subscriptionStatus\":\"Active\","
            + "\"plan\":\"Associate Annual\","
            + "\"renewalDate\":\"Mar 1, 2027\","
            + "\"recentResources\":["
            + "{\"id\":\"R-101\",\"title\":\"Recruiting Playbook (v3)\",\"type\":\"Guide\",\"area\":\"HR\",\"savedAt\":\"2h ago\"},"
            + "{\"id\":\"R-097\",\"title\":\"Monthly KPI Template\",\"type\":\"Template\",\"area\":\"Finance\",\"savedAt\":\"1d ago\"},"
            + "{\"id\":\"R-088\",\"title\":\"Operations Checklist\",\"type\":\"Checklist\",\"area\":\"Ops\",\"savedAt\":\"3d ago\"}"
            + "],"
            + "\"upcomingWebinars\":["
            + "{\"title\":\"Q2 Finance Planning Workshop\",\"date\":\"Apr 3, 2025\",\"time\":\"2:00 PM ET\",\"spots\":12},"
            + "{\"title\":\"Staff Retention Strategies\",\"date\":\"Apr 10, 2025\",\"time\":\"11:00 AM ET\",\"spots\":28}"
            + "]"
            + "}";
    }

    private String getResourcesJson() {
        return "{"
            + "\"resources\":["
            + "{\"id\":\"R-101\",\"title\":\"Recruiting Playbook (v3)\",\"type\":\"Guide\",\"area\":\"HR\",\"roles\":\"Owner\",\"version\":\"3.0\",\"release\":\"Mar 1\",\"saved\":true},"
            + "{\"id\":\"R-097\",\"title\":\"Monthly KPI Template\",\"type\":\"Template\",\"area\":\"Finance\",\"roles\":\"Owner\",\"version\":\"2.1\",\"release\":\"Feb 15\",\"saved\":true},"
            + "{\"id\":\"R-088\",\"title\":\"Operations Checklist\",\"type\":\"Checklist\",\"area\":\"Ops\",\"roles\":\"All\",\"version\":\"1.4\",\"release\":\"Feb 1\",\"saved\":false},"
            + "{\"id\":\"R-082\",\"title\":\"Cash Flow Projection\",\"type\":\"Template\",\"area\":\"Finance\",\"roles\":\"Owner\",\"version\":\"1.2\",\"release\":\"Jan 20\",\"saved\":false},"
            + "{\"id\":\"R-076\",\"title\":\"Social Media Calendar 2025\",\"type\":\"Template\",\"area\":\"Mktg\",\"roles\":\"Staff\",\"version\":\"1.0\",\"release\":\"Jan 10\",\"saved\":false},"
            + "{\"id\":\"R-071\",\"title\":\"Brand Guidelines v2\",\"type\":\"Guide\",\"area\":\"Mktg\",\"roles\":\"Staff\",\"version\":\"2.0\",\"release\":\"Jan 3\",\"saved\":false}"
            + "],"
            + "\"areas\":[\"HR\",\"Finance\",\"Ops\",\"Mktg\"],"
            + "\"types\":[\"Guide\",\"Template\",\"Checklist\"],"
            + "\"roles\":[\"All\",\"Owner\",\"Staff\"]"
            + "}";
    }

    private String getSearchJson(String query) {
        String safeQuery = query != null ? escapeJson(query) : "";
        return "{"
            + "\"query\":\"" + safeQuery + "\","
            + "\"results\":["
            + "{\"id\":\"R-101\",\"title\":\"Recruiting Playbook (v3)\",\"type\":\"Guide\",\"area\":\"HR\",\"relevance\":\"High\"},"
            + "{\"id\":\"R-097\",\"title\":\"Monthly KPI Template\",\"type\":\"Template\",\"area\":\"Finance\",\"relevance\":\"Medium\"}"
            + "],"
            + "\"total\":2"
            + "}";
    }

    private String getWebinarsJson() {
        return "{"
            + "\"upcoming\":["
            + "{\"id\":\"W-08\",\"title\":\"Q2 Finance Planning Workshop\",\"date\":\"Apr 3, 2025\",\"time\":\"2:00 PM ET\",\"duration\":\"60 min\",\"spots\":12,\"registered\":false},"
            + "{\"id\":\"W-09\",\"title\":\"Staff Retention Strategies\",\"date\":\"Apr 10, 2025\",\"time\":\"11:00 AM ET\",\"duration\":\"45 min\",\"spots\":28,\"registered\":false},"
            + "{\"id\":\"W-10\",\"title\":\"Spring Marketing Playbook\",\"date\":\"Apr 17, 2025\",\"time\":\"1:00 PM ET\",\"duration\":\"60 min\",\"spots\":35,\"registered\":false}"
            + "],"
            + "\"replays\":["
            + "{\"id\":\"W-05\",\"title\":\"Operations Efficiency: Q1 Review\",\"date\":\"Mar 6, 2025\",\"duration\":\"58 min\",\"views\":142},"
            + "{\"id\":\"W-04\",\"title\":\"Hiring in a Tight Market\",\"date\":\"Feb 20, 2025\",\"duration\":\"51 min\",\"views\":89},"
            + "{\"id\":\"W-03\",\"title\":\"2025 Financial Planning Kickoff\",\"date\":\"Jan 16, 2025\",\"duration\":\"62 min\",\"views\":213}"
            + "]"
            + "}";
    }

    private String getSupportJson() {
        return "{"
            + "\"tickets\":["
            + "{\"id\":\"TK-041\",\"subject\":\"Cannot access webinar replay\",\"category\":\"Access\",\"priority\":\"High\",\"createdAt\":\"Mar 11, 2025\",\"status\":\"Open\"},"
            + "{\"id\":\"TK-037\",\"subject\":\"Invoice question for February\",\"category\":\"Billing\",\"priority\":\"Medium\",\"createdAt\":\"Mar 5, 2025\",\"status\":\"Resolved\"}"
            + "]"
            + "}";
    }

    private String getBillingJson(HttpSession session) {
        return "{"
            + "\"plan\":{\"name\":\"Associate Annual\",\"price\":\"$299/yr\",\"status\":\"Active\",\"renewalDate\":\"Mar 1, 2027\"},"
            + "\"invoices\":["
            + "{\"id\":\"INV-2025-03\",\"date\":\"Mar 1, 2025\",\"amount\":\"$299.00\",\"status\":\"Paid\"},"
            + "{\"id\":\"INV-2024-03\",\"date\":\"Mar 1, 2024\",\"amount\":\"$299.00\",\"status\":\"Paid\"},"
            + "{\"id\":\"INV-2023-03\",\"date\":\"Mar 1, 2023\",\"amount\":\"$279.00\",\"status\":\"Paid\"}"
            + "],"
            + "\"paymentMethod\":{\"type\":\"Visa\",\"last4\":\"4242\",\"expires\":\"12/26\"}"
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

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}
