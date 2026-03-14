/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Datamap;
import com.interweave.bindings.Iwmappings;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Nexttransaction;
import com.interweave.bindings.Outputs;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Transaction;
import com.interweave.bindings.Translator;
import com.interweave.bindings.Values;
import com.interweave.bindings.Where;
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
import javax.xml.bind.MissingContentException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Access
extends MarshallableObject
implements Element {
    private String _Type;
    private String _Statementpre;
    private String _Statementpost;
    private Translator _Translator;
    private Where _Where;
    private Values _Values;
    private Outputs _Outputs;
    private boolean dynamic = false;

    public final boolean isDynamic() {
        return this.dynamic;
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

    public String getStatementpre() {
        return this._Statementpre;
    }

    public void setStatementpre(String _Statementpre) {
        this._Statementpre = _Statementpre;
        if (_Statementpre == null) {
            this.invalidate();
        }
    }

    public String getStatementpost() {
        return this._Statementpost;
    }

    public void setStatementpost(String _Statementpost) {
        this._Statementpost = _Statementpost;
        if (_Statementpost == null) {
            this.invalidate();
        }
    }

    public Translator getTranslator() {
        return this._Translator;
    }

    public void setTranslator(Translator _Translator) {
        this._Translator = _Translator;
        if (_Translator == null) {
            this.invalidate();
        }
    }

    public Where getWhere() {
        return this._Where;
    }

    public void setWhere(Where _Where) {
        this._Where = _Where;
        if (_Where == null) {
            this.invalidate();
        }
    }

    public Values getValues() {
        return this._Values;
    }

    public void setValues(Values _Values) {
        this._Values = _Values;
        if (_Values == null) {
            this.invalidate();
        }
    }

    public Outputs getOutputs() {
        return this._Outputs;
    }

    public void setOutputs(Outputs _Outputs) {
        this._Outputs = _Outputs;
        if (_Outputs == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (this._Type == null) {
            throw new MissingAttributeException("type");
        }
        if (this._Statementpre == null) {
            throw new MissingContentException("statementpre");
        }
        if (this._Statementpost == null) {
            throw new MissingContentException("statementpost");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        v.validate((ValidatableObject)this._Translator);
        v.validate((ValidatableObject)this._Where);
        v.validate((ValidatableObject)this._Values);
        v.validate((ValidatableObject)this._Outputs);
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("access");
        w.attribute("type", this._Type.toString());
        w.leaf("statementpre", this._Statementpre.toString());
        w.leaf("statementpost", this._Statementpost.toString());
        if (this._Translator != null) {
            m.marshal((MarshallableObject)this._Translator);
        }
        if (this._Where != null) {
            m.marshal((MarshallableObject)this._Where);
        }
        if (this._Values != null) {
            m.marshal((MarshallableObject)this._Values);
        }
        if (this._Outputs != null) {
            m.marshal((MarshallableObject)this._Outputs);
        }
        w.end("access");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("access");
        boolean dataType = false;
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("type")) {
                if (this._Type != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Type = xs.takeAttributeValue();
                if (this._Type.compareToIgnoreCase("dynamic") == 0) {
                    this._Type = "procedure";
                    this.dynamic = true;
                    continue;
                }
                if (this._Type.compareToIgnoreCase("data") != 0) continue;
                this._Type = "procedure";
                dataType = true;
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("statementpre")) {
            xs.takeStart("statementpre");
            int wsp = dataType ? 2 : 0;
            s = xs.atChars(wsp) ? xs.takeChars(wsp) : "";
            try {
                this._Statementpre = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("statementpre", (Throwable)x);
            }
            xs.takeEnd("statementpre");
        }
        if (xs.atStart("statementpost")) {
            xs.takeStart("statementpost");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Statementpost = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("statementpost", (Throwable)x);
            }
            xs.takeEnd("statementpost");
        }
        if (xs.atStart("translator")) {
            this._Translator = (Translator)u.unmarshal();
        }
        if (xs.atStart("where")) {
            this._Where = (Where)u.unmarshal();
        }
        if (xs.atStart("values")) {
            this._Values = (Values)u.unmarshal();
        }
        if (xs.atStart("outputs")) {
            this._Outputs = (Outputs)u.unmarshal();
        }
        xs.takeEnd("access");
    }

    public static Access unmarshal(InputStream in) throws UnmarshalException {
        return Access.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Access unmarshal(XMLScanner xs) throws UnmarshalException {
        return Access.unmarshal(xs, Access.newDispatcher());
    }

    public static Access unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Access)d.unmarshal(xs, Access.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Access)) {
            return false;
        }
        Access tob = (Access)((Object)ob);
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
        if (this._Statementpre != null) {
            if (tob._Statementpre == null) {
                return false;
            }
            if (!this._Statementpre.equals(tob._Statementpre)) {
                return false;
            }
        } else if (tob._Statementpre != null) {
            return false;
        }
        if (this._Statementpost != null) {
            if (tob._Statementpost == null) {
                return false;
            }
            if (!this._Statementpost.equals(tob._Statementpost)) {
                return false;
            }
        } else if (tob._Statementpost != null) {
            return false;
        }
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
        if (this._Where != null) {
            if (tob._Where == null) {
                return false;
            }
            if (!this._Where.equals((Object)tob._Where)) {
                return false;
            }
        } else if (tob._Where != null) {
            return false;
        }
        if (this._Values != null) {
            if (tob._Values == null) {
                return false;
            }
            if (!this._Values.equals((Object)tob._Values)) {
                return false;
            }
        } else if (tob._Values != null) {
            return false;
        }
        if (this._Outputs != null) {
            if (tob._Outputs == null) {
                return false;
            }
            if (!this._Outputs.equals((Object)tob._Outputs)) {
                return false;
            }
        } else if (tob._Outputs != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Type != null ? this._Type.hashCode() : 0);
        h = 127 * h + (this._Statementpre != null ? this._Statementpre.hashCode() : 0);
        h = 127 * h + (this._Statementpost != null ? this._Statementpost.hashCode() : 0);
        h = 127 * h + (this._Translator != null ? this._Translator.hashCode() : 0);
        h = 127 * h + (this._Where != null ? this._Where.hashCode() : 0);
        h = 127 * h + (this._Values != null ? this._Values.hashCode() : 0);
        h = 127 * h + (this._Outputs != null ? this._Outputs.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<access");
        if (this._Type != null) {
            sb.append(" type=");
            sb.append(this._Type.toString());
        }
        if (this._Statementpre != null) {
            sb.append(" statementpre=");
            sb.append(this._Statementpre.toString());
        }
        if (this._Statementpost != null) {
            sb.append(" statementpost=");
            sb.append(this._Statementpost.toString());
        }
        if (this._Translator != null) {
            sb.append(" translator=");
            sb.append(this._Translator.toString());
        }
        if (this._Where != null) {
            sb.append(" where=");
            sb.append(this._Where.toString());
        }
        if (this._Values != null) {
            sb.append(" values=");
            sb.append(this._Values.toString());
        }
        if (this._Outputs != null) {
            sb.append(" outputs=");
            sb.append(this._Outputs.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("access", Access.class);
        d.register("datamap", Datamap.class);
        d.register("iwmappings", Iwmappings.class);
        d.register("mapping", Mapping.class);
        d.register("nexttransaction", Nexttransaction.class);
        d.register("outputs", Outputs.class);
        d.register("parameter", Parameter.class);
        d.register("transaction", Transaction.class);
        d.register("translator", Translator.class);
        d.register("values", Values.class);
        d.register("where", Where.class);
        d.freezeElementNameMap();
        return d;
    }
}

