# InterWeave IW-Portal: Backend-Aware Incremental UI/UX Implementation Plan

**Prepared:** March 9, 2026
**Prerequisite:** Read alongside `UI_UX_ANALYSIS.md` for full competitive context
**Principle:** Every change must keep the platform fully functional. UI-only changes first, then layers that touch API contracts, then new backend work.

---

## Architecture Reference — How It All Connects

Before touching anything, here's the full data flow map showing what depends on what:

```
┌─────────────────────────────────────────────────────────────────┐
│  BROWSER                                                         │
│  ┌──────────────────────────┐   ┌────────────────────────────┐  │
│  │  React iw-portal (SPA)   │   │  Legacy JSP Pages          │  │
│  │  Vite 7 + React 19 + TS  │   │  EditProfile.jsp, etc.     │  │
│  │  Port :5173 (dev)         │   │  Served by Tomcat :9090    │  │
│  │  /iw-portal/* (prod)      │   │  /iw-business-daemon/*     │  │
│  └───────────┬──────────────┘   └──────────┬─────────────────┘  │
│              │ Shared Tomcat JSESSIONID cookie                    │
│              │ credentials: "include"                             │
│              ▼                              ▼                     │
│  ┌───────────────────────────────────────────────────────────┐   │
│  │              Tomcat :9090 (same origin in prod)            │   │
│  │  ┌─────────────────────┐   ┌──────────────────────────┐  │   │
│  │  │ JSON API Servlets   │   │ Legacy Config Servlets   │  │   │
│  │  │ /api/auth/*         │   │ LoginServlet             │  │   │
│  │  │ /api/profile        │   │ FlowProperiesServlet     │  │   │
│  │  │ /api/company/*      │   │ CompanyConfig* servlets   │  │   │
│  │  │ /api/config/*       │   │ EditProfile/Save* servlets│  │   │
│  │  │ /api/flows/*        │   │ DynamicConfigurator       │  │   │
│  │  │ /api/monitoring/*   │   │ BusinessDaemonInit        │  │   │
│  │  │ /api/notifications/*│   │ (uses ConfigContext)      │  │   │
│  │  │ /api/admin/audit/*  │   └──────────────────────────┘  │   │
│  │  │ /api/logs/*         │                                  │   │
│  │  │ /api/auth/mfa/*     │   ┌──────────────────────────┐  │   │
│  │  └─────────┬───────────┘   │ iwtransformationserver   │  │   │
│  │            │               │ SKELETON ONLY (no JARs)  │  │   │
│  │            ▼               │ /transform → 404         │  │   │
│  │  ┌─────────────────────┐   └──────────────────────────┘  │   │
│  │  │ Shared State Layer  │                                  │   │
│  │  │ HttpSession attrs   │                                  │   │
│  │  │ TransactionThread   │                                  │   │
│  │  │ ConfigContext        │                                  │   │
│  │  └─────────┬───────────┘                                  │   │
│  └────────────┼──────────────────────────────────────────────┘   │
│               ▼                                                   │
│  ┌───────────────────────────────────────────────────────────┐   │
│  │  Supabase Postgres (pooler :6543)                         │   │
│  │  14 tables with RLS: companies, users, user_profiles,     │   │
│  │  monitoring_*, mfa_*, notifications, audit_log             │   │
│  └───────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Critical Session Sharing Rules

The React app and JSP pages share a single Tomcat session via JSESSIONID cookie:

- `apiFetch()` in `lib/api.ts` uses `credentials: "include"` — always sends the cookie
- `AuthProvider` checks `GET /api/auth/session` on mount — reads the same session attributes set by `LocalLoginServlet`
- `logout()` hits `/iw-business-daemon/LogoutServlet` — invalidates the shared session
- `useSaveFlowProperties()` POSTs to the **legacy** `FlowProperiesServlet` (not the JSON API) using `application/x-www-form-urlencoded` — this is because the compiled flow properties servlet has no JSON API equivalent

**Rule:** Any new feature that touches authentication or session state MUST work with both the React and JSP codepaths. Never create React-only session attributes.

### API Contract Inventory

These are the **existing** API endpoints the frontend depends on. None of these can break:

| Endpoint | Method | Hook | What It Does |
|----------|--------|------|-------------|
| `/api/auth/login` | POST | `AuthProvider.login()` | JSON login, sets session |
| `/api/auth/session` | GET | `AuthProvider.checkSession()` | Session validation |
| `/api/auth/change-password` | POST | `useChangePassword()` | Password update |
| `/api/auth/mfa/*` | GET/POST | `useMfa()` | TOTP setup/verify |
| `/api/auth/password-reset` | POST | `ForgotPasswordPage` | Reset token generation |
| `/api/profile` | GET/PUT/POST | `useProfile()`, `useUpdateProfile()` | Profile CRUD + password |
| `/api/company/profile` | GET/PUT | `useCompanyProfile()` | Company profile CRUD |
| `/api/register` | POST | `RegisterPage` | User registration |
| `/api/register/company` | POST | `CompanyRegisterPage` | Company registration |
| `/api/register/solution-types` | GET | `CompanyRegisterPage` | Solution type list |
| `/api/config/wizard` | GET/PUT | `useWizardConfig()` | Config wizard state |
| `/api/config/credentials` | GET/PUT | `useCredentials()` | Credential management |
| `/api/config/credentials/test` | POST | `useTestCredential()` | Connection test |
| `/api/config/profiles` | GET | `useProfiles()` | Profile listing |
| `/api/flows` | GET | `useEngineFlows()` | Flow listing (10s refresh) |
| `/api/flows/start` | POST | `useStartFlow()` | Start a flow |
| `/api/flows/stop` | POST | `useStopFlow()` | Stop a flow |
| `/api/flows/schedule` | PUT | `useUpdateFlowSchedule()` | Update schedule |
| `/api/flows/submit` | POST | `useSubmitFlows()` | Submit flow changes |
| `/api/flows/properties` | GET | `useFlowProperties()` | Flow variable params |
| `/api/monitoring/dashboard` | GET | `useDashboard()` | Dashboard KPIs (30s refresh) |
| `/api/monitoring/transactions` | GET | `useTransactions()` | Transaction list (paginated) |
| `/api/monitoring/metrics` | GET | `useMetrics()` | Chart data (Chart.js format) |
| `/api/monitoring/alerts/*` | GET | `useAlertRules()` | Alert rule listing |
| `/api/notifications/*` | GET/PUT/DELETE | `useNotifications()` | Notification CRUD |
| `/api/admin/audit/*` | GET | `useAuditLog()` | Audit events |
| `/api/logs/*` | GET | `useLogs()` | Log file viewer |
| `/FlowProperiesServlet` | POST (form) | `useSaveFlowProperties()` | Legacy flow props save |
| `/LogoutServlet` | GET | `AuthProvider.logout()` | Session invalidation |

**Legacy servlet calls from React:** The `useSaveFlowProperties()` hook POSTs to the legacy `FlowProperiesServlet` using URL-encoded form data (not JSON). This is the one place where the React frontend talks directly to a non-API servlet. Any refactoring of the flow properties system must preserve this endpoint.

### Build & Deploy Pipeline

```
Dev mode:   Vite :5173 → proxy /iw-business-daemon → Tomcat :9090
Prod build: npm run build → outputs to web_portal/tomcat/webapps/iw-portal/
            Tomcat serves /iw-portal/* as static files
            API calls go to /iw-business-daemon/api/* on same origin
```

**Verification after any change:**
```bash
cd frontends/iw-portal && npx tsc --noEmit    # TypeScript must pass
cd frontends/iw-portal && npm run build         # Build must succeed
# Then test login + dashboard + monitoring in browser
```

---

## Phase 0: Pre-Flight Checklist (Before Any UI Work)

Before making any changes, ensure the current system is stable:

- [ ] `npx tsc --noEmit` passes with zero errors
- [ ] `npm run build` succeeds, output goes to `web_portal/tomcat/webapps/iw-portal/`
- [ ] Tomcat is running, login works at `http://localhost:9090/iw-portal/login`
- [ ] Dashboard loads with KPI data (or graceful empty state if no transactions)
- [ ] Classic View links work on every page
- [ ] Dark/light/system theme toggle works

---

## Phase 1: Pure Frontend Polish (No Backend Changes)

These changes touch ONLY React/CSS code. Zero API changes, zero servlet modifications, zero database changes. The backend doesn't even know these happened.

### 1.1 Skeleton Loading States
**Files touched:** Component files only (new + existing pages)
**Backend impact:** None — replaces `<Loader2>` spinners with skeleton placeholders
**Risk:** Zero — purely visual

Create a `SkeletonCard.tsx` and `SkeletonTable.tsx` in `src/components/ui/`:

```tsx
// Shimmer rectangles matching the glass-panel card dimensions
// Used in DashboardPage, MonitoringPage, IntegrationOverviewPage
// Simply replace <Loader2> blocks with skeleton variants
```

**Where to apply:**
- `DashboardPage.tsx` — replace the `isLoading` Loader2 spinner in KPI cards with 4 skeleton cards
- `MonitoringPage.tsx` — skeleton chart area while data loads
- Transaction tables — skeleton rows instead of blank table

**Verification:** All data still loads correctly after skeleton disappears. No API calls changed.

### 1.2 Branded Typography
**Files touched:** `index.css` only (+ Google Fonts link in `index.html`)
**Backend impact:** None
**Risk:** Zero — font swap only

Add a display font for headings. The system font stack stays for body text:

```css
/* In index.html <head>: */
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@600;700;800&display=swap" rel="stylesheet">

