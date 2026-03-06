<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String brand = request.getParameter("PortalBrand");
if(brand==null) brand="";
String solutions = request.getParameter("PortalSolutions");
if(solutions==null) solutions="";
String brandSol1 = "";
if (brand != null && brand.length() > 0) brandSol1 += "?PortalBrand=" + brand;
if (solutions != null && solutions.length() > 0)
    brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + solutions;
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Change Company Password</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<div class="portal-page">

  <div class="portal-header">
    <h1>Change Company Password</h1>
    <div class="portal-header-links">
      <a href='<%= "IWLogin.jsp" + brandSol1%>' class="portal-link">Back to Login</a>
      <a href='http://interweave.biz' class="portal-link-muted" target="_blank">InterWeave</a>
    </div>
  </div>

  <div class="portal-section">
    <form action="ChangeCompanyPasswordServlet" method="post">
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

      <div class="portal-field">
        <label class="portal-label">Company</label>
        <input type="text" name="Company" maxlength="255" class="portal-input" placeholder="Company name"/>
      </div>
      <div class="portal-field">
        <label class="portal-label">Administrator's E-mail</label>
        <input type="text" name="Email" maxlength="255" class="portal-input" placeholder="admin@company.com" autocomplete="email"/>
      </div>
      <div class="portal-field">
        <label class="portal-label">Current Password</label>
        <input type="password" name="OldPassword" maxlength="255" class="portal-input" placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">New Password</label>
          <input type="password" name="NewPassword" maxlength="255" class="portal-input"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Confirm New Password</label>
          <input type="password" name="ConfirmNewPassword" maxlength="255" class="portal-input"/>
        </div>
      </div>
      <button type="submit" name="submit" value="Change Company Password" class="portal-btn portal-btn-primary">Change Company Password</button>
    </form>
  </div>

</div>
</body>
</html>
