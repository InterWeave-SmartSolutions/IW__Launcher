package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiSession2'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiSession2 Interface</B>'
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
public interface IxapiSession2 extends com.interweave.plugin.A4WCOM.IxapiSession, java.io.Serializable {
  /**
   * getSession. Session object
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiSession2
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiSession2 getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSessionDate. property SessionDate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getSessionDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getUser. property User
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUser  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDataBase. property Database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDataBase  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID9ea7be65_3947_11d3_9c61_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiSession2Proxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "9ea7be65-3947-11d3-9c61-00104b71eb3f";
  String DISPID_100_GET_NAME = "getSession";
  String DISPID_101_GET_NAME = "getSessionDate";
  String DISPID_102_GET_NAME = "getUser";
  String DISPID_103_GET_NAME = "getDataBase";
}
