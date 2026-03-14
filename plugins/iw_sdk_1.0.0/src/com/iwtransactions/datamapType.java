/*
 * Decompiled with CFR.
 */
package com.iwtransactions;

import com.altova.types.SchemaString;
import com.iwtransactions.accessType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class datamapType
extends com.altova.xml.Node {
    public datamapType() {
    }

    public datamapType(datamapType node) {
        super(node);
    }

    public datamapType(Node node) {
        super(node);
    }

    public datamapType(Document doc) {
        super(doc);
    }

    public void adjustPrefix() {
        Node tmpNode;
        int count = this.getDomChildCount(0, null, "name");
        int i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(0, null, "name", i);
            datamapType.internalAdjustPrefix(tmpNode, false);
            ++i;
        }
        count = this.getDomChildCount(1, null, "driver");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "driver", i);
            datamapType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "url");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "url", i);
            datamapType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "user");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "user", i);
            datamapType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "password");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "password", i);
            datamapType.internalAdjustPrefix(tmpNode, true);
            ++i;
        }
        count = this.getDomChildCount(1, null, "access");
        i = 0;
        while (i < count) {
            tmpNode = this.getDomChildAt(1, null, "access", i);
            datamapType.internalAdjustPrefix(tmpNode, true);
            new accessType(tmpNode).adjustPrefix();
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
        return new SchemaString(datamapType.getDomNodeValue(this.getDomChildAt(0, null, "name", index)));
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

    public int getdriverMinCount() {
        return 1;
    }

    public int getdriverMaxCount() {
        return 1;
    }

    public int getdriverCount() {
        return this.getDomChildCount(1, null, "driver");
    }

    public boolean hasdriver() {
        return this.hasDomChild(1, null, "driver");
    }

    public SchemaString getdriverAt(int index) throws Exception {
        return new SchemaString(datamapType.getDomNodeValue(this.getDomChildAt(1, null, "driver", index)));
    }

    public SchemaString getdriver() throws Exception {
        return this.getdriverAt(0);
    }

    public void removedriverAt(int index) {
        this.removeDomChildAt(1, null, "driver", index);
    }

    public void removedriver() {
        while (this.hasdriver()) {
            this.removedriverAt(0);
        }
    }

    public void adddriver(SchemaString value) {
        this.appendDomChild(1, null, "driver", value.toString());
    }

    public void adddriver(String value) throws Exception {
        this.adddriver(new SchemaString(value));
    }

    public void insertdriverAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "driver", index, value.toString());
    }

    public void insertdriverAt(String value, int index) throws Exception {
        this.insertdriverAt(new SchemaString(value), index);
    }

    public void replacedriverAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "driver", index, value.toString());
    }

    public void replacedriverAt(String value, int index) throws Exception {
        this.replacedriverAt(new SchemaString(value), index);
    }

    public int geturlMinCount() {
        return 1;
    }

    public int geturlMaxCount() {
        return 1;
    }

    public int geturlCount() {
        return this.getDomChildCount(1, null, "url");
    }

    public boolean hasurl() {
        return this.hasDomChild(1, null, "url");
    }

    public SchemaString geturlAt(int index) throws Exception {
        return new SchemaString(datamapType.getDomNodeValue(this.getDomChildAt(1, null, "url", index)));
    }

    public SchemaString geturl() throws Exception {
        return this.geturlAt(0);
    }

    public void removeurlAt(int index) {
        this.removeDomChildAt(1, null, "url", index);
    }

    public void removeurl() {
        while (this.hasurl()) {
            this.removeurlAt(0);
        }
    }

    public void addurl(SchemaString value) {
        this.appendDomChild(1, null, "url", value.toString());
    }

    public void addurl(String value) throws Exception {
        this.addurl(new SchemaString(value));
    }

    public void inserturlAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "url", index, value.toString());
    }

    public void inserturlAt(String value, int index) throws Exception {
        this.inserturlAt(new SchemaString(value), index);
    }

    public void replaceurlAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "url", index, value.toString());
    }

    public void replaceurlAt(String value, int index) throws Exception {
        this.replaceurlAt(new SchemaString(value), index);
    }

    public int getuserMinCount() {
        return 1;
    }

    public int getuserMaxCount() {
        return 1;
    }

    public int getuserCount() {
        return this.getDomChildCount(1, null, "user");
    }

    public boolean hasuser() {
        return this.hasDomChild(1, null, "user");
    }

    public SchemaString getuserAt(int index) throws Exception {
        return new SchemaString(datamapType.getDomNodeValue(this.getDomChildAt(1, null, "user", index)));
    }

    public SchemaString getuser() throws Exception {
        return this.getuserAt(0);
    }

    public void removeuserAt(int index) {
        this.removeDomChildAt(1, null, "user", index);
    }

    public void removeuser() {
        while (this.hasuser()) {
            this.removeuserAt(0);
        }
    }

    public void adduser(SchemaString value) {
        this.appendDomChild(1, null, "user", value.toString());
    }

    public void adduser(String value) throws Exception {
        this.adduser(new SchemaString(value));
    }

    public void insertuserAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "user", index, value.toString());
    }

    public void insertuserAt(String value, int index) throws Exception {
        this.insertuserAt(new SchemaString(value), index);
    }

    public void replaceuserAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "user", index, value.toString());
    }

    public void replaceuserAt(String value, int index) throws Exception {
        this.replaceuserAt(new SchemaString(value), index);
    }

    public int getpasswordMinCount() {
        return 1;
    }

    public int getpasswordMaxCount() {
        return 1;
    }

    public int getpasswordCount() {
        return this.getDomChildCount(1, null, "password");
    }

    public boolean haspassword() {
        return this.hasDomChild(1, null, "password");
    }

    public SchemaString getpasswordAt(int index) throws Exception {
        return new SchemaString(datamapType.getDomNodeValue(this.getDomChildAt(1, null, "password", index)));
    }

    public SchemaString getpassword() throws Exception {
        return this.getpasswordAt(0);
    }

    public void removepasswordAt(int index) {
        this.removeDomChildAt(1, null, "password", index);
    }

    public void removepassword() {
        while (this.haspassword()) {
            this.removepasswordAt(0);
        }
    }

    public void addpassword(SchemaString value) {
        this.appendDomChild(1, null, "password", value.toString());
    }

    public void addpassword(String value) throws Exception {
        this.addpassword(new SchemaString(value));
    }

    public void insertpasswordAt(SchemaString value, int index) {
        this.insertDomChildAt(1, null, "password", index, value.toString());
    }

    public void insertpasswordAt(String value, int index) throws Exception {
        this.insertpasswordAt(new SchemaString(value), index);
    }

    public void replacepasswordAt(SchemaString value, int index) {
        this.replaceDomChildAt(1, null, "password", index, value.toString());
    }

    public void replacepasswordAt(String value, int index) throws Exception {
        this.replacepasswordAt(new SchemaString(value), index);
    }

    public int getaccessMinCount() {
        return 1;
    }

    public int getaccessMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getaccessCount() {
        return this.getDomChildCount(1, null, "access");
    }

    public boolean hasaccess() {
        return this.hasDomChild(1, null, "access");
    }

    public accessType getaccessAt(int index) throws Exception {
        return new accessType(this.getDomChildAt(1, null, "access", index));
    }

    public accessType getaccess() throws Exception {
        return this.getaccessAt(0);
    }

    public void removeaccessAt(int index) {
        this.removeDomChildAt(1, null, "access", index);
    }

    public void removeaccess() {
        while (this.hasaccess()) {
            this.removeaccessAt(0);
        }
    }

    public void addaccess(accessType value) {
        this.appendDomElement(null, "access", value);
    }

    public void insertaccessAt(accessType value, int index) {
        this.insertDomElementAt(null, "access", index, value);
    }

    public void replaceaccessAt(accessType value, int index) {
        this.replaceDomElementAt(null, "access", index, value);
    }
}

