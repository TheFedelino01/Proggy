/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

/**
 *
 * @author saccani_federico
 */
public class cmdRicevuto {
    private String ipv4;
    private int portaRicevuta;
    private String comando;
    
    public cmdRicevuto(String ipv4, int portaRicevuta, String comando){
        this.ipv4=ipv4;
        this.portaRicevuta=portaRicevuta;
        this.comando=comando;
    }
    
    public int getPorta(){
        return portaRicevuta;
    }
    
    public String getIP(){
        return ipv4;
    }
    
    public String getComando(){
        return comando;
    }
}
