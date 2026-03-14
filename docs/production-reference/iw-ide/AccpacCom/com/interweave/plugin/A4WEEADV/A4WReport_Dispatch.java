package com.interweave.plugin.A4WEEADV;

/**
 * COM Interface 'A4WReport_Dispatch'. Generated 18/10/2006 4:32:53 PM
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
public interface A4WReport_Dispatch extends java.io.Serializable {
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
   * print. print
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void print  (
              int parameter0,
              int parameter1,
              String parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_hashCode. hashCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int zz_hashCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setParam. setParam
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setParam  (
              String parameter0,
              String parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * print2. print2
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String print2  (
              int parameter0,
              int parameter1,
              String parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * printX. printX
   *
   * @param     parameter0 The parameter0 (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void printX  (
              int parameter0,
              int parameter1,
              String parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * select. select
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @param     parameter1 The parameter1 (in)
   * @param     parameter2 The parameter2 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void select  (
              Object parameter0,
              String parameter1,
              String parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDe2a0c2a9_55e1_4ad4_8c1e_12021e5e06a3 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = A4WReport_DispatchProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "00020400-0000-0000-C000-000000000046";
  String DISPID_100_NAME = "zz_wait";
  String DISPID_101_NAME = "print";
  String DISPID_102_NAME = "zz_hashCode";
  String DISPID_103_NAME = "setParam";
  String DISPID_104_NAME = "zz_toString";
  String DISPID_105_NAME = "zz_equals";
  String DISPID_106_NAME = "zz_notify";
  String DISPID_107_NAME = "print2";
  String DISPID_108_NAME = "printX";
  String DISPID_109_NAME = "zz_getClass";
  String DISPID_110_NAME = "zz_notifyAll";
  String DISPID_111_NAME = "select";
}
