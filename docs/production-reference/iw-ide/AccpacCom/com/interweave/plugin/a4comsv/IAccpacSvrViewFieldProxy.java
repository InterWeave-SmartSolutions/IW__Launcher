package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrViewField'. Generated 12/10/2006 12:40:15 PM
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
public class IAccpacSvrViewFieldProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrViewField, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrViewField.class;

  public IAccpacSvrViewFieldProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrViewField.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrViewFieldProxy() {}

  public IAccpacSvrViewFieldProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrViewField.IID);
  }

  protected IAccpacSvrViewFieldProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrViewFieldProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
    super(CLSID, iid, host, authInfo);
  }

  public void addListener(String iidStr, Object theListener, Object theSource) throws java.io.IOException {
    super.addListener(iidStr, theListener, theSource);
  }

  public void removeListener(String iidStr, Object theListener) throws java.io.IOException {
    super.removeListener(iidStr, theListener);
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant parameters[] = {};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter value
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs Parameter used when getting the property.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant parameters[] = {rhs == null ? new Variant("rhs", 10, 0x80020004L) : new Variant("rhs", 12, rhs)};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @param     parameters One element for each parameter.  Use primitive type wrappers
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant variantParameters[] = new com.linar.jintegra.Variant[parameters.length];
    for(int i = 0; i < parameters.length; i++) {
      variantParameters[i] = parameters[i] == null ? new Variant("p" + i, 10, 0x80020004L) :
	                                                   new Variant("p" + i, 12, parameters[i]);
    }
    try {
      return super.invoke(name, super.getDispatchIdOfName(name), 1, variantParameters).getVARIANT();
    } catch(NoSuchFieldException nsfe) {
      throw new NoSuchMethodException("There is no method called " + name);
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
      return invokeMethodByName(name, new Object[]{});
  }

  /**
   * getName. Returns the field name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getName", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getID. Returns the field ID that corresponds to the field index as defined in the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getID", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getAttributes. Returns the field attributes.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrFieldAttributeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getAttributes  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAttributes", 9, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getDescription. Returns the field description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDescription", 10, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getType. Returns the field type.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrFieldTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getType", 11, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getSize. Returns the size of the field.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getSize  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSize", 12, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPrecision. Returns the precision if the field is of numeric type.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getPrecision  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getPrecision", 13, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPresentationType. Returns the presentation type of the field.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrFieldPresentationTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPresentationType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getPresentationType", 14, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPresentationMask. Returns the mask if the presentation type specifies mask.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPresentationMask  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getPresentationMask", 15, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getPresentationStrings. Returns the collection of presentation strings of this field.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings getPresentationStrings  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getPresentationStrings", 16, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings)zz_retVal[0];
  }

  /**
   * setToMax. Sets the field to the maximum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMax  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("setToMax", 17, zz_parameters);
    return;
  }

  /**
   * setToMin. Sets the field to the minimum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMin  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("setToMin", 18, zz_parameters);
    return;
  }

  /**
   * getMaxValue. Returns the maximum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMaxValue  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMaxValue", 19, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * getMinValue. Returns the minimum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMinValue  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMinValue", 20, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * getView. Returns the ACCPAC view object to which this field belongs.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrView getView  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrView zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getView", 21, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrView)zz_retVal[0];
  }

  /**
   * putWithoutVerification. Sets the value of the field without validation.
   *
   * @param     pNewVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putWithoutVerification  (
              Object pNewVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pNewVal == null ? new Variant("pNewVal") : pNewVal, zz_retVal };
    vtblInvoke("putWithoutVerification", 22, zz_parameters);
    return;
  }

  /**
   * getValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getValue  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getValue", 23, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * setValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @param     pVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setValue  (
              Object pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal == null ? new Variant("pVal") : pVal, zz_retVal };
    vtblInvoke("setValue", 24, zz_parameters);
    return;
  }

  /**
   * getIndex. Returns the field's 0-based index in the AccpacViewFields collection to which it belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getIndex  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getIndex", 25, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getInfo. Retrieves all of the view field's information in one call.
   *
   * @param     index The index (out: use single element array)
   * @param     iD The iD (out: use single element array)
   * @param     name The name (out: use single element array)
   * @param     description The description (out: use single element array)
   * @param     type A com.interweave.plugin.a4comsv.tagSvrFieldTypeEnum constant (out: use single element array)
   * @param     attribs A com.interweave.plugin.a4comsv.tagSvrFieldAttributeEnum constant (out: use single element array)
   * @param     size The size (out: use single element array)
   * @param     precision The precision (out: use single element array)
   * @param     maxValue A Variant (out: use single element array)
   * @param     minValue A Variant (out: use single element array)
   * @param     presentationType A com.interweave.plugin.a4comsv.tagSvrFieldPresentationTypeEnum constant (out: use single element array)
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
              Object[] value) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { index, iD, name, description, type, attribs, size, precision, maxValue, minValue, presentationType, presentationMask, value, zz_retVal };
    vtblInvoke("getInfo", 26, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("217e8677-ff78-4669-a946-3d53e3b2495e", com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAttributes",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getDescription",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getSize",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPrecision",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPresentationType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getPresentationMask",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPresentationStrings",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStringsProxy.class) }),
        new com.linar.jintegra.MemberDesc("setToMax",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setToMin",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMaxValue",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getMinValue",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getView",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class) }),
        new com.linar.jintegra.MemberDesc("putWithoutVerification",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pNewVal", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getValue",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setValue",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getIndex",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getInfo",
            new Class[] { int[].class, int[].class, String[].class, String[].class, int[].class, int[].class, short[].class, short[].class, Object[].class, Object[].class, int[].class, String[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("iD", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("name", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("description", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("type", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("attribs", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("size", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("precision", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("maxValue", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("minValue", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("presentationType", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("presentationMask", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("value", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
