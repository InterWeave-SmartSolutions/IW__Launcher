<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.help.*" %>
<%@ page import="com.interweave.error.*" %>
<%
// Parse request parameters
String contextParam = request.getParameter("context");
String operationParam = request.getParameter("operation");
String errorCodeParam = request.getParameter("errorCode");
String pageParam = request.getParameter("page");

// Determine help content to display
HelpLinkService.HelpContext context = null;
HelpLinkService.HelpTopic helpTopic = null;
ErrorCode errorCode = null;
String fullDocUrl = null;

// If error code is provided, show error-specific help
if (errorCodeParam != null && errorCodeParam.length() > 0) {
    try {
        errorCode = ErrorCode.findByCode(errorCodeParam);
        if (errorCode != null) {
            context = HelpLinkService.HelpContext.ERROR_TROUBLESHOOTING;
            fullDocUrl = HelpLinkService.getErrorHelpUrl(errorCode);
        }
    } catch (Exception e) {
        // Ignore - will show general help
    }
}

// If operation is provided, try to get operation-specific help
if (helpTopic == null && operationParam != null && operationParam.length() > 0) {
    try {
        helpTopic = HelpLinkService.getOperationHelp(operationParam);
    } catch (Exception e) {
        // Ignore - will try context help
    }
}

// If context is provided, get context help
if (helpTopic == null && contextParam != null && contextParam.length() > 0) {
    try {
        // Try to match context string to enum
        for (HelpLinkService.HelpContext hc : HelpLinkService.HelpContext.values()) {
            if (hc.getCode().equals(contextParam)) {
                context = hc;
                helpTopic = HelpLinkService.getContextHelp(context);
                break;
            }
        }
    } catch (Exception e) {
        // Ignore - will show default help
    }
}

// Default to general help if nothing else matched
if (helpTopic == null) {
    context = HelpLinkService.HelpContext.GENERAL;
    helpTopic = HelpLinkService.getContextHelp(context);
}

