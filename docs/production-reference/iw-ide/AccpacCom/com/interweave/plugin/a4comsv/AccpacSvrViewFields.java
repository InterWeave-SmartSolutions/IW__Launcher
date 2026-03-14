package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrViewFields'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>View fields collection class</B>'
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
public class AccpacSvrViewFields implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrViewFields {

  private static final String CLSID = "e44e62f6-30d8-40d0-8a96-962b77f4928a";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrViewFieldsProxy d_IAccpacSvrViewFieldsProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrViewFields interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFields getAsIAccpacSvrViewFields() { return d_IAccpacSvrViewFieldsProxy; }

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
  public static AccpacSvrViewFields getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrViewFields(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrViewFields bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrViewFields(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrViewFieldsProxy; }

  /**
   * Constructs a AccpacSvrViewFields on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrViewFields() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrViewFields on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrViewFields(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrViewFieldsProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewFieldsProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrViewFields using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrViewFields(Object obj) throws java.io.IOException {
    d_IAccpacSvrViewFieldsProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewFieldsProxy(obj);
  }

  /**
   * Release a AccpacSvrViewFields.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrViewFieldsProxy);
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
      return d_IAccpacSvrViewFieldsProxy.getPropertyByName(name);
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
      return d_IAccpacSvrViewFieldsProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSvrViewFieldsProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSvrViewFieldsProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getCount. Returns the number of AccpacViewField objects in this collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldsProxy.getCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByName(name,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByIndex(index,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              com.interweave.plugin.a4comsv.IAccpacSvrViewField[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByID(iD,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object pIndex) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewFieldsProxy.item(pIndex);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int[] iD) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByNameEx(name,pVal,index,iD);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByIndexEx(index,pVal,iD,name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String[] name) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewFieldsProxy.getFieldByIDEx(iD,pVal,index,name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
