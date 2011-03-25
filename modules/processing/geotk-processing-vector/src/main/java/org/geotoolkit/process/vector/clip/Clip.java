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
 */
package org.geotoolkit.process.vector.clip;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

import java.util.ArrayList;
import java.util.List;

import org.geotoolkit.data.FeatureCollection;
import org.geotoolkit.data.FeatureIterator;
import org.geotoolkit.feature.FeatureUtilities;
import org.geotoolkit.parameter.Parameters;
import org.geotoolkit.process.AbstractProcess;
import org.geotoolkit.process.vector.VectorDescriptor;
import org.geotoolkit.process.vector.VectorProcessUtils;
import org.geotoolkit.process.vector.clipgeometry.ClipGeometry;

import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.FeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.parameter.ParameterValueGroup;

/**
 * Process to clip a FeatureCollection using another FeatureCollection
 * @author Quentin Boileau
 * @module pending
 */
public class Clip extends AbstractProcess {
    
    private static final GeometryFactory GF = new GeometryFactory();
    ParameterValueGroup result;

    /**
     * Default constructor
     */
    public Clip() {
        super(ClipDescriptor.INSTANCE);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ParameterValueGroup getOutput() {
        return result;
    }

    /**
     *  {@inheritDoc }
     */
    @Override
    public void run() {
        final FeatureCollection<Feature> inputFeatureList = Parameters.value(ClipDescriptor.FEATURE_IN, inputParameters);
        final FeatureCollection<Feature> inputFeatureClippingList = Parameters.value(ClipDescriptor.FEATURE_CLIP, inputParameters);

        final ClipFeatureCollection resultFeatureList = new ClipFeatureCollection(inputFeatureList,inputFeatureClippingList);

        result = super.getOutput();
        result.parameter(VectorDescriptor.FEATURE_OUT.getName().getCode()).setValue(resultFeatureList);
    }

    /**
     * Clip a feature with the FeatureCollection's geometries
     * @param oldFeature Feature
     * @param newType the new FeatureType for the Feature
     * @param featureClippingList FeatureCollection used to clip
     * @return Feature
     */
    public static Feature clipFeature(final Feature oldFeature, final FeatureType newType, final FeatureCollection<Feature> featureClippingList) {

        final Feature resultFeature = FeatureUtilities.defaultFeature(newType, oldFeature.getIdentifier().getID());

        for (Property property : oldFeature.getProperties()) {
            
            //for each Geometry in the oldFeature
            if (property.getDescriptor() instanceof GeometryDescriptor) {

                //loop and test intersection between each geometry of each clipping feature from
                //clipping FeatureCollection
                final List<Geometry> bufferInterGeometries = new ArrayList<Geometry>();
                final FeatureIterator<Feature> clipIterator = featureClippingList.iterator();
                try{
                    while(clipIterator.hasNext()){
                        final Feature clipFeature = clipIterator.next();
                        for (Property clipFeatureProperty : clipFeature.getProperties()) {
                            if (clipFeatureProperty.getDescriptor() instanceof GeometryDescriptor) {
                                final Geometry interGeometry = VectorProcessUtils.testClipping((Geometry) property.getValue(),
                                                                            (Geometry) clipFeatureProperty.getValue());

                                //if an intersection geometry exist, store it into a buffer Collection
                                if (interGeometry != null) {
                                    bufferInterGeometries.add(interGeometry);
                                }                                 
                            }
                        }
                    }  
                }
                finally{
                    clipIterator.close();
                }
               
                //if the feature intersect one of the feature clipping list
                final int size = bufferInterGeometries.size();

                if (size == 1) {
                    resultFeature.getProperty(property.getName()).setValue(bufferInterGeometries.get(0));
                }else if (size > 1) {
                    final Geometry[] bufferArray = bufferInterGeometries.toArray(new Geometry[bufferInterGeometries.size()]);
                    
                    //create a GeometryCollection with all the intersections
                    final GeometryCollection resultGeometry = GF.createGeometryCollection(bufferArray);

                    resultFeature.getProperty(property.getName()).setValue(resultGeometry);
                } else {
                    return null;
                }
            } else {
                //others properties (no geometry)
                resultFeature.getProperty(property.getName()).setValue(property.getValue());
            }
        }
        return resultFeature;
    }

}
