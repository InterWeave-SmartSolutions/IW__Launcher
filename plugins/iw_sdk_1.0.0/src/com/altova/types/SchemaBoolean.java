/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaString;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaBoolean
implements SchemaTypeNumber {
    protected boolean value;
    protected boolean isempty;

    public SchemaBoolean() {
        this.isempty = true;
        this.value = false;
    }

    public SchemaBoolean(SchemaBoolean newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaBoolean(boolean newvalue) {
        this.setValue(newvalue);
    }

    public SchemaBoolean(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaBoolean(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaBoolean(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean newvalue) {
        this.isempty = false;
        this.value = newvalue;
    }

    public void parse(String newvalue) {
        if (newvalue == null) {
            this.isempty = true;
            this.value = false;
            return;
        }
        this.setValue(new SchemaString(newvalue).booleanValue());
    }

    public void assign(SchemaType newvalue) {
        this.parse(newvalue.toString());
    }

    public int hashCode() {
        return this.value ? 1231 : 1237;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaBoolean)) {
            return false;
        }
        return this.value == ((SchemaBoolean)obj).value;
    }

    public Object clone() {
        return new SchemaBoolean(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return this.value ? "true" : "false";
    }

    public int length() {
        return 1;
    }

    public boolean booleanValue() {
        return this.value;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaBoolean)obj);
    }

    public int compareTo(SchemaBoolean obj) {
        if (this.value == obj.value) {
            return 0;
        }
        if (!this.value) {
            return -1;
        }
        return 1;
    }

    public int numericType() {
        return 1;
    }

    public void setValue(int newvalue) {
        this.value = newvalue != 0;
    }

    public void setValue(long newvalue) {
        this.value = newvalue != 0L;
    }

    public void setValue(BigInteger newvalue) {
        this.value = newvalue.compareTo(BigInteger.valueOf(0L)) != 0;
    }

    public void setValue(float newvalue) {
        this.value = newvalue != 0.0f;
    }

    public void setValue(double newvalue) {
        this.value = newvalue != 0.0;
    }

    public void setValue(BigDecimal newvalue) {
        this.value = newvalue.compareTo(BigDecimal.valueOf(0L)) != 0;
    }

    public int intValue() {
        if (this.value) {
            return 1;
        }
        return 0;
    }

    public long longValue() {
        if (this.value) {
            return 1L;
        }
        return 0L;
    }

    public BigInteger bigIntegerValue() {
        if (this.value) {
            return BigInteger.valueOf(1L);
        }
        return BigInteger.valueOf(0L);
    }

    public float floatValue() {
        if (this.value) {
            return 1.0f;
        }
        return 0.0f;
    }

    public double doubleValue() {
        if (this.value) {
            return 1.0;
        }
        return 0.0;
    }

    public BigDecimal bigDecimalValue() {
        if (this.value) {
            return BigDecimal.valueOf(1L);
        }
        return BigDecimal.valueOf(0L);
    }
}

