package com.interweave.plugin.a4wSessionMgr;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSessionMgr'. Generated 18/10/2006 4:28:04 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wSessionMgr.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC session manager interface</B>'
 *
 * Generator Options:
 *   AwtForOcxs = False
 *   PromptForTypeLibraries = False
 *   RetryOnReject = False
 *   IDispatchOnly = False
 *   GenBeanInfo = False
 *   LowerCaseMemberNames = True
 *   TreatInStarAsIn = False
 *   ArraysAsObjects = False
 *   OmitRestrictedMethods = False
 *   ClashPrefix = zz_
 *   ImplementConflictingInterfaces = False
 *   DontRenameSameMethods = False
 *   RenameConflictingInterfaceMethods = False
 *   ReuseMethods = False
 *
 * Command Line Only Options:
 *   MakeClsidsPublic = False
 *   DontOverwrite = False
 */
public class IAccpacSessionMgrProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgr, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wSessionMgr.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSessionMgr.class;

  public IAccpacSessionMgrProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSessionMgr.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSessionMgrProxy() {}

  public IAccpacSessionMgrProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSessionMgr.IID);
  }

  protected IAccpacSessionMgrProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSessionMgrProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
    super(CLSID, iid, host, authInfo);
  }

  public void addListener(String iidStr, Object theListener, Object theSource) throws java.io.IOException {
    super.addListener(iidStr, theListener, theSource);
  }

  public void removeListener(String iidStr, Object theListener) throws java.io.IOException {
    super.removeListener(iidStr, theListener);
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant parameters[] = {};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter value
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs Parameter used when getting the property.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant parameters[] = {rhs == null ? new Variant("rhs", 10, 0x80020004L) : new Variant("rhs", 12, rhs)};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @param     parameters One element for each parameter.  Use primitive type wrappers
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant variantParameters[] = new com.linar.jintegra.Variant[parameters.length];
    for(int i = 0; i < parameters.length; i++) {
      variantParameters[i] = parameters[i] == null ? new Variant("p" + i, 10, 0x80020004L) :
	                                                   new Variant("p" + i, 12, parameters[i]);
    }
    try {
      return super.invoke(name, super.getDispatchIdOfName(name), 1, variantParameters).getVARIANT();
    } catch(NoSuchFieldException nsfe) {
      throw new NoSuchMethodException("There is no method called " + name);
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
      return invokeMethodByName(name, new Object[]{});
  }

  /**
   * getAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppID", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setAppID", 8, zz_parameters);
    return;
  }

  /**
   * getProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProgramName", 9, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setProgramName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setProgramName", 10, zz_parameters);
    return;
  }

  /**
   * getAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppVersion", 11, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppVersion  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setAppVersion", 12, zz_parameters);
    return;
  }

  /**
   * getServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getServerName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getServerName", 13, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setServerName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setServerName", 14, zz_parameters);
    return;
  }

  /**
   * createSession. Gets a session from the session manager, given the object handle and initial signon ID.  The signon dialog box may be displayed.  Also retrieves the session's signon ID.
   *
   * @param     objectHandle The objectHandle (in)
   * @param     signonID The signonID (in/out: use single element array)
   * @param     session An reference to a  (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createSession  (
              String objectHandle,
              int[] signonID,
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { objectHandle, signonID, session, zz_retVal };
    vtblInvoke("createSession", 15, zz_parameters);
    return;
  }

  /**
   * isForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isForceNewSignon  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isForceNewSignon", 16, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setForceNewSignon  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setForceNewSignon", 17, zz_parameters);
    return;
  }

  /**
   * signoff. Signs off the session with the given signon ID.
   *
   * @param     signonID The signonID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void signoff  (
              int signonID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(signonID), zz_retVal };
    vtblInvoke("signoff", 18, zz_parameters);
    return;
  }

  /**
   * installLanguageDependencies. Installs (if needed) the given language's common, application-wide, and UI-specific resource DLLs.
   *
   * @param     language The language (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void installLanguageDependencies  (
              String language) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { language, zz_retVal };
    vtblInvoke("installLanguageDependencies", 19, zz_parameters);
    return;
  }

  /**
   * createAccpacMeterCtrl. Creates a new client-side meter given the session and an initial title for the meter.  Returns the client-side meter (AccpacMeterCtrl) object.
   *
   * @param     pSession An reference to a  (in)
   * @param     meterName The meterName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createAccpacMeterCtrl  (
              Object pSession,
              String meterName) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pSession, meterName, zz_retVal };
    vtblInvoke("createAccpacMeterCtrl", 20, zz_parameters);
    return;
  }

  /**
   * getAppResource. Returns the application-wide resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fallbackProgID, zz_retVal };
    vtblInvoke("getAppResource", 21, zz_parameters);
    return;
  }

  /**
   * getPgmResource. Returns the program-specific resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getPgmResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fallbackProgID, zz_retVal };
    vtblInvoke("getPgmResource", 22, zz_parameters);
    return;
  }

  /**
   * getCommonResource. Returns the system-wide common resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fallbackProgID, zz_retVal };
    vtblInvoke("getCommonResource", 23, zz_parameters);
    return;
  }

  /**
   * getAppResource2. Returns the application-wide resource object.
   *
   * @param     appPath The appPath (in)
   * @param     fallbackRscName The fallbackRscName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppResource2  (
              String appPath,
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { appPath, fallbackRscName, zz_retVal };
    vtblInvoke("getAppResource2", 24, zz_parameters);
    return;
  }

  /**
   * getPgmResource2. Returns the application-wide resource object.
   *
   * @param     appPath The appPath (in)
   * @param     fallbackRscName The fallbackRscName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getPgmResource2  (
              String appPath,
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { appPath, fallbackRscName, zz_retVal };
    vtblInvoke("getPgmResource2", 25, zz_parameters);
    return;
  }

  /**
   * getCommonResource2. Returns the application-wide resource object.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCommonResource2", 26, zz_parameters);
    return;
  }

  /**
   * isCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCheckSessionStatus  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isCheckSessionStatus", 27, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCheckSessionStatus  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setCheckSessionStatus", 28, zz_parameters);
    return;
  }

  /**
   * createSessionEx. Like CreateSession, but made for web and common desktops (more sensitive to which layer pops up dialogs).
   *
   * @param     objectHandle The objectHandle (in)
   * @param     signonID The signonID (in/out: use single element array)
   * @param     session An reference to a  (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createSessionEx  (
              String objectHandle,
              int[] signonID,
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { objectHandle, signonID, session, zz_retVal };
    vtblInvoke("createSessionEx", 29, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("7cf1bee6-c267-49e3-8297-860209fb4cc8", com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgrProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getAppID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setAppID",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProgramName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setProgramName",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppVersion",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setAppVersion",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getServerName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setServerName",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createSession",
            new Class[] { String.class, int[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectHandle", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("signonID", 16387, 6, 8, null, null), 
              new com.linar.jintegra.Param("session", 16393, 4, 4, "b3b13603-a675-11d2-9b95-00104b71eb3f", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isForceNewSignon",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setForceNewSignon",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("signoff",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("signonID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("installLanguageDependencies",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("language", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createAccpacMeterCtrl",
            new Class[] { Object.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pSession", 9, 2, 4, "b3b13603-a675-11d2-9b95-00104b71eb3f", null), 
              new com.linar.jintegra.Param("meterName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppResource",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fallbackProgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPgmResource",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fallbackProgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCommonResource",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fallbackProgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppResource2",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appPath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fallbackRscName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPgmResource2",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appPath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fallbackRscName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCommonResource2",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isCheckSessionStatus",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setCheckSessionStatus",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createSessionEx",
            new Class[] { String.class, int[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectHandle", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("signonID", 16387, 6, 8, null, null), 
              new com.linar.jintegra.Param("session", 16393, 4, 4, "b3b13603-a675-11d2-9b95-00104b71eb3f", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
