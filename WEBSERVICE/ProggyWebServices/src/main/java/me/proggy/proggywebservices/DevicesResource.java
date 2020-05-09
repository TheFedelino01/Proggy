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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("devices")
public class DevicesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesResource
     */
    public DevicesResource() {
    }

    /**
     * Consente di avere l’elenco dei clienti che hanno utilizzato una determinata scheda
     *
     * @param idScheda id della scheda
     * @return xml con le informazioni richieste
     */
    @GET
    @Path("{idScheda}")
    @Produces(MediaType.APPLICATION_XML)
    public String getXmlScheda(@PathParam(value = "idScheda") int idScheda) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }

        //controllo se la scheda esiste
        String sql = "SELECT count(*) FROM scheda " +
                "WHERE scheda.cod = ?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                if (result.getInt(1) == 0)
                    throw new WebApplicationException("Scheda inesistente", 404);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }

        sql = "SELECT cliente.id, nome, cognome, dataOraInizio, dataOraFine FROM cliente " +
                "INNER JOIN utilizza ON (cliente.id = utilizza.idCliente) " +
                "WHERE utilizza.idScheda = ?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);

            StringBuilder ris;
            try (ResultSet result = statement.executeQuery()) {
                ris = new StringBuilder();
                ris.append("<clienti>");
                while (result.next()) {
                    ris.append("<cliente>");
                    ris.append("<id>").append(result.getInt(1)).append("</id>");
                    ris.append("<nome>").append(result.getString(2)).append("</nome>");
                    ris.append("<cognome>").append(result.getString(3)).append("</cognome>");
                    ris.append("<dataOraInizio>").append(result.getString(4)).append("</dataOraInizio>");
                    ris.append("<dataOraFine>").append(result.getString(5)).append("</dataOraFine>");
                    ris.append("</cliente>");
                }
                ris.append("</clienti>");
            }

            return ris.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }
    }



    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void modificaScheda(String content) {
        //TODO
    }


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void creaScheda(String content) {
        //TODO
    }

}
