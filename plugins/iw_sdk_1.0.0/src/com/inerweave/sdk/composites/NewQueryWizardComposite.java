/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.inerweave.sdk.TransactionBase;
import com.inerweave.sdk.vews.iw_dialogs.PropertyDialog;
import com.swtdesigner.SWTResourceManager;
import java.sql.Timestamp;
import java.util.Hashtable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CLabel;
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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class NewQueryWizardComposite
extends Composite {
    private Button activeFlow;
    private Text secondaryTsUrl;
    private CLabel primaryTsLabel;
    private Text primaryTsUrl;
    private Button hostedPublic;
    private CLabel publicLabel;
    private TableColumn newColumnTableColumn_4;
    private TableColumn newColumnTableColumn_3;
    private TableColumn newColumnTableColumn_2;
    private TableColumn newColumnTableColumn_1;
    private TableColumn newColumnTableColumn;
    private Text transactionId;
    private Table propertyTable;

    public NewQueryWizardComposite(Composite parent, int style) {
        super(parent, style);
        this.setBackground(Display.getCurrent().getSystemColor(1));
        this.setLayout((Layout)new FormLayout());
        CLabel idLabel = new CLabel((Composite)this, 0);
        FormData formData = new FormData();
        formData.top = new FormAttachment(5, 0);
        formData.left = new FormAttachment(0, 5);
        idLabel.setLayoutData((Object)formData);
        idLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        idLabel.setText("Id:");
        this.primaryTsLabel = new CLabel((Composite)this, 0);
        this.primaryTsLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_26 = new FormData();
        formData_26.top = new FormAttachment(20, 0);
        formData_26.left = new FormAttachment((Control)idLabel, 0, 16384);
        this.primaryTsLabel.setLayoutData((Object)formData_26);
        this.primaryTsLabel.setText("Primary TS URL:");
        this.propertyTable = new Table((Composite)this, 67584);
        this.propertyTable.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                NewQueryWizardComposite.this.propertyOperation(0);
            }
        });
        this.propertyTable.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                if (e.character == '\r') {
                    NewQueryWizardComposite.this.propertyOperation(0);
                }
            }
        });
        this.propertyTable.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                NewQueryWizardComposite.this.newColumnTableColumn.setWidth(((NewQueryWizardComposite)NewQueryWizardComposite.this).propertyTable.getSize().x / 6);
                NewQueryWizardComposite.this.newColumnTableColumn_2.setWidth(((NewQueryWizardComposite)NewQueryWizardComposite.this).propertyTable.getSize().x / 12);
                NewQueryWizardComposite.this.newColumnTableColumn_3.setWidth(((NewQueryWizardComposite)NewQueryWizardComposite.this).propertyTable.getSize().x / 12);
                NewQueryWizardComposite.this.newColumnTableColumn_4.setWidth(((NewQueryWizardComposite)NewQueryWizardComposite.this).propertyTable.getSize().x / 6);
                NewQueryWizardComposite.this.newColumnTableColumn_1.setWidth(((NewQueryWizardComposite)NewQueryWizardComposite.this).propertyTable.getSize().x - NewQueryWizardComposite.this.newColumnTableColumn.getWidth() * 3 - NewQueryWizardComposite.this.propertyTable.getBorderWidth() * 4);
            }
        });
        this.propertyTable.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment(50, 0);
        formData_6.right = new FormAttachment(100, 0);
        formData_6.bottom = new FormAttachment(100, 0);
        formData_6.left = new FormAttachment(0, 0);
        this.propertyTable.setLayoutData((Object)formData_6);
        this.propertyTable.setLinesVisible(true);
        this.propertyTable.setHeaderVisible(true);
        this.newColumnTableColumn = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn.setWidth(this.propertyTable.getSize().x / 6);
        this.newColumnTableColumn.setText("Name");
        this.newColumnTableColumn_1 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_1.setWidth(this.propertyTable.getSize().x - this.newColumnTableColumn.getWidth() * 3 - this.propertyTable.getBorderWidth() * 4);
        this.newColumnTableColumn_1.setText("Property Value");
        this.newColumnTableColumn_2 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_2.setWidth(this.propertyTable.getSize().x / 12);
        this.newColumnTableColumn_2.setText("File Upload");
        this.newColumnTableColumn_3 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_3.setWidth(this.propertyTable.getSize().x / 12);
        this.newColumnTableColumn_3.setText("Password");
        this.newColumnTableColumn_4 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_4.setWidth(this.propertyTable.getSize().x / 6);
        this.newColumnTableColumn_4.setText("Dynamic Update");
        Menu menu = new Menu((Control)this.propertyTable);
        this.propertyTable.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewQueryWizardComposite.this.propertyOperation(-1);
            }
        });
        newMenuItem.setText("New");
        MenuItem insertMenuItem = new MenuItem(menu, 0);
        insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewQueryWizardComposite.this.propertyOperation(NewQueryWizardComposite.this.propertyTable.getSelectionIndex() + 1);
            }
        });
        insertMenuItem.setText("Insert");
        MenuItem vieweditMenuItem = new MenuItem(menu, 0);
        vieweditMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewQueryWizardComposite.this.propertyOperation(0);
            }
        });
        vieweditMenuItem.setText("Edit");
        MenuItem menuItem = new MenuItem(menu, 2);
        menuItem.setText("Menu item");
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.setEnabled(false);
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
                NewQueryWizardComposite.this.propertyTable.remove(NewQueryWizardComposite.this.propertyTable.getSelectionIndex());
            }
        });
        deleteMenuItem.setText("Delete");
        this.transactionId = new Text((Composite)this, 2048);
        FormData formData_7 = new FormData();
        formData_7.right = new FormAttachment(0, 347);
        formData_7.left = new FormAttachment((Control)this.primaryTsLabel, 0, 131072);
        formData_7.bottom = new FormAttachment((Control)idLabel, 0, 1024);
        formData_7.top = new FormAttachment((Control)idLabel, 0, 128);
        this.transactionId.setLayoutData((Object)formData_7);
        this.transactionId.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.publicLabel = new CLabel((Composite)this, 0);
        this.publicLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_14_1 = new FormData();
        formData_14_1.top = new FormAttachment(35, 0);
        formData_14_1.left = new FormAttachment((Control)this.primaryTsLabel, 0, 16384);
        this.publicLabel.setLayoutData((Object)formData_14_1);
        this.publicLabel.setText("Public:");
        this.hostedPublic = new Button((Composite)this, 32);
        this.hostedPublic.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_15_1 = new FormData();
        formData_15_1.top = new FormAttachment((Control)this.publicLabel, 0, 128);
        formData_15_1.left = new FormAttachment((Control)this.primaryTsLabel, 0, 131072);
        this.hostedPublic.setLayoutData((Object)formData_15_1);
        CLabel label = new CLabel((Composite)this, 0);
        FormData formData_25 = new FormData();
        formData_25.bottom = new FormAttachment(0, 55);
        formData_25.right = new FormAttachment(0, 10);
        formData_25.top = new FormAttachment(0, 55);
        formData_25.left = new FormAttachment((Control)idLabel, 0, 16384);
        label.setLayoutData((Object)formData_25);
        label.setText("label");
        this.primaryTsUrl = new Text((Composite)this, 2048);
        FormData formData_x = new FormData();
        formData_x.right = new FormAttachment((Control)this.getTransactionId(), 0, 131072);
        formData_x.top = new FormAttachment((Control)this.primaryTsLabel, 0, 128);
        formData_x.left = new FormAttachment((Control)this.getTransactionId(), 0, 16384);
        this.primaryTsUrl.setLayoutData((Object)formData_x);
        CLabel secondaryTsUrlLabel = new CLabel((Composite)this, 0);
        secondaryTsUrlLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1_x = new FormData();
        formData_1_x.left = new FormAttachment(0, 367);
        formData_1_x.top = new FormAttachment((Control)this.primaryTsUrl, 0, 128);
        secondaryTsUrlLabel.setLayoutData((Object)formData_1_x);
        secondaryTsUrlLabel.setText("Secondary TS URL:");
        this.secondaryTsUrl = new Text((Composite)this, 2048);
        FormData formData_2_x = new FormData();
        formData_2_x.left = new FormAttachment(0, 487);
        formData_2_x.right = new FormAttachment(100, -20);
        formData_2_x.top = new FormAttachment((Control)secondaryTsUrlLabel, 0, 128);
        this.secondaryTsUrl.setLayoutData((Object)formData_2_x);
        CLabel activeLabel = new CLabel((Composite)this, 0);
        activeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3_x = new FormData();
        formData_3_x.top = new FormAttachment((Control)this.getTransactionId(), 0, 128);
        formData_3_x.left = new FormAttachment((Control)secondaryTsUrlLabel, 0, 16384);
        activeLabel.setLayoutData((Object)formData_3_x);
        activeLabel.setText("Active:");
        this.activeFlow = new Button((Composite)this, 32);
        this.activeFlow.setSelection(true);
        this.activeFlow.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4_x = new FormData();
        formData_4_x.top = new FormAttachment((Control)activeLabel, 0, 128);
        formData_4_x.left = new FormAttachment((Control)this.secondaryTsUrl, 0, 16384);
        this.activeFlow.setLayoutData((Object)formData_4_x);
    }

    private void propertyOperation(int mode) {
        int si = this.propertyTable.getSelectionIndex();
        TableItem sti = null;
        if (si >= 0) {
            if (mode == 0) {
                sti = this.propertyTable.getItem(si);
                try {
                    PropertyDialog pd = new PropertyDialog(this.getShell());
                    pd.setPName(sti.getText(0));
                    pd.setPValue(sti.getText(1));
                    pd.setPUpload(sti.getText(2).equals("Yes"));
                    pd.setPPassword(sti.getText(3).equals("Yes"));
                    pd.setPFixed(sti.getText(4).equals("Prohibited"));
                    pd.open();
                    if (pd.getReturnCode() == 0) {
                        sti.setText(0, pd.getPName());
                        sti.setText(1, pd.getPValue());
                        sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                        sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                        sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
                    }
                }
                catch (RuntimeException e) {
                    e.printStackTrace();
                    MessageDialog.openError((Shell)this.getShell(), (String)"Internal Error", (String)("Unable to create propert dialog: " + e.toString()));
                }
            } else {
                PropertyDialog pd = new PropertyDialog(this.getShell());
                pd.setPName("");
                pd.setPValue("");
                pd.setPUpload(false);
                pd.setPPassword(false);
                pd.setPFixed(false);
                pd.open();
                if (pd.getReturnCode() == 0) {
                    sti = mode == -1 ? new TableItem(this.propertyTable, 2048) : new TableItem(this.propertyTable, 2048, mode - 1);
                    sti.setText(0, pd.getPName());
                    sti.setText(1, pd.getPValue());
                    sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                    sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                    sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
                }
            }
        } else if (mode >= 0) {
            MessageDialog.openWarning((Shell)this.getShell(), (String)"Property operation", (String)"No Property Selected");
        } else {
            PropertyDialog pd = new PropertyDialog(this.getShell());
            pd.setPName("");
            pd.setPValue("");
            pd.setPUpload(false);
            pd.setPPassword(false);
            pd.setPFixed(false);
            pd.open();
            if (pd.getReturnCode() == 0) {
                sti = new TableItem(this.propertyTable, 2048);
                sti.setText(0, pd.getPName());
                sti.setText(1, pd.getPValue());
                sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
            }
        }
    }

    public Table getPropertyTable() {
        return this.propertyTable;
    }

    public Text getTransactionId() {
        return this.transactionId;
    }

    public boolean saveFlow(TransactionBase currentTransactionFlow) {
        TableItem[] pkit;
        if (!this.validate()) {
            return false;
        }
        if (currentTransactionFlow == null) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"No flow to save");
            return false;
        }
        currentTransactionFlow.setTransactionId(this.transactionId.getText().trim());
        currentTransactionFlow.setHostedPublic(this.hostedPublic.getSelection());
        currentTransactionFlow.setActive(this.activeFlow.getSelection());
        currentTransactionFlow.setPrimaryTransformationServerURL(this.primaryTsUrl.getText().trim());
        currentTransactionFlow.setSecondaryTransformationServerURL(this.secondaryTsUrl.getText().trim());
        Hashtable<String, TransactionBase.ParameterValue> partb = new Hashtable<String, TransactionBase.ParameterValue>();
        TableItem[] tableItemArray = pkit = this.propertyTable.getItems();
        int n = 0;
        int n2 = tableItemArray.length;
        while (n < n2) {
            TableItem cti = tableItemArray[n];
            String pnm = cti.getText(0);
            if (pnm.equals("QueryStartTime")) {
                currentTransactionFlow.setTransactionStartTime(Timestamp.valueOf(cti.getText(1)));
            } else {
                TransactionBase transactionBase = currentTransactionFlow;
                transactionBase.getClass();
                partb.put(pnm, transactionBase.new TransactionBase.ParameterValue(cti.getText(1), cti.getText(4).equals("Prohibited"), cti.getText(2).equals("Yes"), cti.getText(3).equals("Yes")));
            }
            ++n;
        }
        currentTransactionFlow.setParameters(partb);
        return true;
    }

    private boolean validate() {
        if (this.transactionId.getText().trim().length() == 0) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"Transaction Flow Id is empty");
            return false;
        }
        return true;
    }

    public Button getHostedPublic() {
        return this.hostedPublic;
    }

    public Button getActiveFlow() {
        return this.activeFlow;
    }

    public Text getPrimaryTsUrl() {
        return this.primaryTsUrl;
    }

    public Text getSecondaryTsUrl() {
        return this.secondaryTsUrl;
    }
}

