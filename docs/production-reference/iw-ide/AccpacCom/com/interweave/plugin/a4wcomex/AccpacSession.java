package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSession'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC user session class</B>'
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
public class AccpacSession implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacSession {

  private static final String CLSID = "b3b13604-a675-11d2-9b95-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacSessionProxy d_IAccpacSessionProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacSession interface */
  public com.interweave.plugin.a4wcomex.IAccpacSession getAsIAccpacSession() { return d_IAccpacSessionProxy; }

  /** Compare this object with another */
  public boolean equals(Object o) { 
	if(java.beans.Beans.isDesignTime()) return super.equals(o);
	else return getJintegraDispatch() == null ? false : getJintegraDispatch().equals(o);
  }


  /** the hashcode for this object */
  public int hashCode() { return getJintegraDispatch() == null ? 0 : getJintegraDispatch().hashCode(); }

   /**
   * getActiveObject. Get a reference to a running instance of this class on the current machine using native code.
   *                  <B>Uses native code (See GetActiveObject() in MS doc) and thus can only be used on MS Windows</B>
   *
   * @return    A reference to the running object.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSession getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSession(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSession bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSession(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSessionProxy; }

  /**
   * Constructs a AccpacSession on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSession() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSession on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSession(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSessionProxy = new com.interweave.plugin.a4wcomex.IAccpacSessionProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSession using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSession(Object obj) throws java.io.IOException {
    d_IAccpacSessionProxy = new com.interweave.plugin.a4wcomex.IAccpacSessionProxy(obj);
  }

  /**
   * Release a AccpacSession.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSessionProxy);
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
    try {
      return d_IAccpacSessionProxy.getPropertyByName(name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs A parameter used when getting the proxy.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getPropertyByName(name, rhs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @param     parameters One element for each parameter. Use primitive type wrappers.
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacSessionProxy.invokeMethodByName(name, parameters);
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacSessionProxy.invokeMethodByName(name, new Object[]{});
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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.init(objectHandle,appID,programName,appVersion);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String reserved) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.open(userIdentifier,password,dataBase,sessionDate,flags,reserved);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * close. Closes the session.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.openDBLink(linkType,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getErrors. Returns an AccpacErrors collection object that contains all the view-related errors.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacErrors
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacErrors getErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getErrors();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getOrganizations. Returns an AccpacOrganizations collection object that contains information on company databases installed in the system.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganizations
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganizations getOrganizations  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getOrganizations();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAccpacProperty. Returns an AccpacProperty object that allows access to ACCPAC properties.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacProperty
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacProperty getAccpacProperty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getAccpacProperty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.reportSelect(bstrReportName,menuID,programID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int choice) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.ieTemplatePerform(operation,object,templateFile,stubParam,choice);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int strID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.rscGetString(appID,strID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAppID. Returns the application ID passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getAppID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProgramName. Returns the program name passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getProgramName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String currencyCode) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurrency(currencyCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurrencyRate(homeCurrencyCode,rateType,sourceCurrencyCode,date);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] rateTypeDescription) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurRateTypeDescription(rateTypeCode,rateTypeDescription);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String rateType) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurrencyTable(curCode,rateType);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] profileDescs) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getProfiles(profileIDs,profileDescs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getProfileCustomizations(profileID,uIKey);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] hiddenControls) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.saveProfileCustomizations(profileIDs,uIKey,hiddenControls);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getUserCustomizations(uIKey);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String profileDesc) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.createProfile(profileID,profileDesc);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isOpened. Returns whether the session object is open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isOpened  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isOpened();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getUserID. Returns the user ID of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getUserID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getUserLanguage. Returns the language code of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserLanguage  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getUserLanguage();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.createObjectHandle(objectID,objectKey,objectHandle,cLSID,codebase);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getObjectKey. Returns the object key passed in by the caller application.
   *
   * @return    The pKey
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectKey  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getObjectKey();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompanyID. Returns the company ID of the current logged-in company. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCompanyID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getPrintSetup(menuID,programID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompanyName. Returns the name of the current logged-in company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCompanyName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSessionDate. Returns the session date used to open the session.
   *
   * @return    The sessionDate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getSessionDate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getSessionDate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpPath  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getHelpPath();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRemote. Returns whether or not the current session is accessing a remote or out-of-process ACCPAC server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemote  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isRemote();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getInstalledReports(appID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurrencyRateComposite(homeCurrencyCode,rateType,sourceCurrencyCode,date);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurrencyRateFloating(homeCurrencyCode,rateType,sourceCurrencyCode,date);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProductSeries. Returns the currently installed ACCPAC product series.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagProductSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getProductSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getProductSeries();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDatabaseSeries. Returns the currently installed ACCPAC database series.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDatabaseSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDatabaseSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getDatabaseSeries();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] keyData) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getIniFileKey(appID,primaryKey,secondaryKey,keyData);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getObjectCLSID(objectID,distFileType,cLSID,codebase);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] periodActive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.checkSessionDate(dateInFiscal,periodActive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * checkHomeCurrency. Returns whether or not the company's home currency exists in the currency table.
   *
   * @return    The currencyExists
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.checkHomeCurrency();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * checkReminders. Returns whether or not there are active reminders for the logged-in user.
   *
   * @return    The remindersExist
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkReminders  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.checkReminders();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.checkRestartRecs(restartRecsExist,isAdmin);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String newPW) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.setPW(userID,oldPW,newPW);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAppVersion. Returns the version of the application to which the UI belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getAppVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCodebase. Returns the base path to the all the applications' CAB files.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCodebase  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCodebase();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAccpacMeter. Returns the AccpacMeter object for session.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacMeter (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAccpacMeter  (
              com.interweave.plugin.a4wcomex.IAccpacMeter[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getAccpacMeter(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String objectName) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.macroRecordObject(hWnd,objectName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * macroPause. Pauses macro recording. Generally macroPause and macroResume are called as a pair.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroPause  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.macroPause();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * macroResume. Resumes macro recording. macroResume must follow macroPause.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroResume  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.macroResume();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getDependencies(codebaseType,cLSIDs,codebases);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getApplication. Returns the Application object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getApplication  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getApplication();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getParent. Returns the parent object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getParent  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getParent();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getDependenciesForLanguage(language,codebaseType,cLSIDs,codebases);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setLegacyReturnCode. Sets the return code given to legacy ACCPAC applications.
   *
   * @param     returnCode The returnCode (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setLegacyReturnCode  (
              short returnCode) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setLegacyReturnCode(returnCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHelpPath  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setHelpPath(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getHelpURL. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getHelpURL();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.checkStatus(cSActivated,dateInFiscal,periodActive,homeCurrencyExists,remindersExist,restartRecsExist,isAdmin);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMultiuser. Returns the AccpacMultiuser object that provides facilities to control multi-user access.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacMultiuser
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacMultiuser getMultiuser  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getMultiuser();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] companyName) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getSignonInfo(userID,companyID,companyName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String companyID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.zz_clone(companyID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProgramsPathOnServer. The DOS path to ACCPAC programs directory on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramsPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getProgramsPathOnServer();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSharedDataPathOnServer. The DOS path to ACCPAC shared data on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSharedDataPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getSharedDataPathOnServer();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] revision) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getVersion(majorVersion,minorVersion,build,revision);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] pMutlipleDirs) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.initClientSharedDataDirectory(pMultipleDirsSetup,pMutlipleDirs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] dataPaths) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getClientSharedDataDirs(domain,user,dataNames,dataPaths);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean bSignOn) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setClientSharedDataDirectory(bstrPath,bSignOn);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getClientSharedDataDirectory. private method
   *
   * @return    The pSharedDataDirectory
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientSharedDataDirectory  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getClientSharedDataDirectory();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String domain) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.remoteConnect(serverName,userID,password,domain);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * svrDetach. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void svrDetach  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.svrDetach();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * svrAttach. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void svrAttach  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.svrAttach();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getServerName. Returns the name of the server of a remote session. Empty is session if not remote.
   *
   * @return    The name
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getServerName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getServerName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] pUser) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getClientDomainUser(pDomain,pUser);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String clientID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.registerDesktopSession(userIdentifier,dataBase,clientID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unregisterDesktopSession. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unregisterDesktopSession  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.unregisterDesktopSession();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isServerMachine. Returns whether or not the application is running on the same machine as the ACCPAC server, if IsRemote is True. If IsRemote is False, this property always returns True.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isServerMachine  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isServerMachine();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getAppDependenciesForLanguage(appID,pgmName,appVersion,language,codebaseType,cLSIDs,codebases);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isEnforceAppVersion. Returns/sets whether OpenDBLink enforces a match of the caller's application version and the activated application version of the company database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEnforceAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isEnforceAppVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setEnforceAppVersion. Returns/sets whether OpenDBLink enforces a match of the caller's application version and the activated application version of the company database.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setEnforceAppVersion  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setEnforceAppVersion(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSystemHelpURL. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The systemHelpURL
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getSystemHelpURL();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isCheckStatusOnOpen. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCheckStatusOnOpen  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isCheckStatusOnOpen();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setCheckStatusOnOpen. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCheckStatusOnOpen  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setCheckStatusOnOpen(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getDBLink(linkType,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] warnPeriodLocked) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.checkSessionDate2(dateInFiscal,periodActive,periodOpen,warnPeriodLocked);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.licenseStatus(appID,appVersion);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] rateTypeDescription) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getCurRateTypeDescription2(rateTypeCode,rateTypeDescription);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] keyData) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getIniFileKey2(appID,primaryKey,secondaryKey,keyData);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String destPath) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.transferFiles(sourceLocation,sourcePath,destLocation,destPath);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getVersion2. Returns version information for the Server. Script programs should use this method.
   *
   * @return    The version
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getVersion2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getVersion2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int distFileType) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getObjectCLSID2(objectID,distFileType);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String objectKey) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.createObjectHandle2(objectID,objectKey);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] daysLeft) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.doesPWExpireToday(userID,pW,expiresToday,expiresInTwoWeeks,daysLeft);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMinimumPasswordLength. Returns the minimum password length required
   *
   * @return    The passwordLength
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMinimumPasswordLength  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getMinimumPasswordLength();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] bstrStatus) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getStatusString(lNumStats,lReqType,dataIn,bstrStatus);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setStatusString(lNumStats,lReqType,bstrRequest2,dataOut);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRightToLeftLanguage. Returns whether the current user language is a right to left language.
   *
   * @return    The rightToLeft
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRightToLeftLanguage  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.isRightToLeftLanguage();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String reserved) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.openWin(domain,userIdentifier,password,dataBase,sessionDate,flags,reserved);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.isWinUserLoggedOn(domain,winUserID,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String bstrFilePath) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getDateFileLastModified(bstrFilePath);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * sessionMacroAppend. Appends a line to a macro. Will NOT generate an OpenView call.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void sessionMacroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.sessionMacroAppend(cmdInString);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * msgrInstalled. Determines if ACCPAC Messenger is installed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.msgrInstalled();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String bstrWinUserID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getAccpacUserID(bstrDomain,bstrWinUserID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMacroData. Internal
   *
   * @return    The dataVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMacroData  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.getMacroData();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * emailConnect. Connects to MAPI.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean emailConnect  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionProxy.emailConnect();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * emailDisconnect. Disconnects from MAPI.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void emailDisconnect  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.emailDisconnect();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getFlagData(lNumStats,lReqType,dataIn,dataOut);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.setFlagData(lNumStats,lReqType,dataIn,dataOut);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.createObjectHandle3(objectID,objectKey,sExtra,objectHandle,cLSID,codebase);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getLegacyReturnCode. Gets the return code given to legacy ACCPAC applications.
   *
   * @param     pReturnCode The pReturnCode (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getLegacyReturnCode  (
              short[] pReturnCode) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getLegacyReturnCode(pReturnCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getASVersion. Gets AS version from system link of currently open DB.
   *
   * @param     pstrVersion The pstrVersion (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getASVersion  (
              String[] pstrVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.getASVersion(pstrVersion);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * activateASCS. Activates AS and CS as required
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void activateASCS  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.activateASCS();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionProxy.checkStatusEx(cSActivated,dateInFiscal,periodActive,homeCurrencyExists,remindersExist,restartRecsExist,isAdmin);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
