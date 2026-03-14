package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSession'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC user session interface</B>'
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
public class IAccpacSessionProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacSession, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSession.class;

  public IAccpacSessionProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSession.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSessionProxy() {}

  public IAccpacSessionProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSession.IID);
  }

  protected IAccpacSessionProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSessionProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * @param     objectHandle The objectHandle (in)
   * @param     appID The appID (in)
   * @param     programName The programName (in)
   * @param     appVersion The appVersion (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  (
              String objectHandle,
              String appID,
              String programName,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { objectHandle, appID, programName, appVersion, zz_retVal };
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
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void open  (
              String userIdentifier,
              String password,
              String dataBase,
              java.util.Date sessionDate,
              int flags,
              String reserved) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userIdentifier, password, dataBase, sessionDate, new Integer(flags), reserved, zz_retVal };
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
   * @param     linkType A com.interweave.plugin.a4wcomex.tagDBLinkTypeEnum constant (in)
   * @param     flags A com.interweave.plugin.a4wcomex.tagDBLinkFlagsEnum constant (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacDBLink
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacDBLink openDBLink  (
              int linkType,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacDBLink zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(linkType), new Integer(flags), zz_retVal };
    vtblInvoke("openDBLink", 10, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacDBLink)zz_retVal[0];
  }

  /**
   * getErrors. Returns an AccpacErrors collection object that contains all the view-related errors.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacErrors
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacErrors getErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacErrors zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getErrors", 11, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacErrors)zz_retVal[0];
  }

  /**
   * getOrganizations. Returns an AccpacOrganizations collection object that contains information on company databases installed in the system.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganizations
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganizations getOrganizations  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacOrganizations zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getOrganizations", 12, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacOrganizations)zz_retVal[0];
  }

  /**
   * getAccpacProperty. Returns an AccpacProperty object that allows access to ACCPAC properties.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacProperty
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacProperty getAccpacProperty  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacProperty zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAccpacProperty", 13, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacProperty)zz_retVal[0];
  }

  /**
   * reportSelect. Creates an AccpacReport object that represents the selected report.
   *
   * @param     bstrReportName The bstrReportName (in)
   * @param     menuID The menuID (in)
   * @param     programID The programID (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacReport
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacReport reportSelect  (
              String bstrReportName,
              String menuID,
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacReport zz_retVal[] = { null };
    Object zz_parameters[] = { bstrReportName, menuID, programID, zz_retVal };
    vtblInvoke("reportSelect", 14, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacReport)zz_retVal[0];
  }

  /**
   * ieTemplatePerform. TO BE REMOVED!!!
   *
   * @param     operation A com.interweave.plugin.a4wcomex.tagOperationType constant (in)
   * @param     object The object (in)
   * @param     templateFile The templateFile (in)
   * @param     stubParam The stubParam (in)
   * @param     choice The choice (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int ieTemplatePerform  (
              int operation,
              String object,
              String templateFile,
              int stubParam,
              int choice) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { new Integer(operation), object, templateFile, new Integer(stubParam), new Integer(choice), zz_retVal };
    vtblInvoke("ieTemplatePerform", 15, zz_parameters);
    return zz_retVal[0];
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
    vtblInvoke("rscGetString", 16, zz_parameters);
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
    vtblInvoke("getAppID", 17, zz_parameters);
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
    vtblInvoke("getProgramName", 18, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getCurrency. Retrieves details of the specified currency code. Returns the corresponding AccpacCurrency object if the currency code is valid.
   *
   * @param     currencyCode The currencyCode (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrency
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCurrency getCurrency  (
              String currencyCode) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCurrency zz_retVal[] = { null };
    Object zz_parameters[] = { currencyCode, zz_retVal };
    vtblInvoke("getCurrency", 19, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCurrency)zz_retVal[0];
  }

  /**
   * getCurrencyRate. Retrieves exchange rate information between the specified home and source currency codes. Returns the corresponding AccpacCurrencyRate object.
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyRate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCurrencyRate getCurrencyRate  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCurrencyRate zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrencyCode, rateType, sourceCurrencyCode, date, zz_retVal };
    vtblInvoke("getCurrencyRate", 20, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCurrencyRate)zz_retVal[0];
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
    vtblInvoke("getCurRateTypeDescription", 21, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getCurrencyTable. Returns the AccpacCurrencyTable object corresponding to the specified parameter values.
   *
   * @param     curCode The curCode (in)
   * @param     rateType The rateType (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyTable
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCurrencyTable getCurrencyTable  (
              String curCode,
              String rateType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCurrencyTable zz_retVal[] = { null };
    Object zz_parameters[] = { curCode, rateType, zz_retVal };
    vtblInvoke("getCurrencyTable", 22, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCurrencyTable)zz_retVal[0];
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
    vtblInvoke("getProfiles", 23, zz_parameters);
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
    vtblInvoke("getProfileCustomizations", 24, zz_parameters);
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
    vtblInvoke("saveProfileCustomizations", 25, zz_parameters);
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
    vtblInvoke("getUserCustomizations", 26, zz_parameters);
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
    vtblInvoke("createProfile", 27, zz_parameters);
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
    vtblInvoke("isOpened", 28, zz_parameters);
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
    vtblInvoke("getUserID", 29, zz_parameters);
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
    vtblInvoke("getUserLanguage", 30, zz_parameters);
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
    vtblInvoke("createObjectHandle", 31, zz_parameters);
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
    vtblInvoke("getObjectKey", 32, zz_parameters);
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
    vtblInvoke("getCompanyID", 33, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getPrintSetup. Returns an AccpacPrintSetup object that represents the print setup for the specified menu and program ID, for the current user.
   *
   * @param     menuID The menuID (in)
   * @param     programID The programID (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacPrintSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacPrintSetup getPrintSetup  (
              String menuID,
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacPrintSetup zz_retVal[] = { null };
    Object zz_parameters[] = { menuID, programID, zz_retVal };
    vtblInvoke("getPrintSetup", 34, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacPrintSetup)zz_retVal[0];
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
    vtblInvoke("getCompanyName", 35, zz_parameters);
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
    vtblInvoke("getSessionDate", 36, zz_parameters);
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
    vtblInvoke("getHelpPath", 37, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isRemote. Returns whether or not the current session is accessing a remote or out-of-process ACCPAC server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemote  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRemote", 38, zz_parameters);
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
    vtblInvoke("getInstalledReports", 39, zz_parameters);
    return (String[])zz_retVal[0];
  }

  /**
   * getCurrencyRateComposite. Retrieves the composite rate between the given source and home currencies. If the currencies are non-block currencies, the call functions the same as GetCurrencyRate. Returns the corresponding AccpacCurrencyRate object.
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyRate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCurrencyRate getCurrencyRateComposite  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCurrencyRate zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrencyCode, rateType, sourceCurrencyCode, date, zz_retVal };
    vtblInvoke("getCurrencyRateComposite", 40, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCurrencyRate)zz_retVal[0];
  }

  /**
   * getCurrencyRateFloating. Retrieves the floating rate between the given source and home currencies. If the currencies are non-block currencies, the call functions the same as GetCurrencyRate. Returns the corresponding AccpacCurrencyRate object.
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyRate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCurrencyRate getCurrencyRateFloating  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCurrencyRate zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrencyCode, rateType, sourceCurrencyCode, date, zz_retVal };
    vtblInvoke("getCurrencyRateFloating", 41, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCurrencyRate)zz_retVal[0];
  }

  /**
   * getProductSeries. Returns the currently installed ACCPAC product series.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagProductSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getProductSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProductSeries", 42, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getDatabaseSeries. Returns the currently installed ACCPAC database series.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDatabaseSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDatabaseSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDatabaseSeries", 43, zz_parameters);
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
    vtblInvoke("getIniFileKey", 44, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getObjectCLSID. Retrieves the CLSID and distribution file for the specified object ID. Returns whether the object ID indicates a valid ActiveX UI object or not.
   *
   * @param     objectID The objectID (in)
   * @param     distFileType A com.interweave.plugin.a4wcomex.tagDistFileType constant (in)
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
    vtblInvoke("getObjectCLSID", 45, zz_parameters);
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
    vtblInvoke("checkSessionDate", 46, zz_parameters);
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
    vtblInvoke("checkHomeCurrency", 47, zz_parameters);
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
    vtblInvoke("checkReminders", 48, zz_parameters);
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
    vtblInvoke("checkRestartRecs", 49, zz_parameters);
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
    vtblInvoke("setPW", 50, zz_parameters);
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
    vtblInvoke("getAppVersion", 51, zz_parameters);
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
    vtblInvoke("getCodebase", 52, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getAccpacMeter. Returns the AccpacMeter object for session.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacMeter (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAccpacMeter  (
              com.interweave.plugin.a4wcomex.IAccpacMeter[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("getAccpacMeter", 53, zz_parameters);
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
    vtblInvoke("macroRecordObject", 54, zz_parameters);
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
    vtblInvoke("macroPause", 55, zz_parameters);
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
    vtblInvoke("macroResume", 56, zz_parameters);
    return;
  }

  /**
   * getDependencies. Returns a list of dependent classes based on the current Program Name and the user's language setting
   *
   * @param     codebaseType A com.interweave.plugin.a4wcomex.tagDistFileType constant (in)
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
    vtblInvoke("getDependencies", 57, zz_parameters);
    return;
  }

  /**
   * getApplication. Returns the Application object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getApplication  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacSession zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getApplication", 58, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacSession)zz_retVal[0];
  }

  /**
   * getParent. Returns the parent object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getParent  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacSession zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getParent", 59, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacSession)zz_retVal[0];
  }

  /**
   * getDependenciesForLanguage. Returns a list of dependent classes based on the current Program Name and the supplied language code.
   *
   * @param     language The language (in)
   * @param     codebaseType A com.interweave.plugin.a4wcomex.tagDistFileType constant (in)
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
    vtblInvoke("getDependenciesForLanguage", 60, zz_parameters);
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
    vtblInvoke("setLegacyReturnCode", 61, zz_parameters);
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
    vtblInvoke("setHelpPath", 62, zz_parameters);
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
    vtblInvoke("getHelpURL", 63, zz_parameters);
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
    vtblInvoke("checkStatus", 64, zz_parameters);
    return;
  }

  /**
   * getMultiuser. Returns the AccpacMultiuser object that provides facilities to control multi-user access.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacMultiuser
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacMultiuser getMultiuser  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacMultiuser zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMultiuser", 65, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacMultiuser)zz_retVal[0];
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
    vtblInvoke("getSignonInfo", 66, zz_parameters);
    return;
  }

  /**
   * zz_clone. Creates a new AccpacSession object with the same signon information, but attaching to the specified company.
   *
   * @param     companyID The companyID (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession zz_clone  (
              String companyID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacSession zz_retVal[] = { null };
    Object zz_parameters[] = { companyID, zz_retVal };
    vtblInvoke("zz_clone", 67, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacSession)zz_retVal[0];
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
    vtblInvoke("getProgramsPathOnServer", 68, zz_parameters);
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
    vtblInvoke("getSharedDataPathOnServer", 69, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getVersion. Returns version information for the Server
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
    vtblInvoke("getVersion", 70, zz_parameters);
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
    vtblInvoke("initClientSharedDataDirectory", 71, zz_parameters);
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
    vtblInvoke("getClientSharedDataDirs", 72, zz_parameters);
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
    vtblInvoke("setClientSharedDataDirectory", 73, zz_parameters);
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
    vtblInvoke("getClientSharedDataDirectory", 74, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * remoteConnect. Connects the session to a remote ACCPAC server using the supplied authentication credentials.  Must be called before Init.
   *
   * @param     serverName The serverName (in)
   * @param     userID The userID (in)
   * @param     password The password (in)
   * @param     domain The domain (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void remoteConnect  (
              String serverName,
              String userID,
              String password,
              String domain) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { serverName, userID, password, domain, zz_retVal };
    vtblInvoke("remoteConnect", 75, zz_parameters);
    return;
  }

  /**
   * svrDetach. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void svrDetach  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("svrDetach", 76, zz_parameters);
    return;
  }

  /**
   * svrAttach. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void svrAttach  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("svrAttach", 77, zz_parameters);
    return;
  }

  /**
   * getServerName. Returns the name of the server of a remote session. Empty is session if not remote.
   *
   * @return    The name
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getServerName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getServerName", 78, zz_parameters);
    return (String)zz_retVal[0];
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
    vtblInvoke("getClientDomainUser", 79, zz_parameters);
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
    vtblInvoke("registerDesktopSession", 80, zz_parameters);
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
    vtblInvoke("unregisterDesktopSession", 81, zz_parameters);
    return;
  }

  /**
   * isServerMachine. Returns whether or not the application is running on the same machine as the ACCPAC server, if IsRemote is True. If IsRemote is False, this property always returns True.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isServerMachine  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isServerMachine", 82, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getAppDependenciesForLanguage. Returns a list of dependent classes based on the supplied application ID, version, program name and language code.
   *
   * @param     appID The appID (in)
   * @param     pgmName The pgmName (in)
   * @param     appVersion The appVersion (in)
   * @param     language The language (in)
   * @param     codebaseType A com.interweave.plugin.a4wcomex.tagDistFileType constant (in)
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
    vtblInvoke("getAppDependenciesForLanguage", 83, zz_parameters);
    return;
  }

  /**
   * isEnforceAppVersion. Returns/sets whether OpenDBLink enforces a match of the caller's application version and the activated application version of the company database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEnforceAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isEnforceAppVersion", 84, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setEnforceAppVersion. Returns/sets whether OpenDBLink enforces a match of the caller's application version and the activated application version of the company database.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setEnforceAppVersion  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setEnforceAppVersion", 85, zz_parameters);
    return;
  }

  /**
   * getSystemHelpURL. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The systemHelpURL
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSystemHelpURL", 86, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isCheckStatusOnOpen. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCheckStatusOnOpen  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isCheckStatusOnOpen", 87, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setCheckStatusOnOpen. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCheckStatusOnOpen  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setCheckStatusOnOpen", 88, zz_parameters);
    return;
  }

  /**
   * getDBLink. private method
   *
   * @param     linkType A com.interweave.plugin.a4wcomex.tagDBLinkTypeEnum constant (in)
   * @param     flags A com.interweave.plugin.a4wcomex.tagDBLinkFlagsEnum constant (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacDBLink
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacDBLink getDBLink  (
              int linkType,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacDBLink zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(linkType), new Integer(flags), zz_retVal };
    vtblInvoke("getDBLink", 89, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacDBLink)zz_retVal[0];
  }

  /**
   * checkSessionDate2. Retrieves whether the logged-in session date falls within a fiscal period, whether that period is active and open (not locked), and whether a warning should be displayed if the period is locked.
   *
   * @param     dateInFiscal The dateInFiscal (out: use single element array)
   * @param     periodActive The periodActive (out: use single element array)
   * @param     periodOpen The periodOpen (out: use single element array)
   * @param     warnPeriodLocked The warnPeriodLocked (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void checkSessionDate2  (
              boolean[] dateInFiscal,
              boolean[] periodActive,
              boolean[] periodOpen,
              boolean[] warnPeriodLocked) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { dateInFiscal, periodActive, periodOpen, warnPeriodLocked, zz_retVal };
    vtblInvoke("checkSessionDate2", 90, zz_parameters);
    return;
  }

  /**
   * licenseStatus. Checks whether the specified license file is installed in the system. AppVersion can be passed an empty string in which case, the current System Manager version will be assumed.
   *
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @return    A com.interweave.plugin.a4wcomex.tagLicenseStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int licenseStatus  (
              String appID,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { appID, appVersion, zz_retVal };
    vtblInvoke("licenseStatus", 91, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getCurRateTypeDescription2. Retrieves the currency rate type description of the specified rate type code. Returns whether the rate type code exists or not. Script languages should use this function.
   *
   * @param     rateTypeCode The rateTypeCode (in)
   * @param     rateTypeDescription A Variant (out: use single element array)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getCurRateTypeDescription2  (
              String rateTypeCode,
              Object[] rateTypeDescription) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { rateTypeCode, rateTypeDescription, zz_retVal };
    vtblInvoke("getCurRateTypeDescription2", 92, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getIniFileKey2. Retrieves the specified key from the application initialization file. Returns whether the key is found or not. Script languages should use this function.
   *
   * @param     appID The appID (in)
   * @param     primaryKey The primaryKey (in)
   * @param     secondaryKey The secondaryKey (in)
   * @param     keyData A Variant (out: use single element array)
   * @return    The keyFound
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getIniFileKey2  (
              String appID,
              String primaryKey,
              String secondaryKey,
              Object[] keyData) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { appID, primaryKey, secondaryKey, keyData, zz_retVal };
    vtblInvoke("getIniFileKey2", 93, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * transferFiles. Tranfers files between the server and the client. Path can be a local path if location is specified as client side. If location is on server, path must be a relative path off the location.
   *
   * @param     sourceLocation A com.interweave.plugin.a4wcomex.tagFileLocation constant (in)
   * @param     sourcePath The sourcePath (in)
   * @param     destLocation A com.interweave.plugin.a4wcomex.tagFileLocation constant (in)
   * @param     destPath The destPath (in)
   * @return    The count
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transferFiles  (
              int sourceLocation,
              String sourcePath,
              int destLocation,
              String destPath) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { new Integer(sourceLocation), sourcePath, new Integer(destLocation), destPath, zz_retVal };
    vtblInvoke("transferFiles", 94, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getVersion2. Returns version information for the Server. Script programs should use this method.
   *
   * @return    The version
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getVersion2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getVersion2", 95, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getObjectCLSID2. Retrieves the CLSID and codebase of the application file for the specified object ID. Returns a string in the form <CLSID>@<Codebase>. Returns an empty string if ObjectID is not valid. Script programs should use this method.
   *
   * @param     objectID The objectID (in)
   * @param     distFileType A com.interweave.plugin.a4wcomex.tagDistFileType constant (in)
   * @return    The cLSIDCodebase
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectCLSID2  (
              String objectID,
              int distFileType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { objectID, new Integer(distFileType), zz_retVal };
    vtblInvoke("getObjectCLSID2", 96, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * createObjectHandle2. Creates a new object handle with the intent to launch another application specified by the object ID. Returns the new object handle. Script programs should use this method.
   *
   * @param     objectID The objectID (in)
   * @param     objectKey The objectKey (in)
   * @return    The objectHandle
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String createObjectHandle2  (
              String objectID,
              String objectKey) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { objectID, objectKey, zz_retVal };
    vtblInvoke("createObjectHandle2", 97, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * doesPWExpireToday. Returns whether user password is expiring today or within two weeks.
   *
   * @param     userID The userID (in)
   * @param     pW The pW (in)
   * @param     expiresToday The expiresToday (out: use single element array)
   * @param     expiresInTwoWeeks The expiresInTwoWeeks (out: use single element array)
   * @param     daysLeft The daysLeft (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void doesPWExpireToday  (
              String userID,
              String pW,
              boolean[] expiresToday,
              boolean[] expiresInTwoWeeks,
              int[] daysLeft) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userID, pW, expiresToday, expiresInTwoWeeks, daysLeft, zz_retVal };
    vtblInvoke("doesPWExpireToday", 98, zz_parameters);
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
    vtblInvoke("getMinimumPasswordLength", 99, zz_parameters);
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
    vtblInvoke("getStatusString", 100, zz_parameters);
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
    vtblInvoke("setStatusString", 101, zz_parameters);
    return;
  }

  /**
   * isRightToLeftLanguage. Returns whether the current user language is a right to left language.
   *
   * @return    The rightToLeft
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRightToLeftLanguage  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRightToLeftLanguage", 102, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * openWin. Establishes an ACCPAC session using Windows Authentication. If a blank domain and user identifier is passed in, the currently logged on Windows user will be authenticated.
   *
   * @param     domain The domain (in)
   * @param     userIdentifier The userIdentifier (in)
   * @param     password The password (in)
   * @param     dataBase The dataBase (in)
   * @param     sessionDate The sessionDate (in)
   * @param     flags The flags (in)
   * @param     reserved The reserved (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void openWin  (
              String domain,
              String userIdentifier,
              String password,
              String dataBase,
              java.util.Date sessionDate,
              int flags,
              String reserved) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { domain, userIdentifier, password, dataBase, sessionDate, new Integer(flags), reserved, zz_retVal };
    vtblInvoke("openWin", 103, zz_parameters);
    return;
  }

  /**
   * isWinUserLoggedOn. Determines if the given Windows user is the user that is currently logged on.
   *
   * @param     domain The domain (in)
   * @param     winUserID The winUserID (in)
   * @param     pVal The pVal (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void isWinUserLoggedOn  (
              String domain,
              String winUserID,
              boolean[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { domain, winUserID, pVal, zz_retVal };
    vtblInvoke("isWinUserLoggedOn", 104, zz_parameters);
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
    vtblInvoke("getDateFileLastModified", 105, zz_parameters);
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
    vtblInvoke("sessionMacroAppend", 106, zz_parameters);
    return;
  }

  /**
   * msgrInstalled. Determines if ACCPAC Messenger is installed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("msgrInstalled", 107, zz_parameters);
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
    vtblInvoke("getAccpacUserID", 108, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getMacroData. Internal
   *
   * @return    The dataVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMacroData  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMacroData", 109, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * emailConnect. Connects to MAPI.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean emailConnect  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("emailConnect", 110, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * emailDisconnect. Disconnects from MAPI.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void emailDisconnect  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("emailDisconnect", 111, zz_parameters);
    return;
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
   * getASVersion. Gets AS version from system link of currently open DB.
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

  /**
   * checkStatusEx. this CheckStatus re-investigates, rather than reporting current values.
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
  public void checkStatusEx  (
              boolean[] cSActivated,
              boolean[] dateInFiscal,
              boolean[] periodActive,
              boolean[] homeCurrencyExists,
              boolean[] remindersExist,
              boolean[] restartRecsExist,
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { cSActivated, dateInFiscal, periodActive, homeCurrencyExists, remindersExist, restartRecsExist, isAdmin, zz_retVal };
    vtblInvoke("checkStatusEx", 118, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("b3b13603-a675-11d2-9b95-00104b71eb3f", com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("init",
            new Class[] { String.class, String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectHandle", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("open",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, Integer.TYPE, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("reserved", 8, 2, 8, null, null), 
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
              new com.linar.jintegra.Param("ppDBLink", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacDBLink.IID, com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy.class) }),
        new com.linar.jintegra.MemberDesc("getErrors",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacErrors.IID, com.interweave.plugin.a4wcomex.IAccpacErrorsProxy.class) }),
        new com.linar.jintegra.MemberDesc("getOrganizations",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacOrganizations.IID, com.interweave.plugin.a4wcomex.IAccpacOrganizationsProxy.class) }),
        new com.linar.jintegra.MemberDesc("getAccpacProperty",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppAccpacProperty", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacProperty.IID, com.interweave.plugin.a4wcomex.IAccpacPropertyProxy.class) }),
        new com.linar.jintegra.MemberDesc("reportSelect",
            new Class[] { String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrReportName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppReport", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacReport.IID, com.interweave.plugin.a4wcomex.IAccpacReportProxy.class) }),
        new com.linar.jintegra.MemberDesc("ieTemplatePerform",
            new Class[] { int.class, String.class, String.class, Integer.TYPE, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("operation", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("object", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("templateFile", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("stubParam", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("choice", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
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
        new com.linar.jintegra.MemberDesc("getCurrency",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("currencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppIAccpacCurrency", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCurrency.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrencyRate",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppIAccpacCurrencyRate", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCurrencyRate.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyRateProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurRateTypeDescription",
            new Class[] { String.class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rateTypeCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateTypeDescription", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCurrencyTable",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppIAccpacCurrencyTable", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCurrencyTable.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyTableProxy.class) }),
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
              new com.linar.jintegra.Param("ppVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacPrintSetup.IID, com.interweave.plugin.a4wcomex.IAccpacPrintSetupProxy.class) }),
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
        new com.linar.jintegra.MemberDesc("getCurrencyRateComposite",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppCurrencyRate", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCurrencyRate.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyRateProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrencyRateFloating",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppCurrencyRate", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCurrencyRate.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyRateProxy.class) }),
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
            new Class[] { com.interweave.plugin.a4wcomex.IAccpacMeter[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4wcomex.IAccpacMeter.IID, com.interweave.plugin.a4wcomex.IAccpacMeterProxy.class), 
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
        new com.linar.jintegra.MemberDesc("getApplication",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppRet", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacSession.IID, com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class) }),
        new com.linar.jintegra.MemberDesc("getParent",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppRet", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacSession.IID, com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class) }),
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
        new com.linar.jintegra.MemberDesc("getMultiuser",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppMultiuser", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacMultiuser.IID, com.interweave.plugin.a4wcomex.IAccpacMultiuserProxy.class) }),
        new com.linar.jintegra.MemberDesc("getSignonInfo",
            new Class[] { String[].class, String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("companyID", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("companyName", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("zz_clone",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("companyID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppSession", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacSession.IID, com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class) }),
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
        new com.linar.jintegra.MemberDesc("remoteConnect",
            new Class[] { String.class, String.class, String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("serverName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("userID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("domain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("svrDetach",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("svrAttach",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getServerName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("name", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientDomainUser",
            new Class[] { String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pDomain", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pUser", 16392, 4, 8, null, null), 
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
        new com.linar.jintegra.MemberDesc("isServerMachine",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
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
              new com.linar.jintegra.Param("systemHelpURL", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isCheckStatusOnOpen",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setCheckStatusOnOpen",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDBLink",
            new Class[] { int.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("linkType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("flags", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("ppDBLink", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacDBLink.IID, com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy.class) }),
        new com.linar.jintegra.MemberDesc("checkSessionDate2",
            new Class[] { boolean[].class, boolean[].class, boolean[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("dateInFiscal", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodActive", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("warnPeriodLocked", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("licenseStatus",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("status", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getCurRateTypeDescription2",
            new Class[] { String.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rateTypeCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateTypeDescription", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getIniFileKey2",
            new Class[] { String.class, String.class, String.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("primaryKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("secondaryKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyData", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("keyFound", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transferFiles",
            new Class[] { int.class, String.class, int.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("sourceLocation", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("sourcePath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("destLocation", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("destPath", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("count", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getVersion2",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("version", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getObjectCLSID2",
            new Class[] { String.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("distFileType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("cLSIDCodebase", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createObjectHandle2",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectKey", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("objectHandle", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("doesPWExpireToday",
            new Class[] { String.class, String.class, boolean[].class, boolean[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pW", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("expiresToday", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("expiresInTwoWeeks", 16395, 4, 8, null, null), 
              new com.linar.jintegra.Param("daysLeft", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMinimumPasswordLength",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("passwordLength", 3, 20, 8, null, null) }),
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
        new com.linar.jintegra.MemberDesc("isRightToLeftLanguage",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rightToLeft", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openWin",
            new Class[] { String.class, String.class, String.class, String.class, java.util.Date.class, Integer.TYPE, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("domain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("reserved", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isWinUserLoggedOn",
            new Class[] { String.class, String.class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("domain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("winUserID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16395, 4, 8, null, null), 
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
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAccpacUserID",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrDomain", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("bstrWinUserID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pbstrUserID", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMacroData",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("dataVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("emailConnect",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("emailDisconnect",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFlagData",
            new Class[] { Integer.TYPE, Integer.TYPE, byte[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataIn", 17, 67, 8, null, null), 
              new com.linar.jintegra.Param("dataOut", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setFlagData",
            new Class[] { Integer.TYPE, Integer.TYPE, byte[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lNumStats", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("lReqType", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataIn", 17, 67, 8, null, null), 
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
        new com.linar.jintegra.MemberDesc("checkStatusEx",
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
});  }
}
