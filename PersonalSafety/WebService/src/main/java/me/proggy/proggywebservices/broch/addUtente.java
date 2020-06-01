package me.proggy.proggywebservices.broch;

import proggy.Database.DbManager;
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
 * REST Aggiunta di una nuovo utente
 * @author Broch Mattia
 */
@Path("addUtente")
public class addUtente {

    @Context
    private UriInfo context;

    public addUtente() {
    }

    /**
     *
     * @param nome nome utente
     * @param cognome cognome utente
     * @param cf  codice fiscale dell'utente
     * @param allergie [Opzionale] allergie dell'utente
     * @param farmaci [Opzionale]  farmaci dell'utente
     * @return xml con il risultato dell'operazione
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes("application/xml")
    @Secured({Role.ADMIN})
    public String addUtente(String content) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            throw new WebApplicationException("DBMS server error!", 500);
        }

        Element root = XMLUtils.loadXmlFromString(content);
        String nome = root.getElementsByTagName("nome").item(0).getTextContent();
        String cognome = root.getElementsByTagName("cognome").item(0).getTextContent();
        String cf = root.getElementsByTagName("cf").item(0).getTextContent();
        String allergie="";
        String farmaci="";

        if (root.getElementsByTagName("allergie").getLength() > 0)
            allergie = root.getElementsByTagName("allergie").item(0).getTextContent();

        if (root.getElementsByTagName("farmaci").getLength() > 0)
            farmaci = root.getElementsByTagName("farmaci").item(0).getTextContent();

        long id = -1;
        final String sql = "INSERT INTO cliente(nome,cognome,cf,allergie, farmaci) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, XMLUtils.getTextValue(root, "nome"));
            statement.setString(2, XMLUtils.getTextValue(root, "cognome"));
            statement.setString(3, XMLUtils.getTextValue(root, "cf"));
            statement.setString(4, XMLUtils.getTextValue(root, "allergie"));
            statement.setString(5, XMLUtils.getTextValue(root, "farmaci"));
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
        return "<conferma><idUtente>"+id+"</idUtente></conferma>";
    }

}
