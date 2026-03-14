package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacCurrencyRate'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Exchange rate information interface</B>'
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
public interface IAccpacCurrencyRate extends java.io.Serializable {
  /**
   * getRate. Returns the exchange rate.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getRate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSpread. Returns the allowed difference that the rate can vary from the actual rate.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getSpread  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDateMatch. Returns the date matching method used to determine the exchange rate.
   *
   * @return    A com.interweave.proxy.tagDateMatchEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDateMatch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateOperator. Returns the operator used to determine the rate.
   *
   * @return    A com.interweave.proxy.tagRateOperatorEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRateOperator  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateDate. Returns the effective rate date.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getRateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHomeCurrency. Returns the home (functional) currency code.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateType. Returns the currency rate type.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSourceCurrency. Returns the source currency code.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSourceCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDef51dc23_9eae_11d3_9fcd_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacCurrencyRateProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "ef51dc23-9eae-11d3-9fcd-00c04f815d63";
  String DISPID_1_GET_NAME = "getRate";
  String DISPID_2_GET_NAME = "getSpread";
  String DISPID_3_GET_NAME = "getDateMatch";
  String DISPID_4_GET_NAME = "getRateOperator";
  String DISPID_5_GET_NAME = "getRateDate";
  String DISPID_6_GET_NAME = "getHomeCurrency";
  String DISPID_7_GET_NAME = "getRateType";
  String DISPID_8_GET_NAME = "getSourceCurrency";
}
