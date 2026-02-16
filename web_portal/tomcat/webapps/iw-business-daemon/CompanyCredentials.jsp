<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%
int lnmbr = 1;
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
String currentProfileName = request.getParameter("CurrentProfile");
String currentUser = "";
if(currentProfileName!=null){
int clps = currentProfileName.indexOf(":");
currentUser = currentProfileName.substring(clps + 1);}
String navigation = request.getParameter("Navigation");
String solutionType = request.getParameter("Solution");
String crm = "CRM";
if(solutionType.startsWith("SF")){
crm="SF";
} else if (solutionType.startsWith("AR")){
crm="Aria";
} else if (solutionType.startsWith("OMS")){
crm="OMS";
} else if (solutionType.startsWith("SUG")){
crm="Sugar";
} else if (solutionType.startsWith("DB")){
crm="DB";
} else if (solutionType.startsWith("ORA")){
crm="Fusion";
} else if (solutionType.startsWith("MSDCRM")){
crm="MSDCRM";
} else if (solutionType.startsWith("PPOL")){
crm="PPOL";
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
}  else if (solutionType.indexOf("PGG") >= 0) {
fs = "Payment Gateway";
} else if (solutionType.indexOf("DBG") >= 0) {
fs = "Database";
} else if (solutionType.indexOf("2OM") >= 0) {
fs = "OMS";
}  
	int cmpNum = Integer.valueOf(request.getParameter("QBCompFilNum"));
	String srName0 = request.getParameter("SourceName");
	String srName1 = request.getParameter("SourceName1");
	String srName2 = request.getParameter("SourceName2");
	String srName3 = request.getParameter("SourceName3");
	String qbLabel = request.getParameter("DestName");
	String sfName = (fs.equals("Accpac") && crm.equals("OMS"))?fs:((cmpNum==1 && srName1!=null)?srName1:crm);
	String sfUser = (cmpNum==1 && srName1!=null)?"Account":"User";
	String sfPassword = (cmpNum==1 && srName1!=null)?"Key":"Password";  
	String qbCompFile = (cmpNum>1 && srName1==null && qbLabel==null)?"Company File":((qbLabel==null)?"Company File":qbLabel); 
