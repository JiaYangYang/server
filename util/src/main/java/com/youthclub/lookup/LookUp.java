package com.youthclub.lookup;

import org.jboss.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * @author Frank
 */
public class LookUp {

    private static final Logger log = Logger.getLogger(LookUp.class.getCanonicalName());

    public static UserTransaction getUserTransaction() {
        try {
            return InitialContext.doLookup("java:comp/UserTransaction");
        } catch (NamingException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
            return null;
        }

    }

    public static EntityManager getEntityManager() {
        try {
            return InitialContext.doLookup("java:/MainEntityManager");
        } catch (NamingException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
            return null;
        }
    }
}
