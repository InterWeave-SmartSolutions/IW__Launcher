-- Notifications Inbox schema for IW Portal
-- Stores per-user notifications (system, alert, flow-status, security)

CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    company_id INTEGER,
    notification_type VARCHAR(20) NOT NULL DEFAULT 'system'
        CHECK (notification_type IN ('system', 'alert', 'flow-status', 'security')),
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    link VARCHAR(500),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_notif_user ON notifications(user_id);
CREATE INDEX IF NOT EXISTS idx_notif_user_read ON notifications(user_id, is_read);
CREATE INDEX IF NOT EXISTS idx_notif_created ON notifications(created_at);

ALTER TABLE notifications ENABLE ROW LEVEL SECURITY;
