package com.interweave.plugin.a4wcomex;

import com.linar.jintegra.*;

/**
 * COM Class 'AccpacViewField'. Generated 02/10/2006 12:21:33 PM
 * from 'C:\Documents and Settings\Dmytro Zotkin\My Documents\My Installations\Java-Com\jacobgen_0.7\jacobgen_0.7\a4wcomex.dll'<P>
 * Generated using com2java Version 2.6 Copyright (c) Intrinsyc Software International, Inc.
 * See  <A HREF="http://j-integra.intrinsyc.com/">http://j-integra.intrinsyc.com/</A><P>
 * Description '<B>View field class</B>'
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
public class AccpacViewField implements com.linar.jintegra.RemoteObjRef, com.interweave.plugin.a4wcomex.IAccpacViewField {

  private static final String CLSID = "3993f687-aee7-11d2-9ba0-00104b71eb3f";

  protected String getJintegraVersion() { return "2.6"; }

  // Interface delegates
  private com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy d_IAccpacViewFieldProxy = null;

  /** Access this COM class's com.interweave.plugin.a4wcomex.IAccpacViewField interface */
  public com.interweave.plugin.a4wcomex.IAccpacViewField getAsIAccpacViewField() { return d_IAccpacViewFieldProxy; }

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
  public static AccpacViewField getActiveObject() throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacViewField(com.linar.jintegra.Dispatch.getActiveObject(CLSID));
  }

  /**
   * bindUsingMoniker. Bind to a running instance of this class using the supplied ObjRef moniker
   *
   * @return    A reference to the running object.
   * @param     moniker The ObjRef Moniker (Created using Windows CreateObjrefMoniker() and IMoniker->GetDisplayName).
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If there was an error attaching to the instance.
   */
  public static AccpacViewField bindUsingMoniker(String moniker) throws com.linar.jintegra.AutomationException, java.io.IOException {
    return new AccpacViewField(com.linar.jintegra.Dispatch.bindUsingMoniker(moniker));
  }

  /** J-Integra for COM internal method */
  public com.linar.jintegra.Dispatch getJintegraDispatch() {  return d_IAccpacViewFieldProxy; }

  /**
   * Constructs a AccpacViewField on the local host.
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacViewField() throws java.io.IOException, java.net.UnknownHostException {
    this("localhost");
  }

  /**
   * Construct a AccpacViewField on specified host.
   * @param     host  the host on which the object should be created
   * @exception java.io.IOException if there are problems communicating via DCOM 
   * @exception java.net.UnknownHostException if the host can not be found 
   */
  public AccpacViewField(String host) throws java.io.IOException, java.net.UnknownHostException {
    d_IAccpacViewFieldProxy = new com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy(CLSID, host, null);
  }

  /**
   * Construct a AccpacViewField using a reference to such an object returned from a COM server
   * @param     obj an object returned from a COM server
   * @exception java.io.IOException if there are problems communicating via DCOM 
   */
  public AccpacViewField(Object obj) throws java.io.IOException {
    d_IAccpacViewFieldProxy = new com.interweave.plugin.a4wcomex.IAccpacViewFieldProxy(obj);
  }

  /**
   * Release a AccpacViewField.
   */
  public void release() {
    com.linar.jintegra.Cleaner.release(d_IAccpacViewFieldProxy);
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
      return d_IAccpacViewFieldProxy.getPropertyByName(name);
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
      return d_IAccpacViewFieldProxy.getPropertyByName(name, rhs);
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
    return d_IAccpacViewFieldProxy.invokeMethodByName(name, parameters);
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
    return d_IAccpacViewFieldProxy.invokeMethodByName(name, new Object[]{});
  }

  /**
   * getName. Returns the field name.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getName  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getName();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getID. Returns the field ID that corresponds to the field index as defined in the view.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getID  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getID();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getAttributes. Returns the field attributes.
   *
   * @return    A com.interweave.plugin.a4wcomex.tagFieldAttributeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getAttributes  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getAttributes();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getDescription. Returns the field description.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getDescription  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getDescription();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getType. Returns the field type.
   *
   * @return    A com.interweave.proxy.tagFieldTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getSize. Returns the size of the field.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getSize  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getSize();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPrecision. Returns the precision if the field is of numeric type.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public short getPrecision  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getPrecision();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPresentationType. Returns the presentation type of the field.
   *
   * @return    A com.interweave.proxy.tagFieldPresentationTypeEnum constant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getPresentationType  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getPresentationType();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPresentationMask. Returns the mask if the presentation type specifies mask.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public String getPresentationMask  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getPresentationMask();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getPresentationStrings. Returns the collection of presentation strings of this field.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacViewFieldPresentsStrings
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacViewFieldPresentsStrings getPresentationStrings  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getPresentationStrings();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setToMax. Sets the field to the maximum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMax  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewFieldProxy.setToMax();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setToMin. Sets the field to the minimum value according to the field type.
   *
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setToMin  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewFieldProxy.setToMin();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMaxValue. Returns the maximum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMaxValue  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getMaxValue();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getMinValue. Returns the minimum value of the field according to the field type.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getMinValue  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getMinValue();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getView. Returns the ACCPAC view object to which this field belongs.
   *
   * @return    An reference to a com.interweave.plugin.a4wcomex.IAccpacView
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public com.interweave.plugin.a4wcomex.IAccpacView getView  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getView();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * putWithoutVerification. Sets the value of the field without validation.
   *
   * @param     pNewVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void putWithoutVerification  (
              Object pNewVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewFieldProxy.putWithoutVerification(pNewVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @return    A Variant
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public Object getValue  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getValue();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * setValue. Returns/sets the value of the field.  The value is set with validation.
   *
   * @param     pVal A Variant (in)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void setValue  (
              Object pVal) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewFieldProxy.setValue(pVal);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getIndex. Returns the field's 0-based index in the AccpacViewFields collection to which it belongs.
   *
   * @return    The pVal
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public int getIndex  () throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      return d_IAccpacViewFieldProxy.getIndex();
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

  /**
   * getInfo. Retrieves all of the view field's information in one call.
   *
   * @param     index The index (out: use single element array)
   * @param     iD The iD (out: use single element array)
   * @param     name The name (out: use single element array)
   * @param     description The description (out: use single element array)
   * @param     type A com.interweave.proxy.tagFieldTypeEnum constant (out: use single element array)
   * @param     attribs A com.interweave.plugin.a4wcomex.tagFieldAttributeEnum constant (out: use single element array)
   * @param     size The size (out: use single element array)
   * @param     precision The precision (out: use single element array)
   * @param     maxValue A Variant (out: use single element array)
   * @param     minValue A Variant (out: use single element array)
   * @param     presentationType A com.interweave.proxy.tagFieldPresentationTypeEnum constant (out: use single element array)
   * @param     presentationMask The presentationMask (out: use single element array)
   * @param     value A Variant (out: use single element array)
   * @exception java.io.IOException If there are communications problems.
   * @exception com.linar.jintegra.AutomationException If the remote server throws an exception.
   */
  public void getInfo  (
              int[] index,
              int[] iD,
              String[] name,
              String[] description,
              int[] type,
              int[] attribs,
              short[] size,
              short[] precision,
              Object[] maxValue,
              Object[] minValue,
              int[] presentationType,
              String[] presentationMask,
              Object[] value) throws java.io.IOException, com.linar.jintegra.AutomationException {
    try {
      d_IAccpacViewFieldProxy.getInfo(index,iD,name,description,type,attribs,size,precision,maxValue,minValue,presentationType,presentationMask,value);
    } catch(com.linar.jintegra.AutomationException automationException) {
      automationException.fillInStackTrace();
      throw automationException;
    }
  }

}
