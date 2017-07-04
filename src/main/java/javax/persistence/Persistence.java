/*
 * Copyright (c) 2008, 2009, 2011 Oracle, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.  The Eclipse Public License is available
 * at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License
 * is available at http://www.eclipse.org/org/documents/edl-v10.php.
 */
package javax.persistence;

import javax.persistence.spi.LoadState;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bootstrap class that provides access to an EntityManagerFactory.
 */
public class Persistence
{
    /**
     * Create and return an EntityManagerFactory for the named persistence unit.
     * @param persistenceUnitName The name of the persistence unit
     * @return The factory that creates EntityManagers configured according to the specified persistence unit
     */
    public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName)
    {
        return createEntityManagerFactory(persistenceUnitName, null);
    }

    /**
     * Create and return an EntityManagerFactory for the named persistence unit using the given properties.
     * @param persistenceUnitName The name of the persistence unit
     * @param properties Additional properties to use when creating the factory. The values of these
     * properties override any values that may have been configured elsewhere
     * @return The factory that creates EntityManagers configured according to the specified persistence unit
     */
    public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties)
    {
        EntityManagerFactory emf = null;
        List<PersistenceProvider> providers = getProviders();
        for (PersistenceProvider provider : providers)
        {
            emf = provider.createEntityManagerFactory(persistenceUnitName, properties);
            if (emf != null)
            {
                break;
            }
        }
        if (emf == null)
        {
            throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
        }
        return emf;
    }

    private static List<PersistenceProvider> getProviders()
    {
        return PersistenceProviderResolverHolder.getPersistenceProviderResolver().getPersistenceProviders();
    }

    /**
     * Create database schemas and/or tables and/or create DDL scripts as determined by the supplied
     * properties Called when schema generation is to occur as a separate phase from creation of the entity manager factory.
     * @param persistenceUnitName the name of the persistence unit
     * @param properties properties for schema generation; these may also contain provider-specific
     * properties. The values of these properties override any values that may have been configured elsewhere.
     * @throws PersistenceException if insufficient or inconsistent configuration information is provided or if schema generation otherwise fails.
     */
    public static void generateSchema(String persistenceUnitName, Map properties)
    {
        List<PersistenceProvider> providers = getProviders();
        for (PersistenceProvider provider : providers)
        {
            final boolean generated = provider.generateSchema(persistenceUnitName, properties);
            if (generated)
            {
                return;
            }
        }

        throw new PersistenceException("No persistence provider found for schema generation for persistence-unit named " + persistenceUnitName);
    }

    /**
     * Return the PersistenceUtil instance
     * @return PersistenceUtil instance
     * @since Java Persistence 2.0
     */
    public static PersistenceUtil getPersistenceUtil()
    {
        return new PersistenceUtilImpl();
    }

    /**
     * Implementation of PersistenceUtil interface
     * @since Java Persistence 2.0
     */
    private static class PersistenceUtilImpl implements PersistenceUtil
    {
        public boolean isLoaded(Object entity, String attributeName)
        {
            PersistenceProviderResolver resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();

            List<PersistenceProvider> providers = resolver.getPersistenceProviders();

            for (PersistenceProvider provider : providers)
            {
                LoadState loadstate = provider.getProviderUtil().isLoadedWithoutReference(entity, attributeName);
                if (loadstate == LoadState.LOADED)
                {
                    return true;
                }
                else if (loadstate == LoadState.NOT_LOADED)
                {
                    return false;
                } // else continue
            }

            // None of the providers could determine the load state try isLoadedWithReference
            for (PersistenceProvider provider : providers)
            {
                LoadState loadstate = provider.getProviderUtil().isLoadedWithReference(entity, attributeName);
                if (loadstate == LoadState.LOADED)
                {
                    return true;
                }
                else if (loadstate == LoadState.NOT_LOADED)
                {
                    return false;
                } // else continue
            }

            // None of the providers could determine the load state.
            return true;
        }

        public boolean isLoaded(Object entity)
        {
            PersistenceProviderResolver resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();

            List<PersistenceProvider> providers = resolver.getPersistenceProviders();

            for (PersistenceProvider provider : providers)
            {
                LoadState loadstate = provider.getProviderUtil().isLoaded(entity);
                if (loadstate == LoadState.LOADED)
                {
                    return true;
                }
                else if (loadstate == LoadState.NOT_LOADED)
                {
                    return false;
                } // else continue
            }
            // None of the providers could determine the load state
            return true;
        }
    }

    /** Only present for TCK compatibility allegedly, but then that is private. */
    @Deprecated
    public static final String PERSISTENCE_PROVIDER = "javax.persistence.spi.PeristenceProvider";

    /** Only present for TCK compatibility allegedly, but then that is private. */
    @Deprecated
    protected static final Set<PersistenceProvider> providers = new HashSet<PersistenceProvider>();
}
