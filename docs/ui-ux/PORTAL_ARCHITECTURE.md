# IW Portal Architecture — Three-Portal System

**Created:** 2026-03-11
**Status:** Reference architecture — defines the three distinct portal surfaces and their relationship to the current React build

---

## Overview

The IW system has three distinct portal surfaces, each serving a different audience. This document maps what has been built, what exists as a static prototype, and how each surface relates to the `frontends/iw-portal/` React build.

```
┌─────────────────────────────────────────────────────────────────────┐
│                         IW Portal Surfaces                           │
├────────────────────┬────────────────────┬───────────────────────────┤
│  iw-portal         │  IW Associate      │  IW Master Console        │
│  (Integration      │  Portal            │  (Program Admin Console)  │
│   Operator Portal) │  (Customer-facing) │                           │
├────────────────────┼────────────────────┼───────────────────────────┤
│ Status: LIVE       │ Status: PROTOTYPE  │ Status: PROTOTYPE         │
│ frontends/iw-portal│ docs/ui-ux/        │ docs/ui-ux/               │
│                    │ iw_associate_portal│ iw_master_console         │
├────────────────────┼────────────────────┼───────────────────────────┤
│ Audience:          │ Audience:          │ Audience:                 │
│ Integration devs   │ ASSA associate     │ IW/ASSA program           │
│ & operators        │ owners/members     │ administrators            │
├────────────────────┼────────────────────┼───────────────────────────┤
│ Auth: Tomcat       │ Auth: TBD          │ Auth: TBD (role-gated)    │
│ session + Bearer   │                    │                            │
└────────────────────┴────────────────────┴───────────────────────────┘
```

---

## Portal 1 — iw-portal (Integration Operator Portal) — LIVE

**Location:** `frontends/iw-portal/` → deployed to `/iw-portal/` webapp and `https://iw-portal.vercel.app`

**Audience:** Integration developers and operators who build and monitor data synchronization flows (Salesforce ↔ QuickBooks, CRM ↔ ERP, etc.)

**22 pages (as of 2026-03-09):**

| Page | Route | Status |
|------|-------|--------|
| Login | `/login` | Live |
| Register | `/register` | Live |
| Company Register | `/register/company` | Live |
| Dashboard | `/dashboard` | Live — KPI cards, sparklines, setup checklist |
| Monitoring | `/monitoring` | Live — transaction chart, table, alerts |
| Transaction History | `/monitoring/transactions` | Live |
| Notifications | `/notifications` | Live — inbox with read/unread |
| Profile | `/profile` | Live — personal info + security |
| Change Password | `/profile/password` | Live |
| MFA Setup | `/profile/mfa` | Live |
| MFA Verify | `/mfa/verify` | Live |
| Forgot Password | `/forgot-password` | Live |
| Company | `/company` | Live |
| Company Config | `/company/config` | Live — progress checklist |
| Config Wizard | `/company/config/wizard` | Live — 5-step, ~80 object properties |
| Integrations | `/admin/configurator` | Live — flow table, engine controls |
| Field Mapping | `/admin/field-mapping` | Live — AI similarity scoring |
| Logging | `/admin/logging` | Live |
| Audit Log | `/admin/audit` | Live |
| Alert Config | `/admin/alerts` | Live |
| MFA Setup (admin) | Various | Live |
| Not Found | `*` | Live |

**API backend:** 16 Java API servlets (`/api/*`) + Tomcat session sharing with JSP classic view

---

## Portal 2 — IW Associate Portal — STATIC PROTOTYPE

**Location:** `docs/ui-ux/iw_associate_portal/iw_associate_portal/`
**Source:** ASSA design language — static HTML prototype, build date 2026-02-09

**Audience:** ASSA associates (business owners, members) who consume program resources, manage their account, access webinars, and get support.

**9 pages:**

| Page | File | Purpose |
|------|------|---------|
| Home | `index.html` | Welcome dashboard, recent resources, saved items, subscription status |
| Resource Library | `pages/library.html` | Browse content by category, filter by role |
| Search | `pages/search.html` | Full-text search across resources, webinars, templates |
| Business Checkup | `pages/intake.html` | Diagnostic intake form / self-assessment wizard |
| Webinars | `pages/webinars.html` | Upcoming + recorded video library |
| Notifications | `pages/notifications.html` | Program updates + alerts |
| Support | `pages/support.html` | Help tickets + knowledge base |
| Profile | `pages/profile.html` | Personal account settings |
| Billing | `pages/billing.html` | Subscription plan, payment history, upgrade |

**Login:** `login.html`

**Key design patterns introduced:**
- `span-3` 4-column grid layout (12-col base → 4-wide cards)
- `hero` section pattern (welcome banner with CTAs)
- `tile` component (content item cards within cards)
- Sparkline charts via `data-spark` attribute (pure JS, no chart lib needed)
- `.status` pattern with `.dot ok/warn/bad` inline status indicators
- 18px border-radius (`--radius:18px`, `--radius2:24px`) — slightly more rounded than current iw-portal

**Design token alignment with iw-portal:**
- Identical `--bg:#071021`, `--brand:#3b82f6`, `--brand2:#22c55e` color system
- Same glassmorphism sidebar + radial gradient background
- Same button, pill, table, and card primitives
- Minor variant: 260px sidebar vs 280px in master console

---

## Portal 3 — IW Master Console — STATIC PROTOTYPE

**Location:** `docs/ui-ux/iw_master_console/iw_master_console/`
**Source:** ASSA design language — static HTML prototype, build date 2026-02-06

**Audience:** IW/ASSA program administrators who manage all associates, content publishing, subscriptions, integration connectors, and program-wide analytics.

