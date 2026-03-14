/*
 * Decompiled with CFR.
 */
package com.altova.xml;

import com.altova.xml.Node;
import com.altova.xml.XmlException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class Document
implements Serializable {
    protected static DocumentBuilderFactory factory = null;
    protected static DocumentBuilder builder = null;
    protected static org.w3c.dom.Document tmpDocument = null;
    protected static DocumentFragment tmpFragment = null;
    protected static int tmpNameCounter = 0;
    protected String encoding = "UTF-8";
    protected String rootElementName = null;
    protected String namespaceURI = null;
    protected String schemaLocation = null;

    protected static synchronized DocumentBuilder getDomBuilder() {
        try {
            if (builder == null) {
                if (factory == null) {
                    factory = DocumentBuilderFactory.newInstance();
                    factory.setIgnoringElementContentWhitespace(true);
                    factory.setNamespaceAware(true);
                }
                builder = factory.newDocumentBuilder();
                builder.setErrorHandler(new ErrorHandler(){

                    public void warning(SAXParseException e) {
                    }

                    public void error(SAXParseException e) throws XmlException {
                        throw new XmlException(e);
                    }

                    public void fatalError(SAXParseException e) throws XmlException {
                        throw new XmlException(e);
                    }
                });
            }
            return builder;
        }
        catch (ParserConfigurationException e) {
            throw new XmlException(e);
        }
    }

    protected static synchronized org.w3c.dom.Document getTemporaryDocument() {
        if (tmpDocument == null) {
            tmpDocument = Document.getDomBuilder().newDocument();
        }
        return tmpDocument;
    }

    protected static synchronized org.w3c.dom.Node createTemporaryDomNode() {
        String tmpName = "_" + tmpNameCounter++;
        if (tmpFragment == null) {
            tmpFragment = Document.getTemporaryDocument().createDocumentFragment();
            tmpDocument.appendChild(tmpFragment);
        }
        Element node = Document.getTemporaryDocument().createElement(tmpName);
        tmpFragment.appendChild(node);
        return node;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setRootElementName(String namespaceURI, String rootElementName) {
        this.namespaceURI = namespaceURI;
        this.rootElementName = rootElementName;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public org.w3c.dom.Node load(String filename) {
        try {
            return Document.getDomBuilder().parse(new File(filename)).getDocumentElement();
        }
        catch (SAXException e) {
            throw new XmlException(e);
        }
        catch (IOException e) {
            throw new XmlException(e);
        }
    }

    public org.w3c.dom.Node load(InputStream istream) {
        try {
            return Document.getDomBuilder().parse(istream).getDocumentElement();
        }
        catch (SAXException e) {
            throw new XmlException(e);
        }
        catch (IOException e) {
            throw new XmlException(e);
        }
    }

    public void save(String filename, Node node) {
        this.finalizeRootElement(node);
        Node.internalAdjustPrefix(node.domNode, true);
        node.adjustPrefix();
        Document.internalSave(new StreamResult(new File(filename)), node.domNode.getOwnerDocument(), this.encoding);
    }

    public void save(OutputStream ostream, Node node) {
        this.finalizeRootElement(node);
        Node.internalAdjustPrefix(node.domNode, true);
        node.adjustPrefix();
        Document.internalSave(new StreamResult(ostream), node.domNode.getOwnerDocument(), this.encoding);
    }

    protected static void internalSave(Result result, org.w3c.dom.Document doc, String encoding) {
        try {
            DOMSource source = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            if (encoding != null) {
                transformer.setOutputProperty("encoding", encoding);
            }
            transformer.transform(source, result);
        }
        catch (TransformerConfigurationException e) {
            throw new XmlException(e);
        }
        catch (TransformerException e) {
            throw new XmlException(e);
        }
    }

    public org.w3c.dom.Node transform(Node node, String xslFilename) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFilename));
            DOMResult result = new DOMResult();
            transformer.transform(new DOMSource(node.domNode), result);
            return result.getNode();
        }
        catch (TransformerException e) {
            throw new XmlException(e);
        }
    }

    protected void finalizeRootElement(Node root) {
        if (root.domNode.getParentNode().getNodeType() != 11) {
            return;
        }
        if (this.rootElementName == null || this.rootElementName.equals("")) {
            throw new XmlException("Call setRootElementName first");
        }
        org.w3c.dom.Document doc = Document.getDomBuilder().newDocument();
        Element newRootElement = doc.createElementNS(this.namespaceURI, this.rootElementName);
        root.cloneInto(newRootElement);
        doc.appendChild(newRootElement);
        newRootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        if (this.namespaceURI == null || this.namespaceURI.equals("")) {
            if (this.schemaLocation != null && this.schemaLocation != "") {
                newRootElement.setAttribute("xsi:noNamespaceSchemaLocation", this.schemaLocation);
            }
        } else if (this.schemaLocation != null && this.schemaLocation != "") {
            newRootElement.setAttribute("xsi:schemaLocation", String.valueOf(this.namespaceURI) + " " + this.schemaLocation);
        }
        root.domNode = newRootElement;
        this.declareNamespaces(root);
    }

    public abstract void declareNamespaces(Node var1);

    protected void declareNamespace(Node node, String prefix, String URI2) {
        node.declareNamespace(prefix, URI2);
    }
}

