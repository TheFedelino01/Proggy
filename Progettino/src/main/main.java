/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Server.*;
import java.io.IOException;
import java.net.ProtocolException;

/**
 *
 * @author saccani_federico
 */
public class main {
    public static void main(String[] args) throws ProtocolException, IOException {
        thSocket thSocket = new thSocket(4210);
        //thSocket.start();
        Whatsapp.send();
        
       
    }
}
