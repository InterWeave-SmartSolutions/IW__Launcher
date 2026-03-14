/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.connector;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Iwmappings;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Transaction;
import com.interweave.bindings.Translator;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import org.apache.xalan.serialize.Serializer;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.xsltc.DOM;
import org.apache.xalan.xsltc.dom.SAXImpl;
import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
import org.apache.xalan.xsltc.runtime.AbstractTranslet;
import org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class IWXsltcImpl {
    public Iwmappings mappings = null;
    public boolean tranFailed = false;
    private Transaction tranNext = null;
    private IWRequest iwRequest;
    private Serializer serializer = null;
    private int outKey = 1;
    private Hashtable tranList = new Hashtable();
    private static boolean validate = false;
    private static boolean setNameSpaces = true;
    private static boolean setLoadExternalDTD = true;
    private static boolean setSchemaSupport = false;
    private static boolean setSchemaFullSupport = false;
    private static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";
    public IWServices lnkIWServices = null;
    public String parserName = "org.apache.xerces.parsers.SAXParser";
    public Vector responses = new Vector();
    public Hashtable outputs = new Hashtable();
    public String tranname = null;
    public String strTransform = null;

    public IWXsltcImpl(IWRequest iwRequest) {
        this.iwRequest = iwRequest;
    }

    public IWXsltcImpl() {
    }

    public void load(String xslStr, IWRequest request) throws Exception {
        this.lnkIWServices.logConsole("<!-- Transaction load: " + xslStr + " -->", IWServices.LOG_ALL, request);
        if (this.mappings == null) {
            try {
                this.lnkIWServices.logConsole("<!-- Transaction load: " + xslStr + " -->", IWServices.LOG_ALL, request);
                this.strTransform = xslStr;
                String response = this.executeTransform(this.lnkIWServices.defaultMessage, request);
                ByteArrayInputStream stream = new ByteArrayInputStream(response.getBytes());
                this.mappings = Iwmappings.unmarshal(stream);
                this.dumpTransactions();
                stream = null;
                response = null;
            }
            catch (Exception e) {
                this.lnkIWServices.logError("IWXsltcImpl.load " + e.getMessage(), IWServices.LOG_TRACE, e, request);
                this.mappings = null;
                throw e;
            }
        }
    }

    public void load(String xslStr, short compiledMappings, String realPath) throws Exception {
        this.lnkIWServices.logConsole("<!-- Transaction load: " + xslStr + " -->", IWServices.LOG_ALL, this.iwRequest);
        if (this.mappings == null) {
            try {
                this.lnkIWServices.logConsole("<!-- Transaction load: " + xslStr + " -->", IWServices.LOG_ALL, this.iwRequest);
                this.strTransform = xslStr;
                String response = "";
                if (realPath == null || compiledMappings > 0) {
                    response = this.executeTransform(this.lnkIWServices.defaultMessage, null);
                }
                if (realPath != null && compiledMappings != 1) {
                    response = IWServices.readFile(String.valueOf(realPath) + "/WEB-INF/" + xslStr + ".xml").trim();
                    if (compiledMappings == 2) {
                        String endTag = "</iwmappings>";
                        String preResponse = this.executeTransform(this.lnkIWServices.defaultMessage, null).trim();
                        if (preResponse.startsWith("<" + endTag.substring(2)) && preResponse.endsWith(endTag)) {
                            response = String.valueOf(preResponse.substring(0, preResponse.indexOf(endTag))) + response.substring(endTag.length() - 1);
                        }
                    }
                }
                ByteArrayInputStream stream = new ByteArrayInputStream(response.getBytes());
                this.mappings = Iwmappings.unmarshal(stream);
                this.dumpTransactions();
            }
            catch (Exception e) {
                this.lnkIWServices.logError("IWXsltcImpl.load " + e.getMessage(), IWServices.LOG_TRACE, e, null);
                this.mappings = null;
                throw e;
            }
        }
    }

    public void load(String xslStr, String xmlStr) throws Exception {
        if (this.mappings == null) {
            try {
                this.lnkIWServices.logConsole("<!-- Transaction load: " + xslStr + " -->", IWServices.LOG_ALL, this.iwRequest);
                this.strTransform = xslStr;
                String response = this.executeTransform(xmlStr, null);
                ByteArrayInputStream stream = new ByteArrayInputStream(response.getBytes());
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                this.mappings = Iwmappings.unmarshal(stream);
                this.dumpTransactions();
                stream = null;
                response = null;
            }
            catch (Exception e) {
                this.lnkIWServices.logError("IWXsltcImpl.load " + e.getMessage(), IWServices.LOG_TRACE, e, null);
                this.mappings = null;
                throw e;
            }
        }
    }

    public void stringToFile(String fileName, String out) throws Exception {
        File file = new File(fileName);
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file));
        output.write(out);
        output.close();
        file = null;
        output = null;
    }

    public void dumpTransactions() {
        Transaction tran = null;
        if (this.mappings != null) {
            StringBuffer outBuffer = new StringBuffer();
            Iterator iterate = this.mappings.getTransaction().iterator();
            outBuffer.append("\n<!------------------------------------- Transaction Names \n");
            while (iterate.hasNext()) {
                tran = (Transaction)((Object)iterate.next());
                outBuffer.append("\n\t" + tran.getName());
            }
            outBuffer.append("\n------------------------------------->");
            this.lnkIWServices.logConsole(outBuffer.toString(), IWServices.LOG_TRACE, this.iwRequest);
            iterate = null;
            Object var2_2 = null;
        }
    }

    public Transaction getTransaction(String strtranname, IWRequest request) {
        if (this.tranNext != null) {
            this.lnkIWServices.logConsole("<!-- Transaction Next " + this.tranNext.getName() + " -->", IWServices.LOG_TRACE, request);
            return this.tranNext;
        }
        return this.getTransactionBasic(strtranname, request);
    }

    public Transaction getTransactionBasic(String strtranname, IWRequest request) {
        Transaction tran = null;
        if (strtranname == null) {
            this.lnkIWServices.logConsole("<!-- Get Transaction Null Name Error -->", IWServices.LOG_ERRORS, request);
        } else if (this.mappings == null) {
            this.lnkIWServices.logConsole("<!-- Get Transaction " + strtranname + " Null Mappings Error-->", IWServices.LOG_ERRORS, request);
        } else {
            Iterator iterate = this.mappings.getTransaction().iterator();
            while (iterate.hasNext()) {
                tran = (Transaction)((Object)iterate.next());
                if (strtranname.compareToIgnoreCase(tran.getName()) != 0) continue;
                iterate = null;
                return tran;
            }
            iterate = null;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public void executeTransaction(IWRequest request) throws Exception {
        try {
            moreTransactions = true;
            startCreate = System.currentTimeMillis();
            this.tranNext = null;
            strData = null;
            tran = null;
            dataMap = null;
            map = null;
            rs = null;
            iterate = null;
            tranBuffer = null;
            nextTranCond = false;
            wasTranCond = false;
            request.setLoopIndex(0);
            this.tranname = request.getParameter("tranname");
            if (this.tranname.compareToIgnoreCase("commit") != 0 && request.iwSession != null && request.iwSession.cacheTran && !request.iwSession.commitTran) {
                request.tranBuffer.append(request.iwSession.cacheTransaction(request));
                return;
            }
            transactions = new Vector<String>();
            while (moreTransactions) {
                block47: {
                    moreTransactions = false;
                    tran = null;
                    if (this.tranname != null && this.tranname.length() > 0) {
                        this.lnkIWServices.logConsole("Incoming TranName = " + this.tranname, IWServices.LOG_TRACE, request);
                        tran = this.getTransaction(this.tranname, request);
                    } else {
                        this.tranname = request.getParameter("tranname");
                        tran = this.getTransaction(this.tranname, request);
                    }
                    if (tran == null) break block47;
                    this.lnkIWServices.logConsole("Executed TranName = " + tran.getName(), IWServices.LOG_TRACE, request);
                    if (!transactions.contains(tran.getName())) ** GOTO lbl46
                    if (nextTranCond || wasTranCond) {
                        request.setLoopIndex(request.getLoopIndex() + 1);
                    } else {
                        tranBuf = new StringBuffer();
                        enumerateerate = transactions.elements();
                        while (enumerateerate.hasMoreElements()) {
                            tranBuf.append("\n" + enumerateerate.nextElement());
                        }
                        tranBuf.append("\n");
                        enumerateerate = null;
                        throw new Exception("Transaction loop: " + tranBuf.toString() + " [" + tran.getName() + "]");
lbl46:
                        // 1 sources

                        transactions.add(tran.getName());
                    }
                    nextTranCond = false;
                    nextTransaction = tran.getNexttransaction();
                    this.lnkIWServices.logConsole("<!-- Execute Transaction " + tran.getName() + " -->", IWServices.LOG_DATA, request);
                    adaptorClassName = tran.getClassname();
                    if (adaptorClassName != null) {
                        this.lnkIWServices.logConsole("<!-- Transaction Class " + adaptorClassName + " -->", IWServices.LOG_DATA, null);
                        if (tran.getDatamap().size() == 0) {
                            this.lnkIWServices.logConsole("Warning: <!-- No Datamap for Transaction " + this.tranname + ":" + adaptorClassName + " -->", IWServices.LOG_ERRORS, request);
                        }
                        iterate = tran.getDatamap().iterator();
                        nextIterator = tran.getDatamap().iterator();
                        if (nextIterator.hasNext()) {
                            nextIterator.next();
                        }
                        while (iterate.hasNext()) {
                            map = (Datamap)iterate.next();
                            nd = null;
                            if (nextIterator.hasNext()) {
                                nd = (Datamap)nextIterator.next();
                            } else if (nextTransaction != null && (ntr = this.getTransactionBasic(nextTransaction.getName(request.getCurrentTransactoionFlowId()), request)) != null && ntr.getClassname().equals(adaptorClassName) && ntr.getDatamap().size() > 0) {
                                nd = (Datamap)ntr.getDatamap().get(0);
                            }
                            this.lnkIWServices.logConsole("<!-- Execute Datamap " + map.getName() + " -->", IWServices.LOG_DATA, request);
                            rs = (IWIDataMap)request.lnkIWServices.getObject(adaptorClassName);
                            if (rs != null) {
                                doneCreate = System.currentTimeMillis() - startCreate;
                                this.lnkIWServices.logConsole("<!-- Datamap Creation  in " + doneCreate + ":msecs -->", IWServices.LOG_TIMING, request);
                                start = System.currentTimeMillis();
                                tranType = tran.getType();
                                request.setFilterTransaction(tranType != null && tranType.equalsIgnoreCase("filter") != false);
                                dataMap = this.getMapData(request, rs, map, nd);
                                done = System.currentTimeMillis() - start;
                                if (dataMap != null) {
                                    this.lnkIWServices.logConsole("Datamap: " + map.getName() + "\n" + dataMap.toString(), IWServices.LOG_DATA, request);
                                    this.lnkIWServices.logConsole("<!-- execute Data IO " + this.tranname + " in " + done + ":msecs -->", IWServices.LOG_TIMING, request);
                                    strXml = "<?xml";
                                    strMap = dataMap.toString();
                                    if (strMap.startsWith(strXml)) {
                                        i = strMap.indexOf(">");
                                        strMap = strMap.substring(i + 1);
                                    }
                                    this.responses.add(strMap);
                                    access = rs.getCurAccess();
                                    outputs = null;
                                    strXpath = null;
                                    strParam = null;
                                    strValue = null;
                                    count = 1;
                                    if (access != null && (outputs = access.getOutputs()) != null) {
                                        strData = this.getXML();
                                        this.lnkIWServices.logConsole("<!-- Add Outputs \n" + strData + "\n -->", IWServices.LOG_TRACE, request);
                                        for (Parameter param : outputs.getParameter()) {
                                            mapping = param.getMapping();
                                            type = mapping.getType();
                                            strParam = null;
                                            strParam = param.getInput();
                                            if (type != null) {
                                                if (type.compareToIgnoreCase("xpath") == 0) {
                                                    strXpath = null;
                                                    strXpath = param.getMapping().getContent();
                                                    if (strXpath != null) {
                                                        this.lnkIWServices.logConsole("<!-- Output XPath " + strXpath + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                        strValue = this.applyXPath(strData, strXpath);
                                                    }
                                                    if (strValue == null) {
                                                        strValue = "";
                                                    }
                                                    this.lnkIWServices.logConsole("<!-- Output XPath " + strParam + ":" + strValue + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                    request.setParam(strParam, strValue);
                                                } else if (type.compareToIgnoreCase("reflect") == 0) {
                                                    strValue = param.getMapping().getContent();
                                                    if (strValue == null) {
                                                        strValue = "";
                                                    }
                                                    this.lnkIWServices.logConsole("<!-- Output XPath " + strParam + ":" + strValue + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                    request.setParam(strParam, strValue);
                                                } else if (type.compareToIgnoreCase("postxpath") == 0) {
                                                    strXpath = null;
                                                    strXpath = param.getMapping().getContent();
                                                    if (strXpath != null) {
                                                        this.lnkIWServices.logConsole("<!-- Output Post XPath " + strXpath + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                        strValue = this.applyXPath(strData, strXpath);
                                                    }
                                                    if (strValue == null) {
                                                        strValue = "";
                                                    }
                                                    this.lnkIWServices.logConsole("<!-- Output Post XPath " + strParam + ":" + strValue + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                    request.setParam(strParam, strValue);
                                                } else if (type.compareToIgnoreCase("post") == 0) {
                                                    strValue = param.getMapping().getContent();
                                                    if (strValue == null) {
                                                        strValue = "";
                                                    }
                                                    this.lnkIWServices.logConsole("<!-- Output Post " + strParam + ":" + strValue + " -->", IWServices.LOG_TRANSFORMDATA, request);
                                                    request.addPost(strParam, strValue);
                                                }
                                            } else {
                                                this.lnkIWServices.logConsole("Error: <!-- Outputs no type Attribute " + strParam + " -->", IWServices.LOG_ERRORS, request);
                                            }
                                            ++count;
                                        }
                                    }
                                    strXml = null;
                                    strMap = null;
                                    dataMap = null;
                                } else {
                                    this.lnkIWServices.logConsole("<!-- No Data " + this.tranname + ":" + map.getName() + " -->", IWServices.LOG_TIMING, request);
                                }
                            } else {
                                this.lnkIWServices.logConsole("Error: <!-- Class " + adaptorClassName + " is not initialized -->", IWServices.LOG_ERRORS, request);
                            }
                            rs = null;
                        }
                        if (!request.isFilterTransaction()) {
                            maps = this.getXML(tran.getDatamap().size());
                            tranBuffer = new StringBuffer("        <transaction name=\"" + this.tranname + "\">\n");
                            tranBuffer.append(maps);
                            tranBuffer.append("        </transaction>\n");
                            request.tranBuffer.append(tranBuffer.toString());
                        }
                    }
                    if (nextTransaction == null) continue;
                    this.tranname = nextTransaction.getName(request.getCurrentTransactoionFlowId());
                    this.lnkIWServices.logConsole("Next Transaction: " + this.tranname, IWServices.LOG_DATA, request);
                    strData = this.getXML();
                    if (strData != null) {
                        this.lnkIWServices.logConsole("<!-- Data \n" + strData + "\n -->", IWServices.LOG_TRACE, request);
                        if (nextTransaction.getType().equalsIgnoreCase("Conditional")) {
                            this.strTransform = nextTransaction.getContent();
                            if (this.strTransform != "" && this.strTransform != null) {
                                nextTranCond = true;
                                this.lnkIWServices.logConsole("Conditional: " + this.strTransform, IWServices.LOG_DATA, request);
                                this.tranNext = null;
                                try {
                                    response = this.executeTransform("<transaction name=\"" + this.tranname + "\">" + strData + "</transaction>", request);
                                    this.tranname = this.applyXPath(response, "/conditional/transaction");
                                    response = null;
                                }
                                catch (Exception e) {
                                    this.lnkIWServices.logConsole("Conditional Transform Error: " + e.getMessage(), IWServices.LOG_ERRORS, request);
                                    this.tranname = tran.getNexttransaction().getError();
                                    nextTranCond = false;
                                    wasTranCond = false;
                                    request.setLoopIndex(0);
                                }
                                wasTranCond = true;
                                if (this.tranname == null || this.tranname.trim().length() == 0) {
                                    this.tranname = nextTransaction.getName(request.getCurrentTransactoionFlowId());
                                    this.lnkIWServices.logConsole("Conditional Transform: No Next Conditional Transaction: " + this.tranname, IWServices.LOG_HTTP, request);
                                    if (this.tranname == null) {
                                        wasTranCond = false;
                                    }
                                    nextTranCond = false;
                                    request.setLoopIndex(0);
                                }
                            }
                        }
                    }
                    if (this.tranname == null || this.tranname.length() <= 0) continue;
                    moreTransactions = true;
                    this.tranNext = null;
                    this.tranNext = this.getTransaction(this.tranname, request);
                    continue;
                }
                request.dump();
                this.lnkIWServices.logConsole("Error: <!-- Transaction not Found " + this.tranname + " -->", IWServices.LOG_ERRORS, request);
                throw new Exception("Transaction not Found " + this.tranname);
            }
            this.lnkIWServices.logConsole("Transaction Result " + request.getParameter("tranname") + "\n", IWServices.LOG_DATA, request);
            return;
        }
        catch (Exception e) {
            request.setError("executeTransaction " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().equals("Connection Failed")) {
                request.setConnectionError(true);
            }
            this.lnkIWServices.logError(request.getError(), IWServices.LOG_TRACE, e, request);
            throw e;
        }
    }

    public String executeTransform(String strInput, IWRequest request) throws Exception {
        return this.executeTransform(strInput, true, request);
    }

    public String executeTransform(String strInput, boolean supressXMLDecl, IWRequest request) throws Exception {
        try {
            this.lnkIWServices.logConsole("<!-- Transformation Instance " + this.strTransform + ": -->\n", IWServices.LOG_TRACE, request);
            this.lnkIWServices.logConsole("\n<!-- Transformation Data \n" + strInput + "\n -->\n", this.strTransform.startsWith("iwp2HTML") ? IWServices.LOG_HTTP : IWServices.LOG_REQUEST, request);
            long startInstance = System.currentTimeMillis();
            AbstractTranslet translet = (AbstractTranslet)this.lnkIWServices.loadObject(String.valueOf(this.lnkIWServices.appName) + "." + this.strTransform);
            if (translet != null) {
                long doneInstance = System.currentTimeMillis() - startInstance;
                this.lnkIWServices.logConsole("<!-- Transformation Instance in " + doneInstance + ":msecs -->", IWServices.LOG_TRACE, request);
                DOM dom = this.string2DOM(strInput, translet, request);
                if (dom != null) {
                    long start = System.currentTimeMillis();
                    String result = "";
                    try {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(5000);
                        TransletOutputHandlerFactory factory = TransletOutputHandlerFactory.newInstance();
                        this.lnkIWServices.logConsole("Encoding= " + translet._encoding + " Method= " + translet._method, IWServices.LOG_TRACE, request);
                        factory.setEncoding(translet._encoding);
                        factory.setOutputMethod(translet._method);
                        factory.setOutputStream((OutputStream)outputStream);
                        factory.setOutputType(0);
                        SerializationHandler handler = factory.getSerializationHandler();
                        translet.prepassDocument(dom);
                        translet.transform(dom, handler);
                        byte[] byteArray = outputStream.toByteArray();
                        result = new String(byteArray, translet._encoding);
                        if (supressXMLDecl && result.startsWith("<?xml version")) {
                            result = result.substring(result.indexOf("?>") + 2);
                        }
                    }
                    catch (Exception e) {
                        this.executeError("Transform Error: [" + e.getMessage() + "] ", this.strTransform, strInput);
                        throw e;
                    }
                    long done = System.currentTimeMillis() - start;
                    this.lnkIWServices.logConsole("<!-- Transformed  \n" + result + "\n -->", IWServices.LOG_ALL, request);
                    this.lnkIWServices.logConsole("<!-- Transformed by XSLTC in " + done + ":msecs -->", IWServices.LOG_TRACE, request);
                    return result;
                }
                return this.executeError("DOM Error: ", this.strTransform, strInput);
            }
            return this.executeError("Translet Error: ", this.strTransform, strInput);
        }
        catch (IOException e) {
            this.lnkIWServices.logError("IWXsltcImpl.execute " + e.getMessage(), IWServices.LOG_ERRORS, e, request);
            throw new Exception("IO Exception the translet class: " + this.strTransform);
        }
        catch (ClassNotFoundException e2) {
            this.lnkIWServices.logError("IWXsltcImpl.execute " + e2.getMessage(), IWServices.LOG_ERRORS, e2, request);
            Object e2 = null;
            throw new Exception("Could not locate the translet class: " + this.strTransform);
        }
        catch (Exception e) {
            this.lnkIWServices.logError("IWXsltcImpl.execute " + e.getMessage(), IWServices.LOG_ERRORS, e, request);
            throw e;
        }
    }

    public String executeError(String message, String strTran, String strIn) {
        return this.lnkIWServices.htmlError(message, "<center>" + strTran + "<br/>" + strIn + "</center>");
    }

    public String applyXPath(String strIn, String strXPath) throws Exception {
        try {
            Document doc = this.docFromString(strIn);
            if (doc != null) {
                return this.getXPath(doc, strXPath);
            }
            return null;
        }
        catch (Exception e) {
            this.lnkIWServices.logError("IWXsltcImpl.applyXpath " + e.getMessage(), IWServices.LOG_ERRORS, e, null);
            throw e;
        }
    }

    public StringBuffer getMapData(IWRequest request, IWIDataMap rs, Datamap map, Datamap nextMap) throws Exception {
        Access access;
        String strAccess = request.getParameter("action");
        StringBuffer dataSets = null;
        Translator translator = null;
        rs.setup(map, request);
        if (strAccess == null || strAccess.length() == 0) {
            strAccess = "procedure";
        }
        if ((access = (Access)((Object)rs.getAccessList().get(strAccess))) != null) {
            translator = access.getTranslator();
            if (translator != null) {
                this.strTransform = translator.getInputclass();
                if (this.strTransform != null && this.strTransform.length() > 0) {
                    if (access.isDynamic()) {
                        access.setStatementpre(this.executeTransform(request.getResponse(), access.getStatementpost().indexOf("%xml_declaration%") < 0, request));
                        this.lnkIWServices.logConsole("Dynamic statement: " + access.getStatementpre(), IWServices.LOG_TRACE, request);
                    } else {
                        String translation = this.executeTransform(request.toXML(), request);
                        IWServices lnkServices = request.lnkIWServices;
                        request = new IWRequest(lnkServices);
                        request.rawData = translation;
                        request.fromIWXML();
                    }
                }
                this.strTransform = null;
            }
        } else {
            this.lnkIWServices.logConsole("getMapData: Warning: <!-- Map No Access Object " + map.getName() + " -->", IWServices.LOG_REQUEST, request);
        }
        try {
            try {
                dataSets = rs.go(request);
                this.lnkIWServices.logConsole("getMapData: Query executed " + map.getName() + " -->", IWServices.LOG_TRACE, request);
            }
            catch (Exception e) {
                request.setError(String.valueOf(rs.getClass().getName()) + ".go " + e.getMessage());
                throw e;
            }
        }
        finally {
            if (nextMap != null) {
                String nu = nextMap.getUser();
                String np = nextMap.getPassword();
                Access ca = (Access)((Object)nextMap.getAccess().get(0));
                if (IWServices.getParameterByReference(nextMap.getUrl(), request, ca).equals(rs.getUrl()) && (nu.trim().length() == 0 || IWServices.getParameterByReference(nu, request, ca).equals(rs.getUser())) && (np.trim().length() == 0 || IWServices.getParameterByReference(np, request, ca).equals(rs.getPassword()))) {
                    this.lnkIWServices.logConsole("XsltcImpl: Connection should be reused", IWServices.LOG_TRACE, request);
                } else {
                    this.lnkIWServices.logConsole("XsltcImpl: New Connection should be used. Closing current one.", IWServices.LOG_TRACE, request);
                    request.setLastConnection(null);
                    rs.closeConnection();
                }
            } else {
                this.lnkIWServices.logConsole("XsltcImpl: No next DataMap.Closing Connection.", IWServices.LOG_TRACE, request);
                request.setLastConnection(null);
                rs.closeConnection();
            }
        }
        if (translator != null) {
            this.strTransform = translator.getOutputclass();
            if (this.strTransform != null && this.strTransform.length() > 0) {
                dataSets = new StringBuffer(this.executeTransform(dataSets.toString(), request));
            }
        }
        return dataSets;
    }

    private DOM string2DOM(String strData, AbstractTranslet translet, IWRequest request) throws Exception {
        SAXImpl dom = null;
        SAXParser reader = null;
        try {
            long startHandler = System.currentTimeMillis();
            SAXParser parser = new SAXParser();
            long done = System.currentTimeMillis() - startHandler;
            this.lnkIWServices.logConsole("<!-- Parser Creation in " + done + ":msecs -->", IWServices.LOG_TRANSFORMS, request);
            if (parser instanceof XMLReader) {
                long startReader = System.currentTimeMillis();
                reader = parser;
                reader.setFeature("http://xml.org/sax/features/validation", validate);
                reader.setFeature("http://xml.org/sax/features/namespaces", setNameSpaces);
                reader.setFeature("http://apache.org/xml/features/validation/schema", setSchemaSupport);
                reader.setFeature("http://apache.org/xml/features/continue-after-fatal-error", false);
                long doneReader = System.currentTimeMillis() - startReader;
                this.lnkIWServices.logConsole("<!-- XML Reader Initializr in " + doneReader + ":msecs -->", IWServices.LOG_TRANSFORMS, request);
                long startImpl = System.currentTimeMillis();
                long doneImpl = System.currentTimeMillis() - startImpl;
                this.lnkIWServices.logConsole("<!-- DOM Implementation in " + doneImpl + ":msecs -->", IWServices.LOG_TRANSFORMS, request);
                long startIn = System.currentTimeMillis();
                String xmlData = new String(strData);
                int lim = xmlData.length();
                int i = 0;
                while (i < lim) {
                    try {
                        StringReader fr = new StringReader(xmlData);
                        InputSource in = new InputSource(fr);
                        SAXSource saxSource = new SAXSource((XMLReader)reader, in);
                        XSLTCDTMManager dtmManager = XSLTCDTMManager.newInstance();
                        dom = (SAXImpl)dtmManager.getDTM((Source)saxSource, false, null, true, false, translet.hasIdCall());
                        break;
                    }
                    catch (WrappedRuntimeException e) {
                        Exception ie = e.getException();
                        if (!(ie instanceof SAXParseException)) {
                            this.lnkIWServices.logError("IWXsltcImpl.stringToDom " + e.getMessage(), IWServices.LOG_ERRORS, (Exception)((Object)e), null);
                            this.lnkIWServices.logConsole("Data: \n\n" + xmlData, IWServices.LOG_ERRORS, request);
                            throw e;
                        }
                        this.lnkIWServices.logConsole("IWXsltcImpl.stringToDom: Illegal XML character will be replaced", IWServices.LOG_TRACE, request);
                        xmlData = this.filteredData((SAXParseException)ie, xmlData, request);
                        ++i;
                    }
                }
                long doneIn = System.currentTimeMillis() - startIn;
                this.lnkIWServices.logConsole("<!-- Input Reader in " + doneIn + ":msecs -->", IWServices.LOG_TRANSFORMS, request);
                return dom;
            }
            parser = null;
            this.lnkIWServices.logConsole("Paresr not XMLReader: " + parser.getClass(), IWServices.LOG_TRANSFORMS, request);
        }
        catch (Exception ex) {
            this.lnkIWServices.logError("IWXsltcImpl.stringToDom " + ex.getMessage(), IWServices.LOG_ERRORS, ex, null);
            this.lnkIWServices.logConsole("Data: \n\n" + strData, IWServices.LOG_ERRORS, request);
            throw ex;
        }
        return dom;
    }

    public synchronized Document docFromString(String strData) throws Exception {
        String xmlData = new String(strData);
        int lim = xmlData.length();
        int i = 0;
        while (i < lim) {
            try {
                StringReader fr = new StringReader(xmlData);
                InputSource in = new InputSource(fr);
                DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
                dfactory.setNamespaceAware(false);
                Document doc = dfactory.newDocumentBuilder().parse(in);
                return doc;
            }
            catch (SAXParseException e) {
                this.lnkIWServices.logConsole("IWXsltcImpl.docFromString: Illegal XML character will be replaced", IWServices.LOG_TRACE, this.iwRequest);
                xmlData = this.filteredData(e, xmlData, this.iwRequest);
            }
            catch (Exception e) {
                this.lnkIWServices.logError("IWXsltcImpl.docFromString " + e.getMessage(), IWServices.LOG_ERRORS, e, null);
                this.lnkIWServices.logConsole("Data: \n\n" + strData, IWServices.LOG_ERRORS, this.iwRequest);
                throw e;
            }
            ++i;
        }
        return null;
    }

    private synchronized String filteredData(SAXParseException e, String xmlData, IWRequest request) throws Exception {
        this.lnkIWServices.logConsole("IWXsltcImpl. Line: " + e.getLineNumber() + " Char: " + e.getColumnNumber(), IWServices.LOG_TRACE, request);
        StringReader fr = new StringReader(xmlData);
        BufferedReader br = new BufferedReader(fr);
        StringWriter wo = new StringWriter();
        BufferedWriter bo = new BufferedWriter(wo);
        int lc = 1;
        try {
            String is;
            while ((is = br.readLine()) != null) {
                if (e.getLineNumber() == lc++) {
                    this.lnkIWServices.logConsole("IWXsltcImpl. Line with symbol to replace: " + is, IWServices.LOG_TRACE, request);
                    StringBuffer ts = new StringBuffer(is);
                    ts.setCharAt(e.getColumnNumber() - 1, ' ');
                    is = ts.toString();
                }
                bo.write(is);
                bo.newLine();
            }
            br.close();
            bo.close();
            return wo.toString();
        }
        catch (IOException e1) {
            this.lnkIWServices.logError("IWXsltcImpl: Unable to filter iwp;", IWServices.LOG_TRACE, e1, null);
            throw e1;
        }
    }

    public String getXPath(Node n, String xpath) throws Exception {
        NodeIterator nlChannel = null;
        Node nChannel = null;
        try {
            nlChannel = XPathAPI.selectNodeIterator((Node)n, (String)xpath);
            nChannel = nlChannel.nextNode();
            if (nChannel != null) {
                NodeList nlst = nChannel.getChildNodes();
                int count = 0;
                while ((nChannel = nlst.item(count++)) != null) {
                    if (nChannel.getNodeType() != 3) continue;
                    return nChannel.getNodeValue();
                }
            }
        }
        catch (Exception e) {
            this.lnkIWServices.logError("IWXsltcImpl.getXpath " + e.getMessage(), IWServices.LOG_ERRORS, e, null);
            this.lnkIWServices.logConsole("Data: \n\n" + xpath, IWServices.LOG_ERRORS, this.iwRequest);
            throw e;
        }
        return null;
    }

    public String getXML() {
        return this.getXML(-1);
    }

    public String getXML(int dataMapNumber) {
        StringBuffer out;
        block4: {
            String strData2 = null;
            out = new StringBuffer();
            if (this.responses.size() <= 0) break block4;
            if (dataMapNumber < 0) {
                for (String strData2 : this.responses) {
                    out.append(strData2);
                }
            } else {
                int rs = this.responses.size();
                int i = rs - dataMapNumber;
                while (i < rs) {
                    out.append(this.responses.get(i));
                    ++i;
                }
            }
        }
        return out.toString();
    }

    public String getFlashResponse(IWRequest iwRequest) {
        StringBuffer out = new StringBuffer();
        out.append("<" + this.lnkIWServices.appName + ">\n");
        if (this.outputs.size() > 0) {
            Enumeration enumerateerate = this.outputs.keys();
            while (enumerateerate.hasMoreElements()) {
                String key = (String)enumerateerate.nextElement();
                String value = (String)this.outputs.get(key);
                out.append(value);
                key = null;
                value = null;
            }
        }
        this.outputs.clear();
        String error = iwRequest.getError();
        boolean iwError = false;
        if (error != null && error.length() > 0) {
            iwError = true;
            out = null;
            out = new StringBuffer();
            out.append("<" + this.lnkIWServices.appName + ">\n");
            out.append("<Error>");
            out.append(error);
            out.append("</Error>\n");
            this.lnkIWServices.logConsole("<!-- Output IWError\n" + out.toString() + ": -->\n", IWServices.LOG_ALL, iwRequest);
        }
        out.append("</" + this.lnkIWServices.appName + ">\n");
        String response = out.toString();
        this.lnkIWServices.logConsole("<-- Response\n" + response + "\n-->", IWServices.LOG_DATA, iwRequest);
        return response;
    }

    public void displayDom(Node displayNode) {
        if (this.serializer == null) {
            this.initializeSerializer();
        }
        try {
            this.serializer.asDOMSerializer().serialize(displayNode);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void initializeSerializer() {
        Serializer serializer = SerializerFactory.getSerializer((Properties)OutputProperties.getDefaultMethodProperties((String)"xml"));
        serializer.setOutputStream((OutputStream)System.out);
    }
}

