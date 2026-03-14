/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.parameter.Parameter;

public class ReturnParameter
implements Parameter {
    private String name = null;

    ReturnParameter(String aName) {
        this.name = aName;
    }

    public String getXML(SoapRequest soapRequest) {
        return "<!-- Response: " + this.name + " -->\r\n";
    }
}

