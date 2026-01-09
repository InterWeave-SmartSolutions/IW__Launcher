-- =============================================================================
-- IW_IDE Monitoring Test Data Generator
-- File: monitoring_test_data.sql
-- Description: Generates sample transaction data for testing the monitoring dashboard
-- =============================================================================
-- This script generates 100+ sample transaction executions with:
-- - Mix of successful and failed transactions
-- - Varying execution times and record counts
-- - Spans multiple days for trend testing
-- - Can be run multiple times safely (idempotent)
-- =============================================================================

USE iw_ide;

-- =============================================================================
-- SECTION 1: CLEANUP (Idempotent - Remove old test data)
-- =============================================================================

-- Delete test transaction payloads first (due to foreign key constraints)
DELETE FROM transaction_payloads
WHERE execution_id IN (
    SELECT id FROM transaction_executions
    WHERE execution_id LIKE 'TEST-%'
);

-- Delete test transaction executions
DELETE FROM transaction_executions
WHERE execution_id LIKE 'TEST-%';

-- Delete test alert history
DELETE FROM alert_history
WHERE alert_rule_id IN (
    SELECT id FROM alert_rules
    WHERE rule_name LIKE 'Test Alert%'
);

-- Delete test alert rules
DELETE FROM alert_rules
WHERE rule_name LIKE 'Test Alert%';

-- =============================================================================
-- SECTION 2: REFERENCE DATA (Get company_id, project_id, transformation_id)
-- =============================================================================

-- Get the first available company (usually admin company or create test company)
SET @test_company_id = (SELECT id FROM companies ORDER BY id LIMIT 1);

-- Get or create test project
INSERT INTO projects (company_id, name, description, status)
VALUES (@test_company_id, 'Test Integration Project', 'Test project for monitoring dashboard', 'active')
ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id);

SET @test_project_id = (SELECT id FROM projects WHERE name = 'Test Integration Project' LIMIT 1);

-- Get or create test transformation
INSERT INTO transformations (project_id, name, description, xslt_content)
VALUES (
    @test_project_id,
    'Test Transformation Flow',
    'Sample transformation for testing',
    '<?xml version="1.0"?><xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"></xsl:stylesheet>'
)
ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id);

SET @test_transformation_id = (SELECT id FROM transformations WHERE name = 'Test Transformation Flow' LIMIT 1);

-- =============================================================================
-- SECTION 3: GENERATE TRANSACTION EXECUTIONS (120 total)
-- Distribution:
-- - 70% successful (84 transactions)
-- - 20% failed (24 transactions)
-- - 5% timeout (6 transactions)
-- - 3% cancelled (4 transactions)
-- - 2% running (2 transactions)
-- Time distribution: Last 7 days with more recent activity
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Day 7 (oldest) - 10 executions
-- -----------------------------------------------------------------------------

-- Successful executions (7)
INSERT INTO transaction_executions (
    execution_id, company_id, project_id, transformation_id,
    flow_name, flow_type, status,
    started_at, completed_at, duration_ms,
    records_processed, records_failed, records_skipped,
    triggered_by, server_hostname
) VALUES
('TEST-D7-001', @test_company_id, @test_project_id, @test_transformation_id,
    'Salesforce to QuickBooks Customer Sync', 'transaction', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 3 SECOND, 3245,
    25, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D7-002', @test_company_id, @test_project_id, @test_transformation_id,
    'QuickBooks Invoice Export', 'transaction', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 2 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 2 HOUR + INTERVAL 5 SECOND, 5123,
    42, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D7-003', @test_company_id, @test_project_id, @test_transformation_id,
    'Order Status Update', 'utility', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 4 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 4 HOUR + INTERVAL 2 SECOND, 2156,
    15, 0, 0, 'manual', 'iw-server-01'),
('TEST-D7-004', @test_company_id, @test_project_id, @test_transformation_id,
    'Product Catalog Sync', 'transaction', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 8 HOUR + INTERVAL 12 SECOND, 12456,
    156, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D7-005', @test_company_id, @test_project_id, @test_transformation_id,
    'Customer Payment Sync', 'transaction', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 12 HOUR + INTERVAL 4 SECOND, 4234,
    33, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D7-006', @test_company_id, @test_project_id, @test_transformation_id,
    'Inventory Level Update', 'transaction', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 16 HOUR + INTERVAL 6 SECOND, 6345,
    78, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D7-007', @test_company_id, @test_project_id, @test_transformation_id,
    'Sales Report Generation', 'query', 'success',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 20 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 20 HOUR + INTERVAL 1 SECOND, 1234,
    1, 0, 0, 'api', 'iw-server-01');

