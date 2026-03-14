/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.adapter.http.IWXMLBaseAdaptor;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;

public class IWSoapBaseAdaptor
extends IWXMLBaseAdaptor {
    public StringBuffer go(IWRequest request) throws Exception {
        String localSoapAction = this.soapAction == null ? "SOAPAction" : this.soapAction;
        this.httpURLConnection.setRequestProperty("SOAPAction", localSoapAction);
        request.lnkIWServices.logConsole("SOAPAction in SoapAdaptor: " + localSoapAction, IWServices.LOG_TRACE, request);
        return super.go(request);
    }
}

