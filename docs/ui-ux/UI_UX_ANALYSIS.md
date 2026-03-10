# InterWeave IW-Portal: UI/UX Deep-Dive Analysis & Modernization Roadmap

**Prepared:** March 9, 2026
**Scope:** Cross-referencing Brand Guidelines, ASSA reference designs, InterWoven prototype, current iw-portal codebase, and competitive iPaaS landscape
**Objective:** Identify gaps and opportunities across UI, UX, features, functions, access, usability, user flow, and all critical UI/UX dimensions

---

## 1. Context Recap — What We're Working With

### 1.1 Brand Foundation (from Brand Guideline Session)

The brand identity established in our Brand Guideline session anchors the entire visual system:

- **Color DNA:** Midnight navy (#071021) as the base dark background; Primary blue (#3b82f6) for interactive and brand elements; Success green (#22c55e) as the secondary accent; Warning amber (#f59e0b) for alerts; Destructive red (#ef4444) for errors. The blue-to-green gradient is the brand's signature motif, representing two systems connected through intelligent integration.
- **Logo Concept — "Infinity Circuit":** A mathematical lemniscate (∞) backbone with hexagonal PCB nodes and right-angle circuit traces. Navy (left/source) and cyan (right/destination) hex nodes. This encodes the core platform narrative of continuous, bidirectional data flow.
- **Typography:** System font stack (ui-sans-serif, system-ui, Segoe UI, Roboto) — functional but **not distinctive**. No branded display font yet.
- **Glassmorphism Design Language:** Semi-transparent panels with backdrop blur, radial gradient backgrounds with multi-colored light sources, rounded 18–24px border radii throughout.

### 1.2 ASSA Reference Designs

The `frontends/assa/` directory contains two static HTML prototypes that serve as the UI pattern library for iw-portal:

**assa_customer_portal** (9 pages): login, index, billing, intake, library, notifications, profile, resource, search, support, webinars
**assa_master_console** (9 pages): index, analytics, audit, content, integrations, users, and others

Key ASSA design patterns currently adopted by iw-portal:
- 260px glass sidebar with grouped navigation and context pills
- Pill-shaped search bar with `/` keyboard shortcut
- 12-column card grid system with `.span-4`, `.span-6`, etc.
- Glass-panel cards with gradient backgrounds and border highlights
- Data tables with separated borders and muted header rows
- KPI cards with large numerics and status dots
- Context pills showing environment state
- Tag/badge system with dot indicators

### 1.3 Current iw-portal Implementation

**Stack:** Vite 7 + React 19 + TypeScript (strict) + Tailwind CSS 4 + shadcn/ui + TanStack Query v5 + React Router v7 + Recharts 3

**Route Map (22 pages):**
- Public: Login, Register (user), Register (company), Forgot Password, MFA Verify
- Protected Shell: Dashboard, Monitoring (with sub-routes: overview, transactions, alerts), Profile, Change Password, Security/MFA Setup, Company, Company Config, Config Wizard, Integrations (admin), Logging, Audit Log, Notifications

**Architecture Highlights:**
- AppShell with collapsible sidebar + topbar + ClassicViewBanner
- Lazy-loaded heavy routes (monitoring, config wizard, integrations, MFA, audit, notifications)
- Dark/light/system theme toggle persisted to localStorage
- "Classic View" banner on every page linking back to legacy JSP
- Shared Tomcat session between React and JSP for incremental migration
- Auth via JSON API servlets sharing session with legacy login

### 1.4 InterWoven Prototype (Aspirational Reference)

The `frontends/InterWoven/` directory contains a Vite+React prototype with significantly more advanced features that represent the platform's aspirational direction:

- **AIAssistant / AIContextualAssistant** — In-app AI helper for mapping suggestions and troubleshooting
- **VisualWorkflowBuilder / WorkflowSystem** — Drag-and-drop flow design canvas
- **MappingStudio / FieldMapper / SchemaMapper / NestedObjectMapper** — Visual data mapping tools
- **ConnectionHub / ConnectionBuilder / ConnectionManager** — Visual connection management with health monitoring
- **IntegrationWizard / IntegrationOnboarding / WelcomeFlow / GuidedTour** — Full onboarding sequence
- **MonitoringCenter / ErrorDebugger / ReportsPage** — Advanced observability
- **WebhookManager / OAuthHelper / CredentialsConfig** — Security and connectivity management
- **ObjectRelationshipVisualizer** — Entity relationship diagrams
- **ActivityLog / PlatformSettings** — Audit and platform configuration

---

## 2. Competitive Landscape — How the Industry Does It

### 2.1 Key Competitors Analyzed

| Platform | Target | UI Philosophy | Key UX Differentiator |
|----------|--------|--------------|----------------------|
| **Workato** | Enterprise, business technologists | Recipe-based visual builder, drag-and-drop page editor, Insights dashboards | AI-powered RecipeIQ suggestions; Workbot in Slack/Teams; Workflow Apps with custom UI builder |
| **MuleSoft Anypoint** | Enterprise IT teams, API developers | Anypoint Studio IDE + web console, API Manager | Real-time API network visualization; comprehensive lifecycle management |
| **Celigo** | Mid-market, ERP/ecommerce | Clean visual workflow builder, prebuilt templates | AI-assisted integration design; automated error resolution; #1 G2 iPaaS; thorough onboarding |
| **n8n** | Technical teams, open-source | Node-based canvas, code-optional workflows | Self-hostable; AI agent workflows; mix of visual and code |
| **Make (Integromat)** | SMB–mid-market | Scenario-based visual builder with circular module UI | Highly visual data flow representation; affordable; large template library |
| **Boomi** | Enterprise IT | Atom-based cloud architecture, flow designer | API Control Plane; EDI support; AI agents for build acceleration |
| **Zapier** | SMB, non-technical users | Zap builder, simple trigger→action flow | 7000+ app connections; extreme simplicity; Tables product for basic data management |

### 2.2 Common UI/UX Patterns Across Top Platforms

**Every major competitor now offers these core UX elements:**

1. **Visual Flow/Workflow Builder** — Drag-and-drop canvas for designing integration logic. This is the single most important feature gap for iw-portal.
2. **Command Palette (Cmd+K / Ctrl+K)** — Quick navigation and action execution. Workato, n8n, and most modern SaaS products include this.
3. **AI-Assisted Design** — Workato (RecipeIQ), Celigo (AI integration design), Boomi (AI agents), n8n (AI agent workflows). AI is no longer optional in iPaaS.
4. **Guided Onboarding** — Celigo's thorough onboarding process is cited as a top differentiator. Workato provides templates and quick-starts. Zapier has an interactive wizard.
5. **Real-Time Monitoring Dashboards** — MuleSoft's API Manager shows live network state. All platforms offer transaction monitoring with filterable, drillable charts.
6. **Prebuilt Templates/Recipes** — Template libraries for common integration patterns (Salesforce↔QuickBooks, etc.) dramatically reduce time-to-value.
7. **Connection Health Indicators** — Live status of each connected system, credential validity, last successful sync.
8. **Error Management UI** — Dedicated error views with intelligent grouping, suggested fixes, retry mechanisms. Celigo leads here.
9. **Role-Based Interfaces** — Different dashboards and available actions based on user role (admin vs. operator vs. viewer).
10. **In-App Help & Documentation** — Contextual help panels, searchable docs, tooltips on complex fields.

### 2.3 Industry UX Trends (2025–2026)

Based on Gartner, Forrester, and industry analysis:

- **AI-first interfaces** — AI suggestions, predictive insights, and automated remediation are table-stakes
- **Adaptive dashboards** — Customizable widget layouts, saved views, role-specific default configurations
- **Micro-interactions and motion** — Loading states, transition animations, success confirmations that provide feedback
- **Progressive disclosure** — Complexity revealed on demand; simple defaults with power-user depth
- **Agentic orchestration** — Workato and Boomi are positioning for AI agent management, not just integration flows
- **Embedded automation** — Running automations inside chat tools (Slack, Teams) via bots

---

## 3. Gap Analysis — IW-Portal vs. Industry Standard

### 3.1 Critical Gaps (High Priority — Competitive Necessity)

#### 🔴 GAP 1: No Visual Workflow/Flow Builder

**Current state:** Integration flows are configured through the Eclipse IDE (`iw_ide.exe`) or the Config Wizard (a 5-step form). There is no visual, drag-and-drop flow canvas in the web portal.

**Industry standard:** Every major iPaaS (Workato, MuleSoft, n8n, Make, Celigo, Boomi) offers a visual flow builder as a core feature. This is the primary design surface where users spend their time.

**Impact:** Users cannot visualize, modify, or debug integration flows without opening the legacy Eclipse IDE. This is the single largest usability gap.

**Recommendation:** Port the InterWoven `VisualWorkflowBuilder` / `WorkflowSystem` concept to iw-portal. Use React Flow or similar library for the node-based canvas. Even a read-only flow visualization would be a massive improvement.

#### 🔴 GAP 2: No Onboarding / First-Run Experience

**Current state:** Users land on the login page, authenticate, and arrive at the dashboard. There is no guided tour, welcome flow, setup checklist, or contextual introduction.

**Industry standard:** Celigo is rated #1 on G2 specifically because of its onboarding process. Workato provides template quick-starts. Modern SaaS universally includes first-run guidance.

**Impact:** New users don't know what to do next. The company config wizard exists but isn't discoverable from the dashboard unless you navigate to it.

**Recommendation:** Implement a welcome flow for first-time users (detect `isFirstLogin` flag or empty company config). Show a progress checklist on the dashboard. Add a GuidedTour component (like the InterWoven prototype).

#### 🔴 GAP 3: No Data Mapping / Field Mapping UI

**Current state:** Object mapping is handled through the Config Wizard's "Object Mapping" step and the Eclipse IDE. No dedicated visual mapping studio exists in the web portal.

**Industry standard:** Workato's field mapping UI, MuleSoft's DataWeave visual transformer, Celigo's mapping interface — all provide drag-and-drop source→destination field mapping with transformation preview.

**Impact:** The core value proposition of InterWeave (connecting business systems) requires mapping configuration. Without a visual mapper, this must be done in the legacy IDE.

**Recommendation:** Build a visual FieldMapper component (drag source fields to destination fields, with transformation dropdowns). The InterWoven prototype had `MappingStudio`, `FieldMapper`, `NestedObjectMapper`, and `SchemaMapper` as reference.

#### 🔴 GAP 4: No AI Assistance Layer

**Current state:** No AI features anywhere in the portal.

**Industry standard:** Workato (RecipeIQ), Celigo (AI integration design), Boomi (AI agents), n8n (AI agent workflows), Tray.io (Merlin AI). AI is a competitive necessity for 2026.

**Impact:** Users must manually configure everything. No suggestions, no auto-completion, no intelligent error resolution.

**Recommendation:** Start with an AI assistant sidebar (like InterWoven's `AIContextualAssistant`) that can suggest field mappings, explain error logs, recommend flow configurations, and answer documentation questions. This can be powered by Claude API.

### 3.2 Important Gaps (Medium Priority — User Experience Quality)

#### 🟡 GAP 5: Dashboard Is Static and Shallow

**Current state:** Dashboard shows 4 KPI cards (total transactions, success rate, active flows, avg duration) with sparklines, running transactions list, and recent transactions table. No customization, no date range selector, no drilldown.

**Industry standard:** Workato Insights offers customizable dashboards with drag-and-drop chart components. MuleSoft API Manager provides real-time network visualization. Modern dashboards support date range filters, saved views, and widget rearrangement.

**Recommendation:** Add date range picker, filterable charts, expandable KPI cards with trend details, and a "customize dashboard" mode for widget arrangement.

#### 🟡 GAP 6: No Connection/Credential Health View

**Current state:** Credentials are managed in the Config Wizard. There is no overview of all connected systems, their health status, last successful sync time, or credential expiry warnings.

**Industry standard:** All competitors show a "Connections" page with live status indicators (green/yellow/red), last sync timestamp, credential validity, and quick re-authentication.

**Recommendation:** Build a Connections overview page showing each configured system (Salesforce, QuickBooks, Creatio, etc.) with status badges, last sync time, and a "Test Connection" button.

#### 🟡 GAP 7: Error Management is Primitive

**Current state:** Errors appear in the monitoring transaction history and system logs. No intelligent error grouping, no suggested fixes, no retry mechanisms.

**Industry standard:** Celigo's error management automation groups similar errors, suggests resolutions, and supports automated retries. n8n provides per-node error inspection with data snapshots.

**Recommendation:** Create an Error Dashboard that groups errors by type/source, shows frequency trends, suggests common fixes, and offers retry/skip actions.

#### 🟡 GAP 8: Missing Breadcrumbs and Contextual Navigation

**Current state:** The sidebar shows which section you're in (active state), but there are no breadcrumbs, no "where am I" indicators within deep pages, and the config wizard doesn't show progress in the sidebar.

**Recommendation:** Add breadcrumb navigation for nested pages (Company → Config → Wizard → Step 3). Show wizard progress as a persistent mini-stepper in the sidebar when on config pages.

#### 🟡 GAP 9: No In-App Help System

**Current state:** No contextual help, no tooltips on complex fields, no documentation browser, no HelpLinkService integration in the React portal (though one exists in the Maven source).

**Industry standard:** Most enterprise platforms include `?` icons on complex fields, a help sidebar with contextual documentation, and a searchable knowledge base.

**Recommendation:** Implement a `HelpPanel` component that provides contextual documentation for the current page. Add `InfoTooltip` components on configuration fields.

#### 🟡 GAP 10: Limited Mobile Experience

**Current state:** The AppShell collapses to a mobile drawer menu. Pages are responsive but not mobile-optimized. No mobile-specific layouts or touch interactions.

**Industry standard:** Workato's Workbot integrates into mobile chat tools. Modern enterprise UX expects functional mobile views for monitoring and alerts at minimum.

**Recommendation:** Prioritize mobile optimization for: Dashboard (monitoring KPIs), Notifications (alert triage), and Monitoring (transaction status).

### 3.3 Polish Gaps (Lower Priority — Design Refinement)

#### 🔵 GAP 11: Typography Is Generic

**Current state:** System font stack (`ui-sans-serif, system-ui`). No branded display font. All text uses the same family.

**Brand guideline context:** The "Inter" / "Weave" brand split-weight treatment suggests a distinctive typographic identity, but it hasn't carried into the portal UI.

**Recommendation:** Introduce a branded display font for headings (candidates: Plus Jakarta Sans, Outfit, General Sans, or Geist) while keeping the system stack for body text. This is a quick win for brand distinction.

#### 🔵 GAP 12: No Micro-Interactions or Motion Design

**Current state:** Minimal transitions. Theme toggle swaps. Tab switching is instant. No loading skeletons on non-lazy pages. Sparklines are static SVG.

**Industry standard:** Modern enterprise UX uses staggered reveal animations on page load, skeleton loading states, smooth transitions between views, and subtle hover effects that provide feedback.

**Recommendation:** Add: (a) skeleton loading states for all data-dependent cards, (b) staggered fade-in on dashboard cards, (c) smooth chart transitions on data refresh, (d) subtle scale/glow on hover for action buttons.

#### 🔵 GAP 13: No Keyboard Shortcuts Beyond Search

**Current state:** `/` focuses the search bar. No other keyboard shortcuts.

**Industry standard:** Enterprise platforms support `Cmd+K` command palette, `Escape` to close modals, arrow navigation in dropdowns, keyboard shortcuts for common actions.

**Recommendation:** Implement a proper `Cmd+K` command palette (separate from the search bar) that supports actions like "Go to Monitoring", "Create new flow", "Open config wizard". The search bar infrastructure in Topbar.tsx already has the navigation items — it just needs the keyboard shortcut and overlay modal.

#### 🔵 GAP 14: Notification Center Needs Depth

**Current state:** NotificationsPage exists with unread count badge. Basic notification list.

**Industry standard:** Grouped notifications by type/source, inline actions (dismiss, view details, take action), notification preferences per category, email/Slack delivery options.

**Recommendation:** Add notification grouping, inline action buttons, and a notification preferences panel.

#### 🔵 GAP 15: Classic View Banner Creates Friction

**Current state:** Every page shows a "Switch to Classic" banner. This is useful for migration but adds visual noise.

**Recommendation:** Make the banner dismissable and add a "Prefer Classic?" toggle in user preferences instead. Show the banner only on first visit to each page.

---

## 4. User Flow Analysis

### 4.1 Current User Flows — Strengths

- **Login → Dashboard** is clean and modern. The split-panel login with feature showcase and demo credentials is above average for enterprise software.
- **Monitoring → Transactions → Transaction Detail** is well-structured with pagination and status badges.
- **Config Wizard** uses a proper multi-step flow (5 steps: solution type, mappings, credentials, execution settings, review).
- **Theme toggle** (dark/light/system) is well-implemented and persisted.
- **Classic View escape hatch** is smart for incremental migration — users are never trapped in the new UI.

### 4.2 Current User Flows — Weaknesses

- **Post-login discovery is poor.** New user logs in → sees KPI dashboard with zeros → doesn't know what to do next. No CTA, no guided path.
- **Config Wizard is buried.** Path: Dashboard → Sidebar "Configuration" → Company Config page → "Open Configuration Wizard" button → Wizard. That's 3–4 clicks to reach the core setup flow.
- **Admin flow is scattered.** The admin needs to visit Integrations (flow management), Logging (server logs), and Audit (activity log) as separate pages. No unified admin console.
- **No flow debugging path.** When a transaction fails, user sees the failure in Monitoring → clicks the transaction → sees status and timestamps → … then what? No link to flow configuration, no error detail, no retry option.
- **Profile/Security is split across 3 pages.** Profile, Change Password, and MFA Setup are separate routes. This could be unified into a tabbed profile page.

### 4.3 Recommended User Flow Improvements

**New User Onboarding Flow:**
```
Login → Welcome Modal (first time) → Dashboard with Setup Checklist
  → Step 1: Complete Company Profile (→ /company)
  → Step 2: Configure Integration (→ /company/config/wizard)
  → Step 3: Test Connection (→ /admin/configurator)
  → Step 4: Run First Sync (→ /monitoring)
```

**Error Investigation Flow (currently broken):**
```
Monitoring → Failed Transaction → Error Detail Panel
  → Suggested Fix
  → Link to Flow Configuration
  → Retry Button
  → View Related Logs
```

**Admin Quick Actions:**
```
Dashboard → Admin Quick Actions Card
  → Start/Stop Engine (1-click)
  → View Active Alerts
  → Open Latest Log Entry
  → Jump to Config Wizard
```

---

## 5. Feature Prioritization Matrix

| Feature | Impact | Effort | Priority |
|---------|--------|--------|----------|
| Welcome/Onboarding Flow | High | Low | **P0 — Do First** |
| Dashboard Setup Checklist | High | Low | **P0 — Do First** |
| Cmd+K Command Palette | Medium | Low | **P0 — Quick Win** |
| Branded Typography | Medium | Low | **P0 — Quick Win** |
| Skeleton Loading States | Medium | Low | **P0 — Quick Win** |
| Connection Health Overview | High | Medium | **P1 — Next Sprint** |
| Error Dashboard with Grouping | High | Medium | **P1 — Next Sprint** |
| Breadcrumb Navigation | Medium | Low | **P1 — Next Sprint** |
| In-App Help Tooltips | Medium | Medium | **P1 — Next Sprint** |
| Dashboard Date Range & Filters | Medium | Medium | **P2 — Near Term** |
| Notification Preferences | Low | Medium | **P2 — Near Term** |
| Mobile-Optimized Monitoring | Medium | Medium | **P2 — Near Term** |
| Visual Flow Viewer (Read-Only) | High | High | **P2 — Near Term** |
| AI Assistant Sidebar | High | High | **P3 — Strategic** |
| Visual Workflow Builder (Edit) | Critical | Very High | **P3 — Strategic** |
| Visual Field Mapping Studio | Critical | Very High | **P3 — Strategic** |
| Customizable Dashboard Widgets | Medium | High | **P3 — Strategic** |

---

## 6. Design System Recommendations

### 6.1 Strengthen the ASSA Foundation

The ASSA design language is solid and well-implemented. Recommended enhancements:

**Card Hierarchy System:**
- **Hero Card** — Full-width, gradient background, used for KPI highlights and CTAs
- **Standard Card** — `glass-panel` with `rounded-[var(--radius)]`, 14px padding, for data display
- **Interactive Card** — Standard card + hover state (border glow, slight lift), for clickable items
- **Compact Card** — Reduced padding, tighter typography, for list items and notifications

**Status Color System:**
Formalize the status language across the entire app:
- 🟢 Green (`--success`) — Healthy, success, active, connected
- 🔵 Blue (`--primary`) — Info, in-progress, default action
- 🟡 Amber (`--warning`) — Warning, degraded, expiring
- 🔴 Red (`--destructive`) — Error, failed, disconnected, critical
- ⚪ Gray (`--muted`) — Inactive, pending, unknown

**Empty State Pattern:**
Every data view needs an empty state with illustration, explanation, and CTA:
```
[Icon/Illustration]
No transactions yet
Run your first integration to see data here.
[Open Config Wizard →]
```

### 6.2 Component Additions Needed

Based on cross-referencing competitors and the InterWoven prototype:

- **Stepper / ProgressBar** — For multi-step flows (config wizard, onboarding)
- **CommandPalette** — Cmd+K overlay with fuzzy search
- **EmptyState** — Standardized empty state component
- **ConnectionStatusBadge** — Animated dot + system icon + status text
- **SkeletonCard / SkeletonTable** — Loading placeholder components
- **InfoTooltip** — `?` icon with popover documentation
- **BreadcrumbNav** — Page hierarchy indicator
- **Timeline** — For activity logs, transaction history
- **StatusTimeline** — Vertical progress for multi-stage flows

---

## 7. Specific Page-Level Recommendations

### 7.1 Login Page
**Current:** Excellent. One of the strongest pages. Feature showcase, demo credentials, MFA support.
**Improve:** Add the Infinity Circuit logo icon instead of the generic Zap icon. Add a "What's new" link for returning users.

### 7.2 Dashboard Page
**Current:** 4 KPI cards with sparklines, running transactions, recent transactions table.
**Improve:**
- Add a **Setup Progress** card for new users showing onboarding completion
- Add **Quick Actions** card (Start Engine, Open Wizard, View Alerts)
- Add **date range selector** to scope KPI data
- Add **Connection Health** summary row showing each connected system's status
- Make KPI cards clickable (link to relevant detail page)
- Add skeleton loading states

### 7.3 Monitoring Page
**Current:** Charts + transactions + alerts as sub-routes. Well-structured.
**Improve:**
- Add **date range picker** (last 1h, 6h, 24h, 7d, custom)
- Add **flow filter** dropdown to scope to specific integration
- Add **error grouping** view (group by error type, show count + last occurrence)
- Add **retry** action on failed transactions
- Add drilldown from chart data points to transaction list

### 7.4 Config Wizard Page
**Current:** 5-step wizard (solution type, mappings, credentials, execution, review).
**Improve:**
- Show **step progress** in sidebar while on wizard pages
- Add **contextual help** tooltips on each configuration field
- Add **"Save & Continue Later"** with draft state
- Add **validation preview** before saving each step
- For the mapping step specifically: build toward a **visual field mapper** (drag source → destination)

### 7.5 Integrations/Configurator Page
**Current:** Flow table, engine controls tab, flow properties dialog, schedule editor.
**Improve:**
- Add **flow status cards** at the top (X active, Y paused, Z errored)
- Add **flow dependency visualization** (which flows depend on which connections)
- Add **bulk actions** (start all, stop all, reschedule selected)
- Long-term: Add **visual flow viewer** that shows the integration flow as a node graph

### 7.6 Profile / Security Pages
**Current:** Separate pages for profile, password change, and MFA setup.
**Improve:** Unify into a **tabbed profile page** (General, Security, Notifications, Preferences) to reduce navigation overhead.

---

## 8. Accessibility & Standards Checklist

| Criteria | Current Status | Action Needed |
|----------|---------------|---------------|
| Color contrast (WCAG AA) | ⚠️ Some muted text may fail | Audit all `text-muted-foreground` against dark backgrounds |
| Keyboard navigation | ✅ Tab navigation works | Add `aria-label` to all icon-only buttons |
| Screen reader support | ⚠️ Not tested | Add `aria-live` regions for toast notifications and real-time updates |
| Focus indicators | ✅ shadcn/ui provides ring styles | Verify visibility in both themes |
| Skip navigation | ❌ Missing | Add "Skip to main content" link |
| Reduced motion | ❌ Missing | Add `prefers-reduced-motion` media query respecting |
| Error announcements | ⚠️ Partial | Ensure form errors use `aria-describedby` |
| Responsive touch targets | ✅ Buttons meet 44px minimum | Verify all interactive elements |

---

## 9. Performance Recommendations

- **Code-split the ConfigurationWizardPage** — Already noted in CLAUDE.md as medium-term. Prioritize this as it's ~80kB.
- **Add Route-level loading indicators** — The `LazyFallback` spinner is minimal. Consider a top-bar progress indicator (NProgress style).
- **Optimize Recharts bundle** — Currently lazy-loaded, good. Consider switching to a lighter charting library (Chart.js) for the dashboard sparklines.
- **Image optimization** — Ensure any future images (logos, illustrations for empty states) use next-gen formats (WebP/AVIF).
- **Service Worker for offline monitoring** — Cache the last dashboard state so users see something immediately while data refreshes.

---

## 10. Summary — The Three Horizons

### Horizon 1: Foundation Polish (Next 2–4 Weeks)
Quick wins that immediately improve perceived quality:
- Branded display font for headings
- Skeleton loading states on all data pages
- Cmd+K command palette
- Breadcrumb navigation
- Dashboard setup checklist for new users
- Welcome modal / onboarding nudge
- Dismissable ClassicView banner
- Empty state components for all data views

### Horizon 2: Functional Depth (Next 1–3 Months)
Features that close the gap with competitor baselines:
- Connection Health overview page
- Error Dashboard with grouping and retry
- Dashboard date range selector and filters
- In-app help tooltips on config fields
- Unified tabbed profile page
- Mobile-optimized monitoring view
- Read-only visual flow viewer (display integration flows as node graphs)
- Notification preferences and grouping

### Horizon 3: Strategic Differentiation (3–6+ Months)
Features that position InterWeave as a modern integration platform:
- AI Assistant sidebar (Claude-powered mapping suggestions, error analysis, documentation Q&A)
- Visual Workflow Builder with drag-and-drop editing
- Visual Field Mapping Studio
- Customizable dashboard with widget arrangement
- Template library for common integration patterns
- Embedded automation in Slack/Teams
- Real-time collaborative editing for flow configurations
- Advanced analytics and reporting page

---

*This analysis is a living document. As features are implemented, each gap should be re-evaluated against the competitive landscape and user feedback.*
