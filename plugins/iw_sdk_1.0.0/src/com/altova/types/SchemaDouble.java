/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaInteger;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import com.altova.types.ValuesNotConvertableException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaDouble
implements SchemaTypeNumber {
    protected double value;
    protected boolean isempty;

    public SchemaDouble() {
        this.value = 0.0;
        this.isempty = true;
    }

    public SchemaDouble(SchemaDouble newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaDouble(double newvalue) {
        this.setValue(newvalue);
    }

    public SchemaDouble(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaDouble(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaDouble(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double newvalue) {
        this.value = newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.isempty = true;
            this.value = 0.0;
            return;
        }
        try {
            this.value = Double.parseDouble(newvalue);
            this.isempty = false;
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            this.value = 0.0;
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = ((SchemaTypeNumber)newvalue).doubleValue();
        this.isempty = false;
    }

    public int hashCode() {
        return (int)Double.doubleToLongBits(this.value);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaDouble)) {
            return false;
        }
        return this.value == ((SchemaDouble)obj).value;
    }

    public Object clone() {
        return new SchemaDouble(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        String result = Double.toString(this.value);
        if (result.length() > 2 && result.substring(result.length() - 2, result.length()).equals(".0")) {
            return result.substring(0, result.length() - 2);
        }
        return result;
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value != 0.0 && this.value != Double.NaN;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaDouble)obj);
    }

    public int compareTo(SchemaDouble obj) {
        return Double.compare(this.value, obj.value);
    }

    public int numericType() {
        return 5;
    }

    public int intValue() {
        return (int)this.value;
    }

    public long longValue() {
        return (long)this.value;
    }

    public BigInteger bigIntegerValue() {
        try {
            return new BigInteger(this.toString());
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaInteger(0L));
        }
    }

    public float floatValue() {
        return (float)this.value;
    }

    public double doubleValue() {
        return this.value;
    }

    public BigDecimal bigDecimalValue() {
        return new BigDecimal(this.value);
    }
}

