/*
 * Decompiled with CFR.
 */
package com.altova.types;

import com.altova.types.SchemaString;
import com.altova.types.SchemaType;
import com.altova.types.SchemaTypeBinary;
import com.altova.types.TypesIncompatibleException;

public abstract class SchemaBinaryBase
implements SchemaTypeBinary {
    protected byte[] value;
    protected boolean isempty = true;

    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaBinaryBase)) {
            return false;
        }
        SchemaBinaryBase bin = (SchemaBinaryBase)obj;
        if (this.value.length != bin.value.length) {
            return false;
        }
        int i = 0;
        while (i < this.value.length) {
            if (this.value[i] != bin.value[i]) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public void assign(SchemaType newvalue) {
        if (newvalue == null || newvalue.isEmpty()) {
            this.value = null;
            this.isempty = true;
            return;
        }
        if (newvalue instanceof SchemaBinaryBase) {
            this.value = ((SchemaBinaryBase)newvalue).value;
            this.isempty = ((SchemaBinaryBase)newvalue).isempty;
        } else if (newvalue instanceof SchemaString) {
            this.parse(newvalue.toString());
        } else {
            throw new TypesIncompatibleException(newvalue, this);
        }
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] newvalue) {
        this.value = newvalue;
        this.isempty = newvalue == null;
    }

    public abstract void parse(String var1);

    public int hashCode() {
        if (this.value == null) {
            return 0;
        }
        return this.value[0];
    }

    public int length() {
        return this.value.length;
    }

    public boolean booleanValue() {
        return true;
    }

    public boolean isEmpty() {
        return this.isempty;
    }

    public int compareTo(Object obj) {
        return this.compareTo((SchemaBinaryBase)obj);
    }

    public int compareTo(SchemaBinaryBase obj) {
        if (this.value.length != obj.value.length) {
            return new Integer(this.value.length).compareTo(new Integer(obj.value.length));
        }
        int i = 0;
        while (i < this.value.length) {
            if (this.value[i] != obj.value[i]) {
                return new Integer(this.value[i]).compareTo(new Integer(obj.value[i]));
            }
            ++i;
        }
        return 0;
    }
}

