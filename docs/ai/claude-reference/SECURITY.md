# Security Reference

## General Notes

- `.env` contains production database credentials (excluded from git)
- Never commit `.env` file
- Supabase Postgres credentials are shared across all team members
- Admin password `%iwps%` is hardcoded in authentication system

## Content Security Policy — STRICT (iw-business-daemon)

- `SecurityHeadersFilter` (in `web.xml`, mapped to `/*`) sets strict CSP on all responses
- `script-src 'self' https://cdn.jsdelivr.net` — **no `'unsafe-inline'`** — all inline scripts extracted to external `.js` files
- `style-src 'self' 'unsafe-inline'` — still needed for legacy JSP inline `<style>` blocks
- CDN allowlist: `cdn.jsdelivr.net` for Chart.js on `monitoring/Dashboard.jsp`

**Pattern for new JSPs**: Do NOT use inline `<script>` or `onclick`/`onload` attributes. Instead:
- Pass server data via `data-*` attributes on a hidden `<div>`
- Read data in an external `.js` file: `el.getAttribute('data-...')`
- Use `addEventListener` / event delegation instead of inline handlers

- Source: `WEB-INF/src/com/interweave/web/SecurityHeadersFilter.java`
- Compile: `javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/web/SecurityHeadersFilter.java`

## Credential Encryption at Rest (AES-256-GCM)

**Status**: ACTIVE — `CredentialEncryptionService` encrypts credential fields in `company_credentials` and `company_configurations` tables.

**How it works**:
- `CREDENTIAL_ENCRYPTION_KEY` in `.env` (64 hex chars = 32 bytes = AES-256)
- Sentinel prefix `ENC:` on encrypted values — values without prefix are plaintext (backwards-compatible)
- `CredentialEncryptionService.encrypt()` / `.decrypt()` / `.isEncrypted()` / `.encryptIfNeeded()`
- **Write path**: `ApiConfigurationServlet.handlePutCredentials()` encrypts `password`, `api_key`, `api_secret` before DB insert. `handlePutWizard()` encrypts credential fields per-field inside the XML blob.
- **Read path**: `parseXmlToJsonFields()` decrypts credential fields before returning to React UI. `handleGetCredentials()` never exposes raw passwords (returns `hasApiKey: boolean`).
- **Compiler boundary**: `WorkspaceProfileCompiler.buildDataConnectionsXslt()` decrypts passwords before writing plaintext XSLT (engine requires plaintext).
- **Passthrough mode**: If no key in `.env`, all operations are no-ops (backwards compatible).

**Generate a key**: `openssl rand -hex 32`

**Design doc**: `docs/security/CREDENTIAL_ENCRYPTION_DESIGN.md`
**CVE audit**: `docs/security/CVE_AUDIT_2026_03_13.md`
