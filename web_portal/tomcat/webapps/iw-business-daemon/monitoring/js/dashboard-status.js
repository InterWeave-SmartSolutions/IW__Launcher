/**
 * Dashboard Status - Real-time status display for monitoring dashboard
 *
 * Fetches and displays current running transactions with auto-refresh.
 * Updates status cards, running flows list, and recent transactions table.
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */

(function() {
    'use strict';

    // Configuration from Dashboard.jsp (read from data attributes for CSP compliance)
    var configEl = document.getElementById('dashboard-config');
    var config = {
        apiBaseUrl: configEl ? configEl.getAttribute('data-api-base-url') : '../api/monitoring',
        refreshInterval: configEl ? parseInt(configEl.getAttribute('data-refresh-interval'), 10) || 10000 : 10000,
        userId: configEl ? configEl.getAttribute('data-user-id') : '',
        companyId: configEl ? configEl.getAttribute('data-company-id') || null : null,
        isAdmin: configEl ? configEl.getAttribute('data-is-admin') === 'true' : false
    };

    // Refresh timer
    var refreshTimer = null;
    var errorCount = 0;
    var maxRetries = 3;
    var retryDelay = 5000;

    /**
     * Initialize dashboard on page load
     */
    function init() {
        // Load initial data
        loadDashboardData();

        // Set up auto-refresh
        startAutoRefresh();

        // Set up manual refresh button
        var refreshBtn = document.getElementById('refresh-transactions-btn');
        if (refreshBtn) {
            refreshBtn.addEventListener('click', function() {
                loadDashboardData();
            });
        }
    }

    /**
     * Start auto-refresh timer
     */
    function startAutoRefresh() {
        // Clear existing timer
        if (refreshTimer) {
            clearInterval(refreshTimer);
        }

        // Start new timer
        refreshTimer = setInterval(function() {
            loadDashboardData();
        }, config.refreshInterval);
    }

    /**
     * Stop auto-refresh timer
     */
    function stopAutoRefresh() {
        if (refreshTimer) {
            clearInterval(refreshTimer);
            refreshTimer = null;
        }
    }

    /**
     * Load dashboard data from API
     */
    function loadDashboardData() {
        var url = config.apiBaseUrl + '/dashboard';

        // Add company_id parameter if admin viewing specific company
        if (config.companyId) {
            url += '?company_id=' + config.companyId;
        }

        fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            credentials: 'same-origin'
        })
        .then(function(response) {
            if (!response.ok) {
                if (response.status === 401 || response.status === 403) {
                    // Session expired or unauthorized
                    handleSessionExpired();
                    return Promise.reject(new Error('Authentication failed'));
                }
                return Promise.reject(new Error('HTTP ' + response.status + ': ' + response.statusText));
            }
            return response.json();
        })
        .then(function(data) {
            if (data.success && data.data) {
                // Reset error count on success
                errorCount = 0;

                // Update all dashboard sections
                updateSummaryCards(data.data.summary);
                updateRunningTransactions(data.data.running_transactions || []);
                updateRecentActivity(data.data.recent_activity);

                // Update last refresh timestamp
                updateLastRefreshTime();
            } else {
                handleError(new Error(data.error || 'Invalid response format'));
            }
        })
        .catch(function(error) {
            handleError(error);
        });
    }

    /**
     * Update summary status cards
     */
    function updateSummaryCards(summary) {
        if (!summary) return;

        // Active Flows
        var activeFlowsEl = document.getElementById('active-flows-count');
        if (activeFlowsEl) {
            activeFlowsEl.innerHTML = '<span class="value">' + summary.running_count + '</span>';
        }

        // Success Rate (24h)
        var successRate24hEl = document.getElementById('success-rate-24h');
        if (successRate24hEl) {
            var rate = summary.success_rate_24h || 0;
            successRate24hEl.innerHTML = '<span class="value">' + rate.toFixed(2) + '%</span>';
        }

        // Errors Today (24h)
        var errorsTodayEl = document.getElementById('errors-today-count');
        if (errorsTodayEl) {
            var errorCount = summary.failed_count_24h || 0;
            errorsTodayEl.innerHTML = '<span class="value">' + errorCount + '</span>';
        }

        // Average Duration
        var avgDurationEl = document.getElementById('avg-duration');
        if (avgDurationEl) {
            var duration = summary.avg_duration_ms_24h || 0;
            avgDurationEl.innerHTML = '<span class="value">' + formatDuration(duration) + '</span>';
        }
    }

    /**
     * Update running transactions list
     */
    function updateRunningTransactions(transactions) {
        var container = document.getElementById('running-flows-container');
        var countEl = document.getElementById('running-flows-count');

        if (!container) return;

        // Update count
        if (countEl) {
            countEl.textContent = transactions.length;
        }

        // Clear container
        container.innerHTML = '';

        if (transactions.length === 0) {
            container.innerHTML = '<div class="empty-state">No flows currently running.</div>';
            return;
        }

        // Create grid of running flow cards
        transactions.forEach(function(flow) {
            var card = createRunningFlowCard(flow);
            container.appendChild(card);
        });
    }

    /**
     * Create a running flow card element
     */
    function createRunningFlowCard(flow) {
        var card = document.createElement('div');
        card.className = 'running-flow-card';

        var html = '<div class="flow-header">';
        html += '<h4 class="flow-name">' + escapeHtml(flow.flow_name) + '</h4>';
        html += '<span class="status-badge status-running">Running</span>';
        html += '</div>';

        html += '<div class="flow-details">';
        html += '<div class="flow-detail">';
        html += '<span class="detail-label">Execution ID:</span> ';
        html += '<span class="detail-value">' + escapeHtml(flow.execution_id) + '</span>';
        html += '</div>';

        if (flow.project_name) {
            html += '<div class="flow-detail">';
            html += '<span class="detail-label">Project:</span> ';
            html += '<span class="detail-value">' + escapeHtml(flow.project_name) + '</span>';
            html += '</div>';
        }

        html += '<div class="flow-detail">';
        html += '<span class="detail-label">Started:</span> ';
        html += '<span class="detail-value">' + formatTimestamp(flow.started_at) + '</span>';
        html += '</div>';

        html += '<div class="flow-detail">';
        html += '<span class="detail-label">Duration:</span> ';
        html += '<span class="detail-value">' + formatDuration(flow.duration_ms) + '</span>';
        html += '</div>';

        html += '<div class="flow-detail">';
        html += '<span class="detail-label">Records Processed:</span> ';
        html += '<span class="detail-value">' + flow.records_processed + '</span>';
        if (flow.records_failed > 0) {
            html += ' <span class="error-text">(' + flow.records_failed + ' failed)</span>';
        }
        html += '</div>';

        html += '</div>';

        card.innerHTML = html;
        return card;
    }

    /**
     * Update recent activity section (transactions table)
     */
    function updateRecentActivity(recentActivity) {
        // For now, we'll update the transactions table with a message
        // The full transaction history will be implemented in subtask 5.4
        var tableBody = document.getElementById('transactions-table-body');

        if (!tableBody) return;

        if (!recentActivity || !recentActivity.last_hour) {
            tableBody.innerHTML = '<tr><td colspan="6" class="table-empty">No recent transaction data available.</td></tr>';
            return;
        }

        var activity = recentActivity.last_hour;
        var message = 'Last hour: ' + activity.total + ' transactions (' +
                     activity.success + ' successful, ' + activity.failed + ' failed)';

        tableBody.innerHTML = '<tr><td colspan="6" class="table-info">' +
                             escapeHtml(message) +
                             '<br><small>Full transaction history will be available in the next update.</small></td></tr>';
    }

    /**
     * Update last refresh timestamp
     */
    function updateLastRefreshTime() {
        // Could add a "Last updated: XX seconds ago" indicator here if needed
        // For now, we rely on the data itself having timestamps
    }

    /**
     * Handle API errors
     */
    function handleError(error) {
        errorCount++;

        // Show error message in status cards
        var activeFlowsEl = document.getElementById('active-flows-count');
        if (activeFlowsEl) {
            activeFlowsEl.innerHTML = '<span class="error-text">Error</span>';
        }

        // Show error in running flows section
        var container = document.getElementById('running-flows-container');
        if (container) {
            container.innerHTML = '<div class="error-state">' +
                                 'Failed to load dashboard data. ' +
                                 (errorCount < maxRetries ? 'Retrying...' : 'Please refresh the page.') +
                                 '</div>';
        }

        // If too many errors, stop auto-refresh
        if (errorCount >= maxRetries) {
            stopAutoRefresh();
        } else {
            // Retry with backoff
            setTimeout(function() {
                loadDashboardData();
            }, retryDelay * errorCount);
        }
    }

    /**
     * Handle session expiration
     */
    function handleSessionExpired() {
        stopAutoRefresh();

        // Redirect to login page after a short delay
        setTimeout(function() {
            window.location.href = '../IWLogin.jsp?error=Session expired. Please login again.';
        }, 2000);

        // Show message
        var container = document.getElementById('running-flows-container');
        if (container) {
            container.innerHTML = '<div class="error-state">Session expired. Redirecting to login...</div>';
        }
    }

    /**
     * Format duration in milliseconds to human-readable string
     */
    function formatDuration(ms) {
        if (!ms || ms < 0) return '0s';

        var seconds = Math.floor(ms / 1000);
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);

        if (hours > 0) {
            return hours + 'h ' + (minutes % 60) + 'm';
        } else if (minutes > 0) {
            return minutes + 'm ' + (seconds % 60) + 's';
        } else if (seconds > 0) {
            return seconds + 's';
        } else {
            return ms + 'ms';
        }
    }

    /**
     * Format timestamp to human-readable string
     */
    function formatTimestamp(timestamp) {
        if (!timestamp) return 'N/A';

        try {
            var date = new Date(timestamp);

            // Check if date is valid
            if (isNaN(date.getTime())) return timestamp;

            var now = new Date();
            var diffMs = now - date;
            var diffSeconds = Math.floor(diffMs / 1000);
            var diffMinutes = Math.floor(diffSeconds / 60);
            var diffHours = Math.floor(diffMinutes / 60);

            // Show relative time for recent events
            if (diffSeconds < 60) {
                return diffSeconds + ' seconds ago';
            } else if (diffMinutes < 60) {
                return diffMinutes + ' minute' + (diffMinutes !== 1 ? 's' : '') + ' ago';
            } else if (diffHours < 24) {
                return diffHours + ' hour' + (diffHours !== 1 ? 's' : '') + ' ago';
            } else {
                // Show formatted date for older events
                return formatDate(date);
            }
        } catch (e) {
            return timestamp;
        }
    }

    /**
     * Format date to readable string
     */
    function formatDate(date) {
        var year = date.getFullYear();
        var month = padZero(date.getMonth() + 1);
        var day = padZero(date.getDate());
        var hours = padZero(date.getHours());
        var minutes = padZero(date.getMinutes());

        return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes;
    }

    /**
     * Pad number with leading zero
     */
    function padZero(num) {
        return (num < 10 ? '0' : '') + num;
    }

    /**
     * Escape HTML to prevent XSS
     */
    function escapeHtml(unsafe) {
        if (unsafe == null) return '';

        return String(unsafe)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }

    /**
     * Create status badge HTML
     */
    function createStatusBadge(status) {
        var badgeClass = 'status-badge';
        var badgeText = status;

        switch (status.toLowerCase()) {
            case 'running':
                badgeClass += ' status-running';
                badgeText = 'Running';
                break;
            case 'success':
                badgeClass += ' status-success';
                badgeText = 'Success';
                break;
            case 'failed':
                badgeClass += ' status-error';
                badgeText = 'Failed';
                break;
            case 'cancelled':
                badgeClass += ' status-warning';
                badgeText = 'Cancelled';
                break;
            case 'timeout':
                badgeClass += ' status-warning';
                badgeText = 'Timeout';
                break;
            default:
                badgeClass += ' status-info';
                break;
        }

        return '<span class="' + badgeClass + '">' + escapeHtml(badgeText) + '</span>';
    }

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Clean up on page unload
    window.addEventListener('beforeunload', function() {
        stopAutoRefresh();
    });

    // Export functions for testing (optional)
    window.dashboardStatus = {
        reload: loadDashboardData,
        stopRefresh: stopAutoRefresh,
        startRefresh: startAutoRefresh
    };

})();
