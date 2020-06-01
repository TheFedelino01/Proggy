/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.orsenigo;

import me.proggy.proggywebservices.utils.XMLValidator;
import proggy.Database.DbManager;
import me.proggy.proggywebservices.Role;
import me.proggy.proggywebservices.Secured;
import me.proggy.proggywebservices.utils.XMLUtils;
import org.w3c.dom.Element;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * REST Web Service
 * Rappresenta le schede in dotazione ai vari enti
 *
 * @author Giacomo Orsenigo
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
            throw new InternalServerErrorException("DB non connesso!");
        }

        //controllo se la scheda esiste
        String sql = "SELECT count(*) FROM scheda " +
                "WHERE scheda.cod = ?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                if (result.getInt(1) == 0)
                    throw new NotFoundException("Scheda inesistente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("DBMS server error!");
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
            throw new InternalServerErrorException("DBMS server error!");
        }
    }

    /**
     * Consente di aggiungere una nuove scheda
     *
     * @param content xml contenente l'id della scheda e l'id dell'ente
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Secured({Role.ADMIN})
    public Response creaScheda(String content, @Context UriInfo uriInfo) throws URISyntaxException, IOException {
        final URL res = getClass().getClassLoader().getResource("xsd/scheda.xsd");
        final File xsd = Paths.get(res.toURI()).toFile();
        if (!XMLValidator.validate(xsd, content))
            throw new BadRequestException("Parametri non validi!"); //Più corretto rispetto al 406

        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new InternalServerErrorException("DB non connesso!");
        }

        final Element root = XMLUtils.loadXmlFromString(content);
        final int idScheda = Integer.parseInt(XMLUtils.getTextValue(root, "idScheda"));
        final int idEnte = Integer.parseInt(XMLUtils.getTextValue(root, "idEnte"));

        String sql = "SELECT * FROM scheda " +
                "WHERE cod = ?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    if (result.getInt("idEnte") != idEnte)
                        throw new ClientErrorException("Scheda già registrata da un altro ente", Response.Status.CONFLICT);
                    else
                        return Response.ok().build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("DBMS server error!");
        }


        sql = "INSERT INTO scheda(cod,idEnte) VALUES (?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            statement.setInt(2, idEnte);
            int affectedRows = statement.executeUpdate();
            // check the affected rows
            if (affectedRows <= 0) {
                throw new InternalServerErrorException("Non aggiunto");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException("DBMS server error!");
        }
        return Response.created(uriInfo.getAbsolutePathBuilder().path(Integer.toString(idScheda)).build()).build();
    }

    /**
     * Consente di eliminare una scheda
     *
     * @param idScheda id della scehda da eliminare
     */
    @DELETE
    @Path("{idScheda}")
    @Secured({Role.ADMIN})
    public void eliminaScheda(@PathParam(value = "idScheda") int idScheda) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new InternalServerErrorException("DB non connesso!");
        }

        //controllo se la scheda esiste
        String sql = "DELETE FROM scheda " +
                "WHERE cod = ?";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            final int rows = statement.executeUpdate();
            if (rows == 0)
                throw new NotFoundException("Scheda inesistente");
            else if (rows > 1)
                throw new InternalServerErrorException("S'è rotto tutto");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("DBMS server error!");
        }
    }

}
