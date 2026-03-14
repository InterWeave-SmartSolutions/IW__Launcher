/*
 * Decompiled with CFR.
 */
package com.altova.xml;

import com.altova.xml.Document;
import com.altova.xml.XmlException;
import java.io.Serializable;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public abstract class Node
implements Serializable {
    protected static final short Attribute = 0;
    protected static final short Element = 1;
    protected org.w3c.dom.Node domNode = null;

    protected static String getDomNodeValue(org.w3c.dom.Node node) {
        if (node == null) {
            return null;
        }
        String value = node.getNodeValue();
        if (value != null) {
            return value;
        }
        org.w3c.dom.Node child = node.getFirstChild();
        if (child != null && child.getNodeType() == 3) {
            return child.getNodeValue();
        }
        return "";
    }

    protected static void setDomNodeValue(org.w3c.dom.Node node, String value) {
        if (node == null) {
            return;
        }
        if (node.getNodeValue() != null) {
            node.setNodeValue(value);
            return;
        }
        org.w3c.dom.Node child = node.getFirstChild();
        if (child == null || child.getNodeType() != 3) {
            node.appendChild(node.getOwnerDocument().createTextNode(value));
            return;
        }
    }

    public Node() {
        this.domNode = Document.createTemporaryDomNode();
    }

    public Node(Node node) {
        this.domNode = node.domNode;
    }

    public Node(org.w3c.dom.Node domNode) {
        this.domNode = domNode;
    }

    public Node(org.w3c.dom.Document domDocument) {
        this.domNode = domDocument.getDocumentElement();
    }

    public org.w3c.dom.Node getDomNode() {
        return this.domNode;
    }

    public void mapPrefix(String prefix, String URI2) {
        if (URI2 == null || URI2.equals("")) {
            return;
        }
        Element element = (Element)this.domNode;
        if (prefix == null || prefix.equals("")) {
            element.setAttribute("xmlns", URI2);
        } else {
            element.setAttribute("xmlns:" + prefix, URI2);
        }
    }

    public void assign(Node other) {
        Node.setDomNodeValue(this.domNode, Node.getDomNodeValue(other.domNode).toString());
    }

    protected void declareNamespace(String prefix, String URI2) {
        if (URI2 == null || URI2.equals("")) {
            return;
        }
        Element root = this.domNode.getOwnerDocument().getDocumentElement();
        NamedNodeMap attrs = root.getAttributes();
        if (attrs != null) {
            int i = 0;
            while (i < attrs.getLength()) {
                Attr attr = (Attr)attrs.item(i);
                if (attr.getValue().equals(URI2)) {
                    return;
                }
                ++i;
            }
        }
        if (prefix == null || prefix.equals("")) {
            root.setAttribute("xmlns", URI2);
        } else {
            root.setAttribute("xmlns:" + prefix, URI2);
        }
    }

    protected org.w3c.dom.Node appendDomChild(int type, String namespaceURI, String name, String value) {
        switch (type) {
            case 0: {
                Attr attribute = this.domNode.getOwnerDocument().createAttributeNS(namespaceURI, name);
                attribute.setNodeValue(value);
                this.domNode.getAttributes().setNamedItemNS(attribute);
                return attribute;
            }
            case 1: {
                Element element = this.domNode.getOwnerDocument().createElementNS(namespaceURI, name);
                if (value != null && !value.equals("")) {
                    element.appendChild(this.domNode.getOwnerDocument().createTextNode(value));
                }
                this.domNode.appendChild(element);
                return element;
            }
        }
        throw new XmlException("Unknown type");
    }

    protected boolean domNodeNameEquals(org.w3c.dom.Node node, String namespaceURI, String localName) {
        String nodeLocalName;
        if (node == null) {
            return false;
        }
        String nodeURI = node.getNamespaceURI() == null ? "" : node.getNamespaceURI();
        String string = nodeLocalName = node.getLocalName() == null ? "" : node.getLocalName();
        if (namespaceURI == null) {
            namespaceURI = "";
        }
        if (localName == null) {
            localName = "";
        }
        return nodeURI.equals(namespaceURI) && nodeLocalName.equals(localName);
    }

    protected int getDomChildCount(int type, String namespaceURI, String name) {
        switch (type) {
            case 0: {
                try {
                    return ((Element)this.domNode).hasAttributeNS(namespaceURI, name) ? 1 : 0;
                }
                catch (Exception e) {
                    return 0;
                }
            }
            case 1: {
                NodeList elements = this.domNode.getChildNodes();
                int length = elements.getLength();
                int count = 0;
                int i = 0;
                while (i < length) {
                    org.w3c.dom.Node child = elements.item(i);
                    if (this.domNodeNameEquals(child, namespaceURI, name)) {
                        ++count;
                    }
                    ++i;
                }
                return count;
            }
        }
        throw new XmlException("Unknown type");
    }

    protected boolean hasDomChild(int type, String namespaceURI, String name) {
        switch (type) {
            case 0: {
                return ((Element)this.domNode).hasAttributeNS(namespaceURI, name);
            }
            case 1: {
                NodeList elements = this.domNode.getChildNodes();
                int length = elements.getLength();
                int i = 0;
                while (i < length) {
                    if (this.domNodeNameEquals(elements.item(i), namespaceURI, name)) {
                        return true;
                    }
                    ++i;
                }
                return false;
            }
        }
        throw new XmlException("Unknown type");
    }

    protected org.w3c.dom.Node getDomChildAt(int type, String namespaceURI, String name, int index) {
        int count = 0;
        switch (type) {
            case 0: {
                return this.domNode.getAttributes().getNamedItemNS(namespaceURI, name);
            }
            case 1: {
                NodeList elements = this.domNode.getChildNodes();
                int length = elements.getLength();
                int i = 0;
                while (i < length) {
                    org.w3c.dom.Node child = elements.item(i);
                    if (this.domNodeNameEquals(child, namespaceURI, name) && count++ == index) {
                        return child;
                    }
                    ++i;
                }
                throw new XmlException("Index out of range");
            }
        }
        throw new XmlException("Unknown type");
    }

    protected org.w3c.dom.Node getDomChild(int type, String namespaceURI, String name) {
        return this.getDomChildAt(type, namespaceURI, name, 0);
    }

    protected org.w3c.dom.Node insertDomChildAt(int type, String namespaceURI, String name, int index, String value) {
        if (type == 0) {
            return this.appendDomChild(type, namespaceURI, name, value);
        }
        Element element = this.domNode.getOwnerDocument().createElementNS(namespaceURI, name);
        element.appendChild(this.domNode.getOwnerDocument().createTextNode(value));
        return this.domNode.insertBefore(element, this.getDomChildAt(1, namespaceURI, name, index));
    }

    protected org.w3c.dom.Node insertDomElementAt(String namespaceURI, String name, int index, Node srcNode) {
        srcNode.domNode = this.domNode.insertBefore(this.cloneDomElementAs(namespaceURI, name, srcNode), this.getDomChildAt(1, namespaceURI, name, index));
        return srcNode.domNode;
    }

    protected org.w3c.dom.Node replaceDomChildAt(int type, String namespaceURI, String name, int index, String value) {
        if (type == 0) {
            return this.appendDomChild(type, namespaceURI, name, value);
        }
        Element element = this.domNode.getOwnerDocument().createElementNS(namespaceURI, name);
        element.appendChild(this.domNode.getOwnerDocument().createTextNode(value));
        return this.domNode.replaceChild(element, this.getDomChildAt(1, namespaceURI, name, index));
    }

    protected org.w3c.dom.Node replaceDomElementAt(String namespaceURI, String name, int index, Node srcNode) {
        srcNode.domNode = this.domNode.replaceChild(this.cloneDomElementAs(namespaceURI, name, srcNode), this.getDomChildAt(1, namespaceURI, name, index));
        return srcNode.domNode;
    }

    protected org.w3c.dom.Node setDomChildAt(int type, String namespaceURI, String name, String value, int index) {
        int count = 0;
        switch (type) {
            case 0: {
                Attr oldAttr = ((Element)this.domNode).getAttributeNodeNS(namespaceURI, name);
                ((Element)this.domNode).setAttributeNS(namespaceURI, name, value);
                return oldAttr;
            }
            case 1: {
                NodeList elements = this.domNode.getChildNodes();
                int length = elements.getLength();
                int i = 0;
                while (i < length) {
                    org.w3c.dom.Node child = elements.item(i);
                    if (this.domNodeNameEquals(child, namespaceURI, name) && count++ == index) {
                        return child.replaceChild(child.getOwnerDocument().createTextNode(value), child.getFirstChild());
                    }
                    ++i;
                }
                throw new XmlException("Index out of range");
            }
        }
        throw new XmlException("Unknown type");
    }

    protected org.w3c.dom.Node setDomChild(int type, String namespaceURI, String name, String value) {
        if (type == 0 || this.getDomChildCount(type, namespaceURI, name) > 0) {
            return this.setDomChildAt(type, namespaceURI, name, value, 0);
        }
        this.appendDomChild(type, namespaceURI, name, value);
        return null;
    }

    protected org.w3c.dom.Node removeDomChildAt(int type, String namespaceURI, String name, int index) {
        int count = 0;
        switch (type) {
            case 0: {
                return this.domNode.getAttributes().removeNamedItemNS(namespaceURI, name);
            }
            case 1: {
                NodeList elements = this.domNode.getChildNodes();
                int length = elements.getLength();
                int i = 0;
                while (i < length) {
                    org.w3c.dom.Node child = elements.item(i);
                    if (this.domNodeNameEquals(child, namespaceURI, name) && count++ == index) {
                        return this.domNode.removeChild(child);
                    }
                    ++i;
                }
                throw new XmlException("Index out of range");
            }
        }
        throw new XmlException("Unknown type");
    }

    protected org.w3c.dom.Node appendDomElement(String namespaceURI, String name, Node srcNode) {
        srcNode.domNode = this.domNode.appendChild(this.cloneDomElementAs(namespaceURI, name, srcNode));
        return srcNode.domNode;
    }

    protected Element cloneDomElementAs(String namespaceURI, String name, Node srcNode) {
        Element newDomNode = this.domNode.getOwnerDocument().createElementNS(namespaceURI, name);
        Element srcDomNode = (Element)srcNode.domNode;
        org.w3c.dom.Document doc = newDomNode.getOwnerDocument();
        NodeList list = srcDomNode.getChildNodes();
        int length = list.getLength();
        int i = 0;
        while (i < length) {
            newDomNode.appendChild(doc.importNode(list.item(i), true));
            ++i;
        }
        NamedNodeMap srcAttributes = srcDomNode.getAttributes();
        NamedNodeMap newAttributes = newDomNode.getAttributes();
        length = srcAttributes.getLength();
        int i2 = 0;
        while (i2 < length) {
            newAttributes.setNamedItemNS((Attr)doc.importNode(srcAttributes.item(i2), false));
            ++i2;
        }
        return newDomNode;
    }

    protected void cloneInto(Element newDomNode) {
        while (this.domNode.getFirstChild() != null) {
            org.w3c.dom.Node n = newDomNode.getOwnerDocument().importNode(this.domNode.getFirstChild(), true);
            newDomNode.appendChild(n);
            this.domNode.removeChild(this.domNode.getFirstChild());
        }
        NamedNodeMap srcAttributes = ((Element)this.domNode).getAttributes();
        NamedNodeMap newAttributes = newDomNode.getAttributes();
        while (srcAttributes.getLength() > 0) {
            org.w3c.dom.Node n = srcAttributes.item(0);
            newAttributes.setNamedItem(newDomNode.getOwnerDocument().importNode(n, true));
            srcAttributes.removeNamedItem(n.getNodeName());
        }
        this.domNode = newDomNode;
    }

    protected static String lookupPrefix(org.w3c.dom.Node node, String URI2) {
        if (node == null || URI2 == null || URI2.equals("")) {
            return null;
        }
        if (node.getNodeType() == 1) {
            NamedNodeMap attrs = node.getAttributes();
            if (attrs != null) {
                int len = attrs.getLength();
                int i = 0;
                while (i < len) {
                    Attr attr = (Attr)attrs.item(i);
                    String name = attr.getName();
                    String value = attr.getValue();
                    if (value != null && value.equals(URI2)) {
                        if (name.startsWith("xmlns:")) {
                            return name.substring(6);
                        }
                        return null;
                    }
                    ++i;
                }
            }
            return Node.lookupPrefix(node.getParentNode(), URI2);
        }
        if (node.getNodeType() == 2) {
            return Node.lookupPrefix(((Attr)node).getOwnerElement(), URI2);
        }
        return null;
    }

    protected static void internalAdjustPrefix(org.w3c.dom.Node node, boolean qualified) {
        String prefix;
        if (node != null && qualified && (prefix = Node.lookupPrefix(node, node.getNamespaceURI())) != null) {
            node.setPrefix(prefix);
        }
    }

    public abstract void adjustPrefix();
}

