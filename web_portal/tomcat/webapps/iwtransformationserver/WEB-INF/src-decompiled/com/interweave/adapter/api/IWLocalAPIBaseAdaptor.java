/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.api;

import com.interweave.adapter.http.IWXMLBaseAdaptor;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Outputs;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Where;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IWLocalAPIBaseAdaptor
extends IWXMLBaseAdaptor {
    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        this.iwRequest = request;
        Enumeration enumerate = this.accessList.elements();
        if (enumerate.hasMoreElements()) {
            Access access = (Access)((Object)enumerate.nextElement());
            this.curAccess = null;
            request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
            if (access != null) {
                this.curAccess = access;
                String stpre0 = this.curAccess.getStatementpre();
                if (stpre0 != null && stpre0.length() > 0) {
                    stpre0 = IWServices.replaceParameters(stpre0, this.curAccess, request, this.dataMap);
                    request.lnkIWServices.logConsole("API: " + stpre0, IWServices.LOG_TRACE, request);
                    String trStr = stpre0;
                    int li = stpre0.lastIndexOf("`");
                    if (li >= 0) {
                        trStr = stpre0.substring(0, li + 1);
                    }
                    String[] str = trStr.split("`");
                    int si = 0;
                    while (si < str.length) {
                        Object resObj;
                        List<Parameter> mrp;
                        int firstPar = str[si].indexOf("(");
                        int lastPar = str[si].lastIndexOf(")");
                        if (firstPar < 0 || lastPar < 0) {
                            throw new Exception("Invalid method signature: parameter list is missing or corrupted");
                        }
                        int lastDot = str[si].substring(0, firstPar).lastIndexOf(".");
                        String className = str[si].substring(0, lastDot);
                        Class<?> methodClass = Class.forName(className);
                        Object ro = null;
                        String methodName = str[si].substring(lastDot + 1, firstPar).trim();
                        String parameterValue = str[si].substring(firstPar + 1, lastPar).trim();
                        String[] parValues = new String[]{};
                        if (parameterValue.length() > 0) {
                            parValues = parameterValue.split(",");
                        }
                        Method invokedMethod = null;
                        Class[] methodParameters = null;
                        Object[] methodParameterValues = new Object[parValues.length];
                        Class[] connectionMethodParameters = new Class[]{String.class, String.class, String.class, String.class};
                        Class[] logLevelMethodParameters = new Class[]{Integer.class, Boolean.class};
                        Method setConnectionMethod = null;
                        Method setLogLevelMethod = null;
                        try {
                            setLogLevelMethod = methodClass.getMethod("setLogLevel", logLevelMethodParameters);
                            if (!Modifier.isStatic(setLogLevelMethod.getModifiers())) {
                                setLogLevelMethod = null;
                            }
                        }
                        catch (SecurityException e2) {
                            setLogLevelMethod = null;
                        }
                        catch (NoSuchMethodException e3) {
                            setLogLevelMethod = null;
                        }
                        try {
                            if ((this.contentType != null && this.contentType.trim().length() > 0 || this.httpURL != null && this.httpURL.trim().length() > 0 || this.user != null && this.user.trim().length() > 0 || this.password != null && this.password.trim().length() > 0) && !Modifier.isStatic((setConnectionMethod = methodClass.getMethod("setConnection", connectionMethodParameters)).getModifiers())) {
                                setConnectionMethod = null;
                            }
                        }
                        catch (SecurityException e2) {
                            setConnectionMethod = null;
                        }
                        catch (NoSuchMethodException e3) {
                            setConnectionMethod = null;
                        }
                        boolean xmlInput = false;
                        Where where = access.getWhere();
                        if (where != null) {
                            List<Parameter> inps = IWServices.getParametersByInput(where.getParameter(), methodName);
                            int np = inps.size();
                            if (np == 1 && parValues.length == 0) {
                                parValues = new String[]{""};
                                methodParameterValues = new Object[1];
                            }
                            methodParameters = new Class[np];
                            int i = 0;
                            while (i < np) {
                                Parameter cp = inps.get(i);
                                String parClass = cp.getMapping().getType();
                                if (parClass == null || parClass.length() == 0) {
                                    throw new Exception("Parameter type is missing for the method " + methodName);
                                }
                                if (parClass.equalsIgnoreCase("XML")) {
                                    if (i != 0 || np > 1) {
                                        throw new Exception("Only one input parameter of XML type is supported: " + methodName);
                                    }
                                    xmlInput = true;
                                    parClass = "java.lang.String";
                                    methodParameterValues = new Object[1];
                                    methodParameters = new Class[1];
                                } else if (np != parValues.length) {
                                    throw new Exception("Extra or missing parameter definition for the method " + methodName);
                                }
                                String spi = cp.getMapping().getContent();
                                if (spi == null || spi.length() == 0) {
                                    throw new Exception("Parameter index is missing for the method " + methodName);
                                }
                                int pi = Integer.valueOf(spi);
                                if (xmlInput && pi != 0) {
                                    throw new Exception("Only parameter index 0 is supported for XML type input: " + methodName);
                                }
                                try {
                                    methodParameters[pi] = Class.forName(parClass);
                                    if (xmlInput) {
                                        methodParameterValues[0] = parameterValue;
                                    } else {
                                        methodParameterValues[pi] = request.lnkIWServices.getObjectValue(methodParameters[pi], parValues[i]);
                                    }
                                }
                                catch (ClassNotFoundException e) {
                                    throw e;
                                }
                                catch (Exception e) {
                                    throw e;
                                }
                                ++i;
                            }
                            invokedMethod = methodClass.getMethod(methodName, methodParameters);
                        } else if (parValues != null) {
                            Method[] am = methodClass.getMethods();
                            int im = 0;
                            while (im < am.length) {
                                if (am[im].getName().equals(methodName) && am[im].getParameterTypes().length == 0) {
                                    if (invokedMethod == null) {
                                        invokedMethod = am[im];
                                    } else {
                                        throw new Exception("Parameter definitions are missing for the multiple methods " + methodName);
                                    }
                                }
                                ++im;
                            }
                            methodParameters = invokedMethod.getParameterTypes();
                            if (methodParameters.length != parValues.length) {
                                throw new Exception("Extra or missing parameter definition for the method " + methodName);
                            }
                            int i = 0;
                            while (i < methodParameters.length) {
                                try {
                                    methodParameterValues[i] = request.lnkIWServices.getObjectValue(methodParameters[i], parValues[i]);
                                }
                                catch (ClassNotFoundException e) {
                                    throw e;
                                }
                                catch (Exception e) {
                                    throw e;
                                }
                                ++i;
                            }
                        }
                        String colName = String.valueOf(className) + "." + methodName;
                        boolean xmlOutput = false;
                        boolean scheduleControl = false;
                        Outputs outputs = access.getOutputs();
                        if (outputs != null && (mrp = IWServices.getParametersByInput(outputs.getParameter(), methodName)).size() != 0) {
                            String mt;
                            if (mrp.size() > 1) {
                                throw new Exception("Too many outputs for the method " + methodName);
                            }
                            Mapping retMap = mrp.get(0).getMapping();
                            String cn = retMap.getContent();
                            if (cn != null && cn.length() > 0) {
                                colName = cn;
                            }
                            if ((mt = retMap.getType()) != null && mt.equalsIgnoreCase("XML")) {
                                xmlOutput = true;
                            }
                            scheduleControl = retMap.getQuoted().equalsIgnoreCase("true");
                        }
                        if (!Modifier.isStatic(invokedMethod.getModifiers())) {
                            ro = request.lnkIWServices.getObject(methodClass);
                        }
                        try {
                            if (setLogLevelMethod != null) {
                                Object[] llMPV = new Object[]{new Integer(request.lnkIWServices.getLogging(request)), new Boolean(request.lnkIWServices.isShowTime())};
                                setLogLevelMethod.invoke(null, llMPV);
                            }
                            if (setConnectionMethod != null) {
                                Object[] cMPV = new Object[]{this.getUrl(), this.getDriver(), this.getUser(), this.getPassword()};
                                setConnectionMethod.invoke(null, cMPV);
                            }
                            resObj = invokedMethod.invoke(ro, methodParameterValues);
                        }
                        catch (IllegalArgumentException e) {
                            throw e;
                        }
                        catch (IllegalAccessException e) {
                            throw e;
                        }
                        catch (InvocationTargetException e) {
                            throw e;
                        }
                        Class<?> retClass = invokedMethod.getReturnType();
                        request.lnkIWServices.logConsole("Return calss: " + retClass.getName(), IWServices.LOG_TRACE, request);
                        if (!retClass.getName().equals(Void.class.getName())) {
                            if (resObj == null) {
                                resObj = "null";
                            }
                            String resString = resObj.toString();
                            StringBuffer tempRes = new StringBuffer();
                            if (xmlOutput) {
                                responseBuffer.append(this.xml2Data(resString));
                            } else {
                                if (retClass.isArray()) {
                                    Object[] oa = (Object[])resObj;
                                    int ia = 0;
                                    while (ia < oa.length) {
                                        tempRes.append(this.oneColOneRow(colName, oa[ia].toString()));
                                        ++ia;
                                    }
                                } else if (IWServices.isImplemented(retClass, "java.util.Map")) {
                                    Map rm = (Map)resObj;
                                    Set ks = rm.keySet();
                                    Iterator kit = ks.iterator();
                                    int cnm = 0;
                                    while (kit.hasNext()) {
                                        colName = kit.next().toString();
                                        tempRes.append(this.oneColBase(colName, rm.get(colName).toString(), cnm));
                                        ++cnm;
                                    }
                                    tempRes = this.addRowXML(tempRes);
                                } else if (IWServices.isImplemented(retClass, "java.lang.Iterable")) {
                                    Iterator rit = ((Iterable)resObj).iterator();
                                    while (rit.hasNext()) {
                                        tempRes.append(this.oneColOneRow(colName, rit.next().toString()));
                                    }
                                } else {
                                    tempRes.append(this.oneColOneRow(colName, resString));
                                    if (scheduleControl) {
                                        request.setStopSchedule(resString.equalsIgnoreCase("true") ? 1 : 0);
                                        if (request.getCycleValue() > 0) {
                                            request.setStopCycleSchedule(resString.equalsIgnoreCase("true") ? 1 : 0);
                                        }
                                    }
                                }
                                responseBuffer.append(this.addDataXML(tempRes));
                            }
                            request.lnkIWServices.logConsole("go: Method " + methodName + " executed; Result= " + responseBuffer, IWServices.LOG_TRACE, request);
                        } else {
                            request.lnkIWServices.logConsole("go: Void method " + methodName + " executed", IWServices.LOG_TRACE, request);
                        }
                        ++si;
                    }
                    responseBuffer = this.addDataMapXML(responseBuffer);
                } else {
                    request.lnkIWServices.logConsole("go: Empty query", IWServices.LOG_TRACE, request);
                }
            }
        }
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.setupInitConnect(map, request);
    }

    public void closeConnection() {
    }
}

