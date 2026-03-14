/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class L2PURCHASING_CARD
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
        op.put("ponumber", "1203A-G4567");
        op.put("taxexempt", "N");
        order.addPart("transactiondetails", op);
        op.clear();
        op.put("cardnumber", "4111-1111-1111-1111");
        op.put("cardexpmonth", "03");
        op.put("cardexpyear", "05");
        order.addPart("creditcard", op);
        op.clear();
        op.put("chargetotal", "12.99");
        op.put("tax", "0.32");
        order.addPart("payment", op);
        return order.toXML();
    }
}

