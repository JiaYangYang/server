package com.youthclub.servlet;

import com.youthclub.exception.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Frank
 */
public class ServletExceptionHandler extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        final Response res = ExceptionHandler.produceResponse(throwable);
        response.setContentType(MediaType.APPLICATION_JSON);
        if (res.getEntity() != null) {
            response.sendError(res.getStatus(), res.getEntity().toString());
        } else {
            response.sendError(res.getStatus(), throwable.getMessage());
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
