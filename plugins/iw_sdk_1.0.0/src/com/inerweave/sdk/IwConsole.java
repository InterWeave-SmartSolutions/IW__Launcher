/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import java.io.IOException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class IwConsole {
    private static IwConsole fDefault = null;
    private String fTitle = null;
    private MessageConsole fMessageConsole = null;
    public static final int MSG_INFORMATION = 1;
    public static final int MSG_ERROR = 2;
    public static final int MSG_WARNING = 3;

    public IwConsole(String messageTitle) {
        fDefault = this;
        this.fTitle = messageTitle;
    }

    public static IwConsole getDefault() {
        return fDefault;
    }

    public void println(String msg, int msgKind) {
        if (msg == null) {
            return;
        }
        if (!this.displayConsoleView()) {
            MessageDialog.openError((Shell)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), (String)"Console: Display Error", (String)msg);
            return;
        }
        MessageConsoleStream ost = this.getNewMessageConsoleStream(msgKind);
        ost.println(msg);
        try {
            ost.flush();
        }
        catch (IOException e) {
            MessageDialog.openError((Shell)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), (String)"Consolse: Flush Error", (String)msg);
        }
    }

    public boolean displayConsoleView() {
        try {
            IWorkbenchPage activePage;
            IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (activeWorkbenchWindow != null && (activePage = activeWorkbenchWindow.getActivePage()) != null) {
                activePage.showView("org.eclipse.ui.console.ConsoleView", null, 2);
            }
        }
        catch (PartInitException partEx) {
            return false;
        }
        return true;
    }

    private MessageConsoleStream getNewMessageConsoleStream(int msgKind) {
        int swtColorId = 6;
        switch (msgKind) {
            case 1: {
                swtColorId = 6;
                break;
            }
            case 2: {
                swtColorId = 12;
                break;
            }
            case 3: {
                swtColorId = 10;
            }
        }
        MessageConsoleStream msgConsoleStream = this.getMessageConsole().newMessageStream();
        msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));
        msgConsoleStream.setActivateOnWrite(true);
        return msgConsoleStream;
    }

    private MessageConsole getMessageConsole() {
        if (this.fMessageConsole == null) {
            this.createMessageConsoleStream(this.fTitle);
        }
        return this.fMessageConsole;
    }

    private void createMessageConsoleStream(String title) {
        this.fMessageConsole = new MessageConsole(title, null);
        ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{this.fMessageConsole});
    }
}

