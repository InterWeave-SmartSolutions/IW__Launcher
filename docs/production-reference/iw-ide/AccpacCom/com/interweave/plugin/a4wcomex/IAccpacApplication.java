package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacApplication'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Activated ACCPAC application interface</B>'
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
public interface IAccpacApplication extends java.io.Serializable {
  /**
   * getPgmID. Returns the 2 character application ID of the add-in (or the base application itself if it isn't an add-in).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPgmID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDataLevel. Returns the application's data level.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDataLevel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPgmVer. Returns the 3 character application version.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPgmVer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSelector. Returns the program ID of the base application.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSelector  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSequence. Returns the add-in sequence number (or 00 if it's a base application).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSequence  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstalled. Returns whether the application module's program files are actually installed or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstalled  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDb5c56173_9214_11d3_9fbc_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacApplicationProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "b5c56173-9214-11d3-9fbc-00c04f815d63";
  String DISPID_4_GET_NAME = "getPgmID";
  String DISPID_5_GET_NAME = "getDataLevel";
  String DISPID_6_GET_NAME = "getPgmVer";
  String DISPID_7_GET_NAME = "getSelector";
  String DISPID_8_GET_NAME = "getSequence";
  String DISPID_9_GET_NAME = "isInstalled";
}