/* In index.css: */
h1, h2, h3, .font-display {
  font-family: 'Plus Jakarta Sans', ui-sans-serif, system-ui, sans-serif;
}
```

**Verification:** All headings render in new font. Body text unchanged. No layout shifts.

### 1.3 Command Palette (Cmd+K)
**Files touched:** New `CommandPalette.tsx` component + `AppShell.tsx` (add component)
**Backend impact:** None — uses the same `SEARCH_ITEMS` array already in `Topbar.tsx`
**Risk:** Zero — keyboard shortcut overlay only

This is an enhancement of the existing search. The `Topbar.tsx` already has the `SEARCH_ITEMS` array and keyboard navigation logic. The command palette is a centered modal overlay triggered by Cmd+K, reusing the same data.

**Key safety:** The existing `/` shortcut stays. Cmd+K opens the overlay. Both navigate via `react-router-dom`. No API calls.

### 1.4 Breadcrumb Navigation
**Files touched:** New `Breadcrumbs.tsx` component + `AppShell.tsx` (add below Topbar)
**Backend impact:** None — derives breadcrumbs from `useLocation()` path segments
**Risk:** Zero — additive layout element

```tsx
// /company/config/wizard → Company > Configuration > Wizard
// Uses react-router-dom's useLocation() — no backend data needed
```

### 1.5 Dismissable Classic View Banner
**Files touched:** `ClassicViewBanner.tsx` only
**Backend impact:** None — the banner already uses `localStorage` for preference
**Risk:** Zero — already has dismiss button, just needs persistence

The banner already has a dismiss `✕` button that sets `dismissed` state, but it resets on page navigation. Persist it to `sessionStorage` so it stays dismissed for the session.

### 1.6 Empty State Components
**Files touched:** New `EmptyState.tsx` + modify pages that show empty tables
**Backend impact:** None — just a better visual for "no data" states
**Risk:** Zero

Replace the plain text "No transaction data yet" in `DashboardPage.tsx` and similar strings in other pages with a proper empty state component (icon + heading + description + optional CTA button).

**Verification for entire Phase 1:**
```bash
cd frontends/iw-portal
npx tsc --noEmit        # Still zero errors
npm run build            # Still builds
# Manual test: login, navigate all pages, check dark/light themes
# All API data still loads correctly — no endpoints were changed
```

---

## Phase 2: Frontend + Existing API Enhancements (No New Servlets)

These changes add new React features that use **existing** API endpoints in new ways. No new Java code, no new servlet mappings, no database schema changes.

### 2.1 Dashboard Setup Checklist
**Files touched:** New `SetupChecklist.tsx` + `DashboardPage.tsx`
**Backend impact:** None — reads from EXISTING endpoints to determine completion status
**Risk:** Low — additive card on dashboard, no mutations

This component calls existing hooks to determine setup progress:
```
Step 1: Profile complete     → useProfile() — check if firstName/lastName filled
Step 2: Company configured   → useCompanyProfile() — check if companyName exists
Step 3: Credentials set      → useCredentials() — check if any credentials saved
Step 4: Flows configured     → useEngineFlows() — check if any flows exist
Step 5: First sync complete  → useDashboard() — check if total_count_24h > 0
```

Every one of these hooks already exists and calls existing API endpoints. The checklist is purely a derived view of existing data.

**Placement:** Top of `DashboardPage.tsx`, conditionally shown when not all steps are complete. Users can dismiss it.

### 2.2 Dashboard Date Range Context
**Files touched:** `DashboardPage.tsx` + `useMonitoring.ts`
**Backend impact:** None — the `useMetrics()` hook ALREADY accepts `period` parameter ("24h", "7d", "30d", "90d")
**Risk:** Low — adds a dropdown that changes the existing query parameter

The `MetricsApiServlet` already supports the `period` query parameter. We just need to expose it in the UI:

```tsx
// DashboardPage.tsx — add a Select dropdown
const [period, setPeriod] = useState<"24h" | "7d" | "30d">("24h");
const { data: metrics } = useMetrics("daily", period);
```

### 2.3 Connection Health Summary
**Files touched:** New `ConnectionHealthCard.tsx` + `DashboardPage.tsx`
**Backend impact:** None — derives from EXISTING `useCredentials()` + `useTestCredential()` data
**Risk:** Low — additive card, uses existing test endpoint

The `ApiConfigurationServlet` already has a `POST /api/config/credentials/test` endpoint. The `useTestCredential()` hook exists. A connection health card can:
1. Read credential list from `useCredentials()`
2. Show each system with its credential type
3. Offer a "Test" button that calls the existing test endpoint

### 2.4 Monitoring Filters
**Files touched:** `MonitoringPage.tsx`, `TransactionHistoryPage.tsx`
**Backend impact:** None — the `TransactionHistoryApiServlet` already accepts query parameters
**Risk:** Low — adds filter UI that sends existing query params

Check if the transaction API already supports filtering. If it accepts `flow_name` or `status` params, add dropdowns. If not, filter client-side (the React app already fetches 50 transactions at a time).

### 2.5 KPI Cards → Clickable Links
**Files touched:** `DashboardPage.tsx` only
**Backend impact:** None
**Risk:** Zero — wrapping cards in `<Link>` components

```tsx
// "Transactions (24h)" card → links to /monitoring/transactions
// "Success Rate" card → links to /monitoring
// "Running Now" card → links to /monitoring (filtered to running)
```

**Verification for entire Phase 2:**
```bash
npx tsc --noEmit && npm run build
# Manual test: dashboard shows checklist, date range works, credentials show health
# Classic view still works on all pages
```

---

## Phase 3: New Frontend Components Using Existing Data (Minor Backend Awareness)

These changes add significant new UI features but still primarily consume existing API data. Some may require small, additive backend changes (new query parameters, not new servlets).

### 3.1 Unified Profile Page (Tabbed)
**Files touched:** Refactor `ProfilePage.tsx`, `ChangePasswordPage.tsx`, `MfaSetupPage.tsx`
**Backend impact:** None — same API endpoints, just reorganized into tabs
**Risk:** Low-Medium — route changes needed

Currently 3 separate routes:
- `/profile` → `ProfilePage.tsx`
- `/profile/password` → `ChangePasswordPage.tsx`
- `/profile/security` → `MfaSetupPage.tsx`

Consolidate into a single tabbed page at `/profile` with tabs: General | Security | Preferences.

**Safety:** Keep the old routes working as redirects to `/profile?tab=security` etc. The sidebar navigation updates to just show "My Profile" instead of separate items. All API calls remain identical.

**Route migration pattern:**
```tsx
// In routes.tsx — keep old routes as redirects
{ path: "profile/password", element: <Navigate to="/profile?tab=security" replace /> },
{ path: "profile/security", element: <Navigate to="/profile?tab=security" replace /> },
```

### 3.2 Error Grouping in Transaction History
**Files touched:** `TransactionHistoryPage.tsx` + new `ErrorGroupView.tsx`
**Backend impact:** None if client-side grouping — all transaction data is already fetched
**Risk:** Low — additive tab/toggle in existing page

The `useTransactions()` hook returns transactions with `error_message` fields. Client-side grouping:
```tsx
// Group by error_message, count occurrences, show most recent
const errorGroups = transactions
  .filter(tx => tx.status === "failed" && tx.error_message)
  .reduce((groups, tx) => { /* group by error_message */ }, {});
