package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrSession'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
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
public class AccpacSvrSession implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrSession {

  private static final String CLSID = "13898c04-c674-4b4e-b5c0-d78af3e566ca";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrSessionProxy d_IAccpacSvrSessionProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrSession interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrSession getAsIAccpacSvrSession() { return d_IAccpacSvrSessionProxy; }

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
  public static AccpacSvrSession getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrSession(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrSession bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrSession(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrSessionProxy; }

  /**
   * Constructs a AccpacSvrSession on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrSession() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrSession on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrSession(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrSessionProxy = new com.interweave.plugin.a4comsv.IAccpacSvrSessionProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrSession using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrSession(Object obj) throws java.io.IOException {
    d_IAccpacSvrSessionProxy = new com.interweave.plugin.a4comsv.IAccpacSvrSessionProxy(obj);
  }

  /**
   * Release a AccpacSvrSession.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrSessionProxy);
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
      return d_IAccpacSvrSessionProxy.getPropertyByName(name);
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
      return d_IAccpacSvrSessionProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSvrSessionProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSvrSessionProxy.invokeMethodByName(name, new Object[]{});
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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.init(key,objectHandle,appID,programName,appVersion,clientID,checkStatus,isOpened,sessionInfo);
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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.open(userIdentifier,password,dataBase,sessionDate,flags,reserved,checkStatus,sessionInfo);
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
      d_IAccpacSvrSessionProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.openDBLink(linkType,flags);
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
   * @param     printSettings An unsigned byte (out: use single element array)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrReport
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrReport reportSelect  (
              String bstrReportName,
              String menuID,
              String programID,
              byte[][] printSettings) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.reportSelect(bstrReportName,menuID,programID,printSettings);
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
      return d_IAccpacSvrSessionProxy.rscGetString(appID,strID);
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
      return d_IAccpacSvrSessionProxy.getAppID();
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
      return d_IAccpacSvrSessionProxy.getProgramName();
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
      return d_IAccpacSvrSessionProxy.getCurRateTypeDescription(rateTypeCode,rateTypeDescription);
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
      d_IAccpacSvrSessionProxy.getProfiles(profileIDs,profileDescs);
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
      return d_IAccpacSvrSessionProxy.getProfileCustomizations(profileID,uIKey);
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
      d_IAccpacSvrSessionProxy.saveProfileCustomizations(profileIDs,uIKey,hiddenControls);
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
      return d_IAccpacSvrSessionProxy.getUserCustomizations(uIKey);
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
      d_IAccpacSvrSessionProxy.createProfile(profileID,profileDesc);
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
      return d_IAccpacSvrSessionProxy.isOpened();
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
      return d_IAccpacSvrSessionProxy.getUserID();
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
      return d_IAccpacSvrSessionProxy.getUserLanguage();
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
      d_IAccpacSvrSessionProxy.createObjectHandle(objectID,objectKey,objectHandle,cLSID,codebase);
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
      return d_IAccpacSvrSessionProxy.getObjectKey();
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
      return d_IAccpacSvrSessionProxy.getCompanyID();
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
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup getPrintSetup  (
              String menuID,
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getPrintSetup(menuID,programID);
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
      return d_IAccpacSvrSessionProxy.getCompanyName();
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
      return d_IAccpacSvrSessionProxy.getSessionDate();
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
      return d_IAccpacSvrSessionProxy.getHelpPath();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRemote. Returns whether the current session is acessing a remote ACCPAC server or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemote  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.isRemote();
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
      return d_IAccpacSvrSessionProxy.getInstalledReports(appID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProductSeries. Returns the currently installed ACCPAC product series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrProductSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getProductSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getProductSeries();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDatabaseSeries. Returns the currently installed ACCPAC database series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDatabaseSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDatabaseSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getDatabaseSeries();
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
      return d_IAccpacSvrSessionProxy.getIniFileKey(appID,primaryKey,secondaryKey,keyData);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getObjectCLSID(objectID,distFileType,cLSID,codebase);
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
      d_IAccpacSvrSessionProxy.checkSessionDate(dateInFiscal,periodActive);
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
      return d_IAccpacSvrSessionProxy.checkHomeCurrency();
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
      return d_IAccpacSvrSessionProxy.checkReminders();
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
      d_IAccpacSvrSessionProxy.checkRestartRecs(restartRecsExist,isAdmin);
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
      return d_IAccpacSvrSessionProxy.setPW(userID,oldPW,newPW);
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
      return d_IAccpacSvrSessionProxy.getAppVersion();
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
      return d_IAccpacSvrSessionProxy.getCodebase();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAccpacMeter. Returns the AccpacMeter object for session.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrMeter (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAccpacMeter  (
              com.interweave.plugin.a4comsv.IAccpacSvrMeter[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getAccpacMeter(pVal);
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
      d_IAccpacSvrSessionProxy.macroRecordObject(hWnd,objectName);
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
      d_IAccpacSvrSessionProxy.macroPause();
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
      d_IAccpacSvrSessionProxy.macroResume();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getDependencies(codebaseType,cLSIDs,codebases);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getDependenciesForLanguage(language,codebaseType,cLSIDs,codebases);
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
      d_IAccpacSvrSessionProxy.setLegacyReturnCode(returnCode);
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
      d_IAccpacSvrSessionProxy.setHelpPath(pVal);
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
      return d_IAccpacSvrSessionProxy.getHelpURL();
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
      d_IAccpacSvrSessionProxy.checkStatus(cSActivated,dateInFiscal,periodActive,homeCurrencyExists,remindersExist,restartRecsExist,isAdmin);
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
      d_IAccpacSvrSessionProxy.getSignonInfo(userID,companyID,companyName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.zz_clone(companyID,ppSession,sessionInfo);
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
      d_IAccpacSvrSessionProxy.initClientSharedDataDirectory(pMultipleDirsSetup,pMutlipleDirs);
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
      d_IAccpacSvrSessionProxy.getClientSharedDataDirs(domain,user,dataNames,dataPaths);
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
      d_IAccpacSvrSessionProxy.setClientSharedDataDirectory(bstrPath,bSignOn);
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
      return d_IAccpacSvrSessionProxy.getClientSharedDataDirectory();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] orgs) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.orgsGetInfo(count,orgs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * errGetCount. 
   *
   * @param     count The count (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGetCount  (
              int[] count) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errGetCount(count);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] helpID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errGetMessage(ulIndex,msg,priority,source,errCode,helpFile,helpID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errPutMessage(msg,enumPriority,params,source,errCode,helpFile,lHelpID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errPutRscMessage(appID,rscID,enumPriority,params,source,errCode,helpFile,lHelpID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * errClear. 
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errClear  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errClear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * errGenerateFile. 
   *
   * @param     errFile The errFile (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGenerateFile  (
              String[] errFile) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.errGenerateFile(errFile);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muLockRsc(resource,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String resource) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muUnlockRsc(resource);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muLockApp(orgID,appID,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muUnlockApp(orgID,appID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muLockOrg(orgID,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String orgID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muUnlockOrg(orgID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muRegradeApp(orgID,appID,upgrade);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muRegradeOrg(orgID,upgrade);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.muTest(resource,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] source) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curGetCurTable(curCode,rateType,description,dateMatch,operator,source);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] rateInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curGetCurRate(homeCurrencyCode,rateType,sourceCurrencyCode,date,curRateType,rateInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] currency) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curGetCurrency(currencyCode,currency);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] isCombination) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curIsBlockCombination(homeCurrency,sourceCurrency,date,blockDateMatch,isCombination);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] isMaster) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curIsBlockMaster(curCode,date,blockDateMatch,isMaster);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] rate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.curIsBlockMember(curCode,date,blockDateMatch,isMember,rate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.propGet(objectID,menuID,keyword,appID,appVersion,varBuf,propType);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int lSize) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.propPut(objectID,menuID,keyword,appID,appVersion,varBuf,lSize);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.propClear(objectID,menuID,keyword,appID,appVersion);
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
      d_IAccpacSvrSessionProxy.getClientDomainUser(pDomain,pUser);
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
      return d_IAccpacSvrSessionProxy.getProgramsPathOnServer();
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
      return d_IAccpacSvrSessionProxy.getSharedDataPathOnServer();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] revision) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getVersion(majorVersion,minorVersion,build,revision);
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
      d_IAccpacSvrSessionProxy.registerDesktopSession(userIdentifier,dataBase,clientID);
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
      d_IAccpacSvrSessionProxy.unregisterDesktopSession();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getAppDependenciesForLanguage(appID,pgmName,appVersion,language,codebaseType,cLSIDs,codebases);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isEnforceAppVersion. 
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEnforceAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.isEnforceAppVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setEnforceAppVersion. 
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setEnforceAppVersion  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.setEnforceAppVersion(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSystemHelpURL. 
   *
   * @return    The helpURL
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getSystemHelpURL();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.licenseStatus(appID,appVersion);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean more) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.upload(filename,root,subDir,data,more);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] moreData) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.download(root,subPath,filename,data,moreFile,moreData);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.reportSelect2(bstrReportName,menuID,programID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] warnPeriodLocked) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.checkStatus2(cSActivated,dateInFiscal,periodActive,homeCurrencyExists,remindersExist,restartRecsExist,isAdmin,periodOpen,warnPeriodLocked);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isServerMachine. property IsServerMachine
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isServerMachine  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.isServerMachine();
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
   * @param     pDaysLeft The pDaysLeft (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void doesPWExpireToday  (
              String userID,
              String pW,
              boolean[] expiresToday,
              boolean[] expiresInTwoWeeks,
              int[] pDaysLeft) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.doesPWExpireToday(userID,pW,expiresToday,expiresInTwoWeeks,pDaysLeft);
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
      return d_IAccpacSvrSessionProxy.getMinimumPasswordLength();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] changed) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.setPWWithErrorCode(userID,oldPW,newPW,changed);
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
      d_IAccpacSvrSessionProxy.getStatusString(lNumStats,lReqType,dataIn,bstrStatus);
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
      d_IAccpacSvrSessionProxy.setStatusString(lNumStats,lReqType,bstrRequest2,dataOut);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.openWin(domain,userIdentifier,password,sid,dataBase,sessionDate,flags,reserved,checkStatus,sessionInfo);
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
      return d_IAccpacSvrSessionProxy.getDateFileLastModified(bstrFilePath);
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
      d_IAccpacSvrSessionProxy.sessionMacroAppend(cmdInString);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * msgrInstalled. Determines if ACCPAC Messenger is installed'
   *
   * @return    The isOpened
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.msgrInstalled();
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
      return d_IAccpacSvrSessionProxy.getAccpacUserID(bstrDomain,bstrWinUserID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMacroData. Internal. Do not use
   *
   * @return    The pData
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMacroData  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrSessionProxy.getMacroData();
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
      d_IAccpacSvrSessionProxy.getFlagData(lNumStats,lReqType,dataIn,dataOut);
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
      d_IAccpacSvrSessionProxy.setFlagData(lNumStats,lReqType,dataIn,dataOut);
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
      d_IAccpacSvrSessionProxy.createObjectHandle3(objectID,objectKey,sExtra,objectHandle,cLSID,codebase);
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
      d_IAccpacSvrSessionProxy.getLegacyReturnCode(pReturnCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getASVersion. Gets AS version from currently open DB.
   *
   * @param     pstrVersion The pstrVersion (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getASVersion  (
              String[] pstrVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrSessionProxy.getASVersion(pstrVersion);
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
      d_IAccpacSvrSessionProxy.activateASCS();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
