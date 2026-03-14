/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards.pages;

import com.inerweave.sdk.composites.NewTransactionFlowWizardComposite;
import java.sql.Timestamp;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TableItem;

public class NewTransactionFlowMainPage
extends WizardPage {
    private NewTransactionFlowWizardComposite ntComposite;

    public NewTransactionFlowMainPage(String pageName) {
        super(pageName);
    }

    public boolean finish() {
        return true;
    }

    public void createControl(Composite parent) {
        try {
            super.setControl((Control)parent);
            Composite top = (Composite)this.getControl();
            top.setLayout((Layout)new FormLayout());
            FormData formData = new FormData();
            formData.bottom = new FormAttachment(100, 0);
            formData.top = new FormAttachment(0, 0);
            formData.right = new FormAttachment(100, 0);
            formData.left = new FormAttachment(0, 0);
            this.ntComposite = new NewTransactionFlowWizardComposite(top, 0);
            this.ntComposite.setLayoutData(formData);
            TableItem newItemTableItem0 = new TableItem(this.ntComposite.getPropertyTable(), 2048);
            newItemTableItem0.setText(new String[]{"applicationname", "iwtransformationserver", "No", "No", "Prohibited"});
            TableItem newItemTableItem1 = new TableItem(this.ntComposite.getPropertyTable(), 2048);
            newItemTableItem1.setText(new String[]{"tranname", "", "No", "No", "Prohibited"});
            TableItem newItemTableItem2 = new TableItem(this.ntComposite.getPropertyTable(), 2048);
            newItemTableItem2.setText(new String[]{"QueryStartTime", new Timestamp(System.currentTimeMillis()).toString(), "No", "No", "Allowed"});
            TableItem newItemTableItem3 = new TableItem(this.ntComposite.getPropertyTable(), 2048);
            newItemTableItem3.setText(new String[]{"ReturnString", "", "No", "No", "Prohibited"});
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public NewTransactionFlowWizardComposite getNtComposite() {
        return this.ntComposite;
    }
}

