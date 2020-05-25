/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("test")
public class TestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
    }

    /**
     * Metodo di test
     *
     * @return stringa "FUNZIA"
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return "FUNZIA";
    }


    /**
     * Metodo accessibile solo da amministratori loggati
     *
     * @return stringa con username
     */
    @GET
    @Path("/auth/admin")
    @Secured({Role.ADMIN})
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextAuthAdmin(@Context SecurityContext securityContext) {
        return "Logged: " + securityContext.getUserPrincipal().getName();
    }

    /**
     * Metodo accessibile solo da clienti loggati
     *
     * @return stringa con username
     */
    @GET
    @Path("/auth/cliente")
    @Secured({Role.CLIENTE})
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextAuth(@Context SecurityContext securityContext) {
        return "Logged: " + securityContext.getUserPrincipal().getName();
    }


}
