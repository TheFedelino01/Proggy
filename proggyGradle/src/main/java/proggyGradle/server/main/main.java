/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.main;

import proggyGradle.Socket.thSocket;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
import proggyGradle.server.coordinate;
import proggyGradle.server.manager;
import proggyGradle.server.utility.NFCManager;
import proggyGradle.server.utility.Telegram;

/**
 * @author saccani_federico
 */
public class main {
    public static void main(String[] args) {
//        manager salvataggi = new manager();
//
//        thSocket thSocket = new thSocket(4210, salvataggi);
//        thSocket.start();
//
//        Telegram t = new Telegram();
//        t.start();
//
//        NFCManager m=new NFCManager("COM4");
//        m.write("12345\n");


        //TEST CON GOOGLE MAPS:
        //https://drive.google.com/open?id=1XUDLaPY-YEZqhT0H5U3juKbUFTnGaRZj&usp=sharing
        APInfo ap1 = new APInfo("AP1", "", new coordinate(45.68779, 9.18132), /*0.016*/-85);
        APInfo ap2 = new APInfo("AP2", "", new coordinate(45.68771, 9.1811), /*0.011*/-80);
        APInfo ap3 = new APInfo("AP3", "", new coordinate(45.68752, 9.18111), /*0.018*/-88);
        System.out.println("COORDINATE TRILATERAZIONE: " + new Trilateration(ap1, ap2, ap3).getPoint());

    }
}
