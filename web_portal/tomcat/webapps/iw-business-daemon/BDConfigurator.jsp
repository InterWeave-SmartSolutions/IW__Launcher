<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<% String refreshValue = request.getParameter("RefreshValue");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
String brand = request.getParameter("PortalBrand");
if(brand==null){
brand="";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
solutions="";
}
String env2Con = request.getParameter("Env2Con");
if(env2Con==null){
env2Con="COM";
}
String brandSol = "";
if (brand != null && brand.length() > 0) {
	brandSol += "&PortalBrand=" + brand;
}
if (solutions != null && solutions.length() > 0) {
	brandSol += "&PortalSolutions=" + solutions;
}
brandSol += "&Env2Con=" + env2Con;
String brandSol1 = "";
if (brand != null && brand.length() > 0) {
	brandSol1 += "?PortalBrand=" + brand;
} 
if (solutions != null && solutions.length() > 0) {
	brandSol1 += ((brand != null && brand.length() > 0)?"&":"?") + "PortalSolutions=" + solutions;
}
brandSol1 += (((brand != null && brand.length() > 0) || (solutions != null && solutions
				.length() > 0)) ? "&" : "?")
				+ "Env2Con=" + env2Con;
String currentProfileName = request.getParameter("CurrentProfile");
String currentUser = "";
if(currentProfileName!=null){
int clps = currentProfileName.indexOf(":");
currentUser = currentProfileName.substring(clps + 1);
long rr = ConfigContext.getProfileDescriptors().get(currentProfileName).getProfileRefresh();
if(rr > 0L){ 
String rv = "10;url=BDConfigurator.jsp?RefreshValue=" + rr + "&CurrentProfile=" + currentProfileName + brandSol;%>
<meta http-equiv="Refresh" content="<%= rv%>"><%}}%>
<title>IM Configurator Page</title>
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
.table
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: normal; font-weight: normal;
   }
.tableitalic
   {
   color: black; font-family: Verdana; font-size: 8pt; font-style: italic; font-weight: normal;
   }
