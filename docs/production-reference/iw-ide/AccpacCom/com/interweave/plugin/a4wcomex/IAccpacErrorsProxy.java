package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacErrors'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC errors collection interface</B>'
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
public class IAccpacErrorsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacErrors, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacErrors.class;

  public IAccpacErrorsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacErrors.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacErrorsProxy() {}

  public IAccpacErrorsProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacErrors.IID);
  }

  protected IAccpacErrorsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacErrorsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * clear. Clears the collection of errors.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clear  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("clear", 7, zz_parameters);
    return;
  }

  /**
   * getCount. Returns the number of errors contained in the collection.
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
   * generateErrorFile. Generates a temporary file that stores the errors in the collection. Returns the path and file name of the generated file.
   *
   * @return    The pFilePath
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String generateErrorFile  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("generateErrorFile", 9, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * item. Returns the error based on the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String item  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), zz_retVal };
    vtblInvoke("item", 10, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * put. Adds a new error message to the collection.
   *
   * @param     msg The msg (in)
   * @param     priority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (in)
   * @param     params A Variant (in, optional, pass null if not required)
   * @param     source The source (in, optional, pass null if not required)
   * @param     errCode The errCode (in, optional, pass null if not required)
   * @param     helpFile The helpFile (in, optional, pass null if not required)
   * @param     helpContextID The helpContextID (in, optional, pass 0 if not required)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void put  (
              String msg,
              int priority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { msg, new Integer(priority), params == null ? new Variant("params", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : params, source, errCode, helpFile, new Integer(helpContextID), zz_retVal };
    vtblInvoke("put", 11, zz_parameters);
    return;
  }

  /**
   * get. Returns the error based on the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @param     pMsg The pMsg (out: use single element array)
   * @param     pPriority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (out: use single element array)
   * @param     pSource The pSource (out: use single element array)
   * @param     pErrCode The pErrCode (out: use single element array)
   * @param     pHelpFile The pHelpFile (out: use single element array)
   * @param     pHelpID The pHelpID (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void get  (
              int index,
              String[] pMsg,
              int[] pPriority,
              String[] pSource,
              String[] pErrCode,
              String[] pHelpFile,
              int[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), pMsg, pPriority, pSource, pErrCode, pHelpFile, pHelpID, zz_retVal };
    vtblInvoke("get", 12, zz_parameters);
    return;
  }

  /**
   * putRscMsg. Adds a new error message (pulled from the resource file of the specified application) to the collection.
   *
   * @param     appID The appID (in)
   * @param     rscID The rscID (in)
   * @param     priority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (in)
   * @param     params A Variant (in, optional, pass null if not required)
   * @param     source The source (in, optional, pass null if not required)
   * @param     errCode The errCode (in, optional, pass null if not required)
   * @param     helpFile The helpFile (in, optional, pass null if not required)
   * @param     helpContextID The helpContextID (in, optional, pass 0 if not required)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putRscMsg  (
              String appID,
              int rscID,
              int priority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { appID, new Integer(rscID), new Integer(priority), params == null ? new Variant("params", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : params, source, errCode, helpFile, new Integer(helpContextID), zz_retVal };
    vtblInvoke("putRscMsg", 13, zz_parameters);
    return;
  }

  /**
   * get2. Returns the error based on the 0-based index in the collection. Script languages should use this function.
   *
   * @param     index The index (in)
   * @param     pMsg A Variant (out: use single element array)
   * @param     pPriority A Variant (out: use single element array)
   * @param     pSource A Variant (out: use single element array)
   * @param     pErrCode A Variant (out: use single element array)
   * @param     pHelpFile A Variant (out: use single element array)
   * @param     pHelpID A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void get2  (
              int index,
              Object[] pMsg,
              Object[] pPriority,
              Object[] pSource,
              Object[] pErrCode,
              Object[] pHelpFile,
              Object[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), pMsg, pPriority, pSource, pErrCode, pHelpFile, pHelpID, zz_retVal };
    vtblInvoke("get2", 14, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("3b8ac495-bf95-11d2-9bb0-00104b71eb3f", com.interweave.plugin.a4wcomex.IAccpacErrorsProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("clear",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("generateErrorFile",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pFilePath", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("item",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("put",
            new Class[] { String.class, int.class, Object.class, String.class, String.class, String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("msg", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("priority", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("params", 16396, 10, 8, null, null), 
              new com.linar.jintegra.Param("source", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("errCode", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("helpFile", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("helpContextID", 3, 10, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("get",
            new Class[] { Integer.TYPE, String[].class, int[].class, String[].class, String[].class, String[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pMsg", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pPriority", 16387, 4, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("pSource", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pErrCode", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pHelpFile", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("pHelpID", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("putRscMsg",
            new Class[] { String.class, Integer.TYPE, int.class, Object.class, String.class, String.class, String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rscID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("priority", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("params", 16396, 10, 8, null, null), 
              new com.linar.jintegra.Param("source", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("errCode", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("helpFile", 8, 10, 8, null, null), 
              new com.linar.jintegra.Param("helpContextID", 3, 10, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("get2",
            new Class[] { Integer.TYPE, Object[].class, Object[].class, Object[].class, Object[].class, Object[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pMsg", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pPriority", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pSource", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pErrCode", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pHelpFile", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pHelpID", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
