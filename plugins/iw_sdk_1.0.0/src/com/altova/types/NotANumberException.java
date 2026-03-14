/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaTypeException;

public class NotANumberException
extends SchemaTypeException {
    public NotANumberException(String text) {
        super(text);
    }

    public NotANumberException(Exception other) {
        super(other);
    }
}

