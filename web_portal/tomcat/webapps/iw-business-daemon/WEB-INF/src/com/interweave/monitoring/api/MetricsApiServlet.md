# MetricsApiServlet - API Documentation

## Overview

The `MetricsApiServlet` provides time-series performance metrics for the monitoring dashboard. It aggregates transaction execution data over configurable time periods with different granularities, returning data in a Chart.js-compatible format for easy visualization.

## Endpoint

```
GET /api/monitoring/metrics
```

## Query Parameters

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `granularity` | string | No | `daily` | Time granularity: `hourly`, `daily`, or `weekly` |
| `period` | string | No | `7d` | Time period: `24h`, `7d`, `30d`, or `90d` |
| `flow_name` | string | No | - | Filter by specific flow name (supports partial match) |
| `project_id` | integer | No | - | Filter by project ID |
| `company_id` | integer | No | User's company | Filter by company (admin only) |

## Response Format

The servlet returns data in a Chart.js-compatible format with four time-series datasets:

```json
{
  "success": true,
  "data": {
    "labels": ["2026-01-08", "2026-01-09", ...],
    "datasets": [
      {
        "label": "Successful Executions",
        "data": [45, 52, 48, ...]
      },
      {
        "label": "Failed Executions",
        "data": [2, 1, 3, ...]
      },
      {
        "label": "Average Duration (ms)",
        "data": [1523, 1456, 1589, ...]
      },
      {
        "label": "Records Processed",
        "data": [1250, 1340, 1180, ...]
      }
    ],
    "summary": {
      "total_executions": 150,
      "success_rate": 97.3,
      "avg_duration_ms": 1523,
      "total_records": 3770
    }
  }
}
```

## Example Usage

### Daily metrics for the last 7 days
```
GET /api/monitoring/metrics?granularity=daily&period=7d
```

### Hourly metrics for the last 24 hours
```
GET /api/monitoring/metrics?granularity=hourly&period=24h
```

### Weekly metrics for a specific flow
```
GET /api/monitoring/metrics?granularity=weekly&period=90d&flow_name=Customer+Order+Sync
```

### Daily metrics for a specific project
```
GET /api/monitoring/metrics?granularity=daily&period=30d&project_id=5
```

## Features

### Time Granularity Options

- **Hourly**: Data points are aggregated by hour
  - Best for: Last 24 hours analysis
  - Label format: `yyyy-MM-dd HH:00`

- **Daily**: Data points are aggregated by day
  - Best for: Last 7-30 days analysis
  - Label format: `yyyy-MM-dd`

- **Weekly**: Data points are aggregated by week
  - Best for: Last 30-90 days analysis
  - Label format: `yyyy-MM-dd` (start of week)

### Metrics Provided

1. **Successful Executions**: Count of successfully completed transactions per time period
2. **Failed Executions**: Count of failed transactions per time period
3. **Average Duration**: Mean execution time in milliseconds for successful transactions
4. **Records Processed**: Total number of records processed per time period

### Summary Statistics

The response includes a `summary` object with:
- `total_executions`: Total number of transactions in the period
- `success_rate`: Success rate as a percentage (0-100)
- `avg_duration_ms`: Overall average execution duration in milliseconds
- `total_records`: Total records processed across all transactions

## Security

- Requires authenticated session (validated by `MonitoringApiServlet` base class)
- Multi-tenant filtering by `company_id`
- Non-admin users can only view their own company's data
- Admin users can view any company's data by specifying `company_id` parameter

## Error Responses

### 401 Unauthorized
Session expired or not authenticated.

### 403 Forbidden
Non-admin user attempting to view another company's data.

### 400 Bad Request
Invalid parameters (e.g., invalid granularity or period).

### 500 Internal Server Error
Database error or unexpected system error.

## Database Schema

The servlet queries the `transaction_executions` table directly for real-time accuracy:

```sql
SELECT
  DATE_FORMAT(started_at, '%Y-%m-%d') AS time_bucket,
  COUNT(CASE WHEN status = 'success' THEN 1 END) AS success_count,
  COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed_count,
  AVG(CASE WHEN status = 'success' THEN duration_ms END) AS avg_duration,
  SUM(records_processed) AS total_records
FROM transaction_executions
WHERE started_at >= ?
  AND company_id = ?
GROUP BY time_bucket
ORDER BY time_bucket ASC
```

## Implementation Details

### Time Bucket Calculation

The servlet uses MySQL's `DATE_FORMAT` function to bucket transactions into time periods:
- **Hourly**: `DATE_FORMAT(started_at, '%Y-%m-%d %H:00:00')`
- **Daily**: `DATE_FORMAT(started_at, '%Y-%m-%d')`
- **Weekly**: `DATE_FORMAT(DATE_SUB(started_at, INTERVAL WEEKDAY(started_at) DAY), '%Y-%m-%d')`

### Data Point Initialization

All time periods are initialized with zero values, then filled with actual data from the database. This ensures consistent data points even when no transactions occurred in certain periods.

### Performance Considerations

- Queries are optimized with compound indexes on `(company_id, status, started_at)`
- Uses `CASE` expressions to compute multiple aggregations in a single query
- Limits result set using `WHERE started_at >= ?` to avoid full table scans

## Chart.js Integration

The response format is designed to be directly compatible with Chart.js:

```javascript
fetch('/api/monitoring/metrics?granularity=daily&period=7d')
  .then(response => response.json())
  .then(result => {
    const ctx = document.getElementById('metricsChart').getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: result.data.labels,
        datasets: result.data.datasets
      },
      options: {
        responsive: true,
        // ... additional chart options
      }
    });
  });
```

## Version History

- **1.0** (2026-01-09): Initial implementation
  - Time-series metrics with configurable granularity
  - Support for hourly, daily, and weekly aggregation
  - Chart.js-compatible response format
  - Multi-tenant security
  - Filtering by flow name and project
