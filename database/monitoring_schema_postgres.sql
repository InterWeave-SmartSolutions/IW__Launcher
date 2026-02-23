-- =============================================================================
-- IW_IDE Monitoring Database Schema (PostgreSQL / Supabase)
-- Real-Time Integration Monitoring Dashboard
-- =============================================================================
-- Ported from monitoring_schema.sql (MySQL) to PostgreSQL.
-- Requires postgres_schema.sql to be run first (companies, users, projects,
-- transformations, settings tables must exist).
-- =============================================================================

-- -----------------------------------------------------------------------------
-- TRANSACTION EXECUTION TRACKING
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS transaction_executions (
    id BIGSERIAL PRIMARY KEY,
    execution_id VARCHAR(100) UNIQUE NOT NULL,

    -- Reference fields
    company_id INTEGER,
    project_id INTEGER,
    transformation_id INTEGER,
    flow_name VARCHAR(255) NOT NULL,
    flow_type VARCHAR(20) DEFAULT 'transaction'
        CHECK (flow_type IN ('transaction', 'utility', 'query')),

    -- Execution metadata
    status VARCHAR(20) NOT NULL DEFAULT 'running'
        CHECK (status IN ('running', 'success', 'failed', 'cancelled', 'timeout')),
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    duration_ms INTEGER,

    -- Processing metrics
    records_processed INTEGER DEFAULT 0,
    records_failed INTEGER DEFAULT 0,
    records_skipped INTEGER DEFAULT 0,

    -- Error information
    error_message TEXT,
    error_code VARCHAR(50),
    stack_trace TEXT,

    -- Environment context
    triggered_by VARCHAR(20) DEFAULT 'scheduler'
        CHECK (triggered_by IN ('scheduler', 'manual', 'webhook', 'api')),
    triggered_by_user_id INTEGER,
    server_hostname VARCHAR(100),

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_exec_company ON transaction_executions(company_id);
CREATE INDEX IF NOT EXISTS idx_exec_project ON transaction_executions(project_id);
CREATE INDEX IF NOT EXISTS idx_exec_transformation ON transaction_executions(transformation_id);
CREATE INDEX IF NOT EXISTS idx_exec_status ON transaction_executions(status);
CREATE INDEX IF NOT EXISTS idx_exec_started ON transaction_executions(started_at);
CREATE INDEX IF NOT EXISTS idx_exec_flow_name ON transaction_executions(flow_name);
CREATE INDEX IF NOT EXISTS idx_exec_flow_type ON transaction_executions(flow_type);
CREATE INDEX IF NOT EXISTS idx_exec_company_status_started ON transaction_executions(company_id, status, started_at);

-- Transaction payloads - request/response data (separated for performance)
CREATE TABLE IF NOT EXISTS transaction_payloads (
    id BIGSERIAL PRIMARY KEY,
    execution_id BIGINT NOT NULL,

    payload_type VARCHAR(20) NOT NULL
        CHECK (payload_type IN ('request', 'response', 'error_context', 'debug')),
    payload_format VARCHAR(10) DEFAULT 'xml'
        CHECK (payload_format IN ('xml', 'json', 'text', 'binary')),
    payload_data TEXT,
    payload_size INTEGER,

    captured_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    source_system VARCHAR(100),
    destination_system VARCHAR(100),

    FOREIGN KEY (execution_id) REFERENCES transaction_executions(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_payload_execution ON transaction_payloads(execution_id);
CREATE INDEX IF NOT EXISTS idx_payload_type ON transaction_payloads(payload_type);

-- -----------------------------------------------------------------------------
-- AGGREGATED METRICS
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS transaction_metrics (
    id BIGSERIAL PRIMARY KEY,

    company_id INTEGER,
    project_id INTEGER,
    flow_name VARCHAR(255),

    metric_period VARCHAR(10) NOT NULL
        CHECK (metric_period IN ('hourly', 'daily', 'weekly')),
    period_start TIMESTAMP NOT NULL,
    period_end TIMESTAMP NOT NULL,

    -- Execution counts
    total_executions INTEGER DEFAULT 0,
    successful_executions INTEGER DEFAULT 0,
    failed_executions INTEGER DEFAULT 0,
    cancelled_executions INTEGER DEFAULT 0,
    timeout_executions INTEGER DEFAULT 0,

    -- Performance metrics
    total_duration_ms BIGINT DEFAULT 0,
    avg_duration_ms INTEGER,
    min_duration_ms INTEGER,
    max_duration_ms INTEGER,

    -- Record processing metrics
    total_records_processed BIGINT DEFAULT 0,
    total_records_failed BIGINT DEFAULT 0,
    avg_records_per_execution NUMERIC(10,2),

    -- Success rate
    success_rate_percent NUMERIC(5,2),

    -- Metadata
    computed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign keys
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL,

    -- Unique constraint for idempotent upserts
    UNIQUE (company_id, project_id, flow_name, metric_period, period_start)
);

CREATE INDEX IF NOT EXISTS idx_metrics_company ON transaction_metrics(company_id);
CREATE INDEX IF NOT EXISTS idx_metrics_project ON transaction_metrics(project_id);
CREATE INDEX IF NOT EXISTS idx_metrics_flow ON transaction_metrics(flow_name);
CREATE INDEX IF NOT EXISTS idx_metrics_period ON transaction_metrics(metric_period, period_start);
CREATE INDEX IF NOT EXISTS idx_metrics_company_period ON transaction_metrics(company_id, metric_period, period_start);

-- -----------------------------------------------------------------------------
-- ALERTING CONFIGURATION
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS alert_rules (
    id SERIAL PRIMARY KEY,

    company_id INTEGER NOT NULL,
    created_by_user_id INTEGER,

    rule_name VARCHAR(255) NOT NULL,
    description TEXT,

    project_id INTEGER,
    flow_name VARCHAR(255),

    -- Trigger conditions
    alert_on_failure BOOLEAN DEFAULT TRUE,
    alert_on_timeout BOOLEAN DEFAULT TRUE,
    failure_threshold INTEGER DEFAULT 1,
    threshold_window_minutes INTEGER DEFAULT 60,

    -- Notification settings
    notification_type VARCHAR(10) NOT NULL DEFAULT 'email'
        CHECK (notification_type IN ('email', 'webhook', 'both')),
    email_recipients TEXT,
    webhook_endpoint_ids VARCHAR(255),

    -- Alert frequency control
    cooldown_minutes INTEGER DEFAULT 30,
    max_alerts_per_day INTEGER DEFAULT 100,

    -- Rule state
    is_enabled BOOLEAN DEFAULT TRUE,
    last_triggered_at TIMESTAMP,
    alerts_sent_today INTEGER DEFAULT 0,

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_alert_rules_company ON alert_rules(company_id);
CREATE INDEX IF NOT EXISTS idx_alert_rules_project ON alert_rules(project_id);
CREATE INDEX IF NOT EXISTS idx_alert_rules_enabled ON alert_rules(is_enabled);
CREATE INDEX IF NOT EXISTS idx_alert_rules_company_enabled ON alert_rules(company_id, is_enabled);

-- Webhook endpoints
CREATE TABLE IF NOT EXISTS webhook_endpoints (
    id SERIAL PRIMARY KEY,

    company_id INTEGER NOT NULL,
    created_by_user_id INTEGER,

    endpoint_name VARCHAR(255) NOT NULL,
    endpoint_url VARCHAR(1000) NOT NULL,
    http_method VARCHAR(10) DEFAULT 'POST'
        CHECK (http_method IN ('POST', 'PUT', 'PATCH')),

    -- Authentication
    auth_type VARCHAR(20) DEFAULT 'none'
        CHECK (auth_type IN ('none', 'basic', 'bearer', 'custom_header')),
    auth_username VARCHAR(255),
    auth_password VARCHAR(255),
    auth_token VARCHAR(500),
    custom_headers TEXT,

    -- Payload format
    payload_format VARCHAR(10) DEFAULT 'json'
        CHECK (payload_format IN ('json', 'form', 'xml')),
    payload_template TEXT,

    -- Reliability settings
    timeout_seconds INTEGER DEFAULT 30,
    retry_count INTEGER DEFAULT 3,
    retry_delay_seconds INTEGER DEFAULT 60,

    -- Endpoint health
    is_enabled BOOLEAN DEFAULT TRUE,
    last_success_at TIMESTAMP,
    last_failure_at TIMESTAMP,
    consecutive_failures INTEGER DEFAULT 0,

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_webhook_company ON webhook_endpoints(company_id);
CREATE INDEX IF NOT EXISTS idx_webhook_enabled ON webhook_endpoints(is_enabled);

-- Alert history
CREATE TABLE IF NOT EXISTS alert_history (
    id BIGSERIAL PRIMARY KEY,

    alert_rule_id INTEGER NOT NULL,
    execution_id BIGINT,

    alert_type VARCHAR(10) NOT NULL
        CHECK (alert_type IN ('email', 'webhook')),
    recipient VARCHAR(500) NOT NULL,

    subject VARCHAR(500),
    message TEXT,
    payload_sent TEXT,

    -- Delivery status
    status VARCHAR(20) NOT NULL DEFAULT 'pending'
        CHECK (status IN ('pending', 'sent', 'failed', 'retrying')),
    status_message TEXT,
    sent_at TIMESTAMP,
    delivered_at TIMESTAMP,

    -- Retry tracking
    retry_count INTEGER DEFAULT 0,
    next_retry_at TIMESTAMP,

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (alert_rule_id) REFERENCES alert_rules(id) ON DELETE CASCADE,
    FOREIGN KEY (execution_id) REFERENCES transaction_executions(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_alert_history_rule ON alert_history(alert_rule_id);
CREATE INDEX IF NOT EXISTS idx_alert_history_execution ON alert_history(execution_id);
CREATE INDEX IF NOT EXISTS idx_alert_history_status ON alert_history(status);
CREATE INDEX IF NOT EXISTS idx_alert_history_created ON alert_history(created_at);
CREATE INDEX IF NOT EXISTS idx_alert_history_retry ON alert_history(status, next_retry_at);

-- -----------------------------------------------------------------------------
-- AUTO-UPDATE TIMESTAMPS (reuse function from postgres_schema.sql)
-- -----------------------------------------------------------------------------

DO $$
DECLARE
    tbl TEXT;
BEGIN
    FOR tbl IN SELECT unnest(ARRAY[
        'transaction_executions', 'transaction_metrics', 'alert_rules', 'webhook_endpoints'
    ]) LOOP
        EXECUTE format(
            'DROP TRIGGER IF EXISTS update_%s_timestamp ON %I; ' ||
            'CREATE TRIGGER update_%s_timestamp BEFORE UPDATE ON %I ' ||
            'FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();',
            tbl, tbl, tbl, tbl
        );
    END LOOP;
END $$;

-- -----------------------------------------------------------------------------
-- DEFAULT MONITORING SETTINGS
-- -----------------------------------------------------------------------------

INSERT INTO settings (setting_key, setting_value, description) VALUES
    ('monitoring_enabled', 'true', 'Enable transaction monitoring and logging'),
    ('monitoring_retention_days', '90', 'Days to retain detailed execution logs'),
    ('metrics_retention_days', '365', 'Days to retain aggregated metrics'),
    ('alert_history_retention_days', '180', 'Days to retain alert history'),
    ('metrics_aggregation_enabled', 'true', 'Enable automatic metrics aggregation'),
    ('default_alert_cooldown_minutes', '30', 'Default alert cooldown period'),
    ('max_payload_size_mb', '10', 'Maximum payload size to store (MB)'),
    ('monitoring_db_version', '1.0.0', 'Monitoring schema version')
ON CONFLICT (setting_key) DO NOTHING;

-- -----------------------------------------------------------------------------
-- VIEWS
-- -----------------------------------------------------------------------------

CREATE OR REPLACE VIEW v_recent_executions AS
SELECT
    te.id,
    te.execution_id,
    te.flow_name,
    te.flow_type,
    te.status,
    te.started_at,
    te.completed_at,
    te.duration_ms,
    te.records_processed,
    te.records_failed,
    te.error_message,
    c.company_name,
    c.id AS company_id,
    p.name AS project_name,
    p.id AS project_id
FROM transaction_executions te
LEFT JOIN companies c ON te.company_id = c.id
LEFT JOIN projects p ON te.project_id = p.id
WHERE te.started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours'
ORDER BY te.started_at DESC;

CREATE OR REPLACE VIEW v_system_health AS
SELECT
    COUNT(CASE WHEN status = 'running' THEN 1 END) AS running_count,
    COUNT(CASE WHEN status = 'success' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN 1 END) AS success_24h,
    COUNT(CASE WHEN status = 'failed' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN 1 END) AS failed_24h,
    COUNT(CASE WHEN status = 'success' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '7 days' THEN 1 END) AS success_7d,
    COUNT(CASE WHEN status = 'failed' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '7 days' THEN 1 END) AS failed_7d,
    ROUND(
        COUNT(CASE WHEN status = 'success' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN 1 END) * 100.0 /
        NULLIF(COUNT(CASE WHEN status IN ('success', 'failed') AND started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN 1 END), 0),
        2
    ) AS success_rate_24h_percent,
    AVG(CASE WHEN status = 'success' AND started_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours' THEN duration_ms END) AS avg_duration_ms_24h
FROM transaction_executions;

CREATE OR REPLACE VIEW v_active_alerts AS
SELECT
    ar.id,
    ar.rule_name,
    ar.company_id,
    c.company_name,
    ar.flow_name,
    ar.notification_type,
    ar.is_enabled,
    ar.last_triggered_at,
    ar.alerts_sent_today,
    COUNT(ah.id) AS total_alerts_sent
FROM alert_rules ar
LEFT JOIN companies c ON ar.company_id = c.id
LEFT JOIN alert_history ah ON ar.id = ah.alert_rule_id
WHERE ar.is_enabled = TRUE
GROUP BY ar.id, ar.rule_name, ar.company_id, c.company_name, ar.flow_name,
         ar.notification_type, ar.is_enabled, ar.last_triggered_at, ar.alerts_sent_today;

-- -----------------------------------------------------------------------------
-- ROW LEVEL SECURITY (Supabase requirement)
-- -----------------------------------------------------------------------------

ALTER TABLE transaction_executions ENABLE ROW LEVEL SECURITY;
ALTER TABLE transaction_payloads   ENABLE ROW LEVEL SECURITY;
ALTER TABLE transaction_metrics    ENABLE ROW LEVEL SECURITY;
ALTER TABLE alert_rules            ENABLE ROW LEVEL SECURITY;
ALTER TABLE webhook_endpoints      ENABLE ROW LEVEL SECURITY;
ALTER TABLE alert_history          ENABLE ROW LEVEL SECURITY;

-- =============================================================================
-- END OF MONITORING SCHEMA (PostgreSQL)
-- =============================================================================
