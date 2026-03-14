/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.inerweave.sdk.Designer;
import java.io.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewProjectComposite
extends Composite {
    private Label totalFlowsValue;
    private Label totalTransactionsValue;
    private Button hostedSolution;
    private Text tsDeploymentURI;
    private List transformerList;
    private Button blackBoxSolution;
    private Text imDeploymentURI;
    private Text projectName;
    public final QualifiedName imURLName = new QualifiedName("iw_sdk", "IM_DEPLOYMENT_URL");
    public final QualifiedName tsURLName = new QualifiedName("iw_sdk", "TS_DEPLOYMENT_URL");

    public NewProjectComposite(Composite parent, int style, boolean newProject) {
        super(parent, style);
        int line0 = newProject ? 0 : 5;
        int line1 = newProject ? 10 : 25;
        int line2 = newProject ? 25 : 50;
        int line3 = newProject ? 42 : 75;
        this.setLayout((Layout)new FormLayout());
        Label deploymentTargetLabel = new Label((Composite)this, 0);
        FormData formData = new FormData();
        formData.top = new FormAttachment(line1, 0);
        formData.left = new FormAttachment(0, 9);
        deploymentTargetLabel.setLayoutData((Object)formData);
        deploymentTargetLabel.setText("IM Deployment Target:");
        this.imDeploymentURI = new Text((Composite)this, 2048);
        this.imDeploymentURI.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.right = new FormAttachment(95, 0);
        formData_2.top = new FormAttachment((Control)deploymentTargetLabel, 0, 128);
        formData_2.left = new FormAttachment((Control)deploymentTargetLabel, 5, 131072);
        this.imDeploymentURI.setLayoutData((Object)formData_2);
        if (!newProject) {
            Label projectNameLabel = new Label((Composite)this, 0);
            FormData formData_6_1 = new FormData();
            formData_6_1.top = new FormAttachment(line0, 0);
            formData_6_1.left = new FormAttachment((Control)deploymentTargetLabel, 0, 16384);
            projectNameLabel.setLayoutData((Object)formData_6_1);
            projectNameLabel.setText("Name:");
            this.projectName = new Text((Composite)this, 2048);
            this.projectName.setBackground(Display.getCurrent().getSystemColor(1));
            FormData formData_7_1 = new FormData();
            formData_7_1.right = new FormAttachment((Control)this.imDeploymentURI, 0, 131072);
            formData_7_1.top = new FormAttachment((Control)projectNameLabel, 0, 128);
            formData_7_1.left = new FormAttachment((Control)projectNameLabel, 5, 131072);
            this.projectName.setLayoutData((Object)formData_7_1);
            Label totalTransactions = new Label((Composite)this, 0);
            FormData formData_1tt = new FormData();
            formData_1tt.top = new FormAttachment(90, 0);
            formData_1tt.left = new FormAttachment((Control)projectNameLabel, 0, 16384);
            totalTransactions.setLayoutData((Object)formData_1tt);
            totalTransactions.setText("Total Transactions:");
            this.totalTransactionsValue = new Label((Composite)this, 0);
            FormData formData_1ttv = new FormData();
            formData_1ttv.top = new FormAttachment(90, 0);
            formData_1ttv.left = new FormAttachment((Control)totalTransactions, 5, 131072);
            this.totalTransactionsValue.setLayoutData((Object)formData_1ttv);
            this.totalTransactionsValue.setText("000000");
            Label totalFlows = new Label((Composite)this, 0);
            FormData formData_1tf = new FormData();
            formData_1tf.top = new FormAttachment(90, 0);
            formData_1tf.left = new FormAttachment((Control)this.totalTransactionsValue, 40, 131072);
            totalFlows.setLayoutData((Object)formData_1tf);
            totalFlows.setText("Total Flows:");
            this.totalFlowsValue = new Label((Composite)this, 0);
            FormData formData_1tfv = new FormData();
            formData_1tfv.top = new FormAttachment(90, 0);
            formData_1tfv.left = new FormAttachment((Control)totalFlows, 5, 131072);
            this.totalFlowsValue.setLayoutData((Object)formData_1tfv);
            this.totalFlowsValue.setText("000000");
            Label os = new Label((Composite)this, 0);
            FormData formData_1os = new FormData();
            formData_1os.top = new FormAttachment(90, 0);
            formData_1os.left = new FormAttachment((Control)this.totalFlowsValue, 40, 131072);
            os.setLayoutData((Object)formData_1os);
            os.setText(System.getProperty("os.name"));
        }
        Label tsDeploymentTargetLabel = new Label((Composite)this, 0);
        FormData formData_6 = new FormData();
        formData_6.top = new FormAttachment(line2, 0);
        formData_6.left = new FormAttachment((Control)deploymentTargetLabel, 0, 16384);
        tsDeploymentTargetLabel.setLayoutData((Object)formData_6);
        tsDeploymentTargetLabel.setText("TS Deployment Target:");
        this.tsDeploymentURI = new Text((Composite)this, 2048);
        this.tsDeploymentURI.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_7 = new FormData();
        formData_7.right = new FormAttachment((Control)this.imDeploymentURI, 0, 131072);
        formData_7.top = new FormAttachment((Control)tsDeploymentTargetLabel, 0, 128);
        formData_7.left = new FormAttachment((Control)tsDeploymentTargetLabel, 5, 131072);
        this.tsDeploymentURI.setLayoutData((Object)formData_7);
        Label blackBoxLabel = new Label((Composite)this, 0);
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(line3, 0);
        formData_1.left = new FormAttachment((Control)tsDeploymentTargetLabel, 0, 16384);
        blackBoxLabel.setLayoutData((Object)formData_1);
        blackBoxLabel.setText("Production Package:");
        this.blackBoxSolution = new Button((Composite)this, 32);
        this.blackBoxSolution.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment((Control)blackBoxLabel, 0, 128);
        formData_3.left = new FormAttachment((Control)this.tsDeploymentURI, 0, 16384);
        this.blackBoxSolution.setLayoutData((Object)formData_3);
        CLabel hostedLabel = new CLabel((Composite)this, 0);
        FormData formData_9 = new FormData();
        hostedLabel.setLayoutData((Object)formData_9);
        hostedLabel.setText("Hosted Solution:");
        this.hostedSolution = new Button((Composite)this, 32);
        formData_9.right = new FormAttachment((Control)this.hostedSolution, -20, 16384);
        formData_9.top = new FormAttachment((Control)this.hostedSolution, 0, 128);
        FormData formData_10 = new FormData();
        formData_10.right = new FormAttachment((Control)this.tsDeploymentURI, 0, 131072);
        formData_10.top = new FormAttachment((Control)blackBoxLabel, 0, 128);
        this.hostedSolution.setLayoutData((Object)formData_10);
        if (newProject) {
            Button addTransformerButton = new Button((Composite)this, 0);
            addTransformerButton.addKeyListener((KeyListener)new KeyAdapter(){

                public void keyPressed(KeyEvent e) {
                    NewProjectComposite.this.addTransformers();
                }
            });
            addTransformerButton.addMouseListener((MouseListener)new MouseAdapter(){

                public void mouseDown(MouseEvent e) {
                    NewProjectComposite.this.addTransformers();
                }
            });
            FormData formData_4 = new FormData();
            formData_4.left = new FormAttachment((Control)blackBoxLabel, 0, 16384);
            addTransformerButton.setLayoutData((Object)formData_4);
            addTransformerButton.setText("Add Transformer");
            this.transformerList = new List((Composite)this, 2818);
            formData_4.top = new FormAttachment((Control)this.transformerList, 0, 128);
            FormData formData_5 = new FormData();
            formData_5.right = new FormAttachment((Control)this.imDeploymentURI, 0, 131072);
            formData_5.bottom = new FormAttachment(95, 0);
            formData_5.top = new FormAttachment(60, 0);
            formData_5.left = new FormAttachment((Control)this.tsDeploymentURI, 0, 16384);
            this.transformerList.setLayoutData((Object)formData_5);
            Button deleteTransformerButton = new Button((Composite)this, 0);
            deleteTransformerButton.addMouseListener((MouseListener)new MouseAdapter(){

                public void mouseDown(MouseEvent e) {
                    NewProjectComposite.this.deleteTransformers();
                }
            });
            deleteTransformerButton.addKeyListener((KeyListener)new KeyAdapter(){

                public void keyPressed(KeyEvent e) {
                    NewProjectComposite.this.deleteTransformers();
                }
            });
            FormData formData_8 = new FormData();
            formData_8.left = new FormAttachment((Control)blackBoxLabel, 0, 16384);
            formData_8.bottom = new FormAttachment((Control)this.transformerList, 0, 1024);
            deleteTransformerButton.setLayoutData((Object)formData_8);
            deleteTransformerButton.setText("Delete Transformer");
        }
    }

    public Button getBlackBoxSolution() {
        return this.blackBoxSolution;
    }

    public Text getImDeploymentURI() {
        return this.imDeploymentURI;
    }

    public List getTransformerList() {
        return this.transformerList;
    }

    public Text getTsDeploymentURI() {
        return this.tsDeploymentURI;
    }

    private void addTransformers() {
        FileDialog fd = new FileDialog(this.getShell(), 4098);
        fd.setFilterExtensions(new String[]{"*.xslt"});
        if (fd.open() != null) {
            String[] oldX = this.transformerList.getItems();
            String[] newF = fd.getFileNames();
            int i = 0;
            while (i < newF.length) {
                newF[i] = String.valueOf(fd.getFilterPath()) + File.separator + newF[i];
                ++i;
            }
            String[] newX = new String[oldX.length + newF.length];
            System.arraycopy(oldX, 0, newX, 0, oldX.length);
            System.arraycopy(newF, 0, newX, oldX.length, newF.length);
            this.transformerList.setItems(newX);
        }
    }

    private void deleteTransformers() {
        String[] stt;
        String[] stringArray = stt = this.transformerList.getSelection();
        int n = 0;
        int n2 = stringArray.length;
        while (n < n2) {
            String cs = stringArray[n];
            this.transformerList.remove(cs);
            ++n;
        }
    }

    public Button getHostedSolution() {
        return this.hostedSolution;
    }

    public void saveDeploymentURL() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)"No project selected to save deployment URLs.");
            return;
        }
        String imURL = this.imDeploymentURI.getText().trim();
        String tsURL = this.tsDeploymentURI.getText().trim();
        try {
            if (imURL.length() == 0) {
                if (cpr.getPersistentProperty(this.imURLName) != null) {
                    cpr.setPersistentProperty(this.imURLName, null);
                }
            } else {
                cpr.setPersistentProperty(this.imURLName, imURL);
            }
            if (tsURL.length() == 0) {
                if (cpr.getPersistentProperty(this.tsURLName) != null) {
                    cpr.setPersistentProperty(this.tsURLName, null);
                }
            } else {
                cpr.setPersistentProperty(this.tsURLName, tsURL);
            }
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Properties", (String)("Unable to save deployment URLs: " + e.toString()));
        }
    }

    public Text getProjectName() {
        return this.projectName;
    }

    public Label getTotalFlowsValue() {
        return this.totalFlowsValue;
    }

    public Label getTotalTransactionsValue() {
        return this.totalTransactionsValue;
    }
}

