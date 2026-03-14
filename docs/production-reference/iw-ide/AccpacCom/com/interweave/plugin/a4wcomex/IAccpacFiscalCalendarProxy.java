package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;
/**
 * Proxy for COM Interface 'IAccpacFiscalCalendar'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description: '<B>Fiscal calendar information interface</B>'
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
public class IAccpacFiscalCalendarProxy extends com.linar.jintegra.Dispatch implements com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar, java.io.Serializable {

  protected String getJintegraVersion() { return "2.6"; }

  static { com.interweave.plugin.a4wcomex.JIntegraInit.init(); }

  public static final Class targetClass = IAccpacFiscalCalendar.class;

  public IAccpacFiscalCalendarProxy(String CLSID, String host, AuthInfo authInfo) throws java.net.UnknownHostException, java.io.IOException{
    super(CLSID, IAccpacFiscalCalendar.IID, host, authInfo);
  }

  /** For internal use only */
  public IAccpacFiscalCalendarProxy() {}

  public IAccpacFiscalCalendarProxy(Object obj) throws java.io.IOException {
    super(obj, IAccpacFiscalCalendar.IID);
  }

  protected IAccpacFiscalCalendarProxy(Object obj, String iid) throws java.io.IOException {
    super(obj, iid);
  }

  protected IAccpacFiscalCalendarProxy(String CLSID, String iid, String host, AuthInfo authInfo) throws java.io.IOException {
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
   * getFirstYear. Looks up the first fiscal year in the calendar and retrieves the year, the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year is found or not.
   *
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getFirstYear  (
              String[] year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("getFirstYear", 7, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getLastYear. Looks up the last fiscal year in the calendar and retrieves the year, the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year is found or not.
   *
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getLastYear  (
              String[] year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("getLastYear", 8, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getYear. Looks up the specified fiscal year in the calendar and retrieves the number of fiscal periods, the quarter with 4 periods if there are 13 periods, and whether it is active or not. Returns whether the year exists in the calendar or not.
   *
   * @param     year The year (in)
   * @param     fiscalPeriods The fiscalPeriods (out: use single element array, optional, pass single element of null if not required)
   * @param     qtr4Period The qtr4Period (out: use single element array, optional, pass single element of null if not required)
   * @param     active The active (out: use single element array, optional, pass single element of null if not required)
   * @return    The found
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getYear  (
              String year,
              short[] fiscalPeriods,
              short[] qtr4Period,
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, fiscalPeriods, qtr4Period, active, zz_retVal };
    vtblInvoke("getYear", 9, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPeriod. Looks up the specified date and retrieves the period and year in the calendar, and whether the period is open or not. Returns whether the date is a valid date in the calendar.
   *
   * @param     date The date (in)
   * @param     period The period (out: use single element array, optional, pass single element of null if not required)
   * @param     year The year (out: use single element array, optional, pass single element of null if not required)
   * @param     periodOpen The periodOpen (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getPeriod  (
              java.util.Date date,
              short[] period,
              String[] year,
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, period, year, periodOpen, zz_retVal };
    vtblInvoke("getPeriod", 10, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getPeriodDates. Looks up the specified year and period in the calendar and retrieves the start and end dates for the period, and whether the period is open or not. Returns whether the year and period exist in the calendar.
   *
   * @param     year The year (in)
   * @param     period The period (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @param     periodOpen The periodOpen (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getPeriodDates  (
              String year,
              short period,
              java.util.Date[] startDate,
              java.util.Date[] endDate,
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(period), startDate, endDate, periodOpen, zz_retVal };
    vtblInvoke("getPeriodDates", 11, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getQuarter. Looks up the specified year and period in the calendar and retrieves the quarter that the period is a part of. Returns whether the period and year exist in the calendar or not.
   *
   * @param     year The year (in)
   * @param     period The period (in)
   * @param     quarter The quarter (out: use single element array)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getQuarter  (
              String year,
              short period,
              short[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(period), quarter, zz_retVal };
    vtblInvoke("getQuarter", 12, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getQuarterDates. Looks up the specified year and quarter in the calendar and retrieves the start and end dates of the quarter. Returns whether the year and quarter exist in the calendar or not.
   *
   * @param     year The year (in)
   * @param     quarter The quarter (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getQuarterDates  (
              String year,
              short quarter,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(quarter), startDate, endDate, zz_retVal };
    vtblInvoke("getQuarterDates", 13, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getYearDates. Looks up the specified year in the calendar and retrieves the start and end dates of the year. Returns whether the year exists in the calendar or not.
   *
   * @param     year The year (in)
   * @param     startDate The startDate (out: use single element array, optional, pass single element of null if not required)
   * @param     endDate The endDate (out: use single element array, optional, pass single element of null if not required)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getYearDates  (
              String year,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, startDate, endDate, zz_retVal };
    vtblInvoke("getYearDates", 14, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * datesFromPeriod. Calculates the period start and end dates, given a period number, period type, period length and base date. Returns whether or not the start and end dates were successfully determined.
   *
   * @param     period The period (in)
   * @param     periodType A com.interweave.plugin.a4wcomex.tagPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     startDate The startDate (out: use single element array)
   * @param     endDate The endDate (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean datesFromPeriod  (
              short period,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              java.util.Date[] startDate,
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Short(period), new Integer(periodType), new Short(periodLength), baseDate, startDate, endDate, zz_retVal };
    vtblInvoke("datesFromPeriod", 15, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * dateToPeriod. Calculates the period number, given a date, period type, period length and base date. Returns whether or not the period number was successfully determined.
   *
   * @param     date The date (in)
   * @param     periodType A com.interweave.plugin.a4wcomex.tagPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     period The period (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean dateToPeriod  (
              java.util.Date date,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              short[] period) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, new Integer(periodType), new Short(periodLength), baseDate, period, zz_retVal };
    vtblInvoke("dateToPeriod", 16, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * getQuarter2. Looks up the specified year and period in the calendar and retrieves the quarter that the period is a part of. Returns whether the period and year exist in the calendar or not. Script languages should use this function.
   *
   * @param     year The year (in)
   * @param     period The period (in)
   * @param     quarter A Variant (out: use single element array)
   * @return    The dateInFiscal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean getQuarter2  (
              String year,
              short period,
              Object[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { year, new Short(period), quarter, zz_retVal };
    vtblInvoke("getQuarter2", 17, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * datesFromPeriod2. Calculates the period start and end dates, given a period number, period type, period length and base date. Returns whether or not the start and end dates were successfully determined. Script languages should use this function.
   *
   * @param     period The period (in)
   * @param     periodType A com.interweave.plugin.a4wcomex.tagPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     startDate A Variant (out: use single element array)
   * @param     endDate A Variant (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean datesFromPeriod2  (
              short period,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              Object[] startDate,
              Object[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { new Short(period), new Integer(periodType), new Short(periodLength), baseDate, startDate, endDate, zz_retVal };
    vtblInvoke("datesFromPeriod2", 18, zz_parameters);
    return zz_retVal[0];
  }

  /**
   * dateToPeriod2. Calculates the period number, given a date, period type, period length and base date. Returns whether or not the period number was successfully determined. Script languages should use this function.
   *
   * @param     date The date (in)
   * @param     periodType A com.interweave.plugin.a4wcomex.tagPeriodType constant (in)
   * @param     periodLength The periodLength (in)
   * @param     baseDate The baseDate (in)
   * @param     period A Variant (out: use single element array)
   * @return    The status
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public boolean dateToPeriod2  (
              java.util.Date date,
              int periodType,
              short periodLength,
              java.util.Date baseDate,
              Object[] period) throws java.io.IOException, com.linar.jintegra.AutomationException{
    boolean zz_retVal[] = { false };
    Object zz_parameters[] = { date, new Integer(periodType), new Short(periodLength), baseDate, period, zz_retVal };
    vtblInvoke("dateToPeriod2", 19, zz_parameters);
    return zz_retVal[0];
  }

  /** Dummy reference from interface to proxy to make sure proxy gets compiled */
  static final int xxDummy = 0;

  static {
    com.linar.jintegra.InterfaceDesc.add("641951c5-999f-481d-9bee-8aaacf5bb626", com.interweave.plugin.a4wcomex.IAccpacFiscalCalendarProxy.class, null, 7, new com.linar.jintegra.MemberDesc[] {
        new com.linar.jintegra.MemberDesc("getFirstYear",
            new Class[] { String[].class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getLastYear",
            new Class[] { String[].class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getYear",
            new Class[] { String.class, short[].class, short[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("fiscalPeriods", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("qtr4Period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("active", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("found", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPeriod",
            new Class[] { java.util.Date.class, short[].class, String[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 16386, 12, 8, null, null), 
              new com.linar.jintegra.Param("year", 16392, 12, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getPeriodDates",
            new Class[] { String.class, Short.TYPE, java.util.Date[].class, java.util.Date[].class, boolean[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("periodOpen", 16395, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getQuarter",
            new Class[] { String.class, Short.TYPE, short[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("quarter", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getQuarterDates",
            new Class[] { String.class, Short.TYPE, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("quarter", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getYearDates",
            new Class[] { String.class, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 12, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("datesFromPeriod",
            new Class[] { Short.TYPE, int.class, Short.TYPE, java.util.Date.class, java.util.Date[].class, java.util.Date[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16391, 4, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16391, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("dateToPeriod",
            new Class[] { java.util.Date.class, int.class, Short.TYPE, java.util.Date.class, short[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 16386, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("getQuarter2",
            new Class[] { String.class, Short.TYPE, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("year", 8, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("quarter", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("dateInFiscal", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("datesFromPeriod2",
            new Class[] { Short.TYPE, int.class, Short.TYPE, java.util.Date.class, Object[].class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("period", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("startDate", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("endDate", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
        new com.linar.jintegra.MemberDesc("dateToPeriod2",
            new Class[] { java.util.Date.class, int.class, Short.TYPE, java.util.Date.class, Object[].class, },
            new com.linar.jintegra.Param[] { 
              new com.linar.jintegra.Param("date", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("periodType", 3, 2, 0, "00000000-0000-0000-0000-000000000000", null), 
              new com.linar.jintegra.Param("periodLength", 2, 2, 8, null, null), 
              new com.linar.jintegra.Param("baseDate", 7, 2, 8, null, null), 
              new com.linar.jintegra.Param("period", 16396, 4, 8, null, null), 
              new com.linar.jintegra.Param("status", 11, 20, 8, null, null) }),
});  }
}
