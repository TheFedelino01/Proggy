/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proggy.server;

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
    private String query;

    public String getQuery(boolean caduta) {
        Integer cad = 0;
        if(caduta == true){
            cad = 1;
        }
        
        query = "INSERT INTO" + "evento" + "(data, posizione, caduta, battiti)" + "VALUES" + 
                "('" + battiti.getLastOra().toString() + "'," + "'" +coordinates.getLast().toString() + 
                "'," + "'" + cad.toString() + "'," + battiti.getLastBattito() + "'," + ")";
        
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

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
        this.query="";
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
        return battiti.getLastBattito();
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
    public void addBattiti(String battiti){
        this.battiti.add(Integer.parseInt(battiti));
    }
}
