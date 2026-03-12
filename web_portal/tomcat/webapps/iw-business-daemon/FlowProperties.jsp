	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.interweave.web.HtmlEncoder" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Transaction Flow Properties Page</title>
  <style>
<!--
.labels
   {
   color: black; font-family: Verdana; font-size: 10pt; font-style: normal; font-weight: bold;
   }
.tablelabels
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: bold;
   }
.tablesmall
   {
   color: black; font-family: Verdana; font-size: 7pt; font-style: normal; font-weight: bold;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: normal;
   }
-->
</style>
</head>
<body class="labels">
<%
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
String whoAmI = request.getParameter("WhoAmI");
if(whoAmI==null){
whoAmI="";
}
String env2Con = request.getParameter("Env2Con");
if(env2Con==null){
env2Con="COM";
}
String flowId = request.getParameter("CurrentFlowId");
if(flowId != null) flowId = flowId.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
String profileId = request.getParameter("CurrentProfile");
String tf = request.getParameter("IsFlow");
String adm = request.getParameter("IsAdmin");
String fromMnt = request.getParameter("FromMonitor");
boolean isAdmin = (adm!=null)&& (adm.equalsIgnoreCase("true"));
boolean isFlow = "1".equals(tf);
boolean uploads = false;
HostedTransactionBase tc = isFlow?ConfigContext.getTransactionContxtById(flowId):ConfigContext.getQueryContxtById(flowId);
if(tc == null){
%>
<table border="0" cellpadding="5" class="table" width="100%">
	<tr>
		<td class="tablelabels"><%= (isFlow?"Transaction Flow: ":"Query: ") + HtmlEncoder.encode(flowId)%></td>
	</tr>
	<tr>
		<td class="tablesmall" style="color:red">Flow context not loaded. Initialize the engine via BDConfigurator first, or use the <a href="/iw-portal/#/integrations">modern portal</a>.</td>
	</tr>
</table>
<%} else {%>
<table border="0" cellpadding="5" class="table" width="100%">
	<tr>
		<td class="tablelabels"><%= (isFlow?"Transaction Flow: ":"Query: ") + HtmlEncoder.encode(flowId)%></td>
	</tr>
	<tr>
		<td class="tablesmall"><%= HtmlEncoder.encode(tc.getDescription())%></td>
	</tr>
</table>
<p>
	<form action="FlowProperiesServlet" method="post" target="_top">
<input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
<input type="hidden" name="PortalBrand" value="<%= HtmlEncoder.encode(brand)%>"/>
<input type="hidden" name="PortalSolutions" value="<%= HtmlEncoder.encode(solutions)%>"/>
		<input type="hidden" name="FlowId" value="<%= HtmlEncoder.encode(flowId) %>"/>
		<input type="hidden" name="IsAdmin" value="<%= HtmlEncoder.encode(adm) %>"/>
		<%if(profileId!=null){%>
		<input type="hidden" name="ProfileId" value="<%= HtmlEncoder.encode(profileId) %>"/>
		<%}%>
		<input type="hidden" name="IsFlow" value="<%= HtmlEncoder.encode(tf) %>"/>
		<input type="hidden" name="WhoAmI" value="<%= HtmlEncoder.encode(whoAmI) %>"/>
		<input type="hidden" name="Env2Con" value="<%= HtmlEncoder.encode(env2Con)%>"/>
	<table border="1" cellpadding="0" width="100%" cellspacing="0">
		<tr class="tablelabels">
			<td>Property Name</td>
			<td>Property Value</td>
		</tr>
		<%
Properties params = new Properties();
boolean running = ConfigContext.getVariableParameters(flowId, profileId, tf, isAdmin, params);
if(fromMnt!=null){
running = true;
}
if(params!=null){
Iterator prs = params.keySet().iterator();
while (prs.hasNext()){
String nm = (String)prs.next();
String cp = params.getProperty(nm);
boolean ps = nm.startsWith("__%p%__");
boolean up = nm.startsWith("__%u%__");
if(ps||up){
nm = nm.substring(7);
}
if(!up){
%>		
		<tr>
			<td><span class="table"><%= HtmlEncoder.encode(nm)%></span></td>
			<td>
				<span class="table">
				<input type=<%= ps?"password":"text"%> name='<%= "PV:" + HtmlEncoder.encode(nm)%>' value="<%= HtmlEncoder.encode(cp)%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
		</tr>
		<%}else{uploads = true;}}}
		if(isAdmin){%>
		<tr>
			<td><span class="table">Primary TS URL PA</span></td>
			<td>
				<span class="table">
				<input type="text" name='U1:' value="<%= HtmlEncoder.encode(tc.getPrimaryTransformationServerURL())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Secondary TS URL PA</span></td>
			<td>
				<span class="table">
				<input type="text" name='U2:' value="<%= HtmlEncoder.encode(tc.getSecondaryTransformationServerURL())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Primary TS URL PB</span></td>
			<td>
				<span class="table">
				<input type="text" name='U1T:' value="<%= HtmlEncoder.encode(tc.getPrimaryTransformationServerURLT())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Secondary TS URL PB</span></td>
			<td>
				<span class="table">
				<input type="text" name='U2T:' value="<%= HtmlEncoder.encode(tc.getSecondaryTransformationServerURLT())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Primary TS URL PC</span></td>
			<td>
				<span class="table">
				<input type="text" name='U11:' value="<%= HtmlEncoder.encode(tc.getPrimaryTransformationServerURL1())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Secondary TS URL PC</span></td>
			<td>
				<span class="table">
				<input type="text" name='U21:' value="<%= HtmlEncoder.encode(tc.getSecondaryTransformationServerURL1())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Primary TS URL PD</span></td>
			<td>
				<span class="table">
				<input type="text" name='U1T1:' value="<%= HtmlEncoder.encode(tc.getPrimaryTransformationServerURLT1())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Secondary TS URL PD</span></td>
			<td>
				<span class="table">
				<input type="text" name='U2T1:' value="<%= HtmlEncoder.encode(tc.getSecondaryTransformationServerURLT1())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Primary TS URL D</span></td>
			<td>
				<span class="table">
				<input type="text" name='U1D:' value="<%= HtmlEncoder.encode(tc.getPrimaryTransformationServerURLD())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><span class="table">Secondary TS URL D</span></td>
			<td>
				<span class="table">
				<input type="text" name='U2D:' value="<%= HtmlEncoder.encode(tc.getSecondaryTransformationServerURLD())%>" <%= running?"disabled":""%> size="100%"/>
				</span>
			</td>
			<td></td>
		</tr>
		<%}%>
	</table>
		<p>
			<input type="submit" name="submit" value=<%= running?"OK":"Submit"%> class="labels"/>
		</p>
	</form><%if(uploads && (!running)){%>
	<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
		<input type="hidden" name="_csrf" value="<%= session.getAttribute("_csrf") %>"/>
		<input type="hidden" name="FlowId" value="<%= HtmlEncoder.encode(flowId) %>"/>
		<%if(profileId!=null){%>
		<input type="hidden" name="ProfileId" value="<%= HtmlEncoder.encode(profileId) %>"/>
		<%}%>
		<input type="hidden" name="IsFlow" value="<%= HtmlEncoder.encode(tf) %>"/>
		<input type="hidden" name="WhoAmI" value="<%= HtmlEncoder.encode(whoAmI) %>"/>
		<input type="hidden" name="Env2Con" value="<%= HtmlEncoder.encode(env2Con)%>"/>
	<table border="1" cellpadding="0" width="100%" cellspacing="0">
		<%
if(params!=null){
Iterator prs = params.keySet().iterator();
while (prs.hasNext()){
String nm = (String)prs.next();
String cp = params.getProperty(nm);
if(nm.startsWith("__%u%__")){
nm = nm.substring(7);
%>		
		<tr>
			<td><span class="table"><%= HtmlEncoder.encode(nm)%></span></td>
			<td>
				<span class="table">
					<input type="file" name='<%= "PV:" + HtmlEncoder.encode(nm)%>' value="<%= HtmlEncoder.encode(cp)%>" size="100%"/>
				</span>
			</td>
		</tr>
		<%}}}%>
	</table>
		<p>
			<input type="submit" name="submit" value="Upload" class="labels"/>
		</p>
	</form><%}%>
</p>
<%}%>
</body>
</html>