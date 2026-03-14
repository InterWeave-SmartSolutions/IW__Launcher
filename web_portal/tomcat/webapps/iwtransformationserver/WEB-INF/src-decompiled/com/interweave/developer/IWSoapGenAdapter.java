/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.interweave.developer.IWXMLDocument;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;

public class IWSoapGenAdapter
implements IWIDataMap {
    private Access curAccess = null;
    public String SOAPUrl = "";
    public String SOAPAction = "";
    public String SOAPName = "";
    public String SOAPMessage = "";
    public String SOAPResponse = "";
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        String strTransformIn = null;
        String strTransformOut = null;
        String strTransformPackageOut = null;
        String strTransformPackageOutEscape = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        this.curAccess = null;
        request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                for (Parameter param : values.getParameter()) {
                    Mapping mapping = param.getMapping();
                    String type = mapping.getType();
                    if (type == null || type == "") continue;
                    if (type.compareToIgnoreCase("TransformIn") == 0) {
                        strTransformIn = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("TransformPackageOut") == 0) {
                        strTransformPackageOut = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("TransformPackageOutEscape") == 0) {
                        strTransformPackageOutEscape = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("TransformOut") == 0) {
                        strTransformOut = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPUrl") == 0) {
                        this.SOAPUrl = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPAction") == 0) {
                        this.SOAPAction = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPName") == 0) {
                        this.SOAPName = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPName") == 0) {
                        this.SOAPName = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPMessage") == 0) {
                        this.SOAPMessage = param.getInput();
                        continue;
                    }
                    if (type.compareToIgnoreCase("SOAPMessage") != 0) continue;
                    this.SOAPResponse = param.getInput();
                }
            }
            if (strTransformIn != null) {
                request.getXsltc().strTransform = strTransformIn;
                this.SOAPMessage = request.getXsltc().executeTransform(request.toXML(), request);
            } else {
                this.SOAPMessage = request.getParameter("soapmessage");
            }
            request.getXsltc().strTransform = strTransformOut;
            responseBuffer.append(request.getXsltc().executeTransform(this.goImpl(), request));
        } else {
            responseBuffer.append("\t\t<Error>There was a WebService error</Error>");
        }
        responseBuffer.append("\t</data>\n</datamap>\n");
        return responseBuffer;
    }

    public StringBuffer replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result;
    }

    public String goImpl() throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        Object strTransformIn = null;
        Object strTransformOut = null;
        String soapOut = "";
        IWXMLDocument inDoc = new IWXMLDocument();
        IWXMLDocument outDoc = new IWXMLDocument();
        inDoc.build(this.SOAPMessage);
        String fileName = "c:/soaptest/" + this.SOAPName + "in.xslt";
        this.stringToFile(fileName, inDoc.makeRequestTransform());
        outDoc.allowMultiple = false;
        outDoc.build(this.SOAPResponse);
        fileName = "c:/soaptest/" + this.SOAPName.toLowerCase() + "out.xslt";
        this.stringToFile(fileName, outDoc.makeResponseTransform());
        fileName = "c:/soaptest/" + this.SOAPName.toLowerCase() + "tran.xslt";
        responseBuffer.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n");
        responseBuffer.append("<xsl:output omit-xml-declaration=\"yes\"/>\n");
        responseBuffer.append("<xsl:template name=\"" + this.SOAPName.toLowerCase() + "\">\n");
        responseBuffer.append("<transaction name=\"" + this.SOAPName.toLowerCase() + "\" type=\"sequential\">\n");
        responseBuffer.append("<transform/>\n");
        responseBuffer.append("<classname>com.interweave.adapter.http.IWSalesforce</classname>\n");
        responseBuffer.append("<datamap name=\"" + this.SOAPName.toLowerCase() + "\">\n");
        responseBuffer.append("<driver/>\n");
        responseBuffer.append("<url/>\n");
        responseBuffer.append("<user/>\n");
        responseBuffer.append("<password/>\n");
        responseBuffer.append("<access type=\"procedure\">\n");
        responseBuffer.append("<statementpre/>\n");
        responseBuffer.append("<statementpost/>\n");
        responseBuffer.append("<values>\n");
        responseBuffer.append("<parameter>\n");
        responseBuffer.append("<input>sessionid</input>\n");
        responseBuffer.append("<mapping quoted=\"true\">sessionid</mapping>\n");
        responseBuffer.append("</parameter>\n");
        responseBuffer.append("<parameter>\n");
        responseBuffer.append("<input>" + this.SOAPName.toLowerCase() + "in" + "</input>\n");
        responseBuffer.append("<mapping quoted=\"true\" type=\"TransformIn\">TransformIn</mapping>\n");
        responseBuffer.append("</parameter>\n");
        responseBuffer.append("<parameter>\n");
        responseBuffer.append("<input>" + this.SOAPName.toLowerCase() + "out" + "</input>\n");
        responseBuffer.append("<mapping quoted=\"true\" type=\"TransformOut\">TransformOut</mapping>\n");
        responseBuffer.append("</parameter>\n");
        responseBuffer.append("<parameter>\n");
        responseBuffer.append("<input>" + this.SOAPUrl + "</input>\n");
        responseBuffer.append("<mapping quoted=\"true\" type=\"SOAPUrl\">SOAPUrl</mapping>\n");
        responseBuffer.append("</parameter>\n");
        responseBuffer.append("<parameter>\n");
        responseBuffer.append("<input>" + this.SOAPAction + "</input>\n");
        responseBuffer.append("<mapping quoted=\"true\" type=\"SOAPAction\">SOAPAction</mapping>\n");
        responseBuffer.append("</parameter>\n");
        responseBuffer.append("</values>\n");
        responseBuffer.append("</access>\n");
        responseBuffer.append("</datamap>\n");
        responseBuffer.append("</transaction>\n");
        responseBuffer.append("</xsl:template>\n");
        responseBuffer.append("</xsl:stylesheet>\n");
        this.stringToFile(fileName, responseBuffer.toString());
        return soapOut;
    }

    public String removeXMLDecl(String inputStr) {
        int pos = inputStr.indexOf("<?xml");
        if (pos < 0) {
            return inputStr;
        }
        pos = inputStr.indexOf("?>");
        return inputStr.substring(pos += 2);
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public String fileToString(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader input = null;
        input = new BufferedReader(new FileReader(file));
        StringBuffer outString = new StringBuffer();
        String line = null;
        while ((line = input.readLine()) != null) {
            outString.append(line);
        }
        input.close();
        line = outString.toString();
        outString = null;
        file = null;
        input = null;
        return line;
    }

    public void stringToFile(String fileName, String out) throws Exception {
        File file = new File(fileName);
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file));
        output.write(out);
        output.close();
        file = null;
        output = null;
    }

    public static void main(String[] params) throws Exception {
        IWSoapGenAdapter iwsoapGenAdapter = new IWSoapGenAdapter();
        String fileName = "c:/soaptest/stockquotemessage.xml";
        iwsoapGenAdapter.SOAPMessage = iwsoapGenAdapter.fileToString(fileName);
        fileName = "c:/soaptest/stockquoteresponse.xml";
        iwsoapGenAdapter.SOAPResponse = iwsoapGenAdapter.fileToString(fileName);
        iwsoapGenAdapter.SOAPUrl = "http://www.xignite.com/xQuotes.asmx";
        iwsoapGenAdapter.SOAPName = "stockqoute";
        iwsoapGenAdapter.SOAPAction = "http://www.xignite.com/services/GetQuotes";
        iwsoapGenAdapter.goImpl();
    }

    public String getDriver() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUser() {
        return null;
    }
}

