package com.interweave.businessDaemon.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Local-only utility servlet that mirrors saved company configuration XML
 * between the shared DB and IDE-visible workspace files.
 *
 * Supported actions:
 *   exportAll      - export every saved company_configurations row
 *   exportProfile  - export one profile by CurrentProfile/profile
 *   importProfile  - import one workspace mirror back into the DB
 */
public class WorkspaceProfileSyncServlet extends LocalUserManagementServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!isLoopbackRequest(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Workspace sync is only available from localhost");
            return;
        }

        response.setContentType("text/plain; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "exportAll";
        }

        PrintWriter out = response.getWriter();
        if ("exportAll".equalsIgnoreCase(action)) {
            exportAll(out);
            return;
        }

        String profileName = param(request, "CurrentProfile");
        if (profileName.isEmpty()) {
            profileName = param(request, "profile");
        }
        if (profileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "CurrentProfile or profile is required");
            return;
        }

        if ("exportProfile".equalsIgnoreCase(action)) {
            exportProfile(profileName, out, response);
            return;
        }

        if ("importProfile".equalsIgnoreCase(action)) {
            importProfile(profileName, param(request, "project"), out, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Unsupported action. Use exportAll, exportProfile, or importProfile.");
    }

    private void exportAll(PrintWriter out) throws IOException {
        int count = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT cc.profile_name, cc.solution_type, cc.configuration_xml " +
                 "FROM company_configurations cc ORDER BY cc.profile_name");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WorkspaceProfileSyncSupport.MirrorResult result =
                    WorkspaceProfileSyncSupport.exportProfile(
                        getServletContext(),
                        rs.getString("profile_name"),
                        rs.getString("solution_type"),
                        rs.getString("configuration_xml"));
                count++;
                out.println("EXPORTED " + result.profileName + " -> " + result.syncPath
                    + (result.projectPath != null && result.projectPath.length() > 0
                        ? " | " + result.projectPath : ""));
            }
            out.println("DONE exported=" + count);
        } catch (SQLException e) {
            log("Workspace exportAll failed", e);
            throw new IOException("Workspace exportAll failed: " + e.getMessage(), e);
        }
    }

    private void exportProfile(String profileName, PrintWriter out, HttpServletResponse response)
            throws IOException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT solution_type, configuration_xml FROM company_configurations " +
                 "WHERE profile_name = ?")) {
            stmt.setString(1, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No saved configuration found for profile " + profileName);
                    return;
                }
                WorkspaceProfileSyncSupport.MirrorResult result =
                    WorkspaceProfileSyncSupport.exportProfile(
                        getServletContext(),
                        profileName,
                        rs.getString("solution_type"),
                        rs.getString("configuration_xml"));
                out.println("EXPORTED " + result.profileName);
                out.println("SYNC " + result.syncPath);
                if (result.projectPath != null && result.projectPath.length() > 0) {
                    out.println("PROJECT " + result.projectPath);
                }
            }
        } catch (SQLException e) {
            log("Workspace exportProfile failed for " + profileName, e);
            throw new IOException("Workspace exportProfile failed: " + e.getMessage(), e);
        }
    }

    private void importProfile(String profileName, String projectName, PrintWriter out,
                               HttpServletResponse response) throws IOException {
        WorkspaceProfileSyncSupport.MirrorPayload payload =
            WorkspaceProfileSyncSupport.loadMirroredProfile(getServletContext(), profileName, projectName);

        try (Connection conn = dataSource.getConnection()) {
            int companyId = resolveCompanyId(conn, profileName);
            if (companyId < 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Could not resolve company for profile " + profileName);
                return;
            }

            String solutionType = payload.solutionType;
            if (solutionType == null || solutionType.trim().isEmpty()) {
                solutionType = lookupSolutionType(conn, profileName);
            }
            if (solutionType == null || solutionType.trim().isEmpty()) {
                solutionType = "QB";
            }

            if (isPostgres(conn)) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml, " +
                        "version, last_modified_source) " +
                        "VALUES (?, ?, ?, ?, 1, 'bridge') " +
                        "ON CONFLICT (company_id, profile_name) DO UPDATE SET " +
                        "solution_type = EXCLUDED.solution_type, " +
                        "configuration_xml = EXCLUDED.configuration_xml, " +
                        "version = company_configurations.version + 1, " +
                        "last_modified_source = 'bridge', " +
                        "updated_at = CURRENT_TIMESTAMP")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, payload.configXml);
                    stmt.executeUpdate();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml, " +
                        "version, last_modified_source) " +
                        "VALUES (?, ?, ?, ?, 1, 'bridge') " +
                        "ON DUPLICATE KEY UPDATE " +
                        "solution_type = VALUES(solution_type), " +
                        "configuration_xml = VALUES(configuration_xml), " +
                        "version = version + 1, " +
                        "last_modified_source = 'bridge'")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, payload.configXml);
                    stmt.executeUpdate();
                }
            }

            // After import, align the .push_epoch sidecar with the new DB timestamp
            // so status correctly reports "in_sync" (the DB now reflects what's in the IDE).
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
                try {
                    File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(getServletContext());
                    String safeProfile = WorkspaceProfileSyncSupport.sanitizeProfileKey(profileName);
                    File profileDir = new File(
                        new File(new File(new File(repoRoot, "workspace"), "IW_Runtime_Sync"), "profiles"), safeProfile);
                    if (profileDir.isDirectory()) {
                        File epochFile = new File(profileDir, ".push_epoch");
                        BufferedWriter w = null;
                        try {
                            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(epochFile), "UTF-8"));
                            w.write(String.valueOf(storedMs));
                        } finally {
                            if (w != null) { try { w.close(); } catch (Exception ignored) {} }
                        }
                    }
                } catch (Exception epochErr) {
                    log("Failed to write .push_epoch after import for " + profileName, epochErr);
                }
            }

            // Notify SSE clients that a profile was imported from IDE workspace
            com.interweave.businessDaemon.api.SyncEventServlet.broadcast(
                "pull-complete", profileName, "bridge");

            out.println("IMPORTED " + profileName);
            out.println("SOURCE " + payload.sourcePath);
            out.println("SOLUTION " + solutionType);
        } catch (SQLException e) {
            log("Workspace importProfile failed for " + profileName, e);
            throw new IOException("Workspace importProfile failed: " + e.getMessage(), e);
        }
    }

    private int resolveCompanyId(Connection conn, String profileName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT company_id FROM company_configurations WHERE profile_name = ?")) {
            stmt.setString(1, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        String companyName = profileName;
        String userEmail = "";
        int idx = profileName.indexOf(':');
        if (idx >= 0) {
            companyName = profileName.substring(0, idx);
            if (idx + 1 < profileName.length()) {
                userEmail = profileName.substring(idx + 1);
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id FROM companies WHERE LOWER(company_name) = LOWER(?)")) {
            stmt.setString(1, companyName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        if (userEmail.length() > 0) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT c.id FROM companies c " +
                    "JOIN users u ON u.company_id = c.id " +
                    "WHERE LOWER(u.email) = LOWER(?)")) {
                stmt.setString(1, userEmail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

    private String lookupSolutionType(Connection conn, String profileName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT solution_type FROM company_configurations WHERE profile_name = ?")) {
            stmt.setString(1, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return "";
    }

    private boolean isLoopbackRequest(HttpServletRequest request) {
        String addr = request.getRemoteAddr();
        return "127.0.0.1".equals(addr)
            || "::1".equals(addr)
            || "0:0:0:0:0:0:0:1".equals(addr);
    }
}
