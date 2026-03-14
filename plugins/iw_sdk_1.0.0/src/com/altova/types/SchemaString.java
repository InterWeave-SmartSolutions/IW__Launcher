/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaDate;
import com.altova.types.SchemaDateTime;
import com.altova.types.SchemaDecimal;
import com.altova.types.SchemaDouble;
import com.altova.types.SchemaDuration;
import com.altova.types.SchemaFloat;
import com.altova.types.SchemaInt;
import com.altova.types.SchemaInteger;
import com.altova.types.SchemaLong;
import com.altova.types.SchemaTime;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeCalendar;
import com.altova.types.SchemaTypeNumber;
import com.altova.types.StringParseException;
import com.altova.types.ValuesNotConvertableException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaString
implements SchemaTypeNumber,
SchemaTypeCalendar {
    protected String value;
    protected boolean isempty;

    public SchemaString() {
        this.value = "";
        this.isempty = true;
    }

    public SchemaString(SchemaString newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaString(String newvalue) {
        this.setValue(newvalue);
    }

    public SchemaString(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaString(SchemaTypeNumber newvalue) {
        this.assign(newvalue);
    }

    public SchemaString(SchemaTypeCalendar newvalue) {
        this.assign(newvalue);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String newvalue) {
        if (newvalue == null) {
            this.isempty = true;
            this.value = "";
            return;
        }
        this.value = newvalue;
        this.isempty = false;
    }

    public void parse(String newvalue) {
        this.setValue(newvalue);
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            this.value = "";
            return;
        }
        this.value = newvalue.toString();
        this.isempty = false;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaString)) {
            return false;
        }
        return this.value.equals(((SchemaString)obj).value);
    }

    public Object clone() {
        return new SchemaString(new String(this.value));
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return this.value;
    }

    public int length() {
        return this.value.length();
    }

    public boolean booleanValue() {
        if (this.value == null || this.value.length() == 0 || this.value.compareToIgnoreCase("false") == 0) {
            return false;
        }
        if (this.isValueNumeric()) {
            return this.bigDecimalValue().compareTo(BigDecimal.valueOf(0L)) != 0;
        }
        return true;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaString)obj);
    }

    public int compareTo(SchemaString obj) {
        return this.value.compareTo(obj.value);
    }

    public boolean isValueNumeric() {
        try {
            BigDecimal tmp = new BigDecimal(this.value);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public int numericType() {
        return 6;
    }

    public int intValue() {
        if (this.value == "") {
            return 0;
        }
        try {
            return Integer.parseInt(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaInt(0));
        }
    }

    public long longValue() {
        if (this.value == "") {
            return 0L;
        }
        try {
            return Long.parseLong(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaLong(0L));
        }
    }

    public BigInteger bigIntegerValue() {
        if (this.value == "") {
            return new BigInteger("0");
        }
        try {
            return new BigInteger(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaInteger(0L));
        }
    }

    public float floatValue() {
        if (this.value == "") {
            return 0.0f;
        }
        try {
            return Float.parseFloat(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaFloat(0.0f));
        }
    }

    public double doubleValue() {
        if (this.value == "") {
            return 0.0;
        }
        try {
            return Double.parseDouble(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaDouble(0.0));
        }
    }

    public BigDecimal bigDecimalValue() {
        if (this.value == "") {
            return new BigDecimal(0);
        }
        try {
            return new BigDecimal(this.value);
        }
        catch (NumberFormatException e) {
            throw new ValuesNotConvertableException(this, new SchemaDecimal(0.0));
        }
    }

    public int calendarType() {
        return -1;
    }

    public SchemaDuration durationValue() {
        try {
            return new SchemaDuration(this.value);
        }
        catch (StringParseException e) {
            throw new ValuesNotConvertableException(this, new SchemaDuration("PT"));
        }
    }

    public SchemaDateTime dateTimeValue() {
        try {
            return new SchemaDateTime(this.value);
        }
        catch (StringParseException e) {
            throw new ValuesNotConvertableException(this, new SchemaDateTime("2003-08-06T00:00:00"));
        }
    }

    public SchemaDate dateValue() {
        try {
            return new SchemaDate(this.value);
        }
        catch (StringParseException e) {
            throw new ValuesNotConvertableException(this, new SchemaDate("2003-08-06"));
        }
    }

    public SchemaTime timeValue() {
        try {
            return new SchemaTime(this.value);
        }
        catch (StringParseException e) {
            throw new ValuesNotConvertableException(this, new SchemaTime("00:00:00"));
        }
    }
}

