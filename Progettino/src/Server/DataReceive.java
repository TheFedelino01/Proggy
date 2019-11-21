package Server;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class DataReceive {

    public static HttpServer createConnection(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/info", new InfoHandler());
            server.createContext("/get", new GetHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            return server;
        } catch (IOException ex) {
            Logger.getLogger(DataReceive.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    };
    
    

  static class InfoHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      String response = "Use /get to download a PDF";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  static class GetHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {

      // add the required response header for a PDF file
      Headers h = t.getResponseHeaders();
      h.add("Content-Type", "application/pdf");

      // a PDF (you provide your own!)
      File file = new File ("c:/temp/doc.pdf");
      byte [] bytearray  = new byte [(int)file.length()];
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(bytearray, 0, bytearray.length);

      // ok, we are ready to send the response.
      t.sendResponseHeaders(200, file.length());
      OutputStream os = t.getResponseBody();
      os.write(bytearray,0,bytearray.length);
      os.close();
    }
  }

    public static JSONObject receive() {
        DataInputStream in = new DataInputStream(new BufferedInputStream(createConnection(3333).getInputStream()));

    }
;
}
