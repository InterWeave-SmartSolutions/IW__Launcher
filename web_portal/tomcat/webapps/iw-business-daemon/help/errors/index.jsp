<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.error.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Error Documentation - InterWeave IDE Portal</title>
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
      max-width: 1200px;
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
    h1 {
      color: #333;
      font-size: 28px;
      margin-top: 0;
      border-bottom: 3px solid #0066cc;
      padding-bottom: 10px;
    }
    h2 {
      color: #0066cc;
      font-size: 20px;
      margin-top: 30px;
      margin-bottom: 15px;
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
    .search-box {
      margin: 20px 0 30px 0;
      padding: 20px;
      background-color: #eef7ff;
      border-radius: 6px;
    }
    .search-input {
      width: 100%;
      max-width: 500px;
      padding: 10px 15px;
      font-size: 14px;
      border: 2px solid #0066cc;
      border-radius: 4px;
      box-sizing: border-box;
    }
    .category-section {
      margin: 30px 0;
      padding: 20px;
      background-color: #fafafa;
      border: 1px solid #e0e0e0;
      border-radius: 6px;
    }
    .category-title {
      font-size: 18px;
      font-weight: bold;
      color: #0066cc;
      margin-bottom: 15px;
      display: flex;
      align-items: center;
    }
    .category-icon {
      font-size: 24px;
      margin-right: 10px;
    }
    .error-list {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    .error-item {
      padding: 12px 15px;
      margin: 8px 0;
      background-color: #ffffff;
      border: 1px solid #ddd;
      border-radius: 4px;
      transition: all 0.2s;
    }
    .error-item:hover {
      background-color: #f0f8ff;
      border-color: #0066cc;
      box-shadow: 0 2px 4px rgba(0,102,204,0.1);
    }
    .error-code {
      font-family: 'Courier New', monospace;
      font-weight: bold;
      color: #cc0000;
      font-size: 13px;
      display: inline-block;
      min-width: 100px;
    }
    .error-description {
      color: #555;
      font-size: 13px;
      margin-left: 10px;
    }
    .error-link {
      text-decoration: none;
      color: inherit;
      display: block;
    }
    .severity-badge {
      display: inline-block;
      padding: 2px 8px;
      border-radius: 3px;
      font-size: 11px;
      font-weight: bold;
      margin-left: 10px;
    }
    .severity-ERROR {
      background-color: #ffeeee;
      color: #cc0000;
    }
    .severity-WARNING {
      background-color: #fffbf0;
      color: #ff9900;
    }
    .severity-INFO {
      background-color: #eef7ff;
      color: #0066cc;
    }
    .quick-links {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 15px;
      margin: 30px 0;
    }
    .quick-link-card {
      background-color: #ffffff;
      border: 2px solid #0066cc;
      border-radius: 6px;
      padding: 20px;
      text-align: center;
      text-decoration: none;
      transition: all 0.2s;
    }
    .quick-link-card:hover {
      background-color: #0066cc;
      color: #ffffff;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,102,204,0.3);
    }
    .quick-link-icon {
      font-size: 36px;
      display: block;
      margin-bottom: 10px;
    }
    .quick-link-title {
      font-size: 16px;
      font-weight: bold;
      color: #0066cc;
    }
    .quick-link-card:hover .quick-link-title {
      color: #ffffff;
    }
    .quick-link-count {
      font-size: 12px;
      color: #999;
      margin-top: 5px;
    }
    .quick-link-card:hover .quick-link-count {
      color: #ffffff;
    }
    .note {
      padding: 15px;
      background-color: #fffbf0;
      border-left: 4px solid #ff9900;
      border-radius: 4px;
      margin: 20px 0;
      font-size: 13px;
      color: #666;
    }
  </style>
  <script src="search.js"></script>
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

    <h1>&#128214; Error Documentation</h1>

    <div class="intro">
      Welcome to the InterWeave IDE error documentation. This guide provides detailed information about
      all error codes you may encounter, including causes, resolution steps, and examples. Use the search
      box below to quickly find information about a specific error code.
    </div>

    <!-- Search Box -->
    <div class="search-box">
      <label for="searchInput" style="font-weight:bold; color:#0066cc; display:block; margin-bottom:8px;">
        &#128269; Search Error Codes:
      </label>
      <input type="text" id="searchInput" class="search-input"
             placeholder="Enter error code (e.g., AUTH001) or keyword..."
             >
    </div>

    <!-- Quick Links -->
    <h2>Browse by Category</h2>
    <div class="quick-links">
      <a href="auth-errors.jsp" class="quick-link-card">
        <span class="quick-link-icon">&#128274;</span>
        <div class="quick-link-title">Authentication</div>
        <div class="quick-link-count">7 error codes</div>
      </a>
      <a href="connection-errors.jsp" class="quick-link-card">
        <span class="quick-link-icon">&#128279;</span>
        <div class="quick-link-title">Connection</div>
        <div class="quick-link-count">10 error codes</div>
      </a>
      <a href="validation-errors.jsp" class="quick-link-card">
        <span class="quick-link-icon">&#9989;</span>
        <div class="quick-link-title">Validation</div>
        <div class="quick-link-count">8 error codes</div>
      </a>
      <a href="xpath-errors.jsp" class="quick-link-card">
        <span class="quick-link-icon">&#128220;</span>
        <div class="quick-link-title">XPath/XSLT</div>
        <div class="quick-link-count">8 error codes</div>
      </a>
    </div>

    <div class="note">
      <strong>&#9432; Note:</strong> If you receive an error code not listed here, please check the system
      logs for additional details or contact support with your transaction ID.
    </div>

    <!-- All Error Codes by Category -->
    <h2>All Error Codes</h2>

    <%
    // Group errors by category
    java.util.Map<ErrorCategory, java.util.List<ErrorCode>> errorsByCategory =
        new java.util.LinkedHashMap<>();

    for (ErrorCode errorCode : ErrorCode.values()) {
      ErrorCategory category = errorCode.getCategory();
      if (!errorsByCategory.containsKey(category)) {
        errorsByCategory.put(category, new java.util.ArrayList<ErrorCode>());
      }
      errorsByCategory.get(category).add(errorCode);
    }

    // Define category icons and links
    java.util.Map<ErrorCategory, String> categoryIcons = new java.util.HashMap<>();
    categoryIcons.put(ErrorCategory.AUTH, "&#128274;");
    categoryIcons.put(ErrorCategory.DB, "&#128190;");
    categoryIcons.put(ErrorCategory.FLOW, "&#128260;");
    categoryIcons.put(ErrorCategory.CONFIG, "&#9881;");
    categoryIcons.put(ErrorCategory.VALIDATION, "&#9989;");
    categoryIcons.put(ErrorCategory.XPATH, "&#128220;");
    categoryIcons.put(ErrorCategory.CONNECTION, "&#128279;");
    categoryIcons.put(ErrorCategory.SYSTEM, "&#9888;");

    java.util.Map<ErrorCategory, String> categoryLinks = new java.util.HashMap<>();
    categoryLinks.put(ErrorCategory.AUTH, "auth-errors.jsp");
    categoryLinks.put(ErrorCategory.CONNECTION, "connection-errors.jsp");
    categoryLinks.put(ErrorCategory.VALIDATION, "validation-errors.jsp");
    categoryLinks.put(ErrorCategory.XPATH, "xpath-errors.jsp");

    // Display each category
    for (java.util.Map.Entry<ErrorCategory, java.util.List<ErrorCode>> entry : errorsByCategory.entrySet()) {
      ErrorCategory category = entry.getKey();
      java.util.List<ErrorCode> codes = entry.getValue();
      String icon = categoryIcons.get(category);
      String link = categoryLinks.get(category);
    %>

    <div class="category-section">
      <div class="category-title">
        <span class="category-icon"><%= icon %></span>
        <%= category.getDisplayName() %>
        <% if (link != null) { %>
          <a href="<%= link %>" style="margin-left:auto; font-size:12px; color:#0066cc; text-decoration:none;">
            View Details &#8594;
          </a>
        <% } %>
      </div>

      <ul class="error-list">
      <% for (ErrorCode code : codes) {
         String detailLink = link != null ? link + "#" + code.getCode() : "#";
      %>
        <li class="error-item">
          <a href="<%= detailLink %>" class="error-link">
            <span class="error-code"><%= code.getCode() %></span>
            <span class="severity-badge severity-<%= code.getSeverity() %>"><%= code.getSeverity() %></span>
            <span class="error-description"><%= code.getDefaultMessage() %></span>
          </a>
        </li>
      <% } %>
      </ul>
    </div>

    <% } %>

  </div>
</div>

</body>
</html>
