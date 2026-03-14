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
import java.util.Iterator;

public class IWReflectType
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
                    String type = mapping.getType();
                    IWMappingType map = new IWMappingType();
                    strData = map.getData(param, request);
                    if (strData == null) {
                        strData = "";
                    }
                    if (type != null && type != "" && type.compareToIgnoreCase("default") != 0) {
                        responseBuffer.append("            <col number=\"" + count + "\" name=\"" + param.getMapping().getContent() + "\">" + strData + "</col>\n");
                    } else {
                        responseBuffer.append("            <col number=\"" + count + "\" name=\"" + param.getInput() + "\">" + strData + "</col>\n");
                    }
                    ++count;
                }
                responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
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

