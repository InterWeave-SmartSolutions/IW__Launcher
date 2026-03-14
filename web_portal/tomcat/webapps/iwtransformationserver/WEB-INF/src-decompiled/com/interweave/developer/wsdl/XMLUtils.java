/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer.wsdl;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils {
    private static HashMap nodeTypeMapping = null;

    public static String dropNamespace(String name) {
        int pos;
        String result = name;
        if (name != null && (pos = result.indexOf(58)) != -1) {
            result = result.substring(pos + 1);
        }
        return result;
    }

    public static String[] getXPathValues(Document doc, String xPath) throws TransformerException {
        Vector<String> result = new Vector<String>();
        NodeIterator ni = XPathAPI.selectNodeIterator((Node)doc, (String)xPath);
        Node n = ni.nextNode();
        while (n != null) {
            if (n.getNodeType() == 1) {
                result.add(n.getNodeName());
            } else if (n.getNodeType() == 2) {
                result.add(n.getNodeValue());
            } else {
                result.add(n.getNodeValue());
            }
            n = ni.nextNode();
        }
        String[] resultArray = new String[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }

    private static Node selectNode_nsaware(Document doc, Document doc_nsaware, Node n) {
        Node n_nsaware = null;
        if (n.equals(doc)) {
            n_nsaware = doc_nsaware;
        } else {
            Node parent = n.getParentNode();
            Node parent_nsaware = XMLUtils.selectNode_nsaware(doc, doc_nsaware, parent);
            NodeList childs = parent.getChildNodes();
            NodeList childs_nsaware = parent_nsaware.getChildNodes();
            int i = 0;
            while (i < childs.getLength()) {
                if (n.equals(childs.item(i))) {
                    n_nsaware = childs_nsaware.item(i);
                }
                ++i;
            }
        }
        return n_nsaware;
    }

    public static String[] getXPathType(Document doc, Document doc_nsaware, String xPath) {
        String[] result = new String[3];
        try {
            NodeIterator ni = XPathAPI.selectNodeIterator((Node)doc, (String)xPath);
            Node n = ni.nextNode();
            if (n != null && n.getNodeType() == 2) {
                String type = null;
                String namespace = null;
                Object namespaceURI = null;
                String attribute = n.getNodeValue();
                String[] ns_type = attribute.split(":");
                if (ns_type.length == 1) {
                    namespace = null;
                    type = ns_type[0];
                } else {
                    namespace = ns_type[0];
                    type = ns_type[1];
                }
                result[0] = namespace;
                result[1] = namespaceURI;
                result[2] = type;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void dumpNode(Node n, String indention) {
        String nodeinfo = XMLUtils.getNodeInfo(n);
        String childindention = String.valueOf(indention) + " ";
        NamedNodeMap attributes = n.getAttributes();
        if (attributes != null) {
            int i = 0;
            while (i < attributes.getLength()) {
                Node attrib = attributes.item(i);
                XMLUtils.dumpNode(attrib, childindention);
                ++i;
            }
        }
        NodeList childNodes = n.getChildNodes();
        int i = 0;
        while (i < childNodes.getLength()) {
            Node child = childNodes.item(i);
            XMLUtils.dumpNode(child, childindention);
            ++i;
        }
    }

    private static void addInfo(StringBuffer sb, String name, String value) {
        if (value != null) {
            sb.append(name).append("='").append(value).append("';");
        }
    }

    public static String getNodeTypeText(int nodeType) {
        String nodeTypeText;
        if (nodeTypeMapping == null) {
            nodeTypeMapping = new HashMap();
            nodeTypeMapping.put(new Integer(2), "ATTRIBUTE_NODE");
            nodeTypeMapping.put(new Integer(4), "CDATA_SECTION_NODE");
            nodeTypeMapping.put(new Integer(8), "COMMENT_NODE");
            nodeTypeMapping.put(new Integer(11), "DOCUMENT_FRAGMENT_NODE");
            nodeTypeMapping.put(new Integer(9), "DOCUMENT_NODE");
            nodeTypeMapping.put(new Integer(10), "DOCUMENT_TYPE_NODE");
            nodeTypeMapping.put(new Integer(1), "ELEMENT_NODE");
            nodeTypeMapping.put(new Integer(6), "ENTITY_NODE");
            nodeTypeMapping.put(new Integer(5), "ENTITY_REFERENCE_NODE");
            nodeTypeMapping.put(new Integer(12), "NOTATION_NODE");
            nodeTypeMapping.put(new Integer(7), "PROCESSING_INSTRUCTION_NODE");
            nodeTypeMapping.put(new Integer(3), "TEXT_NODE");
        }
        if ((nodeTypeText = (String)nodeTypeMapping.get(new Integer(nodeType))) == null) {
            nodeTypeText = "UNKNOWN_TYPE_" + Integer.toString(nodeType) + "_NODE";
        }
        return nodeTypeText;
    }

    public static String getNodeInfo(Node n) {
        StringBuffer nodeInfo = new StringBuffer();
        if (n == null) {
            nodeInfo.append("null");
        } else {
            XMLUtils.addInfo(nodeInfo, "nodeType", XMLUtils.getNodeTypeText(n.getNodeType()));
            XMLUtils.addInfo(nodeInfo, "localName", n.getLocalName());
            XMLUtils.addInfo(nodeInfo, "namespaceURI", n.getNamespaceURI());
            XMLUtils.addInfo(nodeInfo, "nodeName", n.getNodeName());
            XMLUtils.addInfo(nodeInfo, "nodeValue", n.getNodeValue());
            XMLUtils.addInfo(nodeInfo, "prefix", n.getPrefix());
        }
        return nodeInfo.toString();
    }

    public static Document parseXML(String xml) throws ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(new StringReader(xml));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputSource);
        return doc;
    }

    public static Document parseXML_nsaware(String xml) throws ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(new StringReader(xml));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc_nsaware = builder.parse(inputSource);
        return doc_nsaware;
    }
}

