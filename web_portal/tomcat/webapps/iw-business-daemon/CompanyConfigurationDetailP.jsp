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
if(solutionType.startsWith("SF") || solutionType.startsWith("ORA") || solutionType.startsWith("MSDCRM")  || solutionType.startsWith("PPOL")){
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
} else if (solutionType.indexOf("PGG") >= 0) {
fs = "Payment Gateway";
} else if (solutionType.indexOf("DBG") >= 0) {
fs = "Database";
} else if (solutionType.indexOf("2OM") >= 0) {
fs = "OMS";
} 
String fsn = (fs.equals("QB"))?"Company Files":((fs.equals("Payment Gateway"))?"Merchant Providers":"Databases");
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
String prd = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePrd");
if(prd.equals("")){prd="None";}
String scr = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeSC");
if(scr.equals("")){scr="None";}
String pI = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePItm");
if(pI.equals("")){pI="None";}
String pSO = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypePSO");
if(pSO.equals("")){pSO="None";}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Configuration Details (Produicts)</title>
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
		set configuration parameters for your solution.
		</span><br/>Products<%if(!scr.equals("None")){%>/Statement Charges<%}%> Configuration Details</td><td align="right" class="labels">User: <%= currentUser%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= currentProfileName%> <%= oldProfileName%> <%= solutionType%> <%= crm%> <%= navigation%>-->
<form action="CompanyConfigurationServletDTP" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
  <input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
  <%if(oldProfileName!=null){%>
  <input type="hidden" name="OldProfile" value="<%= oldProfileName %>"/>
  <%}%>
  <input type="hidden" name="Solution" value="<%= solutionType %>"/>
