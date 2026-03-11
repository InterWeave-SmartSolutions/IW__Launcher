# UI Incorporation Plan — feat/ui-portal-incorporation

**Branch:** `feat/ui-portal-incorporation`
**Created:** 2026-03-11
**Status:** Investigation complete — decisions pending before code begins
**Synthesizes:** Three-agent deep-dive of iw-portal codebase, both HTML prototypes, and design system docs

---

## What We Investigated

This plan was built by simultaneously analyzing:

1. **`frontends/iw-portal/src/`** — Full component/page/hook/type/route inventory of the current React build
2. **`docs/ui-ux/iw_associate_portal/`** — All 9 HTML prototype pages + CSS + JS, pattern-by-pattern extraction
3. **`docs/ui-ux/iw_master_console/`** — All 10 HTML prototype pages + CSS + JS, pattern-by-pattern extraction
4. **`docs/ui-ux/UI_UX_DESIGN_APPROACH.md`** — Full 6-principle design playbook + Developer Mode architecture + 5-phase rollout plan

---

## Current State Snapshot

| Aspect | Current Value |
|--------|--------------|
| Pages | 23 (5 public, 18 protected, 10 lazy-loaded) |
| Components (UI) | 11 shadcn/ui + 9 integration + layout |
| Role system | `isAdmin: boolean` only — no granular roles |
| Sidebar width | 260px |
| Border radius | `--radius: 1.125rem` (18px) |
| Dev Mode toggle | Not implemented |
| Command palette | Not implemented |
| Skeleton loading | Not implemented — spinner only |
| Plus Jakarta Sans | Not implemented |
| Sparkline KPI pattern | 3 of 4 KPI cards have sparklines |
| Status badge (.dot pattern) | Not implemented as reusable component |
| Hero section | Not implemented |

---

## Architecture Decisions Required (Decide Before Coding)

These are the four decisions that determine every subsequent choice. No code should be written until these are resolved.

---

### Decision 1 — Single App vs. Multiple Apps

**Question:** Do the Associate Portal and Master Console become routes inside `iw-portal`, or separate React apps?

**Option A — Single app, role-gated routes (RECOMMENDED)**
- All three surfaces live in `frontends/iw-portal/`
- User role determines which nav group and routes they see
- Same Vite build, same Tomcat session, same auth flow
- Sidebar auto-configures based on role
- Shared components across all surfaces
- Pros: One deploy, one auth, shared design system, easier maintenance
- Cons: Larger bundle (mitigated by lazy-loading), single point of failure for all surfaces

**Option B — Three separate React apps**
- `frontends/iw-portal/` (current, integration operators)
- `frontends/iw-associate-portal/` (new, customer-facing)
- `frontends/iw-master-console/` (new, program admins)
- Each has its own Vite build, deployment, auth check
- Pros: Clear separation, smaller per-app bundles, independent deployments
- Cons: Triplicate auth/layout boilerplate, harder to share components, 3 Tomcat webapps

**Option C — Single app, separate route trees (compromise)**
- One React app
- Route `/associate/*` renders Associate Portal layout
- Route `/admin/master/*` renders Master Console layout
- Each layout has its own sidebar/nav
- Lazy-loaded layout chunks keep initial bundle small

**Recommendation:** Option A with Option C's layout separation. Single app, but `AppShell` accepts a `portalType` prop (`operator | associate | master`) that switches the sidebar nav. Roles gate which portal a user sees on login.

---

### Decision 2 — Role Architecture

**Question:** How do we distinguish the three user types?

**Current state:** `User.isAdmin: boolean` — just admin vs. non-admin.

**Required roles:**
- `operator` — integration developer/ops (current iw-portal users)
- `associate` — ASSA customer/member (Associate Portal)
- `program_admin` — IW/ASSA program administrator (Master Console)

**Option A — Extend `isAdmin` with a `role` string field (RECOMMENDED)**

