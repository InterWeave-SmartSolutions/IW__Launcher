package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiErrors'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiErrors Interface</B>'
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
public interface IxapiErrors extends java.io.Serializable {
  /**
   * getCount. Number of items in the collection
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getError. property Error
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiError
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiError getError  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * clear. clears error collection
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * item. method Item
   *
   * @param     index A Variant (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiError
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiError item  (
              Object index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * get_NewEnum. property _NewEnum
   *
   * @return    An enumeration.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Enumeration get_NewEnum  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID960780e4_9d87_11d1_b5c7_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiErrorsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "960780e4-9d87-11d1-b5c7-0060083b07c8";
  String DISPID_1_GET_NAME = "getCount";
  String DISPID_2_GET_NAME = "getError";
  String DISPID_3_NAME = "clear";
  String DISPID_0_NAME = "item";
  String DISPID__4_GET_NAME = "get_NewEnum";
}
