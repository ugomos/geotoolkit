/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2009-2010, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2009-2010, Geomatys
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
package org.geotoolkit.coverage.io;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.imageio.ImageReader;

import org.opengis.coverage.grid.GridCoverage;

import org.geotoolkit.coverage.GridSampleDimension;
import org.geotoolkit.coverage.grid.GeneralGridGeometry;
import org.geotoolkit.util.collection.BackingStoreException;
import org.geotoolkit.util.logging.Logging;
import org.geotoolkit.util.Localized;
import org.geotoolkit.resources.Errors;
import org.geotoolkit.resources.Vocabulary;
import org.geotoolkit.internal.io.IOUtilities;


/**
 * Base class of {@link GridCoverage} readers. Reading is a two steps process:
 * <p>
 * <ul>
 *   <li>The input must be set first using the {@link #setInput(Object)} method.</li>
 *   <li>The actual reading is performed by a call to the
 *       {@link #read(int, GridCoverageReadParam)} method.</li>
 * </ul>
 * <p>
 * Example:
 *
 * {@preformat java
 *     GridCoverageReader reader = ...
 *     reader.setInput(new File("MyCoverage.asc"));
 *     GridCoverage coverage = reader.read(0, null);
 * }
 *
 * {@note This class is conceptually equivalent to the <code>ImageReader</code> class in
 * the standard Java library. Implementations of this class are often wrappers around a Java
 * <code>ImageReader</code>, converting geodetic coordinates to pixel coordinates before to
 * delegate the reading of pixel values.}
 *
 * @author Martin Desruisseaux (IRD, Geomatys)
 * @author Johann Sorel (Geomatys)
 * @version 3.09
 *
 * @see ImageReader
 *
 * @since 3.09 (derived from 2.4)
 * @module
 */
public abstract class GridCoverageReader implements Localized {
    /**
     * The logger, mostly for use by the {@link #read(int, GridCoverageReadParam)} method.
     */
    static Logger LOGGER = Logging.getLogger("org.geotoolkit.coverage.io");

    /**
     * The input (typically a {@link java.io.File}, {@link java.net.URL} or {@link String}),
     * or {@code null} if input is not set.
     */
    Object input;

    /**
     * The locale to use for formatting messages, or {@code null} for a default locale.
     */
    Locale locale;

    /**
     * Creates a new instance.
     */
    protected GridCoverageReader() {
    }

    /**
     * Returns the locale to use for formatting warnings and error messages,
     * or {@code null} for the {@linkplain Locale#getDefault() default}.
     *
     * @return The current locale, or {@code null}.
     *
     * @see ImageReader#getLocale()
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the current locale of this coverage reader to the given value. A value of
     * {@code null} removes any previous setting, and indicates that the reader should
     * localize as it sees fit.
     *
     * @param locale The new locale to use.
     *
     * @see ImageReader#setLocale(Locale)
     */
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    /**
     * Returns a localized string for the specified error key.
     *
     * @param key One of the constants declared in the {@link Errors.Keys} inner class.
     */
    final String error(final int key) {
        return Errors.getResources(locale).getString(key);
    }

    /**
     * Sets the input source to the given object. The input is typically a
     * {@link java.io.File} or a {@link String} object. But some other types
     * (e.g. {@link javax.imageio.stream.ImageInputStream}) may be accepted
     * as well depending on the implementation.
     *
     * @param  input The input (typically {@link java.io.File} or {@link String}) to be read.
     * @throws IllegalArgumentException if input is not a valid instance for this reader.
     * @throws CoverageStoreException if the operation failed.
     *
     * @see ImageReader#setInput(Object)
     */
    public void setInput(Object input) throws CoverageStoreException {
        this.input = input;
    }

    /**
     * Returns the input which was set by the last call to {@link #setInput(Object)},
     * or {@code null} if none.
     *
     * @return The current input, or {@code null} if none.
     * @throws CoverageStoreException if the operation failed.
     *
     * @see ImageReader#getInput()
     */
    public Object getInput() throws CoverageStoreException {
        return input;
    }

