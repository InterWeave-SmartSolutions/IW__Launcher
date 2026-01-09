<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.error.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Connection Errors - InterWeave IDE Portal</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f5f5f5;
    }
    .header-banner {
      width: 100%;
      border-bottom: 2px solid #ddd;
    }
    .container {
      max-width: 1000px;
      margin: 30px auto;
      padding: 0 20px;
    }
    .content-box {
      background-color: #ffffff;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 30px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .breadcrumb {
      font-size: 12px;
      color: #666;
      margin-bottom: 20px;
    }
    .breadcrumb a {
      color: #0066cc;
      text-decoration: none;
    }
    .breadcrumb a:hover {
      text-decoration: underline;
    }
    h1 {
      color: #333;
      font-size: 28px;
      margin-top: 0;
      border-bottom: 3px solid #0066cc;
      padding-bottom: 10px;
      display: flex;
      align-items: center;
    }
    .page-icon {
      font-size: 36px;
      margin-right: 15px;
    }
    .intro {
      font-size: 14px;
      line-height: 1.6;
      color: #666;
      margin-bottom: 30px;
      padding: 15px;
      background-color: #f9f9f9;
      border-left: 4px solid #0066cc;
      border-radius: 4px;
    }
    .error-entry {
      margin: 30px 0;
      padding: 25px;
      background-color: #ffffff;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      box-shadow: 0 1px 4px rgba(0,0,0,0.05);
    }
    .error-entry:target {
      border-color: #0066cc;
      background-color: #f0f8ff;
      box-shadow: 0 2px 8px rgba(0,102,204,0.2);
    }
    .error-header {
      display: flex;
      align-items: center;
      margin-bottom: 15px;
      padding-bottom: 12px;
      border-bottom: 2px solid #f0f0f0;
    }
    .error-code {
      font-family: 'Courier New', monospace;
      font-weight: bold;
      color: #cc0000;
      font-size: 20px;
      margin-right: 15px;
    }
    .error-message {
      font-size: 16px;
      color: #333;
      font-weight: bold;
      flex: 1;
    }
    .severity-badge {
      display: inline-block;
      padding: 4px 12px;
      border-radius: 4px;
      font-size: 11px;
      font-weight: bold;
    }
    .severity-ERROR {
      background-color: #ffeeee;
      color: #cc0000;
    }
    .severity-WARNING {
      background-color: #fffbf0;
      color: #ff9900;
    }
    .section-title {
      font-weight: bold;
      font-size: 14px;
      color: #0066cc;
      margin: 20px 0 10px 0;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .section-content {
      font-size: 13px;
      color: #555;
      line-height: 1.7;
    }
    .resolution-steps {
      background-color: #eef7ff;
      border-left: 4px solid #0066cc;
      padding: 15px 15px 15px 20px;
      margin: 15px 0;
      border-radius: 4px;
    }
    .resolution-steps ol {
      margin: 10px 0;
      padding-left: 20px;
    }
    .resolution-steps li {
      margin: 10px 0;
      line-height: 1.6;
    }
    .example-box {
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 15px;
      margin: 15px 0;
      font-family: 'Courier New', monospace;
      font-size: 12px;
      overflow-x: auto;
    }
    .example-title {
      font-family: Arial, Helvetica, sans-serif;
      font-weight: bold;
      font-size: 12px;
      color: #666;
      margin-bottom: 10px;
    }
    .cause-list {
      list-style-type: disc;
      padding-left: 25px;
      margin: 10px 0;
    }
    .cause-list li {
      margin: 8px 0;
      line-height: 1.5;
    }
    .back-link {
      display: inline-block;
      margin-top: 30px;
      padding: 10px 20px;
      background-color: #f0f0f0;
      color: #333;
      text-decoration: none;
      border-radius: 4px;
      font-size: 14px;
      transition: background-color 0.2s;
    }
    .back-link:hover {
      background-color: #e0e0e0;
    }
  </style>
</head>
<body>

<!-- Header Banner -->
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="header-banner">
  <tr>
    <td><img src="../../images/IT Banner.png" alt="InterWeave IDE" align="left" width="100%" height="94"/></td>
  </tr>
</table>

<!-- Main Content -->
<div class="container">
  <div class="content-box">

    <div class="breadcrumb">
      <a href="index.jsp">&#128214; Error Documentation</a> &gt; Connection Errors
    </div>

    <h1>
      <span class="page-icon">&#128279;</span>
      Connection Errors
    </h1>

    <div class="intro">
      Connection errors occur when communicating with external systems such as APIs, databases, or web services.
      These errors typically involve network issues, authentication failures, or configuration problems.
      Below you'll find detailed information about each connection error code.
    </div>

    <!-- CONNECTION001 -->
    <div class="error-entry" id="CONNECTION001">
      <div class="error-header">
        <span class="error-code">CONNECTION001</span>
        <span class="error-message">Cannot connect to external system</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>External system is offline or not responding</li>
          <li>Network connectivity issues between systems</li>
          <li>Incorrect URL or endpoint in connection configuration</li>
          <li>Firewall blocking outbound connections</li>
          <li>DNS resolution failure for hostname</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify external system is online and accessible</strong> - Check status page or ping the system</li>
          <li><strong>Check network connectivity</strong> - Ensure network path exists between systems</li>
          <li><strong>Verify connection URL/endpoint is correct</strong> - Check for typos in configuration</li>
          <li><strong>Test connection with curl or ping</strong> - Verify basic connectivity</li>
          <li><strong>Check firewall and proxy settings</strong> - Ensure outbound connections are allowed</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Flow Execution Error:</div>
        Cannot connect to external system [CONNECTION001]<br>
        <br>
        URL: https://api.example.com/v1/customers<br>
        Error: Connection refused<br>
        <br>
        Verify the external system is running and the URL is correct.
      </div>
    </div>

    <!-- CONNECTION002 -->
    <div class="error-entry" id="CONNECTION002">
      <div class="error-header">
        <span class="error-code">CONNECTION002</span>
        <span class="error-message">Connection timed out</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>External system is responding too slowly</li>
          <li>Network latency or congestion</li>
          <li>Timeout value set too low in configuration</li>
          <li>External system is overloaded or under heavy load</li>
          <li>Firewall dropping packets after timeout</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check external system response time</strong> - Verify normal operation</li>
          <li><strong>Increase timeout setting if appropriate</strong> - Adjust connection timeout in configuration</li>
          <li><strong>Verify network latency is acceptable</strong> - Test ping times and network speed</li>
          <li><strong>Check for network congestion</strong> - Monitor network traffic patterns</li>
          <li><strong>Contact external system administrator</strong> - Report performance issues</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Connection Timeout:</div>
        Connection timed out [CONNECTION002]<br>
        <br>
        URL: https://api.example.com/v1/orders<br>
        Timeout: 30 seconds<br>
        <br>
        The external system did not respond within the configured<br>
        timeout period. Check system performance and network latency.
      </div>
    </div>

    <!-- CONNECTION003 -->
    <div class="error-entry" id="CONNECTION003">
      <div class="error-header">
        <span class="error-code">CONNECTION003</span>
        <span class="error-message">Invalid connection configuration</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Connection configuration file has errors</li>
          <li>Required connection parameters missing or invalid</li>
          <li>Incorrect connection type specified</li>
          <li>Configuration syntax errors</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Review connection configuration settings</strong> - Check all parameters</li>
          <li><strong>Verify all required parameters are set</strong> - Consult connection setup documentation</li>
          <li><strong>Check parameter values and formats</strong> - Ensure correct data types and formats</li>
          <li><strong>Use connection configuration template</strong> - Start with known-good example</li>
          <li><strong>Validate configuration against schema</strong> - Use validation tools if available</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Configuration Error:</div>
        Invalid connection configuration [CONNECTION003]<br>
        <br>
        Connection: SalesforceAPI<br>
        Issue: Authentication method not specified<br>
        <br>
        Required parameters:<br>
        - endpoint (URL)<br>
        - authMethod (OAuth2, Basic, API Key)<br>
        - credentials (username/password or token)
      </div>
    </div>

    <!-- CONNECTION004 -->
    <div class="error-entry" id="CONNECTION004">
      <div class="error-header">
        <span class="error-code">CONNECTION004</span>
        <span class="error-message">Required connection parameter is missing</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Connection configuration incomplete</li>
          <li>Required field not provided</li>
          <li>Parameter name typo in configuration</li>
          <li>Configuration was partially deleted or corrupted</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check which parameters are required</strong> - Review connection type documentation</li>
          <li><strong>Add missing connection parameter</strong> - Provide required value in configuration</li>
          <li><strong>Verify parameter name is correct</strong> - Check for typos or case sensitivity</li>
          <li><strong>Review connection type documentation</strong> - Understand all requirements</li>
          <li><strong>Check parameter is not hidden or conditional</strong> - Some parameters may be context-dependent</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Missing Parameter:</div>
        Required connection parameter is missing [CONNECTION004]<br>
        <br>
        Connection: QuickBooksOnline<br>
        Missing parameter: clientSecret<br>
        <br>
        For OAuth2 connections, both clientId and clientSecret<br>
        are required. Please add the missing parameter.
      </div>
    </div>

    <!-- CONNECTION005 -->
    <div class="error-entry" id="CONNECTION005">
      <div class="error-header">
        <span class="error-code">CONNECTION005</span>
        <span class="error-message">Network error occurred</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Network outage or connectivity loss</li>
          <li>DNS resolution failure</li>
          <li>Network interface down</li>
          <li>Routing issues between systems</li>
          <li>ISP or data center issues</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check network connectivity</strong> - Verify network adapters and interfaces</li>
          <li><strong>Verify DNS resolution</strong> - Test hostname resolution</li>
          <li><strong>Check for network outages</strong> - Review status pages and alerts</li>
          <li><strong>Review network logs</strong> - Look for connection failures or errors</li>
          <li><strong>Contact network administrator</strong> - Escalate persistent network issues</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Network Error:</div>
        Network error occurred [CONNECTION005]<br>
        <br>
        Details: Unable to resolve hostname 'api.example.com'<br>
        <br>
        This may indicate a DNS issue or network connectivity<br>
        problem. Check your network connection and DNS settings.
      </div>
    </div>

    <!-- CONNECTION006 -->
    <div class="error-entry" id="CONNECTION006">
      <div class="error-header">
        <span class="error-code">CONNECTION006</span>
        <span class="error-message">SSL/TLS certificate error</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>SSL/TLS certificate has expired</li>
          <li>Self-signed certificate not trusted</li>
          <li>Certificate chain validation failure</li>
          <li>Hostname mismatch in certificate</li>
          <li>Certificate revoked or invalid</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify SSL/TLS certificate is valid</strong> - Check certificate details</li>
          <li><strong>Check certificate expiration date</strong> - Renew if expired</li>
          <li><strong>Install or update trusted certificates</strong> - Add to Java truststore if needed</li>
          <li><strong>Verify certificate chain</strong> - Ensure all intermediate certificates are present</li>
          <li><strong>Contact external system administrator</strong> - Request certificate information</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">SSL Certificate Error:</div>
        SSL/TLS certificate error [CONNECTION006]<br>
        <br>
        URL: https://secure-api.example.com<br>
        Error: Certificate has expired<br>
        Expiration date: 2025-12-31<br>
        <br>
        Contact the external system administrator to renew<br>
        the SSL certificate.
      </div>
    </div>

    <!-- CONNECTION007 -->
    <div class="error-entry" id="CONNECTION007">
      <div class="error-header">
        <span class="error-code">CONNECTION007</span>
        <span class="error-message">Authentication failed with external system</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Invalid credentials (username/password or API key)</li>
          <li>Credentials have expired or been revoked</li>
          <li>Incorrect authentication method used</li>
          <li>OAuth token expired or invalid</li>
          <li>IP address not whitelisted on external system</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify authentication credentials are correct</strong> - Check username, password, or API key</li>
          <li><strong>Check if credentials have expired</strong> - Refresh or renew if necessary</li>
          <li><strong>Verify authentication method matches external system</strong> - Use correct auth type</li>
          <li><strong>Review external system authentication documentation</strong> - Ensure compliance with requirements</li>
          <li><strong>Check IP whitelist settings</strong> - Ensure server IP is allowed</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Authentication Failure:</div>
        Authentication failed with external system [CONNECTION007]<br>
        <br>
        System: Salesforce API<br>
        Auth Method: OAuth2<br>
        Error: Invalid or expired access token<br>
        <br>
        Please refresh your OAuth token or re-authenticate.
      </div>
    </div>

    <!-- CONNECTION008 -->
    <div class="error-entry" id="CONNECTION008">
      <div class="error-header">
        <span class="error-code">CONNECTION008</span>
        <span class="error-message">API endpoint not found</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Incorrect API endpoint URL or path</li>
          <li>API version in URL is outdated</li>
          <li>Typo in endpoint path</li>
          <li>API endpoint has been deprecated or removed</li>
          <li>Documentation mismatch with actual API</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify API endpoint URL is correct</strong> - Check against current documentation</li>
          <li><strong>Check for typos in endpoint path</strong> - Ensure exact spelling and case</li>
          <li><strong>Review API documentation for correct endpoints</strong> - Verify current paths</li>
          <li><strong>Verify API version in URL if required</strong> - Use correct version number</li>
          <li><strong>Check external system API changes or deprecations</strong> - Review release notes</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">404 Not Found:</div>
        API endpoint not found [CONNECTION008]<br>
        <br>
        URL: https://api.example.com/v1/customer<br>
        HTTP Status: 404 Not Found<br>
        <br>
        Note: The correct endpoint may be '/v1/customers' (plural)<br>
        Check API documentation for valid endpoint paths.
      </div>
    </div>

    <!-- CONNECTION009 -->
    <div class="error-entry" id="CONNECTION009">
      <div class="error-header">
        <span class="error-code">CONNECTION009</span>
        <span class="error-message">External system returned error</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>External system experiencing errors or issues</li>
          <li>Invalid request parameters or data format</li>
          <li>Business rule validation failure on external system</li>
          <li>External system temporarily unavailable</li>
          <li>Rate limiting or quota exceeded</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Review error response from external system</strong> - Read detailed error message</li>
          <li><strong>Check external system status page</strong> - Verify operational status</li>
          <li><strong>Verify request parameters and format</strong> - Ensure compliance with API requirements</li>
          <li><strong>Review external system logs if available</strong> - Look for detailed error information</li>
          <li><strong>Contact external system support</strong> - Escalate persistent issues</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">External System Error:</div>
        External system returned error [CONNECTION009]<br>
        <br>
        System: QuickBooks Online<br>
        HTTP Status: 400 Bad Request<br>
        Error: "Invalid CustomerRef: Customer '12345' not found"<br>
        <br>
        The external system rejected the request. Verify the<br>
        customer ID exists in QuickBooks before retrying.
      </div>
    </div>

    <!-- CONNECTION010 -->
    <div class="error-entry" id="CONNECTION010">
      <div class="error-header">
        <span class="error-code">CONNECTION010</span>
        <span class="error-message">Request rate limit exceeded</span>
        <span class="severity-badge severity-WARNING">WARNING</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Too many requests sent in short time period</li>
          <li>External system API rate limits exceeded</li>
          <li>Multiple integration flows running concurrently</li>
          <li>Insufficient delay between requests</li>
          <li>API quota or usage limits reached</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Reduce request frequency</strong> - Add delays between requests</li>
          <li><strong>Implement rate limiting in integration</strong> - Control request pacing</li>
          <li><strong>Check rate limit details from external system</strong> - Understand limits and quotas</li>
          <li><strong>Consider batch processing requests</strong> - Combine multiple operations if supported</li>
          <li><strong>Contact external system to increase rate limit</strong> - Request higher quota if needed</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Rate Limit Exceeded:</div>
        Request rate limit exceeded [CONNECTION010]<br>
        <br>
        System: Salesforce API<br>
        HTTP Status: 429 Too Many Requests<br>
        Rate Limit: 100 requests per minute<br>
        Retry-After: 45 seconds<br>
        <br>
        Reduce request frequency or wait before retrying.
      </div>
    </div>

    <a href="index.jsp" class="back-link">&#8592; Back to Error Documentation Index</a>

  </div>
</div>

</body>
</html>
