/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.iwnative;

import com.interweave.adapter.IWBaseAdaptor;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.util.ArrayList;
import java.util.Enumeration;

public class IWNativeBaseAdaptor
extends IWBaseAdaptor {
    public void setup(Datamap map, IWRequest request) throws Exception {
        this.setupInitConnect(map, request);
    }

    public StringBuffer go(IWRequest request) throws Exception {
        Enumeration enumerate = this.accessList.elements();
        StringBuffer result = new StringBuffer("");
        if (enumerate.hasMoreElements()) {
            Access access = (Access)((Object)enumerate.nextElement());
            this.curAccess = null;
            request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
            if (access != null) {
                this.curAccess = access;
                String stpre = this.curAccess.getStatementpre();
                if (stpre != null && stpre.trim().length() > 0) {
                    stpre = IWServices.replaceParameters(stpre, this.curAccess, request, this.dataMap);
                    if (this.contentType.equalsIgnoreCase("ARRAY")) {
                        String[] ec;
                        ArrayList<String> rN = new ArrayList<String>();
                        StringBuffer rQN = new StringBuffer();
                        StringBuffer rQ = new StringBuffer();
                        StringBuffer r2QN = new StringBuffer();
                        StringBuffer r2Q = new StringBuffer();
                        this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
                        this.returnName = rN.toArray(new String[0]);
                        this.replaceQuoteName = rQN.toString();
                        this.replaceQuote = rQ.toString();
                        this.replace2QuoteName = r2QN.toString();
                        this.replace2Quote = r2Q.toString();
                        String[] stringArray = ec = stpre.split("`");
                        int n = 0;
                        int n2 = stringArray.length;
                        while (n < n2) {
                            String cec = stringArray[n];
                            String strData = IWServices.processParameters(request, cec, "Result", this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                            Runtime rnt = Runtime.getRuntime();
                            request.lnkIWServices.logConsole("IWNativeBaseAdaptor: Command " + strData, IWServices.LOG_TRACE, this.iwRequest);
                            Process prc = rnt.exec(strData);
                            int res = prc.waitFor();
                            result.append(this.oneColOneRow("Result", res == 0 ? "Success" : "Error code " + res));
                            ++n;
                        }
                    } else {
                        String strData = IWServices.processParameters(request, stpre, "Result", this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                        Runtime rnt = Runtime.getRuntime();
                        request.lnkIWServices.logConsole("IWNativeBaseAdaptor: Command " + strData, IWServices.LOG_TRACE, this.iwRequest);
                        Process prc = rnt.exec(strData);
                        int res = prc.waitFor();
                        result.append(this.oneColOneRow("Result", res == 0 ? "Success" : "Error code " + res));
                    }
                }
            }
        }
        return this.addDataMapXML(this.addDataXML(result));
    }

    public void closeConnection() {
    }
}

