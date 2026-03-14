/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lp.txn.JLinkPointTransaction;

public abstract class JLinkPointSample {
    protected String clientCertPath = "";
    protected String password = "";
    protected String host = "";
    protected String configfile = "";
    protected int port = 0;
    protected String R_Time = "";
    protected String R_Ref = "";
    protected String R_Approved = "";
    protected String R_Code = "";
    protected String R_Authresr = "";
    protected String R_Error = "";
    protected String R_OrderNum = "";
    protected String R_Message = "";
    protected String R_Score = "";
    protected String R_TDate = "";
    protected String R_AVS = "";
    protected String R_FraudCode = "";
    protected String R_ESD = "";
    protected String R_Tax = "";
    protected String R_Shipping = "";

    public JLinkPointSample(String _clientCertPath, String _configfile, String _host, String _password, int _port) {
        this.clientCertPath = _clientCertPath;
        this.password = _password;
        this.host = _host;
        this.configfile = _configfile;
        this.port = _port;
    }

    public JLinkPointSample() {
        FileInputStream is = null;
        try {
            try {
                is = new FileInputStream("lpcli.prop");
                Properties ps = new Properties();
                ps.load(is);
                this.clientCertPath = ps.getProperty("ClientCertificatePath", "");
                this.password = ps.getProperty("Password");
                this.host = ps.getProperty("Host");
                this.configfile = ps.getProperty("ConfigFile");
                this.port = Integer.parseInt((String)ps.get("Port"));
            }
            catch (IOException e) {
                System.out.println("Cannot find/load property file 'lpcli.prop'");
            }
        }
        finally {
            if (is != null) {
                try {
                    ((InputStream)is).close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public boolean process() {
        JLinkPointTransaction txn = new JLinkPointTransaction();
        txn.setClientCertificatePath(this.clientCertPath);
        txn.setPassword(this.password);
        txn.setHost(this.host);
        txn.setPort(this.port);
        System.out.println("OUTGOING XML:\n" + this.getOrderXML());
        String sResponse = "";
        boolean ret = false;
        try {
            sResponse = txn.send(this.getOrderXML());
        }
        catch (Exception e) {
            e.printStackTrace();
            sResponse = "<r_error>" + e.toString() + "</r_error>";
        }
        this.parseResponse(sResponse);
        this.printResp();
        return ret;
    }

    public String processTxn() {
        JLinkPointTransaction txn = new JLinkPointTransaction();
        txn.setClientCertificatePath(this.clientCertPath);
        txn.setPassword(this.password);
        txn.setHost(this.host);
        txn.setPort(this.port);
        System.out.println("OUTGOING XML:\n" + this.getOrderXML());
        String sResponse = "";
        try {
            sResponse = txn.send(this.getOrderXML());
        }
        catch (Exception e) {
            e.printStackTrace();
            sResponse = "<r_error>" + e.toString() + "</r_error>";
        }
        this.parseResponse(sResponse);
        this.printResp();
        return "<Saleminresult>" + sResponse + "</Saleminresult>";
    }

    protected abstract String getOrderXML();

    protected void parseResponse(String rsp) {
        this.R_Time = this.parseTag("r_time", rsp);
        this.R_Ref = this.parseTag("r_ref", rsp);
        this.R_Approved = this.parseTag("r_approved", rsp);
        this.R_Code = this.parseTag("r_code", rsp);
        this.R_Authresr = this.parseTag("r_authresronse", rsp);
        this.R_Error = this.parseTag("r_error", rsp);
        this.R_OrderNum = this.parseTag("r_ordernum", rsp);
        this.R_Message = this.parseTag("r_message", rsp);
        this.R_Score = this.parseTag("r_score", rsp);
        this.R_TDate = this.parseTag("r_tdate", rsp);
        this.R_AVS = this.parseTag("r_avs", rsp);
        this.R_Tax = this.parseTag("r_tax", rsp);
        this.R_Shipping = this.parseTag("r_shipping", rsp);
        this.R_FraudCode = this.parseTag("r_fraudCode", rsp);
        this.R_ESD = this.parseTag("esd", rsp);
    }

    protected String parseTag(String tag, String rsp) {
        StringBuffer sb = new StringBuffer(256);
        sb.append(String.valueOf('<') + tag + '>');
        int len = sb.length();
        int idxSt = -1;
        int idxEnd = -1;
        idxSt = rsp.indexOf(sb.toString());
        if (-1 == idxSt) {
            return "";
        }
        sb.setLength(0);
        sb.append("</" + tag + '>');
        idxEnd = rsp.indexOf(sb.toString(), idxSt += len);
        if (-1 == idxEnd) {
            return "";
        }
        return rsp.substring(idxSt, idxEnd);
    }

    public void printResp() {
        System.out.println("\n******  XML RESPONSE  *******");
        if (this.R_Time.length() != 0) {
            System.out.println("<r_time>" + this.R_Time + "</r_time>");
        }
        if (this.R_Ref.length() != 0) {
            System.out.println("<r_ref>" + this.R_Ref + "</r_ref>");
        }
        if (this.R_Approved.length() != 0) {
            System.out.println("<r_approved>" + this.R_Approved + "</r_approved>");
        }
        if (this.R_Code.length() != 0) {
            System.out.println("<r_code>" + this.R_Code + "</r_code>");
        }
        if (this.R_OrderNum.length() != 0) {
            System.out.println("<r_ordernum>" + this.R_OrderNum + "</r_ordernum>");
        }
        if (this.R_Error.length() != 0) {
            System.out.println("<r_error>" + this.R_Error + "</r_error>");
        }
        if (this.R_FraudCode.length() != 0) {
            System.out.println("<r_fraudCode>" + this.R_FraudCode + "</r_fraudCode>");
        }
        if (this.R_Authresr.length() != 0) {
            System.out.println("<r_authresponse>" + this.R_Authresr + "</r_authresponse>");
        }
        if (this.R_Message.length() != 0) {
            System.out.println("<r_message>" + this.R_Message + "</r_message>");
        }
        if (this.R_TDate.length() != 0) {
            System.out.println("<r_tdate>" + this.R_TDate + "</r_tdate>");
        }
        if (this.R_Shipping.length() != 0) {
            System.out.println("<r_shipping>" + this.R_Shipping + "</r_shipping>");
        }
        if (this.R_Tax.length() != 0) {
            System.out.println("<r_tax>" + this.R_Tax + "</r_tax>");
        }
        if (this.R_AVS.length() != 0) {
            System.out.println("<r_avs>" + this.R_AVS + "</r_avs>");
        }
        if (this.R_ESD.length() != 0) {
            System.out.println("<esd>" + this.R_ESD + "</esd>");
        }
        if (this.R_Score.length() != 0) {
            System.out.println("<r_score>" + this.R_Score + "</r_score>");
        }
        System.out.println("\n****** End Of XML RESPONSE ********");
    }
}

