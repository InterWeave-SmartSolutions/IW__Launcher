/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.adapter.http.IWHttpBaseAdaptor;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IWXMLBaseAdaptor
extends IWHttpBaseAdaptor {
    protected StringBuffer lastRequest = new StringBuffer();
    protected boolean useAttributes = false;

    public StringBuffer go(IWRequest request) throws Exception {
        return this.go(request, false, false);
    }

    public StringBuffer go(IWRequest request, boolean isJSon, boolean coockies) throws Exception {
        int n;
        this.iwRequest = request;
        StringBuffer sres = super.go(this.iwRequest, "", "", !isJSon, this.lastRequest);
        this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: full input: " + sres, IWServices.LOG_TRACE, request);
        if (isJSon) {
            int lix;
            if (sres.indexOf(">") > 0) {
                String[] jrs1 = sres.toString().split(">");
                sres = new StringBuffer("");
                String[] stringArray = jrs1;
                int n2 = 0;
                n = stringArray.length;
                while (n2 < n) {
                    String cr1 = stringArray[n2];
                    String crt1 = this.processJson(cr1, request);
                    if (crt1.length() > 0) {
                        sres.append(crt1).append("`");
                    }
                    ++n2;
                }
            } else {
                sres = new StringBuffer(this.processJson(sres.toString(), request));
            }
            if (coockies && (lix = sres.lastIndexOf("</")) > 0) {
                StringBuffer tsrs = new StringBuffer(sres.substring(0, lix));
                sres = tsrs.append(this.convertCookies(request)).append(sres.substring(lix));
                this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: Result with Cookie: " + sres, IWServices.LOG_TRACE, request);
            }
        }
        String[] rs = IWServices.splitXML(sres.toString());
        StringBuffer res = new StringBuffer();
        String[] stringArray = rs;
        n = 0;
        int n3 = stringArray.length;
        while (n < n3) {
            String cr = stringArray[n];
            String crt = cr.trim();
            if (crt.startsWith("<") && crt.endsWith(">")) {
                res.append(this.xml2DataMap(crt, request));
            } else {
                int xb = crt.indexOf("<");
                if (xb < 0) {
                    res.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Result", crt)))));
                } else {
                    int xe;
                    if (xb > 0) {
                        res.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Prologue", crt.substring(0, xb))))));
                    }
                    if ((xe = crt.lastIndexOf(">")) < 0) {
                        res.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Epilogue", crt.substring(xb))))));
                    } else {
                        res.append(this.xml2DataMap(crt.substring(xb, xe + 1), request));
                        res.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("Epilogue", crt.substring(xe + 1))))));
                    }
                }
            }
            ++n;
        }
        return res;
    }

    protected StringBuffer convertCookies(IWRequest request) {
        StringBuffer ret = new StringBuffer();
        Map<String, List<String>> map = this.httpURLConnection.getHeaderFields();
        this.iwRequest.lnkIWServices.logConsole("Printing Response Header...\n", IWServices.LOG_TRACE, request);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String cvl : entry.getValue()) {
                int smcpos;
                int eqpos;
                String ckey = entry.getKey();
                if (ckey == null) {
                    ckey = "NullKey";
                }
                if (cvl == null) {
                    cvl = "";
                } else if (cvl.startsWith(".")) {
                    cvl = "_" + cvl;
                }
                if (ckey.equals("set-cookie") && (eqpos = cvl.indexOf("=")) > 0 && (smcpos = cvl.indexOf(";", eqpos)) > 0) {
                    ret.append("<" + cvl.substring(0, eqpos) + " type=\"string\">" + cvl.substring(eqpos + 1, smcpos) + "</" + cvl.substring(0, eqpos) + ">");
                }
                this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: Cookie: " + ckey + " : " + cvl, IWServices.LOG_TRACE, request);
            }
        }
        return ret;
    }

    protected String processJson(String sres, IWRequest request) {
        int bjs;
        int bjs0 = sres.indexOf("{");
        int bjs1 = sres.indexOf("[");
        int n = bjs = bjs1 >= 0 ? Math.min(bjs0, bjs1) : bjs0;
        if (bjs >= 0) {
            int ejs;
            int ejs0 = sres.lastIndexOf("}");
            int ejs1 = sres.lastIndexOf("]");
            int n2 = ejs = ejs1 > 0 ? Math.max(ejs0, ejs1) : ejs0;
            if (ejs > bjs) {
                String docJSon = sres.substring(bjs, ++ejs);
                this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input: " + docJSon, IWServices.LOG_TRACE, request);
                if (bjs == bjs0) {
                    return this.processJSonObject(docJSon, request);
                }
                return this.processJSonArray(docJSon, request);
            }
            return "";
        }
        return "";
    }

    protected String processJSonArray(String docJSon, IWRequest request) {
        JSONArray newJA = JSONArray.fromObject((Object)docJSon);
        StringBuffer ret = new StringBuffer("");
        int i = 0;
        while (i < newJA.size()) {
            Object jaO = newJA.get(i);
            if (jaO instanceof JSONArray) {
                ret.append(this.processJSonArray(((JSONArray)jaO).toString(), request));
            }
            if (jaO instanceof JSONObject) {
                ret.append(this.processJSonObject(((JSONObject)jaO).toString(), request)).append("`");
            }
            ++i;
        }
        return ret.toString();
    }

    protected String processJSonObject(String docJSon, IWRequest request) {
        int hxmle;
        XMLSerializer xsrl = new XMLSerializer();
        JSONObject jsno = this.preProcessJSon(JSONObject.fromObject((Object)docJSon), this.iwRequest);
        String xml = "";
        try {
            xml = xsrl.write((JSON)jsno);
            this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon to XML output: " + xml, IWServices.LOG_TRACE, request);
        }
        catch (RuntimeException e) {
            this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon to XML conversion failed: " + e.toString(), IWServices.LOG_TRACE, request);
            xml = docJSon;
        }
        int hxml = xml.indexOf("<?");
        if (hxml >= 0 && (hxmle = xml.indexOf("?>", hxml + 2)) > 0) {
            xml = xml.substring(hxmle + 2);
        }
        return xml.trim();
    }

    protected JSONObject preProcessJSon(JSONObject jsno, IWRequest request) {
        JSONObject retJSno = jsno;
        Set ks = retJSno.keySet();
        for (String fn : ks) {
            String docJSonR;
            String fnn;
            String docJSonR2;
            Object fVal = retJSno.get(fn);
            request.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input Id: " + fn, IWServices.LOG_TRACE, request);
            Character fn1c = Character.valueOf(fn.charAt(0));
            if (Character.isDigit(fn1c.charValue()) || fn1c.charValue() == '.' || fn1c.charValue() == '-') {
                request.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input wrong Id: " + fn, IWServices.LOG_TRACE, request);
                docJSonR2 = retJSno.toString();
                if (docJSonR2 != null) {
                    docJSonR2 = docJSonR2.replace("\"" + fn + "\":", "\"N" + fn + "\":");
                    retJSno = JSONObject.fromObject((Object)docJSonR2);
                    this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input wrong Id replaced", IWServices.LOG_TRACE, request);
                    fn = "N" + fn;
                }
                request.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input correct Id: " + fn, IWServices.LOG_TRACE, request);
            }
            if (fn1c.charValue() == '.') {
                request.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input wrong Id: " + fn, IWServices.LOG_TRACE, request);
                docJSonR2 = retJSno.toString();
                if (docJSonR2 != null) {
                    docJSonR2 = docJSonR2.replace("\"" + fn + "\":", "\"_" + fn.substring(1) + "\":");
                    retJSno = JSONObject.fromObject((Object)docJSonR2);
                    this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input wrong Id replaced (first character)", IWServices.LOG_TRACE, request);
                    fn = "_" + fn.substring(1);
                }
            }
            if (!(fnn = fn.replace('$', '_').replace('@', '_').replace('%', '_').replace('&', '_')).equals(fn) && (docJSonR = retJSno.toString()) != null) {
                docJSonR = docJSonR.replace("\"" + fn + "\":", "\"" + fnn + "\":");
                retJSno = JSONObject.fromObject((Object)docJSonR);
                this.iwRequest.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input wrong Id replaced (any character)", IWServices.LOG_TRACE, request);
                fn = fnn;
            }
            request.lnkIWServices.logConsole("IWXMLBaseAdaptor.go: JSon input Id after replacing: " + fn, IWServices.LOG_TRACE, request);
            if (fVal instanceof JSONArray) {
                JSONArray retJa = this.preProcessJSonArray((JSONArray)fVal, request);
                retJSno.put((Object)fn, (Object)retJa);
            }
            if (!(fVal instanceof JSONObject)) continue;
            JSONObject retJo = this.preProcessJSon((JSONObject)fVal, request);
            retJSno.put((Object)fn, (Object)retJo);
        }
        return retJSno;
    }

    protected JSONArray preProcessJSonArray(JSONArray jao, IWRequest request) {
        JSONArray jaor = jao;
        int i = 0;
        while (i < jaor.size()) {
            Object jaO = jaor.get(i);
            if (jaO instanceof JSONArray) {
                JSONArray jaor1 = this.preProcessJSonArray((JSONArray)jaO, request);
                jaor.element(i, (Collection)jaor1);
            }
            if (jaO instanceof JSONObject) {
                JSONObject retJaO = this.preProcessJSon((JSONObject)jaO, request);
                jaor.element(i, (Map)retJaO);
            }
            ++i;
        }
        return jaor;
    }

    protected StringBuffer xml2DataMap(String inputXML, IWRequest request) throws Exception {
        return this.xml2DataMap(inputXML, request, false);
    }

    protected StringBuffer xml2DataMap(String inputXML, IWRequest request, boolean noWrapper) throws Exception {
        this.iwRequest.lnkIWServices.logConsole("xml2DataMap started: " + (inputXML.length() <= 20 ? inputXML : inputXML.substring(0, 20)) + "...", IWServices.LOG_TRACE, request);
        StringBuffer responseBuffer = new StringBuffer("");
        if (inputXML.trim().length() == 0) {
            return this.addDataMapXML(this.addDataXML(responseBuffer));
        }
        ++this.dataMapID;
        String spost = this.curAccess.getStatementpost();
        if (spost == null || spost.indexOf("%ignore_default%") < 0) {
            int bt;
            String result = inputXML.trim();
            if (spost != null) {
                String lr;
                if (spost.indexOf("%ignore_repeat%") >= 0 && result.startsWith(lr = this.lastRequest.toString().trim())) {
                    result = result.substring(lr.length()).trim();
                }
                boolean bl = this.useAttributes = spost.indexOf("%use_attributes%") >= 0;
            }
            if ((bt = result.indexOf("<")) < 0) {
                throw new Exception("Non-xml document!");
            }
            int et = result.lastIndexOf("<");
            if (result.startsWith("<html>", bt) && result.substring(et).startsWith("</html>")) {
                this.iwRequest.lnkIWServices.logConsole("xml2DataMap: html  to be converted", IWServices.LOG_TRACE, request);
                result = this.convertHTML2XML(result);
                if (result == null) {
                    throw new Exception("Unable to convert HTML to XML!");
                }
                this.iwRequest.lnkIWServices.logConsole("xml2DataMap: html converted - " + result, IWServices.LOG_TRACE, request);
            }
            if (result.startsWith("<?xml version")) {
                result = result.substring(result.indexOf("?>") + 2);
            }
            responseBuffer = this.xml2Data(result, noWrapper);
        } else {
            responseBuffer.append(inputXML);
        }
        if (!this.iwRequest.isFilterTransaction() && !noWrapper) {
            responseBuffer = this.addDataMapXML(responseBuffer);
        }
        return responseBuffer;
    }

    private boolean openTagExists(String mls, String tagName) {
        String ctag = "</" + tagName + ">";
        String otag = "<" + tagName;
        int lps = mls.lastIndexOf(ctag);
        int otp = mls.lastIndexOf(otag);
        if (lps < 0) {
            return otp >= 0;
        }
        if (otp > 0) {
            if (otp > lps) {
                return true;
            }
            if (this.openTagExists(mls.substring(0, lps), tagName)) {
                int lp;
                int cl = lps;
                int dp = 0;
                while ((lp = mls.substring(otp, cl).indexOf(ctag)) >= 0) {
                    cl = lp;
                    ++dp;
                }
                int i = 0;
                while (i < dp) {
                    if ((otp = mls.substring(0, otp).lastIndexOf(otag)) < 0) {
                        return false;
                    }
                    ++i;
                }
                return this.openTagExists(mls.substring(0, otp), tagName);
            }
            return false;
        }
        return false;
    }

    private String convertHTML2XML(String inpHTML) {
        int rps;
        int lps;
        int sp;
        StringBuffer rx = new StringBuffer();
        String inpHTMLs = inpHTML.replaceAll("\\s+", " ");
        String inpHTMLS = inpHTMLs.replaceAll("&nbsp;", "");
        String li = inpHTMLS.toLowerCase();
        int cp = 0;
        while ((sp = li.indexOf("<script", cp)) >= 0) {
            rx.append(inpHTMLS.substring(cp, sp));
            cp = li.indexOf("</script>", sp + 7);
            if (cp < 0) {
                return null;
            }
            cp += 9;
        }
        rx.append(inpHTMLS.substring(cp));
        cp = 0;
        while ((lps = rx.indexOf("</", cp)) >= 0) {
            rps = rx.indexOf(">", lps + 2);
            if (rps < 0) {
                return null;
            }
            if (this.openTagExists(rx.substring(0, lps), rx.substring(lps + 2, rps).trim())) {
                cp = rps + 1;
                continue;
            }
            rx.delete(lps, rps + 1);
            cp = lps;
        }
        cp = 0;
        while ((lps = rx.indexOf("<", cp)) >= 0) {
            int stps0;
            rps = rx.indexOf(">", lps + 1);
            if (rps < 0) {
                return null;
            }
            if (rx.charAt(lps + 1) == '/' || rx.charAt(lps + 1) == '!') {
                cp = rps + 1;
                continue;
            }
            String tag = rx.substring(lps + 1, rps);
            String[] ta = tag.split(" ");
            if (ta.length > 1) {
                int delpos = ta[0].length() + 1;
                int i = 1;
                while (i < ta.length) {
                    int epos = ta[i].indexOf("=");
                    if (epos < 0) {
                        if (i == ta.length - 1) {
                            if (ta[i].endsWith("/")) {
                                rx.delete(lps + 1 + delpos, lps + 1 + delpos + ta[i].length() - 1);
                                rps -= ta[i].length() - 1;
                            } else {
                                rx.delete(lps + 1 + delpos, lps + 1 + delpos + ta[i].length());
                                rps -= ta[i].length();
                            }
                        } else {
                            rx.delete(lps + 1 + delpos, lps + 1 + delpos + ta[i].length() + 1);
                            rps -= ta[i].length() + 1;
                        }
                    } else {
                        String dlm = ta[i].substring(epos + 1, epos + 2);
                        if (dlm.equals("'") || dlm.equals("\"")) {
                            while (!ta[i].endsWith(dlm)) {
                                if (i != ta.length - 1) {
                                    delpos += ta[i++].length() + 1;
                                    continue;
                                }
                                break;
                            }
                        } else {
                            char dc = ta[i].substring(epos + 1).indexOf("\"") >= 0 ? (char)'\'' : '\"';
                            rx.insert(lps + 1 + delpos + epos + 1, dc);
                            rx.insert(lps + 1 + delpos + ta[i].length() + 1, dc);
                            delpos += 2;
                            rps += 2;
                        }
                        delpos += ta[i].length() + 1;
                    }
                    ++i;
                }
            }
            if (rx.charAt(rps - 1) == '/') {
                cp = rps + 1;
                continue;
            }
            int ne = rx.indexOf(" ", lps + 1);
            if (ne < 0 || ne > rps) {
                ne = rps;
            }
            if ((stps0 = rx.indexOf("</" + rx.substring(lps + 1, ne) + ">", rps + 1)) < 0) {
                rx.insert(rps, '/');
                cp = rps + 2;
                continue;
            }
            cp = rps + 1;
        }
        return rx.toString();
    }

    protected StringBuffer xml2Data(String xmlString) throws Exception {
        return this.xml2Data(xmlString, false);
    }

    /*
     * Unable to fully structure code
     */
    protected StringBuffer xml2Data(String xmlString, boolean noWrapper) throws Exception {
        this.iwRequest.lnkIWServices.logConsole("XML go: String to parse: " + xmlString, IWServices.LOG_TRACE, this.iwRequest);
        responseBuffer = new StringBuffer();
        parser = new DOMParser();
        temp = xmlString.getBytes();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e) {
            ict = 0;
            ** while (ict < temp.length)
        }
