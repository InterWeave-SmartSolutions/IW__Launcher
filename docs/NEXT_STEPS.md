# InterWeave IDE — Next Steps Roadmap

**Last Updated:** 2026-03-08 (Session 5)
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
- 58 TypeScript source files, 12 API servlet .class files
- All 12 React pages use shadcn/ui consistently
- ErrorHandlingFilter compiled and enabled (bypassed Maven with direct javac)
- MFA / Forgot Password: TOTP-based MFA (Google Authenticator), password reset tokens, 3 React pages, 2 API servlets
- Notifications Inbox: system/alert/flow/security notification types, bell badge, read/unread, React page + API servlet
- Audit Log: login/config/flow event tracking, admin-only filterable table, AuditService wired into ApiLoginServlet
- 3 database schemas (mfa, notifications, audit_log) with RLS
- Code-split all new pages via React.lazy() — main chunk under 500kB
- Config Wizard expansion: schema-driven object detail panels (~80 properties matching original JSP UI), categorized review sections with human-readable labels

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

---

## Blocked on External Actions

### 6. Configure Email/Webhook Monitoring

**[PRIORITY: MEDIUM]** **[Effort: ~5 min once SMTP credentials are available]**

**Blocker:** SMTP credentials or webhook URLs needed.

```bash
cp web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/monitoring.properties.template \
   web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/monitoring.properties
# Fill in SMTP host, port, username, password
# Restart Tomcat
```

---

### 10. Database Migrations (3 new schemas)

**[PRIORITY: HIGH]** **[Effort: ~5 min via Supabase SQL editor]**

Three new database schemas need to be applied to the Supabase instance:

```bash
# Run in order via Supabase SQL Editor or psql:
database/mfa_password_reset_schema_postgres.sql   # password_reset_tokens + user_mfa_settings
database/notifications_schema_postgres.sql         # notifications table
database/audit_log_schema_postgres.sql             # audit_log table
```

Until these migrations are run, the MFA, notifications, and audit log features will return 500 errors from the API servlets (table does not exist). All other portal functionality remains unaffected.

---

### 11. Wire AuditService into Remaining Servlets

**[PRIORITY: MEDIUM]** **[Effort: ~1 hr]**

AuditService is currently only wired into `ApiLoginServlet`. These servlets still need audit event calls:
- `ApiProfileServlet` (profile update events)
- `ApiCompanyProfileServlet` (company update events)
- `ApiConfigurationServlet` (config change events)
- `ApiRegistrationServlet` / `ApiCompanyRegistrationServlet` (registration events)
- `ApiChangePasswordServlet` (password change events)

---

### 12. Expand Detail Schemas (Invoice, Product, Vendor)

**[PRIORITY: LOW]** **[Effort: ~1 hr]**

The Invoice, Product, and Vendor schemas in `object-detail-schema.ts` are minimal stubs. They can be expanded with additional fields from `DetailT1.jsp` and `DetailT2.jsp` to match full original JSP coverage.

---

## Future / Nice-to-Have

| Item | Description | Effort | Status |
|---|---|---|---|
| ViewLog React page | Log file viewer with search/filter | 2-3 hrs | DONE (LoggingPage.tsx) |
| MoreCustomMappings page | Custom field mapping editor | 4-6 hrs | LOW (compiled-only backend `CustomMappings` servlet) |
| ~~Sparklines~~ | Dashboard KPI sparkline charts | ~1 hr | DONE (3 of 4 KPIs) |
| ~~Error toast~~ | Global error toast for API failures | ~30 min | DONE (MutationCache.onError) |
| ~~MFA / Forgot password~~ | TOTP MFA + password reset tokens | 4-8 hrs | DONE (3 pages, 2 servlets, DB schema) |
| ~~Notifications inbox~~ | Notification system with bell badge | 4-6 hrs | DONE (page, servlet, NotificationService, DB schema) |
| ~~Audit log~~ | Admin audit trail with filters | 6-8 hrs | DONE (page, servlet, AuditService, DB schema) |
| InterWoven features | AI field mapping, visual workflow builder, OAuth broker | 16-40 hrs | — |

### Known Infrastructure Limitation: Transformation Server

The `iwtransformationserver` webapp is deployed as a **skeleton** — it has `web.xml` and native JNI libraries (`TS_JNI.dll`) but the 137 Java class JARs from the InterWeave vendor are **NOT included** in this repo. This means:

- **Query flow "GO" buttons** generate correct HTTP GET URLs (`/iwtransformationserver/transform?...`) but get **404** because `com.interweave.servlets.IWTransform` cannot be loaded
- **Scheduled flow execution** via `/scheduledtransform` also returns 404
- **Flow properties** (variable parameters, credentials) can still be viewed and edited in React — only actual flow *execution* requires the vendor JARs
- **TransactionLoggingFilter** is registered in `web.xml` but has no traffic to intercept until vendor JARs are deployed
- **To fix**: Obtain the full transformation server JAR package from the InterWeave vendor and deploy to `web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/`

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

# React portal build check
cd frontends/iw-portal && npx tsc --noEmit && npm run build
```

---

*This document is maintained as part of the IW_Launcher repository. Update the "Last Updated" date after each major session.*
