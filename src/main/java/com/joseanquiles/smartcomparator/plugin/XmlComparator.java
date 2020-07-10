package com.joseanquiles.smartcomparator.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.joseanquiles.smartcomparator.diff.DiffType;
import com.joseanquiles.smartcomparator.diff.Difference;

public class XmlComparator implements SmartComparatorPlugin {

    public List<Difference> compare(String leftFilename, String rightFilename) throws Exception {

        List<Difference> result = new ArrayList<Difference>();

        List<String> leftDoc = parseXmlFile(leftFilename);
        List<String> rightDoc = parseXmlFile(rightFilename);

        // find out lines in left doc not included in right doc
        for (int i = 0; i < leftDoc.size(); i++) {
            String leftLine = leftDoc.get(i);
            boolean found = false;
            for (int j = 0; j < rightDoc.size(); j++) {
                String rightLine = rightDoc.get(j);
                if (leftLine.equals(rightLine)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Difference diff = new Difference();
                diff.diffType = DiffType.LEFT;
                diff.description = leftLine;
                result.add(diff);
            }
        }

        // find out lines in right doc not included in left doc
        for (int i = 0; i < rightDoc.size(); i++) {
            String rightLine = rightDoc.get(i);
            boolean found = false;
            for (int j = 0; j < leftDoc.size(); j++) {
                String leftLine = leftDoc.get(j);
                if (rightLine.equals(leftLine)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Difference diff = new Difference();
                diff.diffType = DiffType.RIGHT;
                diff.description = rightLine;
                result.add(diff);
            }
        }

        return result;
    }

    private List<String> parseXmlFile(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));
        document.getDocumentElement().normalize();

        List<String> elements = new ArrayList<String>();

        Element root = document.getDocumentElement();

        parseElement(elements, root, "");

        return elements;
    }

    private void parseElement(List<String> result, Element element, String currentStr) {
        // node name
        currentStr = currentStr + "." + element.getNodeName();
        // attributes
        NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            result.add(currentStr + "[" + attrName + "]=" + attrValue);
        }
        // children elements
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node childNode = children.item(i);
            if (childNode.getNodeType() == Node.TEXT_NODE) {
                String textContent = childNode.getNodeValue();
                if (textContent != null && textContent.trim().length() > 0) {
                    textContent = textContent.trim();
                    result.add(currentStr + "=" + textContent);
                }
            }
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                parseElement(result, childElement, currentStr);
            }
        }

    }

}
