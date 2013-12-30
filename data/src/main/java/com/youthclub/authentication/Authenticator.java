package com.youthclub.authentication;

import com.youthclub.model.User;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import com.youthclub.lookup.GlobalDefinition;
import org.jboss.logging.Logger;

/**
 * @author Frank
 */
public abstract class Authenticator {

    public static final String IP = "ip";
    public static final String USER_AGENT = "userAgent";

    protected static final Logger log = Logger.getLogger(Authenticator.class.getName());

    protected static final int TIMEOUT = GlobalDefinition.TIMEOUT; //Minute

    protected TMap<String, String> params = new THashMap<>();

    protected User currentUser;

    public void setParam(String key, String value) {
        params.put(key, value);
    }

    protected boolean preAuthenticate() {
        return true;
    }

    protected abstract User doAuthenticate();

    protected boolean postAuthenticate() {
        return true;
    }

    public boolean authenticate() {
        if (!preAuthenticate()) {
            return false;
        }
        if ((currentUser = doAuthenticate()) == null) {
            return false;
        }
        return postAuthenticate();
    }

    public boolean hasLoggedIn() {
        return getCurrentUser() != null;
    }

    public abstract boolean updateExpiry();

    public abstract User getCurrentUser();

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    protected boolean preLogout() {
        return true;
    }

    protected abstract boolean doLogout();

    protected boolean postLogout() {
        return true;
    }

    public boolean logout() {
        if (!preLogout()) {
            return false;
        }
        if (!doLogout()) {
            return false;
        }
        currentUser = null;
        return postLogout();
    }
}
