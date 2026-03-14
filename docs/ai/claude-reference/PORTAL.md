# IW Portal (React UI) Reference

## Stack & Tooling
- **Stack**: Vite 7 + React 19 + TypeScript (strict) + Tailwind CSS 4 + shadcn/ui + TanStack Query v5 + React Router v7 + Recharts 3
- **Theme**: ASSA dark palette (default) + light mode, toggle in topbar, persisted to localStorage
- **Dev**: `cd frontends/iw-portal && npm run dev` → Vite on :5173, proxies `/iw-business-daemon` to Tomcat :9090
- **Build**: `node node_modules/vite/bin/vite.js build` → outputs to `web_portal/tomcat/webapps/iw-portal/`
  - **IMPORTANT**: The build output at `web_portal/tomcat/webapps/iw-portal/` IS tracked in git. After any production build, commit it: `git add web_portal/tomcat/webapps/iw-portal/ && git commit`
  - Fresh clones get the React portal working immediately (no npm install needed for users)
  - `npm` / `tsc` / `vite` are not on PATH — use `node node_modules/...` paths instead
- **TypeScript**: strict mode, zero errors required before commit (`node node_modules/typescript/bin/tsc -b --noEmit`)

## Accessibility (WCAG 2.2 AA)
Skip nav, route announcer, ARIA combobox search, focus traps on mobile sidebar, aria-live toast notifications, label bindings, `prefers-reduced-motion` support. Color contrast verified: `--primary` 5:1 on white (light), `--muted-foreground` 4.7:1 on dark bg. All Button/Input/Select components have `focus-visible:ring-2`.

## Security Headers
`iw-portal/WEB-INF/web.xml` has Tomcat `HttpHeaderSecurityFilter` (X-Frame-Options: DENY, X-Content-Type-Options: nosniff, X-XSS-Protection). HSTS disabled until HTTPS.

## Classic View Integration
- Every React route maps to its JSP equivalent. "Switch to Classic" banner on every page.
- Users can set "always classic" preference.
- **Hook Page Pattern**: Pages not yet rebuilt in React redirect to the corresponding JSP page.
- Both apps share Tomcat session cookies (same origin).

## SPA Route Mappings

All 41 routes mapped via `spa-fallback` servlet in `iw-portal/WEB-INF/web.xml` (returns HTTP 200 with `index.html`).

### Route → Classic JSP Mapping (22 operator + 9 associate + 10 master)

**Operator routes:**
- `/login` → `IWLogin.jsp`
- `/register` → `Registration.jsp`
- `/register/company` → `CompanyRegistration.jsp`
- `/forgot-password` → password reset flow (React-only)
- `/mfa/verify` → TOTP verification (React-only)
- `/dashboard` → `IWLogin.jsp` (post-login landing)
- `/monitoring` → `monitoring/Dashboard.jsp` (charts + transactions + alerts)
- `/monitoring/transactions` → transaction history with filtering/pagination
- `/monitoring/alerts` → alert configuration
- `/profile` → `EditProfile.jsp`
- `/profile/password` → `ChangePassword.jsp`
- `/profile/security` → MFA setup (React-only)
- `/company` → `EditCompanyProfile.jsp`
- `/company/config` → `CompanyConfiguration.jsp` (progress checklist)
- `/company/config/wizard` → config wizard (5-step: solution type, mappings, credentials, execution settings, review)
- `/admin/configurator` → `BDConfigurator.jsp` (flows, credentials, engine controls with toggleable live log panel)
- `/admin/logging` → `Logging.jsp`
- `/admin/audit` → audit log (React-only)
- `/notifications` → notification inbox (React-only)

**Associate routes:** `/associate/*` → home, resources, webinars, intake, support, billing, search

**Master routes:** `/master/*` → dashboard, users, content, subscriptions, integrations, analytics, audit, notifications, support, settings

## Key Directories

- `src/components/layout/` — AppShell, Sidebar, Topbar, ClassicViewBanner
- `src/components/` — ProtectedRoute (auth gate), layout/ (AppShell, Sidebar, Topbar, ClassicViewBanner)
- `src/pages/` — route pages
- `src/providers/` — ThemeProvider, QueryProvider, AuthProvider (session check + login/logout)
- `src/hooks/` — useMonitoring.ts (useDashboard with 30s auto-refresh, useTransactions with pagination), useConfiguration.ts (wizard/credentials/profiles/test), useFlows.ts (flow listing, start/stop, schedule, properties read/write with post-save verification)
- `src/lib/` — api.ts (fetch wrapper with ApiError class), classic-routes.ts, config-labels.ts (shared label formatters), utils.ts
- `src/types/` — TypeScript interfaces for API responses (monitoring.ts, flows.ts, config.ts)
- `src/components/integrations/` — FlowTable, EngineControlsTab (with toggleable LiveLogPanel — bottom/side/collapsed modes), FlowPropertiesDialog, EditScheduleDialog

## API Servlets (JSON, `com.interweave.businessDaemon.api`)

- **ApiLoginServlet** — `POST /api/auth/login`
- **ApiSessionServlet** — `GET /api/auth/session`
- **ApiLogoutServlet** — `POST /api/auth/logout`
- **ApiProfileServlet** — `GET/PUT /api/profile`
- **ApiCompanyProfileServlet** — `GET/PUT /api/company/profile`
- **ApiRegistrationServlet** — `POST /api/register`
- **ApiCompanyRegistrationServlet** — `POST /api/register/company`
- **ApiChangePasswordServlet** — `POST /api/auth/change-password`
- **ApiConfigurationServlet** — `GET/PUT /api/config/wizard`, `GET/PUT /api/config/credentials`, `GET /api/config/profiles`, `POST /api/config/credentials/test`
- **ApiFlowManagementServlet** — `GET /api/flows` (flow listing, filtered by company solution type), `GET /api/flows/properties` (flow variable parameters), `POST /api/flows/start|stop|submit|initialize`, `PUT /api/flows/schedule`
- **ApiLogViewerServlet** — `GET /api/logs/*`
- **ApiWorkspaceManagementServlet** — `GET /api/workspace/projects` (list all), `GET /api/workspace/projects/{name}` (detail), `GET /api/workspace/projects/{name}/config` (raw config.xml)
- **ApiBuildServlet** — `POST /api/build/compile-xslt` (compile XSLT→bytecode), `GET /api/build/inventory/{name}` (transformer inventory with stale detection)
