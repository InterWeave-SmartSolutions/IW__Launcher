package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacProperty'. Generated 02/10/2006 12:21:34 PM
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
public class IAccpacPropertyProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacProperty, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacProperty.class;

  public IAccpacPropertyProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacProperty.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacPropertyProxy() {}

  public IAccpacPropertyProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacProperty.IID);
  }

  protected IAccpacPropertyProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacPropertyProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getMenuID. Returns/sets the menu ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getMenuID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getMenuID", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setMenuID. Returns/sets the menu ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setMenuID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setMenuID", 8, zz_parameters);
    return;
  }

  /**
   * getObjectID. Returns/sets the object ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getObjectID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getObjectID", 9, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setObjectID. Returns/sets the object ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setObjectID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setObjectID", 10, zz_parameters);
    return;
  }

  /**
   * getKeyword. Returns/sets the property keyword.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getKeyword  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getKeyword", 11, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setKeyword. Returns/sets the property keyword.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setKeyword  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setKeyword", 12, zz_parameters);
    return;
  }

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
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { varBuf == null ? new Variant("varBuf") : varBuf, new Integer(propType), zz_retVal };
    vtblInvoke("propGet", 13, zz_parameters);
    return zz_retVal[0];
  }

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
              int lSize) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { varBuf == null ? new Variant("varBuf") : varBuf, new Integer(lSize), zz_retVal };
    vtblInvoke("propPut", 14, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * propClear. Removes the ACCPAC property. Returns the error code of the operation.
   *
   * @return    The error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int propClear  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("propClear", 15, zz_parameters);
    return zz_retVal[0];
  }

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
              int propType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { objectID, menuID, keyword, buffer, new Integer(propType), zz_retVal };
    vtblInvoke("propGetEx", 16, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getAppID. Returns/sets the application ID to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppID", 17, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setAppID. Returns/sets the application ID to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setAppID", 18, zz_parameters);
    return;
  }

  /**
   * getAppVersion. Returns/sets the application version to which the ACCPAC property belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getAppVersion", 19, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setAppVersion. Returns/sets the application version to which the ACCPAC property belongs.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppVersion  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setAppVersion", 20, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("c5fdb931-7846-11d3-9f9f-00c04f815d63", com.interweave.plugin.a4wcomex.IAccpacPropertyProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getMenuID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setMenuID",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getObjectID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setObjectID",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getKeyword",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setKeyword",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propGet",
            new Class[] { Object.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("varBuf", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("propType", 3, 10, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propPut",
            new Class[] { Object.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("varBuf", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("lSize", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propClear",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("propGetEx",
            new Class[] { String.class, String.class, String.class, Object[].class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("objectID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("keyword", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("buffer", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("propType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setAppID",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getAppVersion",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setAppVersion",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
