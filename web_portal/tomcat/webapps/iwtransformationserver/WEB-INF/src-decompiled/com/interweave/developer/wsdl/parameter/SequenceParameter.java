/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.XMLUtils;
import com.interweave.developer.wsdl.parameter.BaseParameter;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ParameterFactory;

public class SequenceParameter
extends BaseParameter {
    private Parameter[] sequence = null;

    SequenceParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            WSDLParser parser = soapRequest.getParser();
            String typeName = XMLUtils.dropNamespace(this.type);
            String[] names = parser.getWsdlXPathAttributeArray("//complexType[@name='" + typeName + "']/sequence/element/@name");
            this.sequence = new Parameter[names.length];
            int i = 0;
            while (i < names.length) {
                String sName = names[i];
                String typeXPath = "//complexType[@name='" + typeName + "']/sequence/element[@name='" + sName + "']";
                this.sequence[i] = ParameterFactory.createParameter(soapRequest, sName, typeXPath);
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSimpleXML(SoapRequest soapRequest) {
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

