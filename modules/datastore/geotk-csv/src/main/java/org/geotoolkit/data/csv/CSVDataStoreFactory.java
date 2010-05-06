/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2009, Geomatys
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

package org.geotoolkit.data.csv;

import java.io.File;
import java.net.URL;
import java.util.Set;
import org.geotoolkit.data.AbstractFileDataStoreFactory;
import org.geotoolkit.data.DataStore;
import org.geotoolkit.parameter.DefaultParameterDescriptor;
import org.geotoolkit.parameter.DefaultParameterDescriptorGroup;
import org.geotoolkit.storage.DataStoreException;
import org.opengis.feature.type.Name;
import org.opengis.parameter.GeneralParameterDescriptor;
import org.opengis.parameter.ParameterDescriptorGroup;
import org.opengis.parameter.ParameterValueGroup;

/**
 * CSV datastore factory.
 *
 * @author Johann Sorel (Geomatys)
 * @module pending
 */
public class CSVDataStoreFactory extends AbstractFileDataStoreFactory {

    /**
     * Optional - the separator character
     */
    public static final GeneralParameterDescriptor SEPARATOR =
            new DefaultParameterDescriptor("separator","spécify the separator",Character.class,';',false);

    public static final ParameterDescriptorGroup PARAMETERS_DESCRIPTOR =
            new DefaultParameterDescriptorGroup("CSVParameters",
                new GeneralParameterDescriptor[]{URLP,NAMESPACE,SEPARATOR});

    @Override
    public String getDescription() {
        return "CSV files (*.csv)";
    }

    @Override
    public ParameterDescriptorGroup getParametersDescriptor() {
        return PARAMETERS_DESCRIPTOR;
    }

    @Override
    public DataStore createDataStore(ParameterValueGroup params) throws DataStoreException {
        final URL url = (URL) params.parameter(URLP.getName().toString()).getValue();
        String namespace = (String) params.parameter(NAMESPACE.getName().toString()).getValue();
        final char separator = (Character) params.parameter(SEPARATOR.getName().toString()).getValue();
        
        if(namespace == null){
            namespace = "http://geotoolkit.org";
        }
        
        final String path = url.toString();
        final int slash = Math.max(0, path.lastIndexOf('/') + 1);
        int dot = path.indexOf('.', slash);
        if (dot < 0) {
            dot = path.length();
        }
        final String name = path.substring(slash, dot);
        
        return new CSVDataStore(new File(url.toString()), namespace, name, separator);
    }

    @Override
    public DataStore createNewDataStore(ParameterValueGroup params) throws DataStoreException {
        return createDataStore(params);
    }

    @Override
    public String[] getFileExtensions() {
        return new String[] {".csv"};
    }

    @Override
    public String getTypeName(URL url) throws DataStoreException {
        final DataStore ds = createDataStore(url);
        final Set<Name> names = ds.getNames(); // should be exactly one
        return (names.isEmpty()) ? null : names.iterator().next().getLocalPart();
    }


}
