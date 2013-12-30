package com.youthclub.persister;

import com.youthclub.model.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * @author Frank
 */
@Stateless
@LocalBean
public class UserPersister extends AbstractPersister<User> {

    public UserPersister() {
        super(User.class);
    }

    public User login(String username, String password) {
        try {
            return entityManager
                    .createNamedQuery("User.login", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public User findByIp(String ip) {
        try {
            return entityManager
                    .createNamedQuery("User.findByIp", User.class)
                    .setParameter("ip", ip)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
