/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import me.proggy.proggywebservices.utils.XMLUtils;
import me.proggy.proggywebservices.utils.XMLValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * REST Web Service
 *
 * @author giaco
 */
@Path("contatti")
public class ContattiResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ContattiResource
     */
    public ContattiResource() {
    }

    /**
     * Consente di avere l’elenco di tutti i contatti che sono avvenuti tra i clienti
     *
     * @return xml con le informazioni richieste
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }

        final String sql = "SELECT cliente1.id, cliente1.nome, cliente1.cognome, " +
                " cliente2.id, cliente2.nome, cliente2.cognome, collisione.* " +
                "FROM collisione " +
                "INNER JOIN utilizza AS utilizza1 ON (utilizza1.idScheda = collisione.idScheda) " +
                "INNER JOIN utilizza AS utilizza2 ON (utilizza2.idScheda = collisione.idEstraneo) " +
                "INNER JOIN cliente AS cliente1 ON (utilizza1.idCliente = cliente1.id) " +
                "INNER JOIN cliente AS cliente2 ON (utilizza2.idCliente = cliente2.id) " +
                "WHERE (collisione.dataOra > utilizza1.dataOraInizio AND (collisione.dataOra < utilizza1.dataOraFine OR utilizza1.dataOraFine IS NULL)) " +
                "AND (collisione.dataOra > utilizza2.dataOraInizio AND (collisione.dataOra < utilizza2.dataOraFine OR utilizza2.dataOraFine IS NULL))";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {

            StringBuilder ris;
            try (ResultSet result = statement.executeQuery()) {
                ris = new StringBuilder();
                ris.append("<contatti>");
                while (result.next()) {
                    ris.append("<contatto>");

                    ris.append("<cliente1>");
                    ris.append("<id>").append(result.getInt(1)).append("</id>");
                    ris.append("<nome>").append(result.getString(2)).append("</nome>");
                    ris.append("<cognome>").append(result.getString(3)).append("</cognome>");
                    ris.append("</cliente1>");
                    ris.append("<cliente2>");
                    ris.append("<id>").append(result.getInt(4)).append("</id>");
                    ris.append("<nome>").append(result.getString(5)).append("</nome>");
                    ris.append("<cognome>").append(result.getString(6)).append("</cognome>");
                    ris.append("</cliente2>");

                    ris.append("<dataOra>").append(result.getString("dataOra")).append("</dataOra>");
                    ris.append("<latitudine>").append(result.getString("latitudine")).append("</latitudine>");
                    ris.append("<longitudine>").append(result.getString("longitudine")).append("</longitudine>");
                    ris.append("</contatto>");
                }
                ris.append("</contatti>");
            }

            return ris.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }
    }

    /**
     * Consente di aggiungere un contatto avvenuto tra due persone, nel caso si fossero trovate troppo vicine
     *
     * @param content xml
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String creaContatto(String content) throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        final URL res = getClass().getClassLoader().getResource("xsd/contatto.xsd");
        final File xsd = Paths.get(res.toURI()).toFile();
        if (!XMLValidator.validate(xsd, content))
            throw new WebApplicationException("Parametri non validi!", 406);

        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }

        final Element root = XMLUtils.loadXmlFromString(content);

        long id = -1;
        final String sql = "INSERT INTO collisione(idScheda,idEstraneo,dataOra,latitudine,longitudine) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, XMLUtils.getTextValue(root, "idScheda"));
            statement.setString(2, XMLUtils.getTextValue(root, "idEstraneo"));
            statement.setString(3, XMLUtils.getTextValue(root, "dataOra"));
            statement.setString(4, XMLUtils.getTextValue(root, "latitudine"));
            statement.setString(5, XMLUtils.getTextValue(root, "longitudine"));
            int affectedRows = statement.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new WebApplicationException("DBMS server error!", 500);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }
        return "<idContatto>" + id + "</idContatto>";

    }
}
