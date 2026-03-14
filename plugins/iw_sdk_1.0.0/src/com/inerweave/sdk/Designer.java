/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.ApplicationWorkbenchAdvisor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

public class Designer
implements IPlatformRunnable {
    private static IProject selectedProject;
    private static int projectVersion;
    private static int hostedVersion;

    static {
        projectVersion = 220048;
        hostedVersion = 18;
    }

    public static int getProjectVersion() {
        return projectVersion;
    }

    public Object run(Object args) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
            int returnCode = PlatformUI.createAndRunWorkbench((Display)display, (WorkbenchAdvisor)new ApplicationWorkbenchAdvisor());
            if (returnCode == 1) {
                Integer n = IPlatformRunnable.EXIT_RESTART;
                return n;
            }
            Integer n = IPlatformRunnable.EXIT_OK;
            return n;
        }
        finally {
            display.dispose();
        }
    }

    public static void setFirstProjectSelected() {
        IProject[] prs = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        if (prs.length > 0) {
            Designer.setSelectedProject(prs[0]);
        } else {
            Designer.setSelectedProject(null);
        }
    }

    public static final IProject getSelectedProject() {
        return selectedProject;
    }

    public static final void setSelectedProject(IProject selectedProject) {
        Designer.selectedProject = selectedProject;
    }

    public static int getHostedVersion() {
        return hostedVersion;
    }
}

