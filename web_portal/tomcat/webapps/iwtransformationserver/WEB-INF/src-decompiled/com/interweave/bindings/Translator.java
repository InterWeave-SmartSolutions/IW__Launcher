/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Translator
extends MarshallableObject
implements Element {
    private String _Inputclass;
    private String _Outputclass;

    public String getInputclass() {
        return this._Inputclass;
    }

    public void setInputclass(String _Inputclass) {
        this._Inputclass = _Inputclass;
        if (_Inputclass == null) {
            this.invalidate();
        }
    }

    public String getOutputclass() {
        return this._Outputclass;
    }

    public void setOutputclass(String _Outputclass) {
        this._Outputclass = _Outputclass;
        if (_Outputclass == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("translator");
        if (this._Inputclass != null) {
            w.leaf("inputclass", this._Inputclass.toString());
        }
        if (this._Outputclass != null) {
            w.leaf("outputclass", this._Outputclass.toString());
        }
        w.end("translator");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("translator");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("inputclass")) {
            xs.takeStart("inputclass");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Inputclass = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("inputclass", (Throwable)x);
            }
            xs.takeEnd("inputclass");
        }
        if (xs.atStart("outputclass")) {
            xs.takeStart("outputclass");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Outputclass = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("outputclass", (Throwable)x);
            }
            xs.takeEnd("outputclass");
        }
        xs.takeEnd("translator");
    }

    public static Translator unmarshal(InputStream in) throws UnmarshalException {
        return Translator.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Translator unmarshal(XMLScanner xs) throws UnmarshalException {
        return Translator.unmarshal(xs, Translator.newDispatcher());
    }

    public static Translator unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Translator)d.unmarshal(xs, Translator.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Translator)) {
            return false;
        }
        Translator tob = (Translator)((Object)ob);
        if (this._Inputclass != null) {
            if (tob._Inputclass == null) {
                return false;
            }
            if (!this._Inputclass.equals(tob._Inputclass)) {
                return false;
            }
        } else if (tob._Inputclass != null) {
            return false;
        }
        if (this._Outputclass != null) {
            if (tob._Outputclass == null) {
                return false;
            }
            if (!this._Outputclass.equals(tob._Outputclass)) {
                return false;
            }
        } else if (tob._Outputclass != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Inputclass != null ? this._Inputclass.hashCode() : 0);
        h = 127 * h + (this._Outputclass != null ? this._Outputclass.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<translator");
        if (this._Inputclass != null) {
            sb.append(" inputclass=");
            sb.append(this._Inputclass.toString());
        }
        if (this._Outputclass != null) {
            sb.append(" outputclass=");
            sb.append(this._Outputclass.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }
}

