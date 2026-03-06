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
  <title>User Registration</title><style>
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
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">User
		Registration</span><br/></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<form action="RegistrationServlet" method="post">
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
		<td><span class="table">E-mail</span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table"/></span>
		</td>
	</tr>
	<tr align="left" valign="middle">
		<td><span class="table">First Name</span></td>
		<td>
			<span class="table"><input type="text" name="FirstName" maxlength="45" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Last Name</span></td>
		<td>
			<span class="table"><input type="text" name="LastName" maxlength="45" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td><span class="table">Title</span></td>
		<td>
			<span class="table"><input type="text" name="Title" maxlength="255" class="table"/>
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
		<td><span class="table">Interweave Authorization Token</span></td>
		<td>
			<span class="table"><input type="password" name="Token" maxlength="255" class="table"/>
		</span></td>
	</tr>
</table>
	<p>
		<input type="submit" name="submit" value="Register" class="labels"/>
	</p>
	</form>

</body>
</html>
