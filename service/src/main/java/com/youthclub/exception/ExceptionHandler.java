package com.youthclub.exception;

import org.hibernate.JDBCException;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.jboss.resteasy.spi.UnauthorizedException;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger log = Logger.getLogger(ExceptionHandler.class.getName());

    @Override
    public Response toResponse(Throwable throwable) {
        return produceResponse(throwable);
    }

    public static Response produceResponse(final Throwable throwable) {
        if (throwable instanceof NotFoundException) {
            return handle((NotFoundException) throwable);
        } else if (throwable instanceof NotAllowedException) {
            return handle((NotAllowedException) throwable);
        } else if (throwable instanceof ForbiddenException) {
            return handle((ForbiddenException) throwable);
        } else if (throwable instanceof DefaultOptionsMethodException) {
            return handle((DefaultOptionsMethodException) throwable);
        } else if (throwable instanceof UnauthorizedException) {
            return handle((UnauthorizedException) throwable);
        } else if (throwable instanceof JDBCException) {
            return handle((JDBCException) throwable);
        } else if (throwable instanceof EJBTransactionRolledbackException) {
            return handle((EJBTransactionRolledbackException) throwable);
        } else if (throwable instanceof IllegalStateException) {
            return handle((IllegalStateException) throwable);
        } else if (throwable instanceof IllegalArgumentException) {
            return handle((IllegalArgumentException) throwable);
        } else {
            log.log(Level.SEVERE, throwable.getMessage(), throwable);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private static Response handle(final IllegalArgumentException ex) {
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(ex.getMessage()).build();
    }

    private static Response handle(final IllegalStateException ex) {
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(ex.getMessage()).build();
    }

    private static Response handle(final JDBCException ex) {
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(ex.getMessage()).build();
    }

    private static Response handle(final EJBTransactionRolledbackException ex) {

        return Response.status(Response.Status.EXPECTATION_FAILED).entity(ex.getCausedByException().getMessage()).build();
    }

    private static Response handle(final UnauthorizedException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private static Response handle(final DefaultOptionsMethodException ex) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private static Response handle(final NotAllowedException ex) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private static Response handle(final ForbiddenException ex) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private static Response handle(final NotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
