-- IW_IDE Database Schema
-- SQLite database for storing Java pages, webforms, and project data

-- Enable foreign keys
PRAGMA foreign_keys = ON;

-- Projects table - stores project metadata
CREATE TABLE IF NOT EXISTS projects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    config_path TEXT,
    status TEXT DEFAULT 'active' CHECK(status IN ('active', 'archived', 'draft'))
);

-- Pages table - stores Java page definitions
CREATE TABLE IF NOT EXISTS pages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    title TEXT,
    page_type TEXT DEFAULT 'standard' CHECK(page_type IN ('standard', 'form', 'report', 'dashboard')),
    content TEXT,
    java_class TEXT,
    xslt_template TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    UNIQUE(project_id, name)
);

-- Forms table - stores webform definitions
CREATE TABLE IF NOT EXISTS forms (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    page_id INTEGER,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    form_type TEXT DEFAULT 'input' CHECK(form_type IN ('input', 'search', 'edit', 'wizard')),
    fields_json TEXT,  -- JSON structure of form fields
    validation_rules TEXT,  -- JSON validation rules
    submit_action TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Form fields table - detailed field definitions
CREATE TABLE IF NOT EXISTS form_fields (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    form_id INTEGER NOT NULL,
    field_name TEXT NOT NULL,
    field_type TEXT NOT NULL CHECK(field_type IN ('text', 'number', 'date', 'datetime', 'select', 'checkbox', 'radio', 'textarea', 'file', 'hidden')),
    label TEXT,
    placeholder TEXT,
    default_value TEXT,
    required BOOLEAN DEFAULT 0,
    validation_pattern TEXT,
    options_json TEXT,  -- For select/radio/checkbox options
    display_order INTEGER DEFAULT 0,
    FOREIGN KEY (form_id) REFERENCES forms(id) ON DELETE CASCADE
);

-- Transformations table - XSLT transformation records
CREATE TABLE IF NOT EXISTS transformations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    source_system TEXT,
    target_system TEXT,
    xslt_content TEXT,
    schedule_interval INTEGER,  -- in seconds
    last_run DATETIME,
    status TEXT DEFAULT 'enabled' CHECK(status IN ('enabled', 'disabled', 'error')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Data mappings table - field-level mappings
CREATE TABLE IF NOT EXISTS data_mappings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    transformation_id INTEGER NOT NULL,
    source_field TEXT NOT NULL,
    target_field TEXT NOT NULL,
    mapping_type TEXT DEFAULT 'direct' CHECK(mapping_type IN ('direct', 'transform', 'lookup', 'constant')),
    transform_expression TEXT,
    FOREIGN KEY (transformation_id) REFERENCES transformations(id) ON DELETE CASCADE
);

-- Execution log table - track transformation runs
CREATE TABLE IF NOT EXISTS execution_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    transformation_id INTEGER,
    project_id INTEGER,
    execution_type TEXT,
    started_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
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
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Insert default settings
INSERT OR IGNORE INTO settings (key, value, description) VALUES
    ('db_version', '1.0.0', 'Database schema version'),
    ('default_project_path', './data/projects', 'Default path for project files'),
    ('log_retention_days', '30', 'Days to retain execution logs'),
    ('auto_backup', 'true', 'Enable automatic database backups');

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_pages_project ON pages(project_id);
CREATE INDEX IF NOT EXISTS idx_forms_project ON forms(project_id);
CREATE INDEX IF NOT EXISTS idx_forms_page ON forms(page_id);
CREATE INDEX IF NOT EXISTS idx_transformations_project ON transformations(project_id);
CREATE INDEX IF NOT EXISTS idx_execution_log_transformation ON execution_log(transformation_id);
CREATE INDEX IF NOT EXISTS idx_execution_log_started ON execution_log(started_at);

-- Trigger to update updated_at timestamps
CREATE TRIGGER IF NOT EXISTS update_projects_timestamp 
AFTER UPDATE ON projects
BEGIN
    UPDATE projects SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
END;

CREATE TRIGGER IF NOT EXISTS update_pages_timestamp 
AFTER UPDATE ON pages
BEGIN
    UPDATE pages SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
END;

CREATE TRIGGER IF NOT EXISTS update_forms_timestamp 
AFTER UPDATE ON forms
BEGIN
    UPDATE forms SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
END;

CREATE TRIGGER IF NOT EXISTS update_transformations_timestamp 
AFTER UPDATE ON transformations
BEGIN
    UPDATE transformations SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
END;
