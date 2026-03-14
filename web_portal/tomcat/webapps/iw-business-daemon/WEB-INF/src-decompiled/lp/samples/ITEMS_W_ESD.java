/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class ITEMS_W_ESD
extends JLinkPointSample {
    protected String getOrderXML() {
        LPOrderPart order = LPOrderFactory.createOrderPart("order");
        LPOrderPart op = LPOrderFactory.createOrderPart();
        op.put("ordertype", "SALE");
        op.put("result", "LIVE");
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
        op.put("subtotal", "45.98");
        op.put("tax", "0.32");
        op.put("shipping", "1.02");
        op.put("chargetotal", "47.32");
        order.addPart("payment", op);
        op.clear();
        op.put("zip", "12345");
        op.put("addrnum", "123");
        op.put("name", "Joe Customer");
        order.addPart("billing", op);
        LPOrderPart items = LPOrderFactory.createOrderPart();
        LPOrderPart item = LPOrderFactory.createOrderPart();
        LPOrderPart options = LPOrderFactory.createOrderPart();
        item.put("id", "123456-A98765");
        item.put("description", "Logo T-Shirt");
        item.put("quantity", "1");
        item.put("price", "12.99");
        item.put("serial", "0987654321");
        op.clear();
        op.put("name", "Color");
        op.put("value", "Red");
        options.addPart("option", op, 1);
        op.clear();
        op.put("name", "Size");
        op.put("value", "XL");
        options.addPart("option", op, 2);
        item.addPart("options", options);
        items.addPart("item", item, 1);
        item.clear();
        item.put("id", "123456-A98767");
        item.put("description", "Blast-Em Software");
        item.put("price", "32.99");
        item.put("quantity", "1");
        item.put("serial", "0987654321");
        item.put("esdtype", "softgood");
        item.put("softfile", "blastem.exe");
        items.addPart("item", item, 2);
        order.addPart("items", items);
        return order.toXML();
    }
}

