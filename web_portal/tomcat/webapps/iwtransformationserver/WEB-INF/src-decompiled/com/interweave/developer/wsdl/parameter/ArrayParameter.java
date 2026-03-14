/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.BaseParameter;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ParameterFactory;

public class ArrayParameter
extends BaseParameter {
    private Parameter[] elements = null;
    private String arrayType = null;

    ArrayParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            WSDLParser parser = soapRequest.getParser();
            String[] ns_nsuri_type = parser.getNamespaceUriAndType("//complexType[@name='" + aType + "']/complexContent/restriction/attribute/@arrayType");
            soapRequest.checkNamespace(ns_nsuri_type[0], ns_nsuri_type[1]);
            this.namespaceURI = ns_nsuri_type[1];
            this.arrayType = ns_nsuri_type[2];
            this.arrayType = this.arrayType.replaceFirst("\\[\\]$", "");
            this.elements = new Parameter[2];
            this.elements[0] = ParameterFactory.createParameter(soapRequest, "item", null, this.namespaceURI, this.arrayType, null, null, null);
            this.elements[1] = ParameterFactory.createParameter(soapRequest, "item", null, this.namespaceURI, this.arrayType, null, null, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getSimpleXML(SoapRequest soapRequest) {
        String ns = soapRequest.getPrefix(this.namespaceURI);
        String result = "<" + this.paramname + " " + "xsi:type=\"SOAP-ENC:Array\" " + "SOAP-ENC:arrayType=\"" + ns + this.arrayType + "[" + Integer.toString(this.elements.length) + "]" + "\">\r\n";
        int i = 0;
        while (i < this.elements.length) {
            result = String.valueOf(result) + this.elements[i].getXML(soapRequest);
            ++i;
        }
        result = String.valueOf(result) + "</" + this.paramname + ">\r\n";
        return result;
    }
}

