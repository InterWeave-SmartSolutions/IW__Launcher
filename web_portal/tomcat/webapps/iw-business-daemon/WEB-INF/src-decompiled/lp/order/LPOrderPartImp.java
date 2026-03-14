/*
 * Decompiled with CFR 0.152.
 */
package lp.order;

import java.util.HashMap;
import java.util.Iterator;
import lp.order.LPOrderPart;

public class LPOrderPartImp
extends HashMap
implements LPOrderPart {
    protected String partName = "";

    protected LPOrderPartImp() {
    }

    protected LPOrderPartImp(String name) {
        this.partName = name;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartName(String _name) {
        this.partName = _name;
    }

    public void put(String key, String val) {
        super.put(key, val);
    }

    public String get(String key) {
        Object ret = super.get(key);
        if (ret != null && ret instanceof String) {
            return (String)ret;
        }
        return null;
    }

    public void remove(String key) {
        String ret = this.get(key);
        if (ret != null && ret instanceof String) {
            super.remove(key);
        }
    }

    public void removeAllByType(int type) {
        Iterator it = this.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            String cur = this.get(key);
            if (key == null || cur == null) {
                it.remove();
                continue;
            }
            if (type == 1 && cur != null && cur instanceof String) {
                it.remove();
                continue;
            }
            if (type != 2 || cur == null || !(cur instanceof LPOrderPart)) continue;
            it.remove();
        }
    }

    public void removeAll() {
        this.removeAllByType(1);
    }

    public void addPart(String key, LPOrderPart val) {
        this.addPart(key, val, -1);
    }

    public void addPart(String key, LPOrderPart o, int idx) {
        LPOrderPartImp val = (LPOrderPartImp)o;
        val.setPartName(key);
        if (idx > 0) {
            key = key + idx;
        }
        this.put(key, val.Clone());
    }

    public LPOrderPart getPart(String key) {
        return this.getPart(key, -1);
    }

    public LPOrderPart getPart(String key, int idx) {
        Object o;
        if (idx > 0) {
            key = key + idx;
        }
        if ((o = super.get(key)) != null && o instanceof LPOrderPart) {
            return ((LPOrderPartImp)o).Clone();
        }
        return null;
    }

    public void removePart(String key) {
        this.removePart(key, -1);
    }

    public void removePart(String key, int idx) {
        Object ret;
        if (idx > 0) {
            key = key + idx;
        }
        if ((ret = super.get(key)) != null && ret instanceof LPOrderPart) {
            super.remove(key);
        }
    }

    public void removeAllParts() {
        this.removeAllByType(2);
    }

    public String toXML() {
        StringBuffer xml = new StringBuffer(2048);
        this.buildPart(xml);
        return xml.toString();
    }

    public void buildPart(StringBuffer sb) {
        sb.append("\n<");
        sb.append(this.partName);
        sb.append(">");
        Object key = null;
        Object val = null;
        Iterator it = this.keySet().iterator();
        while (it.hasNext()) {
            key = it.next();
            val = super.get(key);
            if (val == null) continue;
            if (val instanceof String) {
                sb.append("\n<");
                sb.append((Object)key);
                sb.append(">");
                sb.append((Object)val);
                sb.append("</");
                sb.append((Object)key);
                sb.append(">");
                continue;
            }
            if (!(val instanceof LPOrderPart)) continue;
            ((LPOrderPartImp)val).buildPart(sb);
        }
        sb.append("\n</");
        sb.append(this.partName);
        sb.append(">");
    }

    protected LPOrderPartImp Clone() {
        LPOrderPartImp cl = new LPOrderPartImp(this.partName);
        Iterator it = this.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            Object val = super.get(key);
            if (val == null || key == null) continue;
            if (val instanceof String) {
                cl.put(key, val);
                continue;
            }
            if (!(val instanceof LPOrderPartImp)) continue;
            cl.put(key, ((LPOrderPartImp)val).Clone());
        }
        return cl;
    }
}

