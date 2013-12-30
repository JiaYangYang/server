package com.youthclub.authentication;

import com.youthclub.model.UserLog;
import com.youthclub.persister.UserLogPersister;
import com.youthclub.model.Session;
import com.youthclub.model.User;
import com.youthclub.persister.SessionPersister;
import com.youthclub.persister.UserPersister;
import com.youthclub.resource.LookUpExtension;
import com.youthclub.util.Encryption;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Frank
 */
public class PasswordAuthenticator extends Authenticator {

    private UserPersister userPersister = LookUpExtension.getPersister(UserPersister.class);

    private UserLogPersister userLogPersister = LookUpExtension.getPersister(UserLogPersister.class);

    private SessionPersister sessionPersister = LookUpExtension.getPersister(SessionPersister.class);


    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SESSION_ID = "sessionId";

    @Override
    public User doAuthenticate() {
        final Calendar calendar = Calendar.getInstance();
        final String username = params.get(USERNAME);
        final String password = params.get(PASSWORD);
        final String ip = params.get(IP);
        final String sessionId = params.get(SESSION_ID);
        final String userAgent = params.get(USER_AGENT);

        final User user = userPersister.login(username, Encryption.encryptPassword(username, password));
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

        userLogPersister.save(userLog);
        sessionPersister.save(session);

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

        Session session = sessionPersister.getCurrentSession(ip, sessionId);
        currentUser = session == null ? null : session.getUser();
        return currentUser;
    }

    @Override
    public boolean doLogout() {
        final String ip = params.get(IP);
        final String sessionId = params.get(SESSION_ID);

        if (sessionId == null) {
            return true;
        }
        Session session = sessionPersister.getCurrentSession(ip, sessionId);
        UserLog userLog = userLogPersister.getCurrentUserLog(session.getUser(), ip);

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
        Session session = sessionPersister.getCurrentSession(ip, sessionId);
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
