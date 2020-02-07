/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.main;

import java.io.IOException;

import proggyGradle.Socket.thSocket;
import proggyGradle.server.Trilateration.APInfo;
import proggyGradle.server.Trilateration.Trilateration;
import proggyGradle.server.coordinate;
import proggyGradle.server.manager;

/**
 * @author saccani_federico
 */
public class main {
    public static void main(String[] args) throws IOException {
        manager salvataggi = new manager();

        thSocket thSocket = new thSocket(4210, salvataggi);
        thSocket.start();

        //Telegram t = new Telegram();
        //t.start();

//        NFCManager m=new NFCManager("COM4");
//        m.write("12345\n");
        APInfo ap1 = new APInfo("AAA", "BBB", new coordinate(37.418436, -121.963477), 0.265710701754);
        APInfo ap2 = new APInfo("AAA", "BBB", new coordinate(37.417243, -121.961889), 0.234592423446);
        APInfo ap3 = new APInfo("AAA", "BBB", new coordinate(37.418692, -121.960194), 0.0548954278262);
        System.out.println("COORDINATE TRILATERAZIONE: " + new Trilateration(ap1, ap2, ap3).getPoint());

    }
}
