# InterWeave IW-Portal: Unified UI/UX Design Approach

**Updated:** March 9, 2026
**Status:** Living document — the single source of truth for all UI/UX decisions
**Synthesizes:** `UI_UX_ANALYSIS.md`, `COMPETITIVE_LANDSCAPE_EXPANDED.md`, `IMPLEMENTATION_PLAN.md`

> This document consolidates every research finding, competitive insight, and implementation constraint into one actionable reference. Every new page, component, or feature should be evaluated against this approach.

---

## Part 1 — Design Principles (What Guides Every Decision)

These principles are derived from cross-referencing our brand guidelines, the ASSA design language, the InterWoven prototype aspirations, and observed patterns across 50+ competing platforms.

### Principle 1: Progressive Disclosure Over Feature Dumping

**Learned from:** Celigo (#1 G2 iPaaS), Zapier, Census

The most praised platforms hide complexity behind simple defaults. InterWeave's Config Wizard already has 5 steps, ~80 object detail properties, and multi-tier mapping categories. Exposing all of this at once overwhelms new users.

**Rule:** Every page starts with the minimum viable information. Expandable sections, "Advanced" toggles, and contextual detail panels reveal depth on demand. The dashboard shows 4 KPIs by default — not 12. The Config Wizard shows core mappings first, extended mappings behind a toggle (which it already does — preserve this pattern).

### Principle 2: Show, Don't Configure

**Learned from:** Ampersand (field mapping), MapForce (visual XSLT), n8n (node canvas), Make (scenario circles)

95% of integration platforms have a visual builder. InterWeave currently requires configuration through forms and the Eclipse IDE. Every future feature should ask: "Can the user SEE what's happening, or are they filling out a form blind?"

**Rule:** Favor visual representations over form fields wherever possible. A connection health row with green/yellow/red dots communicates more than a table of credential metadata. A node graph of Source → Transform → Destination communicates more than a list of flow IDs. A drag-and-drop field mapper communicates more than dropdown selects.

### Principle 3: Never Strand the User

**Learned from:** Celigo (onboarding), ONEiO (conversation trace), n8n (per-node error inspection)

The weakest user flow in iw-portal today: a transaction fails → user sees the failure in Monitoring → clicks it → sees a status and timestamps → dead end. No link to the flow config, no error detail, no fix suggestion, no retry.

**Rule:** Every status indicator must link to the next actionable step. Every error must suggest a resolution path. Every "you're done" state must suggest what to do next. No dead ends.

### Principle 4: Respect the Backend Contract

**Learned from:** Our own architecture analysis — shared Tomcat session, 28 API endpoints, legacy FlowProperiesServlet

UI/UX improvements must not break the existing API contracts, session sharing, or Classic View fallback. Every change is categorized by its backend impact level before implementation begins.

**Rule:** Pure frontend changes first (Phase 1). Existing API enhancements second (Phase 2). New backend endpoints third (Phase 4+). Never modify an existing API endpoint's response shape — only add new endpoints alongside existing ones.

### Principle 5: The ASSA Language Is the Foundation, Not a Cage

**Learned from:** Brand Guideline session, ASSA prototypes

The ASSA glassmorphism system (radial gradients, glass panels, 14-24px radii, pill-shaped controls) is well-implemented and gives IW-Portal a distinctive look. But it needs to evolve, not just replicate. The ASSA prototypes are static HTML — the React portal should add motion, interactivity, and data-driven dynamism that static pages can't achieve.

**Rule:** Keep the ASSA color palette, card system, and layout grid. Enhance with skeleton loading, staggered reveals, hover state feedback, branded typography, and interactive elements like the command palette.

### Principle 6: Standard View + Developer Mode — One Platform, Two Depths

**Learned from:** MuleSoft (Anypoint Studio vs. web console), Paragon (visual builder + TypeScript framework), n8n (visual canvas + code nodes), Jitterbit (low-code + custom scripting), Workato (recipes + SDK)

Every major integration platform serves two audiences with the same product: operators who need things configured and running, and developers/specialists who need to see under the hood. The validated pattern is not two separate products, but a single UI with an additive depth layer.

**Rule:** IW-Portal ships with a **Developer Mode toggle** that is always available to every user. It is not a permission gate. It is not locked behind a role. It is a UI density preference — like dark/light mode for information depth. Standard view presents a clean, no-code operations experience. Developer Mode reveals additional panels, tabs, diagnostic detail, and power tools on the same pages. A business admin never needs to turn it on, but always can if they need more detail. A developer or integration specialist can leave it on permanently.

**What Developer Mode is NOT:**
- Not a paywall or feature gate
- Not an admin-only setting
- Not a separate set of pages or routes
- Not required to complete any core workflow

**What Developer Mode IS:**
- A `localStorage`-persisted boolean, toggled from the Topbar (same row as theme toggle)
- A React context (`useDevMode()`) consumed by individual components
- Additive sections within existing pages that render conditionally
- Discoverable: standard view shows subtle hints ("More detail available in Developer Mode") at strategic touchpoints

---

## Part 2 — Developer Mode: Architecture & Component Model

### 2.1 Implementation Pattern

Developer Mode follows the exact same architecture as the existing `ThemeProvider`:

```tsx
// src/providers/DevModeProvider.tsx
const DEV_MODE_KEY = "iw-portal-dev-mode";

interface DevModeCtx {
  devMode: boolean;
  setDevMode: (v: boolean) => void;
}

// Persists to localStorage, wraps App in provider
// Consumed via useDevMode() hook anywhere in the component tree
```

Topbar toggle (sits alongside the theme toggle):

```
[User pill]  [Notifications]  [Alerts]  [🌙 Dark]  [⚡ Dev]  [Logout]
```

When active, the `⚡ Dev` pill highlights with the primary accent color. The toggle is always visible, always accessible, one click.

### 2.2 The Standard ↔ Developer Split

The guiding question for every component: **"Does a non-technical admin need this to configure, deploy, and monitor their integrations?"**

- **Yes** → Standard view (always visible)
- **No, but it helps debug/customize/optimize** → Developer Mode (additive panel)

Components use a simple conditional pattern:

```tsx
const { devMode } = useDevMode();

return (
  <div>
    {/* Always rendered — the complete standard experience */}
    <KpiCards />
    <RecentTransactions />

    {/* Additive depth — only when Developer Mode is on */}
    {devMode && <ConnectionSchemaStatus />}
    {devMode && <FlowGraphToggle />}
  </div>
);
```

No separate routes. No separate pages. No feature flags server-side. The same page, the same data, with deeper visualization and controls revealed.

### 2.3 Standard View vs. Developer Mode — Feature Map

Every existing and planned feature is assigned to one of two layers:

| Feature Area | Standard View (always visible) | Developer Mode (additive) |
|-------------|-------------------------------|--------------------------|
| **Dashboard** | KPI cards, setup checklist, quick actions, recent transactions table | Connection schema status, engine health detail, raw API response times, per-flow sparklines |
| **Monitoring** | Charts, transaction table, date range selector, alert rules | Transaction conversation trace (step-level timeline), error data snapshots, raw record payloads, retry controls |
| **Config Wizard** | 5-step wizard, object-level sync toggles, credential entry, review | Field-level mapping studio, XSLT preview panel, schema browser, mapping validation detail, dependency graph |
| **Connections** | Connection cards with status dots, test button, edit credentials | Schema drift detection, field-level schema browser, raw API response viewer, connection latency history |
| **Integrations** | Flow table (list view), start/stop, schedule editor, flow properties | Flow graph visualization (node canvas), engine controls (log level, daemon restart), bulk operations, cron expression editor, environment variable inspector |
| **Notifications** | Notification list, mark read, delete | Webhook delivery log, notification payload inspector |
| **Profile** | General info, password change, MFA setup, preferences | API session inspector, active sessions list |
| **Audit Log** | Event table with filters | Raw event payloads, API call trace |
| **Logging** | Log file viewer with search | Live log tail (streaming), log level adjustment, log download |
| **Template Catalog** | Browse templates, apply to wizard | Template JSON editor, custom template creation, import/export |
| **AI Assistant** | (not visible in standard view) | AI sidebar: mapping suggestions, error analysis, config Q&A |

### 2.4 Discoverability: Hints in Standard View

Standard view is complete on its own, but at strategic points, subtle hints let users know Developer Mode exists without being pushy:

**Pattern 1 — Inline hint on detail-heavy areas:**
When a user clicks on a failed transaction in Monitoring and sees only the basic error message, show a small muted-text link below:

```
Error: QB duplicate key on Customer "Acme Corp"
ⓘ Enable Developer Mode for step-by-step trace
```

**Pattern 2 — Empty area with placeholder:**
On the Config Wizard mapping step, below the object-level sync toggles, show a subtle collapsed section:

```
──── Field-Level Mapping ────
Available in Developer Mode ⚡
```

**Pattern 3 — Topbar badge:**
When Developer Mode is off and the user is on a page that has significant developer content, the `⚡ Dev` pill in the topbar could show a subtle dot indicator (similar to the notification badge) to signal "there's more here."

These hints are never blocking, never modal, never interruptive. They're discoverable for curious users and invisible if you're not looking.

### 2.5 Sidebar Navigation with Developer Mode

The sidebar `NAV_ITEMS` array conditionally includes developer-only entries:

```
ALWAYS VISIBLE:
  Platform:        Dashboard, Monitoring, Notifications
  Account:         My Profile, Company
  Configuration:   Company Config

DEVELOPER MODE ADDS:
  Configuration:   + Connections, + Field Mapping, + Templates
  Administration:  Integrations, Logging, Audit Log
  Developer:       + Schema Browser, + Engine Controls
```

Wait — actually, looking at the current sidebar, Administration (Integrations, Logging, Audit Log) is already visible in standard view, and it should stay that way. The sidebar additions for Developer Mode should be minimal and specific. Here's the refined approach:

**Standard sidebar (10 items — current structure preserved exactly):**
```
Platform:       Dashboard, Monitoring, Notifications
Account:        My Profile, Security, Company
Configuration:  Configuration
Admin:          Integrations, Logging, Audit Log
```

**Developer Mode adds to existing groups (not a new group):**
```
Configuration:  + Connections          (between Configuration and Admin)
                + Field Mapping        (when Phase 5 ships)
                + Templates            (when Phase 3 ships)
Developer:      + Schema Browser       (when Phase 5 ships)
                + Engine Diagnostics   (when Phase 5 ships)
```

The key: Developer Mode doesn't reorganize the sidebar — it adds entries within existing groups, and optionally adds a "Developer" group at the bottom for power tools that don't fit elsewhere.

---

## Part 3 — Design Considerations by Feature Area

Each section below maps a UI/UX area to: what we have now, what the industry does, what specific competitors taught us, and the concrete design approach — now split into Standard view and Developer Mode layers.

---

### 3.1 Onboarding & First-Run Experience

**Current state:** None. User logs in, sees a dashboard with zeros, and has no guidance.

**Industry benchmark:** Celigo's onboarding is their #1 rated feature on G2. Zapier walks users through creating their first "Zap" within 60 seconds. Jitterbit was ranked #1 in Enterprise Implementation Index for three quarters running.

**Standard view:**
- `SetupChecklist.tsx` — Glass-panel card at top of DashboardPage with 5 steps, progress bar, "Continue →" links. Dismissable per session.
- `WelcomeModal.tsx` — Full-screen overlay on first login (detect via `localStorage` flag).
- Sidebar context pill shows `Setup: 3/5` when incomplete.

Welcome state detection uses existing hooks only:
```
useProfile()         → firstName/lastName filled?     → Step 1 complete
useCompanyProfile()  → companyName non-empty?          → Step 2 complete
useCredentials()     → credentials array non-empty?    → Step 3 complete
useEngineFlows()     → any flows returned?             → Step 4 complete
useDashboard()       → total_count_24h > 0?            → Step 5 complete
```

**Developer Mode adds:**
- Nothing extra for onboarding — this is a standard-view feature. Developer Mode users skip the welcome modal automatically if their preference is already set.

---

### 3.2 Dashboard

**Current state:** 4 KPI cards (sparklines), running transactions, recent transactions table, quick action links.

**Standard view:**
- KPI cards with skeleton loading, clickable links to detail pages
- Date range selector using existing `useMetrics("daily", period)` parameter
- Setup checklist card (see 3.1)
- Connection status strip: horizontal row of system icons with colored status dots (derived from cached `useTestCredential()` results)
- Recent transactions table with inline expandable error detail (ONEiO accordion pattern)
- Empty state component when no transactions exist
- Subtle health indicator in dashboard header (green if all connections pass, amber if any fail)

**Developer Mode adds:**
- **Connection schema status panel** — shows last schema discovery time per system, field count, custom field count, and schema drift warnings
- **Engine health detail card** — daemon uptime, heartbeat interval, memory usage, thread count (derived from `FlowsResponse.data.heartbeatInterval` + new data if available)
- **Per-flow sparklines** — mini transaction count chart for each active flow, not just the aggregate
- **Raw API response times** — latency of the last dashboard API call, useful for diagnosing slow page loads
- **Quick JSON viewer** — a collapsible panel at the bottom showing the raw `DashboardResponse` payload for debugging

---

### 3.3 Connection & Credential Management

**Current state:** Credentials managed inside Config Wizard step 3. No standalone view.

**Standard view:**
- New sidebar nav item: "Connections" under Configuration group
- Connection cards (ASSA glass-panel): system icon, name, username, status dot, last test time
- "Test", "Edit Credentials", "View Flows" action buttons
- Test button calls existing `useTestCredential()` hook

**Developer Mode adds:**
- **Schema browser per connection** — expandable tree of objects and fields discovered from the source/destination API. Each field shows name, type, required status, and whether it's a custom field (Ampersand pattern)
- **Schema drift detection** — compares current schema to last-known cached schema. Highlights added/removed/changed fields. Fourth status state: `connected | degraded | disconnected | schema-changed`
- **Connection latency history** — mini chart showing response times of the last 10 connection tests
- **Raw API response viewer** — toggle to see the actual HTTP response from the last test call
- **Credential environment inspector** — shows which `.env` values and `context.xml` settings are active (reads existing `FlowsResponse.data.isHosted` and DB mode)

---

### 3.4 Field Mapping & Data Transformation

**Current state:** Object-level sync direction toggles in Config Wizard step 2. No field-level mapping in web portal.

**Standard view:**
- Config Wizard object-level mapping (existing `buildSyncMappings()` system) with enhanced visuals: better bidirectional indicators, dependency warnings from `MAPPING_DEPENDENCIES`, recommended defaults from `RECOMMENDED_DEFAULTS`
- `InfoTooltip` on each mapping row explaining what it syncs
- Pre-publish validation: client-side check that required mappings are set before save

**Developer Mode adds:**
- **Visual Field Mapping Studio** — `FieldMapper.tsx` with source fields (left panel) and destination fields (right panel). Drag connections between them. Data type badges on each field. Unmapped required fields highlighted red. (Requires `GET /api/schemas/{credentialType}/fields` — Phase 5, but UI scaffolding can be built earlier with static schemas from `deriveSolutionMeta()`)
- **XSLT preview panel** — read-only view of the XSLT transformation that will be generated from the current mapping. This is InterWeave's architectural differentiator — no workflow-only platform can show this
- **Mapping expression editor** — for advanced users who need to write custom transformation expressions (e.g., concatenating fields, date format conversion, conditional logic)
- **AI mapping suggestions** — Claude-powered sidebar suggesting field pairings with confidence scores. "Map SF.Account.Name → QB.Customer.CompanyName (92%)" with Apply/Dismiss buttons
- **Mapping diff view** — when editing an existing mapping, show what changed vs. the last saved version
- **Import/Export mappings** — download mapping configuration as JSON, upload to apply. Useful for migrating mappings between environments

---

### 3.5 Flow Visualization & Management

**Current state:** `IntegrationOverviewPage` with `FlowTable`, start/stop/schedule/properties actions, 10s auto-refresh.

**Standard view:**
- Flow status summary cards above the table: Active / Paused / Errored counts
- Flow table with current functionality preserved
- Per-flow "History" link: shows last 5 executions as status dots (● ● ● ✕ ●)
- Flow properties dialog (existing)

**Developer Mode adds:**
- **Flow graph visualization** — toggle between "List view" (current table) and "Graph view" (React Flow node canvas). Each flow is a node card with status color, connected by edges showing data flow direction. Hovering a node shows a tooltip with: description, last execution, schedule, connections used (Jitterbit auto-documentation pattern)
- **Engine controls panel** — start/stop daemon, adjust log level per flow, view heartbeat status. Currently this is scattered across the FlowTable and tabs; Developer Mode consolidates it into a dedicated control surface
- **Cron expression editor** — for developers who want precise scheduling beyond simple interval/shift/counter. Converts to/from the existing interval-based system
- **Environment variable inspector** — shows all flow properties (`FlowProperty[]`) grouped by type (text/password/upload) with the ability to see which are overridden by environment config
- **Bulk operations** — select multiple flows, start/stop/reschedule all at once (the FlowTable already has bulk selection UI; Developer Mode enables the bulk action buttons)
- **Flow dependency map** — which flows share connections or depend on each other. Derived from flow properties matching credential identifiers

---

### 3.6 Monitoring, Errors & Transaction Tracing

**Current state:** MonitoringPage with Recharts, TransactionHistoryPage with pagination, AlertConfigPage.

**Standard view:**
- Charts with date range selector (uses existing `useMetrics()` period parameter)
- Transaction table with error message preview
- Error grouping toggle: group failed transactions by `error_message`, show count and frequency
- Alert rules with enable/disable toggles
- "Retry" button on failed transactions (disabled until transformation server is deployed, with tooltip explaining why)

**Developer Mode adds:**
- **Transaction conversation trace** — slide-in drawer showing step-level timeline of the transaction lifecycle (ONEiO pattern):
  ```
  [10:00:01] ● Source fetch started — Salesforce API
  [10:00:03] ● 247 records retrieved
  [10:00:03] ● Transform started — XSLT processing
  [10:00:05] ● Transform complete — 247 → 245 (2 filtered)
  [10:00:05] ● Destination push started — QuickBooks API
  [10:00:08] ● 243 records written, 2 failed
  [10:00:08] ✕ Error: QB duplicate key on Customer "Acme Corp"
  ```
  Starts with a simplified version using existing data. Full step-level detail via future `GET /api/monitoring/transactions/{id}/trace` endpoint.
- **Error data snapshots** — for each failed record, show the actual source record that failed and the error response from the destination API
- **Raw record payloads** — JSON viewer for the input and output data of a transaction
- **Suggested alert rules** — AI-powered recommendations: "You had 5 failures on Flow X this week — create an alert?"
- **Threshold visualization** — show current metric value relative to alert threshold on a mini gauge
- **Live log correlation** — link from a failed transaction directly to the relevant log entry in LoggingPage

---

### 3.7 Navigation, Command Palette & Information Architecture

**Current state:** 260px glass sidebar, 10 nav items, 4 groups. Topbar search, theme toggle.

**Standard view:**
- Command Palette (`Cmd+K` / `Ctrl+K`) — navigation + actions, reusing existing `SEARCH_ITEMS`
- Breadcrumbs below Topbar
- Sidebar preserved exactly as-is with 10 items in 4 groups
- Dismissable ClassicViewBanner with session persistence

**Developer Mode adds to navigation:**
- Additional sidebar items within existing groups (see Part 2.5)
- Command palette gains developer-specific actions: "Toggle engine log level", "View raw API response", "Open schema browser", "Export mapping config"
- Developer Mode indicator in Topbar: the `⚡` toggle pill highlights when active

**Context-aware sidebar (both modes):** When inside the Config Wizard, the sidebar optionally shows a wizard stepper for persistent progress context.

**Sidebar evolution plan — both modes contribute:**
```
Standard view sidebar:                  Developer Mode adds:
─────────────────────                   ────────────────────
Platform                                (unchanged)
  Dashboard                               
  Monitoring                              
  Notifications                           

Account                                 (unchanged)
  My Profile
  Company

Configuration                           Configuration
  Configuration                           Configuration
                                          + Connections ⚡
                                          + Field Mapping ⚡
                                          + Templates ⚡

Administration                          Administration
  Integrations                            Integrations
  Logging                                 Logging
  Audit Log                               Audit Log

                                        Developer ⚡
                                          + Schema Browser ⚡
                                          + Engine Diagnostics ⚡
```

Items marked ⚡ only appear when Developer Mode is toggled on.

---

### 3.8 Visual Design & Motion

**Standard view (Phase 1):**
- Plus Jakarta Sans heading font
- Skeleton loading states (replaces all `<Loader2>` spinners)
- Empty state components on all data pages
- Staggered fade-in on dashboard cards
- Interactive hover states on cards and table rows
- Micro-feedback on mutations (green pulse on success, amber on warning)

**Developer Mode adds:**
- **Monospace code font** for raw data displays: JSON viewers, XSLT previews, log entries, schema trees. Use `var(--mono)` (already defined in ASSA CSS as `ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas`)
- **Syntax highlighting** in code panels (XSLT, JSON, XML). Use a lightweight library like Prism.js or highlight.js
- **Denser information layout** — Developer Mode components use tighter padding and smaller text (12px body, 11px labels) since developer users expect higher information density
- **Status color intensity** — Developer Mode panels use slightly more saturated status colors and border highlights to make system state immediately scannable

---

### 3.9 AI Integration

**Standard view:** Not visible. No AI features in standard view to keep the core experience clean and focused.

**Developer Mode reveals the AI sidebar:**

```
┌──────────────────────────────┐
│  ✦ InterWeave AI             │
│  ─────────────────────────── │
│  [Context: Config Wizard]    │
│                              │
│  Suggestions:                │
│  • Map SF.Account.Name →     │
│    QB.Customer.CompanyName   │
│    [Apply] [Dismiss]         │
│                              │
│  • 3 required fields are     │
│    unmapped. View details.   │
│                              │
│  ─────────────────────────── │
│  Ask a question...           │
│  [Send]                      │
└──────────────────────────────┘
```

Context-aware: Config Wizard → mapping suggestions, Monitoring → error analysis, Dashboard → optimization tips.

Implementation layers (each ships independently):
1. Static help content (Phase 3 — no AI API)
2. Error analysis via Claude API (Phase 5 — read-only)
3. Mapping suggestions via Claude API (Phase 5 — structured output)
4. Natural language configuration (aspirational — write actions with user approval)

**Standard view hint:** On the Config Wizard mapping step, a subtle line: "ⓘ AI mapping suggestions available in Developer Mode ⚡"

---

### 3.10 Template & Recipe Library

**Standard view (when templates ship in Phase 3):**
- Template catalog page with integration pattern cards
- Browse by source/destination system
- "Use This Template →" button pre-fills Config Wizard

**Developer Mode adds:**
- **Template JSON editor** — view and edit the template definition as structured JSON
- **Custom template creation** — save current configuration as a new reusable template
- **Import/Export** — download templates as JSON files, upload to import
- **Community sharing** (future) — submit templates to a shared library

---

### 3.11 Accessibility, Performance & Mobile

These apply to both modes equally:

**Accessibility:**
- `aria-label` on all icon-only buttons
- `aria-live="polite"` on auto-refresh regions and toast notifications
- "Skip to main content" link
- `prefers-reduced-motion` support
- WCAG AA contrast audit for `text-muted-foreground`

**Performance:**
- Skeleton loading replaces spinners in both modes
- Developer Mode panels are lazy-loaded (React.lazy) since they contain heavier components (code viewers, graph canvas)
- Code-split developer-only components into a separate chunk so standard-view bundle size stays small

**Mobile:**
- Developer Mode toggle available on mobile but most developer panels are desktop-optimized
- Standard view is fully responsive as currently implemented
- Developer panels on mobile: collapsible accordion sections instead of side-by-side layouts

---

## Part 4 — Design Consideration Checklist

Use this checklist when building or reviewing any new feature:

### Before starting:
- [ ] Which phase does this belong to? (Phase 1 = pure CSS, Phase 2 = existing API, Phase 3+ = new work)
- [ ] Does this touch any existing API endpoints? If yes, which hooks?
- [ ] Does this affect session state? (Must work with both React and JSP codepaths)
- [ ] What's the Classic View equivalent? (Must have a fallback JSP link)
- [ ] **Which mode?** Is this Standard view, Developer Mode, or both?
- [ ] If Developer Mode: is there a subtle hint for Standard view users?

### Design quality:
- [ ] Does the component use ASSA glass-panel styling? (`glass-panel rounded-[var(--radius)]`)
- [ ] Does it have a loading state? (Skeleton, not spinner)
- [ ] Does it have an empty state? (Icon + message + CTA)
- [ ] Does it have an error state? (Red border, message, retry action)
- [ ] Does it use the status color system? (Green/Blue/Amber/Red/Gray)
- [ ] Is the heading in the display font? (Plus Jakarta Sans or equivalent)
- [ ] **If Developer Mode:** does it use monospace font for code/data? Is info density appropriate?

### Interaction quality:
- [ ] Does every status indicator link to the next action?
- [ ] Does every error suggest a resolution path?
- [ ] Does it appear in the Command Palette search items?
- [ ] Does it have breadcrumb context?
- [ ] Does it have contextual help tooltips on complex fields?

### Competitive parity:
- [ ] Would this feature hold up against Celigo's equivalent?
- [ ] Does the visual representation match what Workato/n8n/Make would show?
- [ ] Is there a progressive disclosure path from simple to advanced?
- [ ] **Does the Standard/Developer split feel natural?** Would a non-technical user feel complete in Standard view? Would a developer want to leave Dev Mode on?

### Technical safety:
- [ ] `npx tsc --noEmit` passes?
- [ ] `npm run build` succeeds?
- [ ] Session sharing test passes? (Login via React → JSP page → still authenticated)
- [ ] Classic View links still resolve correctly?
- [ ] **Developer Mode components are lazy-loaded?** (Don't bloat the standard-view bundle)

---

## Part 5 — Navigation & Information Architecture Map

```
SIDEBAR GROUP          ROUTE                    PAGE COMPONENT              MODE        STATUS
────────────────────────────────────────────────────────────────────────────────────────────────
Platform
  Dashboard            /dashboard               DashboardPage               Standard    ✅ Live
  Monitoring           /monitoring              MonitoringLayout            Standard    ✅ Live
    Overview           /monitoring              MonitoringPage              Standard    ✅ Live
    Transactions       /monitoring/transactions TransactionHistoryPage      Standard    ✅ Live
    Alerts             /monitoring/alerts       AlertConfigPage             Standard    ✅ Live
  Notifications        /notifications           NotificationsPage           Standard    ✅ Live

Account
  My Profile           /profile                 ProfilePage (tabbed)        Standard    ✅ → 🔄 Unify
    General            /profile                 (tab)                       Standard    ✅ Live
    Security           /profile?tab=security    (tab: password + MFA)       Standard    🔄 Merge
    Preferences        /profile?tab=prefs       (tab: theme, dev mode)      Standard    🔲 Planned
  Company              /company                 CompanyPage                 Standard    ✅ Live

Configuration
  Company Config       /company/config          CompanyConfigPage           Standard    ✅ Live
  Config Wizard        /company/config/wizard   ConfigurationWizardPage     Standard    ✅ Live
  Connections          /connections             ConnectionsPage             ⚡ Dev      🔲 Phase 2
  Field Mapping        /company/config/mapping  FieldMappingStudio          ⚡ Dev      🔲 Phase 5
  Templates            /templates               TemplateCatalogPage         ⚡ Dev      🔲 Phase 3

Administration
  Integrations         /admin/configurator      IntegrationOverviewPage     Standard    ✅ Live
  Logging              /admin/logging           LoggingPage                 Standard    ✅ Live
  Audit Log            /admin/audit             AuditLogPage                Standard    ✅ Live

Developer                                                                   ⚡ Dev
  Schema Browser       /dev/schemas             SchemaBrowserPage           ⚡ Dev      🔲 Phase 5
  Engine Diagnostics   /dev/engine              EngineDiagnosticsPage       ⚡ Dev      🔲 Phase 5
```

**Note on Developer Mode route access:** All routes are technically accessible by URL even when Developer Mode is off. The sidebar just doesn't show them. If a user navigates to `/dev/schemas` directly without Developer Mode, the page renders with a banner: "This is a Developer Mode feature. Enable Developer Mode in the topbar for the best experience." This prevents broken links and respects the always-available philosophy.

---

## Part 6 — Quick Reference: Competitor-to-Feature Mapping

| Feature You're Building | Primary Reference | Secondary Reference | Mode | What to Study |
|------------------------|-------------------|--------------------|----|--------------|
| Onboarding checklist | Celigo | Zapier | Standard | Step progression, CTA placement, dismissal UX |
| Connection health cards | Fivetran | Boomi | Standard | Status dots, last-sync timestamp, test button |
| Error grouping dashboard | Celigo | n8n | Standard | Group by type, occurrence count, suggested fix |
| Date range selector | Fivetran | Polytomic | Standard | Period presets, custom range, chart update |
| Template catalog | Workato | Zapier | ⚡ Dev | Card grid, category filter, "Use template" button |
| Visual field mapper | Census | Ampersand | ⚡ Dev | Drag source→dest, type indicators, validation |
| Transaction conversation trace | ONEiO | Hightouch | ⚡ Dev | Vertical timeline, step-level detail |
| Flow graph visualization | n8n | Make | ⚡ Dev | Node layout, edge drawing, zoom/pan |
| Command palette | Linear | Vercel | Both | Fuzzy search, action items, keyboard nav |
| AI mapping suggestions | Workato RecipeIQ | SnapLogic Iris | ⚡ Dev | Confidence scores, approve/reject |
| Pre-publish validation | Astera | Integrate.io | Both | Required field check, type compat, summary |
| Schema drift detection | Fivetran | Integrate.io | ⚡ Dev | Change diff, alert trigger |
| XSLT visual generation | Altova MapForce | — | ⚡ Dev | Drag-and-drop → auto XSLT code |
| Engine controls | MuleSoft | Jitterbit | ⚡ Dev | Daemon state, log levels, heartbeat |

---

## Part 7 — Implementation Priority with Developer Mode

Developer Mode itself (the toggle, context provider, and conditional rendering pattern) should be implemented in **Phase 1** alongside the other UI polish items. It's zero backend work — just a `DevModeProvider.tsx`, a topbar button, and `useDevMode()` hook. Even though most Developer Mode *content* won't ship until later phases, the toggle infrastructure should exist from the start so that:

1. Every subsequent feature knows where to render (Standard vs. Developer Mode)
2. The user preference is persisted immediately
3. New developer-only components can be added incrementally without architectural changes

**Phase 1 adds:** DevModeProvider, toggle in Topbar, empty Developer Mode framework. Standard view features only (skeletons, typography, command palette, breadcrumbs).

**Phase 2 adds:** Connection health cards (⚡ Dev sidebar item + page), date range selector (Standard). Developer Mode content on Dashboard (engine health detail).

**Phase 3 adds:** Template catalog (⚡ Dev), error grouping (Standard), flow status cards (Standard), in-app help tooltips (Standard).

**Phase 4 adds:** New API endpoints that power Developer Mode features (schema discovery, transaction trace, connection health cache).

**Phase 5 adds:** Field Mapping Studio (⚡ Dev), Flow Graph Visualization (⚡ Dev), AI Assistant (⚡ Dev), Schema Browser (⚡ Dev), Engine Diagnostics (⚡ Dev).

---

*This document supersedes individual feature-level recommendations scattered across the other docs. When in doubt, follow the principles in Part 1, check which mode a feature belongs to in Part 2, and verify against the checklist in Part 4.*
