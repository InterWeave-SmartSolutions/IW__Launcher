-- =============================================================================
-- RBAC Role Migration: Expand binary (user/admin) to three-tier roles
-- Run against Supabase Postgres via pooler (port 6543)
-- =============================================================================

-- Step 1: Rename 'user' → 'operator' (existing integration users)
UPDATE users SET role = 'operator' WHERE role = 'user';

-- Step 2: Add CHECK constraint to enforce valid role values
-- Drop existing constraint if any (idempotent)
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check;
ALTER TABLE users ADD CONSTRAINT users_role_check
    CHECK (role IN ('operator', 'associate', 'admin'));

-- Step 3: Update default from 'user' to 'operator'
ALTER TABLE users ALTER COLUMN role SET DEFAULT 'operator';

-- Step 4: Add index on role for filtered queries
CREATE INDEX IF NOT EXISTS idx_user_role ON users(role);

-- Verify
SELECT role, COUNT(*) FROM users GROUP BY role ORDER BY role;
