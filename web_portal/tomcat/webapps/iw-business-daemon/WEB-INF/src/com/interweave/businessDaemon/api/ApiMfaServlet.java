package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * ApiMfaServlet - JSON API endpoint for TOTP-based multi-factor authentication.
 *
 * Endpoints (dispatched on pathInfo):
 *   GET  /status   - Check current MFA status for authenticated user
 *   POST /setup    - Generate TOTP secret and otpauth URI
 *   POST /verify   - Verify TOTP code and enable MFA (returns backup codes)
 *   POST /validate - Validate TOTP or backup code during login flow
 *   DELETE /disable - Disable MFA (requires password confirmation)
 *
 * API Base: /api/auth/mfa/*
 *
 * @author IW Portal API
 * @version 1.0
 */
public class ApiMfaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DataSource dataSource;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // TOTP parameters
    private static final int TOTP_DIGITS = 6;
    private static final int TOTP_PERIOD = 30;
    private static final int TOTP_SKEW = 1; // allow +/- 1 time step

    // Backup codes
    private static final int BACKUP_CODE_COUNT = 8;
    private static final int BACKUP_CODE_BYTES = 4; // 8 hex chars each

    // Base32 alphabet (RFC 4648)
    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/IWDB");
            log("ApiMfaServlet initialized - using JNDI DataSource jdbc/IWDB");
        } catch (NamingException e) {
            log("Failed to initialize DataSource", e);
            throw new ServletException("Cannot initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();
        if ("/status".equals(path)) {
            handleStatus(request, response);
        } else {
            sendJson(response, HttpServletResponse.SC_NOT_FOUND,
                "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();
        if (path == null) {
            sendJson(response, HttpServletResponse.SC_NOT_FOUND,
                "{\"success\":false,\"error\":\"Unknown endpoint\"}");
            return;
        }

        switch (path) {
            case "/setup":
                handleSetup(request, response);
                break;
            case "/verify":
                handleVerify(request, response);
                break;
            case "/validate":
                handleValidate(request, response);
                break;
            default:
                sendJson(response, HttpServletResponse.SC_NOT_FOUND,
                    "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();
        if ("/disable".equals(path)) {
            handleDisable(request, response);
        } else {
            sendJson(response, HttpServletResponse.SC_NOT_FOUND,
                "{\"success\":false,\"error\":\"Unknown endpoint\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ─── Endpoint Handlers ───────────────────────────────────────────────

    /**
     * GET /status - Return MFA status for the authenticated user.
     */
    private void handleStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer userId = getAuthenticatedUserId(request);
        if (userId == null) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT mfa_enabled, verified_at FROM user_mfa_settings WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        boolean enabled = rs.getBoolean("mfa_enabled");
                        Timestamp verifiedAt = rs.getTimestamp("verified_at");
                        String verifiedStr = verifiedAt != null ? verifiedAt.toString() : "null";
                        sendJson(response, HttpServletResponse.SC_OK,
                            "{\"success\":true,\"mfaEnabled\":" + enabled +
                            ",\"verifiedAt\":" + (verifiedAt != null ? "\"" + escapeJson(verifiedStr) + "\"" : "null") + "}");
                    } else {
                        sendJson(response, HttpServletResponse.SC_OK,
                            "{\"success\":true,\"mfaEnabled\":false,\"verifiedAt\":null}");
                    }
                }
            }
        } catch (SQLException e) {
            log("Database error checking MFA status: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    /**
     * POST /setup - Generate a new TOTP secret and return otpauth URI.
     */
    private void handleSetup(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer userId = getAuthenticatedUserId(request);
        if (userId == null) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String userEmail = getSessionEmail(request);
        if (userEmail == null) userEmail = "user";

        // Generate 20-byte random secret
        byte[] secretBytes = new byte[20];
        SECURE_RANDOM.nextBytes(secretBytes);
        String secret = base32Encode(secretBytes);

        try (Connection conn = dataSource.getConnection()) {
            // Upsert into user_mfa_settings (unverified)
            String sql = "INSERT INTO user_mfa_settings (user_id, totp_secret, mfa_enabled, updated_at) " +
                "VALUES (?, ?, FALSE, NOW()) " +
                "ON CONFLICT (user_id) DO UPDATE SET totp_secret = ?, mfa_enabled = FALSE, " +
                "backup_codes = NULL, verified_at = NULL, updated_at = NOW()";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, secret);
                stmt.setString(3, secret);
                stmt.executeUpdate();
            }

            String otpauthUri = "otpauth://totp/InterWeave:" + urlEncode(userEmail) +
                "?secret=" + secret + "&issuer=InterWeave";

            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"secret\":\"").append(escapeJson(secret)).append("\",");
            json.append("\"otpauthUri\":\"").append(escapeJson(otpauthUri)).append("\"}");

            sendJson(response, HttpServletResponse.SC_OK, json.toString());

        } catch (SQLException e) {
            log("Database error during MFA setup: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    /**
     * POST /verify - Verify a TOTP code and enable MFA. Returns backup codes.
     */
    private void handleVerify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer userId = getAuthenticatedUserId(request);
        if (userId == null) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String body = readRequestBody(request);
        String code = extractJsonString(body, "code");

        if (code == null || code.trim().isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Verification code is required\"}");
            return;
        }
        code = code.trim();
        if (code.length() != 6 || !code.matches("\\d{6}")) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Code must be exactly 6 digits\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Get the stored (unverified) secret
            String sql = "SELECT totp_secret FROM user_mfa_settings WHERE user_id = ? AND mfa_enabled = FALSE";
            String secret = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        secret = rs.getString("totp_secret");
                    }
                }
            }

            if (secret == null || secret.isEmpty()) {
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"MFA setup not initiated. Call /setup first.\"}");
                return;
            }

            // Verify TOTP code
            if (!verifyTotp(secret, code)) {
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"Invalid verification code\"}");
                return;
            }

            // Generate backup codes
            String[] plaintextBackupCodes = new String[BACKUP_CODE_COUNT];
            StringBuilder hashedCodesJson = new StringBuilder("[");
            for (int i = 0; i < BACKUP_CODE_COUNT; i++) {
                byte[] codeBytes = new byte[BACKUP_CODE_BYTES];
                SECURE_RANDOM.nextBytes(codeBytes);
                String backupCode = bytesToHex(codeBytes);
                plaintextBackupCodes[i] = backupCode;
                if (i > 0) hashedCodesJson.append(",");
                hashedCodesJson.append("\"").append(hashSha256(backupCode)).append("\"");
            }
            hashedCodesJson.append("]");

            // Enable MFA and store hashed backup codes
            String updateSql = "UPDATE user_mfa_settings SET mfa_enabled = TRUE, " +
                "backup_codes = ?, verified_at = NOW(), updated_at = NOW() WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, hashedCodesJson.toString());
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            // Return plaintext backup codes (shown once only)
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"backupCodes\":[");
            for (int i = 0; i < plaintextBackupCodes.length; i++) {
                if (i > 0) json.append(",");
                json.append("\"").append(plaintextBackupCodes[i]).append("\"");
            }
            json.append("]}");

            log("MFA enabled for user_id=" + userId);
            sendJson(response, HttpServletResponse.SC_OK, json.toString());

        } catch (Exception e) {
            log("Error during MFA verify: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    /**
     * POST /validate - Validate a TOTP or backup code during login flow.
     */
    private void handleValidate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // During login flow, session may have a pending MFA userId
        HttpSession session = request.getSession(false);
        Integer userId = null;

        // Check for pending MFA user (set during login when MFA is required)
        if (session != null) {
            Object pendingMfaUser = session.getAttribute("pendingMfaUserId");
            if (pendingMfaUser instanceof Integer) {
                userId = (Integer) pendingMfaUser;
            }
        }

        // Fall back to authenticated user
        if (userId == null) {
            userId = getAuthenticatedUserId(request);
        }

        if (userId == null) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String body = readRequestBody(request);
        String code = extractJsonString(body, "code");
        String backupCode = extractJsonString(body, "backupCode");

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT totp_secret, backup_codes FROM user_mfa_settings " +
                "WHERE user_id = ? AND mfa_enabled = TRUE";
            String secret = null;
            String storedBackupCodes = null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        secret = rs.getString("totp_secret");
                        storedBackupCodes = rs.getString("backup_codes");
                    }
                }
            }

            if (secret == null) {
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"MFA is not enabled for this account\"}");
                return;
            }

            boolean valid = false;

            // Try TOTP code first
            if (code != null && !code.trim().isEmpty()) {
                code = code.trim();
                if (code.length() == 6 && code.matches("\\d{6}")) {
                    valid = verifyTotp(secret, code);
                }
            }

            // Try backup code if TOTP didn't work
            if (!valid && backupCode != null && !backupCode.trim().isEmpty()) {
                String hashedInput = hashSha256(backupCode.trim());
                if (storedBackupCodes != null && storedBackupCodes.contains("\"" + hashedInput + "\"")) {
                    valid = true;
                    // Remove the used backup code
                    String updatedCodes = storedBackupCodes.replace("\"" + hashedInput + "\"", "\"USED\"");
                    String updateSql = "UPDATE user_mfa_settings SET backup_codes = ?, updated_at = NOW() WHERE user_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                        stmt.setString(1, updatedCodes);
                        stmt.setInt(2, userId);
                        stmt.executeUpdate();
                    }
                    log("Backup code consumed for user_id=" + userId);
                }
            }

            if (!valid) {
                sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"success\":false,\"error\":\"Invalid code\"}");
                return;
            }

            // MFA passed — if this was a pending MFA login, complete it
            if (session != null && session.getAttribute("pendingMfaUserId") != null) {
                session.removeAttribute("pendingMfaUserId");
                session.setAttribute("authenticated", true);
                session.setAttribute("mfaVerified", true);
            }

            sendJson(response, HttpServletResponse.SC_OK, "{\"success\":true}");

        } catch (Exception e) {
            log("Error during MFA validate: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    /**
     * DELETE /disable - Disable MFA after password confirmation.
     */
    private void handleDisable(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer userId = getAuthenticatedUserId(request);
        if (userId == null) {
            sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                "{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String body = readRequestBody(request);
        String password = extractJsonString(body, "password");

        if (password == null || password.isEmpty()) {
            sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                "{\"success\":false,\"error\":\"Password is required to disable MFA\"}");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Verify password
            String sql = "SELECT password FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"success\":false,\"error\":\"User not found\"}");
                        return;
                    }
                    String storedHash = rs.getString("password");
                    if (!verifyPassword(password, storedHash)) {
                        sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
                            "{\"success\":false,\"error\":\"Incorrect password\"}");
                        return;
                    }
                }
            }

            // Delete MFA settings
            String deleteSql = "DELETE FROM user_mfa_settings WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            log("MFA disabled for user_id=" + userId);
            sendJson(response, HttpServletResponse.SC_OK,
                "{\"success\":true,\"message\":\"MFA has been disabled\"}");

        } catch (Exception e) {
            log("Error disabling MFA: " + e.getMessage(), e);
            sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "{\"success\":false,\"error\":\"A system error occurred\"}");
        }
    }

    // ─── TOTP Implementation (RFC 6238) ──────────────────────────────────

    /**
     * Verify a TOTP code against the secret, checking current time step +/- skew.
     */
    private boolean verifyTotp(String base32Secret, String code) {
        try {
            byte[] secretBytes = base32Decode(base32Secret);
            long currentTime = System.currentTimeMillis() / 1000L;

            for (int i = -TOTP_SKEW; i <= TOTP_SKEW; i++) {
                long timeStep = (currentTime / TOTP_PERIOD) + i;
                String generated = generateTotp(secretBytes, timeStep);
                if (generated.equals(code)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log("TOTP verification error: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Generate a TOTP code for the given secret and time step.
     * Implements RFC 6238 / RFC 4226 with HmacSHA1.
     */
    private String generateTotp(byte[] secret, long timeStep) throws Exception {
        // Step 1: Convert time step to 8-byte big-endian
        byte[] timeBytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            timeBytes[i] = (byte) (timeStep & 0xFF);
            timeStep >>= 8;
        }

        // Step 2: HMAC-SHA1
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secret, "HmacSHA1"));
        byte[] hmac = mac.doFinal(timeBytes);

        // Step 3: Dynamic truncation
        int offset = hmac[hmac.length - 1] & 0x0F;
        int binary = ((hmac[offset] & 0x7F) << 24)
                   | ((hmac[offset + 1] & 0xFF) << 16)
                   | ((hmac[offset + 2] & 0xFF) << 8)
                   | (hmac[offset + 3] & 0xFF);

        // Step 4: Modulo to get N digits
        int otp = binary % (int) Math.pow(10, TOTP_DIGITS);

        // Step 5: Pad with leading zeros
        String otpStr = Integer.toString(otp);
        while (otpStr.length() < TOTP_DIGITS) {
            otpStr = "0" + otpStr;
        }
        return otpStr;
    }

    // ─── Base32 Encode/Decode (RFC 4648) ─────────────────────────────────

    private String base32Encode(byte[] data) {
        StringBuilder result = new StringBuilder();
        int buffer = 0;
        int bitsLeft = 0;

        for (byte b : data) {
            buffer = (buffer << 8) | (b & 0xFF);
            bitsLeft += 8;
            while (bitsLeft >= 5) {
                bitsLeft -= 5;
                result.append(BASE32_CHARS.charAt((buffer >> bitsLeft) & 0x1F));
            }
        }
        if (bitsLeft > 0) {
            buffer <<= (5 - bitsLeft);
            result.append(BASE32_CHARS.charAt(buffer & 0x1F));
        }
        return result.toString();
    }

    private byte[] base32Decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) return new byte[0];
        encoded = encoded.toUpperCase().replaceAll("[=\\s]", "");

        int outputLength = encoded.length() * 5 / 8;
        byte[] result = new byte[outputLength];
        int buffer = 0;
        int bitsLeft = 0;
        int index = 0;

        for (char c : encoded.toCharArray()) {
            int val = BASE32_CHARS.indexOf(c);
            if (val < 0) continue; // skip invalid chars
            buffer = (buffer << 5) | val;
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                bitsLeft -= 8;
                result[index++] = (byte) ((buffer >> bitsLeft) & 0xFF);
            }
        }
        return result;
    }

    // ─── Helper Methods ─────────────────────────────────────────────────

    private Integer getAuthenticatedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object authenticated = session.getAttribute("authenticated");
        if (authenticated == null || !Boolean.TRUE.equals(authenticated)) return null;
        Object userId = session.getAttribute("userId");
        if (userId instanceof Integer) return (Integer) userId;
        if (userId != null) {
            try {
                return Integer.parseInt(userId.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private String getSessionEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object email = session.getAttribute("userEmail");
        return email != null ? email.toString() : null;
    }

    private boolean verifyPassword(String password, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) return false;
        if (password.equals(storedHash)) return true;
        try {
            return hashSha256(password).equals(storedHash);
        } catch (Exception e) {
            log("Error hashing password", e);
            return false;
        }
    }

    private String hashSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }
        return hex.toString();
    }

    /**
     * Minimal URL encoding for email addresses in otpauth URIs.
     */
    private static String urlEncode(String str) {
        if (str == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9') || c == '-' || c == '_' || c == '.' || c == '~') {
                sb.append(c);
            } else if (c == '@') {
                sb.append("%40");
            } else if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append('%');
                String hex = Integer.toHexString(c & 0xFF).toUpperCase();
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private String extractJsonString(String json, String key) {
        if (json == null || key == null) return null;
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex < 0) return null;
        int colonIndex = json.indexOf(':', keyIndex + searchKey.length());
        if (colonIndex < 0) return null;
        int start = json.indexOf('"', colonIndex + 1);
        if (start < 0) return null;
        start++;
        int end = start;
        while (end < json.length()) {
            if (json.charAt(end) == '\\') { end += 2; continue; }
            if (json.charAt(end) == '"') break;
            end++;
        }
        if (end >= json.length()) return null;
        return json.substring(start, end);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendJson(HttpServletResponse response, int statusCode, String json) throws IOException {
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
