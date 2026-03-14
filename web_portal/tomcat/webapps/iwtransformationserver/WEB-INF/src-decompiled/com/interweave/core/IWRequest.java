/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

import com.interweave.actionscript.IWASData;
import com.interweave.actionscript.IWASDataMap;
import com.interweave.actionscript.IWASObject;
import com.interweave.actionscript.IWASResponse;
import com.interweave.bindings.Param;
import com.interweave.bindings.Transaction;
import com.interweave.connector.IWXMLRequest;
import com.interweave.connector.IWXsltcImpl;
import com.interweave.core.IWApplication;
import com.interweave.core.IWServices;
import com.interweave.core.IWTagStream;
import com.interweave.session.IWSession;
import flashgateway.io.ASObject;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.w3c.dom.Node;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IWRequest {
    public Hashtable params = new Hashtable();
    public Hashtable postParams = new Hashtable();
    public long startTime = System.currentTimeMillis();
    public String ipAddress = "";
    public String rawData = "";
    public HttpSession httpSession = null;
    public String tranStart = "<transaction ";
    public String tranStop = "</transaction>";
    public String curTran = null;
    public String attrName = "";
    public String mapStart = "<datamap ";
    public String mapStop = "</datamap>";
    public String curMap = null;
    public String dataStart = "<data ";
    public String dataStop = "</data>";
    public String curData = null;
    public String rowStart = "<row ";
    public String rowStop = "</row>";
    public String curRow = null;
    public String colStart = "<col ";
    public String colStop = "</col>";
    public String curCol = null;
    public String colName = "";
    public String colValue = "";
    public int rowCount = 0;
    public int colCount = 0;
    public IWServices lnkIWServices = null;
    public IWXsltcImpl xsltc = new IWXsltcImpl(this);
    public String error = null;
    public IWSession iwSession = null;
    public StringBuffer tranBuffer = new StringBuffer();
    public StringBuffer out = null;
    private Timestamp queryStartTime;
    private String returnString = "";
    private Timestamp[] otherQueryStartTime = new Timestamp[10];
    private String[] otherReturnString = new String[10];
    private Timestamp[] initialOtherQueryStartTime = new Timestamp[10];
    private String[] initialOtherReturnString = new String[10];
    private Timestamp returnDateTime = null;
    private String timePart = null;
    private Timestamp[] otherReturnDateTime = new Timestamp[10];
    private String[] otherTimePart = new String[10];
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String company;
    private String title;
    private String email;
    private boolean emailNoLoop = true;
    private String transactionFlowIdList;
    private String queryIdList;
    private String companyConfiguration;
    private Timestamp initialQueryStartTime;
    private String initialReturnString = "";
    private boolean filterTransaction;
    private Object lastConnection;
    private int loopIndex = 0;
    private boolean flowSuccess = true;
    private boolean addOneMinute = false;
    private int stopSchedule = 0;
    private int stopCycleSchedule = 0;
    private String currentTransactoionFlowId;
    private String currentSessionId;
    private String userEmail = "";
    private boolean logRequest = false;
    private int timeZoneShift = 0;
    private int localLogLevel = Integer.MIN_VALUE;
    private String emailMode = null;
    private String stopMode = null;
    private boolean connectionError = false;
    private boolean longTimeOut = false;
    private int connectionFailures = 0;
    private int initialConnectionFailures = 0;
    private Integer[] otherConnectionFailures = new Integer[10];
    private Integer[] initialOtherConnectionFailures = new Integer[10];
    private boolean emailUseAdmin = false;
    private String emailCC = null;
    private String emailBCC = null;
    private String lastEmail = null;
    private String emailHPN = null;
    private String env2Con = null;
    private String env2ConC = null;
    private String qbLocation = null;
    private int cycleValue = 0;
    private int cycleCounter = 0;
    private int cycleTransactionCounter = 0;
    private long CONNECT_TO = 160000L;
    private boolean useSandbox = false;
    private boolean tranRecReq = false;
    private int tranRecReqExt = 0;
    private boolean sendEmail = true;
    private Vector<String> emailIndex;

    public IWRequest(HttpServletRequest request, IWServices services) throws Exception {
        this.lnkIWServices = services;
        this.xsltc.lnkIWServices = services;
        this.httpSession = request.getSession();
        this.iwSession = this.lnkIWServices.IWlnkSessionManager.setSession(request);
        if (this.iwSession == null) {
            throw new Exception("Timeout");
        }
        this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
        this.ipAddress = request.getRemoteAddr();
        this.add("ipaddress", this.ipAddress);
        this.cycleValue = 0;
    }

    public IWRequest(HttpServletRequest request) {
        this.httpSession = request.getSession();
        this.ipAddress = request.getRemoteAddr();
        this.add("ipaddress", this.ipAddress);
        this.cycleValue = 0;
    }

    public IWRequest(String ipAddr, IWServices services) {
        this.lnkIWServices = services;
        this.xsltc.lnkIWServices = services;
        this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
        this.ipAddress = ipAddr;
        this.add("ipaddress", this.ipAddress);
        this.cycleValue = 0;
    }

    public IWRequest(IWRequest request) throws Exception {
        this.lnkIWServices = request.lnkIWServices;
        this.iwSession = request.iwSession;
        if (this.iwSession == null) {
            throw new Exception("Timeout");
        }
        this.xsltc.lnkIWServices = this.lnkIWServices;
        this.xsltc.mappings = request.xsltc.mappings;
        this.ipAddress = request.ipAddress;
        this.add("ipaddress", this.ipAddress);
        this.cycleValue = 0;
    }

    public IWRequest(IWServices services) {
        this.lnkIWServices = services;
        this.xsltc.lnkIWServices = services;
        this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
        this.cycleValue = 0;
    }

    public IWRequest(ASObject asObject) throws Exception {
        try {
            for (String paramStr : asObject.keySet()) {
                String valueStr = (String)asObject.get((Object)paramStr);
                this.lnkIWServices.logConsole("Action Sctipt Parameter [" + paramStr + ":" + valueStr + "]", IWServices.LOG_TRACE, this);
                this.params.put(paramStr, valueStr);
                paramStr = null;
                Object var3_5 = null;
            }
            String applicationName = this.getParameter("applicationname");
            this.xsltc.lnkIWServices = this.lnkIWServices = IWApplication.getService(applicationName);
            this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
            String sessionID = this.getParameter("sessionid");
            if (sessionID != null && sessionID.length() > 0) {
                this.iwSession = this.lnkIWServices.IWlnkSessionManager.setSession(sessionID);
                if (this.iwSession == null) {
                    throw new Exception("Timeout");
                }
            }
        }
        catch (Exception e) {
            this.lnkIWServices.logError("Request From Action Script Object Error: " + e.getMessage(), IWServices.LOG_TRACE, e, null);
        }
        this.cycleValue = 0;
    }

    public void setRequest(HttpServletRequest request, IWServices services) throws Exception {
        this.lnkIWServices = services;
        this.xsltc.lnkIWServices = services;
        this.httpSession = request.getSession();
        this.iwSession = this.lnkIWServices.IWlnkSessionManager.setSession(request);
        if (this.iwSession == null) {
            throw new Exception("Timeout");
        }
        this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
        this.ipAddress = request.getRemoteAddr();
        this.add("ipaddress", this.ipAddress);
    }

    public void clear() {
        this.params.clear();
        this.postParams.clear();
    }

    public void dump() {
        Enumeration enumerate = this.params.keys();
        String key = null;
        String value = null;
        this.lnkIWServices.logConsole("<!--------------------- Request Dump ----------->", IWServices.LOG_REQUEST, this);
        while (enumerate.hasMoreElements()) {
            key = (String)enumerate.nextElement();
            value = (String)this.params.get(key);
            this.lnkIWServices.logConsole("<!-- Param: " + key + ":" + value + " -->", IWServices.LOG_REQUEST, this);
            key = null;
            value = null;
        }
        enumerate = null;
        enumerate = this.postParams.keys();
        while (enumerate.hasMoreElements()) {
            key = (String)enumerate.nextElement();
            value = (String)this.postParams.get(key);
            this.lnkIWServices.logConsole("<!-- Post Param: " + key + ":" + value + " -->", IWServices.LOG_REQUEST, this);
            key = null;
            value = null;
        }
        this.lnkIWServices.logConsole("<!-- XML Request\n" + this.toXML() + "\n-->", IWServices.LOG_REQUEST, this);
    }

    public void add(String key, String data) {
        this.params.put(key, data);
    }

    public void addPost(String key, String data) {
        this.postParams.put(key, data);
    }

    public String getParameter(String key) throws Exception {
        String strValue = "";
        if (key == null) {
            this.lnkIWServices.logConsole("<!-- IWRequest.getParameter Null Key:  -->", IWServices.LOG_ERRORS, this);
            this.dump();
            throw new Exception("Severe Error Null Key for getParameter: ");
        }
        if (this.params.containsKey(key)) {
            strValue = (String)this.params.get(key);
        } else {
            this.dump();
        }
        return strValue;
    }

    public void setParam(String key, String data) {
        this.lnkIWServices.logConsole("<!-- IWRequest.setParam " + key + ":" + data + " -->", IWServices.LOG_REQUEST, this);
        if (this.params.get(key) != null) {
            this.params.remove(key);
        }
        this.params.put(key, data);
    }

    public void fromDoc(Node pos) {
        try {
            String strName = null;
            Object strValue = null;
            Node top = pos;
            while (pos != null) {
                Node nextNode = pos.getFirstChild();
                if (pos.getNodeType() == 1) {
                    if (strName != null) {
                        this.setParam(strName, "");
                        strName = null;
                    }
                    strName = pos.getNodeName();
                } else if (pos.getNodeType() == 3 && strName != null) {
                    this.setParam(strName, pos.getNodeValue());
                    strName = null;
                }
                while (nextNode == null) {
                    if (top.equals(pos)) break;
                    nextNode = pos.getNextSibling();
                    if (nextNode != null || (pos = pos.getParentNode()) != null && !top.equals(pos)) continue;
                    nextNode = null;
                    break;
                }
                pos = nextNode;
            }
            this.dump();
        }
        catch (Exception e) {
            this.error = "IWRequest.fromDoc: " + e.getMessage();
            this.lnkIWServices.logError(this.error, IWServices.LOG_ERRORS, e, null);
        }
    }

    public boolean fromXML(String xmlStr) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(xmlStr.getBytes());
            IWXMLRequest request = IWXMLRequest.unmarshal(stream);
            Param param2 = null;
            for (Param param2 : request.getParam()) {
                this.params.put(param2.getName(), param2.getContent());
                param2 = null;
            }
            request = null;
            param2 = null;
            stream = null;
            this.dump();
        }
        catch (Exception e) {
            this.error = "IWRequest.fromXML: " + e.getMessage();
            this.lnkIWServices.logError(this.error, IWServices.LOG_ERRORS, e, null);
            return false;
        }
        return true;
    }

    public boolean fromIWXML(HttpServletRequest iwrequest) throws Exception {
        try {
            this.fromIWXML();
            this.xsltc.lnkIWServices = this.lnkIWServices = IWApplication.getService("iwtransformationserver");
            this.iwSession = this.lnkIWServices.IWlnkSessionManager.setSession(this.getParameter("sessionid"));
            if (this.iwSession == null) {
                this.lnkIWServices.logConsole("Session Timeout", IWServices.LOG_TRACE, this);
                throw new Exception("Timeout");
            }
            this.lnkIWServices.logConsole("Mappings", IWServices.LOG_TRACE, this);
            this.xsltc.mappings = this.lnkIWServices.xsltc.mappings;
        }
        catch (Exception e) {
            this.lnkIWServices.logConsole("Returning False", IWServices.LOG_TRACE, this);
            this.error = "IWRequest.fromIWXML: " + e.getMessage();
            this.lnkIWServices.logError(this.error, IWServices.LOG_REQUEST, e, null);
            return false;
        }
        this.dump();
        return true;
    }

    public boolean fromIWXML() {
        try {
            String paramStart = "<param";
            String paramStop = "</param>";
            String paramName = "";
            String paramValue = "";
            String curParam = "";
            IWTagStream tagStream = new IWTagStream();
            this.rawData = tagStream.removeXMLDecl(this.rawData);
            while (curParam != null) {
                curParam = tagStream.fragment(this.rawData, paramStart, paramStop);
                if (curParam == null || !tagStream.tagFound) {
                    curParam = null;
                    continue;
                }
                this.rawData = tagStream.remove(this.rawData, paramStart, paramStop);
                paramName = tagStream.attributeData(curParam, "name");
                if (paramName == null) continue;
                paramValue = tagStream.tagData(curParam);
                this.lnkIWServices.logConsole(String.valueOf(paramName) + ":" + paramValue, IWServices.LOG_TRACE, this);
                this.params.put(paramName, paramValue);
            }
        }
        catch (Exception e) {
            this.error = "IWRequest.fromIWXML: " + e.getMessage();
            this.lnkIWServices.logConsole(this.error, IWServices.LOG_TRACE, this);
            return false;
        }
        return true;
    }

    public String toXML() {
        StringBuffer responseBuffer = new StringBuffer();
        String response = null;
        Enumeration enumerate = this.params.keys();
        responseBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<iwxmlrequest>\n");
        while (enumerate.hasMoreElements()) {
            String key = (String)enumerate.nextElement();
            String value = (String)this.params.get(key);
            responseBuffer.append("    <param name=\"" + key.trim() + "\">" + value.trim() + "</param>\n");
        }
        responseBuffer.append("\n</iwxmlrequest>\n");
        response = responseBuffer.toString();
        return response;
    }

    public IWXsltcImpl getXsltc() {
        return this.xsltc;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.lnkIWServices.logConsole(error, IWServices.LOG_ERRORS, this);
        this.error = error;
    }

    public String getTranResponse() {
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("<" + this.lnkIWServices.appName + ">\n    <iwrecordset>\n");
        if (this.iwSession != null) {
            this.lnkIWServices.logConsole("<!-- IWSession SelectQST " + this.queryStartTime + " -->", IWServices.LOG_DATA, this);
            this.lnkIWServices.logConsole("<!-- IWSession SelectCC " + this.companyConfiguration + " -->", IWServices.LOG_DATA, this);
            this.lnkIWServices.logConsole("<!-- IWSession Select " + this.iwSession.select(this.companyConfiguration) + " -->", IWServices.LOG_DATA, this);
            responseBuffer.append(this.iwSession.select(this.companyConfiguration).toString());
        }
        if (this.tranBuffer != null) {
            this.lnkIWServices.logConsole("<!-- Tranbuffer " + this.tranBuffer + " -->", IWServices.LOG_DATA, this);
            responseBuffer.append(this.tranBuffer.toString());
        }
        responseBuffer.append("    </iwrecordset>\n</" + this.lnkIWServices.appName + ">\n\n");
        return responseBuffer.toString();
    }

    public String getResponse() {
        if (this.out != null) {
            return this.out.toString();
        }
        return this.getTranResponse();
    }

    public String processTransform() {
        String response = new String("<IW><Data></Data></IW>");
        try {
            this.xsltc.strTransform = this.getParameter("tranname");
            return this.xsltc.executeTransform(response, this);
        }
        catch (Exception e) {
            this.lnkIWServices.logError("Transaction Transform Error: " + e.getMessage(), IWServices.LOG_TRACE, e, this);
            return "";
        }
    }

    public void processIndex() {
        try {
            this.add("tranname", "index");
            this.processDataMap();
        }
        catch (Exception e) {
            this.lnkIWServices.logConsole("<!-- Index Transaction Error " + e.getMessage() + " -->", IWServices.LOG_ERRORS, this);
        }
    }

    public void processDataMap() {
        long startTotal = System.currentTimeMillis();
        String strMap = null;
        long doneParameters = System.currentTimeMillis() - startTotal;
        this.lnkIWServices.logConsole("<!-- Parameter Extraction  in " + doneParameters + ":msecs -->", IWServices.LOG_TIMING, this);
        try {
            strMap = this.getParameter("mapname");
            if (strMap != null && strMap.length() > 0) {
                this.lnkIWServices.logConsole("<!-- Map Name " + strMap + " -->", IWServices.LOG_DATA, this);
                try {
                    this.xsltc.load(strMap, this);
                }
                catch (Exception e) {
                    this.lnkIWServices.logError("Set Map Error: " + e.getMessage(), IWServices.LOG_ERRORS, e, this);
                }
            }
        }
        catch (Exception e) {
            this.setFlowSuccess(false);
            this.lnkIWServices.logError("Null Map Name: " + e.getMessage(), IWServices.LOG_ERRORS, e, this);
        }
        try {
            this.out = null;
            this.xsltc.executeTransaction(this);
            Transaction tran = this.xsltc.getTransaction(this.getParameter("tranname"), this);
            if (tran != null) {
                this.lnkIWServices.logConsole("<!-- Last Transaction Name " + tran.getName() + " -->", IWServices.LOG_DATA, this);
                this.xsltc.strTransform = tran.getTransform();
                if (this.xsltc.strTransform != null && this.xsltc.strTransform.length() > 0) {
                    String outData = this.getResponse();
                    this.out = new StringBuffer(IWServices.substituteStandardParams(this.xsltc.executeTransform(outData, this), this));
                }
            } else {
                this.setFlowSuccess(false);
                this.lnkIWServices.logConsole("<!-- ProcessDataMap Error Null Transaction -->", IWServices.LOG_ERRORS, this);
            }
            return;
        }
        catch (Exception e) {
            this.setFlowSuccess(false);
            if (e.getMessage() != null && e.getMessage().equals("Connection Failed")) {
                this.setConnectionError(true);
            }
            this.lnkIWServices.logError("<!-- ProcessDataMap Error " + e.getMessage() + " -->", IWServices.LOG_TRACE, e, this);
            strMap = null;
            this.xsltc.tranname = null;
            return;
        }
    }

    public void makeASResponse(IWASResponse iwASResponse) throws Exception {
        String responseStr = this.getTranResponse();
        IWTagStream tagStream = new IWTagStream();
        this.curTran = "";
        while (this.curTran != null) {
            try {
                this.curTran = tagStream.fragment(responseStr, this.tranStart, this.tranStop);
                if (this.curTran == null || !tagStream.tagFound) {
                    this.curTran = null;
                    continue;
                }
                responseStr = tagStream.remove(responseStr, this.tranStart, this.tranStop);
                this.attrName = tagStream.attributeData(this.curTran, "name");
            }
            catch (Exception e) {
                this.attrName = null;
            }
            if (this.attrName == null) continue;
            IWASObject iwCurTran = new IWASObject();
            iwASResponse.put(this.attrName, iwCurTran);
            this.curMap = "";
            while (this.curMap != null) {
                try {
                    this.curMap = tagStream.fragment(this.curTran, this.mapStart, this.mapStop);
                    if (this.curMap == null || !tagStream.tagFound) {
                        this.curMap = null;
                        continue;
                    }
                    this.curTran = tagStream.remove(this.curTran, this.mapStart, this.mapStop);
                    this.attrName = tagStream.attributeData(this.curMap, "name");
                }
                catch (Exception e) {
                    this.attrName = null;
                }
                if (this.attrName == null) continue;
                IWASDataMap iwCurMap = new IWASDataMap();
                ArrayList dataList = (ArrayList)iwCurMap.get("data");
                iwCurTran.put(this.attrName, iwCurMap);
                this.curData = "";
                while (this.curData != null) {
                    try {
                        this.curData = tagStream.fragment(this.curMap, this.dataStart, this.dataStop);
                        if (this.curData == null || !tagStream.tagFound) {
                            this.curData = null;
                            continue;
                        }
                        this.curMap = tagStream.remove(this.curMap, this.dataStart, this.dataStop);
                        this.attrName = tagStream.attributeData(this.curData, "rowcount");
                    }
                    catch (Exception e) {
                        this.attrName = null;
                    }
                    if (this.attrName == null) continue;
                    IWASData iwCurData = new IWASData();
                    ArrayList rowList = (ArrayList)iwCurData.get("rows");
                    dataList.add(iwCurData);
                    this.curRow = "";
                    while (this.curRow != null) {
                        try {
                            this.curRow = tagStream.fragment(this.curData, this.rowStart, this.rowStop);
                            if (this.curRow == null || !tagStream.tagFound) {
                                this.curRow = null;
                                continue;
                            }
                            this.curData = tagStream.remove(this.curData, this.rowStart, this.rowStop);
                            this.attrName = tagStream.attributeData(this.curRow, "number");
                        }
                        catch (Exception e) {
                            this.attrName = null;
                        }
                        if (this.attrName == null) continue;
                        IWASObject iwCurRow = new IWASObject();
                        rowList.add(iwCurRow);
                        this.curCol = "";
                        while (this.curCol != null) {
                            try {
                                this.curCol = tagStream.fragment(this.curRow, this.colStart, this.colStop);
                                if (this.curCol == null || !tagStream.tagFound) {
                                    this.curCol = null;
                                    continue;
                                }
                                this.curRow = tagStream.remove(this.curRow, this.colStart, this.colStop);
                                this.colName = tagStream.attributeData(this.curCol, "name");
                            }
                            catch (Exception e) {
                                this.colName = null;
                            }
                            if (this.colName == null) continue;
                            this.colValue = tagStream.tagData(this.curCol);
                            iwCurRow.put(this.colName, this.colValue);
                        }
                    }
                }
            }
        }
    }

    public final Timestamp getQueryStartTime() {
        return this.queryStartTime;
    }

    public final void setQueryStartTime(Timestamp queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public final boolean isFilterTransaction() {
        return this.filterTransaction;
    }

    public final void setFilterTransaction(boolean filterTransaction) {
        this.filterTransaction = filterTransaction;
    }

    public String getEmailBCC() {
        return this.emailBCC;
    }

    public void setEmailBCC(String emailBCC) {
        this.emailBCC = emailBCC;
    }

    public String getEmailCC() {
        return this.emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public boolean isEmailUseAdmin() {
        return this.emailUseAdmin;
    }

    public void setEmailUseAdmin(boolean emailUseAdmin) {
        this.emailUseAdmin = emailUseAdmin;
    }

    public boolean isConnectionError() {
        return this.connectionError;
    }

    public void setConnectionError(boolean connectionError) {
        this.connectionError = connectionError;
    }

    public String getEmailMode() {
        return this.emailMode;
    }

    public void setEmailMode(String emailMode) {
        this.emailMode = emailMode;
    }

    public String getStopMode() {
        return this.stopMode;
    }

    public void setStopMode(String stopMode) {
        this.stopMode = stopMode;
    }

    public int getLocalLogLevel() {
        return this.localLogLevel;
    }

    public void setLocalLogLevel(int localLogLevel) {
        this.localLogLevel = localLogLevel;
    }

    public String getCurrentTransactoionFlowId() {
        return this.currentTransactoionFlowId;
    }

    public void setCurrentTransactoionFlowId(String currentTransactoionFlowId) {
        this.currentTransactoionFlowId = currentTransactoionFlowId;
    }

    public boolean isFlowSuccess() {
        return this.flowSuccess;
    }

    public void setFlowSuccess(boolean flowSuccess) {
        this.flowSuccess = flowSuccess;
    }

    public Timestamp getInitialQueryStartTime() {
        return this.initialQueryStartTime;
    }

    public void setInitialQueryStartTime(Timestamp initialQueryStartTime) {
        this.initialQueryStartTime = initialQueryStartTime;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTransactionFlowIdList() {
        return this.transactionFlowIdList;
    }

    public void setTransactionFlowIdList(String transactionFlowIdList) {
        this.transactionFlowIdList = transactionFlowIdList;
    }

    public String getQueryIdList() {
        return this.queryIdList;
    }

    public void setQueryIdList(String queryIdList) {
        this.queryIdList = queryIdList;
    }

    public Object getLastConnection() {
        return this.lastConnection;
    }

    public void setLastConnection(Object lastTransaction) {
        this.lastConnection = lastTransaction;
    }

    public Timestamp getReturnDateTime() {
        return this.returnDateTime;
    }

    public void setReturnDateTime(Timestamp returnDate) {
        this.returnDateTime = returnDate;
    }

    public String getTimePart() {
        return this.timePart;
    }

    public void setTimePart(String timePart) {
        this.timePart = timePart;
    }

    public int getLoopIndex() {
        return this.loopIndex;
    }

    public void setLoopIndex(int loopIndex) {
        this.loopIndex = loopIndex;
    }

    public int getStopSchedule() {
        return this.stopSchedule;
    }

    public void setStopSchedule(int stopSchedule) {
        this.stopSchedule = stopSchedule;
    }

    public String getReturnString() {
        return this.returnString;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public String getInitialReturnString() {
        return this.initialReturnString;
    }

    public void setInitialReturnString(String initialReturnString) {
        this.initialReturnString = initialReturnString;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyConfiguration() {
        return this.companyConfiguration;
    }

    public void setCompanyConfiguration(String companyConfiguration) {
        this.companyConfiguration = companyConfiguration;
    }

    public Timestamp[] getOtherQueryStartTime() {
        return this.otherQueryStartTime;
    }

    public String[] getOtherReturnString() {
        return this.otherReturnString;
    }

    public Timestamp[] getOtherReturnDateTime() {
        return this.otherReturnDateTime;
    }

    public String[] getOtherTimePart() {
        return this.otherTimePart;
    }

    public Timestamp[] getInitialOtherQueryStartTime() {
        return this.initialOtherQueryStartTime;
    }

    public String[] getInitialOtherReturnString() {
        return this.initialOtherReturnString;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCurrentSessionId() {
        return this.currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    public boolean isAddOneMinute() {
        return this.addOneMinute;
    }

    public void setAddOneMinute(boolean addOneMinute) {
        this.addOneMinute = addOneMinute;
    }

    public boolean isLogRequest() {
        return this.logRequest;
    }

    public void setLogRequest(boolean logRequest) {
        this.logRequest = logRequest;
    }

    public int getTimeZoneShift() {
        return this.timeZoneShift;
    }

    public void setTimeZoneShift(int timeZoneShift) {
        this.timeZoneShift = timeZoneShift;
    }

    public String getLastEmail() {
        return this.lastEmail;
    }

    public void setLastEmail(String lastEmail) {
        this.lastEmail = lastEmail;
    }

    public boolean isEmailNoLoop() {
        return this.emailNoLoop;
    }

    public void setEmailNoLoop(boolean emailNoLoop) {
        this.emailNoLoop = emailNoLoop;
    }

    public String getEmailHPN() {
        return this.emailHPN;
    }

    public void setEmailHPN(String emailHPN) {
        this.emailHPN = emailHPN;
    }

    public int getConnectionFailures() {
        return this.connectionFailures;
    }

    public void setConnectionFailures(int connectionErrorCntr) {
        this.connectionFailures = connectionErrorCntr;
    }

    public int getInitialConnectionFailures() {
        return this.initialConnectionFailures;
    }

    public Integer[] getInitialOtherConnectionFailures() {
        return this.initialOtherConnectionFailures;
    }

    public Integer[] getOtherConnectionFailures() {
        return this.otherConnectionFailures;
    }

    public void setInitialConnectionFailures(int initialConnectionFailures) {
        this.initialConnectionFailures = initialConnectionFailures;
    }

    public int getCycleValue() {
        return this.cycleValue;
    }

    public void setCycleValue(int cycleConstant) {
        this.cycleValue = cycleConstant;
    }

    public int getCycleCounter() {
        return this.cycleCounter;
    }

    public void setCycleCounter(int cycleCounter) {
        this.cycleCounter = cycleCounter;
    }

    public int getStopCycleSchedule() {
        return this.stopCycleSchedule;
    }

    public void setStopCycleSchedule(int stopCycleSchedule) {
        this.stopCycleSchedule = stopCycleSchedule;
    }

    public int getCycleTransactionCounter() {
        return this.cycleTransactionCounter;
    }

    public void setCycleTransactionCounter(int cycleTransactionCounter) {
        this.cycleTransactionCounter = cycleTransactionCounter;
    }

    public long getCONNECT_TO() {
        return this.CONNECT_TO;
    }

    public void setCONNECT_TO(long connect_to) {
        this.CONNECT_TO = connect_to;
    }

    public boolean isLongTimeOut() {
        return this.longTimeOut;
    }

    public void setLongTimeOut(boolean longTimeOut) {
        this.longTimeOut = longTimeOut;
    }

    public boolean isUseSandbox() {
        return this.useSandbox;
    }

    public void setUseSandbox(boolean useSandbox) {
        this.useSandbox = useSandbox;
    }

    public boolean isSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public final Vector<String> getEmailIndex() {
        return this.emailIndex;
    }

    public final void setEmailIndex(Vector<String> emailIndex) {
        this.emailIndex = emailIndex;
    }

    public final String getEnv2Con() {
        return this.env2Con;
    }

    public final void setEnv2Con(String env2Con) {
        this.env2Con = env2Con;
    }

    public final String getEnv2ConC() {
        return this.env2ConC;
    }

    public final void setEnv2ConC(String env2ConC) {
        this.env2ConC = env2ConC;
    }

    public final String getQbLocation() {
        return this.qbLocation;
    }

    public final void setQbLocation(String qbLocation) {
        this.qbLocation = qbLocation;
    }

    public boolean isTranRecReq() {
        return this.tranRecReq;
    }

    public void setTranRecReq(boolean tranRecReq) {
        this.tranRecReq = tranRecReq;
    }

    public int getTranRecReqExt() {
        return this.tranRecReqExt;
    }

    public void setTranRecReqExt(int tranRecReqExt) {
        this.tranRecReqExt = tranRecReqExt;
    }
}

