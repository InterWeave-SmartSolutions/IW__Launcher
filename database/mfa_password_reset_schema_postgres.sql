-- =============================================================================
-- MFA & Password Reset Schema (PostgreSQL / Supabase)
-- Adds TOTP-based multi-factor authentication and self-service password reset
-- =============================================================================

-- -----------------------------------------------------------------------------
-- PASSWORD RESET TOKENS
-- Stores time-limited tokens for self-service password reset flow.
-- Tokens are 48-byte hex strings with a 1-hour expiry window.
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(128) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_password_reset_token ON password_reset_tokens(token);
CREATE INDEX IF NOT EXISTS idx_password_reset_user_id ON password_reset_tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_password_reset_expires ON password_reset_tokens(expires_at);

ALTER TABLE password_reset_tokens ENABLE ROW LEVEL SECURITY;

-- -----------------------------------------------------------------------------
-- USER MFA SETTINGS
-- Stores TOTP secret and backup codes for multi-factor authentication.
-- totp_secret: Base32-encoded 20-byte secret
-- backup_codes: JSON array of SHA-256 hashed backup codes
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS user_mfa_settings (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    mfa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    totp_secret VARCHAR(64),
    backup_codes TEXT,
    verified_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_user_mfa_user_id ON user_mfa_settings(user_id);
CREATE INDEX IF NOT EXISTS idx_user_mfa_enabled ON user_mfa_settings(mfa_enabled);

ALTER TABLE user_mfa_settings ENABLE ROW LEVEL SECURITY;
