/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class TransactionFlowDescription
extends Dialog {
    private StyledText styledText;
    private String flowDescription;

    public TransactionFlowDescription(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite)super.createDialogArea(parent);
        GridLayout gridLayout = new GridLayout();
        container.setLayout((Layout)gridLayout);
        container.setBackground(Display.getCurrent().getSystemColor(1));
        this.styledText = new StyledText(container, 2562);
        this.styledText.setWordWrap(true);
        this.styledText.addFocusListener((FocusListener)new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                TransactionFlowDescription.this.flowDescription = TransactionFlowDescription.this.styledText.getText().trim();
            }
        });
        GridData gridData = new GridData(4, 4, true, true);
        gridData.heightHint = 0;
        gridData.widthHint = 0;
        this.styledText.setLayoutData((Object)gridData);
        Menu menu = new Menu((Control)this.styledText);
        this.styledText.setMenu(menu);
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TransactionFlowDescription.this.styledText.cut();
            }
        });
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(menu, 0);
        copyMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TransactionFlowDescription.this.styledText.copy();
            }
        });
        copyMenuItem.setText("Copy");
        MenuItem pasteMenuItem = new MenuItem(menu, 0);
        pasteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TransactionFlowDescription.this.styledText.paste();
            }
        });
        pasteMenuItem.setText("Paste");
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        this.createButton(parent, 0, IDialogConstants.OK_LABEL, true);
        this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Transaction Flow Description");
        newShell.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/sequence.gif"));
    }

    public StyledText getStyledText() {
        return this.styledText;
    }

    public String getFlowDescription() {
        return this.flowDescription;
    }
}

