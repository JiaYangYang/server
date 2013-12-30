package com.youthclub.resource;

import com.youthclub.persister.AbstractPersister;
import org.jboss.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Frank
 */
public class LookUpExtension {

    private static final Logger log = Logger.getLogger(LookUpExtension.class.getCanonicalName());

    public static ResourceHolder getResourceHolder() {
        try {
            return InitialContext.doLookup("java:app/data/ResourceHolder");
        } catch (NamingException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
            return null;
        }
    }

    public static <T> T getPersister(Class<? extends AbstractPersister> clazz) {
        return getPersister(clazz.getSimpleName());
    }

    public static <T> T getPersister(String clazzName) {
        try {
            return InitialContext.doLookup("java:app/data/" + clazzName);
        } catch (NamingException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
            return null;
        }
    }
}
