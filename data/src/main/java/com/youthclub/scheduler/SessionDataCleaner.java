package com.youthclub.scheduler;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.Session;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;

/**
 * @author Frank
 */
@Singleton
@Startup
public class SessionDataCleaner {

    @Schedule(hour = "1", persistent = false)
    public void run() {
        final EntityManager entityManager = LookUp.getEntityManager();
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        final List<Session> sessions = entityManager.createNamedQuery("Session.beforeDate")
                .setParameter("date", calendar.getTime())
                .getResultList();
        for (final Session session : sessions) {
            entityManager.remove(session);
        }
        entityManager.flush();
    }
}
