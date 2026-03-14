/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.QueryContext;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.wizards.pages.NewQueryMainPage;
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

public class NewQueryWizard
extends Wizard
implements INewWizard {
    NewQueryMainPage mainPage;
    IWorkbench workbench;
    IStructuredSelection selection;

    public boolean performFinish() {
        if (!this.mainPage.finish()) {
            return false;
        }
        return this.createQuery();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        this.setWindowTitle("New Transaction Flow");
    }

    public void addPages() {
        super.addPages();
        try {
            this.mainPage = new NewQueryMainPage("New Query");
            this.mainPage.setTitle("Project " + (Designer.getSelectedProject() == null ? "null" : Designer.getSelectedProject().getName()));
            this.mainPage.setDescription("Create a new query.");
            this.addPage((IWizardPage)this.mainPage);
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Query", (String)("Unable to create main page: " + e.toString()));
        }
    }

    private boolean createQuery() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr != null) {
            ConfigContext.readIMConfigContext();
            QueryContext nqx = new QueryContext();
            if (!this.mainPage.getNqComposite().saveFlow(nqx)) {
                return false;
            }
            ConfigContext.getQueryList().add(nqx);
            if (!ConfigContext.writeIMConfigContext()) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Query", (String)"Unable to store IM configuration");
                return false;
            }
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
            return true;
        }
        MessageDialog.openError((Shell)this.getShell(), (String)"Query", (String)"No project selected");
        return false;
    }
}

