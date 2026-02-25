package com.interweave.businessDaemon.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ApiSessionServlet - JSON API endpoint for session validation.
 *
 * Returns the current user's session state so the React IW Portal
 * can check authentication on page load without re-authenticating.
 * Shares the same Tomcat session set by ApiLoginServlet / LocalLoginServlet.
 *
 * API Endpoint: GET /api/auth/session
 *
 * Authenticated Response (200):
 * {
 *   "authenticated": true,
 *   "user": {
 *     "userId": "1",
 *     "userName": "John Doe",
 *     "email": "user@example.com",
 *     "companyId": 1,
 *     "companyName": "ACME Corp",
 *     "isAdmin": false,
 *     "role": "user",
 *     "solutionType": "QB"
 *   }
 * }
 *
 * Unauthenticated Response (200):
 * {"authenticated": false}
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiSessionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            sendJson(response, 200, "{\"authenticated\":false}");
            return;
        }

        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        if (authenticated == null || !authenticated) {
            sendJson(response, 200, "{\"authenticated\":false}");
            return;
        }

        // Session is authenticated — build user JSON from session attributes
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String userEmail = (String) session.getAttribute("userEmail");
        Integer companyId = (Integer) session.getAttribute("companyId");
        String companyName = (String) session.getAttribute("companyName");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        String role = (String) session.getAttribute("role");
        String solutionType = (String) session.getAttribute("solutionType");

        StringBuilder json = new StringBuilder();
        json.append("{\"authenticated\":true,\"user\":{");
        json.append("\"userId\":\"").append(userId != null ? userId : "").append("\",");
        json.append("\"userName\":\"").append(escapeJson(userName)).append("\",");
        json.append("\"email\":\"").append(escapeJson(userEmail)).append("\",");
        json.append("\"companyId\":").append(companyId != null ? companyId : "null").append(",");
        json.append("\"companyName\":");
        if (companyName != null) {
            json.append("\"").append(escapeJson(companyName)).append("\"");
        } else {
            json.append("null");
        }
        json.append(",");
        json.append("\"isAdmin\":").append(isAdmin != null ? isAdmin : false).append(",");
        json.append("\"role\":\"").append(escapeJson(role)).append("\",");
        json.append("\"solutionType\":\"").append(escapeJson(solutionType != null ? solutionType : "")).append("\"");
        json.append("}}");

        sendJson(response, 200, json.toString());
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendJson(HttpServletResponse response, int statusCode, String json) throws IOException {
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
