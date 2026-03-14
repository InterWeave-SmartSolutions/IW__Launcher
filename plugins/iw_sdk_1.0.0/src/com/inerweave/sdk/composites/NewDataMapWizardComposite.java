/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.vews.AccessParameterView;
import com.inerweave.sdk.vews.ConnectionView;
import com.iwtransactions.accessType;
import com.iwtransactions.parameterlist;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class NewDataMapWizardComposite
extends Composite {
    private Button xmlDeclarationButton;
    private Menu ouptutMenu;
    private Menu inputMenu;
    private Menu genMenu;
    private Button vieweditButton;
    private Button useAttributesButton;
    private Button manualCommitButton;
    private Button ignoreRepeatButton;
    private CCombo accessTypeCombo;
    private Button useCustomConversionButton;
    private StyledText commandStyledText;
    private List outputParamList;
    private List inputParamList;
    private List genParamList;
    private CCombo postTransformerCombo;
    private CCombo preTransformerCombo;
    private CCombo connectionsCombo;
    private Text mapName;
    private accessType currentAccess;

    public void setCurrentAccess(accessType currentAccess) {
        this.currentAccess = currentAccess;
    }

    public NewDataMapWizardComposite(Composite parent, int style) {
        super(parent, style);
        this.setLayout((Layout)new FormLayout());
        this.setBackground(Display.getCurrent().getSystemColor(1));
        CLabel nameLabel = new CLabel((Composite)this, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData = new FormData();
        formData.top = new FormAttachment(10, 0);
        formData.left = new FormAttachment(0, 15);
        nameLabel.setLayoutData((Object)formData);
        nameLabel.setText("Name:");
        CLabel connectionLabel = new CLabel((Composite)this, 0);
        connectionLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(20, 0);
        formData_2.left = new FormAttachment((Control)nameLabel, 0, 16384);
        connectionLabel.setLayoutData((Object)formData_2);
        connectionLabel.setText("Connection:");
        this.mapName = new Text((Composite)this, 2048);
        FormData formData_1 = new FormData();
        formData_1.right = new FormAttachment(60, 0);
        formData_1.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_1.left = new FormAttachment((Control)connectionLabel, 5, 131072);
        this.mapName.setLayoutData((Object)formData_1);
        this.connectionsCombo = new CCombo((Composite)this, 2056);
        this.connectionsCombo.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                if (NewDataMapWizardComposite.this.connectionsCombo.getSelectionIndex() == 0) {
                    NewDataMapWizardComposite.this.vieweditButton.setText("Add");
                } else {
                    NewDataMapWizardComposite.this.vieweditButton.setText("Edit");
                }
            }
        });
        this.connectionsCombo.select(0);
        this.connectionsCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.right = new FormAttachment(60, 0);
        formData_3.top = new FormAttachment((Control)connectionLabel, 0, 128);
        formData_3.left = new FormAttachment((Control)connectionLabel, 5, 131072);
        this.connectionsCombo.setLayoutData((Object)formData_3);
        Group accessGroup = new Group((Composite)this, 0);
        accessGroup.setBackground(Display.getCurrent().getSystemColor(1));
        accessGroup.setText("Access Data");
        FormData formData_5 = new FormData();
        formData_5.bottom = new FormAttachment(100, -15);
        formData_5.right = new FormAttachment(100, -15);
        formData_5.top = new FormAttachment(30, 0);
        formData_5.left = new FormAttachment((Control)connectionLabel, 0, 16384);
        accessGroup.setLayoutData((Object)formData_5);
        accessGroup.setLayout((Layout)new FormLayout());
        CLabel commandLabel = new CLabel((Composite)accessGroup, 0);
        commandLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment(20, 0);
        formData_6.left = new FormAttachment(0, 10);
        commandLabel.setLayoutData((Object)formData_6);
        commandLabel.setText("Command:");
        CLabel preconvertorLabel = new CLabel((Composite)accessGroup, 0);
        preconvertorLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_7 = new FormData();
        formData_7.top = new FormAttachment(50, 0);
        formData_7.left = new FormAttachment((Control)commandLabel, 1, 16384);
        preconvertorLabel.setLayoutData((Object)formData_7);
        preconvertorLabel.setText("Pre-Transformer:");
        CLabel postconvertorLabel = new CLabel((Composite)accessGroup, 0);
        postconvertorLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_8 = new FormData();
        formData_8.top = new FormAttachment(60, 0);
        formData_8.left = new FormAttachment((Control)preconvertorLabel, 0, 16384);
        postconvertorLabel.setLayoutData((Object)formData_8);
        postconvertorLabel.setText("Post-Transformer:");
        this.useCustomConversionButton = new Button((Composite)accessGroup, 32);
        this.useCustomConversionButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9 = new FormData();
        formData_9.right = new FormAttachment(100, -5);
        formData_9.top = new FormAttachment((Control)preconvertorLabel, 0, 128);
        formData_9.left = new FormAttachment(100, -119);
        this.useCustomConversionButton.setLayoutData((Object)formData_9);
        this.useCustomConversionButton.setText("Custom Conversion");
        CLabel generalParametersLabel = new CLabel((Composite)accessGroup, 0);
        generalParametersLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_10 = new FormData();
        formData_10.top = new FormAttachment(70, 0);
        formData_10.left = new FormAttachment((Control)postconvertorLabel, 0, 16384);
        generalParametersLabel.setLayoutData((Object)formData_10);
        generalParametersLabel.setText("General Parameters:");
        CLabel inputParametersLabel = new CLabel((Composite)accessGroup, 0);
        inputParametersLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_11 = new FormData();
        formData_11.left = new FormAttachment(33, 5);
        formData_11.top = new FormAttachment((Control)generalParametersLabel, 0, 128);
        inputParametersLabel.setLayoutData((Object)formData_11);
        inputParametersLabel.setText("Input Parameters:");
        CLabel outputParametersLabel = new CLabel((Composite)accessGroup, 0);
        outputParametersLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_12 = new FormData();
        formData_12.left = new FormAttachment(67, 5);
        formData_12.top = new FormAttachment((Control)inputParametersLabel, 0, 128);
        outputParametersLabel.setLayoutData((Object)formData_12);
        outputParametersLabel.setText("Output Parameters:");
        Button showPreButton = new Button((Composite)accessGroup, 0);
        showPreButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                ConfigContext.openXsltEditor(NewDataMapWizardComposite.this.preTransformerCombo);
            }
        });
        showPreButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                ConfigContext.openXsltEditor(NewDataMapWizardComposite.this.preTransformerCombo);
            }
        });
        showPreButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/changelog_obj.gif"));
        showPreButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_25 = new FormData();
        formData_25.right = new FormAttachment((Control)this.useCustomConversionButton, -15, 16384);
        formData_25.bottom = new FormAttachment((Control)preconvertorLabel, 0, 1024);
        formData_25.top = new FormAttachment((Control)preconvertorLabel, 0, 128);
        showPreButton.setLayoutData((Object)formData_25);
        Button showPostButton = new Button((Composite)accessGroup, 0);
        showPostButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                ConfigContext.openXsltEditor(NewDataMapWizardComposite.this.postTransformerCombo);
            }
        });
        showPostButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                ConfigContext.openXsltEditor(NewDataMapWizardComposite.this.postTransformerCombo);
            }
        });
        showPostButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/changelog_obj.gif"));
        FormData formData_25_1 = new FormData();
        formData_25_1.left = new FormAttachment((Control)showPreButton, 0, 16384);
        formData_25_1.bottom = new FormAttachment((Control)postconvertorLabel, 0, 1024);
        formData_25_1.top = new FormAttachment((Control)postconvertorLabel, 0, 128);
        showPostButton.setLayoutData((Object)formData_25_1);
        showPostButton.setBackground(Display.getCurrent().getSystemColor(1));
        this.preTransformerCombo = new CCombo((Composite)accessGroup, 2056);
        this.preTransformerCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_13 = new FormData();
        formData_13.right = new FormAttachment((Control)showPreButton, -5, 16384);
        formData_13.left = new FormAttachment(25, 5);
        formData_13.top = new FormAttachment((Control)preconvertorLabel, 0, 128);
        this.preTransformerCombo.setLayoutData((Object)formData_13);
        this.postTransformerCombo = new CCombo((Composite)accessGroup, 2056);
        this.postTransformerCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_14 = new FormData();
        formData_14.right = new FormAttachment((Control)this.preTransformerCombo, 0, 131072);
        formData_14.top = new FormAttachment((Control)postconvertorLabel, 0, 128);
        formData_14.left = new FormAttachment((Control)this.preTransformerCombo, 0, 16384);
        this.postTransformerCombo.setLayoutData((Object)formData_14);
        this.genParamList = new List((Composite)accessGroup, 2560);
        this.genParamList.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "V", NewDataMapWizardComposite.this.genParamList);
            }
        });
        this.genParamList.select(0);
        this.genParamList.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_15 = new FormData();
        formData_15.right = new FormAttachment(33, -5);
        formData_15.left = new FormAttachment(0, 5);
        formData_15.bottom = new FormAttachment(100, -5);
        formData_15.top = new FormAttachment((Control)generalParametersLabel, 0, 1024);
        this.genParamList.setLayoutData((Object)formData_15);
        this.inputParamList = new List((Composite)accessGroup, 2560);
        this.inputParamList.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "W", NewDataMapWizardComposite.this.inputParamList);
            }
        });
        this.inputParamList.select(0);
        this.inputParamList.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_17 = new FormData();
        formData_17.right = new FormAttachment(67, -5);
        formData_17.left = new FormAttachment(33, 5);
        formData_17.bottom = new FormAttachment((Control)this.genParamList, 0, 1024);
        formData_17.top = new FormAttachment((Control)this.genParamList, 0, 128);
        this.inputParamList.setLayoutData((Object)formData_17);
        this.inputMenu = new Menu((Control)this.inputParamList);
        this.inputParamList.setMenu(this.inputMenu);
        MenuItem appendMenuItemInp = new MenuItem(this.inputMenu, 0);
        appendMenuItemInp.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(-1, "W", NewDataMapWizardComposite.this.inputParamList);
            }
        });
        appendMenuItemInp.setText("New");
        MenuItem insertMenuItemInp = new MenuItem(this.inputMenu, 0);
        insertMenuItemInp.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int dms = NewDataMapWizardComposite.this.inputParamList.getSelectionIndex();
                if (dms < 0) {
                    MessageDialog.openError((Shell)NewDataMapWizardComposite.this.getShell(), (String)"View Error", (String)"No Parameter selected to insert before it");
                } else {
                    NewDataMapWizardComposite.this.showParameterView(dms + 1, "W", NewDataMapWizardComposite.this.inputParamList);
                }
            }
        });
        insertMenuItemInp.setText("Insert");
        MenuItem editMenuItemInp = new MenuItem(this.inputMenu, 0);
        editMenuItemInp.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "W", NewDataMapWizardComposite.this.inputParamList);
            }
        });
        editMenuItemInp.setText("Edit");
        new MenuItem(this.inputMenu, 2);
        MenuItem cutMenuItemInp = new MenuItem(this.inputMenu, 0);
        cutMenuItemInp.setEnabled(false);
        cutMenuItemInp.setText("Cut");
        MenuItem copyMenuItemInp = new MenuItem(this.inputMenu, 0);
        copyMenuItemInp.setEnabled(false);
        copyMenuItemInp.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        copyMenuItemInp.setText("Copy");
        MenuItem pasteMenuItemInp = new MenuItem(this.inputMenu, 0);
        pasteMenuItemInp.setEnabled(false);
        pasteMenuItemInp.setText("Paste");
        new MenuItem(this.inputMenu, 2);
        MenuItem deleteMenuItemInp = new MenuItem(this.inputMenu, 0);
        deleteMenuItemInp.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int nd = NewDataMapWizardComposite.this.inputParamList.getSelectionIndex();
                if (nd < 0) {
                    MessageDialog.openWarning((Shell)NewDataMapWizardComposite.this.getShell(), (String)"Access Parameter", (String)"No Parameter selected");
                }
                NewDataMapWizardComposite.this.deleteParameter(nd, 'W');
            }
        });
        deleteMenuItemInp.setText("Delete");
        this.outputParamList = new List((Composite)accessGroup, 2560);
        this.outputParamList.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "O", NewDataMapWizardComposite.this.outputParamList);
            }
        });
        this.outputParamList.select(0);
        this.outputParamList.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_18 = new FormData();
        formData_18.left = new FormAttachment(67, 5);
        formData_18.bottom = new FormAttachment((Control)this.inputParamList, 0, 1024);
        formData_18.right = new FormAttachment(100, -5);
        formData_18.top = new FormAttachment((Control)this.genParamList, 0, 128);
        this.outputParamList.setLayoutData((Object)formData_18);
        this.ouptutMenu = new Menu((Control)this.outputParamList);
        this.outputParamList.setMenu(this.ouptutMenu);
        MenuItem appendMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        appendMenuItemOut.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(-1, "O", NewDataMapWizardComposite.this.outputParamList);
            }
        });
        appendMenuItemOut.setText("New");
        MenuItem insertMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        insertMenuItemOut.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int dms = NewDataMapWizardComposite.this.outputParamList.getSelectionIndex();
                if (dms < 0) {
                    MessageDialog.openError((Shell)NewDataMapWizardComposite.this.getShell(), (String)"View Error", (String)"No Parameter selected to insert before it");
                } else {
                    NewDataMapWizardComposite.this.showParameterView(dms + 1, "O", NewDataMapWizardComposite.this.outputParamList);
                }
            }
        });
        insertMenuItemOut.setText("Insert");
        MenuItem editMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        editMenuItemOut.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "O", NewDataMapWizardComposite.this.outputParamList);
            }
        });
        editMenuItemOut.setText("Edit");
        new MenuItem(this.ouptutMenu, 2);
        MenuItem cutMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        cutMenuItemOut.setEnabled(false);
        cutMenuItemOut.setText("Cut");
        MenuItem copyMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        copyMenuItemOut.setEnabled(false);
        copyMenuItemOut.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        copyMenuItemOut.setText("Copy");
        MenuItem pasteMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        pasteMenuItemOut.setEnabled(false);
        pasteMenuItemOut.setText("Paste");
        new MenuItem(this.ouptutMenu, 2);
        MenuItem deleteMenuItemOut = new MenuItem(this.ouptutMenu, 0);
        deleteMenuItemOut.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int nd = NewDataMapWizardComposite.this.outputParamList.getSelectionIndex();
                if (nd < 0) {
                    MessageDialog.openWarning((Shell)NewDataMapWizardComposite.this.getShell(), (String)"Access Parameter", (String)"No Parameter selected");
                }
                NewDataMapWizardComposite.this.deleteParameter(nd, 'O');
            }
        });
        deleteMenuItemOut.setText("Delete");
        this.commandStyledText = new StyledText((Composite)accessGroup, 2624);
        this.commandStyledText.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_19 = new FormData();
        formData_19.bottom = new FormAttachment((Control)this.preTransformerCombo, -5, 128);
        formData_19.right = new FormAttachment(100, -5);
        formData_19.top = new FormAttachment((Control)commandLabel, 0, 128);
        formData_19.left = new FormAttachment((Control)this.preTransformerCombo, 0, 16384);
        this.commandStyledText.setLayoutData((Object)formData_19);
        CLabel accessTypeLabel = new CLabel((Composite)accessGroup, 0);
        accessTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_20 = new FormData();
        formData_20.top = new FormAttachment(7, 0);
        formData_20.left = new FormAttachment((Control)commandLabel, 0, 16384);
        accessTypeLabel.setLayoutData((Object)formData_20);
        accessTypeLabel.setText("Access Type:");
        this.accessTypeCombo = new CCombo((Composite)accessGroup, 2056);
        this.genMenu = new Menu((Control)this.genParamList);
        this.genParamList.setMenu(this.genMenu);
        MenuItem appendMenuItemGen = new MenuItem(this.genMenu, 0);
        appendMenuItemGen.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(-1, "V", NewDataMapWizardComposite.this.genParamList);
            }
        });
        appendMenuItemGen.setText("New");
        MenuItem insertMenuItemGen = new MenuItem(this.genMenu, 0);
        insertMenuItemGen.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int dms = NewDataMapWizardComposite.this.genParamList.getSelectionIndex();
                if (dms < 0) {
                    MessageDialog.openError((Shell)NewDataMapWizardComposite.this.getShell(), (String)"View Error", (String)"No Parameter selected to insert before it");
                } else {
                    NewDataMapWizardComposite.this.showParameterView(dms + 1, "V", NewDataMapWizardComposite.this.genParamList);
                }
            }
        });
        insertMenuItemGen.setText("Insert");
        MenuItem editMenuItemGen = new MenuItem(this.genMenu, 0);
        editMenuItemGen.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.showParameterView(0, "V", NewDataMapWizardComposite.this.genParamList);
            }
        });
        editMenuItemGen.setText("Edit");
        new MenuItem(this.genMenu, 2);
        MenuItem cutMenuItemGen = new MenuItem(this.genMenu, 0);
        cutMenuItemGen.setEnabled(false);
        cutMenuItemGen.setText("Cut");
        MenuItem copyMenuItemGen = new MenuItem(this.genMenu, 0);
        copyMenuItemGen.setEnabled(false);
        copyMenuItemGen.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        copyMenuItemGen.setText("Copy");
        MenuItem pasteMenuItemGen = new MenuItem(this.genMenu, 0);
        pasteMenuItemGen.setEnabled(false);
        pasteMenuItemGen.setText("Paste");
        new MenuItem(this.genMenu, 2);
        MenuItem deleteMenuItemGen = new MenuItem(this.genMenu, 0);
        deleteMenuItemGen.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int nd = NewDataMapWizardComposite.this.genParamList.getSelectionIndex();
                if (nd < 0) {
                    MessageDialog.openWarning((Shell)NewDataMapWizardComposite.this.getShell(), (String)"Access Parameter", (String)"No Parameter selected");
                }
                NewDataMapWizardComposite.this.deleteParameter(nd, 'V');
            }
        });
        deleteMenuItemGen.setText("Delete");
        this.accessTypeCombo.setItems(new String[]{"", "Procedure", "Dynamic", "Data"});
        this.accessTypeCombo.select(0);
        this.accessTypeCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_21 = new FormData();
        formData_21.top = new FormAttachment((Control)accessTypeLabel, 0, 128);
        formData_21.left = new FormAttachment((Control)this.commandStyledText, 0, 16384);
        this.accessTypeCombo.setLayoutData((Object)formData_21);
        this.ignoreRepeatButton = new Button((Composite)accessGroup, 32);
        this.ignoreRepeatButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_22 = new FormData();
        formData_22.top = new FormAttachment((Control)this.preTransformerCombo, 0, 1024);
        formData_22.left = new FormAttachment((Control)this.useCustomConversionButton, 0, 16384);
        this.ignoreRepeatButton.setLayoutData((Object)formData_22);
        this.ignoreRepeatButton.setText("Ignore Repeat");
        this.manualCommitButton = new Button((Composite)accessGroup, 32);
        this.manualCommitButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_23 = new FormData();
        formData_23.bottom = new FormAttachment((Control)this.accessTypeCombo, 0, 1024);
        formData_23.left = new FormAttachment((Control)this.accessTypeCombo, 15, -1);
        formData_23.top = new FormAttachment((Control)accessTypeLabel, 0, 128);
        this.manualCommitButton.setLayoutData((Object)formData_23);
        this.manualCommitButton.setText("Rollback on Error");
        this.useAttributesButton = new Button((Composite)accessGroup, 32);
        this.useAttributesButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_24 = new FormData();
        formData_24.bottom = new FormAttachment((Control)this.manualCommitButton, 21, 128);
        formData_24.top = new FormAttachment((Control)this.manualCommitButton, 0, 128);
        formData_24.left = new FormAttachment((Control)this.useCustomConversionButton, 0, 16384);
        this.useAttributesButton.setLayoutData((Object)formData_24);
        this.useAttributesButton.setText("Use Attributes");
        Menu commandMenu = new Menu((Control)this.commandStyledText);
        this.commandStyledText.setMenu(commandMenu);
        MenuItem cutMenuItem = new MenuItem(commandMenu, 0);
        cutMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.commandStyledText.cut();
            }
        });
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(commandMenu, 0);
        copyMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.commandStyledText.copy();
            }
        });
        copyMenuItem.setText("Copy");
        MenuItem pasteMenuItem = new MenuItem(commandMenu, 0);
        pasteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewDataMapWizardComposite.this.commandStyledText.paste();
            }
        });
        pasteMenuItem.setText("Paste");
        this.xmlDeclarationButton = new Button((Composite)accessGroup, 32);
        this.xmlDeclarationButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_16 = new FormData();
        formData_16.bottom = new FormAttachment((Control)this.manualCommitButton, 0, 1024);
        formData_16.top = new FormAttachment((Control)this.manualCommitButton, 0, 128);
        formData_16.left = new FormAttachment((Control)this.manualCommitButton, 15, 131072);
        this.xmlDeclarationButton.setLayoutData((Object)formData_16);
        this.xmlDeclarationButton.setText("XML Declaration");
        this.vieweditButton = new Button((Composite)this, 0);
        this.vieweditButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                NewDataMapWizardComposite.this.showConnectionView();
            }
        });
        this.vieweditButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                NewDataMapWizardComposite.this.showConnectionView();
            }
        });
        FormData formData_4 = new FormData();
        formData_4.bottom = new FormAttachment((Control)connectionLabel, 0, 1024);
        formData_4.top = new FormAttachment((Control)this.connectionsCombo, 0, 128);
        formData_4.left = new FormAttachment((Control)this.connectionsCombo, 15, 131072);
        this.vieweditButton.setLayoutData((Object)formData_4);
        this.vieweditButton.setText("Edit");
    }

    public final StyledText getCommandStyledText() {
        return this.commandStyledText;
    }

    public final CCombo getConnectionsCombo() {
        return this.connectionsCombo;
    }

    public final List getGenParamList() {
        return this.genParamList;
    }

    public final List getInputParamList() {
        return this.inputParamList;
    }

    public final Text getMapName() {
        return this.mapName;
    }

    public final List getOutputParamList() {
        return this.outputParamList;
    }

    public final CCombo getPostTransformerCombo() {
        return this.postTransformerCombo;
    }

    public final CCombo getPreTransformerCombo() {
        return this.preTransformerCombo;
    }

    public final Button getUseCustomConversionButton() {
        return this.useCustomConversionButton;
    }

    public final CCombo getAccessTypeCombo() {
        return this.accessTypeCombo;
    }

    private void showParameterView(int mode, String prefix, List parameterList) {
        try {
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            ap.showView("iw_sdk.AccessParameterView");
            int si = parameterList.getSelectionIndex();
            if (si >= 0) {
                ConfigContext.setCurrentParameterName(String.valueOf(prefix) + si);
                ((AccessParameterView)ap.findView("iw_sdk.AccessParameterView")).initializeScreen(mode);
            } else if (mode < 0) {
                ConfigContext.setCurrentParameterName(prefix);
                ((AccessParameterView)ap.findView("iw_sdk.AccessParameterView")).initializeScreen(mode);
            } else {
                MessageDialog.openWarning((Shell)this.getShell(), (String)"Access Parameter", (String)"No Parameter selected");
            }
        }
        catch (PartInitException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Access Parameter", (String)("Unable to show Access Parameter view: " + e.toString()));
            return;
        }
    }

    public void deleteParameter(int index, char prefix) {
        if (this.currentAccess == null) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Access Parameter", (String)"No Access Element to delete Access Parameter");
        }
        try {
            switch (prefix) {
                case 'V': {
                    this.currentAccess.getvalues().removeparameterAt(index);
                    if (this.currentAccess.getvaluesCount() > 0) {
                        parameterlist ipt = this.currentAccess.getvalues();
                        int pc = ipt.getparameterCount();
                        if (pc > 0) {
                            this.genParamList.setItems(ConfigContext.getNamelessParameterList(pc));
                            this.genParamList.select(0);
                            break;
                        }
                        this.genParamList.removeAll();
                        break;
                    }
                    this.genParamList.removeAll();
                    break;
                }
                case 'W': {
                    this.currentAccess.getwhere().removeparameterAt(index);
                    if (this.currentAccess.getwhereCount() > 0) {
                        parameterlist ipt = this.currentAccess.getwhere();
                        int pc = ipt.getparameterCount();
                        if (pc > 0) {
                            this.inputParamList.setItems(ConfigContext.getNamelessParameterList(pc));
                            this.inputParamList.select(0);
                            break;
                        }
                        this.inputParamList.removeAll();
                        break;
                    }
                    this.inputParamList.removeAll();
                    break;
                }
                case 'O': {
                    this.currentAccess.getoutputs().removeparameterAt(index);
                    if (this.currentAccess.getoutputsCount() > 0) {
                        parameterlist ipt = this.currentAccess.getoutputs();
                        int pc = ipt.getparameterCount();
                        if (pc > 0) {
                            this.outputParamList.setItems(ConfigContext.getNamelessParameterList(pc));
                            this.outputParamList.select(0);
                            break;
                        }
                        this.outputParamList.removeAll();
                        break;
                    }
                    this.outputParamList.removeAll();
                }
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Access Parameter", (String)("Unable to delete Access Parameter: " + e.toString()));
        }
    }

    private void showConnectionView() {
        try {
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            ap.showView("iw_sdk.ConnectionView");
            int si = this.connectionsCombo.getSelectionIndex();
            if (si >= 0) {
                ConfigContext.setCurrentConnectionName(this.connectionsCombo.getItem(si));
                ((ConnectionView)ap.findView("iw_sdk.ConnectionView")).initializeScreen(si == 0);
            }
        }
        catch (PartInitException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Plugin Error", (String)("Unable to show Access Parameter view " + e.toString()));
            return;
        }
    }

    public Button getIgnoreRepeatButton() {
        return this.ignoreRepeatButton;
    }

    public Button getManualCommitButton() {
        return this.manualCommitButton;
    }

    public Button getUseAttributesButton() {
        return this.useAttributesButton;
    }

    public Button getVieweditButton() {
        return this.vieweditButton;
    }

    public Menu getGenMenu() {
        return this.genMenu;
    }

    public Menu getInputMenu() {
        return this.inputMenu;
    }

    public Menu getOuptutMenu() {
        return this.ouptutMenu;
    }

    public Button getXmlDeclarationButton() {
        return this.xmlDeclarationButton;
    }
}

