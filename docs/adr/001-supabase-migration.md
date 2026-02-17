# ADR 001: Migration from Oracle Cloud MySQL to Supabase Postgres

## Status

Accepted

## Context

The InterWeave IDE platform originally used an Oracle Cloud MySQL instance as its shared database backend. This setup presented several operational challenges:

- **Single shared credential**: All users and environments connected with the same MySQL user (`iw_admin`), making it impossible to audit access or isolate environments.
- **Availability issues**: The Oracle Cloud MySQL instance at `129.153.47.225:3306` experienced intermittent connectivity problems, sometimes becoming unreachable for extended periods.
- **No SSL enforcement**: Connections to the Oracle Cloud MySQL instance did not require or enforce SSL, leaving credentials and data exposed in transit.
- **Limited scalability**: The single-instance MySQL deployment had no built-in replication, failover, or horizontal scaling capabilities.
- **Vendor lock-in concerns**: The Oracle Cloud free-tier instance carried uncertainty about long-term availability and resource limits.

A more reliable, secure, and operationally mature database backend was needed to support the platform as it moves toward enterprise readiness.

## Decision

Migrate the primary database backend from Oracle Cloud MySQL to Supabase Postgres. The key aspects of this decision are:

1. **Supabase Postgres as the default `DB_MODE`**: The `.env` configuration now defaults to `DB_MODE=supabase`, pointing to a managed Supabase Postgres instance with SSL required (`SUPABASE_DB_SSLMODE=require`).

2. **Per-environment credential isolation**: Supabase supports creating separate database roles and credentials, enabling distinct configurations for development, staging, and production environments.

3. **Retain legacy MySQL support**: The Oracle Cloud MySQL and InterWeave-hosted MySQL options remain available as `DB_MODE` alternatives (`oracle_cloud`, `interweave`, `local`) for backward compatibility and offline use.

4. **Dual-dialect SQL maintenance**: Database schema and migration scripts must be maintained in both MySQL and Postgres dialects:
   - `database/mysql_schema.sql` -- MySQL dialect (legacy)
   - `database/postgres_schema.sql` -- Postgres dialect (primary)
   - `database/schema.sql` -- Original reference schema

5. **Configuration generation**: The `scripts/` setup scripts and `CHANGE_DATABASE.bat` were updated to generate the appropriate JDBC connection strings and Tomcat `config.xml` for whichever `DB_MODE` is selected.

## Consequences

### Positive

- **Improved reliability**: Supabase provides managed Postgres with built-in high availability, automatic backups, and a proven uptime track record.
- **SSL by default**: All connections to Supabase require SSL (`sslmode=require`), securing data in transit without additional configuration.
- **Per-environment credentials**: Each environment can have its own database user and password, improving security posture and auditability.
- **Managed infrastructure**: Supabase handles patching, backups, and monitoring, reducing operational burden.
- **Modern tooling**: Access to the Supabase dashboard for database inspection, SQL editor, and real-time logs.

### Negative

- **Dual-dialect SQL maintenance**: Every schema change and migration must be written and tested in both MySQL and Postgres dialects. This increases the effort for each database change.
- **Column naming divergence**: The `settings` table uses `setting_key` as the column name in Postgres (since `key` is a reserved word in Postgres), while the MySQL schema uses `key`. Application code and queries must account for this difference depending on the active `DB_MODE`.
- **Postgres not yet tested in CI**: At the time of this decision, the continuous integration pipeline does not include Postgres integration tests. MySQL-based tests pass, but Postgres-specific behavior (e.g., case sensitivity, type coercion differences) has only been verified through manual testing.
- **Migration coordination**: Existing deployments using Oracle Cloud MySQL need a data migration path. The `scripts/sql/` directory contains migration scripts, but the migration process is not yet fully automated.
- **Network dependency**: Unlike `local` mode, the Supabase option requires internet connectivity. Offline development must still use `DB_MODE=local`.
