/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
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
 *//*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
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
package org.geotoolkit.ignrm.parser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.geotoolkit.ignrm.IGNRMClient;
import org.geotoolkit.ignrm.Token;
import org.geotoolkit.xml.StaxStreamReader;

/**
 * Stax parser to read a token from a GetToken request.
 * 
 * @author Johann Sorel (Geomatys)
 * @module
 */
public class TokenParser extends StaxStreamReader {

    private static final String TAG_TOKEN = "token";
    private static final String ATT_NAME = "name";

    private final IGNRMClient server;
    private final String key;

    public TokenParser(final IGNRMClient server, final String key) {
        this.server = server;
        this.key = key;
    }
    
    public Token read() throws XMLStreamException {
        while (reader.hasNext()) {
            final int type = reader.next();
            if (type == XMLStreamReader.START_ELEMENT
                    && reader.getLocalName().equalsIgnoreCase(TAG_TOKEN)) {
                return readToken();
            }
        }

        throw new XMLStreamException("token tag not found");
    }

    private Token readToken() throws XMLStreamException {

        final String name = reader.getAttributeValue(null, ATT_NAME);
        String value = reader.getElementText();
        value = value.trim();
        value = value.replaceAll("\n", "");
        return new Token(server,key,name,value);
    }
    
}
