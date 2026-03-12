package com.interweave.businessDaemon.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for comparing flat wizard XML configurations.
 * <p>
 * Supports two shapes:
 * <ul>
 *   <li>Plain flat fields: {@code <Root><FieldA>value</FieldA></Root>}</li>
 *   <li>HTML-escaped nested XML: a field whose text value starts with
 *       {@code &lt;} is unescaped, parsed recursively, and its inner
 *       fields are prefixed with {@code ParentTag.}</li>
 * </ul>
 * <p>
 * All parsing is string/regex-based (no javax.xml) because the wizard
 * XML is a predictable flat structure.
 * <p>
 * Java 8 compatible (source/target 1.8).
 */
public final class XmlConfigDiffer {

    private XmlConfigDiffer() { }

    // ------------------------------------------------------------------ //
    //  Tag-content regex — matches <Tag>content</Tag> (non-greedy)
    // ------------------------------------------------------------------ //
    private static final Pattern TAG_PATTERN =
            Pattern.compile("<([A-Za-z][A-Za-z0-9_]*)>(.*?)</\\1>", Pattern.DOTALL);

    // ------------------------------------------------------------------ //
    //  parseConfigFields
    // ------------------------------------------------------------------ //

    /**
     * Parses flat wizard XML into a map of field-name to value.
     * <p>
     * The outermost root element (e.g. {@code <SF2QBConfiguration>}) is
     * stripped; only its direct children are returned.
     * <p>
     * If a child's text value begins with {@code &lt;} (HTML-escaped XML),
     * the value is unescaped, parsed recursively, and the resulting keys
     * are prefixed with {@code ParentTag.} (e.g.
     * {@code CRM2MG2Configuration.SyncTypeAC}).
     *
     * @param xml the full XML string (including the root element)
     * @return ordered map of field paths to their text values
     */
    public static Map<String, String> parseConfigFields(String xml) {
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        if (xml == null || xml.trim().isEmpty()) {
            return result;
        }

        // Strip the outermost root element to get the inner content.
        String inner = stripRootElement(xml.trim());

        Matcher m = TAG_PATTERN.matcher(inner);
        while (m.find()) {
            String tag = m.group(1);
            String value = m.group(2);

            // Check for HTML-escaped nested XML
            String trimmedValue = value.trim();
            if (trimmedValue.startsWith("&lt;")) {
                String unescaped = htmlUnescape(trimmedValue);
                Map<String, String> nested = parseConfigFields(unescaped);
                for (Map.Entry<String, String> entry : nested.entrySet()) {
                    result.put(tag + "." + entry.getKey(), entry.getValue());
                }
            } else {
                result.put(tag, value);
            }
        }
        return result;
    }

    // ------------------------------------------------------------------ //
    //  diff
    // ------------------------------------------------------------------ //

    /**
     * Computes the difference between a base map and an incoming map.
     *
     * @param base     the "before" field map (may be null or empty)
     * @param incoming the "after" field map (may be null or empty)
     * @return a {@link DiffResult} describing added, modified, removed,
     *         and unchanged fields
     */
    public static DiffResult diff(Map<String, String> base, Map<String, String> incoming) {
        Map<String, String> safeBase = (base != null) ? base : new HashMap<String, String>();
        Map<String, String> safeIncoming = (incoming != null) ? incoming : new HashMap<String, String>();

        Map<String, String> added = new LinkedHashMap<String, String>();
        Map<String, String[]> modified = new LinkedHashMap<String, String[]>();
        Set<String> removed = new HashSet<String>();
        Set<String> unchanged = new HashSet<String>();

        // Walk incoming keys
        for (Map.Entry<String, String> entry : safeIncoming.entrySet()) {
            String key = entry.getKey();
            String newVal = entry.getValue();
            if (!safeBase.containsKey(key)) {
                added.put(key, newVal);
            } else {
                String oldVal = safeBase.get(key);
                if (valuesEqual(oldVal, newVal)) {
                    unchanged.add(key);
                } else {
                    modified.put(key, new String[]{ oldVal, newVal });
                }
            }
        }

        // Keys in base but not in incoming → removed
        for (String key : safeBase.keySet()) {
            if (!safeIncoming.containsKey(key)) {
                removed.add(key);
            }
        }

        return new DiffResult(added, modified, removed, unchanged);
    }

