/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.altova.types.SchemaString;
import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.TransactionBase;
import com.inerweave.sdk.TransactionContext;
import com.inerweave.sdk.composites.NewTransactionFlowWizardComposite;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.vews.TransactionDetailsView;
import com.inerweave.sdk.vews.iw_dialogs.NextTransactionDialog;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.nexttransactionType;
import com.iwtransactions.transactionType;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import java.sql.Timestamp;
import java.util.Hashtable;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class TransactionFlowView
extends ViewPart {
    private ScrolledComposite scrolledComposite_1;
    private Action refreshAction;
    private Action saveFlowAction;
    private NewTransactionFlowWizardComposite trFlowPropertiesComposite;
    private ScrolledComposite scrolledComposite;
    private Composite drawComposite;
    public static final String ID = "iw_sdk.TransactionFlowView";
    private SashForm top = null;
    private int minWdthd = 0;
    private int minWdthp = 0;
    private int maxWdthp = 0;
    private int minHeightTop = 0;
    private int minHeightBottom = 0;
    private Point clickPoint = null;
    private boolean showTransaction = false;
    private String selectedTransactionName = null;
    private IProject currentProject;
    private iwmappingsType currentRoot;
    private TransactionBase currentTransactionFlow;
    private boolean cond = false;

    public void createPartControl(Composite parent) {
        System.currentTimeMillis();
        this.top = new SashForm(parent, 2560);
        this.top.setBackground(Display.getCurrent().getSystemColor(1));
        this.top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                int hgt;
                Rectangle sfca = TransactionFlowView.this.top.getClientArea();
                int wdtd = sfca.width;
                if (TransactionFlowView.this.drawComposite != null) {
                    hgt = (sfca.height - TransactionFlowView.this.top.getBorderWidth() * 3) * 1 / 3;
                    if (wdtd < TransactionFlowView.this.minWdthd) {
                        wdtd = TransactionFlowView.this.minWdthd;
                    }
                    if (hgt < TransactionFlowView.this.minHeightTop) {
                        hgt = TransactionFlowView.this.minHeightTop;
                    }
                    TransactionFlowView.this.drawComposite.setSize(wdtd, hgt);
                }
                if (TransactionFlowView.this.trFlowPropertiesComposite != null) {
                    TransactionFlowView.this.maxWdthp = sfca.width - TransactionFlowView.this.top.getBorderWidth() * 2;
                    hgt = (sfca.height - TransactionFlowView.this.top.getBorderWidth() * 3) * 2 / 3;
                    if (wdtd < TransactionFlowView.this.minWdthp) {
                        wdtd = TransactionFlowView.this.minWdthp;
                    } else if (wdtd > TransactionFlowView.this.maxWdthp) {
                        wdtd = TransactionFlowView.this.maxWdthp;
                    }
                    if (hgt < TransactionFlowView.this.minHeightBottom) {
                        hgt = TransactionFlowView.this.minHeightBottom;
                    }
                    TransactionFlowView.this.trFlowPropertiesComposite.setSize(wdtd, hgt);
                }
            }
        });
        this.scrolledComposite = new ScrolledComposite((Composite)this.top, 896);
        this.drawComposite = new Composite((Composite)this.scrolledComposite, 0);
        this.drawComposite.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                TransactionFlowView.this.clickPoint = new Point(e.x, e.y);
                TransactionFlowView.this.showTransaction = true;
                TransactionFlowView.this.selectedTransactionName = null;
                TransactionFlowView.this.redrawDrawComposite(false);
            }

            public void mouseDown(MouseEvent e) {
                TransactionFlowView.this.clickPoint = new Point(e.x, e.y);
                TransactionFlowView.this.selectedTransactionName = null;
                TransactionFlowView.this.redrawDrawComposite(false);
            }
        });
        this.drawComposite.setLocation(0, 0);
        this.drawComposite.setBackground(Display.getCurrent().getSystemColor(1));
        this.drawComposite.setSize(674, 150);
        this.scrolledComposite.setContent((Control)this.drawComposite);
        this.drawComposite.addPaintListener(new PaintListener(){

            public void paintControl(PaintEvent event) {
                int startX;
                transactionType[] transactions = ConfigContext.getFlowTransactions();
                if (transactions == null) {
                    return;
                }
                GC gc = event.gc;
                Color blue = new Color((Device)event.widget.getDisplay(), 0, 0, 255);
                Color red = new Color((Device)event.widget.getDisplay(), 255, 0, 0);
                Color black = new Color((Device)event.widget.getDisplay(), 0, 0, 0);
                gc.setForeground(blue);
                gc.setLineWidth(3);
                Rectangle rect = event.widget.getDisplay().getClientArea();
                gc.drawOval(rect.x + 20, rect.y + 30, 30, 30);
                gc.drawString("Start", rect.x + 20, rect.y + 61);
                int stepX = 150;
                int startXc = startX = rect.x + 80;
                int i = 0;
                while (i < transactions.length) {
                    block28: {
                        gc.setForeground(blue);
                        gc.drawLine(startX - stepX + 120, rect.y + 45, startX, rect.y + 45);
                        TransactionFlowView.this.drawRightArrow(gc, startX, rect.y + 45);
                        Rectangle trn = new Rectangle(startX, rect.y + 30, 120, 30);
                        if (TransactionFlowView.this.clickPoint != null && trn.contains(TransactionFlowView.this.clickPoint)) {
                            TransactionFlowView.this.cond = false;
                            gc.setForeground(red);
                            try {
                                TransactionFlowView.this.selectedTransactionName = transactions[i].getname().getValue().trim();
                            }
                            catch (Exception e) {
                                MessageDialog.openError((Shell)TransactionFlowView.this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to get transaction name: " + e));
                                break;
                            }
                            if (TransactionFlowView.this.showTransaction) {
                                TransactionFlowView.this.showSelectedTransaction();
                                TransactionFlowView.this.showTransaction = false;
                            }
                        }
                        gc.drawRectangle(trn);
                        try {
                            int tep;
                            int tbp;
                            String transformer;
                            String transformerName;
                            SchemaString ntrt;
                            nexttransactionType ntr;
                            String ctr = transactions[i].getname().getValue().trim();
                            gc.drawString(ctr, startX + 5, rect.y + 40);
                            if (transactions[i].getnexttransactionCount() <= 0 || (ntr = transactions[i].getnexttransaction()) == null || (ntrt = ntr.gettype()) == null || !ntrt.getValue().trim().equalsIgnoreCase("Conditional")) break block28;
                            String nextCondTran = null;
                            String errTran = null;
                            SchemaString transformerNameS = ntr.getValue();
                            if (transformerNameS != null && (transformerName = transformerNameS.getValue()) != null && transformerName.trim().length() > 0 && (transformer = ConfigContext.readXSLTwoBS(transformerName, true)) != null && (tbp = transformer.indexOf("<transaction>")) >= 0 && (tep = transformer.indexOf("</transaction>", tbp)) >= 0) {
                                nextCondTran = transformer.substring(tbp + 13, tep);
                            }
                            if (ntr.geterrorCount() > 0) {
                                errTran = ntr.geterror().getValue();
                            }
                            if (ctr.equals(nextCondTran) || ctr.equals(errTran)) {
                                gc.setLineWidth(1);
                                gc.setForeground(black);
                                gc.drawLine(startX + 120, rect.y + 52, startX + 135, rect.y + 52);
                                gc.drawLine(startX + 135, rect.y + 52, startX + 135, rect.y + 75);
                                gc.drawLine(startX + 135, rect.y + 75, startX + 70, rect.y + 75);
                                gc.drawLine(startX + 70, rect.y + 75, startX + 70, rect.y + 60);
                                TransactionFlowView.this.drawUpArrow(gc, startX + 70, rect.y + 60);
                                gc.setLineWidth(3);
                            }
                            if (nextCondTran == null || nextCondTran.equals(ctr)) break block28;
                            gc.setForeground(blue);
                            int stopX = -1;
                            if (startXc <= startX) {
                                startXc = startX;
                                gc.drawLine(startXc + 60, rect.y + 60, startXc + 60, rect.y + 90);
                            } else {
                                gc.drawLine(startX + 60, rect.y + 30, startX + 60, rect.y + 15);
                                gc.drawLine(startX + 60, rect.y + 15, (startXc += stepX) + 60, rect.y + 15);
                                gc.drawLine(startXc + 60, rect.y + 15, startXc + 60, rect.y + 90);
                            }
                            TransactionFlowView.this.drawDownArrow(gc, startXc + 60, rect.y + 90);
                            int ccn = 0;
                            do {
                                nexttransactionType cnttn;
                                Rectangle ctrn = new Rectangle(startXc, rect.y + 90, 120, 30);
                                if (TransactionFlowView.this.clickPoint != null && ctrn.contains(TransactionFlowView.this.clickPoint)) {
                                    TransactionFlowView.this.cond = true;
                                    gc.setForeground(red);
                                    try {
                                        TransactionFlowView.this.selectedTransactionName = nextCondTran;
                                    }
                                    catch (Exception e) {
                                        MessageDialog.openError((Shell)TransactionFlowView.this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to get transaction name: " + e));
                                        break;
                                    }
                                    if (TransactionFlowView.this.showTransaction) {
                                        TransactionFlowView.this.showSelectedTransaction();
                                        TransactionFlowView.this.showTransaction = false;
                                    }
                                } else {
                                    gc.setForeground(blue);
                                }
                                gc.drawRectangle(ctrn);
                                gc.drawString(nextCondTran, startXc + 5, rect.y + 100);
                                gc.setForeground(blue);
                                transactionType cntt = ConfigContext.getCurrentTransaction(nextCondTran);
                                String cnxtrn = null;
                                if (cntt.getnexttransactionCount() > 0 && (cnttn = cntt.getnexttransaction()).getnameCount() > 0) {
                                    cnxtrn = ConfigContext.getNextTransactionName(cnttn.getname().getValue(), TransactionFlowView.this.currentTransactionFlow.getTransactionId());
                                }
                                if (cnxtrn == null || cnxtrn.trim().length() == 0) {
                                    stopX = startX + (transactions.length - i) * stepX + 15;
                                } else {
                                    int pos = -1;
                                    int jpos = 0;
                                    while (jpos < transactions.length) {
                                        if (transactions[jpos].getname().getValue().trim().equals(cnxtrn.trim())) {
                                            pos = jpos;
                                            break;
                                        }
                                        ++jpos;
                                    }
                                    if (pos >= 0) {
                                        stopX = startXc + (pos - i - ccn) * stepX + 60;
                                    }
                                }
                                if (stopX >= 0) {
                                    if (stopX > startXc + 120) {
                                        gc.drawLine(startXc + 120, rect.y + 105, stopX, rect.y + 105);
                                        gc.drawLine(stopX, rect.y + 105, stopX, rect.y + 60);
                                        continue;
                                    }
                                    if (cnxtrn != null && cnxtrn.trim().length() > 0) {
                                        gc.drawLine(startXc + 120, rect.y + 105, startXc + 135, rect.y + 105);
                                        gc.drawLine(startXc + 135, rect.y + 105, startXc + 135, rect.y + 75);
                                        gc.drawLine(startXc + 135, rect.y + 75, stopX, rect.y + 75);
                                        gc.drawLine(stopX, rect.y + 75, stopX, rect.y + 60);
                                        TransactionFlowView.this.drawUpArrow(gc, stopX, rect.y + 60);
                                        continue;
                                    }
                                    gc.drawLine(startXc + 60, rect.y + 90, startXc + 60, rect.y + 45);
                                    gc.drawLine(startXc + 60, rect.y + 45, stopX + 15, rect.y + 45);
                                    TransactionFlowView.this.drawLeftArrow(gc, stopX + 15, rect.y + 45);
                                    continue;
                                }
                                gc.setForeground(blue);
                                gc.drawLine((startXc += stepX) - stepX + 120, rect.y + 105, startXc, rect.y + 105);
                                TransactionFlowView.this.drawRightArrow(gc, startXc, rect.y + 105);
                                nextCondTran = cnxtrn;
                                ++ccn;
                            } while (stopX < 0);
                        }
                        catch (Exception e) {
                            MessageDialog.openError((Shell)TransactionFlowView.this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to get transaction name: " + e));
                            break;
                        }
                    }
                    startX += stepX;
                    ++i;
                }
                gc.setForeground(blue);
                gc.drawLine(startX - stepX + 120, rect.y + 45, startX, rect.y + 45);
                TransactionFlowView.this.drawRightArrow(gc, startX, rect.y + 45);
                gc.drawOval(startX, rect.y + 30, 30, 30);
                gc.drawString("Stop", startX, rect.y + 61);
                blue.dispose();
                red.dispose();
                black.dispose();
                TransactionFlowView.this.minWdthd = Math.max(startX, startXc + stepX) + 30;
            }
        });
        Menu transactionMenu = new Menu((Control)this.drawComposite);
        this.drawComposite.setMenu(transactionMenu);
        MenuItem editMenuItem = new MenuItem(transactionMenu, 0);
        editMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TransactionFlowView.this.showSelectedTransaction();
            }
        });
        editMenuItem.setAccelerator(262245);
        editMenuItem.setText("Edit");
        new MenuItem(transactionMenu, 2);
        MenuItem cutMenuItem = new MenuItem(transactionMenu, 0);
        cutMenuItem.setEnabled(false);
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(transactionMenu, 0);
        copyMenuItem.setEnabled(false);
        copyMenuItem.setText("Copy");
        MenuItem pasteMenuItem = new MenuItem(transactionMenu, 0);
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.setText("Paste");
        new MenuItem(transactionMenu, 2);
        MenuItem deleteMenuItem_1 = new MenuItem(transactionMenu, 0);
        deleteMenuItem_1.setEnabled(false);
        deleteMenuItem_1.setText("Remove");
        new MenuItem(transactionMenu, 2);
        MenuItem nextTransactionMenuItem = new MenuItem(transactionMenu, 0);
        nextTransactionMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                TransactionFlowView.this.processNextTransaction();
            }
        });
        nextTransactionMenuItem.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/next.gif"));
        nextTransactionMenuItem.setText("Next Transaction");
        this.scrolledComposite_1 = new ScrolledComposite((Composite)this.top, 768);
        this.scrolledComposite_1.setBackground(Display.getCurrent().getSystemColor(1));
        this.trFlowPropertiesComposite = new NewTransactionFlowWizardComposite((Composite)this.scrolledComposite_1, 0);
        this.trFlowPropertiesComposite.setBackground(Display.getCurrent().getSystemColor(1));
        this.trFlowPropertiesComposite.setLocation(0, 0);
        this.trFlowPropertiesComposite.setSize(667, 300);
        Point tfps = this.trFlowPropertiesComposite.computeSize(-1, -1);
        this.minWdthp = tfps.x;
        this.minHeightBottom = tfps.y;
        this.scrolledComposite_1.setContent((Control)this.trFlowPropertiesComposite);
        this.top.setWeights(new int[]{1, 2});
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    private void drawUpArrow(GC gc, int x, int y) {
        gc.drawLine(x, y, x - 5, y + 5);
        gc.drawLine(x, y, x + 5, y + 5);
    }

    private void drawDownArrow(GC gc, int x, int y) {
        gc.drawLine(x, y, x - 5, y - 5);
        gc.drawLine(x, y, x + 5, y - 5);
    }

    private void drawRightArrow(GC gc, int x, int y) {
        gc.drawLine(x, y, x - 5, y - 5);
        gc.drawLine(x, y, x - 5, y + 5);
    }

    private void drawLeftArrow(GC gc, int x, int y) {
        gc.drawLine(x, y, x + 5, y - 5);
        gc.drawLine(x, y, x + 5, y + 5);
    }

    public void setFocus() {
    }

    public void refresh() {
        Timestamp tst;
        String title = this.currentTransactionFlow.getTransactionId();
        this.setPartName(title);
        this.setTitleToolTip("Transaction Flow " + title);
        this.trFlowPropertiesComposite.getTransactionId().setText(title);
        this.trFlowPropertiesComposite.setTransactionDescription(this.currentTransactionFlow.getDescription());
        String sol = this.currentTransactionFlow.getSolution();
        this.trFlowPropertiesComposite.getSolution().select(0);
        if (sol != null && sol.trim().length() > 0) {
            String[] sitm = this.trFlowPropertiesComposite.getSolution().getItems();
            int si = 0;
            while (si < sitm.length) {
                if (sitm[si].startsWith(String.valueOf(sol) + ":")) {
                    this.trFlowPropertiesComposite.getSolution().select(si);
                    break;
                }
                ++si;
            }
        }
        this.trFlowPropertiesComposite.getHostedPublic().setSelection(this.currentTransactionFlow.isHostedPublic());
        this.trFlowPropertiesComposite.getActiveFlow().setSelection(this.currentTransactionFlow.isActive());
        this.trFlowPropertiesComposite.getPrimaryTsUrl().setText(this.currentTransactionFlow.getPrimaryTransformationServerURL());
        this.trFlowPropertiesComposite.getSecondaryTsUrl().setText(this.currentTransactionFlow.getSecondaryTransformationServerURL());
        this.trFlowPropertiesComposite.getInnerSycles().setText(String.valueOf(this.currentTransactionFlow.getInnerCycles()));
        Hashtable<String, TransactionBase.ParameterValue> partb = this.currentTransactionFlow.getParameters();
        if (partb != null) {
            this.trFlowPropertiesComposite.getPropertyTable().removeAll();
            for (String pname : partb.keySet()) {
                TableItem newItemTableItem = new TableItem(this.trFlowPropertiesComposite.getPropertyTable(), 2048);
                newItemTableItem.setText(new String[]{pname, partb.get(pname).getParameterValue(), partb.get(pname).isUpload() ? "Yes" : "No", partb.get(pname).isPassword() ? "Yes" : "No", partb.get(pname).isFixed() ? "Prohibited" : "Allowed"});
            }
        }
        if ((tst = this.currentTransactionFlow.getTransactionStartTime()) != null) {
            TableItem newItemTableItem = new TableItem(this.trFlowPropertiesComposite.getPropertyTable(), 2048);
            newItemTableItem.setText(new String[]{"QueryStartTime", tst.toString(), "No", "No", "Allowed"});
        }
        if (this.currentTransactionFlow instanceof TransactionContext) {
            TransactionContext ct = (TransactionContext)this.currentTransactionFlow;
            long shft = ct.getTransactionShiftFromHartBeat();
            long scin = ct.getTransactionInterval();
            if (shft >= 0L) {
                this.trFlowPropertiesComposite.getScheduledInterval().setText(String.valueOf(scin));
                this.trFlowPropertiesComposite.getSchedulingCombo().setEnabled(true);
                if (scin == 0L) {
                    this.trFlowPropertiesComposite.getSchedulingCombo().select(0);
                } else if (scin < 0L) {
                    this.trFlowPropertiesComposite.getSchedulingCombo().select(1);
                } else {
                    this.trFlowPropertiesComposite.getSchedulingCombo().select(2);
                }
                this.trFlowPropertiesComposite.getShift().setText(String.valueOf(shft));
                this.trFlowPropertiesComposite.getScheduledInterval().setEnabled(scin > 0L);
                this.trFlowPropertiesComposite.getShift().setEnabled(true);
                this.trFlowPropertiesComposite.getHoursOfTime().setEnabled(false);
                this.trFlowPropertiesComposite.getMinOfTime().setEnabled(false);
                this.trFlowPropertiesComposite.getSecOfTime().setEnabled(false);
            } else {
                if (shft == -1L) {
                    this.trFlowPropertiesComposite.getSchedulingCombo().select(3);
                    long hr = scin / 3600L;
                    long mn = (scin - hr * 60L * 60L) / 60L;
                    long sc = scin - hr * 60L * 60L - mn * 60L;
                    try {
                        this.trFlowPropertiesComposite.getHoursOfTime().select(ConfigContext.getComboIndex(this.trFlowPropertiesComposite.getHoursOfTime().getItems(), String.format("%1$02d", hr)));
                        this.trFlowPropertiesComposite.getMinOfTime().select(ConfigContext.getComboIndex(this.trFlowPropertiesComposite.getMinOfTime().getItems(), String.format("%1$02d", mn)));
                        this.trFlowPropertiesComposite.getSecOfTime().select(ConfigContext.getComboIndex(this.trFlowPropertiesComposite.getSecOfTime().getItems(), String.format("%1$02d", sc)));
                    }
                    catch (Exception e) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to set day time: " + e));
                    }
                    this.trFlowPropertiesComposite.getHoursOfTime().setEnabled(true);
                    this.trFlowPropertiesComposite.getMinOfTime().setEnabled(true);
                    this.trFlowPropertiesComposite.getSecOfTime().setEnabled(true);
                }
                this.trFlowPropertiesComposite.getScheduledInterval().setText("0");
                this.trFlowPropertiesComposite.getShift().setText("0");
                this.trFlowPropertiesComposite.getScheduledInterval().setEnabled(false);
                this.trFlowPropertiesComposite.getShift().setEnabled(false);
            }
            this.trFlowPropertiesComposite.getRunAtStartUp().setEnabled(true);
            this.trFlowPropertiesComposite.getRunAtStartUp().setSelection(ct.isRunAtStartUp());
            this.trFlowPropertiesComposite.getStatefulFlow().setEnabled(true);
            this.trFlowPropertiesComposite.getStatefulFlow().setSelection(ct.isStateful());
            this.trFlowPropertiesComposite.getNumberOfExecutions().setText(String.valueOf(ct.getNumberOfExecutions()));
            this.trFlowPropertiesComposite.getNumberOfExecutions().setEnabled(true);
        } else {
            this.trFlowPropertiesComposite.getSchedulingCombo().select(0);
            this.trFlowPropertiesComposite.getSchedulingCombo().setEnabled(false);
            this.trFlowPropertiesComposite.getScheduledInterval().setText("0");
            this.trFlowPropertiesComposite.getShift().setText("0");
            this.trFlowPropertiesComposite.getScheduledInterval().setEnabled(false);
            this.trFlowPropertiesComposite.getShift().setEnabled(false);
            this.trFlowPropertiesComposite.getHoursOfTime().setEnabled(false);
            this.trFlowPropertiesComposite.getMinOfTime().setEnabled(false);
            this.trFlowPropertiesComposite.getSecOfTime().setEnabled(false);
            this.trFlowPropertiesComposite.getRunAtStartUp().setSelection(false);
            this.trFlowPropertiesComposite.getStatefulFlow().setEnabled(false);
            this.trFlowPropertiesComposite.getRunAtStartUp().setEnabled(false);
            this.trFlowPropertiesComposite.getNumberOfExecutions().setText("");
            this.trFlowPropertiesComposite.getNumberOfExecutions().setEnabled(false);
        }
        this.redrawDrawComposite(true);
    }

    public void initializeScreen() {
        this.clickPoint = null;
        this.currentProject = Designer.getSelectedProject();
        if (this.currentProject == null) {
            return;
        }
        this.currentRoot = ConfigContext.getIwmappingsRoot();
        this.currentTransactionFlow = ConfigContext.getCurrentFlow();
        if (this.currentTransactionFlow == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Screen Initialize Error", (String)"Unable to find current flow");
            return;
        }
        this.refresh();
    }

    private void redrawDrawComposite(boolean resize) {
        this.drawComposite.redraw();
        this.drawComposite.update();
        if (resize) {
            this.top.notifyListeners(11, new Event());
        }
    }

    private void saveTransactionFlow() {
        NavigationView nv;
        TransactionBase currentTransactionFlow;
        IProject cpr = Designer.getSelectedProject();
        String tfid = null;
        iwmappingsType ciwmr = ConfigContext.getIwmappingsRoot();
        boolean mod = false;
        if (cpr == null || !cpr.getName().equals(this.currentProject.getName())) {
            mod = true;
            Designer.setSelectedProject(this.currentProject);
            tfid = ConfigContext.getCurrentTransactionFlowId();
            ConfigContext.readIMConfigContext();
            ConfigContext.setCurrentTransactionFlowId(this.getPartName());
            ConfigContext.setIwmappingsRoot(this.currentRoot);
        }
        if (!this.trFlowPropertiesComposite.saveFlow(currentTransactionFlow = ConfigContext.getCurrentFlow())) {
            return;
        }
        if (!ConfigContext.writeIMConfigContext()) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"Unable to store IM configuration");
            return;
        }
        try {
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"Unable to save transaction.xml");
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"Unable to save transaction.xml");
        }
        if ((nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView")) != null) {
            nv.setAndSelectViewer();
        }
        if (mod) {
            Designer.setSelectedProject(cpr);
            ConfigContext.readIMConfigContext();
            ConfigContext.setCurrentTransactionFlowId(tfid);
            ConfigContext.setIwmappingsRoot(ciwmr);
        }
        this.redrawDrawComposite(true);
    }

    private void createActions() {
        this.saveFlowAction = new Action("Save Flow"){

            public void run() {
                TransactionFlowView.this.saveTransactionFlow();
            }
        };
        this.saveFlowAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.saveFlowAction.setAccelerator(262227);
        this.refreshAction = new Action("Refresh"){

            public void run() {
                TransactionFlowView.this.refresh();
            }
        };
        this.refreshAction.setToolTipText("Refresh");
        this.refreshAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.refreshAction);
        tbm.add((IAction)this.saveFlowAction);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.refreshAction);
        manager.add((IAction)this.saveFlowAction);
    }

    private void showSelectedTransaction() {
        if (this.selectedTransactionName == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"No Transaction selected");
            return;
        }
        ConfigContext.setCurrentTransactionName(this.selectedTransactionName);
        try {
            IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            ap.showView("iw_sdk.TransactionDetailsView");
            ((TransactionDetailsView)ap.findView("iw_sdk.TransactionDetailsView")).initializeScreen();
        }
        catch (PartInitException e1) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to show Transaction Details view " + e1.toString()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void processNextTransaction() {
        nexttransactionType nt;
        int ntcn;
        NextTransactionDialog ntd;
        transactionType ctr;
        block23: {
            if (this.selectedTransactionName == null) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"No Transaction selected");
                return;
            }
            ctr = ConfigContext.getCurrentTransaction(this.selectedTransactionName);
            if (ctr == null) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)"No Transaction available");
                return;
            }
            ntd = new NextTransactionDialog(this.getSite().getShell());
            ntd.setTransactions(ConfigContext.getTransactions(this.currentProject, true, this.cond ? null : this.selectedTransactionName));
            ntd.setTransformers(ConfigContext.getXsltTransformers(this.currentProject));
            ntcn = ctr.getnexttransactionCount();
            nt = null;
            try {
                String cntr;
                if (ntcn <= 0) break block23;
                nt = ctr.getnexttransaction();
                if (nt.getnameCount() > 0) {
                    ntd.setNextTransactionName(ConfigContext.getNextTransactionName(nt.getname().getValue().trim(), this.currentTransactionFlow.getTransactionId()));
                }
                if (nt.gettypeCount() > 0) {
                    ntd.setNextTransactionTypeValue(nt.gettype().getValue().trim());
                }
                if (nt.geterrorCount() > 0) {
                    ntd.setErrorTransactionName(nt.geterror().getValue().trim());
                }
                if ((cntr = nt.getValue().getValue().trim()).length() > 0) {
                    ntd.setConditionalTransformerName(cntr);
                }
            }
            catch (Exception e) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to parse next transaction " + e));
                return;
            }
        }
        ntd.open();
        if (ntd.getReturnCode() == 0) {
            try {
                String ntp = ntd.getNextTransactionTypeValue();
                String nte = ntd.getErrorTransactionName();
                String ntt = ntd.getConditionalTransformerName();
                if (ntp == null) {
                    ntp = "";
                }
                String ntn = ntd.getNextTransactionName();
                String newName = String.valueOf(this.currentTransactionFlow.getTransactionId()) + ":" + ntn + ";";
                if (ntn == null || ntn.trim().length() == 0) {
                    if (ntcn > 0) {
                        if (nt.getnameCount() <= 0) {
                            ctr.removenexttransaction();
                            this.redrawDrawComposite(true);
                            return;
                        }
                        newName = ConfigContext.removeNextTransactionName(nt.getname().getValue().trim(), this.currentTransactionFlow.getTransactionId());
                        if (newName.length() == 0) {
                            ctr.removenexttransaction();
                            this.redrawDrawComposite(true);
                            return;
                        }
                    }
                } else if (ntcn > 0 && nt.getnameCount() > 0 && (newName = ConfigContext.upsertNextTransactionName(ntn, this.currentTransactionFlow.getTransactionId(), nt.getname().getValue().trim(), false)) == null) {
                    if (!MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Next Transaction", (String)"This transaction may belong exclusively to an another transaction flow. If you use it in the current flow, another flow can be corrupted. Do you want to proceed?")) {
                        return;
                    }
                    newName = ConfigContext.upsertNextTransactionName(ntn, this.currentTransactionFlow.getTransactionId(), nt.getname().getValue().trim(), true);
                }
                nt = new nexttransactionType();
                nt.addname(newName);
                nt.addtype(ntp);
                if (nte != null && nte.trim().length() > 0) {
                    nt.adderror(nte);
                }
                if (ntt != null && ntt.trim().length() > 0) {
                    nt.setValue(new SchemaString(ntt));
                }
                if (ntcn > 0) {
                    ctr.replacenexttransactionAt(nt, 0);
                } else {
                    ctr.addnexttransaction(nt);
                }
            }
            catch (Exception e) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Flow", (String)("Unable to construct next transaction " + e));
                return;
            }
            this.redrawDrawComposite(true);
        }
    }
}

