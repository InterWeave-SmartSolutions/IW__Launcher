<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%
String currentProfileName = request.getParameter("CurrentProfile");
String oldProfileName = request.getParameter("OldProfile");
String solutionType = request.getParameter("Solution");
String crm = "CRM";
if(solutionType.startsWith("SF")){
crm="SF";
} else if (solutionType.startsWith("AR")){
crm="Aria";
} else if (solutionType.startsWith("SUG")){
crm="Sugar";
} else if (solutionType.startsWith("OMS")){
crm="OMS";
} 
String fs = "Financials";
if (solutionType.indexOf("QB") >= 0){
fs = "QB";
} else if (solutionType.indexOf("NS") >= 0) {
fs = "NetSuite";
} else if (solutionType.indexOf("ACC") >= 0) {
fs = "Accpac";
} else if (solutionType.indexOf("PT") >= 0) {
fs = "Sage";
} else if (solutionType.indexOf("2GP") >= 0) {
fs = "MS GP";
} else if (solutionType.indexOf("2OM") >= 0) {
fs = "OMS";
} 
String objectType = request.getParameter("ObjectType");
String objName = objectType + "SFQBCMap";
Hashtable att = null;
TransactionThread tt = null;
Object cfr = null;
Object cfrn = null;
String sel = null;
boolean edit = false;
if(oldProfileName!=null){
att = ConfigContext.getRequestCompany().getTransactionThreads();
if(att!=null){
tt = (TransactionThread)(att.get(oldProfileName));
if(tt!=null){
String cc = tt.getCompanyConfiguration();
if(cc!=null){
cfr = ConfigContext.getConfigRoot(ConfigContext.unEscape(cc));
edit = true;
}
}
}
}
TransactionThread ptt;
if (oldProfileName == null) {
	ptt = ConfigContext.getCompanyRegistration().getTransactionThreads().get(currentProfileName);
} else {
	ptt = ConfigContext.getUpdateCompany().getTransactionThreads().get(currentProfileName);
}
if (ptt != null){
String cnfg = ptt.getParameters().get("configuration").getParameterValue();
if(cnfg!=null){
cfrn = ConfigContext.getConfigRoot(ConfigContext.unEscape(cnfg + "</SF2QBConfiguration>"));
edit = true;
}
}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Custom Mappings Page</title>
<style>
<!--  
.labels
   {
   color: black; font-family: Verdana; font-size: 12pt; font-style: normal; font-weight: bold;
   } 
.tablelabels
   {
   color: black; font-family: Verdana; font-size: 11pt; font-style: normal; font-weight: bold;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 10pt; font-style: normal; font-weight: normal;
   }
-->
</style>
</head>
<body>

<p><b><font size="4"><span style="color: black; font-family: Verdana; font-size: 14pt; font-style: normal; font-weight: bold">Custom Mappings</span></font></b></p>
<form action="CustomMappings" method="post">
  <input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= oldProfileName %>"/>
  <%}%>	
  <input type="hidden" name="Solution" value="<%= solutionType %>"/>
  <input type="hidden" name="ObjectType" value="<%= objectType %>"/>
	<table border="1" cellpadding="5" width="100%">
		<tr>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 3</span></td>
			<td>
				<input type="text" name=<%= objName + "2"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "2") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 4</span></td>
			<td>
				<input type="text" name=<%= objName + "3"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "3") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 5</span></td>
			<td>
				<input type="text" name=<%= objName + "4"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "4") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 6</span></td>
			<td>
				<input type="text" name=<%= objName + "5"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "5") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 7</span></td>
			<td>
				<input type="text" name=<%= objName + "6"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "6") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 8</span></td>
			<td>
				<input type="text" name=<%= objName + "7"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "7") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 9</span></td>
			<td>
				<input type="text" name=<%= objName + "8"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "8") +"\""):""%> size="37"/>
			</td>
		</tr>
		<tr>
			<td><span class="table"><%= crm%> field to <%= fs%> field custom	mapping 10</span></td>
			<td>
				<input type="text" name=<%= objName + "9"%> class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, objName + "9") +"\""):""%> size="37"/>
			</td>
		</tr>
	</table>


<p>
	<input type="submit" name="submit" value="Submit" class="labels"/> <input type="button" name="close" value="Close" class="labels" onclick="self.close()"/>
	</p></form>
</body>
</html>