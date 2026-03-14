/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.webservice;

import com.interweave.actionscript.IWASResponse;
import com.interweave.core.IWRequest;
import flashgateway.io.ASObject;

public class IWWebService {
    public boolean timedOut = false;

    public IWASResponse executeASTransaction(ASObject iwRequest) {
        if (iwRequest == null) {
            System.out.println("Null Action Script Object");
            IWASResponse iwASResponse = new IWASResponse();
            iwASResponse.put("Error", "null Request Object");
            return iwASResponse;
        }
        IWRequest request = null;
        IWASResponse iwASResponse = new IWASResponse();
        try {
            request = new IWRequest(iwRequest);
        }
        catch (Exception e) {
            iwASResponse.put("Error", e.getMessage());
            return iwASResponse;
        }
        try {
            request.processDataMap();
            request.makeASResponse(iwASResponse);
        }
        catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            iwASResponse.put("Error", e.getMessage());
        }
        return iwASResponse;
    }
}