String oldProfileName = request.getParameter("OldProfile");
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
<title>Company Credentials</title>
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
<% 
if(ConfigContext.isAdminLoggedIn()){
ConfigContext.setAdminLoggedIn(false);
if(ConfigContext.isHosted()){
%>
<table border="0" cellpadding="5" width="100%">
	<tr>
		<td colspan="4"><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td class="labels"><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Please
		set credentials for your solution.</span></td><td align="right" class="labels">User: <%= currentUser%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= currentProfileName%> <%= oldProfileName%> <%= solutionType%> <%= crm%> <%= navigation%>-->
<form action="CompanyCredentialsServlet" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
  <input type="hidden" name="CurrentProfile" value="<%= currentProfileName%>"/>
  <input type="hidden" name="QBCompFilNum" value="<%= "" + cmpNum%>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= oldProfileName %>"/><%}%>
  <input type="hidden" name="Solution" value="<%= solutionType %>"/>
  <p><%if(srName0.equals("QB") || solutionType.endsWith("OMC") || crm.equals("Aria") || fs.equals("Payment Gateway") || (fs.equals("Accpac") && crm.equals("OMS")) || (fs.equals("Sage") && crm.equals("CRM"))){%>
	<input type="submit" name="submit" value="Previous" class="labels"/><%}%>
	<input type="submit" name="submit" value="Save and Finish" class="labels"/>
	</p>
	<br/>
	<span class="labels"><%= sfName%> Credentials</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("OMS") || crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("Sugar") || crm.equals("Aria") || crm.equals("DB") || crm.equals("CRM")){if(!crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("Sugar") || crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("Aria") || crm.equals("DB") || crm.equals("CRM") || (crm.equals("OMS") && fs.equals("QB")))?crm:"Accpac"%> <%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM"))?"Personal Domain/Profile":"Integration URI"%></span></td>
			<td>
				<input type="text" name="AccIntURL" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "AccIntURL") +"\""):""%>/>
			</td>
		</tr>
		<%}if((fs.equals("Accpac") && crm.equals("OMS")) || (crm.equals("Aria")) || (crm.equals("PPOL"))){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%=  (crm.equals("Aria"))?"Aria Client Number":((crm.equals("PPOL"))?"PPOL Company Name":"Accpac Integration Company")%></span></td>
			<td>
				<input type="text" name="AccIntComp" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "AccIntComp") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if(fs.equals("NetSuite") && crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Aria authorization key</span></td>
			<td>
				<input type="password" name="ArAuthKey" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ArAuthKey") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= sfName%> Integration <%= sfUser%></span></td>
			<td>
				<input type="text" name="SFIntUsr" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFIntUsr") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= sfName%> Integration <%= sfPassword%></span></td>
			<td>
				<input type="password" name="SFPswd" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFPswd") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Confirm <%= sfName%> Integration <%= sfPassword%></span></td>
			<td>
				<input type="password" name="SFPswdCfrm" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFPswd") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Sandbox/Test Environment</span></td>
			<td>
				<span class="table">
				<select name="SandBoxUsed" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SandBoxUsed"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
	</table>
<%if((cmpNum>1) && (!(fs.equals("Accpac") && crm.equals("OMS"))) &&  (srName1==null || qbLabel!=null)){
  String qbSrName = (srName1==null || cmpNum==1)?srName0:((qbLabel==null)?(srName0 + " and " + srName1):qbLabel);%>
	<br/><span class="labels"><%= qbSrName%> Selection Criteria</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Name of <%= sfName%> Account Field with <%= qbSrName%> Selection Criterion</span></td>
			<td>
				<input type="text" name="AcctQBCompFlSelNm" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "AcctQBCompFlSelNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Name of <%= sfName%> Contact Field with <%= qbSrName%> Selection
			Criterion</span></td>
			<td>
				<input type="text" name="ContSFQBCompFlSelNm" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ContSFQBCompFlSelNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Name of <%= sfName%> Opportunity Field with <%= qbSrName%> Selection Criterion</span></td>
			<td>
				<input type="text" name="OppSFQBCompFlSelNm" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "OppSFQBCompFlSelNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Name of <%= sfName%> Product Field with <%= qbSrName%> Selection Criterion</span></td>
			<td>
				<input type="text" name="PrdSFQBCompFlSelNm" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdSFQBCompFlSelNm") +"\""):""%>/>
			</td>
		</tr>
	</table>
<%}%>
	<br/><span class="labels"><%= (fs.equals("Accpac") && crm.equals("OMS"))?"Default Values and OMS":((srName1==null || cmpNum==1)?srName0:((qbLabel==null)?(srName0 + " and " + srName1):qbLabel))%> Credentials</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%for(int i=0; i<cmpNum; i++){
String srn = null;
		switch (i) {
		case 0:
			srn = srName0;
			break;
		case 1:
			srn = srName1;
			break;
		case 2:
			srn = srName2;
			break;
		case 3:
			srn = srName3;
			break;
		default:
			srn = srName0;
			break;
		}
String srName = (fs.equals("Accpac") && crm.equals("OMS"))?"OMS":((srName1==null)? srName0:srn);
if((cmpNum>1) && (srName1==null || qbLabel!=null)){
%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> <%= (fs.equals("Accpac") && crm.equals("OMS"))?"Prefix":"Selection Criterion"%><%= (cmpNum>1)?(" " + i):""%></span></td>
			<td>
				<input type="text" name='<%= "SFQBCompFlSelVl" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFQBCompFlSelVl" + i) +"\""):""%>/>
			</td>
		</tr>
<%}%>
<%if(fs.equals("Accpac") && crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Inventory Location</span></td>
			<td>
				<input type="text" name='<%= "DefInvLoc" + i%>'<%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefInvLoc" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Miscellaneous Freight Code</span></td>
			<td>
				<input type="text" name='<%= "MiscFrCode" + i%>'<%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "MiscFrCode" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Miscellaneous Discount Code</span></td>
			<td>
				<input type="text" name='<%= "MiscDiscCode" + i%>'<%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "MiscDiscCode" + i) +"\""):""%>/>
			</td>
		</tr>
