package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrReport'. Generated 12/10/2006 12:40:15 PM
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
public interface IAccpacSvrReport extends java.io.Serializable {
  /**
   * getName. Returns the name of the report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setParam. Sets a parameter for the report.  Returns whether the parameter was set successfully or not.
   *
   * @param     paramName The paramName (in)
   * @param     paramValue The paramValue (in)
   * @return    The success
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean setParam  (
              String paramName,
              String paramValue) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * printReport. Prints the report to the appropriate destination.  Returns whether a web-based report was generated or not.
   *
   * @param     pWebReportGenerated The pWebReportGenerated (out: use single element array)
   * @param     webReportURL The webReportURL (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printReport  (
              boolean[] pWebReportGenerated,
              String[] webReportURL) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * confirm. Shows a confirmation box if ShowDialog is True and if the user's preferences say to do so.  Returns whether printing should continue or not.
   *
   * @param     showDialog The showDialog (in)
   * @param     hWnd The hWnd (in)
   * @return    The confirmed
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean confirm  (
              boolean showDialog,
              int hWnd) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDestination. Returns/sets the report's print destination.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrPrintDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setDestination. Returns/sets the report's print destination.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrPrintDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNumOfCopies  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setNumOfCopies  (
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
   * getWebReportURL. Returns the URL for the generated web-based report, if PrintReport returned True.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getWebReportURL  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * printerSetup. Sets the printer characteristics for subsequent printing.
   *
   * @param     pSetup An reference to a com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printerSetup  (
              com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup pSetup) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isRequiresProcessServerSettings. Returns whether or not the report must be configured for the Process Server before printing it.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRequiresProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object for report.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup getProcessServerSetup  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * completeProcessServerSettings. Saves Process Server settings for the report before printing it.
   *
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int completeProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isUseProcessServer. Returns whether the Process Server is being used to print the report or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * init. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     session An reference to a com.interweave.plugin.a4comsv.IAccpacSvrSession (in)
   * @param     reportName The reportName (in)
   * @param     programID The programID (in)
   * @param     menuID The menuID (in)
   * @param     bRequiresPS The bRequiresPS (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  (
              com.interweave.plugin.a4comsv.IAccpacSvrSession session,
              String reportName,
              int programID,
              int menuID,
              boolean bRequiresPS) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * initExt. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     session An reference to a com.interweave.plugin.a4comsv.IAccpacSvrSession (in)
   * @param     sessionInt An reference to a com.interweave.plugin.a4comsv.IAccpacSvrSessionInt (in)
   * @param     reportName The reportName (in)
   * @param     programID The programID (in)
   * @param     menuID The menuID (in)
   * @param     bRequiresPS The bRequiresPS (in)
   * @param     printSettings An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initExt  (
              com.interweave.plugin.a4comsv.IAccpacSvrSession session,
              com.interweave.plugin.a4comsv.IAccpacSvrSessionInt sessionInt,
              String reportName,
              int programID,
              int menuID,
              boolean bRequiresPS,
              byte[][] printSettings) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isCollate. property Collate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCollate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setCollate. property Collate
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCollate  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFormat. Returns/sets the report's email format.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrPrintFormatEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFormat  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setFormat. Returns/sets the report's email format.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrPrintFormatEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFormat  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * reInit. Reinitializes Report Object so that new report may be printed.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reInit  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * msgrConnect. Connects to ACCPAC Messenger.
   *
   * @param     bShowDialog The bShowDialog (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrConnect  (
              boolean bShowDialog) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * msgrDisconnect. Disconnects from ACCPAC Messenger.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void msgrDisconnect  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID2834bbc9_3df1_46d4_a169_830f06106076 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrReportProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "2834bbc9-3df1-46d4-a169-830f06106076";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_NAME = "setParam";
  String DISPID_3_NAME = "printReport";
  String DISPID_4_NAME = "confirm";
  String DISPID_5_GET_NAME = "getDestination";
  String DISPID_5_PUT_NAME = "setDestination";
  String DISPID_6_GET_NAME = "getNumOfCopies";
  String DISPID_6_PUT_NAME = "setNumOfCopies";
  String DISPID_7_GET_NAME = "getPrintDir";
  String DISPID_7_PUT_NAME = "setPrintDir";
  String DISPID_8_GET_NAME = "getWebReportURL";
  String DISPID_9_NAME = "printerSetup";
  String DISPID_10_GET_NAME = "isRequiresProcessServerSettings";
  String DISPID_11_NAME = "getProcessServerSetup";
  String DISPID_13_NAME = "completeProcessServerSettings";
  String DISPID_14_GET_NAME = "isUseProcessServer";
  String DISPID_15_NAME = "init";
  String DISPID_16_NAME = "initExt";
  String DISPID_17_GET_NAME = "isCollate";
  String DISPID_17_PUT_NAME = "setCollate";
  String DISPID_18_GET_NAME = "getFormat";
  String DISPID_18_PUT_NAME = "setFormat";
  String DISPID_19_NAME = "reInit";
  String DISPID_20_NAME = "msgrConnect";
  String DISPID_21_NAME = "msgrDisconnect";
}
