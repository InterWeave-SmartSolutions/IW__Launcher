/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProjectView
extends ViewPart {
    public static final String ID = "com.inerweave.sdk.vews.ProjectView";
    private Composite top = null;

    public void createPartControl(Composite parent) {
        this.top = new Composite(parent, 0);
    }

    public void setFocus() {
    }
}

