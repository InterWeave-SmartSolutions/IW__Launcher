package com.interweave.plugin.a4comsv;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacSvrView'. Generated 12/10/2006 12:40:15 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\a4wcomsv.dll'<P>
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
public class IAccpacSvrViewProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4comsv.IAccpacSvrView, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4comsv.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacSvrView.class;

  public IAccpacSvrViewProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacSvrView.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacSvrViewProxy() {}

  public IAccpacSvrViewProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacSvrView.IID);
  }

  protected IAccpacSvrViewProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacSvrViewProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * close. Closes an open view.
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
   * cancel. Rolls back any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("cancel", 8, zz_parameters);
    return;
  }

  /**
   * init. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("init", 9, zz_parameters);
    return;
  }

  /**
   * delete. Removes the logical record from the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void delete  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("delete", 10, zz_parameters);
    return;
  }

  /**
   * insert. Creates a new logical record in the database from the contents of the current logical record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insert  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("insert", 11, zz_parameters);
    return;
  }

  /**
   * post. Commits any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void post  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("post", 12, zz_parameters);
    return;
  }

  /**
   * process. Used by special types of views as a signal to perform the operations.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void process  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("process", 13, zz_parameters);
    return;
  }

  /**
   * unLock. Unlocks the logical record locked by a FetchLock or ReadLock call.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unLock  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("unLock", 14, zz_parameters);
    return;
  }

  /**
   * update. Writes the contents of the existing logical record back to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void update  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("update", 15, zz_parameters);
    return;
  }

  /**
   * verify. Checks the referential integrity of the current record of the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verify  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("verify", 16, zz_parameters);
    return;
  }

  /**
   * fetch. Retrieves the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("fetch", 17, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * fetchLock. Retrieves and locks the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("fetchLock", 18, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * read. Fetches the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean read  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("read", 19, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * readLock. Fetches and locks the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("readLock", 20, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * browse. Establishes the browse filter and direction that affects all subsequent navigation routines.
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void browse  (
              String filter,
              boolean ascending) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { filter, new Boolean(ascending), zz_retVal };
    vtblInvoke("browse", 21, zz_parameters);
    return;
  }

  /**
   * compose. Composes the view with the supplied array of AccpacView objects.
   *
   * @param     viewOrArrayOfViews A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void compose  (
              Object viewOrArrayOfViews) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewOrArrayOfViews == null ? new Variant("viewOrArrayOfViews") : viewOrArrayOfViews, zz_retVal };
    vtblInvoke("compose", 22, zz_parameters);
    return;
  }

  /**
   * blkGet. Performs a block get of field values of the specified array of fields IDs.  Returns the array of field values.
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGet  (
              Object fieldIDs,
              Object[] pValues) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, pValues, zz_retVal };
    vtblInvoke("blkGet", 23, zz_parameters);
    return;
  }

  /**
   * blkPut. Performs a block put of the specified array of field values to the array of field IDs.
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (in)
   * @param     verify The verify (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkPut  (
              Object fieldIDs,
              Object pValues,
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, pValues == null ? new Variant("pValues") : pValues, new Boolean(verify), zz_retVal };
    vtblInvoke("blkPut", 24, zz_parameters);
    return;
  }

  /**
   * blkGetFields. Performs a block get of field objects of the specified array of fields IDs.  Returns the array of view field objects.
   *
   * @param     fieldIDs A Variant (in)
   * @param     fields A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGetFields  (
              Object fieldIDs,
              Object[] fields) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, fields, zz_retVal };
    vtblInvoke("blkGetFields", 25, zz_parameters);
    return;
  }

  /**
   * zz_clone. Creates a clone of the current AccpacView object.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrView zz_clone  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrView zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("zz_clone", 26, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrView)zz_retVal[0];
  }

  /**
   * goTop. Navigates to the first record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goTop  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("goTop", 27, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goBottom. Navigates to the last record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goBottom  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("goBottom", 28, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goToBookMark. Navigates to the record as specified by the bookmark that is obtained from the RecordBookmark property from a previous record that was navigated to.
   *
   * @param     pBookMark A Variant (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToBookMark  (
              Object pBookMark) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { pBookMark == null ? new Variant("pBookMark") : pBookMark, zz_retVal };
    vtblInvoke("goToBookMark", 29, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goNext. Navigates to the next record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goNext  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("goNext", 30, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goPrev. Navigates to the previous record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goPrev  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("goPrev", 31, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isDirty. Reports whether fields have been altered.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isDirty  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isDirty", 32, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isExists. Reports whether the current logical record exists in the database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isExists  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isExists", 33, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getOrder. Returns/sets the order in which the view is accessed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getOrder  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getOrder", 34, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setOrder. Returns/sets the order in which the view is accessed.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrder  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setOrder", 35, zz_parameters);
    return;
  }

  /**
   * getType. Returns the type of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getType", 36, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getSecurity. Returns the access rights the current user is assigned on the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSecurity", 37, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getCompositeNames. Returns the AccpacViewCompositeNames object that contains the composition information as defined in the view.
   *
   * @param     count The count (out: use single element array)
   * @param     pVal The pVal (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getCompositeNames  (
              int[] count,
              String[][] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, pVal, zz_retVal };
    vtblInvoke("getCompositeNames", 38, zz_parameters);
    return;
  }

  /**
   * getFields. Returns the AccpacViewFields collection object that represents all the fields in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrViewFields zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getFields", 39, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrViewFields)zz_retVal[0];
  }

  /**
   * getKeys. Returns the AccpacViewKeys collection object that represents all the keys in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4comsv.IAccpacSvrViewKeys zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getKeys", 40, zz_parameters);
    return (com.interweave.plugin.a4comsv.IAccpacSvrViewKeys)zz_retVal[0];
  }

  /**
   * getRecordBookMark. Returns the bookmark of the current logical record that could be passed later on to the GotoBookmark method.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getRecordBookMark  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getRecordBookMark", 41, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * getDescription. Returns the description of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDescription", 42, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * isUnpostedRevisions. Returns whether there are unposted revisions in the view or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUnpostedRevisions  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isUnpostedRevisions", 43, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getSystemAccess. Returns/sets the system access flags of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSystemAccess", 44, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setSystemAccess. Returns/sets the system access flags of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setSystemAccess", 45, zz_parameters);
    return;
  }

  /**
   * getReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getReferentialIntegrity", 46, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(pVal), zz_retVal };
    vtblInvoke("setReferentialIntegrity", 47, zz_parameters);
    return;
  }

  /**
   * getViewID. Returns the ID (Roto ID) of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getViewID  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getViewID", 48, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewReturnCode constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getLastReturnCode", 49, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goTopEx. Calls GoTop, and then BlkGet (into Values). Also retrieves the record number. Returns what GoTop returns.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goTopEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("goTopEx", 50, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goBottomEx. Calls GoBottom, and then BlkGet (into Values). Also retrieves the record number. Returns what GoBottom returns.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goBottomEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("goBottomEx", 51, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goToBookMarkEx. Calls GoToBookMark (on pBookMark), then calls BlkGet to retrieve the field values. Returns what the GoToBookMark call returns.
   *
   * @param     pBookMark A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToBookMarkEx  (
              Object pBookMark,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { pBookMark == null ? new Variant("pBookMark") : pBookMark, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("goToBookMarkEx", 52, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goNextEx. Calls BlkPut (on PutFieldValues), then GoNext, and then BlkGet (into Values). Also retrieves the record number. Returns what GoNext returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goNextEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("goNextEx", 53, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goPrevEx. Calls BlkPut (on PutFieldValues), then GoPrev, and then BlkGet (into Values). Also retrieves the record number. Returns what GoPrev returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goPrevEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("goPrevEx", 54, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * fetchEx. Calls BlkPut (on PutFieldValues), then Fetch, and then BlkGet (into Values). Also retrieves the record number. Returns what Fetch returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("fetchEx", 55, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * fetchLockEx. Calls BlkPut (on PutFieldValues), then FetchLock, and then BlkGet (into Values). Also retrieves the record number. Returns what FetchLock returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchLockEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("fetchLockEx", 56, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * readEx. Calls BlkPut (on PutFieldValues), then Read, and then BlkGet (into Values). Also retrieves the record number. Returns what Read returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("readEx", 57, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * readLockEx. Calls BlkPut (on PutFieldValues), then ReadLock, and then BlkGet (into Values). Also retrieves the record number. Returns what ReadLock returns.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readLockEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("readLockEx", 58, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * initEx. Calls Init, and then BlkGet (into Values). Also retrieves the record number.
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initEx  (
              Object fieldIDs,
              Object[] values,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, recNum, zz_retVal };
    vtblInvoke("initEx", 59, zz_parameters);
    return;
  }

  /**
   * cancelEx. Calls Cancel, and then BlkGet (into Values).
   *
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancelEx  (
              Object fieldIDs,
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, zz_retVal };
    vtblInvoke("cancelEx", 60, zz_parameters);
    return;
  }

  /**
   * updateEx. Calls BlkPut (on PutFieldValues), then Update, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void updateEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, getFieldIDs == null ? new Variant("getFieldIDs") : getFieldIDs, getFieldValues, zz_retVal };
    vtblInvoke("updateEx", 61, zz_parameters);
    return;
  }

  /**
   * insertEx. Calls BlkPut (on PutFieldValues), then Insert, and then BlkGet (into GetFieldValues). Also retrieves the record number.
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @param     recNum The recNum (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insertEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues,
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, getFieldIDs == null ? new Variant("getFieldIDs") : getFieldIDs, getFieldValues, recNum, zz_retVal };
    vtblInvoke("insertEx", 62, zz_parameters);
    return;
  }

  /**
   * processEx. Calls BlkPut (on PutFieldValues), then Process, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void processEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, getFieldIDs == null ? new Variant("getFieldIDs") : getFieldIDs, getFieldValues, zz_retVal };
    vtblInvoke("processEx", 63, zz_parameters);
    return;
  }

  /**
   * postEx. Calls BlkPut (on PutFieldValues), then Post, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void postEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, getFieldIDs == null ? new Variant("getFieldIDs") : getFieldIDs, getFieldValues, zz_retVal };
    vtblInvoke("postEx", 64, zz_parameters);
    return;
  }

  /**
   * verifyEx. Calls BlkPut (on PutFieldValues), then Verify, and then BlkGet (into GetFieldValues).
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     getFieldIDs A Variant (in)
   * @param     getFieldValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verifyEx  (
              Object putFieldIDs,
              Object putFieldValues,
              Object getFieldIDs,
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, getFieldIDs == null ? new Variant("getFieldIDs") : getFieldIDs, getFieldValues, zz_retVal };
    vtblInvoke("verifyEx", 65, zz_parameters);
    return;
  }

  /**
   * isUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseRecordNumbering  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isUseRecordNumbering", 66, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseRecordNumbering  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(pVal), zz_retVal };
    vtblInvoke("setUseRecordNumbering", 67, zz_parameters);
    return;
  }

  /**
   * getRecordNumber. Returns the record number of the current record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRecordNumber  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getRecordNumber", 68, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goToRecordNumber. Goes to the specified record number. Returns whether the record is found or not.
   *
   * @param     recordNumber The recordNumber (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToRecordNumber  (
              int recordNumber) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Integer(recordNumber), zz_retVal };
    vtblInvoke("goToRecordNumber", 69, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * goToRecordNumberEx. Calls GoToRecordNumber (on RecordNumber), and then BlkGet (into Values). Returns what GoToRecordNumber returns.
   *
   * @param     recordNumber The recordNumber (in)
   * @param     fieldIDs A Variant (in)
   * @param     values A Variant (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToRecordNumberEx  (
              int recordNumber,
              Object fieldIDs,
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Integer(recordNumber), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, values, zz_retVal };
    vtblInvoke("goToRecordNumberEx", 70, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * resetRecordNumbers. Resets all generated record numbers. Record numbers would be re-generated the next time any method is called that positions to a record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void resetRecordNumbers  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("resetRecordNumbers", 71, zz_parameters);
    return;
  }

  /**
   * macroAppend. Appends a line to a macro.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { cmdInString, zz_retVal };
    vtblInvoke("macroAppend", 72, zz_parameters);
    return;
  }

  /**
   * getHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getHeaderLinkedKeyFieldCount  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getHeaderLinkedKeyFieldCount", 73, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * setHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHeaderLinkedKeyFieldCount  (
              short pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Short(pVal), zz_retVal };
    vtblInvoke("setHeaderLinkedKeyFieldCount", 74, zz_parameters);
    return;
  }

  /**
   * recordClear. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("recordClear", 75, zz_parameters);
    return;
  }

  /**
   * recordGenerate. method RecordGenerate
   *
   * @param     insert The insert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean insert) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(insert), zz_retVal };
    vtblInvoke("recordGenerate", 76, zz_parameters);
    return;
  }

  /**
   * getInstanceSecurity. property InstanceSecurity
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getInstanceSecurity", 77, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * tableEmpty. method TableEmpty
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("tableEmpty", 78, zz_parameters);
    return;
  }

  /**
   * revisionCancel. method RevisionCancel
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionCancel  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(level), zz_retVal };
    vtblInvoke("revisionCancel", 79, zz_parameters);
    return;
  }

  /**
   * revisionPost. method RevisionPost
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionPost  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(level), zz_retVal };
    vtblInvoke("revisionPost", 80, zz_parameters);
    return;
  }

  /**
   * revisionExists. method RevisionExists
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionExists  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Integer(level), zz_retVal };
    vtblInvoke("revisionExists", 81, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * revisionUnposted. method RevisionUnposted
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionUnposted  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Integer(level), zz_retVal };
    vtblInvoke("revisionUnposted", 82, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * filterDelete. method FilterDelete
   *
   * @param     filter The filter (in)
   * @param     strictness A com.interweave.plugin.a4comsv.tagSvrFilterStrictnessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterDelete  (
              String filter,
              int strictness) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { filter, new Integer(strictness), zz_retVal };
    vtblInvoke("filterDelete", 83, zz_parameters);
    return;
  }

  /**
   * filterSelect. method FilterSelect
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @param     order The order (in)
   * @param     origin A com.interweave.plugin.a4comsv.tagSvrFilterOriginEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterSelect  (
              String filter,
              boolean ascending,
              int order,
              int origin) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { filter, new Boolean(ascending), new Integer(order), new Integer(origin), zz_retVal };
    vtblInvoke("filterSelect", 84, zz_parameters);
    return;
  }

  /**
   * filterFetch. method FilterFetch
   *
   * @param     lock The lock (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean filterFetch  (
              boolean lock) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Boolean(lock), zz_retVal };
    vtblInvoke("filterFetch", 85, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getTemplateVersion. property TemplateVersion
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTemplateVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getTemplateVersion", 86, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getTemplateDate. property TemplateDate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getTemplateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    java.util.Date zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getTemplateDate", 87, zz_parameters);
    return (java.util.Date)zz_retVal[0];
  }

  /**
   * isInstanceReadonly. property InstanceReadonly
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceReadonly  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceReadonly", 88, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isInstanceUnrevisioned. property InstanceUnrevisioned
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnrevisioned  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceUnrevisioned", 89, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isInstanceUnvalidated. property InstanceUnvalidated
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnvalidated  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceUnvalidated", 90, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isInstanceRawPut. property InstanceRawPut
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceRawPut  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceRawPut", 91, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isInstanceNoncascading. property InstanceNoncascading
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNoncascading  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceNoncascading", 92, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getInstancePrefetch. property InstancePrefetch
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstancePrefetch  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getInstancePrefetch", 93, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isInstanceNonheritable. property InstanceNonheritable
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNonheritable  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isInstanceNonheritable", 94, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exGetViewInfo. 
   *
   * @param     fieldIdentifiers A Variant (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exGetViewInfo  (
              Object[] fieldIdentifiers) throws java.io.IOException, com.linar.jintegra.AutomationException{
    byte[] zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIdentifiers, zz_retVal };
    vtblInvoke("exGetViewInfo", 95, zz_parameters);
    return (byte[])zz_retVal[0];
  }

  /**
   * exGetFieldInfo. 
   *
   * @param     fieldID A Variant (in)
   * @param     fieldIDType A com.interweave.plugin.a4comsv.tagSvrFieldIDType constant (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exGetFieldInfo  (
              Object fieldID,
              int fieldIDType) throws java.io.IOException, com.linar.jintegra.AutomationException{
    byte[] zz_retVal[] = { null };
    Object zz_parameters[] = { fieldID == null ? new Variant("fieldID") : fieldID, new Integer(fieldIDType), zz_retVal };
    vtblInvoke("exGetFieldInfo", 96, zz_parameters);
    return (byte[])zz_retVal[0];
  }

  /**
   * exPut. 
   *
   * @param     iD The iD (in)
   * @param     value A Variant (in)
   * @param     verify The verify (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exPut  (
              int iD,
              Object value,
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException{
    byte[] zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(iD), value == null ? new Variant("value") : value, new Boolean(verify), zz_retVal };
    vtblInvoke("exPut", 97, zz_parameters);
    return (byte[])zz_retVal[0];
  }

  /**
   * exGoNext. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoNext  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoNext", 98, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exGoPrev. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoPrev  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoPrev", 99, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exGoTop. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoTop  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoTop", 100, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exGoBottom. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoBottom  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoBottom", 101, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exGoToBookMark. 
   *
   * @param     pBookMark A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoToBookMark  (
              Object pBookMark,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { pBookMark == null ? new Variant("pBookMark") : pBookMark, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoToBookMark", 102, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exRead. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exRead  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRead", 103, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exReadLock. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exReadLock  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exReadLock", 104, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exFetch. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFetch  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exFetch", 105, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exFetchLock. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFetchLock  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exFetchLock", 106, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exInit. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInit  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exInit", 107, zz_parameters);
    return;
  }

  /**
   * exCancel. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exCancel  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exCancel", 108, zz_parameters);
    return;
  }

  /**
   * exInsert. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInsert  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exInsert", 109, zz_parameters);
    return;
  }

  /**
   * exUpdate. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exUpdate  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exUpdate", 110, zz_parameters);
    return;
  }

  /**
   * exPost. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exPost  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exPost", 111, zz_parameters);
    return;
  }

  /**
   * exProcess. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exProcess  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exProcess", 112, zz_parameters);
    return;
  }

  /**
   * exGoToRecordNumber. 
   *
   * @param     recordNumber The recordNumber (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exGoToRecordNumber  (
              int recordNumber,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Integer(recordNumber), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exGoToRecordNumber", 113, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exSetOrder. 
   *
   * @param     order The order (in)
   * @param     unique The unique (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exSetOrder  (
              int order,
              boolean unique,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(order), new Boolean(unique), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exSetOrder", 114, zz_parameters);
    return;
  }

  /**
   * exLookupValue. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     fieldValues A Variant (in)
   * @param     order The order (in)
   * @param     targetFieldID The targetFieldID (in)
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object exLookupValue  (
              Object fieldIDs,
              Object fieldValues,
              int order,
              int targetFieldID) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, fieldValues == null ? new Variant("fieldValues") : fieldValues, new Integer(order), new Integer(targetFieldID), zz_retVal };
    vtblInvoke("exLookupValue", 115, zz_parameters);
    return (Object)zz_retVal[0];
  }

  /**
   * exCompose. 
   *
   * @param     viewIDs The viewIDs (in)
   * @param     parentView An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exCompose  (
              String[] viewIDs,
              com.interweave.plugin.a4comsv.IAccpacSvrView parentView) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewIDs, parentView, zz_retVal };
    vtblInvoke("exCompose", 116, zz_parameters);
    return;
  }

  /**
   * exGetComposedView. 
   *
   * @param     viewID The viewID (in)
   * @param     view An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exGetComposedView  (
              String viewID,
              com.interweave.plugin.a4comsv.IAccpacSvrView[] view) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { viewID, view, zz_retVal };
    vtblInvoke("exGetComposedView", 117, zz_parameters);
    return;
  }

  /**
   * exBlkGet. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exBlkGet  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exBlkGet", 118, zz_parameters);
    return;
  }

  /**
   * exBlkPut. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     pValues A Variant (in)
   * @param     verify The verify (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exBlkPut  (
              Object fieldIDs,
              Object pValues,
              boolean verify,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, pValues == null ? new Variant("pValues") : pValues, new Boolean(verify), updatedInfo, zz_retVal };
    vtblInvoke("exBlkPut", 119, zz_parameters);
    return;
  }

  /**
   * exVerify. 
   *
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exVerify  (
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exVerify", 120, zz_parameters);
    return;
  }

  /**
   * exRefreshPresentation. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRefreshPresentation  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRefreshPresentation", 121, zz_parameters);
    return;
  }

  /**
   * exSetSystemAccess. 
   *
   * @param     systemAccess A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exSetSystemAccess  (
              int systemAccess,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(systemAccess), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exSetSystemAccess", 122, zz_parameters);
    return;
  }

  /**
   * exRecordClear. 
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordClear  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRecordClear", 123, zz_parameters);
    return;
  }

  /**
   * exRecordGenerate. 
   *
   * @param     insert The insert (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordGenerate  (
              boolean insert,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(insert), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRecordGenerate", 124, zz_parameters);
    return;
  }

  /**
   * exRevisionCancel. 
   *
   * @param     level The level (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRevisionCancel  (
              int level,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(level), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRevisionCancel", 125, zz_parameters);
    return;
  }

  /**
   * exRevisionPost. 
   *
   * @param     level The level (in)
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRevisionPost  (
              int level,
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(level), putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRevisionPost", 126, zz_parameters);
    return;
  }

  /**
   * initPrimaryKeyFields. 
   *
   * @param     bookmark A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initPrimaryKeyFields  (
              Object bookmark) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { bookmark == null ? new Variant("bookmark") : bookmark, zz_retVal };
    vtblInvoke("initPrimaryKeyFields", 127, zz_parameters);
    return;
  }

  /**
   * getClientViewCache. 
   *
   * @param     clientViewInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getClientViewCache  (
              byte[][] clientViewInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { clientViewInfo, zz_retVal };
    vtblInvoke("getClientViewCache", 128, zz_parameters);
    return;
  }

  /**
   * exFilterFetch. 
   *
   * @param     lock The lock (in)
   * @param     putFieldIDs A Variant (in)
   * @param     putFieldValues A Variant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean exFilterFetch  (
              boolean lock,
              Object putFieldIDs,
              Object putFieldValues,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Boolean(lock), putFieldIDs == null ? new Variant("putFieldIDs") : putFieldIDs, putFieldValues == null ? new Variant("putFieldValues") : putFieldValues, fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exFilterFetch", 129, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exFilterSelect. 
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @param     order The order (in)
   * @param     origin A com.interweave.plugin.a4comsv.tagSvrFilterOriginEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exFilterSelect  (
              String filter,
              boolean ascending,
              int order,
              int origin,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { filter, new Boolean(ascending), new Integer(order), new Integer(origin), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exFilterSelect", 130, zz_parameters);
    return;
  }

  /**
   * isUsedOpenViewInstance. Returns whether the view was opened with .OpenViewInstance
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUsedOpenViewInstance  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("isUsedOpenViewInstance", 131, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exInstanceNotify. Sets whether notifications from the view are received.
   *
   * @param     val The val (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInstanceNotify  (
              boolean val) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Boolean(val), zz_retVal };
    vtblInvoke("exInstanceNotify", 132, zz_parameters);
    return;
  }

  /**
   * recordCreate. method RecordCreate
   *
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrViewRecordCreateEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordCreate  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(flags), zz_retVal };
    vtblInvoke("recordCreate", 133, zz_parameters);
    return;
  }

  /**
   * filterCount. method FilterCount
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterCount  (
              String filter,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { filter, new Integer(flags), zz_retVal };
    vtblInvoke("filterCount", 134, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getInstanceProtocol. method InstanaceProtocol
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceProtocol  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getInstanceProtocol", 135, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exRecordCreate. method ExRecordCreate
   *
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrViewRecordCreateEnum constant (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exRecordCreate  (
              int flags,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(flags), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exRecordCreate", 136, zz_parameters);
    return;
  }

  /**
   * exFilterCount. method ExFilterCount
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int exFilterCount  (
              String filter,
              int flags,
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { filter, new Integer(flags), fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exFilterCount", 137, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * exInstanceProtocol. method ExInstanceProtcol
   *
   * @param     fieldIDs A Variant (in)
   * @param     updatedInfo An unsigned byte (out: use single element array)
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int exInstanceProtocol  (
              Object fieldIDs,
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { fieldIDs == null ? new Variant("fieldIDs") : fieldIDs, updatedInfo, zz_retVal };
    vtblInvoke("exInstanceProtocol", 138, zz_parameters);
    return zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("ad3832d7-3022-47f1-b10c-981fb19e2451", com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("close",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("cancel",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("init",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("delete",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("insert",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("post",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("process",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("unLock",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("update",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("verify",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fetch",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fetchLock",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("read",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("readLock",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("browse",
            new Class[] { String.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ascending", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("compose",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewOrArrayOfViews", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("blkGet",
            new Class[] { Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("blkPut",
            new Class[] { Object.class, Object.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("verify", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("blkGetFields",
            new Class[] { Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fields", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("zz_clone",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class) }),
        new com.linar.jintegra.MemberDesc("goTop",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goBottom",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goToBookMark",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pBookMark", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goNext",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goPrev",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isDirty",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isExists",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getOrder",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setOrder",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getType",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getSecurity",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getCompositeNames",
            new Class[] { int[].class, String[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getFields",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewFields.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewFieldsProxy.class) }),
        new com.linar.jintegra.MemberDesc("getKeys",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4comsv.IAccpacSvrViewKeys.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewKeysProxy.class) }),
        new com.linar.jintegra.MemberDesc("getRecordBookMark",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDescription",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isUnpostedRevisions",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSystemAccess",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("setSystemAccess",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getReferentialIntegrity",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("setReferentialIntegrity",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getViewID",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getLastReturnCode",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("goTopEx",
            new Class[] { Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goBottomEx",
            new Class[] { Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goToBookMarkEx",
            new Class[] { Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pBookMark", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goNextEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goPrevEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fetchEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("fetchLockEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("readEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("readLockEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("initEx",
            new Class[] { Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("cancelEx",
            new Class[] { Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("updateEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("insertEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, int[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("recNum", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("processEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("postEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("verifyEx",
            new Class[] { Object.class, Object.class, Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("getFieldValues", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isUseRecordNumbering",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setUseRecordNumbering",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getRecordNumber",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goToRecordNumber",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("recordNumber", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("goToRecordNumberEx",
            new Class[] { Integer.TYPE, Object.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("recordNumber", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("values", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("resetRecordNumbers",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("macroAppend",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("cmdInString", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getHeaderLinkedKeyFieldCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("setHeaderLinkedKeyFieldCount",
            new Class[] { Short.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("recordClear",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("recordGenerate",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("insert", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getInstanceSecurity",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("tableEmpty",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("revisionCancel",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("revisionPost",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("revisionExists",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("revisionUnposted",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("filterDelete",
            new Class[] { String.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("strictness", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("filterSelect",
            new Class[] { String.class, Boolean.TYPE, Integer.TYPE, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ascending", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("order", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("origin", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("filterFetch",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lock", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getTemplateVersion",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getTemplateDate",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 7, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceReadonly",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceUnrevisioned",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceUnvalidated",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceRawPut",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceNoncascading",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getInstancePrefetch",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 22, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isInstanceNonheritable",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGetViewInfo",
            new Class[] { Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIdentifiers", 12, 67, 8, null, null), 
              new com.linar.jintegra.Param("viewInfo", 17, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGetFieldInfo",
            new Class[] { Object.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldID", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("fieldInfo", 17, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exPut",
            new Class[] { Integer.TYPE, Object.class, Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("iD", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("value", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("verify", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedField", 17, 21, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoNext",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoPrev",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoTop",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoBottom",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoToBookMark",
            new Class[] { Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pBookMark", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRead",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exReadLock",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exFetch",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exFetchLock",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exInit",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exCancel",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exInsert",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exUpdate",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exPost",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exProcess",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGoToRecordNumber",
            new Class[] { Integer.TYPE, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("recordNumber", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exSetOrder",
            new Class[] { Integer.TYPE, Boolean.TYPE, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("order", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("unique", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exLookupValue",
            new Class[] { Object.class, Object.class, Integer.TYPE, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("order", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("targetFieldID", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("targetFieldValue", 12, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exCompose",
            new Class[] { String[].class, com.interweave.plugin.a4comsv.IAccpacSvrView.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewIDs", 8, 67, 8, null, null), 
              new com.linar.jintegra.Param("parentView", 29, 2, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exGetComposedView",
            new Class[] { String.class, com.interweave.plugin.a4comsv.IAccpacSvrView[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("viewID", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("view", 16413, 4, 4, com.interweave.plugin.a4comsv.IAccpacSvrView.IID, com.interweave.plugin.a4comsv.IAccpacSvrViewProxy.class), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exBlkGet",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exBlkPut",
            new Class[] { Object.class, Object.class, Boolean.TYPE, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("pValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("verify", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exVerify",
            new Class[] { Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRefreshPresentation",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exSetSystemAccess",
            new Class[] { int.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("systemAccess", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRecordClear",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRecordGenerate",
            new Class[] { Boolean.TYPE, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("insert", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRevisionCancel",
            new Class[] { Integer.TYPE, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exRevisionPost",
            new Class[] { Integer.TYPE, Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("level", 22, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("initPrimaryKeyFields",
            new Class[] { Object.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("bookmark", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getClientViewCache",
            new Class[] { byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("clientViewInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exFilterFetch",
            new Class[] { Boolean.TYPE, Object.class, Object.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("lock", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("putFieldValues", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exFilterSelect",
            new Class[] { String.class, Boolean.TYPE, Integer.TYPE, int.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("ascending", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("order", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("origin", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isUsedOpenViewInstance",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exInstanceNotify",
            new Class[] { Boolean.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("val", 11, 2, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("recordCreate",
            new Class[] { int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("flags", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("filterCount",
            new Class[] { String.class, Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getInstanceProtocol",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("exRecordCreate",
            new Class[] { int.class, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("flags", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exFilterCount",
            new Class[] { String.class, Integer.TYPE, Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("filter", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("flags", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("exInstanceProtocol",
            new Class[] { Object.class, byte[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("fieldIDs", 16396, 2, 8, null, null), 
              new com.linar.jintegra.Param("updatedInfo", 16401, 5, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
});  }
}
