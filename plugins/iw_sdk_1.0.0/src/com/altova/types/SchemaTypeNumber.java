/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface SchemaTypeNumber
extends SchemaType {
    public static final int NUMERIC_VALUE_INT = 1;
    public static final int NUMERIC_VALUE_LONG = 2;
    public static final int NUMERIC_VALUE_BIGINTEGER = 3;
    public static final int NUMERIC_VALUE_FLOAT = 4;
    public static final int NUMERIC_VALUE_DOUBLE = 5;
    public static final int NUMERIC_VALUE_BIGDECIMAL = 6;

    public int numericType();

    public int intValue();

    public long longValue();

    public BigInteger bigIntegerValue();

    public float floatValue();

    public double doubleValue();

    public BigDecimal bigDecimalValue();
}

