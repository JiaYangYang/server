package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.UserRole;
import com.youthclub.model.support.RoleType;
import com.youthclub.path.EntityPaths;

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

    @Override
    protected Class<UserRole> getEntityClass() {
        return UserRole.class;
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
    public Response put(@PathParam(ID) final String id, final UserRole that) {
        return super.put(id, that);
    }

    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        return super.delete(id);
    }
}
