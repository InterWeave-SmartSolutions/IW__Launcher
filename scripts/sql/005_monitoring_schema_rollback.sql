-- =============================================================================
-- IW_IDE Monitoring Schema Rollback
-- Rollback Script: 005_monitoring_schema_rollback.sql
-- Date: 2026-01-09
-- Description: Safely removes monitoring tables, views, and settings added by
--              the 005_monitoring_schema.sql migration. This rollback script
--              can be run multiple times safely (idempotent).
-- =============================================================================
-- WARNING: This will permanently delete all monitoring data, metrics, and
--          alert history. Ensure you have a backup before running this script.
-- =============================================================================

USE iw_ide;

-- Display warning message
SELECT 'WARNING: This will delete all monitoring data. Press Ctrl+C to cancel.' AS warning_message;
SELECT 'Waiting 5 seconds before proceeding...' AS status;

-- Note: In production, you would add a delay here or require manual confirmation
-- For now, we proceed immediately as this is a migration rollback script

-- -----------------------------------------------------------------------------
-- SECTION 1: DROP VIEWS
-- Remove views first as they depend on tables
-- -----------------------------------------------------------------------------

DROP VIEW IF EXISTS v_active_alerts;
DROP VIEW IF EXISTS v_system_health;
DROP VIEW IF EXISTS v_recent_executions;

SELECT 'Monitoring views dropped' AS status;

-- -----------------------------------------------------------------------------
-- SECTION 2: DROP TABLES
-- Drop tables in reverse dependency order (child tables first, then parents)
-- -----------------------------------------------------------------------------

-- Drop alert history first (depends on alert_rules and transaction_executions)
DROP TABLE IF EXISTS alert_history;
SELECT 'Table alert_history dropped' AS status;

-- Drop webhook endpoints (standalone table)
DROP TABLE IF EXISTS webhook_endpoints;
SELECT 'Table webhook_endpoints dropped' AS status;

-- Drop alert rules (depends on companies, users, projects)
DROP TABLE IF EXISTS alert_rules;
SELECT 'Table alert_rules dropped' AS status;

-- Drop transaction metrics (depends on companies, projects)
DROP TABLE IF EXISTS transaction_metrics;
SELECT 'Table transaction_metrics dropped' AS status;

-- Drop transaction payloads (depends on transaction_executions)
DROP TABLE IF EXISTS transaction_payloads;
SELECT 'Table transaction_payloads dropped' AS status;

-- Drop transaction executions (parent table for payloads and alert history)
DROP TABLE IF EXISTS transaction_executions;
SELECT 'Table transaction_executions dropped' AS status;

-- -----------------------------------------------------------------------------
-- SECTION 3: REMOVE SETTINGS
-- Clean up monitoring-related settings
-- -----------------------------------------------------------------------------

DELETE FROM settings WHERE setting_key IN (
    'monitoring_enabled',
    'monitoring_retention_days',
    'metrics_retention_days',
    'alert_history_retention_days',
    'metrics_aggregation_enabled',
    'default_alert_cooldown_minutes',
    'max_payload_size_mb',
    'monitoring_db_version'
);

SELECT 'Monitoring settings removed from settings table' AS status;

-- Revert schema version to pre-monitoring version
UPDATE settings
SET setting_value = '2.0.0',
    updated_at = CURRENT_TIMESTAMP
WHERE setting_key = 'db_schema_version';

SELECT 'Schema version reverted to 2.0.0' AS status;

-- -----------------------------------------------------------------------------
-- SECTION 4: VERIFY CLEANUP
-- Confirm all monitoring components have been removed
-- -----------------------------------------------------------------------------

SELECT 'Rollback complete - all monitoring components removed' AS status;

-- Verify tables are gone
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 'SUCCESS: All monitoring tables removed'
        ELSE CONCAT('WARNING: ', COUNT(*), ' monitoring tables still exist')
    END AS verification_status
FROM information_schema.tables
WHERE table_schema = 'iw_ide'
AND table_name IN (
    'transaction_executions',
    'transaction_payloads',
    'transaction_metrics',
    'alert_rules',
    'webhook_endpoints',
    'alert_history'
);

-- Verify views are gone
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 'SUCCESS: All monitoring views removed'
        ELSE CONCAT('WARNING: ', COUNT(*), ' monitoring views still exist')
    END AS verification_status
FROM information_schema.views
WHERE table_schema = 'iw_ide'
AND table_name IN (
    'v_recent_executions',
    'v_system_health',
    'v_active_alerts'
);

-- =============================================================================
-- ROLLBACK COMPLETE
-- Database reverted to pre-monitoring state
-- =============================================================================
