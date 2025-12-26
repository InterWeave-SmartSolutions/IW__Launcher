<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.interweave.businessDaemon.*" %>
<%@ page import="java.util.*" %>
<%
String brand = request.getParameter("PortalBrand");
if(brand==null){
brand="";
}
String solutions = request.getParameter("PortalSolutions");
if(solutions==null){
solutions="";
}
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IM Monitor Page</title>
<style>
<!--
.labels
   {
   color: black; font-family: Verdana; font-size: 7pt; font-style: normal; font-weight: bold;
   }
.tablelabels
   {
   color: black; font-family: Verdana; font-size: 6pt; font-style: normal; font-weight: bold;
   }
.table
   {
   color: black; font-family: Verdana; font-size: 6pt; font-style: normal; font-weight: bold;
   }
-->
</style>
</head>
<body>
<table border="0" cellpadding="0" width="100%" cellspacing="0">
	<tr>
		<td><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/IT Banner.png"%>" alt="Title" align="left" width="100%" height="94"/></td>
	</tr>
	<tr>
		<td><span style="color: black; font-family: Verdana; font-size: 15pt; font-style: normal; font-weight: bold">InterWeave Monitoring Utility</span><br/></td>
	</tr>
</table>
<% String currentProfileName = request.getParameter("CurrentProfile");
String email = null;
if(currentProfileName!=null){
int pzc = currentProfileName.lastIndexOf(":");
if(pzc>0){
email = currentProfileName.substring(pzc).trim();
}}
long rfInt = ConfigContext.getRefreshInterval();
%>
<applet height="0" width="0" code="TransState2.class" name="AP20">
	<param name="ProfileName" value='<%= ((currentProfileName==null)?"__default_profile__":currentProfileName)%>'/>
	<param name="RefreshInterval" value='<%= rfInt%>'/>
</applet>
<p><b><font size="4"><span style="color: black; font-family: Verdana; font-size: 14pt; font-style: normal; font-weight: bold">Running Transaction Flows</span></font></b></p>
	<table border="1" cellpadding="0" width="100%" cellspacing="0">
		<tr class="tablelabels">
			<td>Transaction Flow Id</td>
			<td>State</td>
			<td>Scheduled</td>
			<td>Single Run</td>
			<td>Interval</td>
			<td>Shift</td>
			<td>Query Starts</td>
			<td>Counter</td>
			<td>Runs</td>
			<%if(ConfigContext.isHosted()&&(currentProfileName==null)){%>
			<td>Profile</td>
			<% }%>
		</tr>
<%		for(int i = 0; i< ConfigContext.getTransactionList().size(); i++){
		TransactionContext tc = (TransactionContext)(ConfigContext.getTransactionList().get(i)); 
		String tid = tc.getTransactionId();
		Hashtable att = tc.getTransactionThreads();
		Iterator katt = att.keySet().iterator();
		while(katt.hasNext()){
		String profileName = (String)(katt.next());
		if(currentProfileName!=null && ((email==null) || (!profileName.endsWith(email)))){
			continue;
			}
		TransactionThread tt = (TransactionThread)(att.get(profileName));
		boolean stopped = !(tt.isActive() || tt.isExecuting());
		long trInt = tt.getTransactionInterval();
		long trShift = tt.getTransactionShiftFromHartBeat();
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
		String state = "STOPPED";
		String trQST = "";
		String sfr = "0/0";
		int trCnt = 0;
		if(stopped){
		trQST = tt.getTransactionStartTime().toString();
		sfr = tt.getSuccesses() + "/" + tt.getFailures();
		trCnt = tt.getTransactionCounter();
		}
		if(!stopped){%><applet height="0" width="0" code="TransState0.class" name='<%= "AP00:" + i + "=" + profileName%>'>
					<param name="Index" value='<%= "" + i%>'/>
					<param name="ProfileName" value='<%= profileName%>'/>
					<param name="RefreshInterval" value='<%= rfInt%>'/>
				</applet><%}%>
		<tr>
			<td><span class="table"><a href='<%= "FlowProperties.jsp?FromMonitor=1&CurrentFlowId=" + tid + "&CurrentProfile=" + profileName + "&IsFlow=1"%>' target="_blank"><%= tid%></a></span></td>
			<td><span class="table"><%if(stopped){%><%= state%><%}else{%> 
				<applet height="20" width="70" code="TransState1.class" class="table" name='<%= "AP10:" + i + "=" + profileName%>'>
<!--					<param name="Index" value='<%= "" + i%>'/>
					<param name="Mode" value="0"/>
					<param name="ProfileName" value='<%= profileName%>'/>
					<param name="RefreshInterval" value='<%= rfInt%>'/>-->
				</applet><%}%></span>
			</td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="1" <%= (trInt>0L || trShift<0L)?"checked":""%> "disabled" />
				</span></td>
			<td>
				<span class="table">
				<input type="radio" name='<%= "MS:" + i + "=" + profileName%>' value="0" <%= (trInt<=0L && trShift>=0L)?"checked":""%> "disabled" />
				</span></td>
			<td><span class="table"><%= trIntS%></span></td>
			<td><span class="table"><%= trShiftS%></span></td>
			<td><span class="table"><%if(stopped){%><%= trQST%><%}else{%>
				<applet height="20" width="140" code="TransState1.class" class="table" name='<%= "AP11:" + i + "=" + profileName%>'>
<!--					<param name="Index" value='<%= "" + i%>'/>
					<param name="Mode" value="1"/>
					<param name="ProfileName" value='<%= profileName%>'/>
					<param name="RefreshInterval" value='<%= rfInt%>'/>-->
				</applet><%}%></span>
			</td>
			<td><span class="table"><%if(stopped){%><%= trCnt%><%}else{%>
				<applet height="20" width="40" code="TransState1.class" class="table" name='<%= "AP12:" + i + "=" + profileName%>'>
<!--					<param name="Index" value='<%= "" + i%>'/>
					<param name="Mode" value="2"/>
					<param name="ProfileName" value='<%= profileName%>'/>
					<param name="RefreshInterval" value='<%= rfInt%>'/>-->
				</applet><%}%></span>
			</td>
			<td><span class="table"><%if(stopped){%><%= sfr%><%}else{%> 
				<applet height="20" width="40" code="TransState1.class" class="table" name='<%= "AP13:" + i + "=" + profileName%>'>
<!--					<param name="Index" value='<%= "" + i%>'/>
					<param name="Mode" value="3"/>
					<param name="ProfileName" value='<%= profileName%>'/>
					<param name="RefreshInterval" value='<%= rfInt%>'/>-->
				</applet><%}%></span>
			</td>
			<%if(ConfigContext.isHosted()&&(currentProfileName==null)){%>
			<td><span class="table"><%= profileName%></span></td>
			<% }%>
		</tr>
		<% }}%>
		
	</table>


</body>
<HEAD> 
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
</HEAD> 
</html>