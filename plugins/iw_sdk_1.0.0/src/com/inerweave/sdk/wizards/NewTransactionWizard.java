/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.wizards.pages.NewTransactionMainPage;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.transactionType;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class NewTransactionWizard
extends Wizard
implements INewWizard {
    NewTransactionMainPage mainPage;
    IWorkbench workbench;
    IStructuredSelection selection;

    public boolean performFinish() {
        if (!this.mainPage.finish()) {
            return false;
        }
        return this.createTransaction();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        this.setWindowTitle("New Transaction");
    }

    public void addPages() {
        super.addPages();
        try {
            this.mainPage = new NewTransactionMainPage("New Transaction");
            this.mainPage.setTitle("Project " + (Designer.getSelectedProject() == null ? "null" : Designer.getSelectedProject().getName()));
            this.mainPage.setDescription("Create a new transaction.");
            this.mainPage.setWizard((IWizard)this);
            this.addPage((IWizardPage)this.mainPage);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private boolean createTransaction() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr != null) {
            transactionType ntx = new transactionType();
            if (!this.mainPage.getNtComposite().saveTransaction(ntx)) {
                return false;
            }
            iwmappingsType iwt = ConfigContext.getIwmappingsRoot();
            if (iwt == null) {
                ConfigContext.loadIwmappingsRoot(cpr);
                iwt = ConfigContext.getIwmappingsRoot();
                if (iwt == null) {
                    MessageDialog.openError((Shell)this.getShell(), (String)"Transaction", (String)"Unable to load transaction definitions");
                    return false;
                }
            }
            iwt.addtransaction(ntx);
            try {
                ConfigContext.saveTransactions();
            }
            catch (Exception e) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Transaction", (String)("Unable to save transaction definitions: " + e.toString()));
            }
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
            return true;
        }
        MessageDialog.openError((Shell)this.getShell(), (String)"Transaction", (String)"No project selected");
        return false;
    }
}

