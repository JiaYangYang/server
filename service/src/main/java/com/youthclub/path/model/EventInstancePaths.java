package com.youthclub.path.model;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.authentication.Authenticator;
import com.youthclub.lookup.LookUp;
import com.youthclub.model.EventInstance;
import com.youthclub.model.support.RoleType;
import com.youthclub.path.EntityPaths;
import com.youthclub.resource.LookUpExtension;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(EventInstancePaths.PATH)
@RolesAllowed(RoleType.ALL)
public class EventInstancePaths extends EntityPaths<EventInstance> {

    private static final Logger log = Logger.getLogger(EventInstancePaths.class.getName());

    public static final String PATH = "event_instance";
    public static final String ACTIVE = "active";

    @Override
    protected Class<EventInstance> getEntityClass() {
        return EventInstance.class;
    }

    @GET
    @Path(ACTIVE)
    @Produces(APPLICATION_JSON)
    public Response active() {
        return Response.ok(
                LookUp.getEntityManager()
                        .createNamedQuery("EventInstance.active")
                        .getResultList()
        ).build();
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
    public Response post(@FormParam("data") String serializable) {
        final EventInstance that = new EventInstance();
        final Authenticator authenticator = LookUpExtension.getResourceHolder().getAuthenticator();
        that.setCreated(new Date());
        that.setUser(authenticator.getCurrentUser());
        that.setSerializable(serializable);
        return super.post(that);
    }

    @GET
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response get(@PathParam(ID) final String id) {
        return super.get(id);
    }

    @RolesAllowed(RoleType.ADMIN)
    @PUT
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response put(@PathParam(ID) final String id, final EventInstance that) {
        return super.put(id, that);
    }

    @RolesAllowed(RoleType.ADMIN)
    @DELETE
    @Path(BY_ID)
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam(ID) final String id) {
        EventInstance that = getById(id);
        if (that != null && that.getDisabled() == null) {
            that.setDisabled(new Date());
            return Response.ok(that.getId()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
