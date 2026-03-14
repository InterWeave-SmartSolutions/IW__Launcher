/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class TAX
extends JLinkPointSample {
    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("ordertype", "CALCTAX");
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("subtotal", "12.99");
        order.addPart("payment", op);
        op.clear();
        op.put("zip", "12345");
        op.put("total", "12.99");
        op.put("state", "CA");
        order.addPart("shipping", op);
        return order.toXML();
    }
}

