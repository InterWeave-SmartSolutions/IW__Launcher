package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiKey'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiKey Interface</B>'
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
public interface IxapiKey extends java.io.Serializable {
  /**
   * getName. The symbolic name string of the key
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFields. property Fields
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDbc2cc403_9ceb_11d1_b5c6_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiKeyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "bc2cc403-9ceb-11d1-b5c6-0060083b07c8";
  String DISPID_0_GET_NAME = "getName";
  String DISPID_1_GET_NAME = "getFields";
}
