<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%
    // Session authentication check
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("authenticated") == null ||
        !(Boolean)userSession.getAttribute("authenticated")) {
        response.sendRedirect("../IWLogin.jsp?error=Session expired. Please login again.");
        return;
    }

    // Get user context from session
    String userId = (String) userSession.getAttribute("userId");
    String userName = (String) userSession.getAttribute("userName");
    Integer companyId = (Integer) userSession.getAttribute("companyId");
    String companyName = (String) userSession.getAttribute("companyName");
    Boolean isAdmin = (Boolean) userSession.getAttribute("isAdmin");

    if (userId == null) {
        response.sendRedirect("../IWLogin.jsp?error=Invalid session. Please login again.");
        return;
    }

    // Default values if not set
    if (userName == null) userName = userId;
    if (isAdmin == null) isAdmin = false;
    if (companyName == null) companyName = "Unknown Company";

    // Brand parameters for navigation consistency
    String brand = request.getParameter("PortalBrand");
    if (brand == null) brand = "";
    String solutions = request.getParameter("PortalSolutions");
    if (solutions == null) solutions = "";

    // Prevent caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Alert Configuration - InterWeave IDE</title>

    <!-- Dashboard CSS -->
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/alert-config.css">

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="../images/favicon.ico">
</head>
<body>
    <!-- Header Banner -->
    <header class="dashboard-header">
        <div class="header-banner">
            <img src="../images<%= (brand.equals("") ? "" : ("/" + brand)) %>/IT Banner.png"
                 alt="InterWeave Banner"
                 class="header-banner-img"
                 onerror="this.style.display='none'">
        </div>
        <div class="header-toolbar">
            <div class="header-title">
                <h1>Alert Configuration</h1>
                <p class="header-subtitle">Manage alert rules and webhook endpoints</p>
            </div>
            <div class="header-user-info">
                <span class="user-name"><strong>User:</strong> <%= userName %></span>
                <% if (companyName != null && !companyName.equals("Unknown Company")) { %>
                <span class="company-name"><strong>Company:</strong> <%= companyName %></span>
                <% } %>
                <% if (isAdmin) { %>
                <span class="admin-badge">Admin</span>
                <% } %>
            </div>
            <div class="header-actions">
                <a href="Dashboard.jsp?PortalBrand=<%= brand %>&PortalSolutions=<%= solutions %>"
                   class="btn btn-secondary">
                    ← Back to Dashboard
                </a>
                <a href="../LogoutServlet?PortalBrand=<%= brand %>"
                   class="btn btn-secondary">
                    Logout
                </a>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="dashboard-main">
        <!-- Alert Rules Section -->
        <section class="config-section">
            <div class="section-header">
                <h2 class="section-title">Alert Rules</h2>
                <div class="section-actions">
                    <button id="refresh-alerts-btn" class="btn btn-secondary">
                        ↻ Refresh
                    </button>
                    <button id="new-alert-btn" class="btn btn-primary">
                        + New Alert Rule
                    </button>
                </div>
            </div>

            <div id="alert-rules-container">
                <div class="loading-state">
                    <span class="loading">Loading alert rules</span>
                </div>
            </div>
        </section>

        <!-- Webhook Endpoints Section -->
        <section class="config-section">
            <div class="section-header">
                <h2 class="section-title">Webhook Endpoints</h2>
                <div class="section-actions">
                    <button id="refresh-webhooks-btn" class="btn btn-secondary">
                        ↻ Refresh
                    </button>
                    <button id="new-webhook-btn" class="btn btn-primary">
                        + New Webhook
                    </button>
                </div>
            </div>

            <div id="webhooks-container">
                <div class="loading-state">
                    <span class="loading">Loading webhook endpoints</span>
                </div>
            </div>
        </section>

        <!-- Alert History Section -->
        <section class="config-section">
            <div class="section-header">
                <h2 class="section-title">Recent Alert History</h2>
                <div class="section-actions">
                    <button id="refresh-history-btn" class="btn btn-secondary">
                        ↻ Refresh
                    </button>
                </div>
            </div>

            <!-- Filter Controls -->
            <div class="filter-controls">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="history-status-filter">Status:</label>
                        <select id="history-status-filter" class="form-select">
                            <option value="">All Statuses</option>
                            <option value="sent">Sent</option>
                            <option value="failed">Failed</option>
                            <option value="pending">Pending</option>
                            <option value="retrying">Retrying</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="history-type-filter">Type:</label>
                        <select id="history-type-filter" class="form-select">
                            <option value="">All Types</option>
                            <option value="email">Email</option>
                            <option value="webhook">Webhook</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="history-limit">Show:</label>
                        <select id="history-limit" class="form-select">
                            <option value="50">Last 50</option>
                            <option value="100" selected>Last 100</option>
                            <option value="200">Last 200</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <button id="clear-history-filters-btn" class="btn btn-secondary">
                            Clear Filters
                        </button>
                    </div>
                </div>
            </div>

            <div id="alert-history-container">
                <div class="loading-state">
                    <span class="loading">Loading alert history</span>
                </div>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>&copy; 2026 InterWeave Integration Manager. All rights reserved.</p>
            <p>
                <a href="Dashboard.jsp?PortalBrand=<%= brand %>&PortalSolutions=<%= solutions %>">Dashboard</a> |
                <a href="http://www.interweave.biz" target="_blank">InterWeave Home</a> |
                <a href="mailto:support@interweave.biz">Support</a>
            </p>
        </div>
    </footer>

    <!-- Alert Rule Modal -->
    <div id="alert-rule-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="alert-modal-title">New Alert Rule</h3>
                <button class="modal-close" id="alert-modal-close">&times;</button>
            </div>
            <div class="modal-body">
                <form id="alert-rule-form">
                    <input type="hidden" id="alert-rule-id" name="id">

                    <div class="form-group">
                        <label for="alert-rule-name">Rule Name *</label>
                        <input type="text" id="alert-rule-name" name="rule_name" class="form-input" required
                               placeholder="e.g., Production Order Failures">
                    </div>

                    <div class="form-group">
                        <label for="alert-description">Description</label>
                        <textarea id="alert-description" name="description" class="form-input" rows="2"
                                  placeholder="Optional description of this alert rule"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="alert-flow-name">Flow Name</label>
                        <input type="text" id="alert-flow-name" name="flow_name" class="form-input"
                               placeholder="Leave blank to monitor all flows">
                    </div>

                    <div class="form-group">
                        <label>Alert Triggers</label>
                        <div class="checkbox-group">
                            <label class="checkbox-label">
                                <input type="checkbox" id="alert-on-failure" name="alert_on_failure" checked>
                                Alert on failure
                            </label>
                            <label class="checkbox-label">
                                <input type="checkbox" id="alert-on-timeout" name="alert_on_timeout">
                                Alert on timeout
                            </label>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="alert-threshold">Failure Threshold</label>
                            <input type="number" id="alert-threshold" name="failure_threshold" class="form-input"
                                   value="1" min="1" placeholder="Number of failures">
                        </div>
                        <div class="form-group">
                            <label for="alert-threshold-window">Window (minutes)</label>
                            <input type="number" id="alert-threshold-window" name="threshold_window_minutes" class="form-input"
                                   value="15" min="1" placeholder="Time window">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="alert-notification-type">Notification Type *</label>
                        <select id="alert-notification-type" name="notification_type" class="form-select" required>
                            <option value="email">Email only</option>
                            <option value="webhook">Webhook only</option>
                            <option value="both">Both email and webhook</option>
                        </select>
                    </div>

                    <div class="form-group" id="alert-email-group">
                        <label for="alert-email-recipients">Email Recipients *</label>
                        <input type="text" id="alert-email-recipients" name="email_recipients" class="form-input"
                               placeholder="email1@example.com, email2@example.com">
                        <small>Comma-separated email addresses</small>
                    </div>

                    <div class="form-group" id="alert-webhook-group" style="display: none;">
                        <label for="alert-webhook-ids">Webhook Endpoints *</label>
                        <select id="alert-webhook-ids" name="webhook_endpoint_ids" class="form-select" multiple size="4">
                            <option value="">Loading webhooks...</option>
                        </select>
                        <small>Hold Ctrl/Cmd to select multiple</small>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="alert-cooldown">Cooldown (minutes)</label>
                            <input type="number" id="alert-cooldown" name="cooldown_minutes" class="form-input"
                                   value="15" min="0" placeholder="Minutes between alerts">
                        </div>
                        <div class="form-group">
                            <label for="alert-max-per-day">Max per day</label>
                            <input type="number" id="alert-max-per-day" name="max_alerts_per_day" class="form-input"
                                   value="50" min="1" placeholder="Maximum daily alerts">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="checkbox-label">
                            <input type="checkbox" id="alert-is-enabled" name="is_enabled" checked>
                            Enable this alert rule
                        </label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="alert-cancel-btn">Cancel</button>
                <button type="button" class="btn btn-primary" id="alert-save-btn">Save Alert Rule</button>
            </div>
        </div>
    </div>

    <!-- Webhook Modal -->
    <div id="webhook-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="webhook-modal-title">New Webhook Endpoint</h3>
                <button class="modal-close" id="webhook-modal-close">&times;</button>
            </div>
            <div class="modal-body">
                <form id="webhook-form">
                    <input type="hidden" id="webhook-id" name="id">

                    <div class="form-group">
                        <label for="webhook-name">Endpoint Name *</label>
                        <input type="text" id="webhook-name" name="name" class="form-input" required
                               placeholder="e.g., Slack Notifications">
                    </div>

                    <div class="form-group">
                        <label for="webhook-url">Webhook URL *</label>
                        <input type="url" id="webhook-url" name="url" class="form-input" required
                               placeholder="https://hooks.slack.com/services/...">
                    </div>

                    <div class="form-group">
                        <label for="webhook-description">Description</label>
                        <textarea id="webhook-description" name="description" class="form-input" rows="2"
                                  placeholder="Optional description"></textarea>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="webhook-method">HTTP Method</label>
                            <select id="webhook-method" name="http_method" class="form-select">
                                <option value="POST" selected>POST</option>
                                <option value="PUT">PUT</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="webhook-timeout">Timeout (seconds)</label>
                            <input type="number" id="webhook-timeout" name="timeout_seconds" class="form-input"
                                   value="30" min="5" max="120">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="webhook-auth-type">Authentication Type</label>
                        <select id="webhook-auth-type" name="auth_type" class="form-select">
                            <option value="none" selected>None</option>
                            <option value="basic">Basic Auth</option>
                            <option value="bearer">Bearer Token</option>
                            <option value="custom_header">Custom Header</option>
                        </select>
                    </div>

                    <div class="form-group" id="webhook-auth-secret-group" style="display: none;">
                        <label for="webhook-auth-secret">Auth Secret</label>
                        <input type="password" id="webhook-auth-secret" name="auth_secret" class="form-input"
                               placeholder="Authentication token or credentials">
                        <small id="webhook-auth-help">Enter authentication credentials</small>
                    </div>

                    <div class="form-group">
                        <label for="webhook-custom-headers">Custom Headers (JSON)</label>
                        <textarea id="webhook-custom-headers" name="custom_headers" class="form-input" rows="3"
                                  placeholder='{"Content-Type": "application/json"}'></textarea>
                        <small>Optional JSON object with custom HTTP headers</small>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="webhook-max-retries">Max Retries</label>
                            <input type="number" id="webhook-max-retries" name="max_retries" class="form-input"
                                   value="3" min="0" max="10">
                        </div>
                        <div class="form-group">
                            <label for="webhook-retry-delay">Retry Delay (seconds)</label>
                            <input type="number" id="webhook-retry-delay" name="retry_delay_seconds" class="form-input"
                                   value="60" min="10" max="3600">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="checkbox-label">
                            <input type="checkbox" id="webhook-is-enabled" name="is_enabled" checked>
                            Enable this webhook
                        </label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="webhook-test-btn">Test Webhook</button>
                <button type="button" class="btn btn-secondary" id="webhook-cancel-btn">Cancel</button>
                <button type="button" class="btn btn-primary" id="webhook-save-btn">Save Webhook</button>
            </div>
        </div>
    </div>

    <!-- Confirm Delete Modal -->
    <div id="confirm-modal" class="modal">
        <div class="modal-content modal-small">
            <div class="modal-header">
                <h3>Confirm Delete</h3>
                <button class="modal-close" id="confirm-modal-close">&times;</button>
            </div>
            <div class="modal-body">
                <p id="confirm-message">Are you sure you want to delete this item?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="confirm-cancel-btn">Cancel</button>
                <button type="button" class="btn btn-error" id="confirm-delete-btn">Delete</button>
            </div>
        </div>
    </div>

    <!-- JavaScript Configuration -->
    <script>
        // Configuration
        window.alertConfig = {
            userId: '<%= userId %>',
            companyId: <%= companyId != null ? companyId : "null" %>,
            isAdmin: <%= isAdmin %>,
            apiBaseUrl: '../api/monitoring',
            brand: '<%= brand %>',
            solutions: '<%= solutions %>'
        };
    </script>

    <!-- Alert Configuration JavaScript -->
    <script src="js/alert-config.js"></script>
</body>
</html>
