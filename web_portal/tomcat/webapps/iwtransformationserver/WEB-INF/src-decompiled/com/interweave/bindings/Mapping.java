/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Mapping
extends MarshallableObject
implements Element {
    private String _Quoted;
    private String _Type;
    private String _Content;

    public String getQuoted() {
        return this._Quoted;
    }

    public void setQuoted(String _Quoted) {
        this._Quoted = _Quoted;
        if (_Quoted == null) {
            this.invalidate();
        }
    }

    public String getType() {
        return this._Type;
    }

    public void setType(String _Type) {
        this._Type = _Type;
        if (_Type == null) {
            this.invalidate();
        }
    }

    public String getContent() {
        return this._Content;
    }

    public void setContent(String _Content) {
        this._Content = _Content;
        if (_Content == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (this._Quoted == null) {
            throw new MissingAttributeException("quoted");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("mapping");
        w.attribute("quoted", this._Quoted.toString());
        if (this._Type != null) {
            w.attribute("type", this._Type.toString());
        }
        if (this._Content != null) {
            w.chars(this._Content.toString());
        }
        w.end("mapping");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("mapping");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("quoted")) {
                if (this._Quoted != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Quoted = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (this._Type != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Type = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        String s = xs.atChars(0) ? xs.takeChars(0) : "";
        try {
            this._Content = String.valueOf(s);
        }
        catch (Exception x) {
            throw new ConversionException("content", (Throwable)x);
        }
        xs.takeEnd("mapping");
    }

    public static Mapping unmarshal(InputStream in) throws UnmarshalException {
        return Mapping.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Mapping unmarshal(XMLScanner xs) throws UnmarshalException {
        return Mapping.unmarshal(xs, Mapping.newDispatcher());
    }

    public static Mapping unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Mapping)d.unmarshal(xs, Mapping.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Mapping)) {
            return false;
        }
        Mapping tob = (Mapping)((Object)ob);
        if (this._Quoted != null) {
            if (tob._Quoted == null) {
                return false;
            }
            if (!this._Quoted.equals(tob._Quoted)) {
                return false;
            }
        } else if (tob._Quoted != null) {
            return false;
        }
        if (this._Type != null) {
            if (tob._Type == null) {
                return false;
            }
            if (!this._Type.equals(tob._Type)) {
                return false;
            }
        } else if (tob._Type != null) {
            return false;
        }
        if (this._Content != null) {
            if (tob._Content == null) {
                return false;
            }
            if (!this._Content.equals(tob._Content)) {
                return false;
            }
        } else if (tob._Content != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Quoted != null ? this._Quoted.hashCode() : 0);
        h = 127 * h + (this._Type != null ? this._Type.hashCode() : 0);
        h = 127 * h + (this._Content != null ? this._Content.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<mapping");
        if (this._Quoted != null) {
            sb.append(" quoted=");
            sb.append(this._Quoted.toString());
        }
        if (this._Type != null) {
            sb.append(" type=");
            sb.append(this._Type.toString());
        }
        if (this._Content != null) {
            sb.append(" content=");
            sb.append(this._Content.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }
}

