package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacView'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
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
public class AccpacView implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacView {

  private static final String CLSID = "98a62848-abd9-11d2-9b9e-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacViewProxy d_IAccpacViewProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacView interface */
  public com.interweave.plugin.a4wcomex.IAccpacView getAsIAccpacView() { return d_IAccpacViewProxy; }

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
  public static AccpacView getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacView(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacView bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacView(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacViewProxy; }

  /**
   * Constructs a AccpacView on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacView() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacView on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacView(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacViewProxy = new com.interweave.plugin.a4wcomex.IAccpacViewProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacView using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacView(Object obj) throws java.io.IOException {
    d_IAccpacViewProxy = new com.interweave.plugin.a4wcomex.IAccpacViewProxy(obj);
  }

  /**
   * Release a AccpacView.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacViewProxy);
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
      return d_IAccpacViewProxy.getPropertyByName(name);
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
      return d_IAccpacViewProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacViewProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacViewProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * close. Closes an open view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.close();
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
      d_IAccpacViewProxy.cancel();
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
      d_IAccpacViewProxy.init();
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
      d_IAccpacViewProxy.delete();
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
      d_IAccpacViewProxy.insert();
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
      d_IAccpacViewProxy.post();
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
      d_IAccpacViewProxy.process();
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
      d_IAccpacViewProxy.unLock();
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
      d_IAccpacViewProxy.update();
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
      d_IAccpacViewProxy.verify();
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
      return d_IAccpacViewProxy.fetch();
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
      return d_IAccpacViewProxy.fetchLock();
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
      return d_IAccpacViewProxy.read();
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
      return d_IAccpacViewProxy.readLock();
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
      d_IAccpacViewProxy.browse(filter,ascending);
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
      d_IAccpacViewProxy.compose(viewOrArrayOfViews);
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
      d_IAccpacViewProxy.blkGet(fieldIDs,pValues);
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
      d_IAccpacViewProxy.blkPut(fieldIDs,pValues,verify);
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
      d_IAccpacViewProxy.blkGetFields(fieldIDs,fields);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_clone. Creates a clone of the current AccpacView object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacView zz_clone  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.zz_clone();
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
      return d_IAccpacViewProxy.goTop();
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
      return d_IAccpacViewProxy.goBottom();
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
      return d_IAccpacViewProxy.goToBookMark(pBookMark);
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
      return d_IAccpacViewProxy.goNext();
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
      return d_IAccpacViewProxy.goPrev();
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
      return d_IAccpacViewProxy.isDirty();
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
      return d_IAccpacViewProxy.isExists();
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
      return d_IAccpacViewProxy.getOrder();
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
      d_IAccpacViewProxy.setOrder(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Returns the type of the view.
   *
   * @return    A com.interweave.proxy.tagRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSecurity. Returns the access rights the current user is assigned on the view.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompositeNames. Returns the AccpacViewCompositeNames object that contains the composition information as defined in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewCompositeNames
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewCompositeNames getCompositeNames  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getCompositeNames();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFields. Returns the AccpacViewFields collection object that represents all the fields in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getFields();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getKeys. Returns the AccpacViewKeys collection object that represents all the keys in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getKeys();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSession. Returns the AccpacSession object that the view is attached to.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getSession();
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
      return d_IAccpacViewProxy.getRecordBookMark();
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
      return d_IAccpacViewProxy.getDescription();
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
      return d_IAccpacViewProxy.isUnpostedRevisions();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSystemAccess. Returns/sets the system access flags of the view.
   *
   * @return    A com.interweave.proxy.tagSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getSystemAccess();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSystemAccess. Returns/sets the system access flags of the view.
   *
   * @param     pVal A com.interweave.proxy.tagSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.setSystemAccess(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @return    A com.interweave.proxy.tagReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getReferentialIntegrity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @param     pVal A com.interweave.proxy.tagReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.setReferentialIntegrity(pVal);
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
      return d_IAccpacViewProxy.getViewID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagViewReturnCode constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getLastReturnCode();
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
      return d_IAccpacViewProxy.goTopEx(fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.goBottomEx(fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.goToBookMarkEx(pBookMark,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.goNextEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.goPrevEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.fetchEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.fetchLockEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.readEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      return d_IAccpacViewProxy.readLockEx(putFieldIDs,putFieldValues,fieldIDs,values,recNum);
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
      d_IAccpacViewProxy.initEx(fieldIDs,values,recNum);
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
      d_IAccpacViewProxy.cancelEx(fieldIDs,values);
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
      d_IAccpacViewProxy.updateEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
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
      d_IAccpacViewProxy.insertEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues,recNum);
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
      d_IAccpacViewProxy.processEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
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
      d_IAccpacViewProxy.postEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
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
      d_IAccpacViewProxy.verifyEx(putFieldIDs,putFieldValues,getFieldIDs,getFieldValues);
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
      return d_IAccpacViewProxy.isUseRecordNumbering();
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
      d_IAccpacViewProxy.setUseRecordNumbering(pVal);
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
      return d_IAccpacViewProxy.getRecordNumber();
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
      return d_IAccpacViewProxy.goToRecordNumber(recordNumber);
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
      return d_IAccpacViewProxy.goToRecordNumberEx(recordNumber,fieldIDs,values);
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
      d_IAccpacViewProxy.resetRecordNumbers();
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
      d_IAccpacViewProxy.macroAppend(cmdInString);
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
      return d_IAccpacViewProxy.getHeaderLinkedKeyFieldCount();
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
      d_IAccpacViewProxy.setHeaderLinkedKeyFieldCount(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordClear. Blanks, zeroes, or defaults the fields in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.recordClear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordGenerate. Generates a unique nonexistent key, and blanks, zeroes, or defaults the remaining fields in the view.
   *
   * @param     insert The insert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean insert) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.recordGenerate(insert);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstanceSecurity. Returns the current access rights the current user is assigned on the view.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getInstanceSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * tableEmpty. Deletes all records from the view by the fastest method available.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.tableEmpty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionCancel. Rolls back any pending changes to the specified revision level, possibly the database.
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionCancel  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.revisionCancel(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionPost. Commits any pending changes to the specified revision level, possibly the database.
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionPost  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.revisionPost(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionExists. Returns whether the current record exists within the specified revision level, possibly the database.
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionExists  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.revisionExists(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionUnposted. Returns whether the the specified revision level has unposted changes.
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionUnposted  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.revisionUnposted(level);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterDelete. Deletes the specified set of records from the view.
   *
   * @param     filter The filter (in)
   * @param     strictness A com.interweave.plugin.a4wcomex.tagFilterStrictnessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterDelete  (
              String filter,
              int strictness) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.filterDelete(filter,strictness);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterSelect. Establishes the browse filter, origin and direction that affects all subsequent navigation routines.
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @param     order The order (in)
   * @param     origin A com.interweave.plugin.a4wcomex.tagFilterOriginEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterSelect  (
              String filter,
              boolean ascending,
              int order,
              int origin) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.filterSelect(filter,ascending,order,origin);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterFetch. Navigates to the next record, according to the current browse filter and direction. Returns whether the current record is moved or not.
   *
   * @param     lock The lock (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean filterFetch  (
              boolean lock) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.filterFetch(lock);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getTemplateVersion. Returns the version of the template used by the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTemplateVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getTemplateVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getTemplateDate. Returns the date of the template used by the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getTemplateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getTemplateDate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceReadonly. Returns whether the view was opened for read-only access.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceReadonly  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceReadonly();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceUnrevisioned. Returns whether the revisioning is suppressed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnrevisioned  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceUnrevisioned();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceUnvalidated. Returns whether the validations are suppressed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnvalidated  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceUnvalidated();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceRawPut. Reserved
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceRawPut  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceRawPut();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceNoncascading. Reserved
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNoncascading  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceNoncascading();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstancePrefetch. Returns the number of records fetched at a time when the view instance is read-only
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstancePrefetch  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getInstancePrefetch();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isInstanceNonheritable. Returns whether the composites the view opens implicitly inherit the flags with which this view was opened.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNonheritable  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.isInstanceNonheritable();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * initPrimaryKeyFields. Initializes the primary key field values using the same key fields of the record as specified by the bookmark.
   *
   * @param     bookmark A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initPrimaryKeyFields  (
              Object bookmark) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.initPrimaryKeyFields(bookmark);
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
      return d_IAccpacViewProxy.isUsedOpenViewInstance();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordCreate. method RecordCreate
   *
   * @param     flags A com.interweave.plugin.a4wcomex.tagViewRecordCreateEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordCreate  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewProxy.recordCreate(flags);
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
      return d_IAccpacViewProxy.filterCount(filter,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInstanceProtocol. method InstanceProtocol
   *
   * @return    A com.interweave.plugin.a4wcomex.tagViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceProtocol  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewProxy.getInstanceProtocol();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
