/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.utility;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

public class NFCManager {

    SerialPort sp = null;
    String com=null;

    public NFCManager(String com) {
        this.com=com;
        if (open(com)) {       
            System.out.println("aperta");
        }
    }

    public void write(String s) {
        if (sp.isOpen()) {
            try {
                sp.getOutputStream().write(s.getBytes());
                sp.getOutputStream().flush();
                System.out.println("Scritto");
                close();
            } catch (IOException e) {
                System.out.println("Errore scrittura");
            }
        }else{
            open(com);
            write(s);
        }
    }

    private void close() {
        try {
            sp.closePort();
            System.out.println("chiusa");
        } catch (Exception e) {
            System.out.println("Errore chiusura");
        }
    }

    private boolean open(String com) {
        try {
            sp = SerialPort.getCommPort(com);
            sp.setComPortParameters(9600, 8, 1, 0);
            sp.openPort();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to open port :(");
            return false;

        }
    }
}
