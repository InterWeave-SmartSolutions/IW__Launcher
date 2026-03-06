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
  <title>Change User Password</title><style>
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
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Change
		User Password</span><br/></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Back to Login</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<form action="ChangePasswordServlet" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>

<table border="0" cellpadding="5" width="100%" class="labels">
	<tr>
		<td width="25%"><span class="table">E-mail</span></td>
		<td>
			<span class="table"><input type="text" name="Email" maxlength="255" class="table"/></span>
		</td>
	</tr>
	<tr>
		<td width="25%"><span class="table">Current Password</span></td>
		<td>
			<span class="table"><input type="password" name="OldPassword" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td width="25%"><span class="table">New Password</span></td>
		<td>
			<span class="table"><input type="password" name="NewPassword" maxlength="255" class="table"/>
		</span></td>
	</tr>
	<tr>
		<td width="25%"><span class="table">Confirm New Password</span></td>
		<td>
			<span class="table"><input type="password" name="ConfirmNewPassword" maxlength="255" class="table"/>
		</span></td>
	</tr>
</table>
	<p>
		<input type="submit" name="submit" value="Change Password" class="labels"/>
	</p>
	</form>

</body>
</html>
