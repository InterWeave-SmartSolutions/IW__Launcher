/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
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

public class ConfigBDView
extends ViewPart {
    private Text refreshInterval;
    private Action action;
    private Button hostedSolution;
    private Action saveImConfigurationAction;
    private Button secondaryButton;
    private Button primaryButton;
    private Button runAtStartUpCheck;
    private Composite bdComposite;
    private Text bufferSize;
    private Text heartBeatInterval;
    private Text failoverURL;
    private Text secondaryTSURL;
    private Text primaryTSURL;
    private Text secondaryTSURLT;
    private Text primaryTSURLT;
    private Text secondaryTSURL1;
    private Text primaryTSURL1;
    private Text secondaryTSURLT1;
    private Text primaryTSURLT1;
    private Text secondaryTSURLD;
    private Text primaryTSURLD;
    private Text bdName;
    public static final String ID = "iw_sdk.ConfigBDView";
    private int minWdth = 0;
    private int minHeight = 0;

    public void createPartControl(Composite parent) {
        final Composite top = new Composite(parent, 0);
        top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle tfca = top.getClientArea();
                Rectangle tr = top.computeTrim(tfca.x, tfca.y, tfca.width, tfca.height);
                int wdt = tfca.width - (tr.width - tfca.width);
                int hgt = tfca.height - (tr.height - tfca.height);
                if (wdt <= ConfigBDView.this.minWdth) {
                    wdt = ((ConfigBDView)ConfigBDView.this).bdComposite.computeSize((int)-1, (int)-1).x;
                }
                if (hgt <= ConfigBDView.this.minHeight) {
                    hgt = ConfigBDView.this.minHeight;
                }
                ConfigBDView.this.bdComposite.setSize(wdt, hgt);
            }
        });
        top.setLayout((Layout)new FormLayout());
        ScrolledComposite scrolledComposite = new ScrolledComposite(top, 768);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        scrolledComposite.setLayoutData((Object)formData);
        this.bdComposite = new Composite((Composite)scrolledComposite, 0);
        this.bdComposite.setLocation(0, 0);
        this.bdComposite.setBackground(Display.getCurrent().getSystemColor(1));
        this.bdComposite.setLayout((Layout)new FormLayout());
        CLabel nameLabel = new CLabel(this.bdComposite, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(4, 0);
        formData_1.left = new FormAttachment(0, 15);
        nameLabel.setLayoutData((Object)formData_1);
        nameLabel.setText("Name:");
        CLabel primaryTsUrlLabel = new CLabel(this.bdComposite, 0);
        primaryTsUrlLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(10, 0);
        formData_2.left = new FormAttachment((Control)nameLabel, 0, 16384);
        primaryTsUrlLabel.setLayoutData((Object)formData_2);
        primaryTsUrlLabel.setText("Production A Primary TS URL:");
        CLabel secondaryTsUrlLabel = new CLabel(this.bdComposite, 0);
        secondaryTsUrlLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(16, 0);
        formData_3.left = new FormAttachment((Control)primaryTsUrlLabel, 0, 16384);
        secondaryTsUrlLabel.setLayoutData((Object)formData_3);
        secondaryTsUrlLabel.setText("Production A Secondary TS URL:");
        CLabel primaryTsUrlLabelT = new CLabel(this.bdComposite, 0);
        primaryTsUrlLabelT.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2T = new FormData();
        formData_2T.top = new FormAttachment(22, 0);
        formData_2T.left = new FormAttachment((Control)nameLabel, 0, 16384);
        primaryTsUrlLabelT.setLayoutData((Object)formData_2T);
        primaryTsUrlLabelT.setText("Production B Primary TS URL:");
        CLabel secondaryTsUrlLabelT = new CLabel(this.bdComposite, 0);
        secondaryTsUrlLabelT.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3T = new FormData();
        formData_3T.top = new FormAttachment(28, 0);
        formData_3T.left = new FormAttachment((Control)primaryTsUrlLabelT, 0, 16384);
        secondaryTsUrlLabelT.setLayoutData((Object)formData_3T);
        secondaryTsUrlLabelT.setText("Production B Secondary TS URL:");
        CLabel primaryTsUrlLabel1 = new CLabel(this.bdComposite, 0);
        primaryTsUrlLabel1.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_21 = new FormData();
        formData_21.top = new FormAttachment(34, 0);
        formData_21.left = new FormAttachment((Control)nameLabel, 0, 16384);
        primaryTsUrlLabel1.setLayoutData((Object)formData_21);
        primaryTsUrlLabel1.setText("Production C Primary TS URL:");
        CLabel secondaryTsUrlLabel1 = new CLabel(this.bdComposite, 0);
        secondaryTsUrlLabel1.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_31 = new FormData();
        formData_31.top = new FormAttachment(40, 0);
        formData_31.left = new FormAttachment((Control)primaryTsUrlLabel, 0, 16384);
        secondaryTsUrlLabel1.setLayoutData((Object)formData_31);
        secondaryTsUrlLabel1.setText("Production C Secondary TS URL:");
        CLabel primaryTsUrlLabelT1 = new CLabel(this.bdComposite, 0);
        primaryTsUrlLabelT1.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2T1 = new FormData();
        formData_2T1.top = new FormAttachment(46, 0);
        formData_2T1.left = new FormAttachment((Control)nameLabel, 0, 16384);
        primaryTsUrlLabelT1.setLayoutData((Object)formData_2T1);
        primaryTsUrlLabelT1.setText("Production D Primary TS URL:");
        CLabel secondaryTsUrlLabelT1 = new CLabel(this.bdComposite, 0);
        secondaryTsUrlLabelT1.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3T1 = new FormData();
        formData_3T1.top = new FormAttachment(52, 0);
        formData_3T1.left = new FormAttachment((Control)primaryTsUrlLabelT, 0, 16384);
        secondaryTsUrlLabelT1.setLayoutData((Object)formData_3T1);
        secondaryTsUrlLabelT1.setText("Production D Secondary TS URL:");
        CLabel primaryTsUrlLabelD = new CLabel(this.bdComposite, 0);
        primaryTsUrlLabelD.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2D = new FormData();
        formData_2D.top = new FormAttachment(58, 0);
        formData_2D.left = new FormAttachment((Control)nameLabel, 0, 16384);
        primaryTsUrlLabelD.setLayoutData((Object)formData_2D);
        primaryTsUrlLabelD.setText("Production E Primary TS URL:");
        CLabel secondaryTsUrlLabelD = new CLabel(this.bdComposite, 0);
        secondaryTsUrlLabelD.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3D = new FormData();
        formData_3D.top = new FormAttachment(64, 0);
        formData_3D.left = new FormAttachment((Control)primaryTsUrlLabelD, 0, 16384);
        secondaryTsUrlLabelD.setLayoutData((Object)formData_3D);
        secondaryTsUrlLabelD.setText("Production E Secondary TS URL:");
        CLabel failoverUrlLabel = new CLabel(this.bdComposite, 0);
        failoverUrlLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_5 = new FormData();
        formData_5.top = new FormAttachment(76, 0);
        formData_5.left = new FormAttachment(0, 15);
        failoverUrlLabel.setLayoutData((Object)formData_5);
        failoverUrlLabel.setText("Failover URL:");
        CLabel heartbeatIntervalLabel = new CLabel(this.bdComposite, 0);
        heartbeatIntervalLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment(82, 0);
        formData_6.left = new FormAttachment((Control)failoverUrlLabel, 0, 16384);
        heartbeatIntervalLabel.setLayoutData((Object)formData_6);
        heartbeatIntervalLabel.setText("Heartbeat Interval:");
        CLabel runAtStartupLabel = new CLabel(this.bdComposite, 0);
        runAtStartupLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_7 = new FormData();
        formData_7.top = new FormAttachment(88, 0);
        formData_7.left = new FormAttachment((Control)heartbeatIntervalLabel, 0, 16384);
        runAtStartupLabel.setLayoutData((Object)formData_7);
        runAtStartupLabel.setText("Run at Start-up:");
        CLabel bufferSizeLabel = new CLabel(this.bdComposite, 0);
        bufferSizeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_8 = new FormData();
        formData_8.top = new FormAttachment(94, 0);
        formData_8.left = new FormAttachment((Control)runAtStartupLabel, 0, 16384);
        bufferSizeLabel.setLayoutData((Object)formData_8);
        bufferSizeLabel.setText("Buffer Size:");
        this.bdName = new Text(this.bdComposite, 2048);
        FormData formData_9 = new FormData();
        formData_9.right = new FormAttachment(100, -50);
        formData_9.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_9.left = new FormAttachment((Control)secondaryTsUrlLabel, 5, 131072);
        this.bdName.setLayoutData((Object)formData_9);
        this.primaryTSURL = new Text(this.bdComposite, 2048);
        FormData formData_10 = new FormData();
        formData_10.right = new FormAttachment(100, -50);
        formData_10.top = new FormAttachment((Control)primaryTsUrlLabel, 0, 128);
        formData_10.left = new FormAttachment((Control)this.bdName, 0, 16384);
        this.primaryTSURL.setLayoutData((Object)formData_10);
        this.secondaryTSURL = new Text(this.bdComposite, 2048);
        FormData formData_11 = new FormData();
        formData_11.right = new FormAttachment((Control)this.primaryTSURL, 0, 131072);
        formData_11.top = new FormAttachment((Control)secondaryTsUrlLabel, 0, 128);
        formData_11.left = new FormAttachment((Control)secondaryTsUrlLabel, 5, 131072);
        this.secondaryTSURL.setLayoutData((Object)formData_11);
        this.primaryTSURLT = new Text(this.bdComposite, 2048);
        FormData formData_10T = new FormData();
        formData_10T.right = new FormAttachment(100, -50);
        formData_10T.top = new FormAttachment((Control)primaryTsUrlLabelT, 0, 128);
        formData_10T.left = new FormAttachment((Control)this.bdName, 0, 16384);
        this.primaryTSURLT.setLayoutData((Object)formData_10T);
        this.secondaryTSURLT = new Text(this.bdComposite, 2048);
        FormData formData_11T = new FormData();
        formData_11T.right = new FormAttachment((Control)this.primaryTSURLT, 0, 131072);
        formData_11T.top = new FormAttachment((Control)secondaryTsUrlLabelT, 0, 128);
        formData_11T.left = new FormAttachment((Control)secondaryTsUrlLabelT, 5, 131072);
        this.secondaryTSURLT.setLayoutData((Object)formData_11T);
        this.primaryTSURL1 = new Text(this.bdComposite, 2048);
        FormData formData_101 = new FormData();
        formData_101.right = new FormAttachment(100, -50);
        formData_101.top = new FormAttachment((Control)primaryTsUrlLabel1, 0, 128);
        formData_101.left = new FormAttachment((Control)this.bdName, 0, 16384);
        this.primaryTSURL1.setLayoutData((Object)formData_101);
        this.secondaryTSURL1 = new Text(this.bdComposite, 2048);
        FormData formData_111 = new FormData();
        formData_111.right = new FormAttachment((Control)this.primaryTSURL1, 0, 131072);
        formData_111.top = new FormAttachment((Control)secondaryTsUrlLabel1, 0, 128);
        formData_111.left = new FormAttachment((Control)secondaryTsUrlLabel1, 5, 131072);
        this.secondaryTSURL1.setLayoutData((Object)formData_111);
        this.primaryTSURLT1 = new Text(this.bdComposite, 2048);
        FormData formData_10T1 = new FormData();
        formData_10T1.right = new FormAttachment(100, -50);
        formData_10T1.top = new FormAttachment((Control)primaryTsUrlLabelT1, 0, 128);
        formData_10T1.left = new FormAttachment((Control)this.bdName, 0, 16384);
        this.primaryTSURLT1.setLayoutData((Object)formData_10T1);
        this.secondaryTSURLT1 = new Text(this.bdComposite, 2048);
        FormData formData_11T1 = new FormData();
        formData_11T1.right = new FormAttachment((Control)this.primaryTSURLT1, 0, 131072);
        formData_11T1.top = new FormAttachment((Control)secondaryTsUrlLabelT1, 0, 128);
        formData_11T1.left = new FormAttachment((Control)secondaryTsUrlLabelT1, 5, 131072);
        this.secondaryTSURLT1.setLayoutData((Object)formData_11T1);
        this.primaryTSURLD = new Text(this.bdComposite, 2048);
        FormData formData_10D = new FormData();
        formData_10D.right = new FormAttachment(100, -50);
        formData_10D.top = new FormAttachment((Control)primaryTsUrlLabelD, 0, 128);
        formData_10D.left = new FormAttachment((Control)this.bdName, 0, 16384);
        this.primaryTSURLD.setLayoutData((Object)formData_10D);
        this.secondaryTSURLD = new Text(this.bdComposite, 2048);
        FormData formData_11D = new FormData();
        formData_11D.right = new FormAttachment((Control)this.primaryTSURLD, 0, 131072);
        formData_11D.top = new FormAttachment((Control)secondaryTsUrlLabelD, 0, 128);
        formData_11D.left = new FormAttachment((Control)secondaryTsUrlLabelD, 5, 131072);
        this.secondaryTSURLD.setLayoutData((Object)formData_11D);
        this.failoverURL = new Text(this.bdComposite, 2048);
        FormData formData_14 = new FormData();
        formData_14.right = new FormAttachment((Control)this.secondaryTSURL, 0, 131072);
        formData_14.top = new FormAttachment((Control)failoverUrlLabel, 0, 128);
        formData_14.left = new FormAttachment(0, 118);
        this.failoverURL.setLayoutData((Object)formData_14);
        this.heartBeatInterval = new Text(this.bdComposite, 2048);
        FormData formData_15 = new FormData();
        formData_15.right = new FormAttachment((Control)this.bdName, 0, 131072);
        formData_15.top = new FormAttachment((Control)heartbeatIntervalLabel, 0, 128);
        formData_15.left = new FormAttachment((Control)heartbeatIntervalLabel, 5, 131072);
        this.heartBeatInterval.setLayoutData((Object)formData_15);
        this.runAtStartUpCheck = new Button(this.bdComposite, 32);
        this.runAtStartUpCheck.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_16 = new FormData();
        formData_16.top = new FormAttachment((Control)runAtStartupLabel, 0, 128);
        formData_16.left = new FormAttachment((Control)heartbeatIntervalLabel, 5, 131072);
        this.runAtStartUpCheck.setLayoutData((Object)formData_16);
        this.bufferSize = new Text(this.bdComposite, 2048);
        FormData formData_17 = new FormData();
        formData_17.right = new FormAttachment((Control)this.bdName, 0, 131072);
        formData_17.top = new FormAttachment((Control)bufferSizeLabel, 0, 128);
        formData_17.left = new FormAttachment((Control)this.runAtStartUpCheck, 0, 16384);
        this.bufferSize.setLayoutData((Object)formData_17);
        CLabel failoverTypeLabel = new CLabel(this.bdComposite, 0);
        failoverTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_12 = new FormData();
        formData_12.top = new FormAttachment(70, 0);
        formData_12.left = new FormAttachment((Control)secondaryTsUrlLabel, 0, 16384);
        failoverTypeLabel.setLayoutData((Object)formData_12);
        failoverTypeLabel.setText("Failover Type:");
        this.primaryButton = new Button(this.bdComposite, 16);
        this.primaryButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_13 = new FormData();
        formData_13.top = new FormAttachment((Control)failoverTypeLabel, 0, 128);
        formData_13.left = new FormAttachment((Control)secondaryTsUrlLabel, 0, 131072);
        this.primaryButton.setLayoutData((Object)formData_13);
        this.primaryButton.setText("Primary");
        this.secondaryButton = new Button(this.bdComposite, 16);
        this.secondaryButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_18 = new FormData();
        formData_18.top = new FormAttachment((Control)this.primaryButton, 0, 128);
        formData_18.left = new FormAttachment((Control)this.primaryButton, 10, 131072);
        this.secondaryButton.setLayoutData((Object)formData_18);
        this.secondaryButton.setText("Secondary");
        this.hostedSolution = new Button(this.bdComposite, 32);
        this.hostedSolution.setBackground(Display.getCurrent().getSystemColor(1));
        this.hostedSolution.setEnabled(false);
        FormData formData_4 = new FormData();
        formData_4.right = new FormAttachment((Control)this.primaryTSURL, 0, 131072);
        formData_4.top = new FormAttachment((Control)this.secondaryButton, 0, 128);
        this.hostedSolution.setLayoutData((Object)formData_4);
        CLabel hostedSolutionLabel = new CLabel(this.bdComposite, 0);
        hostedSolutionLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_19 = new FormData();
        formData_19.top = new FormAttachment((Control)failoverTypeLabel, 0, 128);
        formData_19.right = new FormAttachment((Control)this.hostedSolution, -5, 16384);
        hostedSolutionLabel.setLayoutData((Object)formData_19);
        hostedSolutionLabel.setText("Multi-tenant Solution:");
        this.refreshInterval = new Text(this.bdComposite, 2048);
        this.refreshInterval.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_20 = new FormData();
        formData_20.right = new FormAttachment((Control)this.failoverURL, 0, 131072);
        formData_20.top = new FormAttachment((Control)this.heartBeatInterval, 0, 128);
        this.refreshInterval.setLayoutData((Object)formData_20);
        CLabel refreshIntervalLabel = new CLabel(this.bdComposite, 0);
        refreshIntervalLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_21R = new FormData();
        formData_21R.right = new FormAttachment((Control)this.refreshInterval, -5, 16384);
        formData_21R.top = new FormAttachment((Control)this.heartBeatInterval, 0, 128);
        refreshIntervalLabel.setLayoutData((Object)formData_21R);
        refreshIntervalLabel.setText("Refresh Interval:");
        this.bdComposite.setSize(814, 624);
        scrolledComposite.setContent((Control)this.bdComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    public void setFocus() {
    }

    public void initializeScreen() {
        if (!ConfigContext.readIMConfigContext()) {
            if (Designer.getSelectedProject() != null) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Read Error", (String)"Unable to read IM configuration ");
            }
            return;
        }
        this.bdName.setText(ConfigContext.getImName());
        if (ConfigContext.isImPrimary()) {
            this.primaryButton.setSelection(true);
            this.secondaryButton.setSelection(false);
        } else {
            this.primaryButton.setSelection(false);
            this.secondaryButton.setSelection(true);
        }
        this.primaryTSURL.setText(ConfigContext.getPrimaryTransformationServerURL());
        this.secondaryTSURL.setText(ConfigContext.getSecondaryTransformationServerURL());
        this.primaryTSURLT.setText(ConfigContext.getPrimaryTransformationServerURLT());
        this.secondaryTSURLT.setText(ConfigContext.getSecondaryTransformationServerURLT());
        this.primaryTSURL1.setText(ConfigContext.getPrimaryTransformationServerURL1());
        this.secondaryTSURL1.setText(ConfigContext.getSecondaryTransformationServerURL1());
        this.primaryTSURLT1.setText(ConfigContext.getPrimaryTransformationServerURLT1());
        this.secondaryTSURLT1.setText(ConfigContext.getSecondaryTransformationServerURLT1());
        this.primaryTSURLD.setText(ConfigContext.getPrimaryTransformationServerURLD());
        this.secondaryTSURLD.setText(ConfigContext.getSecondaryTransformationServerURLD());
        this.failoverURL.setText(ConfigContext.getFailoverURL());
        this.heartBeatInterval.setText(Long.toString(ConfigContext.getHartbeatInterval()));
        this.refreshInterval.setText(Long.toString(ConfigContext.getRefreshInterval()));
        this.runAtStartUpCheck.setSelection(ConfigContext.isRunAtStartUp());
        this.bufferSize.setText(Integer.toString(ConfigContext.getImBufferSize()));
        this.hostedSolution.setSelection(ConfigContext.isHosted());
    }

    private void createActions() {
        this.saveImConfigurationAction = new Action("Save IM Configuration"){

            public void run() {
                ConfigBDView.this.saveConfig();
            }
        };
        this.saveImConfigurationAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.saveImConfigurationAction.setAccelerator(262227);
        this.action = new Action("Refresh"){

            public void run() {
                ConfigBDView.this.initializeScreen();
            }
        };
        this.action.setToolTipText("Refresh");
        this.action.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.saveImConfigurationAction);
        tbm.add((IAction)this.action);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.saveImConfigurationAction);
        manager.add((IAction)this.action);
    }

    private void saveConfig() {
        ConfigContext.setImName(this.bdName.getText());
        ConfigContext.setImPrimary(this.primaryButton.getSelection());
        ConfigContext.setPrimaryTransformationServerURL(this.primaryTSURL.getText());
        ConfigContext.setSecondaryTransformationServerURL(this.secondaryTSURL.getText());
        ConfigContext.setPrimaryTransformationServerURLT(this.primaryTSURLT.getText());
        ConfigContext.setSecondaryTransformationServerURLT(this.secondaryTSURLT.getText());
        ConfigContext.setPrimaryTransformationServerURL1(this.primaryTSURL1.getText());
        ConfigContext.setSecondaryTransformationServerURL1(this.secondaryTSURL1.getText());
        ConfigContext.setPrimaryTransformationServerURLT1(this.primaryTSURLT1.getText());
        ConfigContext.setSecondaryTransformationServerURLT1(this.secondaryTSURLT1.getText());
        ConfigContext.setPrimaryTransformationServerURLD(this.primaryTSURLD.getText());
        ConfigContext.setSecondaryTransformationServerURLD(this.secondaryTSURLD.getText());
        ConfigContext.setFailoverURL(this.failoverURL.getText());
        ConfigContext.setHartbeatInterval(Long.parseLong(this.heartBeatInterval.getText()));
        ConfigContext.setRefreshInterval(Long.parseLong(this.refreshInterval.getText()));
        ConfigContext.setRunAtStartUp(this.runAtStartUpCheck.getSelection());
        ConfigContext.setImBufferSize(Integer.parseInt(this.bufferSize.getText()));
        if (!ConfigContext.writeIMConfigContext()) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Write Error", (String)"Unable to store IM configuration ");
        }
    }
}

