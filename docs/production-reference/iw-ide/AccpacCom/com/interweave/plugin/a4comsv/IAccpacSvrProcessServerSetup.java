package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrProcessServerSetup'. Generated 12/10/2006 12:40:15 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
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
public interface IAccpacSvrProcessServerSetup extends java.io.Serializable {
  /**
   * getProcessServerHostList. DON'T USE THIS METHOD.  (Internal method)
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
   * isUseProcessServer. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setUseProcessServer. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseProcessServer  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRunImmediately. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRunImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setRunImmediately. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setRunImmediately  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isScheduledOnce. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledOnce  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setIsScheduledOnce. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledOnce  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isScheduledDaily. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledDaily  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setIsScheduledDaily. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledDaily  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSelectedHostName. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSelectedHostName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSelectedHostName. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSelectedHostName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getScheduleDate. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getScheduleDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setScheduleDate. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setScheduleDate  (
              java.util.Date pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isProcessServerOptional. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isProcessServerOptional  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewImmediatelyOnly. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewImmediatelyOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewScheduleOnly. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewScheduleOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHostCount. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHostCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHostDetail. DON'T USE THIS METHOD.  (Internal method)
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
   * isHostMaySchedule. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMaySchedule  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isHostMayDoImmediately. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMayDoImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getJobComment. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getJobComment  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setJobComment. DON'T USE THIS PROPERTY.  (Internal property)
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
   * getClientComputerName. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientComputerName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isReportType. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isReportType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isViewType. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRemoteSession. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemoteSession  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID4c290216_37b5_4b8a_bb80_92673c9642e9 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrProcessServerSetupProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "4c290216-37b5-4b8a-bb80-92673c9642e9";
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
