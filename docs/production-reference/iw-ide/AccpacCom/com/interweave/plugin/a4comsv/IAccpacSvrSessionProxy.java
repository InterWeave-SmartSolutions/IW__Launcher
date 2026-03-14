package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrSession'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
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
public class IAccpacSvrSessionProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrSession, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrSession.class;

  public IAccpacSvrSessionProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrSession.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrSessionProxy() {}

  public IAccpacSvrSessionProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrSession.IID);
  }

  protected IAccpacSvrSessionProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrSessionProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * init. Initilizes the session object.
   *
   * @param     key The key (in)
   * @param     objectHandle The objectHandle (in)
   * @param     appID The appID (in)
   * @param     programName The programName (in)
   * @param     appVersion The appVersion (in)
   * @param     clientID The clientID (in)
   * @param     checkStatus The checkStatus (in)
   * @param     isOpened The isOpened (out: use single element array)
   * @param     sessionInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  (
              int key,
              String objectHandle,
              String appID,
              String programName,
              String appVersion,
              String clientID,
              boolean checkStatus,
              boolean[] isOpened,
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(key), objectHandle, appID, programName, appVersion, clientID, new Boolean(checkStatus), isOpened, sessionInfo, zz_retVal };
    vtblInvoke("init", 7, zz_parameters);
    return;
  }

  /**
   * open. Establishes an ACCPAC session.
   *
   * @param     userIdentifier The userIdentifier (in)
   * @param     password The password (in)
   * @param     dataBase The dataBase (in)
   * @param     sessionDate The sessionDate (in)
   * @param     flags The flags (in)
   * @param     reserved The reserved (in)
   * @param     checkStatus The checkStatus (in)
   * @param     sessionInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void open  (
              String userIdentifier,
              String password,
              String dataBase,
              java.util.Date sessionDate,
              int flags,
              String reserved,
              boolean checkStatus,
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userIdentifier, password, dataBase, sessionDate, new Integer(flags), reserved, new Boolean(checkStatus), sessionInfo, zz_retVal };
    vtblInvoke("open", 8, zz_parameters);
    return;
  }

  /**
   * close. Closes the session.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("close", 9, zz_parameters);
    return;
  }

  /**
   * openDBLink. Creates an AccpacDBLink object based on the specified link type and flags.
   *
   * @param     linkType A com.interweave.plugin.a4comsv.tagSvrDBLinkTypeEnum constant (in)
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrDBLinkFlagsEnum constant (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrDBLink
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrDBLink openDBLink  (
              int linkType,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrDBLink zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(linkType), new Integer(flags), zz_retVal };
    vtblInvoke("openDBLink", 10, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrDBLink)zz_retVal[0];
  }

  /**
   * reportSelect. Creates an AccpacReport object that represents the selected report.
   *
   * @param     bstrReportName The bstrReportName (in)
   * @param     menuID The menuID (in)
   * @param     programID The programID (in)
   * @param     printSettings An unsigned byte (out: use single element array)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrReport
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrReport reportSelect  (
              String bstrReportName,
              String menuID,
              String programID,
              byte[][] printSettings) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrReport zz_retVal[] = { null };
    Object zz_parameters[] = { bstrReportName, menuID, programID, printSettings, zz_retVal };
    vtblInvoke("reportSelect", 11, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrReport)zz_retVal[0];
  }

  /**
   * rscGetString. Retrives a resource string according to the specified application ID and resource ID.
   *
   * @param     appID The appID (in)
   * @param     strID The strID (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String rscGetString  (
              String appID,
              int strID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { appID, new Integer(strID), zz_retVal };
    vtblInvoke("rscGetString", 12, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getAppID. Returns the application ID passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppID", 13, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getProgramName. Returns the program name passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProgramName", 14, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getCurRateTypeDescription. Retrieves the currency rate type description of the specified rate type code. Returns whether the rate type code exists or not.
   *
   * @param     rateTypeCode The rateTypeCode (in)
   * @param     rateTypeDescription The rateTypeDescription (out: use single element array)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getCurRateTypeDescription  (
              String rateTypeCode,
              String[] rateTypeDescription) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { rateTypeCode, rateTypeDescription, zz_retVal };
    vtblInvoke("getCurRateTypeDescription", 15, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getProfiles. Returns arrays of Profile IDs and descriptions for all the UI customization profiles that are set up.
   *
   * @param     profileIDs The profileIDs (out: use single element array)
   * @param     profileDescs The profileDescs (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getProfiles  (
              String[][] profileIDs,
              String[][] profileDescs) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { profileIDs, profileDescs, zz_retVal };
    vtblInvoke("getProfiles", 16, zz_parameters);
    return;
  }

  /**
   * getProfileCustomizations. Returns an array of controls to be hidden for a particular UI customization profile applied to the specified UIKey. Only users logged in as administrators can call this method.
   *
   * @param     profileID The profileID (in)
   * @param     uIKey The uIKey (in)
   * @return    The hiddenControls
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String[] getProfileCustomizations  (
              String profileID,
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String[] zz_retVal[] = { null };
    Object zz_parameters[] = { profileID, uIKey, zz_retVal };
    vtblInvoke("getProfileCustomizations", 17, zz_parameters);
    return (String[])zz_retVal[0];
  }

  /**
   * saveProfileCustomizations. Saves customization details to the specified UI customization profile.
   *
   * @param     profileIDs The profileIDs (in)
   * @param     uIKey The uIKey (in)
   * @param     hiddenControls The hiddenControls (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void saveProfileCustomizations  (
              String[] profileIDs,
              String uIKey,
              String[] hiddenControls) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { profileIDs, uIKey, hiddenControls, zz_retVal };
    vtblInvoke("saveProfileCustomizations", 18, zz_parameters);
    return;
  }

  /**
   * getUserCustomizations. Retrives UI customization information for the current user on the specified UI.
   *
   * @param     uIKey The uIKey (in)
   * @return    The hiddenControls
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String[] getUserCustomizations  (
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String[] zz_retVal[] = { null };
    Object zz_parameters[] = { uIKey, zz_retVal };
    vtblInvoke("getUserCustomizations", 19, zz_parameters);
    return (String[])zz_retVal[0];
  }

  /**
   * createProfile. Creates a UI customization profile with the specified name and description.
   *
   * @param     profileID The profileID (in)
   * @param     profileDesc The profileDesc (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createProfile  (
              String profileID,
              String profileDesc) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { profileID, profileDesc, zz_retVal };
    vtblInvoke("createProfile", 20, zz_parameters);
    return;
  }

  /**
   * isOpened. Returns whether the session object is open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isOpened  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isOpened", 21, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getUserID. Returns the user ID of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getUserID", 22, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getUserLanguage. Returns the language code of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserLanguage  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getUserLanguage", 23, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * createObjectHandle. Creates a new object handle with the intent to launch another application specified by the object ID. Returns the new object handle and the actual class ID and codebase of the specified object ID to launch.
   *
   * @param     objectID The objectID (in)
   * @param     objectKey The objectKey (in)
   * @param     objectHandle The objectHandle (out: use single element array)
   * @param     cLSID The cLSID (out: use single element array)
   * @param     codebase The codebase (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createObjectHandle  (
              String objectID,
              String objectKey,
              String[] objectHandle,
              String[] cLSID,
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { objectID, objectKey, objectHandle, cLSID, codebase, zz_retVal };
    vtblInvoke("createObjectHandle", 24, zz_parameters);
    return;
  }

  /**
   * getObjectKey. Returns the object key passed in by the caller application.
   *
   * @return    The pKey
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectKey  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getObjectKey", 25, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getCompanyID. Returns the company ID of the current logged-in company. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCompanyID", 26, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getPrintSetup. Returns an AccpacPrintSetup object that represents the print setup for the specified menu and program ID, for the current user.
   *
   * @param     menuID The menuID (in)
   * @param     programID The programID (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup getPrintSetup  (
              String menuID,
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup zz_retVal[] = { null };
    Object zz_parameters[] = { menuID, programID, zz_retVal };
    vtblInvoke("getPrintSetup", 27, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup)zz_retVal[0];
  }

  /**
   * getCompanyName. Returns the name of the current logged-in company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCompanyName", 28, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getSessionDate. Returns the session date used to open the session.
   *
   * @return    The sessionDate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getSessionDate  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSessionDate", 29, zz_parameters);
    return (java.util.Date)zz_retVal[0];
  }

  /**
   * getHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpPath  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getHelpPath", 30, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isRemote. Returns whether the current session is acessing a remote ACCPAC server or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemote  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRemote", 31, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getInstalledReports. Retrieves an array of installed report files (on the server, if the session is remote) for the specified application.
   *
   * @param     appID The appID (in)
   * @return    The reports
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String[] getInstalledReports  (
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String[] zz_retVal[] = { null };
    Object zz_parameters[] = { appID, zz_retVal };
    vtblInvoke("getInstalledReports", 32, zz_parameters);
    return (String[])zz_retVal[0];
  }

  /**
   * getProductSeries. Returns the currently installed ACCPAC product series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrProductSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getProductSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProductSeries", 33, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getDatabaseSeries. Returns the currently installed ACCPAC database series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDatabaseSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDatabaseSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDatabaseSeries", 34, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getIniFileKey. Retrieves the specified key from the application initialization file. Returns whether the key is found or not.
   *
   * @param     appID The appID (in)
   * @param     primaryKey The primaryKey (in)
   * @param     secondaryKey The secondaryKey (in)
   * @param     keyData The keyData (out: use single element array)
   * @return    The keyFound
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getIniFileKey  (
              String appID,
              String primaryKey,
              String secondaryKey,
              String[] keyData) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { appID, primaryKey, secondaryKey, keyData, zz_retVal };
    vtblInvoke("getIniFileKey", 35, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getObjectCLSID. Retrieves the CLSID and distribution file for the specified object ID. Returns whether the object ID indicates a valid ActiveX UI object or not.
   *
   * @param     objectID The objectID (in)
   * @param     distFileType A com.interweave.plugin.a4comsv.tagSvrDistFileType constant (in)
   * @param     cLSID The cLSID (out: use single element array)
   * @param     codebase The codebase (out: use single element array)
   * @return    The validObjectID
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getObjectCLSID  (
              String objectID,
              int distFileType,
              String[] cLSID,
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { objectID, new Integer(distFileType), cLSID, codebase, zz_retVal };
    vtblInvoke("getObjectCLSID", 36, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * checkSessionDate. Retrieves whether or not the logged-in session date falls within a fiscal period, and whether or not that period is active.
   *
   * @param     dateInFiscal The dateInFiscal (out: use single element array)
   * @param     periodActive The periodActive (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void checkSessionDate  (
              boolean[] dateInFiscal,
              boolean[] periodActive) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { dateInFiscal, periodActive, zz_retVal };
    vtblInvoke("checkSessionDate", 37, zz_parameters);
    return;
  }

  /**
   * checkHomeCurrency. Returns whether or not the company's home currency exists in the currency table.
   *
   * @return    The currencyExists
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("checkHomeCurrency", 38, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * checkReminders. Returns whether or not there are active reminders for the logged-in user.
   *
   * @return    The remindersExist
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkReminders  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("checkReminders", 39, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * checkRestartRecs. Returns whether or not there are restart records and whether or not the current user is the administrator.
   *
   * @param     restartRecsExist The restartRecsExist (out: use single element array)
   * @param     isAdmin The isAdmin (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void checkRestartRecs  (
              boolean[] restartRecsExist,
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { restartRecsExist, isAdmin, zz_retVal };
    vtblInvoke("checkRestartRecs", 40, zz_parameters);
    return;
  }

  /**
   * setPW. 
   *
   * @param     userID The userID (in)
   * @param     oldPW The oldPW (in)
   * @param     newPW The newPW (in)
   * @return    The changed
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean setPW  (
              String userID,
              String oldPW,
              String newPW) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { userID, oldPW, newPW, zz_retVal };
    vtblInvoke("setPW", 41, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getAppVersion. Returns the version of the application to which the UI belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppVersion", 42, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getCodebase. Returns the base path to the all the applications' CAB files.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCodebase  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCodebase", 43, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getAccpacMeter. Returns the AccpacMeter object for session.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrMeter (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAccpacMeter  (
              com.interweave.plugin.a4comsv.IAccpacSvrMeter[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("getAccpacMeter", 44, zz_parameters);
    return;
  }

  /**
   * macroRecordObject. Associates an object name from the application's initialization file with a view, in order for automatic macro recording to take place.
   *
   * @param     hWnd The hWnd (in)
   * @param     objectName The objectName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroRecordObject  (
              int hWnd,
              String objectName) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(hWnd), objectName, zz_retVal };
    vtblInvoke("macroRecordObject", 45, zz_parameters);
    return;
  }

  /**
   * macroPause. Pauses macro recording. Generally macroPause and macroResume are called as a pair.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroPause  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("macroPause", 46, zz_parameters);
    return;
  }

  /**
   * macroResume. Resumes macro recording. macroResume must follow macroPause.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroResume  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("macroResume", 47, zz_parameters);
    return;
  }

  /**
   * getDependencies. Returns a list of dependent classes based on the current Program Name and the user's language setting
   *
   * @param     codebaseType A com.interweave.plugin.a4comsv.tagSvrDistFileType constant (in)
   * @param     cLSIDs The cLSIDs (out: use single element array)
   * @param     codebases The codebases (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getDependencies  (
              int codebaseType,
              String[][] cLSIDs,
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(codebaseType), cLSIDs, codebases, zz_retVal };
    vtblInvoke("getDependencies", 48, zz_parameters);
    return;
  }

  /**
   * getDependenciesForLanguage. Returns a list of dependent classes based on the current Program Name and the supplied language code.
   *
   * @param     language The language (in)
   * @param     codebaseType A com.interweave.plugin.a4comsv.tagSvrDistFileType constant (in)
   * @param     cLSIDs The cLSIDs (out: use single element array)
   * @param     codebases The codebases (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getDependenciesForLanguage  (
              String language,
              int codebaseType,
              String[][] cLSIDs,
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { language, new Integer(codebaseType), cLSIDs, codebases, zz_retVal };
    vtblInvoke("getDependenciesForLanguage", 49, zz_parameters);
    return;
  }

  /**
   * setLegacyReturnCode. Sets the return code given to legacy ACCPAC applications.
   *
   * @param     returnCode The returnCode (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setLegacyReturnCode  (
              short returnCode) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Short(returnCode), zz_retVal };
    vtblInvoke("setLegacyReturnCode", 50, zz_parameters);
    return;
  }

  /**
   * setHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHelpPath  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setHelpPath", 51, zz_parameters);
    return;
  }

  /**
   * getHelpURL. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getHelpURL", 52, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * checkStatus. Returns various status of the current opened session.
   *
   * @param     cSActivated The cSActivated (out: use single element array)
   * @param     dateInFiscal The dateInFiscal (out: use single element array)
   * @param     periodActive The periodActive (out: use single element array)
   * @param     homeCurrencyExists The homeCurrencyExists (out: use single element array)
   * @param     remindersExist The remindersExist (out: use single element array)
   * @param     restartRecsExist The restartRecsExist (out: use single element array)
   * @param     isAdmin The isAdmin (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void checkStatus  (
              boolean[] cSActivated,
              boolean[] dateInFiscal,
              boolean[] periodActive,
              boolean[] homeCurrencyExists,
              boolean[] remindersExist,
              boolean[] restartRecsExist,
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { cSActivated, dateInFiscal, periodActive, homeCurrencyExists, remindersExist, restartRecsExist, isAdmin, zz_retVal };
    vtblInvoke("checkStatus", 53, zz_parameters);
    return;
  }

  /**
   * getSignonInfo. Returns the signon information of the current session in one call.
   *
   * @param     userID The userID (out: use single element array)
   * @param     companyID The companyID (out: use single element array)
   * @param     companyName The companyName (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getSignonInfo  (
              String[] userID,
              String[] companyID,
              String[] companyName) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userID, companyID, companyName, zz_retVal };
    vtblInvoke("getSignonInfo", 54, zz_parameters);
    return;
  }

  /**
   * zz_clone. Creates a new AccpacSession object with the same signon information, but attaching to the specified company.
   *
   * @param     companyID The companyID (in)
   * @param     ppSession An reference to a com.interweave.plugin.a4comsv.IAccpacSvrSession (out: use single element array)
   * @param     sessionInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_clone  (
              String companyID,
              com.interweave.plugin.a4comsv.IAccpacSvrSession[] ppSession,
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { companyID, ppSession, sessionInfo, zz_retVal };
    vtblInvoke("zz_clone", 55, zz_parameters);
    return;
  }

  /**
   * initClientSharedDataDirectory. private method
   *
   * @param     pMultipleDirsSetup The pMultipleDirsSetup (out: use single element array)
   * @param     pMutlipleDirs The pMutlipleDirs (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initClientSharedDataDirectory  (
              boolean[] pMultipleDirsSetup,
              boolean[] pMutlipleDirs) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pMultipleDirsSetup, pMutlipleDirs, zz_retVal };
    vtblInvoke("initClientSharedDataDirectory", 56, zz_parameters);
    return;
  }

  /**
   * getClientSharedDataDirs. private method
   *
   * @param     domain The domain (out: use single element array)
   * @param     user The user (out: use single element array)
   * @param     dataNames The dataNames (out: use single element array)
   * @param     dataPaths The dataPaths (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getClientSharedDataDirs  (
              String[] domain,
              String[] user,
              String[][] dataNames,
              String[][] dataPaths) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { domain, user, dataNames, dataPaths, zz_retVal };
    vtblInvoke("getClientSharedDataDirs", 57, zz_parameters);
    return;
  }

  /**
   * setClientSharedDataDirectory. private method
   *
   * @param     bstrPath The bstrPath (in)
   * @param     bSignOn The bSignOn (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setClientSharedDataDirectory  (
              String bstrPath,
              boolean bSignOn) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { bstrPath, new Boolean(bSignOn), zz_retVal };
    vtblInvoke("setClientSharedDataDirectory", 58, zz_parameters);
    return;
  }

  /**
   * getClientSharedDataDirectory. private method
   *
   * @return    The pSharedDataDirectory
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientSharedDataDirectory  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getClientSharedDataDirectory", 59, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * orgsGetInfo. 
   *
   * @param     count The count (out: use single element array)
   * @param     orgs An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void orgsGetInfo  (
              int[] count,
              byte[][] orgs) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, orgs, zz_retVal };
    vtblInvoke("orgsGetInfo", 60, zz_parameters);
    return;
  }

  /**
   * errGetCount. 
   *
   * @param     count The count (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGetCount  (
              int[] count) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, zz_retVal };
    vtblInvoke("errGetCount", 61, zz_parameters);
    return;
  }

  /**
   * errGetMessage. 
   *
   * @param     ulIndex The ulIndex (in)
   * @param     msg The msg (out: use single element array)
   * @param     priority A com.interweave.plugin.a4comsv.tagSvrErrorPriority constant (out: use single element array)
   * @param     source The source (out: use single element array)
   * @param     errCode The errCode (out: use single element array)
   * @param     helpFile The helpFile (out: use single element array)
   * @param     helpID The helpID (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGetMessage  (
              int ulIndex,
              String[] msg,
              int[] priority,
              String[] source,
              String[] errCode,
              String[] helpFile,
              int[] helpID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(ulIndex), msg, priority, source, errCode, helpFile, helpID, zz_retVal };
    vtblInvoke("errGetMessage", 62, zz_parameters);
    return;
  }

  /**
   * errPutMessage. 
   *
   * @param     msg The msg (in)
   * @param     enumPriority A com.interweave.plugin.a4comsv.tagSvrErrorPriority constant (in)
   * @param     params A Variant (in)
   * @param     source The source (in)
   * @param     errCode The errCode (in)
   * @param     helpFile The helpFile (in)
   * @param     lHelpID The lHelpID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errPutMessage  (
              String msg,
              int enumPriority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { msg, new Integer(enumPriority), params == null ? new Variant("params") : params, source, errCode, helpFile, new Integer(lHelpID), zz_retVal };
    vtblInvoke("errPutMessage", 63, zz_parameters);
    return;
  }

  /**
   * errPutRscMessage. 
   *
   * @param     appID The appID (in)
   * @param     rscID The rscID (in)
   * @param     enumPriority A com.interweave.plugin.a4comsv.tagSvrErrorPriority constant (in)
   * @param     params A Variant (in)
   * @param     source The source (in)
   * @param     errCode The errCode (in)
   * @param     helpFile The helpFile (in)
   * @param     lHelpID The lHelpID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errPutRscMessage  (
              String appID,
              int rscID,
              int enumPriority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { appID, new Integer(rscID), new Integer(enumPriority), params == null ? new Variant("params") : params, source, errCode, helpFile, new Integer(lHelpID), zz_retVal };
    vtblInvoke("errPutRscMessage", 64, zz_parameters);
    return;
  }

  /**
   * errClear. 
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errClear  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("errClear", 65, zz_parameters);
    return;
  }

  /**
   * errGenerateFile. 
   *
   * @param     errFile The errFile (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGenerateFile  (
              String[] errFile) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { errFile, zz_retVal };
    vtblInvoke("errGenerateFile", 66, zz_parameters);
    return;
  }

  /**
   * muLockRsc. Locks a resource shared or exclusive. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muLockRsc  (
              String resource,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { resource, new Boolean(exclusive), zz_retVal };
    vtblInvoke("muLockRsc", 67, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muUnlockRsc. Unlocks a resource locked by a previous call to LockRsc. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muUnlockRsc  (
              String resource) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { resource, zz_retVal };
    vtblInvoke("muUnlockRsc", 68, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muLockApp. Locks an application's data shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muLockApp  (
              String orgID,
              String appID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, appID, new Boolean(exclusive), zz_retVal };
    vtblInvoke("muLockApp", 69, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muUnlockApp. Unlocks an application's data locked by a previous call to LockApp. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muUnlockApp  (
              String orgID,
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, appID, zz_retVal };
    vtblInvoke("muUnlockApp", 70, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muLockOrg. Locks an organization's database shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muLockOrg  (
              String orgID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, new Boolean(exclusive), zz_retVal };
    vtblInvoke("muLockOrg", 71, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muUnlockOrg. Unlocks an organization's database locked by a previous call to LockOrg. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muUnlockOrg  (
              String orgID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, zz_retVal };
    vtblInvoke("muUnlockOrg", 72, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muRegradeApp. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an application's data. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muRegradeApp  (
              String orgID,
              String appID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, appID, new Boolean(upgrade), zz_retVal };
    vtblInvoke("muRegradeApp", 73, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muRegradeOrg. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an organization's database. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muRegradeOrg  (
              String orgID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { orgID, new Boolean(upgrade), zz_retVal };
    vtblInvoke("muRegradeOrg", 74, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * muTest. Tests if the specified resource is locked. If the resource is locked, the Exclusive parameter returns whether the exsiting lock is shared or exclusive.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (out: use single element array)
   * @return    The locked
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean muTest  (
              String resource,
              boolean[] exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { resource, exclusive, zz_retVal };
    vtblInvoke("muTest", 75, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * curGetCurTable. 
   *
   * @param     curCode The curCode (in)
   * @param     rateType The rateType (in)
   * @param     description The description (out: use single element array)
   * @param     dateMatch A com.interweave.plugin.a4comsv.tagSvrDateMatchEnum constant (out: use single element array)
   * @param     operator A com.interweave.plugin.a4comsv.tagSvrRateOperatorEnum constant (out: use single element array)
   * @param     source The source (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curGetCurTable  (
              String curCode,
              String rateType,
              String[] description,
              int[] dateMatch,
              int[] operator,
              String[] source) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { curCode, rateType, description, dateMatch, operator, source, zz_retVal };
    vtblInvoke("curGetCurTable", 76, zz_parameters);
    return;
  }

  /**
   * curGetCurRate. 
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @param     curRateType A com.interweave.plugin.a4comsv.tagSvrCurRateTypeEnum constant (in)
   * @param     rateInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curGetCurRate  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date,
              int curRateType,
              byte[][] rateInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrencyCode, rateType, sourceCurrencyCode, date, new Integer(curRateType), rateInfo, zz_retVal };
    vtblInvoke("curGetCurRate", 77, zz_parameters);
    return;
  }

  /**
   * curGetCurrency. 
   *
   * @param     currencyCode The currencyCode (in)
   * @param     currency An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curGetCurrency  (
              String currencyCode,
              byte[][] currency) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { currencyCode, currency, zz_retVal };
    vtblInvoke("curGetCurrency", 78, zz_parameters);
    return;
  }

  /**
   * curIsBlockCombination. Determines if the currency and the specified currency code belong in the same currency block, both as members, or one as a member and the other as its master.
   *
   * @param     homeCurrency The homeCurrency (in)
   * @param     sourceCurrency The sourceCurrency (in)
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4comsv.tagSvrBlockDateMatchEnum constant (in)
   * @param     isCombination The isCombination (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curIsBlockCombination  (
              String homeCurrency,
              String sourceCurrency,
              java.util.Date date,
              int blockDateMatch,
              boolean[] isCombination) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrency, sourceCurrency, date, new Integer(blockDateMatch), isCombination, zz_retVal };
    vtblInvoke("curIsBlockCombination", 79, zz_parameters);
    return;
  }

  /**
   * curIsBlockMaster. Determines if the currency is a block master currency.
   *
   * @param     curCode The curCode (in)
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4comsv.tagSvrBlockDateMatchEnum constant (in)
   * @param     isMaster The isMaster (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curIsBlockMaster  (
              String curCode,
              java.util.Date date,
              int blockDateMatch,
              boolean[] isMaster) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { curCode, date, new Integer(blockDateMatch), isMaster, zz_retVal };
    vtblInvoke("curIsBlockMaster", 80, zz_parameters);
    return;
  }

  /**
   * curIsBlockMember. Determines if the currency is a member of a currency block, and optionally retrieves the currency rate information with its master.
   *
   * @param     curCode The curCode (in)
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4comsv.tagSvrBlockDateMatchEnum constant (in)
   * @param     isMember The isMember (out: use single element array)
   * @param     rate An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void curIsBlockMember  (
              String curCode,
              java.util.Date date,
              int blockDateMatch,
              boolean[] isMember,
              byte[][] rate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { curCode, date, new Integer(blockDateMatch), isMember, rate, zz_retVal };
    vtblInvoke("curIsBlockMember", 81, zz_parameters);
    return;
  }

  /**
   * propGet. Retrieves the ACCPAC property. Returns the error code of the operation.
   *
   * @param     objectID The objectID (in)
   * @param     menuID The menuID (in)
   * @param     keyword The keyword (in)
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @param     varBuf A Variant (out: use single element array)
   * @param     propType A com.interweave.plugin.a4comsv.tagSvrPropertyType constant (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propGet  (
              String objectID,
              String menuID,
              String keyword,
              String appID,
              String appVersion,
              Object[] varBuf,
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { objectID, menuID, keyword, appID, appVersion, varBuf, new Integer(propType), zz_retVal };
    vtblInvoke("propGet", 82, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * propPut. Saves the value as an ACCPAC property. Returns the error code of the operation.
   *
   * @param     objectID The objectID (in)
   * @param     menuID The menuID (in)
   * @param     keyword The keyword (in)
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @param     varBuf A Variant (in)
   * @param     lSize The lSize (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propPut  (
              String objectID,
              String menuID,
              String keyword,
              String appID,
              String appVersion,
              Object varBuf,
              int lSize) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { objectID, menuID, keyword, appID, appVersion, varBuf == null ? new Variant("varBuf") : varBuf, new Integer(lSize), zz_retVal };
    vtblInvoke("propPut", 83, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * propClear. Removes the ACCPAC property. Returns the error code of the operation.
   *
   * @param     objectID The objectID (in)
   * @param     menuID The menuID (in)
   * @param     keyword The keyword (in)
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propClear  (
              String objectID,
              String menuID,
              String keyword,
              String appID,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { objectID, menuID, keyword, appID, appVersion, zz_retVal };
    vtblInvoke("propClear", 84, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getClientDomainUser. private method
   *
   * @param     pDomain The pDomain (out: use single element array)
   * @param     pUser The pUser (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getClientDomainUser  (
              String[] pDomain,
              String[] pUser) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pDomain, pUser, zz_retVal };
    vtblInvoke("getClientDomainUser", 85, zz_parameters);
    return;
  }

  /**
   * getProgramsPathOnServer. The DOS path to ACCPAC programs directory on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramsPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProgramsPathOnServer", 86, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getSharedDataPathOnServer. The DOS path to ACCPAC shared data on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSharedDataPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSharedDataPathOnServer", 87, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getVersion. Returns version information for the ACCPAC Server
   *
   * @param     majorVersion The majorVersion (in)
   * @param     minorVersion The minorVersion (in)
   * @param     build The build (in)
   * @param     revision The revision (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getVersion  (
              int[] majorVersion,
              int[] minorVersion,
              int[] build,
              int[] revision) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { majorVersion, minorVersion, build, revision, zz_retVal };
    vtblInvoke("getVersion", 88, zz_parameters);
    return;
  }

  /**
   * registerDesktopSession. private method
   *
   * @param     userIdentifier The userIdentifier (in)
   * @param     dataBase The dataBase (in)
   * @param     clientID The clientID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void registerDesktopSession  (
              String userIdentifier,
              String dataBase,
              String clientID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userIdentifier, dataBase, clientID, zz_retVal };
    vtblInvoke("registerDesktopSession", 89, zz_parameters);
    return;
  }

  /**
   * unregisterDesktopSession. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unregisterDesktopSession  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("unregisterDesktopSession", 90, zz_parameters);
    return;
  }

  /**
   * getAppDependenciesForLanguage. 
   *
   * @param     appID The appID (in)
   * @param     pgmName The pgmName (in)
   * @param     appVersion The appVersion (in)
   * @param     language The language (in)
   * @param     codebaseType A com.interweave.plugin.a4comsv.tagSvrDistFileType constant (in)
   * @param     cLSIDs The cLSIDs (out: use single element array)
   * @param     codebases The codebases (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppDependenciesForLanguage  (
              String appID,
              String pgmName,
              String appVersion,
              String language,
              int codebaseType,
              String[][] cLSIDs,
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { appID, pgmName, appVersion, language, new Integer(codebaseType), cLSIDs, codebases, zz_retVal };
    vtblInvoke("getAppDependenciesForLanguage", 91, zz_parameters);
    return;
  }

  /**
   * isEnforceAppVersion. 
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEnforceAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isEnforceAppVersion", 92, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setEnforceAppVersion. 
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setEnforceAppVersion  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setEnforceAppVersion", 93, zz_parameters);
    return;
  }

  /**
   * getSystemHelpURL. 
   *
   * @return    The helpURL
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSystemHelpURL", 94, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * licenseStatus. 
   *
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrLicenseStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int licenseStatus  (
              String appID,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { appID, appVersion, zz_retVal };
    vtblInvoke("licenseStatus", 95, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * upload. 
   *
   * @param     filename The filename (in)
   * @param     root A com.interweave.plugin.a4comsv.tagSvrFileLocation constant (in)
   * @param     subDir The subDir (in)
   * @param     data An unsigned byte (in)
   * @param     more The more (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void upload  (
              String filename,
              int root,
              String subDir,
              byte[] data,
              boolean more) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { filename, new Integer(root), subDir, data, new Boolean(more), zz_retVal };
    vtblInvoke("upload", 96, zz_parameters);
    return;
  }

  /**
   * download. 
   *
   * @param     root A com.interweave.plugin.a4comsv.tagSvrFileLocation constant (in)
   * @param     subPath The subPath (in)
   * @param     filename The filename (out: use single element array)
   * @param     data An unsigned byte (out: use single element array)
   * @param     moreFile The moreFile (out: use single element array)
   * @param     moreData The moreData (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void download  (
              int root,
              String subPath,
              String[] filename,
              byte[][] data,
              boolean[] moreFile,
              boolean[] moreData) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(root), subPath, filename, data, moreFile, moreData, zz_retVal };
    vtblInvoke("download", 97, zz_parameters);
    return;
  }

  /**
   * reportSelect2. Creates an AccpacReport object that represents the selected report.
   *
   * @param     bstrReportName The bstrReportName (in)
   * @param     menuID The menuID (in)
   * @param     programID The programID (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrReport
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrReport reportSelect2  (
              String bstrReportName,
              String menuID,
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrReport zz_retVal[] = { null };
    Object zz_parameters[] = { bstrReportName, menuID, programID, zz_retVal };
    vtblInvoke("reportSelect2", 98, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrReport)zz_retVal[0];
  }

  /**
   * checkStatus2. Returns various status of the current opened session.
   *
   * @param     cSActivated The cSActivated (out: use single element array)
   * @param     dateInFiscal The dateInFiscal (out: use single element array)
   * @param     periodActive The periodActive (out: use single element array)
   * @param     homeCurrencyExists The homeCurrencyExists (out: use single element array)
   * @param     remindersExist The remindersExist (out: use single element array)
   * @param     restartRecsExist The restartRecsExist (out: use single element array)
   * @param     isAdmin The isAdmin (out: use single element array)
   * @param     periodOpen The periodOpen (out: use single element array)
   * @param     warnPeriodLocked The warnPeriodLocked (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void checkStatus2  (
              boolean[] cSActivated,
              boolean[] dateInFiscal,
              boolean[] periodActive,
              boolean[] homeCurrencyExists,
              boolean[] remindersExist,
              boolean[] restartRecsExist,
              boolean[] isAdmin,
              boolean[] periodOpen,
              boolean[] warnPeriodLocked) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { cSActivated, dateInFiscal, periodActive, homeCurrencyExists, remindersExist, restartRecsExist, isAdmin, periodOpen, warnPeriodLocked, zz_retVal };
    vtblInvoke("checkStatus2", 99, zz_parameters);
    return;
  }

  /**
   * isServerMachine. property IsServerMachine
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isServerMachine  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isServerMachine", 100, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * doesPWExpireToday. Returns whether user password is expiring today or within two weeks.
   *
   * @param     userID The userID (in)
   * @param     pW The pW (in)
   * @param     expiresToday The expiresToday (out: use single element array)
   * @param     expiresInTwoWeeks The expiresInTwoWeeks (out: use single element array)
   * @param     pDaysLeft The pDaysLeft (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void doesPWExpireToday  (
              String userID,
              String pW,
              boolean[] expiresToday,
              boolean[] expiresInTwoWeeks,
              int[] pDaysLeft) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userID, pW, expiresToday, expiresInTwoWeeks, pDaysLeft, zz_retVal };
    vtblInvoke("doesPWExpireToday", 101, zz_parameters);
    return;
  }

  /**
   * getMinimumPasswordLength. Returns the minimum password length required
   *
   * @return    The passwordLength
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMinimumPasswordLength  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMinimumPasswordLength", 102, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setPWWithErrorCode. 
   *
   * @param     userID The userID (in)
   * @param     oldPW The oldPW (in)
   * @param     newPW The newPW (in)
   * @param     changed The changed (out: use single element array)
   * @return    The pErrorCode
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int setPWWithErrorCode  (
              String userID,
              String oldPW,
              String newPW,
              boolean[] changed) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { userID, oldPW, newPW, changed, zz_retVal };
    vtblInvoke("setPWWithErrorCode", 103, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getStatusString. 
   *
   * @param     lNumStats The lNumStats (in)
   * @param     lReqType The lReqType (in)
   * @param     dataIn An unsigned byte (in)
   * @param     bstrStatus The bstrStatus (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getStatusString  (
              int lNumStats,
              int lReqType,
              byte[] dataIn,
              String[] bstrStatus) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(lNumStats), new Integer(lReqType), dataIn, bstrStatus, zz_retVal };
    vtblInvoke("getStatusString", 104, zz_parameters);
    return;
  }

  /**
   * setStatusString. 
   *
   * @param     lNumStats The lNumStats (in)
   * @param     lReqType The lReqType (in)
   * @param     bstrRequest2 The bstrRequest2 (in)
   * @param     dataOut An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setStatusString  (
              int lNumStats,
              int lReqType,
              String bstrRequest2,
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(lNumStats), new Integer(lReqType), bstrRequest2, dataOut, zz_retVal };
    vtblInvoke("setStatusString", 105, zz_parameters);
    return;
  }

  /**
   * openWin. Establishes an ACCPAC session using Windows Domain Logon info.
   *
   * @param     domain The domain (in)
   * @param     userIdentifier The userIdentifier (in)
   * @param     password The password (in)
   * @param     sid The sid (in)
   * @param     dataBase The dataBase (in)
   * @param     sessionDate The sessionDate (in)
   * @param     flags The flags (in)
   * @param     reserved The reserved (in)
   * @param     checkStatus The checkStatus (in)
   * @param     sessionInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void openWin  (
              String domain,
              String userIdentifier,
              String password,
              String sid,
              String dataBase,
              java.util.Date sessionDate,
              int flags,
              String reserved,
              boolean checkStatus,
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { domain, userIdentifier, password, sid, dataBase, sessionDate, new Integer(flags), reserved, new Boolean(checkStatus), sessionInfo, zz_retVal };
    vtblInvoke("openWin", 106, zz_parameters);
    return;
  }

  /**
   * getDateFileLastModified. Determine last time file was modified. bstrFilePath represents a DOS path on the server.
   *
   * @param     bstrFilePath The bstrFilePath (in)
   * @return    The pDate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getDateFileLastModified  (
              String bstrFilePath) throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { bstrFilePath, zz_retVal };
    vtblInvoke("getDateFileLastModified", 107, zz_parameters);
    return (java.util.Date)zz_retVal[0];
  }

  /**
   * sessionMacroAppend. Appends a line to a macro. Will NOT generate an OpenView call.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void sessionMacroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { cmdInString, zz_retVal };
    vtblInvoke("sessionMacroAppend", 108, zz_parameters);
    return;
  }

  /**
   * msgrInstalled. Determines if ACCPAC Messenger is installed'
   *
   * @return    The isOpened
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("msgrInstalled", 109, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getAccpacUserID. Returns the ACCPAC User ID given the Windows Domain and Windows User ID.
   *
   * @param     bstrDomain The bstrDomain (in)
   * @param     bstrWinUserID The bstrWinUserID (in)
   * @return    The pbstrUserID
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAccpacUserID  (
              String bstrDomain,
              String bstrWinUserID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { bstrDomain, bstrWinUserID, zz_retVal };
    vtblInvoke("getAccpacUserID", 110, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getMacroData. Internal. Do not use
   *
   * @return    The pData
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMacroData  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMacroData", 111, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getFlagData. 
   *
   * @param     lNumStats The lNumStats (in)
   * @param     lReqType The lReqType (in)
   * @param     dataIn An unsigned byte (in)
   * @param     dataOut An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFlagData  (
              int lNumStats,
              int lReqType,
              byte[] dataIn,
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(lNumStats), new Integer(lReqType), dataIn, dataOut, zz_retVal };
    vtblInvoke("getFlagData", 112, zz_parameters);
    return;
  }

  /**
   * setFlagData. 
   *
   * @param     lNumStats The lNumStats (in)
   * @param     lReqType The lReqType (in)
   * @param     dataIn An unsigned byte (in)
   * @param     dataOut An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFlagData  (
              int lNumStats,
              int lReqType,
              byte[] dataIn,
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(lNumStats), new Integer(lReqType), dataIn, dataOut, zz_retVal };
    vtblInvoke("setFlagData", 113, zz_parameters);
    return;
  }

  /**
   * createObjectHandle3. Creates a new object handle with the intent to launch another application specified by the object ID. Returns the new object handle and the actual class ID and codebase of the specified object ID to launch.
   *
   * @param     objectID The objectID (in)
   * @param     objectKey The objectKey (in)
   * @param     sExtra The sExtra (in)
   * @param     objectHandle The objectHandle (out: use single element array)
   * @param     cLSID The cLSID (out: use single element array)
   * @param     codebase The codebase (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createObjectHandle3  (
              String objectID,
              String objectKey,
              String sExtra,
              String[] objectHandle,
              String[] cLSID,
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { objectID, objectKey, sExtra, objectHandle, cLSID, codebase, zz_retVal };
    vtblInvoke("createObjectHandle3", 114, zz_parameters);
    return;
  }

  /**
   * getLegacyReturnCode. Gets the return code given to legacy ACCPAC applications.
   *
   * @param     pReturnCode The pReturnCode (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getLegacyReturnCode  (
              short[] pReturnCode) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pReturnCode, zz_retVal };
    vtblInvoke("getLegacyReturnCode", 115, zz_parameters);
    return;
  }

  /**
   * getASVersion. Gets AS version from currently open DB.
   *
   * @param     pstrVersion The pstrVersion (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getASVersion  (
              String[] pstrVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pstrVersion, zz_retVal };
    vtblInvoke("getASVersion", 116, zz_parameters);
    return;
  }

  /**
   * activateASCS. Activates AS and CS as required
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void activateASCS  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("activateASCS", 117, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("06d80057-a4ee-4ab9-905d-a603e068da93", com.interweave.plugin.a4comsv.IAccpacSvrSessionProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("init",
            new Class[] { Integer.TYPE, String.class, String.class, String.class, String.class, String.class, Boolean.TYPE, boolean[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("key", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectHandle", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("clientID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("checkStatus", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("isOpened", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("sessionInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("open",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, Integer.TYPE, String.class, Boolean.TYPE, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("reserved", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("checkStatus", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("close",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openDBLink",
            new Class[] { int.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("linkType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("flags", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("ppDBLink", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrDBLink.IID, com.interweave.plugin.a4comsv.IAccpacSvrDBLinkProxy.class) }),
        new com.linar.jintegra.MemberDesc("reportSelect",
            new Class[] { String.class, String.class, String.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrReportName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("printSettings", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("ppReport", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrReport.IID, com.interweave.plugin.a4comsv.IAccpacSvrReportProxy.class) }),
        new com.linar.jintegra.MemberDesc("rscGetString",
            new Class[] { String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("strID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProgramName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCurRateTypeDescription",
            new Class[] { String.class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rateTypeCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateTypeDescription", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProfiles",
            new Class[] { String[][].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("profileIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("profileDescs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProfileCustomizations",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("profileID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("uIKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("hiddenControls", 8, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("saveProfileCustomizations",
            new Class[] { String[].class, String.class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("profileIDs", 8, 67, 8, null, null), 
              new com.linar.jintegra.Param("uIKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("hiddenControls", 8, 67, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getUserCustomizations",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("uIKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("hiddenControls", 8, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createProfile",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("profileID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("profileDesc", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isOpened",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getUserID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getUserLanguage",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createObjectHandle",
            new Class[] { String.class, String.class, String[].class, String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectHandle", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("cLSID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("codebase", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getObjectKey",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pKey", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCompanyID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPrintSetup",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup.IID, com.interweave.plugin.a4comsv.IAccpacSvrPrintSetupProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCompanyName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSessionDate",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("sessionDate", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getHelpPath",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isRemote",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getInstalledReports",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("reports", 8, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProductSeries",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getDatabaseSeries",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getIniFileKey",
            new Class[] { String.class, String.class, String.class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("primaryKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("secondaryKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyData", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("keyFound", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getObjectCLSID",
            new Class[] { String.class, int.class, String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("distFileType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("cLSID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("codebase", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("validObjectID", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("checkSessionDate",
            new Class[] { boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("dateInFiscal", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodActive", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("checkHomeCurrency",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("currencyExists", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("checkReminders",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("remindersExist", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("checkRestartRecs",
            new Class[] { boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("restartRecsExist", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("isAdmin", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setPW",
            new Class[] { String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("oldPW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("newPW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("changed", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppVersion",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCodebase",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAccpacMeter",
            new Class[] { com.interweave.plugin.a4comsv.IAccpacSvrMeter[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrMeter.IID, com.interweave.plugin.a4comsv.IAccpacSvrMeterProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("macroRecordObject",
            new Class[] { Integer.TYPE, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("hWnd", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("macroPause",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("macroResume",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDependencies",
            new Class[] { int.class, String[][].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("codebaseType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("cLSIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("codebases", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDependenciesForLanguage",
            new Class[] { String.class, int.class, String[][].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("language", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("codebaseType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("cLSIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("codebases", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setLegacyReturnCode",
            new Class[] { Short.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("returnCode", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setHelpPath",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getHelpURL",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("checkStatus",
            new Class[] { boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cSActivated", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodActive", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("homeCurrencyExists", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("remindersExist", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("restartRecsExist", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("isAdmin", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSignonInfo",
            new Class[] { String[].class, String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("companyID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("companyName", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("zz_clone",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrSession[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("companyID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppSession", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrSession.IID, com.interweave.plugin.a4comsv.IAccpacSvrSessionProxy.class), 
              new com.linar.jintegra.Param("sessionInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("initClientSharedDataDirectory",
            new Class[] { boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pMultipleDirsSetup", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("pMutlipleDirs", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientSharedDataDirs",
            new Class[] { String[].class, String[].class, String[][].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("domain", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("user", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("dataNames", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("dataPaths", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setClientSharedDataDirectory",
            new Class[] { String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrPath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("bSignOn", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientSharedDataDirectory",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pSharedDataDirectory", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("orgsGetInfo",
            new Class[] { int[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("orgs", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errGetCount",
            new Class[] { int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errGetMessage",
            new Class[] { Integer.TYPE, String[].class, int[].class, String[].class, String[].class, String[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ulIndex", 19, 2, 8, null, null), 
              new com.linar.jintegra.Param("msg", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("priority", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("source", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("errCode", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("helpFile", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("helpID", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errPutMessage",
            new Class[] { String.class, int.class, Object.class, String.class, String.class, String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("msg", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("enumPriority", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("params", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("source", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("errCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("helpFile", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("lHelpID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errPutRscMessage",
            new Class[] { String.class, Integer.TYPE, int.class, Object.class, String.class, String.class, String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rscID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("enumPriority", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("params", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("source", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("errCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("helpFile", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("lHelpID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errClear",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("errGenerateFile",
            new Class[] { String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("errFile", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("muLockRsc",
            new Class[] { String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("resource", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("exclusive", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muUnlockRsc",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("resource", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muLockApp",
            new Class[] { String.class, String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("exclusive", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muUnlockApp",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muLockOrg",
            new Class[] { String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("exclusive", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muUnlockOrg",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muRegradeApp",
            new Class[] { String.class, String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("upgrade", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muRegradeOrg",
            new Class[] { String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("orgID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("upgrade", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("muTest",
            new Class[] { String.class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("resource", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("exclusive", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("locked", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curGetCurTable",
            new Class[] { String.class, String.class, String[].class, int[].class, int[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("description", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateMatch", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("operator", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("source", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curGetCurRate",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, int.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("curRateType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("rateInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curGetCurrency",
            new Class[] { String.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("currencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("currency", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curIsBlockCombination",
            new Class[] { String.class, String.class, java.util.Date.class, int.class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrency", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrency", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("isCombination", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curIsBlockMaster",
            new Class[] { String.class, java.util.Date.class, int.class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("isMaster", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("curIsBlockMember",
            new Class[] { String.class, java.util.Date.class, int.class, boolean[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("isMember", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("rate", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propGet",
            new Class[] { String.class, String.class, String.class, String.class, String.class, Object[].class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyword", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("varBuf", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("propType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propPut",
            new Class[] { String.class, String.class, String.class, String.class, String.class, Object.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyword", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("varBuf", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("lSize", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propClear",
            new Class[] { String.class, String.class, String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyword", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientDomainUser",
            new Class[] { String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pDomain", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pUser", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProgramsPathOnServer",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSharedDataPathOnServer",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getVersion",
            new Class[] { int[].class, int[].class, int[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("majorVersion", 16387, 2, 8, null, null), 
              new com.linar.jintegra.Param("minorVersion", 16387, 2, 8, null, null), 
              new com.linar.jintegra.Param("build", 16387, 2, 8, null, null), 
              new com.linar.jintegra.Param("revision", 16387, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("registerDesktopSession",
            new Class[] { String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("clientID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("unregisterDesktopSession",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppDependenciesForLanguage",
            new Class[] { String.class, String.class, String.class, String.class, int.class, String[][].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pgmName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("language", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("codebaseType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("cLSIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("codebases", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isEnforceAppVersion",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setEnforceAppVersion",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSystemHelpURL",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("helpURL", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("licenseStatus",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("upload",
            new Class[] { String.class, int.class, String.class, byte[].class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filename", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("root", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("subDir", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("data", 17, 67, 8, null, null), 
              new com.linar.jintegra.Param("more", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("download",
            new Class[] { int.class, String.class, String[].class, byte[][].class, boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("root", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("subPath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("filename", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("data", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("moreFile", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("moreData", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("reportSelect2",
            new Class[] { String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrReportName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppReport", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrReport.IID, com.interweave.plugin.a4comsv.IAccpacSvrReportProxy.class) }),
        new com.linar.jintegra.MemberDesc("checkStatus2",
            new Class[] { boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cSActivated", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodActive", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("homeCurrencyExists", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("remindersExist", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("restartRecsExist", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("isAdmin", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("warnPeriodLocked", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isServerMachine",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("doesPWExpireToday",
            new Class[] { String.class, String.class, boolean[].class, boolean[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("expiresToday", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("expiresInTwoWeeks", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("pDaysLeft", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMinimumPasswordLength",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("passwordLength", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setPWWithErrorCode",
            new Class[] { String.class, String.class, String.class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("oldPW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("newPW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("changed", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("pErrorCode", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getStatusString",
            new Class[] { Integer.TYPE, Integer.TYPE, byte[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataIn", 17, 67, 8, null, null), 
              new com.linar.jintegra.Param("bstrStatus", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setStatusString",
            new Class[] { Integer.TYPE, Integer.TYPE, String.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("bstrRequest2", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataOut", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openWin",
            new Class[] { String.class, String.class, String.class, String.class, String.class, java.util.Date.class, Integer.TYPE, String.class, Boolean.TYPE, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("domain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sid", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("reserved", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("checkStatus", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDateFileLastModified",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrFilePath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pDate", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("sessionMacroAppend",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cmdInString", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("msgrInstalled",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("isOpened", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAccpacUserID",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrDomain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("bstrWinUserID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pbstrUserID", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMacroData",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pData", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFlagData",
            new Class[] { Integer.TYPE, Integer.TYPE, byte[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataIn", 17, 3, 8, null, null), 
              new com.linar.jintegra.Param("dataOut", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setFlagData",
            new Class[] { Integer.TYPE, Integer.TYPE, byte[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataIn", 17, 3, 8, null, null), 
              new com.linar.jintegra.Param("dataOut", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createObjectHandle3",
            new Class[] { String.class, String.class, String.class, String[].class, String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sExtra", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectHandle", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("cLSID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("codebase", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getLegacyReturnCode",
            new Class[] { short[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pReturnCode", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getASVersion",
            new Class[] { String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pstrVersion", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("activateASCS",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
