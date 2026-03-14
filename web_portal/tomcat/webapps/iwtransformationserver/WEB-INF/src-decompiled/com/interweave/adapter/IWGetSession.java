/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import java.util.Hashtable;

public class IWGetSession
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
        StringBuffer responseBuffer = new StringBuffer();
        String tranName = request.getParameter("tranname");
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        boolean count = true;
        this.curAccess = null;
        if (tranName.compareToIgnoreCase("cacheon") == 0) {
            request.iwSession.cacheTransactions(true);
        } else if (tranName.compareToIgnoreCase("cacheoff") == 0) {
            request.iwSession.cacheTransactions(false);
        } else if (tranName.compareToIgnoreCase("commit") == 0) {
            System.out.println("Commit");
            request.iwSession.commit(request);
        } else if (tranName.compareToIgnoreCase("rollback") == 0) {
            request.iwSession.rollback();
        } else if (tranName.compareToIgnoreCase("logout") == 0) {
            request.lnkIWServices.IWlnkSessionManager.remove(request.iwSession);
        } else if (tranName.compareToIgnoreCase("update") == 0) {
            String addName = request.getParameter(request.getParameter("addname"));
            for (Parameter param : values.getParameter()) {
                if (param.getInput().compareToIgnoreCase(addName) != 0) continue;
                String strData = null;
                Mapping mapping = param.getMapping();
                String type = mapping.getType();
                IWMappingType map = new IWMappingType();
                strData = map.getData(param, request);
                if (strData == null) {
                    strData = "";
                }
                request.iwSession.insert(addName, strData);
                break;
            }
        } else if (access != null) {
            this.curAccess = access;
            if (request.iwSession == null) {
                System.out.println("Null Session");
            } else {
                responseBuffer.append(request.iwSession.select().toString());
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