<p>
	<%if(!(fs.equals("Payment Gateway"))){%><input type="submit" name="submit" value="Previous" class="labels"/><%}%>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
	<br/>
	<br/>
	<%if(!prd.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM") || crm.equals("OMS")  || crm.equals("PPOL"))?(crm + " Product to " + fs + " Item"):("Aria Item/Service to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Binding between <%= crm%> Product and <%= fs%> Item</span></td>
			<td>
				<span class="table">
				<select name="PrdBind" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdBind"):"PrdNm"; if(sel.equals("")){sel = "PrdNm";}%>
					<option <%= sel.equals("PrdNm")?"selected=\"selected\"":""%> value="PrdNm">Product Name - Item Name</option>
					<option <%= sel.equals("PrdNSKU")?"selected=\"selected\"":""%> value="PrdNSKU">Product Name - Item SKU(custom)</option>
					<option <%= sel.equals("PrdSKU")?"selected=\"selected\"":""%> value="PrdSKU">Product Code - Item SKU(custom)</option>
					<option <%= sel.equals("PrdCNSKU")?"selected=\"selected\"":""%> value="PrdCNSKU">Product Code - Item Name</option>
					<option <%= sel.equals("PrdCFNm")?"selected=\"selected\"":""%> value="PrdCFNm">Product Custom Field - Item Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Item Name</span></td>
			<td>
				<input type="text" name="SFFldPNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFFldPNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Item/Product Name Truncating Mode</span></td>
			<td>
				<input type="text" name="ItmPrdNmTrMd" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ItmPrdNmTrMd") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Custom Object Name to create <%= fs%> Item</span></td>
			<td>
				<input type="text" name="CONItem" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CONItem") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name For Sales Price</span></td>
			<td>
				<input type="text" name="SFPrdSPrcCstm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFPrdSPrcCstm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Product Group/Assembly Name</span></td>
			<td>
				<input type="text" name="PrdGrpNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdGrpNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Product Group/Assembly Quantity</span></td>
			<td>
				<input type="text" name="PrdGrpQt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdGrpQt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Parent Item Name for sub-items</span></td>
			<td>
				<input type="text" name="PrdItmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdItmNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Bind <%= crm%> Product to <%= fs%> Item using Item Name only</td>
			<td>
				<span class="table">
				<select name="SF2QBItmNmOnl" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SF2QBItmNmOnl"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for Prefered Vendor/Item Cost
			required</td>
			<td>
				<span class="table">
				<select name="SFCostItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFCostItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes, using Cost custom object</option>
					<option <%= sel.equals("YesU")?"selected=\"selected\"":""%> value="YesU">Yes, always update Cost</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for Item weight required</td>
			<td>
				<span class="table">
				<select name="SFWeightItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFWeightItm"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Mapping for <%= fs%> Price Level required</td>
			<td>
				<span class="table">
				<select name="SF2QBPriceLevel" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SF2QBPriceLevel"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for <%= fs%> Inventory Item Sites required</td>
			<td>
				<span class="table">
				<select name="SF2QBInvItemSite" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SF2QBInvItemSite"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for <%= fs%> Serial Numbers/Lots required</td>
			<td>
				<span class="table">
				<select name="SerNumLot" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SerNumLot"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("SN")?"selected=\"selected\"":""%> value="SN">Serial Numbers</option>
					<option <%= sel.equals("Lot")?"selected=\"selected\"":""%> value="Lot">Lots</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Purchase Description</span></td>
			<td>
				<input type="text" name="PrdPDescNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdPDescNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Quantity on Hand</span></td>
			<td>
				<input type="text" name="PrdQOnHand" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdQOnHand") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field containing Manufacturer Part Number</span></td>
			<td>
				<input type="text" name="PrdManPNum" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdManPNum") +"\""):""%>/>
			</td>
		</tr>
		<%} if((prd.equals("SFQB"))||(prd.equals("SF2QB"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && prd.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="PrdQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + crm + " Products to " + fs + " Items"):("Permitted sync operations from Aria Items/Services to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="PrdSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(fs.equals("NetSuite")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Tax Schedule Internal Id</span></td>
			<td>
				<input type="text" name="NSTaxId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSTaxId") +"\""):""%>/>
			</td>
		</tr> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Purchase Tax Code Internal Id</span></td>
			<td>
				<input type="text" name="NSTaxPCId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSTaxPCId") +"\""):""%>/>
			</td>
		</tr> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Sales Tax Code Internal Id</span></td>
			<td>
				<input type="text" name="NSTaxSCId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSTaxSCId") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Income Account Internal Id</span></td>
			<td>
				<input type="text" name="NSIncAccId" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "NSIncAccId") +"\""):""%>/>
			</td>
		</tr><%}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field to select <%= fs%> Item Type</span></td>
			<td>
				<input type="text" name="QBItmTp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create COA Accounts Automatically</td>
			<td>
				<span class="table">
				<select name="CreateCOAAuto" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateCOAAuto"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<!--<tr><td></td>
			<td><span class="table"><%= crm%> Product field to select <%= fs%> Item Account</span></td>
			<td>
				<input type="text" name="QBItmAcct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmAcct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td></td>
			<td><span class="table"><%= crm%> Product field to select <%= fs%> Inventory Item COGS Account</span></td>
			<td>
				<input type="text" name="QBItmCAcct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmCAcct") +"\""):""%>/>
			</td>
		</tr>-->
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Inventory Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Inventory Assembly Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpA" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpA") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Income Account for Inventory/Inventory Assembly Item Type</span></td>
			<td>
				<input type="text" name="DIAItmTpI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DIAItmTpI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default COGS Account for Inventory/Inventory Assembly Item Type</span></td>
			<td>
				<input type="text" name="DCAItmTpI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DCAItmTpI") +"\""):"Cost of Goods Sold"%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Asset Account for Inventory/Inventory Assembly Item Type</span></td>
			<td>
				<input type="text" name="DAAItmTpI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DAAItmTpI") +"\""):"Inventory Asset"%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Transactional <%= crm%> Field for Inventory/Inventory Assembly Item Site</span></td>
			<td>
				<input type="text" name="InvItemSite" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "InvItemSite") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Non-Inventory Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpN" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpN") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Account (Income) for Non-Inventory Item Type</span></td>
			<td>
				<input type="text" name="DAItmTpN" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DAItmTpN") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for Reimbursable Non-Inventory Items required</td>
			<td>
				<span class="table">
				<select name="SFNOIItmRI" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFNOIItmRI"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Reimbursable Non-Inventory Item Selection</span></td>
			<td>
				<input type="text" name="ReimNmNISel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimNmNISel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value for Reimbursable Non-Inventory Item Selection </span></td>
			<td>
				<input type="text" name="ReimValNISel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimValNISel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Expense Account for Reimbursable Non-Inventory Item Type</span></td>
			<td>
				<input type="text" name="DEAItmTpN" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DEAItmTpN") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Service Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpS" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpS") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Account (Income) for Service Item Type</span></td>
			<td>
				<input type="text" name="DAItmTpS" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DAItmTpS") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for Reimbursable Service Items required</td>
			<td>
				<span class="table">
				<select name="SFSrvItmRI" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFSrvItmRI"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Reimbursable Service Item Selection</span></td>
			<td>
				<input type="text" name="ReimNmServSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimNmServSel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value for Reimbursable Service Item Selection </span></td>
			<td>
				<input type="text" name="ReimValServSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimValServSel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Expense Account for Service Item Type with Cost Support</span></td>
			<td>
				<input type="text" name="DEAItmTpS" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DEAItmTpS") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Other Charge Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Account (Income) for Other Charge Item Type</span></td>
			<td>
				<input type="text" name="DAItmTpO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DAItmTpO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Support for Reimbursable Other Charge Items required</td>
			<td>
				<span class="table">
				<select name="SFOCItmRI" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFOCItmRI"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Reimbursable Other Charge Item Selection</span></td>
			<td>
				<input type="text" name="ReimNmOCSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimNmOCSel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value for Reimbursable Other Charge Item Selection </span></td>
			<td>
				<input type="text" name="ReimValOCSel" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ReimValOCSel") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Expense Account for Other Charge Item Type with Cost Support</span></td>
			<td>
				<input type="text" name="DEAItmTpO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DEAItmTpO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product field value for Discount Item Type</span></td>
			<td>
				<input type="text" name="QBItmTpD" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBItmTpD") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Account for Discount Item Type</span></td>
			<td>
				<input type="text" name="DAItmTpD" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DAItmTpD") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to Define Margin</span></td>
			<td>
				<input type="text" name="SFMargFldNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFMargFldNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name For Sales Price with Margin</span></td>
			<td>
				<input type="text" name="SFSPMargFldNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFSPMargFldNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use <%= crm%> Name for Item Description</td>
			<td>
				<span class="table">
				<select name="SFNm4ItmDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SFNm4ItmDesc"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes (overwrite)</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (combine)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Tax Code for Taxable Items</span></td>
			<td>
				<input type="text" name="DefTaxCode" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefTaxCode") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Tax Code for Non-Taxable Items</span></td>
			<td>
				<input type="text" name="DefNoTaxCode" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefNoTaxCode") +"\""):""%>/>
			</td>
		</tr>
		<%}}}%>
		<%if((prd.equals("SFQB"))||(prd.equals("QB2SF"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && prd.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="PrdSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + fs + " Items to " + crm + " Products"):("Permitted sync operations from " + fs + " to Aria Items/Services")%></span></td>
			<td>
				<span class="table">
				<select name="PrdQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
					<option <%= sel.equals("UOPPSE")?"selected=\"selected\"":""%> value="UOPPSE">Update Only (Exclusive)</option>
				</select>
				</span>
			</td>
		</tr>
<%}if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to Define Margin</span></td>
			<td>
				<input type="text" name="MargFldNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "MargFldNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Field Values to Define Margins</span></td>
			<td>
				<input type="text" name="MargFldVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "MargFldVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Standard Price Margin Values</span></td>
			<td>
				<input type="text" name="MargVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "MargVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">List of Default Currencies</span></td>
			<td>
				<input type="text" name="DefCurList" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefCurList") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field for <%= crm%> Product Family</span></td>
			<td>
				<input type="text" name="PrdFamily" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdFamily") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom Field to Suppress Transaction</span></td>
			<td>
				<input type="text" name="PrdNoLoad" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrdNoLoad") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate <%= crm%> Product Code with</span></td>
			<td>
				<span class="table">
				<select name="PrdCode" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdCode"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Do Not Populate</option>
					<option <%= sel.equals("ItmNm")?"selected=\"selected\"":""%> value="ItmNm">Item Name</option>
					<option <%= sel.equals("ItmDesc")?"selected=\"selected\"":""%> value="ItmDesc">Item Description</option>
					<option <%= sel.equals("ItmMPN")?"selected=\"selected\"":""%> value="ItmMPN">Item Manufacturer Part Number</option>
					<option <%= sel.equals("ItmSKU")?"selected=\"selected\"":""%> value="ItmSKU">Item SKU(custom)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product to <%= fs%> Item custom
			mapping 1</span></td>
			<td>
				<input type="text" name="ProdSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ProdSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Product to <%= fs%> Item custom
			mapping 2
			(<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Prod" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="ProdSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "ProdSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "ProdSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Upload Inactive <%= fs%> Items</span></td>
			<td>
				<span class="table">
				<select name="PrdUplInact" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PrdUplInact"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Inventory Adjustment Object</span></td>
			<td>
				<input type="text" name="IAObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Inventory Adjustment Line Object</span></td>
			<td>
				<input type="text" name="IAObjectL" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAObjectL") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment #</span></td>
			<td>
				<input type="text" name="IANumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IANumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Inventory Adjusment # is generated by</span></td>
			<td>
				<span class="table">
				<select name="IANumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "IANumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen">QB</option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to <%= fs%> Inventory Adjustment</span></td>
			<td>
				<span class="table">
				<select name="IASF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "IASF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new <%= fs%> Inventory Adjustment</span></td>
			<td>
				<input type="text" name="SFCrCusFIA" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFIA") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Inventory Adjustment</span></td>
			<td>
				<input type="text" name="SFCrCusFIAVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFIAVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Inventory Adjustment to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="IAQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "IAQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Item Name</span></td>
			<td>
				<input type="text" name="IAItem" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAItem") +"\""):""%>/>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Account</span></td>
			<td>
				<input type="text" name="IAAccnt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAAccnt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Date</span></td>
			<td>
				<input type="text" name="IADate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IADate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Memo</span></td>
			<td>
				<input type="text" name="IAMemo" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAMemo") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Quantity Difference</span></td>
			<td>
				<input type="text" name="IAQttyDif" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAQttyDif") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field with <%= fs%> Inventory Adjustment Value Difference</span></td>
			<td>
				<input type="text" name="IAValDif" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IAValDif") +"\""):""%>/>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to <%= fs%> Inventory Adjustment custom
			mapping 1</span></td>
			<td>
				<input type="text" name="IASFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IASFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to <%= fs%> Inventory Adjustment custom
			mapping 2
			(<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=IA" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="IASFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "IASFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "IASFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>		
		<%}%>
	</table>
	<%}%>
	<%if(!scr.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " Object to " + fs + " Statement Charges"):("Aria External Charges to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object for <%= fs%> Statement Charges</span></td>
			<td>
				<input type="text" name="SCCObject" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCCObject") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Statement Charges Number</span></td>
			<td>
				<input type="text" name="SCNumber" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCNumber") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Statement Charges # is generated by</span></td>
			<td>
				<span class="table">
				<select name="SCNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SCNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen">QB</option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) with <%= fs%> Statement Charges Customer/Job</span></td>
			<td>
				<input type="text" name="QBCust4SC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCust4SC") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with <%= fs%> Statement Charges Date</span></td>
			<td>
				<input type="text" name="SCDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for <%= fs%> Statement Charges Item Full Name</span></td>
			<td>
				<input type="text" name="SCItem" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCItem") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for <%= fs%> Statement Charges Rate</span></td>
			<td>
				<input type="text" name="SCRate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCRate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for <%= fs%> Statement Charges Quantity</span></td>
			<td>
				<input type="text" name="SCQtty" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCQtty") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for <%= fs%> Statement Charges Amount</span></td>
			<td>
				<input type="text" name="SCAmount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCAmount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Statement Charges AR Account Full Name</span></td>
			<td>
				<input type="text" name="SCARAccount" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCARAccount") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Statement Charges Class</span></td>
			<td>
				<input type="text" name="SCClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Statement Charges Description</span></td>
			<td>
				<input type="text" name="SCDesc" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCDesc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Statement Charges Billed Date</span></td>
			<td>
				<input type="text" name="SCBDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCBDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for <%= fs%> Statement Charges Due Date</span></td>
			<td>
				<input type="text" name="SCDDate" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCDDate") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to <%= fs%> Statement Charges</span></td>
			<td>
				<span class="table">
				<select name="SCSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SCSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new <%= fs%> Statement Charges</span></td>
			<td>
				<input type="text" name="SFCrCusFSC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSC") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Statement Charges</span></td>
			<td>
				<input type="text" name="SFCrCusFSCVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFSCVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Statement Charges to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="SCQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SCQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Statement Charges custom mapping 1</span></td>
			<td>
				<input type="text" name="SCSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to <%= fs%> Statement Charges custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=SC" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="SCSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "SCSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Statement Charges</span></td>
			<td>
				<input type="text" name="SCRflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SCRflct") +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>	
	<%if(!pI.equals("None")&& (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))){%>
	<span class="labels">IMS Item to <%= fs%> Inventory Item</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Binding between IMS Item and <%= fs%> Inventory Item</span></td>
			<td>
				<span class="table">
				<select name="PItmBind" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmBind"):"PItmNm"; if(sel.equals("")){sel = "PItmNm";}%>
					<option <%= sel.equals("PItmNm")?"selected=\"selected\"":""%> value="PItmNm">IMS Item Name - <%= fs%> Item Name</option>
					<option <%= sel.equals("PItmNSKU")?"selected=\"selected\"":""%> value="PItmNSKU">IMS Item Name - <%= fs%> Item SKU(custom)</option>
					<option <%= sel.equals("PItmSKU")?"selected=\"selected\"":""%> value="PItmSKU">IMS Item Code - <%= fs%> Item SKU(custom)</option>
					<option <%= sel.equals("PItmCNSKU")?"selected=\"selected\"":""%> value="PItmCNSKU">IMS Item Code - <%= fs%> Item Name</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field containing Item Name for sub-items</span></td>
			<td>
				<input type="text" name="PItmItmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PItmItmNm") +"\""):""%>/>
			</td>
		</tr>
		<%if((pI.equals("SFQB"))||(pI.equals("SF2QB"))){%>
		<%if(pI.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="PItmQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from IMS Items to <%= fs%> Inventory Items</span></td>
			<td>
				<span class="table">
				<select name="PItmSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field to select <%= fs%> Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field value for Inventory Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTpI" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTpI") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field value for Non-Inventory Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTpN" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTpN") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field value for Service Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTpS" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTpS") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field value for Other Charge Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTpO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTpO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Item field value for Discount Item Type</span></td>
			<td>
				<input type="text" name="QBPItmTpD" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBPItmTpD") +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<%if((pI.equals("SFQB"))||(pI.equals("QB2SF"))){%>
		<%if(pI.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="PItmSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= fs%> Inventory Items
			to IMS Items</span></td>
			<td>
				<span class="table">
				<select name="PItmQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Location</span></td>
			<td>
				<input type="text" name="DefaultLoc" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefaultLoc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Item Group</span></td>
			<td>
				<input type="text" name="DefaultGrp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefaultGrp") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Unit of Measure</span></td>
			<td>
				<input type="text" name="DefaultUnit" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DefaultUnit") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Custom Field to Suppress Transaction</span></td>
			<td>
				<input type="text" name="PItmNoLoad" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PItmNoLoad") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Upload Inactive <%= fs%> Items</span></td>
			<td>
				<span class="table">
				<select name="PItmUplInact" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PItmUplInact"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
	</table>
	<%}%>
	<br/>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%><%if(solutionType.endsWith("2") || solutionType.endsWith("1") || solutionType.endsWith("B")){%>
	<span class="labels"><%= crm%> Price Book</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Price Book Name</span></td>
			<td>
				<input type="text" name="PrBookNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PrBookNm") +"\""):""%>/>
			</td>
		</tr>
		</table><br/><%}}%><%if(solutionType.endsWith("PGG")){%>
		<span class="labels">Payment Gateway Settings</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Show Credit Card Types</span></td>
			<td>
				<span class="table">
				<select name="ShowCCTypes" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "ShowCCTypes"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Scheduler Type</span></td>
			<td>
				<span class="table">
				<select name="SchedCCType" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "SchedCCType"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Stored CC data supported</span></td>
			<td>
				<span class="table">
				<select name="StrdCCSpprt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "StrdCCSpprt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
	</table><%}%><span class="labels">Multiple <%= (fs.equals("Accpac") && crm.equals("OMS"))?crm:fs%> <%= (fs.equals("Accpac") && crm.equals("OMS"))?"Sites":fsn%> Support</span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Number of <%= (fs.equals("Accpac") && crm.equals("OMS"))?crm:fs%> <%= (fs.equals("Accpac") && crm.equals("OMS"))?"Sites":fsn%></span></td>
			<td>
				<%String ncf = ConfigContext.getConfigurationValue(cfrn, cfr, "QBCompFilNum");%>
				<input type="text" name="QBCompFilNum" class="table" <%= edit?("value=\"" + (ncf.equals("")?"1":ncf) +"\""):("value=\"1\"")%> <%= (solutionType.endsWith("1") || solutionType.endsWith("QB") || solutionType.endsWith("ACC") || solutionType.endsWith("NS") || solutionType.endsWith("PT") || solutionType.endsWith("GP") || solutionType.endsWith("PGG") || solutionType.endsWith("DBG") || solutionType.endsWith("OMC"))?"":"disabled"%>/>
			</td>
		</tr>
	</table>	
	<!--<%if(!pSO.equals("None")){%>
	<span class="labels"><%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"IMS Sales Order to " + fs + " Sales Order":("Aria Write-Off to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || (crm.equals("Aria") && (!pSO.equals("Ar2QBJ")))){%>
		<%if((pSO.equals("SFQB"))||(pSO.equals("SF2QB"))){%>
		<%if(crm.equals("SF") && pSO.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= fs%> to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="PSOQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"Permitted sync operations from IMS Sales Order to " + fs + " Order":("Permitted sync operations from Aria Write-Offs to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="PSOSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new <%= fs%> Sales Order when</span></td>
			<td>
				<span class="table">
				<select name="CreateNPSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNPSO"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When IMS Sales Order created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc">IMS Sales Order is in certain status</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp">IMS Sales Order field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Sales Order Status to create new <%= fs%> Sales
			Order</span></td>
			<td>
				<input type="text" name="SFOppStValPSO" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValPSO")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new <%= fs%> Sales Order</span></td>
			<td>
				<input type="text" name="SFCrCusFPSO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPSO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new <%= fs%> Sales Order</span></td>
			<td>
				<input type="text" name="SFCrCusFPSOVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFCrCusFPSOVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling <%= fs%> Sales Order Item Name</span></td>
			<td>
				<input type="text" name="DummyPSOSHNm" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyPSOSHNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummyPSOSHPrc" size="31" maxlength="31" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "DummyPSOSHPrc") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Default Shipping and Handling for Dummy Sales Order</td>
			<td>
				<span class="table">
				<select name="UseDmy4DmyPSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseDmy4DmyPSO"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="PSO2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSO2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("SOBAddr")?"selected=\"selected\"":""%> value="SOBAddr">Sales Order Billing Address (custom field)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address to <%= fs%> Sales Order from</span></td>
			<td>
				<span class="table">
				<select name="PSO2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSO2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("SOSAddr")?"selected=\"selected\"":""%> value="SOSAddr">Sales Order Delivery Address</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Sales Order Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="PSOBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AsATNA")?"selected=\"selected\"":""%> value="AsATNA">Attn To/Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fill <%= fs%> Sales Order Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="PSOSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not fill</option>
					<option <%= sel.equals("AsSF")?"selected=\"selected\"":""%> value="AsSF">As <%= crm%> Source</option>
					<option <%= sel.equals("AsNA")?"selected=\"selected\"":""%> value="AsNA">Name/Address</option>
					<option <%= sel.equals("AaFLA")?"selected=\"selected\"":""%> value="AaFLA">First Name+Last Name/Address</option>
					<option <%= sel.equals("AsFLNA")?"selected=\"selected\"":""%> value="AsFLNA">First Name+Last Name/Name/Address</option>
					<option <%= sel.equals("AsATNA")?"selected=\"selected\"":""%> value="AsATNA">Attn To/Name/Address</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering <%= crm%> Field Name for Sales Order Operations</span></td>
			<td>
				<input type="text" name="PSOPREmNm" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOPREmNm") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Sales Order to be Printed</span></td>
			<td>
				<input type="text" name="PSOToBePrt" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOToBePrt") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Sales Order to be Emailed</span></td>
			<td>
				<input type="text" name="PSOToBeEml" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOToBeEml") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Sales Order</span></td>
			<td>
				<input type="text" name="PSODefTemp" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSODefTemp") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((pSO.equals("SFQB"))||(pSO.equals("QB2SF"))){%>
		<%if((crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")) && pSO.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to <%= fs%> upload required</td>
			<td>
				<span class="table">
				<select name="PSOSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"Permitted sync operations from " + fs + " Sales Orders to IMS Sales Orders":("Permitted sync operations from " + fs + " to Aria Write-Offs")%></span></td>
			<td>
				<span class="table">
				<select name="PSOQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){if(solutionType.endsWith("B") || solutionType.endsWith("C")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update IMS Sales Order when</span></td>
			<td>
				<span class="table">
				<select name="CreateUpdateOppPSO" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppPSO"):"QBPSOCr"; if(sel.equals("")){sel = "QBPSOCr";}%>
					<option <%= sel.equals("QBPSOCr")?"selected=\"selected\"":""%> value="QBPSOCr"><%= fs%> Sales Order created/modified</option>
					<option <%= sel.equals("QBPSOCFOpp")?"selected=\"selected\"":""%> value="QBPSOCFOpp"><%= fs%> Sales Order field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update IMS Sales Order</span></td>
			<td>
				<input type="text" name="QBCrOppPSOF" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppPSOF") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update IMS Sales Order</span></td>
			<td>
				<input type="text" name="QBCrOppPSOFVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "QBCrOppPSOFVal") +"\""):""%>/>
			</td>	
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> Sales Order Status</span></td>
			<td>
				<input type="text" name="SFOppStagePSO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStagePSO") +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update IMS Sales Order amounts with calculated <%= fs%> Sales Order amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooPSOAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooPSOAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="PSOBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>					
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ABAddr")?"selected=\"selected\"":""%> value="ABAddr">Account Billing Address</option>
					<option <%= sel.equals("SOBAddr")?"selected=\"selected\"":""%> value="SOBAddr">Sales Order Billing Address (custom field)</option>
					<option <%= sel.equals("OBPCAddr")?"selected=\"selected\"":""%> value="OBPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Shipping Address Change in <%= fs%> Sales Order to</span></td>
			<td>
				<span class="table">
				<select name="PSOSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("ASAddr")?"selected=\"selected\"":""%> value="ASAddr">Account Shipping Address</option>
					<option <%= sel.equals("SOSAddr")?"selected=\"selected\"":""%> value="SOSAddr">Sales Order Delivery Address</option>
					<option <%= sel.equals("OSPCAddr")?"selected=\"selected\"":""%> value="OSPCAddr">Primary Contact Mailing Address</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create Group Type Sales Order</span></td>
			<td>
				<input type="text" name="CreateGoupPSO" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupPSO") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create Group Type Sales Order</span></td>
			<td>
				<input type="text" name="CreateGoupPSOVal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "CreateGoupPSOVal") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Expand <%= crm%> Group Product after <%= fs%> Group Item expanded</span></td>
			<td>
				<span class="table">
				<select name="PSOExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="PSOMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Sales Order Custom field with Remaining Balance</span></td>
			<td>
				<input type="text" name="PSORemBal" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSORemBal") +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"IMS Sales Order to " + fs + " Sales Order custom mapping 1":("Aria Write-Off to " + fs + " custom mapping 1")%></span></td>
			<td>
				<input type="text" name="PSOSFQBCMap" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSFQBCMap") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><%= (crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL"))?"IMS Sales Order to " + fs + " Sales Order custom mapping 2":("Aria Write-Off to " + fs + " custom mapping 2")%> (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=PSO" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="PSOSFQBCMap1" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOSFQBCMap1") +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "PSOSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">IMS Sales Order Owner to <%= fs%> Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="PSOOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="PSOClass" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSOClass") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created SO Number</span></td>
			<td>
				<input type="text" name="PSORflct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSORflct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="PSODplct" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "PSODplct") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create/Update Pending Invoice Automatically when
			Invoice Line is created</td>
			<td>
				<span class="table">
				<select name="CUPenInv" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CUPenInv"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="PSOFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "PSOFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesF")?"selected=\"selected\"":""%> value="YesF">Yes (by Full Name Only)</option>
				</select>
				</span>
			</td>
		</tr>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="pSOAccRcv" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "pSOAccRcv") +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Expense Account used for Write-Offs</span></td>
			<td>
				<input type="text" name="pSOAccRcvC" class="table" <%= edit?("value=\"" + ConfigContext.getConfigurationValue(cfrn, cfr, "pSOAccRcvC") +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>-->
<p>
	<%if(!(fs.equals("Payment Gateway"))){%><input type="submit" name="submit" value="Previous" class="labels"/><%}%>
	<input type="submit" name="submit" value="Next" class="labels"/>
	</p>
</form>

<br/>
<br/>
<%}else{%>
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