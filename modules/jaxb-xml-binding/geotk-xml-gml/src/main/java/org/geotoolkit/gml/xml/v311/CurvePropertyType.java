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
package org.geotoolkit.gml.xml.v311;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.geotoolkit.gml.xml.CurveProperty;
import org.geotoolkit.util.Utilities;


/**
 * A property that has a curve as its value domain can either be an appropriate geometry element encapsulated in an 
 * element of this type or an XLink reference to a remote geometry element
 * (where remote includes geometry elements located elsewhere in the same document).
 * Either the reference or the contained element must be given, but neither both nor none.
 * 
 * <p>Java class for CurvePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurvePropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element ref="{http://www.opengis.net/gml}AbstractCurve"/>
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
@XmlType(name = "CurvePropertyType", propOrder = {
    "abstractCurve"
})
public class CurvePropertyType implements CurveProperty {

    @XmlElementRef(name = "AbstractCurve", namespace = "http://www.opengis.net/gml", type = JAXBElement.class)
    private JAXBElement<? extends AbstractCurveType> abstractCurve;
    @XmlAttribute(namespace = "http://www.opengis.net/gml")
    @XmlSchemaType(name = "anyURI")
    private String remoteSchema;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String type;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    private String href;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    private String role;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    private String arcrole;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String title;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String show;
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String actuate;

    CurvePropertyType() {}

    public CurvePropertyType(final AbstractCurveType curve) {
        if (curve != null) {
            ObjectFactory factory = new ObjectFactory();
            if (curve instanceof CurveType) {
                abstractCurve = factory.createCurve((CurveType) curve);
            } else if (curve instanceof OrientableCurveType) {
                abstractCurve = factory.createOrientableCurve((OrientableCurveType) curve);
            } else if (curve instanceof CompositeCurveType) {
                abstractCurve = factory.createCompositeCurve((CompositeCurveType) curve);
            } else if (curve instanceof LineStringType) {
                abstractCurve = factory.createLineString((LineStringType) curve);
            } else {
                throw new IllegalArgumentException("unexpected subclasse of abstractCurveType");
            }
        }
    }

    /**
     * Gets the value of the abstractCurve property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link LineStringType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AbstractCurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrientableCurveType }{@code >}
     *     
     */
    public JAXBElement<? extends AbstractCurveType> getJbAbstractCurve() {
        return abstractCurve;
    }

    /**
     * Sets the value of the abstractCurve property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link LineStringType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AbstractCurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrientableCurveType }{@code >}
     *     
     */
    public void setJbAbstractCurve(final JAXBElement<? extends AbstractCurveType> value) {
        this.abstractCurve = ((JAXBElement<? extends AbstractCurveType> ) value);
    }

    /**
     * Gets the value of the abstractCurve property.
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link LineStringType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AbstractCurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrientableCurveType }{@code >}
     *
     */
    public AbstractCurveType getAbstractCurve() {
        if (abstractCurve != null) {
            return abstractCurve.getValue();
        }
        return null;
    }

    /**
     * Sets the value of the abstractCurve property.
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link LineStringType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AbstractCurveType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrientableCurveType }{@code >}
     *
     */
    public void setAbstractCurve(final AbstractCurveType curve) {
        if (curve != null) {
            ObjectFactory factory = new ObjectFactory();
            if (curve instanceof CurveType) {
                abstractCurve = factory.createCurve((CurveType) curve);
            } else if (curve instanceof OrientableCurveType) {
                abstractCurve = factory.createOrientableCurve((OrientableCurveType) curve);
            } else if (curve instanceof CompositeCurveType) {
                abstractCurve = factory.createCompositeCurve((CompositeCurveType) curve);
            } else if (curve instanceof LineStringType) {
                abstractCurve = factory.createLineString((LineStringType) curve);
            } else {
                throw new IllegalArgumentException("unexpected subclasse of abstractCurveType");
            }
        } else {
            abstractCurve = null;
        }
    }

    /**
     * Gets the value of the remoteSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteSchema() {
        return remoteSchema;
    }

    /**
     * Sets the value of the remoteSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteSchema(final String value) {
        this.remoteSchema = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(final String value) {
        this.type = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(final String value) {
        this.href = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(final String value) {
        this.role = value;
    }

    /**
     * Gets the value of the arcrole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArcrole() {
        return arcrole;
    }

    /**
     * Sets the value of the arcrole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArcrole(final String value) {
        this.arcrole = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(final String value) {
        this.title = value;
    }

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(final String value) {
        this.show = value;
    }

    /**
     * Gets the value of the actuate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActuate() {
        return actuate;
    }

    /**
     * Sets the value of the actuate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActuate(final String value) {
        this.actuate = value;
    }

    /**
     * Verify if this entry is identical to the specified object.
     */
    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof CurvePropertyType) {
            final CurvePropertyType that = (CurvePropertyType) object;

            boolean geom = false;
            if (this.abstractCurve != null && that.abstractCurve != null) {
                geom = Utilities.equals(this.abstractCurve.getValue(),   that.abstractCurve.getValue());
            } else if (this.abstractCurve == null && that.abstractCurve == null) {
                geom = true;
            }

            return Utilities.equals(this.actuate,            that.actuate)          &&
                   Utilities.equals(this.arcrole,            that.arcrole)          &&
                   Utilities.equals(this.type,               that.type)             &&
                   Utilities.equals(this.href,               that.href)             &&
                   Utilities.equals(this.remoteSchema,       that.remoteSchema)     &&
                   Utilities.equals(this.show,               that.show)             &&
                   Utilities.equals(this.role,               that.role)             &&
                   Utilities.equals(this.title,              that.title)            &&
                   geom;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.abstractCurve != null ? this.abstractCurve.hashCode() : 0);
        hash = 29 * hash + (this.remoteSchema != null ? this.remoteSchema.hashCode() : 0);
        hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 29 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 29 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 29 * hash + (this.arcrole != null ? this.arcrole.hashCode() : 0);
        hash = 29 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 29 * hash + (this.show != null ? this.show.hashCode() : 0);
        hash = 29 * hash + (this.actuate != null ? this.actuate.hashCode() : 0);
        return hash;
    }

    /**
     * Retourne une representation de l'objet.
     */

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[CurvePropertyType]");
        if (abstractCurve != null && abstractCurve.getValue() != null)
            s.append(abstractCurve.getValue().toString()).append('\n');

        if(actuate != null) {
            s.append("actuate=").append(actuate).append('\n');
        }
        if(arcrole != null) {
            s.append("arcrole=").append(arcrole).append('\n');
        }
        if(href != null) {
            s.append("href=").append(href).append('\n');
        }
        if(role != null) {
            s.append("role=").append(role).append('\n');
        }
        if(show != null) {
            s.append("show=").append(show).append('\n');
        }
        if(title != null) {
            s.append("title=").append(title).append('\n');
        }
        if(title != null) {
            s.append("title=").append(title).append('\n');
        }
        return s.toString();
    }

}
