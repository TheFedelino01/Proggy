/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.proggy.proggywebservices.saccani;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proggy.DbManager;

/**
 * REST Web Service per Otteneres l'elenco delle schede attive in questo momento
 * Output ridotto in quanto la query in java non funziona mentre in SQL si.. (non ho capito il perché)
 *
 * @author Saccani Federico
 */
@Path("devices/attivi")
public class DevicesAttivi {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesAttivi
     */
    public DevicesAttivi() {
    }

   
    /**
     * Consente di ottenere l'elenco delle schede attualmente in uso
     * N.B. Output del metodo differente perché la query complessa manda un errore. Se eseguita su SQL invece funziona...
     * 
     * @return xml con l'elenco delle schede attive
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getSchedeAttive() {
        final DbManager db = DbManager.getInstance();

        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }

        String sql = "SELECT scheda.* " +
            "FROM utilizza join scheda on Utilizza.idScheda = scheda.cod " +
            "where utilizza.dataOraFine is null " +
            "order by utilizza.idScheda,utilizza.dataOraInizio ASC";
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            StringBuilder ris=new StringBuilder();
           
            try (ResultSet result = statement.executeQuery()) {
                
                ris.append("<result>");
                
                while (result.next()) {   
                    ris.append("<device>");
                        ris.append("<id>").append(result.getInt(1)).append("</id>");
                        ris.append("<idEnte>").append(result.getInt(2)).append("</idEnte>");
                    ris.append("</device>");
                }
                
                ris.append("</result>");
            }

            return ris.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }
    }
    
    //METODO CON QUERY COMPLESSA MA VA IN ERRORE... 
   /* @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getSchedeAttive() {
        final DbManager db = DbManager.getInstance();
        ArrayList array = new ArrayList();
       // System.err.println("SONO QUI!");
        // verifica stato connessione a DBMS
        if (!db.isConnected()) {
            System.err.println("DB non connesso");
            throw new WebApplicationException("DB non connesso!", 500);
        }


        String sql = "SELECT scheda.cod,cliente.nome,cliente.cognome,cliente.cf,cartellaclinica.allergie,cartellaclinica.fermaci,posizione.*\n" +
            "FROM (((utilizza join scheda on Utilizza.idScheda = scheda.cod) join posizione on scheda.cod=posizione.idScheda) join cliente on cliente.id = utilizza.idCliente) join cartellaclinica on cartellaclinica.idCliente=cliente.id \n" +
            "where utilizza.dataOraFine is null and posizione.dataOra>utilizza.dataOraInizio \n" +
            "order by utilizza.idScheda,utilizza.dataOraInizio ASC";
        int lastDevice=-1;
        int currentDevice;
        try (PreparedStatement statement = db.getConnection().prepareStatement(sql)) {
            StringBuilder ris=new StringBuilder();
           
            try (ResultSet result = statement.executeQuery()) {
                
                ris.append("<result>");
                
                while (result.next()) {
                    currentDevice=result.getInt(1);
                    
                    //Creo il nuovo device se ho finito di leggere le posizioni di quello scorso
                    //1 ugo posA 
                    //1 ugo posB
                    //2 pluto posC
                    if(currentDevice!=lastDevice){
                        lastDevice=currentDevice;
                        
                        if(lastDevice!=-1)
                            ris.append("</posizioni>");
                        
                        ris.append("<device>");
                            ris.append("<id>").append(result.getInt(1)).append("</id>");
                            ris.append("<nome>").append(result.getString(1)).append("</nome>");
                            ris.append("<cognome>").append(result.getString(2)).append("</cognome>");

                            ris.append("<cartellaClinica>");
                                ris.append("<CF>").append(result.getString(3)).append("</CF>");
                                ris.append("<allergie>").append(result.getString(4)).append("</allergie>");
                                ris.append("<farmaci>").append(result.getString(5)).append("</farmaci>");
                            ris.append("</cartellaClinica>");

                            ris.append("<posizioni>");
                                ris.append("<posizione>");
                                    ris.append("<latitudine>").append(result.getString(6)).append("</latitudine>");
                                    ris.append("<longitudine>").append(result.getString(7)).append("</longitudine>");
                                    ris.append("<data>").append(result.getDate(1)).append("</data>");
                                ris.append("</posizione>");
                                    
                        ris.append("</device>");
                    }else{
                        ris.append("<posizione>");
                            ris.append("<latitudine>").append(result.getString(6)).append("</latitudine>");
                            ris.append("<longitudine>").append(result.getString(7)).append("</longitudine>");
                           // ris.append("<data>").append(result.getDate(1)).append("</data>");
                        ris.append("</posizione>");
                    }
                }
                ris.append("</posizioni>");
                
                ris.append("</result>");
            }

            return ris.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("DBMS server error!", 500);
        }
    }*/


}
