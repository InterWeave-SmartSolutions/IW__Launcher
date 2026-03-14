# InterWeave IDE — Next Steps Roadmap

**Last Updated:** 2026-03-14 (Session 27-28 — Engine flow definitions in config templates, Cloudflare tunnel + Vercel portal login verified, production server audit committed)
**Project:** IW_Launcher — Enterprise Data Integration Platform
**Stack:** Eclipse 3.1 IDE + Tomcat 9.0.83 + Supabase Postgres
**React Portal:** Vite + React 19 + TypeScript (strict) + Tailwind 4 + shadcn/ui + TanStack Query + Recharts

---

## Completed Items (as of 2026-03-08)

These items from the original roadmap are now DONE:

| # | Item | Branch | Date |
|---|---|---|---|
| 2 | Fix InterWoven submodule drift | main | 2026-03-06 |
| 4 | React forms — Profile/Company pages | feature/react-form-pages | 2026-03-06 |
| 5 | Recharts monitoring visualizations | feature/react-form-pages | 2026-03-06 |
| 6 | Populate data/ directories | feature/react-form-pages | 2026-03-07 |
| 9 | Integration Manager React page | feature/react-form-pages | 2026-03-06 |
| 2b | TransactionLoggingFilter (iwtransformationserver) | feature/react-form-pages | 2026-03-07 |
| 3 | E2E API test script (test_api.sh) | feature/react-form-pages | 2026-03-07 |
| 4 | LoggingPage React page | feature/react-form-pages | 2026-03-07 |
| 7 | SF2AuthNet compiler module | feature/react-form-pages | 2026-03-07 |

