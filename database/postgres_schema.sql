-- IW_IDE Database Schema for PostgreSQL
-- Remote database for storing Java pages, webforms, and project data

-- Projects table - stores project metadata
CREATE TABLE IF NOT EXISTS projects (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    config_path TEXT,
    status TEXT DEFAULT 'active' CHECK(status IN ('active', 'archived', 'draft'))
);

-- Pages table - stores Java page definitions
CREATE TABLE IF NOT EXISTS pages (
    id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    title TEXT,
    page_type TEXT DEFAULT 'standard' CHECK(page_type IN ('standard', 'form', 'report', 'dashboard')),
    content TEXT,
    java_class TEXT,
    xslt_template TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    UNIQUE(project_id, name)
);

-- Forms table - stores webform definitions
CREATE TABLE IF NOT EXISTS forms (
    id SERIAL PRIMARY KEY,
    page_id INTEGER,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    form_type TEXT DEFAULT 'input' CHECK(form_type IN ('input', 'search', 'edit', 'wizard')),
    fields_json JSONB,  -- JSON structure of form fields
    validation_rules JSONB,  -- JSON validation rules
    submit_action TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Form fields table - detailed field definitions
CREATE TABLE IF NOT EXISTS form_fields (
    id SERIAL PRIMARY KEY,
    form_id INTEGER NOT NULL,
    field_name TEXT NOT NULL,
    field_type TEXT NOT NULL CHECK(field_type IN ('text', 'number', 'date', 'datetime', 'select', 'checkbox', 'radio', 'textarea', 'file', 'hidden')),
    label TEXT,
    placeholder TEXT,
    default_value TEXT,
    required BOOLEAN DEFAULT FALSE,
    validation_pattern TEXT,
    options_json JSONB,  -- For select/radio/checkbox options
    display_order INTEGER DEFAULT 0,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE
);

-- Transformations table - XSLT transformation records
CREATE TABLE IF NOT EXISTS transformations (
    id SERIAL PRIMARY KEY,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    source_system TEXT,
    target_system TEXT,
    xslt_content TEXT,
    schedule_interval INTEGER,  -- in seconds
    last_run TIMESTAMP,
    status TEXT DEFAULT 'enabled' CHECK(status IN ('enabled', 'disabled', 'error')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Data mappings table - field-level mappings
CREATE TABLE IF NOT EXISTS data_mappings (
    id SERIAL PRIMARY KEY,
    transformation_id INTEGER NOT NULL,
    source_field TEXT NOT NULL,
    target_field TEXT NOT NULL,
    mapping_type TEXT DEFAULT 'direct' CHECK(mapping_type IN ('direct', 'transform', 'lookup', 'constant')),
    transform_expression TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE CASCADE
);

-- Execution log table - track transformation runs
CREATE TABLE IF NOT EXISTS execution_log (
    id SERIAL PRIMARY KEY,
    transformation_id INTEGER,
    project_id INTEGER,
    execution_type TEXT,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    status TEXT CHECK(status IN ('running', 'success', 'failed', 'cancelled')),
    records_processed INTEGER DEFAULT 0,
    records_failed INTEGER DEFAULT 0,
    error_message TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL
);

-- Settings table - application configuration
CREATE TABLE IF NOT EXISTS settings (
    key TEXT PRIMARY KEY,
    value TEXT,
    description TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default settings
INSERT INTO settings (key, value, description) VALUES
    ('db_version', '1.0.0', 'Database schema version'),
    ('default_project_path', './data/projects', 'Default path for project files'),
    ('log_retention_days', '30', 'Days to retain execution logs'),
    ('auto_backup', 'true', 'Enable automatic database backups')
ON CONFLICT (key) DO NOTHING;

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_pages_project ON pages(project_id);
CREATE INDEX IF NOT EXISTS idx_forms_project ON forms(project_id);
CREATE INDEX IF NOT EXISTS idx_forms_page ON forms(page_id);
CREATE INDEX IF NOT EXISTS idx_transformations_project ON transformations(project_id);
CREATE INDEX IF NOT EXISTS idx_execution_log_transformation ON execution_log(transformation_id);
CREATE INDEX IF NOT EXISTS idx_execution_log_started ON execution_log(started_at);

-- Function to update updated_at timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers for auto-updating timestamps
DROP TRIGGER IF EXISTS update_projects_timestamp ON projects;
CREATE TRIGGER update_projects_timestamp
    BEFORE UPDATE ON projects
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_pages_timestamp ON pages;
CREATE TRIGGER update_pages_timestamp
    BEFORE UPDATE ON pages
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_forms_timestamp ON forms;
CREATE TRIGGER update_forms_timestamp
    BEFORE UPDATE ON forms
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_transformations_timestamp ON transformations;
CREATE TRIGGER update_transformations_timestamp
    BEFORE UPDATE ON transformations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
