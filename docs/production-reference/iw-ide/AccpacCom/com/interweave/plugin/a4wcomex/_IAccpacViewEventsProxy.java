package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface '_IAccpacViewEvents'. Generated 02/10/2006 12:21:35 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Event interface for the ACCPAC view</B>'
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
public class _IAccpacViewEventsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex._IAccpacViewEvents, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = _IAccpacViewEvents.class;

  public _IAccpacViewEventsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, _IAccpacViewEvents.IID, host, authInfo);
  }

  /** For internal use only */
  public _IAccpacViewEventsProxy() {}

  public _IAccpacViewEventsProxy(Object obj) throws java.io.IOException {
    super(obj, _IAccpacViewEvents.IID);
  }

  protected _IAccpacViewEventsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected _IAccpacViewEventsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * onRecordChanged. Fired after an operation that changed the current logical record. This event is fired before OnKeyChanged.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onRecordChanged  (
              int eReason,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("eReason", 3, eReason),
                                         new Variant("pField", 9, pField),};
    invoke("onRecordChanged", 2, 1, parameters);
    return;
  }

  /**
   * onKeyChanged. Fired after an operation that changed the key field(s) of the current logical record. This event is fired after OnRecordChanged.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onKeyChanged  (
              int eReason,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("eReason", 3, eReason),
                                         new Variant("pField", 9, pField),};
    invoke("onKeyChanged", 3, 1, parameters);
    return;
  }

  /**
   * onRecordChanging. Fired before an operation that would change the current logical record.  The pStatus flag could be set to cancel the operation.
   *
   * @param     eReason A com.interweave.plugin.a4wcomex.tagEventReason constant (in)
   * @param     pStatus A com.interweave.plugin.a4wcomex.tagEventStatus constant (in/out: use single element array)
   * @param     pField An reference to a com.interweave.plugin.a4wcomex.IAccpacViewField (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void onRecordChanging  (
              int eReason,
              int[] pStatus,
              com.interweave.plugin.a4wcomex.IAccpacViewField pField) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.linar.jintegra.Variant[] parameters = {new Variant("eReason", 3, eReason),
                                         new Variant("pStatus", 16387, pStatus),
                                         new Variant("pField", 9, pField),};
    invoke("onRecordChanging", 4, 1, parameters);
    return;
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("1a6ab355-af1c-11d2-9ba0-00104b71eb3f", com.interweave.plugin.a4wcomex._IAccpacViewEventsProxy.class, null, 0, null );
  }
}
