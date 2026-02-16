# ADR 002: Additive-Only Modification Policy

## Status

Accepted

## Context

The InterWeave IDE codebase contains several proprietary compiled components that are delivered as binary artifacts without corresponding source code:

- **`LoginServlet.class`**: The compiled authentication servlet that handles user login. It uses a proprietary password hashing format that is not documented. Only the admin account (`__iw_admin__` / `%iwps%`) works reliably; custom user authentication (e.g., `demo@sample.com`) fails due to the unknown hash algorithm. The servlet source is not available.
- **`iw_sdk_1.0.0` plugin** (`plugins/iw_sdk_1.0.0/`): The core InterWeave SDK plugin for Eclipse. This is a proprietary binary plugin that provides the integration flow designer, XML transformation tools, and connection management. No source code is available.
- **Eclipse 3.1 runtime**: The IDE is built on Eclipse 3.1 with a specific set of plugin JARs in `plugins/`. These JARs are interdependent and version-locked.
- **Bundled JRE** (`jre/`): A Java 8 runtime bundled with the distribution. Modifying or replacing it could break compatibility with the Eclipse runtime and the SDK plugin.
- **Eclipse configuration** (`configuration/`): The Eclipse update manager configuration that tracks installed plugins and their dependencies.

Past attempts to modify or replace files in these directories resulted in broken functionality: the IDE would fail to start, authentication would break entirely, or integration flows would not execute correctly. The fragility stems from undocumented interdependencies between these compiled components.

## Decision

All new development in this repository follows an **additive-only pattern**. The core rules are:

1. **Never modify files in protected directories**: The following directories and their contents must not be altered:
   - `plugins/` -- Eclipse and InterWeave SDK plugins
   - `jre/` -- Bundled Java 8 runtime
   - `configuration/` -- Eclipse update manager configuration

2. **Never modify existing compiled classes**: Existing `.class` files (such as `LoginServlet.class`) must not be replaced, overwritten, or patched. If new behavior is needed, create a new servlet with a new URL mapping.

3. **New files alongside old**: When adding functionality, create new files rather than editing existing ones:
   - New servlets get new URL mappings (e.g., `/api/monitoring` alongside existing `/IWLogin`)
   - New JSP pages get new filenames
   - New configuration files use new names or new sections
   - New SQL migrations extend the existing numbering sequence

4. **New scripts wrap existing ones**: Startup scripts (`START.bat`, `STOP.bat`) may be extended, but their core behavior (starting Tomcat, launching the IDE) must remain unchanged. New automation is added as separate scripts.

5. **New profiles for new behavior**: Maven build profiles, Tomcat context configurations, and database connection profiles are added as new entries, never replacing existing ones.

## Consequences

### Positive

- **Zero risk to existing functionality**: Since protected files are never touched, the existing IDE, authentication, and integration flow execution remain exactly as they were in the original distribution.
- **Easy rollback**: Any new addition can be removed without affecting the base system. The original state is always recoverable by deleting the added files.
- **Clear change tracking**: `git diff` and `git log` clearly show what has been added. There is no ambiguity about which files were part of the original distribution versus new development.
- **Safe for multiple contributors**: Different developers can add new servlets, scripts, and configurations without conflicting with each other or with the protected base.

### Negative

- **Some duplication is acceptable and expected**: There may be cases where new code partially duplicates logic that exists in a compiled class. For example, a new authentication servlet may reimplement user lookup logic that already exists in `LoginServlet.class`. This duplication is an intentional trade-off for safety.
- **Coexistence of old and new servlets**: The web application will have both the original servlet mappings and new API endpoints. This means the `web.xml` grows over time, and developers need to understand which endpoints are legacy and which are new.
- **Migration scripts accumulate**: The `_internal/sql/` directory will grow as each change adds new migration files rather than modifying existing ones. Migration ordering must be maintained carefully.
- **Cannot fix bugs in proprietary code**: If a bug exists in `LoginServlet.class` or the SDK plugin, we cannot patch it directly. Workarounds must be implemented as additive layers (e.g., a proxy servlet, a wrapper script, or client-side logic).
- **Documentation overhead**: Developers must be explicitly informed about which directories are protected and why. The `CLAUDE.md`, `AI_WORKFLOW.md`, and this ADR serve as that documentation for both human and AI contributors.
