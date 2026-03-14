package com.interweave.plugin.A4WCOM;

import com.linar.jintegra.*;

/**
 * COM Class 'xapiSession'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>xapiSession Class</B>'
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
public class xapiSession implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.A4WCOM.IxapiSession2 {

  private static final String CLSID = "975d7ad3-8871-11d1-b5a5-0060083b07c8";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.A4WCOM.IxapiSession2Proxy d_IxapiSession2Proxy = null;

  /** Access this COM class's com.interweave.plugin.A4WCOM.IxapiSession2 interface */
  public com.interweave.plugin.A4WCOM.IxapiSession2 getAsIxapiSession2() { return d_IxapiSession2Proxy; }

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
  public static xapiSession getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new xapiSession(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static xapiSession bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new xapiSession(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IxapiSession2Proxy; }

  /**
   * Constructs a xapiSession on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public xapiSession() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a xapiSession on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public xapiSession(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IxapiSession2Proxy = new com.interweave.plugin.A4WCOM.IxapiSession2Proxy(CLSID, host, null);
  }

  /**
   * Construct a xapiSession using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public xapiSession(Object obj) throws java.io.IOException {
    d_IxapiSession2Proxy = new com.interweave.plugin.A4WCOM.IxapiSession2Proxy(obj);
  }

  /**
   * Release a xapiSession.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IxapiSession2Proxy);
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
      return d_IxapiSession2Proxy.getPropertyByName(name);
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
      return d_IxapiSession2Proxy.getPropertyByName(name, rhs);
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
    return d_IxapiSession2Proxy.invokeMethodByName(name, parameters);
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
    return d_IxapiSession2Proxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getSession. Session object
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiSession2
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiSession2 getSession  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getSession();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSessionDate. property SessionDate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getSessionDate  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getSessionDate();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getUser. property User
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getUser  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getUser();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDataBase. property Database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDataBase  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getDataBase();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * close. method closes the session
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiSession2Proxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * open. method establishes an XAPI session
   *
   * @param     userIdentifier The userIdentifier (in)
   * @param     password The password (in)
   * @param     dataBase The dataBase (in)
   * @param     sessionDate The sessionDate (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void open  (
              String userIdentifier,
              String password,
              String dataBase,
              java.util.Date sessionDate,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiSession2Proxy.open(userIdentifier,password,dataBase,sessionDate,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openView. method creates new instance of view object
   *
   * @param     viewId The viewId (in)
   * @param     programId The programId (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiView openView  (
              String viewId,
              String programId) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.openView(viewId,programId);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCompany. Company profile object
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getCompany();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCurrency. Currency format information object
   *
   * @param     currencyCode The currencyCode (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrency
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrency getCurrency  (
              String currencyCode) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getCurrency(currencyCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCurrencyTable. Currency table information object
   *
   * @param     curCode The curCode (in)
   * @param     rateType The rateType (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyTable
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyTable getCurrencyTable  (
              String curCode,
              String rateType) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getCurrencyTable(curCode,rateType);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCurrencyRateType. Currency rate type information object
   *
   * @param     rateTypeCode The rateTypeCode (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyRateType
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyRateType getCurrencyRateType  (
              String rateTypeCode) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getCurrencyRateType(rateTypeCode);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCurrencyRate. Currency rate  information object
   *
   * @param     homeCurrencyCode The homeCurrencyCode (in)
   * @param     rateType The rateType (in)
   * @param     sourceCurrencyCode The sourceCurrencyCode (in)
   * @param     date The date (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCurrencyRate
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCurrencyRate getCurrencyRate  (
              String homeCurrencyCode,
              String rateType,
              String sourceCurrencyCode,
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getCurrencyRate(homeCurrencyCode,rateType,sourceCurrencyCode,date);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getActiveApplications. Collection of active applications
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiActiveApplications
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiActiveApplications getActiveApplications  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getActiveApplications();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getErrors. Collection of errors
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiErrors
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiErrors getErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.getErrors();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * clearErrors. Clears the error stack, frees any memory being used by the error module
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clearErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiSession2Proxy.clearErrors();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * isWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isWarningIsException  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.isWarningIsException();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setWarningIsException  (
              boolean rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IxapiSession2Proxy.setWarningIsException(rhs1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * licenseStatus. Checks whether the specified license file is installed in the system. AppVersion can be passed an empty string in which case, the current System Manager version will be assumed.LICENSE_OK= 0,LICENSE_NOTFOUND= -1,LICENSE_EXPIRED= -2
   *
   * @param     appID The appID (in)
   * @param     appVersion The appVersion (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short licenseStatus  (
              String appID,
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.licenseStatus(appID,appVersion);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * openViewEx. method establishes an XAPI session with extended flag parameters
   *
   * @param     viewId The viewId (in)
   * @param     programId The programId (in)
   * @param     flags The flags (in)
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiView openViewEx  (
              String viewId,
              String programId,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IxapiSession2Proxy.openViewEx(viewId,programId,flags);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
