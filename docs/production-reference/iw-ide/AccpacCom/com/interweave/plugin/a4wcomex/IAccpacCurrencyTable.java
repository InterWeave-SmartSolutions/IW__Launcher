package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacCurrencyTable'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Currency table information interface</B>'
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
public interface IAccpacCurrencyTable extends java.io.Serializable {
  /**
   * getDescription. Returns the currency code's description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSourceOfRates. Returns the source from which the exchange rates are quoted.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSourceOfRates  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDateMatch. Returns the date matching method used to determine exchange rates.
   *
   * @return    A com.interweave.proxy.tagDateMatchEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDateMatch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateOperator. Returns the operator used for currency conversion.
   *
   * @return    A com.interweave.proxy.tagRateOperatorEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRateOperator  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDba16827a_a1ce_11d3_9fd0_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacCurrencyTableProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "ba16827a-a1ce-11d3-9fd0-00c04f815d63";
  String DISPID_1_GET_NAME = "getDescription";
  String DISPID_2_GET_NAME = "getSourceOfRates";
  String DISPID_3_GET_NAME = "getDateMatch";
  String DISPID_4_GET_NAME = "getRateOperator";
}
