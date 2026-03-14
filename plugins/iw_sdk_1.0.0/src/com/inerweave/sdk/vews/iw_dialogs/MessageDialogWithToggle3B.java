/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews.iw_dialogs;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogWithToggle3B
extends MessageDialogWithToggle {
    private int retCode3;

    public MessageDialogWithToggle3B(Shell parentShell, String dialogTitle, Image image, String message, int dialogImageType, String[] dialogButtonLabels, int defaultIndex, String toggleMessage, boolean toggleState) {
        super(parentShell, dialogTitle, image, message, dialogImageType, dialogButtonLabels, defaultIndex, toggleMessage, toggleState);
    }

    public int open() {
        super.open();
        return this.retCode3;
    }

    public void create() {
        super.create();
        this.getButton(0).addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 0;
            }
        });
        this.getButton(0).addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 0;
            }
        });
        this.getButton(1).addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 1;
            }
        });
        this.getButton(1).addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 1;
            }
        });
        this.getButton(2).addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 2;
            }
        });
        this.getButton(2).addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                MessageDialogWithToggle3B.this.retCode3 = 2;
            }
        });
    }
}

