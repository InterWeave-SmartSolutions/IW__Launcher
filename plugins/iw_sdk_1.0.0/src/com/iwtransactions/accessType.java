/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import com.iwtransactions.parameterlist;
import com.iwtransactions.translatorType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class accessType
extends com.altova.xml.Node {
    public accessType() {
    }

    public accessType(accessType node) {
        super(node);
    }

    public accessType(Node node) {
        super(node);
    }

    public accessType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(0, null, "type");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "type", i);
            accessType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(1, null, "statementpre");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "statementpre", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "statementpost");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "statementpost", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "translator");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "translator", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            new translatorType(tmpNode).adjustPrefix();
            ++i;
        }
        count = this.getDomChildCount(1, null, "where");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "where", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            new parameterlist(tmpNode).adjustPrefix();
            ++i;
        }
        count = this.getDomChildCount(1, null, "values");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "values", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            new parameterlist(tmpNode).adjustPrefix();
            ++i;
        }
        count = this.getDomChildCount(1, null, "outputs");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "outputs", i);
            accessType.internalAdjustPrefix(tmpNode, true);
            new parameterlist(tmpNode).adjustPrefix();
            ++i;
        }
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
        return new SchemaString(accessType.getDomNodeValue(this.getDomChildAt(0, null, "type", index)));
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

    public int getstatementpreMinCount() {
        return 1;
    }

    public int getstatementpreMaxCount() {
        return 1;
    }

    public int getstatementpreCount() {
        return this.getDomChildCount(1, null, "statementpre");
    }

    public boolean hasstatementpre() {
        return this.hasDomChild(1, null, "statementpre");
    }

    public SchemaString getstatementpreAt(int index) throws Exception {
        return new SchemaString(accessType.getDomNodeValue(this.getDomChildAt(1, null, "statementpre", index)));
    }

    public SchemaString getstatementpre() throws Exception {
        return this.getstatementpreAt(0);
    }

    public void removestatementpreAt(int index) {
        this.removeDomChildAt(1, null, "statementpre", index);
    }

    public void removestatementpre() {
        while (this.hasstatementpre()) {
            this.removestatementpreAt(0);
        }
    }

    public void addstatementpre(SchemaString value) {
        this.appendDomChild(1, null, "statementpre", value.toString());
    }

    public void addstatementpre(String value) throws Exception {
        this.addstatementpre(new SchemaString(value));
    }

    public void insertstatementpreAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "statementpre", index, value.toString());
    }

    public void insertstatementpreAt(String value, int index) throws Exception {
        this.insertstatementpreAt(new SchemaString(value), index);
    }

    public void replacestatementpreAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "statementpre", index, value.toString());
    }

    public void replacestatementpreAt(String value, int index) throws Exception {
        this.replacestatementpreAt(new SchemaString(value), index);
    }

    public int getstatementpostMinCount() {
        return 1;
    }

    public int getstatementpostMaxCount() {
        return 1;
    }

    public int getstatementpostCount() {
        return this.getDomChildCount(1, null, "statementpost");
    }

    public boolean hasstatementpost() {
        return this.hasDomChild(1, null, "statementpost");
    }

    public SchemaString getstatementpostAt(int index) throws Exception {
        return new SchemaString(accessType.getDomNodeValue(this.getDomChildAt(1, null, "statementpost", index)));
    }

    public SchemaString getstatementpost() throws Exception {
        return this.getstatementpostAt(0);
    }

    public void removestatementpostAt(int index) {
        this.removeDomChildAt(1, null, "statementpost", index);
    }

    public void removestatementpost() {
        while (this.hasstatementpost()) {
            this.removestatementpostAt(0);
        }
    }

    public void addstatementpost(SchemaString value) {
        this.appendDomChild(1, null, "statementpost", value.toString());
    }

    public void addstatementpost(String value) throws Exception {
        this.addstatementpost(new SchemaString(value));
    }

    public void insertstatementpostAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "statementpost", index, value.toString());
    }

    public void insertstatementpostAt(String value, int index) throws Exception {
        this.insertstatementpostAt(new SchemaString(value), index);
    }

    public void replacestatementpostAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "statementpost", index, value.toString());
    }

    public void replacestatementpostAt(String value, int index) throws Exception {
        this.replacestatementpostAt(new SchemaString(value), index);
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

    public int getwhereMinCount() {
        return 0;
    }

    public int getwhereMaxCount() {
        return 1;
    }

    public int getwhereCount() {
        return this.getDomChildCount(1, null, "where");
    }

    public boolean haswhere() {
        return this.hasDomChild(1, null, "where");
    }

    public parameterlist getwhereAt(int index) throws Exception {
        return new parameterlist(this.getDomChildAt(1, null, "where", index));
    }

    public parameterlist getwhere() throws Exception {
        return this.getwhereAt(0);
    }

    public void removewhereAt(int index) {
        this.removeDomChildAt(1, null, "where", index);
    }

    public void removewhere() {
        while (this.haswhere()) {
            this.removewhereAt(0);
        }
    }

    public void addwhere(parameterlist value) {
        this.appendDomElement(null, "where", value);
    }

    public void insertwhereAt(parameterlist value, int index) {
        this.insertDomElementAt(null, "where", index, value);
    }

    public void replacewhereAt(parameterlist value, int index) {
        this.replaceDomElementAt(null, "where", index, value);
    }

    public int getvaluesMinCount() {
        return 0;
    }

    public int getvaluesMaxCount() {
        return 1;
    }

    public int getvaluesCount() {
        return this.getDomChildCount(1, null, "values");
    }

    public boolean hasvalues() {
        return this.hasDomChild(1, null, "values");
    }

    public parameterlist getvaluesAt(int index) throws Exception {
        return new parameterlist(this.getDomChildAt(1, null, "values", index));
    }

    public parameterlist getvalues() throws Exception {
        return this.getvaluesAt(0);
    }

    public void removevaluesAt(int index) {
        this.removeDomChildAt(1, null, "values", index);
    }

    public void removevalues() {
        while (this.hasvalues()) {
            this.removevaluesAt(0);
        }
    }

    public void addvalues(parameterlist value) {
        this.appendDomElement(null, "values", value);
    }

    public void insertvaluesAt(parameterlist value, int index) {
        this.insertDomElementAt(null, "values", index, value);
    }

    public void replacevaluesAt(parameterlist value, int index) {
        this.replaceDomElementAt(null, "values", index, value);
    }

    public int getoutputsMinCount() {
        return 0;
    }

    public int getoutputsMaxCount() {
        return 1;
    }

    public int getoutputsCount() {
        return this.getDomChildCount(1, null, "outputs");
    }

    public boolean hasoutputs() {
        return this.hasDomChild(1, null, "outputs");
    }

    public parameterlist getoutputsAt(int index) throws Exception {
        return new parameterlist(this.getDomChildAt(1, null, "outputs", index));
    }

    public parameterlist getoutputs() throws Exception {
        return this.getoutputsAt(0);
    }

    public void removeoutputsAt(int index) {
        this.removeDomChildAt(1, null, "outputs", index);
    }

    public void removeoutputs() {
        while (this.hasoutputs()) {
            this.removeoutputsAt(0);
        }
    }

    public void addoutputs(parameterlist value) {
        this.appendDomElement(null, "outputs", value);
    }

    public void insertoutputsAt(parameterlist value, int index) {
        this.insertDomElementAt(null, "outputs", index, value);
    }

    public void replaceoutputsAt(parameterlist value, int index) {
        this.replaceDomElementAt(null, "outputs", index, value);
    }
}

