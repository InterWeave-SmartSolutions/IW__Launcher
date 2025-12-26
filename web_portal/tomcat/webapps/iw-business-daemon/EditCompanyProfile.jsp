<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
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
boolean li = ConfigContext.isUserLoggedIn();
ConfigContext.setUserLoggedIn(false);
String currentProfileName = request.getParameter("CurrentProfile");
String oldEmail = request.getParameter("Email");
if(oldEmail==null){
oldEmail = "";
}
String oldCompany = request.getParameter("Company");
if(oldCompany==null){
oldCompany = "";
}
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
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Company Registration Page</title><style>
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
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Edit
		Company Profile</span><br/></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<form action="EditCompanyProfileServlet" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

<table border="0" cellpadding="5" width="100%" class="labels">
	<tr>
		<td width="25%"><span class="table">Company</span></td>
		<td>
			<span class="table"><input type="text" name="Company" maxlength="255" class="table" value='<%= oldCompany%>' <%= li?"disabled":""%>/></span>
		</td>
	</tr>
	<tr>
		<td width="25%"><span class="table">Administrator's e-mail</span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table" value='<%= oldEmail%>' <%= li?"disabled":""%>/></span>
		</td>
	</tr>
	<tr>
		<td width="25%"><span class="table">Password</span></td>
		<td>
			<span class="table"><input type="password" name="Password" maxlength="255" class="table" <%= li?"disabled":""%>/>
		</span></td>
	</tr>
	
</table>
	<p>
		<input type="submit" name="submit" value="Load Company Profile" class="labels" <%= li?"disabled":""%>/>
	</p>
	</form>
<form action="SaveCompanyProfileServlet" method="post">
<input type="hidden" name="OldProfile" value='<%= currentProfileName%>'/>
<input type="hidden" name="Type" value='<%= solution%>'/>
<input type="hidden" name="CompanyOrganization" value='<%= company%>'/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<table border="0" cellpadding="5" width="100%" class="labels">
	<tr>
		<td><span class="table">Company/Organization</span></td>
		<td>
			<span class="table"><%= company%></span></td>
	</tr>
	<tr align="left" valign="middle">
		<td width="25%"><span class="table">First Name</span></td>
		<td>
			<span class="table"><input type="text" name="FirstName" maxlength="45" class="table" value='<%= firstName%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Last Name</span></td>
		<td>
			<span class="table"><input type="text" name="LastName" maxlength="45" class="table" value='<%= lastName%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Administrator's e-mail</span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table" value='<%= email%>' <%= (!li)?"disabled":""%>/></span>
		</td>
	</tr>
	<tr>
		<td><span class="table">Solution type</span></td>
		<td><span class="table"><%= solution%></span></td>
	</tr>
</table>
	<p>
		<input type="submit" name="submit" value="Next" class="labels" <%= (!li)?"disabled":""%>/>
	</p>
	</form>

</body>
</html>