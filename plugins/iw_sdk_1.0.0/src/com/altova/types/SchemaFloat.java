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

public class SchemaFloat
implements SchemaTypeNumber {
    protected float value;
    protected boolean isempty;

    public SchemaFloat() {
        this.value = 0.0f;
        this.isempty = true;
    }

    public SchemaFloat(SchemaFloat newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaFloat(float newvalue) {
        this.setValue(newvalue);
    }

    public SchemaFloat(double newvalue) {
        this.setValue((float)newvalue);
    }

    public SchemaFloat(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaFloat(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaFloat(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float newvalue) {
        this.value = newvalue;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.value = 0.0f;
            this.isempty = true;
            return;
        }
        try {
            this.value = Float.parseFloat(newvalue);
            this.isempty = false;
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.value = 0.0f;
            this.isempty = true;
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = ((SchemaTypeNumber)newvalue).floatValue();
        this.isempty = false;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaFloat)) {
            return false;
        }
        return this.value == ((SchemaFloat)obj).value;
    }

    public Object clone() {
        return new SchemaFloat(this.value);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        String result = Float.toString(this.value);
        if (result.length() > 2 && result.substring(result.length() - 2, result.length()).equals(".0")) {
            return result.substring(0, result.length() - 2);
        }
        return result;
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value != 0.0f && this.value != Float.NaN;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaFloat)obj);
    }

    public int compareTo(SchemaFloat obj) {
        return Float.compare(this.value, obj.value);
    }

    public int numericType() {
        return 4;
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
        return this.value;
    }

    public double doubleValue() {
        return this.value;
    }

    public BigDecimal bigDecimalValue() {
        return new BigDecimal(this.value);
    }
}

