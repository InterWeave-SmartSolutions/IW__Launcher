/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.AllParameter;
import com.interweave.developer.wsdl.parameter.ArrayParameter;
import com.interweave.developer.wsdl.parameter.EmptyElementParameter;
import com.interweave.developer.wsdl.parameter.EnumerationParameter;
import com.interweave.developer.wsdl.parameter.ExtensionParameter;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ReturnParameter;
import com.interweave.developer.wsdl.parameter.SequenceElementIndirectParameter;
import com.interweave.developer.wsdl.parameter.SequenceElementParameter;
import com.interweave.developer.wsdl.parameter.SequenceParameter;
import com.interweave.developer.wsdl.parameter.SkalarParameter;
import javax.xml.transform.TransformerException;

public class ParameterFactory {
    public static Parameter createParameter(SoapRequest soapRequest, String paramname, String typeXPath) {
        WSDLParser parser = soapRequest.getParser();
        String[] ns_nsuri_type = parser.getNamespaceUriAndType(String.valueOf(typeXPath) + "/@type");
        if (ns_nsuri_type[2] == null) {
            ns_nsuri_type = parser.getNamespaceUriAndType(String.valueOf(typeXPath) + "/@element");
        }
        String namespace = ns_nsuri_type[0];
        String namespaceURI = ns_nsuri_type[1];
        String type = ns_nsuri_type[2];
        String minOccurs = null;
        String maxOccurs = null;
        String nillable = null;
        try {
            minOccurs = parser.getWsdlXPathAttribute(String.valueOf(typeXPath) + "/@minOccurs");
            maxOccurs = parser.getWsdlXPathAttribute(String.valueOf(typeXPath) + "/@maxOccurs");
            nillable = parser.getWsdlXPathAttribute(String.valueOf(typeXPath) + "/@nillable");
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }
        String localNamespace = soapRequest.checkNamespace(namespace, namespaceURI);
        Object param = null;
        return ParameterFactory.createParameter(soapRequest, paramname, namespace, namespaceURI, type, minOccurs, maxOccurs, nillable);
    }

    public static Parameter createParameter(SoapRequest soapRequest, String paramname, String namespace, String namespaceURI, String type, String minOccurs, String maxOccurs, String nillable) {
        String localNamespace = soapRequest.checkNamespace(namespace, namespaceURI);
        WSDLParser parser = soapRequest.getParser();
        Parameter param = null;
        try {
            String emptyComplexType;
            String enums;
            String arrayType;
            String baseType;
            String all;
            String sequence;
            String elementType;
            String defaultValue;
            if (type == null) {
                param = new ReturnParameter(paramname);
            }
            if (param == null && (defaultValue = SkalarParameter.getDefaultValueForSimpleType(type)) != null) {
                param = new SkalarParameter(paramname, "http://www.w3.org/2001/XMLSchema", type, minOccurs, maxOccurs, nillable, defaultValue);
            }
            if (param == null && (elementType = parser.getWsdlXPathAttribute("//schema/element[@name='" + type + "']/@type")) != null) {
                String emptyComplexType2;
                String sequence2;
                elementType = elementType.replaceFirst(".*[:]", "");
                if (param == null && (sequence2 = parser.getWsdlXPathAttribute("//complexType[@name='" + elementType + "']/sequence")) != null) {
                    param = new SequenceElementIndirectParameter(soapRequest, paramname, namespaceURI, type, elementType, minOccurs, maxOccurs, nillable);
                }
                if (param == null && (emptyComplexType2 = parser.getWsdlXPathAttribute("//complexType[@name='" + elementType + "']/.[count(*)=0]")) != null) {
                    param = new EmptyElementParameter(paramname);
                }
            }
            if (param == null && (sequence = parser.getWsdlXPathAttribute("//element[@name='" + type + "']/complexType/sequence")) != null) {
                param = new SequenceElementParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (sequence = parser.getWsdlXPathAttribute("//complexType[@name='" + type + "']/sequence")) != null) {
                param = new SequenceParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (all = parser.getWsdlXPathAttribute("//complexType[@name='" + type + "']/all")) != null) {
                param = new AllParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (baseType = parser.getWsdlXPathAttribute("//complexType[@name='" + type + "']/complexContent/extension")) != null) {
                param = new ExtensionParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (arrayType = parser.getWsdlXPathAttribute("//complexType[@name='" + type + "']/complexContent/restriction/attribute/@arrayType")) != null) {
                param = new ArrayParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (enums = parser.getWsdlXPathAttribute("//simpleType[@name='" + type + "']/restriction/enumeration")) != null) {
                param = new EnumerationParameter(soapRequest, paramname, namespaceURI, type, minOccurs, maxOccurs, nillable);
            }
            if (param == null && (emptyComplexType = parser.getWsdlXPathAttribute("//element[@name='" + type + "']/complexType[count(*)=0]")) != null) {
                param = new EmptyElementParameter(paramname);
            }
            if (param == null) {
                param = new SkalarParameter(paramname, null, type, minOccurs, maxOccurs, nillable, "???");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            param = new SkalarParameter(paramname, null, type, minOccurs, maxOccurs, nillable, "???");
        }
        return param;
    }
}

