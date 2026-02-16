<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
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
String env2Con = request.getParameter("Env2Con");
if(env2Con==null){
env2Con="COM";
}
String e2C = "&Env2Con=" + env2Con;
String currentProfileName = request.getParameter("CurrentProfile");
   String refreshValue = request.getParameter("RefreshValue");%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>IW Integration Manager Configuration Page</title>
</head>
<%if(ConfigContext.isAdminLoggedIn() && (!ConfigContext.isHosted() || (currentProfileName!=null))){%>
<FRAMESET ROWS="70%,30%">
<frame src='<%= "BDConfigurator.jsp" + (ConfigContext.isHosted()?("?CurrentProfile=" + currentProfileName + brandSol + e2C):brandSol1)%>' name="main">
<frame name="data">
<%}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value='<%= (currentProfileName==null)?"Your profile is lost. Please press Reset below and login again":"Your session has been lost. Please refresh your browser. If error persists please click Reset button below."%>'/>
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