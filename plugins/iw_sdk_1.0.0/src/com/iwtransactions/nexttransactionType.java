/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import com.altova.types.SchemaType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class nexttransactionType
extends com.altova.xml.Node {
    public nexttransactionType() {
    }

    public nexttransactionType(nexttransactionType node) {
        super(node);
    }

    public nexttransactionType(Node node) {
        super(node);
    }

    public nexttransactionType(Document doc) {
        super(doc);
    }

    public SchemaString getValue() {
        return new SchemaString(nexttransactionType.getDomNodeValue(this.domNode));
    }

    public void setValue(SchemaType value) {
        nexttransactionType.setDomNodeValue(this.domNode, value.toString());
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(0, null, "name");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "name", i);
            nexttransactionType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(0, null, "type");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "type", i);
            nexttransactionType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(0, null, "error");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "error", i);
            nexttransactionType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
    }

    public int getnameMinCount() {
        return 1;
    }

    public int getnameMaxCount() {
        return 1;
    }

    public int getnameCount() {
        return this.getDomChildCount(0, null, "name");
    }

    public boolean hasname() {
        return this.hasDomChild(0, null, "name");
    }

    public SchemaString getnameAt(int index) throws Exception {
        return new SchemaString(nexttransactionType.getDomNodeValue(this.getDomChildAt(0, null, "name", index)));
    }

    public SchemaString getname() throws Exception {
        return this.getnameAt(0);
    }

    public void removenameAt(int index) {
        this.removeDomChildAt(0, null, "name", index);
    }

    public void removename() {
        while (this.hasname()) {
            this.removenameAt(0);
        }
    }

    public void addname(SchemaString value) {
        this.appendDomChild(0, null, "name", value.toString());
    }

    public void addname(String value) throws Exception {
        this.addname(new SchemaString(value));
    }

    public void insertnameAt(SchemaString value, int index) {
        this.insertDomChildAt(0, null, "name", index, value.toString());
    }

    public void insertnameAt(String value, int index) throws Exception {
        this.insertnameAt(new SchemaString(value), index);
    }

    public void replacenameAt(SchemaString value, int index) {
        this.replaceDomChildAt(0, null, "name", index, value.toString());
    }

    public void replacenameAt(String value, int index) throws Exception {
        this.replacenameAt(new SchemaString(value), index);
    }

    public int gettypeMinCount() {
        return 1;
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
        return new SchemaString(nexttransactionType.getDomNodeValue(this.getDomChildAt(0, null, "type", index)));
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

    public int geterrorMinCount() {
        return 0;
    }

    public int geterrorMaxCount() {
        return 1;
    }

    public int geterrorCount() {
        return this.getDomChildCount(0, null, "error");
    }

    public boolean haserror() {
        return this.hasDomChild(0, null, "error");
    }

    public SchemaString geterrorAt(int index) throws Exception {
        return new SchemaString(nexttransactionType.getDomNodeValue(this.getDomChildAt(0, null, "error", index)));
    }

    public SchemaString geterror() throws Exception {
        return this.geterrorAt(0);
    }

    public void removeerrorAt(int index) {
        this.removeDomChildAt(0, null, "error", index);
    }

    public void removeerror() {
        while (this.haserror()) {
            this.removeerrorAt(0);
        }
    }

    public void adderror(SchemaString value) {
        this.appendDomChild(0, null, "error", value.toString());
    }

    public void adderror(String value) throws Exception {
        this.adderror(new SchemaString(value));
    }

    public void inserterrorAt(SchemaString value, int index) {
        this.insertDomChildAt(0, null, "error", index, value.toString());
    }

    public void inserterrorAt(String value, int index) throws Exception {
        this.inserterrorAt(new SchemaString(value), index);
    }

    public void replaceerrorAt(SchemaString value, int index) {
        this.replaceDomChildAt(0, null, "error", index, value.toString());
    }

    public void replaceerrorAt(String value, int index) throws Exception {
        this.replaceerrorAt(new SchemaString(value), index);
    }
}

