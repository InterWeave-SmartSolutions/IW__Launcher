package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacDBLink'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC database link interface</B>'
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
public class IAccpacDBLinkProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacDBLink, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacDBLink.class;

  public IAccpacDBLinkProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacDBLink.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacDBLinkProxy() {}

  public IAccpacDBLinkProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacDBLink.IID);
  }

  protected IAccpacDBLinkProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacDBLinkProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * @param     ppView An reference to a com.interweave.plugin.a4wcomex.IAccpacView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openView  (
              String viewID,
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException{
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
   * getSession. Returns the AccpacSession object from which this object was created.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacSession zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSession", 11, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacSession)zz_retVal[0];
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
    vtblInvoke("transactionBegin", 12, zz_parameters);
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
    vtblInvoke("transactionCommit", 13, zz_parameters);
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
    vtblInvoke("transactionGetLevel", 14, zz_parameters);
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
    vtblInvoke("transactionRollback", 15, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getActiveApp. Returns an AccpacActiveApp object that contains a list of applications activated for the current company database.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacActiveApp
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacActiveApp getActiveApp  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacActiveApp zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getActiveApp", 16, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacActiveApp)zz_retVal[0];
  }

  /**
   * getCompany. Returns an AccpacCompany object that contains the company profile.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacCompany zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCompany", 17, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacCompany)zz_retVal[0];
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
    vtblInvoke("secCheck", 18, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getType. Returns the type of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getType", 19, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFlags", 20, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getFiscalCalendar. Returns the AccpacFiscalCalendar object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar getFiscalCalendar  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFiscalCalendar", 21, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar)zz_retVal[0];
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
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, zz_retVal };
    vtblInvoke("getProcessServerSetup", 22, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup)zz_retVal[0];
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
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, pVal, ppView, zz_retVal };
    vtblInvoke("openViewExt", 23, zz_parameters);
    return zz_retVal[0];
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
              com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { viewID, ppView, new Boolean(readonly), new Boolean(unvalidated), new Boolean(unrevisioned), new Boolean(nonheritable), new Integer(prefetch), new Boolean(rawPut), new Boolean(noncascading), extra == null ? new Variant("extra", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : extra, processServer, zz_retVal };
    vtblInvoke("openViewInstance", 24, zz_parameters);
    return zz_retVal[0];
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
              Object fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, zz_retVal };
    vtblInvoke("paramGet", 25, zz_parameters);
    return (Object)zz_retVal[0];
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
              Object extra) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacView zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, processServer, pExtErrInfo, new Boolean(readonly), new Boolean(unvalidated), new Boolean(unrevisioned), new Boolean(nonheritable), new Integer(prefetch), new Boolean(rawPut), new Boolean(noncascading), extra == null ? new Variant("extra", Variant.VT_ERROR, Variant.DISP_E_PARAMNOTFOUND) : extra, zz_retVal };
    vtblInvoke("openViewInstance2", 26, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacView)zz_retVal[0];
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
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionBegin2", 27, zz_parameters);
    return zz_retVal[0];
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
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionCommit2", 28, zz_parameters);
    return zz_retVal[0];
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
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionGetLevel2", 29, zz_parameters);
    return zz_retVal[0];
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
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { pTransLevel, zz_retVal };
    vtblInvoke("transactionRollback2", 30, zz_parameters);
    return zz_retVal[0];
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
              Object[] pExtErrInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacView zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, pExtErrInfo, zz_retVal };
    vtblInvoke("openView2", 31, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacView)zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("dff32bd3-aa35-11d2-9b9c-00104b71eb3f", com.interweave.plugin.a4wcomex.IAccpacDBLinkProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("close",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openView",
            new Class[] { String.class, com.interweave.plugin.a4wcomex.IAccpacView[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4wcomex.IAccpacView.IID, com.interweave.plugin.a4wcomex.IAccpacViewProxy.class), 
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
        new com.linar.jintegra.MemberDesc("getSession",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacSession.IID, com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class) }),
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
        new com.linar.jintegra.MemberDesc("getActiveApp",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppAccpacActiveApp", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacActiveApp.IID, com.interweave.plugin.a4wcomex.IAccpacActiveAppProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCompany",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppIAccpacCompany", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacCompany.IID, com.interweave.plugin.a4wcomex.IAccpacCompanyProxy.class) }),
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
        new com.linar.jintegra.MemberDesc("getFiscalCalendar",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("ppFiscalCalendar", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar.IID, com.interweave.plugin.a4wcomex.IAccpacFiscalCalendarProxy.class) }),
        new com.linar.jintegra.MemberDesc("getProcessServerSetup",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.IID, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class) }),
        new com.linar.jintegra.MemberDesc("openViewExt",
            new Class[] { String.class, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.class, com.interweave.plugin.a4wcomex.IAccpacView[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 2, 4, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.IID, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4wcomex.IAccpacView.IID, com.interweave.plugin.a4wcomex.IAccpacViewProxy.class), 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openViewInstance",
            new Class[] { String.class, com.interweave.plugin.a4wcomex.IAccpacView[].class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Object.class, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 16413, 4, 4, com.interweave.plugin.a4wcomex.IAccpacView.IID, com.interweave.plugin.a4wcomex.IAccpacViewProxy.class), 
              new com.linar.jintegra.Param("readonly", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unvalidated", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unrevisioned", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("nonheritable", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("prefetch", 22, 10, 8, null, null), 
              new com.linar.jintegra.Param("rawPut", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("noncascading", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("extra", 12, 10, 8, null, null), 
              new com.linar.jintegra.Param("processServer", 29, 10, 4, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.IID, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("paramGet",
            new Class[] { String.class, Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openViewInstance2",
            new Class[] { String.class, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.class, Object[].class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("processServer", 29, 10, 4, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.IID, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class), 
              new com.linar.jintegra.Param("pExtErrInfo", 16396, 12, 8, null, null), 
              new com.linar.jintegra.Param("readonly", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unvalidated", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("unrevisioned", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("nonheritable", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("prefetch", 22, 10, 8, null, null), 
              new com.linar.jintegra.Param("rawPut", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("noncascading", 11, 10, 8, null, null), 
              new com.linar.jintegra.Param("extra", 12, 10, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacView.IID, com.interweave.plugin.a4wcomex.IAccpacViewProxy.class) }),
        new com.linar.jintegra.MemberDesc("transactionBegin2",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionCommit2",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionGetLevel2",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("transactionRollback2",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pTransLevel", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("dbs_error", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openView2",
            new Class[] { String.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pExtErrInfo", 16396, 12, 8, null, null), 
              new com.linar.jintegra.Param("ppView", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacView.IID, com.interweave.plugin.a4wcomex.IAccpacViewProxy.class) }),
});  }
}
