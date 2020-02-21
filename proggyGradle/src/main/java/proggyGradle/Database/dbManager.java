/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.Database;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author broch_mattia
 */
public class dbManager {

    private static dbManager istance = null; // riferimento all' istanza

    static Connection connection = null;
    private String dbName="";

    private dbManager() {
    }

    public static dbManager getIstance() {
        if (istance == null) {
            istance = new dbManager();
        }
        return istance;
    }
    
    public String getChatId(String identificatore){
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                String query="SELECT idTelegram FROM dispositivi INNER JOIN utenti ON dispositivi.usernameUtente=utenti.username WHERE dispositivi.id='"+identificatore+"';";
                ResultSet rs =st.executeQuery(query);
                while (rs.next()) {
                    return rs.getString("idTelegram");
                }

            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            System.out.println("Crea la connessione prima");
            //todo mettere metodo anche qua
        }

        return "";
        
    }
    
    public void createConnection(String dbName) {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);

            String serverName = "localhost";
            String mydatabase = dbName;
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "";
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connessione al database risucita");
            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeOnDb(String query) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                st.executeUpdate(query);
                //System.out.println("Query eseguita correttamente");

            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Crea la connessione prima");
            //todo mettere metodo anche qua
        }

    }

    public String getFromDb(String query) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                ResultSet rs =st.executeQuery(query);
                while (rs.next()) {
                    return "OK";
                }

            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            System.out.println("Crea la connessione prima");
            //todo mettere metodo anche qua
        }

        return "";
    }

    public void scriviDB(String query)
    {
        try {
                        // open a connection to the site
                        URL url = new URL("https://personalsafety.altervista.org/receiveData.php");
                        URLConnection con = url.openConnection();
                        // activate the output
                        con.setDoOutput(true);
                        PrintStream ps = new PrintStream(con.getOutputStream());
                        // send your parameters to your site
                        ps.print("cod=" + query);

                        // we have to get the input stream in order to actually send the
                        // request
                        System.out.println(new String(con.getInputStream().readAllBytes()));

                        // close the print stream
                        ps.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }


}
