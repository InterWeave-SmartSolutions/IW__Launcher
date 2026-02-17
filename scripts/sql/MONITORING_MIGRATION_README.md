# Monitoring Schema Migration Guide

## Overview

This directory contains SQL migration scripts for adding real-time integration monitoring capabilities to the IW_IDE database.

**Migration Version:** 005
**Feature:** Real-Time Integration Monitoring Dashboard
**Date:** 2026-01-09

## Files

- `005_monitoring_schema.sql` - Main migration script (adds monitoring tables)
- `005_monitoring_schema_rollback.sql` - Rollback script (removes monitoring tables)
- `MONITORING_MIGRATION_README.md` - This file

## What This Migration Adds

### New Tables

1. **transaction_executions** - Detailed log of every transaction execution
   - Tracks execution status, timing, records processed/failed
   - Stores error messages and stack traces
   - Links to companies, projects, and transformations

2. **transaction_payloads** - Request/response data storage
   - Separated from executions for performance
   - Supports XML, JSON, text, and binary formats
   - Enables drill-down debugging

3. **transaction_metrics** - Aggregated performance metrics
   - Hourly, daily, and weekly rollups
   - Success/failure rates, execution times
   - Pre-computed for fast dashboard queries

4. **alert_rules** - Alert configuration
   - Email and webhook notifications
   - Threshold-based alerting
   - Cooldown and rate limiting

5. **webhook_endpoints** - External notification endpoints
   - Supports Slack, PagerDuty, custom webhooks
   - Authentication configuration
   - Retry logic and health tracking

6. **alert_history** - Audit log of sent alerts
   - Tracks delivery status
   - Retry attempts
   - Full payload history

### New Views

1. **v_recent_executions** - Last 24 hours of executions with company/project details
2. **v_system_health** - Real-time system health metrics
3. **v_active_alerts** - Active alert rules with statistics

### New Settings

- `monitoring_enabled` - Master toggle for monitoring
- `monitoring_retention_days` - How long to keep execution logs (default: 90 days)
- `metrics_retention_days` - How long to keep metrics (default: 365 days)
- `alert_history_retention_days` - How long to keep alert history (default: 180 days)
- `metrics_aggregation_enabled` - Enable automatic metrics rollup
- `default_alert_cooldown_minutes` - Default alert cooldown period (default: 30 min)
- `max_payload_size_mb` - Maximum payload size to store (default: 10 MB)

## Prerequisites

- MySQL 5.7 or higher (or compatible MariaDB)
- Existing IW_IDE database with tables: `companies`, `users`, `projects`, `transformations`, `settings`
- Database user with CREATE TABLE, CREATE VIEW, and INSERT privileges

## Running the Migration

### Option 1: Using MySQL Command Line

```bash
# Connect to your MySQL database
mysql -h [host] -u [username] -p iw_ide

# Run the migration script
source _internal/sql/005_monitoring_schema.sql

# Or alternatively:
mysql -h [host] -u [username] -p iw_ide < _internal/sql/005_monitoring_schema.sql
```

### Option 2: Using MySQL Workbench

1. Open MySQL Workbench
2. Connect to your database
3. Open `005_monitoring_schema.sql`
4. Click "Execute" (⚡ icon)
5. Verify success messages in output

### Option 3: Automated Database Setup Script

The migration will be integrated into the existing database setup scripts:

```bash
# Windows
.\_internal\SETUP_DB_Windows.bat

# Linux/Mac
./_internal/SETUP_DB_Linux.sh
```

## Verifying the Migration

After running the migration, verify it completed successfully:

```sql
-- Check that all tables were created
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'iw_ide'
  AND table_name LIKE '%transaction%' OR table_name LIKE '%alert%' OR table_name LIKE '%webhook%';

-- Check that views were created
SELECT table_name
FROM information_schema.views
WHERE table_schema = 'iw_ide'
  AND table_name LIKE 'v_%';

-- Check schema version
SELECT setting_value
FROM settings
WHERE setting_key = 'db_schema_version';
-- Should return: 2.1.0

-- Check monitoring settings
SELECT *
FROM settings
WHERE setting_key LIKE 'monitoring%';
```

## Rollback Instructions

If you need to remove the monitoring schema:

⚠️ **WARNING:** This will permanently delete all monitoring data, metrics, and alert history.

```bash
# Backup first (recommended)
mysqldump -h [host] -u [username] -p iw_ide > backup_before_rollback.sql

# Run the rollback script
mysql -h [host] -u [username] -p iw_ide < _internal/sql/005_monitoring_schema_rollback.sql
```

## Migration Characteristics

### Idempotency

