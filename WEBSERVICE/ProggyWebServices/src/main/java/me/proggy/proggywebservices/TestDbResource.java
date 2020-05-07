/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("testdb")
public class TestDbResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestDbResource() {
    }

    /**
     * Retrieves representation of an instance of
 me.proggy.proggywebservices.TestDbResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getText() {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }

        try {
            String sql = "SELECT * FROM scheda";
            Statement statement = db.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            StringBuilder ris = new StringBuilder();
            ris.append("<scheda>");
            while (result.next()) {
                ris.append("<id>").append(result.getString(1)).append("</id>");
            }
            ris.append("</scheda>");
            result.close();
            statement.close();

            return ris.toString();

        } catch (SQLException e) {
            throw new WebApplicationException("DBMS server error!", 500);
        }
    }

    /**
     * PUT method for updating or creating an instance of TestDbResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
}
