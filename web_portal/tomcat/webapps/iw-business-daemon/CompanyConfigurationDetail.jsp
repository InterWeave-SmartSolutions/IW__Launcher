<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="com.interweave.web.HtmlEncoder" %>
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
	brandSol += "&PortalBrand=" + java.net.URLEncoder.encode(brand, "UTF-8");
}
if (solutions != null && solutions.length() > 0) {
	brandSol += "&PortalSolutions=" + java.net.URLEncoder.encode(solutions, "UTF-8");
}
String brandSol1 = "";
if (brand != null && brand.length() > 0) {
	brandSol1 += "?PortalBrand=" + java.net.URLEncoder.encode(brand, "UTF-8");
}
if (solutions != null && solutions.length() > 0) {
	brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + java.net.URLEncoder.encode(solutions, "UTF-8");
}
String currentProfileName = request.getParameter("CurrentProfile");
String currentUser = "";
if(currentProfileName!=null){
int clps = currentProfileName.indexOf(":");
currentUser = currentProfileName.substring(clps + 1);}
String oldProfileName = request.getParameter("OldProfile");
String solutionType = request.getParameter("Solution");
String navigation = request.getParameter("Navigation");
String next = (navigation.equals("B"))?"/CompanyConfiguration.jsp":"/CompanyConfigurationDetailT.jsp";
String crm = "CRM";
if(solutionType.startsWith("SF")){
crm="SF";
} else if (solutionType.startsWith("AR")){
crm="Aria";
} else if (solutionType.startsWith("SUG")){
crm="Sugar";
} else if (solutionType.startsWith("OMS")){
crm="OMS";
} else if (solutionType.startsWith("ORA")){
crm="Fusion";
} else if (solutionType.startsWith("MSDCRM")){
crm="MSDCRM";
} else if (solutionType.startsWith("PPOL")){
crm="PPOL";
}
String crmcust = "Customer";
if(solutionType.startsWith("SF") || solutionType.startsWith("SUG") || solutionType.startsWith("ORA") || solutionType.startsWith("MSDCRM")){
crmcust="Account/Contact";
} else if (solutionType.startsWith("AR")){
crmcust="Account";
} else if (solutionType.startsWith("PPOL")){
crmcust="Organization";
}
String crmtran = "Order";
if(solutionType.startsWith("SF") || solutionType.startsWith("ORA") || solutionType.startsWith("MSDCRM")){
crmtran="Opportunity/Object";
} else if (solutionType.startsWith("SUG")){
crmtran="Quote";
} else if (solutionType.startsWith("AR")){
crmtran="Transaction";
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
String fscust = "Customer";
if (solutionType.indexOf("QB") >= 0){
fscust = "Customer/Job";
}  
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
}
String aC = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeAC");
if(aC.equals("")){aC="None";}
String pAC = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePAC");
if(pAC.equals("")){pAC="None";}
String aV = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVAC");
if(aV.equals("")){aV="None";}
String pSO = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePSO");
if(pSO.equals("")){pSO="None";}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Configuration Details - Accounts</title>
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
<%if((aC.equals("None") && aV.equals("None") && pAC.equals("None"))){
    ConfigContext.setAdminLoggedIn(true);if(oldProfileName!=null){%>
<jsp:forward page="<%= next %>">
	<jsp:param name="CurrentProfile" value="<%= currentProfileName %>"/>
	<jsp:param name="OldProfile" value="<%= oldProfileName %>"/>
	<jsp:param name="Solution" value="<%= solutionType %>"/>
	<jsp:param name="Navigation" value="<%= navigation %>"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
</jsp:forward>
	<%} else {%>
<jsp:forward page="<%= next %>">
	<jsp:param name="CurrentProfile" value="<%= currentProfileName %>"/>
	<jsp:param name="Solution" value="<%= solutionType %>"/>
	<jsp:param name="Navigation" value="<%= navigation %>"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
</jsp:forward>
	<%}%>
<%} else {%>
<table border="0" cellpadding="5" width="100%">
	<tr>
		<td colspan="4"><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td class="labels"><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Please
		set configuration parameters for your solution.
		</span><br/>Customer<%if(!aV.equals("None")){%>/Vendor<%}%> Configuration Details</td><td align="right" class="labels">User: <%= HtmlEncoder.encode(currentUser)%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= HtmlEncoder.encode(currentProfileName)%> <%= HtmlEncoder.encode(oldProfileName)%> <%= HtmlEncoder.encode(solutionType)%> <%= HtmlEncoder.encode(crm)%> <%= HtmlEncoder.encode(navigation)%>-->
<form action="CompanyConfigurationSetvletDT" method="post">
<input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
<input type="hidden" name="PortalBrand" value="<%= HtmlEncoder.encode(brand)%>"/>
<input type="hidden" name="PortalSolutions" value="<%= HtmlEncoder.encode(solutions)%>"/>
  <input type="hidden" name="CurrentProfile" value="<%= HtmlEncoder.encode(currentProfileName) %>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= HtmlEncoder.encode(oldProfileName) %>"/>
  <%}%>
  <input type="hidden" name="Solution" value="<%= HtmlEncoder.encode(solutionType) %>"/>
