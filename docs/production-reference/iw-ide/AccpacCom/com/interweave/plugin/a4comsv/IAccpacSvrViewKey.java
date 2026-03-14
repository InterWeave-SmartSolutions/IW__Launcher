package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrViewKey'. Generated 12/10/2006 12:40:15 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
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
public interface IAccpacSvrViewKey extends java.io.Serializable {
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
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewField getField  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldsInfo. 
   *
   * @param     count The count (out: use single element array)
   * @param     fieldIDs A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldsInfo  (
              int[] count,
              Object[] fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID44c205e6_5299_4154_b83e_61dc69efa7b0 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrViewKeyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "44c205e6-5299-4154-b83e-61dc69efa7b0";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_GET_NAME = "getFieldCount";
  String DISPID_3_GET_NAME = "getFieldValues";
  String DISPID_3_PUT_NAME = "setFieldValues";
  String DISPID_0_GET_NAME = "getField";
  String DISPID_4_NAME = "getFieldsInfo";
}
