/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaDate;
import com.altova.types.SchemaDateTime;
import com.altova.types.SchemaDuration;
import com.altova.types.SchemaTime;
import com.altova.types.SchemaType;

public interface SchemaTypeCalendar
extends SchemaType {
    public static final int CALENDAR_VALUE_UNDEFINED = -1;
    public static final int CALENDAR_VALUE_DURATION = 0;
    public static final int CALENDAR_VALUE_DATETIME = 1;
    public static final int CALENDAR_VALUE_DATE = 2;
    public static final int CALENDAR_VALUE_TIME = 3;

    public int calendarType();

    public SchemaDuration durationValue();

    public SchemaDateTime dateTimeValue();

    public SchemaDate dateValue();

    public SchemaTime timeValue();
}

