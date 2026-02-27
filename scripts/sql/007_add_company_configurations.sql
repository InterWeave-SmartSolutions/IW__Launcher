-- =============================================================================
-- Migration 007: Add company_configurations table
-- Stores the XML configuration produced by the Company Configuration wizard
-- (CompanyConfiguration → CompanyConfigurationDetail → CompanyCredentials flow)
-- =============================================================================

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

-- Add update trigger (same pattern as other tables)
DROP TRIGGER IF EXISTS update_company_configurations_timestamp ON company_configurations;
CREATE TRIGGER update_company_configurations_timestamp
    BEFORE UPDATE ON company_configurations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Enable RLS (same as other tables)
ALTER TABLE company_configurations ENABLE ROW LEVEL SECURITY;
