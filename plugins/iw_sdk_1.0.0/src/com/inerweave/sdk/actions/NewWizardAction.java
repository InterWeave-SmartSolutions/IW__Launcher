/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;

public class NewWizardAction
extends Action {
    private final IWorkbenchWindow window;

    public NewWizardAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        this.setId("iw_sdk.newWizard");
        this.setActionDefinitionId("iw_sdk.newWizard");
        this.setImageDescriptor(Iw_sdkPlugin.getImageDescriptor("/icons/wizard.gif"));
    }

    public void run() {
        ActionFactory.NEW.create(this.window).run();
    }
}

