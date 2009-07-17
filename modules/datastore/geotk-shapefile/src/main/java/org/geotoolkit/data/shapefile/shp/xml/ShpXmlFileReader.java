/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotoolkit.data.shapefile.shp.xml;

import com.vividsolutions.jts.geom.Envelope;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.geotoolkit.data.shapefile.FileReader;
import org.geotoolkit.data.shapefile.ShpFileType;
import org.geotoolkit.data.shapefile.ShpFiles;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ShpXmlFileReader implements FileReader {

    Document dom;

    /**
     * Parse metadataFile (currently for bounding box information).
     * <p>
     * 
     * </p>
     * 
     * @param shapefileFiles
     * @throws JDOMException
     * @throws IOException
     */
    public ShpXmlFileReader(ShpFiles shapefileFiles) throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();

        InputStream inputStream = shapefileFiles.getInputStream(ShpFileType.SHP_XML, this);

        try {
            dom = constructeur.parse(inputStream);
        } finally {
            inputStream.close();
        }
    }

    public Metadata parse() {
        return parseMetadata(dom.getDocumentElement());
    }

    protected Metadata parseMetadata(Element root) {
        Metadata meta = new Metadata();
        meta.setIdinfo(parseIdInfo(getChild(root,"idinfo")));
        return meta;
    }

    protected IdInfo parseIdInfo(Element element) {
        IdInfo idInfo = new IdInfo();

        Element spdom = getChild(element, "spdom");

        Element bounding = getChild(spdom,"bounding");
        idInfo.setBounding(parseBounding(bounding));

        Element lbounding = getChild(spdom,"lbounding");
        idInfo.setLbounding(parseBounding(lbounding));

        return idInfo;
    }

    protected Envelope parseBounding(Element bounding) {
        if (bounding == null)
            return new Envelope();

        double minX = Double.parseDouble(getChildText(bounding,"westbc"));
        double maxX = Double.parseDouble(getChildText(bounding,"eastbc"));
        double minY = Double.parseDouble(getChildText(bounding,"southbc"));
        double maxY = Double.parseDouble(getChildText(bounding,"northbc"));

        return new Envelope(minX, maxX, minY, maxY);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String id() {
        return "Shp Xml Reader";
    }

    private static Element getChild(Element parent, String name){
        NodeList liste = parent.getElementsByTagName(name);
        if(liste.getLength() > 0){
            return (Element) liste.item(0);
        }
        return null;
    }

    private static String getChildText(Element parent, String name){
        NodeList liste = parent.getElementsByTagName(name);
        if(liste.getLength() > 0){
            return ((Element) liste.item(0)).getTextContent();
        }
        return null;
    }


}
