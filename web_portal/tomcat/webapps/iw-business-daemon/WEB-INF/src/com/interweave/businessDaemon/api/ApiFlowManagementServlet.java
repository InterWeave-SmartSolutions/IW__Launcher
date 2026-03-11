package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            // Get solution type from session and resolve allowed flow IDs
            String solutionType = (String) session.getAttribute("solutionType");
            Set<String> allowedFlowIds = getAllowedFlowIds(solutionType);

            PrintWriter out = response.getWriter();
            out.write("{\"success\":true,\"data\":{");

            // Server info
            out.write("\"serverName\":\"" + escapeJson(ConfigContext.getName()) + "\",");
            out.write("\"heartbeatInterval\":" + ConfigContext.getHartbeatInterval() + ",");
            out.write("\"isHosted\":" + ConfigContext.isHosted() + ",");
            out.write("\"profileName\":\"" + escapeJson(profileName) + "\",");
            out.write("\"solutionType\":\"" + escapeJson(solutionType != null ? solutionType : "") + "\",");

            // Transaction flows (scheduled)
            out.write("\"scheduledFlows\":[");
            boolean firstScheduled = true;
            Vector<TransactionContext> txList = ConfigContext.getTransactionList();
            for (int i = 0; i < txList.size(); i++) {
                TransactionContext tc = txList.get(i);
                if (!tc.isStateful()) continue;

                // Filter by solution type: skip flows not in this company's config
                if (allowedFlowIds != null && !allowedFlowIds.contains(tc.getTransactionId())) continue;

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

                // Filter by solution type
                if (allowedFlowIds != null && !allowedFlowIds.contains(tc.getTransactionId())) continue;

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

                // Filter by solution type
                if (allowedFlowIds != null && !allowedFlowIds.contains(qc.getTransactionId())) continue;

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

            // Include configured flows from config.xml (even if engine hasn't loaded them)
            if (allowedFlowIds != null) {
                out.write(",\"configuredFlowIds\":[");
                boolean firstCfg = true;
                for (String fid : allowedFlowIds) {
                    if (!firstCfg) out.write(",");
                    firstCfg = false;
                    out.write("\"" + escapeJson(fid) + "\"");
                }
                out.write("]");
            }

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
            } else if (pathInfo.equals("/initialize")) {
                handleInitializeProfile(session, profileName, response);
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

    // ── Initialize Profile (bind to engine) ────────────────────────────

    private void handleInitializeProfile(HttpSession session, String profileName,
                                          HttpServletResponse response) throws IOException {
        try {
            int companyId = 0;
            Object cid = session.getAttribute("companyId");
            if (cid instanceof Number) companyId = ((Number) cid).intValue();

            // Load saved configuration XML from database
            String savedConfig = null;
            if (companyId > 0) {
                try {
                    DataSource ds = (DataSource) new InitialContext()
                            .lookup("java:comp/env/jdbc/HostedDS");
                    try (Connection conn = ds.getConnection()) {
                        savedConfig = loadSavedConfig(conn, companyId, profileName);
                    }
                } catch (Exception e) {
                    log("Could not load saved config for initialize: " + e.getMessage());
                }
            }

            String config = (savedConfig != null)
                    ? savedConfig
                    : "<SF2QBConfiguration></SF2QBConfiguration>";

            // Bind the profile into ConfigContext (same as login-time bindHostedProfile)
            bindHostedProfile(profileName, config);

            // Compile workspace profile overlay
            String solutionType = (String) session.getAttribute("solutionType");
            if (savedConfig != null) {
                try {
                    com.interweave.businessDaemon.config.WorkspaceProfileCompiler.compileProfile(
                            getServletContext(), profileName, solutionType, savedConfig);
                } catch (Exception e) {
                    log("Profile initialized but workspace compiler failed: " + e.getMessage());
                }
            }

            // Count flows now available for this profile
            int flowCount = 0;
            int queryCount = 0;
            Set<String> allowedFlowIds = getAllowedFlowIds(solutionType);
            for (TransactionContext tc : ConfigContext.getTransactionList()) {
                if (allowedFlowIds != null && !allowedFlowIds.contains(tc.getTransactionId())) continue;
                TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(tc, profileName);
                if (tt != null) flowCount++;
            }
            for (QueryContext qc : ConfigContext.getQueryList()) {
                if (allowedFlowIds != null && !allowedFlowIds.contains(qc.getTransactionId())) continue;
                if (ConfigContext.getQueryInstanceByProfileName(qc, profileName) != null) queryCount++;
            }

            response.getWriter().write("{\"success\":true,\"message\":\"Profile initialized with " +
                    flowCount + " flows and " + queryCount + " queries\",\"flowCount\":" +
                    flowCount + ",\"queryCount\":" + queryCount + "}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private void bindHostedProfile(String profileName, String config) {
        applyRuntimeEndpointMode();

        if (!ConfigContext.getMonitorsStarted().contains(profileName)) {
            ConfigContext.getMonitorsStarted().add(profileName);
        }
        if (!ConfigContext.getProfileDescriptors().containsKey(profileName)) {
            ConfigContext.getProfileDescriptors().put(profileName,
                    new ConfigContext.ProfileDescriptor());
        }

        setupTransactionThread(ConfigContext.getCompanyRegistration(), profileName, config);
        setupTransactionThread(ConfigContext.getUpdateCompany(), profileName, config);
        setupTransactionThread(ConfigContext.getRequestCompany(), profileName, config);

        for (TransactionContext ctx : ConfigContext.getTransactionList()) {
            setupTransactionThread(ctx, profileName, config);
        }
        for (QueryContext ctx : ConfigContext.getQueryList()) {
            setupQueryInstance(ctx, profileName);
        }
    }

    private void setupTransactionThread(TransactionContext ctx, String profileName, String config) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        TransactionThread tt = ConfigContext.getTransactionThreadByProfileName(ctx, profileName);
        if (tt == null) {
            tt = ctx.addTransactionThread(profileName);
        }
        if (tt != null) {
            tt.putParameter("configuration", sanitizeConfig(config));
            tt.setCompanyConfiguration(sanitizeFullConfig(config));
            applyEndpointMode(tt);
        }
    }

    private void setupQueryInstance(QueryContext ctx, String profileName) {
        if (ctx == null) return;
        applyEndpointMode(ctx);
        if (ConfigContext.getQueryInstanceByProfileName(ctx, profileName) == null) {
            ctx.addQueryInstance(profileName);
        }
    }

    private void applyRuntimeEndpointMode() {
        ConfigContext.setMyGlobalIP(resolveTransformationServerHost());
        ConfigContext.setPrimaryTransformationServerURL(
                rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL()));
        ConfigContext.setPrimaryTransformationServerURLT(
                rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT()));
        ConfigContext.setPrimaryTransformationServerURL1(
                rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURL1()));
        ConfigContext.setPrimaryTransformationServerURLT1(
                rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLT1()));
        ConfigContext.setPrimaryTransformationServerURLD(
                rewriteRuntimeUrl(ConfigContext.getPrimaryTransformationServerURLD()));
        ConfigContext.setSecondaryTransformationServerURL(
                rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL()));
        ConfigContext.setSecondaryTransformationServerURLT(
                rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT()));
        ConfigContext.setSecondaryTransformationServerURL1(
                rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURL1()));
        ConfigContext.setSecondaryTransformationServerURLT1(
                rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLT1()));
        ConfigContext.setSecondaryTransformationServerURLD(
                rewriteRuntimeUrl(ConfigContext.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(HostedTransactionBase runtimeItem) {
        runtimeItem.setPrimaryTransformationServerURL(
                rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL()));
        runtimeItem.setPrimaryTransformationServerURLT(
                rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT()));
        runtimeItem.setPrimaryTransformationServerURL1(
                rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURL1()));
        runtimeItem.setPrimaryTransformationServerURLT1(
                rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLT1()));
        runtimeItem.setPrimaryTransformationServerURLD(
                rewriteRuntimeUrl(runtimeItem.getPrimaryTransformationServerURLD()));
        runtimeItem.setSecondaryTransformationServerURL(
                rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL()));
        runtimeItem.setSecondaryTransformationServerURLT(
                rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT()));
        runtimeItem.setSecondaryTransformationServerURL1(
                rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURL1()));
        runtimeItem.setSecondaryTransformationServerURLT1(
                rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLT1()));
        runtimeItem.setSecondaryTransformationServerURLD(
                rewriteRuntimeUrl(runtimeItem.getSecondaryTransformationServerURLD()));
    }

    private void applyEndpointMode(TransactionThread thread) {
        thread.setPrimaryDedicatedURL(rewriteRuntimeUrl(thread.getPrimaryDedicatedURL()));
        thread.setPrimaryDedicatedURLc(rewriteRuntimeUrl(thread.getPrimaryDedicatedURLc()));
        thread.setSecondaryDedicatedURL(rewriteRuntimeUrl(thread.getSecondaryDedicatedURL()));
        thread.setSecondaryDedicatedURLc(rewriteRuntimeUrl(thread.getSecondaryDedicatedURLc()));
    }

    private static String resolveTransformationServerHost() {
        String origin = resolveTransformationServerOrigin();
        int scheme = origin.indexOf("://");
        if (scheme >= 0) origin = origin.substring(scheme + 3);
        int slash = origin.indexOf('/');
        if (slash >= 0) origin = origin.substring(0, slash);
        int colon = origin.lastIndexOf(':');
        return (colon > -1) ? origin.substring(0, colon) : origin;
    }

    private static String resolveTransformationServerOrigin() {
        String base = resolveTransformationServerBase();
        int marker = base.indexOf("/iwtransformationserver");
        if (marker >= 0) return base.substring(0, marker);
        int scheme = base.indexOf("://");
        if (scheme < 0) return base;
        int slash = base.indexOf('/', scheme + 3);
        return (slash >= 0) ? base.substring(0, slash) : base;
    }

    private static String resolveTransformationServerBase() {
        String mode = System.getenv("TS_MODE");
        if (mode != null && "legacy".equalsIgnoreCase(mode.trim())) {
            return envOrDefault("TS_BASE_LEGACY", "http://iw0.interweave.biz:9090/iwtransformationserver");
        }
        return envOrDefault("TS_BASE_LOCAL", "http://localhost:9090/iwtransformationserver");
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) value = fallback;
        value = value.trim();
        while (value.endsWith("/")) value = value.substring(0, value.length() - 1);
        return value;
    }

    private static String rewriteRuntimeUrl(String url) {
        if (url == null) return "";
        String value = url.trim();
        if (value.isEmpty() || "null".equalsIgnoreCase(value)) return value;
        String origin = resolveTransformationServerOrigin();
        int marker = value.indexOf("/iwtransformationserver");
        if (marker >= 0) return origin + value.substring(marker);
        if (value.startsWith("http://") || value.startsWith("https://")) {
            int scheme = value.indexOf("://");
            int slash = value.indexOf('/', scheme + 3);
            if (slash >= 0) return origin + value.substring(slash);
            return resolveTransformationServerBase();
        }
        return resolveTransformationServerBase();
    }

    private static final String CONFIG_CLOSE_TAG = "</SF2QBConfiguration>";

    private static String sanitizeConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "");
    }

    private static String sanitizeFullConfig(String xml) {
        if (xml == null || xml.isEmpty()) return "<SF2QBConfiguration></SF2QBConfiguration>";
        return xml.replace(CONFIG_CLOSE_TAG, "") + CONFIG_CLOSE_TAG;
    }

    private String loadSavedConfig(Connection conn, int companyId, String profileName) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT configuration_xml FROM company_configurations " +
                "WHERE company_id = ? AND profile_name = ?")) {
            stmt.setInt(1, companyId);
            stmt.setString(2, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String xml = rs.getString("configuration_xml");
                    if (xml != null && !xml.isEmpty()) return xml;
                }
            }
        } catch (Exception e) {
            log("Could not load saved configuration for " + profileName, e);
        }
        return null;
    }

    /**
     * Reads the workspace-profile-map.properties to find the workspace project
     * for the given solutionType, then parses that project's im/config.xml to
     * extract the set of allowed TransactionDescription and Query IDs.
     * Returns null if the mapping or config cannot be read (= no filtering).
     */
    private Set<String> getAllowedFlowIds(String solutionType) {
        if (solutionType == null || solutionType.isEmpty()) return null;
        try {
            // Locate workspace-profile-map.properties relative to webapp
            String catalinaBase = System.getProperty("catalina.base",
                    System.getProperty("catalina.home", ""));
            File mapFile = new File(catalinaBase, "../../config/workspace-profile-map.properties");
            if (!mapFile.exists()) {
                // Try alternate path
                mapFile = new File(catalinaBase, "../../../config/workspace-profile-map.properties");
            }
            if (!mapFile.exists()) return null;

            Properties map = new Properties();
            try (FileInputStream fis = new FileInputStream(mapFile)) {
                map.load(fis);
            }

            String projectName = map.getProperty(solutionType);
            if (projectName == null || projectName.isEmpty()) return null;

            // Read im/config.xml from the workspace project
            File configFile = new File(catalinaBase,
                    "../../workspace/" + projectName + "/configuration/im/config.xml");
            if (!configFile.exists()) {
                configFile = new File(catalinaBase,
                        "../../../workspace/" + projectName + "/configuration/im/config.xml");
            }
            if (!configFile.exists()) return null;

            // Simple regex parse for Id attributes (avoid XML parser dependency)
            byte[] bytes = new byte[(int) configFile.length()];
            try (FileInputStream fis = new FileInputStream(configFile)) {
                fis.read(bytes);
            }
            String xml = new String(bytes, "UTF-8");

            Set<String> ids = new HashSet<String>();
            // Match TransactionDescription Id="..." and Query Id="..."
            Pattern p = Pattern.compile("(?:TransactionDescription|Query)\\s+Id=\"([^\"]+)\"");
            Matcher m = p.matcher(xml);
            while (m.find()) {
                ids.add(m.group(1));
            }
            return ids.isEmpty() ? null : ids;
        } catch (Exception e) {
            log("getAllowedFlowIds failed for solutionType=" + solutionType, e);
            return null;
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
