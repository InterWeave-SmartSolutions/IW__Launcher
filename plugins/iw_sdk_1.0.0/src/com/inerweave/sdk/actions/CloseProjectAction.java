/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.vews.NavigationView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class CloseProjectAction
extends Action {
    private final IWorkbenchWindow window;

    public CloseProjectAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        this.setId("iw_sdk.closeProject");
        this.setActionDefinitionId("iw_sdk.closeProject");
        this.setImageDescriptor(ImageDescriptor.createFromImage((Image)PlatformUI.getWorkbench().getSharedImages().getImage("IMG_OBJ_PROJECT_CLOSED")));
    }

    public void run() {
        IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
        if (nv != null) {
            IProject[] spr;
            IProject[] iProjectArray = spr = ConfigContext.getSelectedProjects(nv);
            int n = 0;
            int n2 = iProjectArray.length;
            while (n < n2) {
                IProject oap = iProjectArray[n];
                if (oap == null) {
                    MessageDialog.openError((Shell)this.window.getShell(), (String)"Project Error", (String)"Unable to locate active project");
                    return;
                }
                try {
                    oap.close(null);
                }
                catch (CoreException e) {
                    MessageDialog.openError((Shell)this.window.getShell(), (String)"Project Error", (String)("Unable to close active project: " + e.toString()));
                    return;
                }
                ++n;
            }
            nv.setAndSelectViewer();
        }
    }
}

