package com.youthclub.authentication;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.User;
import com.youthclub.model.UserLog;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * @author Frank
 */
public class IPAuthenticator extends Authenticator {

    private User findByIp(String ip) {
        try{
        return LookUp.getEntityManager()
                .createNamedQuery("User.findByIp", User.class)
                .setParameter("ip", ip)
                .getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public User doAuthenticate() {
        final String ip = params.get(IP);
        final User user = findByIp(ip);
        if (user == null) {
            return null;
        }

        final String userAgent = params.get(USER_AGENT);
        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setLoginTime(new Date());
        userLog.setIp(ip);
        userLog.setUserAgent(userAgent);

        return user;
    }

    @Override
    public User getCurrentUser() {
        if (currentUser != null) {
            return currentUser;
        }
        final String ip = params.get(IP);
        currentUser = findByIp(ip);
        return currentUser;
    }

    private UserLog getCurrentUserLog(String ip) {
        try {
            return LookUp.getEntityManager()
                    .createNamedQuery("UserLog.withIp", UserLog.class)
                    .setParameter("ip", ip)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public boolean doLogout() {
        final String ip = params.get(IP);
        UserLog userLog = getCurrentUserLog(ip);
        if (userLog == null) {
            return true;
        }

        return true;
    }

    public boolean updateExpiry() {
        final String ip = params.get(IP);
        final String userAgent = params.get(USER_AGENT);

        UserLog userLog = getCurrentUserLog(ip);
        final Date date = new Date();
        if (userLog == null) {
            User user = findByIp(ip);
            if (user == null) {
                return false;
            }
            userLog = new UserLog();
            userLog.setUser(user);
            userLog.setIp(ip);
            userLog.setLoginTime(date);
            LookUp.getEntityManager().persist(userLog);
        }
        userLog.setUserAgent(userAgent);
        userLog.setLogoutTime(date);

        return true;
    }
}
