/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.vews.XSLTEditorView;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.transactionType;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

public class TemplateEditorView
extends ViewPart {
    private CLabel sourceFieldsLabel;
    private FormData formData;
    private Action validateAction;
    private MenuItem fieldSetMenuItem;
    private MenuItem newMenuItem;
    private CCombo inputTypeCombo;
    private Action generateXslt;
    public static final String ID = "iw_sdk.IwTemplateEditor";
    private Action systemEditor;
    private Text transformerName;
    private Action refreshTemplate;
    private CCombo xsltTypeCombo;
    private Action saveXsltAction;
    private String fullPathb = "//iwtransformationserver/";
    private String fullPathe = "iwrecordset/";
    private String forEachSelect = "transaction[@name='#']/datamap[@name='#']/data/row";
    private String directPatternb = " select=\"";
    private String directPatterne = "col[@name='#']\"/>";
    private String toNumericPatternb = "<xsl:choose><xsl:when test=\"";
    private String toNumericPatterne = "col[@name='#']=''\">0</xsl:when><xsl:otherwise><xsl:value-of select=\"round(col[@name='#'])\"/></xsl:otherwise></xsl:choose>";
    private StyledText xsltStyledText;
    private Button editButton;
    private Button viewButton;
    private Composite container = null;
    private ScrolledComposite xsltEditorScrolledComposite = null;
    private Composite xsltEditorComposite = null;
    private Tree sourceTree = null;
    private CLabel patternCLabel = null;
    private CCombo patternCCombo = null;
    private List destList = null;
    private Button showXSLTCheckBox = null;
    private CLabel showXSLTCLabel = null;
    private Hashtable<String, MappedValues> destinationFieldsMapping;
    boolean srcSelectionForced = false;
    boolean dstSelectionForced = false;
    private Menu menu_1;

    public void createPartControl(Composite parent) {
        this.container = new Composite(parent, 0);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.right = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.left = new FormAttachment(0, 0);
        this.container.setLayoutData((Object)formData);
        this.container.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                TemplateEditorView.this.xsltEditorComposite.setSize(((TemplateEditorView)TemplateEditorView.this).container.getClientArea().width, ((TemplateEditorView)TemplateEditorView.this).container.getClientArea().height);
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
        tbm.add((IAction)this.refreshTemplate);
        tbm.add((IAction)this.systemEditor);
        tbm.add((IAction)this.validateAction);
        tbm.add((IAction)this.generateXslt);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.saveXsltAction);
        manager.add((IAction)this.refreshTemplate);
        manager.add((IAction)this.systemEditor);
        manager.add((IAction)this.validateAction);
        manager.add((IAction)this.generateXslt);
    }

    private void createXsltEditorScrolledComposite() {
        this.xsltEditorScrolledComposite = new ScrolledComposite(this.container, 768);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.right = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.left = new FormAttachment(0, 0);
        this.xsltEditorScrolledComposite.setLayoutData((Object)formData);
        this.xsltEditorScrolledComposite.setLayout((Layout)new FormLayout());
        this.createXsltEditorComposite();
        this.xsltEditorScrolledComposite.setContent((Control)this.xsltEditorComposite);
    }

    private void createXsltEditorComposite() {
        this.xsltEditorComposite = new Composite((Composite)this.xsltEditorScrolledComposite, 0);
        this.xsltEditorComposite.addPaintListener(new PaintListener(){

            public void paintControl(PaintEvent event) {
                TemplateEditorView.this.paintMap(event, true);
            }
        });
        this.xsltEditorComposite.setSize(800, 600);
        this.xsltEditorComposite.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataXEC = new FormData();
        formDataXEC.bottom = new FormAttachment(100, 0);
        formDataXEC.top = new FormAttachment(0, 0);
        formDataXEC.right = new FormAttachment(100, 0);
        formDataXEC.left = new FormAttachment(0, 0);
        this.xsltEditorComposite.setLayoutData((Object)formDataXEC);
        this.xsltEditorComposite.setLayout((Layout)new FormLayout());
        FormData formDataPCL = new FormData();
        this.patternCLabel = new CLabel(this.xsltEditorComposite, 0);
        this.patternCLabel.setLayoutData((Object)formDataPCL);
        this.patternCLabel.setText("Mapping Pattern:");
        this.patternCLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataPCC = new FormData();
        formDataPCC.top = new FormAttachment((Control)this.patternCLabel, 0, 128);
        formDataPCC.left = new FormAttachment((Control)this.patternCLabel, 5, 131072);
        this.createSourceTree();
        this.patternCCombo = new CCombo(this.xsltEditorComposite, 2056);
        this.patternCCombo.setLayoutData((Object)formDataPCC);
        FormData formDataDL = new FormData();
        formDataDL.top = new FormAttachment((Control)this.sourceTree, 0, 128);
        formDataDL.bottom = new FormAttachment(100, -5);
        formDataDL.right = new FormAttachment(100, -5);
        formDataDL.left = new FormAttachment(75, 0);
        this.destList = new List(this.xsltEditorComposite, 2050);
        this.destList.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.desinationFieldSelected();
            }
        });
        this.destList.setLayoutData((Object)formDataDL);
        Menu menu = new Menu((Control)this.destList);
        this.destList.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newVariable(true);
            }
        });
        newMenuItem.setText("New");
        MenuItem insertMenuItem = new MenuItem(menu, 0);
        insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newVariable(false);
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
        this.patternCCombo.setItems(new String[]{"DIRECT", "TO_NUMERIC", "TO_DATE", "TO_TIME", "TO_TIMESTAMP", "SUBSTRING", "SELECTION"});
        this.patternCCombo.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                if (TemplateEditorView.this.patternCCombo.getSelectionIndex() > 1) {
                    TemplateEditorView.this.patternCCombo.select(0);
                }
            }
        });
        this.patternCCombo.setBackground(Display.getCurrent().getSystemColor(1));
        this.patternCCombo.select(0);
        FormData formDataSXCL = new FormData();
        formDataSXCL.right = new FormAttachment((Control)this.patternCLabel, 100, 16384);
        formDataSXCL.left = new FormAttachment((Control)this.patternCLabel, 0, 16384);
        this.showXSLTCLabel = new CLabel(this.xsltEditorComposite, 0);
        this.formData.top = new FormAttachment((Control)this.showXSLTCLabel, 5, 1024);
        this.showXSLTCLabel.setText("Show Template:");
        this.showXSLTCLabel.setLayoutData((Object)formDataSXCL);
        this.showXSLTCLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formDataSXCB = new FormData();
        formDataSXCB.bottom = new FormAttachment((Control)this.showXSLTCLabel, 0, 1024);
        formDataSXCB.top = new FormAttachment((Control)this.showXSLTCLabel, 0, 128);
        formDataSXCB.left = new FormAttachment((Control)this.showXSLTCLabel, 5, 131072);
        this.showXSLTCheckBox = new Button(this.xsltEditorComposite, 32);
        this.showXSLTCheckBox.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.showXSLTText(TemplateEditorView.this.showXSLTCheckBox.getSelection());
            }
        });
        this.showXSLTCheckBox.setSelection(true);
        this.showXSLTCheckBox.setLayoutData((Object)formDataSXCB);
        this.showXSLTCheckBox.setBackground(Display.getCurrent().getSystemColor(1));
        this.xsltStyledText = new StyledText(this.xsltEditorComposite, 2880);
        this.formData.top = new FormAttachment((Control)this.xsltStyledText, 0, 128);
        formDataPCL.left = new FormAttachment((Control)this.xsltStyledText, 0, 16384);
        this.formData.top = new FormAttachment((Control)this.xsltStyledText, 0, 128);
        this.xsltStyledText.setEditable(false);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, -5);
        formData.right = new FormAttachment((Control)this.destList, -15, 16384);
        formData.left = new FormAttachment((Control)this.sourceTree, 15, 131072);
        this.xsltStyledText.setLayoutData((Object)formData);
        Menu commandMenu = new Menu((Control)this.xsltStyledText);
        this.xsltStyledText.setMenu(commandMenu);
        MenuItem cutMenuItem_1 = new MenuItem(commandMenu, 0);
        cutMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.xsltStyledText.cut();
            }
        });
        cutMenuItem_1.setText("Cut");
        MenuItem copyMenuItem_1 = new MenuItem(commandMenu, 0);
        copyMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.xsltStyledText.copy();
            }
        });
        copyMenuItem_1.setText("Copy");
        MenuItem pasteMenuItem_1 = new MenuItem(commandMenu, 0);
        pasteMenuItem_1.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.xsltStyledText.paste();
            }
        });
        pasteMenuItem_1.setText("Paste");
        Button mapThemButton = new Button(this.xsltEditorComposite, 0);
        mapThemButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                TemplateEditorView.this.mapFields();
            }
        });
        formData.top = new FormAttachment((Control)mapThemButton, 10, -1);
        mapThemButton.setToolTipText("Map Fields");
        mapThemButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/mime_mapping.gif"));
        mapThemButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                TemplateEditorView.this.mapFields();
            }
        });
        FormData formData_1 = new FormData();
        formData_1.left = new FormAttachment((Control)this.patternCCombo, 15, -1);
        formData_1.bottom = new FormAttachment((Control)this.patternCCombo, 0, 1024);
        formData_1.top = new FormAttachment((Control)this.patternCCombo, 0, 128);
        mapThemButton.setLayoutData((Object)formData_1);
        this.sourceFieldsLabel = new CLabel(this.xsltEditorComposite, 2048);
        this.sourceFieldsLabel.setBackground(new Color[]{Display.getCurrent().getSystemColor(13), Display.getCurrent().getSystemColor(1)}, new int[]{100}, true);
        this.sourceFieldsLabel.setAlignment(0x1000000);
        FormData formData_2 = new FormData();
        formData_2.left = new FormAttachment(0, 10);
        formData_2.right = new FormAttachment(0, 190);
        formData_2.bottom = new FormAttachment((Control)this.patternCLabel, 20, 128);
        formData_2.top = new FormAttachment((Control)this.patternCLabel, 0, 128);
        this.sourceFieldsLabel.setLayoutData((Object)formData_2);
        this.sourceFieldsLabel.setText("Source Fields");
        this.viewButton = new Button(this.xsltEditorComposite, 16);
        this.viewButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.editXSLTText(false);
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
                TemplateEditorView.this.editXSLTText(true);
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
        formData_2_1.bottom = new FormAttachment((Control)this.sourceFieldsLabel, 20, 128);
        formData_2_1.top = new FormAttachment((Control)this.sourceFieldsLabel, 0, 128);
        destinationFieldsLabel.setLayoutData((Object)formData_2_1);
        destinationFieldsLabel.setBackground(new Color[]{Display.getCurrent().getSystemColor(13), Display.getCurrent().getSystemColor(1)}, new int[]{100}, true);
        destinationFieldsLabel.setAlignment(0x1000000);
        destinationFieldsLabel.setText("Variables");
        this.xsltTypeCombo = new CCombo(this.xsltEditorComposite, 2056);
        this.xsltTypeCombo.setItems(new String[]{"SOAP", "XML", "SQL"});
        this.xsltTypeCombo.setEnabled(false);
        this.xsltTypeCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9 = new FormData();
        formData_9.right = new FormAttachment(100, -5);
        this.xsltTypeCombo.setLayoutData((Object)formData_9);
        CLabel outputTypeLabel = new CLabel(this.xsltEditorComposite, 0);
        formData_9.top = new FormAttachment((Control)outputTypeLabel, 0, 128);
        outputTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_8 = new FormData();
        formData_8.right = new FormAttachment((Control)this.xsltTypeCombo, -5, 16384);
        outputTypeLabel.setLayoutData((Object)formData_8);
        outputTypeLabel.setText("Output Type:");
        CLabel nameLabel = new CLabel(this.xsltEditorComposite, 0);
        formDataPCL.top = new FormAttachment((Control)nameLabel, 10, -1);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(0, 6);
        formData_3.left = new FormAttachment(0, 8);
        nameLabel.setLayoutData((Object)formData_3);
        nameLabel.setText("Name:");
        this.transformerName = new Text(this.xsltEditorComposite, 2048);
        formDataSXCL.top = new FormAttachment((Control)this.transformerName, -20, 1024);
        this.transformerName.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4 = new FormData();
        formData_4.right = new FormAttachment((Control)this.sourceTree, 0, 131072);
        formData_4.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_4.left = new FormAttachment((Control)nameLabel, 5, 131072);
        this.transformerName.setLayoutData((Object)formData_4);
        CLabel inputTypeLabel = new CLabel(this.xsltEditorComposite, 0);
        formData_8.top = new FormAttachment((Control)inputTypeLabel, 0, 128);
        inputTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_5 = new FormData();
        formData_5.right = new FormAttachment((Control)this.xsltStyledText, 0, 131072);
        formData_5.top = new FormAttachment((Control)nameLabel, 0, 128);
        inputTypeLabel.setLayoutData((Object)formData_5);
        inputTypeLabel.setText("Input Type:");
        this.inputTypeCombo = new CCombo(this.xsltEditorComposite, 2048);
        this.inputTypeCombo.setItems(new String[]{"IWP", "HIWP", "Other"});
        this.inputTypeCombo.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                if (!TemplateEditorView.this.inputTypeCombo.getItem(TemplateEditorView.this.inputTypeCombo.getSelectionIndex()).equals("IWP")) {
                    MessageDialog.openInformation((Shell)TemplateEditorView.this.getSite().getShell(), (String)"Template Editor", (String)"This input type is not supported by your version of IDE.");
                    TemplateEditorView.this.inputTypeCombo.select(0);
                } else {
                    TemplateEditorView.this.fieldSetMenuItem.setEnabled(false);
                }
            }
        });
        this.inputTypeCombo.select(0);
        FormData formData_10 = new FormData();
        formData_10.top = new FormAttachment((Control)inputTypeLabel, 0, 128);
        formData_10.left = new FormAttachment((Control)inputTypeLabel, 5, 131072);
        this.inputTypeCombo.setLayoutData((Object)formData_10);
    }

    private void showXSLTText(boolean show) {
        this.xsltStyledText.setVisible(show);
        this.viewButton.setVisible(show);
        this.editButton.setVisible(show);
        this.xsltEditorComposite.redraw();
        this.xsltEditorComposite.update();
        this.sourceTree.redraw();
        this.sourceTree.update();
    }

    private void editXSLTText(boolean edit) {
        this.xsltStyledText.setEditable(edit);
    }

    private void createSourceTree() {
        this.sourceTree = new Tree(this.xsltEditorComposite, 2050);
        this.sourceTree.addPaintListener(new PaintListener(){

            public void paintControl(PaintEvent e) {
                TemplateEditorView.this.paintMap(e, false);
            }
        });
        this.sourceTree.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.sourceFieldSelected();
            }
        });
        this.formData = new FormData();
        this.formData.top = new FormAttachment(0, 380);
        this.formData.bottom = new FormAttachment(100, -5);
        this.formData.left = new FormAttachment(0, 10);
        this.formData.right = new FormAttachment(0, 205);
        this.sourceTree.setLayoutData((Object)this.formData);
        Menu menu = new Menu((Control)this.sourceTree);
        this.sourceTree.setMenu(menu);
        this.newMenuItem = new MenuItem(menu, 64);
        this.newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        this.newMenuItem.setText("New");
        this.menu_1 = new Menu(this.newMenuItem);
        this.newMenuItem.setMenu(this.menu_1);
        MenuItem transactionMenuItem = new MenuItem(this.menu_1, 0);
        transactionMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newTransaction();
            }
        });
        transactionMenuItem.setText("Transaction");
        MenuItem dataMapMenuItem = new MenuItem(this.menu_1, 0);
        dataMapMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newDataMasp();
            }
        });
        dataMapMenuItem.setText("Data Map");
        this.fieldSetMenuItem = new MenuItem(this.menu_1, 0);
        this.fieldSetMenuItem.setEnabled(false);
        this.fieldSetMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newFieldSet();
            }
        });
        this.fieldSetMenuItem.setText("FieldSet");
        MenuItem fieldMenuItem = new MenuItem(this.menu_1, 0);
        fieldMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.newField();
            }
        });
        fieldMenuItem.setText("Field");
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
        new MenuItem(menu, 2);
        MenuItem importMenuItem = new MenuItem(menu, 64);
        importMenuItem.setText("Import");
        Menu menu_2 = new Menu(importMenuItem);
        importMenuItem.setMenu(menu_2);
        MenuItem iwpMenuItem = new MenuItem(menu_2, 0);
        iwpMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.importIWP();
            }
        });
        iwpMenuItem.setText("IWP");
        MenuItem hiwpMenuItem = new MenuItem(menu_2, 0);
        hiwpMenuItem.setEnabled(false);
        hiwpMenuItem.setText("HIWP");
        MenuItem ddlMenuItem = new MenuItem(menu_2, 0);
        ddlMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TemplateEditorView.this.importDDL();
            }
        });
        ddlMenuItem.setText("DDL");
        MenuItem xsdMenuItem = new MenuItem(menu_2, 0);
        xsdMenuItem.setEnabled(false);
        xsdMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
            }
        });
        xsdMenuItem.setText("XSD");
    }

    private void mapFields() {
        try {
            String mt = this.patternCCombo.getItem(this.patternCCombo.getSelectionIndex());
            TreeItem[] sf = this.sourceTree.getSelection();
            if (sf.length == 0) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Mapping Error", (String)"Source Field is not selected");
                return;
            }
            this.xsltStyledText.append(String.valueOf(sf[0].getText()) + " =");
            String[] df = this.destList.getSelection();
            if (df.length != 1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Mapping Error", (String)"One Variable must be selected");
                return;
            }
            ArrayList<String> os = new ArrayList<String>();
            TreeItem[] treeItemArray = sf;
            int n = 0;
            int n2 = treeItemArray.length;
            while (n < n2) {
                TreeItem ti = treeItemArray[n];
                String label = ti.getText();
                if (label.startsWith("FieldSet[") || label.startsWith("DataMap[") || label.startsWith("Transaction[")) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Mapping Error", (String)"Only source fields can be selected");
                    return;
                }
                os.add(label);
                ++n;
            }
            TreeItem sp = sf[0].getParentItem();
            this.fieldSetMenuItem.isEnabled();
            TreeItem spp = sp.getParentItem();
            String dm = sp.getText();
            String tr = spp.getText();
            int pi = df[0].indexOf("(");
            String vn = pi < 0 ? df[0] : df[0].substring(0, pi);
            String[] mf = os.toArray(new String[0]);
            this.destinationFieldsMapping.put(vn, new MappedValues(mt, tr.substring(tr.indexOf("[") + 1, tr.indexOf("]")), dm.substring(dm.indexOf("[") + 1, dm.indexOf("]")), mf));
            StringBuffer ndb = new StringBuffer(String.valueOf(vn) + "(");
            int i = 0;
            while (i < mf.length) {
                ndb.append(mf[i]);
                if (i < mf.length - 1) {
                    ndb.append(":");
                }
                ++i;
            }
            ndb.append(")");
            this.destList.setItem(this.destList.getSelectionIndex(), ndb.toString());
            this.xsltEditorComposite.redraw();
            this.xsltEditorComposite.update();
            this.sourceTree.redraw();
            this.sourceTree.update();
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Mapping error: " + e));
            return;
        }
    }

    private void createActions() {
        this.saveXsltAction = new Action("Save Template"){

            public void run() {
                TemplateEditorView.this.saveXSLT();
            }
        };
        this.saveXsltAction.setToolTipText("Save");
        this.saveXsltAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.saveXsltAction.setAccelerator(262227);
        this.refreshTemplate = new Action("Refresh"){

            public void run() {
                TemplateEditorView.this.refreshScreen();
            }
        };
        this.refreshTemplate.setToolTipText("Refresh");
        this.refreshTemplate.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
        this.systemEditor = new Action("System Editor"){

            public void run() {
                TemplateEditorView.this.runSystemEditor();
            }
        };
        this.systemEditor.setToolTipText("Run System Editor");
        this.systemEditor.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/usereditor.gif"));
        this.generateXslt = new Action("Generate XSLT Transformer"){

            public void run() {
                TemplateEditorView.this.generateXSLT();
            }
        };
        this.generateXslt.setToolTipText("Generate Transformer");
        this.generateXslt.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/changelog_obj.gif"));
        this.validateAction = new Action("Validate"){

            public void run() {
                TemplateEditorView.this.checkXslt();
            }
        };
        this.validateAction.setToolTipText("Validate");
        this.validateAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/check.gif"));
    }

    private void newVariable(boolean append) {
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Variable", "Enter New Template Variable", "", null);
        ind.open();
        if (append) {
            this.destList.add(ind.getValue().trim());
        } else {
            int si = this.destList.getSelectionIndex();
            if (si == -1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Insert Variable", (String)"Variable is not selected");
                return;
            }
            this.destList.add(ind.getValue().trim(), si);
        }
    }

    private void newTransaction() {
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Transaction", "Enter New Transaction Name", "", null);
        ind.open();
        TreeItem transaction = new TreeItem(this.sourceTree, 0);
        transaction.setText(this.transaction4Tree(ind.getValue()));
    }

    private String transaction4Tree(String transactionName) {
        return "Transaction[" + transactionName.trim() + "]";
    }

    private void newDataMasp() {
        TreeItem[] si = this.sourceTree.getSelection();
        if (si.length != 1 || !si[0].getText().startsWith("Transaction[")) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"One parent transaction for the new Data Map must be selected.");
            return;
        }
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Data Map", "Enter New Data Map Name", "", null);
        ind.open();
        TreeItem datamap = new TreeItem(si[0], 0);
        datamap.setText(this.datamap4Tree(ind.getValue()));
    }

    private String datamap4Tree(String datamapName) {
        return "DataMap[" + datamapName.trim() + "]";
    }

    private void newFieldSet() {
        TreeItem[] si = this.sourceTree.getSelection();
        if (si.length != 1 || !si[0].getText().startsWith("DataMap[") || !si[0].getText().startsWith("FieldSet[")) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"One parent data map or field set for the new fieldset must be selected.");
            return;
        }
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Fieldset", "Enter New Fieldset Name", "", null);
        ind.open();
        TreeItem datamap = new TreeItem(si[0], 0);
        datamap.setText("FieldSet[" + ind.getValue().trim() + "]");
    }

    private void newField() {
        TreeItem[] si;
        String parentName = "DataMap[";
        String parentLabel = "Data Map";
        if (this.fieldSetMenuItem.isEnabled()) {
            parentName = "FieldSet[";
            parentLabel = "Field Set";
        }
        if ((si = this.sourceTree.getSelection()).length != 1 || !si[0].getText().startsWith(parentName)) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("One parent " + parentLabel + " for the new fieldset must be selected."));
            return;
        }
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Field", "Enter New Field Name", "", null);
        ind.open();
        TreeItem datamap = new TreeItem(si[0], 0);
        datamap.setText(ind.getValue().trim());
    }

    public void initializeScreen() {
        String xTitle = ConfigContext.getCurrentTemplateName();
        if (xTitle != null) {
            this.setPartName(String.valueOf(xTitle) + ".iwxt");
        }
        this.refreshScreen();
    }

    public void refreshScreen() {
        int xtp;
        String ixs;
        try {
            this.sourceTree.removeAll();
            this.destinationFieldsMapping = new Hashtable();
            IProject cp = Designer.getSelectedProject();
            String[] stringArray = ConfigContext.getTransactions(cp, false);
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cti = stringArray[n];
                TreeItem ct = new TreeItem(this.sourceTree, 0);
                ct.setText(this.transaction4Tree(cti));
                iwmappingsType ir = ConfigContext.getIwmappingsRoot();
                if (ir == null) {
                    ConfigContext.loadIwmappingsRoot(cp);
                    ir = ConfigContext.getIwmappingsRoot();
                }
                transactionType tt = ir.gettransactionAt(ConfigContext.getTransactionIndex(cti));
                int i = 0;
                while (i < tt.getdatamapCount()) {
                    TreeItem dct = new TreeItem(ct, 0);
                    dct.setText(this.datamap4Tree(tt.getdatamapAt(i).getname().getValue()));
                    ++i;
                }
                ++n;
            }
        }
        catch (Exception e1) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to populate the source tree:" + e1));
        }
        String xn = ConfigContext.getCurrentTemplateName();
        if (xn != null) {
            this.transformerName.setText(xn);
        }
        if ((ixs = ConfigContext.readTemplateWoBS(true)) == null) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to read template. Editing is not possible.");
            return;
        }
        if (this.showXSLTCheckBox.getSelection()) {
            this.xsltStyledText.setText(ConfigContext.readTemplateWoBS(false));
        }
        if ((xtp = ConfigContext.getXsltType(ixs)) < 0) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to recognize template type. Editing is not possible.");
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
        this.destList.setItems(ConfigContext.getTemplateVariables(ixs));
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
                    if (this.fieldSetMenuItem.isEnabled()) {
                        int i3 = 0;
                        while (i3 < ct2.getItemCount()) {
                            TreeItem ct3 = ct2.getItem(i2);
                            if (ct3.getText().equals(name)) {
                                return ct3;
                            }
                            ++i2;
                        }
                    } else if (ct2.getText().equals(name)) {
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

    private TreeItem getTransactionByName(String name) {
        int i0 = 0;
        while (i0 < this.sourceTree.getItemCount()) {
            TreeItem ct0 = this.sourceTree.getItem(i0);
            if (ct0.getText().equals(name)) {
                return ct0;
            }
            ++i0;
        }
        return null;
    }

    private TreeItem getTreeChildByName(TreeItem treeItem, String name) {
        int i0 = 0;
        while (i0 < treeItem.getItemCount()) {
            TreeItem ct0 = treeItem.getItem(i0);
            if (ct0.getText().equals(name)) {
                return ct0;
            }
            ++i0;
        }
        return null;
    }

    private TreeItem getdatamapByName(String name) {
        int i0 = 0;
        while (i0 < this.sourceTree.getItemCount()) {
            TreeItem ct0 = this.sourceTree.getItem(i0);
            int i1 = 0;
            while (i1 < ct0.getItemCount()) {
                TreeItem ct1 = ct0.getItem(i1);
                if (ct1.getText().equals(name)) {
                    return ct1;
                }
                ++i1;
            }
            ++i0;
        }
        return null;
    }

    private void desinationFieldSelected() {
        int pi;
        String vn;
        MappedValues mv;
        if (this.dstSelectionForced) {
            this.dstSelectionForced = false;
        } else if (this.destinationFieldsMapping != null && this.destinationFieldsMapping.size() > 0 && (mv = this.destinationFieldsMapping.get(vn = (pi = this.destList.getSelection()[0].indexOf("(")) < 0 ? this.destList.getSelection()[0] : this.destList.getSelection()[0].substring(0, pi))) != null) {
            String[] treeSel = mv.getMappingValues();
            TreeItem[] selItem = new TreeItem[treeSel.length];
            int i = 0;
            while (i < treeSel.length) {
                selItem[i] = this.getTreeItemByName(treeSel[i]);
                ++i;
            }
            this.srcSelectionForced = true;
            this.sourceTree.setSelection(selItem);
            this.srcSelectionForced = false;
            this.xsltEditorComposite.redraw();
            this.xsltEditorComposite.update();
            this.sourceTree.redraw();
            this.sourceTree.update();
        }
    }

    private void sourceFieldSelected() {
        if (this.srcSelectionForced) {
            this.srcSelectionForced = false;
        } else if (this.destinationFieldsMapping != null && this.destinationFieldsMapping.size() > 0) {
            String tis = this.sourceTree.getSelection()[0].getText();
            int idx = -1;
            Set<String> ks = this.destinationFieldsMapping.keySet();
            for (String dsf : ks) {
                String[] smf;
                String[] stringArray = smf = this.destinationFieldsMapping.get(dsf).getMappingValues();
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    String sf = stringArray[n];
                    if (sf.equals(tis)) {
                        String[] stringArray2 = this.destList.getItems();
                        int n3 = 0;
                        int n4 = stringArray2.length;
                        while (n3 < n4) {
                            String vn;
                            String cd = stringArray2[n3];
                            int pi = cd.indexOf("(");
                            String string = vn = pi < 0 ? cd : cd.substring(0, pi);
                            if (vn.equals(dsf)) {
                                idx = this.destList.indexOf(cd);
                            }
                            ++n3;
                        }
                    }
                    ++n;
                }
            }
            if (idx >= 0) {
                this.dstSelectionForced = true;
                this.destList.setSelection(idx);
                this.dstSelectionForced = false;
                this.xsltEditorComposite.redraw();
                this.xsltEditorComposite.update();
                this.sourceTree.redraw();
                this.sourceTree.update();
            }
        }
    }

    private void saveXSLT() {
        boolean save = true;
        if (!this.validateXslt()) {
            save = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"This transformer is not valid. Do you want to save it anyway?");
        }
        if (!save) {
            return;
        }
        try {
            boolean nn;
            IProject cpr = Designer.getSelectedProject();
            String xn = ConfigContext.getCurrentTemplateName();
            String nxn = this.transformerName.getText().trim();
            boolean bl = nn = !xn.equals(nxn);
            if (nn) {
                IFile nxf = cpr.getFile("xslt/Site/" + nxn + ".iwxt");
                if (nxf.exists()) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Template with name " + nxn + " already exists. Please use another name."));
                    return;
                }
                ConfigContext.setCurrentTemplateName(nxn);
                this.setPartName(String.valueOf(nxn) + ".iwxt");
            }
            ConfigContext.saveFileInProject(cpr, "xslt/Site/" + ConfigContext.getCurrentTemplateName() + ".iwxt", this.xsltStyledText.getText(), true);
            if (nn) {
                IFile nx = cpr.getFile("xslt/Site/" + xn + ".iwxt");
                nx.delete(true, null);
                NavigationView nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView");
                if (nv != null) {
                    nv.setAndSelectViewer();
                }
            }
        }
        catch (IOException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to save template: " + e));
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to save template: " + (Object)((Object)e)));
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to save template: " + e));
        }
    }

    private boolean validateXslt() {
        String xslText = this.xsltStyledText.getText();
        if (xslText == null) {
            return false;
        }
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.xsltc.trax.TransformerFactoryImpl");
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("generate-translet", Boolean.FALSE);
        StreamSource stylesheet = new StreamSource(new ByteArrayInputStream(xslText.trim().getBytes()));
        try {
            factory.newTemplates(stylesheet);
        }
        catch (TransformerConfigurationException e) {
            return false;
        }
        return true;
    }

    private void checkXslt() {
        if (this.validateXslt()) {
            MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"This is valid!");
        } else {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"This is not valid!");
        }
    }

    private void paintMap(PaintEvent event, boolean composite) {
        try {
            GC gc = event.gc;
            if (this.showXSLTCheckBox != null && !this.showXSLTCheckBox.getSelection() && this.destinationFieldsMapping != null && this.destinationFieldsMapping.size() > 0) {
                Color blue = new Color((Device)event.widget.getDisplay(), 0, 0, 255);
                Color red = new Color((Device)event.widget.getDisplay(), 255, 0, 0);
                Color black = new Color((Device)event.widget.getDisplay(), 0, 0, 0);
                gc.setLineWidth(2);
                gc.setLineStyle(3);
                int i = 0;
                while (i < this.destList.getItemCount()) {
                    String[] treeSel;
                    if (this.destList.getSelectionIndex() == i) {
                        gc.setForeground(red);
                    } else {
                        gc.setForeground(blue);
                    }
                    String cdi = this.destList.getItem(i);
                    int pi = cdi.indexOf("(");
                    String vn = pi < 0 ? cdi : cdi.substring(0, pi);
                    MappedValues mv = this.destinationFieldsMapping.get(vn);
                    if (mv != null && (treeSel = mv.getMappingValues()) != null) {
                        int xd = 0;
                        int yd = 0;
                        if (composite) {
                            xd = this.destList.getBounds().x;
                            yd = this.destList.getBounds().y + this.destList.getBorderWidth() + this.destList.getItemHeight() * i + this.destList.getItemHeight() / 2;
                        }
                        int j = 0;
                        while (j < treeSel.length) {
                            Rectangle sr = this.getTreeItemByName(treeSel[j]).getBounds();
                            int xs = this.sourceTree.getBounds().x + this.sourceTree.getBounds().width;
                            int ys = (composite ? this.sourceTree.getBounds().y + this.sourceTree.getBorderWidth() : 0) + sr.y + sr.height / 2;
                            if (!composite) {
                                xd = sr.x + sr.width;
                                yd = ys;
                            }
                            gc.drawLine(xs, ys, xd, yd);
                            ++j;
                        }
                    }
                    ++i;
                }
                blue.dispose();
                red.dispose();
                black.dispose();
            }
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Drawing error: " + e));
            return;
        }
    }

    private void importIWP() {
        FileDialog fd = new FileDialog(this.getSite().getShell(), 4096);
        String fp = fd.open().trim();
        String nameTag = "name=\"";
        String trTag = "<transaction " + nameTag;
        String treTag = "</transaction>";
        String dmTag = "<datamap";
        String dmeTag = "</datamap>";
        String colTag = "<col";
        if (fp != null && fp.length() > 0) {
            int tpe;
            String iwpFile = ConfigContext.readFile(fp);
            int tp = 0;
            while ((tpe = iwpFile.indexOf(trTag, tp)) >= 0) {
                int dmb;
                int nb = tpe + trTag.length();
                int ne = iwpFile.indexOf("\"", nb);
                if (ne < 0) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: transaction name is malformed.");
                    return;
                }
                String trName = iwpFile.substring(nb, ne);
                int trcb = iwpFile.indexOf(">", ne + 1);
                if (ne < 0) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: transaction tag is malformed.");
                    return;
                }
                int tee = iwpFile.indexOf(treTag, trcb);
                if (tee < 0) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: transaction is malformed.");
                    return;
                }
                int dmp = trcb + 1;
                while ((dmb = iwpFile.indexOf(dmTag, dmp)) >= 0 && dmb <= tee) {
                    int dmee;
                    int nmb = iwpFile.indexOf(nameTag, dmb + dmTag.length());
                    if (nmb < 0) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: datamap name is missing.");
                        return;
                    }
                    int nme = iwpFile.indexOf("\"", nmb += nameTag.length());
                    if (nme < 0) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: datamap name is malformed.");
                        return;
                    }
                    String dmName = iwpFile.substring(nmb, nme);
                    int dmcb = iwpFile.indexOf(">", nme + 1);
                    if (nme < 0) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: datamap tag is malformed.");
                        return;
                    }
                    TreeItem di = null;
                    TreeItem ti = this.getTransactionByName(this.transaction4Tree(trName));
                    if (ti == null) {
                        di = this.getdatamapByName(this.datamap4Tree(dmName));
                        if (di != null && !MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Data Map " + dmName + " exists in another transaction " + (trName.length() == 0 ? "" : "(not " + trName + ")") + "in the tree. Do you want to assign fields to it?"))) {
                            di = null;
                        }
                    } else {
                        di = this.getTreeChildByName(ti, this.datamap4Tree(dmName));
                    }
                    if ((dmee = iwpFile.indexOf(dmeTag, dmcb)) < 0) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: datamap is malformed.");
                        return;
                    }
                    if (di != null) {
                        int cb;
                        int clp = dmcb + 1;
                        while ((cb = iwpFile.indexOf(colTag, clp)) >= 0 && cb <= dmee) {
                            int ncb = iwpFile.indexOf(nameTag, cb + colTag.length());
                            if (ncb < 0) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: field name is missing.");
                                return;
                            }
                            int nce = iwpFile.indexOf("\"", ncb += nameTag.length());
                            if (nme < 0) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process IWP: field name is malformed.");
                                return;
                            }
                            String colName = iwpFile.substring(ncb, nce);
                            if (this.getTreeItemByName(colName) == null) {
                                TreeItem fi = new TreeItem(di, 0);
                                fi.setText(colName);
                            }
                            clp = nce + 1;
                        }
                    }
                    dmp = dmee + dmeTag.length();
                }
                tp = tee + treTag.length();
            }
        }
    }

    private void importDDL() {
        TreeItem[] si = this.sourceTree.getSelection();
        if (si.length != 1 || !si[0].getText().startsWith("DataMap[")) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Ona DataMap must be selected to link columns from ddl!");
            return;
        }
        FileDialog fd = new FileDialog(this.getSite().getShell(), 4096);
        fd.setFilterExtensions(new String[]{"*.sql", "*.ddl"});
        String ifp = fd.open().trim();
        if (ifp != null && ifp.length() > 0) {
            int lpp;
            String fp = ConfigContext.readFile(ifp);
            int rpp = -1;
            while ((lpp = fp.indexOf("(", ++rpp)) >= 0) {
                String[] cd;
                if ((rpp = this.findRightBrace(++lpp, fp)) < 0) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process DDL: Syntax error.");
                    return;
                }
                String[] stringArray = cd = fp.substring(lpp, rpp).split(",");
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    String ccd = stringArray[n];
                    String[] tn = ccd.trim().split("\\s+");
                    if (tn.length < 2) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to process DDL: Syntax error.");
                        return;
                    }
                    String cn = tn[0];
                    if (!cn.equalsIgnoreCase("primary") && !cn.equalsIgnoreCase("foreighn")) {
                        if (cn.startsWith("'") && cn.endsWith("'") || cn.startsWith("`") && cn.endsWith("`") || cn.startsWith("\"") && cn.endsWith("\"")) {
                            cn = cn.substring(1, cn.length() - 1);
                        }
                        TreeItem ni = new TreeItem(si[0], 0);
                        ni.setText(cn);
                    }
                    ++n;
                }
            }
        }
    }

    private int findRightBrace(int pos, String str) {
        int rb;
        int rp = pos;
        while ((rb = str.indexOf(")", rp)) >= 0) {
            if (str.substring(rp, rb).indexOf("(") >= 0) {
                rp = ++rb;
                continue;
            }
            return rb;
        }
        return rb;
    }

    private void generateXSLT() {
        int fit;
        int vi;
        if (this.destinationFieldsMapping == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"No mapping is defined to generate transformer");
            return;
        }
        String[] stringArray = this.destList.getItems();
        int n = 0;
        int n2 = stringArray.length;
        while (n < n2) {
            String vn = stringArray[n];
            if (vn.indexOf("(") < 0) {
                if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Not all variables are mapped. Do you still want to generate a transformer?")) break;
                return;
            }
            ++n;
        }
        int vib = 0;
        String template = ConfigContext.readTemplateWoBS(true);
        if (template == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Unable to read template.");
            return;
        }
        String varTag = "<xsl:variable";
        String varEndTag = "</xsl:variable>";
        String nameTag = "name=\"";
        String closeTag = "/>";
        String forEachTag = "<xsl:for-each select=\"";
        StringBuffer oxf = new StringBuffer();
        String dataMapSelected = null;
        String transactionSelected = null;
        String[] fieldsetSelected = null;
        int mfit = -1;
        if (!this.fieldSetMenuItem.isEnabled() && (mfit = template.indexOf(forEachTag)) > 0) {
            TreeItem[] si = this.sourceTree.getSelection();
            if (si.length != 1 || !si[0].getText().startsWith("DataMap[")) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Ona DataMap must be selected for the main loop!");
                return;
            }
            String dm = si[0].getText();
            dataMapSelected = dm.substring(dm.indexOf("[") + 1, dm.indexOf("]"));
            String tr = si[0].getParentItem().getText();
            transactionSelected = tr.substring(tr.indexOf("[") + 1, tr.indexOf("]"));
        }
        while ((vi = template.indexOf(varTag, vib)) >= 0) {
            int ni = template.indexOf(nameTag, vib + varTag.length());
            if (ni < 0) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Name attribute is missing.");
                return;
            }
            int ne = template.indexOf("\"", ni += nameTag.length());
            if (ne < 0) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Name attribute is malformed.");
                return;
            }
            String dsf = template.substring(ni, ne++);
            oxf.append(template.substring(vib, ne));
            int ve = template.indexOf(closeTag, ne);
            if (ve < 0) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)"Variable is malformed.");
                return;
            }
            vib = ve += closeTag.length();
            MappedValues mv = this.destinationFieldsMapping.get(dsf);
            if (mv != null) {
                String[] sf;
                if (mv.getMappingType().equals("DIRECT")) {
                    if (this.fieldSetMenuItem.isEnabled() || (sf = mv.getMappingValues()).length != 1) continue;
                    if (mfit < 0 || vi < mfit || !mv.getParentDataMap().equals(dataMapSelected)) {
                        oxf.append(String.valueOf(this.directPatternb) + this.fullPathb + this.fullPathe + this.forEachSelect.replaceFirst("#", mv.getParentTransaction()).replaceFirst("#", mv.getParentDataMap()) + "/" + this.directPatterne.replaceFirst("#", sf[0]));
                        continue;
                    }
                    oxf.append(String.valueOf(this.directPatternb) + this.directPatterne.replaceFirst("#", sf[0]));
                    continue;
                }
                if (!mv.getMappingType().equals("TO_NUMERIC")) continue;
                oxf.append(">");
                if (!this.fieldSetMenuItem.isEnabled() && (sf = mv.getMappingValues()).length == 1) {
                    if (mfit < 0 || vi < mfit || !mv.getParentDataMap().equals(dataMapSelected)) {
                        oxf.append(String.valueOf(this.toNumericPatternb) + this.fullPathb + this.fullPathe + this.forEachSelect.replaceFirst("#", mv.getParentTransaction()).replaceFirst("#", mv.getParentDataMap()) + "/" + this.toNumericPatterne.replaceAll("#", sf[0]));
                    } else {
                        oxf.append(String.valueOf(this.toNumericPatternb) + this.toNumericPatterne.replaceAll("#", sf[0]));
                    }
                }
                oxf.append(varEndTag);
                continue;
            }
            oxf.append(template.substring(ne, ve));
        }
        oxf.append(template.substring(vib));
        if (!this.fieldSetMenuItem.isEnabled() && (fit = oxf.indexOf(forEachTag)) > 0 && oxf.charAt(fit += forEachTag.length()) == '.') {
            oxf.replace(fit, fit + 1, String.valueOf(this.fullPathe) + this.forEachSelect.replaceFirst("#", transactionSelected).replaceFirst("#", dataMapSelected));
        }
        InputDialog ind = new InputDialog(this.getSite().getShell(), "New Transformer", "Enter New XSLT Transformer Name", "", null);
        ind.open();
        try {
            String xsltName = ind.getValue();
            ConfigContext.saveFileInProject(Designer.getSelectedProject(), "xslt/" + xsltName + ".xslt", oxf.toString(), false);
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
            ConfigContext.setCurrentXsltName(xsltName);
            ap.showView("iw_sdk.XSLTEditorView");
            ((XSLTEditorView)ap.findView("iw_sdk.XSLTEditorView")).initializeScreen();
        }
        catch (IOException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to save file: " + e));
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to save file: " + (Object)((Object)e)));
        }
    }

    private void runSystemEditor() {
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/Site/" + ConfigContext.getCurrentTemplateName() + ".iwxt")), "org.eclipse.ui.systemExternalEditor");
        }
        catch (PartInitException e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Template Editor", (String)("Unable to open System Editor: " + (Object)((Object)e)));
        }
    }

    protected class MappedValues {
        protected String mappingType;
        protected String parentDataMap;
        protected String parentTransaction;
        protected String[] mappingValues;

        public MappedValues(String mappingType2, String parentTransaction, String parentDataMap, String[] mappingValues) {
            this.mappingType = mappingType2;
            this.parentTransaction = parentTransaction;
            this.parentDataMap = parentDataMap;
            this.mappingValues = mappingValues;
        }

        protected String getParentTransaction() {
            return this.parentTransaction;
        }

        protected String getMappingType() {
            return this.mappingType;
        }

        protected String[] getMappingValues() {
            return this.mappingValues;
        }

        public String getParentDataMap() {
            return this.parentDataMap;
        }
    }
}

