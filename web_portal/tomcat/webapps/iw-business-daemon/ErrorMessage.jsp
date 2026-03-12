<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="com.interweave.error.*" %>
<%@ page import="com.interweave.web.HtmlEncoder" %>
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%
// Parse request parameters
String target = request.getParameter("ErrorMessageTarget");
String brand = request.getParameter("PortalBrand");
if(brand==null){
	brand="";
}
String button = request.getParameter("ButtonLable");
if(button==null || button.trim().length()==0){
	button="OK";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
	solutions="";
}
String env2Con = request.getParameter("Env2Con");
if(env2Con==null){
	env2Con="COM";
}
String brandSol = "";
if (brand != null && brand.length() > 0) {
	brandSol += "?PortalBrand=" + java.net.URLEncoder.encode(brand, "UTF-8");
}
if (solutions != null && solutions.length() > 0) {
	brandSol += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + java.net.URLEncoder.encode(solutions, "UTF-8");
}

// Parse error details - support both legacy text errors and new structured IWError
String errorText = request.getParameter("ErrorMessageText");
String errorCode = request.getParameter("ErrorCode");
String errorCause = request.getParameter("ErrorCause");
String errorComponent = request.getParameter("ErrorComponent");
String errorResolution = request.getParameter("ErrorResolution");
String errorDocLink = request.getParameter("ErrorDocLink");
String transactionId = request.getParameter("TransactionId");

// TEMPORARILY DISABLED: Error framework not compiled
// Try to get structured error from request attribute
// IWError iwError = null;
// try {
// 	iwError = (IWError) request.getAttribute("iwError");
// } catch (Exception e) {
// 	// Ignore if error classes not available - just use text error
// }

// Use structured error if available, otherwise fall back to parameters/text
// if (iwError != null) {
// 	if (errorText == null) errorText = iwError.getMessage();
// 	if (errorCode == null && iwError.getErrorCode() != null) errorCode = iwError.getErrorCode().getCode();
// 	if (errorCause == null) errorCause = iwError.getCause();
// 	if (errorComponent == null) errorComponent = iwError.getComponent();
// 	if (errorResolution == null) errorResolution = iwError.getResolution();
// 	if (errorDocLink == null) errorDocLink = iwError.getDocumentationLink();
// 	if (transactionId == null) transactionId = iwError.getTransactionId();
// }

// Ensure we have at least error text
if (errorText == null) {
	errorText = "An unexpected error occurred. Please contact support for assistance.";
}

// TEMPORARILY DISABLED: ErrorCode classes not compiled
// Try to lookup documentation if we have an error code but no resolution
// if (errorCode != null && errorResolution == null) {
// 	try {
// 		ErrorCode code = ErrorCode.findByCode(errorCode);
// 		if (code != null) {
// 			errorResolution = ErrorDocumentation.getResolutionSteps(code);
// 			if (errorDocLink == null) {
// 				errorDocLink = ErrorDocumentation.getDocumentationUrl(code);
// 			}
// 		}
// 	} catch (Exception e) {
// 		// Silently ignore - just won't have enhanced error info
// 	}
// }

