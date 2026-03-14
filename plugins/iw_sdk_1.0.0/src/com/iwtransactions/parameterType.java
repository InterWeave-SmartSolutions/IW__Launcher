/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import com.iwtransactions.mappingType;
import com.iwtransactions.translatorType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class parameterType
extends com.altova.xml.Node {
    public parameterType() {
    }

    public parameterType(parameterType node) {
        super(node);
    }

    public parameterType(Node node) {
        super(node);
    }

    public parameterType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(1, null, "translator");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "translator", i);
            parameterType.internalAdjustPrefix(tmpNode, true);
            new translatorType(tmpNode).adjustPrefix();
            ++i;
        }
        count = this.getDomChildCount(1, null, "input");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "input", i);
            parameterType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "mapping");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "mapping", i);
            parameterType.internalAdjustPrefix(tmpNode, true);
            new mappingType(tmpNode).adjustPrefix();
            ++i;
        }
    }

    public int gettranslatorMinCount() {
        return 0;
    }

    public int gettranslatorMaxCount() {
        return 1;
    }

    public int gettranslatorCount() {
        return this.getDomChildCount(1, null, "translator");
    }

    public boolean hastranslator() {
        return this.hasDomChild(1, null, "translator");
    }

    public translatorType gettranslatorAt(int index) throws Exception {
        return new translatorType(this.getDomChildAt(1, null, "translator", index));
    }

    public translatorType gettranslator() throws Exception {
        return this.gettranslatorAt(0);
    }

    public void removetranslatorAt(int index) {
        this.removeDomChildAt(1, null, "translator", index);
    }

    public void removetranslator() {
        while (this.hastranslator()) {
            this.removetranslatorAt(0);
        }
    }

    public void addtranslator(translatorType value) {
        this.appendDomElement(null, "translator", value);
    }

    public void inserttranslatorAt(translatorType value, int index) {
        this.insertDomElementAt(null, "translator", index, value);
    }

    public void replacetranslatorAt(translatorType value, int index) {
        this.replaceDomElementAt(null, "translator", index, value);
    }

    public int getinputMinCount() {
        return 1;
    }

    public int getinputMaxCount() {
        return 1;
    }

    public int getinputCount() {
        return this.getDomChildCount(1, null, "input");
    }

    public boolean hasinput() {
        return this.hasDomChild(1, null, "input");
    }

    public SchemaString getinputAt(int index) throws Exception {
        return new SchemaString(parameterType.getDomNodeValue(this.getDomChildAt(1, null, "input", index)));
    }

    public SchemaString getinput() throws Exception {
        return this.getinputAt(0);
    }

    public void removeinputAt(int index) {
        this.removeDomChildAt(1, null, "input", index);
    }

    public void removeinput() {
        while (this.hasinput()) {
            this.removeinputAt(0);
        }
    }

    public void addinput(SchemaString value) {
        this.appendDomChild(1, null, "input", value.toString());
    }

    public void addinput(String value) throws Exception {
        this.addinput(new SchemaString(value));
    }

    public void insertinputAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "input", index, value.toString());
    }

    public void insertinputAt(String value, int index) throws Exception {
        this.insertinputAt(new SchemaString(value), index);
    }

    public void replaceinputAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "input", index, value.toString());
    }

    public void replaceinputAt(String value, int index) throws Exception {
        this.replaceinputAt(new SchemaString(value), index);
    }

    public int getmappingMinCount() {
        return 1;
    }

    public int getmappingMaxCount() {
        return 1;
    }

    public int getmappingCount() {
        return this.getDomChildCount(1, null, "mapping");
    }

    public boolean hasmapping() {
        return this.hasDomChild(1, null, "mapping");
    }

    public mappingType getmappingAt(int index) throws Exception {
        return new mappingType(this.getDomChildAt(1, null, "mapping", index));
    }

    public mappingType getmapping() throws Exception {
        return this.getmappingAt(0);
    }

    public void removemappingAt(int index) {
        this.removeDomChildAt(1, null, "mapping", index);
    }

    public void removemapping() {
        while (this.hasmapping()) {
            this.removemappingAt(0);
        }
    }

    public void addmapping(mappingType value) {
        this.appendDomElement(null, "mapping", value);
    }

    public void insertmappingAt(mappingType value, int index) {
        this.insertDomElementAt(null, "mapping", index, value);
    }

    public void replacemappingAt(mappingType value, int index) {
        this.replaceDomElementAt(null, "mapping", index, value);
    }
}

