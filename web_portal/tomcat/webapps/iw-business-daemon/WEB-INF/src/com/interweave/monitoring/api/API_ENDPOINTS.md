# Monitoring API Endpoints

All monitoring API endpoints are registered in web.xml and follow the `/api/monitoring/*` pattern.

## Registered Servlets

### 1. DashboardApiServlet
- **Class:** `com.interweave.monitoring.api.DashboardApiServlet`
- **URL Pattern:** `/api/monitoring/dashboard`
- **Load Priority:** 2 (after BusinessDaemonInit)
- **Methods:** GET
- **Purpose:** Real-time dashboard data including running/completed/failed counts, success rates, and recent activity

### 2. TransactionHistoryApiServlet
- **Class:** `com.interweave.monitoring.api.TransactionHistoryApiServlet`
- **URL Patterns:** `/api/monitoring/transactions/*`
- **Load Priority:** 2
- **Methods:** GET
- **Purpose:** 
  - `GET /api/monitoring/transactions` - Paginated transaction list with filtering
  - `GET /api/monitoring/transactions/{id}` - Detailed transaction view with payload

### 3. MetricsApiServlet
- **Class:** `com.interweave.monitoring.api.MetricsApiServlet`
- **URL Pattern:** `/api/monitoring/metrics`
- **Load Priority:** 2
- **Methods:** GET
- **Purpose:** Time-series performance metrics with configurable granularity (hourly/daily/weekly)

### 4. AlertConfigApiServlet
- **Class:** `com.interweave.monitoring.api.AlertConfigApiServlet`
- **URL Patterns:** 
  - `/api/monitoring/alerts/*`
  - `/api/monitoring/webhooks/*`
- **Load Priority:** 2
- **Methods:** GET, POST, PUT, DELETE
- **Purpose:** CRUD operations for alert rules, webhook endpoints, and alert history

## Load Order
1. BusinessDaemonInit (load-on-startup=1)
2. All Monitoring API Servlets (load-on-startup=2)

## Security
All servlets extend MonitoringApiServlet which enforces:
- Session validation
- Multi-tenant isolation with company_id filtering
- Admin privilege checks where appropriate

## Web.xml Configuration
Registered in: `WEB-INF/web.xml`
- Servlet definitions: Lines 277-316
- Servlet mappings: Lines 429-448
