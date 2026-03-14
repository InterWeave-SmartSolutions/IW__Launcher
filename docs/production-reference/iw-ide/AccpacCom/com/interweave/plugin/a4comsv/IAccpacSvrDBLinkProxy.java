package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrDBLink'. Generated 12/10/2006 12:40:14 PM
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
public class IAccpacSvrDBLinkProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrDBLink, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrDBLink.class;

  public IAccpacSvrDBLinkProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrDBLink.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrDBLinkProxy() {}

  public IAccpacSvrDBLinkProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrDBLink.IID);
  }

  protected IAccpacSvrDBLinkProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrDBLinkProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
    super(CLSID, iid, host, authInfo);
  }

  public void addListener(String iidStr, Object theListener, Object theSource) throws java.io.IOException {
    super.addListener(iidStr, theListener, theSource);
  }

  public void removeListener(String iidStr, Object theListener) throws java.io.IOException {
    super.removeListener(iidStr, theListener);
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
    com.linar.jintegra.Variant parameters[] = {};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter value
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs Parameter used when getting the property.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant parameters[] = {rhs == null ? new Variant("rhs", 10, 0x80020004L) : new Variant("rhs", 12, rhs)};
    return super.invoke(name, super.getDispatchIdOfName(name), 2, parameters).getVARIANT();
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @param     parameters One element for each parameter.  Use primitive type wrappers
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    com.linar.jintegra.Variant variantParameters[] = new com.linar.jintegra.Variant[parameters.length];
    for(int i = 0; i < parameters.length; i++) {
      variantParameters[i] = parameters[i] == null ? new Variant("p" + i, 10, 0x80020004L) :
	                                                   new Variant("p" + i, 12, parameters[i]);
    }
    try {
      return super.invoke(name, super.getDispatchIdOfName(name), 1, variantParameters).getVARIANT();
    } catch(NoSuchFieldException nsfe) {
      throw new NoSuchMethodException("There is no method called " + name);
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
      return invokeMethodByName(name, new Object[]{});
  }

  /**
   * close. Closes the DB Link.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("close", 7, zz_parameters);
    return;
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
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, ppView, zz_retVal };
    vtblInvoke("openView", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * createViewTables. Creates the tables maintained and needed by the view.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, zz_retVal };
    vtblInvoke("createViewTables", 9, zz_parameters);
    return;
  }

  /**
   * dropViewTables. Drops (deletes) tables created by CreateViewTables.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void dropViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, zz_retVal };
    vtblInvoke("dropViewTables", 10, zz_parameters);
    return;
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
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionBegin", 11, zz_parameters);
    return zz_retVal[0];
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
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionCommit", 12, zz_parameters);
    return zz_retVal[0];
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
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionGetLevel", 13, zz_parameters);
    return zz_retVal[0];
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
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionRollback", 14, zz_parameters);
    return zz_retVal[0];
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
              String rscID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { rscID, zz_retVal };
    vtblInvoke("secCheck", 15, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getType. Returns the type of the DB Link.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getType", 16, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFlags", 17, zz_parameters);
    return zz_retVal[0];
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
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, zz_retVal };
    vtblInvoke("getProcessServerSetup", 18, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup)zz_retVal[0];
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
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, pVal, ppView, zz_retVal };
    vtblInvoke("openViewExt", 19, zz_parameters);
    return zz_retVal[0];
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
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, ppView, new Boolean(readonly), new Boolean(unvalidated), new Boolean(unrevisioned), new Boolean(nonheritable), new Integer(prefetch), new Boolean(rawPut), new Boolean(noncascading), extra == null ? new Variant("extra", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : extra, processServer, zz_retVal };
    vtblInvoke("openViewInstance", 20, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * cpGetInfo. 
   *
   * @param     compInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cpGetInfo  (
              byte[][] compInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { compInfo, zz_retVal };
    vtblInvoke("cpGetInfo", 21, zz_parameters);
    return;
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
              byte[][] apps) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, apps, zz_retVal };
    vtblInvoke("cpGetActiveApps", 22, zz_parameters);
    return;
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("fscGetFirstYear", 23, zz_parameters);
    return zz_retVal[0];
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("fscGetLastYear", 24, zz_parameters);
    return zz_retVal[0];
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("fscGetYear", 25, zz_parameters);
    return zz_retVal[0];
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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, period, year, periodOpen, zz_retVal };
    vtblInvoke("fscGetPeriod", 26, zz_parameters);
    return zz_retVal[0];
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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(period), startDate, endDate, periodOpen, zz_retVal };
    vtblInvoke("fscGetPeriodDates", 27, zz_parameters);
    return zz_retVal[0];
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
              short[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(period), quarter, zz_retVal };
    vtblInvoke("fscGetQuarter", 28, zz_parameters);
    return zz_retVal[0];
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(quarter), startDate, endDate, zz_retVal };
    vtblInvoke("fscGetQuarterDates", 29, zz_parameters);
    return zz_retVal[0];
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, startDate, endDate, zz_retVal };
    vtblInvoke("fscGetYearDates", 30, zz_parameters);
    return zz_retVal[0];
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Short(period), new Integer(periodType), new Short(periodLength), baseDate, startDate, endDate, zz_retVal };
    vtblInvoke("fscDatesFromPeriod", 31, zz_parameters);
    return zz_retVal[0];
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
              short[] period) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, new Integer(periodType), new Short(periodLength), baseDate, period, zz_retVal };
    vtblInvoke("fscDateToPeriod", 32, zz_parameters);
    return zz_retVal[0];
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
              Object fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, zz_retVal };
    vtblInvoke("paramGet", 33, zz_parameters);
    return (Object)zz_retVal[0];
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
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, ppView, new Integer(openModes), new Integer(openDirectives), openExtra == null ? new Variant("openExtra", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : openExtra, processServer, zz_retVal };
    vtblInvoke("exOpenViewInstance", 34, zz_parameters);
    return zz_retVal[0];
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
              int[] plPib) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { plLinkNo, plPib, zz_retVal };
    vtblInvoke("getLinkInfo", 35, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("6057df60-33f5-41f5-807c-3356e117d32c", com.interweave.plugin.a4comsv.IAccpacSvrDBLinkProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("close",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openView",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrView[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("createViewTables",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("dropViewTables",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionBegin",
            new Class[] { int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionCommit",
            new Class[] { int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionGetLevel",
            new Class[] { int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionRollback",
            new Class[] { int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("secCheck",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rscID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("access", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getFlags",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getProcessServerSetup",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.IID, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetupProxy.class) }),
        new com.linar.jintegra.MemberDesc("openViewExt",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.class, com.interweave.plugin.a4comsv.IAccpacSvrView[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 2, 4, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.IID, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openViewInstance",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrView[].class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Object.class, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("readonly", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unvalidated", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unrevisioned", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("nonheritable", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("prefetch", 22, 10, 8, null, null), 
              new com.linar.jintegra.Param("rawPut", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("noncascading", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("extra", 12, 10, 8, null, null), 
              new com.linar.jintegra.Param("processServer", 29, 10, 4, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.IID, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("cpGetInfo",
            new Class[] { byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("compInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("cpGetActiveApps",
            new Class[] { int[].class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("apps", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetFirstYear",
            new Class[] { String[].class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetLastYear",
            new Class[] { String[].class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetYear",
            new Class[] { String.class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetPeriod",
            new Class[] { java.util.Date.class, short[].class, String[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetPeriodDates",
            new Class[] { String.class, Short.TYPE, java.util.Date[].class, java.util.Date[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetQuarter",
            new Class[] { String.class, Short.TYPE, short[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("quarter", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetQuarterDates",
            new Class[] { String.class, Short.TYPE, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("quarter", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscGetYearDates",
            new Class[] { String.class, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscDatesFromPeriod",
            new Class[] { Short.TYPE, int.class, Short.TYPE, java.util.Date.class, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 4, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fscDateToPeriod",
            new Class[] { java.util.Date.class, int.class, Short.TYPE, java.util.Date.class, short[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("paramGet",
            new Class[] { String.class, Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exOpenViewInstance",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrView[].class, Integer.TYPE, Integer.TYPE, Object.class, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("openModes", 3, 10, 8, null, null), 
              new com.linar.jintegra.Param("openDirectives", 3, 10, 8, null, null), 
              new com.linar.jintegra.Param("openExtra", 12, 10, 8, null, null), 
              new com.linar.jintegra.Param("processServer", 29, 10, 4, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup.IID, com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("plError", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getLinkInfo",
            new Class[] { int[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("plLinkNo", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("plPib", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
