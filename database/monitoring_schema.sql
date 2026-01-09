-- =============================================================================
-- IW_IDE Monitoring Database Schema
-- Real-Time Integration Monitoring Dashboard
-- =============================================================================
-- This schema extends the existing mysql_schema.sql with comprehensive
-- monitoring capabilities for transaction execution tracking, metrics,
-- and alerting.
-- =============================================================================

-- Use InnoDB for foreign key support and transactions
SET default_storage_engine = InnoDB;

-- -----------------------------------------------------------------------------
-- TRANSACTION EXECUTION TRACKING
-- Detailed logging of individual transaction executions
-- -----------------------------------------------------------------------------

-- Transaction executions table - detailed execution log
-- Tracks every transaction flow execution with detailed metadata
CREATE TABLE IF NOT EXISTS transaction_executions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    execution_id VARCHAR(100) UNIQUE NOT NULL COMMENT 'Unique identifier for this execution',

    -- Reference fields
    company_id INT COMMENT 'Company that owns this transaction',
    project_id INT COMMENT 'Project containing the transaction flow',
    transformation_id INT COMMENT 'Transformation/flow being executed',
    flow_name VARCHAR(255) NOT NULL COMMENT 'Name of the transaction flow',
    flow_type ENUM('transaction', 'utility', 'query') DEFAULT 'transaction' COMMENT 'Type of integration flow',

    -- Execution metadata
    status ENUM('running', 'success', 'failed', 'cancelled', 'timeout') NOT NULL DEFAULT 'running',
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When execution started',
    completed_at TIMESTAMP NULL COMMENT 'When execution finished',
    duration_ms INT UNSIGNED COMMENT 'Execution duration in milliseconds',

    -- Processing metrics
    records_processed INT UNSIGNED DEFAULT 0 COMMENT 'Number of records successfully processed',
    records_failed INT UNSIGNED DEFAULT 0 COMMENT 'Number of records that failed',
    records_skipped INT UNSIGNED DEFAULT 0 COMMENT 'Number of records skipped',

    -- Error information
    error_message TEXT COMMENT 'Primary error message for failed executions',
    error_code VARCHAR(50) COMMENT 'Error code or exception class',
    stack_trace TEXT COMMENT 'Full stack trace for debugging',

    -- Environment context
    triggered_by ENUM('scheduler', 'manual', 'webhook', 'api') DEFAULT 'scheduler',
    triggered_by_user_id INT COMMENT 'User who triggered manual execution',
    server_hostname VARCHAR(100) COMMENT 'Server that executed the transaction',

    -- Indexing and timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE SET NULL,

    -- Performance indexes
    INDEX idx_exec_company (company_id),
    INDEX idx_exec_project (project_id),
    INDEX idx_exec_transformation (transformation_id),
    INDEX idx_exec_status (status),
    INDEX idx_exec_started (started_at),
    INDEX idx_exec_flow_name (flow_name),
    INDEX idx_exec_flow_type (flow_type),
    INDEX idx_exec_company_status_started (company_id, status, started_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Detailed transaction execution log';

-- Transaction payloads table - stores request/response data
-- Separated from executions to keep main table lean and fast
CREATE TABLE IF NOT EXISTS transaction_payloads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    execution_id BIGINT NOT NULL COMMENT 'Reference to transaction execution',

    -- Payload data
    payload_type ENUM('request', 'response', 'error_context', 'debug') NOT NULL,
    payload_format ENUM('xml', 'json', 'text', 'binary') DEFAULT 'xml',
    payload_data LONGTEXT COMMENT 'Actual payload content',
    payload_size INT UNSIGNED COMMENT 'Size in bytes',

    -- Metadata
    captured_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'When payload was captured',
    source_system VARCHAR(100) COMMENT 'System that generated this payload',
    destination_system VARCHAR(100) COMMENT 'Target system for this payload',

    -- Foreign key constraint
    FOREIGN KEY (execution_id) REFERENCES transaction_executions(id) ON DELETE CASCADE,

    -- Indexes
    INDEX idx_payload_execution (execution_id),
    INDEX idx_payload_type (payload_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Transaction request/response payloads';

-- -----------------------------------------------------------------------------
-- AGGREGATED METRICS
-- Pre-computed metrics for fast dashboard queries and trend analysis
-- -----------------------------------------------------------------------------

-- Transaction metrics table - aggregated performance metrics
-- Stores hourly and daily rollups for efficient querying
CREATE TABLE IF NOT EXISTS transaction_metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Dimension fields
    company_id INT COMMENT 'Company for multi-tenant filtering',
    project_id INT COMMENT 'Project for filtering',
    flow_name VARCHAR(255) COMMENT 'Specific flow or NULL for all flows',

    -- Time dimension
    metric_period ENUM('hourly', 'daily', 'weekly') NOT NULL,
    period_start TIMESTAMP NOT NULL COMMENT 'Start of the aggregation period',
    period_end TIMESTAMP NOT NULL COMMENT 'End of the aggregation period',

    -- Execution counts
    total_executions INT UNSIGNED DEFAULT 0 COMMENT 'Total executions in period',
    successful_executions INT UNSIGNED DEFAULT 0 COMMENT 'Successful executions',
    failed_executions INT UNSIGNED DEFAULT 0 COMMENT 'Failed executions',
    cancelled_executions INT UNSIGNED DEFAULT 0 COMMENT 'Cancelled executions',
    timeout_executions INT UNSIGNED DEFAULT 0 COMMENT 'Timeout executions',

    -- Performance metrics
    total_duration_ms BIGINT UNSIGNED DEFAULT 0 COMMENT 'Sum of all execution durations',
    avg_duration_ms INT UNSIGNED COMMENT 'Average execution duration',
    min_duration_ms INT UNSIGNED COMMENT 'Fastest execution',
    max_duration_ms INT UNSIGNED COMMENT 'Slowest execution',

    -- Record processing metrics
    total_records_processed BIGINT UNSIGNED DEFAULT 0 COMMENT 'Total records processed',
    total_records_failed BIGINT UNSIGNED DEFAULT 0 COMMENT 'Total records failed',
    avg_records_per_execution DECIMAL(10,2) COMMENT 'Average records per execution',

    -- Success rate (denormalized for quick access)
    success_rate_percent DECIMAL(5,2) COMMENT 'Success rate as percentage',

    -- Metadata
    computed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'When metrics were computed',

    -- Foreign keys
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL,

    -- Indexes for fast metric queries
    INDEX idx_metrics_company (company_id),
    INDEX idx_metrics_project (project_id),
    INDEX idx_metrics_flow (flow_name),
    INDEX idx_metrics_period (metric_period, period_start),
    INDEX idx_metrics_company_period (company_id, metric_period, period_start),
    UNIQUE KEY uk_metrics_unique (company_id, project_id, flow_name, metric_period, period_start)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Aggregated transaction metrics';

-- -----------------------------------------------------------------------------
-- ALERTING CONFIGURATION
-- Configurable alert rules and notification endpoints
-- -----------------------------------------------------------------------------

-- Alert rules table - defines when and how to send alerts
CREATE TABLE IF NOT EXISTS alert_rules (
    id INT AUTO_INCREMENT PRIMARY KEY,

    -- Ownership
    company_id INT NOT NULL COMMENT 'Company that owns this alert rule',
    created_by_user_id INT COMMENT 'User who created this rule',

    -- Rule identification
    rule_name VARCHAR(255) NOT NULL COMMENT 'Descriptive name for this rule',
    description TEXT COMMENT 'Detailed description of alert purpose',

    -- Scope - what to monitor
    project_id INT COMMENT 'Specific project or NULL for all projects',
    flow_name VARCHAR(255) COMMENT 'Specific flow or NULL for all flows',

    -- Trigger conditions
    alert_on_failure TINYINT(1) DEFAULT 1 COMMENT 'Alert on any failure',
    alert_on_timeout TINYINT(1) DEFAULT 1 COMMENT 'Alert on timeout',
    failure_threshold INT UNSIGNED DEFAULT 1 COMMENT 'Number of failures before alerting',
    threshold_window_minutes INT UNSIGNED DEFAULT 60 COMMENT 'Time window for threshold',

    -- Notification settings
    notification_type ENUM('email', 'webhook', 'both') NOT NULL DEFAULT 'email',
    email_recipients TEXT COMMENT 'Comma-separated email addresses',
    webhook_endpoint_ids VARCHAR(255) COMMENT 'Comma-separated webhook endpoint IDs',

    -- Alert frequency control
    cooldown_minutes INT UNSIGNED DEFAULT 30 COMMENT 'Min time between alerts for same issue',
    max_alerts_per_day INT UNSIGNED DEFAULT 100 COMMENT 'Max alerts to send in 24h',

    -- Rule state
    is_enabled TINYINT(1) DEFAULT 1 COMMENT 'Whether rule is active',
    last_triggered_at TIMESTAMP NULL COMMENT 'When alert was last sent',
    alerts_sent_today INT UNSIGNED DEFAULT 0 COMMENT 'Count of alerts sent today',

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign keys
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,

    -- Indexes
    INDEX idx_alert_rules_company (company_id),
    INDEX idx_alert_rules_project (project_id),
    INDEX idx_alert_rules_enabled (is_enabled),
    INDEX idx_alert_rules_company_enabled (company_id, is_enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Alert rule configuration';

-- Webhook endpoints table - external notification endpoints
CREATE TABLE IF NOT EXISTS webhook_endpoints (
    id INT AUTO_INCREMENT PRIMARY KEY,

    -- Ownership
    company_id INT NOT NULL COMMENT 'Company that owns this webhook',
    created_by_user_id INT COMMENT 'User who created this endpoint',

    -- Endpoint configuration
    endpoint_name VARCHAR(255) NOT NULL COMMENT 'Descriptive name',
    endpoint_url VARCHAR(1000) NOT NULL COMMENT 'Full webhook URL',
    http_method ENUM('POST', 'PUT', 'PATCH') DEFAULT 'POST',

    -- Authentication
    auth_type ENUM('none', 'basic', 'bearer', 'custom_header') DEFAULT 'none',
    auth_username VARCHAR(255) COMMENT 'For basic auth',
    auth_password VARCHAR(255) COMMENT 'For basic auth',
    auth_token VARCHAR(500) COMMENT 'For bearer token auth',
    custom_headers TEXT COMMENT 'JSON object with custom headers',

    -- Payload format
    payload_format ENUM('json', 'form', 'xml') DEFAULT 'json',
    payload_template TEXT COMMENT 'Custom payload template',

    -- Reliability settings
    timeout_seconds INT UNSIGNED DEFAULT 30 COMMENT 'HTTP request timeout',
    retry_count INT UNSIGNED DEFAULT 3 COMMENT 'Number of retries on failure',
    retry_delay_seconds INT UNSIGNED DEFAULT 60 COMMENT 'Delay between retries',

    -- Endpoint health
    is_enabled TINYINT(1) DEFAULT 1 COMMENT 'Whether endpoint is active',
    last_success_at TIMESTAMP NULL COMMENT 'Last successful delivery',
    last_failure_at TIMESTAMP NULL COMMENT 'Last failed delivery',
    consecutive_failures INT UNSIGNED DEFAULT 0 COMMENT 'Consecutive failed attempts',

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign keys
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE SET NULL,

    -- Indexes
    INDEX idx_webhook_company (company_id),
    INDEX idx_webhook_enabled (is_enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Webhook notification endpoints';

-- Alert history table - log of sent alerts
CREATE TABLE IF NOT EXISTS alert_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Alert context
    alert_rule_id INT NOT NULL COMMENT 'Rule that triggered this alert',
    execution_id BIGINT COMMENT 'Transaction execution that triggered alert',

    -- Alert details
    alert_type ENUM('email', 'webhook') NOT NULL,
    recipient VARCHAR(500) NOT NULL COMMENT 'Email address or webhook URL',

    -- Alert content
    subject VARCHAR(500) COMMENT 'Email subject or webhook event name',
    message TEXT COMMENT 'Alert message body',
    payload_sent TEXT COMMENT 'Full payload/body sent',

    -- Delivery status
    status ENUM('pending', 'sent', 'failed', 'retrying') NOT NULL DEFAULT 'pending',
    status_message TEXT COMMENT 'Delivery status or error message',
    sent_at TIMESTAMP NULL COMMENT 'When alert was sent',
    delivered_at TIMESTAMP NULL COMMENT 'When delivery was confirmed',

    -- Retry tracking
    retry_count INT UNSIGNED DEFAULT 0 COMMENT 'Number of retry attempts',
    next_retry_at TIMESTAMP NULL COMMENT 'When to retry if failed',

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign keys
    FOREIGN KEY (alert_rule_id) REFERENCES alert_rules(id) ON DELETE CASCADE,
    FOREIGN KEY (execution_id) REFERENCES transaction_executions(id) ON DELETE SET NULL,

    -- Indexes
    INDEX idx_alert_history_rule (alert_rule_id),
    INDEX idx_alert_history_execution (execution_id),
    INDEX idx_alert_history_status (status),
    INDEX idx_alert_history_created (created_at),
    INDEX idx_alert_history_retry (status, next_retry_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='History of sent alerts';

-- -----------------------------------------------------------------------------
-- CONFIGURATION AND SETTINGS
-- System-level monitoring configuration
-- -----------------------------------------------------------------------------

-- Insert default monitoring settings
INSERT IGNORE INTO settings (setting_key, setting_value, description) VALUES
    ('monitoring_enabled', 'true', 'Enable transaction monitoring and logging'),
    ('monitoring_retention_days', '90', 'Days to retain detailed execution logs'),
    ('metrics_retention_days', '365', 'Days to retain aggregated metrics'),
    ('alert_history_retention_days', '180', 'Days to retain alert history'),
    ('metrics_aggregation_enabled', 'true', 'Enable automatic metrics aggregation'),
    ('default_alert_cooldown_minutes', '30', 'Default alert cooldown period'),
    ('max_payload_size_mb', '10', 'Maximum payload size to store (MB)'),
    ('monitoring_db_version', '1.0.0', 'Monitoring schema version');

-- -----------------------------------------------------------------------------
-- VIEWS FOR CONVENIENT QUERIES
-- Commonly used data aggregations
-- -----------------------------------------------------------------------------

-- View: Recent transaction executions with company and project details
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
WHERE te.started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
ORDER BY te.started_at DESC;

-- View: Current system health summary
CREATE OR REPLACE VIEW v_system_health AS
SELECT
    COUNT(CASE WHEN status = 'running' THEN 1 END) AS running_count,
    COUNT(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) AS success_24h,
    COUNT(CASE WHEN status = 'failed' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) AS failed_24h,
    COUNT(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 END) AS success_7d,
    COUNT(CASE WHEN status = 'failed' AND started_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) THEN 1 END) AS failed_7d,
    ROUND(
        COUNT(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END) * 100.0 /
        NULLIF(COUNT(CASE WHEN status IN ('success', 'failed') AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN 1 END), 0),
        2
    ) AS success_rate_24h_percent,
    AVG(CASE WHEN status = 'success' AND started_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR) THEN duration_ms END) AS avg_duration_ms_24h
FROM transaction_executions;

-- View: Active alert rules summary
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
WHERE ar.is_enabled = 1
GROUP BY ar.id, ar.rule_name, ar.company_id, c.company_name, ar.flow_name,
         ar.notification_type, ar.is_enabled, ar.last_triggered_at, ar.alerts_sent_today;

-- =============================================================================
-- END OF MONITORING SCHEMA
-- =============================================================================
