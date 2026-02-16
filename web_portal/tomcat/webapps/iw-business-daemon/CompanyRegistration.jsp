<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%
String brand = request.getParameter("PortalBrand");
if(brand==null){
brand="";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
solutions="";
}
String brandSol = "";
if (brand != null && brand.length() > 0) {
	brandSol += "&PortalBrand=" + brand;
}
if (solutions != null && solutions.length() > 0) {
	brandSol += "&PortalSolutions=" + solutions;
}
String brandSol1 = "";
if (brand != null && brand.length() > 0) {
	brandSol1 += "?PortalBrand=" + brand;
}
if (solutions != null && solutions.length() > 0) {
	brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + solutions;
}
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Company Registration</title><style>
<!--
.labels
   {
   color: black; font-family: Verdana; font-size: 12pt; font-style: normal; font-weight: bold;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 10pt; font-style: normal; font-weight: normal;
   }
-->
</style>
</head>
<body>
<table border="0" cellpadding="5" width="100%">
	<tr>
		<td colspan="3"><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Company
		Registration</span><br/></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<form action="CompanyRegistrationServlet" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

<table border="0" cellpadding="5" width="100%" class="labels">
	<tr>
		<td><span class="table">Company/Organization</span></td>
		<td>
			<span class="table"><input type="text" name="CompanyOrganization" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Administrator's E-mail</span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table"/></span>
		</td>
	</tr>
	<tr align="left" valign="middle">
		<td><span class="table">Administrator's First Name</span></td>
		<td>
			<span class="table"><input type="text" name="FirstName" maxlength="45" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Administrator's Last Name</span></td>
		<td>
			<span class="table"><input type="text" name="LastName" maxlength="45" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Password</span></td>
		<td>
			<span class="table"><input type="password" name="Password" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Confirm Password</span></td>
		<td>
			<span class="table"><input type="password" name="ConfirmPassword" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Clone Configuration from Company/Organization</span></td>
		<td>
			<span class="table"><input type="text" name="CompanyOrganizationClone" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Source Company Administrator's E-mail</span></td>
		<td>
			<span class="table"><input type="text" name="EmailClone" maxlength="255" class="table"/></span>
		</td>
	</tr>
	<tr>
		<td><span class="table">Source Company Password</span></td>
		<td>
			<span class="table"><input type="password" name="PasswordClone" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Solution type</span></td>
		<td>
			<span class="table">
			<select name="SolutionType" size="1">
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
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Interweave Authorization Token</span></td>
		<td>
			<span class="table"><input type="password" name="IWAuthToken" maxlength="255" class="table"/>
		</span></td>
	</tr>
</table>
	<p>
		<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
	</form>

</body>
</html>