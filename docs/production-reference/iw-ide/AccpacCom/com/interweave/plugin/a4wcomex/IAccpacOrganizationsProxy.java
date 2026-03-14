package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacOrganizations'. Generated 02/10/2006 12:21:34 PM
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
public class IAccpacOrganizationsProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacOrganizations, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacOrganizations.class;

  public IAccpacOrganizationsProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacOrganizations.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacOrganizationsProxy() {}

  public IAccpacOrganizationsProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacOrganizations.IID);
  }

  protected IAccpacOrganizationsProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacOrganizationsProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * item. Returns the AccpacOrganization object by its name.
   *
   * @param     name The name (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization item  (
              String name) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacOrganization zz_retVal[] = { null };
    Object zz_parameters[] = { name, zz_retVal };
    vtblInvoke("item", 7, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacOrganization)zz_retVal[0];
  }

  /**
   * reset. Resets the current position to the beginning of the collection.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void reset  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("reset", 8, zz_parameters);
    return;
  }

  /**
   * next. Gets the next AccpacOrganization object in the collection.  Returns whether we got such an object.
   *
   * @param     pVal An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization (out: use single element array)
   * @return    The fetched
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean next  (
              com.interweave.plugin.a4wcomex.IAccpacOrganization[] pVal) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { pVal, zz_retVal };
    vtblInvoke("next", 9, zz_parameters);
    return zz_retVal[0];
  }

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
              boolean[][] secEnabled) throws java.io.IOException, com.linar.jintegra.AutomationException{
    Object zz_retVal[] = { null };
    Object zz_parameters[] = { count, databaseIDs, names, systemDatabaseIDs, types, secEnabled, zz_retVal };
    vtblInvoke("getOrgsInfo", 10, zz_parameters);
    return;
  }

  /**
   * getCount. property Count
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getCount  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getCount", 11, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * itemByIndex. method ItemByIndex
   *
   * @param     index The index (in)
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacOrganization
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacOrganization itemByIndex  (
              int index) throws java.io.IOException, com.linar.jintegra.AutomationException{
    com.interweave.plugin.a4wcomex.IAccpacOrganization zz_retVal[] = { null };
    Object zz_parameters[] = { new Integer(index), zz_retVal };
    vtblInvoke("itemByIndex", 12, zz_parameters);
    return (com.interweave.plugin.a4wcomex.IAccpacOrganization)zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("d1cd1545-c1ce-11d2-9bb4-00104b71eb3f", com.interweave.plugin.a4wcomex.IAccpacOrganizationsProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("item",
            new Class[] { String.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("name", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacOrganization.IID, com.interweave.plugin.a4wcomex.IAccpacOrganizationProxy.class) }),
        new com.linar.jintegra.MemberDesc("reset",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("next",
            new Class[] { com.interweave.plugin.a4wcomex.IAccpacOrganization[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 16413, 4, 4, com.interweave.plugin.a4wcomex.IAccpacOrganization.IID, com.interweave.plugin.a4wcomex.IAccpacOrganizationProxy.class), 
              new com.linar.jintegra.Param("fetched", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getOrgsInfo",
            new Class[] { int[].class, String[][].class, String[][].class, String[][].class, short[][].class, boolean[][].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("count", 16387, 4, 8, null, null), 
              new com.linar.jintegra.Param("databaseIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("names", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("systemDatabaseIDs", 16392, 5, 8, null, null), 
              new com.linar.jintegra.Param("types", 16386, 5, 8, null, null), 
              new com.linar.jintegra.Param("secEnabled", 16395, 5, 8, null, null), 
              new com.linar.jintegra.Param("", 24, 0, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getCount",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("itemByIndex",
            new Class[] { Integer.TYPE, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("index", 3, 2, 8, null, null), 
              new com.linar.jintegra.Param("pVal", 29, 20, 4, com.interweave.plugin.a4wcomex.IAccpacOrganization.IID, com.interweave.plugin.a4wcomex.IAccpacOrganizationProxy.class) }),
});  }
}