-->
</style>
</head>
<body>
<% 
long rr = (currentProfileName==null)?0L:ConfigContext.getProfileDescriptors().get(currentProfileName).getProfileRefresh();
if(ConfigContext.isAdminLoggedIn() || (refreshValue!=null && rr>0L && rr==Long.valueOf(refreshValue))){
ConfigContext.setAdminLoggedIn(false);
if(currentProfileName==null || currentProfileName.trim().length()==0){
%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="Customer profile is lost. Please login again."/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="ErrorMessageTarget" value="_top"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
</jsp:forward>
<%}%>
<table border="0" cellpadding="0" width="100%" cellspacing="0">
	<tr>
		<td><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
</table>
<table border="0" cellpadding="3" width="100%" cellspacing="0">
	<tr>
		<td align="left"><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">InterWeave
		Integration Manager</span><br/>
		</td>
					<td align="right" class="labels">User: <%= currentUser%></td>
					<td align="right"><a href='<%= "LogoutServlet?ProfileId=" + currentProfileName + brandSol%>' target="_top" class="labels">Logout</a></td>
					<td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
				
	</tr>

</table>
<form action="ProductDemoServlet" method="POST" target="_top">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="Env2Con" value="<%= env2Con%>"/>

<p><img src="images/dots.gif" align="middle" width="100%"/></p>
	<table border="0" cellpadding="0" class="labels" cellspacing="0" width="100%">
		<tr>
			<td colspan="2">
				<p><b><font size="4"><span style="color: black; font-family: Verdana; font-size: 14pt; font-style: normal; font-weight: bold">Configure
					Transactions to Start</span></font><font size="4"></font></b></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><input type="submit" name="submit" value="Submit" class="labels"/></p>
			</td>
			<td align="right"  class="labels">
				<%if(env2Con.equals("DDC")){%>
				<input type="submit" name="command" value="Reset Server" class="labels"/><%}%>
				<input type="checkbox" name="Refresh" value="Refresh" <%= (ConfigContext.getProfileDescriptors().get(currentProfileName).getProfileRefresh()>0L)?"checked":""%>/>Auto-Refresh
			</td>
		</tr>
	</table>
	<table border="1" cellpadding="0" width="100%" cellspacing="0">
		<tr class="tablelabels">
			<td>Transaction Flow Id</td>
			<td>Start/Stop</td>
			<td>State</td>
			<td>Scheduled</td>
			<td>Single Run</td>
			<td>Interval</td>
			<td>Shift</td>
			<td>Query Starts</td>
			<td>Counter</td>
			<td>Runs</td>
		</tr>
		<tr class="tablelabels"><td colspan="11">Scheduled Transaction Flows</td></tr>
		<%
		if(ConfigContext.isHosted()){
		%>
		<input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
		<%}
		for(int i = 0; i< ConfigContext.getTransactionList().size(); i++){
		TransactionContext tc = (TransactionContext)(ConfigContext.getTransactionList().get(i)); 
		String tid = tc.getTransactionId();
		boolean stf = tc.isStateful();
		if(stf){
		Hashtable att = tc.getTransactionThreads();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
		if(!profileName.equals(currentProfileName)){
			continue;
			}
		TransactionThread tt = (TransactionThread)(att.get(profileName));
		if(tt.getLogLevel()==-1111){
			continue;
			}
		long ctm = System.currentTimeMillis();
		tt.setSubmitFlag(ctm);
		String state = ConfigContext.getProfileTransactionState(tc, profileName);
		String command = ConfigContext.getProfileTransactionCommand(tc, profileName);
		long trInt = tt.getTransactionInterval();
		long trShift = tt.getTransactionShiftFromHartBeat();
		int trLogLevel = tt.getLogLevel();
		String trShiftS = String.valueOf(trShift);
		String trIntS = String.valueOf(trInt);
		if(trShift<0L){
			trShiftS = "T";
			long hr = trInt / (60L * 60L);
			long mn = (trInt - hr * 60L * 60L) / 60L;
			long sc = trInt - hr * 60L * 60L - mn * 60L;
			trIntS = String.format("%1$02d", hr) + ":" + 
			         String.format("%1$02d", mn) + ":" + 
			         String.format("%1$02d", sc);
		}
		String trQST = tt.getTransactionStartTime().toString();
		String sfr = tt.getSuccesses() + "/" + tt.getFailures();
		int trCnt = tt.getTransactionCounter();
		boolean running = tt.isActive();
		boolean executing = tt.isExecuting();
		boolean runningEx = running || executing;
		boolean restoreRunning = tt.isRestoredRunning();
		tt.setRestoredRunning(false);
		%>
		<tr bgcolor=<%=running?(executing?"deeppink":"aqua"):(executing?"yellow":"white")%>>
			<td><span class="tableitalic"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + tid + "&CurrentProfile=" + profileName + "&IsAdmin=false&IsFlow=1" + brandSol%>' target="data"><%= tid%></a></span></td>
			<td>
				<span class="table">
				<input type="checkbox" name='<%= "TS:" + i + "=" + profileName%>' <%= restoreRunning?"checked":""%> value="<%= ctm%>"/><%= command%>
				</span>
			</td>
			<td><span class="table"><%= state%></span></td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="1" <%= (trInt>0L || trShift<0L)?"checked":""%> <%= (runningEx || (trInt==0L && trShift>=0L))?"disabled":""%> />
				</span></td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="0" <%= (trInt<=0L && trShift>=0L)?"checked":""%> <%= (runningEx || (trInt==0L && trShift>=0L))?"disabled":""%> />
				</span></td>
			<td>
				<input type="text" name='<%= "IN:" + i + "=" + profileName%>' value='<%= trIntS%>' maxlength="11" class="table" <%= (runningEx || (trInt<=0L && trShift>=0L))?"disabled":""%> size="11"/>
			</td>
			<td>
				<input type="text" name='<%= "SH:" + i + "=" + profileName%>' value='<%= trShiftS%>' maxlength="11" class="table" <%= runningEx?"disabled":""%> size="11"/>
			</td>
			<td>
				<input type="text" name='<%= "QS:" + i + "=" + profileName%>' maxlength="22" value='<%= trQST%>' class="table" <%= runningEx?"disabled":""%>/>
			</td>
			<td><span class="table">
				<input type="text" name='<%= "TC:" + i + "=" + profileName%>' maxlength="11" value='<%= trCnt%>' class="table" <%= runningEx?"disabled":""%> size="11"/>
				</span></td>
			<td><span class="table"><a href='<%= "Logging.jsp?TCURL=" + tc.getPrimaryTSURL4Log(tt.getMode(), tt.getModec(), tt.getPrimaryDedicatedURL(), tt.getPrimaryDedicatedURLc()) + "&__LOG_QUERY_ID__=" + tid + "&CurrentProfile=" + profileName + brandSol%>' target="_blank"><%= sfr%></a></span></td>
		</tr>
		<% }}}%>
		<tr class="tablelabels"><td colspan="11">Utility Transaction Flows</td></tr>
		<%
		if(ConfigContext.isHosted()){
		%>
		<input type="hidden" name="CurrentProfile" value="<%= currentProfileName %>"/>
		<%}
		for(int i = 0; i< ConfigContext.getTransactionList().size(); i++){
		TransactionContext tc = (TransactionContext)(ConfigContext.getTransactionList().get(i)); 
		String tid = tc.getTransactionId();
		boolean stf = tc.isStateful();
		if(!stf){
		Hashtable att = tc.getTransactionThreads();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
		if(!profileName.equals(currentProfileName)){
			continue;
			}
		TransactionThread tt = (TransactionThread)(att.get(profileName));
		long ctm = System.currentTimeMillis();
		tt.setSubmitFlag(ctm);
		String state = ConfigContext.getProfileTransactionState(tc, profileName);
		String command = ConfigContext.getProfileTransactionCommand(tc, profileName);
		long trInt = tt.getTransactionInterval();
		long trShift = tt.getTransactionShiftFromHartBeat();
		int trLogLevel = tt.getLogLevel();
		String trShiftS = String.valueOf(trShift);
		String trIntS = String.valueOf(trInt);
		if(trShift<0L){
			trShiftS = "T";
			long hr = trInt / (60L * 60L);
			long mn = (trInt - hr * 60L * 60L) / 60L;
			long sc = trInt - hr * 60L * 60L - mn * 60L;
			trIntS = String.format("%1$02d", hr) + ":" + 
			         String.format("%1$02d", mn) + ":" + 
			         String.format("%1$02d", sc);
		}
		String trQST = tt.getTransactionStartTime().toString();
		String sfr = tt.getSuccesses() + "/" + tt.getFailures();
		int trCnt = tt.getTransactionCounter();
		boolean running = tt.isActive();
		boolean executing = tt.isExecuting();
		boolean runningEx = running || executing;
		boolean restoreRunning = tt.isRestoredRunning();
		tt.setRestoredRunning(false);
		%>
		<tr bgcolor=<%=running?(executing?"deeppink":"aqua"):(executing?"yellow":"white")%>>
			<td><span class="table"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + tid + "&CurrentProfile=" + profileName + "&IsAdmin=false&IsFlow=1" + brandSol%>' target="data"><%= tid%></a></span></td>
			<td>
				<span class="table">
				<input type="checkbox" name='<%= "TS:" + i + "=" + profileName%>' <%= restoreRunning?"checked":""%> value="<%= ctm%>"/><%= command%>
				</span>
			</td>
			<td><span class="table"><%= state%></span></td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="1" <%= (trInt>0L || trShift<0L)?"checked":""%> <%= (runningEx || (trInt==0L && trShift>=0L))?"disabled":""%> />
				</span></td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="0" <%= (trInt<=0L && trShift>=0L)?"checked":""%> <%= (runningEx || (trInt==0L && trShift>=0L))?"disabled":""%> />
				</span></td>
			<td>
				<input type="text" name='<%= "IN:" + i + "=" + profileName%>' value='<%= trIntS%>' maxlength="11" class="table" <%= (runningEx || (trInt<=0L && trShift>=0L))?"disabled":""%> size="11"/>
			</td>
			<td>
				<input type="text" name='<%= "SH:" + i + "=" + profileName%>' value='<%= trShiftS%>' maxlength="11" class="table" <%= runningEx?"disabled":""%> size="11"/>
			</td>
			<td>
				<input type="text" name='<%= "QS:" + i + "=" + profileName%>' maxlength="22" value='<%= trQST%>' class="table" <%= runningEx?"disabled":""%>/>
			</td>
			<td><span class="table">
				<input type="text" name='<%= "TC:" + i + "=" + profileName%>' maxlength="11" value='<%= trCnt%>' class="table" <%= runningEx?"disabled":""%> size="11"/>
				</span></td>
			<td><span class="table"><a href='<%= "Logging.jsp?TCURL=" + tc.getPrimaryTSURL4Log(tt.getMode(), tt.getModec(), tt.getPrimaryDedicatedURL(), tt.getPrimaryDedicatedURLc()) + "&__LOG_QUERY_ID__=" + tid + "&CurrentProfile=" + profileName + brandSol%>' target="_blank"><%= sfr%></a></span></td>
		</tr>
		<%}}}%>
		
	</table>
	
