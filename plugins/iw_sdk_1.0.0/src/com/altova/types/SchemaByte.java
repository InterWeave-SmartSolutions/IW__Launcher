/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.TypesIncompatibleException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaByte
implements SchemaTypeNumber {
    protected byte value;
    protected boolean isempty;

    public SchemaByte() {
        this.value = 0;
        this.isempty = true;
    }

    public SchemaByte(SchemaByte newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaByte(int newvalue) {
        this.setValue(newvalue);
    }

    public SchemaByte(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaByte(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaByte(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public byte getValue() {
        return this.value;
    }

    public void setValue(int newvalue) {
        this.value = (byte)newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.isempty = true;
            this.value = 0;
        } else {
            this.isempty = false;
            this.value = Byte.parseByte(newvalue);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            this.value = 0;
            return;
        }
        this.isempty = false;
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = (byte)((SchemaTypeNumber)newvalue).intValue();
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaByte)) {
            return false;
        }
        return this.value == ((SchemaByte)obj).value;
    }

    public Object clone() {
        return new SchemaByte(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return Byte.toString(this.value);
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value != 0;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaByte)obj);
    }

    public int compareTo(SchemaByte obj) {
        return new Byte(this.value).compareTo(new Byte(obj.value));
    }

    public int numericType() {
        return 1;
    }

    public int intValue() {
        return this.value;
    }

    public long longValue() {
        return this.value;
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this.value);
    }

    public float floatValue() {
        return this.value;
    }

    public double doubleValue() {
        return this.value;
    }

    public BigDecimal bigDecimalValue() {
        return BigDecimal.valueOf(this.value);
    }
}

