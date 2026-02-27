-- =============================================================================
-- IW_IDE PostgreSQL Database Schema (Supabase-compatible)
-- Full mirror of mysql_schema.sql for Postgres / Supabase
-- Compatible with LocalLoginServlet and all IW_IDE servlets
-- =============================================================================

-- Enable pgcrypto for SHA-256 password hashing
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- -----------------------------------------------------------------------------
-- AUTHENTICATION TABLES (Core InterWeave structure)
-- These tables mirror the InterWeave hosted database for user/company auth
-- -----------------------------------------------------------------------------

-- Companies table - stores company/organization profiles
CREATE TABLE IF NOT EXISTS companies (
    id SERIAL PRIMARY KEY,
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
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_company_code ON companies(company_code);
CREATE INDEX IF NOT EXISTS idx_company_name ON companies(company_name);

-- Users table - stores individual user accounts
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    title VARCHAR(255),
    phone VARCHAR(50),
    role VARCHAR(50) DEFAULT 'user',
    is_active BOOLEAN DEFAULT TRUE,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_user_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_user_company ON users(company_id);

-- User profiles - extended user information (matches InterWeave structure)
CREATE TABLE IF NOT EXISTS profiles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Company credentials - stores third-party API credentials per company
CREATE TABLE IF NOT EXISTS company_credentials (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL,
    credential_type VARCHAR(50) NOT NULL,
    credential_name VARCHAR(100),
    username VARCHAR(255),
    password VARCHAR(255),
    api_key VARCHAR(500),
    api_secret VARCHAR(500),
    endpoint_url VARCHAR(500),
    extra_config TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    UNIQUE (company_id, credential_type)
);

CREATE INDEX IF NOT EXISTS idx_credential_type ON company_credentials(credential_type);

-- Solutions table - available integration solutions
CREATE TABLE IF NOT EXISTS solutions (
    id SERIAL PRIMARY KEY,
    solution_code VARCHAR(20) NOT NULL UNIQUE,
    solution_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Company solutions - which solutions each company has access to
CREATE TABLE IF NOT EXISTS company_solutions (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL,
    solution_id INTEGER NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE,
    config_data TEXT,
    activated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON DELETE CASCADE,
    UNIQUE (company_id, solution_id)
);

-- -----------------------------------------------------------------------------
-- APPLICATION DATA TABLES
-- These tables store project and transformation data
-- -----------------------------------------------------------------------------

-- Projects table - stores project metadata
CREATE TABLE IF NOT EXISTS projects (
    id SERIAL PRIMARY KEY,
    company_id INTEGER,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    config_path VARCHAR(500),
    status VARCHAR(20) DEFAULT 'active' CHECK(status IN ('active', 'archived', 'draft')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_project_company ON projects(company_id);
CREATE INDEX IF NOT EXISTS idx_project_name ON projects(name);

-- Pages table - stores Java page definitions
CREATE TABLE IF NOT EXISTS pages (
    id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    page_type VARCHAR(20) DEFAULT 'standard' CHECK(page_type IN ('standard', 'form', 'report', 'dashboard')),
    content TEXT,
    java_class VARCHAR(500),
    xslt_template TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    UNIQUE (project_id, name)
);

CREATE INDEX IF NOT EXISTS idx_page_project ON pages(project_id);

-- Forms table - stores webform definitions
CREATE TABLE IF NOT EXISTS forms (
    id SERIAL PRIMARY KEY,
    page_id INTEGER,
    project_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    form_type VARCHAR(20) DEFAULT 'input' CHECK(form_type IN ('input', 'search', 'edit', 'wizard')),
    fields_json JSONB,
    validation_rules JSONB,
    submit_action VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_form_project ON forms(project_id);
CREATE INDEX IF NOT EXISTS idx_form_page ON forms(page_id);

-- Form fields table - detailed field definitions
CREATE TABLE IF NOT EXISTS form_fields (
    id SERIAL PRIMARY KEY,
    form_id INTEGER NOT NULL,
    field_name VARCHAR(100) NOT NULL,
    field_type VARCHAR(20) NOT NULL CHECK(field_type IN ('text', 'number', 'date', 'datetime', 'select', 'checkbox', 'radio', 'textarea', 'file', 'hidden')),
    label VARCHAR(255),
    placeholder VARCHAR(255),
    default_value TEXT,
    required BOOLEAN DEFAULT FALSE,
    validation_pattern VARCHAR(500),
    options_json JSONB,
    display_order INTEGER DEFAULT 0,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_field_form ON form_fields(form_id);

-- Transformations table - XSLT transformation records
CREATE TABLE IF NOT EXISTS transformations (
    id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    source_system VARCHAR(100),
    target_system VARCHAR(100),
    xslt_content TEXT,
    schedule_interval INTEGER,
    last_run TIMESTAMP,
    status VARCHAR(20) DEFAULT 'enabled' CHECK(status IN ('enabled', 'disabled', 'error')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_transformation_project ON transformations(project_id);

-- Data mappings table - field-level mappings
CREATE TABLE IF NOT EXISTS data_mappings (
    id SERIAL PRIMARY KEY,
    transformation_id INTEGER NOT NULL,
    source_field VARCHAR(255) NOT NULL,
    target_field VARCHAR(255) NOT NULL,
    mapping_type VARCHAR(20) DEFAULT 'direct' CHECK(mapping_type IN ('direct', 'transform', 'lookup', 'constant')),
    transform_expression TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_mapping_transformation ON data_mappings(transformation_id);

-- Execution log table - track transformation runs
CREATE TABLE IF NOT EXISTS execution_log (
    id SERIAL PRIMARY KEY,
    transformation_id INTEGER,
    project_id INTEGER,
    execution_type VARCHAR(50),
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    status VARCHAR(20) CHECK(status IN ('running', 'success', 'failed', 'cancelled')),
    records_processed INTEGER DEFAULT 0,
    records_failed INTEGER DEFAULT 0,
    error_message TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_log_transformation ON execution_log(transformation_id);
CREATE INDEX IF NOT EXISTS idx_log_started ON execution_log(started_at);

-- Company configurations - stores XML config from the Company Configuration wizard
CREATE TABLE IF NOT EXISTS company_configurations (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL,
    profile_name VARCHAR(255) NOT NULL,
    solution_type VARCHAR(50),
    configuration_xml TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE,
    UNIQUE (company_id, profile_name)
);

CREATE INDEX IF NOT EXISTS idx_company_config_company ON company_configurations(company_id);

-- Settings table - application configuration
-- NOTE: Uses setting_key/setting_value (not key/value) because key is reserved in Postgres
CREATE TABLE IF NOT EXISTS settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value TEXT,
    description VARCHAR(500),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------------------------------
-- AUTO-UPDATE TIMESTAMPS (Postgres trigger replaces MySQL ON UPDATE)
-- -----------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply trigger to all tables with updated_at
DO $$
DECLARE
    tbl TEXT;
BEGIN
    FOR tbl IN SELECT unnest(ARRAY[
        'companies', 'users', 'profiles', 'company_credentials',
        'company_configurations', 'projects', 'pages', 'forms', 'transformations', 'settings'
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
-- DEFAULT DATA
-- -----------------------------------------------------------------------------

-- Insert default solutions (matching InterWeave standard)
INSERT INTO solutions (solution_code, solution_name, description) VALUES
    ('QB',      'QuickBooks Integration',    'QuickBooks Desktop and Online integration'),
    ('SF',      'Salesforce Integration',    'Salesforce CRM integration'),
    ('CRM',     'Creatio CRM Integration',   'Creatio (bpm''online) CRM integration'),
    ('AUTH',    'Authorize.Net Integration', 'Payment processing with Authorize.Net'),
    ('STRIPE',  'Stripe Integration',        'Payment processing with Stripe'),
    ('GENERIC', 'Generic HTTP Integration',  'Custom REST/SOAP integrations')
ON CONFLICT (solution_code) DO NOTHING;

-- Insert default settings
INSERT INTO settings (setting_key, setting_value, description) VALUES
    ('db_version',          '2.0.0',             'Database schema version'),
    ('default_project_path','./data/projects',    'Default path for project files'),
    ('log_retention_days',  '30',                 'Days to retain execution logs'),
    ('auto_backup',         'true',               'Enable automatic database backups'),
    ('session_timeout',     '30',                 'Session timeout in minutes')
ON CONFLICT (setting_key) DO NOTHING;

-- Insert default admin company and user
-- Password is SHA-256 hash of '%iwps%'  (matches MySQL SHA2('%iwps%', 256))
INSERT INTO companies (id, company_name, company_code, password, solution_type, is_active) VALUES
    (1, 'IW Admin', 'IWADMIN', encode(digest('%iwps%', 'sha256'), 'hex'), 'QB', TRUE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, company_id, email, password, first_name, last_name, role) VALUES
    (1, 1, '__iw_admin__', encode(digest('%iwps%', 'sha256'), 'hex'), 'IW', 'Admin', 'admin')
ON CONFLICT (id) DO NOTHING;

-- Enable all solutions for admin company
INSERT INTO company_solutions (company_id, solution_id, is_enabled)
SELECT 1, id, TRUE FROM solutions
ON CONFLICT (company_id, solution_id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- DEMO / TEST DATA
-- -----------------------------------------------------------------------------

-- Demo company  (password: demoCompany123)
INSERT INTO companies (id, company_name, company_code, address1, city, state, zip, country,
                       phone, email, password, license_key, solution_type, is_active) VALUES
    (2, 'Demo Company Inc.', 'DEMO001', '123 Demo Street', 'San Francisco', 'CA', '94102', 'USA',
     '555-0100', 'contact@democompany.com',
     encode(digest('demoCompany123', 'sha256'), 'hex'),
     'DEMO-LICENSE-2026', 'QB', TRUE)
ON CONFLICT (id) DO NOTHING;

-- demo@sample.com / demo123
INSERT INTO users (id, company_id, email, password, first_name, last_name, phone, role, is_active) VALUES
    (2, 2, 'demo@sample.com', encode(digest('demo123', 'sha256'), 'hex'),
     'Demo', 'User', '555-0101', 'user', TRUE)
ON CONFLICT (id) DO NOTHING;

-- admin@sample.com / admin123
INSERT INTO users (id, company_id, email, password, first_name, last_name, phone, role, is_active) VALUES
    (3, 2, 'admin@sample.com', encode(digest('admin123', 'sha256'), 'hex'),
     'Admin', 'User', '555-0102', 'admin', TRUE)
ON CONFLICT (id) DO NOTHING;

-- Enable QB + SF for demo company
INSERT INTO company_solutions (company_id, solution_id, is_enabled)
SELECT 2, id, TRUE FROM solutions WHERE solution_code IN ('QB', 'SF')
ON CONFLICT (company_id, solution_id) DO NOTHING;

-- Reset sequences past seed IDs
SELECT setval('companies_id_seq', GREATEST((SELECT MAX(id) FROM companies), 1));
SELECT setval('users_id_seq',     GREATEST((SELECT MAX(id) FROM users),     1));

-- -----------------------------------------------------------------------------
-- ROW LEVEL SECURITY (Supabase requirement)
-- Enables RLS with no policies → blocks PostgREST anon/authenticated access.
-- JDBC connections use the 'postgres' superuser role which bypasses RLS.
-- -----------------------------------------------------------------------------

ALTER TABLE companies           ENABLE ROW LEVEL SECURITY;
ALTER TABLE users               ENABLE ROW LEVEL SECURITY;
ALTER TABLE profiles            ENABLE ROW LEVEL SECURITY;
ALTER TABLE company_credentials ENABLE ROW LEVEL SECURITY;
ALTER TABLE solutions           ENABLE ROW LEVEL SECURITY;
ALTER TABLE company_solutions   ENABLE ROW LEVEL SECURITY;
ALTER TABLE projects            ENABLE ROW LEVEL SECURITY;
ALTER TABLE pages               ENABLE ROW LEVEL SECURITY;
ALTER TABLE forms               ENABLE ROW LEVEL SECURITY;
ALTER TABLE form_fields         ENABLE ROW LEVEL SECURITY;
ALTER TABLE transformations     ENABLE ROW LEVEL SECURITY;
ALTER TABLE data_mappings       ENABLE ROW LEVEL SECURITY;
ALTER TABLE execution_log           ENABLE ROW LEVEL SECURITY;
ALTER TABLE company_configurations  ENABLE ROW LEVEL SECURITY;
ALTER TABLE settings                ENABLE ROW LEVEL SECURITY;

-- -----------------------------------------------------------------------------
-- VIEWS FOR COMPATIBILITY
-- -----------------------------------------------------------------------------

-- View for user authentication (matches expected LoginServlet query pattern)
CREATE OR REPLACE VIEW v_user_auth AS
SELECT
    u.id        AS user_id,
    u.email,
    u.password  AS user_password,
    u.first_name,
    u.last_name,
    u.role,
    u.is_active AS user_active,
    c.id        AS company_id,
    c.company_name,
    c.company_code,
    c.password  AS company_password,
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
