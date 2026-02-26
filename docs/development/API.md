# InterWeave IDE -- Web Portal API Reference

This document describes the HTTP endpoints exposed by the InterWeave IDE web portal running on Apache Tomcat 9.0.83.

## Base URL

```
http://localhost:9090/iw-business-daemon/
```

The web portal is deployed as an expanded WAR (`iw-business-daemon`) under Tomcat's `webapps/` directory. The Tomcat port can be changed by editing `web_portal/tomcat/conf/server.xml`.

## Authentication

Authentication is session-based. Users can authenticate via the classic JSP form
or via the JSON auth API (for the React portal). Both methods create the same
Tomcat session (`JSESSIONID`) and share credentials.

| Field    | Value           |
|----------|-----------------|
| Username | `__iw_admin__`  |
| Password | `%iwps%`        |

Custom user authentication works when the database contains users with SHA-256
password hashes (LocalLoginServlet / ApiLoginServlet). See ADR 003 and
`docs/development/LOCAL_SERVLETS.md` for implementation details.

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
2. `POST /LoginServlet` -- Submits `Email` and `Password` form fields. On success, redirects to the main portal page with a valid session.

### Notes on JSP Pages

- All JSP pages return `Content-Type: text/html`.
- Pages that require authentication will redirect to `IWLogin.jsp` if no valid session exists.
- Form submissions use standard HTML form encoding (`application/x-www-form-urlencoded`).

## API Servlet Endpoints (JSON Responses)

These endpoints are newer additions that return JSON data. They are mapped via servlet configuration and compiled using `compile_servlet.bat` (see `BUILD.md`).

All API endpoints require an active session (authenticated via the login flow above).

### Auth API (JSON)

**POST /api/auth/login**  
Authenticates and creates a Tomcat session for the React portal.

**GET /api/auth/session**  
Returns session state for the React portal.

### Monitoring API (JSON)

**GET /api/monitoring/dashboard**  
Real-time dashboard summary.

**GET /api/monitoring/transactions**  
Paginated transaction list.

**GET /api/monitoring/transactions/{id}**  
Transaction detail.

**GET /api/monitoring/metrics**  
Time-series metrics.

**GET/POST/PUT/DELETE /api/monitoring/alerts/***  
Alert rules and alert history.

**GET/POST/PUT/DELETE /api/monitoring/webhooks/***  
Webhook endpoint configuration.

Full list: `WEB-INF/src/com/interweave/monitoring/api/API_ENDPOINTS.md`

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

- **Port**: 9090 (configurable in `web_portal/tomcat/conf/server.xml`)
- **WAR deployment**: `web_portal/tomcat/webapps/iw-business-daemon/`
- **Logs**: `web_portal/tomcat/logs/`
  - `catalina.out` -- Main server log
  - `localhost.*.log` -- Application-level logs

## Integration Flow Endpoints (Queries)

InterWeave IDE supports "Query" type flows that are accessible via URL. These are pseudo-REST endpoints designed to be called from external systems (e.g., Salesforce buttons, Creatio actions). Query flows are configured within the IDE workspace and their URLs are defined per-project. They are not part of the standard web portal servlet mappings; rather, they are dynamically routed through the Business Daemon.

Consult the IDE training documentation (`docs/tutorials/`) for details on creating and configuring Query flows.
