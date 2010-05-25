/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.geotoolkit.data.kml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.geotoolkit.data.model.KmlFactory;
import org.geotoolkit.data.model.KmlFactoryDefault;
import org.geotoolkit.data.model.kml.AbstractFeature;
import org.geotoolkit.data.model.kml.AbstractGeometry;
import org.geotoolkit.data.model.kml.Coordinate;
import org.geotoolkit.data.model.kml.Coordinates;
import org.geotoolkit.data.model.kml.ExtendedData;
import org.geotoolkit.data.model.kml.IdAttributes;
import org.geotoolkit.data.model.kml.Kml;
import org.geotoolkit.data.model.kml.Region;
import org.geotoolkit.feature.DefaultComplexAttribute;
import org.geotoolkit.feature.DefaultFeature;
import org.geotoolkit.feature.DefaultName;
import org.geotoolkit.feature.DefaultProperty;
import org.geotoolkit.feature.FeatureTypeBuilder;
import org.geotoolkit.filter.identity.DefaultFeatureId;
import org.geotoolkit.util.StringUtilities;
import org.geotoolkit.util.Strings;
import org.geotoolkit.util.XInteger;
import org.opengis.feature.ComplexAttribute;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.ComplexType;
import org.opengis.feature.type.FeatureType;
import org.opengis.feature.type.FeatureTypeFactory;

/**
 *
 * @author Samuel Andrés
 */
public class App {

    public static void featuresTests(){
        final FeatureTypeBuilder ftb = new FeatureTypeBuilder();
        final FeatureTypeFactory ftf = ftb.getFeatureTypeFactory();


        ftb.setName("Address");
        ftb.add("rue", String.class);
        ftb.add("ville", String.class);
        ComplexType adressType = ftb.buildType();
        AttributeDescriptor descAdress = ftf.createAttributeDescriptor(adressType, adressType.getName(), 1, 1, true, null);
        System.out.println(adressType);

        final Collection<Property> properties = new ArrayList<Property>();
        properties.add(new DefaultProperty("rue jean moulin", adressType.getDescriptor("rue")));
        properties.add(new DefaultProperty("montmartre", adressType.getDescriptor("ville")));
        ComplexAttribute adress1 = new DefaultComplexAttribute(properties,  descAdress, new DefaultFeatureId("RR"));





        ftb.reset();
        ftb.setName("Personne");
        ftb.add("nom", String.class);
        ftb.add("age", Integer.class);
        ftb.add(adressType,new DefaultName(null,  "adress"),null,0,Integer.MAX_VALUE,true,null);
        FeatureType ft = ftb.buildFeatureType();
        System.out.println(ft);


        properties.clear();
        properties.add(new DefaultProperty("robert", ft.getDescriptor("nom")));
        properties.add(new DefaultProperty(23, ft.getDescriptor("age")));
        properties.add(new DefaultProperty(adress1, ft.getDescriptor("adress")));
        properties.add(new DefaultProperty(adress1, ft.getDescriptor("adress")));

        Feature person1 = DefaultFeature.create(properties, ft, new DefaultFeatureId("1"));

        System.out.println(person1);
    }

    public static Kml buildKml(){
        KmlFactory kmlFactory = new KmlFactoryDefault();
        boolean visibility = true;
        boolean extrude = true;
        boolean open = true;
        String name = "This is a name";
        String description = "This is a description";
        String address = "This is an address";
        String snippet = "This is a snippet";
        String phoneNumber = "This is a phone number";
        Region region = null;
        ExtendedData extendedData = null;

        
        //kmlFactory.
        IdAttributes idAttributes = kmlFactory.createIdAttributes("di", "ditarget");
        List<Coordinate> coordinatesList = new LinkedList<Coordinate>();
        Coordinate coordinate1 = kmlFactory.createCoordinate(0.1,0.2,0.3);
        Coordinate coordinate2 = kmlFactory.createCoordinate(0.4,0.55,0.6);
        coordinatesList.add(coordinate1);
        coordinatesList.add(coordinate2);
        Coordinates coordinates = kmlFactory.createCoordinates(coordinatesList);
        AbstractGeometry point = kmlFactory.createPoint(null, idAttributes, null, null, extrude, null, coordinates, null, null);
        AbstractFeature placeMark = kmlFactory.createPlacemark(null, idAttributes,"Placemark name", visibility, open, null, null, "Placemark address", null, "Placemark phone number", "Placemark snippet", "Placemark description", null, null, null, null, region, extendedData, null, null, point, null, null);
        return kmlFactory.createKml(null, placeMark, null, null);
    }

    public static void main(String[] args) {

        
        //featuresTests();
        
        File input = new File("/home/samuel/netbeans/geotoolkit-kml/modules/datastore/geotk-kml/src/main/java/org/geotoolkit/data/kml/" +
                "input3.xml");
        File output = new File("/home/samuel/netbeans/geotoolkit-kml/modules/datastore/geotk-kml/src/main/java/org/geotoolkit/data/kml/" +
                "output2.xml");
        KmlReader reader = new KmlReader(input);
        Kml kml = reader.read();
        //System.out.println(kml);

        KmlWriter writer = new KmlWriter(output);
        writer.write(kml);


    }


}
