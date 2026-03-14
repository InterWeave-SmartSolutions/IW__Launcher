package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiField'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiField Interface</B>'
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
public interface IxapiField extends java.io.Serializable {
  /**
   * getValue. Value of the field
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getValue  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setValue. Value of the field
   *
   * @param     pVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setValue  (
              Object pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getName. Name of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDescription. Description of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAttributes. Attributes of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getAttributes  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Type of the field
   *
   * @return    A com.interweave.plugin.A4WCOM.tagFieldTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSize. Size of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSize  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPrecision. Precision of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPrecision  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getStyle. Style of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getStyle  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationType. PresentationType of the field
   *
   * @return    A com.interweave.plugin.A4WCOM.tagFieldPresentationTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPresentationType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationMask. PresentationMask of the field
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPresentationMask  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * putWithoutVerification. Stores value in the field without verification
   *
   * @param     newVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putWithoutVerification  (
              Object newVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationStrings. Collection of presentation strings for field's value
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiFieldPresentationStrings
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiFieldPresentationStrings getPresentationStrings  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getViewName. The name of the view
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getViewName  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID48ef43c4_97f9_11d1_b5be_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiFieldProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "48ef43c4-97f9-11d1-b5be-0060083b07c8";
  String DISPID_0_GET_NAME = "getValue";
  String DISPID_0_PUT_NAME = "setValue";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_GET_NAME = "getDescription";
  String DISPID_3_GET_NAME = "getAttributes";
  String DISPID_4_GET_NAME = "getType";
  String DISPID_5_GET_NAME = "getSize";
  String DISPID_6_GET_NAME = "getPrecision";
  String DISPID_7_GET_NAME = "getStyle";
  String DISPID_8_GET_NAME = "getPresentationType";
  String DISPID_9_GET_NAME = "getPresentationMask";
  String DISPID_10_NAME = "putWithoutVerification";
  String DISPID_11_GET_NAME = "getPresentationStrings";
  String DISPID_12_GET_NAME = "getViewName";
}
