/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import java.sql.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("devices/associa")
public class DevicesAssocia {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesResource
     */
    public DevicesAssocia() {
    }

   
    @GET
    @Path("{idScheda}/{idUtente}")
    @Produces(MediaType.APPLICATION_XML)
    public String getXmlScheda(@PathParam(value = "idScheda") int idScheda,@PathParam(value = "idUtente") int idUtente) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }


        String sql = "INSERT INTO `utilizza` (`idScheda`, `idCliente`) VALUES (?,?);";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            statement.setInt(2, idUtente);
            
            int i = statement.executeUpdate();
            if (i > 0) {
                //throw new WebApplicationException("Success", 200); Non visualizza niente
                return "<result>200</result>";
            } else {
                //throw new WebApplicationException("Wrong parameters", 406); Non visualizza niente
                return "<result>406</result>";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Wrong parameters", 406);
        }
    }


    @GET
    @Path("{idScheda}/{idUtente}/{dataOra}")
    @Produces(MediaType.APPLICATION_XML)
    public String getXmlScheda(@PathParam(value = "idScheda") int idScheda,@PathParam(value = "idUtente") int idUtente,@PathParam(value = "dataOra") String dataOra){
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }
        
        //Esempio funzionante: /api/devices/associa/1/1/2012-07-10%2014:58:00.000000
        java.util.Date dateObj;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            dateObj = sdf.parse(dataOra);
        }catch(Exception e){
            return "<result>406</result>";
        }
        
        String sql = "INSERT INTO `utilizza` (`idScheda`, `idCliente`,`dataOraInizio`) VALUES (?,?,?);";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            statement.setInt(2, idUtente);
            statement.setTimestamp(3, new Timestamp(dateObj.getTime()));
            
            int i = statement.executeUpdate();
            if (i > 0) {
                //throw new WebApplicationException("Success", 200); Non visualizza niente
                return "<result>200</result>";
            } else {
                //throw new WebApplicationException("Wrong parameters", 406); Non visualizza niente
                return "<result>406</result>";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Wrong parameters", 406);
        }
    }

}
