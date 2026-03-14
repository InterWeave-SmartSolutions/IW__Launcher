/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaBoolean;
import com.altova.types.SchemaString;
import com.altova.types.SchemaType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class mappingType
extends com.altova.xml.Node {
    public mappingType() {
    }

    public mappingType(mappingType node) {
        super(node);
    }

    public mappingType(Node node) {
        super(node);
    }

    public mappingType(Document doc) {
        super(doc);
    }

    public SchemaString getValue() {
        return new SchemaString(mappingType.getDomNodeValue(this.domNode));
    }

    public void setValue(SchemaType value) {
        mappingType.setDomNodeValue(this.domNode, value.toString());
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(0, null, "quoted");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "quoted", i);
            mappingType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(0, null, "type");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "type", i);
            mappingType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
    }

    public int getquotedMinCount() {
        return 1;
    }

    public int getquotedMaxCount() {
        return 1;
    }

    public int getquotedCount() {
        return this.getDomChildCount(0, null, "quoted");
    }

    public boolean hasquoted() {
        return this.hasDomChild(0, null, "quoted");
    }

    public SchemaBoolean getquotedAt(int index) throws Exception {
        return new SchemaBoolean(mappingType.getDomNodeValue(this.getDomChildAt(0, null, "quoted", index)));
    }

    public SchemaBoolean getquoted() throws Exception {
        return this.getquotedAt(0);
    }

    public void removequotedAt(int index) {
        this.removeDomChildAt(0, null, "quoted", index);
    }

    public void removequoted() {
        while (this.hasquoted()) {
            this.removequotedAt(0);
        }
    }

    public void addquoted(SchemaBoolean value) {
        this.appendDomChild(0, null, "quoted", value.toString());
    }

    public void addquoted(String value) throws Exception {
        this.addquoted(new SchemaBoolean(value));
    }

    public void insertquotedAt(SchemaBoolean value, int index) {
        this.insertDomChildAt(0, null, "quoted", index, value.toString());
    }

    public void insertquotedAt(String value, int index) throws Exception {
        this.insertquotedAt(new SchemaBoolean(value), index);
    }

    public void replacequotedAt(SchemaBoolean value, int index) {
        this.replaceDomChildAt(0, null, "quoted", index, value.toString());
    }

    public void replacequotedAt(String value, int index) throws Exception {
        this.replacequotedAt(new SchemaBoolean(value), index);
    }

    public int gettypeMinCount() {
        return 0;
    }

    public int gettypeMaxCount() {
        return 1;
    }

    public int gettypeCount() {
        return this.getDomChildCount(0, null, "type");
    }

    public boolean hastype() {
        return this.hasDomChild(0, null, "type");
    }

    public SchemaString gettypeAt(int index) throws Exception {
        return new SchemaString(mappingType.getDomNodeValue(this.getDomChildAt(0, null, "type", index)));
    }

    public SchemaString gettype() throws Exception {
        return this.gettypeAt(0);
    }

    public void removetypeAt(int index) {
        this.removeDomChildAt(0, null, "type", index);
    }

    public void removetype() {
        while (this.hastype()) {
            this.removetypeAt(0);
        }
    }

    public void addtype(SchemaString value) {
        this.appendDomChild(0, null, "type", value.toString());
    }

    public void addtype(String value) throws Exception {
        this.addtype(new SchemaString(value));
    }

    public void inserttypeAt(SchemaString value, int index) {
        this.insertDomChildAt(0, null, "type", index, value.toString());
    }

    public void inserttypeAt(String value, int index) throws Exception {
        this.inserttypeAt(new SchemaString(value), index);
    }

    public void replacetypeAt(SchemaString value, int index) {
        this.replaceDomChildAt(0, null, "type", index, value.toString());
    }

    public void replacetypeAt(String value, int index) throws Exception {
        this.replacetypeAt(new SchemaString(value), index);
    }
}

