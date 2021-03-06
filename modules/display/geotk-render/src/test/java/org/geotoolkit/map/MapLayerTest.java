/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2010, Geomatys
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

package org.geotoolkit.map;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.sis.feature.builder.FeatureTypeBuilder;

import org.geotoolkit.data.FeatureStore;
import org.apache.sis.storage.DataStoreException;
import org.geotoolkit.data.FeatureCollection;
import org.geotoolkit.data.memory.MemoryFeatureStore;
import org.geotoolkit.data.query.Query;
import org.geotoolkit.data.query.QueryBuilder;
import org.geotoolkit.data.query.QueryUtilities;
import org.geotoolkit.util.NamesExt;
import org.geotoolkit.style.DefaultStyleFactory;
import org.geotoolkit.style.MutableStyle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opengis.feature.FeatureType;

import org.opengis.util.GenericName;
import org.opengis.filter.Filter;

/**
 *
 * @author Johann Sorel (Geomatys)
 * @module
 */
public class MapLayerTest extends TestCase{

    public MapLayerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCollectionLayer() {

        try{
            MapBuilder.createFeatureLayer(null, null);
            fail("Creating collection maplayer with null style and source should raise an error");
        }catch(Exception ex){
            //ok
        }

        final MutableStyle style = new DefaultStyleFactory().style();

        try{
            MapBuilder.createFeatureLayer(null, style);
            fail("Creating collection maplayer with null source should raise an error");
        }catch(Exception ex){
            //ok
        }

        final List<String> collection = new ArrayList<String>();

        final CollectionMapLayer layer = MapBuilder.createCollectionLayer(collection, style);
        assertNotNull(layer);

        assertEquals(layer.getCollection(), collection);

    }

    @Test
    public void testFeatureLayer() throws DataStoreException {

        try{
            MapBuilder.createFeatureLayer(null, null);
            fail("Creating maplayer with null source should raise an error");
        }catch(Exception ex){
            //ok
        }

        final GenericName name = NamesExt.create("test");
        FeatureTypeBuilder builder = new FeatureTypeBuilder();
        builder.setName(name);
        FeatureType type = builder.build();

        FeatureStore ds = new MemoryFeatureStore();
        ds.createFeatureType(type);
        FeatureCollection fs = ds.createSession(true).getFeatureCollection(QueryBuilder.all(name));


        FeatureMapLayer layer = MapBuilder.createFeatureLayer(fs, new DefaultStyleFactory().style());
        assertNotNull(layer);

        Query query = layer.getQuery();
        assertNotNull(query);
        assertTrue( QueryUtilities.queryAll(query) );

        try{
            layer.setQuery(null);
            throw new IllegalArgumentException("Can not set a null query");
        }catch(Exception ex){
            //ok
        }

        try{
            layer.setQuery(QueryBuilder.filtered(fs.getFeatureType().getName().toString(), Filter.EXCLUDE));
        }catch(Exception ex){
            throw new IllegalArgumentException("Should be able to set this query");
        }

    }


}
