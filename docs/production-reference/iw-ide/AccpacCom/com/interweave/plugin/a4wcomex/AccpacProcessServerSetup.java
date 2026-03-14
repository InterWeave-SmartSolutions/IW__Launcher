package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacProcessServerSetup'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>DON'T USE THIS CLASS (Internal use only)</B>'
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
public class AccpacProcessServerSetup implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup {

  private static final String CLSID = "a6788944-e0dd-11d4-a876-00105a0af272";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy d_IAccpacProcessServerSetupProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup interface */
  public com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup getAsIAccpacProcessServerSetup() { return d_IAccpacProcessServerSetupProxy; }

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
  public static AccpacProcessServerSetup getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacProcessServerSetup(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacProcessServerSetup bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacProcessServerSetup(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacProcessServerSetupProxy; }

  /**
   * Constructs a AccpacProcessServerSetup on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacProcessServerSetup() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacProcessServerSetup on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacProcessServerSetup(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacProcessServerSetupProxy = new com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacProcessServerSetup using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacProcessServerSetup(Object obj) throws java.io.IOException {
    d_IAccpacProcessServerSetupProxy = new com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy(obj);
  }

  /**
   * Release a AccpacProcessServerSetup.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacProcessServerSetupProxy);
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
      return d_IAccpacProcessServerSetupProxy.getPropertyByName(name);
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
      return d_IAccpacProcessServerSetupProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacProcessServerSetupProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacProcessServerSetupProxy.invokeMethodByName(name, new Object[]{});
  }

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
              int[][] flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.getProcessServerHostList(hostNames,hostDescs,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isUseProcessServer();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseProcessServer  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setUseProcessServer(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRunImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isRunImmediately();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setRunImmediately  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setRunImmediately(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledOnce  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isScheduledOnce();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setIsScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledOnce  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setIsScheduledOnce(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledDaily  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isScheduledDaily();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setIsScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledDaily  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setIsScheduledDaily(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSelectedHostName. Returns/sets name of selected host.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSelectedHostName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getSelectedHostName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSelectedHostName. Returns/sets name of selected host.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSelectedHostName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setSelectedHostName(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getScheduleDate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getScheduleDate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setScheduleDate  (
              java.util.Date pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setScheduleDate(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isProcessServerOptional. Returns whether it is optional to use Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isProcessServerOptional  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isProcessServerOptional();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isViewImmediatelyOnly. Returns whether jobs may only be run in Immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewImmediatelyOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isViewImmediatelyOnly();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isViewScheduleOnly. Returns whether jobs may only be run as a Scheduled job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewScheduleOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isViewScheduleOnly();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getHostCount. Returns a count of number of hosts available for job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHostCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getHostCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

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
              String[] hostAddr) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getHostDetail(hostDesc,hostAddr);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isHostMaySchedule. Returns whether selected Host may run job as a scheduled process.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMaySchedule  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isHostMaySchedule();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isHostMayDoImmediately. Returns whether selected Host may run job in immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMayDoImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isHostMayDoImmediately();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getJobComment. Returns/sets comment for Process Server job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getJobComment  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getJobComment();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setJobComment. Returns/sets comment for Process Server job.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setJobComment  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacProcessServerSetupProxy.setJobComment(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * beginCall. DON'T USE THIS METHOD.  (Internal method)
   *
   * @param     bstrViewCall The bstrViewCall (in)
   * @return    The __MIDL_0015
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date beginCall  (
              String bstrViewCall) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.beginCall(bstrViewCall);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

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
              String[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.endCall(bstrViewCall,pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getClientComputerName. Returns client computer name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientComputerName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.getClientComputerName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isReportType. Returns whether a job is a report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isReportType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isReportType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isViewType. Returns whether a job is a program.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isViewType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isRemoteSession. Returns whether the session is remote.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemoteSession  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacProcessServerSetupProxy.isRemoteSession();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
