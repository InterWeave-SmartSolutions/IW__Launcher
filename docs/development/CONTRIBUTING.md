# Contributing to InterWeave IDE (IW_Launcher)

## Getting Started

### Prerequisites

- **Windows** (primary) or WSL2. Linux/Mac scripts exist but are less maintained.
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
2. Open `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
3. Login with `__iw_admin__` / `%iwps%`
4. Verify the affected pages load without errors
5. Check `web_portal/tomcat/logs/catalina.out` for exceptions

## Pull Request Process

1. Create a feature branch from `main`.
2. Make small, focused changes. One concern per PR.
3. Ensure `mvn clean verify` passes.
4. Verify manually if the change touches web portal or startup scripts.
5. Fill in the PR template (summary, test plan, known limitations).
6. CI must pass before merge.
7. At least one reviewer must approve.

## AI-Assisted Development

All AI agents (Claude Code, Cursor, ChatGPT, etc.) working in this repo **must** follow the mandatory workflow defined in **`AI_WORKFLOW.md`**. Key requirements:

- Read `CLAUDE.md` and `README.md` at session start.
- Run `git status` and `git diff` before making changes.
- Append a session entry to `AI_WORKLOG.md` after changes.
- Never commit `.env` or any secrets.
- Never read or reference the `InterWoven/` directory unless explicitly asked.

## Known Limitations

1. **Authentication**: Only the admin account (`__iw_admin__` / `%iwps%`) works. The compiled `LoginServlet.class` uses a proprietary password hash format. Custom user registration completes but login fails. Source for the hash is unavailable.

2. **MonitoringContextListener**: Disabled in `web.xml` because it depends on `javax.mail` which is not bundled. Re-enabling requires adding `javax.mail.jar` to `WEB-INF/lib/`.

3. **Windows-centric**: Primary scripts are `.bat` files. Linux/Mac equivalents in `scripts/` are less tested.

## Questions?

Open an issue on [GitHub](https://github.com/InterWeave-SmartSolutions/IW__Launcher/issues) or contact the maintainers.
