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

public class SchemaDecimal
implements SchemaTypeNumber {
    protected BigDecimal value;
    protected boolean isempty;

    public SchemaDecimal() {
        this.value = BigDecimal.valueOf(0L);
        this.isempty = true;
    }

    public SchemaDecimal(SchemaDecimal newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaDecimal(BigDecimal newvalue) {
        this.setValue(newvalue);
    }

    public SchemaDecimal(double newvalue) {
        this.setValue(newvalue);
    }

    public SchemaDecimal(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaDecimal(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaDecimal(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal newvalue) {
        if (newvalue == null) {
            this.value = new BigDecimal(0);
            this.isempty = true;
            return;
        }
        this.value = newvalue;
        this.isempty = false;
    }

    public void setValue(double newvalue) {
        this.value = new BigDecimal(newvalue);
        this.isempty = false;
    }

    public void parse(String newvalue) {
        if (newvalue == null || newvalue == "") {
            this.value = new BigDecimal(0);
            this.isempty = true;
            return;
        }
        try {
            this.value = new BigDecimal(newvalue);
        }
        catch (NumberFormatException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.value = new BigDecimal(0);
            this.isempty = true;
            return;
        }
        if (!(newvalue instanceof SchemaTypeNumber)) {
            throw new TypesIncompatibleException(newvalue, this);
        }
        this.value = ((SchemaTypeNumber)newvalue).bigDecimalValue();
        this.isempty = false;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaDecimal)) {
            return false;
        }
        return this.value.compareTo(((SchemaDecimal)obj).value) == 0;
    }

    public Object clone() {
        return new SchemaDecimal(this.value.toString());
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        String result = this.value.toString();
        if (result.length() > 2) {
            int n = result.length() - 1;
            while (n > 0 && result.substring(n, n + 1).equals("0")) {
                --n;
            }
            if (result.substring(n, n + 1).equals(".")) {
                return result.substring(0, n);
            }
        }
        return result;
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return this.value.compareTo(new BigDecimal(0)) != 0;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaDecimal)obj);
    }

    public int compareTo(SchemaDecimal obj) {
        return this.value.compareTo(obj.value);
    }

    public void reduceScale() {
        if (this.value.scale() <= 0) {
            return;
        }
        String sScaled = this.value.toString();
        int nPos = sScaled.length() - 1;
        int nReduceScale = 0;
        while (nPos >= 0 && sScaled.substring(nPos, nPos + 1).equals("0")) {
            --nPos;
            ++nReduceScale;
        }
        this.value = this.value.setScale(this.value.scale() - nReduceScale);
    }

    public int numericType() {
        return 6;
    }

    public int intValue() {
        return this.value.intValue();
    }

    public long longValue() {
        return this.value.longValue();
    }

    public BigInteger bigIntegerValue() {
        return this.value.toBigInteger();
    }

    public float floatValue() {
        return this.value.floatValue();
    }

    public double doubleValue() {
        return this.value.doubleValue();
    }

    public BigDecimal bigDecimalValue() {
        return this.value;
    }
}

