/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.parameter.Parameter;

public class EmptyElementParameter
implements Parameter {
    private String name = null;

    EmptyElementParameter(String aName) {
        this.name = aName;
    }

    public String getXML(SoapRequest soapRequest) {
        return "<!-- " + this.name + " empty -->\r\n";
    }
}

