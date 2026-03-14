package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacDBLink'. Generated 02/10/2006 12:21:34 PM
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
public interface IAccpacDBLink extends java.io.Serializable {
  /**
   * close. Closes the DB Link.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * createViewTables. Creates the tables maintained and needed by the view.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * dropViewTables. Drops (deletes) tables created by CreateViewTables.
   *
   * @param     viewID The viewID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void dropViewTables  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSession. Returns the AccpacSession object from which this object was created.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacSession
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacSession getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionBegin. Begins a transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionBegin  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionCommit. Commits the transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionCommit  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionGetLevel. Returns the transaction level on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionGetLevel  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionRollback. Rolls back the transaction on the current DB Link.
   *
   * @param     pTransLevel The pTransLevel (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionRollback  (
              int[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getActiveApp. Returns an AccpacActiveApp object that contains a list of applications activated for the current company database.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacActiveApp
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacActiveApp getActiveApp  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCompany. Returns an AccpacCompany object that contains the company profile.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * secCheck. Checks the access rights of the current user on the specified security resource.
   *
   * @param     rscID The rscID (in)
   * @return    The access
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean secCheck  (
              String rscID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Returns the type of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFiscalCalendar. Returns the AccpacFiscalCalendar object.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar getFiscalCalendar  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object.
   *
   * @param     viewID The viewID (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup getProcessServerSetup  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4wcomex.IAccpacView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object extra) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionBegin2. Begins a transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionBegin2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionCommit2. Commits the transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionCommit2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionGetLevel2. Returns the transaction level on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionGetLevel2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * transactionRollback2. Rolls back the transaction on the current DB Link. Script languages should use this function.
   *
   * @param     pTransLevel A Variant (out: use single element array)
   * @return    The dbs_error
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int transactionRollback2  (
              Object[] pTransLevel) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] pExtErrInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDdff32bd3_aa35_11d2_9b9c_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacDBLinkProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "dff32bd3-aa35-11d2-9b9c-00104b71eb3f";
  String DISPID_1610743808_NAME = "close";
  String DISPID_1_NAME = "openView";
  String DISPID_2_NAME = "createViewTables";
  String DISPID_3_NAME = "dropViewTables";
  String DISPID_4_GET_NAME = "getSession";
  String DISPID_5_NAME = "transactionBegin";
  String DISPID_6_NAME = "transactionCommit";
  String DISPID_7_NAME = "transactionGetLevel";
  String DISPID_8_NAME = "transactionRollback";
  String DISPID_9_NAME = "getActiveApp";
  String DISPID_10_NAME = "getCompany";
  String DISPID_11_NAME = "secCheck";
  String DISPID_12_GET_NAME = "getType";
  String DISPID_13_GET_NAME = "getFlags";
  String DISPID_18_NAME = "getFiscalCalendar";
  String DISPID_20_NAME = "getProcessServerSetup";
  String DISPID_22_NAME = "openViewExt";
  String DISPID_24_NAME = "openViewInstance";
  String DISPID_26_NAME = "paramGet";
  String DISPID_27_NAME = "openViewInstance2";
  String DISPID_28_NAME = "transactionBegin2";
  String DISPID_29_NAME = "transactionCommit2";
  String DISPID_30_NAME = "transactionGetLevel2";
  String DISPID_31_NAME = "transactionRollback2";
  String DISPID_32_NAME = "openView2";
}
