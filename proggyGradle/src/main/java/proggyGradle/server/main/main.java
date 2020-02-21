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
import proggyGradle.server.utility.ESPManager;
import proggyGradle.server.utility.NFCManager;
import proggyGradle.server.utility.Telegram;

/**
 * @author saccani_federico
 */
public class main {

    public static void main(String[] args) {

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

        Thread t3 = new Thread() {
            public void run() {
                manager salvataggi = new manager();

                thSocket thSocket = new thSocket(4210, salvataggi);
                thSocket.start();
            }
        };
        t3.start();



        ESPManager m=new ESPManager(1234);
        m.start();

//
//        NFCManager m=new NFCManager("COM4");
//        m.write("12345\n");
    }
}
