/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

import com.interweave.core.IWServices;
import java.util.Enumeration;
import java.util.Hashtable;

public class IWApplication {
    public static Hashtable applications = new Hashtable();

    public static synchronized IWServices getService(String applicationName) {
        return IWApplication.getService(applicationName, null);
    }

    public static synchronized IWServices getService(String applicationName, String realPath) {
        IWServices iwService;
        if (applications.size() == 0 || !applications.containsKey(applicationName)) {
            iwService = new IWServices();
            iwService.initialize(applicationName, realPath);
            applications.put(applicationName, iwService);
        } else {
            iwService = (IWServices)applications.get(applicationName);
        }
        return iwService;
    }

    public static void remove(IWServices iwService) {
        applications.remove(iwService);
    }

    public static String appliactionList() {
        StringBuffer responseBuffer = new StringBuffer();
        if (applications.size() > 0) {
            int row = 1;
            responseBuffer.append("<transaction name=\"applications\">\n    <datamap ID=\"1\" name=\"applications\" rowcount=\"" + applications.size() + "\">\n");
            Enumeration enumerateerate = applications.keys();
            while (enumerateerate.hasMoreElements()) {
                String strKey = (String)enumerateerate.nextElement();
                responseBuffer.append("        <row number\"" + row++ + ">\n                 <col number=\"1\" name=\"appname\">" + strKey + "</col>\n        </row>\n");
                Object var3_3 = null;
            }
            responseBuffer.append("    </datamap>\n");
        }
        return responseBuffer.toString();
    }
}

