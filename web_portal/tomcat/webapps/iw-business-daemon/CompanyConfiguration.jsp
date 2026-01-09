<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%
int lnmbr = 1; 
String currentProfileName = request.getParameter("CurrentProfile");
String currentUser = "";
if(currentProfileName!=null){
int clps = currentProfileName.indexOf(":");
currentUser = currentProfileName.substring(clps + 1);}
String oldProfileName = request.getParameter("OldProfile");
String solutionType = request.getParameter("Solution");
String navigation = request.getParameter("Navigation");
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
}  else if (solutionType.startsWith("PPOL")){
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
}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Configuration</title>
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
		<td colspan="5"><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td class="labels"><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">Please
		set configuration parameters for your solution.
		</span><br/>Object Selection</td><td align="right" class="labels">User: <%= currentUser%></td><td align="right"><a href='<%= "monitoring/Dashboard.jsp" + brandSol1%>' target="_top" class="labels">Monitoring Dashboard</a></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= currentProfileName%> <%= oldProfileName%> <%= solutionType%> <%= crm%> <%= navigation%>-->
<form action="CompanyConfigurationServletOS" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
  <input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= oldProfileName %>"/>
  <%}%>
  <input type="hidden" name="Solution" value="<%= solutionType %>"/>	
<p>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("OMS") || (crm.equals("Aria") && fs.equals("NetSuite")) || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmcust%> to <%= fs%> <%= fscust%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeAC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeAC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("2") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<%if(fs.equals("QB") || fs.equals("NetSuite") || fs.equals("Sage") || fs.equals("MS GP")){%>
		<%if(!crm.equals("Sugar") && (!crm.equals("Fusion")) && (!crm.equals("MSDCRM")) && (!crm.equals("PPOL")) && (solutionType.endsWith("2") || solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C"))){%>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Person Account to <%= fs%> Customer/Job</span></td>
			<td>
				<span class="table">
				<select name="SyncTypePAC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePAC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("2") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if((solutionType.endsWith("2") && crm.equals("Sugar")) || solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Account/Contact/Object to <%= fs%> Vendor</span></td>
			<td>
				<span class="table">
				<select name="SyncTypeVAC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVAC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= (crm.equals("Sugar"))?"Opportunity":crmtran%> to <%= fs%> Job</span></td>
			<td>
				<span class="table">
				<select name="SyncTypeOJ" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeOJ"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr><%}}if(!crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Sales Order</span></td>
			<td>
				<span class="table">
				<select name="SyncTypeSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSO"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Purchase Order</span></td>
			<td>
				<span class="table">
				<select name="SyncTypePO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePO"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr><%}}%>
		<%if(!(fs.equals("Accpac") && crm.equals("OMS"))){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " " + crmtran + " to " + fs + " Invoice"):("Aria Invoice to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeInv"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%}%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
<%if(!crm.equals("Aria")){%>					
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}}%>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " " + crmtran + " to " + fs + " Sales Receipt"):("Aria Dunning Charges to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeSR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSR"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%} else {%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(crm.equals("Aria") || solutionType.endsWith("2") || solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " " + ((crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM"))?"Quote/":"") + crmtran + " to " + fs + " Estimate"):("Aria Refund to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeEst" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeEst"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%} else {%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(crm.equals("Aria") || solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){String crmtranb=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM"))?"Object":crmtran;%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " " + crmtranb + " to " + fs + " Bill"):("Aria Cash Credit to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeBill" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeBill"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%} else {%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){String crmtranp="Object";%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm + " " + crmtranp + " to " + fs + " Vendor Credit"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeVC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){String crmtranp="Object";%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm + " " + crmtranp + " to " + fs + " Deposit"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeDep" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeDep"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){String crmtranp="Object";%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm + " " + crmtranp + " to " + fs + " Payment Received"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypePR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePR"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm + " " + crmtran + " to " + fs + " Credit Memo"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCM"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(crm.equals("Aria") || solutionType.endsWith("2") || solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){%>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " " + crmtran + " to " + fs + " Check"):("Aria External Charges to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeCheck" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCheck"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%} else {%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){%>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= crm + " Object to " + fs + " Credit Card Charge"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeCCC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCCC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){String crmtranp="Object";%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm + " " + crmtranp + " to " + fs + " Bill Payment"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeBP" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeBP"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP")){%>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= crm + " Object to " + fs + " Account (COA)"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeCOA" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCOA"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= crm + " Object to " + fs + " Journal Entry"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeJE" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeJE"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= crm + " Object to " + fs + " Time Tracking"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeTT" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeTT"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table"><%= crm + " Object to " + fs + " Statement Charges"%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeSC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSC"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>	
			<td><span class="table">Aria Void Transactions to <%= fs%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypeVoid" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVoid"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option>
				</select>
				</span>
			</td>
		</tr><%}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Aria") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product to <%= fs%> Item</span></td>
			<td>
				<span class="table">
				<select name="SyncTypePrd" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePrd"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr><%}%>
<%if(crm.equals("SF") || crm.equals("Aria") || crm.equals("Fusion") || crm.equals("MSDCRM")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (!crm.equals("Aria"))?(crm + " IMS Sales Order to " + fs + " Sales Order"):("Aria Write-Off to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SyncTypePSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePSO"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
<%if(crm.equals("Aria")){%>
					<option <%= sel.equals("Ar2QBJ")?"selected=\"selected\"":""%> value="Ar2QBJ">Post as <%= fs%> Journal Entries</option><%} else {%>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%><%}%>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">SF IMS Item to <%= fs%> Item</span></td>
			<td>
				<span class="table">
				<select name="SyncTypePItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePItm"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("SF2QB")?"selected=\"selected\"":""%> value="SF2QB"><%= crm%> to <%= fs%> unidirectional</option>
					<option <%= sel.equals("QB2SF")?"selected=\"selected\"":""%>value="QB2SF"><%= fs%> to <%= crm%> unidirectional</option>
					<%if(solutionType.endsWith("1") || solutionType.endsWith("B") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("C")){%><option <%= sel.equals("SFQB")?"selected=\"selected\"":""%>value="SFQB">Bi-directional</option><%}%>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">SF IMS Type</span></td>
			<td>
				<span class="table">
				<select name="SyncIMSType" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SyncIMSType"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("TopShelf")?"selected=\"selected\"":""%>value="TopShelf">Scout TopShelf</option>
					<option <%= sel.equals("Ascent")?"selected=\"selected\"":""%> value="Ascent">Ascent Order Management</option>
				</select>
				</span>
			</td>
		</tr><%}}}%>
	</table>
	
<p>
	<input type="submit" name="submit" value="Next" class="labels"/>
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