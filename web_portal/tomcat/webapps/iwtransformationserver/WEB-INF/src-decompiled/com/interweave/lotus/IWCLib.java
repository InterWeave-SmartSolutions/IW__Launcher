/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.lotus;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.jniwrapper.AnsiString;
import com.jniwrapper.DefaultLibraryLoader;
import com.jniwrapper.Function;
import com.jniwrapper.Library;
import java.util.Hashtable;
import java.util.Iterator;

public class IWCLib
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
        Object strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        int count = 1;
        this.curAccess = null;
        String strLibPath = "";
        String strLibrary = "";
        String strFunction = "";
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                Iterator iterate = values.getParameter().iterator();
                while (iterate.hasNext()) {
                    strData = null;
                    Parameter param = (Parameter)((Object)iterate.next());
                    Mapping mapping = param.getMapping();
                    String type = mapping.getContent();
                    if (type != null && type != "") {
                        if (type.compareToIgnoreCase("LibraryPath") == 0) {
                            strLibPath = param.getInput();
                        } else if (type.compareToIgnoreCase("Library") == 0) {
                            strLibrary = param.getInput();
                        } else if (type.compareToIgnoreCase("Function") == 0) {
                            strFunction = param.getInput();
                        }
                    }
                    ++count;
                }
                System.out.println("\nLibrary Path : " + strLibPath);
                System.out.println("\nLibrary      : " + strLibrary);
                System.out.println("\nFunction     : " + strFunction);
                request.add("mapname", this.mapName);
                DefaultLibraryLoader.getInstance().addPath(strLibPath);
                Function cFunc = new Library(strLibrary).getFunction(strFunction);
                cFunc.setCallingConvention((byte)1);
                AnsiString result_buffer = new AnsiString(20480);
                AnsiString request_buffer = new AnsiString(request.toXML());
                cFunc.invoke(null, (com.jniwrapper.Parameter)request_buffer, (com.jniwrapper.Parameter)result_buffer);
                responseBuffer.append(result_buffer.getValue());
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

