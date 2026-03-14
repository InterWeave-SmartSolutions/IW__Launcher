package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacCurrency'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>ACCPAC currency information interface</B>'
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
public class IAccpacCurrencyProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacCurrency, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacCurrency.class;

  public IAccpacCurrencyProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacCurrency.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacCurrencyProxy() {}

  public IAccpacCurrencyProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacCurrency.IID);
  }

  protected IAccpacCurrencyProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacCurrencyProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getDescription. Returns the currency code description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDescription", 7, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getSymbol. Returns the currency symbol.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getSymbol  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    String zz_retVal[] = { null };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSymbol", 8, zz_parameters);
    return (String)zz_retVal[0];
  }

  /**
   * getDecimals. Returns the number of decimal places (0, 1, 2 or 3) for the currency.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDecimals  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDecimals", 9, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getSymbolDisplay. Returns the display position for the currency symbol.
   *
   * @return    A com.interweave.proxy.tagCurrencySymbolDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getSymbolDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getSymbolDisplay", 10, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getNegativeDisplay. Returns the method used to indicate negative amounts.
   *
   * @return    A com.interweave.proxy.tagNegativeDisplayEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getNegativeDisplay  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    int zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getNegativeDisplay", 11, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getThousandsSep. Returns the thousands group separator.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getThousandsSep  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getThousandsSep", 12, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getDecimalSep. Returns the decimal group separator.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getDecimalSep  () throws java.io.IOException, com.linar.jintegra.AutomationException{
    short zz_retVal[] = { 0 };
    Object zz_parameters[] = { zz_retVal };
    vtblInvoke("getDecimalSep", 13, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isBlockCombinationWith. Determines if the currency and the specified currency code belong in the same currency block, both as members, or one as a member and the other as its master.
   *
   * @param     curCode The curCode (in)
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @return    The isCombination
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockCombinationWith  (
              String curCode,
              java.util.Date date,
              int blockDateMatch) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { curCode, date, new Integer(blockDateMatch), zz_retVal };
    vtblInvoke("isBlockCombinationWith", 14, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isBlockMaster. Determines if the currency is a block master currency.
   *
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @return    The isMaster
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockMaster  (
              java.util.Date date,
              int blockDateMatch) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, new Integer(blockDateMatch), zz_retVal };
    vtblInvoke("isBlockMaster", 15, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * isBlockMember. Determines if the currency is a member of a currency block, and optionally retrieves the currency rate information with its master.
   *
   * @param     date The date (in)
   * @param     blockDateMatch A com.interweave.plugin.a4wcomex.tagBlockDateMatchEnum constant (in)
   * @param     currencyRate An reference to a com.interweave.plugin.a4wcomex.IAccpacCurrencyRate (out: use single element array, optional, pass single element of null if not required)
   * @return    The isMember
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean isBlockMember  (
              java.util.Date date,
              int blockDateMatch,
              com.interweave.plugin.a4wcomex.IAccpacCurrencyRate[] currencyRate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, new Integer(blockDateMatch), currencyRate, zz_retVal };
    vtblInvoke("isBlockMember", 16, zz_parameters);
    return zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("0cf93b33-9d1d-11d3-9fca-00c04f815d63", com.interweave.plugin.a4wcomex.IAccpacCurrencyProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getDescription",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSymbol",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 8, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDecimals",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getSymbolDisplay",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getNegativeDisplay",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 3, 20, 0, "00000000-0000-0000-0000-000000000000", null) }),
        new com.linar.jintegra.MemberDesc("getThousandsSep",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getDecimalSep",
            new Class[] { },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("pVal", 2, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isBlockCombinationWith",
            new Class[] { String.class, java.util.Date.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("curCode", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("isCombination", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isBlockMaster",
            new Class[] { java.util.Date.class, int.class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("isMaster", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("isBlockMember",
            new Class[] { java.util.Date.class, int.class, com.interweave.plugin.a4wcomex.IAccpacCurrencyRate[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("blockDateMatch", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("currencyRate", 16413, 12, 4, com.interweave.plugin.a4wcomex.IAccpacCurrencyRate.IID, com.interweave.plugin.a4wcomex.IAccpacCurrencyRateProxy.class), 
              new com.linar.jintegra.Param("isMember", 11, 20, 8, null, null) }),
});  }
}
