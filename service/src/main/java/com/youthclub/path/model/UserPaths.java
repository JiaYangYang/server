package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.User;
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
@Path(UserPaths.PATH)
@RolesAllowed(RoleType.ADMIN)
public class UserPaths extends EntityPaths<User> {

    private static final Logger log = Logger.getLogger(UserPaths.class.getName());

    public static final String PATH = "user";

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
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
    public Response post(final User that) {
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
    public Response put(@PathParam(ID) final String id, final User that) {
        return super.put(id, that);
    }

    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        return super.delete(id);
    }

    @GET
    @Path(BY_ID + FIELD_NAME)
    @Produces(APPLICATION_JSON)
    public Response getField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName) {
        return super.getField(id, fieldName);
    }

    @PUT
    @Path(BY_ID + FIELD_NAME)
    @Produces(APPLICATION_JSON)
    @Consumes({APPLICATION_JSON, APPLICATION_FORM_URLENCODED})
    public Response setField(@PathParam(ID) final String id, @PathParam(FIELD) final String fieldName, final User that) {
        return super.setField(id, fieldName, that);
    }
}
