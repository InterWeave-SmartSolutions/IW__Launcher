package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacOrganizations'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC organization setups collection class</B>'
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
public class AccpacOrganizations implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacOrganizations {

  private static final String CLSID = "d1cd1546-c1ce-11d2-9bb4-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacOrganizationsProxy d_IAccpacOrganizationsProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacOrganizations interface */
  public com.interweave.plugin.a4wcomex.IAccpacOrganizations getAsIAccpacOrganizations() { return d_IAccpacOrganizationsProxy; }

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
  public static AccpacOrganizations getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacOrganizations(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacOrganizations bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacOrganizations(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacOrganizationsProxy; }

  /**
   * Constructs a AccpacOrganizations on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacOrganizations() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacOrganizations on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacOrganizations(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacOrganizationsProxy = new com.interweave.plugin.a4wcomex.IAccpacOrganizationsProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacOrganizations using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacOrganizations(Object obj) throws java.io.IOException {
    d_IAccpacOrganizationsProxy = new com.interweave.plugin.a4wcomex.IAccpacOrganizationsProxy(obj);
  }

  /**
   * Release a AccpacOrganizations.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacOrganizationsProxy);
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
      return d_IAccpacOrganizationsProxy.getPropertyByName(name);
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
      return d_IAccpacOrganizationsProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacOrganizationsProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacOrganizationsProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * item. Returns the AccpacOrganization object by its name.
   *
   * @param     name The name (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization item  (
              String name) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacOrganizationsProxy.item(name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * reset. Resets the current position to the beginning of the collection.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reset  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacOrganizationsProxy.reset();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * next. Gets the next AccpacOrganization object in the collection.  Returns whether we got such an object.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization (out: use single element array)
   * @return    The fetched
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean next  (
              com.interweave.plugin.a4wcomex.IAccpacOrganization[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacOrganizationsProxy.next(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getOrgsInfo. Gets the database ID, company name, associated system database ID, whether the database is a company or system database, and whether security is enabled for that company.
   *
   * @param     count The count (out: use single element array)
   * @param     databaseIDs The databaseIDs (out: use single element array)
   * @param     names The names (out: use single element array)
   * @param     systemDatabaseIDs The systemDatabaseIDs (out: use single element array)
   * @param     types The types (out: use single element array)
   * @param     secEnabled The secEnabled (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getOrgsInfo  (
              int[] count,
              String[][] databaseIDs,
              String[][] names,
              String[][] systemDatabaseIDs,
              short[][] types,
              boolean[][] secEnabled) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacOrganizationsProxy.getOrgsInfo(count,databaseIDs,names,systemDatabaseIDs,types,secEnabled);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCount. property Count
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacOrganizationsProxy.getCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * itemByIndex. method ItemByIndex
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization itemByIndex  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacOrganizationsProxy.itemByIndex(index);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
