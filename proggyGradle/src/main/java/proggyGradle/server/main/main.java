/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.main;

import java.io.IOException;
import proggyGradle.Socket.thSocket;
import proggyGradle.server.manager;
import proggyGradle.server.utility.NFCManager;

/**
 *
 * @author saccani_federico
 */
public class main {
    public static void main(String[] args) throws IOException {
        manager salvataggi = new manager();
        
        thSocket thSocket = new thSocket(4210,salvataggi);
        thSocket.start();
        
        //Telegram t = new Telegram();
        //t.start();
        
        NFCManager m=new NFCManager("COM4");
        m.write("12345\n");
        
       
    }
}
