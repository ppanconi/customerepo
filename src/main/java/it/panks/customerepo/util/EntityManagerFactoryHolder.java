package it.panks.customerepo.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * This is a helper class for creating entity managers.
 *
 * @author Miro Bezjak
 * @version 1.0
 * @since 6/2/2008
 */
public final class EntityManagerFactoryHolder {

    /**
     * A name of persistence unit in persistence.xml file.
     */
    private static final String PERSISTENCE_UNIT = "customerepo";

    /**
     * A logger for this class.
     */
    private static final Logger log = Logger.getLogger(EntityManagerFactoryHolder.class.getName());

    /*
     * Persistence managers.
     */
    private static final EntityManagerFactory factory;
    private static final ThreadLocal<EntityManager> entityManager = new ThreadLocal<>();

    static {
        // initialize factory
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Error in building entity manager factory.", e);
            // throw exception to cancel system initialization
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private EntityManagerFactoryHolder() {
    }

    /**
     * Creates an entity manager factor. Repeated invocation of this method will
     * do nothing (i.e. an entity manager factory can't initialize twice).
     */
    public static void createEntityManagerFactory() {
        /*
         * Method is empty because initialization is done in a static block
         * instead to ensure a final modifier.
         */
    }

    /**
     * Closes an entity manager factory.
     */
    public static void shutdown() {
        try {
            factory.close();
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Error during closing entity manager factory.", e);
        }
    }

    /**
     * Returns an entity manager for current thread. If no entity manager is
     * active for current thread then this method will first create new entity
     * manager then return it.
     *
     * @return an entity manager for current thread
     */
    public static EntityManager currentEntityManager() {
        EntityManager em = entityManager.get();
        // Create a new entity manager, if this Thread has none yet
        if (em == null) {
            try {
                em = factory.createEntityManager();
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "Error during creating entity manager.", e);
                // rethrow exception
                throw e;
            }
            entityManager.set(em);
        }
        return em;
    }

    /**
     * Closes an entity manager for current thread.
     */
    public static void closeEntityManager() {
        EntityManager em = entityManager.get();
        entityManager.set(null);
        if (em != null) {
            try {
                em.close();
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "Error during closing entity manager.", e);
            }
        }
    }

    /**
     * Creates and returns an entity transaction object.
     *
     * @return an entity transaction object
     */
    private static EntityTransaction getTransaction() {
        return currentEntityManager().getTransaction();
    }

    /**
     * Begins a transaction.
     *
     * @throws Exception if exception occurred during beginning of
     * transaction
     */
    public static void beginTransaction() throws Exception {
        EntityTransaction tx = getTransaction();
        if (!tx.isActive()) {
            try {
                tx.begin();
            } catch (PersistenceException e) {
                log.log(Level.SEVERE, "Error during beginning transaction.", e);
                throw e;
            }
        }
    }

    /**
     * Commits a transaction. If no transaction is opened, then this method will
     * do nothing.
     *
     * @throws Exception if exception occurred during transaction committing
     */
    public static void commitTransaction() throws Exception {
        EntityTransaction tx = getTransaction();
        if (tx.isActive()) {
            try {
                tx.commit();
            } catch (PersistenceException e) {
                log.log(Level.WARNING, "Possible error during commiting transaction.", e);
                throw e;
            }
        }
    }

    /**
     * Rollbacks a transaction.
     */
    public static void rollbackTransaction() {
        EntityTransaction tx = getTransaction();
        if (tx.isActive()) {
            try {
                tx.rollback();
            } catch (PersistenceException e) {
                log.log(Level.SEVERE, "Error during transaction rollback.", e);
                // nothing to do if this happens so suppress exception
            }
        }
    }

    /**
     * Commits a transaction. If no transaction is opened, then this method will
     * do nothing. If any error occurs during transaction committing then this
     * method will rollback.
     *
     * @throws Exception if exception occurred during transaction committing
     */
    public static void commitAndCloseTransaction() throws Exception {
        try {
            commitTransaction();
            closeEntityManager();
        } catch (Exception e) {
            rollbackTransaction();
            closeEntityManager();
            // rethrow dao exception
            throw e;
        }
    }

}
