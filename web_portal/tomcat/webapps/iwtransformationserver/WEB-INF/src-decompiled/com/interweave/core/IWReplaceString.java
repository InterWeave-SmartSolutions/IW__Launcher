/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

public class IWReplaceString {
    public static String replace(String str, String pattern, String replace) {
        int slen = str.length();
        int plen = pattern.length();
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer(slen);
        char[] chars = new char[slen];
        while ((e = str.indexOf(pattern, s)) >= 0) {
            str.getChars(s, e, chars, 0);
            result.append(chars, 0, e - s).append(replace);
            s = e + plen;
        }
        str.getChars(s, slen, chars, 0);
        result.append(chars, 0, slen - s);
        return result.toString();
    }
}

