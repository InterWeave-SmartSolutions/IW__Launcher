/**
 * Transaction History - Filterable, sortable, paginated transaction history table
 *
 * Provides comprehensive transaction history viewing with:
 * - Real-time data from TransactionHistoryApiServlet
 * - Status filtering (All, Success, Failed, Running, etc.)
 * - Date range filtering
 * - Search by execution ID or error message
 * - Column sorting (flow name, status, start time, duration)
 * - Pagination with configurable page size
 * - Row click to view transaction details
 * - Auto-refresh with manual refresh button
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */

(function() {
    'use strict';

    // Configuration from Dashboard.jsp
    var config = window.dashboardConfig || {
        apiBaseUrl: '../api/monitoring',
        refreshInterval: 10000,
        userId: '',
        companyId: null,
        isAdmin: false
    };

    // State management
    var state = {
        transactions: [],
        pagination: {
            page: 1,
            page_size: 20,
            total_count: 0,
            total_pages: 0
        },
        filters: {
            status: '',
            start_date: '',
            end_date: '',
            search: ''
        },
        sort: {
            column: 'started_at',
            direction: 'desc'
        },
        loading: false,
        error: null
    };

    // Refresh timer
    var refreshTimer = null;

    /**
     * Initialize transaction history on page load
     */
    function init() {
        // Set up filter controls
        setupFilterControls();

        // Set up pagination controls
        setupPaginationControls();

        // Set up sorting
        setupSortingControls();

        // Set up manual refresh button
        var refreshBtn = document.getElementById('refresh-transactions-btn');
        if (refreshBtn) {
            refreshBtn.addEventListener('click', function() {
                loadTransactions();
            });
        }

        // Load initial data
        loadTransactions();

        // Start auto-refresh
        startAutoRefresh();
    }

    /**
     * Set up filter controls
     */
    function setupFilterControls() {
        // Status filter
        var statusFilter = document.getElementById('filter-status');
        if (statusFilter) {
            statusFilter.addEventListener('change', function() {
                state.filters.status = this.value;
                state.pagination.page = 1; // Reset to first page
                loadTransactions();
            });
        }

        // Date range filters
        var startDateFilter = document.getElementById('filter-start-date');
        if (startDateFilter) {
            startDateFilter.addEventListener('change', function() {
                state.filters.start_date = this.value;
                state.pagination.page = 1;
                loadTransactions();
            });
        }

        var endDateFilter = document.getElementById('filter-end-date');
        if (endDateFilter) {
            endDateFilter.addEventListener('change', function() {
                state.filters.end_date = this.value;
                state.pagination.page = 1;
                loadTransactions();
            });
        }

        // Search box
        var searchBox = document.getElementById('filter-search');
        if (searchBox) {
            // Debounce search input
            var searchTimeout = null;
            searchBox.addEventListener('input', function() {
                clearTimeout(searchTimeout);
                var searchValue = this.value;
                searchTimeout = setTimeout(function() {
                    state.filters.search = searchValue;
                    state.pagination.page = 1;
                    loadTransactions();
                }, 500); // Wait 500ms after user stops typing
            });
        }

        // Clear filters button
        var clearFiltersBtn = document.getElementById('clear-filters-btn');
        if (clearFiltersBtn) {
            clearFiltersBtn.addEventListener('click', function() {
                clearFilters();
            });
        }
    }

    /**
     * Set up pagination controls
     */
    function setupPaginationControls() {
        // Page size selector
        var pageSizeSelect = document.getElementById('page-size-select');
        if (pageSizeSelect) {
            pageSizeSelect.addEventListener('change', function() {
                state.pagination.page_size = parseInt(this.value, 10);
                state.pagination.page = 1;
                loadTransactions();
            });
        }

        // Previous page button
        var prevPageBtn = document.getElementById('prev-page-btn');
        if (prevPageBtn) {
            prevPageBtn.addEventListener('click', function() {
                if (state.pagination.page > 1) {
                    state.pagination.page--;
                    loadTransactions();
                }
            });
        }

        // Next page button
        var nextPageBtn = document.getElementById('next-page-btn');
        if (nextPageBtn) {
            nextPageBtn.addEventListener('click', function() {
                if (state.pagination.page < state.pagination.total_pages) {
                    state.pagination.page++;
                    loadTransactions();
                }
            });
        }

        // First page button
        var firstPageBtn = document.getElementById('first-page-btn');
        if (firstPageBtn) {
            firstPageBtn.addEventListener('click', function() {
                if (state.pagination.page > 1) {
                    state.pagination.page = 1;
                    loadTransactions();
                }
            });
        }

        // Last page button
        var lastPageBtn = document.getElementById('last-page-btn');
        if (lastPageBtn) {
            lastPageBtn.addEventListener('click', function() {
                if (state.pagination.page < state.pagination.total_pages) {
                    state.pagination.page = state.pagination.total_pages;
                    loadTransactions();
                }
            });
        }
    }

    /**
     * Set up sorting controls
     */
    function setupSortingControls() {
        var sortableHeaders = document.querySelectorAll('.sortable');
        sortableHeaders.forEach(function(header) {
            header.addEventListener('click', function() {
                var column = this.getAttribute('data-column');
                if (column) {
                    handleSort(column);
                }
            });
        });
    }

    /**
     * Handle column sort
     */
    function handleSort(column) {
        if (state.sort.column === column) {
            // Toggle direction
            state.sort.direction = state.sort.direction === 'asc' ? 'desc' : 'asc';
        } else {
            // New column, default to descending
            state.sort.column = column;
            state.sort.direction = 'desc';
        }

        // Sort the current data client-side for immediate feedback
        sortTransactions();

        // Update sort indicators
        updateSortIndicators();

        // Render the sorted table
        renderTransactionTable();
    }

    /**
     * Sort transactions array based on current sort state
     */
    function sortTransactions() {
        var column = state.sort.column;
        var direction = state.sort.direction;
        var multiplier = direction === 'asc' ? 1 : -1;

        state.transactions.sort(function(a, b) {
            var aValue = a[column];
            var bValue = b[column];

            // Handle null/undefined values
            if (aValue == null && bValue == null) return 0;
            if (aValue == null) return 1;
            if (bValue == null) return -1;

            // Handle different data types
            if (column === 'started_at' || column === 'completed_at') {
                // Date comparison
                var aDate = new Date(aValue);
                var bDate = new Date(bValue);
                return (aDate - bDate) * multiplier;
            } else if (column === 'duration_ms' || column === 'records_processed') {
                // Numeric comparison
                return (aValue - bValue) * multiplier;
            } else {
                // String comparison
                var aStr = String(aValue).toLowerCase();
                var bStr = String(bValue).toLowerCase();
                if (aStr < bStr) return -1 * multiplier;
                if (aStr > bStr) return 1 * multiplier;
                return 0;
            }
        });
    }

    /**
     * Update sort indicator icons in table headers
     */
    function updateSortIndicators() {
        var headers = document.querySelectorAll('.sortable');
        headers.forEach(function(header) {
            var column = header.getAttribute('data-column');
            var indicator = header.querySelector('.sort-indicator');

            if (!indicator) {
                indicator = document.createElement('span');
                indicator.className = 'sort-indicator';
                header.appendChild(indicator);
            }

            if (column === state.sort.column) {
                indicator.textContent = state.sort.direction === 'asc' ? ' ▲' : ' ▼';
            } else {
                indicator.textContent = ' ⬍';
            }
        });
    }

    /**
     * Clear all filters
     */
    function clearFilters() {
        // Reset filter state
        state.filters = {
            status: '',
            start_date: '',
            end_date: '',
            search: ''
        };
        state.pagination.page = 1;

        // Reset filter UI
        var statusFilter = document.getElementById('filter-status');
        if (statusFilter) statusFilter.value = '';

        var startDateFilter = document.getElementById('filter-start-date');
        if (startDateFilter) startDateFilter.value = '';

        var endDateFilter = document.getElementById('filter-end-date');
        if (endDateFilter) endDateFilter.value = '';

        var searchBox = document.getElementById('filter-search');
        if (searchBox) searchBox.value = '';

        // Reload data
        loadTransactions();
    }

    /**
     * Start auto-refresh timer
     */
    function startAutoRefresh() {
        if (refreshTimer) {
            clearInterval(refreshTimer);
        }

        refreshTimer = setInterval(function() {
            loadTransactions();
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
     * Load transactions from API
     */
    function loadTransactions() {
        if (state.loading) {
            return; // Prevent duplicate requests
        }

        state.loading = true;
        state.error = null;

        // Show loading state
        showLoadingState();

        // Build query parameters
        var params = [];
        params.push('page=' + state.pagination.page);
        params.push('page_size=' + state.pagination.page_size);

        if (state.filters.status) {
            params.push('status=' + encodeURIComponent(state.filters.status));
        }
        if (state.filters.start_date) {
            params.push('start_date=' + encodeURIComponent(state.filters.start_date));
        }
        if (state.filters.end_date) {
            params.push('end_date=' + encodeURIComponent(state.filters.end_date));
        }
        if (state.filters.search) {
            params.push('search=' + encodeURIComponent(state.filters.search));
        }

        // Add company_id if available
        if (config.companyId) {
            params.push('company_id=' + config.companyId);
        }

        var url = config.apiBaseUrl + '/transactions?' + params.join('&');

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
                    handleSessionExpired();
                    return Promise.reject(new Error('Authentication failed'));
                }
                return Promise.reject(new Error('HTTP ' + response.status + ': ' + response.statusText));
            }
            return response.json();
        })
        .then(function(data) {
            state.loading = false;

            if (data.success && data.data) {
                state.transactions = data.data.transactions || [];
                state.pagination = data.data.pagination || state.pagination;

                // Render the table
                renderTransactionTable();
                updatePaginationControls();
                updateSortIndicators();
            } else {
                handleError(new Error(data.error || 'Invalid response format'));
            }
        })
        .catch(function(error) {
            state.loading = false;
            handleError(error);
        });
    }

    /**
     * Show loading state in table
     */
    function showLoadingState() {
        var tableBody = document.getElementById('transactions-table-body');
        if (!tableBody) return;

        tableBody.innerHTML = '<tr><td colspan="7" class="table-loading">' +
                             '<span class="loading">Loading transactions</span>' +
                             '</td></tr>';
    }

    /**
     * Render transaction table
     */
    function renderTransactionTable() {
        var tableBody = document.getElementById('transactions-table-body');
        if (!tableBody) return;

        // Clear existing rows
        tableBody.innerHTML = '';

        if (state.transactions.length === 0) {
            var emptyMessage = state.filters.status || state.filters.search || state.filters.start_date || state.filters.end_date
                ? 'No transactions found matching your filters.'
                : 'No recent transactions available.';

            tableBody.innerHTML = '<tr><td colspan="7" class="table-empty">' +
                                 escapeHtml(emptyMessage) +
                                 '</td></tr>';
            return;
        }

        // Create rows for each transaction
        state.transactions.forEach(function(transaction) {
            var row = createTransactionRow(transaction);
            tableBody.appendChild(row);
        });
    }

    /**
     * Create a table row for a transaction
     */
    function createTransactionRow(transaction) {
        var row = document.createElement('tr');
        row.className = 'transaction-row';
        row.setAttribute('data-transaction-id', transaction.id);

        // Make row clickable
        row.style.cursor = 'pointer';
        row.addEventListener('click', function() {
            openTransactionDetail(transaction.id);
        });

        // Flow Name
        var flowNameCell = document.createElement('td');
        flowNameCell.innerHTML = escapeHtml(transaction.flow_name || 'N/A');
        row.appendChild(flowNameCell);

        // Status
        var statusCell = document.createElement('td');
        statusCell.innerHTML = createStatusBadge(transaction.status);
        row.appendChild(statusCell);

        // Started
        var startedCell = document.createElement('td');
        startedCell.innerHTML = formatTimestamp(transaction.started_at);
        row.appendChild(startedCell);

        // Duration
        var durationCell = document.createElement('td');
        durationCell.innerHTML = formatDuration(transaction.duration_ms);
        row.appendChild(durationCell);

        // Records
        var recordsCell = document.createElement('td');
        var recordsHtml = escapeHtml(String(transaction.records_processed || 0));
        if (transaction.records_failed > 0) {
            recordsHtml += ' <span class="error-text">(' + transaction.records_failed + ' failed)</span>';
        }
        recordsCell.innerHTML = recordsHtml;
        row.appendChild(recordsCell);

        // Error (if any)
        var errorCell = document.createElement('td');
        if (transaction.error_message) {
            var truncatedError = transaction.error_message.length > 50
                ? transaction.error_message.substring(0, 50) + '...'
                : transaction.error_message;
            errorCell.innerHTML = '<span class="error-text" title="' +
                                 escapeHtml(transaction.error_message) + '">' +
                                 escapeHtml(truncatedError) +
                                 '</span>';
        } else {
            errorCell.innerHTML = '-';
        }
        row.appendChild(errorCell);

        // Actions
        var actionsCell = document.createElement('td');
        actionsCell.innerHTML = '<button class="btn-link" onclick="event.stopPropagation()">View Details →</button>';
        actionsCell.addEventListener('click', function(e) {
            e.stopPropagation();
            openTransactionDetail(transaction.id);
        });
        row.appendChild(actionsCell);

        return row;
    }

    /**
     * Open transaction detail page or modal
     */
    function openTransactionDetail(transactionId) {
        // Navigate to transaction detail page
        var brand = getUrlParameter('PortalBrand') || '';
        var solutions = getUrlParameter('PortalSolutions') || '';
        window.location.href = 'TransactionDetail.jsp?id=' + transactionId +
                              '&PortalBrand=' + encodeURIComponent(brand) +
                              '&PortalSolutions=' + encodeURIComponent(solutions);
    }

    /**
     * Update pagination controls
     */
    function updatePaginationControls() {
        var pagination = state.pagination;

        // Update page info text
        var pageInfoEl = document.getElementById('page-info');
        if (pageInfoEl) {
            var start = (pagination.page - 1) * pagination.page_size + 1;
            var end = Math.min(pagination.page * pagination.page_size, pagination.total_count);
            pageInfoEl.textContent = 'Showing ' + start + '-' + end + ' of ' + pagination.total_count;
        }

        // Update button states
        var prevBtn = document.getElementById('prev-page-btn');
        var nextBtn = document.getElementById('next-page-btn');
        var firstBtn = document.getElementById('first-page-btn');
        var lastBtn = document.getElementById('last-page-btn');

        if (prevBtn) {
            prevBtn.disabled = pagination.page <= 1;
        }
        if (nextBtn) {
            nextBtn.disabled = pagination.page >= pagination.total_pages;
        }
        if (firstBtn) {
            firstBtn.disabled = pagination.page <= 1;
        }
        if (lastBtn) {
            lastBtn.disabled = pagination.page >= pagination.total_pages;
        }

        // Update current page display
        var currentPageEl = document.getElementById('current-page');
        if (currentPageEl) {
            currentPageEl.textContent = 'Page ' + pagination.page + ' of ' + pagination.total_pages;
        }
    }

    /**
     * Handle API errors
     */
    function handleError(error) {
        state.error = error.message;

        var tableBody = document.getElementById('transactions-table-body');
        if (tableBody) {
            tableBody.innerHTML = '<tr><td colspan="7" class="error-state">' +
                                 'Failed to load transactions: ' + escapeHtml(error.message) +
                                 '<br><button class="btn btn-primary" onclick="window.dashboardHistory.reload()">Retry</button>' +
                                 '</td></tr>';
        }
    }

    /**
     * Handle session expiration
     */
    function handleSessionExpired() {
        stopAutoRefresh();

        setTimeout(function() {
            window.location.href = '../IWLogin.jsp?error=Session expired. Please login again.';
        }, 2000);

        var tableBody = document.getElementById('transactions-table-body');
        if (tableBody) {
            tableBody.innerHTML = '<tr><td colspan="7" class="error-state">' +
                                 'Session expired. Redirecting to login...' +
                                 '</td></tr>';
        }
    }

    /**
     * Create status badge HTML
     */
    function createStatusBadge(status) {
        var badgeClass = 'status-badge';
        var badgeText = status;

        switch ((status || '').toLowerCase()) {
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
                badgeText = status || 'Unknown';
                break;
        }

        return '<span class="' + badgeClass + '">' + escapeHtml(badgeText) + '</span>';
    }

    /**
     * Format duration in milliseconds to human-readable string
     */
    function formatDuration(ms) {
        if (ms == null || ms < 0) return '-';

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
     * Get URL parameter by name
     */
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
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

    // Export functions for external access
    window.dashboardHistory = {
        reload: loadTransactions,
        stopRefresh: stopAutoRefresh,
        startRefresh: startAutoRefresh,
        clearFilters: clearFilters,
        setFilters: function(filters) {
            state.filters = Object.assign(state.filters, filters);
            state.pagination.page = 1;
            loadTransactions();
        }
    };

})();
