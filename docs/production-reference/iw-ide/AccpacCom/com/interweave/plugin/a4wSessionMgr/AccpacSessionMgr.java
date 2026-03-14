package com.interweave.plugin.a4wSessionMgr;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacSessionMgr'. Generated 18/10/2006 4:28:04 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wSessionMgr.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>ACCPAC session manager class</B>'
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
public class AccpacSessionMgr implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgr {

  private static final String CLSID = "bb37d786-4ca2-4fa0-a8c4-e85bc2523ea9";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgrProxy d_IAccpacSessionMgrProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgr interface */
  public com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgr getAsIAccpacSessionMgr() { return d_IAccpacSessionMgrProxy; }

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
  public static AccpacSessionMgr getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSessionMgr(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacSessionMgr bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacSessionMgr(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacSessionMgrProxy; }

  /**
   * Constructs a AccpacSessionMgr on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSessionMgr() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacSessionMgr on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacSessionMgr(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacSessionMgrProxy = new com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgrProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacSessionMgr using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacSessionMgr(Object obj) throws java.io.IOException {
    d_IAccpacSessionMgrProxy = new com.interweave.plugin.a4wSessionMgr.IAccpacSessionMgrProxy(obj);
  }

  /**
   * Release a AccpacSessionMgr.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacSessionMgrProxy);
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
      return d_IAccpacSessionMgrProxy.getPropertyByName(name);
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
      return d_IAccpacSessionMgrProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacSessionMgrProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacSessionMgrProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.getAppID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setAppID. Returns/sets the 2 character application ID (i.e. GL) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppID  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setAppID(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getProgramName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.getProgramName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setProgramName. Returns/sets the program name (Roto ID) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setProgramName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setProgramName(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAppVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.getAppVersion();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setAppVersion. Returns/sets the 3 character application version (i.e. 50A) associated with the session.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setAppVersion  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setAppVersion(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getServerName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.getServerName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setServerName. Returns/sets the name of the server on which the session is created.  If the session is local, the name is "".
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setServerName  (
              String pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setServerName(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * createSession. Gets a session from the session manager, given the object handle and initial signon ID.  The signon dialog box may be displayed.  Also retrieves the session's signon ID.
   *
   * @param     objectHandle The objectHandle (in)
   * @param     signonID The signonID (in/out: use single element array)
   * @param     session An reference to a  (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createSession  (
              String objectHandle,
              int[] signonID,
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.createSession(objectHandle,signonID,session);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isForceNewSignon  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.isForceNewSignon();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setForceNewSignon. Returns/sets whether or not to display the signon dialog box each time CreateSession is called.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setForceNewSignon  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setForceNewSignon(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * signoff. Signs off the session with the given signon ID.
   *
   * @param     signonID The signonID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void signoff  (
              int signonID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.signoff(signonID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * installLanguageDependencies. Installs (if needed) the given language's common, application-wide, and UI-specific resource DLLs.
   *
   * @param     language The language (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void installLanguageDependencies  (
              String language) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.installLanguageDependencies(language);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * createAccpacMeterCtrl. Creates a new client-side meter given the session and an initial title for the meter.  Returns the client-side meter (AccpacMeterCtrl) object.
   *
   * @param     pSession An reference to a  (in)
   * @param     meterName The meterName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createAccpacMeterCtrl  (
              Object pSession,
              String meterName) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.createAccpacMeterCtrl(pSession,meterName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAppResource. Returns the application-wide resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getAppResource(fallbackProgID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPgmResource. Returns the program-specific resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getPgmResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getPgmResource(fallbackProgID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCommonResource. Returns the system-wide common resource object.
   *
   * @param     fallbackProgID The fallbackProgID (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource  (
              String fallbackProgID) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getCommonResource(fallbackProgID);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAppResource2. Returns the application-wide resource object.
   *
   * @param     appPath The appPath (in)
   * @param     fallbackRscName The fallbackRscName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getAppResource2  (
              String appPath,
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getAppResource2(appPath,fallbackRscName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPgmResource2. Returns the application-wide resource object.
   *
   * @param     appPath The appPath (in)
   * @param     fallbackRscName The fallbackRscName (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getPgmResource2  (
              String appPath,
              String fallbackRscName) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getPgmResource2(appPath,fallbackRscName);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCommonResource2. Returns the application-wide resource object.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCommonResource2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.getCommonResource2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isCheckSessionStatus  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacSessionMgrProxy.isCheckSessionStatus();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setCheckSessionStatus. DON'T USE THIS PROPERTY.  (Internal property)
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setCheckSessionStatus  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.setCheckSessionStatus(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * createSessionEx. Like CreateSession, but made for web and common desktops (more sensitive to which layer pops up dialogs).
   *
   * @param     objectHandle The objectHandle (in)
   * @param     signonID The signonID (in/out: use single element array)
   * @param     session An reference to a  (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createSessionEx  (
              String objectHandle,
              int[] signonID,
              Object[] session) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacSessionMgrProxy.createSessionEx(objectHandle,signonID,session);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
