/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.connector;

import com.interweave.bindings.Param;
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

public class IWXMLRequest
extends MarshallableRootElement
implements RootElement {
    private List _Param = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)new ParamPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Param = new ParamPredicate();

    public List getParam() {
        return this._Param;
    }

    public void deleteParam() {
        this._Param = null;
        this.invalidate();
    }

    public void emptyParam() {
        this._Param = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Param, new ArrayList());
    }

    public void validateThis() throws LocalValidationException {
        if (this._Param == null) {
            throw new MissingContentException("Param");
        }
    }

    public void validate(Validator v) throws StructureValidationException {
        Iterator i = this._Param.iterator();
        while (i.hasNext()) {
            v.validate((ValidatableObject)i.next());
        }
    }

    public void marshal(Marshaller m) throws IOException {
        XMLWriter w = m.writer();
        w.start("iwxmlrequest");
        Iterator i = this._Param.iterator();
        while (i.hasNext()) {
            m.marshal((MarshallableObject)i.next());
        }
        w.end("iwxmlrequest");
    }

    public void unmarshal(Unmarshaller u) throws UnmarshalException {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("iwxmlrequest");
        if (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        List l = PredicatedLists.create((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Param, new ArrayList());
        while (xs.atStart("param")) {
            l.add((Param)u.unmarshal());
        }
        this._Param = PredicatedLists.createInvalidating((MarshallableObject)this, (PredicatedLists.Predicate)this.pred_Param, (List)l);
        xs.takeEnd("iwxmlrequest");
    }

    public static IWXMLRequest unmarshal(InputStream in) throws UnmarshalException {
        return IWXMLRequest.unmarshal(XMLScanner.open((InputStream)in));
    }

    public static IWXMLRequest unmarshal(XMLScanner xs) throws UnmarshalException {
        return IWXMLRequest.unmarshal(xs, IWXMLRequest.newDispatcher());
    }

    public static IWXMLRequest unmarshal(XMLScanner xs, Dispatcher d) throws UnmarshalException {
        return (IWXMLRequest)d.unmarshal(xs, IWXMLRequest.class);
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof IWXMLRequest)) {
            return false;
        }
        IWXMLRequest tob = (IWXMLRequest)((Object)ob);
        if (this._Param != null) {
            if (tob._Param == null) {
                return false;
            }
            if (!this._Param.equals(tob._Param)) {
                return false;
            }
        } else if (tob._Param != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = 127 * h + (this._Param != null ? this._Param.hashCode() : 0);
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<iwxmlrequest");
        if (this._Param != null) {
            sb.append(" param=");
            sb.append(this._Param.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("IWXMLRequest", IWXMLRequest.class);
        d.register("Param", Param.class);
        d.freezeElementNameMap();
        return d;
    }

    private static class ParamPredicate
    implements PredicatedLists.Predicate {
        ParamPredicate() {
        }

        public void check(Object ob) {
            if (!(ob instanceof Param)) {
                throw new InvalidContentObjectException(ob, Param.class);
            }
        }
    }
}

