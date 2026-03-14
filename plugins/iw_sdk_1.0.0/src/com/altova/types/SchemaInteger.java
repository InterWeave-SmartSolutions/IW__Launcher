/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaDecimal;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import com.altova.types.ValuesNotConvertableException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaInteger
implements SchemaTypeNumber {
    protected BigInteger value;
    protected boolean isempty;

    public SchemaInteger() {
        this.value = BigInteger.valueOf(0L);
        this.isempty = true;
    }

    public SchemaInteger(SchemaInteger newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaInteger(BigInteger newvalue) {
        this.setValue(newvalue);
    }

    public SchemaInteger(long newvalue) {
        this.setValue(newvalue);
    }

    public SchemaInteger(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaInteger(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaInteger(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public BigInteger getValue() {
        return this.value;
    }

    public void setValue(BigInteger newvalue) {
        if (newvalue == null) {
            this.isempty = true;
            this.value = BigInteger.valueOf(0L);
            return;
        }
        this.value = newvalue;
        this.isempty = false;
    }

    public void setValue(long newvalue) {
        this.value = BigInteger.valueOf(newvalue);
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.isempty = true;
            this.value = BigInteger.valueOf(0L);
            return;
        }
        try {
            this.value = new BigInteger(newvalue);
            this.isempty = false;
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            this.value = BigInteger.valueOf(0L);
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = ((SchemaTypeNumber)newvalue).bigIntegerValue();
        this.isempty = false;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaInteger)) {
            return false;
        }
        return this.value.equals(((SchemaInteger)obj).value);
    }

    public Object clone() {
        return new SchemaInteger(this.value.toString());
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return this.value.toString();
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value.compareTo(BigInteger.valueOf(0L)) != 0;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaInteger)obj);
    }

    public int compareTo(SchemaInteger obj) {
        return this.value.compareTo(obj.value);
    }

    public int numericType() {
        return 3;
    }

    public int intValue() {
        return this.value.intValue();
    }

    public long longValue() {
        return this.value.longValue();
    }

    public BigInteger bigIntegerValue() {
        return this.value;
    }

    public float floatValue() {
        return this.value.floatValue();
    }

    public double doubleValue() {
        return this.value.doubleValue();
    }

    public BigDecimal bigDecimalValue() {
        try {
            return new BigDecimal(this.value.toString());
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaDecimal(0.0));
        }
    }
}

