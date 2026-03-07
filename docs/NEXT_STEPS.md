# InterWeave IDE — Next Steps Roadmap

**Last Updated:** 2026-03-07
**Project:** IW_Launcher — Enterprise Data Integration Platform
**Stack:** Eclipse 3.1 IDE + Tomcat 9.0.83 + Supabase Postgres
**React Portal:** Vite + React 19 + TypeScript (strict) + Tailwind 4 + shadcn/ui + TanStack Query + Recharts

---

## Completed Items (as of 2026-03-07)

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
- Configuration Wizard (4-step React page + ApiConfigurationServlet backend)
- Registration pages (user + company, React + API servlets)
- Change Password page + ApiChangePasswordServlet
- Flow Management API (ApiFlowManagementServlet — start/stop/schedule flows)
- Toast notification system, ErrorBoundary, functional search/command palette
- Mobile navigation (hamburger drawer)
- Dynamic document titles on all pages
- Login page redesign + "already signed in" state (banner bug fix)
- 55 TypeScript source files, 10 API servlet .class files
- All 11 React pages use shadcn/ui consistently

---

## Table of Contents

1. [Immediate (Ready Now)](#immediate-ready-now)
   - [1. Merge feature/react-form-pages to main](#1-merge-featurereact-form-pages-to-main) — only remaining immediate item
2. [Blocked on External Actions](#blocked-on-external-actions)
   - [5. Enable ErrorHandlingFilter](#5-enable-errorhandlingfilter) — blocked on Maven
   - [6. Configure Email/Webhook Monitoring](#6-configure-emailwebhook-monitoring) — blocked on SMTP creds
3. [Medium Term](#medium-term)
   - [8. Deep Configuration Wizard](#8-deep-configuration-wizard) — LOW priority, 8-12 hrs
4. [Future / Nice-to-Have](#future--nice-to-have)

---

## Immediate (Ready Now)

### 1. Merge feature/react-form-pages to main

**[PRIORITY: CRITICAL]** **[Effort: ~15 min]**

The `feature/react-form-pages` branch contains 20+ commits of React portal work: 55 source files, 10 API servlets, full shadcn/ui library, monitoring charts, configuration wizard, engine controls, registration pages, and more. This should be merged to establish a clean checkpoint.

**Steps:**
```bash
git checkout main
git merge feature/react-form-pages
git push origin main
```

---

### ~~2. TransactionLogger Instrumentation~~ — DONE (2026-03-07)

`TransactionLoggingFilter.java` implemented in `iwtransformationserver/WEB-INF/`, registered in `web.xml`, compiled. Intercepts `/transform`, `/scheduledtransform`, `/iwxml`. Requires Tomcat restart to activate.

---

### ~~3. E2E Tests for New API Endpoints~~ — DONE (2026-03-07)

`web_portal/test_api.sh` — 7 phases, ~25 test cases covering all 12+ new API endpoints.

---

### ~~4. LoggingPage React Replacement~~ — DONE (2026-03-07)

`LoggingPage.tsx` already exists and is routed at `/admin/logging`. Shows log file list, system status bar, and links to classic Logging.jsp.

---

## Blocked on External Actions

### 5. Enable ErrorHandlingFilter

**[PRIORITY: LOW]** **[Effort: ~30 min once Maven is installed]**

**Blocker:** Maven 3.6+ must be installed. Not currently available on this machine.

The filter source is ready at `src/main/java/com/interweave/web/ErrorHandlingFilter.java`. Once Maven is installed:
```bash
mvn -DskipTests package
cp target/iw-error-framework-1.0.0.jar web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/
# Uncomment ErrorHandlingFilter in web.xml, restart Tomcat
```

**Note:** All servlets/JSPs have fallback error handling. This is an enhancement, not a blocker.

---

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

## Medium Term

### 7. ~~SF2AuthNet Workflow Deepening~~ — DONE (2026-03-07)

Completed: SF2AUTH compiler module added to WorkspaceProfileCompiler, regression corpus file created, verify_profile_compiler.ps1 path fixed.

---

### 8. Deep Configuration Wizard

**[PRIORITY: LOW]** **[Effort: ~8-12 hrs]**

The current React wizard has 4 steps (solution type, object mapping, credentials, review). The classic JSP wizard has 6 pages with 80-120 field-level mappings. Expanding the React wizard to cover field-level detail would provide full classic parity.

**Affected files:** `ConfigurationWizardPage.tsx`, `types/configuration.ts`, potentially `ApiConfigurationServlet.java`

---

## Future / Nice-to-Have

| Item | Description | Effort |
|---|---|---|
| ViewLog React page | Log file viewer with search/filter | 2-3 hrs |
| MoreCustomMappings page | Custom field mapping editor | 4-6 hrs |
| ASSA enhancements | Sparklines, MFA, notifications, audit log | 8-16 hrs |
| InterWoven features | AI field mapping, visual workflow builder, OAuth broker | 16-40 hrs |

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
