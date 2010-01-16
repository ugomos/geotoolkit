/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2004-2010, Open Source Geospatial Foundation (OSGeo)
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
package org.geotoolkit.referencing.factory.web;

import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.opengis.util.InternationalString;
import org.opengis.metadata.citation.Citation;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.IdentifiedObject;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.ProjectedCRS;

import org.geotoolkit.factory.Hints;
import org.geotoolkit.lang.ThreadSafe;
import org.geotoolkit.util.SimpleInternationalString;
import org.geotoolkit.metadata.iso.citation.Citations;
import org.geotoolkit.metadata.iso.citation.DefaultCitation;
import org.geotoolkit.referencing.factory.DirectAuthorityFactory;


/**
 * The factory for {@linkplain ProjectedCRS projected CRS} in the {@code AUTO} and {@code AUTO2}
 * space.
 *
 * @author Jody Garnett (Refractions)
 * @author Rueben Schulz (UBC)
 * @author Martin Desruisseaux (IRD)
 * @version 3.00
 *
 * @since 2.0
 * @module
 */
@ThreadSafe(concurrent = true)
public class AutoCRSFactory extends DirectAuthorityFactory implements CRSAuthorityFactory {
    /**
     * The authority code. We use the {@code AUTO2} title, but merge the identifiers from
     * {@code AUTO} and {@code AUTO2} in order to use the same factory for both authorities.
     */
    private static final Citation AUTHORITY;
    static {
        final DefaultCitation c = new DefaultCitation(Citations.AUTO2);
        c.getIdentifiers().addAll(Citations.AUTO.getIdentifiers());
        AUTHORITY = (Citation) c.unmodifiable();
    }

    /**
     * Map of Factlets by integer code (from {@code AUTO:code}).
     *
     * @todo Replace this with full FactorySPI system.
     */
    private final Map<Integer,Factlet> factlets = new TreeMap<Integer,Factlet>();

    /**
     * Constructs a default factory for the {@code AUTO} authority.
     */
    public AutoCRSFactory() {
        this(EMPTY_HINTS);
    }

    /**
     * Constructs a factory for the {@code AUTO} authority using the specified hints.
     *
     * @param userHints An optional set of hints, or {@code null} for the default ones.
     */
    public AutoCRSFactory(final Hints userHints) {
        super(userHints);
        add(Auto42001.DEFAULT);
        add(Auto42002.DEFAULT);
        add(Auto42003.DEFAULT);
        add(Auto42004.DEFAULT);
        add(Auto42005.DEFAULT);
    }

    /**
     * Adds the specified factlet.
     */
    private void add(final Factlet f) {
        final int code = f.code();
        if (factlets.put(code, f) != null) {
            throw new IllegalArgumentException(String.valueOf(code));
        }
    }

    /**
     * Returns the {@link Factlet} for the given code.
     *
     * @param  code The code.
     * @return The fatclet for the specified code.
     * @throws NoSuchAuthorityCodeException if no factlet has been found for the specified code.
     */
    private Factlet findFactlet(final Code code) throws NoSuchAuthorityCodeException {
        if (code.authority.equalsIgnoreCase("AUTO") ||
            code.authority.equalsIgnoreCase("AUTO2"))
        {
            final Integer key = code.code;
            final Factlet fac = factlets.get(key);
            if (fac != null) {
                return fac;
            }
        }
        throw noSuchAuthorityCode(code.type, code.toString());
    }

    /**
     * Returns the authority for this factory.
     */
    @Override
    public Citation getAuthority() {
        return AUTHORITY;
    }

    /**
     * Provides a complete set of the known codes provided by this authority.
     * The returned set contains only numeric identifiers like {@code "42001"},
     * {@code "42002"}, <i>etc</i>. The authority name ({@code "AUTO"})
     * and the {@code lon0,lat0} part are not included. This is consistent with the
     * {@linkplain org.geotoolkit.referencing.factory.epsg.DirectEpsgFactory#getAuthorityCodes
     * codes returned by the EPSG factory} and avoid duplication, since the authority is the
     * same for every codes returned by this factory. It also make it easier for clients to
     * prepend whatever authority name they wish, as for example in the
     * {@linkplain org.geotoolkit.referencing.factory.AllAuthoritiesFactory#getAuthorityCodes
     * all authorities factory}.
     */
    @Override
    public Set<String> getAuthorityCodes(final Class<? extends IdentifiedObject> type)
            throws FactoryException
    {
        if (type.isAssignableFrom(ProjectedCRS.class)) {
            final Set<String> set = new LinkedHashSet<String>();
            for (final Integer code : factlets.keySet()) {
                set.add(String.valueOf(code));
            }
            return set;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Returns the CRS name for the given code.
     *
     * @throws FactoryException if an error occured while fetching the description.
     */
    @Override
    public InternationalString getDescriptionText(final String code) throws FactoryException {
        final Code c = new Code(code, ProjectedCRS.class);
        return new SimpleInternationalString(findFactlet(c).getName());
    }

    /**
     * Creates an object from the specified code. The default implementation delegates to
     * <code>{@linkplain #createCoordinateReferenceSystem createCoordinateReferenceSystem}(code)</code>.
     *
     * @throws FactoryException if the object creation failed.
     */
    @Override
    public IdentifiedObject createObject(final String code) throws FactoryException {
        return createCoordinateReferenceSystem(code);
    }

    /**
     * Creates a coordinate reference system from the specified code. The default implementation
     * delegates to <code>{@linkplain #createProjectedCRS createProjectedCRS}(code)</code>.
     *
     * @throws FactoryException if the object creation failed.
     */
    @Override
    public CoordinateReferenceSystem createCoordinateReferenceSystem(final String code)
            throws FactoryException
    {
        return createProjectedCRS(code);
    }

    /**
     * Creates a projected coordinate reference system from the specified code.
     *
     * @throws FactoryException if the object creation failed.
     */
    @Override
    public ProjectedCRS createProjectedCRS(final String code) throws FactoryException {
        final Code c = new Code(code, ProjectedCRS.class);
        return findFactlet(c).create(c, factories);
    }
}
