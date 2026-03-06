<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
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
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Registration</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<div class="portal-page">

  <div class="portal-header">
    <h1>User Registration</h1>
    <div class="portal-header-links">
      <a href='<%= "IWLogin.jsp" + brandSol1%>' class="portal-link">Back to Login</a>
      <a href='http://interweave.biz' class="portal-link-muted" target="_blank">InterWeave</a>
    </div>
  </div>

  <div class="portal-section">
    <form action="RegistrationServlet" method="post">
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

      <div class="portal-field">
        <label class="portal-label">Company / Organization</label>
        <input type="text" name="CompanyOrganization" maxlength="255" class="portal-input" placeholder="Your company name"/>
      </div>
      <div class="portal-field">
        <label class="portal-label">E-mail</label>
        <input type="text" name="Email" maxlength="255" class="portal-input" placeholder="email@company.com" autocomplete="email"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">First Name</label>
          <input type="text" name="FirstName" maxlength="45" class="portal-input"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Last Name</label>
          <input type="text" name="LastName" maxlength="45" class="portal-input"/>
        </div>
      </div>
      <div class="portal-field">
        <label class="portal-label">Title</label>
        <input type="text" name="Title" maxlength="255" class="portal-input" placeholder="e.g. Integration Architect"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">Password</label>
          <input type="password" name="Password" maxlength="255" class="portal-input"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Confirm Password</label>
          <input type="password" name="ConfirmPassword" maxlength="255" class="portal-input"/>
        </div>
      </div>
      <div class="portal-field">
        <label class="portal-label">InterWeave Authorization Token</label>
        <input type="password" name="Token" maxlength="255" class="portal-input" placeholder="Provided by your administrator"/>
        <p class="portal-field-hint">Required to link your account to your company.</p>
      </div>
      <button type="submit" name="submit" value="Register" class="portal-btn portal-btn-primary portal-btn-full">Register</button>
    </form>
  </div>

</div>
</body>
</html>
