-- =============================================================================
-- Migration 006: Add title column to users table
-- Supports both PostgreSQL and MySQL
-- =============================================================================

-- PostgreSQL / Supabase:
ALTER TABLE users ADD COLUMN IF NOT EXISTS title VARCHAR(255);

-- MySQL (uncomment if using MySQL instead):
-- ALTER TABLE users ADD COLUMN title VARCHAR(255) AFTER last_name;
