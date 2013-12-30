package com.youthclub.path.view;

import com.youthclub.annotation.RolesAllowed;
import com.youthclub.model.support.RoleType;
import com.youthclub.path.ViewPaths;
import com.youthclub.producer.gis.AddressViewByCoordinatesProducer;
import com.youthclub.producer.gis.AddressViewByIPProducer;
import com.youthclub.producer.gis.CoordinatesByAddressProducer;
import com.youthclub.view.AddressView;
import com.youthclub.view.CoordinatesView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Path(GeographyViewPaths.PATH)
@RolesAllowed(RoleType.ALL)
public class GeographyViewPaths extends ViewPaths {

    public static final String PATH = "geography_view";
    public static final String ADDRESS_PATH = "/address";
    public static final String MY_ADDRESS = "/my_address";
    public static final String COORDINATES_PATH = "/coordinates";
    public static final String MY_COORDINATES = "/my_coordinates";

    @Inject
    private AddressViewByCoordinatesProducer addressViewByCoordinatesProducer;

    @Inject
    private AddressViewByIPProducer addressViewByIPProducer;

    @Inject
    private CoordinatesByAddressProducer coordinatesByAddressProducer;

    private String getPreferredLanguage(List<String> acceptLanguages) {
        String ret = null;
        if (acceptLanguages != null && !acceptLanguages.isEmpty()) {
            final String str = acceptLanguages.get(0);
            ret = str.substring(0, str.indexOf(','));
        }
        return ret;
    }

    @Path(ADDRESS_PATH)
    @GET
    @Produces(APPLICATION_JSON)
    public Response getAddress(@HeaderParam("Accept-Language") List<String> acceptLanguages,
                               @QueryParam("lat") double latitude,
                               @QueryParam("lng") double longitude) throws Exception {
        final CoordinatesView coordinatesView = new CoordinatesView();
        coordinatesView.setLatitude(latitude);
        coordinatesView.setLongitude(longitude);
        final AddressView ret = addressViewByCoordinatesProducer.produce(coordinatesView, getPreferredLanguage(acceptLanguages));
        return Response.ok(ret).build();
    }

    @Path(COORDINATES_PATH)
    @GET
    @Produces(APPLICATION_JSON)
    @Consumes({APPLICATION_JSON, APPLICATION_FORM_URLENCODED})
    public Response getCoordinates(final AddressView addressView) throws Exception {
        final CoordinatesView ret = coordinatesByAddressProducer.produce(addressView);
        return Response.ok(ret).build();
    }

    @Path(MY_ADDRESS)
    @GET
    @Produces(APPLICATION_JSON)
    public Response getAddressByIP(@HeaderParam("Accept-Language") List<String> acceptLanguages,
                                   @Context HttpServletRequest request) throws Exception {
        final String ip = request.getRemoteAddr();
        final AddressView ret = addressViewByIPProducer.produce(ip);
        return Response.ok(ret).build();
    }

    @Path(MY_COORDINATES)
    @GET
    @Produces(APPLICATION_JSON)
    public Response getCoordinatesByIP(@Context HttpServletRequest request) throws Exception {
        final String ip = request.getRemoteAddr();
        final AddressView addressView = addressViewByIPProducer.produce(ip);
        if (addressView.toString().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        final CoordinatesView ret = coordinatesByAddressProducer.produce(addressView);
        return Response.ok(ret).build();
    }
}
