/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saccani_federico
 */
public class device {
    private String identificatore;
    private coordinates coordinates;
    private battiti battiti;

    private List<emergencyNumber> numeriEmergenza;
    private int port=-1;
    private String ip="";
    private String nomeTutore="";
    
    public device(String identificatore,int port,String ip,String nomeTutore){
        coordinates = new coordinates();
        battiti = new battiti();
        this.identificatore=identificatore;
        numeriEmergenza= new ArrayList<emergencyNumber>();
        this.port=port;
        this.ip=ip;
        this.nomeTutore=nomeTutore;
    }

    public String getIdentificatore() {
        return identificatore;
    }
    
    public String getTutore() {
        return nomeTutore;
    }

    public coordinate getLastCoordinate() {
        return coordinates.getLast();
    }

    public Integer getLastBattito() {
        return battiti.getLast();
    }
    
    public void addEmergencyNumber(String numero,String tipologia){
        numeriEmergenza.add(new emergencyNumber(numero,tipologia));
    }
    
    public List<emergencyNumber> getEmergencyNumbers(){
        return numeriEmergenza;
    }

    public int getPort() {
        return port;
    }
    
    public String getIP() {
        return ip;
    }

    public String getResoconto(String cosaVoglioOttenere) {
        String ris="";
        if(cosaVoglioOttenere.equals("*")){
            ris="COORDINATE;"+coordinates.getResoconto()+"BATTITI;"+battiti.getResoconto();
            
        }else if(cosaVoglioOttenere.toLowerCase().equals("battiti")){
            ris=battiti.getResoconto();
            
        }else if(cosaVoglioOttenere.toLowerCase().equals("coordinate")){
            ris=coordinates.getResoconto();
        }
        
        return ris;
    }

    public String getMedia() {
        return battiti.getMedia();
    }
    
    public coordinates getCoordinates() {
        return coordinates;
    }
}
