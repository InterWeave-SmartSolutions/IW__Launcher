/*
 * Decompiled with CFR 0.152.
 */
package lp.txn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class JLinkPointTransaction {
    private String m_sClientCertPath = null;
    private String m_sPassword = null;
    private String m_sHost = null;
    private int m_iPort = 0;

    public JLinkPointTransaction() {
    }

    public JLinkPointTransaction(String sClientCertPath, String sPw, String sHost, int iPort) {
        this.m_sClientCertPath = sClientCertPath;
        this.m_sPassword = sPw;
        this.m_sHost = sHost;
        this.m_iPort = iPort;
    }

    private String validate() {
        String sRet = null;
        if (this.m_sClientCertPath == null || this.m_sClientCertPath.length() == 0) {
            sRet = "Client certificate file path has not been specified.";
        }
        if (this.m_sPassword == null || this.m_sPassword.length() == 0) {
            sRet = "Password has not been specified.";
        }
        if (this.m_sHost == null || this.m_sHost.length() == 0) {
            sRet = "Host has not been specified.";
        }
        if (this.m_iPort == 0) {
            sRet = "Port number has not been specified.";
        }
        if (sRet != null) {
            sRet = "<r_error>" + sRet + "</r_error>";
        }
        return sRet;
    }

    public void setClientCertificatePath(String s) {
        this.m_sClientCertPath = s;
    }

    public void setPassword(String s) {
        this.m_sPassword = s;
    }

    public void setHost(String s) {
        this.m_sHost = s;
    }

    public void setPort(int i) {
        this.m_iPort = i;
    }

    /*
     * WARNING - void declaration
     */
    public String send(String sXml) throws Exception {
        String sRet = this.validate();
        if (sRet != null) {
            return sRet;
        }
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        try {
            String sResponse;
            SSLSocketFactory factory = null;
            SSLContext ctx = SSLContext.getInstance("TLS");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, null);
            KeyStore ks2 = KeyStore.getInstance("PKCS12", "SunJSSE");
            FileInputStream fin = new FileInputStream(this.m_sClientCertPath);
            ks2.load(fin, this.m_sPassword.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SUNX509");
            kmf.init(ks2, this.m_sPassword.toCharArray());
            fin.close();
            ctx.init(kmf.getKeyManagers(), null, null);
            factory = ctx.getSocketFactory();
            socket = (SSLSocket)factory.createSocket(this.m_sHost, this.m_iPort);
            ((SSLSocket)socket).startHandshake();
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(sXml);
            out.println();
            out.flush();
            if (out.checkError()) {
                try {
                    out.close();
                }
                catch (Exception e1) {
                    // empty catch block
                }
                try {
                    socket.close();
                }
                catch (Exception e1) {
                    // empty catch block
                }
                return "<r_error>SSLSocketClient: java.io.PrintWriter error</r_error>";
            }
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String sResp = "";
            while ((sResponse = in.readLine()) != null) {
                void e1;
                if (e1 == null) continue;
                sResp = sResp + (String)e1;
            }
            in.close();
            out.close();
            socket.close();
            in = null;
            out = null;
            socket = null;
            return sResp;
        }
        catch (Exception e) {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e1) {
                // empty catch block
            }
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (Exception e1) {
                // empty catch block
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            }
            catch (Exception e1) {
                // empty catch block
            }
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            JLinkPointTransaction txn = new JLinkPointTransaction();
            String sClientCertPath = "c:/jsse-lsgs/909001.p12";
            txn.setClientCertificatePath(sClientCertPath);
            txn.setPassword("12345678");
            txn.setHost("secure.linkpt.net");
            txn.setPort(1119);
            String sXml = "<order><orderoptions><result>GOOD</result><ordertype>SALE</ordertype></orderoptions><merchantinfo><configfile>909001</configfile></merchantinfo><creditcard><cardnumber>4111111111111111</cardnumber><cardexpmonth>12</cardexpmonth><cardexpyear>08</cardexpyear></creditcard><payment><chargetotal>100.03</chargetotal></payment></order>";
            String sResponse = txn.send(sXml);
            System.out.println(sResponse);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

