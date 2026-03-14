/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import com.interweave.developer.IWXMLTag;
import java.util.Enumeration;

public class IWXMLDocument {
    public boolean allowMultiple = true;
    IWXMLTag root = new IWXMLTag();

    public void build(String inputStr) {
        this.root.makeTag(inputStr);
        this.makeTags(this.root);
    }

    public IWXMLTag makeTag(IWXMLTag tag) {
        boolean posStart = false;
        IWXMLTag newTag = new IWXMLTag();
        Object childTag = null;
        newTag.makeTag(tag.tagData);
        if (!this.allowMultiple) {
            if (tag.get(newTag.tagName) == null) {
                tag.children.add(newTag);
            }
        } else {
            tag.children.add(newTag);
        }
        this.makeTags(newTag);
        return newTag;
    }

    public void makeTags(IWXMLTag tag) {
        int posStart = 0;
        IWXMLTag childTag = null;
        while (tag.tagData.startsWith("<")) {
            childTag = this.makeTag(tag);
            if (childTag != null) {
                String tagString = null;
                tagString = !childTag.isNullTag ? "</" + childTag.tagName + ">" : "<" + childTag.tagName + "/>";
                posStart = tag.tagData.indexOf(tagString);
                if ((posStart += tagString.length()) < 0) continue;
                if (posStart == tag.tagData.length()) {
                    tag.tagData = "";
                    continue;
                }
                tag.tagData = tag.tagData.substring(posStart).trim();
                continue;
            }
            childTag.tagData = "";
        }
    }

    public String makeRequestTransform() {
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        responseBuffer.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n");
        responseBuffer.append("<xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/>\n");
        responseBuffer.append("<xsl:template match=\"/\">\n");
        responseBuffer.append(this.root.makeRequest());
        responseBuffer.append("</xsl:template>\n");
        responseBuffer.append("</xsl:stylesheet>\n");
        return responseBuffer.toString();
    }

    public String makeResponseTransform() {
        IWXMLTag body = this.root.get("soap:Body");
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        responseBuffer.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n");
        responseBuffer.append("<xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/>\n");
        responseBuffer.append("<xsl:template match=\"/\">\n");
        responseBuffer.append("<xsl:for-each select=\"/*/*\">\n");
        Enumeration enumerate = body.children.elements();
        while (enumerate.hasMoreElements()) {
            IWXMLTag tag = (IWXMLTag)enumerate.nextElement();
            responseBuffer.append(tag.makeResponse());
        }
        responseBuffer.append("</xsl:for-each>\n");
        responseBuffer.append("</xsl:template>\n");
        responseBuffer.append("</xsl:stylesheet>\n");
        return responseBuffer.toString();
    }
}

