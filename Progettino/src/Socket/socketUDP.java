/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 *
 * @author feder
 */
public class socketUDP {
    // buffer di spedizione e ricezione
    private byte[] bufferOUT = new byte[1028]; 
    private byte[] bufferIN = new byte[1028];
    private DatagramSocket socket;
    private DatagramPacket ultimoPacchettoRicevuto;
    
    public socketUDP(){
        try{
        socket = new DatagramSocket();
        System.out.println("Client ready!");
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString());}
       
    }
    public socketUDP(int port){
        try{
        socket = new DatagramSocket(port);
        System.out.println("Server ready!");
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString());}
       
    }
    
    //SI ASPETTA UN ACK
    public cmdRicevuto send(String msg, int port, String ip){
        try{
        //Preparo il buffer output
        bufferOUT = msg.getBytes();
        //Creo il pacchetto da inviare
        DatagramPacket sendPacket = new DatagramPacket(bufferOUT, msg.length(), InetAddress.getByName(ip), port);
        //Invio
        socket.send(sendPacket);
        
        System.out.println("Send: "+msg+" ip: "+ip+" porta: "+port);
        
        System.out.println("Aspetto ACK");
        cmdRicevuto ipoteticoACK = receive();//Aspetto ACK
        if(ipoteticoACK.getComando().contains("ACK"))
            return ipoteticoACK;
        else
            return null;
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString()); return null;}
    }
    
    //SI ASPETTA UN ACK
    public cmdRicevuto send(byte[] msg, int port, String ip){
        try{
        //Preparo il buffer output
        bufferOUT = msg;
        //Creo il pacchetto da inviare
        DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, InetAddress.getByName(ip), port);
        //Invio
        socket.send(sendPacket);
        
        return receive();//Aspetto ACK
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString()); return null;}
    }
    
    //NON ASPETTO NESSUN ALTRO ACK
    public void inviaACK(int port, String ip){
        try{
        //Preparo il buffer output
        bufferOUT = "OK-ACK".getBytes();
        //Creo il pacchetto da inviare
        DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, InetAddress.getByName(ip), port);
        //Invio
        socket.send(sendPacket);
        System.out.println("Send ACK! ip: "+ip+" porta: "+port);
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString());}
    }
    
    //IL RICEVI NON INVIA ACK IN MODO AUTOMATICO
    //Perché se mi chiede un dato e io gli invio ACK poi se gli invio il dato richiesto, non capirebbe
     public cmdRicevuto receive(){
        try{
        
        cmdRicevutoByte ricevuto = receiveByte();
        String comandoStrLungo = new String(ricevuto.getComando());
        
        // elaborazione dei dati ricevuti eliminando i caratteri in eccesso
        String comandoStr = comandoStrLungo.substring(0, ultimoPacchettoRicevuto.getLength());
        
        System.out.println("Ricevuto: "+comandoStr);       
        return new cmdRicevuto(ricevuto.getIP(),ricevuto.getPorta(),comandoStr); 
            
        }catch(Exception e){System.out.println("Errore socket: "+e.toString()); return null;}
    }
    
    public cmdRicevutoByte receiveByte(){
        try{
        //Creo l'allocazione del pacchetto da ricevere
        ultimoPacchettoRicevuto = new DatagramPacket(bufferIN, bufferIN.length);
        
        //Aspetto il pacchetto
        socket.receive(ultimoPacchettoRicevuto);
        
        //int offset = ultimoPacchettoRicevuto;
             
        int lung = ultimoPacchettoRicevuto.getLength();
        byte[] comando = Arrays.copyOfRange(ultimoPacchettoRicevuto.getData(),0,lung);
        
        return new cmdRicevutoByte(getIP(),ultimoPacchettoRicevuto.getPort(),comando);
        
        }catch(Exception e){System.out.println("Errore socket: "+e.toString()); return null;}
    }
    
    
    public int getPort(){
        return ultimoPacchettoRicevuto.getPort();
    }
    
    public String getIP(){
       return ultimoPacchettoRicevuto.getAddress().toString().replace("/", "");
    }
    
    public void closeConnection(){
        socket.close();
    }

    public void setTimeOut(int i) {
        try{
        socket.setSoTimeout(i);
        }catch(SocketException e){
            System.out.println("Impossibile ricevere risposta dal server: "+e.toString());
        }
    }
}
