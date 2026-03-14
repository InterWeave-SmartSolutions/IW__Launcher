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

public class SchemaLong
implements SchemaTypeNumber {
    protected long value;
    protected boolean isempty;

    public SchemaLong() {
        this.value = 0L;
        this.isempty = true;
    }

    public SchemaLong(SchemaLong newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaLong(long newvalue) {
        this.setValue(newvalue);
    }

    public SchemaLong(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaLong(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaLong(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long newvalue) {
        this.value = newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.value = 0L;
            this.isempty = true;
            return;
        }
        try {
            this.value = Long.parseLong(newvalue);
            this.isempty = false;
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.value = 0L;
            this.isempty = true;
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = ((SchemaTypeNumber)newvalue).longValue();
        this.isempty = false;
    }

    public int hashCode() {
        return (int)this.value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaLong)) {
            return false;
        }
        return this.value == ((SchemaLong)obj).value;
    }

    public Object clone() {
        return new SchemaLong(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return Long.toString(this.value);
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value != 0L;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaLong)obj);
    }

    public int compareTo(SchemaLong obj) {
        return new Long(this.value).compareTo(new Long(obj.value));
    }

    public int numericType() {
        return 2;
    }

    public int intValue() {
        return (int)this.value;
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

