/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import com.interweave.bindings.Transaction;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;

public class Iwmappings
extends MarshallableRootElement
implements RootElement {
    private List _Transaction = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)new TransactionPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Transaction = new TransactionPredicate();

    public List getTransaction() {
        return this._Transaction;
    }

    public void deleteTransaction() {
        this._Transaction = null;
        this.invalidate();
    }

    public void emptyTransaction() {
        this._Transaction = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Transaction, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (this._Transaction == null) {
            throw new MissingContentException("transaction");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        Iterator i = this._Transaction.iterator();
        while (i.hasNext()) {
            v.validate((ValidatableObject)i.next());
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("iwmappings");
        Iterator i = this._Transaction.iterator();
        while (i.hasNext()) {
            m.marshal((MarshallableObject)i.next());
        }
        w.end("iwmappings");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("iwmappings");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        List l = PredicatedLists.create((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Transaction, new ArrayList());
        while (xs.atStart("transaction")) {
            l.add((Transaction)u.unmarshal());
        }
        this._Transaction = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Transaction, (List)l);
        xs.takeEnd("iwmappings");
    }

    public static Iwmappings unmarshal(InputStream in) throws UnmarshalException {
        return Iwmappings.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static Iwmappings unmarshal(XMLScanner xs) throws UnmarshalException {
        return Iwmappings.unmarshal(xs, Iwmappings.newDispatcher());
    }

    public static Iwmappings unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (Iwmappings)d.unmarshal(xs, Iwmappings.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Iwmappings)) {
            return false;
        }
        Iwmappings tob = (Iwmappings)((Object)ob);
        if (this._Transaction != null) {
            if (tob._Transaction == null) {
                return false;
            }
            if (!this._Transaction.equals(tob._Transaction)) {
                return false;
            }
        } else if (tob._Transaction != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Transaction != null ? this._Transaction.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<iwmappings");
        if (this._Transaction != null) {
            sb.append(" transaction=");
            sb.append(this._Transaction.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Access.newDispatcher();
    }

    private static class TransactionPredicate
    implements PredicatedLists.Predicate {
        TransactionPredicate() {
        }

        public void check(Object ob) {
            if (!(ob instanceof Transaction)) {
                throw new InvalidContentObjectException(ob, Transaction.class);
            }
        }
    }
}

