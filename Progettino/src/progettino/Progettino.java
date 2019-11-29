package progettino;

import Server.VoipCall;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Progettino {

    public static void main(String[] args) throws IOException {
        try {
            // TODO code application logic here
            VoipCall.chiama();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Progettino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
