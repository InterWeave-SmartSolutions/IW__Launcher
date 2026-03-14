/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;

public interface SchemaTypeBinary
extends SchemaType {
    public static final int BINARY_VALUE_UNDEFINED = -1;
    public static final int BINARY_VALUE_BASE64 = 0;
    public static final int BINARY_VALUE_HEX = 1;

    public int binaryType();
}

