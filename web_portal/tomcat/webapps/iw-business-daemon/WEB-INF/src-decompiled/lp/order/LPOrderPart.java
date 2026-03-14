/*
 * Decompiled with CFR 0.152.
 */
package lp.order;

public interface LPOrderPart {
    public String getPartName();

    public void setPartName(String var1);

    public void put(String var1, String var2);

    public String get(String var1);

    public void remove(String var1);

    public void removeAll();

    public void addPart(String var1, LPOrderPart var2);

    public void addPart(String var1, LPOrderPart var2, int var3);

    public LPOrderPart getPart(String var1);

    public LPOrderPart getPart(String var1, int var2);

    public void removePart(String var1);

    public void removePart(String var1, int var2);

    public void removeAllParts();

    public String toXML();

    public void clear();
}

