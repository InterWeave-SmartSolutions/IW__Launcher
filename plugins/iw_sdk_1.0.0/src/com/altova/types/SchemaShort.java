/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.TypesIncompatibleException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaShort
implements SchemaTypeNumber {
    protected short value;
    protected boolean isempty;

    public SchemaShort() {
        this.value = 0;
        this.isempty = true;
    }

    public SchemaShort(SchemaShort newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaShort(short newvalue) {
        this.setValue(newvalue);
    }

    public SchemaShort(int newvalue) {
        this.setValue((short)newvalue);
    }

    public SchemaShort(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaShort(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaShort(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public short getValue() {
        return this.value;
    }

    public void setValue(short newvalue) {
        this.value = newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.isempty = true;
            this.value = 0;
            return;
        }
        this.value = Short.parseShort(newvalue);
        this.isempty = false;
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            this.value = 0;
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = (short)((SchemaTypeNumber)newvalue).intValue();
        this.isempty = false;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaShort)) {
            return false;
        }
        return this.value == ((SchemaShort)obj).value;
    }

    public Object clone() {
        return new SchemaShort(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return Short.toString(this.value);
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
        return this.compareTo((SchemaShort)obj);
    }

    public int compareTo(SchemaShort obj) {
        return new Short(this.value).compareTo(new Short(obj.value));
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

