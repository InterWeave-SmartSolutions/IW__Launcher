/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

import java.util.ArrayList;
import java.util.List;

public class IWTransaction {
    public String transactionName = null;
    private ArrayList _DataMaps = new ArrayList();

    public List getDataMaps() {
        return this._DataMaps;
    }

    public String marshal() {
        String response = null;
        StringBuffer dataMaps2 = null;
        StringBuffer buffer = new StringBuffer();
        buffer.append("  <transaction name=\"" + this.transactionName + "\">\n");
        for (StringBuffer dataMaps2 : this._DataMaps) {
            buffer.append(dataMaps2.toString());
        }
        buffer.append("  </transaction>\n");
        response = buffer.toString();
        buffer = null;
        return response;
    }

    public void clear() {
        StringBuffer dataMaps2 = null;
        for (StringBuffer dataMaps2 : this._DataMaps) {
            this._DataMaps.remove(dataMaps2);
            dataMaps2 = null;
        }
    }
}

