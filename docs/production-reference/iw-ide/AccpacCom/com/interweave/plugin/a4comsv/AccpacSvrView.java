package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrView'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC view class</B>'
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
public class AccpacSvrView implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrView {

  private static final String CLSID = "20a2fa0d-e199-4e4a-8b24-b6e3b814a590";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrViewProxy d_IAccpacSvrViewProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrView interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrView getAsIAccpacSvrView() { return d_IAccpacSvrViewProxy; }

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
  public static AccpacSvrView getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrView(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrView bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrView(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrViewProxy; }

  /**
   * Constructs a AccpacSvrView on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrView() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrView on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrView(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrViewProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrView using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrView(Object obj) throws java.io.IOException {
    d_IAccpacSvrViewProxy = new com.interweave.plugin.a4comsv.IAccpacSvrViewProxy(obj);
  }

  /**
   * Release a AccpacSvrView.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrViewProxy);
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
      return d_IAccpacSvrViewProxy.getPropertyByName(name);
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
      return d_IAccpacSvrViewProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSvrViewProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSvrViewProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * close. Closes an open view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cancel. Rolls back any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.cancel();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * init. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.init();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * delete. Removes the logical record from the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void delete  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.delete();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * insert. Creates a new logical record in the database from the contents of the current logical record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insert  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.insert();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * post. Commits any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void post  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.post();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * process. Used by special types of views as a signal to perform the operations.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void process  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.process();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unLock. Unlocks the logical record locked by a FetchLock or ReadLock call.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.unLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * update. Writes the contents of the existing logical record back to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void update  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.update();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * verify. Checks the referential integrity of the current record of the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verify  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.verify();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetch. Retrieves the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.fetch();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetchLock. Retrieves and locks the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.fetchLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * read. Fetches the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean read  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.read();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * readLock. Fetches and locks the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.readLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * browse. Establishes the browse filter and direction that affects all subsequent navigation routines.
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void browse  (
              String filter,
              boolean ascending) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.browse(filter,ascending);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * compose. Composes the view with the supplied array of AccpacView objects.
   *
   * @param     viewOrArrayOfViews A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void compose  (
              Object viewOrArrayOfViews) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.compose(viewOrArrayOfViews);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkGet. Performs a block get of field values of the specified array of fields IDs.  Returns the array of field values.
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGet  (
              Object fieldIDs,
              Object[] pValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.blkGet(fieldIDs,pValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkPut. Performs a block put of the specified array of field values to the array of field IDs.
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (in)
   * @param     verify The verify (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkPut  (
              Object fieldIDs,
              Object pValues,
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.blkPut(fieldIDs,pValues,verify);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkGetFields. Performs a block get of field objects of the specified array of fields IDs.  Returns the array of view field objects.
   *
   * @param     fieldIDs A Variant (in)
   * @param     fields A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGetFields  (
              Object fieldIDs,
              Object[] fields) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.blkGetFields(fieldIDs,fields);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_clone. Creates a clone of the current AccpacView object.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrView zz_clone  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.zz_clone();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goTop. Navigates to the first record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goTop  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goTop();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goBottom. Navigates to the last record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goBottom  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goBottom();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goToBookMark. Navigates to the record as specified by the bookmark that is obtained from the RecordBookmark property from a previous record that was navigated to.
   *
   * @param     pBookMark A Variant (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToBookMark  (
              Object pBookMark) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goToBookMark(pBookMark);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goNext. Navigates to the next record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goNext  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goNext();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goPrev. Navigates to the previous record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goPrev  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goPrev();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isDirty. Reports whether fields have been altered.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isDirty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isDirty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isExists. Reports whether the current logical record exists in the database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isExists  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isExists();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getOrder. Returns/sets the order in which the view is accessed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getOrder  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getOrder();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setOrder. Returns/sets the order in which the view is accessed.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrder  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.setOrder(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Returns the type of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSecurity. Returns the access rights the current user is assigned on the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompositeNames. Returns the AccpacViewCompositeNames object that contains the composition information as defined in the view.
   *
   * @param     count The count (out: use single element array)
   * @param     pVal The pVal (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCompositeNames  (
              int[] count,
              String[][] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.getCompositeNames(count,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFields. Returns the AccpacViewFields collection object that represents all the fields in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getFields();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getKeys. Returns the AccpacViewKeys collection object that represents all the keys in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getKeys();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getRecordBookMark. Returns the bookmark of the current logical record that could be passed later on to the GotoBookmark method.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getRecordBookMark  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getRecordBookMark();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDescription. Returns the description of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getDescription();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isUnpostedRevisions. Returns whether there are unposted revisions in the view or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUnpostedRevisions  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isUnpostedRevisions();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSystemAccess. Returns/sets the system access flags of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getSystemAccess();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSystemAccess. Returns/sets the system access flags of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.setSystemAccess(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getReferentialIntegrity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.setReferentialIntegrity(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getViewID. Returns the ID (Roto ID) of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getViewID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getViewID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewReturnCode constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getLastReturnCode();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goTopEx. Calls GoTop, and then BlkGet (into Values). Also retrieves the record number. Returns what GoTop returns.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goTopEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goTopEx(fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goBottomEx. Calls GoBottom, and then BlkGet (into Values). Also retrieves the record number. Returns what GoBottom returns.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goBottomEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goBottomEx(fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goToBookMarkEx. Calls GoToBookMark (on pBookMark), then calls BlkGet to retrieve the field values. Returns what the GoToBookMark call returns.
   *
   * @param     pBookMark A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToBookMarkEx  (
              Object pBookMark,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goToBookMarkEx(pBookMark,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goNextEx. Calls BlkPut (on PutFieldValues), then GoNext, and then BlkGet (into Values). Also retrieves the record number. Returns what GoNext returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goNextEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goNextEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goPrevEx. Calls BlkPut (on PutFieldValues), then GoPrev, and then BlkGet (into Values). Also retrieves the record number. Returns what GoPrev returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goPrevEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goPrevEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetchEx. Calls BlkPut (on PutFieldValues), then Fetch, and then BlkGet (into Values). Also retrieves the record number. Returns what Fetch returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.fetchEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetchLockEx. Calls BlkPut (on PutFieldValues), then FetchLock, and then BlkGet (into Values). Also retrieves the record number. Returns what FetchLock returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchLockEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.fetchLockEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * readEx. Calls BlkPut (on PutFieldValues), then Read, and then BlkGet (into Values). Also retrieves the record number. Returns what Read returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.readEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * readLockEx. Calls BlkPut (on PutFieldValues), then ReadLock, and then BlkGet (into Values). Also retrieves the record number. Returns what ReadLock returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readLockEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.readLockEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * initEx. Calls Init, and then BlkGet (into Values). Also retrieves the record number.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.initEx(fieldIDs,values,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cancelEx. Calls Cancel, and then BlkGet (into Values).
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancelEx  (
              Object fieldIDs,
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.cancelEx(fieldIDs,values);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * updateEx. Calls BlkPut (on PutFieldValues), then Update, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void updateEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.updateEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * insertEx. Calls BlkPut (on PutFieldValues), then Insert, and then BlkGet (into GetFieldValues). Also retrieves the record number.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insertEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.insertEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues,recNum);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * processEx. Calls BlkPut (on PutFieldValues), then Process, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void processEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.processEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * postEx. Calls BlkPut (on PutFieldValues), then Post, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void postEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.postEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * verifyEx. Calls BlkPut (on PutFieldValues), then Verify, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verifyEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.verifyEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseRecordNumbering  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isUseRecordNumbering();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseRecordNumbering  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.setUseRecordNumbering(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getRecordNumber. Returns the record number of the current record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRecordNumber  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getRecordNumber();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goToRecordNumber. Goes to the specified record number. Returns whether the record is found or not.
   *
   * @param     recordNumber The recordNumber (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToRecordNumber  (
              int recordNumber) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goToRecordNumber(recordNumber);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * goToRecordNumberEx. Calls GoToRecordNumber (on RecordNumber), and then BlkGet (into Values). Returns what GoToRecordNumber returns.
   *
   * @param     recordNumber The recordNumber (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToRecordNumberEx  (
              int recordNumber,
              Object fieldIDs,
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.goToRecordNumberEx(recordNumber,fieldIDs,values);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * resetRecordNumbers. Resets all generated record numbers. Record numbers would be re-generated the next time any method is called that positions to a record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void resetRecordNumbers  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.resetRecordNumbers();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * macroAppend. Appends a line to a macro.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.macroAppend(cmdInString);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getHeaderLinkedKeyFieldCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getHeaderLinkedKeyFieldCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHeaderLinkedKeyFieldCount  (
              short pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.setHeaderLinkedKeyFieldCount(pVal);
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
      d_IAccpacSvrViewProxy.recordClear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordGenerate. method RecordGenerate
   *
   * @param     insert The insert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean insert) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.recordGenerate(insert);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstanceSecurity. property InstanceSecurity
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getInstanceSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * tableEmpty. method TableEmpty
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.tableEmpty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionCancel. method RevisionCancel
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionCancel  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.revisionCancel(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionPost. method RevisionPost
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionPost  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.revisionPost(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionExists. method RevisionExists
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionExists  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.revisionExists(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionUnposted. method RevisionUnposted
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionUnposted  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.revisionUnposted(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterDelete. method FilterDelete
   *
   * @param     filter The filter (in)
   * @param     strictness A com.interweave.plugin.a4comsv.tagSvrFilterStrictnessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterDelete  (
              String filter,
              int strictness) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.filterDelete(filter,strictness);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterSelect. method FilterSelect
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @param     order The order (in)
   * @param     origin A com.interweave.plugin.a4comsv.tagSvrFilterOriginEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterSelect  (
              String filter,
              boolean ascending,
              int order,
              int origin) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.filterSelect(filter,ascending,order,origin);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterFetch. method FilterFetch
   *
   * @param     lock The lock (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean filterFetch  (
              boolean lock) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.filterFetch(lock);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getTemplateVersion. property TemplateVersion
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTemplateVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getTemplateVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getTemplateDate. property TemplateDate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getTemplateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getTemplateDate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceReadonly. property InstanceReadonly
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceReadonly  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceReadonly();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceUnrevisioned. property InstanceUnrevisioned
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnrevisioned  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceUnrevisioned();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceUnvalidated. property InstanceUnvalidated
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnvalidated  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceUnvalidated();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceRawPut. property InstanceRawPut
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceRawPut  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceRawPut();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceNoncascading. property InstanceNoncascading
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNoncascading  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceNoncascading();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstancePrefetch. property InstancePrefetch
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstancePrefetch  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getInstancePrefetch();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceNonheritable. property InstanceNonheritable
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNonheritable  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isInstanceNonheritable();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGetViewInfo. 
   *
   * @param     fieldIdentifiers A Variant (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exGetViewInfo  (
              Object[] fieldIdentifiers) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGetViewInfo(fieldIdentifiers);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGetFieldInfo. 
   *
   * @param     fieldID A Variant (in)
   * @param     fieldIDType A com.interweave.plugin.a4comsv.tagSvrFieldIDType constant (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exGetFieldInfo  (
              Object fieldID,
              int fieldIDType) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGetFieldInfo(fieldID,fieldIDType);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exPut. 
   *
   * @param     iD The iD (in)
   * @param     value A Variant (in)
   * @param     verify The verify (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exPut  (
              int iD,
              Object value,
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exPut(iD,value,verify);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoNext. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoNext  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoNext(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoPrev. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoPrev  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoPrev(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoTop. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoTop  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoTop(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoBottom. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoBottom  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoBottom(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoToBookMark. 
   *
   * @param     pBookMark A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoToBookMark  (
              Object pBookMark,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoToBookMark(pBookMark,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRead. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exRead  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exRead(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exReadLock. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exReadLock  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exReadLock(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exFetch. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFetch  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exFetch(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exFetchLock. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFetchLock  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exFetchLock(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exInit. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInit  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exInit(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exCancel. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exCancel  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exCancel(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exInsert. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInsert  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exInsert(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exUpdate. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exUpdate  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exUpdate(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exPost. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exPost  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exPost(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exProcess. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exProcess  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exProcess(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGoToRecordNumber. 
   *
   * @param     recordNumber The recordNumber (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoToRecordNumber  (
              int recordNumber,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exGoToRecordNumber(recordNumber,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exSetOrder. 
   *
   * @param     order The order (in)
   * @param     unique The unique (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exSetOrder  (
              int order,
              boolean unique,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exSetOrder(order,unique,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exLookupValue. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     fieldValues A Variant (in)
   * @param     order The order (in)
   * @param     targetFieldID The targetFieldID (in)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object exLookupValue  (
              Object fieldIDs,
              Object fieldValues,
              int order,
              int targetFieldID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exLookupValue(fieldIDs,fieldValues,order,targetFieldID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exCompose. 
   *
   * @param     viewIDs The viewIDs (in)
   * @param     parentView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exCompose  (
              String[] viewIDs,
              com.interweave.plugin.a4comsv.IAccpacSvrView parentView) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exCompose(viewIDs,parentView);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exGetComposedView. 
   *
   * @param     viewID The viewID (in)
   * @param     view An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exGetComposedView  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] view) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exGetComposedView(viewID,view);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exBlkGet. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exBlkGet  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exBlkGet(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exBlkPut. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (in)
   * @param     verify The verify (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exBlkPut  (
              Object fieldIDs,
              Object pValues,
              boolean verify,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exBlkPut(fieldIDs,pValues,verify,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exVerify. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exVerify  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exVerify(putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRefreshPresentation. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRefreshPresentation  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRefreshPresentation(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exSetSystemAccess. 
   *
   * @param     systemAccess A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exSetSystemAccess  (
              int systemAccess,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exSetSystemAccess(systemAccess,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRecordClear. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordClear  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRecordClear(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRecordGenerate. 
   *
   * @param     insert The insert (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordGenerate  (
              boolean insert,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRecordGenerate(insert,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRevisionCancel. 
   *
   * @param     level The level (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRevisionCancel  (
              int level,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRevisionCancel(level,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRevisionPost. 
   *
   * @param     level The level (in)
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRevisionPost  (
              int level,
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRevisionPost(level,putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * initPrimaryKeyFields. 
   *
   * @param     bookmark A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initPrimaryKeyFields  (
              Object bookmark) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.initPrimaryKeyFields(bookmark);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getClientViewCache. 
   *
   * @param     clientViewInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getClientViewCache  (
              byte[][] clientViewInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.getClientViewCache(clientViewInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exFilterFetch. 
   *
   * @param     lock The lock (in)
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFilterFetch  (
              boolean lock,
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exFilterFetch(lock,putFieldIDs,putFieldValues,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exFilterSelect. 
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @param     order The order (in)
   * @param     origin A com.interweave.plugin.a4comsv.tagSvrFilterOriginEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exFilterSelect  (
              String filter,
              boolean ascending,
              int order,
              int origin,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exFilterSelect(filter,ascending,order,origin,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isUsedOpenViewInstance. Returns whether the view was opened with .OpenViewInstance
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUsedOpenViewInstance  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.isUsedOpenViewInstance();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exInstanceNotify. Sets whether notifications from the view are received.
   *
   * @param     val The val (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInstanceNotify  (
              boolean val) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exInstanceNotify(val);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordCreate. method RecordCreate
   *
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrViewRecordCreateEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordCreate  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.recordCreate(flags);
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
      return d_IAccpacSvrViewProxy.filterCount(filter,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstanceProtocol. method InstanaceProtocol
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceProtocol  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.getInstanceProtocol();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exRecordCreate. method ExRecordCreate
   *
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrViewRecordCreateEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordCreate  (
              int flags,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrViewProxy.exRecordCreate(flags,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exFilterCount. method ExFilterCount
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int exFilterCount  (
              String filter,
              int flags,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exFilterCount(filter,flags,fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exInstanceProtocol. method ExInstanceProtcol
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int exInstanceProtocol  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrViewProxy.exInstanceProtocol(fieldIDs,updatedInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
