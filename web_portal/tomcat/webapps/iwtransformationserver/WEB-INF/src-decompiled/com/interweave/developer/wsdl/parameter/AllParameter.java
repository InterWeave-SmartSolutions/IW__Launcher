/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.BaseParameter;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ParameterFactory;

public class AllParameter
extends BaseParameter {
    private Parameter[] sequence = null;

    AllParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            WSDLParser parser = soapRequest.getParser();
            String[] names = parser.getWsdlXPathAttributeArray("//complexType[@name='" + this.type + "']/all/element/@name");
            this.sequence = new Parameter[names.length];
            int i = 0;
            while (i < names.length) {
                String sName = names[i];
                String typeXPath = "//complexType[@name='" + this.type + "']/all/element[@name='" + sName + "']";
                this.sequence[i] = ParameterFactory.createParameter(soapRequest, sName, typeXPath);
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getSimpleXML(SoapRequest soapRequest) {
        String ns = soapRequest.getPrefix(this.namespaceURI);
        String result = "<" + this.paramname + " xsi:type=\"" + ns + this.type + "\">\r\n";
        int i = 0;
        while (i < this.sequence.length) {
            result = String.valueOf(result) + this.sequence[i].getXML(soapRequest);
            ++i;
        }
        result = String.valueOf(result) + "</" + this.paramname + ">\r\n";
        return result;
    }
}

