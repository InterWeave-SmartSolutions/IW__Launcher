-- =============================================================================
-- IW_IDE MySQL Database Schema
-- Matches InterWeave's hostedprofiles database structure for authentication
-- Compatible with both Oracle Cloud MySQL and InterWeave Server
-- =============================================================================

-- Use InnoDB for foreign key support
SET default_storage_engine = InnoDB;

-- -----------------------------------------------------------------------------
-- AUTHENTICATION TABLES (Core InterWeave structure)
-- These tables mirror the InterWeave hosted database for user/company auth
-- -----------------------------------------------------------------------------

-- Companies table - stores company/organization profiles
CREATE TABLE IF NOT EXISTS companies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    company_code VARCHAR(50) UNIQUE,
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    zip VARCHAR(20),
    country VARCHAR(100) DEFAULT 'USA',
    phone VARCHAR(50),
    fax VARCHAR(50),
    email VARCHAR(255),
    website VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    license_key VARCHAR(255),
    license_expiry DATE,
    solution_type VARCHAR(50) DEFAULT 'QB',
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_company_code (company_code),
    INDEX idx_company_name (company_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Users table - stores individual user accounts
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(50),
    role VARCHAR(50) DEFAULT 'user',
    is_active TINYINT(1) DEFAULT 1,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    INDEX idx_user_email (email),
    INDEX idx_user_company (company_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- User profiles - extended user information (matches InterWeave structure)
CREATE TABLE IF NOT EXISTS profiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    profile_name VARCHAR(255),
    sf_username VARCHAR(255),
    sf_password VARCHAR(255),
    sf_token VARCHAR(255),
    sf_url VARCHAR(500),
    qb_company_file VARCHAR(500),
    qb_username VARCHAR(255),
    qb_password VARCHAR(255),
    crm_url VARCHAR(500),
    crm_username VARCHAR(255),
    crm_password VARCHAR(255),
    custom_settings TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Company credentials - stores third-party API credentials per company
CREATE TABLE IF NOT EXISTS company_credentials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    credential_type VARCHAR(50) NOT NULL,
    credential_name VARCHAR(100),
    username VARCHAR(255),
    password VARCHAR(255),
    api_key VARCHAR(500),
    api_secret VARCHAR(500),
    endpoint_url VARCHAR(500),
    extra_config TEXT,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    UNIQUE KEY uk_company_credential (company_id, credential_type),
    INDEX idx_credential_type (credential_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Solutions table - available integration solutions
CREATE TABLE IF NOT EXISTS solutions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    solution_code VARCHAR(20) NOT NULL UNIQUE,
    solution_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Company solutions - which solutions each company has access to
CREATE TABLE IF NOT EXISTS company_solutions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    solution_id INT NOT NULL,
    is_enabled TINYINT(1) DEFAULT 1,
    config_data TEXT,
    activated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON DELETE CASCADE,
    UNIQUE KEY uk_company_solution (company_id, solution_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------------------------
-- APPLICATION DATA TABLES
-- These tables store project and transformation data
-- -----------------------------------------------------------------------------

-- Projects table - stores project metadata
CREATE TABLE IF NOT EXISTS projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    config_path VARCHAR(500),
    status ENUM('active', 'archived', 'draft') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE SET NULL,
    INDEX idx_project_company (company_id),
    INDEX idx_project_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Pages table - stores Java page definitions
CREATE TABLE IF NOT EXISTS pages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    page_type ENUM('standard', 'form', 'report', 'dashboard') DEFAULT 'standard',
    content LONGTEXT,
    java_class VARCHAR(500),
    xslt_template TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    UNIQUE KEY uk_project_page (project_id, name),
    INDEX idx_page_project (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Forms table - stores webform definitions
CREATE TABLE IF NOT EXISTS forms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    page_id INT,
    project_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    form_type ENUM('input', 'search', 'edit', 'wizard') DEFAULT 'input',
    fields_json JSON,
    validation_rules JSON,
    submit_action VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    INDEX idx_form_project (project_id),
    INDEX idx_form_page (page_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Form fields table - detailed field definitions
CREATE TABLE IF NOT EXISTS form_fields (
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_id INT NOT NULL,
    field_name VARCHAR(100) NOT NULL,
    field_type ENUM('text', 'number', 'date', 'datetime', 'select', 'checkbox', 'radio', 'textarea', 'file', 'hidden') NOT NULL,
    label VARCHAR(255),
    placeholder VARCHAR(255),
    default_value TEXT,
    required TINYINT(1) DEFAULT 0,
    validation_pattern VARCHAR(500),
    options_json JSON,
    display_order INT DEFAULT 0,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE,
    INDEX idx_field_form (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Transformations table - XSLT transformation records
CREATE TABLE IF NOT EXISTS transformations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    source_system VARCHAR(100),
    target_system VARCHAR(100),
    xslt_content LONGTEXT,
    schedule_interval INT,
    last_run TIMESTAMP NULL,
    status ENUM('enabled', 'disabled', 'error') DEFAULT 'enabled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    INDEX idx_transformation_project (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data mappings table - field-level mappings
CREATE TABLE IF NOT EXISTS data_mappings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transformation_id INT NOT NULL,
    source_field VARCHAR(255) NOT NULL,
    target_field VARCHAR(255) NOT NULL,
    mapping_type ENUM('direct', 'transform', 'lookup', 'constant') DEFAULT 'direct',
    transform_expression TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE CASCADE,
    INDEX idx_mapping_transformation (transformation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Execution log table - track transformation runs
CREATE TABLE IF NOT EXISTS execution_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transformation_id INT,
    project_id INT,
    execution_type VARCHAR(50),
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    status ENUM('running', 'success', 'failed', 'cancelled'),
    records_processed INT DEFAULT 0,
    records_failed INT DEFAULT 0,
    error_message TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL,
    INDEX idx_log_transformation (transformation_id),
    INDEX idx_log_started (started_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Settings table - application configuration
CREATE TABLE IF NOT EXISTS settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value TEXT,
    description VARCHAR(500),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------------------------------
-- DEFAULT DATA
-- -----------------------------------------------------------------------------

-- Insert default solutions (matching InterWeave standard)
INSERT IGNORE INTO solutions (solution_code, solution_name, description) VALUES
    ('QB', 'QuickBooks Integration', 'QuickBooks Desktop and Online integration'),
    ('SF', 'Salesforce Integration', 'Salesforce CRM integration'),
    ('CRM', 'Creatio CRM Integration', 'Creatio (bpm''online) CRM integration'),
    ('AUTH', 'Authorize.Net Integration', 'Payment processing with Authorize.Net'),
    ('STRIPE', 'Stripe Integration', 'Payment processing with Stripe'),
    ('GENERIC', 'Generic HTTP Integration', 'Custom REST/SOAP integrations');

-- Insert default settings
INSERT IGNORE INTO settings (setting_key, setting_value, description) VALUES
    ('db_version', '2.0.0', 'Database schema version'),
    ('default_project_path', './data/projects', 'Default path for project files'),
    ('log_retention_days', '30', 'Days to retain execution logs'),
    ('auto_backup', 'true', 'Enable automatic database backups'),
    ('session_timeout', '30', 'Session timeout in minutes');

-- Insert default admin company and user
-- Password is SHA-256 hash of '%iwps%'
INSERT IGNORE INTO companies (id, company_name, company_code, password, solution_type, is_active) VALUES
    (1, 'IW Admin', 'IWADMIN', SHA2('%iwps%', 256), 'QB', 1);

INSERT IGNORE INTO users (id, company_id, email, password, first_name, last_name, role) VALUES
    (1, 1, '__iw_admin__', SHA2('%iwps%', 256), 'IW', 'Admin', 'admin');

-- Enable all solutions for admin company
INSERT IGNORE INTO company_solutions (company_id, solution_id, is_enabled)
SELECT 1, id, 1 FROM solutions;

-- -----------------------------------------------------------------------------
-- DEMO/TEST DATA
-- Demo user accounts for testing authentication flow
-- -----------------------------------------------------------------------------

-- Insert demo company
-- Password: 'demoCompany123' hashed with SHA-256
INSERT IGNORE INTO companies (id, company_name, company_code, address1, city, state, zip, country,
                              phone, email, password, license_key, solution_type, is_active) VALUES
    (2, 'Demo Company Inc.', 'DEMO001', '123 Demo Street', 'San Francisco', 'CA', '94102', 'USA',
     '555-0100', 'contact@democompany.com', SHA2('demoCompany123', 256), 'DEMO-LICENSE-2026', 'QB', 1);

-- Insert demo user account
-- Email: demo@sample.com
-- Password: demo123 (hashed with SHA-256 to match LocalLoginServlet verification)
INSERT IGNORE INTO users (id, company_id, email, password, first_name, last_name, phone, role, is_active) VALUES
    (2, 2, 'demo@sample.com', SHA2('demo123', 256), 'Demo', 'User', '555-0101', 'user', 1);

-- Insert demo admin user account for testing admin features
-- Email: admin@sample.com
-- Password: admin123 (hashed with SHA-256)
INSERT IGNORE INTO users (id, company_id, email, password, first_name, last_name, phone, role, is_active) VALUES
    (3, 2, 'admin@sample.com', SHA2('admin123', 256), 'Admin', 'User', '555-0102', 'admin', 1);

-- Enable QuickBooks and Salesforce solutions for demo company
INSERT IGNORE INTO company_solutions (company_id, solution_id, is_enabled)
SELECT 2, id, 1 FROM solutions WHERE solution_code IN ('QB', 'SF');

-- -----------------------------------------------------------------------------
-- VIEWS FOR COMPATIBILITY
-- -----------------------------------------------------------------------------

-- View for user authentication (matches expected LoginServlet query pattern)
CREATE OR REPLACE VIEW v_user_auth AS
SELECT
    u.id AS user_id,
    u.email,
    u.password AS user_password,
    u.first_name,
    u.last_name,
    u.role,
    u.is_active AS user_active,
    c.id AS company_id,
    c.company_name,
    c.company_code,
    c.password AS company_password,
    c.solution_type,
    c.is_active AS company_active
FROM users u
JOIN companies c ON u.company_id = c.id;

-- View for profiles (matches InterWeave profile query)
CREATE OR REPLACE VIEW v_user_profiles AS
SELECT
    u.email,
    u.first_name,
    u.last_name,
    c.company_name,
    c.company_code,
    p.*
FROM users u
JOIN companies c ON u.company_id = c.id
LEFT JOIN profiles p ON u.id = p.user_id;