<p>
	<input type="submit" name="submit" value="Submit" class="labels"/>
	</p>
</form>
<table border="0" cellpadding="0" width="100%" cellspacing="0">
	<tr>
		<td><img src="images/dots.gif" align="left" width="100%"/></td>
	</tr>
	<tr>
		<td>
			<table border="1" cellpadding="0" width="100%" cellspacing="0">
				<tr class="tablelabels">
					<td>Query Id</td>
					<td>Execute Query</td>
				</tr>
				<%for(int i=0; i<ConfigContext.getQueryList().size(); i++){
			QueryContext qc = (QueryContext)(ConfigContext.getQueryList().get(i)); 
			String qid = qc.getTransactionId();
			Hashtable att = qc.getQueryInstances();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
		if(!profileName.equals(currentProfileName)){
			continue;
			}
			TransactionBase tb = (TransactionBase)(att.get(profileName));%>
				<tr>
					<td><span class="table"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + qid + "&CurrentProfile=" + profileName + "&IsAdmin=false&IsFlow=0" + brandSol%>' target="data"><%= qid%></a></span></td>
					<td>
						<span class="table"><a href="<%= qc.getHTTPGetQuery(profileName) + brandSol1%>" target="data">GO</a></span>
					</td>
				</tr>
		<% }}%>
			</table>			
		</td>
	</tr>
</table>
<br/>
<br/>
<%}else{%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="Your session has been lost. Please refresh your browser. If error persists please click Reset button below."/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="ErrorMessageTarget" value="_top"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
	<jsp:param name="ButtonLable" value="Reset"/>
</jsp:forward>
<%}%>
</body>
</html>