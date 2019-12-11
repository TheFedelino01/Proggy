/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Whatsapp {

    public static void send() throws IOException {
        URL url = new URL("https://www.waboxapp.com/api/send/chat");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        System.out.println(con.getResponseMessage());
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);

        String postData = "token=my-test-api-key&uid=393459499843&to=393459499843&custom_uid=msg-5696&text=Hello world!";
        con.setRequestProperty("Content-length", String.valueOf(postData.length()));

        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        System.out.println(con.getOutputStream());
        output.writeBytes(postData);
        output.close();
    }
}
