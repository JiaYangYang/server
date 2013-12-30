package com.youthclub.resource;

import com.youthclub.authentication.Authenticator;

import javax.ejb.Singleton;

/**
 * @author Frank
 */
@Singleton
public class ResourceHolder {

    private ThreadLocal<Authenticator> authenticator = new ThreadLocal<>();

    public Authenticator getAuthenticator() {
        return authenticator.get();
    }

    public void setAuthenticator(Authenticator auth) {
        authenticator.set(auth);
    }

    public void removeAuthenticator() {
        authenticator.remove();
    }
}
