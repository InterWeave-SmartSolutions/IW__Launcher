/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaDuration;
import com.altova.types.SchemaTypeCalendar;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SchemaCalendarBase
implements SchemaTypeCalendar {
    protected int year;
    protected int month;
    protected int day;
    protected int hour;
    protected int minute;
    protected int second;
    protected double partsecond;
    protected boolean hasTZ;
    protected int offsetTZ;
    protected boolean isempty;

    public SchemaCalendarBase() {
        this.setInternalValues(0, 0, 0, 0, 0, 0, 0.0, false, 0);
        this.isempty = true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaCalendarBase)) {
            return false;
        }
        SchemaCalendarBase dt = (SchemaCalendarBase)obj;
        if (this.year != dt.year) {
            return false;
        }
        if (this.month != dt.month) {
            return false;
        }
        if (this.day != dt.day) {
            return false;
        }
        if (this.hour != dt.hour) {
            return false;
        }
        if (this.minute != dt.minute) {
            return false;
        }
        if (this.second != dt.second) {
            return false;
        }
        if (this.partsecond != dt.partsecond) {
            return false;
        }
        if (this.hasTZ != dt.hasTZ) {
            return false;
        }
        return this.offsetTZ == dt.offsetTZ;
    }

    public int hashCode() {
        return (int)Double.doubleToLongBits(this.getApproximatedTotal());
    }

    public String toDateString() {
        StringBuffer s = new StringBuffer();
        s.append(new DecimalFormat("0000").format(this.year));
        s.append("-");
        s.append(new DecimalFormat("00").format(this.month));
        s.append("-");
        s.append(new DecimalFormat("00").format(this.day));
        return s.toString();
    }

    public String toTimeString() {
        StringBuffer s = new StringBuffer();
        s.append(new DecimalFormat("00").format(this.hour));
        s.append(":");
        s.append(new DecimalFormat("00").format(this.minute));
        s.append(":");
        s.append(new DecimalFormat("00").format(this.second));
        if (this.partsecond > 0.0 && this.partsecond < 1.0) {
            String sPartSecond = new DecimalFormat("0.0###############").format(this.partsecond);
            s.append(".");
            s.append(sPartSecond.substring(2, sPartSecond.length()));
        }
        if (this.hasTZ) {
            if (this.offsetTZ == 0) {
                s.append("Z");
            } else {
                int absOffsetTZ = this.offsetTZ;
                if (this.offsetTZ < 0) {
                    s.append("-");
                    absOffsetTZ = -this.offsetTZ;
                } else {
                    s.append("+");
                }
                s.append(new DecimalFormat("00").format(absOffsetTZ / 60));
                s.append(":");
                s.append(new DecimalFormat("00").format(absOffsetTZ % 60));
            }
        }
        return s.toString();
    }

    public int length() {
        return this.toString().length();
    }

    public boolean booleanValue() {
        return true;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaCalendarBase)obj);
    }

    public int compareTo(SchemaCalendarBase obj) {
        return (int)(this.getApproximatedTotal() - obj.getApproximatedTotal());
    }

    protected void parseDate(String newvalue) throws StringParseException {
        if (newvalue.length() < 10) {
            throw new StringParseException("date-part of string is too short", 0);
        }
        try {
            int nStart = 0;
            if (newvalue.substring(0, 1).equals("-")) {
                nStart = 1;
            }
            this.year = Integer.parseInt(newvalue.substring(0, nStart + 4));
            if (!newvalue.substring(nStart + 4, nStart + 5).equals("-")) {
                throw new StringParseException("invalid date format", 2);
            }
            this.month = Integer.parseInt(newvalue.substring(nStart + 5, nStart + 7));
            if (!newvalue.substring(nStart + 7, nStart + 8).equals("-")) {
                throw new StringParseException("invalid date format", 2);
            }
            this.day = Integer.parseInt(newvalue.substring(nStart + 8, newvalue.length()));
        }
        catch (NumberFormatException e) {
            throw new StringParseException("invalid date format", 2);
        }
        this.isempty = false;
    }

    protected void parseTime(String newvalue) throws StringParseException {
        if (newvalue.length() < 8) {
            throw new StringParseException("time-part of string is too short", 0);
        }
        try {
            int nStart = 0;
            this.hour = Integer.parseInt(newvalue.substring(nStart, nStart + 2));
            if (!newvalue.substring(nStart + 2, nStart + 3).equals(":")) {
                throw new StringParseException("invalid date format", 2);
            }
            this.minute = Integer.parseInt(newvalue.substring(nStart + 3, nStart + 5));
            if (!newvalue.substring(nStart + 5, nStart + 6).equals(":")) {
                throw new StringParseException("invalid date format", 2);
            }
            this.second = Integer.parseInt(newvalue.substring(nStart + 6, nStart + 8));
            int nTZStartPosition = nStart + 8;
            this.partsecond = 0.0;
            if (newvalue.length() > nStart + 8 && newvalue.substring(nStart + 8, nStart + 9).equals(".")) {
                nStart = nTZStartPosition + 1;
                int nEnd = newvalue.length();
                int nMSecEnd = newvalue.indexOf("Z", nStart);
                if (nMSecEnd > -1 && nMSecEnd < nEnd) {
                    nEnd = nMSecEnd;
                }
                if ((nMSecEnd = newvalue.indexOf("+", nStart)) > -1 && nMSecEnd < nEnd) {
                    nEnd = nMSecEnd;
                }
                if ((nMSecEnd = newvalue.indexOf("-", nStart)) > -1 && nMSecEnd < nEnd) {
                    nEnd = nMSecEnd;
                }
                nTZStartPosition = nEnd;
                this.partsecond = Double.parseDouble("0." + newvalue.substring(nStart, nEnd));
            }
            this.hasTZ = false;
            this.offsetTZ = 0;
            if (newvalue.length() > nTZStartPosition && newvalue.substring(nTZStartPosition, nTZStartPosition + 1).equals("Z")) {
                this.hasTZ = true;
            } else if (newvalue.length() == nTZStartPosition + 6) {
                this.hasTZ = true;
                this.offsetTZ = Integer.parseInt(newvalue.substring(nTZStartPosition + 1, nTZStartPosition + 3)) * 60 + Integer.parseInt(newvalue.substring(nTZStartPosition + 4, nTZStartPosition + 6));
                if (newvalue.substring(nTZStartPosition, nTZStartPosition + 1).equals("-")) {
                    this.offsetTZ = -this.offsetTZ;
                }
            }
        }
        catch (NumberFormatException e) {
            throw new StringParseException("invalid number format", 2);
        }
        this.isempty = false;
    }

    public double getApproximatedTotal() {
        return (double)this.second + 60.0 * ((double)this.minute + 60.0 * ((double)this.hour + 24.0 * ((double)this.day + 31.0 * ((double)this.month + 12.0 * (double)this.year)))) + this.partsecond;
    }

    public Date getDate() {
        String s = String.valueOf(this.toDateString()) + " " + this.toTimeString();
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        }
        catch (ParseException e) {
            throw new StringParseException("Could not convert to date.", 0);
        }
    }

    protected void setInternalValues(int newyear, int newmonth, int newday, int newhour, int newminute, int newsecond, double newpartsecond, boolean newhasTZ, int newoffsetTZ) {
        this.year = newyear;
        this.month = newmonth;
        this.day = newday;
        this.hour = newhour;
        this.minute = newminute;
        this.second = newsecond;
        this.partsecond = newpartsecond;
        this.hasTZ = newhasTZ;
        this.offsetTZ = newoffsetTZ;
        this.isempty = false;
    }

    public SchemaDuration durationValue() {
        throw new TypesIncompatibleException(this, new SchemaDuration("PT"));
    }
}

