/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggyGradle.server.utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import purejavacomm.*;

public class NFCManager {

    SerialPort sp = null;
    String com = null;
    private DataOutputStream outputStream;

    public NFCManager(String com) {
        this.com = com;
        if (open(com)) {
            System.out.println("aperta");
        }
    }

    public void write(String s) {
        try {
            outputStream.writeChars(s);
            System.out.println("Scritto");
            DataInputStream inputStream = new DataInputStream(sp.getInputStream());
            
            while(true)
                System.out.println(inputStream.readLine());

        } catch (IOException e) {
            System.out.println("Errore scrittura");
        }
    }

    private boolean open(String com) {
        try {
            sp = (SerialPort) CommPortIdentifier.getPortIdentifier(com).open("AAA", 2000);//selectSerialPort();
            sp.setSerialPortParams(9600, 8, 1, 0);
            outputStream = new DataOutputStream(sp.getOutputStream());
            return true;
        } catch (Exception e) {
            System.out.println("Failed to open port :(");
            return false;

        }
    }
}
