package com.interweave.workspace;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.IStartup;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Eclipse startup extension that automatically imports workspace directories
 * into the IDE Navigator. Creates .project files if missing, then registers
 * and opens each project.
 *
 * Skips: .metadata, GeneratedProfiles, IW_Runtime_Sync (infrastructure dirs).
 * This ensures projects created by the Portal sync or added externally
 * appear in the Navigator without manual File > Import.
 */
public class AutoImportStartup implements IStartup {

    private static final String[] SKIP_DIRS = {
        "GeneratedProfiles", "IW_Runtime_Sync", ".metadata"
    };

    public void earlyStartup() {
        try {
            importWorkspaceProjects();
        } catch (Exception e) {
            System.err.println("[IW Auto-Import] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void importWorkspaceProjects() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        File workspaceDir = root.getLocation().toFile();

        File[] dirs = workspaceDir.listFiles();
        if (dirs == null) return;

        for (File dir : dirs) {
            if (!dir.isDirectory()) continue;
            if (dir.getName().startsWith(".")) continue;

            String name = dir.getName();
            if (shouldSkip(name)) continue;

            // Must have at least configuration/ or xslt/ to be a real project
            File configDir = new File(dir, "configuration");
            File xsltDir = new File(dir, "xslt");
            if (!configDir.exists() && !xsltDir.exists()) continue;

            File projectFile = new File(dir, ".project");
            if (!projectFile.exists()) {
                createProjectFile(projectFile, name);
            }
            if (!projectFile.exists()) continue;

            IProject project = root.getProject(name);

            if (project.exists() && project.isOpen()) {
                continue;
            }

            try {
                if (!project.exists()) {
                    IProjectDescription desc = workspace.loadProjectDescription(
                        new Path(projectFile.getAbsolutePath()));
                    project.create(desc, null);
                    System.out.println("[IW Auto-Import] Created project: " + name);
                }

                if (!project.isOpen()) {
                    project.open(null);
                    System.out.println("[IW Auto-Import] Opened project: " + name);
                }
            } catch (CoreException e) {
                System.err.println("[IW Auto-Import] Failed to import " + name
                    + ": " + e.getMessage());
            }
        }
    }

    private boolean shouldSkip(String name) {
        for (String skip : SKIP_DIRS) {
            if (skip.equals(name)) return true;
        }
        return false;
    }

    private void createProjectFile(File projectFile, String projectName) {
        try {
            FileWriter fw = new FileWriter(projectFile);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.write("<projectDescription>\n");
            fw.write("  <name>" + projectName + "</name>\n");
            fw.write("  <comment></comment>\n");
            fw.write("  <projects></projects>\n");
            fw.write("  <buildSpec></buildSpec>\n");
            fw.write("  <natures></natures>\n");
            fw.write("</projectDescription>\n");
            fw.close();
            System.out.println("[IW Auto-Import] Generated .project for: " + projectName);
        } catch (IOException e) {
            System.err.println("[IW Auto-Import] Could not create .project for "
                + projectName + ": " + e.getMessage());
        }
    }
}
