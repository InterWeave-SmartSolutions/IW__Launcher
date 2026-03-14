/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Datamap
extends MarshallableObject
implements Element {
    private String _Name;
    private String _Driver;
    private String _Url;
    private String _User;
    private String _Password;
    private List _Access = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)new AccessPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Access = new AccessPredicate();

    public String getName() {
        return this._Name;
    }

    public void setName(String _Name) {
        this._Name = _Name;
        if (_Name == null) {
            this.invalidate();
        }
    }

    public String getDriver() {
        return this._Driver;
    }

    public void setDriver(String _Driver) {
        this._Driver = _Driver;
        if (_Driver == null) {
            this.invalidate();
        }
    }

    public String getUrl() {
        return this._Url;
    }

    public void setUrl(String _Url) {
        this._Url = _Url;
        if (_Url == null) {
            this.invalidate();
        }
    }

    public String getUser() {
        return this._User;
    }

    public void setUser(String _User) {
        this._User = _User;
        if (_User == null) {
            this.invalidate();
        }
    }

    public String getPassword() {
        return this._Password;
    }

    public void setPassword(String _Password) {
        this._Password = _Password;
        if (_Password == null) {
            this.invalidate();
        }
    }

    public List getAccess() {
        return this._Access;
    }

    public void deleteAccess() {
        this._Access = null;
        this.invalidate();
    }

    public void emptyAccess() {
        this._Access = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Access, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (this._Name == null) {
            throw new MissingAttributeException("name");
        }
        if (this._Driver == null) {
            throw new MissingContentException("driver");
        }
        if (this._Url == null) {
            throw new MissingContentException("url");
        }
        if (this._User == null) {
            throw new MissingContentException("user");
        }
        if (this._Password == null) {
            throw new MissingContentException("password");
        }
        if (this._Access == null) {
            throw new MissingContentException("access");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        Iterator i = this._Access.iterator();
        while (i.hasNext()) {
            v.validate((ValidatableObject)i.next());
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("datamap");
        w.attribute("name", this._Name.toString());
        w.leaf("driver", this._Driver.toString());
        w.leaf("url", this._Url.toString());
        w.leaf("user", this._User.toString());
        w.leaf("password", this._Password.toString());
        Iterator i = this._Access.iterator();
        while (i.hasNext()) {
            m.marshal((MarshallableObject)i.next());
        }
        w.end("datamap");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("datamap");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("name")) {
                if (this._Name != null) {
                    throw new DuplicateAttributeException(an);
                }
                this._Name = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("driver")) {
            xs.takeStart("driver");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Driver = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("driver", (Throwable)x);
            }
            xs.takeEnd("driver");
        }
        if (xs.atStart("url")) {
            xs.takeStart("url");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Url = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("url", (Throwable)x);
            }
            xs.takeEnd("url");
        }
        if (xs.atStart("user")) {
            xs.takeStart("user");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._User = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("user", (Throwable)x);
            }
            xs.takeEnd("user");
        }
        if (xs.atStart("password")) {
            xs.takeStart("password");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Password = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("password", (Throwable)x);
            }
            xs.takeEnd("password");
        }
        List l = PredicatedLists.create((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Access, new ArrayList());
        while (xs.atStart("access")) {
            l.add((Access)u.unmarshal());
        }
        this._Access = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Access, (List)l);
        xs.takeEnd("datamap");
    }

    public static Datamap unmarshal(InputStream in) throws UnmarshalException {
        return Datamap.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Datamap unmarshal(XMLScanner xs) throws UnmarshalException {
        return Datamap.unmarshal(xs, Datamap.newDispatcher());
    }

    public static Datamap unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Datamap)d.unmarshal(xs, Datamap.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Datamap)) {
            return false;
        }
        Datamap tob = (Datamap)((Object)ob);
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
        if (this._Driver != null) {
            if (tob._Driver == null) {
                return false;
            }
            if (!this._Driver.equals(tob._Driver)) {
                return false;
            }
        } else if (tob._Driver != null) {
            return false;
        }
        if (this._Url != null) {
            if (tob._Url == null) {
                return false;
            }
            if (!this._Url.equals(tob._Url)) {
                return false;
            }
        } else if (tob._Url != null) {
            return false;
        }
        if (this._User != null) {
            if (tob._User == null) {
                return false;
            }
            if (!this._User.equals(tob._User)) {
                return false;
            }
        } else if (tob._User != null) {
            return false;
        }
        if (this._Password != null) {
            if (tob._Password == null) {
                return false;
            }
            if (!this._Password.equals(tob._Password)) {
                return false;
            }
        } else if (tob._Password != null) {
            return false;
        }
        if (this._Access != null) {
            if (tob._Access == null) {
                return false;
            }
            if (!this._Access.equals(tob._Access)) {
                return false;
            }
        } else if (tob._Access != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Name != null ? this._Name.hashCode() : 0);
        h = 127 * h + (this._Driver != null ? this._Driver.hashCode() : 0);
        h = 127 * h + (this._Url != null ? this._Url.hashCode() : 0);
        h = 127 * h + (this._User != null ? this._User.hashCode() : 0);
        h = 127 * h + (this._Password != null ? this._Password.hashCode() : 0);
        h = 127 * h + (this._Access != null ? this._Access.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<datamap");
        if (this._Name != null) {
            sb.append(" name=");
            sb.append(this._Name.toString());
        }
        if (this._Driver != null) {
            sb.append(" driver=");
            sb.append(this._Driver.toString());
        }
        if (this._Url != null) {
            sb.append(" url=");
            sb.append(this._Url.toString());
        }
        if (this._User != null) {
            sb.append(" user=");
            sb.append(this._User.toString());
        }
        if (this._Password != null) {
            sb.append(" password=");
            sb.append(this._Password.toString());
        }
        if (this._Access != null) {
            sb.append(" access=");
            sb.append(this._Access.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }

    private static class AccessPredicate
    implements PredicatedLists.Predicate {
        AccessPredicate() {
        }

        public void check(Object ob) {
            if (!(ob instanceof Access)) {
                throw new InvalidContentObjectException(ob, Access.class);
            }
        }
    }
}

