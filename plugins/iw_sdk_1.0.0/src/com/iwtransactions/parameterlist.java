/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.iwtransactions.parameterType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class parameterlist
extends com.altova.xml.Node {
    public parameterlist() {
    }

    public parameterlist(parameterlist node) {
        super(node);
    }

    public parameterlist(Node node) {
        super(node);
    }

    public parameterlist(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        int count = this.getDomChildCount(1, null, "parameter");
        int i = 0;
        while (i < count) {
            Node tmpNode = this.getDomChildAt(1, null, "parameter", i);
            parameterlist.internalAdjustPrefix(tmpNode, true);
            new parameterType(tmpNode).adjustPrefix();
            ++i;
        }
    }

    public int getparameterMinCount() {
        return 1;
    }

    public int getparameterMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getparameterCount() {
        return this.getDomChildCount(1, null, "parameter");
    }

    public boolean hasparameter() {
        return this.hasDomChild(1, null, "parameter");
    }

    public parameterType getparameterAt(int index) throws Exception {
        return new parameterType(this.getDomChildAt(1, null, "parameter", index));
    }

    public parameterType getparameter() throws Exception {
        return this.getparameterAt(0);
    }

    public void removeparameterAt(int index) {
        this.removeDomChildAt(1, null, "parameter", index);
    }

    public void removeparameter() {
        while (this.hasparameter()) {
            this.removeparameterAt(0);
        }
    }

    public void addparameter(parameterType value) {
        this.appendDomElement(null, "parameter", value);
    }

    public void insertparameterAt(parameterType value, int index) {
        this.insertDomElementAt(null, "parameter", index, value);
    }

    public void replaceparameterAt(parameterType value, int index) {
        this.replaceDomElementAt(null, "parameter", index, value);
    }
}

