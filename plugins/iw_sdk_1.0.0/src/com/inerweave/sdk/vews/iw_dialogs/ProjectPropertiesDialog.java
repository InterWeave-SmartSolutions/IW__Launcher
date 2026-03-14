/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.composites.NewProjectComposite;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ProjectPropertiesDialog
extends Dialog {
    private NewProjectComposite npc;
    private String projectTitle;
    private IProject cpr;
    private String oldProjectName;

    public ProjectPropertiesDialog(Shell parentShell) {
        super(parentShell);
        this.projectTitle = "Project Properties";
    }

    public ProjectPropertiesDialog(Shell parentShell, String projectTitle) {
        super(parentShell);
        this.projectTitle = projectTitle;
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite)super.createDialogArea(parent);
        this.npc = new NewProjectComposite(container, 0, false);
        this.npc.setLayoutData(new GridData(4, 4, true, true));
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        Button button = this.createButton(parent, 0, "Save", true);
        button.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                ProjectPropertiesDialog.this.saveProjectProperties();
            }
        });
        button.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                ProjectPropertiesDialog.this.saveProjectProperties();
            }
        });
        this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }

    public void initializeScreen() {
        this.npc.getBlackBoxSolution().setSelection(ConfigContext.isProductionPackage());
        this.npc.getHostedSolution().setSelection(ConfigContext.isHosted());
        this.cpr = Designer.getSelectedProject();
        this.oldProjectName = this.cpr.getName();
        this.npc.getProjectName().setText(this.oldProjectName);
        this.npc.getTotalTransactionsValue().setText(String.valueOf(ConfigContext.getTransactions(this.cpr, false).length));
        this.npc.getTotalFlowsValue().setText(String.valueOf(ConfigContext.getTransactionFlows().length));
        String imURL = null;
        String tsURL = null;
        try {
            imURL = this.cpr.getPersistentProperty(this.npc.imURLName);
            tsURL = this.cpr.getPersistentProperty(this.npc.tsURLName);
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)("Unable to obtain deployment URLs: " + e.toString()));
        }
        if (imURL != null) {
            this.npc.getImDeploymentURI().setText(imURL);
        }
        if (tsURL != null) {
            this.npc.getTsDeploymentURI().setText(tsURL);
        }
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/properties1.gif"));
        newShell.setText(this.projectTitle);
    }

    private void saveProjectProperties() {
        ConfigContext.setProductionPackage(this.npc.getBlackBoxSolution().getSelection());
        ConfigContext.setHosted(this.npc.getHostedSolution().getSelection());
        if (!ConfigContext.writeIMConfigContext()) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)"Unable to save IM configuration.");
        }
        if (!ConfigContext.writeTSConfigContext()) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)"Unable to save TS configuration.");
        }
        this.npc.saveDeploymentURL();
        String newProjectName = this.npc.getProjectName().getText().trim();
        if (!this.oldProjectName.equals(newProjectName) && MessageDialog.openQuestion((Shell)this.getShell(), (String)"Project Properties", (String)("Ary you sure you want to rename project flom " + this.oldProjectName + " to " + newProjectName + "?"))) {
            try {
                IProjectDescription opd = this.cpr.getDescription();
                opd.setName(newProjectName);
                this.cpr.move(opd, true, (IProgressMonitor)new NullProgressMonitor());
                this.cpr = ConfigContext.getProjectByName(newProjectName);
                Designer.setSelectedProject(this.cpr);
                IFile pf = this.cpr.getFile("configuration/" + this.oldProjectName + ".properties");
                if (pf.exists()) {
                    pf.move((IPath)new Path(String.valueOf(newProjectName) + ".properties"), true, false, null);
                }
            }
            catch (CoreException e) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)("Unable to rename project: " + e.toString()));
            }
        }
        this.close();
    }
}

