

import me.proggy.proggywebservices.DbManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("posizioni")
public class getPosisioni {
    /**
     * REST Web Service
     *
     * @author Broch Mattia
     */

        @Context
        private UriInfo context;

        public getPosisioni() {
        }

        /**
         * Permette di visualizzare tutte le posizioni di una scheda passando tramite get il suo id
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

            sql = " SELECT latitudine, longitudine, ora FROM posizione WHERE idScheda =?";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
                statement.setInt(1, idScheda);

                StringBuilder ris;
                try (ResultSet result = statement.executeQuery()) {
                    ris = new StringBuilder();
                    ris.append("<posizioni>");
                    while (result.next()) {
                        ris.append("<posizione>");
                        ris.append("<latitudine>").append(result.getInt(1)).append("</latitudine>");
                        ris.append("<longitudine>").append(result.getInt(2)).append("</longitudine>");
                        ris.append("<ora>").append(result.getTime(3)).append("</ora>");
                        ris.append("</posizione>");
                    }
                    ris.append("</posizioni>");
                }

                return ris.toString();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new WebApplicationException("DBMS server error!", 500);
            }
        }

    }

}
