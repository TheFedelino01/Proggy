/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.broch;

import proggy.DbManager;
import me.proggy.proggywebservices.Role;
import me.proggy.proggywebservices.Secured;
import me.proggy.proggywebservices.utils.XMLUtils;
import org.w3c.dom.Element;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.*;

/**
 * REST Aggiunta di una nuova posizione
 * @desc Permette di aggiungere una nuova posizione in cui si è trovato un dispositivo
 * 
 * @author Broch Mattia
 */
@Path("salvaPosizione")
public class salvaPosizione {

    @Context
    private UriInfo context;

    public salvaPosizione() {
    }

    /**
     *
     * @param idScheda id della scheda
     * @param posizione posizione della scheda
     * @param ora  ora della scheda in quella posizione
     * @return xml con il risultato dell'operazione
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes("application/xml")
    @Secured({Role.ADMIN})
    public String salavPosizione(String content) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }

        final Element root = XMLUtils.loadXmlFromString(content);

        long id = -1;
        final String sql = "INSERT INTO posizione(latitudine,longitudine,dataOra,idScheda) VALUES (?,?,?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, XMLUtils.getTextValue(root, "latitudine"));
            statement.setString(2, XMLUtils.getTextValue(root, "longitudine"));
            statement.setString(3, XMLUtils.getTextValue(root, "dataOra"));
            statement.setString(4, XMLUtils.getTextValue(root, "idScheda"));
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
        return "<result>200</result>";
    }

}
