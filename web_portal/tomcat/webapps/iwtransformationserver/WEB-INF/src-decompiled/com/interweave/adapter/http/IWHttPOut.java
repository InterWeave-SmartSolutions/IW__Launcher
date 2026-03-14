/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;

public class IWHttPOut {
    protected Access curAccess = null;
    public String Url = "";
    public Hashtable props = new Hashtable();
    private String mapName = null;
    protected Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public String post(String message) throws Exception {
        StringBuffer outBuffer = new StringBuffer();
        URL url = new URL(this.Url);
        System.out.println("Connecting to: " + this.Url);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection)connection;
        httpConn.setRequestProperty("Content-Length", String.valueOf(message.length()));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        Enumeration enumerate = this.props.keys();
        while (enumerate.hasMoreElements()) {
            String key = (String)enumerate.nextElement();
            System.out.println("Set Key: " + key + " = " + (String)this.props.get(key));
            httpConn.setRequestProperty(key, (String)this.props.get(key));
        }
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        out.write(message.getBytes());
        out.close();
        try {
            String inputLine;
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            while ((inputLine = in.readLine()) != null) {
                outBuffer.append(inputLine);
            }
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Output: " + outBuffer.toString());
        return outBuffer.toString();
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

    public void setup(Datamap map) throws Exception {
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
}

