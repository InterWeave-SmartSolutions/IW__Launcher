/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ConfigTSView
extends ViewPart {
    private Action action;
    private Button hostedSolution;
    private Action saveTsConfigurationAction;
    private Button secondaryButton;
    private Button primaryButton;
    private Button blackBoxButton;
    private Button timestampsInTheLogButton;
    private Composite tsComposite;
    private Text bufferSize;
    private CCombo logLevel;
    private Text tsName;
    public static final String ID = "iw_sdk.ConfigTSView";
    private Composite top = null;
    private int minWdth = 0;
    private int minHeight = 0;
    private ScrolledComposite scrolledComposite = null;

    public void createPartControl(Composite parent) {
        this.top = new Composite(parent, 0);
        this.top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle tfca = ConfigTSView.this.top.getClientArea();
                Rectangle tr = ConfigTSView.this.top.computeTrim(tfca.x, tfca.y, tfca.width, tfca.height);
                int wdt = tfca.width - (tr.width - tfca.width);
                int hgt = tfca.height - (tr.height - tfca.height);
                if (wdt <= ConfigTSView.this.minWdth) {
                    wdt = ((ConfigTSView)ConfigTSView.this).tsComposite.computeSize((int)-1, (int)-1).x;
                }
                if (hgt <= ConfigTSView.this.minHeight) {
                    hgt = ConfigTSView.this.minHeight;
                }
                ConfigTSView.this.tsComposite.setSize(wdt, hgt);
            }
        });
        this.top.setLayout((Layout)new FormLayout());
        this.createScrolledComposite();
        this.top.setSize(800, 600);
        this.createActions();
    }

    public void setFocus() {
    }

    private void createScrolledComposite() {
        this.scrolledComposite = new ScrolledComposite(this.top, 768);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        this.scrolledComposite.setLayoutData((Object)formData);
        this.tsComposite = new Composite((Composite)this.scrolledComposite, 0);
        this.tsComposite.setBackground(Display.getCurrent().getSystemColor(1));
        this.tsComposite.setLayout((Layout)new FormLayout());
        CLabel nameLabel = new CLabel(this.tsComposite, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(14, 0);
        formData_1.left = new FormAttachment(0, 20);
        nameLabel.setLayoutData((Object)formData_1);
        nameLabel.setText("Name:");
        CLabel solutionTypeLabel = new CLabel(this.tsComposite, 0);
        solutionTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(28, 0);
        formData_2.left = new FormAttachment((Control)nameLabel, 0, 16384);
        solutionTypeLabel.setLayoutData((Object)formData_2);
        solutionTypeLabel.setText("Production Package:");
        CLabel logLevelLabel = new CLabel(this.tsComposite, 0);
        logLevelLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(56, 0);
        formData_3.left = new FormAttachment((Control)solutionTypeLabel, 0, 16384);
        logLevelLabel.setLayoutData((Object)formData_3);
        logLevelLabel.setText("Log Level:");
        CLabel timestampsInTheLabel = new CLabel(this.tsComposite, 0);
        timestampsInTheLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4 = new FormData();
        formData_4.top = new FormAttachment(70, 0);
        formData_4.left = new FormAttachment((Control)logLevelLabel, 0, 16384);
        timestampsInTheLabel.setLayoutData((Object)formData_4);
        timestampsInTheLabel.setText("Timestamps in the Log:");
        CLabel bufferSizeLabel = new CLabel(this.tsComposite, 0);
        bufferSizeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_5 = new FormData();
        formData_5.top = new FormAttachment(84, 0);
        formData_5.left = new FormAttachment((Control)timestampsInTheLabel, 0, 16384);
        bufferSizeLabel.setLayoutData((Object)formData_5);
        bufferSizeLabel.setText("Buffer Size:");
        this.tsName = new Text(this.tsComposite, 2048);
        FormData formData_6 = new FormData();
        formData_6.right = new FormAttachment((Control)timestampsInTheLabel, 180, 131072);
        formData_6.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_6.left = new FormAttachment((Control)timestampsInTheLabel, 5, 131072);
        this.tsName.setLayoutData((Object)formData_6);
        this.blackBoxButton = new Button(this.tsComposite, 32);
        this.blackBoxButton.setEnabled(false);
        this.blackBoxButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_8 = new FormData();
        formData_8.left = new FormAttachment((Control)this.tsName, 0, 16384);
        formData_8.top = new FormAttachment((Control)solutionTypeLabel, 0, 128);
        this.blackBoxButton.setLayoutData((Object)formData_8);
        this.logLevel = new CCombo(this.tsComposite, 2056);
        this.logLevel.setItems(new String[]{"LOG_ERRORS", "LOG_MINIMUM", "LOG_HTTP", "LOG_REQUEST", "LOG_RESPONSE", "LOG_TRANSACTION", "LOG_TIMING", "LOG_TRANSFORMDATA", "LOG_IO", "LOG_DATA", "LOG_TRANSFORMS", "LOG_OBJECTS", "LOG_OUTPUT", "LOG_PARAMETERS", "LOG_ALL", "LOG_TRACE"});
        this.logLevel.setBackground(Display.getCurrent().getSystemColor(1));
        this.logLevel.setEditable(false);
        FormData formData_9 = new FormData();
        formData_9.left = new FormAttachment(0, 141);
        formData_9.top = new FormAttachment((Control)logLevelLabel, 0, 128);
        this.logLevel.setLayoutData((Object)formData_9);
        this.logLevel.select(0);
        this.timestampsInTheLogButton = new Button(this.tsComposite, 32);
        this.timestampsInTheLogButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_10 = new FormData();
        formData_10.top = new FormAttachment((Control)timestampsInTheLabel, 0, 128);
        formData_10.left = new FormAttachment((Control)this.logLevel, 0, 16384);
        this.timestampsInTheLogButton.setLayoutData((Object)formData_10);
        this.bufferSize = new Text(this.tsComposite, 2048);
        FormData formData_11 = new FormData();
        formData_11.top = new FormAttachment((Control)bufferSizeLabel, 0, 128);
        formData_11.left = new FormAttachment((Control)this.timestampsInTheLogButton, 0, 16384);
        this.bufferSize.setLayoutData((Object)formData_11);
        CLabel failoverTypeLabel = new CLabel(this.tsComposite, 0);
        FormData formData_12_1 = new FormData();
        formData_12_1.top = new FormAttachment(42, 0);
        formData_12_1.left = new FormAttachment((Control)solutionTypeLabel, 0, 16384);
        failoverTypeLabel.setLayoutData((Object)formData_12_1);
        failoverTypeLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        failoverTypeLabel.setText("Failover Type:");
        Composite composite = new Composite(this.tsComposite, 0);
        composite.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_14 = new FormData();
        formData_14.top = new FormAttachment((Control)failoverTypeLabel, 0, 128);
        formData_14.left = new FormAttachment((Control)this.tsName, 0, 16384);
        composite.setLayoutData((Object)formData_14);
        composite.setLayout((Layout)new FormLayout());
        this.primaryButton = new Button(composite, 16);
        FormData formData_13 = new FormData();
        formData_13.top = new FormAttachment(0, 0);
        formData_13.left = new FormAttachment(0, 0);
        this.primaryButton.setLayoutData((Object)formData_13);
        this.primaryButton.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.primaryButton.setText("Primary");
        this.secondaryButton = new Button(composite, 16);
        FormData formData_15 = new FormData();
        formData_15.left = new FormAttachment((Control)this.primaryButton, 0, 131072);
        formData_15.top = new FormAttachment(0, 0);
        this.secondaryButton.setLayoutData((Object)formData_15);
        this.secondaryButton.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.secondaryButton.setText("Secondary");
        this.hostedSolution = new Button(this.tsComposite, 32);
        this.hostedSolution.setEnabled(false);
        this.hostedSolution.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_10_1 = new FormData();
        formData_10_1.right = new FormAttachment(100, -10);
        formData_10_1.top = new FormAttachment((Control)failoverTypeLabel, 0, 128);
        this.hostedSolution.setLayoutData((Object)formData_10_1);
        CLabel hostedLabel = new CLabel(this.tsComposite, 0);
        hostedLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9_1 = new FormData();
        formData_9_1.top = new FormAttachment((Control)this.hostedSolution, 0, 128);
        formData_9_1.right = new FormAttachment((Control)this.hostedSolution, -20, 16384);
        hostedLabel.setLayoutData((Object)formData_9_1);
        hostedLabel.setText("Multi-tenant Solution:");
        this.tsComposite.setSize(494, 349);
        this.scrolledComposite.setContent((Control)this.tsComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    public void initializeScreen() {
        try {
            if (!ConfigContext.readTSConfigContext()) {
                if (Designer.getSelectedProject() != null) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Read Error", (String)"Unable to read TS configuration ");
                }
                return;
            }
            this.tsName.setText(ConfigContext.getTsName());
            if (ConfigContext.isTsPrimary()) {
                this.primaryButton.setSelection(true);
                this.secondaryButton.setSelection(false);
            } else {
                this.primaryButton.setSelection(false);
                this.secondaryButton.setSelection(true);
            }
            if (ConfigContext.isProductionPackage()) {
                this.blackBoxButton.setSelection(true);
            } else {
                this.blackBoxButton.setSelection(false);
            }
            if (ConfigContext.isHosted()) {
                this.hostedSolution.setSelection(true);
            } else {
                this.hostedSolution.setSelection(false);
            }
            this.logLevel.select(ConfigContext.getComboIndex(this.logLevel.getItems(), ConfigContext.getTsLogLevel()));
            this.timestampsInTheLogButton.setSelection(ConfigContext.isTsTimeStamping());
            this.bufferSize.setText(Integer.toString(ConfigContext.getImBufferSize()));
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"TS Config View Error", (String)("Unable to initialize TS configuration view: " + e));
        }
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.action);
        tbm.add((IAction)this.saveTsConfigurationAction);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.action);
        manager.add((IAction)this.saveTsConfigurationAction);
    }

    private void createActions() {
        this.saveTsConfigurationAction = new Action("Save TS Configuration"){

            public void run() {
                ConfigTSView.this.saveConfig();
            }
        };
        this.saveTsConfigurationAction.setAccelerator(262227);
        this.saveTsConfigurationAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.action = new Action("Refresh"){

            public void run() {
                ConfigTSView.this.initializeScreen();
            }
        };
        this.action.setToolTipText("Refresh");
        this.action.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
    }

    private void saveConfig() {
        ConfigContext.setTsName(this.tsName.getText());
        ConfigContext.setTsPrimary(this.primaryButton.getSelection());
        ConfigContext.setTsLogLevel(this.logLevel.getItem(this.logLevel.getSelectionIndex()));
        ConfigContext.setTsTimeStamping(this.timestampsInTheLogButton.getSelection());
        ConfigContext.setTsBufferSize(Integer.parseInt(this.bufferSize.getText()));
        if (!ConfigContext.writeTSConfigContext()) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Write Error", (String)"Unable to store TS configuration ");
        }
    }
}

