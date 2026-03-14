/*
 * Decompiled with CFR.
 */
package com.altova;

public class AltovaException
extends RuntimeException {
    protected Exception innerException;
    protected String message;

    public AltovaException(String text) {
        this.innerException = null;
        this.message = text;
    }

    public AltovaException(Exception other) {
        this.innerException = other;
        this.message = other.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public Exception getInnerException() {
        return this.innerException;
    }
}

