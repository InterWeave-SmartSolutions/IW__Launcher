/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class OpenViewAction
extends Action {
    private final IWorkbenchWindow window;
    private int instanceNum = 0;
    private final String viewId;

    public OpenViewAction(IWorkbenchWindow window, String label, String viewId) {
        this.window = window;
        this.viewId = viewId;
        this.setText(label);
        this.setId("iw_sdk.openView");
        this.setActionDefinitionId("iw_sdk.openView");
    }

    public void run() {
        if (this.window != null) {
            try {
                this.window.getActivePage().showView(this.viewId, Integer.toString(this.instanceNum++), 1);
            }
            catch (PartInitException e) {
                MessageDialog.openError((Shell)this.window.getShell(), (String)"Error", (String)("Error opening view:" + e.getMessage()));
            }
        }
    }
}