**10 pages:**

| Page | File | Purpose |
|------|------|---------|
| Dashboard | `index.html` | Program health: MRR, active subscribers, engagement, exceptions |
| Users & Roles | `pages/users.html` | Cross-org user management, role assignment |
| Content Library | `pages/content.html` | Publish, organize, tag program resources |
| Subscriptions | `pages/subscriptions.html` | Plan management, billing, churn metrics |
| Integrations | `pages/integrations.html` | Connector inventory, mapping/validation policy, retry queue |
| Notifications | `pages/notifications.html` | Broadcast messages, alert rules |
| Analytics | `pages/analytics.html` | MRR chart, engagement DAU/WAU, content adoption |
| Audit & Security | `pages/audit.html` | Role-based access log, security events |
| Support | `pages/support.html` | Ticket management across all orgs |
| Settings | `pages/settings.html` | Program-wide configuration |

**Key design patterns introduced:**
- `span-3` 4-column KPI grid (vs `span-4` 3-column in associate portal)
- 280px sidebar (wider for admin nav density)
- Spark chart JS (`data-spark` mini-chart attribute) rendered by `assets/js/app.js`
- Role-aware UI hints (admin-only badge, permission gating notes)
- KPI pattern: `.kpi` number + `.muted` delta + `.spark` chart in a single card

---

## CSS Design Token Comparison

| Token | iw-portal (Tailwind/CSS vars) | Associate Portal | Master Console |
|-------|-------------------------------|------------------|----------------|
| Background | `#071021` | `#071021` | `#0b1020` |
| Panel | `oklch(...)` via Tailwind 4 | `#0c1833` | `#111a33` |
| Brand | `oklch(...)` ~blue | `#3b82f6` | `#3b82f6` |
| Success | `oklch(...)` ~green | `#22c55e` | `#22c55e` |
| Radius | 14-24px | 18-24px | 16-22px |
| Sidebar width | 260px | 260px | 280px |
| Font | Plus Jakarta Sans | System UI | System UI |

**Conclusion:** All three surfaces share the same visual language. The ASSA token system is the foundation for all three. The iw-portal uses Tailwind 4 CSS variables to implement the same palette. No redesign needed — only feature additions.

---

## Incorporation Strategy

### What iw-portal can adopt from the new prototypes

The following patterns appear in the new prototypes and are candidates for addition to `frontends/iw-portal/`:

#### From Associate Portal

| Pattern | Current iw-portal state | Incorporation |
|---------|------------------------|---------------|
| Resource Library page | Not present | New route `/resources` — content browsing for associates |
| Business Checkup/Intake | Not present | Maps to Config Wizard (already exists) — could add intake-style self-assessment before wizard |
| Webinar/media library | Not present | New route `/resources/webinars` |
| Billing page | Not present | New route `/billing` — integrate with Stripe MCP |
| `hero` welcome section | DashboardPage has a heading but no hero | Add to DashboardPage: welcome banner with role-aware CTAs |
| `tile` content cards | Not present as pattern | Add as reusable component `<ContentTile>` |
| `span-3` 4-col KPI grid | Dashboard uses 3 KPI cards | Could add 4th KPI card (associate count or content items) |

#### From Master Console

| Pattern | Current iw-portal state | Incorporation |
|---------|------------------------|---------------|
| MRR / subscription KPIs | Not present | Add to admin Dashboard as collapsed "Program Health" section (Dev Mode or admin role) |
| `spark` JS chart primitive | Recharts Sparkline component exists | The CSS/JS spark is lighter — consider for inline use |
| Content management | Not present | New `/admin/content` route for publishing resources |
| Subscriber analytics | Not present | Extend MonitoringPage or add `/admin/analytics` |
| Integration connector inventory | IntegrationOverviewPage covers flows | Master console `integrations.html` adds connector-level (Salesforce, Stripe) view |
| 280px sidebar variant | 260px | Consider widening for admin-role users |

### Phased Adoption Plan

**Phase A — Design token/component alignment (1-2 hrs)**
- Add `hero` section component to DashboardPage
- Ensure all 4 KPI card slots are filled (currently 3)
- Confirm `--radius` tokens match across all card components

**Phase B — Associate pages (8-12 hrs)**
- `/resources` — Resource Library (cards + filter by category/role)
- `/resources/webinars` — Webinar listing (upcoming + recorded)
- `/billing` — Billing page (requires Stripe integration)
- Add `span-3` grid option to dashboard for 4-column layout

**Phase C — Master Console admin pages (12-16 hrs)**
- `/admin/content` — Content publishing (CMS-lite)
- `/admin/analytics` — Program analytics (MRR, engagement charts)
- `/admin/subscriptions` — Subscription management
- Role-aware sidebar (show Master Console nav items to program admins)

**Phase D — Role Architecture (4-8 hrs)**
- Add `role` field to user session (`operator` | `associate` | `admin`)
- Sidebar auto-configures nav groups based on role
- Single React app, three navigation "views"
- Route guard extends `ProtectedRoute` with role check

---

## Related Documents

- `docs/ui-ux/UI_UX_DESIGN_APPROACH.md` — Primary design playbook
- `docs/ui-ux/IMPLEMENTATION_PLAN.md` — Backend-aware phased rollout
- `docs/ui-ux/COMPETITIVE_LANDSCAPE_EXPANDED.md` — Competitive patterns reference
- `frontends/iw-portal/` — Current live React build
- `docs/assa-specs/` — ASSA specification documents
- `docs/ui-ux/prototype/IW_Portal_Prototype_v2.jsx` — React prototype artifact
