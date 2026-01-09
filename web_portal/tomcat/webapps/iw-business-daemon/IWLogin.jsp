<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>IW_IDE Secure Portal Login</title>
<style>
.arial12{font:Arial, Helvetica, sans-serif; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#454545; line-height:16px;}
.verdana10{font:verdana,Arial, sans-serif; font-family:verdana,Arial, sans-serif; font-size:10px; color:#000000; line-height:16px; font-weight:bold;}
.input-box{border:1px solid #000000; font:Verdana, Arial; font-family:Verdana, Arial; font-size:10px;}
.submit-button{border:1px solid #000000; font:Verdana, Arial; font-family:Verdana, Arial; font-size:10px; font-weight:bold; background-color:#CCCCCC;}
.error-msg{color:#cc0000; font-weight:bold; background-color:#ffeeee; padding:12px; border:1px solid #cc0000; margin:10px 0; border-radius:4px; font-family:Arial, sans-serif; font-size:12px;}
.error-msg-title{font-size:14px; margin-bottom:8px; color:#990000;}
.error-msg-text{font-weight:normal; line-height:18px; margin-bottom:8px;}
.error-msg-help{font-weight:normal; font-size:11px; background-color:#fff; padding:8px; margin-top:8px; border-left:3px solid #cc0000; color:#666;}
.error-code{font-family:monospace; font-size:10px; color:#999; float:right;}
.success-msg{color:#006600; font-weight:bold; background-color:#eeffee; padding:8px; border:1px solid #006600; margin:10px 0; border-radius:4px;}
</style>
</head>

<body>
<%
String email = request.getParameter("Email");
if(email==null){
    email = "";
}
String errorMsg = request.getParameter("error");
String errorCode = request.getParameter("errorCode");
String successMsg = request.getParameter("success");
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

// Helper function to get context-specific help message based on error code
String getErrorHelp(String code) {
    if (code == null) return null;

    if ("AUTH001".equals(code)) {
        return "&#8226; Verify your email address and password are correct<br>" +
               "&#8226; Passwords are case-sensitive<br>" +
               "&#8226; If you've forgotten your password, use the \"Change User Password\" link below<br>" +
               "&#8226; If you haven't registered, use the registration link above";
    } else if ("AUTH002".equals(code)) {
        return "&#8226; Contact your company administrator to activate your account<br>" +
               "&#8226; Check if you received an account activation email<br>" +
               "&#8226; Your account may need to be reactivated after a period of inactivity";
    } else if ("AUTH003".equals(code)) {
        return "&#8226; Contact your company administrator for assistance<br>" +
               "&#8226; Your company's subscription may have expired<br>" +
               "&#8226; Contact InterWeave support if you need immediate access";
    } else if ("DB001".equals(code)) {
        return "&#8226; Please try again in a few moments<br>" +
               "&#8226; If the problem continues, contact your system administrator<br>" +
               "&#8226; The system may be undergoing maintenance";
    } else if ("VALIDATION001".equals(code)) {
        return "&#8226; Both email and password are required to log in<br>" +
               "&#8226; Please fill in all fields before clicking Login";
    }

    return null;
}

String errorHelp = errorCode != null ? getErrorHelp(errorCode) : null;
%>
<form action="LoginServlet" method="post">
<input type="hidden" name="PortalBrand" value="<%= brand%>"/>
<input type="hidden" name="PortalSolutions" value="<%= solutions%>"/>
<center>
  <p>&nbsp;</p>
  <table width="535" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td align="left"><img src="<%= "images" + ((brand==null || brand.equals(""))?"":("/" + brand)) + "/logo.gif"%>" width="201" height="43"></td>
  </tr>
  <% if(errorMsg != null && !errorMsg.isEmpty()) { %>
  <tr>
    <td>
      <div class="error-msg">
        <% if(errorCode != null && !errorCode.isEmpty()) { %>
          <span class="error-code">[<%= errorCode %>]</span>
        <% } %>
        <div class="error-msg-title">&#9888; Login Error</div>
        <div class="error-msg-text"><%= errorMsg %></div>
        <% if(errorHelp != null) { %>
        <div class="error-msg-help">
          <strong>What to do next:</strong><br>
          <%= errorHelp %>
        </div>
        <% } %>
      </div>
    </td>
  </tr>
  <% } %>
  <% if(successMsg != null && !successMsg.isEmpty()) { %>
  <tr>
    <td><div class="success-msg"><%= successMsg %></div></td>
  </tr>
  <% } %>
  <tr>
    <td bgcolor="E7E6E3"  style="border:1px solid #B2B2B2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" class="arial12" >New User? Please <a href="<%= "Registration.jsp" + brandSol1%>" class="arial12">register here</a> </td>
        <td align="right" class="arial12">New Company? Please <a href="<%= "CompanyRegistration.jsp" + brandSol1%>" class="arial12">register here</a></td>
      </tr>
    </table>
      </td>
  </tr>
  <tr>
    <td width="533" height="250" background="images/bg.jpg" style="border-left:1px solid #B2B2B2; border-right:1px solid #B2B2B2"><table width="100%"  border="0" cellspacing="0" cellpadding="24">
      <tr>
        <td class="verdana10" align="left"><p>Username<br>
              <input name="Email" type="text" maxlength="255" class="input-box" value='<%= email%>'/>
              <br>
              <br>
            Password<br>
            <input name="Password" type="password" maxlength="255" class="input-box"/>
        <br>
              <br>
            <input type="submit" name="submit" value="Login" class="submit-button"/></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="E7E6E3"  style="border:1px solid #B2B2B2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" class="arial12"><a href="ChangePassword.jsp" class="arial12">Change User Password</a><br>
          <a href="<%= "EditProfile.jsp" + brandSol1%>" class="arial12">Edit User Profile</a><br>
          <a href="<%= "ChangeCompanyPassword.jsp" + brandSol1%>" class="arial12">Change Company Password</a><br>
          <a href="<%= "EditCompanyProfile.jsp" + brandSol1%>" class="arial12">Edit Company Profile</a></td>
        <td align="right" class="arial12"><%= brand.equals("")?"Return to ":"Visit "%>Interweave Home Page<br>
          <a href="http://www.interweave.biz" class="arial12">www.interweave.biz</a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="center"><img src="images/power.gif" width="224" height="27"></td>
  </tr>
</table>


</center>
</form>
</body>
</html>