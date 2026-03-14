package com.interweave.plugin.a4wcomex;

/**
 * COM Interface 'IAccpacOrganization'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC organization setup interface</B>'
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
public interface IAccpacOrganization extends java.io.Serializable {
  /**
   * getName. Returns the name of the company.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getDatabaseID. Returns the company ID.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDatabaseID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getType. Returns whether the database is a company or system database.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagOrganizationTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getSystemDatabaseID. Returns the associated system database ID if the current one is a company database.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSystemDatabaseID  () throws java.io.IOException, com.linar.jintegra.AutomationException;

  /**
   * getOrgInfo. Gets the database ID, company name, associated system database ID, whether the database is a company or system database, and whether security is enabled for that company.
   *
   * @param     databaseID The databaseID (out: use single element array)
   * @param     name The name (out: use single element array)
   * @param     systemDatabaseID The systemDatabaseID (out: use single element array)
   * @param     type A com.interweave.plugin.a4wcomex.tagOrganizationTypeEnum constant (out: use single element array)
   * @param     secEnabled The secEnabled (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getOrgInfo  (
              String[] databaseID,
              String[] name,
              String[] systemDatabaseID,
              int[] type,
              boolean[] secEnabled) throws java.io.IOException, com.linar.jintegra.AutomationException;


  // Constants to help J-Integra for COM dynamically map DCOM invocations to
  // interface members.  Don't worry, you will never need to explicitly use these constants.
  int IID0aacfc45_c1d2_11d2_9bb4_00104b71eb3f = 1;
  /** Dummy reference to interface proxy to make sure it gets compiled */
  int xxDummy = IAccpacOrganizationProxy.xxDummy;
  /** Used internally by J-Integra for COM, please ignore */
  String IID = "0aacfc45-c1d2-11d2-9bb4-00104b71eb3f";
  String DISPID_1_GET_NAME = "getName";
  String DISPID_2_GET_NAME = "getDatabaseID";
  String DISPID_3_GET_NAME = "getType";
  String DISPID_4_GET_NAME = "getSystemDatabaseID";
  String DISPID_5_NAME = "getOrgInfo";
}
