/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class translatorType
extends com.altova.xml.Node {
    public translatorType() {
    }

    public translatorType(translatorType node) {
        super(node);
    }

    public translatorType(Node node) {
        super(node);
    }

    public translatorType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(1, null, "inputclass");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "inputclass", i);
            translatorType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "outputclass");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "outputclass", i);
            translatorType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
    }

    public int getinputclassMinCount() {
        return 0;
    }

    public int getinputclassMaxCount() {
        return 1;
    }

    public int getinputclassCount() {
        return this.getDomChildCount(1, null, "inputclass");
    }

    public boolean hasinputclass() {
        return this.hasDomChild(1, null, "inputclass");
    }

    public SchemaString getinputclassAt(int index) throws Exception {
        return new SchemaString(translatorType.getDomNodeValue(this.getDomChildAt(1, null, "inputclass", index)));
    }

    public SchemaString getinputclass() throws Exception {
        return this.getinputclassAt(0);
    }

    public void removeinputclassAt(int index) {
        this.removeDomChildAt(1, null, "inputclass", index);
    }

    public void removeinputclass() {
        while (this.hasinputclass()) {
            this.removeinputclassAt(0);
        }
    }

    public void addinputclass(SchemaString value) {
        this.appendDomChild(1, null, "inputclass", value.toString());
    }

    public void addinputclass(String value) throws Exception {
        this.addinputclass(new SchemaString(value));
    }

    public void insertinputclassAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "inputclass", index, value.toString());
    }

    public void insertinputclassAt(String value, int index) throws Exception {
        this.insertinputclassAt(new SchemaString(value), index);
    }

    public void replaceinputclassAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "inputclass", index, value.toString());
    }

    public void replaceinputclassAt(String value, int index) throws Exception {
        this.replaceinputclassAt(new SchemaString(value), index);
    }

    public int getoutputclassMinCount() {
        return 0;
    }

    public int getoutputclassMaxCount() {
        return 1;
    }

    public int getoutputclassCount() {
        return this.getDomChildCount(1, null, "outputclass");
    }

    public boolean hasoutputclass() {
        return this.hasDomChild(1, null, "outputclass");
    }

    public SchemaString getoutputclassAt(int index) throws Exception {
        return new SchemaString(translatorType.getDomNodeValue(this.getDomChildAt(1, null, "outputclass", index)));
    }

    public SchemaString getoutputclass() throws Exception {
        return this.getoutputclassAt(0);
    }

    public void removeoutputclassAt(int index) {
        this.removeDomChildAt(1, null, "outputclass", index);
    }

    public void removeoutputclass() {
        while (this.hasoutputclass()) {
            this.removeoutputclassAt(0);
        }
    }

    public void addoutputclass(SchemaString value) {
        this.appendDomChild(1, null, "outputclass", value.toString());
    }

    public void addoutputclass(String value) throws Exception {
        this.addoutputclass(new SchemaString(value));
    }

    public void insertoutputclassAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "outputclass", index, value.toString());
    }

    public void insertoutputclassAt(String value, int index) throws Exception {
        this.insertoutputclassAt(new SchemaString(value), index);
    }

    public void replaceoutputclassAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "outputclass", index, value.toString());
    }

    public void replaceoutputclassAt(String value, int index) throws Exception {
        this.replaceoutputclassAt(new SchemaString(value), index);
    }
}

