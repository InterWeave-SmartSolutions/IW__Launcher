package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacPrintSetup'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Local printer setup interface</B>'
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
public interface IAccpacPrintSetup extends java.io.Serializable {
  /**
   * getDeviceName. Returns/sets the name of the printer (device).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDeviceName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setDeviceName. Returns/sets the name of the printer (device).
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDeviceName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDriverName. Returns/sets the filename of the device driver.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDriverName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setDriverName. Returns/sets the filename of the device driver.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDriverName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOutputName. Returns/sets the device name for the physical output medium (i.e. LPT1:).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getOutputName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setOutputName. Returns/sets the device name for the physical output medium (i.e. LPT1:).
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOutputName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPaperSize. property PaperSize
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPaperSize  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setPaperSize. property PaperSize
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPaperSize  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOrientation. property Orientation
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getOrientation  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setOrientation. property Orientation
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrientation  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPaperSource. property PaperSource
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPaperSource  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setPaperSource. property PaperSource
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPaperSource  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * query. Shows the printer setup dialog.  Returns whether or not any printer setup information has changed.
   *
   * @param     hWnd The hWnd (in)
   * @return    The setupChanged
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean query  (
              int hWnd) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * save. Saves the printer setup information for the current user.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void save  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDestination. Returns/sets the default print destination for ACCPAC reports.
   *
   * @return    A com.interweave.proxy.tagPrintDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setDestination. Returns/sets the default print destination for ACCPAC reports.
   *
   * @param     pVal A com.interweave.proxy.tagPrintDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPrintDir  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPrintDir  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isDuplex. Returns/sets duplex printing.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isDuplex  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setDuplex. Returns/sets duplex printing.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDuplex  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID51d64206_a6d4_4ef6_ab17_f39146e87238 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacPrintSetupProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "51d64206-a6d4-4ef6-ab17-f39146e87238";
  String DISPID_1_GET_NAME = "getDeviceName";
  String DISPID_1_PUT_NAME = "setDeviceName";
  String DISPID_2_GET_NAME = "getDriverName";
  String DISPID_2_PUT_NAME = "setDriverName";
  String DISPID_3_GET_NAME = "getOutputName";
  String DISPID_3_PUT_NAME = "setOutputName";
  String DISPID_4_GET_NAME = "getPaperSize";
  String DISPID_4_PUT_NAME = "setPaperSize";
  String DISPID_5_GET_NAME = "getOrientation";
  String DISPID_5_PUT_NAME = "setOrientation";
  String DISPID_6_GET_NAME = "getPaperSource";
  String DISPID_6_PUT_NAME = "setPaperSource";
  String DISPID_7_NAME = "query";
  String DISPID_8_NAME = "save";
  String DISPID_9_GET_NAME = "getDestination";
  String DISPID_9_PUT_NAME = "setDestination";
  String DISPID_10_GET_NAME = "getPrintDir";
  String DISPID_10_PUT_NAME = "setPrintDir";
  String DISPID_11_GET_NAME = "isDuplex";
  String DISPID_11_PUT_NAME = "setDuplex";
}
