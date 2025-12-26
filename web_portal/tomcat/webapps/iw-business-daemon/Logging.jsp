<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>

<%String url = request.getParameter("TCURL");
if(url==null){
url = "";
}
String query = request.getParameter("__LOG_QUERY_ID__");
if(query==null){
query="";
}
String profile = request.getParameter("CurrentProfile");
if(profile==null){
profile="";
}
String hv = String.valueOf((query + profile).hashCode());%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Profile Login Page</title><style>
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
<body onload="top.location.href='<%= url + "?__LOG_QUERY_ID__=" + query + "&CurrentProfile=" + profile + "&HV=" + hv%>'"/>
</html>