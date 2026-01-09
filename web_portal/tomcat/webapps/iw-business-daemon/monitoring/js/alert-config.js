/**
 * Alert Configuration - Manages alert rules, webhook endpoints, and alert history
 *
 * Provides comprehensive alert configuration capabilities:
 * - Alert rules CRUD operations
 * - Webhook endpoints management
 * - Alert history viewing with filtering
 * - Enable/disable toggles
 * - Webhook testing functionality
 * - Form validation
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */

(function() {
    'use strict';

    // Configuration from AlertConfig.jsp
    var config = window.alertConfig || {
        userId: '',
        companyId: null,
        isAdmin: false,
        apiBaseUrl: '../api/monitoring',
        brand: '',
        solutions: ''
    };

    // State management
    var state = {
        alertRules: [],
        webhooks: [],
        alertHistory: [],
        loading: {
            alerts: false,
            webhooks: false,
            history: false
        },
        modals: {
            alertRule: null,
            webhook: null,
            confirm: null
        },
        editingAlertId: null,
        editingWebhookId: null,
        deleteCallback: null
    };

    // DOM Elements
    var elements = {
        // Containers
        alertRulesContainer: null,
        webhooksContainer: null,
        historyContainer: null,

        // Buttons
        newAlertBtn: null,
        refreshAlertsBtn: null,
        newWebhookBtn: null,
        refreshWebhooksBtn: null,
        refreshHistoryBtn: null,

        // Modals
        alertModal: null,
        webhookModal: null,
        confirmModal: null,

        // Forms
        alertForm: null,
        webhookForm: null,

        // Filters
        historyStatusFilter: null,
        historyTypeFilter: null,
        historyLimitFilter: null,
        clearHistoryFiltersBtn: null
    };

    /**
     * Initialize the alert configuration page
     */
    function init() {
        // Wait for DOM to be ready
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', init);
            return;
        }

        // Cache DOM elements
        cacheElements();

        // Set up event listeners
        setupEventListeners();

        // Load initial data
        loadAlertRules();
        loadWebhooks();
        loadAlertHistory();
    }

    /**
     * Cache DOM elements
     */
    function cacheElements() {
        // Containers
        elements.alertRulesContainer = document.getElementById('alert-rules-container');
        elements.webhooksContainer = document.getElementById('webhooks-container');
        elements.historyContainer = document.getElementById('alert-history-container');

        // Buttons
        elements.newAlertBtn = document.getElementById('new-alert-btn');
        elements.refreshAlertsBtn = document.getElementById('refresh-alerts-btn');
        elements.newWebhookBtn = document.getElementById('new-webhook-btn');
        elements.refreshWebhooksBtn = document.getElementById('refresh-webhooks-btn');
        elements.refreshHistoryBtn = document.getElementById('refresh-history-btn');

        // Modals
        elements.alertModal = document.getElementById('alert-rule-modal');
        elements.webhookModal = document.getElementById('webhook-modal');
        elements.confirmModal = document.getElementById('confirm-modal');

        // Forms
        elements.alertForm = document.getElementById('alert-rule-form');
        elements.webhookForm = document.getElementById('webhook-form');

        // Filters
        elements.historyStatusFilter = document.getElementById('history-status-filter');
        elements.historyTypeFilter = document.getElementById('history-type-filter');
        elements.historyLimitFilter = document.getElementById('history-limit');
        elements.clearHistoryFiltersBtn = document.getElementById('clear-history-filters-btn');

        // Store modal references
        state.modals.alertRule = elements.alertModal;
        state.modals.webhook = elements.webhookModal;
        state.modals.confirm = elements.confirmModal;
    }

    /**
     * Set up event listeners
     */
    function setupEventListeners() {
        // Alert Rule buttons
        if (elements.newAlertBtn) {
            elements.newAlertBtn.addEventListener('click', showNewAlertModal);
        }
        if (elements.refreshAlertsBtn) {
            elements.refreshAlertsBtn.addEventListener('click', function() {
                loadAlertRules();
            });
        }

        // Webhook buttons
        if (elements.newWebhookBtn) {
            elements.newWebhookBtn.addEventListener('click', showNewWebhookModal);
        }
        if (elements.refreshWebhooksBtn) {
            elements.refreshWebhooksBtn.addEventListener('click', function() {
                loadWebhooks();
            });
        }

        // History buttons
        if (elements.refreshHistoryBtn) {
            elements.refreshHistoryBtn.addEventListener('click', function() {
                loadAlertHistory();
            });
        }

        // History filters
        if (elements.historyStatusFilter) {
            elements.historyStatusFilter.addEventListener('change', loadAlertHistory);
        }
        if (elements.historyTypeFilter) {
            elements.historyTypeFilter.addEventListener('change', loadAlertHistory);
        }
        if (elements.historyLimitFilter) {
            elements.historyLimitFilter.addEventListener('change', loadAlertHistory);
        }
        if (elements.clearHistoryFiltersBtn) {
            elements.clearHistoryFiltersBtn.addEventListener('click', clearHistoryFilters);
        }

        // Alert Rule modal
        setupAlertModalListeners();

        // Webhook modal
        setupWebhookModalListeners();

        // Confirm modal
        setupConfirmModalListeners();
    }

    /**
     * Set up alert rule modal listeners
     */
    function setupAlertModalListeners() {
        var closeBtn = document.getElementById('alert-modal-close');
        var cancelBtn = document.getElementById('alert-cancel-btn');
        var saveBtn = document.getElementById('alert-save-btn');
        var notificationTypeSelect = document.getElementById('alert-notification-type');

        if (closeBtn) {
            closeBtn.addEventListener('click', closeAlertModal);
        }
        if (cancelBtn) {
            cancelBtn.addEventListener('click', closeAlertModal);
        }
        if (saveBtn) {
            saveBtn.addEventListener('click', saveAlertRule);
        }

        // Notification type change handler
        if (notificationTypeSelect) {
            notificationTypeSelect.addEventListener('change', function() {
                updateAlertFormVisibility();
            });
        }

        // Close modal on background click
        if (elements.alertModal) {
            elements.alertModal.addEventListener('click', function(e) {
                if (e.target === elements.alertModal) {
                    closeAlertModal();
                }
            });
        }
    }

    /**
     * Set up webhook modal listeners
     */
    function setupWebhookModalListeners() {
        var closeBtn = document.getElementById('webhook-modal-close');
        var cancelBtn = document.getElementById('webhook-cancel-btn');
        var saveBtn = document.getElementById('webhook-save-btn');
        var testBtn = document.getElementById('webhook-test-btn');
        var authTypeSelect = document.getElementById('webhook-auth-type');

        if (closeBtn) {
            closeBtn.addEventListener('click', closeWebhookModal);
        }
        if (cancelBtn) {
            cancelBtn.addEventListener('click', closeWebhookModal);
        }
        if (saveBtn) {
            saveBtn.addEventListener('click', saveWebhook);
        }
        if (testBtn) {
            testBtn.addEventListener('click', testWebhook);
        }

        // Auth type change handler
        if (authTypeSelect) {
            authTypeSelect.addEventListener('change', function() {
                updateWebhookFormVisibility();
            });
        }

        // Close modal on background click
        if (elements.webhookModal) {
            elements.webhookModal.addEventListener('click', function(e) {
                if (e.target === elements.webhookModal) {
                    closeWebhookModal();
                }
            });
        }
    }

    /**
     * Set up confirm modal listeners
     */
    function setupConfirmModalListeners() {
        var closeBtn = document.getElementById('confirm-modal-close');
        var cancelBtn = document.getElementById('confirm-cancel-btn');
        var deleteBtn = document.getElementById('confirm-delete-btn');

        if (closeBtn) {
            closeBtn.addEventListener('click', closeConfirmModal);
        }
        if (cancelBtn) {
            cancelBtn.addEventListener('click', closeConfirmModal);
        }
        if (deleteBtn) {
            deleteBtn.addEventListener('click', function() {
                if (state.deleteCallback) {
                    state.deleteCallback();
                }
                closeConfirmModal();
            });
        }

        // Close modal on background click
        if (elements.confirmModal) {
            elements.confirmModal.addEventListener('click', function(e) {
                if (e.target === elements.confirmModal) {
                    closeConfirmModal();
                }
            });
        }
    }

    /**
     * Load alert rules from API
     */
    function loadAlertRules() {
        state.loading.alerts = true;
        showLoadingState(elements.alertRulesContainer, 'Loading alert rules');

        var url = config.apiBaseUrl + '/alerts';

        fetch(url, {
            method: 'GET',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                throw new Error('Failed to load alert rules: ' + response.statusText);
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            state.loading.alerts = false;

            if (result.success && result.data) {
                state.alertRules = result.data;
                renderAlertRules();
            } else {
                showErrorState(elements.alertRulesContainer, result.message || 'Failed to load alert rules');
            }
        })
        .catch(function(error) {
            state.loading.alerts = false;
            showErrorState(elements.alertRulesContainer, error.message);
        });
    }

    /**
     * Render alert rules list
     */
    function renderAlertRules() {
        if (!elements.alertRulesContainer) return;

        if (state.alertRules.length === 0) {
            elements.alertRulesContainer.innerHTML =
                '<div class="empty-state">' +
                '  <p>No alert rules configured.</p>' +
                '  <p>Click "New Alert Rule" to create your first alert.</p>' +
                '</div>';
            return;
        }

        var html = '<div class="alert-rules-list">';

        state.alertRules.forEach(function(rule) {
            var isEnabled = rule.is_enabled === 1 || rule.is_enabled === true;
            var disabledClass = isEnabled ? '' : ' disabled';

            html += '<div class="alert-rule-card' + disabledClass + '">';
            html += '  <div class="card-header">';
            html += '    <div class="card-title-text">';
            html += '      <h3>' + escapeHtml(rule.rule_name) + '</h3>';
            if (rule.description) {
                html += '      <p>' + escapeHtml(rule.description) + '</p>';
            }
            html += '    </div>';
            html += '    <div class="card-actions">';
            html += '      <button class="btn btn-icon btn-secondary" onclick="window.alertConfigAPI.toggleAlertRule(' + rule.id + ', ' + !isEnabled + ')">';
            html += isEnabled ? 'Disable' : 'Enable';
            html += '      </button>';
            html += '      <button class="btn btn-icon btn-secondary" onclick="window.alertConfigAPI.editAlertRule(' + rule.id + ')">Edit</button>';
            html += '      <button class="btn btn-icon btn-error" onclick="window.alertConfigAPI.deleteAlertRule(' + rule.id + ')">Delete</button>';
            html += '    </div>';
            html += '  </div>';
            html += '  <div class="card-body">';

            // Status badge
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Status:</span>';
            html += '      <span class="badge badge-' + (isEnabled ? 'enabled' : 'disabled') + '">' +
                        (isEnabled ? 'Enabled' : 'Disabled') + '</span>';
            html += '    </div>';

            // Flow filter
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Flow:</span>';
            html += '      <span class="card-detail-value">' +
                        (rule.flow_name ? escapeHtml(rule.flow_name) : 'All flows') + '</span>';
            html += '    </div>';

            // Notification type
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Notification:</span>';
            html += '      <span class="badge badge-notification">' +
                        escapeHtml(rule.notification_type || 'email') + '</span>';
            html += '    </div>';

            // Threshold
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Threshold:</span>';
            html += '      <span class="card-detail-value">' +
                        (rule.failure_threshold || 1) + ' failure(s) in ' +
                        (rule.threshold_window_minutes || 15) + ' min</span>';
            html += '    </div>';

            // Cooldown
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Cooldown:</span>';
            html += '      <span class="card-detail-value">' +
                        (rule.cooldown_minutes || 15) + ' minutes</span>';
            html += '    </div>';

            // Last triggered
            if (rule.last_triggered_at) {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Last triggered:</span>';
                html += '      <span class="card-detail-value">' +
                            formatTimestamp(rule.last_triggered_at) + '</span>';
                html += '    </div>';
            }

            // Alerts sent today
            if (rule.alerts_sent_today > 0) {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Alerts today:</span>';
                html += '      <span class="card-detail-value">' +
                            rule.alerts_sent_today + ' / ' + (rule.max_alerts_per_day || 50) + '</span>';
                html += '    </div>';
            }

            html += '  </div>';
            html += '</div>';
        });

        html += '</div>';

        elements.alertRulesContainer.innerHTML = html;
    }

    /**
     * Load webhooks from API
     */
    function loadWebhooks() {
        state.loading.webhooks = true;
        showLoadingState(elements.webhooksContainer, 'Loading webhook endpoints');

        var url = config.apiBaseUrl + '/webhooks';

        fetch(url, {
            method: 'GET',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                throw new Error('Failed to load webhooks: ' + response.statusText);
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            state.loading.webhooks = false;

            if (result.success && result.data) {
                state.webhooks = result.data;
                renderWebhooks();
                updateAlertWebhookDropdown();
            } else {
                showErrorState(elements.webhooksContainer, result.message || 'Failed to load webhooks');
            }
        })
        .catch(function(error) {
            state.loading.webhooks = false;
            showErrorState(elements.webhooksContainer, error.message);
        });
    }

    /**
     * Render webhooks list
     */
    function renderWebhooks() {
        if (!elements.webhooksContainer) return;

        if (state.webhooks.length === 0) {
            elements.webhooksContainer.innerHTML =
                '<div class="empty-state">' +
                '  <p>No webhook endpoints configured.</p>' +
                '  <p>Click "New Webhook" to add a webhook endpoint.</p>' +
                '</div>';
            return;
        }

        var html = '<div class="webhooks-list">';

        state.webhooks.forEach(function(webhook) {
            var isEnabled = webhook.is_enabled === 1 || webhook.is_enabled === true;
            var disabledClass = isEnabled ? '' : ' disabled';

            // Determine health status
            var healthClass = '';
            var healthText = 'Unknown';
            if (webhook.last_success_at && !webhook.last_failure_at) {
                healthClass = 'healthy';
                healthText = 'Healthy';
            } else if (webhook.last_failure_at) {
                if (webhook.consecutive_failures >= 3) {
                    healthClass = 'failed';
                    healthText = 'Failed (' + webhook.consecutive_failures + 'x)';
                } else {
                    healthClass = '';
                    healthText = 'Warning (' + webhook.consecutive_failures + 'x)';
                }
            }

            html += '<div class="webhook-card' + disabledClass + '">';
            html += '  <div class="card-header">';
            html += '    <div class="card-title-text">';
            html += '      <h3>' + escapeHtml(webhook.name) + '</h3>';
            if (webhook.description) {
                html += '      <p>' + escapeHtml(webhook.description) + '</p>';
            }
            html += '    </div>';
            html += '    <div class="card-actions">';
            html += '      <button class="btn btn-icon btn-secondary" onclick="window.alertConfigAPI.toggleWebhook(' + webhook.id + ', ' + !isEnabled + ')">';
            html += isEnabled ? 'Disable' : 'Enable';
            html += '      </button>';
            html += '      <button class="btn btn-icon btn-secondary" onclick="window.alertConfigAPI.editWebhook(' + webhook.id + ')">Edit</button>';
            html += '      <button class="btn btn-icon btn-error" onclick="window.alertConfigAPI.deleteWebhook(' + webhook.id + ')">Delete</button>';
            html += '    </div>';
            html += '  </div>';
            html += '  <div class="card-body">';

            // Status badge
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Status:</span>';
            html += '      <span class="badge badge-' + (isEnabled ? 'enabled' : 'disabled') + '">' +
                        (isEnabled ? 'Enabled' : 'Disabled') + '</span>';
            html += '    </div>';

            // URL
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">URL:</span>';
            html += '      <span class="card-detail-value" style="word-break: break-all;">' +
                        escapeHtml(webhook.url) + '</span>';
            html += '    </div>';

            // Method and timeout
            html += '    <div class="card-detail">';
            html += '      <span class="card-detail-label">Method:</span>';
            html += '      <span class="card-detail-value">' +
                        escapeHtml(webhook.http_method || 'POST') +
                        ' (' + (webhook.timeout_seconds || 30) + 's timeout)</span>';
            html += '    </div>';

            // Auth type
            if (webhook.auth_type && webhook.auth_type !== 'none') {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Auth:</span>';
                html += '      <span class="card-detail-value">' +
                            escapeHtml(webhook.auth_type) + '</span>';
                html += '    </div>';
            }

            // Health status
            if (healthText !== 'Unknown') {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Health:</span>';
                html += '      <span class="badge badge-health ' + healthClass + '">' + healthText + '</span>';
                html += '    </div>';
            }

            // Last success
            if (webhook.last_success_at) {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Last success:</span>';
                html += '      <span class="card-detail-value">' +
                            formatTimestamp(webhook.last_success_at) + '</span>';
                html += '    </div>';
            }

            // Last failure
            if (webhook.last_failure_at) {
                html += '    <div class="card-detail">';
                html += '      <span class="card-detail-label">Last failure:</span>';
                html += '      <span class="card-detail-value">' +
                            formatTimestamp(webhook.last_failure_at) + '</span>';
                html += '    </div>';
            }

            html += '  </div>';
            html += '</div>';
        });

        html += '</div>';

        elements.webhooksContainer.innerHTML = html;
    }

    /**
     * Load alert history from API
     */
    function loadAlertHistory() {
        state.loading.history = true;
        showLoadingState(elements.historyContainer, 'Loading alert history');

        var params = [];

        var status = elements.historyStatusFilter ? elements.historyStatusFilter.value : '';
        if (status) {
            params.push('status=' + encodeURIComponent(status));
        }

        var type = elements.historyTypeFilter ? elements.historyTypeFilter.value : '';
        if (type) {
            params.push('notification_type=' + encodeURIComponent(type));
        }

        var limit = elements.historyLimitFilter ? elements.historyLimitFilter.value : '100';
        params.push('limit=' + encodeURIComponent(limit));

        var url = config.apiBaseUrl + '/alerts/history';
        if (params.length > 0) {
            url += '?' + params.join('&');
        }

        fetch(url, {
            method: 'GET',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                throw new Error('Failed to load alert history: ' + response.statusText);
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            state.loading.history = false;

            if (result.success && result.data) {
                state.alertHistory = result.data;
                renderAlertHistory();
            } else {
                showErrorState(elements.historyContainer, result.message || 'Failed to load alert history');
            }
        })
        .catch(function(error) {
            state.loading.history = false;
            showErrorState(elements.historyContainer, error.message);
        });
    }

    /**
     * Render alert history table
     */
    function renderAlertHistory() {
        if (!elements.historyContainer) return;

        if (state.alertHistory.length === 0) {
            elements.historyContainer.innerHTML =
                '<div class="empty-state">' +
                '  <p>No alert history found.</p>' +
                '</div>';
            return;
        }

        var html = '<div class="table-container">';
        html += '<table class="alert-history-table">';
        html += '  <thead>';
        html += '    <tr>';
        html += '      <th>Sent At</th>';
        html += '      <th>Rule</th>';
        html += '      <th>Type</th>';
        html += '      <th>Recipient</th>';
        html += '      <th>Status</th>';
        html += '      <th>Retry Count</th>';
        html += '      <th>Next Retry</th>';
        html += '    </tr>';
        html += '  </thead>';
        html += '  <tbody>';

        state.alertHistory.forEach(function(alert) {
            html += '    <tr>';
            html += '      <td>' + formatTimestamp(alert.sent_at) + '</td>';
            html += '      <td>' + escapeHtml(alert.rule_name || 'Unknown') + '</td>';
            html += '      <td><span class="badge badge-notification">' + escapeHtml(alert.notification_type || 'email') + '</span></td>';
            html += '      <td style="word-break: break-all;">' + escapeHtml(alert.recipient || alert.webhook_url || '-') + '</td>';

            var statusClass = 'status-info';
            if (alert.status === 'sent') statusClass = 'status-success';
            else if (alert.status === 'failed') statusClass = 'status-error';
            else if (alert.status === 'retrying') statusClass = 'status-warning';

            html += '      <td><span class="status-badge ' + statusClass + '">' + escapeHtml(alert.status || 'pending') + '</span></td>';
            html += '      <td>' + (alert.retry_count || 0) + '</td>';
            html += '      <td>' + (alert.next_retry_at ? formatTimestamp(alert.next_retry_at) : '-') + '</td>';
            html += '    </tr>';
        });

        html += '  </tbody>';
        html += '</table>';
        html += '</div>';

        elements.historyContainer.innerHTML = html;
    }

    /**
     * Clear history filters
     */
    function clearHistoryFilters() {
        if (elements.historyStatusFilter) {
            elements.historyStatusFilter.value = '';
        }
        if (elements.historyTypeFilter) {
            elements.historyTypeFilter.value = '';
        }
        if (elements.historyLimitFilter) {
            elements.historyLimitFilter.value = '100';
        }
        loadAlertHistory();
    }

    /**
     * Show new alert rule modal
     */
    function showNewAlertModal() {
        state.editingAlertId = null;

        // Reset form
        if (elements.alertForm) {
            elements.alertForm.reset();
        }

        // Set modal title
        var title = document.getElementById('alert-modal-title');
        if (title) {
            title.textContent = 'New Alert Rule';
        }

        // Load webhooks for dropdown
        loadWebhooksForDropdown();

        // Update form visibility based on notification type
        updateAlertFormVisibility();

        // Show modal
        if (elements.alertModal) {
            elements.alertModal.classList.add('active');
        }
    }

    /**
     * Edit alert rule
     */
    function editAlertRule(alertId) {
        state.editingAlertId = alertId;

        // Find the alert rule
        var rule = state.alertRules.find(function(r) {
            return r.id === alertId;
        });

        if (!rule) {
            alert('Alert rule not found');
            return;
        }

        // Set modal title
        var title = document.getElementById('alert-modal-title');
        if (title) {
            title.textContent = 'Edit Alert Rule';
        }

        // Populate form
        document.getElementById('alert-rule-id').value = rule.id || '';
        document.getElementById('alert-rule-name').value = rule.rule_name || '';
        document.getElementById('alert-description').value = rule.description || '';
        document.getElementById('alert-flow-name').value = rule.flow_name || '';
        document.getElementById('alert-on-failure').checked = rule.alert_on_failure === 1 || rule.alert_on_failure === true;
        document.getElementById('alert-on-timeout').checked = rule.alert_on_timeout === 1 || rule.alert_on_timeout === true;
        document.getElementById('alert-threshold').value = rule.failure_threshold || 1;
        document.getElementById('alert-threshold-window').value = rule.threshold_window_minutes || 15;
        document.getElementById('alert-notification-type').value = rule.notification_type || 'email';
        document.getElementById('alert-email-recipients').value = rule.email_recipients || '';
        document.getElementById('alert-cooldown').value = rule.cooldown_minutes || 15;
        document.getElementById('alert-max-per-day').value = rule.max_alerts_per_day || 50;
        document.getElementById('alert-is-enabled').checked = rule.is_enabled === 1 || rule.is_enabled === true;

        // Load webhooks for dropdown
        loadWebhooksForDropdown(function() {
            // Set selected webhook IDs
            if (rule.webhook_endpoint_ids) {
                var webhookSelect = document.getElementById('alert-webhook-ids');
                if (webhookSelect) {
                    var ids = rule.webhook_endpoint_ids.split(',');
                    Array.from(webhookSelect.options).forEach(function(option) {
                        option.selected = ids.indexOf(option.value) !== -1;
                    });
                }
            }
        });

        // Update form visibility based on notification type
        updateAlertFormVisibility();

        // Show modal
        if (elements.alertModal) {
            elements.alertModal.classList.add('active');
        }
    }

    /**
     * Save alert rule
     */
    function saveAlertRule() {
        // Validate form
        if (!elements.alertForm.checkValidity()) {
            alert('Please fill in all required fields');
            return;
        }

        // Collect form data
        var formData = {
            rule_name: document.getElementById('alert-rule-name').value,
            description: document.getElementById('alert-description').value || null,
            flow_name: document.getElementById('alert-flow-name').value || null,
            alert_on_failure: document.getElementById('alert-on-failure').checked ? 1 : 0,
            alert_on_timeout: document.getElementById('alert-on-timeout').checked ? 1 : 0,
            failure_threshold: parseInt(document.getElementById('alert-threshold').value) || 1,
            threshold_window_minutes: parseInt(document.getElementById('alert-threshold-window').value) || 15,
            notification_type: document.getElementById('alert-notification-type').value,
            email_recipients: document.getElementById('alert-email-recipients').value || null,
            webhook_endpoint_ids: getSelectedWebhookIds() || null,
            cooldown_minutes: parseInt(document.getElementById('alert-cooldown').value) || 15,
            max_alerts_per_day: parseInt(document.getElementById('alert-max-per-day').value) || 50,
            is_enabled: document.getElementById('alert-is-enabled').checked ? 1 : 0
        };

        // Validate notification type requirements
        if (formData.notification_type === 'email' || formData.notification_type === 'both') {
            if (!formData.email_recipients) {
                alert('Email recipients are required for email notifications');
                return;
            }
        }
        if (formData.notification_type === 'webhook' || formData.notification_type === 'both') {
            if (!formData.webhook_endpoint_ids) {
                alert('Webhook endpoints are required for webhook notifications');
                return;
            }
        }

        var url = config.apiBaseUrl + '/alerts';
        var method = 'POST';

        if (state.editingAlertId) {
            url += '/' + state.editingAlertId;
            method = 'PUT';
        }

        // Disable save button during save
        var saveBtn = document.getElementById('alert-save-btn');
        if (saveBtn) {
            saveBtn.disabled = true;
            saveBtn.textContent = 'Saving...';
        }

        fetch(url, {
            method: method,
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                return response.json().then(function(err) {
                    throw new Error(err.message || 'Failed to save alert rule');
                });
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            if (result.success) {
                closeAlertModal();
                loadAlertRules();
            } else {
                alert('Error: ' + (result.message || 'Failed to save alert rule'));
            }
        })
        .catch(function(error) {
            alert('Error: ' + error.message);
        })
        .finally(function() {
            // Re-enable save button
            if (saveBtn) {
                saveBtn.disabled = false;
                saveBtn.textContent = 'Save Alert Rule';
            }
        });
    }

    /**
     * Toggle alert rule enabled status
     */
    function toggleAlertRule(alertId, enabled) {
        var url = config.apiBaseUrl + '/alerts/' + alertId;

        fetch(url, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                is_enabled: enabled ? 1 : 0
            })
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                return response.json().then(function(err) {
                    throw new Error(err.message || 'Failed to toggle alert rule');
                });
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            if (result.success) {
                loadAlertRules();
            } else {
                alert('Error: ' + (result.message || 'Failed to toggle alert rule'));
            }
        })
        .catch(function(error) {
            alert('Error: ' + error.message);
        });
    }

    /**
     * Delete alert rule
     */
    function deleteAlertRule(alertId) {
        var rule = state.alertRules.find(function(r) {
            return r.id === alertId;
        });

        var message = 'Are you sure you want to delete the alert rule "' +
                      (rule ? rule.rule_name : 'this alert') + '"?';

        showConfirmModal(message, function() {
            var url = config.apiBaseUrl + '/alerts/' + alertId;

            fetch(url, {
                method: 'DELETE',
                credentials: 'same-origin',
                headers: {
                    'Accept': 'application/json'
                }
            })
            .then(function(response) {
                if (response.status === 401 || response.status === 403) {
                    redirectToLogin();
                    return null;
                }
                if (!response.ok) {
                    return response.json().then(function(err) {
                        throw new Error(err.message || 'Failed to delete alert rule');
                    });
                }
                return response.json();
            })
            .then(function(result) {
                if (!result) return;

                if (result.success) {
                    loadAlertRules();
                } else {
                    alert('Error: ' + (result.message || 'Failed to delete alert rule'));
                }
            })
            .catch(function(error) {
                alert('Error: ' + error.message);
            });
        });
    }

    /**
     * Show new webhook modal
     */
    function showNewWebhookModal() {
        state.editingWebhookId = null;

        // Reset form
        if (elements.webhookForm) {
            elements.webhookForm.reset();
        }

        // Set modal title
        var title = document.getElementById('webhook-modal-title');
        if (title) {
            title.textContent = 'New Webhook Endpoint';
        }

        // Update form visibility based on auth type
        updateWebhookFormVisibility();

        // Show modal
        if (elements.webhookModal) {
            elements.webhookModal.classList.add('active');
        }
    }

    /**
     * Edit webhook
     */
    function editWebhook(webhookId) {
        state.editingWebhookId = webhookId;

        // Find the webhook
        var webhook = state.webhooks.find(function(w) {
            return w.id === webhookId;
        });

        if (!webhook) {
            alert('Webhook not found');
            return;
        }

        // Set modal title
        var title = document.getElementById('webhook-modal-title');
        if (title) {
            title.textContent = 'Edit Webhook Endpoint';
        }

        // Populate form
        document.getElementById('webhook-id').value = webhook.id || '';
        document.getElementById('webhook-name').value = webhook.name || '';
        document.getElementById('webhook-url').value = webhook.url || '';
        document.getElementById('webhook-description').value = webhook.description || '';
        document.getElementById('webhook-method').value = webhook.http_method || 'POST';
        document.getElementById('webhook-timeout').value = webhook.timeout_seconds || 30;
        document.getElementById('webhook-auth-type').value = webhook.auth_type || 'none';
        document.getElementById('webhook-auth-secret').value = ''; // Don't populate secrets
        document.getElementById('webhook-custom-headers').value = webhook.custom_headers || '';
        document.getElementById('webhook-max-retries').value = webhook.max_retries || 3;
        document.getElementById('webhook-retry-delay').value = webhook.retry_delay_seconds || 60;
        document.getElementById('webhook-is-enabled').checked = webhook.is_enabled === 1 || webhook.is_enabled === true;

        // Update form visibility based on auth type
        updateWebhookFormVisibility();

        // Show modal
        if (elements.webhookModal) {
            elements.webhookModal.classList.add('active');
        }
    }

    /**
     * Save webhook
     */
    function saveWebhook() {
        // Validate form
        if (!elements.webhookForm.checkValidity()) {
            alert('Please fill in all required fields');
            return;
        }

        // Collect form data
        var formData = {
            name: document.getElementById('webhook-name').value,
            url: document.getElementById('webhook-url').value,
            description: document.getElementById('webhook-description').value || null,
            http_method: document.getElementById('webhook-method').value,
            timeout_seconds: parseInt(document.getElementById('webhook-timeout').value) || 30,
            auth_type: document.getElementById('webhook-auth-type').value,
            auth_secret: document.getElementById('webhook-auth-secret').value || null,
            custom_headers: document.getElementById('webhook-custom-headers').value || null,
            max_retries: parseInt(document.getElementById('webhook-max-retries').value) || 3,
            retry_delay_seconds: parseInt(document.getElementById('webhook-retry-delay').value) || 60,
            is_enabled: document.getElementById('webhook-is-enabled').checked ? 1 : 0
        };

        // Validate JSON if custom headers provided
        if (formData.custom_headers) {
            try {
                JSON.parse(formData.custom_headers);
            } catch (e) {
                alert('Invalid JSON in custom headers: ' + e.message);
                return;
            }
        }

        var url = config.apiBaseUrl + '/webhooks';
        var method = 'POST';

        if (state.editingWebhookId) {
            url += '/' + state.editingWebhookId;
            method = 'PUT';
        }

        // Disable save button during save
        var saveBtn = document.getElementById('webhook-save-btn');
        if (saveBtn) {
            saveBtn.disabled = true;
            saveBtn.textContent = 'Saving...';
        }

        fetch(url, {
            method: method,
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                return response.json().then(function(err) {
                    throw new Error(err.message || 'Failed to save webhook');
                });
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            if (result.success) {
                closeWebhookModal();
                loadWebhooks();
            } else {
                alert('Error: ' + (result.message || 'Failed to save webhook'));
            }
        })
        .catch(function(error) {
            alert('Error: ' + error.message);
        })
        .finally(function() {
            // Re-enable save button
            if (saveBtn) {
                saveBtn.disabled = false;
                saveBtn.textContent = 'Save Webhook';
            }
        });
    }

    /**
     * Test webhook
     */
    function testWebhook() {
        var url = document.getElementById('webhook-url').value;

        if (!url) {
            alert('Please enter a webhook URL');
            return;
        }

        // Disable test button
        var testBtn = document.getElementById('webhook-test-btn');
        if (testBtn) {
            testBtn.disabled = true;
            testBtn.textContent = 'Testing...';
        }

        // Create a test payload
        var testPayload = {
            event_type: 'test',
            flow_name: 'Test Flow',
            status: 'failed',
            error_message: 'This is a test webhook notification from InterWeave Monitoring',
            timestamp: new Date().toISOString()
        };

        // For testing, we'll just try to call the URL directly
        // In a real scenario, this might go through the backend
        fetch(url, {
            method: document.getElementById('webhook-method').value || 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(testPayload)
        })
        .then(function(response) {
            if (response.ok) {
                alert('Webhook test successful! Response status: ' + response.status);
            } else {
                alert('Webhook test returned status: ' + response.status + '. Please check webhook configuration.');
            }
        })
        .catch(function(error) {
            alert('Webhook test failed: ' + error.message + '\n\nNote: This may be due to CORS restrictions. The webhook should still work from the server.');
        })
        .finally(function() {
            // Re-enable test button
            if (testBtn) {
                testBtn.disabled = false;
                testBtn.textContent = 'Test Webhook';
            }
        });
    }

    /**
     * Toggle webhook enabled status
     */
    function toggleWebhook(webhookId, enabled) {
        var url = config.apiBaseUrl + '/webhooks/' + webhookId;

        fetch(url, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                is_enabled: enabled ? 1 : 0
            })
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                redirectToLogin();
                return null;
            }
            if (!response.ok) {
                return response.json().then(function(err) {
                    throw new Error(err.message || 'Failed to toggle webhook');
                });
            }
            return response.json();
        })
        .then(function(result) {
            if (!result) return;

            if (result.success) {
                loadWebhooks();
            } else {
                alert('Error: ' + (result.message || 'Failed to toggle webhook'));
            }
        })
        .catch(function(error) {
            alert('Error: ' + error.message);
        });
    }

    /**
     * Delete webhook
     */
    function deleteWebhook(webhookId) {
        var webhook = state.webhooks.find(function(w) {
            return w.id === webhookId;
        });

        var message = 'Are you sure you want to delete the webhook "' +
                      (webhook ? webhook.name : 'this webhook') + '"?';

        showConfirmModal(message, function() {
            var url = config.apiBaseUrl + '/webhooks/' + webhookId;

            fetch(url, {
                method: 'DELETE',
                credentials: 'same-origin',
                headers: {
                    'Accept': 'application/json'
                }
            })
            .then(function(response) {
                if (response.status === 401 || response.status === 403) {
                    redirectToLogin();
                    return null;
                }
                if (!response.ok) {
                    return response.json().then(function(err) {
                        throw new Error(err.message || 'Failed to delete webhook');
                    });
                }
                return response.json();
            })
            .then(function(result) {
                if (!result) return;

                if (result.success) {
                    loadWebhooks();
                } else {
                    alert('Error: ' + (result.message || 'Failed to delete webhook'));
                }
            })
            .catch(function(error) {
                alert('Error: ' + error.message);
            });
        });
    }

    /**
     * Load webhooks for alert rule dropdown
     */
    function loadWebhooksForDropdown(callback) {
        var select = document.getElementById('alert-webhook-ids');
        if (!select) return;

        // If we already have webhooks loaded, use them
        if (state.webhooks.length > 0) {
            updateAlertWebhookDropdown();
            if (callback) callback();
            return;
        }

        // Otherwise load from API
        var url = config.apiBaseUrl + '/webhooks';

        fetch(url, {
            method: 'GET',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Failed to load webhooks');
            }
            return response.json();
        })
        .then(function(result) {
            if (result.success && result.data) {
                state.webhooks = result.data;
                updateAlertWebhookDropdown();
                if (callback) callback();
            }
        })
        .catch(function(error) {
            select.innerHTML = '<option value="">Error loading webhooks</option>';
        });
    }

    /**
     * Update alert webhook dropdown with current webhooks
     */
    function updateAlertWebhookDropdown() {
        var select = document.getElementById('alert-webhook-ids');
        if (!select) return;

        if (state.webhooks.length === 0) {
            select.innerHTML = '<option value="">No webhooks configured</option>';
            return;
        }

        var html = '';
        state.webhooks.forEach(function(webhook) {
            var disabled = webhook.is_enabled === 0 || webhook.is_enabled === false;
            html += '<option value="' + webhook.id + '"' + (disabled ? ' disabled' : '') + '>' +
                    escapeHtml(webhook.name) + (disabled ? ' (disabled)' : '') +
                    '</option>';
        });

        select.innerHTML = html;
    }

    /**
     * Get selected webhook IDs from multi-select
     */
    function getSelectedWebhookIds() {
        var select = document.getElementById('alert-webhook-ids');
        if (!select) return null;

        var selected = [];
        Array.from(select.options).forEach(function(option) {
            if (option.selected && option.value) {
                selected.push(option.value);
            }
        });

        return selected.length > 0 ? selected.join(',') : null;
    }

    /**
     * Update alert form field visibility based on notification type
     */
    function updateAlertFormVisibility() {
        var type = document.getElementById('alert-notification-type');
        if (!type) return;

        var emailGroup = document.getElementById('alert-email-group');
        var webhookGroup = document.getElementById('alert-webhook-group');
        var emailInput = document.getElementById('alert-email-recipients');
        var webhookSelect = document.getElementById('alert-webhook-ids');

        var selectedType = type.value;

        if (emailGroup && emailInput) {
            if (selectedType === 'email' || selectedType === 'both') {
                emailGroup.style.display = 'block';
                emailInput.required = true;
            } else {
                emailGroup.style.display = 'none';
                emailInput.required = false;
            }
        }

        if (webhookGroup && webhookSelect) {
            if (selectedType === 'webhook' || selectedType === 'both') {
                webhookGroup.style.display = 'block';
                webhookSelect.required = true;
            } else {
                webhookGroup.style.display = 'none';
                webhookSelect.required = false;
            }
        }
    }

    /**
     * Update webhook form field visibility based on auth type
     */
    function updateWebhookFormVisibility() {
        var authType = document.getElementById('webhook-auth-type');
        if (!authType) return;

        var authSecretGroup = document.getElementById('webhook-auth-secret-group');
        var authSecretInput = document.getElementById('webhook-auth-secret');
        var authHelp = document.getElementById('webhook-auth-help');

        var selectedType = authType.value;

        if (authSecretGroup && authSecretInput) {
            if (selectedType === 'none') {
                authSecretGroup.style.display = 'none';
                authSecretInput.required = false;
            } else {
                authSecretGroup.style.display = 'block';
                authSecretInput.required = false; // Optional unless editing

                // Update help text based on auth type
                if (authHelp) {
                    if (selectedType === 'basic') {
                        authHelp.textContent = 'Enter username:password for Basic Auth';
                    } else if (selectedType === 'bearer') {
                        authHelp.textContent = 'Enter bearer token';
                    } else if (selectedType === 'custom_header') {
                        authHelp.textContent = 'Enter header value (header name in custom headers JSON)';
                    }
                }
            }
        }
    }

    /**
     * Close alert rule modal
     */
    function closeAlertModal() {
        if (elements.alertModal) {
            elements.alertModal.classList.remove('active');
        }
        state.editingAlertId = null;
    }

    /**
     * Close webhook modal
     */
    function closeWebhookModal() {
        if (elements.webhookModal) {
            elements.webhookModal.classList.remove('active');
        }
        state.editingWebhookId = null;
    }

    /**
     * Show confirm modal
     */
    function showConfirmModal(message, callback) {
        var messageEl = document.getElementById('confirm-message');
        if (messageEl) {
            messageEl.textContent = message;
        }

        state.deleteCallback = callback;

        if (elements.confirmModal) {
            elements.confirmModal.classList.add('active');
        }
    }

    /**
     * Close confirm modal
     */
    function closeConfirmModal() {
        if (elements.confirmModal) {
            elements.confirmModal.classList.remove('active');
        }
        state.deleteCallback = null;
    }

    /**
     * Show loading state in container
     */
    function showLoadingState(container, message) {
        if (!container) return;
        container.innerHTML = '<div class="loading-state"><span class="loading">' + message + '</span></div>';
    }

    /**
     * Show error state in container
     */
    function showErrorState(container, message) {
        if (!container) return;
        container.innerHTML = '<div class="error-state">' + escapeHtml(message) + '</div>';
    }

    /**
     * Format timestamp for display
     */
    function formatTimestamp(timestamp) {
        if (!timestamp) return '-';

        var date = new Date(timestamp);
        if (isNaN(date.getTime())) return timestamp;

        var month = padZero(date.getMonth() + 1);
        var day = padZero(date.getDate());
        var year = date.getFullYear();
        var hours = padZero(date.getHours());
        var minutes = padZero(date.getMinutes());
        var seconds = padZero(date.getSeconds());

        return month + '/' + day + '/' + year + ' ' + hours + ':' + minutes + ':' + seconds;
    }

    /**
     * Pad number with zero
     */
    function padZero(num) {
        return (num < 10 ? '0' : '') + num;
    }

    /**
     * Escape HTML to prevent XSS
     */
    function escapeHtml(text) {
        if (!text) return '';
        var map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return String(text).replace(/[&<>"']/g, function(m) { return map[m]; });
    }

    /**
     * Redirect to login page
     */
    function redirectToLogin() {
        var loginUrl = '../IWLogin.jsp?error=Session expired. Please login again.';
        if (config.brand) {
            loginUrl += '&PortalBrand=' + encodeURIComponent(config.brand);
        }
        window.location.href = loginUrl;
    }

    /**
     * Public API for window access
     */
    window.alertConfigAPI = {
        reload: function() {
            loadAlertRules();
            loadWebhooks();
            loadAlertHistory();
        },
        editAlertRule: editAlertRule,
        deleteAlertRule: deleteAlertRule,
        toggleAlertRule: toggleAlertRule,
        editWebhook: editWebhook,
        deleteWebhook: deleteWebhook,
        toggleWebhook: toggleWebhook
    };

    // Initialize on load
    init();

})();
