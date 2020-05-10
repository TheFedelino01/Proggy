/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saccani;

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
import me.proggy.proggywebservices.DbManager;

/**
 * REST Web Service per Dissociare una scheda in uso
 *
 * @author Saccani Federico
 */
@Path("devices/dissocia")
public class DevicesDissocia {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesDissocia
     */
    public DevicesDissocia() {
    }

    /**
     * Permette di impostare la data e l'ora di fine utilizzo della scheda specificata
     * La data e l'ora impostata per la fine corrisponde al current timestamp
     * 
     * @param idScheda la scheda da dissociare
     * @return xml con il risultato dell'operazione
     */
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    public String getXmlScheda(@QueryParam("idScheda") int idScheda) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }


        String sql = "UPDATE `utilizza` SET `dataOraFine`=CURRENT_TIMESTAMP WHERE idScheda=? and dataOraFine is null";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idScheda);
            
            int i = statement.executeUpdate();
            if (i > 0) {
                //throw new WebApplicationException("Success", 200); Non visualizza niente
                return "<result>200</result>";
            } else {
                //throw new WebApplicationException("Wrong parameters", 406); Non visualizza niente
                return "<result>"
                        + "<status>406</status>"
                        + "<errore>Parametri inseriti errati</errore>"
                        + "<errore>La scheda selezionata non puó essere dissociata in quanto non é attiva una sessione</errore>"
                        + "</result>";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Wrong parameters", 406);
        }
    }

    
    /**
     * Permette di impostare la data e l'ora di fine utilizzo della scheda specificata
     * 
     * @param idScheda la scheda da dissociare
     * @param dataOra la data e l'ora di quanto la scheda é stata dissociata (esempio: /api/devices/dissocia/1/2012-07-10%2014:58:00.000000) 
     * @return xml con il risultato dell'operazione
     */
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    public String getXmlScheda(@QueryParam("idScheda") int idScheda,@QueryParam("dataOra") String dataOra) {
        final DbManager db = DbManager.getInstance();
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }


        String sql = "UPDATE `utilizza` SET `dataOraFine`=? WHERE idScheda=? and dataOraFine is null";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            statement.setString(1, dataOra);
            statement.setInt(2, idScheda);
            
            int i = statement.executeUpdate();
            if (i > 0) {
                //throw new WebApplicationException("Success", 200); Non visualizza niente
                return "<result>200</result>";
            } else {
                //throw new WebApplicationException("Wrong parameters", 406); Non visualizza niente
                return "<result>"
                        + "<status>406</status>"
                        + "<errore>Parametri inseriti errati</errore>"
                        + "<errore>La scheda selezionata non puó essere dissociata in quanto non é attiva una sessione</errore>"
                        + "</result>";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Wrong parameters", 406);
        }
    }
   

}
