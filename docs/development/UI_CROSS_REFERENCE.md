# UI Cross-Reference: Classic JSP → React iw-portal Migration Map

> Generated 2026-03-06 by comprehensive analysis of all 5 UI surfaces + backend + IDE sync.
> Source: 7 parallel deep-dive agents analyzing 31 JSPs, 26 React files, 20 ASSA pages, 47 InterWoven components, 30 Java servlets, full IDE sync bridge, and platform training docs.

---

## Table of Contents

1. [Classic → React Coverage Matrix](#1-classic--react-coverage-matrix)
2. [User Flow Analysis: Classic Portal](#2-user-flow-analysis-classic-portal)
3. [What the React UI Currently Implements](#3-what-the-react-ui-currently-implements)
4. [Critical Gaps (Classic Functionality Missing from React)](#4-critical-gaps)
5. [IDE Sync Requirements for React UI](#5-ide-sync-requirements)
6. [ASSA Design Additions](#6-assa-design-additions)
7. [InterWoven Feature Additions](#7-interwoven-feature-additions)
8. [Prioritized Implementation Roadmap](#8-prioritized-implementation-roadmap)
9. [New API Servlets Needed](#9-new-api-servlets-needed)
10. [Login Page Redesign Notes](#10-login-page-redesign-notes)

---

## 1. Classic → React Coverage Matrix

> Updated 2026-03-07 — reflects all work on `feature/react-form-pages` branch.

| # | Classic JSP Page | URL | React Route | Status | Notes |
|---|---|---|---|---|---|
| 1 | IWLogin.jsp | `/IWLogin.jsp` | `/login` (LoginPage) | **DONE** ✓ | Redesigned, shadcn/ui, "already signed in" state |
| 2 | EditProfile.jsp | `/EditProfile.jsp` | `/profile` (ProfilePage) | **DONE** ✓ | Full form, password section, shadcn/ui |
| 3 | EditCompanyProfile.jsp | `/EditCompanyProfile.jsp` | `/company` (CompanyPage) | **DONE** ✓ | Full form, KPI bar, password change, shadcn/ui |
| 4 | ChangePassword.jsp | `/ChangePassword.jsp` | `/profile/password` (ChangePasswordPage) | **DONE** ✓ | Dedicated page + ApiChangePasswordServlet |
| 5 | ChangeCompanyPassword.jsp | `/ChangeCompanyPassword.jsp` | `/company` (built-in section) | **DONE** ✓ | Integrated in CompanyPage security panel |
| 6 | Registration.jsp | `/Registration.jsp` | `/register` (RegisterPage) | **DONE** ✓ | shadcn/ui, ApiRegistrationServlet |
| 7 | CompanyRegistration.jsp | `/CompanyRegistration.jsp` | `/register/company` (CompanyRegisterPage) | **DONE** ✓ | Radix Select, ApiCompanyRegistrationServlet |
| 8 | CompanyConfiguration.jsp | `/CompanyConfiguration.jsp` | `/company/config` (CompanyConfigPage) | **DONE** ✓ | 5-step checklist, progress bar, dynamic completion |
| 9-13 | ConfigurationDetail*.jsp (5 pages) | (servlet forward) | `/company/config/wizard` (ConfigurationWizardPage) | **DONE** ✓ | 4-step wizard replaces 5 JSP steps |
| 14 | CompanyCredentials.jsp | (servlet forward) | `/company/config/wizard` (step 3) | **DONE** ✓ | Credentials in wizard step 3 |
| 15 | BDConfigurator.jsp | `/BDConfigurator.jsp` | `/admin/configurator` (IntegrationOverviewPage) | **DONE** ✓ | 3 tabs: Flows, Credentials, Engine Controls |
| 16 | BDConfiguratorA.jsp | `/BDConfiguratorA.jsp` | `/admin/configurator` (Engine Controls tab) | **DONE** ✓ | Start/stop, schedule editing via Dialog |
| 17 | BDConfiguratorB.jsp | `/BDConfiguratorB.jsp` | -- | LOW | Advanced config (rarely used) |
| 18 | FlowProperties.jsp | `/FlowProperties.jsp` | `/admin/configurator` (EditScheduleDialog) | **DONE** ✓ | Dialog with interval/shift/counter editing |
| 19 | monitoring/Dashboard.jsp | `/monitoring/Dashboard.jsp` | `/monitoring` (MonitoringPage) | **DONE** ✓ | Recharts: area, bar, area + 4 KPI cards |
| 20 | monitoring/AlertConfig.jsp | `/monitoring/AlertConfig.jsp` | `/monitoring/alerts` (AlertConfigPage) | **DONE** ✓ | Switch toggle, Tooltip, severity badges |
| 21 | monitoring/TransactionDetail.jsp | `/monitoring/TransactionDetail.jsp` | `/monitoring/transactions` (TransactionHistoryPage) | **DONE** ✓ | Paginated, filterable, status icons |
| 22 | IMConfig.jsp | `/IMConfig.jsp` | -- | N/A | Frameset wrapper (obsolete) |
| 23 | ViewLog.jsp | `/ViewLog.jsp` | `/admin/logging` (LoggingPage) | **DONE** ✓ | Log targets, system status, classic link |
| 24 | Logging.jsp | `/Logging.jsp` | `/admin/logging` (LoggingPage) | **DONE** ✓ | Merged with ViewLog into single page |
| 25 | MoreCustomMappings.jsp | `/MoreCustomMappings.jsp` | -- | LOW | Custom field mapping editor (future) |
| 26 | ErrorMessage.jsp | (forward target) | ToastProvider + ErrorBoundary | **DONE** ✓ | Toast system + error boundary replace this |
| 27 | BDMinitor.jsp | `/BDMinitor.jsp` | -- | N/A | Java Applet (dead technology) |
| 28 | AssignLead.jsp | `/AssignLead.jsp` | -- | N/A | Dead prototype |
| 29 | db_test.jsp | `/db_test.jsp` | -- | N/A | Dev diagnostic |

**Summary: 22 DONE, 1 LOW priority, 4 N/A — ~85% functional coverage of classic JSP pages**

---

## 2. User Flow Analysis: Classic Portal

### Flow A: New Company Onboarding (most important business workflow)

```
IWLogin.jsp → "Register Company" link
  → CompanyRegistration.jsp (company name, admin email, password, SolutionType dropdown [50+ options])
    → CompanyRegistrationServlet (creates company + admin user in DB)
      → CompanyConfiguration.jsp (Step 1: select sync types per object — 20+ dropdowns)
        → CompanyConfigurationServletOS (builds SF2QBConfiguration XML in memory)
          → CompanyConfigurationDetail.jsp (Step 2: field-level sync options — 80-120 fields)
            → CompanyConfigurationSetvletDT
              → CompanyConfigurationDetailT.jsp (Step 3: transaction mapping)
                → CompanyConfigurationServletDTT
                  → CompanyConfigurationDetailT1.jsp (Step 4)
                    → CompanyConfigurationServletDTT1
                      → CompanyConfigurationDetailT2.jsp (Step 5)
                        → CompanyConfigurationServletDTT2
                          → CompanyCredentials.jsp (Final: CRM + FS credentials, email config, scheduling)
                            → CompanyCredentialsServlet
                              → DB upsert (company_configurations)
                              → WorkspaceProfileCompiler.compileProfile()
                              → Workspace files written (6-8 files)
                              → Redirect to BDConfigurator.jsp
```

**This 6-page wizard is the CORE business workflow.** It configures the entire integration: which objects sync, in which direction, with what credentials, on what schedule. The React UI has ZERO implementation of this.

### Flow B: Login → Integration Manager

```
IWLogin.jsp (email + password)
  → LocalLoginServlet
    → Sets session + ConfigContext + bindHostedProfile()
    → Loads saved config from company_configurations
    → WorkspaceProfileCompiler.compileProfile() (workspace sync)
    → Redirect to IMConfig.jsp (frameset)
      → BDConfigurator.jsp (main frame)
        → Shows all transaction flows + queries from ConfigContext
        → Per-flow: START/STOP, Scheduled/Single, Interval, Shift, Query Start
        → Flow ID links → FlowProperties.jsp (bottom frame)
        → "Monitoring" link → monitoring/Dashboard.jsp
```

**The Integration Manager is the operational control center.** Users START/STOP flows, configure schedules, and monitor execution. React has no equivalent.

### Flow C: Edit Existing Configuration

```
IWLogin.jsp → "Edit Company Profile" link
  → EditCompanyProfile.jsp (re-authenticate with company name + admin email + password)
    → EditCompanyProfileServlet (loads profile into ConfigContext)
      → EditCompanyProfile.jsp (shows admin name fields)
        → SaveCompanyProfileServlet (saves admin fields)
          → CompanyConfiguration.jsp (wizard with pre-populated values)
            → (same 6-page wizard flow as above, but editing existing config)
```

### Flow D: User Self-Registration

```
IWLogin.jsp → "Register User" link
  → Registration.jsp (company name, email, name, password, title, auth token)
    → RegistrationServlet (creates user linked to existing company)
      → IWLogin.jsp (success message)
```

### Flow E: Monitoring

```
BDConfigurator.jsp → "Monitoring Dashboard" link
  → monitoring/Dashboard.jsp (AJAX-driven, Chart.js charts, filterable transaction table)
    → Transaction row click → monitoring/TransactionDetail.jsp (execution detail + payloads)
    → "Alert Configuration" → monitoring/AlertConfig.jsp (alert rules + webhooks CRUD)
```

---

## 3. What the React UI Currently Implements

### Fully Functional (5 routes)
| Route | What It Does | API Endpoints |
|---|---|---|
| `/login` | Email/password login form | `POST /api/auth/login` |
| `/dashboard` | KPI cards (4), recent transactions table (10 rows), quick action links | `GET /api/monitoring/dashboard`, `GET /api/monitoring/transactions` |
| `/profile` | Personal info form (firstName, lastName, title) + password change | `GET/PUT/POST /api/profile` |
| `/company` | Company admin form + company password change (admin-gated) | `GET/PUT/POST /api/company/profile` |
| `/*` | 404 Not Found page | -- |

### Partially Functional (1 route)
| Route | What Works | What's Missing |
|---|---|---|
| `/monitoring` | 3 summary stat cards, tab bar | No charts (recharts installed but unused), no transaction table, no filtering, sub-routes are stubs |

### Classic Redirects (3 routes)
| Route | Redirects To |
|---|---|
| `/company/config` | `CompanyConfiguration.jsp` |
| `/admin/configurator` | `BDConfigurator.jsp` |
| `/admin/logging` | `Logging.jsp` |

### Cosmetic/Non-Functional Elements
- **Topbar search**: Input renders but no handler, no search logic
- **Sidebar "Environment: Local Dev"**: Hardcoded, not dynamic
- **"Always classic" preference**: `CLASSIC_MODE_KEY` in localStorage — no UI to set it
- **Unused dependencies**: `recharts`, `react-hook-form`, `zod`, `@hookform/resolvers` all installed but not used
- **Unused types**: `AlertRule` defined but no page uses it
- **Unused utilities**: `formatDate`, `formatNumber` in utils.ts

---

## 4. Critical Gaps

### Gap 1: Registration Pages (P1) — ✅ COMPLETED
**Classic**: `Registration.jsp` (user) + `CompanyRegistration.jsp` (company)
**React**: Nothing. No `/register` or `/register-company` routes exist.
**Impact**: New users and companies cannot onboard through the React UI.
**Backend needed**: New `ApiRegistrationServlet` and `ApiCompanyRegistrationServlet` (JSON endpoints)
**Key complexity**: CompanyRegistration has 50+ SolutionType options filtered by PortalSolutions

### Gap 2: Company Configuration Wizard (P1)
**Classic**: 6-page wizard (CompanyConfiguration → Detail → DetailT → DetailT1 → DetailT2 → Credentials)
**React**: Nothing. `/company/config` redirects to JSP.
**Impact**: The CORE business workflow — configuring what syncs, how, and with what credentials — is completely absent from React.
**Backend needed**: New `ApiCompanyConfigurationServlet` (multi-step wizard state management, JSON)
**Key complexity**:
- 20+ sync-type dropdowns on step 1, conditional on SolutionType
- 80-120 field-level mapping options on steps 2-5, conditional on sync types
- CRM + FS credential collection on final step
- Must trigger `WorkspaceProfileCompiler.compileProfile()` on save
- Must write to `company_configurations` table
- XML accumulation across multiple form submissions

### Gap 3: Integration Manager / BDConfigurator (P1)
**Classic**: `BDConfigurator.jsp` — shows all transaction flows + queries, START/STOP controls, scheduling
**React**: Nothing. `/admin/configurator` redirects to JSP.
**Impact**: Users cannot start/stop integration flows or configure scheduling through the React UI.
**Backend needed**: New `ApiIntegrationManagerServlet` (JSON wrapper around ConfigContext + ProductDemoServlet logic)
**Key complexity**:
- `ProductDemoServlet` is compiled-only (no source) — must reverse-engineer or wrap
- ConfigContext is a JVM singleton with per-flow state
- Per-flow fields: start/stop toggle, scheduled/single mode, interval, shift, query start time, transaction counter
- Admin variant adds: log level, kill flag, DB config

### Gap 4: Flow Properties Editor (P1)
**Classic**: `FlowProperties.jsp` — editable key/value pairs per flow, admin TS URL config
**React**: Nothing
**Backend needed**: New `ApiFlowPropertiesServlet` (JSON read/write wrapper for flow parameters)
**Key complexity**: `FlowProperiesServlet` is compiled-only — must wrap or replace

### Gap 5: Monitoring Completion (P1) — ✅ COMPLETED
**Classic**: Dashboard.jsp has Chart.js charts + filterable/paginated transaction table + transaction detail + alert config
**React**: MonitoringPage has summary cards only. No charts, no table, no detail, no alerts.
**Backend**: APIs already exist (`/api/monitoring/*`) — just need React pages
**What's needed**:
- Charts using recharts (already installed) consuming `/api/monitoring/metrics`
- Transaction history table with filters consuming `/api/monitoring/transactions`
- Transaction detail page consuming `/api/monitoring/transactions/{id}`
- Alert configuration page consuming `/api/monitoring/alerts/*` and `/api/monitoring/webhooks/*`

### Gap 6: Login Page Redesign (P0) — ✅ COMPLETED
**User request**: The login page needs a redesign when users click the classic banner link
**Current state**: Two-panel layout with branding left, form right
**ASSA login**: Two-panel (marketing left, form right) with MFA code field and "Forgot password" link
**InterWoven login**: Full auth flow with sign-up, SSO, password reset, demo credentials
**What's needed**: Redesign the login page as the entry point for the new UI experience

---

## 5. IDE Sync Requirements for React UI

The new React UI must maintain the same IDE sync behavior as the classic JSPs. This is critical for the platform to work end-to-end.

### What Already Works (via shared Tomcat session)
- `ApiLoginServlet` calls `bindHostedProfile()` — same as `LocalLoginServlet` ✓
- `ApiLoginServlet` calls `WorkspaceProfileCompiler.compileProfile()` on login ✓
- Session attributes are identical between JSP and React login paths ✓
- A user logged in via React can open JSP pages (same JSESSIONID) ✓

### What New React Pages MUST Do

**Registration pages must**:
- Create users/companies in DB (same as classic servlets) ✓ straightforward
- NOT need to touch workspace (registration doesn't trigger sync)

**Configuration wizard must**:
- Build `SF2QBConfiguration` XML from form inputs (same accumulation pattern)
- Store in `company_configurations` table via `INSERT ON CONFLICT UPDATE`
- Call `WorkspaceProfileCompiler.compileProfile()` after final save
- This writes 6-8 files to workspace/ — immediately visible to running IDE

**Integration Manager must**:
- Read from `ConfigContext.getTransactionList()` and `getQueryList()`
- Submit START/STOP/schedule changes to `ProductDemoServlet` (or a new JSON wrapper)
- Read from `ConfigContext.getProfileDescriptors()` for auto-refresh status

### Sync Trigger Summary

| User Action | What Must Happen | Current Status |
|---|---|---|
| Login (React) | `bindHostedProfile()` + compile | DONE (ApiLoginServlet) |
| Login (JSP) | Same | DONE (LocalLoginServlet) |
| Wizard "Save and Finish" | DB upsert + compile | MISSING (no React wizard) |
| Start/Stop flow | ProductDemoServlet call | MISSING (no React IM) |
| Edit flow properties | FlowProperiesServlet call | MISSING (no React props page) |
| Tomcat startup | exportAll + compileAll | DONE (START.bat) |

---

## 6. ASSA Design Additions

Features from the ASSA prototypes that should enhance (not replace) the classic functionality:

### Relevant to iw-portal (integration platform context)

| ASSA Feature | Applies To | Priority |
|---|---|---|
| Sparkline trend charts on KPI cards | Dashboard | Medium |
| Live-filterable operational queue table | Dashboard / Monitoring | Medium |
| MFA code field on login | LoginPage redesign | Low (auth dependent) |
| "Forgot password" link on login | LoginPage redesign | Medium |
| Notification inbox | New route `/notifications` | Low |
| Support ticket creation | New route `/support` | Low |
| RBAC role management (5 roles + MFA tiers) | Admin section | Low |
| Audit log with domain categorization | Admin section | Low |
| Analytics (engagement funnel, content perf) | Future | Low |
| Subscription/billing management | Future (SaaS features) | Low |

### ASSA Design Language (already adopted)
- Dark navy background with aurora radial gradients ✓ (in index.css)
- Glass panels with `backdrop-filter: blur` ✓ (`.glass-panel` class)
- 260px sticky sidebar ✓ (Sidebar component)
- System font stack ✓
- `--brand: #3b82f6` blue accent ✓
- Status pills with colored dots ✓
- 24px border-radius cards ✓

---

## 7. InterWoven Feature Additions

Features from the InterWoven prototype ranked by relevance to the integration platform:

### High Priority (directly extends classic functionality)

| Feature | What It Does | Why It Matters |
|---|---|---|
| **AI-Assisted Field Mapping** | LLM-suggested field mappings with confidence scores + XSLT generation | Directly maps to the IW XSLT transformation engine. Could replace the manual 80-120 field wizard pages with intelligent suggestions |
| **Visual Workflow Builder** | Canvas-based drag-and-drop node editor for transaction flows | Replaces Eclipse IDE's XML-only workflow editing for non-developer users |
| **Per-Connection Workspace** | Multi-tab view per connection (overview, mappings, transformations, test) | Modernizes the flat BDConfigurator + FlowProperties pattern |
| **OAuth 2.0 Token Broker** | Automated OAuth flow for SF, HubSpot, Pipedrive, Dynamics, QB | Currently all OAuth done in Eclipse IDE manually |
| **Live CRM Schema Detection** | Call live CRM APIs to discover available objects and fields | Eliminates manual XML schema writing for connection setup |
| **6-Step Integration Wizard** | Guided onboarding: Welcome → Connections → Credentials → Objects → Config → Test | Directly modernizes the classic 6-page JSP wizard |

### Medium Priority

| Feature | What It Does |
|---|---|
| Streaming AI Assistant panel | Context-aware chat for integration help |
| Webhook Manager | Full webhook CRUD with test delivery |
| Guided Tour system | Step-by-step overlay for feature discovery |
| Integration Health Scoring | Composite reliability/performance scores |
| Workflow Template Library | Pre-built integration templates |

### Low Priority (infrastructure-level)

| Feature | Notes |
|---|---|
| Multi-tenant org model with RBAC | Current system uses company-level tenancy |
| MFA settings management | Dependent on auth provider |
| Platform AI configuration | Model selection, confidence thresholds |
| Activity audit log | Partially covered by monitoring |

---

## 8. Prioritized Implementation Roadmap

### Phase 1: Make the React UI Functional (Classic Parity)

**P0 — Login Redesign** (prerequisite: first thing users see)
- Redesign LoginPage.tsx as the new UI entry point
- Add "Create Account" and "Register Company" links
- Add "Forgot Password" flow
- Update the classic JSP banner link to point to `/iw-portal/login`

**P1-A — Registration Pages** (unblocks onboarding)
- New: `RegisterPage.tsx` (user self-registration)
- New: `CompanyRegisterPage.tsx` (company + admin registration with SolutionType)
- New: `ApiRegistrationServlet.java` (JSON endpoint)
- New: `ApiCompanyRegistrationServlet.java` (JSON endpoint)

**P1-B — Monitoring Completion** (backend APIs already exist)
- Finish MonitoringPage: add recharts line/bar charts from `/api/monitoring/metrics`
- New: `TransactionHistoryPage.tsx` with filterable/sortable/paginated table
- New: `TransactionDetailPage.tsx` consuming `/api/monitoring/transactions/{id}`
- New: `AlertConfigPage.tsx` with alert rules + webhooks CRUD

**P1-C — Company Configuration Wizard** (core business workflow)
- New multi-step wizard component (React Hook Form + Zod validation)
- Step 1: Sync type selection (20+ conditional dropdowns based on SolutionType)
- Steps 2-5: Field mapping options (conditional sections)
- Final step: Credentials collection (CRM + FS)
- New: `ApiCompanyConfigurationServlet.java` (stateful wizard, JSON)
- Must call `WorkspaceProfileCompiler.compileProfile()` on save

**P1-D — Integration Manager** (operational control)
- New: `IntegrationManagerPage.tsx` (flow list, START/STOP, scheduling)
- New: `FlowPropertiesPanel.tsx` (sidebar or modal for per-flow config)
- New: `ApiIntegrationManagerServlet.java` (JSON wrapper for ConfigContext + flow control)
- New: `ApiFlowPropertiesServlet.java` (JSON read/write for flow params)

### Phase 2: Enhanced Experience (ASSA + InterWoven additions)

- Recharts sparklines on dashboard KPI cards
- Notification inbox
- Better error handling with toast system (replace ErrorMessage.jsp pattern)
- Topbar search functionality
- Dynamic environment indicator (replace hardcoded "Local Dev")
- "Always classic" preference toggle in settings

### Phase 3: Next-Gen Features (InterWoven-inspired)

- AI-assisted field mapping suggestions in wizard
- Visual workflow builder (canvas node editor)
- Per-connection multi-tab workspace
- OAuth 2.0 connection wizard
- Live CRM schema detection
- Guided tour for new users
- Streaming AI assistant panel

---

## 9. New API Servlets Needed

| Servlet | URL Pattern | Methods | Purpose |
|---|---|---|---|
| `ApiRegistrationServlet` | `/api/register` | POST | User self-registration (JSON) |
| `ApiCompanyRegistrationServlet` | `/api/register/company` | POST | Company + admin registration (JSON) |
| `ApiCompanyConfigurationServlet` | `/api/company/configuration` | GET, PUT, POST | Wizard state management (JSON) |
| `ApiIntegrationManagerServlet` | `/api/integrations` | GET | List flows + queries + status |
| | `/api/integrations/{id}/start` | POST | Start a flow |
| | `/api/integrations/{id}/stop` | POST | Stop a flow |
| | `/api/integrations/{id}/schedule` | PUT | Update scheduling params |
| `ApiFlowPropertiesServlet` | `/api/integrations/{id}/properties` | GET, PUT | Read/write flow property key-values |
| `ApiSolutionTypesServlet` | `/api/solution-types` | GET | List available solution types (for registration dropdown) |

All servlets must:
- Return JSON (not JSP redirects)
- Use the shared Tomcat session (same as existing API servlets)
- Set CORS header for dev mode (`http://localhost:5173`)
- Authenticate via session check (except registration endpoints)
- Call `WorkspaceProfileCompiler.compileProfile()` where applicable

---

## 10. Login Page Redesign Notes

The user requested that when clicking the classic banner "Switch to Modern Portal" link, users should land on a redesigned login page that serves as the entry point to the new platform experience.

### Current LoginPage Issues
- Two-panel split is functional but feels like a clone of the classic layout
- No registration links (user or company)
- No "forgot password" flow
- Demo credentials shown as a banner (should be more subtle or removable)
- Email field uses `type="text"` instead of `type="email"`

### Redesign Requirements
1. **Entry point feel**: This is the gateway to the "Integration Platform" — should communicate the product value
2. **Navigation**: Links to Register (user), Register Company, Forgot Password
3. **Post-login destination**: Dashboard (current behavior — keep this)
4. **ASSA design language**: Dark theme with aurora background, glass panels
5. **InterWoven inspiration**: Feature highlights, possibly animated, clean modern auth form
6. **Classic fallback**: Keep "Prefer the classic login?" link

### Post-Login Flow Change
Currently: Login → Dashboard
Should become: Login → Dashboard (with onboarding state check)
- If company has no saved configuration → show "Complete Setup" CTA on dashboard
- If company has configuration → show normal dashboard with flow status

---

## Appendix A: Classic JSP Form Field Reference

### Registration.jsp Fields
| Field | Name | Type | Max | Required |
|---|---|---|---|---|
| Company | `CompanyOrganization` | text | 255 | Yes |
| Email | `Email` | text | 255 | Yes |
| First Name | `FirstName` | text | 45 | Yes |
| Last Name | `LastName` | text | 45 | Yes |
| Title | `Title` | text | 255 | No |
| Password | `Password` | password | 255 | Yes |
| Confirm | `ConfirmPassword` | password | 255 | Yes |
| Auth Token | `Token` | password | 255 | Yes |

### CompanyRegistration.jsp Fields
| Field | Name | Type | Max | Required |
|---|---|---|---|---|
| Company | `CompanyOrganization` | text | 255 | Yes |
| Email | `Email` | text | 255 | Yes |
| First Name | `FirstName` | text | 45 | Yes |
| Last Name | `LastName` | text | 45 | Yes |
| Password | `Password` | password | 255 | Yes |
| Confirm | `ConfirmPassword` | password | 255 | Yes |
| Solution Type | `SolutionType` | select | -- | Yes (50+ options) |
| Clone Company | `CompanyOrganizationClone` | text | 255 | No |
| Clone Email | `EmailClone` | text | 255 | No |
| Clone Password | `PasswordClone` | password | 255 | No |
| Auth Token | `IWAuthToken` | password | 255 | Yes |

### CompanyConfiguration.jsp Sync Type Dropdowns (Step 1)
Each dropdown has options: `None`, `SF2QB` (CRM→FS), `QB2SF` (FS→CRM), `SFQB` (Bi-directional)

| Field | Object Mapping | Shown When |
|---|---|---|
| `SyncTypeAC` | Account/Contact → Customer | Always |
| `SyncTypePAC` | Person Account → Customer/Job | Tier 2+ |
| `SyncTypeVAC` | Account → Vendor | Tier 2+ |
| `SyncTypeOJ` | Transaction → Job | Tier 2+ |
| `SyncTypeSO` | Transaction → Sales Order | Always |
| `SyncTypePO` | Transaction → Purchase Order | Tier 2+ |
| `SyncTypeInv` | Transaction → Invoice | Always |
| `SyncTypeSR` | Transaction → Sales Receipt | Always |
| `SyncTypeEst` | Transaction → Estimate | Tier 2+ |
| `SyncTypeBill` | Transaction → Bill | Tier 2+ |
| `SyncTypeVC` | Object → Vendor Credit | Tier 3 |
| `SyncTypeDep` | Object → Deposit | Tier 3 |
| `SyncTypePR` | Object → Payment Received | Tier 3 |
| `SyncTypeCM` | Transaction → Credit Memo | Tier 3 |
| `SyncTypeCheck` | Transaction → Check | Tier 3 |
| `SyncTypeCCC` | Object → Credit Card Charge | Tier 3 |
| `SyncTypeBP` | Object → Bill Payment | Tier 3 |
| `SyncTypeCOA` | Object → Account (COA) | Enterprise |
| `SyncTypeJE` | Object → Journal Entry | Tier 3 |
| `SyncTypeTT` | Object → Time Tracking | Tier 3 |
| `SyncTypeSC` | Object → Statement Charges | Tier 3 |
| `SyncTypePrd` | Product → Item | Always |

### CompanyCredentials.jsp Key Fields (Final Step)
| Section | Field | Name | Type |
|---|---|---|---|
| CRM | Integration URI | `AccIntURL` | text |
| CRM | Integration User | `SFIntUsr` | text |
| CRM | Integration Password | `SFPswd` | password |
| CRM | Sandbox Mode | `SandBoxUsed` | select (Yes/No) |
| FS | Company File Path/URI | `QDSN{i}` | text |
| FS | Integration User | `QBIntUsr{i}` | text |
| FS | Integration Password | `QBPswd{i}` | password |
| Config | FS Version/Locale | `QBVersion` | select (USA/UK/CAN/AUS/NZ) |
| Config | FS Location | `QBLocation` | select (Hosted/In-House/Online) |
| Config | Environment | `Env2Con` | select (Production A-E/Dedicated) |
| Config | Email Notification | `EmlNtf` | select (8 modes) |
| Config | Stop on Error | `StopSchedTr` | select (Never/Connection/Every) |
| Config | Sleep Window | `SleepStart` / `SleepEnd` | text |
| Config | Time Zone | `TimeZone` | text |

---

## Appendix B: Session Attribute Sharing

Both login paths (JSP `LocalLoginServlet` and React `ApiLoginServlet`) set identical session attributes:

| Attribute | Type | Set At Login | Read By |
|---|---|---|---|
| `authenticated` | Boolean | Yes | All API servlets, monitoring JSPs |
| `userId` | Integer | Yes | Profile/company servlets |
| `userEmail` | String | Yes | Profile lookup key |
| `userName` | String | Yes | Topbar display, monitoring |
| `companyId` | Integer | Yes | Company profile, monitoring |
| `companyName` | String | Yes | Profile key construction |
| `isAdmin` | Boolean | Yes | Company edit gating |
| `role` | String | Yes | UI role display |
| `solutionType` | String | Yes | Wizard routing |

This means React and JSP pages are fully interchangeable within the same browser session. A user can log in via React, then open a JSP page in a new tab, and vice versa.