Backend changes needed:
1. Add `role` column to `users` table (Supabase migration)
2. Add `role` to `ApiSessionServlet` response
3. Add `role` to `ApiLoginServlet` response
4. Update `AuthProvider.tsx` to read `role` field
5. Update `User` type: add `role: 'operator' | 'associate' | 'program_admin'`

Frontend changes:
1. `AuthProvider` exposes `role` alongside `isAdmin`
2. Sidebar uses `role` to select nav group set
3. `ProtectedRoute` accepts optional `requiredRole` prop
4. Keep `isAdmin` for backward compat (derived: `role === 'program_admin'`)

**Option B — Derive role from existing `isAdmin` + new flag**
- Add `isAssociate: boolean` to session
- No DB migration needed — add column or use a lookup
- Simpler but less extensible

**Option C — No role system yet — build pages but no gating**
- Build the new pages, add them to the sidebar for all users
- Gate later once role system exists
- Quickest to start, but users see pages they shouldn't

**Recommendation:** Option A — proper `role` field. One small DB migration + 3 backend file changes = clean foundation for everything downstream.

---

### Decision 3 — Developer Mode First or Pages First?

**Question:** Do we implement the Dev Mode infrastructure before building new pages, or build pages and add Dev Mode later?

**Option A — Dev Mode infrastructure first (RECOMMENDED)**
- Create `DevModeProvider.tsx`, `useDevMode()` hook, topbar toggle
- Every new page and component knows from the start whether its content is Standard or Dev
- Prevents retrofitting Dev Mode onto completed pages later
- Takes ~2 hours

**Option B — Pages first, Dev Mode retrofitted later**
- Build new pages in Standard view only
- Add Dev Mode overlays in a follow-up pass
- Risk: Some page designs will need restructuring to support the additive pattern

**Recommendation:** Option A — implement Dev Mode toggle as the first commit on this branch. It's 2 hours that saves 10 hours later.

---

### Decision 4 — Phase A UI Polish First or Jump Straight to New Pages?

**Question:** Should we do the design token/polish work before or after building new pages?

**Phase A work (design token alignment, ~2-4 hrs):**
- Hero welcome section on Dashboard
- 4th KPI card (currently have 3, prototypes show 4)
- Skeleton loading states (replace all spinners)
- Plus Jakarta Sans typography
- Status badge component (`.dot ok/warn/bad` pattern)
- Command palette (Cmd+K)

**Argument for Phase A first:**
- Every new page benefits from the skeleton, status badge, and hero components immediately
- Typography and design token cleanup is easier to do before adding 15 new pages
- Lowest risk, highest visual impact

**Argument for skipping Phase A:**
- The existing portal already looks good
- Phase A delays new feature delivery

**Recommendation:** Do Phase A as the first code commit. These are foundational components that every subsequent page will use.

---

## Phased Code Plan (After Decisions Are Made)

### Phase 0 — Foundation (Commit 1) — ~2 hrs
*No user-facing features. Infrastructure only.*

**DevMode infrastructure:**
- `src/providers/DevModeProvider.tsx` — `localStorage`-persisted boolean context
- `src/hooks/useDevMode.ts` — consumer hook
- Topbar button: `⚡ Dev` pill alongside theme toggle
- Wrap `AppShell` with `DevModeProvider`

**File changes:**
- `src/providers/DevModeProvider.tsx` (new)
- `src/hooks/useDevMode.ts` (new)
- `src/components/layout/Topbar.tsx` (add Dev toggle)
- `src/providers/index.tsx` or `main.tsx` (add DevModeProvider)

---

### Phase A — Design Token & Component Polish (Commits 2-3) — ~4 hrs
*Pure frontend. Zero API changes. Highest visual impact.*

