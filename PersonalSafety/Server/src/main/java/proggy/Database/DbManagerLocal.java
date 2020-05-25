/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author broch_mattia
 */
public class DbManagerLocal implements DbManagerInterface {

    private static DbManagerLocal instance = null; // riferimento all' istanza

    static Connection connection = null;
    private String dbName = "";

    private DbManagerLocal() {
        createConnection("my_personalsafety");
    }

    static DbManagerLocal getInstance() {
        if (instance == null) {
            instance = new DbManagerLocal();
        }
        return instance;
    }

    public String getChatId(String identificatore) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                String query = "SELECT idTelegram FROM dispositivi INNER JOIN utenti ON dispositivi.usernameUtente=utenti.username WHERE dispositivi.id='" + identificatore + "';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    return rs.getString("idTelegram");
                }

            } catch (SQLException ex) {
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String writeOnDb(String query) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                st.executeUpdate(query);
                //System.out.println("Query eseguita correttamente");

            } catch (SQLException ex) {
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Crea la connessione prima");
            //todo mettere metodo anche qua
        }
        return "done";

    }

    @Override
    public String getFromDb(String query) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();

                // execute the query, and get a java resultset
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    return "OK";
                }

            } catch (SQLException ex) {
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            System.out.println("Crea la connessione prima");
            //todo mettere metodo anche qua
        }

        return "";
    }


}
