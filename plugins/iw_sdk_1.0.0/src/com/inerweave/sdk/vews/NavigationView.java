/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.QueryContext;
import com.inerweave.sdk.TransactionBase;
import com.inerweave.sdk.TransactionContext;
import com.inerweave.sdk.vews.ConfigBDView;
import com.inerweave.sdk.vews.ConfigTSView;
import com.inerweave.sdk.vews.ConnectionView;
import com.inerweave.sdk.vews.TemplateEditorView;
import com.inerweave.sdk.vews.TransactionDetailsView;
import com.inerweave.sdk.vews.TransactionFlowView;
import com.inerweave.sdk.vews.XSLTEditorView;
import com.inerweave.sdk.vews.iw_dialogs.MessageDialogWithToggle3B;
import com.iwtransactions.accessType;
import com.iwtransactions.datamapType;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.transactionType;
import com.iwtransactions.translatorType;
import iw_sdk.Iw_sdkPlugin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

public class NavigationView
extends ViewPart {
    private MenuItem renameMenuItem;
    private MenuItem cloneTransactionsMenuItem;
    private Tree tree;
    private Action refreshAction;
    private static final String TRANSFORTMATION_SERVER = "Transfortmation Server";
    private static final String QUERIES = "Queries";
    private static final String TRANSACTION_FLOWS = "Transaction Flows";
    private static final String INTEGRATION_FLOWS = "Integration Flows";
    private static final String CONNECTIONS = "Connections";
    private static final String XSLT = "XSLT";
    private static final String TEMPLATES = "Templates";
    private static final String TRANSACTIONS = "Transactions";
    private static final String CONFIGURATION = "Configuration";
    private static final String INTEGRATION_MANAGER = "Integration Manager";
    private static boolean demoMode = false;
    public static final String ID = "iw_sdk.NavigationView";
    private TreeViewer viewer;
    private DeleteAction deleteActionGlobal;
    private boolean projectSelected = false;
    private boolean xsltSelected = false;
    private boolean templateSelected = false;
    private boolean transactionSelected = false;
    private boolean transactionFlowSelected = false;
    private boolean querySelected = false;
    private boolean connectionSelected = false;
    private Object[] expanedElements;
    private TreeObject selectedObject;
    private boolean processSelection = true;
    private MenuItem pasteMenuItem;

    public void createDummyModel(TreeParent root) {
        TreeParent p1 = this.createProjectView("SF_MSSQL_Solution", new String[]{"login_into_sf", "get_updated", "retrieve", "store_to_db"}, new String[]{"iwp2retrieve", "iwp2insert"}, new String[]{"SFAccount", "SFOpportunity"}, new String[]{"From SF to DB", "New From DB to SF", "Updated From DB To SF"}, new String[]{"View SF", "View DB"}, new String[]{"SalesForce", "MS SQL"}, true);
        TreeParent p2 = this.createProjectView("ACS_GSK_Solution", new String[]{"select_from_db", "store_to_nc"}, new String[]{"iwp2select", "iwp2store"}, new String[]{"SFAccount", "SFOpportunity"}, new String[]{"From NC to DB", "From DB to NC"}, new String[]{"View NC"}, new String[]{"NetCool", "Oracle"}, true);
        root.addChild(p1);
        root.addChild(p2);
    }

    public TreeParent createProjectModel() {
        TreeParent root = new TreeParent("");
        if (demoMode) {
            this.createDummyModel(root);
        }
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        IProject spj = Designer.getSelectedProject();
        IProject[] iProjectArray = projects;
        int n = 0;
        int n2 = iProjectArray.length;
        while (n < n2) {
            block42: {
                String[] xfn;
                Integer hv;
                Integer pv;
                IProject cpr;
                block40: {
                    cpr = iProjectArray[n];
                    pv = null;
                    hv = null;
                    try {
                        if (!cpr.isOpen()) break block40;
                        pv = ConfigContext.loadProjectVersion(cpr);
                        hv = ConfigContext.loadHostedVersion(cpr);
                    }
                    catch (Exception e1) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to get project compatibility version.");
                        break;
                    }
                }
                if (cpr.isOpen()) {
                    block41: {
                        if (pv == null || pv < Designer.getProjectVersion() && hv != null && hv == Designer.getHostedVersion()) {
                            if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Project Version", (String)("Project " + cpr.getName() + " is created with a previous version of the product and requires upgrade. You have to upgrade it or it will be closed. Do you want to upgrade it now?"))) {
                                try {
                                    ConfigContext.upgradeProject(cpr);
                                    break block41;
                                }
                                catch (Exception e) {
                                    try {
                                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to upgrade project " + cpr.getName() + " " + e));
                                        cpr.close(null);
                                        break block42;
                                    }
                                    catch (CoreException e1) {
                                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to close project " + cpr.getName() + " " + (Object)((Object)e1)));
                                        break;
                                    }
                                }
                            }
                            try {
                                cpr.close(null);
                                break block42;
                            }
                            catch (CoreException e) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to close project " + cpr.getName() + " " + (Object)((Object)e)));
                                break;
                            }
                        }
                    }
                    if ((hv == null || hv < Designer.getHostedVersion()) && pv != null && pv == Designer.getProjectVersion()) {
                        try {
                            ConfigContext.copyFileToProject(cpr, "xslt/Site/include/sitetran_host.xslt", "xslt/Site/include/sitetran_host.xslt", true);
                            ConfigContext.copyFileToProject(cpr, "configuration/project.properties", "configuration/" + cpr.getName() + ".properties", true);
                        }
                        catch (IOException e) {
                            try {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy sitetran_host.xslt to project " + cpr.getName() + " " + e));
                                cpr.close(null);
                                break block42;
                            }
                            catch (CoreException e1) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy sitetran_host.xslt to project " + cpr.getName() + " " + (Object)((Object)e1)));
                                break;
                            }
                        }
                        catch (CoreException e) {
                            try {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy sitetran_host.xslt to project " + cpr.getName() + " " + (Object)((Object)e)));
                                cpr.close(null);
                                break block42;
                            }
                            catch (CoreException e1) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy sitetran_host.xslt to project " + cpr.getName() + " " + (Object)((Object)e1)));
                                break;
                            }
                        }
                    }
                    if (hv != null && hv < Designer.getHostedVersion() && pv != null && pv < Designer.getProjectVersion()) {
                        try {
                            ConfigContext.copyFileToProject(cpr, "xslt/Site/new/transactions.xslt", "xslt/Site/new/transactions.xslt", true);
                            ConfigContext.copyFileToProject(cpr, "configuration/project.properties", "configuration/" + cpr.getName() + ".properties", true);
                        }
                        catch (IOException e) {
                            try {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy transactions.xslt to project " + cpr.getName() + " " + e));
                                cpr.close(null);
                                break block42;
                            }
                            catch (CoreException e1) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy transactions.xslt to project " + cpr.getName() + " " + (Object)((Object)e1)));
                                break;
                            }
                        }
                        catch (CoreException e) {
                            try {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy transactions.xslt to project " + cpr.getName() + " " + (Object)((Object)e)));
                                cpr.close(null);
                                break block42;
                            }
                            catch (CoreException e1) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to copy transactions.xslt to project " + cpr.getName() + " " + (Object)((Object)e1)));
                                break;
                            }
                        }
                    }
                }
                if ((xfn = ConfigContext.getXsltTransformers(cpr, false)) == null) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to create xslt list");
                    break;
                }
                String[] tfn = ConfigContext.getXsltTemplates(cpr, false);
                if (tfn == null) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to create template list");
                    break;
                }
                Object[] ctrs = ConfigContext.getTransactions(cpr, false);
                if (ctrs == null) {
                    if (!MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to create transaction list. Project " + cpr.getName() + " may be corrupted. Delete?"))) break;
                    try {
                        cpr.delete(false, null);
                    }
                    catch (CoreException e) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)("Unable to delete project " + cpr.getName() + " " + (Object)((Object)e)));
                    }
                    break;
                }
                Arrays.sort(ctrs);
                Object[] ccons = ConfigContext.getProjectConnectionNames(cpr, false);
                if (ccons == null) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to create connection list");
                    break;
                }
                Arrays.sort(ccons);
                if (!ConfigContext.readTSConfigContext(cpr)) {
                    return null;
                }
                if (!ConfigContext.readIMConfigContext(cpr)) {
                    return null;
                }
                Object[] trfls = ConfigContext.getTransactionFlows();
                Arrays.sort(trfls);
                Object[] qrfls = ConfigContext.getQueries();
                Arrays.sort(qrfls);
                TreeParent ctp = this.createProjectView(cpr.getName(), (String[])ctrs, xfn, tfn, (String[])trfls, (String[])qrfls, (String[])ccons, cpr.isOpen());
                root.addChild(ctp);
            }
            ++n;
        }
        if (spj != null) {
            if (!ConfigContext.readTSConfigContext(spj)) {
                return null;
            }
            if (!ConfigContext.readIMConfigContext(spj)) {
                return null;
            }
        }
        return root;
    }

    private boolean wasExpanded(TreeParent currentNode) {
        Object[] objectArray = this.expanedElements;
        int n = 0;
        int n2 = objectArray.length;
        while (n < n2) {
            Object ceo = objectArray[n];
            if (currentNode.getName().equals(((TreeParent)ceo).getName())) {
                TreeParent ceop = ((TreeParent)ceo).getParent();
                TreeParent cnp = currentNode.getParent();
                boolean ce = true;
                while (!ceop.getName().equals("") && !cnp.getName().equals("")) {
                    if (!ceop.getName().equals(cnp.getName())) {
                        ce = false;
                        break;
                    }
                    ceop = ceop.getParent();
                    cnp = cnp.getParent();
                }
                if (ce) {
                    return true;
                }
            }
            ++n;
        }
        return false;
    }

    private Object[] getExpanded(TreeParent root) {
        TreeObject[] pjts;
        ArrayList<TreeParent> neto = new ArrayList<TreeParent>();
        TreeObject[] treeObjectArray = pjts = root.getChildren();
        int n = 0;
        int n2 = treeObjectArray.length;
        while (n < n2) {
            TreeObject cpj = treeObjectArray[n];
            if (this.wasExpanded((TreeParent)cpj)) {
                TreeObject[] ccp;
                neto.add((TreeParent)cpj);
                TreeObject[] treeObjectArray2 = ccp = ((TreeParent)cpj).getChildren();
                int n3 = 0;
                int n4 = treeObjectArray2.length;
                while (n3 < n4) {
                    TreeObject cccp = treeObjectArray2[n3];
                    if (this.wasExpanded((TreeParent)cccp)) {
                        neto.add((TreeParent)cccp);
                        if (cccp.getName().equals(INTEGRATION_FLOWS)) {
                            TreeObject[] icp;
                            TreeObject[] treeObjectArray3 = icp = ((TreeParent)cccp).getChildren();
                            int n5 = 0;
                            int n6 = treeObjectArray3.length;
                            while (n5 < n6) {
                                TreeObject ticp = treeObjectArray3[n5];
                                if (this.wasExpanded((TreeParent)ticp)) {
                                    neto.add((TreeParent)ticp);
                                }
                                ++n5;
                            }
                        }
                    }
                    ++n3;
                }
            }
            ++n;
        }
        return neto.toArray();
    }

    private TreeParent createProjectView(String projectName, String[] transactionName, String[] transformer, String[] template, String[] transactionFlow, String[] query, String[] connection, boolean opened) {
        TreeParent p1 = new TreeParent(projectName);
        if (opened) {
            TreeObject ct;
            String s;
            int n;
            int n2;
            String[] stringArray;
            TreeParent tp1 = new TreeParent(CONFIGURATION);
            TreeParent tp2 = new TreeParent(TRANSACTIONS);
            TreeParent tp3 = new TreeParent(XSLT);
            TreeParent tp5 = new TreeParent(CONNECTIONS);
            TreeParent tp4 = new TreeParent(INTEGRATION_FLOWS);
            p1.addChild(tp1);
            p1.addChild(tp2);
            p1.addChild(tp5);
            p1.addChild(tp3);
            p1.addChild(tp4);
            TreeParent tp31 = new TreeParent(TEMPLATES);
            tp3.addChild(tp31);
            TreeParent tp41 = new TreeParent(TRANSACTION_FLOWS);
            TreeParent tp42 = new TreeParent(QUERIES);
            tp4.addChild(tp41);
            tp4.addChild(tp42);
            TreeObject to1 = new TreeObject(INTEGRATION_MANAGER);
            TreeObject to2 = new TreeObject(TRANSFORTMATION_SERVER);
            tp1.addChild(to1);
            tp1.addChild(to2);
            if (transactionName != null) {
                stringArray = transactionName;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp2.addChild(ct);
                    ++n2;
                }
            }
            if (transformer != null) {
                stringArray = transformer;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp3.addChild(ct);
                    ++n2;
                }
            }
            if (template != null) {
                stringArray = template;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp31.addChild(ct);
                    ++n2;
                }
            }
            if (connection != null) {
                stringArray = connection;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp5.addChild(ct);
                    ++n2;
                }
            }
            if (transactionFlow != null) {
                stringArray = transactionFlow;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp41.addChild(ct);
                    ++n2;
                }
            }
            if (query != null) {
                stringArray = query;
                n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    s = stringArray[n2];
                    ct = new TreeObject(s);
                    tp42.addChild(ct);
                    ++n2;
                }
            }
        }
        return p1;
    }

    public final TreeViewer getViewer() {
        return this.viewer;
    }

    public void createPartControl(Composite parent) {
        this.viewer = new TreeViewer(parent, 2818);
        this.viewer.addSelectionChangedListener(new ISelectionChangedListener(){

            public void selectionChanged(SelectionChangedEvent e) {
            }
        });
        this.tree = this.viewer.getTree();
        Menu menu = new Menu((Control)this.tree);
        this.tree.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.setImage(Iw_sdkPlugin.getImageDescriptor("icons/wizard.gif").createImage());
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                ActionFactory.NEW.create(NavigationView.this.getSite().getWorkbenchWindow()).run();
            }
        });
        newMenuItem.setText("New");
        MenuItem openMenuItem = new MenuItem(menu, 0);
        openMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.processTreeSelection((IStructuredSelection)((StructuredSelection)NavigationView.this.viewer.getSelection()));
            }
        });
        openMenuItem.setText("Open");
        MenuItem openWithMenuItem = new MenuItem(menu, 64);
        openWithMenuItem.setText("Open With...");
        Menu menu_1 = new Menu(openWithMenuItem);
        openWithMenuItem.setMenu(menu_1);
        MenuItem systemEditorMenuItem = new MenuItem(menu_1, 0);
        systemEditorMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.processTreeSelection((IStructuredSelection)((StructuredSelection)NavigationView.this.viewer.getSelection()), true);
            }
        });
        systemEditorMenuItem.setText("System Editor");
        MenuItem defaultEditorMenuItem = new MenuItem(menu_1, 0);
        defaultEditorMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.processTreeSelection((IStructuredSelection)((StructuredSelection)NavigationView.this.viewer.getSelection()));
            }
        });
        defaultEditorMenuItem.setText("Default Editor");
        new MenuItem(menu, 2);
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.setEnabled(false);
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(menu, 0);
        copyMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.copyElement();
            }
        });
        copyMenuItem.setEnabled(true);
        copyMenuItem.setText("Copy");
        this.pasteMenuItem = new MenuItem(menu, 0);
        this.pasteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.pasteElement();
            }
        });
        this.pasteMenuItem.setEnabled(ConfigContext.pasteEnabled());
        this.pasteMenuItem.setText("Paste");
        new MenuItem(menu, 2);
        MenuItem copyFlowsqueriesMenuItem = new MenuItem(menu, 0);
        copyFlowsqueriesMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.moveElement(true);
            }
        });
        copyFlowsqueriesMenuItem.setText("Copy Flows/Queries");
        MenuItem moveMenuItem = new MenuItem(menu, 0);
        moveMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.moveElement(false);
            }
        });
        moveMenuItem.setText("Move Flows/Queries");
        this.cloneTransactionsMenuItem = new MenuItem(menu, 0);
        this.cloneTransactionsMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.cloneTransactions();
            }
        });
        this.cloneTransactionsMenuItem.setEnabled(false);
        this.cloneTransactionsMenuItem.setText("Clone Transactions");
        new MenuItem(menu, 2);
        MenuItem deleteMenuItem = new MenuItem(menu, 0);
        deleteMenuItem.setImage(Iw_sdkPlugin.getImageDescriptor("icons/delete.gif").createImage());
        deleteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.deleteElement();
            }
        });
        deleteMenuItem.setText("Delete");
        deleteMenuItem.setImage(Iw_sdkPlugin.getImageDescriptor("icons/delete.gif").createImage());
        this.renameMenuItem = new MenuItem(menu, 0);
        this.renameMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.renameElement();
            }
        });
        this.renameMenuItem.setEnabled(false);
        this.renameMenuItem.setText("Rename");
        MenuItem actvateMenuItem = new MenuItem(menu, 0);
        actvateMenuItem.setEnabled(false);
        actvateMenuItem.setText("Actvate");
        new MenuItem(menu, 2);
        MenuItem importMenuItem = new MenuItem(menu, 0);
        importMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                ActionFactory.IMPORT.create(NavigationView.this.getSite().getWorkbenchWindow()).run();
            }
        });
        importMenuItem.setText("Import");
        MenuItem exportMenuItem = new MenuItem(menu, 0);
        exportMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                ActionFactory.EXPORT.create(NavigationView.this.getSite().getWorkbenchWindow()).run();
            }
        });
        exportMenuItem.setText("Export");
        new MenuItem(menu, 2);
        MenuItem refreshMenuItem = new MenuItem(menu, 0);
        refreshMenuItem.setImage(Iw_sdkPlugin.getImageDescriptor("icons/refresh.gif").createImage());
        refreshMenuItem.setImage(Iw_sdkPlugin.getImageDescriptor("icons/refresh.gif").createImage());
        refreshMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NavigationView.this.refreshAction.run();
            }
        });
        refreshMenuItem.setText("Refresh");
        this.viewer.addTreeListener(new ITreeViewerListener(){

            public void treeExpanded(TreeExpansionEvent e) {
                StructuredSelection ss = new StructuredSelection(e.getElement());
                e.getTreeViewer().setSelection((ISelection)ss);
                NavigationView.this.processTreeSelection((IStructuredSelection)ss);
            }

            public void treeCollapsed(TreeExpansionEvent e) {
            }
        });
        this.viewer.addDoubleClickListener(new IDoubleClickListener(){

            public void doubleClick(DoubleClickEvent e) {
                if (!NavigationView.this.processSelection) {
                    return;
                }
                IStructuredSelection selection = (IStructuredSelection)e.getSelection();
                NavigationView.this.processTreeSelection(selection);
            }
        });
        this.viewer.setContentProvider((IContentProvider)new ViewContentProvider());
        this.viewer.setLabelProvider((IBaseLabelProvider)new ViewLabelProvider());
        this.setAndSelectViewer();
        this.createActions();
        this.initializeToolBar();
    }

    private void processTreeSelection(IStructuredSelection selection) {
        this.processTreeSelection(selection, false);
    }

    private void processTreeSelection(IStructuredSelection selection, boolean systemEditor) {
        this.cloneTransactionsMenuItem.setEnabled(false);
        if (selection == null) {
            return;
        }
        TreeObject sto = (TreeObject)selection.getFirstElement();
        if (sto == null) {
            return;
        }
        this.selectedObject = sto;
        TreeObject ston = sto;
        this.projectSelected = true;
        while (true) {
            TreeParent spo;
            if ((spo = ston.getParent()) == null) {
                this.projectSelected = false;
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate active project");
                break;
            }
            if (spo.getName().equals("")) {
                IProject pr = this.getProjectByName(ston);
                if (pr != null) {
                    IProject sp = Designer.getSelectedProject();
                    boolean ucbd = sp == null || !pr.getName().equals(sp.getName());
                    Designer.setSelectedProject(pr);
                    if (ucbd) {
                        this.setAndSelectViewer();
                    }
                } else {
                    this.projectSelected = false;
                    if (!demoMode) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate active project or project view is out of synch");
                        return;
                    }
                }
                if (!spo.equals(ston)) break;
                return;
            }
            this.projectSelected = false;
            ston = spo;
        }
        IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IPerspectiveDescriptor cps = null;
        if (ap != null) {
            cps = ap.getPerspective();
        }
        this.xsltSelected = false;
        this.templateSelected = false;
        this.transactionSelected = false;
        this.transactionFlowSelected = false;
        this.querySelected = false;
        this.connectionSelected = false;
        if (sto.getName().equals(INTEGRATION_MANAGER) || sto.getName().equals(CONFIGURATION) || sto.getName().equals(TRANSFORTMATION_SERVER)) {
            if (!(systemEditor || cps != null && cps.getId().equals("iw_sdk.config_perspective"))) {
                ap.setPerspective(PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("iw_sdk.config_perspective"));
            }
            try {
                if (!systemEditor) {
                    ((ConfigBDView)ap.findView("iw_sdk.ConfigBDView")).initializeScreen();
                    ((ConfigTSView)ap.findView("iw_sdk.ConfigTSView")).initializeScreen();
                }
                if (sto.getName().equals(INTEGRATION_MANAGER)) {
                    if (systemEditor) {
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"System Editor", (String)"Incorrect editing of config.xml file may seriously damage the project upto complete corruption! Do you want to edit config.xml?")) {
                            ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("configuration/im/config.xml")), "org.eclipse.ui.systemExternalEditor");
                        }
                    } else {
                        ap.showView("iw_sdk.ConfigBDView");
                    }
                } else if (sto.getName().equals(TRANSFORTMATION_SERVER)) {
                    if (systemEditor) {
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"System Editor", (String)"Incorrect editing of config.xml file may seriously damage the project upto complete corruption! Do you want to edit config.xml?")) {
                            ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("configuration/ts/config.xml")), "org.eclipse.ui.systemExternalEditor");
                        }
                    } else {
                        ap.showView("iw_sdk.ConfigTSView");
                    }
                }
            }
            catch (PartInitException e1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show IM config view " + e1.toString()));
                return;
            }
            return;
        }
        if (sto.getName().equals(XSLT) || sto.getParent().getName().equals(XSLT) || sto.getParent().getName().equals(TEMPLATES)) {
            block58: {
                if (!(systemEditor || cps != null && cps.getId().equals("iw_sdk.te_perspective"))) {
                    ap.setPerspective(PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("iw_sdk.te_perspective"));
                }
                if (!sto.getName().equals(XSLT) && !sto.getName().equals(TEMPLATES)) {
                    if (sto.getParent().getName().equals(XSLT)) {
                        this.xsltSelected = true;
                        ConfigContext.setCurrentXsltName(sto.getName());
                        try {
                            if (systemEditor) {
                                ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/" + sto.getName().trim() + ".xslt")), "org.eclipse.ui.systemExternalEditor");
                                break block58;
                            }
                            ap.showView("iw_sdk.XSLTEditorView");
                            ((XSLTEditorView)ap.findView("iw_sdk.XSLTEditorView")).initializeScreen();
                        }
                        catch (PartInitException e1) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show XSLT Editor view " + e1.toString()));
                        }
                    } else {
                        this.templateSelected = true;
                        ConfigContext.setCurrentTemplateName(sto.getName());
                        try {
                            if (systemEditor) {
                                ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/Site/" + sto.getName().trim() + ".iwxt")), "org.eclipse.ui.systemExternalEditor");
                            } else {
                                ap.showView("iw_sdk.IwTemplateEditor");
                                ((TemplateEditorView)ap.findView("iw_sdk.IwTemplateEditor")).initializeScreen();
                            }
                        }
                        catch (PartInitException e1) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show Template Editor view " + e1.toString()));
                        }
                    }
                }
            }
            return;
        }
        if (sto.getParent().getName().equals("")) {
            return;
        }
        if (!(systemEditor || cps != null && cps.getId().equals("iw_sdk.tf_perspective"))) {
            ap.setPerspective(PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("iw_sdk.tf_perspective"));
        }
        if (sto.getParent().getName().equals(TRANSACTIONS)) {
            this.transactionSelected = true;
            ConfigContext.setCurrentTransactionName(sto.getName());
            try {
                if (systemEditor) {
                    if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"System Editor", (String)"Incorrect editing of transactions.xml file may seriously damage the project upto complete corruption! Do you want to edit transaction.xml?")) {
                        ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/Site/new/xml/transactions.xml")), "org.eclipse.ui.systemExternalEditor");
                    }
                } else {
                    ap.showView("iw_sdk.TransactionDetailsView");
                    ((TransactionDetailsView)ap.findView("iw_sdk.TransactionDetailsView")).initializeScreen();
                }
            }
            catch (PartInitException e1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show Transaction Details view " + e1.toString()));
            }
            return;
        }
        if (sto.getParent().getName().equals(CONNECTIONS)) {
            this.connectionSelected = true;
            ConfigContext.setCurrentConnectionName(sto.getName());
            try {
                if (systemEditor) {
                    if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"System Editor", (String)"Incorrect editing of transactions.xml file may seriously damage the project upto complete corruption! Do you want to edit config.xml?")) {
                        ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("xslt/Site/new/xml/transactions.xml")), "org.eclipse.ui.systemExternalEditor");
                    }
                } else {
                    ap.showView("iw_sdk.ConnectionView");
                    ((ConnectionView)ap.findView("iw_sdk.ConnectionView")).initializeScreen(false);
                }
            }
            catch (PartInitException e1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show Transaction Details view " + e1.toString()));
            }
            return;
        }
        if (sto.getParent().getName().equals(TRANSACTION_FLOWS) || sto.getParent().getName().equals(QUERIES)) {
            this.cloneTransactionsMenuItem.setEnabled(true);
            if (sto.getParent().getName().equals(TRANSACTION_FLOWS)) {
                this.transactionFlowSelected = true;
                ConfigContext.setCurrentTransactionFlowId(sto.getName());
                ConfigContext.setCurrentQueryId(null);
            } else {
                this.querySelected = true;
                ConfigContext.setCurrentTransactionFlowId(null);
                ConfigContext.setCurrentQueryId(sto.getName());
            }
            try {
                if (systemEditor) {
                    if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"System Editor", (String)"Incorrect editing of config.xml file may seriously damage the project upto complete corruption! Do you want to edit config.xml?")) {
                        ap.openEditor((IEditorInput)new FileEditorInput(Designer.getSelectedProject().getFile("configuration/im/config.xml")), "org.eclipse.ui.systemExternalEditor");
                    }
                } else {
                    ap.showView("iw_sdk.TransactionFlowView");
                    ((TransactionFlowView)ap.findView("iw_sdk.TransactionFlowView")).initializeScreen();
                }
            }
            catch (PartInitException e1) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Plugin Error", (String)("Unable to show Transaction Flow view " + e1.toString()));
            }
            return;
        }
    }

    private void initializeViews(IWorkbenchPage ap) {
        if (ap != null) {
            ConfigBDView cdbv = (ConfigBDView)ap.findView("iw_sdk.ConfigBDView");
            ConfigTSView ctsv = (ConfigTSView)ap.findView("iw_sdk.ConfigTSView");
            if (cdbv != null) {
                cdbv.initializeScreen();
            } else if (Designer.getSelectedProject() != null && !ConfigContext.readIMConfigContext()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Read Error", (String)"Unable to read IM configuration ");
            }
            if (ctsv != null) {
                ctsv.initializeScreen();
            }
        } else if (Designer.getSelectedProject() != null && !ConfigContext.readIMConfigContext()) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Read Error", (String)"Unable to read IM configuration ");
        }
    }

    public IProject getProjectByName(TreeObject sto) {
        return ConfigContext.getProjectByName(sto.getName().trim());
    }

    public IProject getActiveProject() {
        return null;
    }

    public void setFocus() {
        this.viewer.getControl().setFocus();
    }

    private void deleteElement() {
        block57: {
            try {
                StructuredSelection selection = (StructuredSelection)this.viewer.getSelection();
                int selSize = selection.size();
                if (selSize == 1 && selection.getFirstElement().equals(this.selectedObject)) {
                    if (this.projectSelected) {
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Project Deletion", (String)("Are you sure you want to delete project \"" + Designer.getSelectedProject().getName() + "\"?"))) {
                            Designer.getSelectedProject().delete(true, false, null);
                            Designer.setSelectedProject(null);
                            this.selectedObject = null;
                        }
                        break block57;
                    }
                    if (this.transactionSelected) {
                        try {
                            String ct = ConfigContext.getCurrentTransaction().getname().getValue();
                            if (!MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Transaction Deletion", (String)("Are you sure you want to delete transaction \"" + ct + "\"?"))) break block57;
                            int ti = ConfigContext.getTransactionIndex(ct);
                            transactionType tt = ConfigContext.getIwmappingsRoot().gettransactionAt(ti);
                            if (ConfigContext.isTransactionUsed(tt, Designer.getSelectedProject())) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Used Transaction!");
                                break block57;
                            }
                            ConfigContext.getIwmappingsRoot().removetransactionAt(ti);
                            ConfigContext.saveTransactions();
                            this.selectedObject = null;
                            ConfigContext.setCurrentTransactionName(null);
                        }
                        catch (Exception e) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)("Unable to delete Transaction: " + e.toString()));
                        }
                        break block57;
                    }
                    if (this.transactionFlowSelected) {
                        String ct = ConfigContext.getCurrentTransactionFlowId();
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Transaction Flow Deletion", (String)("Are you sure you want to delete Transaction Flow \"" + ct + "\"?"))) {
                            for (TransactionContext ctx : ConfigContext.getTransactionList()) {
                                if (!ctx.getTransactionId().equals(ct)) continue;
                                ConfigContext.getTransactionList().remove(ctx);
                                break;
                            }
                            if (!ConfigContext.writeIMConfigContext()) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Transaction Flow");
                            } else {
                                this.selectedObject = null;
                                ConfigContext.setCurrentTransactionFlowId(null);
                            }
                        }
                        break block57;
                    }
                    if (this.querySelected) {
                        String ct = ConfigContext.getCurrentQueryId();
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete Query \"" + ct + "\"?"))) {
                            for (QueryContext ctx : ConfigContext.getQueryList()) {
                                if (!ctx.getTransactionId().equals(ct)) continue;
                                ConfigContext.getQueryList().remove(ctx);
                                break;
                            }
                            if (!ConfigContext.writeIMConfigContext()) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Query");
                            } else {
                                ConfigContext.setCurrentQueryId(null);
                                this.selectedObject = null;
                            }
                        }
                        break block57;
                    }
                    if (this.connectionSelected) {
                        MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Connections can not be deleted.");
                        break block57;
                    }
                    if (this.xsltSelected) {
                        String ct = String.valueOf(ConfigContext.getCurrentXsltName()) + ".xslt";
                        if (!MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete transformer \"" + ct + "\"?"))) break block57;
                        IFile dt = Designer.getSelectedProject().getFile("xslt/" + ct);
                        if (!dt.exists()) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete non-existing transformer");
                            break block57;
                        }
                        try {
                            if (ConfigContext.isXSLTUsed(dt, Designer.getSelectedProject())) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete used transformer");
                                break block57;
                            }
                            dt.delete(true, null);
                            this.selectedObject = null;
                            ConfigContext.setCurrentXsltName(null);
                        }
                        catch (Exception e) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)("Unable to delete used transformer: " + e.getMessage()));
                        }
                        break block57;
                    }
                    if (this.templateSelected) {
                        String ct = String.valueOf(ConfigContext.getCurrentTemplateName()) + ".iwxt";
                        if (MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete template \"" + ct + "\"?"))) {
                            IFile dt = Designer.getSelectedProject().getFile("xslt/Site/" + ct);
                            if (!dt.exists()) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete template");
                            } else {
                                dt.delete(true, null);
                                this.selectedObject = null;
                                ConfigContext.setCurrentTemplateName(null);
                            }
                        }
                    }
                    break block57;
                }
                boolean w2d = true;
                if (selSize > 1) {
                    w2d = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)"Are you sure you want to delete all these elements?");
                }
                if (w2d) {
                    Iterator sit = selection.iterator();
                    IProject csp = Designer.getSelectedProject();
                    while (sit.hasNext()) {
                        boolean w2dtf;
                        boolean w2dx;
                        String ct;
                        TreeObject sto = (TreeObject)sit.next();
                        if (sto == null) continue;
                        TreeObject ston = sto;
                        boolean localProjectSelected = true;
                        IProject pr = null;
                        while (true) {
                            TreeParent spo;
                            if ((spo = ston.getParent()) == null) {
                                localProjectSelected = false;
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate project");
                                break;
                            }
                            if (spo.getName().equals("")) {
                                pr = this.getProjectByName(ston);
                                break;
                            }
                            localProjectSelected = false;
                            ston = spo;
                        }
                        if (pr == null) continue;
                        if (!pr.equals((Object)csp)) {
                            ConfigContext.loadIwmappingsRoot(pr);
                            ConfigContext.readIMConfigContext(pr);
                            csp = pr;
                        }
                        if (localProjectSelected) {
                            boolean w2dp = true;
                            if (selSize == 1) {
                                w2dp = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Project Deletion", (String)("Are you sure you want to delete project \"" + pr.getName() + "\"?"));
                            }
                            if (!w2dp) continue;
                            pr.delete(true, false, null);
                            continue;
                        }
                        if (sto.getName().equals(INTEGRATION_MANAGER) || sto.getName().equals(CONFIGURATION) || sto.getName().equals(TRANSFORTMATION_SERVER) || sto.getName().equals(XSLT)) continue;
                        if (sto.getParent().getName().equals(XSLT)) {
                            ct = String.valueOf(sto.getName()) + ".xslt";
                            w2dx = true;
                            if (selSize == 1) {
                                w2dx = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete transformer \"" + ct + "\"?"));
                            }
                            if (!w2dx) continue;
                            IFile dt = pr.getFile("xslt/" + ct);
                            if (!dt.exists()) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete transformer");
                                continue;
                            }
                            try {
                                if (ConfigContext.isXSLTUsed(dt, pr)) {
                                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete used transformer");
                                    continue;
                                }
                                dt.delete(true, null);
                            }
                            catch (Exception e) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)("Unable to delete transformer: " + e.getMessage()));
                            }
                            continue;
                        }
                        if (sto.getParent().getName().equals(TEMPLATES)) {
                            ct = String.valueOf(sto.getName()) + ".iwxt";
                            w2dx = true;
                            if (selSize == 1) {
                                w2dx = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete template \"" + ct + "\"?"));
                            }
                            if (!w2dx) continue;
                            IFile dt = pr.getFile("xslt/Site/" + ct);
                            if (!dt.exists()) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete template");
                                continue;
                            }
                            dt.delete(true, null);
                            continue;
                        }
                        if (sto.getParent().getName().equals("")) continue;
                        if (sto.getParent().getName().equals(TRANSACTIONS)) {
                            try {
                                ct = sto.getName();
                                boolean w2dtr = true;
                                if (selSize == 1) {
                                    w2dtr = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Transaction Deletion", (String)("Are you sure you want to delete transaction \"" + ct + "\"?"));
                                }
                                if (!w2dtr) continue;
                                int ti = ConfigContext.getTransactionIndex(ct);
                                transactionType tt = ConfigContext.getIwmappingsRoot().gettransactionAt(ti);
                                if (ConfigContext.isTransactionUsed(tt, pr)) {
                                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Used Transaction!");
                                    continue;
                                }
                                ConfigContext.getIwmappingsRoot().removetransactionAt(ti);
                                ConfigContext.saveTransactions(pr);
                            }
                            catch (Exception e) {
                                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)("Unable to delete Transaction: " + e.toString()));
                            }
                            continue;
                        }
                        if (sto.getParent().getName().equals(CONNECTIONS)) continue;
                        if (sto.getParent().getName().equals(TRANSACTION_FLOWS)) {
                            ct = sto.getName();
                            w2dtf = true;
                            if (selSize == 1) {
                                w2dtf = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Transaction Flow Deletion", (String)("Are you sure you want to delete Transaction Flow \"" + ct + "\"?"));
                            }
                            if (!w2dtf) continue;
                            for (TransactionContext ctx : ConfigContext.getTransactionList()) {
                                if (!ctx.getTransactionId().equals(ct)) continue;
                                ConfigContext.getTransactionList().remove(ctx);
                                break;
                            }
                            if (ConfigContext.writeIMConfigContext(pr)) continue;
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Transaction Flow");
                            continue;
                        }
                        if (!sto.getParent().getName().equals(QUERIES)) continue;
                        ct = sto.getName();
                        w2dtf = true;
                        if (selSize == 1) {
                            w2dtf = MessageDialog.openQuestion((Shell)this.getSite().getShell(), (String)"Query Deletion", (String)("Are you sure you want to delete Query \"" + ct + "\"?"));
                        }
                        if (!w2dtf) continue;
                        for (QueryContext ctx : ConfigContext.getQueryList()) {
                            if (!ctx.getTransactionId().equals(ct)) continue;
                            ConfigContext.getQueryList().remove(ctx);
                            break;
                        }
                        if (ConfigContext.writeIMConfigContext(pr)) continue;
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)"Unable to delete Query");
                    }
                }
            }
            catch (CoreException e) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Deletion Error", (String)("Unable to delete project or its element: " + e.toString()));
            }
        }
        this.setAndSelectViewer();
    }

    private void copyElement() {
        ConfigContext.cleanClipboard();
        StructuredSelection selection = (StructuredSelection)this.viewer.getSelection();
        int selSize = selection.size();
        if (selSize == 1 && selection.getFirstElement().equals(this.selectedObject)) {
            if (this.projectSelected) {
                ArrayList<IProject> c = ConfigContext.getPrClp();
                c.add(Designer.getSelectedProject());
                ConfigContext.setPrClp(c);
            } else if (this.transactionSelected) {
                ArrayList<transactionType> c = ConfigContext.getTrtClp();
                c.add(ConfigContext.cloneTransaction(ConfigContext.getCurrentTransaction()));
                ConfigContext.setTrtClp(c);
            } else if (this.transactionFlowSelected) {
                ArrayList<TransactionContext> c = ConfigContext.getTcClp();
                c.add((TransactionContext)ConfigContext.getCurrentFlow());
                ConfigContext.setTcClp(c);
            } else if (this.querySelected) {
                ArrayList<QueryContext> c = ConfigContext.getQcClp();
                c.add((QueryContext)ConfigContext.getCurrentFlow());
                ConfigContext.setQcClp(c);
            } else if (this.connectionSelected) {
                MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Connections can not be copied.");
            } else if (this.xsltSelected) {
                ArrayList<IFile> c = ConfigContext.getXsltClp();
                c.add((IFile)((IFolder)Designer.getSelectedProject().findMember("xslt")).findMember(String.valueOf(ConfigContext.getCurrentXsltName()) + ".xslt"));
                ConfigContext.setXsltClp(c);
            } else if (this.templateSelected) {
                ArrayList<IFile> c = ConfigContext.getTemplateClp();
                c.add((IFile)((IFolder)Designer.getSelectedProject().findMember("xslt/Site")).findMember(String.valueOf(ConfigContext.getCurrentTemplateName()) + ".iwxt"));
                ConfigContext.setTemplateClp(c);
            }
        } else {
            Iterator sit = selection.iterator();
            IProject csp = Designer.getSelectedProject();
            while (sit.hasNext()) {
                ArrayList<Object> c;
                TreeObject sto = (TreeObject)sit.next();
                if (sto == null) continue;
                TreeObject ston = sto;
                boolean localProjectSelected = true;
                IProject pr = null;
                while (true) {
                    TreeParent spo;
                    if ((spo = ston.getParent()) == null) {
                        localProjectSelected = false;
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate project");
                        break;
                    }
                    if (spo.getName().equals("")) {
                        pr = this.getProjectByName(ston);
                        break;
                    }
                    localProjectSelected = false;
                    ston = spo;
                }
                if (pr == null) continue;
                if (!pr.equals((Object)csp)) {
                    ConfigContext.loadIwmappingsRoot(pr);
                    ConfigContext.readIMConfigContext(pr);
                    csp = pr;
                }
                if (localProjectSelected) {
                    c = ConfigContext.getPrClp();
                    c.add(pr);
                    ConfigContext.setPrClp(c);
                    continue;
                }
                if (sto.getName().equals(INTEGRATION_MANAGER) || sto.getName().equals(CONFIGURATION) || sto.getName().equals(TRANSFORTMATION_SERVER) || sto.getName().equals(XSLT) || sto.getName().equals(TEMPLATES)) continue;
                if (sto.getParent().getName().equals(XSLT)) {
                    c = ConfigContext.getXsltClp();
                    c.add((IProject)((IFile)((IFolder)pr.findMember("xslt")).findMember(String.valueOf(sto.getName()) + ".xslt")));
                    ConfigContext.setXsltClp(c);
                    continue;
                }
                if (sto.getParent().getName().equals(TEMPLATES)) {
                    c = ConfigContext.getTemplateClp();
                    c.add((IProject)((IFile)((IFolder)pr.findMember("xslt/Site")).findMember(String.valueOf(sto.getName()) + ".iwxt")));
                    ConfigContext.setTemplateClp(c);
                    continue;
                }
                if (sto.getParent().getName().equals("")) continue;
                if (sto.getParent().getName().equals(TRANSACTIONS)) {
                    c = ConfigContext.getTrtClp();
                    c.add((IProject)ConfigContext.cloneTransaction(ConfigContext.getCurrentTransaction(sto.getName())));
                    ConfigContext.setTrtClp(c);
                    continue;
                }
                if (sto.getParent().getName().equals(CONNECTIONS)) continue;
                if (sto.getParent().getName().equals(TRANSACTION_FLOWS)) {
                    c = ConfigContext.getTcClp();
                    c.add(ConfigContext.getTransactionFlow(sto.getName()));
                    ConfigContext.setTcClp(c);
                    continue;
                }
                if (!sto.getParent().getName().equals(QUERIES)) continue;
                c = ConfigContext.getQcClp();
                c.add(ConfigContext.getQuery(sto.getName()));
                ConfigContext.setQcClp(c);
            }
            IProject cpr = Designer.getSelectedProject();
            if (!cpr.equals((Object)csp)) {
                ConfigContext.loadIwmappingsRoot(cpr);
                ConfigContext.readIMConfigContext();
            }
        }
        this.pasteMenuItem.setEnabled(ConfigContext.pasteEnabled());
    }

    private void moveElement(boolean copy) {
        block28: {
            IProject csp;
            StructuredSelection selection;
            block26: {
                block33: {
                    block32: {
                        block31: {
                            block30: {
                                block29: {
                                    block27: {
                                        ConfigContext.cleanClipboard();
                                        selection = (StructuredSelection)this.viewer.getSelection();
                                        int selSize = selection.size();
                                        csp = Designer.getSelectedProject();
                                        ArrayList<IProject> p = ConfigContext.getPrClp();
                                        p.add(csp);
                                        ConfigContext.setPrClp(p);
                                        if (selSize != 1 || !selection.getFirstElement().equals(this.selectedObject)) break block26;
                                        if (!this.projectSelected) break block27;
                                        MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Projects can not be moved.");
                                        break block28;
                                    }
                                    if (!this.transactionSelected) break block29;
                                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Transactions can not be moved.");
                                    break block28;
                                }
                                if (!this.transactionFlowSelected) break block30;
                                ArrayList<TransactionContext> c = copy ? ConfigContext.getTcClpCopy() : ConfigContext.getTcClpMove();
                                c.add((TransactionContext)ConfigContext.getCurrentFlow());
                                if (copy) {
                                    ConfigContext.setTcClpCopy(c);
                                } else {
                                    ConfigContext.setTcClpMove(c);
                                }
                                try {
                                    this.copyFlowContext(ConfigContext.getCurrentFlow().getTransactionId(), true);
                                }
                                catch (Exception e) {
                                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Unable to move Transaction Flow Context.");
                                }
                                break block28;
                            }
                            if (!this.querySelected) break block31;
                            ArrayList<QueryContext> c = copy ? ConfigContext.getQcClpCopy() : ConfigContext.getQcClpMove();
                            c.add((QueryContext)ConfigContext.getCurrentFlow());
                            if (copy) {
                                ConfigContext.setQcClpCopy(c);
                            } else {
                                ConfigContext.setQcClpMove(c);
                            }
                            try {
                                this.copyFlowContext(ConfigContext.getCurrentFlow().getTransactionId(), false);
                            }
                            catch (Exception e) {
                                MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Unable to move Transaction Flow Context.");
                            }
                            break block28;
                        }
                        if (!this.connectionSelected) break block32;
                        MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Connections can not be moved.");
                        break block28;
                    }
                    if (!this.xsltSelected) break block33;
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"XSLT can not be moved.");
                    break block28;
                }
                if (!this.templateSelected) break block28;
                MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Templates can not be moved.");
                break block28;
            }
            for (TreeObject sto : selection) {
                ArrayList<TransactionBase> c;
                if (sto == null) continue;
                TreeObject ston = sto;
                boolean localProjectSelected = true;
                IProject pr = null;
                while (true) {
                    TreeParent spo;
                    if ((spo = ston.getParent()) == null) {
                        localProjectSelected = false;
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate project");
                        break;
                    }
                    if (spo.getName().equals("")) {
                        pr = this.getProjectByName(ston);
                        break;
                    }
                    localProjectSelected = false;
                    ston = spo;
                }
                if (pr == null) continue;
                if (!pr.equals((Object)csp)) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to move from non-current project");
                    continue;
                }
                if (localProjectSelected) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Projects can not be moved.");
                    continue;
                }
                if (sto.getName().equals(INTEGRATION_MANAGER) || sto.getName().equals(CONFIGURATION) || sto.getName().equals(TRANSFORTMATION_SERVER) || sto.getName().equals(XSLT) || sto.getName().equals(TEMPLATES)) continue;
                if (sto.getParent().getName().equals(XSLT)) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"XSLT can not be moved.");
                    continue;
                }
                if (sto.getParent().getName().equals(TEMPLATES)) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Templates can not be moved.");
                    continue;
                }
                if (sto.getParent().getName().equals("")) continue;
                if (sto.getParent().getName().equals(TRANSACTIONS)) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Transactions can not be moved.");
                    continue;
                }
                if (sto.getParent().getName().equals(CONNECTIONS)) continue;
                if (sto.getParent().getName().equals(TRANSACTION_FLOWS)) {
                    c = copy ? ConfigContext.getTcClpCopy() : ConfigContext.getTcClpMove();
                    c.add(ConfigContext.getTransactionFlow(sto.getName()));
                    if (copy) {
                        ConfigContext.setTcClpCopy(c);
                    } else {
                        ConfigContext.setTcClpMove(c);
                    }
                    try {
                        this.copyFlowContext(sto.getName(), true);
                    }
                    catch (Exception e) {
                        MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Unable to move Transaction Flow Context.");
                    }
                    continue;
                }
                if (!sto.getParent().getName().equals(QUERIES)) continue;
                c = copy ? ConfigContext.getQcClpCopy() : ConfigContext.getQcClpMove();
                c.add((TransactionContext)((Object)ConfigContext.getQuery(sto.getName())));
                if (copy) {
                    ConfigContext.setQcClpCopy(c);
                } else {
                    ConfigContext.setQcClpMove(c);
                }
                try {
                    this.copyFlowContext(sto.getName(), false);
                }
                catch (Exception e) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Unable to move Transaction Flow Context.");
                }
            }
        }
        this.pasteMenuItem.setEnabled(ConfigContext.pasteEnabled());
    }

    private void copyFlowContext(String flowId, boolean transactionFlow) throws Exception {
        String cflid = ConfigContext.getCurrentTransactionFlowId();
        String cqid = ConfigContext.getCurrentQueryId();
        if (transactionFlow) {
            ConfigContext.setCurrentTransactionFlowId(flowId);
            ConfigContext.setCurrentQueryId(null);
        } else {
            ConfigContext.setCurrentTransactionFlowId(null);
            ConfigContext.setCurrentQueryId(flowId);
        }
        transactionType[] tft = ConfigContext.getFlowTransactions();
        if (tft != null) {
            transactionType[] transactionTypeArray = tft;
            int n = 0;
            int n2 = transactionTypeArray.length;
            while (n < n2) {
                transactionType ct = transactionTypeArray[n];
                ArrayList<transactionType> c = ConfigContext.getTrtClp();
                if (!c.contains(ct)) {
                    c.add(ConfigContext.cloneTransaction(ct));
                    ConfigContext.setTrtClp(c);
                    int i = 0;
                    while (i < ct.getdatamapCount()) {
                        accessType at;
                        datamapType dm = ct.getdatamapAt(i);
                        if (dm.getaccessCount() > 0 && (at = dm.getaccess()).gettranslatorCount() > 0) {
                            IFile inf;
                            ArrayList<IFile> f;
                            String xsi;
                            translatorType tt = at.gettranslator();
                            if (tt.getinputclassCount() > 0 && (xsi = tt.getinputclass().getValue().trim()).length() > 0 && !(f = ConfigContext.getXsltClp()).contains(inf = (IFile)((IFolder)Designer.getSelectedProject().findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                                f.add(inf);
                                ConfigContext.setXsltClp(f);
                            }
                            if (tt.getoutputclassCount() > 0 && (xsi = tt.getoutputclass().getValue().trim()).length() > 0 && !(f = ConfigContext.getXsltClp()).contains(inf = (IFile)((IFolder)Designer.getSelectedProject().findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                                f.add(inf);
                                ConfigContext.setXsltClp(f);
                            }
                        }
                        ++i;
                    }
                }
                ++n;
            }
        }
        ConfigContext.setCurrentTransactionFlowId(cflid);
        ConfigContext.setCurrentQueryId(cqid);
    }

    private void renameElement() {
        StructuredSelection selection = (StructuredSelection)this.viewer.getSelection();
        if (selection.size() != 1) {
            return;
        }
        if (selection.getFirstElement().equals(this.selectedObject)) {
            if (!(this.projectSelected || this.transactionSelected || this.transactionFlowSelected || this.querySelected)) {
                if (this.connectionSelected) {
                    MessageDialog.openInformation((Shell)this.getSite().getShell(), (String)"Navigation View", (String)"Connections can not be renamed.");
                } else if (!this.xsltSelected) {
                    // empty if block
                }
            }
        } else {
            Iterator sit = selection.iterator();
            IProject csp = Designer.getSelectedProject();
            while (sit.hasNext()) {
                TreeObject sto = (TreeObject)sit.next();
                if (sto == null) continue;
                TreeObject ston = sto;
                boolean localProjectSelected = true;
                IProject pr = null;
                while (true) {
                    TreeParent spo;
                    if ((spo = ston.getParent()) == null) {
                        localProjectSelected = false;
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Navigation Error", (String)"Unable to locate project");
                        break;
                    }
                    if (spo.getName().equals("")) {
                        pr = this.getProjectByName(ston);
                        break;
                    }
                    localProjectSelected = false;
                    ston = spo;
                }
                if (pr == null) continue;
                if (!pr.equals((Object)csp)) {
                    ConfigContext.loadIwmappingsRoot(pr);
                    ConfigContext.readIMConfigContext(pr);
                    csp = pr;
                }
                if (!localProjectSelected && !sto.getName().equals(INTEGRATION_MANAGER) && !sto.getName().equals(CONFIGURATION) && !sto.getName().equals(TRANSFORTMATION_SERVER) && !sto.getName().equals(XSLT) && !sto.getParent().getName().equals(XSLT) && !sto.getParent().getName().equals("") && !sto.getParent().getName().equals(TRANSACTIONS) && !sto.getParent().getName().equals(CONNECTIONS) && !sto.getParent().getName().equals(TRANSACTION_FLOWS) && !sto.getParent().getName().equals(QUERIES)) continue;
            }
            IProject cpr = Designer.getSelectedProject();
            if (!cpr.equals((Object)csp)) {
                ConfigContext.loadIwmappingsRoot(cpr);
                ConfigContext.readIMConfigContext();
            }
        }
        this.pasteMenuItem.setEnabled(ConfigContext.pasteEnabled());
    }

    private void pasteElement() {
        boolean afterMove = false;
        boolean deleteAfter = true;
        try {
            ArrayList<QueryContext> cqc;
            ArrayList<TransactionContext> ctc;
            ArrayList<IFile> tfl;
            ArrayList<IFile> cfl;
            Object newName;
            MessageDialogWithToggle3B mdwt;
            String newName2;
            ArrayList<transactionType> ctt;
            String newName3;
            ArrayList<IProject> cp = ConfigContext.getPrClp();
            ArrayList<TransactionContext> ctcm = ConfigContext.getTcClpMove();
            ArrayList<TransactionContext> ctcc = ConfigContext.getTcClpCopy();
            if (ctcc != null && !ctcc.isEmpty()) {
                ctcm = ctcc;
                deleteAfter = false;
            }
            if (ctcm != null && !ctcm.isEmpty()) {
                if (cp == null || cp.isEmpty()) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move: No source project!");
                    return;
                }
                if (cp.get(0).equals((Object)Designer.getSelectedProject())) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move: Source and destination projects are the same!");
                    return;
                }
                afterMove = true;
                for (TransactionContext tc : ctcm) {
                    String newName4 = tc.getTransactionId();
                    if (ConfigContext.getTransactionFlow(newName4) == null) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move transaction flow: " + newName4 + " already exists in destination project"));
                    return;
                }
                for (TransactionContext tc : ctcm) {
                    TransactionContext ntc = new TransactionContext(tc);
                    ConfigContext.getTransactionList().add(ntc);
                    if (ConfigContext.writeIMConfigContext()) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move transaction flow: " + tc.getTransactionId()));
                    return;
                }
            }
            ArrayList<QueryContext> cqcm = ConfigContext.getQcClpMove();
            ArrayList<QueryContext> cqcc = ConfigContext.getQcClpCopy();
            if (cqcc != null && !cqcc.isEmpty()) {
                cqcm = cqcc;
                deleteAfter = false;
            }
            if (cqcm != null && !cqcm.isEmpty()) {
                if (!afterMove) {
                    if (cp == null || cp.isEmpty()) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move: No source project!");
                        return;
                    }
                    if (cp.get(0).equals((Object)Designer.getSelectedProject())) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move: Source and destination projects are the same!");
                        return;
                    }
                }
                afterMove = true;
                for (QueryContext qc : cqcm) {
                    newName3 = qc.getTransactionId();
                    if (ConfigContext.getQuery(newName3) == null) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move query: " + newName3 + " already exists in destination project"));
                    return;
                }
                for (QueryContext qc : cqcm) {
                    QueryContext nqc = new QueryContext(qc);
                    ConfigContext.getQueryList().add(nqc);
                    if (ConfigContext.writeIMConfigContext()) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move query: " + qc.getTransactionId()));
                }
            }
            if (cp != null) {
                if (afterMove) {
                    if (deleteAfter && ctcm != null) {
                        if (!ConfigContext.readIMConfigContext(cp.get(0))) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transaction flow; error reading source project");
                            return;
                        }
                        for (TransactionContext tc : ctcm) {
                            if (ConfigContext.getTransactionList().remove(tc)) continue;
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move transaction flow; error deleting source flow " + tc.getTransactionId()));
                            return;
                        }
                        if (!ConfigContext.writeIMConfigContext(cp.get(0))) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transaction flows; error writing source project");
                            return;
                        }
                        if (!ConfigContext.readIMConfigContext(Designer.getSelectedProject())) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transaction flow; error reading destination project");
                            return;
                        }
                    }
                    if (deleteAfter && cqcm != null) {
                        if (!ConfigContext.readIMConfigContext(cp.get(0))) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move query; error reading source project");
                            return;
                        }
                        for (QueryContext qc : cqcm) {
                            if (ConfigContext.getQueryList().remove(qc)) continue;
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to move query; error deleting source query " + qc.getTransactionId()));
                            return;
                        }
                        if (!ConfigContext.writeIMConfigContext(cp.get(0))) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transaction flows; error writing source project");
                            return;
                        }
                        if (!ConfigContext.readIMConfigContext(Designer.getSelectedProject())) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transaction flow; error reading destination project");
                            return;
                        }
                    }
                } else {
                    for (IProject pr : cp) {
                        if (pr == null) continue;
                        newName3 = "Copy of " + pr.getName();
                        pr.copy(ResourcesPlugin.getWorkspace().getRoot().getFullPath().append(newName3), true, null);
                        IProject npr = ConfigContext.getProjectByName(newName3);
                        IFile pf = npr.getFile("configuration/" + pr.getName() + ".properties");
                        if (!pf.exists()) continue;
                        pf.move(npr.getFullPath().addTrailingSeparator().append("configuration/" + newName3 + ".properties"), true, null);
                    }
                }
            } else if (afterMove) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Source project is lost. Unable to move.");
                return;
            }
            if ((ctt = ConfigContext.getTrtClp()) != null) {
                if (afterMove) {
                    int rc = 0;
                    boolean ask = true;
                    iwmappingsType cit = ConfigContext.getIwmappingsRoot();
                    for (transactionType tt : ctt) {
                        transactionType ntt = ConfigContext.cloneTransaction(tt);
                        newName2 = tt.getname().getValue().trim();
                        int ti = ConfigContext.getTransactionIndex(newName2);
                        if (ti >= 0) {
                            if (ask) {
                                mdwt = new MessageDialogWithToggle3B(this.getSite().getShell(), "Paste Operation", null, "Transaction " + newName2 + " already exists in destination project. Do you want to use existing one or overwrite it?", 3, new String[]{"Use", "Overwrite", "Cancel"}, 0, "Apply to all", false);
                                rc = mdwt.open();
                                ask = !mdwt.getToggleState();
                            }
                            switch (rc) {
                                case 0: {
                                    break;
                                }
                                case 1: {
                                    cit.replacetransactionAt(ntt, ti);
                                    break;
                                }
                                case 2: {
                                    return;
                                }
                            }
                            continue;
                        }
                        cit.addtransaction(ntt);
                    }
                    if (!ConfigContext.saveTransactions()) {
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)"Unable to move transactions: saving failure");
                    }
                    if (deleteAfter) {
                        ConfigContext.cleanNonUsedTransactions(cp.get(0));
                    }
                } else {
                    for (transactionType tt : ctt) {
                        transactionType ntt = ConfigContext.cloneTransaction(tt);
                        newName = tt.getname().getValue().trim();
                        boolean clone = ConfigContext.getTransactionIndex((String)newName) >= 0;
                        iwmappingsType cit = ConfigContext.getIwmappingsRoot();
                        cit.addtransaction(ntt);
                        if (clone) {
                            cit.gettransactionAt(cit.gettransactionCount() - 1).replacenameAt("Copy of " + (String)newName, 0);
                        }
                        if (ConfigContext.saveTransactions()) continue;
                        MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste transaction: " + (String)newName));
                    }
                }
            }
            if ((cfl = ConfigContext.getXsltClp()) != null) {
                if (afterMove) {
                    int rc = 0;
                    boolean ask = true;
                    for (IFile xf : cfl) {
                        if (xf == null || !xf.exists()) continue;
                        String newName5 = xf.getName();
                        IProject cpr = Designer.getSelectedProject();
                        if (cpr == null) continue;
                        IFile cf = cpr.getFile("xslt/" + newName5);
                        if (cf.exists()) {
                            if (ask) {
                                mdwt = new MessageDialogWithToggle3B(this.getSite().getShell(), "Paste Operation", null, "Transformer " + newName5 + " already exists in destination project. Do you want to use existing one or overwrite it?", 3, new String[]{"Use", "Overwrite", "Cancel"}, 0, "Apply to all", false);
                                rc = mdwt.open();
                                ask = !mdwt.getToggleState();
                            }
                            switch (rc) {
                                case 0: {
                                    break;
                                }
                                case 1: {
                                    cf.delete(true, null);
                                    xf.copy(cpr.getFullPath().addTrailingSeparator().append("xslt/" + newName5), true, null);
                                    break;
                                }
                                case 2: {
                                    return;
                                }
                            }
                            continue;
                        }
                        xf.copy(cpr.getFullPath().addTrailingSeparator().append("xslt/" + newName5), true, null);
                    }
                    if (deleteAfter) {
                        ConfigContext.cleanNonUsedXSLT(cp.get(0));
                    }
                } else {
                    for (IFile xf : cfl) {
                        if (xf == null || !xf.exists()) continue;
                        newName = xf.getName();
                        IProject cpr = Designer.getSelectedProject();
                        if (cpr == null) continue;
                        IFile cf = cpr.getFile("xslt/" + (String)newName);
                        if (cf.exists()) {
                            newName = "Copy of " + (String)newName;
                        }
                        xf.copy(cpr.getFullPath().addTrailingSeparator().append("xslt/" + (String)newName), true, null);
                    }
                }
            }
            if ((tfl = ConfigContext.getTemplateClp()) != null) {
                for (IFile xf : tfl) {
                    if (xf == null || !xf.exists()) continue;
                    Object newName6 = xf.getName();
                    IProject cpr = Designer.getSelectedProject();
                    if (cpr == null) continue;
                    IFile cf = cpr.getFile("xslt/Site" + (String)newName6);
                    if (cf.exists()) {
                        newName6 = "Copy of " + (String)newName6;
                    }
                    xf.copy(cpr.getFullPath().addTrailingSeparator().append("xslt/Site/" + (String)newName6), true, null);
                }
            }
            if ((ctc = ConfigContext.getTcClp()) != null) {
                for (TransactionContext tc : ctc) {
                    boolean clone;
                    TransactionContext ntc = new TransactionContext(tc);
                    newName2 = tc.getTransactionId();
                    boolean bl = clone = ConfigContext.getTransactionFlow(newName2) != null;
                    if (clone) {
                        String ctf = ConfigContext.getCurrentTransactionFlowId();
                        ConfigContext.setCurrentTransactionFlowId(newName2);
                        String cnm = "Copy of " + newName2;
                        if (!ConfigContext.cloneAllNextTransactions(newName2, cnm)) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste transaction flow: " + newName2));
                        }
                        if (!ConfigContext.saveTransactions()) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste transaction flow: " + newName2));
                        }
                        ConfigContext.setCurrentTransactionFlowId(ctf);
                        ntc.setTransactionId("Copy of " + newName2);
                    }
                    ConfigContext.getTransactionList().add(ntc);
                    if (ConfigContext.writeIMConfigContext()) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste transaction flow: " + newName2));
                }
            }
            if ((cqc = ConfigContext.getQcClp()) != null) {
                for (QueryContext qc : cqc) {
                    boolean clone;
                    QueryContext nqc = new QueryContext(qc);
                    String newName7 = qc.getTransactionId();
                    boolean bl = clone = ConfigContext.getQuery(newName7) != null;
                    if (clone) {
                        String cq = ConfigContext.getCurrentQueryId();
                        ConfigContext.setCurrentQueryId(newName7);
                        String cnm = "Copy of " + newName7;
                        if (!ConfigContext.cloneAllNextTransactions(newName7, cnm)) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste query: " + newName7));
                        }
                        if (!ConfigContext.saveTransactions()) {
                            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste query: " + newName7));
                        }
                        ConfigContext.setCurrentQueryId(cq);
                        nqc.setTransactionId("Copy of " + newName7);
                    }
                    ConfigContext.getQueryList().add(nqc);
                    if (ConfigContext.writeIMConfigContext()) continue;
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste query: " + newName7));
                }
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Paste Error", (String)("Unable to paste project or its element: " + e.toString()));
        }
        this.setAndSelectViewer();
        if (afterMove && deleteAfter) {
            ConfigContext.cleanClipboard();
        }
    }

    private void cloneTransactions() {
        String tsrc = null;
        if (this.transactionFlowSelected || this.querySelected) {
            tsrc = this.transactionFlowSelected ? ConfigContext.getCurrentTransactionFlowId() : ConfigContext.getCurrentQueryId();
        } else {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"No source transaction flow or query is selected!");
            return;
        }
        StructuredSelection selection = (StructuredSelection)this.viewer.getSelection();
        int selSize = selection.size();
        if (selSize != 1) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"One and only one destination transaction flow or query must be selected!");
            return;
        }
        TreeObject sto = (TreeObject)selection.getFirstElement();
        if (!sto.getParent().getName().equals(TRANSACTION_FLOWS) && !sto.getParent().getName().equals(QUERIES)) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"You can clone transactions to another transaction flow or query only!");
            return;
        }
        String destName = sto.getName();
        if (destName.equals(tsrc)) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"Unable to clone transactions into the same transaction flow or query!");
            return;
        }
        if (!ConfigContext.cloneTransactions(destName)) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"Unable to clone transactions.");
            return;
        }
        try {
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)"Unable to save cloned transactions.");
                return;
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Cloning Error", (String)("Unable to save cloned transactions." + e));
            return;
        }
    }

    public void setAndSelectViewer() {
        TreeObject so;
        this.expanedElements = this.viewer.getExpandedElements();
        TreeParent root = this.createProjectModel();
        this.expanedElements = this.getExpanded(root);
        this.viewer.setInput((Object)root);
        this.viewer.setExpandedElements(this.expanedElements);
        IProject cp = Designer.getSelectedProject();
        ConfigContext.loadIwmappingsRoot(cp);
        if (root != null && cp != null && this.selectedObject != null && (so = root.getChild(this.selectedObject)) != null) {
            this.processSelection = false;
            this.viewer.setSelection((ISelection)new StructuredSelection((Object)so), true);
            this.processSelection = true;
        }
        this.initializeViews(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage());
    }

    private void createActions() {
        this.deleteActionGlobal = new DeleteAction();
        this.getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.DELETE.getId(), (IAction)this.deleteActionGlobal);
        this.refreshAction = new Action("Refresh"){

            public void run() {
                NavigationView.this.setAndSelectViewer();
            }
        };
        this.refreshAction.setImageDescriptor(Iw_sdkPlugin.getImageDescriptor("icons/refresh.gif"));
        this.refreshAction.setAccelerator(0x100000E);
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.refreshAction);
    }

    public void setSelectedObject(TreeObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public class TreeObject {
        private String name;
        private TreeParent parent;

        public TreeObject(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setParent(TreeParent parent) {
            this.parent = parent;
        }

        public TreeParent getParent() {
            return this.parent;
        }

        public String toString() {
            return this.getName();
        }

        boolean equals(TreeObject treeObject) {
            if (treeObject == null || treeObject.getName() == null) {
                return false;
            }
            if (this.parent != null && treeObject.getParent() != null) {
                return this.name.equals(treeObject.getName()) && this.parent.equals(treeObject.getParent());
            }
            return this.name.equals(treeObject.getName()) && this.parent == null && treeObject.getParent() == null;
        }
    }

    class TreeParent
    extends TreeObject {
        private ArrayList<TreeObject> children;

        public TreeParent(String name) {
            super(name);
            this.children = new ArrayList();
        }

        public void addChild(TreeObject child) {
            this.children.add(child);
            child.setParent(this);
        }

        public void removeChild(TreeObject child) {
            this.children.remove(child);
            child.setParent(null);
        }

        public TreeObject[] getChildren() {
            return this.children.toArray(new TreeObject[this.children.size()]);
        }

        public boolean hasChildren() {
            return this.children.size() > 0;
        }

        public TreeObject getChild(String childName) {
            TreeObject[] treeObjectArray = this.getChildren();
            int n = 0;
            int n2 = treeObjectArray.length;
            while (n < n2) {
                TreeObject ctn;
                TreeObject cto = treeObjectArray[n];
                if (cto.getName().equals(childName)) {
                    return cto;
                }
                if (cto instanceof TreeParent && (ctn = ((TreeParent)cto).getChild(childName)) != null) {
                    return ctn;
                }
                ++n;
            }
            return null;
        }

        public TreeObject getChild(TreeObject child) {
            TreeObject[] treeObjectArray = this.getChildren();
            int n = 0;
            int n2 = treeObjectArray.length;
            while (n < n2) {
                TreeObject ctn;
                TreeObject cto = treeObjectArray[n];
                if (cto.equals(child)) {
                    return cto;
                }
                if (cto instanceof TreeParent && (ctn = ((TreeParent)cto).getChild(child)) != null) {
                    return ctn;
                }
                ++n;
            }
            return null;
        }
    }

    class ViewContentProvider
    implements IStructuredContentProvider,
    ITreeContentProvider {
        ViewContentProvider() {
        }

        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }

        public void dispose() {
        }

        public Object[] getElements(Object parent) {
            return this.getChildren(parent);
        }

        public Object getParent(Object child) {
            if (child instanceof TreeObject) {
                return ((TreeObject)child).getParent();
            }
            return null;
        }

        public Object[] getChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent)parent).getChildren();
            }
            return new Object[0];
        }

        public boolean hasChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent)parent).hasChildren();
            }
            return false;
        }
    }

    class ViewLabelProvider
    extends LabelProvider {
        ViewLabelProvider() {
        }

        public String getText(Object obj) {
            return obj.toString();
        }

        public Image getImage(Object obj) {
            String imageKey = "IMG_OBJ_ELEMENTS";
            if (obj instanceof TreeParent) {
                TreeParent cp = ((TreeParent)obj).getParent();
                if (cp != null) {
                    IProject cpr;
                    imageKey = cp.getName().equals("") ? ((cpr = NavigationView.this.getProjectByName((TreeObject)obj)) == null || cpr.isOpen() ? "IMG_OBJ_PROJECT" : "IMG_OBJ_PROJECT_CLOSED") : "IMG_OBJ_FOLDER";
                }
            } else {
                TreeObject co = (TreeObject)obj;
                TreeParent cp = co.getParent();
                if (cp != null) {
                    String pn = cp.getName();
                    String cn = co.getName();
                    if (pn.equals(NavigationView.CONFIGURATION)) {
                        if (cn.equals(NavigationView.INTEGRATION_MANAGER)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/calendar.gif").createImage();
                        }
                        if (cn.equals(NavigationView.TRANSFORTMATION_SERVER)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/session_config.gif").createImage();
                        }
                    } else {
                        if (pn.equals(NavigationView.TRANSACTIONS)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/properties.gif").createImage();
                        }
                        if (pn.equals(NavigationView.CONNECTIONS)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/connection.gif").createImage();
                        }
                        if (pn.equals(NavigationView.XSLT)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/changelog_obj.gif").createImage();
                        }
                        if (pn.equals(NavigationView.TEMPLATES)) {
                            return Iw_sdkPlugin.getImageDescriptor("icons/mime_mapping.gif").createImage();
                        }
                        if (pn.equals(NavigationView.TRANSACTION_FLOWS)) {
                            TransactionContext ctx = ConfigContext.getTransactionFlow(cn);
                            if (ctx != null && ctx.isActive()) {
                                return Iw_sdkPlugin.getImageDescriptor("icons/sequence.gif").createImage();
                            }
                            return Iw_sdkPlugin.getImageDescriptor("icons/sequence_gr.gif").createImage();
                        }
                        if (pn.equals(NavigationView.QUERIES)) {
                            QueryContext qtx = ConfigContext.getQuery(cn);
                            if (qtx != null && qtx.isActive()) {
                                return Iw_sdkPlugin.getImageDescriptor("icons/query_method_obj.gif").createImage();
                            }
                            return Iw_sdkPlugin.getImageDescriptor("icons/query_method_obj_gr.gif").createImage();
                        }
                    }
                }
            }
            return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
    }

    private class DeleteAction
    extends Action {
        DeleteAction() {
        }

        public void run() {
            NavigationView.this.deleteElement();
        }
    }
}

