package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacViewField'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>View field interface</B>'
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
public interface IAccpacViewField extends java.io.Serializable {
  /**
   * getName. Returns the field name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getID. Returns the field ID that corresponds to the field index as defined in the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getAttributes. Returns the field attributes.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagFieldAttributeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getAttributes  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDescription. Returns the field description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Returns the field type.
   *
   * @return    A com.interweave.proxy.tagFieldTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSize. Returns the size of the field.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getSize  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPrecision. Returns the precision if the field is of numeric type.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getPrecision  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationType. Returns the presentation type of the field.
   *
   * @return    A com.interweave.proxy.tagFieldPresentationTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPresentationType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationMask. Returns the mask if the presentation type specifies mask.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPresentationMask  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPresentationStrings. Returns the collection of presentation strings of this field.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewFieldPresentsStrings
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewFieldPresentsStrings getPresentationStrings  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setToMax. Sets the field to the maximum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMax  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setToMin. Sets the field to the minimum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMin  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getMaxValue. Returns the maximum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMaxValue  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getMinValue. Returns the minimum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMinValue  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getView. Returns the ACCPAC view object to which this field belongs.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacView getView  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * putWithoutVerification. Sets the value of the field without validation.
   *
   * @param     pNewVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putWithoutVerification  (
              Object pNewVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getValue  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @param     pVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setValue  (
              Object pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getIndex. Returns the field's 0-based index in the AccpacViewFields collection to which it belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getIndex  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getInfo. Retrieves all of the view field's information in one call.
   *
   * @param     index The index (out: use single element array)
   * @param     iD The iD (out: use single element array)
   * @param     name The name (out: use single element array)
   * @param     description The description (out: use single element array)
   * @param     type A com.interweave.proxy.tagFieldTypeEnum constant (out: use single element array)
   * @param     attribs A com.interweave.plugin.a4wcomex.tagFieldAttributeEnum constant (out: use single element array)
   * @param     size The size (out: use single element array)
   * @param     precision The precision (out: use single element array)
   * @param     maxValue A Variant (out: use single element array)
   * @param     minValue A Variant (out: use single element array)
   * @param     presentationType A com.interweave.proxy.tagFieldPresentationTypeEnum constant (out: use single element array)
   * @param     presentationMask The presentationMask (out: use single element array)
   * @param     value A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getInfo  (
              int[] index,
              int[] iD,
              String[] name,
              String[] description,
              int[] type,
              int[] attribs,
              short[] size,
              short[] precision,
              Object[] maxValue,
              Object[] minValue,
              int[] presentationType,
              String[] presentationMask,
              Object[] value) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID3993f686_aee7_11d2_9ba0_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacViewFieldProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "3993f686-aee7-11d2-9ba0-00104b71eb3f";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_GET_NAME = "getID";
  String DISPID_3_GET_NAME = "getAttributes";
  String DISPID_4_GET_NAME = "getDescription";
  String DISPID_5_GET_NAME = "getType";
  String DISPID_6_GET_NAME = "getSize";
  String DISPID_7_GET_NAME = "getPrecision";
  String DISPID_8_GET_NAME = "getPresentationType";
  String DISPID_9_GET_NAME = "getPresentationMask";
  String DISPID_10_GET_NAME = "getPresentationStrings";
  String DISPID_11_NAME = "setToMax";
  String DISPID_12_NAME = "setToMin";
  String DISPID_13_GET_NAME = "getMaxValue";
  String DISPID_14_GET_NAME = "getMinValue";
  String DISPID_15_GET_NAME = "getView";
  String DISPID_16_NAME = "putWithoutVerification";
  String DISPID_0_GET_NAME = "getValue";
  String DISPID_0_PUT_NAME = "setValue";
  String DISPID_19_GET_NAME = "getIndex";
  String DISPID_20_NAME = "getInfo";
}
