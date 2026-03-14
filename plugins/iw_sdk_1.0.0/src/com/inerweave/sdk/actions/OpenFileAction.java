/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class OpenFileAction
extends Action {
    private final IWorkbenchWindow window;

    public OpenFileAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        this.setId("iw_sdk.openFile");
        this.setActionDefinitionId("iw_sdk.openFile");
    }

    public void run() {
        FileDialog fd = new FileDialog(this.window.getShell(), 4096);
        fd.setFilterExtensions(new String[]{"*.xslt"});
        fd.open();
    }
}

