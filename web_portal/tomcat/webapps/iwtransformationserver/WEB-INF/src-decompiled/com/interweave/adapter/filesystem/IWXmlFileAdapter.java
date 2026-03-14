/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.filesystem;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

public class IWXmlFileAdapter
implements IWIDataMap {
    private Access curAccess = null;
    public String fileName = "";
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
        String strIn = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        this.curAccess = null;
        request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
        responseBuffer.append("<datamap ID=\"1\" name=\"" + this.dataMap.getName() + "\" rowcount=\"1\">\n");
        responseBuffer.append("\t<data rowcount=\"1\">\n");
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                for (Parameter param : values.getParameter()) {
                    Mapping mapping = param.getMapping();
                    String type = mapping.getType();
                    if (type == null || type == "" || type.compareToIgnoreCase("XmlFile") != 0) continue;
                    this.fileName = param.getInput();
                }
            }
            strIn = this.removeXMLDecl(this.fileToString(this.fileName));
            responseBuffer.append(strIn);
        } else {
            responseBuffer.append("\t\t<Error>There was an XML File " + this.fileName + " error</Error>");
        }
        responseBuffer.append("\t</data>\n</datamap>\n");
        return responseBuffer;
    }

    public StringBuffer replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result;
    }

    public String removeXMLDecl(String inputStr) {
        int pos = inputStr.indexOf("<?xml");
        if (pos < 0) {
            return inputStr;
        }
        pos = inputStr.indexOf("?>");
        return inputStr.substring(pos += 2);
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public String fileToString(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader input = null;
        input = new BufferedReader(new FileReader(file));
        StringBuffer outString = new StringBuffer();
        String line = null;
        while ((line = input.readLine()) != null) {
            outString.append(line);
        }
        input.close();
        line = outString.toString();
        outString = null;
        file = null;
        input = null;
        return line;
    }

    public static void main(String[] params) throws Exception {
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

