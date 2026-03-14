/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class SALE_MININFO
extends JLinkPointSample {
    private String addrnum;
    private String zip;
    private String cardnumber;
    private String cardexpmonth;
    private String cardexpyear;
    private String chargetotal;
    private String cvmvalue;
    private String mode = null;

    public SALE_MININFO() {
    }

    public SALE_MININFO(String _clientCertPath, String _configfile, String _host, String _password, int _port, String _mode) {
        super(_clientCertPath, _configfile, _host, _password, _port);
        this.mode = _mode;
    }

    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("ordertype", "SALE");
        op.put("result", this.mode);
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("zip", this.zip);
        op.put("addrnum", this.addrnum);
        order.addPart("billing", op);
        op.clear();
        op.put("cardnumber", this.cardnumber);
        op.put("cardexpmonth", this.cardexpmonth);
        op.put("cardexpyear", this.cardexpyear);
        op.put("cvmvalue", this.cvmvalue);
        op.put("cvmindicator", "provided");
        order.addPart("creditcard", op);
        op.clear();
        op.put("chargetotal", this.chargetotal);
        order.addPart("payment", op);
        return order.toXML();
    }

    public void setAddrnum(String addrnum) {
        this.addrnum = addrnum;
    }

    public void setCardexpmonth(String cardexpmonth) {
        this.cardexpmonth = cardexpmonth;
    }

    public void setCardexpyear(String cardexpyear) {
        this.cardexpyear = cardexpyear;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public void setChargetotal(String chargetotal) {
        this.chargetotal = chargetotal;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCvmvalue(String cvmvalue) {
        this.cvmvalue = cvmvalue;
    }
}