<p>
	<input type="submit" name="submit" value="Previous" class="labels"/>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
	<br/>
	<br/>
	<%if(!aC.equals("None")){%>
	<span class="labels"><%= crm%> <%= crmcust%> to <%= fs%> Customer</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr>
			<td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding <%= crm%> Custom Field with <%= fs%> ListID/Custom Binding Mapping</span></td>
			<td>
				<input type="text" name="BSFQBLID" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BSFQBLID")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding criteria (optional)</td>
			<td>
				<span class="table">
				<select name="AcctCustBind" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AcctCustBind"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">None</option>
					<option <%= sel.equals("BACN")?"selected=\"selected\"":""%> value="BACN">Name</option>
					<option <%= sel.equals("BACNP")?"selected=\"selected\"":""%> value="BACNP">Name/Phone</option>
					<option <%= sel.equals("BACA")?"selected=\"selected\"":""%> value="BACA">Name/Address(no street)</option>
					<option <%= sel.equals("BACNPA")?"selected=\"selected\"":""%> value="BACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("BACNOP")?"selected=\"selected\"":""%> value="BACNOP">Name or Phone</option>
					<option <%= sel.equals("BACNC")?"selected=\"selected\"":""%> value="BACNC">Name or Company Name</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding <%= crm%> Custom Field with <%= fs%> Full/Normalized
			Name</span></td>
			<td>
				<input type="text" name="BSFQBFN" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BSFQBFN")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Normalize Names for binding</td>
			<td>
				<span class="table">
				<select name="NormBindName" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "NormBindName"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to create/update <%= fs%> Customer</span></td>
			<td>
				<input type="text" name="SFQBCustObj" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFQBCustObj")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Customer Name</span></td>
			<td>
				<input type="text" name="SFQBCustNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFQBCustNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Company Name</span></td>
			<td>
				<input type="text" name="SFQBCompNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFQBCompNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Primary contact selected via</span></td>
			<td>
				<span class="table">
				<select name="PrimCont" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrimCont"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("ACR")?"selected=\"selected\"":""%> value="ACR">Account/Contact Role</option>
					<option <%= sel.equals("CLCF")?"selected=\"selected\"":""%> value="CLCF">Contact Level Custom Field</option>
					<option <%= sel.equals("ALCf")?"selected=\"selected\"":""%> value="ALCf">Contact Lookup in Account</option>
					<option <%= sel.equals("OLCf")?"selected=\"selected\"":""%> value="OLCf">Contact Lookup in <%= crmtran%>/Object</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Default Primary Role Name/<%= crmtran%>/Object Primary Contact</span></td>
			<td>
				<input type="text" name="DefPrimRole" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"DefPrimRole")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Synchronize Contact Mail Address and Account Billing Address</span></td>
			<td>
				<span class="table">
				<select name="SyncCnMlAcBl" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncCnMlAcBl"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Primary Contact</option>
					<option <%= sel.equals("All")?"selected=\"selected\"":""%> value="All">For All Contacts</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Use Billing Address as Shipping</span></td>
			<td>
				<span class="table">
				<select name="UseBillAsShipCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseBillAsShipCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Use Shipping Address as Billing</span></td>
			<td>
				<span class="table">
				<select name="UseShipAsBillCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseShipAsBillCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Propagate <%= crm%> Hierarchy to <%= fs%> Customer/Job
			Hierarchy</span></td>
			<td>
				<span class="table">
				<select name="SyncACJHr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncACJHr"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesO")?"selected=\"selected\"":""%> value="YesO">Yes, using Custom Object</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Hierarchy Level to create <%= fs%> Job</span></td>
			<td>
				<input type="text" name="HJobLevel" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"HJobLevel")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name to create <%= fs%> Job/Address</span></td>
			<td>
				<input type="text" name="CONJob" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CONJob")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Customer Terms</span></td>
			<td>
				<input type="text" name="CustTerm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustTerm")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Customer Type</span></td>
			<td>
				<input type="text" name="CustTypeSt" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustTypeSt")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Customer Tax Code</span></td>
			<td>
				<input type="text" name="CustTaxCodeSt" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustTaxCodeSt")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Customer Tax Item/Taxable</span></td>
			<td>
				<input type="text" name="CustTaxItemSt" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustTaxItemSt")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account Custom field for Total Balance</span></td>
			<td>
				<input type="text" name="SustRemBal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SustRemBal")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Contact Custom field for Middle Name</span></td>
			<td>
				<input type="text" name="MIddleName" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"MIddleName")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<%if((aC.equals("SFQB"))||(aC.equals("SF2QB"))){%>
		<%if((crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")) && aC.equals("SF2QB")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="AccQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> <%= crmcust%>s to <%= fs%> Customers</span></td>
			<td>
				<span class="table">
				<select name="ACSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ACSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(fs.equals("Accpac") && crm.equals("OMS")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group 100</span></td>
			<td>
				<input type="text" name="AcCustGr100" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGr100")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group 200</span></td>
			<td>
				<input type="text" name="AcCustGr200" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGr200")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group 300</span></td>
			<td>
				<input type="text" name="AcCustGr300" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGr300")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group 400</span></td>
			<td>
				<input type="text" name="AcCustGr400" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGr400")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group 500</span></td>
			<td>
				<input type="text" name="AcCustGr500" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGr500")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group CORP</span></td>
			<td>
				<input type="text" name="AcCustGrCORP" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGrCORP")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Types for Accpac Customer Group DIST</span></td>
			<td>
				<input type="text" name="AcCustGrDIST" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGrDIST")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Additional Accpac Customer Groups</span></td>
			<td>
				<input type="text" name="AcCustGrAdd" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustGrAdd")):""%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">OMS Customer Type(s) for Additional Accpac Customer Groups</span></td>
			<td>
				<input type="text" name="AcCustAddTps" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "AcCustAddTps")):""%>"/>
			</td>
		</tr>
<%}%>
<%if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("CRM") && (fs.equals("Sage") || fs.equals("QB")))){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Customer when</span></td>
			<td>
				<span class="table">
				<select name="CreateNC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNC"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr"><%= crm%> Account created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> <%= crmtran%> is in certain stage</option>
					<option <%= sel.equals("AFAccCFAcc")?"selected=\"selected\"":""%> value="AFAccCFAcc"><%= crm%> Account Custom field has certain value</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> <%= crmtran%> Custom field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> <%= crmtran%> is Won</option>
					<option <%= sel.equals("AFAccOppCr")?"selected=\"selected\"":""%> value="AFAccOppCr"><%= crm%> Account and <%= crmtran%> created</option>
		<%if(!pSO.equals("None")){%>
					<option <%= sel.equals("AFAccPS")?"selected=\"selected\"":""%> value="AFAccPS">IMS Sales Order is in certain state</option>
		<%}%>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new <%= fs%> Customer</span></td>
			<td>
				<input type="text" name="SFOppStVal" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStVal")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Customer</span></td>
			<td>
				<input type="text" name="SFCrCusF" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusF")) +"\""):""%>/>
			</td>
		</tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Customer</span></td>
			<td>
				<input type="text" name="SFCrCusFVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= crm%> Accounts with existing <%= fs%> Customers</span></td>
			<td>
				<span class="table">
				<select name="MergeAC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergeAC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Customer Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="AcctBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AcctBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Account</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Customer Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="AcctSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AcctSSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Account</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<%}}%>
		<%if((aC.equals("SFQB"))||(aC.equals("QB2SF"))){%>
		<%if((crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")) && aC.equals("QB2SF")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="AccSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Customers to <%= crm%> <%= crmcust%>s</span></td>
			<td>
				<span class="table">
				<select name="ACQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ACQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("CRM") && (fs.equals("Sage") || fs.equals("QB")))){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> Account when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateAc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateAc"):"QBCustCr"; if(sel.equals("")){sel = "QBCustCr";}%>
					<option <%= sel.equals("QBCustCr")?"selected=\"selected\"":""%> value="QBCustCr"><%= fs%> Customer created/modified</option>
					<option <%= sel.equals("QBCustCFAcc")?"selected=\"selected\"":""%> value="QBCustCFAcc"><%= fs%> Customer field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> Account</span></td>
			<td>
				<input type="text" name="QBCrAccF" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrAccF")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> Account</span></td>
			<td>
				<input type="text" name="QBCrAccFVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrAccFVal")) +"\""):""%>/>
			</td>
		</tr><%}%>	
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= fs%> Customers with existing <%= crm%> Accounts</span></td>
			<td>
				<span class="table">
				<select name="MergeCA" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergeCA"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> Contact Records</td>
			<td>
				<span class="table">
				<select name="CreateSFCont" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateSFCont"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create/Update Alt. Contact as <%= crm%> Contact Record</td>
			<td>
				<span class="table">
				<select name="CreateSFContAlt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateSFContAlt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%if((aC.equals("SFQB"))||(aC.equals("QB2SF"))){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= crm%> Account Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="AcctBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AcctBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= fs%> Customer</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= crm%> Account Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="AcctSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AcctSSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= fs%> Customer</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Ignore <%= fs%> Jobs</td>
			<td>
				<span class="table">
				<select name="AccQB2SFNoJobs" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccQB2SFNoJobs"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Provide to <%= crm%> Financial Information Only </td>
			<td>
				<span class="table">
				<select name="QB2SFFinInfoOnly" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QB2SFFinInfoOnly"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Synchronize <%= fs%> Account Number to <%= crm%></td>
			<td>
				<span class="table">
				<select name="AccQB2SFAcctNmbr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccQB2SFAcctNmbr"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">30/60/90 terms support required</td>
			<td>
				<span class="table">
				<select name="QB306090" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QB306090"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesX")?"selected=\"selected\"":""%> value="YesX">Yes (Extended)</option>
					<option <%= sel.equals("YesX15")?"selected=\"selected\"":""%> value="YesX15">Yes (15 days)</option>
					<option <%= sel.equals("YesI")?"selected=\"selected\"":""%> value="YesI">Yes (Invoice Date)</option>
					<option <%= sel.equals("YesXI")?"selected=\"selected\"":""%> value="YesXI">Yes (Extended, Invoice Date)</option>
					<option <%= sel.equals("YesX15I")?"selected=\"selected\"":""%> value="YesX15I">Yes (15 days, Invoice Date)</option>
				</select>
				</span>
			</td>
		</tr>
		<%}}if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("CRM") && fs.equals("Sage"))){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Use <%= fs%> Full Name to merge</td>
			<td>
				<span class="table">
				<select name="MrgQBFN" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MrgQBFN"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="AccOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesT")?"selected=\"selected\"":""%> value="YesT">Yes (using Customer Type)</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(crm.equals("Aria") && fs.equals("NetSuite")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field for <%= crm%> User Id</span></td>
			<td>
				<input type="text" name="NSFldArId" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"NSFldArId")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Payment Method</span></td>
			<td>
				<span class="table">
				<select name="ArPayMet" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ArPayMet"):"PM0"; if(sel.equals("")){sel = "PM0";}%>
					<option <%= sel.equals("PM0")?"selected=\"selected\"":""%> value="PM0">None</option>
					<option <%= sel.equals("PM3")?"selected=\"selected\"":""%> value="PM3">Pre-paid</option>
					<option <%= sel.equals("PM4")?"selected=\"selected\"":""%> value="PM4">Net terms 30</option>
					<option <%= sel.equals("PM5")?"selected=\"selected\"":""%> value="PM5">Net terms 10</option>
					<option <%= sel.equals("PM6")?"selected=\"selected\"":""%> value="PM6">Net terms 15</option>
					<option <%= sel.equals("PM7")?"selected=\"selected\"":""%> value="PM7">Net terms 60</option>
					<option <%= sel.equals("PM9")?"selected=\"selected\"":""%> value="PM9">Net terms 0</option>
					<option <%= sel.equals("PM-1")?"selected=\"selected\"":""%> value="PM-1">External Payment</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Notify Method Code</span></td>
			<td>
				<input type="text" name="ArNotMetCode" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"ArNotMetCode")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Status Code</span></td>
			<td>
				<input type="text" name="ArStCode" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"ArStCode")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Populate <%= crm%> Locality with State/Province</td>
			<td>
				<span class="table">
				<select name="ArPopLoc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ArPopLoc"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmcust%> to <%= fs%> Customer custom	mapping 1</span></td>
			<td>
				<input type="text" name="AccSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmcust%> to <%= fs%> Customer custom	mapping 2
			(<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Acc" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="AccSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "AccSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Credit Card Info Synchronization required</td>
			<td>
				<span class="table">
				<select name="CreditCard" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreditCard"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("Short")?"selected=\"selected\"":""%> value="Short">Short</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Primary Contact Synchronization to Customer Contact Area required</td>
			<td>
				<span class="table">
				<select name="QBContact" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QBContact"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr><%}%>
	</table>
	<%} if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || ( crm.equals("CRM") && fs.equals("MS GP")) || ( crm.equals("CRM") && fs.equals("Sage"))){
	if(!pAC.equals("None")){%>
	<span class="labels"><%= crm%> Person Account to <%= fs%> Customer</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr>
			<td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Synchronize Person Account Mail Address and Account Billing Address</span></td>
			<td>
				<span class="table">
				<select name="SyncPACMlAcBl" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncPACMlAcBl"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Propagate <%= crm%> Person Accounts Hierarchy to <%= fs%> Customer/Job Hierarchy</span></td>
			<td>
				<span class="table">
				<select name="SyncPACJHr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncPACJHr"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%if((pAC.equals("SFQB"))||(pAC.equals("SF2QB"))){%>
		<%if(pAC.equals("SF2QB")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="PAccQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PAccQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Person Accounts to <%= fs%> Customers</span></td>
			<td>
				<span class="table">
				<select name="PACSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PACSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= crm%> Person Accounts with existing <%= fs%> Customers</span></td>
			<td>
				<span class="table">
				<select name="MergePAC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergePAC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Consider <%= crm%> Person Contact as Primary</span></td>
			<td>
				<span class="table">
				<select name="PCAsPrim" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PCAsPrim"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Customer Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="PAcctBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PAcctBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Person Account</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Customer Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="PAcctSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PAcctSSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Person Account</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<%if((pAC.equals("SFQB"))||(pAC.equals("QB2SF"))){%>
		<%if(pAC.equals("QB2SF")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="PAccSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PAccSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Customers to <%= crm%> Person Accounts</span></td>
			<td>
				<span class="table">
				<select name="PACQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PACQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= fs%> Customers with existing <%= crm%> Person Accounts</span></td>
			<td>
				<span class="table">
				<select name="MergeCPA" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergeCPA"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create <%= crm%> Person Account when</span></td>
			<td>
				<span class="table">
				<select name="CreateCPA" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateCPA"):"COMEMP"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("COMEMP")?"selected=\"selected\"":""%> value="COMEMP"><%= fs%> Company Name is empty</option>
					<option <%= sel.equals("FIRSTLAST")?"selected=\"selected\"":""%> value="FIRSTLAST">First and Last Names are not empty</option>
					<option <%= sel.equals("CONTNEMP")?"selected=\"selected\"":""%> value="CONTNEMP">Contact Name is not empty</option>
					<option <%= sel.equals("CUSTFPER")?"selected=\"selected\"":""%> value="CUSTFPER">Custom Field (Person) is not empty</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Person Account Record Type ID</span></td>
			<td>
				<input type="text" name="PAcctRTID" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"PAcctRTID")) +"\""):""%>/>
			</td>
		</tr>
		<%}%>
	</table>
	<%}%>
	<%if(!aV.equals("None")){%>
	<span class="labels"><%= crm%> Account/Contact/Object to <%= fs%> Vendor</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr>
			<td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for <%= fs%> Vendor</span></td>
			<td>
				<input type="text" name="VendorObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VendorObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding <%= crm%> Custom Field with <%= fs%> ListID</span></td>
			<td>
				<input type="text" name="BSFQBLIDV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BSFQBLIDV")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding criteria (optional)</td>
			<td>
				<span class="table">
				<select name="VenCustBind" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VenCustBind"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">None</option>
					<option <%= sel.equals("BACN")?"selected=\"selected\"":""%> value="BACN">Name</option>
					<option <%= sel.equals("BACNP")?"selected=\"selected\"":""%> value="BACNP">Name/Phone</option>
					<option <%= sel.equals("BACA")?"selected=\"selected\"":""%> value="BACA">Name/Address(no street)</option>
					<option <%= sel.equals("BACNPA")?"selected=\"selected\"":""%> value="BACNPA">Name/Phone/Address(no street)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Binding <%= crm%> Custom Field with <%= fs%> Name</span></td>
			<td>
				<input type="text" name="BSFQBFNV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BSFQBFNV")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Normalize Names for binding</td>
			<td>
				<span class="table">
				<select name="NormBindNameV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "NormBindNameV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Vendor Name</span></td>
			<td>
				<input type="text" name="SFQBVendNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFQBVendNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Company Name</span></td>
			<td>
				<input type="text" name="SFQBCompNmV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFQBCompNmV")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Primary contact selected via</span></td>
			<td>
				<span class="table">
				<select name="PrimContV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrimContV"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("ACR")?"selected=\"selected\"":""%> value="ACR">Account/Contact Role</option>
					<option <%= sel.equals("CLCF")?"selected=\"selected\"":""%> value="CLCF">Contact Level Custom Field</option>
					<option <%= sel.equals("ALCf")?"selected=\"selected\"":""%> value="ALCf">Contact Lookup in Account</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Default Primary Role Name</span></td>
			<td>
				<input type="text" name="DefPrimRoleV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"DefPrimRoleV")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Synchronize Contact Mail Address and Account Billing Address</span></td>
			<td>
				<span class="table">
				<select name="SyncCnMlAcBlV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncCnMlAcBlV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Primary Contact</option>
					<option <%= sel.equals("All")?"selected=\"selected\"":""%> value="All">For All Contacts</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Vendor Terms</span></td>
			<td>
				<input type="text" name="VendTerm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VendTerm")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact/Object Field for Total Balance</span></td>
			<td>
				<input type="text" name="VendRemBal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VendRemBal")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Contact/Object Field for Middle Name</span></td>
			<td>
				<input type="text" name="MIddleNameV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"MIddleNameV")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<%if((aV.equals("SFQB"))||(aV.equals("SF2QB"))){%>
		<%if((crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")) && aV.equals("SF2QB")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="VendQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VendQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Accounts/Contacts/Objects
			to <%= fs%> Vendors</span></td>
			<td>
				<span class="table">
				<select name="ACSF2QBOpsV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ACSF2QBOpsV"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Vendor when</span></td>
			<td>
				<span class="table">
				<select name="CreateNV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNV"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr"><%= crm%> Account created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> Opportunity is in certain stage</option>
					<option <%= sel.equals("AFAccCFAcc")?"selected=\"selected\"":""%> value="AFAccCFAcc"><%= crm%> Account Custom field has certain value</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> Opportunity Custom field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> Opportunity is Won</option>
					<option <%= sel.equals("AFAccOppCr")?"selected=\"selected\"":""%> value="AFAccOppCr"><%= crm%> Account or Opportunity created</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Opportunity Stage to create new <%= fs%> Vendor</span></td>
			<td>
				<input type="text" name="SFOppStValV" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValV")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Vendor</span></td>
			<td>
				<input type="text" name="SFCrVenF" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrVenF")) +"\""):""%>/>
			</td>
		</tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Vendor</span></td>
			<td>
				<input type="text" name="SFCrVenFVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrVenFVal")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= crm%> Accounts/Contacts/Objects with
			existing <%= fs%> Vendors</span></td>
			<td>
				<span class="table">
				<select name="MergeAV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergeAV"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Populate <%= fs%> Vendor Address from</span></td>
			<td>
				<span class="table">
				<select name="OCOCFA2Vend" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OCOCFA2Vend"):"ABAddr"; if(sel.equals("")){sel = "ABAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr"><%= crm%> Account/Contact/Object Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crm%> Parent Account Billing Address</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crm%> Parent Contact Address</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr"><%= crm%> Primary Child Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Vendor Street Address with</span></td>
			<td>
				<span class="table">
				<select name="VenBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VenBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Account/Contact</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<%}}%>
		<%if((aV.equals("SFQB"))||(aV.equals("QB2SF"))){%>
		<%if((crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")) && aV.equals("QB2SF")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="AccSF2QBETLV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "AccSF2QBETLV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Vendors to SF
			Accounts/Contacts/Objects</span></td>
			<td>
				<span class="table">
				<select name="ACQB2SFOpsV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ACQB2SFOpsV"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL")){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> Account/Contact/Object when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateAcV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateAcV"):"QBCustCr"; if(sel.equals("")){sel = "QBCustCr";}%>
					<option <%= sel.equals("QBCustCr")?"selected=\"selected\"":""%> value="QBCustCr"><%= fs%> Vendor created/modified</option>
					<option <%= sel.equals("QBCustCFAcc")?"selected=\"selected\"":""%> value="QBCustCFAcc"><%= fs%> Vendor field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> Account/Contact/Object</span></td>
			<td>
				<input type="text" name="QBCrAccFV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrAccFV")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> Account/Contact/Object</span></td>
			<td>
				<input type="text" name="QBCrAccFVVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrAccFVVal")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Merge new <%= fs%> Vendors with existing <%= crm%>
			Accounts/Contacts/Objects</span></td>
			<td>
				<span class="table">
				<select name="MergeVA" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MergeVA"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not merge</option>
					<option <%= sel.equals("MACNPA")?"selected=\"selected\"":""%> value="MACNPA">Name/Phone/Address(no street)</option>
					<option <%= sel.equals("MACNP")?"selected=\"selected\"":""%> value="MACNP">Name/Phone</option>
					<option <%= sel.equals("MACA")?"selected=\"selected\"":""%> value="MACA">Name/Address(no street)</option>
					<option <%= sel.equals("MACN")?"selected=\"selected\"":""%> value="MACN">Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Create <%= crm%> Contact Records</td>
			<td>
				<span class="table">
				<select name="CreateSFContV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateSFContV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%if((aV.equals("SFQB"))||(aV.equals("QB2SF"))){%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill SF Account/Contact/Object Billing/Mailing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="VenBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VenBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= fs%> Vendor</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= crm%> Account/Contact/Object Shipping/Other Street Address with</span></td>
			<td>
				<span class="table">
				<select name="VenSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VenSSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= fs%> Vendor</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Provide to <%= crm%> Financial Information Only </td>
			<td>
				<span class="table">
				<select name="QB2SFFinInfoOnlyV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QB2SFFinInfoOnlyV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<%}}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact/Object to <%= fs%> Vendor custom
			mapping 1</span></td>
			<td>
				<input type="text" name="AccVSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccVSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact/Object to <%= fs%> Vendor custom
			mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=AccV" + "&Solution=" + solutionType%>' target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="AccVSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccVSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "AccVSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
	</table>
	<%}}%>
	<%if(((aC.equals("SFQB"))||(aC.equals("SF2QB"))) && ((aV.equals("SFQB"))||(aV.equals("SF2QB")))){%>
	<span class="labels"><%= crm%> Account/Contact criteria to create Customer or Vendor</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr>
			<td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact Field to select Customer or Vendor</span></td>
			<td>
				<input type="text" name="CustVendSelFN" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustVendSelFN")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact Field Value(s) to select Customer</span></td>
			<td>
				<input type="text" name="CustVendSelFVC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustVendSelFVC")) +"\""):""%>/>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact Field Value(s) to select Vendor</span></td>
			<td>
				<input type="text" name="CustVendSelFVV" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CustVendSelFVV")) +"\""):""%>/>
			</td>
		</tr>
	</table>
	<%}%>
<p>
	<input type="submit" name="submit" value="Previous" class="labels"/>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
</form>

<br/>
<br/>
<%}}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="This is not a multi-tenant solution."/>
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