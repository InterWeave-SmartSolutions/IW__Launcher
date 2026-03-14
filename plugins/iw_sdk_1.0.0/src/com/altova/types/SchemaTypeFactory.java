/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaBoolean;
import com.altova.types.SchemaByte;
import com.altova.types.SchemaDate;
import com.altova.types.SchemaDateTime;
import com.altova.types.SchemaDecimal;
import com.altova.types.SchemaDuration;
import com.altova.types.SchemaInt;
import com.altova.types.SchemaInteger;
import com.altova.types.SchemaString;
import com.altova.types.SchemaTime;
import com.altova.types.SchemaType;
import com.altova.types.StringParseException;
import java.math.BigDecimal;

public class SchemaTypeFactory {
    public static SchemaType createInstanceByString(String value) {
        if (value.compareToIgnoreCase("false") == 0) {
            return new SchemaBoolean(false);
        }
        if (value.compareToIgnoreCase("true") == 0) {
            return new SchemaBoolean(true);
        }
        try {
            SchemaDateTime result = new SchemaDateTime(value);
            return result;
        }
        catch (StringParseException result) {
            try {
                SchemaDuration result2 = new SchemaDuration(value);
                return result2;
            }
            catch (StringParseException result2) {
                try {
                    SchemaDate result3 = new SchemaDate(value);
                    return result3;
                }
                catch (StringParseException result3) {
                    try {
                        SchemaTime result4 = new SchemaTime(value);
                        return result4;
                    }
                    catch (StringParseException result4) {
                        try {
                            BigDecimal tmp = new BigDecimal(value);
                            if (tmp.scale() <= 0) {
                                if (tmp.compareTo(new BigDecimal(Integer.MAX_VALUE)) <= 0 && tmp.compareTo(new BigDecimal(Integer.MIN_VALUE)) >= 0) {
                                    return new SchemaInt(tmp.intValue());
                                }
                                return new SchemaInteger(tmp.toBigInteger());
                            }
                            return new SchemaDecimal(tmp);
                        }
                        catch (NumberFormatException e) {
                            return new SchemaString(value);
                        }
                    }
                }
            }
        }
    }

    public static SchemaType createInstanceByObject(Object value) {
        if (value instanceof Boolean) {
            return new SchemaBoolean((Boolean)value);
        }
        if (value instanceof Byte) {
            return new SchemaByte(((Byte)value).byteValue());
        }
        return new SchemaString(value.toString());
    }
}

