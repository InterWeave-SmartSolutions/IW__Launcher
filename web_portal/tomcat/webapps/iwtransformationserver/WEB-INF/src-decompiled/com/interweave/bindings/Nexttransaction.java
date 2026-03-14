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

public class Nexttransaction
extends MarshallableObject
implements Element {
    private String _Name;
    private String _Type;
    private String _Error;
    private String _Content;

    public String getName() {
        return this._Name;
    }

    public String getName(String currentFlow) {
        if (this._Name.indexOf(":") > 0 && this._Name.indexOf(";") > 0) {
            String[] perFlow;
            String[] stringArray = perFlow = this._Name.split(";");
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnm = stringArray[n];
                int dlm = cnm.indexOf(":");
                if (dlm > 0 && cnm.substring(0, dlm).equals(currentFlow)) {
                    return cnm.substring(dlm + 1);
                }
                ++n;
            }
            return "";
        }
        return this._Name;
    }

    public void setName(String _Name) {
        this._Name = _Name;
        if (_Name == null) {
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

    public String getError() {
        return this._Error;
    }

    public void setError(String _Error) {
        this._Error = _Error;
        if (_Error == null) {
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
        if (this._Name == null) {
            throw new MissingAttributeException("name");
        }
        if (this._Type == null) {
            throw new MissingAttributeException("type");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("nexttransaction");
        w.attribute("name", this._Name.toString());
        w.attribute("type", this._Type.toString());
        if (this._Error != null) {
            w.attribute("error", this._Error.toString());
        }
        if (this._Content != null) {
            w.chars(this._Content.toString());
        }
        w.end("nexttransaction");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("nexttransaction");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("name")) {
                if (this._Name != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Name = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (this._Type != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Type = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("error")) {
                if (this._Error != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Error = xs.takeAttributeValue();
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
        xs.takeEnd("nexttransaction");
    }

    public static Nexttransaction unmarshal(InputStream in) throws UnmarshalException {
        return Nexttransaction.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Nexttransaction unmarshal(XMLScanner xs) throws UnmarshalException {
        return Nexttransaction.unmarshal(xs, Nexttransaction.newDispatcher());
    }

    public static Nexttransaction unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Nexttransaction)d.unmarshal(xs, Nexttransaction.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Nexttransaction)) {
            return false;
        }
        Nexttransaction tob = (Nexttransaction)((Object)ob);
        if (this._Name != null) {
            if (tob._Name == null) {
                return false;
            }
            if (!this._Name.equals(tob._Name)) {
                return false;
            }
        } else if (tob._Name != null) {
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
        if (this._Error != null) {
            if (tob._Error == null) {
                return false;
            }
            if (!this._Error.equals(tob._Error)) {
                return false;
            }
        } else if (tob._Error != null) {
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
        h = 127 * h + (this._Name != null ? this._Name.hashCode() : 0);
        h = 127 * h + (this._Type != null ? this._Type.hashCode() : 0);
        h = 127 * h + (this._Error != null ? this._Error.hashCode() : 0);
        h = 127 * h + (this._Content != null ? this._Content.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<nexttransaction");
        if (this._Name != null) {
            sb.append(" name=");
            sb.append(this._Name.toString());
        }
        if (this._Type != null) {
            sb.append(" type=");
            sb.append(this._Type.toString());
        }
        if (this._Error != null) {
            sb.append(" error=");
            sb.append(this._Error.toString());
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