**New reusable components:**
- `src/components/ui/skeleton.tsx` — Skeleton loading (rect, circle, text variants)
- `src/components/ui/empty-state.tsx` — Empty state with icon + message + CTA
- `src/components/ui/status-badge.tsx` — `.dot` + label with ok/warn/bad/info color variants
- `src/components/ui/hero-section.tsx` — Gradient hero banner with CTAs
- `src/components/ui/kpi-card.tsx` — KPI number + label + trend + sparkline (extract from DashboardPage)
- `src/components/CommandPalette.tsx` — Cmd+K fuzzy search overlay (promote existing search)

**Typography:**
- `index.html` → add Google Fonts `Plus+Jakarta+Sans` link
- `src/index.css` → add `--font-display: 'Plus Jakarta Sans', sans-serif` + heading usage

**DashboardPage improvements:**
- Replace spinner with skeleton in loading state
- Promote KPI cards to `<KpiCard>` reusable component
- Add 4th KPI card (candidates: Active Connections, Active Associates, or Records/Hour)
- Add hero welcome section above KPIs

**Sidebar:**
- Widen to 280px (from 260px) — matches Master Console, better readability at admin density
- Update `AppShell` grid: `grid-cols-[280px_1fr]`

**CSS token audit:**
- Confirm `--radius: 1.125rem` = 18px (matches Associate Portal `--radius: 18px`) ✓
- Border-radius on card variants

**File changes (Phase A):**
- `src/components/ui/skeleton.tsx` (new)
- `src/components/ui/empty-state.tsx` (new)
- `src/components/ui/status-badge.tsx` (new)
- `src/components/ui/hero-section.tsx` (new)
- `src/components/ui/kpi-card.tsx` (new — extracted from DashboardPage)
- `src/components/CommandPalette.tsx` (new)
- `src/components/layout/AppShell.tsx` (sidebar width → 280px)
- `src/components/layout/Sidebar.tsx` (width → 280px)
- `src/components/layout/Topbar.tsx` (Cmd+K integration)
- `src/pages/DashboardPage.tsx` (skeleton, hero, kpi-card refactor, 4th KPI)
- `src/index.css` (Plus Jakarta Sans)
- `index.html` (font link)

---

### Phase B — Role System (Commit 4) — ~3 hrs
*One DB migration + 3 backend file changes + frontend type/provider update.*

**Backend (Java):**
- `database/migrations/003_add_user_role.sql` — Add `role VARCHAR(20) DEFAULT 'operator'` to `users` table
- `ApiSessionServlet.java` — Return `role` in session JSON
- `ApiLoginServlet.java` — Return `role` in login response
- Recompile API servlets

**Frontend:**
- `src/types/auth.ts` — Add `role: 'operator' | 'associate' | 'program_admin'` to `User`
- `src/providers/AuthProvider.tsx` — Read + expose `role`
- `src/components/ProtectedRoute.tsx` — Add `requiredRole?: string` prop
- `src/components/layout/Sidebar.tsx` — Add `portalType` prop + role-based nav group switching

**Sidebar nav structure (role-aware):**
```
operator: Platform, Account, Configuration, Administration (current 11 items)
associate: Home, Resources, Webinars, Support, Billing, Account (new items)
program_admin: Dashboard, Users, Content, Subscriptions, Integrations, Analytics, Audit, Settings (new items)
```

**File changes (Phase B):**
- `database/migrations/003_add_user_role.sql` (new)
- `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/.../ApiSessionServlet.java` (modified)
- `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/.../ApiLoginServlet.java` (modified)
- `src/types/auth.ts` (modified)
- `src/providers/AuthProvider.tsx` (modified)
- `src/components/ProtectedRoute.tsx` (modified)
- `src/components/layout/Sidebar.tsx` (modified)

---

### Phase C — Associate Portal Pages (Commits 5-8) — ~10 hrs
*New pages for associate/customer users. Uses existing API where possible.*

