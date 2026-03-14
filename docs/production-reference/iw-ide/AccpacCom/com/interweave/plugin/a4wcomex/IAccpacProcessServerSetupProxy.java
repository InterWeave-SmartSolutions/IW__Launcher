package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacProcessServerSetup'. Generated 02/10/2006 12:21:34 PM
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
public class IAccpacProcessServerSetupProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacProcessServerSetup, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacProcessServerSetup.class;

  public IAccpacProcessServerSetupProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacProcessServerSetup.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacProcessServerSetupProxy() {}

  public IAccpacProcessServerSetupProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacProcessServerSetup.IID);
  }

  protected IAccpacProcessServerSetupProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacProcessServerSetupProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
              int[][] flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { hostNames, hostDescs, flags, zz_retVal };
    vtblInvoke("getProcessServerHostList", 7, zz_parameters);
    return;
  }

  /**
   * isUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseProcessServer  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isUseProcessServer", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setUseProcessServer. Returns/sets whether jobs is to be done by Process Server.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseProcessServer  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setUseProcessServer", 9, zz_parameters);
    return;
  }

  /**
   * isRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRunImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRunImmediately", 10, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setRunImmediately. Returns/set whether job is to be run immediately.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setRunImmediately  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setRunImmediately", 11, zz_parameters);
    return;
  }

  /**
   * isScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledOnce  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isScheduledOnce", 12, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setIsScheduledOnce. Returns/set whether job is to be schedule to run once.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledOnce  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setIsScheduledOnce", 13, zz_parameters);
    return;
  }

  /**
   * isScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isScheduledDaily  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isScheduledDaily", 14, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setIsScheduledDaily. Returns/set whether job is to be schedule to run daily.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setIsScheduledDaily  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setIsScheduledDaily", 15, zz_parameters);
    return;
  }

  /**
   * getSelectedHostName. Returns/sets name of selected host.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSelectedHostName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSelectedHostName", 16, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setSelectedHostName. Returns/sets name of selected host.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSelectedHostName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setSelectedHostName", 17, zz_parameters);
    return;
  }

  /**
   * getScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getScheduleDate  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getScheduleDate", 18, zz_parameters);
    return (java.util.Date)zz_retVal[0];
  }

  /**
   * setScheduleDate. Returns/set date job is to be scheduled for.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setScheduleDate  (
              java.util.Date pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setScheduleDate", 19, zz_parameters);
    return;
  }

  /**
   * isProcessServerOptional. Returns whether it is optional to use Process Server.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isProcessServerOptional  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isProcessServerOptional", 20, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isViewImmediatelyOnly. Returns whether jobs may only be run in Immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewImmediatelyOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isViewImmediatelyOnly", 21, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isViewScheduleOnly. Returns whether jobs may only be run as a Scheduled job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewScheduleOnly  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isViewScheduleOnly", 22, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getHostCount. Returns a count of number of hosts available for job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getHostCount  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getHostCount", 23, zz_parameters);
    return zz_retVal[0];
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
              String[] hostAddr) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { hostDesc, hostAddr, zz_retVal };
    vtblInvoke("getHostDetail", 24, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isHostMaySchedule. Returns whether selected Host may run job as a scheduled process.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMaySchedule  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isHostMaySchedule", 25, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isHostMayDoImmediately. Returns whether selected Host may run job in immediate mode.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isHostMayDoImmediately  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isHostMayDoImmediately", 26, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getJobComment. Returns/sets comment for Process Server job.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getJobComment  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getJobComment", 27, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * setJobComment. Returns/sets comment for Process Server job.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setJobComment  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("setJobComment", 28, zz_parameters);
    return;
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
              String bstrViewCall) throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { bstrViewCall, zz_retVal };
    vtblInvoke("beginCall", 29, zz_parameters);
    return (java.util.Date)zz_retVal[0];
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
              String[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { bstrViewCall, pVal, zz_retVal };
    vtblInvoke("endCall", 30, zz_parameters);
    return (java.util.Date)zz_retVal[0];
  }

  /**
   * getClientComputerName. Returns client computer name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getClientComputerName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getClientComputerName", 31, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isReportType. Returns whether a job is a report.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isReportType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isReportType", 32, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isViewType. Returns whether a job is a program.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isViewType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isViewType", 33, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isRemoteSession. Returns whether the session is remote.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isRemoteSession  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isRemoteSession", 34, zz_parameters);
    return zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("256b2cc0-e109-11d4-a876-00105a0af272", com.interweave.plugin.a4wcomex.IAccpacProcessServerSetupProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getProcessServerHostList",
            new Class[] { String[][].class, String[][].class, int[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("hostNames", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("hostDescs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("flags", 16387, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isUseProcessServer",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setUseProcessServer",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isRunImmediately",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setRunImmediately",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isScheduledOnce",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setIsScheduledOnce",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isScheduledDaily",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setIsScheduledDaily",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSelectedHostName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setSelectedHostName",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getScheduleDate",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setScheduleDate",
            new Class[] { java.util.Date.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isProcessServerOptional",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isViewImmediatelyOnly",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isViewScheduleOnly",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getHostCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getHostDetail",
            new Class[] { String[].class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("hostDesc", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("hostAddr", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("portNo", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isHostMaySchedule",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isHostMayDoImmediately",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getJobComment",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setJobComment",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("beginCall",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrViewCall", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("__MIDL_0015", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("endCall",
            new Class[] { String.class, String[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bstrViewCall", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16392, 4, 8, null, null), 
              new com.linar.jintegra.Param("__MIDL_0016", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientComputerName",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isReportType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isViewType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isRemoteSession",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
});  }
}
