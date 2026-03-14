package com.interweave.businessDaemon.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * CredentialEncryptionService — AES-256-GCM envelope encryption for credentials at rest.
 *
 * Encrypted values use an "ENC:" prefix sentinel:
 *   ENC:<base64(12-byte-IV + ciphertext + 16-byte-GCM-tag)>
 *
 * Values without the prefix are treated as plaintext (backwards-compatible).
 *
 * The encryption key is loaded from .env file (CREDENTIAL_ENCRYPTION_KEY=<64 hex chars>).
 * If the key is missing, the service operates in passthrough mode (no encryption/decryption).
 */
public class CredentialEncryptionService {

    private static final String PREFIX = "ENC:";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int IV_LENGTH = 12; // 96 bits, recommended for GCM
    private static final String ENV_KEY_NAME = "CREDENTIAL_ENCRYPTION_KEY";

    private static volatile byte[] encryptionKey = null;
    private static volatile boolean initialized = false;
    private static final SecureRandom RANDOM = new SecureRandom();

    // Base64 encoding/decoding (Java 8 compatible)
    private static final java.util.Base64.Encoder B64_ENC = java.util.Base64.getEncoder();
    private static final java.util.Base64.Decoder B64_DEC = java.util.Base64.getDecoder();

    private CredentialEncryptionService() {} // utility class

    /**
     * Initialize from a .env file path. Call once at startup.
     * Safe to call multiple times (idempotent).
     */
    public static synchronized void initialize(File envFile) {
        if (envFile == null || !envFile.isFile()) {
            System.out.println("[CredentialEncryptionService] .env not found — passthrough mode");
            initialized = true;
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(envFile), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(ENV_KEY_NAME + "=")) {
                    String hexKey = line.substring(ENV_KEY_NAME.length() + 1).trim();
                    // Strip surrounding quotes if present
                    if (hexKey.startsWith("\"") && hexKey.endsWith("\"")) {
                        hexKey = hexKey.substring(1, hexKey.length() - 1);
                    }
                    if (hexKey.length() == 64 && hexKey.matches("[0-9a-fA-F]+")) {
                        encryptionKey = hexToBytes(hexKey);
                        System.out.println("[CredentialEncryptionService] Encryption key loaded (AES-256-GCM active)");
                    } else if (!hexKey.isEmpty() && !hexKey.startsWith("generate")) {
                        System.err.println("[CredentialEncryptionService] WARNING: CREDENTIAL_ENCRYPTION_KEY must be 64 hex chars (32 bytes). Got " + hexKey.length() + " chars. Passthrough mode.");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("[CredentialEncryptionService] Error reading .env: " + e.getMessage());
        }

        if (encryptionKey == null) {
            System.out.println("[CredentialEncryptionService] No encryption key configured — passthrough mode (credentials stored as plaintext)");
        }
        initialized = true;
    }

    /**
     * Initialize from a directory containing .env.
     */
    public static void initialize(String repoRootPath) {
        initialize(new File(repoRootPath, ".env"));
    }

    /**
     * Returns true if the encryption key is loaded and active.
     */
    public static boolean isActive() {
        return encryptionKey != null;
    }

    /**
     * Returns true if the value appears to be encrypted (has ENC: prefix).
     */
    public static boolean isEncrypted(String value) {
        return value != null && value.startsWith(PREFIX);
    }

    /**
     * Encrypt a plaintext credential value.
     * Returns "ENC:<base64>" or the original value if encryption is not active.
     */
    public static String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) return plaintext;
        if (isEncrypted(plaintext)) return plaintext; // already encrypted
        if (encryptionKey == null) return plaintext; // passthrough mode

        try {
            byte[] iv = new byte[IV_LENGTH];
            RANDOM.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptionKey, ALGORITHM), spec);

            byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

            // Combine: IV + ciphertext (includes GCM tag)
            byte[] combined = new byte[IV_LENGTH + ciphertext.length];
            System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
            System.arraycopy(ciphertext, 0, combined, IV_LENGTH, ciphertext.length);

            return PREFIX + B64_ENC.encodeToString(combined);
        } catch (Exception e) {
            System.err.println("[CredentialEncryptionService] Encryption failed: " + e.getMessage());
            return plaintext; // fail open to avoid data loss
        }
    }

    /**
     * Decrypt an encrypted credential value.
     * If the value doesn't have the ENC: prefix, returns it as-is (plaintext passthrough).
     */
    public static String decrypt(String value) {
        if (value == null || value.isEmpty()) return value;
        if (!isEncrypted(value)) return value; // plaintext passthrough
        if (encryptionKey == null) {
            System.err.println("[CredentialEncryptionService] Cannot decrypt: no key loaded. Returning masked value.");
            return "[ENCRYPTED-NO-KEY]";
        }

        try {
            byte[] combined = B64_DEC.decode(value.substring(PREFIX.length()));
            if (combined.length < IV_LENGTH + 1) {
                System.err.println("[CredentialEncryptionService] Invalid ciphertext length");
                return "[DECRYPT-ERROR]";
            }

            byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
            byte[] ciphertext = Arrays.copyOfRange(combined, IV_LENGTH, combined.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, iv);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptionKey, ALGORITHM), spec);

            byte[] plaintext = cipher.doFinal(ciphertext);
            return new String(plaintext, "UTF-8");
        } catch (Exception e) {
            System.err.println("[CredentialEncryptionService] Decryption failed: " + e.getMessage());
            return "[DECRYPT-ERROR]";
        }
    }

    /**
     * Encrypt a value if it's not already encrypted and encryption is active.
     * Returns the value unchanged if already encrypted or key not loaded.
     */
    public static String encryptIfNeeded(String value) {
        if (value == null || value.isEmpty() || isEncrypted(value) || encryptionKey == null) {
            return value;
        }
        return encrypt(value);
    }

    /**
     * For display purposes: returns a masked version of the value.
     * If encrypted, returns "[ENCRYPTED]". If plaintext, masks with asterisks.
     */
    public static String mask(String value) {
        if (value == null || value.isEmpty()) return "";
        if (isEncrypted(value)) return "[ENCRYPTED]";
        if (value.length() <= 4) return "****";
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }

    // ─── Hex utilities ─────────────────────────────────────────

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
