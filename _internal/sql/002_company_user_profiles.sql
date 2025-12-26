-- =============================================================================
-- IW_IDE Company and User Profile Tables
-- Migration: 002_company_user_profiles.sql
-- Date: 2024-12-04
-- Description: Creates tables for local storage of company and user profiles
--              (replaces dependency on InterWeave central server)
-- =============================================================================

-- Solution Types Reference Table
CREATE TABLE IF NOT EXISTS solution_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    crm_type VARCHAR(50),
    target_system VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Companies Table
CREATE TABLE IF NOT EXISTS companies (
    id SERIAL PRIMARY KEY,
    organization_name VARCHAR(255) UNIQUE NOT NULL,
    solution_type VARCHAR(20) NOT NULL,
    auth_token VARCHAR(255),
    portal_brand VARCHAR(100),
    portal_solutions TEXT,
    cloned_from_company_id INTEGER REFERENCES companies(id),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    is_admin BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    last_login TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(company_id, email)
);

-- User Sessions Table (for web portal login tracking)
CREATE TABLE IF NOT EXISTS user_sessions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    session_token VARCHAR(255) UNIQUE NOT NULL,
    ip_address VARCHAR(45),
    user_agent TEXT,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Company Configuration Table (stores key-value pairs for company settings)
CREATE TABLE IF NOT EXISTS company_configurations (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    config_key VARCHAR(100) NOT NULL,
    config_value TEXT,
    config_type VARCHAR(20) DEFAULT 'string',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(company_id, config_key)
);

