# Threat Mitigation Mapping — InterWeave IDE Platform

**Generated**: 2026-03-12
**Methodology**: STRIDE threat modeling mapped to existing/recommended controls
**Scope**: Full stack — Tomcat 9.0.83 + JSP/Servlets + React portal + Supabase Postgres

---

## Executive Summary

| Metric | Value |
|--------|-------|
| **Total Threats Identified** | 24 |
| **Controls Implemented** | 14 |
| **Controls Partial** | 5 |
| **Controls Missing** | 12 |
| **Critical Gaps** | 5 |
| **Overall Coverage** | ~45% |

**Top 5 Critical Gaps** (immediate action recommended):
1. **No TLS/HTTPS** — all credentials transit in plaintext
2. **Pervasive XSS** in 37+ JSP files — reflected input without HTML encoding
3. **No CSRF protection** on any iw-business-daemon endpoint
4. **SHA-256 without salt** for password hashing — fast brute-force
5. **No account lockout** — unlimited login attempts

---

## Threat Inventory (STRIDE)

### S — Spoofing Identity

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| S-1 | Credential theft via network sniffing (no TLS) | Critical | High | **Critical** |
| S-2 | Brute-force login (no lockout) | High | High | **High** |
| S-3 | Session hijacking (no Secure/HttpOnly cookie flags) | High | Medium | **High** |
| S-4 | Hardcoded admin password (`__iw_admin__` / `%iwps%`) | Critical | Medium | **Critical** |
| S-5 | Token replay (Bearer token without rotation) | Medium | Low | **Medium** |

### T — Tampering

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| T-1 | Reflected XSS in JSP pages (100+ vulnerable lines) | High | High | **Critical** |
| T-2 | CSRF on state-changing endpoints (no tokens) | High | Medium | **High** |
| T-3 | Parameter tampering on form submissions | Medium | Medium | **Medium** |
| T-4 | Log tampering (no log integrity protection) | Medium | Low | **Medium** |

### R — Repudiation

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| R-1 | Undeniable actions without audit trail | Medium | Medium | **Medium** |
| R-2 | Audit log tampering (DB superuser bypass) | Medium | Low | **Low** |

### I — Information Disclosure

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| I-1 | Credential exposure in plaintext HTTP traffic | Critical | High | **Critical** |
| I-2 | Database credentials in config files | High | Medium | **High** |
| I-3 | API credentials in XSLT `dataconnections.xslt` | High | Medium | **High** |
| I-4 | Error stack traces leaked to users | Medium | Medium | **Medium** |
| I-5 | Session data in URL parameters (JSP brand/solutions) | Low | High | **Low** |

### D — Denial of Service

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| D-1 | No rate limiting on API endpoints | Medium | Medium | **Medium** |
| D-2 | Unbounded file upload/request size | Medium | Low | **Low** |
| D-3 | ConfigContext single-threaded loading | Low | Low | **Low** |

### E — Elevation of Privilege

| ID | Threat | Impact | Likelihood | Risk |
|----|--------|--------|------------|------|
| E-1 | RLS bypassed by JDBC superuser connection | High | Medium | **High** |
| E-2 | Admin functions accessible without role check | High | Medium | **High** |
| E-3 | Cross-company data access (solutionType spoofing) | High | Low | **Medium** |
| E-4 | Legacy compiled servlets with unknown behavior | Medium | Low | **Medium** |

---

## Control Inventory

### Implemented Controls

| ID | Control | Type | Layer | Effectiveness | Mitigates |
|----|---------|------|-------|---------------|-----------|
| C-01 | **SHA-256 Password Hashing** | Preventive | Application | LOW (unsalted) | S-1, S-2 |
| C-02 | **PreparedStatement (SQL Injection Prevention)** | Preventive | Application | HIGH | T-3 |
| C-03 | **Row Level Security (Supabase)** | Preventive | Data | LOW (bypassed by superuser) | E-1 |
| C-04 | **Session Timeout (30 min)** | Preventive | Application | MEDIUM | S-3 |
| C-05 | **Bearer Token Auth (ApiTokenAuthFilter)** | Preventive | Application | MEDIUM | S-5 |
| C-06 | **Token TTL (24h expiry + lazy cleanup)** | Preventive | Application | MEDIUM | S-5 |
| C-07 | **Audit Logging (AuditService)** | Detective | Application | MEDIUM | R-1 |
| C-08 | **Per-Company Flow Isolation** | Preventive | Application | MEDIUM | E-3 |
| C-09 | **ErrorHandlingFilter** | Corrective | Application | MEDIUM | I-4 |
| C-10 | **MFA (TOTP)** | Preventive | Application | HIGH (opt-in) | S-1, S-2 |
| C-11 | **Monitoring Dashboard** | Detective | Application | MEDIUM | D-1 |
| C-12 | **Alert Service (threshold + cooldown)** | Detective | Application | MEDIUM | D-1 |
| C-13 | **`.gitignore` for credentials** | Preventive | Process | MEDIUM | I-2 |
| C-14 | **PR Checks (secret scan workflow)** | Detective | Process | MEDIUM | I-2 |

