/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.BaseParameter;

public class EnumerationParameter
extends BaseParameter {
    private String[] enumList = null;
    private String enumType = null;

    EnumerationParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            WSDLParser parser = soapRequest.getParser();
            String[] ns_nsuri_type = parser.getNamespaceUriAndType("//simpleType[@name='" + aType + "']/restriction/@base");
            soapRequest.checkNamespace(ns_nsuri_type[0], ns_nsuri_type[1]);
            this.namespaceURI = ns_nsuri_type[1];
            this.enumType = ns_nsuri_type[2];
            this.enumList = parser.getWsdlXPathAttributeArray("//simpleType[@name='" + aType + "']/restriction/enumeration/@value");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getComment(SoapRequest soapRequest) {
        String result = " one out of {";
        int i = 0;
        while (i < this.enumList.length) {
            result = String.valueOf(result) + "'" + this.enumList[i] + "', ";
            ++i;
        }
        result = String.valueOf(result.substring(0, result.lastIndexOf(44))) + "} ";
        return result;
    }

    public String getSimpleXML(SoapRequest soapRequest) {
        String value = "???";
        if (this.enumList.length > 0) {
            value = this.enumList[0];
        }
        String ns = soapRequest.getPrefix(this.namespaceURI);
        String result = "<" + this.paramname + ">" + value + "</" + this.paramname + ">\r\n";
        return result;
    }
}

