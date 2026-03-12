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
String next = (navigation.equals("B"))?"/CompanyConfigurationDetailT1.jsp":"/CompanyConfigurationDetailP.jsp";
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
} else if (solutionType.indexOf("PT") >= 0) {
fs = "Sage";
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
String oC = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCheck");
if(oC.equals("")){oC="None";}
String cm = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCM");
if(cm.equals("")){cm="None";}
String vc = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeVC");
if(vc.equals("")){vc="None";}
String coa = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCOA");
if(coa.equals("")){coa="None";}
String je = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeJE");
if(je.equals("")){je="None";}
String ccc = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeCCC");
if(ccc.equals("")){ccc="None";}
String bp = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeBP");
if(bp.equals("")){bp="None";}
String ttr = ConfigContext.getConfigurationValue(cfrn, cfr, "SyncTypeTT");
if(ttr.equals("")){ttr="None";}
if(crm.equals("")){crm="None";}%>
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
<%if(cm.equals("None") && vc.equals("None") && coa.equals("None") && je.equals("None") && oC.equals("None") && ccc.equals("None") && bp.equals("None") && ttr.equals("None")){
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
		</span><br/>Transactions (<%if(!oC.equals("None")){%>Check<%}%><%if(!bp.equals("None")){%> Bill Payment<%}%><%if(!ccc.equals("None")){%> CC Charge<%}%><%if(!cm.equals("None")){%> CM<%}%><%if(!vc.equals("None")){%> Vendor Credit<%}%><%if(!coa.equals("None")){%> COA<%}%><%if(!je.equals("None")){%> Journal Entry<%}%><%if(!ttr.equals("None")){%> Time Tracking<%}%>) Configuration Details</td><td align="right" class="labels">User: <%= HtmlEncoder.encode(currentUser)%></td><td align="right"><a href='<%= "IWLogin.jsp" + brandSol1%>' target="_top" class="labels">Logout</a></td><td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
	</tr>
</table>
<!--<%= HtmlEncoder.encode(currentProfileName)%> <%= HtmlEncoder.encode(oldProfileName)%> <%= HtmlEncoder.encode(solutionType)%> <%= HtmlEncoder.encode(crm)%> <%= HtmlEncoder.encode(navigation)%>-->
<form action="CompanyConfigurationServletDTT2" method="post">
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
		
	<%if(!oC.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to QB Check"):("Aria External Charges to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!oC.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for QB Commission Check</span></td>
			<td>
				<input type="text" name="CheckCObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckCObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Commission Check#</span></td>
			<td>
				<input type="text" name="CheckNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Commission Check # is generated by</span></td>
			<td>
				<span class="table">
				<select name="CheckNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CheckNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Bank Account Name to create comission QB Check</span></td>
			<td>
				<input type="text" name="QBBank4Check" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBBank4Check")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB GL Account Name to create comission QB Check</span></td>
			<td>
				<input type="text" name="QBGL4Check" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBGL4Check")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Commission Check Amount</span></td>
			<td>
				<input type="text" name="CheckAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Field with QB Commission Check Date</span></td>
			<td>
				<input type="text" name="CheckDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Commission Check Payee Reference</span></td>
			<td>
				<input type="text" name="CheckPayee" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckPayee")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for QB Compensation Check</span></td>
			<td>
				<input type="text" name="CheckCObjectCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckCObjectCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Compensation Check#</span></td>
			<td>
				<input type="text" name="CheckNumberCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckNumberCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Compensation Check # is generated by</span></td>
			<td>
				<span class="table">
				<select name="CheckNumGenCmp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CheckNumGenCmp"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Bank Account Name to create QB Compensation Check</span></td>
			<td>
				<input type="text" name="QBBank4CheckCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBBank4CheckCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB GL Account Name to create QB Compensation Check</span></td>
			<td>
				<input type="text" name="QBGL4CheckCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBGL4CheckCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Compensation Check Amount</span></td>
			<td>
				<input type="text" name="CheckAmountCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckAmountCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Field with QB Compensation Check Date</span></td>
			<td>
				<input type="text" name="CheckDateCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckDateCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Compensation Check Payee Reference</span></td>
			<td>
				<input type="text" name="CheckPayeeCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckPayeeCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Customer/Job</span></td>
			<td>
				<input type="text" name="CheckCustJob" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckCustJob")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> <%= crmtran%> to QB Checks</span></td>
			<td>
				<span class="table">
				<select name="CheckSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CheckSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
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
			<td><span class="table">Create new QB Check when</span></td>
			<td>
				<span class="table">
				<select name="CreateNCheck" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNCheck"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
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
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new QB Check</span></td>
			<td>
				<input type="text" name="SFOppStValCheck" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValCheck")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Check</span></td>
			<td>
				<input type="text" name="SFCrCusFCheck" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCheck")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Check</span></td>
			<td>
				<input type="text" name="SFCrCusFCheckVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCheckVal")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Address to QB Check from</span></td>
			<td>
				<span class="table">
				<select name="Check2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Check2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
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
			<td><span class="table">Fill QB Check Street Address with</span></td>
			<td>
				<span class="table">
				<select name="CheckBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CheckBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Permitted sync operations from QB Cheks to <%= crm%> <%= crmtran%></span></td>
			<td>
				<span class="table">
				<select name="CheckQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CheckQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Check custom mapping 1</span></td>
			<td>
				<input type="text" name="CheckSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Check custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Check" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="CheckSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "CheckSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="CheckClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Memo</span></td>
			<td>
				<input type="text" name="CheckMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckMemo")) +"\""):""%>/>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="CheckAccRcv" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckAccRcv")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account used for External Charges</span></td>
			<td>
				<input type="text" name="CheckAccRcvC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CheckAccRcvC")) +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(!bp.equals("None")){String crmtranp=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?"Object":crmtran;%>
	<span class="labels"><%= crm + " " + crmtranp + " to QB Bill Payment"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
<%if(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for QB Bill Payment</span></td>
			<td>
				<input type="text" name="BPObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPObject")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Bill Payment#</span></td>
			<td>
				<input type="text" name="BPNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Bill # Payment is applied to</span></td>
			<td>
				<input type="text" name="BPAppldNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPAppldNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Bill Payment # is generated by</span></td>
			<td>
				<span class="table">
				<select name="BPNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BPNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB AP Account Name </span></td>
			<td>
				<input type="text" name="QBBank4BP" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBBank4BP")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Bank/CreditCard Account Name </span></td>
			<td>
				<input type="text" name="QBDep4BP" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBDep4BP")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for QB Bill Payment Amount</span></td>
			<td>
				<input type="text" name="BPAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field with QB Bill Payment
			Date</span></td>
			<td>
				<input type="text" name="BPDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for QB Bill Payment Payee
			Reference</span></td>
			<td>
				<input type="text" name="BPPayee" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPPayee")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for QB Bill Payment Lines</span></td>
			<td>
				<input type="text" name="BPCObjectCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPCObjectCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to
			QB Bill Payment</span></td>
			<td>
				<span class="table">
				<select name="BPSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "BPSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Bill Payment</span></td>
			<td>
				<input type="text" name="SFCrCusFBPmnt" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFBPmnt")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Bill Payment</span></td>
			<td>
				<input type="text" name="SFCrCusFBPmntVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFBPmntVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> to QB Bill Payment custom mapping
			1</span></td>
			<td>
				<input type="text" name="BPSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> to QB Bill Payment custom mapping
			2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=BP" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="BPSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "BPSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for Payment Memo</span></td>
			<td>
				<input type="text" name="BPMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranp%> Field for Created Payment Number</span></td>
			<td>
				<input type="text" name="BPRflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"BPRflct")) +"\""):""%>/>
			</td>
		</tr><%}%>
	</table>
	<%}%>
	<%if(!ccc.equals("None")){%>
	<span class="labels"><%= crm + " " + crmtran + " to QB Credit Card Charges"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for QB Credit Card Charge</span></td>
			<td>
				<input type="text" name="CCCCObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCCObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Credit Card Charge#</span></td>
			<td>
				<input type="text" name="CCCNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Credit Card Charge # is generated by</span></td>
			<td>
				<span class="table">
				<select name="CCCNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CCCNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Account Name </span></td>
			<td>
				<input type="text" name="QBBank4CCC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBBank4CCC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to create QB Expense Credit Card Charge Lines</span></td>
			<td>
				<input type="text" name="CCCCObjectCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCCObjectCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object to create QB Item Credit Card Charge Lines</span></td>
			<td>
				<input type="text" name="CORef4CCCLI" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CCCLI")) +"\""):""%>/>
			</td>
		</tr>	
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name(s) for/Default QB Expense Account Name to create QB Credit Card Charge</span></td>
			<td>
				<input type="text" name="QBGL4CCC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBGL4CCC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name(s) for/Default QB Item Name to create QB Credit Card Charge</span></td>
			<td>
				<input type="text" name="QBItem4CCC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBItem4CCC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Credit Card Charge Amount</span></td>
			<td>
				<input type="text" name="CCCAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for QB Credit Card Charge Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4CCCLIQT" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CCCLIQT")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Field with QB Credit Card Charge Date</span></td>
			<td>
				<input type="text" name="CCCDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Credit Card Charge Payee Reference</span></td>
			<td>
				<input type="text" name="CCCPayee" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCPayee")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Customer/Job</span></td>
			<td>
				<input type="text" name="CCCCustJob" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCCustJob")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> <%= crmtran%> to QB Credit Card Charges</span></td>
			<td>
				<span class="table">
				<select name="CCCSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CCCSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Credit Card Charge</span></td>
			<td>
				<input type="text" name="SFCrCusFCCC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCCC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Credit Card Charge</span></td>
			<td>
				<input type="text" name="SFCrCusFCCCVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCCCVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to set QB Credit Card Charge Type</span></td>
			<td>
				<input type="text" name="SFCrCCCType" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCCCType")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create QB Expence Credit Card Charge</span></td>
			<td>
				<input type="text" name="SFCrCCCTypeExp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCCCTypeExp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create QB Item Credit Card Charge</span></td>
			<td>
				<input type="text" name="SFCrCCCTypeItm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCCCTypeItm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from QB Credit Card Charges to <%= crm%> <%= crmtran%></span></td>
			<td>
				<span class="table">
				<select name="CCCQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CCCQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
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
				<select name="CreateUpdateOppCCC" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppCCC"):"QBCCCCr"; if(sel.equals("")){sel = "QBCCCCr";}%>
					<option <%= sel.equals("QBCCCCr")?"selected=\"selected\"":""%> value="QBCCCCr"><%= fs%> Credit Card Charge created/modified</option>
					<option <%= sel.equals("QBCCCCFOpp")?"selected=\"selected\"":""%> value="QBCCCCFOpp"><%= fs%> Credit Card Charge field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppCCCF" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrOppCCCF")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppCCCFVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrOppCCCFVal")) +"\""):""%>/>
			</td>
		</tr>		
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Credit Card Charge custom mapping 1</span></td>
			<td>
				<input type="text" name="CCCSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Credit Card Charge custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=CCC" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="CCCSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "CCCSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Credit Card Charge Class</span></td>
			<td>
				<input type="text" name="CCCClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Credit Card Charge Memo</span></td>
			<td>
				<input type="text" name="CCCMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Expense Credit Card Charge Billable Status</span></td>
			<td>
				<input type="text" name="CCCBillable" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCBillable")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Credit Card Charge Number</span></td>
			<td>
				<input type="text" name="CCCRflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CCCRflct")) +"\""):""%>/>
			</td>
		</tr>
	</table>
	<%}%>
	<%if(!cm.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to QB Credit Memo"):("Aria Credit Memo to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!crm.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with QB Credit Memo#</span></td>
			<td>
				<input type="text" name="CMNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Credit Memo # is generated by</span></td>
			<td>
				<span class="table">
				<select name="CMNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
					<option <%= sel.equals("MSFQBGen")?"selected=\"selected\"":""%> value="MSFQBGen"><%= crm%>(Merged)</option>
				</select>
				</span>
			</td>
		</tr>
		<%}if((cm.equals("SFQB"))||(cm.equals("SF2QB"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && cm.equals("SF2QB")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial QB to <%= crm%> upload required</td>
			<td>
				<span class="table">
				<select name="CMQB2SFETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMQB2SFETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from " + crm + " " + crmtran + " to " + fs + " Credit Memo"):("Permitted sync operations from Aria Credit Memos to " + fs)%></span></td>
			<td>
				<span class="table">
				<select name="CMSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Use Delete/Insert to Update an Credit Memo</span></td>
			<td>
				<span class="table">
				<select name="DelInsAsUpdCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "DelInsAsUpdCM"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="UseBillAsShipCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseBillAsShipCM"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="UseShipAsBillCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UseShipAsBillCM"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<input type="text" name="NSDepartId" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"NSDepartId")) +"\""):""%>/>
			</td>
		</tr> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= fs%> Default Class Internal Id</span></td>
			<td>
				<input type="text" name="NSClassId" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"NSClassId")) +"\""):""%>/>
			</td>
		</tr><%}%> 
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new QB Credit Memo when</span></td>
			<td>
				<span class="table">
				<select name="CreateNCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNCM"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
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
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new QB Credit Memo</span></td>
			<td>
				<input type="text" name="SFOppStValCM" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValCM")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Credit Memo</span></td>
			<td>
				<input type="text" name="SFCrCusFCM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Credit Memo</span></td>
			<td>
				<input type="text" name="SFCrCusFCMVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCMVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name to create QB Credit Memo</span></td>
			<td>
				<input type="text" name="CORef4CM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object name/Field name(s) to create QB Credit Memo Line Items</span></td>
			<td>
				<input type="text" name="CORef4CMLI" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CMLI")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for QB Credit Memo Line Item Rate</span></td>
			<td>
				<input type="text" name="CORef4CMLIAM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CMLIAM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for QB Credit Memo Line Item Quantity</span></td>
			<td>
				<input type="text" name="CORef4CMLIQT" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CMLIQT")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name(s) for QB Credit Memo Line Item Service Dates</span></td>
			<td>
				<input type="text" name="CORef4CMLID" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CMLID")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field name for QB Credit Memo Line Item Name</span></td>
			<td>
				<input type="text" name="CORef4CMLINm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef4CMLINm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Skip <%= crm%> <%= crmtran%> amounts when creating/updating QB Credit Memo</span></td>
			<td>
				<span class="table">
				<select name="UpdSF2CMAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdSF2CMAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Shipping and Handling QB Item Name(s)</span></td>
			<td>
				<input type="text" name="DummyCMSHNm" size="31" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"DummyCMSHNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Default Shipping and Handling Price</span></td>
			<td>
				<input type="text" name="DummyCMSHPrc" size="31" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"DummyCMSHPrc")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for Dummy Description as Line Item</span></td>
			<td>
				<input type="text" name="DummyCMLIDesc" size="31" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"DummyCMLIDesc")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with reference to Address Object</span></td>
			<td>
				<input type="text" name="AdrObjRefCM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AdrObjRefCM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with Invoice # to apply</span></td>
			<td>
				<input type="text" name="CMApp2InvNum" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMApp2InvNum")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address to QB Credit Memo from</span></td>
			<td>
				<span class="table">
				<select name="CM2BACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CM2BACFOpp"):"OBAddr"; if(sel.equals("")){sel = "OBAddr";}%>
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
			<td><span class="table">Populate Shipping Address to QB Credit Memo from</span></td>
			<td>
				<span class="table">
				<select name="CM2SACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CM2SACFOpp"):"OSAddr"; if(sel.equals("")){sel = "OSAddr";}%>
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
			<td><span class="table">Fill QB Credit Memo Billing Street Address with</span></td>
			<td>
				<span class="table">
				<select name="CMBSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMBSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Fill QB Credit Memo Shipping Street Address with</span></td>
			<td>
				<span class="table">
				<select name="CMSSAddr" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMSSAddr"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Create Pending Credit Memo</td>
			<td>
				<span class="table">
				<select name="CMQBPend" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMQBPend"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to convert QB Credit Memo to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FCM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrConc2FCM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to convert QB Credit Memo to Final</span></td>
			<td>
				<input type="text" name="SFCrConc2FCMVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrConc2FCMVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create QB Line Item Description from <%= crm%> Product Line and Product ones using </span></td>
			<td>
				<span class="table">
				<select name="CMLineDesc" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMLineDesc"):"Ovr"; if(sel.equals("")){sel = "Ovr";}%>
					<option <%= sel.equals("Ovr")?"selected=\"selected\"":""%> value="Ovr">Overwrite</option>
					<option <%= sel.equals("Conc")?"selected=\"selected\"":""%> value="Conc">Concatenate</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with Default Customer Full Name</span></td>
			<td>
				<input type="text" name="CMDefCustFlNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMDefCustFlNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering <%= crm%> Field Name for Credit Memo Operations</span></td>
			<td>
				<input type="text" name="CMPREmNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMPREmNm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Credit Memo to be Printed</span></td>
			<td>
				<input type="text" name="CMToBePrt" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMToBePrt")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Filtering Value(s) for Credit Memo to be Emailed</span></td>
			<td>
				<input type="text" name="CMToBeEml" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMToBeEml")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Name to skip QB Credit Memo Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineCM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFSkipLineCM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Line Field Value to skip QB Credit Memo Line Item</span></td>
			<td>
				<input type="text" name="SFSkipLineCMVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFSkipLineCMVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default Template for Credit Memo</span></td>
			<td>
				<input type="text" name="CMDefTemp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMDefTemp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field to setup Tax value(s) in QB Credit Memo</span></td>
			<td>
				<input type="text" name="Tax4CM" size="31" maxlength="31" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"Tax4CM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Pre-Populate Tax value to QB Credit Memo as</span></td>
			<td>
				<span class="table">
				<select name="Tax4CMValAs" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "Tax4CMValAs"):"None"; if(sel.equals("")){sel = "None";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">Do not populate</option>
					<option <%= sel.equals("TaxItem")?"selected=\"selected\"":""%> value="TaxItem">Additional Line Item</option>
					<option <%= sel.equals("TaxValue")?"selected=\"selected\"":""%> value="TaxValue">Additional Amount for each Line Item</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Sales Tax Item Full Name</span></td>
			<td>
				<input type="text" name="QSBTaxFullN" size="31" maxlength="31" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QSBTaxFullN")) +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
		<%if((cm.equals("SFQB"))||(cm.equals("QB2SF"))){%>
		<%if((crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")) && cm.equals("QB2SF")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Initial <%= crm%> to QB upload required</span></td>
			<td>
				<span class="table">
				<select name="CMSF2QBETL" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMSF2QBETL"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?("Permitted sync operations from "+ fs + " Credit Memo to " + crm + " " + crmtran):("Permitted sync operations from " + fs + " to Aria Credit Memos")%></span></td>
			<td>
				<span class="table">
				<select name="CMQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
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
				<select name="CreateUpdateOppCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateUpdateOppCM"):"QBCMCr"; if(sel.equals("")){sel = "QBCMCr";}%>
					<option <%= sel.equals("QBCMCr")?"selected=\"selected\"":""%> value="QBCMCr">QB Credit Memo created/modified</option>
					<option <%= sel.equals("QBCMCFOpp")?"selected=\"selected\"":""%> value="QBCMCFOpp">QB Credit Memo field has certain value</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Field Name to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppCMF" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrOppCMF")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Field Value to create/update <%= crm%> <%= crmtran%></span></td>
			<td>
				<input type="text" name="QBCrOppCMFVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrOppCMFVal")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">New <%= crm%> <%= crmtran%> Stage</span></td>
			<td>
				<input type="text" name="SFOppStageCM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFOppStageCM")) +"\""):""%>/>
			</td>	
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Update <%= crm%> <%= crmtran%> amounts with calculated QB
			Credit Memo amounts</span></td>
			<td>
				<span class="table">
				<select name="UpdOooCMAmnt" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "UpdOooCMAmnt"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">Never</option>
					<option <%= sel.equals("YesLI")?"selected=\"selected\"":""%> value="YesLI">For Line Items Only</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">For Line Items and Total</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Populate Billing Address Change in QB Credit Memo to</span></td>
			<td>
				<span class="table">
				<select name="CMBACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMBACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Populate Shipping Address Change in QB Credit Memo to</span></td>
			<td>
				<span class="table">
				<select name="CMSACFOpp" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMSACFOpp"):"None"; if(sel.equals("")){sel = "None";}%>
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
			<td><span class="table">Expand <%= crm%> Group Product after QB Group Item expanded</span></td>
			<td>
				<span class="table">
				<select name="CMExpGrpItm" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMExpGrpItm"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="CMNoItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMNoItems"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="CMMulItems" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMMulItems"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (with clones)</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Remaining
			Credit</span></td>
			<td>
				<input type="text" name="CMRemBal" class="table"/ <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMRemBal")) +"\""):""%>>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with Customer/Job Name</span></td>
			<td>
				<input type="text" name="CMCJName" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMCJName")) +"\""):""%>/>
			</td>
		</tr>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%> 
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Include <%= crmtran%> Primary Contact Lookup</span></td>
			<td>
				<span class="table">
				<select name="InclPCOppCM" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "InclPCOppCM"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name with <%= crmtran%> Primary Contact</span></td>
			<td>
				<input type="text" name="CORef2Cont4CM" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CORef2Cont4CM")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Custom field with QB Credit Memo Date</span></td>
			<td>
				<input type="text" name="CMDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Custom field for <%= crm%> <%= crmtran%> Name</span></td>
			<td>
				<input type="text" name="CMQBOppNm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMQBOppNm")) +"\""):""%>/>
			</td>
		</tr><%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to QB Credit Memo custom mapping 1"):("Aria Credit Memo to " + fs + " custom mapping 1")%></span></td>
			<td>
				<input type="text" name="CMSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " " + crmtran + " to QB Credit Memo custom mapping 2"):("Aria Credit Memo to " + fs + " custom mapping 2")%> (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=CM"	+ "&Solution=" + solutionType%>' target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="CMSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "CMSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Owner to QB Sales Rep mapping required</td>
			<td>
				<span class="table">
				<select name="CMOwnRepMap" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMOwnRepMap"):"No"; if(sel.equals("")){sel = "No";}%>
					<option <%= sel.equals("No")?"selected=\"selected\"":""%> value="No">No</option>
					<option <%= sel.equals("Yes")?"selected=\"selected\"":""%> value="Yes">Yes</option>
					<option <%= sel.equals("YesC")?"selected=\"selected\"":""%> value="YesC">Yes (using Custom Field)</option>
				</select>
				</span>
			</td>
		</tr>
		<%if(!crm.equals("Sugar")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Credit Memo Terms</span></td>
			<td>
				<input type="text" name="CMTerm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMTerm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Shipping Method</span></td>
			<td>
				<input type="text" name="CMShipVia" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMShipVia")) +"\""):""%>/>
			</td>
		</tr>
		<%}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Default Discount Item</span></td>
			<td>
				<input type="text" name="CMDefDiscItm" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMDefDiscItm")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="CMClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Credit Memo Number</span></td>
			<td>
				<input type="text" name="CMRflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMRflct")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Duplicate Prevention Field</span></td>
			<td>
				<input type="text" name="CMDplct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMDplct")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field for Last Transaction Date</span></td>
			<td>
				<input type="text" name="CMLastTranDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMLastTranDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Fast Search for a Customer</span></td>
			<td>
				<span class="table">
				<select name="CMFastCust" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMFastCust"):"No"; if(sel.equals("")){sel = "No";}%>
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
				<select name="CMUse4Qty" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CMUse4Qty"):"Std"; if(sel.equals("")){sel = "Std";}%>
					<option <%= sel.equals("Std")?"selected=\"selected\"":""%> value="Std">Standard Field</option>
					<option <%= sel.equals("Cust")?"selected=\"selected\"":""%> value="Cust">Custom Field</option>
				</select>
				</span>
			</td>
		</tr><%}} else if(crm.equals("Aria")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Debit Account (Account Receivable)</span></td>
			<td>
				<input type="text" name="CMAccRcv" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMAccRcv")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Default <%= fs%> Credit Account</span></td>
			<td>
				<input type="text" name="CMAccRcvC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"CMAccRcvC")) +"\""):""%>/>
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
	<%if(!vc.equals("None")){String crmtranb=(crm.equals("SF") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?"Object":crmtran;%>
	<span class="labels"><%= crm + " " + crmtranb + " to QB Vendor Credit"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for QB Vendor Credit</span></td>
			<td>
				<input type="text" name="VCObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Vendor Credit#</span></td>
			<td>
				<input type="text" name="VCNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Vendor Credit # is generated by</span></td>
			<td>
				<span class="table">
				<select name="VCNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VCNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB AP Account Name </span></td>
			<td>
				<input type="text" name="QBBank4VC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBBank4VC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Default Expense Account Name to create QB
			Vendor Credit</span></td>
			<td>
				<input type="text" name="QBGL4VC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBGL4VC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Default Item Name to create QB
			Vendor Credit</span></td>
			<td>
				<input type="text" name="QBItem4VC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBItem4VC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Vendor Credit Amount</span></td>
			<td>
				<input type="text" name="VCAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> Field with QB Vendor Credit
			Date</span></td>
			<td>
				<input type="text" name="VCDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Vendor Credit Vendor
			Reference</span></td>
			<td>
				<input type="text" name="VCPayee" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCPayee")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Object for QB Expense Vendor Credit Lines</span></td>
			<td>
				<input type="text" name="VCCObjectCmp" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCCObjectCmp")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to QB Vendor Credit</span></td>
			<td>
				<span class="table">
				<select name="VCSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VCSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Vendor Credit</span></td>
			<td>
				<input type="text" name="SFCrCusFVC" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFVC")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Vendor Credit</span></td>
			<td>
				<input type="text" name="SFCrCusFVCVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFVCVal")) +"\""):""%>/>
			</td>
		</tr>
		<!--<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from QB Vendor Credit to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="VCQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "VCQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>-->
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to QB Vendor Credit custom mapping 1</span></td>
			<td>
				<input type="text" name="VCSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to QB Vendor Credit custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=VC" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="VCSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "VCSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Vendor Credit Class</span></td>
			<td>
				<input type="text" name="VCClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Vendor Credit Memo</span></td>
			<td>
				<input type="text" name="VCMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Vendor Credit Number</span></td>
			<td>
				<input type="text" name="VCRflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"VCRflct")) +"\""):""%>/>
			</td>
		</tr>
	</table><%}%>	
	<%if(!coa.equals("None")){String crmtranb="Object";%>
	<span class="labels"><%= crm + " Object to QB Account (COA)"%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
		<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object Name for QB Account</span></td>
			<td>
				<input type="text" name="COAObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COAObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Account#</span></td>
			<td>
				<input type="text" name="COANumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COANumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Account Type</span></td>
			<td>
				<input type="text" name="COAType" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COAType")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Account # is generated by</span></td>
			<td>
				<span class="table">
				<select name="COANumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "COANumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Account Name </span></td>
			<td>
				<input type="text" name="COAAccountName" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COAAccountName")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Parent Account Name </span></td>
			<td>
				<input type="text" name="COAParentAccount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COAParentAccount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Bank Number</span></td>
			<td>
				<input type="text" name="COABankNum" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COABankNum")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Account Balance</span></td>
			<td>
				<input type="text" name="COABalance" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COABalance")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to QB Account</span></td>
			<td>
				<span class="table">
				<select name="COASF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "COASF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new QB Account</span></td>
			<td>
				<input type="text" name="SFCrCusFCOA" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCOA")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new QB Account</span></td>
			<td>
				<input type="text" name="SFCrCusFCOAVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFCOAVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from QB Account to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="COAQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "COAQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to QB Account custom mapping 1</span></td>
			<td>
				<input type="text" name="AccountSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccountSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtranb%> to QB Account custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=Account" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="AccountSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"AccountSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "AccountSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> field for Account Descroption</span></td>
			<td>
				<input type="text" name="COAMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COAMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Account Number</span></td>
			<td>
				<input type="text" name="COARflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"COARflct")) +"\""):""%>/>
			</td>
		</tr><%}}%>
	</table>
	<%}%><%if(!je.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " Object to QB Journal Entry"):("Aria External Charges to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!je.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object for QB Journal Entry</span></td>
			<td>
				<input type="text" name="JECObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JECObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Journal Entry Number</span></td>
			<td>
				<input type="text" name="JENumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JENumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Journal Entry # is generated by</span></td>
			<td>
				<span class="table">
				<select name="JENumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JENumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) with QB Journal Entry Credit Account Name</span></td>
			<td>
				<input type="text" name="QBCrd4JE" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCrd4JE")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) with QB Journal Entry Debit Account Name</span></td>
			<td>
				<input type="text" name="QBDbt4JE" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBDbt4JE")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for QB Journal Entry Credit Amount</span></td>
			<td>
				<input type="text" name="JECrdAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JECrdAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for QB Journal Entry Debit Amount</span></td>
			<td>
				<input type="text" name="JEDbtAmount" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JEDbtAmount")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Journal Entry Date</span></td>
			<td>
				<input type="text" name="JEDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JEDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Journal Entry Entity
			Reference</span></td>
			<td>
				<input type="text" name="JEEntity" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JEEntity")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to QB Journal Entry</span></td>
			<td>
				<span class="table">
				<select name="JESF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JESF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Create new QB Journal Entry when</span></td>
			<td>
				<span class="table">
				<select name="CreateNJE" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "CreateNJE"):"AFAccCr"; if(sel.equals("")){sel = "AFAccCr";}%>
					<option <%= sel.equals("AFAccCr")?"selected=\"selected\"":""%> value="AFAccCr">When <%= crm%> Object created</option>
					<option <%= sel.equals("AFAcc")?"selected=\"selected\"":""%> value="AFAcc"><%= crm%> Object is in certain stage</option>
					<option <%= sel.equals("AFAccCFOpp")?"selected=\"selected\"":""%> value="AFAccCFOpp"><%= crm%> Object Custom field has certain value</option>
					<option <%= sel.equals("AFAccIsWOpp")?"selected=\"selected\"":""%> value="AFAccIsWOpp"><%= crm%> Object is Won</option>
					<option <%= sel.equals("Other")?"selected=\"selected\"":""%> value="Other">Other</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> Stage to create new QB Journal Entry</span></td>
			<td>
				<input type="text" name="SFOppStValJE" class="table" value="<%= edit?(ConfigContext.getConfigurationValue(cfrn, cfr, "SFOppStValJE")):"Closed Won"%>"/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Name to create new QB Journal Entry</span></td>
			<td>
				<input type="text" name="SFCrCusFJE" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFJE")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom Field Value to create new QB Journal Entry</span></td>
			<td>
				<input type="text" name="SFCrCusFJEVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFJEVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from QB Journal Entry to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="JEQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "JEQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Journal Entry custom mapping 1</span></td>
			<td>
				<input type="text" name="JESFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JESFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Journal Entry custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=JE" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="JESFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JESFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "JESFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Class</span></td>
			<td>
				<input type="text" name="JEClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JEClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Credit Journal Entry Memo</span></td>
			<td>
				<input type="text" name="JECdtMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JECdtMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Debit Journal Entry Memo</span></td>
			<td>
				<input type="text" name="JEDbtMemo" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JEDbtMemo")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Journal Entry</span></td>
			<td>
				<input type="text" name="JERflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"JERflct")) +"\""):""%>/>
			</td>
		</tr><%}}%>
	</table>
	<%}%><%if(!ttr.equals("None")){%>
	<span class="labels"><%= (crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS"))?(crm + " Object to QB Time Tracking"):("Aria External Charges to " + fs)%></span><br/>
	<table border="1" cellpadding="5" width="100%">
		<tr><td>#</td>
			<td><span class="tablelabels">Property Name</span></td>
			<td><span class="tablelabels">Property Value</span></td>
		</tr>
<%if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS") || (crm.equals("Aria") && (!je.equals("Ar2QBJ")))){
if(crm.equals("CRM") || crm.equals("SF") || crm.equals("Sugar") || crm.equals("Fusion") || crm.equals("MSDCRM")  || crm.equals("PPOL") || crm.equals("OMS")){%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Object for QB Time Tracking</span></td>
			<td>
				<input type="text" name="TTCObject" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTCObject")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Time Tracking Number</span></td>
			<td>
				<input type="text" name="TTNumber" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTNumber")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">QB Time Tracking # is generated by</span></td>
			<td>
				<span class="table">
				<select name="TTNumGen" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "TTNumGen"):"SFGen"; if(sel.equals("")){sel = "SFGen";}%>
					<option <%= sel.equals("SFGen")?"selected=\"selected\"":""%> value="SFGen"><%= crm%></option>
					<option <%= sel.equals("QBGen")?"selected=\"selected\"":""%> value="QBGen"><%= fs%></option>
					<option <%= sel.equals("SFQBGen")?"selected=\"selected\"":""%> value="SFQBGen">Mixed</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Time Tracking Entity Full Name</span></td>
			<td>
				<input type="text" name="QBEnt4TT" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBEnt4TT")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) with QB Time Tracking Customer/Job Full Name</span></td>
			<td>
				<input type="text" name="QBCust4TT" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"QBCust4TT")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for QB Time Tracking Service Full Name</span></td>
			<td>
				<input type="text" name="TTItemService" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTItemService")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field(s) for QB Time Tracking Rate</span></td>
			<td>
				<input type="text" name="TTRate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTRate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field with QB Time Tracking Date</span></td>
			<td>
				<input type="text" name="TTDate" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTDate")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Time Tracking Payroll Item Full Name</span></td>
			<td>
				<input type="text" name="TTPayrollItem" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTPayrollItem")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name for QB Time Tracking Payroll Class</span></td>
			<td>
				<input type="text" name="TTClass" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTClass")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from <%= crm%> Object to QB Time Tracking</span></td>
			<td>
				<span class="table">
				<select name="TTSF2QBOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "TTSF2QBOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Name to create new QB Time Tracking</span></td>
			<td>
				<input type="text" name="SFCrCusFTT" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFTT")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Field Value to create new QB Time Tracking</span></td>
			<td>
				<input type="text" name="SFCrCusFTTVal" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"SFCrCusFTTVal")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table">Permitted sync operations from QB Time Tracking to <%= crm%> Object</span></td>
			<td>
				<span class="table">
				<select name="TTQB2SFOps" size="1"><%sel = edit?ConfigContext.getConfigurationValue(cfrn, cfr, "TTQB2SFOps"):"CUOPPS"; if(sel.equals("")){sel = "CUOPPS";}%>
					<option <%= sel.equals("None")?"selected=\"selected\"":""%> value="None">None</option>
					<option <%= sel.equals("CUOPPS")?"selected=\"selected\"":""%> value="CUOPPS">Create and Update</option>
					<option <%= sel.equals("COPPS")?"selected=\"selected\"":""%> value="COPPS">Create Only</option>
					<option <%= sel.equals("UOPPS")?"selected=\"selected\"":""%> value="UOPPS">Update Only</option>
				</select>
				</span>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Time Tracking custom mapping 1</span></td>
			<td>
				<input type="text" name="TTSFQBCMap" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTSFQBCMap")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> <%= crmtran%> to QB Time Tracking custom mapping 2 (<a href='<%= "MoreCustomMappings.jsp" + "?CurrentProfile=" + currentProfileName  + ((oldProfileName==null)?"":("&OldProfile=" + oldProfileName)) + "&ObjectType=TT" + "&Solution=" + solutionType%>'	target="_blank">more mappings</a>)</span></td>
			<td>
				<input type="text" name="TTSFQBCMap1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTSFQBCMap1")) +"\""):""%>/>
			</td>
		</tr>
		<%for (int imp=2; imp<10; imp++){
		String ecmn = "TTSFQBCMap" + imp;
		String ecmv = ConfigContext.getConfigurationValue(cfr, ecmn);
		if(ecmv.trim().length()>0){%>
		<input type="hidden" name=<%= ecmn%> value="<%= ecmv%>"/>
		<%}}%>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Time Tracking Duration</span></td>
			<td>
				<input type="text" name="TTDuration" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTDuration")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field(s) for Time Tracking Notes</span></td>
			<td>
				<input type="text" name="TTNotes" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTNotes")) +"\""):""%>/>
			</td>
		</tr>
		<tr><td><%= lnmbr++%></td>
			<td><span class="table"><%= crm%> Custom field for Created Time Tracking</span></td>
			<td>
				<input type="text" name="TTRflct" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"TTRflct")) +"\""):""%>/>
			</td>
		</tr><%}}%>
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
				<input type="text" name="FieldGroup1" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"FieldGroup1")) +"\""):""%>/>
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
				<input type="text" name="FieldGroup2" class="table" <%= edit?("value=\"" + HtmlEncoder.encode(ConfigContext.getConfigurationValue(cfrn, cfr,"FieldGroup2")) +"\""):""%>/>
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




		