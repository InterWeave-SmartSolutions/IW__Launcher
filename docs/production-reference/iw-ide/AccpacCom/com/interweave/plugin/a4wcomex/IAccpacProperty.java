package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacProperty'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC property interface</B>'
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
public interface IAccpacProperty extends java.io.Serializable {
  /**
   * getMenuID. Returns/sets the menu ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getMenuID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setMenuID. Returns/sets the menu ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setMenuID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getObjectID. Returns/sets the object ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setObjectID. Returns/sets the object ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setObjectID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getKeyword. Returns/sets the property keyword.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getKeyword  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setKeyword. Returns/sets the property keyword.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setKeyword  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * propGet. Retrieves the ACCPAC property. Returns the error code of the operation.
   *
   * @param     varBuf A Variant (in)
   * @param     propType A com.interweave.plugin.a4wcomex.tagPropertyType constant (in, optional, pass 0 if not required)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propGet  (
              Object varBuf,
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * propPut. Saves the value as an ACCPAC property. Returns the error code of the operation.
   *
   * @param     varBuf A Variant (in)
   * @param     lSize The lSize (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propPut  (
              Object varBuf,
              int lSize) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * propClear. Removes the ACCPAC property. Returns the error code of the operation.
   *
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propClear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * propGetEx. Retrieves the ACCPAC property corresponding to the specified ObjectID, MenuID and Keyword. returns the error code of the operation.
   *
   * @param     objectID The objectID (in)
   * @param     menuID The menuID (in)
   * @param     keyword The keyword (in)
   * @param     buffer A Variant (out: use single element array)
   * @param     propType A com.interweave.plugin.a4wcomex.tagPropertyType constant (in)
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propGetEx  (
              String objectID,
              String menuID,
              String keyword,
              Object[] buffer,
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppID. Returns/sets the application ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setAppID. Returns/sets the application ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAppVersion. Returns/sets the application version to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setAppVersion. Returns/sets the application version to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppVersion  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDc5fdb931_7846_11d3_9f9f_00c04f815d63 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacPropertyProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "c5fdb931-7846-11d3-9f9f-00c04f815d63";
  String DISPID_1_GET_NAME = "getMenuID";
  String DISPID_1_PUT_NAME = "setMenuID";
  String DISPID_2_GET_NAME = "getObjectID";
  String DISPID_2_PUT_NAME = "setObjectID";
  String DISPID_3_GET_NAME = "getKeyword";
  String DISPID_3_PUT_NAME = "setKeyword";
  String DISPID_4_NAME = "propGet";
  String DISPID_5_NAME = "propPut";
  String DISPID_6_NAME = "propClear";
  String DISPID_7_NAME = "propGetEx";
  String DISPID_8_GET_NAME = "getAppID";
  String DISPID_8_PUT_NAME = "setAppID";
  String DISPID_9_GET_NAME = "getAppVersion";
  String DISPID_9_PUT_NAME = "setAppVersion";
}
