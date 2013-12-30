package com.youthclub.scheduler;

import com.youthclub.lookup.LookUp;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import java.util.Calendar;

/**
 * @author Frank
 */
@Singleton
@Startup
public class SessionDataCleaner {

    @PostConstruct
    public void run() {
        final EntityManager entityManager = LookUp.getEntityManager();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        entityManager.createNamedQuery("Session.beforeDate")
                .setParameter("date", calendar.getTime())
                .executeUpdate();
    }
}
