/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.connector;

import com.interweave.adapter.database.IWSqlBase;
import com.interweave.core.IWApplication;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IWHttpImpl {
    public IWRequest iwRequest = null;
    protected long startTotal = System.currentTimeMillis();
    protected IWServices lnkIWServices = null;
    protected String transactionInstanseId;
    protected boolean fromPrimary = true;

    /*
     * Unable to fully structure code
     */
    public IWRequest createRequest(HttpServletRequest request, String applicationName) throws Exception {
        block154: {
            block155: {
                this.lnkIWServices = IWApplication.getService(applicationName);
                this.iwRequest = new IWRequest(request, this.lnkIWServices);
                if (this.iwRequest == null) {
                    return null;
                }
                this.iwRequest.setFlowSuccess(true);
                this.iwRequest.setStopSchedule(0);
                this.iwRequest.setStopCycleSchedule(0);
                this.iwRequest.setUseSandbox(false);
                this.iwRequest.setTranRecReq(false);
                this.iwRequest.setTranRecReqExt(0);
                this.iwRequest.setEmailIndex(IWServices.getInitEmailIndex());
                method = request.getMethod();
                enumerate = null;
                sessionID = request.getParameter("sessionid");
                if (sessionID != null && sessionID.length() > 0) {
                    this.iwRequest.iwSession = this.lnkIWServices.IWlnkSessionManager.setSession(sessionID);
                }
                this.lnkIWServices.logConsole("Method = " + method, IWServices.LOG_TRACE, this.iwRequest);
                if (!method.equalsIgnoreCase("POST") && !method.equalsIgnoreCase("GET")) break block154;
                query = request.getQueryString();
                if (query != null) break block155;
                enumerate = request.getParameterNames();
                if (enumerate.hasMoreElements()) ** GOTO lbl192
                bf = request.getReader();
                bufferSize = 4096;
                temp = new char[bufferSize];
                binString = new StringBuffer();
                while ((count = bf.read(temp, 0, bufferSize)) != -1) {
                    binString.append(temp, 0, count);
                }
                bf.close();
                this.lnkIWServices.logConsole("BINS = " + binString, IWServices.LOG_TRACE, this.iwRequest);
                parser = new DOMParser();
                tmp = binString.toString().getBytes();
                bis = new ByteArrayInputStream(tmp);
                try {
                    parser.parse(new InputSource(bis));
                }
                catch (SAXException e) {
                    ict = 0;
                    ** while (ict < tmp.length)
                }
lbl-1000:
                // 1 sources

                {
                    if (!(tmp[ict] == 9 || tmp[ict] == 10 || tmp[ict] == 13 || tmp[ict] >= 32 && tmp[ict] <= 55295 || tmp[ict] >= 57344 && tmp[ict] <= 65533 || tmp[ict] >= 65536 && tmp[ict] <= 0x10FFFF)) {
                        tmp[ict] = 32;
                    }
                    ++ict;
                    continue;
                }
lbl50:
                // 1 sources

                this.lnkIWServices.logConsole("XML go: Reduced string to parse: " + new String(tmp), IWServices.LOG_TRACE, this.iwRequest);
                parser = new DOMParser();
                bis = new ByteArrayInputStream(tmp);
                try {
                    parser.parse(new InputSource(bis));
                }
                catch (SAXException e1) {
                    throw e1;
                }
                catch (IOException e1) {
                    throw e1;
                }
                catch (IOException e) {
                    throw e;
                }
                trConDoc = parser.getDocument();
                trCon = trConDoc.getDocumentElement();
                this.iwRequest.iwSession.insert("CurrentStartDateTime", new Timestamp(System.currentTimeMillis()).toString());
                this.iwRequest.setCurrentTransactoionFlowId(trCon.getAttribute("Id"));
                this.iwRequest.iwSession.insert("CurrentFlowId", trCon.getAttribute("Id"));
                this.transactionInstanseId = trCon.getAttribute("InstanceId");
                this.iwRequest.setCurrentSessionId(this.transactionInstanseId.split(":")[0]);
                this.iwRequest.setUserEmail(trCon.getAttribute("Email"));
                ccvl = trCon.getAttribute("InnerCycles");
                if (ccvl == null) {
                    ccvl = "0";
                }
                this.iwRequest.setCycleValue(Integer.valueOf(ccvl));
                ats = trCon.getElementsByTagName("Parameter");
                i = 0;
                while (i < ats.getLength()) {
                    an = (Element)ats.item(i);
                    annm = an.getAttribute("Name");
                    anvl = an.getAttribute("Value");
                    if (annm.equals("QueryStartTime")) {
                        tm = Timestamp.valueOf(anvl);
                        this.iwRequest.setQueryStartTime(tm);
                        this.iwRequest.setInitialQueryStartTime(tm);
                        this.iwRequest.setAddOneMinute(false);
                    } else if (annm.equals("ReturnString")) {
                        this.iwRequest.setReturnString(anvl);
                        this.iwRequest.setInitialReturnString(anvl);
                    } else if (annm.equals("ConnectionFailures")) {
                        cfs = Integer.valueOf(anvl);
                        this.iwRequest.setConnectionFailures(cfs);
                        this.iwRequest.setInitialConnectionFailures(cfs);
                    } else if (annm.startsWith("QueryStartTime_")) {
                        qidx = IWServices.retIndex(annm);
                        if (qidx >= 0) {
                            this.iwRequest.getOtherQueryStartTime()[qidx] = tm = Timestamp.valueOf(anvl);
                            this.iwRequest.getInitialOtherQueryStartTime()[qidx] = tm;
                        }
                    } else if (annm.startsWith("ReturnString_")) {
                        qidx = IWServices.retIndex(annm);
                        if (qidx >= 0) {
                            this.iwRequest.getOtherReturnString()[qidx] = anvl;
                            this.iwRequest.getInitialOtherReturnString()[qidx] = anvl;
                        }
                    } else if (annm.startsWith("ConnectionFailures_")) {
                        qidx = IWServices.retIndex(annm);
                        if (qidx >= 0) {
                            cfs = Integer.valueOf(anvl);
                            this.iwRequest.getOtherConnectionFailures()[qidx] = cfs;
                            this.iwRequest.getInitialOtherConnectionFailures()[qidx] = cfs;
                        }
                    } else if (annm.equals("LogLevel") && (ll = Integer.valueOf(anvl).intValue()) != -1000) {
                        this.iwRequest.setLocalLogLevel(ll);
                        this.lnkIWServices.logConsole("LocalLogLevel = " + this.iwRequest.getLocalLogLevel(), IWServices.LOG_ERRORS, this.iwRequest);
                    }
                    this.iwRequest.add(annm, anvl);
                    this.iwRequest.iwSession.insert("__Param__" + annm, anvl);
                    ++i;
                }
                ccl = trCon.getElementsByTagName("CompanyConfiguration");
                cmpcf = new StringBuffer();
                i = 0;
                while (i < ccl.getLength()) {
                    cp = (Element)ccl.item(i);
                    ccp = cp.getElementsByTagName("*");
                    j = 0;
                    while (j < ccp.getLength()) {
                        ccpp = (Element)ccp.item(j);
                        ccppl = ccpp.getElementsByTagName("*");
                        k = 0;
                        while (k < ccppl.getLength()) {
                            ccppc = (Element)ccppl.item(k);
                            cmpcf.append("<col number=\"1\" name=\"" + ccppc.getTagName() + "\">" + ccppc.getTextContent() + "</col>\n");
                            if (ccppc.getTagName().equals("TimeZone") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setTimeZoneShift(Integer.valueOf(ccppc.getTextContent()));
                            } else if (ccppc.getTagName().equals("EmlNtf") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEmailMode(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("StopSchedTr") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setStopMode(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("UseAdmEml") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEmailUseAdmin(ccppc.getTextContent().equals("Yes"));
                            } else if (ccppc.getTagName().equals("CCEmail") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEmailCC(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("BCCEmail") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEmailBCC(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("HPNEmail") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEmailHPN(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("LongTimeOut") && ccppc.getTextContent().equals("Yes")) {
                                this.iwRequest.setLongTimeOut(true);
                                this.iwRequest.setCONNECT_TO(320000L);
                            } else if (ccppc.getTagName().equals("SandBoxUsed") && ccppc.getTextContent().equals("Yes")) {
                                this.iwRequest.setUseSandbox(true);
                            } else if (ccppc.getTagName().equals("TranRecReq") && ccppc.getTextContent().startsWith("Yes")) {
                                this.iwRequest.setTranRecReq(true);
                                if (!ccppc.getTextContent().equals("Yes")) {
                                    try {
                                        this.iwRequest.setTranRecReqExt(Integer.parseInt(ccppc.getTextContent().substring(3)));
                                    }
                                    catch (RuntimeException e) {
                                        this.lnkIWServices.logError("Error setting extended security level", IWServices.LOG_TRACE, e, this.iwRequest);
                                    }
                                }
                            } else if (ccppc.getTagName().equals("Env2Con") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEnv2Con(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("Env2ConC") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setEnv2ConC(ccppc.getTextContent());
                            } else if (ccppc.getTagName().equals("QBLocation") && ccppc.getTextContent().trim().length() > 0) {
                                this.iwRequest.setQbLocation(ccppc.getTextContent());
                            }
                            ++k;
                        }
                        ++j;
                    }
                    ++i;
                }
                this.iwRequest.setCompanyConfiguration(cmpcf.toString());
                break block154;
lbl-1000:
                // 1 sources

                {
                    name = (String)enumerate.nextElement();
                    this.lnkIWServices.logConsole("ParName = " + name, IWServices.LOG_TRACE, this.iwRequest);
                    this.iwRequest.add(name, request.getParameter(name));
lbl192:
                    // 2 sources

                    ** while (enumerate.hasMoreElements())
                }
lbl193:
                // 1 sources

                break block154;
            }
            this.lnkIWServices.logConsole("Query String = " + query, IWServices.LOG_TRACE, this.iwRequest);
            amp = 0;
            equ = 0;
            params = query;
            param = null;
            name = null;
            value = null;
            getConfig = false;
            company = null;
            portalBrand = null;
            token = null;
            amp = params.indexOf("&");
            while (amp != -1) {
                param = params.substring(0, amp);
                params = params.substring(amp + 1, params.length());
                amp = params.indexOf("&");
                equ = param.indexOf("=");
                if (equ != -1) {
                    name = param.substring(0, equ);
                    value = param.substring(equ + 1, param.length());
                } else {
                    name = param;
                    value = "";
                }
                if (method.equalsIgnoreCase("GET")) {
                    name = URLDecoder.decode(name, "UTF-8");
                    value = URLDecoder.decode(value, "UTF-8");
                }
                if (name.equals("QueryStartTime")) {
                    tm = Timestamp.valueOf(value);
                    this.iwRequest.setQueryStartTime(tm);
                    this.iwRequest.setInitialQueryStartTime(tm);
                    this.iwRequest.setAddOneMinute(false);
                    continue;
                }
                if (name.equals("__LOG_QUERY_ID__")) {
                    this.iwRequest.setCurrentTransactoionFlowId(value);
                    this.iwRequest.setLogRequest(true);
                    continue;
                }
                if (name.equals("__QUERY_ID__")) {
                    this.iwRequest.setCurrentTransactoionFlowId(value);
                    continue;
                }
                if (name.equals("LogLevel")) {
                    ll = Integer.valueOf(value);
                    if (ll == -1000) continue;
                    this.iwRequest.setLocalLogLevel(ll);
                    this.lnkIWServices.logConsole("LocalLogLevel = " + this.iwRequest.getLocalLogLevel(), IWServices.LOG_ERRORS, this.iwRequest);
                    continue;
                }
                if (name.equals("__GETCONFIG__") && value.equalsIgnoreCase("yes")) {
                    getConfig = true;
                    continue;
                }
                if (name.equals("__PORTAL_BRAND__")) {
                    portalBrand = value;
                    continue;
                }
                if (name.equals("__COMPANY__")) {
                    company = value;
                    continue;
                }
                if (name.equals("__TOKEN__")) {
                    token = value;
                    continue;
                }
                if (name.equals("__CYCLE_VALUE__")) {
                    this.iwRequest.setCycleValue(Integer.valueOf(value));
                    continue;
                }
                if (name.startsWith("$") && name.endsWith("$") && name.length() > 2) {
                    this.iwRequest.add(name.substring(1, name.length() - 1), value.substring(1));
                }
                if (value.equals("__TODAY_START__")) {
                    ctm = System.currentTimeMillis();
                    value = String.valueOf(new Date(ctm).toString()) + " 00:00:00.0";
                }
                if (value.equals("__TODAY_END__")) {
                    ctm = System.currentTimeMillis();
                    value = String.valueOf(new Date(ctm).toString()) + " 23:59:59.9";
                }
                if (value.equals("__YESTERDAY_START__")) {
                    cln = Calendar.getInstance();
                    cln.add(5, -1);
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                }
                if (value.equals("__YESTERDAY_END__")) {
                    cln = Calendar.getInstance();
                    cln.add(5, -1);
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                }
                if (value.equals("__TODAY_START_Z__")) {
                    cln = Calendar.getInstance();
                    cln.add(11, -this.iwRequest.getTimeZoneShift());
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                }
                if (value.equals("__TODAY_END_Z__")) {
                    cln = Calendar.getInstance();
                    cln.add(11, -this.iwRequest.getTimeZoneShift());
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                }
                if (value.equals("__YESTERDAY_START_Z__")) {
                    cln = Calendar.getInstance();
                    cln.add(5, -1);
                    cln.add(11, -this.iwRequest.getTimeZoneShift());
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                }
                if (value.equals("__YESTERDAY_END_Z__")) {
                    cln = Calendar.getInstance();
                    cln.add(5, -1);
                    cln.add(11, -this.iwRequest.getTimeZoneShift());
                    value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                }
                this.iwRequest.add(name, value);
                this.iwRequest.iwSession.insert("__Param__" + name, value);
            }
            if (params.length() > 1) {
                param = params;
                equ = param.indexOf("=");
                if (equ != -1) {
                    name = param.substring(0, equ);
                    value = param.substring(equ + 1, param.length());
                } else {
                    name = param;
                    value = "";
                }
                if (method.equalsIgnoreCase("GET")) {
                    name = URLDecoder.decode(name, "UTF-8");
                    value = URLDecoder.decode(value, "UTF-8");
                }
                this.iwRequest.iwSession.insert("CurrentStartDateTime", new Timestamp(System.currentTimeMillis()).toString());
                if (name.equals("QueryStartTime")) {
                    tm = Timestamp.valueOf(value);
                    this.iwRequest.setQueryStartTime(tm);
                    this.iwRequest.setInitialQueryStartTime(tm);
                    this.iwRequest.setAddOneMinute(false);
                } else if (name.equals("__LOG_QUERY_ID__")) {
                    this.iwRequest.setCurrentTransactoionFlowId(value);
                    this.iwRequest.setLogRequest(true);
                } else if (name.equals("__QUERY_ID__")) {
                    this.iwRequest.setCurrentTransactoionFlowId(value);
                    this.iwRequest.iwSession.insert("CurrentFlowId", value);
                } else if (name.equals("LogLevel")) {
                    ll = Integer.valueOf(value);
                    if (ll != -1000) {
                        this.iwRequest.setLocalLogLevel(ll);
                        this.lnkIWServices.logConsole("LocalLogLevel = " + this.iwRequest.getLocalLogLevel(), IWServices.LOG_ERRORS, this.iwRequest);
                    }
                } else if (name.equals("__GETCONFIG__") && value.equalsIgnoreCase("yes")) {
                    getConfig = true;
                } else if (name.equals("__PORTAL_BRAND__")) {
                    portalBrand = value;
                } else if (name.equals("__COMPANY__")) {
                    company = value;
                } else if (name.equals("__TOKEN__")) {
                    token = value;
                } else if (name.equals("__CYCLE_VALUE__")) {
                    this.iwRequest.setCycleValue(Integer.valueOf(value));
                } else {
                    if (name.startsWith("$") && name.endsWith("$") && name.length() > 2) {
                        this.iwRequest.add(name.substring(1, name.length() - 1), value.substring(1));
                    }
                    if (value.equals("__TODAY_START__")) {
                        ctm = System.currentTimeMillis();
                        value = String.valueOf(new Date(ctm).toString()) + " 00:00:00.0";
                    }
                    if (value.equals("__TODAY_END__")) {
                        ctm = System.currentTimeMillis();
                        value = String.valueOf(new Date(ctm).toString()) + " 23:59:59.9";
                    }
                    if (value.equals("__YESTERDAY_START__")) {
                        cln = Calendar.getInstance();
                        cln.add(5, -1);
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                    }
                    if (value.equals("__YESTERDAY_END__")) {
                        cln = Calendar.getInstance();
                        cln.add(5, -1);
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                    }
                    if (value.equals("__TODAY_START_Z__")) {
                        cln = Calendar.getInstance();
                        cln.add(11, -this.iwRequest.getTimeZoneShift());
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                    }
                    if (value.equals("__TODAY_END_Z__")) {
                        cln = Calendar.getInstance();
                        cln.add(11, -this.iwRequest.getTimeZoneShift());
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                    }
                    if (value.equals("__YESTERDAY_START_Z__")) {
                        cln = Calendar.getInstance();
                        cln.add(5, -1);
                        cln.add(11, -this.iwRequest.getTimeZoneShift());
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 00:00:00.0";
                    }
                    if (value.equals("__YESTERDAY_END_Z__")) {
                        cln = Calendar.getInstance();
                        cln.add(5, -1);
                        cln.add(11, -this.iwRequest.getTimeZoneShift());
                        value = String.valueOf(new Timestamp(cln.getTimeInMillis()).toString().substring(0, 10)) + " 23:59:59.9";
                    }
                    this.iwRequest.add(name, value);
                    this.iwRequest.iwSession.insert("__Param__" + name, value);
                }
            }
            if (getConfig && company != null && token != null) {
                isq = new IWSqlBase();
                isq.strDriver = "com.mysql.jdbc.Driver";
                isq.strDSN = "jdbc:mysql://iw0.interweave.biz:3306/" + (portalBrand == null ? "hostedprofiles" : portalBrand);
                isq.strUser = portalBrand == null ? "root" : portalBrand;
                pwd = "dima1234";
                if (portalBrand != null) {
                    pwd = "%" + portalBrand.substring(2, 4) + "_" + portalBrand.substring(0, 2) + "_" + (portalBrand.length() - 1) + "%";
                }
                isq.strPWD = pwd;
                try {
                    isq.getConn();
                    isq.iwRequest = this.iwRequest;
                    isq.mapBuffer = new StringBuffer();
                    sqlIn = "SELECT Configuration FROM " + (portalBrand == null ? "hostedprofiles" : portalBrand) + ".companies WHERE Name='" + company + "' AND Token='" + token + "'";
                    isq.execute(sqlIn);
                    this.lnkIWServices.logConsole("Configuration: " + isq.mapBuffer, IWServices.LOG_TRACE, this.iwRequest);
                    parser = new DOMParser();
                    bis = new ByteArrayInputStream(IWServices.unEscape(isq.mapBuffer.toString()).getBytes());
                    parser.parse(new InputSource(bis));
                    trConDoc = parser.getDocument();
                    trCon = trConDoc.getDocumentElement();
                    ccl0 = trCon.getElementsByTagName("row");
                    cmpcf = new StringBuffer();
                    i0 = 0;
                    while (i0 < ccl0.getLength()) {
                        cp0 = (Element)ccl0.item(i0);
                        this.lnkIWServices.logConsole("NodeCol = " + cp0.toString(), IWServices.LOG_TRACE, this.iwRequest);
                        ccl = cp0.getElementsByTagName("col");
                        i = 0;
                        while (i < ccl.getLength()) {
                            cp = (Element)ccl.item(i);
                            this.lnkIWServices.logConsole("NodeConf = " + cp.toString(), IWServices.LOG_TRACE, this.iwRequest);
                            ccp = cp.getElementsByTagName("SF2QBConfiguration");
                            j = 0;
                            while (j < ccp.getLength()) {
                                ccpp = (Element)ccp.item(j);
                                ccppl = ccpp.getElementsByTagName("*");
                                k = 0;
                                while (k < ccppl.getLength()) {
                                    ccppc = (Element)ccppl.item(k);
                                    this.lnkIWServices.logConsole("NodeConfColCol = " + ccppc.toString(), IWServices.LOG_TRACE, this.iwRequest);
                                    cmpcf.append("<col number=\"1\" name=\"" + ccppc.getTagName() + "\">" + ccppc.getTextContent() + "</col>\n");
                                    if (ccppc.getTagName().equals("TimeZone") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setTimeZoneShift(Integer.valueOf(ccppc.getTextContent()));
                                    } else if (ccppc.getTagName().equals("EmlNtf") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEmailMode(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("StopSchedTr") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setStopMode(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("UseAdmEml") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEmailUseAdmin(ccppc.getTextContent().equals("Yes"));
                                    } else if (ccppc.getTagName().equals("CCEmail") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEmailCC(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("BCCEmail") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEmailBCC(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("HPNEmail") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEmailHPN(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("LongTimeOut") && ccppc.getTextContent().equals("Yes")) {
                                        this.iwRequest.setLongTimeOut(true);
                                        this.iwRequest.setCONNECT_TO(320000L);
                                    } else if (ccppc.getTagName().equals("SandBoxUsed") && ccppc.getTextContent().equals("Yes")) {
                                        this.iwRequest.setUseSandbox(true);
                                    } else if (ccppc.getTagName().equals("TranRecReq") && ccppc.getTextContent().startsWith("Yes")) {
                                        this.iwRequest.setTranRecReq(true);
                                        if (!ccppc.getTextContent().equals("Yes")) {
                                            try {
                                                this.iwRequest.setTranRecReqExt(Integer.parseInt(ccppc.getTextContent().substring(3)));
                                            }
                                            catch (RuntimeException e) {
                                                this.lnkIWServices.logError("Error setting extended security level", IWServices.LOG_TRACE, e, this.iwRequest);
                                            }
                                        }
                                    } else if (ccppc.getTagName().equals("Env2Con") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEnv2Con(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("Env2ConC") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setEnv2ConC(ccppc.getTextContent());
                                    } else if (ccppc.getTagName().equals("QBLocation") && ccppc.getTextContent().trim().length() > 0) {
                                        this.iwRequest.setQbLocation(ccppc.getTextContent());
                                    }
                                    ++k;
                                }
                                ++j;
                            }
                            ++i;
                        }
                        ++i0;
                    }
                    this.iwRequest.setCompanyConfiguration(cmpcf.toString());
                }
                catch (Exception e) {
                    this.lnkIWServices.logError("Error getting configuration", IWServices.LOG_TRACE, e, this.iwRequest);
                }
            }
        }
        if (this.lnkIWServices.getLogging() >= IWServices.LOG_DATA) {
            this.lnkIWServices.dumpRequest(request);
        }
        this.iwRequest.dump();
        return this.iwRequest;
    }

    public IWRequest createFlashRequest(HttpServletRequest request) {
        this.createRawRequest(request);
        this.iwRequest.fromIWXML();
        return this.iwRequest;
    }

    public IWRequest createRawRequest(HttpServletRequest request) {
        this.iwRequest = new IWRequest(request);
        this.iwRequest.rawData = this.rawData(request);
        return this.iwRequest;
    }

    public String rawData(HttpServletRequest request) {
        boolean eof = false;
        StringBuffer message = new StringBuffer("");
        char[] inBuf = new char[10240];
        int count = 0;
        int chars = 0;
        this.startTotal = System.currentTimeMillis();
        try {
            BufferedReader in = request.getReader();
            while (!eof) {
                try {
                    chars = in.read(inBuf, 0, 10240);
                }
                catch (Exception e) {
                    this.lnkIWServices.logError("XsltHttp.rawData ", IWServices.LOG_ERRORS, e, null);
                    break;
                }
                if (chars < 128) {
                    eof = true;
                    if (chars == -1) continue;
                }
                message.append(inBuf, 0, chars);
                count += chars;
            }
        }
        catch (Exception e) {
            this.lnkIWServices.logError("XsltHttp.rawData ", IWServices.LOG_ERRORS, e, null);
            return null;
        }
        return message.toString();
    }
}