<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> Integration URI<%= (cmpNum>1)?(" " + i):""%></span></td>
			<td>
				<input type="text" name='<%= "QDSN" + i%>'<%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QDSN" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> Integration User/Id<%= (cmpNum>1)?(" " + i):""%></span></td>
			<td>
				<input type="text" name='<%= "QBIntUsr" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBIntUsr" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> Integration Password/Token<%= (cmpNum>1)?(" " + i):""%></span></td>
			<td>
				<input type="password" name='<%= "QBPswd" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPswd" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Confirm <%= srName%> Integration Password/Token<%= (cmpNum>1)?(" " + i):""%></span></td>
			<td>
				<input type="password" name='<%= "QBPswdCfrm" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPswd" + i) +"\""):""%>/>
			</td>
		</tr><%if(cmpNum>1){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Time Zone Shift <%= " " + i%></span></td>
			<td>
				<input type="text" name='<%= "TimeZone" + i%>' class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "TimeZone" + i) +"\""):"0"%>/>
			</td>
		</tr><%}%>
		<%if(fs.equals("NetSuite")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> Subsidiary Name</span></td>
			<td>
				<input type="text" name='<%= "NSSubsNm" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSSubsNm" + i) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= srName%> Subsidiary Internal ID</span></td>
			<td>
				<input type="text" name='<%= "NSSubsID" + i%>' <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSSubsID" + i) +"\""):""%>/>
			</td>
		</tr>
