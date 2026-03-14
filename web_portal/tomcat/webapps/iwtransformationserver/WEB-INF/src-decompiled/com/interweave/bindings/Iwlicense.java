/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Iwlicense
extends MarshallableRootElement
implements RootElement {
    private String _Guid;
    private String _Expiretype;
    private String _Month;
    private String _Day;
    private String _Year;

    public String getGuid() {
        return this._Guid;
    }

    public void setGuid(String _Guid) {
        this._Guid = _Guid;
        if (_Guid == null) {
            this.invalidate();
        }
    }

    public String getExpiretype() {
        return this._Expiretype;
    }

    public void setExpiretype(String _Expiretype) {
        this._Expiretype = _Expiretype;
        if (_Expiretype == null) {
            this.invalidate();
        }
    }

    public String getMonth() {
        return this._Month;
    }

    public void setMonth(String _Month) {
        this._Month = _Month;
        if (_Month == null) {
            this.invalidate();
        }
    }

    public String getDay() {
        return this._Day;
    }

    public void setDay(String _Day) {
        this._Day = _Day;
        if (_Day == null) {
            this.invalidate();
        }
    }

    public String getYear() {
        return this._Year;
    }

    public void setYear(String _Year) {
        this._Year = _Year;
        if (_Year == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (this._Guid == null) {
            throw new MissingContentException("guid");
        }
        if (this._Expiretype == null) {
            throw new MissingContentException("expiretype");
        }
        if (this._Month == null) {
            throw new MissingContentException("month");
        }
        if (this._Day == null) {
            throw new MissingContentException("day");
        }
        if (this._Year == null) {
            throw new MissingContentException("year");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("iwlicense");
        w.leaf("guid", this._Guid.toString());
        w.leaf("expiretype", this._Expiretype.toString());
        w.leaf("month", this._Month.toString());
        w.leaf("day", this._Day.toString());
        w.leaf("year", this._Year.toString());
        w.end("iwlicense");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("iwlicense");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("guid")) {
            xs.takeStart("guid");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Guid = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("guid", (Throwable)x);
            }
            xs.takeEnd("guid");
        }
        if (xs.atStart("expiretype")) {
            xs.takeStart("expiretype");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Expiretype = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("expiretype", (Throwable)x);
            }
            xs.takeEnd("expiretype");
        }
        if (xs.atStart("month")) {
            xs.takeStart("month");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Month = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("month", (Throwable)x);
            }
            xs.takeEnd("month");
        }
        if (xs.atStart("day")) {
            xs.takeStart("day");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Day = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("day", (Throwable)x);
            }
            xs.takeEnd("day");
        }
        if (xs.atStart("year")) {
            xs.takeStart("year");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Year = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("year", (Throwable)x);
            }
            xs.takeEnd("year");
        }
        xs.takeEnd("iwlicense");
    }

    public static Iwlicense unmarshal(InputStream in) throws UnmarshalException {
        return Iwlicense.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Iwlicense unmarshal(XMLScanner xs) throws UnmarshalException {
        return Iwlicense.unmarshal(xs, Iwlicense.newDispatcher());
    }

    public static Iwlicense unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Iwlicense)d.unmarshal(xs, Iwlicense.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Iwlicense)) {
            return false;
        }
        Iwlicense tob = (Iwlicense)((Object)ob);
        if (this._Guid != null) {
            if (tob._Guid == null) {
                return false;
            }
            if (!this._Guid.equals(tob._Guid)) {
                return false;
            }
        } else if (tob._Guid != null) {
            return false;
        }
        if (this._Expiretype != null) {
            if (tob._Expiretype == null) {
                return false;
            }
            if (!this._Expiretype.equals(tob._Expiretype)) {
                return false;
            }
        } else if (tob._Expiretype != null) {
            return false;
        }
        if (this._Month != null) {
            if (tob._Month == null) {
                return false;
            }
            if (!this._Month.equals(tob._Month)) {
                return false;
            }
        } else if (tob._Month != null) {
            return false;
        }
        if (this._Day != null) {
            if (tob._Day == null) {
                return false;
            }
            if (!this._Day.equals(tob._Day)) {
                return false;
            }
        } else if (tob._Day != null) {
            return false;
        }
        if (this._Year != null) {
            if (tob._Year == null) {
                return false;
            }
            if (!this._Year.equals(tob._Year)) {
                return false;
            }
        } else if (tob._Year != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Guid != null ? this._Guid.hashCode() : 0);
        h = 127 * h + (this._Expiretype != null ? this._Expiretype.hashCode() : 0);
        h = 127 * h + (this._Month != null ? this._Month.hashCode() : 0);
        h = 127 * h + (this._Day != null ? this._Day.hashCode() : 0);
        h = 127 * h + (this._Year != null ? this._Year.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<iwlicense");
        if (this._Guid != null) {
            sb.append(" guid=");
            sb.append(this._Guid.toString());
        }
        if (this._Expiretype != null) {
            sb.append(" expiretype=");
            sb.append(this._Expiretype.toString());
        }
        if (this._Month != null) {
            sb.append(" month=");
            sb.append(this._Month.toString());
        }
        if (this._Day != null) {
            sb.append(" day=");
            sb.append(this._Day.toString());
        }
        if (this._Year != null) {
            sb.append(" year=");
            sb.append(this._Year.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("iwlicense", Iwlicense.class);
        d.freezeElementNameMap();
        return d;
    }
}

