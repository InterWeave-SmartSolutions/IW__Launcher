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
String oldProfileName = request.getParameter("OldProfile");
String solutionType = request.getParameter("Solution");
String navigation = request.getParameter("Navigation");
String next = (navigation.equals("B"))?"/CompanyConfigurationDetail.jsp":"/CompanyConfigurationDetailT1.jsp";
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
String crmtran = "Order";
if(solutionType.startsWith("SF") || solutionType.startsWith("ORA") || solutionType.startsWith("MSDCRM") || solutionType.startsWith("PPOL")){
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
String oJ = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeOJ");
if(oJ.equals("")){oJ="None";}
String sO = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSO");
if(sO.equals("")){sO="None";}
String pO = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePO");
if(pO.equals("")){pO="None";}
String oD = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeDep");
if(oD.equals("")){oD="None";}
String oPR = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePR");
if(oPR.equals("")){oPR="None";}
String oB = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeBill");
if(oB.equals("")){oB="None";}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Configuration Details (Transactions)</title>
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
<%if(oJ.equals("None") && sO.equals("None") && pO.equals("None") && oD.equals("None") && oPR.equals("None") && oB.equals("None")){
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
		</span><br/>Transactions (<%if(!sO.equals("None")){%>SO<%}%><%if(!pO.equals("None")){%> PO<%}%><%if(!oJ.equals("None")){%> Job<%}%><%if(!oD.equals("None")){%> Deposit<%}%><%if(!oPR.equals("None")){%> PR<%}%><%if(!oB.equals("None")){%> Bill<%}%>) Configuration Details</td><td align="right" class="labels">User: <%= currentUser%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= currentProfileName%> <%= oldProfileName%> <%= solutionType%> <%= crm%> <%= navigation%>-->
<form action="CompanyConfigurationServletDTT" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
  <input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= oldProfileName %>"/>
  <%}%>
  <input type="hidden" name="Solution" value="<%= solutionType %>"/>
<p>
	<input type="submit" name="submit" value="Previous" class="labels"/>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
	<br/>
	<br/>
	<%if(!sO.equals("None")&& (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("OMS") || crm.equals("Aria") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))){%>
	<span class="labels"><%= crm%> <%= crmtran%> to <%= fs%> Sales Order</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("Aria") && fs.equals("NetSuite")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Location Internal Id</span></td>
			<td>
				<input type="text" name="NSDefLoc" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSDefLoc") +"\""):""%>/>
			</td>
		</tr><%}%>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Sales Order #</span></td>
			<td>
				<input type="text" name="SONumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SONumber") +"\""):""%>/>
			</td>
		</tr>
		<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")  || crm.equals("CRM") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Name</span></td>
			<td>
				<input type="text" name="SOTranObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOTranObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Line Name</span></td>
			<td>
				<input type="text" name="SOTranObjectLine" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOTranObjectLine") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Sales Order # is generated by</span></td>
			<td>
				<span class="table">
				<select name="SONumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SONumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr><%}%>
		<%if((sO.equals("SFQB"))||(sO.equals("SF2QB"))){%>
		<%if(sO.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="SOQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> <%= crmtran%> to <%= fs%> Sales Order</span></td>
			<td>
				<span class="table">
				<select name="SOSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update Sales Order</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdSO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Always create Sales Order in full (cached)</span></td>
			<td>
				<span class="table">
				<select name="CreateInFullSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateInFullSO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Sales Order when</span></td>
			<td>
				<span class="table">
				<select name="CreateNSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNSO"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When <%= crm%> <%= crmtran%> created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> <%= crmtran%> is in certain stage</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> <%= crmtran%> Custom field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> <%= crmtran%> is Won</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new <%= fs%> Sales Order</span></td>
			<td>
				<input type="text" name="SFOppStValSO" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValSO")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Sales Order</span></td>
			<td>
				<input type="text" name="SFCrCusFSO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Sales Order</span></td>
			<td>
				<input type="text" name="SFCrCusFSOVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSOVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling Item Name/Additional Descriptions</span></td>
			<td>
				<input type="text" name="DummySOSHNm" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummySOSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummySOSHPrc" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummySOSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Custom Sorting of Line Items</span></td>
			<td>
				<input type="text" name="SOLISorting" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOLISorting") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Skipping Line Item</span></td>
			<td>
				<input type="text" name="SOSkipLI" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOSkipLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value for Skipping Line Item</span></td>
			<td>
				<input type="text" name="SOSkipLIVal" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOSkipLIVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Default Shipping and Handling for Dummy Sales Order</td>
			<td>
				<span class="table">
				<select name="UseDmy4DmySO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseDmy4DmySO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to <%= fs%> Sales Order from</span></td>
			<td>
				<span class="table">
				<select name="SO2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SO2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Billing Address (custom field)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Billing Address (custom field - all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address to <%= fs%> Sales Order from</span></td>
			<td>
				<span class="table">
				<select name="SO2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SO2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtran%> Shipping Address (custom field)</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field with name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtran%> Shipping Address (custom field - all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Sales Order Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="SOBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Sales Order Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="SOSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= fs%> Line Item Description from <%= crm%> Product Line and Product ones using </span></td>
			<td>
				<span class="table">
				<select name="SOLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering <%= crm%> Field Name for Sales Order Operations</span></td>
			<td>
				<input type="text" name="SOPREmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOPREmNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Sales Order to be Printed</span></td>
			<td>
				<input type="text" name="SOToBePrt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOToBePrt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Sales Order to be Emailed</span></td>
			<td>
				<input type="text" name="SOToBeEml" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOToBeEml") +"\""):""%>/>
			</td>
		</tr>
		<%if(crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create Group Item from Product Group</span></td>
			<td>
				<input type="text" name="CreateGoupItemPG" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupItemPG") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create Group Item from Product Group</span></td>
			<td>
				<input type="text" name="CreateGoupItemPGVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupItemPGVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with Group Item Name</span></td>
			<td>
				<input type="text" name="CreateGoupItemNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupItemNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with Group Item Quantity</span></td>
			<td>
				<input type="text" name="CreateGoupItemQt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupItemQt") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Sales Order</span></td>
			<td>
				<input type="text" name="SODefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODefTemp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Convert Sales Order to Invoice</td>
			<td>
				<span class="table">
				<select name="ConvSO2InvQB" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ConvSO2InvQB"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes (Standard Prefix IN)</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (Custom Prefix)</option>
					<option <%= sel.equals("YesI")?"selected=\"selected\"":""%> value="YesI">Yes (New Invoice #)</option>
					<option <%= sel.equals("Man")?"selected=\"selected\"":""%> value="Man">Manually</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Prexix For Invoice Number</span></td>
			<td>
				<input type="text" name="ConvSO2InvQBPrx" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ConvSO2InvQBPrx") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with Converted Invoice Index</span></td>
			<td>
				<input type="text" name="ConvSO2InvQBSfx" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ConvSO2InvQBSfx") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((sO.equals("SFQB"))||(sO.equals("QB2SF"))){%>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("OMS") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<%if(sO.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="SOSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Sales Order to <%= crm%> <%= crmtran%></span></td>
			<td>
				<span class="table">
				<select name="SOQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> <%= crmtran%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppSO"):"QBSOCr"; if(sel.equals("")){sel = "QBSOCr";}%>
					<option <%= sel.equals("QBSOCr")?"selected=\"selected\"":""%> value="QBSOCr"><%= fs%> SO created/modified</option>
					<option <%= sel.equals("QBSOCFOpp")?"selected=\"selected\"":""%> value="QBSOCFOpp"><%= fs%> SO field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppSOF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppSOF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppSOFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppSOFVal") +"\""):""%>/>
			</td>	
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtran%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStageSO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStageSO") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtran%> amounts with calculated <%= fs%> Sales Order amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooSOAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooSOAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address Change in <%= fs%> Sales Order to</span></td>
			<td>
				<span class="table">
				<select name="SOBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Billing Address (custom field)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Billing Address (custom field - all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Sales Order to</span></td>
			<td>
				<span class="table">
				<select name="SOSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtran%> Shipping Address (custom field)</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field with name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtran%> Shipping Address (custom field - all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Expand <%= crm%> Group Product after <%= fs%> Group Item expanded</span></td>
			<td>
				<span class="table">
				<select name="SOExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Always Update <%= fs%> Item</span></td>
			<td>
				<span class="table">
				<select name="SOItmAlwUpd" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOItmAlwUpd"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= crmtran%> without line items</td>
			<td>
				<span class="table">
				<select name="SONoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SONoItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Support for multiple identical line items required</td>
			<td>
				<span class="table">
				<select name="SOMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (with clones)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Sales Order line items with Invoice information</td>
			<td>
				<span class="table">
				<select name="SOInvItemsInfo" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOInvItemsInfo"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Remaining Balance</span></td>
			<td>
				<input type="text" name="SORemBal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SORemBal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Customer/Job Name</span></td>
			<td>
				<input type="text" name="SOCJName" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOCJName") +"\""):""%>/>
			</td>
		</tr>
		<%}}}%>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtran%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppSO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Purchase Order#</span></td>
			<td>
				<input type="text" name="SOPONumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOPONumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with QB
			Invoice#</span></td>
			<td>
				<input type="text" name="SOInvNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOInvNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Sales Receipt#</span></td>
			<td>
				<input type="text" name="SOSRNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOSRNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Sales Order Date</span></td>
			<td>
				<input type="text" name="SODate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom field for <%= crm%> <%= crmtran%> Name</span></td>
			<td>
				<input type="text" name="SOQBOppNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOQBOppNm") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Sales Order custom mapping
			1</span></td>
			<td>
				<input type="text" name="SOSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Sales Order custom mapping
			2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=SO" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="SOSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>		
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "SOSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="SOOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Sales Order Terms</span></td>
			<td>
				<input type="text" name="SOTerm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOTerm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="SOShipVia" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOShipVia") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Discount Item Name(s)</span></td>
			<td>
				<input type="text" name="SODiscountNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODiscountNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Percentage discount</span></td>
			<td>
				<input type="text" name="SODiscount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODiscount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Amount discount</span></td>
			<td>
				<input type="text" name="SODiscountAm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODiscountAm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="SOClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SOClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created SO Number</span></td>
			<td>
				<input type="text" name="SORflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SORflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="SODplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SODplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="SOFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Description</span></td>
			<td>
				<span class="table">
				<select name="SOUse4Desc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SOUse4Desc"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}%>
	</table>
	<%}%><%if(!pO.equals("None")&& (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))){%>
	<span class="labels"><%= crm%> <%= crmtran%> to <%= fs%> Purchase Order</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Purchase Order #</span></td>
			<td>
				<input type="text" name="PONumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PONumber") +"\""):""%>/>
			</td>
		</tr>
		<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Name</span></td>
			<td>
				<input type="text" name="POTranObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POTranObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Line Name</span></td>
			<td>
				<input type="text" name="POTranObjectLine" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POTranObjectLine") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Purchase Order # is generated by</span></td>
			<td>
				<span class="table">
				<select name="PONumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PONumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Vendor is linked to the <%= crmtran%> as</span></td>
			<td>
				<span class="table">
				<select name="QBVendLink" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "QBVendLink"):"SFAcct"; if(sel.equals("")){sel = "SFAcct";}%>
					<option <%= sel.equals("SFAcct")?"selected=\"selected\"":""%> value="SFAcct"><%= crm%> Account via standard link</option>
					<option <%= sel.equals("SFCustAcct")?"selected=\"selected\"":""%> value="SFCustAcct"><%= crm%> Account via custom link</option>
					<option <%= sel.equals("SFCustOpp")?"selected=\"selected\"":""%> value="SFCustOpp">Custom field in <%= crm%> <%= crmtran%></option>
					<option <%= sel.equals("SFCustProd")?"selected=\"selected\"":""%> value="SFCustProd">Custom field in <%= crm%> Product</option>
					<option <%= sel.equals("SFCustProdLn")?"selected=\"selected\"":""%> value="SFCustProdLn">Custom field in <%= crm%> Product Line</option>
					<option <%= sel.equals("SFCust")?"selected=\"selected\"":""%> value="SFCust"><%= crm%> Custom Object</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field to Split Purchase Order</span></td>
			<td>
				<input type="text" name="POSplit" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POSplit") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Vendor Exceptions List</span></td>
			<td>
				<input type="text" name="VendExcept" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VendExcept") +"\""):""%>/>
			</td>
		</tr>
		<%if((pO.equals("SFQB"))||(pO.equals("SF2QB"))){%>
		<%if(pO.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="POQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Opportunities to <%= fs%> Purchase Orders</span></td>
			<td>
				<span class="table">
				<select name="POSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update Purchase Order</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdPO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdPO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Purchase Order when</span></td>
			<td>
				<span class="table">
				<select name="CreateNPO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNPO"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When <%= crm%> <%= crmtran%> created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> <%= crmtran%> is in certain stage</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> <%= crmtran%> Custom field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> <%= crmtran%> is Won</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new <%= fs%> Purchase Order</span></td>
			<td>
				<input type="text" name="SFOppStValPO" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValPO")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Purchase Order</span></td>
			<td>
				<input type="text" name="SFCrCusFPO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Purchase Order</span></td>
			<td>
				<input type="text" name="SFCrCusFPOVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPOVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling Item Name/Additional Descriptions</span></td>
			<td>
				<input type="text" name="DummyPOSHNm" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyPOSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummyPOSHPrc" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyPOSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Vendor Address to <%= fs%> Purchase Order from</span></td>
			<td>
				<span class="table">
				<select name="PO2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PO2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Vendor Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Vendor Address (custom field)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Vendor Address (custom field - all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address to <%= fs%> Purchase Order from</span></td>
			<td>
				<span class="table">
				<select name="PO2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PO2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtran%> Shipping Address (custom field)</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field with name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtran%> Shipping Address (custom field - all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Purchase Order Vendor Street Address with</span></td>
			<td>
				<span class="table">
				<select name="POBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Purchase Order Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="POSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Product Field Name for Vendor price</span></td>
			<td>
				<input type="text" name="VendPriceLI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VendPriceLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= fs%> Line Item Description from <%= crm%> Product Line and Product ones using </span></td>
			<td>
				<span class="table">
				<select name="POLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering <%= crm%> Field Name for Purchase Order Operations</span></td>
			<td>
				<input type="text" name="POPREmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POPREmNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Purchase Order to be Printed</span></td>
			<td>
				<input type="text" name="POToBePrt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POToBePrt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Purchase Order to be Emailed</span></td>
			<td>
				<input type="text" name="POToBeEml" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POToBeEml") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Purchase Order</span></td>
			<td>
				<input type="text" name="PODefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PODefTemp") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if((pO.equals("SFQB"))||(pO.equals("QB2SF"))){%>
		<%if(pO.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="POSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Purchase Orders to <%= crm%> Opportunities</span></td>
			<td>
				<span class="table">
				<select name="POQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> <%= crmtran%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppPO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppPO"):"QBPOCr"; if(sel.equals("")){sel = "QBPOCr";}%>
					<option <%= sel.equals("QBPOCr")?"selected=\"selected\"":""%> value="QBPOCr"><%= fs%> PO created/modified</option>
					<option <%= sel.equals("QBPOCFOpp")?"selected=\"selected\"":""%> value="QBPOCFOpp"><%= fs%> PO field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppPOF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppPOF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppPOFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppPOFVal") +"\""):""%>/>
			</td>	
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtran%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStagePO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStagePO") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtran%> amounts with calculated <%= fs%> Purchase Order amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooPOAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooPOAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Vendor Address Change in <%= fs%> Purchase Order to</span></td>
			<td>
				<span class="table">
				<select name="POBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Vendor Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Vendor Address (custom field)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Vendor Address (custom field - all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Purchase Order to</span></td>
			<td>
				<span class="table">
				<select name="POSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtran%> Shipping Address (custom field)</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field with name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtran%> Shipping Address (custom field - all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Expand <%= crm%> Group Product after <%= fs%> Group Item expanded</span></td>
			<td>
				<span class="table">
				<select name="POExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= crmtran%> without line items</td>
			<td>
				<span class="table">
				<select name="PONoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PONoItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Support for multiple identical line items required</td>
			<td>
				<span class="table">
				<select name="POMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (with clones)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Entity Name/Reference</span></td>
			<td>
				<input type="text" name="POCJName" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POCJName") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtran%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppPO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppPO"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Purchase Order Date</span></td>
			<td>
				<input type="text" name="PODate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PODate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom field for <%= crm%> <%= crmtran%> Name</span></td>
			<td>
				<input type="text" name="POQBOppNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POQBOppNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Purchase Order custom mapping
			1</span></td>
			<td>
				<input type="text" name="POSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Purchase Order custom mapping
			2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=PO" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="POSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "POSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Purchase Order Terms</span></td>
			<td>
				<input type="text" name="POTerm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POTerm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="POShipVia" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POShipVia") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Vendor Percentage discount</span></td>
			<td>
				<input type="text" name="PODiscount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PODiscount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Vendor Amount discount</span></td>
			<td>
				<input type="text" name="PODiscountAm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PODiscountAm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="POClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "POClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created PO Number</span></td>
			<td>
				<input type="text" name="PORflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PORflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="PODplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PODplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Vendor</span></td>
			<td>
				<span class="table">
				<select name="POFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "POFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
	</table>
	<%}%>
	
	<%if(!oJ.equals("None")&& (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))){String crmtranj = (crm.equals("Sugar"))?"Opportunity":crmtran;%>
	<span class="labels"><%= crm%> <%= crmtranj%> to <%= fs%> Job</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("Sugar") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Name</span></td>
			<td>
				<input type="text" name="OJTranObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "OJTranObject") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Binding <%= crm%> Custom Field with <%= fs%> ListID</span></td>
			<td>
				<input type="text" name="BSFQBLIDOJ" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BSFQBLIDOJ") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Binding criteria (optional)</td>
			<td>
				<span class="table">
				<select name="OppJobBind" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OppJobBind"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">None</option>
					<option <%= sel.equals("BACN")?"selected=\"selected\"":""%> value="BACN">Name</option>
					<option <%= sel.equals("BACNP")?"selected=\"selected\"":""%> value="BACNP">Name/Phone</option>
					<option <%= sel.equals("BACA")?"selected=\"selected\"":""%> value="BACA">Name/Address(no street)</option>
					<option <%= sel.equals("BACNPA")?"selected=\"selected\"":""%> value="BACNPA">Name/Phone/Address(no street)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job Name</span></td>
			<td>
				<input type="text" name="QBCFJobNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Custom field for Current Balance</span></td>
			<td>
				<input type="text" name="JobRemBal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "JobRemBal") +"\""):""%>/>
			</td>
		</tr>
		<%if((oJ.equals("SFQB"))||(oJ.equals("SF2QB"))){%>
		<%if(oJ.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="OppJQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OppJQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Primary contact selected via</span></td>
			<td>
				<span class="table">
				<select name="PrimContOJ" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrimContOJ"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("ACR")?"selected=\"selected\"":""%> value="ACR">Object/Contact Role</option>
					<option <%= sel.equals("CLCF")?"selected=\"selected\"":""%> value="CLCF">Contact Level Custom Field</option>
					<option <%= sel.equals("ALCf")?"selected=\"selected\"":""%> value="ALCf">Contact Lookup in Object</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr>
			<td><%= lnmbr++%></td>
			<td><span class="table">Default Primary Role Name</span></td>
			<td>
				<input type="text" name="DefPrimRoleOJ" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefPrimRoleOJ") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> <%= crmtranj%> to <%= fs%> Jobs</span></td>
			<td>
				<span class="table">
				<select name="OJSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OJSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Job when</span></td>
			<td>
				<span class="table">
				<select name="CreateNJob" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNJob"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When <%= crm%> <%= crmtran%> created</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> <%= crmtran%> field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> <%= crmtran%> is Won</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new <%= fs%> Job</span></td>
			<td>
				<input type="text" name="SFCrCusFJob" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFJob") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Job</span></td>
			<td>
				<input type="text" name="SFCrCusFJobVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFJobVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to <%= fs%> Job from</span></td>
			<td>
				<span class="table">
				<select name="Job2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Job2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtranj%> Billing Address</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtranj%> Billing Address (all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address to <%= fs%> Job from</span></td>
			<td>
				<span class="table">
				<select name="Job2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Job2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtranj%> Shipping Address</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtranj%> Shipping Address (all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Job Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="JobBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JobBSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Job Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="JobSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JobSSAddr"):"AsSF"; if(sel.equals("")){sel = "AsSF";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Job Initial Status</span></td>
			<td>
				<span class="table">
				<select name="JobInitStat" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JobInitStat"):"StNo"; if(sel.equals("")){sel = "StNo";}%>
					<option <%= sel.equals("StNo")?"selected=\"selected\"":""%> value="StNo">None</option>
					<option <%= sel.equals("StPn")?"selected=\"selected\"":""%> value="StPn">Pending</option>
					<option <%= sel.equals("StAw")?"selected=\"selected\"":""%> value="StAw">Awarded</option>
					<option <%= sel.equals("StIP")?"selected=\"selected\"":""%> value="StIP">InProgress</option>
					<option <%= sel.equals("StCl")?"selected=\"selected\"":""%> value="StCl">Closed</option>
					<option <%= sel.equals("StNA")?"selected=\"selected\"":""%> value="StNA">NotAwarded</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job Start Date</span></td>
			<td>
				<input type="text" name="QBCFJobStDt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobStDt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job Projected End Date</span></td>
			<td>
				<input type="text" name="QBCFJobPEDt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobPEDt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job End Date</span></td>
			<td>
				<input type="text" name="QBCFJobEDt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobEDt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job Type</span></td>
			<td>
				<input type="text" name="QBCFJobTp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobTp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> Field with <%= fs%> Job Description</span></td>
			<td>
				<input type="text" name="QBCFJobDsc" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCFJobDsc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> to <%= fs%> Job custom
			mapping 1</span></td>
			<td>
				<input type="text" name="JobSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "JobSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranj%> to <%= fs%> Job custom
			mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Job" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="JobSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "JobSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "JobSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<%}%>
		<%if((oJ.equals("SFQB"))||(oJ.equals("QB2SF"))){%>
		<%if(oJ.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="OppJSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OppJSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Jobs to <%= crm%> <%= crmtranj%></span></td>
			<td>
				<span class="table">
				<select name="OJQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "OJQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
	</table>
	<%}%>
	<%if(!oD.equals("None")){String crmtranb=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"Object":crmtran;%>
	<span class="labels"><%= crm + " " + crmtranb + " to " + fs + " Deposit"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for <%= fs%> Deposit</span></td>
			<td>
				<input type="text" name="DepositObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositObject") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Deposit#</span></td>
			<td>
				<input type="text" name="DepositNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Deposit # is generated by</span></td>
			<td>
				<span class="table">
				<select name="DepositNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DepositNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Deposit Account Name </span></td>
			<td>
				<input type="text" name="QBBank4Deposit" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBBank4Deposit") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Cash Account Name to create QB
			Deposit</span></td>
			<td>
				<input type="text" name="QBGL4Deposit" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBGL4Deposit") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Deposit Amount</span></td>
			<td>
				<input type="text" name="DepositAmount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositAmount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> Field with <%= fs%> Deposit
			Date</span></td>
			<td>
				<input type="text" name="DepositDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Deposit Entity
			Reference</span></td>
			<td>
				<input type="text" name="DepositPayee" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositPayee") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for <%= fs%> Deposit Lines</span></td>
			<td>
				<input type="text" name="DepositCObjectCmp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositCObjectCmp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to <%= fs%> Deposit</span></td>
			<td>
				<span class="table">
				<select name="DepositSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DepositSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Deposit</span></td>
			<td>
				<input type="text" name="SFCrCusFDeposit" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFDeposit") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Deposit</span></td>
			<td>
				<input type="text" name="SFCrCusFDepositVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFDepositVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to <%= fs%> Deposit custom mapping 1</span></td>
			<td>
				<input type="text" name="DepositSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to <%= fs%> Deposit custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Deposit" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="DepositSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "DepositSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Deposit Class</span></td>
			<td>
				<input type="text" name="DepositClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Deposit Memo</span></td>
			<td>
				<input type="text" name="DepositMemo" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositMemo") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Deposit Number</span></td>
			<td>
				<input type="text" name="DepositRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DepositRflct") +"\""):""%>/>
			</td>
		</tr><%}}%>
	</table>
	<%}%>
	<%if(!oPR.equals("None")){String crmtranp=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"Object":crmtran;%>
	<span class="labels"><%= crm + " " + crmtranp + " to " + fs + " Payment"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for <%= fs%> Payment</span></td>
			<td>
				<input type="text" name="PRObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRObject") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Payment#</span></td>
			<td>
				<input type="text" name="PRNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Undeposited Payment#</span></td>
			<td>
				<input type="text" name="PRUndepNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRUndepNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Invoice # Payment is applied to</span></td>
			<td>
				<input type="text" name="PRAppldNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRAppldNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Payment # is generated by</span></td>
			<td>
				<span class="table">
				<select name="PRNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PRNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> AR Account Name </span></td>
			<td>
				<input type="text" name="QBBank4PR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBBank4PR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Deposit Account Name </span></td>
			<td>
				<input type="text" name="QBDep4PR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBDep4PR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for <%= fs%> Payment Amount</span></td>
			<td>
				<input type="text" name="PRAmount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRAmount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field with <%= fs%> Payment
			Date</span></td>
			<td>
				<input type="text" name="PRDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for <%= fs%> Payment Customer
			Reference</span></td>
			<td>
				<input type="text" name="PRPayee" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRPayee") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for <%= fs%> Payment Lines</span></td>
			<td>
				<input type="text" name="PRCObjectCmp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRCObjectCmp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to
			<%= fs%> Payment</span></td>
			<td>
				<span class="table">
				<select name="PRSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PRSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Payment</span></td>
			<td>
				<input type="text" name="SFCrCusFPmnt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPmnt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Payment</span></td>
			<td>
				<input type="text" name="SFCrCusFPmntVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPmntVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> to <%= fs%> Payment custom mapping
			1</span></td>
			<td>
				<input type="text" name="PRSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> to <%= fs%> Payment custom mapping
			2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=PR" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="PRSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "PRSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for Payment Memo</span></td>
			<td>
				<input type="text" name="PRMemo" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRMemo") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for Created Payment Number</span></td>
			<td>
				<input type="text" name="PRRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PRRflct") +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(!oB.equals("None")){String crmtranb=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?"Object":crmtran;%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtranb + " to " + fs + " Bill"):("Aria Cash Credit to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || (crm.equals("Aria") && (!oB.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for <%= fs%> Bill</span></td>
			<td>
				<input type="text" name="BillObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Bill#</span></td>
			<td>
				<input type="text" name="BillNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Bill # is generated by</span></td>
			<td>
				<span class="table">
				<select name="BillNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BillNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Synchronize to Item Receipt</td>
			<td>
				<span class="table">
				<select name="BillAsItemRec" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BillAsItemRec"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update a Bill</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdBill" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdBill"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB AP Account Name </span></td>
			<td>
				<input type="text" name="QBBank4Bill" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBBank4Bill") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to create <%= fs%> Expense Bill Lines</span></td>
			<td>
				<input type="text" name="BillCObjectCmp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillCObjectCmp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to create <%= fs%> Item Bill Lines</span></td>
			<td>
				<input type="text" name="CORef4BillLI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4BillLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name(s) for/Default <%= fs%> Expense Account Name to create <%= fs%> 
			Bill</span></td>
			<td>
				<input type="text" name="QBGL4Bill" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBGL4Bill") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name(s) for/Default <%= fs%> Item Name to create <%= fs%> Bill</span></td>
			<td>
				<input type="text" name="QBItem4Bill" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItem4Bill") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name(s) for <%= fs%> Bill Amount</span></td>
			<td>
				<input type="text" name="BillAmount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillAmount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Bill Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4BillLIQT" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4BillLIQT") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> Field with <%= fs%> Bill Date</span></td>
			<td>
				<input type="text" name="BillDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Bill Vendor	Reference</span></td>
			<td>
				<input type="text" name="BillPayee" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillPayee") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Customer/Job</span></td>
			<td>
				<input type="text" name="BillCustJob" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillCustJob") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to <%= fs%> Bill</span></td>
			<td>
				<span class="table">
				<select name="BillSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BillSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new <%= fs%> Bill</span></td>
			<td>
				<input type="text" name="SFCrCusFBill" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFBill") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Bill</span></td>
			<td>
				<input type="text" name="SFCrCusFBillVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFBillVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to set <%= fs%> Bill Type</span></td>
			<td>
				<input type="text" name="SFCrBillType" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrBillType") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create <%= fs%> Expence Bill</span></td>
			<td>
				<input type="text" name="SFCrBillTypeExp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrBillTypeExp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create <%= fs%> Item Bill</span></td>
			<td>
				<input type="text" name="SFCrBillTypeItm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrBillTypeItm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Name to skip <%= fs%> Bill Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineBill" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFSkipLineBill") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Value to skip <%= fs%> Bill Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineBillVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFSkipLineBillVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Bill to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="BillQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BillQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> <%= crmtran%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppBill" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppBill"):"QBBillCr"; if(sel.equals("")){sel = "QBBillCr";}%>
					<option <%= sel.equals("QBBillCr")?"selected=\"selected\"":""%> value="QBBillCr"><%= fs%> Bill created/modified</option>
					<option <%= sel.equals("QBBillCFOpp")?"selected=\"selected\"":""%> value="QBBillCFOpp"><%= fs%> Bill field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppBillF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppBillF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppBillFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppBillFVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to <%= fs%> Bill custom mapping 1</span></td>
			<td>
				<input type="text" name="BillSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to <%= fs%> Bill custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Bill" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="BillSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "BillSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Bill Term</span></td>
			<td>
				<input type="text" name="BillTerm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillTerm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Bill Class</span></td>
			<td>
				<input type="text" name="BillClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Bill Memo</span></td>
			<td>
				<input type="text" name="BillMemo" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillMemo") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Expense Bill Billable Status</span></td>
			<td>
				<input type="text" name="BillBillable" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillBillable") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Copy Vendor Credit to <%= crm%> Custom Object</td>
			<td>
				<span class="table">
				<select name="CreditMemoV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreditMemoV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (fast query)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Copy Bill Payment to <%= crm%> Custom Object</td>
			<td>
				<span class="table">
				<select name="PaymentQBV" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PaymentQBV"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (fast query)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Bill Number</span></td>
			<td>
				<input type="text" name="BillRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillRflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Quantity</span></td>
			<td>
				<span class="table">
				<select name="BillUse4Qty" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BillUse4Qty"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="BillAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account used for Cash Credits</span></td>
			<td>
				<input type="text" name="BillAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "BillAccRcvC") +"\""):""%>/>
			</td>
		</tr><%}%>
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