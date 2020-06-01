/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database
 *
 * @author Giacomo Orsenigo
 */
public class DbManager {

    private static DbManager INSTANCE;

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbms_url = "jdbc:mysql://localhost/";
    private static final String database = "id12710500_personalsafety_new";
    private static final String user = "root";
    private static final String password = "";
    private Connection connection;
    private boolean connected;

    private DbManager() {
        connect();
    }

    private void connect() {
        String url = dbms_url + database + "?serverTimezone=Europe/Rome";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            connected = true;
        } catch (SQLException | ClassNotFoundException e) {
            connected = false;
            e.printStackTrace();
        }
        System.out.println("DB connesso: " + connected);
    }

    public static synchronized DbManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbManager();
        } else if (!INSTANCE.connected) {
            INSTANCE.connect();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        try {
            if (!connection.isValid(0))
                connected = false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connected;
    }

}
