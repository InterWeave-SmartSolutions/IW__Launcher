<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IW_IDE Secure Portal Login</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<%
String email = request.getParameter("Email");
if(email==null) email = "";
String errorMsg = request.getParameter("error");
String errorCode = request.getParameter("errorCode");
String successMsg = request.getParameter("success");
String brand = request.getParameter("PortalBrand");
if(brand==null) brand="";
String solutions = request.getParameter("PortalSolutions");
if(solutions==null) solutions="";
String brandSol = "";
if (brand != null && brand.length() > 0) brandSol += "&PortalBrand=" + brand;
if (solutions != null && solutions.length() > 0) brandSol += "&PortalSolutions=" + solutions;
String brandSol1 = "";
if (brand != null && brand.length() > 0) brandSol1 += "?PortalBrand=" + brand;
if (solutions != null && solutions.length() > 0)
    brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + solutions;
%>

<%!
String getErrorHelp(String code) {
    if (code == null) return null;
    if ("AUTH001".equals(code))
        return "&#8226; Verify your email address and password are correct<br>&#8226; Passwords are case-sensitive<br>&#8226; If you've forgotten your password, use \"Change User Password\" below";
    if ("AUTH002".equals(code))
        return "&#8226; Contact your company administrator to activate your account<br>&#8226; Your account may need reactivation";
    if ("AUTH003".equals(code))
        return "&#8226; Contact your company administrator<br>&#8226; Your company's subscription may have expired";
    if ("DB001".equals(code))
        return "&#8226; Please try again in a few moments<br>&#8226; The system may be undergoing maintenance";
    if ("VALIDATION001".equals(code))
        return "&#8226; Both email and password are required";
    return null;
}
%>
<%
String errorHelp = errorCode != null ? getErrorHelp(errorCode) : null;
%>

<div class="portal-center">
  <div class="portal-split">

    <!-- Left panel: branding -->
    <div class="portal-card">
      <div class="portal-brand">
        <div class="portal-brand-icon"></div>
        <div>
          <p class="portal-brand-text">InterWeave</p>
          <h1 class="portal-brand-name">Integration Platform</h1>
        </div>
      </div>
      <hr class="portal-divider"/>
      <div class="portal-feature-box">
        <h2>Enterprise Data Integration</h2>
        <p>Monitor transactions, manage configurations, and control your integration workflows.</p>
        <div class="portal-pills">
          <span class="portal-pill"><span class="dot"></span> Monitoring Dashboard</span>
          <span class="portal-pill"><span class="dot"></span> Session-based Auth</span>
          <span class="portal-pill"><span class="dot"></span> Classic View</span>
        </div>
      </div>
      <hr class="portal-divider"/>
      <div style="font-size:0.75rem; color:var(--fg-muted);">
        <p style="margin:0 0 0.5rem 0;"><strong>Quick links</strong></p>
        <div class="portal-links-row">
          <a href="<%= "Registration.jsp" + brandSol1%>" class="portal-link">Register User</a>
          <a href="<%= "CompanyRegistration.jsp" + brandSol1%>" class="portal-link">Register Company</a>
        </div>
        <div class="portal-links-row" style="margin-top:0.5rem;">
          <a href="ChangePassword.jsp" class="portal-link-muted">Change User Password</a>
          <a href="<%= "ChangeCompanyPassword.jsp" + brandSol1%>" class="portal-link-muted">Change Company Password</a>
        </div>
        <div class="portal-links-row" style="margin-top:0.5rem;">
          <a href="<%= "EditProfile.jsp" + brandSol1%>" class="portal-link-muted">Edit User Profile</a>
          <a href="<%= "EditCompanyProfile.jsp" + brandSol1%>" class="portal-link-muted">Edit Company Profile</a>
        </div>
      </div>
    </div>

    <!-- Right panel: login form -->
    <div class="portal-card">
      <h1 class="portal-title">Log in</h1>
      <p class="portal-subtitle">Use your InterWeave credentials.</p>
      <hr class="portal-divider"/>

      <% if(errorMsg != null && !errorMsg.isEmpty()) { %>
      <div class="portal-alert portal-alert-error">
        <% if(errorCode != null && !errorCode.isEmpty()) { %>
          <span class="portal-alert-code">[<%= errorCode %>]</span>
        <% } %>
        <strong>Login Error</strong><br/>
        <%= errorMsg %>
        <% if(errorHelp != null) { %>
        <div class="portal-alert-help"><%= errorHelp %></div>
        <% } %>
      </div>
      <% } %>

      <% if(successMsg != null && !successMsg.isEmpty()) { %>
      <div class="portal-alert portal-alert-success"><%= successMsg %></div>
      <% } %>

      <div class="portal-demo-banner">
        <div>
          <strong>Demo accounts:</strong>
          <p>Admin: admin@sample.com / admin123</p>
          <p>User: demo@sample.com / demo123</p>
        </div>
      </div>

      <form action="LoginServlet" method="post">
        <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
        <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

        <div class="portal-field">
          <label class="portal-label">Email</label>
          <input name="Email" type="text" maxlength="255" class="portal-input" value='<%= email%>' placeholder="email@company.com" autocomplete="email"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Password</label>
          <input name="Password" type="password" maxlength="255" class="portal-input" placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;" autocomplete="current-password"/>
        </div>

        <button type="submit" name="submit" value="Login" class="portal-btn portal-btn-primary portal-btn-full">Continue</button>
      </form>

      <hr class="portal-divider"/>
      <a href="/iw-portal/login" class="portal-modern-link">
        Try the modern portal &rarr;
      </a>
    </div>

  </div>
</div>

</body>
</html>
