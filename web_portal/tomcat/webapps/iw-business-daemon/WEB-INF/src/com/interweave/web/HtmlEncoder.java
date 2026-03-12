package com.interweave.web;

/**
 * Minimal HTML encoding utility for JSP output.
 *
 * Encodes the five XML special characters that can cause XSS when
 * embedded in HTML text or attribute contexts:
 *   &  →  &amp;amp;
 *   <  →  &amp;lt;
 *   >  →  &amp;gt;
 *   "  →  &amp;quot;
 *   '  →  &amp;#39;
 *
 * Usage in JSP:
 *   <%@ page import="com.interweave.web.HtmlEncoder" %>
 *   <input value="<%= HtmlEncoder.encode(someVariable) %>"/>
 *
 * This class has ZERO dependencies beyond the JDK. It is designed to be
 * drop-in compatible with legacy JSPs that don't use JSTL.
 */
public final class HtmlEncoder {

    private HtmlEncoder() {} // utility class

    /**
     * Encodes a string for safe inclusion in HTML text or attribute values.
     * Returns empty string for null input (safe default for JSP expressions).
     */
    public static String encode(String input) {
        if (input == null) return "";
        int len = input.length();
        if (len == 0) return "";

        StringBuilder sb = null; // lazy init — avoid allocation for clean strings
        for (int i = 0; i < len; i++) {
            char c = input.charAt(i);
            String replacement;
            switch (c) {
                case '&':  replacement = "&amp;";  break;
                case '<':  replacement = "&lt;";   break;
                case '>':  replacement = "&gt;";   break;
                case '"':  replacement = "&quot;"; break;
                case '\'': replacement = "&#39;";  break;
                default:   replacement = null;
            }
            if (replacement != null) {
                if (sb == null) {
                    sb = new StringBuilder(len + 16);
                    sb.append(input, 0, i);
                }
                sb.append(replacement);
            } else if (sb != null) {
                sb.append(c);
            }
        }
        return (sb != null) ? sb.toString() : input;
    }

    /**
     * Encodes an Object's toString() for HTML. Convenience for JSP expressions
     * where the type may not be String (e.g., Integer session attributes).
     */
    public static String encode(Object input) {
        return (input == null) ? "" : encode(input.toString());
    }
}
