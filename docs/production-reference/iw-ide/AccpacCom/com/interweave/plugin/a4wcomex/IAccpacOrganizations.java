package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacOrganizations'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC organization setups collection interface</B>'
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
public interface IAccpacOrganizations extends java.io.Serializable {
  /**
   * item. Returns the AccpacOrganization object by its name.
   *
   * @param     name The name (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization item  (
              String name) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * reset. Resets the current position to the beginning of the collection.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reset  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * next. Gets the next AccpacOrganization object in the collection.  Returns whether we got such an object.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization (out: use single element array)
   * @return    The fetched
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean next  (
              com.interweave.plugin.a4wcomex.IAccpacOrganization[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOrgsInfo. Gets the database ID, company name, associated system database ID, whether the database is a company or system database, and whether security is enabled for that company.
   *
   * @param     count The count (out: use single element array)
   * @param     databaseIDs The databaseIDs (out: use single element array)
   * @param     names The names (out: use single element array)
   * @param     systemDatabaseIDs The systemDatabaseIDs (out: use single element array)
   * @param     types The types (out: use single element array)
   * @param     secEnabled The secEnabled (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getOrgsInfo  (
              int[] count,
              String[][] databaseIDs,
              String[][] names,
              String[][] systemDatabaseIDs,
              short[][] types,
              boolean[][] secEnabled) throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getCount. property Count
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * itemByIndex. method ItemByIndex
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization itemByIndex  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IIDd1cd1545_c1ce_11d2_9bb4_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacOrganizationsProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "d1cd1545-c1ce-11d2-9bb4-00104b71eb3f";
  String DISPID_0_NAME = "item";
  String DISPID_3_NAME = "reset";
  String DISPID_4_NAME = "next";
  String DISPID_6_NAME = "getOrgsInfo";
  String DISPID_7_GET_NAME = "getCount";
  String DISPID_8_NAME = "itemByIndex";
}
