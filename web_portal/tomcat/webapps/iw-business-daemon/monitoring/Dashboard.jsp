<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession, com.interweave.web.HtmlEncoder"%>
<%
    // Session authentication check
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("authenticated") == null ||
        !(Boolean)userSession.getAttribute("authenticated")) {
        response.sendRedirect("../IWLogin.jsp?error=Session expired. Please login again.");
        return;
    }

    // Get user context from session
    String userId = String.valueOf(userSession.getAttribute("userId"));
    String userName = (String) userSession.getAttribute("userName");
    Integer companyId = (userSession.getAttribute("companyId") instanceof Integer) ?
        (Integer) userSession.getAttribute("companyId") : null;
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
    // URL-encode for safe use in href attributes
    String brandEnc = java.net.URLEncoder.encode(brand, "UTF-8");
    String solEnc = java.net.URLEncoder.encode(solutions, "UTF-8");

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
    <title>Integration Monitoring Dashboard - InterWeave IDE</title>

    <!-- Dashboard CSS -->
    <link rel="stylesheet" href="css/dashboard.css">

    <!-- Chart.js for visualizations -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>

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
                <h1>Integration Monitoring Dashboard</h1>
                <p class="header-subtitle">Real-time integration status and performance metrics</p>
            </div>
            <div class="header-user-info">
                <span class="user-name"><strong>User:</strong> <%= HtmlEncoder.encode(userName) %></span>
                <% if (companyName != null && !companyName.equals("Unknown Company")) { %>
                <span class="company-name"><strong>Company:</strong> <%= HtmlEncoder.encode(companyName) %></span>
                <% } %>
                <% if (isAdmin) { %>
                <span class="admin-badge">Admin</span>
                <% } %>
            </div>
            <div class="header-actions">
                <a href="../BDConfigurator.jsp?PortalBrand=<%= brandEnc %>&PortalSolutions=<%= solEnc %>"
                   class="btn btn-secondary">
                    ← Back to Portal
                </a>
                <a href="../LogoutServlet?PortalBrand=<%= brandEnc %>"
                   class="btn btn-secondary">
                    Logout
                </a>
            </div>
        </div>
    </header>

    <!-- Main Dashboard Content -->
    <main class="dashboard-main">
        <!-- Status Cards Section -->
        <section class="status-cards">
            <div class="card card-active">
                <div class="card-icon">
                    <span class="icon icon-running">▶</span>
                </div>
                <div class="card-content">
                    <h3 class="card-title">Active Flows</h3>
                    <div class="card-value" id="active-flows-count">
                        <span class="loading">Loading...</span>
                    </div>
                    <p class="card-subtitle">Currently running</p>
                </div>
            </div>

            <div class="card card-success">
                <div class="card-icon">
                    <span class="icon icon-success">✓</span>
                </div>
                <div class="card-content">
                    <h3 class="card-title">Success Rate (24h)</h3>
                    <div class="card-value" id="success-rate-24h">
                        <span class="loading">Loading...</span>
                    </div>
                    <p class="card-subtitle">Last 24 hours</p>
                </div>
            </div>

            <div class="card card-error">
                <div class="card-icon">
                    <span class="icon icon-error">✕</span>
                </div>
                <div class="card-content">
                    <h3 class="card-title">Errors Today</h3>
                    <div class="card-value" id="errors-today-count">
                        <span class="loading">Loading...</span>
                    </div>
                    <p class="card-subtitle">Failed transactions</p>
                </div>
            </div>

            <div class="card card-info">
                <div class="card-icon">
                    <span class="icon icon-chart">📊</span>
                </div>
                <div class="card-content">
                    <h3 class="card-title">Avg Duration</h3>
                    <div class="card-value" id="avg-duration">
                        <span class="loading">Loading...</span>
                    </div>
                    <p class="card-subtitle">Last 24 hours</p>
                </div>
            </div>
        </section>

        <!-- Charts Section -->
        <section class="charts-section">
            <div class="chart-container">
                <div class="chart-header">
                    <h2 class="chart-title">Transaction Success/Failure Trends</h2>
                    <div class="chart-controls">
                        <select id="chart-range-selector" class="form-select">
                            <option value="24h" selected>Last 24 Hours</option>
                            <option value="7d">Last 7 Days</option>
                            <option value="30d">Last 30 Days</option>
                        </select>
                    </div>
                </div>
                <div class="chart-body">
                    <canvas id="success-failure-chart"></canvas>
                </div>
            </div>

            <div class="chart-container">
                <div class="chart-header">
                    <h2 class="chart-title">Execution Time Trends</h2>
                    <div class="chart-controls">
                        <span class="chart-info">Average duration in milliseconds</span>
                    </div>
                </div>
                <div class="chart-body">
                    <canvas id="execution-time-chart"></canvas>
                </div>
            </div>
        </section>

        <!-- Recent Transactions Table -->
        <section class="transactions-section">
            <div class="section-header">
                <h2 class="section-title">Recent Transactions</h2>
                <div class="section-actions">
                    <button id="refresh-transactions-btn" class="btn btn-primary">
                        ↻ Refresh
                    </button>
                </div>
            </div>

            <!-- Filter Controls -->
            <div class="filter-controls">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="filter-status">Status:</label>
                        <select id="filter-status" class="form-select">
                            <option value="">All Statuses</option>
                            <option value="success">Success</option>
                            <option value="failed">Failed</option>
                            <option value="running">Running</option>
                            <option value="cancelled">Cancelled</option>
                            <option value="timeout">Timeout</option>
                        </select>
                    </div>

                    <div class="filter-group">
                        <label for="filter-start-date">From:</label>
                        <input type="date" id="filter-start-date" class="form-input">
                    </div>

                    <div class="filter-group">
                        <label for="filter-end-date">To:</label>
                        <input type="date" id="filter-end-date" class="form-input">
                    </div>

                    <div class="filter-group filter-search-group">
                        <label for="filter-search">Search:</label>
                        <input type="text" id="filter-search" class="form-input"
                               placeholder="Execution ID or error message...">
                    </div>

                    <div class="filter-group">
                        <button id="clear-filters-btn" class="btn btn-secondary">
                            Clear Filters
                        </button>
                    </div>
                </div>
            </div>

            <div class="table-container">
                <table class="transactions-table" id="transactions-table">
                    <thead>
                        <tr>
                            <th class="sortable" data-column="flow_name">
                                Flow Name<span class="sort-indicator"></span>
                            </th>
                            <th class="sortable" data-column="status">
                                Status<span class="sort-indicator"></span>
                            </th>
                            <th class="sortable" data-column="started_at">
                                Started<span class="sort-indicator"></span>
                            </th>
                            <th class="sortable" data-column="duration_ms">
                                Duration<span class="sort-indicator"></span>
                            </th>
                            <th class="sortable" data-column="records_processed">
                                Records<span class="sort-indicator"></span>
                            </th>
                            <th>Error</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="transactions-table-body">
                        <tr>
                            <td colspan="7" class="table-loading">
                                <span class="loading">Loading transactions</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Pagination Controls -->
            <div class="pagination-controls">
                <div class="pagination-info">
                    <span id="page-info">Showing 0-0 of 0</span>
                </div>
                <div class="pagination-buttons">
                    <button id="first-page-btn" class="btn btn-secondary btn-sm">⟨⟨</button>
                    <button id="prev-page-btn" class="btn btn-secondary btn-sm">⟨</button>
                    <span id="current-page" class="page-display">Page 1 of 1</span>
                    <button id="next-page-btn" class="btn btn-secondary btn-sm">⟩</button>
                    <button id="last-page-btn" class="btn btn-secondary btn-sm">⟩⟩</button>
                </div>
                <div class="pagination-size">
                    <label for="page-size-select">Per page:</label>
                    <select id="page-size-select" class="form-select">
                        <option value="10">10</option>
                        <option value="20" selected>20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </div>
            </div>
        </section>

        <!-- Running Flows Section -->
        <section class="running-flows-section">
            <div class="section-header">
                <h2 class="section-title">Currently Running Flows</h2>
                <div class="section-info">
                    <span id="running-flows-count">0</span> active
                </div>
            </div>

            <div id="running-flows-container" class="running-flows-list">
                <div class="empty-state">
                    <span class="loading">Loading...</span>
                </div>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>&copy; 2026 InterWeave Integration Manager. All rights reserved.</p>
            <p>
                <a href="AlertConfig.jsp?PortalBrand=<%= brandEnc %>&PortalSolutions=<%= solEnc %>">Alert Configuration</a> |
                <a href="http://www.interweave.biz" target="_blank">InterWeave Home</a> |
                <a href="mailto:support@interweave.biz">Support</a>
            </p>
        </div>
    </footer>

    <!-- Dashboard JavaScript Configuration -->
    <script>
        // Configuration
        window.dashboardConfig = {
            userId: '<%= HtmlEncoder.encode(userId) %>',
            companyId: <%= companyId != null ? companyId : "null" %>,
            isAdmin: <%= isAdmin %>,
            refreshInterval: 10000, // 10 seconds
            apiBaseUrl: '../api/monitoring'
        };
    </script>

    <!-- Dashboard Real-time Status Display -->
    <script src="js/dashboard-status.js"></script>

    <!-- Dashboard Charts Visualizations -->
    <script src="js/dashboard-charts.js"></script>

    <!-- Transaction History Table -->
    <script src="js/transaction-history.js"></script>
</body>
</html>
