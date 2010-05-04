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

package org.geotoolkit.data.gpx.model;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.geotoolkit.io.TableWriter;
import org.opengis.geometry.Envelope;

/**
 * Metadatas of GPX files.
 * 
 * @author Johann Sorel (Geomatys)
 * @module pending
 */
public class MetaData {

    private final String name;
    private final String description;
    private final Person person;
    private final CopyRight copyRight;
    private final List<URI> links;
    private final Date time;
    private final String keywords;
    private final Envelope bounds;

    public MetaData(String name, String description, Person person, CopyRight copyRight,
            List<URI> links, Date time, String keywords, Envelope bounds) {
        this.name = name;
        this.description = description;
        this.person = person;
        this.copyRight = copyRight;
        this.time = time;
        this.keywords = keywords;
        this.bounds = bounds;

        if(links != null && !links.isEmpty()){
            this.links = links;
        }else{
            this.links = Collections.emptyList();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    public CopyRight getCopyRight() {
        return copyRight;
    }

    public List<URI> getLinks() {
        return links;
    }

    public Date getTime() {
        return time;
    }

    public String getKeywords() {
        return keywords;
    }

    public Envelope getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        final StringWriter writer = new StringWriter();
        final TableWriter tablewriter = new TableWriter(writer);

        tablewriter.nextLine(TableWriter.DOUBLE_HORIZONTAL_LINE);
        tablewriter.write("GPX-Metadata\t \n");
        tablewriter.nextLine(TableWriter.SINGLE_HORIZONTAL_LINE);

        tablewriter.write("Name\t"+getName()+"\n");
        tablewriter.write("Desc\t"+getDescription()+"\n");
        tablewriter.write("Time\t"+getTime()+"\n");
        tablewriter.write("Keywords\t"+getKeywords()+"\n");
        tablewriter.write("Bounds\t"+getBounds()+"\n");

        final Person person = getPerson();
        if(person != null){
            tablewriter.write("Person - Name\t"+person.getName()+"\n");
            tablewriter.write("Person - EMail\t"+person.getEmail()+"\n");
            tablewriter.write("Person - Link\t"+person.getLink()+"\n");
        }else{
            tablewriter.write("Person\t"+person+"\n");
        }

        final CopyRight copyright = getCopyRight();
        if(copyright != null){
            tablewriter.write("CopyRight - Author\t"+copyright.getAuthor()+"\n");
            tablewriter.write("CopyRight - Year\t"+copyright.getYear()+"\n");
            tablewriter.write("CopyRight - License\t"+copyright.getLicense()+"\n");
        }else{
            tablewriter.write("CopyRight\t"+copyright+"\n");
        }

        tablewriter.write("Links\t");
        final List<URI> links = getLinks();
        if(links.isEmpty()){
            tablewriter.write("None\n");
        }else{
            tablewriter.write("\n");
            for(final URI uri : getLinks()){
                tablewriter.write("\t"+uri+"\n");
            }
        }
        
        tablewriter.nextLine(TableWriter.DOUBLE_HORIZONTAL_LINE);

        try {
            tablewriter.flush();
            writer.flush();
        } catch (IOException ex) {
            //will never happen is this case
            ex.printStackTrace();
        }

        return writer.getBuffer().toString();
    }

}