    /**
     * Returns the name of the {@linkplain #input}, or "<cite>Untitled</cite>" if
     * the input is not a recognized type. This is used for formatting messages only.
     */
    final String getInputName() {
        final Object input = this.input;
        if (IOUtilities.canProcessAsPath(input)) {
            return IOUtilities.name(input);
        } else {
            return Vocabulary.getResources(locale).getString(Vocabulary.Keys.UNTITLED);
        }
    }

    /**
     * Returns the list of coverage names available from the current input source. The length
     * of the returned list is the number of coverages found in the current input source. The
     * elements in the returned list are the names of each coverage.
     * <p>
     * The returned list may be backed by this {@code GridCoverageReader}: it should be used
     * only as long as this reader and its input source are valids, iterating over the list
     * may be costly and the operation performed on the list may throw a
     * {@link BackingStoreException}.
     *
     * @return The names of the coverages.
     * @throws IllegalStateException If the input source has not been set.
     * @throws CoverageStoreException If an error occurs reading the information from the input source.
     *
     * @see ImageReader#getNumImages(boolean)
     */
    public abstract List<String> getCoverageNames() throws CoverageStoreException;

    /**
     * Returns the grid geometry for the {@link GridCoverage} to be read at the given index.
     *
     * @param  index The index of the coverage to be queried.
     * @return The grid geometry for the {@link GridCoverage} at the specified index.
     * @throws IllegalStateException if the input source has not been set.
     * @throws IndexOutOfBoundsException if the supplied index is out of bounds.
     * @throws CoverageStoreException if an error occurs reading the information from the input source.
     *
     * @see ImageReader#getWidth(int)
     * @see ImageReader#getHeight(int)
     */
    public abstract GeneralGridGeometry getGridGeometry(int index) throws CoverageStoreException;

    /**
     * Returns the sample dimensions for each band of the {@link GridCoverage} to be read.
     * If sample dimensions are not known, then this method returns {@code null}.
     *
     * @param  index The index of the coverage to be queried.
     * @return The list of sample dimensions for the {@link GridCoverage} at the specified index.
     *         This list length is equals to the number of bands in the {@link GridCoverage}.
     * @throws IllegalStateException if the input source has not been set.
     * @throws IndexOutOfBoundsException if the supplied index is out of bounds.
     * @throws CoverageStoreException if an error occurs reading the information from the input source.
     */
    public abstract List<GridSampleDimension> getSampleDimensions(int index) throws CoverageStoreException;

    /**
     * Returns an optional map of properties associated with the coverage at the given index, or
     * {@code null} if none. The properties are implementation-specific; they are available to
     * subclasses for any use. The {@code GridCoverageReader} class will simply gives those
     * properties to the {@link javax.media.jai.PropertySource} object to be created by the
     * {@link #read read} method, without any processing.
     * <p>
     * The default implementation returns {@code null} in every cases.
     *
     * @param  index The index of the coverage to be queried.
     * @return The properties, or {@code null} if none.
     * @throws CoverageStoreException if an error occurs reading the information from the input source.
     */
    public Map<?,?> getProperties(int index) throws CoverageStoreException {
        return null;
    }

    /**
     * Reads the grid coverage.
     *
     * @param  index The index of the image to be queried.
     * @param  param Optional parameters used to control the reading process, or {@code null}.
     * @return The {@link GridCoverage} at the specified index.
     * @throws IllegalStateException if the input source has not been set.
     * @throws IndexOutOfBoundsException if the supplied index is out of bounds.
     * @throws CoverageStoreException if an error occurs reading the width information from the input source.
     *
     * @see ImageReader#read(int)
     */
    public abstract GridCoverage read(int index, GridCoverageReadParam param) throws CoverageStoreException;

    /**
     * Restores the {@code GridCoverageReader} to its initial state.
     *
     * @throws CoverageStoreException if an error occurs while disposing resources.
     *
     * @see ImageReader#reset()
     */
    public void reset() throws CoverageStoreException {
        locale = null;
        input  = null;
    }
}
