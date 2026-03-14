package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiCurrencyTable'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiCurrencyTable Interface</B>'
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
public interface IxapiCurrencyTable extends java.io.Serializable {
  /**
   * getCode. The functional currency code
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRateType. The currency rate type
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * getDescription. The currency description
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSourceOfRates. The source from which the rates are quoted
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSourceOfRates  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAudDateTime. The audit stamp date and time
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getAudDateTime  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID26fb1744_9110_11d1_b5b4_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiCurrencyTableProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "26fb1744-9110-11d1-b5b4-0060083b07c8";
  String DISPID_1_GET_NAME = "getCode";
  String DISPID_2_GET_NAME = "getRateType";
  String DISPID_3_GET_NAME = "getAudUserID";
  String DISPID_4_GET_NAME = "getAudOrgID";
  String DISPID_5_GET_NAME = "getDescription";
  String DISPID_6_GET_NAME = "getSourceOfRates";
  String DISPID_7_GET_NAME = "getAudDateTime";
  String DISPID_8_GET_NAME = "getDateMatch";
  String DISPID_9_GET_NAME = "getRateOperator";
}
