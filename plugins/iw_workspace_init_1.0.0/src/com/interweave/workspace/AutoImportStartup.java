package com.interweave.workspace;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.IStartup;
import java.io.File;

/**
 * Eclipse startup extension that automatically imports any workspace directories
 * containing .project files that aren't yet registered in the workspace.
 *
 * This ensures that projects created by the Portal sync (IW_Runtime_Sync,
 * GeneratedProfiles) or added to workspace/ externally appear in the
 * InterWeave Navigator without manual File > Import.
 */
public class AutoImportStartup implements IStartup {

    public void earlyStartup() {
        try {
            importWorkspaceProjects();
        } catch (Exception e) {
            // Log but don't prevent IDE from starting
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

            // Skip infrastructure directories that aren't real InterWeave projects
            String name = dir.getName();
            if ("GeneratedProfiles".equals(name) || "IW_Runtime_Sync".equals(name)) continue;

            File projectFile = new File(dir, ".project");
            if (!projectFile.exists()) continue;

            IProject project = root.getProject(dir.getName());

            // Already registered and open — nothing to do
            if (project.exists() && project.isOpen()) {
                continue;
            }

            try {
                if (!project.exists()) {
                    // Load the .project description and create the project in workspace
                    IProjectDescription desc = workspace.loadProjectDescription(
                        new Path(projectFile.getAbsolutePath()));
                    project.create(desc, null);
                    System.out.println("[IW Auto-Import] Created project: " + dir.getName());
                }

                if (!project.isOpen()) {
                    project.open(null);
                    System.out.println("[IW Auto-Import] Opened project: " + dir.getName());
                }
            } catch (CoreException e) {
                System.err.println("[IW Auto-Import] Failed to import " + dir.getName()
                    + ": " + e.getMessage());
            }
        }
    }
}
