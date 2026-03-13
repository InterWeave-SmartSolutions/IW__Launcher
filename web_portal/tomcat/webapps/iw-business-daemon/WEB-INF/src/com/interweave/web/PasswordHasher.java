package com.interweave.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Centralized password hashing utility with progressive bcrypt migration.
 *
 * Supports three hash formats:
 * 1. bcrypt ($2a$ prefix) — target format, used for all new passwords
 * 2. SHA-256 hex (64 chars) — legacy format, auto-upgraded on login
 * 3. Plain text — development/testing only, auto-upgraded on login
 *
 * On successful verification of a non-bcrypt hash, call {@link #rehashIfNeeded}
 * to transparently upgrade the stored hash to bcrypt.
 *
 * Cost factor: 12 (2^12 = 4096 iterations, ~250ms on modern hardware).
 */
public final class PasswordHasher {

    private static final Logger LOG = Logger.getLogger(PasswordHasher.class.getName());
    private static final int BCRYPT_COST = 12;

    private PasswordHasher() {}

    /**
     * Hash a password using bcrypt. Use for new registrations and password changes.
     */
    public static String hash(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt(BCRYPT_COST));
    }

    /**
     * Verify a plaintext password against a stored hash.
     * Supports bcrypt, SHA-256 hex, and plain text (testing).
     *
     * @return true if the password matches
     */
    public static boolean verify(String plaintext, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        // bcrypt hashes start with $2a$, $2b$, or $2y$
        if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
            try {
                return BCrypt.checkpw(plaintext, storedHash);
            } catch (IllegalArgumentException e) {
                LOG.log(Level.WARNING, "Invalid bcrypt hash format", e);
                return false;
            }
        }

        // SHA-256 hex (64 lowercase hex chars)
        if (storedHash.length() == 64 && storedHash.matches("[0-9a-f]+")) {
            return sha256Hex(plaintext).equals(storedHash);
        }

        // Plain text fallback (development/testing only)
        return plaintext.equals(storedHash);
    }

    /**
     * Check if a stored hash needs upgrading to bcrypt.
     *
     * @return true if the hash is NOT bcrypt (SHA-256 or plain text)
     */
    public static boolean needsRehash(String storedHash) {
        if (storedHash == null) return false;
        return !storedHash.startsWith("$2a$") && !storedHash.startsWith("$2b$") && !storedHash.startsWith("$2y$");
    }

    /**
     * Re-hash a password to bcrypt and update the database.
     * Call this after successful login when {@link #needsRehash} returns true.
     *
     * @param conn      active database connection
     * @param tableName "users" or "companies"
     * @param idColumn  "id" (for users) or "id" (for companies)
     * @param recordId  the primary key value
     * @param plaintext the verified plaintext password
     */
    public static void rehashIfNeeded(Connection conn, String tableName,
                                       String idColumn, int recordId,
                                       String plaintext) {
        String bcryptHash = hash(plaintext);
        String sql = "UPDATE " + tableName + " SET password = ? WHERE " + idColumn + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bcryptHash);
            stmt.setInt(2, recordId);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                LOG.info("Upgraded password hash to bcrypt for " + tableName + " id=" + recordId);
            }
        } catch (SQLException e) {
            // Non-fatal: login still succeeds, hash upgrade will be retried next login
            LOG.log(Level.WARNING, "Failed to upgrade password hash for " + tableName + " id=" + recordId, e);
        }
    }

    /**
     * SHA-256 hex hash (legacy format, matches MySQL SHA2(password, 256)).
     */
    static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hex = new StringBuilder(64);
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
