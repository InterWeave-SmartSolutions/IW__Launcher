/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

public class NextTransactionComposite
extends Composite {
    private CCombo conditionalTransformer;
    private CCombo errorTransaction;
    private CCombo nextTransactionType;
    private CCombo nextTransaction;

    public NextTransactionComposite(Composite parent, int style) {
        super(parent, style);
        this.setLayout((Layout)new FormLayout());
        this.setBackground(Display.getCurrent().getSystemColor(1));
        CLabel nameLabel = new CLabel((Composite)this, 0);
        FormData formData_5 = new FormData();
        formData_5.top = new FormAttachment(20, 0);
        formData_5.left = new FormAttachment(0, 30);
        nameLabel.setLayoutData((Object)formData_5);
        nameLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        nameLabel.setText("Name:");
        this.nextTransaction = new CCombo((Composite)this, 2056);
        FormData formData_8_1 = new FormData();
        formData_8_1.top = new FormAttachment((Control)nameLabel, 0, 128);
        this.nextTransaction.setLayoutData((Object)formData_8_1);
        this.nextTransaction.setBackground(SWTResourceManager.getColor(255, 255, 255));
        CLabel typeLabel = new CLabel((Composite)this, 0);
        FormData formData_1_1 = new FormData();
        formData_1_1.top = new FormAttachment(40, 0);
        formData_1_1.left = new FormAttachment((Control)nameLabel, 0, 16384);
        typeLabel.setLayoutData((Object)formData_1_1);
        typeLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        typeLabel.setText("Type:");
        this.nextTransactionType = new CCombo((Composite)this, 2056);
        this.nextTransactionType.setItems(new String[]{"", "Unconditional", "Conditional"});
        FormData formData_9_1 = new FormData();
        formData_9_1.top = new FormAttachment((Control)typeLabel, 0, 128);
        formData_9_1.left = new FormAttachment((Control)this.nextTransaction, 0, 16384);
        this.nextTransactionType.setLayoutData((Object)formData_9_1);
        this.nextTransactionType.select(0);
        this.nextTransactionType.setEditable(false);
        this.nextTransactionType.setBackground(SWTResourceManager.getColor(255, 255, 255));
        CLabel errorTransactionLabel = new CLabel((Composite)this, 0);
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment(60, 0);
        formData_6.left = new FormAttachment((Control)nameLabel, 0, 16384);
        errorTransactionLabel.setLayoutData((Object)formData_6);
        errorTransactionLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        errorTransactionLabel.setText("Error Transaction");
        this.errorTransaction = new CCombo((Composite)this, 2056);
        FormData formData_12 = new FormData();
        formData_12.top = new FormAttachment((Control)errorTransactionLabel, 0, 128);
        formData_12.left = new FormAttachment((Control)this.nextTransaction, 0, 16384);
        this.errorTransaction.setLayoutData((Object)formData_12);
        this.errorTransaction.setBackground(SWTResourceManager.getColor(255, 255, 255));
        CLabel conditionalTransformerLabel = new CLabel((Composite)this, 0);
        formData_8_1.left = new FormAttachment((Control)conditionalTransformerLabel, 5, -1);
        FormData formData_7 = new FormData();
        formData_7.top = new FormAttachment(80, 0);
        formData_7.left = new FormAttachment((Control)nameLabel, 0, 16384);
        conditionalTransformerLabel.setLayoutData((Object)formData_7);
        conditionalTransformerLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        conditionalTransformerLabel.setText("Conditional Transformer:");
        this.conditionalTransformer = new CCombo((Composite)this, 2056);
        this.conditionalTransformer.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_11_1 = new FormData();
        formData_11_1.top = new FormAttachment((Control)conditionalTransformerLabel, 0, 128);
        formData_11_1.left = new FormAttachment((Control)this.nextTransaction, 0, 16384);
        this.conditionalTransformer.setLayoutData((Object)formData_11_1);
    }

    public void dispose() {
        super.dispose();
    }

    protected void checkSubclass() {
    }

    public CCombo getErrorTransaction() {
        return this.errorTransaction;
    }

    public CCombo getConditionalTransformer() {
        return this.conditionalTransformer;
    }

    public CCombo getNextTransaction() {
        return this.nextTransaction;
    }

    public CCombo getNextTransactionType() {
        return this.nextTransactionType;
    }
}