// Generate transaction ID if not present
if (transactionId == null || transactionId.trim().length() == 0) {
	transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
}
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Error - InterWeave IDE Portal</title>
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
      max-width: 900px;
      margin: 30px auto;
      padding: 0 20px;
    }
    .error-box {
      background-color: #ffffff;
      border: 2px solid #cc0000;
      border-radius: 8px;
      padding: 30px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .error-header {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      padding-bottom: 15px;
      border-bottom: 1px solid #eee;
    }
    .error-icon {
      font-size: 48px;
      color: #cc0000;
      margin-right: 15px;
    }
    .error-title {
      flex: 1;
    }
    .error-title h1 {
      margin: 0;
      font-size: 24px;
      color: #cc0000;
      font-weight: bold;
    }
    .error-code {
      display: inline-block;
      background-color: #ffeeee;
      color: #cc0000;
      padding: 4px 12px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: bold;
      font-family: 'Courier New', monospace;
      margin-top: 5px;
    }
    .error-section {
      margin: 20px 0;
      padding: 15px;
      background-color: #f9f9f9;
      border-left: 4px solid #cc0000;
      border-radius: 4px;
    }
    .error-section-title {
      font-weight: bold;
      font-size: 14px;
      color: #333;
      margin-bottom: 8px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .error-section-content {
      font-size: 13px;
      color: #555;
      line-height: 1.6;
    }
    .error-message {
      font-size: 15px;
      color: #333;
      line-height: 1.6;
      margin: 15px 0;
    }
    .resolution-steps {
      background-color: #eef7ff;
      border-left: 4px solid #0066cc;
      padding: 15px;
      margin: 20px 0;
      border-radius: 4px;
    }
    .resolution-steps .error-section-title {
      color: #0066cc;
      border-left-color: #0066cc;
    }
    .resolution-steps ul {
      margin: 10px 0;
      padding-left: 25px;
    }
    .resolution-steps li {
      margin: 8px 0;
      line-height: 1.5;
    }
    .transaction-id {
      font-family: 'Courier New', monospace;
      background-color: #f0f0f0;
      padding: 2px 6px;
      border-radius: 3px;
      font-size: 12px;
      color: #666;
    }
    .support-section {
      background-color: #fffbf0;
      border: 1px solid #ffd700;
      border-radius: 4px;
      padding: 15px;
      margin: 20px 0;
    }
    .support-section-title {
      font-weight: bold;
      font-size: 13px;
      color: #b8860b;
      margin-bottom: 8px;
    }
    .support-section-content {
      font-size: 12px;
      color: #666;
    }
    .doc-link {
      display: inline-block;
      background-color: #0066cc;
      color: #ffffff;
      text-decoration: none;
      padding: 8px 16px;
      border-radius: 4px;
      font-size: 13px;
      margin-top: 10px;
      transition: background-color 0.2s;
    }
    .doc-link:hover {
      background-color: #0052a3;
    }
    .button-container {
      margin-top: 30px;
      text-align: center;
    }
    .submit-button {
      background-color: #CCCCCC;
      border: 1px solid #000000;
      font-family: Verdana, Arial;
      font-size: 12px;
      font-weight: bold;
      padding: 8px 24px;
      cursor: pointer;
      border-radius: 4px;
      transition: background-color 0.2s;
    }
    .submit-button:hover {
      background-color: #BBBBBB;
    }
    .timestamp {
      font-size: 11px;
      color: #999;
      text-align: right;
      margin-top: 20px;
    }
  </style>
</head>
<body>

<!-- Header Banner -->
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="header-banner">
	<tr>
		<td><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="InterWeave IDE" align="left" width="100%" height="94"/></td>
	</tr>
</table>

<!-- Error Content -->
<div class="container">
	<div class="error-box">
		<!-- Error Header -->
		<div class="error-header">
			<div class="error-icon">&#9888;</div>
			<div class="error-title">
				<h1>Error Occurred</h1>
				<% if (errorCode != null && errorCode.trim().length() > 0) { %>
					<span class="error-code">Error Code: <%= HtmlEncoder.encode(errorCode) %></span>
				<% } %>
			</div>
		</div>

		<!-- Error Message -->
		<div class="error-message">
			<%= HtmlEncoder.encode(errorText) %>
		</div>

		<!-- Error Details Sections -->
		<% if (errorComponent != null && errorComponent.trim().length() > 0) { %>
		<div class="error-section">
			<div class="error-section-title">Affected Component</div>
			<div class="error-section-content"><%= HtmlEncoder.encode(errorComponent) %></div>
		</div>
		<% } %>

		<% if (errorCause != null && errorCause.trim().length() > 0) { %>
		<div class="error-section">
			<div class="error-section-title">Cause</div>
			<div class="error-section-content"><%= HtmlEncoder.encode(errorCause) %></div>
		</div>
		<% } %>

		<!-- Resolution Steps -->
		<% if (errorResolution != null && errorResolution.trim().length() > 0) { %>
		<div class="resolution-steps">
			<div class="error-section-title">&#128161; How to Resolve</div>
			<div class="error-section-content">
				<%
				// Format resolution steps - convert line breaks to list items if needed
				String formattedResolution = errorResolution;
				if (errorResolution.contains("\n") && !errorResolution.contains("<")) {
					// Plain text with line breaks - convert to list
					String[] steps = errorResolution.split("\n");
					out.println("<ul>");
					for (String step : steps) {
						if (step.trim().length() > 0) {
							// Remove leading numbers/bullets if present
							String cleanStep = step.trim().replaceAll("^[0-9]+\\.\\s*", "").replaceAll("^[-*]\\s*", "");
							out.println("<li>" + HtmlEncoder.encode(cleanStep) + "</li>");
						}
					}
					out.println("</ul>");
				} else {
					// Already formatted or single line
					out.println(HtmlEncoder.encode(formattedResolution));
				}
				%>
			</div>
		</div>
		<% } %>

		<!-- Documentation Link -->
		<% if (errorDocLink != null && errorDocLink.trim().length() > 0) { %>
		<div style="text-align: center;">
			<a href="<%= HtmlEncoder.encode(errorDocLink) %>" class="doc-link" target="_blank">&#128214; View Documentation</a>
		</div>
		<% } %>

		<!-- Support Section -->
		<div class="support-section">
			<div class="support-section-title">&#128172; Need Help?</div>
			<div class="support-section-content">
				If you continue to experience issues, please contact support and provide this transaction ID:
				<span class="transaction-id"><%= HtmlEncoder.encode(transactionId) %></span>
			</div>
		</div>

		<!-- Return Button -->
		<form <%= (target==null)?"":("target=\"" + HtmlEncoder.encode(target) + "\"")%> method="post" action='<%= HtmlEncoder.encode(request.getParameter("ErrorMessageReturn")) + brandSol%>'>
			<div class="button-container">
				<input type="submit" name="submit" value="<%= HtmlEncoder.encode(button)%>" class="submit-button"/>
			</div>
		</form>

		<!-- Timestamp -->
		<div class="timestamp">
			<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new java.util.Date()) %>
		</div>
	</div>
</div>

</body>
</html>
