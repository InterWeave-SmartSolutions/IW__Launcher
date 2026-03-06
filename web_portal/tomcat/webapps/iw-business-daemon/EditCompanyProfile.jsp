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
String oldCompany = request.getParameter("Company");
if(oldCompany==null) oldCompany = "";
String firstName = "";
String lastName = "";
String company = "";
String solution = "";
String email = "";
Hashtable att = null;
TransactionThread tt = null;
if(currentProfileName!=null){
    att = ConfigContext.getRequestCompany().getTransactionThreads();
    if(att!=null){
        tt = (TransactionThread)(att.get(currentProfileName));
        if(tt!=null){
            firstName = tt.getFirstName();
            lastName = tt.getLastName();
            company = oldCompany;
            solution = tt.getTitle();
            email = oldEmail;
        }
    }
}
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Company Profile</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<div class="portal-page">

  <div class="portal-header">
    <h1>Edit Company Profile</h1>
    <div class="portal-header-links">
      <a href='<%= "monitoring/Dashboard.jsp" + brandSol1%>' class="portal-link">Monitoring</a>
      <a href='<%= "IWLogin.jsp" + brandSol1%>' class="portal-link">Logout</a>
      <a href='http://interweave.biz' class="portal-link-muted" target="_blank">InterWeave</a>
    </div>
  </div>

  <a href="/iw-portal/company" class="portal-modern-link" style="margin-bottom:1.5rem;">
    Open in modern portal &rarr;
  </a>

  <!-- Load Company Profile -->
  <div class="portal-section">
    <h2 class="portal-section-title">Load Company Profile</h2>
    <form action="EditCompanyProfileServlet" method="post">
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
      <div class="portal-field">
        <label class="portal-label">Company</label>
        <input type="text" name="Company" maxlength="255" class="portal-input" value='<%= oldCompany%>' <%= li?"disabled":""%> placeholder="Company name"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">Administrator's E-mail</label>
          <input type="text" name="Email" maxlength="255" class="portal-input" value='<%= oldEmail%>' <%= li?"disabled":""%> placeholder="admin@company.com"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Password</label>
          <input type="password" name="Password" maxlength="255" class="portal-input" <%= li?"disabled":""%> placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;"/>
        </div>
      </div>
      <button type="submit" name="submit" value="Load Company Profile" class="portal-btn portal-btn-outline" <%= li?"disabled":""%>>Load Company Profile</button>
    </form>
  </div>

  <!-- Company Details -->
  <div class="portal-section">
    <h2 class="portal-section-title">Company Details</h2>
    <form action="SaveCompanyProfileServlet" method="post">
      <input type="hidden" name="OldProfile" value='<%= currentProfileName%>'/>
      <input type="hidden" name="Type" value='<%= solution%>'/>
      <input type="hidden" name="CompanyOrganization" value='<%= company%>'/>
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

      <div class="portal-field">
        <label class="portal-label">Company / Organization</label>
        <div class="portal-readonly"><%= company%></div>
      </div>
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
        <label class="portal-label">Administrator's E-mail</label>
        <input type="text" name="Email" maxlength="255" class="portal-input" value='<%= email%>' <%= (!li)?"disabled":""%>/>
      </div>
      <div class="portal-field">
        <label class="portal-label">Solution Type</label>
        <div class="portal-readonly"><%= solution%></div>
      </div>
      <button type="submit" name="submit" value="Next" class="portal-btn portal-btn-primary" <%= (!li)?"disabled":""%>>Next</button>
    </form>
  </div>

</div>
</body>
</html>
