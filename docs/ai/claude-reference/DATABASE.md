# Database Reference

## Configuration

Database settings are in `.env` (auto-created from `.env.example`):

```bash
# Set DB_MODE to: supabase | interweave | local
DB_MODE=supabase

# Supabase Postgres (default)
SUPABASE_DB_HOST=db.hpodmkchdzwjtlnxjohf.supabase.co
SUPABASE_DB_PORT=5432
SUPABASE_DB_NAME=postgres
SUPABASE_DB_USER=postgres
SUPABASE_DB_PASSWORD={{SUPABASE_DB_PASSWORD}}

# InterWeave Server (alternative)
IW_DB_HOST=148.62.63.8
IW_DB_PORT=3306
IW_DB_NAME=hostedprofiles
```

## Critical Connection Rules

- **Supabase pooler (port 6543)** is the ONLY working endpoint — verified 2026-02-26
- **Direct connection (port 5432)** is BLOCKED/UNREACHABLE from this network — do NOT use
- JDBC URL **must** include `prepareThreshold=0` for pooler compatibility (PgBouncer/Supavisor)
- Pooler username: `postgres.hpodmkchdzwjtlnxjohf`
- See `context.xml` for the working JDBC config

## Schema Structure

Key tables (see `database/mysql_schema.sql`):
- `companies` - Organization profiles with license management
- `users` - User accounts linked to companies
- `user_profiles` - Extended user information
- Authentication uses email + bcrypt password hash (via `PasswordHasher` utility, jBCrypt 0.4). Progressive migration from SHA-256/plaintext → bcrypt on login.

Schema files:
- `database/postgres_schema.sql` — primary (Supabase)
- `database/monitoring_schema_postgres.sql` — monitoring (6 tables, 3 views)
- `database/mysql_schema.sql` — legacy MySQL
- `database/schema.sql` — original
- RLS enabled on all 14 tables

## Setup Scripts

```bash
# Windows
./scripts/SETUP_DB_Windows.bat

# Linux/Mac
./scripts/SETUP_DB_Linux.sh
```

## Change Database Connection
```bash
./CHANGE_DATABASE.bat
```
Interactive menu to switch between Supabase, InterWeave server, or offline mode.
