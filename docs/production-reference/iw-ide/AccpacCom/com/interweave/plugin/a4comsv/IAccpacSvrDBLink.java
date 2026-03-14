package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrDBLink'. Generated 12/10/2006 12:40:14 PM
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
public interface IAccpacSvrDBLink extends java.io.Serializable {
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
   * @param     ppView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int openView  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFlags. Returns the flags of the DB Link.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrDBLinkFlagsEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFlags  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object.
   *
   * @param     viewID The viewID (in)
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup getProcessServerSetup  (
              String viewID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4comsv.IAccpacSvrView[] ppView) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * cpGetInfo. 
   *
   * @param     compInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cpGetInfo  (
              byte[][] compInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] apps) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              short[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              short[] period) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object fieldIDs) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup processServer) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] plPib) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID6057df60_33f5_41f5_807c_3356e117d32c = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrDBLinkProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "6057df60-33f5-41f5-807c-3356e117d32c";
  String DISPID_1610743808_NAME = "close";
  String DISPID_1_NAME = "openView";
  String DISPID_2_NAME = "createViewTables";
  String DISPID_3_NAME = "dropViewTables";
  String DISPID_5_NAME = "transactionBegin";
  String DISPID_6_NAME = "transactionCommit";
  String DISPID_7_NAME = "transactionGetLevel";
  String DISPID_8_NAME = "transactionRollback";
  String DISPID_11_NAME = "secCheck";
  String DISPID_12_GET_NAME = "getType";
  String DISPID_13_GET_NAME = "getFlags";
  String DISPID_20_NAME = "getProcessServerSetup";
  String DISPID_22_NAME = "openViewExt";
  String DISPID_24_NAME = "openViewInstance";
  String DISPID_26_NAME = "cpGetInfo";
  String DISPID_27_NAME = "cpGetActiveApps";
  String DISPID_28_NAME = "fscGetFirstYear";
  String DISPID_29_NAME = "fscGetLastYear";
  String DISPID_30_NAME = "fscGetYear";
  String DISPID_31_NAME = "fscGetPeriod";
  String DISPID_32_NAME = "fscGetPeriodDates";
  String DISPID_33_NAME = "fscGetQuarter";
  String DISPID_34_NAME = "fscGetQuarterDates";
  String DISPID_35_NAME = "fscGetYearDates";
  String DISPID_36_NAME = "fscDatesFromPeriod";
  String DISPID_37_NAME = "fscDateToPeriod";
  String DISPID_38_NAME = "paramGet";
  String DISPID_39_NAME = "exOpenViewInstance";
  String DISPID_40_NAME = "getLinkInfo";
}
