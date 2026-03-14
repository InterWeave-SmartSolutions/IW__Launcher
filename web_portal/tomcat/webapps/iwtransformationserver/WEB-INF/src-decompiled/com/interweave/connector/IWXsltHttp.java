/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.connector;

import com.interweave.adapter.email.IWEmailBaseAdaptor;
import com.interweave.connector.IWHttpImpl;
import com.interweave.core.IWApplication;
import com.interweave.core.IWServices;
import com.oreilly.servlet.MultipartRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class IWXsltHttp
extends IWHttpImpl {
    public void processTransform(HttpServletRequest request, String applicationname) {
        try {
            this.iwRequest = this.createRequest(request, applicationname);
        }
        catch (Exception e) {
            return;
        }
        this.iwRequest.processTransform();
    }

    public String processIWXmlTransform(HttpServletRequest request, String applicationname) {
        this.iwRequest = this.createRawRequest(request);
        try {
            if (this.iwRequest.fromIWXML()) {
                this.iwRequest.xsltc.lnkIWServices = this.iwRequest.lnkIWServices = IWApplication.getService(applicationname);
                return this.iwRequest.processTransform();
            }
        }
        catch (Exception e) {
            return "";
        }
        return "";
    }

    public void processIndexTransaction(HttpServletRequest request, String applicationName) {
        System.out.println("AppName = " + applicationName);
        try {
            this.iwRequest = this.createRequest(request, applicationName);
        }
        catch (Exception e) {
            System.out.println("Create Request ERROR: " + e.getMessage());
            return;
        }
        this.iwRequest.processIndex();
    }

    public void processFileUpload(HttpServletRequest request) {
        try {
            String dirName = this.lnkIWServices.uploadPath;
            MultipartRequest multi = new MultipartRequest(request, dirName, 0xA00000);
            Enumeration params = multi.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String)params.nextElement();
                String value = multi.getParameter(name);
                System.out.println(String.valueOf(name) + " = " + value);
            }
            System.out.println();
            System.out.println("Files:");
            Enumeration files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String)files.nextElement();
                String filename = multi.getFilesystemName(name);
                String type = multi.getContentType(name);
                File f = multi.getFile(name);
                System.out.println("name: " + name);
                System.out.println("filename: " + filename);
                System.out.println("type: " + type);
                if (f == null) continue;
                System.out.println("f.toString(): " + f.toString());
                System.out.println("f.getName(): " + f.getName());
                System.out.println("f.exists(): " + f.exists());
                System.out.println("f.length(): " + f.length());
                System.out.println();
            }
        }
        catch (Exception ex) {
            System.out.println("FileUpload Error: " + ex.getMessage() + "\n");
        }
    }

    public void processLoggingLevel(HttpServletRequest request) {
        String strLoggingLevel = request.getParameter("loglevel");
        this.lnkIWServices = IWApplication.getService(request.getParameter("applicationname"));
        this.lnkIWServices.setLogging(this.lnkIWServices.setLoggingLevel(strLoggingLevel));
    }

    public void processDataMap(HttpServletRequest request) {
        try {
            String apNm = request.getParameter("applicationname");
            if (apNm == null) {
                apNm = "iwtransformationserver";
            }
            this.iwRequest = this.createRequest(request, apNm);
        }
        catch (Exception e) {
            this.iwRequest.lnkIWServices.logError("Exception in processing Data Map.", IWServices.LOG_TRACE, e, null);
            return;
        }
        if (this.iwRequest != null && !this.iwRequest.isLogRequest() && !this.iwRequest.getCurrentTransactoionFlowId().equals("__reset_dedicated_server__")) {
            int cvl = this.iwRequest.getCycleValue() + 1;
            int i = 0;
            while (i < cvl) {
                this.iwRequest.tranBuffer = new StringBuffer();
                this.iwRequest.setCycleCounter(i);
                this.iwRequest.setCycleTransactionCounter(0);
                this.iwRequest.processDataMap();
                if (this.iwRequest.getStopSchedule() == 1 || this.iwRequest.getStopCycleSchedule() == 1) {
                    this.iwRequest.setCycleValue(0);
                    break;
                }
                ++i;
            }
        }
    }

    public void xmlIWDataMap(HttpServletRequest request) {
        this.iwRequest = this.createRawRequest(request);
        try {
            System.out.println("Create Request");
            if (this.iwRequest.fromIWXML(request)) {
                System.out.println("Processing Data Map");
                this.iwRequest.processDataMap();
            } else {
                System.out.println("Not Processing Data Map");
            }
        }
        catch (Exception e) {
            return;
        }
    }

    public void xmlIWFlashMap(HttpServletRequest request) {
        this.iwRequest = this.createFlashRequest(request);
        this.iwRequest.processDataMap();
    }

    public void xmlIWFlashCompileMap(HttpServletRequest request) {
        this.iwRequest = this.createRawRequest(request);
    }

    public void xmlIWDataBatch(HttpServletRequest request) {
        String xPath = request.getHeader("xpath");
        String tranname = request.getHeader("tranname");
        String transform = request.getHeader("transformation");
        String message = this.rawData(request);
        Vector<String> responses = new Vector<String>();
        if (message != null) {
            Object nlChannel = null;
            Object nChannel = null;
            try {
                Document doc = this.iwRequest.getXsltc().docFromString(message);
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.setOutputProperty("omit-xml-declaration", "yes");
                NodeIterator nl = XPathAPI.selectNodeIterator((Node)doc, (String)xPath);
                if (nl != null) {
                    Node n;
                    while ((n = nl.nextNode()) != null) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        this.iwRequest.setParam("tranname", tranname);
                        this.iwRequest.setParam("action", "Procedure");
                        serializer.transform(new DOMSource(n), new StreamResult(out));
                        this.iwRequest.fromDoc(n);
                        this.iwRequest.processDataMap();
                        responses.add(this.iwRequest.getTranResponse());
                        this.iwRequest.clear();
                        out = null;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("XsltHttp.xmlIWDataBatch " + e.getMessage());
            }
            StringBuffer out = new StringBuffer();
            if (responses.size() > 0) {
                out.append("<" + this.iwRequest.lnkIWServices.appName + ">\n");
                for (String strData : responses) {
                    out.append(strData);
                    strData = null;
                }
                out.append("</" + this.iwRequest.lnkIWServices.appName + ">\n");
            }
            this.iwRequest.getXsltc().outputs.put("1", out.toString());
            out = null;
            this.iwRequest.getXsltc().strTransform = transform;
        }
    }

    public void xmlDataMap(HttpServletRequest request) {
        String tranname = request.getHeader("tranname");
        String message = this.rawData(request);
        if (message != null) {
            Object nlChannel = null;
            Object nChannel = null;
            try {
                Document n = this.iwRequest.getXsltc().docFromString(message);
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.setOutputProperty("omit-xml-declaration", "yes");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                this.iwRequest.setParam("tranname", tranname);
                this.iwRequest.setParam("action", "Procedure");
                serializer.transform(new DOMSource(n), new StreamResult(out));
                this.iwRequest.fromDoc(n);
                this.iwRequest.processDataMap();
            }
            catch (Exception e) {
                System.out.println("XsltHttp.xmlDataMap " + e.getMessage());
            }
        }
    }

    public String getResponse(HttpServletRequest request) {
        String response = null;
        long doneTotal = System.currentTimeMillis() - this.startTotal;
        String tranname = "";
        response = this.iwRequest == null ? "<iwtimeout><iwrecordset></iwrecordset></iwtimeout>" : this.iwRequest.getResponse();
        try {
            tranname = this.iwRequest.getParameter("tranname");
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.iwRequest != null) {
            try {
                this.iwRequest.lnkIWServices.logConsole("<!-- " + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs [" + this.iwRequest.iwSession.getValue("sessionid") + "] -->", IWServices.LOG_MINIMUM, this.iwRequest);
            }
            catch (Exception e) {
                this.iwRequest.lnkIWServices.logConsole("<!-- " + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs ] -->", IWServices.LOG_MINIMUM, this.iwRequest);
            }
            this.iwRequest.clear();
            this.iwRequest = null;
        } else {
            this.iwRequest.lnkIWServices.logConsole("<!-- " + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs ] -->", IWServices.LOG_MINIMUM, this.iwRequest);
        }
        return response;
    }

    public String getScheduledResponse() {
        long doneTotal = System.currentTimeMillis() - this.startTotal;
        String tranname = "";
        StringBuffer transactionInfo = new StringBuffer();
        if (this.iwRequest != null) {
            try {
                long cts;
                long its;
                if (this.iwRequest.getCurrentTransactoionFlowId().equals("__reset_dedicated_server__")) {
                    String line;
                    BufferedReader bis;
                    InputStream is;
                    this.iwRequest.lnkIWServices.logConsole("<!-- Dedicated Server will be restarted due to client's request -->", IWServices.LOG_ERRORS, this.iwRequest);
                    String restartCommand = "C:\\Windows\\System32\\cmd.exe /c start C:\\restart_tomcat.bat";
                    Process p = Runtime.getRuntime().exec(restartCommand);
                    try {
                        is = p.getInputStream();
                        bis = new BufferedReader(new InputStreamReader(is));
                        line = null;
                        while ((line = bis.readLine()) != null) {
                            this.iwRequest.lnkIWServices.logConsole("Server restart:" + line, IWServices.LOG_ERRORS, this.iwRequest);
                        }
                        bis.close();
                    }
                    catch (IOException e) {
                        this.iwRequest.lnkIWServices.logError("Server restart output exception:", IWServices.LOG_ERRORS, e, this.iwRequest);
                        return e.toString();
                    }
                    p.waitFor();
                    this.iwRequest.lnkIWServices.logConsole("<!-- Dedicated Server has been restarted due to client's request with code " + p.exitValue() + " -->", IWServices.LOG_ERRORS, this.iwRequest);
                    if (p.exitValue() != 0) {
                        try {
                            is = p.getErrorStream();
                            if (is != null) {
                                bis = new BufferedReader(new InputStreamReader(is));
                                line = null;
                                while ((line = bis.readLine()) != null) {
                                    this.iwRequest.lnkIWServices.logConsole("Server restart error:" + line, IWServices.LOG_ERRORS, this.iwRequest);
                                }
                                bis.close();
                            }
                        }
                        catch (IOException e) {
                            this.iwRequest.lnkIWServices.logError("Server restart error exception:", IWServices.LOG_ERRORS, e, this.iwRequest);
                            return e.toString();
                        }
                    }
                }
                if (this.iwRequest.isLogRequest()) {
                    String cp = this.iwRequest.getParameter("CurrentProfile");
                    String flow = this.iwRequest.getCurrentTransactoionFlowId();
                    String hv0 = this.iwRequest.getParameter("HV");
                    String hv1 = String.valueOf((String.valueOf(flow) + cp).hashCode());
                    StringBuffer lr = new StringBuffer("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
                    lr.append("<html>\n<head>\n");
                    lr.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n");
                    lr.append("<title>Log Viewer</title>\n<style>\n<!--\n.table\n{\n");
                    lr.append("color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: normal;\n}\n-->\n</style>\n</head>\n<body class=\"labels\">\n");
                    lr.append("<table border=\"0\" cellpadding=\"5\" class=\"table\" width=\"100%\">\n");
                    if (hv0.equals(hv1)) {
                        int qi = cp.indexOf(":");
                        cp = qi < 0 ? "" : cp.substring(qi + 1);
                        String[] log = IWServices.getLogFile(cp, flow);
                        int il = 0;
                        while (il < log.length) {
                            boolean err = log[il].indexOf("Error") >= 0 || log[il].indexOf("error") >= 0 || log[il].indexOf("ERROR") >= 0 || log[il].indexOf("Exception") >= 0 || log[il].indexOf("exception") >= 0 || log[il].indexOf("EXCEPTION") >= 0 || log[il].indexOf("Failure") >= 0 || log[il].indexOf("failure") >= 0 || log[il].indexOf("FAILURE") >= 0;
                            lr.append("<tr color=");
                            lr.append(err ? "white" : "black");
                            lr.append(" bgcolor=");
                            lr.append(err ? "red" : "white");
                            lr.append(">\n<td>");
                            lr.append(log[il]);
                            lr.append("</td>\n</tr>\n");
                            ++il;
                        }
                    }
                    lr.append("</table>\n</body>\n</html>");
                    return lr.toString();
                }
                if (this.iwRequest.isAddOneMinute() && (its = this.iwRequest.getInitialQueryStartTime().getTime()) >= (cts = this.iwRequest.getQueryStartTime().getTime())) {
                    this.iwRequest.setQueryStartTime(new Timestamp(its + 60000L));
                }
                transactionInfo.append("<TransactionResult Id=\"" + this.iwRequest.getCurrentTransactoionFlowId() + "\" InstanceId=\"" + this.transactionInstanseId + "\" Result=\"" + (this.iwRequest.isFlowSuccess() ? "SUCCESS" : "FAILURE") + "\" StopSchedule=\"" + this.iwRequest.getStopSchedule() + "\">");
                transactionInfo.append("<Parameter Name=\"QueryStartTime\" Value=\"" + this.iwRequest.getQueryStartTime().toString() + "\"/>");
                transactionInfo.append("<Parameter Name=\"ReturnString\" Value=\"" + this.iwRequest.getReturnString() + "\"/>");
                if (this.iwRequest.getInitialConnectionFailures() == this.iwRequest.getConnectionFailures()) {
                    this.iwRequest.setConnectionFailures(0);
                }
                transactionInfo.append("<Parameter Name=\"ConnectionFailures\" Value=\"" + this.iwRequest.getConnectionFailures() + "\"/>");
                int oi = 0;
                while (oi < 10) {
                    if (this.iwRequest.getOtherQueryStartTime()[oi] != null) {
                        transactionInfo.append("<Parameter Name=\"QueryStartTime_" + oi + "\" Value=\"" + this.iwRequest.getOtherQueryStartTime()[oi].toString() + "\"/>");
                    }
                    if (this.iwRequest.getOtherReturnString()[oi] != null) {
                        transactionInfo.append("<Parameter Name=\"ReturnString_" + oi + "\" Value=\"" + this.iwRequest.getOtherReturnString()[oi].toString() + "\"/>");
                    }
                    if (this.iwRequest.getOtherConnectionFailures()[oi] != null) {
                        transactionInfo.append("<Parameter Name=\"ConnectionFailures_" + oi + "\" Value=\"" + this.iwRequest.getOtherConnectionFailures()[oi].toString() + "\"/>");
                    }
                    ++oi;
                }
                if (IWServices.isHosted()) {
                    if (this.iwRequest.getCurrentTransactoionFlowId().equals("__hosted_edit__")) {
                        transactionInfo.append("<Parameter Name=\"FirstName\" Value=\"" + this.iwRequest.getFirstName() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"LastName\" Value=\"" + this.iwRequest.getLastName() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"PasswordHash\" Value=\"" + this.iwRequest.getPasswordHash() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"Company\" Value=\"" + this.iwRequest.getCompany() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"Email\" Value=\"" + this.iwRequest.getEmail() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"Title\" Value=\"" + this.iwRequest.getTitle() + "\"/>");
                    } else if (this.iwRequest.getCurrentTransactoionFlowId().equals("__hosted_company_edit__")) {
                        transactionInfo.append("<Parameter Name=\"Email\" Value=\"" + this.iwRequest.getEmail() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"FirstName\" Value=\"" + this.iwRequest.getFirstName() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"LastName\" Value=\"" + this.iwRequest.getLastName() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"PasswordHash\" Value=\"" + this.iwRequest.getPasswordHash() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"Configuration\" Value=\"" + IWServices.escape(this.iwRequest.getCompanyConfiguration()) + "\"/>");
                        transactionInfo.append("<Parameter Name=\"Title\" Value=\"" + this.iwRequest.getTitle() + "\"/>");
                    } else if (this.iwRequest.getCurrentTransactoionFlowId().equals("__hosted_login__")) {
                        transactionInfo.append("<Parameter Name=\"Email\" Value=\"" + this.iwRequest.getEmail() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"PasswordHash\" Value=\"" + this.iwRequest.getPasswordHash() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"TrasactionFlowIdList\" Value=\"" + this.iwRequest.getTransactionFlowIdList() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"QueryIdList\" Value=\"" + this.iwRequest.getQueryIdList() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"CompanyConfiguration\" Value=\"" + IWServices.escape(this.iwRequest.getCompanyConfiguration()) + "\"/>");
                    } else if (this.iwRequest.getCurrentTransactoionFlowId().equals("__hosted_registration_request__")) {
                        transactionInfo.append("<Parameter Name=\"Email\" Value=\"" + this.iwRequest.getEmail() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"TrasactionFlowIdList\" Value=\"" + this.iwRequest.getTransactionFlowIdList() + "\"/>");
                        transactionInfo.append("<Parameter Name=\"QueryIdList\" Value=\"" + this.iwRequest.getQueryIdList() + "\"/>");
                    }
                }
                tranname = this.iwRequest.getParameter("tranname");
                this.iwRequest.lnkIWServices.logConsole("<!-- " + this.iwRequest.getCurrentTransactoionFlowId() + ":" + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs [" + this.iwRequest.iwSession.getValue("sessionid") + "] QST=" + this.iwRequest.getQueryStartTime().toString() + " Run#=" + this.iwRequest.getParameter("TransactionFlowCounter") + "-->", IWServices.LOG_MINIMUM, this.iwRequest);
            }
            catch (Exception e) {
                try {
                    this.iwRequest.lnkIWServices.logConsole("<!-- " + this.iwRequest.getCurrentTransactoionFlowId() + ":" + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs ] QST=" + this.iwRequest.getQueryStartTime().toString() + " Run#=" + this.iwRequest.getParameter("TransactionFlowCounter") + " -->", IWServices.LOG_MINIMUM, this.iwRequest);
                }
                catch (Exception e1) {
                    this.iwRequest.lnkIWServices.logError("Getting Runs # Exception in " + this.iwRequest.getCurrentTransactoionFlowId() + ":" + tranname, IWServices.LOG_TRACE, e, this.iwRequest);
                }
                this.iwRequest.lnkIWServices.logError("Exception in " + this.iwRequest.getCurrentTransactoionFlowId() + ":" + tranname, IWServices.LOG_TRACE, e, this.iwRequest);
            }
            if (doneTotal > 1800000L) {
                IWEmailBaseAdaptor eba = new IWEmailBaseAdaptor();
                String to = "support@interweave.biz" + (this.iwRequest.getEnv2Con().equals("Ddctd") && this.iwRequest.getEnv2ConC().equals("Ddctd") && this.iwRequest.isEmailUseAdmin() ? ";" + this.iwRequest.getUserEmail() : "");
                String context = "<IWEMail Debug=\"false\">\n<To>" + to + "</To>\n<From>iw_system_alert@interweave.biz</From>\n";
                String ce = String.valueOf(this.iwRequest.getCurrentTransactoionFlowId()) + " flow for " + this.iwRequest.getUserEmail();
                context = String.valueOf(context) + "<Subject>" + (doneTotal >= 3600000L ? "Error! " : "Warning! ") + "Long " + ce + "</Subject>\n" + (doneTotal >= 3600000L ? "<Priority>1</Priority>\n<Importance>high</Importance>\n" : "") + "<Message>Hello!\n\n  The execution of " + ce + " is very long: " + (doneTotal + 1L) + "ms. New Query Statrt Time is " + this.iwRequest.getQueryStartTime().toString() + (doneTotal >= 3600000L ? ". Return to IM is in question. Please re-start the flow with a new QST." : ". Please check it out.") + "\n\n  Regards,\n\n  Interweave System Administration.\n\n\nThis message is automatically generated. Please do not reply!\n</Message>\n</IWEMail>";
                try {
                    if (this.iwRequest.isSendEmail()) {
                        eba.sendNotificationEMail(null, this.iwRequest, this.iwRequest.lnkIWServices.getEmailNoteURL(), this.iwRequest.lnkIWServices.getEmailNotePort(), "iw_system_alert@" + this.iwRequest.lnkIWServices.getEmailNoteDNS(), context);
                    } else {
                        this.iwRequest.setSendEmail(true);
                    }
                }
                catch (Exception e) {
                    this.iwRequest.setSendEmail(false);
                    this.iwRequest.lnkIWServices.logError("Unable to send the email notification for long " + ce + " QST= " + this.iwRequest.getQueryStartTime().toString(), IWServices.LOG_TRACE, e, this.iwRequest);
                }
            }
            this.iwRequest.clear();
            this.iwRequest = null;
        } else {
            transactionInfo.append("<TransactionResult Id=\"_NOT_PARSED_ID_\" InstanceId=\"_NOT_PARSED_INSTANCE_ID_\" Result=\"" + (this.fromPrimary ? "TIMEOUT" : "REJECTED") + "\">");
            this.lnkIWServices.logConsole("<!--  Total Execution  in " + (doneTotal + 1L) + ":msecs ] -->", IWServices.LOG_MINIMUM, this.iwRequest);
        }
        transactionInfo.append("</TransactionResult>");
        return transactionInfo.toString();
    }

    public String getFlashResponse(HttpServletRequest request) {
        String response = this.iwRequest.getXsltc().getFlashResponse(this.iwRequest);
        long doneTotal = System.currentTimeMillis() - this.startTotal;
        String tranname = "";
        try {
            tranname = this.iwRequest.getParameter("tranname");
        }
        catch (Exception exception) {
            // empty catch block
        }
        System.out.println("<!-- " + tranname + " Total Execution  in " + (doneTotal + 1L) + ":msecs [" + this.iwRequest.iwSession.getValue("sessionid") + "] -->");
        this.iwRequest.clear();
        this.iwRequest = null;
        return response;
    }
}

