package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacViewKey'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>View key interface</B>'
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
public interface IAccpacViewKey extends java.io.Serializable {
  /**
   * getName. Returns the name of the key.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldCount. Returns the number of fields in the key.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFieldCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldValues. Returns/sets an array of all the values of the fields in the key.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getFieldValues  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setFieldValues. Returns/sets an array of all the values of the fields in the key.
   *
   * @param     pVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFieldValues  (
              Object pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getField. Returns the AccpacViewField object by the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getField  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDf86a6c55_b7ce_11d2_9baa_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacViewKeyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "f86a6c55-b7ce-11d2-9baa-00104b71eb3f";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_GET_NAME = "getFieldCount";
  String DISPID_3_GET_NAME = "getFieldValues";
  String DISPID_3_PUT_NAME = "setFieldValues";
  String DISPID_0_GET_NAME = "getField";
}
