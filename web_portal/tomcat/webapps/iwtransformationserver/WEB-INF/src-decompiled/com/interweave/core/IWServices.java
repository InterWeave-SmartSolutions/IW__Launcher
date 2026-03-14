/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

import com.interweave.adapter.email.IWEmailBaseAdaptor;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.connector.IWXsltcImpl;
import com.interweave.core.IWReplaceString;
import com.interweave.core.IWRequest;
import com.interweave.core.IWTagStream;
import com.interweave.session.IWSessionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IWServices {
    public static String versionNumber = "2.41";
    public static String VERSION = "InterWeave Integration Platform v." + versionNumber + ". Transformation Server Build 712\n";
    private String logFile = null;
    private String errorFile = null;
    public Properties envVars = new Properties();
    public long max = 2L;
    public long min = 100000L;
    public long total = 0L;
    public long average = 0L;
    public long monitorTime = 0L;
    public long startTime = System.currentTimeMillis();
    public long lastMonitorTime = System.currentTimeMillis();
    public java.util.Date timeStarted = null;
    public String appName = "IWApplication";
    private long messageNumber = 1L;
    private long lastMessageNumber = 1L;
    public static int LOG_ERRORS = -2;
    public static int LOG_MINIMUM = 1;
    public static int LOG_HTTP = 2;
    public static int LOG_REQUEST = 3;
    public static int LOG_RESPONSE = 4;
    public static int LOG_TRANSACTION = 5;
    public static int LOG_TIMING = 6;
    public static int LOG_TRANSFORMDATA = 12;
    public static int LOG_IO = 14;
    public static int LOG_DATA = 15;
    public static int LOG_TRANSFORMS = 16;
    public static int LOG_OBJECTS = 17;
    public static int LOG_OUTPUT = 18;
    public static int LOG_PARAMETERS = 20;
    public static int LOG_ALL = 21;
    public static int LOG_TRACE = 100;
    private static boolean hosted = false;
    public IWXsltcImpl xsltc = new IWXsltcImpl();
    public Hashtable themes = new Hashtable();
    public Hashtable appvars = new Hashtable();
    private int loggingLevel = LOG_ERRORS;
    public IWSessionManager IWlnkSessionManager = null;
    public String defaultMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><iwtransaction><data></data></iwtransaction>";
    private boolean showTime = false;
    public String uploadPath = "/images";
    private String configName;
    private boolean primary;
    public static String emailHost = "Rackspace";
    public static boolean usestunnel = !emailHost.equals("Rackspace");
    private String emailNoteURL = emailHost.equals("Rackspace") ? "secure.emailsrvr.com" : (usestunnel ? "127.0.0.1" : "smtp.office365.com");
    private int emailNotePort = usestunnel ? 45587 : -1;
    public static String emailNoteName = emailHost.equals("Rackspace") ? "iwn_alert" : "iw_alert";
    private String emailNoteDNS = "interweave.biz";
    private String emailNotePwd = emailHost.equals("Rackspace") ? "Weave$$011" : "Weave$$01";
    private String emailNotePwdSys = "Weave$$02";
    public static int startTLS = emailHost.equals("Rackspace") ? 0 : 3;

    public void dumpRequest(HttpServletRequest request) {
        String name;
        Enumeration enumerate = null;
        this.logConsole("Method: " + request.getMethod(), LOG_REQUEST, null);
        this.logConsole("Auth Type: " + request.getAuthType(), LOG_REQUEST, null);
        this.logConsole("Content Length:" + request.getContentLength(), LOG_REQUEST, null);
        this.logConsole("Method:" + request.getMethod(), LOG_REQUEST, null);
        this.logConsole("Query String:" + request.getQueryString(), LOG_REQUEST, null);
        this.logConsole("==================================== Attributes", LOG_REQUEST, null);
        enumerate = request.getAttributeNames();
        while (enumerate.hasMoreElements()) {
            name = (String)enumerate.nextElement();
            this.logConsole("\t" + name + ": " + request.getAttribute(name), LOG_REQUEST, null);
        }
        this.logConsole("\n==================================== Header", LOG_REQUEST, null);
        enumerate = request.getHeaderNames();
        while (enumerate.hasMoreElements()) {
            name = (String)enumerate.nextElement();
            this.logConsole("\t" + name + ": " + request.getHeader(name), LOG_REQUEST, null);
        }
        this.logConsole("\n==================================== Parameters", LOG_REQUEST, null);
        enumerate = request.getParameterNames();
        while (enumerate.hasMoreElements()) {
            name = (String)enumerate.nextElement();
            this.logConsole("\t" + name + ": " + request.getParameter(name), LOG_REQUEST, null);
        }
    }

    public void dumpObject(Object obj) {
        if (this.loggingLevel >= LOG_OBJECTS) {
            Class<?> tc = obj.getClass();
            this.logConsole("<!-- Class Name " + tc.getName() + ": -->", LOG_ALL, null);
            Method[] methods = tc.getDeclaredMethods();
            int i = 0;
            while (i < methods.length) {
                this.logConsole("<!-- Method " + methods[i].getName() + ": -->", LOG_ALL, null);
                ++i;
            }
        }
    }

    public String htmlError(String message, String strIn) {
        String output = null;
        StringBuffer out = new StringBuffer();
        out.append("<html>\n");
        out.append("   <head>\n");
        out.append("      <title>IW 2.2 ERROR</title>\n");
        out.append("   </head>\n");
        out.append("   <body>\n");
        out.append("<p>" + message + "</p>");
        out.append("<p>" + strIn + "</p>");
        out.append("   </body>\n");
        out.append("</html>\n");
        output = out.toString();
        out = null;
        return output;
    }

    public void loadDefaultMappings(short compiledMapping, String realPath) {
        try {
            this.xsltc.load("transactions", compiledMapping, realPath);
        }
        catch (Exception e) {
            this.logError("IWServices.loadDefaultMapings " + e.getMessage(), LOG_ERRORS, e, null);
        }
    }

    public String executeTransaction(String strtranname, String strmapname) throws Exception {
        String out = "";
        IWRequest request = new IWRequest("127.0.0.0", this);
        request.add("tranname", strtranname);
        request.add("mapname", strmapname);
        request.processDataMap();
        out = request.getResponse();
        return out;
    }

    public String executeTransaction(IWRequest request, String strtranname) throws Exception {
        request.add("tranname", strtranname);
        request.processDataMap();
        String out = request.getResponse();
        return out;
    }

    public Object loadObject(String className) throws Exception {
        Class<?> _Object = null;
        Object object = null;
        try {
            this.logConsole("<!-- Class For Name " + className + ": -->", LOG_OBJECTS, null);
            _Object = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            this.logError("IWServices.loadObject " + e.getMessage(), LOG_ERRORS, e, null);
            throw e;
        }
        if (_Object != null) {
            try {
                object = _Object.newInstance();
            }
            catch (InstantiationException e) {
                this.logError("IWServices.loadObject " + e.getMessage(), LOG_ERRORS, e, null);
                throw e;
            }
            catch (IllegalAccessException e) {
                this.logError("IWServices.loadObject " + e.getMessage(), LOG_ERRORS, e, null);
                throw e;
            }
        } else {
            this.logConsole("Severe Error Class Not Found: " + className, LOG_ERRORS, null);
            throw new Exception("Severe Error Class Not Found: " + className);
        }
        this.dumpObject(object);
        return object;
    }

    public Object getObject(Class _Object) throws Exception {
        if (_Object != null) {
            Object object;
            try {
                object = _Object.newInstance();
            }
            catch (InstantiationException e) {
                this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                throw e;
            }
            catch (IllegalAccessException e) {
                this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                throw e;
            }
            this.dumpObject(object);
            return object;
        }
        this.logConsole("Severe Error Class Not Found: " + _Object.getName(), LOG_ERRORS, null);
        throw new Exception("Severe Error Class Not Found: " + _Object.getName());
    }

    public Object getObject(String className) throws Exception {
        Class<?> _Object;
        this.logConsole("<!-- Class For Name " + className + ": -->", LOG_OBJECTS, null);
        try {
            _Object = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            this.logConsole("------------- Protocol Translation Engine Error:" + e.toString(), LOG_ERRORS, null);
            throw e;
        }
        catch (Exception e1) {
            this.logError("------------- Protocol Translation Engine Error:" + e1.toString(), LOG_TRACE, e1, null);
            throw e1;
        }
        Object object = this.getObject(_Object);
        return object;
    }

    public Object getObjectValue(Class valueClass, String value) throws Exception {
        Object object = null;
        if (valueClass != null) {
            Object[] ov = new Object[]{value};
            Class[] strConArgs = new Class[]{Class.forName("java.lang.String")};
            try {
                Constructor stringConstructor = valueClass.getConstructor(strConArgs);
                try {
                    object = stringConstructor.newInstance(ov);
                    return object;
                }
                catch (IllegalArgumentException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (InstantiationException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (IllegalAccessException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (InvocationTargetException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
            }
            catch (SecurityException e0) {
                this.logConsole("IWServices.getObject " + e0.getMessage(), LOG_ERRORS, null);
                throw e0;
            }
            catch (NoSuchMethodException e0) {
                Method mvo = null;
                try {
                    mvo = valueClass.getDeclaredMethod("valueOf", strConArgs);
                }
                catch (SecurityException e1) {
                    this.logConsole("IWServices.getObject " + e1.getMessage(), LOG_ERRORS, null);
                    throw e1;
                }
                catch (NoSuchMethodException e1) {
                    this.logConsole("IWServices.getObject " + e1.getMessage(), LOG_ERRORS, null);
                    throw e1;
                }
                try {
                    object = valueClass.newInstance();
                }
                catch (InstantiationException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (IllegalAccessException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                try {
                    object = mvo.invoke(object, ov);
                    return object;
                }
                catch (IllegalArgumentException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (IllegalAccessException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
                catch (InvocationTargetException e) {
                    this.logConsole("IWServices.getObject " + e.getMessage(), LOG_ERRORS, null);
                    throw e;
                }
            }
        }
        this.logConsole("Severe Error Class Not Found: ", LOG_ERRORS, null);
        throw new Exception("Severe Error: Class Not Found.");
    }

    public long getMessageNumber() {
        return this.messageNumber++;
    }

    public synchronized void logConsole(byte[] bufOut, int length, int logLevel) {
        if (bufOut == null) {
            this.logConsole(null, logLevel, null);
        } else {
            String strOut = new String(bufOut, 0, length);
            if (strOut.length() > 0) {
                this.logConsole(strOut, logLevel, null);
            }
            strOut = null;
        }
    }

    public synchronized void logError(String strOut, int logLevel, Exception e, IWRequest iwRequest) {
        if (iwRequest != null) {
            String sm;
            if (iwRequest.getCycleValue() > 0 && iwRequest.isConnectionError()) {
                iwRequest.setStopCycleSchedule(1);
            }
            if ((sm = iwRequest.getStopMode()) != null && !sm.equals("None")) {
                if (sm.equals("EveryErr")) {
                    iwRequest.setStopSchedule(1);
                } else if (sm.equals("Con") && iwRequest.isConnectionError()) {
                    iwRequest.setStopSchedule(1);
                }
            }
        }
        String outStr = null;
        Timestamp tms = new Timestamp(System.currentTimeMillis());
        String prefix = String.valueOf(this.showTime ? tms.toString() : "") + " IW " + versionNumber;
        outStr = strOut == null || strOut.length() == 0 ? String.valueOf(prefix) + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + " ERROR:\t" + "******************Unknown Message*************\n" : String.valueOf(prefix) + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + " ERROR\t" + strOut + "\n";
        System.out.print(outStr);
        if (logLevel == LOG_TRACE) {
            StackTraceElement[] tr = e.getStackTrace();
            if (tr == null || tr.length == 0) {
                System.out.println("No stack trace for: " + e.getMessage());
            } else {
                try {
                    e.printStackTrace();
                }
                catch (RuntimeException e1) {
                    System.out.println("Unable to print stack trace for: " + e.getMessage() + ". Exception " + e1.getMessage());
                }
            }
        } else {
            System.out.println(e.getMessage());
        }
        if (this.errorFile != null) {
            this.appendFile(this.errorFile, outStr);
        }
        if (iwRequest != null && iwRequest.getEmailMode() != null && !iwRequest.getEmailMode().equals("None") && iwRequest.isEmailNoLoop()) {
            iwRequest.setEmailNoLoop(false);
            this.sendNotification(outStr, e.getMessage(), iwRequest, logLevel);
            iwRequest.setEmailNoLoop(true);
        }
    }

    public void logConsole(String strOut, int logLevel, IWRequest iwRequest) {
        String outStr = null;
        int baseLogLevel = this.loggingLevel;
        if (iwRequest != null && iwRequest.getLocalLogLevel() != Integer.MIN_VALUE) {
            baseLogLevel = iwRequest.getLocalLogLevel();
        }
        Timestamp tms = new Timestamp(System.currentTimeMillis());
        if (logLevel <= baseLogLevel) {
            outStr = strOut == null || strOut.length() == 0 ? (this.showTime ? String.valueOf(tms.toString()) + " IW " + versionNumber + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + "\t" + "******************Unknown Message*************\n" : "IW " + versionNumber + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + "\t" + "******************Unknown Message*************\n") : (this.showTime ? String.valueOf(tms.toString()) + " IW " + versionNumber + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + "\t" + strOut + "\n" : "IW " + versionNumber + " " + "TS" + " " + (iwRequest == null ? "" : iwRequest.getCurrentTransactoionFlowId()) + " " + (iwRequest == null ? "" : iwRequest.getUserEmail()) + "\t" + strOut + "\n");
            System.out.print(outStr);
            if (this.logFile != null) {
                this.appendFile(this.logFile, outStr);
            }
            if (iwRequest != null && iwRequest.getEmailMode() != null && (iwRequest.getEmailMode().equals("Daily") || iwRequest.getEmailMode().equals("DailyEveryErr") || iwRequest.getEmailMode().equals("ConD")) && iwRequest.isEmailNoLoop()) {
                iwRequest.setEmailNoLoop(false);
                this.storeAndSendNotification(outStr, iwRequest, logLevel);
                iwRequest.setEmailNoLoop(true);
            }
        }
    }

    private void storeAndSendNotification(String error, IWRequest iwRequest, int logLevel) {
        Calendar cln = Calendar.getInstance();
        Date today = new Date(cln.getTimeInMillis());
        cln.add(5, -1);
        Date yesterday = new Date(cln.getTimeInMillis());
        String tdt = IWReplaceString.replace(today.toString(), "-", "");
        String yd = yesterday.toString();
        String ydt = IWReplaceString.replace(yd, "-", "");
        String fn = IWReplaceString.replace(iwRequest.getUserEmail(), "@", "_");
        String fileName = IWReplaceString.replace(fn, ".", "_");
        String fileNameYd = String.valueOf(fileName) + ydt + ".erl";
        String fileNameTd = String.valueOf(fileName) + tdt + ".erl";
        File logFileYd = new File(fileNameYd);
        File logFileTd = new File(fileNameTd);
        if (!logFileTd.exists() && logFileYd.exists()) {
            if (this.sendEMail(yd, iwRequest, logLevel, fileNameYd)) {
                if (!logFileYd.delete()) {
                    System.out.println("Unable to delete the log file " + fileNameYd);
                }
                this.appendFile(fileNameTd, error);
            } else {
                System.out.println("Unable to send the log file " + fileNameYd);
                this.appendFile(fileNameYd, error);
            }
        } else {
            this.appendFile(fileNameTd, error);
            if (logFileYd.exists() && !logFileYd.delete()) {
                System.out.println("Can not delete the log file " + fileNameYd);
            }
        }
    }

    private void sendNotification(String prefix, String error, IWRequest iwRequest, int logLevel) {
        if (error == null || iwRequest == null || iwRequest.getEmailMode() == null || iwRequest.getLastEmail() != null && error.equals(iwRequest.getLastEmail())) {
            return;
        }
        iwRequest.setLastEmail(error);
        if (iwRequest.isConnectionError() && iwRequest.getEmailMode().indexOf("Con") >= 0 || iwRequest.getEmailMode().indexOf("EveryErr") >= 0) {
            this.sendEMail(String.valueOf(prefix) + error, iwRequest, logLevel, null);
        }
        if (iwRequest.getEmailMode().startsWith("ConD") || iwRequest.getEmailMode().startsWith("Daily")) {
            this.storeAndSendNotification(String.valueOf(prefix) + error, iwRequest, logLevel);
        }
        iwRequest.setConnectionError(false);
    }

    private boolean sendEMail(String logData, IWRequest iwRequest, int logLevel, String attachment) {
        String context;
        String bcc;
        String cc;
        IWEmailBaseAdaptor eba = new IWEmailBaseAdaptor();
        boolean ret = true;
        String to = iwRequest.getUserEmail();
        if (to == null) {
            to = "";
        }
        if ((cc = iwRequest.getEmailCC()) == null) {
            cc = "";
        }
        if ((bcc = iwRequest.getEmailBCC()) == null) {
            bcc = "";
        }
        if (!iwRequest.isEmailUseAdmin()) {
            to = cc;
            cc = "";
        }
        if (to.trim().length() == 0) {
            if (cc.trim().length() == 0) {
                System.out.println("Unable to send the email notification for: " + logData + "\n\nNo Recipient");
                ret = false;
            } else {
                to = cc;
            }
        }
        if (!(!ret || iwRequest.isConnectionError() && iwRequest.getQbLocation().equals("HOST"))) {
            context = "<IWEMail Debug=\"" + (logLevel == LOG_TRACE ? "true" : "false") + "\">\n<To>" + to + "</To>\n<From>" + emailNoteName + IWServices.getEmailSuffix(iwRequest) + "@interweave.biz</From>\n<Cc>" + cc + "</Cc>\n<Bcc>" + bcc + "</Bcc>\n";
            if (attachment == null) {
                String increase;
                String ce = String.valueOf(iwRequest.isConnectionError() ? " Connection" : "") + " Error";
                if (iwRequest.isConnectionError()) {
                    switch (iwRequest.getConnectionFailures()) {
                        case 2: {
                            increase = "\n\n Execution interval will be increased to 1 - 4 hours if the original one is shorter then 4 hours.";
                            break;
                        }
                        case 0: 
                        case 1: {
                            increase = "";
                            break;
                        }
                        default: {
                            increase = "\n\n Execution interval has been increased to 1 - 4 hours if the original one is shorter then 4 hours.";
                            break;
                        }
                    }
                } else {
                    increase = "";
                }
                context = String.valueOf(context) + "<Subject>IW Transaction Flow " + iwRequest.getCurrentTransactoionFlowId() + ce + "</Subject>\n<Message>Hello!\n\n  The following" + ce + " occured during the execution of the Transaction Flow " + iwRequest.getCurrentTransactoionFlowId() + ":\n" + IWServices.escape(logData, false) + "\n\nInitial Query Start Time: " + iwRequest.getInitialQueryStartTime().toString() + "\nFinal Query Start Time: " + iwRequest.getQueryStartTime().toString() + "\n\n Please take a look." + increase + "\n\n  Regards,\n\n  Interweave Support.\n\n\nThis message is automatically generated. Please do not reply!\n</Message>\n</IWEMail>";
            } else {
                context = String.valueOf(context) + "<Subject>IW Transaction Flow Daily Log report for " + logData + "</Subject>\n<Message>Hello!\n\n  The log report for " + logData + " is attached.\n\n  Regards,\n\n  Interweave Support.\n\n\nThis message is automatically generated. Please do not reply!\n</Message>\n<FileAttachment>" + attachment + "</FileAttachment></IWEMail>";
            }
            try {
                if (iwRequest.isSendEmail()) {
                    eba.sendNotificationEMail(iwRequest, iwRequest.lnkIWServices.getEmailNoteURL(), iwRequest.lnkIWServices.getEmailNotePort(), context);
                } else {
                    iwRequest.setSendEmail(true);
                }
            }
            catch (Exception e) {
                iwRequest.setSendEmail(false);
                System.out.println("Unable to send the email notification for: " + logData + "\n\n Exception " + e.getMessage());
                ret = false;
            }
        }
        if ((to = iwRequest.getEmailHPN()) != null && to.trim().length() > 0 && iwRequest.isConnectionError() && iwRequest.getConnectionFailures() == 1) {
            context = "<IWEMail Debug=\"" + (logLevel == LOG_TRACE ? "true" : "false") + "\">\n<To>" + to + "</To>\n<From>" + emailNoteName + IWServices.getEmailSuffix(iwRequest) + "@interweave.biz</From>\n";
            context = String.valueOf(context) + "<Subject>IW Transaction Flow " + iwRequest.getCurrentTransactoionFlowId() + " Connection Error</Subject>\n<Priority>1</Priority>\n<Importance>high</Importance>\n<Message>Hello!\n\n  The following connection error occured during the execution of the Transaction Flow " + iwRequest.getCurrentTransactoionFlowId() + ":\n" + IWServices.escape(logData, false) + "\n\n Please take a look.\n\n  Regards,\n\n  Interweave Support.\n\n\nThis message is automatically generated. Please do not reply!\n</Message>\n</IWEMail>";
            try {
                eba.sendNotificationEMail(iwRequest, iwRequest.lnkIWServices.getEmailNoteURL(), iwRequest.lnkIWServices.getEmailNotePort(), context);
            }
            catch (Exception e) {
                System.out.println("Unable to send the hosted provider email notification for: " + logData + "\n\n Exception " + e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    private void appendFile(String fileName, String strOut) {
        RandomAccessFile out = null;
        try {
            out = new RandomAccessFile(fileName, "rw");
            out.seek(out.length());
            out.write(strOut.getBytes());
            out.close();
            out = null;
        }
        catch (Exception e) {
            System.out.println("Unable to append " + strOut + " to the file " + fileName + "; Exception " + e.getMessage());
        }
        if (out != null) {
            try {
                out.close();
                out = null;
            }
            catch (Exception e) {
                System.out.println("Unable to append " + strOut + " to the file " + fileName + "; Exception " + e.getMessage());
            }
        }
    }

    public int setLoggingLevel(String strLevel) {
        if (strLevel.compareToIgnoreCase("LOG_ERRORS") == 0) {
            this.logConsole("Set Logging Level: LOG_ERRORS", LOG_ERRORS, null);
            return LOG_ERRORS;
        }
        if (strLevel.compareToIgnoreCase("LOG_MINIMUM") == 0) {
            this.logConsole("Set Logging Level: LOG_MINIMUM", LOG_ERRORS, null);
            return LOG_MINIMUM;
        }
        if (strLevel.compareToIgnoreCase("LOG_HTTP") == 0) {
            this.logConsole("Set Logging Level: LOG_HTTP", LOG_ERRORS, null);
            return LOG_HTTP;
        }
        if (strLevel.compareToIgnoreCase("LOG_REQUEST") == 0) {
            this.logConsole("Set Logging Level: LOG_REQUEST", LOG_ERRORS, null);
            return LOG_REQUEST;
        }
        if (strLevel.compareToIgnoreCase("LOG_RESPONSE") == 0) {
            this.logConsole("Set Logging Level: LOG_RESPONSE", LOG_ERRORS, null);
            return LOG_RESPONSE;
        }
        if (strLevel.compareToIgnoreCase("LOG_TRANSACTION") == 0) {
            this.logConsole("Set Logging Level: LOG_TRANSACTION", LOG_ERRORS, null);
            return LOG_TRANSACTION;
        }
        if (strLevel.compareToIgnoreCase("LOG_TIMING") == 0) {
            this.logConsole("Set Logging Level: LOG_TIMING", LOG_ERRORS, null);
            return LOG_TIMING;
        }
        if (strLevel.compareToIgnoreCase("LOG_TRANSFORMDATA") == 0) {
            this.logConsole("Set Logging Level: LOG_TRANSFORMDATA", LOG_ERRORS, null);
            return LOG_TRANSFORMDATA;
        }
        if (strLevel.compareToIgnoreCase("LOG_IO") == 0) {
            this.logConsole("Set Logging Level: LOG_IO", LOG_ERRORS, null);
            return LOG_IO;
        }
        if (strLevel.compareToIgnoreCase("LOG_DATA") == 0) {
            this.logConsole("Set Logging Level: LOG_DATA", LOG_ERRORS, null);
            return LOG_DATA;
        }
        if (strLevel.compareToIgnoreCase("LOG_TRANSFORMS") == 0) {
            this.logConsole("Set Logging Level: LOG_TRANSFORMS", LOG_ERRORS, null);
            return LOG_TRANSFORMS;
        }
        if (strLevel.compareToIgnoreCase("LOG_OBJECTS") == 0) {
            this.logConsole("Set Logging Level: LOG_OBJECTS", LOG_ERRORS, null);
            return LOG_OBJECTS;
        }
        if (strLevel.compareToIgnoreCase("LOG_OUTPUT") == 0) {
            this.logConsole("Set Logging Level: LOG_OUTPUT", LOG_ERRORS, null);
            return LOG_OUTPUT;
        }
        if (strLevel.compareToIgnoreCase("LOG_PARAMETERS") == 0) {
            this.logConsole("Set Logging Level: LOG_PARAMETERS", LOG_ERRORS, null);
            return LOG_PARAMETERS;
        }
        if (strLevel.compareToIgnoreCase("LOG_ALL") == 0) {
            this.logConsole("Set Logging Level: LOG_ALL", LOG_ERRORS, null);
            return LOG_ALL;
        }
        if (strLevel.compareToIgnoreCase("LOG_TRACE") == 0) {
            this.logConsole("Set Logging Level: LOG_TRACE", LOG_ERRORS, null);
            return LOG_TRACE;
        }
        this.logConsole("Set Logging Level: LOG_ERRORS", LOG_ERRORS, null);
        return LOG_ERRORS;
    }

    public void loadAppVars(String strGlobals) {
        String colStart = "<col";
        String colStop = "</col>";
        String colName = "";
        String colValue = "";
        String curCol = "";
        this.appvars.clear();
        IWTagStream tagStream = new IWTagStream();
        while (curCol != null) {
            curCol = tagStream.fragment(strGlobals, colStart, colStop);
            if (curCol == null || !tagStream.tagFound) {
                curCol = null;
                continue;
            }
            strGlobals = tagStream.remove(strGlobals, colStart, colStop);
            colName = tagStream.attributeData(curCol, "name");
            if (colName == null) continue;
            colValue = tagStream.tagData(curCol);
            if (colName.indexOf("logginglevel") != -1) {
                if (colValue == null || colValue.length() <= 0) continue;
                this.loggingLevel = this.setLoggingLevel(colValue);
                continue;
            }
            if (colName.indexOf("loggingdir") != -1) {
                if (colValue == null || colValue.length() <= 0) continue;
                this.logFile = String.valueOf(colValue) + "/IW" + this.appName + ".log";
                continue;
            }
            if (colName.indexOf("errordir") != -1) {
                if (colValue == null || colValue.length() <= 0) continue;
                this.errorFile = String.valueOf(colValue) + "/IW" + this.appName + "Error.log";
                continue;
            }
            this.appvars.put(colName, colValue);
        }
    }

    private short readConfigContext(String realPath) {
        String cm;
        int ret = -1;
        DOMParser parser = new DOMParser();
        try {
            parser.parse(String.valueOf(realPath) + "/WEB-INF/config.xml");
        }
        catch (SAXException e) {
            e.printStackTrace();
            return (short)ret;
        }
        catch (IOException e) {
            e.printStackTrace();
            return (short)ret;
        }
        Document conDoc = parser.getDocument();
        Element bdConf = conDoc.getDocumentElement();
        String tsnm = bdConf.getAttribute("Name").trim();
        if (tsnm.indexOf(":") >= 0) {
            String[] nmpr = tsnm.split(":");
            switch (nmpr.length) {
                default: {
                    if (nmpr[5].trim().length() > 0) {
                        this.emailNotePwdSys = nmpr[5];
                    }
                }
                case 5: {
                    if (nmpr[4].trim().length() > 0) {
                        this.emailNotePwd = nmpr[4];
                    }
                }
                case 4: {
                    if (nmpr[4].trim().length() > 0) {
                        this.emailNoteDNS = nmpr[3];
                    }
                }
                case 3: {
                    if (nmpr[2].trim().length() > 0) {
                        this.emailNotePort = Integer.valueOf(nmpr[2]);
                    }
                }
                case 2: {
                    if (nmpr[1].trim().length() > 0) {
                        this.emailNoteURL = nmpr[1];
                    }
                }
                case 1: {
                    this.setConfigName(nmpr[0].trim());
                    break;
                }
                case 0: {
                    this.setConfigName("");
                    break;
                }
            }
        } else {
            this.setConfigName(tsnm);
        }
        String prm = bdConf.getAttribute("IsPrimary");
        this.setPrimary(new Boolean(prm.equalsIgnoreCase("true") || prm.equals("1")));
        this.loggingLevel = this.setLoggingLevel(bdConf.getAttribute("LogLevel"));
        String tms = bdConf.getAttribute("IsTimeStamping");
        if (tms != null) {
            this.setShowTime(new Boolean(tms.equalsIgnoreCase("true") || tms.equals("1")));
        }
        ret = (cm = bdConf.getAttribute("IsCompiledMapping")) != null ? (cm.equalsIgnoreCase("true") || cm.equals("1") ? 1 : 0) : 1;
        String hst = bdConf.getAttribute("IsHosted");
        if (hst == null || hst.length() == 0) {
            hosted = false;
        } else {
            boolean bl = hosted = hst.equalsIgnoreCase("true") || hst.equals("1");
        }
        if (hosted && ret == 0) {
            ret = 2;
        }
        return (short)ret;
    }

    public void initialize(String applicationName, String realPath) {
        String strGlobals = null;
        Calendar calendar = Calendar.getInstance();
        System.getProperties().list(System.out);
        try {
            this.appName = applicationName;
            try {
                this.getEnvVars();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.timeStarted = calendar.getTime();
            System.out.println("\tWelcome to " + VERSION + this.timeStarted.toString() + "\tStarting Application " + applicationName);
            this.IWlnkSessionManager = new IWSessionManager(this);
            try {
                this.xsltc.lnkIWServices = this;
                strGlobals = this.executeTransaction("appglobals", "appglobaltran");
                this.loadAppVars(strGlobals);
                short cm = 1;
                if (realPath != null && (cm = this.readConfigContext(realPath)) < 0) {
                    this.logConsole("Configuration file reading error ", LOG_ERRORS, null);
                }
                this.xsltc.mappings = null;
                this.loadDefaultMappings(cm, realPath);
            }
            catch (Exception ex) {
                this.logConsole("Warning: IWServices.initialize AppGlobals transform ERROR " + ex.getMessage(), LOG_ERRORS, null);
                return;
            }
        }
        catch (Exception e) {
            this.logConsole("Warning: IWServices.initialize config transform ERROR [USING DEFAULT VALUES]" + e.getMessage(), LOG_ERRORS, null);
            return;
        }
        if (this.logFile != null && this.logFile.length() > 0) {
            System.out.println(String.valueOf(VERSION) + this.timeStarted.toString() + " Log File Directory:" + this.logFile);
        }
        if (this.errorFile != null && this.errorFile.length() > 0) {
            System.out.println(String.valueOf(VERSION) + this.timeStarted.toString() + " Error File Directory:" + this.errorFile);
        }
        IWEmailBaseAdaptor eba = new IWEmailBaseAdaptor();
        String to = "support@interweave.biz";
        String context = "<IWEMail Debug=\"false\">\n<To>" + to + "</To>\n<From>iw_system_alert@interweave.biz</From>\n";
        int lps = realPath.lastIndexOf(File.separator);
        this.timeStarted = calendar.getTime();
        String ce = String.valueOf(lps < 0 ? realPath : realPath.substring(lps)) + " started";
        context = String.valueOf(context) + "<Subject>" + ce + "</Subject>\n<Message>Hello!\n\n  The server " + ce + " at " + this.timeStarted.toString() + ". \n\n  Regards,\n\n  Interweave System Administration.\n\n\nThis message is automatically generated. Please do not reply!\n</Message>\n</IWEMail>";
        try {
            eba.sendNotificationEMail(this, null, this.emailNoteURL, this.emailNotePort, "iw_system_alert@" + this.emailNoteDNS, context);
        }
        catch (Exception e) {
            System.out.println("Unable to send the email notification for server start: \n\n Exception " + e.getMessage());
            return;
        }
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

    public void init(String applicationName) {
        Object strGlobals = null;
        Object data = null;
        Calendar calendar = Calendar.getInstance();
        try {
            this.appName = applicationName;
            this.timeStarted = calendar.getTime();
            System.out.println("Welcome to " + VERSION + this.timeStarted.toString() + "\tStarting Application " + applicationName);
            this.IWlnkSessionManager = new IWSessionManager(this);
        }
        catch (Exception e) {
            this.logConsole("Warning: IWServices.initialize config transform ERROR [USING DEFAULT VALUES]" + e.getMessage(), LOG_ERRORS, null);
            return;
        }
        if (this.logFile != null && this.logFile.length() > 0) {
            System.out.println(String.valueOf(VERSION) + this.timeStarted.toString() + " Log File Directory:" + this.logFile);
        }
        if (this.errorFile != null && this.errorFile.length() > 0) {
            System.out.println(String.valueOf(VERSION) + this.timeStarted.toString() + " Error File Directory:" + this.errorFile);
        }
    }

    public String formatGMT() {
        Calendar gmtTime = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        String Month = null;
        String Day = null;
        String Hour = null;
        String Minute = null;
        String Second = null;
        String Year = String.valueOf(gmtTime.get(1));
        int month = gmtTime.get(2);
        Month = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        int day = gmtTime.get(5);
        Day = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        int hour = gmtTime.get(11);
        Hour = hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour);
        int min = gmtTime.get(12);
        Minute = min < 10 ? "0" + String.valueOf(min) : String.valueOf(min);
        int sec = gmtTime.get(13);
        Second = sec < 10 ? "0" + String.valueOf(sec) : String.valueOf(sec);
        return String.valueOf(Year) + Month + Day + "-" + Hour + ":" + Minute + ":" + Second;
    }

    public void timings(long time) {
        this.total += ++time;
        if (time < this.min) {
            this.min = time;
        }
        if (time > this.max) {
            this.max = time;
        }
    }

    public void printHeadings() {
        if (this.monitorTime == 0L) {
            return;
        }
        if (System.currentTimeMillis() > this.lastMonitorTime + this.monitorTime) {
            this.lastMonitorTime = System.currentTimeMillis();
            this.logConsole("============================== Statistics ==============================", LOG_MINIMUM, null);
            this.logConsole("Time-Active   Requests      MinTime      MaxTime", LOG_MINIMUM, null);
            this.printTimings();
            this.logConsole("========================================================================", LOG_MINIMUM, null);
        }
    }

    public void printTimings() {
        String strFormat = null;
        StringBuffer strBuffer = new StringBuffer();
        StringBuffer strOut = new StringBuffer();
        long diffTime = System.currentTimeMillis() - this.startTime;
        long diffDays = 0L;
        long diffHours = 0L;
        long diffMinutes = 0L;
        long diffSeconds = 0L;
        diffDays = diffTime / 86400000L;
        diffHours = (diffTime %= 86400000L) / 3600000L;
        diffMinutes = (diffTime %= 3600000L) / 60000L;
        diffSeconds = (diffTime %= 60000L) / 1000L;
        diffTime = System.currentTimeMillis() - this.startTime;
        strFormat = this.formatNumber(diffDays, 3);
        strBuffer.append(strFormat);
        strBuffer.append(":");
        strFormat = null;
        strFormat = this.formatNumber(diffHours, 2);
        strBuffer.append(strFormat);
        strBuffer.append(":");
        strFormat = null;
        strFormat = this.formatNumber(diffMinutes, 2);
        strBuffer.append(strFormat);
        strBuffer.append(":");
        strFormat = null;
        strFormat = this.formatNumber(diffSeconds, 2);
        strBuffer.append(strFormat);
        strFormat = null;
        strFormat = this.formatColumn(strBuffer.toString(), 13);
        strOut.append(strFormat);
        strFormat = null;
        strBuffer = null;
        strBuffer = new StringBuffer();
        strFormat = this.formatNumber(this.messageNumber, 13);
        strBuffer.append(strFormat);
        strFormat = null;
        strFormat = this.formatColumn(strBuffer.toString(), 15);
        strOut.append(strFormat);
        strFormat = null;
        strBuffer = null;
        strBuffer = new StringBuffer();
        strFormat = this.formatNumber(this.min / 1000L, 3);
        strBuffer.append(strFormat);
        strFormat = null;
        strBuffer.append(".");
        strFormat = this.formatNumber(this.min % 1000L + 1L, 3);
        strBuffer.append(strFormat);
        strFormat = null;
        strFormat = this.formatColumn(strBuffer.toString(), 13);
        strOut.append(strFormat);
        strFormat = null;
        strBuffer = null;
        strBuffer = new StringBuffer();
        strFormat = this.formatNumber(this.max / 1000L, 3);
        strBuffer.append(strFormat);
        strFormat = null;
        strBuffer.append(".");
        strFormat = this.formatNumber(this.max % 1000L, 3);
        strBuffer.append(strFormat);
        strFormat = null;
        strFormat = this.formatColumn(strBuffer.toString(), 13);
        strOut.append(strFormat);
        strFormat = null;
        this.logConsole(strOut.toString(), LOG_MINIMUM, null);
        strBuffer = null;
        strOut = null;
    }

    private String formatColumn(String data, int width) {
        String strData = null;
        StringBuffer strBuffer = new StringBuffer();
        int pad = width - data.length();
        strBuffer.append(data);
        while (pad-- > 0) {
            strBuffer.append(" ");
        }
        strData = strBuffer.toString();
        strBuffer = null;
        return strData;
    }

    private String formatNumber(long number, int format) {
        String strNumber = "" + number;
        StringBuffer strBuffer = new StringBuffer();
        int pad = format - strNumber.length();
        while (pad-- > 0) {
            strBuffer.append("0");
        }
        strBuffer.append(strNumber);
        strNumber = null;
        strNumber = strBuffer.toString();
        strBuffer = null;
        return strNumber;
    }

    public String formatPad(String strNumber, int format) {
        StringBuffer strBuffer = new StringBuffer();
        int pad = format - strNumber.length();
        while (pad-- > 0) {
            strBuffer.append("0");
        }
        strBuffer.append(strNumber);
        strNumber = null;
        strNumber = strBuffer.toString();
        strBuffer = null;
        return strNumber;
    }

    public void printDir() {
        System.out.println(this.getDir());
    }

    public String getDir() {
        String path = null;
        File file = new File("tmp.gbg");
        try {
            path = file.getCanonicalPath();
        }
        catch (Exception e) {
            this.logError("IWServices.getDir " + e.getMessage(), LOG_ERRORS, e, null);
        }
        file.delete();
        path = path.substring(0, path.length() - 7);
        return path;
    }

    public String fileToString(String fileName) throws Exception {
        System.out.println("Loading File: " + fileName);
        File file = new File(fileName);
        BufferedReader input = null;
        try {
            System.out.println("Loaded File: " + file.getName());
            input = new BufferedReader(new FileReader(file));
            System.out.println("Reader: ");
        }
        catch (FileNotFoundException e) {
            this.logError(" IWServices.fileToString " + e.getMessage(), LOG_ERRORS, e, null);
            throw e;
        }
        StringBuffer outString = new StringBuffer();
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                outString.append(line);
            }
            input.close();
        }
        catch (IOException e) {
            this.logError(" IWServices.fileToString " + e.getMessage(), LOG_ERRORS, e, null);
            throw e;
        }
        line = outString.toString();
        outString = null;
        file = null;
        input = null;
        return line;
    }

    public void setLogging(int logLevel) {
        this.loggingLevel = logLevel;
    }

    public int getLogging() {
        return this.loggingLevel;
    }

    public int getLogging(IWRequest request) {
        if (request != null && request.getLocalLogLevel() != Integer.MIN_VALUE) {
            return request.getLocalLogLevel();
        }
        return this.loggingLevel;
    }

    public void loadThemes() {
    }

    private void loadTheme(String strTheme, Document doc) {
    }

    public String getTheme(String strTheme) {
        return (String)this.themes.get(strTheme);
    }

    public void getEnvVars() throws Throwable {
        String line;
        Process p = null;
        Runtime r = Runtime.getRuntime();
        String OS = System.getProperty("os.name").toLowerCase();
        this.envVars.clear();
        System.out.println(OS);
        p = OS.indexOf("windows 9") > -1 ? r.exec("command.com /c set") : (OS.indexOf("nt") > -1 || OS.indexOf("windows 2000") > -1 || OS.indexOf("windows xp") > -1 ? r.exec("cmd.exe /c set") : r.exec("env"));
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = br.readLine()) != null) {
            int idx = line.indexOf(61);
            String key = line.substring(0, idx).toUpperCase();
            String value = line.substring(idx + 1);
            String appStr = this.appName.toUpperCase();
            if (key.startsWith("IW_HOME")) {
                if (System.getProperties().getProperty("java.library.path").indexOf(";" + value + "\\dll") != -1) continue;
                System.getProperties().setProperty("java.library.path", String.valueOf(System.getProperties().getProperty("java.library.path")) + ";" + value + "\\dll");
                System.out.println(System.getProperties().getProperty("java.library.path"));
                continue;
            }
            if (key.startsWith("INTERWEAVE")) {
                this.envVars.setProperty(key, value);
                System.out.println("Global Variable:    " + key + " = " + value);
                continue;
            }
            if (!key.startsWith(appStr)) continue;
            this.envVars.setProperty(key, value);
            System.out.println("Application Variable:" + key + " = " + value);
        }
    }

    public static String escape(String s) {
        return IWServices.escape(s, true);
    }

    public static String escape(String s, boolean xml) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < s.length()) {
            switch (s.charAt(i)) {
                case '&': {
                    if (i > s.length() - 5 || s.charAt(i + 1) != 'a' || s.charAt(i + 2) != 'm' || s.charAt(i + 3) != 'p' || s.charAt(i + 4) != ';') {
                        sb.append(xml ? "&amp;" : "+");
                        break;
                    }
                    sb.append('&');
                    break;
                }
                case '<': {
                    sb.append(xml ? "&lt;" : "[");
                    break;
                }
                case '>': {
                    sb.append(xml ? "&gt;" : "]");
                    break;
                }
                default: {
                    sb.append(s.charAt(i));
                }
            }
            ++i;
        }
        return sb.toString();
    }

    public static String escapeQuote(String sqlString, String escString) {
        if (sqlString == null) {
            return null;
        }
        int comaPos = 0;
        int comaPose = 0;
        int qb = 0;
        int qe = 0;
        StringBuffer ret = new StringBuffer(sqlString);
        int el = escString.length();
        while (true) {
            int qc;
            comaPose = ret.indexOf(",", comaPos);
            qb = ret.indexOf("'", comaPos);
            if (comaPose < 0) {
                if (qb < 0) {
                    return ret.toString();
                }
                qe = ret.lastIndexOf("'");
                if (qe < 0 || qe == qb) {
                    return ret.toString();
                }
                qc = ret.indexOf("'", ++qb);
                while (qc > 0 && qc < qe) {
                    ret.replace(qc, qc + 1, escString);
                    qc = ret.indexOf("'", qc + el);
                    qe += el - 1;
                }
                return ret.toString();
            }
            if (qb >= 0 && (qe = ret.lastIndexOf("'", comaPose)) >= 0 && qe > qb) {
                qc = ret.indexOf("'", ++qb);
                while (qc > 0 && qc < qe) {
                    ret.replace(qc, qc + 1, escString);
                    qc = ret.indexOf("'", qc + el);
                    qe += el - 1;
                }
            }
            comaPos = comaPose + 1;
        }
    }

    public static boolean isImplemented(Class inputClass, String implementedInterface) {
        Class tmp = inputClass;
        while (tmp != null) {
            Class<?>[] its = tmp.getInterfaces();
            int i = 0;
            while (i < its.length) {
                if (its[i].getName().equals(implementedInterface)) {
                    return true;
                }
                ++i;
            }
            tmp = tmp.getSuperclass();
        }
        return false;
    }

    public static String unEscape(String s) {
        return s.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&apos;", "'").replaceAll("&#10;", "\n").replaceAll("&#xD;", "\r");
    }

    public static List<Parameter> getParametersByInput(List<Parameter> inputParameters, String inputMask) {
        Vector<Parameter> outList = new Vector<Parameter>();
        for (Parameter np : inputParameters) {
            String inp = np.getInput();
            if (inp == null || !inp.equals(inputMask)) continue;
            outList.add(np);
        }
        return outList;
    }

    public static String getParameterByReference(String parNameOrValue, IWRequest request, Access curAccess) throws Exception {
        if (parNameOrValue.startsWith("@") && parNameOrValue.endsWith("@")) {
            return IWServices.substituteStandardParams(request.getParameter(parNameOrValue.substring(1, parNameOrValue.length() - 1)), request);
        }
        if (parNameOrValue.startsWith("@") && parNameOrValue.indexOf("@", 1) >= 0) {
            return String.valueOf(IWServices.substituteStandardParams(request.getParameter(parNameOrValue.substring(1, parNameOrValue.indexOf("@", 1))), request)) + parNameOrValue.substring(parNameOrValue.indexOf("@", 1) + 1);
        }
        if (parNameOrValue.startsWith("?") && parNameOrValue.endsWith("?")) {
            parNameOrValue = parNameOrValue.substring(1, parNameOrValue.length() - 1);
            if (curAccess == null) {
                return parNameOrValue;
            }
            Values values = curAccess.getValues();
            if (values == null) {
                return parNameOrValue;
            }
            List parameters = values.getParameter();
            for (Parameter param : parameters) {
                if (!param.getInput().trim().equals(parNameOrValue)) continue;
                IWMappingType map = new IWMappingType();
                return map.getData(param, request);
            }
            return parNameOrValue;
        }
        return parNameOrValue;
    }

    public static String MD5(IWRequest iwRequest, String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuffer sb = new StringBuffer();
            byte[] buf = message.getBytes();
            byte[] md5 = md.digest(buf);
            int i = 0;
            while (i < md5.length) {
                String tmpStr = "0" + Integer.toHexString(0xFF & md5[i]);
                sb.append(tmpStr.substring(tmpStr.length() - 2));
                ++i;
            }
            return sb.toString();
        }
        catch (Exception e) {
            iwRequest.lnkIWServices.logError("Error in creating MD5 " + e.getMessage(), LOG_ERRORS, e, null);
            return "";
        }
    }

    public static String replaceParameters(String statement, Access curAccess, IWRequest iwRequest) {
        return IWServices.replaceParameters(statement, curAccess, iwRequest, null);
    }

    public static String replaceParameters(String statement, Access curAccess, IWRequest iwRequest, Datamap dataMap) {
        iwRequest.lnkIWServices.logConsole("Statement in createParams " + statement, LOG_TRACE, iwRequest);
        String retStatement = statement;
        String strData = null;
        StringBuffer buffer = new StringBuffer();
        Values values = curAccess.getValues();
        if (values != null) {
            int ln = statement.length();
            List parameters = values.getParameter();
            int paramCount = 0;
            int paramData = 0;
            int paramTotal = parameters.size();
            if (statement.startsWith("<?")) {
                paramData = statement.indexOf("?>") + 2;
                if (curAccess.getStatementpost().indexOf("%xml_declaration%") >= 0) {
                    buffer.append(statement.substring(0, paramData));
                }
            }
            while (paramData < ln) {
                char ch;
                char chp = paramData == 0 ? (char)'\u0000' : statement.charAt(paramData - 1);
                if ((ch = statement.charAt(paramData++)) == '?' && (Character.isWhitespace(chp) || chp == '\u0000' || chp == '=' || chp == ' ' || chp == '>' || chp == '\"') && paramCount < paramTotal && !((Parameter)((Object)parameters.get(0))).getInput().equals("__%DUMMY%__")) {
                    try {
                        String mc;
                        Parameter param = (Parameter)((Object)parameters.get(paramCount++));
                        Mapping mapping = param.getMapping();
                        boolean quoted = mapping.getQuoted().compareToIgnoreCase("true") == 0;
                        IWMappingType map = new IWMappingType();
                        strData = map.getData(param, iwRequest);
                        String mt = mapping.getType();
                        if (mt != null && mt.equalsIgnoreCase("space_substitute") && (mc = mapping.getContent()) != null) {
                            strData = strData.replaceAll(" ", mc);
                        }
                        if (quoted) {
                            strData = "'" + strData + "'";
                        }
                        buffer.append(strData);
                    }
                    catch (Exception e) {
                        iwRequest.lnkIWServices.logError("Statement in createParams " + statement, LOG_TRACE, e, null);
                    }
                    continue;
                }
                buffer.append(ch);
            }
            retStatement = buffer.toString();
            buffer = null;
        }
        if (dataMap != null) {
            try {
                Access ca = (Access)((Object)dataMap.getAccess().get(0));
                retStatement = IWReplaceString.replace(IWReplaceString.replace(retStatement, "%iw_password%", IWServices.getParameterByReference(dataMap.getPassword(), iwRequest, ca)), "%iw_user%", IWServices.getParameterByReference(dataMap.getUser(), iwRequest, ca));
                retStatement = IWReplaceString.replace(retStatement, "%md5_iw_password%", IWServices.MD5(iwRequest, IWServices.getParameterByReference(dataMap.getPassword(), iwRequest, ca)));
                retStatement = IWReplaceString.replace(retStatement, "%iw_url%", IWServices.getParameterByReference(dataMap.getUrl(), iwRequest, ca));
                retStatement = IWReplaceString.replace(retStatement, "%iw_driver%", IWServices.getParameterByReference(dataMap.getDriver(), iwRequest, ca));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return IWServices.substituteStandardParams(retStatement, iwRequest);
    }

    public static String substituteStandardParams(String inp, IWRequest iwRequest) {
        int fbp;
        int bp;
        String cts;
        String cds;
        long ctm = System.currentTimeMillis();
        String retStatement = inp;
        if (retStatement.indexOf("%current_time%") >= 0) {
            retStatement = retStatement.replaceAll("%current_time%", new Time(ctm).toString());
        }
        if (retStatement.indexOf("%current_date%") >= 0) {
            retStatement = retStatement.replaceAll("%current_date%", new Date(ctm).toString());
        }
        if (retStatement.indexOf("%current_date_us%") >= 0) {
            cds = new Date(ctm).toString();
            retStatement = retStatement.replaceAll("%current_date_us%", String.valueOf(cds.substring(5, 7)) + "/" + cds.substring(8) + "/" + cds.substring(0, 4));
        }
        if (retStatement.indexOf("%current_date_uk%") >= 0) {
            cds = new Date(ctm).toString();
            retStatement = retStatement.replaceAll("%current_date_uk%", String.valueOf(cds.substring(8)) + "/" + cds.substring(5, 7) + "/" + cds.substring(0, 4));
        }
        if (retStatement.indexOf("%current_date_up%") >= 0) {
            cds = new Date(ctm).toString();
            retStatement = retStatement.replaceAll("%current_date_up%", String.valueOf(cds.substring(5, 7)) + cds.substring(8) + cds.substring(0, 4));
        }
        if (retStatement.indexOf("%current_date_p%") >= 0) {
            cds = new Date(ctm).toString();
            retStatement = retStatement.replaceAll("%current_date_p%", String.valueOf(cds.substring(0, 4)) + cds.substring(5, 7) + cds.substring(8));
        }
        if (retStatement.indexOf("%current_timestamp%") >= 0) {
            retStatement = retStatement.replaceAll("%current_timestamp%", new Timestamp(ctm).toString());
        }
        if (retStatement.indexOf("%current_timestamp_s%") >= 0) {
            retStatement = retStatement.replaceAll("%current_timestamp_s%", new Timestamp(ctm).toString().substring(0, 19));
        }
        if (retStatement.indexOf("%current_timestamp_ms%") >= 0) {
            retStatement = retStatement.replaceAll("%current_timestamp_ms%", String.valueOf(ctm));
        }
        if (retStatement.indexOf("%current_timestamp_p%") >= 0) {
            cts = new Timestamp(ctm).toString();
            retStatement = retStatement.replaceAll("%current_timestamp_p%", String.valueOf(cts.substring(0, 4)) + cts.substring(5, 7) + cts.substring(8, 10) + cts.substring(11, 13) + cts.substring(14, 16) + cts.substring(17, 19) + cts.substring(20, 21));
        }
        if (retStatement.indexOf("%current_timestamp_ps%") >= 0) {
            cts = new Timestamp(ctm).toString();
            retStatement = retStatement.replaceAll("%current_timestamp_ps%", String.valueOf(cts.substring(0, 4)) + cts.substring(5, 7) + cts.substring(8, 10) + cts.substring(11, 13) + cts.substring(14, 16) + cts.substring(17, 19));
        }
        if (retStatement.indexOf("%_GUID_%") >= 0) {
            retStatement = retStatement.replaceAll("%_GUID_%", "{" + UUID.randomUUID().toString() + "}");
        }
        if (retStatement.indexOf("%GUID%") >= 0) {
            retStatement = retStatement.replaceAll("%GUID%", UUID.randomUUID().toString());
        }
        int ep = -2;
        do {
            if ((bp = retStatement.indexOf("%C(", ep + 2)) < 0 || (ep = retStatement.indexOf(")%", fbp = bp + 3)) < 0) continue;
            String cValue = retStatement.substring(fbp, ep);
            retStatement = IWReplaceString.replace(retStatement, retStatement.substring(bp, ep + 2), IWServices.compressGUID(cValue));
        } while (bp >= 0);
        if (iwRequest != null) {
            if (retStatement.indexOf("%start_time%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time%", iwRequest.getInitialQueryStartTime().toString().substring(11, 19));
            }
            if (retStatement.indexOf("%start_time_m%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time_m%", iwRequest.getInitialQueryStartTime().toString().substring(11, 19));
                iwRequest.setAddOneMinute(true);
            }
            if (retStatement.indexOf("%start_time_1%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time_1%", new Timestamp(iwRequest.getInitialQueryStartTime().getTime() + 100L).toString().substring(11, 19));
            }
            if (retStatement.indexOf("%start_time_hm%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time_hm%", iwRequest.getInitialQueryStartTime().toString().substring(11, 16));
            }
            if (retStatement.indexOf("%start_time_hm_m%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time_hm_m%", iwRequest.getInitialQueryStartTime().toString().substring(11, 16));
                iwRequest.setAddOneMinute(true);
            }
            if (retStatement.indexOf("%start_time_hm_1%") >= 0) {
                retStatement = retStatement.replaceAll("%start_time_hm_1%", new Timestamp(iwRequest.getInitialQueryStartTime().getTime() + 100L).toString().substring(11, 16));
            }
            if (retStatement.indexOf("%start_date%") >= 0) {
                retStatement = retStatement.replaceAll("%start_date%", iwRequest.getInitialQueryStartTime().toString().substring(0, 10));
            }
            if (retStatement.indexOf("%start_date_us%") >= 0) {
                String cds2 = iwRequest.getInitialQueryStartTime().toString().substring(0, 10);
                retStatement = retStatement.replaceAll("%start_date_us%", String.valueOf(cds2.substring(5, 7)) + "/" + cds2.substring(8) + "/" + cds2.substring(0, 4));
            }
            if (retStatement.indexOf("%start_date_up%") >= 0) {
                String cds3 = iwRequest.getInitialQueryStartTime().toString().substring(0, 10);
                retStatement = retStatement.replaceAll("%start_date_up%", String.valueOf(cds3.substring(5, 7)) + cds3.substring(8) + cds3.substring(0, 4));
            }
            if (retStatement.indexOf("%start_timestamp%") >= 0) {
                retStatement = retStatement.replaceAll("%start_timestamp%", iwRequest.getInitialQueryStartTime().toString().substring(0, 19));
            }
            if (retStatement.indexOf("%start_timestamp_1%") >= 0) {
                retStatement = retStatement.replaceAll("%start_timestamp_1%", new Timestamp(iwRequest.getInitialQueryStartTime().getTime() + 1L).toString().substring(0, 19));
            }
            if (retStatement.indexOf("%start_timestamp_z%") >= 0) {
                Calendar cln = Calendar.getInstance();
                cln.setTimeInMillis(Timestamp.valueOf(iwRequest.getInitialQueryStartTime().toString().substring(0, 19)).getTime());
                cln.add(11, -iwRequest.getTimeZoneShift());
                retStatement = retStatement.replaceAll("%start_timestamp_z%", new Timestamp(cln.getTimeInMillis()).toString().substring(0, 19));
            }
            if (retStatement.indexOf("%return_string%") >= 0) {
                String rs = iwRequest.getInitialReturnString();
                if (rs == null || rs.equalsIgnoreCase("null")) {
                    rs = "";
                }
                retStatement = retStatement.replaceAll("%return_string%", rs);
            }
            if (retStatement.indexOf("%connection_failures%") >= 0) {
                retStatement = retStatement.replaceAll("%connection_failures%", new Integer(iwRequest.getInitialConnectionFailures()).toString());
            }
            if (retStatement.indexOf("%loop_index%") >= 0) {
                retStatement = retStatement.replaceAll("%loop_index%", String.valueOf(iwRequest.getLoopIndex()));
            }
            if (retStatement.indexOf("%loop_index_1%") >= 0) {
                retStatement = retStatement.replaceAll("%loop_index_1%", String.valueOf(iwRequest.getLoopIndex() + 1));
            }
            if (retStatement.indexOf("%return_current_timestamp%") >= 0) {
                iwRequest.setQueryStartTime(new Timestamp(System.currentTimeMillis()));
                retStatement = retStatement.replaceAll("%return_current_timestamp%", "");
            }
            if (retStatement.indexOf("%stop_scheduler%") >= 0) {
                iwRequest.setStopSchedule(1);
                retStatement = retStatement.replaceAll("%stop_scheduler%", "");
            }
            if (retStatement.indexOf("%stop_cycle_scheduler%") >= 0) {
                iwRequest.setStopCycleSchedule(1);
                retStatement = retStatement.replaceAll("%stop_cycle_scheduler%", "");
            }
            if (retStatement.indexOf("%session_id%") >= 0) {
                retStatement = retStatement.replaceAll("%session_id%", iwRequest.getCurrentSessionId());
            }
            if ((bp = retStatement.indexOf("%skip_to_flow=")) >= 0 && (ep = retStatement.indexOf("%", fbp = bp + 14)) >= 0) {
                iwRequest.setReturnString(retStatement.substring(fbp, ep));
                iwRequest.setStopSchedule(-1);
                retStatement = retStatement.replaceAll(retStatement.substring(bp, ep + 1), "");
            }
            if ((bp = retStatement.indexOf("%pause=")) >= 0 && (ep = retStatement.indexOf("%", fbp = bp + 7)) >= 0) {
                long si = -1L;
                try {
                    si = Long.valueOf(retStatement.substring(fbp, ep));
                }
                catch (NumberFormatException e1) {
                    iwRequest.lnkIWServices.logError("Incorrect sleep format " + si, LOG_REQUEST, e1, null);
                }
                if (si > 0L) {
                    try {
                        Thread.sleep(si);
                    }
                    catch (InterruptedException e) {
                        iwRequest.lnkIWServices.logError("Sleep " + si + " interrupted", LOG_REQUEST, e, null);
                    }
                }
                retStatement = retStatement.replaceAll(retStatement.substring(bp, ep + 1), "");
            }
            ep = -2;
            do {
                if ((bp = retStatement.indexOf("%P(", ep + 2)) < 0 || (ep = retStatement.indexOf(")%", fbp = bp + 3)) < 0) continue;
                String pName = retStatement.substring(fbp, ep);
                String pValue = null;
                try {
                    pValue = iwRequest.getParameter(pName);
                }
                catch (Exception e) {
                    iwRequest.lnkIWServices.logError("Parameter " + pName + " is not defined", LOG_TRACE, e, null);
                }
                if (pValue == null) {
                    pValue = "";
                }
                retStatement = IWReplaceString.replace(retStatement, retStatement.substring(bp, ep + 2), pValue);
            } while (bp >= 0);
        }
        return retStatement;
    }

    static String compressGUID(String fullGUID) {
        return fullGUID;
    }

    public static void filterRows(String filter, IWRequest request, Access curAccess) {
        System.out.println("IncomingFilterValue =" + filter + "|");
        String trBuf = request.tranBuffer.toString();
        StringBuffer outBuf = new StringBuffer();
        int inpos = 0;
        int cpos = 0;
        while ((cpos = trBuf.indexOf("<row number=", inpos)) >= 0) {
            int spos = trBuf.indexOf(">", cpos);
            if (spos < 0) break;
            outBuf.append(trBuf.substring(inpos, cpos));
            int rpos = trBuf.indexOf("</row>", cpos);
            if (rpos < 0) break;
            inpos = rpos + 6;
            if (IWServices.getFilterValue(curAccess, trBuf.substring(spos + 1, rpos)).equals(filter)) continue;
            outBuf.append(trBuf.substring(cpos, inpos));
        }
        outBuf.append(trBuf.substring(inpos));
        request.tranBuffer = outBuf;
    }

    public static String getFilterValue(Access curAccess, String row) {
        StringBuffer outBuf = new StringBuffer();
        int ni = 0;
        for (Parameter np : curAccess.getValues().getParameter()) {
            int ei;
            int li;
            String is;
            String filterName;
            Mapping mp = np.getMapping();
            String type = mp.getType();
            if (type == null || !type.equalsIgnoreCase("filter")) continue;
            String prefix = np.getInput();
            if (prefix != null) {
                outBuf.append(prefix.substring(1, prefix.length() - 1));
            }
            if ((filterName = mp.getContent()) == null || (ni = row.indexOf(is = "name=\"" + filterName + "\">")) < 0 || (li = row.indexOf("</col>", ei = ni + is.length())) < 0) continue;
            outBuf.append(row.substring(ei, li));
        }
        return outBuf.toString().trim();
    }

    public static Integer[] analyzeParameters(Access curAccess, ArrayList<String> returnName, StringBuffer replaceQuoteName, StringBuffer replaceQuote, StringBuffer replace2QuoteName, StringBuffer replace2Quote) {
        if (curAccess == null) {
            return null;
        }
        ArrayList<Integer> retFormat = new ArrayList<Integer>();
        Values vl = curAccess.getValues();
        if (vl == null) {
            return null;
        }
        List ptrs = vl.getParameter();
        if (ptrs == null) {
            return null;
        }
        for (Parameter np : ptrs) {
            Mapping mp = np.getMapping();
            String type = mp.getType();
            if (type == null) continue;
            String cnt = mp.getContent();
            if (type.equalsIgnoreCase("return")) {
                returnName.add(np.getInput());
                if (cnt != null) {
                    int sfi = IWServices.retIndex(cnt);
                    if (cnt.equalsIgnoreCase("ms")) {
                        retFormat.add(new Integer(1));
                        continue;
                    }
                    if (cnt.startsWith("ms_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 1));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("s")) {
                        retFormat.add(new Integer(2));
                        continue;
                    }
                    if (cnt.startsWith("s_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 2));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("z")) {
                        retFormat.add(new Integer(3));
                        continue;
                    }
                    if (cnt.startsWith("z_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 3));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("dpu")) {
                        retFormat.add(new Integer(4));
                        continue;
                    }
                    if (cnt.startsWith("dpu_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 4));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("tpu")) {
                        retFormat.add(new Integer(5));
                        continue;
                    }
                    if (cnt.startsWith("tpu_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 5));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("dou")) {
                        retFormat.add(new Integer(6));
                        continue;
                    }
                    if (cnt.startsWith("dou_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 6));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("do")) {
                        retFormat.add(new Integer(7));
                        continue;
                    }
                    if (cnt.startsWith("do_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 7));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("nvp")) {
                        retFormat.add(new Integer(8));
                        continue;
                    }
                    if (cnt.startsWith("nvp_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 8));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("is")) {
                        retFormat.add(new Integer(9));
                        continue;
                    }
                    if (cnt.startsWith("is_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 8));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("p")) {
                        retFormat.add(new Integer(10));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("t")) {
                        retFormat.add(new Integer(11));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("q")) {
                        retFormat.add(new Integer(12));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("r")) {
                        retFormat.add(new Integer(13));
                        continue;
                    }
                    if (cnt.startsWith("r_") && sfi >= 0) {
                        retFormat.add(new Integer(1000 * (sfi + 1) + 13));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("fn")) {
                        retFormat.add(new Integer(14));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("ln")) {
                        retFormat.add(new Integer(15));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("fc")) {
                        retFormat.add(new Integer(16));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("ft")) {
                        retFormat.add(new Integer(17));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("fe")) {
                        retFormat.add(new Integer(18));
                        continue;
                    }
                    if (cnt.equalsIgnoreCase("cc")) {
                        retFormat.add(new Integer(19));
                        continue;
                    }
                    retFormat.add(new Integer(0));
                    continue;
                }
                retFormat.add(new Integer(0));
                continue;
            }
            if (type.equalsIgnoreCase("replaceQuote")) {
                replaceQuoteName.append(np.getInput());
                replaceQuote.append(cnt);
                continue;
            }
            if (!type.equalsIgnoreCase("replaceDoubleQuote")) continue;
            replace2QuoteName.append(np.getInput());
            replace2Quote.append(cnt);
        }
        return retFormat.toArray(new Integer[0]);
    }

    public static int retIndex(String value) {
        int ui = value.indexOf("_");
        if (ui < 0) {
            return -1;
        }
        try {
            int ret = Integer.valueOf(value.substring(ui + 1));
            if (ret >= 0 && ret < 10) {
                return ret;
            }
            return -1;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Vector<String> getInitEmailIndex() {
        String[] iniEIR = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        String[] iniEIO = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        String[] iniEI = emailHost.equals("Rackspace") ? iniEIR : iniEIO;
        Vector<String> initEmailIndex = new Vector<String>();
        int ie = 0;
        while (ie < iniEI.length) {
            initEmailIndex.add(iniEI[ie]);
            ++ie;
        }
        return initEmailIndex;
    }

    public static String getEmailSuffix(IWRequest iwRequest) {
        if (iwRequest == null) {
            return "01";
        }
        int emisz = iwRequest.getEmailIndex().size();
        if (emisz == 1) {
            String eis = iwRequest.getEmailIndex().get(0);
            Vector<String> nei = IWServices.getInitEmailIndex();
            nei.remove(eis);
            iwRequest.setEmailIndex(nei);
            return eis;
        }
        Random rn = new Random();
        int emind = rn.nextInt(emisz);
        String eisr = iwRequest.getEmailIndex().get(emind);
        iwRequest.getEmailIndex().remove(emind);
        return eisr;
    }

    public static String processParameters(IWRequest iwRequest, String strData, String colName, String[] returnName, String replaceQuoteName, String replaceQuote, String replace2QuoteName, String replace2Quote, Integer[] retFormat) throws Exception {
        if (iwRequest == null) {
            return strData;
        }
        iwRequest.lnkIWServices.logConsole("Parameter for " + colName + " is being processed: " + strData, LOG_TRACE, iwRequest);
        String retStr = strData;
        if (replaceQuoteName != null && (replaceQuoteName.equals("*") || replaceQuoteName.equalsIgnoreCase(colName))) {
            if (replaceQuote == null) {
                replaceQuote = "";
            }
            String ui = "__%UNESCAPE%__";
            int uil = ui.length();
            if (replaceQuote.toUpperCase().startsWith(ui)) {
                retStr = retStr.replaceAll("&#039;", "'");
                int rql = replaceQuote.length();
                if (rql > uil) {
                    String rq = replaceQuote.substring(uil);
                    retStr = retStr.replaceAll("'", rq);
                }
            } else {
                retStr = retStr.replaceAll("'", replaceQuote);
            }
            iwRequest.lnkIWServices.logConsole("Data after replacement " + colName + " with " + replaceQuote + " processed. Value = " + retStr, LOG_TRACE, iwRequest);
        }
        if (replace2QuoteName != null && (replace2QuoteName.equals("*") || replace2QuoteName.equalsIgnoreCase(colName) || replace2QuoteName.startsWith("^") && (replace2QuoteName.substring(1).equals("*") || replace2QuoteName.substring(1).equalsIgnoreCase(colName)))) {
            if (replace2Quote == null) {
                replace2Quote = "";
            }
            retStr = replace2QuoteName.startsWith("^") ? retStr.replaceAll("\n", replace2Quote) : retStr.replaceAll("\"", replace2Quote);
            iwRequest.lnkIWServices.logConsole("Data after replacement 2 " + colName + " with " + replace2Quote + " processed. Value = " + retStr, LOG_TRACE, iwRequest);
        }
        if (returnName != null) {
            int i = 0;
            while (i < returnName.length) {
                String rtn = returnName[i];
                if (rtn != null) {
                    int fr = retFormat[i];
                    int rfi = -1;
                    if (fr > 1000) {
                        rfi = fr / 1000;
                        fr -= rfi * 1000;
                        --rfi;
                    }
                    int nvpi = rtn.indexOf(":");
                    String nvpfn = null;
                    if (nvpi > 0 && fr == 8) {
                        nvpfn = rtn.substring(nvpi + 1).trim();
                        rtn = rtn.substring(0, nvpi);
                    }
                    if (fr != 8 && rtn.equalsIgnoreCase(colName) && strData != null && strData.trim().length() > 0 || fr == 8 && strData != null && strData.trim().equals(nvpfn)) {
                        iwRequest.lnkIWServices.logConsole("Raw Data to return " + colName + " processed. Value = " + strData, LOG_TRACE, iwRequest);
                        Timestamp cts = null;
                        switch (fr) {
                            case 0: {
                                cts = Timestamp.valueOf(strData);
                                iwRequest.lnkIWServices.logConsole("New data of 0 type returned = " + cts.toString(), LOG_TRACE, iwRequest);
                                break;
                            }
                            case 1: {
                                cts = new Timestamp(Long.valueOf(strData) + 1L);
                                break;
                            }
                            case 2: {
                                cts = new Timestamp(1000L * Long.valueOf(strData) + 1000L);
                                break;
                            }
                            case 3: {
                                if (strData.endsWith("Z")) {
                                    cts = Timestamp.valueOf(strData.replaceAll("T", " ").substring(0, strData.length() - 1));
                                    break;
                                }
                                int lastDash = strData.lastIndexOf("-");
                                if (lastDash > 11) {
                                    cts = Timestamp.valueOf(strData.replaceAll("T", " ").substring(0, lastDash));
                                    break;
                                }
                                if (lastDash <= 0) break;
                                cts = Timestamp.valueOf(strData.replaceAll("T", " "));
                                break;
                            }
                            case 4: {
                                String td;
                                String[] udt = strData.split("/");
                                if (udt.length != 3) break;
                                String cd = String.valueOf(udt[2]) + "-" + udt[0] + "-" + udt[1] + " ";
                                if (rfi < 0) {
                                    td = iwRequest.getTimePart();
                                    if (td != null) {
                                        cts = Timestamp.valueOf(String.valueOf(cd) + td);
                                        iwRequest.setReturnDateTime(null);
                                        iwRequest.setTimePart(null);
                                        break;
                                    }
                                    iwRequest.setReturnDateTime(Timestamp.valueOf(String.valueOf(cd) + "00:00:00.0"));
                                    break;
                                }
                                td = iwRequest.getOtherTimePart()[rfi];
                                if (td != null) {
                                    cts = Timestamp.valueOf(String.valueOf(cd) + td);
                                    iwRequest.getOtherReturnDateTime()[rfi] = null;
                                    iwRequest.getOtherTimePart()[rfi] = null;
                                    break;
                                }
                                iwRequest.getOtherReturnDateTime()[rfi] = Timestamp.valueOf(String.valueOf(cd) + "00:00:00.0");
                                break;
                            }
                            case 5: {
                                StringBuffer nt;
                                int bp;
                                String rt;
                                Timestamp rdt;
                                if (rfi < 0) {
                                    rdt = iwRequest.getReturnDateTime();
                                    if (rdt == null) {
                                        iwRequest.setTimePart(strData);
                                        break;
                                    }
                                    rt = rdt.toString();
                                    bp = rt.indexOf(" ");
                                    if (bp > 0) {
                                        nt = new StringBuffer("00:00:00.0");
                                        nt.replace(0, strData.trim().length(), strData.trim());
                                        cts = Timestamp.valueOf(String.valueOf(rt.substring(0, bp + 1)) + nt.toString());
                                        iwRequest.setTimePart(null);
                                    } else {
                                        iwRequest.setTimePart(strData);
                                    }
                                    iwRequest.setReturnDateTime(null);
                                    break;
                                }
                                rdt = iwRequest.getOtherReturnDateTime()[rfi];
                                if (rdt == null) {
                                    iwRequest.getOtherTimePart()[rfi] = strData;
                                    break;
                                }
                                rt = rdt.toString();
                                bp = rt.indexOf(" ");
                                if (bp > 0) {
                                    nt = new StringBuffer("00:00:00.0");
                                    nt.replace(0, strData.trim().length(), strData.trim());
                                    cts = Timestamp.valueOf(String.valueOf(rt.substring(0, bp + 1)) + nt.toString());
                                    iwRequest.getOtherTimePart()[rfi] = null;
                                } else {
                                    iwRequest.getOtherTimePart()[rfi] = strData;
                                }
                                iwRequest.getOtherReturnDateTime()[rfi] = null;
                                break;
                            }
                            case 6: {
                                String[] udo = strData.split("/");
                                cts = Timestamp.valueOf(String.valueOf(udo[2]) + "-" + udo[0] + "-" + udo[1] + " 00:00:00.0");
                                break;
                            }
                            case 7: {
                                cts = Timestamp.valueOf(String.valueOf(strData) + " 00:00:00.0");
                                break;
                            }
                            case 8: {
                                cts = new Timestamp(System.currentTimeMillis());
                                break;
                            }
                            case 9: {
                                cts = new Timestamp(Timestamp.valueOf(strData).getTime() + 1000L);
                                break;
                            }
                            case 10: {
                                iwRequest.setPasswordHash(retStr);
                                break;
                            }
                            case 11: {
                                iwRequest.setTransactionFlowIdList(retStr);
                                break;
                            }
                            case 12: {
                                iwRequest.setQueryIdList(retStr);
                                break;
                            }
                            case 13: {
                                if (rfi < 0) {
                                    iwRequest.setReturnString(retStr);
                                    break;
                                }
                                iwRequest.getOtherReturnString()[rfi] = retStr;
                                break;
                            }
                            case 14: {
                                iwRequest.setFirstName(retStr);
                                break;
                            }
                            case 15: {
                                iwRequest.setLastName(retStr);
                                break;
                            }
                            case 16: {
                                iwRequest.setCompany(retStr);
                                break;
                            }
                            case 17: {
                                iwRequest.setTitle(retStr);
                                break;
                            }
                            case 18: {
                                iwRequest.setEmail(retStr);
                                break;
                            }
                            case 19: {
                                iwRequest.setCompanyConfiguration(retStr);
                                break;
                            }
                            default: {
                                return retStr;
                            }
                        }
                        if (fr < 10) {
                            if (rfi < 0) {
                                Timestamp bts = iwRequest.getQueryStartTime();
                                if (bts != null && cts != null && cts.compareTo(bts) > 0) {
                                    iwRequest.setQueryStartTime(cts);
                                    iwRequest.lnkIWServices.logConsole("New QST is set = " + iwRequest.getQueryStartTime().toString(), LOG_TRACE, iwRequest);
                                }
                            } else {
                                Timestamp bts = iwRequest.getOtherQueryStartTime()[rfi];
                                if (bts != null && cts != null && cts.compareTo(bts) > 0) {
                                    iwRequest.getOtherQueryStartTime()[rfi] = cts;
                                    iwRequest.lnkIWServices.logConsole("New QST is set for rfi= " + rfi + " " + iwRequest.getOtherQueryStartTime()[rfi].toString(), LOG_TRACE, iwRequest);
                                }
                            }
                        }
                    }
                }
                ++i;
            }
        }
        return retStr;
    }

    public static String[] splitXML(String ixml) {
        ArrayList<String> res = new ArrayList<String>();
        String ixml0 = ixml.trim();
        String xml0 = ixml0.endsWith(">`") ? ixml0.substring(0, ixml0.length() - 1) : ixml0;
        String xml = xml0.startsWith("`<") ? xml0.substring(1, xml0.length()) : xml0;
        int bqp = 0;
        while (true) {
            int bqpn;
            if ((bqpn = xml.indexOf("`", bqp)) < 0) {
                res.add(xml.substring(bqp));
                break;
            }
            boolean wasBQ = false;
            do {
                if (xml.charAt(bqpn - 1) == '>' || xml.length() > bqpn + 2 && xml.charAt(bqpn + 1) == '<' && xml.charAt(bqpn + 2) != '/') {
                    if (wasBQ) {
                        res.add(xml.substring(bqp, bqpn).replaceAll("`", ""));
                        wasBQ = false;
                    } else {
                        res.add(xml.substring(bqp, bqpn));
                    }
                    break;
                }
                wasBQ = true;
            } while ((bqpn = xml.indexOf("`", bqpn + 1)) >= 0);
            if (bqpn < 0) {
                if (wasBQ) {
                    res.add(xml.substring(bqp).replaceAll("`", ""));
                    wasBQ = false;
                    break;
                }
                res.add(xml.substring(bqp));
                break;
            }
            bqp = bqpn + 1;
        }
        return res.toArray(new String[0]);
    }

    private static String calendar2Date(Calendar calendar) {
        int mn = calendar.get(2) + 1;
        int dy = calendar.get(5);
        return String.valueOf(String.valueOf(calendar.get(1))) + (mn < 10 ? "0" : "") + String.valueOf(mn) + (dy < 10 ? "0" : "") + String.valueOf(dy);
    }

    private static String calendar2Daten(Calendar calendar) {
        int mn = calendar.get(2) + 1;
        int dy = calendar.get(5);
        return String.valueOf(String.valueOf(calendar.get(1))) + "-" + (mn < 10 ? "0" : "") + String.valueOf(mn) + "-" + (dy < 10 ? "0" : "") + String.valueOf(dy);
    }

    public static String[] getLogFile(String profile, String flow) {
        ArrayList<String> log = new ArrayList<String>();
        if (flow != null && flow.trim().length() > 0) {
            String os = System.getProperty("os.name");
            File inf = null;
            boolean fileExists = false;
            if (os.indexOf("Windows") >= 0) {
                Calendar cln = Calendar.getInstance();
                int cd = cln.get(6);
                inf = new File("logs\\stdout_" + IWServices.calendar2Date(cln) + ".log");
                try {
                    fileExists = inf.exists();
                }
                catch (SecurityException e) {
                    String errMessage = "Security Exception " + e.getMessage() + " in access to Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow;
                    log.add(errMessage);
                    return log.toArray(new String[0]);
                }
                if (!fileExists) {
                    inf = new File("logs\\tomcat5-stdout." + IWServices.calendar2Daten(cln) + ".log");
                }
                try {
                    fileExists = inf.exists();
                }
                catch (SecurityException e) {
                    String errMessage = "Security Exception " + e.getMessage() + " in access to Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow;
                    log.add(errMessage);
                    return log.toArray(new String[0]);
                }
                if (!fileExists) {
                    String warnMessage = "No today's Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow + ". Looking for previous dates.";
                    log.add(warnMessage);
                    int i = 0;
                    while (i < cd) {
                        cln.set(6, cln.get(6) - 1);
                        inf = new File("logs\\stdout_" + IWServices.calendar2Date(cln) + ".log");
                        try {
                            fileExists = inf.exists();
                        }
                        catch (SecurityException e) {
                            String errMessage = "Security Exception " + e.getMessage() + " in access to Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow;
                            log.add(errMessage);
                            return log.toArray(new String[0]);
                        }
                        if (fileExists) break;
                        inf = new File("logs\\tomcat5-stdout." + IWServices.calendar2Daten(cln) + ".log");
                        try {
                            fileExists = inf.exists();
                        }
                        catch (SecurityException e) {
                            String errMessage = "Security Exception " + e.getMessage() + " in access to Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow;
                            log.add(errMessage);
                            return log.toArray(new String[0]);
                        }
                        if (fileExists) break;
                        ++i;
                    }
                    if (i == cd) {
                        String errMessage = "No Log File (" + inf.getAbsolutePath() + " in " + new File(".").getAbsolutePath() + ") found in Windows folder for " + profile + "; Flow: " + flow;
                        log.add(errMessage);
                        return log.toArray(new String[0]);
                    }
                }
            } else {
                inf = new File("../logs/catalina.out");
            }
            if (inf == null && (inf = new File("/var/log/tomcat5/catalina.out")) == null) {
                String errMessage = "No Log File Found for " + profile + "; Flow: " + flow;
                log.add(errMessage);
                return log.toArray(new String[0]);
            }
            FileReader fr = null;
            try {
                fr = new FileReader(inf);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                log.add(e.getMessage());
                return log.toArray(new String[0]);
            }
            BufferedReader br = new BufferedReader(fr);
            String is = "";
            try {
                boolean getLine = false;
                while ((is = br.readLine()) != null) {
                    if (profile != null && profile.trim().length() != 0) {
                        if (is.indexOf("IW 2.4 TS") >= 0 || is.indexOf("IW 2.41 TS") >= 0) {
                            getLine = is.indexOf(profile) >= 0 && is.indexOf(flow) >= 0;
                        }
                    } else if (is.indexOf("IW 2.4 TS") >= 0 || is.indexOf("IW 2.41 TS") >= 0) {
                        getLine = true;
                    }
                    if (!getLine || profile != null && profile.trim().length() != 0 && (is.indexOf("%sups%") >= 0 || is.indexOf("__su_admin__") >= 0 || is.indexOf("%iwps%") >= 0 || is.indexOf("__iw_admin__") >= 0 || is.indexOf("$#sprt#$") >= 0 || is.indexOf("__iw_support__") >= 0 || is.indexOf("IWBusinessDaemon") >= 0)) continue;
                    log.add(is);
                }
                String footer = "Log File " + log.size() + " lines for " + profile + "; Flow: " + flow;
                log.add(footer);
            }
            catch (IOException e) {
                e.printStackTrace();
                log.add(e.getMessage());
            }
        }
        return log.toArray(new String[0]);
    }

    public final String getConfigName() {
        return this.configName;
    }

    public final void setConfigName(String configName) {
        this.configName = configName;
    }

    public final boolean isPrimary() {
        return this.primary;
    }

    public final void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public final boolean isShowTime() {
        return this.showTime;
    }

    public final void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public static String[] readLineContext(String fileContext) throws IOException {
        StringReader srd = new StringReader(fileContext);
        BufferedReader br = new BufferedReader(srd);
        ArrayList<String> sb = new ArrayList<String>();
        String is = "";
        while ((is = br.readLine()) != null) {
            sb.add(is);
        }
        return sb.toArray(new String[0]);
    }

    public static boolean isHosted() {
        return hosted;
    }

    public final String getEmailNoteDNS() {
        return this.emailNoteDNS;
    }

    public final void setEmailNoteDNS(String emailNoteDNS) {
        this.emailNoteDNS = emailNoteDNS;
    }

    public final int getEmailNotePort() {
        return this.emailNotePort;
    }

    public final void setEmailNotePort(int emailNotePort) {
        this.emailNotePort = emailNotePort;
    }

    public final String getEmailNotePwd(String user) {
        if (user.toLowerCase().indexOf("system") >= 0) {
            return this.emailNotePwdSys;
        }
        return this.emailNotePwd;
    }

    public final String getEmailNoteURL() {
        return this.emailNoteURL;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public static String getEmailNoteName() {
        return emailNoteName;
    }
}

