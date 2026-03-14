/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.IwConnection;
import com.inerweave.sdk.composites.NewConnectionWizardComposite;
import com.inerweave.sdk.vews.DataMapView;
import com.inerweave.sdk.vews.NavigationView;
import com.iwtransactions.datamapType;
import com.iwtransactions.iwmappingsType;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ConnectionView
extends ViewPart {
    private Action asveAllConnections;
    private Action saveConnectionAction;
    private Composite container;
    private NewConnectionWizardComposite connectionComposite;
    private int minWdth = 0;
    private int minHeight = 0;
    public static final String ID = "iw_sdk.ConnectionView";
    private datamapType currentDataMap;
    private boolean newConnection = false;
    private IProject currentProject;
    private iwmappingsType currentRoot;

    public void createPartControl(Composite parent) {
        this.container = new Composite(parent, 0);
        this.container.setLayout((Layout)new FormLayout());
        this.container.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle sfca = ConnectionView.this.container.getClientArea();
                if (ConnectionView.this.connectionComposite != null) {
                    int wdt = sfca.width;
                    int hgt = sfca.height;
                    if (wdt < ConnectionView.this.minWdth) {
                        wdt = ConnectionView.this.minWdth;
                    }
                    if (hgt < ConnectionView.this.minHeight) {
                        hgt = ConnectionView.this.minHeight;
                    }
                    ConnectionView.this.connectionComposite.setSize(wdt, hgt);
                }
            }
        });
        this.container.setBackground(Display.getCurrent().getSystemColor(1));
        ScrolledComposite scrolledComposite_1 = new ScrolledComposite(this.container, 768);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        scrolledComposite_1.setLayoutData((Object)formData);
        this.connectionComposite = new NewConnectionWizardComposite((Composite)scrolledComposite_1, 0);
        this.connectionComposite.setBounds(0, 0, 490, 345);
        this.connectionComposite.setBackground(Display.getCurrent().getSystemColor(1));
        scrolledComposite_1.setContent((Control)this.connectionComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    private void createActions() {
        this.saveConnectionAction = new Action("Save to Data Map"){

            public void run() {
                ConnectionView.this.saveConnection();
            }
        };
        this.saveConnectionAction.setToolTipText("Save Connection into Data Map");
        this.saveConnectionAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.asveAllConnections = new Action("Save to all Data Maps"){

            public void run() {
            }
        };
        this.asveAllConnections.setEnabled(false);
        this.asveAllConnections.setToolTipText("Save Connection to All Data Maps");
        this.asveAllConnections.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/saveall_edit.gif"));
    }

    private void initializeToolBar() {
        IToolBarManager toolbarManager = this.getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add((IAction)this.saveConnectionAction);
        toolbarManager.add((IAction)this.asveAllConnections);
    }

    private void initializeMenu() {
        IMenuManager menuManager = this.getViewSite().getActionBars().getMenuManager();
        menuManager.add((IAction)this.saveConnectionAction);
        menuManager.add((IAction)this.asveAllConnections);
    }

    public void setFocus() {
    }

    public void initializeScreen(boolean newConnection) {
        this.currentProject = Designer.getSelectedProject();
        this.currentRoot = ConfigContext.getIwmappingsRoot();
        this.currentDataMap = ConfigContext.getCurrentDataMap();
        this.newConnection = newConnection;
        if (newConnection) {
            this.connectionComposite.getUser().setText("");
            this.connectionComposite.getDriver().setText("");
            this.connectionComposite.getUrl().setText("");
            this.connectionComposite.getPassword().setText("");
            this.connectionComposite.getRefPassword().setText("");
            this.connectionComposite.getPasswordAsReference().setSelection(false);
            this.connectionComposite.getConfirmPassword().setText("");
            this.setPartName("Connection View");
            this.setTitleToolTip("New Connection");
            return;
        }
        IwConnection currentConnection = ConfigContext.getCurrentConnection();
        if (currentConnection == null || currentConnection.isNull()) {
            return;
        }
        String title = ConfigContext.getCurrentConnectionName();
        String toolTip = "Connection " + title;
        if (this.currentDataMap != null) {
            if (this.currentProject == null) {
                this.currentDataMap = null;
            } else {
                String dmcn = ConfigContext.getDataMapConnectionName(this.currentProject, this.currentDataMap);
                if (dmcn == null) {
                    this.currentDataMap = null;
                } else if (!dmcn.equals(title)) {
                    this.currentDataMap = null;
                }
            }
        }
        if (this.currentDataMap != null) {
            try {
                String newTitle = this.currentDataMap.getname().getValue().trim();
                toolTip = "Connection " + title + " in " + this.currentProject.getName() + "/" + ConfigContext.getCurrentTransaction().getname().getValue() + "/" + newTitle;
                title = String.valueOf(newTitle) + "->";
            }
            catch (Exception e) {
                title = ConfigContext.getCurrentConnectionName();
            }
        }
        this.setPartName(title);
        this.setTitleToolTip(toolTip);
        this.connectionComposite.getUser().setText(currentConnection.getUserName());
        this.connectionComposite.getDriver().setText(currentConnection.getDriver());
        this.connectionComposite.getUrl().setText(currentConnection.getUrl());
        String password = currentConnection.getPassword();
        if (password.startsWith("@") && password.endsWith("@") || password.startsWith("?") && password.endsWith("?")) {
            this.connectionComposite.getPasswordAsReference().setSelection(true);
            this.connectionComposite.getPassword().setText("");
            this.connectionComposite.getPassword().setVisible(false);
            this.connectionComposite.getPassword().setEnabled(false);
            this.connectionComposite.getRefPassword().setVisible(true);
            this.connectionComposite.getRefPassword().setEnabled(true);
            this.connectionComposite.getRefPassword().setText(password);
            this.connectionComposite.getConfirmPassword().setEnabled(false);
        } else {
            this.connectionComposite.getPasswordAsReference().setSelection(false);
            this.connectionComposite.getRefPassword().setVisible(false);
            this.connectionComposite.getRefPassword().setEnabled(false);
            this.connectionComposite.getPassword().setVisible(true);
            this.connectionComposite.getPassword().setEnabled(true);
            this.connectionComposite.getPassword().setText(password);
            this.connectionComposite.getRefPassword().setText("");
            this.connectionComposite.getConfirmPassword().setEnabled(true);
        }
        this.connectionComposite.getConfirmPassword().setText("");
    }

    public void saveConnection() {
        String dmcn;
        if (!this.validate()) {
            return;
        }
        if (this.currentDataMap == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)"Unable to save Connection into Data Map: No Data Map selected or Connection is out of sync with Data Map");
            return;
        }
        IProject cpr = Designer.getSelectedProject();
        iwmappingsType ciwmr = null;
        boolean mod = false;
        if (cpr == null || !cpr.getName().equals(this.currentProject.getName())) {
            mod = true;
            ciwmr = ConfigContext.getIwmappingsRoot();
            Designer.setSelectedProject(this.currentProject);
            ConfigContext.setIwmappingsRoot(this.currentRoot);
        }
        try {
            String password = "";
            password = this.connectionComposite.getPasswordAsReference().getSelection() ? this.connectionComposite.getRefPassword().getText().trim() : this.connectionComposite.getPassword().getText().trim();
            if (this.newConnection) {
                if (this.currentDataMap.getdriverCount() > 0) {
                    this.currentDataMap.replacedriverAt(this.connectionComposite.getDriver().getText(), 0);
                } else {
                    this.currentDataMap.adddriver(this.connectionComposite.getDriver().getText());
                }
                if (this.currentDataMap.geturlCount() > 0) {
                    this.currentDataMap.replaceurlAt(this.connectionComposite.getUrl().getText(), 0);
                } else {
                    this.currentDataMap.addurl(this.connectionComposite.getUrl().getText());
                }
                if (this.currentDataMap.getuserCount() > 0) {
                    this.currentDataMap.replaceuserAt(this.connectionComposite.getUser().getText(), 0);
                } else {
                    this.currentDataMap.adduser(this.connectionComposite.getUser().getText());
                }
                if (this.currentDataMap.getpasswordCount() > 0) {
                    this.currentDataMap.replacepasswordAt(password, 0);
                } else {
                    this.currentDataMap.addpassword(password);
                }
            } else {
                this.currentDataMap.replacedriverAt(this.connectionComposite.getDriver().getText(), 0);
                this.currentDataMap.replaceurlAt(this.connectionComposite.getUrl().getText(), 0);
                this.currentDataMap.replaceuserAt(this.connectionComposite.getUser().getText(), 0);
                this.currentDataMap.replacepasswordAt(password, 0);
            }
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)"Unable to save transaction.xml");
                return;
            }
            NavigationView nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)("Unable to save connection: " + e));
        }
        if (this.newConnection && (dmcn = ConfigContext.getDataMapConnectionName(this.currentProject, this.currentDataMap)) != null) {
            String title = dmcn;
            String toolTip = "Connection " + title;
            try {
                String newTitle = this.currentDataMap.getname().getValue().trim();
                toolTip = "Connection " + title + " in " + this.currentProject.getName() + "/" + ConfigContext.getCurrentTransaction().getname().getValue() + "/" + newTitle;
                title = String.valueOf(newTitle) + "->";
            }
            catch (Exception e) {
                title = dmcn;
            }
            this.setPartName(title);
            this.setTitleToolTip(toolTip);
        }
        DataMapView dv = (DataMapView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.DataMapView");
        try {
            if (dv != null && dv.getCurrentProject().getName().equals(this.currentProject.getName()) && dv.getCurrentDataMap().getname().equals(this.currentDataMap.getname())) {
                dv.processConnectionNames(dv.getConnectionNames());
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)("Unable to refresh Data Map View: " + e));
        }
        if (mod) {
            Designer.setSelectedProject(cpr);
            ConfigContext.setIwmappingsRoot(ciwmr);
        }
    }

    private boolean validate() {
        if (this.connectionComposite.getPasswordAsReference().getSelection()) {
            String pwd = this.connectionComposite.getRefPassword().getText().trim();
            if (!(pwd.length() >= 3 && (pwd.startsWith("@") && pwd.endsWith("@") || pwd.startsWith("?") && pwd.endsWith("?")))) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)"Incorrect password reference. Please re-enter.");
                return false;
            }
        } else if (!this.connectionComposite.getPassword().getText().equals(this.connectionComposite.getConfirmPassword().getText())) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Connection", (String)"Passwords entered are out of sync. Please re-enter.");
            this.connectionComposite.getPassword().setText("");
            this.connectionComposite.getConfirmPassword().setText("");
            return false;
        }
        return true;
    }
}

