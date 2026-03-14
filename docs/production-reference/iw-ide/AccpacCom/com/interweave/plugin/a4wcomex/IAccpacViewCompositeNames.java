package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacViewCompositeNames'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>View composition names collection interface</B>'
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
public interface IAccpacViewCompositeNames extends java.io.Serializable {
  /**
   * item. Returns the composition name (View ID) based on the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @return    The pName
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String item  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCount. Returns the number of composition names contained in the collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDbb181e05_b084_11d2_9ba2_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacViewCompositeNamesProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "bb181e05-b084-11d2-9ba2-00104b71eb3f";
  String DISPID_0_NAME = "item";
  String DISPID_1_GET_NAME = "getCount";
}
