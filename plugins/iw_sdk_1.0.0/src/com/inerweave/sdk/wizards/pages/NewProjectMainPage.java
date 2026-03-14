/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards.pages;

import com.inerweave.sdk.composites.NewProjectComposite;
import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewProjectMainPage
extends WizardNewProjectCreationPage {
    NewProjectComposite npc;
    private String tsDeploymentURIValue;
    private String[] transformerListValue;
    private boolean blackBoxSolutionValue;
    private boolean hostedSolutionValue;
    private String imDeploymentURIValue;

    public NewProjectMainPage(String pageName) {
        super(pageName);
    }

    public boolean finish() {
        if (!this.validatePage()) {
            return false;
        }
        try {
            this.imDeploymentURIValue = this.npc.getImDeploymentURI().getText().trim();
            if (this.imDeploymentURIValue.length() > 0) {
                try {
                    new URL(this.imDeploymentURIValue);
                }
                catch (MalformedURLException e) {
                    MessageDialog.openError((Shell)this.getShell(), (String)"Deployment URI Error", (String)"Malformed IM Deployment URI");
                    return false;
                }
            }
            this.tsDeploymentURIValue = this.npc.getTsDeploymentURI().getText().trim();
            if (this.tsDeploymentURIValue.length() > 0) {
                try {
                    new URL(this.tsDeploymentURIValue);
                }
                catch (MalformedURLException e) {
                    MessageDialog.openError((Shell)this.getShell(), (String)"Deployment URI Error", (String)"Malformed TS Deployment URI");
                    return false;
                }
            }
            this.transformerListValue = this.npc.getTransformerList().getItems();
            this.blackBoxSolutionValue = this.npc.getBlackBoxSolution().getSelection();
            this.hostedSolutionValue = this.npc.getHostedSolution().getSelection();
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Main Page", (String)("Run time error: " + e.getStackTrace()[0].toString() + e.getStackTrace()[1].toString()));
            return false;
        }
        return true;
    }

    public void createControl(Composite parent) {
        super.createControl(parent);
        this.npc = new NewProjectComposite((Composite)this.getControl(), 0, true);
        this.npc.setLayoutData(new GridData(4, 4, true, true));
    }

    protected boolean validatePage() {
        return super.validatePage();
    }

    public boolean isBlackBoxSolutionValue() {
        return this.blackBoxSolutionValue;
    }

    public String getImDeploymentURIValue() {
        return this.imDeploymentURIValue;
    }

    public String[] getTransformerListValue() {
        return this.transformerListValue;
    }

    public String getTsDeploymentURIValue() {
        return this.tsDeploymentURIValue;
    }

    public boolean isHostedSolutionValue() {
        return this.hostedSolutionValue;
    }

    public void saveDeploymentURL() {
        this.npc.saveDeploymentURL();
    }
}

