package com.interweave.plugin.A4WEEADV;

/**
 * COM Interface 'A4WSizeString_Dispatch'. Generated 18/10/2006 4:32:53 PM
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
public interface A4WSizeString_Dispatch extends java.io.Serializable {
  /**
   * getSize. size
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSize  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_hashCode. hashCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int zz_hashCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_toString. toString
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String zz_toString  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_equals. equals
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean zz_equals  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSize2. getSize
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSize2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getString. getString
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getString  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_getClass. getClass
   *
   * @return    A reference to another Automation Object (IDispatch)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object zz_getClass  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_notifyAll. notifyAll
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notifyAll  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getString2. string
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getString2  () throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDa47e5ab6_1c10_4362_a258_d014aefd2907 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = A4WSizeString_DispatchProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "00020400-0000-0000-C000-000000000046";
  String DISPID_100_GET_NAME = "getSize";
  String DISPID_101_NAME = "zz_wait";
  String DISPID_102_NAME = "zz_hashCode";
  String DISPID_103_NAME = "zz_toString";
  String DISPID_104_NAME = "zz_equals";
  String DISPID_105_NAME = "getSize2";
  String DISPID_106_NAME = "zz_notify";
  String DISPID_107_NAME = "getString";
  String DISPID_108_NAME = "zz_getClass";
  String DISPID_109_NAME = "zz_notifyAll";
  String DISPID_110_GET_NAME = "getString2";
}
