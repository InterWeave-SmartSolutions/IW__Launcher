/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.creditcard;

import com.PNPssl.pnpapi;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import java.util.Hashtable;
import java.util.Properties;

public class IWPNPDirectLink
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
        pnpapi pnpConnect = new pnpapi();
        Properties pnpRequest = new Properties();
        Properties pnpResponse = new Properties();
        StringBuffer responseBuffer = new StringBuffer();
        Object strData = null;
        pnpRequest.put("cert_dir", request.getParameter("cert_dir"));
        pnpRequest.put("publisher--name", request.getParameter("publisher-name"));
        pnpRequest.put("mode", request.getParameter("mode"));
        pnpRequest.put("card-name", request.getParameter("card-name"));
        pnpRequest.put("card-number", request.getParameter("card-number"));
        pnpRequest.put("card-address1", request.getParameter("card-address1"));
        pnpRequest.put("card-city", request.getParameter("card-city"));
        pnpRequest.put("card-state", request.getParameter("card-state"));
        pnpRequest.put("card-zip", request.getParameter("card-zip"));
        pnpRequest.put("card-country", request.getParameter("card-country"));
        pnpRequest.put("card-amount", request.getParameter("card-amount"));
        pnpRequest.put("card-exp", request.getParameter("card-exp"));
        pnpRequest.put("email", request.getParameter("email"));
        pnpRequest.put("card-type", request.getParameter("card-type"));
        pnpRequest.put("publisher-email", request.getParameter("publisher-email"));
        pnpResponse = pnpConnect.doTransaction(pnpRequest);
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

