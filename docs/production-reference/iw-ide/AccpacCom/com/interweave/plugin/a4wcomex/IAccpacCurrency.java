package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacCurrency'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC currency information interface</B>'
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
public interface IAccpacCurrency extends java.io.Serializable {
  /**
   * getDescription. Returns the currency code description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSymbol. Returns the currency symbol.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSymbol  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDecimals. Returns the number of decimal places (0, 1, 2 or 3) for the currency.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDecimals  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSymbolDisplay. Returns the display position for the currency symbol.
   *
   * @return    A com.interweave.proxy.tagCurrencySymbolDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSymbolDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getNegativeDisplay. Returns the method used to indicate negative amounts.
   *
   * @return    A com.interweave.proxy.tagNegativeDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNegativeDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getThousandsSep. Returns the thousands group separator.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getThousandsSep  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDecimalSep. Returns the decimal group separator.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDecimalSep  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isBlockCombinationWith. Determines if the currency and the specified currency code belong in the same currency block, both as members, or one as a member and the other as its master.
   *
   * @param     curCode The curCode (in)
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @return    The isCombination
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockCombinationWith  (
              String curCode,
              java.util.Date date,
              int blockDateMatch) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isBlockMaster. Determines if the currency is a block master currency.
   *
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @return    The isMaster
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockMaster  (
              java.util.Date date,
              int blockDateMatch) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isBlockMember. Determines if the currency is a member of a currency block, and optionally retrieves the currency rate information with its master.
   *
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @param     currencyRate An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyRate (out: use single element array, optional, pass single element of null if not required)
   * @return    The isMember
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockMember  (
              java.util.Date date,
              int blockDateMatch,
              com.interweave.plugin.a4wcomex.IAccpacCurrencyRate[] currencyRate) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID0cf93b33_9d1d_11d3_9fca_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacCurrencyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "0cf93b33-9d1d-11d3-9fca-00c04f815d63";
  String DISPID_6_GET_NAME = "getDescription";
  String DISPID_7_GET_NAME = "getSymbol";
  String DISPID_8_GET_NAME = "getDecimals";
  String DISPID_9_GET_NAME = "getSymbolDisplay";
  String DISPID_10_GET_NAME = "getNegativeDisplay";
  String DISPID_11_GET_NAME = "getThousandsSep";
  String DISPID_12_GET_NAME = "getDecimalSep";
  String DISPID_13_NAME = "isBlockCombinationWith";
  String DISPID_14_NAME = "isBlockMaster";
  String DISPID_15_NAME = "isBlockMember";
}
