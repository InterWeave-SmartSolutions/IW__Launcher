package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiCurrency'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiCurrency Interface</B>'
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
public interface IxapiCurrency extends java.io.Serializable {
  /**
   * getCode. The currency code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * getDescription. The currency code description
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSymbol. The currency symbol
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSymbol  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getThousandsSep. The thousands group separator
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getThousandsSep  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDecimalSep. The decimal group separator
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDecimalSep  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDecimals. The number of decimals places (0,1,2, or 3)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDecimals  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSymbolDisplay. The display position for the currency symbol
   *
   * @return    A com.interweave.plugin.A4WCOM.tagCurrencySymbolDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSymbolDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getNegativeDisplay. The method used to indicate negative amounts
   *
   * @return    A com.interweave.plugin.A4WCOM.tagNegativeDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNegativeDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID9cfccbaa_90e4_11d1_b5b3_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiCurrencyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "9cfccbaa-90e4-11d1-b5b3-0060083b07c8";
  String DISPID_0_GET_NAME = "getCode";
  String DISPID_2_GET_NAME = "getAudDateTime";
  String DISPID_3_GET_NAME = "getAudUserID";
  String DISPID_4_GET_NAME = "getAudOrgID";
  String DISPID_5_GET_NAME = "getDescription";
  String DISPID_6_GET_NAME = "getSymbol";
  String DISPID_7_GET_NAME = "getThousandsSep";
  String DISPID_8_GET_NAME = "getDecimalSep";
  String DISPID_9_GET_NAME = "getDecimals";
  String DISPID_10_GET_NAME = "getSymbolDisplay";
  String DISPID_11_GET_NAME = "getNegativeDisplay";
}
