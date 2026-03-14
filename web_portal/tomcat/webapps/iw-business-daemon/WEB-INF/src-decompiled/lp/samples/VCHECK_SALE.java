/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class VCHECK_SALE
extends JLinkPointSample {
    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("result", "LIVE");
        op.put("ordertype", "SALE");
        order.addPart("orderoptions", op);
        op.clear();
        op.put("configfile", this.configfile);
        order.addPart("merchantinfo", op);
        op.clear();
        op.put("name", "Joe Customer");
        op.put("address1", "123 Broadway");
        op.put("state", "CA");
        op.put("city", "Moorpark");
        op.put("zip", "12345");
        op.put("phone", "8051234567");
        op.put("email", "joe.customer@somewhere.com");
        order.addPart("billing", op);
        op.clear();
        op.put("routing", "123456789");
        op.put("account", "2139842610");
        op.put("accounttype", "pc");
        op.put("dl", "120381698");
        op.put("dlstate", "CA");
        op.put("bankname", "Wells Fargo");
        op.put("bankstate", "CA");
        order.addPart("telecheck", op);
        op.clear();
        op.put("chargetotal", "12.99");
        order.addPart("payment", op);
        return order.toXML();
    }
}

