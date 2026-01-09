# Dashboard API Documentation

## DashboardApiServlet

Provides real-time dashboard data for the monitoring UI.

### Endpoint

**GET** `/api/monitoring/dashboard`

### Authentication

Requires valid user session (authenticated).

### Query Parameters

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `company_id` | integer | No | User's company | Filter results by specific company. Non-admin users can only view their own company data. |
| `include_running` | boolean | No | true | Include detailed list of currently running transactions. |

### Response Format

```json
{
  "success": true,
  "data": {
    "summary": {
      "running_count": 5,
      "completed_count_24h": 1234,
      "failed_count_24h": 23,
      "total_count_24h": 1257,
      "success_rate_24h": 98.17,
      "completed_count_7d": 8650,
      "failed_count_7d": 150,
      "total_count_7d": 8800,
      "success_rate_7d": 98.30,
      "avg_duration_ms_24h": 1523,
      "last_updated": "2026-01-09T14:30:00Z"
    },
    "running_transactions": [
      {
        "execution_id": "exec-12345-abcd",
        "flow_name": "Customer Order Sync",
        "flow_type": "transaction",
        "started_at": "2026-01-09T14:25:00Z",
        "duration_ms": 5000,
        "records_processed": 150,
        "records_failed": 2,
        "project_name": "ERP Integration"
      }
    ],
    "recent_activity": {
      "last_hour": {
        "total": 45,
        "success": 43,
        "failed": 2
      }
    }
  }
}
```

### Error Response

```json
{
  "success": false,
  "error": {
    "code": "AUTH005",
    "message": "You are not authorized to view data for other companies",
    "suggestedResolution": "Please contact your administrator"
  }
}
```

### Status Codes

- `200 OK` - Success
- `400 Bad Request` - Invalid parameters
- `401 Unauthorized` - Not authenticated
- `403 Forbidden` - Not authorized (trying to view other company's data)
- `405 Method Not Allowed` - Only GET is supported
- `500 Internal Server Error` - Database or server error

### Usage Examples

#### Basic Request (Own Company)
```javascript
fetch('/api/monitoring/dashboard')
  .then(response => response.json())
  .then(data => {
    console.log('Dashboard:', data);
  });
```

#### Request with Company Filter (Admin Only)
```javascript
fetch('/api/monitoring/dashboard?company_id=42')
  .then(response => response.json())
  .then(data => {
    console.log('Company 42 Dashboard:', data);
  });
```

#### Request Without Running Transactions
```javascript
fetch('/api/monitoring/dashboard?include_running=false')
  .then(response => response.json())
  .then(data => {
    console.log('Summary only:', data.data.summary);
  });
```

### Database Tables Used

- `transaction_executions` - Main execution log
- `projects` - Project names for running transactions

### Security

- Non-admin users can only view their own company's data
- Admin users can view any company's data by specifying `company_id`
- Session must be valid with `authenticated=true` and valid `userId`

### Performance Notes

- Running transactions query is limited to 50 most recent
- Summary statistics are calculated in real-time
- Consider caching for high-traffic scenarios

### Compilation

To compile this servlet:

```bash
cd web_portal/tomcat/webapps/iw-business-daemon/WEB-INF
javac -cp "lib/*:classes:../../../lib/servlet-api.jar" \
  -d classes \
  src/com/interweave/monitoring/api/DashboardApiServlet.java
```

Or use the provided compile script:
```bash
./compile_monitoring_api.sh
```

### Testing

Before deploying, verify:
1. Migration script `005_monitoring_schema.sql` has been run
2. `transaction_executions` table exists and has data
3. Session authentication is configured
4. User has valid `company_id` in session

Test endpoint manually:
```bash
# After logging in, get session cookie
curl -b cookies.txt 'http://localhost:8080/iw-business-daemon/api/monitoring/dashboard'
```
