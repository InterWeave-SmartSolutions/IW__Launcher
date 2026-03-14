package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacViewFields'. Generated 02/10/2006 12:21:34 PM
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
public class IAccpacViewFieldsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacViewFields, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacViewFields.class;

  public IAccpacViewFieldsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacViewFields.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacViewFieldsProxy() {}

  public IAccpacViewFieldsProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacViewFields.IID);
  }

  protected IAccpacViewFieldsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacViewFieldsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByName  (
              String name) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacViewField zz_retVal[] = { null };
    Object zz_parameters[] = { name, zz_retVal };
    vtblInvoke("getFieldByName", 8, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacViewField)zz_retVal[0];
  }

  /**
   * getFieldByIndex. Returns the AccpacViewField object by the 0-based index in this collection.
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByIndex  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacViewField zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), zz_retVal };
    vtblInvoke("getFieldByIndex", 9, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacViewField)zz_retVal[0];
  }

  /**
   * getFieldByID. Returns the AccpacViewField object by its 1-based field ID defined in the view.
   *
   * @param     iD The iD (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getFieldByID  (
              int iD) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacViewField zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(iD), zz_retVal };
    vtblInvoke("getFieldByID", 10, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacViewField)zz_retVal[0];
  }

  /**
   * item. Returns the specified AccpacViewField object.
   *
   * @param     pIndex A Variant (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewField item  (
              Object pIndex) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacViewField zz_retVal[] = { null };
    Object zz_parameters[] = { pIndex == null ? new Variant("pIndex") : pIndex, zz_retVal };
    vtblInvoke("item", 11, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacViewField)zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("1ca4c2e1-b0ba-11d2-9ba2-00104b71eb3f", com.interweave.plugin.a4wcomex.IAccpacViewFieldsProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldByName",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("name", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacViewField.IID, com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy.class) }),
        new com.linar.jintegra.MemberDesc("getFieldByIndex",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacViewField.IID, com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy.class) }),
        new com.linar.jintegra.MemberDesc("getFieldByID",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("iD", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacViewField.IID, com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy.class) }),
        new com.linar.jintegra.MemberDesc("item",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pIndex", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacViewField.IID, com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy.class) }),
});  }
}
