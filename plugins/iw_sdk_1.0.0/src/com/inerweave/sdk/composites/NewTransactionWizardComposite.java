/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.vews.DataMapView;
import com.iwtransactions.transactionType;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class NewTransactionWizardComposite
extends Composite {
    private List dataMapList;
    private CCombo finalTransformer;
    private CCombo adaptorClassName;
    private CCombo transactionTypeCombo;
    private Text transactionName;
    private transactionType currentTransaction;

    public NewTransactionWizardComposite(Composite parent, int style, boolean view) {
        super(parent, style);
        this.init(view);
        this.currentTransaction = null;
    }

    private void init(boolean view) {
        this.setBackground(Display.getCurrent().getSystemColor(1));
        this.setLayout((Layout)new FormLayout());
        CLabel nameLabel = new CLabel((Composite)this, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData = new FormData();
        formData.top = new FormAttachment(16, 0);
        formData.left = new FormAttachment(0, 10);
        nameLabel.setLayoutData((Object)formData);
        nameLabel.setText("Name:");
        CLabel typeLabel = new CLabel((Composite)this, 0);
        typeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(32, 0);
        formData_1.left = new FormAttachment((Control)nameLabel, 0, 16384);
        typeLabel.setLayoutData((Object)formData_1);
        typeLabel.setText("Type:");
        CLabel adaptorClassNameLabel = new CLabel((Composite)this, 0);
        adaptorClassNameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(48, 0);
        formData_2.left = new FormAttachment((Control)typeLabel, 0, 16384);
        adaptorClassNameLabel.setLayoutData((Object)formData_2);
        adaptorClassNameLabel.setText("Adaptor Class Name:");
        CLabel finalTransformerLabel = new CLabel((Composite)this, 0);
        finalTransformerLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(64, 0);
        formData_3.left = new FormAttachment((Control)adaptorClassNameLabel, 0, 16384);
        finalTransformerLabel.setLayoutData((Object)formData_3);
        finalTransformerLabel.setText("Final Transformer:");
        this.transactionName = new Text((Composite)this, 2048);
        FormData formData_8 = new FormData();
        formData_8.right = new FormAttachment(50, 0);
        formData_8.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_8.left = new FormAttachment((Control)adaptorClassNameLabel, 5, 131072);
        this.transactionName.setLayoutData((Object)formData_8);
        this.transactionTypeCombo = new CCombo((Composite)this, 2056);
        this.transactionTypeCombo.setItems(new String[]{"", "Sequential", "Concurrent"});
        this.transactionTypeCombo.setEditable(false);
        this.transactionTypeCombo.setBackground(Display.getCurrent().getSystemColor(1));
        this.transactionTypeCombo.select(0);
        FormData formData_9 = new FormData();
        formData_9.top = new FormAttachment((Control)typeLabel, 0, 128);
        formData_9.left = new FormAttachment((Control)this.transactionName, 0, 16384);
        this.transactionTypeCombo.setLayoutData((Object)formData_9);
        this.adaptorClassName = new CCombo((Composite)this, 2056);
        this.adaptorClassName.setItems(new String[]{"", "com.interweave.adapter.api.IWLocalAPIBaseAdaptor", "com.interweave.adapter.database.IWSqlBase", "com.interweave.adapter.database.IWSqlSync", "com.interweave.adapter.http.IWHttpBaseAdaptor", "com.interweave.adapter.http.IWXMLBaseAdaptor", "com.interweave.adapter.http.IWXMLHierarchicalAdaptor", "com.interweave.adapter.http.IWJSonCookieAdaptor", "com.interweave.adapter.http.IWSoapBaseAdaptor", "com.interweave.adapter.http.IWSoapHierarchicalAdaptor", "com.interweave.adapter.http.IWJSonHierarchicalAdaptor", "com.interweave.adapter.email.IWEmailBaseAdaptor", "com.interweave.adapter.filesystem.IWFileBaseAdaptor", "com.interweave.adapter.filesystem.IWFtpBaseAdaptor", "com.interweave.adapter.socket.IWSocketBaseAdaptor", "com.interweave.adapter.IWStringAdaptor", "com.interweave.adapter.iwnative.IWNativeBaseAdaptor"});
        this.adaptorClassName.setEditable(false);
        this.adaptorClassName.select(0);
        this.adaptorClassName.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_10 = new FormData();
        formData_10.top = new FormAttachment((Control)adaptorClassNameLabel, 0, 128);
        formData_10.left = new FormAttachment((Control)this.transactionName, 0, 16384);
        this.adaptorClassName.setLayoutData((Object)formData_10);
        this.finalTransformer = new CCombo((Composite)this, 2056);
        this.finalTransformer.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_11 = new FormData();
        formData_11.right = new FormAttachment((Control)this.adaptorClassName, 0, 131072);
        formData_11.top = new FormAttachment((Control)finalTransformerLabel, 0, 128);
        formData_11.left = new FormAttachment((Control)this.adaptorClassName, 0, 16384);
        this.finalTransformer.setLayoutData((Object)formData_11);
        if (view) {
            CLabel dataMapsLabel = new CLabel((Composite)this, 0);
            dataMapsLabel.setBackground(Display.getCurrent().getSystemColor(1));
            FormData formData_4 = new FormData();
            formData_4.top = new FormAttachment(80, 0);
            formData_4.left = new FormAttachment((Control)finalTransformerLabel, 0, 16384);
            dataMapsLabel.setLayoutData((Object)formData_4);
            dataMapsLabel.setText("Data Maps:");
            this.dataMapList = new List((Composite)this, 2048);
            this.dataMapList.addMouseListener((MouseListener)new MouseAdapter(){

                public void mouseDoubleClick(MouseEvent e) {
                    NewTransactionWizardComposite.this.showDataMapView(0);
                }
            });
            FormData formData_5 = new FormData();
            formData_5.right = new FormAttachment((Control)this.finalTransformer, 0, 131072);
            formData_5.bottom = new FormAttachment(97, 0);
            formData_5.top = new FormAttachment((Control)dataMapsLabel, 0, 128);
            formData_5.left = new FormAttachment((Control)this.finalTransformer, 0, 16384);
            this.dataMapList.setLayoutData((Object)formData_5);
            Menu menu = new Menu((Control)this.dataMapList);
            this.dataMapList.setMenu(menu);
            MenuItem appendMenuItem = new MenuItem(menu, 0);
            appendMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

                public void widgetSelected(SelectionEvent e) {
                    NewTransactionWizardComposite.this.showDataMapView(-1);
                }
            });
            appendMenuItem.setText("New");
            MenuItem insertMenuItem = new MenuItem(menu, 0);
            insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

                public void widgetSelected(SelectionEvent e) {
                    int dms = NewTransactionWizardComposite.this.dataMapList.getSelectionIndex();
                    if (dms < 0) {
                        MessageDialog.openError((Shell)NewTransactionWizardComposite.this.getShell(), (String)"View Error", (String)"No DataMap selected to insert before it");
                    } else {
                        NewTransactionWizardComposite.this.showDataMapView(dms + 1);
                    }
                }
            });
            insertMenuItem.setText("Insert");
            MenuItem editMenuItem = new MenuItem(menu, 0);
            editMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

                public void widgetSelected(SelectionEvent e) {
                    NewTransactionWizardComposite.this.showDataMapView(0);
                }
            });
            editMenuItem.setText("Edit");
            new MenuItem(menu, 2);
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
                    NewTransactionWizardComposite.this.deleteDataMap();
                }
            });
            deleteMenuItem.setText("Delete");
        }
        Button showPreButton = new Button((Composite)this, 0);
        showPreButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                ConfigContext.openXsltEditor(NewTransactionWizardComposite.this.finalTransformer);
            }
        });
        showPreButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                ConfigContext.openXsltEditor(NewTransactionWizardComposite.this.finalTransformer);
            }
        });
        showPreButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/changelog_obj.gif"));
        FormData formData_25 = new FormData();
        formData_25.bottom = new FormAttachment((Control)this.finalTransformer, 19, 128);
        formData_25.top = new FormAttachment((Control)this.finalTransformer, 0, 128);
        formData_25.left = new FormAttachment((Control)this.finalTransformer, 5, 131072);
        showPreButton.setLayoutData((Object)formData_25);
        showPreButton.setBackground(Display.getCurrent().getSystemColor(1));
    }

    public final CCombo getAdaptorClassName() {
        return this.adaptorClassName;
    }

    public final List getDataMapList() {
        return this.dataMapList;
    }

    public final CCombo getFinalTransformer() {
        return this.finalTransformer;
    }

    public final Text getTransactionName() {
        return this.transactionName;
    }

    public final CCombo getTransactionTypeCombo() {
        return this.transactionTypeCombo;
    }

    private void showDataMapView(int add) {
        if (add == 0) {
            int dmsi = this.dataMapList.getSelectionIndex();
            if (dmsi == -1) {
                MessageDialog.openError((Shell)this.getShell(), (String)"View Error", (String)"No DataMap selected");
                return;
            }
            ConfigContext.setCurrentDataMapName(this.dataMapList.getItem(dmsi));
        }
        try {
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            ap.showView("iw_sdk.DataMapView");
            ((DataMapView)ap.findView("iw_sdk.DataMapView")).initializeScreen(add);
        }
        catch (PartInitException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Plugin Error", (String)("Unable to show DataMap Flow view " + e.toString()));
            return;
        }
    }

    private void deleteDataMap() {
        int dmsi = this.dataMapList.getSelectionIndex();
        if (dmsi == -1) {
            MessageDialog.openError((Shell)this.getShell(), (String)"View Error", (String)"No DataMap selected");
            return;
        }
        ConfigContext.setCurrentDataMapName(this.dataMapList.getItem(dmsi));
        if (this.currentTransaction == null) {
            MessageDialog.openError((Shell)this.getShell(), (String)"View Error", (String)"No Transaction selected to delete DataMap");
            return;
        }
        this.currentTransaction.removedatamapAt(dmsi);
    }

    public boolean saveTransaction(transactionType currentTransaction) {
        try {
            currentTransaction.replacenameAt(this.transactionName.getText().trim(), 0);
            int ts = this.transactionTypeCombo.getSelectionIndex();
            if (ts > 0) {
                currentTransaction.replacetypeAt(this.transactionTypeCombo.getItem(ts).toLowerCase(), 0);
            } else {
                currentTransaction.replacetypeAt("", 0);
            }
            ts = this.finalTransformer.getSelectionIndex();
            String acn = "";
            if (ts >= 0) {
                acn = this.finalTransformer.getItem(ts).trim();
            }
            if (currentTransaction.gettransformCount() > 0) {
                currentTransaction.replacetransformAt(acn, 0);
            } else {
                currentTransaction.addtransform(acn);
            }
            ts = this.adaptorClassName.getSelectionIndex();
            acn = "";
            if (ts >= 0) {
                acn = this.adaptorClassName.getItem(ts).trim();
            }
            if (currentTransaction.getclassnameCount() > 0) {
                if (acn.length() > 0) {
                    currentTransaction.replaceclassnameAt(acn, 0);
                } else {
                    currentTransaction.removeclassname();
                }
            } else if (acn.length() > 0) {
                currentTransaction.addclassname(acn);
            }
            return true;
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transactions Details", (String)("Unable to save transaction details: " + e.toString()));
            return false;
        }
    }

    public void setCurrentTransaction(transactionType currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
}

