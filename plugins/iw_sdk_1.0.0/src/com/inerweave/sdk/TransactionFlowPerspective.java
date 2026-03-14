/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class TransactionFlowPerspective
implements IPerspectiveFactory {
    public static final String ID = "iw_sdk.tf_perspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.setFixed(false);
        layout.addStandaloneView("iw_sdk.NavigationView", true, 1, 0.3f, editorArea);
        IFolderLayout propertiesFolder = layout.createFolder("properties_folder", 4, 0.7f, "iw_sdk.NavigationView");
        propertiesFolder.addPlaceholder("iw_sdk.ConnectionView:*");
        propertiesFolder.addView("iw_sdk.ConnectionView");
        propertiesFolder.addPlaceholder("iw_sdk.AccessParameterView:*");
        propertiesFolder.addView("iw_sdk.AccessParameterView");
        IFolderLayout mainFolder = layout.createFolder("transaction_flow", 3, 0.5f, editorArea);
        mainFolder.addPlaceholder("iw_sdk.TransactionFlowView:*");
        mainFolder.addView("iw_sdk.TransactionFlowView");
        IFolderLayout detailsFolder = layout.createFolder("details_folder", 4, 0.5f, "transaction_flow");
        detailsFolder.addPlaceholder("iw_sdk.TransactionDetailsView:*");
        detailsFolder.addView("iw_sdk.TransactionDetailsView");
        detailsFolder.addPlaceholder("iw_sdk.DataMapView:*");
        detailsFolder.addView("iw_sdk.DataMapView");
        detailsFolder.addPlaceholder("org.eclipse.ui.console.ConsoleView:*");
        detailsFolder.addView("org.eclipse.ui.console.ConsoleView");
        layout.getViewLayout("iw_sdk.NavigationView").setCloseable(true);
        layout.addPerspectiveShortcut("iw_sdk.te_perspective");
        layout.addPerspectiveShortcut("iw_sdk.config_perspective");
        layout.addShowViewShortcut("iw_sdk.TransactionFlowView");
        layout.addShowViewShortcut("iw_sdk.TransactionDetailsView");
        layout.addShowViewShortcut("iw_sdk.DataMapView");
        layout.addShowViewShortcut("iw_sdk.ConnectionView");
        layout.addShowViewShortcut("iw_sdk.AccessParameterView");
    }
}

