package com.interweave.web;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Shared account lockout utility for login servlets.
 *
 * Tracks failed login attempts per email address and enforces a temporary
 * lockout after too many consecutive failures. Thread-safe via ConcurrentHashMap.
 *
 * Used by both ApiLoginServlet and LocalLoginServlet.
 *
 * Defaults: 5 failed attempts → 15-minute lockout.
 * Successful login clears the failure count.
 */
public class LoginRateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION_MS = 15 * 60 * 1000L; // 15 minutes
    private static final long CLEANUP_INTERVAL_MS = 30 * 60 * 1000L; // 30 minutes

    // Key: lowercase email, Value: [failCount, lastFailTimeMs]
    private static final ConcurrentHashMap<String, long[]> failedAttempts = new ConcurrentHashMap<>();
    private static volatile long lastCleanup = System.currentTimeMillis();

    /**
     * Check if the given email is currently locked out.
     * @return true if locked out, false if login attempts are allowed
     */
    public static boolean isLockedOut(String email) {
        if (email == null) return false;
        String key = email.toLowerCase();
        long[] record = failedAttempts.get(key);
        if (record == null) return false;

        if (record[0] >= MAX_ATTEMPTS) {
            long elapsed = System.currentTimeMillis() - record[1];
            if (elapsed < LOCKOUT_DURATION_MS) {
                return true; // Still locked out
            }
            // Lockout expired — clear
            failedAttempts.remove(key);
        }
        return false;
    }

    /**
     * Returns remaining lockout seconds, or 0 if not locked out.
     */
    public static long getRemainingLockoutSeconds(String email) {
        if (email == null) return 0;
        String key = email.toLowerCase();
        long[] record = failedAttempts.get(key);
        if (record == null || record[0] < MAX_ATTEMPTS) return 0;

        long elapsed = System.currentTimeMillis() - record[1];
        long remaining = LOCKOUT_DURATION_MS - elapsed;
        return (remaining > 0) ? (remaining / 1000) : 0;
    }

    /**
     * Record a failed login attempt for the given email.
     */
    public static void recordFailure(String email) {
        if (email == null) return;
        String key = email.toLowerCase();
        long now = System.currentTimeMillis();

        failedAttempts.merge(key, new long[]{1, now},
            (existing, newVal) -> {
                existing[0]++;
                existing[1] = now;
                return existing;
            });

        // Lazy cleanup of expired entries
        if (now - lastCleanup > CLEANUP_INTERVAL_MS) {
            lastCleanup = now;
            long cutoff = now - LOCKOUT_DURATION_MS;
            failedAttempts.entrySet().removeIf(e -> e.getValue()[1] < cutoff);
        }
    }

    /**
     * Clear failed attempts after a successful login.
     */
    public static void clearFailures(String email) {
        if (email == null) return;
        failedAttempts.remove(email.toLowerCase());
    }

    /**
     * Returns the number of failed attempts remaining before lockout.
     */
    public static int getAttemptsRemaining(String email) {
        if (email == null) return MAX_ATTEMPTS;
        String key = email.toLowerCase();
        long[] record = failedAttempts.get(key);
        if (record == null) return MAX_ATTEMPTS;
        int remaining = MAX_ATTEMPTS - (int) record[0];
        return Math.max(0, remaining);
    }
}
