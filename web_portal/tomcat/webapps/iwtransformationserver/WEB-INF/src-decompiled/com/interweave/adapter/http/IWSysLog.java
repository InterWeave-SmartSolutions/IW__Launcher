/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.PrintfFormat;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class IWSysLog
implements IWIDataMap {
    private Access curAccess = null;
    private static String ipAddress = "localhost";
    private String identification = "";
    private String logOptions = "";
    private String severity = "LOG_INFO";
    private String format = "%s";
    private String message = "";
    private boolean logOpened = false;
    private boolean sendOkay = false;
    private InetAddress in;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public void closeConnection() {
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        String strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        this.curAccess = null;
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                Vector<String> params = new Vector<String>();
                Iterator iterate = values.getParameter().iterator();
                while (iterate.hasNext()) {
                    String strName;
                    strData = null;
                    Parameter param = (Parameter)((Object)iterate.next());
                    Mapping mapping = param.getMapping();
                    String type = mapping.getType();
                    IWMappingType map = new IWMappingType();
                    strData = map.getData(param, request);
                    if (strData == null) {
                        strData = "";
                    }
                    if ((strName = mapping.getContent()).compareToIgnoreCase("ipAddress") == 0) {
                        ipAddress = strData;
                        continue;
                    }
                    if (strName.compareToIgnoreCase("identification") == 0) {
                        this.identification = strData;
                        continue;
                    }
                    if (strName.compareToIgnoreCase("logOptions") == 0) {
                        this.logOptions = strData;
                        continue;
                    }
                    if (strName.compareToIgnoreCase("severity") == 0) {
                        this.severity = strData;
                        continue;
                    }
                    if (strName.compareToIgnoreCase("format") == 0) {
                        this.format = strData;
                        continue;
                    }
                    System.out.println("Sprintf " + strName + ":" + strData);
                    params.add(strData);
                }
                PrintfFormat outFmt = new PrintfFormat(this.format);
                this.message = outFmt.sprintf(params.toArray());
                this.openLog(this.identification, this.logOptions);
                boolean res = this.isOpen();
                System.out.println("Log file open is " + res);
                try {
                    this.sysLog(this.severity, this.message);
                }
                catch (IOException e) {
                    System.out.println("Exception is " + e.getMessage());
                }
                this.closeLog();
            }
        }
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    private String setLocalHostAddress() {
        try {
            this.in = InetAddress.getLocalHost();
        }
        catch (UnknownHostException unknownHostException) {
            // empty catch block
        }
        String inp = this.in.getHostAddress();
        return inp;
    }

    public boolean isOpen() {
        return this.logOpened;
    }

    public void setSysLogger() {
        ipAddress = this.setLocalHostAddress();
    }

    public void setSysLogger(String ip) {
        ipAddress = ip;
    }

    public String showSysLogger() {
        return ipAddress;
    }

    public void openLog() {
        System.out.println("No identification used");
        this.logOptions = "LOG_IP";
        this.identification = "InterWeave";
        this.logOpened = true;
    }

    public void openLog(String ident, String logopt) {
        if (ident == null) {
            System.out.println("No identification used");
        }
        this.identification = ident;
        System.out.println("Identification used will be " + this.identification);
        if (logopt == null) {
            this.logOptions = "LOG_IP";
        }
        if (logopt != "LOG_IP" || logopt == "LOG_CONS" || logopt == "LOG_ODELAY" || logopt == "LOG_NDELAY" || logopt == "LOG_NOWAIT") {
            System.out.println("Invalid Logging Option" + logopt + ", " + "Will use LOP_IP as default");
            this.logOptions = "LOG_IP";
        }
        if (logopt == "LOG_IP") {
            System.out.println("Logging Option is " + logopt);
            this.logOptions = "LOG_IP";
        }
        if (logopt == "LOG_CONS") {
            System.out.println("Logging Option is " + logopt);
            this.logOptions = "LOG_CONS";
        }
        if (logopt == "LOG_ODELAY") {
            System.out.println("Logging Option is " + logopt);
            this.logOptions = "LOG_ODELAY";
        }
        if (logopt == "LOG_NDELAY") {
            System.out.println("Logging Option is " + logopt);
            this.logOptions = "LOG_NDELAY";
        }
        if (logopt == "LOG_NOWAIT") {
            System.out.println("Logging Option is " + logopt);
            this.logOptions = "LOG_NOWAIT";
        }
        this.logOpened = true;
    }

    public void closeLog() {
        this.identification = "";
        this.logOptions = "";
        this.logOpened = false;
    }

    public void sysLog(String sev, String msg) throws Exception {
        if (!this.logOpened) {
            throw new Exception("Log file is not open");
        }
        if (msg == null) {
            this.message = "";
            this.severity = "LOG_INFO";
        }
        if (sev == "LOG_EMERG") {
            this.message = msg;
            this.severity = "LOG_EMERG";
        }
        if (sev == "LOG_ALERT") {
            this.message = msg;
            this.severity = "LOG_ALERT";
        }
        if (sev == "LOG_CRIT") {
            this.message = msg;
            this.severity = "LOG_CRIT";
        }
        if (sev == "LOG_ERR") {
            this.message = msg;
            this.severity = "LOG_ERR";
        }
        if (sev == "LOG_WARNING") {
            this.message = msg;
            this.severity = "LOG_WARNING";
        }
        if (sev == "LOG_NOTICE") {
            this.message = msg;
            this.severity = "LOG_NOTICE";
        }
        if (sev == "LOG_INFO") {
            this.message = msg;
            this.severity = "LOG_INFO";
        }
        System.out.println("Severity and message is " + this.severity + this.message);
        this.sendOkay = true;
        this.sendMessage(this.identification, this.severity, this.message);
    }

    private void sendMessage(String identity, String logSev, String logMsg) throws Exception {
        this.sendOkay = false;
        if (false) {
            throw new Exception("Log file is not open");
        }
        try {
            System.out.println("Inside sendMessage");
            DatagramSocket soc = new DatagramSocket();
            String msgA = "<3>" + identity + ":  " + logSev + ":  " + logMsg;
            System.out.println("Message about to be logged is " + msgA);
            byte[] msg = msgA.getBytes();
            System.out.println("msg length is " + msg.length);
            System.out.println("ipAddress is " + ipAddress);
            DatagramPacket p = new DatagramPacket(msg, msg.length, InetAddress.getByName(ipAddress), 514);
            soc.send(p);
            System.out.println("Send successful");
            soc.close();
        }
        catch (Exception e) {
            System.out.println("Error sending message to syslogd");
            e.getMessage();
            System.exit(1);
        }
    }

    public String getDriver() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUser() {
        return null;
    }
}

