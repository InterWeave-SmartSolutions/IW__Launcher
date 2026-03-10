# IW-Portal Interactive Prototype

## Files

- `IW_Portal_Prototype_v2.jsx` — The main prototype (React/JSX artifact)

## How to Use

This is a **Claude artifact** — paste the `.jsx` file content into a new Claude chat as a React artifact, or run it in any React playground that supports JSX + Tailwind.

The prototype mirrors the actual `frontends/iw-portal/src/` structure:

### What's Included

| Screen | Matches | Features |
|--------|---------|----------|
| **Login** | `LoginPage.tsx` | Split-panel with branding + auth form, platform badges, feature list |
| **AppShell** | `AppShell.tsx` | 260px sidebar + main grid, topbar, ClassicViewBanner |
| **Sidebar** | `Sidebar.tsx` | Exact NAV_ITEMS, 4 groups, brand area, context pill, footer |
| **Topbar** | `Topbar.tsx` | Search bar (Cmd+K), user pill, notification badge, theme toggle, Dev Mode toggle, logout |
| **Dashboard** | `DashboardPage.tsx` | KPI cards with sparklines, setup checklist, recent transactions table, quick actions |
| **Monitoring** | `MonitoringPage.tsx` | Date range pills, chart area, transaction table with status badges |
| **Company Config** | `CompanyConfigPage.tsx` | Breadcrumb, progress bar, step list with icons/checkmarks, wizard CTA |
| **Config Wizard** | `ConfigurationWizardPage.tsx` | 5-step stepper, object mapping toggles with direction selection |
| **Integrations** | `IntegrationOverviewPage.tsx` | Status summary cards, flow table with start/stop, flow state indicators |
| **Connections** | *(new — Dev Mode)* | Connection cards with health status, test/edit buttons, schema info |
| **Profile** | `ProfilePage.tsx` | Personal info form, security section |
| **Company** | `CompanyPage.tsx` | Organization details form |
| **Templates** | *(new — Dev Mode)* | Integration pattern cards with "Use Template" button |
| **Command Palette** | *(new)* | Cmd+K overlay with fuzzy search, keyboard nav |
| **Transaction Trace** | *(new — Dev Mode)* | Step-level timeline drawer (ONEiO pattern) |
| **Welcome Modal** | *(new)* | First-login onboarding overlay |

### Developer Mode

Toggle the `⚡ Dev` pill in the topbar. Additive panels appear on:
- **Dashboard** → Engine health detail
- **Monitoring** → Transaction trace column
- **Config Wizard** → Field-level mapping studio + AI suggest button
- **Integrations** → Graph view toggle + engine controls
- **Connections** → Schema browser per connection
- **Sidebar** → Connections, Templates nav items

### CSS System

Uses the exact ASSA token system from `index.css`:
- `--primary: 217 91% 60%` (blue)
- `--success: 142 71% 45%` (green)
- `--warning: 38 92% 50%` (amber)
- `--destructive: 0 84% 60%` (red)
- Glass panels, radial gradient background, 14-18px radii
- Plus Jakarta Sans headings, system UI body text
