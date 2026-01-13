# InterWeave Integration Monitoring Dashboard - User Guide

**Version:** 1.0
**Last Updated:** January 2026

---

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [Dashboard Overview](#dashboard-overview)
4. [Understanding Metrics and Charts](#understanding-metrics-and-charts)
5. [Transaction History](#transaction-history)
6. [Transaction Details](#transaction-details)
7. [Configuring Email Alerts](#configuring-email-alerts)
8. [Setting Up Webhook Integrations](#setting-up-webhook-integrations)
9. [Alert History](#alert-history)
10. [Troubleshooting](#troubleshooting)
11. [Best Practices](#best-practices)
12. [FAQ](#faq)

---

## Introduction

The InterWeave Integration Monitoring Dashboard provides real-time visibility into your integration flows, transaction execution, and system health. This modern web-based dashboard replaces the legacy monitoring interface and works in all modern browsers without requiring Java applets.

### Key Features

- **Real-Time Status Monitoring**: View currently running integrations and their progress
- **Performance Metrics**: Track success rates, execution times, and throughput trends
- **Transaction History**: Search and filter past transactions with detailed drill-down
- **Intelligent Alerting**: Get notified immediately when integrations fail via email or webhooks
- **Multi-Tenant Support**: Secure access to your company's data only
- **Responsive Design**: Works on desktop, tablet, and mobile devices

### Who Should Use This Guide

- **Operations Managers**: Monitor system health and track success rates
- **Support Engineers**: Investigate transaction failures and data issues
- **Developers**: Set up alerts and analyze integration performance
- **Business Users**: View integration status and verify data flow

---

## Getting Started

### Accessing the Dashboard

1. **Log in** to your InterWeave portal with your username and password
2. Look for the **"Monitoring Dashboard"** link in the navigation area (usually between your user info and logout link)
3. Click the link to open the monitoring dashboard

### Dashboard Permissions

- **All Users**: Can view monitoring data for their company
- **Administrators**: Can view data across all companies and manage global alerts
- **Session Required**: You must be logged in; the dashboard will redirect to login if your session expires

### Browser Compatibility

The dashboard works best in modern browsers:
- Chrome 90+
- Firefox 88+
- Microsoft Edge 90+
- Safari 14+
- Mobile browsers (iOS Safari, Chrome Mobile)

---

## Dashboard Overview

The main dashboard page displays four key sections:

### 1. Status Cards (Top Section)

Four prominent cards show at-a-glance metrics:

- **Active Flows**: Number of integrations currently running
- **Success Rate (24h)**: Percentage of successful executions in the last 24 hours
- **Errors Today**: Count of failed transactions today
- **Avg Duration**: Average execution time for recent transactions

**What to Look For:**
- Green indicators = healthy
- Red indicators = attention needed
- Yellow/orange = warning state

### 2. Performance Charts (Middle Section)

Two interactive charts visualize trends:

- **Success/Failure Trends**: Line chart showing successful vs. failed executions over time
- **Execution Time Trends**: Line chart showing average duration in milliseconds

**Chart Controls:**
- Use the dropdown to select time range: Last 24 hours, 7 days, or 30 days
- Hover over data points to see exact values and timestamps
- Charts automatically refresh every 10 seconds

### 3. Running Flows (Middle Section)

Shows all currently executing transactions with:
- Flow name and type
- Start time and current duration
- Records processed so far
- Progress status

### 4. Transaction History (Bottom Section)

A searchable table of recent transaction executions with:
- Flow name
- Status (Success, Failed, Running, Cancelled)
- Start time
- Duration
- Records processed/failed
- Action button to view details

**Auto-Refresh:** The dashboard automatically refreshes every 10 seconds. Use the "Refresh" button to manually update data immediately.

---

## Understanding Metrics and Charts

### Success Rate Interpretation

**Success Rate Formula:**
```
Success Rate = (Successful Executions / Total Executions) × 100
```

**What's Normal:**
- **95-100%**: Excellent - integrations are running smoothly
- **90-95%**: Good - minor issues, monitor trends
- **80-90%**: Concerning - investigate failures
- **Below 80%**: Critical - immediate attention required

**Tip:** Check both 24-hour and 7-day success rates. A drop in the 24-hour rate might indicate a recent issue, while a low 7-day rate suggests systemic problems.

### Execution Time Metrics

**Average Duration** shows how long transactions typically take:
- **Seconds to Minutes**: Normal for most integrations
- **Sudden Increases**: May indicate API slowdowns or network issues
- **Gradual Increases**: Could signal data volume growth or performance degradation

**When to Act:**
- Duration doubles compared to baseline → investigate
- Timeouts occurring → check external API availability
- Consistent slowness → review data volumes and optimize queries

### Chart Time Granularity

The system automatically selects the best granularity:
- **Last 24 hours**: Hourly data points (24 points)
- **Last 7 days**: Daily data points (7 points)
- **Last 30 days**: Daily data points (30 points)

### Records Processed

Tracks how many data records were successfully processed:
- **High Volume**: Normal for batch integrations
- **Zero Records**: May indicate no data available (not necessarily an error)
- **Records Failed**: Count of records that failed validation or processing

---

## Transaction History

### Viewing Transaction History

The transaction history table shows all recent executions with powerful filtering and search capabilities.

### Using Filters

**Status Filter:**
- Select from: All, Success, Failed, Running, Cancelled, Timeout
- Use to quickly find problematic transactions

**Date Range Filter:**
- **Start Date**: Show transactions from this date forward
- **End Date**: Show transactions up to this date
- Leave blank to show all dates
- Format: YYYY-MM-DD or use the date picker

**Search Box:**
- Searches across execution IDs and error messages
- Type to search, results update automatically (500ms delay)
- Clear the box to show all results

**Clear Filters Button:**
- Resets all filters to their default state
- Shows all recent transactions

### Sorting

Click any column header to sort:
- **Flow Name**: Alphabetical order
- **Status**: Groups by status type
- **Started At**: Chronological order (most recent first by default)
- **Duration**: Fastest to slowest or vice versa
- **Records Processed**: Highest to lowest volume

Click the same header again to reverse sort order.

### Pagination

Control how many results you see:
- **Page Size**: Select 10, 20, 50, or 100 rows per page
- **Navigation**: Use First, Previous, Next, Last buttons
- **Page Info**: Shows current page and total results

**Performance Tip:** Use smaller page sizes (10-20) for faster loading on slower connections.

### Opening Transaction Details

Click any row in the transaction history table to view detailed information about that execution (see next section).

---

## Transaction Details

### Accessing Details

From the transaction history table, click any row to open the detailed view for that transaction execution.

### Detail View Sections

#### 1. Transaction Overview

Displays complete metadata:
- **Execution ID**: Unique identifier for this transaction
- **Flow Name**: The integration flow that ran
- **Status**: Current state with color-coded badge
- **Started At / Completed At**: Execution timestamps
- **Duration**: How long the transaction took
- **Company**: Your company name
- **Project**: Associated project
- **Triggered By**: User or system that initiated the flow
- **Server**: Hostname where the transaction executed

#### 2. Records Processing Statistics

Three cards show processing metrics:
- **Records Processed** (green checkmark): Successfully processed records
- **Records Failed** (red X): Records that failed
- **Records Skipped** (yellow dash): Records skipped (if applicable)

#### 3. Error Information (Failed Transactions Only)

For failed transactions, this section displays:
- **Error Code**: Categorizes the error type
- **Error Message**: Detailed description of what went wrong
- **Stack Trace**: Technical details (collapsible) - useful for developers

**Reading Error Messages:**
- Authentication errors → Check credentials
- Connection errors → Verify network/API availability
- Validation errors → Review data format
- Timeout errors → Check API performance or increase timeout

#### 4. Transaction Payloads

Shows request/response data exchanged during the transaction:
- **Request Payload**: Data sent to destination system
- **Response Payload**: Data received back
- **Intermediate Payloads**: Any transformation steps

**Payload Features:**
- **Syntax Highlighting**: JSON/XML displayed with color coding
- **Collapsible Content**: Expand/collapse to manage screen space
- **Copy to Clipboard**: Click "Copy" button to copy entire payload
- **Size Display**: Shows payload size in bytes/KB/MB
- **Captured Timestamp**: When the payload was recorded

**Use Cases:**
- **Debugging**: Compare request vs. response to find mismatches
- **Data Validation**: Verify data format and values
- **Support**: Copy payloads to share with technical support

### Navigation

- **Back to Dashboard**: Return to main monitoring dashboard
- Use browser back button or click "Monitoring Dashboard" in header

---

## Configuring Email Alerts

Email alerts notify you when integrations fail, allowing quick response to issues.

### Accessing Alert Configuration

1. From the monitoring dashboard, click **"Alert Configuration"** in the footer
2. Or navigate directly to the Alert Configuration page via the link

### Creating an Email Alert Rule

**Step 1: Click "New Alert Rule"**

**Step 2: Configure Alert Settings**

- **Alert Name**: Descriptive name (e.g., "Salesforce Sync Failures")
- **Flow Name**:
  - Leave blank to monitor ALL flows
  - Enter specific flow name to monitor only that flow
- **Project ID**: (Optional) Filter to specific project
- **Notification Type**: Select "Email" or "Email and Webhook"

**Step 3: Set Trigger Conditions**

- **Alert on Failure**: Check this box to enable failure notifications
- **Failure Threshold**: Number of failures that trigger an alert (default: 1)
  - Set to 1 for immediate notification on first failure
  - Set higher (e.g., 3) to avoid alerts for transient issues
- **Threshold Time Window**: Time period in minutes (default: 15)
  - Example: "3 failures in 15 minutes" triggers alert

**Step 4: Configure Email Recipients**

- **Email Addresses**: Enter comma-separated email addresses
  - Example: `ops@company.com, admin@company.com`
  - Validation ensures proper email format

**Step 5: Set Rate Limiting**

- **Cooldown Period**: Minutes between repeat alerts (default: 60)
  - Prevents alert spam for repeated failures
  - Example: 60 = maximum one alert per hour for same flow
- **Max Alerts Per Day**: Daily limit (default: 10)
  - Caps total alerts to prevent email overload
  - Resets at midnight

**Step 6: Save the Rule**

Click **"Create Alert"** to save your configuration.

### Email Alert Content

When a failure occurs, recipients receive an HTML email containing:
- **Subject**: "Integration Alert: [Flow Name] Failed"
- **Flow Name**: Which integration failed
- **Execution ID**: Transaction identifier for tracking
- **Error Code**: Category of error
- **Error Message**: Detailed failure description
- **Timestamp**: When the failure occurred
- **Link**: Direct link to transaction detail view in dashboard

### Managing Existing Alert Rules

#### Enable/Disable Alerts

Click the toggle switch next to any alert rule to:
- **Enable** (green): Alert is active and monitoring
- **Disable** (gray): Alert is paused, no notifications sent

**Tip:** Disable alerts during planned maintenance to avoid false alarms.

#### Edit Alert Rules

1. Click **"Edit"** button next to the alert rule
2. Modify any settings
3. Click **"Update Alert"** to save changes

#### Delete Alert Rules

1. Click **"Delete"** button next to the alert rule
2. Confirm deletion in the popup dialog
3. Rule and associated history are removed

### Alert Rule Best Practices

**For Critical Flows:**
- Threshold: 1 failure
- Cooldown: 30-60 minutes
- Multiple recipients for redundancy

**For Less Critical Flows:**
- Threshold: 3-5 failures
- Cooldown: 120 minutes
- Fewer recipients

**For High-Volume Flows:**
- Use threshold (e.g., 5 failures in 15 minutes) to filter transient issues
- Set max alerts per day to prevent overload

---

## Setting Up Webhook Integrations

Webhooks allow you to integrate alerts with external systems like Slack, PagerDuty, Microsoft Teams, or custom applications.

### What are Webhooks?

Webhooks are HTTP callbacks that send real-time notifications to external URLs when failures occur. They enable integration with:
- **Slack**: Post alerts to channels
- **PagerDuty**: Create incidents
- **Microsoft Teams**: Send messages to teams
- **Custom Systems**: Your own alerting platform

### Creating a Webhook Endpoint

**Step 1: Click "New Webhook Endpoint"** (in Alert Configuration page)

**Step 2: Basic Configuration**

- **Endpoint Name**: Descriptive name (e.g., "Operations Slack Channel")
- **URL**: Full webhook URL
  - Slack: `https://hooks.slack.com/services/YOUR/WEBHOOK/URL`
  - Teams: `https://outlook.office.com/webhook/YOUR/WEBHOOK/URL`
  - PagerDuty: `https://events.pagerduty.com/v2/enqueue`
  - Custom: Your application endpoint
- **HTTP Method**: Usually POST (some services use PUT)

**Step 3: Authentication (if required)**

Select authentication type:

**None**: No authentication needed (URL contains secret)
- Common for Slack, Teams

**Basic Authentication**: Username and password
```
Username: your-username
Password: your-password
```

**Bearer Token**: Token-based auth
```
Token: your-bearer-token
```

**Custom Header**: Custom authentication header
```
Header Name: X-API-Key
Header Value: your-api-key
```

**Step 4: Advanced Configuration**

- **Timeout**: Seconds to wait for response (default: 30)
  - Increase for slow endpoints
  - Decrease for faster failure detection
- **Max Retries**: Retry attempts on failure (default: 3)
  - 0 = no retries
  - 3 = try up to 3 times with exponential backoff
- **Custom Headers**: Additional headers as JSON
  ```json
  {
    "Content-Type": "application/json",
    "X-Custom-Header": "value"
  }
  ```

**Step 5: Save Endpoint**

Click **"Create Webhook"** to save configuration.

### Webhook Payload Format

When an alert triggers, the system sends JSON payload:

```json
{
  "event_type": "transaction_failure",
  "flow_name": "Salesforce to QuickBooks Sync",
  "flow_id": 123,
  "execution_id": "exec_abc123",
  "status": "failed",
  "error_code": "AUTH_FAILED",
  "error_message": "Authentication failed: Invalid API key",
  "timestamp": "2026-01-09T15:30:00Z",
  "company_id": 1,
  "project_id": 5,
  "records_processed": 0,
  "records_failed": 10,
  "duration_ms": 1500
}
```

### Platform-Specific Setup

#### Slack Webhook Setup

1. In Slack, go to **Your Apps** → **Incoming Webhooks**
2. Click **"Add New Webhook to Workspace"**
3. Select channel and authorize
4. Copy the webhook URL
5. In InterWeave:
   - Paste URL in "URL" field
   - Authentication: None
   - HTTP Method: POST

**Slack Message Customization:** Slack will format the JSON automatically. For custom formatting, use a middleware service.

#### PagerDuty Integration

1. In PagerDuty, go to **Services** → **Your Service** → **Integrations**
2. Add **"Events API V2"** integration
3. Copy the **Integration Key**
4. In InterWeave:
   - URL: `https://events.pagerduty.com/v2/enqueue`
   - Authentication: Custom Header
   - Header Name: `Authorization`
   - Header Value: `Token token=YOUR_INTEGRATION_KEY`
   - HTTP Method: POST

#### Microsoft Teams Webhook

1. In Teams, go to your channel → **Connectors**
2. Configure **"Incoming Webhook"**
3. Name it and copy the webhook URL
4. In InterWeave:
   - Paste URL in "URL" field
   - Authentication: None
   - HTTP Method: POST

### Testing Webhooks

After creating a webhook endpoint:

1. Click the **"Test"** button next to the webhook
2. System sends a test payload to verify connectivity
3. Check your external system (Slack channel, Teams, etc.) for the test message
4. If test fails:
   - Verify URL is correct
   - Check authentication credentials
   - Review error message in InterWeave

### Creating Alert Rules with Webhooks

After setting up webhook endpoints:

1. Create a new alert rule (see previous section)
2. Set **Notification Type** to "Webhook" or "Email and Webhook"
3. In the **Webhook Endpoints** section, check the webhooks to use
4. You can select multiple webhooks for one alert rule
5. Save the alert rule

### Managing Webhook Endpoints

#### Health Status

Each webhook displays health indicators:
- **Healthy** (green): Recent successful deliveries
- **Warning** (yellow): Some failures but still active
- **Failed** (red): Multiple consecutive failures (auto-disabled after 10 failures)

**Health Metrics:**
- Last Success: Timestamp of last successful delivery
- Last Failure: Timestamp of last failed attempt
- Consecutive Failures: Count of recent failures

#### Enable/Disable Webhooks

Toggle webhooks on/off without deleting configuration.

#### Edit/Delete Webhooks

- **Edit**: Update URL, authentication, or settings
- **Delete**: Remove webhook (confirm in dialog)

### Webhook Troubleshooting

**Webhook Not Receiving Alerts:**
1. Check webhook is enabled
2. Verify alert rule includes this webhook
3. Test the webhook endpoint
4. Check external system for blocks/filters

**Test Passes But Real Alerts Fail:**
1. Check alert rule trigger conditions
2. Verify failure actually occurred
3. Review alert history for delivery status

**Webhooks Disabled Automatically:**
- After 10 consecutive failures, webhooks auto-disable
- Fix the underlying issue (URL, auth, etc.)
- Test the webhook
- Re-enable if test succeeds

---

## Alert History

The Alert History section shows all alerts sent by the system.

### Viewing Alert History

In the Alert Configuration page, scroll to the **"Alert History"** section at the bottom.

### History Table Columns

- **Sent At**: When the alert was triggered
- **Rule Name**: Which alert rule triggered
- **Type**: Email or Webhook
- **Recipient**: Email address or webhook name
- **Status**:
  - **Sent** (green): Successfully delivered
  - **Failed** (red): Delivery failed
  - **Pending** (blue): Waiting to be sent
  - **Retrying** (yellow): Retry scheduled
- **Retry Count**: Number of retry attempts

### Filtering History

- **Status Filter**: Show only sent, failed, pending, or retrying alerts
- **Type Filter**: Show only email or webhook alerts
- **Limit**: Number of records to display (50, 100, 200)

### Understanding Alert Status

**Sent**: Email delivered via SMTP or webhook returned 2xx HTTP status

**Failed**:
- Email: SMTP error (invalid address, server unreachable)
- Webhook: HTTP error (4xx, 5xx) or timeout

**Pending**: Alert created but not yet processed (typically sent within 30 seconds)

**Retrying**: Previous attempt failed, retry scheduled
- Retry schedule: 1 min, 5 min, 15 min
- After 3 failed attempts, status becomes "Failed"

### Investigating Failed Alerts

If you see failed alerts:

1. **For Emails:**
   - Verify SMTP configuration in `monitoring.properties`
   - Check email addresses are valid
   - Review spam/junk folders
   - Check SMTP server logs

2. **For Webhooks:**
   - Click "Test" button on webhook endpoint
   - Verify URL is accessible
   - Check authentication credentials
   - Review external system logs

---

## Troubleshooting

### Dashboard Not Loading

**Symptoms:** Blank page, error message, or redirect to login

**Solutions:**
1. Verify you're logged in (session may have expired)
2. Clear browser cache and refresh
3. Try a different browser
4. Check JavaScript is enabled
5. Verify network connectivity

### Charts Not Displaying

**Symptoms:** Empty chart areas or "Loading..." message persists

**Solutions:**
1. Check browser console for JavaScript errors (F12 → Console)
2. Verify Chart.js library loaded (network tab)
3. Ensure API endpoints return data:
   - Open browser developer tools
   - Check Network tab for `/api/monitoring/metrics` response
4. Try manual refresh button
5. Check date range - may have no data for selected period

### No Transaction Data Showing

**Symptoms:** Empty transaction history table

**Possible Causes:**
1. **No Transactions**: No integrations have run recently
   - Solution: Wait for scheduled flows to execute or run manually
2. **Filters Too Restrictive**: Active filters exclude all data
   - Solution: Click "Clear Filters" button
3. **Date Range**: Selected date range has no data
   - Solution: Adjust start/end dates
4. **Database Issue**: Transaction logging not configured
   - Solution: Contact administrator to verify schema migration

### Alerts Not Sending

**Symptoms:** Failures occur but no email/webhook received

**Email Alerts:**
1. Check alert rule is **enabled**
2. Verify **email addresses** are correct
3. Verify alert rule **matches the flow** (flow name filter)
4. Check **threshold conditions** are met (e.g., needs 3 failures)
5. Verify **cooldown period** hasn't suppressed alerts
6. Check **max alerts per day** limit not reached
7. Review **Alert History** for delivery status
8. Verify **SMTP configuration** in `monitoring.properties`
9. Check **JavaMail JARs** installed (required for email)

**Webhook Alerts:**
1. Check webhook endpoint is **enabled**
2. Verify alert rule includes this webhook (checkboxes)
3. Check webhook **health status** (not auto-disabled)
4. Test webhook endpoint - click "Test" button
5. Review webhook **URL and authentication**
6. Check **external system** receiving webhooks
7. Review **Alert History** for delivery errors

### Slow Dashboard Performance

**Symptoms:** Pages load slowly, charts lag, auto-refresh stutters

**Solutions:**
1. **Reduce Auto-Refresh Frequency**: Disable auto-refresh if not needed
2. **Smaller Page Size**: Use 10-20 rows instead of 100
3. **Limit Date Range**: Don't query 30 days if you only need recent data
4. **Clear Browser Cache**: Old cached data may conflict
5. **Close Other Tabs**: Free up browser memory
6. **Check Network**: Slow connection affects API calls
7. **Database Cleanup**: Ask administrator to run cleanup (old data retention)

### Session Timeout Issues

**Symptoms:** Redirected to login while using dashboard

**Explanation:** Your session expired due to inactivity (typically 30-60 minutes)

**Solutions:**
1. Log back in - dashboard will resume where you left off
2. For long monitoring sessions:
   - Periodically interact with the page (click refresh)
   - Open another portal page in different tab to keep session alive
3. Ask administrator about extending session timeout

### Permission Errors

**Symptoms:** "Access denied" or "Unauthorized" errors

**Possible Causes:**
1. **Session Expired**: Log in again
2. **Insufficient Permissions**: Contact administrator
3. **Company Mismatch**: Trying to view another company's data (admins only)

**Solutions:**
1. Verify you're logged in
2. Refresh the page
3. Contact administrator to verify user permissions

### Metrics Aggregation Not Running

**Symptoms:** Charts show no data or stale data despite transactions running

**Diagnosis:**
1. Check if MetricsAggregator service is running
2. Review application logs for aggregation errors
3. Verify `metrics_aggregation_enabled` setting in database

**Solutions:**
1. Contact administrator to check service status
2. Administrator can trigger manual aggregation
3. Verify database schema migration completed successfully

---

## Best Practices

### Monitoring Strategy

**Daily Monitoring:**
- Check dashboard at start of day
- Review success rate and errors today
- Investigate any red indicators immediately

**Weekly Reviews:**
- Analyze 7-day trends in charts
- Look for patterns (e.g., failures on specific days)
- Review alert history for false positives

**Monthly Analysis:**
- Review 30-day execution time trends
- Identify performance degradation
- Optimize alert rules based on patterns

### Alert Configuration Strategy

**Start Conservative:**
- Begin with immediate alerts (threshold = 1)
- Monitor for false positives
- Adjust thresholds based on actual failure patterns

**Use Tiered Alerts:**
- **Critical Flows**: Immediate alert (threshold 1, short cooldown)
- **Important Flows**: Threshold 3 in 15 minutes
- **Batch Jobs**: Threshold 5 in 30 minutes (tolerate retries)

**Prevent Alert Fatigue:**
- Set appropriate cooldown periods (60+ minutes)
- Use max alerts per day limits
- Disable alerts during planned maintenance
- Review and remove unused alert rules

### Investigation Workflow

When an alert arrives:

1. **Check Dashboard**: View real-time status
2. **Find Transaction**: Use transaction history filters
3. **Review Details**: Open transaction detail view
4. **Analyze Error**: Read error message and stack trace
5. **Check Payloads**: Review request/response data
6. **Identify Root Cause**: Common causes below
7. **Take Action**: Fix configuration, contact vendor, etc.
8. **Verify Fix**: Monitor subsequent executions

### Common Failure Patterns

**Authentication Failures:**
- Expired API credentials
- Changed passwords
- Revoked OAuth tokens
- **Action**: Update credentials in integration configuration

**Connection Timeouts:**
- External API slow or down
- Network issues
- Firewall blocks
- **Action**: Check API status page, verify network, increase timeout

**Data Validation Errors:**
- Required fields missing
- Invalid data format
- Business rule violations
- **Action**: Review source data, fix data quality issues

**Rate Limiting:**
- Exceeded API rate limits
- Too many concurrent requests
- **Action**: Reduce frequency, implement queuing, contact vendor

### Data Retention

The system automatically cleans up old data based on retention policies:
- **Transaction Executions**: 90 days (default)
- **Transaction Metrics**: 365 days (default)
- **Alert History**: 180 days (default)

**Note:** Administrators can adjust retention policies in the database `settings` table.

### Performance Optimization

**For Large Transaction Volumes:**
- Use metrics charts instead of querying all raw transactions
- Filter transaction history to specific date ranges
- Search for specific execution IDs rather than browsing
- Export data for offline analysis if needed

**For Distributed Teams:**
- Create flow-specific alert rules for different teams
- Use project-based filtering
- Set up dedicated webhook channels per team (e.g., #sales-integrations, #finance-integrations)

---

## FAQ

### General Questions

**Q: Do I need to install anything to use the dashboard?**
A: No, it's a web application that works in any modern browser. No plugins, Java, or downloads required.

**Q: Can I access the dashboard on my mobile phone?**
A: Yes, the dashboard has a responsive design that works on tablets and smartphones.

**Q: How often does the dashboard refresh?**
A: Automatically every 10 seconds. You can also click the "Refresh" button for immediate updates.

**Q: What happened to the old BDMonitor.jsp page?**
A: It has been replaced by this modern dashboard. The old page used Java applets which are no longer supported by browsers.

### Metrics Questions

**Q: Why does my success rate show 0% or N/A?**
A: This means no transactions have executed in the selected time period (24h or 7d). Wait for scheduled flows to run or check your filters.

**Q: What does "Records Processed" mean?**
A: It's the number of individual data records (e.g., customers, invoices, orders) processed within a transaction execution.

**Q: Why don't I see metrics for today?**
A: Hourly metrics exclude the current hour (incomplete data). Daily metrics exclude today. Use the transaction history table for real-time data.

**Q: Can I export chart data to Excel?**
A: Not directly from the UI. Contact your administrator about database query access for custom reporting.

### Transaction Questions

**Q: How far back can I search transaction history?**
A: By default, 90 days. Older data is archived. Contact your administrator if you need older data.

**Q: Can I re-run a failed transaction?**
A: This feature is planned for a future release. Currently, you need to trigger the flow again through the integration manager.

**Q: Why don't I see request/response payloads for some transactions?**
A: Payload logging may be disabled for specific flows to save space, or the transaction may have failed before payload capture.

**Q: What's the difference between "Failed" and "Timeout" status?**
A: "Failed" means the transaction executed but encountered an error. "Timeout" means the transaction exceeded the maximum execution time limit.

### Alert Questions

**Q: How quickly will I receive an email alert?**
A: Email alerts are sent within 30 seconds of the failure (assuming SMTP is configured correctly).

**Q: Can I receive alerts via SMS?**
A: Not directly, but you can integrate with services like Twilio via webhooks to send SMS.

**Q: Why am I not receiving alerts even though failures occurred?**
A: Check: (1) Alert rule is enabled, (2) Flow name matches, (3) Threshold is met, (4) Not in cooldown period, (5) Max alerts per day not exceeded, (6) Email address is correct, (7) Alert History shows delivery status.

**Q: Can I set up alerts for successful transactions?**
A: Currently, only failure alerts are supported. Success notifications are planned for a future release.

**Q: How do I stop receiving alerts temporarily?**
A: Disable the alert rule using the toggle switch. Re-enable when you want alerts to resume.

**Q: Can different team members receive alerts for different flows?**
A: Yes, create separate alert rules with flow name filters and different recipient lists.

### Webhook Questions

**Q: What if my webhook endpoint requires custom payload format?**
A: Consider using a middleware service (e.g., Zapier, n8n) to transform the InterWeave payload to your required format.

**Q: Can I send webhooks to multiple Slack channels?**
A: Yes, create separate webhook endpoints for each Slack incoming webhook URL, then select all of them in your alert rule.

**Q: Why was my webhook automatically disabled?**
A: After 10 consecutive delivery failures, webhooks auto-disable to prevent resource waste. Fix the endpoint issue and re-enable.

**Q: Can I see the webhook payload that was sent?**
A: The payload format is documented in this guide (see "Webhook Payload Format"). For debugging, check your webhook endpoint logs.

### Security Questions

**Q: Can other companies see my transaction data?**
A: No, the system enforces multi-tenant isolation. You can only see your company's data (unless you're an administrator).

**Q: Are webhook URLs and email passwords stored securely?**
A: Webhook secrets and SMTP passwords are stored in the database and configuration files. Ensure proper file permissions and database access controls.

**Q: What happens if I forget to log out?**
A: Your session will expire automatically after the configured timeout period (typically 30-60 minutes of inactivity).

**Q: Can I limit which users can configure alerts?**
A: Currently, all authenticated users can configure alerts for their company. Role-based access control is planned for a future release.

---

## Additional Resources

### Documentation Files

- **Database Schema**: `_internal/sql/MONITORING_MIGRATION_README.md`
- **API Endpoints**: `WEB-INF/src/com/interweave/monitoring/api/API_ENDPOINTS.md`
- **Email Configuration**: `WEB-INF/src/com/interweave/monitoring/service/README_EMAIL_NOTIFICATION.md`
- **Webhook Configuration**: `WEB-INF/src/com/interweave/monitoring/service/README_WEBHOOK_NOTIFICATION.md`
- **Manual Testing Guide**: `.auto-claude/specs/005-real-time-integration-monitoring-dashboard/MANUAL_TESTING_GUIDE.md`

### Administrator Resources

- **Schema Migration**: `_internal/sql/005_monitoring_schema.sql`
- **Configuration File**: `WEB-INF/monitoring.properties`
- **Test Data Generator**: `_internal/sql/monitoring_test_data.sql`

### Support

For technical support or questions:
1. Contact your system administrator
2. Reference this documentation
3. Check application logs for technical details
4. Provide execution IDs when reporting issues

---

## Appendix: Glossary

**Alert Rule**: Configuration that defines when and how to send notifications

**API Endpoint**: Server URL that provides data to the dashboard

**Auto-Refresh**: Automatic periodic update of dashboard data

**Cooldown Period**: Minimum time between repeated alerts for the same flow

**Execution**: Single run of an integration flow

**Flow**: Integration process that exchanges data between systems

**Granularity**: Time interval for chart data points (hourly, daily)

**Metrics**: Aggregated statistics about transaction performance

**Multi-Tenant**: System that isolates data between different companies

**Payload**: Request or response data exchanged during a transaction

**Session**: Your authenticated login period

**Threshold**: Minimum number of failures required to trigger an alert

**Transaction**: Another term for an integration execution

**Webhook**: HTTP callback for real-time notifications to external systems

---

**End of Guide**

*For the latest updates to this documentation, check the docs folder in your InterWeave installation.*
