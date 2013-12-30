package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.UserRole;
import com.youthclub.model.support.RoleType;
import com.youthclub.path.EntityPaths;
import com.youthclub.persister.AbstractPersister;
import com.youthclub.persister.UserRolePersister;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(UserRolePaths.PATH)
@RolesAllowed(RoleType.ALL)
public class UserRolePaths extends EntityPaths<UserRole> {

    private static final Logger log = Logger.getLogger(UserRolePaths.class.getName());

    public static final String PATH = "user_role";

    @Inject
    private UserRolePersister userRolePersister;

    @Override
    protected AbstractPersister<UserRole> getPersister() {
        return userRolePersister;
    }

    @GET
    @Path(ROOT)
    @Produces(APPLICATION_JSON)
    public Response get() {
        return super.get();
    }

    @POST
    @Path(ROOT)
    @Produces(APPLICATION_JSON)
    @Consumes({APPLICATION_JSON, APPLICATION_FORM_URLENCODED})
    public Response post(final UserRole that) {
        return super.post(that);
    }

    @GET
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response get(@PathParam(ID) final String id) {
        return super.get(id);
    }

    @PUT
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response get(@PathParam(ID) final String id, final UserRole that) {
        return super.get(id, that);
    }

    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        return super.delete(id);
    }
}
