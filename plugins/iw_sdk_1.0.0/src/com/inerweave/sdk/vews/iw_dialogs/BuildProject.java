/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class BuildProject
extends TitleAreaDialog {
    private Button defaultStyleBuildButton;
    private CheckboxTableViewer checkboxTableViewer;
    private Table table;
    private IProject[] allProjects;
    private IProject[] selectedProjects;
    private boolean defaultStyle = false;
    private boolean splitTransactions = false;

    public boolean isSplitTransactions() {
        return this.splitTransactions;
    }

    public BuildProject(Shell parentShell, IProject[] allProjects) {
        super(parentShell);
        this.allProjects = allProjects;
        this.selectedProjects = new IProject[0];
    }

    public BuildProject(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite)super.createDialogArea(parent);
        Composite container = new Composite(area, 0);
        container.setLayout((Layout)new FormLayout());
        container.setBackground(Display.getCurrent().getSystemColor(1));
        container.setLayoutData((Object)new GridData(1808));
        this.checkboxTableViewer = CheckboxTableViewer.newCheckList((Composite)container, (int)2048);
        this.table = this.checkboxTableViewer.getTable();
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(85, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        this.table.setLayoutData((Object)formData);
        this.defaultStyleBuildButton = new Button(container, 32);
        this.defaultStyleBuildButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                BuildProject.this.defaultStyle = BuildProject.this.defaultStyleBuildButton.getSelection();
                BuildProject.this.table.setEnabled(!BuildProject.this.defaultStyle);
            }
        });
        this.defaultStyleBuildButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/web_library_project_obj.gif"));
        this.defaultStyleBuildButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(89, 0);
        formData_1.left = new FormAttachment(2, 0);
        this.defaultStyleBuildButton.setLayoutData((Object)formData_1);
        this.defaultStyleBuildButton.setText("Default Style Build");
        Object[] prnm = new String[this.allProjects.length];
        int i = 0;
        while (i < this.allProjects.length) {
            prnm[i] = this.allProjects[i].getName();
            ++i;
        }
        this.checkboxTableViewer.add(prnm);
        final Button splitTransactionsButton = new Button(container, 32);
        splitTransactionsButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                BuildProject.this.splitTransactions = splitTransactionsButton.getSelection();
            }
        });
        splitTransactionsButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment((Control)this.defaultStyleBuildButton, 0, 128);
        formData_2.left = new FormAttachment((Control)this.defaultStyleBuildButton, 15, 131072);
        splitTransactionsButton.setLayoutData((Object)formData_2);
        splitTransactionsButton.setText("Split Transactions");
        this.setMessage("Select projects to include in the solution build.");
        return area;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        this.createButton(parent, 0, IDialogConstants.OK_LABEL, true);
        this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }

    public boolean isDefaultStyle() {
        return this.defaultStyle;
    }

    public IProject[] getSelectedProjects() {
        return this.selectedProjects;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Biuld Solution");
        newShell.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/web_library_project_obj.gif"));
    }

    protected void okPressed() {
        Object[] si = this.checkboxTableViewer.getCheckedElements();
        this.selectedProjects = new IProject[si.length];
        int i = 0;
        while (i < si.length) {
            this.selectedProjects[i] = ResourcesPlugin.getWorkspace().getRoot().getProject(si[i].toString());
            ++i;
        }
        super.okPressed();
    }
}

