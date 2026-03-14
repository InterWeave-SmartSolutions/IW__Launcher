package com.interweave.plugin.a4comsv;

/**
 * COM Interface 'IAccpacSvrView'. Generated 12/10/2006 12:40:15 PM
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
public interface IAccpacSvrView extends java.io.Serializable {
  /**
   * close. Closes an open view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * cancel. Rolls back any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * init. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * delete. Removes the logical record from the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void delete  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * insert. Creates a new logical record in the database from the contents of the current logical record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insert  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * post. Commits any pending changes to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void post  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * process. Used by special types of views as a signal to perform the operations.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void process  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unLock. Unlocks the logical record locked by a FetchLock or ReadLock call.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * update. Writes the contents of the existing logical record back to the database.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void update  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * verify. Checks the referential integrity of the current record of the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetch. Retrieves the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetchLock. Retrieves and locks the next logical record according to the direction and filter set by the latest Browse call.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * read. Fetches the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean read  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * readLock. Fetches and locks the logical record indexed by the current contents of the view's key field(s).
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean ascending) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * compose. Composes the view with the supplied array of AccpacView objects.
   *
   * @param     viewOrArrayOfViews A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void compose  (
              Object viewOrArrayOfViews) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] pValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] fields) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * zz_clone. Creates a clone of the current AccpacView object.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrView zz_clone  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goTop. Navigates to the first record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goTop  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goBottom. Navigates to the last record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goBottom  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goToBookMark. Navigates to the record as specified by the bookmark that is obtained from the RecordBookmark property from a previous record that was navigated to.
   *
   * @param     pBookMark A Variant (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToBookMark  (
              Object pBookMark) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goNext. Navigates to the next record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goNext  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goPrev. Navigates to the previous record in the set of records according to the current browse filter and direction.  Returns whether the current record is moved or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goPrev  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isDirty. Reports whether fields have been altered.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isDirty  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isExists. Reports whether the current logical record exists in the database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isExists  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOrder. Returns/sets the order in which the view is accessed.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getOrder  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setOrder. Returns/sets the order in which the view is accessed.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrder  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Returns the type of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSecurity. Returns the access rights the current user is assigned on the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              String[][] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFields. Returns the AccpacViewFields collection object that represents all the fields in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getKeys. Returns the AccpacViewKeys collection object that represents all the keys in the view.
   *
   * @return    An reference to a com.interweave.plugin.a4comsv.IAccpacSvrViewKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4comsv.IAccpacSvrViewKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRecordBookMark. Returns the bookmark of the current logical record that could be passed later on to the GotoBookmark method.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getRecordBookMark  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDescription. Returns the description of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isUnpostedRevisions. Returns whether there are unposted revisions in the view or not.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUnpostedRevisions  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSystemAccess. Returns/sets the system access flags of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSystemAccess  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setSystemAccess. Returns/sets the system access flags of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrSystemAccessEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setSystemAccess  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getReferentialIntegrity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setReferentialIntegrity. Returns/sets the referential integrity flag of the view.
   *
   * @param     pVal A com.interweave.plugin.a4comsv.tagSvrReferentialIntegrityEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setReferentialIntegrity  (
              int pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getViewID. Returns the ID (Roto ID) of the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getViewID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewReturnCode constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int[] recNum) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] getFieldValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUseRecordNumbering  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setUseRecordNumbering. Returns/sets whether record numbers would be generated for each record.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setUseRecordNumbering  (
              boolean pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getRecordNumber. Returns the record number of the current record.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getRecordNumber  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * goToRecordNumber. Goes to the specified record number. Returns whether the record is found or not.
   *
   * @param     recordNumber The recordNumber (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean goToRecordNumber  (
              int recordNumber) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              Object[] values) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * resetRecordNumbers. Resets all generated record numbers. Record numbers would be re-generated the next time any method is called that positions to a record.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void resetRecordNumbers  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * macroAppend. Appends a line to a macro.
   *
   * @param     cmdInString The cmdInString (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void macroAppend  (
              String cmdInString) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getHeaderLinkedKeyFieldCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setHeaderLinkedKeyFieldCount. Returns/sets the number of key fields linked to the header view.
   *
   * @param     pVal The pVal (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setHeaderLinkedKeyFieldCount  (
              short pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordClear. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordGenerate. method RecordGenerate
   *
   * @param     insert The insert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean insert) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getInstanceSecurity. property InstanceSecurity
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewSecurityEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * tableEmpty. method TableEmpty
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void tableEmpty  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionCancel. method RevisionCancel
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionCancel  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionPost. method RevisionPost
   *
   * @param     level The level (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void revisionPost  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionExists. method RevisionExists
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionExists  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * revisionUnposted. method RevisionUnposted
   *
   * @param     level The level (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean revisionUnposted  (
              int level) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int strictness) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int origin) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * filterFetch. method FilterFetch
   *
   * @param     lock The lock (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean filterFetch  (
              boolean lock) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getTemplateVersion. property TemplateVersion
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getTemplateVersion  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getTemplateDate. property TemplateDate
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public java.util.Date getTemplateDate  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceReadonly. property InstanceReadonly
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceReadonly  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceUnrevisioned. property InstanceUnrevisioned
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnrevisioned  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceUnvalidated. property InstanceUnvalidated
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceUnvalidated  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceRawPut. property InstanceRawPut
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceRawPut  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceNoncascading. property InstanceNoncascading
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNoncascading  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getInstancePrefetch. property InstancePrefetch
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstancePrefetch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isInstanceNonheritable. property InstanceNonheritable
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isInstanceNonheritable  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * exGetViewInfo. 
   *
   * @param     fieldIdentifiers A Variant (in)
   * @return    An unsigned byte
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public byte[] exGetViewInfo  (
              Object[] fieldIdentifiers) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int fieldIDType) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              boolean verify) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int targetFieldID) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4comsv.IAccpacSvrView parentView) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              com.interweave.plugin.a4comsv.IAccpacSvrView[] view) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * initPrimaryKeyFields. 
   *
   * @param     bookmark A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void initPrimaryKeyFields  (
              Object bookmark) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getClientViewCache. 
   *
   * @param     clientViewInfo An unsigned byte (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getClientViewCache  (
              byte[][] clientViewInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isUsedOpenViewInstance. Returns whether the view was opened with .OpenViewInstance
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isUsedOpenViewInstance  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * exInstanceNotify. Sets whether notifications from the view are received.
   *
   * @param     val The val (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void exInstanceNotify  (
              boolean val) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordCreate. method RecordCreate
   *
   * @param     flags A com.interweave.plugin.a4comsv.tagSvrViewRecordCreateEnum constant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordCreate  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getInstanceProtocol. method InstanaceProtocol
   *
   * @return    A com.interweave.plugin.a4comsv.tagSvrViewInstanceProtocolEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getInstanceProtocol  () throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;

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
              byte[][] updatedInfo) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDad3832d7_3022_47f1_b10c_981fb19e2451 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacSvrViewProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "ad3832d7-3022-47f1-b10c-981fb19e2451";
  String DISPID_1_NAME = "close";
  String DISPID_2_NAME = "cancel";
  String DISPID_3_NAME = "init";
  String DISPID_4_NAME = "delete";
  String DISPID_5_NAME = "insert";
  String DISPID_6_NAME = "post";
  String DISPID_7_NAME = "process";
  String DISPID_8_NAME = "unLock";
  String DISPID_9_NAME = "update";
  String DISPID_10_NAME = "verify";
  String DISPID_11_NAME = "fetch";
  String DISPID_12_NAME = "fetchLock";
  String DISPID_13_NAME = "read";
  String DISPID_14_NAME = "readLock";
  String DISPID_15_NAME = "browse";
  String DISPID_16_NAME = "compose";
  String DISPID_17_NAME = "blkGet";
  String DISPID_18_NAME = "blkPut";
  String DISPID_19_NAME = "blkGetFields";
  String DISPID_20_NAME = "zz_clone";
  String DISPID_21_NAME = "goTop";
  String DISPID_22_NAME = "goBottom";
  String DISPID_23_NAME = "goToBookMark";
  String DISPID_24_NAME = "goNext";
  String DISPID_25_NAME = "goPrev";
  String DISPID_26_GET_NAME = "isDirty";
  String DISPID_27_GET_NAME = "isExists";
  String DISPID_28_GET_NAME = "getOrder";
  String DISPID_28_PUT_NAME = "setOrder";
  String DISPID_29_GET_NAME = "getType";
  String DISPID_30_GET_NAME = "getSecurity";
  String DISPID_31_GET_NAME = "getCompositeNames";
  String DISPID_32_GET_NAME = "getFields";
  String DISPID_33_GET_NAME = "getKeys";
  String DISPID_35_GET_NAME = "getRecordBookMark";
  String DISPID_36_GET_NAME = "getDescription";
  String DISPID_37_GET_NAME = "isUnpostedRevisions";
  String DISPID_38_GET_NAME = "getSystemAccess";
  String DISPID_38_PUT_NAME = "setSystemAccess";
  String DISPID_39_GET_NAME = "getReferentialIntegrity";
  String DISPID_39_PUT_NAME = "setReferentialIntegrity";
  String DISPID_40_GET_NAME = "getViewID";
  String DISPID_41_GET_NAME = "getLastReturnCode";
  String DISPID_47_NAME = "goTopEx";
  String DISPID_48_NAME = "goBottomEx";
  String DISPID_49_NAME = "goToBookMarkEx";
  String DISPID_50_NAME = "goNextEx";
  String DISPID_51_NAME = "goPrevEx";
  String DISPID_52_NAME = "fetchEx";
  String DISPID_53_NAME = "fetchLockEx";
  String DISPID_54_NAME = "readEx";
  String DISPID_55_NAME = "readLockEx";
  String DISPID_56_NAME = "initEx";
  String DISPID_57_NAME = "cancelEx";
  String DISPID_58_NAME = "updateEx";
  String DISPID_59_NAME = "insertEx";
  String DISPID_60_NAME = "processEx";
  String DISPID_61_NAME = "postEx";
  String DISPID_62_NAME = "verifyEx";
  String DISPID_63_GET_NAME = "isUseRecordNumbering";
  String DISPID_63_PUT_NAME = "setUseRecordNumbering";
  String DISPID_64_GET_NAME = "getRecordNumber";
  String DISPID_65_NAME = "goToRecordNumber";
  String DISPID_66_NAME = "goToRecordNumberEx";
  String DISPID_67_NAME = "resetRecordNumbers";
  String DISPID_68_NAME = "macroAppend";
  String DISPID_69_GET_NAME = "getHeaderLinkedKeyFieldCount";
  String DISPID_69_PUT_NAME = "setHeaderLinkedKeyFieldCount";
  String DISPID_70_NAME = "recordClear";
  String DISPID_71_NAME = "recordGenerate";
  String DISPID_72_GET_NAME = "getInstanceSecurity";
  String DISPID_73_NAME = "tableEmpty";
  String DISPID_74_NAME = "revisionCancel";
  String DISPID_75_NAME = "revisionPost";
  String DISPID_76_NAME = "revisionExists";
  String DISPID_77_NAME = "revisionUnposted";
  String DISPID_78_NAME = "filterDelete";
  String DISPID_79_NAME = "filterSelect";
  String DISPID_80_NAME = "filterFetch";
  String DISPID_81_GET_NAME = "getTemplateVersion";
  String DISPID_82_GET_NAME = "getTemplateDate";
  String DISPID_83_GET_NAME = "isInstanceReadonly";
  String DISPID_84_GET_NAME = "isInstanceUnrevisioned";
  String DISPID_85_GET_NAME = "isInstanceUnvalidated";
  String DISPID_86_GET_NAME = "isInstanceRawPut";
  String DISPID_87_GET_NAME = "isInstanceNoncascading";
  String DISPID_88_GET_NAME = "getInstancePrefetch";
  String DISPID_89_GET_NAME = "isInstanceNonheritable";
  String DISPID_90_NAME = "exGetViewInfo";
  String DISPID_91_NAME = "exGetFieldInfo";
  String DISPID_92_NAME = "exPut";
  String DISPID_93_NAME = "exGoNext";
  String DISPID_94_NAME = "exGoPrev";
  String DISPID_95_NAME = "exGoTop";
  String DISPID_96_NAME = "exGoBottom";
  String DISPID_97_NAME = "exGoToBookMark";
  String DISPID_98_NAME = "exRead";
  String DISPID_99_NAME = "exReadLock";
  String DISPID_100_NAME = "exFetch";
  String DISPID_101_NAME = "exFetchLock";
  String DISPID_102_NAME = "exInit";
  String DISPID_103_NAME = "exCancel";
  String DISPID_104_NAME = "exInsert";
  String DISPID_105_NAME = "exUpdate";
  String DISPID_106_NAME = "exPost";
  String DISPID_107_NAME = "exProcess";
  String DISPID_108_NAME = "exGoToRecordNumber";
  String DISPID_109_NAME = "exSetOrder";
  String DISPID_110_NAME = "exLookupValue";
  String DISPID_111_NAME = "exCompose";
  String DISPID_112_NAME = "exGetComposedView";
  String DISPID_113_NAME = "exBlkGet";
  String DISPID_114_NAME = "exBlkPut";
  String DISPID_115_NAME = "exVerify";
  String DISPID_116_NAME = "exRefreshPresentation";
  String DISPID_117_NAME = "exSetSystemAccess";
  String DISPID_118_NAME = "exRecordClear";
  String DISPID_119_NAME = "exRecordGenerate";
  String DISPID_120_NAME = "exRevisionCancel";
  String DISPID_121_NAME = "exRevisionPost";
  String DISPID_122_NAME = "initPrimaryKeyFields";
  String DISPID_123_NAME = "getClientViewCache";
  String DISPID_124_NAME = "exFilterFetch";
  String DISPID_125_NAME = "exFilterSelect";
  String DISPID_126_GET_NAME = "isUsedOpenViewInstance";
  String DISPID_127_NAME = "exInstanceNotify";
  String DISPID_128_NAME = "recordCreate";
  String DISPID_129_NAME = "filterCount";
  String DISPID_130_GET_NAME = "getInstanceProtocol";
  String DISPID_131_NAME = "exRecordCreate";
  String DISPID_132_NAME = "exFilterCount";
  String DISPID_133_NAME = "exInstanceProtocol";
}
