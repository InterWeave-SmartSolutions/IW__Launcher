package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;
import com.interweave.businessDaemon.QueryContext;
import com.interweave.businessDaemon.TransactionBase;
import com.interweave.businessDaemon.HostedTransactionBase;

/**
 * ApiFlowManagementServlet - JSON API for integration flow management.
 *
 * Wraps ConfigContext to provide flow listing, start/stop controls,
 * and schedule management for the React Integration Manager page.
 *
 * API Endpoints:
 *   GET  /api/flows             - List all flows with state
 *   POST /api/flows/start       - Start a flow  (body: {"flowIndex":0,"profileName":"..."})
 *   POST /api/flows/stop        - Stop a flow   (body: {"flowIndex":0,"profileName":"..."})
 *   PUT  /api/flows/schedule    - Update schedule (body: {"flowIndex":0,"profileName":"...","interval":300,"shift":0,"counter":0})
 *   POST /api/flows/submit      - Submit all (mirrors form POST to ProductDemoServlet)
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiFlowManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String profileName = getProfileName(session);
        if (profileName == null || profileName.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"No profile context. Login via classic portal to initialize flows.\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if ("/properties".equals(pathInfo)) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            handleGetProperties(request, response, profileName, isAdmin != null && isAdmin);
            return;
        }

        try {
            PrintWriter out = response.getWriter();
            out.write("{\"success\":true,\"data\":{");

            // Server info
            out.write("\"serverName\":\"" + escapeJson(ConfigContext.getName()) + "\",");
            out.write("\"heartbeatInterval\":" + ConfigContext.getHartbeatInterval() + ",");
            out.write("\"isHosted\":" + ConfigContext.isHosted() + ",");
            out.write("\"profileName\":\"" + escapeJson(profileName) + "\",");

            // Transaction flows (scheduled)
            out.write("\"scheduledFlows\":[");
            boolean firstScheduled = true;
            Vector<TransactionContext> txList = ConfigContext.getTransactionList();
            for (int i = 0; i < txList.size(); i++) {
                TransactionContext tc = txList.get(i);
                if (!tc.isStateful()) continue;

                Hashtable<String, TransactionThread> threads = tc.getTransactionThreads();
                TransactionThread tt = threads.get(profileName);
                if (tt == null || tt.getLogLevel() == -1111) continue;

                if (!firstScheduled) out.write(",");
                firstScheduled = false;
                writeFlowJson(out, tc, tt, i, profileName);
            }
            out.write("],");

            // Utility flows (non-scheduled)
            out.write("\"utilityFlows\":[");
            boolean firstUtility = true;
            for (int i = 0; i < txList.size(); i++) {
                TransactionContext tc = txList.get(i);
                if (tc.isStateful()) continue;

                Hashtable<String, TransactionThread> threads = tc.getTransactionThreads();
                TransactionThread tt = threads.get(profileName);
                if (tt == null) continue;

                if (!firstUtility) out.write(",");
                firstUtility = false;
                writeFlowJson(out, tc, tt, i, profileName);
            }
            out.write("],");

            // Query flows
            out.write("\"queryFlows\":[");
            boolean firstQuery = true;
            Vector<QueryContext> queryList = ConfigContext.getQueryList();
            for (int i = 0; i < queryList.size(); i++) {
                QueryContext qc = queryList.get(i);
                Hashtable att = qc.getQueryInstances();
                if (att == null || !att.containsKey(profileName)) continue;

                if (!firstQuery) out.write(",");
                firstQuery = false;
                out.write("{\"index\":" + i);
                out.write(",\"flowId\":\"" + escapeJson(qc.getTransactionId()) + "\"");
                out.write(",\"type\":\"query\"");
                TransactionBase tb = (TransactionBase) att.get(profileName);
                if (tb != null) {
                    out.write(",\"interval\":" + tb.getTransactionInterval());
                    out.write(",\"counter\":" + tb.getTransactionCounter());
                }
                try {
                    String httpGetQuery = qc.getHTTPGetQuery(profileName);
                    if (httpGetQuery != null && !httpGetQuery.isEmpty()) {
                        out.write(",\"httpGetQuery\":\"" + escapeJson(httpGetQuery) + "\"");
                    }
                } catch (Exception e) { /* method may not exist in some builds */ }
                out.write("}");
            }
            out.write("]");

            out.write("}}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String profileName = getProfileName(session);
        if (profileName == null || profileName.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"No profile context\"}");
            return;
        }

        // Check admin
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.setStatus(403);
            response.getWriter().write("{\"success\":false,\"error\":\"Admin access required\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        try {
            String body = readBody(request);

            if (pathInfo.equals("/start")) {
                handleStartStop(body, profileName, true, response);
            } else if (pathInfo.equals("/stop")) {
                handleStartStop(body, profileName, false, response);
            } else if (pathInfo.equals("/submit")) {
                handleSubmitAll(body, profileName, response);
            } else {
                response.setStatus(404);
                response.getWriter().write("{\"success\":false,\"error\":\"Unknown action: " + escapeJson(pathInfo) + "\"}");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String profileName = getProfileName(session);
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.setStatus(403);
            response.getWriter().write("{\"success\":false,\"error\":\"Admin access required\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if ("/schedule".equals(pathInfo)) {
            try {
                String body = readBody(request);
                handleScheduleUpdate(body, profileName, response);
            } catch (Exception e) {
                response.setStatus(500);
                response.getWriter().write("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            }
        } else {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Unknown path\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }

    // ── Properties endpoint ────────────────────────────────

    private void handleGetProperties(HttpServletRequest request, HttpServletResponse response,
                                     String profileName, boolean isAdmin) throws IOException {
        String flowId = request.getParameter("flowId");
        String isFlowStr = request.getParameter("isFlow"); // "1" for transaction, "0" for query
        if (flowId == null || flowId.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"flowId parameter required\"}");
            return;
        }
        if (isFlowStr == null) isFlowStr = "1";
        boolean isFlow = "1".equals(isFlowStr);

        try {
            // Get description from the flow context
            String description = "";
            try {
                HostedTransactionBase tc = isFlow
                        ? ConfigContext.getTransactionContxtById(flowId)
                        : ConfigContext.getQueryContxtById(flowId);
                if (tc != null) {
                    description = tc.getDescription();
                    if (description == null) description = "";
                }
            } catch (Exception e) { /* context may not be loaded */ }

            // Get variable parameters
            Properties params = new Properties();
            boolean running = false;
            String debugError = null;
            try {
                running = ConfigContext.getVariableParameters(flowId, profileName, isFlowStr, isAdmin, params);
            } catch (Exception e) {
                debugError = e.getClass().getSimpleName() + ": " + e.getMessage();
                log("getVariableParameters failed for flowId=" + flowId + " profile=" + profileName + " isFlow=" + isFlowStr, e);
            }

            PrintWriter out = response.getWriter();
            out.write("{\"success\":true,\"data\":{");
            out.write("\"flowId\":\"" + escapeJson(flowId) + "\"");
            out.write(",\"isFlow\":" + isFlow);
            out.write(",\"description\":\"" + escapeJson(description) + "\"");
            out.write(",\"running\":" + running);
            out.write(",\"profileName\":\"" + escapeJson(profileName) + "\"");
            if (debugError != null) {
                out.write(",\"debugError\":\"" + escapeJson(debugError) + "\"");
            }

            // Variable parameters
            out.write(",\"properties\":[");
            if (params != null) {
                boolean first = true;
                Iterator<?> keys = params.keySet().iterator();
                while (keys.hasNext()) {
                    String name = (String) keys.next();
                    String value = params.getProperty(name);

                    boolean isPassword = name.startsWith("__%p%__");
                    boolean isUpload = name.startsWith("__%u%__");
                    String displayName = (isPassword || isUpload) ? name.substring(7) : name;

                    if (!first) out.write(",");
                    first = false;
                    out.write("{\"name\":\"" + escapeJson(displayName) + "\"");
                    out.write(",\"value\":\"" + escapeJson(value) + "\"");
                    out.write(",\"type\":\"" + (isPassword ? "password" : (isUpload ? "upload" : "text")) + "\"");
                    out.write("}");
                }
            }
            out.write("]");

            // Admin-only: Transformation Server URLs
            if (isAdmin && isFlow) {
                try {
                    HostedTransactionBase tc = ConfigContext.getTransactionContxtById(flowId);
                    if (tc != null) {
                        out.write(",\"tsUrls\":{");
                        out.write("\"U1\":\"" + escapeJson(str(tc.getPrimaryTransformationServerURL())) + "\"");
                        out.write(",\"U2\":\"" + escapeJson(str(tc.getSecondaryTransformationServerURL())) + "\"");
                        out.write(",\"U1T\":\"" + escapeJson(str(tc.getPrimaryTransformationServerURLT())) + "\"");
                        out.write(",\"U2T\":\"" + escapeJson(str(tc.getSecondaryTransformationServerURLT())) + "\"");
                        out.write(",\"U11\":\"" + escapeJson(str(tc.getPrimaryTransformationServerURL1())) + "\"");
                        out.write(",\"U21\":\"" + escapeJson(str(tc.getSecondaryTransformationServerURL1())) + "\"");
                        out.write(",\"U1T1\":\"" + escapeJson(str(tc.getPrimaryTransformationServerURLT1())) + "\"");
                        out.write(",\"U2T1\":\"" + escapeJson(str(tc.getSecondaryTransformationServerURLT1())) + "\"");
                        out.write(",\"U1D\":\"" + escapeJson(str(tc.getPrimaryTransformationServerURLD())) + "\"");
                        out.write(",\"U2D\":\"" + escapeJson(str(tc.getSecondaryTransformationServerURLD())) + "\"");
                        out.write("}");
                    }
                } catch (Exception e) { /* ignore */ }
            }

            out.write("}}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private static String str(String s) {
        return s != null ? s : "";
    }

    // ── Helpers ─────────────────────────────────────────────

    private void writeFlowJson(PrintWriter out, TransactionContext tc, TransactionThread tt,
                               int index, String profileName) {
        String flowId = tc.getTransactionId();
        boolean running = tt.isActive();
        boolean executing = tt.isExecuting();
        String state = ConfigContext.getProfileTransactionState(tc, profileName);
        String command = ConfigContext.getProfileTransactionCommand(tc, profileName);
        long interval = tt.getTransactionInterval();
        long shift = tt.getTransactionShiftFromHartBeat();
        int counter = tt.getTransactionCounter();
        int successes = tt.getSuccesses();
        int failures = tt.getFailures();
        String startTime = "";
        try {
            startTime = tt.getTransactionStartTime() != null ? tt.getTransactionStartTime().toString() : "";
        } catch (Exception e) { /* ignore */ }

        String intervalDisplay;
        String shiftDisplay;
        if (shift < 0L) {
            shiftDisplay = "T";
            long hr = interval / (60L * 60L);
            long mn = (interval - hr * 60L * 60L) / 60L;
            long sc = interval - hr * 60L * 60L - mn * 60L;
            intervalDisplay = String.format("%02d:%02d:%02d", hr, mn, sc);
        } else {
            intervalDisplay = String.valueOf(interval);
            shiftDisplay = String.valueOf(shift);
        }

        boolean isScheduled = interval > 0L || shift < 0L;

        out.write("{\"index\":" + index);
        out.write(",\"flowId\":\"" + escapeJson(flowId) + "\"");
        out.write(",\"type\":\"" + (tc.isStateful() ? "scheduled" : "utility") + "\"");
        out.write(",\"state\":\"" + escapeJson(state) + "\"");
        out.write(",\"command\":\"" + escapeJson(command) + "\"");
        out.write(",\"running\":" + running);
        out.write(",\"executing\":" + executing);
        out.write(",\"isScheduled\":" + isScheduled);
        out.write(",\"interval\":" + interval);
        out.write(",\"intervalDisplay\":\"" + escapeJson(intervalDisplay) + "\"");
        out.write(",\"shift\":" + shift);
        out.write(",\"shiftDisplay\":\"" + escapeJson(shiftDisplay) + "\"");
        out.write(",\"counter\":" + counter);
        out.write(",\"successes\":" + successes);
        out.write(",\"failures\":" + failures);
        out.write(",\"startTime\":\"" + escapeJson(startTime) + "\"");
        out.write(",\"logLevel\":" + tt.getLogLevel());
        out.write("}");
    }

    private void handleStartStop(String body, String profileName, boolean start,
                                 HttpServletResponse response) throws IOException {
        int flowIndex = extractInt(body, "flowIndex", -1);
        if (flowIndex < 0) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"flowIndex required\"}");
            return;
        }

        Vector<TransactionContext> txList = ConfigContext.getTransactionList();
        if (flowIndex >= txList.size()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"Invalid flow index\"}");
            return;
        }

        TransactionContext tc = txList.get(flowIndex);
        if (start) {
            ConfigContext.runTransactionThread(tc, profileName);
        } else {
            ConfigContext.stopTransactionThread(tc, profileName);
        }

        // Return updated state
        TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(tc, profileName);
        if (tt != null) {
            PrintWriter out = response.getWriter();
            out.write("{\"success\":true,\"data\":");
            writeFlowJson(out, tc, tt, flowIndex, profileName);
            out.write("}");
        } else {
            response.getWriter().write("{\"success\":true,\"message\":\"" + (start ? "Started" : "Stopped") + "\"}");
        }
    }

    private void handleScheduleUpdate(String body, String profileName,
                                      HttpServletResponse response) throws IOException {
        int flowIndex = extractInt(body, "flowIndex", -1);
        if (flowIndex < 0) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"flowIndex required\"}");
            return;
        }

        Vector<TransactionContext> txList = ConfigContext.getTransactionList();
        if (flowIndex >= txList.size()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"Invalid flow index\"}");
            return;
        }

        TransactionContext tc = txList.get(flowIndex);
        TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(tc, profileName);
        if (tt == null) {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Flow thread not found for profile\"}");
            return;
        }

        if (tt.isActive() || tt.isExecuting()) {
            response.setStatus(409);
            response.getWriter().write("{\"success\":false,\"error\":\"Cannot modify schedule while flow is running\"}");
            return;
        }

        // Update schedule parameters
        long interval = extractLong(body, "interval", -1);
        long shift = extractLong(body, "shift", Long.MIN_VALUE);
        int counter = extractInt(body, "counter", -1);

        if (interval >= 0) tt.setTransactionInterval(interval);
        if (shift != Long.MIN_VALUE) tt.setTransactionShiftFromHartBeat(shift);
        if (counter >= 0) tt.setTransactionCounter(counter);

        PrintWriter out = response.getWriter();
        out.write("{\"success\":true,\"data\":");
        writeFlowJson(out, tc, tt, flowIndex, profileName);
        out.write("}");
    }

    private void handleSubmitAll(String body, String profileName,
                                 HttpServletResponse response) throws IOException {
        // This mirrors the ProductDemoServlet Submit action - saves and persists
        boolean saved = ConfigContext.adminSaveTransactions(true);
        response.getWriter().write("{\"success\":" + saved + ",\"message\":\"" +
                (saved ? "Configuration saved" : "Save failed") + "\"}");
    }

    private String getProfileName(HttpSession session) {
        // Profile name format: "companyName:email"
        String profileName = (String) session.getAttribute("currentProfile");
        if (profileName != null && !profileName.isEmpty()) return profileName;

        // Fallback: construct from session attributes
        String company = (String) session.getAttribute("companyName");
        String email = (String) session.getAttribute("userEmail");
        if (company != null && email != null) {
            return company + ":" + email;
        }
        return null;
    }

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private int extractInt(String json, String key, int defaultValue) {
        try {
            String pattern = "\"" + key + "\"";
            int idx = json.indexOf(pattern);
            if (idx < 0) return defaultValue;
            idx = json.indexOf(":", idx + pattern.length());
            if (idx < 0) return defaultValue;
            idx++;
            while (idx < json.length() && json.charAt(idx) == ' ') idx++;
            int end = idx;
            while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
            return Integer.parseInt(json.substring(idx, end));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private long extractLong(String json, String key, long defaultValue) {
        try {
            String pattern = "\"" + key + "\"";
            int idx = json.indexOf(pattern);
            if (idx < 0) return defaultValue;
            idx = json.indexOf(":", idx + pattern.length());
            if (idx < 0) return defaultValue;
            idx++;
            while (idx < json.length() && json.charAt(idx) == ' ') idx++;
            int end = idx;
            while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
            return Long.parseLong(json.substring(idx, end));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
