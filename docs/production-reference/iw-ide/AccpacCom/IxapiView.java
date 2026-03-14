package com.interweave.plugin.A4WCOM;

/**
 * COM Interface 'IxapiView'. Generated 06/10/2006 6:20:01 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\Temp\ITI\ttt\ACCPAC\A4WCOM.DLL'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>IxapiView Interface</B>'
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
public interface IxapiView extends java.io.Serializable {
  /**
   * getID. Roto object string
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getPgmID. Two-letter program ID
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPgmID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSecurity. Operations available to the user in the given database link
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSecurity  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Type of the view
   *
   * @return    A com.interweave.plugin.A4WCOM.tagRotoViewTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSubviewNames. Collection of names for the subviews composed with a view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiSubviewNames
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiSubviewNames getSubviewNames  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setOrder. Determines the order in which a view is accessed
   *
   * @param     rhs1 The rhs1 (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setOrder  (
              int rhs1) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * compose. Specifies the subviews with which the view composes
   *
   * @param     viewOrViews A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void compose  (
              Object viewOrViews) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * createTables. Creates the tables maintained and needed by the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void createTables  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * dropTables. Deletes tables created by CreateTables method
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void dropTables  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * browse. Starts a query
   *
   * @param     filter The filter (in)
   * @param     ascending The ascending (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void browse  (
              String filter,
              int ascending) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetch. Retrieves the next logical record
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetch  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * fetchLock. Retrieves the next logical record and locks it
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int fetchLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * post. Commits any pending changes to the database
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void post  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * cancel. Rolls back any pending changes to the database
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void cancel  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * process. Initiates special(view dependent) operations
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void process  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * init. Blanks, zeroes, or defaults the contents of each field in the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void init  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * verify. Checks the referential integrity of the current record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void verify  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDirty. Reports whether fields have been altered with put method
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getDirty  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getExists. Reports whether the current logical record exists in database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getExists  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * read. Fetches the logical record indexed by the current contents of the view's key fields
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int read  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * readLock. Fetches and locks the logical record indexed by the current contents of the view's key fields
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int readLock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * unlock. Unlocks the logical record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void unlock  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * update. Writes the contents of the existing logical record back to the database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int update  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * insert. Creates new record in the database from the contents of the current logical record
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void insert  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * delete. Removes the logical record from database
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int delete  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getFields. Returns the collection of fields available in the view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiFields
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiFields getFields  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getKeys. Returns the collection of keys available in the view
   *
   * @return    An reference to a com.interweave.plugin.A4WCOM.IxapiKeys
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.A4WCOM.IxapiKeys getKeys  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * blkGet. takes an array of Field objects and reads values of the fields in a single operation
   *
   * @param     fields A Variant (in)
   * @param     pValues A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkGet  (
              Object fields,
              Object[] pValues) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * blkPut. takes an array of Field objects and array of values, and stores the values of the fields into the view
   *
   * @param     fields A Variant (in)
   * @param     values A Variant (in)
   * @param     verify The verify (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void blkPut  (
              Object fields,
              Object values,
              int verify) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * close. method closes the view
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void close  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordGenerate. RecordGenerate
   *
   * @param     bInsert The bInsert (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordGenerate  (
              boolean bInsert) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * recordClear. Blanks, zeroes, or defaults the contents of each field in the view.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void recordClear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * isWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @return    The bThrowIt
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isWarningIsException  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * setWarningIsException. if set to TRUE a View Warning will result in an Exception. Default is TRUE 
   *
   * @param     bThrowIt The bThrowIt (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setWarningIsException  (
              boolean bThrowIt) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getLastReturnCode. Returns the ACCPAC API return code of the last view operation.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getLastReturnCode  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * filterSelect. method FilterSelect
   *
   * @param     key The key (in)
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterSelect  (
              int key,
              String filter,
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * filterFetch. method FilterFetch
   *
   * @param     flags The flags (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int filterFetch  (
              int flags) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * filterDelete. method FilterDelete
   *
   * @param     filter The filter (in)
   * @param     flags The flags (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void filterDelete  (
              String filter,
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
   * instanceProtocol. method InstanceProtocol
   *
   * @param     flags The flags (in)
   * @param     protocols The protocols (out: use single element array)
   * @param     count The count (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void instanceProtocol  (
              int flags,
              int[] protocols,
              int[] count) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID351fa047_8c34_11d1_b5ab_0060083b07c8 = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IxapiViewProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "351fa047-8c34-11d1-b5ab-0060083b07c8";
  String DISPID_1_GET_NAME = "getID";
  String DISPID_2_GET_NAME = "getPgmID";
  String DISPID_3_GET_NAME = "getSecurity";
  String DISPID_4_GET_NAME = "getType";
  String DISPID_6_GET_NAME = "getSubviewNames";
  String DISPID_7_PUT_NAME = "setOrder";
  String DISPID_8_NAME = "compose";
  String DISPID_9_NAME = "createTables";
  String DISPID_10_NAME = "dropTables";
  String DISPID_11_NAME = "browse";
  String DISPID_12_NAME = "fetch";
  String DISPID_13_NAME = "fetchLock";
  String DISPID_14_NAME = "post";
  String DISPID_15_NAME = "cancel";
  String DISPID_16_NAME = "process";
  String DISPID_17_NAME = "init";
  String DISPID_18_NAME = "verify";
  String DISPID_19_GET_NAME = "getDirty";
  String DISPID_20_GET_NAME = "getExists";
  String DISPID_21_NAME = "read";
  String DISPID_22_NAME = "readLock";
  String DISPID_23_NAME = "unlock";
  String DISPID_24_NAME = "update";
  String DISPID_25_NAME = "insert";
  String DISPID_26_NAME = "delete";
  String DISPID_27_GET_NAME = "getFields";
  String DISPID_28_GET_NAME = "getKeys";
  String DISPID_29_NAME = "blkGet";
  String DISPID_30_NAME = "blkPut";
  String DISPID_31_NAME = "close";
  String DISPID_32_NAME = "recordGenerate";
  String DISPID_33_NAME = "recordClear";
  String DISPID_34_GET_NAME = "isWarningIsException";
  String DISPID_34_PUT_NAME = "setWarningIsException";
  String DISPID_35_GET_NAME = "getLastReturnCode";
  String DISPID_36_NAME = "filterSelect";
  String DISPID_37_NAME = "filterFetch";
  String DISPID_38_NAME = "filterDelete";
  String DISPID_39_NAME = "filterCount";
  String DISPID_40_NAME = "instanceProtocol";
}
