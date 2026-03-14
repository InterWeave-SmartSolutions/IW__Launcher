package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrSession'. Generated 12/10/2006 12:40:14 PM
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
public interface IAccpacSvrSession extends java.io.Serializable {
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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * close. Closes the session.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] printSettings) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int strID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppID. Returns the application ID passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProgramName. Returns the program name passed into the Init method.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] rateTypeDescription) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] profileDescs) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] hiddenControls) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getUserCustomizations. Retrives UI customization information for the current user on the specified UI.
   *
   * @param     uIKey The uIKey (in)
   * @return    The hiddenControls
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String[] getUserCustomizations  (
              String uIKey) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String profileDesc) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isOpened. Returns whether the session object is open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isOpened  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getUserID. Returns the user ID of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getUserLanguage. Returns the language code of the current logged-in user. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUserLanguage  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getObjectKey. Returns the object key passed in by the caller application.
   *
   * @return    The pKey
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectKey  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCompanyID. Returns the company ID of the current logged-in company. Returns blank if the session isn't open.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCompanyName. Returns the name of the current logged-in company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSessionDate. Returns the session date used to open the session.
   *
   * @return    The sessionDate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getSessionDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpPath  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRemote. Returns whether the current session is acessing a remote ACCPAC server or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemote  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getInstalledReports. Retrieves an array of installed report files (on the server, if the session is remote) for the specified application.
   *
   * @param     appID The appID (in)
   * @return    The reports
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String[] getInstalledReports  (
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProductSeries. Returns the currently installed ACCPAC product series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrProductSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getProductSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDatabaseSeries. Returns the currently installed ACCPAC database series.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDatabaseSeries constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDatabaseSeries  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] keyData) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] periodActive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * checkHomeCurrency. Returns whether or not the company's home currency exists in the currency table.
   *
   * @return    The currencyExists
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * checkReminders. Returns whether or not there are active reminders for the logged-in user.
   *
   * @return    The remindersExist
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean checkReminders  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String newPW) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppVersion. Returns the version of the application to which the UI belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCodebase. Returns the base path to the all the applications' CAB files.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCodebase  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAccpacMeter. Returns the AccpacMeter object for session.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrMeter (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAccpacMeter  (
              com.interweave.plugin.a4comsv.IAccpacSvrMeter[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String objectName) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * macroPause. Pauses macro recording. Generally macroPause and macroResume are called as a pair.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroPause  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * macroResume. Resumes macro recording. macroResume must follow macroPause.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroResume  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setLegacyReturnCode. Sets the return code given to legacy ACCPAC applications.
   *
   * @param     returnCode The returnCode (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setLegacyReturnCode  (
              short returnCode) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setHelpPath. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHelpPath  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHelpURL. Returns the path to the ACCPAC help files. If the UI is accessing a remote ACCPAC server, the path is the URL to the help path. Otherwise, it is a local path.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] isAdmin) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] companyName) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] pMutlipleDirs) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] dataPaths) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean bSignOn) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getClientSharedDataDirectory. private method
   *
   * @return    The pSharedDataDirectory
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientSharedDataDirectory  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] orgs) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * errGetCount. 
   *
   * @param     count The count (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGetCount  (
              int[] count) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] helpID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int lHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * errClear. 
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errClear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * errGenerateFile. 
   *
   * @param     errFile The errFile (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void errGenerateFile  (
              String[] errFile) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * muUnlockRsc. Unlocks a resource locked by a previous call to LockRsc. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muUnlockRsc  (
              String resource) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * muUnlockOrg. Unlocks an organization's database locked by a previous call to LockOrg. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @return    A com.interweave.plugin.a4comsv.tagSvrMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int muUnlockOrg  (
              String orgID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] source) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] rateInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] currency) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] isCombination) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] isMaster) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] rate) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int lSize) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] pUser) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProgramsPathOnServer. The DOS path to ACCPAC programs directory on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramsPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSharedDataPathOnServer. The DOS path to ACCPAC shared data on the Server
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSharedDataPathOnServer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] revision) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String clientID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unregisterDesktopSession. private method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unregisterDesktopSession  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] codebases) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isEnforceAppVersion. 
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEnforceAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setEnforceAppVersion. 
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setEnforceAppVersion  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSystemHelpURL. 
   *
   * @return    The helpURL
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemHelpURL  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean more) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] moreData) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String programID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] warnPeriodLocked) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isServerMachine. property IsServerMachine
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isServerMachine  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] pDaysLeft) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getMinimumPasswordLength. Returns the minimum password length required
   *
   * @return    The passwordLength
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMinimumPasswordLength  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] changed) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] bstrStatus) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] sessionInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDateFileLastModified. Determine last time file was modified. bstrFilePath represents a DOS path on the server.
   *
   * @param     bstrFilePath The bstrFilePath (in)
   * @return    The pDate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getDateFileLastModified  (
              String bstrFilePath) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * sessionMacroAppend. Appends a line to a macro. Will NOT generate an OpenView call.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void sessionMacroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * msgrInstalled. Determines if ACCPAC Messenger is installed'
   *
   * @return    The isOpened
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String bstrWinUserID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getMacroData. Internal. Do not use
   *
   * @return    The pData
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMacroData  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] dataOut) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[] codebase) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLegacyReturnCode. Gets the return code given to legacy ACCPAC applications.
   *
   * @param     pReturnCode The pReturnCode (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getLegacyReturnCode  (
              short[] pReturnCode) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getASVersion. Gets AS version from currently open DB.
   *
   * @param     pstrVersion The pstrVersion (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getASVersion  (
              String[] pstrVersion) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * activateASCS. Activates AS and CS as required
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void activateASCS  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID06d80057_a4ee_4ab9_905d_a603e068da93 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrSessionProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "06d80057-a4ee-4ab9-905d-a603e068da93";
  String DISPID_1_NAME = "init";
  String DISPID_3_NAME = "open";
  String DISPID_4_NAME = "close";
  String DISPID_6_NAME = "openDBLink";
  String DISPID_10_NAME = "reportSelect";
  String DISPID_16_NAME = "rscGetString";
  String DISPID_17_GET_NAME = "getAppID";
  String DISPID_18_GET_NAME = "getProgramName";
  String DISPID_21_NAME = "getCurRateTypeDescription";
  String DISPID_23_NAME = "getProfiles";
  String DISPID_24_NAME = "getProfileCustomizations";
  String DISPID_25_NAME = "saveProfileCustomizations";
  String DISPID_26_NAME = "getUserCustomizations";
  String DISPID_27_NAME = "createProfile";
  String DISPID_28_GET_NAME = "isOpened";
  String DISPID_29_GET_NAME = "getUserID";
  String DISPID_30_GET_NAME = "getUserLanguage";
  String DISPID_31_NAME = "createObjectHandle";
  String DISPID_32_NAME = "getObjectKey";
  String DISPID_33_GET_NAME = "getCompanyID";
  String DISPID_34_NAME = "getPrintSetup";
  String DISPID_35_GET_NAME = "getCompanyName";
  String DISPID_36_GET_NAME = "getSessionDate";
  String DISPID_46_GET_NAME = "getHelpPath";
  String DISPID_47_GET_NAME = "isRemote";
  String DISPID_48_NAME = "getInstalledReports";
  String DISPID_53_GET_NAME = "getProductSeries";
  String DISPID_54_GET_NAME = "getDatabaseSeries";
  String DISPID_55_NAME = "getIniFileKey";
  String DISPID_56_NAME = "getObjectCLSID";
  String DISPID_57_NAME = "checkSessionDate";
  String DISPID_58_NAME = "checkHomeCurrency";
  String DISPID_59_NAME = "checkReminders";
  String DISPID_60_NAME = "checkRestartRecs";
  String DISPID_61_NAME = "setPW";
  String DISPID_62_GET_NAME = "getAppVersion";
  String DISPID_63_GET_NAME = "getCodebase";
  String DISPID_64_NAME = "getAccpacMeter";
  String DISPID_67_NAME = "macroRecordObject";
  String DISPID_68_NAME = "macroPause";
  String DISPID_69_NAME = "macroResume";
  String DISPID_70_NAME = "getDependencies";
  String DISPID_75_NAME = "getDependenciesForLanguage";
  String DISPID_76_NAME = "setLegacyReturnCode";
  String DISPID_46_PUT_NAME = "setHelpPath";
  String DISPID_77_GET_NAME = "getHelpURL";
  String DISPID_78_NAME = "checkStatus";
  String DISPID_81_NAME = "getSignonInfo";
  String DISPID_82_NAME = "zz_clone";
  String DISPID_84_NAME = "initClientSharedDataDirectory";
  String DISPID_85_NAME = "getClientSharedDataDirs";
  String DISPID_86_NAME = "setClientSharedDataDirectory";
  String DISPID_87_NAME = "getClientSharedDataDirectory";
  String DISPID_88_NAME = "orgsGetInfo";
  String DISPID_89_NAME = "errGetCount";
  String DISPID_90_NAME = "errGetMessage";
  String DISPID_91_NAME = "errPutMessage";
  String DISPID_92_NAME = "errPutRscMessage";
  String DISPID_93_NAME = "errClear";
  String DISPID_94_NAME = "errGenerateFile";
  String DISPID_95_NAME = "muLockRsc";
  String DISPID_96_NAME = "muUnlockRsc";
  String DISPID_97_NAME = "muLockApp";
  String DISPID_98_NAME = "muUnlockApp";
  String DISPID_99_NAME = "muLockOrg";
  String DISPID_100_NAME = "muUnlockOrg";
  String DISPID_101_NAME = "muRegradeApp";
  String DISPID_102_NAME = "muRegradeOrg";
  String DISPID_103_NAME = "muTest";
  String DISPID_104_NAME = "curGetCurTable";
  String DISPID_105_NAME = "curGetCurRate";
  String DISPID_106_NAME = "curGetCurrency";
  String DISPID_107_NAME = "curIsBlockCombination";
  String DISPID_108_NAME = "curIsBlockMaster";
  String DISPID_109_NAME = "curIsBlockMember";
  String DISPID_110_NAME = "propGet";
  String DISPID_111_NAME = "propPut";
  String DISPID_112_NAME = "propClear";
  String DISPID_113_NAME = "getClientDomainUser";
  String DISPID_114_GET_NAME = "getProgramsPathOnServer";
  String DISPID_115_GET_NAME = "getSharedDataPathOnServer";
  String DISPID_116_NAME = "getVersion";
  String DISPID_117_NAME = "registerDesktopSession";
  String DISPID_118_NAME = "unregisterDesktopSession";
  String DISPID_119_NAME = "getAppDependenciesForLanguage";
  String DISPID_120_GET_NAME = "isEnforceAppVersion";
  String DISPID_120_PUT_NAME = "setEnforceAppVersion";
  String DISPID_121_GET_NAME = "getSystemHelpURL";
  String DISPID_122_NAME = "licenseStatus";
  String DISPID_123_NAME = "upload";
  String DISPID_124_NAME = "download";
  String DISPID_125_NAME = "reportSelect2";
  String DISPID_126_NAME = "checkStatus2";
  String DISPID_127_GET_NAME = "isServerMachine";
  String DISPID_128_NAME = "doesPWExpireToday";
  String DISPID_129_NAME = "getMinimumPasswordLength";
  String DISPID_130_NAME = "setPWWithErrorCode";
  String DISPID_131_NAME = "getStatusString";
  String DISPID_132_NAME = "setStatusString";
  String DISPID_133_NAME = "openWin";
  String DISPID_134_NAME = "getDateFileLastModified";
  String DISPID_135_NAME = "sessionMacroAppend";
  String DISPID_136_NAME = "msgrInstalled";
  String DISPID_137_NAME = "getAccpacUserID";
  String DISPID_138_NAME = "getMacroData";
  String DISPID_139_NAME = "getFlagData";
  String DISPID_140_NAME = "setFlagData";
  String DISPID_141_NAME = "createObjectHandle3";
  String DISPID_142_NAME = "getLegacyReturnCode";
  String DISPID_143_NAME = "getASVersion";
  String DISPID_144_NAME = "activateASCS";
}
