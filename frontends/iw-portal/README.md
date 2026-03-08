# IW Portal — InterWeave Modern React UI

Modern React-based portal that incrementally replaces the classic JSP interface for the InterWeave IDE platform.

## Stack

- **Vite 7** — build tool + dev server
- **React 19** — UI framework
- **TypeScript** — strict mode, zero errors required
- **Tailwind CSS 4** — utility-first styling
- **shadcn/ui** — Radix-based component library (10 components: Button, Input, Label, Badge, Select, Tabs, Dialog, Switch, Separator, Tooltip)
- **TanStack Query v5** — server state management with auto-refresh
- **React Router v7** — client-side routing
- **Recharts 3** — charting library (monitoring dashboards)

## Quick Start

```bash
# Development (Vite dev server on :5173, proxies to Tomcat :9090)
npm install
npm run dev

# Production build (outputs to ../../web_portal/tomcat/webapps/iw-portal/)
npm run build

# Type check
npx tsc --noEmit
```

## Architecture

### Providers (src/providers/)
- **AuthProvider** — session check + login/logout via `/api/auth/*`
- **ThemeProvider** — ASSA dark palette (default) + light mode, persisted to localStorage
- **QueryProvider** — TanStack Query client with defaults
- **ToastProvider** — shared notification system

### Pages (src/pages/)
| Route | Page | Description |
|---|---|---|
| `/login` | LoginPage | Redesigned auth form with shadcn/ui |
| `/register` | RegisterPage | User self-registration |
| `/register/company` | CompanyRegisterPage | Company + admin registration |
| `/dashboard` | DashboardPage | 4 KPI cards, recent transactions |
| `/monitoring` | MonitoringPage | Recharts area/bar charts, KPI cards |
| `/monitoring/transactions` | TransactionHistoryPage | Paginated, filterable table |
| `/monitoring/alerts` | AlertConfigPage | Alert rules CRUD |
| `/profile` | ProfilePage | Personal info form |
| `/profile/password` | ChangePasswordPage | Password change |
| `/company` | CompanyPage | Company admin form + KPI bar |
| `/company/config` | CompanyConfigPage | 5-step progress checklist |
| `/company/config/wizard` | ConfigurationWizardPage | 5-step config wizard |
| `/admin/configurator` | IntegrationOverviewPage | Flows, credentials, engine controls |
| `/admin/logging` | LoggingPage | Log targets, system status |

### Hooks (src/hooks/)
- **useMonitoring** — `useDashboard` (30s auto-refresh), `useTransactions` (pagination), `useMetrics`, `useAlertRules`
- **useFlows** — `useEngineFlows` (10s auto-refresh), `useStartFlow`, `useStopFlow`, `useUpdateFlowSchedule`, `useFlowProperties`, `useSaveFlowProperties` (with post-save verification), `useSubmitFlows`
- **useConfiguration** — `useWizardConfig`, `useSaveWizardConfig`, `useCredentials`, `useSaveCredential`, `useTestCredential`, `useProfiles`
- **useProfile** / **useCompanyProfile** — profile CRUD
- **useDocumentTitle** — dynamic `<title>` per page

### Key Features
- **Classic View**: "Switch to Classic" banner on every page (shared Tomcat session)
- **Mobile**: Hamburger menu with slide-out drawer
- **Command Palette**: "/" shortcut, 10+ indexed pages
- **Config Wizard**: Progressive disclosure, bulk actions, smart defaults, dependency warnings, config diff, draft persistence (sessionStorage), JSON export, test connection
- **Flow Properties**: React dialog for viewing/editing variable parameters per flow (text/password/upload types, read-only when running, post-save verification)
- **Dashboard Sparklines**: Transaction count, success rate, and avg duration KPI cards have per-bucket sparkline charts derived from transaction history
- **Global Error Toast**: MutationCache.onError handles 401 (session expired), 500+ (server errors), and network failures automatically
- **ASSA Theme**: Dark navy + aurora gradients, glass panels, 24px radius

## Known Limitations

- **Transformation Server**: The `iwtransformationserver` webapp is skeleton-only (no Java class JARs). Query flow "GO" buttons and scheduled flow execution return 404. Flow properties and configuration work normally — only actual flow *execution* requires the vendor JARs.
- **File Upload Properties**: Flow properties of type `upload` are filtered from the React dialog. Use the classic JSP for file uploads.
- **TransactionLogger**: Phase 1B — transaction logging filter is registered but has no traffic to intercept until transformation server JARs are deployed.

## Session Sharing

Both React and classic JSP pages share the same Tomcat `JSESSIONID` cookie (same origin on :9090). Login via React sets identical session attributes as the JSP login. Users can switch between React and classic views freely within the same session.

## Build Output

Production build outputs to `../../web_portal/tomcat/webapps/iw-portal/` which is served by Tomcat at `/iw-portal/`. This directory is gitignored — users must run `npm run build` after cloning.
