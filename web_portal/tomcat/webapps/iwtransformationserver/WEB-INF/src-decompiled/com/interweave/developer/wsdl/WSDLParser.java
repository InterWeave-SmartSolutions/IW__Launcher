/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.XMLUtils;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class WSDLParser {
    public Vector methods = new Vector();
    private Document wsdl;
    private Document wsdl_nsaware;

    public WSDLParser(String wsdlText) {
        try {
            this.wsdl = XMLUtils.parseXML(wsdlText);
            this.wsdl_nsaware = XMLUtils.parseXML_nsaware(wsdlText);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.wsdl = null;
            this.wsdl_nsaware = null;
        }
    }

    public void listMethods() throws Exception {
        NodeIterator ni = XPathAPI.selectNodeIterator((Node)this.wsdl, (String)"//portType/operation/@name");
        Node n = ni.nextNode();
        while (n != null) {
            String methodName = n.getNodeValue();
            SoapRequest soapRequest = this.generateSoapRequest(methodName, null);
            this.methods.addElement(soapRequest);
            n = ni.nextNode();
        }
    }

    public String[] listMethodPorts(String method) {
        String[] resultArray = null;
        try {
            resultArray = this.getWsdlXPathAttributeArray("//portType/operation[@name='" + method + "']/../@name");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    public SoapRequest generateSoapRequest(String method, String portType) throws Exception {
        String portTypeXPath = "//portType";
        if (portType != null && !portType.equals("")) {
            portTypeXPath = String.valueOf(portTypeXPath) + "[@name='" + portType + "']";
        }
        String parameterOrder = this.getWsdlXPathAttribute(String.valueOf(portTypeXPath) + "/operation[@name='" + method + "']/@parameterOrder");
        String requestMessageName = this.getWsdlXPathAttribute(String.valueOf(portTypeXPath) + "/operation[@name='" + method + "']/input/@message");
        String requestNamespace = this.getWsdlXPathAttribute("//binding/operation[@name='" + method + "']/input/body/@namespace");
        if (requestNamespace == null) {
            String[] ns_nsuri_type = this.getNamespaceUriAndType("//service/port/@binding");
            requestNamespace = ns_nsuri_type[1];
        }
        requestMessageName = XMLUtils.dropNamespace(requestMessageName);
        SoapRequest soapRequest = new SoapRequest(this, method, requestNamespace);
        String[] parameterNames = parameterOrder != null ? parameterOrder.split("\\s+") : this.getWsdlXPathAttributeArray("//message[@name='" + requestMessageName + "']/part/@name");
        int i = 0;
        while (i < parameterNames.length) {
            String paramname = parameterNames[i];
            String typeXPath = "//message[@name='" + requestMessageName + "']/part[@name='" + paramname + "']";
            soapRequest.addParam(paramname, typeXPath);
            ++i;
        }
        return soapRequest;
    }

    public String getWsdlXPathAttribute(String xPath) throws TransformerException {
        String result = null;
        String[] values = this.getWsdlXPathAttributeArray(xPath);
        if (values.length > 0) {
            result = values[0];
        }
        return result;
    }

    public String[] getNamespaceUriAndType(String xPathToTypeAttrib) {
        String[] ns_nsuri_type = null;
        ns_nsuri_type = XMLUtils.getXPathType(this.wsdl, this.wsdl_nsaware, xPathToTypeAttrib);
        return ns_nsuri_type;
    }

    public String[] getWsdlXPathAttributeArray(String xPath) throws TransformerException {
        String[] resultArray = XMLUtils.getXPathValues(this.wsdl, xPath);
        return resultArray;
    }

    public String getLocation() {
        String location = null;
        try {
            location = this.getWsdlXPathAttribute("//address/@location");
        }
        catch (Exception exception) {
            // empty catch block
        }
        return location;
    }

    public String getSoapAction(String method) {
        String soapAction = null;
        try {
            String[] portType_method = method.split("\\.");
            if (portType_method.length == 2) {
                method = portType_method[1];
            }
            soapAction = this.getWsdlXPathAttribute("//binding/operation[@name='" + method + "']/operation/@soapAction");
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (soapAction == null) {
            soapAction = "";
        }
        return soapAction;
    }
}

