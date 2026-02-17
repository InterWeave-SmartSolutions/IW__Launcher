# Credential Rotation Guide

This document describes how to rotate database credentials for the InterWeave IDE platform. Follow these procedures when credentials need to be changed due to scheduled rotation, personnel changes, or a security incident.

## Table of Contents

1. [Supabase Postgres Credential Rotation](#1-supabase-postgres-credential-rotation)
2. [Oracle Cloud MySQL Credential Rotation](#2-oracle-cloud-mysql-credential-rotation)
3. [Verification Steps After Rotation](#3-verification-steps-after-rotation)
4. [Notifications and Restarts](#4-notifications-and-restarts)
5. [Emergency: Credential Leak Response](#5-emergency-credential-leak-response)

---

## 1. Supabase Postgres Credential Rotation

Supabase Postgres is the primary database (`DB_MODE=supabase`). Credentials are managed through the Supabase dashboard and stored locally in `.env`.

### Step 1: Generate new credentials in Supabase

1. Log in to the Supabase dashboard at `https://supabase.com/dashboard`.
2. Select the project used by InterWeave IDE.
3. Navigate to **Settings** > **Database**.
4. Under **Connection string**, note the current host, port, and database name (these typically do not change during a password rotation).
5. To change the database password:
   - Go to **Settings** > **Database** > **Database Password**.
   - Click **Reset database password**.
   - Copy the new password immediately. It will not be shown again.

### Step 2: Update the local .env file

Edit `.env` on every machine running the InterWeave IDE:

```bash
# Update only the password (host, port, db name, and user usually stay the same)
SUPABASE_DB_PASSWORD=<new-password-here>
```

Ensure no trailing whitespace or quotes around the value.

### Step 3: Regenerate Tomcat config.xml

Run the database configuration script to regenerate the JDBC connection settings:

```cmd
CHANGE_DATABASE.bat
```

Select the `supabase` option when prompted. This regenerates the Tomcat context configuration with the updated credentials.

### Step 4: Restart Tomcat

```cmd
:: Stop the web portal
scripts\stop_webportal.bat

:: Start the web portal
scripts\start_webportal.bat
```

### Step 5: Verify connectivity

See [Verification Steps](#3-verification-steps-after-rotation) below.

---

## 2. Oracle Cloud MySQL Credential Rotation

Oracle Cloud MySQL is the legacy database option (`DB_MODE=oracle_cloud`). The instance is at `129.153.47.225:3306`.

### Step 1: Change the MySQL password

Connect to the Oracle Cloud MySQL instance with current admin credentials and change the password:

```sql
ALTER USER 'iw_admin'@'%' IDENTIFIED BY '<new-password-here>';
FLUSH PRIVILEGES;
```

Alternatively, use the Oracle Cloud console if MySQL is managed through Oracle Cloud Infrastructure.

### Step 2: Update the local .env file

Edit `.env`:

```bash
ORACLE_DB_PASSWORD=<new-password-here>
```

### Step 3: Regenerate Tomcat config.xml

```cmd
CHANGE_DATABASE.bat
```

Select the `oracle_cloud` option.

### Step 4: Restart Tomcat

```cmd
scripts\stop_webportal.bat
scripts\start_webportal.bat
```

### Step 5: Verify connectivity

See [Verification Steps](#3-verification-steps-after-rotation) below.

---

## 3. Verification Steps After Rotation

After rotating credentials for any database backend, perform the following checks:

### 3.1 Verify Tomcat starts without errors

```cmd
:: Check for JDBC connection errors in the log
:: (on Windows, open the file; on WSL2/Linux, use grep)
```

Open `web_portal/tomcat/logs/catalina.out` and look for:
- `java.sql.SQLException` -- indicates connection failure
- `Access denied` -- indicates wrong credentials
- `Communications link failure` -- indicates network/host issue

A clean startup will show Tomcat initialization messages without database errors.

### 3.2 Verify login works

1. Open `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Log in with `__iw_admin__` / `%iwps%`
3. Confirm the portal loads without database errors

### 3.3 Verify API endpoints

Test the monitoring endpoint to confirm the database connection is active:

```bash
curl http://localhost:8080/iw-business-daemon/api/monitoring
```

The response should indicate a healthy database connection.

### 3.4 Verify from the IDE

1. Launch the IDE (`scripts\start_ide.bat` or via `START.bat`)
2. Open a workspace project
3. Confirm that any project operations that query the database succeed

---

## 4. Notifications and Restarts

After rotating credentials, the following actions are required:

### Who to notify

| Audience              | Reason                                              |
|-----------------------|------------------------------------------------------|
| All developers        | They need to update `.env` on their local machines   |
| DevOps / deployment   | Production and staging `.env` files need updating     |
| QA team               | Test environments need the new credentials           |

### What to restart

| Component                 | Action Required                                    |
|---------------------------|----------------------------------------------------|
| Tomcat (each instance)    | Must be restarted to pick up new JDBC credentials  |
| Eclipse IDE               | No restart needed (IDE does not connect directly to DB in most configurations) |
| CI/CD pipeline            | Update environment variables or secrets store       |
| Database setup scripts    | No change needed (they read from `.env` at runtime) |

### Restart sequence

1. Stop Tomcat: `scripts\stop_webportal.bat`
2. Update `.env` with new credentials
3. Run `CHANGE_DATABASE.bat` to regenerate config
4. Start Tomcat: `scripts\start_webportal.bat`
5. Verify (see section 3)

---

## 5. Emergency: Credential Leak Response

If database credentials have been leaked (committed to a public repository, shared in an insecure channel, found in logs, etc.), follow this procedure immediately.

### Step 1: Rotate credentials NOW

Do not wait. Rotate the affected credentials immediately using the procedures in sections 1 or 2 above.

**For Supabase:**
- Reset the database password via the Supabase dashboard immediately.
- If the Supabase project URL or API keys were also leaked, consider creating a new Supabase project entirely.

**For Oracle Cloud MySQL:**
- Change the `iw_admin` password via SQL or the Oracle Cloud console.
- If the MySQL instance is exposed to the internet, consider restricting access to known IP addresses.

### Step 2: Check git history for leaked credentials

```bash
# Search for credentials in git history
git log --all -p -- .env
git log --all -p -- '*.xml' | grep -i password
git log --all -p -- '*.properties' | grep -i password
```

If credentials were committed to git:
- Use `git filter-repo` or BFG Repo-Cleaner to remove them from history
- Force-push the cleaned history (coordinate with the team first)
- Consider all credentials in the old history as compromised

### Step 3: Revoke old credentials

After rotating, explicitly verify that the old credentials no longer work:

```bash
# For Supabase Postgres
psql "postgresql://<user>:<OLD-password>@<host>:<port>/<dbname>?sslmode=require"
# This should FAIL with "password authentication failed"

# For Oracle Cloud MySQL
mysql -h 129.153.47.225 -u iw_admin -p'<OLD-password>' iw_ide
# This should FAIL with "Access denied"
```

### Step 4: Audit for unauthorized access

- Check Supabase dashboard logs for any unusual queries or connections during the exposure window.
- Check Oracle Cloud MySQL slow query log and general log if enabled.
- Look for any data that may have been exfiltrated or modified.

### Step 5: Document the incident

Record the following:
- When the credentials were leaked
- How they were leaked (commit, chat message, log file, etc.)
- When they were rotated
- What access, if any, occurred during the exposure window
- Corrective actions taken to prevent recurrence

### Step 6: Preventive measures

After resolving the incident:
- Ensure `.env` is in `.gitignore` (it should already be)
- Review `.env.example` to confirm it contains only placeholder values, not real credentials
- Consider adding a pre-commit hook to detect accidental credential commits
- Review access logs periodically
