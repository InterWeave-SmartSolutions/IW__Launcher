/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import com.interweave.bindings.Parameter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Outputs
extends MarshallableObject
implements Element {
    private List _Parameter = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)new ParameterPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Parameter = new ParameterPredicate();

    public List getParameter() {
        return this._Parameter;
    }

    public void deleteParameter() {
        this._Parameter = null;
        this.invalidate();
    }

    public void emptyParameter() {
        this._Parameter = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Parameter, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (this._Parameter == null) {
            throw new MissingContentException("parameter");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        Iterator i = this._Parameter.iterator();
        while (i.hasNext()) {
            v.validate((ValidatableObject)i.next());
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("outputs");
        Iterator i = this._Parameter.iterator();
        while (i.hasNext()) {
            m.marshal((MarshallableObject)i.next());
        }
        w.end("outputs");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("outputs");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        List l = PredicatedLists.create((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Parameter, new ArrayList());
        while (xs.atStart("parameter")) {
            l.add((Parameter)u.unmarshal());
        }
        this._Parameter = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Parameter, (List)l);
        xs.takeEnd("outputs");
    }

    public static Outputs unmarshal(InputStream in) throws UnmarshalException {
        return Outputs.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Outputs unmarshal(XMLScanner xs) throws UnmarshalException {
        return Outputs.unmarshal(xs, Outputs.newDispatcher());
    }

    public static Outputs unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Outputs)d.unmarshal(xs, Outputs.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Outputs)) {
            return false;
        }
        Outputs tob = (Outputs)((Object)ob);
        if (this._Parameter != null) {
            if (tob._Parameter == null) {
                return false;
            }
            if (!this._Parameter.equals(tob._Parameter)) {
                return false;
            }
        } else if (tob._Parameter != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Parameter != null ? this._Parameter.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<outputs");
        if (this._Parameter != null) {
            sb.append(" parameter=");
            sb.append(this._Parameter.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }

    private static class ParameterPredicate
    implements PredicatedLists.Predicate {
        ParameterPredicate() {
        }

        public void check(Object ob) {
            if (!(ob instanceof Parameter)) {
                throw new InvalidContentObjectException(ob, Parameter.class);
            }
        }
    }
}

