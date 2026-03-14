/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class POSTAUTH
extends JLinkPointSample {
    private String oid = "";

    public POSTAUTH() {
    }

    public POSTAUTH(String _oid) {
        this.oid = _oid;
    }

    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("result", "GOOD");
        op.put("ordertype", "POSTAUTH");
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
        op.put("chargetotal", "12.99");
        order.addPart("payment", op);
        op.clear();
        op.put("oid", this.oid);
        order.addPart("transactiondetails", op);
        return order.toXML();
    }
}

