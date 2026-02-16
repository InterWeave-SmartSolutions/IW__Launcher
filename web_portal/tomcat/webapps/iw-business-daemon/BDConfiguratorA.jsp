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
if(ConfigContext.getRefresh() > 0L){ 
String rv = "10;url=BDConfiguratorA.jsp?RefreshValue=" + ConfigContext.getRefresh()+ brandSol;%>
<meta http-equiv="Refresh" content="<%= rv%>"><%}%>
<title>IM Admin Configurator Page</title>
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
if(ConfigContext.isAdminLoggedIn() || (refreshValue!=null && ConfigContext.getRefresh()>0L && ConfigContext.getRefresh()==Long.valueOf(refreshValue))){
ConfigContext.setAdminLoggedIn(false);
%>
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
					<td align="right"><a href='<%= "BDMinitor.jsp" + brandSol1%>'	class="labels" target="_blank">IW Monitor</a></td>
					<td align="right"><a href='<%= "LogoutServlet" + brandSol1%>' target="_top" class="labels">Logout</a></td>
					<td align="right"><a href='http://interweave.biz' class="labels" target="_blank">InterWeave</a></td>
				
	</tr>
<%if(ConfigContext.isHosted() && (!ConfigContext.isSupportLoggedIn())){ %>
	<tr bgcolor="#eeeeee" align="right">
		<td><form action="ProductDemoServlet" method="post" target="_top">
				<input type="submit" name="command" value="Flow Assignment" class="labels"/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
			</form>
		</td>
		<td>
			<form action="ProductDemoServlet" method="post" target="_top">
				<input type="submit" name="command" value="List Users" class="labels" disabled="disabled"/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
			</form>
		</td>
		<td>
			<form action="ProductDemoServlet" method="post" target="_top">
				<input type="submit" name="command" value="Stop and Save" class="labels"/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
			</form>
		</td>
		<td>
			<form action="ProductDemoServlet" method="post" target="_top">
				<input type="submit" name="command" value="Save" class="labels"/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
			</form>
		</td>
		<td>
			<form action="ProductDemoServlet" method="post" target="_top">
				<input type="submit" name="command" value="Restore" class="labels"/>
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
			</form>
		</td>
	</tr>
<%}%>
</table>
<form action="ProductDemoServlet" method="post" target="_top">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<input type="hidden" name="WhoAmI" value="A"/>
<%
if(ConfigContext.isHosted() && ((ConfigContext.getLoggedUserType()=='A') || (ConfigContext.getLoggedUserType()=='S')) && (!ConfigContext.isSupportLoggedIn())){ %>
	
	<table border="0" cellpadding="0" class="labels" cellspacing="0" width="100%">
		<tr>
			<td>DB Driver</td>
			<td>
				<input type="text" name="LocalDBDriver" value='<%= ConfigContext.getHostedDriver()%>' size="60" maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td>DB DSN</td>
			<td>
				<input type="text" name="LocalDBDSN" value='<%= ConfigContext.getHostedURL()%>' size="60" maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td>DB User</td>
			<td>
				<input type="text" name="LocalDBUser" value='<%= ConfigContext.getHostedUser()%>' size="20" maxlength="80"/>
			</td>
		</tr>
		<tr>
			<td>DB Password</td>
			<td>
				<input type="password" name="LocalDBPassword" value='<%= ConfigContext.getHostedPassword()%>' size="20" maxlength="20"/>
			</td>
		</tr>
	</table>
<%}%>
<p><img src="images/dots.gif" align="middle" width="100%"/></p>
	<table border="0" cellpadding="0" class="labels" cellspacing="0" width="100%">
		<tr>
			<td colspan="5">
				<p><b><font size="4"><span style="color: black; font-family: Verdana; font-size: 14pt; font-style: normal; font-weight: bold">Configure
					Transactions to Start</span></font><font size="4"></font></b></p>
			</td>
		</tr>	
		<tr>
			<td>
				<p><input type="submit" name="submit" value="Submit" class="labels"/></p>
			</td>
			<td align="right"  class="labels">
				<input type="checkbox" name="Refresh" value="Refresh" <%= (ConfigContext.getRefresh()>0L)?"checked":""%>/>Auto-Refresh
			</td>
			<td align="right"  class="labels">
				<select name="AfterEnd" size="1"><%String sel = ConfigContext.isToStartAfterEnding()?"START":"STOP";%>
					<option <%= sel.equals("START")?"selected=\"selected\"":""%> value="Start">Start</option>
					<option <%= sel.equals("STOP")?"selected=\"selected\"":""%>value="Stop">Stop</option>
				</select> after ending
			</td>
			<td align="right"  class="labels">
				<input type="checkbox" name="Lock All" value="Lock All" <%= ConfigContext.isLockAll()?"checked":""%>/>Lock All
			</td><%if(ConfigContext.isHosted() && (ConfigContext.getLoggedUserType()=='S') && (!ConfigContext.isSupportLoggedIn())){ %>
			<td align="right"  class="labels">
				<input type="checkbox" name="SU Lock All" value="SU Lock All" <%= ConfigContext.isSuLockAll()?"checked":""%>/>SU Lock All
			</td><%}%>
			<td align="right">
				<a href='<%= "ViewLog.jsp?IsAdmin=" + "true"%>'	class="labels" target="_blank">View Log</a>
			</td>
		</tr>
	</table>
	<table border="1" cellpadding="0" width="100%" cellspacing="0">
		<tr class="tablelabels">
			<td>Transaction Flow Id</td>
			<td>Start Transaction Name</td>
			<td>Start/Stop</td>
			<td>State</td>
			<td>Scheduled</td>
			<td>Single Run</td>
			<td>Interval</td>
			<td>Shift</td>
			<td>Query Starts</td>
			<td>Counter</td>
			<td>Runs</td>
			<%if(ConfigContext.isHosted()){%>
			<td>Log Level</td>
			<td>Profile</td>
			<td>Kill</td>
			<%}%>
		</tr>
		<tr class="tablelabels"><td colspan=<%= (ConfigContext.isHosted()?"14":"11")%>>Scheduled Transaction Flows</td></tr>
		<%
		Vector<TransactionContext> vTC = (ConfigContext.getLoggedUserType()=='V')?ConfigContext.sortByProfile():ConfigContext.getTransactionList();
		for(int i = 0; i< vTC.size(); i++){
		TransactionContext tc = (TransactionContext)(vTC.get(i)); 
		String tid = tc.getTransactionId();
		boolean stf = tc.isStateful();
		if(stf){
		Hashtable att = tc.getTransactionThreads();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
		TransactionThread tt = (TransactionThread)(att.get(profileName));
		if((tt.getLogLevel()==-1111) && (ConfigContext.getLoggedUserType()=='V')){
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
		<tr bgcolor=<%=running?(executing?"deeppink":"aqua"):(executing?"yellow":((trLogLevel==-1111)?"lightgrey":"white"))%>>
			<td><span class="tableitalic"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + tid + "&CurrentProfile=" + profileName + "&IsAdmin=true" + "&IsFlow=1&WhoAmI=A" + brandSol%>' target="data"><%= tid%></a></span></td>
			<td><span class="table"><%= tc.getParameters().get("tranname").getParameterValue()%></span></td>
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
			<%if(ConfigContext.isHosted()){%>
			<td>
				<input type="text" name='<%= "LL:" + i + "=" + profileName%>' value='<%= trLogLevel%>' maxlength="5" class="table" size="5"/>
			</td>
			<td><span class="table"><%= profileName%></span></td>
			<td>
				<span class="table">
				<input type="checkbox" name='<%= "KL:" + i + "=" + profileName%>'/>
				</span>
			</td>
			<% }%>
		</tr>
		<% }}}
		if(ConfigContext.getLoggedUserType()!='V'){%>
		<tr class="tablelabels"><td colspan=<%= (ConfigContext.isHosted()?"14":"11")%>>Utility Transaction Flows</td></tr>
		<%
		for(int i = 0; i< ConfigContext.getTransactionList().size(); i++){
		TransactionContext tc = (TransactionContext)(ConfigContext.getTransactionList().get(i)); 
		String tid = tc.getTransactionId();
		boolean stf = tc.isStateful();
		if(!stf){
		Hashtable att = tc.getTransactionThreads();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
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
			<td><span class="table"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + tid + "&CurrentProfile=" + profileName + "&IsAdmin=true" + "&IsFlow=1&WhoAmI=A" + brandSol%>' target="data"><%= tid%></a></span></td>
			<td><span class="table"><%= tc.getParameters().get("tranname").getParameterValue()%></span></td>
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
			<%if(ConfigContext.isHosted()){%>
			<td>
				<input type="text" name='<%= "LL:" + i + "=" + profileName%>' value='<%= trLogLevel%>' maxlength="5" class="table" size="5"/>
			</td>
			<td><span class="table"><%= profileName%></span></td>
			<td>
				<span class="table">
				<input type="checkbox" name='<%= "KL:" + i + "=" + profileName%>'/>
				</span>
			</td>
			<% }%>
		</tr>
		<%}}}}%>		
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
					<td>Start Transaction Name</td>
					<td>Execute Query</td>
					<%if(ConfigContext.isHosted()){%>
					<td>Profile</td>
					<td>Kill</td>
					<% }%>
				</tr>
				<%for(int i=0; i<ConfigContext.getQueryList().size(); i++){
			QueryContext qc = (QueryContext)(ConfigContext.getQueryList().get(i)); 
			String qid = qc.getTransactionId();
			Hashtable att = qc.getQueryInstances();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
			TransactionBase tb = (TransactionBase)(att.get(profileName));%>
				<tr>
					<td><span class="table"><a href='<%= "FlowProperties.jsp?CurrentFlowId=" + qid + "&CurrentProfile=" + profileName + "&IsAdmin=true" + "&IsFlow=0&WhoAmI=A" + brandSol%>' target="data"><%= qid%></a></span></td>
					<td><span class="table"><%= tb.getParameters().get("tranname").getParameterValue()%></span></td>
					<td>
						<span class="table"><a href="<%= qc.getHTTPGetQuery(profileName) + brandSol1%>" target="data">GO</a></span>
					</td>
			<%if(ConfigContext.isHosted()){%>
			<td><span class="table"><%= profileName%></span></td>
			<td><span class="table"><a href='<%= "ProductDemoServlet?KilledProfile=" + profileName + "&KilledFlowId=" + qid + brandSol%>'>KILL</a></span></td>
			<% }%>
				</tr>
		<% }}%>
			</table>			
		</td>
	</tr>
</table>
<br/>
<br/>
<%}else{
String em = "Your session has been lost. Please refresh your browser. If error persists please click Reset button below.";
if(refreshValue==null){
em = "Your session has been lost(authentication failed: null). Please refresh your browser. If error persists please click Reset button below.";
} else if(ConfigContext.getRefresh()==0L){
em = "Your session has been lost(authentication failed: 0). Please refresh your browser. If error persists please click Reset button below.";
} else if (ConfigContext.getRefresh()!=Long.valueOf(refreshValue)){
em = "Your session has been lost(authentication failed). Please refresh your browser. If error persists please click Reset button below.";
}%>
<jsp:forward page="/ErrorMessage.jsp">
	<jsp:param name="ErrorMessageText" value="<%= em%>"/>
	<jsp:param name="ErrorMessageReturn" value="IWLogin.jsp"/>
	<jsp:param name="PortalBrand" value="<%= brand%>"/>
	<jsp:param name="PortalSolutions" value="<%= solutions%>"/>
	<jsp:param name="ButtonLable" value="Reset"/>
</jsp:forward>
<%}%>
</body>
</html>