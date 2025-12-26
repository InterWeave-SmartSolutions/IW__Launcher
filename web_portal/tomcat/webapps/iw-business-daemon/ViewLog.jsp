<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Log Viewer</title>
  <style>
<!--
.labels
   {
   color: black; font-family: Verdana; font-size: 10pt; font-style: normal; font-weight: bold;
   }
.tablelabels
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: bold;
   }
.tablesmall
   {
   color: black; font-family: Verdana; font-size: 7pt; font-style: normal; font-weight: bold;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: normal;
   }
-->
</style>
</head>
<body class="labels">
<%
String brand = request.getParameter("PortalBrand");
if(brand==null){
brand="";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
solutions="";
}
String profileId = request.getParameter("CurrentProfile");
String adm = request.getParameter("IsAdmin");
boolean isAdmin = (adm!=null)&& (adm.equalsIgnoreCase("true"));
String[] log = ConfigContext.getLogFile(profileId);
%>
	<table border="0" cellpadding="5" class="table" width="100%">
<%for(int i=0; i<log.length; i++){ 
boolean err = (log[i].indexOf("Error")>=0) || (log[i].indexOf("error")>=0) || (log[i].indexOf("ERROR")>=0) || (log[i].indexOf("Exception")>=0) || (log[i].indexOf("exception")>=0) || (log[i].indexOf("EXCEPTION")>=0) || (log[i].indexOf("Failure")>=0) || (log[i].indexOf("failure")>=0) || (log[i].indexOf("FAILURE")>=0);%>
		<tr color=<%= err?"white":"black"%> bgcolor=<%= err?"red":"white"%>>
			<td><%= log[i]%></td>
		</tr>
<%}%>
	</table>
	
</body>
</html>