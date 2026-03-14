/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
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

public class PropertyDialog
extends Dialog {
    private Button passwordButton;
    private Button fileToUploadButton;
    private Button fixedButton;
    private Text propertyValue;
    private Text propertyName;
    private boolean pFixed;
    private boolean pUpload;
    private boolean pPassword;
    private String pValue;
    private String pName;
    private CCombo trPropertyValue;

    public PropertyDialog(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite)super.createDialogArea(parent);
        container.setLayout((Layout)new FormLayout());
        container.setBackground(Display.getCurrent().getSystemColor(1));
        CLabel propertyNameLabel = new CLabel(container, 0);
        propertyNameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData = new FormData();
        formData.top = new FormAttachment(20, 0);
        formData.left = new FormAttachment(0, 20);
        propertyNameLabel.setLayoutData((Object)formData);
        propertyNameLabel.setText("Property Name:");
        CLabel propertyValueLabel = new CLabel(container, 0);
        propertyValueLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(45, 0);
        formData_1.left = new FormAttachment((Control)propertyNameLabel, 0, 16384);
        propertyValueLabel.setLayoutData((Object)formData_1);
        propertyValueLabel.setText("Property Value:");
        this.propertyName = new Text(container, 2048);
        this.propertyName.addFocusListener((FocusListener)new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                PropertyDialog.this.pName = PropertyDialog.this.propertyName.getText().trim();
            }
        });
        this.propertyName.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.right = new FormAttachment(80, 0);
        formData_2.top = new FormAttachment((Control)propertyNameLabel, 0, 128);
        formData_2.left = new FormAttachment((Control)propertyNameLabel, 5, 131072);
        this.propertyName.setLayoutData((Object)formData_2);
        if (this.pName == null) {
            this.propertyName.setText("");
        } else {
            this.propertyName.setText(this.pName);
        }
        FormData formData_3 = new FormData();
        formData_3.left = new FormAttachment((Control)propertyNameLabel, 5, -1);
        formData_3.right = new FormAttachment((Control)this.propertyName, 0, 131072);
        formData_3.top = new FormAttachment((Control)propertyValueLabel, 0, 128);
        if (this.pName.equals("tranname")) {
            this.trPropertyValue = new CCombo(container, 2056);
            this.trPropertyValue.addFocusListener((FocusListener)new FocusAdapter(){

                public void focusLost(FocusEvent e) {
                    PropertyDialog.this.pValue = PropertyDialog.this.trPropertyValue.getItem(PropertyDialog.this.trPropertyValue.getSelectionIndex());
                }
            });
            this.trPropertyValue.setItems(ConfigContext.getTransactions(Designer.getSelectedProject(), true));
            try {
                this.trPropertyValue.select(ConfigContext.getComboIndex(this.trPropertyValue.getItems(), this.pValue));
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            this.trPropertyValue.setBackground(Display.getCurrent().getSystemColor(1));
            this.trPropertyValue.setLayoutData((Object)formData_3);
        } else {
            this.propertyValue = new Text(container, 2048);
            this.propertyValue.addFocusListener((FocusListener)new FocusAdapter(){

                public void focusLost(FocusEvent e) {
                    PropertyDialog.this.pValue = PropertyDialog.this.propertyValue.getText().trim();
                }
            });
            this.propertyValue.setBackground(Display.getCurrent().getSystemColor(1));
            this.propertyValue.setLayoutData((Object)formData_3);
            this.propertyValue.setText(this.pValue);
        }
        this.fixedButton = new Button(container, 32);
        this.fixedButton.addFocusListener((FocusListener)new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                PropertyDialog.this.pFixed = PropertyDialog.this.fixedButton.getSelection();
            }
        });
        this.fixedButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4 = new FormData();
        formData_4.left = new FormAttachment((Control)this.propertyName, 0, 16384);
        formData_4.top = new FormAttachment(70, 0);
        this.fixedButton.setLayoutData((Object)formData_4);
        this.fixedButton.setText("Fixed");
        this.fixedButton.setSelection(this.pFixed);
        this.fileToUploadButton = new Button(container, 32);
        this.fileToUploadButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                PropertyDialog.this.passwordButton.setEnabled(!PropertyDialog.this.fileToUploadButton.getSelection());
            }
        });
        this.fileToUploadButton.addFocusListener((FocusListener)new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                PropertyDialog.this.pUpload = PropertyDialog.this.fileToUploadButton.getSelection();
            }
        });
        this.fileToUploadButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_5 = new FormData();
        formData_5.top = new FormAttachment((Control)this.fixedButton, 0, 128);
        formData_5.left = new FormAttachment((Control)this.fixedButton, 5, 131072);
        this.fileToUploadButton.setLayoutData((Object)formData_5);
        this.fileToUploadButton.setText("File to Upload");
        this.fileToUploadButton.setSelection(this.pUpload);
        this.passwordButton = new Button(container, 32);
        this.passwordButton.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                PropertyDialog.this.fileToUploadButton.setEnabled(!PropertyDialog.this.passwordButton.getSelection());
            }
        });
        this.passwordButton.addFocusListener((FocusListener)new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                PropertyDialog.this.pPassword = PropertyDialog.this.passwordButton.getSelection();
            }
        });
        this.passwordButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment((Control)this.fileToUploadButton, 0, 128);
        formData_6.left = new FormAttachment((Control)this.fileToUploadButton, 5, 131072);
        this.passwordButton.setLayoutData((Object)formData_6);
        this.passwordButton.setText("Password");
        this.passwordButton.setSelection(this.pPassword);
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        this.createButton(parent, 0, IDialogConstants.OK_LABEL, true);
        this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 221);
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/properties.gif"));
        newShell.setText("Property Details");
    }

    public final Text getPropertyName() {
        return this.propertyName;
    }

    public final Text getPropertyValue() {
        return this.propertyValue;
    }

    public final Button getFixedButton() {
        return this.fixedButton;
    }

    public boolean isPFixed() {
        return this.pFixed;
    }

    public String getPName() {
        return this.pName;
    }

    public String getPValue() {
        return this.pValue;
    }

    public void setPValue(String value) {
        this.pValue = value;
    }

    public void setPFixed(boolean fixed) {
        this.pFixed = fixed;
    }

    public void setPName(String name) {
        this.pName = name;
    }

    public boolean isPPassword() {
        return this.pPassword;
    }

    public void setPPassword(boolean password) {
        this.pPassword = password;
    }

    public boolean isPUpload() {
        return this.pUpload;
    }

    public void setPUpload(boolean upload) {
        this.pUpload = upload;
    }

    public CCombo getTrPropertyValue() {
        return this.trPropertyValue;
    }

    public void setTrPropertyValue(CCombo trPropertyValue) {
        this.trPropertyValue = trPropertyValue;
    }
}

