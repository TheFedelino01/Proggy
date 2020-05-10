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
import javax.xml.parsers.ParserConfigurationException;
import me.proggy.proggywebservices.DbManager;
import me.proggy.proggywebservices.utils.XMLUtils;
import org.w3c.dom.Element;

/**
 * REST Web Service per Associazione scheda con utente
 * @desc Permette di associare una scheda Personal Safety ad un profilo utente giá registrato.
 * 
 * @author Saccani Federico
 */
@Path("devices/associa")
public class DevicesAssocia {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesAssocia
     */
    public DevicesAssocia() {
    }

    /**
     * Consente di associare la scheda con uno specifico utente
     *
     * @param idScheda id della scheda da associare
     * @param idUtente id dell'utente
     * @param dataOra ]Opzionale] la data e l'ora dell'associazione (esempio: /api/devices/associa/1/1/2012-07-10%2014:58:00.000000)
     * @return xml con il risultato dell'operazione
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes("application/xml")
    public String getXmlScheda(String content) {
        final DbManager db = DbManager.getInstance();
        //@PathParam(value = "idScheda") int idScheda,@PathParam(value = "idUtente") int idUtente
        try {
            Element root = XMLUtils.loadXmlFromString(content);
            int idScheda = Integer.parseInt(root.getElementsByTagName("idScheda").item(0).getTextContent());
            int idUtente = Integer.parseInt(root.getElementsByTagName("idUtente").item(0).getTextContent());
            String dataOra="";
            java.util.Date dateObj=new java.util.Date();
            
            //Se ha specificato il parametro opzionale
            if(root.getElementsByTagName("dataOra").getLength()>0){
                dataOra = root.getElementsByTagName("dataOra").item(0).getTextContent();
                
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    dateObj = sdf.parse(dataOra);
                }catch(Exception e){
                    return "<result>406</result>";
                }
            }
            
            // verifica stato connessione a DBMS
            if (!db.isConnected()) {
                System.err.println("DB non connesso");
                throw new WebApplicationException("DB non connesso!", 500);
            }
            
            PreparedStatement statement;
            if(dataOra.isEmpty()){
                String sql = "INSERT INTO `utilizza` (`idScheda`, `idCliente`) VALUES (?,?);";
                statement = db.getConnection().prepareStatement(sql);
                statement.setInt(1, idScheda);
                statement.setInt(2, idUtente);
            }else{
                String sql = "INSERT INTO `utilizza` (`idScheda`, `idCliente`,`dataOraInizio`) VALUES (?,?,?);";
                statement = db.getConnection().prepareStatement(sql);
                statement.setInt(1, idScheda);
                statement.setInt(2, idUtente);
                statement.setTimestamp(3, new Timestamp(dateObj.getTime()));
            }
            
            int i = statement.executeUpdate();
            if (i > 0) {
                //throw new WebApplicationException("Success", 200); Non visualizza niente
                return "<result>200</result>";
            } else {
                //throw new WebApplicationException("Wrong parameters", 406); Non visualizza niente
                return "<result>"
                        + "<status>406</status>"
                        + "<errore>Parametri inseriti errati</errore>"
                        + "</result>";
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Wrong parameters", 406);
        }
    } 

}
