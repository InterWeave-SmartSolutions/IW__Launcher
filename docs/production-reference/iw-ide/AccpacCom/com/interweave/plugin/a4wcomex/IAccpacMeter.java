package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacMeter'. Generated 02/10/2006 12:21:34 PM
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
public interface IAccpacMeter extends java.io.Serializable {
  /**
   * isRunning. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRunning  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLabel. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLabel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPercent. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPercent  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCaption. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCaption  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isShowGauge. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isShowGauge  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isShowCancel. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isShowCancel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * cancel. DON'T USE THIS METHOD.  (Internal method)
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCurrentStatus. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     isRunning The isRunning (out: use single element array)
   * @param     pLabel The pLabel (out: use single element array)
   * @param     pPercent The pPercent (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCurrentStatus  (
              boolean[] isRunning,
              String[] pLabel,
              int[] pPercent) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID5e4aa420_1a23_11d5_a8c7_00105a0af272 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacMeterProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "5e4aa420-1a23-11d5-a8c7-00105a0af272";
  String DISPID_1_GET_NAME = "isRunning";
  String DISPID_2_GET_NAME = "getLabel";
  String DISPID_3_GET_NAME = "getPercent";
  String DISPID_4_GET_NAME = "getCaption";
  String DISPID_5_GET_NAME = "isShowGauge";
  String DISPID_6_GET_NAME = "isShowCancel";
  String DISPID_7_NAME = "cancel";
  String DISPID_8_NAME = "getCurrentStatus";
}