| Page | Route | New API needed? | Effort |
|------|-------|----------------|--------|
| `ResourceLibraryPage` | `/resources` | `GET /api/resources` (new) | 2 hrs |
| `ResourceDetailPage` | `/resources/:id` | `GET /api/resources/:id` (new) | 1 hr |
| `SearchResultsPage` | `/search` | `GET /api/resources/search` (new) | 1 hr |
| `WebinarListPage` | `/resources/webinars` | `GET /api/webinars` (new) | 1 hr |
| `BusinessIntakePage` | `/intake` | `POST /api/intake` (new) | 1 hr |
| `SupportTicketPage` | `/support` | `GET/POST /api/support/tickets` (new) | 2 hrs |
| `BillingPage` | `/billing` | Stripe MCP (external) | 2 hrs |

**Shared patterns used:** `<KpiCard>`, `<StatusBadge>`, `<EmptyState>`, `<Skeleton>`, `<HeroSection>`, card/tile layout from Phase A

**Note on new API servlets:** Resource Library, Webinars, Support Tickets, and Business Intake require new Java servlets + DB tables. These can be deferred by using mock/static data initially for the frontend. Add `// TODO: wire to /api/resources` comments.

---

### Phase D — Master Console Pages (Commits 9-13) — ~14 hrs
*New pages for program admin users. Most require new API endpoints.*

| Page | Route | New API needed? | Effort |
|------|-------|----------------|--------|
| `MasterDashboardPage` | `/master/dashboard` | Aggregate queries | 2 hrs |
| `UserManagementPage` | `/master/users` | `GET/PUT /api/admin/users` (extend existing) | 2 hrs |
| `ContentManagementPage` | `/master/content` | `GET/POST /api/content` (new) | 2 hrs |
| `SubscriptionManagementPage` | `/master/subscriptions` | Stripe MCP | 2 hrs |
| `ConnectorManagementPage` | `/master/integrations` | Extend `/api/flows` | 1 hr |
| `ProgramAnalyticsPage` | `/master/analytics` | Aggregate monitoring data | 2 hrs |
| `SecurityEventsPage` | `/master/audit` | Extend `/api/admin/audit` | 1 hr |
| `TenantSettingsPage` | `/master/settings` | `GET/PUT /api/admin/settings` (new) | 1 hr |
| `NotificationTemplatesPage` | `/master/notifications` | Extend notifications API | 1 hr |

---

## New Component Inventory (All Phases)

Components to create across all phases, in dependency order:

```
Phase 0:
  DevModeProvider.tsx
  useDevMode.ts

Phase A:
  ui/skeleton.tsx         ← used by everything
  ui/empty-state.tsx      ← used by every page
  ui/status-badge.tsx     ← used by tables throughout
  ui/kpi-card.tsx         ← used by dashboards
  ui/hero-section.tsx     ← used by dashboards + landing pages
  CommandPalette.tsx      ← used by Topbar

Phase B:
  (no new components, just role-aware wiring)

Phase C (Associate Portal):
  pages/ResourceLibraryPage.tsx
  pages/ResourceDetailPage.tsx
  pages/SearchResultsPage.tsx
  pages/WebinarListPage.tsx
  pages/BusinessIntakePage.tsx
  pages/SupportTicketPage.tsx
  pages/BillingPage.tsx
  hooks/useResources.ts
  hooks/useSupport.ts
  types/resources.ts

Phase D (Master Console):
  pages/MasterDashboardPage.tsx
  pages/UserManagementPage.tsx
  pages/ContentManagementPage.tsx
  pages/SubscriptionManagementPage.tsx
  pages/ConnectorManagementPage.tsx
  pages/ProgramAnalyticsPage.tsx
  pages/SecurityEventsPage.tsx
  pages/TenantSettingsPage.tsx
  pages/NotificationTemplatesPage.tsx
  hooks/useMasterConsole.ts
  types/master.ts
```

---

## Design Token Harmonization

Current `index.css` dark mode tokens vs. prototype values:

