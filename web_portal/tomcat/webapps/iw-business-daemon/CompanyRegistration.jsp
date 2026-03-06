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
<title>Company Registration</title>
<link rel="stylesheet" href="styles/modern-portal.css"/>
</head>
<body>
<div class="portal-page">

  <div class="portal-header">
    <h1>Company Registration</h1>
    <div class="portal-header-links">
      <a href='<%= "IWLogin.jsp" + brandSol1%>' class="portal-link">Back to Login</a>
      <a href='http://interweave.biz' class="portal-link-muted" target="_blank">InterWeave</a>
    </div>
  </div>

  <div class="portal-section">
    <h2 class="portal-section-title">Company Information</h2>
    <form action="CompanyRegistrationServlet" method="post">
      <input type="hidden" name="PortalBrand" value="<%= brand%>"/>
      <input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

      <div class="portal-field">
        <label class="portal-label">Company / Organization</label>
        <input type="text" name="CompanyOrganization" maxlength="255" class="portal-input" placeholder="Your company name"/>
      </div>
      <div class="portal-field">
        <label class="portal-label">Administrator's E-mail</label>
        <input type="text" name="Email" maxlength="255" class="portal-input" placeholder="admin@company.com" autocomplete="email"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">Administrator's First Name</label>
          <input type="text" name="FirstName" maxlength="45" class="portal-input"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Administrator's Last Name</label>
          <input type="text" name="LastName" maxlength="45" class="portal-input"/>
        </div>
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

      <hr class="portal-divider"/>
      <h2 class="portal-section-title">Clone Configuration (Optional)</h2>
      <p class="portal-field-hint" style="margin-bottom:1rem;">Copy an existing company's configuration to get started faster.</p>

      <div class="portal-field">
        <label class="portal-label">Clone Configuration from Company</label>
        <input type="text" name="CompanyOrganizationClone" maxlength="255" class="portal-input" placeholder="Source company name (optional)"/>
      </div>
      <div class="portal-field-row">
        <div class="portal-field">
          <label class="portal-label">Source Company Admin E-mail</label>
          <input type="text" name="EmailClone" maxlength="255" class="portal-input" placeholder="admin@source-company.com"/>
        </div>
        <div class="portal-field">
          <label class="portal-label">Source Company Password</label>
          <input type="password" name="PasswordClone" maxlength="255" class="portal-input"/>
        </div>
      </div>

      <hr class="portal-divider"/>

      <div class="portal-field">
        <label class="portal-label">Solution Type</label>
        <select name="SolutionType" class="portal-select">
          <%if(solutions.equals("")||solutions.contains("SF2QB;")||solutions.equals("SF2QB")||solutions.contains("SF2QB3")){%><option value="SF2QB3">SalesForce to QuickBooks Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2QB;")||solutions.equals("SF2QB")||solutions.contains("SF2QB2")){%><option value="SF2QB2">SalesForce to QuickBooks Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2QB;")||solutions.equals("SF2QB")||solutions.contains("SF2QB1")){%><option value="SF2QB1">SalesForce to QuickBooks Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2QB;")||solutions.equals("SF2QB")){%><option value="SF2QB">SalesForce to QuickBooks Enterprise</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2CMS")){%><option value="SF2CMS">SalesForce to CMS</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2OMC")){%><option value="SF2OMC">SalesForce to Ecommerce/OMS (Full Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2OMS")){%><option value="SF2OMS">SalesForce to Ecommerce/OMS (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2OMSQB")){%><option value="SF2OMSQB">SalesForce to Nexternal and QB</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2OMSDB")){%><option value="SF2OMSDB">SalesForce to Nexternal and DB</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2DBG")){%><option value="SF2DBG">SalesForce to DB (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2AUTH")){%><option value="SF2AUTH">SalesForce to Authorize.net</option><%}%>
          <%if(solutions.equals("")||solutions.contains("OMS2QB")){%><option value="OMS2QB">Nexternal to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("OMS2ACC")){%><option value="OMS2ACC">Nexternal to Accpac</option><%}%>
          <%if(solutions.equals("")||solutions.contains("OMS2QB")){%><option value="OMS2QB">Ecommerce/OMS to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2OMS2")){%><option value="SF2OMS2">SalesForce to Nexternal 2</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2MAS200")){%><option value="SF2MAS200">Salesforce to MAS</option><%}%>
          <%if(solutions.equals("")||solutions.contains("ORA2QB")){%><option value="ORA2QB">Oracle Fusion to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("MSDCRM2QB")){%><option value="MSDCRM2QB">Microsoft Dynamics CRM to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("PPOL2QB")){%><option value="PPOL2QB">PPOL to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("AR2QB")){%><option value="AR2QB">Aria to QuickBooks</option><%}%>
          <%if(solutions.equals("")||solutions.contains("AR2NS")){%><option value="AR2NS">Aria to NetSuite</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SUG2QB;")||solutions.equals("SUG2QB")||solutions.contains("SUG2QB3")){%><option value="SUG2QB3">Sugar CRM to QuickBooks Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SUG2QB;")||solutions.equals("SUG2QB")||solutions.contains("SUG2QB2")){%><option value="SUG2QB2">Sugar CRM to QuickBooks Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SUG2QB;")||solutions.equals("SUG2QB")||solutions.contains("SUG2QB1")){%><option value="SUG2QB1">Sugar CRM to QuickBooks Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SUG2QB;")||solutions.equals("SUG2QB")){%><option value="SUG2QB">Sugar CRM to QuickBooks Enterprise</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SUG2DBG")){%><option value="SUG2DBG">Sugar CRM to DB (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2PGG")){%><option value="SF2PGG">SalesForce to Payment Gateway (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("DB2QBG")){%><option value="DB2QBG">DB to QB (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("DB2FSG")){%><option value="DB2FSG">DB to Financial System (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2EGG")){%><option value="CRM2EGG">CRM to Email Gateway (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2PGG")){%><option value="CRM2PGG">CRM to Payment Gateway (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2DBG")){%><option value="CRM2DBG">CRM to DB (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2NS;")||solutions.equals("SF2NS")||solutions.contains("SF2NS3")){%><option value="SF2NS3">SalesForce to NetSuite Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2NS;")||solutions.equals("SF2NS")||solutions.contains("SF2NS2")){%><option value="SF2NS2">SalesForce to NetSuite Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2NS;")||solutions.equals("SF2NS")||solutions.contains("SF2NS1")){%><option value="SF2NS1">SalesForce to NetSuite Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("SF2NS;")||solutions.equals("SF2NS")){%><option value="SF2NS">SalesForce to NetSuite Enterprise</option><%}%>
          <%if(solutions.equals("")||solutions.equals("CRM2NS")){%><option value="CRM2NS">CRM to NetSuite (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.equals("CRM2OMC")){%><option value="CRM2OMC">CRM to Ecommerce/OMS (Generic)</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2PT;")||solutions.equals("CRM2PT")||solutions.contains("CRM2PT3")){%><option value="CRM2PT3">CRM to Sage Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2PT;")||solutions.equals("CRM2PT")||solutions.contains("CRM2PT2")){%><option value="CRM2PT2">CRM to Sage Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2PT;")||solutions.equals("CRM2PT")||solutions.contains("CRM2PT1")){%><option value="CRM2PT1">CRM to Sage Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2PT;")||solutions.equals("CRM2PT")){%><option value="CRM2PT">CRM to Sage Enterprise</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2GP;")||solutions.equals("CRM2GP")||solutions.contains("CRM2GP3")){%><option value="CRM2GP3">CRM to MS GP Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2GP;")||solutions.equals("CRM2GP")||solutions.contains("CRM2GP2")){%><option value="CRM2GP2">CRM to MS GP Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2GP;")||solutions.equals("CRM2GP")||solutions.contains("CRM2GP1")){%><option value="CRM2GP1">CRM to MS GP Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2GP;")||solutions.equals("CRM2GP")){%><option value="CRM2GP">CRM to MS GP Enterprise</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2QB;")||solutions.equals("CRM2QB")||solutions.contains("CRM2QB3")){%><option value="CRM2QB3">CRM to QuickBooks Professional</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2QB;")||solutions.equals("CRM2QB")||solutions.contains("CRM2QB2")){%><option value="CRM2QB2">CRM to QuickBooks Premier</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2QB;")||solutions.equals("CRM2QB")||solutions.contains("CRM2QB1")){%><option value="CRM2QB1">CRM to QuickBooks Small Business</option><%}%>
          <%if(solutions.equals("")||solutions.contains("CRM2QB;")||solutions.equals("CRM2QB")){%><option value="CRM2QB">CRM to QuickBooks Enterprise</option><%}%>
        </select>
      </div>

      <div class="portal-field">
        <label class="portal-label">InterWeave Authorization Token</label>
        <input type="password" name="IWAuthToken" maxlength="255" class="portal-input" placeholder="Provided by InterWeave"/>
        <p class="portal-field-hint">Required to register a new company.</p>
      </div>

      <button type="submit" name="submit" value="Next" class="portal-btn portal-btn-primary portal-btn-full">Next</button>
    </form>
  </div>

</div>
</body>
</html>
