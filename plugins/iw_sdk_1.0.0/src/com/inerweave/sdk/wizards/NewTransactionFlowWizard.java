/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.TransactionContext;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.wizards.pages.NewTransactionFlowMainPage;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class NewTransactionFlowWizard
extends Wizard
implements INewWizard {
    NewTransactionFlowMainPage mainPage;
    IWorkbench workbench;
    IStructuredSelection selection;

    public boolean performFinish() {
        if (!this.mainPage.finish()) {
            return false;
        }
        return this.createTransactionFlow();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        this.setWindowTitle("New Transaction Flow");
    }

    public void addPages() {
        super.addPages();
        try {
            this.mainPage = new NewTransactionFlowMainPage("New Transaction Flow");
            this.mainPage.setTitle("Project " + (Designer.getSelectedProject() == null ? "null" : Designer.getSelectedProject().getName()));
            this.mainPage.setDescription("Create a new transaction flow.");
            this.addPage((IWizardPage)this.mainPage);
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)("Unable to create main page: " + e.toString()));
        }
    }

    private boolean createTransactionFlow() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr != null) {
            ConfigContext.readIMConfigContext();
            TransactionContext ntx = new TransactionContext();
            if (!this.mainPage.getNtComposite().saveFlow(ntx)) {
                return false;
            }
            ConfigContext.getTransactionList().add(ntx);
            if (!ConfigContext.writeIMConfigContext()) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"Unable to store IM configuration");
                return false;
            }
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
            return true;
        }
        MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"No project selected");
        return false;
    }
}

