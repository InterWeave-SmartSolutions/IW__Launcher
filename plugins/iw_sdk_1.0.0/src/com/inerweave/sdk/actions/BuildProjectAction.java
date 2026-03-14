/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.actions;

import com.inerweave.sdk.Designer;
import com.inerweave.sdk.ProjectActions;
import com.inerweave.sdk.vews.iw_dialogs.BuildProject;
import iw_sdk.Iw_sdkPlugin;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

public class BuildProjectAction
extends Action {
    private final IWorkbenchWindow window;
    private int operationMask;

    public BuildProjectAction(String text, IWorkbenchWindow window, int operationMask, String id, String image) {
        super(text);
        this.window = window;
        this.operationMask = operationMask;
        this.setId(id);
        this.setActionDefinitionId(id);
        if (image != null) {
            this.setImageDescriptor(Iw_sdkPlugin.getImageDescriptor(image));
        }
    }

    public void run() {
        IProject[] prs;
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            MessageDialog.openError((Shell)this.window.getShell(), (String)"Bulid Project", (String)"No Project to build!");
            return;
        }
        if (!cpr.isOpen()) {
            MessageDialog.openError((Shell)this.window.getShell(), (String)"Bulid Project", (String)"Cannot build closed project!");
            return;
        }
        ArrayList<IProject> prSel = new ArrayList<IProject>();
        IProject[] iProjectArray = prs = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        int n = 0;
        int n2 = iProjectArray.length;
        while (n < n2) {
            IProject cp = iProjectArray[n];
            if (cp.isOpen() && !cp.equals((Object)cpr)) {
                prSel.add(cp);
            }
            ++n;
        }
        IProject[] cprsAll = prSel.toArray(new IProject[0]);
        BuildProject rlsd = new BuildProject(this.window.getShell(), cprsAll);
        rlsd.create();
        rlsd.setTitle("Lead Project to build: " + cpr.getName());
        if (rlsd.open() != 0) {
            return;
        }
        IProject[] cprs = rlsd.getSelectedProjects();
        boolean defaultStyle = rlsd.isDefaultStyle();
        boolean splitTransactions = rlsd.isSplitTransactions();
        StringBuffer an = new StringBuffer(String.valueOf(cpr.getName()) + "; ");
        IProject[] iProjectArray2 = cprs;
        int n3 = 0;
        int n4 = iProjectArray2.length;
        while (n3 < n4) {
            IProject cp = iProjectArray2[n3];
            an.append(String.valueOf(cp.getName()) + "; ");
            ++n3;
        }
        String allNames = an.toString();
        ProjectActions runnableWithProgress = new ProjectActions(this.operationMask, cpr, cprs, allNames, defaultStyle, splitTransactions);
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.window.getShell());
        try {
            dialog.run(true, true, (IRunnableWithProgress)runnableWithProgress);
            String err = runnableWithProgress.getErrorMessage();
            if (err != null) {
                MessageDialog.openError((Shell)this.window.getShell(), (String)"Bulid Project", (String)err);
                return;
            }
        }
        catch (InvocationTargetException e) {
            MessageDialog.openError((Shell)this.window.getShell(), (String)"Bulid Project", (String)("Can not invoke progress operation: " + e));
            e.printStackTrace();
            return;
        }
        catch (InterruptedException e) {
            return;
        }
        String message = "";
        switch (this.operationMask) {
            case 1: {
                message = "Trasformers for project(s) " + allNames + "have been compiled successfully!";
                break;
            }
            case 4: {
                message = "Integration Manager for project(s) " + allNames + "has been built successfully!";
                break;
            }
            case 11: {
                message = "Transformation Server for project(s) " + allNames + "has been built successfully!";
                break;
            }
            case 15: {
                message = "Project(s) " + allNames + "have been built successfully!";
            }
        }
        MessageDialog.openInformation((Shell)this.window.getShell(), (String)"Bulid Project", (String)message);
    }
}