| Token | Current iw-portal | Target (harmonized) | Change needed? |
|-------|------------------|--------------------|-|
| Background | `222 68% 6%` (oklch) | Renders ~`#071021` | No — same visual value |
| Card | `222 56% 12%` | `#0c1833` | No — same visual value |
| Border | `0 0% 100% / 0.10` | `rgba(255,255,255,.10)` | No — identical |
| Brand | `217 91% 60%` | `#3b82f6` | No — same (Tailwind blue-500) |
| Success | `142 71% 45%` | `#22c55e` | No — same (Tailwind green-500) |
| Warning | `38 92% 50%` | `#f59e0b` | No — same (Tailwind amber-500) |
| Destructive | `0 84% 60%` | `#ef4444` | No — same (Tailwind red-500) |
| Sidebar width | 260px | 280px | **Yes — +20px** |
| Radius | `1.125rem` (18px) | 18px | No — identical |

**Conclusion:** Design tokens are already harmonized. The only visual change is sidebar width (+20px). No token migration needed.

---

## Files That Must NOT Be Modified

To preserve Classic View compatibility and session sharing:

- `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/web.xml` — Only add, never remove entries
- All `Local*.java` servlets — Don't modify existing servlet behavior
- Any existing `Api*.java` servlet response shapes — Only add new fields, never remove
- `src/lib/api.ts` `apiFetch` — Must keep `credentials: "include"` and Bearer token logic
- `src/lib/classic-routes.ts` — Must keep all existing JSP mappings

---

## Quick Reference: Prototype → React Component Mapping

| HTML pattern | React component | Location |
|-------------|----------------|----------|
| `.card` | `<section className="glass-panel">` | Already exists (implicit) |
| `.tile` | `<div className="...bg-white/5 rounded-[var(--radius)] border border-white/10 p-3">` | Inline (no component needed) |
| `.hero` | `<HeroSection>` | `ui/hero-section.tsx` (new) |
| `.kpi` | `<KpiCard>` | `ui/kpi-card.tsx` (new) |
| `.status` + `.dot` | `<StatusBadge>` | `ui/status-badge.tsx` (new) |
| `.spark` (JS) | `<Sparkline>` | Already exists in `components/Sparkline.tsx` |
| `.btn.primary` | `<Button>` with `variant="default"` | Already exists |
| `.btn.success` | `<Button>` with `variant="success"` | Already exists |
| `.table` | `<table className="w-full ...">` | Inline (pattern, no component) |
| `.form` + `.field` | React Hook Form + shadcn/ui `Input`/`Label` | Already exists pattern |
| `data-table-filter` | `useState` filter + Array.filter | Inline in page |
| Active nav | React Router `<NavLink>` | Already in Sidebar |

---

## Pre-Coding Checklist

Before the first code commit on this branch, confirm:

- [ ] **Decision 1** resolved: Single app (Option A+C) confirmed?
- [ ] **Decision 2** resolved: Role system approach confirmed?
- [ ] **Decision 3** resolved: Dev Mode infrastructure first?
- [ ] **Decision 4** resolved: Phase A polish before new pages?
- [ ] `git status` is clean on `feat/ui-portal-incorporation`
- [ ] TypeScript currently passes: `node node_modules/typescript/bin/tsc -b --noEmit`
- [ ] Agreed on sidebar width: 260px → 280px?
- [ ] Agreed on 4th KPI card content (what metric)?

---

## Effort Summary

| Phase | Description | Effort | Risk |
|-------|-------------|--------|------|
| 0 | Dev Mode infrastructure | 2 hrs | Zero |
| A | Design token polish + components | 4 hrs | Minimal |
| B | Role system | 3 hrs | Low (one DB migration) |
| C | Associate Portal pages (7 pages) | 10 hrs | Medium (new API servlets needed) |
| D | Master Console pages (9 pages) | 14 hrs | Medium-High (multiple new APIs) |
| **Total** | | **~33 hrs** | |

Phases 0, A, B can be completed within this sprint. Phases C and D span multiple sessions.
