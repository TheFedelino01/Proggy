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
import main.device;
import main.salvataggi;

/**
 *
 * @author saccani_federico
 * GESTISCE INVIO E RICEZIONE
 */
public class thSocket extends Thread {

    private socketUDP socket;
    private salvataggi salvataggi;
    private thPhone thPhone;
    
    public thSocket(int portaAscolto, salvataggi saves) {
        socket= new socketUDP(portaAscolto);
        salvataggi = saves;
        //socket.setTimeOut(3000);
    }

    //DESCRIZIONE PROTOCOLLO:
    //COORDINATE;identificativo ->Restituisco l'ultima coordinata registrata
    //BATTITO;identificativo -> Restituisco l'ultimo battito registrato
    //SHAKE;indentificativo;tempoDiVibrazione -> Dico al dispositivo di vibrare per tempoDiVibrazione milllis
    @Override
    public void run() {   
        
        while (true) {
            cmdRicevuto comandoComplesso = socket.receive();       
            boolean continua=true;
            if (comandoComplesso != null) {
                String cmdSplitted[] = comandoComplesso.getComando().split(";");
                String comandoTxt = cmdSplitted[0];
                String identificatore="";
                
                try{
                identificatore = cmdSplitted[1]; 
                }catch(java.lang.ArrayIndexOutOfBoundsException e){continua=false; System.out.println("Identificatore non pervenuto: "+e.toString());}
                
                if(continua){
                    switch(comandoTxt){
                        case "COORDINATE":
                            String coordinates = salvataggi.getLastCoordinate(identificatore).toString();
                            socket.send(coordinates,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio le coordinate
                            break;

                        case "BATTITO":
                            String battiti = salvataggi.getLastBattito(identificatore).toString();
                            socket.send(battiti,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio il battito
                            break;

                        case "SHAKE":
                            //Dico  al dispositivo di vibrare per cmdSplitted[2] millis
                            socket.send("SHAKE;"+cmdSplitted[2], salvataggi.getPort(identificatore), salvataggi.getIP(identificatore));
                            socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                            break;

                        case "EMERGENZA":
                            //TODO @tiaBroch INVIA MESSAGGIO TELEGRAM
                            //Effettuo le chiamate per i parenti
                            thPhone = new thPhone(salvataggi.getDevice(identificatore),"PARENTI");
                            thPhone.start();

                            socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                            break;

                        case "OK-ACK":
                            break;
                    }
                }else{
                    socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                }

            }
        }
    }
}
