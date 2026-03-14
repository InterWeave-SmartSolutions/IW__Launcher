/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.composites.NewTransactionWizardComposite;
import com.inerweave.sdk.vews.NavigationView;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.transactionType;
import com.swtdesigner.ResourceManager;
import iw_sdk.Iw_sdkPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class TransactionDetailsView
extends ViewPart {
    private Action action;
    private Action saveTransactionsDetailsAction;
    private NewTransactionWizardComposite detailsComposite;
    public static final String ID = "iw_sdk.TransactionDetailsView";
    private Composite top = null;
    private int minWdthDetails = 0;
    private int minHeightDetails = 0;
    private transactionType currentTransaction;
    private IProject currentProject;
    private iwmappingsType currentRoot;

    public void createPartControl(Composite parent) {
        this.top = new Composite(parent, 0);
        this.top.setLayout((Layout)new FormLayout());
        this.top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle sfca = TransactionDetailsView.this.top.getClientArea();
                if (TransactionDetailsView.this.detailsComposite != null) {
                    int wdt = sfca.width;
                    int hgt = sfca.height;
                    if (wdt < TransactionDetailsView.this.minWdthDetails) {
                        wdt = TransactionDetailsView.this.minWdthDetails;
                    }
                    if (hgt < TransactionDetailsView.this.minHeightDetails) {
                        hgt = TransactionDetailsView.this.minHeightDetails;
                    }
                    TransactionDetailsView.this.detailsComposite.setSize(wdt, hgt);
                }
            }
        });
        this.top.setBackground(Display.getCurrent().getSystemColor(1));
        ScrolledComposite scrolledComposite_1 = new ScrolledComposite(this.top, 768);
        FormData formData = new FormData();
        formData.bottom = new FormAttachment(100, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.left = new FormAttachment(0, 0);
        scrolledComposite_1.setLayoutData((Object)formData);
        this.detailsComposite = new NewTransactionWizardComposite((Composite)scrolledComposite_1, 0, true);
        this.detailsComposite.setBackground(Display.getCurrent().getSystemColor(1));
        scrolledComposite_1.setContent((Control)this.detailsComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    public void setFocus() {
    }

    public void saveTransaction() {
        if (this.currentTransaction == null) {
            return;
        }
        IProject cpr = Designer.getSelectedProject();
        iwmappingsType ciwmr = null;
        boolean mod = false;
        if (cpr == null || !cpr.getName().equals(this.currentProject.getName())) {
            mod = true;
            ciwmr = ConfigContext.getIwmappingsRoot();
            Designer.setSelectedProject(this.currentProject);
            ConfigContext.setIwmappingsRoot(this.currentRoot);
        }
        try {
            if (!this.detailsComposite.saveTransaction(this.currentTransaction)) {
                return;
            }
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transaction Details", (String)"Unable to save transaction.xml");
                return;
            }
            NavigationView nv = (NavigationView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.NavigationView");
            if (nv != null) {
                nv.setAndSelectViewer();
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transactions Details", (String)("Unable to save transaction details: " + e.toString()));
        }
        if (mod) {
            Designer.setSelectedProject(cpr);
            ConfigContext.setIwmappingsRoot(ciwmr);
        }
    }

    public void initializeScreen() {
        this.currentTransaction = ConfigContext.getCurrentTransaction();
        if (this.currentTransaction == null) {
            return;
        }
        this.currentProject = Designer.getSelectedProject();
        this.currentRoot = ConfigContext.getIwmappingsRoot();
        this.detailsComposite.setCurrentTransaction(this.currentTransaction);
        this.refreshScreen();
    }

    public void refreshScreen() {
        try {
            if (this.currentProject == null || this.currentTransaction == null) {
                return;
            }
            String trName = this.currentTransaction.getname().getValue().trim();
            this.setPartName(trName);
            this.setTitleToolTip("Transaction " + trName);
            this.detailsComposite.getTransactionName().setText(trName);
            if (this.currentTransaction.gettypeCount() > 0) {
                String ttp = this.currentTransaction.gettype().getValue().trim();
                if (ttp.length() == 0) {
                    this.detailsComposite.getTransactionTypeCombo().select(0);
                } else {
                    this.detailsComposite.getTransactionTypeCombo().select(ttp.equalsIgnoreCase("sequential") ? 1 : 2);
                }
            }
            if (this.currentTransaction.getclassnameCount() > 0) {
                this.detailsComposite.getAdaptorClassName().select(ConfigContext.getComboIndex(this.detailsComposite.getAdaptorClassName().getItems(), this.currentTransaction.getclassname().getValue().trim()));
            } else {
                this.detailsComposite.getAdaptorClassName().select(0);
            }
            String[] fti = ConfigContext.getXsltTransformers(this.currentProject);
            this.detailsComposite.getFinalTransformer().setItems(fti);
            if (this.currentTransaction.gettransformCount() > 0) {
                String fs = this.currentTransaction.gettransform().getValue();
                if (fs == null || fs.trim().length() == 0) {
                    this.detailsComposite.getFinalTransformer().select(0);
                } else {
                    this.detailsComposite.getFinalTransformer().select(ConfigContext.getComboIndex(fti, fs.trim()));
                }
            } else {
                this.detailsComposite.getFinalTransformer().select(0);
            }
            this.refreshDataMaps(null);
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Transactions Details", (String)("Unable to create transaction view: " + e.toString()));
        }
    }

    public void refreshDataMaps(String mapName) throws Exception {
        String[] fti = ConfigContext.getDataMaps(this.currentTransaction);
        this.detailsComposite.getDataMapList().setItems(fti);
        this.detailsComposite.getDataMapList().select(mapName == null ? -1 : ConfigContext.getComboIndex(fti, mapName));
        ConfigContext.setCurrentDataMapName(mapName);
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.action);
        tbm.add((IAction)this.saveTransactionsDetailsAction);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.action);
        manager.add((IAction)this.saveTransactionsDetailsAction);
    }

    private void createActions() {
        this.saveTransactionsDetailsAction = new Action("Save Transaction Details"){

            public void run() {
                TransactionDetailsView.this.saveTransaction();
            }
        };
        this.saveTransactionsDetailsAction.setToolTipText("Save Transaction");
        this.saveTransactionsDetailsAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.saveTransactionsDetailsAction.setAccelerator(262227);
        this.action = new Action("Refresh"){

            public void run() {
                TransactionDetailsView.this.refreshScreen();
            }
        };
        this.action.setToolTipText("Refresh View");
        this.action.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
    }

    public transactionType getCurrentTransaction() {
        return this.currentTransaction;
    }

    public IProject getCurrentProject() {
        return this.currentProject;
    }
}

