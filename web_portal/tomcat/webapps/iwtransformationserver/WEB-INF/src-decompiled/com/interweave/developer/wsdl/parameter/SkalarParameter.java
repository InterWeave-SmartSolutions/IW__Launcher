/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl.parameter;

import com.interweave.developer.wsdl.SoapRequest;
import com.interweave.developer.wsdl.parameter.BaseParameter;
import java.util.HashMap;

public class SkalarParameter
extends BaseParameter {
    private static HashMap defaultsFromType = null;
    private static String[][] simpleTypeDefaults = new String[][]{{"string", "String"}, {"boolean", "true"}, {"decimal", "1.0"}, {"float", "1.0"}, {"double", "1.0"}, {"duration", "P1Y2M3DT10H30M0S"}, {"dateTime", "2003-06-26T07:31:00+01:00"}, {"time", "11:54:59.125+01:00"}, {"date", "2003-10-27"}, {"gYearMonth", "2003-10"}, {"gYear", "2003"}, {"gMonthDay", "-10-27"}, {"gDay", "--27"}, {"gMonth", "-10-"}, {"hexBinary", "0FB7"}, {"base64Binary", "UkZDMTUyMQo="}, {"anyURI", "http://any/URI"}, {"QName", "ns:name"}, {"NOTATION", "???"}, {"normalizedString", "Normalized String"}, {"token", "Token"}, {"language", "en"}, {"integer", "0"}, {"nonPositiveInteger", "0"}, {"negativeInteger", "-1"}, {"long", "0"}, {"int", "0"}, {"short", "0"}, {"byte", "0"}, {"nonNegativeInteger", "0"}, {"unsignedLong", "0"}, {"unsignedInt", "0"}, {"unsignedShort", "0"}, {"unsignedByte", "0"}, {"positiveInteger", "1"}};

    SkalarParameter(String aParamname, String aNamespaceURI, String aType, String aMinOccurs, String aMaxOccurs, String aNillable, String aDefaultvalue) {
        super(aParamname, aNamespaceURI, aType, aMinOccurs, aMaxOccurs, aNillable, aDefaultvalue);
    }

    public static String getDefaultValueForSimpleType(String type) {
        if (defaultsFromType == null) {
            HashMap<String, String> defaulValues = new HashMap<String, String>();
            int i = 0;
            while (i < simpleTypeDefaults.length) {
                defaulValues.put(simpleTypeDefaults[i][0], simpleTypeDefaults[i][1]);
                ++i;
            }
            defaultsFromType = defaulValues;
        }
        String result = (String)defaultsFromType.get(type);
        return result;
    }

    public String getSimpleXML(SoapRequest soapRequest) {
        String ns = soapRequest.getPrefix(this.namespaceURI);
        String result = "<" + this.paramname + " xsi:type=\"" + ns + this.type + "\">" + this.defaultvalue + "</" + this.paramname + ">\r\n";
        return result;
    }
}

