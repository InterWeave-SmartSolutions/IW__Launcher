/**
 * Dashboard Charts - Chart.js visualizations for monitoring metrics
 *
 * Displays success/failure trends and execution time charts with configurable
 * date range selector. Uses Chart.js library for responsive visualizations.
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

    // Chart instances
    var successFailureChart = null;
    var executionTimeChart = null;

    // Current chart settings
    var currentPeriod = '7d';
    var currentGranularity = 'daily';

    /**
     * Initialize charts on page load
     */
    function init() {
        // Set up date range selector
        var rangeSelector = document.getElementById('chart-range-selector');
        if (rangeSelector) {
            rangeSelector.addEventListener('change', function() {
                currentPeriod = this.value;
                currentGranularity = determineGranularity(currentPeriod);
                loadMetricsData();
            });
        }

        // Load initial data
        loadMetricsData();

        // Set up auto-refresh (same interval as status data)
        setInterval(function() {
            loadMetricsData();
        }, config.refreshInterval);
    }

    /**
     * Determine appropriate granularity based on period
     *
     * @param {string} period - Period string (24h, 7d, 30d)
     * @return {string} Granularity (hourly, daily, weekly)
     */
    function determineGranularity(period) {
        switch (period) {
            case '24h':
                return 'hourly';
            case '7d':
                return 'daily';
            case '30d':
                return 'daily';
            default:
                return 'daily';
        }
    }

    /**
     * Load metrics data from API
     */
    function loadMetricsData() {
        var url = config.apiBaseUrl + '/metrics?period=' + currentPeriod +
                  '&granularity=' + currentGranularity;

        // Add company_id parameter if admin viewing specific company
        if (config.companyId) {
            url += '&company_id=' + config.companyId;
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
                    handleSessionExpired();
                    return Promise.reject(new Error('Authentication failed'));
                }
                return Promise.reject(new Error('HTTP ' + response.status + ': ' + response.statusText));
            }
            return response.json();
        })
        .then(function(data) {
            if (data.success && data.data) {
                updateCharts(data.data);
            } else {
                throw new Error(data.message || 'Failed to load metrics data');
            }
        })
        .catch(function(error) {
            handleError(error);
        });
    }

    /**
     * Update or create charts with new data
     *
     * @param {Object} metricsData - Metrics data from API
     */
    function updateCharts(metricsData) {
        updateSuccessFailureChart(metricsData);
        updateExecutionTimeChart(metricsData);
    }

    /**
     * Update success/failure trends chart
     *
     * @param {Object} metricsData - Metrics data with labels and datasets
     */
    function updateSuccessFailureChart(metricsData) {
        var canvas = document.getElementById('success-failure-chart');
        if (!canvas) {
            return;
        }

        var ctx = canvas.getContext('2d');

        // Find success and failure datasets
        var successData = findDataset(metricsData.datasets, 'Successful Executions');
        var failureData = findDataset(metricsData.datasets, 'Failed Executions');

        var chartData = {
            labels: metricsData.labels,
            datasets: [
                {
                    label: 'Successful Executions',
                    data: successData || [],
                    borderColor: 'rgb(92, 184, 92)',
                    backgroundColor: 'rgba(92, 184, 92, 0.1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointRadius: 3,
                    pointHoverRadius: 5
                },
                {
                    label: 'Failed Executions',
                    data: failureData || [],
                    borderColor: 'rgb(217, 83, 79)',
                    backgroundColor: 'rgba(217, 83, 79, 0.1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointRadius: 3,
                    pointHoverRadius: 5
                }
            ]
        };

        var options = {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 11
                        },
                        usePointStyle: true,
                        padding: 15
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0, 0, 0, 0.8)',
                    padding: 10,
                    titleFont: {
                        family: 'Verdana, Arial, sans-serif',
                        size: 12
                    },
                    bodyFont: {
                        family: 'Verdana, Arial, sans-serif',
                        size: 11
                    },
                    callbacks: {
                        title: function(tooltipItems) {
                            return formatChartLabel(tooltipItems[0].label);
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 10
                        },
                        maxRotation: 45,
                        minRotation: 0,
                        callback: function(value, index, values) {
                            var label = this.getLabelForValue(value);
                            return formatChartLabel(label);
                        }
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 10
                        },
                        precision: 0
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.05)'
                    }
                }
            }
        };

        // Destroy existing chart if it exists
        if (successFailureChart) {
            successFailureChart.destroy();
        }

        // Create new chart
        successFailureChart = new Chart(ctx, {
            type: 'line',
            data: chartData,
            options: options
        });
    }

    /**
     * Update execution time trends chart
     *
     * @param {Object} metricsData - Metrics data with labels and datasets
     */
    function updateExecutionTimeChart(metricsData) {
        var canvas = document.getElementById('execution-time-chart');
        if (!canvas) {
            return;
        }

        var ctx = canvas.getContext('2d');

        // Find duration dataset
        var durationData = findDataset(metricsData.datasets, 'Average Duration (ms)');

        var chartData = {
            labels: metricsData.labels,
            datasets: [
                {
                    label: 'Average Duration (ms)',
                    data: durationData || [],
                    borderColor: 'rgb(91, 192, 222)',
                    backgroundColor: 'rgba(91, 192, 222, 0.1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointRadius: 3,
                    pointHoverRadius: 5
                }
            ]
        };

        var options = {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 11
                        },
                        usePointStyle: true,
                        padding: 15
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0, 0, 0, 0.8)',
                    padding: 10,
                    titleFont: {
                        family: 'Verdana, Arial, sans-serif',
                        size: 12
                    },
                    bodyFont: {
                        family: 'Verdana, Arial, sans-serif',
                        size: 11
                    },
                    callbacks: {
                        title: function(tooltipItems) {
                            return formatChartLabel(tooltipItems[0].label);
                        },
                        label: function(context) {
                            var label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            var value = context.parsed.y;
                            label += formatDuration(value);
                            return label;
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 10
                        },
                        maxRotation: 45,
                        minRotation: 0,
                        callback: function(value, index, values) {
                            var label = this.getLabelForValue(value);
                            return formatChartLabel(label);
                        }
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        font: {
                            family: 'Verdana, Arial, sans-serif',
                            size: 10
                        },
                        callback: function(value) {
                            return formatDuration(value);
                        }
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.05)'
                    }
                }
            }
        };

        // Destroy existing chart if it exists
        if (executionTimeChart) {
            executionTimeChart.destroy();
        }

        // Create new chart
        executionTimeChart = new Chart(ctx, {
            type: 'line',
            data: chartData,
            options: options
        });
    }

    /**
     * Find dataset by label from metrics data
     *
     * @param {Array} datasets - Array of datasets
     * @param {string} label - Dataset label to find
     * @return {Array} Dataset data array
     */
    function findDataset(datasets, label) {
        if (!datasets || !Array.isArray(datasets)) {
            return [];
        }

        for (var i = 0; i < datasets.length; i++) {
            if (datasets[i].label === label) {
                return datasets[i].data;
            }
        }

        return [];
    }

    /**
     * Format chart label for display (shorten dates for readability)
     *
     * @param {string} label - Full timestamp label
     * @return {string} Formatted label
     */
    function formatChartLabel(label) {
        if (!label) {
            return '';
        }

        // For hourly: show just the hour (e.g., "14:00")
        if (currentGranularity === 'hourly' && label.length > 10) {
            return label.substring(11, 16);
        }

        // For daily: show just the date (e.g., "Jan 09")
        if (currentGranularity === 'daily') {
            var parts = label.split('-');
            if (parts.length === 3) {
                var monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                                 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
                var month = parseInt(parts[1], 10);
                var day = parseInt(parts[2], 10);
                if (month >= 1 && month <= 12) {
                    return monthNames[month - 1] + ' ' + day;
                }
            }
        }

        return label;
    }

    /**
     * Format duration in milliseconds for display
     *
     * @param {number} ms - Duration in milliseconds
     * @return {string} Formatted duration
     */
    function formatDuration(ms) {
        if (!ms || ms < 0) {
            return '0ms';
        }

        if (ms < 1000) {
            return Math.round(ms) + 'ms';
        }

        var seconds = ms / 1000;
        if (seconds < 60) {
            return seconds.toFixed(1) + 's';
        }

        var minutes = seconds / 60;
        if (minutes < 60) {
            return minutes.toFixed(1) + 'm';
        }

        var hours = minutes / 60;
        return hours.toFixed(1) + 'h';
    }

    /**
     * Handle session expiration
     */
    function handleSessionExpired() {
        window.location.href = '../IWLogin.jsp?error=Session expired. Please login again.';
    }

    /**
     * Handle errors gracefully
     *
     * @param {Error} error - Error object
     */
    function handleError(error) {
        // Show error message in console for debugging
        if (window.console && console.error) {
            console.error('Chart error:', error);
        }

        // Show error message in chart containers
        showChartError('success-failure-chart', 'Failed to load chart data');
        showChartError('execution-time-chart', 'Failed to load chart data');
    }

    /**
     * Display error message in chart container
     *
     * @param {string} canvasId - Canvas element ID
     * @param {string} message - Error message
     */
    function showChartError(canvasId, message) {
        var canvas = document.getElementById(canvasId);
        if (!canvas) {
            return;
        }

        var container = canvas.parentElement;
        if (container) {
            var errorDiv = container.querySelector('.chart-error');
            if (!errorDiv) {
                errorDiv = document.createElement('div');
                errorDiv.className = 'chart-error error-text';
                errorDiv.style.textAlign = 'center';
                errorDiv.style.padding = '20px';
                container.appendChild(errorDiv);
            }
            errorDiv.textContent = message;
            canvas.style.display = 'none';
        }
    }

    /**
     * Reload charts manually
     */
    function reload() {
        loadMetricsData();
    }

    /**
     * Set period and reload charts
     *
     * @param {string} period - New period value (24h, 7d, 30d)
     */
    function setPeriod(period) {
        currentPeriod = period;
        currentGranularity = determineGranularity(period);

        // Update selector
        var rangeSelector = document.getElementById('chart-range-selector');
        if (rangeSelector) {
            rangeSelector.value = period;
        }

        loadMetricsData();
    }

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Export public API
    window.dashboardCharts = {
        reload: reload,
        setPeriod: setPeriod
    };

})();
