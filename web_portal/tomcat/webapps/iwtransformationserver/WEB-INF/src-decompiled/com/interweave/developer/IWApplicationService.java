/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import com.interweave.actionscript.IWASResponse;
import com.interweave.core.IWApplication;
import com.interweave.core.IWRequest;
import flashgateway.io.ASObject;

public class IWApplicationService {
    public IWASResponse executeASTransaction(ASObject iwRequest) {
        IWASResponse iwASResponse = new IWASResponse();
        try {
            IWRequest request = new IWRequest(iwRequest);
            request.tranBuffer = null;
            request.tranBuffer = new StringBuffer();
            request.tranBuffer.append(IWApplication.appliactionList());
            request.makeASResponse(iwASResponse);
        }
        catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            iwASResponse.put("Error", e.getMessage());
        }
        return iwASResponse;
    }
}

