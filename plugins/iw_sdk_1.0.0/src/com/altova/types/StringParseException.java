/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaTypeException;

public class StringParseException
extends SchemaTypeException {
    int position;

    public StringParseException(String text, int newposition) {
        super(text);
        this.position = newposition;
    }

    public StringParseException(Exception other) {
        super(other);
    }
}

