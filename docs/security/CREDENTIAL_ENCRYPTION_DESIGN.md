# Credential Encryption at Rest — Architecture Blueprint

**Date**: 2026-03-13
**Designed by**: Claude Opus 4.6 (code-architect agent)
**Status**: DESIGN — ready for implementation

---

## Credential Surface Analysis

**Storage locations (all currently plaintext):**

1. `workspace/{Project}/xslt/include/dataconnections.xslt` — XSLT `<xsl:param>` values for `iwurl`, `iwuser`, `password`, `msurl`, `msuser`, `mspassword`
2. `workspace/GeneratedProfiles/{profile}/xslt/include/dataconnections.xslt` — compiler output, same structure
3. `workspace/GeneratedProfiles/{profile}/configuration/profile/company_configuration.xml` — full wizard XML including credential fields
4. `workspace/{Project}/configuration/runtime_profiles/*.xml` — sync bridge output, same wizard XML
5. Supabase `company_credentials` — columns `password`, `api_key`, `api_secret` (plaintext)
6. Supabase `company_configurations` — `configuration_xml TEXT` contains wizard XML with credential fields
7. Supabase `profiles` — columns `sf_password`, `qb_password`, `crm_password` (plaintext)

**Data flow:**
```
React UI wizard → PUT /api/config/credentials → company_credentials (DB)
                → PUT /api/config/wizard → company_configurations (DB, XML blob)
                  → WorkspaceProfileSyncServlet.exportProfile()
                    → writes runtime_profiles/*.xml
                      → WorkspaceProfileCompiler.compileProfile()
                        → buildDataConnectionsXslt() → dataconnections.xslt (filesystem)
```

The engine reads `dataconnections.xslt` at execution time, expecting plaintext. That's the terminal consumer.

---

## Architecture Decision

**Chosen approach: Envelope encryption with AES-256-GCM, key stored in `.env`, transparent decrypt at the compiler boundary.**

**Rationale:**
- AES-256-GCM: authenticated encryption (integrity + confidentiality), `javax.crypto` since Java 7, no external deps
- Compiler (`WorkspaceProfileCompiler.buildDataConnectionsXslt()`) is the single choke point — decrypt here, plaintext never reaches disk in final output
- DB stores ciphertext for sensitive columns, non-credential fields unencrypted
- `.env` already exists, is gitignored, established pattern for machine-specific secrets
- Backwards-compatible: `ENC:` prefix sentinel on encrypted values; values without prefix = plaintext (legacy path)

**Threat model:** Protects against DB dumps, accidental git commits of workspace files, Supabase credential leaks. Does NOT protect against full filesystem access (attacker would have `.env` too).

---

## Component Design

### Component 1: CredentialEncryptionService

**File**: `WEB-INF/src/com/interweave/businessDaemon/config/CredentialEncryptionService.java`

**Core interface:**
```java
public class CredentialEncryptionService {
    static String encrypt(String plaintext)     // → "ENC:<base64(iv + ciphertext + tag)>"
    static String decrypt(String value)         // if ENC: prefix → decrypt; else → return as-is
    static boolean isEncrypted(String value)    // starts with "ENC:"
    static void initialize(String envFilePath)  // load key from .env on startup
}
```

**Crypto spec:**
- Algorithm: `AES/GCM/NoPadding` (128-bit tag, 12-byte IV)
- Key: 256-bit, from `CREDENTIAL_ENCRYPTION_KEY` in `.env`
- Key format: 64 hex chars (32 bytes) or Base64
- IV: random 12 bytes per encryption (prepended to ciphertext)
- Output: `"ENC:" + Base64(12-byte-IV + ciphertext + 16-byte-GCM-tag)`

### Component 2: Key Management

**Key location**: `.env` file (already gitignored)

```bash
# Add to .env.example (placeholder, not real key):
CREDENTIAL_ENCRYPTION_KEY=generate-with-openssl-rand-hex-32

# Generate real key:
# openssl rand -hex 32
```

**First-run behavior**: If `CREDENTIAL_ENCRYPTION_KEY` is absent/empty, service operates in passthrough mode (no encryption, no decryption). This preserves backwards compatibility for existing installs that haven't generated a key yet.

### Component 3: Integration Points

**Files to modify:**

1. **`ApiConfigurationServlet.java`** — `handlePutCredentials()` and `handlePutWizard()`
   - Encrypt sensitive field values before DB INSERT
   - Decrypt on read in `handleGetCredentials()` and `handleGetWizard()`
   - Field list: `password`, `api_key`, `api_secret`, `SFPswd`, `QBPswd0`, `CRMPassword`

2. **`WorkspaceProfileCompiler.java`** — `buildDataConnectionsXslt()`
   - Decrypt credential values before writing to `dataconnections.xslt`
   - This is the ONLY point where engine needs plaintext

3. **`WorkspaceProfileSyncServlet.java`** — `exportProfile()`
   - Read from DB (ciphertext), write to filesystem (keep encrypted in runtime_profiles)
   - Compiler handles decryption at build time

4. **`ApiWorkspaceManagementServlet.java`** — project detail endpoint
   - When returning `dataconnections.xslt` content, mask credential values
   - Show `[ENCRYPTED]` placeholder instead of plaintext/ciphertext

### Component 4: Migration

**Migration servlet**: `POST /api/admin/encrypt-credentials`
- Admin-only endpoint
- Reads all `company_credentials` and `company_configurations` rows
- For each credential field: if not `ENC:` prefixed, encrypt and update
- Idempotent (skips already-encrypted values)
- Returns count of migrated records

**Migration is optional and progressive:**
- New credentials saved via wizard are auto-encrypted on write
- Old plaintext credentials remain functional (decrypt() returns as-is)
- Admin can trigger bulk migration at their convenience
- Zero downtime — no schema changes needed

---

## File Summary

| File | Action | Description |
|------|--------|-------------|
| `CredentialEncryptionService.java` | CREATE | Core crypto utility (AES-256-GCM, key from .env) |
| `.env.example` | MODIFY | Add `CREDENTIAL_ENCRYPTION_KEY` placeholder |
| `ApiConfigurationServlet.java` | MODIFY | Encrypt on write, decrypt on read |
| `WorkspaceProfileCompiler.java` | MODIFY | Decrypt at dataconnections.xslt build |
| `WorkspaceProfileSyncServlet.java` | MODIFY | Preserve ciphertext in filesystem exports |
| `ApiWorkspaceManagementServlet.java` | MODIFY | Mask credentials in API responses |
| `web.xml` | MODIFY (optional) | Add migration servlet if standalone |

---

## Implementation Order

1. Create `CredentialEncryptionService` with unit tests
2. Add `CREDENTIAL_ENCRYPTION_KEY` to `.env.example`
3. Modify `ApiConfigurationServlet` (encrypt on write, decrypt on read)
4. Modify `WorkspaceProfileCompiler` (decrypt at build boundary)
5. Modify `ApiWorkspaceManagementServlet` (mask in API responses)
6. Test end-to-end: wizard save → DB shows ENC: → compiler produces plaintext XSLT
7. Build migration endpoint
8. Test progressive migration (mix of plaintext + encrypted values)
