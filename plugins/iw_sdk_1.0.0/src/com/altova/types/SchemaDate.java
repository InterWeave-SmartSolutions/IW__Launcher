/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaCalendarBase;
import com.altova.types.SchemaDateTime;
import com.altova.types.SchemaString;
import com.altova.types.SchemaTime;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeCalendar;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import java.util.Calendar;

public class SchemaDate
extends SchemaCalendarBase {
    public SchemaDate() {
        this.setValue(Calendar.getInstance());
        this.isempty = true;
    }

    public SchemaDate(SchemaDate newvalue) {
        this.year = newvalue.year;
        this.month = newvalue.month;
        this.day = newvalue.day;
        this.hour = newvalue.hour;
        this.minute = newvalue.minute;
        this.second = newvalue.second;
        this.partsecond = newvalue.partsecond;
        this.hasTZ = newvalue.hasTZ;
        this.offsetTZ = newvalue.offsetTZ;
        this.isempty = newvalue.isempty;
    }

    public SchemaDate(int newyear, int newmonth, int newday) {
        this.setInternalValues(newyear, newmonth, newday, 0, 0, 0, 0.0, false, 0);
    }

    public SchemaDate(Calendar newvalue) {
        this.setValue(newvalue);
    }

    public SchemaDate(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaDate(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public SchemaDate(SchemaTypeCalendar newvalue) {
        this.assign(newvalue);
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public Calendar getValue() {
        Calendar cal = Calendar.getInstance();
        cal.set(this.year, this.month - 1, this.day);
        return cal;
    }

    public void setYear(int newyear) {
        this.year = newyear;
        this.isempty = false;
    }

    public void setMonth(int newmonth) {
        this.month = newmonth;
        this.isempty = false;
    }

    public void setDay(int newday) {
        this.day = newday;
        this.isempty = false;
    }

    public void setValue(Calendar newvalue) {
        if (newvalue == null) {
            this.setValue(Calendar.getInstance());
            this.isempty = true;
        } else {
            this.setInternalValues(newvalue.get(1), newvalue.get(2) + 1, newvalue.get(5), 0, 0, 0, 0.0, false, 0);
        }
    }

    public void parse(String newvalue) throws StringParseException {
        if (newvalue == null) {
            this.isempty = true;
            this.setValue(Calendar.getInstance());
        } else {
            this.parseDate(newvalue);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.setValue(Calendar.getInstance());
            this.isempty = true;
            return;
        }
        this.isempty = false;
        if (newvalue instanceof SchemaDate) {
            this.year = ((SchemaDate)newvalue).year;
            this.month = ((SchemaDate)newvalue).month;
            this.day = ((SchemaDate)newvalue).day;
        } else if (newvalue instanceof SchemaDateTime) {
            this.year = ((SchemaDateTime)newvalue).year;
            this.month = ((SchemaDateTime)newvalue).month;
            this.day = ((SchemaDateTime)newvalue).day;
        } else if (newvalue instanceof SchemaString) {
            this.parse(newvalue.toString());
        } else {
            throw new TypesIncompatibleException(newvalue, this);
        }
    }

    public Object clone() {
        return new SchemaDate(this);
    }

    public String toString() {
        if (this.isempty) {
            return "";
        }
        return this.toDateString();
    }

    public static SchemaDate now() {
        return new SchemaDate(Calendar.getInstance());
    }

    public int calendarType() {
        return 2;
    }

    public SchemaDateTime dateTimeValue() {
        return new SchemaDateTime(this.year, this.month, this.day, 0, 0, 0, 0.0);
    }

    public SchemaDate dateValue() {
        return new SchemaDate(this);
    }

    public SchemaTime timeValue() {
        throw new TypesIncompatibleException(this, new SchemaTime("2003-07-28"));
    }
}