```

### 3.3 Flow Status Summary Cards
**Files touched:** `IntegrationOverviewPage.tsx`
**Backend impact:** None — derived from existing `useEngineFlows()` data
**Risk:** Low — additive summary above existing flow table

Add 3 stat cards above the flow table:
```
Active Flows: X    |    Paused Flows: Y    |    Errored Flows: Z
```

Derived entirely from the `scheduledFlows`, `utilityFlows`, and `queryFlows` arrays already returned by `GET /api/flows`.

### 3.4 In-App Help Tooltips
**Files touched:** New `InfoTooltip.tsx` + various page files
**Backend impact:** None — tooltip content is static strings in React code
**Risk:** Zero — additive `?` icons next to complex fields

The Maven source already has a `HelpLinkService` class. For now, implement static tooltips in React (no backend call). Later, these can be wired to the `HelpLinkService` if needed.

**Priority fields for tooltips:**
- Config Wizard: Solution Type, Object Selection, Credential fields
- Flow Properties: Interval, Shift, Counter fields
- Monitoring: Success Rate calculation explanation

**Verification for Phase 3:**
```bash
npx tsc --noEmit && npm run build
# Test: tabbed profile works, error grouping shows in transactions
# Test: ALL classic view links still resolve correctly
# Test: session sharing — login via React, navigate to JSP page, session persists
```

---

## Phase 4: New Backend Endpoints (Additive Only)

These changes require NEW Java servlet code or database schema changes. They follow the existing patterns established by the API servlets. Nothing existing is modified — only new endpoints are added.

### 4.1 Onboarding Status API (Optional Enhancement)

If the client-side checklist from Phase 2.1 isn't sufficient (e.g., you want server-determined onboarding state):

**New endpoint:** `GET /api/onboarding/status`
**New servlet:** `ApiOnboardingServlet.java` in `com.interweave.businessDaemon.api`
**Response:** `{ "profileComplete": true, "companyConfigured": false, "credentialsSet": true, ... }`

This is a READ-ONLY endpoint that aggregates existing data. It queries the same tables the other servlets use. No new tables, no new mutations.

**Pattern to follow:** Copy `ApiSessionServlet.java` structure (simple GET, reads session + DB).

### 4.2 Connection Health API (Optional Enhancement)

If client-side credential testing from Phase 2.3 is too slow:

**New endpoint:** `GET /api/connections/health`
**Behavior:** Returns cached connection status from the last test, rather than testing live on every request.

**This requires:** A new `connection_health` table or column in the existing credentials system to cache last-test-result and last-test-time.

### 4.3 Transaction Retry API

**New endpoint:** `POST /api/monitoring/transactions/{id}/retry`
**New addition to:** `TransactionHistoryApiServlet.java`

This would re-queue a failed transaction. **However**, this depends on the transformation server being functional (which it currently isn't — skeleton only). So this is blocked by the vendor JAR dependency.

**Recommendation:** Add the UI button now (Phase 3), but show a tooltip "Retry unavailable — transformation engine not connected" until the vendor JARs are deployed.

---

## Phase 5: Strategic Features (Larger Scope)

These features require significant new code in both frontend and backend. They should only be started after Phases 1–3 are stable and deployed.

### 5.1 Read-Only Flow Visualization

**Scope:** Display integration flows as a node graph (source → transform → destination)

**Frontend:** React Flow library for node-based canvas rendering.

**Backend dependency:** The flow structure is stored in `workspace/` project XML files and loaded by `ConfigContext` at startup. The `GET /api/flows` endpoint returns flow metadata but NOT the flow graph structure.

**New endpoint needed:** `GET /api/flows/{flowId}/graph` that parses the workspace XML and returns a node/edge JSON structure.

**Safety approach:**
1. Start with a static visualization that reads from the `GET /api/flows` response (flow names, types, states)
2. Show a simple list-to-graph mapping: each flow as a node, connections as edges
3. Later, add workspace XML parsing for the full source→transform→destination graph

### 5.2 AI Assistant Sidebar

**Scope:** Claude-powered assistant for mapping suggestions, error analysis, doc Q&A

**Frontend:** Sidebar panel component with chat interface.

**Backend dependency:** Requires a new servlet that proxies to the Anthropic API, OR a direct client-side call to the API (simpler but exposes the API key).

**Safety approach:**
1. Start with a documentation-only assistant (search static help content)
2. Then add error analysis (pass error messages from `useTransactions()` to Claude)
3. Then add mapping suggestions (pass field schemas to Claude)

### 5.3 Visual Field Mapping Studio

**Scope:** Drag-and-drop source→destination field mapping

**Backend dependency:** Heavy — requires the full object schema from both source and destination systems. Currently, object schemas are partially available through the config wizard API (`/api/config/wizard`) but not in the field-level detail needed for a mapper.

**New endpoints needed:**
- `GET /api/schemas/{connectionId}/objects` — list objects for a connection
- `GET /api/schemas/{connectionId}/objects/{objectId}/fields` — list fields for an object

**This is blocked by:** The transformation server skeleton. Field schemas come from live API calls to Salesforce, QuickBooks, etc., which require the vendor JARs.

---

## Risk Mitigation: The Classic View Safety Net

The `ClassicViewBanner` component + `classic-routes.ts` mapping provides an automatic safety net:

1. **If a React page breaks:** User clicks "Switch to Classic View" → lands on the equivalent JSP page
2. **If a user prefers the old UI:** `localStorage` "always classic" preference redirects automatically
3. **Session is shared:** Login state persists regardless of which UI the user is on

**Rule:** Never remove the Classic View escape hatch until ALL JSP pages have fully equivalent React replacements AND those replacements have been tested in production for at least 2 weeks.

---

## Change Safety Matrix

| Phase | Backend Changes | DB Changes | Session Impact | Build Impact | Can Rollback By |
|-------|----------------|------------|---------------|-------------|----------------|
| Phase 1 | None | None | None | CSS/components only | `git revert` |
| Phase 2 | None | None | None | Component + hook changes | `git revert` |
| Phase 3 | None | None | Route redirects added | Route refactoring | `git revert` routes |
| Phase 4 | New servlets (additive) | Possible new table | None (GET-only) | + Java compilation | Remove web.xml mapping |
| Phase 5 | New servlets + external API | New tables | None | + new dependencies | Feature flag off |

---

## Testing Protocol for Each Phase

Before merging any phase:

```bash
# 1. TypeScript check
cd frontends/iw-portal && npx tsc --noEmit

