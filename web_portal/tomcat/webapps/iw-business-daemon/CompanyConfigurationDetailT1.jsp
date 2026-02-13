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
String next = (navigation.equals("B"))?"/CompanyConfigurationDetailT.jsp":"/CompanyConfigurationDetailT2.jsp";
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
}else if (solutionType.indexOf("PT") >= 0) {
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
String inv = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeInv");
if(inv.equals("")){inv="None";}
String sR = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSR");
if(sR.equals("")){sR="None";}
String est = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeEst");
if(est.equals("")){est="None";}
String vO = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVoid");
if(vO.equals("")){vO="None";}%>
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
<%if(inv.equals("None") && sR.equals("None") && est.equals("None")){
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
		</span><br/>Transactions (<%if(!inv.equals("None")){%>Invoice<%}%><%if(!sR.equals("None")){%> SR<%}%><%if(!est.equals("None")){%> Estimate/Quote<%}%>) Configuration Details</td><td align="right" class="labels">User: <%= currentUser%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= currentProfileName%> <%= oldProfileName%> <%= solutionType%> <%= crm%> <%= navigation%>-->
<form action="CompanyConfigurationServletDTT1" method="post">
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
	
	<%if(!inv.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Invoice"):("Aria Invoice to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!inv.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Invoice#</span></td>
			<td>
				<input type="text" name="InvNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Invoice # is generated by</span></td>
			<td>
				<span class="table">
				<select name="InvNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
					<option <%= sel.equals("MSFQBGen")?"selected=\"selected\"":""%> value="MSFQBGen"><%= crm%>(Merged)</option>
				</select>
				</span>
			</td>
		</tr>
		<%}if((inv.equals("SFQB"))||(inv.equals("SF2QB"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && inv.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="InvQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + crm + " " + crmtran + " to " + fs + " Invoice"):("Permitted sync operations from Aria Invoices to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="InvSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update an Invoice</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdInv"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Always create Invoice in full (cached)</span></td>
			<td>
				<span class="table">
				<select name="CreateInFullInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateInFullInv"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Consider dash (-) in the Invoice Number as a delimiter</span></td>
			<td>
				<span class="table">
				<select name="DashAsDelimInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DashAsDelimInv"):"Yes"; if(sel.equals("")){sel = "Yes";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Billing Address as Shipping</span></td>
			<td>
				<span class="table">
				<select name="UseBillAsShip" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseBillAsShip"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Shipping Address as Billing</span></td>
			<td>
				<span class="table">
				<select name="UseShipAsBill" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseShipAsBill"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
<%if(fs.equals("NetSuite")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Department Internal Id</span></td>
			<td>
				<input type="text" name="NSDepartId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSDepartId") +"\""):""%>/>
			</td>
		</tr> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Class Internal Id</span></td>
			<td>
				<input type="text" name="NSClassId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSClassId") +"\""):""%>/>
			</td>
		</tr><%}%> 
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Invoice when</span></td>
			<td>
				<span class="table">
				<select name="CreateNInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNInv"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When <%= crm%> <%= crmtran%> created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> <%= crmtran%> is in certain stage</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> <%= crmtran%> Custom field has certain value</option>
					<option <%= sel.equals("AFAccCFOppEx")?"selected=\"selected\"":""%> value="AFAccCFOppEx"><%= crm%> <%= crmtran%> Custom field has certain value (exclusively)</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> <%= crmtran%> is Won</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new <%= fs%> Invoice</span></td>
			<td>
				<input type="text" name="SFOppStValInv" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValInv")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Invoice</span></td>
			<td>
				<input type="text" name="SFCrCusFInv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFInv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Invoice</span></td>
			<td>
				<input type="text" name="SFCrCusFInvVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFInvVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name to create <%= fs%> Invoice</span></td>
			<td>
				<input type="text" name="CORef4Inv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4Inv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name/Field name(s) to create <%= fs%> Invoice Line Items</span></td>
			<td>
				<input type="text" name="CORef4InvLI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4InvLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Invoice Line Item Rate</span></td>
			<td>
				<input type="text" name="CORef4InvLIAM" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4InvLIAM") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Invoice Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4InvLIQT" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4InvLIQT") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Invoice Line Item Service Dates</span></td>
			<td>
				<input type="text" name="CORef4InvLID" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4InvLID") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name for <%= fs%> Invoice Line Item Name</span></td>
			<td>
				<input type="text" name="CORef4InvLINm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4InvLINm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Skip <%= crm%> <%= crmtran%> amounts when creating/updating <%= fs%> Invoice</span></td>
			<td>
				<span class="table">
				<select name="UpdSF2InvAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdSF2InvAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling <%= fs%> Item Name(s)</span></td>
			<td>
				<input type="text" name="DummyInvSHNm" size="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyInvSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummyInvSHPrc" size="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyInvSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Dummy Description as Line Item/Invoice Line Item Description</span></td>
			<td>
				<input type="text" name="DummyInvLIDesc" size="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyInvLIDesc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Custom Sorting of Line Items</span></td>
			<td>
				<input type="text" name="InvLISorting" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvLISorting") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with reference to Address Object</span></td>
			<td>
				<input type="text" name="AdrObjRefInv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "AdrObjRefInv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to <%= fs%> Invoice from</span></td>
			<td>
				<span class="table">
				<select name="Inv2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Inv2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
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
			<td><span class="table">Populate Shipping Address to <%= fs%> Invoice from</span></td>
			<td>
				<span class="table">
				<select name="Inv2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Inv2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
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
			<td><span class="table">Fill <%= fs%> Invoice Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="InvBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Fill <%= fs%> Invoice Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="InvSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Create Pending Invoice</td>
			<td>
				<span class="table">
				<select name="InvQBPend" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvQBPend"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to convert <%= fs%> Invoice to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FInv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrConc2FInv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to convert <%= fs%> Invoice to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FInvVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrConc2FInvVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= fs%> Line Item Description from <%= crm%> Product Line and Product ones using </span></td>
			<td>
				<span class="table">
				<select name="InvLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with Default Customer Full Name</span></td>
			<td>
				<input type="text" name="InvDefCustFlNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvDefCustFlNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering <%= crm%> Field Name for Invoice Operations</span></td>
			<td>
				<input type="text" name="InvPREmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvPREmNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Invoice to be Printed</span></td>
			<td>
				<input type="text" name="InvToBePrt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvToBePrt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Invoice to be Emailed</span></td>
			<td>
				<input type="text" name="InvToBeEml" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvToBeEml") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Name to skip <%= fs%> Invoice Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineInv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFSkipLineInv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Value to skip <%= fs%> Invoice Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineInvVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFSkipLineInvVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Product Schedule to filter <%= fs%> Invoice Line Items</span></td>
			<td>
				<span class="table">
				<select name="UseProdSched" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseProdSched"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes (Both)</option>
					<option <%= sel.equals("YesQ")?"selected=\"selected\"":""%> value="YesQ">Yes (Quantity)</option>
					<option <%= sel.equals("YesR")?"selected=\"selected\"":""%> value="YesR">Yes (Revenue)</option>
					<option <%= sel.equals("YesM")?"selected=\"selected\"":""%> value="YesM">Yes (Multiple Invoices)</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (Custom Scheduler)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Vendor Name/Reference for instant payment</span></td>
			<td>
				<input type="text" name="VendInstPmt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VendInstPmt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Field with a value for instant payment</span></td>
			<td>
				<input type="text" name="VendInstPmtVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VendInstPmtVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Invoice</span></td>
			<td>
				<input type="text" name="InvDefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvDefTemp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field to setup Tax value(s)/Taxable <%= fs%> Invoice</span></td>
			<td>
				<input type="text" name="Tax4Inv" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "Tax4Inv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Pre-Populate Tax value to <%= fs%> Invoice as</span></td>
			<td>
				<span class="table">
				<select name="Tax4InvValAs" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Tax4InvValAs"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("SalesTaxItem")?"selected=\"selected\"":""%> value="SalesTaxItem">Sales Tax Item/Tax Rate</option>
					<option <%= sel.equals("TaxItem")?"selected=\"selected\"":""%> value="TaxItem">Additional Line Item</option>
					<option <%= sel.equals("TaxValue")?"selected=\"selected\"":""%> value="TaxValue">Additional Amount for each Line Item</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Sales Tax Item/Tax Rate Full Name</span></td>
			<td>
				<input type="text" name="QSBTaxFullN" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QSBTaxFullN") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((inv.equals("SFQB"))||(inv.equals("QB2SF"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && inv.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</span></td>
			<td>
				<span class="table">
				<select name="InvSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from "+ fs + " Invoice to " + crm + " " + crmtran):("Permitted sync operations from " + fs + " to Aria Invoices")%></span></td>
			<td>
				<span class="table">
				<select name="InvQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
					<option <%= sel.equals("UOPPSBPO")?"selected=\"selected\"":""%> value="UOPPSBPO">Update Balance/Payments Only </option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> <%= crmtran%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppInv"):"QBInvCr"; if(sel.equals("")){sel = "QBInvCr";}%>
					<option <%= sel.equals("QBInvCr")?"selected=\"selected\"":""%> value="QBInvCr"><%= fs%> Invoice created/modified</option>
					<option <%= sel.equals("QBInvCFOpp")?"selected=\"selected\"":""%> value="QBInvCFOpp"><%= fs%> Invoice field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppInvF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppInvF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppInvFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppInvFVal") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtran%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStageInv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStageInv") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtran%> amounts with calculated <%= fs%> 
			Invoice amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooInvAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooInvAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("YesLI1")?"selected=\"selected\"":""%> value="YesLI1">For Line Items Only, Quantity 1</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
					<option <%= sel.equals("Yes1")?"selected=\"selected\"":""%> value="Yes1">For Line Items and Total, Quantity 1</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address Change in <%= fs%> Invoice to</span></td>
			<td>
				<span class="table">
				<select name="InvBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Invoice to</span></td>
			<td>
				<span class="table">
				<select name="InvSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
				<select name="InvExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crmtran%> without line items</td>
			<td>
				<span class="table">
				<select name="InvNoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvNoItems"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="InvMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (with clones)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Copy Credit Memo to <%= crm%> Custom Object</td>
			<td>
				<span class="table">
				<select name="CreditMemo" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreditMemo"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (fast query)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Copy Payment to <%= crm%> Custom Object</td>
			<td>
				<span class="table">
				<select name="PaymentQB" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PaymentQB"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (fast query)</option>
					<option <%= sel.equals("YesFC")?"selected=\"selected\"":""%> value="YesFC">Yes (fast query with check #)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Remaining Balance</span></td>
			<td>
				<input type="text" name="InvRemBal" class="table"/ <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvRemBal") +"\""):""%>>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Customer/Job Name</span></td>
			<td>
				<input type="text" name="InvCJName" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvCJName") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtran%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppInv"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with <%= crmtran%> Primary Contact</span></td>
			<td>
				<input type="text" name="CORef2Cont4Inv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef2Cont4Inv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Purchase Order#</span></td>
			<td>
				<input type="text" name="InvPONumber" class="table"/ <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvPONumber") +"\""):""%>>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom Checkbox Is Paid</span></td>
			<td>
				<input type="text" name="InvIsPaid" class="table"/ <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvIsPaid") +"\""):""%>>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Invoice Date</span></td>
			<td>
				<input type="text" name="InvDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom field for <%= crm%> <%= crmtran%> Name</span></td>
			<td>
				<input type="text" name="InvQBOppNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvQBOppNm") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Invoice custom mapping 1"):("Aria Invoice to " + fs + " custom mapping 1")%></span></td>
			<td>
				<input type="text" name="InvSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Invoice custom mapping 2"):("Aria Invoice to " + fs + " custom mapping 2")%> (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Inv"	+ "&Solution=" + solutionType%>' target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="InvSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "InvSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="InvOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Invoice Terms</span></td>
			<td>
				<input type="text" name="InvTerm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvTerm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="InvShipVia" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvShipVia") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Default Discount Item</span></td>
			<td>
				<input type="text" name="InvDefDiscItm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvDefDiscItm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="InvClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Invoice Number</span></td>
			<td>
				<input type="text" name="InvRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvRflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="InvDplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvDplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Last Transaction Date</span></td>
			<td>
				<input type="text" name="InvLastTranDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvLastTranDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="InvFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Quantity</span></td>
			<td>
				<span class="table">
				<select name="InvUse4Qty" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvUse4Qty"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Description</span></td>
			<td>
				<span class="table">
				<select name="InvUse4Desc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InvUse4Desc"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="InvAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account</span></td>
			<td>
				<input type="text" name="InvAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvAccRcvC") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">To Group Entries by <%= fs%> Account Name</span></td>
			<td>
				<span class="table">
				<select name="GrpAcctNm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "GrpAcctNm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field to display Aria Account Name</span></td>
			<td>
				<span class="table">
				<select name="DispAcctNm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DispAcctNm"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not display</option>
					<option <%= sel.equals("Memo")?"selected=\"selected\"":""%> value="Memo">Memo</option>
					<option <%= sel.equals("Name")?"selected=\"selected\"":""%> value="Name">Name</option>
				</select>
				</span>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(!sR.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Sales Receipt"):("Aria Dunning Charges to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!sR.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Sales Receipt#</span></td>
			<td>
				<input type="text" name="SRNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Sales Receipt # is generated by</span></td>
			<td>
				<span class="table">
				<select name="SRNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<%} if((sR.equals("SFQB"))||(sR.equals("SF2QB"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && sR.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="SRQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + crm + " Opportunities to " + fs + " Sales Receipts"):("Permitted sync operations from Aria Payments to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="SRSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update an Sales Receipt</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdSR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdSR"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Sales Receipt when</span></td>
			<td>
				<span class="table">
				<select name="CreateNSR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNSR"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
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
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new <%= fs%> Sales Receipt</span></td>
			<td>
				<input type="text" name="SFOppStValSR" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValSR")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Sales Receipt</span></td>
			<td>
				<input type="text" name="SFCrCusFSR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Sales Receipt</span></td>
			<td>
				<input type="text" name="SFCrCusFSRVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSRVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name to create <%= fs%> Sales Receipt</span></td>
			<td>
				<input type="text" name="CORef4SR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name/Field name(s) to create <%= fs%> Sales Receipt Line Items</span></td>
			<td>
				<input type="text" name="CORef4SRLI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SRLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Sales Receipt Line Item Rate</span></td>
			<td>
				<input type="text" name="CORef4SRLIAM" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SRLIAM") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Sales Receipt Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4SRLIQT" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SRLIQT") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Sales Receipt Line Item Service Dates</span></td>
			<td>
				<input type="text" name="CORef4SRLID" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SRLID") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name for <%= fs%> Sales Receipt Line Item Name</span></td>
			<td>
				<input type="text" name="CORef4SRLINm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4SRLINm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Skip <%= crm%> <%= crmtran%> amounts when creating/updating <%= fs%> Sales Receipt</span></td>
			<td>
				<span class="table">
				<select name="UpdSF2SRAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdSF2SRAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling <%= fs%> Item Name</span></td>
			<td>
				<input type="text" name="DummySRSHNm" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummySRSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummySRSHPrc" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummySRSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Custom Sorting of Line Items</span></td>
			<td>
				<input type="text" name="SRLISorting" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRLISorting") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to <%= fs%> Sales Receipt from</span></td>
			<td>
				<span class="table">
				<select name="SR2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SR2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
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
			<td><span class="table">Populate Shipping Address to <%= fs%> Sales Receipt from</span></td>
			<td>
				<span class="table">
				<select name="SR2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SR2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
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
			<td><span class="table">Fill <%= fs%> Sales Receipt Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="SRBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Fill <%= fs%> Sales Receipt Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="SRSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Create Pending Sales Receipt</td>
			<td>
				<span class="table">
				<select name="SRQBPend" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRQBPend"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to convert <%= fs%> Sales Receipt to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FSR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrConc2FSR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to convert <%= fs%> Sales Receipt to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FSRVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrConc2FSRVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= fs%> Line Item Description from <%= crm%> Product Line and Product ones using </span></td>
			<td>
				<span class="table">
				<select name="SRLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Default Customer Full Name</span></td>
			<td>
				<input type="text" name="SRDefCustFlNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRDefCustFlNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Vendor Name/Reference for instant payment</span></td>
			<td>
				<input type="text" name="SRVendInstPmt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRVendInstPmt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Field with a value for instant payment</span></td>
			<td>
				<input type="text" name="SRVendInstPmtVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRVendInstPmtVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Sales Receipt</span></td>
			<td>
				<input type="text" name="SRDefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRDefTemp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field to setup Tax value(s) in <%= fs%> Sales Receipt</span></td>
			<td>
				<input type="text" name="Tax4SR" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "Tax4SR") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Pre-Populate Tax value to <%= fs%> Sales Receipt as</span></td>
			<td>
				<span class="table">
				<select name="Tax4SRValAs" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Tax4SRValAs"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("SalesTaxItem")?"selected=\"selected\"":""%> value="SalesTaxItem">Sales Tax Item</option>
					<option <%= sel.equals("TaxItem")?"selected=\"selected\"":""%> value="TaxItem">Additional Line Item</option>
					<option <%= sel.equals("TaxValue")?"selected=\"selected\"":""%> value="TaxValue">Additional Amount for each Line Item</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Sales Tax Item Full Name</span></td>
			<td>
				<input type="text" name="QSBTaxFullNSR" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QSBTaxFullNSR") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((sR.equals("SFQB"))||(sR.equals("QB2SF"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && sR.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="SRSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + fs + " Sales Receipts to " + crm + " Opportunities"):("Permitted sync operations from " + fs + " to Aria Payments")%></span></td>
			<td>
				<span class="table">
				<select name="SRQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtran%> amounts with calculated <%= fs%> Sales Receipt amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooSRAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooSRAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update <%= crm%> <%= crmtran%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppSR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppSR"):"QBSRCr"; if(sel.equals("")){sel = "QBSRCr";}%>
					<option <%= sel.equals("QBSRCr")?"selected=\"selected\"":""%> value="QBSRCr"><%= fs%> SR created/modified</option>
					<option <%= sel.equals("QBSRCFOpp")?"selected=\"selected\"":""%> value="QBSRCFOpp"><%= fs%> SR field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppSRF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppSRF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppSRFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppSRFVal") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtran%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStageSR" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStageSR") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address Change in <%= fs%> Sales Receipt to</span></td>
			<td>
				<span class="table">
				<select name="SRBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Sales Receipt to</span></td>
			<td>
				<span class="table">
				<select name="SRSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
				<select name="SRExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="SRNoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRNoItems"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="SRMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Remaining Balance</span></td>
			<td>
				<input type="text" name="SRRemBal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRRemBal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Customer/Job Name</span></td>
			<td>
				<input type="text" name="SRCJName" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRCJName") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtran%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppSR" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppSR"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Sales Receipt Date</span></td>
			<td>
				<input type="text" name="SRDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom field for <%= crm%> <%= crmtran%> Name</span></td>
			<td>
				<input type="text" name="SRQBOppNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRQBOppNm") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Sales Receipt custom mapping 1"):("Aria Payment to " + fs + " custom mapping 1")%></span></td>
			<td>
				<input type="text" name="SRSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Sales Receipt custom mapping 2"):("Aria Payment to " + fs + " custom mapping 2")%>(<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=SR" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="SRSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "SRSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="SROwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SROwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="SRShipVia" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRShipVia") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="SRClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created SR Number</span></td>
			<td>
				<input type="text" name="SRRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRRflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="SRDplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRDplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="SRFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Quantity</span></td>
			<td>
				<span class="table">
				<select name="SRUse4Qty" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SRUse4Qty"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="SRAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account used for Dunning Charges</span></td>
			<td>
				<input type="text" name="SRAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRAccRcvC") +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(!est.equals("None")){String crmtrane=((crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?"Quote/":"") + crmtran;%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtrane + " to " + fs + " Estimate"):("Aria Refund to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!est.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtrane%> Custom field with <%= fs%> Estimate#</span></td>
			<td>
				<input type="text" name="EstNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstNumber") +"\""):""%>/>
			</td>
		</tr>
		<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Transactional Object Name</span></td>
			<td>
				<input type="text" name="EstTranObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstTranObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field with <%= fs%> Estimate#</span></td>
			<td>
				<input type="text" name="EstTranObjectLine" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstTranObjectLine") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Estimate # is generated by</span></td>
			<td>
				<span class="table">
				<select name="EstNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<%} if((est.equals("SFQB"))||(est.equals("SF2QB"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && est.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="EstQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + crm + " Opportunities to " + fs + " Estimates"):("Permitted sync operations from Aria Refunds to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="EstSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Estimate when</span></td>
			<td>
				<span class="table">
				<select name="CreateNEst" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNEst"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
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
			<td><span class="table"><%= crm%> <%= crmtrane%> Stage to create new <%= fs%> Estimate</span></td>
			<td>
				<input type="text" name="SFOppStValEst" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValEst")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new <%= fs%> Estimate</span></td>
			<td>
				<input type="text" name="SFCrCusFEst" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFEst") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new <%= fs%> Estimate</span></td>
			<td>
				<input type="text" name="SFCrCusFEstVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFEstVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name to create <%= fs%> Estimate</span></td>
			<td>
				<input type="text" name="CORef4Est" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4Est") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name/Field name(s) to create <%= fs%> Estimate Line Items</span></td>
			<td>
				<input type="text" name="CORef4EstLI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4EstLI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Estimate Line Item Rate</span></td>
			<td>
				<input type="text" name="CORef4EstLIAM" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4EstLIAM") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Estimate Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4EstLIQT" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4EstLIQT") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for <%= fs%> Estimate Line Item Service Dates</span></td>
			<td>
				<input type="text" name="CORef4EstLID" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4EstLID") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name for <%= fs%> Estimate Line Item Name</span></td>
			<td>
				<input type="text" name="CORef4EstLINm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CORef4EstLINm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling <%= fs%> Item Name</span></td>
			<td>
				<input type="text" name="DummyEstSHNm" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyEstSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummyEstSHPrc" size="31" maxlength="210" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyEstSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Custom Sorting of Line Items</span></td>
			<td>
				<input type="text" name="EstLISorting" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstLISorting") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to <%= fs%> Estimate from</span></td>
			<td>
				<span class="table">
				<select name="Est2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Est2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Billing Address</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Billing Address (all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address to <%= fs%> Estimate from</span></td>
			<td>
				<span class="table">
				<select name="Est2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Est2SACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("OSAddr")?"selected=\"selected\"":""%> value="OSAddr"><%= crmtran%> Shipping Address</option>
					<option <%= sel.equals("OSFLAddr")?"selected=\"selected\"":""%> value="OSFLAddr"><%= crmtran%> Shipping Address (custom field with first/last name)</option>
					<option <%= sel.equals("OSNAddr")?"selected=\"selected\"":""%> value="OSNAddr"><%= crmtran%> Shipping Address (custom field with name)</option>
					<option <%= sel.equals("OSCAddr")?"selected=\"selected\"":""%> value="OSCAddr"><%= crmtran%> Shipping Address (all)</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Estimate Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="EstBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Fill <%= fs%> Estimate Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="EstSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
				<select name="EstLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Estimate</span></td>
			<td>
				<input type="text" name="EstDefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstDefTemp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Convert Estimate to SO/Invoice</td>
			<td>
				<span class="table">
				<select name="ConvEst2InvQB" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ConvEst2InvQB"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes (Standard Prefix SO/IN)</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (Custom Prefix)</option>
					<option <%= sel.equals("Man")?"selected=\"selected\"":""%> value="Man">Manually</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Prexix For Invoice Number</span></td>
			<td>
				<input type="text" name="ConvEst2InvQBPrx" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ConvEst2InvQBPrx") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Estimate Default Markup Rate Value</span></td>
			<td>
				<input type="text" name="EstDefMarkRt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstDefMarkRt") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((est.equals("SFQB"))||(est.equals("QB2SF"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && est.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="EstSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + fs + " Estimates to " + crm + " Opportunities"):("Permitted sync operations from " + fs + " to Aria Refunds")%></span></td>
			<td>
				<span class="table">
				<select name="EstQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
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
			<td><span class="table">Create/Update <%= crm%> <%= crmtrane%> when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppEst" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppEst"):"QBEstCr"; if(sel.equals("")){sel = "QBEstCr";}%>
					<option <%= sel.equals("QBEstCr")?"selected=\"selected\"":""%> value="QBEstCr"><%= fs%> Estimate created/modified</option>
					<option <%= sel.equals("QBEstCFOpp")?"selected=\"selected\"":""%> value="QBEstCFOpp"><%= fs%> Estimate field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtrane%></span></td>
			<td>
				<input type="text" name="QBCrOppEstF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppEstF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtrane%></span></td>
			<td>
				<input type="text" name="QBCrOppEstFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppEstFVal") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtrane%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStageEst" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStageEst") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtrane%> amounts with calculated <%= fs%> Estimate amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooEstAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooEstAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address Change in <%= fs%> Estimate to</span></td>
			<td>
				<span class="table">
				<select name="EstBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("OBAddr")?"selected=\"selected\"":""%> value="OBAddr"><%= crmtran%> Billing Address (custom field)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBFLAddr")?"selected=\"selected\"":""%> value="OBFLAddr"><%= crmtran%> Billing Address (custom field with first/last name)</option>
					<option <%= sel.equals("OBNAddr")?"selected=\"selected\"":""%> value="OBNAddr"><%= crmtran%> Billing Address (custom field with name)</option>
					<option <%= sel.equals("OBCAddr")?"selected=\"selected\"":""%> value="OBCAddr"><%= crmtran%> Billing Address (custom field - all)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Estimate to</span></td>
			<td>
				<span class="table">
				<select name="EstSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
				<select name="EstExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create <%= crmtrane%> without line items</td>
			<td>
				<span class="table">
				<select name="EstNoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstNoItems"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="EstMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtrane%> Custom field with Customer/Job Name</span></td>
			<td>
				<input type="text" name="EstCJName" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstCJName") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Estimate Date</span></td>
			<td>
				<input type="text" name="EstDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom field for <%= crm%> <%= crmtrane%> Name</span></td>
			<td>
				<input type="text" name="EstQBOppNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstQBOppNm") +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Estimate custom mapping 1"):("Aria Refund to " + fs + " custom mapping 1")%></span></td>
			<td>
				<input type="text" name="EstSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to " + fs + " Estimate custom mapping 2"):("Aria Refund to " + fs + " custom mapping 2")%> (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Est" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="EstSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "EstSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtrane%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppEst" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppEst"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with <%= fs%> Purchase Order#</span></td>
			<td>
				<input type="text" name="EstPONumber" class="table"/ <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstPONumber") +"\""):""%>>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtrane%> Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="EstOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Customer Terms</span></td>
			<td>
				<input type="text" name="EstTerm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstTerm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="EstShipVia" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstShipVia") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Default Discount Item</span></td>
			<td>
				<input type="text" name="EstDefDiscItm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstDefDiscItm") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="EstClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Estimate Number</span></td>
			<td>
				<input type="text" name="EstRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstRflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="EstDplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstDplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="EstFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use for <%= crm%> Product Quantity</span></td>
			<td>
				<span class="table">
				<select name="EstUse4Qty" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "EstUse4Qty"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="EstAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account used for Refunds</span></td>
			<td>
				<input type="text" name="EstAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstAccRcvC") +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(crm.equals("Aria")){%>
	<!--<%if(!vO.equals("None")){%>
	<span class="labels">Aria Void Transaction to <%= fs%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr><tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="VoidAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VoidAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account used for Void
			Transactions</span></td>
			<td>
				<input type="text" name="VoidAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "VoidAccRcvC") +"\""):""%>/>
			</td>
		</tr>
	</table>
	<%}%>-->
	<span class="labels">Common transactions properties</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Merge all transactions for each <%= fs%> Account into one GL record</span></td>
			<td>
				<span class="table">
				<select name="MrgAllIn1GL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "MrgAllIn1GL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Always process transactions even if voided immediatelly</span></td>
			<td>
				<span class="table">
				<select name="ProcVoidNow" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ProcVoidNow"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span></td>
		</tr>
	</table>
	<%}%>
	<%if((crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && (((!inv.equals("None"))&&(!sR.equals("None")))||((!inv.equals("None"))&&(!est.equals("None")))||((!sR.equals("None"))&&(!est.equals("None")))||((!oJ.equals("None"))&&(!inv.equals("None")))||((!oJ.equals("None"))&&(!sR.equals("None")))||((!oJ.equals("None"))&&(!est.equals("None"))))){%>
	<span class="labels"><%= fs%> Multiple Transactions Support</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> field with Transaction Selection criterion</span></td>
			<td>
				<input type="text" name="TrSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "TrSel") +"\""):""%>/>
			</td>
		</tr>
		<%if(!oJ.equals("None")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Job Selection value</span></td>
			<td>
				<input type="text" name="JobSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "JobSel") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if(!inv.equals("None")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Invoice Selection value</span></td>
			<td>
				<input type="text" name="InvSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvSel") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if(!sR.equals("None")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Sales Receipt Selection value</span></td>
			<td>
				<input type="text" name="SRSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SRSel") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if(!est.equals("None")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Estimate Selection value</span></td>
			<td>
				<input type="text" name="EstSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "EstSel") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
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
<!--<tr><td><%= lnmbr++%></td>
			<td><span class="table">Aria Supplemental Field 1 for additional grouping</span></td>
			<td>
				<input type="text" name="FieldGroup1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "FieldGroup1") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Field to display Supplemental Field 1</span></td>
			<td>
				<span class="table">
				<select name="DispFieldGroup1" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DispFieldGroup1"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not display</option>
					<option <%= sel.equals("Memo")?"selected=\"selected\"":""%> value="Memo">Memo</option>
					<option <%= sel.equals("Name")?"selected=\"selected\"":""%> value="Name">Name</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Aria Supplemental Field 2 for additional grouping</span></td>
			<td>
				<input type="text" name="FieldGroup2" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "FieldGroup2") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Field to display Supplemental Field 2</span></td>
			<td>
				<span class="table">
				<select name="DispFieldGroup2" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DispFieldGroup2"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not display</option>
					<option <%= sel.equals("Memo")?"selected=\"selected\"":""%> value="Memo">Memo</option>
					<option <%= sel.equals("Name")?"selected=\"selected\"":""%> value="Name">Name</option>
				</select>
				</span>
			</td>
		</tr>-->
</body>
</html>




		