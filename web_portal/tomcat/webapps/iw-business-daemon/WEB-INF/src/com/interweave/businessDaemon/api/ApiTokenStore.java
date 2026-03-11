package com.interweave.businessDaemon.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory token store for stateless API authentication.
 *
 * When the React portal is served from a different origin (e.g. Vercel)
 * and proxied to Tomcat, session cookies don't survive the proxy hop.
 * This store maps opaque Bearer tokens to user session attributes so that
 * a servlet filter can reconstruct the HttpSession on each request.
 *
 * Tokens expire after 24 hours and are cleaned up lazily.
 */
public class ApiTokenStore {

    private static final ConcurrentHashMap<String, TokenEntry> tokens = new ConcurrentHashMap<>();
    private static final long TOKEN_TTL_MS = 24L * 60 * 60 * 1000; // 24 hours

    public static class TokenEntry {
        public final Map<String, Object> attributes;
        public final long createdAt;

        public TokenEntry(Map<String, Object> attributes) {
            this.attributes = attributes;
            this.createdAt = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - createdAt > TOKEN_TTL_MS;
        }
    }

    /**
     * Creates a new token storing the given session attributes.
     * Returns the token string (a UUID).
     */
    public static String createToken(Map<String, Object> sessionAttributes) {
        cleanup();
        String token = UUID.randomUUID().toString();
        tokens.put(token, new TokenEntry(new HashMap<>(sessionAttributes)));
        return token;
    }

    /**
     * Looks up a token. Returns null if not found or expired.
     */
    public static TokenEntry getToken(String token) {
        if (token == null) return null;
        TokenEntry entry = tokens.get(token);
        if (entry == null) return null;
        if (entry.isExpired()) {
            tokens.remove(token);
            return null;
        }
        return entry;
    }

    /**
     * Removes a token (used on logout).
     */
    public static void removeToken(String token) {
        if (token != null) tokens.remove(token);
    }

    /** Lazily remove expired entries. */
    private static void cleanup() {
        Iterator<Map.Entry<String, TokenEntry>> it = tokens.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue().isExpired()) {
                it.remove();
            }
        }
    }
}
