package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSvrReport'. Generated 12/10/2006 12:40:14 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC report printing class</B>'
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
public class AccpacSvrReport implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4comsv.IAccpacSvrReport {

  private static final String CLSID = "2ae7e049-79d0-4006-a973-135a11141058";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4comsv.IAccpacSvrReportProxy d_IAccpacSvrReportProxy = null;

  /** Access this COM class's com.interweave.plugin.a4comsv.IAccpacSvrReport interface */
  public com.interweave.plugin.a4comsv.IAccpacSvrReport getAsIAccpacSvrReport() { return d_IAccpacSvrReportProxy; }

  /** Compare this object with another */
  public boolean equals(Object o) { 
	if(java.beans.Beans.isDesignTime()) return super.equals(o);
	else return getJintegraDispatch() == null ? false : getJintegraDispatch().equals(o);
  }


  /** the hashcode for this object */
  public int hashCode() { return getJintegraDispatch() == null ? 0 : getJintegraDispatch().hashCode(); }

   /**
   * getActiveObject. Get a reference to a running instance of this class on the current machine using native code.
   *                  <B>Uses native code (See GetActiveObject() in MS doc) and thus can only be used on MS Windows</B>
   *
   * @return    A reference to the running object.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrReport getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrReport(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSvrReport bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSvrReport(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSvrReportProxy; }

  /**
   * Constructs a AccpacSvrReport on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrReport() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSvrReport on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSvrReport(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSvrReportProxy = new com.interweave.plugin.a4comsv.IAccpacSvrReportProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSvrReport using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSvrReport(Object obj) throws java.io.IOException {
    d_IAccpacSvrReportProxy = new com.interweave.plugin.a4comsv.IAccpacSvrReportProxy(obj);
  }

  /**
   * Release a AccpacSvrReport.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSvrReportProxy);
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
    try {
      return d_IAccpacSvrReportProxy.getPropertyByName(name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs A parameter used when getting the proxy.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getPropertyByName(name, rhs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @param     parameters One element for each parameter. Use primitive type wrappers.
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacSvrReportProxy.invokeMethodByName(name, parameters);
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacSvrReportProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getName. Returns the name of the report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              String paramValue) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.setParam(paramName,paramValue);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

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
              String[] webReportURL) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.printReport(pWebReportGenerated,webReportURL);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              int hWnd) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.confirm(showDialog,hWnd);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDestination. Returns/sets the report's print destination.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrPrintDestinationEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDestination  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getDestination();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setDestination. Returns/sets the report's print destination.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrPrintDestinationEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDestination  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.setDestination(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNumOfCopies  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getNumOfCopies();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setNumOfCopies. Returns/sets the number of copies to print, if the destination is the printer.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setNumOfCopies  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.setNumOfCopies(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPrintDir  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getPrintDir();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setPrintDir. Returns/sets the print directory, if the destination is a file.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setPrintDir  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.setPrintDir(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getWebReportURL. Returns the URL for the generated web-based report, if PrintReport returned True.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getWebReportURL  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getWebReportURL();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * printerSetup. Sets the printer characteristics for subsequent printing.
   *
   * @param     pSetup An reference to a com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printerSetup  (
              com.interweave.plugin.a4comsv.IAccpacSvrPrintSetup pSetup) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.printerSetup(pSetup);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRequiresProcessServerSettings. Returns whether or not the report must be configured for the Process Server before printing it.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRequiresProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.isRequiresProcessServerSettings();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProcessServerSetup. Returns the AccpacProcessServerSetup object for report.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrProcessServerSetup getProcessServerSetup  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getProcessServerSetup();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * completeProcessServerSettings. Saves Process Server settings for the report before printing it.
   *
   * @return    The plExtErrInfo
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int completeProcessServerSettings  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.completeProcessServerSettings();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isUseProcessServer. Returns whether the Process Server is being used to print the report or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.isUseProcessServer();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

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
              boolean bRequiresPS) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.init(session,reportName,programID,menuID,bRequiresPS);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

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
              byte[][] printSettings) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.initExt(session,sessionInt,reportName,programID,menuID,bRequiresPS,printSettings);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isCollate. property Collate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCollate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.isCollate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setCollate. property Collate
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCollate  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.setCollate(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getFormat. Returns/sets the report's email format.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrPrintFormatEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFormat  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.getFormat();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setFormat. Returns/sets the report's email format.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrPrintFormatEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setFormat  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.setFormat(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * reInit. Reinitializes Report Object so that new report may be printed.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reInit  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.reInit();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * msgrConnect. Connects to ACCPAC Messenger.
   *
   * @param     bShowDialog The bShowDialog (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean msgrConnect  (
              boolean bShowDialog) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSvrReportProxy.msgrConnect(bShowDialog);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * msgrDisconnect. Disconnects from ACCPAC Messenger.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void msgrDisconnect  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSvrReportProxy.msgrDisconnect();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
