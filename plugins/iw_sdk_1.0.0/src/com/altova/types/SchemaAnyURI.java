/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaString;
import com.altova.types.SchemaType;
import com.altova.types.StringParseException;
import com.altova.types.TypesIncompatibleException;
import java.net.URI;

public class SchemaAnyURI
implements SchemaType {
    protected URI value;
    protected boolean isempty;

    public SchemaAnyURI() {
        this.parse("http://www.altova.com/language_select.html");
    }

    public SchemaAnyURI(SchemaAnyURI newvalue) {
        this.value = newvalue.value;
        this.isempty = newvalue.isempty;
    }

    public SchemaAnyURI(URI newvalue) {
        this.setValue(newvalue);
    }

    public SchemaAnyURI(String newvalue) {
        this.parse(newvalue);
    }

    public SchemaAnyURI(SchemaType newvalue) {
        this.assign(newvalue);
    }

    public URI getValue() {
        return this.value;
    }

    public void setValue(URI newvalue) {
        if (newvalue == null) {
            this.isempty = true;
        } else {
            this.isempty = false;
            this.value = newvalue;
        }
    }

    public void parse(String newvalue) {
        try {
            this.value = URI.create(newvalue);
        }
        catch (IllegalArgumentException e) {
            throw new StringParseException(e);
        }
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.isempty = true;
            return;
        }
        if (newvalue instanceof SchemaString) {
            this.parse(newvalue.toString());
        } else if (newvalue instanceof SchemaAnyURI) {
            this.setValue(((SchemaAnyURI)newvalue).value);
        } else {
            throw new TypesIncompatibleException(newvalue, this);
        }
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaAnyURI)) {
            return false;
        }
        return this.value == ((SchemaAnyURI)obj).value;
    }

    public Object clone() {
        return new SchemaAnyURI(this.value);
    }

    public String toString() {
        return this.value.toString();
    }

    public int length() {
        return this.value.toString().length();
    }

    public boolean booleanValue() {
        return this.value != null && this.value.toString().length() != 0;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaAnyURI)obj);
    }

    public int compareTo(URI obj) {
        return this.value.compareTo(obj);
    }
}

