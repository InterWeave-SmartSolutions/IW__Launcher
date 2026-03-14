package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacProcessServerSetup'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>DON'T USE THIS INTERFACE (Internal use only)</B>'
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
public interface IAccpacProcessServerSetup extends java.io.Serializable {
  /**
   * getProcessServerHostList. Get a list of possible hosts to handle job
   *
   * @param     hostNames The hostNames (out: use single element array)
   * @param     hostDescs The hostDescs (out: use single element array)
   * @param     flags The flags (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getProcessServerHostList  (
              String[][] hostNames,
              String[][] hostDescs,
              int[][] flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseProcessServer  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRunImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setRunImmediately  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledOnce  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setIsScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledOnce  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledDaily  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setIsScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledDaily  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSelectedHostName. Returns/sets name of selected host.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSelectedHostName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSelectedHostName. Returns/sets name of selected host.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSelectedHostName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getScheduleDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setScheduleDate  (
              java.util.Date pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isProcessServerOptional. Returns whether it is optional to use Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isProcessServerOptional  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewImmediatelyOnly. Returns whether jobs may only be run in Immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewImmediatelyOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewScheduleOnly. Returns whether jobs may only be run as a Scheduled job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewScheduleOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHostCount. Returns a count of number of hosts available for job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHostCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHostDetail. Returns detail about currently selected host.
   *
   * @param     hostDesc The hostDesc (out: use single element array)
   * @param     hostAddr The hostAddr (out: use single element array)
   * @return    The portNo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHostDetail  (
              String[] hostDesc,
              String[] hostAddr) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isHostMaySchedule. Returns whether selected Host may run job as a scheduled process.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMaySchedule  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isHostMayDoImmediately. Returns whether selected Host may run job in immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMayDoImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getJobComment. Returns/sets comment for Process Server job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getJobComment  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setJobComment. Returns/sets comment for Process Server job.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setJobComment  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * beginCall. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     bstrViewCall The bstrViewCall (in)
   * @return    The __MIDL_0015
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date beginCall  (
              String bstrViewCall) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * endCall. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     bstrViewCall The bstrViewCall (in)
   * @param     pVal The pVal (out: use single element array)
   * @return    The __MIDL_0016
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date endCall  (
              String bstrViewCall,
              String[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getClientComputerName. Returns client computer name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientComputerName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isReportType. Returns whether a job is a report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isReportType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewType. Returns whether a job is a program.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRemoteSession. Returns whether the session is remote.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemoteSession  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID256b2cc0_e109_11d4_a876_00105a0af272 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacProcessServerSetupProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "256b2cc0-e109-11d4-a876-00105a0af272";
  String DISPID_1_NAME = "getProcessServerHostList";
  String DISPID_2_GET_NAME = "isUseProcessServer";
  String DISPID_2_PUT_NAME = "setUseProcessServer";
  String DISPID_3_GET_NAME = "isRunImmediately";
  String DISPID_3_PUT_NAME = "setRunImmediately";
  String DISPID_4_GET_NAME = "isScheduledOnce";
  String DISPID_4_PUT_NAME = "setIsScheduledOnce";
  String DISPID_5_GET_NAME = "isScheduledDaily";
  String DISPID_5_PUT_NAME = "setIsScheduledDaily";
  String DISPID_6_GET_NAME = "getSelectedHostName";
  String DISPID_6_PUT_NAME = "setSelectedHostName";
  String DISPID_7_GET_NAME = "getScheduleDate";
  String DISPID_7_PUT_NAME = "setScheduleDate";
  String DISPID_8_GET_NAME = "isProcessServerOptional";
  String DISPID_9_GET_NAME = "isViewImmediatelyOnly";
  String DISPID_10_GET_NAME = "isViewScheduleOnly";
  String DISPID_11_GET_NAME = "getHostCount";
  String DISPID_12_NAME = "getHostDetail";
  String DISPID_13_GET_NAME = "isHostMaySchedule";
  String DISPID_14_GET_NAME = "isHostMayDoImmediately";
  String DISPID_15_GET_NAME = "getJobComment";
  String DISPID_15_PUT_NAME = "setJobComment";
  String DISPID_16_NAME = "beginCall";
  String DISPID_17_NAME = "endCall";
  String DISPID_18_GET_NAME = "getClientComputerName";
  String DISPID_19_GET_NAME = "isReportType";
  String DISPID_20_GET_NAME = "isViewType";
  String DISPID_21_GET_NAME = "isRemoteSession";
}
