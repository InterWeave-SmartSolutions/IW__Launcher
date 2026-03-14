package com.interweave.plugin.A4WCOM;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IxapiSession'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiSession Interface</B>'
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
public class IxapiSessionProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.A4WCOM.IxapiSession, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.A4WCOM.JIntegraInit.init(); }

  public static final Class targetClass = IxapiSession.class;

  public IxapiSessionProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IxapiSession.IID, host, authInfo);
  }

  /** For internal use only */
  public IxapiSessionProxy() {}

  public IxapiSessionProxy(Object obj) throws java.io.IOException {
    super(obj, IxapiSession.IID);
  }

  protected IxapiSessionProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IxapiSessionProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * close. method closes the session
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("close", 7, zz_parameters);
    return;
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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { userIdentifier, password, dataBase, sessionDate, new Integer(flags), zz_retVal };
    vtblInvoke("open", 8, zz_parameters);
    return;
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
              String programId) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiView zz_retVal[] = { null };
    Object zz_parameters[] = { viewId, programId, zz_retVal };
    vtblInvoke("openView", 9, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiView)zz_retVal[0];
  }

  /**
   * getCompany. Company profile object
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiCompany
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiCompany getCompany  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiCompany zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCompany", 10, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiCompany)zz_retVal[0];
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
              String currencyCode) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiCurrency zz_retVal[] = { null };
    Object zz_parameters[] = { currencyCode, zz_retVal };
    vtblInvoke("getCurrency", 11, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiCurrency)zz_retVal[0];
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
              String rateType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiCurrencyTable zz_retVal[] = { null };
    Object zz_parameters[] = { curCode, rateType, zz_retVal };
    vtblInvoke("getCurrencyTable", 12, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiCurrencyTable)zz_retVal[0];
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
              String rateTypeCode) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiCurrencyRateType zz_retVal[] = { null };
    Object zz_parameters[] = { rateTypeCode, zz_retVal };
    vtblInvoke("getCurrencyRateType", 13, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiCurrencyRateType)zz_retVal[0];
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
              java.util.Date date) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiCurrencyRate zz_retVal[] = { null };
    Object zz_parameters[] = { homeCurrencyCode, rateType, sourceCurrencyCode, date, zz_retVal };
    vtblInvoke("getCurrencyRate", 14, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiCurrencyRate)zz_retVal[0];
  }

  /**
   * getActiveApplications. Collection of active applications
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiActiveApplications
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiActiveApplications getActiveApplications  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiActiveApplications zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getActiveApplications", 15, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiActiveApplications)zz_retVal[0];
  }

  /**
   * getErrors. Collection of errors
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiErrors
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiErrors getErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiErrors zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getErrors", 16, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiErrors)zz_retVal[0];
  }

  /**
   * clearErrors. Clears the error stack, frees any memory being used by the error module
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clearErrors  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("clearErrors", 17, zz_parameters);
    return;
  }

  /**
   * isWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isWarningIsException  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isWarningIsException", 18, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setWarningIsException  (
              boolean rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(rhs1), zz_retVal };
    vtblInvoke("setWarningIsException", 19, zz_parameters);
    return;
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
              String appVersion) throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { appID, appVersion, zz_retVal };
    vtblInvoke("licenseStatus", 20, zz_parameters);
    return zz_retVal[0];
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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.A4WCOM.IxapiView zz_retVal[] = { null };
    Object zz_parameters[] = { viewId, programId, new Integer(flags), zz_retVal };
    vtblInvoke("openViewEx", 21, zz_parameters);
    return (com.interweave.plugin.A4WCOM.IxapiView)zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("975d7ad2-8871-11d1-b5a5-0060083b07c8", com.interweave.plugin.A4WCOM.IxapiSessionProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("close",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("open",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("userIdentifier", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("password", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("dataBase", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sessionDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openView",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewId", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programId", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiView.IID, com.interweave.plugin.A4WCOM.IxapiViewProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCompany",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiCompany.IID, com.interweave.plugin.A4WCOM.IxapiCompanyProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrency",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("currencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiCurrency.IID, com.interweave.plugin.A4WCOM.IxapiCurrencyProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrencyTable",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiCurrencyTable.IID, com.interweave.plugin.A4WCOM.IxapiCurrencyTableProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrencyRateType",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rateTypeCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiCurrencyRateType.IID, com.interweave.plugin.A4WCOM.IxapiCurrencyRateTypeProxy.class) }),
        new com.linar.jintegra.MemberDesc("getCurrencyRate",
            new Class[] { String.class, String.class, String.class, java.util.Date.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("homeCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("rateType", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("sourceCurrencyCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiCurrencyRate.IID, com.interweave.plugin.A4WCOM.IxapiCurrencyRateProxy.class) }),
        new com.linar.jintegra.MemberDesc("getActiveApplications",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiActiveApplications.IID, com.interweave.plugin.A4WCOM.IxapiActiveApplicationsProxy.class) }),
        new com.linar.jintegra.MemberDesc("getErrors",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiErrors.IID, com.interweave.plugin.A4WCOM.IxapiErrorsProxy.class) }),
        new com.linar.jintegra.MemberDesc("clearErrors",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isWarningIsException",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("returnValue", 11, 16, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setWarningIsException",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("rhs1", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("licenseStatus",
            new Class[] { String.class, String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("appID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("appVersion", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 2, 16, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("openViewEx",
            new Class[] { String.class, String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewId", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("programId", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("returnValue", 29, 16, 4, com.interweave.plugin.A4WCOM.IxapiView.IID, com.interweave.plugin.A4WCOM.IxapiViewProxy.class) }),
});  }
}
