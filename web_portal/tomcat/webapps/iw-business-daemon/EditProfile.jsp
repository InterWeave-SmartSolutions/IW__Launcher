<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
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

boolean li = ConfigContext.isUserLoggedIn();
ConfigContext.setUserLoggedIn(false);
String currentProfileName = request.getParameter("CurrentProfile");
String oldEmail = request.getParameter("Email");
if(oldEmail==null) oldEmail = "";
String firstName = "";
String lastName = "";
String company = "";
String title = "";
String email = "";
Hashtable att = null;
TransactionThread tt = null;
if(currentProfileName!=null){
    att = ConfigContext.getRequestUser().getTransactionThreads();
    if(att!=null){
        tt = (TransactionThread)(att.get(currentProfileName));
        if(tt!=null){
            firstName = tt.getFirstName();
            lastName = tt.getLastName();
            company = tt.getCompany();
            title = tt.getTitle();
            email = oldEmail;
        }
    }
}
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit User Profile</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<div class="portal-page">

  <div class="portal-header">
    <h1>Edit Profile</h1>
    <div class="portal-header-links">
      <a href='<%= "monitoring/Dashboard.jsp" + brandSol1%>' class="portal-link">Monitoring</a>
      <a href='<%= "IWLogin.jsp" + brandSol1%>' class="portal-link">Logout</a>
      <a href='http://interweave.biz' class="portal-link-muted" target="_blank">InterWeave</a>
    </div>
  </div>

  <a href="/iw-portal/profile" class="portal-modern-link" style="margin-bottom:1.5rem;">
    Open in modern portal &rarr;
  </a>

  <!-- Load Profile Section -->
  <div class="portal-section">
    <h2 class="portal-section-title">Load Profile</h2>
    <form action="EditProfileServlet" method="post">
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">E-mail</label>
          <input type="text" name="Email" maxlength="255" class="portal-input" value='<%= oldEmail%>' <%= li?"disabled":""%> placeholder="email@company.com"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Password</label>
          <input type="password" name="Password" maxlength="255" class="portal-input" <%= li?"disabled":""%> placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;"/>
        </div>
      </div>
      <button type="submit" name="submit" value="Load Profile" class="portal-btn portal-btn-outline" <%= li?"disabled":""%>>Load Profile</button>
    </form>
  </div>

  <!-- Edit Profile Section -->
  <div class="portal-section">
    <h2 class="portal-section-title">Profile Details</h2>
    <form action="SaveProfileServlet" method="post">
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">First Name</label>
          <input type="text" name="FirstName" maxlength="45" class="portal-input" value='<%= firstName%>' <%= (!li)?"disabled":""%>/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Last Name</label>
          <input type="text" name="LastName" maxlength="45" class="portal-input" value='<%= lastName%>' <%= (!li)?"disabled":""%>/>
        </div>
      </div>
      <div class="portal-field">
        <label class="portal-label">Company / Organization</label>
        <input type="text" name="CompanyOrganization" maxlength="255" class="portal-input" value='<%= company%>' <%= (!li)?"disabled":""%>/>
      </div>
      <div class="portal-field">
        <label class="portal-label">Title</label>
        <input type="text" name="Title" maxlength="255" class="portal-input" value='<%= title%>' <%= (!li)?"disabled":""%> placeholder="e.g. Integration Architect"/>
      </div>
      <div class="portal-field">
        <label class="portal-label">E-mail</label>
        <input type="text" name="Email" maxlength="255" class="portal-input" value='<%= email%>' <%= (!li)?"disabled":""%>/>
      </div>
      <button type="submit" name="submit" value="Save Profile" class="portal-btn portal-btn-primary" <%= (!li)?"disabled":""%>>Save Profile</button>
    </form>
  </div>

</div>
</body>
</html>
