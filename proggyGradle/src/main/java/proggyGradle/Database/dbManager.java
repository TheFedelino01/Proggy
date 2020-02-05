/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.Database;

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

    private dbManager() {
    }

    public static dbManager getIstance() {
        if (istance == null) {
            istance = new dbManager();
        }
        return istance;
    }
    
    public void getChatId(String identificatore){
        //"SELECT chatId FROM dispositivi WHERE dispositivi.id="+
        
    }
    
    public void createConnection() {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);

            String serverName = "localhost";
            String mydatabase = "dbproggy";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "";
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connessione risucita");
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
                System.out.println("Query eseguita correttamente");

            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createConnection();
            writeOnDb(query);
            //todo mettere metodo anche qua
        }

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
                        //System.out.println(new String(con.getInputStream().readAllBytes()));

                        // close the print stream
                        ps.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }


}
