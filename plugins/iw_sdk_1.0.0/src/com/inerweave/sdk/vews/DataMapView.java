/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.IwConnection;
import com.inerweave.sdk.composites.NewDataMapWizardComposite;
import com.inerweave.sdk.vews.TransactionDetailsView;
import com.iwtransactions.accessType;
import com.iwtransactions.datamapType;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.parameterlist;
import com.iwtransactions.transactionType;
import com.iwtransactions.translatorType;
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

public class DataMapView
extends ViewPart {
    private Action action;
    private Action saveDataMapAction;
    private NewDataMapWizardComposite dataMapComposite;
    public static final String ID = "iw_sdk.DataMapView";
    private Composite top = null;
    private int minWdthDetails = 0;
    private int minHeightDetails = 0;
    private datamapType currentDataMap;
    private boolean newDataMap = false;
    private int newDataMapIndex = -1;
    private transactionType currentTransaction;
    private IProject currentProject;
    private iwmappingsType currentRoot;

    public void createPartControl(Composite parent) {
        this.top = new Composite(parent, 0);
        this.top.setLayout((Layout)new FormLayout());
        this.top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle sfca = DataMapView.this.top.getClientArea();
                if (DataMapView.this.dataMapComposite != null) {
                    int wdt = sfca.width;
                    int hgt = sfca.height;
                    if (wdt < DataMapView.this.minWdthDetails) {
                        wdt = DataMapView.this.minWdthDetails;
                    }
                    if (hgt < DataMapView.this.minHeightDetails) {
                        hgt = DataMapView.this.minHeightDetails;
                    }
                    DataMapView.this.dataMapComposite.setSize(wdt, hgt);
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
        this.dataMapComposite = new NewDataMapWizardComposite((Composite)scrolledComposite_1, 0);
        this.dataMapComposite.setBounds(0, 0, 490, 345);
        this.dataMapComposite.setBackground(Display.getCurrent().getSystemColor(1));
        scrolledComposite_1.setContent((Control)this.dataMapComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    public void setFocus() {
    }

    public void initializeScreen(int addNew) {
        this.newDataMap = addNew != 0;
        this.newDataMapIndex = addNew;
        this.currentTransaction = ConfigContext.getCurrentTransaction();
        this.currentProject = Designer.getSelectedProject();
        this.currentRoot = ConfigContext.getIwmappingsRoot();
        this.refreshScreen();
    }

    public String[] getConnectionNames() {
        String[] conNames = ConfigContext.getProjectConnectionNames(this.currentProject, true);
        if (conNames != null) {
            this.dataMapComposite.getConnectionsCombo().setItems(conNames);
        }
        return conNames;
    }

    public void processConnectionNames(String[] connectionNames) throws Exception {
        if (connectionNames != null) {
            String cnm = ConfigContext.getDataMapConnectionName(this.currentProject, this.currentDataMap);
            if (cnm != null) {
                this.dataMapComposite.getConnectionsCombo().select(ConfigContext.getComboIndex(connectionNames, cnm));
                this.dataMapComposite.getVieweditButton().setText("Edit");
            } else {
                this.dataMapComposite.getConnectionsCombo().select(0);
                this.dataMapComposite.getVieweditButton().setText("Add");
            }
        }
    }

    public void refreshScreen() {
        if (this.currentProject == null) {
            return;
        }
        try {
            String[] fti = ConfigContext.getXsltTransformers(this.currentProject);
            this.dataMapComposite.getPreTransformerCombo().setItems(fti);
            this.dataMapComposite.getPostTransformerCombo().setItems(fti);
            this.dataMapComposite.getPreTransformerCombo().select(0);
            this.dataMapComposite.getPostTransformerCombo().select(0);
            String[] conNames = this.getConnectionNames();
            if (this.newDataMap) {
                this.dataMapComposite.getMapName().setText("");
                this.dataMapComposite.getConnectionsCombo().select(0);
                this.dataMapComposite.getAccessTypeCombo().select(0);
                this.dataMapComposite.getCommandStyledText().setText("");
                this.dataMapComposite.getPreTransformerCombo().select(0);
                this.dataMapComposite.getPostTransformerCombo().select(0);
                this.dataMapComposite.getManualCommitButton().setSelection(false);
                this.dataMapComposite.getUseAttributesButton().setSelection(false);
                this.dataMapComposite.getUseCustomConversionButton().setSelection(false);
                this.dataMapComposite.getIgnoreRepeatButton().setSelection(false);
                this.dataMapComposite.getXmlDeclarationButton().setSelection(false);
                this.dataMapComposite.getGenParamList().removeAll();
                this.dataMapComposite.getInputParamList().removeAll();
                this.dataMapComposite.getOutputParamList().removeAll();
                this.dataMapComposite.getVieweditButton().setText("Add");
                this.dataMapComposite.getVieweditButton().setEnabled(false);
                this.dataMapComposite.getGenParamList().setMenu(null);
                this.dataMapComposite.getInputParamList().setMenu(null);
                this.dataMapComposite.getOutputParamList().setMenu(null);
                this.setPartName("Data Map View");
                this.setTitleToolTip("New Data Map");
                return;
            }
            this.dataMapComposite.getVieweditButton().setEnabled(true);
            this.restoreMenus();
            this.currentDataMap = ConfigContext.getCurrentDataMap();
            if (this.currentDataMap == null) {
                return;
            }
            String dmName = this.currentDataMap.getname().getValue().trim();
            this.setPartName(dmName);
            this.setTitleToolTip("Data Map " + dmName);
            this.dataMapComposite.getMapName().setText(dmName);
            this.processConnectionNames(conNames);
            if (this.currentDataMap.getaccessCount() > 0) {
                parameterlist ipt;
                accessType atp = this.currentDataMap.getaccess();
                this.dataMapComposite.setCurrentAccess(atp);
                String tp = atp.gettype().getValue().trim();
                if (tp.equalsIgnoreCase("procedure")) {
                    this.dataMapComposite.getAccessTypeCombo().select(1);
                } else if (tp.equalsIgnoreCase("dynamic")) {
                    this.dataMapComposite.getAccessTypeCombo().select(2);
                } else if (tp.equalsIgnoreCase("data")) {
                    this.dataMapComposite.getAccessTypeCombo().select(3);
                } else {
                    this.dataMapComposite.getAccessTypeCombo().select(0);
                }
                this.dataMapComposite.getCommandStyledText().setText(atp.getstatementpre().getValue().trim());
                String instr = atp.getstatementpost().getValue().trim();
                this.dataMapComposite.getManualCommitButton().setSelection(instr.indexOf("%manual_commit%") >= 0);
                this.dataMapComposite.getIgnoreRepeatButton().setSelection(instr.indexOf("%ignore_repeat%") >= 0);
                this.dataMapComposite.getUseCustomConversionButton().setSelection(instr.indexOf("%ignore_default%") >= 0);
                this.dataMapComposite.getUseAttributesButton().setSelection(instr.indexOf("%use_attributes%") >= 0);
                this.dataMapComposite.getXmlDeclarationButton().setSelection(instr.indexOf("%xml_declaration%") >= 0);
                if (atp.gettranslatorCount() > 0) {
                    translatorType ctt = atp.gettranslator();
                    if (ctt.getinputclassCount() > 0) {
                        String iv = ctt.getinputclass().getValue().trim();
                        if (iv.length() > 0) {
                            this.dataMapComposite.getPreTransformerCombo().select(ConfigContext.getComboIndex(fti, iv));
                        } else {
                            this.dataMapComposite.getPreTransformerCombo().select(0);
                        }
                    } else {
                        this.dataMapComposite.getPreTransformerCombo().select(0);
                    }
                    if (ctt.getoutputclassCount() > 0) {
                        String ov = ctt.getoutputclass().getValue().trim();
                        if (ov.length() > 0) {
                            this.dataMapComposite.getPostTransformerCombo().select(ConfigContext.getComboIndex(fti, ov));
                        } else {
                            this.dataMapComposite.getPostTransformerCombo().select(0);
                        }
                    } else {
                        this.dataMapComposite.getPostTransformerCombo().select(0);
                    }
                }
                if (atp.getvaluesCount() > 0) {
                    ipt = atp.getvalues();
                    int pc = ipt.getparameterCount();
                    if (pc > 0) {
                        this.dataMapComposite.getGenParamList().setItems(ConfigContext.getNamelessParameterList(pc));
                        this.dataMapComposite.getGenParamList().select(0);
                    } else {
                        this.dataMapComposite.getGenParamList().removeAll();
                    }
                } else {
                    this.dataMapComposite.getGenParamList().removeAll();
                }
                if (atp.getwhereCount() > 0) {
                    ipt = atp.getwhere();
                    int pc = ipt.getparameterCount();
                    if (pc > 0) {
                        this.dataMapComposite.getInputParamList().setItems(ConfigContext.getNamelessParameterList(pc));
                        this.dataMapComposite.getInputParamList().select(0);
                    } else {
                        this.dataMapComposite.getInputParamList().removeAll();
                    }
                } else {
                    this.dataMapComposite.getInputParamList().removeAll();
                }
                if (atp.getoutputsCount() > 0) {
                    ipt = atp.getoutputs();
                    int pc = ipt.getparameterCount();
                    if (pc > 0) {
                        this.dataMapComposite.getOutputParamList().setItems(ConfigContext.getNamelessParameterList(pc));
                        this.dataMapComposite.getOutputParamList().select(0);
                    } else {
                        this.dataMapComposite.getOutputParamList().removeAll();
                    }
                } else {
                    this.dataMapComposite.getOutputParamList().removeAll();
                }
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Dat Map", (String)("Unable to initialize dat map view: " + e.toString()));
        }
    }

    public void saveDataMap() {
        if (!this.newDataMap && this.currentDataMap == null) {
            MessageDialog.openWarning((Shell)this.getSite().getShell(), (String)"Dat Map", (String)"No Data Map to save");
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
        String dmName = this.dataMapComposite.getMapName().getText().trim();
        try {
            boolean notEmptyTranslator;
            if (this.newDataMap) {
                this.currentDataMap = new datamapType();
            }
            if (this.currentDataMap.getnameCount() > 0) {
                this.currentDataMap.replacenameAt(dmName, 0);
            } else {
                this.currentDataMap.addname(dmName);
            }
            int si = this.dataMapComposite.getConnectionsCombo().getSelectionIndex();
            String conNm = this.dataMapComposite.getConnectionsCombo().getItem(si);
            if (si >= 0 && conNm.trim().length() > 0) {
                ConfigContext.setCurrentConnectionName(conNm);
                IwConnection ic = ConfigContext.getCurrentConnection();
                if (this.currentDataMap.getdriverCount() > 0) {
                    this.currentDataMap.replacedriverAt(ic.getDriver(), 0);
                } else {
                    this.currentDataMap.adddriver(ic.getDriver());
                }
                if (this.currentDataMap.geturlCount() > 0) {
                    this.currentDataMap.replaceurlAt(ic.getUrl(), 0);
                } else {
                    this.currentDataMap.addurl(ic.getUrl());
                }
                if (this.currentDataMap.getuserCount() > 0) {
                    this.currentDataMap.replaceuserAt(ic.getUserName(), 0);
                } else {
                    this.currentDataMap.adduser(ic.getUserName());
                }
                if (this.currentDataMap.getpasswordCount() > 0) {
                    this.currentDataMap.replacepasswordAt(ic.getPassword(), 0);
                } else {
                    this.currentDataMap.addpassword(ic.getPassword());
                }
            } else {
                if (this.currentDataMap.getdriverCount() > 0) {
                    this.currentDataMap.replacedriverAt("", 0);
                } else {
                    this.currentDataMap.adddriver("");
                }
                if (this.currentDataMap.geturlCount() > 0) {
                    this.currentDataMap.replaceurlAt("", 0);
                } else {
                    this.currentDataMap.addurl("");
                }
                if (this.currentDataMap.getuserCount() > 0) {
                    this.currentDataMap.replaceuserAt("", 0);
                } else {
                    this.currentDataMap.adduser("");
                }
                if (this.currentDataMap.getpasswordCount() > 0) {
                    this.currentDataMap.replacepasswordAt("", 0);
                } else {
                    this.currentDataMap.addpassword("");
                }
            }
            boolean noAccess = this.newDataMap || this.currentDataMap.getaccessCount() <= 0;
            accessType atp = noAccess ? new accessType() : this.currentDataMap.getaccess();
            int atsi = this.dataMapComposite.getAccessTypeCombo().getSelectionIndex();
            if (atsi >= 0) {
                String mt = this.dataMapComposite.getAccessTypeCombo().getItem(atsi).trim().toLowerCase();
                if (atp.gettypeCount() > 0) {
                    if (mt.length() > 0) {
                        atp.replacetypeAt(mt, 0);
                    } else {
                        atp.removetype();
                    }
                } else if (mt.length() > 0) {
                    atp.addtype(mt);
                }
            } else if (atp.gettypeCount() > 0) {
                atp.removetype();
            }
            if (atp.getstatementpreCount() > 0) {
                atp.replacestatementpreAt(this.dataMapComposite.getCommandStyledText().getText().trim(), 0);
            } else {
                atp.addstatementpre(this.dataMapComposite.getCommandStyledText().getText().trim());
            }
            StringBuffer instr = new StringBuffer();
            if (this.dataMapComposite.getIgnoreRepeatButton().getSelection()) {
                instr.append("%ignore_repeat%");
            }
            if (this.dataMapComposite.getUseCustomConversionButton().getSelection()) {
                if (instr.length() > 0) {
                    instr.append(" | ");
                }
                instr.append("%ignore_default%");
            }
            if (this.dataMapComposite.getManualCommitButton().getSelection()) {
                if (instr.length() > 0) {
                    instr.append(" | ");
                }
                instr.append("%manual_commit%");
            }
            if (this.dataMapComposite.getUseAttributesButton().getSelection()) {
                if (instr.length() > 0) {
                    instr.append(" | ");
                }
                instr.append("%use_attributes%");
            }
            if (this.dataMapComposite.getXmlDeclarationButton().getSelection()) {
                if (instr.length() > 0) {
                    instr.append(" | ");
                }
                instr.append("%xml_declaration%");
            }
            if (atp.getstatementpostCount() > 0) {
                atp.replacestatementpostAt(instr.toString(), 0);
            } else {
                atp.addstatementpost(instr.toString());
            }
            int iprt = this.dataMapComposite.getPreTransformerCombo().getSelectionIndex();
            String prtr = "";
            if (iprt >= 0) {
                prtr = this.dataMapComposite.getPreTransformerCombo().getItem(iprt);
            }
            iprt = this.dataMapComposite.getPostTransformerCombo().getSelectionIndex();
            String potr = "";
            if (iprt >= 0) {
                potr = this.dataMapComposite.getPostTransformerCombo().getItem(iprt);
            }
            translatorType ctt = null;
            boolean bl = notEmptyTranslator = prtr.trim().length() > 0 || potr.trim().length() > 0;
            if (notEmptyTranslator) {
                ctt = new translatorType();
                if (prtr.trim().length() > 0) {
                    ctt.addinputclass(prtr);
                }
                if (potr.trim().length() > 0) {
                    ctt.addoutputclass(potr);
                }
            }
            if (atp.gettranslatorCount() > 0) {
                if (notEmptyTranslator) {
                    atp.replacetranslatorAt(ctt, 0);
                } else {
                    atp.removetranslator();
                }
            } else if (notEmptyTranslator) {
                atp.addtranslator(ctt);
            }
            if (noAccess) {
                this.currentDataMap.addaccess(atp);
            }
            if (this.newDataMap) {
                if (this.newDataMapIndex == -1) {
                    this.currentTransaction.adddatamap(this.currentDataMap);
                } else {
                    this.currentTransaction.insertdatamapAt(this.currentDataMap, this.newDataMapIndex - 1);
                }
            }
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Data Map", (String)"Unable to save transaction.xml");
                return;
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Dat Map", (String)("Unable to save data map: " + e.toString()));
        }
        if (this.newDataMap) {
            try {
                this.setPartName(dmName);
                this.setTitleToolTip("Data Map " + dmName);
                this.dataMapComposite.getVieweditButton().setEnabled(true);
                this.restoreMenus();
            }
            catch (Exception e) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Dat Map", (String)("Unable to set data map: " + e.toString()));
            }
        }
        TransactionDetailsView tv = (TransactionDetailsView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.TransactionDetailsView");
        try {
            if (tv != null && tv.getCurrentProject().getName().equals(this.currentProject.getName()) && tv.getCurrentTransaction().getname().equals(this.currentTransaction.getname())) {
                tv.refreshDataMaps(dmName);
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Dat Map", (String)("Unable to refresh Transaction View: " + e.toString()));
        }
        if (mod) {
            Designer.setSelectedProject(cpr);
            ConfigContext.setIwmappingsRoot(ciwmr);
        } else if (this.newDataMap) {
            ConfigContext.setCurrentDataMapName(dmName);
        }
    }

    private void initializeToolBar() {
        IToolBarManager tbm = this.getViewSite().getActionBars().getToolBarManager();
        tbm.add((IAction)this.action);
        tbm.add((IAction)this.saveDataMapAction);
    }

    private void initializeMenu() {
        IMenuManager manager = this.getViewSite().getActionBars().getMenuManager();
        manager.add((IAction)this.action);
        manager.add((IAction)this.saveDataMapAction);
    }

    private void createActions() {
        this.saveDataMapAction = new Action("Save Data Map"){

            public void run() {
                DataMapView.this.saveDataMap();
            }
        };
        this.saveDataMapAction.setToolTipText("Save Data Map");
        this.saveDataMapAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
        this.action = new Action("Refresh"){

            public void run() {
                DataMapView.this.refreshScreen();
            }
        };
        this.action.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/refresh.gif"));
        this.action.setToolTipText("Refresh Data Map");
    }

    private void restoreMenus() {
        if (this.dataMapComposite.getInputParamList().getMenu() == null) {
            this.dataMapComposite.getInputParamList().setMenu(this.dataMapComposite.getInputMenu());
        }
        if (this.dataMapComposite.getOutputParamList().getMenu() == null) {
            this.dataMapComposite.getOutputParamList().setMenu(this.dataMapComposite.getOuptutMenu());
        }
        if (this.dataMapComposite.getGenParamList().getMenu() == null) {
            this.dataMapComposite.getGenParamList().setMenu(this.dataMapComposite.getGenMenu());
        }
    }

    public datamapType getCurrentDataMap() {
        return this.currentDataMap;
    }

    public NewDataMapWizardComposite getDataMapComposite() {
        return this.dataMapComposite;
    }

    public IProject getCurrentProject() {
        return this.currentProject;
    }
}