-- Transaction Threads Table (for integration flow tracking)
CREATE TABLE IF NOT EXISTS transaction_threads (
    id SERIAL PRIMARY KEY,
    company_id INTEGER NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    thread_name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(255),
    status VARCHAR(50) DEFAULT 'active',
    config_data TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(company_id, thread_name)
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_company_id ON users(company_id);
CREATE INDEX IF NOT EXISTS idx_user_sessions_token ON user_sessions(session_token);
CREATE INDEX IF NOT EXISTS idx_user_sessions_expires ON user_sessions(expires_at);
CREATE INDEX IF NOT EXISTS idx_company_configurations_company ON company_configurations(company_id);
CREATE INDEX IF NOT EXISTS idx_transaction_threads_company ON transaction_threads(company_id);

-- Insert default solution types
INSERT INTO solution_types (code, name, crm_type, target_system, description) VALUES
    ('SF2QB', 'SalesForce to QuickBooks Enterprise', 'Salesforce', 'QuickBooks', 'Enterprise level SF to QB integration'),
    ('SF2QB1', 'SalesForce to QuickBooks Small Business', 'Salesforce', 'QuickBooks', 'Small business SF to QB integration'),
    ('SF2QB2', 'SalesForce to QuickBooks Premier', 'Salesforce', 'QuickBooks', 'Premier SF to QB integration'),
    ('SF2QB3', 'SalesForce to QuickBooks Professional', 'Salesforce', 'QuickBooks', 'Professional SF to QB integration'),
    ('SF2NS', 'SalesForce to NetSuite Enterprise', 'Salesforce', 'NetSuite', 'Enterprise level SF to NS integration'),
    ('SF2NS1', 'SalesForce to NetSuite Small Business', 'Salesforce', 'NetSuite', 'Small business SF to NS integration'),
    ('SF2NS2', 'SalesForce to NetSuite Premier', 'Salesforce', 'NetSuite', 'Premier SF to NS integration'),
    ('SF2NS3', 'SalesForce to NetSuite Professional', 'Salesforce', 'NetSuite', 'Professional SF to NS integration'),
    ('SF2CMS', 'SalesForce to CMS', 'Salesforce', 'CMS', 'SF to CMS integration'),
    ('SF2OMC', 'SalesForce to Ecommerce/OMS (Full Generic)', 'Salesforce', 'OMS', 'Full generic ecommerce integration'),
    ('SF2OMS', 'SalesForce to Ecommerce/OMS (Generic)', 'Salesforce', 'OMS', 'Generic ecommerce integration'),
    ('SF2OMSQB', 'SalesForce to Nexternal and QB', 'Salesforce', 'OMS+QuickBooks', 'SF to Nexternal and QuickBooks'),
    ('SF2OMSDB', 'SalesForce to Nexternal and DB', 'Salesforce', 'OMS+Database', 'SF to Nexternal and Database'),
    ('SF2DBG', 'SalesForce to DB (Generic)', 'Salesforce', 'Database', 'Generic database integration'),
    ('SF2AUTH', 'SalesForce to Authorize.net', 'Salesforce', 'Authorize.net', 'Payment gateway integration'),
    ('SF2MAS200', 'Salesforce to MAS', 'Salesforce', 'MAS', 'SF to MAS integration'),
    ('SF2PGG', 'SalesForce to Payment Gateway (Generic)', 'Salesforce', 'PaymentGateway', 'Generic payment gateway'),
    ('OMS2QB', 'Nexternal to QuickBooks', 'OMS', 'QuickBooks', 'Nexternal to QuickBooks integration'),
    ('OMS2ACC', 'Nexternal to Accpac', 'OMS', 'Accpac', 'Nexternal to Accpac integration'),
    ('ORA2QB', 'Oracle Fusion to QuickBooks', 'OracleFusion', 'QuickBooks', 'Oracle Fusion to QB integration'),
    ('MSDCRM2QB', 'Microsoft Dynamics CRM to QuickBooks', 'MSDCRM', 'QuickBooks', 'MS Dynamics CRM to QB'),
    ('PPOL2QB', 'PPOL to QuickBooks', 'PayPal', 'QuickBooks', 'PayPal Online to QuickBooks'),
    ('AR2QB', 'Aria to QuickBooks', 'Aria', 'QuickBooks', 'Aria billing to QuickBooks'),
    ('AR2NS', 'Aria to NetSuite', 'Aria', 'NetSuite', 'Aria billing to NetSuite'),
    ('SUG2QB', 'Sugar CRM to QuickBooks Enterprise', 'SugarCRM', 'QuickBooks', 'Enterprise Sugar to QB'),
    ('SUG2QB1', 'Sugar CRM to QuickBooks Small Business', 'SugarCRM', 'QuickBooks', 'Small business Sugar to QB'),
    ('SUG2QB2', 'Sugar CRM to QuickBooks Premier', 'SugarCRM', 'QuickBooks', 'Premier Sugar to QB'),
    ('SUG2QB3', 'Sugar CRM to QuickBooks Professional', 'SugarCRM', 'QuickBooks', 'Professional Sugar to QB'),
    ('SUG2DBG', 'Sugar CRM to DB (Generic)', 'SugarCRM', 'Database', 'Generic Sugar to DB'),
    ('CRM2QB', 'CRM to QuickBooks Enterprise', 'CRM', 'QuickBooks', 'Generic CRM to QB Enterprise'),
    ('CRM2QB1', 'CRM to QuickBooks Small Business', 'CRM', 'QuickBooks', 'Generic CRM to QB Small Business'),
    ('CRM2QB2', 'CRM to QuickBooks Premier', 'CRM', 'QuickBooks', 'Generic CRM to QB Premier'),
    ('CRM2QB3', 'CRM to QuickBooks Professional', 'CRM', 'QuickBooks', 'Generic CRM to QB Professional'),
    ('CRM2NS', 'CRM to NetSuite (Generic)', 'CRM', 'NetSuite', 'Generic CRM to NetSuite'),
    ('CRM2OMC', 'CRM to Ecommerce/OMS (Generic)', 'CRM', 'OMS', 'Generic CRM to ecommerce'),
    ('CRM2PT', 'CRM to Sage Enterprise', 'CRM', 'Sage', 'CRM to Sage Enterprise'),
    ('CRM2PT1', 'CRM to Sage Small Business', 'CRM', 'Sage', 'CRM to Sage Small Business'),
    ('CRM2PT2', 'CRM to Sage Premier', 'CRM', 'Sage', 'CRM to Sage Premier'),
    ('CRM2PT3', 'CRM to Sage Professional', 'CRM', 'Sage', 'CRM to Sage Professional'),
    ('CRM2GP', 'CRM to MS GP Enterprise', 'CRM', 'MSGP', 'CRM to MS Great Plains Enterprise'),
    ('CRM2GP1', 'CRM to MS GP Small Business', 'CRM', 'MSGP', 'CRM to MS Great Plains Small Business'),
    ('CRM2GP2', 'CRM to MS GP Premier', 'CRM', 'MSGP', 'CRM to MS Great Plains Premier'),
    ('CRM2GP3', 'CRM to MS GP Professional', 'CRM', 'MSGP', 'CRM to MS Great Plains Professional'),
    ('CRM2EGG', 'CRM to Email Gateway (Generic)', 'CRM', 'EmailGateway', 'Generic email gateway integration'),
    ('CRM2PGG', 'CRM to Payment Gateway (Generic)', 'CRM', 'PaymentGateway', 'Generic payment gateway'),
    ('CRM2DBG', 'CRM to DB (Generic)', 'CRM', 'Database', 'Generic database integration'),
    ('DB2QBG', 'DB to QB (Generic)', 'Database', 'QuickBooks', 'Generic DB to QuickBooks'),
    ('DB2FSG', 'DB to Financial System (Generic)', 'Database', 'FinancialSystem', 'Generic DB to financial system')
ON CONFLICT (code) DO NOTHING;

-- Update settings table with new version
INSERT INTO settings (key, value, description)
VALUES ('schema_version', '2.0', 'Database schema version with company/user profiles')
ON CONFLICT (key) DO UPDATE SET value = '2.0', updated_at = CURRENT_TIMESTAMP;

-- Create a function to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for updated_at columns
DROP TRIGGER IF EXISTS update_companies_updated_at ON companies;
CREATE TRIGGER update_companies_updated_at
    BEFORE UPDATE ON companies
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_company_configurations_updated_at ON company_configurations;
CREATE TRIGGER update_company_configurations_updated_at
    BEFORE UPDATE ON company_configurations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_transaction_threads_updated_at ON transaction_threads;
CREATE TRIGGER update_transaction_threads_updated_at
    BEFORE UPDATE ON transaction_threads
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- =============================================================================
-- End of Migration
-- =============================================================================
