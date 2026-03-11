package com.interweave.businessDaemon.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * Bridges wizard-saved company configuration XML into IDE-visible workspace
 * files without mutating the engine's design-time project config files.
 *
 * The wizard persists a flat SF2QBConfiguration XML payload per profile.
 * That payload is not structurally compatible with configuration/im/config.xml,
 * so this helper mirrors it into dedicated runtime-profile sidecar files that
 * can be viewed and edited in the IDE, then imported back explicitly.
 */
public final class WorkspaceProfileSyncSupport {

    private static final String SYNC_PROJECT_NAME = "IW_Runtime_Sync";

    private WorkspaceProfileSyncSupport() {
    }

    public static MirrorResult exportProfile(ServletContext servletContext,
                                             String profileName,
                                             String solutionType,
                                             String configXml) throws IOException {
        if (profileName == null || profileName.trim().isEmpty()) {
            throw new IOException("Profile name is required");
        }

        String normalizedProfile = profileName.trim();
        String normalizedXml = LocalUserManagementServlet.sanitizeFullConfig(configXml);
        String safeProfile = sanitizeProfileKey(normalizedProfile);
        String companyName = extractCompanyName(normalizedProfile);
        String userEmail = extractUserEmail(normalizedProfile);

        File repoRoot = resolveRepoRoot(servletContext);
        File workspaceRoot = new File(repoRoot, "workspace");
        if (!workspaceRoot.isDirectory()) {
            throw new IOException("Workspace folder not found: " + workspaceRoot.getAbsolutePath());
        }

        File syncProjectRoot = ensureSyncProject(workspaceRoot);
        File profileDir = new File(new File(syncProjectRoot, "profiles"), safeProfile);
        if (!profileDir.isDirectory() && !profileDir.mkdirs()) {
            throw new IOException("Could not create sync profile directory: " + profileDir.getAbsolutePath());
        }

        File syncXml = new File(profileDir, "company_configuration.xml");
        File syncMeta = new File(profileDir, "metadata.properties");
        writeUtf8IfChanged(syncXml, normalizedXml);

        String mappedProject = resolveMappedProjectName(repoRoot, solutionType);
        Properties metadata = new Properties();
        metadata.setProperty("profile_name", normalizedProfile);
        metadata.setProperty("company_name", companyName);
        metadata.setProperty("user_email", userEmail);
        metadata.setProperty("solution_type", safeString(solutionType));
        metadata.setProperty("mapped_project", safeString(mappedProject));
        metadata.setProperty("sync_project", SYNC_PROJECT_NAME);
        metadata.setProperty("sync_file", relativize(repoRoot, syncXml));
        storeProperties(syncMeta, metadata);

        String projectMirrorPath = "";
        if (mappedProject != null && !mappedProject.isEmpty()) {
            File projectRoot = new File(workspaceRoot, mappedProject);
            if (projectRoot.isDirectory()) {
                File runtimeDir = new File(new File(projectRoot, "configuration"), "runtime_profiles");
                if (!runtimeDir.isDirectory() && !runtimeDir.mkdirs()) {
                    throw new IOException("Could not create project runtime profile directory: "
                        + runtimeDir.getAbsolutePath());
                }
                File projectXml = new File(runtimeDir, safeProfile + ".xml");
                File projectMeta = new File(runtimeDir, safeProfile + ".properties");
                writeUtf8IfChanged(projectXml, normalizedXml);
                storeProperties(projectMeta, metadata);
                projectMirrorPath = relativize(repoRoot, projectXml);
            }
        }

        return new MirrorResult(normalizedProfile, safeString(solutionType),
            relativize(repoRoot, syncXml), projectMirrorPath);
    }

    public static MirrorPayload loadMirroredProfile(ServletContext servletContext,
                                                    String profileName,
                                                    String projectName) throws IOException {
        if (profileName == null || profileName.trim().isEmpty()) {
            throw new IOException("Profile name is required");
        }

        String normalizedProfile = profileName.trim();
        String safeProfile = sanitizeProfileKey(normalizedProfile);
        File repoRoot = resolveRepoRoot(servletContext);
        File workspaceRoot = new File(repoRoot, "workspace");
        File xmlFile;
        File metaFile;

        if (projectName != null && !projectName.trim().isEmpty()) {
            File runtimeDir = new File(new File(new File(workspaceRoot, projectName.trim()), "configuration"),
                "runtime_profiles");
            xmlFile = new File(runtimeDir, safeProfile + ".xml");
            metaFile = new File(runtimeDir, safeProfile + ".properties");
        } else {
            File profileDir = new File(new File(new File(workspaceRoot, SYNC_PROJECT_NAME), "profiles"),
                safeProfile);
            xmlFile = new File(profileDir, "company_configuration.xml");
            metaFile = new File(profileDir, "metadata.properties");
        }

        if (!xmlFile.isFile()) {
            throw new IOException("Workspace mirror not found: " + xmlFile.getAbsolutePath());
        }

        String xml = readUtf8(xmlFile);
        String solutionType = "";
        if (metaFile.isFile()) {
            Properties metadata = loadProperties(metaFile);
            solutionType = metadata.getProperty("solution_type", "");
        }
        return new MirrorPayload(normalizedProfile, solutionType, LocalUserManagementServlet.sanitizeFullConfig(xml),
            relativize(repoRoot, xmlFile));
    }

    public static File resolveRepoRoot(ServletContext servletContext) throws IOException {
        String realPath = servletContext.getRealPath("/");
        if (realPath == null || realPath.trim().isEmpty()) {
            throw new IOException("Servlet context real path is unavailable");
        }
        File current = new File(realPath).getCanonicalFile();
        while (current != null) {
            if (new File(current, "START.bat").isFile() && new File(current, "workspace").isDirectory()) {
                return current;
            }
            current = current.getParentFile();
        }
        throw new IOException("Could not resolve repo root from " + realPath);
    }

