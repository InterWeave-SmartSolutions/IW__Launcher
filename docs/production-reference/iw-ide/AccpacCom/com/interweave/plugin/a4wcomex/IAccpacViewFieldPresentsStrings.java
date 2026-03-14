package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacViewFieldPresentsStrings'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>View field presentation strings collection interface</B>'
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
public interface IAccpacViewFieldPresentsStrings extends java.io.Serializable {
  /**
   * getPredefinedString. Returns the predefined string and cookie according to the index.
   *
   * @param     index The index (in)
   * @param     pCookie The pCookie (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPredefinedString  (
              int index,
              int[] pCookie) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCount. Returns the number of predefined strings.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPredefinedValue. Returns the predefined value according to the cookie.
   *
   * @param     cookie The cookie (in)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPredefinedValue  (
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setFieldValue. Sets the field value to the predefined value according to the cookie.
   *
   * @param     cookie The cookie (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFieldValue  (
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFieldString. Returns the predefined string for the current field value.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFieldString  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAllPredefinedString. Returns arrays of the predefined strings and their corresponding cookies.
   *
   * @param     pCookies A Variant (out: use single element array)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getAllPredefinedString  (
              Object[] pCookies) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * refresh. Refreshes the presentation strings from the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void refresh  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAll. Retrieves all of the presentation strings collection's properties in one method call.
   *
   * @param     count The count (out: use single element array)
   * @param     cookies A Variant (out: use single element array)
   * @param     strings A Variant (out: use single element array)
   * @param     values A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAll  (
              int[] count,
              Object[] cookies,
              Object[] strings,
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDc99a56e5_b627_11d2_9ba8_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacViewFieldPresentsStringsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "c99a56e5-b627-11d2-9ba8-00104b71eb3f";
  String DISPID_1_GET_NAME = "getPredefinedString";
  String DISPID_2_GET_NAME = "getCount";
  String DISPID_3_GET_NAME = "getPredefinedValue";
  String DISPID_4_NAME = "setFieldValue";
  String DISPID_5_GET_NAME = "getFieldString";
  String DISPID_6_GET_NAME = "getAllPredefinedString";
  String DISPID_7_NAME = "refresh";
  String DISPID_8_NAME = "getAll";
}
