/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2011, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.test.xml;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.net.URI;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import static org.geotoolkit.test.Assert.*;


/**
 * Compares the XML document produced by a test method with the expected XML document.
 * The two XML documents are specified at construction time. The comparison is performed
 * by a call to the {@link #compare()} method. The execution is delegated to the various
 * protected methods defined in this class, which can be overridden.
 * <p>
 * By default, this comparator expects the documents to contain the same elements and
 * the same attributes (but the order of attributes may be different). However it is
 * possible to:
 * <p>
 * <ul>
 *   <li>Specify attributes to ignore in comparisons (see {@link #ignoredAttributes})</li>
 * </ul>
 *
 * @author Johann Sorel (Geomatys)
 * @author Martin Desruisseaux (Geomatys)
 * @version 3.17
 *
 * @since 3.17
 */
public class DomComparator {
    /**
     * The expected document.
     */
    private final Node expectedDoc;

    /**
     * The document resulting from the test method.
     */
    private final Node actualDoc;

    /**
     * {@code true} if the comments shall be ignored. The default value is {@code false}.
     */
    public boolean ignoreComments;

    /**
     * The fully-qualified name of attributes to ignore in comparisons. The name shall be in
     * the form {@code "namespace:name"}, or only {@code "name"} if there is no namespace. In
     * order to ignore everything in a namespace, use {@code "namespace:*"}.
     * <p>
     * For example in order to ignore the namespace, type and schema location declaration,
     * the following strings can be added in this set:
     *
     * {@preformat text
     *   "xmlns:*", "xsi:schemaLocation", "xsi:type"
     * }
     *
     * This set is initially empty. Users can add or remove elements in this set as they wish.
     * The content of this set will be honored by the default {@link #compareAttributes(Node, Node)}
     * implementation.
     */
    public final Set<String> ignoredAttributes;

    /**
     * The fully-qualified name of nodes to ignore in comparisons. The name shall be in the form
     * {@code "namespace:name"}, or only {@code "name"} if there is no namespace. In order to
     * ignore everything in a namespace, use {@code "namespace:*"}.
     * <p>
     * This set is initially empty. Users can add or remove elements in this set as they wish.
     * The content of this set will be honored by the default {@link #compareChildren(Node, Node)}
     * implementation.
     */
    public final Set<String> ignoredNodes;

    /**
     * Creates a new comparator for the given root nodes.
     *
     * @param expected The root node of the expected XML document.
     * @param actual   The root node of the XML document to compare.
     */
    public DomComparator(final Node expected, final Node actual) {
        assertNotNull("A non-null 'expected' node shall be specified.", expected);
        assertNotNull("A non-null 'actual' node shall be specified.", actual);
        expectedDoc = expected;
        actualDoc   = actual;
        ignoredAttributes = new HashSet<String>();
        ignoredNodes = new HashSet<String>();
    }

    /**
     * Creates a new comparator for the given inputs. The inputs can be any of the
     * following types:
     * <p>
     * <ul>
     *   <li>{@link Node}; used directly without further processing.</li>
     *   <li>{@link File}, {@link URL} or {@link URI}: the stream is opened and parsed
     *       as a XML document.</li>
     *   <li>{@link String}: The string content is parsed directly as a XML document.
     *       Encoding <strong>must</strong> be UTF-8 (no other encoding is supported
     *       by current implementation of this method).</li>
     * </ul>
     *
     * @param  expected  The expected XML document.
     * @param  actual    The XML document to compare.
     * @throws IOException If the stream can not be read.
     * @throws ParserConfigurationException If a {@link DocumentBuilder} can not be created.
     * @throws SAXException If an error occurred while parsing the XML document.
     */
    public DomComparator(final Object expected, final Object actual)
            throws IOException, ParserConfigurationException, SAXException
    {
        this((expected instanceof Node) ? (Node) expected : read(expected),
               (actual instanceof Node) ? (Node) actual   : read(actual));
    }

    /**
     * Convenience method to acquire a DOM document from an input. This convenience method
     * uses the default JRE classes, so it may not be the fastest parsing method.
     */
    private static Document read(final Object input)
            throws IOException, ParserConfigurationException, SAXException
    {
        final InputStream stream = toInputStream(input);
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder constructeur = factory.newDocumentBuilder();
        final Document document = constructeur.parse(stream);
        stream.close();
        return document;
    }

