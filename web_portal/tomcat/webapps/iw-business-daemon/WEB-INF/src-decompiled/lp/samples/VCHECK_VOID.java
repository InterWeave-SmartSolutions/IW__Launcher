/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class VCHECK_VOID
extends JLinkPointSample {
    private String oid = "";

    public VCHECK_VOID() {
    }

    public VCHECK_VOID(String _oid) {
        this.oid = _oid;
    }

    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("ordertype", "VOID");
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("void", "1");
        order.addPart("telecheck", op);
        op.clear();
        op.put("chargetotal", "12.99");
        order.addPart("payment", op);
        op.clear();
        op.put("oid", this.oid);
        order.addPart("transactiondetails", op);
        return order.toXML();
    }
}

