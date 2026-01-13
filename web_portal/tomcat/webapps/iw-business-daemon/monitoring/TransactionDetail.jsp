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

    // Get transaction ID from URL parameter
    String transactionId = request.getParameter("id");
    if (transactionId == null || transactionId.trim().isEmpty()) {
        response.sendRedirect("Dashboard.jsp?error=No transaction ID specified.");
        return;
    }

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
    <title>Transaction Details - InterWeave IDE</title>

    <!-- Dashboard CSS -->
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/transaction-detail.css">

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
                <h1>Transaction Details</h1>
                <p class="header-subtitle">Detailed execution information and payload data</p>
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
        <div id="loading-container" class="loading-container">
            <div class="loading-spinner">
                <span class="loading">Loading transaction details</span>
            </div>
        </div>

        <div id="error-container" class="error-container" style="display: none;">
            <div class="error-message">
                <h3>Error Loading Transaction</h3>
                <p id="error-message-text"></p>
                <button id="retry-btn" class="btn btn-primary">Retry</button>
                <a href="Dashboard.jsp?PortalBrand=<%= brand %>&PortalSolutions=<%= solutions %>"
                   class="btn btn-secondary">
                    Back to Dashboard
                </a>
            </div>
        </div>

        <div id="detail-container" class="detail-container" style="display: none;">
            <!-- Transaction Overview -->
            <section class="detail-section overview-section">
                <div class="section-header">
                    <h2 class="section-title">Transaction Overview</h2>
                    <div id="transaction-status-badge"></div>
                </div>
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">Execution ID:</span>
                        <span class="detail-value" id="execution-id"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Flow Name:</span>
                        <span class="detail-value" id="flow-name"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Flow Type:</span>
                        <span class="detail-value" id="flow-type"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value" id="status-text"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Started:</span>
                        <span class="detail-value" id="started-at"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Completed:</span>
                        <span class="detail-value" id="completed-at"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Duration:</span>
                        <span class="detail-value" id="duration"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Triggered By:</span>
                        <span class="detail-value" id="triggered-by"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Company:</span>
                        <span class="detail-value" id="company-name"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Project:</span>
                        <span class="detail-value" id="project-name"></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Server:</span>
                        <span class="detail-value" id="server-hostname"></span>
                    </div>
                </div>
            </section>

            <!-- Records Processing Statistics -->
            <section class="detail-section records-section">
                <div class="section-header">
                    <h2 class="section-title">Records Processing</h2>
                </div>
                <div class="records-grid">
                    <div class="record-card card-success">
                        <div class="record-icon">✓</div>
                        <div class="record-content">
                            <div class="record-label">Processed</div>
                            <div class="record-value" id="records-processed">0</div>
                        </div>
                    </div>
                    <div class="record-card card-error">
                        <div class="record-icon">✕</div>
                        <div class="record-content">
                            <div class="record-label">Failed</div>
                            <div class="record-value" id="records-failed">0</div>
                        </div>
                    </div>
                    <div class="record-card card-warning">
                        <div class="record-icon">⊘</div>
                        <div class="record-content">
                            <div class="record-label">Skipped</div>
                            <div class="record-value" id="records-skipped">0</div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Error Information (only shown if transaction failed) -->
            <section class="detail-section error-section" id="error-section" style="display: none;">
                <div class="section-header">
                    <h2 class="section-title">Error Information</h2>
                </div>
                <div class="error-details">
                    <div class="error-detail-item">
                        <span class="detail-label">Error Code:</span>
                        <span class="detail-value error-code" id="error-code"></span>
                    </div>
                    <div class="error-detail-item">
                        <span class="detail-label">Error Message:</span>
                        <div class="detail-value error-message-box" id="error-message-box"></div>
                    </div>
                    <div class="collapsible-section" id="stack-trace-section">
                        <button class="collapsible-header" id="stack-trace-toggle">
                            <span class="toggle-icon">▶</span>
                            <span>Stack Trace</span>
                        </button>
                        <div class="collapsible-content">
                            <pre class="stack-trace" id="stack-trace"></pre>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Transaction Payloads -->
            <section class="detail-section payloads-section">
                <div class="section-header">
                    <h2 class="section-title">Transaction Payloads</h2>
                    <span class="section-info" id="payload-count">0 payloads</span>
                </div>
                <div id="payloads-container">
                    <p class="empty-state">No payload data available for this transaction.</p>
                </div>
            </section>

            <!-- Future Enhancement Placeholder -->
            <section class="detail-section actions-section">
                <div class="section-header">
                    <h2 class="section-title">Actions</h2>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-secondary" disabled title="Feature coming soon">
                        🔄 Re-run Transaction
                    </button>
                    <button class="btn btn-secondary" disabled title="Feature coming soon">
                        📋 Copy Configuration
                    </button>
                    <button class="btn btn-secondary" disabled title="Feature coming soon">
                        📊 View Related Transactions
                    </button>
                </div>
                <p class="action-note">Note: These features are planned for future releases.</p>
            </section>
        </div>
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

    <!-- JavaScript Configuration -->
    <script>
        // Configuration
        window.transactionConfig = {
            transactionId: '<%= transactionId %>',
            userId: '<%= userId %>',
            companyId: <%= companyId != null ? companyId : "null" %>,
            isAdmin: <%= isAdmin %>,
            apiBaseUrl: '../api/monitoring',
            brand: '<%= brand %>',
            solutions: '<%= solutions %>'
        };
    </script>

    <!-- Transaction Detail JavaScript -->
    <script src="js/transaction-detail.js"></script>
</body>
</html>
