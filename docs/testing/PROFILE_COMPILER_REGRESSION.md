# Profile Compiler Regression

This is the current regression path for the local workspace profile compiler.

Use it when validating that wizard-saved profile configuration, the generated
workspace overlays, and the active runtime are still aligned after changes.

## Primary Regression Target

- Profile: `Tester1:amagown@interweave.biz`
- Solution type: `CRM2QB3`
- Expected compiler module: `CRM2QB3`
- Expected runtime mode: `DB_MODE=supabase`, `TS_MODE=local`

The stored expectation corpus lives at:

- `tests/compiler-regression/Tester1.CRM2QB3.expected.properties`

## Automated Verification

Run:

```bat
scripts\verify_profile_compiler.bat
```

What it does:

1. Calls `WorkspaceProfileCompilerServlet?action=compileProfile`
2. Rebuilds the generated overlay for the target profile
3. Verifies:
   - generated files exist
   - the compiler module matches expectations
   - `ts_base_url` matches the active local runtime
   - expected active/inactive transactions are present
   - expected active/inactive queries are present
   - `ReturnString` is correctly derived from the saved wizard config

## Manual Spot Check

After the automated script passes:

1. Run `START.bat`
2. Log in as `Tester1`
3. Open the Integration Manager
4. Confirm the expected flow/query set renders
5. Start and stop a low-risk flow such as `BPMTransactions2Auth`
6. Confirm `GO` links resolve to `localhost:9090/iwtransformationserver`

## Generated Artifacts To Inspect

- `workspace/GeneratedProfiles/Tester1_amagown_interweave.biz/configuration/im/config.xml`
- `workspace/GeneratedProfiles/Tester1_amagown_interweave.biz/configuration/profile/profile.properties`
- `workspace/GeneratedProfiles/Tester1_amagown_interweave.biz/configuration/profile/compiler-selection.properties`
- `workspace/GeneratedProfiles/Tester1_amagown_interweave.biz/xslt/Site/new/xml/compiler-selection.xml`

## When To Re-run This

Re-run this regression check after changes to:

- `WorkspaceProfileCompiler.java`
- `WorkspaceProfileCompilerServlet.java`
- `WorkspaceProfileSyncSupport.java`
- `LocalLoginServlet.java`
- `ApiLoginServlet.java`
- `LocalCompanyCredentialsServlet.java`
- `START.bat`
- `scripts/start_webportal.bat`

## Scope Note

This regression validates the current local compiler replacement. It does not
prove perfect equivalence with the unavailable original InterWeave backend
compiler.
