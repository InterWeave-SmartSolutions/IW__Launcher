/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.iwtransactions.transactionType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class iwmappingsType
extends com.altova.xml.Node {
    public iwmappingsType() {
    }

    public iwmappingsType(iwmappingsType node) {
        super(node);
    }

    public iwmappingsType(Node node) {
        super(node);
    }

    public iwmappingsType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        int count = this.getDomChildCount(1, null, "transaction");
        int i = 0;
        while (i < count) {
            Node tmpNode = this.getDomChildAt(1, null, "transaction", i);
            iwmappingsType.internalAdjustPrefix(tmpNode, true);
            new transactionType(tmpNode).adjustPrefix();
            ++i;
        }
    }

    public int gettransactionMinCount() {
        return 0;
    }

    public int gettransactionMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int gettransactionCount() {
        return this.getDomChildCount(1, null, "transaction");
    }

    public boolean hastransaction() {
        return this.hasDomChild(1, null, "transaction");
    }

    public transactionType gettransactionAt(int index) throws Exception {
        return new transactionType(this.getDomChildAt(1, null, "transaction", index));
    }

    public transactionType gettransaction() throws Exception {
        return this.gettransactionAt(0);
    }

    public void removetransactionAt(int index) {
        this.removeDomChildAt(1, null, "transaction", index);
    }

    public void removetransaction() {
        while (this.hastransaction()) {
            this.removetransactionAt(0);
        }
    }

    public void addtransaction(transactionType value) {
        this.appendDomElement(null, "transaction", value);
    }

    public void inserttransactionAt(transactionType value, int index) {
        this.insertDomElementAt(null, "transaction", index, value);
    }

    public void replacetransactionAt(transactionType value, int index) {
        this.replaceDomElementAt(null, "transaction", index, value);
    }
}

