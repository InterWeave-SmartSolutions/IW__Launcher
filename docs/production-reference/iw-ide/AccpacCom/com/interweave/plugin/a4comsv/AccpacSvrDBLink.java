package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrDBLink'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
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
public class AccpacSvrDBLink implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrDBLink {

  private static final String CLSID = "cfa69d17-0f07-443a-a7ff-eaf4502ed43d";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrDBLinkProxy d_IAccpacSvrDBLinkProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrDBLink interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrDBLink getAsIAccpacSvrDBLink() { return d_IAccpacSvrDBLinkProxy; }

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
  public static AccpacSvrDBLink getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrDBLink(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrDBLink bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrDBLink(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrDBLinkProxy; }

  /**
   * Constructs a AccpacSvrDBLink on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrDBLink() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrDBLink on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrDBLink(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrDBLinkProxy = new com.interweave.plugin.a4comsv.IAccpacSvrDBLinkProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrDBLink using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrDBLink(Object obj) throws java.io.IOException {
    d_IAccpacSvrDBLinkProxy = new com.interweave.plugin.a4comsv.IAccpacSvrDBLinkProxy(obj);
  }

  /**
   * Release a AccpacSvrDBLink.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrDBLinkProxy);
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
      return d_IAccpacSvrDBLinkProxy.getPropertyByName(name);
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
      return d_IAccpacSvrDBLinkProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSvrDBLinkProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSvrDBLinkProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * close. Closes the DB Link.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrDBLinkProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openView. Opens a view based on the specified view ID.
   *
   * @param     viewID The viewID (in)
   * @param     ppView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openView  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.openView(viewID,ppView);
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
      d_IAccpacSvrDBLinkProxy.createViewTables(viewID);
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
      d_IAccpacSvrDBLinkProxy.dropViewTables(viewID);
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
      return d_IAccpacSvrDBLinkProxy.transactionBegin(pTransLevel);
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
      return d_IAccpacSvrDBLinkProxy.transactionCommit(pTransLevel);
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
      return d_IAccpacSvrDBLinkProxy.transactionGetLevel(pTransLevel);
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
      return d_IAccpacSvrDBLinkProxy.transactionRollback(pTransLevel);
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
      return d_IAccpacSvrDBLinkProxy.secCheck(rscID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Returns the type of the DB Link.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.getFlags();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object.
   *
   * @param     viewID The viewID (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup getProcessServerSetup  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.getProcessServerSetup(viewID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewExt. Opens a view based on the specified view ID and Process Server setup.
   *
   * @param     viewID The viewID (in)
   * @param     pVal An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup (in)
   * @param     ppView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openViewExt  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup pVal,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.openViewExt(viewID,pVal,ppView);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewInstance. Opens an instance of the specified view ID, open modes and process server setup.
   *
   * @param     viewID The viewID (in)
   * @param     ppView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @param     readonly The readonly (in, optional, pass false if not required)
   * @param     unvalidated The unvalidated (in, optional, pass false if not required)
   * @param     unrevisioned The unrevisioned (in, optional, pass false if not required)
   * @param     nonheritable The nonheritable (in, optional, pass false if not required)
   * @param     prefetch The prefetch (in, optional, pass 0 if not required)
   * @param     rawPut The rawPut (in, optional, pass false if not required)
   * @param     noncascading The noncascading (in, optional, pass false if not required)
   * @param     extra A Variant (in, optional, pass null if not required)
   * @param     processServer An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup (in, optional, pass 0 if not required)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openViewInstance  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView,
              boolean readonly,
              boolean unvalidated,
              boolean unrevisioned,
              boolean nonheritable,
              int prefetch,
              boolean rawPut,
              boolean noncascading,
              Object extra,
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.openViewInstance(viewID,ppView,readonly,unvalidated,unrevisioned,nonheritable,prefetch,rawPut,noncascading,extra,processServer);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cpGetInfo. 
   *
   * @param     compInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cpGetInfo  (
              byte[][] compInfo) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrDBLinkProxy.cpGetInfo(compInfo);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cpGetActiveApps. 
   *
   * @param     count The count (out: use single element array)
   * @param     apps An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cpGetActiveApps  (
              int[] count,
              byte[][] apps) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrDBLinkProxy.cpGetActiveApps(count,apps);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetFirstYear. Looks up the first fiscal year in the calendar and retrieves the year, the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year is found or not.
   *
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetFirstYear  (
              String[] year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetFirstYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetLastYear. Looks up the last fiscal year in the calendar and retrieves the year, the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year is found or not.
   *
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetLastYear  (
              String[] year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetLastYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetYear. Looks up the specified fiscal year in the calendar and retrieves the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year exists in the calendar or not.
   *
   * @param     year The year (in)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetYear  (
              String year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetPeriod. Looks up the specified date and retrieves the period and year in the calendar, and whether the period is open or not. Returns whether the date is a valid date in the calendar.
   *
   * @param     date The date (in)
   * @param     period The period (out: use single element array, optional, pass single element of null if not required)
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     periodOpen The periodOpen (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetPeriod  (
              java.util.Date date,
              short[] period,
              String[] year,
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetPeriod(date,period,year,periodOpen);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetPeriodDates. Looks up the specified year and period in the calendar and retrieves the start and end dates for the period, and whether the period is open or not. Returns whether the year and period exist in the calendar.
   *
   * @param     year The year (in)
   * @param     period The period (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @param     periodOpen The periodOpen (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetPeriodDates  (
              String year,
              short period,
              java.util.Date[] startDate,
              java.util.Date[] endDate,
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetPeriodDates(year,period,startDate,endDate,periodOpen);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetQuarter. Looks up the specified year and period in the calendar and retrieves the quarter that the period is a part of. Returns whether the period and year exist in the calendar or not.
   *
   * @param     year The year (in)
   * @param     period The period (in)
   * @param     quarter The quarter (out: use single element array)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetQuarter  (
              String year,
              short period,
              short[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetQuarter(year,period,quarter);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetQuarterDates. Looks up the specified year and quarter in the calendar and retrieves the start and end dates of the quarter. Returns whether the year and quarter exist in the calendar or not.
   *
   * @param     year The year (in)
   * @param     quarter The quarter (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetQuarterDates  (
              String year,
              short quarter,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetQuarterDates(year,quarter,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscGetYearDates. Looks up the specified year in the calendar and retrieves the start and end dates of the year. Returns whether the year exists in the calendar or not.
   *
   * @param     year The year (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscGetYearDates  (
              String year,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscGetYearDates(year,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscDatesFromPeriod. Calculates the period start and end dates, given a period number, period type, period length and base date. Returns whether or not the start and end dates were successfully determined.
   *
   * @param     period The period (in)
   * @param     periodType A com.interweave.plugin.a4comsv.tagSvrPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     startDate The startDate (out: use single element array)
   * @param     endDate The endDate (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscDatesFromPeriod  (
              short period,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscDatesFromPeriod(period,periodType,periodLength,baseDate,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fscDateToPeriod. Calculates the period number, given a date, period type, period length and base date. Returns whether or not the period number was successfully determined.
   *
   * @param     date The date (in)
   * @param     periodType A com.interweave.plugin.a4comsv.tagSvrPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     period The period (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fscDateToPeriod  (
              java.util.Date date,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              short[] period) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.fscDateToPeriod(date,periodType,periodLength,baseDate,period);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * paramGet. 
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
      return d_IAccpacSvrDBLinkProxy.paramGet(viewID,fieldIDs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exOpenViewInstance. Opens an instance of the specified view ID, open modes and process server setup.
   *
   * @param     viewID The viewID (in)
   * @param     ppView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @param     openModes The openModes (in, optional, pass 0 if not required)
   * @param     openDirectives The openDirectives (in, optional, pass 0 if not required)
   * @param     openExtra A Variant (in, optional, pass null if not required)
   * @param     processServer An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup (in, optional, pass 0 if not required)
   * @return    The plError
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int exOpenViewInstance  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView,
              int openModes,
              int openDirectives,
              Object openExtra,
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrDBLinkProxy.exOpenViewInstance(viewID,ppView,openModes,openDirectives,openExtra,processServer);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getLinkInfo. 
   *
   * @param     plLinkNo The plLinkNo (out: use single element array)
   * @param     plPib The plPib (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getLinkInfo  (
              int[] plLinkNo,
              int[] plPib) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrDBLinkProxy.getLinkInfo(plLinkNo,plPib);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
