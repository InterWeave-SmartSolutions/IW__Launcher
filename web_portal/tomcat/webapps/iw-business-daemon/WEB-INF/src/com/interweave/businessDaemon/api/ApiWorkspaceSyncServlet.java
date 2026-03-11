package com.interweave.businessDaemon.api;

import com.interweave.businessDaemon.config.WorkspaceProfileCompiler;
import com.interweave.businessDaemon.config.WorkspaceProfileSyncSupport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * ApiWorkspaceSyncServlet — IDE ↔ Portal sync visibility and control.
 *
 * GET  /api/sync/status   — per-profile sync state + bridge running flag
 * POST /api/sync/push     — export all (or one) profile from DB → workspace
 * POST /api/sync/pull     — import a profile from workspace → DB
 * GET  /api/sync/log      — last N lines from logs/sync_bridge.log
 */
public class ApiWorkspaceSyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Profiles whose push epoch and DB updated_at differ by less than this are "in_sync". */
    private static final long SKEW_MS = 60_000L;

    /**
     * If the sync log file was written within this window, the bridge is considered active.
     * The bridge polls every 1 s, so 2 minutes provides comfortable leeway.
     */
    private static final long BRIDGE_ACTIVE_WINDOW_MS = 120_000L;

    /**
     * Sidecar file written next to company_configuration.xml when a portal push is performed.
     * Contains the epoch milliseconds (as a plain long) of when the push happened.
     * Used instead of File.lastModified() to avoid a 32-bit JVM timezone offset bug on Windows.
     */
    private static final String PUSH_EPOCH_FILE = ".push_epoch";

    private static final int DEFAULT_LOG_LINES = 60;

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:/comp/env");
            dataSource = (DataSource) envCtx.lookup("jdbc/IWDB");
        } catch (Exception e) {
            throw new ServletException("ApiWorkspaceSyncServlet: DataSource unavailable", e);
        }
    }

    // ── GET ─────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        if (!isAuthenticated(request)) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/status".equals(pathInfo)) {
            handleStatus(response);
        } else if ("/log".equals(pathInfo)) {
            handleLog(request, response);
        } else {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Use /status or /log\"}");
        }
    }

    // ── POST ────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");

        if (!isAuthenticated(request)) {
            response.setStatus(401);
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        if ("/push".equals(pathInfo)) {
            handlePush(request, response);
        } else if ("/pull".equals(pathInfo)) {
            handlePull(request, response);
        } else {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"Use POST /push or /pull\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(204);
    }

    // ── Handlers ────────────────────────────────────────────────────────────

    private void handleStatus(HttpServletResponse response) throws IOException {
        try {
            File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(getServletContext());
            File logsDir = new File(repoRoot, "logs");
            File logFile = new File(logsDir, "sync_bridge.log");
            File pidFile = new File(logsDir, "sync_bridge.pid");

            // Bridge is considered running if the pid file exists AND the log file
            // was written recently (the bridge emits at least one line per second).
            boolean bridgeRunning = pidFile.isFile()
                && logFile.isFile()
                && (System.currentTimeMillis() - logFile.lastModified()) < BRIDGE_ACTIVE_WINDOW_MS;

            File workspaceRoot = new File(repoRoot, "workspace");
            StringBuilder profiles = new StringBuilder("[");
            boolean firstProfile = true;

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "SELECT profile_name, solution_type, updated_at " +
                     "FROM company_configurations ORDER BY profile_name");
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String profileName = rs.getString("profile_name");
                    String solutionType = rs.getString("solution_type");
                    Timestamp dbUpdated = rs.getTimestamp("updated_at");
                    long dbMs = dbUpdated != null ? dbUpdated.getTime() : 0L;

                    String safeProfile = WorkspaceProfileSyncSupport.sanitizeProfileKey(profileName);

                    // IW_Runtime_Sync mirror
                    File profileDir = new File(
                        new File(new File(workspaceRoot, "IW_Runtime_Sync"), "profiles"), safeProfile);
                    File syncXml = new File(profileDir, "company_configuration.xml");
                    boolean wsExists = syncXml.isFile();

                    // Read the push epoch sidecar (written when portal last pushed to IDE).
                    // We use this instead of File.lastModified() to avoid a 32-bit JVM
                    // timezone-offset bug on Windows where lastModified() returns
                    // epoch_ms - timezone_offset_ms instead of the true epoch ms.
                    File pushEpochFile = new File(profileDir, PUSH_EPOCH_FILE);
                    long pushMs = readEpochFile(pushEpochFile);

                    // GeneratedProfiles overlay
                    File genIm = new File(
                        new File(new File(new File(new File(workspaceRoot, "GeneratedProfiles"), safeProfile),
                            "configuration"), "im"), "config.xml");
                    boolean genExists = genIm.isFile();

                    String syncStatus = computeSyncStatus(dbMs, pushMs, wsExists);

                    if (!firstProfile) profiles.append(",");
                    firstProfile = false;
                    profiles.append("{");
                    profiles.append("\"profileName\":\"").append(escJson(profileName)).append("\",");
                    profiles.append("\"solutionType\":\"").append(escJson(safe(solutionType))).append("\",");
                    profiles.append("\"dbUpdatedAt\":").append(isoOrNull(dbMs)).append(",");
                    profiles.append("\"workspaceXmlExists\":").append(wsExists).append(",");
                    profiles.append("\"workspaceXmlModified\":").append(isoOrNull(pushMs)).append(",");
                    profiles.append("\"generatedProfileExists\":").append(genExists).append(",");
                    profiles.append("\"generatedProfileModified\":").append(isoOrNull(0L)).append(",");
                    profiles.append("\"syncStatus\":\"").append(syncStatus).append("\"");
                    profiles.append("}");
                }

            } catch (SQLException e) {
                log("handleStatus DB error", e);
                response.setStatus(500);
                response.getWriter().write("{\"success\":false,\"error\":\"Database error\"}");
                return;
            }

            profiles.append("]");

            response.getWriter().write("{\"success\":true,\"data\":{" +
                "\"bridgeRunning\":" + bridgeRunning + "," +
                "\"profiles\":" + profiles.toString() +
                "}}");

        } catch (IOException e) {
            log("handleStatus IO error", e);
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escJson(e.getMessage()) + "\"}");
        }
    }

    private void handlePush(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String body = readBody(request);
        String targetProfile = extractJsonString(body, "profileName"); // empty = push all

        int pushed = 0;
        int failed = 0;
        StringBuilder errors = new StringBuilder();
        List<String> pushedNames = new ArrayList<String>();

        try (Connection conn = dataSource.getConnection()) {
            String sql = targetProfile.isEmpty()
                ? "SELECT profile_name, solution_type, configuration_xml FROM company_configurations ORDER BY profile_name"
                : "SELECT profile_name, solution_type, configuration_xml FROM company_configurations WHERE profile_name = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (!targetProfile.isEmpty()) stmt.setString(1, targetProfile);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String pName = rs.getString("profile_name");
                        String sType = rs.getString("solution_type");
                        String cXml  = rs.getString("configuration_xml");
                        try {
                            WorkspaceProfileSyncSupport.exportProfile(getServletContext(), pName, sType, cXml);
                            WorkspaceProfileCompiler.compileProfile(getServletContext(), pName, sType, cXml);
                            pushedNames.add(pName);
                            pushed++;
                        } catch (Exception ex) {
                            failed++;
                            if (errors.length() > 0) errors.append("; ");
                            errors.append(pName).append(": ").append(ex.getMessage());
                            log("push failed for " + pName, ex);
                        }
                    }
                }
            }

            // After a successful push: (a) align updated_at in DB to NOW, then
            // (b) read back the stored timestamp via SELECT so we get the exact
            // epoch ms that JDBC will return on future status checks. Write that
            // value to the .push_epoch sidecar so the comparison is consistent.
            // (System.currentTimeMillis() cannot be used here because on a 32-bit
            //  Windows JVM it returns local-time-as-epoch, not the true UTC epoch.)
            File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(getServletContext());
            File workspaceRoot = new File(repoRoot, "workspace");
            for (String pName : pushedNames) {
                try (PreparedStatement upd = conn.prepareStatement(
                        "UPDATE company_configurations SET updated_at = CURRENT_TIMESTAMP WHERE profile_name = ?")) {
                    upd.setString(1, pName);
                    upd.executeUpdate();
                }
                // Read back the timestamp Postgres actually stored
                long storedMs = 0L;
                try (PreparedStatement sel = conn.prepareStatement(
                        "SELECT updated_at FROM company_configurations WHERE profile_name = ?")) {
                    sel.setString(1, pName);
                    try (ResultSet rs2 = sel.executeQuery()) {
                        if (rs2.next()) {
                            Timestamp ts = rs2.getTimestamp(1);
                            if (ts != null) storedMs = ts.getTime();
                        }
                    }
                }
                if (storedMs > 0) {
                    String safeProfile = WorkspaceProfileSyncSupport.sanitizeProfileKey(pName);
                    File profileDir = new File(
                        new File(new File(workspaceRoot, "IW_Runtime_Sync"), "profiles"), safeProfile);
                    if (profileDir.isDirectory()) {
                        writeEpochFile(new File(profileDir, PUSH_EPOCH_FILE), storedMs);
                    }
                }
            }
        } catch (SQLException e) {
            log("handlePush DB error", e);
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"Database error\"}");
            return;
        }

        if (failed > 0 && pushed == 0) {
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escJson(errors.toString()) + "\"}");
        } else {
            String warn = errors.length() > 0
                ? ",\"warnings\":\"" + escJson(errors.toString()) + "\"" : "";
            response.getWriter().write("{\"success\":true,\"data\":{" +
                "\"pushed\":" + pushed + ",\"failed\":" + failed + warn + "}}");
        }
    }

    private void handlePull(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String body = readBody(request);
        String profileName = extractJsonString(body, "profileName");
        String projectName = extractJsonString(body, "projectName");

        if (profileName.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"success\":false,\"error\":\"profileName is required\"}");
            return;
        }

        WorkspaceProfileSyncSupport.MirrorPayload payload;
        try {
            payload = WorkspaceProfileSyncSupport.loadMirroredProfile(
                getServletContext(), profileName, projectName.isEmpty() ? null : projectName);
        } catch (IOException e) {
            response.setStatus(404);
            response.getWriter().write("{\"success\":false,\"error\":\"" + escJson(e.getMessage()) + "\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            int companyId = resolveCompanyId(conn, profileName);
            if (companyId < 0) {
                response.setStatus(404);
                response.getWriter().write("{\"success\":false,\"error\":\"No company found for profile\"}");
                return;
            }

            String solutionType = payload.solutionType;
            if (solutionType == null || solutionType.isEmpty()) solutionType = lookupSolutionType(conn, profileName);
            if (solutionType == null || solutionType.isEmpty()) solutionType = "QB";

            if (isPostgres(conn)) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations " +
                        "(company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (company_id, profile_name) DO UPDATE SET " +
                        "solution_type = EXCLUDED.solution_type, " +
                        "configuration_xml = EXCLUDED.configuration_xml, " +
                        "updated_at = CURRENT_TIMESTAMP")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, payload.configXml);
                    stmt.executeUpdate();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations " +
                        "(company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "solution_type = VALUES(solution_type), " +
                        "configuration_xml = VALUES(configuration_xml)")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, payload.configXml);
                    stmt.executeUpdate();
                }
            }

            // After import, align the .push_epoch sidecar with the new DB timestamp
            // so that /status correctly reports "in_sync" (the DB now reflects the IDE).
            long storedMs = 0L;
            try (PreparedStatement sel = conn.prepareStatement(
                    "SELECT updated_at FROM company_configurations WHERE profile_name = ?")) {
                sel.setString(1, profileName);
                try (ResultSet rs2 = sel.executeQuery()) {
                    if (rs2.next()) {
                        Timestamp ts = rs2.getTimestamp(1);
                        if (ts != null) storedMs = ts.getTime();
                    }
                }
            }
            if (storedMs > 0) {
                File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(getServletContext());
                File workspaceRoot = new File(repoRoot, "workspace");
                String safeProfile = WorkspaceProfileSyncSupport.sanitizeProfileKey(profileName);
                File profileDir = new File(
                    new File(new File(workspaceRoot, "IW_Runtime_Sync"), "profiles"), safeProfile);
                if (profileDir.isDirectory()) {
                    writeEpochFile(new File(profileDir, PUSH_EPOCH_FILE), storedMs);
                }
            }

            response.getWriter().write("{\"success\":true,\"data\":{" +
                "\"profileName\":\"" + escJson(profileName) + "\"," +
                "\"solutionType\":\"" + escJson(solutionType) + "\"," +
                "\"sourcePath\":\"" + escJson(safe(payload.sourcePath)) + "\"" +
                "}}");

        } catch (SQLException e) {
            log("handlePull DB error for " + profileName, e);
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"error\":\"Database error\"}");
        }
    }

    private void handleLog(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int maxLines = DEFAULT_LOG_LINES;
        try { maxLines = Integer.parseInt(request.getParameter("lines")); } catch (Exception ignored) {}
        if (maxLines < 1) maxLines = 1;
        if (maxLines > 500) maxLines = 500;

        File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(getServletContext());
        File logFile = new File(new File(repoRoot, "logs"), "sync_bridge.log");

        List<String> lines = new ArrayList<String>();
        if (logFile.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) lines.add(line);
            } finally {
                if (reader != null) reader.close();
            }
        }

        int from = Math.max(0, lines.size() - maxLines);
        List<String> tail = lines.subList(from, lines.size());

        StringBuilder arr = new StringBuilder("[");
        for (int i = 0; i < tail.size(); i++) {
            if (i > 0) arr.append(",");
            arr.append("\"").append(escJson(tail.get(i))).append("\"");
        }
        arr.append("]");

        response.getWriter().write("{\"success\":true,\"data\":{" +
            "\"logExists\":" + logFile.isFile() + "," +
            "\"totalLines\":" + lines.size() + "," +
            "\"lines\":" + arr.toString() +
            "}}");
    }

    // ── Utilities ────────────────────────────────────────────────────────────

    /**
     * Reads epoch ms from a sidecar file (plain-text long). Returns 0 if missing.
     * Using file content avoids File.lastModified() timezone bugs on 32-bit Windows.
     */
    private long readEpochFile(File f) {
        if (!f.isFile()) return 0L;
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line = r.readLine();
            return line != null ? Long.parseLong(line.trim()) : 0L;
        } catch (Exception e) {
            return 0L;
        } finally {
            if (r != null) { try { r.close(); } catch (Exception ignored) {} }
        }
    }

    /** Writes epoch ms to a sidecar file as a plain-text long. */
    private void writeEpochFile(File f, long epochMs) {
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
            w.write(String.valueOf(epochMs));
        } catch (Exception e) {
            log("Failed to write epoch sidecar " + f.getAbsolutePath(), e);
        } finally {
            if (w != null) { try { w.close(); } catch (Exception ignored) {} }
        }
    }

    private String computeSyncStatus(long dbMs, long pushMs, boolean wsExists) {
        if (!wsExists) return "not_synced";
        if (pushMs == 0) return "db_ahead";      // workspace exists but never been pushed from portal
        long diff = dbMs - pushMs;
        if (Math.abs(diff) <= SKEW_MS) return "in_sync";
        return diff > 0 ? "db_ahead" : "workspace_ahead"; // db newer = needs push; push newer = IDE may have changed
    }

    private int resolveCompanyId(Connection conn, String profileName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement(
                "SELECT company_id FROM company_configurations WHERE profile_name = ?")) {
            s.setString(1, profileName);
            try (ResultSet rs = s.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        String companyName = profileName;
        String userEmail = "";
        int idx = profileName.indexOf(':');
        if (idx >= 0) { companyName = profileName.substring(0, idx); userEmail = profileName.substring(idx + 1); }
        try (PreparedStatement s = conn.prepareStatement(
                "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
            s.setString(1, companyName);
            try (ResultSet rs = s.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        if (!userEmail.isEmpty()) {
            try (PreparedStatement s = conn.prepareStatement(
                    "SELECT c.id FROM companies c JOIN users u ON u.company_id = c.id WHERE LOWER(u.email) = LOWER(?)")) {
                s.setString(1, userEmail);
                try (ResultSet rs = s.executeQuery()) { if (rs.next()) return rs.getInt(1); }
            }
        }
        return -1;
    }

    private String lookupSolutionType(Connection conn, String profileName) throws SQLException {
        try (PreparedStatement s = conn.prepareStatement(
                "SELECT solution_type FROM company_configurations WHERE profile_name = ?")) {
            s.setString(1, profileName);
            try (ResultSet rs = s.executeQuery()) { if (rs.next()) return rs.getString(1); }
        }
        return "";
    }

    private boolean isPostgres(Connection conn) {
        try { return conn.getMetaData().getDatabaseProductName().toLowerCase().contains("postgres"); }
        catch (Exception e) { return false; }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute("authenticated"));
    }

    private String readBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        return sb.toString();
    }

    /** Minimal single-key JSON string extractor (no nested objects). */
    private String extractJsonString(String json, String key) {
        if (json == null || json.isEmpty()) return "";
        String search = "\"" + key + "\"";
        int ki = json.indexOf(search);
        if (ki < 0) return "";
        int colon = json.indexOf(':', ki + search.length());
        if (colon < 0) return "";
        int q1 = json.indexOf('"', colon + 1);
        if (q1 < 0) return "";
        int q2 = q1 + 1;
        while (q2 < json.length()) {
            if (json.charAt(q2) == '\\') { q2 += 2; continue; }
            if (json.charAt(q2) == '"') break;
            q2++;
        }
        return q2 < json.length() ? json.substring(q1 + 1, q2) : "";
    }

    private String isoOrNull(long ms) {
        if (ms == 0) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return "\"" + sdf.format(new Date(ms)) + "\"";
    }

    private String safe(String s) { return s != null ? s : ""; }

    private String escJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
