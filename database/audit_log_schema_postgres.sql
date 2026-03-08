-- Audit Log Schema for InterWeave Portal
-- Tracks user actions for security and compliance

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER,
    company_id INTEGER,
    user_email VARCHAR(255),
    action_type VARCHAR(50) NOT NULL
        CHECK (action_type IN (
            'login', 'logout', 'login_failed',
            'profile_update', 'password_change', 'company_update',
            'flow_start', 'flow_stop', 'flow_config_change',
            'config_change', 'user_register', 'company_register',
            'mfa_enable', 'mfa_disable', 'password_reset',
            'alert_create', 'alert_update', 'alert_delete',
            'admin_action'
        )),
    action_detail TEXT,
    resource_type VARCHAR(50),
    resource_id VARCHAR(100),
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    metadata TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_audit_user ON audit_log(user_id);
CREATE INDEX IF NOT EXISTS idx_audit_company ON audit_log(company_id);
CREATE INDEX IF NOT EXISTS idx_audit_action ON audit_log(action_type);
CREATE INDEX IF NOT EXISTS idx_audit_created ON audit_log(created_at);
CREATE INDEX IF NOT EXISTS idx_audit_resource ON audit_log(resource_type, resource_id);

ALTER TABLE audit_log ENABLE ROW LEVEL SECURITY;
