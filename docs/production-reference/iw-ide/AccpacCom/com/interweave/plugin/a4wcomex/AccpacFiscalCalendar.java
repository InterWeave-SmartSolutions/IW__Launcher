package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacFiscalCalendar'. Generated 02/10/2006 12:21:34 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>Fiscal calendar information class</B>'
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
public class AccpacFiscalCalendar implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar {

  private static final String CLSID = "1270c515-b48b-4ac1-beec-96def59c1bff";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacFiscalCalendarProxy d_IAccpacFiscalCalendarProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar interface */
  public com.interweave.plugin.a4wcomex.IAccpacFiscalCalendar getAsIAccpacFiscalCalendar() { return d_IAccpacFiscalCalendarProxy; }

  /** Compare this object with another */
  public boolean equals(Object o) { 
	if(java.beans.Beans.isDesignTime()) return super.equals(o);
	else return getJintegraDispatch() == null ? false : getJintegraDispatch().equals(o);
  }


  /** the hashcode for this object */
  public int hashCode() { return getJintegraDispatch() == null ? 0 : getJintegraDispatch().hashCode(); }

   /**
   * getActiveObject. Get a reference to a running instance of this class on the current machine using native code.
   *                  <B>Uses native code (See GetActiveObject() in MS doc) and thus can only be used on MS Windows</B>
   *
   * @return    A reference to the running object.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacFiscalCalendar getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacFiscalCalendar(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacFiscalCalendar bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacFiscalCalendar(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacFiscalCalendarProxy; }

  /**
   * Constructs a AccpacFiscalCalendar on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacFiscalCalendar() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacFiscalCalendar on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacFiscalCalendar(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacFiscalCalendarProxy = new com.interweave.plugin.a4wcomex.IAccpacFiscalCalendarProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacFiscalCalendar using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacFiscalCalendar(Object obj) throws java.io.IOException {
    d_IAccpacFiscalCalendarProxy = new com.interweave.plugin.a4wcomex.IAccpacFiscalCalendarProxy(obj);
  }

  /**
   * Release a AccpacFiscalCalendar.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacFiscalCalendarProxy);
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
    try {
      return d_IAccpacFiscalCalendarProxy.getPropertyByName(name);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * getPropertyByName. Get the value of a property dynamically at run-time, based on its name and a parameter
   *
   * @return    The value of the property.
   * @param     name The name of the property to get.
   * @param     rhs A parameter used when getting the proxy.
   * @exception java.lang.NoSuchFieldException If the property does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getPropertyByName(String name, Object rhs) throws NoSuchFieldException, java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getPropertyByName(name, rhs);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    } catch(NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.fillInStackTrace();
      throw noSuchFieldException;
    }
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @param     parameters One element for each parameter. Use primitive type wrappers.
   *            to pass primitive types (eg Integer to pass an int).
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name, Object[] parameters) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacFiscalCalendarProxy.invokeMethodByName(name, parameters);
  }

  /**
   * invokeMethodByName. Invoke a method dynamically at run-time
   *
   * @return    The value returned by the method (null if none).
   * @param     name The name of the method to be invoked.
   * @exception java.lang.NoSuchMethodException If the method does not exit.
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object invokeMethodByName(String name) throws NoSuchMethodException, java.io.IOException, com.linar.jintegra.AutomationException {
    return d_IAccpacFiscalCalendarProxy.invokeMethodByName(name, new Object[]{});
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getFirstYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getLastYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] active) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getYear(year,fiscalPeriods,qtr4Period,active);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getPeriod(date,period,year,periodOpen);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              boolean[] periodOpen) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getPeriodDates(year,period,startDate,endDate,periodOpen);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              short[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getQuarter(year,period,quarter);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getQuarterDates(year,quarter,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getYearDates(year,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              java.util.Date[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.datesFromPeriod(period,periodType,periodLength,baseDate,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              short[] period) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.dateToPeriod(date,periodType,periodLength,baseDate,period);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] quarter) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.getQuarter2(year,period,quarter);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] endDate) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.datesFromPeriod2(period,periodType,periodLength,baseDate,startDate,endDate);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
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
              Object[] period) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacFiscalCalendarProxy.dateToPeriod2(date,periodType,periodLength,baseDate,period);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
