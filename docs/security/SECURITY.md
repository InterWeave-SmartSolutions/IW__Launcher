# Security Policy

## Supported Versions

| Component | Version | Status |
|---|---|---|
| InterWeave IDE | Current | Supported |
| Apache Tomcat | 9.0.83 | Supported |
| Bundled JRE | Java 8u382 | Supported (update recommended) |
| Eclipse Platform | 3.1 | Legacy -- no upstream patches |

## Reporting Vulnerabilities

If you discover a security vulnerability, **do not open a public issue**.

Contact the maintainers privately:
- Email: **security@interweave-smartsolutions.com** *(placeholder -- update with actual contact)*
- GitHub: Use [private vulnerability reporting](https://github.com/InterWeave-SmartSolutions/IW_Launcher/security/advisories/new)

We aim to acknowledge reports within 48 hours and provide a fix or mitigation within 7 business days.

## Known Security Considerations

### 1. `.env` Contains Production Credentials

The `.env` file holds database hostnames, usernames, and passwords in plaintext. It is excluded from git via `.gitignore`.

**Risk**: `.env.example` historically shipped with a real Oracle Cloud password. If you forked before this was redacted, rotate that credential immediately.

**Mitigation**: Never commit `.env`. Verify `.gitignore` includes `.env` before pushing.

### 2. Admin Password is Hardcoded

The default admin account (`__iw_admin__` / `%iwps%`) has a hardcoded password baked into the authentication system. It cannot be changed without modifying compiled servlet bytecode.

**Mitigation**: Restrict network access to the Tomcat port (9090) in production. Do not expose the web portal to the public internet.

### 3. Legacy LoginServlet Bytecode Still Exists

The historical compiled `LoginServlet.class` still exists in the legacy webapp,
but the current supported runtime path is `LocalLoginServlet` /
`ApiLoginServlet`.

Practical security implications:
- The historical `LoginServlet.class` remains unauditable bytecode.
- The supported local login path is now auditable source and uses standard
  SHA-256-based password verification against the active database.
- DB-backed non-admin users can authenticate when their credentials exist in
  the configured database.

**Mitigation**: Continue routing production/local use through the local servlet
bridge. Treat the legacy compiled servlet as historical fallback code, not the
current authentication source of truth.

### 4. Legacy MySQL Compatibility Modes Use Shared Credentials

Legacy MySQL-style modes (`DB_MODE=interweave`, and the historical
`DB_MODE=oracle_cloud` compatibility label) typically use a single shared MySQL
username/password. Any user with `.env` access has broad database access in
those modes.

**Mitigation**: Use `DB_MODE=supabase` as the primary team mode. Limit legacy
MySQL modes to explicit fallback scenarios only.

### 5. `config.xml` Contains Database Password in Plaintext

The file `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/config.xml` stores database connection details including passwords in plaintext XML.

**Mitigation**: This file is excluded from git via `.gitignore` (as `context.xml`). Ensure it is never committed. Use `scripts/SETUP_DB_*.bat|sh` scripts to generate it from `.env`.

### 6. Tomcat Context Resource

`web_portal/tomcat/conf/context.xml` may contain JDBC connection strings with embedded passwords. This file is gitignored.

**Mitigation**: Never commit `context.xml`. Regenerate from `.env` using the setup scripts.

## Best Practices

1. **Use `.env` for all credentials.** Never hardcode passwords in scripts or config files that are committed to git.

2. **Rotate database passwords periodically.** Update `.env` and regenerate `config.xml` / `context.xml` after rotation.

3. **Keep the JRE updated.** The bundled JRE is Java 8u382. Monitor for critical security patches and update `jre/` when needed. Java 8 reached public end-of-life but receives commercial/extended support.

4. **Restrict Tomcat to localhost.** Unless the web portal must be externally accessible, bind Tomcat to `127.0.0.1` in `web_portal/tomcat/conf/server.xml`:
   ```xml
   <Connector port="9090" address="127.0.0.1" ... />
   ```

5. **Audit `.gitignore` before pushing.** Confirm that `.env`, `context.xml`, `config.xml`, and any credential files are excluded.

6. **Do not expose the web portal publicly** without adding TLS (HTTPS), proper authentication, and input validation review.
