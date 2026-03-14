/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.session;

import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IWSession {
    public long timeOut = 10L;
    public long startTime = System.currentTimeMillis();
    public IWServices lnkIWServices = null;
    public Vector transactions = new Vector();
    public boolean cacheTran = false;
    public boolean commitTran = false;
    public String themeKey = null;
    private Hashtable values = new Hashtable();

    public IWSession(IWServices services) {
        this.lnkIWServices = services;
    }

    private void setAppVars() {
        Enumeration enumerate = this.lnkIWServices.appvars.keys();
        while (enumerate.hasMoreElements()) {
            String key = (String)enumerate.nextElement();
            String value = null;
            value = (String)this.lnkIWServices.appvars.get(key);
            this.setValue(key, value);
        }
    }

    public StringBuffer select() {
        return this.select(null);
    }

    public StringBuffer select(String companyConfiguration) {
        StringBuffer responseBuffer = new StringBuffer();
        Enumeration enumerate = this.values.keys();
        this.setAppVars();
        responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
        while (enumerate.hasMoreElements()) {
            String sessionKey = (String)enumerate.nextElement();
            responseBuffer.append("<col number=\"1\" name=\"" + sessionKey + "\">" + this.values.get(sessionKey) + "</col>\n");
            sessionKey = null;
        }
        if (companyConfiguration != null) {
            responseBuffer.append(companyConfiguration);
        }
        responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
        return responseBuffer;
    }

    public void insert(String name, String value) {
        this.update(name, value);
    }

    public void update(String name, String value) {
        this.values.put(name, value);
    }

    public void delete(String name) {
        this.values.remove(name);
    }

    public void clear() {
        if (!this.values.isEmpty()) {
            this.values.clear();
        }
    }

    public void dump() {
        if (!this.values.isEmpty()) {
            Enumeration enumerate = this.values.keys();
            while (enumerate.hasMoreElements()) {
                String sessionKey = (String)enumerate.nextElement();
                System.out.println("Session Value :" + sessionKey + ":" + this.values.get(sessionKey));
                Object var2_2 = null;
            }
        } else {
            System.out.println("Values Empty:");
        }
    }

    public String getTheme(IWServices lnkIWServices) {
        String value = (String)this.values.get("themename");
        return lnkIWServices.getTheme(value);
    }

    public void setValue(String key, String value) {
        this.values.put(key, value);
    }

    public String getValue(String key) {
        return (String)this.values.get(key);
    }

    public String cacheTransactions(boolean value) {
        this.cacheTran = value;
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
        responseBuffer.append("<col number=\"1\" name=\"result\">true</col>\n");
        responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
        return responseBuffer.toString();
    }

    public String commit(IWRequest request) {
        IWRequest iwRequest = null;
        StringBuffer responseBuffer = new StringBuffer();
        if (this.cacheTran) {
            responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
            responseBuffer.append("<col number=\"1\" name=\"result\">true</col>\n");
            responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
            this.commitTran = true;
            Enumeration enumerate = this.transactions.elements();
            while (enumerate.hasMoreElements()) {
                try {
                    iwRequest = new IWRequest(request);
                }
                catch (Exception e) {
                    System.out.println("Session Timeout");
                    responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
                    responseBuffer.append("<col number=\"1\" name=\"result\">timeout</col>\n");
                    responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
                    return responseBuffer.toString();
                }
                iwRequest.rawData = (String)enumerate.nextElement();
                System.out.println("Commit \n" + iwRequest.rawData);
                iwRequest.fromIWXML();
                iwRequest.processDataMap();
                iwRequest = null;
            }
            this.commitTran = false;
            this.transactions.removeAllElements();
        } else {
            responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
            responseBuffer.append("<col number=\"1\" name=\"result\">false</col>\n");
            responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
        }
        return responseBuffer.toString();
    }

    public String rollback() {
        StringBuffer responseBuffer = new StringBuffer();
        if (this.cacheTran) {
            responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
            responseBuffer.append("<col number=\"1\" name=\"result\">true</col>\n");
            responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
            this.transactions.removeAllElements();
        } else {
            responseBuffer.append("<transaction name=\"sessionvars\">\n<datamap ID=\"1\" name=\"sessionvars\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
            responseBuffer.append("<col number=\"1\" name=\"result\">false</col>\n");
            responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
        }
        return responseBuffer.toString();
    }

    public String cacheTransaction(IWRequest request) {
        StringBuffer responseBuffer = new StringBuffer();
        try {
            this.transactions.add(request.toXML());
            responseBuffer.append("<transaction name=\"" + request.getParameter("tranname") + "\">\n<datamap ID=\"1\" name=\"trancache\" rowcount=\"1\">\n<data rowcount=\"1\">\n<row number=\"1\">\n");
            responseBuffer.append("<col number=\"1\" name=\"result\">true</col>\n");
            responseBuffer.append("</row>\n</data>\n</datamap>\n</transaction>\n");
        }
        catch (Exception exception) {
            // empty catch block
        }
        return responseBuffer.toString();
    }

    public void setRequest(IWRequest request) {
        Enumeration enumerate = request.params.keys();
        String key = null;
        String value = null;
        while (enumerate.hasMoreElements()) {
            key = (String)enumerate.nextElement();
            value = (String)request.params.get(key);
            this.values.put(key, value);
            key = null;
            value = null;
        }
        enumerate = null;
        enumerate = request.postParams.keys();
        while (enumerate.hasMoreElements()) {
            key = (String)enumerate.nextElement();
            value = (String)request.postParams.get(key);
            this.values.put(key, value);
            key = null;
            value = null;
        }
    }

    public void setValues(HttpServletRequest request) {
        String strData = null;
        strData = this.lnkIWServices.appName != null ? this.lnkIWServices.appName : "";
        this.values.put("applicationname", strData);
        strData = request.getServerName() != null ? request.getServerName() : "";
        this.values.put("servername", strData);
        strData = String.valueOf(request.getServerPort()) != null ? String.valueOf(request.getServerPort()) : "";
        this.values.put("serverport", strData);
        strData = request.getRequestedSessionId() != null ? request.getRequestedSessionId() : "";
        this.values.put("requestedsessionid", strData);
        strData = request.getContextPath() != null ? request.getContextPath() : "";
        this.values.put("contextpath", strData);
        strData = request.getAuthType() != null ? request.getAuthType() : "";
        this.values.put("authtype", strData);
        strData = request.getPathInfo() != null ? request.getPathInfo() : "";
        this.values.put("pathinfo", strData);
        strData = request.getPathTranslated() != null ? request.getPathTranslated() : "";
        this.values.put("pathtranslated", strData);
        strData = request.getProtocol() != null ? request.getProtocol() : "";
        this.values.put("protocol", strData);
        strData = request.getRemoteAddr() != null ? request.getRemoteAddr() : "";
        this.values.put("remoteaddr", strData);
        strData = request.getRemoteHost() != null ? request.getRemoteHost() : "";
        this.values.put("remotehost", strData);
        strData = request.getRemoteUser() != null ? request.getRemoteUser() : "";
        this.values.put("remoteuser", strData);
        strData = request.getRequestURI() != null ? request.getRequestURI() : "";
        this.values.put("requesturi", strData);
        strData = request.getRequestedSessionId() != null ? request.getRequestedSessionId() : "";
        this.values.put("requestedsessionid", strData);
        strData = request.getScheme() != null ? request.getScheme() : "";
        this.values.put("scheme", strData);
        strData = request.getServletPath() != null ? request.getServletPath() : "";
        this.values.put("servletpath", strData);
        HttpSession httpSession = request.getSession();
        strData = String.valueOf(httpSession.getCreationTime()) != null ? String.valueOf(httpSession.getCreationTime()) : "";
        this.values.put("creationtime", strData);
        strData = String.valueOf(httpSession.getLastAccessedTime()) != null ? String.valueOf(httpSession.getLastAccessedTime()) : "";
        this.values.put("lastaccessedtime", strData);
        strData = String.valueOf(httpSession.getMaxInactiveInterval()) != null ? String.valueOf(httpSession.getMaxInactiveInterval()) : "";
        this.values.put("maxinactiveinterval", strData);
    }
}

