package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrViewFields'. Generated 12/10/2006 12:40:15 PM
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
public interface IAccpacSvrViewFields extends java.io.Serializable {
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
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByName  (
              String name,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByIndex. Returns the AccpacViewField object by the 0-based index in this collection.
   *
   * @param     index The index (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByIndex  (
              int index,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByID. Returns the AccpacViewField object by its 1-based field ID defined in the view.
   *
   * @param     iD The iD (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByID  (
              int iD,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * item. Returns the specified AccpacViewField object.
   *
   * @param     pIndex A Variant (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewField item  (
              Object pIndex) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByNameEx. Returns the AccpacViewField object by the field name.
   *
   * @param     name The name (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @param     index The index (out: use single element array)
   * @param     iD The iD (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByNameEx  (
              String name,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal,
              int[] index,
              int[] iD) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByIndexEx. Returns the AccpacViewField object by the 0-based index in this collection.
   *
   * @param     index The index (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @param     iD The iD (out: use single element array)
   * @param     name The name (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByIndexEx  (
              int index,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal,
              int[] iD,
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldByIDEx. Returns the AccpacViewField object by its 1-based field ID defined in the view.
   *
   * @param     iD The iD (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField (out: use single element array)
   * @param     index The index (out: use single element array)
   * @param     name The name (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getFieldByIDEx  (
              int iD,
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal,
              int[] index,
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID45fae347_996f_4bce_8c7d_89dc33e04d7f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrViewFieldsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "45fae347-996f-4bce-8c7d-89dc33e04d7f";
  String DISPID_1_GET_NAME = "getCount";
  String DISPID_2_GET_NAME = "getFieldByName";
  String DISPID_3_GET_NAME = "getFieldByIndex";
  String DISPID_4_GET_NAME = "getFieldByID";
  String DISPID_0_NAME = "item";
  String DISPID_5_GET_NAME = "getFieldByNameEx";
  String DISPID_6_GET_NAME = "getFieldByIndexEx";
  String DISPID_7_GET_NAME = "getFieldByIDEx";
}
