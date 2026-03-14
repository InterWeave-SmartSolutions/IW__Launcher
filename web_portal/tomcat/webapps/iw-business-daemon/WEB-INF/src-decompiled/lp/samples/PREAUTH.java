/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class PREAUTH
extends JLinkPointSample {
    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("result", "GOOD");
        op.put("ordertype", "PREAUTH");
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
        op.put("chargetotal", "12.99");
        order.addPart("payment", op);
        return order.toXML();
    }
}

