/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.composites.NextTransactionComposite;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

public class NextTransactionDialog
extends Dialog {
    private NextTransactionComposite nextTransactionComposite;
    private String conditionalTransformerName = null;
    private String errorTransactionName = null;
    private String nextTransactionTypeValue = null;
    private String nextTransactionName = null;
    private String[] transactions = null;
    private String[] transformers = null;

    public NextTransactionDialog(Shell parentShell) {
        super(parentShell);
    }

    public String getErrorTransactionName() {
        return this.errorTransactionName;
    }

    public void setErrorTransactionName(String errorTransactionName) {
        this.errorTransactionName = errorTransactionName;
    }

    public String getConditionalTransformerName() {
        return this.conditionalTransformerName;
    }

    public void setConditionalTransformerName(String finalTransformerName) {
        this.conditionalTransformerName = finalTransformerName;
    }

    public String getNextTransactionName() {
        return this.nextTransactionName;
    }

    public void setNextTransactionName(String nextTransactionName) {
        this.nextTransactionName = nextTransactionName;
    }

    public String getNextTransactionTypeValue() {
        return this.nextTransactionTypeValue;
    }

    public void setNextTransactionTypeValue(String nextTransactionTypeValue) {
        this.nextTransactionTypeValue = nextTransactionTypeValue;
    }

    protected Control createDialogArea(Composite parent) {
        CCombo cc;
        Composite container = (Composite)super.createDialogArea(parent);
        container.setLayout((Layout)new FormLayout());
        container.setBackground(Display.getCurrent().getSystemColor(1));
        this.nextTransactionComposite = new NextTransactionComposite(container, 0);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        formData.top = new FormAttachment(0, 0);
        this.nextTransactionComposite.setLayoutData(formData);
        if (this.nextTransactionTypeValue != null) {
            cc = this.nextTransactionComposite.getNextTransactionType();
            try {
                cc.select(ConfigContext.getComboIndex(cc.getItems(), this.nextTransactionTypeValue));
            }
            catch (Exception e1) {
                cc.select(0);
            }
        }
        cc = this.nextTransactionComposite.getNextTransaction();
        if (this.nextTransactionName != null && this.transactions != null) {
            cc.setItems(this.transactions);
            try {
                cc.select(ConfigContext.getComboIndex(cc.getItems(), this.nextTransactionName));
            }
            catch (Exception e1) {
                cc.select(0);
            }
        } else {
            if (this.transactions != null) {
                cc.setItems(this.transactions);
            } else {
                cc.setItems(new String[]{""});
            }
            cc.select(0);
        }
        cc = this.nextTransactionComposite.getErrorTransaction();
        if (this.errorTransactionName != null && this.transactions != null) {
            cc.setItems(this.transactions);
            try {
                cc.select(ConfigContext.getComboIndex(cc.getItems(), this.errorTransactionName));
            }
            catch (Exception e1) {
                cc.select(0);
            }
        } else {
            if (this.transactions != null) {
                cc.setItems(this.transactions);
            } else {
                cc.setItems(new String[]{""});
            }
            cc.select(0);
        }
        cc = this.nextTransactionComposite.getConditionalTransformer();
        if (this.conditionalTransformerName != null && this.transformers != null) {
            cc.setItems(this.transformers);
            try {
                cc.select(ConfigContext.getComboIndex(cc.getItems(), this.conditionalTransformerName));
            }
            catch (Exception e1) {
                cc.select(0);
            }
        } else {
            if (this.transformers != null) {
                cc.setItems(this.transformers);
            } else {
                cc.setItems(new String[]{""});
            }
            cc.select(0);
        }
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        Button button = this.createButton(parent, 0, IDialogConstants.OK_LABEL, true);
        button.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                NextTransactionDialog.this.okProcess();
            }
        });
        button.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                NextTransactionDialog.this.okProcess();
            }
        });
        this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Next Transaction");
        newShell.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/next.gif"));
    }

    public void setTransactions(String[] transactions) {
        this.transactions = transactions;
    }

    public void setTransformers(String[] transformers) {
        this.transformers = transformers;
    }

    public void okProcess() {
        int ws = this.nextTransactionComposite.getNextTransactionType().getSelectionIndex();
        this.nextTransactionTypeValue = ws < 0 ? "" : this.nextTransactionComposite.getNextTransactionType().getItem(ws);
        ws = this.nextTransactionComposite.getNextTransaction().getSelectionIndex();
        this.nextTransactionName = ws < 0 ? "" : this.nextTransactionComposite.getNextTransaction().getItem(ws);
        ws = this.nextTransactionComposite.getConditionalTransformer().getSelectionIndex();
        this.conditionalTransformerName = ws < 0 ? "" : this.nextTransactionComposite.getConditionalTransformer().getItem(ws);
        ws = this.nextTransactionComposite.getErrorTransaction().getSelectionIndex();
        this.errorTransactionName = ws < 0 ? "" : this.nextTransactionComposite.getErrorTransaction().getItem(ws);
    }
}

