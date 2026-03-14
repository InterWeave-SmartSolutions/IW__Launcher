/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class AVS_CVM
extends JLinkPointSample {
    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("ordertype", "SALE");
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("zip", "87123");
        op.put("addrnum", "123");
        order.addPart("billing", op);
        op.clear();
        op.put("cardnumber", "4111-1111-1111-1111");
        op.put("cardexpmonth", "03");
        op.put("cardexpyear", "05");
        op.put("cvmvalue", "123");
        op.put("cvmindicator", "provided");
        order.addPart("creditcard", op);
        op.clear();
        op.put("chargetotal", "12.99");
        order.addPart("payment", op);
        return order.toXML();
    }
}

