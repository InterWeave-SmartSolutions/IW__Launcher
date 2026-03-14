/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import java.util.Enumeration;
import java.util.Vector;

public class IWXMLTag {
    public boolean isNullTag = false;
    public static int colNum = 1;
    public Vector children = new Vector();
    public String tagAttributes = "";
    public String tagName = "";
    public String tagData = "";
    public boolean dataTag = false;

    public void makeTag(String inputStr) {
        String outputStr;
        String tagStr = outputStr = this.removeXMLDecl(inputStr);
        int posStart = tagStr.indexOf("<");
        int posTag = 0;
        int posNullTag = 0;
        int posStop = 0;
        if (posStart < 0) {
            return;
        }
        tagStr = tagStr.substring(posStart + 1);
        posTag = tagStr.indexOf(">");
        posStop = tagStr.indexOf(" ");
        if (posStop > 0 && posStop < posTag) {
            this.tagName = tagStr.substring(0, posStop);
            posNullTag = this.tagName.indexOf("/");
            if (posNullTag >= 0) {
                this.isNullTag = true;
                this.tagName = this.tagName.substring(0, posNullTag);
            }
            posStart = posStop + 1;
            posStop = tagStr.indexOf(">");
            if (posStop < 0) {
                return;
            }
            this.tagAttributes = tagStr.substring(posStart, posStop);
        } else {
            posStop = posTag;
            this.tagName = tagStr.substring(0, posStop);
            posNullTag = this.tagName.indexOf("/");
            if (posNullTag >= 0) {
                this.isNullTag = true;
                this.tagName = this.tagName.substring(0, posNullTag);
            }
        }
        tagStr = tagStr.substring(posStop + 1);
        posStart = posStop;
        posStop = tagStr.indexOf("</" + this.tagName + ">");
        if (posStop < 0) {
            return;
        }
        this.tagData = tagStr.substring(0, posStop);
        this.tagData = this.tagData.trim();
        if (!this.tagData.startsWith("<")) {
            this.dataTag = true;
        }
    }

    public IWXMLTag get(String name) {
        Enumeration enumerate = this.children.elements();
        while (enumerate.hasMoreElements()) {
            IWXMLTag tag = (IWXMLTag)enumerate.nextElement();
            if (tag.tagName.compareToIgnoreCase(name) == 0) {
                return tag;
            }
            if ((tag = tag.get(name)) == null) continue;
            return tag;
        }
        return null;
    }

    public String makeRequest() {
        StringBuffer responseBuffer = new StringBuffer();
        if (this.tagAttributes.length() > 0) {
            responseBuffer.append("<" + this.tagName + " " + this.tagAttributes + ">\n");
        } else {
            responseBuffer.append("<" + this.tagName + ">\n");
        }
        Enumeration enumerate = this.children.elements();
        while (enumerate.hasMoreElements()) {
            IWXMLTag tag = (IWXMLTag)enumerate.nextElement();
            responseBuffer.append(tag.makeRequest());
        }
        if (this.dataTag) {
            responseBuffer.append("<xsl:value-of select=\"/*/Param[@name='" + this.tagName.toLowerCase() + "']\"/>\n");
        }
        responseBuffer.append("</" + this.tagName + ">\n");
        return responseBuffer.toString();
    }

    public String makeResponse() {
        boolean isRow = false;
        StringBuffer responseBuffer = new StringBuffer();
        if (this.children.size() > 0) {
            responseBuffer.append("<xsl:for-each select=\"" + this.tagName + "\">\n");
            Enumeration enumerate = this.children.elements();
            while (enumerate.hasMoreElements()) {
                IWXMLTag tag = (IWXMLTag)enumerate.nextElement();
                if (tag.children.size() == 0 && !isRow) {
                    colNum = 1;
                    isRow = true;
                    responseBuffer.append("<xsl:variable name=\"pos1\" select=\"position()\"/>\n<row number=\"{$pos1}\">\n");
                }
                responseBuffer.append(tag.makeResponse());
            }
            if (isRow) {
                responseBuffer.append("</row>\n");
            }
            responseBuffer.append("</xsl:for-each>\n");
        } else {
            responseBuffer.append("<col number=\"" + colNum++ + "\" name=\"" + this.tagName.toLowerCase() + "\"><xsl:value-of select=\"" + this.tagName + "\"/></col>\n");
        }
        return responseBuffer.toString();
    }

    public String tagString(boolean incTags) {
        StringBuffer responseBuffer = new StringBuffer();
        if (this.isNullTag) {
            responseBuffer.append("<" + this.tagName + "/>");
        } else {
            if (this.tagAttributes.length() > 0) {
                responseBuffer.append("<" + this.tagName + " " + this.tagAttributes + ">");
            } else {
                responseBuffer.append("<" + this.tagName + ">");
            }
            if (incTags || !this.tagData.startsWith("<")) {
                responseBuffer.append(this.tagData);
            }
            responseBuffer.append("</" + this.tagName + ">");
        }
        return responseBuffer.toString();
    }

    public String tagData(String inputStr) {
        int posStart = inputStr.indexOf(">");
        int posStop = 0;
        if (posStart < 0) {
            return null;
        }
        posStop = (inputStr = inputStr.substring(posStart + 1)).indexOf("<");
        if (posStop < 0) {
            return null;
        }
        String tagStr = inputStr.substring(0, posStop);
        return tagStr;
    }

    public String removeXMLDecl(String inputStr) {
        int pos = inputStr.indexOf("<?xml");
        if (pos < 0) {
            return inputStr;
        }
        pos = inputStr.indexOf("?>");
        return inputStr.substring(pos += 2);
    }
}

