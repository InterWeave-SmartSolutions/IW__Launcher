package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacErrors'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC errors collection interface</B>'
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
public interface IAccpacErrors extends java.io.Serializable {
  /**
   * clear. Clears the collection of errors.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void clear  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCount. Returns the number of errors contained in the collection.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * generateErrorFile. Generates a temporary file that stores the errors in the collection. Returns the path and file name of the generated file.
   *
   * @return    The pFilePath
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String generateErrorFile  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * item. Returns the error based on the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String item  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * put. Adds a new error message to the collection.
   *
   * @param     msg The msg (in)
   * @param     priority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (in)
   * @param     params A Variant (in, optional, pass null if not required)
   * @param     source The source (in, optional, pass null if not required)
   * @param     errCode The errCode (in, optional, pass null if not required)
   * @param     helpFile The helpFile (in, optional, pass null if not required)
   * @param     helpContextID The helpContextID (in, optional, pass 0 if not required)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void put  (
              String msg,
              int priority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * get. Returns the error based on the 0-based index in the collection.
   *
   * @param     index The index (in)
   * @param     pMsg The pMsg (out: use single element array)
   * @param     pPriority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (out: use single element array)
   * @param     pSource The pSource (out: use single element array)
   * @param     pErrCode The pErrCode (out: use single element array)
   * @param     pHelpFile The pHelpFile (out: use single element array)
   * @param     pHelpID The pHelpID (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void get  (
              int index,
              String[] pMsg,
              int[] pPriority,
              String[] pSource,
              String[] pErrCode,
              String[] pHelpFile,
              int[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * putRscMsg. Adds a new error message (pulled from the resource file of the specified application) to the collection.
   *
   * @param     appID The appID (in)
   * @param     rscID The rscID (in)
   * @param     priority A com.interweave.plugin.a4wcomex.tagErrorPriority constant (in)
   * @param     params A Variant (in, optional, pass null if not required)
   * @param     source The source (in, optional, pass null if not required)
   * @param     errCode The errCode (in, optional, pass null if not required)
   * @param     helpFile The helpFile (in, optional, pass null if not required)
   * @param     helpContextID The helpContextID (in, optional, pass 0 if not required)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putRscMsg  (
              String appID,
              int rscID,
              int priority,
              Object params,
              String source,
              String errCode,
              String helpFile,
              int helpContextID) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * get2. Returns the error based on the 0-based index in the collection. Script languages should use this function.
   *
   * @param     index The index (in)
   * @param     pMsg A Variant (out: use single element array)
   * @param     pPriority A Variant (out: use single element array)
   * @param     pSource A Variant (out: use single element array)
   * @param     pErrCode A Variant (out: use single element array)
   * @param     pHelpFile A Variant (out: use single element array)
   * @param     pHelpID A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void get2  (
              int index,
              Object[] pMsg,
              Object[] pPriority,
              Object[] pSource,
              Object[] pErrCode,
              Object[] pHelpFile,
              Object[] pHelpID) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID3b8ac495_bf95_11d2_9bb0_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacErrorsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "3b8ac495-bf95-11d2-9bb0-00104b71eb3f";
  String DISPID_1_NAME = "clear";
  String DISPID_2_GET_NAME = "getCount";
  String DISPID_3_NAME = "generateErrorFile";
  String DISPID_0_NAME = "item";
  String DISPID_4_NAME = "put";
  String DISPID_5_NAME = "get";
  String DISPID_6_NAME = "putRscMsg";
  String DISPID_7_NAME = "get2";
}
