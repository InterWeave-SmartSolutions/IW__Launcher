package com.interweave.plugin.a4wcomex;

/**
 * COM Interface '_IAccpacViewEvents'. Generated 02/10/2006 12:21:35 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Event interface for the ACCPAC view</B>'
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
public interface _IAccpacViewEvents extends java.io.Serializable {
  /**
   * onRecordChanged. Fired after an operation that changed the current logical record. This event is fired before OnKeyChanged.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onRecordChanged  (
              int eReason,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * onKeyChanged. Fired after an operation that changed the key field(s) of the current logical record. This event is fired after OnRecordChanged.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onKeyChanged  (
              int eReason,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * onRecordChanging. Fired before an operation that would change the current logical record.  The pStatus flag could be set to cancel the operation.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pStatus A com.interweave.plugin.a4wcomex.tagEventStatus constant (in/out: use single element array)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onRecordChanging  (
              int eReason,
              int[] pStatus,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID1a6ab355_af1c_11d2_9ba0_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = _IAccpacViewEventsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "00020400-0000-0000-C000-000000000046";
  String DISPID_2_NAME = "onRecordChanged";
  String DISPID_3_NAME = "onKeyChanged";
  String DISPID_4_NAME = "onRecordChanging";
}
