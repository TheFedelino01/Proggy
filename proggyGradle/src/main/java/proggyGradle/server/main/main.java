/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.main;

import proggyGradle.Socket.thSocket;
import proggyGradle.server.manager;
import proggyGradle.server.utility.ESPManager;
import proggyGradle.server.utility.Telegram;

import java.io.IOException;

/**
 * @author saccani_federico
 */
public class main {

    public static void main(String[] args) {


        manager salvataggi = new manager();
        try {
            thSocket thSocket = new thSocket(4040, salvataggi);
            thSocket.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Telegram t = new Telegram();
        t.start();

        ESPManager m = new ESPManager(1234);
        m.start();


//
//        NFCManager m=new NFCManager("COM4");
//        m.write("12345\n");
    }
}
