package com.interweave.plugin.A4WEEADV;

import com.linar.jintegra.*;

/**
 * COM Class 'A4WView'. Generated 18/10/2006 4:32:52 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\Pluswdev\LIB\A4WEADV.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>A4WView</B>'
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
public class A4WView implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.A4WEEADV.A4WView_Dispatch {

  private static final String CLSID = "2ca70017-e03b-11d2-8521-00104b1f474e";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.A4WEEADV.A4WView_DispatchProxy d_A4WView_DispatchProxy = null;

  /** Access this COM class's com.interweave.plugin.A4WEEADV.A4WView_Dispatch interface */
  public com.interweave.plugin.A4WEEADV.A4WView_Dispatch getAsA4WView_Dispatch() { return d_A4WView_DispatchProxy; }

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
  public static A4WView getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new A4WView(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static A4WView bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new A4WView(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_A4WView_DispatchProxy; }

  /**
   * Constructs a A4WView on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public A4WView() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a A4WView on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public A4WView(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_A4WView_DispatchProxy = new com.interweave.plugin.A4WEEADV.A4WView_DispatchProxy(CLSID, host, null);
  }

  /**
   * Construct a A4WView using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public A4WView(Object obj) throws java.io.IOException {
    d_A4WView_DispatchProxy = new com.interweave.plugin.A4WEEADV.A4WView_DispatchProxy(obj);
  }

  /**
   * Release a A4WView.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_A4WView_DispatchProxy);
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
      return d_A4WView_DispatchProxy.getPropertyByName(name);
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
      return d_A4WView_DispatchProxy.getPropertyByName(name, rhs);
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
    return d_A4WView_DispatchProxy.invokeMethodByName(name, parameters);
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
    return d_A4WView_DispatchProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * get. get
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int get  (
              int parameter0,
              int parameter1,
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.get(parameter0,parameter1,parameter2);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * key. key
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int key  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.key(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * put. put
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @param     parameter3 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int put  (
              int parameter0,
              int parameter1,
              int parameter2,
              Object parameter3) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.put(parameter0,parameter1,parameter2,parameter3);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getRVH. RVH
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRVH  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getRVH();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * drop. drop
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int drop  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.drop();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * init. init
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int init  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.init();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * keys. keys
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int keys  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.keys();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * load. load
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int load  (
              String parameter0,
              String parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.load(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * name. name
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int name  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.name(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * open. open
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int open  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.open();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * post. post
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int post  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.post();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * read. read
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int read  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.read();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * type. type
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int type  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.type(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getView. view
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getView  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getView();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.zz_wait(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * close. close
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int close  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.close();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * dirty. dirty
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean dirty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.dirty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetch. fetch
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.fetch();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * field. field
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int field  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.field(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * order. order
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int order  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.order(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_hashCode. hashCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int zz_hashCode  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.zz_hashCode();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * identifyViewTables. identifyViewTables
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyViewTables  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.identifyViewTables(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * identifyVersionAPI. identifyVersionAPI
   *
   * @param     parameter0 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyVersionAPI  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.identifyVersionAPI(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionPost. revisionPost
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionPost  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.revisionPost(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * presentsList. presentsList
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int presentsList  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.presentsList(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * presentsMask. presentsMask
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int presentsMask  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.presentsMask(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * presentsType. presentsType
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int presentsType  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.presentsType(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSession. setSession
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSession  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_A4WView_DispatchProxy.setSession(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkGet. blkGet
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A Variant (in)
   * @param     parameter2 A Variant (in)
   * @param     parameter3 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int blkGet  (
              int parameter0,
              Object parameter1,
              Object parameter2,
              Object parameter3) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.blkGet(parameter0,parameter1,parameter2,parameter3);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * blkPut. blkPut
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 A Variant (in)
   * @param     parameter3 A Variant (in)
   * @param     parameter4 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int blkPut  (
              int parameter0,
              int parameter1,
              Object parameter2,
              Object parameter3,
              Object parameter4) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.blkPut(parameter0,parameter1,parameter2,parameter3,parameter4);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionUnposted. revisionUnposted
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionUnposted  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.revisionUnposted(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * browse. browse
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int browse  (
              String parameter0,
              int parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.browse(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * cancel. cancel
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.cancel();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * create. create
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int create  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.create();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * identifyViewName. identifyViewName
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyViewName  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.identifyViewName(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * delete. delete
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int delete  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.delete();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCmpCount. cmpCount
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCmpCount  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getCmpCount();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_toString. toString
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String zz_toString  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.zz_toString();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCmpNames. cmpNames
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCmpNames  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getCmpNames();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.zz_equals(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * exists. exists
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exists  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.exists();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fields. fields
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fields  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.fields();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * readLock. readLock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.readLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterSelect. filterSelect
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterSelect  (
              int parameter0,
              String parameter1,
              int parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.filterSelect(parameter0,parameter1,parameter2);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getRVH2. getRVH
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRVH2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getRVH2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getView2. getView
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getView2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getView2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * instanceSecurity. instanceSecurity
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int instanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.instanceSecurity();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * insert. insert
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int insert  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.insert();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * fetchLock. fetchLock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.fetchLock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * process. process
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int process  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.process();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_A4WView_DispatchProxy.zz_notify();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * instanceOpen. instanceOpen
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int instanceOpen  (
              int parameter0,
              int parameter1,
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.instanceOpen(parameter0,parameter1,parameter2);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * security. security
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int security  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.security();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionCancel. revisionCancel
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionCancel  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.revisionCancel(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * identifyVersionTemplate. identifyVersionTemplate
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyVersionTemplate  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.identifyVersionTemplate(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_getClass. getClass
   *
   * @return    A reference to another Automation Object (IDispatch)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object zz_getClass  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.zz_getClass();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * attribs. attribs
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int attribs  (
              int parameter0,
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.attribs(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordClear. recordClear
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.recordClear();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setSession2. session
   *
   * @param     rhs1 A reference to another Automation Object (IDispatch) (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSession2  (
              Object rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_A4WView_DispatchProxy.setSession2(rhs1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * revisionExists. revisionExists
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionExists  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.revisionExists(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * zz_notifyAll. notifyAll
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notifyAll  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_A4WView_DispatchProxy.zz_notifyAll();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * identifyViewTableCount. identifyViewTableCount
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyViewTableCount  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.identifyViewTableCount(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * tableEmpty. tableEmpty
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.tableEmpty();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * recordGenerate. recordGenerate
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int recordGenerate  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.recordGenerate(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCmpCount2. getCmpCount
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCmpCount2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getCmpCount2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getCmpNames2. getCmpNames
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCmpNames2  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.getCmpNames2();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterFetch. filterFetch
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterFetch  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.filterFetch(parameter0);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unload. unload
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unload  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.unload();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * unlock. unlock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlock  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.unlock();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * update. update
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int update  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.update();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * verify. verify
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int verify  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.verify();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * filterDelete. filterDelete
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterDelete  (
              String parameter0,
              int parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.filterDelete(parameter0,parameter1);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * compose. compose
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 A Variant (in)
   * @param     parameter2 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int compose  (
              int parameter0,
              Object parameter1,
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_A4WView_DispatchProxy.compose(parameter0,parameter1,parameter2);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
