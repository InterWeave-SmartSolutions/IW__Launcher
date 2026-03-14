package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacErrors'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC errors collection class</B>'
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
public class AccpacErrors implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacErrors {

  private static final String CLSID = "3b8ac496-bf95-11d2-9bb0-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacErrorsProxy d_IAccpacErrorsProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacErrors interface */
  public com.interweave.plugin.a4wcomex.IAccpacErrors getAsIAccpacErrors() { return d_IAccpacErrorsProxy; }

  /** Compare this object with another */
  public boolean equals(Object o) { 
	if(java.beans.Beans.isDesignTime()) return super.equals(o);
	else return getJintegraDispatch() == null ? false : getJintegraDispatch().equals(o);
  }


  /** the hashcode for this object */
  public int hashCode() { return getJintegraDispatch() == null ? 0 : getJintegraDispatch().hashCode(); }

   /**
   * getActiveObject. Get a reference to a running instance of this class on the current machine using native code.
   *                  <B>Uses native code (See GetActiveObject() in MS doc) and thus can only be used on MS Windows</B>
   *
   * @return    A reference to the running object.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacErrors getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacErrors(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacErrors bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacErrors(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacErrorsProxy; }

  /**
   * Constructs a AccpacErrors on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacErrors() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacErrors on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacErrors(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacErrorsProxy = new com.interweave.plugin.a4wcomex.IAccpacErrorsProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacErrors using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacErrors(Object obj) throws java.io.IOException {
    d_IAccpacErrorsProxy = new com.interweave.plugin.a4wcomex.IAccpacErrorsProxy(obj);
  }

  /**
   * Release a AccpacErrors.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacErrorsProxy);
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
    try {
      return d_IAccpacErrorsProxy.getPropertyByName(name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs A parameter used when getting the proxy.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacErrorsProxy.getPropertyByName(name, rhs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @param     parameters One element for each parameter. Use primitive type wrappers.
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacErrorsProxy.invokeMethodByName(name, parameters);
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacErrorsProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * clear. Clears the collection of errors.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clear  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacErrorsProxy.clear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCount. Returns the number of errors contained in the collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacErrorsProxy.getCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * generateErrorFile. Generates a temporary file that stores the errors in the collection. Returns the path and file name of the generated file.
   *
   * @return    The pFilePath
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String generateErrorFile  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacErrorsProxy.generateErrorFile();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacErrorsProxy.item(index);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacErrorsProxy.put(msg,priority,params,source,errCode,helpFile,helpContextID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacErrorsProxy.get(index,pMsg,pPriority,pSource,pErrCode,pHelpFile,pHelpID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacErrorsProxy.putRscMsg(appID,rscID,priority,params,source,errCode,helpFile,helpContextID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacErrorsProxy.get2(index,pMsg,pPriority,pSource,pErrCode,pHelpFile,pHelpID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
