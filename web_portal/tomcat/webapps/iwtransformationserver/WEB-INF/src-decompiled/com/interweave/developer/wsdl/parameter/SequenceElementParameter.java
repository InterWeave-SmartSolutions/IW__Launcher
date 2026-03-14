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

public class SequenceElementParameter
extends BaseParameter {
    private Parameter[] sequence = null;
    private String seqName = null;

    public String getSeqName() {
        return this.seqName;
    }

    SequenceElementParameter(SoapRequest soapRequest, String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, null);
        try {
            String typeName;
            WSDLParser parser = soapRequest.getParser();
            this.seqName = typeName = XMLUtils.dropNamespace(this.type);
            String[] names = parser.getWsdlXPathAttributeArray("//element[@name='" + typeName + "']/complexType/sequence/element/@name");
            this.sequence = new Parameter[names.length];
            int i = 0;
            while (i < names.length) {
                String sName = names[i];
                String typeXPath = "//element[@name='" + typeName + "']/complexType/sequence/element[@name='" + sName + "']";
                this.sequence[i] = ParameterFactory.createParameter(soapRequest, sName, typeXPath);
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSimpleXML(SoapRequest soapRequest) {
        String result = "";
        int i = 0;
        while (i < this.sequence.length) {
            result = String.valueOf(result) + this.sequence[i].getXML(soapRequest);
            ++i;
        }
        return result;
    }
}

