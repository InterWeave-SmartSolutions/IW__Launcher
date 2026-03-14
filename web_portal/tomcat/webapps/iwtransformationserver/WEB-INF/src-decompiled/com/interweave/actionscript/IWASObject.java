/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.actionscript;

import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IWASObject
extends HashMap {
    private static final Log log = LogFactory.getLog(IWASObject.class);
    private String type;

    public IWASObject() {
    }

    public IWASObject(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    public Object get(Object key) {
        return super.get(key);
    }

    public Object put(Object key, Object value) {
        return super.put(key, value);
    }

    public Object remove(Object key) {
        return super.remove(key);
    }

    private Object toLowerCase(Object key) {
        if (key != null && key instanceof String) {
            key = ((String)key).toLowerCase();
        }
        return key;
    }

    public Object instantiate() {
        Object ret;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = loader.loadClass(this.type);
            ret = clazz.newInstance();
        }
        catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    public String toString() {
        if (log.isDebugEnabled()) {
            return "IWASObject[type=" + this.getType() + "," + super.toString() + "]";
        }
        return "IWASObject[type=" + this.getType() + "]";
    }
}

