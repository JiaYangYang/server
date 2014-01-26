package com.youthclub.session;

import com.youthclub.authentication.Authenticator;
import com.youthclub.authentication.PasswordAuthenticator;
import com.youthclub.lookup.GlobalDefinition;
import com.youthclub.resource.LookUpExtension;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(SessionPaths.SESSION_PATH)
public class SessionPaths {

    private static final Logger log = Logger.getLogger(SessionPaths.class.getName());

    public static final String SESSION_PATH = "session";
    public static final String LOGIN_PATH = "login";
    public static final String LOGOUT_PATH = "logout";
    public static final String STATUS_PATH = "status";

    @POST
    @Path(LOGIN_PATH)
    public Response login(@FormParam("username") final String username,
                          @FormParam("password") final String password) {
        final Authenticator authenticator = LookUpExtension.getResourceHolder().getAuthenticator();
        authenticator.setParam(PasswordAuthenticator.USERNAME, username);
        authenticator.setParam(PasswordAuthenticator.PASSWORD, password);

        if (authenticator.hasLoggedIn()) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        final String sessionId = UUID.randomUUID().toString();
        authenticator.setParam(PasswordAuthenticator.SESSION_ID, sessionId);

        if (authenticator.authenticate()) {
            log.log(Level.WARNING, "User {0} logged in.", username);
            return Response.ok().cookie(new NewCookie(GlobalDefinition.SESSION_COOKIE_NAME, sessionId, "/", null, -1, null, -1, false)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @NoCache
    @Produces(APPLICATION_JSON)
    @Path(LOGOUT_PATH)
    public Response logout() {
        final Authenticator authenticator = LookUpExtension.getResourceHolder().getAuthenticator();
        return Response.ok(authenticator.logout())
                .cookie(new NewCookie(GlobalDefinition.SESSION_COOKIE_NAME, "0", "/", null, -1, null, -1, false))
                .build();
    }

    @GET
    @Path(STATUS_PATH)
    @Produces(APPLICATION_JSON)
    @NoCache
    public Response status() {
        final Authenticator authenticator = LookUpExtension.getResourceHolder().getAuthenticator();
        return Response.ok(authenticator != null && authenticator.hasLoggedIn()).build();
    }
}
