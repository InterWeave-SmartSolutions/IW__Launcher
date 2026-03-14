package com.interweave.plugin.A4WCOM;

import com.linar.jintegra.*;

/**
 * COM Class 'xapiView'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>xapiView Class</B>'
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
public class xapiView implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.A4WCOM.IxapiView2 {

  private static final String CLSID = "25e6b455-8b9d-11d1-b5a9-0060083b07c8";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.A4WCOM.IxapiView2Proxy d_IxapiView2Proxy = null;

  /** Access this COM class's com.interweave.plugin.A4WCOM.IxapiView2 interface */
  public com.interweave.plugin.A4WCOM.IxapiView2 getAsIxapiView2() { return d_IxapiView2Proxy; }

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
  public static xapiView getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new xapiView(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static xapiView bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new xapiView(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IxapiView2Proxy; }

  /**
   * Constructs a xapiView on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public xapiView() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a xapiView on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public xapiView(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IxapiView2Proxy = new com.interweave.plugin.A4WCOM.IxapiView2Proxy(CLSID, host, null);
  }

  /**
   * Construct a xapiView using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public xapiView(Object obj) throws java.io.IOException {
    d_IxapiView2Proxy = new com.interweave.plugin.A4WCOM.IxapiView2Proxy(obj);
  }

  /**
   * Release a xapiView.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IxapiView2Proxy);
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
      return d_IxapiView2Proxy.getPropertyByName(name);
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
      return d_IxapiView2Proxy.getPropertyByName(name, rhs);
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
    return d_IxapiView2Proxy.invokeMethodByName(name, parameters);
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
    return d_IxapiView2Proxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getTitle. The description of the view
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTitle  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getTitle();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getUnpostedRevisions. Property UnpostedRevisions indicates whether the revision list is dirty
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getUnpostedRevisions  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getUnpostedRevisions();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getReferentialIntegrity. Property ReferentialIntegrity
   *
   * @return    A com.interweave.plugin.A4WCOM.tagReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getReferentialIntegrity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setReferentialIntegrity. Property ReferentialIntegrity
   *
   * @param     pVal A com.interweave.plugin.A4WCOM.tagReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.setReferentialIntegrity(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSystemAccess. Property SystemAcces indicates view access mode
   *
   * @return    A com.interweave.plugin.A4WCOM.tagSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getSystemAccess();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSystemAccess. Property SystemAcces indicates view access mode
   *
   * @param     pVal A com.interweave.plugin.A4WCOM.tagSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.setSystemAccess(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getID. Roto object string
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPgmID. Two-letter program ID
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPgmID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getPgmID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSecurity. Operations available to the user in the given database link
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Type of the view
   *
   * @return    A com.interweave.plugin.A4WCOM.tagRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSubviewNames. Collection of names for the subviews composed with a view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiSubviewNames
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiSubviewNames getSubviewNames  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getSubviewNames();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setOrder. Determines the order in which a view is accessed
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrder  (
              int rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.setOrder(rhs1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * compose. Specifies the subviews with which the view composes
   *
   * @param     viewOrViews A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void compose  (
              Object viewOrViews) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.compose(viewOrViews);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * createTables. Creates the tables maintained and needed by the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createTables  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.createTables();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * dropTables. Deletes tables created by CreateTables method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void dropTables  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.dropTables();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * browse. Starts a query
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void browse  (
              String filter,
              int ascending) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.browse(filter,ascending);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetch. Retrieves the next logical record
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.fetch();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetchLock. Retrieves the next logical record and locks it
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.fetchLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * post. Commits any pending changes to the database
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void post  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.post();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cancel. Rolls back any pending changes to the database
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.cancel();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * process. Initiates special(view dependent) operations
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void process  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.process();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * init. Blanks, zeroes, or defaults the contents of each field in the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.init();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * verify. Checks the referential integrity of the current record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verify  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.verify();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDirty. Reports whether fields have been altered with put method
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDirty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getDirty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getExists. Reports whether the current logical record exists in database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getExists  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getExists();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * read. Fetches the logical record indexed by the current contents of the view's key fields
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int read  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.read();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * readLock. Fetches and locks the logical record indexed by the current contents of the view's key fields
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.readLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unlock. Unlocks the logical record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unlock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.unlock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * update. Writes the contents of the existing logical record back to the database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int update  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.update();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * insert. Creates new record in the database from the contents of the current logical record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insert  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.insert();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * delete. Removes the logical record from database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int delete  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.delete();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFields. Returns the collection of fields available in the view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getFields();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getKeys. Returns the collection of keys available in the view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getKeys();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkGet. takes an array of Field objects and reads values of the fields in a single operation
   *
   * @param     fields A Variant (in)
   * @param     pValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGet  (
              Object fields,
              Object[] pValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.blkGet(fields,pValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkPut. takes an array of Field objects and array of values, and stores the values of the fields into the view
   *
   * @param     fields A Variant (in)
   * @param     values A Variant (in)
   * @param     verify The verify (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkPut  (
              Object fields,
              Object values,
              int verify) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.blkPut(fields,values,verify);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * close. method closes the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordGenerate. RecordGenerate
   *
   * @param     bInsert The bInsert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean bInsert) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.recordGenerate(bInsert);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordClear. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.recordClear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @return    The bThrowIt
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isWarningIsException  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.isWarningIsException();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @param     bThrowIt The bThrowIt (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setWarningIsException  (
              boolean bThrowIt) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.setWarningIsException(bThrowIt);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.getLastReturnCode();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterSelect. method FilterSelect
   *
   * @param     key The key (in)
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterSelect  (
              int key,
              String filter,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.filterSelect(key,filter,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterFetch. method FilterFetch
   *
   * @param     flags The flags (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterFetch  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.filterFetch(flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterDelete. method FilterDelete
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterDelete  (
              String filter,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.filterDelete(filter,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterCount. method FilterCount
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterCount  (
              String filter,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiView2Proxy.filterCount(filter,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * instanceProtocol. method InstanceProtocol
   *
   * @param     flags The flags (in)
   * @param     protocols The protocols (out: use single element array)
   * @param     count The count (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void instanceProtocol  (
              int flags,
              int[] protocols,
              int[] count) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiView2Proxy.instanceProtocol(flags,protocols,count);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
