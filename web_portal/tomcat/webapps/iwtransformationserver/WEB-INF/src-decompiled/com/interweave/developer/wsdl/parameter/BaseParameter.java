/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.parameter.Parameter;

public abstract class BaseParameter
implements Parameter {
    protected String paramname = null;
    protected String type = null;
    protected int minOccurs = 1;
    protected int maxOccurs = 1;
    protected boolean nillable = false;
    protected String defaultvalue = null;
    protected String namespaceURI = null;

    BaseParameter(String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable, String aDefaultvalue) {
        this.paramname = aParamname;
        this.namespaceURI = aNamespaceURI;
        this.type = aType;
        this.defaultvalue = aDefaultvalue;
        if (aMinOccurs != null) {
            this.minOccurs = Integer.parseInt(aMinOccurs);
        }
        if (aMaxOccurs != null) {
            this.maxOccurs = aMaxOccurs.equals("unbounded") ? -1 : Integer.parseInt(aMaxOccurs);
        }
        if (aNillable != null) {
            this.nillable = aNillable.equals("true");
        }
    }

    abstract String getSimpleXML(SoapRequest var1);

    protected String getComment(SoapRequest soapRequest) {
        return "";
    }

    public String getXML(SoapRequest soapRequest) {
        String result = this.getSimpleXML(soapRequest);
        String comment = this.getComment(soapRequest);
        if (this.minOccurs != 1 || this.maxOccurs != 1) {
            if (this.minOccurs == 0 && this.maxOccurs == 1) {
                comment = String.valueOf(comment) + " optional ";
            } else if (this.minOccurs == 0 && this.maxOccurs == -1) {
                comment = String.valueOf(comment) + " Array[] ";
            } else {
                String max = this.maxOccurs == -1 ? "unbounded" : Integer.toString(this.maxOccurs);
                comment = String.valueOf(comment) + " occurences min=" + Integer.toString(this.minOccurs) + " max=" + max + " ";
                if (this.minOccurs > 1) {
                    String line = result;
                    int i = 0;
                    while (i < this.minOccurs) {
                        result = String.valueOf(result) + line;
                        ++i;
                    }
                }
            }
        }
        if (this.nillable) {
            comment = String.valueOf(comment) + "  nillable";
        }
        if (!comment.equals("")) {
            result = "<!--" + comment + "-->\r\n" + result;
        }
        return result;
    }
}

