/*
 * Decompiled with CFR 0.152.
 */
package lp.order;

import lp.order.LPOrderPart;
import lp.order.LPOrderPartImp;

public class LPOrderFactory {
    public static LPOrderPart createOrderPart(String name) {
        return new LPOrderPartImp(name);
    }

    public static LPOrderPart createOrderPart() {
        return new LPOrderPartImp("");
    }
}

