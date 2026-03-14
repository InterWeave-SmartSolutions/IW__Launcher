/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.socket;

import com.interweave.adapter.http.IWXMLHierarchicalAdaptor;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IWSocketBaseAdaptor
extends IWXMLHierarchicalAdaptor {
    protected DataType fileType;
    protected SocketCommand fileCommand;
    protected Socket dataSocket;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setup(Datamap map, IWRequest request) throws Exception {
        request.lnkIWServices.logConsole("IWSocketBaseAdaptor - setup started!", IWServices.LOG_TRACE, request);
        this.setupInitConnect(map, request);
        String ct = this.contentType.trim();
        if (ct.length() <= 0) throw new Exception("Socket type/command is missing.");
        String[] tpc = ct.split(";");
        if (tpc.length != 2) {
            throw new Exception("Incorrect Socket type/command format");
        }
        if (tpc[0].equalsIgnoreCase(DataType.TXT.getLabel())) {
            this.fileType = DataType.TXT;
        } else {
            if (!tpc[0].equalsIgnoreCase(DataType.XML.getLabel())) throw new Exception("Unknown Data type " + tpc[0]);
            this.fileType = DataType.XML;
        }
        if (tpc[1].equalsIgnoreCase(SocketCommand.READ.getLabel())) {
            this.fileCommand = SocketCommand.READ;
        } else {
            if (!tpc[1].equalsIgnoreCase(SocketCommand.WRITE.getLabel())) throw new Exception("Unknown Socket command " + tpc[1]);
            this.fileCommand = SocketCommand.WRITE;
        }
        try {
            this.connect2SocketServer(request);
        }
        catch (Exception e) {
            request.setConnectionError(true);
            request.setConnectionFailures(request.getConnectionFailures() + 1);
            request.setQueryStartTime(request.getInitialQueryStartTime());
            throw e;
        }
        request.lnkIWServices.logConsole("IWSocketBaseAdaptor - setup ended!", IWServices.LOG_TRACE, request);
    }

    private void connect2SocketServer(IWRequest request) throws Exception {
        int port;
        String[] hostport = this.httpURL.split(":");
        if (hostport.length < 2) {
            throw new Exception("Socket host or port is missing.");
        }
        if (hostport.length < 2) {
            throw new Exception("Too many parameters!");
        }
        if (hostport[0].length() == 0) {
            throw new Exception("Socket host is missing.");
        }
        if (hostport[1].length() == 0) {
            throw new Exception("Socket port is missing.");
        }
        try {
            port = Integer.valueOf(hostport[1]);
        }
        catch (NumberFormatException e) {
            throw new Exception("Malformed Socket port: " + e.getMessage());
        }
        if (port < 0) {
            throw new Exception("Socket port is missing.");
        }
        this.dataSocket = new Socket(hostport[0], port);
    }

    public void closeConnection() {
        try {
            this.dataSocket.close();
        }
        catch (IOException e) {
            this.iwRequest.lnkIWServices.logError("IWSocketBaseAdaptor: Unable to close socket.", IWServices.LOG_TRACE, e, null);
        }
    }

    public StringBuffer go(IWRequest request) throws Exception {
        boolean xml;
        this.iwRequest = request;
        this.iwRequest.lnkIWServices.logConsole("IWSocketBaseAdaptor.go started!", IWServices.LOG_TRACE, this.iwRequest);
        boolean read = this.fileCommand == SocketCommand.READ;
        boolean bl = xml = this.fileType == DataType.XML;
        if (read) {
            int count;
            int bufferSize = 4096;
            char[] temp = new char[bufferSize];
            InputStreamReader ist = null;
            ist = new InputStreamReader(this.dataSocket.getInputStream());
            BufferedReader inb = new BufferedReader(ist);
            StringBuffer res = new StringBuffer();
            while ((count = inb.read(temp, 0, bufferSize)) != -1) {
                res.append(temp, 0, count);
            }
            inb.close();
            ist.close();
            if (xml) {
                return this.xml2DataMap(res.toString(), request);
            }
            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Text", res.toString()))));
        }
        String stpre = this.applyParaneters(xml, read);
        DataOutputStream ost = new DataOutputStream(this.dataSocket.getOutputStream());
        ost.writeBytes(stpre);
        ost.flush();
        ost.close();
        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Socket Write", "SUCCESS"))));
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum DataType {
        TXT("TXT"),
        XML("XML");

        private final String label;

        private DataType(String value) {
            this.label = value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum SocketCommand {
        READ("READ"),
        WRITE("WRITE");

        private final String label;

        private SocketCommand(String value) {
            this.label = value;
        }

        public String getLabel() {
            return this.label;
        }
    }
}

