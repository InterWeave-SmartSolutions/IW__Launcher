/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class TemplateEditorPerspective
implements IPerspectiveFactory {
    public static final String ID = "iw_sdk.te_perspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.setFixed(false);
        layout.addStandaloneView("iw_sdk.NavigationView", true, 1, 0.25f, editorArea);
        IFolderLayout folder = layout.createFolder("template_editor", 3, 0.95f, editorArea);
        folder.addPlaceholder("iw_sdk.XSLTEditorView:*");
        folder.addView("iw_sdk.XSLTEditorView");
        folder.addPlaceholder("iw_sdk.IwTemplateEditor:*");
        folder.addView("iw_sdk.IwTemplateEditor");
        layout.getViewLayout("iw_sdk.NavigationView").setCloseable(true);
        layout.addPerspectiveShortcut("iw_sdk.config_perspective");
        layout.addPerspectiveShortcut("iw_sdk.tf_perspective");
        layout.addShowViewShortcut("iw_sdk.XSLTEditorView");
        layout.addShowViewShortcut("iw_sdk.IwTemplateEditor");
    }
}

