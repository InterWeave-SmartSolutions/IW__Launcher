<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>IW Integration Manager Admin Configuration Page</title>
</head>
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
if(ConfigContext.isAdminLoggedIn()){%>
<FRAMESET ROWS="70%,30%">
<frame src="<%= "BDConfiguratorA.jsp" + brandSol1%>" name="main">
<frame src="Stub.html" name="data">
<%}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="Your session has been lost. Please refresh your browser. If error persists please click Reset button below."/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
	<jsp:param name="ButtonLable" value="Reset"/>
</jsp:forward>
<%}%>
<noframes>
<body>
Frames must be supported by the browser!
</body>
</noframes>
</FRAMESET>