### Partial Controls

| ID | Control | Status | Gap |
|----|---------|--------|-----|
| C-15 | **Input Validation Framework** (`src/main/java/com/interweave/validation/`) | Built, not deployed | JAR not in Tomcat classpath |
| C-16 | **Email Notifications** (AlertService) | Webhook only | No SMTP credentials configured |
| C-17 | **MFA Enrollment** | Available | Opt-in, not enforced for admins |
| C-18 | **Supabase RLS Policies** | Tables enabled | No row-level policies defined (blanket deny for anon) |
| C-19 | **TLS Configuration** | Template in server.xml | Connectors commented out, no certificate |

### Missing Controls

| ID | Control | Priority | Mitigates |
|----|---------|----------|-----------|
| M-01 | **TLS/HTTPS** | CRITICAL | S-1, I-1 |
| M-02 | **XSS Output Encoding** (JSP `<c:out>` or ESAPI) | CRITICAL | T-1 |
| M-03 | **CSRF Token Filter** | HIGH | T-2 |
| M-04 | **bcrypt/Argon2 Password Hashing** | HIGH | S-1, S-2 |
| M-05 | **Account Lockout / Rate Limiting** | HIGH | S-2, D-1 |
| M-06 | **Secure Cookie Flags** (HttpOnly, Secure, SameSite) | HIGH | S-3, T-2 |
| M-07 | **Content Security Policy Header** | MEDIUM | T-1 |
| M-08 | **X-Frame-Options / X-Content-Type-Options** | MEDIUM | T-1 |
| M-09 | **RBAC Middleware** (admin vs operator role enforcement) | MEDIUM | E-2 |
| M-10 | **Credential Encryption at Rest** | MEDIUM | I-2, I-3 |
| M-11 | **Log Integrity Protection** | LOW | T-4, R-2 |
| M-12 | **Request Size Limiting** | LOW | D-2 |

---

## Mitigation Matrix

Maps each threat to its applicable controls (implemented, partial, and missing):

```
Threat    │ Implemented          │ Partial           │ Missing (Gap)
──────────┼──────────────────────┼───────────────────┼─────────────────────
S-1       │ C-01(LOW), C-10(HI)  │                   │ M-01(CRIT), M-04(HI)
S-2       │ C-01(LOW), C-10(HI)  │                   │ M-04(HI), M-05(HI)
S-3       │ C-04(MED)            │                   │ M-06(HI), M-01(CRIT)
S-4       │                      │                   │ ⚠ HARDCODED — needs code change
S-5       │ C-05(MED), C-06(MED) │                   │
T-1       │                      │ C-15(built)       │ M-02(CRIT), M-07(MED), M-08(MED)
T-2       │                      │                   │ M-03(HI), M-06(HI)
T-3       │ C-02(HIGH)           │                   │
T-4       │ C-07(MED)            │                   │ M-11(LOW)
R-1       │ C-07(MED)            │                   │
R-2       │                      │ C-18(partial)     │ M-11(LOW)
I-1       │                      │ C-19(template)    │ M-01(CRIT)
I-2       │ C-13(MED), C-14(MED) │                   │ M-10(MED)
I-3       │                      │                   │ M-10(MED)
I-4       │ C-09(MED)            │                   │
I-5       │                      │                   │ M-08(MED)
D-1       │ C-11(MED), C-12(MED) │                   │ M-05(HI), M-12(LOW)
D-2       │                      │                   │ M-12(LOW)
D-3       │                      │                   │ (accept — low risk)
E-1       │                      │ C-03(bypassed)    │ M-09(MED)
E-2       │                      │                   │ M-09(MED)
E-3       │ C-08(MED)            │                   │
E-4       │                      │                   │ (accept — legacy code)
```

---

## Defense-in-Depth Analysis

```
Layer          │ Implemented Controls                   │ Gaps
───────────────┼────────────────────────────────────────┼──────────────────────
Network        │ (none)                                 │ TLS, WAF, Rate limiting
Application    │ PreparedStatement, Session timeout,    │ XSS encoding, CSRF,
               │ Token auth, Flow isolation, MFA(opt),  │ Account lockout,
               │ ErrorHandlingFilter, Audit logging     │ Secure cookies, CSP, RBAC
Data           │ RLS (enabled, no policies), .gitignore │ Credential encryption,
               │                                        │ Log integrity
Endpoint       │ (N/A — server-side app)                │
Process        │ PR secret scan, Security docs          │ Pen testing, training
```

