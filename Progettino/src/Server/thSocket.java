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
import main.salvataggi;

/**
 *
 * @author saccani_federico
 * GESTISCE INVIO E RICEZIONE
 */
public class thSocket extends Thread {

    private socketUDP socket;
    private salvataggi salvataggi;
    
    public thSocket(int portaAscolto, salvataggi saves) {
        socket= new socketUDP(portaAscolto);
        salvataggi = saves;
        //socket.setTimeOut(2000);
    }

    @Override
    public void run() {
        while (true) {
            cmdRicevuto comandoComplesso = socket.receive();       
            
            if (comandoComplesso != null) {
                String cmdSplitted[] = comandoComplesso.getComando().split(";");
                String comandoTxt = cmdSplitted[0];
                String identificatore = cmdSplitted[1]; 
                
                if (comandoTxt.equals("COORDINATE")) {
                    String coordinates = salvataggi.getLastCoordinate(identificatore).toString();
                    socket.send(coordinates,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio le coordinate

                } 
            }
        }
    }
}
