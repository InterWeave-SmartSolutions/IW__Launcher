package com.interweave.plugin.A4WEEADV;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'A4WCompany_Dispatch'. Generated 18/10/2006 4:32:52 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\Pluswdev\LIB\A4WEADV.dll'<P>
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
public class A4WCompany_DispatchProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.A4WEEADV.A4WCompany_Dispatch, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.A4WEEADV.JIntegraInit.init(); }

  public static final Class targetClass = A4WCompany_Dispatch.class;

  public A4WCompany_DispatchProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, A4WCompany_Dispatch.IID, host, authInfo);
  }

  /** For internal use only */
  public A4WCompany_DispatchProxy() {}

  public A4WCompany_DispatchProxy(Object obj) throws java.io.IOException {
    super(obj, A4WCompany_Dispatch.IID);
  }

  protected A4WCompany_DispatchProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected A4WCompany_DispatchProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getFax. fax
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFax  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFax", 100, 2, parameters).getBSTR();
  }

  /**
   * getCity. city
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCity  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCity", 101, 2, parameters).getBSTR();
  }

  /**
   * read. read
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void read  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 9, parameter0),};
    invoke("read", 102, 1, parameters);
    return;
  }

  /**
   * zz_wait. 
   *
   * @param     parameter0 A Variant (in/out: use single element array)
   * @param     parameter1 A Variant (in/out: use single element array)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object zz_wait  (
              Object[] parameter0,
              Object[] parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 16396, parameter0),
                                         new Variant("parameter1", 16396, parameter1),};
    return invoke("zz_wait", 103, 1, parameters).getVARIANT();
  }

  /**
   * getPhone. phone
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPhone  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getPhone", 104, 2, parameters).getBSTR();
  }

  /**
   * getState. state
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getState  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getState", 105, 2, parameters).getBSTR();
  }

  /**
   * zz_hashCode. hashCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int zz_hashCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("zz_hashCode", 106, 1, parameters).getI4();
  }

  /**
   * getFiscalPeriods. fiscalPeriods
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFiscalPeriods  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFiscalPeriods", 107, 2, parameters).getI4();
  }

  /**
   * getMultiCurrency. getMultiCurrency
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMultiCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getMultiCurrency", 108, 1, parameters).getI4();
  }

  /**
   * getCompName. getCompName
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompName  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCompName", 109, 1, parameters).getBSTR();
  }

  /**
   * getWarnDays. warnDays
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getWarnDays  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getWarnDays", 110, 2, parameters).getI4();
  }

  /**
   * getRateType. rateType
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getRateType", 111, 2, parameters).getBSTR();
  }

  /**
   * getAddress1. getAddress1
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress1  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress1", 112, 1, parameters).getBSTR();
  }

  /**
   * getAddress2. getAddress2
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress2", 113, 1, parameters).getBSTR();
  }

  /**
   * getAddress3. getAddress3
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress3  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress3", 114, 1, parameters).getBSTR();
  }

  /**
   * getAddress4. getAddress4
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress4  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress4", 115, 1, parameters).getBSTR();
  }

  /**
   * getCountryCode. countryCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountryCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCountryCode", 116, 2, parameters).getBSTR();
  }

  /**
   * zz_toString. toString
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String zz_toString  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("zz_toString", 117, 1, parameters).getBSTR();
  }

  /**
   * zz_equals. equals
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean zz_equals  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 9, parameter0),};
    return invoke("zz_equals", 118, 1, parameters).getBOOL();
  }

  /**
   * getCompanyID. getCompanyID
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCompanyID", 119, 1, parameters).getBSTR();
  }

  /**
   * getFax2. getFax
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getFax2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFax2", 120, 1, parameters).getBSTR();
  }

  /**
   * getCity2. getCity
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCity2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCity2", 121, 1, parameters).getBSTR();
  }

  /**
   * getHomeCurrency. homeCurrency
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrency  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getHomeCurrency", 122, 2, parameters).getBSTR();
  }

  /**
   * getFormatPhoneNumbers. formatPhoneNumbers
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFormatPhoneNumbers  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFormatPhoneNumbers", 123, 2, parameters).getI4();
  }

  /**
   * getHomeCurrency2. getHomeCurrency
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getHomeCurrency2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getHomeCurrency2", 124, 1, parameters).getBSTR();
  }

  /**
   * getFiscalPeriods2. getFiscalPeriods
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFiscalPeriods2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFiscalPeriods2", 125, 1, parameters).getI4();
  }

  /**
   * getContact. getContact
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getContact  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getContact", 126, 1, parameters).getBSTR();
  }

  /**
   * getCountry. getCountry
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountry  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCountry", 127, 1, parameters).getBSTR();
  }

  /**
   * getBranchCode. getBranchCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getBranchCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getBranchCode", 128, 1, parameters).getBSTR();
  }

  /**
   * getCompName2. compName
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompName2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCompName2", 129, 2, parameters).getBSTR();
  }

  /**
   * getFormatPhoneNumbers2. getFormatPhoneNumbers
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getFormatPhoneNumbers2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getFormatPhoneNumbers2", 130, 1, parameters).getI4();
  }

  /**
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    invoke("zz_notify", 131, 1, parameters);
    return;
  }

  /**
   * getLocationCode. locationCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getLocationCode", 132, 2, parameters).getBSTR();
  }

  /**
   * getLocationType. locationType
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getLocationType", 133, 2, parameters).getBSTR();
  }

  /**
   * getQtrWithExtraPeriod. getQtrWithExtraPeriod
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getQtrWithExtraPeriod  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getQtrWithExtraPeriod", 134, 1, parameters).getI4();
  }

  /**
   * getPostalCode. getPostalCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPostalCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getPostalCode", 135, 1, parameters).getBSTR();
  }

  /**
   * getWarnDays2. getWarnDays
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getWarnDays2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getWarnDays2", 136, 1, parameters).getI4();
  }

  /**
   * getRateType2. getRateType
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getRateType2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getRateType2", 137, 1, parameters).getBSTR();
  }

  /**
   * zz_getClass. getClass
   *
   * @return    A reference to another Automation Object (IDispatch)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object zz_getClass  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("zz_getClass", 138, 1, parameters).getDISPATCH();
  }

  /**
   * getPhone2. getPhone
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPhone2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getPhone2", 139, 1, parameters).getBSTR();
  }

  /**
   * getState2. getState
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getState2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getState2", 140, 1, parameters).getBSTR();
  }

  /**
   * getCompanyID2. companyID
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCompanyID2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCompanyID2", 141, 2, parameters).getBSTR();
  }

  /**
   * getQtrWithExtraPeriod2. qtrWithExtraPeriod
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getQtrWithExtraPeriod2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getQtrWithExtraPeriod2", 142, 2, parameters).getI4();
  }

  /**
   * zz_notifyAll. notifyAll
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notifyAll  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    invoke("zz_notifyAll", 143, 1, parameters);
    return;
  }

  /**
   * getAddress12. address1
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress12  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress12", 144, 2, parameters).getBSTR();
  }

  /**
   * getAddress22. address2
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress22  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress22", 145, 2, parameters).getBSTR();
  }

  /**
   * getAddress32. address3
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress32  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress32", 146, 2, parameters).getBSTR();
  }

  /**
   * getAddress42. address4
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getAddress42  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getAddress42", 147, 2, parameters).getBSTR();
  }

  /**
   * getLocationCode2. getLocationCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationCode2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getLocationCode2", 148, 1, parameters).getBSTR();
  }

  /**
   * getLocationType2. getLocationType
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getLocationType2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getLocationType2", 149, 1, parameters).getBSTR();
  }

  /**
   * getBranchCode2. branchCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getBranchCode2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getBranchCode2", 150, 2, parameters).getBSTR();
  }

  /**
   * getCountryCode2. getCountryCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountryCode2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCountryCode2", 151, 1, parameters).getBSTR();
  }

  /**
   * getPostalCode2. postalCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPostalCode2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getPostalCode2", 152, 2, parameters).getBSTR();
  }

  /**
   * getMultiCurrency2. multiCurrency
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMultiCurrency2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getMultiCurrency2", 153, 2, parameters).getI4();
  }

  /**
   * getContact2. contact
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getContact2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getContact2", 154, 2, parameters).getBSTR();
  }

  /**
   * getCountry2. country
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCountry2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getCountry2", 155, 2, parameters).getBSTR();
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("51cca639-3489-4eaf-8574-810a6774f771", com.interweave.plugin.A4WEEADV.A4WCompany_DispatchProxy.class, null, 0, null );
  }
}
