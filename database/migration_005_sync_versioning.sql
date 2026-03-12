-- migration_005_sync_versioning.sql
-- Phase 2: Optimistic Locking for Company Configurations
--
-- Adds versioning columns to company_configurations to support optimistic
-- concurrency control between the React portal, Eclipse IDE, and sync bridge.
--
-- version            — incremented on every write; UPDATE must include
--                      WHERE version = <expected> to detect conflicts
-- last_modified_by   — email or identifier of the user/process that wrote
-- last_modified_source — which subsystem triggered the write
--
-- Idempotent: safe to run multiple times on Supabase Postgres 15.
-- Does NOT drop or modify any existing columns.

DO $$
BEGIN
    -- 1. version — optimistic lock counter
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'company_configurations'
          AND column_name = 'version'
    ) THEN
        ALTER TABLE company_configurations
            ADD COLUMN version INTEGER NOT NULL DEFAULT 1;
    END IF;

    -- 2. last_modified_by — who made the change
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'company_configurations'
          AND column_name = 'last_modified_by'
    ) THEN
        ALTER TABLE company_configurations
            ADD COLUMN last_modified_by VARCHAR(255) NOT NULL DEFAULT 'system';
    END IF;

    -- 3. last_modified_source — what triggered the change
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'company_configurations'
          AND column_name = 'last_modified_source'
    ) THEN
        ALTER TABLE company_configurations
            ADD COLUMN last_modified_source VARCHAR(20) NOT NULL DEFAULT 'portal';
    END IF;

    -- 4. CHECK constraint on last_modified_source
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.constraint_column_usage ccu
        JOIN information_schema.check_constraints cc
          ON cc.constraint_name = ccu.constraint_name
         AND cc.constraint_schema = ccu.constraint_schema
        WHERE ccu.table_name = 'company_configurations'
          AND ccu.column_name = 'last_modified_source'
          AND cc.constraint_name = 'chk_last_modified_source'
    ) THEN
        ALTER TABLE company_configurations
            ADD CONSTRAINT chk_last_modified_source
            CHECK (last_modified_source IN ('portal', 'ide', 'bridge', 'system'));
    END IF;
END
$$;
