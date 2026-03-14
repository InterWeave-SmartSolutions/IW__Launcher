package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrViewFieldPresentsStrings'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>View field presentation strings collection class</B>'
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
public class AccpacSvrViewFieldPresentsStrings implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings {

  private static final String CLSID = "babc4acb-edc4-446b-a83b-234a9ace94f8";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStringsProxy d_IAccpacSvrViewFieldPresentsStringsProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStrings getAsIAccpacSvrViewFieldPresentsStrings() { return d_IAccpacSvrViewFieldPresentsStringsProxy; }

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
  public static AccpacSvrViewFieldPresentsStrings getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrViewFieldPresentsStrings(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrViewFieldPresentsStrings bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrViewFieldPresentsStrings(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrViewFieldPresentsStringsProxy; }

  /**
   * Constructs a AccpacSvrViewFieldPresentsStrings on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrViewFieldPresentsStrings() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrViewFieldPresentsStrings on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrViewFieldPresentsStrings(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrViewFieldPresentsStringsProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStringsProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrViewFieldPresentsStrings using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrViewFieldPresentsStrings(Object obj) throws java.io.IOException {
    d_IAccpacSvrViewFieldPresentsStringsProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewFieldPresentsStringsProxy(obj);
  }

  /**
   * Release a AccpacSvrViewFieldPresentsStrings.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrViewFieldPresentsStringsProxy);
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
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getPropertyByName(name);
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
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSvrViewFieldPresentsStringsProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSvrViewFieldPresentsStringsProxy.invokeMethodByName(name, new Object[]{});
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
              int[] pCookie) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getPredefinedString(index,pCookie);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCount. Returns the number of predefined strings.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getPredefinedValue(cookie);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setFieldValue. Sets the field value to the predefined value according to the cookie.
   *
   * @param     cookie The cookie (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFieldValue  (
              int cookie) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldPresentsStringsProxy.setFieldValue(cookie);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFieldString. Returns the predefined string for the current field value.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFieldString  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getFieldString();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] pCookies) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldPresentsStringsProxy.getAllPredefinedString(pCookies);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * refresh. Refreshes the presentation strings from the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void refresh  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldPresentsStringsProxy.refresh();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldPresentsStringsProxy.getAll(count,cookies,strings,values);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
