package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.EventType;
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
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(EventTypePaths.PATH)
@RolesAllowed(RoleType.ALL)
public class EventTypePaths extends EntityPaths<EventType> {

    private static final Logger log = Logger.getLogger(EventTypePaths.class.getName());

    public static final String PATH = "event_type";

    @Override
    protected Class<EventType> getEntityClass() {
        return EventType.class;
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
    public Response post(final EventType that) {
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
    public Response put(@PathParam(ID) final String id, final EventType that) {
        return super.put(id, that);
    }

    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        EventType that = getById(id);
        if (that != null) {
            that.setDisabled(new Date());
            return Response.ok(that.getId()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
