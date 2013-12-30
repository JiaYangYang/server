package com.youthclub.persister;

import com.youthclub.model.User;
import com.youthclub.model.UserLog;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * @author Frank
 */
@Stateless
@LocalBean
public class UserLogPersister extends AbstractPersister<UserLog> {

    public UserLogPersister() {
        super(UserLog.class);
    }

    public UserLog getCurrentUserLog(String ip) {
        try {
            return entityManager
                    .createNamedQuery("UserLog.withIp", UserLog.class)
                    .setParameter("ip", ip)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserLog getCurrentUserLog(User user, String ip) {
        try {
            return entityManager
                    .createNamedQuery("UserLog.withUserAndIp", UserLog.class)
                    .setParameter("user", user)
                    .setParameter("ip", ip)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
