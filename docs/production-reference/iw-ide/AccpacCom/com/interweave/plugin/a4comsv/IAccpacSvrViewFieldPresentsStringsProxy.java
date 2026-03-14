package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrViewFieldPresentsStrings'. Generated 12/10/2006 12:40:15 PM
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
public class IAccpacSvrViewFieldPresentsStringsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrViewFieldPresentsStrings.class;

  public IAccpacSvrViewFieldPresentsStringsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrViewFieldPresentsStrings.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrViewFieldPresentsStringsProxy() {}

  public IAccpacSvrViewFieldPresentsStringsProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrViewFieldPresentsStrings.IID);
  }

  protected IAccpacSvrViewFieldPresentsStringsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrViewFieldPresentsStringsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
              int[] pCookie) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), pCookie, zz_retVal };
    vtblInvoke("getPredefinedString", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getCount. Returns the number of predefined strings.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCount", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPredefinedValue. Returns the predefined value according to the cookie.
   *
   * @param     cookie The cookie (in)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPredefinedValue  (
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(cookie), zz_retVal };
    vtblInvoke("getPredefinedValue", 9, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * setFieldValue. Sets the field value to the predefined value according to the cookie.
   *
   * @param     cookie The cookie (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFieldValue  (
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(cookie), zz_retVal };
    vtblInvoke("setFieldValue", 10, zz_parameters);
    return;
  }

  /**
   * getFieldString. Returns the predefined string for the current field value.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFieldString  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFieldString", 11, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getAllPredefinedString. Returns arrays of the predefined strings and their corresponding cookies.
   *
   * @param     pCookies A Variant (out: use single element array)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getAllPredefinedString  (
              Object[] pCookies) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pCookies, zz_retVal };
    vtblInvoke("getAllPredefinedString", 12, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * refresh. Refreshes the presentation strings from the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void refresh  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("refresh", 13, zz_parameters);
    return;
  }

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
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, cookies, strings, values, zz_retVal };
    vtblInvoke("getAll", 14, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("76679c5b-17e2-40a4-9ea1-495aabf38bcb", com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStringsProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getPredefinedString",
            new Class[] { Integer.TYPE, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pCookie", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPredefinedValue",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cookie", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setFieldValue",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cookie", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFieldString",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAllPredefinedString",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pCookies", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pStrings", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("refresh",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAll",
            new Class[] { int[].class, Object[].class, Object[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("cookies", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("strings", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
