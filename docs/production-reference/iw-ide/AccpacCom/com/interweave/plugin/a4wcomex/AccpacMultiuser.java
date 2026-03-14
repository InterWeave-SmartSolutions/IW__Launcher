package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacMultiuser'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>AccpacMultiuser Class</B>'
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
public class AccpacMultiuser implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacMultiuser {

  private static final String CLSID = "3ed1940a-a0ae-499f-9cd6-dfae3c3be25b";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacMultiuserProxy d_IAccpacMultiuserProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacMultiuser interface */
  public com.interweave.plugin.a4wcomex.IAccpacMultiuser getAsIAccpacMultiuser() { return d_IAccpacMultiuserProxy; }

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
  public static AccpacMultiuser getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacMultiuser(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacMultiuser bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacMultiuser(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacMultiuserProxy; }

  /**
   * Constructs a AccpacMultiuser on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacMultiuser() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacMultiuser on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacMultiuser(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacMultiuserProxy = new com.interweave.plugin.a4wcomex.IAccpacMultiuserProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacMultiuser using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacMultiuser(Object obj) throws java.io.IOException {
    d_IAccpacMultiuserProxy = new com.interweave.plugin.a4wcomex.IAccpacMultiuserProxy(obj);
  }

  /**
   * Release a AccpacMultiuser.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacMultiuserProxy);
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
      return d_IAccpacMultiuserProxy.getPropertyByName(name);
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
      return d_IAccpacMultiuserProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacMultiuserProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacMultiuserProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * lockRsc. Locks a resource shared or exclusive. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockRsc  (
              String resource,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.lockRsc(resource,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unlockRsc. Unlocks a resource locked by a previous call to LockRsc. Returns the status of the call.
   *
   * @param     resource The resource (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockRsc  (
              String resource) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.unlockRsc(resource);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * lockApp. Locks an application's data shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockApp  (
              String orgID,
              String appID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.lockApp(orgID,appID,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unlockApp. Unlocks an application's data locked by a previous call to LockApp. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockApp  (
              String orgID,
              String appID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.unlockApp(orgID,appID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * lockOrg. Locks an organization's database shared or exclusive. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     exclusive The exclusive (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int lockOrg  (
              String orgID,
              boolean exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.lockOrg(orgID,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unlockOrg. Unlocks an organization's database locked by a previous call to LockOrg. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlockOrg  (
              String orgID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.unlockOrg(orgID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * regradeApp. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an application's data. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     appID The appID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int regradeApp  (
              String orgID,
              String appID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.regradeApp(orgID,appID,upgrade);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * regradeOrg. Upgrades or downgrades (Shared->Exclusive or Exclusive->Shared) a lock on an organization's database. Returns the status of the call.
   *
   * @param     orgID The orgID (in)
   * @param     upgrade The upgrade (in)
   * @return    A com.interweave.plugin.a4wcomex.tagMultiuserStatus constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int regradeOrg  (
              String orgID,
              boolean upgrade) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.regradeOrg(orgID,upgrade);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * test. Tests if the specified resource is locked. If the resource is locked, the Exclusive parameter returns whether the exsiting lock is shared or exclusive.
   *
   * @param     resource The resource (in)
   * @param     exclusive The exclusive (out: use single element array)
   * @return    The locked
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean test  (
              String resource,
              boolean[] exclusive) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacMultiuserProxy.test(resource,exclusive);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
