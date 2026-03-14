package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiCurrencyRate'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiCurrencyRate Interface</B>'
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
public interface IxapiCurrencyRate extends java.io.Serializable {
  /**
   * getHomeCurrencyCode. The home currency rate type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrencyCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateType. The currency rate type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSourceCurrencyCode. The source currency rate type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSourceCurrencyCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateDate. The currency rate date
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getRateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * getDateMatch. Controls date matching(exact, later, erlier)
   *
   * @return    A com.interweave.plugin.A4WCOM.tagDateMatchEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDateMatch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateOperator. The operator used to to determine the rate (multiplication, division)
   *
   * @return    A com.interweave.plugin.A4WCOM.tagRateOperatorEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRateOperator  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRate. The exchange rate
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getRate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSpread. The difference that the rate can vary from the actual rate without generating a warning
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getSpread  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID0631ecc4_91ac_11d1_b5b5_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiCurrencyRateProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "0631ecc4-91ac-11d1-b5b5-0060083b07c8";
  String DISPID_1_GET_NAME = "getHomeCurrencyCode";
  String DISPID_2_GET_NAME = "getRateType";
  String DISPID_3_GET_NAME = "getSourceCurrencyCode";
  String DISPID_4_GET_NAME = "getRateDate";
  String DISPID_5_GET_NAME = "getAudDateTime";
  String DISPID_6_GET_NAME = "getAudUserID";
  String DISPID_7_GET_NAME = "getAudOrgID";
  String DISPID_8_GET_NAME = "getDateMatch";
  String DISPID_9_GET_NAME = "getRateOperator";
  String DISPID_0_GET_NAME = "getRate";
  String DISPID_11_GET_NAME = "getSpread";
}
