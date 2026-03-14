package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacCompany'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Company profile interface</B>'
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
public interface IAccpacCompany extends java.io.Serializable {
  /**
   * getAddress1. Returns the first line of the company's address.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress1  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress2. Returns the second line of the company's address.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress3. Returns the third line of the company's address.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress3  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAddress4. Returns the fourth line of the company's address.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress4  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOrgID. Returns the company's database ID.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getOrgID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getName. Returns the company name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCity. Returns the city where the company is located.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getState. Returns the state where the company is located.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getState  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPostCode. Returns the company's postal code.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPostCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCountry. Returns the country where the company is located.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountry  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLocationType. Returns the company's 2 character location type (for VAT reporting purposes).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLocationCode. Returns the company's location code (for VAT reporting purposes).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPhone. Returns the company's phone number.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPhone  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFax. Returns the company's fax number.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFax  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getContact. Returns the name of the company's contact person.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getContact  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCountryCode. Returns the 3 character country code for the company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountryCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getBranchCode. Returns the 3 character branch code for the company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getBranchCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHomeCurrency. Returns the company's functional (home) currency code.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateType. Returns the company's default rate type code.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isPhoneFormat. Returns whether or not formatting should be done when displaying phone numbers.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isPhoneFormat  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFiscalPeriods. Returns the company's number of fiscal periods in a fiscal year.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getFiscalPeriods  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFourPeriodQuarter. Returns which quarter contains 4 fiscal periods, if the company uses a 13-period ledger.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getFourPeriodQuarter  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSessionWarnDays. Returns the company's warning date range, which is the maximum number of days before/after the session date where a date warning isn't issued.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getSessionWarnDays  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isMulticurrency. Returns whether the company is a multicurrency company or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isMulticurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isEuroCurrency. Returns wheter the company's functional (home) currency is a Euro currency or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isEuroCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getReportingCurrency. Returns the currency to use for that company's reports.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getReportingCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHandleLockedFscPeriods. Indicates how to handle locked fiscal periods.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagCompanyHandleLockedFscPeriods constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHandleLockedFscPeriods  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHandleInactiveGLAccounts. Indicates how to handle inactive GL accounts.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagCompanyHandleInactiveGLAccounts constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHandleInactiveGLAccounts  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHandleNonexistantGLAccounts. Indicates how to handle non-existent GL accounts.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagCompanyHandleNonexistantGLAccounts constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHandleNonexistantGLAccounts  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPhoneMask. Returns the mask used to format phone numbers.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPhoneMask  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDd5d052b3_9c53_11d3_9fc6_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacCompanyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "d5d052b3-9c53-11d3-9fc6-00c04f815d63";
  String DISPID_1_GET_NAME = "getAddress1";
  String DISPID_2_GET_NAME = "getAddress2";
  String DISPID_3_GET_NAME = "getAddress3";
  String DISPID_4_GET_NAME = "getAddress4";
  String DISPID_5_GET_NAME = "getOrgID";
  String DISPID_6_GET_NAME = "getName";
  String DISPID_7_GET_NAME = "getCity";
  String DISPID_8_GET_NAME = "getState";
  String DISPID_9_GET_NAME = "getPostCode";
  String DISPID_10_GET_NAME = "getCountry";
  String DISPID_11_GET_NAME = "getLocationType";
  String DISPID_12_GET_NAME = "getLocationCode";
  String DISPID_13_GET_NAME = "getPhone";
  String DISPID_14_GET_NAME = "getFax";
  String DISPID_15_GET_NAME = "getContact";
  String DISPID_16_GET_NAME = "getCountryCode";
  String DISPID_17_GET_NAME = "getBranchCode";
  String DISPID_18_GET_NAME = "getHomeCurrency";
  String DISPID_19_GET_NAME = "getRateType";
  String DISPID_20_GET_NAME = "isPhoneFormat";
  String DISPID_21_GET_NAME = "getFiscalPeriods";
  String DISPID_22_GET_NAME = "getFourPeriodQuarter";
  String DISPID_23_GET_NAME = "getSessionWarnDays";
  String DISPID_24_GET_NAME = "isMulticurrency";
  String DISPID_25_GET_NAME = "isEuroCurrency";
  String DISPID_26_GET_NAME = "getReportingCurrency";
  String DISPID_27_GET_NAME = "getHandleLockedFscPeriods";
  String DISPID_28_GET_NAME = "getHandleInactiveGLAccounts";
  String DISPID_29_GET_NAME = "getHandleNonexistantGLAccounts";
  String DISPID_30_GET_NAME = "getPhoneMask";
}