    /**
     * Converts the given object to a stream.
     * See the constructor javadoc for the list of allowed input type.
     */
    private static InputStream toInputStream(final Object input) throws IOException {
        if (input instanceof InputStream) {
            return (InputStream) input;
        } else if (input instanceof File) {
            return new FileInputStream((File) input);
        } else if (input instanceof URI) {
            return ((URI) input).toURL().openStream();
        } else if (input instanceof URL) {
            return ((URL) input).openStream();
        } else if (input instanceof String) {
            return new ByteArrayInputStream(input.toString().getBytes("UTF-8"));
        } else {
            throw new IOException("Can not handle input type: " + (input != null ? input.getClass() : input));
        }
    }

    /**
     * Compares the XML document specified at construction time. Before to invoke this
     * method, users may consider to add some values to the {@link #ignoredAttributes}
     * set.
     */
    public void compare() {
        compareNode(expectedDoc, actualDoc);
    }

    /**
     * Compares the two given nodes. This method delegates to one of the given methods depending
     * on the expected node type:
     * <p>
     * <ul>
     *   <li>{@link #compareCDATASectionNode(CDATASection, Node)}</li>
     *   <li>{@link #compareTextNode(Text, Node)}</li>
     *   <li>{@link #compareCommentNode(Comment, Node)}</li>
     *   <li>{@link #compareProcessingInstructionNode(ProcessingInstruction, Node)}</li>
     *   <li>For all other types, {@link #compareNames(Node, Node)} and
     *       {@link #compareAttributes(Node, Node)}</li>
     * </ul>
     * <p>
     * Then this method invokes itself recursively for every children,
     * by a call to {@link #compareChildren(Node, Node)}.
     *
     * @param expected The expected node.
     * @param actual The node to compare.
     */
    protected void compareNode(final Node expected, final Node actual) {
        if (expected == null || actual == null) {
            fail(formatErrorMessage(expected, actual));
        }
        /*
         * Check text value for types:
         * TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE, PROCESSING_INSTRUCTION_NODE
         */
        if (expected instanceof CDATASection) {
            compareCDATASectionNode((CDATASection) expected, actual);
        } else if (expected instanceof Text) {
            compareTextNode((Text) expected, actual);
        } else if (expected instanceof Comment) {
            compareCommentNode((Comment) expected, actual);
        } else if (expected instanceof ProcessingInstruction) {
            compareProcessingInstructionNode((ProcessingInstruction) expected, actual);
        } else if (expected instanceof Attr) {
            compareAttributeNode((Attr) expected, actual);
        } else {
            compareNames(expected, actual);
            compareAttributes(expected, actual);
        }
        /*
         * Check child nodes recursivly if it's not an attribut.
         */
        if (expected.getNodeType() != Node.ATTRIBUTE_NODE) {
            compareChildren(expected, actual);
        }
    }

    /**
     * Compares a node which is expected to be of {@link Text} type. The default implementation
     * ensures that the given node is an instance of {@link Text}, then ensures that both nodes
     * have the same names, attributes and text content.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual   The actual node.
     */
    protected void compareTextNode(final Text expected, final Node actual) {
        assertInstanceOf("Actual node is not of the expected type.", Text.class, actual);
        compareNames(expected, actual);
        compareAttributes(expected, actual);
        assertTextContentEquals(expected, actual);
    }

    /**
     * Compares a node which is expected to be of {@link CDATASection} type. The default
     * implementation ensures that the given node is an instance of {@link CDATASection},
     * then ensures that both nodes have the same names, attributes and text content.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual   The actual node.
     */
    protected void compareCDATASectionNode(final CDATASection expected, final Node actual) {
        assertInstanceOf("Actual node is not of the expected type.", CDATASection.class, actual);
        compareNames(expected, actual);
        compareAttributes(expected, actual);
        assertTextContentEquals(expected, actual);
    }

