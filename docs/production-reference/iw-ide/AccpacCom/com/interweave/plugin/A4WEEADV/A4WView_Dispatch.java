package com.interweave.plugin.A4WEEADV;

/**
 * COM Interface 'A4WView_Dispatch'. Generated 18/10/2006 4:32:53 PM
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
public interface A4WView_Dispatch extends java.io.Serializable {
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
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter3) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRVH. RVH
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRVH  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * drop. drop
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int drop  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * init. init
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int init  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * keys. keys
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int keys  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * open. open
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int open  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * post. post
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int post  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * read. read
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int read  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getView. view
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getView  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * close. close
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * dirty. dirty
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean dirty  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetch. fetch
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * order. order
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int order  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_hashCode. hashCode
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int zz_hashCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * identifyVersionAPI. identifyVersionAPI
   *
   * @param     parameter0 A Variant (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyVersionAPI  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionPost. revisionPost
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionPost  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSession. setSession
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSession  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter3) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter4) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionUnposted. revisionUnposted
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionUnposted  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * cancel. cancel
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * create. create
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int create  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * delete. delete
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int delete  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCmpCount. cmpCount
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCmpCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_toString. toString
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String zz_toString  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCmpNames. cmpNames
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCmpNames  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
   * exists. exists
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exists  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fields. fields
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fields  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * readLock. readLock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRVH2. getRVH
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRVH2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getView2. getView
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getView2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * instanceSecurity. instanceSecurity
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int instanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * insert. insert
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int insert  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetchLock. fetchLock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * process. process
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int process  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_notify. notify
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * security. security
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int security  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionCancel. revisionCancel
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionCancel  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * identifyVersionTemplate. identifyVersionTemplate
   *
   * @param     parameter0 A reference to another Automation Object (IDispatch) (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyVersionTemplate  (
              Object parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_getClass. getClass
   *
   * @return    A reference to another Automation Object (IDispatch)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object zz_getClass  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordClear. recordClear
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSession2. session
   *
   * @param     rhs1 A reference to another Automation Object (IDispatch) (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSession2  (
              Object rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionExists. revisionExists
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int revisionExists  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_notifyAll. notifyAll
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void zz_notifyAll  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * identifyViewTableCount. identifyViewTableCount
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int identifyViewTableCount  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * tableEmpty. tableEmpty
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordGenerate. recordGenerate
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int recordGenerate  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCmpCount2. getCmpCount
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCmpCount2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCmpNames2. getCmpNames
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getCmpNames2  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * filterFetch. filterFetch
   *
   * @param     parameter0 The parameter0 (in)
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterFetch  (
              int parameter0) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unload. unload
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unload  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unlock. unlock
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int unlock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * update. update
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int update  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * verify. verify
   *
   * @return    The returnValue
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int verify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int parameter1) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object parameter2) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDd3912b2f_ede6_4a5c_94e6_e07d5cb1966e = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = A4WView_DispatchProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "00020400-0000-0000-C000-000000000046";
  String DISPID_100_NAME = "get";
  String DISPID_101_NAME = "key";
  String DISPID_102_NAME = "put";
  String DISPID_103_GET_NAME = "getRVH";
  String DISPID_104_NAME = "drop";
  String DISPID_105_NAME = "init";
  String DISPID_106_NAME = "keys";
  String DISPID_107_NAME = "load";
  String DISPID_108_NAME = "name";
  String DISPID_109_NAME = "open";
  String DISPID_110_NAME = "post";
  String DISPID_111_NAME = "read";
  String DISPID_112_NAME = "type";
  String DISPID_113_GET_NAME = "getView";
  String DISPID_114_NAME = "zz_wait";
  String DISPID_115_NAME = "close";
  String DISPID_116_NAME = "dirty";
  String DISPID_117_NAME = "fetch";
  String DISPID_118_NAME = "field";
  String DISPID_119_NAME = "order";
  String DISPID_120_NAME = "zz_hashCode";
  String DISPID_121_NAME = "identifyViewTables";
  String DISPID_122_NAME = "identifyVersionAPI";
  String DISPID_123_NAME = "revisionPost";
  String DISPID_124_NAME = "presentsList";
  String DISPID_125_NAME = "presentsMask";
  String DISPID_126_NAME = "presentsType";
  String DISPID_127_NAME = "setSession";
  String DISPID_128_NAME = "blkGet";
  String DISPID_129_NAME = "blkPut";
  String DISPID_130_NAME = "revisionUnposted";
  String DISPID_131_NAME = "browse";
  String DISPID_132_NAME = "cancel";
  String DISPID_133_NAME = "create";
  String DISPID_134_NAME = "identifyViewName";
  String DISPID_135_NAME = "delete";
  String DISPID_136_GET_NAME = "getCmpCount";
  String DISPID_137_NAME = "zz_toString";
  String DISPID_138_GET_NAME = "getCmpNames";
  String DISPID_139_NAME = "zz_equals";
  String DISPID_140_NAME = "exists";
  String DISPID_141_NAME = "fields";
  String DISPID_142_NAME = "readLock";
  String DISPID_143_NAME = "filterSelect";
  String DISPID_144_NAME = "getRVH2";
  String DISPID_145_NAME = "getView2";
  String DISPID_146_NAME = "instanceSecurity";
  String DISPID_147_NAME = "insert";
  String DISPID_148_NAME = "fetchLock";
  String DISPID_149_NAME = "process";
  String DISPID_150_NAME = "zz_notify";
  String DISPID_151_NAME = "instanceOpen";
  String DISPID_152_NAME = "security";
  String DISPID_153_NAME = "revisionCancel";
  String DISPID_154_NAME = "identifyVersionTemplate";
  String DISPID_155_NAME = "zz_getClass";
  String DISPID_156_NAME = "attribs";
  String DISPID_157_NAME = "recordClear";
  String DISPID_158_PUT_NAME = "setSession2";
  String DISPID_159_NAME = "revisionExists";
  String DISPID_160_NAME = "zz_notifyAll";
  String DISPID_161_NAME = "identifyViewTableCount";
  String DISPID_162_NAME = "tableEmpty";
  String DISPID_163_NAME = "recordGenerate";
  String DISPID_164_NAME = "getCmpCount2";
  String DISPID_165_NAME = "getCmpNames2";
  String DISPID_166_NAME = "filterFetch";
  String DISPID_167_NAME = "unload";
  String DISPID_168_NAME = "unlock";
  String DISPID_169_NAME = "update";
  String DISPID_170_NAME = "verify";
  String DISPID_171_NAME = "filterDelete";
  String DISPID_172_NAME = "compose";
}
