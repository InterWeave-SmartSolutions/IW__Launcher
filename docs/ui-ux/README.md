# UI/UX Documentation

This directory contains the UI/UX strategy, competitive analysis, and implementation planning for the IW-Portal modernization effort.

## Start Here

**→ `UI_UX_DESIGN_APPROACH.md`** — The unified design playbook. Read this first. It synthesizes all research, competitive findings, and constraints into one actionable reference with 5 design principles, 11 feature-area approaches with concrete component specs, a per-feature design checklist, the full navigation/IA map, and a competitor-to-feature lookup table.

## All Documents

| File | Description | Role |
|------|-------------|------|
| **`UI_UX_DESIGN_APPROACH.md`** | Unified design playbook: 5 principles, 11 feature-area approaches with component specs, design checklist, navigation map, competitor-to-feature reference table. | **Primary reference — consult for every UI decision** |
| `UI_UX_ANALYSIS.md` | Deep-dive analysis: brand integration, ASSA/InterWoven references, initial competitive landscape (7 iPaaS), 15-gap analysis across 3 priority tiers, user flow analysis, feature prioritization matrix, design system recommendations, accessibility checklist, 3-horizon roadmap. | Background research |
| `COMPETITIVE_LANDSCAPE_EXPANDED.md` | Expanded research across 50+ platforms in 10 market categories. Detailed profiles of 23 platforms. Market-wide UI/UX patterns by adoption tier. Prioritized pattern adoption list. | Background research |
| `IMPLEMENTATION_PLAN.md` | Backend-aware phased rollout: architecture data flow map, session sharing rules, API contract inventory (28 endpoints), 5-phase plan, risk/rollback matrix, testing protocol. | Execution reference |
| **`PORTAL_ARCHITECTURE.md`** | Three-portal system architecture: iw-portal (live, 22 pages), Associate Portal (prototype, 9 pages), Master Console (prototype, 10 pages). Design token comparison, phased adoption plan (Phases A-D). | **Architecture reference for multi-portal work** |

## Static HTML Prototypes (New)

Two new prototype directories provide the target UI for future portal surfaces. Both use the same ASSA design tokens as `iw-portal`.

| Directory | Portal | Pages | Build Date | Key Patterns |
|-----------|--------|-------|------------|--------------|
| `iw_associate_portal/` | Associate Portal (customer-facing) | 9 | 2026-02-09 | Hero section, tile cards, intake wizard, resource library, billing |
| `iw_master_console/` | Master Console (program admin) | 10 | 2026-02-06 | 4-col KPI grid, SVG sparklines (`data-spark`), operational queue, analytics funnel |

**Serve locally:** `cd iw_associate_portal/iw_associate_portal && python -m http.server 8080`

**Design token alignment:** Both prototypes share `--bg:#071021`, `--brand:#3b82f6`, `--brand2:#22c55e`, glassmorphism sidebar, 12-col grid. The iw-portal implements the same palette via Tailwind 4 CSS variables. See `PORTAL_ARCHITECTURE.md` for the full token comparison table.

## Related Documents

- `docs/development/UI_CROSS_REFERENCE.md` — Classic JSP → React migration coverage matrix
- `docs/ai/UI_MODERNIZATION_NOTES.md` — Original UI modernization strategy notes (2026-02-27)
- `frontends/assa/` — ASSA static HTML design prototypes (design reference)
- `frontends/InterWoven/` — InterWoven React prototype (aspirational feature reference)
- `frontends/iw-portal/` — Current React portal implementation

## Reading Order

1. **`UI_UX_DESIGN_APPROACH.md`** — The playbook. Start and end here for day-to-day decisions.
2. `UI_UX_ANALYSIS.md` — For context on why the approach was chosen (gap analysis, user flow weaknesses).
3. `COMPETITIVE_LANDSCAPE_EXPANDED.md` — When you need to study how a specific competitor handles a specific feature.
4. `IMPLEMENTATION_PLAN.md` — When you're ready to code and need to verify backend safety for your change.
