package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrViewFields'. Generated 12/10/2006 12:40:15 PM
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
public class IAccpacSvrViewFieldsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrViewFields, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrViewFields.class;

  public IAccpacSvrViewFieldsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrViewFields.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrViewFieldsProxy() {}

  public IAccpacSvrViewFieldsProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrViewFields.IID);
  }

  protected IAccpacSvrViewFieldsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrViewFieldsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getCount. Returns the number of AccpacViewField objects in this collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCount", 7, zz_parameters);
    return zz_retVal[0];
  }

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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { name, pVal, zz_retVal };
    vtblInvoke("getFieldByName", 8, zz_parameters);
    return;
  }

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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), pVal, zz_retVal };
    vtblInvoke("getFieldByIndex", 9, zz_parameters);
    return;
  }

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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(iD), pVal, zz_retVal };
    vtblInvoke("getFieldByID", 10, zz_parameters);
    return;
  }

  /**
   * item. Returns the specified AccpacViewField object.
   *
   * @param     pIndex A Variant (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewField item  (
              Object pIndex) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrViewField zz_retVal[] = { null };
    Object zz_parameters[] = { pIndex == null ? new Variant("pIndex") : pIndex, zz_retVal };
    vtblInvoke("item", 11, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrViewField)zz_retVal[0];
  }

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
              int[] iD) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { name, pVal, index, iD, zz_retVal };
    vtblInvoke("getFieldByNameEx", 12, zz_parameters);
    return;
  }

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
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), pVal, iD, name, zz_retVal };
    vtblInvoke("getFieldByIndexEx", 13, zz_parameters);
    return;
  }

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
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(iD), pVal, index, name, zz_retVal };
    vtblInvoke("getFieldByIDEx", 14, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("45fae347-996f-4bce-8c7d-89dc33e04d7f", com.interweave.plugin.a4comsv.IAccpacSvrViewFieldsProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByName",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("name", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByIndex",
            new Class[] { Integer.TYPE, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByID",
            new Class[] { Integer.TYPE, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("iD", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("item",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pIndex", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class) }),
        new com.linar.jintegra.MemberDesc("getFieldByNameEx",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, int[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("name", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("index", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("iD", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByIndexEx",
            new Class[] { Integer.TYPE, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, int[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("iD", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("name", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByIDEx",
            new Class[] { Integer.TYPE, com.interweave.plugin.a4comsv.IAccpacSvrViewField[].class, int[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("iD", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewField.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldProxy.class), 
              new com.linar.jintegra.Param("index", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("name", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
