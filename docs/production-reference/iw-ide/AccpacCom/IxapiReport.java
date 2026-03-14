package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiReport'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiReport Interface</B>'
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
public interface IxapiReport extends java.io.Serializable {
  /**
   * getPrintDestination. Current print destination
   *
   * @return    A com.interweave.plugin.A4WCOM.tagPrintDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPrintDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setPrintDestination. Current print destination
   *
   * @param     pVal A com.interweave.plugin.A4WCOM.tagPrintDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPrintDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPrintDirectory. Current print directory
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPrintDirectory  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setPrintDirectory. Current print directory
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPrintDirectory  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getName. Name of the report
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getQuery. property Query
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getQuery  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * select. Selects particular report
   *
   * @param     pSession An reference to a com.interweave.plugin.A4WCOM.IxapiSession (in)
   * @param     bstrReportName The bstrReportName (in)
   * @param     bstrQueryName The bstrQueryName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void select  (
              com.interweave.plugin.A4WCOM.IxapiSession pSession,
              String bstrReportName,
              String bstrQueryName) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * printReport. Prints report
   *
   * @param     numCopies The numCopies (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printReport  (
              short numCopies) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setParam. Sets report's parameter to a given value
   *
   * @param     paramName The paramName (in)
   * @param     paramValue The paramValue (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setParam  (
              String paramName,
              String paramValue) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * close. method closes the Report
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID9cfccba6_90e4_11d1_b5b3_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiReportProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "9cfccba6-90e4-11d1-b5b3-0060083b07c8";
  String DISPID_1_GET_NAME = "getPrintDestination";
  String DISPID_1_PUT_NAME = "setPrintDestination";
  String DISPID_2_GET_NAME = "getPrintDirectory";
  String DISPID_2_PUT_NAME = "setPrintDirectory";
  String DISPID_0_GET_NAME = "getName";
  String DISPID_4_GET_NAME = "getQuery";
  String DISPID_5_NAME = "select";
  String DISPID_6_NAME = "printReport";
  String DISPID_7_NAME = "setParam";
  String DISPID_8_NAME = "close";
}