<%}}%>
	</table><br/>
	<span class="labels">Other Properties</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Version/Edition</span></td>
			<td>
				<span class="table">
				<select name="SFVersion" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFVersion"):"V0"; if(sel.equals("")){sel = "V0";}%>
					<option <%= sel.equals("V0")?"selected=\"selected\"":""%> value="V0">Home</option>
					<option <%= sel.equals("V1")?"selected=\"selected\"":""%> value="V1">Professional</option>
					<option <%= sel.equals("V3")?"selected=\"selected\"":""%> value="V3">Enterprise</option>
					<option <%= sel.equals("V2")?"selected=\"selected\"":""%> value="V2">Custom</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (srName1==null || cmpNum==1)?srName0:(srName0 + " and " + srName1)%> Version/Local</span></td>
			<td>
				<span class="table">
				<select name="QBVersion" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QBVersion"):"USA"; if(sel.equals("")){sel = "USA";}%>
					<option <%= sel.equals("USA")?"selected=\"selected\"":""%> value="USA">USA</option>
					<option <%= sel.equals("UK")?"selected=\"selected\"":""%> value="UK">UK</option>
					<option <%= sel.equals("CAN")?"selected=\"selected\"":""%> value="CAN">Canada</option>
					<option <%= sel.equals("AUS")?"selected=\"selected\"":""%> value="AUS">Australia</option>
					<option <%= sel.equals("NZ")?"selected=\"selected\"":""%> value="NZ">New Zeland</option>
					<option <%= sel.equals("SEA")?"selected=\"selected\"":""%> value="SEA">South East Asia</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Location</span></td>
			<td>
				<span class="table">
				<select name="QBLocation" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QBLocation"):"DEFAULT"; if(sel.equals("")){sel = "DEFAULT";}%>
					<option <%= sel.equals("HOST")?"selected=\"selected\"":""%> value="HOST">Hosted Managed</option>
					<option <%= sel.equals("HOSTU")?"selected=\"selected\"":""%> value="HOSTU">Hosted Unmanaged</option>
					<option <%= sel.equals("HOUSE")?"selected=\"selected\"":""%> value="HOUSE">In-House</option>
					<option <%= sel.equals("ONLINE")?"selected=\"selected\"":""%> value="ONLINE">On-Line</option>
					<option <%= sel.equals("STANDARD")?"selected=\"selected\"":""%> value="STANDARD">Standard Only</option>
					<option <%= sel.equals("DEFAULT")?"selected=\"selected\"":""%> value="DEFAULT">Default</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Old SDK Used/Special mode</span></td>
			<td>
				<span class="table">
				<select name="OldSDKUsed" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OldSDKUsed"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Base Environment to Connect</span></td>
			<td>
				<span class="table">
				<select name="Env2Con" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Env2Con"):"Tst"; if(sel.equals("")){sel = "Tst";}%>
					<option <%= sel.equals("Prd")?"selected=\"selected\"":""%> value="Prd">Production A</option>
					<option <%= sel.equals("Tst")?"selected=\"selected\"":""%> value="Tst">Production B</option>
					<option <%= sel.equals("Prd1")?"selected=\"selected\"":""%> value="Prd1">Production C</option>
					<option <%= sel.equals("Tst1")?"selected=\"selected\"":""%> value="Tst1">Production D</option>
					<option <%= sel.equals("Dev")?"selected=\"selected\"":""%> value="Dev">Production E</option>
					<option <%= sel.equals("Addtnl")?"selected=\"selected\"":""%> value="Addtnl">Additional Server</option>
					<option <%= sel.equals("Ddctd")?"selected=\"selected\"":""%> value="Ddctd">Dedicated Server</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Environment to Connect</span></td>
			<td>
				<span class="table">
				<select name="Env2ConC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Env2ConC"):"Tst"; if(sel.equals("")){sel = "SAB";}%>
					<option <%= sel.equals("Prd")?"selected=\"selected\"":""%> value="SAB">Same As Base</option>
					<option <%= sel.equals("Prd")?"selected=\"selected\"":""%> value="Prd">Production A</option>
					<option <%= sel.equals("Tst")?"selected=\"selected\"":""%> value="Tst">Production B</option>
					<option <%= sel.equals("Prd1")?"selected=\"selected\"":""%> value="Prd1">Production C</option>
					<option <%= sel.equals("Tst1")?"selected=\"selected\"":""%> value="Tst1">Production D</option>
					<option <%= sel.equals("Dev")?"selected=\"selected\"":""%> value="Dev">Production E</option>
					<option <%= sel.equals("Addtnl")?"selected=\"selected\"":""%> value="Addtnl">Additional Server</option>
					<option <%= sel.equals("Ddctd")?"selected=\"selected\"":""%> value="Ddctd">Dedicated Server</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Primary Additional/Dedicated Server DNS/IP</span></td>
			<td>
				<input type="text" name="DdctdSrvr0" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DdctdSrvr0") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Secondary Additional/Dedicated Server DNS/IP</span></td>
			<td>
				<input type="text" name="DdctdSrvr1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DdctdSrvr1") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Primary Additional/Dedicated Server DNS/IP</span></td>
			<td>
				<input type="text" name="DdctdSrvr0C" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DdctdSrvr0C") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Secondary Additional/Dedicated Server DNS/IP</span></td>
			<td>
				<input type="text" name="DdctdSrvr1C" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DdctdSrvr1C") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Multi-currency support</span></td>
			<td>
				<span class="table">
				<select name="MulCur" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MulCur"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("One")?"selected=\"selected\"":""%> value="One">Convert to base currency</option>
					<option <%= sel.equals("Many")?"selected=\"selected\"":""%> value="Many">Propagate transaction currency</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (fs.equals("Payment Gateway"))?"Object to pay from":"Number of decimal characters for Currency format (default 2)"%></span></td>
			<td>
				<input type="text" name="DecCharCur" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DecCharCur") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Extended Connection Timeout required</span></td>
			<td>
				<span class="table">
				<select name="LongTimeOut" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "LongTimeOut"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Restore State on Connection Failure</span></td>
			<td>
				<span class="table">
				<select name="ConFailState" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ConFailState"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Advanced Security Required</span></td>
			<td>
				<span class="table">
				<select name="TranRecReq" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "TranRecReq"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("Yes1")?"selected=\"selected\"":""%> value="Yes1">Yes - Ext. 1</option>
					<option <%= sel.equals("Yes2")?"selected=\"selected\"":""%> value="Yes2">Yes - Ext. 2</option>
					<option <%= sel.equals("Yes3")?"selected=\"selected\"":""%> value="Yes3">Yes - Ext. 3</option>
					<option <%= sel.equals("Yes4")?"selected=\"selected\"":""%> value="Yes4">Yes - Ext. 4</option>
					<option <%= sel.equals("Yes5")?"selected=\"selected\"":""%> value="Yes5">Yes - Ext. 5</option>
					<option <%= sel.equals("Yes6")?"selected=\"selected\"":""%> value="Yes6">Yes - Ext. 6</option>
					<option <%= sel.equals("Yes7")?"selected=\"selected\"":""%> value="Yes7">Yes - Ext. 7</option>
					<option <%= sel.equals("Yes8")?"selected=\"selected\"":""%> value="Yes8">Yes - Ext. 8</option>
					<option <%= sel.equals("Yes9")?"selected=\"selected\"":""%> value="Yes9">Yes - Ext. 9</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Starting time/date for Objects to integrate</span></td>
			<td>
				<input type="text" name="StDtTmInt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "StDtTmInt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Email Notification Mode</span></td>
			<td>
				<span class="table">
				<select name="EmlNtf" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EmlNtf"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("Con")?"selected=\"selected\"":""%> value="Con">Connection Failures Only</option>
					<option <%= sel.equals("EveryErr")?"selected=\"selected\"":""%> value="EveryErr">After Every Error</option>
					<option <%= sel.equals("DailyEveryErr")?"selected=\"selected\"":""%> value="DailyEveryErr">After Every Error and Full Daily Report</option>
					<option <%= sel.equals("ConD")?"selected=\"selected\"":""%> value="ConD">Connection Failures and Full Daily Report</option>
					<option <%= sel.equals("ConDE")?"selected=\"selected\"":""%> value="ConDE">Connection Failures and Error Daily Report</option>
					<option <%= sel.equals("DailyE")?"selected=\"selected\"":""%> value="DailyE">Error Daily Report Only</option>
					<option <%= sel.equals("Daily")?"selected=\"selected\"":""%> value="Daily">Full Daily Report Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Admin e-mail for Notification</span></td>
			<td>
				<span class="table">
				<select name="UseAdmEml" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseAdmEml"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">CC Email Notification Addresses</span></td>
			<td>
				<input type="text" name="CCEmail" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CCEmail") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">BCC Email Notification Addresses</span></td>
			<td>
				<input type="text" name="BCCEmail" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BCCEmail") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Hosting Provider Email Notification Addresses</span></td>
			<td>
				<input type="text" name="HPNEmail" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "HPNEmail") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Stop Scheduled Transaction</span></td>
			<td>
				<span class="table">
				<select name="StopSchedTr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "StopSchedTr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Never</option>
					<option <%= sel.equals("Con")?"selected=\"selected\"":""%> value="Con">After Connection Failure</option>
					<option <%= sel.equals("EveryErr")?"selected=\"selected\"":""%> value="EveryErr">After Every Error</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Start time of sleep window</span></td>
			<td>
				<input type="text" name="SleepStart" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SleepStart") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">End time of sleep window</span></td>
			<td>
				<input type="text" name="SleepEnd" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SleepEnd") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Time Zone Shift</span></td>
			<td>
				<input type="text" name="TimeZone" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "TimeZone") +"\""):"0"%>/>
			</td>
		</tr>
	</table>
	
<p>
	<%if(srName0.equals("QB") || crm.equals("Aria") || fs.equals("Payment Gateway") || (fs.equals("Accpac") && crm.equals("OMS"))){%><input type="submit" name="submit" value="Previous" class="labels"/><%}%>
	<input type="submit" name="submit" value="Save and Finish" class="labels"/>
	</p>
</form>

<br/>
<br/>
<%}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="This is not a multi-tenant solution"/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
</jsp:forward>
<%}}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="Your session has been lost. Please refresh your browser. If error persists please click Reset button below."/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
	<jsp:param name="ButtonLable" value="Reset"/>
</jsp:forward>
<%}%>

</body>
</html>