    /**
     * Compares a node which is expected to be of {@link Comment} type. The default
     * implementation ensures that the given node is an instance of {@link Comment},
     * then ensures that both nodes have the same names, attributes and text content.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual   The actual node.
     */
    protected void compareCommentNode(final Comment expected, final Node actual) {
        assertInstanceOf("Actual node is not of the expected type.", Comment.class, actual);
        compareNames(expected, actual);
        compareAttributes(expected, actual);
        assertTextContentEquals(expected, actual);
    }

    /**
     * Compares a node which is expected to be of {@link ProcessingInstruction} type. The default
     * implementation ensures that the given node is an instance of {@link ProcessingInstruction},
     * then ensures that both nodes have the same names, attributes and text content.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual   The actual node.
     */
    protected void compareProcessingInstructionNode(final ProcessingInstruction expected, final Node actual) {
        assertInstanceOf("Actual node is not of the expected type.", ProcessingInstruction.class, actual);
        compareNames(expected, actual);
        compareAttributes(expected, actual);
        assertTextContentEquals(expected, actual);
    }

    /**
     * Compares a node which is expected to be of {@link Attr} type. The default
     * implementation ensures that the given node is an instance of {@link Attr},
     * then ensures that both nodes have the same names and text content.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual   The actual node.
     */
    protected void compareAttributeNode(final Attr expected, final Node actual) {
        assertInstanceOf("Actual node is not of the expected type.", Attr.class, actual);
        compareNames(expected, actual);
        compareAttributes(expected, actual);
        assertTextContentEquals(expected, actual);
    }

    /**
     * Compares the children of the given nodes. The node themselves are not compared.
     * Children shall appear in the same order. Nodes having a name declared in the
     * {@link #ignoredNodes} set are ignored.
     * <p>
     * Subclasses can override this method if they need a different processing.
     *
     * @param expected The expected node.
     * @param actual The node for which to compare children.
     */
    protected void compareChildren(Node expected, Node actual) {
        expected = firstNonEmptySibling(expected.getFirstChild());
        actual   = firstNonEmptySibling(actual  .getFirstChild());
        while (expected != null) {
            compareNode(expected, actual);
            expected = firstNonEmptySibling(expected.getNextSibling());
            actual   = firstNonEmptySibling(actual  .getNextSibling());
        }
        if (actual != null) {
            fail(formatErrorMessage(expected, actual));
        }
    }

    /**
     * Compares the names and namespaces of the given node.
     * Subclasses can override this method if they need a different comparison.
     *
     * @param expected The node having the expected name and namespace.
     * @param actual The node to compare.
     */
    protected void compareNames(final Node expected, final Node actual) {
        assertPropertyEquals("namespace", expected.getNamespaceURI(), actual.getNamespaceURI(), expected, actual);
        assertPropertyEquals("name",      expected.getNodeName(),     actual.getNodeName(),     expected, actual);
    }

    /**
     * Compares the attributes of the given nodes.
     * Subclasses can override this method if they need a different comparison.
     * <p>
     * <strong>NOTE:</strong> Current implementation requires the number of attributes to be the
     * same only if the {@link #ignoredAttributes} set is empty. If the {@code ignoredAttributes}
     * set is not empty, then the actual node could have more attributes than the expected node;
     * the extra attributes are ignored. This may change in a future version if it appears to be
     * a problem in practice.
     *
     * @param expected The node having the expected attributes.
     * @param actual The node to compare.
     */
    protected void compareAttributes(final Node expected, final Node actual) {
        final NamedNodeMap expectedAttributes = expected.getAttributes();
        final NamedNodeMap actualAttributes   = actual.getAttributes();
        final int n = (expectedAttributes != null) ? expectedAttributes.getLength() : 0;
        if (ignoredAttributes.isEmpty()) {
            assertPropertyEquals("nbAttributes", n,
                    (actualAttributes != null) ? actualAttributes.getLength() : 0, expected, actual);
        }
        for (int i=0; i<n; i++) {
            final Node expAttr = expectedAttributes.item(i);
            final String ns    = expAttr.getNamespaceURI();
            final String name  = expAttr.getNodeName();
            if (!isIgnored(ignoredAttributes, ns, name)) {
                final Node actAttr;
                if (ns == null) {
                    actAttr = actualAttributes.getNamedItem(name);
                } else {
                    actAttr = actualAttributes.getNamedItemNS(ns, name);
                }
                compareNode(expAttr, actAttr);
            }
        }
    }

