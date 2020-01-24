/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Database.dbManager;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import main.device;
import main.manager;

/**
 *
 * @author saccani_federico
 * GESTISCE INVIO E RICEZIONE
 */
public class thSocket extends Thread {

    private socketUDP socket;
    private manager manager;
    private thPhone thPhone;
    
    public thSocket(int portaAscolto, manager saves) {
        socket= new socketUDP(portaAscolto);
        manager = saves;
        //socket.setTimeOut(3000);
    }

    //DESCRIZIONE PROTOCOLLO:
    //ADD-COORDINATE;indentificativo;longitudine,latitudine
    //COORDINATE;identificativo ->Restituisco l'ultima coordinata registrata
    //BATTITO;identificativo -> Restituisco l'ultimo battito registrato
    //SHAKE;indentificativo;tempoDiVibrazione -> Dico al dispositivo di vibrare per tempoDiVibrazione milllis
    //RESOCONTO;identificativo;battitiOcoordinate; -> restituisco elenco battitiOcoordinate
            //return 07/01/2020 19:25:12-78;07/01/2020 19:26:10-85
    //MEDIA;identificativo; --> Dice la media, il valore max, quello min e il numero di battiti registrati
            //return media;MaxValue;minValue
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
                        
                        case "ACOORDINATE":
                            dbManager.getIstance().query("select * from stoCazzo");
                            manager.salvaCoordinate(identificatore, cmdSplitted[2]);
                            break;
                        
                        case "COORDINATE":
                            String coordinates = manager.getLastCoordinate(identificatore).toString();
                            socket.send(coordinates,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio le coordinate
                            break;

                        case "BATTITO":
                            String battiti = manager.getLastBattito(identificatore).toString();
                            socket.send(battiti,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio il battito
                            break;

                        case "SHAKE":
                            //Dico  al dispositivo di vibrare per cmdSplitted[2] millis
                            socket.send("SHAKE;"+cmdSplitted[2], manager.getPort(identificatore), manager.getIP(identificatore));
                            socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                            break;

                         case "EMERGENZA":
                            //Effettuo le chiamate per i parenti
                            thPhone = new thPhone(manager.getDevice(identificatore),"PARENTI");
                            //thPhone.start();
                            //Telegram.emergenza(manager.getDevicesWithName(/* qua è da fare una query al db che avendo l'identificatore del dispositivo ti ricava di chi è il dispositivo(tutore) e il suo chat id*/));
                            break;
                            
                        case "RESOCONTO":
                            String listaToSend = manager.getResoconto(identificatore,cmdSplitted[2]);
                            socket.send(listaToSend,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio il resoconto
                            break;
                            
                        case "MEDIA":
                            String valori = manager.getMedia(identificatore);
                            socket.send(valori,comandoComplesso.getPorta(), comandoComplesso.getIP());//invio info MEDIA,MAX e MIN
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
