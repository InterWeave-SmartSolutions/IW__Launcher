package com.interweave.plugin.A4WEEADV;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'A4WDate_Dispatch'. Generated 18/10/2006 4:32:52 PM
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
public class A4WDate_DispatchProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.A4WEEADV.A4WDate_Dispatch, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.A4WEEADV.JIntegraInit.init(); }

  public static final Class targetClass = A4WDate_Dispatch.class;

  public A4WDate_DispatchProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, A4WDate_Dispatch.IID, host, authInfo);
  }

  /** For internal use only */
  public A4WDate_DispatchProxy() {}

  public A4WDate_DispatchProxy(Object obj) throws java.io.IOException {
    super(obj, A4WDate_Dispatch.IID);
  }

  protected A4WDate_DispatchProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected A4WDate_DispatchProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getDay. day
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDay  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getDay", 100, 2, parameters).getI4();
  }

  /**
   * setDay. day
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDay  (
              int rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("rhs1", 3, rhs1),};
    invoke("setDay", 100, 4, parameters);
    return;
  }

  /**
   * setDate. date
   *
   * @param     rhs1 A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDate  (
              Object rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {rhs1 == null ? new Variant("rhs1", 0, 0x80020004L) : new Variant("rhs1", 12, rhs1),};
    invoke("setDate", 101, 4, parameters);
    return;
  }

  /**
   * isNull. null
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isNull  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("isNull", 102, 2, parameters).getBOOL();
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
   * setYear. year
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setYear  (
              int rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("rhs1", 3, rhs1),};
    invoke("setYear", 104, 4, parameters);
    return;
  }

  /**
   * getYear. year
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getYear  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getYear", 104, 2, parameters).getI4();
  }

  /**
   * setMonth. month
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setMonth  (
              int rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("rhs1", 3, rhs1),};
    invoke("setMonth", 105, 4, parameters);
    return;
  }

  /**
   * getMonth. month
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMonth  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getMonth", 105, 2, parameters).getI4();
  }

  /**
   * toBCD. toBCD
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object toBCD  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("toBCD", 106, 1, parameters).getVARIANT();
  }

  /**
   * toYMD. toYMD
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String toYMD  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("toYMD", 107, 1, parameters).getBSTR();
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
    return invoke("zz_hashCode", 108, 1, parameters).getI4();
  }

  /**
   * setMonth2. setMonth
   *
   * @param     parameter0 The parameter0 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setMonth2  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 3, parameter0),};
    invoke("setMonth2", 109, 1, parameters);
    return;
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
    return invoke("zz_toString", 110, 1, parameters).getBSTR();
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
    return invoke("zz_equals", 111, 1, parameters).getBOOL();
  }

  /**
   * getDay2. getDay
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDay2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getDay2", 112, 1, parameters).getI4();
  }

  /**
   * getYear2. getYear
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getYear2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getYear2", 113, 1, parameters).getI4();
  }

  /**
   * isNull2. isNull
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isNull2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("isNull2", 114, 1, parameters).getBOOL();
  }

  /**
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    invoke("zz_notify", 115, 1, parameters);
    return;
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
    return invoke("zz_getClass", 116, 1, parameters).getDISPATCH();
  }

  /**
   * getMonth2. getMonth
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getMonth2  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    return invoke("getMonth2", 117, 1, parameters).getI4();
  }

  /**
   * setDate2. setDate
   *
   * @param     parameter0 A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDate2  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {parameter0 == null ? new Variant("parameter0", 0, 0x80020004L) : new Variant("parameter0", 12, parameter0),};
    invoke("setDate2", 118, 1, parameters);
    return;
  }

  /**
   * setYear2. setYear
   *
   * @param     parameter0 The parameter0 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setYear2  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 3, parameter0),};
    invoke("setYear2", 119, 1, parameters);
    return;
  }

  /**
   * zz_notifyAll. notifyAll
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notifyAll  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {};
    invoke("zz_notifyAll", 120, 1, parameters);
    return;
  }

  /**
   * setDay2. setDay
   *
   * @param     parameter0 The parameter0 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setDay2  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("parameter0", 3, parameter0),};
    invoke("setDay2", 121, 1, parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("6f8f4d5e-569b-41f4-ac0b-3a29bf36fd35", com.interweave.plugin.A4WEEADV.A4WDate_DispatchProxy.class, null, 0, null );
  }
}
