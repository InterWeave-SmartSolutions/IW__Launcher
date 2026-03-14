/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ConfigurationPerspective
implements IPerspectiveFactory {
    public static final String ID = "iw_sdk.config_perspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView("iw_sdk.NavigationView", true, 1, 0.25f, editorArea);
        IFolderLayout folder = layout.createFolder("configurations", 3, 0.95f, editorArea);
        folder.addPlaceholder("iw_sdk.ConfigBDView:*");
        folder.addView("iw_sdk.ConfigBDView");
        folder.addPlaceholder("iw_sdk.ConfigTSView:*");
        folder.addView("iw_sdk.ConfigTSView");
        layout.setFixed(false);
        layout.getViewLayout("iw_sdk.NavigationView").setCloseable(true);
        layout.addPerspectiveShortcut("iw_sdk.te_perspective");
        layout.addPerspectiveShortcut("iw_sdk.tf_perspective");
        layout.addShowViewShortcut("iw_sdk.ConfigBDView");
        layout.addShowViewShortcut("iw_sdk.ConfigTSView");
    }
}

