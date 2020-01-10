/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author broch_mattia
 */
public class dbManager {

    static Connection connection = null;

    public dbManager() {

    }

    public static void createConnection() {
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

    public static void writeOnDb(String query) {
        if (connection != null) {
            try {
                query = "INSERT INTO `gps` (`latitudine`, `longitudine`) VALUES ('1', '1');";
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                st.executeUpdate(query);
                System.out.println("Query eseguita correttamente");

            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createConnection();
            //todo mettere metodo anche qua
        }

    }
}
