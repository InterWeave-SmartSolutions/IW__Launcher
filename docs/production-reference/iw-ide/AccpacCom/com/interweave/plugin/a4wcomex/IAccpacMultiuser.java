package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacMultiuser'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IAccpacMultiuser Interface</B>'
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
public interface IAccpacMultiuser extends java.io.Serializable {
  /**
   * lockRsc. Locks a resource shared or exclusive. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockRsc  (
              String resource,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unlockRsc. Unlocks a resource locked by a previous call to LockRsc. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockRsc  (
              String resource) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * lockApp. Locks an application's data shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockApp  (
              String orgID,
              String appID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unlockApp. Unlocks an application's data locked by a previous call to LockApp. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockApp  (
              String orgID,
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * lockOrg. Locks an organization's database shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockOrg  (
              String orgID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unlockOrg. Unlocks an organization's database locked by a previous call to LockOrg. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockOrg  (
              String orgID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * regradeApp. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an application's data. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int regradeApp  (
              String orgID,
              String appID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * regradeOrg. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an organization's database. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int regradeOrg  (
              String orgID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * test. Tests if the specified resource is locked. If the resource is locked, the Exclusive parameter returns whether the exsiting lock is shared or exclusive.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (out: use single element array)
   * @return    The locked
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean test  (
              String resource,
              boolean[] exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID06b6ccf0_6790_430b_aad8_ff3ce294b2a4 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacMultiuserProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "06b6ccf0-6790-430b-aad8-ff3ce294b2a4";
  String DISPID_1_NAME = "lockRsc";
  String DISPID_2_NAME = "unlockRsc";
  String DISPID_3_NAME = "lockApp";
  String DISPID_4_NAME = "unlockApp";
  String DISPID_5_NAME = "lockOrg";
  String DISPID_6_NAME = "unlockOrg";
  String DISPID_7_NAME = "regradeApp";
  String DISPID_8_NAME = "regradeOrg";
  String DISPID_9_NAME = "test";
}
