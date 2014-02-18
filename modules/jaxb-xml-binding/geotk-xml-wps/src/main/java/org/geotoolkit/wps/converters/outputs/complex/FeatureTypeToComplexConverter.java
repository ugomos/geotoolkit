/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 *    (C) 2011, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.wps.converters.outputs.complex;


import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.geotoolkit.feature.xml.XmlFeatureTypeWriter;
import org.geotoolkit.feature.xml.jaxb.JAXBFeatureTypeWriter;
import org.geotoolkit.util.converter.NonconvertibleObjectException;
import org.geotoolkit.wps.xml.v100.ComplexDataType;
import org.opengis.feature.type.FeatureType;



/**
 * Implementation of ObjectConverter to convert a FeatureType into a {@link ComplexDataType}.
 * 
 * @author Quentin Boileau (Geomatys).
 */
public final class FeatureTypeToComplexConverter extends AbstractComplexOutputConverter<FeatureType> {

    private static FeatureTypeToComplexConverter INSTANCE;

    private FeatureTypeToComplexConverter(){
    }

    public static synchronized FeatureTypeToComplexConverter getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FeatureTypeToComplexConverter();
        }
        return INSTANCE;
    } 
    
    @Override
    public Class<? super FeatureType> getSourceClass() {
        return FeatureType.class;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ComplexDataType convert(final FeatureType source, final Map<String, Object> params) throws NonconvertibleObjectException {
        
        
        if (source == null) {
            throw new NonconvertibleObjectException("The output data should be defined.");
        }
        if (!(source instanceof FeatureType)) {
            throw new NonconvertibleObjectException("The requested output data is not an instance of FeatureType.");
        }
        final ComplexDataType complex = new ComplexDataType();
        
        complex.setMimeType((String) params.get(MIME));
        complex.setEncoding((String) params.get(ENCODING));
        
        try {
            
            final XmlFeatureTypeWriter xmlWriter = new JAXBFeatureTypeWriter();
            complex.getContent().add(xmlWriter.writeToElement(source));
            
        } catch (JAXBException ex) {
            throw new NonconvertibleObjectException("Can't write FeatureType into ResponseDocument.",ex);
        } catch (ParserConfigurationException ex) {
            throw new NonconvertibleObjectException("Can't write FeatureType into ResponseDocument.",ex);
        }

       return  complex;
       
    }
}