// Get full documentation URL if not already set
if (fullDocUrl == null && helpTopic != null) {
    fullDocUrl = helpTopic.getHelpUrl();
}
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title><% if (helpTopic != null) { %><%= helpTopic.getTitle() %><% } else { %>Help<% } %> - InterWeave IDE</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #ffffff;
      font-size: 13px;
      line-height: 1.5;
    }
    .help-container {
      padding: 20px;
      max-width: 700px;
      margin: 0 auto;
    }
    .help-header {
      border-bottom: 2px solid #0066cc;
      padding-bottom: 15px;
      margin-bottom: 20px;
    }
    .help-title {
      font-size: 20px;
      font-weight: bold;
      color: #0066cc;
      margin: 0 0 5px 0;
      display: flex;
      align-items: center;
    }
    .help-icon {
      font-size: 24px;
      margin-right: 10px;
    }
    .help-description {
      font-size: 14px;
      color: #666;
      margin: 10px 0 0 0;
    }
    .help-section {
      margin: 25px 0;
    }
    .section-title {
      font-size: 15px;
      font-weight: bold;
      color: #333;
      margin: 0 0 10px 0;
      padding-bottom: 5px;
      border-bottom: 1px solid #ddd;
    }
    .error-code-display {
      background-color: #fff3cd;
      border: 1px solid #ffc107;
      border-radius: 4px;
      padding: 15px;
      margin-bottom: 20px;
    }
    .error-code-label {
      font-weight: bold;
      color: #856404;
      font-size: 14px;
    }
    .error-code-value {
      font-family: "Courier New", monospace;
      font-size: 16px;
      color: #856404;
      font-weight: bold;
      margin-top: 5px;
    }
    .error-message {
      color: #333;
      margin-top: 10px;
      font-size: 13px;
    }
    .examples-list {
      list-style: none;
      padding: 0;
      margin: 10px 0;
    }
    .example-item {
      padding: 10px 15px;
      margin: 6px 0;
      background-color: #f8f9fa;
      border-left: 3px solid #0066cc;
      border-radius: 3px;
      font-family: "Courier New", monospace;
      font-size: 12px;
      color: #333;
    }
    .example-item::before {
      content: "\u2713  ";
      color: #0066cc;
      font-weight: bold;
      font-family: Arial, sans-serif;
    }
    .related-topics {
      margin-top: 20px;
      padding-top: 15px;
      border-top: 1px solid #ddd;
    }
    .related-topics-title {
      font-size: 14px;
      font-weight: bold;
      color: #666;
      margin-bottom: 10px;
    }
    .related-links {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    .related-link {
      display: inline-block;
      margin: 5px 10px 5px 0;
    }
    .related-link a {
      display: inline-block;
      padding: 5px 12px;
      background-color: #e9ecef;
      color: #0066cc;
      text-decoration: none;
      border-radius: 3px;
      font-size: 12px;
      transition: background-color 0.2s;
    }
    .related-link a:hover {
      background-color: #d3d6d9;
    }
    .resolution-steps {
      background-color: #f0f8ff;
      border: 1px solid #b3d9ff;
      border-radius: 4px;
      padding: 15px;
      margin: 15px 0;
    }
    .resolution-steps-title {
      font-weight: bold;
      color: #0066cc;
      margin-bottom: 10px;
      font-size: 14px;
    }
    .resolution-list {
      margin: 0;
      padding-left: 25px;
    }
    .resolution-list li {
      margin: 8px 0;
      color: #333;
      line-height: 1.5;
    }
    .action-buttons {
      margin-top: 25px;
      padding-top: 20px;
      border-top: 1px solid #ddd;
      text-align: center;
    }
    .btn {
      display: inline-block;
      padding: 10px 20px;
      margin: 5px;
      border: none;
      border-radius: 4px;
      font-size: 13px;
      cursor: pointer;
      text-decoration: none;
      transition: background-color 0.2s;
    }
    .btn-primary {
      background-color: #0066cc;
      color: #ffffff;
    }
    .btn-primary:hover {
      background-color: #0052a3;
    }
    .btn-secondary {
      background-color: #6c757d;
      color: #ffffff;
    }
    .btn-secondary:hover {
      background-color: #5a6268;
    }
    .info-box {
      background-color: #e7f3ff;
      border-left: 4px solid #0066cc;
      padding: 12px 15px;
      margin: 15px 0;
      border-radius: 3px;
    }
    .info-box-icon {
      color: #0066cc;
      font-weight: bold;
      margin-right: 5px;
    }
    .no-print {
      /* Hide in print */
    }
    @media print {
      .no-print {
        display: none !important;
      }
      .action-buttons {
        display: none;
      }
    }
  </style>
  <script>
    function openFullDocs() {
      <% if (fullDocUrl != null) { %>
      window.open('<%= fullDocUrl %>', '_blank');
      <% } %>
    }

    function closeHelp() {
      window.close();
    }

    function printHelp() {
      window.print();
    }
  </script>
</head>
<body>
  <div class="help-container">
    <!-- Header -->
    <div class="help-header">
      <div class="help-title">
        <span class="help-icon">&#9432;</span>
        <% if (helpTopic != null) { %>
          <%= helpTopic.getTitle() %>
        <% } else { %>
          Help
        <% } %>
      </div>
      <% if (helpTopic != null && helpTopic.getDescription() != null) { %>
      <div class="help-description">
        <%= helpTopic.getDescription() %>
      </div>
      <% } %>
    </div>

    <!-- Error Code Display (if showing error help) -->
    <% if (errorCode != null) { %>
    <div class="error-code-display">
      <div class="error-code-label">Error Code:</div>
      <div class="error-code-value"><%= errorCode.getCode() %></div>
      <div class="error-message"><%= errorCode.getDefaultMessage() %></div>
    </div>

    <!-- Resolution Steps for Error -->
    <div class="resolution-steps">
      <div class="resolution-steps-title">Resolution Steps:</div>
      <%
      String resolutionSteps = ErrorDocumentation.getResolutionSteps(errorCode);
      if (resolutionSteps != null && resolutionSteps.length() > 0) {
        String[] steps = resolutionSteps.split("\n");
      %>
        <ol class="resolution-list">
        <% for (String step : steps) {
          step = step.trim();
          // Remove leading numbers like "1. ", "2. ", etc.
          step = step.replaceAll("^\\d+\\.\\s*", "");
          if (step.length() > 0) {
        %>
          <li><%= step %></li>
        <%
          }
        }
        %>
        </ol>
      <% } else { %>
        <p>Check the full documentation for detailed resolution steps.</p>
      <% } %>
    </div>
    <% } %>

    <!-- Examples Section -->
    <% if (helpTopic != null && helpTopic.getExamples() != null && helpTopic.getExamples().length > 0) { %>
    <div class="help-section">
      <div class="section-title">
        <% if (errorCode != null) { %>
          Common Causes:
        <% } else { %>
          Quick Tips:
        <% } %>
      </div>
      <ul class="examples-list">
        <% for (String example : helpTopic.getExamples()) { %>
        <li class="example-item"><%= example %></li>
        <% } %>
      </ul>
    </div>
    <% } %>

    <!-- Additional Info Box -->
    <% if (errorCode != null) { %>
    <div class="info-box">
      <span class="info-box-icon">&#9432;</span>
      <strong>Need More Help?</strong> Reference this error code (<strong><%= errorCode.getCode() %></strong>) when contacting support for faster assistance.
    </div>
    <% } else if (context != null && context != HelpLinkService.HelpContext.GENERAL) { %>
    <div class="info-box">
      <span class="info-box-icon">&#128161;</span>
      <strong>Tip:</strong> Press F1 while working in the IDE to get context-sensitive help for your current task.
    </div>
    <% } %>

    <!-- Related Topics -->
    <% if (helpTopic != null && helpTopic.getRelatedTopics() != null && helpTopic.getRelatedTopics().length > 0) { %>
    <div class="related-topics">
      <div class="related-topics-title">Related Topics:</div>
      <ul class="related-links">
        <% for (String topic : helpTopic.getRelatedTopics()) { %>
        <li class="related-link"><a href="javascript:void(0)"><%= topic %></a></li>
        <% } %>
      </ul>
    </div>
    <% } %>

    <!-- Action Buttons -->
    <div class="action-buttons no-print">
      <% if (fullDocUrl != null) { %>
      <button class="btn btn-primary" onclick="openFullDocs()">View Full Documentation</button>
      <% } %>
      <button class="btn btn-secondary" onclick="printHelp()">Print</button>
      <button class="btn btn-secondary" onclick="closeHelp()">Close</button>
    </div>
  </div>
</body>
</html>
