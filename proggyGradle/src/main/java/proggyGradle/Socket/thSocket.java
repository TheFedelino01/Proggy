/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.Socket;

import proggyGradle.Database.DbManager;
import proggyGradle.Database.DbManagerLocal;
import proggyGradle.server.coordinate;
import proggyGradle.server.manager;
import proggyGradle.server.utility.Telegram;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author saccani_federico
 * GESTISCE INVIO E RICEZIONE
 */
public class thSocket extends Thread {

    private ServerSocket socket;
    private manager manager;
    private thPhone thPhone;

    public thSocket(int portaAscolto, manager saves) throws IOException {
        socket = new ServerSocket(portaAscolto);
        manager = saves;
        //socket.setTimeOut(3000);
    }

    //DESCRIZIONE PROTOCOLLO:
    //ACADUTA;ricezione da arduino;sccrivo su db tutti i dati
    //ACOORDINATE;ricezione da arduino;coordinate;salvo in ram
    //ABATTITO;ricezione da arduino;battiti;salvo in ram
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

        int i = 0;
        long startTime = 0;

        try {


            while (true) {       //we want the server to run till the end of times

                Socket sock = socket.accept();             //will block until connection recieved

                System.out.println("connesso: " + sock.toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
                String line = br.readLine();
                System.out.println("Ricevuto: " + line);

                boolean continua = true;

                if (line != null) {
                    String[] cmdSplitted = line.split(";");
                    String comandoTxt = cmdSplitted[0];
                    String identificatore = "";
                    long indentificatoreLong = -1;

                    //sccrivo ogni 60 secondi su db se il dispositivo è attivo e manda dati
                    if (i == 0) {
                        startTime = System.currentTimeMillis();
                        i++;
                    }

                    if (i == 1 && (System.currentTimeMillis() - startTime) / 1000 > 60) {
                        //TODO: SCOMMENTARE QUANTO LA SCRITTURA SU DB FUNZIONERA'
                        //dbManager.getIstance().scriviDB(manager.getDevice(identificatore).getQuery(false));
                        i = 0;
                    }

                    try {
                        identificatore = cmdSplitted[1];
                        indentificatoreLong = Long.parseLong(DbManager.getInstance().getChatId(identificatore));
                    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        continua = false;
                        System.out.println("Identificatore non pervenuto: " + e.toString());
                    }

                    if (continua) {
                        switch (comandoTxt) {

                            case "test":
                                pr.println("WORKING");
                                break;

//                            case "ACADUTA":
//                                manager.salvaCoordinate(identificatore, cmdSplitted[0]);
//                                DbManager.getInstance().writeOnDb(manager.getDevice(identificatore).getQuery(true));
//                                break;
//
//                            case "ACOORDINATE":
//                                manager.salvaCoordinate(identificatore, cmdSplitted[2]);
//                                break;
//
//                            case "ABATTITO":
//                                manager.salvaBattiti(identificatore, cmdSplitted[2]);
//                                break;

                            case "COORDINATE":
                                coordinate coord = manager.getLastCoordinate(identificatore);
                                if (coord != null)
                                    pr.println(coord.toString());//invio le coordinate
                                else
                                    pr.println("Impossibile localizzare");
                                break;

                            case "BATTITO":
                                String battiti = manager.getLastBattito(identificatore).toString();
                                pr.println(battiti);//invio il battito
                                break;

                            case "SHAKE":
                                //Dico  al dispositivo di vibrare per cmdSplitted[2] millis
                                pr.println("SHAKE;" + cmdSplitted[2]);
                                //socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                                Telegram.scrivi("REQUEST SHAKING DEVICE...", indentificatoreLong);
                                break;

                            case "EMERGENZA":
                                //Effettuo le chiamate per i parenti
                                thPhone = new thPhone(manager.getDevice(identificatore), "PARENTI");
                                // thPhone.start(); TODO SCOMMENTARE PER EFFETTUARE LE CHIAMATE
                                Telegram.emergenza(indentificatoreLong);
                                pr.println("WORKING");
                                break;

                            case "RESOCONTO":
                                String listaToSend = manager.getResoconto(identificatore, cmdSplitted[2]);
                                pr.println(listaToSend);//invio il resoconto
                                break;

                            case "MEDIA":
                                String valori = manager.getMedia(identificatore);
                                pr.println(valori);//invio info MEDIA,MAX e MIN
                                break;

//                            case "OK-ACK":
//                                break;
                        }
                    } else {
                        //socket.inviaACK(comandoComplesso.getPorta(), comandoComplesso.getIP());//invio l'ack
                    }


                    //Closing streams and the current socket (not the listening socket!)
                    pr.close();
                    br.close();
                    sock.close();
                }


            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}