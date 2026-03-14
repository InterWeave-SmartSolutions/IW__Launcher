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

public class Iwconfiguration
extends MarshallableRootElement
implements RootElement {
    private String _Appname;
    private String _Logginglevel;

    public String getAppname() {
        return this._Appname;
    }

    public void setAppname(String _Appname) {
        this._Appname = _Appname;
        if (_Appname == null) {
            this.invalidate();
        }
    }

    public String getLogginglevel() {
        return this._Logginglevel;
    }

    public void setLogginglevel(String _Logginglevel) {
        this._Logginglevel = _Logginglevel;
        if (_Logginglevel == null) {
            this.invalidate();
        }
    }

    public void validateThis() throws LocalValidationException {
        if (this._Appname == null) {
            throw new MissingContentException("appname");
        }
        if (this._Logginglevel == null) {
            throw new MissingContentException("logginglevel");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("iwconfiguration");
        w.leaf("appname", this._Appname.toString());
        w.leaf("logginglevel", this._Logginglevel.toString());
        w.end("iwconfiguration");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("iwconfiguration");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("appname")) {
            xs.takeStart("appname");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Appname = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("appname", (Throwable)x);
            }
            xs.takeEnd("appname");
        }
        if (xs.atStart("logginglevel")) {
            xs.takeStart("logginglevel");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Logginglevel = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("logginglevel", (Throwable)x);
            }
            xs.takeEnd("logginglevel");
        }
        xs.takeEnd("iwconfiguration");
    }

    public static Iwconfiguration unmarshal(InputStream in) throws UnmarshalException {
        return Iwconfiguration.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Iwconfiguration unmarshal(XMLScanner xs) throws UnmarshalException {
        return Iwconfiguration.unmarshal(xs, Iwconfiguration.newDispatcher());
    }

    public static Iwconfiguration unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Iwconfiguration)d.unmarshal(xs, Iwconfiguration.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Iwconfiguration)) {
            return false;
        }
        Iwconfiguration tob = (Iwconfiguration)((Object)ob);
        if (this._Appname != null) {
            if (tob._Appname == null) {
                return false;
            }
            if (!this._Appname.equals(tob._Appname)) {
                return false;
            }
        } else if (tob._Appname != null) {
            return false;
        }
        if (this._Logginglevel != null) {
            if (tob._Logginglevel == null) {
                return false;
            }
            if (!this._Logginglevel.equals(tob._Logginglevel)) {
                return false;
            }
        } else if (tob._Logginglevel != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Appname != null ? this._Appname.hashCode() : 0);
        h = 127 * h + (this._Logginglevel != null ? this._Logginglevel.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<iwconfiguration");
        if (this._Appname != null) {
            sb.append(" appname=");
            sb.append(this._Appname.toString());
        }
        if (this._Logginglevel != null) {
            sb.append(" logginglevel=");
            sb.append(this._Logginglevel.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("iwconfiguration", Iwconfiguration.class);
        d.freezeElementNameMap();
        return d;
    }
}

