/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2008 - 2009, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.sml.xml.v100;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.geotoolkit.sml.xml.AbstractComponents;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="ComponentList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="component" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element ref="{http://www.opengis.net/sensorML/1.0}_Process"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opengis.net/gml}AssociationAttributeGroup"/>
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opengis.net/gml}AssociationAttributeGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 * @module pending
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "componentList"
})
@XmlRootElement(name = "Components")
public class Components implements AbstractComponents {

    @XmlElement(name = "ComponentList")
    private ComponentList componentList;
    @XmlAttribute
    private List<String> nilReason;
    @XmlAttribute(namespace = "http://www.opengis.net/gml")
    private String remoteSchema;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String actuate;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String arcrole;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String href;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String role;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String show;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String title;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String type;

    public Components() {

    }

    public Components(final ComponentList componentList) {
        this.componentList = componentList;
    }

    public Components(final List<ComponentPropertyType> component) {
        this.componentList = new ComponentList(component);
    }

    public Components(final AbstractComponents components) {
        if (components != null) {
            this.actuate       = components.getActuate();
            this.arcrole       = components.getArcrole();
            if (components.getComponentList() != null) {
                this.componentList = new ComponentList(components.getComponentList());
            }
            this.href          = components.getHref();
            this.remoteSchema  = components.getRemoteSchema();
            this.role          = components.getRole();
            this.show          = components.getShow();
            this.title         = components.getTitle();
            this.type          = components.getType();
        }
    }

    /**
     * Gets the value of the componentList property.
     */
    public ComponentList getComponentList() {
        return componentList;
    }

    /**
     * Sets the value of the componentList property.
     * 
     */
    public void setComponentList(final ComponentList value) {
        this.componentList = value;
    }

    /**
     * Gets the value of the nilReason property.
     * 
     */
    public List<String> getNilReason() {
        if (nilReason == null) {
            nilReason = new ArrayList<String>();
        }
        return this.nilReason;
    }

    /**
     * Gets the value of the remoteSchema property.
     */
    public String getRemoteSchema() {
        return remoteSchema;
    }

    /**
     * Sets the value of the remoteSchema property.
     */
    public void setRemoteSchema(final String value) {
        this.remoteSchema = value;
    }

    /**
     * Gets the value of the actuate property.
    */
    public String getActuate() {
        return actuate;
    }

    /**
     * Sets the value of the actuate property.
     */
    public void setActuate(final String value) {
        this.actuate = value;
    }

    /**
     * Gets the value of the arcrole property.
     */
    public String getArcrole() {
        return arcrole;
    }

    /**
     * Sets the value of the arcrole property.
     */
    public void setArcrole(final String value) {
        this.arcrole = value;
    }

    /**
     * Gets the value of the href property.
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     */
    public void setHref(final String value) {
        this.href = value;
    }

    /**
     * Gets the value of the role property.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     */
    public void setRole(final String value) {
        this.role = value;
    }

    /**
     * Gets the value of the show property.
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     */
    public void setShow(final String value) {
        this.show = value;
    }

    /**
     * Gets the value of the title property.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     */
    public void setTitle(final String value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     */
    public String getType() {
        return type;
     }

    /**
     * Sets the value of the type property.
     */
    public void setType(final String value) {
        this.type = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Components]").append("\n");
        if (componentList != null) {
            sb.append("componentList: ").append(componentList).append('\n');
        }
        if (nilReason != null) {
            sb.append("nilReason:").append('\n');
            for (String k : nilReason) {
                sb.append("nilReason: ").append(k).append('\n');
            }
        }
        if (remoteSchema != null) {
            sb.append("remoteSchema: ").append(remoteSchema).append('\n');
        }
        if (actuate != null) {
            sb.append("actuate: ").append(actuate).append('\n');
        }
        if (arcrole != null) {
            sb.append("actuate: ").append(arcrole).append('\n');
        }
        if (href != null) {
            sb.append("href: ").append(href).append('\n');
        }
        if (role != null) {
            sb.append("role: ").append(role).append('\n');
        }
        if (show != null) {
            sb.append("show: ").append(show).append('\n');
        }
        if (title != null) {
            sb.append("title: ").append(title).append('\n');
        }
        if (type != null) {
            sb.append("type: ").append(type).append('\n');
        }
        return sb.toString();
    }

    /**
     * Verify if this entry is identical to specified object.
     */
    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Components) {
            final Components that = (Components) object;

            return Objects.equals(this.actuate, that.actuate)             &&
                   Objects.equals(this.href, that.href)                   &&
                   Objects.equals(this.componentList, that.componentList) &&
                   Objects.equals(this.nilReason, that.nilReason)         &&
                   Objects.equals(this.remoteSchema, that.remoteSchema)   &&
                   Objects.equals(this.role, that.role)                   &&
                   Objects.equals(this.show, that.show)                   &&
                   Objects.equals(this.title, that.title)                 &&
                   Objects.equals(this.getType(), that.getType())         &&
                   Objects.equals(this.arcrole, that.arcrole);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.componentList != null ? this.componentList.hashCode() : 0);
        hash = 29 * hash + (this.nilReason != null ? this.nilReason.hashCode() : 0);
        hash = 29 * hash + (this.remoteSchema != null ? this.remoteSchema.hashCode() : 0);
        hash = 29 * hash + (this.actuate != null ? this.actuate.hashCode() : 0);
        hash = 29 * hash + (this.arcrole != null ? this.arcrole.hashCode() : 0);
        hash = 29 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 29 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 29 * hash + (this.show != null ? this.show.hashCode() : 0);
        hash = 29 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }

}
