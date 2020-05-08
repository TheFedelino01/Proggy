/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
     * Esempio di richiesta GET (ritorna elenco schede)
     *
     * @return stringa xml con elenco delle schede
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getText() {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }

        final String sql = "SELECT * FROM scheda";
        try (Statement statement = db.getConnection().createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            StringBuilder ris = new StringBuilder();
            ris.append("<scheda>");
            while (result.next()) {
                ris.append("<id>").append(result.getString(1)).append("</id>");
            }
            ris.append("</scheda>");

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

    /**
     * Esempio di richiesta POST (inserimento nuova scheda)
     *
     * @param xml id dell'ente a cui appartiene
     * @return id elemento inserito
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public String createScheda(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document document = builder.parse(is);
        final String ente = document.getElementsByTagName("idEnte").item(0).getFirstChild().getNodeValue();

        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }
        long id = -1;
        final String sql = "INSERT INTO scheda (idEnte) VALUES (?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ente);
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
        return "Inserimento effettuato. ID: " + id;

    }
}
