package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.Event;
import com.youthclub.model.support.RoleType;
import com.youthclub.path.EntityPaths;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(EventPaths.PATH)
@RolesAllowed(RoleType.ALL)
public class EventPaths extends EntityPaths<Event> {

    private static final Logger log = Logger.getLogger(EventPaths.class.getName());

    public static final String PATH = "event";

    @Override
    protected Class<Event> getEntityClass() {
        return Event.class;
    }

    @RolesAllowed(RoleType.ADMIN)
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
    public Response post(final Event that) {
        that.setCreated(new Date());
        return super.post(that);
    }

    @GET
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response get(@PathParam(ID) final String id) {
        return super.get(id);
    }

    @RolesAllowed(RoleType.ADMIN)
    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        Event that = getById(id);
        if (that != null && that.getDisabled() == null) {
            that.setDisabled(new Date());
            return Response.ok(that.getId()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
