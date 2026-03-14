/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards.pages;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.composites.NewTransactionWizardComposite;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class NewTransactionMainPage
extends WizardPage {
    Button sectionCheckbox = null;
    Button subsectionCheckbox = null;
    Button openFileCheckbox = null;
    private NewTransactionWizardComposite ntComposite;

    public NewTransactionMainPage(String pageName) {
        super(pageName);
    }

    public boolean finish() {
        return true;
    }

    public void createControl(Composite parent) {
        try {
            this.setControl((Control)parent);
            Composite top = (Composite)this.getControl();
            top.setLayout((Layout)new FormLayout());
            FormData formData = new FormData();
            formData.bottom = new FormAttachment(100, 0);
            formData.top = new FormAttachment(0, 0);
            formData.right = new FormAttachment(100, 0);
            formData.left = new FormAttachment(0, 0);
            this.ntComposite = new NewTransactionWizardComposite(top, 0, false);
            this.ntComposite.setLayoutData(formData);
            IProject cpr = Designer.getSelectedProject();
            if (cpr == null) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Transaction", (String)"No project selected");
                return;
            }
            String[] fti = ConfigContext.getXsltTransformers(cpr);
            this.ntComposite.getFinalTransformer().setItems(fti);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public NewTransactionWizardComposite getNtComposite() {
        return this.ntComposite;
    }
}

