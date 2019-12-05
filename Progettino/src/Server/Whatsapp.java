/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author tiabr
 */
public class Whatsapp {

    public static String send() throws MalformedURLException, ProtocolException, IOException {

        URL url = new URL("https://wa.me/393459499843?text=ciao");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
      
        return con.getResponseMessage();
    }
}
