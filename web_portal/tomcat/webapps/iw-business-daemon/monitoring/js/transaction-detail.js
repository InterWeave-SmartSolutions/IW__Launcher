/**
 * Transaction Detail - Detailed transaction execution view with payload display
 *
 * Provides comprehensive transaction detail viewing with:
 * - Full transaction metadata display
 * - Records processing statistics
 * - Error information with collapsible stack trace
 * - Transaction payloads with JSON syntax highlighting
 * - Collapsible payload sections
 * - Copy to clipboard functionality
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */

(function() {
    'use strict';

    // Configuration from TransactionDetail.jsp
    var config = window.transactionConfig || {
        transactionId: '',
        apiBaseUrl: '../api/monitoring',
        userId: '',
        companyId: null,
        isAdmin: false,
        brand: '',
        solutions: ''
    };

    // State
    var state = {
        transaction: null,
        payloads: [],
        loading: true,
        error: null
    };

    // DOM Elements
    var elements = {
        loadingContainer: null,
        errorContainer: null,
        detailContainer: null,
        errorMessageText: null,
        retryBtn: null
    };

    /**
     * Initialize the transaction detail view
     */
    function init() {
        // Cache DOM elements
        cacheElements();

        // Set up event listeners
        setupEventListeners();

        // Load transaction data
        loadTransaction();
    }

    /**
     * Cache DOM elements
     */
    function cacheElements() {
        elements.loadingContainer = document.getElementById('loading-container');
        elements.errorContainer = document.getElementById('error-container');
        elements.detailContainer = document.getElementById('detail-container');
        elements.errorMessageText = document.getElementById('error-message-text');
        elements.retryBtn = document.getElementById('retry-btn');
    }

    /**
     * Set up event listeners
     */
    function setupEventListeners() {
        // Retry button
        if (elements.retryBtn) {
            elements.retryBtn.addEventListener('click', function() {
                hideError();
                showLoading();
                loadTransaction();
            });
        }
    }

    /**
     * Load transaction data from API
     */
    function loadTransaction() {
        if (!config.transactionId) {
            showError('No transaction ID specified.');
            return;
        }

        state.loading = true;

        var url = config.apiBaseUrl + '/transactions/' + encodeURIComponent(config.transactionId);

        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
        .then(function(response) {
            if (response.status === 401 || response.status === 403) {
                window.location.href = '../IWLogin.jsp?error=Session expired. Please login again.';
                return null;
            }

            if (!response.ok) {
                return response.json().then(function(data) {
                    throw new Error(data.message || 'Failed to load transaction details');
                });
            }

            return response.json();
        })
        .then(function(data) {
            if (data === null) {
                return;
            }

            if (!data.success) {
                throw new Error(data.message || 'Failed to load transaction details');
            }

            state.transaction = data.data.execution;
            state.payloads = data.data.payloads || [];
            state.loading = false;

            hideLoading();
            displayTransaction();
        })
        .catch(function(error) {
            state.loading = false;
            showError(error.message || 'An error occurred while loading transaction details.');
        });
    }

    /**
     * Display transaction data
     */
    function displayTransaction() {
        var tx = state.transaction;

        // Display overview
        setText('execution-id', tx.execution_id);
        setText('flow-name', tx.flow_name);
        setText('flow-type', tx.flow_type || 'N/A');
        setText('status-text', capitalizeFirst(tx.status));
        setText('started-at', formatTimestamp(tx.started_at));
        setText('completed-at', tx.completed_at ? formatTimestamp(tx.completed_at) : 'N/A');
        setText('duration', formatDuration(tx.duration_ms));
        setText('triggered-by', tx.triggered_by_username || tx.triggered_by || 'System');
        setText('company-name', tx.company_name || 'N/A');
        setText('project-name', tx.project_name || 'N/A');
        setText('server-hostname', tx.server_hostname || 'N/A');

        // Set status badge
        var statusBadge = document.getElementById('transaction-status-badge');
        if (statusBadge) {
            statusBadge.className = 'status-badge status-' + getStatusClass(tx.status);
            statusBadge.textContent = capitalizeFirst(tx.status);
        }

        // Display records processing
        setText('records-processed', tx.records_processed || 0);
        setText('records-failed', tx.records_failed || 0);
        setText('records-skipped', tx.records_skipped || 0);

        // Display error section if transaction failed
        if (tx.status === 'failed' || tx.error_message) {
            displayErrorSection(tx);
        }

        // Display payloads
        displayPayloads();

        // Show detail container
        if (elements.detailContainer) {
            elements.detailContainer.style.display = 'block';
        }
    }

    /**
     * Display error section for failed transactions
     */
    function displayErrorSection(tx) {
        var errorSection = document.getElementById('error-section');
        if (!errorSection) return;

        errorSection.style.display = 'block';

        setText('error-code', tx.error_code || 'N/A');
        setText('error-message-box', tx.error_message || 'No error message available');

        // Set up collapsible stack trace
        if (tx.stack_trace) {
            setText('stack-trace', tx.stack_trace);
            setupCollapsible('stack-trace-toggle', 'stack-trace-section');
        } else {
            var stackTraceSection = document.getElementById('stack-trace-section');
            if (stackTraceSection) {
                stackTraceSection.style.display = 'none';
            }
        }
    }

    /**
     * Display transaction payloads
     */
    function displayPayloads() {
        var container = document.getElementById('payloads-container');
        var countEl = document.getElementById('payload-count');

        if (!container) return;

        if (state.payloads.length === 0) {
            container.innerHTML = '<p class="empty-state">No payload data available for this transaction.</p>';
            if (countEl) countEl.textContent = '0 payloads';
            return;
        }

        if (countEl) {
            countEl.textContent = state.payloads.length + ' payload' + (state.payloads.length === 1 ? '' : 's');
        }

        container.innerHTML = '';

        state.payloads.forEach(function(payload, index) {
            var payloadCard = createPayloadCard(payload, index);
            container.appendChild(payloadCard);
        });
    }

    /**
     * Create a payload card element
     */
    function createPayloadCard(payload, index) {
        var card = document.createElement('div');
        card.className = 'payload-card';

        // Header
        var header = document.createElement('div');
        header.className = 'payload-header';

        var title = document.createElement('div');
        title.className = 'payload-title';

        var badge = document.createElement('span');
        badge.className = 'payload-type-badge ' + payload.payload_type;
        badge.textContent = payload.payload_type;
        title.appendChild(badge);

        var format = document.createElement('span');
        format.textContent = payload.payload_format || 'Text';
        title.appendChild(format);

        header.appendChild(title);

        var meta = document.createElement('div');
        meta.className = 'payload-meta';

        if (payload.source_system) {
            var source = document.createElement('span');
            source.textContent = 'From: ' + payload.source_system;
            meta.appendChild(source);
        }

        if (payload.destination_system) {
            var dest = document.createElement('span');
            dest.textContent = 'To: ' + payload.destination_system;
            meta.appendChild(dest);
        }

        if (payload.payload_size) {
            var size = document.createElement('span');
            size.textContent = 'Size: ' + formatBytes(payload.payload_size);
            meta.appendChild(size);
        }

        header.appendChild(meta);
        card.appendChild(header);

        // Body
        var body = document.createElement('div');
        body.className = 'payload-body';

        // Toolbar
        var toolbar = document.createElement('div');
        toolbar.className = 'payload-toolbar';

        var info = document.createElement('div');
        info.className = 'payload-info';
        info.textContent = 'Captured: ' + formatTimestamp(payload.captured_at);
        toolbar.appendChild(info);

        var actions = document.createElement('div');
        actions.className = 'payload-actions';

        var copyBtn = document.createElement('button');
        copyBtn.className = 'btn-icon';
        copyBtn.textContent = '📋 Copy';
        copyBtn.setAttribute('data-payload-index', index);
        copyBtn.addEventListener('click', function() {
            copyPayloadToClipboard(payload);
        });
        actions.appendChild(copyBtn);

        var toggleBtn = document.createElement('button');
        toggleBtn.className = 'btn-icon';
        toggleBtn.textContent = '▼ Collapse';
        toggleBtn.setAttribute('data-payload-index', index);
        toggleBtn.addEventListener('click', function() {
            togglePayloadContent(index);
        });
        actions.appendChild(toggleBtn);

        toolbar.appendChild(actions);
        body.appendChild(toolbar);

        // Content
        var content = document.createElement('div');
        content.className = 'payload-content';
        content.id = 'payload-content-' + index;

        var code = document.createElement('pre');
        code.className = 'payload-code';

        if (payload.payload_data) {
            if (payload.payload_format === 'JSON') {
                code.className += ' json';
                code.innerHTML = syntaxHighlightJSON(payload.payload_data);
            } else if (payload.payload_format === 'XML') {
                code.className += ' xml';
                code.textContent = payload.payload_data;
            } else {
                code.textContent = payload.payload_data;
            }
        } else {
            code.textContent = 'No payload data available';
            code.style.fontStyle = 'italic';
            code.style.color = '#999';
        }

        content.appendChild(code);
        body.appendChild(content);

        card.appendChild(body);

        return card;
    }

    /**
     * Toggle payload content visibility
     */
    function togglePayloadContent(index) {
        var content = document.getElementById('payload-content-' + index);
        if (!content) return;

        var toggleBtn = document.querySelector('[data-payload-index="' + index + '"]');

        if (content.style.display === 'none') {
            content.style.display = 'block';
            if (toggleBtn) toggleBtn.textContent = '▼ Collapse';
        } else {
            content.style.display = 'none';
            if (toggleBtn) toggleBtn.textContent = '▶ Expand';
        }
    }

    /**
     * Copy payload to clipboard
     */
    function copyPayloadToClipboard(payload) {
        if (!payload.payload_data) {
            alert('No payload data to copy');
            return;
        }

        var textArea = document.createElement('textarea');
        textArea.value = payload.payload_data;
        textArea.style.position = 'fixed';
        textArea.style.left = '-9999px';
        document.body.appendChild(textArea);
        textArea.select();

        try {
            var successful = document.execCommand('copy');
            if (successful) {
                alert('Payload copied to clipboard!');
            } else {
                alert('Failed to copy payload');
            }
        } catch (err) {
            alert('Failed to copy payload: ' + err.message);
        }

        document.body.removeChild(textArea);
    }

    /**
     * Set up a collapsible section
     */
    function setupCollapsible(toggleId, sectionId) {
        var toggle = document.getElementById(toggleId);
        var section = document.getElementById(sectionId);

        if (!toggle || !section) return;

        toggle.addEventListener('click', function() {
            var content = section.querySelector('.collapsible-content');
            if (!content) return;

            var isActive = toggle.classList.contains('active');

            if (isActive) {
                toggle.classList.remove('active');
                content.classList.remove('active');
            } else {
                toggle.classList.add('active');
                content.classList.add('active');
            }
        });
    }

    /**
     * Show loading state
     */
    function showLoading() {
        if (elements.loadingContainer) {
            elements.loadingContainer.style.display = 'block';
        }
        if (elements.errorContainer) {
            elements.errorContainer.style.display = 'none';
        }
        if (elements.detailContainer) {
            elements.detailContainer.style.display = 'none';
        }
    }

    /**
     * Hide loading state
     */
    function hideLoading() {
        if (elements.loadingContainer) {
            elements.loadingContainer.style.display = 'none';
        }
    }

    /**
     * Show error state
     */
    function showError(message) {
        if (elements.errorContainer) {
            elements.errorContainer.style.display = 'block';
        }
        if (elements.errorMessageText) {
            elements.errorMessageText.textContent = message;
        }
        if (elements.loadingContainer) {
            elements.loadingContainer.style.display = 'none';
        }
        if (elements.detailContainer) {
            elements.detailContainer.style.display = 'none';
        }
    }

    /**
     * Hide error state
     */
    function hideError() {
        if (elements.errorContainer) {
            elements.errorContainer.style.display = 'none';
        }
    }

    /**
     * Set text content of element
     */
    function setText(elementId, text) {
        var el = document.getElementById(elementId);
        if (el) {
            el.textContent = text;
        }
    }

    /**
     * Format timestamp for display
     */
    function formatTimestamp(timestamp) {
        if (!timestamp) return 'N/A';

        try {
            var date = new Date(timestamp);
            var month = String(date.getMonth() + 1).padStart(2, '0');
            var day = String(date.getDate()).padStart(2, '0');
            var year = date.getFullYear();
            var hours = String(date.getHours()).padStart(2, '0');
            var minutes = String(date.getMinutes()).padStart(2, '0');
            var seconds = String(date.getSeconds()).padStart(2, '0');

            return month + '/' + day + '/' + year + ' ' + hours + ':' + minutes + ':' + seconds;
        } catch (e) {
            return timestamp;
        }
    }

    /**
     * Format duration in milliseconds
     */
    function formatDuration(ms) {
        if (ms === null || ms === undefined) return 'N/A';

        var duration = parseInt(ms);
        if (isNaN(duration)) return 'N/A';

        if (duration < 1000) {
            return duration + ' ms';
        } else if (duration < 60000) {
            return (duration / 1000).toFixed(2) + ' s';
        } else if (duration < 3600000) {
            var minutes = Math.floor(duration / 60000);
            var seconds = Math.floor((duration % 60000) / 1000);
            return minutes + ' m ' + seconds + ' s';
        } else {
            var hours = Math.floor(duration / 3600000);
            var minutes = Math.floor((duration % 3600000) / 60000);
            return hours + ' h ' + minutes + ' m';
        }
    }

    /**
     * Format bytes to human-readable size
     */
    function formatBytes(bytes) {
        if (bytes === 0) return '0 Bytes';
        if (!bytes) return 'N/A';

        var k = 1024;
        var sizes = ['Bytes', 'KB', 'MB', 'GB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
    }

    /**
     * Get CSS class for status
     */
    function getStatusClass(status) {
        var statusMap = {
            'success': 'success',
            'completed': 'success',
            'failed': 'error',
            'error': 'error',
            'running': 'running',
            'in_progress': 'running',
            'cancelled': 'warning',
            'timeout': 'warning'
        };

        return statusMap[status] || 'info';
    }

    /**
     * Capitalize first letter
     */
    function capitalizeFirst(str) {
        if (!str) return '';
        return str.charAt(0).toUpperCase() + str.slice(1);
    }

    /**
     * Syntax highlight JSON string
     */
    function syntaxHighlightJSON(jsonString) {
        if (!jsonString) return '';

        try {
            var obj = JSON.parse(jsonString);
            var pretty = JSON.stringify(obj, null, 2);

            return escapeHtml(pretty)
                .replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?)/g, function(match) {
                    var cls = 'json-string';
                    if (/:$/.test(match)) {
                        cls = 'json-key';
                        match = match.slice(0, -1) + '<span style="color:#abb2bf">:</span>';
                    }
                    return '<span class="' + cls + '">' + match + '</span>';
                })
                .replace(/\b(true|false)\b/g, '<span class="json-boolean">$1</span>')
                .replace(/\bnull\b/g, '<span class="json-null">null</span>')
                .replace(/\b(-?\d+\.?\d*)\b/g, '<span class="json-number">$1</span>');
        } catch (e) {
            return escapeHtml(jsonString);
        }
    }

    /**
     * Escape HTML to prevent XSS
     */
    function escapeHtml(text) {
        var map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return text.replace(/[&<>"']/g, function(m) { return map[m]; });
    }

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Export API for testing
    window.transactionDetail = {
        reload: loadTransaction
    };

})();
