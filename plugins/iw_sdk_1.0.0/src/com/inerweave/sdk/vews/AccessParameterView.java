/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.vews;

import com.altova.types.SchemaBoolean;
import com.altova.types.SchemaString;
import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.composites.AccessParameterComposite;
import com.inerweave.sdk.vews.DataMapView;
import com.iwtransactions.accessType;
import com.iwtransactions.datamapType;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.mappingType;
import com.iwtransactions.parameterType;
import com.iwtransactions.parameterlist;
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

public class AccessParameterView
extends ViewPart {
    private Action saveParameterAction;
    private AccessParameterComposite accessParameterComposite;
    public static final String ID = "iw_sdk.AccessParameterView";
    private Composite top = null;
    private int minWdthDetails = 0;
    private int minHeightDetails = 0;
    private String[] mappingTypesForOutput = new String[]{"", "<java type>", "xml", "xpath", "xpath_number", "postxpath", "post", "reflect"};
    private String[] mappingTypesForWhere = new String[]{"", "<java type>", "xml"};
    private String[] mappingTypesForValue = new String[]{"", "return", "replacequote", "replacedoublequote", "filter", "space_substitute", "xpath", "xpath_number", "postxpath", "post", "transform", "constant", "default"};
    private parameterType currentParameter;
    private int newParameterIndex = -1;
    private datamapType currentDataMap;
    private boolean newParameter = false;
    private IProject currentProject;
    private iwmappingsType currentRoot;
    private char parameterListType = (char)32;

    public void createPartControl(Composite parent) {
        this.top = new Composite(parent, 0);
        this.top.setLayout((Layout)new FormLayout());
        this.top.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                Rectangle sfca = AccessParameterView.this.top.getClientArea();
                if (AccessParameterView.this.accessParameterComposite != null) {
                    int wdt = sfca.width;
                    int hgt = sfca.height;
                    if (wdt < AccessParameterView.this.minWdthDetails) {
                        wdt = AccessParameterView.this.minWdthDetails;
                    }
                    if (hgt < AccessParameterView.this.minHeightDetails) {
                        hgt = AccessParameterView.this.minHeightDetails;
                    }
                    AccessParameterView.this.accessParameterComposite.setSize(wdt, hgt);
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
        this.accessParameterComposite = new AccessParameterComposite((Composite)scrolledComposite_1, 0);
        this.accessParameterComposite.setBounds(0, 0, 490, 345);
        this.accessParameterComposite.setBackground(Display.getCurrent().getSystemColor(1));
        scrolledComposite_1.setContent((Control)this.accessParameterComposite);
        this.createActions();
        this.initializeToolBar();
        this.initializeMenu();
    }

    private void createActions() {
        this.saveParameterAction = new Action("Save Parameter"){

            public void run() {
                AccessParameterView.this.saveParameter();
            }
        };
        this.saveParameterAction.setToolTipText("Save Access Parameter");
        this.saveParameterAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor((Object)Iw_sdkPlugin.getDefault(), "icons/save_edit.gif"));
    }

    private void initializeToolBar() {
        IToolBarManager toolbarManager = this.getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add((IAction)this.saveParameterAction);
    }

    private void initializeMenu() {
        IMenuManager menuManager = this.getViewSite().getActionBars().getMenuManager();
        menuManager.add((IAction)this.saveParameterAction);
    }

    public void setFocus() {
    }

    public void initializeScreen(int addNew) {
        try {
            String mt;
            this.newParameter = addNew != 0;
            this.newParameterIndex = addNew;
            this.currentDataMap = ConfigContext.getCurrentDataMap();
            this.currentProject = Designer.getSelectedProject();
            this.currentRoot = ConfigContext.getIwmappingsRoot();
            String[] fti = ConfigContext.getXsltTransformers(this.currentProject);
            this.accessParameterComposite.getPreTransformerCombo().setItems(fti);
            this.accessParameterComposite.getPostTransformerCombo().setItems(fti);
            this.accessParameterComposite.getPreTransformerCombo().select(0);
            this.accessParameterComposite.getPostTransformerCombo().select(0);
            this.parameterListType = ConfigContext.getCurrentParameterName().charAt(0);
            switch (this.parameterListType) {
                case 'V': {
                    this.accessParameterComposite.getMappingTypeCombo().setItems(this.mappingTypesForValue);
                    break;
                }
                case 'W': {
                    this.accessParameterComposite.getMappingTypeCombo().setItems(this.mappingTypesForWhere);
                    break;
                }
                case 'O': {
                    this.accessParameterComposite.getMappingTypeCombo().setItems(this.mappingTypesForOutput);
                }
            }
            if (this.newParameter) {
                this.accessParameterComposite.getInputName().setText("");
                this.accessParameterComposite.getMappingTypeCombo().select(0);
                this.accessParameterComposite.getPreTransformerCombo().select(0);
                this.accessParameterComposite.getMappingValue().setText("");
                this.accessParameterComposite.getQuoted().setSelection(false);
                return;
            }
            this.currentParameter = ConfigContext.getCurrentParameter();
            if (this.currentParameter == null) {
                return;
            }
            this.accessParameterComposite.getInputName().setText(this.currentParameter.getinput().getValue().trim());
            this.accessParameterComposite.getMappingTypeCombo().select(0);
            if (this.currentParameter.gettranslatorCount() > 0) {
                String it;
                translatorType tt = this.currentParameter.gettranslator();
                if (tt.getinputclassCount() > 0 && (it = tt.getinputclass().getValue().trim()).length() > 0) {
                    this.accessParameterComposite.getPreTransformerCombo().select(ConfigContext.getComboIndex(fti, it));
                }
                if (tt.getoutputclassCount() > 0 && (it = tt.getoutputclass().getValue().trim()).length() > 0) {
                    this.accessParameterComposite.getPostTransformerCombo().select(ConfigContext.getComboIndex(fti, it));
                }
            }
            if (this.currentParameter.getmapping().gettypeCount() > 0 && (mt = this.currentParameter.getmapping().gettype().getValue().trim()).length() > 0) {
                int si;
                try {
                    si = ConfigContext.getComboIndex(this.accessParameterComposite.getMappingTypeCombo().getItems(), mt, true);
                }
                catch (Exception e) {
                    si = ConfigContext.getComboIndex(this.accessParameterComposite.getMappingTypeCombo().getItems(), "<java type>");
                    this.accessParameterComposite.getMappingTypeCombo().setItem(si, mt);
                }
                this.accessParameterComposite.getMappingTypeCombo().select(si);
            }
            this.accessParameterComposite.getMappingValue().setText(this.currentParameter.getmapping().getValue().getValue().trim());
            this.accessParameterComposite.getQuoted().setSelection(this.currentParameter.getmapping().getquoted().getValue());
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)("Unable to initialize parameter view: " + e.toString()));
        }
    }

    private void saveParameter() {
        if (!this.newParameter && this.currentParameter == null) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)"No parameter available");
            return;
        }
        IProject cpr = Designer.getSelectedProject();
        iwmappingsType ciwmr = null;
        accessType atp = null;
        boolean mod = false;
        if (cpr == null || !cpr.getName().equals(this.currentProject.getName())) {
            mod = true;
            ciwmr = ConfigContext.getIwmappingsRoot();
            Designer.setSelectedProject(this.currentProject);
            ConfigContext.setIwmappingsRoot(this.currentRoot);
        }
        try {
            boolean notEmptyTranslator;
            if (this.newParameter) {
                this.currentParameter = new parameterType();
            }
            int iprt = this.accessParameterComposite.getPreTransformerCombo().getSelectionIndex();
            String prtr = "";
            if (iprt >= 0) {
                prtr = this.accessParameterComposite.getPreTransformerCombo().getItem(iprt);
            }
            iprt = this.accessParameterComposite.getPostTransformerCombo().getSelectionIndex();
            String potr = "";
            if (iprt >= 0) {
                potr = this.accessParameterComposite.getPostTransformerCombo().getItem(iprt);
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
            if (this.currentParameter.gettranslatorCount() > 0) {
                if (notEmptyTranslator) {
                    this.currentParameter.replacetranslatorAt(ctt, 0);
                } else {
                    this.currentParameter.removetranslator();
                }
            } else if (notEmptyTranslator) {
                this.currentParameter.addtranslator(ctt);
            }
            String inpnm = this.accessParameterComposite.getInputName().getText().trim();
            if (this.currentParameter.getinputCount() > 0) {
                this.currentParameter.replaceinputAt(inpnm, 0);
            } else {
                this.currentParameter.addinput(inpnm);
            }
            mappingType mpt = new mappingType();
            mpt.addquoted(new SchemaBoolean(this.accessParameterComposite.getQuoted().getSelection()));
            String st = this.accessParameterComposite.getSelectedMappingType();
            if (st == null) {
                mpt.addtype(this.accessParameterComposite.getMappingTypeCombo().getItem(this.accessParameterComposite.getMappingTypeCombo().getSelectionIndex()));
            } else {
                mpt.addtype(st);
            }
            mpt.setValue(new SchemaString(this.accessParameterComposite.getMappingValue().getText().trim()));
            if (this.currentParameter.getmappingCount() > 0) {
                this.currentParameter.replacemappingAt(mpt, 0);
            } else {
                this.currentParameter.addmapping(mpt);
            }
            if (this.newParameter) {
                if (this.currentDataMap == null) {
                    MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)"No Data Map to save parameter for. If Data Map is new try to save it first.");
                    return;
                }
                if (this.currentDataMap.getaccessCount() > 0) {
                    atp = this.currentDataMap.getaccess();
                } else {
                    atp = new accessType();
                    this.currentDataMap.addaccess(atp);
                }
                if (atp.gettranslatorCount() <= 0) {
                    atp.addtranslator(new translatorType());
                }
                if (this.newParameterIndex == -1) {
                    switch (this.parameterListType) {
                        case 'V': {
                            if (atp.getvaluesCount() > 0) {
                                atp.getvalues().addparameter(this.currentParameter);
                                break;
                            }
                            parameterlist pml = new parameterlist();
                            pml.addparameter(this.currentParameter);
                            atp.addvalues(pml);
                            break;
                        }
                        case 'W': {
                            if (atp.getwhereCount() > 0) {
                                atp.getwhere().addparameter(this.currentParameter);
                                break;
                            }
                            parameterlist pml = new parameterlist();
                            pml.addparameter(this.currentParameter);
                            atp.addwhere(pml);
                            break;
                        }
                        case 'O': {
                            if (atp.getoutputsCount() > 0) {
                                atp.getoutputs().addparameter(this.currentParameter);
                                break;
                            }
                            parameterlist pml = new parameterlist();
                            pml.addparameter(this.currentParameter);
                            atp.addoutputs(pml);
                        }
                    }
                } else {
                    switch (this.parameterListType) {
                        case 'V': {
                            atp.getvalues().insertparameterAt(this.currentParameter, this.newParameterIndex - 1);
                            break;
                        }
                        case 'W': {
                            atp.getwhere().insertparameterAt(this.currentParameter, this.newParameterIndex - 1);
                            break;
                        }
                        case 'O': {
                            atp.getoutputs().insertparameterAt(this.currentParameter, this.newParameterIndex - 1);
                        }
                    }
                }
            }
            if (!ConfigContext.saveTransactions()) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)"Unable to save transaction.xml");
                return;
            }
        }
        catch (Exception e) {
            MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)("Unable to save parameter input: " + e.toString()));
        }
        if (this.newParameter) {
            DataMapView dv = (DataMapView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("iw_sdk.DataMapView");
            try {
                if (dv != null && dv.getCurrentProject().getName().equals(this.currentProject.getName()) && dv.getCurrentDataMap().getname().equals(this.currentDataMap.getname())) {
                    switch (this.parameterListType) {
                        case 'V': {
                            dv.getDataMapComposite().getGenParamList().setItems(ConfigContext.getNamelessParameterList(atp.getvalues().getparameterCount()));
                            break;
                        }
                        case 'W': {
                            dv.getDataMapComposite().getInputParamList().setItems(ConfigContext.getNamelessParameterList(atp.getwhere().getparameterCount()));
                            break;
                        }
                        case 'O': {
                            dv.getDataMapComposite().getOutputParamList().setItems(ConfigContext.getNamelessParameterList(atp.getoutputs().getparameterCount()));
                        }
                    }
                }
            }
            catch (Exception e) {
                MessageDialog.openError((Shell)this.getSite().getShell(), (String)"Parameter View", (String)("Unable to reftesh Data Map View: " + e.toString()));
            }
        }
        if (mod) {
            Designer.setSelectedProject(cpr);
            ConfigContext.setIwmappingsRoot(ciwmr);
        }
    }
}

