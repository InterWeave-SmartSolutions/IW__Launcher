<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.error.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Authentication Errors - InterWeave IDE Portal</title>
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
      <a href="index.jsp">&#128214; Error Documentation</a> &gt; Authentication Errors
    </div>

    <h1>
      <span class="page-icon">&#128274;</span>
      Authentication Errors
    </h1>

    <div class="intro">
      Authentication errors occur during login, session management, and access control operations.
      These errors typically involve invalid credentials, inactive accounts, or insufficient permissions.
      Below you'll find detailed information about each authentication error code.
    </div>

    <!-- AUTH001 -->
    <div class="error-entry" id="AUTH001">
      <div class="error-header">
        <span class="error-code">AUTH001</span>
        <span class="error-message">Invalid email or password</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Incorrect email address entered (typo or wrong account)</li>
          <li>Incorrect password (passwords are case-sensitive)</li>
          <li>Account has not been registered yet</li>
          <li>Caps Lock is enabled when entering password</li>
          <li>Copy-pasted password includes extra whitespace</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify email address is correct</strong> - Check for typos and ensure you're using the registered email</li>
          <li><strong>Check password for typos</strong> - Remember that passwords are case-sensitive</li>
          <li><strong>If forgotten, contact your administrator</strong> to reset your password</li>
          <li><strong>Ensure account has been activated</strong> - Check your email for activation instructions</li>
          <li><strong>Try disabling browser auto-fill</strong> and manually entering credentials</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Login Form Error:</div>
        Email: user@example.com<br>
        Password: ********<br>
        <br>
        Error: Invalid email or password [AUTH001]<br>
        <br>
        Note: For security reasons, we don't specify whether the email<br>
        or password was incorrect.
      </div>
    </div>

    <!-- AUTH002 -->
    <div class="error-entry" id="AUTH002">
      <div class="error-header">
        <span class="error-code">AUTH002</span>
        <span class="error-message">User account is inactive</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>User account has been deactivated by administrator</li>
          <li>Account was never activated after registration</li>
          <li>Account was temporarily suspended</li>
          <li>Account expired due to inactivity period</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Contact your company administrator</strong> to activate your account</li>
          <li><strong>Check if account activation email was received</strong> and follow activation instructions</li>
          <li><strong>Verify your user status</strong> in the company user management portal</li>
          <li><strong>If recently registered</strong>, allow time for administrator approval</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Login Response:</div>
        Your user account is currently inactive [AUTH002]<br>
        <br>
        Please contact your company administrator to activate<br>
        your account before logging in.<br>
        <br>
        Administrator contact: admin@yourcompany.com
      </div>
    </div>

    <!-- AUTH003 -->
    <div class="error-entry" id="AUTH003">
      <div class="error-header">
        <span class="error-code">AUTH003</span>
        <span class="error-message">Company account is inactive</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Company subscription or license has expired</li>
          <li>Company account was suspended by InterWeave support</li>
          <li>Payment issues or billing problems</li>
          <li>Company account is pending activation</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Contact InterWeave support</strong> to verify company account status</li>
          <li><strong>Check if company license has expired</strong> and renew if necessary</li>
          <li><strong>Verify payment and subscription status</strong> with your billing department</li>
          <li><strong>Contact your company administrator</strong> for account status information</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Login Response:</div>
        Your company account is currently inactive [AUTH003]<br>
        <br>
        This may be due to subscription expiration or suspension.<br>
        <br>
        Please contact InterWeave support for assistance:<br>
        support@interweave.com
      </div>
    </div>

    <!-- AUTH004 -->
    <div class="error-entry" id="AUTH004">
      <div class="error-header">
        <span class="error-code">AUTH004</span>
        <span class="error-message">Session has expired. Please log in again</span>
        <span class="severity-badge severity-WARNING">WARNING</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>User was inactive for too long (session timeout)</li>
          <li>Browser cookies were cleared or disabled</li>
          <li>Server was restarted, invalidating sessions</li>
          <li>User logged in from another device or browser</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Log in again</strong> to create a new session</li>
          <li><strong>Ensure browser cookies are enabled</strong> for session management</li>
          <li><strong>Check session timeout settings</strong> in company configuration if timeouts are too short</li>
          <li><strong>Contact administrator</strong> if sessions expire too frequently</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Page Access Error:</div>
        Your session has expired [AUTH004]<br>
        <br>
        For security reasons, you have been logged out after<br>
        30 minutes of inactivity.<br>
        <br>
        Please log in again to continue.
      </div>
    </div>

    <!-- AUTH005 -->
    <div class="error-entry" id="AUTH005">
      <div class="error-header">
        <span class="error-code">AUTH005</span>
        <span class="error-message">Access denied. Insufficient permissions</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>User role does not have required permissions</li>
          <li>Attempting to access administrator-only features</li>
          <li>Permissions were recently revoked</li>
          <li>Resource requires special access rights</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Contact your administrator</strong> to request necessary permissions</li>
          <li><strong>Verify you are accessing the correct resource</strong> for your role</li>
          <li><strong>Check your user role and permission assignments</strong> in the user management portal</li>
          <li><strong>Review company access policies</strong> to understand permission requirements</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Access Denied:</div>
        You do not have permission to access this resource [AUTH005]<br>
        <br>
        Required permission: ADMIN_COMPANY_CONFIG<br>
        Your current role: Standard User<br>
        <br>
        Contact your administrator to request access.
      </div>
    </div>

    <!-- AUTH006 -->
    <div class="error-entry" id="AUTH006">
      <div class="error-header">
        <span class="error-code">AUTH006</span>
        <span class="error-message">Authentication credentials are required</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Attempting to access protected resource without logging in</li>
          <li>Authentication token missing from API request</li>
          <li>Direct URL access to authenticated page</li>
          <li>Session cookie not sent with request</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Log in</strong> before accessing this resource</li>
          <li><strong>Include authentication token</strong> in API requests (if using API)</li>
          <li><strong>Check that session has not expired</strong> and refresh if needed</li>
          <li><strong>Enable cookies</strong> in your browser for session management</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Authentication Required:</div>
        Authentication credentials are required [AUTH006]<br>
        <br>
        You must be logged in to access this page.<br>
        <br>
        Click here to log in: /iw-business-daemon/IWLogin.jsp
      </div>
    </div>

    <!-- AUTH007 -->
    <div class="error-entry" id="AUTH007">
      <div class="error-header">
        <span class="error-code">AUTH007</span>
        <span class="error-message">Invalid or expired authentication token</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Authentication token has expired</li>
          <li>Token was invalidated (logout or password change)</li>
          <li>Token format is incorrect or corrupted</li>
          <li>System time synchronization issues (for time-based tokens)</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Log in again</strong> to obtain a new authentication token</li>
          <li><strong>Verify token format and encoding</strong> if using API</li>
          <li><strong>Check system time synchronization</strong> (tokens may be time-based)</li>
          <li><strong>Ensure token is properly stored and transmitted</strong> in API requests</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">API Request Error:</div>
        POST /api/flows/execute<br>
        Authorization: Bearer eyJhbGc...<br>
        <br>
        Response: 401 Unauthorized<br>
        {<br>
        &nbsp;&nbsp;"error": "AUTH007",<br>
        &nbsp;&nbsp;"message": "Invalid or expired authentication token"<br>
        }<br>
        <br>
        Please obtain a new token by logging in again.
      </div>
    </div>

    <a href="index.jsp" class="back-link">&#8592; Back to Error Documentation Index</a>

  </div>
</div>

</body>
</html>
