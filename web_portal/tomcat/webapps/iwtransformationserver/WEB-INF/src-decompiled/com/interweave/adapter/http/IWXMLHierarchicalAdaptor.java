/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.adapter.http.IWXMLBaseAdaptor;
import com.interweave.core.IWServices;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IWXMLHierarchicalAdaptor
extends IWXMLBaseAdaptor {
    protected StringBuffer xpathColumn = new StringBuffer();

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
        int resIniPos = response.length();
        int xln = this.xpathColumn.length();
        this.xpathColumn.append("/" + colName);
        if (this.useAttributes && (nnm = inputNode.getAttributes()) != null) {
            int i = 0;
            while (i < nnm.getLength()) {
                Node aItem = nnm.item(i);
                String attrName = String.valueOf(aItem.getNodeName()) + "_at_" + colName;
                try {
                    int xlna = this.xpathColumn.length();
                    this.xpathColumn.append("/" + attrName);
                    String strData = IWServices.processParameters(this.iwRequest, aItem.getNodeValue(), this.xpathColumn.toString(), this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                    this.xpathColumn.setLength(xlna);
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
                        strData = IWServices.processParameters(this.iwRequest, strData, this.xpathColumn.toString(), this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                    }
                    catch (Exception e) {
                        this.iwRequest.lnkIWServices.logError("xml2iwp: unable to process parameters", IWServices.LOG_ERRORS, e, null);
                        this.returnName = null;
                    }
                    dataBuffer.append("          <col number=\"" + (cn + attrnum) + "\" name=\"" + colName + "\">" + IWServices.escape(strData) + "</col>\n");
                }
                this.xpathColumn.setLength(xln);
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
            if (cnc > cn || cn == 0 && this.useAttributes && dataBuffer.length() > 0) {
                outCol.append("        <row number=\"" + this.rowcount++ + "\">\n" + dataBuffer.toString() + "        </row>\n");
                response.append(outCol);
                dataBuffer = new StringBuffer();
            }
            this.addRowSetXML(response, resIniPos, inputNode.getLocalName());
            this.xpathColumn.setLength(xln);
            return cn;
        }
        dataBuffer.append("          <col number=\"" + (cn + attrnum) + "\" name=\"" + inputNode.getLocalName() + "\"></col>\n");
        this.xpathColumn.setLength(xln);
        return cn + 1;
    }
}

