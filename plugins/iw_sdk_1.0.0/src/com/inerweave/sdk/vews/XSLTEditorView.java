/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.vews.NavigationView;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

public class XSLTEditorView
extends ViewPart {
    private Action systemEditor;
    private Text transformerName;
    private Action validateAcdtion;
    private Action action;
    private CCombo xsltTypeCombo;
    private Action saveXsltAction;
    private String directPattern = "\n<xsl:value-of select=\"col[@name='#']\"/>\n";
    private String toNumericPattern = "\n<xsl:choose>\n<xsl:when test=\"col[@name='#']=''\">0</xsl:when>\n<xsl:otherwise>\n<xsl:value-of select=\"round(col[@name='#'])\"/>\n</xsl:otherwise>\n</xsl:choose>\n";
    private StyledText xsltStyledText;
    private Button editButton;
    private Button viewButton;
    public static final String ID = "iw_sdk.XSLTEditorView";
    private Composite container = null;
    private ScrolledComposite xsltEditorScrolledComposite = null;
    private Composite xsltEditorComposite = null;
    private Tree sourceTree = null;
    private CLabel patternCLabel = null;
    private CCombo patternCCombo = null;
    private List destList = null;
    private Button showXSLTCheckBox = null;
    private CLabel showXSLTCLabel = null;
    private Hashtable<String, String[]> destinationFieldsMapping = new Hashtable();
    boolean srcSelectionForced = false;
    boolean dstSelectionForced = false;

    public void createPartControl(Composite parent) {
        this.container = new Composite(parent, 0);
        this.container.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                XSLTEditorView.this.xsltEditorComposite.setSize(((XSLTEditorView)XSLTEditorView.this).container.getClientArea().width, ((XSLTEditorView)XSLTEditorView.this).container.getClientArea().height);
            }
        });
        this.container.setLayout((Layout)new FormLayout());
        this.createXsltEditorScrolledComposite();
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    public void setFocus() {
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.saveXsltAction);
        tbm.add((IAction)this.action);
        tbm.add((IAction)this.validateAcdtion);
        tbm.add((IAction)this.systemEditor);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.saveXsltAction);
        manager.add((IAction)this.action);
        manager.add((IAction)this.validateAcdtion);
        manager.add((IAction)this.systemEditor);
    }

    private void createXsltEditorScrolledComposite() {
        this.xsltEditorScrolledComposite = new ScrolledComposite(this.container, 0);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, -5);
        formData.right = new FormAttachment(100, -5);
        formData.top = new FormAttachment(0, 0);
        formData.left = new FormAttachment(0, 0);
        this.xsltEditorScrolledComposite.setLayoutData((Object)formData);
        this.xsltEditorScrolledComposite.setLayout((Layout)new FormLayout());
        this.createXsltEditorComposite();
        this.xsltEditorScrolledComposite.setContent((Control)this.xsltEditorComposite);
    }

    private void createXsltEditorComposite() {
        this.xsltEditorComposite = new Composite((Composite)this.xsltEditorScrolledComposite, 0);
        this.xsltEditorComposite.setBounds(0, 0, 800, 600);
        this.xsltEditorComposite.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataXEC = new FormData();
        formDataXEC.bottom = new FormAttachment(100, 0);
        formDataXEC.top = new FormAttachment(0, 0);
        formDataXEC.right = new FormAttachment(100, 0);
        formDataXEC.left = new FormAttachment(0, 0);
        this.xsltEditorComposite.setLayoutData((Object)formDataXEC);
        this.xsltEditorComposite.setLayout((Layout)new FormLayout());
        this.createSourceTree();
        FormData formDataPCL = new FormData();
        formDataPCL.top = new FormAttachment(0, 70);
        formDataPCL.bottom = new FormAttachment(0, 90);
        this.patternCLabel = new CLabel(this.xsltEditorComposite, 0);
        this.patternCLabel.setLayoutData((Object)formDataPCL);
        this.patternCLabel.setText("Mapping Pattern:");
        this.patternCLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataPCC = new FormData();
        formDataPCC.bottom = new FormAttachment((Control)this.patternCLabel, 0, 1024);
        formDataPCC.top = new FormAttachment((Control)this.patternCLabel, 0, 128);
        formDataPCC.left = new FormAttachment((Control)this.patternCLabel, 5, 131072);
        this.patternCCombo = new CCombo(this.xsltEditorComposite, 2056);
        this.patternCCombo.setLayoutData((Object)formDataPCC);
        FormData formDataDL = new FormData();
        formDataDL.bottom = new FormAttachment(100, 0);
        formDataDL.top = new FormAttachment(0, 30);
        formDataDL.right = new FormAttachment(100, 0);
        formDataDL.left = new FormAttachment(75, 0);
        this.destList = new List(this.xsltEditorComposite, 2050);
        this.destList.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.desinationFieldSelected();
            }
        });
        this.destList.setLayoutData((Object)formDataDL);
        Menu menu = new Menu((Control)this.destList);
        this.destList.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.newDestField(true);
            }
        });
        newMenuItem.setText("New");
        MenuItem insertMenuItem = new MenuItem(menu, 0);
        insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.newDestField(false);
            }
        });
        insertMenuItem.setText("Insert");
        MenuItem editMenuItem = new MenuItem(menu, 0);
        editMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        editMenuItem.setText("Edit");
        new MenuItem(menu, 2);
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.setEnabled(false);
        cutMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(menu, 0);
        copyMenuItem.setEnabled(false);
        copyMenuItem.setText("Copy");
        MenuItem pasteMenuItem = new MenuItem(menu, 0);
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.setText("Paste");
        new MenuItem(menu, 2);
        MenuItem deleteMenuItem = new MenuItem(menu, 0);
        deleteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        deleteMenuItem.setText("Delete");
        this.patternCCombo.setItems(new String[]{"DIRECT", "TO_NUMERIC", "TO_DATE", "TO_TIME", "TO_TIMESTAMP", "SUBSTRING", "CUSTOM"});
        this.patternCCombo.setBackground(Display.getCurrent().getSystemColor(1));
        this.patternCCombo.select(0);
        FormData formDataSXCL = new FormData();
        formDataSXCL.right = new FormAttachment((Control)this.patternCLabel, 100, 16384);
        formDataSXCL.left = new FormAttachment((Control)this.patternCLabel, 0, 16384);
        this.showXSLTCLabel = new CLabel(this.xsltEditorComposite, 0);
        this.showXSLTCLabel.setText("Show XSLT:");
        this.showXSLTCLabel.setLayoutData((Object)formDataSXCL);
        this.showXSLTCLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataSXCB = new FormData();
        formDataSXCB.bottom = new FormAttachment((Control)this.showXSLTCLabel, 0, 1024);
        formDataSXCB.top = new FormAttachment((Control)this.showXSLTCLabel, 0, 128);
        formDataSXCB.left = new FormAttachment((Control)this.showXSLTCLabel, 5, 131072);
        this.showXSLTCheckBox = new Button(this.xsltEditorComposite, 32);
        this.showXSLTCheckBox.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.showXSLTText(XSLTEditorView.this.showXSLTCheckBox.getSelection());
            }
        });
        this.showXSLTCheckBox.setSelection(true);
        this.showXSLTCheckBox.setLayoutData((Object)formDataSXCB);
        this.showXSLTCheckBox.setBackground(Display.getCurrent().getSystemColor(1));
        this.xsltStyledText = new StyledText(this.xsltEditorComposite, 2880);
        formDataPCL.right = new FormAttachment((Control)this.xsltStyledText, 100, 16384);
        formDataPCL.left = new FormAttachment((Control)this.xsltStyledText, 0, 16384);
        formDataSXCL.top = new FormAttachment((Control)this.xsltStyledText, -25, 128);
        formDataSXCL.bottom = new FormAttachment((Control)this.xsltStyledText, -5, 128);
        this.xsltStyledText.setEditable(false);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, -15);
        formData.right = new FormAttachment((Control)this.destList, -15, 16384);
        formData.left = new FormAttachment((Control)this.sourceTree, 15, 131072);
        this.xsltStyledText.setLayoutData((Object)formData);
        Menu commandMenu = new Menu((Control)this.xsltStyledText);
        this.xsltStyledText.setMenu(commandMenu);
        MenuItem cutMenuItem_1 = new MenuItem(commandMenu, 0);
        cutMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.xsltStyledText.cut();
            }
        });
        cutMenuItem_1.setText("Cut");
        MenuItem copyMenuItem_1 = new MenuItem(commandMenu, 0);
        copyMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.xsltStyledText.copy();
            }
        });
        copyMenuItem_1.setText("Copy");
        MenuItem pasteMenuItem_1 = new MenuItem(commandMenu, 0);
        pasteMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.xsltStyledText.paste();
            }
        });
        pasteMenuItem_1.setText("Paste");
        Button mapThemButton = new Button(this.xsltEditorComposite, 0);
        mapThemButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                XSLTEditorView.this.applyPattern();
            }
        });
        formData.top = new FormAttachment((Control)mapThemButton, 40, -1);
        mapThemButton.setToolTipText("Map Fields");
        mapThemButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/mime_mapping.gif"));
        mapThemButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                XSLTEditorView.this.applyPattern();
            }
        });
        FormData formData_1 = new FormData();
        formData_1.left = new FormAttachment((Control)this.patternCCombo, 15, -1);
        formData_1.bottom = new FormAttachment((Control)this.patternCCombo, 0, 1024);
        formData_1.top = new FormAttachment((Control)this.patternCCombo, 0, 128);
        mapThemButton.setLayoutData((Object)formData_1);
        CLabel sourceFieldsLabel = new CLabel(this.xsltEditorComposite, 2048);
        sourceFieldsLabel.setBackground(new Color[]{Display.getCurrent().getSystemColor(13), Display.getCurrent().getSystemColor(1)}, new int[]{100}, true);
        sourceFieldsLabel.setAlignment(0x1000000);
        FormData formData_2 = new FormData();
        formData_2.bottom = new FormAttachment(0, 25);
        formData_2.right = new FormAttachment(25, -10);
        formData_2.top = new FormAttachment(0, 5);
        formData_2.left = new FormAttachment(0, 10);
        sourceFieldsLabel.setLayoutData((Object)formData_2);
        sourceFieldsLabel.setText("Source Fields");
        this.viewButton = new Button(this.xsltEditorComposite, 16);
        this.viewButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.editXSLTText(false);
            }
        });
        this.viewButton.setSelection(true);
        this.viewButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.bottom = new FormAttachment((Control)this.showXSLTCheckBox, 0, 1024);
        formData_6.left = new FormAttachment((Control)this.showXSLTCheckBox, 15, -1);
        formData_6.top = new FormAttachment((Control)this.showXSLTCheckBox, 0, 128);
        this.viewButton.setLayoutData((Object)formData_6);
        this.viewButton.setText("View");
        this.editButton = new Button(this.xsltEditorComposite, 16);
        this.editButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.editXSLTText(true);
            }
        });
        this.editButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_7 = new FormData();
        formData_7.bottom = new FormAttachment((Control)this.viewButton, 0, 1024);
        formData_7.top = new FormAttachment((Control)this.viewButton, 0, 128);
        formData_7.left = new FormAttachment((Control)this.viewButton, 5, 131072);
        this.editButton.setLayoutData((Object)formData_7);
        this.editButton.setText("Edit");
        CLabel destinationFieldsLabel = new CLabel(this.xsltEditorComposite, 2048);
        FormData formData_2_1 = new FormData();
        formData_2_1.right = new FormAttachment(100, -10);
        formData_2_1.left = new FormAttachment((Control)this.destList, 5, 16384);
        formData_2_1.bottom = new FormAttachment((Control)sourceFieldsLabel, 20, 128);
        formData_2_1.top = new FormAttachment((Control)sourceFieldsLabel, 0, 128);
        destinationFieldsLabel.setLayoutData((Object)formData_2_1);
        destinationFieldsLabel.setBackground(new Color[]{Display.getCurrent().getSystemColor(13), Display.getCurrent().getSystemColor(1)}, new int[]{100}, true);
        destinationFieldsLabel.setAlignment(0x1000000);
        destinationFieldsLabel.setText("Destination Fields");
        CLabel outputTypeLabel = new CLabel(this.xsltEditorComposite, 0);
        outputTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_8 = new FormData();
        formData_8.top = new FormAttachment((Control)this.sourceTree, 0, 128);
        formData_8.left = new FormAttachment((Control)this.patternCLabel, 0, 16384);
        outputTypeLabel.setLayoutData((Object)formData_8);
        outputTypeLabel.setText("Output Type:");
        this.xsltTypeCombo = new CCombo(this.xsltEditorComposite, 2056);
        this.xsltTypeCombo.setItems(new String[]{"SOAP", "XML", "SQL"});
        this.xsltTypeCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9 = new FormData();
        formData_9.top = new FormAttachment((Control)outputTypeLabel, 0, 128);
        formData_9.left = new FormAttachment((Control)this.patternCLabel, 5, 131072);
        this.xsltTypeCombo.setLayoutData((Object)formData_9);
        CLabel nameLabel = new CLabel(this.xsltEditorComposite, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment((Control)sourceFieldsLabel, 0, 128);
        formData_3.left = new FormAttachment((Control)outputTypeLabel, 0, 16384);
        nameLabel.setLayoutData((Object)formData_3);
        nameLabel.setText("Name:");
        this.transformerName = new Text(this.xsltEditorComposite, 2048);
        this.transformerName.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4 = new FormData();
        formData_4.right = new FormAttachment((Control)this.xsltStyledText, 0, 131072);
        formData_4.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_4.left = new FormAttachment((Control)this.xsltTypeCombo, 0, 16384);
        this.transformerName.setLayoutData((Object)formData_4);
    }

    private void showXSLTText(boolean show) {
        this.xsltStyledText.setVisible(show);
        this.viewButton.setVisible(show);
        this.editButton.setVisible(show);
    }

    private void editXSLTText(boolean edit) {
        this.xsltStyledText.setEditable(edit);
    }

    private void createSourceTree() {
        this.sourceTree = new Tree(this.xsltEditorComposite, 2050);
        this.sourceTree.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                XSLTEditorView.this.sourceFieldSelected();
            }
        });
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 30);
        formData.right = new FormAttachment(25, 0);
        formData.left = new FormAttachment(0, 0);
        this.sourceTree.setLayoutData((Object)formData);
        Menu menu = new Menu((Control)this.sourceTree);
        this.sourceTree.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        newMenuItem.setText("New");
        MenuItem insertMenuItem = new MenuItem(menu, 0);
        insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        insertMenuItem.setText("Insert");
        MenuItem editMenuItem = new MenuItem(menu, 0);
        editMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        editMenuItem.setText("Edit");
        new MenuItem(menu, 2);
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.setEnabled(false);
        cutMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(menu, 0);
        copyMenuItem.setEnabled(false);
        copyMenuItem.setText("Copy");
        MenuItem pasteMenuItem = new MenuItem(menu, 0);
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.setText("Paste");
        new MenuItem(menu, 2);
        MenuItem deleteMenuItem = new MenuItem(menu, 0);
        deleteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        deleteMenuItem.setText("Delete");
    }

    private void applyPattern() {
        StringBuffer xsltText = new StringBuffer();
        TreeItem[] sf = this.sourceTree.getSelection();
        if (sf.length == 0) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Mapping Error", (String)"Source Field is not selected");
            return;
        }
        this.xsltStyledText.append(String.valueOf(sf[0].getText()) + " =");
        String[] df = this.destList.getSelection();
        if (df.length == 0) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Mapping Error", (String)"Destination Field is not selected");
            return;
        }
        switch (this.patternCCombo.getSelectionIndex()) {
            case 0: {
                xsltText.append(this.directPattern.replaceAll("#", sf[0].getText()));
                break;
            }
            case 1: {
                xsltText.append(this.toNumericPattern.replaceAll("#", sf[0].getText()));
                break;
            }
            default: {
                MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)"This pattern is not supportd by your version of IW IDE");
            }
        }
        this.xsltStyledText.insert(xsltText.toString());
    }

    private void createActions() {
        this.saveXsltAction = new Action("Save XSLT"){

            public void run() {
                XSLTEditorView.this.saveXSLT();
            }
        };
        this.saveXsltAction.setToolTipText("Save");
        this.saveXsltAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.saveXsltAction.setAccelerator(262227);
        this.action = new Action("Refresh"){

            public void run() {
                XSLTEditorView.this.refreshScreen();
            }
        };
        this.action.setToolTipText("Refresh");
        this.action.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
        this.validateAcdtion = new Action("Validate"){

            public void run() {
                XSLTEditorView.this.checkXslt();
            }
        };
        this.validateAcdtion.setToolTipText("Validate");
        this.validateAcdtion.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/check.gif"));
        this.systemEditor = new Action("System Editor"){

            public void run() {
                XSLTEditorView.this.runSystemEditor();
            }
        };
        this.systemEditor.setToolTipText("Run System Editor");
        this.systemEditor.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/usereditor.gif"));
    }

    private void newDestField(boolean append) {
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Field", "Enter New Destination Field", "", null);
        ind.open();
        if (append) {
            this.destList.add(ind.getValue().trim());
        } else {
            int si = this.destList.getSelectionIndex();
            if (si == -1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Insert Field Error", (String)"Destination Field is not selected");
                return;
            }
            this.destList.add(ind.getValue().trim(), si);
        }
    }

    public void initializeScreen() {
        String xTitle = ConfigContext.getCurrentXsltName();
        if (xTitle.equals("iwp2HTMLTable")) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)"Internal Transformers are not editable");
            return;
        }
        if (xTitle != null) {
            this.setPartName(String.valueOf(xTitle) + ".xslt");
        }
        this.refreshScreen();
    }

    public void refreshScreen() {
        Set<String> ks;
        int xtp;
        String xn = ConfigContext.getCurrentXsltName();
        if (xn != null) {
            this.transformerName.setText(xn);
        }
        String ixs = ConfigContext.readXSLTwoBS(true);
        if (this.showXSLTCheckBox.getSelection()) {
            this.xsltStyledText.setText(ConfigContext.readXSLTwoBS(false));
        }
        if ((xtp = ConfigContext.getXsltType(ixs)) < 0) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)"Unable to recognize transformer type. Editing is not possible.");
            return;
        }
        int ctp = -1;
        switch (xtp) {
            case 0: 
            case 1: {
                ctp = 2;
                break;
            }
            case 2: {
                ctp = 0;
                break;
            }
            case 3: {
                ctp = 1;
            }
        }
        this.xsltTypeCombo.select(ctp);
        this.xsltTypeCombo.setEnabled(false);
        this.destinationFieldsMapping.clear();
        Hashtable<String, String[]> sfl = ConfigContext.getSourseFields(xtp, this.destinationFieldsMapping);
        this.sourceTree.removeAll();
        TreeItem transactions = new TreeItem(this.sourceTree, 0);
        transactions.setText("Transaction[]");
        if (sfl != null && sfl.size() > 0) {
            ks = sfl.keySet();
            for (String mn : ks) {
                String[] cols;
                TreeItem map = new TreeItem(transactions, 0);
                map.setText(mn);
                String[] stringArray = cols = sfl.get(mn);
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    String cf = stringArray[n];
                    TreeItem sourceField1 = new TreeItem(map, 0);
                    sourceField1.setText(cf);
                    ++n;
                }
            }
        }
        this.destList.removeAll();
        if (this.destinationFieldsMapping.size() > 0) {
            ks = this.destinationFieldsMapping.keySet();
            Iterator<String> kit = ks.iterator();
            while (kit.hasNext()) {
                this.destList.add(kit.next());
            }
        }
    }

    private TreeItem getTreeItemByName(String name) {
        int i0 = 0;
        while (i0 < this.sourceTree.getItemCount()) {
            TreeItem ct0 = this.sourceTree.getItem(i0);
            int i1 = 0;
            while (i1 < ct0.getItemCount()) {
                TreeItem ct1 = ct0.getItem(i1);
                int i2 = 0;
                while (i2 < ct1.getItemCount()) {
                    TreeItem ct2 = ct1.getItem(i2);
                    if (ct2.getText().equals(name)) {
                        return ct2;
                    }
                    ++i2;
                }
                ++i1;
            }
            ++i0;
        }
        return null;
    }

    private void desinationFieldSelected() {
        String[] listSel;
        if (this.dstSelectionForced) {
            this.dstSelectionForced = false;
        } else if (this.destinationFieldsMapping != null && this.destinationFieldsMapping.size() > 0 && (listSel = this.destList.getSelection()).length > 0) {
            String[] treeSel = this.destinationFieldsMapping.get(listSel[0]);
            TreeItem[] selItem = new TreeItem[treeSel.length];
            int i = 0;
            while (i < treeSel.length) {
                selItem[i] = this.getTreeItemByName(treeSel[i]);
                ++i;
            }
            this.srcSelectionForced = true;
            this.sourceTree.setSelection(selItem);
            this.srcSelectionForced = false;
        }
    }

    private void sourceFieldSelected() {
        if (this.srcSelectionForced) {
            this.srcSelectionForced = false;
        } else if (this.destinationFieldsMapping != null && this.destinationFieldsMapping.size() > 0) {
            String tis = this.sourceTree.getSelection()[0].getText();
            ArrayList<Integer> idx = new ArrayList<Integer>();
            Set<String> ks = this.destinationFieldsMapping.keySet();
            for (String dsf : ks) {
                String[] smf;
                String[] stringArray = smf = this.destinationFieldsMapping.get(dsf);
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    int si;
                    String sf = stringArray[n];
                    if (sf.equals(tis) && (si = this.destList.indexOf(dsf)) >= 0) {
                        idx.add(si);
                    }
                    ++n;
                }
            }
            int is = idx.size();
            if (is > 0) {
                int[] ida = new int[is];
                Integer[] idd = idx.toArray(new Integer[0]);
                int i = 0;
                while (i < is) {
                    ida[i] = idd[i];
                    ++i;
                }
                this.dstSelectionForced = true;
                this.destList.setSelection(ida);
                this.dstSelectionForced = false;
            }
        }
    }

    private void saveXSLT() {
        boolean save = true;
        if (this.validateXslt().trim().length() > 0) {
            save = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)"This transformer is not valid. Do you want to save it anyway?");
        }
        if (!save) {
            return;
        }
        try {
            boolean nn;
            IProject cpr = Designer.getSelectedProject();
            String xn = ConfigContext.getCurrentXsltName();
            String nxn = this.transformerName.getText().trim();
            boolean bl = nn = !xn.equals(nxn);
            if (nn) {
                IFile nxf = cpr.getFile("xslt/" + nxn + ".xslt");
                if (nxf.exists()) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("Xslt file with name " + nxn + " already exists. Please use another name."));
                    return;
                }
                ConfigContext.setCurrentXsltName(nxn);
                this.setPartName(String.valueOf(nxn) + ".xslt");
            }
            ConfigContext.saveFileInProject(cpr, "xslt/" + ConfigContext.getCurrentXsltName() + ".xslt", this.xsltStyledText.getText(), true);
            if (nn) {
                IFile nx = cpr.getFile("xslt/" + xn + ".xslt");
                nx.delete(true, null);
                NavigationView nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView");
                if (nv != null) {
                    nv.setAndSelectViewer();
                }
            }
        }
        catch (IOException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("Unable to save xslt file: " + e));
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("Unable to save xslt file: " + (Object)((Object)e)));
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("Unable to save xslt file: " + e));
        }
    }

    private String validateXslt() {
        String xslText = this.xsltStyledText.getText();
        if (xslText == null) {
            return "Error: Empty transformer";
        }
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.xsltc.trax.TransformerFactoryImpl");
        TransformerFactory factory = TransformerFactory.newInstance();
        String packageName = "iwtransformationserver";
        factory.setAttribute("generate-translet", Boolean.FALSE);
        factory.setAttribute("package-name", packageName);
        factory.setAttribute("enable-inlining", Boolean.FALSE);
        StreamSource stylesheet = new StreamSource(new ByteArrayInputStream(xslText.trim().getBytes()));
        try {
            factory.newTemplates(stylesheet);
        }
        catch (TransformerConfigurationException e) {
            return "Error: " + e.getMessage();
        }
        return "";
    }

    private void checkXslt() {
        String valRes = this.validateXslt();
        if (valRes.trim().length() == 0) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)"This is valid!");
        } else {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("This is not valid! " + valRes));
        }
    }

    private void runSystemEditor() {
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/" + ConfigContext.getCurrentXsltName() + ".xslt")), "org.eclipse.ui.systemExternalEditor");
        }
        catch (PartInitException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"XSLT Editor", (String)("Unable to open System Editor: " + (Object)((Object)e)));
        }
    }
}

