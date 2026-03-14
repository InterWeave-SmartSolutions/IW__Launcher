/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import com.iwtransactions.datamapType;
import com.iwtransactions.nexttransactionType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class transactionType
extends com.altova.xml.Node {
    public transactionType() {
    }

    public transactionType(transactionType node) {
        super(node);
    }

    public transactionType(Node node) {
        super(node);
    }

    public transactionType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(0, null, "name");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "name", i);
            transactionType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(0, null, "type");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "type", i);
            transactionType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(1, null, "transform");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "transform", i);
            transactionType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "classname");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "classname", i);
            transactionType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "nexttransaction");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "nexttransaction", i);
            transactionType.internalAdjustPrefix(tmpNode, true);
            new nexttransactionType(tmpNode).adjustPrefix();
            ++i;
        }
        count = this.getDomChildCount(1, null, "datamap");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "datamap", i);
            transactionType.internalAdjustPrefix(tmpNode, true);
            new datamapType(tmpNode).adjustPrefix();
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
        return new SchemaString(transactionType.getDomNodeValue(this.getDomChildAt(0, null, "name", index)));
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
        return new SchemaString(transactionType.getDomNodeValue(this.getDomChildAt(0, null, "type", index)));
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

    public int gettransformMinCount() {
        return 1;
    }

    public int gettransformMaxCount() {
        return 1;
    }

    public int gettransformCount() {
        return this.getDomChildCount(1, null, "transform");
    }

    public boolean hastransform() {
        return this.hasDomChild(1, null, "transform");
    }

    public SchemaString gettransformAt(int index) throws Exception {
        return new SchemaString(transactionType.getDomNodeValue(this.getDomChildAt(1, null, "transform", index)));
    }

    public SchemaString gettransform() throws Exception {
        return this.gettransformAt(0);
    }

    public void removetransformAt(int index) {
        this.removeDomChildAt(1, null, "transform", index);
    }

    public void removetransform() {
        while (this.hastransform()) {
            this.removetransformAt(0);
        }
    }

    public void addtransform(SchemaString value) {
        this.appendDomChild(1, null, "transform", value.toString());
    }

    public void addtransform(String value) throws Exception {
        this.addtransform(new SchemaString(value));
    }

    public void inserttransformAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "transform", index, value.toString());
    }

    public void inserttransformAt(String value, int index) throws Exception {
        this.inserttransformAt(new SchemaString(value), index);
    }

    public void replacetransformAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "transform", index, value.toString());
    }

    public void replacetransformAt(String value, int index) throws Exception {
        this.replacetransformAt(new SchemaString(value), index);
    }

    public int getclassnameMinCount() {
        return 0;
    }

    public int getclassnameMaxCount() {
        return 1;
    }

    public int getclassnameCount() {
        return this.getDomChildCount(1, null, "classname");
    }

    public boolean hasclassname() {
        return this.hasDomChild(1, null, "classname");
    }

    public SchemaString getclassnameAt(int index) throws Exception {
        return new SchemaString(transactionType.getDomNodeValue(this.getDomChildAt(1, null, "classname", index)));
    }

    public SchemaString getclassname() throws Exception {
        return this.getclassnameAt(0);
    }

    public void removeclassnameAt(int index) {
        this.removeDomChildAt(1, null, "classname", index);
    }

    public void removeclassname() {
        while (this.hasclassname()) {
            this.removeclassnameAt(0);
        }
    }

    public void addclassname(SchemaString value) {
        this.appendDomChild(1, null, "classname", value.toString());
    }

    public void addclassname(String value) throws Exception {
        this.addclassname(new SchemaString(value));
    }

    public void insertclassnameAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "classname", index, value.toString());
    }

    public void insertclassnameAt(String value, int index) throws Exception {
        this.insertclassnameAt(new SchemaString(value), index);
    }

    public void replaceclassnameAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "classname", index, value.toString());
    }

    public void replaceclassnameAt(String value, int index) throws Exception {
        this.replaceclassnameAt(new SchemaString(value), index);
    }

    public int getnexttransactionMinCount() {
        return 0;
    }

    public int getnexttransactionMaxCount() {
        return 1;
    }

    public int getnexttransactionCount() {
        return this.getDomChildCount(1, null, "nexttransaction");
    }

    public boolean hasnexttransaction() {
        return this.hasDomChild(1, null, "nexttransaction");
    }

    public nexttransactionType getnexttransactionAt(int index) throws Exception {
        return new nexttransactionType(this.getDomChildAt(1, null, "nexttransaction", index));
    }

    public nexttransactionType getnexttransaction() throws Exception {
        return this.getnexttransactionAt(0);
    }

    public void removenexttransactionAt(int index) {
        this.removeDomChildAt(1, null, "nexttransaction", index);
    }

    public void removenexttransaction() {
        while (this.hasnexttransaction()) {
            this.removenexttransactionAt(0);
        }
    }

    public void addnexttransaction(nexttransactionType value) {
        this.appendDomElement(null, "nexttransaction", value);
    }

    public void insertnexttransactionAt(nexttransactionType value, int index) {
        this.insertDomElementAt(null, "nexttransaction", index, value);
    }

    public void replacenexttransactionAt(nexttransactionType value, int index) {
        this.replaceDomElementAt(null, "nexttransaction", index, value);
    }

    public int getdatamapMinCount() {
        return 0;
    }

    public int getdatamapMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getdatamapCount() {
        return this.getDomChildCount(1, null, "datamap");
    }

    public boolean hasdatamap() {
        return this.hasDomChild(1, null, "datamap");
    }

    public datamapType getdatamapAt(int index) throws Exception {
        return new datamapType(this.getDomChildAt(1, null, "datamap", index));
    }

    public datamapType getdatamap() throws Exception {
        return this.getdatamapAt(0);
    }

    public void removedatamapAt(int index) {
        this.removeDomChildAt(1, null, "datamap", index);
    }

    public void removedatamap() {
        while (this.hasdatamap()) {
            this.removedatamapAt(0);
        }
    }

    public void adddatamap(datamapType value) {
        this.appendDomElement(null, "datamap", value);
    }

    public void insertdatamapAt(datamapType value, int index) {
        this.insertDomElementAt(null, "datamap", index, value);
    }

    public void replacedatamapAt(datamapType value, int index) {
        this.replaceDomElementAt(null, "datamap", index, value);
    }
}

