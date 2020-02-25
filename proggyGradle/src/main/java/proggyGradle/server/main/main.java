/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.main;

import proggyGradle.Database.dbManager;
import proggyGradle.Socket.thSocket;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
import proggyGradle.server.coordinate;
import proggyGradle.server.manager;
import proggyGradle.server.utility.ESPManager;
import proggyGradle.server.utility.NFCManager;
import proggyGradle.server.utility.Telegram;

import java.io.IOException;

/**
 * @author saccani_federico
 */
public class main {

    public static void main(String[] args) {


        Thread t3 = new Thread() {
            public void run() {
                manager salvataggi = new manager();
                //dbManager.getIstance().createConnection("my_personalsafety");
                try {
                    thSocket thSocket = new thSocket(4040, salvataggi);
                    thSocket.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();


        Thread t1 = new Thread() {
            public void run() {
                Telegram t = new Telegram();
                t.start();
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            public void run() {
                ESPManager m = new ESPManager(1234);
                m.start();
            }
        };
        t2.start();

//
//        NFCManager m=new NFCManager("COM4");
//        m.write("12345\n");
    }
}
