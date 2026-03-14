/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeException;

public class TypesIncompatibleException
extends SchemaTypeException {
    protected SchemaType object1;
    protected SchemaType object2;

    public TypesIncompatibleException(SchemaType newobj1, SchemaType newobj2) {
        super("Incompatible schema-types");
        this.object1 = newobj1;
        this.object2 = newobj2;
    }

    public TypesIncompatibleException(Exception other) {
        super(other);
    }
}

