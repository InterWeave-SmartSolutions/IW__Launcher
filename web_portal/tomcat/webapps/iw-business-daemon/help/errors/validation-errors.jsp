<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.error.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Validation Errors - InterWeave IDE Portal</title>
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
      background-color: #ffeeee;
      color: #cc0000;
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
      <a href="index.jsp">&#128214; Error Documentation</a> &gt; Validation Errors
    </div>

    <h1>
      <span class="page-icon">&#9989;</span>
      Validation Errors
    </h1>

    <div class="intro">
      Validation errors occur when user input or configuration data does not meet required format, length,
      or content requirements. These errors help prevent invalid data from being processed or saved.
      Below you'll find detailed information about each validation error code.
    </div>

    <!-- VALIDATION001 -->
    <div class="error-entry" id="VALIDATION001">
      <div class="error-header">
        <span class="error-code">VALIDATION001</span>
        <span class="error-message">Required field is missing</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Form submitted with empty required field</li>
          <li>Required configuration parameter not provided</li>
          <li>Field was cleared but is mandatory</li>
          <li>JavaScript validation bypassed</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Provide value for required field</strong> - Fill in all mandatory fields</li>
          <li><strong>Check field name in documentation</strong> - Verify which field is required</li>
          <li><strong>Verify field is not hidden or disabled</strong> - Ensure field is accessible</li>
          <li><strong>Review form validation rules</strong> - Understand requirements</li>
          <li><strong>Check for client-side validation errors</strong> - Look for inline error messages</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Form Validation Error:</div>
        Required field is missing [VALIDATION001]<br>
        <br>
        Field: Email Address<br>
        <br>
        Please provide a value for this required field.
      </div>
    </div>

    <!-- VALIDATION002 -->
    <div class="error-entry" id="VALIDATION002">
      <div class="error-header">
        <span class="error-code">VALIDATION002</span>
        <span class="error-message">Invalid field format</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Field value doesn't match expected format or pattern</li>
          <li>Wrong data type (text instead of number, etc.)</li>
          <li>Special characters used where not allowed</li>
          <li>Incorrect date or time format</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check expected format in documentation</strong> - Review format requirements</li>
          <li><strong>Review format examples</strong> - Use provided examples as guide</li>
          <li><strong>Verify special characters and encoding</strong> - Remove invalid characters</li>
          <li><strong>Check data type</strong> - Ensure correct type (string, number, date)</li>
          <li><strong>Use format validation tools</strong> - Test format before submitting</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid Format:</div>
        Invalid field format [VALIDATION002]<br>
        <br>
        Field: Phone Number<br>
        Value: "123-ABC-4567"<br>
        Expected format: (###) ###-#### or ###-###-####<br>
        <br>
        Phone numbers must contain only digits, parentheses,<br>
        spaces, and hyphens.
      </div>
    </div>

    <!-- VALIDATION003 -->
    <div class="error-entry" id="VALIDATION003">
      <div class="error-header">
        <span class="error-code">VALIDATION003</span>
        <span class="error-message">Field value out of range</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Numeric value exceeds maximum or below minimum</li>
          <li>Date value outside allowed range</li>
          <li>Percentage value not between 0-100</li>
          <li>Business rule constraint violated</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check minimum and maximum allowed values</strong> - Review constraints</li>
          <li><strong>Review range constraints in documentation</strong> - Understand limits</li>
          <li><strong>Verify value is within business rules</strong> - Check domain rules</li>
          <li><strong>Check for numeric overflow</strong> - Ensure value fits in data type</li>
          <li><strong>Ensure value makes sense in context</strong> - Verify logical correctness</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Value Out of Range:</div>
        Field value out of range [VALIDATION003]<br>
        <br>
        Field: Connection Timeout<br>
        Value: 500000 milliseconds<br>
        Allowed range: 1000 - 300000 milliseconds (1 sec - 5 min)<br>
        <br>
        Please enter a value within the allowed range.
      </div>
    </div>

    <!-- VALIDATION004 -->
    <div class="error-entry" id="VALIDATION004">
      <div class="error-header">
        <span class="error-code">VALIDATION004</span>
        <span class="error-message">Invalid email address format</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Missing @ symbol</li>
          <li>Missing or invalid domain name</li>
          <li>Invalid characters in email address</li>
          <li>Multiple @ symbols</li>
          <li>Spaces in email address</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify email format: user@domain.com</strong> - Follow standard format</li>
          <li><strong>Check for typos in email address</strong> - Review carefully</li>
          <li><strong>Ensure @ symbol is present</strong> - Required separator</li>
          <li><strong>Verify domain has valid TLD</strong> - Check domain extension (.com, .org, etc.)</li>
          <li><strong>Remove extra spaces or special characters</strong> - Clean up input</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid Email:</div>
        Invalid email address format [VALIDATION004]<br>
        <br>
        Value: "john.smith.company.com"<br>
        <br>
        Email addresses must contain an @ symbol separating<br>
        the username from the domain name.<br>
        <br>
        Example: john.smith@company.com
      </div>
    </div>

    <!-- VALIDATION005 -->
    <div class="error-entry" id="VALIDATION005">
      <div class="error-header">
        <span class="error-code">VALIDATION005</span>
        <span class="error-message">Invalid URL format</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Missing protocol (http:// or https://)</li>
          <li>Invalid characters in URL</li>
          <li>Malformed domain name</li>
          <li>Spaces in URL (should use %20)</li>
          <li>Invalid port number</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify URL format</strong> - Must start with http:// or https://</li>
          <li><strong>Include protocol</strong> - Specify http:// or https:// at beginning</li>
          <li><strong>Check for typos in URL</strong> - Review entire URL carefully</li>
          <li><strong>Verify domain name is valid</strong> - Check domain format</li>
          <li><strong>Test URL in browser</strong> - Ensure URL is accessible</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid URL:</div>
        Invalid URL format [VALIDATION005]<br>
        <br>
        Value: "api.example.com/v1/customers"<br>
        <br>
        URLs must include the protocol (http:// or https://)<br>
        <br>
        Correct format:<br>
        https://api.example.com/v1/customers
      </div>
    </div>

    <!-- VALIDATION006 -->
    <div class="error-entry" id="VALIDATION006">
      <div class="error-header">
        <span class="error-code">VALIDATION006</span>
        <span class="error-message">Invalid date/time format</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Date format doesn't match expected pattern</li>
          <li>Invalid date values (e.g., February 30)</li>
          <li>Wrong separator characters</li>
          <li>Missing time zone information where required</li>
          <li>12-hour vs 24-hour format mismatch</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check expected date/time format</strong> - Review documentation</li>
          <li><strong>Use ISO-8601 format when possible</strong> - YYYY-MM-DD or YYYY-MM-DDTHH:mm:ss</li>
          <li><strong>Verify date is valid</strong> - Check for invalid dates like Feb 30</li>
          <li><strong>Check timezone handling</strong> - Include timezone if required</li>
          <li><strong>Use date picker if available</strong> - Ensures correct format</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid Date Format:</div>
        Invalid date/time format [VALIDATION006]<br>
        <br>
        Field: Start Date<br>
        Value: "03/32/2026"<br>
        <br>
        The date is invalid (March does not have 32 days)<br>
        <br>
        Expected format: YYYY-MM-DD (e.g., 2026-03-15)<br>
        or MM/DD/YYYY (e.g., 03/15/2026)
      </div>
    </div>

    <!-- VALIDATION007 -->
    <div class="error-entry" id="VALIDATION007">
      <div class="error-header">
        <span class="error-code">VALIDATION007</span>
        <span class="error-message">Field length exceeds maximum</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Text input exceeds maximum character limit</li>
          <li>Database column size constraint</li>
          <li>Pasted content includes hidden characters</li>
          <li>Multi-byte characters counted incorrectly</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Check maximum length constraint</strong> - Review field limits</li>
          <li><strong>Shorten field value</strong> - Reduce text to fit within limit</li>
          <li><strong>Review length requirements in documentation</strong> - Understand constraints</li>
          <li><strong>Check for accidental extra content</strong> - Remove unnecessary text</li>
          <li><strong>Use text truncation if appropriate</strong> - Summarize longer content</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Length Exceeded:</div>
        Field length exceeds maximum [VALIDATION007]<br>
        <br>
        Field: Company Name<br>
        Current length: 275 characters<br>
        Maximum length: 255 characters<br>
        <br>
        Please shorten the company name by at least 20 characters.
      </div>
    </div>

    <!-- VALIDATION008 -->
    <div class="error-entry" id="VALIDATION008">
      <div class="error-header">
        <span class="error-code">VALIDATION008</span>
        <span class="error-message">Field value does not match required pattern</span>
        <span class="severity-badge">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Value doesn't match regular expression pattern</li>
          <li>Missing required prefix or suffix</li>
          <li>Wrong format for structured data (e.g., postal codes)</li>
          <li>Case sensitivity issues</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Review required pattern in documentation</strong> - Understand pattern rules</li>
          <li><strong>Check pattern examples</strong> - Use provided examples as template</li>
          <li><strong>Verify value matches regex pattern</strong> - Test against pattern</li>
          <li><strong>Look for typos or format errors</strong> - Review input carefully</li>
          <li><strong>Use pattern validation tools</strong> - Test pattern matching online</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Pattern Mismatch:</div>
        Field value does not match required pattern [VALIDATION008]<br>
        <br>
        Field: Postal Code<br>
        Value: "12345678"<br>
        Required pattern: #####-#### or #####<br>
        <br>
        US postal codes must be 5 digits or 5 digits + 4 digits<br>
        separated by a hyphen.<br>
        <br>
        Valid examples: 12345 or 12345-6789
      </div>
    </div>

    <a href="index.jsp" class="back-link">&#8592; Back to Error Documentation Index</a>

  </div>
</div>

</body>
</html>
