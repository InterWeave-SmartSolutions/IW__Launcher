/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.order.LPOrderFactory;
import lp.order.LPOrderPart;
import lp.samples.JLinkPointSample;

public class MULTIPLE
extends JLinkPointSample {
    String[][] orders = new String[][]{{"1.11", "4111-1111-1111-1111", "01", "04", "123", "12345"}, {"2.22", "4111-1111-1111-1111", "02", "05", "234", "23456"}, {"3.33", "4111-1111-1111-1111", "03", "06", "345", "34567"}};
    private LPOrderPart order = LPOrderFactory.createOrderPart("order");
    private LPOrderPart op = LPOrderFactory.createOrderPart();
    private int idxTxn = 0;

    public boolean process() {
        this.op.put("ordertype", "PREAUTH");
        this.op.put("result", "GOOD");
        this.order.addPart("orderoptions", this.op);
        this.op.clear();
        this.op.put("configfile", this.configfile);
        this.order.addPart("merchantinfo", this.op);
        this.idxTxn = 0;
        while (this.idxTxn < 3) {
            super.process();
            ++this.idxTxn;
        }
        return true;
    }

    protected String getOrderXML() {
        this.op.clear();
        this.op.put("zip", this.orders[this.idxTxn][5]);
        this.op.put("addrnum", this.orders[this.idxTxn][4]);
        this.order.addPart("billing", this.op);
        this.op.clear();
        this.op.put("cardnumber", this.orders[this.idxTxn][1]);
        this.op.put("cardexpmonth", this.orders[this.idxTxn][2]);
        this.op.put("cardexpyear", this.orders[this.idxTxn][3]);
        this.order.addPart("creditcard", this.op);
        this.op.clear();
        this.op.put("chargetotal", this.orders[this.idxTxn][0]);
        this.order.addPart("payment", this.op);
        return this.order.toXML();
    }
}