-- Failed executions (2)
INSERT INTO transaction_executions (
    execution_id, company_id, project_id, transformation_id,
    flow_name, flow_type, status,
    started_at, completed_at, duration_ms,
    records_processed, records_failed, records_skipped,
    error_message, error_code, stack_trace,
    triggered_by, server_hostname
) VALUES
('TEST-D7-008', @test_company_id, @test_project_id, @test_transformation_id,
    'Salesforce to QuickBooks Customer Sync', 'transaction', 'failed',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 14 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 14 HOUR + INTERVAL 2 SECOND, 2456,
    12, 3, 0,
    'Connection timeout to QuickBooks API', 'QB_TIMEOUT_ERROR',
    'java.net.SocketTimeoutException: connect timed out\n\tat java.net.PlainSocketImpl.socketConnect(Native Method)\n\tat java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)',
    'scheduler', 'iw-server-01'),
('TEST-D7-009', @test_company_id, @test_project_id, @test_transformation_id,
    'Product Catalog Sync', 'transaction', 'failed',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 18 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 18 HOUR + INTERVAL 5 SECOND, 5123,
    45, 8, 0,
    'Invalid product SKU format in source data', 'VALIDATION_ERROR',
    'com.interweave.exception.ValidationException: Invalid SKU format\n\tat com.interweave.validators.ProductValidator.validate(ProductValidator.java:42)',
    'scheduler', 'iw-server-02');

