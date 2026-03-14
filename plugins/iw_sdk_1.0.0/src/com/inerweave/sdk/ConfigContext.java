/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.altova.xml.Node;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.IwConnection;
import com.inerweave.sdk.QueryContext;
import com.inerweave.sdk.TransactionBase;
import com.inerweave.sdk.TransactionContext;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.vews.XSLTEditorView;
import com.iwtransactions.accessType;
import com.iwtransactions.datamapType;
import com.iwtransactions.iwmappingsType;
import com.iwtransactions.iwtransactionsDoc;
import com.iwtransactions.nexttransactionType;
import com.iwtransactions.parameterType;
import com.iwtransactions.parameterlist;
import com.iwtransactions.transactionType;
import com.iwtransactions.translatorType;
import iw_sdk.Iw_sdkPlugin;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import org.apache.xerces.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ConfigContext {
    private static String imName;
    private static String tsName;
    private static String tsLogLevel;
    private static String failoverURL;
    private static String primaryTransformationServerURL;
    private static String secondaryTransformationServerURL;
    private static String primaryTransformationServerURLT;
    private static String secondaryTransformationServerURLT;
    private static String primaryTransformationServerURL1;
    private static String secondaryTransformationServerURL1;
    private static String primaryTransformationServerURLT1;
    private static String secondaryTransformationServerURLT1;
    private static String primaryTransformationServerURLD;
    private static String secondaryTransformationServerURLD;
    private static Vector<TransactionContext> transactionList;
    private static Vector<QueryContext> queryList;
    private static int imBufferSize;
    private static int tsBufferSize;
    private static long hartbeatInterval;
    private static long refreshInterval;
    private static boolean imPrimary;
    private static boolean hosted;
    private static boolean tsPrimary;
    private static boolean tsTimeStamping;
    private static boolean productionPackage;
    private static boolean runAtStartUp;
    private static String currentTransactionName;
    private static String currentXsltName;
    private static String currentTemplateName;
    private static String currentDataMapName;
    private static String currentConnectionName;
    private static String currentTransactionFlowId;
    private static String currentQueryId;
    private static String currentParameterName;
    private static iwmappingsType iwmappingsRoot;
    public static String[] n_0_59;
    public static String mapName;
    public static String colName;
    public static String valueTag;
    public static ArrayList<IProject> prClp;
    private static ArrayList<TransactionContext> tcClp;
    private static ArrayList<QueryContext> qcClp;
    private static ArrayList<transactionType> trtClp;
    private static ArrayList<IFile> xsltClp;
    private static ArrayList<IFile> templateClp;
    private static String[][] paramsClp;
    private static ArrayList<TransactionContext> tcClpMove;
    private static ArrayList<QueryContext> qcClpMove;
    private static ArrayList<TransactionContext> tcClpCopy;
    private static ArrayList<QueryContext> qcClpCopy;

    static {
        imBufferSize = 1024;
        tsBufferSize = 1024;
        hartbeatInterval = 0L;
        refreshInterval = 1000L;
        runAtStartUp = true;
        currentTransactionName = null;
        currentXsltName = null;
        currentTemplateName = null;
        currentDataMapName = null;
        currentConnectionName = null;
        currentTransactionFlowId = null;
        currentQueryId = null;
        currentParameterName = null;
        iwmappingsRoot = null;
        n_0_59 = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        mapName = "datamap[@name='";
        colName = "col[@name='";
        valueTag = "<xsl:value-of select=\"";
    }

    public static ArrayList<QueryContext> getQcClpMove() {
        return qcClpMove;
    }

    public static void setQcClpMove(ArrayList<QueryContext> qcClpMove) {
        ConfigContext.qcClpMove = qcClpMove;
    }

    public static ArrayList<TransactionContext> getTcClpMove() {
        return tcClpMove;
    }

    public static void setTcClpMove(ArrayList<TransactionContext> tcClpMove) {
        ConfigContext.tcClpMove = tcClpMove;
    }

    public static ArrayList<QueryContext> getQcClp() {
        return qcClp;
    }

    public static void setQcClp(ArrayList<QueryContext> qcClp) {
        ConfigContext.qcClp = qcClp;
    }

    public static ArrayList<TransactionContext> getTcClp() {
        return tcClp;
    }

    public static void setTcClp(ArrayList<TransactionContext> tcClp) {
        ConfigContext.tcClp = tcClp;
    }

    public static ArrayList<transactionType> getTrtClp() {
        return trtClp;
    }

    public static void setTrtClp(ArrayList<transactionType> trtClp) {
        ConfigContext.trtClp = trtClp;
    }

    public static ArrayList<IFile> getXsltClp() {
        return xsltClp;
    }

    public static void setXsltClp(ArrayList<IFile> xsltClp) {
        ConfigContext.xsltClp = xsltClp;
    }

    public static final boolean isImPrimary() {
        return imPrimary;
    }

    public static final void setImPrimary(boolean primary) {
        imPrimary = primary;
    }

    public static final int getImBufferSize() {
        return imBufferSize;
    }

    public static final void setImBufferSize(int bufferSize) {
        imBufferSize = bufferSize;
    }

    public static final String getFailoverURL() {
        return failoverURL;
    }

    public static final void setFailoverURL(String failoverURL) {
        ConfigContext.failoverURL = failoverURL;
    }

    public static final String getPrimaryTransformationServerURL() {
        return primaryTransformationServerURL;
    }

    public static final void setPrimaryTransformationServerURL(String primaryTransformationServerURL) {
        ConfigContext.primaryTransformationServerURL = primaryTransformationServerURL;
    }

    public static final String getSecondaryTransformationServerURL() {
        return secondaryTransformationServerURL;
    }

    public static final void setSecondaryTransformationServerURL(String secondaryTransformationServerURL) {
        ConfigContext.secondaryTransformationServerURL = secondaryTransformationServerURL;
    }

    public static final long getHartbeatInterval() {
        return hartbeatInterval;
    }

    public static final void setHartbeatInterval(long hartbeatInterval) {
        ConfigContext.hartbeatInterval = hartbeatInterval;
    }

    public static boolean readIMConfigContext() {
        return ConfigContext.readIMConfigContext(Designer.getSelectedProject());
    }

    public static boolean readIMConfigContext(IProject spr) {
        if (spr == null) {
            return false;
        }
        IPath clc = spr.getLocation();
        if (clc == null) {
            return false;
        }
        DOMParser parser = new DOMParser();
        try {
            parser.parse(clc + "/configuration/im/config.xml");
        }
        catch (SAXException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return ConfigContext.imFromXML(parser);
    }

    public static boolean readTSConfigContext() {
        return ConfigContext.readTSConfigContext(Designer.getSelectedProject());
    }

    public static boolean readTSConfigContext(IProject spr) {
        DOMParser parser = new DOMParser();
        if (spr == null) {
            return false;
        }
        IPath clc = spr.getLocation();
        if (clc == null) {
            return false;
        }
        try {
            parser.parse(clc + "/configuration/ts/config.xml");
        }
        catch (SAXException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return ConfigContext.tsFromXML(parser);
    }

    public static boolean writeIMConfigContext() {
        return ConfigContext.writeIMConfigContext(Designer.getSelectedProject());
    }

    public static boolean writeIMConfigContext(IProject spr) {
        if (spr == null) {
            return false;
        }
        try {
            ConfigContext.saveFileInProject(spr, "configuration/im/config.xml", ConfigContext.imToXML(), true);
        }
        catch (IOException e) {
            return false;
        }
        catch (CoreException e) {
            return false;
        }
        return true;
    }

    public static boolean writeTSConfigContext() {
        IProject spr = Designer.getSelectedProject();
        if (spr == null) {
            return false;
        }
        try {
            ConfigContext.saveFileInProject(spr, "configuration/ts/config.xml", ConfigContext.tsToXML(), true);
        }
        catch (IOException e) {
            return false;
        }
        catch (CoreException e) {
            return false;
        }
        return true;
    }

    public static String imToXML() {
        String rootTag = "BusinessDaemonConfiguration Name=\"" + ConfigContext.getImName() + "\" HartbeatInterval=\"" + ConfigContext.getHartbeatInterval() + "\" RefreshInterval=\"" + ConfigContext.getRefreshInterval() + "\" PrimaryTSURL=\"" + ConfigContext.getPrimaryTransformationServerURL() + "\" SecondaryTSURL=\"" + ConfigContext.getSecondaryTransformationServerURL() + "\" PrimaryTSURLT=\"" + ConfigContext.getPrimaryTransformationServerURLT() + "\" SecondaryTSURLT=\"" + ConfigContext.getSecondaryTransformationServerURLT() + "\" PrimaryTSURL1=\"" + ConfigContext.getPrimaryTransformationServerURL1() + "\" SecondaryTSURL1=\"" + ConfigContext.getSecondaryTransformationServerURL1() + "\" PrimaryTSURLT1=\"" + ConfigContext.getPrimaryTransformationServerURLT1() + "\" SecondaryTSURLT1=\"" + ConfigContext.getSecondaryTransformationServerURLT1() + "\" PrimaryTSURLD=\"" + ConfigContext.getPrimaryTransformationServerURLD() + "\" SecondaryTSURLD=\"" + ConfigContext.getSecondaryTransformationServerURLD() + "\" FailoverURL=\"" + ConfigContext.getFailoverURL() + "\" IsPrimary=\"" + (ConfigContext.isImPrimary() ? "1" : "0") + "\" RunAtStartUp=\"" + (ConfigContext.isRunAtStartUp() ? "1" : "0") + "\" BufferSize=\"" + ConfigContext.getImBufferSize() + "\" IsHosted=\"" + (ConfigContext.isHosted() ? "1" : "0") + "\"";
        StringBuffer transactionsInfo = new StringBuffer("<" + rootTag + ">");
        if (transactionList != null) {
            Iterator<TransactionContext> trItr = transactionList.iterator();
            while (trItr.hasNext()) {
                transactionsInfo.append(TransactionContext.toXML(trItr.next()));
            }
        }
        if (queryList != null) {
            Iterator<QueryContext> qrItr = queryList.iterator();
            while (qrItr.hasNext()) {
                transactionsInfo.append(QueryContext.toXML(qrItr.next()));
            }
        }
        transactionsInfo.append("</" + rootTag.substring(0, rootTag.indexOf(" ")) + ">");
        return transactionsInfo.toString();
    }

    public static String tsToXML() {
        String rootTag = "TransformationServerConfiguration Name=\"" + ConfigContext.getTsName() + "\" LogLevel=\"" + ConfigContext.getTsLogLevel() + "\" IsPrimary=\"" + (ConfigContext.isTsPrimary() ? "1" : "0") + "\" IsTimeStamping=\"" + (ConfigContext.isTsTimeStamping() ? "1" : "0") + "\" IsCompiledMapping=\"" + (ConfigContext.isProductionPackage() ? "1" : "0") + "\" BufferSize=\"" + ConfigContext.getTsBufferSize() + "\" IsHosted=\"" + (ConfigContext.isHosted() ? "1" : "0") + "\"";
        StringBuffer transactionsInfo = new StringBuffer("<" + rootTag + ">");
        transactionsInfo.append("</" + rootTag.substring(0, rootTag.indexOf(" ")) + ">");
        return transactionsInfo.toString();
    }

    public static boolean imFromXML(DOMParser parser) {
        Document conDoc = parser.getDocument();
        Element bdConf = conDoc.getDocumentElement();
        if (!bdConf.getTagName().equals("BusinessDaemonConfiguration")) {
            return false;
        }
        ConfigContext.setImName(bdConf.getAttribute("Name"));
        String prm = bdConf.getAttribute("IsPrimary");
        if (prm == null || prm.trim().length() == 0) {
            ConfigContext.setImPrimary(true);
        } else {
            ConfigContext.setImPrimary(prm.equalsIgnoreCase("true") || prm.equals("1"));
        }
        String hst = bdConf.getAttribute("IsHosted");
        if (hst == null || hst.trim().length() == 0) {
            ConfigContext.setHosted(false);
        } else {
            ConfigContext.setHosted(hst.equalsIgnoreCase("true") || hst.equals("1"));
        }
        String grs = bdConf.getAttribute("RunAtStartUp");
        if (grs == null || grs.trim().length() == 0) {
            ConfigContext.setRunAtStartUp(true);
        } else {
            ConfigContext.setRunAtStartUp(grs.equalsIgnoreCase("true") || grs.equals("1"));
        }
        String fur = bdConf.getAttribute("FailoverURL");
        if (fur == null) {
            ConfigContext.setFailoverURL("");
        } else {
            ConfigContext.setFailoverURL(fur);
        }
        String hbi = bdConf.getAttribute("HartbeatInterval");
        if (hbi == null || hbi.trim().length() == 0) {
            ConfigContext.setHartbeatInterval(0L);
        } else {
            ConfigContext.setHartbeatInterval(new Long(hbi));
        }
        String rfi = bdConf.getAttribute("RefreshInterval");
        if (rfi == null || rfi.trim().length() == 0) {
            ConfigContext.setRefreshInterval(1000L);
        } else {
            ConfigContext.setRefreshInterval(new Long(rfi));
        }
        String pts = bdConf.getAttribute("PrimaryTSURL");
        if (pts == null) {
            ConfigContext.setPrimaryTransformationServerURL("");
        } else {
            ConfigContext.setPrimaryTransformationServerURL(pts);
        }
        String sts = bdConf.getAttribute("SecondaryTSURL");
        if (sts == null) {
            ConfigContext.setSecondaryTransformationServerURL("");
        } else {
            ConfigContext.setSecondaryTransformationServerURL(sts);
        }
        String ptst = bdConf.getAttribute("PrimaryTSURLT");
        if (ptst == null) {
            ConfigContext.setPrimaryTransformationServerURLT("");
        } else {
            ConfigContext.setPrimaryTransformationServerURLT(ptst);
        }
        String stst = bdConf.getAttribute("SecondaryTSURLT");
        if (stst == null) {
            ConfigContext.setSecondaryTransformationServerURLT("");
        } else {
            ConfigContext.setSecondaryTransformationServerURLT(stst);
        }
        String pts1 = bdConf.getAttribute("PrimaryTSURL1");
        if (pts1 == null) {
            ConfigContext.setPrimaryTransformationServerURL1("");
        } else {
            ConfigContext.setPrimaryTransformationServerURL1(pts1);
        }
        String sts1 = bdConf.getAttribute("SecondaryTSURL1");
        if (sts1 == null) {
            ConfigContext.setSecondaryTransformationServerURL1("");
        } else {
            ConfigContext.setSecondaryTransformationServerURL1(sts1);
        }
        String ptst1 = bdConf.getAttribute("PrimaryTSURLT1");
        if (ptst1 == null) {
            ConfigContext.setPrimaryTransformationServerURLT1("");
        } else {
            ConfigContext.setPrimaryTransformationServerURLT1(ptst1);
        }
        String stst1 = bdConf.getAttribute("SecondaryTSURLT1");
        if (stst1 == null) {
            ConfigContext.setSecondaryTransformationServerURLT1("");
        } else {
            ConfigContext.setSecondaryTransformationServerURLT1(stst1);
        }
        String ptsd = bdConf.getAttribute("PrimaryTSURLD");
        if (ptsd == null) {
            ConfigContext.setPrimaryTransformationServerURLD("");
        } else {
            ConfigContext.setPrimaryTransformationServerURLD(ptsd);
        }
        String stsd = bdConf.getAttribute("SecondaryTSURLD");
        if (stsd == null) {
            ConfigContext.setSecondaryTransformationServerURLD("");
        } else {
            ConfigContext.setSecondaryTransformationServerURLD(stsd);
        }
        String bfs = bdConf.getAttribute("BufferSize");
        if (bfs == null || bfs.trim().length() == 0) {
            ConfigContext.setImBufferSize(1024);
        } else {
            ConfigContext.setImBufferSize(new Integer(bfs));
        }
        NodeList tds = bdConf.getElementsByTagName("TransactionDescription");
        transactionList = new Vector(tds.getLength());
        int i = 0;
        while (i < tds.getLength()) {
            TransactionContext tc = new TransactionContext();
            Element tdn = (Element)tds.item(i);
            tc.setTransactionId(tdn.getAttribute("Id"));
            String dsc = tdn.getAttribute("Description");
            if (dsc != null) {
                tc.setDescription(dsc);
            } else {
                tc.setDescription("");
            }
            String sol = tdn.getAttribute("Solution");
            if (sol != null) {
                tc.setSolution(sol);
            } else {
                tc.setSolution("");
            }
            tc.setTransactionInterval(new Integer(tdn.getAttribute("Interval")).intValue());
            tc.setTransactionShiftFromHartBeat(new Integer(tdn.getAttribute("Shift")).intValue());
            tc.setTransactionStartTime(Timestamp.valueOf(ConfigContext.getStartTime(tdn)));
            String lrs = tdn.getAttribute("RunAtStartUp");
            if (lrs == null || lrs.trim().length() == 0) {
                tc.setRunAtStartUp(ConfigContext.isRunAtStartUp());
            } else {
                tc.setRunAtStartUp(lrs.equalsIgnoreCase("true") || lrs.equals("1"));
            }
            String nox = tdn.getAttribute("NumberOfExecutions");
            if (nox == null || nox.trim().length() == 0) {
                tc.setNumberOfExecutions(0);
            } else {
                tc.setNumberOfExecutions(new Integer(nox));
            }
            String inc = tdn.getAttribute("InnerCycles");
            if (inc == null || inc.trim().length() == 0) {
                tc.setInnerCycles(0);
            } else {
                tc.setInnerCycles(new Integer(inc));
            }
            String pbl = tdn.getAttribute("IsPublic");
            if (pbl == null || pbl.trim().length() == 0) {
                tc.setHostedPublic(false);
            } else {
                tc.setHostedPublic(pbl.equalsIgnoreCase("true") || pbl.equals("1"));
            }
            String act = tdn.getAttribute("IsActive");
            if (act == null || act.trim().length() == 0) {
                tc.setActive(true);
            } else {
                tc.setActive(act.equalsIgnoreCase("true") || act.equals("1"));
            }
            String stf = tdn.getAttribute("IsStateful");
            if (stf == null || stf.trim().length() == 0) {
                tc.setStateful(false);
            } else {
                tc.setStateful(stf.equalsIgnoreCase("true") || stf.equals("1"));
            }
            String ptstf = tdn.getAttribute("PrimaryTSURL");
            if (ptstf == null || ptstf.trim().length() == 0) {
                tc.setPrimaryTransformationServerURL("");
            } else {
                tc.setPrimaryTransformationServerURL(ptstf);
            }
            String ststf = tdn.getAttribute("SecondaryTSURL");
            if (ststf == null || ststf.trim().length() == 0) {
                tc.setSecondaryTransformationServerURL("");
            } else {
                tc.setSecondaryTransformationServerURL(ststf);
            }
            tc.setPrimaryTransformationServerURLT("");
            tc.setPrimaryTransformationServerURLD("");
            tc.setSecondaryTransformationServerURLT("");
            tc.setSecondaryTransformationServerURLD("");
            NodeList tps = tdn.getElementsByTagName("Parameter");
            tc.setParameters(new Hashtable<String, TransactionBase.ParameterValue>());
            int j = 0;
            while (j < tps.getLength()) {
                Element stTime = (Element)tps.item(j);
                String nm = stTime.getAttribute("Name");
                if (!nm.equals("QueryStartTime")) {
                    String fx = stTime.getAttribute("Fixed");
                    String up = stTime.getAttribute("Upload");
                    String ps = stTime.getAttribute("Password");
                    if (fx == null || fx.trim().length() == 0) {
                        fx = "false";
                    }
                    if (up == null || up.trim().length() == 0) {
                        up = "false";
                    }
                    if (ps == null || ps.trim().length() == 0) {
                        ps = "false";
                    }
                    tc.addParameter(nm, stTime.getAttribute("Value"), fx.equalsIgnoreCase("true") || fx.equals("1"), up.equalsIgnoreCase("true") || up.equals("1"), ps.equalsIgnoreCase("true") || ps.equals("1"));
                }
                ++j;
            }
            transactionList.add(i, tc);
            ++i;
        }
        NodeList qds = bdConf.getElementsByTagName("Query");
        queryList = new Vector(qds.getLength());
        int i2 = 0;
        while (i2 < qds.getLength()) {
            QueryContext qc = new QueryContext();
            Element qdn = (Element)qds.item(i2);
            qc.setTransactionId(qdn.getAttribute("Id"));
            String dsc = qdn.getAttribute("Description");
            if (dsc != null) {
                qc.setDescription(dsc);
            } else {
                qc.setDescription("");
            }
            String sol = qdn.getAttribute("Solution");
            if (sol != null) {
                qc.setSolution(sol);
            } else {
                qc.setSolution("");
            }
            String inc = qdn.getAttribute("InnerCycles");
            if (inc == null || inc.trim().length() == 0) {
                qc.setInnerCycles(0);
            } else {
                qc.setInnerCycles(new Integer(inc));
            }
            String pbl = qdn.getAttribute("IsPublic");
            if (pbl == null || pbl.trim().length() == 0) {
                qc.setHostedPublic(false);
            } else {
                qc.setHostedPublic(pbl.equalsIgnoreCase("true") || pbl.equals("1"));
            }
            String act = qdn.getAttribute("IsActive");
            if (act == null || act.trim().length() == 0) {
                qc.setActive(true);
            } else {
                qc.setActive(act.equalsIgnoreCase("true") || act.equals("1"));
            }
            String ptstq = qdn.getAttribute("PrimaryTSURL");
            if (ptstq == null || ptstq.trim().length() == 0) {
                qc.setPrimaryTransformationServerURL("");
            } else {
                qc.setPrimaryTransformationServerURL(ptstq);
            }
            String ststq = qdn.getAttribute("SecondaryTSURL");
            if (ststq == null || ststq.trim().length() == 0) {
                qc.setSecondaryTransformationServerURL("");
            } else {
                qc.setSecondaryTransformationServerURL(ststq);
            }
            qc.setPrimaryTransformationServerURLT("");
            qc.setPrimaryTransformationServerURLD("");
            qc.setSecondaryTransformationServerURLT("");
            qc.setSecondaryTransformationServerURLD("");
            qc.setTransactionStartTime(Timestamp.valueOf(ConfigContext.getStartTime(qdn)));
            NodeList qps = qdn.getElementsByTagName("Parameter");
            qc.setParameters(new Hashtable<String, TransactionBase.ParameterValue>());
            int j = 0;
            while (j < qps.getLength()) {
                Element stTime = (Element)qps.item(j);
                String nm = stTime.getAttribute("Name");
                if (!nm.equals("QueryStartTime")) {
                    String fx = stTime.getAttribute("Fixed");
                    String up = stTime.getAttribute("Upload");
                    String ps = stTime.getAttribute("Password");
                    if (fx == null || fx.trim().length() == 0) {
                        fx = "false";
                    }
                    if (up == null || up.trim().length() == 0) {
                        up = "false";
                    }
                    if (ps == null || ps.trim().length() == 0) {
                        ps = "false";
                    }
                    qc.addParameter(nm, stTime.getAttribute("Value"), fx.equalsIgnoreCase("true") || fx.equals("1"), up.equalsIgnoreCase("true") || up.equals("1"), ps.equalsIgnoreCase("true") || ps.equals("1"));
                }
                ++j;
            }
            queryList.add(i2, qc);
            ++i2;
        }
        return true;
    }

    public static boolean tsFromXML(DOMParser parser) {
        Document conDoc = parser.getDocument();
        Element tsConf = conDoc.getDocumentElement();
        if (!tsConf.getTagName().equals("TransformationServerConfiguration")) {
            return false;
        }
        ConfigContext.setTsName(tsConf.getAttribute("Name"));
        ConfigContext.setTsLogLevel(tsConf.getAttribute("LogLevel"));
        String prm = tsConf.getAttribute("IsPrimary");
        ConfigContext.setTsPrimary(prm == null || prm.equals("") || prm.equalsIgnoreCase("true") || prm.equals("1"));
        String grs = tsConf.getAttribute("IsTimeStamping");
        if (grs == null || grs.trim().length() == 0) {
            ConfigContext.setTsTimeStamping(false);
        } else {
            ConfigContext.setTsTimeStamping(grs.equalsIgnoreCase("true") || grs.equals("1"));
        }
        String cpm = tsConf.getAttribute("IsCompiledMapping");
        if (cpm == null || cpm.trim().length() == 0) {
            ConfigContext.setProductionPackage(true);
        } else {
            ConfigContext.setProductionPackage(cpm.equalsIgnoreCase("true") || cpm.equals("1"));
        }
        String bfs = tsConf.getAttribute("BufferSize");
        if (bfs == null || bfs.trim().length() == 0) {
            ConfigContext.setImBufferSize(1024);
        } else {
            ConfigContext.setImBufferSize(new Integer(bfs));
        }
        return true;
    }

    public static String getStartTime(Element tdn) {
        NodeList tps = tdn.getElementsByTagName("Parameter");
        int j = 0;
        while (j < tps.getLength()) {
            Element stTime = (Element)tps.item(j);
            if (stTime.getAttribute("Name").equals("QueryStartTime")) {
                return stTime.getAttribute("Value");
            }
            ++j;
        }
        return null;
    }

    public static String readFile(String filePath) {
        if (filePath == null) {
            return null;
        }
        File inf = new File(filePath);
        if (!inf.exists()) {
            System.out.println("File " + filePath + " does not exist");
            return null;
        }
        int fl = (int)inf.length();
        byte[] outs = new byte[fl];
        int osz = 0;
        try {
            FileInputStream fr = new FileInputStream(inf);
            osz = fr.read(outs);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (osz <= 0) {
            System.out.println("File " + filePath + " is empty");
            return null;
        }
        return new String(outs);
    }

    public static boolean writeFile(String filePath, String fileContext, boolean update) {
        if (filePath == null || fileContext == null) {
            return false;
        }
        File inf = new File(filePath);
        if (inf.exists() && !update) {
            System.out.println("File " + filePath + " already exists");
            return false;
        }
        byte[] outs = fileContext.getBytes();
        try {
            FileOutputStream fr = new FileOutputStream(inf);
            fr.write(outs);
            fr.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static final String getImName() {
        return imName;
    }

    public static final void setImName(String name) {
        imName = name;
    }

    public static final boolean isRunAtStartUp() {
        return runAtStartUp;
    }

    public static final void setRunAtStartUp(boolean runAtStartUp) {
        ConfigContext.runAtStartUp = runAtStartUp;
    }

    public static final Vector<TransactionContext> getTransactionList() {
        return transactionList;
    }

    public static final Vector<QueryContext> getQueryList() {
        return queryList;
    }

    public static final int getTsBufferSize() {
        return tsBufferSize;
    }

    public static final void setTsBufferSize(int tsBufferSize) {
        ConfigContext.tsBufferSize = tsBufferSize;
    }

    public static final boolean isProductionPackage() {
        return productionPackage;
    }

    public static final void setProductionPackage(boolean tsCompiledMapping) {
        productionPackage = tsCompiledMapping;
    }

    public static final String getTsLogLevel() {
        return tsLogLevel;
    }

    public static final void setTsLogLevel(String tsLogLevel) {
        ConfigContext.tsLogLevel = tsLogLevel;
    }

    public static final String getTsName() {
        return tsName;
    }

    public static final void setTsName(String tsName) {
        ConfigContext.tsName = tsName;
    }

    public static final boolean isTsPrimary() {
        return tsPrimary;
    }

    public static final void setTsPrimary(boolean tsPrimary) {
        ConfigContext.tsPrimary = tsPrimary;
    }

    public static final boolean isTsTimeStamping() {
        return tsTimeStamping;
    }

    public static final void setTsTimeStamping(boolean tsTimeStamping) {
        ConfigContext.tsTimeStamping = tsTimeStamping;
    }

    public static final iwmappingsType getIwmappingsRoot() {
        return iwmappingsRoot;
    }

    public static final transactionType getCurrentTransaction() {
        return ConfigContext.getCurrentTransaction(currentTransactionName);
    }

    public static final transactionType getCurrentTransaction(String ctn) {
        if (ctn == null || iwmappingsRoot == null) {
            return null;
        }
        int tcn = iwmappingsRoot.gettransactionCount();
        int i = 0;
        while (i < tcn) {
            try {
                transactionType ctr = iwmappingsRoot.gettransactionAt(i);
                if (ctr.getname().getValue().trim().equals(ctn)) {
                    return ctr;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            ++i;
        }
        return null;
    }

    public static final int getTransactionIndex(String transactionName) {
        if (transactionName == null || iwmappingsRoot == null) {
            return -1;
        }
        int tcn = iwmappingsRoot.gettransactionCount();
        int i = 0;
        while (i < tcn) {
            try {
                transactionType ctr = iwmappingsRoot.gettransactionAt(i);
                if (ctr.getname().getValue().trim().equals(transactionName)) {
                    return i;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
            ++i;
        }
        return -1;
    }

    public static final datamapType getCurrentDataMap() {
        if (currentDataMapName == null) {
            return null;
        }
        transactionType ctn = ConfigContext.getCurrentTransaction();
        if (ctn == null) {
            return null;
        }
        int tcn = ctn.getdatamapCount();
        int i = 0;
        while (i < tcn) {
            try {
                datamapType cdt = ctn.getdatamapAt(i);
                if (cdt.getname().getValue().trim().equals(currentDataMapName)) {
                    return cdt;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            ++i;
        }
        return null;
    }

    public static final void setCurrentTransactionName(String currentTransactionName) {
        ConfigContext.currentTransactionName = currentTransactionName;
    }

    public static int getComboIndex(String[] comboItems, String item) throws Exception {
        return ConfigContext.getComboIndex(comboItems, item, false);
    }

    public static int getComboIndex(String[] comboItems, String item, boolean ignoringCase) throws Exception {
        int ri = 0;
        String[] stringArray = comboItems;
        int n = 0;
        int n2 = stringArray.length;
        while (n < n2) {
            String ci = stringArray[n];
            if (ci.equals(item) || ignoringCase && ci.equalsIgnoreCase(item)) {
                return ri;
            }
            ++ri;
            ++n;
        }
        throw new Exception("Unable to find an element " + item + " in combo");
    }

    public static String[] getXsltTransformers(IProject cpr) {
        return ConfigContext.getXsltTransformers(cpr, true);
    }

    public static String[] getXsltTransformers(IProject cpr, boolean withEmpty) {
        ArrayList<String> xfn = new ArrayList<String>();
        if (withEmpty) {
            xfn.add("");
        }
        try {
            if (cpr.isOpen()) {
                IResource[] xfl;
                IFolder xf = (IFolder)cpr.findMember("xslt");
                if (xf == null) {
                    return null;
                }
                IResource[] iResourceArray = xfl = xf.members();
                int n = 0;
                int n2 = iResourceArray.length;
                while (n < n2) {
                    IResource cr = iResourceArray[n];
                    if (cr != null) {
                        String cre = cr.getFileExtension();
                        if (cr.getType() == 1 && cre != null && cre.equals("xslt")) {
                            String fn = cr.getName();
                            int dp = fn.lastIndexOf(".");
                            if (dp >= 0) {
                                fn = fn.substring(0, dp);
                            }
                            xfn.add(fn);
                        }
                    }
                    ++n;
                }
            }
        }
        catch (CoreException e) {
            e.printStackTrace();
            return null;
        }
        xfn.add("iwp2HTMLTable");
        return xfn.toArray(new String[0]);
    }

    public static String[] getXsltTemplates(IProject cpr) {
        return ConfigContext.getXsltTemplates(cpr, true);
    }

    public static String[] getXsltTemplates(IProject cpr, boolean withEmpty) {
        ArrayList<String> xfn = new ArrayList<String>();
        if (withEmpty) {
            xfn.add("");
        }
        try {
            if (cpr.isOpen()) {
                IResource[] xfl;
                IResource[] iResourceArray = xfl = ((IFolder)cpr.findMember("xslt/Site")).members();
                int n = 0;
                int n2 = iResourceArray.length;
                while (n < n2) {
                    IResource cr = iResourceArray[n];
                    if (cr != null) {
                        String cre = cr.getFileExtension();
                        if (cr.getType() == 1 && cre != null && cre.equals("iwxt")) {
                            String fn = cr.getName();
                            int dp = fn.lastIndexOf(".");
                            if (dp >= 0) {
                                fn = fn.substring(0, dp);
                            }
                            xfn.add(fn);
                        }
                    }
                    ++n;
                }
            }
        }
        catch (CoreException e) {
            e.printStackTrace();
            return null;
        }
        return xfn.toArray(new String[0]);
    }

    public static File[] getXsltTransformerFiles() {
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            return null;
        }
        return ConfigContext.getXsltTransformerFiles(cpr);
    }

    public static File[] getXsltTransformerFiles(IProject cpr) {
        ArrayList<File> xfn = new ArrayList<File>();
        try {
            if (cpr.isOpen()) {
                IResource[] xfl;
                IResource[] iResourceArray = xfl = ((IFolder)cpr.findMember("xslt")).members();
                int n = 0;
                int n2 = iResourceArray.length;
                while (n < n2) {
                    IResource cr = iResourceArray[n];
                    if (cr.getType() == 1 && cr.getFileExtension().equals("xslt")) {
                        IPath clc = cr.getLocation();
                        if (clc == null) {
                            return null;
                        }
                        xfn.add(new File(clc.toString()));
                    }
                    ++n;
                }
            }
        }
        catch (CoreException e) {
            e.printStackTrace();
            return null;
        }
        IPath clc = cpr.getLocation();
        if (clc == null) {
            return null;
        }
        xfn.add(new File(String.valueOf(clc.toString()) + "/xslt/Site/new/transactions.xslt"));
        return xfn.toArray(new File[0]);
    }

    public static String[] getTransactions(IProject cpr, boolean withEmpty) {
        return ConfigContext.getTransactions(cpr, withEmpty, null);
    }

    public static String[] getTransactions(IProject cpr, boolean withEmpty, String excludingBefore) {
        iwmappingsType iwt;
        String[] ctrs = null;
        IPath clc = cpr.getLocation();
        if (clc == null) {
            return null;
        }
        try {
            iwt = new iwmappingsType(new iwtransactionsDoc().load(clc + "/xslt/Site/new/xml/transactions.xml"));
        }
        catch (RuntimeException e1) {
            return null;
        }
        if (iwt != null) {
            transactionType[] flt;
            int tcn = iwt.gettransactionCount();
            int d = 0;
            if (withEmpty) {
                ctrs = new String[++tcn];
                ctrs[0] = "";
                d = 1;
            } else {
                ctrs = new String[tcn];
            }
            int i = d;
            while (i < tcn) {
                try {
                    ctrs[i] = iwt.gettransactionAt(i - d).getname().getValue().trim();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                ++i;
            }
            if (excludingBefore != null && (flt = ConfigContext.getFlowTransactions(excludingBefore)) != null) {
                Vector<String> fltn = new Vector<String>();
                try {
                    transactionType[] transactionTypeArray = flt;
                    int n = 0;
                    int n2 = transactionTypeArray.length;
                    while (n < n2) {
                        transactionType cflt = transactionTypeArray[n];
                        fltn.add(cflt.getname().getValue());
                        ++n;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                ArrayList<String> xfn = new ArrayList<String>();
                if (withEmpty) {
                    xfn.add("");
                }
                int i2 = d;
                while (i2 < tcn) {
                    if (!fltn.contains(ctrs[i2])) {
                        xfn.add(ctrs[i2]);
                    }
                    ++i2;
                }
                return xfn.toArray(new String[0]);
            }
        }
        return ctrs;
    }

    public static String[] getTransactionFlows() {
        String[] trfls = null;
        Vector<TransactionContext> trls = ConfigContext.getTransactionList();
        if (trls != null) {
            trfls = new String[trls.size()];
            int i = 0;
            for (TransactionContext trc : trls) {
                trfls[i++] = trc.getTransactionId();
            }
        }
        return trfls;
    }

    public static String[] getQueries() {
        String[] qrfls = null;
        Vector<QueryContext> qrls = ConfigContext.getQueryList();
        if (qrls != null) {
            qrfls = new String[qrls.size()];
            int i = 0;
            for (QueryContext qrc : qrls) {
                qrfls[i++] = qrc.getTransactionId();
            }
        }
        return qrfls;
    }

    public static String[] getDataMaps(transactionType currentTransaction) {
        int tcn = currentTransaction.getdatamapCount();
        String[] ctrs = new String[tcn];
        int i = 0;
        while (i < tcn) {
            try {
                ctrs[i] = currentTransaction.getdatamapAt(i).getname().getValue().trim();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            ++i;
        }
        return ctrs;
    }

    public static TransactionBase getCurrentFlow() {
        block4: {
            block3: {
                if (currentQueryId != null) break block3;
                if (transactionList == null) break block4;
                for (TransactionContext ctx : transactionList) {
                    if (!ctx.getTransactionId().equals(currentTransactionFlowId)) continue;
                    return ctx;
                }
                break block4;
            }
            if (queryList != null) {
                for (QueryContext ctx : queryList) {
                    if (!ctx.getTransactionId().equals(currentQueryId)) continue;
                    return ctx;
                }
            }
        }
        return null;
    }

    public static TransactionContext getTransactionFlow(String flowId) {
        if (transactionList != null) {
            for (TransactionContext ctx : transactionList) {
                if (!ctx.getTransactionId().equals(flowId)) continue;
                return ctx;
            }
        }
        return null;
    }

    public static QueryContext getQuery(String queryId) {
        if (queryList != null) {
            for (QueryContext ctx : queryList) {
                if (!ctx.getTransactionId().equals(queryId)) continue;
                return ctx;
            }
        }
        return null;
    }

    public static transactionType[] getFlowTransactions() {
        return ConfigContext.getFlowTransactions(null);
    }

    /*
     * Unable to fully structure code
     */
    public static transactionType[] getFlowTransactions(String transactionBefore) {
        block4: {
            block5: {
                xfn = new ArrayList<transactionType>();
                ctx = ConfigContext.getCurrentFlow();
                if (ctx == null) break block5;
                trName = ctx.retrieveParameter("tranname").getParameterValue();
                if (trName == null || (ctr = ConfigContext.getCurrentTransaction(trName)) == null) break block4;
                xfn.add(ctr);
                if (!trName.equals(transactionBefore)) ** GOTO lbl21
                return xfn.toArray(new transactionType[0]);
lbl-1000:
                // 1 sources

                {
                    try {
                        trName = ConfigContext.getNextTransactionName(ctr.getnexttransaction().getname().getValue().trim(), ctx.getTransactionId());
                        if (trName.length() > 0) {
                            ctr = ConfigContext.getCurrentTransaction(trName);
                            xfn.add(ctr);
                            if (transactionBefore == null || !transactionBefore.equals(trName)) continue;
                        }
                        break block4;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
lbl21:
                    // 2 sources

                    ** while (ctr.getnexttransactionCount() > 0)
                }
lbl22:
                // 1 sources

                break block4;
            }
            return null;
        }
        return xfn.toArray(new transactionType[0]);
    }

    private static ArrayList<IwConnection> getUniqueProjectConnections(IProject cpr) {
        iwmappingsType iwt;
        if (cpr == null) {
            return null;
        }
        IPath clc = cpr.getLocation();
        if (clc == null) {
            return null;
        }
        ArrayList<IwConnection> puc = new ArrayList<IwConnection>();
        try {
            iwt = new iwmappingsType(new iwtransactionsDoc().load(clc + "/xslt/Site/new/xml/transactions.xml"));
        }
        catch (RuntimeException e1) {
            return null;
        }
        if (iwt == null) {
            return null;
        }
        int tcn = iwt.gettransactionCount();
        int i = 0;
        while (i < tcn) {
            try {
                transactionType ctr = iwt.gettransactionAt(i);
                if (ctr == null) {
                    return null;
                }
                int dmn = ctr.getdatamapCount();
                int j = 0;
                while (j < dmn) {
                    IwConnection icn = new IwConnection(ctr.getdatamapAt(j));
                    if (!icn.isEmpty() && !puc.contains(icn)) {
                        puc.add(icn);
                    }
                    ++j;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            ++i;
        }
        return puc;
    }

    public static IwConnection getCurrentConnection() {
        if (currentConnectionName.trim().length() == 0) {
            return null;
        }
        ArrayList<IwConnection> puc = ConfigContext.getUniqueProjectConnections(Designer.getSelectedProject());
        String[] connm = ConfigContext.getProjectConnectionNames(Designer.getSelectedProject(), false);
        if (puc == null) {
            return null;
        }
        int i = 0;
        while (i < connm.length) {
            if (connm[i].equals(currentConnectionName)) {
                return puc.get(i);
            }
            ++i;
        }
        return null;
    }

    public static String[] getProjectConnectionNames(IProject cpr, boolean withEmpty) {
        ArrayList<IwConnection> puc = ConfigContext.getUniqueProjectConnections(cpr);
        ArrayList<String> pnm = new ArrayList<String>();
        if (withEmpty) {
            pnm.add("");
        }
        for (IwConnection cpc : puc) {
            String unm = cpc.getUserName();
            if (unm.trim().length() == 0 || pnm.contains(unm)) {
                String enm = String.valueOf(unm) + "[" + cpc.getUrl() + "]";
                if (enm.trim().equals("[]") || pnm.contains(enm)) {
                    pnm.add(String.valueOf(enm) + "{" + cpc.getDriver() + "}");
                    continue;
                }
                pnm.add(enm);
                continue;
            }
            pnm.add(unm);
        }
        return pnm.toArray(new String[0]);
    }

    public static String getDataMapConnectionName(IProject cpr, datamapType dmt) {
        ArrayList<IwConnection> puc = ConfigContext.getUniqueProjectConnections(cpr);
        ArrayList<String> pnm = new ArrayList<String>();
        String cnm = null;
        IwConnection icn = new IwConnection(dmt);
        for (IwConnection cpc : puc) {
            cnm = cpc.getUserName();
            if ((cnm.trim().length() == 0 || pnm.contains(cnm)) && ((cnm = String.valueOf(cnm) + "[" + cpc.getUrl() + "]").trim().equals("[]") || pnm.contains(cnm))) {
                cnm = String.valueOf(cnm) + "{" + cpc.getDriver() + "}";
            }
            if (cpc.equals(icn)) {
                return cnm;
            }
            pnm.add(cnm);
        }
        return null;
    }

    public static final void setCurrentQueryId(String currentQueryId) {
        ConfigContext.currentQueryId = currentQueryId;
    }

    public static final void setCurrentTransactionFlowId(String currentTransactionFlowId) {
        ConfigContext.currentTransactionFlowId = currentTransactionFlowId;
    }

    public static final void setCurrentDataMapName(String currentDataMapName) {
        ConfigContext.currentDataMapName = currentDataMapName;
    }

    public static final void setCurrentConnectionName(String currentConnectionName) {
        ConfigContext.currentConnectionName = currentConnectionName;
    }

    public static final String[] getNamelessParameterList(int parameterNumber) {
        String[] params = new String[parameterNumber];
        int i = 0;
        while (i < parameterNumber) {
            params[i] = "Parameter_" + String.valueOf(i);
            ++i;
        }
        return params;
    }

    public static void setCurrentParameterName(String currentParameterName) {
        ConfigContext.currentParameterName = currentParameterName;
    }

    public static parameterType getCurrentParameter() {
        try {
            if (currentParameterName == null) {
                return null;
            }
            datamapType dmt = ConfigContext.getCurrentDataMap();
            if (dmt == null) {
                return null;
            }
            accessType atp = dmt.getaccess();
            if (atp == null) {
                return null;
            }
            parameterType rt = null;
            parameterlist pl = null;
            int pndx = Integer.valueOf(currentParameterName.substring(1).trim());
            switch (currentParameterName.charAt(0)) {
                case 'V': {
                    if (atp.getvaluesCount() <= 0) break;
                    pl = atp.getvalues();
                    break;
                }
                case 'W': {
                    if (atp.getwhereCount() <= 0) break;
                    pl = atp.getwhere();
                    break;
                }
                case 'O': {
                    if (atp.getoutputsCount() <= 0) break;
                    pl = atp.getoutputs();
                }
            }
            if (pl != null && pl.getparameterCount() > pndx) {
                rt = pl.getparameterAt(pndx);
            }
            return rt;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentParameterName() {
        return currentParameterName;
    }

    public static void setCurrentXsltName(String currentXsltName) {
        ConfigContext.currentXsltName = currentXsltName;
    }

    public static Hashtable<String, String[]> getSourseFields(int xsltType, Hashtable<String, String[]> destFields) {
        int rpp;
        int lpp;
        ArrayList<String> clmns;
        String ixs = ConfigContext.readXSLTwoBS(true);
        if (ixs == null) {
            return null;
        }
        Hashtable<String, String[]> ott = new Hashtable<String, String[]>();
        int mpix = ixs.indexOf(mapName);
        if (mpix > 0) {
            clmns = ConfigContext.getColNames(xsltType, ixs.substring(0, mpix), destFields);
            if (clmns.size() > 0) {
                ott.put("DataMap[]", clmns.toArray(new String[0]));
            }
            while (mpix < ixs.length()) {
                int mpns = mpix += mapName.length();
                int mpnf = ixs.indexOf("'", mpix);
                if (mpnf < 0) break;
                String mpnm = "DataMap[" + ixs.substring(mpns, mpnf) + "]";
                if ((mpix = ixs.indexOf(mapName, mpix)) < 0) {
                    clmns = ConfigContext.getColNames(xsltType, ixs.substring(mpnf, ixs.length()), destFields);
                    if (clmns.size() > 0) {
                        ConfigContext.addToSourses(mpnm, ott, clmns);
                    }
                    break;
                }
                clmns = ConfigContext.getColNames(xsltType, ixs.substring(mpnf, mpix), destFields);
                if (clmns.size() <= 0) continue;
                ConfigContext.addToSourses(mpnm, ott, clmns);
            }
        } else {
            clmns = ConfigContext.getColNames(xsltType, ixs, destFields);
            if (clmns.size() > 0) {
                ott.put("DataMap[]", clmns.toArray(new String[0]));
            }
        }
        if (xsltType == 1 && (lpp = ixs.indexOf("(")) > 0 && (rpp = ixs.indexOf(")", lpp)) > 0) {
            String[] dsf = ixs.substring(lpp + 1, rpp).split(",");
            int idsf = 0;
            Set<String> ks = ott.keySet();
            Iterator<String> kit = ks.iterator();
            block1: while (kit.hasNext() && idsf < dsf.length) {
                String[] cols;
                String mn = kit.next();
                String[] stringArray = cols = ott.get(mn);
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    String cf = stringArray[n];
                    if (idsf >= dsf.length) continue block1;
                    destFields.put(dsf[idsf++], new String[]{cf});
                    ++n;
                }
            }
        }
        return ott;
    }

    public static Vector<Integer[]> getMappingPositions(String destField, String[] sourceFields) {
        return null;
    }

    public static String[] getTemplateVariables(String template) {
        int vi;
        ArrayList<String> ots = new ArrayList<String>();
        int vib = 0;
        String varTag = "<xsl:variable";
        String nameTag = "name=\"";
        while ((vi = template.indexOf(varTag, vib)) >= 0) {
            int ni = template.indexOf(nameTag, vib + varTag.length());
            if (ni < 0) {
                return null;
            }
            int ne = template.indexOf("\"", ni += nameTag.length());
            if (ne < 0) {
                return null;
            }
            ots.add(template.substring(ni, ne));
            vib = ne + 1;
        }
        return ots.toArray(new String[0]);
    }

    private static void addToSourses(String key, Hashtable<String, String[]> ott, ArrayList<String> clmns) {
        String[] cols = ott.get(key);
        if (cols == null) {
            ott.put(key, clmns.toArray(new String[0]));
        } else {
            ArrayList<String> nl = new ArrayList<String>();
            String[] stringArray = cols;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cs = stringArray[n];
                nl.add(cs);
                ++n;
            }
            nl.addAll(clmns);
            ott.put(key, nl.toArray(new String[0]));
        }
    }

    private static void addToDestinations(String key, Hashtable<String, String[]> ott, String sourse) {
        String[] cols = ott.get(key);
        if (cols == null) {
            ott.put(key, new String[]{sourse});
        } else {
            ArrayList<String> nl = new ArrayList<String>();
            boolean sourceIncluded = false;
            String[] stringArray = cols;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cs = stringArray[n];
                nl.add(cs);
                if (cs.equals(sourse)) {
                    sourceIncluded = true;
                }
                ++n;
            }
            if (!sourceIncluded) {
                nl.add(sourse);
            }
            ott.put(key, nl.toArray(new String[0]));
        }
    }

    private static ArrayList<String> getColNames(int xType, String xsltSubString, Hashtable<String, String[]> destsFields) {
        int idx = 0;
        ArrayList<String> outList = new ArrayList<String>();
        block6: while (idx < xsltSubString.length()) {
            if ((idx = xsltSubString.indexOf(colName, idx)) < 0) break;
            int ido = idx;
            int idf = xsltSubString.indexOf("'", idx += colName.length());
            if (idf < 0) break;
            String sfn = xsltSubString.substring(idx, idf);
            outList.add(sfn);
            switch (xType) {
                case -1: {
                    break;
                }
                case 0: {
                    int eqp;
                    if (ido < valueTag.length() || (eqp = xsltSubString.lastIndexOf("=", ido - valueTag.length())) < 0) continue block6;
                    int en = eqp;
                    while (xsltSubString.charAt(en--) == ' ' && en > 0) {
                    }
                    if (en <= 0) break;
                    int bn = xsltSubString.lastIndexOf(" ", en);
                    ConfigContext.addToDestinations(xsltSubString.substring(bn + 1, en + 1), destsFields, sfn);
                    break;
                }
                case 1: {
                    break;
                }
                case 2: 
                case 3: {
                    int en;
                    int bn;
                    if (ido < valueTag.length() || (bn = xsltSubString.lastIndexOf("<", ido)) < 0 || (bn = xsltSubString.lastIndexOf("<", bn - 1)) < 0) continue block6;
                    int en0 = xsltSubString.indexOf(">", bn);
                    if (en0 < 0) {
                        en0 = Integer.MAX_VALUE;
                    }
                    if ((en = Math.min(xsltSubString.indexOf(" ", bn), en0)) <= 0) break;
                    ConfigContext.addToDestinations(xsltSubString.substring(bn + 1, en), destsFields, sfn);
                }
            }
        }
        return outList;
    }

    public static String readXSLTwoBS(boolean replace) {
        return ConfigContext.readXSLTwoBS(currentXsltName, replace);
    }

    public static String readXSLTwoBS(String xsltName, boolean replace) {
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            return null;
        }
        if (xsltName != null) {
            IPath clc = cpr.getLocation();
            if (clc == null) {
                return null;
            }
            String ret = ConfigContext.readFile(clc + "/xslt/" + xsltName + ".xslt").trim();
            if (replace) {
                ret = ret.replaceAll("\\s+", " ");
            }
            return ret;
        }
        return null;
    }

    public static String readTemplateWoBS(boolean replace) {
        IProject cpr = Designer.getSelectedProject();
        if (cpr == null) {
            return null;
        }
        if (currentTemplateName != null) {
            IPath clc = cpr.getLocation();
            if (clc == null) {
                return null;
            }
            String ret = ConfigContext.readFile(clc + "/xslt/Site/" + currentTemplateName + ".iwxt").trim();
            if (ret == null) {
                return null;
            }
            if (replace) {
                ret = ret.replaceAll("\\s+", " ");
            }
            return ret;
        }
        return null;
    }

    public static int getXsltType(String xsltString) {
        if (xsltString.indexOf("xsl:output method=\"text\"") > 0) {
            if (xsltString.indexOf("='" + valueTag + colName) > 0 || xsltString.indexOf("=" + valueTag + colName) > 0) {
                return 0;
            }
            return 1;
        }
        if (xsltString.indexOf("xsl:output method=\"xml\"") > 0) {
            if (xsltString.indexOf("Envelop") > 0 && xsltString.indexOf("Body") > 0) {
                return 2;
            }
            return 3;
        }
        return -1;
    }

    public static String getCurrentXsltName() {
        return currentXsltName;
    }

    public static void copyFileToProject(IProject newProjectHandle, String filePath) throws IOException, CoreException {
        ConfigContext.copyFileToProject(newProjectHandle, filePath, filePath, false);
    }

    public static void saveFileInProject(IProject newProjectHandle, String filePath, String fileContent, boolean overwrite) throws IOException, CoreException {
        IFile newFile = newProjectHandle.getFile(filePath);
        if (overwrite && newFile.exists()) {
            int i = 0;
            while (i < 2000) {
                try {
                    newFile.delete(true, null);
                    break;
                }
                catch (CoreException e) {
                    newProjectHandle.touch(null);
                    newProjectHandle.refreshLocal(2, null);
                    newProjectHandle.clearHistory(null);
                    newFile.touch(null);
                    newFile.refreshLocal(2, null);
                    newFile.clearHistory(null);
                    try {
                        Thread.sleep(10L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    ++i;
                }
            }
        }
        ByteArrayInputStream bfis = new ByteArrayInputStream(fileContent.getBytes());
        newFile.create((InputStream)bfis, true, null);
        bfis.close();
    }

    public static void copyFileToProject(IProject newProjectHandle, String inFilePath, String outFilePath, boolean overwrite) throws IOException, CoreException {
        URL fp;
        IFile newFile = newProjectHandle.getFile(outFilePath);
        if (overwrite && newFile.exists()) {
            newFile.delete(true, null);
        }
        if ((fp = Iw_sdkPlugin.fileURLFromPlugin(inFilePath)) == null) {
            return;
        }
        BufferedInputStream bfis = new BufferedInputStream(fp.openStream());
        newFile.create((InputStream)bfis, true, null);
        bfis.close();
    }

    /*
     * Unable to fully structure code
     */
    public static void modifyJar(String nameOfJar, String[] fileNameToAdd, String[] entryToAdd, String[] entryToRemove) throws IOException {
        block34: {
            jarFile = new File(String.valueOf(nameOfJar) + ".war");
            tempJar = new File(String.valueOf(nameOfJar) + ".new");
            if (tempJar.exists()) {
                tempJar.delete();
                if (tempJar.createNewFile()) {
                    return;
                }
            }
            jar = null;
            jar = new JarFile(jarFile);
            try {
                newJar = new JarOutputStream(new FileOutputStream(tempJar));
                buffer = new byte[10240];
                try {
                    entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        entry = entries.nextElement();
                        if (entryToRemove != null) {
                            remove = false;
                            var16_23 = entryToRemove;
                            var14_20 = 0;
                            var15_22 = var16_23.length;
                            while (var14_20 < var15_22) {
                                ce = var16_23[var14_20];
                                if (entry.getName().equals(ce)) {
                                    remove = true;
                                    break;
                                }
                                ++var14_20;
                            }
                            if (remove) continue;
                        }
                        is = jar.getInputStream(entry);
                        newJar.putNextEntry(entry);
                        while ((bytesRead = is.read(buffer)) != -1) {
                            newJar.write(buffer, 0, bytesRead);
                        }
                    }
                    if (fileNameToAdd == null) break block34;
                    i = 0;
                    while (i < fileNameToAdd.length) {
                        block35: {
                            fis = fileNameToAdd[i] == null ? null : new FileInputStream(fileNameToAdd[i]);
                            try {
                                entry = new JarEntry(entryToAdd[i]);
                                newJar.putNextEntry(entry);
                                if (fis != null) ** GOTO lbl54
                                buffer = ConfigContext.optimizeTransactions().getBytes();
                                newJar.write(buffer, 0, buffer.length);
                                break block35;
lbl-1000:
                                // 1 sources

                                {
                                    newJar.write(buffer, 0, bytesRead);
lbl54:
                                    // 2 sources

                                    ** while ((bytesRead = fis.read((byte[])buffer)) != -1)
                                }
lbl55:
                                // 1 sources

                            }
                            finally {
                                if (fis != null) {
                                    fis.close();
                                }
                            }
                        }
                        ++i;
                    }
                }
                finally {
                    try {
                        newJar.close();
                    }
                    catch (IOException var18_26) {}
                }
            }
            finally {
                try {
                    jar.close();
                }
                catch (IOException var20_29) {}
            }
        }
        if (jarFile.delete()) {
            tempJar.renameTo(jarFile);
        }
    }

    public static boolean saveTransactions() throws Exception {
        IProject cp = Designer.getSelectedProject();
        if (cp == null) {
            return false;
        }
        return ConfigContext.saveTransactions(cp);
    }

    public static boolean saveTransactions(IProject newProjectHandle) throws Exception {
        if (iwmappingsRoot != null) {
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            iwtransactionsDoc doc = new iwtransactionsDoc();
            doc.save(ous, (Node)iwmappingsRoot);
            String filePath = "xslt/Site/new/xml/transactions.xml";
            try {
                try {
                    ConfigContext.saveFileInProject(newProjectHandle, filePath, new String(ous.toByteArray()), true);
                }
                catch (IOException e) {
                    throw e;
                }
                catch (CoreException e) {
                    throw e;
                }
            }
            finally {
                ous.close();
            }
            return true;
        }
        return false;
    }

    public static String optimizeTransactions() {
        if (iwmappingsRoot != null) {
            iwmappingsType or = new iwmappingsType();
            try {
                int l0 = 0;
                while (l0 < iwmappingsRoot.gettransactionCount()) {
                    nexttransactionType cnt;
                    transactionType ntr = new transactionType();
                    transactionType otr = iwmappingsRoot.gettransactionAt(l0);
                    ntr.addname(otr.getname());
                    ntr.addtype(otr.gettype());
                    ntr.addtransform(otr.gettransform());
                    if (otr.getclassnameCount() > 0) {
                        ntr.addclassname(otr.getclassname());
                    }
                    if (otr.getnexttransactionCount() > 0 && (cnt = otr.getnexttransaction()).getname().getValue().trim().length() > 0) {
                        ntr.addnexttransaction(cnt);
                    }
                    int l1 = 0;
                    while (l1 < otr.getdatamapCount()) {
                        datamapType ndm = new datamapType();
                        datamapType odm = otr.getdatamapAt(l1);
                        ndm.addname(odm.getname());
                        ndm.adddriver(odm.getdriver());
                        ndm.addurl(odm.geturl());
                        ndm.adduser(odm.getuser());
                        ndm.addpassword(odm.getpassword());
                        int l2 = 0;
                        while (l2 < odm.getaccessCount()) {
                            parameterlist oopl;
                            parameterlist nopl;
                            parameterlist ovpl;
                            parameterlist nvpl;
                            parameterlist owpl;
                            parameterlist nwpl;
                            accessType nat = new accessType();
                            accessType oat = odm.getaccessAt(l2);
                            nat.addtype(oat.gettype());
                            nat.addstatementpre(oat.getstatementpre());
                            nat.addstatementpost(oat.getstatementpost());
                            if (oat.gettranslatorCount() > 0) {
                                boolean hasOutputClass;
                                translatorType otrt = oat.gettranslator();
                                boolean hasInputClass = otrt.getinputclassCount() > 0 && otrt.getinputclass().getValue().trim().length() > 0;
                                boolean bl = hasOutputClass = otrt.getoutputclassCount() > 0 && otrt.getoutputclass().getValue().trim().length() > 0;
                                if (hasInputClass || hasOutputClass) {
                                    translatorType ntrt = new translatorType();
                                    if (hasInputClass) {
                                        ntrt.addinputclass(otrt.getinputclass());
                                    }
                                    if (hasOutputClass) {
                                        ntrt.addoutputclass(otrt.getoutputclass());
                                    }
                                    nat.addtranslator(ntrt);
                                }
                            }
                            if (oat.getwhereCount() > 0 && (nwpl = ConfigContext.cloneParameters(owpl = oat.getwhere())) != null) {
                                nat.addwhere(nwpl);
                            }
                            if (oat.getvaluesCount() > 0 && (nvpl = ConfigContext.cloneParameters(ovpl = oat.getvalues())) != null) {
                                nat.addvalues(nvpl);
                            }
                            if (oat.getoutputsCount() > 0 && (nopl = ConfigContext.cloneParameters(oopl = oat.getoutputs())) != null) {
                                nat.addoutputs(nopl);
                            }
                            ndm.addaccess(nat);
                            ++l2;
                        }
                        ntr.adddatamap(ndm);
                        ++l1;
                    }
                    or.addtransaction(ntr);
                    ++l0;
                }
                ByteArrayOutputStream ous = new ByteArrayOutputStream();
                iwtransactionsDoc doc = new iwtransactionsDoc();
                doc.setRootElementName(null, "iwmappings");
                doc.save(ous, (Node)or);
                String iwm = new String(ous.toByteArray());
                int ti = iwm.indexOf("<transaction");
                if (ti < 0) {
                    return null;
                }
                iwm = iwm.substring(ti);
                return "<iwmappings>" + iwm;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private static parameterlist cloneParameters(parameterlist ogpl) throws Exception {
        int pc = ogpl.getparameterCount();
        if (pc > 0) {
            parameterlist ngpl = new parameterlist();
            int i = 0;
            while (i < pc) {
                parameterType npr = new parameterType();
                parameterType opr = ogpl.getparameterAt(i);
                if (opr.gettranslatorCount() > 0) {
                    boolean hasOutputClass;
                    translatorType otrt = opr.gettranslator();
                    boolean hasInputClass = otrt.getinputclassCount() > 0 && otrt.getinputclass().getValue().trim().length() > 0;
                    boolean bl = hasOutputClass = otrt.getoutputclassCount() > 0 && otrt.getoutputclass().getValue().trim().length() > 0;
                    if (hasInputClass || hasOutputClass) {
                        translatorType ntrt = new translatorType();
                        if (hasInputClass) {
                            ntrt.addinputclass(otrt.getinputclass());
                        }
                        if (hasOutputClass) {
                            ntrt.addoutputclass(otrt.getoutputclass());
                        }
                        npr.addtranslator(ntrt);
                    }
                }
                npr.addinput(opr.getinput());
                npr.addmapping(opr.getmapping());
                ngpl.addparameter(npr);
                ++i;
            }
            return ngpl;
        }
        return null;
    }

    public static String getCurrentConnectionName() {
        return currentConnectionName;
    }

    public static String getCurrentTransactionFlowId() {
        return currentTransactionFlowId;
    }

    public static void loadIwmappingsRoot(IProject cp) {
        iwmappingsRoot = null;
        if (cp != null) {
            iwtransactionsDoc doc = new iwtransactionsDoc();
            iwmappingsRoot = new iwmappingsType(doc.load(cp.getLocation() + "/xslt/Site/new/xml/transactions.xml"));
        }
    }

    public static String getNextTransactionName(String transactionNames, String currentFlow) {
        if (transactionNames.indexOf(":") > 0 && transactionNames.indexOf(";") > 0) {
            String[] perFlow;
            String[] stringArray = perFlow = transactionNames.split(";");
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnm = stringArray[n];
                int dlm = cnm.indexOf(":");
                if (dlm > 0 && cnm.substring(0, dlm).equals(currentFlow)) {
                    return cnm.substring(dlm + 1);
                }
                ++n;
            }
            return "";
        }
        return transactionNames;
    }

    public static String removeNextTransactionName(String currentNames, String currentFlow) {
        return ConfigContext.renameNextTransactionName(currentNames, currentFlow, null, false);
    }

    public static String renameNextTransactionName(String currentNames, String oldFlow, String newFlow, boolean append) {
        if (currentNames.indexOf(":") > 0 && currentNames.indexOf(";") > 0) {
            String[] perFlow = currentNames.split(";");
            StringBuffer ret = new StringBuffer();
            String[] stringArray = perFlow;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnm = stringArray[n];
                int dlm = cnm.indexOf(":");
                if (dlm > 0) {
                    if (!cnm.substring(0, dlm).equals(oldFlow)) {
                        ret.append(String.valueOf(cnm) + ";");
                    } else if (newFlow != null) {
                        if (append) {
                            ret.append(String.valueOf(cnm) + ";");
                        }
                        ret.append(String.valueOf(newFlow) + ":" + cnm.substring(dlm + 1) + ";");
                    }
                }
                ++n;
            }
            return ret.toString();
        }
        return "";
    }

    public static boolean renameAllNextTransactions(String oldFlow, String newFlow) {
        return ConfigContext.processAllNextTransactions(oldFlow, newFlow, false);
    }

    public static boolean cloneAllNextTransactions(String oldFlow, String newFlow) {
        return ConfigContext.processAllNextTransactions(oldFlow, newFlow, true);
    }

    public static boolean processAllNextTransactions(String oldFlow, String newFlow, boolean append) {
        transactionType[] aft = ConfigContext.getFlowTransactions();
        try {
            transactionType[] transactionTypeArray = aft;
            int n = 0;
            int n2 = transactionTypeArray.length;
            while (n < n2) {
                transactionType ct = transactionTypeArray[n];
                if (ct.getnexttransactionCount() > 0) {
                    nexttransactionType nt = ct.getnexttransaction();
                    if (nt.getnameCount() > 0) {
                        String ntl = nt.getname().getValue().trim();
                        String ntln = ConfigContext.renameNextTransactionName(ntl, oldFlow, newFlow, append);
                        if (ntln.length() == 0) {
                            ct.removenexttransaction();
                        } else {
                            nt.replacenameAt(ntln, 0);
                        }
                    } else {
                        ct.removenexttransaction();
                    }
                }
                ++n;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static String upsertNextTransactionName(String transactionName, String currentFlow, String currentNames, boolean reassign) {
        String newName = String.valueOf(currentFlow) + ":" + transactionName + ";";
        if (currentNames.indexOf(":") > 0 && currentNames.indexOf(";") > 0) {
            String[] perFlow = currentNames.split(";");
            StringBuffer ret = new StringBuffer();
            boolean app = false;
            String[] stringArray = perFlow;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnm = stringArray[n];
                int dlm = cnm.indexOf(":");
                if (dlm > 0) {
                    if (cnm.substring(0, dlm).equals(currentFlow)) {
                        app = true;
                        ret.append(newName);
                    } else {
                        ret.append(String.valueOf(cnm) + ";");
                    }
                }
                ++n;
            }
            if (!app) {
                ret.append(newName);
            }
            return ret.toString();
        }
        if (currentNames.equals(transactionName)) {
            return newName;
        }
        if (reassign) {
            return newName;
        }
        return null;
    }

    public static void setIwmappingsRoot(iwmappingsType iwmappingsRoot) {
        ConfigContext.iwmappingsRoot = iwmappingsRoot;
    }

    public static boolean isHosted() {
        return hosted;
    }

    public static void setHosted(boolean imHosted) {
        hosted = imHosted;
    }

    public static long getRefreshInterval() {
        return refreshInterval;
    }

    public static void setRefreshInterval(long refreshInterval) {
        ConfigContext.refreshInterval = refreshInterval;
    }

    public static void upgradeProject(IProject cpr) throws Exception {
        String newNames;
        String oldNames;
        nexttransactionType cnt;
        transactionType ctr;
        String trName;
        IFile str = cpr.getFile("xslt/Site/include/sitetran_host.xslt");
        if (!str.exists()) {
            ConfigContext.copyFileToProject(cpr, "xslt/Site/include/sitetran_host.xslt");
        }
        if (!(str = cpr.getFile("xslt/Site/include/sitetran_ent.xslt")).exists()) {
            ConfigContext.copyFileToProject(cpr, "xslt/Site/include/sitetran_ent.xslt");
        }
        if (!(str = cpr.getFile("xslt/Site/new/include/soltran_start.dat")).exists()) {
            ConfigContext.copyFileToProject(cpr, "xslt/Site/new/include/soltran_start.dat");
        }
        if (!(str = cpr.getFile("xslt/Site/new/include/soltran_end.dat")).exists()) {
            ConfigContext.copyFileToProject(cpr, "xslt/Site/new/include/soltran_end.dat");
        }
        if (!(str = cpr.getFile("xslt/Site/new/transactions.xslt")).exists()) {
            ConfigContext.copyFileToProject(cpr, "xslt/Site/new/transactions.xslt");
        }
        if (!ConfigContext.readIMConfigContext(cpr)) {
            throw new Exception("Unable to read IM Config");
        }
        iwmappingsType iwt = iwmappingsRoot;
        iwmappingsRoot = new iwmappingsType(new iwtransactionsDoc().load(cpr.getLocation() + "/xslt/Site/new/xml/transactions.xml"));
        block0: for (TransactionContext ctx : transactionList) {
            if (ctx == null) continue;
            String ctxId = ctx.getTransactionId();
            trName = ctx.retrieveParameter("tranname").getParameterValue();
            if (trName == null || (ctr = ConfigContext.getCurrentTransaction(trName)) == null) continue;
            while (ctr.getnexttransactionCount() > 0) {
                cnt = ctr.getnexttransaction();
                oldNames = cnt.getname().getValue().trim();
                trName = ConfigContext.getNextTransactionName(oldNames, ctxId);
                if (trName.length() <= 0) continue block0;
                newNames = ConfigContext.upsertNextTransactionName(trName, ctxId, oldNames, false);
                if (newNames == null) {
                    iwmappingsRoot = iwt;
                    throw new Exception("Unable to create next transaction reference");
                }
                cnt.replacenameAt(newNames, 0);
                ctr = ConfigContext.getCurrentTransaction(trName);
            }
        }
        block2: for (QueryContext cqx : queryList) {
            if (cqx == null) continue;
            String cqxId = cqx.getTransactionId();
            trName = cqx.retrieveParameter("tranname").getParameterValue();
            if (trName == null || (ctr = ConfigContext.getCurrentTransaction(trName)) == null) continue;
            while (ctr.getnexttransactionCount() > 0) {
                cnt = ctr.getnexttransaction();
                oldNames = cnt.getname().getValue().trim();
                trName = ConfigContext.getNextTransactionName(oldNames, cqxId);
                if (trName.length() <= 0) continue block2;
                newNames = ConfigContext.upsertNextTransactionName(trName, cqxId, oldNames, false);
                if (newNames == null) {
                    iwmappingsRoot = iwt;
                    throw new Exception("Unable to create next transaction reference");
                }
                cnt.replacenameAt(newNames, 0);
                ctr = ConfigContext.getCurrentTransaction(trName);
            }
        }
        ConfigContext.saveTransactions(cpr);
        iwmappingsRoot = iwt;
        ConfigContext.copyFileToProject(cpr, "configuration/project.properties", "configuration/" + cpr.getName() + ".properties", true);
    }

    public static Integer loadProjectVersion(IProject cpr) throws Exception {
        IFile pf = cpr.getFile("configuration/" + cpr.getName() + ".properties");
        if (pf.exists()) {
            Properties prp = new Properties();
            InputStream is = pf.getContents(true);
            prp.load(is);
            is.close();
            String pv = prp.getProperty("COMPLIENT_PROJECT_VERSION");
            if (pv == null) {
                return null;
            }
            return Integer.valueOf(pv);
        }
        return null;
    }

    public static Integer loadHostedVersion(IProject cpr) throws Exception {
        IFile pf = cpr.getFile("configuration/" + cpr.getName() + ".properties");
        if (pf.exists()) {
            Properties prp = new Properties();
            InputStream is = pf.getContents(true);
            prp.load(is);
            is.close();
            String pv = prp.getProperty("COMPLIENT_HOSTED_VERSION");
            if (pv == null) {
                return null;
            }
            return Integer.valueOf(pv);
        }
        return null;
    }

    public static IProject[] getSelectedProjects(NavigationView nv) {
        StructuredSelection selection = (StructuredSelection)nv.getViewer().getSelection();
        Iterator sit = selection.iterator();
        IProject[] prt = new IProject[selection.size()];
        int pi = 0;
        while (sit.hasNext()) {
            NavigationView.TreeParent spo;
            NavigationView.TreeObject ston = (NavigationView.TreeObject)sit.next();
            if (ston == null) continue;
            while ((spo = ston.getParent()) != null) {
                if (spo.getName().equals("")) {
                    prt[pi] = nv.getProjectByName(ston);
                    break;
                }
                ston = spo;
            }
            ++pi;
        }
        return prt;
    }

    public static IProject getProjectByName(String name) {
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        IProject pr = null;
        IProject[] iProjectArray = projects;
        int n = 0;
        int n2 = iProjectArray.length;
        while (n < n2) {
            IProject cpr = iProjectArray[n];
            if (name.equals(cpr.getName().trim())) {
                pr = cpr;
                break;
            }
            ++n;
        }
        return pr;
    }

    public static boolean pasteEnabled() {
        return prClp != null && !prClp.isEmpty() || tcClp != null && !tcClp.isEmpty() || qcClp != null && !qcClp.isEmpty() || trtClp != null && !trtClp.isEmpty() || xsltClp != null && !xsltClp.isEmpty() || templateClp != null && !templateClp.isEmpty();
    }

    public static void cleanClipboard() {
        prClp = new ArrayList();
        tcClp = new ArrayList();
        qcClp = new ArrayList();
        trtClp = new ArrayList();
        xsltClp = new ArrayList();
        templateClp = new ArrayList();
        tcClpMove = new ArrayList();
        qcClpMove = new ArrayList();
        tcClpCopy = new ArrayList();
        qcClpCopy = new ArrayList();
    }

    public static boolean cloneTransactions(String dest) {
        transactionType[] fts = ConfigContext.getFlowTransactions();
        if (fts == null) {
            return false;
        }
        transactionType[] transactionTypeArray = fts;
        int n = 0;
        int n2 = transactionTypeArray.length;
        while (n < n2) {
            transactionType ct = transactionTypeArray[n];
            if (ct.getnexttransactionCount() > 0) {
                try {
                    nexttransactionType nt = ct.getnexttransaction();
                    if (nt.getnameCount() > 0) {
                        String oldNames = nt.getname().getValue().trim();
                        String newName = ConfigContext.upsertNextTransactionName(ConfigContext.getNextTransactionName(oldNames, ConfigContext.getCurrentFlow().getTransactionId()), dest, oldNames, true);
                        if (newName == null) {
                            return false;
                        }
                        nt.replacenameAt(newName, 0);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            ++n;
        }
        return true;
    }

    public static void openXsltEditor(CCombo transformerCombo) {
        int si = transformerCombo.getSelectionIndex();
        if (si < 0) {
            return;
        }
        String xsltName = transformerCombo.getItem(si);
        if (xsltName == null || xsltName.trim().length() == 0) {
            return;
        }
        IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        ap.setPerspective(PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("iw_sdk.te_perspective"));
        try {
            ap.showView("iw_sdk.XSLTEditorView");
        }
        catch (PartInitException e) {
            e.printStackTrace();
        }
        ConfigContext.setCurrentXsltName(xsltName);
        ((XSLTEditorView)ap.findView("iw_sdk.XSLTEditorView")).initializeScreen();
    }

    public static boolean resetBaseTSUrl(String name) {
        String pts = primaryTransformationServerURL;
        String sts = secondaryTransformationServerURL;
        String ptst = primaryTransformationServerURLT;
        String stst = secondaryTransformationServerURLT;
        String pts1 = primaryTransformationServerURL1;
        String sts1 = secondaryTransformationServerURL1;
        String ptst1 = primaryTransformationServerURLT1;
        String stst1 = secondaryTransformationServerURLT1;
        String ptsd = primaryTransformationServerURLD;
        String stsd = secondaryTransformationServerURLD;
        if (pts == null || pts.trim().length() == 0) {
            pts = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pu = null;
        try {
            pu = new URL(pts);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL su = null;
        if (sts != null && sts.trim().length() > 0) {
            try {
                su = new URL(sts);
            }
            catch (MalformedURLException e) {
                su = null;
            }
        }
        if (ptst == null || ptst.trim().length() == 0) {
            ptst = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL put = null;
        try {
            put = new URL(ptst);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sut = null;
        if (stst != null && stst.trim().length() > 0) {
            try {
                sut = new URL(stst);
            }
            catch (MalformedURLException e) {
                sut = null;
            }
        }
        if (pts1 == null || pts1.trim().length() == 0) {
            pts1 = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pu1 = null;
        try {
            pu1 = new URL(pts1);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL su1 = null;
        if (sts1 != null && sts1.trim().length() > 0) {
            try {
                su1 = new URL(sts1);
            }
            catch (MalformedURLException e) {
                su1 = null;
            }
        }
        if (ptst1 == null || ptst1.trim().length() == 0) {
            ptst1 = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL put1 = null;
        try {
            put1 = new URL(ptst1);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sut1 = null;
        if (stst1 != null && stst1.trim().length() > 0) {
            try {
                sut1 = new URL(stst1);
            }
            catch (MalformedURLException e) {
                sut1 = null;
            }
        }
        if (ptsd == null || ptsd.trim().length() == 0) {
            ptsd = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pud = null;
        try {
            pud = new URL(ptsd);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sud = null;
        if (stsd != null && stsd.trim().length() > 0) {
            try {
                sud = new URL(stsd);
            }
            catch (MalformedURLException e) {
                sud = null;
            }
        }
        primaryTransformationServerURL = ConfigContext.createTSURL(pu, name, null);
        secondaryTransformationServerURL = ConfigContext.createTSURL(su, name, null);
        primaryTransformationServerURLT = ConfigContext.createTSURL(put, name, null);
        secondaryTransformationServerURLT = ConfigContext.createTSURL(sut, name, null);
        primaryTransformationServerURL1 = ConfigContext.createTSURL(pu1, name, null);
        secondaryTransformationServerURL1 = ConfigContext.createTSURL(su1, name, null);
        primaryTransformationServerURLT1 = ConfigContext.createTSURL(put1, name, null);
        secondaryTransformationServerURLT1 = ConfigContext.createTSURL(sut1, name, null);
        primaryTransformationServerURLD = ConfigContext.createTSURL(pud, name, null);
        secondaryTransformationServerURLD = ConfigContext.createTSURL(sud, name, null);
        return true;
    }

    public static boolean resetTSUrl(String name) {
        String sctd;
        String pctd;
        String sctt1;
        String pctt1;
        String sct1;
        String pct1;
        String sctt;
        String pctt;
        String sct;
        String pct;
        String pts = primaryTransformationServerURL;
        String sts = secondaryTransformationServerURL;
        String ptst = primaryTransformationServerURLT;
        String stst = secondaryTransformationServerURLT;
        String pts1 = primaryTransformationServerURL1;
        String sts1 = secondaryTransformationServerURL1;
        String ptst1 = primaryTransformationServerURLT1;
        String stst1 = secondaryTransformationServerURLT1;
        String ptsd = primaryTransformationServerURLD;
        String stsd = secondaryTransformationServerURLD;
        if (pts == null || pts.trim().length() == 0) {
            pts = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pu = null;
        try {
            pu = new URL(pts);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL su = null;
        if (sts != null && sts.trim().length() > 0) {
            try {
                su = new URL(sts);
            }
            catch (MalformedURLException e) {
                su = null;
            }
        }
        if (ptst == null || ptst.trim().length() == 0) {
            ptst = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL put = null;
        try {
            put = new URL(ptst);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sut = null;
        if (stst != null && stst.trim().length() > 0) {
            try {
                sut = new URL(stst);
            }
            catch (MalformedURLException e) {
                sut = null;
            }
        }
        if (pts1 == null || pts1.trim().length() == 0) {
            pts1 = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pu1 = null;
        try {
            pu1 = new URL(pts1);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL su1 = null;
        if (sts1 != null && sts1.trim().length() > 0) {
            try {
                su1 = new URL(sts1);
            }
            catch (MalformedURLException e) {
                su1 = null;
            }
        }
        if (ptst1 == null || ptst1.trim().length() == 0) {
            ptst1 = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL put1 = null;
        try {
            put1 = new URL(ptst1);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sut1 = null;
        if (stst1 != null && stst1.trim().length() > 0) {
            try {
                sut1 = new URL(stst1);
            }
            catch (MalformedURLException e) {
                sut1 = null;
            }
        }
        if (ptsd == null || ptsd.trim().length() == 0) {
            ptsd = "http://127.0.0.1:8080/iwtransformationserver";
        }
        URL pud = null;
        try {
            pud = new URL(ptsd);
        }
        catch (MalformedURLException e) {
            return false;
        }
        URL sud = null;
        if (stsd != null && stsd.trim().length() > 0) {
            try {
                sud = new URL(stsd);
            }
            catch (MalformedURLException malformedURLException) {
                sud = null;
            }
        }
        for (TransactionContext transactionContext : transactionList) {
            pct = transactionContext.getPrimaryTransformationServerURL();
            if (pct == null || pct.trim().length() == 0) {
                transactionContext.setPrimaryTransformationServerURL(ConfigContext.createTSURL(pu, name, true));
            }
            if ((sct = transactionContext.getSecondaryTransformationServerURL()) == null || sct.trim().length() == 0) {
                transactionContext.setSecondaryTransformationServerURL(ConfigContext.createTSURL(su, name, true));
            }
            if ((pctt = transactionContext.getPrimaryTransformationServerURLT()) == null || pctt.trim().length() == 0) {
                transactionContext.setPrimaryTransformationServerURLT(ConfigContext.createTSURL(put, name, true));
            }
            if ((sctt = transactionContext.getSecondaryTransformationServerURLT()) == null || sctt.trim().length() == 0) {
                transactionContext.setSecondaryTransformationServerURLT(ConfigContext.createTSURL(sut, name, true));
            }
            if ((pct1 = transactionContext.getPrimaryTransformationServerURL1()) == null || pct1.trim().length() == 0) {
                transactionContext.setPrimaryTransformationServerURL1(ConfigContext.createTSURL(pu1, name, true));
            }
            if ((sct1 = transactionContext.getSecondaryTransformationServerURL1()) == null || sct1.trim().length() == 0) {
                transactionContext.setSecondaryTransformationServerURL1(ConfigContext.createTSURL(su1, name, true));
            }
            if ((pctt1 = transactionContext.getPrimaryTransformationServerURLT1()) == null || pctt1.trim().length() == 0) {
                transactionContext.setPrimaryTransformationServerURLT1(ConfigContext.createTSURL(put1, name, true));
            }
            if ((sctt1 = transactionContext.getSecondaryTransformationServerURLT1()) == null || sctt1.trim().length() == 0) {
                transactionContext.setSecondaryTransformationServerURLT1(ConfigContext.createTSURL(sut1, name, true));
            }
            if ((pctd = transactionContext.getPrimaryTransformationServerURLD()) == null || pctd.trim().length() == 0) {
                transactionContext.setPrimaryTransformationServerURLD(ConfigContext.createTSURL(pud, name, true));
            }
            if ((sctd = transactionContext.getSecondaryTransformationServerURLD()) != null && sctd.trim().length() != 0) continue;
            transactionContext.setSecondaryTransformationServerURLD(ConfigContext.createTSURL(sud, name, true));
        }
        for (QueryContext queryContext : queryList) {
            pct = queryContext.getPrimaryTransformationServerURL();
            if (pct == null || pct.trim().length() == 0) {
                queryContext.setPrimaryTransformationServerURL(ConfigContext.createTSURL(pu, name, false));
            }
            sct = queryContext.getSecondaryTransformationServerURL();
            if (su != null && (sct == null || sct.trim().length() == 0)) {
                queryContext.setSecondaryTransformationServerURL(ConfigContext.createTSURL(su, name, false));
            }
            if ((pctt = queryContext.getPrimaryTransformationServerURLT()) == null || pctt.trim().length() == 0) {
                queryContext.setPrimaryTransformationServerURLT(ConfigContext.createTSURL(put, name, false));
            }
            sctt = queryContext.getSecondaryTransformationServerURLT();
            if (sut != null && (sctt == null || sctt.trim().length() == 0)) {
                queryContext.setSecondaryTransformationServerURLT(ConfigContext.createTSURL(sut, name, false));
            }
            if ((pct1 = queryContext.getPrimaryTransformationServerURL1()) == null || pct1.trim().length() == 0) {
                queryContext.setPrimaryTransformationServerURL1(ConfigContext.createTSURL(pu1, name, true));
            }
            sct1 = queryContext.getSecondaryTransformationServerURL1();
            if (su1 != null && (sct1 == null || sct1.trim().length() == 0)) {
                queryContext.setSecondaryTransformationServerURL1(ConfigContext.createTSURL(su1, name, true));
            }
            if ((pctt1 = queryContext.getPrimaryTransformationServerURLT1()) == null || pctt1.trim().length() == 0) {
                queryContext.setPrimaryTransformationServerURLT1(ConfigContext.createTSURL(put1, name, true));
            }
            sctt1 = queryContext.getSecondaryTransformationServerURLT1();
            if (sut1 != null && (sctt1 == null || sctt1.trim().length() == 0)) {
                queryContext.setSecondaryTransformationServerURLT1(ConfigContext.createTSURL(sut1, name, true));
            }
            if ((pctd = queryContext.getPrimaryTransformationServerURLD()) == null || pctd.trim().length() == 0) {
                queryContext.setPrimaryTransformationServerURLD(ConfigContext.createTSURL(pud, name, false));
            }
            sctd = queryContext.getSecondaryTransformationServerURLD();
            if (sud == null || sctd != null && sctd.trim().length() != 0) continue;
            queryContext.setSecondaryTransformationServerURLD(ConfigContext.createTSURL(sud, name, false));
        }
        return true;
    }

    public static String createTSURL(URL tsURL, String name, Boolean flow) {
        if (tsURL == null) {
            return "";
        }
        return String.valueOf(tsURL.getProtocol()) + "://" + tsURL.getHost() + (tsURL.getPort() == -1 ? "" : ":" + tsURL.getPort()) + "/" + name + (flow == null ? "" : (flow != false ? "/scheduledtransform" : "/transform"));
    }

    public static boolean isTransactionUsed(transactionType transaction, IProject project) throws Exception {
        IProject cpr;
        int n;
        String ctfid = ConfigContext.getCurrentTransactionFlowId();
        String cqid = ConfigContext.getCurrentQueryId();
        if (!ConfigContext.readIMConfigContext(project)) {
            throw new Exception("Unable to read IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(project);
        boolean res = false;
        String[] tfs = ConfigContext.getTransactionFlows();
        ConfigContext.setCurrentQueryId(null);
        String[] stringArray = tfs;
        int n2 = 0;
        int n3 = stringArray.length;
        while (n2 < n3) {
            transactionType[] tts;
            String cf = stringArray[n2];
            ConfigContext.setCurrentTransactionFlowId(cf);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            int n4 = 0;
            n = transactionTypeArray.length;
            while (n4 < n) {
                transactionType ctt = transactionTypeArray[n4];
                if (ctt.getname().getValue().equals(transaction.getname().getValue())) {
                    res = true;
                }
                ++n4;
            }
            ++n2;
        }
        if (!res) {
            String[] qs = ConfigContext.getQueries();
            ConfigContext.setCurrentTransactionFlowId(null);
            String[] stringArray2 = qs;
            n3 = 0;
            int n5 = stringArray2.length;
            while (n3 < n5) {
                transactionType[] tts;
                String cq = stringArray2[n3];
                ConfigContext.setCurrentQueryId(cq);
                transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
                n = 0;
                int n6 = transactionTypeArray.length;
                while (n < n6) {
                    transactionType ctt = transactionTypeArray[n];
                    if (ctt.getname().getValue().equals(transaction.getname().getValue())) {
                        res = true;
                    }
                    ++n;
                }
                ++n3;
            }
        }
        if (!ConfigContext.readIMConfigContext(cpr = Designer.getSelectedProject())) {
            throw new Exception("Unable to restore IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(cpr);
        ConfigContext.setCurrentTransactionFlowId(ctfid);
        ConfigContext.setCurrentQueryId(cqid);
        return res;
    }

    public static boolean isXSLTUsed(IFile transformer, IProject project) throws Exception {
        IProject cpr;
        int n;
        String ctfid = ConfigContext.getCurrentTransactionFlowId();
        String cqid = ConfigContext.getCurrentQueryId();
        if (!ConfigContext.readIMConfigContext(project)) {
            throw new Exception("Unable to read IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(project);
        boolean res = false;
        String[] tfs = ConfigContext.getTransactionFlows();
        ConfigContext.setCurrentQueryId(null);
        String[] stringArray = tfs;
        int n2 = 0;
        int n3 = stringArray.length;
        while (n2 < n3) {
            transactionType[] tts;
            String cf = stringArray[n2];
            ConfigContext.setCurrentTransactionFlowId(cf);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            int n4 = 0;
            n = transactionTypeArray.length;
            while (n4 < n) {
                transactionType ct = transactionTypeArray[n4];
                int i = 0;
                while (i < ct.getdatamapCount()) {
                    accessType at;
                    datamapType dm = ct.getdatamapAt(i);
                    if (dm.getaccessCount() > 0 && (at = dm.getaccess()).gettranslatorCount() > 0) {
                        IFile inf;
                        String xsi;
                        translatorType tt = at.gettranslator();
                        if (tt.getinputclassCount() > 0 && (xsi = tt.getinputclass().getValue().trim()).length() > 0 && transformer.equals((Object)(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt")))) {
                            res = true;
                        }
                        if (tt.getoutputclassCount() > 0 && (xsi = tt.getoutputclass().getValue().trim()).length() > 0 && transformer.equals((Object)(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt")))) {
                            res = true;
                        }
                    }
                    ++i;
                }
                ++n4;
            }
            ++n2;
        }
        if (!res) {
            String[] qs = ConfigContext.getQueries();
            ConfigContext.setCurrentTransactionFlowId(null);
            String[] stringArray2 = qs;
            n3 = 0;
            int n5 = stringArray2.length;
            while (n3 < n5) {
                transactionType[] tts;
                String cq = stringArray2[n3];
                ConfigContext.setCurrentQueryId(cq);
                transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
                n = 0;
                int n6 = transactionTypeArray.length;
                while (n < n6) {
                    transactionType ct = transactionTypeArray[n];
                    int i = 0;
                    while (i < ct.getdatamapCount()) {
                        accessType at;
                        datamapType dm = ct.getdatamapAt(i);
                        if (dm.getaccessCount() > 0 && (at = dm.getaccess()).gettranslatorCount() > 0) {
                            IFile inf;
                            String xsi;
                            translatorType tt = at.gettranslator();
                            if (tt.getinputclassCount() > 0 && (xsi = tt.getinputclass().getValue().trim()).length() > 0 && transformer.equals((Object)(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt")))) {
                                res = true;
                            }
                            if (tt.getoutputclassCount() > 0 && (xsi = tt.getoutputclass().getValue().trim()).length() > 0 && transformer.equals((Object)(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt")))) {
                                res = true;
                            }
                        }
                        ++i;
                    }
                    ++n;
                }
                ++n3;
            }
        }
        if (!ConfigContext.readIMConfigContext(cpr = Designer.getSelectedProject())) {
            throw new Exception("Unable to restore IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(cpr);
        ConfigContext.setCurrentTransactionFlowId(ctfid);
        ConfigContext.setCurrentQueryId(cqid);
        return res;
    }

    public static void cleanNonUsedTransactions(IProject project) throws Exception {
        int n;
        String ctfid = ConfigContext.getCurrentTransactionFlowId();
        String cqid = ConfigContext.getCurrentQueryId();
        if (!ConfigContext.readIMConfigContext(project)) {
            throw new Exception("Unable to read IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(project);
        String[] tfs = ConfigContext.getTransactionFlows();
        ConfigContext.setCurrentQueryId(null);
        String[] stringArray = tfs;
        int n2 = 0;
        int n3 = stringArray.length;
        while (n2 < n3) {
            transactionType[] tts;
            String cf = stringArray[n2];
            ConfigContext.setCurrentTransactionFlowId(cf);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            int n4 = 0;
            n = transactionTypeArray.length;
            while (n4 < n) {
                transactionType ctt = transactionTypeArray[n4];
                int i = 0;
                while (i < trtClp.size()) {
                    transactionType ct = trtClp.get(i);
                    if (ct.getname().getValue().equals(ctt.getname().getValue())) {
                        trtClp.remove(i);
                    }
                    ++i;
                }
                ++n4;
            }
            ++n2;
        }
        String[] qs = ConfigContext.getQueries();
        ConfigContext.setCurrentTransactionFlowId(null);
        String[] stringArray2 = qs;
        n3 = 0;
        int n5 = stringArray2.length;
        while (n3 < n5) {
            transactionType[] tts;
            String cq = stringArray2[n3];
            ConfigContext.setCurrentQueryId(cq);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            n = 0;
            int n6 = transactionTypeArray.length;
            while (n < n6) {
                transactionType ctt = transactionTypeArray[n];
                int i = 0;
                while (i < trtClp.size()) {
                    transactionType ct = trtClp.get(i);
                    if (ct.getname().getValue().equals(ctt.getname().getValue())) {
                        trtClp.remove(i);
                    }
                    ++i;
                }
                ++n;
            }
            ++n3;
        }
        for (transactionType ct : trtClp) {
            iwmappingsRoot.removetransactionAt(ConfigContext.getTransactionIndex(ct.getname().getValue().trim()));
        }
        ConfigContext.saveTransactions(project);
        IProject cpr = Designer.getSelectedProject();
        if (!ConfigContext.readIMConfigContext(cpr)) {
            throw new Exception("Unable to restore IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(cpr);
        ConfigContext.setCurrentTransactionFlowId(ctfid);
        ConfigContext.setCurrentQueryId(cqid);
    }

    public static void cleanNonUsedXSLT(IProject project) throws Exception {
        int n;
        String ctfid = ConfigContext.getCurrentTransactionFlowId();
        String cqid = ConfigContext.getCurrentQueryId();
        if (!ConfigContext.readIMConfigContext(project)) {
            throw new Exception("Unable to read IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(project);
        String[] tfs = ConfigContext.getTransactionFlows();
        ConfigContext.setCurrentQueryId(null);
        String[] stringArray = tfs;
        int n2 = 0;
        int n3 = stringArray.length;
        while (n2 < n3) {
            transactionType[] tts;
            String cf = stringArray[n2];
            ConfigContext.setCurrentTransactionFlowId(cf);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            int n4 = 0;
            n = transactionTypeArray.length;
            while (n4 < n) {
                transactionType ct = transactionTypeArray[n4];
                int i = 0;
                while (i < ct.getdatamapCount()) {
                    accessType at;
                    datamapType dm = ct.getdatamapAt(i);
                    if (dm.getaccessCount() > 0 && (at = dm.getaccess()).gettranslatorCount() > 0) {
                        IFile inf;
                        String xsi;
                        translatorType tt = at.gettranslator();
                        if (tt.getinputclassCount() > 0 && (xsi = tt.getinputclass().getValue().trim()).length() > 0 && xsltClp.contains(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                            xsltClp.remove(inf);
                        }
                        if (tt.getoutputclassCount() > 0 && (xsi = tt.getoutputclass().getValue().trim()).length() > 0 && xsltClp.contains(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                            xsltClp.remove(inf);
                        }
                    }
                    ++i;
                }
                ++n4;
            }
            ++n2;
        }
        String[] qs = ConfigContext.getQueries();
        ConfigContext.setCurrentTransactionFlowId(null);
        String[] stringArray2 = qs;
        n3 = 0;
        int n5 = stringArray2.length;
        while (n3 < n5) {
            transactionType[] tts;
            String cq = stringArray2[n3];
            ConfigContext.setCurrentQueryId(cq);
            transactionType[] transactionTypeArray = tts = ConfigContext.getFlowTransactions();
            n = 0;
            int n6 = transactionTypeArray.length;
            while (n < n6) {
                transactionType ct = transactionTypeArray[n];
                int i = 0;
                while (i < ct.getdatamapCount()) {
                    accessType at;
                    datamapType dm = ct.getdatamapAt(i);
                    if (dm.getaccessCount() > 0 && (at = dm.getaccess()).gettranslatorCount() > 0) {
                        IFile inf;
                        String xsi;
                        translatorType tt = at.gettranslator();
                        if (tt.getinputclassCount() > 0 && (xsi = tt.getinputclass().getValue().trim()).length() > 0 && xsltClp.contains(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                            xsltClp.remove(inf);
                        }
                        if (tt.getoutputclassCount() > 0 && (xsi = tt.getoutputclass().getValue().trim()).length() > 0 && xsltClp.contains(inf = (IFile)((IFolder)project.findMember("xslt")).findMember(String.valueOf(xsi) + ".xslt"))) {
                            xsltClp.remove(inf);
                        }
                    }
                    ++i;
                }
                ++n;
            }
            ++n3;
        }
        for (IFile cf : xsltClp) {
            cf.delete(true, null);
        }
        IProject cpr = Designer.getSelectedProject();
        if (!ConfigContext.readIMConfigContext(cpr)) {
            throw new Exception("Unable to restore IM Configuration.");
        }
        ConfigContext.loadIwmappingsRoot(cpr);
        ConfigContext.setCurrentTransactionFlowId(ctfid);
        ConfigContext.setCurrentQueryId(cqid);
    }

    public static transactionType cloneTransaction(transactionType sourse) {
        return new transactionType(sourse.getDomNode().cloneNode(true));
    }

    public static String getCurrentQueryId() {
        return currentQueryId;
    }

    public static ArrayList<IProject> getPrClp() {
        return prClp;
    }

    public static void setPrClp(ArrayList<IProject> prClp) {
        ConfigContext.prClp = prClp;
    }

    public static String getCurrentTemplateName() {
        return currentTemplateName;
    }

    public static void setCurrentTemplateName(String currentTemplateName) {
        ConfigContext.currentTemplateName = currentTemplateName;
    }

    public static ArrayList<IFile> getTemplateClp() {
        return templateClp;
    }

    public static void setTemplateClp(ArrayList<IFile> templateClp) {
        ConfigContext.templateClp = templateClp;
    }

    public static String[][] getParamsClp() {
        return paramsClp;
    }

    public static void setParamsClp(String[][] paramsClp) {
        ConfigContext.paramsClp = paramsClp;
    }

    public static void setTransactionList(Vector<TransactionContext> transactionList) {
        ConfigContext.transactionList = transactionList;
    }

    public static void setQueryList(Vector<QueryContext> queryList) {
        ConfigContext.queryList = queryList;
    }

    public static ArrayList<QueryContext> getQcClpCopy() {
        return qcClpCopy;
    }

    public static void setQcClpCopy(ArrayList<QueryContext> qcClpCopy) {
        ConfigContext.qcClpCopy = qcClpCopy;
    }

    public static ArrayList<TransactionContext> getTcClpCopy() {
        return tcClpCopy;
    }

    public static void setTcClpCopy(ArrayList<TransactionContext> tcClpCopy) {
        ConfigContext.tcClpCopy = tcClpCopy;
    }

    public static String getPrimaryTransformationServerURLD() {
        return primaryTransformationServerURLD;
    }

    public static void setPrimaryTransformationServerURLD(String primaryTransformationServerURLD) {
        ConfigContext.primaryTransformationServerURLD = primaryTransformationServerURLD;
    }

    public static String getPrimaryTransformationServerURLT() {
        return primaryTransformationServerURLT;
    }

    public static void setPrimaryTransformationServerURLT(String primaryTransformationServerURLT) {
        ConfigContext.primaryTransformationServerURLT = primaryTransformationServerURLT;
    }

    public static String getSecondaryTransformationServerURLD() {
        return secondaryTransformationServerURLD;
    }

    public static void setSecondaryTransformationServerURLD(String secondaryTransformationServerURLD) {
        ConfigContext.secondaryTransformationServerURLD = secondaryTransformationServerURLD;
    }

    public static String getSecondaryTransformationServerURLT() {
        return secondaryTransformationServerURLT;
    }

    public static void setSecondaryTransformationServerURLT(String secondaryTransformationServerURLT) {
        ConfigContext.secondaryTransformationServerURLT = secondaryTransformationServerURLT;
    }

    public static String getPrimaryTransformationServerURL1() {
        return primaryTransformationServerURL1;
    }

    public static void setPrimaryTransformationServerURL1(String primaryTransformationServerURL1) {
        ConfigContext.primaryTransformationServerURL1 = primaryTransformationServerURL1;
    }

    public static String getPrimaryTransformationServerURLT1() {
        return primaryTransformationServerURLT1;
    }

    public static void setPrimaryTransformationServerURLT1(String primaryTransformationServerURLT1) {
        ConfigContext.primaryTransformationServerURLT1 = primaryTransformationServerURLT1;
    }

    public static String getSecondaryTransformationServerURL1() {
        return secondaryTransformationServerURL1;
    }

    public static void setSecondaryTransformationServerURL1(String secondaryTransformationServerURL1) {
        ConfigContext.secondaryTransformationServerURL1 = secondaryTransformationServerURL1;
    }

    public static String getSecondaryTransformationServerURLT1() {
        return secondaryTransformationServerURLT1;
    }

    public static void setSecondaryTransformationServerURLT1(String secondaryTransformationServerURLT1) {
        ConfigContext.secondaryTransformationServerURLT1 = secondaryTransformationServerURLT1;
    }
}