lbl-1000:
        // 1 sources

        {
            if (!(temp[ict] == 9 || temp[ict] == 10 || temp[ict] == 13 || temp[ict] >= 32 && temp[ict] <= 55295 || temp[ict] >= 57344 && temp[ict] <= 65533 || temp[ict] >= 65536 && temp[ict] <= 0x10FFFF)) {
                temp[ict] = 32;
            }
            ++ict;
            continue;
        }
lbl16:
        // 1 sources

        this.iwRequest.lnkIWServices.logConsole("XML go: Reduced string to parse: " + new String(temp), IWServices.LOG_TRACE, this.iwRequest);
        parser = new DOMParser();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e1) {
            throw e1;
        }
        catch (IOException e1) {
            throw e1;
        }
        catch (IOException e) {
            throw e;
        }
        xmlDoc = parser.getDocument();
        res = new StringBuffer();
        rN = new ArrayList<String>();
        rQN = new StringBuffer();
        rQ = new StringBuffer();
        r2QN = new StringBuffer();
        r2Q = new StringBuffer();
        this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
        this.returnName = rN.toArray(new String[0]);
        this.replaceQuoteName = rQN.toString();
        this.replaceQuote = rQ.toString();
        this.replace2QuoteName = r2QN.toString();
        this.replace2Quote = r2Q.toString();
        this.leaf2RowCol(xmlDoc.getDocumentElement(), res, new StringBuffer(), 0);
        if (!this.iwRequest.isFilterTransaction()) {
            responseBuffer = noWrapper != false ? res : this.addDataXML(res);
        }
        return responseBuffer;
    }

    protected int leaf2RowCol(Node inputNode, StringBuffer response, StringBuffer dataBuffer, int cn) {
        NamedNodeMap nnm;
        if (inputNode.getNodeType() != 1) {
            return cn;
        }
        NodeList cnl = inputNode.getChildNodes();
        if (cnl == null) {
            return cn;
        }
        String colName = inputNode.getLocalName();
        int chn = cnl.getLength();
        int attrnum = 0;
        if (this.useAttributes && (nnm = inputNode.getAttributes()) != null) {
            int i = 0;
            while (i < nnm.getLength()) {
                Node aItem = nnm.item(i);
                String attrName = String.valueOf(aItem.getNodeName()) + "_at_" + colName;
                try {
                    String strData = IWServices.processParameters(this.iwRequest, aItem.getNodeValue(), attrName, this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                    dataBuffer.append("          <col number=\"" + (cn + attrnum++) + "\" name=\"" + attrName + "\">" + IWServices.escape(strData) + "</col>\n");
                }
                catch (Exception e) {
                    this.iwRequest.lnkIWServices.logError("xml2iwp: unable to process parameters", IWServices.LOG_TRACE, e, null);
                    this.returnName = null;
                }
                ++i;
            }
        }
        if (chn > 0) {
            StringBuffer outCol = new StringBuffer();
            if (chn == 1 && cnl.item(0).getNodeType() == 3) {
                Node currentNode = cnl.item(0);
                String strData = currentNode.getNodeValue();
                if (this.iwRequest.isFilterTransaction()) {
                    if (strData.length() > 0) {
                        IWServices.filterRows(strData, this.iwRequest, this.curAccess);
                    }
                } else {
                    try {
                        strData = IWServices.processParameters(this.iwRequest, strData, colName, this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                    }
                    catch (Exception e) {
                        this.iwRequest.lnkIWServices.logError("xml2iwp: unable to process parameters", IWServices.LOG_ERRORS, e, null);
                        this.returnName = null;
                    }
                    dataBuffer.append("          <col number=\"" + (cn + attrnum) + "\" name=\"" + colName + "\">" + IWServices.escape(strData) + "</col>\n");
                }
                return cn + 1;
            }
            int cnc = cn;
            int i = 0;
            while (i < chn) {
                Node curNode = cnl.item(i);
                if (curNode != null) {
                    if (curNode.getNodeType() == 1 && ((Element)curNode).getElementsByTagName("*").getLength() > 0) {
                        StringBuffer oC = new StringBuffer();
                        StringBuffer tD = new StringBuffer();
                        if (this.leaf2RowCol(curNode, response, tD, 0) > 0) {
                            oC.append("        <row number=\"" + this.rowcount++ + "\">\n" + tD.toString() + "        </row>\n");
                            response.append(oC);
                        }
                    } else {
                        cnc = this.leaf2RowCol(curNode, response, dataBuffer, cnc);
                    }
                }
                ++i;
            }
            if (cnc > cn) {
                outCol.append("        <row number=\"" + this.rowcount++ + "\">\n" + dataBuffer.toString() + "        </row>\n");
                response.append(outCol);
                dataBuffer = new StringBuffer();
            }
            return cn;
        }
        dataBuffer.append("          <col number=\"" + (cn + attrnum) + "\" name=\"" + inputNode.getLocalName() + "\"></col>\n");
        return cn + 1;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        super.setup(map, request, true);
        if (this.httpURLConnection != null) {
            if (this.encodingType == null) {
                request.lnkIWServices.logConsole("Empty Encoding in XMLBaseAdaptor", IWServices.LOG_REQUEST, request);
                this.encodingType = "text/xml; charset=utf-8";
                this.httpURLConnection.setRequestProperty("Content-Type", this.encodingType);
            }
            request.lnkIWServices.logConsole("Encoding: " + this.httpURLConnection.getRequestProperty("Content-Type"), IWServices.LOG_REQUEST, request);
        }
    }
}