    private static File ensureSyncProject(File workspaceRoot) throws IOException {
        File syncRoot = new File(workspaceRoot, SYNC_PROJECT_NAME);
        if (!syncRoot.isDirectory() && !syncRoot.mkdirs()) {
            throw new IOException("Could not create sync project directory: " + syncRoot.getAbsolutePath());
        }

        File projectFile = new File(syncRoot, ".project");
        if (!projectFile.isFile()) {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<projectDescription>\n"
                + "  <name>" + SYNC_PROJECT_NAME + "</name>\n"
                + "  <comment></comment>\n"
                + "  <projects></projects>\n"
                + "  <buildSpec></buildSpec>\n"
                + "  <natures></natures>\n"
                + "</projectDescription>\n";
            writeUtf8IfChanged(projectFile, xml);
        }

        File readme = new File(syncRoot, "README.md");
        if (!readme.isFile()) {
            String text = "# IW Runtime Sync\n\n"
                + "This project is populated automatically from wizard-saved company profile configuration.\n"
                + "Generated profile XML lives under `profiles/` and mirrors the runtime `SF2QBConfiguration`\n"
                + "payload stored in the database. These files are safe to inspect in the IDE and can be\n"
                + "imported back through WorkspaceProfileSyncServlet.\n";
            writeUtf8IfChanged(readme, text);
        }

        File gitIgnore = new File(syncRoot, ".gitignore");
        if (!gitIgnore.isFile()) {
            writeUtf8IfChanged(gitIgnore, "profiles/\n");
        }

        return syncRoot;
    }

    public static String resolveMappedProjectName(ServletContext servletContext, String solutionType)
            throws IOException {
        return resolveMappedProjectName(resolveRepoRoot(servletContext), solutionType);
    }

    public static String resolveMappedProjectName(File repoRoot, String solutionType) throws IOException {
        String configured = lookupConfiguredProject(solutionType, repoRoot);
        if (configured != null && !configured.isEmpty()) {
            return configured;
        }
        if (solutionType == null) {
            return "";
        }
        String upper = solutionType.toUpperCase(Locale.US);
        if (upper.startsWith("CRM2QB")) {
            return "Creatio_QuickBooks_Integration";
        }
        if (upper.startsWith("CRM2M") || upper.startsWith("CRM2MG")) {
            return "Creatio_Magento2_Integration";
        }
        if (upper.startsWith("SF") || upper.contains("AUTH")) {
            return "SF2AuthNet";
        }
        return "";
    }

    private static String lookupConfiguredProject(String solutionType, File repoRoot) throws IOException {
        if (solutionType == null || solutionType.trim().isEmpty()) {
            return "";
        }
        File mappingFile = new File(repoRoot, "config\\workspace-profile-map.properties");
        if (!mappingFile.isFile()) {
            return "";
        }
        Properties p = loadProperties(mappingFile);
        return p.getProperty(solutionType.trim(), "");
    }

    private static Properties loadProperties(File file) throws IOException {
        Properties p = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            p.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return p;
    }

    private static void storeProperties(File file, Properties p) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            p.store(out, "Generated by WorkspaceProfileSyncSupport");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void writeUtf8IfChanged(File file, String content) throws IOException {
        String existing = file.isFile() ? readUtf8(file) : null;
        if (existing != null && existing.equals(content)) {
            return;
        }
        OutputStream out = null;
        OutputStreamWriter writer = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            } else if (out != null) {
                out.close();
            }
        }
    }

    private static String readUtf8(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        Reader reader = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            reader = new InputStreamReader(in, "UTF-8");
            char[] buf = new char[4096];
            int read;
            while ((read = reader.read(buf)) >= 0) {
                sb.append(buf, 0, read);
            }
        } finally {
            if (reader != null) {
                reader.close();
            } else if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }

    public static String sanitizeProfileKey(String input) {
        String value = safeString(input).replaceAll("[^A-Za-z0-9._-]+", "_");
        return value.isEmpty() ? "profile" : value;
    }

    private static String extractCompanyName(String profileName) {
        int idx = profileName.indexOf(':');
        return idx >= 0 ? profileName.substring(0, idx) : profileName;
    }

    private static String extractUserEmail(String profileName) {
        int idx = profileName.indexOf(':');
        return idx >= 0 && idx + 1 < profileName.length() ? profileName.substring(idx + 1) : "";
    }

    private static String relativize(File repoRoot, File file) {
        String root = repoRoot.getAbsolutePath();
        String path = file.getAbsolutePath();
        if (path.startsWith(root)) {
            String rel = path.substring(root.length());
            if (rel.startsWith("\\") || rel.startsWith("/")) {
                return rel.substring(1);
            }
            return rel;
        }
        return path;
    }

    private static String safeString(String value) {
        return value != null ? value.trim() : "";
    }

    public static final class MirrorResult {
        public final String profileName;
        public final String solutionType;
        public final String syncPath;
        public final String projectPath;

        MirrorResult(String profileName, String solutionType, String syncPath, String projectPath) {
            this.profileName = profileName;
            this.solutionType = solutionType;
            this.syncPath = syncPath;
            this.projectPath = projectPath;
        }
    }

    public static final class MirrorPayload {
        public final String profileName;
        public final String solutionType;
        public final String configXml;
        public final String sourcePath;

        MirrorPayload(String profileName, String solutionType, String configXml, String sourcePath) {
            this.profileName = profileName;
            this.solutionType = solutionType;
            this.configXml = configXml;
            this.sourcePath = sourcePath;
        }
    }
}
