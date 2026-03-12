<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.interweave.web.HtmlEncoder" %>
<% 
boolean li = ConfigContext.isUserLoggedIn();
ConfigContext.setUserLoggedIn(false);
String currentProfileName = request.getParameter("CurrentProfile");
String UID = request.getParameter("UID");
if(UID == null) UID = "";
String encodedUID = java.net.URLEncoder.encode(UID, "UTF-8");
boolean showLeads = (currentProfileName!=null)?(currentProfileName.equalsIgnoreCase("show")):false;
String getNewLead = request.getParameter("GetLead");
boolean getLead = (getNewLead!=null)?(getNewLead.equalsIgnoreCase("show")):false;
String oldEmail = request.getParameter("Email");
if(oldEmail==null){
oldEmail = "";
}
String oldCompany = request.getParameter("Company");
if(oldCompany==null){
oldCompany = "";
}
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Lead Processing</title><style>
<!--
.labels
   {
   color: black; font-family: Verdana; font-size: 12pt; font-style: normal; font-weight: bold;
   }
.labelssmall
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: bold;
   }
.labelssmallu
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: bold; text-decoration: underline;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 10pt; font-style: normal; font-weight: normal;
   }
.tablesmall
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: normal;
   }
.linkbutton
   {
   background-color: silver; border: thick solid black; color: black; font-family: Verdana; font-size: 12pt; font-style: normal; font-weight: bold; text-decoration: underline
   }
-->
</style>
</head>
<body>
<input type="hidden" name="OldProfile" value='<%= HtmlEncoder.encode(currentProfileName)%>'/>
<p class="labels">Lead Detail</p>
	<table border="0" cellpadding="0" class="tablesmall" width="100%">
	<tr>
		<td>Name</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>Address</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>City</td>
		<td></td>
		<td>Lead Group</td>
		<td></td>
	</tr>
	<tr>
		<td>State</td>
		<td></td>
		<td>Lead Id</td>
		<td></td>
	</tr>
	<tr>
		<td>Zip</td>
		<td></td>
		<td>Lead Status</td>
		<td></td>
	</tr>
	<tr>
		<td>Phone1</td>
		<td></td>
		<td>Disposition</td>
		<td></td>
	</tr>
	<tr>
		<td>Phone2</td>
		<td></td>
		<td>Sub-Disposition</td>
		<td></td>
	</tr>
</table>
<p class="labels">Notes and Attachments</p>
	<table border="0" cellpadding="5" class="tablesmall" width="100%">
		<tr class="labelssmall">
			<td>Title</td>
		</tr>
		<tr>
			<td></td>
		</tr>
	</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
	<p class="labels">Current Rep Ranking: Platinum</p>
	<table border="1" cellpadding="0" class="labelssmall" width="40%">
		<tr>
			<td>
				<table border="0" cellpadding="5" width="100%" class="labelssmall">
					<tr class="labelssmallu">
						<td>Opportunity</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
			<td>
				<table border="0" cellpadding="5" width="100%" class="labelssmall">
					<tr class="labelssmallu">
						<td>Lead</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="5" width="100%" class="labelssmall">
					<tr class="labelssmallu">
						<td>Quota</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table></td>
			<td>
				<table border="0" cellpadding="5" width="100%" class="labelssmall">
					<tr>
						<td class="labelssmallu">Contacted</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table></td>
		</tr>
	</table>
	<table border="1" cellpadding="5" width="100%" class="labels">
	<tr class="labels">
		<td width="25%">Lead Id</td>
		<td>Name</td>
		<td>Phone</td>
		<td>Lead Group</td>
		<td>Medium</td>
		
		
	</tr>
	<%if(getLead){%>
	<tr valign="middle" class="table">
		<td>12345</td>
		<td>John Doe</td>
		<td>(123)456-7890</td>
		<td>Trump Radio</td>
		<td>Radio</td>
	</tr>
	<%}%>	
</table>
		<a href='<%= "http://72.3.142.149:8080/ProsperCMS2SF/transform?__QUERY_ID__=GetNextLeadUI&applicationname=iwtransformationserver&CMSDriver=com.mysql.jdbc.Driver&CMSURL=jdbc:mysql://dcms.prosperlearning.com:3306/tele?zeroDateTimeBehavior=convertToNull&ReturnString=&CMSPassword=DevData1&QueryStartTime=2007-01-10 23:32:02.515&UserId=" + encodedUID + "&CMSUser=dmytro&tranname=GetNextLeadeFromTank"%>' style="text-align: left" class="linkbutton">New Lead</a>
		<a href='<%= "http://72.3.142.149:8080/ProsperCMS2SF/transform?__QUERY_ID__=AcceptNextLeadUI&applicationname=iwtransformationserver&CMSDriver=com.mysql.jdbc.Driver&CMSURL=jdbc:mysql://dcms.prosperlearning.com:3306/tele?zeroDateTimeBehavior=convertToNull&ReturnString=&CMSPassword=DevData1&QueryStartTime=2007-01-10 23:32:02.515&UserId=" + encodedUID + "&CMSUser=dmytro&tranname=GetNextLeadeFromTank"%>' style="text-align: center" class="linkbutton">Contacted (keep)</a>
		<a href='<%= "http://72.3.142.149:8080/ProsperCMS2SF/transform?__QUERY_ID__=DeclineNextLeadUI&applicationname=iwtransformationserver&CMSDriver=com.mysql.jdbc.Driver&CMSURL=jdbc:mysql://dcms.prosperlearning.com:3306/tele?zeroDateTimeBehavior=convertToNull&ReturnString=&CMSPassword=DevData1&QueryStartTime=2007-01-10 23:32:02.515&UserId=" + encodedUID + "&CMSUser=dmytro&tranname=GetNextLeadeFromTank"%>'  style="text-align: right;" class="linkbutton">Return to Lead Pool</a>
	</p></td>
		<td>
			<table border="1" cellpadding="1" width="100%" class="tablesmall">
				<tr class="labelssmall">
					<td>Id</td>
					<td>Name</td>
					<td>Type</td>
				</tr>
				<tr>
					<td>12345</td>
					<td>Jane Doe</td>
					<td>Platinum</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>