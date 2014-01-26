package com.youthclub.authentication;

import com.youthclub.lookup.LookUp;
import com.youthclub.model.Session;
import com.youthclub.model.User;
import com.youthclub.model.UserLog;
import com.youthclub.util.Encryption;

import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Frank
 */
public class PasswordAuthenticator extends Authenticator {


    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SESSION_ID = "sessionId";

    private User login(String username, String password) {
        try {
            return LookUp.getEntityManager()
                    .createNamedQuery("User.login", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private Session getCurrentSession(String ip, String sessionId) {
        try {
            return LookUp.getEntityManager()
                    .createNamedQuery("Session.currentSession", Session.class)
                    .setParameter("sessionId", sessionId)
                    .setParameter("ip", ip)
                    .setParameter("expireTime", new Date())
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User doAuthenticate() {
        final Calendar calendar = Calendar.getInstance();
        final String username = params.get(USERNAME);
        final String password = params.get(PASSWORD);
        final String ip = params.get(IP);
        final String sessionId = params.get(SESSION_ID);
        final String userAgent = params.get(USER_AGENT);

        final User user = login(username, Encryption.encryptPassword(username, password));
        if (user == null) {
            return null;
        }
        final Session session = new Session();
        session.setIp(ip);
        session.setSessionId(sessionId);
        session.setLastRequest(calendar.getTime());
        session.setStartTime(calendar.getTime());
        calendar.add(Calendar.MINUTE, TIMEOUT);
        session.setExpireTime(calendar.getTime());
        session.setUser(user);

        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setLoginTime(calendar.getTime());
        userLog.setIp(ip);
        userLog.setUserAgent(userAgent);

        LookUp.getEntityManager().persist(userLog);
        LookUp.getEntityManager().persist(session);

        return user;
    }

    @Override
    public User getCurrentUser() {
        if (currentUser != null) {
            return currentUser;
        }
        final String ip = params.get(IP);
        final String sessionId = params.get(SESSION_ID);
        if (sessionId == null) {
            return null;
        }

        Session session = getCurrentSession(ip, sessionId);
        currentUser = session == null ? null : session.getUser();
        return currentUser;
    }

    private UserLog getCurrentUserLog(User user, String ip) {
        try {
            return LookUp.getEntityManager()
                    .createNamedQuery("UserLog.withUserAndIp", UserLog.class)
                    .setParameter("user", user)
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
        final String sessionId = params.get(SESSION_ID);

        if (sessionId == null) {
            return true;
        }
        Session session = getCurrentSession(ip, sessionId);
        UserLog userLog = getCurrentUserLog(session.getUser(), ip);

        if (session == null || userLog == null) {
            return true;
        }

        final Date date = new Date();
        session.setExpireTime(date);
        userLog.setLogoutTime(date);

        return true;
    }

    public boolean updateExpiry() {
        final String ip = params.get(IP);
        final String sessionId = params.get(SESSION_ID);

        if (sessionId == null) {
            return false;
        }
        Session session = getCurrentSession(ip, sessionId);
        if (session == null) {
            return false;
        }

        final Calendar calendar = Calendar.getInstance();
        session.setLastRequest(calendar.getTime());
        calendar.add(Calendar.MINUTE, TIMEOUT);
        session.setExpireTime(calendar.getTime());

        return true;
    }
}
