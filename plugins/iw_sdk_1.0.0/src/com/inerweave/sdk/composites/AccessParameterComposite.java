/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.swtdesigner.SWTResourceManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.custom.CCombo;
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

public class AccessParameterComposite
extends Composite {
    private Button quoted;
    private Text mappingValue;
    private CCombo mappingTypeCombo;
    private String selectedMappingType = null;
    private int javaTypeIndex = -1;
    private Text inputName;
    private CCombo preTransformerCombo;
    private CCombo postTransformerCombo;

    public AccessParameterComposite(Composite parent, int style) {
        super(parent, style);
        this.setBackground(Display.getCurrent().getSystemColor(1));
        this.setLayout((Layout)new FormLayout());
        CLabel inputNameLabel = new CLabel((Composite)this, 0);
        inputNameLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData = new FormData();
        formData.top = new FormAttachment(14, 0);
        formData.left = new FormAttachment(0, 25);
        inputNameLabel.setLayoutData((Object)formData);
        inputNameLabel.setText("Input Name:");
        CLabel mappingTypeLabel = new CLabel((Composite)this, 0);
        mappingTypeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(28, 0);
        formData_1.left = new FormAttachment((Control)inputNameLabel, 0, 16384);
        mappingTypeLabel.setLayoutData((Object)formData_1);
        mappingTypeLabel.setText("Mapping Type:");
        CLabel mappimgValueLabel = new CLabel((Composite)this, 0);
        mappimgValueLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(42, 0);
        formData_2.left = new FormAttachment((Control)mappingTypeLabel, 0, 16384);
        mappimgValueLabel.setLayoutData((Object)formData_2);
        mappimgValueLabel.setText("Mapping Value:");
        CLabel quotedLabel = new CLabel((Composite)this, 0);
        quotedLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3 = new FormData();
        formData_3.top = new FormAttachment(56, 0);
        formData_3.left = new FormAttachment((Control)mappimgValueLabel, 0, 16384);
        quotedLabel.setLayoutData((Object)formData_3);
        quotedLabel.setText("Quoted:");
        CLabel preconvertorLabel = new CLabel((Composite)this, 0);
        FormData formData_7 = new FormData();
        formData_7.top = new FormAttachment(70, 0);
        formData_7.left = new FormAttachment((Control)quotedLabel, 0, 16384);
        preconvertorLabel.setLayoutData((Object)formData_7);
        preconvertorLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        preconvertorLabel.setText("Pre-Transformer:");
        CLabel postconvertorLabel = new CLabel((Composite)this, 0);
        FormData formData_8 = new FormData();
        formData_8.top = new FormAttachment(84, 0);
        formData_8.left = new FormAttachment((Control)preconvertorLabel, 0, 16384);
        postconvertorLabel.setLayoutData((Object)formData_8);
        postconvertorLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        postconvertorLabel.setText("Post-Transformer:");
        this.inputName = new Text((Composite)this, 2048);
        FormData formData_4 = new FormData();
        formData_4.left = new FormAttachment((Control)postconvertorLabel, 10, -1);
        formData_4.right = new FormAttachment(95, 0);
        formData_4.top = new FormAttachment((Control)inputNameLabel, 0, 128);
        this.inputName.setLayoutData((Object)formData_4);
        this.mappingTypeCombo = new CCombo((Composite)this, 2056);
        this.mappingTypeCombo.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int si = AccessParameterComposite.this.mappingTypeCombo.getSelectionIndex();
                String ss = AccessParameterComposite.this.mappingTypeCombo.getItem(si);
                if (si == AccessParameterComposite.this.javaTypeIndex || ss.equals("<java type>")) {
                    InputDialog id = new InputDialog(AccessParameterComposite.this.getShell(), "Java Class Dialog", "Qualified class name", si == AccessParameterComposite.this.javaTypeIndex ? ss : "", null);
                    if (id.open() == 0) {
                        AccessParameterComposite.this.javaTypeIndex = si;
                        AccessParameterComposite.this.selectedMappingType = id.getValue();
                        AccessParameterComposite.this.mappingTypeCombo.setItem(si, AccessParameterComposite.this.selectedMappingType);
                    }
                } else {
                    AccessParameterComposite.this.selectedMappingType = ss;
                }
            }
        });
        this.mappingTypeCombo.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_5 = new FormData();
        formData_5.right = new FormAttachment((Control)this.inputName, 0, 131072);
        formData_5.top = new FormAttachment((Control)mappingTypeLabel, 0, 128);
        formData_5.left = new FormAttachment((Control)this.inputName, 0, 16384);
        this.mappingTypeCombo.setLayoutData((Object)formData_5);
        this.mappingValue = new Text((Composite)this, 2048);
        FormData formData_6 = new FormData();
        formData_6.right = new FormAttachment((Control)this.mappingTypeCombo, 0, 131072);
        formData_6.top = new FormAttachment((Control)mappimgValueLabel, 0, 128);
        formData_6.left = new FormAttachment((Control)this.mappingTypeCombo, 0, 16384);
        this.mappingValue.setLayoutData((Object)formData_6);
        this.quoted = new Button((Composite)this, 32);
        this.quoted.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_9 = new FormData();
        formData_9.left = new FormAttachment((Control)this.mappingValue, 1, 16384);
        formData_9.top = new FormAttachment((Control)quotedLabel, 0, 128);
        this.quoted.setLayoutData((Object)formData_9);
        this.preTransformerCombo = new CCombo((Composite)this, 2056);
        FormData formData_13 = new FormData();
        formData_13.left = new FormAttachment((Control)this.quoted, 0, 16384);
        formData_13.right = new FormAttachment((Control)this.mappingValue, 0, 131072);
        formData_13.top = new FormAttachment((Control)preconvertorLabel, 0, 128);
        this.preTransformerCombo.setLayoutData((Object)formData_13);
        this.preTransformerCombo.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.postTransformerCombo = new CCombo((Composite)this, 2056);
        FormData formData_14 = new FormData();
        formData_14.right = new FormAttachment((Control)this.preTransformerCombo, 0, 131072);
        formData_14.top = new FormAttachment((Control)postconvertorLabel, 0, 128);
        formData_14.left = new FormAttachment((Control)this.preTransformerCombo, 0, 16384);
        this.postTransformerCombo.setLayoutData((Object)formData_14);
        this.postTransformerCombo.setBackground(SWTResourceManager.getColor(255, 255, 255));
    }

    public void dispose() {
        super.dispose();
    }

    protected void checkSubclass() {
    }

    public final Text getInputName() {
        return this.inputName;
    }

    public final CCombo getMappingTypeCombo() {
        return this.mappingTypeCombo;
    }

    public final Text getMappingValue() {
        return this.mappingValue;
    }

    public final CCombo getPostTransformerCombo() {
        return this.postTransformerCombo;
    }

    public final CCombo getPreTransformerCombo() {
        return this.preTransformerCombo;
    }

    public final Button getQuoted() {
        return this.quoted;
    }

    public String getSelectedMappingType() {
        return this.selectedMappingType;
    }
}

