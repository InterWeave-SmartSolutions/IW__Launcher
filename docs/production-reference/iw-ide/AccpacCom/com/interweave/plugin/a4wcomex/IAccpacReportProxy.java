package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacReport'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC report printing interface</B>'
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
public class IAccpacReportProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacReport, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacReport.class;

  public IAccpacReportProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacReport.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacReportProxy() {}

  public IAccpacReportProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacReport.IID);
  }

  protected IAccpacReportProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacReportProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getName. Returns the name of the report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getName", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

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
              String paramValue) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { paramName, paramValue, zz_retVal };
    vtblInvoke("setParam", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * printReport. Prints the report to the appropriate destination.  Returns whether a web-based report was generated or not.
   *
   * @return    The pWebReportGenerated
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean printReport  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("printReport", 9, zz_parameters);
    return zz_retVal[0];
  }

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
              int hWnd) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Boolean(showDialog), new Integer(hWnd), zz_retVal };
    vtblInvoke("confirm", 10, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getDestination. Returns/sets the report's print destination.
   *
   * @return    A com.interweave.proxy.tagPrintDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDestination", 11, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setDestination. Returns/sets the report's print destination.
   *
   * @param     pVal A com.interweave.proxy.tagPrintDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setDestination", 12, zz_parameters);
    return;
  }

  /**
   * getNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNumOfCopies  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getNumOfCopies", 13, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setNumOfCopies  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setNumOfCopies", 14, zz_parameters);
    return;
  }

  /**
   * getPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPrintDir  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getPrintDir", 15, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPrintDir  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setPrintDir", 16, zz_parameters);
    return;
  }

  /**
   * getWebReportURL. Returns the URL for the generated web-based report, if PrintReport returned True.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getWebReportURL  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getWebReportURL", 17, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * printerSetup. Sets the printer characteristics for subsequent printing.
   *
   * @param     pSetup An reference to a com.interweave.plugin.a4wcomex.IAccpacPrintSetup (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printerSetup  (
              com.interweave.plugin.a4wcomex.IAccpacPrintSetup pSetup) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pSetup, zz_retVal };
    vtblInvoke("printerSetup", 18, zz_parameters);
    return;
  }

  /**
   * isRequiresProcessServerSettings. Returns whether or not the report must be configured for the Process Server before printing it.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRequiresProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRequiresProcessServerSettings", 19, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object for report.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup getProcessServerSetup  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getProcessServerSetup", 20, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup)zz_retVal[0];
  }

  /**
   * completeProcessServerSettings. Saves Process Server settings for the report before printing it.
   *
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int completeProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("completeProcessServerSettings", 21, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isUseProcessServer. Returns whether the Process Server is being used to print the report or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isUseProcessServer", 22, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * init. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     session An reference to a com.interweave.plugin.a4wcomex.IAccpacSession (in)
   * @param     reportName The reportName (in)
   * @param     programID The programID (in)
   * @param     menuID The menuID (in)
   * @param     bRequiresPS The bRequiresPS (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  (
              com.interweave.plugin.a4wcomex.IAccpacSession session,
              String reportName,
              String programID,
              String menuID,
              boolean bRequiresPS) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { session, reportName, programID, menuID, new Boolean(bRequiresPS), zz_retVal };
    vtblInvoke("init", 23, zz_parameters);
    return;
  }

  /**
   * saveWebReportToLocal. Download a web report to a local file. The report is generated as the specified report type.
   *
   * @param     reportType A com.interweave.plugin.a4wcomex.tagReportOutputType constant (in)
   * @return    The localFileName
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String saveWebReportToLocal  (
              int reportType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(reportType), zz_retVal };
    vtblInvoke("saveWebReportToLocal", 24, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isCollate. Returns/sets collation when printing a report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCollate  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isCollate", 25, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setCollate. Returns/sets collation when printing a report.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCollate  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setCollate", 26, zz_parameters);
    return;
  }

  /**
   * getFormat. Returns/sets the report format, if destination is set to email.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagPrintFormatEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFormat  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFormat", 27, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setFormat. Returns/sets the report format, if destination is set to email.
   *
   * @param     pVal A com.interweave.plugin.a4wcomex.tagPrintFormatEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFormat  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setFormat", 28, zz_parameters);
    return;
  }

  /**
   * getOutputDestination. Returns/sets whether the report would be generated on the server or the client. This property applies when the client is connecting to a remote server and is ignored otherwise.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagOutputDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getOutputDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getOutputDestination", 29, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setOutputDestination. Returns/sets whether the report would be generated on the server or the client. This property applies when the client is connecting to a remote server and is ignored otherwise.
   *
   * @param     pVal A com.interweave.plugin.a4wcomex.tagOutputDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOutputDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setOutputDestination", 30, zz_parameters);
    return;
  }

  /**
   * reInit. Reinitialize Report Object so it may be used to print a new report.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reInit  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("reInit", 31, zz_parameters);
    return;
  }

  /**
   * msgrConnect. Connects to ACCPAC Messenger.
   *
   * @param     bShowDialog The bShowDialog (in)
   * @return    The bConnected
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrConnect  (
              boolean bShowDialog) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Boolean(bShowDialog), zz_retVal };
    vtblInvoke("msgrConnect", 32, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * msgrDisconnect. Disconnects from ACCPAC Messenger.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void msgrDisconnect  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("msgrDisconnect", 33, zz_parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("30648b17-0c67-4a74-9438-666529701465", com.interweave.plugin.a4wcomex.IAccpacReportProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setParam",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("paramName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("paramValue", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("success", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("printReport",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pWebReportGenerated", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("confirm",
            new Class[] { Boolean.TYPE, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("showDialog", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("hWnd", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("confirmed", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDestination",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("setDestination",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getNumOfCopies",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 22, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setNumOfCopies",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPrintDir",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setPrintDir",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getWebReportURL",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("printerSetup",
            new Class[] { com.interweave.plugin.a4wcomex.IAccpacPrintSetup.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pSetup", 29, 2, 4, com.interweave.plugin.a4wcomex.IAccpacPrintSetup.IID, com.interweave.plugin.a4wcomex.IAccpacPrintSetupProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isRequiresProcessServerSettings",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getProcessServerSetup",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup.IID, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class) }),
        new com.linar.jintegra.MemberDesc("completeProcessServerSettings",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("plExtErrInfo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isUseProcessServer",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("init",
            new Class[] { com.interweave.plugin.a4wcomex.IAccpacSession.class, String.class, String.class, String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("session", 29, 2, 4, com.interweave.plugin.a4wcomex.IAccpacSession.IID, com.interweave.plugin.a4wcomex.IAccpacSessionProxy.class), 
              new com.linar.jintegra.Param("reportName", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("menuID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("bRequiresPS", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("saveWebReportToLocal",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("reportType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("localFileName", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isCollate",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setCollate",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFormat",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("setFormat",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getOutputDestination",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("setOutputDestination",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("reInit",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("msgrConnect",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bShowDialog", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("bConnected", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("msgrDisconnect",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
});  }
}
