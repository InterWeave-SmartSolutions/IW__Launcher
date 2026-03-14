/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl;

import com.interweave.developer.wsdl.WSDLParser;
import com.interweave.developer.wsdl.parameter.Parameter;
import com.interweave.developer.wsdl.parameter.ParameterFactory;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class SoapRequest {
    private String method = null;
    private String namespaceURI = null;
    private Vector params = null;
    private HashMap namespaceURIs;
    private WSDLParser parser;
    public static final String NS_SOAP_ENV = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String NS_SOAP_ENC = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String NS_xsi = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String NS_xsd = "http://www.w3.org/2001/XMLSchema";

    public String getMethodName() {
        return this.method;
    }

    public SoapRequest(WSDLParser aParser, String aMethod, String aNamespaceURI) {
        this.parser = aParser;
        this.method = aMethod;
        this.namespaceURI = aNamespaceURI;
        this.params = new Vector();
        this.namespaceURIs = new HashMap();
        this.checkNamespace("SOAP-ENV", NS_SOAP_ENV);
        this.checkNamespace("SOAP-ENC", NS_SOAP_ENC);
        this.checkNamespace("xsi", NS_xsi);
        this.checkNamespace("xsd", NS_xsd);
        this.checkNamespace("m", this.namespaceURI);
    }

    private String buildNamespaceDeclarations() {
        String result = "";
        Set uris = this.namespaceURIs.keySet();
        for (String uri : uris) {
            String prefix = (String)this.namespaceURIs.get(uri);
            result = String.valueOf(result) + "xmlns:" + prefix + "=\"" + uri + "\" ";
        }
        return result;
    }

    public String getXML() {
        String funcname = this.method;
        this.params.size();
        String request = "<SOAP-ENV:Envelope " + this.buildNamespaceDeclarations() + " SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n" + "  <SOAP-ENV:Body>\r\n" + "    <" + funcname + " xmlns=\"" + this.namespaceURI + "\">\r\n";
        int i = 0;
        while (i < this.params.size()) {
            Parameter p = (Parameter)this.params.get(i);
            request = String.valueOf(request) + "      " + p.getXML(this);
            ++i;
        }
        request = String.valueOf(request) + "    </" + funcname + ">\r\n" + "  </SOAP-ENV:Body>\r\n" + "</SOAP-ENV:Envelope>\r\n";
        System.out.println(request);
        return request;
    }

    public void addParam(String paramname, String typeXPath) {
        Parameter parameter = ParameterFactory.createParameter(this, paramname, typeXPath);
        this.params.add(parameter);
    }

    public String checkNamespace(String namespace, String namespaceURI) {
        String resultNS = namespace;
        if (namespaceURI != null && (resultNS = (String)this.namespaceURIs.get(namespaceURI)) == null) {
            resultNS = namespace;
            while (this.namespaceURIs.containsValue(resultNS)) {
                resultNS = String.valueOf(resultNS) + "_" + Integer.toString(this.namespaceURIs.size());
            }
            this.namespaceURIs.put(namespaceURI, resultNS);
        }
        return resultNS;
    }

    public String getPrefix(String namespaceURI) {
        String resultPrefix = namespaceURI != null ? String.valueOf(this.checkNamespace("x", namespaceURI)) + ":" : "";
        return resultPrefix;
    }

    public WSDLParser getParser() {
        return this.parser;
    }
}

