# Contributing to InterWeave IDE (IW_Launcher)

## Getting Started

### Prerequisites

- **Windows** is the primary supported runtime path. Linux/Mac scripts exist, but the bundled portal/IDE workflow is maintained for Windows first.
- **Git LFS** -- required before cloning. Many binaries (`.exe`, `.jar`) are LFS-tracked.
  ```bash
  git lfs install
  git clone https://github.com/InterWeave-SmartSolutions/IW__Launcher.git
  git lfs pull
  ```
  Verify LFS worked: `jre/bin/java.exe` and `web_portal/tomcat/lib/catalina.jar` should NOT be tiny text files.
- **JDK 8+** for compilation (the bundled `jre/` is runtime-only).
- **Apache Maven 3.6+** for building the error framework and running tests.

### First Run

```bash
./START.bat          # Windows -- auto-creates .env from .env.example
```

Login: `__iw_admin__` / `%iwps%`

See `README.md` for full setup details.

## Development Setup

### Maven Build (error framework, validators, tests)

```bash
mvn clean compile    # compile
mvn test             # unit tests
mvn verify           # unit + integration tests
```

Full build instructions: **`BUILD.md`**

### Servlet Compilation

Individual servlets under `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/` are compiled with:

```bash
./compile_servlet.bat   # Windows
./compile_servlet.sh    # Linux/WSL
```

### Deployment

After building, copy classes to Tomcat:

```bash
cp -r target/classes/* web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/
```

Then restart Tomcat via `scripts/stop_webportal.bat` and `scripts/start_webportal.bat`.

## Protected Directories

These directories MUST NOT be modified:

| Directory | Reason |
|---|---|
| `plugins/iw_sdk_1.0.0/` | Proprietary InterWeave SDK plugin -- no source available |
| `jre/` | Bundled Java 8 runtime -- managed by LFS |
| `configuration/` | Eclipse runtime configuration -- auto-generated |
| `workspace/` | User project data -- never overwrite |

### Careful-Change Directories

| Directory | Rule |
|---|---|
| `web_portal/` | Additive changes only. Do not remove existing servlets, JSPs, or config. |
| `scripts/` | Extend existing script patterns. Do not rename or restructure. |

## Branching and Commits

### Branch Naming

```
feature/<description>    # new features
fix/<description>        # bug fixes
docs/<description>       # documentation only
chore/<description>      # maintenance, dependencies
```

Branch from `main`. Keep branches short-lived.

### Commit Messages

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: add connection pooling to LoginServlet
fix: correct SQL dialect for Supabase Postgres
docs: update BUILD.md with servlet compile steps
chore: update Tomcat to 9.0.84
```

## Testing

### Automated

```bash
mvn test                    # unit tests
mvn test -P all-tests       # include integration tests
mvn verify                  # full verification
```

### Manual Verification

After any web portal change:

1. Start Tomcat: `scripts/start_webportal.bat`
2. Open `http://localhost:9090/iw-business-daemon/IWLogin.jsp`
3. Login with `__iw_admin__` / `%iwps%`
4. Verify the affected pages load without errors
5. Check `web_portal/tomcat/logs/catalina*.log` for exceptions

## Accessibility (WCAG 2.2 AA) Checklist

The React portal targets WCAG 2.2 Level AA. When modifying `frontends/iw-portal/`, verify:

- [ ] **Keyboard navigation**: All interactive elements reachable via Tab, operable via Enter/Space, dismissible via Escape
- [ ] **Focus visible**: `focus-visible:ring-2` on all interactive elements (Button, Input, Select already have it)
- [ ] **ARIA attributes**: Forms have `htmlFor`/`id` label bindings, errors use `role="alert"`, modals use `role="dialog"` + `aria-modal`
- [ ] **Color contrast**: Text meets 4.5:1 on background (use `--primary` at `197 100% 36%` in light mode, not lighter)
- [ ] **Reduced motion**: Animations wrapped in `prefers-reduced-motion` check (global CSS rule in `index.css`)
- [ ] **Screen readers**: Decorative icons have `aria-hidden="true"`, status updates use `aria-live="polite"`

## Pull Request Process

1. Create a feature branch from `main`.
2. Make small, focused changes. One concern per PR.
3. Ensure `mvn clean verify` passes.
4. Verify manually if the change touches web portal or startup scripts.
5. Run `node node_modules/typescript/bin/tsc -b --noEmit` for React portal changes.
6. Fill in the PR template (summary, test plan, known limitations).
7. CI must pass before merge.
8. At least one reviewer must approve.

## AI-Assisted Development

All AI agents (Claude Code, Cursor, ChatGPT, etc.) working in this repo **must** follow the mandatory workflow defined in **`docs/ai/AI_WORKFLOW.md`**. Key requirements:

- Read `CLAUDE.md` and `README.md` at session start.
- Run `git status` and `git diff` before making changes.
- Append a session entry to `AI_WORKLOG.md` after changes.
- Never commit `.env` or any secrets.
- Never read or reference the `InterWoven/` directory unless explicitly asked.

## Known Limitations

1. **Authentication**: The current supported runtime uses `LocalLoginServlet` / `ApiLoginServlet`. Admin login works locally, and DB-backed users work when their credentials exist in the active database. Prefer testing against the shared Supabase-backed profiles or the `Tester1` regression profile rather than assuming admin-only behavior.

2. **MonitoringContextListener**: Disabled in `web.xml` because it depends on `javax.mail` which is not bundled. Re-enabling requires adding `javax.mail.jar` to `WEB-INF/lib/`.

3. **Windows-centric**: Primary scripts are `.bat` files. Linux/Mac equivalents in `scripts/` are less tested.

## Questions?

Open an issue on [GitHub](https://github.com/InterWeave-SmartSolutions/IW__Launcher/issues) or contact the maintainers.
