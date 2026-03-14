package com.interweave.plugin.a4wSessionMgr;

/**
 * COM Interface 'IAccpacSessionMgr'. Generated 18/10/2006 4:28:04 PM
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
public interface IAccpacSessionMgr extends java.io.Serializable {
  /**
   * getAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setProgramName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppVersion  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getServerName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setServerName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isForceNewSignon  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setForceNewSignon  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * signoff. Signs off the session with the given signon ID.
   *
   * @param     signonID The signonID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void signoff  (
              int signonID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * installLanguageDependencies. Installs (if needed) the given language's common, application-wide, and UI-specific resource DLLs.
   *
   * @param     language The language (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void installLanguageDependencies  (
              String language) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String meterName) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppResource. Returns the application-wide resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPgmResource. Returns the program-specific resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getPgmResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCommonResource. Returns the system-wide common resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCommonResource2. Returns the application-wide resource object.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCheckSessionStatus  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCheckSessionStatus  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID7cf1bee6_c267_49e3_8297_860209fb4cc8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSessionMgrProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "7cf1bee6-c267-49e3-8297-860209fb4cc8";
  String DISPID_1_GET_NAME = "getAppID";
  String DISPID_1_PUT_NAME = "setAppID";
  String DISPID_2_GET_NAME = "getProgramName";
  String DISPID_2_PUT_NAME = "setProgramName";
  String DISPID_3_GET_NAME = "getAppVersion";
  String DISPID_3_PUT_NAME = "setAppVersion";
  String DISPID_4_GET_NAME = "getServerName";
  String DISPID_4_PUT_NAME = "setServerName";
  String DISPID_5_NAME = "createSession";
  String DISPID_6_GET_NAME = "isForceNewSignon";
  String DISPID_6_PUT_NAME = "setForceNewSignon";
  String DISPID_7_NAME = "signoff";
  String DISPID_8_NAME = "installLanguageDependencies";
  String DISPID_9_NAME = "createAccpacMeterCtrl";
  String DISPID_10_GET_NAME = "getAppResource";
  String DISPID_11_GET_NAME = "getPgmResource";
  String DISPID_12_GET_NAME = "getCommonResource";
  String DISPID_13_GET_NAME = "getAppResource2";
  String DISPID_14_GET_NAME = "getPgmResource2";
  String DISPID_15_GET_NAME = "getCommonResource2";
  String DISPID_16_GET_NAME = "isCheckSessionStatus";
  String DISPID_16_PUT_NAME = "setCheckSessionStatus";
  String DISPID_17_NAME = "createSessionEx";
}
