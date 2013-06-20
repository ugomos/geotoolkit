/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2004-2012, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2009-2012, Geomatys
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
 *
 *    This package contains documentation from OpenGIS specifications.
 *    OpenGIS consortium's work is fully acknowledged here.
 */
package org.geotoolkit.metadata.iso.extent;

import java.util.Date;
import java.util.logging.LogRecord;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import net.jcip.annotations.ThreadSafe;

import org.opengis.geometry.Envelope;
import org.opengis.temporal.Period;
import org.opengis.temporal.Instant;
import org.opengis.temporal.TemporalPrimitive;
import org.opengis.metadata.extent.TemporalExtent;
import org.opengis.metadata.extent.SpatialTemporalExtent;
import org.opengis.referencing.operation.TransformException;

import org.geotoolkit.metadata.iso.MetadataEntity;
import org.geotoolkit.factory.FactoryNotFoundException;
import org.geotoolkit.internal.TemporalUtilities;
import org.geotoolkit.internal.referencing.ProxyForMetadata;


/**
 * Time period covered by the content of the dataset. The only property required by ISO 19115
 * is the {@linkplain #getExtent() extent} temporal primitive. However this implementation
 * class defines also {@linkplain #getStartTime() start time} and {@linkplain #getEndTime()
 * end time} properties for convenience.
 *
 * @author Martin Desruisseaux (IRD, Geomatys)
 * @author Touraïvane (IRD)
 * @author Cédric Briançon (Geomatys)
 * @version 3.20
 *
 * @since 2.1
 * @module
 *
 * @deprecated Moved to the {@link org.apache.sis.metadata.iso} package.
 */
@Deprecated
@ThreadSafe
@XmlType(name = "EX_TemporalExtent_Type")
@XmlRootElement(name = "EX_TemporalExtent")
@XmlSeeAlso(DefaultSpatialTemporalExtent.class)
public class DefaultTemporalExtent extends MetadataEntity implements TemporalExtent {
    /**
     * Serial number for inter-operability with different versions.
     */
    private static final long serialVersionUID = 3668140516657118045L;

    /**
     * The start date and time for the content of the dataset,
     * in milliseconds elapsed since January 1st, 1970. A value
     * of {@link Long#MIN_VALUE} means that this attribute is not set.
     */
    private long startTime = Long.MIN_VALUE;

    /**
     * The end date and time for the content of the dataset,
     * in milliseconds elapsed since January 1st, 1970. A value
     * of {@link Long#MIN_VALUE} means that this attribute is not set.
     */
    private long endTime = Long.MIN_VALUE;

    /**
     * The date and time for the content of the dataset.
     */
    private TemporalPrimitive extent;

    /**
     * The extent generated by {@link #getExtent()} from the start time and end time,
     * or {@code null} if none.
     *
     * @since 3.20
     */
    private transient TemporalPrimitive cachedExtent;

    /**
     * Constructs an initially empty temporal extent.
     */
    public DefaultTemporalExtent() {
    }

    /**
     * Constructs a metadata entity initialized with the values from the specified metadata.
     *
     * @param source The metadata to copy, or {@code null} if none.
     *
     * @since 2.4
     */
    public DefaultTemporalExtent(final TemporalExtent source) {
        super(source);
        /*
         * The startTime and endTime attributes are not part of GeoAPI interfaces.
         * Consequently they are not copied by the above super-class constructor.
         */
    }

    /**
     * Constructs a temporal extent from the specified envelope. The envelope can be multi-dimensional,
     * in which case the {@linkplain Envelope#getCoordinateReferenceSystem() envelope CRS} must have
     * a vertical component.
     *
     * {@note This constructor is available only if the referencing module is on the classpath.}
     *
     * @param  envelope The envelope to use for initializing this temporal extent.
     * @throws UnsupportedOperationException if the referencing module is not on the classpath.
     * @throws TransformException if the envelope can't be transformed to a temporal extent.
     *
     * @see DefaultExtent#DefaultExtent(Envelope)
     * @see DefaultGeographicBoundingBox#DefaultGeographicBoundingBox(Envelope)
     * @see DefaultVerticalExtent#DefaultVerticalExtent(Envelope)
     *
     * @since 3.18
     */
    public DefaultTemporalExtent(final Envelope envelope) throws TransformException {
        ProxyForMetadata.getInstance().copy(envelope, this);
    }

    /**
     * Creates a temporal extent initialized to the specified values.
     *
     * @param startTime The start date and time for the content of the dataset.
     * @param endTime   The end date and time for the content of the dataset.
     */
    public DefaultTemporalExtent(final Date startTime, final Date endTime) {
        setStartTime(startTime);
        setEndTime  (endTime);
    }

