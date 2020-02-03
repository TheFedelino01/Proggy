/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.main;

import Database.dbManager;
import Socket.thSocket;
import Server.*;
import java.io.IOException;
import java.net.ProtocolException;
import server.manager;

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
        
       
    }
}
