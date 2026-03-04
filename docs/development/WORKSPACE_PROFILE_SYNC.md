# Workspace Profile Sync

The JSP wizard saves per-profile runtime configuration as flat
`<SF2QBConfiguration>` XML in the `company_configurations` table.

That payload is not structurally compatible with the IDE's existing engine files:

- `workspace/<Project>/configuration/im/config.xml`
- `workspace/<Project>/configuration/ts/config.xml`

So the sync bridge does **not** overwrite those engine files. Instead, it mirrors
the saved profile XML into IDE-visible sidecar files:

- `workspace/IW_Runtime_Sync/profiles/<sanitized_profile>/company_configuration.xml`
- `workspace/IW_Runtime_Sync/profiles/<sanitized_profile>/metadata.properties`

If a project mapping exists, it also writes:

- `workspace/<MappedProject>/configuration/runtime_profiles/<sanitized_profile>.xml`
- `workspace/<MappedProject>/configuration/runtime_profiles/<sanitized_profile>.properties`

The compiler layer then emits a generated engine overlay project:

- `workspace/GeneratedProfiles/<sanitized_profile>/configuration/im/config.xml`
- `workspace/GeneratedProfiles/<sanitized_profile>/configuration/ts/config.xml`
- `workspace/GeneratedProfiles/<sanitized_profile>/configuration/profile/company_configuration.xml`
- `workspace/GeneratedProfiles/<sanitized_profile>/configuration/profile/profile.properties`
- `workspace/GeneratedProfiles/<sanitized_profile>/configuration/profile/compiler-selection.properties`
- `workspace/GeneratedProfiles/<sanitized_profile>/xslt/Site/new/xml/compiler-selection.xml`

## Automatic behavior

- `START.bat` and `scripts/start_webportal.bat` call `WorkspaceProfileSyncServlet?action=exportAll`
  after Tomcat is ready.
- `START.bat` and `scripts/start_webportal.bat` then call
  `WorkspaceProfileCompilerServlet?action=compileAll`.
- `LocalLoginServlet` and `ApiLoginServlet` compile the current profile on successful login
  when a saved configuration already exists.
- `LocalCompanyCredentialsServlet` compiles the current profile immediately after a successful save.

## Manual bridge

The local-only servlet endpoint is:

- `/iw-business-daemon/WorkspaceProfileSyncServlet`
- `/iw-business-daemon/WorkspaceProfileCompilerServlet`

Supported actions:

- `exportAll`
- `exportProfile`
- `importProfile`
- `compileAll`
- `compileProfile`

Helper scripts:

- `scripts/sync_workspace_profiles.bat`
- `scripts/sync_workspace_profiles.ps1`
- `scripts/compile_workspace_profiles.bat`
- `scripts/compile_workspace_profiles.ps1`

Examples:

```bat
scripts\sync_workspace_profiles.bat exportAll
scripts\sync_workspace_profiles.bat exportProfile "Tester1:amagown@interweave.biz"
scripts\sync_workspace_profiles.bat importProfile "Tester1:amagown@interweave.biz" "Creatio_QuickBooks_Integration"
scripts\compile_workspace_profiles.bat compileAll
scripts\compile_workspace_profiles.bat compileProfile "Tester1:amagown@interweave.biz"
```

## Project mapping

Default solution-to-project routing lives in:

- `config/workspace-profile-map.properties`

Current defaults:

- `CRM2QB3 -> Creatio_QuickBooks_Integration`
- `SF2AUTH -> SF2AuthNet`

Edit that file if a different workspace project should receive the per-project mirror.

## Compiler behavior

The compiler is a practical local replacement for the missing legacy InterWeave
backend compiler.

- It uses the wizard-saved `SF2QBConfiguration` as input.
- It mirrors that input first, then generates a per-profile engine overlay.
- It uses the mapped project when available.
- If the mapped project's `configuration/im/config.xml` does not contain actual
  `TransactionDescription` or `Query` nodes, it falls back to the seeded
  `SF2AuthNet` runtime template.
- It applies the active `TS_MODE` endpoints and writes a profile-specific
  `profile.properties` manifest for IDE inspection.
- It also writes `compiler-selection` artifacts that make the generated
  transaction/query activation decisions visible to humans and tools.

## Current solution-specific logic

The compiler now supports solution-aware modules instead of treating every
profile as generic parameter injection.

### `CRM2QB3`

`CRM2QB3` currently has explicit selection logic in the local compiler:

- Transaction flow activation is narrowed to the `Auth`-oriented baseline
  flow set used by the seeded runtime template.
- Query activation is decided from wizard fields such as `SyncTypeAC`,
  `SyncTypeSO`, `SyncTypeInv`, `SyncTypeSR`, and `SyncTypePrd`.
- Unrelated query families (for example `Sugar*`) are disabled in the generated
  overlay.

This is still a local approximation of the missing legacy backend compiler, but
it is now deterministic and inspectable.

## Regression verification

The regression corpus currently uses the known `Tester1` profile to validate the
generated `CRM2QB3` overlay against expected compiler output.

Files:

- `tests/compiler-regression/Tester1.CRM2QB3.expected.properties`
- `scripts/verify_profile_compiler.bat`
- `scripts/verify_profile_compiler.ps1`

Run:

```bat
scripts\verify_profile_compiler.bat
```

That script recompiles the target profile through
`WorkspaceProfileCompilerServlet?action=compileProfile`, then checks the
generated overlay and compiler-selection output against the stored expectation
set.
