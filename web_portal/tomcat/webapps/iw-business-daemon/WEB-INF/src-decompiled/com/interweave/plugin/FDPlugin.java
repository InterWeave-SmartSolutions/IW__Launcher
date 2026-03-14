/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import lp.samples.SALE_MININFO;

public class FDPlugin {
    public static String storeid;
    public static String certPath;
    public static String userid;
    public static String userpwd;
    public static String hostname;
    public static String ordermode;
    public static int port;
    public static int defaultPort;
    private static int logLevel;
    private static int LOG_ERRORS;
    private static int LOG_MINIMUM;
    private static int LOG_TRACE;
    private static boolean timestampLog;

    static {
        defaultPort = 1129;
        logLevel = -2;
        LOG_ERRORS = -2;
        LOG_MINIMUM = 1;
        LOG_TRACE = 100;
        timestampLog = false;
    }

    public static void setLogLevel(Integer logLevel, Boolean timestampLog) {
        FDPlugin.logLevel = logLevel;
        FDPlugin.timestampLog = timestampLog;
    }

    public static void setConnection(String URL2, String context, String user, String password) {
        FDPlugin.logString("FDPlugin Plug-in version 1.1 build 007", LOG_ERRORS);
        try {
            URL cu = new URL(URL2);
            hostname = cu.getHost();
            if (hostname.startsWith("[") && hostname.endsWith("]")) {
                hostname = hostname.substring(1, hostname.length() - 1);
            }
            if ((port = cu.getPort()) == -1) {
                port = defaultPort;
            }
        }
        catch (MalformedURLException e) {
            FDPlugin.logException("AccpacERP0: Malformed URL " + URL2 + "; ", e);
        }
        int mi = context.lastIndexOf(":");
        String fcontext = null;
        if (mi < 0) {
            fcontext = context;
            ordermode = "LIVE";
        } else {
            fcontext = context.substring(0, mi);
            ordermode = context.substring(mi + 1);
        }
        File p12File = new File(fcontext);
        String fn = p12File.getName();
        int pp = fn.indexOf(".");
        storeid = pp > 0 ? fn.substring(0, pp) : fn;
        certPath = fcontext;
        userid = user;
        userpwd = password;
        FDPlugin.logString("FDPLugin setConnection: host '" + hostname + "'");
        FDPlugin.logString("FDPLugin setConnection: port '" + port + "'");
        FDPlugin.logString("FDPLugin setConnection: user '" + userid + "'");
        FDPlugin.logString("FDPLugin setConnection: password '" + userpwd + "'");
        FDPlugin.logString("FDPLugin setConnection: storeid '" + storeid + "'");
        FDPlugin.logString("FDPLugin setConnection: certPath '" + certPath + "'");
        FDPlugin.logString("FDPLugin setConnection: ordermode '" + ordermode + "'");
    }

    public static String processCCSaleMin(String addrnum, String zip, String cardnumber, String cardexpmonth, String cardexpyear, String chargetotal, String cvmvalue) {
        SALE_MININFO saleCCMin = new SALE_MININFO(certPath, storeid, hostname, userpwd, port, ordermode);
        saleCCMin.setAddrnum(addrnum);
        saleCCMin.setZip(zip);
        saleCCMin.setCardnumber(cardnumber);
        saleCCMin.setCardexpmonth(cardexpmonth);
        saleCCMin.setCardexpyear(cardexpyear);
        saleCCMin.setChargetotal(chargetotal);
        saleCCMin.setCvmvalue(cvmvalue);
        return saleCCMin.processTxn();
    }

    public static String escape(String s) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < s.length()) {
            char cs = s.charAt(i);
            switch (cs) {
                case '&': {
                    sb.append("&amp;");
                    break;
                }
                case '<': {
                    sb.append("&lt;");
                    break;
                }
                case '>': {
                    sb.append("&gt;");
                    break;
                }
                default: {
                    sb.append(cs);
                }
            }
            ++i;
        }
        return sb.toString().trim();
    }

    private static void logString(String logRecord) {
        FDPlugin.logString(logRecord, LOG_TRACE);
    }

    private static void logString(String logRecord, int requiredLogLevel) {
        if (requiredLogLevel <= logLevel) {
            System.out.println(String.valueOf(timestampLog ? String.valueOf(new Timestamp(System.currentTimeMillis()).toString()) + " " : "") + "FDPlugin: " + logRecord);
        }
    }

    private static void logException(String logRecord, Exception e) {
        System.out.println(String.valueOf(timestampLog ? String.valueOf(new Timestamp(System.currentTimeMillis()).toString()) + " " : "") + "FDPlugin: " + logRecord + ". " + e.getMessage());
        if (logLevel > LOG_ERRORS) {
            e.printStackTrace();
        }
    }
}

