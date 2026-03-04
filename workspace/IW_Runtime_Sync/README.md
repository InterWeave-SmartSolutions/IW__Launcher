# IW Runtime Sync

This Eclipse project is the workspace mirror for wizard-saved company profile
configuration.

- `profiles/<sanitized_profile>/company_configuration.xml` is the exact runtime
  `SF2QBConfiguration` payload saved from the JSP wizard.
- `profiles/<sanitized_profile>/metadata.properties` records the profile name,
  solution type, and any mapped project mirror.

The sync bridge also writes a per-project mirror under:

- `workspace/<MappedProject>/configuration/runtime_profiles/<sanitized_profile>.xml`

Use `WorkspaceProfileSyncServlet` to export/import these mirrors without
editing the database directly.
