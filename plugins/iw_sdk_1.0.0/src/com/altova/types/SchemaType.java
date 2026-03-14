/*
 * Decompiled with CFR.
 */
package com.altova.types;

import java.io.Serializable;

public interface SchemaType
extends Cloneable,
Comparable,
Serializable {
    public String toString();

    public int length();

    public boolean booleanValue();

    public boolean isEmpty();
}

