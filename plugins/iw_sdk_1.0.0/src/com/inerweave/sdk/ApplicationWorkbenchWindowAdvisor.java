/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.ApplicationActionBarAdvisor;
import com.inerweave.sdk.Designer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor
extends WorkbenchWindowAdvisor {
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
        PlatformUI.getPreferenceStore().setValue("SHOW_TRADITIONAL_STYLE_TABS", false);
        PlatformUI.getPreferenceStore().setValue("DOCK_PERSPECTIVE_BAR", "topRight");
        PlatformUI.getPreferenceStore().setValue("SHOW_TEXT_ON_PERSPECTIVE_BAR", false);
        PlatformUI.getPreferenceStore().setValue("OPEN_NEW_PERSPECTIVE", "OPEN_PERSPECTIVE_REPLACE");
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = this.getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(false);
        configurer.setShowPerspectiveBar(true);
        Designer.setFirstProjectSelected();
    }
}

