<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%String target = request.getParameter("ErrorMessageTarget");
String brand = request.getParameter("PortalBrand");
if(brand==null){
brand="";
}
String button = request.getParameter("ButtonLable");
if(button==null || button.trim().length()==0){
button="OK";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
solutions="";
}
String env2Con = request.getParameter("Env2Con");
if(env2Con==null){
env2Con="COM";
}
String brandSol = "";
if (brand != null && brand.length() > 0) {
	brandSol += "?PortalBrand=" + brand;
}
if (solutions != null && solutions.length() > 0) {
	brandSol += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + solutions;
}%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Error Message Page</title>
</head>
<body>
<table border="0" cellpadding="5" width="100%">
	<tr>
		<td><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/>
		</td>
	</tr>
</table>
<br/>
<br/>
<br/>
<br/>
<br/>
<p style="color: orangered; font-family: Verdana; font-size: 13pt; font-style: italic; font-weight: bold"><%= request.getParameter("ErrorMessageText")%></p>
<p>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<form <%= (target==null)?"":("target=\"" + target + "\"")%> method="post" action='<%= request.getParameter("ErrorMessageReturn") + brandSol%>'>
		<input type="submit" name="submit" value="<%= button%>"/>
	</form>
	</p>
</body>
</html>