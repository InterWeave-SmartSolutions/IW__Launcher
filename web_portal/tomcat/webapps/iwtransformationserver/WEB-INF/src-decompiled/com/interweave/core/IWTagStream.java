/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

public class IWTagStream {
    public boolean tagFound;

    public String startTag(String inputStr) {
        int posStart = inputStr.indexOf(">");
        if (posStart < 0) {
            return inputStr;
        }
        return inputStr.substring(0, posStart + 1);
    }

    public String stopTag(String inputStr) {
        int posStop = inputStr.lastIndexOf("<");
        if (posStop < 0) {
            return inputStr;
        }
        return inputStr.substring(posStop, inputStr.length());
    }

    public String tagRemove(String inputStr) {
        int posStart = inputStr.indexOf(">");
        if (posStart < 0) {
            return inputStr;
        }
        int posStop = inputStr.lastIndexOf("<");
        if (posStop < 0) {
            return inputStr;
        }
        return inputStr.substring(posStart + 1, posStop);
    }

    public String fragment(String inputStr, String start, String stop) {
        String out = this.stringFrom(inputStr, start);
        if (!this.tagFound) {
            return inputStr;
        }
        return this.stringIncluding(out, stop);
    }

    public String children(String inputStr, String start, String stop) {
        String out = this.stringFrom(inputStr, start);
        if (!this.tagFound) {
            return inputStr;
        }
        this.tagFound = true;
        int posStart = out.indexOf(">");
        if (posStart < 0) {
            this.tagFound = false;
            return inputStr;
        }
        out = out.substring(posStart + 1);
        return this.stringExcluding(out, stop);
    }

    public String tagData(String inputStr) {
        this.tagFound = true;
        int posStart = inputStr.indexOf(">");
        int posStop = 0;
        if (posStart < 0) {
            this.tagFound = false;
            return null;
        }
        posStop = (inputStr = inputStr.substring(posStart + 1)).indexOf("<");
        if (posStop < 0) {
            this.tagFound = false;
            return null;
        }
        return inputStr.substring(0, posStop);
    }

    public String attributeData(String inputStr, String attributeStr) {
        String searchStr = String.valueOf(attributeStr) + "=\"";
        int posStart = inputStr.indexOf(searchStr);
        int posStop = 0;
        if ((posStart += attributeStr.length() + 2) < 0) {
            return null;
        }
        posStop = (inputStr = inputStr.substring(posStart)).indexOf("\"");
        if (posStop < 0) {
            return null;
        }
        return inputStr.substring(0, posStop);
    }

    public String stringBefore(String inputStr, String before, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        return String.valueOf(before) + inputStr.substring(pos);
    }

    public String stringInsert(String inputStr, String before, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        if (pos <= 0) {
            return inputStr;
        }
        return String.valueOf(inputStr.substring(0, pos - 1)) + before + inputStr.substring(pos);
    }

    public String stringFrom(String inputStr, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        return inputStr.substring(pos);
    }

    public String stringIncluding(String inputStr, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        return inputStr.substring(0, pos += tag.length());
    }

    public String stringExcluding(String inputStr, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        return inputStr.substring(0, (pos += tag.length()) - tag.length());
    }

    public String stringTo(String inputStr, String tag) {
        this.tagFound = true;
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            this.tagFound = false;
            return inputStr;
        }
        if (pos <= 0) {
            this.tagFound = false;
            return inputStr;
        }
        return inputStr.substring(0, pos - 1);
    }

    public String removeXMLDecl(String inputStr) {
        int pos = inputStr.indexOf("<?xml");
        if (pos < 0) {
            return inputStr;
        }
        pos = inputStr.indexOf("?>");
        return inputStr.substring(pos += 2);
    }

    public String remove(String inputStr, String start, String stop) {
        StringBuffer out = new StringBuffer();
        int posStart = inputStr.indexOf(start);
        int posStop = 0;
        if (posStart < 0) {
            return inputStr;
        }
        out.append(inputStr.substring(0, posStart));
        String balance = inputStr.substring(posStart + start.length());
        posStart = balance.indexOf(">");
        if (posStart < 0) {
            return inputStr;
        }
        posStop = (balance = balance.substring(posStart)).indexOf(stop);
        if (posStop <= 0) {
            return inputStr;
        }
        out.append(balance.substring(posStop += stop.length()));
        return out.toString();
    }

    public String stringReplace(String inputStr, String tag) {
        int pos = inputStr.indexOf(tag);
        if (pos < 0) {
            return inputStr;
        }
        inputStr = inputStr.substring(pos);
        pos = inputStr.indexOf(">");
        return String.valueOf(tag) + ">" + inputStr.substring(++pos);
    }
}

