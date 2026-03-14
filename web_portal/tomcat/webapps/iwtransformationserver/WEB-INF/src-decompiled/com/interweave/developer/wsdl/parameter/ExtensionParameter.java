/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.BaseParameter;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ParameterFactory;

public class ExtensionParameter
extends BaseParameter {
    private Parameter extParameter = null;

    ExtensionParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            WSDLParser parser = soapRequest.getParser();
            String[] ns_nsuri_type = parser.getNamespaceUriAndType("//complexType[@name='" + this.type + "']/complexContent/extension/@base");
            this.extParameter = ns_nsuri_type[2] == null ? ParameterFactory.createParameter(soapRequest, this.paramname, null, null, "anyType", null, null, null) : ParameterFactory.createParameter(soapRequest, this.paramname, ns_nsuri_type[0], ns_nsuri_type[1], ns_nsuri_type[2], null, null, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSimpleXML(SoapRequest soapRequest) {
        String result = this.extParameter.getXML(soapRequest);
        return result;
    }
}