    /**
     * Returns a Geotk metadata implementation with the same values than the given arbitrary
     * implementation. If the given object is {@code null}, then this method returns {@code null}.
     * Otherwise if the given object is already a Geotk implementation, then the given object is
     * returned unchanged. Otherwise a new Geotk implementation is created and initialized to the
     * attribute values of the given object, using a <cite>shallow</cite> copy operation
     * (i.e. attributes are not cloned).
     * <p>
     * This method checks for the {@link SpatialTemporalExtent} sub-interfaces. If this interface
     * is found, then this method delegates to the corresponding {@code castOrCopy} static method.
     *
     * @param  object The object to get as a Geotk implementation, or {@code null} if none.
     * @return A Geotk implementation containing the values of the given object (may be the
     *         given object itself), or {@code null} if the argument was null.
     *
     * @since 3.18
     */
    public static DefaultTemporalExtent castOrCopy(final TemporalExtent object) {
        if (object instanceof SpatialTemporalExtent) {
            return DefaultSpatialTemporalExtent.castOrCopy((SpatialTemporalExtent) object);
        }
        return (object == null) || (object instanceof DefaultTemporalExtent)
                ? (DefaultTemporalExtent) object : new DefaultTemporalExtent(object);
    }

    /**
     * Returns the given value as a {@link Date} object, or infers the value from the extent.
     *
     * @param time  The number of milliseconds elapsed since January 1st 1970,
     *              or {@link Long#MIN_VALUE} if none.
     * @param begin {@code true} if we are asking for the start time,
     *              or {@code false} for the end time.
     * @return The requested time as a Java date, or {@code null} if none.
     */
    private Date getTime(final long time, final boolean begin) {
        if (time != Long.MIN_VALUE) {
            return new Date(time);
        }
        final TemporalPrimitive extent = this.extent;
        final Instant instant;
        if (extent instanceof Instant) {
            instant = (Instant) extent;
        } else if (extent instanceof Period) {
            instant = begin ? ((Period) extent).getBeginning() : ((Period) extent).getEnding();
        } else {
            return null;
        }
        return instant.getPosition().getDate();
    }

    /**
     * The start date and time for the content of the dataset.
     * If no start time has been {@linkplain #setStartTime(Date) explicitely set},
     * then this method tries to infer it from the {@linkplain #getExtent() extent}.
     *
     * @return The start time, or {@code null} if none.
     */
    public synchronized Date getStartTime() {
        return getTime(startTime, true);
    }

    /**
     * Sets the start date and time for the content of the dataset.
     *
     * @param newValue The new start time.
     */
    public synchronized void setStartTime(final Date newValue) {
        checkWritePermission();
        cachedExtent = null;
        startTime = (newValue != null) ? newValue.getTime() : Long.MIN_VALUE;
    }

    /**
     * Returns the end date and time for the content of the dataset.
     * If no end time has been {@linkplain #setEndTime(Date) explicitely set},
     * then this method tries to infer it from the {@linkplain #getExtent() extent}.
     *
     * @return The end time, or {@code null} if none.
     */
    public synchronized Date getEndTime() {
        return getTime(endTime, false);
    }

    /**
     * Sets the end date and time for the content of the dataset.
     *
     * @param newValue The new end time.
     */
    public synchronized void setEndTime(final Date newValue) {
        checkWritePermission();
        cachedExtent = null;
        endTime = (newValue != null) ? newValue.getTime() : Long.MIN_VALUE;
    }

    /**
     * Returns the date and time for the content of the dataset.
     * If no extent has been {@linkplain #setExtent(TemporalPrimitive) explicitely set},
     * then this method will build an extent from the {@linkplain #getStartTime() start
     * time} and {@linkplain #getEndTime() end time} if any.
     *
     * @since 2.4
     */
    @Override
    @XmlElement(name = "extent", required = true)
    public synchronized TemporalPrimitive getExtent() {
        TemporalPrimitive value = extent;
        if (value == null) {
            value = cachedExtent;
            if (value == null) {
                final Date tmin = (startTime != Long.MIN_VALUE) ? new Date(startTime) : null;
                final Date tmax = (  endTime != Long.MIN_VALUE) ? new Date(  endTime) : tmin;
                if (tmax != null) try {
                    if (tmin == null || tmin.equals(tmax)) {
                        value = TemporalUtilities.createInstant(tmax);
                    } else {
                        value = TemporalUtilities.createPeriod(tmin, tmax);
                    }
                    cachedExtent = value;
                } catch (FactoryNotFoundException e) {
                    final LogRecord record = TemporalUtilities.createLog(e);
                    record.setSourceClassName(DefaultTemporalExtent.class.getCanonicalName());
                    record.setSourceMethodName("getExtent");
                    record.setLoggerName(LOGGER.getName());
                    LOGGER.log(record);
                }
            }
        }
        return value;
    }

    /**
     * Sets the date and time for the content of the dataset.
     *
     * @param newValue The new extent.
     *
     * @since 2.4
     */
    public synchronized void setExtent(final TemporalPrimitive newValue) {
        checkWritePermission();
        cachedExtent = null;
        extent = newValue;
    }
}
