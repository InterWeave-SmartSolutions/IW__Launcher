/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Translator;
import com.interweave.core.IWReplaceString;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.interweave.core.IWTagStream;

public class IWMappingType {
    public String getData(Parameter param, IWRequest request) throws Exception {
        String strXpath = null;
        String strParam = null;
        String strValue = null;
        String strValueSuffix = null;
        String strData = "";
        Mapping mapping = param.getMapping();
        Translator translator = param.getTranslator();
        String type = mapping.getType();
        try {
            strData = request.getTranResponse();
        }
        catch (Exception e) {
            strData = "";
        }
        strParam = param.getInput();
        if (type != null && type != "") {
            if (type.compareToIgnoreCase("xpath") == 0 || type.compareToIgnoreCase("xpath_number") == 0) {
                strXpath = null;
                strXpath = param.getMapping().getContent();
                if (strXpath != null) {
                    request.lnkIWServices.logConsole("<!-- Mapping Type XPath " + strXpath + " for " + strData + " -->", IWServices.LOG_IO, request);
                    int sfi = strXpath.indexOf("+C::");
                    if (sfi > 0) {
                        strValueSuffix = strXpath.substring(sfi + 4);
                        strXpath = strXpath.substring(0, sfi);
                    }
                    strValue = null;
                    strValue = request.getXsltc().applyXPath(strData, strXpath);
                }
                if (strValue == null) {
                    strValue = "";
                }
                if (type.compareToIgnoreCase("xpath_number") == 0 && strValue.length() == 0) {
                    strValue = "0";
                }
                if (strValueSuffix != null) {
                    strValue = String.valueOf(strValue) + strValueSuffix;
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type XPath Data " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
                request.setParam(strParam, strValue);
            } else if (type.compareToIgnoreCase("reflect") == 0) {
                strValue = param.getMapping().getContent();
                if (strValue == null) {
                    strValue = "";
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type Reflect " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
                request.setParam(strParam, strValue);
            } else if (type.compareToIgnoreCase("default") == 0) {
                strValue = request.getParameter(param.getInput());
                if (strValue == null || strValue.length() == 0) {
                    strValue = param.getMapping().getContent();
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type Default " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
                request.setParam(strParam, strValue);
            } else if (type.compareToIgnoreCase("htmlescape") == 0) {
                strValue = request.getParameter(param.getInput());
                StringBuffer replacer = new StringBuffer();
                try {
                    byte[] bytes = strValue.getBytes();
                    int charCount = 0;
                    while (charCount < strValue.length()) {
                        char ch = (char)bytes[charCount];
                        if (bytes[charCount] > 31) {
                            replacer.append(ch);
                        }
                        ++charCount;
                    }
                }
                catch (Exception charCount) {
                    // empty catch block
                }
                strValue = replacer.toString();
                replacer = null;
                strValue = this.replace(strValue, "&nbsp;", "");
                strValue = this.replace(strValue, "&", "&amp;");
                strValue = this.replace(strValue, "'", "\\&apos;");
                strValue = this.replace(strValue, "\"", "\\&quot;");
                strValue = this.replace(strValue, "<", "&lt;");
                strValue = this.replace(strValue, ">", "&gt;");
                if (strValue == null) {
                    strValue = "";
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type Html Escape " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
            } else if (type.compareToIgnoreCase("postxpath") == 0) {
                strXpath = null;
                strXpath = param.getMapping().getContent();
                if (strXpath != null) {
                    request.lnkIWServices.logConsole("<!-- Mapping Type Post XPath " + strXpath + " -->", IWServices.LOG_IO, request);
                    strValue = null;
                    strValue = request.getXsltc().applyXPath(strData, strXpath);
                }
                if (strValue == null) {
                    strValue = "";
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type Post XPath " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
                request.addPost(strParam, strValue);
            } else if (type.compareToIgnoreCase("post") == 0) {
                strValue = param.getMapping().getContent();
                if (strValue == null) {
                    strValue = "";
                }
                request.lnkIWServices.logConsole("<!-- Mapping Type Post " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
                request.addPost(strParam, strValue);
            } else if (type.compareToIgnoreCase("constant") == 0) {
                strValue = param.getInput();
                request.lnkIWServices.logConsole("<!-- Mapping Type Constant " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
            } else if (type.compareToIgnoreCase("transform") == 0) {
                String tempTransform = request.getXsltc().strTransform;
                request.getXsltc().strTransform = param.getInput();
                strValue = request.getXsltc().executeTransform(request.getTranResponse(), request);
                request.getXsltc().strTransform = tempTransform;
                request.lnkIWServices.logConsole("<!-- Mapping Type Transform " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
            } else {
                strValue = request.getParameter(param.getInput());
                request.lnkIWServices.logConsole("<!-- Other Mapping Type Request Data " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
            }
        } else if (translator != null && translator.getInputclass() != null && translator.getInputclass().length() > 0) {
            IWRequest iwRequest = new IWRequest(request);
            iwRequest.getXsltc().strTransform = translator.getInputclass();
            String inStr = request.getTranResponse();
            strValue = iwRequest.getXsltc().executeTransform(inStr, request);
            IWTagStream tags = new IWTagStream();
            strValue = tags.removeXMLDecl(strValue);
            strValue = IWReplaceString.replace(strValue, "<br/>", "\n");
            request.lnkIWServices.logConsole("<!-- Mapping Type Request Translator Result\n\n" + strValue + " -->", IWServices.LOG_IO, request);
        } else {
            strValue = request.getParameter(param.getInput());
            request.lnkIWServices.logConsole("<!-- Mapping Type Request Data " + strParam + ":" + strValue + " -->", IWServices.LOG_IO, request);
        }
        strParam = null;
        strData = null;
        type = null;
        if (strValue == null || strValue.compareToIgnoreCase("null") == 0) {
            strValue = "";
        }
        return strValue;
    }

    public String replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }
}

