/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class IWBaseAdaptor
implements IWIDataMap {
    protected Datamap dataMap = null;
    protected Hashtable<String, Access> accessList = new Hashtable();
    protected int totalRows = 0;
    protected String httpURL;
    protected Access curAccess = null;
    protected int dataMapID = 0;
    protected int rowcount = 0;
    protected String contentType = null;
    protected String user;
    protected String password;
    protected Integer[] retFormat;
    protected String[] returnName;
    protected String replaceQuoteName;
    protected String replaceQuote;
    protected String replace2QuoteName;
    protected String replace2Quote;
    protected IWRequest iwRequest;
    protected String httpsOldHost = null;

    public String getDriver() {
        return this.contentType;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUrl() {
        return this.httpURL;
    }

    public String getUser() {
        return this.user;
    }

    public abstract void setup(Datamap var1, IWRequest var2) throws Exception;

    protected void setupInit(Datamap map) throws Exception {
        this.totalRows = 0;
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    protected void setupInitConnect(Datamap map, IWRequest request) throws Exception {
        request.lnkIWServices.logConsole("IWBaseAdaptor - setupInitConnect started!", IWServices.LOG_TRACE, request);
        this.setupInit(map);
        Access ca = (Access)((Object)map.getAccess().get(0));
        this.httpURL = IWServices.getParameterByReference(map.getUrl(), request, ca);
        request.lnkIWServices.logConsole("IWBaseAdaptor - HTTP URL= " + this.httpURL, IWServices.LOG_TRACE, request);
        int dpp = this.httpURL.indexOf("|||");
        if (dpp >= 0) {
            this.httpURL = request.isUseSandbox() ? this.httpURL.substring(dpp + 3) : this.httpURL.substring(0, dpp);
        }
        if (request.isTranRecReq()) {
            String pR = "38";
            String pPR = "";
            if (this.httpURL.contains("https:") & this.httpURL.contains(" %STP:")) {
                int eurl = this.httpURL.indexOf(" %STP:");
                int eurle = this.httpURL.indexOf("%", eurl + 2);
                int edns = this.httpURL.indexOf("/", 8);
                String fUrl = this.httpURL.substring(8, edns > 8 ? edns : eurl);
                String stpStr = "";
                stpStr = eurle > 0 ? this.httpURL.substring(eurl, eurle + 1) : this.httpURL.substring(eurl);
                String stpVal = "";
                stpVal = eurle > 0 ? this.httpURL.substring(eurl + 6, eurle).trim() : this.httpURL.substring(eurl + 6).trim();
                this.httpsOldHost = fUrl;
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(fUrl, "127.0.0.1:" + stpVal).replaceFirst(stpStr, "");
            } else if (this.httpURL.contains("login.salesforce.com")) {
                this.httpsOldHost = "login.salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("login.salesforce.com", "127.0.0.1:38500");
            } else if (this.httpURL.contains("www.salesforce.com")) {
                this.httpsOldHost = "www.salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("www.salesforce.com", "127.0.0.1:38500");
            } else if (this.httpURL.contains(".my.salesforce.com")) {
                String s2rpl = String.valueOf(this.httpURL.substring(8, this.httpURL.indexOf(".my.salesforce.com"))) + ".my.salesforce.com";
                int extPort = 9 - request.getTranRecReqExt();
                this.httpsOldHost = s2rpl;
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(s2rpl, "127.0.0.1:3849" + extPort);
            } else if (this.httpURL.contains("https://na") && this.httpURL.contains("salesforce.com")) {
                String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                if (sfInst.length() > 2) {
                    pPR = sfInst.substring(0, sfInst.length() - 2);
                    sfInst = sfInst.substring(sfInst.length() - 2);
                    pR = "40";
                }
                this.httpsOldHost = "na" + pPR + sfInst + ".salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "5" + (sfInst.length() == 1 ? "0" : "") + sfInst);
            } else if (this.httpURL.contains("https://ap") && this.httpURL.contains("salesforce.com")) {
                String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                if (sfInst.length() > 2) {
                    pPR = sfInst.substring(0, sfInst.length() - 2);
                    sfInst = sfInst.substring(sfInst.length() - 2);
                    pR = "40";
                }
                this.httpsOldHost = "ap" + pPR + sfInst + ".salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "7" + (sfInst.length() == 1 ? "0" : "") + sfInst);
            } else if (this.httpURL.contains("https://eu") && this.httpURL.contains("salesforce.com")) {
                String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                if (sfInst.length() > 2) {
                    pPR = sfInst.substring(0, sfInst.length() - 2);
                    sfInst = sfInst.substring(sfInst.length() - 2);
                    pR = "40";
                }
                this.httpsOldHost = "eu" + pPR + sfInst + ".salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "8" + (sfInst.length() == 1 ? "0" : "") + sfInst);
            } else if (this.httpURL.contains("test.salesforce.com")) {
                this.httpsOldHost = "test.salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("test.salesforce.com", "127.0.0.1:38600");
            } else if (this.httpURL.contains("https://cs") && this.httpURL.contains("salesforce.com")) {
                String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                if (sfInst.length() > 2) {
                    pPR = sfInst.substring(0, sfInst.length() - 2);
                    sfInst = sfInst.substring(sfInst.length() - 2);
                    pR = "40";
                }
                this.httpsOldHost = "cs" + pPR + sfInst + ".salesforce.com";
                this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "6" + (sfInst.length() == 1 ? "0" : "") + sfInst);
            }
            request.lnkIWServices.logConsole("IWBaseAdaptor - Proxy service used - " + this.httpURL, IWServices.LOG_HTTP, request);
        }
        this.httpURL = IWServices.substituteStandardParams(this.httpURL, request);
        this.contentType = IWServices.getParameterByReference(map.getDriver(), request, ca);
        this.user = IWServices.getParameterByReference(map.getUser(), request, ca);
        this.password = IWServices.getParameterByReference(map.getPassword(), request, ca);
        request.lnkIWServices.logConsole("IWBaseAdaptor - setupInitConnect ended! URL= " + this.httpURL + " Content= " + this.contentType + " User= " + this.user + " Password= " + this.password, IWServices.LOG_TRACE, request);
    }

    public abstract StringBuffer go(IWRequest var1) throws Exception;

    protected StringBuffer addDataXML(StringBuffer rowColXML) {
        return this.addDataXML(rowColXML, null);
    }

    protected StringBuffer addDataXML(StringBuffer rowColXML, String descriptor) {
        StringBuffer responseBuffer = new StringBuffer();
        this.totalRows += this.rowcount;
        responseBuffer.append("      <data rowcount=\"" + this.rowcount + "\"");
        if (descriptor != null) {
            responseBuffer.append(" descriptor=\"" + descriptor + "\"");
        }
        responseBuffer.append(">\n");
        responseBuffer.append(rowColXML);
        responseBuffer.append("      </data>\n");
        this.rowcount = 0;
        return responseBuffer;
    }

    protected StringBuffer addDataMapXML(StringBuffer dataXML) {
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("    <datamap ID=\"" + this.dataMapID + "\" name=\"" + this.dataMap.getName() + "\" rowcount=\"" + this.totalRows + "\">\n");
        responseBuffer.append(dataXML);
        responseBuffer.append("    </datamap>\n");
        this.totalRows = 0;
        return responseBuffer;
    }

    protected StringBuffer addRowXML(StringBuffer colsXML) {
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("        <row number=\"" + this.rowcount++ + "\">\n");
        responseBuffer.append(colsXML);
        responseBuffer.append("        </row>\n");
        return responseBuffer;
    }

    protected String oneColOneRow(String colName, String colValue) {
        return this.addRowXML(new StringBuffer(this.oneColBase(colName, colValue, 0))).toString();
    }

    protected String oneCol(String colName, String colValue, int colNumber) {
        String strdata = colValue;
        try {
            strdata = IWServices.processParameters(this.iwRequest, strdata, colName, this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
        }
        catch (Exception e1) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
        }
        return "          <col number=\"" + colNumber + "\" name=\"" + colName.replaceAll("\"", "") + "\">" + IWServices.escape(strdata) + "</col>\n";
    }

    protected String oneColBase(String colName, String colValue, int colNumber) {
        return "          <col number=\"" + colNumber + "\" name=\"" + colName.replaceAll("\"", "") + "\">" + IWServices.escape(colValue) + "</col>\n";
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public Hashtable getAccessList() {
        return this.accessList;
    }

    protected String applyParaneters(boolean unescape, boolean analyze) {
        this.iwRequest.lnkIWServices.logConsole("applyParameters started", IWServices.LOG_TRACE, this.iwRequest);
        String stpre = null;
        Enumeration<Access> enumerate = this.accessList.elements();
        if (enumerate.hasMoreElements()) {
            Access access = enumerate.nextElement();
            this.curAccess = null;
            this.iwRequest.lnkIWServices.logConsole("applyParameters: enumerate access", IWServices.LOG_TRACE, this.iwRequest);
            if (access != null) {
                this.curAccess = access;
                stpre = this.curAccess.getStatementpre();
                if (unescape && !this.curAccess.isDynamic()) {
                    stpre = IWServices.unEscape(stpre);
                }
                stpre = IWServices.replaceParameters(stpre, this.curAccess, this.iwRequest, this.dataMap);
                this.iwRequest.lnkIWServices.logConsole("IWAdaptor data: " + stpre, IWServices.LOG_TRACE, this.iwRequest);
                if (analyze) {
                    ArrayList<String> rN = new ArrayList<String>();
                    StringBuffer rQN = new StringBuffer();
                    StringBuffer rQ = new StringBuffer();
                    StringBuffer r2QN = new StringBuffer();
                    StringBuffer r2Q = new StringBuffer();
                    this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
                    this.returnName = rN.toArray(new String[0]);
                    this.replaceQuoteName = rQN.toString();
                    this.replaceQuote = rQ.toString();
                    this.replace2QuoteName = r2QN.toString();
                    this.replace2Quote = r2Q.toString();
                }
            }
        }
        return stpre;
    }

    public abstract void closeConnection();

    protected void addRowSetXML(StringBuffer rowsXML, int pos, String name) {
        rowsXML.insert(pos, "        <rowset name=\"" + name + "\">\n");
        rowsXML.append("        </rowset>\n");
    }
}

