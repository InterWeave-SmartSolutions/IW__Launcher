package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacDBLink'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC database link class</B>'
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
public class AccpacDBLink implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacDBLink {

  private static final String CLSID = "dff32bd4-aa35-11d2-9b9c-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy d_IAccpacDBLinkProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacDBLink interface */
  public com.interweave.plugin.a4wcomex.IAccpacDBLink getAsIAccpacDBLink() { return d_IAccpacDBLinkProxy; }

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
  public static AccpacDBLink getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacDBLink(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacDBLink bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacDBLink(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacDBLinkProxy; }

  /**
   * Constructs a AccpacDBLink on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacDBLink() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacDBLink on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacDBLink(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacDBLinkProxy = new com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacDBLink using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacDBLink(Object obj) throws java.io.IOException {
    d_IAccpacDBLinkProxy = new com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy(obj);
  }

  /**
   * Release a AccpacDBLink.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacDBLinkProxy);
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
      return d_IAccpacDBLinkProxy.getPropertyByName(name);
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
      return d_IAccpacDBLinkProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacDBLinkProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacDBLinkProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * close. Closes the DB Link.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacDBLinkProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openView. Opens a view based on the specified view ID.
   *
   * @param     viewID The viewID (in)
   * @param     ppView An reference to a com.interweave.plugin.a4wcomex.IAccpacView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openView  (
              String viewID,
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.openView(viewID,ppView);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * createViewTables. Creates the tables maintained and needed by the view.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacDBLinkProxy.createViewTables(viewID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * dropViewTables. Drops (deletes) tables created by CreateViewTables.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void dropViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacDBLinkProxy.dropViewTables(viewID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSession. Returns the AccpacSession object from which this object was created.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getSession();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionBegin. Begins a transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionBegin  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionBegin(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionCommit. Commits the transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionCommit  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionCommit(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionGetLevel. Returns the transaction level on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionGetLevel  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionGetLevel(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionRollback. Rolls back the transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionRollback  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionRollback(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getActiveApp. Returns an AccpacActiveApp object that contains a list of applications activated for the current company database.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacActiveApp
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacActiveApp getActiveApp  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getActiveApp();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompany. Returns an AccpacCompany object that contains the company profile.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getCompany();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * secCheck. Checks the access rights of the current user on the specified security resource.
   *
   * @param     rscID The rscID (in)
   * @return    The access
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean secCheck  (
              String rscID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.secCheck(rscID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Returns the type of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getFlags();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFiscalCalendar. Returns the AccpacFiscalCalendar object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar getFiscalCalendar  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getFiscalCalendar();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object.
   *
   * @param     viewID The viewID (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup getProcessServerSetup  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.getProcessServerSetup(viewID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewExt. Opens a view based on the specified view ID and Process Server setup.
   *
   * @param     viewID The viewID (in)
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup (in)
   * @param     ppView An reference to a com.interweave.plugin.a4wcomex.IAccpacView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openViewExt  (
              String viewID,
              com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup pVal,
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.openViewExt(viewID,pVal,ppView);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewInstance. Opens a view based on the specified view ID and Process Server setup.
   *
   * @param     viewID The viewID (in)
   * @param     ppView An reference to a com.interweave.plugin.a4wcomex.IAccpacView (out: use single element array)
   * @param     readonly The readonly (in, optional, pass false if not required)
   * @param     unvalidated The unvalidated (in, optional, pass false if not required)
   * @param     unrevisioned The unrevisioned (in, optional, pass false if not required)
   * @param     nonheritable The nonheritable (in, optional, pass false if not required)
   * @param     prefetch The prefetch (in, optional, pass 0 if not required)
   * @param     rawPut The rawPut (in, optional, pass false if not required)
   * @param     noncascading The noncascading (in, optional, pass false if not required)
   * @param     extra A Variant (in, optional, pass null if not required)
   * @param     processServer An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup (in, optional, pass 0 if not required)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openViewInstance  (
              String viewID,
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView,
              boolean readonly,
              boolean unvalidated,
              boolean unrevisioned,
              boolean nonheritable,
              int prefetch,
              boolean rawPut,
              boolean noncascading,
              Object extra,
              com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.openViewInstance(viewID,ppView,readonly,unvalidated,unrevisioned,nonheritable,prefetch,rawPut,noncascading,extra,processServer);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * paramGet. Gets the values of the specified field IDs (array of field IDs) from the first record of the specified view. The values are returned as an array with positions corresponding to the FieldIDs array.
   *
   * @param     viewID The viewID (in)
   * @param     fieldIDs A Variant (in)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object paramGet  (
              String viewID,
              Object fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.paramGet(viewID,fieldIDs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewInstance2. Opens a view based on the specified view ID and Process Server setup. Script languages should use this function.
   *
   * @param     viewID The viewID (in)
   * @param     processServer An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup (in, optional, pass 0 if not required)
   * @param     pExtErrInfo A Variant (out: use single element array, optional, pass single element of null if not required)
   * @param     readonly The readonly (in, optional, pass false if not required)
   * @param     unvalidated The unvalidated (in, optional, pass false if not required)
   * @param     unrevisioned The unrevisioned (in, optional, pass false if not required)
   * @param     nonheritable The nonheritable (in, optional, pass false if not required)
   * @param     prefetch The prefetch (in, optional, pass 0 if not required)
   * @param     rawPut The rawPut (in, optional, pass false if not required)
   * @param     noncascading The noncascading (in, optional, pass false if not required)
   * @param     extra A Variant (in, optional, pass null if not required)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacView openViewInstance2  (
              String viewID,
              com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup processServer,
              Object[] pExtErrInfo,
              boolean readonly,
              boolean unvalidated,
              boolean unrevisioned,
              boolean nonheritable,
              int prefetch,
              boolean rawPut,
              boolean noncascading,
              Object extra) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.openViewInstance2(viewID,processServer,pExtErrInfo,readonly,unvalidated,unrevisioned,nonheritable,prefetch,rawPut,noncascading,extra);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionBegin2. Begins a transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionBegin2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionBegin2(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionCommit2. Commits the transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionCommit2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionCommit2(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionGetLevel2. Returns the transaction level on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionGetLevel2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionGetLevel2(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * transactionRollback2. Rolls back the transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionRollback2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.transactionRollback2(pTransLevel);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openView2. Opens a view based on the specified view ID. Script languages should use this function.
   *
   * @param     viewID The viewID (in)
   * @param     pExtErrInfo A Variant (out: use single element array, optional, pass single element of null if not required)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacView openView2  (
              String viewID,
              Object[] pExtErrInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacDBLinkProxy.openView2(viewID,pExtErrInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