# 2. Build check
npm run build

# 3. Manual smoke test (with Tomcat running)
# - Login as admin@sample.com / admin123
# - Login as demo@sample.com / demo123
# - Navigate ALL sidebar items
# - Check Classic View link on every page
# - Toggle dark/light/system theme
# - Test on mobile viewport (Chrome DevTools responsive)

# 4. Session sharing check
# - Login via React (/iw-portal/login)
# - Open /iw-business-daemon/EditProfile.jsp in new tab — should be authenticated
# - Logout via React — JSP pages should require re-login

# 5. API regression (if available)
bash web_portal/test_portal.sh
```

---

## Immediate First Commit (Start Here)

The safest possible first commit combines these Phase 1 items into a single PR:

**Branch:** `feature/ui-polish-phase1`

**Files to create:**
- `src/components/ui/skeleton.tsx` — Skeleton loading component
- `src/components/ui/empty-state.tsx` — Empty state component
- `src/components/CommandPalette.tsx` — Cmd+K overlay
- `src/components/layout/Breadcrumbs.tsx` — Path breadcrumbs

**Files to modify:**
- `index.html` — Add Google Fonts link
- `src/index.css` — Add display font rule for headings
- `src/components/layout/AppShell.tsx` — Add Breadcrumbs + CommandPalette
- `src/components/layout/ClassicViewBanner.tsx` — Persist dismiss to sessionStorage
- `src/pages/DashboardPage.tsx` — Skeleton states + empty state for transactions

**Files NOT modified:** No hooks, no API calls, no providers, no types, no routes, no backend code.

**Estimated effort:** 3–4 hours
**Risk level:** Minimal — all changes are visual/additive, zero API contract changes

---

*This plan is designed to be executed sequentially. Each phase builds on the previous one. Skip ahead only if all verification steps pass for the current phase.*
