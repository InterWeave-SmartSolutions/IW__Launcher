package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiCompany'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiCompany Interface</B>'
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
public interface IxapiCompany extends java.io.Serializable {
  /**
   * getOrgID. property OrgID
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getOrgID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAudDateTime. The audit stamp date and time
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getAudDateTime  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAudUserID. The audit stamp user ID
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAudUserID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAudOrgID. The audit stamp organization ID
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAudOrgID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getName. Name of the company
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress1. Address information
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress1  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress2. Address information
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress3. Address information
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress3  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress4. Address information
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress4  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCity. The city
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getState. The state
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getState  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPostCode. The postal code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPostCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCountry. The country
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountry  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLocationType. The location type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLocationCode. The location code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPhoneFormat. A flag indication whether the phone and fax numbers are formated
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPhoneFormat  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPhone. The phone number
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPhone  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFax. The fax number
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFax  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getContact. the contact name
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getContact  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCountryCode. The country code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountryCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getBranchCode. The branch code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getBranchCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFiscalPeriods. The number of fiscal periods in the fiscal year
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getFiscalPeriods  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFourPeriodQuarter. the number of the fiscal quarter that contains 4 periods
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getFourPeriodQuarter  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHomeCurrency. The company functional currency
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getMultiCurrency. A flag indicating the company's use of multicurrency
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMultiCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateType. The default rate type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSessionWarnDays. The number of days that date can deviate from the session date without a warning message
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getSessionWarnDays  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID42f0f6e5_8dd4_11d1_b5b1_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiCompanyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "42f0f6e5-8dd4-11d1-b5b1-0060083b07c8";
  String DISPID_0_GET_NAME = "getOrgID";
  String DISPID_2_GET_NAME = "getAudDateTime";
  String DISPID_3_GET_NAME = "getAudUserID";
  String DISPID_4_GET_NAME = "getAudOrgID";
  String DISPID_5_GET_NAME = "getName";
  String DISPID_6_GET_NAME = "getAddress1";
  String DISPID_7_GET_NAME = "getAddress2";
  String DISPID_8_GET_NAME = "getAddress3";
  String DISPID_9_GET_NAME = "getAddress4";
  String DISPID_10_GET_NAME = "getCity";
  String DISPID_11_GET_NAME = "getState";
  String DISPID_12_GET_NAME = "getPostCode";
  String DISPID_13_GET_NAME = "getCountry";
  String DISPID_14_GET_NAME = "getLocationType";
  String DISPID_15_GET_NAME = "getLocationCode";
  String DISPID_16_GET_NAME = "getPhoneFormat";
  String DISPID_17_GET_NAME = "getPhone";
  String DISPID_18_GET_NAME = "getFax";
  String DISPID_19_GET_NAME = "getContact";
  String DISPID_20_GET_NAME = "getCountryCode";
  String DISPID_21_GET_NAME = "getBranchCode";
  String DISPID_22_GET_NAME = "getFiscalPeriods";
  String DISPID_23_GET_NAME = "getFourPeriodQuarter";
  String DISPID_24_GET_NAME = "getHomeCurrency";
  String DISPID_25_GET_NAME = "getMultiCurrency";
  String DISPID_26_GET_NAME = "getRateType";
  String DISPID_27_GET_NAME = "getSessionWarnDays";
}
