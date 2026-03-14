package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiView2'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiView Interface</B>'
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
public interface IxapiView2 extends com.interweave.plugin.A4WCOM.IxapiView, java.io.Serializable {
  /**
   * getTitle. The description of the view
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTitle  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getUnpostedRevisions. Property UnpostedRevisions indicates whether the revision list is dirty
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getUnpostedRevisions  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getReferentialIntegrity. Property ReferentialIntegrity
   *
   * @return    A com.interweave.plugin.A4WCOM.tagReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setReferentialIntegrity. Property ReferentialIntegrity
   *
   * @param     pVal A com.interweave.plugin.A4WCOM.tagReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSystemAccess. Property SystemAcces indicates view access mode
   *
   * @return    A com.interweave.plugin.A4WCOM.tagSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSystemAccess. Property SystemAcces indicates view access mode
   *
   * @param     pVal A com.interweave.plugin.A4WCOM.tagSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID6d9e7425_e2e5_11d2_9bee_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiView2Proxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "6d9e7425-e2e5-11d2-9bee-00104b71eb3f";
  String DISPID_100_GET_NAME = "getTitle";
  String DISPID_101_GET_NAME = "getUnpostedRevisions";
  String DISPID_102_GET_NAME = "getReferentialIntegrity";
  String DISPID_102_PUT_NAME = "setReferentialIntegrity";
  String DISPID_103_GET_NAME = "getSystemAccess";
  String DISPID_103_PUT_NAME = "setSystemAccess";
}