**Additional completed work (not in original roadmap):**
- 10 shadcn/ui components (Button, Input, Label, Badge, Separator, Select, Tabs, Dialog, Switch, Tooltip)
- Configuration Wizard (5-step React page + ApiConfigurationServlet backend with profiles/test endpoints)
- Configuration system overhaul: progressive disclosure, bulk actions, smart defaults, dependency warnings, config diff, draft persistence, JSON export, test connection, help tooltips
- Registration pages (user + company, React + API servlets)
- Change Password page + ApiChangePasswordServlet
- Flow Management API (ApiFlowManagementServlet — start/stop/schedule/properties flows)
- Flow Properties Dialog (React modal for viewing/editing variable parameters, password toggle, read-only when running)
- Log Viewer API (ApiLogViewerServlet — log file listing, content retrieval, search)
- Toast notification system, ErrorBoundary, functional search/command palette
- Global error toast for mutations (MutationCache.onError handles 401, 500+, network errors)
- Dashboard KPI sparklines (transaction count, success rate, avg duration — derived from transaction history)
- Mobile navigation (hamburger drawer)
- Dynamic document titles on all pages
- Login page redesign + "already signed in" state (banner bug fix)
- Shared config label utilities (config-labels.ts)
- 108 TypeScript source files (41 pages), 16 API servlet sources
- All React pages use shadcn/ui consistently, light + dark mode verified
- ErrorHandlingFilter compiled and enabled (bypassed Maven with direct javac)
- MFA / Forgot Password: TOTP-based MFA (Google Authenticator), password reset tokens, 3 React pages, 2 API servlets
- Notifications Inbox: system/alert/flow/security notification types, bell badge, read/unread, React page + API servlet
- Audit Log: login/config/flow event tracking, admin-only filterable table, AuditService wired into ApiLoginServlet
- 3 database schemas (mfa, notifications, audit_log) with RLS
- Code-split all new pages via React.lazy() — main chunk under 500kB
- Config Wizard expansion: schema-driven object detail panels (~80 properties matching original JSP UI), categorized review sections with human-readable labels
- Session 14: Filtering/pagination on all listing pages, light mode blending fixes (LoggingPage, IDESyncPage), toggleable Live Engine Log panel (bottom/side/collapsed modes)
- Session 16-18: Full integration test, XML sanitizer fix, IDE project visibility, STRIDE threat mitigation, comprehensive security hardening (5 filters, 26 JSPs, CSRF, rate limiting, session fixation)
- Session 19: AutoImportStartup plugin compiled (IDE Navigator shows workspace projects), OSGI cache recovery, START/STOP.bat tunnel orchestration
- Session 19b: WCAG 2.2 AA accessibility audit (8 CRITICAL fixes: skip nav, route announcer, ARIA combobox, focus traps, toast ARIA, label bindings, form error alerts). Security headers (HttpHeaderSecurityFilter). SPA route completeness (+7 missing mappings). Contrast fixes (--primary 5:1, --muted-foreground 4.7:1). Focus ring overhaul (Button/Input/Select). prefers-reduced-motion support.
- Session 14: Cross-UI session leak fix — LocalLogoutServlet replaces broken original (session.invalidate() + Bearer token clearing). ApiLogoutServlet for React UI. E2E test suite (29/29 pass).
- Session 20: IDE deep dive — all 308 plugin classes analyzed (229 GUI-dependent/74%, 79 GUI-free). ConfigContext, ProjectActions, JAXB model, build pipeline reverse-engineered. Headless verdict: NO. Server-side replication 80%+ complete. Full analysis: `docs/development/IDE_DEEP_DIVE.md`.
- Session 21: AI Management Architecture — three-party (AI + IDE + Portal) concurrent workspace management. 5-layer architecture, 14 new API endpoints designed, SSE push, change-source tracking, optimistic locking. Full design: `docs/development/AI_MANAGEMENT_ARCHITECTURE.md`.
- Session 22: Verified RBAC compiles clean, ErrorHandlingFilter already active (updated CLAUDE.md Known Issues #2), installed cloudflared, Vercel + tunnel deployment prep. CSP `search.js` committed.
- Session 23: CSP hardening — extracted all inline scripts from 7 JSPs to external .js files, converted 8 onclick handlers to event delegation, deployed HelpLinkService (fixed help-popup.jsp 500), SecurityHeadersFilter now sends `script-src 'self' https://cdn.jsdelivr.net` (no unsafe-inline).
- Session 24: bcrypt migration — centralized PasswordHasher utility (hash/verify/needsRehash/rehashIfNeeded), jBCrypt 0.4 deployed, progressive rehash on login (SHA-256→bcrypt transparent upgrade), 10+ servlets updated (all API + Local config), login verified.
- Session 25: AI Management Architecture Phase 1 — ApiWorkspaceManagementServlet (3 endpoints: project listing, detail, raw config) + ApiBuildServlet (XSLT compilation + inventory). Fixed cross-compilation bug: `--release 8` required instead of `-source 1.8 -target 1.8` (endorsed JAXB 1.0-ea JAR causes AbstractMethodError). All compile commands updated in CLAUDE.md.
- Session 25-26: Credential encryption at rest (AES-256-GCM, CredentialEncryptionService, ENC: prefix), vendor JAR CVE audit (175 JARs, `docs/security/CVE_AUDIT_2026_03_13.md`), NPE fix in handlePutWizard version parsing.
- Session 27: Engine flow definitions added to all 3 config.xml templates (SF2AUTH + CRM2M2 = 14 flows). Cloudflare tunnel started, Vercel portal login verified end-to-end via Bearer token auth.
- Session 28: Production server audit committed — `docs/SERVER_AUDIT_2026_03_13.md` (551-line architecture overview), `docs/production-reference/` (525 files from 107525-UVS13: configs, engine logs, workspace projects, IDE snapshots, customer portal). `frontends/IWCustomerPortal` (legacy payment portal HTML).

---

## Table of Contents

1. [Immediate (Ready Now)](#immediate-ready-now)
   - [Database Migrations](#10-database-migrations-3-new-schemas) — run 3 new schema files on Supabase
   - [Audit Integration](#11-wire-auditservice-into-remaining-servlets) — AuditService in remaining servlets
2. [Blocked on External Actions](#blocked-on-external-actions)
   - [6. Configure Email/Webhook Monitoring](#6-configure-emailwebhook-monitoring) — blocked on SMTP creds
3. [Future / Nice-to-Have](#future--nice-to-have)

---

## Immediate (Ready Now)

### ~~13. Bidirectional Sync Bridge (IDE ↔ Web Portal)~~ DONE

**Implemented:** `scripts/sync_bridge.ps1` — PowerShell FileSystemWatcher daemon

**What it does:**
- Watches `workspace/*/configuration/` and `workspace/*/xslt/` for IDE-initiated file changes
- On change (debounced 2s), calls `WorkspaceProfileSyncServlet?action=importProfile` to push to DB
- Then calls `WorkspaceProfileCompilerServlet?action=compileProfile` to regenerate overlays
- Excludes `GeneratedProfiles/`, `IW_Runtime_Sync/`, `.metadata/` (portal-generated, not IDE)
- Zero dependencies — uses built-in Windows PowerShell `FileSystemWatcher` (.NET)

**Integration:**
- `START.bat` launches sync bridge automatically (background, minimized)
- `STOP.bat` kills sync bridge before shutting down Tomcat
- Standalone: `scripts/start_sync_bridge.bat` / `scripts/stop_sync_bridge.bat`
- Logs: `logs/sync_bridge.log`
- PID tracking: `logs/sync_bridge.pid` (prevents duplicate instances)
- **Live-tested 2026-03-09:** Both profiles imported + recompiled, bridge survives errors, stops cleanly

### ~~14. Obtain iw_sdk_1.0.0 Source Code~~ PARTIALLY RESOLVED

**Deep decompilation analysis completed (2026-03-13, Session 20):**
- All 308 plugin classes analyzed (229 GUI-dependent/74%, 79 GUI-free)
- Key classes fully reverse-engineered via `javap`: Designer, ConfigContext (73KB/51 static fields/110+ methods), ProjectActions (4-stage bitmask build pipeline), TransactionBase, TransactionContext, QueryContext, NavigationView, TemplateEditorView, XSLTEditorView
- JAXB model mapped: `iwmappingsType` → `transactionType[]` → `datamapType[]` + `nexttransactionType[]`
- Three-way config binding documented: config.xml ↔ soltran.xslt ↔ transactions.xml
- Build pipeline: XSLT → .class via Apache XSLTC, soltran assembly from dat fragments
- **Headless verdict: NO** — Windows x86 binary, SWT GUI deps, PlatformUI.createAndRunWorkbench
- **Server-side replication already 80%+ complete** via Business Daemon ConfigContext (GUI-free)
- Full analysis: `docs/development/IDE_DEEP_DIVE.md`

**Still beneficial but no longer blocking:** vendor source access would enable live workspace auto-refresh, reverse sync on IDE build/save, and native Push/Pull commands. Contact Integration Technologies, Inc. if desired.

---

## Blocked on External Actions

### ~~6. Configure Email/Webhook Monitoring~~ PARTIALLY DONE

**Webhook monitoring ENABLED (2026-03-10):**
- `monitoring.properties` created with `webhook.enabled=true`, `email.enabled=false`
- `ApiWebhookReceiverServlet` created — self-notification endpoint at `POST /api/webhooks/receive`
- Webhook receiver verified: returns `{"success":true}` on POST
- Webhook polling: 30s interval, 10 max failures before auto-disable
- Alerting enabled globally with 15min cooldown, 50/day max

**Still blocked:** SMTP email notifications require SMTP credentials.
To enable email, edit `monitoring.properties`: set `email.enabled=true`, fill in `smtp.*` settings.

---

### ~~10. Database Migrations (3 new schemas)~~ DONE

All three schemas verified present on Supabase (2026-03-10):
- `password_reset_tokens` — 6 columns, RLS enabled
- `user_mfa_settings` — 8 columns, RLS enabled
- `notifications` — 10 columns, RLS enabled
- `audit_log` — 12 columns, RLS enabled, 26 rows already recorded

---

### ~~11. Wire AuditService into Remaining Servlets~~ DONE

AuditService wired into all 6 remaining servlets (2026-03-10):
- `ApiProfileServlet` — profile_update, password_change events
- `ApiCompanyProfileServlet` — company_update, password_change events
- `ApiConfigurationServlet` — config_change events
- `ApiRegistrationServlet` / `ApiCompanyRegistrationServlet` — registration events
- `ApiChangePasswordServlet` — password_change events

---

### ~~12. Expand Detail Schemas (Invoice, Product, Vendor)~~ DONE

Detail schemas fully expanded (2026-03-10):
- INVOICE: 14 groups, 79 fields (was 1 group / 4 fields)
- PRODUCT: 8 groups, 72 fields (was 1 group / 6 fields)
- VENDOR: 8 groups, 42 fields (was 2 groups / 4 fields)
- 149 new CONFIG_KEY_LABELS entries, new "vendor" ReviewCategory

---

## Public Showcase (Vercel + Tunnel)

**React portal is live at https://iw-portal.vercel.app**

| Component | Status | Notes |
|---|---|---|
| Vercel deployment | ✅ Live | `frontends/iw-portal`, auto-builds on push to main |
| Vercel login | ✅ Working | Bearer token auth through Cloudflare tunnel (verified 2026-03-14) |
| Engine flows via Vercel | ✅ Working | 8 scheduled + 6 query flows returned through tunnel proxy |
| Cloudflare Quick Tunnel | ✅ Working | `scripts/quickstart_tunnel.bat` — no auth needed, auto-patches vercel.json |
| Named Cloudflare Tunnel | ⚠️ Optional | `scripts/setup_cloudflare_tunnel.bat` — stable URL, requires Cloudflare account |

### 15. ~~Replace localtunnel with Cloudflare Tunnel~~ DONE

**Resolved (2026-03-11):**
- **Quick tunnel (recommended):** Double-click `scripts\quickstart_tunnel.bat` — starts Cloudflare Quick Tunnel (no account/auth), captures `*.trycloudflare.com` URL, auto-patches `vercel.json`, commits + pushes, Vercel rebuilds in ~30s. Keep the window open (tunnel lives while it runs).
- **Named tunnel (optional):** `scripts\setup_cloudflare_tunnel.bat` creates a persistent named tunnel with stable `*.cfargotunnel.com` URL (requires Cloudflare account login).
- **Vercel rootDirectory bug fixed (2026-03-11):** trailing space in project settings caused all builds to fail (0ms/ERROR). Patched via Vercel API.

**Scripts:**
- `scripts/quickstart_tunnel.bat` — one-click: starts Tomcat if needed → Cloudflare quick tunnel → patches vercel.json → git push
- `scripts/setup_cloudflare_tunnel.bat` — named tunnel setup (Cloudflare auth + stable URL)
- `scripts/start_cloudflare_tunnel.bat` — starts named tunnel from existing config
- `scripts/stop_cloudflare_tunnel.bat` — kills tunnel process

---

## UI Prototype Incorporation (Three-Portal Architecture)

Two new static HTML prototypes were added to `docs/ui-ux/` — they define the target UI for the Associate Portal and Master Console surfaces. Both use the same ASSA design tokens as `iw-portal` (same color system, glassmorphism, card/table/sidebar primitives).

**Full architecture doc:** `docs/ui-ux/PORTAL_ARCHITECTURE.md`

### Associate Portal (`docs/ui-ux/iw_associate_portal/`) — 9 pages
Customer-facing portal for ASSA associate owners/members. Key new patterns:
- **Hero section** — welcome banner with role-aware CTAs (not in iw-portal yet)
- **Tile component** — nested content cards within cards
- **Business Checkup** — structured intake wizard (maps to Config Wizard concept)
- **Resource Library + Search** — content browsing with category filters
- **Webinars** — media library (upcoming + recorded)
- **Billing** — subscription/plan management (future Stripe integration)

### Master Console (`docs/ui-ux/iw_master_console/`) — 10 pages
Program admin console for IW/ASSA administrators. Key new patterns:
- **4-column KPI grid** (`span-3`) with SVG sparklines via `data-spark` attribute
- **Operational Queue** — filterable admin action table with status dots
- **Analytics** — engagement funnel, content adoption, search demand signals
- **Subscriptions** — plan management, churn metrics, MRR tracking
- **Content Library** — resource publishing/CMS admin
- **Users & Roles** — cross-org user management with role assignment

### Phased adoption into iw-portal (see PORTAL_ARCHITECTURE.md):
- **Phase A** (1-2 hrs): Hero section on Dashboard, 4th KPI card, radius token alignment
- **Phase B** (8-12 hrs): `/resources`, `/resources/webinars`, `/billing` pages
- **Phase C** (12-16 hrs): `/admin/content`, `/admin/analytics`, `/admin/subscriptions` pages
- **Phase D** (4-8 hrs): Role architecture — `operator|associate|admin` field, sidebar auto-config, route guards

---

## Future / Nice-to-Have

| Item | Description | Effort | Status |
|---|---|---|---|
| ViewLog React page | Log file viewer with search/filter | 2-3 hrs | DONE (LoggingPage.tsx) |
| ~~MoreCustomMappings page~~ | Custom field mapping editor | 4-6 hrs | DONE (maps 2-9 in detail schema + config wizard) |
| ~~Sparklines~~ | Dashboard KPI sparkline charts | ~1 hr | DONE (3 of 4 KPIs) |
| ~~Error toast~~ | Global error toast for API failures | ~30 min | DONE (MutationCache.onError) |
| ~~MFA / Forgot password~~ | TOTP MFA + password reset tokens | 4-8 hrs | DONE (3 pages, 2 servlets, DB schema) |
| ~~Notifications inbox~~ | Notification system with bell badge | 4-6 hrs | DONE (page, servlet, NotificationService, DB schema) |
| ~~Audit log~~ | Admin audit trail with filters | 6-8 hrs | DONE (page, servlet, AuditService, DB schema) |
| InterWoven features | AI field mapping, visual workflow builder, OAuth broker | 16-40 hrs | STARTED (FieldMappingPage prototype) |
| RBAC middleware | Per-role access filter (operator/associate/admin) | 3-5 hrs | DONE (RoleGuard, getAllowedPortals, TS compiles clean) |
| ~~Security Headers~~ | ~~HttpHeaderSecurityFilter on iw-portal, meta tags~~ | ~~1 hr~~ | DONE (X-Frame-Options, nosniff, XSS-Protection) |
| ~~WCAG 2.2 AA Accessibility~~ | ~~Skip nav, ARIA, focus traps, contrast, reduced motion~~ | ~~3-4 hrs~~ | DONE (8 CRITICAL + 4 contrast/focus fixes) |
| ~~Cross-UI Session Leak~~ | ~~Logout from JSP/React properly invalidates sessions + Bearer tokens~~ | ~~3-4 hrs~~ | DONE (LocalLogoutServlet + ApiLogoutServlet + E2E 29/29) |
| ~~Content Security Policy (JSPs)~~ | ~~Audit inline scripts/styles in JSPs, add CSP headers~~ | ~~2-4 hrs~~ | DONE (7 JSPs refactored, strict `script-src 'self'`, SecurityHeadersFilter) |
| ~~bcrypt migration~~ | ~~Replace SHA-256 with bcrypt, add jBCrypt JAR~~ | ~~2-3 hrs~~ | DONE (PasswordHasher utility, progressive rehash, Session 24) |
| ~~Credential encryption~~ | ~~Encrypt DB passwords in config files at rest~~ | ~~2-3 hrs~~ | DONE (AES-256-GCM, CredentialEncryptionService, Session 25-26) |
| ~~Vendor JAR CVE audit~~ | ~~OWASP dependency-check on 133 iwtransformationserver JARs~~ | ~~1-2 hrs~~ | DONE (175 JARs audited, `docs/security/CVE_AUDIT_2026_03_13.md`, Session 25-26) |
| ~~Engine flow definitions~~ | ~~Populate config.xml templates with flow definitions~~ | ~~1 hr~~ | DONE (14 CRM2M2 + 3 SF2AUTH flows in all 3 templates, Session 27) |
| ~~Production server audit~~ | ~~Comprehensive sweep of 107525-UVS13~~ | ~~4-6 hrs~~ | DONE (551-line report + 525 reference files, Session 28) |
| CVE remediation | Tomcat 9.0.98+, Xerces/Xalan upgrade, remove test JARs | 4-8 hrs | Open |
| Named Cloudflare tunnel | Stable URL (requires Cloudflare account login) | 1 hr | Open (quick tunnel working) |

### Transformation Server Status: OPERATIONAL (2026-03-09)

The `iwtransformationserver` webapp is **fully deployed and serving requests**. Vendor JARs were obtained from the Rackspace InterWeave test server and deployed to `WEB-INF/lib/`.

- **`/transform`** → HTTP 200, returns `<iwtransformationserver><iwrecordset>` XML
- **`/index`** → HTTP 200
- **JAXB 1.0-ea conflict resolved**: Created `jre/lib/endorsed/jaxb-1.0-ea-trimmed.jar` — a version of `jaxb-rt-1.0-ea.jar` with `org/w3c/dom/` and `org/xml/sax/` classes excluded to prevent DOM Level 1 vs Level 2 conflict with Tomcat 9
- **`TransactionLoggingFilter` disabled** in `web.xml` (class lives in `iw-business-daemon`, not reachable here)

**Remaining**: Query flow "GO" buttons generate correct URLs — actual end-to-end flow execution against live external systems (Salesforce, QuickBooks, etc.) requires valid API credentials in workspace project configurations.

---

## Verification Commands

```powershell
# Full system readiness check
scripts\system_check.bat

# Runtime + iwtransformationserver reachability
powershell -ExecutionPolicy Bypass -File .\scripts\verify_legacy_engine.ps1

# Profile compiler regression
powershell -ExecutionPolicy Bypass -File .\scripts\verify_profile_compiler.ps1

# E2E portal tests (from WSL2)
bash web_portal/test_portal.sh

# E2E session + routing tests (Playwright, requires Tomcat running)
python frontends/iw-portal/tests/e2e_session_and_routing.py

# React portal build check
cd frontends/iw-portal && npx tsc --noEmit && npm run build
```

---

*This document is maintained as part of the IW_Launcher repository. Update the "Last Updated" date after each major session.*
