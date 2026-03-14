/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import com.interweave.actionscript.IWASResponse;
import com.interweave.developer.IWSoapGenAdapter;
import flashgateway.io.ASObject;

public class IWLoggingService {
    public IWASResponse executeASTransaction(ASObject iwRequest) {
        IWASResponse iwASResponse = new IWASResponse();
        try {
            IWSoapGenAdapter soapGenService = new IWSoapGenAdapter();
            soapGenService.SOAPAction = (String)iwRequest.get((Object)"soapaction");
            soapGenService.SOAPUrl = (String)iwRequest.get((Object)"soapurl");
            soapGenService.SOAPName = (String)iwRequest.get((Object)"soapname");
            soapGenService.SOAPMessage = (String)iwRequest.get((Object)"soapmessage");
            soapGenService.SOAPResponse = (String)iwRequest.get((Object)"soapresponse");
            String soapOutput = soapGenService.goImpl();
            iwASResponse.put("soapresponse", "complete");
        }
        catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            iwASResponse.put("Error", e.getMessage());
        }
        return iwASResponse;
    }
}

