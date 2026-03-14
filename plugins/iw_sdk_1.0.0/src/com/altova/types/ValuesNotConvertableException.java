/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeException;

public class ValuesNotConvertableException
extends SchemaTypeException {
    protected SchemaType object1;
    protected SchemaType object2;

    public ValuesNotConvertableException(SchemaType newobj1, SchemaType newobj2) {
        super("Values could not be converted");
        this.object1 = newobj1;
        this.object2 = newobj2;
    }

    public ValuesNotConvertableException(Exception other) {
        super(other);
    }
}

