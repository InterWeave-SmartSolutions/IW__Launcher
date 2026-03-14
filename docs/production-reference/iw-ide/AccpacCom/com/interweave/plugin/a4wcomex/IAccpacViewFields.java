package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacViewFields'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>View fields collection interface</B>'
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
public interface IAccpacViewFields extends java.io.Serializable {
  /**
   * getCount. Returns the number of AccpacViewField objects in this collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByName. Returns the AccpacViewField object by the field name.
   *
   * @param     name The name (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByName  (
              String name) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByIndex. Returns the AccpacViewField object by the 0-based index in this collection.
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByIndex  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByID. Returns the AccpacViewField object by its 1-based field ID defined in the view.
   *
   * @param     iD The iD (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByID  (
              int iD) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * item. Returns the specified AccpacViewField object.
   *
   * @param     pIndex A Variant (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField item  (
              Object pIndex) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID1ca4c2e1_b0ba_11d2_9ba2_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacViewFieldsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "1ca4c2e1-b0ba-11d2-9ba2-00104b71eb3f";
  String DISPID_1_GET_NAME = "getCount";
  String DISPID_2_GET_NAME = "getFieldByName";
  String DISPID_3_GET_NAME = "getFieldByIndex";
  String DISPID_4_GET_NAME = "getFieldByID";
  String DISPID_0_NAME = "item";
}
