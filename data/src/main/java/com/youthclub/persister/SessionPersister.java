package com.youthclub.persister;

import com.youthclub.model.Session;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Date;

/**
 * @author Frank
 */
@Stateless
@LocalBean
public class SessionPersister extends AbstractPersister<Session> {

    public SessionPersister() {
        super(Session.class);
    }

    public Session getCurrentSession(String ip, String sessionId) {
        try {
            return entityManager
                    .createNamedQuery("Session.currentSession", Session.class)
                    .setParameter("sessionId", sessionId)
                    .setParameter("ip", ip)
                    .setParameter("expireTime", new Date())
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
