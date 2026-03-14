/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaInt
implements SchemaTypeNumber {
    protected int value;
    protected boolean isempty;

    public SchemaInt() {
        this.value = 0;
        this.isempty = true;
    }

    public SchemaInt(SchemaInt newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaInt(int newvalue) {
        this.setValue(newvalue);
    }

    public SchemaInt(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaInt(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaInt(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newvalue) {
        this.value = newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.isempty = true;
            this.value = 0;
            return;
        }
        try {
            this.value = Integer.parseInt(newvalue);
            this.isempty = false;
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
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
        this.value = ((SchemaTypeNumber)newvalue).intValue();
        this.isempty = false;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaInt)) {
            return false;
        }
        return this.value == ((SchemaInt)obj).value;
    }

    public Object clone() {
        return new SchemaInt(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return Integer.toString(this.value);
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
        return this.compareTo((SchemaInt)obj);
    }

    public int compareTo(SchemaInt obj) {
        return new Integer(this.value).compareTo(new Integer(obj.value));
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