**Verdict**: No network-layer controls. Application layer has good SQL injection protection but critical gaps in XSS, CSRF, and authentication hardening. Data layer relies on process controls (.gitignore) rather than encryption.

---

## Implementation Roadmap

### Phase 1 — Critical (Do Now)

| # | Action | Effort | Mitigates |
|---|--------|--------|-----------|
| 1.1 | **Enable TLS** — Uncomment SSL connector in `server.xml`, generate/install certificate (Let's Encrypt or self-signed for dev) | 2h | S-1, I-1, S-3 |
| 1.2 | **Fix XSS in JSPs** — Replace all `<%= variable %>` with `<c:out value="${variable}"/>` or `StringEscapeUtils.escapeHtml4()`. Priority files: IWLogin.jsp, BDConfigurator.jsp, CompanyCredentials.jsp, EditProfile.jsp | 4-8h | T-1 |
| 1.3 | **Add CSRF filter** — Implement `CsrfFilter` generating per-session tokens, validate on POST/PUT/DELETE. Add hidden `_csrf` field to all JSP forms | 4h | T-2 |
| 1.4 | **Set Secure cookie flags** — Add to `web.xml`: `<cookie-config><http-only>true</http-only><secure>true</secure></cookie-config>` | 15min | S-3 |
| 1.5 | **Add account lockout** — Track failed attempts in `ApiLoginServlet` / `LocalLoginServlet`, lock after 5 failures for 15 minutes | 2h | S-2 |

### Phase 2 — High Priority (This Sprint)

| # | Action | Effort | Mitigates |
|---|--------|--------|-----------|
| 2.1 | **Upgrade password hashing** — Migrate from SHA-256 to bcrypt (jBCrypt library). Add migration path: verify old hash, re-hash on successful login | 4h | S-1, S-2 |
| 2.2 | **Add security headers filter** — CSP, X-Frame-Options: DENY, X-Content-Type-Options: nosniff, Referrer-Policy, Permissions-Policy | 2h | T-1, I-5 |
| 2.3 | **Rate limiting on API endpoints** — Simple token-bucket per IP using servlet filter + ConcurrentHashMap (similar to ApiTokenStore pattern) | 3h | D-1, S-2 |
| 2.4 | **Enforce MFA for admin accounts** — Require TOTP setup on first admin login | 2h | S-1, S-4 |
| 2.5 | **Deploy validation framework** — Copy `src/` validation classes to Tomcat classpath, wire into API servlets | 2h | T-3 |

### Phase 3 — Medium Priority (Next Sprint)

| # | Action | Effort | Mitigates |
|---|--------|--------|-----------|
| 3.1 | **RBAC middleware** — Add role-based access filter checking session `userType` against endpoint permissions | 4h | E-2 |
| 3.2 | **Encrypt credentials at rest** — AES-256 encrypt DB passwords in context.xml, XSLT dataconnections. Decrypt at runtime with env-provided key | 8h | I-2, I-3 |
| 3.3 | **Define RLS policies** — Create Supabase RLS policies so even if JDBC connection leaks, data is scoped per company | 4h | E-1 |
| 3.4 | **Request size limiting** — Set `maxPostSize` on Tomcat connector, add `maxSwallowSize` | 30min | D-2 |
| 3.5 | **Log integrity** — Write audit logs to append-only storage or add HMAC signing | 4h | T-4, R-2 |

### Phase 4 — Hardening (Ongoing)

| # | Action | Effort | Mitigates |
|---|--------|--------|-----------|
| 4.1 | **Penetration testing** — Run OWASP ZAP against deployed portal | 4h | All |
| 4.2 | **Dependency audit** — Check 133 vendor JARs for known CVEs (OWASP Dependency-Check) | 2h | E-4 |
| 4.3 | **Remove legacy compiled servlets** — Audit and remove unused `.class` files from original deployment | 2h | E-4 |
| 4.4 | **Security training** — Document secure coding standards for JSP/servlet development | 2h | Process |

---

## Control Effectiveness Scoring

| Control | Status | Effectiveness | Coverage Score | Notes |
|---------|--------|---------------|----------------|-------|
| C-01 SHA-256 Hashing | Implemented | LOW (1/4) | 0.8 | Unsalted, fast to crack |
| C-02 PreparedStatement | Implemented | HIGH (3/4) | 2.4 | Consistent across 41 files, 651 usages |
| C-03 RLS | Implemented | LOW (1/4) | 0.8 | Enabled but superuser bypasses |
| C-04 Session Timeout | Implemented | MEDIUM (2/4) | 1.6 | 30min is reasonable |
| C-05 Bearer Token | Implemented | MEDIUM (2/4) | 1.6 | UUID-based, 24h TTL |
| C-06 Token TTL | Implemented | MEDIUM (2/4) | 1.6 | Lazy cleanup, no rotation |
| C-07 Audit Logging | Implemented | MEDIUM (2/4) | 1.6 | Wired into all 7 API servlets |
| C-08 Flow Isolation | Implemented | MEDIUM (2/4) | 1.6 | solutionType filtering works |
| C-09 ErrorHandlingFilter | Implemented | MEDIUM (2/4) | 1.6 | Catches stack traces |
| C-10 MFA (TOTP) | Implemented | HIGH (3/4) | 2.4 | Opt-in only |
| C-11 Monitoring Dashboard | Implemented | MEDIUM (2/4) | 1.6 | Detects anomalies |
| C-12 Alert Service | Implemented | MEDIUM (2/4) | 1.6 | Threshold + cooldown |
| C-13 .gitignore | Implemented | MEDIUM (2/4) | 1.6 | Process-dependent |
| C-14 PR Secret Scan | Implemented | MEDIUM (2/4) | 1.6 | CI/CD workflow |

**Weighted Score**: 23.6 / 56.0 possible = **42.1% coverage**

---

## Risk Acceptance Register

These threats are acknowledged but accepted given current constraints:

| Threat | Risk | Rationale |
|--------|------|-----------|
| S-4 (Hardcoded admin pw) | Critical | Cannot change without vendor SDK source. Mitigate with network restrictions + MFA enforcement |
| E-4 (Legacy bytecode) | Medium | 253 compiled classes without source. Monitor with dependency scanning |
| D-3 (ConfigContext loading) | Low | Single-instance deployment, restart is acceptable |
| S-5 (Token replay) | Medium | Token TTL limits window. Acceptable for internal tool |

---

## Compliance Mapping

| Control | OWASP Top 10 (2021) | PCI-DSS 4.0 | SOC 2 |
|---------|---------------------|-------------|-------|
| M-01 TLS | A02 Crypto Failures | Req 4.1 | CC6.1 |
| M-02 XSS Fix | A03 Injection | Req 6.2 | CC6.1 |
| M-03 CSRF | A01 Broken Access | Req 6.2 | CC6.1 |
| M-04 bcrypt | A02 Crypto Failures | Req 8.3 | CC6.1 |
| M-05 Lockout | A07 Auth Failures | Req 8.1.6 | CC6.1 |
| M-06 Cookies | A07 Auth Failures | Req 6.2 | CC6.1 |
| C-02 SQLi Prevention | A03 Injection | Req 6.2 | CC6.1 |
| C-07 Audit Log | A09 Logging Failures | Req 10.2 | CC7.2 |
| C-10 MFA | A07 Auth Failures | Req 8.3 | CC6.1 |

---

## Quick-Win Implementation Guide

### 1. Secure Cookie Flags (15 minutes)

Add to `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/web.xml` inside `<session-config>`:

```xml
<session-config>
    <session-timeout>30</session-timeout>
    <cookie-config>
        <http-only>true</http-only>
        <secure>true</secure>  <!-- enable after TLS -->
    </cookie-config>
</session-config>
```

### 2. Security Headers Filter (2 hours)

Create `SecurityHeadersFilter.java`:

```java
package com.interweave.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SecurityHeadersFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        response.setHeader("Permissions-Policy", "geolocation=(), camera=(), microphone=()");
        // Add CSP after XSS fixes are in place:
        // response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
        chain.doFilter(req, res);
    }
    public void init(FilterConfig config) {}
    public void destroy() {}
}
```

### 3. Account Lockout Pattern (2 hours)

Add to `ApiLoginServlet` / `LocalLoginServlet`:

```java
// In-memory lockout tracker (ConcurrentHashMap)
private static final ConcurrentHashMap<String, int[]> failedAttempts = new ConcurrentHashMap<>();
private static final int MAX_ATTEMPTS = 5;
private static final long LOCKOUT_DURATION_MS = 15 * 60 * 1000; // 15 minutes

private boolean isLockedOut(String email) {
    int[] record = failedAttempts.get(email);
    if (record == null) return false;
    if (record[0] >= MAX_ATTEMPTS) {
        if (System.currentTimeMillis() - record[1] < LOCKOUT_DURATION_MS) return true;
        failedAttempts.remove(email); // Lockout expired
    }
    return false;
}

private void recordFailure(String email) {
    failedAttempts.merge(email, new int[]{1, (int)(System.currentTimeMillis()/1000)},
        (old, v) -> new int[]{old[0] + 1, (int)(System.currentTimeMillis()/1000)});
}

private void clearFailures(String email) {
    failedAttempts.remove(email);
}
```

---

*This document should be reviewed and updated quarterly or after any significant architecture change.*
