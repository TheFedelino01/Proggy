package Server;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataReceive {

    public static String socketReceive(int port) throws SocketException {
            DatagramSocket serverSocket = new DatagramSocket(port);//6789
            String out = "";
            boolean attivo = true;				// per la ripetizione
            byte[] bufferIN = new byte[1024];	// buffer spedizione e ricezione
            byte[] bufferOUT = new byte[1024];
            System.out.println("Server avviato...");
            while (attivo) {
                // definizione del datagramma
                DatagramPacket receivePacket
                        = new DatagramPacket(bufferIN, bufferIN.length);
                try {
                    // attesa della ricezione dato dal client
                    serverSocket.receive(receivePacket);
                } catch (IOException ex) {
                    Logger.getLogger(DataReceive.class.getName()).log(Level.SEVERE, null, ex);
                }

                // analisi del pacchetto ricevuto
                String ricevuto = new String(receivePacket.getData());
                int numCaratteri = receivePacket.getLength();
                ricevuto = ricevuto.substring(0, numCaratteri);	// elimina i caratteri in eccesso
                System.out.println("RICEVUTO: " + ricevuto);
                out = ricevuto;
                // riconoscimento dei parametri del socket dei client
                InetAddress IPClient = receivePacket.getAddress();
                int portClient = receivePacket.getPort();
                // preparo il dato da spedire
                String daSpedire = ricevuto.toUpperCase();
                bufferOUT = daSpedire.getBytes();
                // invio del datagramma             
                DatagramPacket sendPacket
                        = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portClient);
                try {
                    serverSocket.send(sendPacket);
                } catch (IOException ex) {
                    Logger.getLogger(DataReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
                // controllo termine esecuzione del server
                if (ricevuto.equals("fine")) {
                    System.out.println("SERVER IN CHIUSURA.");
                    attivo = false;
                }
        }
        serverSocket.close();
        return out;
    }
;
}
