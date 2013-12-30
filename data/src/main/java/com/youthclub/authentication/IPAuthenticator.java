package com.youthclub.authentication;

import com.youthclub.model.UserLog;
import com.youthclub.persister.UserLogPersister;
import com.youthclub.model.User;
import com.youthclub.persister.UserPersister;
import com.youthclub.resource.LookUpExtension;

import java.util.Date;

/**
 * @author Frank
 */
public class IPAuthenticator extends Authenticator {

    private UserPersister userPersister = LookUpExtension.getPersister(UserPersister.class);

    private UserLogPersister userLogPersister = LookUpExtension.getPersister(UserLogPersister.class);

    @Override
    public User doAuthenticate() {
        final String ip = params.get(IP);
        final User user = userPersister.findByIp(ip);
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
        currentUser = userPersister.findByIp(ip);
        return currentUser;
    }

    @Override
    public boolean doLogout() {
        final String ip = params.get(IP);
        UserLog userLog = userLogPersister.getCurrentUserLog(ip);
        if (userLog == null) {
            return true;
        }

        return true;
    }

    public boolean updateExpiry() {
        final String ip = params.get(IP);
        final String userAgent = params.get(USER_AGENT);

        UserLog userLog = userLogPersister.getCurrentUserLog(ip);
        final Date date = new Date();
        if (userLog == null) {
            User user = userPersister.findByIp(ip);
            if (user == null) {
                return false;
            }
            userLog = new UserLog();
            userLog.setUser(user);
            userLog.setIp(ip);
            userLog.setLoginTime(date);
            userLogPersister.save(userLog);
        }
        userLog.setUserAgent(userAgent);
        userLog.setLogoutTime(date);

        return true;
    }
}
