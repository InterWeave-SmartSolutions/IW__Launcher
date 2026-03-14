/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.filesystem;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import java.io.File;
import java.util.Hashtable;

public class IWFileSystem
implements IWIDataMap {
    private Access curAccess = null;
    private File rootFile = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public StringBuffer go(IWRequest request) throws Exception {
        return this.goImpl();
    }

    public void closeConnection() {
    }

    public StringBuffer goImpl() throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        File[] allFiles = null;
        try {
            this.rootFile = File.listRoots()[0];
            allFiles = this.rootFile.listFiles();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        responseBuffer.append("<datamap ID=\"1\" name=\"RootFS\" rowcount=\"" + allFiles.length + "\">\n");
        responseBuffer.append("\t<data rowcount=\"" + allFiles.length + "\">\n");
        if (allFiles != null) {
            int i = 0;
            while (i < allFiles.length) {
                try {
                    responseBuffer.append("\t\t<row number=\"" + (i + 1) + "\">\n");
                    responseBuffer.append("\t\t\t<col number=\"1\" name=\"FileName\">" + allFiles[i].getName() + "</col>\n");
                    responseBuffer.append("\t\t\t<col number=\"2\" name=\"FileType\">" + (allFiles[i].isFile() ? "File" : "Directory") + "</col>\n");
                    responseBuffer.append("\t\t</row>\n");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                ++i;
            }
        }
        responseBuffer.append("\t</data>\n</datamap>\n");
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public static void main(String[] params) throws Exception {
        IWFileSystem iwfileAdapter = new IWFileSystem();
        System.out.println(iwfileAdapter.goImpl().toString());
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

