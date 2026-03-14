/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.https;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.sun.net.ssl.internal.ssl.Provider;
import flashgateway.io.ASObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class IWURLSecurePost
implements IWIDataMap {
    private Access curAccess = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public void goAS(ASObject request, ArrayList dataMapList) throws Exception {
    }

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public StringBuffer go(IWRequest request) throws Exception {
        String str;
        String urlStr = this.dataMap.getUrl();
        StringBuffer responseBuffer = new StringBuffer();
        String strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        int count = 1;
        this.curAccess = null;
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                Iterator iterate = values.getParameter().iterator();
                responseBuffer.append("    <datamap ID=\"1\" name=\"" + this.mapName + "\" rowcount=\"" + values.getParameter().size() + "\">\n      <data rowcount=\"" + values.getParameter().size() + "\">\n        <row number=\"1\">\n");
                while (iterate.hasNext()) {
                    strData = null;
                    Parameter param = (Parameter)((Object)iterate.next());
                    Mapping mapping = param.getMapping();
                    strData = request.getParameter(param.getInput());
                    if (strData == null) {
                        strData = "";
                    }
                    responseBuffer.append("            <col number=\"" + count + "\" name=\"" + param.getMapping().getContent() + "\">" + strData + "</col>\n");
                    ++count;
                }
                responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
            }
        }
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider((java.security.Provider)new Provider());
        URL url = new URL("https://" + urlStr);
        URLConnection urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "/text/html");
        DataOutputStream printout = new DataOutputStream(urlConn.getOutputStream());
        printout.writeBytes(responseBuffer.toString());
        printout.flush();
        printout.close();
        responseBuffer = null;
        responseBuffer = new StringBuffer();
        BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        while ((str = input.readLine()) != null) {
            responseBuffer.append(str);
        }
        input.close();
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