    /**
     * Returns {@code true} if the given node or attribute shall be ignored.
     *
     * @param ignored The set of node or attribute fully qualified names to ignore.
     * @param ns      The node or attribute namespace, or {@code null}.
     * @param name    The node or attribute name.
     * @return        {@coce true} if the node or attribute shall be ignored.
     */
    private static boolean isIgnored(final Set<String> ignored, String ns, final String name) {
        if (!ignored.isEmpty()) {
            if (ignored.contains((ns != null) ? ns + ':' + name : name)) {
                // Ignore a specific node (for example "xsi:schemaLocation")
                return true;
            }
            if (ns == null) {
                final int s = name.indexOf(':');
                if (s >= 1) {
                    ns = name.substring(0, s);
                }
            }
            if (ns != null && ignored.contains(ns + ":*")) {
                // Ignore a full namespace (typically "xmlns:*")
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first sibling of the given node having a non-empty text content, or {@code null}
     * if none. This method first check the given node, then check all siblings. Attribute nodes are
     * ignored.
     *
     * @param  node The node to check, or {@code null}.
     * @return The first node having a non-empty text content, or {@code null} if none.
     */
    private Node firstNonEmptySibling(Node node) {
        for (; node != null; node = node.getNextSibling()) {
            if (!isIgnored(ignoredNodes, node.getNamespaceURI(), node.getNodeName())) {
                switch (node.getNodeType()) {
                    // For attribute node, continue the search unconditionally.
                    case Node.ATTRIBUTE_NODE: continue;

                    // For text node, continue the search if the node is empty.
                    case Node.TEXT_NODE: {
                        final String text = node.getTextContent();
                        if (text == null || text.trim().length() == 0) {
                            continue;
                        }
                        break;
                    }

                    // Ignore comment nodes only if requested.
                    case Node.COMMENT_NODE: {
                        if (ignoreComments) {
                            continue;
                        }
                        break;
                    }
                }
                // Found a node: stop the search.
                break;
            }
        }
        return node;
    }

    /**
     * Verifies that the text content of the given nodes are equal.
     */
    private void assertTextContentEquals(final Node expected, final Node actual) {
        assertPropertyEquals("textContent", expected.getTextContent(), actual.getTextContent(), expected, actual);
    }

    /**
     * Verifies that the given property (text or number) are equal, ignoring spaces. If they are
     * not equal, then an error message is formatted using the given property name and nodes.
     *
     * @param property     The property being compared (typically "name", "namespace", etc.).
     * @param expected     The property from the expected node to compare.
     * @param actual       The property to compare to the expected one.
     * @param expectedNode The node from which the expected property has been fetched.
     * @param actualNode   The node being compared to the expected node.
     */
    private void assertPropertyEquals(final String property, Comparable<?> expected, Comparable<?> actual,
            final Node expectedNode, final Node actualNode)
    {
        expected = trim(expected);
        actual   = trim(actual);
        if ((expected != actual) && (expected == null || !expected.equals(actual))) {
            final StringBuilder buffer = new StringBuilder(1024).append("Expected ")
                    .append(property).append(" \"")
                    .append(expected).append("\" but got \"")
                    .append(actual).append("\" for nodes:\n");
            formatErrorMessage(buffer, expectedNode, actualNode);
            fail(buffer.toString());
        }
    }

    /**
     * Trims the leading and trailing spaces in the given property
     * if it is actually a {@link String} object.
     */
    private static Comparable<?> trim(final Comparable<?> property) {
        return (property instanceof String) ? ((String) property).trim() : property;
    }

    /**
     * Formats an error message for a node mismatch. The message will contain a string
     * representation of the expected and actual node.
     *
     * @param expected The expected node.
     * @param result   The actual node.
     * @return         An error message containing the expected and actual node.
     */
    protected static String formatErrorMessage(final Node expected, final Node result) {
        final StringBuilder buffer = new StringBuilder(1024).append("Nodes are not equal:\n");
        formatErrorMessage(buffer, expected, result);
        return buffer.toString();
    }

    /**
     * Formats in the given buffer an error message for a node mismatch.
     */
    private static void formatErrorMessage(final StringBuilder buffer, final Node expected, final Node result) {
        formatNode(buffer.append("Expected node: "), expected);
        formatNode(buffer.append("Actual node:   "), result);

        buffer.append("Expected hierarchy:\n");
        final List<String> hierarchy = formatHierarchy(buffer, expected, null);
        buffer.append("Actual hierarchy:\n");
        formatHierarchy(buffer, result, hierarchy);
    }

    /**
     * Appends to the given buffer the string representation of the node hierarchy.
     * The first line will contains the root of the tree. Other lines will contain
     * the child down in the hierarchy until the given node, inclusive.
     * <p>
     * This method formats only a summary if the hierarchy is equals to the expected one.
     *
     * @param buffer   The buffer in which to append the formatted hierarchy.
     * @param node     The node for which to format the parents.
     * @param expected The expected hierarchy, or {@code null} if unknown.
     */
    private static List<String> formatHierarchy(final StringBuilder buffer, Node node,
            final List<String> expected)
    {
        final List<String> hierarchy = new ArrayList<String>();
        while (node != null) {
            hierarchy.add(node.getNodeName());
            node = node.getParentNode();
        }
        if (hierarchy.equals(expected)) {
            buffer.append("\u2514\u2500Same as expected\n");
        } else {
            int indent = 2;
            for (int i=hierarchy.size(); --i>=0;) {
                for (int j=indent; --j>=0;) {
                    buffer.append('\u00A0');
                }
                buffer.append("\u2514\u2500").append(hierarchy.get(i)).append('\n');
                indent += 4;
            }
        }
        return hierarchy;
    }

    /**
     * Appends to the given buffer a string representation of the given node.
     * The string representation is terminated by a line feed.
     *
     * @param buffer The buffer in which to append the formatted node.
     * @param node   The node to format.
     */
    private static void formatNode(final StringBuilder buffer, final Node node) {
        if (node == null) {
            buffer.append("(no node)\n");
            return;
        }
        // Format the text content, together with the text content of the
        // child if there is exactly one child.
        final String ns = node.getNamespaceURI();
        if (ns != null) {
            buffer.append(ns).append(':');
        }
        buffer.append(node.getNodeName());
        boolean hasText = appendTextContent(buffer, node);
        final NodeList children = node.getChildNodes();
        int numChildren = 0;
        if (children != null) {
            numChildren = children.getLength();
            if (numChildren == 1 && !hasText) {
                hasText = appendTextContent(buffer, children.item(0));
            }
        }

        // Format the number of children and the number of attributes, if any.
        String separator = " (";
        if (numChildren != 0) {
            buffer.append(separator).append("nbChild=").append(numChildren);
            separator = ", ";
        }
        final NamedNodeMap atts = node.getAttributes();
        int numAtts = 0;
        if (atts != null) {
            numAtts = atts.getLength();
            if (numAtts != 0) {
                buffer.append(separator).append("nbAtt=").append(numAtts);
                separator = ", ";
            }
        }
        if (!separator.equals(" (")) {
            buffer.append(')');
        }

        // Format all attributes, if any.
        separator = " [";
        for (int i=0; i<numAtts; i++) {
            buffer.append(separator).append(atts.item(i));
            separator = ", ";
        }
        if (!separator.equals(" [")) {
            buffer.append(']');
        }
        buffer.append('\n');
    }

    /**
     * Appends the text content of the given node only if the node is an instance of {@link Text}
     * or related type ({@link CDATASection}, {@link Comment} or {@link ProcessingInstruction}).
     * Otherwise this method does nothing.
     *
     * @param  buffer The buffer in which to append text content.
     * @param  node   The node for which to append text content.
     * @return {@code true} if a text has been formatted.
     */
    private static boolean appendTextContent(final StringBuilder buffer, final Node node) {
        if (node instanceof Text || node instanceof CDATASection ||
                node instanceof Comment || node instanceof ProcessingInstruction)
        {
            buffer.append("=\"").append(node.getTextContent()).append('"');
            return true;
        }
        return false;
    }
}
