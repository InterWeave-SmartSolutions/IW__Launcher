/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.lotus;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.interweave.core.IWTagStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;

public class IWLotusSoapAdapter
implements IWIDataMap {
    private Access curAccess = null;
    public String SOAPUrl = "";
    public String SOAPAction = "";
    public String SOAPName = "";
    public String SOAPMessage = "";
    public Hashtable props = new Hashtable();
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
        this.SOAPName = this.dataMap.getName();
        responseBuffer.append("<datamap ID=\"1\" name=\"" + this.SOAPName + "\" rowcount=\"1\">\n");
        responseBuffer.append("\t<data rowcount=\"1\">\n");
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
                    if (type.compareToIgnoreCase("SOAPAction") != 0) continue;
                    this.SOAPAction = param.getInput();
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
        StringBuffer soapBuffer = new StringBuffer();
        URL url = new URL(this.SOAPUrl);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection)connection;
        System.out.println("Soap Connection to: " + this.SOAPUrl);
        this.SOAPMessage = String.valueOf(this.SOAPMessage) + "\n";
        httpConn.setRequestProperty("Content-Length", String.valueOf(this.SOAPMessage.length()));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", this.SOAPAction);
        httpConn.setRequestProperty("Proxy-Connection", "Keep-Alive");
        Enumeration kEnum = this.props.keys();
        while (kEnum.hasMoreElements()) {
            String key = (String)kEnum.nextElement();
            httpConn.setRequestProperty(key, (String)this.props.get(key));
        }
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        out.write(this.SOAPMessage.getBytes());
        out.close();
        try {
            String inputLine;
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            while ((inputLine = in.readLine()) != null) {
                soapBuffer.append(inputLine);
            }
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String soapOut = soapBuffer.toString();
        IWTagStream tag = new IWTagStream();
        soapOut = tag.removeXMLDecl(soapOut);
        while (soapOut.startsWith("<SOAP") || soapOut.startsWith("<soap") || soapOut.startsWith("<loginResponse")) {
            soapOut = tag.tagRemove(soapOut).trim();
            System.out.println("\n\n" + soapOut);
        }
        tag = null;
        soapOut = "<iwsoap>" + soapOut + "</iwsoap>";
        System.out.println("Soap Buffer-------------------------\n\n" + soapOut + "\n-----------------------------\n\n");
        return soapOut;
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

    public String removeXMLDecl(String inputStr) {
        int pos = inputStr.indexOf("<?xml");
        if (pos < 0) {
            return inputStr;
        }
        pos = inputStr.indexOf("?>");
        return inputStr.substring(pos += 2);
    }

    public String removeAttributes(String inputStr) {
        int posStart = inputStr.indexOf(">");
        int posStop = 0;
        if (posStart < 0) {
            return inputStr;
        }
        String outputStr = inputStr.substring(posStart + 1);
        String responseTag = inputStr.substring(0, posStart);
        posStop = responseTag.indexOf(" ");
        if (posStop < 0) {
            return inputStr;
        }
        responseTag = responseTag.substring(0, posStop);
        return String.valueOf(responseTag) + ">" + outputStr;
    }

    public String tagData(String inputStr) {
        int posStart = inputStr.indexOf(">");
        int posStop = 0;
        if (posStart < 0) {
            return inputStr;
        }
        posStop = (inputStr = inputStr.substring(posStart + 1)).lastIndexOf("<");
        if (posStop < 0) {
            return inputStr;
        }
        return inputStr.substring(0, posStop);
    }

    public String startTag(String inputStr) {
        int posStart = inputStr.indexOf(">");
        if (posStart < 0) {
            return inputStr;
        }
        return inputStr.substring(0, posStart + 1);
    }

    public String stopTag(String inputStr) {
        int posStop = inputStr.lastIndexOf("<");
        if (posStop < 0) {
            return inputStr;
        }
        return inputStr.substring(posStop, inputStr.length());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        InputStream inputStream = in;
        synchronized (inputStream) {
            OutputStream outputStream = out;
            synchronized (outputStream) {
                int bytesRead;
                byte[] buffer = new byte[256];
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
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

