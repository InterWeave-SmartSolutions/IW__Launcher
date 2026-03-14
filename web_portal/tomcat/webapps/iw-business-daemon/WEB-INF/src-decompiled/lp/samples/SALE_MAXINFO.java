/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class SALE_MAXINFO
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
        op.put("transactionorigin", "MOTO");
        op.put("ponumber", "09876543-Q1234");
        op.put("taxexempt", "N");
        op.put("terminaltype", "UNSPECIFIED");
        op.put("ip", "123.123.123.123");
        order.addPart("transactiondetails", op);
        op.clear();
        op.put("zip", "12345");
        op.put("addrnum", "123");
        op.put("name", "Joe Customer");
        op.put("company", "SomeWhere, Inc.");
        op.put("address1", "123 Broadway");
        op.put("address2", "Suite 23");
        op.put("city", "Moorpark");
        op.put("state", "CA");
        op.put("country", "US");
        op.put("phone", "8051234567");
        op.put("fax", "8059876543");
        op.put("email", "joe.customer@somewhere.com");
        order.addPart("billing", op);
        op.clear();
        op.put("name", "Joe Customer");
        op.put("address1", "123 Broadway");
        op.put("address2", "Suite 23");
        op.put("city", "Moorpark");
        op.put("state", "CA");
        op.put("country", "US");
        op.put("zip", "12345");
        order.addPart("shipping", op);
        op.clear();
        op.put("cardnumber", "4111-1111-1111-1111");
        op.put("cardexpmonth", "03");
        op.put("cardexpyear", "05");
        op.put("cvmvalue", "123");
        op.put("cvmindicator", "provided");
        order.addPart("creditcard", op);
        op.clear();
        op.put("subtotal", "12.99");
        op.put("tax", "0.34");
        op.put("shipping", "1.45");
        op.put("vattax", "0.00");
        op.put("chargetotal", "14.78");
        order.addPart("payment", op);
        op.clear();
        op.put("referred", "Saw ad on Web site.");
        op.put("comments", "Repeat customer. Ship immediately.");
        order.addPart("notes", op);
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
        order.addPart("items", items);
        return order.toXML();
    }
}

