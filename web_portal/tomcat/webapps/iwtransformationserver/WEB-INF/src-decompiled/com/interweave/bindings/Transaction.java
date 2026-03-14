/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.Nexttransaction;
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

public class Transaction
extends MarshallableObject
implements Element {
    private String _Name;
    private String _Type;
    private String _Transform;
    private String _Classname;
    private Nexttransaction _Nexttransaction;
    private List _Datamap = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)new DatamapPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Datamap = new DatamapPredicate();

    public String getName() {
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

    public String getTransform() {
        return this._Transform;
    }

    public void setTransform(String _Transform) {
        this._Transform = _Transform;
        if (_Transform == null) {
            this.invalidate();
        }
    }

    public String getClassname() {
        return this._Classname;
    }

    public void setClassname(String _Classname) {
        this._Classname = _Classname;
        if (_Classname == null) {
            this.invalidate();
        }
    }

    public Nexttransaction getNexttransaction() {
        return this._Nexttransaction;
    }

    public void setNexttransaction(Nexttransaction _Nexttransaction) {
        this._Nexttransaction = _Nexttransaction;
        if (_Nexttransaction == null) {
            this.invalidate();
        }
    }

    public List getDatamap() {
        return this._Datamap;
    }

    public void deleteDatamap() {
        this._Datamap = null;
        this.invalidate();
    }

    public void emptyDatamap() {
        this._Datamap = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Datamap, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (this._Name == null) {
            throw new MissingAttributeException("name");
        }
        if (this._Type == null) {
            throw new MissingAttributeException("type");
        }
        if (this._Transform == null) {
            throw new MissingContentException("transform");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        v.validate((ValidatableObject)this._Nexttransaction);
        Iterator i = this._Datamap.iterator();
        while (i.hasNext()) {
            v.validate((ValidatableObject)i.next());
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("transaction");
        w.attribute("name", this._Name.toString());
        w.attribute("type", this._Type.toString());
        w.leaf("transform", this._Transform.toString());
        if (this._Classname != null) {
            w.leaf("classname", this._Classname.toString());
        }
        if (this._Nexttransaction != null) {
            m.marshal((MarshallableObject)this._Nexttransaction);
        }
        if (this._Datamap.size() > 0) {
            Iterator i = this._Datamap.iterator();
            while (i.hasNext()) {
                m.marshal((MarshallableObject)i.next());
            }
        }
        w.end("transaction");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        String s;
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("transaction");
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
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("transform")) {
            xs.takeStart("transform");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Transform = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("transform", (Throwable)x);
            }
            xs.takeEnd("transform");
        }
        if (xs.atStart("classname")) {
            xs.takeStart("classname");
            s = xs.atChars(0) ? xs.takeChars(0) : "";
            try {
                this._Classname = String.valueOf(s);
            }
            catch (Exception x) {
                throw new ConversionException("classname", (Throwable)x);
            }
            xs.takeEnd("classname");
        }
        if (xs.atStart("nexttransaction")) {
            this._Nexttransaction = (Nexttransaction)u.unmarshal();
        }
        List l = PredicatedLists.create((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Datamap, new ArrayList());
        while (xs.atStart("datamap")) {
            l.add((Datamap)u.unmarshal());
        }
        this._Datamap = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Datamap, (List)l);
        xs.takeEnd("transaction");
    }

    public static Transaction unmarshal(InputStream in) throws UnmarshalException {
        return Transaction.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Transaction unmarshal(XMLScanner xs) throws UnmarshalException {
        return Transaction.unmarshal(xs, Transaction.newDispatcher());
    }

    public static Transaction unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Transaction)d.unmarshal(xs, Transaction.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Transaction)) {
            return false;
        }
        Transaction tob = (Transaction)((Object)ob);
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
        if (this._Transform != null) {
            if (tob._Transform == null) {
                return false;
            }
            if (!this._Transform.equals(tob._Transform)) {
                return false;
            }
        } else if (tob._Transform != null) {
            return false;
        }
        if (this._Classname != null) {
            if (tob._Classname == null) {
                return false;
            }
            if (!this._Classname.equals(tob._Classname)) {
                return false;
            }
        } else if (tob._Classname != null) {
            return false;
        }
        if (this._Nexttransaction != null) {
            if (tob._Nexttransaction == null) {
                return false;
            }
            if (!this._Nexttransaction.equals((Object)tob._Nexttransaction)) {
                return false;
            }
        } else if (tob._Nexttransaction != null) {
            return false;
        }
        if (this._Datamap != null) {
            if (tob._Datamap == null) {
                return false;
            }
            if (!this._Datamap.equals(tob._Datamap)) {
                return false;
            }
        } else if (tob._Datamap != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Name != null ? this._Name.hashCode() : 0);
        h = 127 * h + (this._Type != null ? this._Type.hashCode() : 0);
        h = 127 * h + (this._Transform != null ? this._Transform.hashCode() : 0);
        h = 127 * h + (this._Classname != null ? this._Classname.hashCode() : 0);
        h = 127 * h + (this._Nexttransaction != null ? this._Nexttransaction.hashCode() : 0);
        h = 127 * h + (this._Datamap != null ? this._Datamap.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<transaction");
        if (this._Name != null) {
            sb.append(" name=");
            sb.append(this._Name.toString());
        }
        if (this._Type != null) {
            sb.append(" type=");
            sb.append(this._Type.toString());
        }
        if (this._Transform != null) {
            sb.append(" transform=");
            sb.append(this._Transform.toString());
        }
        if (this._Classname != null) {
            sb.append(" classname=");
            sb.append(this._Classname.toString());
        }
        if (this._Nexttransaction != null) {
            sb.append(" nexttransaction=");
            sb.append(this._Nexttransaction.toString());
        }
        if (this._Datamap != null) {
            sb.append(" datamap=");
            sb.append(this._Datamap.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }

    private static class DatamapPredicate
    implements PredicatedLists.Predicate {
        DatamapPredicate() {
        }

        public void check(Object ob) {
            if (!(ob instanceof Datamap)) {
                throw new InvalidContentObjectException(ob, Datamap.class);
            }
        }
    }
}

