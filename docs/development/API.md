# InterWeave IDE -- Web Portal API Reference

This document describes the HTTP endpoints exposed by the InterWeave IDE web portal running on Apache Tomcat 9.0.83.

## Base URL

```
http://localhost:8080/iw-business-daemon/
```

The web portal is deployed as an expanded WAR (`iw-business-daemon`) under Tomcat's `webapps/` directory. The Tomcat port can be changed by editing `web_portal/tomcat/conf/server.xml`.

## Authentication

**Current limitation:** Only the built-in admin account is functional for authentication.

| Field    | Value           |
|----------|-----------------|
| Username | `__iw_admin__`  |
| Password | `%iwps%`        |

Custom user authentication (e.g., `demo@sample.com` / `demo123`) does not work due to a proprietary password hash format in the compiled `LoginServlet.class`. See ADR 002 (`docs/adr/002-additive-only-changes.md`) for context on why this servlet cannot be modified.

Authentication is session-based. After a successful login via `IWLogin.jsp`, Tomcat issues a `JSESSIONID` cookie that must be included in subsequent requests to access protected pages.

## JSP Pages (HTML Responses)

These endpoints serve HTML pages rendered by JavaServer Pages. They are the original web portal interface.

| Endpoint                       | Method | Description                                      |
|--------------------------------|--------|--------------------------------------------------|
| `/IWLogin.jsp`                 | GET    | Login page. Submit credentials via POST.         |
| `/EditProfile.jsp`             | GET    | User profile editor. Requires active session.    |
| `/EditCompanyProfile.jsp`      | GET    | Company profile settings. Requires active session.|
| `/CompanyConfiguration.jsp`    | GET    | Company-level configuration. Requires active session.|
| `/BDConfigurator.jsp`          | GET    | Business Daemon configurator. Requires active session.|
| `/Registration.jsp`            | GET    | New user registration form.                      |
| `/CompanyRegistration.jsp`     | GET    | New company registration form.                   |

### Login Flow

1. `GET /IWLogin.jsp` -- Renders the login form.
2. `POST /IWLogin.jsp` -- Submits `username` and `password` form fields. On success, redirects to the main portal page with a valid session. On failure, re-renders the login page with an error message.

### Notes on JSP Pages

- All JSP pages return `Content-Type: text/html`.
- Pages that require authentication will redirect to `IWLogin.jsp` if no valid session exists.
- Form submissions use standard HTML form encoding (`application/x-www-form-urlencoded`).

## API Servlet Endpoints (JSON Responses)

These endpoints are newer additions that return JSON data. They are mapped via servlet configuration and compiled using `compile_servlet.bat` (see `BUILD.md`).

All API endpoints require an active session (authenticated via the login flow above).

### GET /api/monitoring

Returns the current system monitoring status.

**Response:**
```json
{
  "status": "ok",
  "uptime": "...",
  "components": {
    "database": "connected",
    "tomcat": "running",
    "ide": "..."
  }
}
```

### GET /api/dashboard

Returns dashboard summary data for the web portal home screen.

**Response:**
```json
{
  "activeFlows": 0,
  "recentTransactions": [],
  "systemHealth": "..."
}
```

### GET /api/transactions

Returns a list of recent integration transactions.

**Query parameters:**

| Parameter | Type   | Required | Description                          |
|-----------|--------|----------|--------------------------------------|
| `limit`   | int    | No       | Maximum number of results (default: 50) |
| `offset`  | int    | No       | Pagination offset (default: 0)       |
| `status`  | string | No       | Filter by status (e.g., `success`, `failed`) |

**Response:**
```json
{
  "transactions": [
    {
      "id": "...",
      "flowName": "...",
      "status": "...",
      "timestamp": "...",
      "duration": "..."
    }
  ],
  "total": 0
}
```

### GET /api/metrics

Returns system and application metrics.

**Response:**
```json
{
  "cpu": "...",
  "memory": "...",
  "dbConnections": "...",
  "requestsPerMinute": "...",
  "errorRate": "..."
}
```

### GET /api/alerts

Returns active alerts and recent alert history.

**Response:**
```json
{
  "active": [],
  "recent": [],
  "acknowledgedCount": 0
}
```

## Error Responses

### JSP Pages

JSP pages display errors inline in the HTML. HTTP status codes follow standard Tomcat behavior:

| Code | Meaning                |
|------|------------------------|
| 200  | Success                |
| 302  | Redirect (e.g., to login) |
| 404  | Page not found         |
| 500  | Internal server error  |

### API Servlets

API endpoints return JSON error responses:

```json
{
  "error": "Description of the error",
  "code": 500
}
```

| Code | Meaning                    |
|------|----------------------------|
| 200  | Success                    |
| 401  | Not authenticated          |
| 403  | Forbidden                  |
| 404  | Endpoint not found         |
| 500  | Internal server error      |

## Tomcat Configuration

- **Port**: 8080 (configurable in `web_portal/tomcat/conf/server.xml`)
- **WAR deployment**: `web_portal/tomcat/webapps/iw-business-daemon/`
- **Logs**: `web_portal/tomcat/logs/`
  - `catalina.out` -- Main server log
  - `localhost.*.log` -- Application-level logs

## Integration Flow Endpoints (Queries)

InterWeave IDE supports "Query" type flows that are accessible via URL. These are pseudo-REST endpoints designed to be called from external systems (e.g., Salesforce buttons, Creatio actions). Query flows are configured within the IDE workspace and their URLs are defined per-project. They are not part of the standard web portal servlet mappings; rather, they are dynamically routed through the Business Daemon.

Consult the IDE training documentation (`docs/tutorials/`) for details on creating and configuring Query flows.