-- Timeout execution (1)
INSERT INTO transaction_executions (
    execution_id, company_id, project_id, transformation_id,
    flow_name, flow_type, status,
    started_at, completed_at, duration_ms,
    records_processed, records_failed, records_skipped,
    error_message, error_code,
    triggered_by, server_hostname
) VALUES
('TEST-D7-010', @test_company_id, @test_project_id, @test_transformation_id,
    'Large Order Batch Import', 'transaction', 'timeout',
    DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 22 HOUR, DATE_SUB(NOW(), INTERVAL 7 DAY) + INTERVAL 22 HOUR + INTERVAL 30 MINUTE, 1800000,
    1250, 0, 0,
    'Transaction exceeded maximum execution time of 30 minutes', 'EXECUTION_TIMEOUT',
    'scheduler', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 6 - 12 executions
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (
    execution_id, company_id, project_id, transformation_id,
    flow_name, flow_type, status,
    started_at, completed_at, duration_ms,
    records_processed, records_failed, records_skipped,
    triggered_by, server_hostname
) VALUES
('TEST-D6-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 1 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 1 HOUR + INTERVAL 3 SECOND, 3412, 28, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D6-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 3 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 3 HOUR + INTERVAL 6 SECOND, 6234, 51, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D6-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 5 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 5 HOUR + INTERVAL 2 SECOND, 2345, 18, 0, 0, 'manual', 'iw-server-01'),
('TEST-D6-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 7 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 7 HOUR + INTERVAL 14 SECOND, 14567, 178, 0, 3, 'scheduler', 'iw-server-02'),
('TEST-D6-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 9 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 9 HOUR + INTERVAL 5 SECOND, 5123, 39, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D6-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 11 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 11 HOUR + INTERVAL 7 SECOND, 7234, 89, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D6-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 13 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 13 HOUR + INTERVAL 1 SECOND, 1456, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D6-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 15 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 15 HOUR + INTERVAL 4 SECOND, 4123, 32, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D6-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 17 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 17 HOUR + INTERVAL 5 SECOND, 5345, 44, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D6-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 19 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 19 HOUR + INTERVAL 11 SECOND, 11234, 142, 0, 1, 'scheduler', 'iw-server-01');

-- Failed executions for Day 6 (2)
INSERT INTO transaction_executions (
    execution_id, company_id, project_id, transformation_id,
    flow_name, flow_type, status,
    started_at, completed_at, duration_ms,
    records_processed, records_failed, records_skipped,
    error_message, error_code, stack_trace,
    triggered_by, server_hostname
) VALUES
('TEST-D6-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 21 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 21 HOUR + INTERVAL 3 SECOND, 3234, 15, 5, 0, 'Authentication failed with payment gateway', 'AUTH_FAILURE', 'com.interweave.exception.AuthenticationException: Invalid API credentials\n\tat com.interweave.connectors.PaymentGateway.authenticate(PaymentGateway.java:67)', 'scheduler', 'iw-server-02'),
('TEST-D6-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 23 HOUR, DATE_SUB(NOW(), INTERVAL 6 DAY) + INTERVAL 23 HOUR + INTERVAL 2 SECOND, 2456, 8, 2, 0, 'Database connection lost during transaction', 'DB_CONNECTION_ERROR', 'java.sql.SQLException: Connection refused\n\tat com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1073)', 'scheduler', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 5 - 14 executions
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D5-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 0 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 0 HOUR + INTERVAL 3 SECOND, 3567, 31, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D5-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 2 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 2 HOUR + INTERVAL 5 SECOND, 5678, 47, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D5-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 4 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 4 HOUR + INTERVAL 2 SECOND, 2234, 16, 0, 0, 'manual', 'iw-server-01'),
('TEST-D5-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 6 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 6 HOUR + INTERVAL 13 SECOND, 13456, 165, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D5-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 8 HOUR + INTERVAL 4 SECOND, 4567, 36, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D5-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 10 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 10 HOUR + INTERVAL 6 SECOND, 6789, 82, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D5-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 12 HOUR + INTERVAL 1 SECOND, 1567, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D5-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 14 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 14 HOUR + INTERVAL 3 SECOND, 3789, 29, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D5-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 16 HOUR + INTERVAL 5 SECOND, 5234, 43, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D5-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 18 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 18 HOUR + INTERVAL 12 SECOND, 12345, 151, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D5-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 20 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 20 HOUR + INTERVAL 4 SECOND, 4321, 34, 0, 0, 'scheduler', 'iw-server-02');

-- Failed and cancelled for Day 5 (3)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, error_message, error_code, stack_trace, triggered_by, server_hostname) VALUES
('TEST-D5-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 22 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 22 HOUR + INTERVAL 4 SECOND, 4123, 22, 7, 0, 'XML parsing error in response', 'XML_PARSE_ERROR', 'org.xml.sax.SAXParseException: Content is not allowed in prolog\n\tat com.sun.org.apache.xerces.internal.parsers.DOMParser.parse(DOMParser.java:257)', 'scheduler', 'iw-server-01'),
('TEST-D5-013', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 23 HOUR, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 23 HOUR + INTERVAL 2 SECOND, 2678, 10, 4, 0, 'Salesforce API rate limit exceeded', 'RATE_LIMIT_ERROR', 'com.sforce.ws.ConnectionException: Rate limit exceeded\n\tat com.sforce.ws.SforceService.invoke(SforceService.java:789)', 'scheduler', 'iw-server-02'),
('TEST-D5-014', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'cancelled', DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 23 HOUR + INTERVAL 30 MINUTE, DATE_SUB(NOW(), INTERVAL 5 DAY) + INTERVAL 23 HOUR + INTERVAL 32 MINUTE, 120000, 450, 0, 0, 'User cancelled transaction', 'USER_CANCELLED', NULL, 'manual', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 4 - 16 executions
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D4-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 0 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 0 HOUR + INTERVAL 4 SECOND, 4123, 33, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D4-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 2 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 2 HOUR + INTERVAL 6 SECOND, 6123, 49, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D4-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 4 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 4 HOUR + INTERVAL 2 SECOND, 2456, 19, 0, 0, 'manual', 'iw-server-01'),
('TEST-D4-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 6 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 6 HOUR + INTERVAL 15 SECOND, 15234, 189, 0, 3, 'scheduler', 'iw-server-02'),
('TEST-D4-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 8 HOUR + INTERVAL 5 SECOND, 5234, 41, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D4-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 10 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 10 HOUR + INTERVAL 7 SECOND, 7456, 91, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D4-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 12 HOUR + INTERVAL 1 SECOND, 1678, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D4-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 14 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 14 HOUR + INTERVAL 3 SECOND, 3456, 27, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D4-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 16 HOUR + INTERVAL 5 SECOND, 5567, 46, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D4-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 18 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 18 HOUR + INTERVAL 13 SECOND, 13678, 168, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D4-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 20 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 20 HOUR + INTERVAL 4 SECOND, 4567, 37, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D4-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 22 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 22 HOUR + INTERVAL 6 SECOND, 6234, 76, 0, 1, 'scheduler', 'iw-server-01');

-- Failed, timeout, and cancelled for Day 4 (4)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, error_message, error_code, stack_trace, triggered_by, server_hostname) VALUES
('TEST-D4-013', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 10 HOUR + INTERVAL 30 MINUTE, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 10 HOUR + INTERVAL 35 MINUTE, 300000, 95, 12, 0, 'Duplicate SKU detected in batch', 'DUPLICATE_KEY_ERROR', 'java.sql.SQLIntegrityConstraintViolationException: Duplicate entry\n\tat com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1073)', 'scheduler', 'iw-server-02'),
('TEST-D4-014', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 15 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 15 HOUR + INTERVAL 3 SECOND, 3234, 18, 6, 0, 'Payment gateway returned HTTP 503', 'SERVICE_UNAVAILABLE', 'java.io.IOException: Server returned HTTP response code: 503\n\tat sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1625)', 'scheduler', 'iw-server-01'),
('TEST-D4-015', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'timeout', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 19 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 19 HOUR + INTERVAL 30 MINUTE, 1800000, 1180, 0, 0, 'Transaction exceeded maximum execution time', 'EXECUTION_TIMEOUT', NULL, 'scheduler', 'iw-server-02'),
('TEST-D4-016', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 21 HOUR, DATE_SUB(NOW(), INTERVAL 4 DAY) + INTERVAL 21 HOUR + INTERVAL 1 MINUTE, 60000, 23, 0, 0, 'Cancelled by administrator', 'ADMIN_CANCELLED', NULL, 'manual', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 3 - 18 executions
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D3-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 0 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 0 HOUR + INTERVAL 3 SECOND, 3789, 30, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D3-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 1 HOUR + INTERVAL 30 MINUTE, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 1 HOUR + INTERVAL 36 SECOND, 6234, 52, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D3-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 3 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 3 HOUR + INTERVAL 2 SECOND, 2567, 20, 0, 0, 'manual', 'iw-server-01'),
('TEST-D3-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 5 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 5 HOUR + INTERVAL 14 SECOND, 14789, 182, 0, 3, 'scheduler', 'iw-server-02'),
('TEST-D3-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 7 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 7 HOUR + INTERVAL 4 SECOND, 4789, 38, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D3-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 9 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 9 HOUR + INTERVAL 6 SECOND, 6890, 84, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D3-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 11 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 11 HOUR + INTERVAL 1 SECOND, 1789, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D3-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 13 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 13 HOUR + INTERVAL 3 SECOND, 3234, 26, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D3-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 15 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 15 HOUR + INTERVAL 5 SECOND, 5890, 48, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D3-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 17 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 17 HOUR + INTERVAL 12 SECOND, 12890, 158, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D3-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 19 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 19 HOUR + INTERVAL 4 SECOND, 4234, 35, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D3-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 21 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 21 HOUR + INTERVAL 6 SECOND, 6567, 80, 0, 1, 'scheduler', 'iw-server-01'),
('TEST-D3-013', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 23 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 23 HOUR + INTERVAL 3 SECOND, 3567, 29, 0, 0, 'scheduler', 'iw-server-02');

-- Failed and timeout for Day 3 (5)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, error_message, error_code, stack_trace, triggered_by, server_hostname) VALUES
('TEST-D3-014', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 8 HOUR + INTERVAL 4 SECOND, 4123, 25, 8, 0, 'QuickBooks connection refused', 'CONNECTION_REFUSED', 'java.net.ConnectException: Connection refused\n\tat java.net.PlainSocketImpl.socketConnect(Native Method)', 'scheduler', 'iw-server-01'),
('TEST-D3-015', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 12 HOUR + INTERVAL 6 SECOND, 6234, 54, 15, 0, 'Invalid XML in product data', 'XML_VALIDATION_ERROR', 'org.xml.sax.SAXException: Invalid element structure\n\tat org.apache.xerces.parsers.DOMParser.parse(DOMParser.java:257)', 'scheduler', 'iw-server-02'),
('TEST-D3-016', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 16 HOUR + INTERVAL 3 SECOND, 3456, 14, 5, 0, 'Payment API token expired', 'TOKEN_EXPIRED', 'com.interweave.exception.AuthenticationException: Token expired\n\tat com.interweave.auth.TokenValidator.validate(TokenValidator.java:45)', 'scheduler', 'iw-server-01'),
('TEST-D3-017', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'timeout', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 20 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 20 HOUR + INTERVAL 30 MINUTE, 1800000, 1320, 0, 0, 'Transaction exceeded maximum execution time', 'EXECUTION_TIMEOUT', NULL, 'scheduler', 'iw-server-02'),
('TEST-D3-018', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 22 HOUR, DATE_SUB(NOW(), INTERVAL 3 DAY) + INTERVAL 22 HOUR + INTERVAL 2 SECOND, 2789, 11, 3, 0, 'Out of memory during data processing', 'OUT_OF_MEMORY', 'java.lang.OutOfMemoryError: Java heap space\n\tat java.util.Arrays.copyOf(Arrays.java:3332)', 'scheduler', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 2 - 20 executions (more recent, more activity)
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D2-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 0 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 0 HOUR + INTERVAL 3 SECOND, 3234, 28, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D2-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 1 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 1 HOUR + INTERVAL 5 SECOND, 5456, 45, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D2-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 2 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 2 HOUR + INTERVAL 2 SECOND, 2123, 17, 0, 0, 'manual', 'iw-server-01'),
('TEST-D2-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 3 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 3 HOUR + INTERVAL 13 SECOND, 13234, 162, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D2-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 4 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 4 HOUR + INTERVAL 4 SECOND, 4345, 35, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D2-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 5 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 5 HOUR + INTERVAL 6 SECOND, 6123, 75, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D2-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 6 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 6 HOUR + INTERVAL 1 SECOND, 1234, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D2-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 7 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 7 HOUR + INTERVAL 3 SECOND, 3456, 27, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D2-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 8 HOUR + INTERVAL 5 SECOND, 5678, 47, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D2-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 9 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 9 HOUR + INTERVAL 12 SECOND, 12567, 154, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D2-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 10 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 10 HOUR + INTERVAL 4 SECOND, 4567, 36, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D2-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 11 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 11 HOUR + INTERVAL 6 SECOND, 6345, 77, 0, 1, 'scheduler', 'iw-server-01'),
('TEST-D2-013', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 12 HOUR + INTERVAL 3 SECOND, 3678, 29, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D2-014', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 13 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 13 HOUR + INTERVAL 5 SECOND, 5234, 43, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D2-015', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 14 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 14 HOUR + INTERVAL 13 SECOND, 13456, 165, 0, 2, 'scheduler', 'iw-server-02');

-- Failed and cancelled for Day 2 (5)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, error_message, error_code, stack_trace, triggered_by, server_hostname) VALUES
('TEST-D2-016', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 15 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 15 HOUR + INTERVAL 3 SECOND, 3234, 16, 6, 0, 'Payment gateway maintenance window', 'SERVICE_MAINTENANCE', 'java.io.IOException: Server returned HTTP response code: 503\n\tat sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1625)', 'scheduler', 'iw-server-01'),
('TEST-D2-017', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 16 HOUR + INTERVAL 2 SECOND, 2456, 9, 3, 0, 'Invalid inventory location code', 'VALIDATION_ERROR', 'com.interweave.exception.ValidationException: Invalid location\n\tat com.interweave.validators.LocationValidator.validate(LocationValidator.java:38)', 'scheduler', 'iw-server-02'),
('TEST-D2-018', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 17 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 17 HOUR + INTERVAL 2 SECOND, 2678, 11, 4, 0, 'Salesforce session expired', 'SESSION_EXPIRED', 'com.sforce.ws.ConnectionException: INVALID_SESSION_ID\n\tat com.sforce.ws.SforceService.invoke(SforceService.java:789)', 'scheduler', 'iw-server-01'),
('TEST-D2-019', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'timeout', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 18 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 18 HOUR + INTERVAL 30 MINUTE, 1800000, 1410, 0, 0, 'Transaction exceeded maximum execution time', 'EXECUTION_TIMEOUT', NULL, 'scheduler', 'iw-server-02'),
('TEST-D2-020', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'cancelled', DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 19 HOUR, DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 19 HOUR + INTERVAL 2 MINUTE, 120000, 67, 0, 0, 'Cancelled by system maintenance', 'SYSTEM_MAINTENANCE', NULL, 'scheduler', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Day 1 (Yesterday) - 22 executions (high activity day)
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D1-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 0 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 0 HOUR + INTERVAL 3 SECOND, 3123, 25, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D1-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 1 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 1 HOUR + INTERVAL 5 SECOND, 5234, 43, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D1-003', @test_company_id, @test_project_id, @test_transformation_id, 'Order Status Update', 'utility', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 2 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 2 HOUR + INTERVAL 2 SECOND, 2234, 18, 0, 0, 'manual', 'iw-server-01'),
('TEST-D1-004', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 3 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 3 HOUR + INTERVAL 14 SECOND, 14123, 173, 0, 3, 'scheduler', 'iw-server-02'),
('TEST-D1-005', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 4 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 4 HOUR + INTERVAL 4 SECOND, 4456, 37, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D1-006', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 5 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 5 HOUR + INTERVAL 6 SECOND, 6234, 76, 0, 1, 'scheduler', 'iw-server-02'),
('TEST-D1-007', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 6 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 6 HOUR + INTERVAL 1 SECOND, 1345, 1, 0, 0, 'api', 'iw-server-01'),
('TEST-D1-008', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 7 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 7 HOUR + INTERVAL 3 SECOND, 3345, 27, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D1-009', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 8 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 8 HOUR + INTERVAL 5 SECOND, 5456, 45, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D1-010', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 9 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 9 HOUR + INTERVAL 13 SECOND, 13234, 162, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D1-011', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 10 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 10 HOUR + INTERVAL 4 SECOND, 4567, 36, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D1-012', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 11 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 11 HOUR + INTERVAL 6 SECOND, 6456, 79, 0, 1, 'scheduler', 'iw-server-01'),
('TEST-D1-013', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 12 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 12 HOUR + INTERVAL 3 SECOND, 3567, 28, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D1-014', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 13 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 13 HOUR + INTERVAL 5 SECOND, 5345, 44, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D1-015', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 14 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 14 HOUR + INTERVAL 12 SECOND, 12678, 156, 0, 2, 'scheduler', 'iw-server-02'),
('TEST-D1-016', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 15 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 15 HOUR + INTERVAL 4 SECOND, 4345, 35, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D1-017', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 16 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 16 HOUR + INTERVAL 6 SECOND, 6123, 75, 0, 1, 'scheduler', 'iw-server-02');

-- Failed and timeout for Day 1 (5)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, error_message, error_code, stack_trace, triggered_by, server_hostname) VALUES
('TEST-D1-018', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 17 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 17 HOUR + INTERVAL 2 SECOND, 2345, 12, 4, 0, 'QuickBooks Web Connector offline', 'QB_CONNECTOR_OFFLINE', 'java.net.ConnectException: Connection refused\n\tat java.net.PlainSocketImpl.socketConnect(Native Method)', 'scheduler', 'iw-server-01'),
('TEST-D1-019', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 18 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 18 HOUR + INTERVAL 5 SECOND, 5123, 48, 12, 0, 'Corrupted product image data', 'DATA_CORRUPTION', 'java.io.IOException: Invalid image format\n\tat javax.imageio.ImageIO.read(ImageIO.java:1400)', 'scheduler', 'iw-server-02'),
('TEST-D1-020', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 19 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 19 HOUR + INTERVAL 3 SECOND, 3456, 17, 7, 0, 'Payment amount exceeds daily limit', 'LIMIT_EXCEEDED', 'com.interweave.exception.BusinessRuleException: Daily limit exceeded\n\tat com.interweave.rules.PaymentRules.validate(PaymentRules.java:89)', 'scheduler', 'iw-server-01'),
('TEST-D1-021', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'timeout', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 20 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 20 HOUR + INTERVAL 30 MINUTE, 1800000, 1485, 0, 0, 'Transaction exceeded maximum execution time', 'EXECUTION_TIMEOUT', NULL, 'scheduler', 'iw-server-02'),
('TEST-D1-022', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'failed', DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 21 HOUR, DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 21 HOUR + INTERVAL 2 SECOND, 2567, 10, 3, 0, 'Negative inventory quantity detected', 'BUSINESS_RULE_ERROR', 'com.interweave.exception.BusinessRuleException: Negative inventory\n\tat com.interweave.rules.InventoryRules.validate(InventoryRules.java:56)', 'scheduler', 'iw-server-01');

-- -----------------------------------------------------------------------------
-- Today - 8 executions (including 2 running)
-- -----------------------------------------------------------------------------

INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D0-001', @test_company_id, @test_project_id, @test_transformation_id, 'Salesforce to QuickBooks Customer Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR) + INTERVAL 3 SECOND, 3234, 26, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D0-002', @test_company_id, @test_project_id, @test_transformation_id, 'QuickBooks Invoice Export', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 5 HOUR), DATE_SUB(NOW(), INTERVAL 5 HOUR) + INTERVAL 5 SECOND, 5345, 44, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D0-003', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 4 HOUR), DATE_SUB(NOW(), INTERVAL 4 HOUR) + INTERVAL 13 SECOND, 13456, 165, 0, 2, 'scheduler', 'iw-server-01'),
('TEST-D0-004', @test_company_id, @test_project_id, @test_transformation_id, 'Customer Payment Sync', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR) + INTERVAL 4 SECOND, 4456, 36, 0, 0, 'scheduler', 'iw-server-02'),
('TEST-D0-005', @test_company_id, @test_project_id, @test_transformation_id, 'Inventory Level Update', 'transaction', 'success', DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR) + INTERVAL 6 SECOND, 6234, 76, 0, 1, 'scheduler', 'iw-server-01'),
('TEST-D0-006', @test_company_id, @test_project_id, @test_transformation_id, 'Sales Report Generation', 'query', 'success', DATE_SUB(NOW(), INTERVAL 1 HOUR), DATE_SUB(NOW(), INTERVAL 1 HOUR) + INTERVAL 1 SECOND, 1234, 1, 0, 0, 'api', 'iw-server-02');

-- Running transactions (currently executing)
INSERT INTO transaction_executions (execution_id, company_id, project_id, transformation_id, flow_name, flow_type, status, started_at, completed_at, duration_ms, records_processed, records_failed, records_skipped, triggered_by, server_hostname) VALUES
('TEST-D0-007', @test_company_id, @test_project_id, @test_transformation_id, 'Large Order Batch Import', 'transaction', 'running', DATE_SUB(NOW(), INTERVAL 5 MINUTE), NULL, NULL, 234, 0, 0, 'scheduler', 'iw-server-01'),
('TEST-D0-008', @test_company_id, @test_project_id, @test_transformation_id, 'Product Catalog Sync', 'transaction', 'running', DATE_SUB(NOW(), INTERVAL 2 MINUTE), NULL, NULL, 89, 0, 0, 'scheduler', 'iw-server-02');

-- =============================================================================
-- SECTION 4: SAMPLE TRANSACTION PAYLOADS (for drill-down testing)
-- Add payloads for a few transactions to enable payload viewing
-- =============================================================================

-- Sample payloads for successful Salesforce to QuickBooks sync
INSERT INTO transaction_payloads (execution_id, payload_type, payload_format, payload_data, payload_size, source_system, destination_system) VALUES
((SELECT id FROM transaction_executions WHERE execution_id = 'TEST-D0-001'), 'request', 'xml', '<?xml version="1.0"?>\n<SalesforceCustomerExport>\n  <Customer>\n    <Id>001XXXXXXXXXXXXXXX</Id>\n    <Name>Acme Corporation</Name>\n    <Email>contact@acme.com</Email>\n    <Phone>(555) 123-4567</Phone>\n    <BillingStreet>123 Main St</BillingStreet>\n    <BillingCity>San Francisco</BillingCity>\n    <BillingState>CA</BillingState>\n    <BillingPostalCode>94105</BillingPostalCode>\n  </Customer>\n</SalesforceCustomerExport>', 456, 'Salesforce', 'QuickBooks'),
((SELECT id FROM transaction_executions WHERE execution_id = 'TEST-D0-001'), 'response', 'xml', '<?xml version="1.0"?>\n<QuickBooksResponse>\n  <Status>Success</Status>\n  <CustomerRef>12345</CustomerRef>\n  <Message>Customer created successfully</Message>\n</QuickBooksResponse>', 187, 'QuickBooks', 'IW_Business_Daemon');

-- Sample payloads for failed transaction
INSERT INTO transaction_payloads (execution_id, payload_type, payload_format, payload_data, payload_size, source_system, destination_system) VALUES
((SELECT id FROM transaction_executions WHERE execution_id = 'TEST-D1-018'), 'request', 'xml', '<?xml version="1.0"?>\n<SalesforceCustomerExport>\n  <Customer>\n    <Id>001YYYYYYYYYYYYYYY</Id>\n    <Name>Test Company Inc</Name>\n    <Email>test@example.com</Email>\n  </Customer>\n</SalesforceCustomerExport>', 234, 'Salesforce', 'QuickBooks'),
((SELECT id FROM transaction_executions WHERE execution_id = 'TEST-D1-018'), 'error_context', 'text', 'Connection attempt to QuickBooks Web Connector at http://localhost:8080/qbwc failed.\nServer returned: Connection refused (Connection refused)\nAttempted at: 2026-01-09 17:00:00\nRetry count: 3\nAll retry attempts exhausted.', 215, 'IW_Business_Daemon', 'QuickBooks');

-- Sample JSON payload for invoice export
INSERT INTO transaction_payloads (execution_id, payload_type, payload_format, payload_data, payload_size, source_system, destination_system) VALUES
((SELECT id FROM transaction_executions WHERE execution_id = 'TEST-D0-002'), 'request', 'json', '{\n  "invoices": [\n    {\n      "invoiceNumber": "INV-2026-001",\n      "customerName": "Acme Corporation",\n      "invoiceDate": "2026-01-09",\n      "dueDate": "2026-02-08",\n      "lineItems": [\n        {\n          "description": "Professional Services",\n          "quantity": 40,\n          "unitPrice": 150.00,\n          "amount": 6000.00\n        }\n      ],\n      "subtotal": 6000.00,\n      "tax": 480.00,\n      "total": 6480.00\n    }\n  ]\n}', 512, 'QuickBooks', 'Salesforce');

-- =============================================================================
-- SECTION 5: VERIFICATION QUERIES
-- Run these to confirm data was generated correctly
-- =============================================================================

-- Summary of generated data
SELECT
    DATE(started_at) AS execution_date,
    COUNT(*) AS total_executions,
    SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) AS successful,
    SUM(CASE WHEN status = 'failed' THEN 1 ELSE 0 END) AS failed,
    SUM(CASE WHEN status = 'timeout' THEN 1 ELSE 0 END) AS timeout,
    SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled,
    SUM(CASE WHEN status = 'running' THEN 1 ELSE 0 END) AS running
FROM transaction_executions
WHERE execution_id LIKE 'TEST-%'
GROUP BY DATE(started_at)
ORDER BY execution_date DESC;

-- Total count verification
SELECT
    COUNT(*) AS total_test_executions,
    COUNT(DISTINCT flow_name) AS unique_flows,
    MIN(started_at) AS earliest_execution,
    MAX(started_at) AS latest_execution
FROM transaction_executions
WHERE execution_id LIKE 'TEST-%';

-- Status distribution
SELECT
    status,
    COUNT(*) AS count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM transaction_executions WHERE execution_id LIKE 'TEST-%'), 2) AS percentage
FROM transaction_executions
WHERE execution_id LIKE 'TEST-%'
GROUP BY status
ORDER BY count DESC;

-- =============================================================================
-- TEST DATA GENERATION COMPLETE
-- =============================================================================

SELECT 'Test data generation completed successfully!' AS status,
       (SELECT COUNT(*) FROM transaction_executions WHERE execution_id LIKE 'TEST-%') AS total_executions,
       (SELECT COUNT(*) FROM transaction_payloads WHERE execution_id IN (SELECT id FROM transaction_executions WHERE execution_id LIKE 'TEST-%')) AS total_payloads;
