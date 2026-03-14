/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import org.eclipse.swt.custom.CLabel;
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
import org.eclipse.swt.widgets.Text;

public class NewConnectionWizardComposite
extends Composite {
    private Button passwordAsReference;
    private Text confirmPassword;
    private Text password;
    private Text refPassword;
    private Text user;
    private Text url;
    private Text driver;

    public NewConnectionWizardComposite(Composite parent, int style) {
        super(parent, style);
        NewConnectionWizardComposite ncwc = this;
        this.setBackground(Display.getCurrent().getSystemColor(1));
        this.setLayout((Layout)new FormLayout());
        CLabel nameLabel = new CLabel((Composite)this, 0);
        nameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData = new FormData();
        formData.top = new FormAttachment(14, 0);
        formData.left = new FormAttachment(0, 10);
        nameLabel.setLayoutData((Object)formData);
        nameLabel.setText("Driver/Type:");
        CLabel typeLabel = new CLabel((Composite)this, 0);
        typeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(28, 0);
        formData_1.left = new FormAttachment((Control)nameLabel, 0, 16384);
        typeLabel.setLayoutData((Object)formData_1);
        typeLabel.setText("URL:");
        CLabel adaptorClassNameLabel = new CLabel((Composite)this, 0);
        adaptorClassNameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(42, 0);
        formData_2.left = new FormAttachment((Control)typeLabel, 0, 16384);
        adaptorClassNameLabel.setLayoutData((Object)formData_2);
        adaptorClassNameLabel.setText("User:");
        CLabel finalTransformerLabel = new CLabel((Composite)this, 0);
        finalTransformerLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(56, 0);
        formData_3.left = new FormAttachment((Control)adaptorClassNameLabel, 0, 16384);
        finalTransformerLabel.setLayoutData((Object)formData_3);
        finalTransformerLabel.setText("Password:");
        CLabel finalTransformerLabel_1 = new CLabel((Composite)this, 0);
        FormData formData_3_1 = new FormData();
        formData_3_1.top = new FormAttachment(84, 0);
        formData_3_1.left = new FormAttachment((Control)finalTransformerLabel, 0, 16384);
        finalTransformerLabel_1.setLayoutData((Object)formData_3_1);
        finalTransformerLabel_1.setBackground(Display.getCurrent().getSystemColor(1));
        finalTransformerLabel_1.setText("Confirm Password:");
        this.driver = new Text((Composite)this, 2048);
        FormData formData_8 = new FormData();
        formData_8.right = new FormAttachment(95, 0);
        formData_8.top = new FormAttachment((Control)nameLabel, 0, 128);
        formData_8.left = new FormAttachment((Control)finalTransformerLabel_1, 5, 131072);
        this.driver.setLayoutData((Object)formData_8);
        this.url = new Text((Composite)this, 2048);
        FormData formData_4 = new FormData();
        formData_4.right = new FormAttachment((Control)this.driver, 0, 131072);
        formData_4.top = new FormAttachment((Control)typeLabel, 0, 128);
        formData_4.left = new FormAttachment((Control)this.driver, 0, 16384);
        this.url.setLayoutData((Object)formData_4);
        this.user = new Text((Composite)this, 2048);
        FormData formData_5 = new FormData();
        formData_5.right = new FormAttachment((Control)this.url, 0, 131072);
        formData_5.top = new FormAttachment((Control)adaptorClassNameLabel, 0, 128);
        formData_5.left = new FormAttachment((Control)this.url, 0, 16384);
        this.user.setLayoutData((Object)formData_5);
        this.password = new Text((Composite)this, 0x400800);
        this.password.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.right = new FormAttachment((Control)this.user, 0, 131072);
        formData_6.top = new FormAttachment((Control)finalTransformerLabel, 0, 128);
        formData_6.left = new FormAttachment((Control)this.user, 0, 16384);
        this.password.setLayoutData((Object)formData_6);
        this.refPassword = new Text((Composite)this, 2048);
        this.refPassword.setVisible(false);
        this.refPassword.setEnabled(false);
        this.refPassword.setBackground(Display.getCurrent().getSystemColor(1));
        this.refPassword.setLayoutData((Object)formData_6);
        this.confirmPassword = new Text((Composite)this, 0x400800);
        FormData formData_6_1 = new FormData();
        formData_6_1.right = new FormAttachment((Control)this.password, 0, 131072);
        formData_6_1.left = new FormAttachment((Control)finalTransformerLabel_1, 5, 131072);
        formData_6_1.bottom = new FormAttachment((Control)finalTransformerLabel_1, 19, 128);
        formData_6_1.top = new FormAttachment((Control)finalTransformerLabel_1, 0, 128);
        this.confirmPassword.setLayoutData((Object)formData_6_1);
        CLabel asReferenceLabel = new CLabel((Composite)this, 0);
        asReferenceLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_7 = new FormData();
        formData_7.top = new FormAttachment(70, 0);
        formData_7.left = new FormAttachment((Control)finalTransformerLabel, 0, 16384);
        asReferenceLabel.setLayoutData((Object)formData_7);
        asReferenceLabel.setText("As Reference:");
        this.passwordAsReference = new Button((Composite)this, 32);
        this.passwordAsReference.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                if (NewConnectionWizardComposite.this.passwordAsReference.getSelection()) {
                    NewConnectionWizardComposite.this.password.setVisible(false);
                    NewConnectionWizardComposite.this.password.setEnabled(false);
                    NewConnectionWizardComposite.this.refPassword.setVisible(true);
                    NewConnectionWizardComposite.this.refPassword.setEnabled(true);
                    NewConnectionWizardComposite.this.confirmPassword.setText("");
                    NewConnectionWizardComposite.this.confirmPassword.setEnabled(false);
                } else {
                    NewConnectionWizardComposite.this.refPassword.setVisible(false);
                    NewConnectionWizardComposite.this.refPassword.setEnabled(false);
                    NewConnectionWizardComposite.this.password.setVisible(true);
                    NewConnectionWizardComposite.this.password.setEnabled(true);
                    NewConnectionWizardComposite.this.confirmPassword.setEnabled(true);
                    NewConnectionWizardComposite.this.confirmPassword.setText("");
                }
            }
        });
        this.passwordAsReference.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9 = new FormData();
        formData_9.top = new FormAttachment((Control)asReferenceLabel, 0, 128);
        formData_9.left = new FormAttachment((Control)this.confirmPassword, 0, 16384);
        this.passwordAsReference.setLayoutData((Object)formData_9);
    }

    public void dispose() {
        super.dispose();
    }

    protected void checkSubclass() {
    }

    public final Text getConfirmPassword() {
        return this.confirmPassword;
    }

    public final Text getDriver() {
        return this.driver;
    }

    public final Text getPassword() {
        return this.password;
    }

    public final Text getUrl() {
        return this.url;
    }

    public final Text getUser() {
        return this.user;
    }

    public Button getPasswordAsReference() {
        return this.passwordAsReference;
    }

    public Text getRefPassword() {
        return this.refPassword;
    }
}