    // ------------------------------------------------------------------ //
    //  toJson
    // ------------------------------------------------------------------ //

    /**
     * Serialises a {@link DiffResult} to a JSON string.
     * Hand-rolled to avoid any external dependency.
     *
     * @param diff the diff result to serialise
     * @return a JSON string
     */
    public static String toJson(DiffResult diff) {
        if (diff == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        // added
        sb.append("\"added\":{");
        appendStringMap(sb, diff.added);
        sb.append("},");

        // modified
        sb.append("\"modified\":{");
        boolean first = true;
        for (Map.Entry<String, String[]> entry : diff.modified.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(jsonEscape(entry.getKey())).append("\":");
            sb.append("[\"").append(jsonEscape(entry.getValue()[0])).append("\",\"")
              .append(jsonEscape(entry.getValue()[1])).append("\"]");
        }
        sb.append("},");

        // removed
        sb.append("\"removed\":[");
        boolean firstRemoved = true;
        for (String key : diff.removed) {
            if (!firstRemoved) {
                sb.append(",");
            }
            firstRemoved = false;
            sb.append("\"").append(jsonEscape(key)).append("\"");
        }
        sb.append("],");

        // unchanged
        sb.append("\"unchanged\":[");
        boolean firstUnchanged = true;
        for (String key : diff.unchanged) {
            if (!firstUnchanged) {
                sb.append(",");
            }
            firstUnchanged = false;
            sb.append("\"").append(jsonEscape(key)).append("\"");
        }
        sb.append("]");

        sb.append("}");
        return sb.toString();
    }

    // ------------------------------------------------------------------ //
    //  DiffResult inner class
    // ------------------------------------------------------------------ //

    /**
     * Holds the result of comparing two configuration field maps.
     */
    public static class DiffResult {
        /** Fields present in incoming but not in base. */
        public final Map<String, String> added;
        /** Fields present in both but with different values: key → [oldValue, newValue]. */
        public final Map<String, String[]> modified;
        /** Field keys present in base but absent from incoming. */
        public final Set<String> removed;
        /** Field keys present in both with identical values. */
        public final Set<String> unchanged;

        public DiffResult(Map<String, String> added,
                          Map<String, String[]> modified,
                          Set<String> removed,
                          Set<String> unchanged) {
            this.added = added;
            this.modified = modified;
            this.removed = removed;
            this.unchanged = unchanged;
        }
    }

    // ------------------------------------------------------------------ //
    //  Private helpers
    // ------------------------------------------------------------------ //

    /**
     * Strips the outermost XML element, returning only its inner content.
     * E.g. {@code <Root>inner</Root>} → {@code inner}.
     */
    private static String stripRootElement(String xml) {
        // Find the first '>' that closes the opening tag
        int openEnd = xml.indexOf('>');
        if (openEnd < 0) {
            return xml;
        }
        // Find the last '</' which starts the closing root tag
        int closeStart = xml.lastIndexOf("</");
        if (closeStart < 0 || closeStart <= openEnd) {
            return xml;
        }
        return xml.substring(openEnd + 1, closeStart);
    }

    /**
     * Unescapes the five standard HTML/XML character entities.
     */
    private static String htmlUnescape(String text) {
        if (text == null) {
            return "";
        }
        String result = text;
        result = result.replace("&amp;", "&");
        result = result.replace("&lt;", "<");
        result = result.replace("&gt;", ">");
        result = result.replace("&quot;", "\"");
        result = result.replace("&apos;", "'");
        return result;
    }

    /**
     * Null-safe value comparison.
     */
    private static boolean valuesEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * Appends a {@code Map<String,String>} as JSON object entries.
     */
    private static void appendStringMap(StringBuilder sb, Map<String, String> map) {
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(jsonEscape(entry.getKey())).append("\":\"")
              .append(jsonEscape(entry.getValue())).append("\"");
        }
    }

    /**
     * Escapes a string for inclusion in a JSON string literal.
     */
    private static String jsonEscape(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }
}