✅ **Safe to run multiple times** - The migration uses:
- `CREATE TABLE IF NOT EXISTS` - Won't fail if tables already exist
- `CREATE OR REPLACE VIEW` - Updates views if they exist
- `INSERT IGNORE` - Won't duplicate settings

### Backward Compatibility

✅ **Non-breaking** - The migration:
- Only adds new tables and views
- Does not modify existing tables
- Uses foreign keys with `ON DELETE SET NULL` or `CASCADE` appropriately
- Existing queries and code continue to work

### Performance Impact

✅ **Minimal impact during migration**:
- Creates tables and indexes (fast on empty tables)
- Adds settings (8 rows)
- Creates views (computed at query time, not stored)
- **Estimated time:** < 5 seconds on typical hardware

### Foreign Key Constraints

The migration properly references existing tables:

- `transaction_executions.company_id` → `companies.id` (CASCADE)
- `transaction_executions.project_id` → `projects.id` (SET NULL)
- `transaction_executions.transformation_id` → `transformations.id` (SET NULL)
- `alert_rules.company_id` → `companies.id` (CASCADE)
- `alert_rules.created_by_user_id` → `users.id` (SET NULL)
- `alert_rules.project_id` → `projects.id` (CASCADE)

## Database Connection Modes

This migration works with all three IW_IDE database modes:

1. **Oracle Cloud MySQL** (oracle_cloud) - `129.153.47.225:3306/iw_ide`
2. **InterWeave Server** (interweave) - `148.62.63.8:3306/hostedprofiles`
3. **Local Mode** (local) - Admin-only offline mode

## Testing After Migration

After running the migration, test that monitoring is working:

```sql
-- Insert a test execution
INSERT INTO transaction_executions (
    execution_id, company_id, flow_name, flow_type, status, started_at
) VALUES (
    'TEST-001', 1, 'TestFlow', 'transaction', 'success', NOW()
);

-- Verify the test execution appears
SELECT * FROM transaction_executions WHERE execution_id = 'TEST-001';

-- Check the recent executions view
SELECT * FROM v_recent_executions LIMIT 5;

-- Check system health view
SELECT * FROM v_system_health;

-- Clean up test data
DELETE FROM transaction_executions WHERE execution_id = 'TEST-001';
```

## Troubleshooting

### Error: "Table 'companies' doesn't exist"

**Cause:** The base schema hasn't been created yet.
**Solution:** Run the base schema first:
```bash
mysql -h [host] -u [username] -p iw_ide < database/mysql_schema.sql
```

### Error: "Cannot add foreign key constraint"

**Cause:** Referenced table doesn't exist or has different structure.
**Solution:** Verify base tables exist and have expected structure:
```sql
SHOW TABLES;
DESCRIBE companies;
DESCRIBE users;
DESCRIBE projects;
DESCRIBE transformations;
```

### Error: "Access denied for user"

**Cause:** Database user lacks necessary privileges.
**Solution:** Grant required privileges:
```sql
GRANT CREATE, ALTER, INDEX, REFERENCES ON iw_ide.* TO 'username'@'host';
FLUSH PRIVILEGES;
```

## Next Steps

After successfully running the migration:

1. ✅ **Phase 1 Complete** - Database schema ready
2. → **Phase 2** - Build REST API servlets
3. → **Phase 3** - Integrate transaction logging
4. → **Phase 4** - Implement alerting service
5. → **Phase 5** - Build dashboard frontend
6. → **Phase 6** - Integration testing

## Support

For issues or questions about this migration:
- Review the implementation plan: `.auto-claude/specs/005-real-time-integration-monitoring-dashboard/implementation_plan.json`
- Check the spec: `.auto-claude/specs/005-real-time-integration-monitoring-dashboard/spec.md`
- Review existing database schema: `database/mysql_schema.sql`

## Schema Diagram

```
companies ──┬──> transaction_executions ──> transaction_payloads
           │           │
           │           └──> alert_history
           │
           ├──> alert_rules ──> alert_history
           │
           ├──> webhook_endpoints
           │
           └──> transaction_metrics

users ──┬──> alert_rules
       └──> webhook_endpoints

projects ──┬──> transaction_executions
          └──> transaction_metrics
          └──> alert_rules

transformations ──> transaction_executions
```

## Change Log

- **2026-01-09** - Initial migration created (v1.0.0)
  - Added 6 tables: transaction_executions, transaction_payloads, transaction_metrics, alert_rules, webhook_endpoints, alert_history
  - Added 3 views: v_recent_executions, v_system_health, v_active_alerts
  - Added 8 configuration settings
  - Schema version updated to 2.1.0
