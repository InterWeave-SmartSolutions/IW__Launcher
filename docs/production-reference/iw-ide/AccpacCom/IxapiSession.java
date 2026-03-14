package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiSession'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiSession Interface</B>'
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
public interface IxapiSession extends java.io.Serializable {
  /**
   * close. method closes the session
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * open. method establishes an XAPI session
   *
   * @param     userIdentifier The userIdentifier (in)
   * @param     password The password (in)
   * @param     dataBase The dataBase (in)
   * @param     sessionDate The sessionDate (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void open  (
              String userIdentifier,
              String password,
              String dataBase,
              java.util.Date sessionDate,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * openView. method creates new instance of view object
   *
   * @param     viewId The viewId (in)
   * @param     programId The programId (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiView openView  (
              String viewId,
              String programId) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCompany. Company profile object
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCurrency. Currency format information object
   *
   * @param     currencyCode The currencyCode (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrency
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrency getCurrency  (
              String currencyCode) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCurrencyTable. Currency table information object
   *
   * @param     curCode The curCode (in)
   * @param     rateType The rateType (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyTable
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyTable getCurrencyTable  (
              String curCode,
              String rateType) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCurrencyRateType. Currency rate type information object
   *
   * @param     rateTypeCode The rateTypeCode (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyRateType
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyRateType getCurrencyRateType  (
              String rateTypeCode) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCurrencyRate. Currency rate  information object
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyRate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyRate getCurrencyRate  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getActiveApplications. Collection of active applications
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiActiveApplications
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiActiveApplications getActiveApplications  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getErrors. Collection of errors
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiErrors
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiErrors getErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * clearErrors. Clears the error stack, frees any memory being used by the error module
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clearErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isWarningIsException  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setWarningIsException  (
              boolean rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * licenseStatus. Checks whether the specified license file is installed in the system. AppVersion can be passed an empty string in which case, the current System Manager version will be assumed.LICENSE_OK= 0,LICENSE_NOTFOUND= -1,LICENSE_EXPIRED= -2
   *
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short licenseStatus  (
              String appID,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * openViewEx. method establishes an XAPI session with extended flag parameters
   *
   * @param     viewId The viewId (in)
   * @param     programId The programId (in)
   * @param     flags The flags (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiView openViewEx  (
              String viewId,
              String programId,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID975d7ad2_8871_11d1_b5a5_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiSessionProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "975d7ad2-8871-11d1-b5a5-0060083b07c8";
  String DISPID_1_NAME = "close";
  String DISPID_2_NAME = "open";
  String DISPID_3_NAME = "openView";
  String DISPID_4_GET_NAME = "getCompany";
  String DISPID_5_GET_NAME = "getCurrency";
  String DISPID_6_GET_NAME = "getCurrencyTable";
  String DISPID_7_GET_NAME = "getCurrencyRateType";
  String DISPID_8_GET_NAME = "getCurrencyRate";
  String DISPID_9_GET_NAME = "getActiveApplications";
  String DISPID_10_GET_NAME = "getErrors";
  String DISPID_11_NAME = "clearErrors";
  String DISPID_12_GET_NAME = "isWarningIsException";
  String DISPID_12_PUT_NAME = "setWarningIsException";
  String DISPID_13_NAME = "licenseStatus";
  String DISPID_14_NAME = "openViewEx";
}
