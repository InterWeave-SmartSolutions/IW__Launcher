/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards.pages;

import com.inerweave.sdk.composites.NewQueryWizardComposite;
import java.sql.Timestamp;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TableItem;

public class NewQueryMainPage
extends WizardPage {
    private NewQueryWizardComposite nqComposite;

    public NewQueryMainPage(String pageName) {
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
            this.nqComposite = new NewQueryWizardComposite(top, 0);
            this.nqComposite.setLayoutData(formData);
            TableItem newItemTableItem0 = new TableItem(this.nqComposite.getPropertyTable(), 2048);
            newItemTableItem0.setText(new String[]{"applicationname", "iwtransformationserver", "No", "No", "Prohibited"});
            TableItem newItemTableItem1 = new TableItem(this.nqComposite.getPropertyTable(), 2048);
            newItemTableItem1.setText(new String[]{"tranname", "", "No", "No", "Prohibited"});
            TableItem newItemTableItem2 = new TableItem(this.nqComposite.getPropertyTable(), 2048);
            newItemTableItem2.setText(new String[]{"QueryStartTime", new Timestamp(System.currentTimeMillis()).toString(), "No", "No", "Allowed"});
            TableItem newItemTableItem3 = new TableItem(this.nqComposite.getPropertyTable(), 2048);
            newItemTableItem3.setText(new String[]{"ReturnString", "", "No", "No", "Prohibited"});
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public NewQueryWizardComposite getNqComposite() {
        return this.nqComposite;
    }
}

