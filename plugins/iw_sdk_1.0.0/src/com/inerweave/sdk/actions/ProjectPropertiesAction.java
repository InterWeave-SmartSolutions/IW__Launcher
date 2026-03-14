/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import com.inerweave.sdk.Designer;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.vews.iw_dialogs.ProjectPropertiesDialog;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ProjectPropertiesAction
extends Action {
    private final IWorkbenchWindow window;

    public ProjectPropertiesAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        this.setId("iw_sdk.projectProperties");
        this.setActionDefinitionId("iw_sdk.projectProperties");
        this.setImageDescriptor(Iw_sdkPlugin.getImageDescriptor("/icons/properties.gif"));
    }

    public void run() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            MessageDialog.openError((Shell)this.window.getShell(), (String)"Project Properties", (String)"No project selected.");
            return;
        }
        String title = "Properties for " + cpr.getName();
        ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(this.window.getShell(), title);
        ppd.create();
        ppd.initializeScreen();
        ppd.open();
        NavigationView nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView");
        if (nv != null) {
            nv.setAndSelectViewer();
        }
    }
}

