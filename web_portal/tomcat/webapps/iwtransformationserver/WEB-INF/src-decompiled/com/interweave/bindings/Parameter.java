/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Translator;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Parameter
extends MarshallableObject
implements Element {
    private Translator _Translator;
    private String _Input;
    private Mapping _Mapping;

    public Translator getTranslator() {
        return this._Translator;
    }

    public void setTranslator(Translator _Translator) {
        this._Translator = _Translator;
        if (_Translator == null) {
            this.invalidate();
        }
    }

    public String getInput() {
        return this._Input;
    }

    public void setInput(String _Input) {
        this._Input = _Input;
        if (_Input == null) {
            this.invalidate();
        }
    }

    public Mapping getMapping() {
        return this._Mapping;
    }

    public void setMapping(Mapping _Mapping) {
        this._Mapping = _Mapping;
        if (_Mapping == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (this._Input == null) {
            throw new MissingContentException("input");
        }
        if (this._Mapping == null) {
            throw new MissingContentException("mapping");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        v.validate((ValidatableObject)this._Translator);
        v.validate((ValidatableObject)this._Mapping);
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("parameter");
        if (this._Translator != null) {
            m.marshal((MarshallableObject)this._Translator);
        }
        w.leaf("input", this._Input.toString());
        m.marshal((MarshallableObject)this._Mapping);
        w.end("parameter");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("parameter");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("translator")) {
            this._Translator = (Translator)u.unmarshal();
        }
        if (xs.atStart("input")) {
            xs.takeStart("input");
            String s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Input = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("input", (Throwable)x);
            }
            xs.takeEnd("input");
        }
        this._Mapping = (Mapping)u.unmarshal();
        xs.takeEnd("parameter");
    }

    public static Parameter unmarshal(InputStream in) throws UnmarshalException {
        return Parameter.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Parameter unmarshal(XMLScanner xs) throws UnmarshalException {
        return Parameter.unmarshal(xs, Parameter.newDispatcher());
    }

    public static Parameter unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Parameter)d.unmarshal(xs, Parameter.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Parameter)) {
            return false;
        }
        Parameter tob = (Parameter)((Object)ob);
        if (this._Translator != null) {
            if (tob._Translator == null) {
                return false;
            }
            if (!this._Translator.equals((Object)tob._Translator)) {
                return false;
            }
        } else if (tob._Translator != null) {
            return false;
        }
        if (this._Input != null) {
            if (tob._Input == null) {
                return false;
            }
            if (!this._Input.equals(tob._Input)) {
                return false;
            }
        } else if (tob._Input != null) {
            return false;
        }
        if (this._Mapping != null) {
            if (tob._Mapping == null) {
                return false;
            }
            if (!this._Mapping.equals((Object)tob._Mapping)) {
                return false;
            }
        } else if (tob._Mapping != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Translator != null ? this._Translator.hashCode() : 0);
        h = 127 * h + (this._Input != null ? this._Input.hashCode() : 0);
        h = 127 * h + (this._Mapping != null ? this._Mapping.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<parameter");
        if (this._Translator != null) {
            sb.append(" translator=");
            sb.append(this._Translator.toString());
        }
        if (this._Input != null) {
            sb.append(" input=");
            sb.append(this._Input.toString());
        }
        if (this._Mapping != null) {
            sb.append(" mapping=");
            sb.append(this._Mapping.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }
}

