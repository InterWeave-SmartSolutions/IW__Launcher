/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class PB_CANCEL
extends JLinkPointSample {
    private String oid = "";

    public PB_CANCEL() {
    }

    public PB_CANCEL(String _oid) {
        this.oid = _oid;
    }

    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("result", "GOOD");
        op.put("ordertype", "SALE");
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("cardnumber", "4111-1111-1111-1111");
        op.put("cardexpmonth", "03");
        op.put("cardexpyear", "05");
        order.addPart("creditcard", op);
        op.clear();
        op.put("addrnum", "123");
        op.put("zip", "12345");
        order.addPart("billing", op);
        op.clear();
        op.put("chargetotal", "14.99");
        order.addPart("payment", op);
        op.clear();
        op.put("action", "CANCEL");
        op.put("startdate", "immediate");
        op.put("periodicity", "monthly");
        op.put("installments", "3");
        op.put("threshold", "3");
        order.addPart("periodic", op);
        op.clear();
        op.put("oid", this.oid);
        order.addPart("transactiondetails", op);
        return order.toXML();
    }
}

