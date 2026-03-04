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
 * Compiles wizard-saved profile XML into a generated engine overlay project.
 */
public class WorkspaceProfileCompilerServlet extends LocalUserManagementServlet {

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
            throws IOException {
        if (!isLoopbackRequest(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Workspace compiler is only available from localhost");
            return;
        }

        response.setContentType("text/plain; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "compileAll";
        }

        PrintWriter out = response.getWriter();
        if ("compileAll".equalsIgnoreCase(action)) {
            compileAll(out);
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

        if ("compileProfile".equalsIgnoreCase(action)) {
            compileProfile(profileName, out, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Unsupported action. Use compileAll or compileProfile.");
    }

    private void compileAll(PrintWriter out) throws IOException {
        int count = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT profile_name, solution_type, configuration_xml " +
                 "FROM company_configurations ORDER BY profile_name");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                WorkspaceProfileCompiler.CompileResult result =
                    WorkspaceProfileCompiler.compileProfile(
                        getServletContext(),
                        rs.getString("profile_name"),
                        rs.getString("solution_type"),
                        rs.getString("configuration_xml"));
                count++;
                out.println("COMPILED " + result.profileName + " -> " + result.generatedRoot);
            }
            out.println("DONE compiled=" + count);
        } catch (SQLException e) {
            log("compileAll failed", e);
            throw new IOException("compileAll failed: " + e.getMessage(), e);
        }
    }

    private void compileProfile(String profileName, PrintWriter out, HttpServletResponse response)
            throws IOException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT solution_type, configuration_xml FROM company_configurations WHERE profile_name = ?")) {
            stmt.setString(1, profileName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No saved configuration found for profile " + profileName);
                    return;
                }
                WorkspaceProfileCompiler.CompileResult result =
                    WorkspaceProfileCompiler.compileProfile(
                        getServletContext(),
                        profileName,
                        rs.getString("solution_type"),
                        rs.getString("configuration_xml"));
                out.println("COMPILED " + result.profileName);
                out.println("GENERATED " + result.generatedRoot);
                out.println("ENGINE " + result.engineConfigPath);
                out.println("TEMPLATE " + result.templateProject);
            }
        } catch (SQLException e) {
            log("compileProfile failed for " + profileName, e);
            throw new IOException("compileProfile failed: " + e.getMessage(), e);
        }
    }

    private boolean isLoopbackRequest(HttpServletRequest request) {
        String addr = request.getRemoteAddr();
        return "127.0.0.1".equals(addr)
            || "::1".equals(addr)
            || "0:0:0:0:0:0:0:1".equals(addr);
    }
}
