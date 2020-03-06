/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class DbManagerRemote implements DbManagerInterface {

    private static DbManagerRemote instance = null; // riferimento all' istanza

    private DbManagerRemote() {
    }

    public static DbManagerRemote getInstance() {
        if (instance == null) {
            instance = new DbManagerRemote();
        }
        return instance;
    }

//    public String getChatId(String identificatore) {
//        if (connection != null) {
//            try {
//                Statement st = connection.createStatement();
//
//                // execute the query, and get a java resultset
//                String query = "SELECT idTelegram FROM dispositivi INNER JOIN utenti ON dispositivi.usernameUtente=utenti.username WHERE dispositivi.id='" + identificatore + "';";
//                ResultSet rs = st.executeQuery(query);
//                while (rs.next()) {
//                    return rs.getString("idTelegram");
//                }
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Exception e) {
//                Logger.getLogger(DbManagerLocal.class.getName()).log(Level.SEVERE, null, e);
//            }
//        } else {
//            System.out.println("Crea la connessione prima");
//            //todo mettere metodo anche qua
//        }
//
//        return "";
//
//    }


    @Override
    public void writeOnDb(String query) {
        try {
            // open a connection to the site
            URL url = new URL("https://personalsafety.000webhostapp.com/dbjava.php");
            URLConnection con = url.openConnection();
            // activate the output
            con.setDoOutput(true);
            PrintStream ps = new PrintStream(con.getOutputStream());
            // send your parameters to your site

            //mando una chiave per un minimo di sicurezza
            ps.print("key==@WG{,%8=M;Us2a,s-U'bWs\\OOv=L[qFleF(s?s*!DzA2XU-$MaMLunjVJtPr4z<dX.S@Qp4:UZH!>=,q\\;3]QWYQ5l]{(U,#'@90-/<M(}QhNkJEgX<lu71rs02g`q?x\"U#*\\6$L0P)Qv4=jbPN,,*rM3CFKYbLTQ:Xb[VK:6J.UmBK/7ACV[5#[Es2^o)#Ytm\\m1i3JRZoA<XWtOSj-dKP*7g-[1n$A%2Hv4_^bJj0FFRTb!H3h'`#MY.RK%H");
            ps.print("&query=" + query);

            // we have to get the input stream in order to actually send the
            // request
            //System.out.println(new BufferedReader(new InputStreamReader(con.getInputStream())).lines().collect(Collectors.joining("\n")));

            // close the print stream
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getChatId(String identificatore) {
        return getFromDb("SELECT idTelegram FROM dispositivi INNER JOIN utenti ON dispositivi.usernameUtente=utenti.username WHERE dispositivi.id='" + identificatore + "';");
    }

    @Override
    public String getFromDb(String query) {
        try {
            // open a connection to the site
            URL url = new URL("https://personalsafety.000webhostapp.com/dbjava.php");
            URLConnection con = url.openConnection();
            // activate the output
            con.setDoOutput(true);
            PrintStream ps = new PrintStream(con.getOutputStream());
            // send your parameters to your site

            //mando una chiave per un minimo di sicurezza
            ps.print("key==@WG{,%8=M;Us2a,s-U'bWs\\OOv=L[qFleF(s?s*!DzA2XU-$MaMLunjVJtPr4z<dX.S@Qp4:UZH!>=,q\\;3]QWYQ5l]{(U,#'@90-/<M(}QhNkJEgX<lu71rs02g`q?x\"U#*\\6$L0P)Qv4=jbPN,,*rM3CFKYbLTQ:Xb[VK:6J.UmBK/7ACV[5#[Es2^o)#Ytm\\m1i3JRZoA<XWtOSj-dKP*7g-[1n$A%2Hv4_^bJj0FFRTb!H3h'`#MY.RK%H");
            ps.print("&query=" + query);

            // we have to get the input stream in order to actually send the
            // request
            String ris = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().collect(Collectors.joining("\n"));

            // close the print stream
            ps.close();
            return ris;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
