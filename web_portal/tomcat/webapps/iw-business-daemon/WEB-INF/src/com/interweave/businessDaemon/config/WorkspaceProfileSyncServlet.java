package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (company_id, profile_name) DO UPDATE SET " +
                        "solution_type = EXCLUDED.solution_type, " +
                        "configuration_xml = EXCLUDED.configuration_xml")) {
                    stmt.setInt(1, companyId);
                    stmt.setString(2, profileName);
                    stmt.setString(3, solutionType);
                    stmt.setString(4, payload.configXml);
                    stmt.executeUpdate();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO company_configurations (company_id, profile_name, solution_type, configuration_xml) " +
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
