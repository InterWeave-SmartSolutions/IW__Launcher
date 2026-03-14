/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl;

import com.interweave.core.IWTagStream;
import com.interweave.developer.wsdl.SoapRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RawMessageSender {
    private String urlString = null;
    private String soapAction = null;
    private boolean prettyPrint = false;
    private URL url = null;
    private HttpURLConnection connection = null;

    public RawMessageSender(String aUrlString) throws Exception {
        this.urlString = aUrlString;
        this.url = new URL(this.urlString);
    }

    public void connect() throws Exception {
        this.connection = (HttpURLConnection)this.url.openConnection();
    }

    private String send() throws Exception {
        String response = null;
        this.connection.connect();
        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] responseBytes = null;
        try {
            int c;
            inputStream = this.connection.getInputStream();
            while ((c = inputStream.read()) != -1) {
                baos.write(c);
            }
            responseBytes = baos.toByteArray();
        }
        catch (IOException ioe) {
            int c;
            inputStream = this.connection.getErrorStream();
            while ((c = inputStream.read()) != -1) {
                baos.write(c);
            }
            responseBytes = baos.toByteArray();
        }
        response = new String(responseBytes);
        return response;
    }

    public String sendSoap(SoapRequest soapRequest) throws Exception {
        String message = soapRequest.getXML();
        this.url = new URL(soapRequest.getParser().getLocation());
        this.connect();
        this.connection.setRequestProperty("Content-Length", String.valueOf(message.length()));
        this.connection.setRequestProperty("Content-Type", "text/xml");
        this.connection.setRequestProperty("Connection", "Close");
        this.connection.setRequestProperty("SoapAction", soapRequest.getParser().getSoapAction(soapRequest.getMethodName()));
        this.connection.setDoOutput(true);
        PrintWriter pw = new PrintWriter(this.connection.getOutputStream());
        pw.write(message);
        pw.flush();
        String soapOut = this.send();
        IWTagStream tag = new IWTagStream();
        soapOut = tag.removeXMLDecl(soapOut);
        while (soapOut.startsWith("<SOAP") || soapOut.startsWith("<soap")) {
            soapOut = tag.tagRemove(soapOut).trim();
        }
        tag = null;
        soapOut = "<iwsoap>" + soapOut + "</iwsoap>";
        return soapOut;
    }

    public String getWSDL() throws Exception {
        this.connect();
        this.connection.setDoOutput(false);
        return this.send();
    }

    public String prettyPrint(String unformattedString) {
        try {
            String oneLine = RawMessageSender.trimXML(unformattedString);
            InputSource inputSource = new InputSource(new StringReader(oneLine));
            StringWriter writer = new StringWriter();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(inputSource);
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty("encoding", "ISO-8859-1");
            serializer.setOutputProperty("indent", "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            serializer.transform(domSource, streamResult);
            return writer.getBuffer().toString();
        }
        catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return unformattedString;
        }
        catch (SAXException e) {
            return unformattedString;
        }
        catch (FactoryConfigurationError e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return unformattedString;
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return unformattedString;
        }
        catch (TransformerConfigurationException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return unformattedString;
        }
        catch (TransformerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return unformattedString;
        }
    }

    /*
     * Unable to fully structure code
     */
    private static String trimXML(String xml) {
        len = xml.length();
        result = new StringBuffer(len);
        pos = 0;
        ** GOTO lbl31
        {
            ++pos;
            do {
                if (pos < len && RawMessageSender.isSpace(xml.charAt(pos))) continue block0;
                if (pos >= len) break block0;
                startCopy = pos;
                while (pos < len && xml.charAt(pos) != '<') {
                    ++pos;
                }
                nextLess = pos--;
                while (pos >= startCopy && RawMessageSender.isSpace(xml.charAt(pos))) {
                    --pos;
                }
                endCopy = pos;
                if (endCopy >= startCopy) {
                    result.append(xml.substring(startCopy, endCopy + 1));
                }
                if (nextLess >= len) break block0;
                pos = nextLess;
                while (pos < len && xml.charAt(pos) != '>') {
                    ++pos;
                }
                nextGreater = pos;
                if (nextGreater >= nextLess) {
                    result.append(xml.substring(nextLess, nextGreater + 1));
                }
                pos = nextGreater + 1;
lbl31:
                // 2 sources

            } while (pos < len);
        }
        return result.toString();
    }

    private static boolean isSpace(char c) {
        return " \t\r\n".indexOf(c) != -1;
    }
}

