/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;

public class IWURLPost
implements IWIDataMap {
    private Access curAccess = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public StringBuffer go(IWRequest request) throws Exception {
        Access access = (Access)((Object)this.accessList.get("procedure"));
        String preTranslation = request.getParameter("preTranslation");
        String preTranslationType = request.getParameter("preTranslationType");
        String postTranslation = request.getParameter("postTranslation");
        String urlStr = this.dataMap.getUrl();
        StringBuffer responseBuffer = new StringBuffer();
        String strData = request.toXML();
        request.lnkIWServices.logConsole("<!-- URLPostIW TO: http://" + urlStr + " -->", IWServices.LOG_DATA, request);
        if (preTranslation != null && preTranslation.length() > 0) {
            String strOutput = null;
            if (preTranslationType.compareToIgnoreCase("OUTPUT") == 0) {
                strData = null;
                strData = request.getResponse();
                request.lnkIWServices.logConsole("<!-- XMLOutput \n" + strData + "\n -->", IWServices.LOG_DATA, request);
            }
            strOutput = strData;
            strData = null;
            request.getXsltc().strTransform = preTranslation;
            strData = request.getXsltc().executeTransform(strOutput, request);
            strOutput = null;
            preTranslation = null;
        }
        request.lnkIWServices.logConsole("<!-- URLPostIW: Data: \n" + strData + "\n -->", IWServices.LOG_TRANSFORMDATA, request);
        this.curAccess = access;
        URL url = new URL("http://" + urlStr);
        URLConnection urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "/text/xml");
        request.lnkIWServices.logConsole("<!-- URLPostIW: Open: http://" + urlStr + " -->", IWServices.LOG_DATA, request);
        if (request.postParams.size() > 0) {
            request.lnkIWServices.logConsole("<!-- URLPostIW: Params: http://" + urlStr + " -->", IWServices.LOG_DATA, request);
            Enumeration enumerate = request.postParams.keys();
            while (enumerate.hasMoreElements()) {
                String key = (String)enumerate.nextElement();
                String value = (String)request.postParams.get(key);
                request.lnkIWServices.logConsole("<!-- URLPostIW: Param: " + key + ":" + value + " -->", IWServices.LOG_DATA, request);
                urlConn.setRequestProperty(key, value);
                key = null;
                value = null;
            }
            request.postParams = null;
            request.postParams = new Hashtable();
        }
        DataOutputStream printout = new DataOutputStream(urlConn.getOutputStream());
        printout.writeBytes(strData);
        printout.flush();
        printout.close();
        request.lnkIWServices.logConsole("<!-- URLPostIW: Sent: http://" + urlStr + " -->", IWServices.LOG_DATA, request);
        BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        strData = null;
        while ((strData = input.readLine()) != null) {
            responseBuffer.append(strData);
            strData = null;
        }
        input.close();
        printout.close();
        if (postTranslation != null && postTranslation.length() > 0) {
            strData = null;
            request.getXsltc().strTransform = postTranslation;
            strData = request.getXsltc().executeTransform(responseBuffer.toString(), request);
            postTranslation = null;
            responseBuffer = null;
            responseBuffer = new StringBuffer();
            responseBuffer.append(strData);
        }
        request.lnkIWServices.logConsole("<!-- URLPostIW: received \n" + responseBuffer.toString() + "\n -->", IWServices.LOG_DATA, request);
        url = null;
        urlConn = null;
        printout = null;
        input = null;
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
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

