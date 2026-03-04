# InterWeave IDE System Readiness

## Status

Verified as of 2026-03-04 for the current supported team mode:

- `DB_MODE=supabase`
- `TS_MODE=local`

In that mode, a fresh Windows checkout can be brought up by:

1. Running `START.bat` once to create `.env`
2. Replacing `SUPABASE_DB_PASSWORD` in `.env` with the shared team password
3. Running `START.bat` again

That startup path is the current supported baseline for teammates using this repository on another Windows machine.

## What `START.bat` Does Now

The current launcher is more than a simple Tomcat start:

1. Ensures `.env` exists from `.env.example`
2. Loads `DB_MODE` and `TS_MODE`
3. Renders the correct Tomcat and Business Daemon runtime config
4. Prepares the legacy transformation runtime assets
5. Starts Tomcat
6. Waits for `IWLogin.jsp`
7. Exports saved profile XML into the workspace mirror
8. Compiles per-profile engine overlays into `workspace/GeneratedProfiles/`
9. Opens the browser
10. Launches `iw_ide.exe`

Operationally, `START.bat` is the canonical way to keep the web runtime and the IDE in the same environment mode.

## Current Supported Modes

### Database mode

- `supabase`
  Team-default and primary mode for profile, company, and user data.
- `local`
  Offline fallback for admin-only use or isolated troubleshooting.
- `interweave`
  Legacy hosted fallback when you intentionally want to reconnect to the historical InterWeave database.

### Transformation/log host mode

- `TS_MODE=local`
  Uses this repository's bundled runtime at `http://localhost:9090/iwtransformationserver`
- `TS_MODE=legacy`
  Points flow/query/log URLs at the historical InterWeave host

These modes are intentionally separate. `DB_MODE` controls profile/auth storage. `TS_MODE` controls flow execution and log URLs.

## Runtime Components Present

- Bundled Java runtime in `jre/`
- `iw_ide.exe` launcher and Eclipse plugin/runtime files
- Embedded Tomcat under `web_portal/tomcat/`
- `iw-business-daemon` expanded webapp
- `iwtransformationserver` expanded webapp
- Local profile sync bridge (`WorkspaceProfileSync*`)
- Local profile compiler (`WorkspaceProfileCompiler*`)
- Manual helper scripts for sync, compile, and verification

## Current Runtime Expectations

### Login and profiles

- The admin account (`__iw_admin__` / `%iwps%`) works in local/offline mode.
- Supabase-backed users and hosted profiles also work when valid database credentials are present.
- Known profile regression target: `Tester1` (`amagown@interweave.biz`) in `CRM2QB3`.

### Integration Manager

- `BDConfigurator.jsp` should render the seeded flow/query set after successful login.
- `START` / `STOP` flow toggles work through `ProductDemoServlet`.
- `GO` links route through the local transformation server in `TS_MODE=local`.
- `Runs` links should also stay local in `TS_MODE=local`.

### IDE tandem behavior

- The IDE and portal are aligned at the environment/runtime layer when launched through `START.bat`.
- Wizard-saved profile XML is mirrored into `workspace/IW_Runtime_Sync/`.
- A generated per-profile overlay is emitted into `workspace/GeneratedProfiles/`.
- This is a safe tandem bridge, not a destructive rewrite of the base workspace template projects.

## Validation Commands

Use these after startup when you need a quick health check:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\verify_legacy_engine.ps1
powershell -ExecutionPolicy Bypass -File .\scripts\verify_profile_compiler.ps1
```

What they cover:

- `verify_legacy_engine.ps1`
  Checks portal/runtime reachability and the local transformation server baseline.
- `verify_profile_compiler.ps1`
  Recompiles the known `Tester1` profile and validates the generated `CRM2QB3` overlay against the regression corpus.

## Tomcat Logs

On Windows, use the rotating log files under:

- `web_portal/tomcat/logs/catalina.YYYY-MM-DD.log`
- `web_portal/tomcat/logs/localhost.YYYY-MM-DD.log`
- `web_portal/tomcat/logs/interweave-error.YYYY-MM-DD.log`

Do not rely on `catalina.out` as the primary log path on this Windows setup.

## Known Limits

- The local compiler is a practical replacement for the missing legacy InterWeave backend compiler; it is not a byte-for-byte reproduction of the original backend.
- Generated overlays are profile-specific sidecar projects. They do not overwrite the shared template workspace projects.
- Real external integrations can still fail if downstream connector credentials are invalid (for example Salesforce or payment gateway credentials embedded in a flow).
- Windows native PowerShell/cmd is the supported runtime path. WSL can be useful for repo work, but it is not the primary supported way to run the bundled portal stack.

## Quick Ready Checklist

- [ ] `.env` exists and contains the shared Supabase password
- [ ] `DB_MODE=supabase`
- [ ] `TS_MODE=local`
- [ ] `START.bat` launches the browser and `iw_ide.exe`
- [ ] `http://localhost:9090/iw-business-daemon/IWLogin.jsp` loads
- [ ] Login succeeds
- [ ] `scripts\verify_legacy_engine.ps1` passes
- [ ] `scripts\verify_profile_compiler.ps1` passes

## Related Docs

- `README.md`
- `docs/README.md`
- `docs/development/ENGINE_SYNC_MAP.md`
- `docs/development/WORKSPACE_PROFILE_SYNC.md`
- `docs/development/DEVELOPER_ONBOARDING.md`
