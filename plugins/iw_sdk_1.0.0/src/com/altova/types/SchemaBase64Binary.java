/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaBinaryBase;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeBinary;
import java.io.IOException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SchemaBase64Binary
extends SchemaBinaryBase {
    public SchemaBase64Binary() {
    }

    public SchemaBase64Binary(SchemaBase64Binary newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaBase64Binary(byte[] newvalue) {
        this.value = newvalue;
        this.isempty = newvalue != null;
    }

    public SchemaBase64Binary(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaBase64Binary(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaBase64Binary(SchemaTypeBinary newvalue) {
        this.assign(newvalue);
    }

    public void parse(String newvalue) {
        if (newvalue == null) {
            this.isempty = true;
            this.value = null;
        } else {
            this.isempty = false;
            try {
                this.value = new BASE64Decoder().decodeBuffer(newvalue);
            }
            catch (IOException e) {
                this.value = null;
                this.isempty = true;
            }
        }
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaBase64Binary)) {
            return false;
        }
        return this.value == ((SchemaBase64Binary)obj).value;
    }

    public Object clone() {
        return new SchemaBase64Binary(this.value);
    }

    public String toString() {
        if (this.isempty || this.value == null) {
            return "";
        }
        String sResult = new BASE64Encoder().encode(this.value);
        return sResult.replaceAll("&#13;", "\n");
    }

    public int binaryType() {
        return 0;
    }
}

