/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author saccani_federico
 * GESTISCE INVIO E RICEZIONE
 */
public class thSocket extends Thread {

    private socketUDP socket;
    
    public thSocket(int portaAscolto) {
        socket= new socketUDP(portaAscolto);

        //socket.setTimeOut(2000);
    }

    @Override
    public void run() {
        while (true) {
            cmdRicevuto comandoComplesso = socket.receive();
            socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio ACK
                    
            System.out.println(comandoComplesso.getComando());
            
            if (comandoComplesso != null) {
                String comandoTxt = comandoComplesso.getComando();
                
                String[] splitted = comandoTxt.split(";");
                String cmd = splitted[0];
                
                if (cmd.equals("DATO")) {
                    socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio ACK
                    
                    String nomePeriferica = splitted[1];
                    String dato = splitted[2];

                    
                    //System.out.println("Ricevuta rilevazione: "+nomePeriferica+" dato: "+dato);
                    
                } else if (cmd.equals("DIMMELO")) {
                    String nomePeriferica = splitted[1];

                    
                    //Rispondo al client che mi ha fatto la richiesta inviandogli il valore richiesto
                    //socket.send(valore, comandoComplesso.getPorta(), comandoComplesso.getIP());

                }else if(cmd.equals("ACCENDI")){
                    String ipClient=comandoComplesso.getIP();
                    int portaClient = comandoComplesso.getPorta();
                    

                }
            }
        }
    }
}
