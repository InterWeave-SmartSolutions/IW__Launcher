<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.interweave.web.HtmlEncoder" %>
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
	brandSol += "&PortalBrand=" + java.net.URLEncoder.encode(brand, "UTF-8");
}
if (solutions != null && solutions.length() > 0) {
	brandSol += "&PortalSolutions=" + java.net.URLEncoder.encode(solutions, "UTF-8");
}
String brandSol1 = "";
if (brand != null && brand.length() > 0) {
	brandSol1 += "?PortalBrand=" + java.net.URLEncoder.encode(brand, "UTF-8");
}
if (solutions != null && solutions.length() > 0) {
	brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + java.net.URLEncoder.encode(solutions, "UTF-8");
} 
boolean li = ConfigContext.isUserLoggedIn();
ConfigContext.setUserLoggedIn(false);
String currentProfileName = request.getParameter("CurrentProfile");
String oldEmail = request.getParameter("Email");
if(oldEmail==null){
oldEmail = "";
}
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
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Profile Registration Page</title><style>
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
		<td colspan="4"><img src="<%= HtmlEncoder.encode("images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png")%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Edit
		Profile</span><br/></td><td align="right"><a href='<%= "monitoring/Dashboard.jsp" + brandSol1%>' target="_top" class="labels">Monitoring Dashboard</a></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
	<tr>
		<td colspan="4" style="padding:4px 5px;"><a href="/iw-portal/profile" style="font-family:Arial,sans-serif; font-size:11px; color:#336699;">&#9654; Open in Modern Portal</a></td>
	</tr>
</table>
<form action="EditProfileServlet" method="post">
<input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
<input type="hidden" name="PortalBrand" value="<%= HtmlEncoder.encode(brand)%>"/>
<input type="hidden" name="PortalSolutions" value="<%= HtmlEncoder.encode(solutions)%>"/>

<table border="0" cellpadding="5" width="100%" class="labels">
	<tr>
		<td width="25%"><span class="table">E-mail </span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table" value='<%= HtmlEncoder.encode(oldEmail)%>' <%= li?"disabled":""%>/></span>
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
		<input type="submit" name="submit" value="Load Profile" class="labels" <%= li?"disabled":""%>/>
	</p>
	</form>
<form action="SaveProfileServlet" method="post">
<input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
<table border="0" cellpadding="5" width="100%" class="labels">
	<tr align="left" valign="middle">
		<td width="25%"><span class="table">First Name</span></td>
		<td>
			<span class="table"><input type="text" name="FirstName" maxlength="45" class="table" value='<%= HtmlEncoder.encode(firstName)%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Last Name</span></td>
		<td>
			<span class="table"><input type="text" name="LastName" maxlength="45" class="table" value='<%= HtmlEncoder.encode(lastName)%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Company/Organization</span></td>
		<td>
			<span class="table"><input type="text" name="CompanyOrganization" maxlength="255" class="table" value='<%= HtmlEncoder.encode(company)%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Title</span></td>
		<td>
			<span class="table"><input type="text" name="Title" maxlength="255" class="table" value='<%= HtmlEncoder.encode(title)%>' <%= (!li)?"disabled":""%>/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">E-mail </span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table" value='<%= HtmlEncoder.encode(email)%>' <%= (!li)?"disabled":""%>/></span>
		</td>
	</tr>
</table>
	<p>
		<input type="submit" name="submit" value="Save Profile" class="labels" <%= (!li)?"disabled":""%>/>
	</p>
	</form>

</body>
</html>